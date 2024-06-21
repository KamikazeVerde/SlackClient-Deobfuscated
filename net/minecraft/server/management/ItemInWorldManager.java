/*     */ package net.minecraft.server.management;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockChest;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*     */ import net.minecraft.network.play.server.S38PacketPlayerListItem;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.ILockableContainer;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemInWorldManager
/*     */ {
/*     */   public World theWorld;
/*     */   public EntityPlayerMP thisPlayerMP;
/*  30 */   private WorldSettings.GameType gameType = WorldSettings.GameType.NOT_SET;
/*     */   
/*     */   private boolean isDestroyingBlock;
/*     */   
/*     */   private int initialDamage;
/*  35 */   private BlockPos field_180240_f = BlockPos.ORIGIN;
/*     */ 
/*     */   
/*     */   private int curblockDamage;
/*     */ 
/*     */   
/*     */   private boolean receivedFinishDiggingPacket;
/*     */   
/*  43 */   private BlockPos field_180241_i = BlockPos.ORIGIN;
/*     */   private int initialBlockDamage;
/*  45 */   private int durabilityRemainingOnBlock = -1;
/*     */ 
/*     */   
/*     */   public ItemInWorldManager(World worldIn) {
/*  49 */     this.theWorld = worldIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGameType(WorldSettings.GameType type) {
/*  54 */     this.gameType = type;
/*  55 */     type.configurePlayerCapabilities(this.thisPlayerMP.capabilities);
/*  56 */     this.thisPlayerMP.sendPlayerAbilities();
/*  57 */     this.thisPlayerMP.mcServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.UPDATE_GAME_MODE, new EntityPlayerMP[] { this.thisPlayerMP }));
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldSettings.GameType getGameType() {
/*  62 */     return this.gameType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean survivalOrAdventure() {
/*  67 */     return this.gameType.isSurvivalOrAdventure();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCreative() {
/*  75 */     return this.gameType.isCreative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeGameType(WorldSettings.GameType type) {
/*  83 */     if (this.gameType == WorldSettings.GameType.NOT_SET)
/*     */     {
/*  85 */       this.gameType = type;
/*     */     }
/*     */     
/*  88 */     setGameType(this.gameType);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateBlockRemoving() {
/*  93 */     this.curblockDamage++;
/*     */     
/*  95 */     if (this.receivedFinishDiggingPacket) {
/*     */       
/*  97 */       int i = this.curblockDamage - this.initialBlockDamage;
/*  98 */       Block block = this.theWorld.getBlockState(this.field_180241_i).getBlock();
/*     */       
/* 100 */       if (block.getMaterial() == Material.air) {
/*     */         
/* 102 */         this.receivedFinishDiggingPacket = false;
/*     */       }
/*     */       else {
/*     */         
/* 106 */         float f = block.getPlayerRelativeBlockHardness((EntityPlayer)this.thisPlayerMP, this.thisPlayerMP.worldObj, this.field_180241_i) * (i + 1);
/* 107 */         int j = (int)(f * 10.0F);
/*     */         
/* 109 */         if (j != this.durabilityRemainingOnBlock) {
/*     */           
/* 111 */           this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180241_i, j);
/* 112 */           this.durabilityRemainingOnBlock = j;
/*     */         } 
/*     */         
/* 115 */         if (f >= 1.0F)
/*     */         {
/* 117 */           this.receivedFinishDiggingPacket = false;
/* 118 */           tryHarvestBlock(this.field_180241_i);
/*     */         }
/*     */       
/*     */       } 
/* 122 */     } else if (this.isDestroyingBlock) {
/*     */       
/* 124 */       Block block1 = this.theWorld.getBlockState(this.field_180240_f).getBlock();
/*     */       
/* 126 */       if (block1.getMaterial() == Material.air) {
/*     */         
/* 128 */         this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, -1);
/* 129 */         this.durabilityRemainingOnBlock = -1;
/* 130 */         this.isDestroyingBlock = false;
/*     */       }
/*     */       else {
/*     */         
/* 134 */         int k = this.curblockDamage - this.initialDamage;
/* 135 */         float f1 = block1.getPlayerRelativeBlockHardness((EntityPlayer)this.thisPlayerMP, this.thisPlayerMP.worldObj, this.field_180241_i) * (k + 1);
/* 136 */         int l = (int)(f1 * 10.0F);
/*     */         
/* 138 */         if (l != this.durabilityRemainingOnBlock) {
/*     */           
/* 140 */           this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, l);
/* 141 */           this.durabilityRemainingOnBlock = l;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockClicked(BlockPos pos, EnumFacing side) {
/* 156 */     if (isCreative()) {
/*     */       
/* 158 */       if (!this.theWorld.extinguishFire(null, pos, side))
/*     */       {
/* 160 */         tryHarvestBlock(pos);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 165 */       Block block = this.theWorld.getBlockState(pos).getBlock();
/*     */       
/* 167 */       if (this.gameType.isAdventure()) {
/*     */         
/* 169 */         if (this.gameType == WorldSettings.GameType.SPECTATOR) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 174 */         if (!this.thisPlayerMP.isAllowEdit()) {
/*     */           
/* 176 */           ItemStack itemstack = this.thisPlayerMP.getCurrentEquippedItem();
/*     */           
/* 178 */           if (itemstack == null) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/* 183 */           if (!itemstack.canDestroy(block)) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 190 */       this.theWorld.extinguishFire(null, pos, side);
/* 191 */       this.initialDamage = this.curblockDamage;
/* 192 */       float f = 1.0F;
/*     */       
/* 194 */       if (block.getMaterial() != Material.air) {
/*     */         
/* 196 */         block.onBlockClicked(this.theWorld, pos, (EntityPlayer)this.thisPlayerMP);
/* 197 */         f = block.getPlayerRelativeBlockHardness((EntityPlayer)this.thisPlayerMP, this.thisPlayerMP.worldObj, pos);
/*     */       } 
/*     */       
/* 200 */       if (block.getMaterial() != Material.air && f >= 1.0F) {
/*     */         
/* 202 */         tryHarvestBlock(pos);
/*     */       }
/*     */       else {
/*     */         
/* 206 */         this.isDestroyingBlock = true;
/* 207 */         this.field_180240_f = pos;
/* 208 */         int i = (int)(f * 10.0F);
/* 209 */         this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), pos, i);
/* 210 */         this.durabilityRemainingOnBlock = i;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void blockRemoving(BlockPos pos) {
/* 217 */     if (pos.equals(this.field_180240_f)) {
/*     */       
/* 219 */       int i = this.curblockDamage - this.initialDamage;
/* 220 */       Block block = this.theWorld.getBlockState(pos).getBlock();
/*     */       
/* 222 */       if (block.getMaterial() != Material.air) {
/*     */         
/* 224 */         float f = block.getPlayerRelativeBlockHardness((EntityPlayer)this.thisPlayerMP, this.thisPlayerMP.worldObj, pos) * (i + 1);
/*     */         
/* 226 */         if (f >= 0.7F) {
/*     */           
/* 228 */           this.isDestroyingBlock = false;
/* 229 */           this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), pos, -1);
/* 230 */           tryHarvestBlock(pos);
/*     */         }
/* 232 */         else if (!this.receivedFinishDiggingPacket) {
/*     */           
/* 234 */           this.isDestroyingBlock = false;
/* 235 */           this.receivedFinishDiggingPacket = true;
/* 236 */           this.field_180241_i = pos;
/* 237 */           this.initialBlockDamage = this.initialDamage;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelDestroyingBlock() {
/* 248 */     this.isDestroyingBlock = false;
/* 249 */     this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.field_180240_f, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean removeBlock(BlockPos pos) {
/* 259 */     IBlockState iblockstate = this.theWorld.getBlockState(pos);
/* 260 */     iblockstate.getBlock().onBlockHarvested(this.theWorld, pos, iblockstate, (EntityPlayer)this.thisPlayerMP);
/* 261 */     boolean flag = this.theWorld.setBlockToAir(pos);
/*     */     
/* 263 */     if (flag)
/*     */     {
/* 265 */       iblockstate.getBlock().onBlockDestroyedByPlayer(this.theWorld, pos, iblockstate);
/*     */     }
/*     */     
/* 268 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tryHarvestBlock(BlockPos pos) {
/* 278 */     if (this.gameType.isCreative() && this.thisPlayerMP.getHeldItem() != null && this.thisPlayerMP.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword)
/*     */     {
/* 280 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 284 */     IBlockState iblockstate = this.theWorld.getBlockState(pos);
/* 285 */     TileEntity tileentity = this.theWorld.getTileEntity(pos);
/*     */     
/* 287 */     if (this.gameType.isAdventure()) {
/*     */       
/* 289 */       if (this.gameType == WorldSettings.GameType.SPECTATOR)
/*     */       {
/* 291 */         return false;
/*     */       }
/*     */       
/* 294 */       if (!this.thisPlayerMP.isAllowEdit()) {
/*     */         
/* 296 */         ItemStack itemstack = this.thisPlayerMP.getCurrentEquippedItem();
/*     */         
/* 298 */         if (itemstack == null)
/*     */         {
/* 300 */           return false;
/*     */         }
/*     */         
/* 303 */         if (!itemstack.canDestroy(iblockstate.getBlock()))
/*     */         {
/* 305 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 310 */     this.theWorld.playAuxSFXAtEntity((EntityPlayer)this.thisPlayerMP, 2001, pos, Block.getStateId(iblockstate));
/* 311 */     boolean flag1 = removeBlock(pos);
/*     */     
/* 313 */     if (isCreative()) {
/*     */       
/* 315 */       this.thisPlayerMP.playerNetServerHandler.sendPacket((Packet)new S23PacketBlockChange(this.theWorld, pos));
/*     */     }
/*     */     else {
/*     */       
/* 319 */       ItemStack itemstack1 = this.thisPlayerMP.getCurrentEquippedItem();
/* 320 */       boolean flag = this.thisPlayerMP.canHarvestBlock(iblockstate.getBlock());
/*     */       
/* 322 */       if (itemstack1 != null) {
/*     */         
/* 324 */         itemstack1.onBlockDestroyed(this.theWorld, iblockstate.getBlock(), pos, (EntityPlayer)this.thisPlayerMP);
/*     */         
/* 326 */         if (itemstack1.stackSize == 0)
/*     */         {
/* 328 */           this.thisPlayerMP.destroyCurrentEquippedItem();
/*     */         }
/*     */       } 
/*     */       
/* 332 */       if (flag1 && flag)
/*     */       {
/* 334 */         iblockstate.getBlock().harvestBlock(this.theWorld, (EntityPlayer)this.thisPlayerMP, pos, iblockstate, tileentity);
/*     */       }
/*     */     } 
/*     */     
/* 338 */     return flag1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tryUseItem(EntityPlayer player, World worldIn, ItemStack stack) {
/* 347 */     if (this.gameType == WorldSettings.GameType.SPECTATOR)
/*     */     {
/* 349 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 353 */     int i = stack.stackSize;
/* 354 */     int j = stack.getMetadata();
/* 355 */     ItemStack itemstack = stack.useItemRightClick(worldIn, player);
/*     */     
/* 357 */     if (itemstack != stack || (itemstack != null && (itemstack.stackSize != i || itemstack.getMaxItemUseDuration() > 0 || itemstack.getMetadata() != j))) {
/*     */       
/* 359 */       player.inventory.mainInventory[player.inventory.currentItem] = itemstack;
/*     */       
/* 361 */       if (isCreative()) {
/*     */         
/* 363 */         itemstack.stackSize = i;
/*     */         
/* 365 */         if (itemstack.isItemStackDamageable())
/*     */         {
/* 367 */           itemstack.setItemDamage(j);
/*     */         }
/*     */       } 
/*     */       
/* 371 */       if (itemstack.stackSize == 0)
/*     */       {
/* 373 */         player.inventory.mainInventory[player.inventory.currentItem] = null;
/*     */       }
/*     */       
/* 376 */       if (!player.isUsingItem())
/*     */       {
/* 378 */         ((EntityPlayerMP)player).sendContainerToPlayer(player.inventoryContainer);
/*     */       }
/*     */       
/* 381 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 385 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean activateBlockOrUseItem(EntityPlayer player, World worldIn, ItemStack stack, BlockPos pos, EnumFacing side, float offsetX, float offsetY, float offsetZ) {
/* 398 */     if (this.gameType == WorldSettings.GameType.SPECTATOR) {
/*     */       
/* 400 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 402 */       if (tileentity instanceof ILockableContainer) {
/*     */         
/* 404 */         Block block = worldIn.getBlockState(pos).getBlock();
/* 405 */         ILockableContainer ilockablecontainer = (ILockableContainer)tileentity;
/*     */         
/* 407 */         if (ilockablecontainer instanceof net.minecraft.tileentity.TileEntityChest && block instanceof BlockChest)
/*     */         {
/* 409 */           ilockablecontainer = ((BlockChest)block).getLockableContainer(worldIn, pos);
/*     */         }
/*     */         
/* 412 */         if (ilockablecontainer != null)
/*     */         {
/* 414 */           player.displayGUIChest((IInventory)ilockablecontainer);
/* 415 */           return true;
/*     */         }
/*     */       
/* 418 */       } else if (tileentity instanceof IInventory) {
/*     */         
/* 420 */         player.displayGUIChest((IInventory)tileentity);
/* 421 */         return true;
/*     */       } 
/*     */       
/* 424 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 428 */     if (!player.isSneaking() || player.getHeldItem() == null) {
/*     */       
/* 430 */       IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */       
/* 432 */       if (iblockstate.getBlock().onBlockActivated(worldIn, pos, iblockstate, player, side, offsetX, offsetY, offsetZ))
/*     */       {
/* 434 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 438 */     if (stack == null)
/*     */     {
/* 440 */       return false;
/*     */     }
/* 442 */     if (isCreative()) {
/*     */       
/* 444 */       int j = stack.getMetadata();
/* 445 */       int i = stack.stackSize;
/* 446 */       boolean flag = stack.onItemUse(player, worldIn, pos, side, offsetX, offsetY, offsetZ);
/* 447 */       stack.setItemDamage(j);
/* 448 */       stack.stackSize = i;
/* 449 */       return flag;
/*     */     } 
/*     */ 
/*     */     
/* 453 */     return stack.onItemUse(player, worldIn, pos, side, offsetX, offsetY, offsetZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorld(WorldServer serverWorld) {
/* 463 */     this.theWorld = (World)serverWorld;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\server\management\ItemInWorldManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
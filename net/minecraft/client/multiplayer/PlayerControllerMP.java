/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import cc.slack.events.impl.player.AttackEvent;
/*     */ import cc.slack.utils.player.AttackUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*     */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.C0EPacketClickWindow;
/*     */ import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
/*     */ import net.minecraft.network.play.client.C11PacketEnchantItem;
/*     */ import net.minecraft.stats.StatFileWriter;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ 
/*     */ public class PlayerControllerMP
/*     */ {
/*     */   private final Minecraft mc;
/*     */   private final NetHandlerPlayClient netClientHandler;
/*  39 */   private BlockPos currentBlock = new BlockPos(-1, -1, -1);
/*     */ 
/*     */ 
/*     */   
/*     */   private ItemStack currentItemHittingBlock;
/*     */ 
/*     */ 
/*     */   
/*     */   public float curBlockDamageMP;
/*     */ 
/*     */ 
/*     */   
/*     */   private float stepSoundTickCounter;
/*     */ 
/*     */ 
/*     */   
/*     */   public int blockHitDelay;
/*     */ 
/*     */   
/*     */   public boolean isHittingBlock;
/*     */ 
/*     */   
/*  61 */   private WorldSettings.GameType currentGameType = WorldSettings.GameType.SURVIVAL;
/*     */ 
/*     */   
/*     */   private int currentPlayerItem;
/*     */ 
/*     */   
/*     */   public PlayerControllerMP(Minecraft mcIn, NetHandlerPlayClient p_i45062_2_) {
/*  68 */     this.mc = mcIn;
/*  69 */     this.netClientHandler = p_i45062_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clickBlockCreative(Minecraft mcIn, PlayerControllerMP p_178891_1_, BlockPos p_178891_2_, EnumFacing p_178891_3_) {
/*  74 */     if (!mcIn.theWorld.extinguishFire((EntityPlayer)mcIn.thePlayer, p_178891_2_, p_178891_3_))
/*     */     {
/*  76 */       p_178891_1_.onPlayerDestroyBlock(p_178891_2_, p_178891_3_);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerCapabilities(EntityPlayer p_78748_1_) {
/*  85 */     this.currentGameType.configurePlayerCapabilities(p_78748_1_.capabilities);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpectator() {
/*  93 */     return (this.currentGameType == WorldSettings.GameType.SPECTATOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGameType(WorldSettings.GameType p_78746_1_) {
/* 101 */     this.currentGameType = p_78746_1_;
/* 102 */     this.currentGameType.configurePlayerCapabilities(this.mc.thePlayer.capabilities);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flipPlayer(EntityPlayer playerIn) {
/* 110 */     playerIn.rotationYaw = -180.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldDrawHUD() {
/* 115 */     return this.currentGameType.isSurvivalOrAdventure();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onPlayerDestroyBlock(BlockPos pos, EnumFacing side) {
/* 126 */     if (this.currentGameType.isAdventure()) {
/*     */       
/* 128 */       if (this.currentGameType == WorldSettings.GameType.SPECTATOR)
/*     */       {
/* 130 */         return false;
/*     */       }
/*     */       
/* 133 */       if (!this.mc.thePlayer.isAllowEdit()) {
/*     */         
/* 135 */         Block block = this.mc.theWorld.getBlockState(pos).getBlock();
/* 136 */         ItemStack itemstack = this.mc.thePlayer.getCurrentEquippedItem();
/*     */         
/* 138 */         if (itemstack == null)
/*     */         {
/* 140 */           return false;
/*     */         }
/*     */         
/* 143 */         if (!itemstack.canDestroy(block))
/*     */         {
/* 145 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     if (this.currentGameType.isCreative() && this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword)
/*     */     {
/* 152 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 156 */     World world = this.mc.theWorld;
/* 157 */     IBlockState iblockstate = world.getBlockState(pos);
/* 158 */     Block block1 = iblockstate.getBlock();
/*     */     
/* 160 */     if (block1.getMaterial() == Material.air)
/*     */     {
/* 162 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 166 */     world.playAuxSFX(2001, pos, Block.getStateId(iblockstate));
/* 167 */     boolean flag = world.setBlockToAir(pos);
/*     */     
/* 169 */     if (flag)
/*     */     {
/* 171 */       block1.onBlockDestroyedByPlayer(world, pos, iblockstate);
/*     */     }
/*     */     
/* 174 */     this.currentBlock = new BlockPos(this.currentBlock.getX(), -1, this.currentBlock.getZ());
/*     */     
/* 176 */     if (!this.currentGameType.isCreative()) {
/*     */       
/* 178 */       ItemStack itemstack1 = this.mc.thePlayer.getCurrentEquippedItem();
/*     */       
/* 180 */       if (itemstack1 != null) {
/*     */         
/* 182 */         itemstack1.onBlockDestroyed(world, block1, pos, (EntityPlayer)this.mc.thePlayer);
/*     */         
/* 184 */         if (itemstack1.stackSize == 0)
/*     */         {
/* 186 */           this.mc.thePlayer.destroyCurrentEquippedItem();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 191 */     return flag;
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
/*     */   public boolean clickBlock(BlockPos loc, EnumFacing face) {
/* 204 */     if (this.currentGameType.isAdventure()) {
/*     */       
/* 206 */       if (this.currentGameType == WorldSettings.GameType.SPECTATOR)
/*     */       {
/* 208 */         return false;
/*     */       }
/*     */       
/* 211 */       if (!this.mc.thePlayer.isAllowEdit()) {
/*     */         
/* 213 */         Block block = this.mc.theWorld.getBlockState(loc).getBlock();
/* 214 */         ItemStack itemstack = this.mc.thePlayer.getCurrentEquippedItem();
/*     */         
/* 216 */         if (itemstack == null)
/*     */         {
/* 218 */           return false;
/*     */         }
/*     */         
/* 221 */         if (!itemstack.canDestroy(block))
/*     */         {
/* 223 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 228 */     if (!this.mc.theWorld.getWorldBorder().contains(loc))
/*     */     {
/* 230 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 234 */     if (this.currentGameType.isCreative()) {
/*     */       
/* 236 */       this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, loc, face));
/* 237 */       clickBlockCreative(this.mc, this, loc, face);
/* 238 */       this.blockHitDelay = 5;
/*     */     }
/* 240 */     else if (!this.isHittingBlock || !isHittingPosition(loc)) {
/*     */       
/* 242 */       if (this.isHittingBlock)
/*     */       {
/* 244 */         this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.currentBlock, face));
/*     */       }
/*     */       
/* 247 */       this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, loc, face));
/* 248 */       Block block1 = this.mc.theWorld.getBlockState(loc).getBlock();
/* 249 */       boolean flag = (block1.getMaterial() != Material.air);
/*     */       
/* 251 */       if (flag && this.curBlockDamageMP == 0.0F)
/*     */       {
/* 253 */         block1.onBlockClicked(this.mc.theWorld, loc, (EntityPlayer)this.mc.thePlayer);
/*     */       }
/*     */       
/* 256 */       if (flag && block1.getPlayerRelativeBlockHardness((EntityPlayer)this.mc.thePlayer, this.mc.thePlayer.worldObj, loc) >= 1.0F) {
/*     */         
/* 258 */         onPlayerDestroyBlock(loc, face);
/*     */       }
/*     */       else {
/*     */         
/* 262 */         this.isHittingBlock = true;
/* 263 */         this.currentBlock = loc;
/* 264 */         this.currentItemHittingBlock = this.mc.thePlayer.getHeldItem();
/* 265 */         this.curBlockDamageMP = 0.0F;
/* 266 */         this.stepSoundTickCounter = 0.0F;
/* 267 */         this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, (int)(this.curBlockDamageMP * 10.0F) - 1);
/*     */       } 
/*     */     } 
/*     */     
/* 271 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetBlockRemoving() {
/* 280 */     if (this.isHittingBlock) {
/*     */       
/* 282 */       this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.currentBlock, EnumFacing.DOWN));
/* 283 */       this.isHittingBlock = false;
/* 284 */       this.curBlockDamageMP = 0.0F;
/* 285 */       this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing) {
/* 291 */     syncCurrentPlayItem();
/*     */     
/* 293 */     if (this.blockHitDelay > 0) {
/*     */       
/* 295 */       this.blockHitDelay--;
/* 296 */       return true;
/*     */     } 
/* 298 */     if (this.currentGameType.isCreative() && this.mc.theWorld.getWorldBorder().contains(posBlock)) {
/*     */       
/* 300 */       this.blockHitDelay = 5;
/* 301 */       this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, posBlock, directionFacing));
/* 302 */       clickBlockCreative(this.mc, this, posBlock, directionFacing);
/* 303 */       return true;
/*     */     } 
/* 305 */     if (isHittingPosition(posBlock)) {
/*     */       
/* 307 */       Block block = this.mc.theWorld.getBlockState(posBlock).getBlock();
/*     */       
/* 309 */       if (block.getMaterial() == Material.air) {
/*     */         
/* 311 */         this.isHittingBlock = false;
/* 312 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 316 */       this.curBlockDamageMP += block.getPlayerRelativeBlockHardness((EntityPlayer)this.mc.thePlayer, this.mc.thePlayer.worldObj, posBlock);
/*     */       
/* 318 */       if (this.stepSoundTickCounter % 4.0F == 0.0F)
/*     */       {
/* 320 */         this.mc.getSoundHandler().playSound((ISound)new PositionedSoundRecord(new ResourceLocation(block.stepSound.getStepSound()), (block.stepSound.getVolume() + 1.0F) / 8.0F, block.stepSound.getFrequency() * 0.5F, posBlock.getX() + 0.5F, posBlock.getY() + 0.5F, posBlock.getZ() + 0.5F));
/*     */       }
/*     */       
/* 323 */       this.stepSoundTickCounter++;
/*     */       
/* 325 */       if (this.curBlockDamageMP >= 1.0F) {
/*     */         
/* 327 */         this.isHittingBlock = false;
/* 328 */         this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, posBlock, directionFacing));
/* 329 */         onPlayerDestroyBlock(posBlock, directionFacing);
/* 330 */         this.curBlockDamageMP = 0.0F;
/* 331 */         this.stepSoundTickCounter = 0.0F;
/* 332 */         this.blockHitDelay = 5;
/*     */       } 
/*     */       
/* 335 */       this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.currentBlock, (int)(this.curBlockDamageMP * 10.0F) - 1);
/* 336 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 341 */     return clickBlock(posBlock, directionFacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBlockReachDistance() {
/* 350 */     return this.currentGameType.isCreative() ? 5.0F : 4.5F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateController() {
/* 355 */     syncCurrentPlayItem();
/*     */     
/* 357 */     if (this.netClientHandler.getNetworkManager().isChannelOpen()) {
/*     */       
/* 359 */       this.netClientHandler.getNetworkManager().processReceivedPackets();
/*     */     }
/*     */     else {
/*     */       
/* 363 */       this.netClientHandler.getNetworkManager().checkDisconnected();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isHittingPosition(BlockPos pos) {
/* 369 */     ItemStack itemstack = this.mc.thePlayer.getHeldItem();
/* 370 */     boolean flag = (this.currentItemHittingBlock == null && itemstack == null);
/*     */     
/* 372 */     if (this.currentItemHittingBlock != null && itemstack != null)
/*     */     {
/* 374 */       flag = (itemstack.getItem() == this.currentItemHittingBlock.getItem() && ItemStack.areItemStackTagsEqual(itemstack, this.currentItemHittingBlock) && (itemstack.isItemStackDamageable() || itemstack.getMetadata() == this.currentItemHittingBlock.getMetadata()));
/*     */     }
/*     */     
/* 377 */     return (pos.equals(this.currentBlock) && flag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void syncCurrentPlayItem() {
/* 385 */     int i = this.mc.thePlayer.inventory.currentItem;
/*     */     
/* 387 */     if (i != this.currentPlayerItem) {
/*     */       
/* 389 */       this.currentPlayerItem = i;
/* 390 */       this.netClientHandler.addToSendQueue((Packet)new C09PacketHeldItemChange(this.currentPlayerItem));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerRightClick(EntityPlayerSP player, WorldClient worldIn, ItemStack heldStack, BlockPos hitPos, EnumFacing side, Vec3 hitVec) {
/* 396 */     syncCurrentPlayItem();
/* 397 */     float f = (float)(hitVec.xCoord - hitPos.getX());
/* 398 */     float f1 = (float)(hitVec.yCoord - hitPos.getY());
/* 399 */     float f2 = (float)(hitVec.zCoord - hitPos.getZ());
/* 400 */     boolean flag = false;
/*     */     
/* 402 */     if (!this.mc.theWorld.getWorldBorder().contains(hitPos))
/*     */     {
/* 404 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 408 */     if (this.currentGameType != WorldSettings.GameType.SPECTATOR) {
/*     */       
/* 410 */       IBlockState iblockstate = worldIn.getBlockState(hitPos);
/*     */       
/* 412 */       if ((!player.isSneaking() || player.getHeldItem() == null) && iblockstate.getBlock().onBlockActivated(worldIn, hitPos, iblockstate, (EntityPlayer)player, side, f, f1, f2))
/*     */       {
/* 414 */         flag = true;
/*     */       }
/*     */       
/* 417 */       if (!flag && heldStack != null && heldStack.getItem() instanceof ItemBlock) {
/*     */         
/* 419 */         ItemBlock itemblock = (ItemBlock)heldStack.getItem();
/*     */         
/* 421 */         if (!itemblock.canPlaceBlockOnSide(worldIn, hitPos, side, (EntityPlayer)player, heldStack))
/*     */         {
/* 423 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 428 */     this.netClientHandler.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(hitPos, side.getIndex(), player.inventory.getCurrentItem(), f, f1, f2));
/*     */     
/* 430 */     if (!flag && this.currentGameType != WorldSettings.GameType.SPECTATOR) {
/*     */       
/* 432 */       if (heldStack == null)
/*     */       {
/* 434 */         return false;
/*     */       }
/* 436 */       if (this.currentGameType.isCreative()) {
/*     */         
/* 438 */         int i = heldStack.getMetadata();
/* 439 */         int j = heldStack.stackSize;
/* 440 */         boolean flag1 = heldStack.onItemUse((EntityPlayer)player, worldIn, hitPos, side, f, f1, f2);
/* 441 */         heldStack.setItemDamage(i);
/* 442 */         heldStack.stackSize = j;
/* 443 */         return flag1;
/*     */       } 
/*     */ 
/*     */       
/* 447 */       return heldStack.onItemUse((EntityPlayer)player, worldIn, hitPos, side, f, f1, f2);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 452 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendUseItem(EntityPlayer playerIn, World worldIn, ItemStack itemStackIn) {
/* 462 */     if (this.currentGameType == WorldSettings.GameType.SPECTATOR)
/*     */     {
/* 464 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 468 */     syncCurrentPlayItem();
/* 469 */     this.netClientHandler.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(playerIn.inventory.getCurrentItem()));
/* 470 */     int i = itemStackIn.stackSize;
/* 471 */     ItemStack itemstack = itemStackIn.useItemRightClick(worldIn, playerIn);
/*     */     
/* 473 */     if (itemstack != itemStackIn || (itemstack != null && itemstack.stackSize != i)) {
/*     */       
/* 475 */       playerIn.inventory.mainInventory[playerIn.inventory.currentItem] = itemstack;
/*     */       
/* 477 */       if (itemstack.stackSize == 0)
/*     */       {
/* 479 */         playerIn.inventory.mainInventory[playerIn.inventory.currentItem] = null;
/*     */       }
/*     */       
/* 482 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 486 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityPlayerSP func_178892_a(World worldIn, StatFileWriter p_178892_2_) {
/* 493 */     return new EntityPlayerSP(this.mc, worldIn, this.netClientHandler, p_178892_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackEntity(EntityPlayer playerIn, Entity targetEntity) {
/* 501 */     AttackEvent event = new AttackEvent(targetEntity);
/* 502 */     if (targetEntity == null || event.call().isCanceled()) {
/*     */       return;
/*     */     }
/* 505 */     syncCurrentPlayItem();
/* 506 */     this.netClientHandler.addToSendQueue((Packet)new C02PacketUseEntity(targetEntity, C02PacketUseEntity.Action.ATTACK));
/*     */     
/* 508 */     if (this.currentGameType != WorldSettings.GameType.SPECTATOR)
/*     */     {
/* 510 */       playerIn.attackTargetEntityWithCurrentItem(targetEntity);
/*     */     }
/*     */     
/* 513 */     AttackUtil.inCombat = true;
/* 514 */     AttackUtil.combatTarget = targetEntity;
/* 515 */     AttackUtil.combatTimer.reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactWithEntitySendPacket(EntityPlayer playerIn, Entity targetEntity) {
/* 523 */     syncCurrentPlayItem();
/* 524 */     this.netClientHandler.addToSendQueue((Packet)new C02PacketUseEntity(targetEntity, C02PacketUseEntity.Action.INTERACT));
/* 525 */     return (this.currentGameType != WorldSettings.GameType.SPECTATOR && playerIn.interactWith(targetEntity));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_178894_a(EntityPlayer p_178894_1_, Entity p_178894_2_, MovingObjectPosition p_178894_3_) {
/* 530 */     syncCurrentPlayItem();
/* 531 */     Vec3 vec3 = new Vec3(p_178894_3_.hitVec.xCoord - p_178894_2_.posX, p_178894_3_.hitVec.yCoord - p_178894_2_.posY, p_178894_3_.hitVec.zCoord - p_178894_2_.posZ);
/* 532 */     this.netClientHandler.addToSendQueue((Packet)new C02PacketUseEntity(p_178894_2_, vec3));
/* 533 */     return (this.currentGameType != WorldSettings.GameType.SPECTATOR && p_178894_2_.interactAt(p_178894_1_, vec3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack windowClick(int windowId, int slotId, int mouseButtonClicked, int mode, EntityPlayer playerIn) {
/* 541 */     short short1 = playerIn.openContainer.getNextTransactionID(playerIn.inventory);
/* 542 */     ItemStack itemstack = playerIn.openContainer.slotClick(slotId, mouseButtonClicked, mode, playerIn);
/* 543 */     this.netClientHandler.addToSendQueue((Packet)new C0EPacketClickWindow(windowId, slotId, mouseButtonClicked, mode, itemstack, short1));
/* 544 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendEnchantPacket(int p_78756_1_, int p_78756_2_) {
/* 553 */     this.netClientHandler.addToSendQueue((Packet)new C11PacketEnchantItem(p_78756_1_, p_78756_2_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendSlotPacket(ItemStack itemStackIn, int slotId) {
/* 561 */     if (this.currentGameType.isCreative())
/*     */     {
/* 563 */       this.netClientHandler.addToSendQueue((Packet)new C10PacketCreativeInventoryAction(slotId, itemStackIn));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPacketDropItem(ItemStack itemStackIn) {
/* 572 */     if (this.currentGameType.isCreative() && itemStackIn != null)
/*     */     {
/* 574 */       this.netClientHandler.addToSendQueue((Packet)new C10PacketCreativeInventoryAction(-1, itemStackIn));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onStoppedUsingItem(EntityPlayer playerIn) {
/* 580 */     syncCurrentPlayItem();
/* 581 */     this.netClientHandler.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
/* 582 */     playerIn.stopUsingItem();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean gameIsSurvivalOrAdventure() {
/* 587 */     return this.currentGameType.isSurvivalOrAdventure();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotCreative() {
/* 595 */     return !this.currentGameType.isCreative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInCreativeMode() {
/* 603 */     return this.currentGameType.isCreative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean extendedReach() {
/* 611 */     return this.currentGameType.isCreative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRidingHorse() {
/* 619 */     return (this.mc.thePlayer.isRiding() && this.mc.thePlayer.ridingEntity instanceof net.minecraft.entity.passive.EntityHorse);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSpectatorMode() {
/* 624 */     return (this.currentGameType == WorldSettings.GameType.SPECTATOR);
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldSettings.GameType getCurrentGameType() {
/* 629 */     return this.currentGameType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_181040_m() {
/* 634 */     return this.isHittingBlock;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\multiplayer\PlayerControllerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockChest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerChest;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryLargeChest;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ 
/*     */ public class TileEntityChest extends TileEntityLockable implements ITickable, IInventory {
/*  21 */   private ItemStack[] chestContents = new ItemStack[27];
/*     */ 
/*     */   
/*     */   public boolean adjacentChestChecked;
/*     */ 
/*     */   
/*     */   public TileEntityChest adjacentChestZNeg;
/*     */ 
/*     */   
/*     */   public TileEntityChest adjacentChestXPos;
/*     */ 
/*     */   
/*     */   public TileEntityChest adjacentChestXNeg;
/*     */ 
/*     */   
/*     */   public TileEntityChest adjacentChestZPos;
/*     */ 
/*     */   
/*     */   public float lidAngle;
/*     */ 
/*     */   
/*     */   public float prevLidAngle;
/*     */   
/*     */   public int numPlayersUsing;
/*     */   
/*     */   private int ticksSinceSync;
/*     */   
/*     */   private int cachedChestType;
/*     */   
/*     */   private String customName;
/*     */ 
/*     */   
/*     */   public TileEntityChest() {
/*  54 */     this.cachedChestType = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntityChest(int chestType) {
/*  59 */     this.cachedChestType = chestType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  67 */     return 27;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  77 */     return this.chestContents[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  88 */     if (this.chestContents[index] != null) {
/*     */       
/*  90 */       if ((this.chestContents[index]).stackSize <= count) {
/*     */         
/*  92 */         ItemStack itemstack1 = this.chestContents[index];
/*  93 */         this.chestContents[index] = null;
/*  94 */         markDirty();
/*  95 */         return itemstack1;
/*     */       } 
/*     */ 
/*     */       
/*  99 */       ItemStack itemstack = this.chestContents[index].splitStack(count);
/*     */       
/* 101 */       if ((this.chestContents[index]).stackSize == 0)
/*     */       {
/* 103 */         this.chestContents[index] = null;
/*     */       }
/*     */       
/* 106 */       markDirty();
/* 107 */       return itemstack;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 112 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlotOnClosing(int index) {
/* 123 */     if (this.chestContents[index] != null) {
/*     */       
/* 125 */       ItemStack itemstack = this.chestContents[index];
/* 126 */       this.chestContents[index] = null;
/* 127 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 131 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 140 */     this.chestContents[index] = stack;
/*     */     
/* 142 */     if (stack != null && stack.stackSize > getInventoryStackLimit())
/*     */     {
/* 144 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */     
/* 147 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandSenderName() {
/* 155 */     return hasCustomName() ? this.customName : "container.chest";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 163 */     return (this.customName != null && this.customName.length() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCustomName(String name) {
/* 168 */     this.customName = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 173 */     super.readFromNBT(compound);
/* 174 */     NBTTagList nbttaglist = compound.getTagList("Items", 10);
/* 175 */     this.chestContents = new ItemStack[getSizeInventory()];
/*     */     
/* 177 */     if (compound.hasKey("CustomName", 8))
/*     */     {
/* 179 */       this.customName = compound.getString("CustomName");
/*     */     }
/*     */     
/* 182 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/* 184 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 185 */       int j = nbttagcompound.getByte("Slot") & 0xFF;
/*     */       
/* 187 */       if (j >= 0 && j < this.chestContents.length)
/*     */       {
/* 189 */         this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/* 196 */     super.writeToNBT(compound);
/* 197 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 199 */     for (int i = 0; i < this.chestContents.length; i++) {
/*     */       
/* 201 */       if (this.chestContents[i] != null) {
/*     */         
/* 203 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 204 */         nbttagcompound.setByte("Slot", (byte)i);
/* 205 */         this.chestContents[i].writeToNBT(nbttagcompound);
/* 206 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 210 */     compound.setTag("Items", (NBTBase)nbttaglist);
/*     */     
/* 212 */     if (hasCustomName())
/*     */     {
/* 214 */       compound.setString("CustomName", this.customName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 223 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 231 */     return (this.worldObj.getTileEntity(this.pos) != this) ? false : ((player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateContainingBlockInfo() {
/* 236 */     super.updateContainingBlockInfo();
/* 237 */     this.adjacentChestChecked = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_174910_a(TileEntityChest chestTe, EnumFacing side) {
/* 243 */     if (chestTe.isInvalid()) {
/*     */       
/* 245 */       this.adjacentChestChecked = false;
/*     */     }
/* 247 */     else if (this.adjacentChestChecked) {
/*     */       
/* 249 */       switch (side) {
/*     */         
/*     */         case NORTH:
/* 252 */           if (this.adjacentChestZNeg != chestTe)
/*     */           {
/* 254 */             this.adjacentChestChecked = false;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case SOUTH:
/* 260 */           if (this.adjacentChestZPos != chestTe)
/*     */           {
/* 262 */             this.adjacentChestChecked = false;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case EAST:
/* 268 */           if (this.adjacentChestXPos != chestTe)
/*     */           {
/* 270 */             this.adjacentChestChecked = false;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case WEST:
/* 276 */           if (this.adjacentChestXNeg != chestTe)
/*     */           {
/* 278 */             this.adjacentChestChecked = false;
/*     */           }
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkForAdjacentChests() {
/* 289 */     if (!this.adjacentChestChecked) {
/*     */       
/* 291 */       this.adjacentChestChecked = true;
/* 292 */       this.adjacentChestXNeg = getAdjacentChest(EnumFacing.WEST);
/* 293 */       this.adjacentChestXPos = getAdjacentChest(EnumFacing.EAST);
/* 294 */       this.adjacentChestZNeg = getAdjacentChest(EnumFacing.NORTH);
/* 295 */       this.adjacentChestZPos = getAdjacentChest(EnumFacing.SOUTH);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected TileEntityChest getAdjacentChest(EnumFacing side) {
/* 301 */     BlockPos blockpos = this.pos.offset(side);
/*     */     
/* 303 */     if (isChestAt(blockpos)) {
/*     */       
/* 305 */       TileEntity tileentity = this.worldObj.getTileEntity(blockpos);
/*     */       
/* 307 */       if (tileentity instanceof TileEntityChest) {
/*     */         
/* 309 */         TileEntityChest tileentitychest = (TileEntityChest)tileentity;
/* 310 */         tileentitychest.func_174910_a(this, side.getOpposite());
/* 311 */         return tileentitychest;
/*     */       } 
/*     */     } 
/*     */     
/* 315 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isChestAt(BlockPos posIn) {
/* 320 */     if (this.worldObj == null)
/*     */     {
/* 322 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 326 */     Block block = this.worldObj.getBlockState(posIn).getBlock();
/* 327 */     return (block instanceof BlockChest && ((BlockChest)block).chestType == getChestType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 336 */     checkForAdjacentChests();
/* 337 */     int i = this.pos.getX();
/* 338 */     int j = this.pos.getY();
/* 339 */     int k = this.pos.getZ();
/* 340 */     this.ticksSinceSync++;
/*     */     
/* 342 */     if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + i + j + k) % 200 == 0) {
/*     */       
/* 344 */       this.numPlayersUsing = 0;
/* 345 */       float f = 5.0F;
/*     */       
/* 347 */       for (EntityPlayer entityplayer : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB((i - f), (j - f), (k - f), ((i + 1) + f), ((j + 1) + f), ((k + 1) + f)))) {
/*     */         
/* 349 */         if (entityplayer.openContainer instanceof ContainerChest) {
/*     */           
/* 351 */           IInventory iinventory = ((ContainerChest)entityplayer.openContainer).getLowerChestInventory();
/*     */           
/* 353 */           if (iinventory == this || (iinventory instanceof InventoryLargeChest && ((InventoryLargeChest)iinventory).isPartOfLargeChest(this)))
/*     */           {
/* 355 */             this.numPlayersUsing++;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 361 */     this.prevLidAngle = this.lidAngle;
/* 362 */     float f1 = 0.1F;
/*     */     
/* 364 */     if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
/*     */       
/* 366 */       double d1 = i + 0.5D;
/* 367 */       double d2 = k + 0.5D;
/*     */       
/* 369 */       if (this.adjacentChestZPos != null)
/*     */       {
/* 371 */         d2 += 0.5D;
/*     */       }
/*     */       
/* 374 */       if (this.adjacentChestXPos != null)
/*     */       {
/* 376 */         d1 += 0.5D;
/*     */       }
/*     */       
/* 379 */       this.worldObj.playSoundEffect(d1, j + 0.5D, d2, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
/*     */     } 
/*     */     
/* 382 */     if ((this.numPlayersUsing == 0 && this.lidAngle > 0.0F) || (this.numPlayersUsing > 0 && this.lidAngle < 1.0F)) {
/*     */       
/* 384 */       float f2 = this.lidAngle;
/*     */       
/* 386 */       if (this.numPlayersUsing > 0) {
/*     */         
/* 388 */         this.lidAngle += f1;
/*     */       }
/*     */       else {
/*     */         
/* 392 */         this.lidAngle -= f1;
/*     */       } 
/*     */       
/* 395 */       if (this.lidAngle > 1.0F)
/*     */       {
/* 397 */         this.lidAngle = 1.0F;
/*     */       }
/*     */       
/* 400 */       float f3 = 0.5F;
/*     */       
/* 402 */       if (this.lidAngle < f3 && f2 >= f3 && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
/*     */         
/* 404 */         double d3 = i + 0.5D;
/* 405 */         double d0 = k + 0.5D;
/*     */         
/* 407 */         if (this.adjacentChestZPos != null)
/*     */         {
/* 409 */           d0 += 0.5D;
/*     */         }
/*     */         
/* 412 */         if (this.adjacentChestXPos != null)
/*     */         {
/* 414 */           d3 += 0.5D;
/*     */         }
/*     */         
/* 417 */         this.worldObj.playSoundEffect(d3, j + 0.5D, d0, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
/*     */       } 
/*     */       
/* 420 */       if (this.lidAngle < 0.0F)
/*     */       {
/* 422 */         this.lidAngle = 0.0F;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean receiveClientEvent(int id, int type) {
/* 429 */     if (id == 1) {
/*     */       
/* 431 */       this.numPlayersUsing = type;
/* 432 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 436 */     return super.receiveClientEvent(id, type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void openInventory(EntityPlayer player) {
/* 442 */     if (!player.isSpectator()) {
/*     */       
/* 444 */       if (this.numPlayersUsing < 0)
/*     */       {
/* 446 */         this.numPlayersUsing = 0;
/*     */       }
/*     */       
/* 449 */       this.numPlayersUsing++;
/* 450 */       this.worldObj.addBlockEvent(this.pos, getBlockType(), 1, this.numPlayersUsing);
/* 451 */       this.worldObj.notifyNeighborsOfStateChange(this.pos, getBlockType());
/* 452 */       this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), getBlockType());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {
/* 458 */     if (!player.isSpectator() && getBlockType() instanceof BlockChest) {
/*     */       
/* 460 */       this.numPlayersUsing--;
/* 461 */       this.worldObj.addBlockEvent(this.pos, getBlockType(), 1, this.numPlayersUsing);
/* 462 */       this.worldObj.notifyNeighborsOfStateChange(this.pos, getBlockType());
/* 463 */       this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), getBlockType());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValidForSlot(int index, ItemStack stack) {
/* 472 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidate() {
/* 480 */     super.invalidate();
/* 481 */     updateContainingBlockInfo();
/* 482 */     checkForAdjacentChests();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChestType() {
/* 487 */     if (this.cachedChestType == -1) {
/*     */       
/* 489 */       if (this.worldObj == null || !(getBlockType() instanceof BlockChest))
/*     */       {
/* 491 */         return 0;
/*     */       }
/*     */       
/* 494 */       this.cachedChestType = ((BlockChest)getBlockType()).chestType;
/*     */     } 
/*     */     
/* 497 */     return this.cachedChestType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 502 */     return "minecraft:chest";
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 507 */     return (Container)new ContainerChest((IInventory)playerInventory, this, playerIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 512 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 521 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 526 */     for (int i = 0; i < this.chestContents.length; i++)
/*     */     {
/* 528 */       this.chestContents[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\tileentity\TileEntityChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
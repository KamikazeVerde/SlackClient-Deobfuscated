/*     */ package net.minecraft.tileentity;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockChest;
/*     */ import net.minecraft.block.BlockHopper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerHopper;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.ILockableContainer;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class TileEntityHopper extends TileEntityLockable implements IHopper, ITickable {
/*  28 */   private ItemStack[] inventory = new ItemStack[5];
/*     */   private String customName;
/*  30 */   private int transferCooldown = -1;
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  34 */     super.readFromNBT(compound);
/*  35 */     NBTTagList nbttaglist = compound.getTagList("Items", 10);
/*  36 */     this.inventory = new ItemStack[getSizeInventory()];
/*     */     
/*  38 */     if (compound.hasKey("CustomName", 8))
/*     */     {
/*  40 */       this.customName = compound.getString("CustomName");
/*     */     }
/*     */     
/*  43 */     this.transferCooldown = compound.getInteger("TransferCooldown");
/*     */     
/*  45 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/*  47 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  48 */       int j = nbttagcompound.getByte("Slot");
/*     */       
/*  50 */       if (j >= 0 && j < this.inventory.length)
/*     */       {
/*  52 */         this.inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/*  59 */     super.writeToNBT(compound);
/*  60 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/*  62 */     for (int i = 0; i < this.inventory.length; i++) {
/*     */       
/*  64 */       if (this.inventory[i] != null) {
/*     */         
/*  66 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  67 */         nbttagcompound.setByte("Slot", (byte)i);
/*  68 */         this.inventory[i].writeToNBT(nbttagcompound);
/*  69 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/*  73 */     compound.setTag("Items", (NBTBase)nbttaglist);
/*  74 */     compound.setInteger("TransferCooldown", this.transferCooldown);
/*     */     
/*  76 */     if (hasCustomName())
/*     */     {
/*  78 */       compound.setString("CustomName", this.customName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/*  88 */     super.markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  96 */     return this.inventory.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/* 106 */     return this.inventory[index];
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
/* 117 */     if (this.inventory[index] != null) {
/*     */       
/* 119 */       if ((this.inventory[index]).stackSize <= count) {
/*     */         
/* 121 */         ItemStack itemstack1 = this.inventory[index];
/* 122 */         this.inventory[index] = null;
/* 123 */         return itemstack1;
/*     */       } 
/*     */ 
/*     */       
/* 127 */       ItemStack itemstack = this.inventory[index].splitStack(count);
/*     */       
/* 129 */       if ((this.inventory[index]).stackSize == 0)
/*     */       {
/* 131 */         this.inventory[index] = null;
/*     */       }
/*     */       
/* 134 */       return itemstack;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 139 */     return null;
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
/* 150 */     if (this.inventory[index] != null) {
/*     */       
/* 152 */       ItemStack itemstack = this.inventory[index];
/* 153 */       this.inventory[index] = null;
/* 154 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 158 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 167 */     this.inventory[index] = stack;
/*     */     
/* 169 */     if (stack != null && stack.stackSize > getInventoryStackLimit())
/*     */     {
/* 171 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandSenderName() {
/* 180 */     return hasCustomName() ? this.customName : "container.hopper";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 188 */     return (this.customName != null && this.customName.length() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCustomName(String customNameIn) {
/* 193 */     this.customName = customNameIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 201 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 209 */     return (this.worldObj.getTileEntity(this.pos) != this) ? false : ((player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void openInventory(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValidForSlot(int index, ItemStack stack) {
/* 225 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 233 */     if (this.worldObj != null && !this.worldObj.isRemote) {
/*     */       
/* 235 */       this.transferCooldown--;
/*     */       
/* 237 */       if (!isOnTransferCooldown()) {
/*     */         
/* 239 */         setTransferCooldown(0);
/* 240 */         updateHopper();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean updateHopper() {
/* 247 */     if (this.worldObj != null && !this.worldObj.isRemote) {
/*     */       
/* 249 */       if (!isOnTransferCooldown() && BlockHopper.isEnabled(getBlockMetadata())) {
/*     */         
/* 251 */         boolean flag = false;
/*     */         
/* 253 */         if (!isEmpty())
/*     */         {
/* 255 */           flag = transferItemsOut();
/*     */         }
/*     */         
/* 258 */         if (!isFull())
/*     */         {
/* 260 */           flag = (captureDroppedItems(this) || flag);
/*     */         }
/*     */         
/* 263 */         if (flag) {
/*     */           
/* 265 */           setTransferCooldown(8);
/* 266 */           markDirty();
/* 267 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 271 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 275 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isEmpty() {
/* 281 */     for (ItemStack itemstack : this.inventory) {
/*     */       
/* 283 */       if (itemstack != null)
/*     */       {
/* 285 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 289 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isFull() {
/* 294 */     for (ItemStack itemstack : this.inventory) {
/*     */       
/* 296 */       if (itemstack == null || itemstack.stackSize != itemstack.getMaxStackSize())
/*     */       {
/* 298 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 302 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean transferItemsOut() {
/* 307 */     IInventory iinventory = getInventoryForHopperTransfer();
/*     */     
/* 309 */     if (iinventory == null)
/*     */     {
/* 311 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 315 */     EnumFacing enumfacing = BlockHopper.getFacing(getBlockMetadata()).getOpposite();
/*     */     
/* 317 */     if (isInventoryFull(iinventory, enumfacing))
/*     */     {
/* 319 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 323 */     for (int i = 0; i < getSizeInventory(); i++) {
/*     */       
/* 325 */       if (getStackInSlot(i) != null) {
/*     */         
/* 327 */         ItemStack itemstack = getStackInSlot(i).copy();
/* 328 */         ItemStack itemstack1 = putStackInInventoryAllSlots(iinventory, decrStackSize(i, 1), enumfacing);
/*     */         
/* 330 */         if (itemstack1 == null || itemstack1.stackSize == 0) {
/*     */           
/* 332 */           iinventory.markDirty();
/* 333 */           return true;
/*     */         } 
/*     */         
/* 336 */         setInventorySlotContents(i, itemstack);
/*     */       } 
/*     */     } 
/*     */     
/* 340 */     return false;
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
/*     */   private boolean isInventoryFull(IInventory inventoryIn, EnumFacing side) {
/* 353 */     if (inventoryIn instanceof ISidedInventory) {
/*     */       
/* 355 */       ISidedInventory isidedinventory = (ISidedInventory)inventoryIn;
/* 356 */       int[] aint = isidedinventory.getSlotsForFace(side);
/*     */       
/* 358 */       for (int k = 0; k < aint.length; k++)
/*     */       {
/* 360 */         ItemStack itemstack1 = isidedinventory.getStackInSlot(aint[k]);
/*     */         
/* 362 */         if (itemstack1 == null || itemstack1.stackSize != itemstack1.getMaxStackSize())
/*     */         {
/* 364 */           return false;
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 370 */       int i = inventoryIn.getSizeInventory();
/*     */       
/* 372 */       for (int j = 0; j < i; j++) {
/*     */         
/* 374 */         ItemStack itemstack = inventoryIn.getStackInSlot(j);
/*     */         
/* 376 */         if (itemstack == null || itemstack.stackSize != itemstack.getMaxStackSize())
/*     */         {
/* 378 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 383 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isInventoryEmpty(IInventory inventoryIn, EnumFacing side) {
/* 394 */     if (inventoryIn instanceof ISidedInventory) {
/*     */       
/* 396 */       ISidedInventory isidedinventory = (ISidedInventory)inventoryIn;
/* 397 */       int[] aint = isidedinventory.getSlotsForFace(side);
/*     */       
/* 399 */       for (int i = 0; i < aint.length; i++)
/*     */       {
/* 401 */         if (isidedinventory.getStackInSlot(aint[i]) != null)
/*     */         {
/* 403 */           return false;
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 409 */       int j = inventoryIn.getSizeInventory();
/*     */       
/* 411 */       for (int k = 0; k < j; k++) {
/*     */         
/* 413 */         if (inventoryIn.getStackInSlot(k) != null)
/*     */         {
/* 415 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 420 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean captureDroppedItems(IHopper p_145891_0_) {
/* 425 */     IInventory iinventory = getHopperInventory(p_145891_0_);
/*     */     
/* 427 */     if (iinventory != null) {
/*     */       
/* 429 */       EnumFacing enumfacing = EnumFacing.DOWN;
/*     */       
/* 431 */       if (isInventoryEmpty(iinventory, enumfacing))
/*     */       {
/* 433 */         return false;
/*     */       }
/*     */       
/* 436 */       if (iinventory instanceof ISidedInventory) {
/*     */         
/* 438 */         ISidedInventory isidedinventory = (ISidedInventory)iinventory;
/* 439 */         int[] aint = isidedinventory.getSlotsForFace(enumfacing);
/*     */         
/* 441 */         for (int i = 0; i < aint.length; i++)
/*     */         {
/* 443 */           if (pullItemFromSlot(p_145891_0_, iinventory, aint[i], enumfacing))
/*     */           {
/* 445 */             return true;
/*     */           }
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 451 */         int j = iinventory.getSizeInventory();
/*     */         
/* 453 */         for (int k = 0; k < j; k++)
/*     */         {
/* 455 */           if (pullItemFromSlot(p_145891_0_, iinventory, k, enumfacing))
/*     */           {
/* 457 */             return true;
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 464 */       for (EntityItem entityitem : func_181556_a(p_145891_0_.getWorld(), p_145891_0_.getXPos(), p_145891_0_.getYPos() + 1.0D, p_145891_0_.getZPos())) {
/*     */         
/* 466 */         if (putDropInInventoryAllSlots(p_145891_0_, entityitem))
/*     */         {
/* 468 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 473 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean pullItemFromSlot(IHopper hopper, IInventory inventoryIn, int index, EnumFacing direction) {
/* 484 */     ItemStack itemstack = inventoryIn.getStackInSlot(index);
/*     */     
/* 486 */     if (itemstack != null && canExtractItemFromSlot(inventoryIn, itemstack, index, direction)) {
/*     */       
/* 488 */       ItemStack itemstack1 = itemstack.copy();
/* 489 */       ItemStack itemstack2 = putStackInInventoryAllSlots(hopper, inventoryIn.decrStackSize(index, 1), (EnumFacing)null);
/*     */       
/* 491 */       if (itemstack2 == null || itemstack2.stackSize == 0) {
/*     */         
/* 493 */         inventoryIn.markDirty();
/* 494 */         return true;
/*     */       } 
/*     */       
/* 497 */       inventoryIn.setInventorySlotContents(index, itemstack1);
/*     */     } 
/*     */     
/* 500 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean putDropInInventoryAllSlots(IInventory p_145898_0_, EntityItem itemIn) {
/* 509 */     boolean flag = false;
/*     */     
/* 511 */     if (itemIn == null)
/*     */     {
/* 513 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 517 */     ItemStack itemstack = itemIn.getEntityItem().copy();
/* 518 */     ItemStack itemstack1 = putStackInInventoryAllSlots(p_145898_0_, itemstack, (EnumFacing)null);
/*     */     
/* 520 */     if (itemstack1 != null && itemstack1.stackSize != 0) {
/*     */       
/* 522 */       itemIn.setEntityItemStack(itemstack1);
/*     */     }
/*     */     else {
/*     */       
/* 526 */       flag = true;
/* 527 */       itemIn.setDead();
/*     */     } 
/*     */     
/* 530 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack putStackInInventoryAllSlots(IInventory inventoryIn, ItemStack stack, EnumFacing side) {
/* 539 */     if (inventoryIn instanceof ISidedInventory && side != null) {
/*     */       
/* 541 */       ISidedInventory isidedinventory = (ISidedInventory)inventoryIn;
/* 542 */       int[] aint = isidedinventory.getSlotsForFace(side);
/*     */       
/* 544 */       for (int k = 0; k < aint.length && stack != null && stack.stackSize > 0; k++)
/*     */       {
/* 546 */         stack = insertStack(inventoryIn, stack, aint[k], side);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 551 */       int i = inventoryIn.getSizeInventory();
/*     */       
/* 553 */       for (int j = 0; j < i && stack != null && stack.stackSize > 0; j++)
/*     */       {
/* 555 */         stack = insertStack(inventoryIn, stack, j, side);
/*     */       }
/*     */     } 
/*     */     
/* 559 */     if (stack != null && stack.stackSize == 0)
/*     */     {
/* 561 */       stack = null;
/*     */     }
/*     */     
/* 564 */     return stack;
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
/*     */   private static boolean canInsertItemInSlot(IInventory inventoryIn, ItemStack stack, int index, EnumFacing side) {
/* 577 */     return !inventoryIn.isItemValidForSlot(index, stack) ? false : ((!(inventoryIn instanceof ISidedInventory) || ((ISidedInventory)inventoryIn).canInsertItem(index, stack, side)));
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
/*     */   private static boolean canExtractItemFromSlot(IInventory inventoryIn, ItemStack stack, int index, EnumFacing side) {
/* 590 */     return (!(inventoryIn instanceof ISidedInventory) || ((ISidedInventory)inventoryIn).canExtractItem(index, stack, side));
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
/*     */   private static ItemStack insertStack(IInventory inventoryIn, ItemStack stack, int index, EnumFacing side) {
/* 603 */     ItemStack itemstack = inventoryIn.getStackInSlot(index);
/*     */     
/* 605 */     if (canInsertItemInSlot(inventoryIn, stack, index, side)) {
/*     */       
/* 607 */       boolean flag = false;
/*     */       
/* 609 */       if (itemstack == null) {
/*     */         
/* 611 */         inventoryIn.setInventorySlotContents(index, stack);
/* 612 */         stack = null;
/* 613 */         flag = true;
/*     */       }
/* 615 */       else if (canCombine(itemstack, stack)) {
/*     */         
/* 617 */         int i = stack.getMaxStackSize() - itemstack.stackSize;
/* 618 */         int j = Math.min(stack.stackSize, i);
/* 619 */         stack.stackSize -= j;
/* 620 */         itemstack.stackSize += j;
/* 621 */         flag = (j > 0);
/*     */       } 
/*     */       
/* 624 */       if (flag) {
/*     */         
/* 626 */         if (inventoryIn instanceof TileEntityHopper) {
/*     */           
/* 628 */           TileEntityHopper tileentityhopper = (TileEntityHopper)inventoryIn;
/*     */           
/* 630 */           if (tileentityhopper.mayTransfer())
/*     */           {
/* 632 */             tileentityhopper.setTransferCooldown(8);
/*     */           }
/*     */           
/* 635 */           inventoryIn.markDirty();
/*     */         } 
/*     */         
/* 638 */         inventoryIn.markDirty();
/*     */       } 
/*     */     } 
/*     */     
/* 642 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IInventory getInventoryForHopperTransfer() {
/* 650 */     EnumFacing enumfacing = BlockHopper.getFacing(getBlockMetadata());
/* 651 */     return getInventoryAtPosition(getWorld(), (this.pos.getX() + enumfacing.getFrontOffsetX()), (this.pos.getY() + enumfacing.getFrontOffsetY()), (this.pos.getZ() + enumfacing.getFrontOffsetZ()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IInventory getHopperInventory(IHopper hopper) {
/* 661 */     return getInventoryAtPosition(hopper.getWorld(), hopper.getXPos(), hopper.getYPos() + 1.0D, hopper.getZPos());
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<EntityItem> func_181556_a(World p_181556_0_, double p_181556_1_, double p_181556_3_, double p_181556_5_) {
/* 666 */     return p_181556_0_.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(p_181556_1_ - 0.5D, p_181556_3_ - 0.5D, p_181556_5_ - 0.5D, p_181556_1_ + 0.5D, p_181556_3_ + 0.5D, p_181556_5_ + 0.5D), EntitySelectors.selectAnything);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IInventory getInventoryAtPosition(World worldIn, double x, double y, double z) {
/*     */     ILockableContainer iLockableContainer;
/* 674 */     IInventory iInventory1, iinventory = null;
/* 675 */     int i = MathHelper.floor_double(x);
/* 676 */     int j = MathHelper.floor_double(y);
/* 677 */     int k = MathHelper.floor_double(z);
/* 678 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 679 */     Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */     
/* 681 */     if (block.hasTileEntity()) {
/*     */       
/* 683 */       TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */       
/* 685 */       if (tileentity instanceof IInventory) {
/*     */         
/* 687 */         iinventory = (IInventory)tileentity;
/*     */         
/* 689 */         if (iinventory instanceof TileEntityChest && block instanceof BlockChest)
/*     */         {
/* 691 */           iLockableContainer = ((BlockChest)block).getLockableContainer(worldIn, blockpos);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 696 */     if (iLockableContainer == null) {
/*     */       
/* 698 */       List<Entity> list = worldIn.getEntitiesInAABBexcluding(null, new AxisAlignedBB(x - 0.5D, y - 0.5D, z - 0.5D, x + 0.5D, y + 0.5D, z + 0.5D), EntitySelectors.selectInventories);
/*     */       
/* 700 */       if (list.size() > 0)
/*     */       {
/* 702 */         iInventory1 = (IInventory)list.get(worldIn.rand.nextInt(list.size()));
/*     */       }
/*     */     } 
/*     */     
/* 706 */     return iInventory1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean canCombine(ItemStack stack1, ItemStack stack2) {
/* 711 */     return (stack1.getItem() != stack2.getItem()) ? false : ((stack1.getMetadata() != stack2.getMetadata()) ? false : ((stack1.stackSize > stack1.getMaxStackSize()) ? false : ItemStack.areItemStackTagsEqual(stack1, stack2)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getXPos() {
/* 719 */     return this.pos.getX() + 0.5D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYPos() {
/* 727 */     return this.pos.getY() + 0.5D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZPos() {
/* 735 */     return this.pos.getZ() + 0.5D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTransferCooldown(int ticks) {
/* 740 */     this.transferCooldown = ticks;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOnTransferCooldown() {
/* 745 */     return (this.transferCooldown > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mayTransfer() {
/* 750 */     return (this.transferCooldown <= 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 755 */     return "minecraft:hopper";
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 760 */     return (Container)new ContainerHopper(playerInventory, this, playerIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 765 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 774 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 779 */     for (int i = 0; i < this.inventory.length; i++)
/*     */     {
/* 781 */       this.inventory[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\tileentity\TileEntityHopper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
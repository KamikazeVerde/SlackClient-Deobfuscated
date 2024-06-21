/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.ILockableContainer;
/*     */ import net.minecraft.world.LockCode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InventoryLargeChest
/*     */   implements ILockableContainer
/*     */ {
/*     */   private String name;
/*     */   private ILockableContainer upperChest;
/*     */   private ILockableContainer lowerChest;
/*     */   
/*     */   public InventoryLargeChest(String nameIn, ILockableContainer upperChestIn, ILockableContainer lowerChestIn) {
/*  25 */     this.name = nameIn;
/*     */     
/*  27 */     if (upperChestIn == null)
/*     */     {
/*  29 */       upperChestIn = lowerChestIn;
/*     */     }
/*     */     
/*  32 */     if (lowerChestIn == null)
/*     */     {
/*  34 */       lowerChestIn = upperChestIn;
/*     */     }
/*     */     
/*  37 */     this.upperChest = upperChestIn;
/*  38 */     this.lowerChest = lowerChestIn;
/*     */     
/*  40 */     if (upperChestIn.isLocked()) {
/*     */       
/*  42 */       lowerChestIn.setLockCode(upperChestIn.getLockCode());
/*     */     }
/*  44 */     else if (lowerChestIn.isLocked()) {
/*     */       
/*  46 */       upperChestIn.setLockCode(lowerChestIn.getLockCode());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  55 */     return this.upperChest.getSizeInventory() + this.lowerChest.getSizeInventory();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPartOfLargeChest(IInventory inventoryIn) {
/*  63 */     return (this.upperChest == inventoryIn || this.lowerChest == inventoryIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandSenderName() {
/*  71 */     return this.upperChest.hasCustomName() ? this.upperChest.getCommandSenderName() : (this.lowerChest.hasCustomName() ? this.lowerChest.getCommandSenderName() : this.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/*  79 */     return (this.upperChest.hasCustomName() || this.lowerChest.hasCustomName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/*  87 */     return hasCustomName() ? (IChatComponent)new ChatComponentText(getCommandSenderName()) : (IChatComponent)new ChatComponentTranslation(getCommandSenderName(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  97 */     return (index >= this.upperChest.getSizeInventory()) ? this.lowerChest.getStackInSlot(index - this.upperChest.getSizeInventory()) : this.upperChest.getStackInSlot(index);
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
/* 108 */     return (index >= this.upperChest.getSizeInventory()) ? this.lowerChest.decrStackSize(index - this.upperChest.getSizeInventory(), count) : this.upperChest.decrStackSize(index, count);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlotOnClosing(int index) {
/* 118 */     return (index >= this.upperChest.getSizeInventory()) ? this.lowerChest.getStackInSlotOnClosing(index - this.upperChest.getSizeInventory()) : this.upperChest.getStackInSlotOnClosing(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 126 */     if (index >= this.upperChest.getSizeInventory()) {
/*     */       
/* 128 */       this.lowerChest.setInventorySlotContents(index - this.upperChest.getSizeInventory(), stack);
/*     */     }
/*     */     else {
/*     */       
/* 132 */       this.upperChest.setInventorySlotContents(index, stack);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 141 */     return this.upperChest.getInventoryStackLimit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 150 */     this.upperChest.markDirty();
/* 151 */     this.lowerChest.markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 159 */     return (this.upperChest.isUseableByPlayer(player) && this.lowerChest.isUseableByPlayer(player));
/*     */   }
/*     */ 
/*     */   
/*     */   public void openInventory(EntityPlayer player) {
/* 164 */     this.upperChest.openInventory(player);
/* 165 */     this.lowerChest.openInventory(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {
/* 170 */     this.upperChest.closeInventory(player);
/* 171 */     this.lowerChest.closeInventory(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValidForSlot(int index, ItemStack stack) {
/* 179 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 184 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 193 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLocked() {
/* 198 */     return (this.upperChest.isLocked() || this.lowerChest.isLocked());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLockCode(LockCode code) {
/* 203 */     this.upperChest.setLockCode(code);
/* 204 */     this.lowerChest.setLockCode(code);
/*     */   }
/*     */ 
/*     */   
/*     */   public LockCode getLockCode() {
/* 209 */     return this.upperChest.getLockCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 214 */     return this.upperChest.getGuiID();
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 219 */     return new ContainerChest((IInventory)playerInventory, (IInventory)this, playerIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 224 */     this.upperChest.clear();
/* 225 */     this.lowerChest.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\inventory\InventoryLargeChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
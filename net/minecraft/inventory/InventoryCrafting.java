/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InventoryCrafting
/*     */   implements IInventory
/*     */ {
/*     */   private final ItemStack[] stackList;
/*     */   private final int inventoryWidth;
/*     */   private final int inventoryHeight;
/*     */   private final Container eventHandler;
/*     */   
/*     */   public InventoryCrafting(Container eventHandlerIn, int width, int height) {
/*  25 */     int i = width * height;
/*  26 */     this.stackList = new ItemStack[i];
/*  27 */     this.eventHandler = eventHandlerIn;
/*  28 */     this.inventoryWidth = width;
/*  29 */     this.inventoryHeight = height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  37 */     return this.stackList.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  47 */     return (index >= getSizeInventory()) ? null : this.stackList[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInRowAndColumn(int row, int column) {
/*  55 */     return (row >= 0 && row < this.inventoryWidth && column >= 0 && column <= this.inventoryHeight) ? getStackInSlot(row + column * this.inventoryWidth) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandSenderName() {
/*  63 */     return "container.crafting";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/*  79 */     return hasCustomName() ? (IChatComponent)new ChatComponentText(getCommandSenderName()) : (IChatComponent)new ChatComponentTranslation(getCommandSenderName(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlotOnClosing(int index) {
/*  89 */     if (this.stackList[index] != null) {
/*     */       
/*  91 */       ItemStack itemstack = this.stackList[index];
/*  92 */       this.stackList[index] = null;
/*  93 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/*  97 */     return null;
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
/*     */   public ItemStack decrStackSize(int index, int count) {
/* 109 */     if (this.stackList[index] != null) {
/*     */       
/* 111 */       if ((this.stackList[index]).stackSize <= count) {
/*     */         
/* 113 */         ItemStack itemstack1 = this.stackList[index];
/* 114 */         this.stackList[index] = null;
/* 115 */         this.eventHandler.onCraftMatrixChanged(this);
/* 116 */         return itemstack1;
/*     */       } 
/*     */ 
/*     */       
/* 120 */       ItemStack itemstack = this.stackList[index].splitStack(count);
/*     */       
/* 122 */       if ((this.stackList[index]).stackSize == 0)
/*     */       {
/* 124 */         this.stackList[index] = null;
/*     */       }
/*     */       
/* 127 */       this.eventHandler.onCraftMatrixChanged(this);
/* 128 */       return itemstack;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 133 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 142 */     this.stackList[index] = stack;
/* 143 */     this.eventHandler.onCraftMatrixChanged(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 151 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 167 */     return true;
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
/* 183 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 188 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 197 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 202 */     for (int i = 0; i < this.stackList.length; i++)
/*     */     {
/* 204 */       this.stackList[i] = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 210 */     return this.inventoryHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 215 */     return this.inventoryWidth;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\inventory\InventoryCrafting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
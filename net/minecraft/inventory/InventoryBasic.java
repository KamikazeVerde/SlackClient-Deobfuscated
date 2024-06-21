/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ public class InventoryBasic
/*     */   implements IInventory
/*     */ {
/*     */   private String inventoryTitle;
/*     */   private int slotsCount;
/*     */   private ItemStack[] inventoryContents;
/*     */   private List<IInvBasic> field_70480_d;
/*     */   private boolean hasCustomName;
/*     */   
/*     */   public InventoryBasic(String title, boolean customName, int slotCount) {
/*  21 */     this.inventoryTitle = title;
/*  22 */     this.hasCustomName = customName;
/*  23 */     this.slotsCount = slotCount;
/*  24 */     this.inventoryContents = new ItemStack[slotCount];
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryBasic(IChatComponent title, int slotCount) {
/*  29 */     this(title.getUnformattedText(), true, slotCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_110134_a(IInvBasic p_110134_1_) {
/*  34 */     if (this.field_70480_d == null)
/*     */     {
/*  36 */       this.field_70480_d = Lists.newArrayList();
/*     */     }
/*     */     
/*  39 */     this.field_70480_d.add(p_110134_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_110132_b(IInvBasic p_110132_1_) {
/*  44 */     this.field_70480_d.remove(p_110132_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  54 */     return (index >= 0 && index < this.inventoryContents.length) ? this.inventoryContents[index] : null;
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
/*  65 */     if (this.inventoryContents[index] != null) {
/*     */       
/*  67 */       if ((this.inventoryContents[index]).stackSize <= count) {
/*     */         
/*  69 */         ItemStack itemstack1 = this.inventoryContents[index];
/*  70 */         this.inventoryContents[index] = null;
/*  71 */         markDirty();
/*  72 */         return itemstack1;
/*     */       } 
/*     */ 
/*     */       
/*  76 */       ItemStack itemstack = this.inventoryContents[index].splitStack(count);
/*     */       
/*  78 */       if ((this.inventoryContents[index]).stackSize == 0)
/*     */       {
/*  80 */         this.inventoryContents[index] = null;
/*     */       }
/*     */       
/*  83 */       markDirty();
/*  84 */       return itemstack;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  89 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_174894_a(ItemStack stack) {
/*  95 */     ItemStack itemstack = stack.copy();
/*     */     
/*  97 */     for (int i = 0; i < this.slotsCount; i++) {
/*     */       
/*  99 */       ItemStack itemstack1 = getStackInSlot(i);
/*     */       
/* 101 */       if (itemstack1 == null) {
/*     */         
/* 103 */         setInventorySlotContents(i, itemstack);
/* 104 */         markDirty();
/* 105 */         return null;
/*     */       } 
/*     */       
/* 108 */       if (ItemStack.areItemsEqual(itemstack1, itemstack)) {
/*     */         
/* 110 */         int j = Math.min(getInventoryStackLimit(), itemstack1.getMaxStackSize());
/* 111 */         int k = Math.min(itemstack.stackSize, j - itemstack1.stackSize);
/*     */         
/* 113 */         if (k > 0) {
/*     */           
/* 115 */           itemstack1.stackSize += k;
/* 116 */           itemstack.stackSize -= k;
/*     */           
/* 118 */           if (itemstack.stackSize <= 0) {
/*     */             
/* 120 */             markDirty();
/* 121 */             return null;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 127 */     if (itemstack.stackSize != stack.stackSize)
/*     */     {
/* 129 */       markDirty();
/*     */     }
/*     */     
/* 132 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlotOnClosing(int index) {
/* 142 */     if (this.inventoryContents[index] != null) {
/*     */       
/* 144 */       ItemStack itemstack = this.inventoryContents[index];
/* 145 */       this.inventoryContents[index] = null;
/* 146 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 150 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 159 */     this.inventoryContents[index] = stack;
/*     */     
/* 161 */     if (stack != null && stack.stackSize > getInventoryStackLimit())
/*     */     {
/* 163 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */     
/* 166 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/* 174 */     return this.slotsCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandSenderName() {
/* 182 */     return this.inventoryTitle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 190 */     return this.hasCustomName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomName(String inventoryTitleIn) {
/* 198 */     this.hasCustomName = true;
/* 199 */     this.inventoryTitle = inventoryTitleIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/* 207 */     return hasCustomName() ? (IChatComponent)new ChatComponentText(getCommandSenderName()) : (IChatComponent)new ChatComponentTranslation(getCommandSenderName(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 215 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 224 */     if (this.field_70480_d != null)
/*     */     {
/* 226 */       for (int i = 0; i < this.field_70480_d.size(); i++)
/*     */       {
/* 228 */         ((IInvBasic)this.field_70480_d.get(i)).onInventoryChanged(this);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 238 */     return true;
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
/* 254 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 259 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 268 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 273 */     for (int i = 0; i < this.inventoryContents.length; i++)
/*     */     {
/* 275 */       this.inventoryContents[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\inventory\InventoryBasic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
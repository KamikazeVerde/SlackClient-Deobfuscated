/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.IMerchant;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.village.MerchantRecipe;
/*     */ import net.minecraft.village.MerchantRecipeList;
/*     */ 
/*     */ public class InventoryMerchant
/*     */   implements IInventory {
/*     */   private final IMerchant theMerchant;
/*  15 */   private ItemStack[] theInventory = new ItemStack[3];
/*     */   
/*     */   private final EntityPlayer thePlayer;
/*     */   private MerchantRecipe currentRecipe;
/*     */   private int currentRecipeIndex;
/*     */   
/*     */   public InventoryMerchant(EntityPlayer thePlayerIn, IMerchant theMerchantIn) {
/*  22 */     this.thePlayer = thePlayerIn;
/*  23 */     this.theMerchant = theMerchantIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  31 */     return this.theInventory.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  41 */     return this.theInventory[index];
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
/*  52 */     if (this.theInventory[index] != null) {
/*     */       
/*  54 */       if (index == 2) {
/*     */         
/*  56 */         ItemStack itemstack2 = this.theInventory[index];
/*  57 */         this.theInventory[index] = null;
/*  58 */         return itemstack2;
/*     */       } 
/*  60 */       if ((this.theInventory[index]).stackSize <= count) {
/*     */         
/*  62 */         ItemStack itemstack1 = this.theInventory[index];
/*  63 */         this.theInventory[index] = null;
/*     */         
/*  65 */         if (inventoryResetNeededOnSlotChange(index))
/*     */         {
/*  67 */           resetRecipeAndSlots();
/*     */         }
/*     */         
/*  70 */         return itemstack1;
/*     */       } 
/*     */ 
/*     */       
/*  74 */       ItemStack itemstack = this.theInventory[index].splitStack(count);
/*     */       
/*  76 */       if ((this.theInventory[index]).stackSize == 0)
/*     */       {
/*  78 */         this.theInventory[index] = null;
/*     */       }
/*     */       
/*  81 */       if (inventoryResetNeededOnSlotChange(index))
/*     */       {
/*  83 */         resetRecipeAndSlots();
/*     */       }
/*     */       
/*  86 */       return itemstack;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean inventoryResetNeededOnSlotChange(int p_70469_1_) {
/* 100 */     return (p_70469_1_ == 0 || p_70469_1_ == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlotOnClosing(int index) {
/* 110 */     if (this.theInventory[index] != null) {
/*     */       
/* 112 */       ItemStack itemstack = this.theInventory[index];
/* 113 */       this.theInventory[index] = null;
/* 114 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 118 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 127 */     this.theInventory[index] = stack;
/*     */     
/* 129 */     if (stack != null && stack.stackSize > getInventoryStackLimit())
/*     */     {
/* 131 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */     
/* 134 */     if (inventoryResetNeededOnSlotChange(index))
/*     */     {
/* 136 */       resetRecipeAndSlots();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandSenderName() {
/* 145 */     return "mob.villager";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/* 161 */     return hasCustomName() ? (IChatComponent)new ChatComponentText(getCommandSenderName()) : (IChatComponent)new ChatComponentTranslation(getCommandSenderName(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 169 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 177 */     return (this.theMerchant.getCustomer() == player);
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
/* 193 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 202 */     resetRecipeAndSlots();
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetRecipeAndSlots() {
/* 207 */     this.currentRecipe = null;
/* 208 */     ItemStack itemstack = this.theInventory[0];
/* 209 */     ItemStack itemstack1 = this.theInventory[1];
/*     */     
/* 211 */     if (itemstack == null) {
/*     */       
/* 213 */       itemstack = itemstack1;
/* 214 */       itemstack1 = null;
/*     */     } 
/*     */     
/* 217 */     if (itemstack == null) {
/*     */       
/* 219 */       setInventorySlotContents(2, null);
/*     */     }
/*     */     else {
/*     */       
/* 223 */       MerchantRecipeList merchantrecipelist = this.theMerchant.getRecipes(this.thePlayer);
/*     */       
/* 225 */       if (merchantrecipelist != null) {
/*     */         
/* 227 */         MerchantRecipe merchantrecipe = merchantrecipelist.canRecipeBeUsed(itemstack, itemstack1, this.currentRecipeIndex);
/*     */         
/* 229 */         if (merchantrecipe != null && !merchantrecipe.isRecipeDisabled()) {
/*     */           
/* 231 */           this.currentRecipe = merchantrecipe;
/* 232 */           setInventorySlotContents(2, merchantrecipe.getItemToSell().copy());
/*     */         }
/* 234 */         else if (itemstack1 != null) {
/*     */           
/* 236 */           merchantrecipe = merchantrecipelist.canRecipeBeUsed(itemstack1, itemstack, this.currentRecipeIndex);
/*     */           
/* 238 */           if (merchantrecipe != null && !merchantrecipe.isRecipeDisabled())
/*     */           {
/* 240 */             this.currentRecipe = merchantrecipe;
/* 241 */             setInventorySlotContents(2, merchantrecipe.getItemToSell().copy());
/*     */           }
/*     */           else
/*     */           {
/* 245 */             setInventorySlotContents(2, null);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 250 */           setInventorySlotContents(2, null);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 255 */     this.theMerchant.verifySellingItem(getStackInSlot(2));
/*     */   }
/*     */ 
/*     */   
/*     */   public MerchantRecipe getCurrentRecipe() {
/* 260 */     return this.currentRecipe;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentRecipeIndex(int currentRecipeIndexIn) {
/* 265 */     this.currentRecipeIndex = currentRecipeIndexIn;
/* 266 */     resetRecipeAndSlots();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 271 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 280 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 285 */     for (int i = 0; i < this.theInventory.length; i++)
/*     */     {
/* 287 */       this.theInventory[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\inventory\InventoryMerchant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
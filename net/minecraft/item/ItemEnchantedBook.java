/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentData;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.WeightedRandomChestContent;
/*     */ 
/*     */ public class ItemEnchantedBook
/*     */   extends Item {
/*     */   public boolean hasEffect(ItemStack stack) {
/*  18 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemTool(ItemStack stack) {
/*  26 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumRarity getRarity(ItemStack stack) {
/*  34 */     return (getEnchantments(stack).tagCount() > 0) ? EnumRarity.UNCOMMON : super.getRarity(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagList getEnchantments(ItemStack stack) {
/*  39 */     NBTTagCompound nbttagcompound = stack.getTagCompound();
/*  40 */     return (nbttagcompound != null && nbttagcompound.hasKey("StoredEnchantments", 9)) ? (NBTTagList)nbttagcompound.getTag("StoredEnchantments") : new NBTTagList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/*  51 */     super.addInformation(stack, playerIn, tooltip, advanced);
/*  52 */     NBTTagList nbttaglist = getEnchantments(stack);
/*     */     
/*  54 */     if (nbttaglist != null)
/*     */     {
/*  56 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */         
/*  58 */         int j = nbttaglist.getCompoundTagAt(i).getShort("id");
/*  59 */         int k = nbttaglist.getCompoundTagAt(i).getShort("lvl");
/*     */         
/*  61 */         if (Enchantment.getEnchantmentById(j) != null)
/*     */         {
/*  63 */           tooltip.add(Enchantment.getEnchantmentById(j).getTranslatedName(k));
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEnchantment(ItemStack stack, EnchantmentData enchantment) {
/*  74 */     NBTTagList nbttaglist = getEnchantments(stack);
/*  75 */     boolean flag = true;
/*     */     
/*  77 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/*  79 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*     */       
/*  81 */       if (nbttagcompound.getShort("id") == enchantment.enchantmentobj.effectId) {
/*     */         
/*  83 */         if (nbttagcompound.getShort("lvl") < enchantment.enchantmentLevel)
/*     */         {
/*  85 */           nbttagcompound.setShort("lvl", (short)enchantment.enchantmentLevel);
/*     */         }
/*     */         
/*  88 */         flag = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  93 */     if (flag) {
/*     */       
/*  95 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  96 */       nbttagcompound1.setShort("id", (short)enchantment.enchantmentobj.effectId);
/*  97 */       nbttagcompound1.setShort("lvl", (short)enchantment.enchantmentLevel);
/*  98 */       nbttaglist.appendTag((NBTBase)nbttagcompound1);
/*     */     } 
/*     */     
/* 101 */     if (!stack.hasTagCompound())
/*     */     {
/* 103 */       stack.setTagCompound(new NBTTagCompound());
/*     */     }
/*     */     
/* 106 */     stack.getTagCompound().setTag("StoredEnchantments", (NBTBase)nbttaglist);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getEnchantedItemStack(EnchantmentData data) {
/* 114 */     ItemStack itemstack = new ItemStack(this);
/* 115 */     addEnchantment(itemstack, data);
/* 116 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public void getAll(Enchantment enchantment, List<ItemStack> list) {
/* 121 */     for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++)
/*     */     {
/* 123 */       list.add(getEnchantedItemStack(new EnchantmentData(enchantment, i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public WeightedRandomChestContent getRandom(Random rand) {
/* 129 */     return getRandom(rand, 1, 1, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public WeightedRandomChestContent getRandom(Random rand, int minChance, int maxChance, int weight) {
/* 134 */     ItemStack itemstack = new ItemStack(Items.book, 1, 0);
/* 135 */     EnchantmentHelper.addRandomEnchantment(rand, itemstack, 30);
/* 136 */     return new WeightedRandomChestContent(itemstack, minChance, maxChance, weight);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\item\ItemEnchantedBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
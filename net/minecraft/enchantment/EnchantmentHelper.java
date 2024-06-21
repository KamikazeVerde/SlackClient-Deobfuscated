/*     */ package net.minecraft.enchantment;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ 
/*     */ 
/*     */ public class EnchantmentHelper
/*     */ {
/*  24 */   private static final Random enchantmentRand = new Random();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  29 */   private static final ModifierDamage enchantmentModifierDamage = new ModifierDamage();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   private static final ModifierLiving enchantmentModifierLiving = new ModifierLiving();
/*  35 */   private static final HurtIterator ENCHANTMENT_ITERATOR_HURT = new HurtIterator();
/*  36 */   private static final DamageIterator ENCHANTMENT_ITERATOR_DAMAGE = new DamageIterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEnchantmentLevel(int enchID, ItemStack stack) {
/*  46 */     if (stack != null) {
/*  47 */       NBTTagList nbttaglist = stack.getEnchantmentTagList();
/*     */       
/*  49 */       if (nbttaglist != null) {
/*  50 */         for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*  51 */           int j = nbttaglist.getCompoundTagAt(i).getShort("id");
/*  52 */           int k = nbttaglist.getCompoundTagAt(i).getShort("lvl");
/*     */           
/*  54 */           if (j == enchID) {
/*  55 */             return k;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  61 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map<Integer, Integer> getEnchantments(ItemStack stack) {
/*  66 */     Map<Integer, Integer> map = Maps.newLinkedHashMap();
/*  67 */     NBTTagList nbttaglist = (stack.getItem() == Items.enchanted_book) ? Items.enchanted_book.getEnchantments(stack) : stack.getEnchantmentTagList();
/*     */     
/*  69 */     if (nbttaglist != null)
/*     */     {
/*  71 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */         
/*  73 */         int j = nbttaglist.getCompoundTagAt(i).getShort("id");
/*  74 */         int k = nbttaglist.getCompoundTagAt(i).getShort("lvl");
/*  75 */         map.put(Integer.valueOf(j), Integer.valueOf(k));
/*     */       } 
/*     */     }
/*     */     
/*  79 */     return map;
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
/*     */   public static void setEnchantments(Map<Integer, Integer> enchMap, ItemStack stack) {
/*  91 */     NBTTagList nbttaglist = new NBTTagList();
/*  92 */     Iterator<Integer> iterator = enchMap.keySet().iterator();
/*     */     
/*  94 */     while (iterator.hasNext()) {
/*     */       
/*  96 */       int i = ((Integer)iterator.next()).intValue();
/*  97 */       Enchantment enchantment = Enchantment.getEnchantmentById(i);
/*     */       
/*  99 */       if (enchantment != null) {
/*     */         
/* 101 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 102 */         nbttagcompound.setShort("id", (short)i);
/* 103 */         nbttagcompound.setShort("lvl", (short)((Integer)enchMap.get(Integer.valueOf(i))).intValue());
/* 104 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */         
/* 106 */         if (stack.getItem() == Items.enchanted_book)
/*     */         {
/* 108 */           Items.enchanted_book.addEnchantment(stack, new EnchantmentData(enchantment, ((Integer)enchMap.get(Integer.valueOf(i))).intValue()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     if (nbttaglist.tagCount() > 0) {
/*     */       
/* 115 */       if (stack.getItem() != Items.enchanted_book)
/*     */       {
/* 117 */         stack.setTagInfo("ench", (NBTBase)nbttaglist);
/*     */       }
/*     */     }
/* 120 */     else if (stack.hasTagCompound()) {
/*     */       
/* 122 */       stack.getTagCompound().removeTag("ench");
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
/*     */   public static int getMaxEnchantmentLevel(int enchID, ItemStack[] stacks) {
/* 134 */     if (stacks == null)
/*     */     {
/* 136 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 140 */     int i = 0;
/*     */     
/* 142 */     for (ItemStack itemstack : stacks) {
/*     */       
/* 144 */       int j = getEnchantmentLevel(enchID, itemstack);
/*     */       
/* 146 */       if (j > i)
/*     */       {
/* 148 */         i = j;
/*     */       }
/*     */     } 
/*     */     
/* 152 */     return i;
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
/*     */   private static void applyEnchantmentModifier(IModifier modifier, ItemStack stack) {
/* 164 */     if (stack != null) {
/*     */       
/* 166 */       NBTTagList nbttaglist = stack.getEnchantmentTagList();
/*     */       
/* 168 */       if (nbttaglist != null)
/*     */       {
/* 170 */         for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */           
/* 172 */           int j = nbttaglist.getCompoundTagAt(i).getShort("id");
/* 173 */           int k = nbttaglist.getCompoundTagAt(i).getShort("lvl");
/*     */           
/* 175 */           if (Enchantment.getEnchantmentById(j) != null)
/*     */           {
/* 177 */             modifier.calculateModifier(Enchantment.getEnchantmentById(j), k);
/*     */           }
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
/*     */   private static void applyEnchantmentModifierArray(IModifier modifier, ItemStack[] stacks) {
/* 192 */     for (ItemStack itemstack : stacks)
/*     */     {
/* 194 */       applyEnchantmentModifier(modifier, itemstack);
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
/*     */   public static int getEnchantmentModifierDamage(ItemStack[] stacks, DamageSource source) {
/* 206 */     enchantmentModifierDamage.damageModifier = 0;
/* 207 */     enchantmentModifierDamage.source = source;
/* 208 */     applyEnchantmentModifierArray(enchantmentModifierDamage, stacks);
/*     */     
/* 210 */     if (enchantmentModifierDamage.damageModifier > 25) {
/*     */       
/* 212 */       enchantmentModifierDamage.damageModifier = 25;
/*     */     }
/* 214 */     else if (enchantmentModifierDamage.damageModifier < 0) {
/*     */       
/* 216 */       enchantmentModifierDamage.damageModifier = 0;
/*     */     } 
/*     */     
/* 219 */     return (enchantmentModifierDamage.damageModifier + 1 >> 1) + enchantmentRand.nextInt((enchantmentModifierDamage.damageModifier >> 1) + 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float func_152377_a(ItemStack p_152377_0_, EnumCreatureAttribute p_152377_1_) {
/* 224 */     enchantmentModifierLiving.livingModifier = 0.0F;
/* 225 */     enchantmentModifierLiving.entityLiving = p_152377_1_;
/* 226 */     applyEnchantmentModifier(enchantmentModifierLiving, p_152377_0_);
/* 227 */     return enchantmentModifierLiving.livingModifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void applyThornEnchantments(EntityLivingBase p_151384_0_, Entity p_151384_1_) {
/* 232 */     ENCHANTMENT_ITERATOR_HURT.attacker = p_151384_1_;
/* 233 */     ENCHANTMENT_ITERATOR_HURT.user = p_151384_0_;
/*     */     
/* 235 */     if (p_151384_0_ != null)
/*     */     {
/* 237 */       applyEnchantmentModifierArray(ENCHANTMENT_ITERATOR_HURT, p_151384_0_.getInventory());
/*     */     }
/*     */     
/* 240 */     if (p_151384_1_ instanceof net.minecraft.entity.player.EntityPlayer)
/*     */     {
/* 242 */       applyEnchantmentModifier(ENCHANTMENT_ITERATOR_HURT, p_151384_0_.getHeldItem());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void applyArthropodEnchantments(EntityLivingBase p_151385_0_, Entity p_151385_1_) {
/* 248 */     ENCHANTMENT_ITERATOR_DAMAGE.user = p_151385_0_;
/* 249 */     ENCHANTMENT_ITERATOR_DAMAGE.target = p_151385_1_;
/*     */     
/* 251 */     if (p_151385_0_ != null)
/*     */     {
/* 253 */       applyEnchantmentModifierArray(ENCHANTMENT_ITERATOR_DAMAGE, p_151385_0_.getInventory());
/*     */     }
/*     */     
/* 256 */     if (p_151385_0_ instanceof net.minecraft.entity.player.EntityPlayer)
/*     */     {
/* 258 */       applyEnchantmentModifier(ENCHANTMENT_ITERATOR_DAMAGE, p_151385_0_.getHeldItem());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getKnockbackModifier(EntityLivingBase player) {
/* 269 */     return getEnchantmentLevel(Enchantment.knockback.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getFireAspectModifier(EntityLivingBase player) {
/* 279 */     return getEnchantmentLevel(Enchantment.fireAspect.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getRespiration(Entity player) {
/* 289 */     return getMaxEnchantmentLevel(Enchantment.respiration.effectId, player.getInventory());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDepthStriderModifier(Entity player) {
/* 299 */     return getMaxEnchantmentLevel(Enchantment.depthStrider.effectId, player.getInventory());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEfficiencyModifier(EntityLivingBase player) {
/* 309 */     return getEnchantmentLevel(Enchantment.efficiency.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getSilkTouchModifier(EntityLivingBase player) {
/* 319 */     return (getEnchantmentLevel(Enchantment.silkTouch.effectId, player.getHeldItem()) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getFortuneModifier(EntityLivingBase player) {
/* 329 */     return getEnchantmentLevel(Enchantment.fortune.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLuckOfSeaModifier(EntityLivingBase player) {
/* 339 */     return getEnchantmentLevel(Enchantment.luckOfTheSea.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLureModifier(EntityLivingBase player) {
/* 349 */     return getEnchantmentLevel(Enchantment.lure.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLootingModifier(EntityLivingBase player) {
/* 359 */     return getEnchantmentLevel(Enchantment.looting.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getAquaAffinityModifier(EntityLivingBase player) {
/* 369 */     return (getMaxEnchantmentLevel(Enchantment.aquaAffinity.effectId, player.getInventory()) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack getEnchantedItem(Enchantment p_92099_0_, EntityLivingBase p_92099_1_) {
/* 374 */     for (ItemStack itemstack : p_92099_1_.getInventory()) {
/*     */       
/* 376 */       if (itemstack != null && getEnchantmentLevel(p_92099_0_.effectId, itemstack) > 0)
/*     */       {
/* 378 */         return itemstack;
/*     */       }
/*     */     } 
/*     */     
/* 382 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calcItemStackEnchantability(Random p_77514_0_, int p_77514_1_, int p_77514_2_, ItemStack p_77514_3_) {
/* 391 */     Item item = p_77514_3_.getItem();
/* 392 */     int i = item.getItemEnchantability();
/*     */     
/* 394 */     if (i <= 0)
/*     */     {
/* 396 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 400 */     if (p_77514_2_ > 15)
/*     */     {
/* 402 */       p_77514_2_ = 15;
/*     */     }
/*     */     
/* 405 */     int j = p_77514_0_.nextInt(8) + 1 + (p_77514_2_ >> 1) + p_77514_0_.nextInt(p_77514_2_ + 1);
/* 406 */     return (p_77514_1_ == 0) ? Math.max(j / 3, 1) : ((p_77514_1_ == 1) ? (j * 2 / 3 + 1) : Math.max(j, p_77514_2_ * 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack addRandomEnchantment(Random p_77504_0_, ItemStack p_77504_1_, int p_77504_2_) {
/* 415 */     List<EnchantmentData> list = buildEnchantmentList(p_77504_0_, p_77504_1_, p_77504_2_);
/* 416 */     boolean flag = (p_77504_1_.getItem() == Items.book);
/*     */     
/* 418 */     if (flag)
/*     */     {
/* 420 */       p_77504_1_.setItem((Item)Items.enchanted_book);
/*     */     }
/*     */     
/* 423 */     if (list != null)
/*     */     {
/* 425 */       for (EnchantmentData enchantmentdata : list) {
/*     */         
/* 427 */         if (flag) {
/*     */           
/* 429 */           Items.enchanted_book.addEnchantment(p_77504_1_, enchantmentdata);
/*     */           
/*     */           continue;
/*     */         } 
/* 433 */         p_77504_1_.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 438 */     return p_77504_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<EnchantmentData> buildEnchantmentList(Random randomIn, ItemStack itemStackIn, int p_77513_2_) {
/* 443 */     Item item = itemStackIn.getItem();
/* 444 */     int i = item.getItemEnchantability();
/*     */     
/* 446 */     if (i <= 0)
/*     */     {
/* 448 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 452 */     i /= 2;
/* 453 */     i = 1 + randomIn.nextInt((i >> 1) + 1) + randomIn.nextInt((i >> 1) + 1);
/* 454 */     int j = i + p_77513_2_;
/* 455 */     float f = (randomIn.nextFloat() + randomIn.nextFloat() - 1.0F) * 0.15F;
/* 456 */     int k = (int)(j * (1.0F + f) + 0.5F);
/*     */     
/* 458 */     if (k < 1)
/*     */     {
/* 460 */       k = 1;
/*     */     }
/*     */     
/* 463 */     List<EnchantmentData> list = null;
/* 464 */     Map<Integer, EnchantmentData> map = mapEnchantmentData(k, itemStackIn);
/*     */     
/* 466 */     if (map != null && !map.isEmpty()) {
/*     */       
/* 468 */       EnchantmentData enchantmentdata = (EnchantmentData)WeightedRandom.getRandomItem(randomIn, map.values());
/*     */       
/* 470 */       if (enchantmentdata != null) {
/*     */         
/* 472 */         list = Lists.newArrayList();
/* 473 */         list.add(enchantmentdata);
/*     */         int l;
/* 475 */         for (l = k; randomIn.nextInt(50) <= l; l >>= 1) {
/*     */           
/* 477 */           Iterator<Integer> iterator = map.keySet().iterator();
/*     */           
/* 479 */           while (iterator.hasNext()) {
/*     */             
/* 481 */             Integer integer = iterator.next();
/* 482 */             boolean flag = true;
/*     */             
/* 484 */             for (EnchantmentData enchantmentdata1 : list) {
/*     */               
/* 486 */               if (!enchantmentdata1.enchantmentobj.canApplyTogether(Enchantment.getEnchantmentById(integer.intValue()))) {
/*     */                 
/* 488 */                 flag = false;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 493 */             if (!flag)
/*     */             {
/* 495 */               iterator.remove();
/*     */             }
/*     */           } 
/*     */           
/* 499 */           if (!map.isEmpty()) {
/*     */             
/* 501 */             EnchantmentData enchantmentdata2 = (EnchantmentData)WeightedRandom.getRandomItem(randomIn, map.values());
/* 502 */             list.add(enchantmentdata2);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 508 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<Integer, EnchantmentData> mapEnchantmentData(int p_77505_0_, ItemStack p_77505_1_) {
/* 514 */     Item item = p_77505_1_.getItem();
/* 515 */     Map<Integer, EnchantmentData> map = null;
/* 516 */     boolean flag = (p_77505_1_.getItem() == Items.book);
/*     */     
/* 518 */     for (Enchantment enchantment : Enchantment.enchantmentsBookList) {
/*     */       
/* 520 */       if (enchantment != null && (enchantment.type.canEnchantItem(item) || flag))
/*     */       {
/* 522 */         for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++) {
/*     */           
/* 524 */           if (p_77505_0_ >= enchantment.getMinEnchantability(i) && p_77505_0_ <= enchantment.getMaxEnchantability(i)) {
/*     */             
/* 526 */             if (map == null)
/*     */             {
/* 528 */               map = Maps.newHashMap();
/*     */             }
/*     */             
/* 531 */             map.put(Integer.valueOf(enchantment.effectId), new EnchantmentData(enchantment, i));
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 537 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   static final class DamageIterator
/*     */     implements IModifier
/*     */   {
/*     */     public EntityLivingBase user;
/*     */     
/*     */     public Entity target;
/*     */     
/*     */     private DamageIterator() {}
/*     */     
/*     */     public void calculateModifier(Enchantment enchantmentIn, int enchantmentLevel) {
/* 551 */       enchantmentIn.onEntityDamaged(this.user, this.target, enchantmentLevel);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class HurtIterator
/*     */     implements IModifier
/*     */   {
/*     */     public EntityLivingBase user;
/*     */     
/*     */     public Entity attacker;
/*     */     
/*     */     private HurtIterator() {}
/*     */     
/*     */     public void calculateModifier(Enchantment enchantmentIn, int enchantmentLevel) {
/* 566 */       enchantmentIn.onUserHurt(this.user, this.attacker, enchantmentLevel);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static interface IModifier
/*     */   {
/*     */     void calculateModifier(Enchantment param1Enchantment, int param1Int);
/*     */   }
/*     */ 
/*     */   
/*     */   static final class ModifierDamage
/*     */     implements IModifier
/*     */   {
/*     */     public int damageModifier;
/*     */     public DamageSource source;
/*     */     
/*     */     private ModifierDamage() {}
/*     */     
/*     */     public void calculateModifier(Enchantment enchantmentIn, int enchantmentLevel) {
/* 586 */       this.damageModifier += enchantmentIn.calcModifierDamage(enchantmentLevel, this.source);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class ModifierLiving
/*     */     implements IModifier
/*     */   {
/*     */     public float livingModifier;
/*     */     
/*     */     public EnumCreatureAttribute entityLiving;
/*     */     
/*     */     private ModifierLiving() {}
/*     */     
/*     */     public void calculateModifier(Enchantment enchantmentIn, int enchantmentLevel) {
/* 601 */       this.livingModifier += enchantmentIn.calcDamageByCreature(enchantmentLevel, this.entityLiving);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\enchantment\EnchantmentHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
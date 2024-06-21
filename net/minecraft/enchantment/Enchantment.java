/*     */ package net.minecraft.enchantment;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StatCollector;
/*     */ 
/*     */ public abstract class Enchantment
/*     */ {
/*  18 */   private static final Enchantment[] enchantmentsList = new Enchantment[256];
/*     */   public static final Enchantment[] enchantmentsBookList;
/*  20 */   private static final Map<ResourceLocation, Enchantment> locationEnchantments = Maps.newHashMap();
/*  21 */   public static final Enchantment protection = new EnchantmentProtection(0, new ResourceLocation("protection"), 10, 0);
/*     */ 
/*     */   
/*  24 */   public static final Enchantment fireProtection = new EnchantmentProtection(1, new ResourceLocation("fire_protection"), 5, 1);
/*  25 */   public static final Enchantment featherFalling = new EnchantmentProtection(2, new ResourceLocation("feather_falling"), 5, 2);
/*     */ 
/*     */   
/*  28 */   public static final Enchantment blastProtection = new EnchantmentProtection(3, new ResourceLocation("blast_protection"), 2, 3);
/*  29 */   public static final Enchantment projectileProtection = new EnchantmentProtection(4, new ResourceLocation("projectile_protection"), 5, 4);
/*  30 */   public static final Enchantment respiration = new EnchantmentOxygen(5, new ResourceLocation("respiration"), 2);
/*     */ 
/*     */   
/*  33 */   public static final Enchantment aquaAffinity = new EnchantmentWaterWorker(6, new ResourceLocation("aqua_affinity"), 2);
/*  34 */   public static final Enchantment thorns = new EnchantmentThorns(7, new ResourceLocation("thorns"), 1);
/*  35 */   public static final Enchantment depthStrider = new EnchantmentWaterWalker(8, new ResourceLocation("depth_strider"), 2);
/*  36 */   public static final Enchantment sharpness = new EnchantmentDamage(16, new ResourceLocation("sharpness"), 10, 0);
/*  37 */   public static final Enchantment smite = new EnchantmentDamage(17, new ResourceLocation("smite"), 5, 1);
/*  38 */   public static final Enchantment baneOfArthropods = new EnchantmentDamage(18, new ResourceLocation("bane_of_arthropods"), 5, 2);
/*  39 */   public static final Enchantment knockback = new EnchantmentKnockback(19, new ResourceLocation("knockback"), 5);
/*     */ 
/*     */   
/*  42 */   public static final Enchantment fireAspect = new EnchantmentFireAspect(20, new ResourceLocation("fire_aspect"), 2);
/*     */ 
/*     */   
/*  45 */   public static final Enchantment looting = new EnchantmentLootBonus(21, new ResourceLocation("looting"), 2, EnumEnchantmentType.WEAPON);
/*     */ 
/*     */   
/*  48 */   public static final Enchantment efficiency = new EnchantmentDigging(32, new ResourceLocation("efficiency"), 10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public static final Enchantment silkTouch = new EnchantmentUntouching(33, new ResourceLocation("silk_touch"), 1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   public static final Enchantment unbreaking = new EnchantmentDurability(34, new ResourceLocation("unbreaking"), 5);
/*     */ 
/*     */   
/*  62 */   public static final Enchantment fortune = new EnchantmentLootBonus(35, new ResourceLocation("fortune"), 2, EnumEnchantmentType.DIGGER);
/*     */ 
/*     */   
/*  65 */   public static final Enchantment power = new EnchantmentArrowDamage(48, new ResourceLocation("power"), 10);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public static final Enchantment punch = new EnchantmentArrowKnockback(49, new ResourceLocation("punch"), 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public static final Enchantment flame = new EnchantmentArrowFire(50, new ResourceLocation("flame"), 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public static final Enchantment infinity = new EnchantmentArrowInfinite(51, new ResourceLocation("infinity"), 1);
/*  82 */   public static final Enchantment luckOfTheSea = new EnchantmentLootBonus(61, new ResourceLocation("luck_of_the_sea"), 2, EnumEnchantmentType.FISHING_ROD);
/*  83 */   public static final Enchantment lure = new EnchantmentFishingSpeed(62, new ResourceLocation("lure"), 2, EnumEnchantmentType.FISHING_ROD);
/*     */ 
/*     */   
/*     */   public final int effectId;
/*     */ 
/*     */   
/*     */   private final int weight;
/*     */ 
/*     */   
/*     */   public EnumEnchantmentType type;
/*     */ 
/*     */   
/*     */   protected String name;
/*     */ 
/*     */ 
/*     */   
/*     */   public static Enchantment getEnchantmentById(int enchID) {
/* 100 */     return (enchID >= 0 && enchID < enchantmentsList.length) ? enchantmentsList[enchID] : null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Enchantment(int enchID, ResourceLocation enchName, int enchWeight, EnumEnchantmentType enchType) {
/* 105 */     this.effectId = enchID;
/* 106 */     this.weight = enchWeight;
/* 107 */     this.type = enchType;
/*     */     
/* 109 */     if (enchantmentsList[enchID] != null)
/*     */     {
/* 111 */       throw new IllegalArgumentException("Duplicate enchantment id!");
/*     */     }
/*     */ 
/*     */     
/* 115 */     enchantmentsList[enchID] = this;
/* 116 */     locationEnchantments.put(enchName, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Enchantment getEnchantmentByLocation(String location) {
/* 125 */     return locationEnchantments.get(new ResourceLocation(location));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Set<ResourceLocation> func_181077_c() {
/* 130 */     return locationEnchantments.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWeight() {
/* 139 */     return this.weight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinLevel() {
/* 147 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxLevel() {
/* 155 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinEnchantability(int enchantmentLevel) {
/* 163 */     return 1 + enchantmentLevel * 10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnchantability(int enchantmentLevel) {
/* 171 */     return getMinEnchantability(enchantmentLevel) + 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int calcModifierDamage(int level, DamageSource source) {
/* 182 */     return 0;
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
/*     */   public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
/* 195 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canApplyTogether(Enchantment ench) {
/* 205 */     return (this != ench);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enchantment setName(String enchName) {
/* 215 */     this.name = enchName;
/* 216 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 224 */     return "enchantment." + this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTranslatedName(int level) {
/* 235 */     String s = StatCollector.translateToLocal(getName());
/* 236 */     return s + " " + StatCollector.translateToLocal("enchantment.level." + level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canApply(ItemStack stack) {
/* 246 */     return this.type.canEnchantItem(stack.getItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 274 */     List<Enchantment> list = Lists.newArrayList();
/*     */     
/* 276 */     for (Enchantment enchantment : enchantmentsList) {
/*     */       
/* 278 */       if (enchantment != null)
/*     */       {
/* 280 */         list.add(enchantment);
/*     */       }
/*     */     } 
/*     */     
/* 284 */     enchantmentsBookList = list.<Enchantment>toArray(new Enchantment[list.size()]);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\enchantment\Enchantment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
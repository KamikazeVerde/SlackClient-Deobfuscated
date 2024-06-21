/*     */ package com.viaversion.viaversion.protocols.protocol1_9to1_8;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum ArmorType
/*     */ {
/*  25 */   LEATHER_HELMET(1, 298, "minecraft:leather_helmet"),
/*  26 */   LEATHER_CHESTPLATE(3, 299, "minecraft:leather_chestplate"),
/*  27 */   LEATHER_LEGGINGS(2, 300, "minecraft:leather_leggings"),
/*  28 */   LEATHER_BOOTS(1, 301, "minecraft:leather_boots"),
/*  29 */   CHAINMAIL_HELMET(2, 302, "minecraft:chainmail_helmet"),
/*  30 */   CHAINMAIL_CHESTPLATE(5, 303, "minecraft:chainmail_chestplate"),
/*  31 */   CHAINMAIL_LEGGINGS(4, 304, "minecraft:chainmail_leggings"),
/*  32 */   CHAINMAIL_BOOTS(1, 305, "minecraft:chainmail_boots"),
/*  33 */   IRON_HELMET(2, 306, "minecraft:iron_helmet"),
/*  34 */   IRON_CHESTPLATE(6, 307, "minecraft:iron_chestplate"),
/*  35 */   IRON_LEGGINGS(5, 308, "minecraft:iron_leggings"),
/*  36 */   IRON_BOOTS(2, 309, "minecraft:iron_boots"),
/*  37 */   DIAMOND_HELMET(3, 310, "minecraft:diamond_helmet"),
/*  38 */   DIAMOND_CHESTPLATE(8, 311, "minecraft:diamond_chestplate"),
/*  39 */   DIAMOND_LEGGINGS(6, 312, "minecraft:diamond_leggings"),
/*  40 */   DIAMOND_BOOTS(3, 313, "minecraft:diamond_boots"),
/*  41 */   GOLD_HELMET(2, 314, "minecraft:gold_helmet"),
/*  42 */   GOLD_CHESTPLATE(5, 315, "minecraft:gold_chestplate"),
/*  43 */   GOLD_LEGGINGS(3, 316, "minecraft:gold_leggings"),
/*  44 */   GOLD_BOOTS(1, 317, "minecraft:gold_boots"),
/*  45 */   NONE(0, 0, "none");
/*     */   private static final Map<Integer, ArmorType> armor;
/*     */   private final int armorPoints;
/*     */   
/*     */   static {
/*  50 */     armor = new HashMap<>();
/*  51 */     for (ArmorType a : values()) {
/*  52 */       armor.put(Integer.valueOf(a.getId()), a);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private final int id;
/*     */   private final String type;
/*     */   
/*     */   ArmorType(int armorPoints, int id, String type) {
/*  61 */     this.armorPoints = armorPoints;
/*  62 */     this.id = id;
/*  63 */     this.type = type;
/*     */   }
/*     */   
/*     */   public int getArmorPoints() {
/*  67 */     return this.armorPoints;
/*     */   }
/*     */   
/*     */   public String getType() {
/*  71 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArmorType findById(int id) {
/*  81 */     ArmorType type = armor.get(Integer.valueOf(id));
/*  82 */     return (type == null) ? NONE : type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArmorType findByType(String type) {
/*  92 */     for (ArmorType a : values()) {
/*  93 */       if (a.getType().equals(type))
/*  94 */         return a; 
/*  95 */     }  return NONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isArmor(int id) {
/* 105 */     return armor.containsKey(Integer.valueOf(id));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isArmor(String type) {
/* 115 */     for (ArmorType a : values()) {
/* 116 */       if (a.getType().equals(type))
/* 117 */         return true; 
/* 118 */     }  return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/* 127 */     return this.id;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\ArmorType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viaversion.protocols.protocol1_9to1_8;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import java.util.Collections;
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
/*     */ public class ItemRewriter
/*     */ {
/*  33 */   private static final Map<String, Integer> ENTTIY_NAME_TO_ID = new HashMap<>();
/*  34 */   private static final Map<Integer, String> ENTTIY_ID_TO_NAME = new HashMap<>();
/*  35 */   private static final Map<String, Integer> POTION_NAME_TO_ID = new HashMap<>();
/*  36 */   private static final Map<Integer, String> POTION_ID_TO_NAME = new HashMap<>();
/*     */   
/*  38 */   private static final Int2IntMap POTION_INDEX = (Int2IntMap)new Int2IntOpenHashMap(36, 0.99F);
/*     */ 
/*     */   
/*     */   static {
/*  42 */     registerEntity(1, "Item");
/*  43 */     registerEntity(2, "XPOrb");
/*  44 */     registerEntity(7, "ThrownEgg");
/*  45 */     registerEntity(8, "LeashKnot");
/*  46 */     registerEntity(9, "Painting");
/*  47 */     registerEntity(10, "Arrow");
/*  48 */     registerEntity(11, "Snowball");
/*  49 */     registerEntity(12, "Fireball");
/*  50 */     registerEntity(13, "SmallFireball");
/*  51 */     registerEntity(14, "ThrownEnderpearl");
/*  52 */     registerEntity(15, "EyeOfEnderSignal");
/*  53 */     registerEntity(16, "ThrownPotion");
/*  54 */     registerEntity(17, "ThrownExpBottle");
/*  55 */     registerEntity(18, "ItemFrame");
/*  56 */     registerEntity(19, "WitherSkull");
/*  57 */     registerEntity(20, "PrimedTnt");
/*  58 */     registerEntity(21, "FallingSand");
/*  59 */     registerEntity(22, "FireworksRocketEntity");
/*  60 */     registerEntity(30, "ArmorStand");
/*  61 */     registerEntity(40, "MinecartCommandBlock");
/*  62 */     registerEntity(41, "Boat");
/*  63 */     registerEntity(42, "MinecartRideable");
/*  64 */     registerEntity(43, "MinecartChest");
/*  65 */     registerEntity(44, "MinecartFurnace");
/*  66 */     registerEntity(45, "MinecartTNT");
/*  67 */     registerEntity(46, "MinecartHopper");
/*  68 */     registerEntity(47, "MinecartSpawner");
/*  69 */     registerEntity(48, "Mob");
/*  70 */     registerEntity(49, "Monster");
/*  71 */     registerEntity(50, "Creeper");
/*  72 */     registerEntity(51, "Skeleton");
/*  73 */     registerEntity(52, "Spider");
/*  74 */     registerEntity(53, "Giant");
/*  75 */     registerEntity(54, "Zombie");
/*  76 */     registerEntity(55, "Slime");
/*  77 */     registerEntity(56, "Ghast");
/*  78 */     registerEntity(57, "PigZombie");
/*  79 */     registerEntity(58, "Enderman");
/*  80 */     registerEntity(59, "CaveSpider");
/*  81 */     registerEntity(60, "Silverfish");
/*  82 */     registerEntity(61, "Blaze");
/*  83 */     registerEntity(62, "LavaSlime");
/*  84 */     registerEntity(63, "EnderDragon");
/*  85 */     registerEntity(64, "WitherBoss");
/*  86 */     registerEntity(65, "Bat");
/*  87 */     registerEntity(66, "Witch");
/*  88 */     registerEntity(67, "Endermite");
/*  89 */     registerEntity(68, "Guardian");
/*  90 */     registerEntity(90, "Pig");
/*  91 */     registerEntity(91, "Sheep");
/*  92 */     registerEntity(92, "Cow");
/*  93 */     registerEntity(93, "Chicken");
/*  94 */     registerEntity(94, "Squid");
/*  95 */     registerEntity(95, "Wolf");
/*  96 */     registerEntity(96, "MushroomCow");
/*  97 */     registerEntity(97, "SnowMan");
/*  98 */     registerEntity(98, "Ozelot");
/*  99 */     registerEntity(99, "VillagerGolem");
/* 100 */     registerEntity(100, "EntityHorse");
/* 101 */     registerEntity(101, "Rabbit");
/* 102 */     registerEntity(120, "Villager");
/* 103 */     registerEntity(200, "EnderCrystal");
/*     */ 
/*     */     
/* 106 */     registerPotion(-1, "empty");
/* 107 */     registerPotion(0, "water");
/* 108 */     registerPotion(64, "mundane");
/* 109 */     registerPotion(32, "thick");
/* 110 */     registerPotion(16, "awkward");
/*     */     
/* 112 */     registerPotion(8198, "night_vision");
/* 113 */     registerPotion(8262, "long_night_vision");
/*     */     
/* 115 */     registerPotion(8206, "invisibility");
/* 116 */     registerPotion(8270, "long_invisibility");
/*     */     
/* 118 */     registerPotion(8203, "leaping");
/* 119 */     registerPotion(8267, "long_leaping");
/* 120 */     registerPotion(8235, "strong_leaping");
/*     */     
/* 122 */     registerPotion(8195, "fire_resistance");
/* 123 */     registerPotion(8259, "long_fire_resistance");
/*     */     
/* 125 */     registerPotion(8194, "swiftness");
/* 126 */     registerPotion(8258, "long_swiftness");
/* 127 */     registerPotion(8226, "strong_swiftness");
/*     */     
/* 129 */     registerPotion(8202, "slowness");
/* 130 */     registerPotion(8266, "long_slowness");
/*     */     
/* 132 */     registerPotion(8205, "water_breathing");
/* 133 */     registerPotion(8269, "long_water_breathing");
/*     */     
/* 135 */     registerPotion(8261, "healing");
/* 136 */     registerPotion(8229, "strong_healing");
/*     */     
/* 138 */     registerPotion(8204, "harming");
/* 139 */     registerPotion(8236, "strong_harming");
/*     */     
/* 141 */     registerPotion(8196, "poison");
/* 142 */     registerPotion(8260, "long_poison");
/* 143 */     registerPotion(8228, "strong_poison");
/*     */     
/* 145 */     registerPotion(8193, "regeneration");
/* 146 */     registerPotion(8257, "long_regeneration");
/* 147 */     registerPotion(8225, "strong_regeneration");
/*     */     
/* 149 */     registerPotion(8201, "strength");
/* 150 */     registerPotion(8265, "long_strength");
/* 151 */     registerPotion(8233, "strong_strength");
/*     */     
/* 153 */     registerPotion(8200, "weakness");
/* 154 */     registerPotion(8264, "long_weakness");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void toServer(Item item) {
/* 159 */     if (item != null) {
/* 160 */       if (item.identifier() == 383 && item.data() == 0) {
/* 161 */         CompoundTag tag = item.tag();
/* 162 */         int data = 0;
/* 163 */         if (tag != null && tag.get("EntityTag") instanceof CompoundTag) {
/* 164 */           CompoundTag entityTag = (CompoundTag)tag.get("EntityTag");
/* 165 */           if (entityTag.get("id") instanceof StringTag) {
/* 166 */             StringTag id = (StringTag)entityTag.get("id");
/* 167 */             if (ENTTIY_NAME_TO_ID.containsKey(id.getValue()))
/* 168 */               data = ((Integer)ENTTIY_NAME_TO_ID.get(id.getValue())).intValue(); 
/*     */           } 
/* 170 */           tag.remove("EntityTag");
/*     */         } 
/* 172 */         item.setTag(tag);
/* 173 */         item.setData((short)data);
/*     */       } 
/* 175 */       if (item.identifier() == 373) {
/* 176 */         CompoundTag tag = item.tag();
/* 177 */         int data = 0;
/* 178 */         if (tag != null && tag.get("Potion") instanceof StringTag) {
/* 179 */           StringTag potion = (StringTag)tag.get("Potion");
/* 180 */           String potionName = potion.getValue().replace("minecraft:", "");
/* 181 */           if (POTION_NAME_TO_ID.containsKey(potionName)) {
/* 182 */             data = ((Integer)POTION_NAME_TO_ID.get(potionName)).intValue();
/*     */           }
/* 184 */           tag.remove("Potion");
/*     */         } 
/* 186 */         item.setTag(tag);
/* 187 */         item.setData((short)data);
/*     */       } 
/*     */       
/* 190 */       if (item.identifier() == 438) {
/* 191 */         CompoundTag tag = item.tag();
/* 192 */         int data = 0;
/* 193 */         item.setIdentifier(373);
/* 194 */         if (tag != null && tag.get("Potion") instanceof StringTag) {
/* 195 */           StringTag potion = (StringTag)tag.get("Potion");
/* 196 */           String potionName = potion.getValue().replace("minecraft:", "");
/* 197 */           if (POTION_NAME_TO_ID.containsKey(potionName)) {
/* 198 */             data = ((Integer)POTION_NAME_TO_ID.get(potionName)).intValue() + 8192;
/*     */           }
/* 200 */           tag.remove("Potion");
/*     */         } 
/* 202 */         item.setTag(tag);
/* 203 */         item.setData((short)data);
/*     */       } 
/*     */       
/* 206 */       boolean newItem = (item.identifier() >= 198 && item.identifier() <= 212);
/* 207 */       int i = newItem | ((item.identifier() == 397 && item.data() == 5) ? 1 : 0);
/* 208 */       i |= (item.identifier() >= 432 && item.identifier() <= 448) ? 1 : 0;
/* 209 */       if (i != 0) {
/* 210 */         item.setIdentifier(1);
/* 211 */         item.setData((short)0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void rewriteBookToServer(Item item) {
/* 217 */     int id = item.identifier();
/* 218 */     if (id != 387) {
/*     */       return;
/*     */     }
/* 221 */     CompoundTag tag = item.tag();
/* 222 */     ListTag pages = (ListTag)tag.get("pages");
/* 223 */     if (pages == null) {
/*     */       return;
/*     */     }
/* 226 */     for (int i = 0; i < pages.size(); i++) {
/* 227 */       Tag pageTag = pages.get(i);
/* 228 */       if (pageTag instanceof StringTag) {
/*     */ 
/*     */         
/* 231 */         StringTag stag = (StringTag)pageTag;
/* 232 */         String value = stag.getValue();
/* 233 */         if (value.replaceAll(" ", "").isEmpty()) {
/* 234 */           value = "\"" + fixBookSpaceChars(value) + "\"";
/*     */         } else {
/* 236 */           value = fixBookSpaceChars(value);
/*     */         } 
/* 238 */         stag.setValue(value);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private static String fixBookSpaceChars(String str) {
/* 243 */     if (!str.startsWith(" ")) {
/* 244 */       return str;
/*     */     }
/*     */     
/* 247 */     str = "§r" + str;
/* 248 */     return str;
/*     */   }
/*     */   
/*     */   public static void toClient(Item item) {
/* 252 */     if (item != null) {
/* 253 */       if (item.identifier() == 383 && item.data() != 0) {
/* 254 */         CompoundTag tag = item.tag();
/* 255 */         if (tag == null) {
/* 256 */           tag = new CompoundTag();
/*     */         }
/* 258 */         CompoundTag entityTag = new CompoundTag();
/* 259 */         String entityName = ENTTIY_ID_TO_NAME.get(Integer.valueOf(item.data()));
/* 260 */         if (entityName != null) {
/* 261 */           StringTag id = new StringTag(entityName);
/* 262 */           entityTag.put("id", (Tag)id);
/* 263 */           tag.put("EntityTag", (Tag)entityTag);
/*     */         } 
/* 265 */         item.setTag(tag);
/* 266 */         item.setData((short)0);
/*     */       } 
/* 268 */       if (item.identifier() == 373) {
/* 269 */         CompoundTag tag = item.tag();
/* 270 */         if (tag == null) {
/* 271 */           tag = new CompoundTag();
/*     */         }
/* 273 */         if (item.data() >= 16384) {
/* 274 */           item.setIdentifier(438);
/* 275 */           item.setData((short)(item.data() - 8192));
/*     */         } 
/* 277 */         String name = potionNameFromDamage(item.data());
/* 278 */         StringTag potion = new StringTag("minecraft:" + name);
/* 279 */         tag.put("Potion", (Tag)potion);
/* 280 */         item.setTag(tag);
/* 281 */         item.setData((short)0);
/*     */       } 
/* 283 */       if (item.identifier() == 387) {
/* 284 */         CompoundTag tag = item.tag();
/* 285 */         if (tag == null) {
/* 286 */           tag = new CompoundTag();
/*     */         }
/* 288 */         ListTag pages = (ListTag)tag.get("pages");
/* 289 */         if (pages == null) {
/* 290 */           pages = new ListTag(Collections.singletonList(new StringTag(Protocol1_9To1_8.fixJson("").toString())));
/* 291 */           tag.put("pages", (Tag)pages);
/* 292 */           item.setTag(tag);
/*     */           
/*     */           return;
/*     */         } 
/* 296 */         for (int i = 0; i < pages.size(); i++) {
/* 297 */           if (pages.get(i) instanceof StringTag) {
/*     */             
/* 299 */             StringTag page = (StringTag)pages.get(i);
/* 300 */             page.setValue(Protocol1_9To1_8.fixJson(page.getValue()).toString());
/*     */           } 
/* 302 */         }  item.setTag(tag);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String potionNameFromDamage(short damage) {
/* 308 */     String id, cached = POTION_ID_TO_NAME.get(Integer.valueOf(damage));
/* 309 */     if (cached != null) {
/* 310 */       return cached;
/*     */     }
/* 312 */     if (damage == 0) {
/* 313 */       return "water";
/*     */     }
/*     */     
/* 316 */     int effect = damage & 0xF;
/* 317 */     int name = damage & 0x3F;
/* 318 */     boolean enhanced = ((damage & 0x20) > 0);
/* 319 */     boolean extended = ((damage & 0x40) > 0);
/*     */     
/* 321 */     boolean canEnhance = true;
/* 322 */     boolean canExtend = true;
/*     */ 
/*     */     
/* 325 */     switch (effect) {
/*     */       case 1:
/* 327 */         id = "regeneration";
/*     */         break;
/*     */       case 2:
/* 330 */         id = "swiftness";
/*     */         break;
/*     */       case 3:
/* 333 */         id = "fire_resistance";
/* 334 */         canEnhance = false;
/*     */         break;
/*     */       case 4:
/* 337 */         id = "poison";
/*     */         break;
/*     */       case 5:
/* 340 */         id = "healing";
/* 341 */         canExtend = false;
/*     */         break;
/*     */       case 6:
/* 344 */         id = "night_vision";
/* 345 */         canEnhance = false;
/*     */         break;
/*     */       
/*     */       case 8:
/* 349 */         id = "weakness";
/* 350 */         canEnhance = false;
/*     */         break;
/*     */       case 9:
/* 353 */         id = "strength";
/*     */         break;
/*     */       case 10:
/* 356 */         id = "slowness";
/* 357 */         canEnhance = false;
/*     */         break;
/*     */       case 11:
/* 360 */         id = "leaping";
/*     */         break;
/*     */       case 12:
/* 363 */         id = "harming";
/* 364 */         canExtend = false;
/*     */         break;
/*     */       case 13:
/* 367 */         id = "water_breathing";
/* 368 */         canEnhance = false;
/*     */         break;
/*     */       case 14:
/* 371 */         id = "invisibility";
/* 372 */         canEnhance = false;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 377 */         canEnhance = false;
/* 378 */         canExtend = false;
/* 379 */         switch (name) {
/*     */           case 0:
/* 381 */             id = "mundane";
/*     */             break;
/*     */           case 16:
/* 384 */             id = "awkward";
/*     */             break;
/*     */           case 32:
/* 387 */             id = "thick";
/*     */             break;
/*     */         } 
/* 390 */         id = "empty";
/*     */         break;
/*     */     } 
/*     */     
/* 394 */     if (effect > 0) {
/* 395 */       if (canEnhance && enhanced) {
/* 396 */         id = "strong_" + id;
/* 397 */       } else if (canExtend && extended) {
/* 398 */         id = "long_" + id;
/*     */       } 
/*     */     }
/*     */     
/* 402 */     return id;
/*     */   }
/*     */   
/*     */   public static int getNewEffectID(int oldID) {
/* 406 */     if (oldID >= 16384) {
/* 407 */       oldID -= 8192;
/*     */     }
/*     */     
/* 410 */     int index = POTION_INDEX.get(oldID);
/* 411 */     if (index != -1) {
/* 412 */       return index;
/*     */     }
/*     */     
/* 415 */     oldID = ((Integer)POTION_NAME_TO_ID.get(potionNameFromDamage((short)oldID))).intValue(); return 
/* 416 */       ((index = POTION_INDEX.get(oldID)) != -1) ? index : 0;
/*     */   }
/*     */   
/*     */   private static void registerEntity(int id, String name) {
/* 420 */     ENTTIY_ID_TO_NAME.put(Integer.valueOf(id), name);
/* 421 */     ENTTIY_NAME_TO_ID.put(name, Integer.valueOf(id));
/*     */   }
/*     */   
/*     */   private static void registerPotion(int id, String name) {
/* 425 */     POTION_INDEX.put(id, POTION_ID_TO_NAME.size());
/* 426 */     POTION_ID_TO_NAME.put(Integer.valueOf(id), name);
/* 427 */     POTION_NAME_TO_ID.put(name, Integer.valueOf(id));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\ItemRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viaversion.api.minecraft.entities;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Entity1_13Types
/*     */ {
/*     */   public static EntityType getTypeFromId(int typeID, boolean isObject) {
/*     */     Optional<EntityType> type;
/*  35 */     if (isObject) {
/*  36 */       type = ObjectType.getPCEntity(typeID);
/*     */     } else {
/*  38 */       type = EntityType.findById(typeID);
/*     */     } 
/*  40 */     if (!type.isPresent()) {
/*  41 */       Via.getPlatform().getLogger().severe("Could not find 1.13 type id " + typeID + " isObject=" + isObject);
/*  42 */       return EntityType.ENTITY;
/*     */     } 
/*     */     
/*  45 */     return type.get();
/*     */   }
/*     */   
/*     */   public enum EntityType
/*     */     implements EntityType
/*     */   {
/*  51 */     ENTITY(-1),
/*     */     
/*  53 */     AREA_EFFECT_CLOUD(0, ENTITY),
/*  54 */     END_CRYSTAL(16, ENTITY),
/*  55 */     EVOKER_FANGS(20, ENTITY),
/*  56 */     EXPERIENCE_ORB(22, ENTITY),
/*  57 */     EYE_OF_ENDER(23, ENTITY),
/*  58 */     FALLING_BLOCK(24, ENTITY),
/*  59 */     FIREWORK_ROCKET(25, ENTITY),
/*  60 */     ITEM(32, ENTITY),
/*  61 */     LLAMA_SPIT(37, ENTITY),
/*  62 */     TNT(55, ENTITY),
/*  63 */     SHULKER_BULLET(60, ENTITY),
/*  64 */     FISHING_BOBBER(93, ENTITY),
/*     */     
/*  66 */     LIVINGENTITY(-1, ENTITY),
/*  67 */     ARMOR_STAND(1, LIVINGENTITY),
/*  68 */     PLAYER(92, LIVINGENTITY),
/*     */     
/*  70 */     ABSTRACT_INSENTIENT(-1, LIVINGENTITY),
/*  71 */     ENDER_DRAGON(17, ABSTRACT_INSENTIENT),
/*     */     
/*  73 */     ABSTRACT_CREATURE(-1, ABSTRACT_INSENTIENT),
/*     */     
/*  75 */     ABSTRACT_AGEABLE(-1, ABSTRACT_CREATURE),
/*  76 */     VILLAGER(79, ABSTRACT_AGEABLE),
/*     */ 
/*     */     
/*  79 */     ABSTRACT_ANIMAL(-1, ABSTRACT_AGEABLE),
/*  80 */     CHICKEN(7, ABSTRACT_ANIMAL),
/*  81 */     COW(9, ABSTRACT_ANIMAL),
/*  82 */     MOOSHROOM(47, COW),
/*  83 */     PIG(51, ABSTRACT_ANIMAL),
/*  84 */     POLAR_BEAR(54, ABSTRACT_ANIMAL),
/*  85 */     RABBIT(56, ABSTRACT_ANIMAL),
/*  86 */     SHEEP(58, ABSTRACT_ANIMAL),
/*  87 */     TURTLE(73, ABSTRACT_ANIMAL),
/*     */     
/*  89 */     ABSTRACT_TAMEABLE_ANIMAL(-1, ABSTRACT_ANIMAL),
/*  90 */     OCELOT(48, ABSTRACT_TAMEABLE_ANIMAL),
/*  91 */     WOLF(86, ABSTRACT_TAMEABLE_ANIMAL),
/*     */     
/*  93 */     ABSTRACT_PARROT(-1, ABSTRACT_TAMEABLE_ANIMAL),
/*  94 */     PARROT(50, ABSTRACT_PARROT),
/*     */ 
/*     */     
/*  97 */     ABSTRACT_HORSE(-1, ABSTRACT_ANIMAL),
/*  98 */     CHESTED_HORSE(-1, ABSTRACT_HORSE),
/*  99 */     DONKEY(11, CHESTED_HORSE),
/* 100 */     MULE(46, CHESTED_HORSE),
/* 101 */     LLAMA(36, CHESTED_HORSE),
/* 102 */     HORSE(29, ABSTRACT_HORSE),
/* 103 */     SKELETON_HORSE(63, ABSTRACT_HORSE),
/* 104 */     ZOMBIE_HORSE(88, ABSTRACT_HORSE),
/*     */ 
/*     */     
/* 107 */     ABSTRACT_GOLEM(-1, ABSTRACT_CREATURE),
/* 108 */     SNOW_GOLEM(66, ABSTRACT_GOLEM),
/* 109 */     IRON_GOLEM(80, ABSTRACT_GOLEM),
/* 110 */     SHULKER(59, ABSTRACT_GOLEM),
/*     */ 
/*     */     
/* 113 */     ABSTRACT_FISHES(-1, ABSTRACT_CREATURE),
/* 114 */     COD(8, ABSTRACT_FISHES),
/* 115 */     PUFFERFISH(52, ABSTRACT_FISHES),
/* 116 */     SALMON(57, ABSTRACT_FISHES),
/* 117 */     TROPICAL_FISH(72, ABSTRACT_FISHES),
/*     */ 
/*     */     
/* 120 */     ABSTRACT_MONSTER(-1, ABSTRACT_CREATURE),
/* 121 */     BLAZE(4, ABSTRACT_MONSTER),
/* 122 */     CREEPER(10, ABSTRACT_MONSTER),
/* 123 */     ENDERMITE(19, ABSTRACT_MONSTER),
/* 124 */     ENDERMAN(18, ABSTRACT_MONSTER),
/* 125 */     GIANT(27, ABSTRACT_MONSTER),
/* 126 */     SILVERFISH(61, ABSTRACT_MONSTER),
/* 127 */     VEX(78, ABSTRACT_MONSTER),
/* 128 */     WITCH(82, ABSTRACT_MONSTER),
/* 129 */     WITHER(83, ABSTRACT_MONSTER),
/*     */ 
/*     */     
/* 132 */     ABSTRACT_ILLAGER_BASE(-1, ABSTRACT_MONSTER),
/* 133 */     ABSTRACT_EVO_ILLU_ILLAGER(-1, ABSTRACT_ILLAGER_BASE),
/* 134 */     EVOKER(21, ABSTRACT_EVO_ILLU_ILLAGER),
/* 135 */     ILLUSIONER(31, ABSTRACT_EVO_ILLU_ILLAGER),
/* 136 */     VINDICATOR(81, ABSTRACT_ILLAGER_BASE),
/*     */ 
/*     */     
/* 139 */     ABSTRACT_SKELETON(-1, ABSTRACT_MONSTER),
/* 140 */     SKELETON(62, ABSTRACT_SKELETON),
/* 141 */     STRAY(71, ABSTRACT_SKELETON),
/* 142 */     WITHER_SKELETON(84, ABSTRACT_SKELETON),
/*     */ 
/*     */     
/* 145 */     GUARDIAN(28, ABSTRACT_MONSTER),
/* 146 */     ELDER_GUARDIAN(15, GUARDIAN),
/*     */ 
/*     */     
/* 149 */     SPIDER(69, ABSTRACT_MONSTER),
/* 150 */     CAVE_SPIDER(6, SPIDER),
/*     */ 
/*     */     
/* 153 */     ZOMBIE(87, ABSTRACT_MONSTER),
/* 154 */     DROWNED(14, ZOMBIE),
/* 155 */     HUSK(30, ZOMBIE),
/* 156 */     ZOMBIE_PIGMAN(53, ZOMBIE),
/* 157 */     ZOMBIE_VILLAGER(89, ZOMBIE),
/*     */ 
/*     */     
/* 160 */     ABSTRACT_FLYING(-1, ABSTRACT_INSENTIENT),
/* 161 */     GHAST(26, ABSTRACT_FLYING),
/* 162 */     PHANTOM(90, ABSTRACT_FLYING),
/*     */     
/* 164 */     ABSTRACT_AMBIENT(-1, ABSTRACT_INSENTIENT),
/* 165 */     BAT(3, ABSTRACT_AMBIENT),
/*     */     
/* 167 */     ABSTRACT_WATERMOB(-1, ABSTRACT_INSENTIENT),
/* 168 */     SQUID(70, ABSTRACT_WATERMOB),
/* 169 */     DOLPHIN(12, ABSTRACT_WATERMOB),
/*     */ 
/*     */     
/* 172 */     SLIME(64, ABSTRACT_INSENTIENT),
/* 173 */     MAGMA_CUBE(38, SLIME),
/*     */ 
/*     */     
/* 176 */     ABSTRACT_HANGING(-1, ENTITY),
/* 177 */     LEASH_KNOT(35, ABSTRACT_HANGING),
/* 178 */     ITEM_FRAME(33, ABSTRACT_HANGING),
/* 179 */     PAINTING(49, ABSTRACT_HANGING),
/*     */     
/* 181 */     ABSTRACT_LIGHTNING(-1, ENTITY),
/* 182 */     LIGHTNING_BOLT(91, ABSTRACT_LIGHTNING),
/*     */ 
/*     */     
/* 185 */     ABSTRACT_ARROW(-1, ENTITY),
/* 186 */     ARROW(2, ABSTRACT_ARROW),
/* 187 */     SPECTRAL_ARROW(68, ABSTRACT_ARROW),
/* 188 */     TRIDENT(94, ABSTRACT_ARROW),
/*     */ 
/*     */     
/* 191 */     ABSTRACT_FIREBALL(-1, ENTITY),
/* 192 */     DRAGON_FIREBALL(13, ABSTRACT_FIREBALL),
/* 193 */     FIREBALL(34, ABSTRACT_FIREBALL),
/* 194 */     SMALL_FIREBALL(65, ABSTRACT_FIREBALL),
/* 195 */     WITHER_SKULL(85, ABSTRACT_FIREBALL),
/*     */ 
/*     */     
/* 198 */     PROJECTILE_ABSTRACT(-1, ENTITY),
/* 199 */     SNOWBALL(67, PROJECTILE_ABSTRACT),
/* 200 */     ENDER_PEARL(75, PROJECTILE_ABSTRACT),
/* 201 */     EGG(74, PROJECTILE_ABSTRACT),
/* 202 */     POTION(77, PROJECTILE_ABSTRACT),
/* 203 */     EXPERIENCE_BOTTLE(76, PROJECTILE_ABSTRACT),
/*     */ 
/*     */     
/* 206 */     MINECART_ABSTRACT(-1, ENTITY),
/* 207 */     CHESTED_MINECART_ABSTRACT(-1, MINECART_ABSTRACT),
/* 208 */     CHEST_MINECART(40, CHESTED_MINECART_ABSTRACT),
/* 209 */     HOPPER_MINECART(43, CHESTED_MINECART_ABSTRACT),
/* 210 */     MINECART(39, MINECART_ABSTRACT),
/* 211 */     FURNACE_MINECART(42, MINECART_ABSTRACT),
/* 212 */     COMMAND_BLOCK_MINECART(41, MINECART_ABSTRACT),
/* 213 */     TNT_MINECART(45, MINECART_ABSTRACT),
/* 214 */     SPAWNER_MINECART(44, MINECART_ABSTRACT),
/* 215 */     BOAT(5, ENTITY);
/*     */     
/* 217 */     private static final Map<Integer, EntityType> TYPES = new HashMap<>();
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
/*     */     private final int id;
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
/*     */     private final EntityType parent;
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
/*     */     static {
/* 253 */       for (EntityType type : values())
/* 254 */         TYPES.put(Integer.valueOf(type.id), type); 
/*     */     }
/*     */     EntityType(int id) { this.id = id;
/*     */       this.parent = null; }
/*     */     EntityType(int id, EntityType parent) { this.id = id;
/* 259 */       this.parent = parent; } public int getId() { return this.id; } public static Optional<EntityType> findById(int id) { if (id == -1)
/* 260 */         return Optional.empty(); 
/* 261 */       return Optional.ofNullable(TYPES.get(Integer.valueOf(id))); } public EntityType getParent() { return this.parent; } public String identifier() {
/*     */       throw new UnsupportedOperationException();
/*     */     } public boolean isAbstractType() {
/*     */       return (this.id != -1);
/*     */     }
/* 266 */   } public enum ObjectType implements ObjectType { BOAT(1, Entity1_13Types.EntityType.BOAT),
/* 267 */     ITEM(2, Entity1_13Types.EntityType.ITEM),
/* 268 */     AREA_EFFECT_CLOUD(3, Entity1_13Types.EntityType.AREA_EFFECT_CLOUD),
/* 269 */     MINECART(10, Entity1_13Types.EntityType.MINECART),
/* 270 */     TNT_PRIMED(50, Entity1_13Types.EntityType.TNT),
/* 271 */     ENDER_CRYSTAL(51, Entity1_13Types.EntityType.END_CRYSTAL),
/* 272 */     TIPPED_ARROW(60, Entity1_13Types.EntityType.ARROW),
/* 273 */     SNOWBALL(61, Entity1_13Types.EntityType.SNOWBALL),
/* 274 */     EGG(62, Entity1_13Types.EntityType.EGG),
/* 275 */     FIREBALL(63, Entity1_13Types.EntityType.FIREBALL),
/* 276 */     SMALL_FIREBALL(64, Entity1_13Types.EntityType.SMALL_FIREBALL),
/* 277 */     ENDER_PEARL(65, Entity1_13Types.EntityType.ENDER_PEARL),
/* 278 */     WITHER_SKULL(66, Entity1_13Types.EntityType.WITHER_SKULL),
/* 279 */     SHULKER_BULLET(67, Entity1_13Types.EntityType.SHULKER_BULLET),
/* 280 */     LLAMA_SPIT(68, Entity1_13Types.EntityType.LLAMA_SPIT),
/* 281 */     FALLING_BLOCK(70, Entity1_13Types.EntityType.FALLING_BLOCK),
/* 282 */     ITEM_FRAME(71, Entity1_13Types.EntityType.ITEM_FRAME),
/* 283 */     EYE_OF_ENDER(72, Entity1_13Types.EntityType.EYE_OF_ENDER),
/* 284 */     POTION(73, Entity1_13Types.EntityType.POTION),
/* 285 */     EXPERIENCE_BOTTLE(75, Entity1_13Types.EntityType.EXPERIENCE_BOTTLE),
/* 286 */     FIREWORK_ROCKET(76, Entity1_13Types.EntityType.FIREWORK_ROCKET),
/* 287 */     LEASH(77, Entity1_13Types.EntityType.LEASH_KNOT),
/* 288 */     ARMOR_STAND(78, Entity1_13Types.EntityType.ARMOR_STAND),
/* 289 */     EVOKER_FANGS(79, Entity1_13Types.EntityType.EVOKER_FANGS),
/* 290 */     FISHIHNG_HOOK(90, Entity1_13Types.EntityType.FISHING_BOBBER),
/* 291 */     SPECTRAL_ARROW(91, Entity1_13Types.EntityType.SPECTRAL_ARROW),
/* 292 */     DRAGON_FIREBALL(93, Entity1_13Types.EntityType.DRAGON_FIREBALL),
/* 293 */     TRIDENT(94, Entity1_13Types.EntityType.TRIDENT);
/*     */     
/* 295 */     private static final Map<Integer, ObjectType> TYPES = new HashMap<>();
/*     */     
/*     */     private final int id;
/*     */     private final Entity1_13Types.EntityType type;
/*     */     
/*     */     static {
/* 301 */       for (ObjectType type : values()) {
/* 302 */         TYPES.put(Integer.valueOf(type.id), type);
/*     */       }
/*     */     }
/*     */     
/*     */     ObjectType(int id, Entity1_13Types.EntityType type) {
/* 307 */       this.id = id;
/* 308 */       this.type = type;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getId() {
/* 313 */       return this.id;
/*     */     }
/*     */ 
/*     */     
/*     */     public Entity1_13Types.EntityType getType() {
/* 318 */       return this.type;
/*     */     }
/*     */     
/*     */     public static Optional<ObjectType> findById(int id) {
/* 322 */       if (id == -1)
/* 323 */         return Optional.empty(); 
/* 324 */       return Optional.ofNullable(TYPES.get(Integer.valueOf(id)));
/*     */     }
/*     */     
/*     */     public static Optional<Entity1_13Types.EntityType> getPCEntity(int id) {
/* 328 */       Optional<ObjectType> output = findById(id);
/* 329 */       if (!output.isPresent())
/* 330 */         return Optional.empty(); 
/* 331 */       return Optional.of(((ObjectType)output.get()).type);
/*     */     }
/*     */     
/*     */     public static Optional<ObjectType> fromEntityType(Entity1_13Types.EntityType type) {
/* 335 */       for (ObjectType ent : values()) {
/* 336 */         if (ent.type == type)
/* 337 */           return Optional.of(ent); 
/* 338 */       }  return Optional.empty();
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\entities\Entity1_13Types.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
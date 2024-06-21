/*     */ package com.viaversion.viaversion.api.minecraft.entities;
/*     */ 
/*     */ import com.viaversion.viaversion.util.EntityTypeUtil;
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
/*     */ public enum Entity1_17Types
/*     */   implements EntityType
/*     */ {
/*  29 */   ENTITY(-1),
/*     */   
/*  31 */   AREA_EFFECT_CLOUD(0, ENTITY),
/*  32 */   END_CRYSTAL(19, ENTITY),
/*  33 */   EVOKER_FANGS(24, ENTITY),
/*  34 */   EXPERIENCE_ORB(25, ENTITY),
/*  35 */   EYE_OF_ENDER(26, ENTITY),
/*  36 */   FALLING_BLOCK(27, ENTITY),
/*  37 */   FIREWORK_ROCKET(28, ENTITY),
/*  38 */   ITEM(41, ENTITY),
/*  39 */   LLAMA_SPIT(47, ENTITY),
/*  40 */   TNT(69, ENTITY),
/*  41 */   SHULKER_BULLET(76, ENTITY),
/*  42 */   FISHING_BOBBER(112, ENTITY),
/*     */   
/*  44 */   LIVINGENTITY(-1, ENTITY),
/*  45 */   ARMOR_STAND(1, LIVINGENTITY),
/*  46 */   MARKER(49, ENTITY),
/*  47 */   PLAYER(111, LIVINGENTITY),
/*     */   
/*  49 */   ABSTRACT_INSENTIENT(-1, LIVINGENTITY),
/*  50 */   ENDER_DRAGON(20, ABSTRACT_INSENTIENT),
/*     */   
/*  52 */   BEE(5, ABSTRACT_INSENTIENT),
/*     */   
/*  54 */   ABSTRACT_CREATURE(-1, ABSTRACT_INSENTIENT),
/*     */   
/*  56 */   ABSTRACT_AGEABLE(-1, ABSTRACT_CREATURE),
/*  57 */   VILLAGER(98, ABSTRACT_AGEABLE),
/*  58 */   WANDERING_TRADER(100, ABSTRACT_AGEABLE),
/*     */ 
/*     */   
/*  61 */   ABSTRACT_ANIMAL(-1, ABSTRACT_AGEABLE),
/*  62 */   AXOLOTL(3, ABSTRACT_ANIMAL),
/*  63 */   DOLPHIN(14, ABSTRACT_INSENTIENT),
/*  64 */   CHICKEN(10, ABSTRACT_ANIMAL),
/*  65 */   COW(12, ABSTRACT_ANIMAL),
/*  66 */   MOOSHROOM(58, COW),
/*  67 */   PANDA(61, ABSTRACT_INSENTIENT),
/*  68 */   PIG(64, ABSTRACT_ANIMAL),
/*  69 */   POLAR_BEAR(68, ABSTRACT_ANIMAL),
/*  70 */   RABBIT(71, ABSTRACT_ANIMAL),
/*  71 */   SHEEP(74, ABSTRACT_ANIMAL),
/*  72 */   TURTLE(96, ABSTRACT_ANIMAL),
/*  73 */   FOX(29, ABSTRACT_ANIMAL),
/*  74 */   GOAT(34, ABSTRACT_ANIMAL),
/*     */   
/*  76 */   ABSTRACT_TAMEABLE_ANIMAL(-1, ABSTRACT_ANIMAL),
/*  77 */   CAT(8, ABSTRACT_TAMEABLE_ANIMAL),
/*  78 */   OCELOT(59, ABSTRACT_TAMEABLE_ANIMAL),
/*  79 */   WOLF(105, ABSTRACT_TAMEABLE_ANIMAL),
/*     */   
/*  81 */   ABSTRACT_PARROT(-1, ABSTRACT_TAMEABLE_ANIMAL),
/*  82 */   PARROT(62, ABSTRACT_PARROT),
/*     */ 
/*     */   
/*  85 */   ABSTRACT_HORSE(-1, ABSTRACT_ANIMAL),
/*  86 */   CHESTED_HORSE(-1, ABSTRACT_HORSE),
/*  87 */   DONKEY(15, CHESTED_HORSE),
/*  88 */   MULE(57, CHESTED_HORSE),
/*  89 */   LLAMA(46, CHESTED_HORSE),
/*  90 */   TRADER_LLAMA(94, CHESTED_HORSE),
/*  91 */   HORSE(37, ABSTRACT_HORSE),
/*  92 */   SKELETON_HORSE(79, ABSTRACT_HORSE),
/*  93 */   ZOMBIE_HORSE(108, ABSTRACT_HORSE),
/*     */ 
/*     */   
/*  96 */   ABSTRACT_GOLEM(-1, ABSTRACT_CREATURE),
/*  97 */   SNOW_GOLEM(82, ABSTRACT_GOLEM),
/*  98 */   IRON_GOLEM(40, ABSTRACT_GOLEM),
/*  99 */   SHULKER(75, ABSTRACT_GOLEM),
/*     */ 
/*     */   
/* 102 */   ABSTRACT_FISHES(-1, ABSTRACT_CREATURE),
/* 103 */   COD(11, ABSTRACT_FISHES),
/* 104 */   PUFFERFISH(70, ABSTRACT_FISHES),
/* 105 */   SALMON(73, ABSTRACT_FISHES),
/* 106 */   TROPICAL_FISH(95, ABSTRACT_FISHES),
/*     */ 
/*     */   
/* 109 */   ABSTRACT_MONSTER(-1, ABSTRACT_CREATURE),
/* 110 */   BLAZE(6, ABSTRACT_MONSTER),
/* 111 */   CREEPER(13, ABSTRACT_MONSTER),
/* 112 */   ENDERMITE(22, ABSTRACT_MONSTER),
/* 113 */   ENDERMAN(21, ABSTRACT_MONSTER),
/* 114 */   GIANT(31, ABSTRACT_MONSTER),
/* 115 */   SILVERFISH(77, ABSTRACT_MONSTER),
/* 116 */   VEX(97, ABSTRACT_MONSTER),
/* 117 */   WITCH(101, ABSTRACT_MONSTER),
/* 118 */   WITHER(102, ABSTRACT_MONSTER),
/* 119 */   RAVAGER(72, ABSTRACT_MONSTER),
/*     */   
/* 121 */   ABSTRACT_PIGLIN(-1, ABSTRACT_MONSTER),
/*     */   
/* 123 */   PIGLIN(65, ABSTRACT_PIGLIN),
/* 124 */   PIGLIN_BRUTE(66, ABSTRACT_PIGLIN),
/*     */   
/* 126 */   HOGLIN(36, ABSTRACT_ANIMAL),
/* 127 */   STRIDER(88, ABSTRACT_ANIMAL),
/* 128 */   ZOGLIN(106, ABSTRACT_MONSTER),
/*     */ 
/*     */   
/* 131 */   ABSTRACT_ILLAGER_BASE(-1, ABSTRACT_MONSTER),
/* 132 */   ABSTRACT_EVO_ILLU_ILLAGER(-1, ABSTRACT_ILLAGER_BASE),
/* 133 */   EVOKER(23, ABSTRACT_EVO_ILLU_ILLAGER),
/* 134 */   ILLUSIONER(39, ABSTRACT_EVO_ILLU_ILLAGER),
/* 135 */   VINDICATOR(99, ABSTRACT_ILLAGER_BASE),
/* 136 */   PILLAGER(67, ABSTRACT_ILLAGER_BASE),
/*     */ 
/*     */   
/* 139 */   ABSTRACT_SKELETON(-1, ABSTRACT_MONSTER),
/* 140 */   SKELETON(78, ABSTRACT_SKELETON),
/* 141 */   STRAY(87, ABSTRACT_SKELETON),
/* 142 */   WITHER_SKELETON(103, ABSTRACT_SKELETON),
/*     */ 
/*     */   
/* 145 */   GUARDIAN(35, ABSTRACT_MONSTER),
/* 146 */   ELDER_GUARDIAN(18, GUARDIAN),
/*     */ 
/*     */   
/* 149 */   SPIDER(85, ABSTRACT_MONSTER),
/* 150 */   CAVE_SPIDER(9, SPIDER),
/*     */ 
/*     */   
/* 153 */   ZOMBIE(107, ABSTRACT_MONSTER),
/* 154 */   DROWNED(17, ZOMBIE),
/* 155 */   HUSK(38, ZOMBIE),
/* 156 */   ZOMBIFIED_PIGLIN(110, ZOMBIE),
/* 157 */   ZOMBIE_VILLAGER(109, ZOMBIE),
/*     */ 
/*     */   
/* 160 */   ABSTRACT_FLYING(-1, ABSTRACT_INSENTIENT),
/* 161 */   GHAST(30, ABSTRACT_FLYING),
/* 162 */   PHANTOM(63, ABSTRACT_FLYING),
/*     */   
/* 164 */   ABSTRACT_AMBIENT(-1, ABSTRACT_INSENTIENT),
/* 165 */   BAT(4, ABSTRACT_AMBIENT),
/*     */   
/* 167 */   ABSTRACT_WATERMOB(-1, ABSTRACT_INSENTIENT),
/* 168 */   SQUID(86, ABSTRACT_WATERMOB),
/* 169 */   GLOW_SQUID(33, SQUID),
/*     */ 
/*     */   
/* 172 */   SLIME(80, ABSTRACT_INSENTIENT),
/* 173 */   MAGMA_CUBE(48, SLIME),
/*     */ 
/*     */   
/* 176 */   ABSTRACT_HANGING(-1, ENTITY),
/* 177 */   LEASH_KNOT(44, ABSTRACT_HANGING),
/* 178 */   ITEM_FRAME(42, ABSTRACT_HANGING),
/* 179 */   GLOW_ITEM_FRAME(32, ITEM_FRAME),
/* 180 */   PAINTING(60, ABSTRACT_HANGING),
/*     */   
/* 182 */   ABSTRACT_LIGHTNING(-1, ENTITY),
/* 183 */   LIGHTNING_BOLT(45, ABSTRACT_LIGHTNING),
/*     */ 
/*     */   
/* 186 */   ABSTRACT_ARROW(-1, ENTITY),
/* 187 */   ARROW(2, ABSTRACT_ARROW),
/* 188 */   SPECTRAL_ARROW(84, ABSTRACT_ARROW),
/* 189 */   TRIDENT(93, ABSTRACT_ARROW),
/*     */ 
/*     */   
/* 192 */   ABSTRACT_FIREBALL(-1, ENTITY),
/* 193 */   DRAGON_FIREBALL(16, ABSTRACT_FIREBALL),
/* 194 */   FIREBALL(43, ABSTRACT_FIREBALL),
/* 195 */   SMALL_FIREBALL(81, ABSTRACT_FIREBALL),
/* 196 */   WITHER_SKULL(104, ABSTRACT_FIREBALL),
/*     */ 
/*     */   
/* 199 */   PROJECTILE_ABSTRACT(-1, ENTITY),
/* 200 */   SNOWBALL(83, PROJECTILE_ABSTRACT),
/* 201 */   ENDER_PEARL(90, PROJECTILE_ABSTRACT),
/* 202 */   EGG(89, PROJECTILE_ABSTRACT),
/* 203 */   POTION(92, PROJECTILE_ABSTRACT),
/* 204 */   EXPERIENCE_BOTTLE(91, PROJECTILE_ABSTRACT),
/*     */ 
/*     */   
/* 207 */   MINECART_ABSTRACT(-1, ENTITY),
/* 208 */   CHESTED_MINECART_ABSTRACT(-1, MINECART_ABSTRACT),
/* 209 */   CHEST_MINECART(51, CHESTED_MINECART_ABSTRACT),
/* 210 */   HOPPER_MINECART(54, CHESTED_MINECART_ABSTRACT),
/* 211 */   MINECART(50, MINECART_ABSTRACT),
/* 212 */   FURNACE_MINECART(53, MINECART_ABSTRACT),
/* 213 */   COMMAND_BLOCK_MINECART(52, MINECART_ABSTRACT),
/* 214 */   TNT_MINECART(56, MINECART_ABSTRACT),
/* 215 */   SPAWNER_MINECART(55, MINECART_ABSTRACT),
/* 216 */   BOAT(7, ENTITY);
/*     */   
/*     */   private static final EntityType[] TYPES;
/*     */   
/*     */   private final int id;
/*     */   private final EntityType parent;
/*     */   
/*     */   Entity1_17Types(int id) {
/* 224 */     this.id = id;
/* 225 */     this.parent = null;
/*     */   }
/*     */   
/*     */   Entity1_17Types(int id, EntityType parent) {
/* 229 */     this.id = id;
/* 230 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 235 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType getParent() {
/* 240 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public String identifier() {
/* 245 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAbstractType() {
/* 250 */     return (this.id != -1);
/*     */   }
/*     */   
/*     */   static {
/* 254 */     TYPES = EntityTypeUtil.toOrderedArray((EntityType[])values());
/*     */   }
/*     */   
/*     */   public static EntityType getTypeFromId(int typeId) {
/* 258 */     return EntityTypeUtil.getTypeFromId(TYPES, typeId, ENTITY);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\entities\Entity1_17Types.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
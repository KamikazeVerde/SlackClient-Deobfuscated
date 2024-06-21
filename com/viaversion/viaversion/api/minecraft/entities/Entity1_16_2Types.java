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
/*     */ public enum Entity1_16_2Types
/*     */   implements EntityType
/*     */ {
/*  29 */   ENTITY(-1),
/*     */   
/*  31 */   AREA_EFFECT_CLOUD(0, ENTITY),
/*  32 */   END_CRYSTAL(18, ENTITY),
/*  33 */   EVOKER_FANGS(23, ENTITY),
/*  34 */   EXPERIENCE_ORB(24, ENTITY),
/*  35 */   EYE_OF_ENDER(25, ENTITY),
/*  36 */   FALLING_BLOCK(26, ENTITY),
/*  37 */   FIREWORK_ROCKET(27, ENTITY),
/*  38 */   ITEM(37, ENTITY),
/*  39 */   LLAMA_SPIT(43, ENTITY),
/*  40 */   TNT(64, ENTITY),
/*  41 */   SHULKER_BULLET(71, ENTITY),
/*  42 */   FISHING_BOBBER(107, ENTITY),
/*     */   
/*  44 */   LIVINGENTITY(-1, ENTITY),
/*  45 */   ARMOR_STAND(1, LIVINGENTITY),
/*  46 */   PLAYER(106, LIVINGENTITY),
/*     */   
/*  48 */   ABSTRACT_INSENTIENT(-1, LIVINGENTITY),
/*  49 */   ENDER_DRAGON(19, ABSTRACT_INSENTIENT),
/*     */   
/*  51 */   BEE(4, ABSTRACT_INSENTIENT),
/*     */   
/*  53 */   ABSTRACT_CREATURE(-1, ABSTRACT_INSENTIENT),
/*     */   
/*  55 */   ABSTRACT_AGEABLE(-1, ABSTRACT_CREATURE),
/*  56 */   VILLAGER(93, ABSTRACT_AGEABLE),
/*  57 */   WANDERING_TRADER(95, ABSTRACT_AGEABLE),
/*     */ 
/*     */   
/*  60 */   ABSTRACT_ANIMAL(-1, ABSTRACT_AGEABLE),
/*  61 */   DOLPHIN(13, ABSTRACT_INSENTIENT),
/*  62 */   CHICKEN(9, ABSTRACT_ANIMAL),
/*  63 */   COW(11, ABSTRACT_ANIMAL),
/*  64 */   MOOSHROOM(53, COW),
/*  65 */   PANDA(56, ABSTRACT_INSENTIENT),
/*  66 */   PIG(59, ABSTRACT_ANIMAL),
/*  67 */   POLAR_BEAR(63, ABSTRACT_ANIMAL),
/*  68 */   RABBIT(66, ABSTRACT_ANIMAL),
/*  69 */   SHEEP(69, ABSTRACT_ANIMAL),
/*  70 */   TURTLE(91, ABSTRACT_ANIMAL),
/*  71 */   FOX(28, ABSTRACT_ANIMAL),
/*     */   
/*  73 */   ABSTRACT_TAMEABLE_ANIMAL(-1, ABSTRACT_ANIMAL),
/*  74 */   CAT(7, ABSTRACT_TAMEABLE_ANIMAL),
/*  75 */   OCELOT(54, ABSTRACT_TAMEABLE_ANIMAL),
/*  76 */   WOLF(100, ABSTRACT_TAMEABLE_ANIMAL),
/*     */   
/*  78 */   ABSTRACT_PARROT(-1, ABSTRACT_TAMEABLE_ANIMAL),
/*  79 */   PARROT(57, ABSTRACT_PARROT),
/*     */ 
/*     */   
/*  82 */   ABSTRACT_HORSE(-1, ABSTRACT_ANIMAL),
/*  83 */   CHESTED_HORSE(-1, ABSTRACT_HORSE),
/*  84 */   DONKEY(14, CHESTED_HORSE),
/*  85 */   MULE(52, CHESTED_HORSE),
/*  86 */   LLAMA(42, CHESTED_HORSE),
/*  87 */   TRADER_LLAMA(89, CHESTED_HORSE),
/*  88 */   HORSE(33, ABSTRACT_HORSE),
/*  89 */   SKELETON_HORSE(74, ABSTRACT_HORSE),
/*  90 */   ZOMBIE_HORSE(103, ABSTRACT_HORSE),
/*     */ 
/*     */   
/*  93 */   ABSTRACT_GOLEM(-1, ABSTRACT_CREATURE),
/*  94 */   SNOW_GOLEM(77, ABSTRACT_GOLEM),
/*  95 */   IRON_GOLEM(36, ABSTRACT_GOLEM),
/*  96 */   SHULKER(70, ABSTRACT_GOLEM),
/*     */ 
/*     */   
/*  99 */   ABSTRACT_FISHES(-1, ABSTRACT_CREATURE),
/* 100 */   COD(10, ABSTRACT_FISHES),
/* 101 */   PUFFERFISH(65, ABSTRACT_FISHES),
/* 102 */   SALMON(68, ABSTRACT_FISHES),
/* 103 */   TROPICAL_FISH(90, ABSTRACT_FISHES),
/*     */ 
/*     */   
/* 106 */   ABSTRACT_MONSTER(-1, ABSTRACT_CREATURE),
/* 107 */   BLAZE(5, ABSTRACT_MONSTER),
/* 108 */   CREEPER(12, ABSTRACT_MONSTER),
/* 109 */   ENDERMITE(21, ABSTRACT_MONSTER),
/* 110 */   ENDERMAN(20, ABSTRACT_MONSTER),
/* 111 */   GIANT(30, ABSTRACT_MONSTER),
/* 112 */   SILVERFISH(72, ABSTRACT_MONSTER),
/* 113 */   VEX(92, ABSTRACT_MONSTER),
/* 114 */   WITCH(96, ABSTRACT_MONSTER),
/* 115 */   WITHER(97, ABSTRACT_MONSTER),
/* 116 */   RAVAGER(67, ABSTRACT_MONSTER),
/*     */   
/* 118 */   ABSTRACT_PIGLIN(-1, ABSTRACT_MONSTER),
/*     */   
/* 120 */   PIGLIN(60, ABSTRACT_PIGLIN),
/* 121 */   PIGLIN_BRUTE(61, ABSTRACT_PIGLIN),
/*     */   
/* 123 */   HOGLIN(32, ABSTRACT_ANIMAL),
/* 124 */   STRIDER(83, ABSTRACT_ANIMAL),
/* 125 */   ZOGLIN(101, ABSTRACT_MONSTER),
/*     */ 
/*     */   
/* 128 */   ABSTRACT_ILLAGER_BASE(-1, ABSTRACT_MONSTER),
/* 129 */   ABSTRACT_EVO_ILLU_ILLAGER(-1, ABSTRACT_ILLAGER_BASE),
/* 130 */   EVOKER(22, ABSTRACT_EVO_ILLU_ILLAGER),
/* 131 */   ILLUSIONER(35, ABSTRACT_EVO_ILLU_ILLAGER),
/* 132 */   VINDICATOR(94, ABSTRACT_ILLAGER_BASE),
/* 133 */   PILLAGER(62, ABSTRACT_ILLAGER_BASE),
/*     */ 
/*     */   
/* 136 */   ABSTRACT_SKELETON(-1, ABSTRACT_MONSTER),
/* 137 */   SKELETON(73, ABSTRACT_SKELETON),
/* 138 */   STRAY(82, ABSTRACT_SKELETON),
/* 139 */   WITHER_SKELETON(98, ABSTRACT_SKELETON),
/*     */ 
/*     */   
/* 142 */   GUARDIAN(31, ABSTRACT_MONSTER),
/* 143 */   ELDER_GUARDIAN(17, GUARDIAN),
/*     */ 
/*     */   
/* 146 */   SPIDER(80, ABSTRACT_MONSTER),
/* 147 */   CAVE_SPIDER(8, SPIDER),
/*     */ 
/*     */   
/* 150 */   ZOMBIE(102, ABSTRACT_MONSTER),
/* 151 */   DROWNED(16, ZOMBIE),
/* 152 */   HUSK(34, ZOMBIE),
/* 153 */   ZOMBIFIED_PIGLIN(105, ZOMBIE),
/* 154 */   ZOMBIE_VILLAGER(104, ZOMBIE),
/*     */ 
/*     */   
/* 157 */   ABSTRACT_FLYING(-1, ABSTRACT_INSENTIENT),
/* 158 */   GHAST(29, ABSTRACT_FLYING),
/* 159 */   PHANTOM(58, ABSTRACT_FLYING),
/*     */   
/* 161 */   ABSTRACT_AMBIENT(-1, ABSTRACT_INSENTIENT),
/* 162 */   BAT(3, ABSTRACT_AMBIENT),
/*     */   
/* 164 */   ABSTRACT_WATERMOB(-1, ABSTRACT_INSENTIENT),
/* 165 */   SQUID(81, ABSTRACT_WATERMOB),
/*     */ 
/*     */   
/* 168 */   SLIME(75, ABSTRACT_INSENTIENT),
/* 169 */   MAGMA_CUBE(44, SLIME),
/*     */ 
/*     */   
/* 172 */   ABSTRACT_HANGING(-1, ENTITY),
/* 173 */   LEASH_KNOT(40, ABSTRACT_HANGING),
/* 174 */   ITEM_FRAME(38, ABSTRACT_HANGING),
/* 175 */   PAINTING(55, ABSTRACT_HANGING),
/*     */   
/* 177 */   ABSTRACT_LIGHTNING(-1, ENTITY),
/* 178 */   LIGHTNING_BOLT(41, ABSTRACT_LIGHTNING),
/*     */ 
/*     */   
/* 181 */   ABSTRACT_ARROW(-1, ENTITY),
/* 182 */   ARROW(2, ABSTRACT_ARROW),
/* 183 */   SPECTRAL_ARROW(79, ABSTRACT_ARROW),
/* 184 */   TRIDENT(88, ABSTRACT_ARROW),
/*     */ 
/*     */   
/* 187 */   ABSTRACT_FIREBALL(-1, ENTITY),
/* 188 */   DRAGON_FIREBALL(15, ABSTRACT_FIREBALL),
/* 189 */   FIREBALL(39, ABSTRACT_FIREBALL),
/* 190 */   SMALL_FIREBALL(76, ABSTRACT_FIREBALL),
/* 191 */   WITHER_SKULL(99, ABSTRACT_FIREBALL),
/*     */ 
/*     */   
/* 194 */   PROJECTILE_ABSTRACT(-1, ENTITY),
/* 195 */   SNOWBALL(78, PROJECTILE_ABSTRACT),
/* 196 */   ENDER_PEARL(85, PROJECTILE_ABSTRACT),
/* 197 */   EGG(84, PROJECTILE_ABSTRACT),
/* 198 */   POTION(87, PROJECTILE_ABSTRACT),
/* 199 */   EXPERIENCE_BOTTLE(86, PROJECTILE_ABSTRACT),
/*     */ 
/*     */   
/* 202 */   MINECART_ABSTRACT(-1, ENTITY),
/* 203 */   CHESTED_MINECART_ABSTRACT(-1, MINECART_ABSTRACT),
/* 204 */   CHEST_MINECART(46, CHESTED_MINECART_ABSTRACT),
/* 205 */   HOPPER_MINECART(49, CHESTED_MINECART_ABSTRACT),
/* 206 */   MINECART(45, MINECART_ABSTRACT),
/* 207 */   FURNACE_MINECART(48, MINECART_ABSTRACT),
/* 208 */   COMMAND_BLOCK_MINECART(47, MINECART_ABSTRACT),
/* 209 */   TNT_MINECART(51, MINECART_ABSTRACT),
/* 210 */   SPAWNER_MINECART(50, MINECART_ABSTRACT),
/* 211 */   BOAT(6, ENTITY);
/*     */   
/*     */   private static final EntityType[] TYPES;
/*     */   
/*     */   private final int id;
/*     */   private final EntityType parent;
/*     */   
/*     */   Entity1_16_2Types(int id) {
/* 219 */     this.id = id;
/* 220 */     this.parent = null;
/*     */   }
/*     */   
/*     */   Entity1_16_2Types(int id, EntityType parent) {
/* 224 */     this.id = id;
/* 225 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 230 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType getParent() {
/* 235 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public String identifier() {
/* 240 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAbstractType() {
/* 245 */     return (this.id != -1);
/*     */   }
/*     */   
/*     */   static {
/* 249 */     TYPES = EntityTypeUtil.toOrderedArray((EntityType[])values());
/*     */   }
/*     */   
/*     */   public static EntityType getTypeFromId(int typeId) {
/* 253 */     return EntityTypeUtil.getTypeFromId(TYPES, typeId, ENTITY);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\entities\Entity1_16_2Types.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
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
/*     */ public enum Entity1_16Types
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
/*  40 */   TNT(63, ENTITY),
/*  41 */   SHULKER_BULLET(70, ENTITY),
/*  42 */   FISHING_BOBBER(106, ENTITY),
/*     */   
/*  44 */   LIVINGENTITY(-1, ENTITY),
/*  45 */   ARMOR_STAND(1, LIVINGENTITY),
/*  46 */   PLAYER(105, LIVINGENTITY),
/*     */   
/*  48 */   ABSTRACT_INSENTIENT(-1, LIVINGENTITY),
/*  49 */   ENDER_DRAGON(19, ABSTRACT_INSENTIENT),
/*     */   
/*  51 */   BEE(4, ABSTRACT_INSENTIENT),
/*     */   
/*  53 */   ABSTRACT_CREATURE(-1, ABSTRACT_INSENTIENT),
/*     */   
/*  55 */   ABSTRACT_AGEABLE(-1, ABSTRACT_CREATURE),
/*  56 */   VILLAGER(92, ABSTRACT_AGEABLE),
/*  57 */   WANDERING_TRADER(94, ABSTRACT_AGEABLE),
/*     */ 
/*     */   
/*  60 */   ABSTRACT_ANIMAL(-1, ABSTRACT_AGEABLE),
/*  61 */   DOLPHIN(13, ABSTRACT_INSENTIENT),
/*  62 */   CHICKEN(9, ABSTRACT_ANIMAL),
/*  63 */   COW(11, ABSTRACT_ANIMAL),
/*  64 */   MOOSHROOM(53, COW),
/*  65 */   PANDA(56, ABSTRACT_INSENTIENT),
/*  66 */   PIG(59, ABSTRACT_ANIMAL),
/*  67 */   POLAR_BEAR(62, ABSTRACT_ANIMAL),
/*  68 */   RABBIT(65, ABSTRACT_ANIMAL),
/*  69 */   SHEEP(68, ABSTRACT_ANIMAL),
/*  70 */   TURTLE(90, ABSTRACT_ANIMAL),
/*  71 */   FOX(28, ABSTRACT_ANIMAL),
/*     */   
/*  73 */   ABSTRACT_TAMEABLE_ANIMAL(-1, ABSTRACT_ANIMAL),
/*  74 */   CAT(7, ABSTRACT_TAMEABLE_ANIMAL),
/*  75 */   OCELOT(54, ABSTRACT_TAMEABLE_ANIMAL),
/*  76 */   WOLF(99, ABSTRACT_TAMEABLE_ANIMAL),
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
/*  87 */   TRADER_LLAMA(88, CHESTED_HORSE),
/*  88 */   HORSE(33, ABSTRACT_HORSE),
/*  89 */   SKELETON_HORSE(73, ABSTRACT_HORSE),
/*  90 */   ZOMBIE_HORSE(102, ABSTRACT_HORSE),
/*     */ 
/*     */   
/*  93 */   ABSTRACT_GOLEM(-1, ABSTRACT_CREATURE),
/*  94 */   SNOW_GOLEM(76, ABSTRACT_GOLEM),
/*  95 */   IRON_GOLEM(36, ABSTRACT_GOLEM),
/*  96 */   SHULKER(69, ABSTRACT_GOLEM),
/*     */ 
/*     */   
/*  99 */   ABSTRACT_FISHES(-1, ABSTRACT_CREATURE),
/* 100 */   COD(10, ABSTRACT_FISHES),
/* 101 */   PUFFERFISH(64, ABSTRACT_FISHES),
/* 102 */   SALMON(67, ABSTRACT_FISHES),
/* 103 */   TROPICAL_FISH(89, ABSTRACT_FISHES),
/*     */ 
/*     */   
/* 106 */   ABSTRACT_MONSTER(-1, ABSTRACT_CREATURE),
/* 107 */   BLAZE(5, ABSTRACT_MONSTER),
/* 108 */   CREEPER(12, ABSTRACT_MONSTER),
/* 109 */   ENDERMITE(21, ABSTRACT_MONSTER),
/* 110 */   ENDERMAN(20, ABSTRACT_MONSTER),
/* 111 */   GIANT(30, ABSTRACT_MONSTER),
/* 112 */   SILVERFISH(71, ABSTRACT_MONSTER),
/* 113 */   VEX(91, ABSTRACT_MONSTER),
/* 114 */   WITCH(95, ABSTRACT_MONSTER),
/* 115 */   WITHER(96, ABSTRACT_MONSTER),
/* 116 */   RAVAGER(66, ABSTRACT_MONSTER),
/* 117 */   PIGLIN(60, ABSTRACT_MONSTER),
/*     */   
/* 119 */   HOGLIN(32, ABSTRACT_ANIMAL),
/* 120 */   STRIDER(82, ABSTRACT_ANIMAL),
/* 121 */   ZOGLIN(100, ABSTRACT_MONSTER),
/*     */ 
/*     */   
/* 124 */   ABSTRACT_ILLAGER_BASE(-1, ABSTRACT_MONSTER),
/* 125 */   ABSTRACT_EVO_ILLU_ILLAGER(-1, ABSTRACT_ILLAGER_BASE),
/* 126 */   EVOKER(22, ABSTRACT_EVO_ILLU_ILLAGER),
/* 127 */   ILLUSIONER(35, ABSTRACT_EVO_ILLU_ILLAGER),
/* 128 */   VINDICATOR(93, ABSTRACT_ILLAGER_BASE),
/* 129 */   PILLAGER(61, ABSTRACT_ILLAGER_BASE),
/*     */ 
/*     */   
/* 132 */   ABSTRACT_SKELETON(-1, ABSTRACT_MONSTER),
/* 133 */   SKELETON(72, ABSTRACT_SKELETON),
/* 134 */   STRAY(81, ABSTRACT_SKELETON),
/* 135 */   WITHER_SKELETON(97, ABSTRACT_SKELETON),
/*     */ 
/*     */   
/* 138 */   GUARDIAN(31, ABSTRACT_MONSTER),
/* 139 */   ELDER_GUARDIAN(17, GUARDIAN),
/*     */ 
/*     */   
/* 142 */   SPIDER(79, ABSTRACT_MONSTER),
/* 143 */   CAVE_SPIDER(8, SPIDER),
/*     */ 
/*     */   
/* 146 */   ZOMBIE(101, ABSTRACT_MONSTER),
/* 147 */   DROWNED(16, ZOMBIE),
/* 148 */   HUSK(34, ZOMBIE),
/* 149 */   ZOMBIFIED_PIGLIN(104, ZOMBIE),
/* 150 */   ZOMBIE_VILLAGER(103, ZOMBIE),
/*     */ 
/*     */   
/* 153 */   ABSTRACT_FLYING(-1, ABSTRACT_INSENTIENT),
/* 154 */   GHAST(29, ABSTRACT_FLYING),
/* 155 */   PHANTOM(58, ABSTRACT_FLYING),
/*     */   
/* 157 */   ABSTRACT_AMBIENT(-1, ABSTRACT_INSENTIENT),
/* 158 */   BAT(3, ABSTRACT_AMBIENT),
/*     */   
/* 160 */   ABSTRACT_WATERMOB(-1, ABSTRACT_INSENTIENT),
/* 161 */   SQUID(80, ABSTRACT_WATERMOB),
/*     */ 
/*     */   
/* 164 */   SLIME(74, ABSTRACT_INSENTIENT),
/* 165 */   MAGMA_CUBE(44, SLIME),
/*     */ 
/*     */   
/* 168 */   ABSTRACT_HANGING(-1, ENTITY),
/* 169 */   LEASH_KNOT(40, ABSTRACT_HANGING),
/* 170 */   ITEM_FRAME(38, ABSTRACT_HANGING),
/* 171 */   PAINTING(55, ABSTRACT_HANGING),
/*     */   
/* 173 */   ABSTRACT_LIGHTNING(-1, ENTITY),
/* 174 */   LIGHTNING_BOLT(41, ABSTRACT_LIGHTNING),
/*     */ 
/*     */   
/* 177 */   ABSTRACT_ARROW(-1, ENTITY),
/* 178 */   ARROW(2, ABSTRACT_ARROW),
/* 179 */   SPECTRAL_ARROW(78, ABSTRACT_ARROW),
/* 180 */   TRIDENT(87, ABSTRACT_ARROW),
/*     */ 
/*     */   
/* 183 */   ABSTRACT_FIREBALL(-1, ENTITY),
/* 184 */   DRAGON_FIREBALL(15, ABSTRACT_FIREBALL),
/* 185 */   FIREBALL(39, ABSTRACT_FIREBALL),
/* 186 */   SMALL_FIREBALL(75, ABSTRACT_FIREBALL),
/* 187 */   WITHER_SKULL(98, ABSTRACT_FIREBALL),
/*     */ 
/*     */   
/* 190 */   PROJECTILE_ABSTRACT(-1, ENTITY),
/* 191 */   SNOWBALL(77, PROJECTILE_ABSTRACT),
/* 192 */   ENDER_PEARL(84, PROJECTILE_ABSTRACT),
/* 193 */   EGG(83, PROJECTILE_ABSTRACT),
/* 194 */   POTION(86, PROJECTILE_ABSTRACT),
/* 195 */   EXPERIENCE_BOTTLE(85, PROJECTILE_ABSTRACT),
/*     */ 
/*     */   
/* 198 */   MINECART_ABSTRACT(-1, ENTITY),
/* 199 */   CHESTED_MINECART_ABSTRACT(-1, MINECART_ABSTRACT),
/* 200 */   CHEST_MINECART(46, CHESTED_MINECART_ABSTRACT),
/* 201 */   HOPPER_MINECART(49, CHESTED_MINECART_ABSTRACT),
/* 202 */   MINECART(45, MINECART_ABSTRACT),
/* 203 */   FURNACE_MINECART(48, MINECART_ABSTRACT),
/* 204 */   COMMAND_BLOCK_MINECART(47, MINECART_ABSTRACT),
/* 205 */   TNT_MINECART(51, MINECART_ABSTRACT),
/* 206 */   SPAWNER_MINECART(50, MINECART_ABSTRACT),
/* 207 */   BOAT(6, ENTITY);
/*     */   
/*     */   private static final EntityType[] TYPES;
/*     */   
/*     */   private final int id;
/*     */   private final EntityType parent;
/*     */   
/*     */   Entity1_16Types(int id) {
/* 215 */     this.id = id;
/* 216 */     this.parent = null;
/*     */   }
/*     */   
/*     */   Entity1_16Types(int id, EntityType parent) {
/* 220 */     this.id = id;
/* 221 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 226 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType getParent() {
/* 231 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public String identifier() {
/* 236 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAbstractType() {
/* 241 */     return (this.id != -1);
/*     */   }
/*     */   
/*     */   static {
/* 245 */     TYPES = EntityTypeUtil.toOrderedArray((EntityType[])values());
/*     */   }
/*     */   
/*     */   public static EntityType getTypeFromId(int typeId) {
/* 249 */     return EntityTypeUtil.getTypeFromId(TYPES, typeId, ENTITY);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\entities\Entity1_16Types.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
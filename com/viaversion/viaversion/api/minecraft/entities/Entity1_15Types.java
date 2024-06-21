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
/*     */ public enum Entity1_15Types
/*     */   implements EntityType
/*     */ {
/*  29 */   ENTITY(-1),
/*     */   
/*  31 */   AREA_EFFECT_CLOUD(0, ENTITY),
/*  32 */   END_CRYSTAL(18, ENTITY),
/*  33 */   EVOKER_FANGS(22, ENTITY),
/*  34 */   EXPERIENCE_ORB(24, ENTITY),
/*  35 */   EYE_OF_ENDER(25, ENTITY),
/*  36 */   FALLING_BLOCK(26, ENTITY),
/*  37 */   FIREWORK_ROCKET(27, ENTITY),
/*  38 */   ITEM(35, ENTITY),
/*  39 */   LLAMA_SPIT(40, ENTITY),
/*  40 */   TNT(59, ENTITY),
/*  41 */   SHULKER_BULLET(64, ENTITY),
/*  42 */   FISHING_BOBBER(102, ENTITY),
/*     */   
/*  44 */   LIVINGENTITY(-1, ENTITY),
/*  45 */   ARMOR_STAND(1, LIVINGENTITY),
/*  46 */   PLAYER(101, LIVINGENTITY),
/*     */   
/*  48 */   ABSTRACT_INSENTIENT(-1, LIVINGENTITY),
/*  49 */   ENDER_DRAGON(19, ABSTRACT_INSENTIENT),
/*     */   
/*  51 */   BEE(4, ABSTRACT_INSENTIENT),
/*     */   
/*  53 */   ABSTRACT_CREATURE(-1, ABSTRACT_INSENTIENT),
/*     */   
/*  55 */   ABSTRACT_AGEABLE(-1, ABSTRACT_CREATURE),
/*  56 */   VILLAGER(85, ABSTRACT_AGEABLE),
/*  57 */   WANDERING_TRADER(89, ABSTRACT_AGEABLE),
/*     */ 
/*     */   
/*  60 */   ABSTRACT_ANIMAL(-1, ABSTRACT_AGEABLE),
/*  61 */   DOLPHIN(14, ABSTRACT_INSENTIENT),
/*  62 */   CHICKEN(9, ABSTRACT_ANIMAL),
/*  63 */   COW(11, ABSTRACT_ANIMAL),
/*  64 */   MOOSHROOM(50, COW),
/*  65 */   PANDA(53, ABSTRACT_INSENTIENT),
/*  66 */   PIG(55, ABSTRACT_ANIMAL),
/*  67 */   POLAR_BEAR(58, ABSTRACT_ANIMAL),
/*  68 */   RABBIT(60, ABSTRACT_ANIMAL),
/*  69 */   SHEEP(62, ABSTRACT_ANIMAL),
/*  70 */   TURTLE(78, ABSTRACT_ANIMAL),
/*  71 */   FOX(28, ABSTRACT_ANIMAL),
/*     */   
/*  73 */   ABSTRACT_TAMEABLE_ANIMAL(-1, ABSTRACT_ANIMAL),
/*  74 */   CAT(7, ABSTRACT_TAMEABLE_ANIMAL),
/*  75 */   OCELOT(51, ABSTRACT_TAMEABLE_ANIMAL),
/*  76 */   WOLF(94, ABSTRACT_TAMEABLE_ANIMAL),
/*     */   
/*  78 */   ABSTRACT_PARROT(-1, ABSTRACT_TAMEABLE_ANIMAL),
/*  79 */   PARROT(54, ABSTRACT_PARROT),
/*     */ 
/*     */   
/*  82 */   ABSTRACT_HORSE(-1, ABSTRACT_ANIMAL),
/*  83 */   CHESTED_HORSE(-1, ABSTRACT_HORSE),
/*  84 */   DONKEY(13, CHESTED_HORSE),
/*  85 */   MULE(49, CHESTED_HORSE),
/*  86 */   LLAMA(39, CHESTED_HORSE),
/*  87 */   TRADER_LLAMA(76, CHESTED_HORSE),
/*  88 */   HORSE(32, ABSTRACT_HORSE),
/*  89 */   SKELETON_HORSE(67, ABSTRACT_HORSE),
/*  90 */   ZOMBIE_HORSE(96, ABSTRACT_HORSE),
/*     */ 
/*     */   
/*  93 */   ABSTRACT_GOLEM(-1, ABSTRACT_CREATURE),
/*  94 */   SNOW_GOLEM(70, ABSTRACT_GOLEM),
/*  95 */   IRON_GOLEM(86, ABSTRACT_GOLEM),
/*  96 */   SHULKER(63, ABSTRACT_GOLEM),
/*     */ 
/*     */   
/*  99 */   ABSTRACT_FISHES(-1, ABSTRACT_CREATURE),
/* 100 */   COD(10, ABSTRACT_FISHES),
/* 101 */   PUFFERFISH(56, ABSTRACT_FISHES),
/* 102 */   SALMON(61, ABSTRACT_FISHES),
/* 103 */   TROPICAL_FISH(77, ABSTRACT_FISHES),
/*     */ 
/*     */   
/* 106 */   ABSTRACT_MONSTER(-1, ABSTRACT_CREATURE),
/* 107 */   BLAZE(5, ABSTRACT_MONSTER),
/* 108 */   CREEPER(12, ABSTRACT_MONSTER),
/* 109 */   ENDERMITE(21, ABSTRACT_MONSTER),
/* 110 */   ENDERMAN(20, ABSTRACT_MONSTER),
/* 111 */   GIANT(30, ABSTRACT_MONSTER),
/* 112 */   SILVERFISH(65, ABSTRACT_MONSTER),
/* 113 */   VEX(84, ABSTRACT_MONSTER),
/* 114 */   WITCH(90, ABSTRACT_MONSTER),
/* 115 */   WITHER(91, ABSTRACT_MONSTER),
/* 116 */   RAVAGER(99, ABSTRACT_MONSTER),
/*     */ 
/*     */   
/* 119 */   ABSTRACT_ILLAGER_BASE(-1, ABSTRACT_MONSTER),
/* 120 */   ABSTRACT_EVO_ILLU_ILLAGER(-1, ABSTRACT_ILLAGER_BASE),
/* 121 */   EVOKER(23, ABSTRACT_EVO_ILLU_ILLAGER),
/* 122 */   ILLUSIONER(34, ABSTRACT_EVO_ILLU_ILLAGER),
/* 123 */   VINDICATOR(87, ABSTRACT_ILLAGER_BASE),
/* 124 */   PILLAGER(88, ABSTRACT_ILLAGER_BASE),
/*     */ 
/*     */   
/* 127 */   ABSTRACT_SKELETON(-1, ABSTRACT_MONSTER),
/* 128 */   SKELETON(66, ABSTRACT_SKELETON),
/* 129 */   STRAY(75, ABSTRACT_SKELETON),
/* 130 */   WITHER_SKELETON(92, ABSTRACT_SKELETON),
/*     */ 
/*     */   
/* 133 */   GUARDIAN(31, ABSTRACT_MONSTER),
/* 134 */   ELDER_GUARDIAN(17, GUARDIAN),
/*     */ 
/*     */   
/* 137 */   SPIDER(73, ABSTRACT_MONSTER),
/* 138 */   CAVE_SPIDER(8, SPIDER),
/*     */ 
/*     */   
/* 141 */   ZOMBIE(95, ABSTRACT_MONSTER),
/* 142 */   DROWNED(16, ZOMBIE),
/* 143 */   HUSK(33, ZOMBIE),
/* 144 */   ZOMBIE_PIGMAN(57, ZOMBIE),
/* 145 */   ZOMBIE_VILLAGER(97, ZOMBIE),
/*     */ 
/*     */   
/* 148 */   ABSTRACT_FLYING(-1, ABSTRACT_INSENTIENT),
/* 149 */   GHAST(29, ABSTRACT_FLYING),
/* 150 */   PHANTOM(98, ABSTRACT_FLYING),
/*     */   
/* 152 */   ABSTRACT_AMBIENT(-1, ABSTRACT_INSENTIENT),
/* 153 */   BAT(3, ABSTRACT_AMBIENT),
/*     */   
/* 155 */   ABSTRACT_WATERMOB(-1, ABSTRACT_INSENTIENT),
/* 156 */   SQUID(74, ABSTRACT_WATERMOB),
/*     */ 
/*     */   
/* 159 */   SLIME(68, ABSTRACT_INSENTIENT),
/* 160 */   MAGMA_CUBE(41, SLIME),
/*     */ 
/*     */   
/* 163 */   ABSTRACT_HANGING(-1, ENTITY),
/* 164 */   LEASH_KNOT(38, ABSTRACT_HANGING),
/* 165 */   ITEM_FRAME(36, ABSTRACT_HANGING),
/* 166 */   PAINTING(52, ABSTRACT_HANGING),
/*     */   
/* 168 */   ABSTRACT_LIGHTNING(-1, ENTITY),
/* 169 */   LIGHTNING_BOLT(100, ABSTRACT_LIGHTNING),
/*     */ 
/*     */   
/* 172 */   ABSTRACT_ARROW(-1, ENTITY),
/* 173 */   ARROW(2, ABSTRACT_ARROW),
/* 174 */   SPECTRAL_ARROW(72, ABSTRACT_ARROW),
/* 175 */   TRIDENT(83, ABSTRACT_ARROW),
/*     */ 
/*     */   
/* 178 */   ABSTRACT_FIREBALL(-1, ENTITY),
/* 179 */   DRAGON_FIREBALL(15, ABSTRACT_FIREBALL),
/* 180 */   FIREBALL(37, ABSTRACT_FIREBALL),
/* 181 */   SMALL_FIREBALL(69, ABSTRACT_FIREBALL),
/* 182 */   WITHER_SKULL(93, ABSTRACT_FIREBALL),
/*     */ 
/*     */   
/* 185 */   PROJECTILE_ABSTRACT(-1, ENTITY),
/* 186 */   SNOWBALL(71, PROJECTILE_ABSTRACT),
/* 187 */   ENDER_PEARL(80, PROJECTILE_ABSTRACT),
/* 188 */   EGG(79, PROJECTILE_ABSTRACT),
/* 189 */   POTION(82, PROJECTILE_ABSTRACT),
/* 190 */   EXPERIENCE_BOTTLE(81, PROJECTILE_ABSTRACT),
/*     */ 
/*     */   
/* 193 */   MINECART_ABSTRACT(-1, ENTITY),
/* 194 */   CHESTED_MINECART_ABSTRACT(-1, MINECART_ABSTRACT),
/* 195 */   CHEST_MINECART(43, CHESTED_MINECART_ABSTRACT),
/* 196 */   HOPPER_MINECART(46, CHESTED_MINECART_ABSTRACT),
/* 197 */   MINECART(42, MINECART_ABSTRACT),
/* 198 */   FURNACE_MINECART(45, MINECART_ABSTRACT),
/* 199 */   COMMAND_BLOCK_MINECART(44, MINECART_ABSTRACT),
/* 200 */   TNT_MINECART(48, MINECART_ABSTRACT),
/* 201 */   SPAWNER_MINECART(47, MINECART_ABSTRACT),
/* 202 */   BOAT(6, ENTITY);
/*     */   
/*     */   private static final EntityType[] TYPES;
/*     */   
/*     */   private final int id;
/*     */   private final EntityType parent;
/*     */   
/*     */   Entity1_15Types(int id) {
/* 210 */     this.id = id;
/* 211 */     this.parent = null;
/*     */   }
/*     */   
/*     */   Entity1_15Types(int id, EntityType parent) {
/* 215 */     this.id = id;
/* 216 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 221 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType getParent() {
/* 226 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public String identifier() {
/* 231 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAbstractType() {
/* 236 */     return (this.id != -1);
/*     */   }
/*     */   
/*     */   static {
/* 240 */     TYPES = EntityTypeUtil.toOrderedArray((EntityType[])values());
/*     */   }
/*     */   
/*     */   public static EntityType getTypeFromId(int typeId) {
/* 244 */     return EntityTypeUtil.getTypeFromId(TYPES, typeId, ENTITY);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\entities\Entity1_15Types.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
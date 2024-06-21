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
/*     */ public enum Entity1_14Types
/*     */   implements EntityType
/*     */ {
/*  29 */   ENTITY(-1),
/*     */   
/*  31 */   AREA_EFFECT_CLOUD(0, ENTITY),
/*  32 */   END_CRYSTAL(17, ENTITY),
/*  33 */   EVOKER_FANGS(21, ENTITY),
/*  34 */   EXPERIENCE_ORB(23, ENTITY),
/*  35 */   EYE_OF_ENDER(24, ENTITY),
/*  36 */   FALLING_BLOCK(25, ENTITY),
/*  37 */   FIREWORK_ROCKET(26, ENTITY),
/*  38 */   ITEM(34, ENTITY),
/*  39 */   LLAMA_SPIT(39, ENTITY),
/*  40 */   TNT(58, ENTITY),
/*  41 */   SHULKER_BULLET(63, ENTITY),
/*  42 */   FISHING_BOBBER(101, ENTITY),
/*     */   
/*  44 */   LIVINGENTITY(-1, ENTITY),
/*  45 */   ARMOR_STAND(1, LIVINGENTITY),
/*  46 */   PLAYER(100, LIVINGENTITY),
/*     */   
/*  48 */   ABSTRACT_INSENTIENT(-1, LIVINGENTITY),
/*  49 */   ENDER_DRAGON(18, ABSTRACT_INSENTIENT),
/*     */   
/*  51 */   ABSTRACT_CREATURE(-1, ABSTRACT_INSENTIENT),
/*     */   
/*  53 */   ABSTRACT_AGEABLE(-1, ABSTRACT_CREATURE),
/*  54 */   VILLAGER(84, ABSTRACT_AGEABLE),
/*  55 */   WANDERING_TRADER(88, ABSTRACT_AGEABLE),
/*     */ 
/*     */   
/*  58 */   ABSTRACT_ANIMAL(-1, ABSTRACT_AGEABLE),
/*  59 */   DOLPHIN(13, ABSTRACT_INSENTIENT),
/*  60 */   CHICKEN(8, ABSTRACT_ANIMAL),
/*  61 */   COW(10, ABSTRACT_ANIMAL),
/*  62 */   MOOSHROOM(49, COW),
/*  63 */   PANDA(52, ABSTRACT_INSENTIENT),
/*  64 */   PIG(54, ABSTRACT_ANIMAL),
/*  65 */   POLAR_BEAR(57, ABSTRACT_ANIMAL),
/*  66 */   RABBIT(59, ABSTRACT_ANIMAL),
/*  67 */   SHEEP(61, ABSTRACT_ANIMAL),
/*  68 */   TURTLE(77, ABSTRACT_ANIMAL),
/*  69 */   FOX(27, ABSTRACT_ANIMAL),
/*     */   
/*  71 */   ABSTRACT_TAMEABLE_ANIMAL(-1, ABSTRACT_ANIMAL),
/*  72 */   CAT(6, ABSTRACT_TAMEABLE_ANIMAL),
/*  73 */   OCELOT(50, ABSTRACT_TAMEABLE_ANIMAL),
/*  74 */   WOLF(93, ABSTRACT_TAMEABLE_ANIMAL),
/*     */   
/*  76 */   ABSTRACT_PARROT(-1, ABSTRACT_TAMEABLE_ANIMAL),
/*  77 */   PARROT(53, ABSTRACT_PARROT),
/*     */ 
/*     */   
/*  80 */   ABSTRACT_HORSE(-1, ABSTRACT_ANIMAL),
/*  81 */   CHESTED_HORSE(-1, ABSTRACT_HORSE),
/*  82 */   DONKEY(12, CHESTED_HORSE),
/*  83 */   MULE(48, CHESTED_HORSE),
/*  84 */   LLAMA(38, CHESTED_HORSE),
/*  85 */   TRADER_LLAMA(75, CHESTED_HORSE),
/*  86 */   HORSE(31, ABSTRACT_HORSE),
/*  87 */   SKELETON_HORSE(66, ABSTRACT_HORSE),
/*  88 */   ZOMBIE_HORSE(95, ABSTRACT_HORSE),
/*     */ 
/*     */   
/*  91 */   ABSTRACT_GOLEM(-1, ABSTRACT_CREATURE),
/*  92 */   SNOW_GOLEM(69, ABSTRACT_GOLEM),
/*  93 */   IRON_GOLEM(85, ABSTRACT_GOLEM),
/*  94 */   SHULKER(62, ABSTRACT_GOLEM),
/*     */ 
/*     */   
/*  97 */   ABSTRACT_FISHES(-1, ABSTRACT_CREATURE),
/*  98 */   COD(9, ABSTRACT_FISHES),
/*  99 */   PUFFERFISH(55, ABSTRACT_FISHES),
/* 100 */   SALMON(60, ABSTRACT_FISHES),
/* 101 */   TROPICAL_FISH(76, ABSTRACT_FISHES),
/*     */ 
/*     */   
/* 104 */   ABSTRACT_MONSTER(-1, ABSTRACT_CREATURE),
/* 105 */   BLAZE(4, ABSTRACT_MONSTER),
/* 106 */   CREEPER(11, ABSTRACT_MONSTER),
/* 107 */   ENDERMITE(20, ABSTRACT_MONSTER),
/* 108 */   ENDERMAN(19, ABSTRACT_MONSTER),
/* 109 */   GIANT(29, ABSTRACT_MONSTER),
/* 110 */   SILVERFISH(64, ABSTRACT_MONSTER),
/* 111 */   VEX(83, ABSTRACT_MONSTER),
/* 112 */   WITCH(89, ABSTRACT_MONSTER),
/* 113 */   WITHER(90, ABSTRACT_MONSTER),
/* 114 */   RAVAGER(98, ABSTRACT_MONSTER),
/*     */ 
/*     */   
/* 117 */   ABSTRACT_ILLAGER_BASE(-1, ABSTRACT_MONSTER),
/* 118 */   ABSTRACT_EVO_ILLU_ILLAGER(-1, ABSTRACT_ILLAGER_BASE),
/* 119 */   EVOKER(22, ABSTRACT_EVO_ILLU_ILLAGER),
/* 120 */   ILLUSIONER(33, ABSTRACT_EVO_ILLU_ILLAGER),
/* 121 */   VINDICATOR(86, ABSTRACT_ILLAGER_BASE),
/* 122 */   PILLAGER(87, ABSTRACT_ILLAGER_BASE),
/*     */ 
/*     */   
/* 125 */   ABSTRACT_SKELETON(-1, ABSTRACT_MONSTER),
/* 126 */   SKELETON(65, ABSTRACT_SKELETON),
/* 127 */   STRAY(74, ABSTRACT_SKELETON),
/* 128 */   WITHER_SKELETON(91, ABSTRACT_SKELETON),
/*     */ 
/*     */   
/* 131 */   GUARDIAN(30, ABSTRACT_MONSTER),
/* 132 */   ELDER_GUARDIAN(16, GUARDIAN),
/*     */ 
/*     */   
/* 135 */   SPIDER(72, ABSTRACT_MONSTER),
/* 136 */   CAVE_SPIDER(7, SPIDER),
/*     */ 
/*     */   
/* 139 */   ZOMBIE(94, ABSTRACT_MONSTER),
/* 140 */   DROWNED(15, ZOMBIE),
/* 141 */   HUSK(32, ZOMBIE),
/* 142 */   ZOMBIE_PIGMAN(56, ZOMBIE),
/* 143 */   ZOMBIE_VILLAGER(96, ZOMBIE),
/*     */ 
/*     */   
/* 146 */   ABSTRACT_FLYING(-1, ABSTRACT_INSENTIENT),
/* 147 */   GHAST(28, ABSTRACT_FLYING),
/* 148 */   PHANTOM(97, ABSTRACT_FLYING),
/*     */   
/* 150 */   ABSTRACT_AMBIENT(-1, ABSTRACT_INSENTIENT),
/* 151 */   BAT(3, ABSTRACT_AMBIENT),
/*     */   
/* 153 */   ABSTRACT_WATERMOB(-1, ABSTRACT_INSENTIENT),
/* 154 */   SQUID(73, ABSTRACT_WATERMOB),
/*     */ 
/*     */   
/* 157 */   SLIME(67, ABSTRACT_INSENTIENT),
/* 158 */   MAGMA_CUBE(40, SLIME),
/*     */ 
/*     */   
/* 161 */   ABSTRACT_HANGING(-1, ENTITY),
/* 162 */   LEASH_KNOT(37, ABSTRACT_HANGING),
/* 163 */   ITEM_FRAME(35, ABSTRACT_HANGING),
/* 164 */   PAINTING(51, ABSTRACT_HANGING),
/*     */   
/* 166 */   ABSTRACT_LIGHTNING(-1, ENTITY),
/* 167 */   LIGHTNING_BOLT(99, ABSTRACT_LIGHTNING),
/*     */ 
/*     */   
/* 170 */   ABSTRACT_ARROW(-1, ENTITY),
/* 171 */   ARROW(2, ABSTRACT_ARROW),
/* 172 */   SPECTRAL_ARROW(71, ABSTRACT_ARROW),
/* 173 */   TRIDENT(82, ABSTRACT_ARROW),
/*     */ 
/*     */   
/* 176 */   ABSTRACT_FIREBALL(-1, ENTITY),
/* 177 */   DRAGON_FIREBALL(14, ABSTRACT_FIREBALL),
/* 178 */   FIREBALL(36, ABSTRACT_FIREBALL),
/* 179 */   SMALL_FIREBALL(68, ABSTRACT_FIREBALL),
/* 180 */   WITHER_SKULL(92, ABSTRACT_FIREBALL),
/*     */ 
/*     */   
/* 183 */   PROJECTILE_ABSTRACT(-1, ENTITY),
/* 184 */   SNOWBALL(70, PROJECTILE_ABSTRACT),
/* 185 */   ENDER_PEARL(79, PROJECTILE_ABSTRACT),
/* 186 */   EGG(78, PROJECTILE_ABSTRACT),
/* 187 */   POTION(81, PROJECTILE_ABSTRACT),
/* 188 */   EXPERIENCE_BOTTLE(80, PROJECTILE_ABSTRACT),
/*     */ 
/*     */   
/* 191 */   MINECART_ABSTRACT(-1, ENTITY),
/* 192 */   CHESTED_MINECART_ABSTRACT(-1, MINECART_ABSTRACT),
/* 193 */   CHEST_MINECART(42, CHESTED_MINECART_ABSTRACT),
/* 194 */   HOPPER_MINECART(45, CHESTED_MINECART_ABSTRACT),
/* 195 */   MINECART(41, MINECART_ABSTRACT),
/* 196 */   FURNACE_MINECART(44, MINECART_ABSTRACT),
/* 197 */   COMMAND_BLOCK_MINECART(43, MINECART_ABSTRACT),
/* 198 */   TNT_MINECART(47, MINECART_ABSTRACT),
/* 199 */   SPAWNER_MINECART(46, MINECART_ABSTRACT),
/* 200 */   BOAT(5, ENTITY);
/*     */   
/*     */   private static final EntityType[] TYPES;
/*     */   
/*     */   private final int id;
/*     */   private final EntityType parent;
/*     */   
/*     */   Entity1_14Types(int id) {
/* 208 */     this.id = id;
/* 209 */     this.parent = null;
/*     */   }
/*     */   
/*     */   Entity1_14Types(int id, EntityType parent) {
/* 213 */     this.id = id;
/* 214 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 219 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType getParent() {
/* 224 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public String identifier() {
/* 229 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAbstractType() {
/* 234 */     return (this.id != -1);
/*     */   }
/*     */   
/*     */   static {
/* 238 */     TYPES = EntityTypeUtil.toOrderedArray((EntityType[])values());
/*     */   }
/*     */   
/*     */   public static EntityType getTypeFromId(int typeId) {
/* 242 */     return EntityTypeUtil.getTypeFromId(TYPES, typeId, ENTITY);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\entities\Entity1_14Types.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
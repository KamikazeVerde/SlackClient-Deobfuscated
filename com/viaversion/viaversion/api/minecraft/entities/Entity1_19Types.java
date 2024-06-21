/*     */ package com.viaversion.viaversion.api.minecraft.entities;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.util.EntityTypeUtil;
/*     */ import java.util.Locale;
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
/*     */ public enum Entity1_19Types
/*     */   implements EntityType
/*     */ {
/*  33 */   ENTITY(null, null),
/*     */   
/*  35 */   AREA_EFFECT_CLOUD(ENTITY),
/*  36 */   END_CRYSTAL(ENTITY),
/*  37 */   EVOKER_FANGS(ENTITY),
/*  38 */   EXPERIENCE_ORB(ENTITY),
/*  39 */   EYE_OF_ENDER(ENTITY),
/*  40 */   FALLING_BLOCK(ENTITY),
/*  41 */   FIREWORK_ROCKET(ENTITY),
/*  42 */   ITEM(ENTITY),
/*  43 */   LLAMA_SPIT(ENTITY),
/*  44 */   TNT(ENTITY),
/*  45 */   SHULKER_BULLET(ENTITY),
/*  46 */   FISHING_BOBBER(ENTITY),
/*     */   
/*  48 */   LIVINGENTITY(ENTITY, null),
/*  49 */   ARMOR_STAND(LIVINGENTITY),
/*  50 */   MARKER(ENTITY),
/*  51 */   PLAYER(LIVINGENTITY),
/*     */   
/*  53 */   ABSTRACT_INSENTIENT(LIVINGENTITY, null),
/*  54 */   ENDER_DRAGON(ABSTRACT_INSENTIENT),
/*     */   
/*  56 */   BEE(ABSTRACT_INSENTIENT),
/*     */   
/*  58 */   ABSTRACT_CREATURE(ABSTRACT_INSENTIENT, null),
/*     */   
/*  60 */   ABSTRACT_AGEABLE(ABSTRACT_CREATURE, null),
/*  61 */   VILLAGER(ABSTRACT_AGEABLE),
/*  62 */   WANDERING_TRADER(ABSTRACT_AGEABLE),
/*     */ 
/*     */   
/*  65 */   ABSTRACT_ANIMAL(ABSTRACT_AGEABLE, null),
/*  66 */   AXOLOTL(ABSTRACT_ANIMAL),
/*  67 */   DOLPHIN(ABSTRACT_INSENTIENT),
/*  68 */   CHICKEN(ABSTRACT_ANIMAL),
/*  69 */   COW(ABSTRACT_ANIMAL),
/*  70 */   MOOSHROOM(COW),
/*  71 */   PANDA(ABSTRACT_INSENTIENT),
/*  72 */   PIG(ABSTRACT_ANIMAL),
/*  73 */   POLAR_BEAR(ABSTRACT_ANIMAL),
/*  74 */   RABBIT(ABSTRACT_ANIMAL),
/*  75 */   SHEEP(ABSTRACT_ANIMAL),
/*  76 */   TURTLE(ABSTRACT_ANIMAL),
/*  77 */   FOX(ABSTRACT_ANIMAL),
/*  78 */   FROG(ABSTRACT_ANIMAL),
/*  79 */   GOAT(ABSTRACT_ANIMAL),
/*     */   
/*  81 */   ABSTRACT_TAMEABLE_ANIMAL(ABSTRACT_ANIMAL, null),
/*  82 */   CAT(ABSTRACT_TAMEABLE_ANIMAL),
/*  83 */   OCELOT(ABSTRACT_TAMEABLE_ANIMAL),
/*  84 */   WOLF(ABSTRACT_TAMEABLE_ANIMAL),
/*     */   
/*  86 */   ABSTRACT_PARROT(ABSTRACT_TAMEABLE_ANIMAL, null),
/*  87 */   PARROT(ABSTRACT_PARROT),
/*     */ 
/*     */   
/*  90 */   ABSTRACT_HORSE(ABSTRACT_ANIMAL, null),
/*  91 */   CHESTED_HORSE(ABSTRACT_HORSE, null),
/*  92 */   DONKEY(CHESTED_HORSE),
/*  93 */   MULE(CHESTED_HORSE),
/*  94 */   LLAMA(CHESTED_HORSE),
/*  95 */   TRADER_LLAMA(CHESTED_HORSE),
/*  96 */   HORSE(ABSTRACT_HORSE),
/*  97 */   SKELETON_HORSE(ABSTRACT_HORSE),
/*  98 */   ZOMBIE_HORSE(ABSTRACT_HORSE),
/*     */ 
/*     */   
/* 101 */   ABSTRACT_GOLEM(ABSTRACT_CREATURE, null),
/* 102 */   SNOW_GOLEM(ABSTRACT_GOLEM),
/* 103 */   IRON_GOLEM(ABSTRACT_GOLEM),
/* 104 */   SHULKER(ABSTRACT_GOLEM),
/*     */ 
/*     */   
/* 107 */   ABSTRACT_FISHES(ABSTRACT_CREATURE, null),
/* 108 */   COD(ABSTRACT_FISHES),
/* 109 */   PUFFERFISH(ABSTRACT_FISHES),
/* 110 */   SALMON(ABSTRACT_FISHES),
/* 111 */   TROPICAL_FISH(ABSTRACT_FISHES),
/*     */ 
/*     */   
/* 114 */   ABSTRACT_MONSTER(ABSTRACT_CREATURE, null),
/* 115 */   BLAZE(ABSTRACT_MONSTER),
/* 116 */   CREEPER(ABSTRACT_MONSTER),
/* 117 */   ENDERMITE(ABSTRACT_MONSTER),
/* 118 */   ENDERMAN(ABSTRACT_MONSTER),
/* 119 */   GIANT(ABSTRACT_MONSTER),
/* 120 */   SILVERFISH(ABSTRACT_MONSTER),
/* 121 */   VEX(ABSTRACT_MONSTER),
/* 122 */   WITCH(ABSTRACT_MONSTER),
/* 123 */   WITHER(ABSTRACT_MONSTER),
/* 124 */   RAVAGER(ABSTRACT_MONSTER),
/*     */   
/* 126 */   ABSTRACT_PIGLIN(ABSTRACT_MONSTER, null),
/*     */   
/* 128 */   PIGLIN(ABSTRACT_PIGLIN),
/* 129 */   PIGLIN_BRUTE(ABSTRACT_PIGLIN),
/*     */   
/* 131 */   HOGLIN(ABSTRACT_ANIMAL),
/* 132 */   STRIDER(ABSTRACT_ANIMAL),
/* 133 */   TADPOLE(ABSTRACT_FISHES),
/* 134 */   ZOGLIN(ABSTRACT_MONSTER),
/* 135 */   WARDEN(ABSTRACT_MONSTER),
/*     */ 
/*     */   
/* 138 */   ABSTRACT_ILLAGER_BASE(ABSTRACT_MONSTER, null),
/* 139 */   ABSTRACT_EVO_ILLU_ILLAGER(ABSTRACT_ILLAGER_BASE, null),
/* 140 */   EVOKER(ABSTRACT_EVO_ILLU_ILLAGER),
/* 141 */   ILLUSIONER(ABSTRACT_EVO_ILLU_ILLAGER),
/* 142 */   VINDICATOR(ABSTRACT_ILLAGER_BASE),
/* 143 */   PILLAGER(ABSTRACT_ILLAGER_BASE),
/*     */ 
/*     */   
/* 146 */   ABSTRACT_SKELETON(ABSTRACT_MONSTER, null),
/* 147 */   SKELETON(ABSTRACT_SKELETON),
/* 148 */   STRAY(ABSTRACT_SKELETON),
/* 149 */   WITHER_SKELETON(ABSTRACT_SKELETON),
/*     */ 
/*     */   
/* 152 */   GUARDIAN(ABSTRACT_MONSTER),
/* 153 */   ELDER_GUARDIAN(GUARDIAN),
/*     */ 
/*     */   
/* 156 */   SPIDER(ABSTRACT_MONSTER),
/* 157 */   CAVE_SPIDER(SPIDER),
/*     */ 
/*     */   
/* 160 */   ZOMBIE(ABSTRACT_MONSTER),
/* 161 */   DROWNED(ZOMBIE),
/* 162 */   HUSK(ZOMBIE),
/* 163 */   ZOMBIFIED_PIGLIN(ZOMBIE),
/* 164 */   ZOMBIE_VILLAGER(ZOMBIE),
/*     */ 
/*     */   
/* 167 */   ABSTRACT_FLYING(ABSTRACT_INSENTIENT, null),
/* 168 */   GHAST(ABSTRACT_FLYING),
/* 169 */   PHANTOM(ABSTRACT_FLYING),
/*     */   
/* 171 */   ABSTRACT_AMBIENT(ABSTRACT_INSENTIENT, null),
/* 172 */   BAT(ABSTRACT_AMBIENT),
/* 173 */   ALLAY(ABSTRACT_CREATURE),
/*     */   
/* 175 */   ABSTRACT_WATERMOB(ABSTRACT_INSENTIENT, null),
/* 176 */   SQUID(ABSTRACT_WATERMOB),
/* 177 */   GLOW_SQUID(SQUID),
/*     */ 
/*     */   
/* 180 */   SLIME(ABSTRACT_INSENTIENT),
/* 181 */   MAGMA_CUBE(SLIME),
/*     */ 
/*     */   
/* 184 */   ABSTRACT_HANGING(ENTITY, null),
/* 185 */   LEASH_KNOT(ABSTRACT_HANGING),
/* 186 */   ITEM_FRAME(ABSTRACT_HANGING),
/* 187 */   GLOW_ITEM_FRAME(ITEM_FRAME),
/* 188 */   PAINTING(ABSTRACT_HANGING),
/*     */   
/* 190 */   ABSTRACT_LIGHTNING(ENTITY, null),
/* 191 */   LIGHTNING_BOLT(ABSTRACT_LIGHTNING),
/*     */ 
/*     */   
/* 194 */   ABSTRACT_ARROW(ENTITY, null),
/* 195 */   ARROW(ABSTRACT_ARROW),
/* 196 */   SPECTRAL_ARROW(ABSTRACT_ARROW),
/* 197 */   TRIDENT(ABSTRACT_ARROW),
/*     */ 
/*     */   
/* 200 */   ABSTRACT_FIREBALL(ENTITY, null),
/* 201 */   DRAGON_FIREBALL(ABSTRACT_FIREBALL),
/* 202 */   FIREBALL(ABSTRACT_FIREBALL),
/* 203 */   SMALL_FIREBALL(ABSTRACT_FIREBALL),
/* 204 */   WITHER_SKULL(ABSTRACT_FIREBALL),
/*     */ 
/*     */   
/* 207 */   PROJECTILE_ABSTRACT(ENTITY, null),
/* 208 */   SNOWBALL(PROJECTILE_ABSTRACT),
/* 209 */   ENDER_PEARL(PROJECTILE_ABSTRACT),
/* 210 */   EGG(PROJECTILE_ABSTRACT),
/* 211 */   POTION(PROJECTILE_ABSTRACT),
/* 212 */   EXPERIENCE_BOTTLE(PROJECTILE_ABSTRACT),
/*     */ 
/*     */   
/* 215 */   MINECART_ABSTRACT(ENTITY, null),
/* 216 */   CHESTED_MINECART_ABSTRACT(MINECART_ABSTRACT, null),
/* 217 */   CHEST_MINECART(CHESTED_MINECART_ABSTRACT),
/* 218 */   HOPPER_MINECART(CHESTED_MINECART_ABSTRACT),
/* 219 */   MINECART(MINECART_ABSTRACT),
/* 220 */   FURNACE_MINECART(MINECART_ABSTRACT),
/* 221 */   COMMAND_BLOCK_MINECART(MINECART_ABSTRACT),
/* 222 */   TNT_MINECART(MINECART_ABSTRACT),
/* 223 */   SPAWNER_MINECART(MINECART_ABSTRACT),
/* 224 */   BOAT(ENTITY),
/* 225 */   CHEST_BOAT(BOAT);
/*     */   static {
/* 227 */     TYPES = EntityTypeUtil.createSizedArray((EntityType[])values());
/*     */   }
/*     */   
/* 230 */   private int id = -1; private static final EntityType[] TYPES;
/*     */   
/*     */   Entity1_19Types(EntityType parent) {
/* 233 */     this.parent = parent;
/* 234 */     this.identifier = "minecraft:" + name().toLowerCase(Locale.ROOT);
/*     */   }
/*     */   private final EntityType parent; private final String identifier;
/*     */   Entity1_19Types(EntityType parent, String identifier) {
/* 238 */     this.parent = parent;
/* 239 */     this.identifier = identifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 244 */     if (this.id == -1) {
/* 245 */       throw new IllegalStateException("Ids have not been initialized yet (type " + name() + ")");
/*     */     }
/* 247 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String identifier() {
/* 252 */     Preconditions.checkArgument((this.identifier != null), "Called identifier method on abstract type");
/* 253 */     return this.identifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType getParent() {
/* 258 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAbstractType() {
/* 263 */     return (this.identifier == null);
/*     */   }
/*     */   
/*     */   public static EntityType getTypeFromId(int typeId) {
/* 267 */     return EntityTypeUtil.getTypeFromId(TYPES, typeId, ENTITY);
/*     */   }
/*     */   
/*     */   public static void initialize(Protocol<?, ?, ?, ?> protocol) {
/* 271 */     EntityTypeUtil.initialize((EntityType[])values(), TYPES, protocol, (type, id) -> type.id = id);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\entities\Entity1_19Types.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
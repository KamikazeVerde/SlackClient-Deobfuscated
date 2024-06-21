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
/*     */ public enum Entity1_19_3Types
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
/*  99 */   CAMEL(ABSTRACT_HORSE),
/*     */ 
/*     */   
/* 102 */   ABSTRACT_GOLEM(ABSTRACT_CREATURE, null),
/* 103 */   SNOW_GOLEM(ABSTRACT_GOLEM),
/* 104 */   IRON_GOLEM(ABSTRACT_GOLEM),
/* 105 */   SHULKER(ABSTRACT_GOLEM),
/*     */ 
/*     */   
/* 108 */   ABSTRACT_FISHES(ABSTRACT_CREATURE, null),
/* 109 */   COD(ABSTRACT_FISHES),
/* 110 */   PUFFERFISH(ABSTRACT_FISHES),
/* 111 */   SALMON(ABSTRACT_FISHES),
/* 112 */   TROPICAL_FISH(ABSTRACT_FISHES),
/*     */ 
/*     */   
/* 115 */   ABSTRACT_MONSTER(ABSTRACT_CREATURE, null),
/* 116 */   BLAZE(ABSTRACT_MONSTER),
/* 117 */   CREEPER(ABSTRACT_MONSTER),
/* 118 */   ENDERMITE(ABSTRACT_MONSTER),
/* 119 */   ENDERMAN(ABSTRACT_MONSTER),
/* 120 */   GIANT(ABSTRACT_MONSTER),
/* 121 */   SILVERFISH(ABSTRACT_MONSTER),
/* 122 */   VEX(ABSTRACT_MONSTER),
/* 123 */   WITCH(ABSTRACT_MONSTER),
/* 124 */   WITHER(ABSTRACT_MONSTER),
/* 125 */   RAVAGER(ABSTRACT_MONSTER),
/*     */   
/* 127 */   ABSTRACT_PIGLIN(ABSTRACT_MONSTER, null),
/*     */   
/* 129 */   PIGLIN(ABSTRACT_PIGLIN),
/* 130 */   PIGLIN_BRUTE(ABSTRACT_PIGLIN),
/*     */   
/* 132 */   HOGLIN(ABSTRACT_ANIMAL),
/* 133 */   STRIDER(ABSTRACT_ANIMAL),
/* 134 */   TADPOLE(ABSTRACT_FISHES),
/* 135 */   ZOGLIN(ABSTRACT_MONSTER),
/* 136 */   WARDEN(ABSTRACT_MONSTER),
/*     */ 
/*     */   
/* 139 */   ABSTRACT_ILLAGER_BASE(ABSTRACT_MONSTER, null),
/* 140 */   ABSTRACT_EVO_ILLU_ILLAGER(ABSTRACT_ILLAGER_BASE, null),
/* 141 */   EVOKER(ABSTRACT_EVO_ILLU_ILLAGER),
/* 142 */   ILLUSIONER(ABSTRACT_EVO_ILLU_ILLAGER),
/* 143 */   VINDICATOR(ABSTRACT_ILLAGER_BASE),
/* 144 */   PILLAGER(ABSTRACT_ILLAGER_BASE),
/*     */ 
/*     */   
/* 147 */   ABSTRACT_SKELETON(ABSTRACT_MONSTER, null),
/* 148 */   SKELETON(ABSTRACT_SKELETON),
/* 149 */   STRAY(ABSTRACT_SKELETON),
/* 150 */   WITHER_SKELETON(ABSTRACT_SKELETON),
/*     */ 
/*     */   
/* 153 */   GUARDIAN(ABSTRACT_MONSTER),
/* 154 */   ELDER_GUARDIAN(GUARDIAN),
/*     */ 
/*     */   
/* 157 */   SPIDER(ABSTRACT_MONSTER),
/* 158 */   CAVE_SPIDER(SPIDER),
/*     */ 
/*     */   
/* 161 */   ZOMBIE(ABSTRACT_MONSTER),
/* 162 */   DROWNED(ZOMBIE),
/* 163 */   HUSK(ZOMBIE),
/* 164 */   ZOMBIFIED_PIGLIN(ZOMBIE),
/* 165 */   ZOMBIE_VILLAGER(ZOMBIE),
/*     */ 
/*     */   
/* 168 */   ABSTRACT_FLYING(ABSTRACT_INSENTIENT, null),
/* 169 */   GHAST(ABSTRACT_FLYING),
/* 170 */   PHANTOM(ABSTRACT_FLYING),
/*     */   
/* 172 */   ABSTRACT_AMBIENT(ABSTRACT_INSENTIENT, null),
/* 173 */   BAT(ABSTRACT_AMBIENT),
/* 174 */   ALLAY(ABSTRACT_CREATURE),
/*     */   
/* 176 */   ABSTRACT_WATERMOB(ABSTRACT_INSENTIENT, null),
/* 177 */   SQUID(ABSTRACT_WATERMOB),
/* 178 */   GLOW_SQUID(SQUID),
/*     */ 
/*     */   
/* 181 */   SLIME(ABSTRACT_INSENTIENT),
/* 182 */   MAGMA_CUBE(SLIME),
/*     */ 
/*     */   
/* 185 */   ABSTRACT_HANGING(ENTITY, null),
/* 186 */   LEASH_KNOT(ABSTRACT_HANGING),
/* 187 */   ITEM_FRAME(ABSTRACT_HANGING),
/* 188 */   GLOW_ITEM_FRAME(ITEM_FRAME),
/* 189 */   PAINTING(ABSTRACT_HANGING),
/*     */   
/* 191 */   ABSTRACT_LIGHTNING(ENTITY, null),
/* 192 */   LIGHTNING_BOLT(ABSTRACT_LIGHTNING),
/*     */ 
/*     */   
/* 195 */   ABSTRACT_ARROW(ENTITY, null),
/* 196 */   ARROW(ABSTRACT_ARROW),
/* 197 */   SPECTRAL_ARROW(ABSTRACT_ARROW),
/* 198 */   TRIDENT(ABSTRACT_ARROW),
/*     */ 
/*     */   
/* 201 */   ABSTRACT_FIREBALL(ENTITY, null),
/* 202 */   DRAGON_FIREBALL(ABSTRACT_FIREBALL),
/* 203 */   FIREBALL(ABSTRACT_FIREBALL),
/* 204 */   SMALL_FIREBALL(ABSTRACT_FIREBALL),
/* 205 */   WITHER_SKULL(ABSTRACT_FIREBALL),
/*     */ 
/*     */   
/* 208 */   PROJECTILE_ABSTRACT(ENTITY, null),
/* 209 */   SNOWBALL(PROJECTILE_ABSTRACT),
/* 210 */   ENDER_PEARL(PROJECTILE_ABSTRACT),
/* 211 */   EGG(PROJECTILE_ABSTRACT),
/* 212 */   POTION(PROJECTILE_ABSTRACT),
/* 213 */   EXPERIENCE_BOTTLE(PROJECTILE_ABSTRACT),
/*     */ 
/*     */   
/* 216 */   MINECART_ABSTRACT(ENTITY, null),
/* 217 */   CHESTED_MINECART_ABSTRACT(MINECART_ABSTRACT, null),
/* 218 */   CHEST_MINECART(CHESTED_MINECART_ABSTRACT),
/* 219 */   HOPPER_MINECART(CHESTED_MINECART_ABSTRACT),
/* 220 */   MINECART(MINECART_ABSTRACT),
/* 221 */   FURNACE_MINECART(MINECART_ABSTRACT),
/* 222 */   COMMAND_BLOCK_MINECART(MINECART_ABSTRACT),
/* 223 */   TNT_MINECART(MINECART_ABSTRACT),
/* 224 */   SPAWNER_MINECART(MINECART_ABSTRACT),
/* 225 */   BOAT(ENTITY),
/* 226 */   CHEST_BOAT(BOAT);
/*     */   static {
/* 228 */     TYPES = EntityTypeUtil.createSizedArray((EntityType[])values());
/*     */   }
/*     */   
/* 231 */   private int id = -1; private static final EntityType[] TYPES;
/*     */   
/*     */   Entity1_19_3Types(EntityType parent) {
/* 234 */     this.parent = parent;
/* 235 */     this.identifier = "minecraft:" + name().toLowerCase(Locale.ROOT);
/*     */   }
/*     */   private final EntityType parent; private final String identifier;
/*     */   Entity1_19_3Types(EntityType parent, String identifier) {
/* 239 */     this.parent = parent;
/* 240 */     this.identifier = identifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 245 */     if (this.id == -1) {
/* 246 */       throw new IllegalStateException("Ids have not been initialized yet (type " + name() + ")");
/*     */     }
/* 248 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String identifier() {
/* 253 */     Preconditions.checkArgument((this.identifier != null), "Called identifier method on abstract type");
/* 254 */     return this.identifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType getParent() {
/* 259 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAbstractType() {
/* 264 */     return (this.identifier == null);
/*     */   }
/*     */   
/*     */   public static EntityType getTypeFromId(int typeId) {
/* 268 */     return EntityTypeUtil.getTypeFromId(TYPES, typeId, ENTITY);
/*     */   }
/*     */   
/*     */   public static void initialize(Protocol<?, ?, ?, ?> protocol) {
/* 272 */     EntityTypeUtil.initialize((EntityType[])values(), TYPES, protocol, (type, id) -> type.id = id);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\entities\Entity1_19_3Types.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
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
/*     */ public enum Entity1_19_4Types
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
/*  53 */   DISPLAY(ENTITY, null),
/*  54 */   BLOCK_DISPLAY(DISPLAY),
/*  55 */   ITEM_DISPLAY(DISPLAY),
/*  56 */   TEXT_DISPLAY(DISPLAY),
/*  57 */   INTERACTION(ENTITY),
/*     */   
/*  59 */   ABSTRACT_INSENTIENT(LIVINGENTITY, null),
/*  60 */   ENDER_DRAGON(ABSTRACT_INSENTIENT),
/*     */   
/*  62 */   BEE(ABSTRACT_INSENTIENT),
/*     */   
/*  64 */   ABSTRACT_CREATURE(ABSTRACT_INSENTIENT, null),
/*     */   
/*  66 */   ABSTRACT_AGEABLE(ABSTRACT_CREATURE, null),
/*  67 */   VILLAGER(ABSTRACT_AGEABLE),
/*  68 */   WANDERING_TRADER(ABSTRACT_AGEABLE),
/*     */ 
/*     */   
/*  71 */   ABSTRACT_ANIMAL(ABSTRACT_AGEABLE, null),
/*  72 */   AXOLOTL(ABSTRACT_ANIMAL),
/*  73 */   DOLPHIN(ABSTRACT_INSENTIENT),
/*  74 */   CHICKEN(ABSTRACT_ANIMAL),
/*  75 */   COW(ABSTRACT_ANIMAL),
/*  76 */   MOOSHROOM(COW),
/*  77 */   PANDA(ABSTRACT_INSENTIENT),
/*  78 */   PIG(ABSTRACT_ANIMAL),
/*  79 */   POLAR_BEAR(ABSTRACT_ANIMAL),
/*  80 */   RABBIT(ABSTRACT_ANIMAL),
/*  81 */   SHEEP(ABSTRACT_ANIMAL),
/*  82 */   TURTLE(ABSTRACT_ANIMAL),
/*  83 */   FOX(ABSTRACT_ANIMAL),
/*  84 */   FROG(ABSTRACT_ANIMAL),
/*  85 */   GOAT(ABSTRACT_ANIMAL),
/*  86 */   SNIFFER(ABSTRACT_ANIMAL),
/*     */   
/*  88 */   ABSTRACT_TAMEABLE_ANIMAL(ABSTRACT_ANIMAL, null),
/*  89 */   CAT(ABSTRACT_TAMEABLE_ANIMAL),
/*  90 */   OCELOT(ABSTRACT_TAMEABLE_ANIMAL),
/*  91 */   WOLF(ABSTRACT_TAMEABLE_ANIMAL),
/*     */   
/*  93 */   ABSTRACT_PARROT(ABSTRACT_TAMEABLE_ANIMAL, null),
/*  94 */   PARROT(ABSTRACT_PARROT),
/*     */ 
/*     */   
/*  97 */   ABSTRACT_HORSE(ABSTRACT_ANIMAL, null),
/*  98 */   CHESTED_HORSE(ABSTRACT_HORSE, null),
/*  99 */   DONKEY(CHESTED_HORSE),
/* 100 */   MULE(CHESTED_HORSE),
/* 101 */   LLAMA(CHESTED_HORSE),
/* 102 */   TRADER_LLAMA(CHESTED_HORSE),
/* 103 */   HORSE(ABSTRACT_HORSE),
/* 104 */   SKELETON_HORSE(ABSTRACT_HORSE),
/* 105 */   ZOMBIE_HORSE(ABSTRACT_HORSE),
/* 106 */   CAMEL(ABSTRACT_HORSE),
/*     */ 
/*     */   
/* 109 */   ABSTRACT_GOLEM(ABSTRACT_CREATURE, null),
/* 110 */   SNOW_GOLEM(ABSTRACT_GOLEM),
/* 111 */   IRON_GOLEM(ABSTRACT_GOLEM),
/* 112 */   SHULKER(ABSTRACT_GOLEM),
/*     */ 
/*     */   
/* 115 */   ABSTRACT_FISHES(ABSTRACT_CREATURE, null),
/* 116 */   COD(ABSTRACT_FISHES),
/* 117 */   PUFFERFISH(ABSTRACT_FISHES),
/* 118 */   SALMON(ABSTRACT_FISHES),
/* 119 */   TROPICAL_FISH(ABSTRACT_FISHES),
/*     */ 
/*     */   
/* 122 */   ABSTRACT_MONSTER(ABSTRACT_CREATURE, null),
/* 123 */   BLAZE(ABSTRACT_MONSTER),
/* 124 */   CREEPER(ABSTRACT_MONSTER),
/* 125 */   ENDERMITE(ABSTRACT_MONSTER),
/* 126 */   ENDERMAN(ABSTRACT_MONSTER),
/* 127 */   GIANT(ABSTRACT_MONSTER),
/* 128 */   SILVERFISH(ABSTRACT_MONSTER),
/* 129 */   VEX(ABSTRACT_MONSTER),
/* 130 */   WITCH(ABSTRACT_MONSTER),
/* 131 */   WITHER(ABSTRACT_MONSTER),
/* 132 */   RAVAGER(ABSTRACT_MONSTER),
/*     */   
/* 134 */   ABSTRACT_PIGLIN(ABSTRACT_MONSTER, null),
/*     */   
/* 136 */   PIGLIN(ABSTRACT_PIGLIN),
/* 137 */   PIGLIN_BRUTE(ABSTRACT_PIGLIN),
/*     */   
/* 139 */   HOGLIN(ABSTRACT_ANIMAL),
/* 140 */   STRIDER(ABSTRACT_ANIMAL),
/* 141 */   TADPOLE(ABSTRACT_FISHES),
/* 142 */   ZOGLIN(ABSTRACT_MONSTER),
/* 143 */   WARDEN(ABSTRACT_MONSTER),
/*     */ 
/*     */   
/* 146 */   ABSTRACT_ILLAGER_BASE(ABSTRACT_MONSTER, null),
/* 147 */   ABSTRACT_EVO_ILLU_ILLAGER(ABSTRACT_ILLAGER_BASE, null),
/* 148 */   EVOKER(ABSTRACT_EVO_ILLU_ILLAGER),
/* 149 */   ILLUSIONER(ABSTRACT_EVO_ILLU_ILLAGER),
/* 150 */   VINDICATOR(ABSTRACT_ILLAGER_BASE),
/* 151 */   PILLAGER(ABSTRACT_ILLAGER_BASE),
/*     */ 
/*     */   
/* 154 */   ABSTRACT_SKELETON(ABSTRACT_MONSTER, null),
/* 155 */   SKELETON(ABSTRACT_SKELETON),
/* 156 */   STRAY(ABSTRACT_SKELETON),
/* 157 */   WITHER_SKELETON(ABSTRACT_SKELETON),
/*     */ 
/*     */   
/* 160 */   GUARDIAN(ABSTRACT_MONSTER),
/* 161 */   ELDER_GUARDIAN(GUARDIAN),
/*     */ 
/*     */   
/* 164 */   SPIDER(ABSTRACT_MONSTER),
/* 165 */   CAVE_SPIDER(SPIDER),
/*     */ 
/*     */   
/* 168 */   ZOMBIE(ABSTRACT_MONSTER),
/* 169 */   DROWNED(ZOMBIE),
/* 170 */   HUSK(ZOMBIE),
/* 171 */   ZOMBIFIED_PIGLIN(ZOMBIE),
/* 172 */   ZOMBIE_VILLAGER(ZOMBIE),
/*     */ 
/*     */   
/* 175 */   ABSTRACT_FLYING(ABSTRACT_INSENTIENT, null),
/* 176 */   GHAST(ABSTRACT_FLYING),
/* 177 */   PHANTOM(ABSTRACT_FLYING),
/*     */   
/* 179 */   ABSTRACT_AMBIENT(ABSTRACT_INSENTIENT, null),
/* 180 */   BAT(ABSTRACT_AMBIENT),
/* 181 */   ALLAY(ABSTRACT_CREATURE),
/*     */   
/* 183 */   ABSTRACT_WATERMOB(ABSTRACT_INSENTIENT, null),
/* 184 */   SQUID(ABSTRACT_WATERMOB),
/* 185 */   GLOW_SQUID(SQUID),
/*     */ 
/*     */   
/* 188 */   SLIME(ABSTRACT_INSENTIENT),
/* 189 */   MAGMA_CUBE(SLIME),
/*     */ 
/*     */   
/* 192 */   ABSTRACT_HANGING(ENTITY, null),
/* 193 */   LEASH_KNOT(ABSTRACT_HANGING),
/* 194 */   ITEM_FRAME(ABSTRACT_HANGING),
/* 195 */   GLOW_ITEM_FRAME(ITEM_FRAME),
/* 196 */   PAINTING(ABSTRACT_HANGING),
/*     */   
/* 198 */   ABSTRACT_LIGHTNING(ENTITY, null),
/* 199 */   LIGHTNING_BOLT(ABSTRACT_LIGHTNING),
/*     */ 
/*     */   
/* 202 */   ABSTRACT_ARROW(ENTITY, null),
/* 203 */   ARROW(ABSTRACT_ARROW),
/* 204 */   SPECTRAL_ARROW(ABSTRACT_ARROW),
/* 205 */   TRIDENT(ABSTRACT_ARROW),
/*     */ 
/*     */   
/* 208 */   ABSTRACT_FIREBALL(ENTITY, null),
/* 209 */   DRAGON_FIREBALL(ABSTRACT_FIREBALL),
/* 210 */   FIREBALL(ABSTRACT_FIREBALL),
/* 211 */   SMALL_FIREBALL(ABSTRACT_FIREBALL),
/* 212 */   WITHER_SKULL(ABSTRACT_FIREBALL),
/*     */ 
/*     */   
/* 215 */   PROJECTILE_ABSTRACT(ENTITY, null),
/* 216 */   SNOWBALL(PROJECTILE_ABSTRACT),
/* 217 */   ENDER_PEARL(PROJECTILE_ABSTRACT),
/* 218 */   EGG(PROJECTILE_ABSTRACT),
/* 219 */   POTION(PROJECTILE_ABSTRACT),
/* 220 */   EXPERIENCE_BOTTLE(PROJECTILE_ABSTRACT),
/*     */ 
/*     */   
/* 223 */   MINECART_ABSTRACT(ENTITY, null),
/* 224 */   CHESTED_MINECART_ABSTRACT(MINECART_ABSTRACT, null),
/* 225 */   CHEST_MINECART(CHESTED_MINECART_ABSTRACT),
/* 226 */   HOPPER_MINECART(CHESTED_MINECART_ABSTRACT),
/* 227 */   MINECART(MINECART_ABSTRACT),
/* 228 */   FURNACE_MINECART(MINECART_ABSTRACT),
/* 229 */   COMMAND_BLOCK_MINECART(MINECART_ABSTRACT),
/* 230 */   TNT_MINECART(MINECART_ABSTRACT),
/* 231 */   SPAWNER_MINECART(MINECART_ABSTRACT),
/* 232 */   BOAT(ENTITY),
/* 233 */   CHEST_BOAT(BOAT);
/*     */   static {
/* 235 */     TYPES = EntityTypeUtil.createSizedArray((EntityType[])values());
/*     */   }
/*     */   
/* 238 */   private int id = -1; private static final EntityType[] TYPES;
/*     */   
/*     */   Entity1_19_4Types(EntityType parent) {
/* 241 */     this.parent = parent;
/* 242 */     this.identifier = "minecraft:" + name().toLowerCase(Locale.ROOT);
/*     */   }
/*     */   private final EntityType parent; private final String identifier;
/*     */   Entity1_19_4Types(EntityType parent, String identifier) {
/* 246 */     this.parent = parent;
/* 247 */     this.identifier = identifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 252 */     if (this.id == -1) {
/* 253 */       throw new IllegalStateException("Ids have not been initialized yet (type " + name() + ")");
/*     */     }
/* 255 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String identifier() {
/* 260 */     Preconditions.checkArgument((this.identifier != null), "Called identifier method on abstract type");
/* 261 */     return this.identifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType getParent() {
/* 266 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAbstractType() {
/* 271 */     return (this.identifier == null);
/*     */   }
/*     */   
/*     */   public static EntityType getTypeFromId(int typeId) {
/* 275 */     return EntityTypeUtil.getTypeFromId(TYPES, typeId, ENTITY);
/*     */   }
/*     */   
/*     */   public static void initialize(Protocol<?, ?, ?, ?> protocol) {
/* 279 */     EntityTypeUtil.initialize((EntityType[])values(), TYPES, protocol, (type, id) -> type.id = id);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\entities\Entity1_19_4Types.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
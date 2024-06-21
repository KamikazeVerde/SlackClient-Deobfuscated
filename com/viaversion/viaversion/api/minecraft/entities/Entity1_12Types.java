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
/*     */ 
/*     */ 
/*     */ public class Entity1_12Types
/*     */ {
/*     */   public static EntityType getTypeFromId(int typeID, boolean isObject) {
/*     */     Optional<EntityType> type;
/*  37 */     if (isObject) {
/*  38 */       type = ObjectType.getPCEntity(typeID);
/*     */     } else {
/*  40 */       type = EntityType.findById(typeID);
/*     */     } 
/*  42 */     if (!type.isPresent()) {
/*  43 */       Via.getPlatform().getLogger().severe("Could not find 1.12 type id " + typeID + " isObject=" + isObject);
/*  44 */       return EntityType.ENTITY;
/*     */     } 
/*     */     
/*  47 */     return type.get();
/*     */   }
/*     */   
/*     */   public enum EntityType implements EntityType {
/*  51 */     ENTITY(-1),
/*  52 */     DROPPED_ITEM(1, ENTITY),
/*  53 */     EXPERIENCE_ORB(2, ENTITY),
/*  54 */     LEASH_HITCH(8, ENTITY),
/*  55 */     PAINTING(9, ENTITY),
/*  56 */     ARROW(10, ENTITY),
/*  57 */     SNOWBALL(11, ENTITY),
/*  58 */     FIREBALL(12, ENTITY),
/*  59 */     SMALL_FIREBALL(13, ENTITY),
/*  60 */     ENDER_PEARL(14, ENTITY),
/*  61 */     ENDER_SIGNAL(15, ENTITY),
/*  62 */     THROWN_EXP_BOTTLE(17, ENTITY),
/*  63 */     ITEM_FRAME(18, ENTITY),
/*  64 */     WITHER_SKULL(19, ENTITY),
/*  65 */     PRIMED_TNT(20, ENTITY),
/*  66 */     FALLING_BLOCK(21, ENTITY),
/*  67 */     FIREWORK(22, ENTITY),
/*  68 */     SPECTRAL_ARROW(24, ARROW),
/*  69 */     SHULKER_BULLET(25, ENTITY),
/*  70 */     DRAGON_FIREBALL(26, FIREBALL),
/*  71 */     EVOCATION_FANGS(33, ENTITY),
/*     */ 
/*     */     
/*  74 */     ENTITY_LIVING(-1, ENTITY),
/*  75 */     ENTITY_INSENTIENT(-1, ENTITY_LIVING),
/*  76 */     ENTITY_AGEABLE(-1, ENTITY_INSENTIENT),
/*  77 */     ENTITY_TAMEABLE_ANIMAL(-1, ENTITY_AGEABLE),
/*  78 */     ENTITY_HUMAN(-1, ENTITY_LIVING),
/*     */     
/*  80 */     ARMOR_STAND(30, ENTITY_LIVING),
/*  81 */     ENTITY_ILLAGER_ABSTRACT(-1, ENTITY_INSENTIENT),
/*  82 */     EVOCATION_ILLAGER(34, ENTITY_ILLAGER_ABSTRACT),
/*  83 */     VEX(35, ENTITY_INSENTIENT),
/*  84 */     VINDICATION_ILLAGER(36, ENTITY_ILLAGER_ABSTRACT),
/*  85 */     ILLUSION_ILLAGER(37, EVOCATION_ILLAGER),
/*     */ 
/*     */     
/*  88 */     MINECART_ABSTRACT(-1, ENTITY),
/*  89 */     MINECART_COMMAND(40, MINECART_ABSTRACT),
/*  90 */     BOAT(41, ENTITY),
/*  91 */     MINECART_RIDEABLE(42, MINECART_ABSTRACT),
/*  92 */     MINECART_CHEST(43, MINECART_ABSTRACT),
/*  93 */     MINECART_FURNACE(44, MINECART_ABSTRACT),
/*  94 */     MINECART_TNT(45, MINECART_ABSTRACT),
/*  95 */     MINECART_HOPPER(46, MINECART_ABSTRACT),
/*  96 */     MINECART_MOB_SPAWNER(47, MINECART_ABSTRACT),
/*     */     
/*  98 */     CREEPER(50, ENTITY_INSENTIENT),
/*     */     
/* 100 */     ABSTRACT_SKELETON(-1, ENTITY_INSENTIENT),
/* 101 */     SKELETON(51, ABSTRACT_SKELETON),
/* 102 */     WITHER_SKELETON(5, ABSTRACT_SKELETON),
/* 103 */     STRAY(6, ABSTRACT_SKELETON),
/*     */     
/* 105 */     SPIDER(52, ENTITY_INSENTIENT),
/* 106 */     GIANT(53, ENTITY_INSENTIENT),
/*     */     
/* 108 */     ZOMBIE(54, ENTITY_INSENTIENT),
/* 109 */     HUSK(23, ZOMBIE),
/* 110 */     ZOMBIE_VILLAGER(27, ZOMBIE),
/*     */     
/* 112 */     SLIME(55, ENTITY_INSENTIENT),
/* 113 */     GHAST(56, ENTITY_INSENTIENT),
/* 114 */     PIG_ZOMBIE(57, ZOMBIE),
/* 115 */     ENDERMAN(58, ENTITY_INSENTIENT),
/* 116 */     CAVE_SPIDER(59, SPIDER),
/* 117 */     SILVERFISH(60, ENTITY_INSENTIENT),
/* 118 */     BLAZE(61, ENTITY_INSENTIENT),
/* 119 */     MAGMA_CUBE(62, SLIME),
/* 120 */     ENDER_DRAGON(63, ENTITY_INSENTIENT),
/* 121 */     WITHER(64, ENTITY_INSENTIENT),
/* 122 */     BAT(65, ENTITY_INSENTIENT),
/* 123 */     WITCH(66, ENTITY_INSENTIENT),
/* 124 */     ENDERMITE(67, ENTITY_INSENTIENT),
/*     */     
/* 126 */     GUARDIAN(68, ENTITY_INSENTIENT),
/* 127 */     ELDER_GUARDIAN(4, GUARDIAN),
/*     */     
/* 129 */     IRON_GOLEM(99, ENTITY_INSENTIENT),
/* 130 */     SHULKER(69, IRON_GOLEM),
/* 131 */     PIG(90, ENTITY_AGEABLE),
/* 132 */     SHEEP(91, ENTITY_AGEABLE),
/* 133 */     COW(92, ENTITY_AGEABLE),
/* 134 */     CHICKEN(93, ENTITY_AGEABLE),
/* 135 */     SQUID(94, ENTITY_INSENTIENT),
/* 136 */     WOLF(95, ENTITY_TAMEABLE_ANIMAL),
/* 137 */     MUSHROOM_COW(96, COW),
/* 138 */     SNOWMAN(97, IRON_GOLEM),
/* 139 */     OCELOT(98, ENTITY_TAMEABLE_ANIMAL),
/* 140 */     PARROT(105, ENTITY_TAMEABLE_ANIMAL),
/*     */     
/* 142 */     ABSTRACT_HORSE(-1, ENTITY_AGEABLE),
/* 143 */     HORSE(100, ABSTRACT_HORSE),
/* 144 */     SKELETON_HORSE(28, ABSTRACT_HORSE),
/* 145 */     ZOMBIE_HORSE(29, ABSTRACT_HORSE),
/*     */     
/* 147 */     CHESTED_HORSE(-1, ABSTRACT_HORSE),
/* 148 */     DONKEY(31, CHESTED_HORSE),
/* 149 */     MULE(32, CHESTED_HORSE),
/* 150 */     LIAMA(103, CHESTED_HORSE),
/*     */ 
/*     */     
/* 153 */     RABBIT(101, ENTITY_AGEABLE),
/* 154 */     POLAR_BEAR(102, ENTITY_AGEABLE),
/* 155 */     VILLAGER(120, ENTITY_AGEABLE),
/* 156 */     ENDER_CRYSTAL(200, ENTITY),
/* 157 */     SPLASH_POTION(-1, ENTITY),
/* 158 */     LINGERING_POTION(-1, SPLASH_POTION),
/* 159 */     AREA_EFFECT_CLOUD(-1, ENTITY),
/* 160 */     EGG(-1, ENTITY),
/* 161 */     FISHING_HOOK(-1, ENTITY),
/* 162 */     LIGHTNING(-1, ENTITY),
/* 163 */     WEATHER(-1, ENTITY),
/* 164 */     PLAYER(-1, ENTITY_HUMAN),
/* 165 */     COMPLEX_PART(-1, ENTITY),
/* 166 */     LIAMA_SPIT(-1, ENTITY);
/*     */     
/* 168 */     private static final Map<Integer, EntityType> TYPES = new HashMap<>();
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
/* 204 */       for (EntityType type : values())
/* 205 */         TYPES.put(Integer.valueOf(type.id), type); 
/*     */     }
/*     */     EntityType(int id) { this.id = id;
/*     */       this.parent = null; }
/*     */     EntityType(int id, EntityType parent) { this.id = id;
/* 210 */       this.parent = parent; } public int getId() { return this.id; } public static Optional<EntityType> findById(int id) { if (id == -1)
/* 211 */         return Optional.empty(); 
/* 212 */       return Optional.ofNullable(TYPES.get(Integer.valueOf(id))); } public EntityType getParent() { return this.parent; } public String identifier() {
/*     */       throw new UnsupportedOperationException();
/*     */     } public boolean isAbstractType() {
/*     */       return (this.id != -1);
/*     */     }
/* 217 */   } public enum ObjectType implements ObjectType { BOAT(1, Entity1_12Types.EntityType.BOAT),
/* 218 */     ITEM(2, Entity1_12Types.EntityType.DROPPED_ITEM),
/* 219 */     AREA_EFFECT_CLOUD(3, Entity1_12Types.EntityType.AREA_EFFECT_CLOUD),
/* 220 */     MINECART(10, Entity1_12Types.EntityType.MINECART_RIDEABLE),
/* 221 */     TNT_PRIMED(50, Entity1_12Types.EntityType.PRIMED_TNT),
/* 222 */     ENDER_CRYSTAL(51, Entity1_12Types.EntityType.ENDER_CRYSTAL),
/* 223 */     TIPPED_ARROW(60, Entity1_12Types.EntityType.ARROW),
/* 224 */     SNOWBALL(61, Entity1_12Types.EntityType.SNOWBALL),
/* 225 */     EGG(62, Entity1_12Types.EntityType.EGG),
/* 226 */     FIREBALL(63, Entity1_12Types.EntityType.FIREBALL),
/* 227 */     SMALL_FIREBALL(64, Entity1_12Types.EntityType.SMALL_FIREBALL),
/* 228 */     ENDER_PEARL(65, Entity1_12Types.EntityType.ENDER_PEARL),
/* 229 */     WITHER_SKULL(66, Entity1_12Types.EntityType.WITHER_SKULL),
/* 230 */     SHULKER_BULLET(67, Entity1_12Types.EntityType.SHULKER_BULLET),
/* 231 */     LIAMA_SPIT(68, Entity1_12Types.EntityType.LIAMA_SPIT),
/* 232 */     FALLING_BLOCK(70, Entity1_12Types.EntityType.FALLING_BLOCK),
/* 233 */     ITEM_FRAME(71, Entity1_12Types.EntityType.ITEM_FRAME),
/* 234 */     ENDER_SIGNAL(72, Entity1_12Types.EntityType.ENDER_SIGNAL),
/* 235 */     POTION(73, Entity1_12Types.EntityType.SPLASH_POTION),
/* 236 */     THROWN_EXP_BOTTLE(75, Entity1_12Types.EntityType.THROWN_EXP_BOTTLE),
/* 237 */     FIREWORK(76, Entity1_12Types.EntityType.FIREWORK),
/* 238 */     LEASH(77, Entity1_12Types.EntityType.LEASH_HITCH),
/* 239 */     ARMOR_STAND(78, Entity1_12Types.EntityType.ARMOR_STAND),
/* 240 */     EVOCATION_FANGS(79, Entity1_12Types.EntityType.EVOCATION_FANGS),
/* 241 */     FISHIHNG_HOOK(90, Entity1_12Types.EntityType.FISHING_HOOK),
/* 242 */     SPECTRAL_ARROW(91, Entity1_12Types.EntityType.SPECTRAL_ARROW),
/* 243 */     DRAGON_FIREBALL(93, Entity1_12Types.EntityType.DRAGON_FIREBALL);
/*     */     
/* 245 */     private static final Map<Integer, ObjectType> TYPES = new HashMap<>();
/*     */     
/*     */     private final int id;
/*     */     private final Entity1_12Types.EntityType type;
/*     */     
/*     */     static {
/* 251 */       for (ObjectType type : values()) {
/* 252 */         TYPES.put(Integer.valueOf(type.id), type);
/*     */       }
/*     */     }
/*     */     
/*     */     ObjectType(int id, Entity1_12Types.EntityType type) {
/* 257 */       this.id = id;
/* 258 */       this.type = type;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getId() {
/* 263 */       return this.id;
/*     */     }
/*     */ 
/*     */     
/*     */     public Entity1_12Types.EntityType getType() {
/* 268 */       return this.type;
/*     */     }
/*     */     
/*     */     public static Optional<ObjectType> findById(int id) {
/* 272 */       if (id == -1)
/* 273 */         return Optional.empty(); 
/* 274 */       return Optional.ofNullable(TYPES.get(Integer.valueOf(id)));
/*     */     }
/*     */     
/*     */     public static Optional<Entity1_12Types.EntityType> getPCEntity(int id) {
/* 278 */       Optional<ObjectType> output = findById(id);
/*     */       
/* 280 */       if (!output.isPresent())
/* 281 */         return Optional.empty(); 
/* 282 */       return Optional.of(((ObjectType)output.get()).type);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\entities\Entity1_12Types.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
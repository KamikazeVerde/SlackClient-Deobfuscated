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
/*     */ public class Entity1_11Types
/*     */ {
/*     */   public static EntityType getTypeFromId(int typeID, boolean isObject) {
/*     */     Optional<EntityType> type;
/*  36 */     if (isObject) {
/*  37 */       type = ObjectType.getPCEntity(typeID);
/*     */     } else {
/*  39 */       type = EntityType.findById(typeID);
/*     */     } 
/*  41 */     if (!type.isPresent()) {
/*  42 */       Via.getPlatform().getLogger().severe("Could not find 1.11 type id " + typeID + " isObject=" + isObject);
/*  43 */       return EntityType.ENTITY;
/*     */     } 
/*     */     
/*  46 */     return type.get();
/*     */   }
/*     */   
/*     */   public enum EntityType implements EntityType {
/*  50 */     ENTITY(-1),
/*  51 */     DROPPED_ITEM(1, ENTITY),
/*  52 */     EXPERIENCE_ORB(2, ENTITY),
/*  53 */     LEASH_HITCH(8, ENTITY),
/*  54 */     PAINTING(9, ENTITY),
/*  55 */     ARROW(10, ENTITY),
/*  56 */     SNOWBALL(11, ENTITY),
/*  57 */     FIREBALL(12, ENTITY),
/*  58 */     SMALL_FIREBALL(13, ENTITY),
/*  59 */     ENDER_PEARL(14, ENTITY),
/*  60 */     ENDER_SIGNAL(15, ENTITY),
/*  61 */     THROWN_EXP_BOTTLE(17, ENTITY),
/*  62 */     ITEM_FRAME(18, ENTITY),
/*  63 */     WITHER_SKULL(19, ENTITY),
/*  64 */     PRIMED_TNT(20, ENTITY),
/*  65 */     FALLING_BLOCK(21, ENTITY),
/*  66 */     FIREWORK(22, ENTITY),
/*  67 */     SPECTRAL_ARROW(24, ARROW),
/*  68 */     SHULKER_BULLET(25, ENTITY),
/*  69 */     DRAGON_FIREBALL(26, FIREBALL),
/*  70 */     EVOCATION_FANGS(33, ENTITY),
/*     */ 
/*     */     
/*  73 */     ENTITY_LIVING(-1, ENTITY),
/*  74 */     ENTITY_INSENTIENT(-1, ENTITY_LIVING),
/*  75 */     ENTITY_AGEABLE(-1, ENTITY_INSENTIENT),
/*  76 */     ENTITY_TAMEABLE_ANIMAL(-1, ENTITY_AGEABLE),
/*  77 */     ENTITY_HUMAN(-1, ENTITY_LIVING),
/*     */     
/*  79 */     ARMOR_STAND(30, ENTITY_LIVING),
/*  80 */     EVOCATION_ILLAGER(34, ENTITY_INSENTIENT),
/*  81 */     VEX(35, ENTITY_INSENTIENT),
/*  82 */     VINDICATION_ILLAGER(36, ENTITY_INSENTIENT),
/*     */ 
/*     */     
/*  85 */     MINECART_ABSTRACT(-1, ENTITY),
/*  86 */     MINECART_COMMAND(40, MINECART_ABSTRACT),
/*  87 */     BOAT(41, ENTITY),
/*  88 */     MINECART_RIDEABLE(42, MINECART_ABSTRACT),
/*  89 */     MINECART_CHEST(43, MINECART_ABSTRACT),
/*  90 */     MINECART_FURNACE(44, MINECART_ABSTRACT),
/*  91 */     MINECART_TNT(45, MINECART_ABSTRACT),
/*  92 */     MINECART_HOPPER(46, MINECART_ABSTRACT),
/*  93 */     MINECART_MOB_SPAWNER(47, MINECART_ABSTRACT),
/*     */     
/*  95 */     CREEPER(50, ENTITY_INSENTIENT),
/*     */     
/*  97 */     ABSTRACT_SKELETON(-1, ENTITY_INSENTIENT),
/*  98 */     SKELETON(51, ABSTRACT_SKELETON),
/*  99 */     WITHER_SKELETON(5, ABSTRACT_SKELETON),
/* 100 */     STRAY(6, ABSTRACT_SKELETON),
/*     */     
/* 102 */     SPIDER(52, ENTITY_INSENTIENT),
/* 103 */     GIANT(53, ENTITY_INSENTIENT),
/*     */     
/* 105 */     ZOMBIE(54, ENTITY_INSENTIENT),
/* 106 */     HUSK(23, ZOMBIE),
/* 107 */     ZOMBIE_VILLAGER(27, ZOMBIE),
/*     */     
/* 109 */     SLIME(55, ENTITY_INSENTIENT),
/* 110 */     GHAST(56, ENTITY_INSENTIENT),
/* 111 */     PIG_ZOMBIE(57, ZOMBIE),
/* 112 */     ENDERMAN(58, ENTITY_INSENTIENT),
/* 113 */     CAVE_SPIDER(59, SPIDER),
/* 114 */     SILVERFISH(60, ENTITY_INSENTIENT),
/* 115 */     BLAZE(61, ENTITY_INSENTIENT),
/* 116 */     MAGMA_CUBE(62, SLIME),
/* 117 */     ENDER_DRAGON(63, ENTITY_INSENTIENT),
/* 118 */     WITHER(64, ENTITY_INSENTIENT),
/* 119 */     BAT(65, ENTITY_INSENTIENT),
/* 120 */     WITCH(66, ENTITY_INSENTIENT),
/* 121 */     ENDERMITE(67, ENTITY_INSENTIENT),
/*     */     
/* 123 */     GUARDIAN(68, ENTITY_INSENTIENT),
/* 124 */     ELDER_GUARDIAN(4, GUARDIAN),
/*     */     
/* 126 */     IRON_GOLEM(99, ENTITY_INSENTIENT),
/* 127 */     SHULKER(69, IRON_GOLEM),
/* 128 */     PIG(90, ENTITY_AGEABLE),
/* 129 */     SHEEP(91, ENTITY_AGEABLE),
/* 130 */     COW(92, ENTITY_AGEABLE),
/* 131 */     CHICKEN(93, ENTITY_AGEABLE),
/* 132 */     SQUID(94, ENTITY_INSENTIENT),
/* 133 */     WOLF(95, ENTITY_TAMEABLE_ANIMAL),
/* 134 */     MUSHROOM_COW(96, COW),
/* 135 */     SNOWMAN(97, IRON_GOLEM),
/* 136 */     OCELOT(98, ENTITY_TAMEABLE_ANIMAL),
/*     */     
/* 138 */     ABSTRACT_HORSE(-1, ENTITY_AGEABLE),
/* 139 */     HORSE(100, ABSTRACT_HORSE),
/* 140 */     SKELETON_HORSE(28, ABSTRACT_HORSE),
/* 141 */     ZOMBIE_HORSE(29, ABSTRACT_HORSE),
/*     */     
/* 143 */     CHESTED_HORSE(-1, ABSTRACT_HORSE),
/* 144 */     DONKEY(31, CHESTED_HORSE),
/* 145 */     MULE(32, CHESTED_HORSE),
/* 146 */     LIAMA(103, CHESTED_HORSE),
/*     */ 
/*     */     
/* 149 */     RABBIT(101, ENTITY_AGEABLE),
/* 150 */     POLAR_BEAR(102, ENTITY_AGEABLE),
/* 151 */     VILLAGER(120, ENTITY_AGEABLE),
/* 152 */     ENDER_CRYSTAL(200, ENTITY),
/* 153 */     SPLASH_POTION(-1, ENTITY),
/* 154 */     LINGERING_POTION(-1, SPLASH_POTION),
/* 155 */     AREA_EFFECT_CLOUD(-1, ENTITY),
/* 156 */     EGG(-1, ENTITY),
/* 157 */     FISHING_HOOK(-1, ENTITY),
/* 158 */     LIGHTNING(-1, ENTITY),
/* 159 */     WEATHER(-1, ENTITY),
/* 160 */     PLAYER(-1, ENTITY_HUMAN),
/* 161 */     COMPLEX_PART(-1, ENTITY),
/* 162 */     LIAMA_SPIT(-1, ENTITY);
/*     */     
/* 164 */     private static final Map<Integer, EntityType> TYPES = new HashMap<>();
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
/* 200 */       for (EntityType type : values())
/* 201 */         TYPES.put(Integer.valueOf(type.id), type); 
/*     */     }
/*     */     EntityType(int id) { this.id = id;
/*     */       this.parent = null; }
/*     */     EntityType(int id, EntityType parent) { this.id = id;
/* 206 */       this.parent = parent; } public int getId() { return this.id; } public static Optional<EntityType> findById(int id) { if (id == -1)
/* 207 */         return Optional.empty(); 
/* 208 */       return Optional.ofNullable(TYPES.get(Integer.valueOf(id))); } public EntityType getParent() { return this.parent; } public String identifier() {
/*     */       throw new UnsupportedOperationException();
/*     */     } public boolean isAbstractType() {
/*     */       return (this.id != -1);
/*     */     }
/* 213 */   } public enum ObjectType implements ObjectType { BOAT(1, Entity1_11Types.EntityType.BOAT),
/* 214 */     ITEM(2, Entity1_11Types.EntityType.DROPPED_ITEM),
/* 215 */     AREA_EFFECT_CLOUD(3, Entity1_11Types.EntityType.AREA_EFFECT_CLOUD),
/* 216 */     MINECART(10, Entity1_11Types.EntityType.MINECART_RIDEABLE),
/* 217 */     TNT_PRIMED(50, Entity1_11Types.EntityType.PRIMED_TNT),
/* 218 */     ENDER_CRYSTAL(51, Entity1_11Types.EntityType.ENDER_CRYSTAL),
/* 219 */     TIPPED_ARROW(60, Entity1_11Types.EntityType.ARROW),
/* 220 */     SNOWBALL(61, Entity1_11Types.EntityType.SNOWBALL),
/* 221 */     EGG(62, Entity1_11Types.EntityType.EGG),
/* 222 */     FIREBALL(63, Entity1_11Types.EntityType.FIREBALL),
/* 223 */     SMALL_FIREBALL(64, Entity1_11Types.EntityType.SMALL_FIREBALL),
/* 224 */     ENDER_PEARL(65, Entity1_11Types.EntityType.ENDER_PEARL),
/* 225 */     WITHER_SKULL(66, Entity1_11Types.EntityType.WITHER_SKULL),
/* 226 */     SHULKER_BULLET(67, Entity1_11Types.EntityType.SHULKER_BULLET),
/* 227 */     LIAMA_SPIT(68, Entity1_11Types.EntityType.LIAMA_SPIT),
/* 228 */     FALLING_BLOCK(70, Entity1_11Types.EntityType.FALLING_BLOCK),
/* 229 */     ITEM_FRAME(71, Entity1_11Types.EntityType.ITEM_FRAME),
/* 230 */     ENDER_SIGNAL(72, Entity1_11Types.EntityType.ENDER_SIGNAL),
/* 231 */     POTION(73, Entity1_11Types.EntityType.SPLASH_POTION),
/* 232 */     THROWN_EXP_BOTTLE(75, Entity1_11Types.EntityType.THROWN_EXP_BOTTLE),
/* 233 */     FIREWORK(76, Entity1_11Types.EntityType.FIREWORK),
/* 234 */     LEASH(77, Entity1_11Types.EntityType.LEASH_HITCH),
/* 235 */     ARMOR_STAND(78, Entity1_11Types.EntityType.ARMOR_STAND),
/* 236 */     EVOCATION_FANGS(79, Entity1_11Types.EntityType.EVOCATION_FANGS),
/* 237 */     FISHIHNG_HOOK(90, Entity1_11Types.EntityType.FISHING_HOOK),
/* 238 */     SPECTRAL_ARROW(91, Entity1_11Types.EntityType.SPECTRAL_ARROW),
/* 239 */     DRAGON_FIREBALL(93, Entity1_11Types.EntityType.DRAGON_FIREBALL);
/*     */     
/* 241 */     private static final Map<Integer, ObjectType> TYPES = new HashMap<>();
/*     */     
/*     */     private final int id;
/*     */     private final Entity1_11Types.EntityType type;
/*     */     
/*     */     static {
/* 247 */       for (ObjectType type : values()) {
/* 248 */         TYPES.put(Integer.valueOf(type.id), type);
/*     */       }
/*     */     }
/*     */     
/*     */     ObjectType(int id, Entity1_11Types.EntityType type) {
/* 253 */       this.id = id;
/* 254 */       this.type = type;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getId() {
/* 259 */       return this.id;
/*     */     }
/*     */ 
/*     */     
/*     */     public Entity1_11Types.EntityType getType() {
/* 264 */       return this.type;
/*     */     }
/*     */     
/*     */     public static Optional<ObjectType> findById(int id) {
/* 268 */       if (id == -1)
/* 269 */         return Optional.empty(); 
/* 270 */       return Optional.ofNullable(TYPES.get(Integer.valueOf(id)));
/*     */     }
/*     */     
/*     */     public static Optional<Entity1_11Types.EntityType> getPCEntity(int id) {
/* 274 */       Optional<ObjectType> output = findById(id);
/*     */       
/* 276 */       if (!output.isPresent())
/* 277 */         return Optional.empty(); 
/* 278 */       return Optional.of(((ObjectType)output.get()).type);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\entities\Entity1_11Types.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
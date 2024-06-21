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
/*     */ public class Entity1_10Types
/*     */ {
/*     */   public static EntityType getTypeFromId(int typeID, boolean isObject) {
/*     */     Optional<EntityType> type;
/*  36 */     if (isObject) {
/*  37 */       type = ObjectType.getPCEntity(typeID);
/*     */     } else {
/*  39 */       type = EntityType.findById(typeID);
/*     */     } 
/*  41 */     if (!type.isPresent()) {
/*  42 */       Via.getPlatform().getLogger().severe("Could not find 1.10 type id " + typeID + " isObject=" + isObject);
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
/*  67 */     TIPPED_ARROW(23, ARROW),
/*  68 */     SPECTRAL_ARROW(24, ARROW),
/*  69 */     SHULKER_BULLET(25, ENTITY),
/*  70 */     DRAGON_FIREBALL(26, FIREBALL),
/*     */     
/*  72 */     ENTITY_LIVING(-1, ENTITY),
/*  73 */     ENTITY_INSENTIENT(-1, ENTITY_LIVING),
/*  74 */     ENTITY_AGEABLE(-1, ENTITY_INSENTIENT),
/*  75 */     ENTITY_TAMEABLE_ANIMAL(-1, ENTITY_AGEABLE),
/*  76 */     ENTITY_HUMAN(-1, ENTITY_LIVING),
/*     */     
/*  78 */     ARMOR_STAND(30, ENTITY_LIVING),
/*     */ 
/*     */     
/*  81 */     MINECART_ABSTRACT(-1, ENTITY),
/*  82 */     MINECART_COMMAND(40, MINECART_ABSTRACT),
/*  83 */     BOAT(41, ENTITY),
/*  84 */     MINECART_RIDEABLE(42, MINECART_ABSTRACT),
/*  85 */     MINECART_CHEST(43, MINECART_ABSTRACT),
/*  86 */     MINECART_FURNACE(44, MINECART_ABSTRACT),
/*  87 */     MINECART_TNT(45, MINECART_ABSTRACT),
/*  88 */     MINECART_HOPPER(46, MINECART_ABSTRACT),
/*  89 */     MINECART_MOB_SPAWNER(47, MINECART_ABSTRACT),
/*     */     
/*  91 */     CREEPER(50, ENTITY_INSENTIENT),
/*  92 */     SKELETON(51, ENTITY_INSENTIENT),
/*  93 */     SPIDER(52, ENTITY_INSENTIENT),
/*  94 */     GIANT(53, ENTITY_INSENTIENT),
/*  95 */     ZOMBIE(54, ENTITY_INSENTIENT),
/*  96 */     SLIME(55, ENTITY_INSENTIENT),
/*  97 */     GHAST(56, ENTITY_INSENTIENT),
/*  98 */     PIG_ZOMBIE(57, ZOMBIE),
/*  99 */     ENDERMAN(58, ENTITY_INSENTIENT),
/* 100 */     CAVE_SPIDER(59, SPIDER),
/* 101 */     SILVERFISH(60, ENTITY_INSENTIENT),
/* 102 */     BLAZE(61, ENTITY_INSENTIENT),
/* 103 */     MAGMA_CUBE(62, SLIME),
/* 104 */     ENDER_DRAGON(63, ENTITY_INSENTIENT),
/* 105 */     WITHER(64, ENTITY_INSENTIENT),
/* 106 */     BAT(65, ENTITY_INSENTIENT),
/* 107 */     WITCH(66, ENTITY_INSENTIENT),
/* 108 */     ENDERMITE(67, ENTITY_INSENTIENT),
/* 109 */     GUARDIAN(68, ENTITY_INSENTIENT),
/* 110 */     IRON_GOLEM(99, ENTITY_INSENTIENT),
/* 111 */     SHULKER(69, IRON_GOLEM),
/* 112 */     PIG(90, ENTITY_AGEABLE),
/* 113 */     SHEEP(91, ENTITY_AGEABLE),
/* 114 */     COW(92, ENTITY_AGEABLE),
/* 115 */     CHICKEN(93, ENTITY_AGEABLE),
/* 116 */     SQUID(94, ENTITY_INSENTIENT),
/* 117 */     WOLF(95, ENTITY_TAMEABLE_ANIMAL),
/* 118 */     MUSHROOM_COW(96, COW),
/* 119 */     SNOWMAN(97, IRON_GOLEM),
/* 120 */     OCELOT(98, ENTITY_TAMEABLE_ANIMAL),
/* 121 */     HORSE(100, ENTITY_AGEABLE),
/* 122 */     RABBIT(101, ENTITY_AGEABLE),
/* 123 */     POLAR_BEAR(102, ENTITY_AGEABLE),
/* 124 */     VILLAGER(120, ENTITY_AGEABLE),
/* 125 */     ENDER_CRYSTAL(200, ENTITY),
/* 126 */     SPLASH_POTION(-1, ENTITY),
/* 127 */     LINGERING_POTION(-1, SPLASH_POTION),
/* 128 */     AREA_EFFECT_CLOUD(-1, ENTITY),
/* 129 */     EGG(-1, ENTITY),
/* 130 */     FISHING_HOOK(-1, ENTITY),
/* 131 */     LIGHTNING(-1, ENTITY),
/* 132 */     WEATHER(-1, ENTITY),
/* 133 */     PLAYER(-1, ENTITY_HUMAN),
/* 134 */     COMPLEX_PART(-1, ENTITY);
/*     */     
/* 136 */     private static final Map<Integer, EntityType> TYPES = new HashMap<>();
/*     */ 
/*     */     
/*     */     private final int id;
/*     */ 
/*     */     
/*     */     private final EntityType parent;
/*     */ 
/*     */     
/*     */     EntityType(int id) {
/*     */       this.id = id;
/*     */       this.parent = null;
/*     */     }
/*     */ 
/*     */     
/*     */     static {
/* 152 */       for (EntityType type : values()) {
/* 153 */         TYPES.put(Integer.valueOf(type.id), type);
/*     */       }
/*     */     }
/*     */     
/*     */     public static Optional<EntityType> findById(int id) {
/* 158 */       if (id == -1)
/* 159 */         return Optional.empty(); 
/* 160 */       return Optional.ofNullable(TYPES.get(Integer.valueOf(id)));
/*     */     }
/*     */ 
/*     */     
/*     */     public int getId() {
/* 165 */       return this.id;
/*     */     } EntityType(int id, EntityType parent) {
/*     */       this.id = id;
/*     */       this.parent = parent;
/*     */     } public EntityType getParent() {
/* 170 */       return this.parent;
/*     */     }
/*     */ 
/*     */     
/*     */     public String identifier() {
/* 175 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isAbstractType() {
/* 180 */       return (this.id != -1);
/*     */     }
/*     */   }
/*     */   
/*     */   public enum ObjectType implements ObjectType {
/* 185 */     BOAT(1, Entity1_10Types.EntityType.BOAT),
/* 186 */     ITEM(2, Entity1_10Types.EntityType.DROPPED_ITEM),
/* 187 */     AREA_EFFECT_CLOUD(3, Entity1_10Types.EntityType.AREA_EFFECT_CLOUD),
/* 188 */     MINECART(10, Entity1_10Types.EntityType.MINECART_RIDEABLE),
/* 189 */     TNT_PRIMED(50, Entity1_10Types.EntityType.PRIMED_TNT),
/* 190 */     ENDER_CRYSTAL(51, Entity1_10Types.EntityType.ENDER_CRYSTAL),
/* 191 */     TIPPED_ARROW(60, Entity1_10Types.EntityType.TIPPED_ARROW),
/* 192 */     SNOWBALL(61, Entity1_10Types.EntityType.SNOWBALL),
/* 193 */     EGG(62, Entity1_10Types.EntityType.EGG),
/* 194 */     FIREBALL(63, Entity1_10Types.EntityType.FIREBALL),
/* 195 */     SMALL_FIREBALL(64, Entity1_10Types.EntityType.SMALL_FIREBALL),
/* 196 */     ENDER_PEARL(65, Entity1_10Types.EntityType.ENDER_PEARL),
/* 197 */     WITHER_SKULL(66, Entity1_10Types.EntityType.WITHER_SKULL),
/* 198 */     SHULKER_BULLET(67, Entity1_10Types.EntityType.SHULKER_BULLET),
/* 199 */     FALLING_BLOCK(70, Entity1_10Types.EntityType.FALLING_BLOCK),
/* 200 */     ITEM_FRAME(71, Entity1_10Types.EntityType.ITEM_FRAME),
/* 201 */     ENDER_SIGNAL(72, Entity1_10Types.EntityType.ENDER_SIGNAL),
/* 202 */     POTION(73, Entity1_10Types.EntityType.SPLASH_POTION),
/* 203 */     THROWN_EXP_BOTTLE(75, Entity1_10Types.EntityType.THROWN_EXP_BOTTLE),
/* 204 */     FIREWORK(76, Entity1_10Types.EntityType.FIREWORK),
/* 205 */     LEASH(77, Entity1_10Types.EntityType.LEASH_HITCH),
/* 206 */     ARMOR_STAND(78, Entity1_10Types.EntityType.ARMOR_STAND),
/* 207 */     FISHIHNG_HOOK(90, Entity1_10Types.EntityType.FISHING_HOOK),
/* 208 */     SPECTRAL_ARROW(91, Entity1_10Types.EntityType.SPECTRAL_ARROW),
/* 209 */     DRAGON_FIREBALL(93, Entity1_10Types.EntityType.DRAGON_FIREBALL);
/*     */     
/* 211 */     private static final Map<Integer, ObjectType> TYPES = new HashMap<>();
/*     */     
/*     */     private final int id;
/*     */     private final Entity1_10Types.EntityType type;
/*     */     
/*     */     static {
/* 217 */       for (ObjectType type : values()) {
/* 218 */         TYPES.put(Integer.valueOf(type.id), type);
/*     */       }
/*     */     }
/*     */     
/*     */     ObjectType(int id, Entity1_10Types.EntityType type) {
/* 223 */       this.id = id;
/* 224 */       this.type = type;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getId() {
/* 229 */       return this.id;
/*     */     }
/*     */ 
/*     */     
/*     */     public Entity1_10Types.EntityType getType() {
/* 234 */       return this.type;
/*     */     }
/*     */     
/*     */     public static Optional<ObjectType> findById(int id) {
/* 238 */       if (id == -1)
/* 239 */         return Optional.empty(); 
/* 240 */       return Optional.ofNullable(TYPES.get(Integer.valueOf(id)));
/*     */     }
/*     */     
/*     */     public static Optional<Entity1_10Types.EntityType> getPCEntity(int id) {
/* 244 */       Optional<ObjectType> output = findById(id);
/*     */       
/* 246 */       if (!output.isPresent())
/* 247 */         return Optional.empty(); 
/* 248 */       return Optional.of(((ObjectType)output.get()).type);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\entities\Entity1_10Types.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
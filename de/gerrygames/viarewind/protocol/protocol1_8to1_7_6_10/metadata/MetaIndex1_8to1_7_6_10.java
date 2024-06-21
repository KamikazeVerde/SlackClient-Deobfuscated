/*     */ package de.gerrygames.viarewind.protocol.protocol1_8to1_7_6_10.metadata;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
/*     */ import com.viaversion.viaversion.util.Pair;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.MetaType1_7_6_10;
/*     */ import java.util.HashMap;
/*     */ import java.util.Optional;
/*     */ 
/*     */ 
/*     */ public enum MetaIndex1_8to1_7_6_10
/*     */ {
/*  13 */   ENTITY_FLAGS(Entity1_10Types.EntityType.ENTITY, 0, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  14 */   ENTITY_AIR(Entity1_10Types.EntityType.ENTITY, 1, MetaType1_7_6_10.Short, MetaType1_8.Short),
/*  15 */   ENTITY_NAME_TAG(Entity1_10Types.EntityType.ENTITY, -1, MetaType1_7_6_10.NonExistent, 2, MetaType1_8.String),
/*  16 */   ENTITY_NAME_TAG_VISIBILITY(Entity1_10Types.EntityType.ENTITY, -1, MetaType1_7_6_10.NonExistent, 3, MetaType1_8.Byte),
/*  17 */   ENTITY_SILENT(Entity1_10Types.EntityType.ENTITY, -1, MetaType1_7_6_10.NonExistent, 4, MetaType1_8.Byte),
/*  18 */   ENTITY_LIVING_HEALTH(Entity1_10Types.EntityType.ENTITY_LIVING, 6, MetaType1_7_6_10.Float, MetaType1_8.Float),
/*  19 */   ENTITY_LIVING_POTION_EFFECT_COLOR(Entity1_10Types.EntityType.ENTITY_LIVING, 7, MetaType1_7_6_10.Int, MetaType1_8.Int),
/*  20 */   ENTITY_LIVING_IS_POTION_EFFECT_AMBIENT(Entity1_10Types.EntityType.ENTITY_LIVING, 8, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  21 */   ENTITY_LIVING_ARROWS(Entity1_10Types.EntityType.ENTITY_LIVING, 9, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  22 */   ENTITY_LIVING_NAME_TAG(Entity1_10Types.EntityType.ENTITY_LIVING, 10, MetaType1_7_6_10.String, 2, MetaType1_8.String),
/*  23 */   ENTITY_LIVING_NAME_TAG_VISIBILITY(Entity1_10Types.EntityType.ENTITY_LIVING, 11, MetaType1_7_6_10.Byte, 3, MetaType1_8.Byte),
/*  24 */   ENTITY_LIVING_AI(Entity1_10Types.EntityType.ENTITY_LIVING, -1, MetaType1_7_6_10.NonExistent, 15, MetaType1_8.Byte),
/*  25 */   ENTITY_AGEABLE_AGE(Entity1_10Types.EntityType.ENTITY_AGEABLE, 12, MetaType1_7_6_10.Int, MetaType1_8.Byte),
/*  26 */   ARMOR_STAND_FLAGS(Entity1_10Types.EntityType.ARMOR_STAND, -1, MetaType1_7_6_10.NonExistent, 10, MetaType1_8.Byte),
/*  27 */   ARMOR_STAND_HEAD_POSITION(Entity1_10Types.EntityType.ARMOR_STAND, -1, MetaType1_7_6_10.NonExistent, 11, MetaType1_8.Rotation),
/*  28 */   ARMOR_STAND_BODY_POSITION(Entity1_10Types.EntityType.ARMOR_STAND, -1, MetaType1_7_6_10.NonExistent, 12, MetaType1_8.Rotation),
/*  29 */   ARMOR_STAND_LEFT_ARM_POSITION(Entity1_10Types.EntityType.ARMOR_STAND, -1, MetaType1_7_6_10.NonExistent, 13, MetaType1_8.Rotation),
/*  30 */   ARMOR_STAND_RIGHT_ARM_POSITION(Entity1_10Types.EntityType.ARMOR_STAND, -1, MetaType1_7_6_10.NonExistent, 14, MetaType1_8.Rotation),
/*  31 */   ARMOR_STAND_LEFT_LEG_POSITION(Entity1_10Types.EntityType.ARMOR_STAND, -1, MetaType1_7_6_10.NonExistent, 15, MetaType1_8.Rotation),
/*  32 */   ARMOR_STAND_RIGHT_LEG_POSITION(Entity1_10Types.EntityType.ARMOR_STAND, -1, MetaType1_7_6_10.NonExistent, 16, MetaType1_8.Rotation),
/*  33 */   HUMAN_SKIN_FLAGS(Entity1_10Types.EntityType.ENTITY_HUMAN, 16, MetaType1_7_6_10.Byte, 10, MetaType1_8.Byte),
/*  34 */   HUMAN_UNUSED(Entity1_10Types.EntityType.ENTITY_HUMAN, -1, MetaType1_7_6_10.NonExistent, 16, MetaType1_8.Byte),
/*  35 */   HUMAN_ABSORPTION_HEATS(Entity1_10Types.EntityType.ENTITY_HUMAN, 17, MetaType1_7_6_10.Float, MetaType1_8.Float),
/*  36 */   HUMAN_SCORE(Entity1_10Types.EntityType.ENTITY_HUMAN, 18, MetaType1_7_6_10.Int, MetaType1_8.Int),
/*  37 */   HORSE_FLAGS(Entity1_10Types.EntityType.HORSE, 16, MetaType1_7_6_10.Int, MetaType1_8.Int),
/*  38 */   HORSE_TYPE(Entity1_10Types.EntityType.HORSE, 19, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  39 */   HORSE_COLOR(Entity1_10Types.EntityType.HORSE, 20, MetaType1_7_6_10.Int, MetaType1_8.Int),
/*  40 */   HORSE_OWNER(Entity1_10Types.EntityType.HORSE, 21, MetaType1_7_6_10.String, MetaType1_8.String),
/*  41 */   HORSE_ARMOR(Entity1_10Types.EntityType.HORSE, 22, MetaType1_7_6_10.Int, MetaType1_8.Int),
/*  42 */   BAT_HANGING(Entity1_10Types.EntityType.BAT, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  43 */   TAMEABLE_FLAGS(Entity1_10Types.EntityType.ENTITY_TAMEABLE_ANIMAL, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  44 */   TAMEABLE_OWNER(Entity1_10Types.EntityType.ENTITY_TAMEABLE_ANIMAL, 17, MetaType1_7_6_10.String, MetaType1_8.String),
/*  45 */   OCELOT_TYPE(Entity1_10Types.EntityType.OCELOT, 18, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  46 */   WOLF_FLAGS(Entity1_10Types.EntityType.WOLF, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  47 */   WOLF_HEALTH(Entity1_10Types.EntityType.WOLF, 18, MetaType1_7_6_10.Float, MetaType1_8.Float),
/*  48 */   WOLF_BEGGING(Entity1_10Types.EntityType.WOLF, 19, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  49 */   WOLF_COLLAR_COLOR(Entity1_10Types.EntityType.WOLF, 20, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  50 */   PIG_SADDLE(Entity1_10Types.EntityType.PIG, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  51 */   SHEEP_COLOR_OR_SHEARED(Entity1_10Types.EntityType.SHEEP, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  52 */   VILLAGER_TYPE(Entity1_10Types.EntityType.VILLAGER, 16, MetaType1_7_6_10.Int, MetaType1_8.Int),
/*  53 */   ENDERMAN_CARRIED_BLOCK(Entity1_10Types.EntityType.ENDERMAN, 16, MetaType1_7_6_10.NonExistent, MetaType1_8.Short),
/*  54 */   ENDERMAN_CARRIED_BLOCK_DATA(Entity1_10Types.EntityType.ENDERMAN, 17, MetaType1_7_6_10.NonExistent, MetaType1_8.Byte),
/*  55 */   ENDERMAN_IS_SCREAMING(Entity1_10Types.EntityType.ENDERMAN, 18, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  56 */   ZOMBIE_CHILD(Entity1_10Types.EntityType.ZOMBIE, 12, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  57 */   ZOMBIE_VILLAGER(Entity1_10Types.EntityType.ZOMBIE, 13, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  58 */   ZOMBIE_CONVERTING(Entity1_10Types.EntityType.ZOMBIE, 14, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  59 */   BLAZE_ON_FIRE(Entity1_10Types.EntityType.BLAZE, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  60 */   SPIDER_CLIMBING(Entity1_10Types.EntityType.SPIDER, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  61 */   CREEPER_STATE(Entity1_10Types.EntityType.CREEPER, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  62 */   CREEPER_POWERED(Entity1_10Types.EntityType.CREEPER, 17, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  63 */   GHAST_STATE(Entity1_10Types.EntityType.GHAST, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  64 */   GHAST_IS_POWERED(Entity1_10Types.EntityType.GHAST, 17, MetaType1_7_6_10.NonExistent, MetaType1_8.Byte),
/*  65 */   SLIME_SIZE(Entity1_10Types.EntityType.SLIME, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  66 */   SKELETON_TYPE(Entity1_10Types.EntityType.SKELETON, 13, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  67 */   WITCH_AGRESSIVE(Entity1_10Types.EntityType.WITCH, 21, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  68 */   IRON_GOLEM_IS_PLAYER_CREATED(Entity1_10Types.EntityType.IRON_GOLEM, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  69 */   WITHER_WATCHED_TAGRET_1(Entity1_10Types.EntityType.WITHER, 17, MetaType1_7_6_10.Int, MetaType1_8.Int),
/*  70 */   WITHER_WATCHED_TAGRET_2(Entity1_10Types.EntityType.WITHER, 18, MetaType1_7_6_10.Int, MetaType1_8.Int),
/*  71 */   WITHER_WATCHED_TAGRET_3(Entity1_10Types.EntityType.WITHER, 19, MetaType1_7_6_10.Int, MetaType1_8.Int),
/*  72 */   WITHER_INVULNERABLE_TIME(Entity1_10Types.EntityType.WITHER, 20, MetaType1_7_6_10.Int, MetaType1_8.Int),
/*  73 */   GUARDIAN_FLAGS(Entity1_10Types.EntityType.GUARDIAN, 16, MetaType1_7_6_10.NonExistent, MetaType1_8.Byte),
/*  74 */   GUARDIAN_TARGET(Entity1_10Types.EntityType.GUARDIAN, 17, MetaType1_7_6_10.NonExistent, MetaType1_8.Int),
/*  75 */   BOAT_TIME_SINCE_HIT(Entity1_10Types.EntityType.BOAT, 17, MetaType1_7_6_10.Int, MetaType1_8.Int),
/*  76 */   BOAT_FORWARD_DIRECTION(Entity1_10Types.EntityType.BOAT, 18, MetaType1_7_6_10.Int, MetaType1_8.Int),
/*  77 */   BOAT_DAMAGE_TAKEN(Entity1_10Types.EntityType.BOAT, 19, MetaType1_7_6_10.Float, MetaType1_8.Float),
/*  78 */   MINECART_SHAKING_POWER(Entity1_10Types.EntityType.MINECART_ABSTRACT, 17, MetaType1_7_6_10.Int, MetaType1_8.Int),
/*  79 */   MINECART_SHAKING_DIRECTION(Entity1_10Types.EntityType.MINECART_ABSTRACT, 18, MetaType1_7_6_10.Int, MetaType1_8.Int),
/*  80 */   MINECART_DAMAGE_TAKEN(Entity1_10Types.EntityType.MINECART_ABSTRACT, 19, MetaType1_7_6_10.Float, MetaType1_8.Float),
/*  81 */   MINECART_BLOCK_INSIDE(Entity1_10Types.EntityType.MINECART_ABSTRACT, 20, MetaType1_7_6_10.Int, MetaType1_8.Int),
/*  82 */   MINECART_BLOCK_Y(Entity1_10Types.EntityType.MINECART_ABSTRACT, 21, MetaType1_7_6_10.Int, MetaType1_8.Int),
/*  83 */   MINECART_SHOW_BLOCK(Entity1_10Types.EntityType.MINECART_ABSTRACT, 22, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  84 */   FURNACE_MINECART_IS_POWERED(Entity1_10Types.EntityType.MINECART_FURNACE, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  85 */   ITEM_ITEM(Entity1_10Types.EntityType.DROPPED_ITEM, 10, MetaType1_7_6_10.Slot, MetaType1_8.Slot),
/*  86 */   ARROW_IS_CRITICAL(Entity1_10Types.EntityType.ARROW, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte),
/*  87 */   FIREWORK_INFO(Entity1_10Types.EntityType.FIREWORK, 8, MetaType1_7_6_10.Slot, MetaType1_8.Slot),
/*  88 */   ITEM_FRAME_ITEM(Entity1_10Types.EntityType.ITEM_FRAME, 2, MetaType1_7_6_10.Slot, 8, MetaType1_8.Slot),
/*  89 */   ITEM_FRAME_ROTATION(Entity1_10Types.EntityType.ITEM_FRAME, 3, MetaType1_7_6_10.Byte, 9, MetaType1_8.Byte),
/*  90 */   ENDER_CRYSTAL_HEALTH(Entity1_10Types.EntityType.ENDER_CRYSTAL, 8, MetaType1_7_6_10.Int, 9, MetaType1_8.Int);
/*     */   
/*     */   static {
/*  93 */     metadataRewrites = new HashMap<>();
/*     */ 
/*     */     
/*  96 */     for (MetaIndex1_8to1_7_6_10 index : values())
/*  97 */       metadataRewrites.put(new Pair(index.getClazz(), Integer.valueOf(index.getIndex())), index); 
/*     */   }
/*     */   private static final HashMap<Pair<Entity1_10Types.EntityType, Integer>, MetaIndex1_8to1_7_6_10> metadataRewrites;
/*     */   private final Entity1_10Types.EntityType clazz;
/*     */   private final int newIndex;
/*     */   private final MetaType1_8 newType;
/*     */   private final MetaType1_7_6_10 oldType;
/*     */   private final int index;
/*     */   
/*     */   MetaIndex1_8to1_7_6_10(Entity1_10Types.EntityType type, int index, MetaType1_7_6_10 oldType, MetaType1_8 newType) {
/* 107 */     this.clazz = type;
/* 108 */     this.index = index;
/* 109 */     this.newIndex = index;
/* 110 */     this.oldType = oldType;
/* 111 */     this.newType = newType;
/*     */   }
/*     */   
/*     */   MetaIndex1_8to1_7_6_10(Entity1_10Types.EntityType type, int index, MetaType1_7_6_10 oldType, int newIndex, MetaType1_8 newType) {
/* 115 */     this.clazz = type;
/* 116 */     this.index = index;
/* 117 */     this.oldType = oldType;
/* 118 */     this.newIndex = newIndex;
/* 119 */     this.newType = newType;
/*     */   }
/*     */   
/*     */   private static Optional<MetaIndex1_8to1_7_6_10> getIndex(Entity1_10Types.EntityType type, int index) {
/* 123 */     Pair pair = new Pair(type, Integer.valueOf(index));
/* 124 */     if (metadataRewrites.containsKey(pair)) {
/* 125 */       return Optional.of(metadataRewrites.get(pair));
/*     */     }
/*     */     
/* 128 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   public Entity1_10Types.EntityType getClazz() {
/* 132 */     return this.clazz;
/*     */   }
/*     */   
/*     */   public int getNewIndex() {
/* 136 */     return this.newIndex;
/*     */   }
/*     */   
/*     */   public MetaType1_8 getNewType() {
/* 140 */     return this.newType;
/*     */   }
/*     */   
/*     */   public MetaType1_7_6_10 getOldType() {
/* 144 */     return this.oldType;
/*     */   }
/*     */   
/*     */   public int getIndex() {
/* 148 */     return this.index;
/*     */   }
/*     */   
/*     */   public static MetaIndex1_8to1_7_6_10 searchIndex(Entity1_10Types.EntityType type, int index) {
/* 152 */     Entity1_10Types.EntityType currentType = type;
/*     */     do {
/* 154 */       Optional<MetaIndex1_8to1_7_6_10> optMeta = getIndex(currentType, index);
/*     */       
/* 156 */       if (optMeta.isPresent()) {
/* 157 */         return optMeta.get();
/*     */       }
/*     */       
/* 160 */       currentType = currentType.getParent();
/* 161 */     } while (currentType != null);
/*     */     
/* 163 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_7_6_10\metadata\MetaIndex1_8to1_7_6_10.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
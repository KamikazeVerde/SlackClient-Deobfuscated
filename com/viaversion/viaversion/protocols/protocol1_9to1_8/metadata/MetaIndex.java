/*     */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
/*     */ import com.viaversion.viaversion.util.Pair;
/*     */ import java.util.HashMap;
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
/*     */ public enum MetaIndex
/*     */ {
/*  34 */   ENTITY_STATUS(Entity1_10Types.EntityType.ENTITY, 0, MetaType1_8.Byte, MetaType1_9.Byte),
/*  35 */   ENTITY_AIR(Entity1_10Types.EntityType.ENTITY, 1, MetaType1_8.Short, MetaType1_9.VarInt),
/*  36 */   ENTITY_NAMETAG(Entity1_10Types.EntityType.ENTITY, 2, MetaType1_8.String, MetaType1_9.String),
/*  37 */   ENTITY_ALWAYS_SHOW_NAMETAG(Entity1_10Types.EntityType.ENTITY, 3, MetaType1_8.Byte, MetaType1_9.Boolean),
/*  38 */   ENTITY_SILENT(Entity1_10Types.EntityType.ENTITY, 4, MetaType1_8.Byte, MetaType1_9.Boolean),
/*     */   
/*  40 */   LIVINGENTITY_HEALTH(Entity1_10Types.EntityType.ENTITY_LIVING, 6, MetaType1_8.Float, MetaType1_9.Float),
/*  41 */   LIVINGENTITY_POTION_EFFECT_COLOR(Entity1_10Types.EntityType.ENTITY_LIVING, 7, MetaType1_8.Int, MetaType1_9.VarInt),
/*  42 */   LIVINGENTITY_IS_POTION_AMBIENT(Entity1_10Types.EntityType.ENTITY_LIVING, 8, MetaType1_8.Byte, MetaType1_9.Boolean),
/*  43 */   LIVINGENTITY_NUMBER_OF_ARROWS_IN(Entity1_10Types.EntityType.ENTITY_LIVING, 9, MetaType1_8.Byte, MetaType1_9.VarInt),
/*  44 */   LIVINGENTITY_NO_AI(Entity1_10Types.EntityType.ENTITY_LIVING, 15, MetaType1_8.Byte, 10, MetaType1_9.Byte),
/*     */   
/*  46 */   AGEABLE_AGE(Entity1_10Types.EntityType.ENTITY_AGEABLE, 12, MetaType1_8.Byte, 11, MetaType1_9.Boolean),
/*     */   
/*  48 */   STAND_INFO(Entity1_10Types.EntityType.ARMOR_STAND, 10, MetaType1_8.Byte, MetaType1_9.Byte),
/*  49 */   STAND_HEAD_POS(Entity1_10Types.EntityType.ARMOR_STAND, 11, MetaType1_8.Rotation, MetaType1_9.Vector3F),
/*  50 */   STAND_BODY_POS(Entity1_10Types.EntityType.ARMOR_STAND, 12, MetaType1_8.Rotation, MetaType1_9.Vector3F),
/*  51 */   STAND_LA_POS(Entity1_10Types.EntityType.ARMOR_STAND, 13, MetaType1_8.Rotation, MetaType1_9.Vector3F),
/*  52 */   STAND_RA_POS(Entity1_10Types.EntityType.ARMOR_STAND, 14, MetaType1_8.Rotation, MetaType1_9.Vector3F),
/*  53 */   STAND_LL_POS(Entity1_10Types.EntityType.ARMOR_STAND, 15, MetaType1_8.Rotation, MetaType1_9.Vector3F),
/*  54 */   STAND_RL_POS(Entity1_10Types.EntityType.ARMOR_STAND, 16, MetaType1_8.Rotation, MetaType1_9.Vector3F),
/*     */   
/*  56 */   PLAYER_SKIN_FLAGS(Entity1_10Types.EntityType.ENTITY_HUMAN, 10, MetaType1_8.Byte, 12, MetaType1_9.Byte),
/*  57 */   PLAYER_HUMAN_BYTE(Entity1_10Types.EntityType.ENTITY_HUMAN, 16, MetaType1_8.Byte, null),
/*  58 */   PLAYER_ADDITIONAL_HEARTS(Entity1_10Types.EntityType.ENTITY_HUMAN, 17, MetaType1_8.Float, 10, MetaType1_9.Float),
/*  59 */   PLAYER_SCORE(Entity1_10Types.EntityType.ENTITY_HUMAN, 18, MetaType1_8.Int, 11, MetaType1_9.VarInt),
/*  60 */   PLAYER_HAND(Entity1_10Types.EntityType.ENTITY_HUMAN, -1, MetaType1_8.NonExistent, 5, MetaType1_9.Byte),
/*  61 */   SOMETHING_ANTICHEAT_PLUGINS_FOR_SOME_REASON_USE(Entity1_10Types.EntityType.ENTITY_HUMAN, 11, MetaType1_8.Byte, null),
/*     */   
/*  63 */   HORSE_INFO(Entity1_10Types.EntityType.HORSE, 16, MetaType1_8.Int, 12, MetaType1_9.Byte),
/*  64 */   HORSE_TYPE(Entity1_10Types.EntityType.HORSE, 19, MetaType1_8.Byte, 13, MetaType1_9.VarInt),
/*  65 */   HORSE_SUBTYPE(Entity1_10Types.EntityType.HORSE, 20, MetaType1_8.Int, 14, MetaType1_9.VarInt),
/*  66 */   HORSE_OWNER(Entity1_10Types.EntityType.HORSE, 21, MetaType1_8.String, 15, MetaType1_9.OptUUID),
/*  67 */   HORSE_ARMOR(Entity1_10Types.EntityType.HORSE, 22, MetaType1_8.Int, 16, MetaType1_9.VarInt),
/*     */   
/*  69 */   BAT_ISHANGING(Entity1_10Types.EntityType.BAT, 16, MetaType1_8.Byte, 11, MetaType1_9.Byte),
/*     */   
/*  71 */   TAMING_INFO(Entity1_10Types.EntityType.ENTITY_TAMEABLE_ANIMAL, 16, MetaType1_8.Byte, 12, MetaType1_9.Byte),
/*  72 */   TAMING_OWNER(Entity1_10Types.EntityType.ENTITY_TAMEABLE_ANIMAL, 17, MetaType1_8.String, 13, MetaType1_9.OptUUID),
/*     */   
/*  74 */   OCELOT_TYPE(Entity1_10Types.EntityType.OCELOT, 18, MetaType1_8.Byte, 14, MetaType1_9.VarInt),
/*     */   
/*  76 */   WOLF_HEALTH(Entity1_10Types.EntityType.WOLF, 18, MetaType1_8.Float, 14, MetaType1_9.Float),
/*  77 */   WOLF_BEGGING(Entity1_10Types.EntityType.WOLF, 19, MetaType1_8.Byte, 15, MetaType1_9.Boolean),
/*  78 */   WOLF_COLLAR(Entity1_10Types.EntityType.WOLF, 20, MetaType1_8.Byte, 16, MetaType1_9.VarInt),
/*     */   
/*  80 */   PIG_SADDLE(Entity1_10Types.EntityType.PIG, 16, MetaType1_8.Byte, 12, MetaType1_9.Boolean),
/*     */   
/*  82 */   RABBIT_TYPE(Entity1_10Types.EntityType.RABBIT, 18, MetaType1_8.Byte, 12, MetaType1_9.VarInt),
/*     */   
/*  84 */   SHEEP_COLOR(Entity1_10Types.EntityType.SHEEP, 16, MetaType1_8.Byte, 12, MetaType1_9.Byte),
/*     */   
/*  86 */   VILLAGER_PROFESSION(Entity1_10Types.EntityType.VILLAGER, 16, MetaType1_8.Int, 12, MetaType1_9.VarInt),
/*     */   
/*  88 */   ENDERMAN_BLOCKSTATE(Entity1_10Types.EntityType.ENDERMAN, 16, MetaType1_8.Short, 11, MetaType1_9.BlockID),
/*  89 */   ENDERMAN_BLOCKDATA(Entity1_10Types.EntityType.ENDERMAN, 17, MetaType1_8.Byte, null),
/*  90 */   ENDERMAN_ISSCREAMING(Entity1_10Types.EntityType.ENDERMAN, 18, MetaType1_8.Byte, 12, MetaType1_9.Boolean),
/*     */   
/*  92 */   ZOMBIE_ISCHILD(Entity1_10Types.EntityType.ZOMBIE, 12, MetaType1_8.Byte, 11, MetaType1_9.Boolean),
/*  93 */   ZOMBIE_ISVILLAGER(Entity1_10Types.EntityType.ZOMBIE, 13, MetaType1_8.Byte, 12, MetaType1_9.VarInt),
/*  94 */   ZOMBIE_ISCONVERTING(Entity1_10Types.EntityType.ZOMBIE, 14, MetaType1_8.Byte, 13, MetaType1_9.Boolean),
/*     */ 
/*     */   
/*  97 */   BLAZE_ONFIRE(Entity1_10Types.EntityType.BLAZE, 16, MetaType1_8.Byte, 11, MetaType1_9.Byte),
/*     */   
/*  99 */   SPIDER_CIMBING(Entity1_10Types.EntityType.SPIDER, 16, MetaType1_8.Byte, 11, MetaType1_9.Byte),
/*     */   
/* 101 */   CREEPER_FUSE(Entity1_10Types.EntityType.CREEPER, 16, MetaType1_8.Byte, 11, MetaType1_9.VarInt),
/* 102 */   CREEPER_ISPOWERED(Entity1_10Types.EntityType.CREEPER, 17, MetaType1_8.Byte, 12, MetaType1_9.Boolean),
/* 103 */   CREEPER_ISIGNITED(Entity1_10Types.EntityType.CREEPER, 18, MetaType1_8.Byte, 13, MetaType1_9.Boolean),
/*     */   
/* 105 */   GHAST_ISATTACKING(Entity1_10Types.EntityType.GHAST, 16, MetaType1_8.Byte, 11, MetaType1_9.Boolean),
/*     */   
/* 107 */   SLIME_SIZE(Entity1_10Types.EntityType.SLIME, 16, MetaType1_8.Byte, 11, MetaType1_9.VarInt),
/*     */   
/* 109 */   SKELETON_TYPE(Entity1_10Types.EntityType.SKELETON, 13, MetaType1_8.Byte, 11, MetaType1_9.VarInt),
/*     */   
/* 111 */   WITCH_AGGRO(Entity1_10Types.EntityType.WITCH, 21, MetaType1_8.Byte, 11, MetaType1_9.Boolean),
/*     */   
/* 113 */   IRON_PLAYERMADE(Entity1_10Types.EntityType.IRON_GOLEM, 16, MetaType1_8.Byte, 11, MetaType1_9.Byte),
/*     */   
/* 115 */   WITHER_TARGET1(Entity1_10Types.EntityType.WITHER, 17, MetaType1_8.Int, 11, MetaType1_9.VarInt),
/* 116 */   WITHER_TARGET2(Entity1_10Types.EntityType.WITHER, 18, MetaType1_8.Int, 12, MetaType1_9.VarInt),
/* 117 */   WITHER_TARGET3(Entity1_10Types.EntityType.WITHER, 19, MetaType1_8.Int, 13, MetaType1_9.VarInt),
/* 118 */   WITHER_INVULN_TIME(Entity1_10Types.EntityType.WITHER, 20, MetaType1_8.Int, 14, MetaType1_9.VarInt),
/* 119 */   WITHER_PROPERTIES(Entity1_10Types.EntityType.WITHER, 10, MetaType1_8.Byte, null),
/* 120 */   WITHER_UNKNOWN(Entity1_10Types.EntityType.WITHER, 11, MetaType1_8.NonExistent, null),
/*     */   
/* 122 */   WITHERSKULL_INVULN(Entity1_10Types.EntityType.WITHER_SKULL, 10, MetaType1_8.Byte, 5, MetaType1_9.Boolean),
/*     */   
/* 124 */   GUARDIAN_INFO(Entity1_10Types.EntityType.GUARDIAN, 16, MetaType1_8.Int, 11, MetaType1_9.Byte),
/* 125 */   GUARDIAN_TARGET(Entity1_10Types.EntityType.GUARDIAN, 17, MetaType1_8.Int, 12, MetaType1_9.VarInt),
/*     */   
/* 127 */   BOAT_SINCEHIT(Entity1_10Types.EntityType.BOAT, 17, MetaType1_8.Int, 5, MetaType1_9.VarInt),
/* 128 */   BOAT_FORWARDDIR(Entity1_10Types.EntityType.BOAT, 18, MetaType1_8.Int, 6, MetaType1_9.VarInt),
/* 129 */   BOAT_DMGTAKEN(Entity1_10Types.EntityType.BOAT, 19, MetaType1_8.Float, 7, MetaType1_9.Float),
/*     */ 
/*     */   
/* 132 */   MINECART_SHAKINGPOWER(Entity1_10Types.EntityType.MINECART_ABSTRACT, 17, MetaType1_8.Int, 5, MetaType1_9.VarInt),
/* 133 */   MINECART_SHAKINGDIRECTION(Entity1_10Types.EntityType.MINECART_ABSTRACT, 18, MetaType1_8.Int, 6, MetaType1_9.VarInt),
/* 134 */   MINECART_DAMAGETAKEN(Entity1_10Types.EntityType.MINECART_ABSTRACT, 19, MetaType1_8.Float, 7, MetaType1_9.Float),
/* 135 */   MINECART_BLOCK(Entity1_10Types.EntityType.MINECART_ABSTRACT, 20, MetaType1_8.Int, 8, MetaType1_9.VarInt),
/* 136 */   MINECART_BLOCK_Y(Entity1_10Types.EntityType.MINECART_ABSTRACT, 21, MetaType1_8.Int, 9, MetaType1_9.VarInt),
/* 137 */   MINECART_SHOWBLOCK(Entity1_10Types.EntityType.MINECART_ABSTRACT, 22, MetaType1_8.Byte, 10, MetaType1_9.Boolean),
/*     */   
/* 139 */   MINECART_COMMANDBLOCK_COMMAND(Entity1_10Types.EntityType.MINECART_ABSTRACT, 23, MetaType1_8.String, 11, MetaType1_9.String),
/* 140 */   MINECART_COMMANDBLOCK_OUTPUT(Entity1_10Types.EntityType.MINECART_ABSTRACT, 24, MetaType1_8.String, 12, MetaType1_9.Chat),
/*     */   
/* 142 */   FURNACECART_ISPOWERED(Entity1_10Types.EntityType.MINECART_ABSTRACT, 16, MetaType1_8.Byte, 11, MetaType1_9.Boolean),
/*     */   
/* 144 */   ITEM_ITEM(Entity1_10Types.EntityType.DROPPED_ITEM, 10, MetaType1_8.Slot, 5, MetaType1_9.Slot),
/*     */   
/* 146 */   ARROW_ISCRIT(Entity1_10Types.EntityType.ARROW, 16, MetaType1_8.Byte, 5, MetaType1_9.Byte),
/*     */   
/* 148 */   FIREWORK_INFO(Entity1_10Types.EntityType.FIREWORK, 8, MetaType1_8.Slot, 5, MetaType1_9.Slot),
/*     */   
/* 150 */   ITEMFRAME_ITEM(Entity1_10Types.EntityType.ITEM_FRAME, 8, MetaType1_8.Slot, 5, MetaType1_9.Slot),
/* 151 */   ITEMFRAME_ROTATION(Entity1_10Types.EntityType.ITEM_FRAME, 9, MetaType1_8.Byte, 6, MetaType1_9.VarInt),
/*     */   
/* 153 */   ENDERCRYSTAL_HEALTH(Entity1_10Types.EntityType.ENDER_CRYSTAL, 8, MetaType1_8.Int, null),
/*     */   
/* 155 */   ENDERDRAGON_UNKNOWN(Entity1_10Types.EntityType.ENDER_DRAGON, 5, MetaType1_8.Byte, null),
/* 156 */   ENDERDRAGON_NAME(Entity1_10Types.EntityType.ENDER_DRAGON, 10, MetaType1_8.String, null),
/*     */   
/* 158 */   ENDERDRAGON_FLAG(Entity1_10Types.EntityType.ENDER_DRAGON, 15, MetaType1_8.Byte, null),
/* 159 */   ENDERDRAGON_PHASE(Entity1_10Types.EntityType.ENDER_DRAGON, 11, MetaType1_8.Byte, MetaType1_9.VarInt);
/*     */   static {
/* 161 */     metadataRewrites = new HashMap<>();
/*     */ 
/*     */     
/* 164 */     for (MetaIndex index : values())
/* 165 */       metadataRewrites.put(new Pair(index.clazz, Integer.valueOf(index.index)), index); 
/*     */   }
/*     */   private static final HashMap<Pair<Entity1_10Types.EntityType, Integer>, MetaIndex> metadataRewrites;
/*     */   private final Entity1_10Types.EntityType clazz;
/*     */   private final int newIndex;
/*     */   private final MetaType1_9 newType;
/*     */   private final MetaType1_8 oldType;
/*     */   private final int index;
/*     */   
/*     */   MetaIndex(Entity1_10Types.EntityType type, int index, MetaType1_8 oldType, MetaType1_9 newType) {
/* 175 */     this.clazz = type;
/* 176 */     this.index = index;
/* 177 */     this.newIndex = index;
/* 178 */     this.oldType = oldType;
/* 179 */     this.newType = newType;
/*     */   }
/*     */   
/*     */   MetaIndex(Entity1_10Types.EntityType type, int index, MetaType1_8 oldType, int newIndex, MetaType1_9 newType) {
/* 183 */     this.clazz = type;
/* 184 */     this.index = index;
/* 185 */     this.oldType = oldType;
/* 186 */     this.newIndex = newIndex;
/* 187 */     this.newType = newType;
/*     */   }
/*     */   
/*     */   public Entity1_10Types.EntityType getClazz() {
/* 191 */     return this.clazz;
/*     */   }
/*     */   
/*     */   public int getNewIndex() {
/* 195 */     return this.newIndex;
/*     */   }
/*     */   
/*     */   public MetaType1_9 getNewType() {
/* 199 */     return this.newType;
/*     */   }
/*     */   
/*     */   public MetaType1_8 getOldType() {
/* 203 */     return this.oldType;
/*     */   }
/*     */   
/*     */   public int getIndex() {
/* 207 */     return this.index;
/*     */   }
/*     */   
/*     */   private static Optional<MetaIndex> getIndex(EntityType type, int index) {
/* 211 */     Pair pair = new Pair(type, Integer.valueOf(index));
/* 212 */     return Optional.ofNullable(metadataRewrites.get(pair));
/*     */   }
/*     */   
/*     */   public static MetaIndex searchIndex(EntityType type, int index) {
/* 216 */     EntityType currentType = type;
/*     */     do {
/* 218 */       Optional<MetaIndex> optMeta = getIndex(currentType, index);
/*     */       
/* 220 */       if (optMeta.isPresent()) {
/* 221 */         return optMeta.get();
/*     */       }
/*     */       
/* 224 */       currentType = currentType.getParent();
/* 225 */     } while (currentType != null);
/*     */     
/* 227 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\metadata\MetaIndex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
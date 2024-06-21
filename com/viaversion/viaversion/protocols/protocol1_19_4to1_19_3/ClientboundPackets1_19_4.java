/*     */ package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3;
/*     */ 
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
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
/*     */ public enum ClientboundPackets1_19_4
/*     */   implements ClientboundPacketType
/*     */ {
/*  24 */   BUNDLE,
/*  25 */   SPAWN_ENTITY,
/*  26 */   SPAWN_EXPERIENCE_ORB,
/*  27 */   SPAWN_PLAYER,
/*  28 */   ENTITY_ANIMATION,
/*  29 */   STATISTICS,
/*  30 */   BLOCK_CHANGED_ACK,
/*  31 */   BLOCK_BREAK_ANIMATION,
/*  32 */   BLOCK_ENTITY_DATA,
/*  33 */   BLOCK_ACTION,
/*  34 */   BLOCK_CHANGE,
/*  35 */   BOSSBAR,
/*  36 */   SERVER_DIFFICULTY,
/*  37 */   CHUNK_BIOMES,
/*  38 */   CLEAR_TITLES,
/*  39 */   TAB_COMPLETE,
/*  40 */   DECLARE_COMMANDS,
/*  41 */   CLOSE_WINDOW,
/*  42 */   WINDOW_ITEMS,
/*  43 */   WINDOW_PROPERTY,
/*  44 */   SET_SLOT,
/*  45 */   COOLDOWN,
/*  46 */   CUSTOM_CHAT_COMPLETIONS,
/*  47 */   PLUGIN_MESSAGE,
/*  48 */   DAMAGE_EVENT,
/*  49 */   DELETE_CHAT_MESSAGE,
/*  50 */   DISCONNECT,
/*  51 */   DISGUISED_CHAT,
/*  52 */   ENTITY_STATUS,
/*  53 */   EXPLOSION,
/*  54 */   UNLOAD_CHUNK,
/*  55 */   GAME_EVENT,
/*  56 */   OPEN_HORSE_WINDOW,
/*  57 */   HIT_ANIMATION,
/*  58 */   WORLD_BORDER_INIT,
/*  59 */   KEEP_ALIVE,
/*  60 */   CHUNK_DATA,
/*  61 */   EFFECT,
/*  62 */   SPAWN_PARTICLE,
/*  63 */   UPDATE_LIGHT,
/*  64 */   JOIN_GAME,
/*  65 */   MAP_DATA,
/*  66 */   TRADE_LIST,
/*  67 */   ENTITY_POSITION,
/*  68 */   ENTITY_POSITION_AND_ROTATION,
/*  69 */   ENTITY_ROTATION,
/*  70 */   VEHICLE_MOVE,
/*  71 */   OPEN_BOOK,
/*  72 */   OPEN_WINDOW,
/*  73 */   OPEN_SIGN_EDITOR,
/*  74 */   PING,
/*  75 */   CRAFT_RECIPE_RESPONSE,
/*  76 */   PLAYER_ABILITIES,
/*  77 */   PLAYER_CHAT,
/*  78 */   COMBAT_END,
/*  79 */   COMBAT_ENTER,
/*  80 */   COMBAT_KILL,
/*  81 */   PLAYER_INFO_REMOVE,
/*  82 */   PLAYER_INFO_UPDATE,
/*  83 */   FACE_PLAYER,
/*  84 */   PLAYER_POSITION,
/*  85 */   UNLOCK_RECIPES,
/*  86 */   REMOVE_ENTITIES,
/*  87 */   REMOVE_ENTITY_EFFECT,
/*  88 */   RESOURCE_PACK,
/*  89 */   RESPAWN,
/*  90 */   ENTITY_HEAD_LOOK,
/*  91 */   MULTI_BLOCK_CHANGE,
/*  92 */   SELECT_ADVANCEMENTS_TAB,
/*  93 */   SERVER_DATA,
/*  94 */   ACTIONBAR,
/*  95 */   WORLD_BORDER_CENTER,
/*  96 */   WORLD_BORDER_LERP_SIZE,
/*  97 */   WORLD_BORDER_SIZE,
/*  98 */   WORLD_BORDER_WARNING_DELAY,
/*  99 */   WORLD_BORDER_WARNING_DISTANCE,
/* 100 */   CAMERA,
/* 101 */   HELD_ITEM_CHANGE,
/* 102 */   UPDATE_VIEW_POSITION,
/* 103 */   UPDATE_VIEW_DISTANCE,
/* 104 */   SPAWN_POSITION,
/* 105 */   DISPLAY_SCOREBOARD,
/* 106 */   ENTITY_METADATA,
/* 107 */   ATTACH_ENTITY,
/* 108 */   ENTITY_VELOCITY,
/* 109 */   ENTITY_EQUIPMENT,
/* 110 */   SET_EXPERIENCE,
/* 111 */   UPDATE_HEALTH,
/* 112 */   SCOREBOARD_OBJECTIVE,
/* 113 */   SET_PASSENGERS,
/* 114 */   TEAMS,
/* 115 */   UPDATE_SCORE,
/* 116 */   SET_SIMULATION_DISTANCE,
/* 117 */   TITLE_SUBTITLE,
/* 118 */   TIME_UPDATE,
/* 119 */   TITLE_TEXT,
/* 120 */   TITLE_TIMES,
/* 121 */   ENTITY_SOUND,
/* 122 */   SOUND,
/* 123 */   STOP_SOUND,
/* 124 */   SYSTEM_CHAT,
/* 125 */   TAB_LIST,
/* 126 */   NBT_QUERY,
/* 127 */   COLLECT_ITEM,
/* 128 */   ENTITY_TELEPORT,
/* 129 */   ADVANCEMENTS,
/* 130 */   ENTITY_PROPERTIES,
/* 131 */   UPDATE_ENABLED_FEATURES,
/* 132 */   ENTITY_EFFECT,
/* 133 */   DECLARE_RECIPES,
/* 134 */   TAGS;
/*     */ 
/*     */   
/*     */   public int getId() {
/* 138 */     return ordinal();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 143 */     return name();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19_4to1_19_3\ClientboundPackets1_19_4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
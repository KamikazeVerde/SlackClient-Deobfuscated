/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ 
/*    */ public enum ClientboundPackets1_7 implements ClientboundPacketType {
/*  6 */   KEEP_ALIVE,
/*  7 */   JOIN_GAME,
/*  8 */   CHAT_MESSAGE,
/*  9 */   TIME_UPDATE,
/* 10 */   ENTITY_EQUIPMENT,
/* 11 */   SPAWN_POSITION,
/* 12 */   UPDATE_HEALTH,
/* 13 */   RESPAWN,
/* 14 */   PLAYER_POSITION,
/* 15 */   HELD_ITEM_CHANGE,
/* 16 */   USE_BED,
/* 17 */   ENTITY_ANIMATION,
/* 18 */   SPAWN_PLAYER,
/* 19 */   COLLECT_ITEM,
/* 20 */   SPAWN_ENTITY,
/* 21 */   SPAWN_MOB,
/* 22 */   SPAWN_PAINTING,
/* 23 */   SPAWN_EXPERIENCE_ORB,
/* 24 */   ENTITY_VELOCITY,
/* 25 */   DESTROY_ENTITIES,
/* 26 */   ENTITY_MOVEMENT,
/* 27 */   ENTITY_POSITION,
/* 28 */   ENTITY_ROTATION,
/* 29 */   ENTITY_POSITION_AND_ROTATION,
/* 30 */   ENTITY_TELEPORT,
/* 31 */   ENTITY_HEAD_LOOK,
/* 32 */   ENTITY_STATUS,
/* 33 */   ATTACH_ENTITY,
/* 34 */   ENTITY_METADATA,
/* 35 */   ENTITY_EFFECT,
/* 36 */   REMOVE_ENTITY_EFFECT,
/* 37 */   SET_EXPERIENCE,
/* 38 */   ENTITY_PROPERTIES,
/* 39 */   CHUNK_DATA,
/* 40 */   MULTI_BLOCK_CHANGE,
/* 41 */   BLOCK_CHANGE,
/* 42 */   BLOCK_ACTION,
/* 43 */   BLOCK_BREAK_ANIMATION,
/* 44 */   MAP_BULK_CHUNK,
/* 45 */   EXPLOSION,
/* 46 */   EFFECT,
/* 47 */   NAMED_SOUND,
/* 48 */   SPAWN_PARTICLE,
/* 49 */   GAME_EVENT,
/* 50 */   SPAWN_GLOBAL_ENTITY,
/* 51 */   OPEN_WINDOW,
/* 52 */   CLOSE_WINDOW,
/* 53 */   SET_SLOT,
/* 54 */   WINDOW_ITEMS,
/* 55 */   WINDOW_PROPERTY,
/* 56 */   WINDOW_CONFIRMATION,
/* 57 */   UPDATE_SIGN,
/* 58 */   MAP_DATA,
/* 59 */   BLOCK_ENTITY_DATA,
/* 60 */   OPEN_SIGN_EDITOR,
/* 61 */   STATISTICS,
/* 62 */   PLAYER_INFO,
/* 63 */   PLAYER_ABILITIES,
/* 64 */   TAB_COMPLETE,
/* 65 */   SCOREBOARD_OBJECTIVE,
/* 66 */   UPDATE_SCORE,
/* 67 */   DISPLAY_SCOREBOARD,
/* 68 */   TEAMS,
/* 69 */   PLUGIN_MESSAGE,
/* 70 */   DISCONNECT;
/*    */ 
/*    */   
/*    */   public int getId() {
/* 74 */     return ordinal();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 79 */     return name();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\ClientboundPackets1_7.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
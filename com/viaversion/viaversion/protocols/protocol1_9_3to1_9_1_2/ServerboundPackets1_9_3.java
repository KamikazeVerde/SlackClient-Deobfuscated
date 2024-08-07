/*    */ package com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum ServerboundPackets1_9_3
/*    */   implements ServerboundPacketType
/*    */ {
/* 24 */   TELEPORT_CONFIRM,
/* 25 */   TAB_COMPLETE,
/* 26 */   CHAT_MESSAGE,
/* 27 */   CLIENT_STATUS,
/* 28 */   CLIENT_SETTINGS,
/* 29 */   WINDOW_CONFIRMATION,
/* 30 */   CLICK_WINDOW_BUTTON,
/* 31 */   CLICK_WINDOW,
/* 32 */   CLOSE_WINDOW,
/* 33 */   PLUGIN_MESSAGE,
/* 34 */   INTERACT_ENTITY,
/* 35 */   KEEP_ALIVE,
/* 36 */   PLAYER_POSITION,
/* 37 */   PLAYER_POSITION_AND_ROTATION,
/* 38 */   PLAYER_ROTATION,
/* 39 */   PLAYER_MOVEMENT,
/* 40 */   VEHICLE_MOVE,
/* 41 */   STEER_BOAT,
/* 42 */   PLAYER_ABILITIES,
/* 43 */   PLAYER_DIGGING,
/* 44 */   ENTITY_ACTION,
/* 45 */   STEER_VEHICLE,
/* 46 */   RESOURCE_PACK_STATUS,
/* 47 */   HELD_ITEM_CHANGE,
/* 48 */   CREATIVE_INVENTORY_ACTION,
/* 49 */   UPDATE_SIGN,
/* 50 */   ANIMATION,
/* 51 */   SPECTATE,
/* 52 */   PLAYER_BLOCK_PLACEMENT,
/* 53 */   USE_ITEM;
/*    */ 
/*    */   
/*    */   public int getId() {
/* 57 */     return ordinal();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 62 */     return name();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9_3to1_9_1_2\ServerboundPackets1_9_3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*    */ 
/*    */ public enum ServerboundPackets1_7 implements ServerboundPacketType {
/*  6 */   KEEP_ALIVE,
/*  7 */   CHAT_MESSAGE,
/*  8 */   INTERACT_ENTITY,
/*  9 */   PLAYER_MOVEMENT,
/* 10 */   PLAYER_POSITION,
/* 11 */   PLAYER_ROTATION,
/* 12 */   PLAYER_POSITION_AND_ROTATION,
/* 13 */   PLAYER_DIGGING,
/* 14 */   PLAYER_BLOCK_PLACEMENT,
/* 15 */   HELD_ITEM_CHANGE,
/* 16 */   ANIMATION,
/* 17 */   ENTITY_ACTION,
/* 18 */   STEER_VEHICLE,
/* 19 */   CLOSE_WINDOW,
/* 20 */   CLICK_WINDOW,
/* 21 */   WINDOW_CONFIRMATION,
/* 22 */   CREATIVE_INVENTORY_ACTION,
/* 23 */   CLICK_WINDOW_BUTTON,
/* 24 */   UPDATE_SIGN,
/* 25 */   PLAYER_ABILITIES,
/* 26 */   TAB_COMPLETE,
/* 27 */   CLIENT_SETTINGS,
/* 28 */   CLIENT_STATUS,
/* 29 */   PLUGIN_MESSAGE;
/*    */ 
/*    */   
/*    */   public int getId() {
/* 33 */     return ordinal();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 38 */     return name();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\ServerboundPackets1_7.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
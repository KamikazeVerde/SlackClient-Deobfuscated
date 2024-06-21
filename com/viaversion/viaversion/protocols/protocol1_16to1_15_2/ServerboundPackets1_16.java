/*    */ package com.viaversion.viaversion.protocols.protocol1_16to1_15_2;
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
/*    */ public enum ServerboundPackets1_16
/*    */   implements ServerboundPacketType
/*    */ {
/* 24 */   TELEPORT_CONFIRM,
/* 25 */   QUERY_BLOCK_NBT,
/* 26 */   SET_DIFFICULTY,
/* 27 */   CHAT_MESSAGE,
/* 28 */   CLIENT_STATUS,
/* 29 */   CLIENT_SETTINGS,
/* 30 */   TAB_COMPLETE,
/* 31 */   WINDOW_CONFIRMATION,
/* 32 */   CLICK_WINDOW_BUTTON,
/* 33 */   CLICK_WINDOW,
/* 34 */   CLOSE_WINDOW,
/* 35 */   PLUGIN_MESSAGE,
/* 36 */   EDIT_BOOK,
/* 37 */   ENTITY_NBT_REQUEST,
/* 38 */   INTERACT_ENTITY,
/* 39 */   GENERATE_JIGSAW,
/* 40 */   KEEP_ALIVE,
/* 41 */   LOCK_DIFFICULTY,
/* 42 */   PLAYER_POSITION,
/* 43 */   PLAYER_POSITION_AND_ROTATION,
/* 44 */   PLAYER_ROTATION,
/* 45 */   PLAYER_MOVEMENT,
/* 46 */   VEHICLE_MOVE,
/* 47 */   STEER_BOAT,
/* 48 */   PICK_ITEM,
/* 49 */   CRAFT_RECIPE_REQUEST,
/* 50 */   PLAYER_ABILITIES,
/* 51 */   PLAYER_DIGGING,
/* 52 */   ENTITY_ACTION,
/* 53 */   STEER_VEHICLE,
/* 54 */   RECIPE_BOOK_DATA,
/* 55 */   RENAME_ITEM,
/* 56 */   RESOURCE_PACK_STATUS,
/* 57 */   ADVANCEMENT_TAB,
/* 58 */   SELECT_TRADE,
/* 59 */   SET_BEACON_EFFECT,
/* 60 */   HELD_ITEM_CHANGE,
/* 61 */   UPDATE_COMMAND_BLOCK,
/* 62 */   UPDATE_COMMAND_BLOCK_MINECART,
/* 63 */   CREATIVE_INVENTORY_ACTION,
/* 64 */   UPDATE_JIGSAW_BLOCK,
/* 65 */   UPDATE_STRUCTURE_BLOCK,
/* 66 */   UPDATE_SIGN,
/* 67 */   ANIMATION,
/* 68 */   SPECTATE,
/* 69 */   PLAYER_BLOCK_PLACEMENT,
/* 70 */   USE_ITEM;
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


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_16to1_15_2\ServerboundPackets1_16.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
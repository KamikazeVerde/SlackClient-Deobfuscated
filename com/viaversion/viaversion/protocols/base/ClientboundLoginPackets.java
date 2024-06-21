/*    */ package com.viaversion.viaversion.protocols.base;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.State;
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
/*    */ public enum ClientboundLoginPackets
/*    */   implements ClientboundPacketType
/*    */ {
/* 24 */   LOGIN_DISCONNECT,
/* 25 */   HELLO,
/* 26 */   GAME_PROFILE,
/* 27 */   LOGIN_COMPRESSION,
/* 28 */   CUSTOM_QUERY;
/*    */ 
/*    */   
/*    */   public final int getId() {
/* 32 */     return ordinal();
/*    */   }
/*    */ 
/*    */   
/*    */   public final String getName() {
/* 37 */     return name();
/*    */   }
/*    */ 
/*    */   
/*    */   public final State state() {
/* 42 */     return State.LOGIN;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\base\ClientboundLoginPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
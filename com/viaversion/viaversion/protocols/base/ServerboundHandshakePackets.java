/*    */ package com.viaversion.viaversion.protocols.base;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
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
/*    */ public enum ServerboundHandshakePackets
/*    */   implements ServerboundPacketType
/*    */ {
/* 24 */   CLIENT_INTENTION;
/*    */ 
/*    */   
/*    */   public final int getId() {
/* 28 */     return ordinal();
/*    */   }
/*    */ 
/*    */   
/*    */   public final String getName() {
/* 33 */     return name();
/*    */   }
/*    */ 
/*    */   
/*    */   public final State state() {
/* 38 */     return State.HANDSHAKE;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\base\ServerboundHandshakePackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
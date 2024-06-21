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
/*    */ public enum ServerboundStatusPackets
/*    */   implements ServerboundPacketType
/*    */ {
/* 24 */   STATUS_REQUEST,
/* 25 */   PING_REQUEST;
/*    */ 
/*    */   
/*    */   public final int getId() {
/* 29 */     return ordinal();
/*    */   }
/*    */ 
/*    */   
/*    */   public final String getName() {
/* 34 */     return name();
/*    */   }
/*    */ 
/*    */   
/*    */   public final State state() {
/* 39 */     return State.STATUS;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\base\ServerboundStatusPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
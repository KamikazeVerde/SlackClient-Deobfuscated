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
/*    */ public enum ServerboundLoginPackets
/*    */   implements ServerboundPacketType
/*    */ {
/* 24 */   HELLO,
/* 25 */   ENCRYPTION_KEY,
/* 26 */   CUSTOM_QUERY;
/*    */ 
/*    */   
/*    */   public final int getId() {
/* 30 */     return ordinal();
/*    */   }
/*    */ 
/*    */   
/*    */   public final String getName() {
/* 35 */     return name();
/*    */   }
/*    */ 
/*    */   
/*    */   public final State state() {
/* 40 */     return State.LOGIN;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\base\ServerboundLoginPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
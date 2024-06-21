/*    */ package com.viaversion.viaversion.api.protocol.packet;
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
/*    */ public interface ClientboundPacketType
/*    */   extends PacketType
/*    */ {
/*    */   default Direction direction() {
/* 33 */     return Direction.CLIENTBOUND;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\packet\ClientboundPacketType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
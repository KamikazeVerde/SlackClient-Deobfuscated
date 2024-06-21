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
/*    */ public interface ServerboundPacketType
/*    */   extends PacketType
/*    */ {
/*    */   default Direction direction() {
/* 33 */     return Direction.SERVERBOUND;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\packet\ServerboundPacketType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
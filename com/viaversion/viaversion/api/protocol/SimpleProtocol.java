/*    */ package com.viaversion.viaversion.api.protocol;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.Direction;
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
/*    */ public interface SimpleProtocol
/*    */   extends Protocol<SimpleProtocol.DummyPacketTypes, SimpleProtocol.DummyPacketTypes, SimpleProtocol.DummyPacketTypes, SimpleProtocol.DummyPacketTypes>
/*    */ {
/*    */   public enum DummyPacketTypes
/*    */     implements ClientboundPacketType, ServerboundPacketType
/*    */   {
/*    */     public int getId() {
/* 42 */       return 0;
/*    */     }
/*    */ 
/*    */     
/*    */     public String getName() {
/* 47 */       return name();
/*    */     }
/*    */ 
/*    */     
/*    */     public Direction direction() {
/* 52 */       throw new UnsupportedOperationException();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\SimpleProtocol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
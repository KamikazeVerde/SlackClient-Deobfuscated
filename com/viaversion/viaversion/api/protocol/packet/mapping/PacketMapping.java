/*    */ package com.viaversion.viaversion.api.protocol.packet.mapping;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface PacketMapping
/*    */ {
/*    */   void applyType(PacketWrapper paramPacketWrapper);
/*    */   
/*    */   PacketHandler handler();
/*    */   
/*    */   static PacketMapping of(int mappedPacketId, PacketHandler handler) {
/* 50 */     return new PacketIdMapping(mappedPacketId, handler);
/*    */   }
/*    */   
/*    */   static PacketMapping of(PacketType mappedPacketType, PacketHandler handler) {
/* 54 */     return new PacketTypeMapping(mappedPacketType, handler);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\packet\mapping\PacketMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
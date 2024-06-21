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
/*    */ final class PacketTypeMapping
/*    */   implements PacketMapping
/*    */ {
/*    */   private final PacketType mappedPacketType;
/*    */   private final PacketHandler handler;
/*    */   
/*    */   PacketTypeMapping(PacketType mappedPacketType, PacketHandler handler) {
/* 35 */     this.mappedPacketType = mappedPacketType;
/* 36 */     this.handler = handler;
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyType(PacketWrapper wrapper) {
/* 41 */     if (this.mappedPacketType != null) {
/* 42 */       wrapper.setPacketType(this.mappedPacketType);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public PacketHandler handler() {
/* 48 */     return this.handler;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\packet\mapping\PacketTypeMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
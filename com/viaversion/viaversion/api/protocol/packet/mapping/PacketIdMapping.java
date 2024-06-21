/*    */ package com.viaversion.viaversion.api.protocol.packet.mapping;
/*    */ 
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
/*    */ final class PacketIdMapping
/*    */   implements PacketMapping
/*    */ {
/*    */   private final int mappedPacketId;
/*    */   private final PacketHandler handler;
/*    */   
/*    */   PacketIdMapping(int mappedPacketId, PacketHandler handler) {
/* 34 */     this.mappedPacketId = mappedPacketId;
/* 35 */     this.handler = handler;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void applyType(PacketWrapper wrapper) {
/* 41 */     wrapper.setId(this.mappedPacketId);
/*    */   }
/*    */ 
/*    */   
/*    */   public PacketHandler handler() {
/* 46 */     return this.handler;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\packet\mapping\PacketIdMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
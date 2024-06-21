/*    */ package com.viaversion.viaversion.api.protocol.packet.mapping;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
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
/*    */ public interface PacketMappings
/*    */ {
/*    */   PacketMapping mappedPacket(State paramState, int paramInt);
/*    */   
/*    */   default boolean hasMapping(PacketType packetType) {
/* 50 */     return (mappedPacket(packetType.state(), packetType.getId()) != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default boolean hasMapping(State state, int unmappedId) {
/* 61 */     return (mappedPacket(state, unmappedId) != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default void addMapping(PacketType packetType, PacketMapping mapping) {
/* 71 */     addMapping(packetType.state(), packetType.getId(), mapping);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void addMapping(State paramState, int paramInt, PacketMapping paramPacketMapping);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static PacketMappings arrayMappings() {
/* 84 */     return new PacketArrayMappings();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\packet\mapping\PacketMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
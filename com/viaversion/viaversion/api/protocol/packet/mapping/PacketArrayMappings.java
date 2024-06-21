/*    */ package com.viaversion.viaversion.api.protocol.packet.mapping;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.packet.State;
/*    */ import java.util.Arrays;
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
/*    */ final class PacketArrayMappings
/*    */   implements PacketMappings
/*    */ {
/* 30 */   private final PacketMapping[][] packets = new PacketMapping[(State.values()).length][];
/*    */ 
/*    */   
/*    */   public PacketMapping mappedPacket(State state, int unmappedId) {
/* 34 */     PacketMapping[] packets = this.packets[state.ordinal()];
/* 35 */     if (packets != null && unmappedId >= 0 && unmappedId < packets.length) {
/* 36 */       return packets[unmappedId];
/*    */     }
/* 38 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addMapping(State state, int unmappedId, PacketMapping mapping) {
/* 43 */     int ordinal = state.ordinal();
/* 44 */     PacketMapping[] packets = this.packets[ordinal];
/* 45 */     if (packets == null) {
/* 46 */       packets = new PacketMapping[unmappedId + 8];
/* 47 */       this.packets[ordinal] = packets;
/* 48 */     } else if (unmappedId >= packets.length) {
/* 49 */       packets = Arrays.<PacketMapping>copyOf(packets, unmappedId + 32);
/* 50 */       this.packets[ordinal] = packets;
/*    */     } 
/*    */     
/* 53 */     packets[unmappedId] = mapping;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\packet\mapping\PacketArrayMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
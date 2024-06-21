/*    */ package com.viaversion.viaversion.api.protocol.packet.provider;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
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
/*    */ final class PacketTypeArrayMap<P>
/*    */   implements PacketTypeMap<P>
/*    */ {
/*    */   private final Map<String, P> packetsByName;
/*    */   private final P[] packets;
/*    */   
/*    */   PacketTypeArrayMap(Map<String, P> packetsByName, P[] packets) {
/* 35 */     this.packetsByName = packetsByName;
/* 36 */     this.packets = packets;
/*    */   }
/*    */ 
/*    */   
/*    */   public P typeByName(String packetTypeName) {
/* 41 */     return this.packetsByName.get(packetTypeName);
/*    */   }
/*    */ 
/*    */   
/*    */   public P typeById(int packetTypeId) {
/* 46 */     return (packetTypeId >= 0 && packetTypeId < this.packets.length) ? this.packets[packetTypeId] : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<P> types() {
/* 51 */     return Arrays.asList(this.packets);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\packet\provider\PacketTypeArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
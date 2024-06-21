/*    */ package com.viaversion.viaversion.api.protocol.packet.provider;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
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
/*    */ public interface PacketTypeMap<P>
/*    */ {
/*    */   P typeByName(String paramString);
/*    */   
/*    */   P typeById(int paramInt);
/*    */   
/*    */   Collection<P> types();
/*    */   
/*    */   static <S extends PacketType, T extends S> PacketTypeMap<S> of(Class<T> enumClass) {
/* 58 */     if (!enumClass.isEnum()) {
/* 59 */       throw new IllegalArgumentException("Given class is not an enum");
/*    */     }
/*    */     
/* 62 */     PacketType[] arrayOfPacketType = (PacketType[])enumClass.getEnumConstants();
/* 63 */     Map<String, S> byName = new HashMap<>(arrayOfPacketType.length);
/* 64 */     for (PacketType packetType : arrayOfPacketType) {
/* 65 */       byName.put(packetType.getName(), (S)packetType);
/*    */     }
/* 67 */     return of(byName, (S[])arrayOfPacketType);
/*    */   }
/*    */   
/*    */   static <T> PacketTypeMap<T> of(Map<String, T> packetsByName, Int2ObjectMap<T> packetsById) {
/* 71 */     return new PacketTypeMapMap<>(packetsByName, packetsById);
/*    */   }
/*    */   
/*    */   static <T> PacketTypeMap<T> of(Map<String, T> packetsByName, T[] packets) {
/* 75 */     return new PacketTypeArrayMap<>(packetsByName, packets);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\packet\provider\PacketTypeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.viaversion.viaversion.api.protocol.packet.provider;
/*    */ 
/*    */ import com.google.common.annotations.Beta;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.State;
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
/*    */ @Beta
/*    */ public final class SimplePacketTypesProvider<CU extends ClientboundPacketType, CM extends ClientboundPacketType, SM extends ServerboundPacketType, SU extends ServerboundPacketType>
/*    */   implements PacketTypesProvider<CU, CM, SM, SU>
/*    */ {
/*    */   private final Map<State, PacketTypeMap<CU>> unmappedClientboundPacketTypes;
/*    */   private final Map<State, PacketTypeMap<CM>> mappedClientboundPacketTypes;
/*    */   private final Map<State, PacketTypeMap<SM>> mappedServerboundPacketTypes;
/*    */   private final Map<State, PacketTypeMap<SU>> unmappedServerboundPacketTypes;
/*    */   
/*    */   public SimplePacketTypesProvider(Map<State, PacketTypeMap<CU>> unmappedClientboundPacketTypes, Map<State, PacketTypeMap<CM>> mappedClientboundPacketTypes, Map<State, PacketTypeMap<SM>> mappedServerboundPacketTypes, Map<State, PacketTypeMap<SU>> unmappedServerboundPacketTypes) {
/* 44 */     this.unmappedClientboundPacketTypes = unmappedClientboundPacketTypes;
/* 45 */     this.mappedClientboundPacketTypes = mappedClientboundPacketTypes;
/* 46 */     this.mappedServerboundPacketTypes = mappedServerboundPacketTypes;
/* 47 */     this.unmappedServerboundPacketTypes = unmappedServerboundPacketTypes;
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<State, PacketTypeMap<CU>> unmappedClientboundPacketTypes() {
/* 52 */     return this.unmappedClientboundPacketTypes;
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<State, PacketTypeMap<CM>> mappedClientboundPacketTypes() {
/* 57 */     return this.mappedClientboundPacketTypes;
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<State, PacketTypeMap<SM>> mappedServerboundPacketTypes() {
/* 62 */     return this.mappedServerboundPacketTypes;
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<State, PacketTypeMap<SU>> unmappedServerboundPacketTypes() {
/* 67 */     return this.unmappedServerboundPacketTypes;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\packet\provider\SimplePacketTypesProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
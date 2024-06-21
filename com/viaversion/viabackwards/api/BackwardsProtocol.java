/*    */ package com.viaversion.viabackwards.api;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*    */ import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.data.MappingData;
/*    */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
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
/*    */ public abstract class BackwardsProtocol<CU extends ClientboundPacketType, CM extends ClientboundPacketType, SM extends ServerboundPacketType, SU extends ServerboundPacketType>
/*    */   extends AbstractProtocol<CU, CM, SM, SU>
/*    */ {
/*    */   protected BackwardsProtocol() {}
/*    */   
/*    */   protected BackwardsProtocol(Class<CU> oldClientboundPacketEnum, Class<CM> clientboundPacketEnum, Class<SM> oldServerboundPacketEnum, Class<SU> serverboundPacketEnum) {
/* 37 */     super(oldClientboundPacketEnum, clientboundPacketEnum, oldServerboundPacketEnum, serverboundPacketEnum);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void executeAsyncAfterLoaded(Class<? extends Protocol> protocolClass, Runnable runnable) {
/* 44 */     Via.getManager().getProtocolManager().addMappingLoaderFuture(getClass(), protocolClass, runnable);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 49 */     super.registerPackets();
/*    */     
/* 51 */     BackwardsMappings mappingData = getMappingData();
/* 52 */     if (mappingData != null && mappingData.getViaVersionProtocolClass() != null) {
/* 53 */       executeAsyncAfterLoaded(mappingData.getViaVersionProtocolClass(), this::loadMappingData);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasMappingDataToLoad() {
/* 60 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public BackwardsMappings getMappingData() {
/* 65 */     return null;
/*    */   }
/*    */   
/*    */   public TranslatableRewriter<CU> getTranslatableRewriter() {
/* 69 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\BackwardsProtocol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
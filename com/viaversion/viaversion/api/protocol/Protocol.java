/*     */ package com.viaversion.viaversion.api.protocol;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.platform.providers.ViaProviders;
/*     */ import com.viaversion.viaversion.api.protocol.packet.Direction;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.State;
/*     */ import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Protocol<CU extends com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType, CM extends com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType, SM extends com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType, SU extends com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType>
/*     */ {
/*     */   default void registerServerbound(State state, int unmappedPacketId, int mappedPacketId) {
/*  54 */     registerServerbound(state, unmappedPacketId, mappedPacketId, (PacketHandler)null);
/*     */   }
/*     */   
/*     */   default void registerServerbound(State state, int unmappedPacketId, int mappedPacketId, PacketHandler handler) {
/*  58 */     registerServerbound(state, unmappedPacketId, mappedPacketId, handler, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void registerServerbound(State paramState, int paramInt1, int paramInt2, PacketHandler paramPacketHandler, boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void cancelServerbound(State paramState, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void registerClientbound(State state, int unmappedPacketId, int mappedPacketId) {
/*  76 */     registerClientbound(state, unmappedPacketId, mappedPacketId, (PacketHandler)null);
/*     */   }
/*     */   
/*     */   default void registerClientbound(State state, int unmappedPacketId, int mappedPacketId, PacketHandler handler) {
/*  80 */     registerClientbound(state, unmappedPacketId, mappedPacketId, handler, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void cancelClientbound(State paramState, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void registerClientbound(State paramState, int paramInt1, int paramInt2, PacketHandler paramPacketHandler, boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void registerClientbound(CU paramCU, PacketHandler paramPacketHandler);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void registerClientbound(CU packetType, CM mappedPacketType) {
/* 115 */     registerClientbound(packetType, mappedPacketType, (PacketHandler)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void registerClientbound(CU packetType, CM mappedPacketType, PacketHandler handler) {
/* 126 */     registerClientbound(packetType, mappedPacketType, handler, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void registerClientbound(CU paramCU, CM paramCM, PacketHandler paramPacketHandler, boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void cancelClientbound(CU paramCU);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void registerServerbound(SU packetType, SM mappedPacketType) {
/* 154 */     registerServerbound(packetType, mappedPacketType, (PacketHandler)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void registerServerbound(SU paramSU, PacketHandler paramPacketHandler);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void registerServerbound(SU packetType, SM mappedPacketType, PacketHandler handler) {
/* 173 */     registerServerbound(packetType, mappedPacketType, handler, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void registerServerbound(SU paramSU, SM paramSM, PacketHandler paramPacketHandler, boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void cancelServerbound(SU paramSU);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean hasRegisteredClientbound(CU packetType) {
/* 200 */     return hasRegisteredClientbound(packetType.state(), packetType.getId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean hasRegisteredServerbound(SU packetType) {
/* 210 */     return hasRegisteredServerbound(packetType.state(), packetType.getId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasRegisteredClientbound(State paramState, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasRegisteredServerbound(State paramState, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void transform(Direction paramDirection, State paramState, PacketWrapper paramPacketWrapper) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   PacketTypesProvider<CU, CM, SM, SU> getPacketTypesProvider();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <T> T get(Class<T> paramClass);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void put(Object paramObject);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void initialize();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean hasMappingDataToLoad() {
/* 282 */     return (getMappingData() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void loadMappingData();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void register(ViaProviders providers) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void init(UserConnection connection) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default MappingData getMappingData() {
/* 319 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default EntityRewriter<?> getEntityRewriter() {
/* 328 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ItemRewriter<?> getItemRewriter() {
/* 337 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean isBaseProtocol() {
/* 346 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void cancelServerbound(State state, int unmappedPacketId, int mappedPacketId) {
/* 356 */     cancelServerbound(state, unmappedPacketId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void cancelClientbound(State state, int unmappedPacketId, int mappedPacketId) {
/* 364 */     cancelClientbound(state, unmappedPacketId);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void registerClientbound(State state, int unmappedPacketId, int mappedPacketId, PacketRemapper packetRemapper) {
/* 369 */     registerClientbound(state, unmappedPacketId, mappedPacketId, packetRemapper.asPacketHandler(), false);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void registerClientbound(State state, int unmappedPacketId, int mappedPacketId, PacketRemapper packetRemapper, boolean override) {
/* 374 */     registerClientbound(state, unmappedPacketId, mappedPacketId, packetRemapper.asPacketHandler(), override);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void registerClientbound(CU packetType, PacketRemapper packetRemapper) {
/* 379 */     registerClientbound(packetType, packetRemapper.asPacketHandler());
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void registerClientbound(CU packetType, CM mappedPacketType, PacketRemapper packetRemapper) {
/* 384 */     registerClientbound(packetType, mappedPacketType, packetRemapper.asPacketHandler(), false);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void registerClientbound(CU packetType, CM mappedPacketType, PacketRemapper packetRemapper, boolean override) {
/* 389 */     registerClientbound(packetType, mappedPacketType, packetRemapper.asPacketHandler(), override);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void registerServerbound(State state, int unmappedPacketId, int mappedPacketId, PacketRemapper packetRemapper) {
/* 394 */     registerServerbound(state, unmappedPacketId, mappedPacketId, packetRemapper.asPacketHandler(), false);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void registerServerbound(State state, int unmappedPacketId, int mappedPacketId, PacketRemapper packetRemapper, boolean override) {
/* 399 */     registerServerbound(state, unmappedPacketId, mappedPacketId, packetRemapper.asPacketHandler(), override);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void registerServerbound(SU packetType, PacketRemapper packetRemapper) {
/* 404 */     registerServerbound(packetType, packetRemapper.asPacketHandler());
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void registerServerbound(SU packetType, SM mappedPacketType, PacketRemapper packetRemapper) {
/* 409 */     registerServerbound(packetType, mappedPacketType, packetRemapper.asPacketHandler(), false);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void registerServerbound(SU packetType, SM mappedPacketType, PacketRemapper packetRemapper, boolean override) {
/* 414 */     registerServerbound(packetType, mappedPacketType, packetRemapper.asPacketHandler(), override);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\Protocol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viaversion.api.connection;
/*     */ 
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketTracker;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Function;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface UserConnection
/*     */ {
/*     */   <T extends StorableObject> T get(Class<T> paramClass);
/*     */   
/*     */   boolean has(Class<? extends StorableObject> paramClass);
/*     */   
/*     */   <T extends StorableObject> T remove(Class<T> paramClass);
/*     */   
/*     */   void put(StorableObject paramStorableObject);
/*     */   
/*     */   Collection<EntityTracker> getEntityTrackers();
/*     */   
/*     */   <T extends EntityTracker> T getEntityTracker(Class<? extends Protocol> paramClass);
/*     */   
/*     */   void addEntityTracker(Class<? extends Protocol> paramClass, EntityTracker paramEntityTracker);
/*     */   
/*     */   default void clearStoredObjects() {
/* 105 */     clearStoredObjects(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void clearStoredObjects(boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void sendRawPacket(ByteBuf paramByteBuf);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void scheduleSendRawPacket(ByteBuf paramByteBuf);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ChannelFuture sendRawPacketFuture(ByteBuf paramByteBuf);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PacketTracker getPacketTracker();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void disconnect(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void sendRawPacketToServer(ByteBuf paramByteBuf);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void scheduleSendRawPacketToServer(ByteBuf paramByteBuf);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean checkServerboundPacket();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean checkClientboundPacket();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean checkIncomingPacket() {
/* 185 */     return isClientSide() ? checkClientboundPacket() : checkServerboundPacket();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean checkOutgoingPacket() {
/* 193 */     return isClientSide() ? checkServerboundPacket() : checkClientboundPacket();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean shouldTransformPacket();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void transformClientbound(ByteBuf paramByteBuf, Function<Throwable, Exception> paramFunction) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void transformServerbound(ByteBuf paramByteBuf, Function<Throwable, Exception> paramFunction) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void transformOutgoing(ByteBuf buf, Function<Throwable, Exception> cancelSupplier) throws Exception {
/* 233 */     if (isClientSide()) {
/* 234 */       transformServerbound(buf, cancelSupplier);
/*     */     } else {
/* 236 */       transformClientbound(buf, cancelSupplier);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void transformIncoming(ByteBuf buf, Function<Throwable, Exception> cancelSupplier) throws Exception {
/* 247 */     if (isClientSide()) {
/* 248 */       transformClientbound(buf, cancelSupplier);
/*     */     } else {
/* 250 */       transformServerbound(buf, cancelSupplier);
/*     */     } 
/*     */   }
/*     */   
/*     */   long getId();
/*     */   
/*     */   Channel getChannel();
/*     */   
/*     */   ProtocolInfo getProtocolInfo();
/*     */   
/*     */   Map<Class<?>, StorableObject> getStoredObjects();
/*     */   
/*     */   boolean isActive();
/*     */   
/*     */   void setActive(boolean paramBoolean);
/*     */   
/*     */   boolean isPendingDisconnect();
/*     */   
/*     */   void setPendingDisconnect(boolean paramBoolean);
/*     */   
/*     */   boolean isClientSide();
/*     */   
/*     */   boolean shouldApplyBlockProtocol();
/*     */   
/*     */   boolean isPacketLimiterEnabled();
/*     */   
/*     */   void setPacketLimiterEnabled(boolean paramBoolean);
/*     */   
/*     */   UUID generatePassthroughToken();
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\connection\UserConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
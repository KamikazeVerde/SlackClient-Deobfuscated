/*     */ package com.viaversion.viaversion.connection;
/*     */ 
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.ProtocolInfo;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.Direction;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketTracker;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.exception.CancelException;
/*     */ import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
/*     */ import com.viaversion.viaversion.util.ChatColorUtil;
/*     */ import com.viaversion.viaversion.util.PipelineUtil;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicLong;
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
/*     */ public class UserConnectionImpl
/*     */   implements UserConnection
/*     */ {
/*  52 */   private static final AtomicLong IDS = new AtomicLong();
/*  53 */   private final long id = IDS.incrementAndGet();
/*  54 */   private final Map<Class<?>, StorableObject> storedObjects = new ConcurrentHashMap<>();
/*  55 */   private final Map<Class<? extends Protocol>, EntityTracker> entityTrackers = new HashMap<>();
/*  56 */   private final PacketTracker packetTracker = new PacketTracker(this);
/*  57 */   private final Set<UUID> passthroughTokens = Collections.newSetFromMap(CacheBuilder.newBuilder()
/*  58 */       .expireAfterWrite(10L, TimeUnit.SECONDS)
/*  59 */       .build().asMap());
/*  60 */   private final ProtocolInfo protocolInfo = new ProtocolInfoImpl(this);
/*     */   
/*     */   private final Channel channel;
/*     */   
/*     */   private final boolean clientSide;
/*     */   
/*     */   private boolean active = true;
/*     */   
/*     */   private boolean pendingDisconnect;
/*     */   
/*     */   private boolean packetLimiterEnabled = true;
/*     */ 
/*     */   
/*     */   public UserConnectionImpl(Channel channel, boolean clientSide) {
/*  74 */     this.channel = channel;
/*  75 */     this.clientSide = clientSide;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserConnectionImpl(Channel channel) {
/*  82 */     this(channel, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends StorableObject> T get(Class<T> objectClass) {
/*  87 */     return (T)this.storedObjects.get(objectClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean has(Class<? extends StorableObject> objectClass) {
/*  92 */     return this.storedObjects.containsKey(objectClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends StorableObject> T remove(Class<T> objectClass) {
/*  97 */     return (T)this.storedObjects.remove(objectClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public void put(StorableObject object) {
/* 102 */     this.storedObjects.put(object.getClass(), object);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<EntityTracker> getEntityTrackers() {
/* 107 */     return this.entityTrackers.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends EntityTracker> T getEntityTracker(Class<? extends Protocol> protocolClass) {
/* 112 */     return (T)this.entityTrackers.get(protocolClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addEntityTracker(Class<? extends Protocol> protocolClass, EntityTracker tracker) {
/* 117 */     if (!this.entityTrackers.containsKey(protocolClass)) {
/* 118 */       this.entityTrackers.put(protocolClass, tracker);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearStoredObjects(boolean isServerSwitch) {
/* 124 */     if (isServerSwitch) {
/* 125 */       this.storedObjects.values().removeIf(StorableObject::clearOnServerSwitch);
/* 126 */       for (EntityTracker tracker : this.entityTrackers.values()) {
/* 127 */         tracker.clearEntities();
/* 128 */         tracker.trackClientEntity();
/*     */       } 
/*     */     } else {
/* 131 */       this.storedObjects.clear();
/* 132 */       this.entityTrackers.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendRawPacket(ByteBuf packet) {
/* 138 */     sendRawPacket(packet, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void scheduleSendRawPacket(ByteBuf packet) {
/* 143 */     sendRawPacket(packet, false);
/*     */   }
/*     */   
/*     */   private void sendRawPacket(ByteBuf packet, boolean currentThread) {
/*     */     Runnable act;
/* 148 */     if (this.clientSide) {
/*     */       
/* 150 */       act = (() -> getChannel().pipeline().context(Via.getManager().getInjector().getDecoderName()).fireChannelRead(packet));
/*     */     } else {
/*     */       
/* 153 */       act = (() -> this.channel.pipeline().context(Via.getManager().getInjector().getEncoderName()).writeAndFlush(packet));
/*     */     } 
/* 155 */     if (currentThread) {
/* 156 */       act.run();
/*     */     } else {
/*     */       try {
/* 159 */         this.channel.eventLoop().submit(act);
/* 160 */       } catch (Throwable e) {
/* 161 */         packet.release();
/* 162 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture sendRawPacketFuture(ByteBuf packet) {
/* 169 */     if (this.clientSide) {
/*     */       
/* 171 */       getChannel().pipeline().context(Via.getManager().getInjector().getDecoderName()).fireChannelRead(packet);
/* 172 */       return getChannel().newSucceededFuture();
/*     */     } 
/* 174 */     return this.channel.pipeline().context(Via.getManager().getInjector().getEncoderName()).writeAndFlush(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PacketTracker getPacketTracker() {
/* 180 */     return this.packetTracker;
/*     */   }
/*     */ 
/*     */   
/*     */   public void disconnect(String reason) {
/* 185 */     if (!this.channel.isOpen() || this.pendingDisconnect)
/*     */       return; 
/* 187 */     this.pendingDisconnect = true;
/* 188 */     Via.getPlatform().runSync(() -> {
/*     */           if (!Via.getPlatform().disconnect(this, ChatColorUtil.translateAlternateColorCodes(reason))) {
/*     */             this.channel.close();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendRawPacketToServer(ByteBuf packet) {
/* 197 */     if (this.clientSide) {
/* 198 */       sendRawPacketToServerClientSide(packet, true);
/*     */     } else {
/* 200 */       sendRawPacketToServerServerSide(packet, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void scheduleSendRawPacketToServer(ByteBuf packet) {
/* 206 */     if (this.clientSide) {
/* 207 */       sendRawPacketToServerClientSide(packet, false);
/*     */     } else {
/* 209 */       sendRawPacketToServerServerSide(packet, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sendRawPacketToServerServerSide(ByteBuf packet, boolean currentThread) {
/* 214 */     ByteBuf buf = packet.alloc().buffer();
/*     */ 
/*     */     
/*     */     try {
/* 218 */       ChannelHandlerContext context = PipelineUtil.getPreviousContext(Via.getManager().getInjector().getDecoderName(), this.channel.pipeline());
/*     */       
/* 220 */       if (shouldTransformPacket()) {
/*     */         
/*     */         try {
/* 223 */           Type.VAR_INT.writePrimitive(buf, 1000);
/* 224 */           Type.UUID.write(buf, generatePassthroughToken());
/* 225 */         } catch (Exception shouldNotHappen) {
/* 226 */           throw new RuntimeException(shouldNotHappen);
/*     */         } 
/*     */       }
/*     */       
/* 230 */       buf.writeBytes(packet);
/* 231 */       Runnable act = () -> {
/*     */           if (context != null) {
/*     */             context.fireChannelRead(buf);
/*     */           } else {
/*     */             this.channel.pipeline().fireChannelRead(buf);
/*     */           } 
/*     */         };
/* 238 */       if (currentThread) {
/* 239 */         act.run();
/*     */       } else {
/*     */         try {
/* 242 */           this.channel.eventLoop().submit(act);
/* 243 */         } catch (Throwable t) {
/*     */           
/* 245 */           buf.release();
/* 246 */           throw t;
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 250 */       packet.release();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sendRawPacketToServerClientSide(ByteBuf packet, boolean currentThread) {
/* 255 */     Runnable act = () -> getChannel().pipeline().context(Via.getManager().getInjector().getEncoderName()).writeAndFlush(packet);
/*     */     
/* 257 */     if (currentThread) {
/* 258 */       act.run();
/*     */     } else {
/*     */       try {
/* 261 */         getChannel().eventLoop().submit(act);
/* 262 */       } catch (Throwable e) {
/* 263 */         e.printStackTrace();
/* 264 */         packet.release();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkServerboundPacket() {
/* 271 */     if (this.pendingDisconnect) {
/* 272 */       return false;
/*     */     }
/*     */     
/* 275 */     return (!this.packetLimiterEnabled || !this.packetTracker.incrementReceived() || !this.packetTracker.exceedsMaxPPS());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkClientboundPacket() {
/* 280 */     this.packetTracker.incrementSent();
/* 281 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldTransformPacket() {
/* 286 */     return this.active;
/*     */   }
/*     */ 
/*     */   
/*     */   public void transformClientbound(ByteBuf buf, Function<Throwable, Exception> cancelSupplier) throws Exception {
/* 291 */     transform(buf, Direction.CLIENTBOUND, cancelSupplier);
/*     */   }
/*     */ 
/*     */   
/*     */   public void transformServerbound(ByteBuf buf, Function<Throwable, Exception> cancelSupplier) throws Exception {
/* 296 */     transform(buf, Direction.SERVERBOUND, cancelSupplier);
/*     */   }
/*     */   
/*     */   private void transform(ByteBuf buf, Direction direction, Function<Throwable, Exception> cancelSupplier) throws Exception {
/* 300 */     if (!buf.isReadable())
/*     */       return; 
/* 302 */     int id = Type.VAR_INT.readPrimitive(buf);
/* 303 */     if (id == 1000) {
/* 304 */       if (!this.passthroughTokens.remove(Type.UUID.read(buf))) {
/* 305 */         throw new IllegalArgumentException("Invalid token");
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 310 */     PacketWrapperImpl packetWrapperImpl = new PacketWrapperImpl(id, buf, this);
/*     */     try {
/* 312 */       this.protocolInfo.getPipeline().transform(direction, this.protocolInfo.getState(), (PacketWrapper)packetWrapperImpl);
/* 313 */     } catch (CancelException ex) {
/* 314 */       throw (Exception)cancelSupplier.apply(ex);
/*     */     } 
/*     */     
/* 317 */     ByteBuf transformed = buf.alloc().buffer();
/*     */     try {
/* 319 */       packetWrapperImpl.writeToBuffer(transformed);
/* 320 */       buf.clear().writeBytes(transformed);
/*     */     } finally {
/* 322 */       transformed.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long getId() {
/* 328 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public Channel getChannel() {
/* 333 */     return this.channel;
/*     */   }
/*     */ 
/*     */   
/*     */   public ProtocolInfo getProtocolInfo() {
/* 338 */     return this.protocolInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Class<?>, StorableObject> getStoredObjects() {
/* 343 */     return this.storedObjects;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 348 */     return this.active;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setActive(boolean active) {
/* 353 */     this.active = active;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPendingDisconnect() {
/* 358 */     return this.pendingDisconnect;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPendingDisconnect(boolean pendingDisconnect) {
/* 363 */     this.pendingDisconnect = pendingDisconnect;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClientSide() {
/* 368 */     return this.clientSide;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldApplyBlockProtocol() {
/* 373 */     return !this.clientSide;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPacketLimiterEnabled() {
/* 378 */     return this.packetLimiterEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPacketLimiterEnabled(boolean packetLimiterEnabled) {
/* 383 */     this.packetLimiterEnabled = packetLimiterEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID generatePassthroughToken() {
/* 388 */     UUID token = UUID.randomUUID();
/* 389 */     this.passthroughTokens.add(token);
/* 390 */     return token;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 395 */     if (this == o) return true; 
/* 396 */     if (o == null || getClass() != o.getClass()) return false; 
/* 397 */     UserConnectionImpl that = (UserConnectionImpl)o;
/* 398 */     return (this.id == that.id);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 403 */     return Long.hashCode(this.id);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\connection\UserConnectionImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
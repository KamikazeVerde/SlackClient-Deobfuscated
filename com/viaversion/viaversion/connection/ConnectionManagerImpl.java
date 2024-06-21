/*     */ package com.viaversion.viaversion.connection;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.ConnectionManager;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public class ConnectionManagerImpl
/*     */   implements ConnectionManager
/*     */ {
/*  34 */   protected final Map<UUID, UserConnection> clients = new ConcurrentHashMap<>();
/*  35 */   protected final Set<UserConnection> connections = Collections.newSetFromMap(new ConcurrentHashMap<>());
/*     */ 
/*     */   
/*     */   public void onLoginSuccess(UserConnection connection) {
/*  39 */     Objects.requireNonNull(connection, "connection is null!");
/*  40 */     Channel channel = connection.getChannel();
/*     */ 
/*     */     
/*  43 */     if (channel != null && !channel.isOpen())
/*     */       return; 
/*  45 */     boolean newlyAdded = this.connections.add(connection);
/*     */     
/*  47 */     if (isFrontEnd(connection)) {
/*  48 */       UUID id = connection.getProtocolInfo().getUuid();
/*  49 */       UserConnection previous = this.clients.put(id, connection);
/*  50 */       if (previous != null && previous != connection) {
/*  51 */         Via.getPlatform().getLogger().warning("Duplicate UUID on frontend connection! (" + id + ")");
/*     */       }
/*     */     } 
/*     */     
/*  55 */     if (channel != null)
/*     */     {
/*     */       
/*  58 */       if (!channel.isOpen()) {
/*  59 */         onDisconnect(connection);
/*  60 */       } else if (newlyAdded) {
/*  61 */         channel.closeFuture().addListener((GenericFutureListener)(future -> onDisconnect(connection)));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisconnect(UserConnection connection) {
/*  68 */     Objects.requireNonNull(connection, "connection is null!");
/*  69 */     this.connections.remove(connection);
/*     */     
/*  71 */     if (isFrontEnd(connection)) {
/*  72 */       UUID id = connection.getProtocolInfo().getUuid();
/*  73 */       this.clients.remove(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<UUID, UserConnection> getConnectedClients() {
/*  79 */     return Collections.unmodifiableMap(this.clients);
/*     */   }
/*     */ 
/*     */   
/*     */   public UserConnection getConnectedClient(UUID clientIdentifier) {
/*  84 */     return this.clients.get(clientIdentifier);
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getConnectedClientId(UserConnection connection) {
/*  89 */     if (connection.getProtocolInfo() == null) return null; 
/*  90 */     UUID uuid = connection.getProtocolInfo().getUuid();
/*  91 */     UserConnection client = this.clients.get(uuid);
/*  92 */     if (connection.equals(client))
/*     */     {
/*  94 */       return uuid;
/*     */     }
/*  96 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<UserConnection> getConnections() {
/* 101 */     return Collections.unmodifiableSet(this.connections);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClientConnected(UUID playerId) {
/* 106 */     return this.clients.containsKey(playerId);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\connection\ConnectionManagerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
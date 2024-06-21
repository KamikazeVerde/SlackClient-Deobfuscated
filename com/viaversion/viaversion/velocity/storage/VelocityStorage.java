/*     */ package com.viaversion.viaversion.velocity.storage;
/*     */ 
/*     */ import com.velocitypowered.api.proxy.Player;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.util.ReflectionUtil;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
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
/*     */ public class VelocityStorage
/*     */   implements StorableObject
/*     */ {
/*     */   private final Player player;
/*     */   private String currentServer;
/*     */   private List<UUID> cachedBossbar;
/*     */   private static Method getServerBossBars;
/*     */   private static Class<?> clientPlaySessionHandler;
/*     */   private static Method getMinecraftConnection;
/*     */   
/*     */   static {
/*     */     try {
/*  39 */       clientPlaySessionHandler = Class.forName("com.velocitypowered.proxy.connection.client.ClientPlaySessionHandler");
/*     */       
/*  41 */       getServerBossBars = clientPlaySessionHandler.getDeclaredMethod("getServerBossBars", new Class[0]);
/*     */       
/*  43 */       getMinecraftConnection = Class.forName("com.velocitypowered.proxy.connection.client.ConnectedPlayer").getDeclaredMethod("getMinecraftConnection", new Class[0]);
/*  44 */     } catch (NoSuchMethodException|ClassNotFoundException e) {
/*  45 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public VelocityStorage(Player player) {
/*  50 */     this.player = player;
/*  51 */     this.currentServer = "";
/*     */   }
/*     */   
/*     */   public List<UUID> getBossbar() {
/*  55 */     if (this.cachedBossbar == null) {
/*  56 */       if (clientPlaySessionHandler == null) return null; 
/*  57 */       if (getServerBossBars == null) return null; 
/*  58 */       if (getMinecraftConnection == null) return null;
/*     */       
/*     */       try {
/*  61 */         Object connection = getMinecraftConnection.invoke(this.player, new Object[0]);
/*  62 */         Object sessionHandler = ReflectionUtil.invoke(connection, "getSessionHandler");
/*  63 */         if (clientPlaySessionHandler.isInstance(sessionHandler)) {
/*  64 */           this.cachedBossbar = (List<UUID>)getServerBossBars.invoke(sessionHandler, new Object[0]);
/*     */         }
/*  66 */       } catch (NoSuchMethodException|java.lang.reflect.InvocationTargetException|IllegalAccessException e) {
/*  67 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*  70 */     return this.cachedBossbar;
/*     */   }
/*     */   
/*     */   public Player getPlayer() {
/*  74 */     return this.player;
/*     */   }
/*     */   
/*     */   public String getCurrentServer() {
/*  78 */     return this.currentServer;
/*     */   }
/*     */   
/*     */   public void setCurrentServer(String currentServer) {
/*  82 */     this.currentServer = currentServer;
/*     */   }
/*     */   
/*     */   public List<UUID> getCachedBossbar() {
/*  86 */     return this.cachedBossbar;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  91 */     if (this == o) return true; 
/*  92 */     if (o == null || getClass() != o.getClass()) return false; 
/*  93 */     VelocityStorage that = (VelocityStorage)o;
/*  94 */     if (!Objects.equals(this.player, that.player)) return false; 
/*  95 */     if (!Objects.equals(this.currentServer, that.currentServer)) return false; 
/*  96 */     return Objects.equals(this.cachedBossbar, that.cachedBossbar);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 101 */     int result = (this.player != null) ? this.player.hashCode() : 0;
/* 102 */     result = 31 * result + ((this.currentServer != null) ? this.currentServer.hashCode() : 0);
/* 103 */     result = 31 * result + ((this.cachedBossbar != null) ? this.cachedBossbar.hashCode() : 0);
/* 104 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\velocity\storage\VelocityStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
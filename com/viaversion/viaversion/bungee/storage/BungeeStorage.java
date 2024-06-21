/*    */ package com.viaversion.viaversion.bungee.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import net.md_5.bungee.api.connection.ProxiedPlayer;
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
/*    */ public class BungeeStorage
/*    */   implements StorableObject
/*    */ {
/*    */   private static Field bossField;
/*    */   private final ProxiedPlayer player;
/*    */   private String currentServer;
/*    */   private Set<UUID> bossbar;
/*    */   
/*    */   static {
/*    */     try {
/* 32 */       Class<?> user = Class.forName("net.md_5.bungee.UserConnection");
/* 33 */       bossField = user.getDeclaredField("sentBossBars");
/* 34 */       bossField.setAccessible(true);
/* 35 */     } catch (ClassNotFoundException classNotFoundException) {
/*    */     
/* 37 */     } catch (NoSuchFieldException noSuchFieldException) {}
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BungeeStorage(ProxiedPlayer player) {
/* 47 */     this.player = player;
/* 48 */     this.currentServer = "";
/*    */ 
/*    */     
/* 51 */     if (bossField != null) {
/*    */       try {
/* 53 */         this.bossbar = (Set<UUID>)bossField.get(player);
/* 54 */       } catch (IllegalAccessException e) {
/* 55 */         e.printStackTrace();
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public ProxiedPlayer getPlayer() {
/* 61 */     return this.player;
/*    */   }
/*    */   
/*    */   public String getCurrentServer() {
/* 65 */     return this.currentServer;
/*    */   }
/*    */   
/*    */   public void setCurrentServer(String currentServer) {
/* 69 */     this.currentServer = currentServer;
/*    */   }
/*    */   
/*    */   public Set<UUID> getBossbar() {
/* 73 */     return this.bossbar;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 78 */     if (this == o) return true; 
/* 79 */     if (o == null || getClass() != o.getClass()) return false; 
/* 80 */     BungeeStorage that = (BungeeStorage)o;
/* 81 */     if (!Objects.equals(this.player, that.player)) return false; 
/* 82 */     if (!Objects.equals(this.currentServer, that.currentServer)) return false; 
/* 83 */     return Objects.equals(this.bossbar, that.bossbar);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 88 */     int result = (this.player != null) ? this.player.hashCode() : 0;
/* 89 */     result = 31 * result + ((this.currentServer != null) ? this.currentServer.hashCode() : 0);
/* 90 */     result = 31 * result + ((this.bossbar != null) ? this.bossbar.hashCode() : 0);
/* 91 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\storage\BungeeStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
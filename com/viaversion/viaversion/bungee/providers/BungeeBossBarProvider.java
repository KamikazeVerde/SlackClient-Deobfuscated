/*    */ package com.viaversion.viaversion.bungee.providers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.bungee.storage.BungeeStorage;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider;
/*    */ import java.util.UUID;
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
/*    */ public class BungeeBossBarProvider
/*    */   extends BossBarProvider
/*    */ {
/*    */   public void handleAdd(UserConnection user, UUID barUUID) {
/* 28 */     if (user.has(BungeeStorage.class)) {
/* 29 */       BungeeStorage storage = (BungeeStorage)user.get(BungeeStorage.class);
/*    */       
/* 31 */       if (storage.getBossbar() != null) {
/* 32 */         storage.getBossbar().add(barUUID);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleRemove(UserConnection user, UUID barUUID) {
/* 39 */     if (user.has(BungeeStorage.class)) {
/* 40 */       BungeeStorage storage = (BungeeStorage)user.get(BungeeStorage.class);
/* 41 */       if (storage.getBossbar() != null)
/* 42 */         storage.getBossbar().remove(barUUID); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\providers\BungeeBossBarProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
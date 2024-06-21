/*    */ package com.viaversion.viaversion.velocity.providers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider;
/*    */ import com.viaversion.viaversion.velocity.storage.VelocityStorage;
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
/*    */ public class VelocityBossBarProvider
/*    */   extends BossBarProvider
/*    */ {
/*    */   public void handleAdd(UserConnection user, UUID barUUID) {
/* 28 */     if (user.has(VelocityStorage.class)) {
/* 29 */       VelocityStorage storage = (VelocityStorage)user.get(VelocityStorage.class);
/*    */       
/* 31 */       if (storage.getBossbar() != null) {
/* 32 */         storage.getBossbar().add(barUUID);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleRemove(UserConnection user, UUID barUUID) {
/* 39 */     if (user.has(VelocityStorage.class)) {
/* 40 */       VelocityStorage storage = (VelocityStorage)user.get(VelocityStorage.class);
/* 41 */       if (storage.getBossbar() != null)
/* 42 */         storage.getBossbar().remove(barUUID); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\velocity\providers\VelocityBossBarProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.viaversion.viaversion.velocity.listeners;
/*    */ 
/*    */ import com.velocitypowered.api.event.Subscribe;
/*    */ import com.velocitypowered.api.event.connection.PostLoginEvent;
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.update.UpdateUtil;
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
/*    */ public class UpdateListener
/*    */ {
/*    */   @Subscribe
/*    */   public void onJoin(PostLoginEvent e) {
/* 28 */     if (e.getPlayer().hasPermission("viaversion.update") && 
/* 29 */       Via.getConfig().isCheckForUpdates())
/* 30 */       UpdateUtil.sendUpdateMessage(e.getPlayer().getUniqueId()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\velocity\listeners\UpdateListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
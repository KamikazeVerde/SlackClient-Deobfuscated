/*    */ package com.viaversion.viaversion.bungee.listeners;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.update.UpdateUtil;
/*    */ import net.md_5.bungee.api.event.PostLoginEvent;
/*    */ import net.md_5.bungee.api.plugin.Listener;
/*    */ import net.md_5.bungee.event.EventHandler;
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
/*    */   implements Listener
/*    */ {
/*    */   @EventHandler
/*    */   public void onJoin(PostLoginEvent e) {
/* 30 */     if (e.getPlayer().hasPermission("viaversion.update") && 
/* 31 */       Via.getConfig().isCheckForUpdates())
/* 32 */       UpdateUtil.sendUpdateMessage(e.getPlayer().getUniqueId()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\listeners\UpdateListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
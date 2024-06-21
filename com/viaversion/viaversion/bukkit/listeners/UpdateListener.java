/*    */ package com.viaversion.viaversion.bukkit.listeners;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.update.UpdateUtil;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
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
/*    */   public void onJoin(PlayerJoinEvent e) {
/* 30 */     if (e.getPlayer().hasPermission("viaversion.update") && 
/* 31 */       Via.getConfig().isCheckForUpdates())
/* 32 */       UpdateUtil.sendUpdateMessage(e.getPlayer().getUniqueId()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\listeners\UpdateListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
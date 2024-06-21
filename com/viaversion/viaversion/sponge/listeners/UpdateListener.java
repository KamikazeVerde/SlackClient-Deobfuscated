/*    */ package com.viaversion.viaversion.sponge.listeners;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.update.UpdateUtil;
/*    */ import org.spongepowered.api.event.Listener;
/*    */ import org.spongepowered.api.event.network.ServerSideConnectionEvent;
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
/*    */ 
/*    */ public class UpdateListener
/*    */ {
/*    */   @Listener
/*    */   public void onJoin(ServerSideConnectionEvent.Join join) {
/* 29 */     if (join.player().hasPermission("viaversion.update") && Via.getConfig().isCheckForUpdates())
/* 30 */       UpdateUtil.sendUpdateMessage(join.player().uniqueId()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\sponge\listeners\UpdateListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.viaversion.viaversion.protocols.protocol1_9to1_8;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.connection.ProtocolInfo;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;
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
/*    */ public class ViaIdleThread
/*    */   implements Runnable
/*    */ {
/*    */   public void run() {
/* 30 */     for (UserConnection info : Via.getManager().getConnectionManager().getConnections()) {
/* 31 */       ProtocolInfo protocolInfo = info.getProtocolInfo();
/* 32 */       if (protocolInfo == null || !protocolInfo.getPipeline().contains(Protocol1_9To1_8.class))
/*    */         continue; 
/* 34 */       MovementTracker movementTracker = (MovementTracker)info.get(MovementTracker.class);
/* 35 */       if (movementTracker == null)
/*    */         continue; 
/* 37 */       long nextIdleUpdate = movementTracker.getNextIdlePacket();
/* 38 */       if (nextIdleUpdate <= System.currentTimeMillis() && info.getChannel().isOpen())
/* 39 */         ((MovementTransmitterProvider)Via.getManager().getProviders().get(MovementTransmitterProvider.class)).sendPlayer(info); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\ViaIdleThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
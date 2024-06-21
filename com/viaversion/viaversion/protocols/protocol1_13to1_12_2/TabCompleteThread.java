/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.TabCompleteTracker;
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
/*    */ public class TabCompleteThread
/*    */   implements Runnable
/*    */ {
/*    */   public void run() {
/* 27 */     for (UserConnection info : Via.getManager().getConnectionManager().getConnections()) {
/* 28 */       if (info.getProtocolInfo() != null && 
/* 29 */         info.getProtocolInfo().getPipeline().contains(Protocol1_13To1_12_2.class) && 
/* 30 */         info.getChannel().isOpen())
/* 31 */         ((TabCompleteTracker)info.get(TabCompleteTracker.class)).sendPacketToServer(info); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\TabCompleteThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
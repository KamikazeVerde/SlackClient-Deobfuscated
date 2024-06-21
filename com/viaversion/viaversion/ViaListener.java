/*    */ package com.viaversion.viaversion;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
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
/*    */ 
/*    */ public abstract class ViaListener
/*    */ {
/*    */   private final Class<? extends Protocol> requiredPipeline;
/*    */   private boolean registered;
/*    */   
/*    */   protected ViaListener(Class<? extends Protocol> requiredPipeline) {
/* 31 */     this.requiredPipeline = requiredPipeline;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected UserConnection getUserConnection(UUID uuid) {
/* 41 */     return Via.getManager().getConnectionManager().getConnectedClient(uuid);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isOnPipe(UUID uuid) {
/* 51 */     UserConnection userConnection = getUserConnection(uuid);
/* 52 */     return (userConnection != null && (this.requiredPipeline == null || userConnection
/* 53 */       .getProtocolInfo().getPipeline().contains(this.requiredPipeline)));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void register();
/*    */ 
/*    */   
/*    */   protected Class<? extends Protocol> getRequiredPipeline() {
/* 62 */     return this.requiredPipeline;
/*    */   }
/*    */   
/*    */   protected boolean isRegistered() {
/* 66 */     return this.registered;
/*    */   }
/*    */   
/*    */   protected void setRegistered(boolean registered) {
/* 70 */     this.registered = registered;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\ViaListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
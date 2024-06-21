/*    */ package com.viaversion.viaversion.api.protocol;
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
/*    */ public interface ProtocolPathKey
/*    */ {
/*    */   int clientProtocolVersion();
/*    */   
/*    */   int serverProtocolVersion();
/*    */   
/*    */   @Deprecated
/*    */   default int getClientProtocolVersion() {
/* 43 */     return clientProtocolVersion();
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   default int getServerProtocolVersion() {
/* 48 */     return serverProtocolVersion();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\ProtocolPathKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
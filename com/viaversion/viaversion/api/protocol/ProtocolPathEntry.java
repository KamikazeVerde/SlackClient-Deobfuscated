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
/*    */ 
/*    */ public interface ProtocolPathEntry
/*    */ {
/*    */   int outputProtocolVersion();
/*    */   
/*    */   Protocol<?, ?, ?, ?> protocol();
/*    */   
/*    */   @Deprecated
/*    */   default int getOutputProtocolVersion() {
/* 44 */     return outputProtocolVersion();
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   default Protocol getProtocol() {
/* 49 */     return protocol();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\ProtocolPathEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
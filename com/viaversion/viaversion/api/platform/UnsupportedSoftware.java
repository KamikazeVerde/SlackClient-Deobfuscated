/*    */ package com.viaversion.viaversion.api.platform;
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
/*    */ public interface UnsupportedSoftware
/*    */ {
/*    */   String getName();
/*    */   
/*    */   String getReason();
/*    */   
/*    */   String match();
/*    */   
/*    */   default boolean findMatch() {
/* 56 */     return (match() != null);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\platform\UnsupportedSoftware.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
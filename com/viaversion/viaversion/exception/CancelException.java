/*    */ package com.viaversion.viaversion.exception;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
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
/*    */ public class CancelException
/*    */   extends Exception
/*    */ {
/* 34 */   public static final CancelException CACHED = new CancelException("This packet is supposed to be cancelled; If you have debug enabled, you can ignore these")
/*    */     {
/*    */       public Throwable fillInStackTrace() {
/* 37 */         return this;
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   public CancelException() {}
/*    */   
/*    */   public CancelException(String message) {
/* 45 */     super(message);
/*    */   }
/*    */   
/*    */   public CancelException(String message, Throwable cause) {
/* 49 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public CancelException(Throwable cause) {
/* 53 */     super(cause);
/*    */   }
/*    */   
/*    */   public CancelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
/* 57 */     super(message, cause, enableSuppression, writableStackTrace);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static CancelException generate() {
/* 66 */     return Via.getManager().isDebug() ? new CancelException() : CACHED;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\exception\CancelException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
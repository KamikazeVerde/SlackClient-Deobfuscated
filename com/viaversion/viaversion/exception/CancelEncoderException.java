/*    */ package com.viaversion.viaversion.exception;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import io.netty.handler.codec.EncoderException;
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
/*    */ public class CancelEncoderException
/*    */   extends EncoderException
/*    */   implements CancelCodecException
/*    */ {
/* 34 */   public static final CancelEncoderException CACHED = new CancelEncoderException("This packet is supposed to be cancelled; If you have debug enabled, you can ignore these")
/*    */     {
/*    */       public Throwable fillInStackTrace() {
/* 37 */         return (Throwable)this;
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   public CancelEncoderException() {}
/*    */ 
/*    */   
/*    */   public CancelEncoderException(String message, Throwable cause) {
/* 46 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public CancelEncoderException(String message) {
/* 50 */     super(message);
/*    */   }
/*    */   
/*    */   public CancelEncoderException(Throwable cause) {
/* 54 */     super(cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static CancelEncoderException generate(Throwable cause) {
/* 64 */     return Via.getManager().isDebug() ? new CancelEncoderException(cause) : CACHED;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\exception\CancelEncoderException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
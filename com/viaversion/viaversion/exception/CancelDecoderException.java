/*    */ package com.viaversion.viaversion.exception;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import io.netty.handler.codec.DecoderException;
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
/*    */ public class CancelDecoderException
/*    */   extends DecoderException
/*    */   implements CancelCodecException
/*    */ {
/* 34 */   public static final CancelDecoderException CACHED = new CancelDecoderException("This packet is supposed to be cancelled; If you have debug enabled, you can ignore these")
/*    */     {
/*    */       public Throwable fillInStackTrace() {
/* 37 */         return (Throwable)this;
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   public CancelDecoderException() {}
/*    */ 
/*    */   
/*    */   public CancelDecoderException(String message, Throwable cause) {
/* 46 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public CancelDecoderException(String message) {
/* 50 */     super(message);
/*    */   }
/*    */   
/*    */   public CancelDecoderException(Throwable cause) {
/* 54 */     super(cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static CancelDecoderException generate(Throwable cause) {
/* 64 */     return Via.getManager().isDebug() ? new CancelDecoderException(cause) : CACHED;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\exception\CancelDecoderException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
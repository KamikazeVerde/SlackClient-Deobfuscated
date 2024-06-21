/*    */ package com.viaversion.viaversion.libs.opennbt.conversion;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConversionException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -2022049594558041160L;
/*    */   
/*    */   public ConversionException() {}
/*    */   
/*    */   public ConversionException(String message) {
/* 14 */     super(message);
/*    */   }
/*    */   
/*    */   public ConversionException(Throwable cause) {
/* 18 */     super(cause);
/*    */   }
/*    */   
/*    */   public ConversionException(String message, Throwable cause) {
/* 22 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\conversion\ConversionException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
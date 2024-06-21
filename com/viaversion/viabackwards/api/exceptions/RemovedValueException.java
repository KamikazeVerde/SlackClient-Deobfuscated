/*    */ package com.viaversion.viabackwards.api.exceptions;
/*    */ 
/*    */ import java.io.IOException;
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
/*    */ public class RemovedValueException
/*    */   extends IOException
/*    */ {
/* 28 */   public static final RemovedValueException EX = new RemovedValueException()
/*    */     {
/*    */       public Throwable fillInStackTrace() {
/* 31 */         return this;
/*    */       }
/*    */     };
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\exceptions\RemovedValueException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.viaversion.viaversion.api.minecraft.nbt;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class StringTagParseException
/*    */   extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = -3001637554903912905L;
/*    */   private final CharSequence buffer;
/*    */   private final int position;
/*    */   
/*    */   public StringTagParseException(String message, CharSequence buffer, int position) {
/* 37 */     super(message);
/* 38 */     this.buffer = buffer;
/* 39 */     this.position = position;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 44 */     return super.getMessage() + "(at position " + this.position + ")";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\nbt\StringTagParseException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
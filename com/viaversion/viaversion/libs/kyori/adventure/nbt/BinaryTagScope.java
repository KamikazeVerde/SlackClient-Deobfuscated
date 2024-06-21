/*    */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
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
/*    */ interface BinaryTagScope
/*    */   extends AutoCloseable
/*    */ {
/*    */   void close() throws IOException;
/*    */   
/*    */   public static final class NoOp
/*    */     implements BinaryTagScope
/*    */   {
/* 33 */     static final NoOp INSTANCE = new NoOp();
/*    */     
/*    */     public void close() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\BinaryTagScope.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
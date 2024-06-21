/*    */ package com.viaversion.viaversion.libs.opennbt.tag.limiter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface TagLimiter
/*    */ {
/*    */   static TagLimiter create(int maxBytes, int maxLevels) {
/* 13 */     return new TagLimiterImpl(maxBytes, maxLevels);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static TagLimiter noop() {
/* 22 */     return NoopTagLimiter.INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void countBytes(int paramInt);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void checkLevel(int paramInt);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default void countByte() {
/* 42 */     countBytes(1);
/*    */   }
/*    */   
/*    */   default void countShort() {
/* 46 */     countBytes(2);
/*    */   }
/*    */   
/*    */   default void countInt() {
/* 50 */     countBytes(4);
/*    */   }
/*    */   
/*    */   default void countFloat() {
/* 54 */     countBytes(4);
/*    */   }
/*    */   
/*    */   default void countLong() {
/* 58 */     countBytes(8);
/*    */   }
/*    */   
/*    */   default void countDouble() {
/* 62 */     countBytes(8);
/*    */   }
/*    */   
/*    */   int maxBytes();
/*    */   
/*    */   int maxLevels();
/*    */   
/*    */   int bytes();
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\tag\limiter\TagLimiter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
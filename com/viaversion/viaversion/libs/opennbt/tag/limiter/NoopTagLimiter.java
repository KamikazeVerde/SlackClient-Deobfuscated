/*    */ package com.viaversion.viaversion.libs.opennbt.tag.limiter;
/*    */ 
/*    */ final class NoopTagLimiter
/*    */   implements TagLimiter {
/*  5 */   static final TagLimiter INSTANCE = new NoopTagLimiter();
/*    */ 
/*    */ 
/*    */   
/*    */   public void countBytes(int bytes) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void checkLevel(int nestedLevel) {}
/*    */ 
/*    */   
/*    */   public int maxBytes() {
/* 17 */     return Integer.MAX_VALUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxLevels() {
/* 22 */     return Integer.MAX_VALUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int bytes() {
/* 27 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\tag\limiter\NoopTagLimiter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
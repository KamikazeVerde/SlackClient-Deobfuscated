/*    */ package com.viaversion.viaversion.libs.opennbt.tag.limiter;
/*    */ 
/*    */ final class TagLimiterImpl
/*    */   implements TagLimiter {
/*    */   private final int maxBytes;
/*    */   private final int maxLevels;
/*    */   private int bytes;
/*    */   
/*    */   TagLimiterImpl(int maxBytes, int maxLevels) {
/* 10 */     this.maxBytes = maxBytes;
/* 11 */     this.maxLevels = maxLevels;
/*    */   }
/*    */ 
/*    */   
/*    */   public void countBytes(int bytes) {
/* 16 */     this.bytes += bytes;
/* 17 */     if (this.bytes >= this.maxBytes) {
/* 18 */       throw new IllegalArgumentException("NBT data larger than expected (capped at " + this.maxBytes + ")");
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void checkLevel(int nestedLevel) {
/* 24 */     if (nestedLevel >= this.maxLevels) {
/* 25 */       throw new IllegalArgumentException("Nesting level higher than expected (capped at " + this.maxLevels + ")");
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxBytes() {
/* 31 */     return this.maxBytes;
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxLevels() {
/* 36 */     return this.maxLevels;
/*    */   }
/*    */ 
/*    */   
/*    */   public int bytes() {
/* 41 */     return this.bytes;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\tag\limiter\TagLimiterImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
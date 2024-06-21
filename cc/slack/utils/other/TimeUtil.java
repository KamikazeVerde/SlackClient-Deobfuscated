/*    */ package cc.slack.utils.other;
/*    */ 
/*    */ public class TimeUtil
/*    */ {
/*    */   private long currentMs;
/*    */   
/*    */   public TimeUtil() {
/*  8 */     reset();
/*    */   }
/*    */   
/*    */   public boolean hasReached(int milliseconds) {
/* 12 */     return (elapsed() >= milliseconds);
/*    */   }
/*    */   
/*    */   public boolean hasReached(long milliseconds) {
/* 16 */     return (elapsed() >= milliseconds);
/*    */   }
/*    */   
/*    */   public void resetWithOffset(long offset) {
/* 20 */     this.currentMs = getTime() + offset;
/*    */   }
/*    */   
/*    */   public long elapsed() {
/* 24 */     return System.currentTimeMillis() - this.currentMs;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 28 */     this.currentMs = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   private long getTime() {
/* 32 */     return System.nanoTime() / 1000000L;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\other\TimeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
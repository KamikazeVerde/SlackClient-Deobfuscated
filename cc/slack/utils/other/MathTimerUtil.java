/*     */ package cc.slack.utils.other;
/*     */ 
/*     */ import java.util.function.BooleanSupplier;
/*     */ import java.util.function.LongSupplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MathTimerUtil
/*     */ {
/*     */   private long lastMs;
/*     */   private long time;
/*     */   private boolean checkedFinish;
/*     */   
/*     */   public MathTimerUtil(long lasts) {
/*  23 */     this.lastMs = lasts;
/*     */   }
/*     */   
/*     */   public MathTimerUtil() {
/*  27 */     this.lastMs = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  32 */     reset();
/*  33 */     this.checkedFinish = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean firstFinish() {
/*  42 */     return checkAndSetFinish(() -> (System.currentTimeMillis() >= this.time + this.lastMs));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCooldown(long time) {
/*  51 */     this.lastMs = time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasFinished() {
/*  60 */     return isElapsed(this.time + this.lastMs, System::currentTimeMillis);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean finished(long delay) {
/*  70 */     return isElapsed(this.time, () -> System.currentTimeMillis() - delay);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDelayComplete(long l) {
/*  80 */     return isElapsed(this.lastMs, () -> System.currentTimeMillis() - l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean reached(long currentTime) {
/*  90 */     return isElapsed(this.time, () -> Math.max(0L, System.currentTimeMillis() - currentTime));
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/*  95 */     this.time = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getTime() {
/* 100 */     return Math.max(0L, System.currentTimeMillis() - this.time);
/*     */   }
/*     */   
/*     */   public boolean getCum(long hentai) {
/* 104 */     return (System.currentTimeMillis() - this.lastMs >= hentai);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasTimeElapsed(long owo, boolean reset) {
/* 115 */     if (getTime() >= owo) {
/* 116 */       if (reset) {
/* 117 */         reset();
/*     */       }
/* 119 */       return true;
/*     */     } 
/* 121 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkAndSetFinish(BooleanSupplier condition) {
/* 126 */     if (condition.getAsBoolean() && !this.checkedFinish) {
/* 127 */       this.checkedFinish = true;
/* 128 */       return true;
/*     */     } 
/* 130 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isElapsed(long targetTime, LongSupplier currentTimeSupplier) {
/* 135 */     return (currentTimeSupplier.getAsLong() >= targetTime);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\other\MathTimerUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package cc.slack.utils.other;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import java.math.MathContext;
/*    */ import java.math.RoundingMode;
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ 
/*    */ public class MathUtil
/*    */ {
/*    */   public static double roundToDecimalPlace(double value, double inc) {
/* 11 */     double halfOfInc = inc / 2.0D;
/* 12 */     double floored = Math.floor(value / inc) * inc;
/*    */     
/* 14 */     if (value >= floored + halfOfInc) {
/* 15 */       return (new BigDecimal(Math.ceil(value / inc) * inc, MathContext.DECIMAL64)).stripTrailingZeros().doubleValue();
/*    */     }
/* 17 */     return (new BigDecimal(floored, MathContext.DECIMAL64)).stripTrailingZeros().doubleValue();
/*    */   }
/*    */   
/*    */   public static double getRandomInRange(double min, double max) {
/* 21 */     if (min == max)
/* 22 */       return min; 
/* 23 */     if (min > max) {
/* 24 */       double oldMin = min;
/* 25 */       min = max;
/* 26 */       max = oldMin;
/*    */     } 
/*    */     
/* 29 */     return ThreadLocalRandom.current().nextDouble(min, max);
/*    */   }
/*    */   
/*    */   public static int getRandomInRange(int min, int max) {
/* 33 */     if (min == max)
/* 34 */       return min; 
/* 35 */     if (min > max) {
/* 36 */       int oldMin = min;
/* 37 */       min = max;
/* 38 */       max = oldMin;
/*    */     } 
/*    */     
/* 41 */     return ThreadLocalRandom.current().nextInt(min, max);
/*    */   }
/*    */   
/*    */   public static float getDifference(float base, float yaw) {
/*    */     float bigger;
/* 46 */     if (base >= yaw) {
/* 47 */       bigger = base - yaw;
/*    */     } else {
/* 49 */       bigger = yaw - base;
/* 50 */     }  return bigger;
/*    */   }
/*    */ 
/*    */   
/*    */   public static float interpolate(float newValue, float oldValue, float partialTicks) {
/* 55 */     return oldValue + (newValue - oldValue) * partialTicks;
/*    */   }
/*    */   
/*    */   public static double interpolate(double newValue, double oldValue, float partialTicks) {
/* 59 */     return oldValue + (newValue - oldValue) * partialTicks;
/*    */   }
/*    */   
/*    */   public static double interpolate(double newValue, double oldValue, double partialTicks) {
/* 63 */     return oldValue + (newValue - oldValue) * partialTicks;
/*    */   }
/*    */ 
/*    */   
/*    */   public static double lerp(double oldValue, double newValue, double partialTicks) {
/* 68 */     return oldValue + partialTicks * (newValue - oldValue);
/*    */   }
/*    */   
/*    */   public static float lerp(float oldValue, float newValue, float partialTicks) {
/* 72 */     return oldValue + partialTicks * (newValue - oldValue);
/*    */   }
/*    */   
/*    */   public static float clamp(float number, float min, float max) {
/* 76 */     return (number < min) ? min : Math.min(number, max);
/*    */   }
/*    */   
/*    */   public static double round(double value, int places) {
/* 80 */     if (places < 0) throw new IllegalArgumentException();
/*    */     
/* 82 */     return (new BigDecimal(Double.toString(value))).setScale(places, RoundingMode.HALF_UP).doubleValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\other\MathUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
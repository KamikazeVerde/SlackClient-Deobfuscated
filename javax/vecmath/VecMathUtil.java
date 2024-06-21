/*    */ package javax.vecmath;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class VecMathUtil
/*    */ {
/*    */   static int floatToIntBits(float paramFloat) {
/* 60 */     if (paramFloat == 0.0F) {
/* 61 */       return 0;
/*    */     }
/*    */     
/* 64 */     return Float.floatToIntBits(paramFloat);
/*    */   }
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
/*    */   static long doubleToLongBits(double paramDouble) {
/* 89 */     if (paramDouble == 0.0D) {
/* 90 */       return 0L;
/*    */     }
/*    */     
/* 93 */     return Double.doubleToLongBits(paramDouble);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\VecMathUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
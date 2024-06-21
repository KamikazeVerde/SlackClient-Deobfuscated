/*    */ package net.optifine.util;
/*    */ 
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class MathUtils
/*    */ {
/*    */   public static final float PI = 3.1415927F;
/*    */   public static final float PI2 = 6.2831855F;
/*    */   public static final float PId2 = 1.5707964F;
/* 10 */   private static final float[] ASIN_TABLE = new float[65536];
/*    */ 
/*    */   
/*    */   public static float asin(float value) {
/* 14 */     return ASIN_TABLE[(int)((value + 1.0F) * 32767.5D) & 0xFFFF];
/*    */   }
/*    */ 
/*    */   
/*    */   public static float acos(float value) {
/* 19 */     return 1.5707964F - ASIN_TABLE[(int)((value + 1.0F) * 32767.5D) & 0xFFFF];
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getAverage(int[] vals) {
/* 24 */     if (vals.length <= 0)
/*    */     {
/* 26 */       return 0;
/*    */     }
/*    */ 
/*    */     
/* 30 */     int i = getSum(vals);
/* 31 */     int j = i / vals.length;
/* 32 */     return j;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getSum(int[] vals) {
/* 38 */     if (vals.length <= 0)
/*    */     {
/* 40 */       return 0;
/*    */     }
/*    */ 
/*    */     
/* 44 */     int i = 0;
/*    */     
/* 46 */     for (int j = 0; j < vals.length; j++) {
/*    */       
/* 48 */       int k = vals[j];
/* 49 */       i += k;
/*    */     } 
/*    */     
/* 52 */     return i;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static int roundDownToPowerOfTwo(int val) {
/* 58 */     int i = MathHelper.roundUpToPowerOfTwo(val);
/* 59 */     return (val == i) ? i : (i / 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean equalsDelta(float f1, float f2, float delta) {
/* 64 */     return (Math.abs(f1 - f2) <= delta);
/*    */   }
/*    */ 
/*    */   
/*    */   public static float toDeg(float angle) {
/* 69 */     return angle * 180.0F / MathHelper.PI;
/*    */   }
/*    */ 
/*    */   
/*    */   public static float toRad(float angle) {
/* 74 */     return angle / 180.0F * MathHelper.PI;
/*    */   }
/*    */ 
/*    */   
/*    */   public static float roundToFloat(double d) {
/* 79 */     return (float)(Math.round(d * 1.0E8D) / 1.0E8D);
/*    */   }
/*    */ 
/*    */   
/*    */   static {
/* 84 */     for (int i = 0; i < 65536; i++)
/*    */     {
/* 86 */       ASIN_TABLE[i] = (float)Math.asin(i / 32767.5D - 1.0D);
/*    */     }
/*    */     
/* 89 */     for (int j = -1; j < 2; j++)
/*    */     {
/* 91 */       ASIN_TABLE[(int)((j + 1.0D) * 32767.5D) & 0xFFFF] = (float)Math.asin(j);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifin\\util\MathUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
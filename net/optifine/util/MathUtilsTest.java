/*    */ package net.optifine.util;
/*    */ 
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ public class MathUtilsTest
/*    */ {
/*    */   public static void main(String[] args) throws Exception {
/*  9 */     OPER[] amathutilstest$oper = OPER.values();
/*    */     
/* 11 */     for (OPER mathutilstest$oper : amathutilstest$oper) {
/* 12 */       dbg("******** " + mathutilstest$oper + " ***********");
/* 13 */       test(mathutilstest$oper, false);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void test(OPER oper, boolean fast) {
/*    */     double d0, d1;
/* 19 */     MathHelper.fastMath = fast;
/*    */ 
/*    */ 
/*    */     
/* 23 */     switch (oper) {
/*    */       
/*    */       case SIN:
/*    */       case COS:
/* 27 */         d0 = -MathHelper.PI;
/* 28 */         d1 = MathHelper.PI;
/*    */         break;
/*    */       
/*    */       case ASIN:
/*    */       case ACOS:
/* 33 */         d0 = -1.0D;
/* 34 */         d1 = 1.0D;
/*    */         break;
/*    */       
/*    */       default:
/*    */         return;
/*    */     } 
/*    */     
/* 41 */     int i = 10;
/*    */     
/* 43 */     for (int j = 0; j <= i; j++) {
/*    */       float f, f1;
/* 45 */       double d2 = d0 + j * (d1 - d0) / i;
/*    */ 
/*    */ 
/*    */       
/* 49 */       switch (oper) {
/*    */         
/*    */         case SIN:
/* 52 */           f = (float)Math.sin(d2);
/* 53 */           f1 = MathHelper.sin((float)d2);
/*    */           break;
/*    */         
/*    */         case COS:
/* 57 */           f = (float)Math.cos(d2);
/* 58 */           f1 = MathHelper.cos((float)d2);
/*    */           break;
/*    */         
/*    */         case ASIN:
/* 62 */           f = (float)Math.asin(d2);
/* 63 */           f1 = MathUtils.asin((float)d2);
/*    */           break;
/*    */         
/*    */         case ACOS:
/* 67 */           f = (float)Math.acos(d2);
/* 68 */           f1 = MathUtils.acos((float)d2);
/*    */           break;
/*    */         
/*    */         default:
/*    */           return;
/*    */       } 
/*    */       
/* 75 */       dbg(String.format("%.2f, Math: %f, Helper: %f, diff: %f", new Object[] { Double.valueOf(d2), Float.valueOf(f), Float.valueOf(f1), Float.valueOf(Math.abs(f - f1)) }));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void dbg(String str) {
/* 81 */     System.out.println(str);
/*    */   }
/*    */   
/*    */   private enum OPER
/*    */   {
/* 86 */     SIN,
/* 87 */     COS,
/* 88 */     ASIN,
/* 89 */     ACOS;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifin\\util\MathUtilsTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
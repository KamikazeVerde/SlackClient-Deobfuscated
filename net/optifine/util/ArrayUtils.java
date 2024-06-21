/*    */ package net.optifine.util;
/*    */ 
/*    */ 
/*    */ public class ArrayUtils
/*    */ {
/*    */   public static boolean contains(Object[] arr, Object val) {
/*  7 */     if (arr == null)
/*    */     {
/*  9 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 13 */     for (int i = 0; i < arr.length; i++) {
/*    */       
/* 15 */       Object object = arr[i];
/*    */       
/* 17 */       if (object == val)
/*    */       {
/* 19 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 23 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifin\\util\ArrayUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
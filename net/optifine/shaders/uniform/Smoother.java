/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.optifine.util.CounterInt;
/*    */ import net.optifine.util.SmoothFloat;
/*    */ 
/*    */ public class Smoother
/*    */ {
/* 10 */   private static Map<Integer, SmoothFloat> mapSmoothValues = new HashMap<>();
/* 11 */   private static CounterInt counterIds = new CounterInt(1);
/*    */ 
/*    */   
/*    */   public static float getSmoothValue(int id, float value, float timeFadeUpSec, float timeFadeDownSec) {
/* 15 */     synchronized (mapSmoothValues) {
/*    */       
/* 17 */       Integer integer = Integer.valueOf(id);
/* 18 */       SmoothFloat smoothfloat = mapSmoothValues.get(integer);
/*    */       
/* 20 */       if (smoothfloat == null) {
/*    */         
/* 22 */         smoothfloat = new SmoothFloat(value, timeFadeUpSec, timeFadeDownSec);
/* 23 */         mapSmoothValues.put(integer, smoothfloat);
/*    */       } 
/*    */       
/* 26 */       float f = smoothfloat.getSmoothValue(value, timeFadeUpSec, timeFadeDownSec);
/* 27 */       return f;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getNextId() {
/* 33 */     synchronized (counterIds) {
/*    */       
/* 35 */       return counterIds.nextValue();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void resetValues() {
/* 41 */     synchronized (mapSmoothValues) {
/*    */       
/* 43 */       mapSmoothValues.clear();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shader\\uniform\Smoother.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
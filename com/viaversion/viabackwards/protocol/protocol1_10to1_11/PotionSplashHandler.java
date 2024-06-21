/*    */ package com.viaversion.viabackwards.protocol.protocol1_10to1_11;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
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
/*    */ public class PotionSplashHandler
/*    */ {
/* 25 */   private static final Int2IntMap DATA = (Int2IntMap)new Int2IntOpenHashMap(14, 0.99F);
/*    */   
/*    */   static {
/* 28 */     DATA.defaultReturnValue(-1);
/* 29 */     DATA.put(2039713, 5);
/* 30 */     DATA.put(8356754, 7);
/* 31 */     DATA.put(2293580, 9);
/* 32 */     DATA.put(14981690, 12);
/* 33 */     DATA.put(8171462, 14);
/* 34 */     DATA.put(5926017, 17);
/* 35 */     DATA.put(3035801, 19);
/* 36 */     DATA.put(16262179, 21);
/* 37 */     DATA.put(4393481, 23);
/* 38 */     DATA.put(5149489, 25);
/* 39 */     DATA.put(13458603, 28);
/* 40 */     DATA.put(9643043, 31);
/* 41 */     DATA.put(4738376, 34);
/* 42 */     DATA.put(3381504, 36);
/*    */   }
/*    */   
/*    */   public static int getOldData(int data) {
/* 46 */     return DATA.get(data);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_10to1_11\PotionSplashHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
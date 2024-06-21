/*    */ package com.viaversion.viaversion.protocols.protocol1_11to1_10.data;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import com.viaversion.viaversion.util.Pair;
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
/*    */ public class PotionColorMapping
/*    */ {
/* 27 */   private static final Int2ObjectMap<Pair<Integer, Boolean>> POTIONS = (Int2ObjectMap<Pair<Integer, Boolean>>)new Int2ObjectOpenHashMap(37, 0.99F);
/*    */   
/*    */   static {
/* 30 */     addRewrite(0, 3694022, false);
/* 31 */     addRewrite(1, 3694022, false);
/* 32 */     addRewrite(2, 3694022, false);
/* 33 */     addRewrite(3, 3694022, false);
/* 34 */     addRewrite(4, 3694022, false);
/* 35 */     addRewrite(5, 2039713, false);
/* 36 */     addRewrite(6, 2039713, false);
/* 37 */     addRewrite(7, 8356754, false);
/* 38 */     addRewrite(8, 8356754, false);
/* 39 */     addRewrite(9, 2293580, false);
/* 40 */     addRewrite(10, 2293580, false);
/* 41 */     addRewrite(11, 2293580, false);
/* 42 */     addRewrite(12, 14981690, false);
/* 43 */     addRewrite(13, 14981690, false);
/* 44 */     addRewrite(14, 8171462, false);
/* 45 */     addRewrite(15, 8171462, false);
/* 46 */     addRewrite(16, 8171462, false);
/* 47 */     addRewrite(17, 5926017, false);
/* 48 */     addRewrite(18, 5926017, false);
/* 49 */     addRewrite(19, 3035801, false);
/* 50 */     addRewrite(20, 3035801, false);
/* 51 */     addRewrite(21, 16262179, true);
/* 52 */     addRewrite(22, 16262179, true);
/* 53 */     addRewrite(23, 4393481, true);
/* 54 */     addRewrite(24, 4393481, true);
/* 55 */     addRewrite(25, 5149489, false);
/* 56 */     addRewrite(26, 5149489, false);
/* 57 */     addRewrite(27, 5149489, false);
/* 58 */     addRewrite(28, 13458603, false);
/* 59 */     addRewrite(29, 13458603, false);
/* 60 */     addRewrite(30, 13458603, false);
/* 61 */     addRewrite(31, 9643043, false);
/* 62 */     addRewrite(32, 9643043, false);
/* 63 */     addRewrite(33, 9643043, false);
/* 64 */     addRewrite(34, 4738376, false);
/* 65 */     addRewrite(35, 4738376, false);
/* 66 */     addRewrite(36, 3381504, false);
/*    */   }
/*    */   
/*    */   public static Pair<Integer, Boolean> getNewData(int oldData) {
/* 70 */     return (Pair<Integer, Boolean>)POTIONS.get(oldData);
/*    */   }
/*    */   
/*    */   private static void addRewrite(int oldData, int newData, boolean isInstant) {
/* 74 */     POTIONS.put(oldData, new Pair(Integer.valueOf(newData), Boolean.valueOf(isInstant)));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_11to1_10\data\PotionColorMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
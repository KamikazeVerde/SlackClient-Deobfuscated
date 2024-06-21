/*    */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.sounds;
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
/*    */ public class Effect
/*    */ {
/* 25 */   private static final Int2IntMap EFFECTS = (Int2IntMap)new Int2IntOpenHashMap(19, 0.99F);
/*    */   
/*    */   static {
/* 28 */     addRewrite(1005, 1010);
/* 29 */     addRewrite(1003, 1005);
/* 30 */     addRewrite(1006, 1011);
/* 31 */     addRewrite(1004, 1009);
/* 32 */     addRewrite(1007, 1015);
/* 33 */     addRewrite(1008, 1016);
/* 34 */     addRewrite(1009, 1016);
/* 35 */     addRewrite(1010, 1019);
/* 36 */     addRewrite(1011, 1020);
/* 37 */     addRewrite(1012, 1021);
/* 38 */     addRewrite(1014, 1024);
/* 39 */     addRewrite(1015, 1025);
/* 40 */     addRewrite(1016, 1026);
/* 41 */     addRewrite(1017, 1027);
/* 42 */     addRewrite(1020, 1029);
/* 43 */     addRewrite(1021, 1030);
/* 44 */     addRewrite(1022, 1031);
/* 45 */     addRewrite(1013, 1023);
/* 46 */     addRewrite(1018, 1028);
/*    */   }
/*    */   
/*    */   public static int getNewId(int id) {
/* 50 */     return EFFECTS.getOrDefault(id, id);
/*    */   }
/*    */   
/*    */   public static boolean contains(int oldId) {
/* 54 */     return EFFECTS.containsKey(oldId);
/*    */   }
/*    */   
/*    */   private static void addRewrite(int oldId, int newId) {
/* 58 */     EFFECTS.put(oldId, newId);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\sounds\Effect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
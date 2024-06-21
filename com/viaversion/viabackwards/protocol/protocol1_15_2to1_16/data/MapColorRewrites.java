/*    */ package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data;
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
/*    */ public final class MapColorRewrites
/*    */ {
/* 25 */   private static final Int2IntMap MAPPINGS = (Int2IntMap)new Int2IntOpenHashMap();
/*    */   
/*    */   static {
/* 28 */     MAPPINGS.put(208, 113);
/* 29 */     MAPPINGS.put(209, 114);
/* 30 */     MAPPINGS.put(210, 114);
/* 31 */     MAPPINGS.put(211, 112);
/* 32 */     MAPPINGS.put(212, 152);
/* 33 */     MAPPINGS.put(213, 83);
/* 34 */     MAPPINGS.put(214, 83);
/* 35 */     MAPPINGS.put(215, 155);
/* 36 */     MAPPINGS.put(216, 143);
/* 37 */     MAPPINGS.put(217, 115);
/* 38 */     MAPPINGS.put(218, 115);
/* 39 */     MAPPINGS.put(219, 143);
/* 40 */     MAPPINGS.put(220, 127);
/* 41 */     MAPPINGS.put(221, 127);
/* 42 */     MAPPINGS.put(222, 127);
/* 43 */     MAPPINGS.put(223, 95);
/* 44 */     MAPPINGS.put(224, 127);
/* 45 */     MAPPINGS.put(225, 127);
/* 46 */     MAPPINGS.put(226, 124);
/* 47 */     MAPPINGS.put(227, 95);
/* 48 */     MAPPINGS.put(228, 187);
/* 49 */     MAPPINGS.put(229, 155);
/* 50 */     MAPPINGS.put(230, 184);
/* 51 */     MAPPINGS.put(231, 187);
/* 52 */     MAPPINGS.put(232, 127);
/* 53 */     MAPPINGS.put(233, 124);
/* 54 */     MAPPINGS.put(234, 125);
/* 55 */     MAPPINGS.put(235, 127);
/*    */   }
/*    */   
/*    */   public static int getMappedColor(int color) {
/* 59 */     return MAPPINGS.getOrDefault(color, -1);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_15_2to1_16\data\MapColorRewrites.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.data;
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
/* 28 */     MAPPINGS.put(236, 85);
/* 29 */     MAPPINGS.put(237, 27);
/* 30 */     MAPPINGS.put(238, 45);
/* 31 */     MAPPINGS.put(239, 84);
/* 32 */     MAPPINGS.put(240, 144);
/* 33 */     MAPPINGS.put(241, 145);
/* 34 */     MAPPINGS.put(242, 146);
/* 35 */     MAPPINGS.put(243, 147);
/* 36 */     MAPPINGS.put(244, 127);
/* 37 */     MAPPINGS.put(245, 226);
/* 38 */     MAPPINGS.put(246, 124);
/* 39 */     MAPPINGS.put(247, 227);
/*    */   }
/*    */   
/*    */   public static int getMappedColor(int color) {
/* 43 */     return MAPPINGS.getOrDefault(color, -1);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_16_4to1_17\data\MapColorRewrites.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
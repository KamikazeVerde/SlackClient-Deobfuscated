/*    */ package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.data;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ public final class BlockEntityIds
/*    */ {
/*    */   private static final int[] IDS;
/*    */   
/*    */   static {
/* 27 */     int[] ids = com.viaversion.viaversion.protocols.protocol1_18to1_17_1.BlockEntityIds.getIds();
/* 28 */     IDS = new int[Arrays.stream(ids).max().getAsInt() + 1];
/* 29 */     Arrays.fill(IDS, -1);
/* 30 */     for (int i = 0; i < ids.length; i++) {
/* 31 */       int id = ids[i];
/* 32 */       if (id != -1) {
/* 33 */         IDS[id] = i;
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public static int mappedId(int id) {
/* 39 */     if (id < 0 || id > IDS.length) {
/* 40 */       return -1;
/*    */     }
/* 42 */     return IDS[id];
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_17_1to1_18\data\BlockEntityIds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
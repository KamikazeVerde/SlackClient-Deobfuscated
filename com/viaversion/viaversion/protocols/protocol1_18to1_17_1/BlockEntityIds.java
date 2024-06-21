/*    */ package com.viaversion.viaversion.protocols.protocol1_18to1_17_1;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
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
/* 25 */   private static final int[] IDS = new int[14];
/*    */ 
/*    */   
/*    */   static {
/* 29 */     Arrays.fill(IDS, -1);
/* 30 */     IDS[1] = 8;
/* 31 */     IDS[2] = 21;
/* 32 */     IDS[3] = 13;
/* 33 */     IDS[4] = 14;
/* 34 */     IDS[5] = 24;
/* 35 */     IDS[6] = 18;
/* 36 */     IDS[7] = 19;
/* 37 */     IDS[8] = 20;
/* 38 */     IDS[9] = 7;
/* 39 */     IDS[10] = 22;
/* 40 */     IDS[11] = 23;
/* 41 */     IDS[12] = 30;
/* 42 */     IDS[13] = 31;
/*    */   }
/*    */   
/*    */   public static int newId(int id) {
/*    */     int newId;
/* 47 */     if (id < 0 || id > IDS.length || (newId = IDS[id]) == -1) {
/* 48 */       Via.getPlatform().getLogger().warning("Received out of bounds block entity id: " + id);
/* 49 */       return -1;
/*    */     } 
/* 51 */     return newId;
/*    */   }
/*    */   
/*    */   public static int[] getIds() {
/* 55 */     return IDS;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_18to1_17_1\BlockEntityIds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
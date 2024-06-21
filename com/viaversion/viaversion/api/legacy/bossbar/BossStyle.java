/*    */ package com.viaversion.viaversion.api.legacy.bossbar;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum BossStyle
/*    */ {
/* 27 */   SOLID(0),
/* 28 */   SEGMENTED_6(1),
/* 29 */   SEGMENTED_10(2),
/* 30 */   SEGMENTED_12(3),
/* 31 */   SEGMENTED_20(4);
/*    */   
/*    */   private final int id;
/*    */   
/*    */   BossStyle(int id) {
/* 36 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 40 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\legacy\bossbar\BossStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
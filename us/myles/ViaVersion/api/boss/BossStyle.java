/*    */ package us.myles.ViaVersion.api.boss;
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
/*    */ @Deprecated
/*    */ public enum BossStyle
/*    */ {
/* 28 */   SOLID(0),
/* 29 */   SEGMENTED_6(1),
/* 30 */   SEGMENTED_10(2),
/* 31 */   SEGMENTED_12(3),
/* 32 */   SEGMENTED_20(4);
/*    */   
/*    */   private final int id;
/*    */   
/*    */   BossStyle(int id) {
/* 37 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 41 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar\\us\myles\ViaVersion\api\boss\BossStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
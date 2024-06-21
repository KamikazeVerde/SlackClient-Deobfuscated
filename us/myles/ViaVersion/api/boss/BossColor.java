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
/*    */ public enum BossColor
/*    */ {
/* 28 */   PINK(0),
/* 29 */   BLUE(1),
/* 30 */   RED(2),
/* 31 */   GREEN(3),
/* 32 */   YELLOW(4),
/* 33 */   PURPLE(5),
/* 34 */   WHITE(6);
/*    */   
/*    */   private final int id;
/*    */   
/*    */   BossColor(int id) {
/* 39 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 43 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar\\us\myles\ViaVersion\api\boss\BossColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
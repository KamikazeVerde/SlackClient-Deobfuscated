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
/*    */ public enum BossFlag
/*    */ {
/* 27 */   DARKEN_SKY(1),
/* 28 */   PLAY_BOSS_MUSIC(2),
/* 29 */   CREATE_FOG(4);
/*    */   
/*    */   private final int id;
/*    */   
/*    */   BossFlag(int id) {
/* 34 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 38 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\legacy\bossbar\BossFlag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
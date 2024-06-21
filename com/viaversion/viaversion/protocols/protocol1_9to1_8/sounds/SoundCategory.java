/*    */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.sounds;
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
/*    */ public enum SoundCategory
/*    */ {
/* 22 */   MASTER("master", 0),
/* 23 */   MUSIC("music", 1),
/* 24 */   RECORD("record", 2),
/* 25 */   WEATHER("weather", 3),
/* 26 */   BLOCK("block", 4),
/* 27 */   HOSTILE("hostile", 5),
/* 28 */   NEUTRAL("neutral", 6),
/* 29 */   PLAYER("player", 7),
/* 30 */   AMBIENT("ambient", 8),
/* 31 */   VOICE("voice", 9);
/*    */   
/*    */   private final String name;
/*    */   private final int id;
/*    */   
/*    */   SoundCategory(String name, int id) {
/* 37 */     this.name = name;
/* 38 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 42 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 46 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\sounds\SoundCategory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
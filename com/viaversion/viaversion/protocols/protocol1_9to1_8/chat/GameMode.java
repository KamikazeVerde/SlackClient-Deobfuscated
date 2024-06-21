/*    */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.chat;
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
/*    */ public enum GameMode
/*    */ {
/* 21 */   SURVIVAL(0, "Survival Mode"),
/* 22 */   CREATIVE(1, "Creative Mode"),
/* 23 */   ADVENTURE(2, "Adventure Mode"),
/* 24 */   SPECTATOR(3, "Spectator Mode");
/*    */   
/*    */   private final int id;
/*    */   private final String text;
/*    */   
/*    */   GameMode(int id, String text) {
/* 30 */     this.id = id;
/* 31 */     this.text = text;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 35 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getText() {
/* 39 */     return this.text;
/*    */   }
/*    */   
/*    */   public static GameMode getById(int id) {
/* 43 */     for (GameMode gm : values()) {
/* 44 */       if (gm.getId() == id)
/* 45 */         return gm; 
/* 46 */     }  return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\chat\GameMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
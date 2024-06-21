/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;
/*    */ 
/*    */ import java.util.Optional;
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
/*    */ public enum SoundSource
/*    */ {
/* 23 */   MASTER("master", 0),
/* 24 */   MUSIC("music", 1),
/* 25 */   RECORD("record", 2),
/* 26 */   WEATHER("weather", 3),
/* 27 */   BLOCK("block", 4),
/* 28 */   HOSTILE("hostile", 5),
/* 29 */   NEUTRAL("neutral", 6),
/* 30 */   PLAYER("player", 7),
/* 31 */   AMBIENT("ambient", 8),
/* 32 */   VOICE("voice", 9);
/*    */   
/*    */   private final String name;
/*    */   private final int id;
/*    */   
/*    */   SoundSource(String name, int id) {
/* 38 */     this.name = name;
/* 39 */     this.id = id;
/*    */   }
/*    */   
/*    */   public static Optional<SoundSource> findBySource(String source) {
/* 43 */     for (SoundSource item : values()) {
/* 44 */       if (item.name.equalsIgnoreCase(source))
/* 45 */         return Optional.of(item); 
/* 46 */     }  return Optional.empty();
/*    */   }
/*    */   
/*    */   public String getName() {
/* 50 */     return this.name;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 54 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\data\SoundSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
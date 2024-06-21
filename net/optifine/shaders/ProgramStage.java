/*    */ package net.optifine.shaders;
/*    */ 
/*    */ public enum ProgramStage
/*    */ {
/*  5 */   NONE(""),
/*  6 */   SHADOW("shadow"),
/*  7 */   GBUFFERS("gbuffers"),
/*  8 */   DEFERRED("deferred"),
/*  9 */   COMPOSITE("composite");
/*    */   
/*    */   private String name;
/*    */ 
/*    */   
/*    */   ProgramStage(String name) {
/* 15 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 20 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\ProgramStage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
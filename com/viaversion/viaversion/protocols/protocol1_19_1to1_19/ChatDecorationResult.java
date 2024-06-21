/*    */ package com.viaversion.viaversion.protocols.protocol1_19_1to1_19;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.gson.JsonElement;
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
/*    */ public final class ChatDecorationResult
/*    */ {
/*    */   private final JsonElement content;
/*    */   private final boolean overlay;
/*    */   
/*    */   public ChatDecorationResult(JsonElement content, boolean overlay) {
/* 28 */     this.content = content;
/* 29 */     this.overlay = overlay;
/*    */   }
/*    */   
/*    */   public JsonElement content() {
/* 33 */     return this.content;
/*    */   }
/*    */   
/*    */   public boolean overlay() {
/* 37 */     return this.overlay;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19_1to1_19\ChatDecorationResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
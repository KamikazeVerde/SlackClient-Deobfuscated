/*    */ package com.viaversion.viaversion.api.minecraft;
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
/*    */ public final class TagData
/*    */ {
/*    */   private final String identifier;
/*    */   private final int[] entries;
/*    */   
/*    */   public TagData(String identifier, int[] entries) {
/* 30 */     this.identifier = identifier;
/* 31 */     this.entries = entries;
/*    */   }
/*    */   
/*    */   public String identifier() {
/* 35 */     return this.identifier;
/*    */   }
/*    */   
/*    */   public int[] entries() {
/* 39 */     return this.entries;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\TagData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
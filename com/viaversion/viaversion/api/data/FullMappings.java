/*    */ package com.viaversion.viaversion.api.data;
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
/*    */ public interface FullMappings
/*    */   extends Mappings
/*    */ {
/*    */   FullMappings inverse();
/*    */   
/*    */   String mappedIdentifier(String paramString);
/*    */   
/*    */   String mappedIdentifier(int paramInt);
/*    */   
/*    */   String identifier(int paramInt);
/*    */   
/*    */   int mappedId(String paramString);
/*    */   
/*    */   int id(String paramString);
/*    */   
/*    */   @Deprecated
/*    */   default Mappings mappings() {
/* 34 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\data\FullMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
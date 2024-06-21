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
/*    */ public interface BiMappings
/*    */   extends Mappings
/*    */ {
/*    */   static BiMappings of(Mappings mappings) {
/* 36 */     return of(mappings, mappings.inverse());
/*    */   }
/*    */   
/*    */   static BiMappings of(Mappings mappings, Mappings inverse) {
/* 40 */     return new BiMappingsBase(mappings, inverse);
/*    */   }
/*    */   
/*    */   BiMappings inverse();
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\data\BiMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
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
/*    */ public class BiMappingsBase
/*    */   implements BiMappings
/*    */ {
/*    */   protected final Mappings mappings;
/*    */   private final BiMappingsBase inverse;
/*    */   
/*    */   protected BiMappingsBase(Mappings mappings, Mappings inverse) {
/* 31 */     this.mappings = mappings;
/* 32 */     this.inverse = new BiMappingsBase(inverse, this);
/*    */   }
/*    */   
/*    */   private BiMappingsBase(Mappings mappings, BiMappingsBase inverse) {
/* 36 */     this.mappings = mappings;
/* 37 */     this.inverse = inverse;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getNewId(int id) {
/* 42 */     return this.mappings.getNewId(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setNewId(int id, int mappedId) {
/* 47 */     this.mappings.setNewId(id, mappedId);
/* 48 */     this.inverse.mappings.setNewId(mappedId, id);
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 53 */     return this.mappings.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public int mappedSize() {
/* 58 */     return this.mappings.mappedSize();
/*    */   }
/*    */ 
/*    */   
/*    */   public BiMappings inverse() {
/* 63 */     return this.inverse;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\data\BiMappingsBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
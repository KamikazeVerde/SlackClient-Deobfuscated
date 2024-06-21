/*    */ package com.viaversion.viabackwards.api.data;
/*    */ 
/*    */ import com.viaversion.viaversion.api.data.BiMappingsBase;
/*    */ import com.viaversion.viaversion.api.data.Mappings;
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
/*    */ public final class ItemMappings
/*    */   extends BiMappingsBase
/*    */ {
/*    */   private ItemMappings(Mappings mappings, Mappings inverse) {
/* 26 */     super(mappings, inverse);
/*    */   }
/*    */   
/*    */   public static ItemMappings of(Mappings mappings, Mappings inverse) {
/* 30 */     return new ItemMappings(mappings, inverse);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setNewId(int id, int mappedId) {
/* 36 */     this.mappings.setNewId(id, mappedId);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\data\ItemMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
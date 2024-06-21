/*    */ package com.viaversion.viaversion.api.data;
/*    */ 
/*    */ import com.viaversion.viaversion.util.Int2IntBiMap;
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
/*    */ public class Int2IntMapBiMappings
/*    */   implements BiMappings
/*    */ {
/*    */   private final Int2IntBiMap mappings;
/*    */   private final Int2IntMapBiMappings inverse;
/*    */   
/*    */   protected Int2IntMapBiMappings(Int2IntBiMap mappings) {
/* 33 */     this.mappings = mappings;
/* 34 */     this.inverse = new Int2IntMapBiMappings(mappings.inverse(), this);
/* 35 */     mappings.defaultReturnValue(-1);
/*    */   }
/*    */   
/*    */   private Int2IntMapBiMappings(Int2IntBiMap mappings, Int2IntMapBiMappings inverse) {
/* 39 */     this.mappings = mappings;
/* 40 */     this.inverse = inverse;
/*    */   }
/*    */   
/*    */   public static Int2IntMapBiMappings of(Int2IntBiMap mappings) {
/* 44 */     return new Int2IntMapBiMappings(mappings);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getNewId(int id) {
/* 49 */     return this.mappings.get(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setNewId(int id, int mappedId) {
/* 54 */     this.mappings.put(id, mappedId);
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 59 */     return this.mappings.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public int mappedSize() {
/* 64 */     return this.mappings.inverse().size();
/*    */   }
/*    */ 
/*    */   
/*    */   public BiMappings inverse() {
/* 69 */     return this.inverse;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\data\Int2IntMapBiMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
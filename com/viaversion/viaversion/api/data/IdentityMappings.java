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
/*    */ public class IdentityMappings
/*    */   implements Mappings
/*    */ {
/*    */   private final int size;
/*    */   private final int mappedSize;
/*    */   
/*    */   public IdentityMappings(int size, int mappedSize) {
/* 31 */     this.size = size;
/* 32 */     this.mappedSize = mappedSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getNewId(int id) {
/* 37 */     return (id >= 0 && id < this.size) ? id : -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setNewId(int id, int mappedId) {
/* 42 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 47 */     return this.size;
/*    */   }
/*    */ 
/*    */   
/*    */   public int mappedSize() {
/* 52 */     return this.mappedSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public Mappings inverse() {
/* 57 */     return new IdentityMappings(this.mappedSize, this.size);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\data\IdentityMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
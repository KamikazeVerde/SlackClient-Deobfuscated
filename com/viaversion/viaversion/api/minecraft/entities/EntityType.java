/*    */ package com.viaversion.viaversion.api.minecraft.entities;
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
/*    */ public interface EntityType
/*    */ {
/*    */   int getId();
/*    */   
/*    */   EntityType getParent();
/*    */   
/*    */   String name();
/*    */   
/*    */   String identifier();
/*    */   
/*    */   boolean isAbstractType();
/*    */   
/*    */   @Deprecated
/*    */   boolean is(EntityType... types) {
/* 68 */     for (EntityType type : types) {
/* 69 */       if (this == type) {
/* 70 */         return true;
/*    */       }
/*    */     } 
/* 73 */     return false;
/*    */   }
/*    */   
/*    */   default boolean is(EntityType type) {
/* 77 */     return (this == type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default boolean isOrHasParent(EntityType type) {
/* 87 */     EntityType parent = this;
/*    */     
/*    */     do {
/* 90 */       if (parent == type) {
/* 91 */         return true;
/*    */       }
/*    */       
/* 94 */       parent = parent.getParent();
/* 95 */     } while (parent != null);
/*    */     
/* 97 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\entities\EntityType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
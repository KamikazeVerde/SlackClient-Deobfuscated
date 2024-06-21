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
/*    */ 
/*    */ public enum Environment
/*    */ {
/* 27 */   NORMAL(0),
/* 28 */   NETHER(-1),
/* 29 */   END(1);
/*    */   
/*    */   private final int id;
/*    */   
/*    */   Environment(int id) {
/* 34 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int id() {
/* 38 */     return this.id;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public int getId() {
/* 43 */     return this.id;
/*    */   }
/*    */   
/*    */   public static Environment getEnvironmentById(int id) {
/* 47 */     switch (id)
/*    */     
/*    */     { default:
/* 50 */         return NETHER;
/*    */       case 0:
/* 52 */         return NORMAL;
/*    */       case 1:
/* 54 */         break; }  return END;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\Environment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
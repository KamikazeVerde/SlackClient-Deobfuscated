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
/*    */ public final class Quaternion
/*    */ {
/*    */   private final float x;
/*    */   private final float y;
/*    */   private final float z;
/*    */   private final float w;
/*    */   
/*    */   public Quaternion(float x, float y, float z, float w) {
/* 32 */     this.x = x;
/* 33 */     this.y = y;
/* 34 */     this.z = z;
/* 35 */     this.w = w;
/*    */   }
/*    */   
/*    */   public float x() {
/* 39 */     return this.x;
/*    */   }
/*    */   
/*    */   public float y() {
/* 43 */     return this.y;
/*    */   }
/*    */   
/*    */   public float z() {
/* 47 */     return this.z;
/*    */   }
/*    */   
/*    */   public float w() {
/* 51 */     return this.w;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\Quaternion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
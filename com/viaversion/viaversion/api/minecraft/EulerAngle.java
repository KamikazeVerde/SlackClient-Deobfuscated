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
/*    */ public class EulerAngle
/*    */ {
/*    */   private final float x;
/*    */   private final float y;
/*    */   private final float z;
/*    */   
/*    */   public EulerAngle(float x, float y, float z) {
/* 31 */     this.x = x;
/* 32 */     this.y = y;
/* 33 */     this.z = z;
/*    */   }
/*    */   
/*    */   public float x() {
/* 37 */     return this.x;
/*    */   }
/*    */   
/*    */   public float y() {
/* 41 */     return this.y;
/*    */   }
/*    */   
/*    */   public float z() {
/* 45 */     return this.z;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public float getX() {
/* 50 */     return this.x;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public float getY() {
/* 55 */     return this.y;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public float getZ() {
/* 60 */     return this.z;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\EulerAngle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
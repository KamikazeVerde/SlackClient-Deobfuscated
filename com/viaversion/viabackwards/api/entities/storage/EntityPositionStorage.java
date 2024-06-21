/*    */ package com.viaversion.viabackwards.api.entities.storage;
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
/*    */ public abstract class EntityPositionStorage
/*    */ {
/*    */   private double x;
/*    */   private double y;
/*    */   private double z;
/*    */   
/*    */   public double getX() {
/* 26 */     return this.x;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 30 */     return this.y;
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 34 */     return this.z;
/*    */   }
/*    */   
/*    */   public void setCoordinates(double x, double y, double z, boolean relative) {
/* 38 */     if (relative) {
/* 39 */       this.x += x;
/* 40 */       this.y += y;
/* 41 */       this.z += z;
/*    */     } else {
/* 43 */       this.x = x;
/* 44 */       this.y = y;
/* 45 */       this.z = z;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\entities\storage\EntityPositionStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
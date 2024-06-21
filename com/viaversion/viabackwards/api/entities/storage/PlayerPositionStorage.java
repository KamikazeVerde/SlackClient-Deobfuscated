/*    */ package com.viaversion.viabackwards.api.entities.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
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
/*    */ public abstract class PlayerPositionStorage
/*    */   implements StorableObject
/*    */ {
/*    */   private double x;
/*    */   private double y;
/*    */   private double z;
/*    */   
/*    */   public double getX() {
/* 33 */     return this.x;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 37 */     return this.y;
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 41 */     return this.z;
/*    */   }
/*    */   
/*    */   public void setX(double x) {
/* 45 */     this.x = x;
/*    */   }
/*    */   
/*    */   public void setY(double y) {
/* 49 */     this.y = y;
/*    */   }
/*    */   
/*    */   public void setZ(double z) {
/* 53 */     this.z = z;
/*    */   }
/*    */   
/*    */   public void setCoordinates(PacketWrapper wrapper, boolean relative) throws Exception {
/* 57 */     setCoordinates(((Double)wrapper.get((Type)Type.DOUBLE, 0)).doubleValue(), ((Double)wrapper.get((Type)Type.DOUBLE, 1)).doubleValue(), ((Double)wrapper.get((Type)Type.DOUBLE, 2)).doubleValue(), relative);
/*    */   }
/*    */   
/*    */   public void setCoordinates(double x, double y, double z, boolean relative) {
/* 61 */     if (relative) {
/* 62 */       this.x += x;
/* 63 */       this.y += y;
/* 64 */       this.z += z;
/*    */     } else {
/* 66 */       this.x = x;
/* 67 */       this.y = y;
/* 68 */       this.z = z;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\entities\storage\PlayerPositionStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
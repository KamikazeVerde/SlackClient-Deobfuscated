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
/*    */ public class Vector
/*    */ {
/*    */   private int blockX;
/*    */   private int blockY;
/*    */   private int blockZ;
/*    */   
/*    */   public Vector(int blockX, int blockY, int blockZ) {
/* 31 */     this.blockX = blockX;
/* 32 */     this.blockY = blockY;
/* 33 */     this.blockZ = blockZ;
/*    */   }
/*    */   
/*    */   public int blockX() {
/* 37 */     return this.blockX;
/*    */   }
/*    */   
/*    */   public int blockY() {
/* 41 */     return this.blockY;
/*    */   }
/*    */   
/*    */   public int blockZ() {
/* 45 */     return this.blockZ;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public int getBlockX() {
/* 50 */     return this.blockX;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public int getBlockY() {
/* 55 */     return this.blockY;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public int getBlockZ() {
/* 60 */     return this.blockZ;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public void setBlockX(int blockX) {
/* 65 */     this.blockX = blockX;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public void setBlockY(int blockY) {
/* 70 */     this.blockY = blockY;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public void setBlockZ(int blockZ) {
/* 75 */     this.blockZ = blockZ;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\Vector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
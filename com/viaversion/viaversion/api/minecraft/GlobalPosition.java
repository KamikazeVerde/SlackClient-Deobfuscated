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
/*    */ public final class GlobalPosition
/*    */   extends Position
/*    */ {
/*    */   private final String dimension;
/*    */   
/*    */   public GlobalPosition(String dimension, int x, int y, int z) {
/* 29 */     super(x, y, z);
/* 30 */     this.dimension = dimension;
/*    */   }
/*    */   
/*    */   public String dimension() {
/* 34 */     return this.dimension;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 39 */     if (this == o) return true; 
/* 40 */     if (o == null || getClass() != o.getClass()) return false; 
/* 41 */     GlobalPosition position = (GlobalPosition)o;
/* 42 */     if (this.x != position.x) return false; 
/* 43 */     if (this.y != position.y) return false; 
/* 44 */     if (this.z != position.z) return false; 
/* 45 */     return this.dimension.equals(position.dimension);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 50 */     int result = this.dimension.hashCode();
/* 51 */     result = 31 * result + this.x;
/* 52 */     result = 31 * result + this.y;
/* 53 */     result = 31 * result + this.z;
/* 54 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 59 */     return "GlobalPosition{dimension='" + this.dimension + '\'' + ", x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\GlobalPosition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
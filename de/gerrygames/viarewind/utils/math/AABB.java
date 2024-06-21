/*    */ package de.gerrygames.viarewind.utils.math;
/*    */ 
/*    */ public class AABB {
/*    */   Vector3d min;
/*    */   Vector3d max;
/*    */   
/*    */   public AABB(Vector3d min, Vector3d max) {
/*  8 */     this.min = min;
/*  9 */     this.max = max;
/*    */   }
/*    */   
/*    */   public Vector3d getMin() {
/* 13 */     return this.min;
/*    */   }
/*    */   
/*    */   public Vector3d getMax() {
/* 17 */     return this.max;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewin\\utils\math\AABB.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
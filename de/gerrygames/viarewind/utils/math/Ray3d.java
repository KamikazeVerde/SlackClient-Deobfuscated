/*    */ package de.gerrygames.viarewind.utils.math;
/*    */ 
/*    */ public class Ray3d {
/*    */   Vector3d start;
/*    */   Vector3d dir;
/*    */   
/*    */   public Ray3d(Vector3d start, Vector3d dir) {
/*  8 */     this.start = start;
/*  9 */     this.dir = dir;
/*    */   }
/*    */   
/*    */   public Vector3d getStart() {
/* 13 */     return this.start;
/*    */   }
/*    */   
/*    */   public Vector3d getDir() {
/* 17 */     return this.dir;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewin\\utils\math\Ray3d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
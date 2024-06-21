/*     */ package javax.vecmath;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Point3d
/*     */   extends Tuple3d
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 5718062286069042927L;
/*     */   
/*     */   public Point3d(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  54 */     super(paramDouble1, paramDouble2, paramDouble3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3d(double[] paramArrayOfdouble) {
/*  64 */     super(paramArrayOfdouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3d(Point3d paramPoint3d) {
/*  74 */     super(paramPoint3d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3d(Point3f paramPoint3f) {
/*  84 */     super(paramPoint3f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3d(Tuple3f paramTuple3f) {
/*  94 */     super(paramTuple3f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3d(Tuple3d paramTuple3d) {
/* 104 */     super(paramTuple3d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3d() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double distanceSquared(Point3d paramPoint3d) {
/* 126 */     double d1 = this.x - paramPoint3d.x;
/* 127 */     double d2 = this.y - paramPoint3d.y;
/* 128 */     double d3 = this.z - paramPoint3d.z;
/* 129 */     return d1 * d1 + d2 * d2 + d3 * d3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double distance(Point3d paramPoint3d) {
/* 142 */     double d1 = this.x - paramPoint3d.x;
/* 143 */     double d2 = this.y - paramPoint3d.y;
/* 144 */     double d3 = this.z - paramPoint3d.z;
/* 145 */     return Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double distanceL1(Point3d paramPoint3d) {
/* 157 */     return Math.abs(this.x - paramPoint3d.x) + Math.abs(this.y - paramPoint3d.y) + Math.abs(this.z - paramPoint3d.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double distanceLinf(Point3d paramPoint3d) {
/* 171 */     double d = Math.max(Math.abs(this.x - paramPoint3d.x), Math.abs(this.y - paramPoint3d.y));
/*     */     
/* 173 */     return Math.max(d, Math.abs(this.z - paramPoint3d.z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void project(Point4d paramPoint4d) {
/* 186 */     double d = 1.0D / paramPoint4d.w;
/* 187 */     this.x = paramPoint4d.x * d;
/* 188 */     this.y = paramPoint4d.y * d;
/* 189 */     this.z = paramPoint4d.z * d;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Point3d.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
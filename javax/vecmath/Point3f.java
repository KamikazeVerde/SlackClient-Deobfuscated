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
/*     */ 
/*     */ public class Point3f
/*     */   extends Tuple3f
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -8689337816398030143L;
/*     */   
/*     */   public Point3f(float paramFloat1, float paramFloat2, float paramFloat3) {
/*  55 */     super(paramFloat1, paramFloat2, paramFloat3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3f(float[] paramArrayOffloat) {
/*  65 */     super(paramArrayOffloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3f(Point3f paramPoint3f) {
/*  75 */     super(paramPoint3f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3f(Point3d paramPoint3d) {
/*  85 */     super(paramPoint3d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3f(Tuple3f paramTuple3f) {
/*  95 */     super(paramTuple3f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3f(Tuple3d paramTuple3d) {
/* 105 */     super(paramTuple3d);
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
/*     */   public Point3f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float distanceSquared(Point3f paramPoint3f) {
/* 128 */     float f1 = this.x - paramPoint3f.x;
/* 129 */     float f2 = this.y - paramPoint3f.y;
/* 130 */     float f3 = this.z - paramPoint3f.z;
/* 131 */     return f1 * f1 + f2 * f2 + f3 * f3;
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
/*     */   public final float distance(Point3f paramPoint3f) {
/* 144 */     float f1 = this.x - paramPoint3f.x;
/* 145 */     float f2 = this.y - paramPoint3f.y;
/* 146 */     float f3 = this.z - paramPoint3f.z;
/* 147 */     return (float)Math.sqrt((f1 * f1 + f2 * f2 + f3 * f3));
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
/*     */   public final float distanceL1(Point3f paramPoint3f) {
/* 160 */     return Math.abs(this.x - paramPoint3f.x) + Math.abs(this.y - paramPoint3f.y) + Math.abs(this.z - paramPoint3f.z);
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
/*     */   public final float distanceLinf(Point3f paramPoint3f) {
/* 174 */     float f = Math.max(Math.abs(this.x - paramPoint3f.x), Math.abs(this.y - paramPoint3f.y));
/* 175 */     return Math.max(f, Math.abs(this.z - paramPoint3f.z));
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
/*     */   public final void project(Point4f paramPoint4f) {
/* 189 */     float f = 1.0F / paramPoint4f.w;
/* 190 */     this.x = paramPoint4f.x * f;
/* 191 */     this.y = paramPoint4f.y * f;
/* 192 */     this.z = paramPoint4f.z * f;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Point3f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
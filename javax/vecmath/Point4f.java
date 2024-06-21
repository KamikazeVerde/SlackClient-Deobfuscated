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
/*     */ 
/*     */ public class Point4f
/*     */   extends Tuple4f
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 4643134103185764459L;
/*     */   
/*     */   public Point4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  56 */     super(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4f(float[] paramArrayOffloat) {
/*  66 */     super(paramArrayOffloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4f(Point4f paramPoint4f) {
/*  76 */     super(paramPoint4f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4f(Point4d paramPoint4d) {
/*  86 */     super(paramPoint4d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4f(Tuple4f paramTuple4f) {
/*  96 */     super(paramTuple4f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4f(Tuple4d paramTuple4d) {
/* 106 */     super(paramTuple4d);
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
/*     */   public Point4f(Tuple3f paramTuple3f) {
/* 120 */     super(paramTuple3f.x, paramTuple3f.y, paramTuple3f.z, 1.0F);
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
/*     */   public Point4f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3f paramTuple3f) {
/* 142 */     this.x = paramTuple3f.x;
/* 143 */     this.y = paramTuple3f.y;
/* 144 */     this.z = paramTuple3f.z;
/* 145 */     this.w = 1.0F;
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
/*     */   public final float distanceSquared(Point4f paramPoint4f) {
/* 158 */     float f1 = this.x - paramPoint4f.x;
/* 159 */     float f2 = this.y - paramPoint4f.y;
/* 160 */     float f3 = this.z - paramPoint4f.z;
/* 161 */     float f4 = this.w - paramPoint4f.w;
/* 162 */     return f1 * f1 + f2 * f2 + f3 * f3 + f4 * f4;
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
/*     */   public final float distance(Point4f paramPoint4f) {
/* 175 */     float f1 = this.x - paramPoint4f.x;
/* 176 */     float f2 = this.y - paramPoint4f.y;
/* 177 */     float f3 = this.z - paramPoint4f.z;
/* 178 */     float f4 = this.w - paramPoint4f.w;
/* 179 */     return (float)Math.sqrt((f1 * f1 + f2 * f2 + f3 * f3 + f4 * f4));
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
/*     */   public final float distanceL1(Point4f paramPoint4f) {
/* 192 */     return Math.abs(this.x - paramPoint4f.x) + Math.abs(this.y - paramPoint4f.y) + Math.abs(this.z - paramPoint4f.z) + Math.abs(this.w - paramPoint4f.w);
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
/*     */   public final float distanceLinf(Point4f paramPoint4f) {
/* 206 */     float f1 = Math.max(Math.abs(this.x - paramPoint4f.x), Math.abs(this.y - paramPoint4f.y));
/* 207 */     float f2 = Math.max(Math.abs(this.z - paramPoint4f.z), Math.abs(this.w - paramPoint4f.w));
/*     */     
/* 209 */     return Math.max(f1, f2);
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
/* 223 */     float f = 1.0F / paramPoint4f.w;
/* 224 */     paramPoint4f.x *= f;
/* 225 */     paramPoint4f.y *= f;
/* 226 */     paramPoint4f.z *= f;
/* 227 */     this.w = 1.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Point4f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
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
/*     */ public class Point4d
/*     */   extends Tuple4d
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 1733471895962736949L;
/*     */   
/*     */   public Point4d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  56 */     super(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4d(double[] paramArrayOfdouble) {
/*  66 */     super(paramArrayOfdouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4d(Point4d paramPoint4d) {
/*  76 */     super(paramPoint4d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4d(Point4f paramPoint4f) {
/*  86 */     super(paramPoint4f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4d(Tuple4f paramTuple4f) {
/*  96 */     super(paramTuple4f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4d(Tuple4d paramTuple4d) {
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
/*     */   public Point4d(Tuple3d paramTuple3d) {
/* 120 */     super(paramTuple3d.x, paramTuple3d.y, paramTuple3d.z, 1.0D);
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
/*     */   public Point4d() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3d paramTuple3d) {
/* 142 */     this.x = paramTuple3d.x;
/* 143 */     this.y = paramTuple3d.y;
/* 144 */     this.z = paramTuple3d.z;
/* 145 */     this.w = 1.0D;
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
/*     */   public final double distanceSquared(Point4d paramPoint4d) {
/* 158 */     double d1 = this.x - paramPoint4d.x;
/* 159 */     double d2 = this.y - paramPoint4d.y;
/* 160 */     double d3 = this.z - paramPoint4d.z;
/* 161 */     double d4 = this.w - paramPoint4d.w;
/* 162 */     return d1 * d1 + d2 * d2 + d3 * d3 + d4 * d4;
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
/*     */   public final double distance(Point4d paramPoint4d) {
/* 175 */     double d1 = this.x - paramPoint4d.x;
/* 176 */     double d2 = this.y - paramPoint4d.y;
/* 177 */     double d3 = this.z - paramPoint4d.z;
/* 178 */     double d4 = this.w - paramPoint4d.w;
/* 179 */     return Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3 + d4 * d4);
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
/*     */   public final double distanceL1(Point4d paramPoint4d) {
/* 191 */     return Math.abs(this.x - paramPoint4d.x) + Math.abs(this.y - paramPoint4d.y) + Math.abs(this.z - paramPoint4d.z) + Math.abs(this.w - paramPoint4d.w);
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
/*     */   public final double distanceLinf(Point4d paramPoint4d) {
/* 204 */     double d1 = Math.max(Math.abs(this.x - paramPoint4d.x), Math.abs(this.y - paramPoint4d.y));
/* 205 */     double d2 = Math.max(Math.abs(this.z - paramPoint4d.z), Math.abs(this.w - paramPoint4d.w));
/*     */     
/* 207 */     return Math.max(d1, d2);
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
/* 220 */     double d = 1.0D / paramPoint4d.w;
/* 221 */     paramPoint4d.x *= d;
/* 222 */     paramPoint4d.y *= d;
/* 223 */     paramPoint4d.z *= d;
/* 224 */     this.w = 1.0D;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Point4d.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
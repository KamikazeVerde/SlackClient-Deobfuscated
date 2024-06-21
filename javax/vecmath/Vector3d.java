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
/*     */ public class Vector3d
/*     */   extends Tuple3d
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 3761969948420550442L;
/*     */   
/*     */   public Vector3d(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  55 */     super(paramDouble1, paramDouble2, paramDouble3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3d(double[] paramArrayOfdouble) {
/*  65 */     super(paramArrayOfdouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3d(Vector3d paramVector3d) {
/*  75 */     super(paramVector3d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3d(Vector3f paramVector3f) {
/*  85 */     super(paramVector3f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3d(Tuple3f paramTuple3f) {
/*  95 */     super(paramTuple3f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3d(Tuple3d paramTuple3d) {
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
/*     */   public Vector3d() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void cross(Vector3d paramVector3d1, Vector3d paramVector3d2) {
/* 127 */     double d1 = paramVector3d1.y * paramVector3d2.z - paramVector3d1.z * paramVector3d2.y;
/* 128 */     double d2 = paramVector3d2.x * paramVector3d1.z - paramVector3d2.z * paramVector3d1.x;
/* 129 */     this.z = paramVector3d1.x * paramVector3d2.y - paramVector3d1.y * paramVector3d2.x;
/* 130 */     this.x = d1;
/* 131 */     this.y = d2;
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
/*     */   public final void normalize(Vector3d paramVector3d) {
/* 143 */     double d = 1.0D / Math.sqrt(paramVector3d.x * paramVector3d.x + paramVector3d.y * paramVector3d.y + paramVector3d.z * paramVector3d.z);
/* 144 */     paramVector3d.x *= d;
/* 145 */     paramVector3d.y *= d;
/* 146 */     paramVector3d.z *= d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize() {
/* 157 */     double d = 1.0D / Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/* 158 */     this.x *= d;
/* 159 */     this.y *= d;
/* 160 */     this.z *= d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double dot(Vector3d paramVector3d) {
/* 171 */     return this.x * paramVector3d.x + this.y * paramVector3d.y + this.z * paramVector3d.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double lengthSquared() {
/* 181 */     return this.x * this.x + this.y * this.y + this.z * this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double length() {
/* 191 */     return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
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
/*     */   public final double angle(Vector3d paramVector3d) {
/* 203 */     double d = dot(paramVector3d) / length() * paramVector3d.length();
/* 204 */     if (d < -1.0D) d = -1.0D; 
/* 205 */     if (d > 1.0D) d = 1.0D; 
/* 206 */     return Math.acos(d);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Vector3d.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
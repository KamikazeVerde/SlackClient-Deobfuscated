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
/*     */ public class Vector4d
/*     */   extends Tuple4d
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 3938123424117448700L;
/*     */   
/*     */   public Vector4d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  55 */     super(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4d(double[] paramArrayOfdouble) {
/*  65 */     super(paramArrayOfdouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4d(Vector4d paramVector4d) {
/*  74 */     super(paramVector4d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4d(Vector4f paramVector4f) {
/*  83 */     super(paramVector4f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4d(Tuple4f paramTuple4f) {
/*  92 */     super(paramTuple4f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4d(Tuple4d paramTuple4d) {
/* 101 */     super(paramTuple4d);
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
/*     */   public Vector4d(Tuple3d paramTuple3d) {
/* 115 */     super(paramTuple3d.x, paramTuple3d.y, paramTuple3d.z, 0.0D);
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
/*     */   public Vector4d() {}
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
/* 137 */     this.x = paramTuple3d.x;
/* 138 */     this.y = paramTuple3d.y;
/* 139 */     this.z = paramTuple3d.z;
/* 140 */     this.w = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double length() {
/* 150 */     return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double lengthSquared() {
/* 161 */     return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
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
/*     */   public final double dot(Vector4d paramVector4d) {
/* 173 */     return this.x * paramVector4d.x + this.y * paramVector4d.y + this.z * paramVector4d.z + this.w * paramVector4d.w;
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
/*     */   public final void normalize(Vector4d paramVector4d) {
/* 185 */     double d = 1.0D / Math.sqrt(paramVector4d.x * paramVector4d.x + paramVector4d.y * paramVector4d.y + paramVector4d.z * paramVector4d.z + paramVector4d.w * paramVector4d.w);
/* 186 */     paramVector4d.x *= d;
/* 187 */     paramVector4d.y *= d;
/* 188 */     paramVector4d.z *= d;
/* 189 */     paramVector4d.w *= d;
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
/* 200 */     double d = 1.0D / Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
/*     */     
/* 202 */     this.x *= d;
/* 203 */     this.y *= d;
/* 204 */     this.z *= d;
/* 205 */     this.w *= d;
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
/*     */   public final double angle(Vector4d paramVector4d) {
/* 218 */     double d = dot(paramVector4d) / length() * paramVector4d.length();
/* 219 */     if (d < -1.0D) d = -1.0D; 
/* 220 */     if (d > 1.0D) d = 1.0D; 
/* 221 */     return Math.acos(d);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Vector4d.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
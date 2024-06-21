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
/*     */ public class Vector4f
/*     */   extends Tuple4f
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 8749319902347760659L;
/*     */   
/*     */   public Vector4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  55 */     super(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f(float[] paramArrayOffloat) {
/*  65 */     super(paramArrayOffloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f(Vector4f paramVector4f) {
/*  75 */     super(paramVector4f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f(Vector4d paramVector4d) {
/*  85 */     super(paramVector4d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f(Tuple4f paramTuple4f) {
/*  95 */     super(paramTuple4f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f(Tuple4d paramTuple4d) {
/* 105 */     super(paramTuple4d);
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
/*     */   public Vector4f(Tuple3f paramTuple3f) {
/* 119 */     super(paramTuple3f.x, paramTuple3f.y, paramTuple3f.z, 0.0F);
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
/*     */   public Vector4f() {}
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
/* 141 */     this.x = paramTuple3f.x;
/* 142 */     this.y = paramTuple3f.y;
/* 143 */     this.z = paramTuple3f.z;
/* 144 */     this.w = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float length() {
/* 154 */     return (float)Math.sqrt((this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float lengthSquared() {
/* 165 */     return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float dot(Vector4f paramVector4f) {
/* 176 */     return this.x * paramVector4f.x + this.y * paramVector4f.y + this.z * paramVector4f.z + this.w * paramVector4f.w;
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
/*     */   public final void normalize(Vector4f paramVector4f) {
/* 188 */     float f = (float)(1.0D / Math.sqrt((paramVector4f.x * paramVector4f.x + paramVector4f.y * paramVector4f.y + paramVector4f.z * paramVector4f.z + paramVector4f.w * paramVector4f.w)));
/*     */     
/* 190 */     paramVector4f.x *= f;
/* 191 */     paramVector4f.y *= f;
/* 192 */     paramVector4f.z *= f;
/* 193 */     paramVector4f.w *= f;
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
/* 204 */     float f = (float)(1.0D / Math.sqrt((this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w)));
/*     */     
/* 206 */     this.x *= f;
/* 207 */     this.y *= f;
/* 208 */     this.z *= f;
/* 209 */     this.w *= f;
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
/*     */   public final float angle(Vector4f paramVector4f) {
/* 222 */     double d = (dot(paramVector4f) / length() * paramVector4f.length());
/* 223 */     if (d < -1.0D) d = -1.0D; 
/* 224 */     if (d > 1.0D) d = 1.0D; 
/* 225 */     return (float)Math.acos(d);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Vector4f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
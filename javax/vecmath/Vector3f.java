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
/*     */ public class Vector3f
/*     */   extends Tuple3f
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -7031930069184524614L;
/*     */   
/*     */   public Vector3f(float paramFloat1, float paramFloat2, float paramFloat3) {
/*  55 */     super(paramFloat1, paramFloat2, paramFloat3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f(float[] paramArrayOffloat) {
/*  65 */     super(paramArrayOffloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f(Vector3f paramVector3f) {
/*  75 */     super(paramVector3f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f(Vector3d paramVector3d) {
/*  85 */     super(paramVector3d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f(Tuple3f paramTuple3f) {
/*  94 */     super(paramTuple3f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f(Tuple3d paramTuple3d) {
/* 103 */     super(paramTuple3d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float lengthSquared() {
/* 122 */     return this.x * this.x + this.y * this.y + this.z * this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float length() {
/* 131 */     return (float)Math.sqrt((this.x * this.x + this.y * this.y + this.z * this.z));
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
/*     */   public final void cross(Vector3f paramVector3f1, Vector3f paramVector3f2) {
/* 145 */     float f1 = paramVector3f1.y * paramVector3f2.z - paramVector3f1.z * paramVector3f2.y;
/* 146 */     float f2 = paramVector3f2.x * paramVector3f1.z - paramVector3f2.z * paramVector3f1.x;
/* 147 */     this.z = paramVector3f1.x * paramVector3f2.y - paramVector3f1.y * paramVector3f2.x;
/* 148 */     this.x = f1;
/* 149 */     this.y = f2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float dot(Vector3f paramVector3f) {
/* 159 */     return this.x * paramVector3f.x + this.y * paramVector3f.y + this.z * paramVector3f.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize(Vector3f paramVector3f) {
/* 170 */     float f = (float)(1.0D / Math.sqrt((paramVector3f.x * paramVector3f.x + paramVector3f.y * paramVector3f.y + paramVector3f.z * paramVector3f.z)));
/* 171 */     paramVector3f.x *= f;
/* 172 */     paramVector3f.y *= f;
/* 173 */     paramVector3f.z *= f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize() {
/* 183 */     float f = (float)(1.0D / Math.sqrt((this.x * this.x + this.y * this.y + this.z * this.z)));
/*     */     
/* 185 */     this.x *= f;
/* 186 */     this.y *= f;
/* 187 */     this.z *= f;
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
/*     */   public final float angle(Vector3f paramVector3f) {
/* 199 */     double d = (dot(paramVector3f) / length() * paramVector3f.length());
/* 200 */     if (d < -1.0D) d = -1.0D; 
/* 201 */     if (d > 1.0D) d = 1.0D; 
/* 202 */     return (float)Math.acos(d);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Vector3f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
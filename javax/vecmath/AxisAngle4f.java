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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AxisAngle4f
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -163246355858070601L;
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */   public float angle;
/*     */   static final double EPS = 1.0E-6D;
/*     */   
/*     */   public AxisAngle4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  78 */     this.x = paramFloat1;
/*  79 */     this.y = paramFloat2;
/*  80 */     this.z = paramFloat3;
/*  81 */     this.angle = paramFloat4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4f(float[] paramArrayOffloat) {
/*  91 */     this.x = paramArrayOffloat[0];
/*  92 */     this.y = paramArrayOffloat[1];
/*  93 */     this.z = paramArrayOffloat[2];
/*  94 */     this.angle = paramArrayOffloat[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4f(AxisAngle4f paramAxisAngle4f) {
/* 105 */     this.x = paramAxisAngle4f.x;
/* 106 */     this.y = paramAxisAngle4f.y;
/* 107 */     this.z = paramAxisAngle4f.z;
/* 108 */     this.angle = paramAxisAngle4f.angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4f(AxisAngle4d paramAxisAngle4d) {
/* 118 */     this.x = (float)paramAxisAngle4d.x;
/* 119 */     this.y = (float)paramAxisAngle4d.y;
/* 120 */     this.z = (float)paramAxisAngle4d.z;
/* 121 */     this.angle = (float)paramAxisAngle4d.angle;
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
/*     */   public AxisAngle4f(Vector3f paramVector3f, float paramFloat) {
/* 134 */     this.x = paramVector3f.x;
/* 135 */     this.y = paramVector3f.y;
/* 136 */     this.z = paramVector3f.z;
/* 137 */     this.angle = paramFloat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4f() {
/* 146 */     this.x = 0.0F;
/* 147 */     this.y = 0.0F;
/* 148 */     this.z = 1.0F;
/* 149 */     this.angle = 0.0F;
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
/*     */   public final void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 162 */     this.x = paramFloat1;
/* 163 */     this.y = paramFloat2;
/* 164 */     this.z = paramFloat3;
/* 165 */     this.angle = paramFloat4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(float[] paramArrayOffloat) {
/* 176 */     this.x = paramArrayOffloat[0];
/* 177 */     this.y = paramArrayOffloat[1];
/* 178 */     this.z = paramArrayOffloat[2];
/* 179 */     this.angle = paramArrayOffloat[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(AxisAngle4f paramAxisAngle4f) {
/* 189 */     this.x = paramAxisAngle4f.x;
/* 190 */     this.y = paramAxisAngle4f.y;
/* 191 */     this.z = paramAxisAngle4f.z;
/* 192 */     this.angle = paramAxisAngle4f.angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(AxisAngle4d paramAxisAngle4d) {
/* 202 */     this.x = (float)paramAxisAngle4d.x;
/* 203 */     this.y = (float)paramAxisAngle4d.y;
/* 204 */     this.z = (float)paramAxisAngle4d.z;
/* 205 */     this.angle = (float)paramAxisAngle4d.angle;
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
/*     */   public final void set(Vector3f paramVector3f, float paramFloat) {
/* 218 */     this.x = paramVector3f.x;
/* 219 */     this.y = paramVector3f.y;
/* 220 */     this.z = paramVector3f.z;
/* 221 */     this.angle = paramFloat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(float[] paramArrayOffloat) {
/* 231 */     paramArrayOffloat[0] = this.x;
/* 232 */     paramArrayOffloat[1] = this.y;
/* 233 */     paramArrayOffloat[2] = this.z;
/* 234 */     paramArrayOffloat[3] = this.angle;
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
/*     */   public final void set(Quat4f paramQuat4f) {
/* 247 */     double d = (paramQuat4f.x * paramQuat4f.x + paramQuat4f.y * paramQuat4f.y + paramQuat4f.z * paramQuat4f.z);
/*     */     
/* 249 */     if (d > 1.0E-6D) {
/* 250 */       d = Math.sqrt(d);
/* 251 */       double d1 = 1.0D / d;
/*     */       
/* 253 */       this.x = (float)(paramQuat4f.x * d1);
/* 254 */       this.y = (float)(paramQuat4f.y * d1);
/* 255 */       this.z = (float)(paramQuat4f.z * d1);
/* 256 */       this.angle = (float)(2.0D * Math.atan2(d, paramQuat4f.w));
/*     */     } else {
/* 258 */       this.x = 0.0F;
/* 259 */       this.y = 1.0F;
/* 260 */       this.z = 0.0F;
/* 261 */       this.angle = 0.0F;
/*     */     } 
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
/*     */   public final void set(Quat4d paramQuat4d) {
/* 275 */     double d = paramQuat4d.x * paramQuat4d.x + paramQuat4d.y * paramQuat4d.y + paramQuat4d.z * paramQuat4d.z;
/*     */     
/* 277 */     if (d > 1.0E-6D) {
/* 278 */       d = Math.sqrt(d);
/* 279 */       double d1 = 1.0D / d;
/*     */       
/* 281 */       this.x = (float)(paramQuat4d.x * d1);
/* 282 */       this.y = (float)(paramQuat4d.y * d1);
/* 283 */       this.z = (float)(paramQuat4d.z * d1);
/* 284 */       this.angle = (float)(2.0D * Math.atan2(d, paramQuat4d.w));
/*     */     } else {
/* 286 */       this.x = 0.0F;
/* 287 */       this.y = 1.0F;
/* 288 */       this.z = 0.0F;
/* 289 */       this.angle = 0.0F;
/*     */     } 
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
/*     */   public final void set(Matrix4f paramMatrix4f) {
/* 303 */     Matrix3f matrix3f = new Matrix3f();
/*     */     
/* 305 */     paramMatrix4f.get(matrix3f);
/*     */     
/* 307 */     this.x = matrix3f.m21 - matrix3f.m12;
/* 308 */     this.y = matrix3f.m02 - matrix3f.m20;
/* 309 */     this.z = matrix3f.m10 - matrix3f.m01;
/* 310 */     double d = (this.x * this.x + this.y * this.y + this.z * this.z);
/*     */     
/* 312 */     if (d > 1.0E-6D) {
/* 313 */       d = Math.sqrt(d);
/* 314 */       double d1 = 0.5D * d;
/* 315 */       double d2 = 0.5D * ((matrix3f.m00 + matrix3f.m11 + matrix3f.m22) - 1.0D);
/*     */       
/* 317 */       this.angle = (float)Math.atan2(d1, d2);
/* 318 */       double d3 = 1.0D / d;
/* 319 */       this.x = (float)(this.x * d3);
/* 320 */       this.y = (float)(this.y * d3);
/* 321 */       this.z = (float)(this.z * d3);
/*     */     } else {
/* 323 */       this.x = 0.0F;
/* 324 */       this.y = 1.0F;
/* 325 */       this.z = 0.0F;
/* 326 */       this.angle = 0.0F;
/*     */     } 
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
/*     */   
/*     */   public final void set(Matrix4d paramMatrix4d) {
/* 342 */     Matrix3d matrix3d = new Matrix3d();
/*     */     
/* 344 */     paramMatrix4d.get(matrix3d);
/*     */ 
/*     */     
/* 347 */     this.x = (float)(matrix3d.m21 - matrix3d.m12);
/* 348 */     this.y = (float)(matrix3d.m02 - matrix3d.m20);
/* 349 */     this.z = (float)(matrix3d.m10 - matrix3d.m01);
/* 350 */     double d = (this.x * this.x + this.y * this.y + this.z * this.z);
/*     */     
/* 352 */     if (d > 1.0E-6D) {
/* 353 */       d = Math.sqrt(d);
/* 354 */       double d1 = 0.5D * d;
/* 355 */       double d2 = 0.5D * (matrix3d.m00 + matrix3d.m11 + matrix3d.m22 - 1.0D);
/* 356 */       this.angle = (float)Math.atan2(d1, d2);
/*     */       
/* 358 */       double d3 = 1.0D / d;
/* 359 */       this.x = (float)(this.x * d3);
/* 360 */       this.y = (float)(this.y * d3);
/* 361 */       this.z = (float)(this.z * d3);
/*     */     } else {
/* 363 */       this.x = 0.0F;
/* 364 */       this.y = 1.0F;
/* 365 */       this.z = 0.0F;
/* 366 */       this.angle = 0.0F;
/*     */     } 
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
/*     */   public final void set(Matrix3f paramMatrix3f) {
/* 381 */     this.x = paramMatrix3f.m21 - paramMatrix3f.m12;
/* 382 */     this.y = paramMatrix3f.m02 - paramMatrix3f.m20;
/* 383 */     this.z = paramMatrix3f.m10 - paramMatrix3f.m01;
/* 384 */     double d = (this.x * this.x + this.y * this.y + this.z * this.z);
/* 385 */     if (d > 1.0E-6D) {
/* 386 */       d = Math.sqrt(d);
/* 387 */       double d1 = 0.5D * d;
/* 388 */       double d2 = 0.5D * ((paramMatrix3f.m00 + paramMatrix3f.m11 + paramMatrix3f.m22) - 1.0D);
/*     */       
/* 390 */       this.angle = (float)Math.atan2(d1, d2);
/*     */       
/* 392 */       double d3 = 1.0D / d;
/* 393 */       this.x = (float)(this.x * d3);
/* 394 */       this.y = (float)(this.y * d3);
/* 395 */       this.z = (float)(this.z * d3);
/*     */     } else {
/* 397 */       this.x = 0.0F;
/* 398 */       this.y = 1.0F;
/* 399 */       this.z = 0.0F;
/* 400 */       this.angle = 0.0F;
/*     */     } 
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
/*     */   
/*     */   public final void set(Matrix3d paramMatrix3d) {
/* 416 */     this.x = (float)(paramMatrix3d.m21 - paramMatrix3d.m12);
/* 417 */     this.y = (float)(paramMatrix3d.m02 - paramMatrix3d.m20);
/* 418 */     this.z = (float)(paramMatrix3d.m10 - paramMatrix3d.m01);
/* 419 */     double d = (this.x * this.x + this.y * this.y + this.z * this.z);
/*     */     
/* 421 */     if (d > 1.0E-6D) {
/* 422 */       d = Math.sqrt(d);
/* 423 */       double d1 = 0.5D * d;
/* 424 */       double d2 = 0.5D * (paramMatrix3d.m00 + paramMatrix3d.m11 + paramMatrix3d.m22 - 1.0D);
/*     */       
/* 426 */       this.angle = (float)Math.atan2(d1, d2);
/*     */       
/* 428 */       double d3 = 1.0D / d;
/* 429 */       this.x = (float)(this.x * d3);
/* 430 */       this.y = (float)(this.y * d3);
/* 431 */       this.z = (float)(this.z * d3);
/*     */     } else {
/* 433 */       this.x = 0.0F;
/* 434 */       this.y = 1.0F;
/* 435 */       this.z = 0.0F;
/* 436 */       this.angle = 0.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 447 */     return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.angle + ")";
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
/*     */   public boolean equals(AxisAngle4f paramAxisAngle4f) {
/*     */     try {
/* 460 */       return (this.x == paramAxisAngle4f.x && this.y == paramAxisAngle4f.y && this.z == paramAxisAngle4f.z && this.angle == paramAxisAngle4f.angle);
/*     */     } catch (NullPointerException nullPointerException) {
/*     */       
/* 463 */       return false;
/*     */     } 
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
/*     */   public boolean equals(Object paramObject) {
/*     */     
/* 477 */     try { AxisAngle4f axisAngle4f = (AxisAngle4f)paramObject;
/* 478 */       return (this.x == axisAngle4f.x && this.y == axisAngle4f.y && this.z == axisAngle4f.z && this.angle == axisAngle4f.angle); }
/*     */     catch (NullPointerException nullPointerException)
/*     */     
/* 481 */     { return false; }
/* 482 */     catch (ClassCastException classCastException) { return false; }
/*     */   
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
/*     */ 
/*     */   
/*     */   public boolean epsilonEquals(AxisAngle4f paramAxisAngle4f, float paramFloat) {
/* 499 */     float f = this.x - paramAxisAngle4f.x;
/* 500 */     if (((f < 0.0F) ? -f : f) > paramFloat) return false;
/*     */     
/* 502 */     f = this.y - paramAxisAngle4f.y;
/* 503 */     if (((f < 0.0F) ? -f : f) > paramFloat) return false;
/*     */     
/* 505 */     f = this.z - paramAxisAngle4f.z;
/* 506 */     if (((f < 0.0F) ? -f : f) > paramFloat) return false;
/*     */     
/* 508 */     f = this.angle - paramAxisAngle4f.angle;
/* 509 */     if (((f < 0.0F) ? -f : f) > paramFloat) return false;
/*     */     
/* 511 */     return true;
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
/*     */   public int hashCode() {
/* 525 */     long l = 1L;
/* 526 */     l = 31L * l + VecMathUtil.floatToIntBits(this.x);
/* 527 */     l = 31L * l + VecMathUtil.floatToIntBits(this.y);
/* 528 */     l = 31L * l + VecMathUtil.floatToIntBits(this.z);
/* 529 */     l = 31L * l + VecMathUtil.floatToIntBits(this.angle);
/* 530 */     return (int)(l ^ l >> 32L);
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
/*     */   public Object clone() {
/*     */     try {
/* 544 */       return super.clone();
/* 545 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 547 */       throw new InternalError();
/*     */     } 
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
/*     */   public final float getAngle() {
/* 561 */     return this.angle;
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
/*     */   public final void setAngle(float paramFloat) {
/* 574 */     this.angle = paramFloat;
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
/*     */   public final float getX() {
/* 586 */     return this.x;
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
/*     */   public final void setX(float paramFloat) {
/* 598 */     this.x = paramFloat;
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
/*     */   public final float getY() {
/* 610 */     return this.y;
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
/*     */   public final void setY(float paramFloat) {
/* 622 */     this.y = paramFloat;
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
/*     */   public final float getZ() {
/* 634 */     return this.z;
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
/*     */   public final void setZ(float paramFloat) {
/* 646 */     this.z = paramFloat;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\AxisAngle4f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
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
/*     */ 
/*     */ public class AxisAngle4d
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 3644296204459140589L;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   public double angle;
/*     */   static final double EPS = 1.0E-12D;
/*     */   
/*     */   public AxisAngle4d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  79 */     this.x = paramDouble1;
/*  80 */     this.y = paramDouble2;
/*  81 */     this.z = paramDouble3;
/*  82 */     this.angle = paramDouble4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4d(double[] paramArrayOfdouble) {
/*  93 */     this.x = paramArrayOfdouble[0];
/*  94 */     this.y = paramArrayOfdouble[1];
/*  95 */     this.z = paramArrayOfdouble[2];
/*  96 */     this.angle = paramArrayOfdouble[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4d(AxisAngle4d paramAxisAngle4d) {
/* 104 */     this.x = paramAxisAngle4d.x;
/* 105 */     this.y = paramAxisAngle4d.y;
/* 106 */     this.z = paramAxisAngle4d.z;
/* 107 */     this.angle = paramAxisAngle4d.angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4d(AxisAngle4f paramAxisAngle4f) {
/* 118 */     this.x = paramAxisAngle4f.x;
/* 119 */     this.y = paramAxisAngle4f.y;
/* 120 */     this.z = paramAxisAngle4f.z;
/* 121 */     this.angle = paramAxisAngle4f.angle;
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
/*     */   public AxisAngle4d(Vector3d paramVector3d, double paramDouble) {
/* 134 */     this.x = paramVector3d.x;
/* 135 */     this.y = paramVector3d.y;
/* 136 */     this.z = paramVector3d.z;
/* 137 */     this.angle = paramDouble;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4d() {
/* 146 */     this.x = 0.0D;
/* 147 */     this.y = 0.0D;
/* 148 */     this.z = 1.0D;
/* 149 */     this.angle = 0.0D;
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
/*     */   public final void set(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 162 */     this.x = paramDouble1;
/* 163 */     this.y = paramDouble2;
/* 164 */     this.z = paramDouble3;
/* 165 */     this.angle = paramDouble4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double[] paramArrayOfdouble) {
/* 175 */     this.x = paramArrayOfdouble[0];
/* 176 */     this.y = paramArrayOfdouble[1];
/* 177 */     this.z = paramArrayOfdouble[2];
/* 178 */     this.angle = paramArrayOfdouble[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(AxisAngle4d paramAxisAngle4d) {
/* 188 */     this.x = paramAxisAngle4d.x;
/* 189 */     this.y = paramAxisAngle4d.y;
/* 190 */     this.z = paramAxisAngle4d.z;
/* 191 */     this.angle = paramAxisAngle4d.angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(AxisAngle4f paramAxisAngle4f) {
/* 201 */     this.x = paramAxisAngle4f.x;
/* 202 */     this.y = paramAxisAngle4f.y;
/* 203 */     this.z = paramAxisAngle4f.z;
/* 204 */     this.angle = paramAxisAngle4f.angle;
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
/*     */   public final void set(Vector3d paramVector3d, double paramDouble) {
/* 217 */     this.x = paramVector3d.x;
/* 218 */     this.y = paramVector3d.y;
/* 219 */     this.z = paramVector3d.z;
/* 220 */     this.angle = paramDouble;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(double[] paramArrayOfdouble) {
/* 231 */     paramArrayOfdouble[0] = this.x;
/* 232 */     paramArrayOfdouble[1] = this.y;
/* 233 */     paramArrayOfdouble[2] = this.z;
/* 234 */     paramArrayOfdouble[3] = this.angle;
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
/*     */   public final void set(Matrix4f paramMatrix4f) {
/* 248 */     Matrix3d matrix3d = new Matrix3d();
/*     */     
/* 250 */     paramMatrix4f.get(matrix3d);
/*     */     
/* 252 */     this.x = (float)(matrix3d.m21 - matrix3d.m12);
/* 253 */     this.y = (float)(matrix3d.m02 - matrix3d.m20);
/* 254 */     this.z = (float)(matrix3d.m10 - matrix3d.m01);
/* 255 */     double d = this.x * this.x + this.y * this.y + this.z * this.z;
/*     */     
/* 257 */     if (d > 1.0E-12D) {
/* 258 */       d = Math.sqrt(d);
/* 259 */       double d1 = 0.5D * d;
/* 260 */       double d2 = 0.5D * (matrix3d.m00 + matrix3d.m11 + matrix3d.m22 - 1.0D);
/*     */       
/* 262 */       this.angle = (float)Math.atan2(d1, d2);
/*     */       
/* 264 */       double d3 = 1.0D / d;
/* 265 */       this.x *= d3;
/* 266 */       this.y *= d3;
/* 267 */       this.z *= d3;
/*     */     } else {
/* 269 */       this.x = 0.0D;
/* 270 */       this.y = 1.0D;
/* 271 */       this.z = 0.0D;
/* 272 */       this.angle = 0.0D;
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
/*     */   public final void set(Matrix4d paramMatrix4d) {
/* 287 */     Matrix3d matrix3d = new Matrix3d();
/*     */     
/* 289 */     paramMatrix4d.get(matrix3d);
/*     */     
/* 291 */     this.x = (float)(matrix3d.m21 - matrix3d.m12);
/* 292 */     this.y = (float)(matrix3d.m02 - matrix3d.m20);
/* 293 */     this.z = (float)(matrix3d.m10 - matrix3d.m01);
/*     */     
/* 295 */     double d = this.x * this.x + this.y * this.y + this.z * this.z;
/*     */     
/* 297 */     if (d > 1.0E-12D) {
/* 298 */       d = Math.sqrt(d);
/*     */       
/* 300 */       double d1 = 0.5D * d;
/* 301 */       double d2 = 0.5D * (matrix3d.m00 + matrix3d.m11 + matrix3d.m22 - 1.0D);
/* 302 */       this.angle = (float)Math.atan2(d1, d2);
/*     */       
/* 304 */       double d3 = 1.0D / d;
/* 305 */       this.x *= d3;
/* 306 */       this.y *= d3;
/* 307 */       this.z *= d3;
/*     */     } else {
/* 309 */       this.x = 0.0D;
/* 310 */       this.y = 1.0D;
/* 311 */       this.z = 0.0D;
/* 312 */       this.angle = 0.0D;
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
/*     */   public final void set(Matrix3f paramMatrix3f) {
/* 326 */     this.x = (paramMatrix3f.m21 - paramMatrix3f.m12);
/* 327 */     this.y = (paramMatrix3f.m02 - paramMatrix3f.m20);
/* 328 */     this.z = (paramMatrix3f.m10 - paramMatrix3f.m01);
/* 329 */     double d = this.x * this.x + this.y * this.y + this.z * this.z;
/*     */     
/* 331 */     if (d > 1.0E-12D) {
/* 332 */       d = Math.sqrt(d);
/*     */       
/* 334 */       double d1 = 0.5D * d;
/* 335 */       double d2 = 0.5D * ((paramMatrix3f.m00 + paramMatrix3f.m11 + paramMatrix3f.m22) - 1.0D);
/* 336 */       this.angle = (float)Math.atan2(d1, d2);
/*     */       
/* 338 */       double d3 = 1.0D / d;
/* 339 */       this.x *= d3;
/* 340 */       this.y *= d3;
/* 341 */       this.z *= d3;
/*     */     } else {
/* 343 */       this.x = 0.0D;
/* 344 */       this.y = 1.0D;
/* 345 */       this.z = 0.0D;
/* 346 */       this.angle = 0.0D;
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
/*     */   public final void set(Matrix3d paramMatrix3d) {
/* 360 */     this.x = (float)(paramMatrix3d.m21 - paramMatrix3d.m12);
/* 361 */     this.y = (float)(paramMatrix3d.m02 - paramMatrix3d.m20);
/* 362 */     this.z = (float)(paramMatrix3d.m10 - paramMatrix3d.m01);
/*     */     
/* 364 */     double d = this.x * this.x + this.y * this.y + this.z * this.z;
/*     */     
/* 366 */     if (d > 1.0E-12D) {
/* 367 */       d = Math.sqrt(d);
/*     */       
/* 369 */       double d1 = 0.5D * d;
/* 370 */       double d2 = 0.5D * (paramMatrix3d.m00 + paramMatrix3d.m11 + paramMatrix3d.m22 - 1.0D);
/*     */       
/* 372 */       this.angle = (float)Math.atan2(d1, d2);
/*     */       
/* 374 */       double d3 = 1.0D / d;
/* 375 */       this.x *= d3;
/* 376 */       this.y *= d3;
/* 377 */       this.z *= d3;
/*     */     } else {
/* 379 */       this.x = 0.0D;
/* 380 */       this.y = 1.0D;
/* 381 */       this.z = 0.0D;
/* 382 */       this.angle = 0.0D;
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
/*     */   public final void set(Quat4f paramQuat4f) {
/* 398 */     double d = (paramQuat4f.x * paramQuat4f.x + paramQuat4f.y * paramQuat4f.y + paramQuat4f.z * paramQuat4f.z);
/*     */     
/* 400 */     if (d > 1.0E-12D) {
/* 401 */       d = Math.sqrt(d);
/* 402 */       double d1 = 1.0D / d;
/*     */       
/* 404 */       this.x = paramQuat4f.x * d1;
/* 405 */       this.y = paramQuat4f.y * d1;
/* 406 */       this.z = paramQuat4f.z * d1;
/* 407 */       this.angle = 2.0D * Math.atan2(d, paramQuat4f.w);
/*     */     } else {
/* 409 */       this.x = 0.0D;
/* 410 */       this.y = 1.0D;
/* 411 */       this.z = 0.0D;
/* 412 */       this.angle = 0.0D;
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
/* 426 */     double d = paramQuat4d.x * paramQuat4d.x + paramQuat4d.y * paramQuat4d.y + paramQuat4d.z * paramQuat4d.z;
/*     */     
/* 428 */     if (d > 1.0E-12D) {
/* 429 */       d = Math.sqrt(d);
/* 430 */       double d1 = 1.0D / d;
/*     */       
/* 432 */       this.x = paramQuat4d.x * d1;
/* 433 */       this.y = paramQuat4d.y * d1;
/* 434 */       this.z = paramQuat4d.z * d1;
/* 435 */       this.angle = 2.0D * Math.atan2(d, paramQuat4d.w);
/*     */     } else {
/* 437 */       this.x = 0.0D;
/* 438 */       this.y = 1.0D;
/* 439 */       this.z = 0.0D;
/* 440 */       this.angle = 0.0D;
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
/* 451 */     return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.angle + ")";
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
/*     */   public boolean equals(AxisAngle4d paramAxisAngle4d) {
/*     */     try {
/* 464 */       return (this.x == paramAxisAngle4d.x && this.y == paramAxisAngle4d.y && this.z == paramAxisAngle4d.z && this.angle == paramAxisAngle4d.angle);
/*     */     } catch (NullPointerException nullPointerException) {
/*     */       
/* 467 */       return false;
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
/*     */   public boolean equals(Object paramObject) {
/*     */     
/* 480 */     try { AxisAngle4d axisAngle4d = (AxisAngle4d)paramObject;
/* 481 */       return (this.x == axisAngle4d.x && this.y == axisAngle4d.y && this.z == axisAngle4d.z && this.angle == axisAngle4d.angle); }
/*     */     catch (NullPointerException nullPointerException)
/*     */     
/* 484 */     { return false; }
/* 485 */     catch (ClassCastException classCastException) { return false; }
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
/*     */   
/*     */   public boolean epsilonEquals(AxisAngle4d paramAxisAngle4d, double paramDouble) {
/* 503 */     double d = this.x - paramAxisAngle4d.x;
/* 504 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*     */     
/* 506 */     d = this.y - paramAxisAngle4d.y;
/* 507 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*     */     
/* 509 */     d = this.z - paramAxisAngle4d.z;
/* 510 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*     */     
/* 512 */     d = this.angle - paramAxisAngle4d.angle;
/* 513 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*     */     
/* 515 */     return true;
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
/*     */   public int hashCode() {
/* 528 */     long l = 1L;
/* 529 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.x);
/* 530 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.y);
/* 531 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.z);
/* 532 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.angle);
/* 533 */     return (int)(l ^ l >> 32L);
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
/* 547 */       return super.clone();
/* 548 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 550 */       throw new InternalError();
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
/*     */   public final double getAngle() {
/* 564 */     return this.angle;
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
/*     */   public final void setAngle(double paramDouble) {
/* 577 */     this.angle = paramDouble;
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
/*     */   public double getX() {
/* 589 */     return this.x;
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
/*     */   public final void setX(double paramDouble) {
/* 601 */     this.x = paramDouble;
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
/*     */   public final double getY() {
/* 613 */     return this.y;
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
/*     */   public final void setY(double paramDouble) {
/* 625 */     this.y = paramDouble;
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
/*     */   public double getZ() {
/* 637 */     return this.z;
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
/*     */   public final void setZ(double paramDouble) {
/* 649 */     this.z = paramDouble;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\AxisAngle4d.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
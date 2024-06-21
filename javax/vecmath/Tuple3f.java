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
/*     */ public abstract class Tuple3f
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 5019834619484343712L;
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */   
/*     */   public Tuple3f(float paramFloat1, float paramFloat2, float paramFloat3) {
/*  69 */     this.x = paramFloat1;
/*  70 */     this.y = paramFloat2;
/*  71 */     this.z = paramFloat3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3f(float[] paramArrayOffloat) {
/*  81 */     this.x = paramArrayOffloat[0];
/*  82 */     this.y = paramArrayOffloat[1];
/*  83 */     this.z = paramArrayOffloat[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3f(Tuple3f paramTuple3f) {
/*  93 */     this.x = paramTuple3f.x;
/*  94 */     this.y = paramTuple3f.y;
/*  95 */     this.z = paramTuple3f.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3f(Tuple3d paramTuple3d) {
/* 105 */     this.x = (float)paramTuple3d.x;
/* 106 */     this.y = (float)paramTuple3d.y;
/* 107 */     this.z = (float)paramTuple3d.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3f() {
/* 116 */     this.x = 0.0F;
/* 117 */     this.y = 0.0F;
/* 118 */     this.z = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 128 */     return "(" + this.x + ", " + this.y + ", " + this.z + ")";
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
/*     */   public final void set(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 140 */     this.x = paramFloat1;
/* 141 */     this.y = paramFloat2;
/* 142 */     this.z = paramFloat3;
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
/* 153 */     this.x = paramArrayOffloat[0];
/* 154 */     this.y = paramArrayOffloat[1];
/* 155 */     this.z = paramArrayOffloat[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3f paramTuple3f) {
/* 165 */     this.x = paramTuple3f.x;
/* 166 */     this.y = paramTuple3f.y;
/* 167 */     this.z = paramTuple3f.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3d paramTuple3d) {
/* 177 */     this.x = (float)paramTuple3d.x;
/* 178 */     this.y = (float)paramTuple3d.y;
/* 179 */     this.z = (float)paramTuple3d.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(float[] paramArrayOffloat) {
/* 189 */     paramArrayOffloat[0] = this.x;
/* 190 */     paramArrayOffloat[1] = this.y;
/* 191 */     paramArrayOffloat[2] = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple3f paramTuple3f) {
/* 201 */     paramTuple3f.x = this.x;
/* 202 */     paramTuple3f.y = this.y;
/* 203 */     paramTuple3f.z = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3f paramTuple3f1, Tuple3f paramTuple3f2) {
/* 214 */     paramTuple3f1.x += paramTuple3f2.x;
/* 215 */     paramTuple3f1.y += paramTuple3f2.y;
/* 216 */     paramTuple3f1.z += paramTuple3f2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3f paramTuple3f) {
/* 226 */     this.x += paramTuple3f.x;
/* 227 */     this.y += paramTuple3f.y;
/* 228 */     this.z += paramTuple3f.z;
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
/*     */   public final void sub(Tuple3f paramTuple3f1, Tuple3f paramTuple3f2) {
/* 240 */     paramTuple3f1.x -= paramTuple3f2.x;
/* 241 */     paramTuple3f1.y -= paramTuple3f2.y;
/* 242 */     paramTuple3f1.z -= paramTuple3f2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple3f paramTuple3f) {
/* 253 */     this.x -= paramTuple3f.x;
/* 254 */     this.y -= paramTuple3f.y;
/* 255 */     this.z -= paramTuple3f.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple3f paramTuple3f) {
/* 265 */     this.x = -paramTuple3f.x;
/* 266 */     this.y = -paramTuple3f.y;
/* 267 */     this.z = -paramTuple3f.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 276 */     this.x = -this.x;
/* 277 */     this.y = -this.y;
/* 278 */     this.z = -this.z;
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
/*     */   public final void scale(float paramFloat, Tuple3f paramTuple3f) {
/* 290 */     this.x = paramFloat * paramTuple3f.x;
/* 291 */     this.y = paramFloat * paramTuple3f.y;
/* 292 */     this.z = paramFloat * paramTuple3f.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(float paramFloat) {
/* 303 */     this.x *= paramFloat;
/* 304 */     this.y *= paramFloat;
/* 305 */     this.z *= paramFloat;
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
/*     */   public final void scaleAdd(float paramFloat, Tuple3f paramTuple3f1, Tuple3f paramTuple3f2) {
/* 318 */     this.x = paramFloat * paramTuple3f1.x + paramTuple3f2.x;
/* 319 */     this.y = paramFloat * paramTuple3f1.y + paramTuple3f2.y;
/* 320 */     this.z = paramFloat * paramTuple3f1.z + paramTuple3f2.z;
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
/*     */   public final void scaleAdd(float paramFloat, Tuple3f paramTuple3f) {
/* 333 */     this.x = paramFloat * this.x + paramTuple3f.x;
/* 334 */     this.y = paramFloat * this.y + paramTuple3f.y;
/* 335 */     this.z = paramFloat * this.z + paramTuple3f.z;
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
/*     */   public boolean equals(Tuple3f paramTuple3f) {
/*     */     try {
/* 349 */       return (this.x == paramTuple3f.x && this.y == paramTuple3f.y && this.z == paramTuple3f.z);
/*     */     } catch (NullPointerException nullPointerException) {
/* 351 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*     */     
/* 363 */     try { Tuple3f tuple3f = (Tuple3f)paramObject;
/* 364 */       return (this.x == tuple3f.x && this.y == tuple3f.y && this.z == tuple3f.z); }
/*     */     catch (NullPointerException nullPointerException)
/* 366 */     { return false; }
/* 367 */     catch (ClassCastException classCastException) { return false; }
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
/*     */   public boolean epsilonEquals(Tuple3f paramTuple3f, float paramFloat) {
/* 384 */     float f = this.x - paramTuple3f.x;
/* 385 */     if (Float.isNaN(f)) return false; 
/* 386 */     if (((f < 0.0F) ? -f : f) > paramFloat) return false;
/*     */     
/* 388 */     f = this.y - paramTuple3f.y;
/* 389 */     if (Float.isNaN(f)) return false; 
/* 390 */     if (((f < 0.0F) ? -f : f) > paramFloat) return false;
/*     */     
/* 392 */     f = this.z - paramTuple3f.z;
/* 393 */     if (Float.isNaN(f)) return false; 
/* 394 */     if (((f < 0.0F) ? -f : f) > paramFloat) return false;
/*     */     
/* 396 */     return true;
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
/* 410 */     long l = 1L;
/* 411 */     l = 31L * l + VecMathUtil.floatToIntBits(this.x);
/* 412 */     l = 31L * l + VecMathUtil.floatToIntBits(this.y);
/* 413 */     l = 31L * l + VecMathUtil.floatToIntBits(this.z);
/* 414 */     return (int)(l ^ l >> 32L);
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
/*     */   public final void clamp(float paramFloat1, float paramFloat2, Tuple3f paramTuple3f) {
/* 428 */     if (paramTuple3f.x > paramFloat2) {
/* 429 */       this.x = paramFloat2;
/* 430 */     } else if (paramTuple3f.x < paramFloat1) {
/* 431 */       this.x = paramFloat1;
/*     */     } else {
/* 433 */       this.x = paramTuple3f.x;
/*     */     } 
/*     */     
/* 436 */     if (paramTuple3f.y > paramFloat2) {
/* 437 */       this.y = paramFloat2;
/* 438 */     } else if (paramTuple3f.y < paramFloat1) {
/* 439 */       this.y = paramFloat1;
/*     */     } else {
/* 441 */       this.y = paramTuple3f.y;
/*     */     } 
/*     */     
/* 444 */     if (paramTuple3f.z > paramFloat2) {
/* 445 */       this.z = paramFloat2;
/* 446 */     } else if (paramTuple3f.z < paramFloat1) {
/* 447 */       this.z = paramFloat1;
/*     */     } else {
/* 449 */       this.z = paramTuple3f.z;
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
/*     */   public final void clampMin(float paramFloat, Tuple3f paramTuple3f) {
/* 463 */     if (paramTuple3f.x < paramFloat) {
/* 464 */       this.x = paramFloat;
/*     */     } else {
/* 466 */       this.x = paramTuple3f.x;
/*     */     } 
/*     */     
/* 469 */     if (paramTuple3f.y < paramFloat) {
/* 470 */       this.y = paramFloat;
/*     */     } else {
/* 472 */       this.y = paramTuple3f.y;
/*     */     } 
/*     */     
/* 475 */     if (paramTuple3f.z < paramFloat) {
/* 476 */       this.z = paramFloat;
/*     */     } else {
/* 478 */       this.z = paramTuple3f.z;
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
/*     */   public final void clampMax(float paramFloat, Tuple3f paramTuple3f) {
/* 492 */     if (paramTuple3f.x > paramFloat) {
/* 493 */       this.x = paramFloat;
/*     */     } else {
/* 495 */       this.x = paramTuple3f.x;
/*     */     } 
/*     */     
/* 498 */     if (paramTuple3f.y > paramFloat) {
/* 499 */       this.y = paramFloat;
/*     */     } else {
/* 501 */       this.y = paramTuple3f.y;
/*     */     } 
/*     */     
/* 504 */     if (paramTuple3f.z > paramFloat) {
/* 505 */       this.z = paramFloat;
/*     */     } else {
/* 507 */       this.z = paramTuple3f.z;
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
/*     */   public final void absolute(Tuple3f paramTuple3f) {
/* 520 */     this.x = Math.abs(paramTuple3f.x);
/* 521 */     this.y = Math.abs(paramTuple3f.y);
/* 522 */     this.z = Math.abs(paramTuple3f.z);
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
/*     */   public final void clamp(float paramFloat1, float paramFloat2) {
/* 534 */     if (this.x > paramFloat2) {
/* 535 */       this.x = paramFloat2;
/* 536 */     } else if (this.x < paramFloat1) {
/* 537 */       this.x = paramFloat1;
/*     */     } 
/*     */     
/* 540 */     if (this.y > paramFloat2) {
/* 541 */       this.y = paramFloat2;
/* 542 */     } else if (this.y < paramFloat1) {
/* 543 */       this.y = paramFloat1;
/*     */     } 
/*     */     
/* 546 */     if (this.z > paramFloat2) {
/* 547 */       this.z = paramFloat2;
/* 548 */     } else if (this.z < paramFloat1) {
/* 549 */       this.z = paramFloat1;
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
/*     */   public final void clampMin(float paramFloat) {
/* 561 */     if (this.x < paramFloat) this.x = paramFloat; 
/* 562 */     if (this.y < paramFloat) this.y = paramFloat; 
/* 563 */     if (this.z < paramFloat) this.z = paramFloat;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float paramFloat) {
/* 574 */     if (this.x > paramFloat) this.x = paramFloat; 
/* 575 */     if (this.y > paramFloat) this.y = paramFloat; 
/* 576 */     if (this.z > paramFloat) this.z = paramFloat;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 586 */     this.x = Math.abs(this.x);
/* 587 */     this.y = Math.abs(this.y);
/* 588 */     this.z = Math.abs(this.z);
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
/*     */   public final void interpolate(Tuple3f paramTuple3f1, Tuple3f paramTuple3f2, float paramFloat) {
/* 602 */     this.x = (1.0F - paramFloat) * paramTuple3f1.x + paramFloat * paramTuple3f2.x;
/* 603 */     this.y = (1.0F - paramFloat) * paramTuple3f1.y + paramFloat * paramTuple3f2.y;
/* 604 */     this.z = (1.0F - paramFloat) * paramTuple3f1.z + paramFloat * paramTuple3f2.z;
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
/*     */   public final void interpolate(Tuple3f paramTuple3f, float paramFloat) {
/* 618 */     this.x = (1.0F - paramFloat) * this.x + paramFloat * paramTuple3f.x;
/* 619 */     this.y = (1.0F - paramFloat) * this.y + paramFloat * paramTuple3f.y;
/* 620 */     this.z = (1.0F - paramFloat) * this.z + paramFloat * paramTuple3f.z;
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
/*     */   public Object clone() {
/*     */     try {
/* 636 */       return super.clone();
/* 637 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 639 */       throw new InternalError();
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
/*     */   public final float getX() {
/* 652 */     return this.x;
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
/* 664 */     this.x = paramFloat;
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
/* 676 */     return this.y;
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
/* 688 */     this.y = paramFloat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getZ() {
/* 699 */     return this.z;
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
/* 711 */     this.z = paramFloat;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Tuple3f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
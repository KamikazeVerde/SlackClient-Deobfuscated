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
/*     */ public abstract class Tuple4f
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 7068460319248845763L;
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */   public float w;
/*     */   
/*     */   public Tuple4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  75 */     this.x = paramFloat1;
/*  76 */     this.y = paramFloat2;
/*  77 */     this.z = paramFloat3;
/*  78 */     this.w = paramFloat4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4f(float[] paramArrayOffloat) {
/*  88 */     this.x = paramArrayOffloat[0];
/*  89 */     this.y = paramArrayOffloat[1];
/*  90 */     this.z = paramArrayOffloat[2];
/*  91 */     this.w = paramArrayOffloat[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4f(Tuple4f paramTuple4f) {
/* 101 */     this.x = paramTuple4f.x;
/* 102 */     this.y = paramTuple4f.y;
/* 103 */     this.z = paramTuple4f.z;
/* 104 */     this.w = paramTuple4f.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4f(Tuple4d paramTuple4d) {
/* 114 */     this.x = (float)paramTuple4d.x;
/* 115 */     this.y = (float)paramTuple4d.y;
/* 116 */     this.z = (float)paramTuple4d.z;
/* 117 */     this.w = (float)paramTuple4d.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4f() {
/* 126 */     this.x = 0.0F;
/* 127 */     this.y = 0.0F;
/* 128 */     this.z = 0.0F;
/* 129 */     this.w = 0.0F;
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
/* 142 */     this.x = paramFloat1;
/* 143 */     this.y = paramFloat2;
/* 144 */     this.z = paramFloat3;
/* 145 */     this.w = paramFloat4;
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
/* 156 */     this.x = paramArrayOffloat[0];
/* 157 */     this.y = paramArrayOffloat[1];
/* 158 */     this.z = paramArrayOffloat[2];
/* 159 */     this.w = paramArrayOffloat[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4f paramTuple4f) {
/* 169 */     this.x = paramTuple4f.x;
/* 170 */     this.y = paramTuple4f.y;
/* 171 */     this.z = paramTuple4f.z;
/* 172 */     this.w = paramTuple4f.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4d paramTuple4d) {
/* 182 */     this.x = (float)paramTuple4d.x;
/* 183 */     this.y = (float)paramTuple4d.y;
/* 184 */     this.z = (float)paramTuple4d.z;
/* 185 */     this.w = (float)paramTuple4d.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(float[] paramArrayOffloat) {
/* 195 */     paramArrayOffloat[0] = this.x;
/* 196 */     paramArrayOffloat[1] = this.y;
/* 197 */     paramArrayOffloat[2] = this.z;
/* 198 */     paramArrayOffloat[3] = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple4f paramTuple4f) {
/* 208 */     paramTuple4f.x = this.x;
/* 209 */     paramTuple4f.y = this.y;
/* 210 */     paramTuple4f.z = this.z;
/* 211 */     paramTuple4f.w = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4f paramTuple4f1, Tuple4f paramTuple4f2) {
/* 222 */     paramTuple4f1.x += paramTuple4f2.x;
/* 223 */     paramTuple4f1.y += paramTuple4f2.y;
/* 224 */     paramTuple4f1.z += paramTuple4f2.z;
/* 225 */     paramTuple4f1.w += paramTuple4f2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4f paramTuple4f) {
/* 235 */     this.x += paramTuple4f.x;
/* 236 */     this.y += paramTuple4f.y;
/* 237 */     this.z += paramTuple4f.z;
/* 238 */     this.w += paramTuple4f.w;
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
/*     */   public final void sub(Tuple4f paramTuple4f1, Tuple4f paramTuple4f2) {
/* 250 */     paramTuple4f1.x -= paramTuple4f2.x;
/* 251 */     paramTuple4f1.y -= paramTuple4f2.y;
/* 252 */     paramTuple4f1.z -= paramTuple4f2.z;
/* 253 */     paramTuple4f1.w -= paramTuple4f2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple4f paramTuple4f) {
/* 264 */     this.x -= paramTuple4f.x;
/* 265 */     this.y -= paramTuple4f.y;
/* 266 */     this.z -= paramTuple4f.z;
/* 267 */     this.w -= paramTuple4f.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple4f paramTuple4f) {
/* 277 */     this.x = -paramTuple4f.x;
/* 278 */     this.y = -paramTuple4f.y;
/* 279 */     this.z = -paramTuple4f.z;
/* 280 */     this.w = -paramTuple4f.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 289 */     this.x = -this.x;
/* 290 */     this.y = -this.y;
/* 291 */     this.z = -this.z;
/* 292 */     this.w = -this.w;
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
/*     */   public final void scale(float paramFloat, Tuple4f paramTuple4f) {
/* 304 */     this.x = paramFloat * paramTuple4f.x;
/* 305 */     this.y = paramFloat * paramTuple4f.y;
/* 306 */     this.z = paramFloat * paramTuple4f.z;
/* 307 */     this.w = paramFloat * paramTuple4f.w;
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
/* 318 */     this.x *= paramFloat;
/* 319 */     this.y *= paramFloat;
/* 320 */     this.z *= paramFloat;
/* 321 */     this.w *= paramFloat;
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
/*     */   public final void scaleAdd(float paramFloat, Tuple4f paramTuple4f1, Tuple4f paramTuple4f2) {
/* 334 */     this.x = paramFloat * paramTuple4f1.x + paramTuple4f2.x;
/* 335 */     this.y = paramFloat * paramTuple4f1.y + paramTuple4f2.y;
/* 336 */     this.z = paramFloat * paramTuple4f1.z + paramTuple4f2.z;
/* 337 */     this.w = paramFloat * paramTuple4f1.w + paramTuple4f2.w;
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
/*     */   public final void scaleAdd(float paramFloat, Tuple4f paramTuple4f) {
/* 349 */     this.x = paramFloat * this.x + paramTuple4f.x;
/* 350 */     this.y = paramFloat * this.y + paramTuple4f.y;
/* 351 */     this.z = paramFloat * this.z + paramTuple4f.z;
/* 352 */     this.w = paramFloat * this.w + paramTuple4f.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 363 */     return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Tuple4f paramTuple4f) {
/*     */     try {
/* 375 */       return (this.x == paramTuple4f.x && this.y == paramTuple4f.y && this.z == paramTuple4f.z && this.w == paramTuple4f.w);
/*     */     } catch (NullPointerException nullPointerException) {
/*     */       
/* 378 */       return false;
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
/* 391 */     try { Tuple4f tuple4f = (Tuple4f)paramObject;
/* 392 */       return (this.x == tuple4f.x && this.y == tuple4f.y && this.z == tuple4f.z && this.w == tuple4f.w); }
/*     */     catch (NullPointerException nullPointerException)
/*     */     
/* 395 */     { return false; }
/* 396 */     catch (ClassCastException classCastException) { return false; }
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
/*     */   public boolean epsilonEquals(Tuple4f paramTuple4f, float paramFloat) {
/* 414 */     float f = this.x - paramTuple4f.x;
/* 415 */     if (Float.isNaN(f)) return false; 
/* 416 */     if (((f < 0.0F) ? -f : f) > paramFloat) return false;
/*     */     
/* 418 */     f = this.y - paramTuple4f.y;
/* 419 */     if (Float.isNaN(f)) return false; 
/* 420 */     if (((f < 0.0F) ? -f : f) > paramFloat) return false;
/*     */     
/* 422 */     f = this.z - paramTuple4f.z;
/* 423 */     if (Float.isNaN(f)) return false; 
/* 424 */     if (((f < 0.0F) ? -f : f) > paramFloat) return false;
/*     */     
/* 426 */     f = this.w - paramTuple4f.w;
/* 427 */     if (Float.isNaN(f)) return false; 
/* 428 */     if (((f < 0.0F) ? -f : f) > paramFloat) return false;
/*     */     
/* 430 */     return true;
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
/* 443 */     long l = 1L;
/* 444 */     l = 31L * l + VecMathUtil.floatToIntBits(this.x);
/* 445 */     l = 31L * l + VecMathUtil.floatToIntBits(this.y);
/* 446 */     l = 31L * l + VecMathUtil.floatToIntBits(this.z);
/* 447 */     l = 31L * l + VecMathUtil.floatToIntBits(this.w);
/* 448 */     return (int)(l ^ l >> 32L);
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
/*     */   public final void clamp(float paramFloat1, float paramFloat2, Tuple4f paramTuple4f) {
/* 461 */     if (paramTuple4f.x > paramFloat2) {
/* 462 */       this.x = paramFloat2;
/* 463 */     } else if (paramTuple4f.x < paramFloat1) {
/* 464 */       this.x = paramFloat1;
/*     */     } else {
/* 466 */       this.x = paramTuple4f.x;
/*     */     } 
/*     */     
/* 469 */     if (paramTuple4f.y > paramFloat2) {
/* 470 */       this.y = paramFloat2;
/* 471 */     } else if (paramTuple4f.y < paramFloat1) {
/* 472 */       this.y = paramFloat1;
/*     */     } else {
/* 474 */       this.y = paramTuple4f.y;
/*     */     } 
/*     */     
/* 477 */     if (paramTuple4f.z > paramFloat2) {
/* 478 */       this.z = paramFloat2;
/* 479 */     } else if (paramTuple4f.z < paramFloat1) {
/* 480 */       this.z = paramFloat1;
/*     */     } else {
/* 482 */       this.z = paramTuple4f.z;
/*     */     } 
/*     */     
/* 485 */     if (paramTuple4f.w > paramFloat2) {
/* 486 */       this.w = paramFloat2;
/* 487 */     } else if (paramTuple4f.w < paramFloat1) {
/* 488 */       this.w = paramFloat1;
/*     */     } else {
/* 490 */       this.w = paramTuple4f.w;
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
/*     */   public final void clampMin(float paramFloat, Tuple4f paramTuple4f) {
/* 504 */     if (paramTuple4f.x < paramFloat) {
/* 505 */       this.x = paramFloat;
/*     */     } else {
/* 507 */       this.x = paramTuple4f.x;
/*     */     } 
/*     */     
/* 510 */     if (paramTuple4f.y < paramFloat) {
/* 511 */       this.y = paramFloat;
/*     */     } else {
/* 513 */       this.y = paramTuple4f.y;
/*     */     } 
/*     */     
/* 516 */     if (paramTuple4f.z < paramFloat) {
/* 517 */       this.z = paramFloat;
/*     */     } else {
/* 519 */       this.z = paramTuple4f.z;
/*     */     } 
/*     */     
/* 522 */     if (paramTuple4f.w < paramFloat) {
/* 523 */       this.w = paramFloat;
/*     */     } else {
/* 525 */       this.w = paramTuple4f.w;
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
/*     */   public final void clampMax(float paramFloat, Tuple4f paramTuple4f) {
/* 540 */     if (paramTuple4f.x > paramFloat) {
/* 541 */       this.x = paramFloat;
/*     */     } else {
/* 543 */       this.x = paramTuple4f.x;
/*     */     } 
/*     */     
/* 546 */     if (paramTuple4f.y > paramFloat) {
/* 547 */       this.y = paramFloat;
/*     */     } else {
/* 549 */       this.y = paramTuple4f.y;
/*     */     } 
/*     */     
/* 552 */     if (paramTuple4f.z > paramFloat) {
/* 553 */       this.z = paramFloat;
/*     */     } else {
/* 555 */       this.z = paramTuple4f.z;
/*     */     } 
/*     */     
/* 558 */     if (paramTuple4f.w > paramFloat) {
/* 559 */       this.w = paramFloat;
/*     */     } else {
/* 561 */       this.w = paramTuple4f.z;
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
/*     */   public final void absolute(Tuple4f paramTuple4f) {
/* 574 */     this.x = Math.abs(paramTuple4f.x);
/* 575 */     this.y = Math.abs(paramTuple4f.y);
/* 576 */     this.z = Math.abs(paramTuple4f.z);
/* 577 */     this.w = Math.abs(paramTuple4f.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float paramFloat1, float paramFloat2) {
/* 588 */     if (this.x > paramFloat2) {
/* 589 */       this.x = paramFloat2;
/* 590 */     } else if (this.x < paramFloat1) {
/* 591 */       this.x = paramFloat1;
/*     */     } 
/*     */     
/* 594 */     if (this.y > paramFloat2) {
/* 595 */       this.y = paramFloat2;
/* 596 */     } else if (this.y < paramFloat1) {
/* 597 */       this.y = paramFloat1;
/*     */     } 
/*     */     
/* 600 */     if (this.z > paramFloat2) {
/* 601 */       this.z = paramFloat2;
/* 602 */     } else if (this.z < paramFloat1) {
/* 603 */       this.z = paramFloat1;
/*     */     } 
/*     */     
/* 606 */     if (this.w > paramFloat2) {
/* 607 */       this.w = paramFloat2;
/* 608 */     } else if (this.w < paramFloat1) {
/* 609 */       this.w = paramFloat1;
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
/* 621 */     if (this.x < paramFloat) this.x = paramFloat; 
/* 622 */     if (this.y < paramFloat) this.y = paramFloat; 
/* 623 */     if (this.z < paramFloat) this.z = paramFloat; 
/* 624 */     if (this.w < paramFloat) this.w = paramFloat;
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
/* 635 */     if (this.x > paramFloat) this.x = paramFloat; 
/* 636 */     if (this.y > paramFloat) this.y = paramFloat; 
/* 637 */     if (this.z > paramFloat) this.z = paramFloat; 
/* 638 */     if (this.w > paramFloat) this.w = paramFloat;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 648 */     this.x = Math.abs(this.x);
/* 649 */     this.y = Math.abs(this.y);
/* 650 */     this.z = Math.abs(this.z);
/* 651 */     this.w = Math.abs(this.w);
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
/*     */   public void interpolate(Tuple4f paramTuple4f1, Tuple4f paramTuple4f2, float paramFloat) {
/* 664 */     this.x = (1.0F - paramFloat) * paramTuple4f1.x + paramFloat * paramTuple4f2.x;
/* 665 */     this.y = (1.0F - paramFloat) * paramTuple4f1.y + paramFloat * paramTuple4f2.y;
/* 666 */     this.z = (1.0F - paramFloat) * paramTuple4f1.z + paramFloat * paramTuple4f2.z;
/* 667 */     this.w = (1.0F - paramFloat) * paramTuple4f1.w + paramFloat * paramTuple4f2.w;
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
/*     */   public void interpolate(Tuple4f paramTuple4f, float paramFloat) {
/* 680 */     this.x = (1.0F - paramFloat) * this.x + paramFloat * paramTuple4f.x;
/* 681 */     this.y = (1.0F - paramFloat) * this.y + paramFloat * paramTuple4f.y;
/* 682 */     this.z = (1.0F - paramFloat) * this.z + paramFloat * paramTuple4f.z;
/* 683 */     this.w = (1.0F - paramFloat) * this.w + paramFloat * paramTuple4f.w;
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
/*     */   public Object clone() {
/*     */     try {
/* 698 */       return super.clone();
/* 699 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 701 */       throw new InternalError();
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
/*     */   public final float getX() {
/* 713 */     return this.x;
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
/* 725 */     this.x = paramFloat;
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
/* 737 */     return this.y;
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
/* 749 */     this.y = paramFloat;
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
/* 760 */     return this.z;
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
/* 772 */     this.z = paramFloat;
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
/*     */   public final float getW() {
/* 784 */     return this.w;
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
/*     */   public final void setW(float paramFloat) {
/* 796 */     this.w = paramFloat;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Tuple4f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
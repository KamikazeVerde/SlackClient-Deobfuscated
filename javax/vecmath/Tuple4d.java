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
/*     */ public abstract class Tuple4d
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -4748953690425311052L;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   public double w;
/*     */   
/*     */   public Tuple4d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  75 */     this.x = paramDouble1;
/*  76 */     this.y = paramDouble2;
/*  77 */     this.z = paramDouble3;
/*  78 */     this.w = paramDouble4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4d(double[] paramArrayOfdouble) {
/*  89 */     this.x = paramArrayOfdouble[0];
/*  90 */     this.y = paramArrayOfdouble[1];
/*  91 */     this.z = paramArrayOfdouble[2];
/*  92 */     this.w = paramArrayOfdouble[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4d(Tuple4d paramTuple4d) {
/* 102 */     this.x = paramTuple4d.x;
/* 103 */     this.y = paramTuple4d.y;
/* 104 */     this.z = paramTuple4d.z;
/* 105 */     this.w = paramTuple4d.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4d(Tuple4f paramTuple4f) {
/* 115 */     this.x = paramTuple4f.x;
/* 116 */     this.y = paramTuple4f.y;
/* 117 */     this.z = paramTuple4f.z;
/* 118 */     this.w = paramTuple4f.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4d() {
/* 127 */     this.x = 0.0D;
/* 128 */     this.y = 0.0D;
/* 129 */     this.z = 0.0D;
/* 130 */     this.w = 0.0D;
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
/* 143 */     this.x = paramDouble1;
/* 144 */     this.y = paramDouble2;
/* 145 */     this.z = paramDouble3;
/* 146 */     this.w = paramDouble4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double[] paramArrayOfdouble) {
/* 156 */     this.x = paramArrayOfdouble[0];
/* 157 */     this.y = paramArrayOfdouble[1];
/* 158 */     this.z = paramArrayOfdouble[2];
/* 159 */     this.w = paramArrayOfdouble[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4d paramTuple4d) {
/* 169 */     this.x = paramTuple4d.x;
/* 170 */     this.y = paramTuple4d.y;
/* 171 */     this.z = paramTuple4d.z;
/* 172 */     this.w = paramTuple4d.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4f paramTuple4f) {
/* 182 */     this.x = paramTuple4f.x;
/* 183 */     this.y = paramTuple4f.y;
/* 184 */     this.z = paramTuple4f.z;
/* 185 */     this.w = paramTuple4f.w;
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
/* 196 */     paramArrayOfdouble[0] = this.x;
/* 197 */     paramArrayOfdouble[1] = this.y;
/* 198 */     paramArrayOfdouble[2] = this.z;
/* 199 */     paramArrayOfdouble[3] = this.w;
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
/*     */   public final void get(Tuple4d paramTuple4d) {
/* 211 */     paramTuple4d.x = this.x;
/* 212 */     paramTuple4d.y = this.y;
/* 213 */     paramTuple4d.z = this.z;
/* 214 */     paramTuple4d.w = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4d paramTuple4d1, Tuple4d paramTuple4d2) {
/* 225 */     paramTuple4d1.x += paramTuple4d2.x;
/* 226 */     paramTuple4d1.y += paramTuple4d2.y;
/* 227 */     paramTuple4d1.z += paramTuple4d2.z;
/* 228 */     paramTuple4d1.w += paramTuple4d2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4d paramTuple4d) {
/* 238 */     this.x += paramTuple4d.x;
/* 239 */     this.y += paramTuple4d.y;
/* 240 */     this.z += paramTuple4d.z;
/* 241 */     this.w += paramTuple4d.w;
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
/*     */   public final void sub(Tuple4d paramTuple4d1, Tuple4d paramTuple4d2) {
/* 253 */     paramTuple4d1.x -= paramTuple4d2.x;
/* 254 */     paramTuple4d1.y -= paramTuple4d2.y;
/* 255 */     paramTuple4d1.z -= paramTuple4d2.z;
/* 256 */     paramTuple4d1.w -= paramTuple4d2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple4d paramTuple4d) {
/* 267 */     this.x -= paramTuple4d.x;
/* 268 */     this.y -= paramTuple4d.y;
/* 269 */     this.z -= paramTuple4d.z;
/* 270 */     this.w -= paramTuple4d.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple4d paramTuple4d) {
/* 280 */     this.x = -paramTuple4d.x;
/* 281 */     this.y = -paramTuple4d.y;
/* 282 */     this.z = -paramTuple4d.z;
/* 283 */     this.w = -paramTuple4d.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 292 */     this.x = -this.x;
/* 293 */     this.y = -this.y;
/* 294 */     this.z = -this.z;
/* 295 */     this.w = -this.w;
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
/*     */   public final void scale(double paramDouble, Tuple4d paramTuple4d) {
/* 307 */     this.x = paramDouble * paramTuple4d.x;
/* 308 */     this.y = paramDouble * paramTuple4d.y;
/* 309 */     this.z = paramDouble * paramTuple4d.z;
/* 310 */     this.w = paramDouble * paramTuple4d.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(double paramDouble) {
/* 321 */     this.x *= paramDouble;
/* 322 */     this.y *= paramDouble;
/* 323 */     this.z *= paramDouble;
/* 324 */     this.w *= paramDouble;
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
/*     */   public final void scaleAdd(double paramDouble, Tuple4d paramTuple4d1, Tuple4d paramTuple4d2) {
/* 337 */     this.x = paramDouble * paramTuple4d1.x + paramTuple4d2.x;
/* 338 */     this.y = paramDouble * paramTuple4d1.y + paramTuple4d2.y;
/* 339 */     this.z = paramDouble * paramTuple4d1.z + paramTuple4d2.z;
/* 340 */     this.w = paramDouble * paramTuple4d1.w + paramTuple4d2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(float paramFloat, Tuple4d paramTuple4d) {
/* 349 */     scaleAdd(paramFloat, paramTuple4d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(double paramDouble, Tuple4d paramTuple4d) {
/* 360 */     this.x = paramDouble * this.x + paramTuple4d.x;
/* 361 */     this.y = paramDouble * this.y + paramTuple4d.y;
/* 362 */     this.z = paramDouble * this.z + paramTuple4d.z;
/* 363 */     this.w = paramDouble * this.w + paramTuple4d.w;
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
/* 374 */     return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
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
/*     */   public boolean equals(Tuple4d paramTuple4d) {
/*     */     try {
/* 387 */       return (this.x == paramTuple4d.x && this.y == paramTuple4d.y && this.z == paramTuple4d.z && this.w == paramTuple4d.w);
/*     */     } catch (NullPointerException nullPointerException) {
/*     */       
/* 390 */       return false;
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
/* 404 */     try { Tuple4d tuple4d = (Tuple4d)paramObject;
/* 405 */       return (this.x == tuple4d.x && this.y == tuple4d.y && this.z == tuple4d.z && this.w == tuple4d.w); }
/*     */     catch (NullPointerException nullPointerException)
/*     */     
/* 408 */     { return false; }
/* 409 */     catch (ClassCastException classCastException) { return false; }
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
/*     */   public boolean epsilonEquals(Tuple4d paramTuple4d, double paramDouble) {
/* 427 */     double d = this.x - paramTuple4d.x;
/* 428 */     if (Double.isNaN(d)) return false; 
/* 429 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*     */     
/* 431 */     d = this.y - paramTuple4d.y;
/* 432 */     if (Double.isNaN(d)) return false; 
/* 433 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*     */     
/* 435 */     d = this.z - paramTuple4d.z;
/* 436 */     if (Double.isNaN(d)) return false; 
/* 437 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*     */     
/* 439 */     d = this.w - paramTuple4d.w;
/* 440 */     if (Double.isNaN(d)) return false; 
/* 441 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*     */     
/* 443 */     return true;
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
/* 457 */     long l = 1L;
/* 458 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.x);
/* 459 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.y);
/* 460 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.z);
/* 461 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.w);
/* 462 */     return (int)(l ^ l >> 32L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float paramFloat1, float paramFloat2, Tuple4d paramTuple4d) {
/* 470 */     clamp(paramFloat1, paramFloat2, paramTuple4d);
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
/*     */   public final void clamp(double paramDouble1, double paramDouble2, Tuple4d paramTuple4d) {
/* 482 */     if (paramTuple4d.x > paramDouble2) {
/* 483 */       this.x = paramDouble2;
/* 484 */     } else if (paramTuple4d.x < paramDouble1) {
/* 485 */       this.x = paramDouble1;
/*     */     } else {
/* 487 */       this.x = paramTuple4d.x;
/*     */     } 
/*     */     
/* 490 */     if (paramTuple4d.y > paramDouble2) {
/* 491 */       this.y = paramDouble2;
/* 492 */     } else if (paramTuple4d.y < paramDouble1) {
/* 493 */       this.y = paramDouble1;
/*     */     } else {
/* 495 */       this.y = paramTuple4d.y;
/*     */     } 
/*     */     
/* 498 */     if (paramTuple4d.z > paramDouble2) {
/* 499 */       this.z = paramDouble2;
/* 500 */     } else if (paramTuple4d.z < paramDouble1) {
/* 501 */       this.z = paramDouble1;
/*     */     } else {
/* 503 */       this.z = paramTuple4d.z;
/*     */     } 
/*     */     
/* 506 */     if (paramTuple4d.w > paramDouble2) {
/* 507 */       this.w = paramDouble2;
/* 508 */     } else if (paramTuple4d.w < paramDouble1) {
/* 509 */       this.w = paramDouble1;
/*     */     } else {
/* 511 */       this.w = paramTuple4d.w;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(float paramFloat, Tuple4d paramTuple4d) {
/* 521 */     clampMin(paramFloat, paramTuple4d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(double paramDouble, Tuple4d paramTuple4d) {
/* 532 */     if (paramTuple4d.x < paramDouble) {
/* 533 */       this.x = paramDouble;
/*     */     } else {
/* 535 */       this.x = paramTuple4d.x;
/*     */     } 
/*     */     
/* 538 */     if (paramTuple4d.y < paramDouble) {
/* 539 */       this.y = paramDouble;
/*     */     } else {
/* 541 */       this.y = paramTuple4d.y;
/*     */     } 
/*     */     
/* 544 */     if (paramTuple4d.z < paramDouble) {
/* 545 */       this.z = paramDouble;
/*     */     } else {
/* 547 */       this.z = paramTuple4d.z;
/*     */     } 
/*     */     
/* 550 */     if (paramTuple4d.w < paramDouble) {
/* 551 */       this.w = paramDouble;
/*     */     } else {
/* 553 */       this.w = paramTuple4d.w;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float paramFloat, Tuple4d paramTuple4d) {
/* 563 */     clampMax(paramFloat, paramTuple4d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(double paramDouble, Tuple4d paramTuple4d) {
/* 574 */     if (paramTuple4d.x > paramDouble) {
/* 575 */       this.x = paramDouble;
/*     */     } else {
/* 577 */       this.x = paramTuple4d.x;
/*     */     } 
/*     */     
/* 580 */     if (paramTuple4d.y > paramDouble) {
/* 581 */       this.y = paramDouble;
/*     */     } else {
/* 583 */       this.y = paramTuple4d.y;
/*     */     } 
/*     */     
/* 586 */     if (paramTuple4d.z > paramDouble) {
/* 587 */       this.z = paramDouble;
/*     */     } else {
/* 589 */       this.z = paramTuple4d.z;
/*     */     } 
/*     */     
/* 592 */     if (paramTuple4d.w > paramDouble) {
/* 593 */       this.w = paramDouble;
/*     */     } else {
/* 595 */       this.w = paramTuple4d.z;
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
/*     */   public final void absolute(Tuple4d paramTuple4d) {
/* 608 */     this.x = Math.abs(paramTuple4d.x);
/* 609 */     this.y = Math.abs(paramTuple4d.y);
/* 610 */     this.z = Math.abs(paramTuple4d.z);
/* 611 */     this.w = Math.abs(paramTuple4d.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float paramFloat1, float paramFloat2) {
/* 621 */     clamp(paramFloat1, paramFloat2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(double paramDouble1, double paramDouble2) {
/* 631 */     if (this.x > paramDouble2) {
/* 632 */       this.x = paramDouble2;
/* 633 */     } else if (this.x < paramDouble1) {
/* 634 */       this.x = paramDouble1;
/*     */     } 
/*     */     
/* 637 */     if (this.y > paramDouble2) {
/* 638 */       this.y = paramDouble2;
/* 639 */     } else if (this.y < paramDouble1) {
/* 640 */       this.y = paramDouble1;
/*     */     } 
/*     */     
/* 643 */     if (this.z > paramDouble2) {
/* 644 */       this.z = paramDouble2;
/* 645 */     } else if (this.z < paramDouble1) {
/* 646 */       this.z = paramDouble1;
/*     */     } 
/*     */     
/* 649 */     if (this.w > paramDouble2) {
/* 650 */       this.w = paramDouble2;
/* 651 */     } else if (this.w < paramDouble1) {
/* 652 */       this.w = paramDouble1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(float paramFloat) {
/* 662 */     clampMin(paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(double paramDouble) {
/* 671 */     if (this.x < paramDouble) this.x = paramDouble; 
/* 672 */     if (this.y < paramDouble) this.y = paramDouble; 
/* 673 */     if (this.z < paramDouble) this.z = paramDouble; 
/* 674 */     if (this.w < paramDouble) this.w = paramDouble;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float paramFloat) {
/* 682 */     clampMax(paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(double paramDouble) {
/* 691 */     if (this.x > paramDouble) this.x = paramDouble; 
/* 692 */     if (this.y > paramDouble) this.y = paramDouble; 
/* 693 */     if (this.z > paramDouble) this.z = paramDouble; 
/* 694 */     if (this.w > paramDouble) this.w = paramDouble;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 704 */     this.x = Math.abs(this.x);
/* 705 */     this.y = Math.abs(this.y);
/* 706 */     this.z = Math.abs(this.z);
/* 707 */     this.w = Math.abs(this.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate(Tuple4d paramTuple4d1, Tuple4d paramTuple4d2, float paramFloat) {
/* 716 */     interpolate(paramTuple4d1, paramTuple4d2, paramFloat);
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
/*     */   public void interpolate(Tuple4d paramTuple4d1, Tuple4d paramTuple4d2, double paramDouble) {
/* 728 */     this.x = (1.0D - paramDouble) * paramTuple4d1.x + paramDouble * paramTuple4d2.x;
/* 729 */     this.y = (1.0D - paramDouble) * paramTuple4d1.y + paramDouble * paramTuple4d2.y;
/* 730 */     this.z = (1.0D - paramDouble) * paramTuple4d1.z + paramDouble * paramTuple4d2.z;
/* 731 */     this.w = (1.0D - paramDouble) * paramTuple4d1.w + paramDouble * paramTuple4d2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate(Tuple4d paramTuple4d, float paramFloat) {
/* 739 */     interpolate(paramTuple4d, paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate(Tuple4d paramTuple4d, double paramDouble) {
/* 750 */     this.x = (1.0D - paramDouble) * this.x + paramDouble * paramTuple4d.x;
/* 751 */     this.y = (1.0D - paramDouble) * this.y + paramDouble * paramTuple4d.y;
/* 752 */     this.z = (1.0D - paramDouble) * this.z + paramDouble * paramTuple4d.z;
/* 753 */     this.w = (1.0D - paramDouble) * this.w + paramDouble * paramTuple4d.w;
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
/* 767 */       return super.clone();
/* 768 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 770 */       throw new InternalError();
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
/*     */   public final double getX() {
/* 782 */     return this.x;
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
/* 794 */     this.x = paramDouble;
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
/* 806 */     return this.y;
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
/* 818 */     this.y = paramDouble;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getZ() {
/* 829 */     return this.z;
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
/* 841 */     this.z = paramDouble;
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
/*     */   public final double getW() {
/* 853 */     return this.w;
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
/*     */   public final void setW(double paramDouble) {
/* 865 */     this.w = paramDouble;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Tuple4d.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
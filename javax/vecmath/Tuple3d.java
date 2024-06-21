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
/*     */ public abstract class Tuple3d
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 5542096614926168415L;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   
/*     */   public Tuple3d(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  69 */     this.x = paramDouble1;
/*  70 */     this.y = paramDouble2;
/*  71 */     this.z = paramDouble3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3d(double[] paramArrayOfdouble) {
/*  80 */     this.x = paramArrayOfdouble[0];
/*  81 */     this.y = paramArrayOfdouble[1];
/*  82 */     this.z = paramArrayOfdouble[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3d(Tuple3d paramTuple3d) {
/*  91 */     this.x = paramTuple3d.x;
/*  92 */     this.y = paramTuple3d.y;
/*  93 */     this.z = paramTuple3d.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3d(Tuple3f paramTuple3f) {
/* 102 */     this.x = paramTuple3f.x;
/* 103 */     this.y = paramTuple3f.y;
/* 104 */     this.z = paramTuple3f.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3d() {
/* 112 */     this.x = 0.0D;
/* 113 */     this.y = 0.0D;
/* 114 */     this.z = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 125 */     this.x = paramDouble1;
/* 126 */     this.y = paramDouble2;
/* 127 */     this.z = paramDouble3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double[] paramArrayOfdouble) {
/* 137 */     this.x = paramArrayOfdouble[0];
/* 138 */     this.y = paramArrayOfdouble[1];
/* 139 */     this.z = paramArrayOfdouble[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3d paramTuple3d) {
/* 148 */     this.x = paramTuple3d.x;
/* 149 */     this.y = paramTuple3d.y;
/* 150 */     this.z = paramTuple3d.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3f paramTuple3f) {
/* 159 */     this.x = paramTuple3f.x;
/* 160 */     this.y = paramTuple3f.y;
/* 161 */     this.z = paramTuple3f.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(double[] paramArrayOfdouble) {
/* 171 */     paramArrayOfdouble[0] = this.x;
/* 172 */     paramArrayOfdouble[1] = this.y;
/* 173 */     paramArrayOfdouble[2] = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple3d paramTuple3d) {
/* 183 */     paramTuple3d.x = this.x;
/* 184 */     paramTuple3d.y = this.y;
/* 185 */     paramTuple3d.z = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3d paramTuple3d1, Tuple3d paramTuple3d2) {
/* 196 */     paramTuple3d1.x += paramTuple3d2.x;
/* 197 */     paramTuple3d1.y += paramTuple3d2.y;
/* 198 */     paramTuple3d1.z += paramTuple3d2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3d paramTuple3d) {
/* 208 */     this.x += paramTuple3d.x;
/* 209 */     this.y += paramTuple3d.y;
/* 210 */     this.z += paramTuple3d.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple3d paramTuple3d1, Tuple3d paramTuple3d2) {
/* 221 */     paramTuple3d1.x -= paramTuple3d2.x;
/* 222 */     paramTuple3d1.y -= paramTuple3d2.y;
/* 223 */     paramTuple3d1.z -= paramTuple3d2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple3d paramTuple3d) {
/* 233 */     this.x -= paramTuple3d.x;
/* 234 */     this.y -= paramTuple3d.y;
/* 235 */     this.z -= paramTuple3d.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple3d paramTuple3d) {
/* 245 */     this.x = -paramTuple3d.x;
/* 246 */     this.y = -paramTuple3d.y;
/* 247 */     this.z = -paramTuple3d.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 256 */     this.x = -this.x;
/* 257 */     this.y = -this.y;
/* 258 */     this.z = -this.z;
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
/*     */   public final void scale(double paramDouble, Tuple3d paramTuple3d) {
/* 270 */     this.x = paramDouble * paramTuple3d.x;
/* 271 */     this.y = paramDouble * paramTuple3d.y;
/* 272 */     this.z = paramDouble * paramTuple3d.z;
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
/* 283 */     this.x *= paramDouble;
/* 284 */     this.y *= paramDouble;
/* 285 */     this.z *= paramDouble;
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
/*     */   public final void scaleAdd(double paramDouble, Tuple3d paramTuple3d1, Tuple3d paramTuple3d2) {
/* 298 */     this.x = paramDouble * paramTuple3d1.x + paramTuple3d2.x;
/* 299 */     this.y = paramDouble * paramTuple3d1.y + paramTuple3d2.y;
/* 300 */     this.z = paramDouble * paramTuple3d1.z + paramTuple3d2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(double paramDouble, Tuple3f paramTuple3f) {
/* 308 */     scaleAdd(paramDouble, new Point3d(paramTuple3f));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(double paramDouble, Tuple3d paramTuple3d) {
/* 319 */     this.x = paramDouble * this.x + paramTuple3d.x;
/* 320 */     this.y = paramDouble * this.y + paramTuple3d.y;
/* 321 */     this.z = paramDouble * this.z + paramTuple3d.z;
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
/* 332 */     return "(" + this.x + ", " + this.y + ", " + this.z + ")";
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
/* 345 */     long l = 1L;
/* 346 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.x);
/* 347 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.y);
/* 348 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.z);
/* 349 */     return (int)(l ^ l >> 32L);
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
/*     */   public boolean equals(Tuple3d paramTuple3d) {
/*     */     try {
/* 362 */       return (this.x == paramTuple3d.x && this.y == paramTuple3d.y && this.z == paramTuple3d.z);
/*     */     } catch (NullPointerException nullPointerException) {
/* 364 */       return false;
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
/* 377 */     try { Tuple3d tuple3d = (Tuple3d)paramObject;
/* 378 */       return (this.x == tuple3d.x && this.y == tuple3d.y && this.z == tuple3d.z); }
/*     */     catch (ClassCastException classCastException)
/* 380 */     { return false; }
/* 381 */     catch (NullPointerException nullPointerException) { return false; }
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
/*     */   public boolean epsilonEquals(Tuple3d paramTuple3d, double paramDouble) {
/* 398 */     double d = this.x - paramTuple3d.x;
/* 399 */     if (Double.isNaN(d)) return false; 
/* 400 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*     */     
/* 402 */     d = this.y - paramTuple3d.y;
/* 403 */     if (Double.isNaN(d)) return false; 
/* 404 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*     */     
/* 406 */     d = this.z - paramTuple3d.z;
/* 407 */     if (Double.isNaN(d)) return false; 
/* 408 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*     */     
/* 410 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float paramFloat1, float paramFloat2, Tuple3d paramTuple3d) {
/* 419 */     clamp(paramFloat1, paramFloat2, paramTuple3d);
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
/*     */   public final void clamp(double paramDouble1, double paramDouble2, Tuple3d paramTuple3d) {
/* 431 */     if (paramTuple3d.x > paramDouble2) {
/* 432 */       this.x = paramDouble2;
/* 433 */     } else if (paramTuple3d.x < paramDouble1) {
/* 434 */       this.x = paramDouble1;
/*     */     } else {
/* 436 */       this.x = paramTuple3d.x;
/*     */     } 
/*     */     
/* 439 */     if (paramTuple3d.y > paramDouble2) {
/* 440 */       this.y = paramDouble2;
/* 441 */     } else if (paramTuple3d.y < paramDouble1) {
/* 442 */       this.y = paramDouble1;
/*     */     } else {
/* 444 */       this.y = paramTuple3d.y;
/*     */     } 
/*     */     
/* 447 */     if (paramTuple3d.z > paramDouble2) {
/* 448 */       this.z = paramDouble2;
/* 449 */     } else if (paramTuple3d.z < paramDouble1) {
/* 450 */       this.z = paramDouble1;
/*     */     } else {
/* 452 */       this.z = paramTuple3d.z;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(float paramFloat, Tuple3d paramTuple3d) {
/* 462 */     clampMin(paramFloat, paramTuple3d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(double paramDouble, Tuple3d paramTuple3d) {
/* 473 */     if (paramTuple3d.x < paramDouble) {
/* 474 */       this.x = paramDouble;
/*     */     } else {
/* 476 */       this.x = paramTuple3d.x;
/*     */     } 
/*     */     
/* 479 */     if (paramTuple3d.y < paramDouble) {
/* 480 */       this.y = paramDouble;
/*     */     } else {
/* 482 */       this.y = paramTuple3d.y;
/*     */     } 
/*     */     
/* 485 */     if (paramTuple3d.z < paramDouble) {
/* 486 */       this.z = paramDouble;
/*     */     } else {
/* 488 */       this.z = paramTuple3d.z;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float paramFloat, Tuple3d paramTuple3d) {
/* 498 */     clampMax(paramFloat, paramTuple3d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(double paramDouble, Tuple3d paramTuple3d) {
/* 509 */     if (paramTuple3d.x > paramDouble) {
/* 510 */       this.x = paramDouble;
/*     */     } else {
/* 512 */       this.x = paramTuple3d.x;
/*     */     } 
/*     */     
/* 515 */     if (paramTuple3d.y > paramDouble) {
/* 516 */       this.y = paramDouble;
/*     */     } else {
/* 518 */       this.y = paramTuple3d.y;
/*     */     } 
/*     */     
/* 521 */     if (paramTuple3d.z > paramDouble) {
/* 522 */       this.z = paramDouble;
/*     */     } else {
/* 524 */       this.z = paramTuple3d.z;
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
/*     */   public final void absolute(Tuple3d paramTuple3d) {
/* 537 */     this.x = Math.abs(paramTuple3d.x);
/* 538 */     this.y = Math.abs(paramTuple3d.y);
/* 539 */     this.z = Math.abs(paramTuple3d.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float paramFloat1, float paramFloat2) {
/* 548 */     clamp(paramFloat1, paramFloat2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(double paramDouble1, double paramDouble2) {
/* 558 */     if (this.x > paramDouble2) {
/* 559 */       this.x = paramDouble2;
/* 560 */     } else if (this.x < paramDouble1) {
/* 561 */       this.x = paramDouble1;
/*     */     } 
/*     */     
/* 564 */     if (this.y > paramDouble2) {
/* 565 */       this.y = paramDouble2;
/* 566 */     } else if (this.y < paramDouble1) {
/* 567 */       this.y = paramDouble1;
/*     */     } 
/*     */     
/* 570 */     if (this.z > paramDouble2) {
/* 571 */       this.z = paramDouble2;
/* 572 */     } else if (this.z < paramDouble1) {
/* 573 */       this.z = paramDouble1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(float paramFloat) {
/* 583 */     clampMin(paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(double paramDouble) {
/* 592 */     if (this.x < paramDouble) this.x = paramDouble; 
/* 593 */     if (this.y < paramDouble) this.y = paramDouble; 
/* 594 */     if (this.z < paramDouble) this.z = paramDouble;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float paramFloat) {
/* 603 */     clampMax(paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(double paramDouble) {
/* 612 */     if (this.x > paramDouble) this.x = paramDouble; 
/* 613 */     if (this.y > paramDouble) this.y = paramDouble; 
/* 614 */     if (this.z > paramDouble) this.z = paramDouble;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 623 */     this.x = Math.abs(this.x);
/* 624 */     this.y = Math.abs(this.y);
/* 625 */     this.z = Math.abs(this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Tuple3d paramTuple3d1, Tuple3d paramTuple3d2, float paramFloat) {
/* 633 */     interpolate(paramTuple3d1, paramTuple3d2, paramFloat);
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
/*     */   public final void interpolate(Tuple3d paramTuple3d1, Tuple3d paramTuple3d2, double paramDouble) {
/* 645 */     this.x = (1.0D - paramDouble) * paramTuple3d1.x + paramDouble * paramTuple3d2.x;
/* 646 */     this.y = (1.0D - paramDouble) * paramTuple3d1.y + paramDouble * paramTuple3d2.y;
/* 647 */     this.z = (1.0D - paramDouble) * paramTuple3d1.z + paramDouble * paramTuple3d2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Tuple3d paramTuple3d, float paramFloat) {
/* 655 */     interpolate(paramTuple3d, paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Tuple3d paramTuple3d, double paramDouble) {
/* 666 */     this.x = (1.0D - paramDouble) * this.x + paramDouble * paramTuple3d.x;
/* 667 */     this.y = (1.0D - paramDouble) * this.y + paramDouble * paramTuple3d.y;
/* 668 */     this.z = (1.0D - paramDouble) * this.z + paramDouble * paramTuple3d.z;
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
/* 682 */       return super.clone();
/* 683 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 685 */       throw new InternalError();
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
/* 697 */     return this.x;
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
/* 709 */     this.x = paramDouble;
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
/* 721 */     return this.y;
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
/* 733 */     this.y = paramDouble;
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
/* 744 */     return this.z;
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
/* 756 */     this.z = paramDouble;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Tuple3d.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
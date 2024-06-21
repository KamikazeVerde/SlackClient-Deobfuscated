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
/*     */ public abstract class Tuple2d
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 6205762482756093838L;
/*     */   public double x;
/*     */   public double y;
/*     */   
/*     */   public Tuple2d(double paramDouble1, double paramDouble2) {
/*  63 */     this.x = paramDouble1;
/*  64 */     this.y = paramDouble2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2d(double[] paramArrayOfdouble) {
/*  74 */     this.x = paramArrayOfdouble[0];
/*  75 */     this.y = paramArrayOfdouble[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2d(Tuple2d paramTuple2d) {
/*  85 */     this.x = paramTuple2d.x;
/*  86 */     this.y = paramTuple2d.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2d(Tuple2f paramTuple2f) {
/*  96 */     this.x = paramTuple2f.x;
/*  97 */     this.y = paramTuple2f.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2d() {
/* 105 */     this.x = 0.0D;
/* 106 */     this.y = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double paramDouble1, double paramDouble2) {
/* 117 */     this.x = paramDouble1;
/* 118 */     this.y = paramDouble2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double[] paramArrayOfdouble) {
/* 129 */     this.x = paramArrayOfdouble[0];
/* 130 */     this.y = paramArrayOfdouble[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2d paramTuple2d) {
/* 140 */     this.x = paramTuple2d.x;
/* 141 */     this.y = paramTuple2d.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2f paramTuple2f) {
/* 151 */     this.x = paramTuple2f.x;
/* 152 */     this.y = paramTuple2f.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(double[] paramArrayOfdouble) {
/* 161 */     paramArrayOfdouble[0] = this.x;
/* 162 */     paramArrayOfdouble[1] = this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2d paramTuple2d1, Tuple2d paramTuple2d2) {
/* 173 */     paramTuple2d1.x += paramTuple2d2.x;
/* 174 */     paramTuple2d1.y += paramTuple2d2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2d paramTuple2d) {
/* 184 */     this.x += paramTuple2d.x;
/* 185 */     this.y += paramTuple2d.y;
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
/*     */   public final void sub(Tuple2d paramTuple2d1, Tuple2d paramTuple2d2) {
/* 197 */     paramTuple2d1.x -= paramTuple2d2.x;
/* 198 */     paramTuple2d1.y -= paramTuple2d2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple2d paramTuple2d) {
/* 209 */     this.x -= paramTuple2d.x;
/* 210 */     this.y -= paramTuple2d.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple2d paramTuple2d) {
/* 220 */     this.x = -paramTuple2d.x;
/* 221 */     this.y = -paramTuple2d.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 230 */     this.x = -this.x;
/* 231 */     this.y = -this.y;
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
/*     */   public final void scale(double paramDouble, Tuple2d paramTuple2d) {
/* 243 */     this.x = paramDouble * paramTuple2d.x;
/* 244 */     this.y = paramDouble * paramTuple2d.y;
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
/* 255 */     this.x *= paramDouble;
/* 256 */     this.y *= paramDouble;
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
/*     */   public final void scaleAdd(double paramDouble, Tuple2d paramTuple2d1, Tuple2d paramTuple2d2) {
/* 269 */     this.x = paramDouble * paramTuple2d1.x + paramTuple2d2.x;
/* 270 */     this.y = paramDouble * paramTuple2d1.y + paramTuple2d2.y;
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
/*     */   public final void scaleAdd(double paramDouble, Tuple2d paramTuple2d) {
/* 282 */     this.x = paramDouble * this.x + paramTuple2d.x;
/* 283 */     this.y = paramDouble * this.y + paramTuple2d.y;
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
/* 297 */     long l = 1L;
/* 298 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.x);
/* 299 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.y);
/* 300 */     return (int)(l ^ l >> 32L);
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
/*     */   public boolean equals(Tuple2d paramTuple2d) {
/*     */     try {
/* 313 */       return (this.x == paramTuple2d.x && this.y == paramTuple2d.y);
/*     */     } catch (NullPointerException nullPointerException) {
/* 315 */       return false;
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
/* 329 */     try { Tuple2d tuple2d = (Tuple2d)paramObject;
/* 330 */       return (this.x == tuple2d.x && this.y == tuple2d.y); }
/*     */     catch (NullPointerException nullPointerException)
/* 332 */     { return false; }
/* 333 */     catch (ClassCastException classCastException) { return false; }
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
/*     */   public boolean epsilonEquals(Tuple2d paramTuple2d, double paramDouble) {
/* 350 */     double d = this.x - paramTuple2d.x;
/* 351 */     if (Double.isNaN(d)) return false; 
/* 352 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*     */     
/* 354 */     d = this.y - paramTuple2d.y;
/* 355 */     if (Double.isNaN(d)) return false; 
/* 356 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*     */     
/* 358 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 368 */     return "(" + this.x + ", " + this.y + ")";
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
/*     */   public final void clamp(double paramDouble1, double paramDouble2, Tuple2d paramTuple2d) {
/* 381 */     if (paramTuple2d.x > paramDouble2) {
/* 382 */       this.x = paramDouble2;
/* 383 */     } else if (paramTuple2d.x < paramDouble1) {
/* 384 */       this.x = paramDouble1;
/*     */     } else {
/* 386 */       this.x = paramTuple2d.x;
/*     */     } 
/*     */     
/* 389 */     if (paramTuple2d.y > paramDouble2) {
/* 390 */       this.y = paramDouble2;
/* 391 */     } else if (paramTuple2d.y < paramDouble1) {
/* 392 */       this.y = paramDouble1;
/*     */     } else {
/* 394 */       this.y = paramTuple2d.y;
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
/*     */   public final void clampMin(double paramDouble, Tuple2d paramTuple2d) {
/* 408 */     if (paramTuple2d.x < paramDouble) {
/* 409 */       this.x = paramDouble;
/*     */     } else {
/* 411 */       this.x = paramTuple2d.x;
/*     */     } 
/*     */     
/* 414 */     if (paramTuple2d.y < paramDouble) {
/* 415 */       this.y = paramDouble;
/*     */     } else {
/* 417 */       this.y = paramTuple2d.y;
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
/*     */   public final void clampMax(double paramDouble, Tuple2d paramTuple2d) {
/* 431 */     if (paramTuple2d.x > paramDouble) {
/* 432 */       this.x = paramDouble;
/*     */     } else {
/* 434 */       this.x = paramTuple2d.x;
/*     */     } 
/*     */     
/* 437 */     if (paramTuple2d.y > paramDouble) {
/* 438 */       this.y = paramDouble;
/*     */     } else {
/* 440 */       this.y = paramTuple2d.y;
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
/*     */   public final void absolute(Tuple2d paramTuple2d) {
/* 453 */     this.x = Math.abs(paramTuple2d.x);
/* 454 */     this.y = Math.abs(paramTuple2d.y);
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
/*     */   public final void clamp(double paramDouble1, double paramDouble2) {
/* 466 */     if (this.x > paramDouble2) {
/* 467 */       this.x = paramDouble2;
/* 468 */     } else if (this.x < paramDouble1) {
/* 469 */       this.x = paramDouble1;
/*     */     } 
/*     */     
/* 472 */     if (this.y > paramDouble2) {
/* 473 */       this.y = paramDouble2;
/* 474 */     } else if (this.y < paramDouble1) {
/* 475 */       this.y = paramDouble1;
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
/*     */   public final void clampMin(double paramDouble) {
/* 487 */     if (this.x < paramDouble) this.x = paramDouble; 
/* 488 */     if (this.y < paramDouble) this.y = paramDouble;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(double paramDouble) {
/* 498 */     if (this.x > paramDouble) this.x = paramDouble; 
/* 499 */     if (this.y > paramDouble) this.y = paramDouble;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 508 */     this.x = Math.abs(this.x);
/* 509 */     this.y = Math.abs(this.y);
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
/*     */   public final void interpolate(Tuple2d paramTuple2d1, Tuple2d paramTuple2d2, double paramDouble) {
/* 522 */     this.x = (1.0D - paramDouble) * paramTuple2d1.x + paramDouble * paramTuple2d2.x;
/* 523 */     this.y = (1.0D - paramDouble) * paramTuple2d1.y + paramDouble * paramTuple2d2.y;
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
/*     */   public final void interpolate(Tuple2d paramTuple2d, double paramDouble) {
/* 535 */     this.x = (1.0D - paramDouble) * this.x + paramDouble * paramTuple2d.x;
/* 536 */     this.y = (1.0D - paramDouble) * this.y + paramDouble * paramTuple2d.y;
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
/* 551 */       return super.clone();
/* 552 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 554 */       throw new InternalError();
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
/*     */   public final double getX() {
/* 567 */     return this.x;
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
/* 579 */     this.x = paramDouble;
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
/* 591 */     return this.y;
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
/* 603 */     this.y = paramDouble;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Tuple2d.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
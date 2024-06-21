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
/*     */ public abstract class Tuple2f
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 9011180388985266884L;
/*     */   public float x;
/*     */   public float y;
/*     */   
/*     */   public Tuple2f(float paramFloat1, float paramFloat2) {
/*  63 */     this.x = paramFloat1;
/*  64 */     this.y = paramFloat2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2f(float[] paramArrayOffloat) {
/*  74 */     this.x = paramArrayOffloat[0];
/*  75 */     this.y = paramArrayOffloat[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2f(Tuple2f paramTuple2f) {
/*  85 */     this.x = paramTuple2f.x;
/*  86 */     this.y = paramTuple2f.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2f(Tuple2d paramTuple2d) {
/*  96 */     this.x = (float)paramTuple2d.x;
/*  97 */     this.y = (float)paramTuple2d.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2f() {
/* 106 */     this.x = 0.0F;
/* 107 */     this.y = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(float paramFloat1, float paramFloat2) {
/* 118 */     this.x = paramFloat1;
/* 119 */     this.y = paramFloat2;
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
/* 130 */     this.x = paramArrayOffloat[0];
/* 131 */     this.y = paramArrayOffloat[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2f paramTuple2f) {
/* 141 */     this.x = paramTuple2f.x;
/* 142 */     this.y = paramTuple2f.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2d paramTuple2d) {
/* 152 */     this.x = (float)paramTuple2d.x;
/* 153 */     this.y = (float)paramTuple2d.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(float[] paramArrayOffloat) {
/* 163 */     paramArrayOffloat[0] = this.x;
/* 164 */     paramArrayOffloat[1] = this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2f paramTuple2f1, Tuple2f paramTuple2f2) {
/* 175 */     paramTuple2f1.x += paramTuple2f2.x;
/* 176 */     paramTuple2f1.y += paramTuple2f2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2f paramTuple2f) {
/* 186 */     this.x += paramTuple2f.x;
/* 187 */     this.y += paramTuple2f.y;
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
/*     */   public final void sub(Tuple2f paramTuple2f1, Tuple2f paramTuple2f2) {
/* 199 */     paramTuple2f1.x -= paramTuple2f2.x;
/* 200 */     paramTuple2f1.y -= paramTuple2f2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple2f paramTuple2f) {
/* 211 */     this.x -= paramTuple2f.x;
/* 212 */     this.y -= paramTuple2f.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple2f paramTuple2f) {
/* 222 */     this.x = -paramTuple2f.x;
/* 223 */     this.y = -paramTuple2f.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 232 */     this.x = -this.x;
/* 233 */     this.y = -this.y;
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
/*     */   public final void scale(float paramFloat, Tuple2f paramTuple2f) {
/* 245 */     this.x = paramFloat * paramTuple2f.x;
/* 246 */     this.y = paramFloat * paramTuple2f.y;
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
/* 257 */     this.x *= paramFloat;
/* 258 */     this.y *= paramFloat;
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
/*     */   public final void scaleAdd(float paramFloat, Tuple2f paramTuple2f1, Tuple2f paramTuple2f2) {
/* 271 */     this.x = paramFloat * paramTuple2f1.x + paramTuple2f2.x;
/* 272 */     this.y = paramFloat * paramTuple2f1.y + paramTuple2f2.y;
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
/*     */   public final void scaleAdd(float paramFloat, Tuple2f paramTuple2f) {
/* 284 */     this.x = paramFloat * this.x + paramTuple2f.x;
/* 285 */     this.y = paramFloat * this.y + paramTuple2f.y;
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
/* 299 */     long l = 1L;
/* 300 */     l = 31L * l + VecMathUtil.floatToIntBits(this.x);
/* 301 */     l = 31L * l + VecMathUtil.floatToIntBits(this.y);
/* 302 */     return (int)(l ^ l >> 32L);
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
/*     */   public boolean equals(Tuple2f paramTuple2f) {
/*     */     try {
/* 315 */       return (this.x == paramTuple2f.x && this.y == paramTuple2f.y);
/*     */     } catch (NullPointerException nullPointerException) {
/* 317 */       return false;
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
/* 331 */     try { Tuple2f tuple2f = (Tuple2f)paramObject;
/* 332 */       return (this.x == tuple2f.x && this.y == tuple2f.y); }
/*     */     catch (NullPointerException nullPointerException)
/* 334 */     { return false; }
/* 335 */     catch (ClassCastException classCastException) { return false; }
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
/*     */   public boolean epsilonEquals(Tuple2f paramTuple2f, float paramFloat) {
/* 352 */     float f = this.x - paramTuple2f.x;
/* 353 */     if (Float.isNaN(f)) return false; 
/* 354 */     if (((f < 0.0F) ? -f : f) > paramFloat) return false;
/*     */     
/* 356 */     f = this.y - paramTuple2f.y;
/* 357 */     if (Float.isNaN(f)) return false; 
/* 358 */     if (((f < 0.0F) ? -f : f) > paramFloat) return false;
/*     */     
/* 360 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 370 */     return "(" + this.x + ", " + this.y + ")";
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
/*     */   public final void clamp(float paramFloat1, float paramFloat2, Tuple2f paramTuple2f) {
/* 383 */     if (paramTuple2f.x > paramFloat2) {
/* 384 */       this.x = paramFloat2;
/* 385 */     } else if (paramTuple2f.x < paramFloat1) {
/* 386 */       this.x = paramFloat1;
/*     */     } else {
/* 388 */       this.x = paramTuple2f.x;
/*     */     } 
/*     */     
/* 391 */     if (paramTuple2f.y > paramFloat2) {
/* 392 */       this.y = paramFloat2;
/* 393 */     } else if (paramTuple2f.y < paramFloat1) {
/* 394 */       this.y = paramFloat1;
/*     */     } else {
/* 396 */       this.y = paramTuple2f.y;
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
/*     */   public final void clampMin(float paramFloat, Tuple2f paramTuple2f) {
/* 410 */     if (paramTuple2f.x < paramFloat) {
/* 411 */       this.x = paramFloat;
/*     */     } else {
/* 413 */       this.x = paramTuple2f.x;
/*     */     } 
/*     */     
/* 416 */     if (paramTuple2f.y < paramFloat) {
/* 417 */       this.y = paramFloat;
/*     */     } else {
/* 419 */       this.y = paramTuple2f.y;
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
/*     */   public final void clampMax(float paramFloat, Tuple2f paramTuple2f) {
/* 433 */     if (paramTuple2f.x > paramFloat) {
/* 434 */       this.x = paramFloat;
/*     */     } else {
/* 436 */       this.x = paramTuple2f.x;
/*     */     } 
/*     */     
/* 439 */     if (paramTuple2f.y > paramFloat) {
/* 440 */       this.y = paramFloat;
/*     */     } else {
/* 442 */       this.y = paramTuple2f.y;
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
/*     */   public final void absolute(Tuple2f paramTuple2f) {
/* 455 */     this.x = Math.abs(paramTuple2f.x);
/* 456 */     this.y = Math.abs(paramTuple2f.y);
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
/* 468 */     if (this.x > paramFloat2) {
/* 469 */       this.x = paramFloat2;
/* 470 */     } else if (this.x < paramFloat1) {
/* 471 */       this.x = paramFloat1;
/*     */     } 
/*     */     
/* 474 */     if (this.y > paramFloat2) {
/* 475 */       this.y = paramFloat2;
/* 476 */     } else if (this.y < paramFloat1) {
/* 477 */       this.y = paramFloat1;
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
/* 489 */     if (this.x < paramFloat) this.x = paramFloat; 
/* 490 */     if (this.y < paramFloat) this.y = paramFloat;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float paramFloat) {
/* 500 */     if (this.x > paramFloat) this.x = paramFloat; 
/* 501 */     if (this.y > paramFloat) this.y = paramFloat;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 510 */     this.x = Math.abs(this.x);
/* 511 */     this.y = Math.abs(this.y);
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
/*     */   public final void interpolate(Tuple2f paramTuple2f1, Tuple2f paramTuple2f2, float paramFloat) {
/* 524 */     this.x = (1.0F - paramFloat) * paramTuple2f1.x + paramFloat * paramTuple2f2.x;
/* 525 */     this.y = (1.0F - paramFloat) * paramTuple2f1.y + paramFloat * paramTuple2f2.y;
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
/*     */   public final void interpolate(Tuple2f paramTuple2f, float paramFloat) {
/* 539 */     this.x = (1.0F - paramFloat) * this.x + paramFloat * paramTuple2f.x;
/* 540 */     this.y = (1.0F - paramFloat) * this.y + paramFloat * paramTuple2f.y;
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
/* 555 */       return super.clone();
/* 556 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 558 */       throw new InternalError();
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
/* 571 */     return this.x;
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
/* 583 */     this.x = paramFloat;
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
/* 595 */     return this.y;
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
/* 607 */     this.y = paramFloat;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Tuple2f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
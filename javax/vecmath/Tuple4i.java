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
/*     */ public abstract class Tuple4i
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 8064614250942616720L;
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   public int w;
/*     */   
/*     */   public Tuple4i(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  76 */     this.x = paramInt1;
/*  77 */     this.y = paramInt2;
/*  78 */     this.z = paramInt3;
/*  79 */     this.w = paramInt4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4i(int[] paramArrayOfint) {
/*  88 */     this.x = paramArrayOfint[0];
/*  89 */     this.y = paramArrayOfint[1];
/*  90 */     this.z = paramArrayOfint[2];
/*  91 */     this.w = paramArrayOfint[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4i(Tuple4i paramTuple4i) {
/* 101 */     this.x = paramTuple4i.x;
/* 102 */     this.y = paramTuple4i.y;
/* 103 */     this.z = paramTuple4i.z;
/* 104 */     this.w = paramTuple4i.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4i() {
/* 112 */     this.x = 0;
/* 113 */     this.y = 0;
/* 114 */     this.z = 0;
/* 115 */     this.w = 0;
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
/*     */   public final void set(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 128 */     this.x = paramInt1;
/* 129 */     this.y = paramInt2;
/* 130 */     this.z = paramInt3;
/* 131 */     this.w = paramInt4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(int[] paramArrayOfint) {
/* 141 */     this.x = paramArrayOfint[0];
/* 142 */     this.y = paramArrayOfint[1];
/* 143 */     this.z = paramArrayOfint[2];
/* 144 */     this.w = paramArrayOfint[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4i paramTuple4i) {
/* 153 */     this.x = paramTuple4i.x;
/* 154 */     this.y = paramTuple4i.y;
/* 155 */     this.z = paramTuple4i.z;
/* 156 */     this.w = paramTuple4i.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(int[] paramArrayOfint) {
/* 165 */     paramArrayOfint[0] = this.x;
/* 166 */     paramArrayOfint[1] = this.y;
/* 167 */     paramArrayOfint[2] = this.z;
/* 168 */     paramArrayOfint[3] = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple4i paramTuple4i) {
/* 177 */     paramTuple4i.x = this.x;
/* 178 */     paramTuple4i.y = this.y;
/* 179 */     paramTuple4i.z = this.z;
/* 180 */     paramTuple4i.w = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4i paramTuple4i1, Tuple4i paramTuple4i2) {
/* 190 */     paramTuple4i1.x += paramTuple4i2.x;
/* 191 */     paramTuple4i1.y += paramTuple4i2.y;
/* 192 */     paramTuple4i1.z += paramTuple4i2.z;
/* 193 */     paramTuple4i1.w += paramTuple4i2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4i paramTuple4i) {
/* 202 */     this.x += paramTuple4i.x;
/* 203 */     this.y += paramTuple4i.y;
/* 204 */     this.z += paramTuple4i.z;
/* 205 */     this.w += paramTuple4i.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple4i paramTuple4i1, Tuple4i paramTuple4i2) {
/* 216 */     paramTuple4i1.x -= paramTuple4i2.x;
/* 217 */     paramTuple4i1.y -= paramTuple4i2.y;
/* 218 */     paramTuple4i1.z -= paramTuple4i2.z;
/* 219 */     paramTuple4i1.w -= paramTuple4i2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple4i paramTuple4i) {
/* 229 */     this.x -= paramTuple4i.x;
/* 230 */     this.y -= paramTuple4i.y;
/* 231 */     this.z -= paramTuple4i.z;
/* 232 */     this.w -= paramTuple4i.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple4i paramTuple4i) {
/* 241 */     this.x = -paramTuple4i.x;
/* 242 */     this.y = -paramTuple4i.y;
/* 243 */     this.z = -paramTuple4i.z;
/* 244 */     this.w = -paramTuple4i.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 252 */     this.x = -this.x;
/* 253 */     this.y = -this.y;
/* 254 */     this.z = -this.z;
/* 255 */     this.w = -this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(int paramInt, Tuple4i paramTuple4i) {
/* 266 */     this.x = paramInt * paramTuple4i.x;
/* 267 */     this.y = paramInt * paramTuple4i.y;
/* 268 */     this.z = paramInt * paramTuple4i.z;
/* 269 */     this.w = paramInt * paramTuple4i.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(int paramInt) {
/* 279 */     this.x *= paramInt;
/* 280 */     this.y *= paramInt;
/* 281 */     this.z *= paramInt;
/* 282 */     this.w *= paramInt;
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
/*     */   public final void scaleAdd(int paramInt, Tuple4i paramTuple4i1, Tuple4i paramTuple4i2) {
/* 294 */     this.x = paramInt * paramTuple4i1.x + paramTuple4i2.x;
/* 295 */     this.y = paramInt * paramTuple4i1.y + paramTuple4i2.y;
/* 296 */     this.z = paramInt * paramTuple4i1.z + paramTuple4i2.z;
/* 297 */     this.w = paramInt * paramTuple4i1.w + paramTuple4i2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(int paramInt, Tuple4i paramTuple4i) {
/* 308 */     this.x = paramInt * this.x + paramTuple4i.x;
/* 309 */     this.y = paramInt * this.y + paramTuple4i.y;
/* 310 */     this.z = paramInt * this.z + paramTuple4i.z;
/* 311 */     this.w = paramInt * this.w + paramTuple4i.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 321 */     return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
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
/*     */     try {
/* 334 */       Tuple4i tuple4i = (Tuple4i)paramObject;
/* 335 */       return (this.x == tuple4i.x && this.y == tuple4i.y && this.z == tuple4i.z && this.w == tuple4i.w);
/*     */     
/*     */     }
/* 338 */     catch (NullPointerException nullPointerException) {
/* 339 */       return false;
/*     */     }
/* 341 */     catch (ClassCastException classCastException) {
/* 342 */       return false;
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
/*     */   public int hashCode() {
/* 356 */     long l = 1L;
/* 357 */     l = 31L * l + this.x;
/* 358 */     l = 31L * l + this.y;
/* 359 */     l = 31L * l + this.z;
/* 360 */     l = 31L * l + this.w;
/* 361 */     return (int)(l ^ l >> 32L);
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
/*     */   public final void clamp(int paramInt1, int paramInt2, Tuple4i paramTuple4i) {
/* 373 */     if (paramTuple4i.x > paramInt2) {
/* 374 */       this.x = paramInt2;
/* 375 */     } else if (paramTuple4i.x < paramInt1) {
/* 376 */       this.x = paramInt1;
/*     */     } else {
/* 378 */       this.x = paramTuple4i.x;
/*     */     } 
/*     */     
/* 381 */     if (paramTuple4i.y > paramInt2) {
/* 382 */       this.y = paramInt2;
/* 383 */     } else if (paramTuple4i.y < paramInt1) {
/* 384 */       this.y = paramInt1;
/*     */     } else {
/* 386 */       this.y = paramTuple4i.y;
/*     */     } 
/*     */     
/* 389 */     if (paramTuple4i.z > paramInt2) {
/* 390 */       this.z = paramInt2;
/* 391 */     } else if (paramTuple4i.z < paramInt1) {
/* 392 */       this.z = paramInt1;
/*     */     } else {
/* 394 */       this.z = paramTuple4i.z;
/*     */     } 
/*     */     
/* 397 */     if (paramTuple4i.w > paramInt2) {
/* 398 */       this.w = paramInt2;
/* 399 */     } else if (paramTuple4i.w < paramInt1) {
/* 400 */       this.w = paramInt1;
/*     */     } else {
/* 402 */       this.w = paramTuple4i.w;
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
/*     */   public final void clampMin(int paramInt, Tuple4i paramTuple4i) {
/* 414 */     if (paramTuple4i.x < paramInt) {
/* 415 */       this.x = paramInt;
/*     */     } else {
/* 417 */       this.x = paramTuple4i.x;
/*     */     } 
/*     */     
/* 420 */     if (paramTuple4i.y < paramInt) {
/* 421 */       this.y = paramInt;
/*     */     } else {
/* 423 */       this.y = paramTuple4i.y;
/*     */     } 
/*     */     
/* 426 */     if (paramTuple4i.z < paramInt) {
/* 427 */       this.z = paramInt;
/*     */     } else {
/* 429 */       this.z = paramTuple4i.z;
/*     */     } 
/*     */     
/* 432 */     if (paramTuple4i.w < paramInt) {
/* 433 */       this.w = paramInt;
/*     */     } else {
/* 435 */       this.w = paramTuple4i.w;
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
/*     */   public final void clampMax(int paramInt, Tuple4i paramTuple4i) {
/* 449 */     if (paramTuple4i.x > paramInt) {
/* 450 */       this.x = paramInt;
/*     */     } else {
/* 452 */       this.x = paramTuple4i.x;
/*     */     } 
/*     */     
/* 455 */     if (paramTuple4i.y > paramInt) {
/* 456 */       this.y = paramInt;
/*     */     } else {
/* 458 */       this.y = paramTuple4i.y;
/*     */     } 
/*     */     
/* 461 */     if (paramTuple4i.z > paramInt) {
/* 462 */       this.z = paramInt;
/*     */     } else {
/* 464 */       this.z = paramTuple4i.z;
/*     */     } 
/*     */     
/* 467 */     if (paramTuple4i.w > paramInt) {
/* 468 */       this.w = paramInt;
/*     */     } else {
/* 470 */       this.w = paramTuple4i.z;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute(Tuple4i paramTuple4i) {
/* 481 */     this.x = Math.abs(paramTuple4i.x);
/* 482 */     this.y = Math.abs(paramTuple4i.y);
/* 483 */     this.z = Math.abs(paramTuple4i.z);
/* 484 */     this.w = Math.abs(paramTuple4i.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(int paramInt1, int paramInt2) {
/* 494 */     if (this.x > paramInt2) {
/* 495 */       this.x = paramInt2;
/* 496 */     } else if (this.x < paramInt1) {
/* 497 */       this.x = paramInt1;
/*     */     } 
/*     */     
/* 500 */     if (this.y > paramInt2) {
/* 501 */       this.y = paramInt2;
/* 502 */     } else if (this.y < paramInt1) {
/* 503 */       this.y = paramInt1;
/*     */     } 
/*     */     
/* 506 */     if (this.z > paramInt2) {
/* 507 */       this.z = paramInt2;
/* 508 */     } else if (this.z < paramInt1) {
/* 509 */       this.z = paramInt1;
/*     */     } 
/*     */     
/* 512 */     if (this.w > paramInt2) {
/* 513 */       this.w = paramInt2;
/* 514 */     } else if (this.w < paramInt1) {
/* 515 */       this.w = paramInt1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(int paramInt) {
/* 525 */     if (this.x < paramInt) {
/* 526 */       this.x = paramInt;
/*     */     }
/* 528 */     if (this.y < paramInt) {
/* 529 */       this.y = paramInt;
/*     */     }
/* 531 */     if (this.z < paramInt) {
/* 532 */       this.z = paramInt;
/*     */     }
/* 534 */     if (this.w < paramInt) {
/* 535 */       this.w = paramInt;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(int paramInt) {
/* 544 */     if (this.x > paramInt) {
/* 545 */       this.x = paramInt;
/*     */     }
/* 547 */     if (this.y > paramInt) {
/* 548 */       this.y = paramInt;
/*     */     }
/* 550 */     if (this.z > paramInt) {
/* 551 */       this.z = paramInt;
/*     */     }
/* 553 */     if (this.w > paramInt) {
/* 554 */       this.w = paramInt;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 562 */     this.x = Math.abs(this.x);
/* 563 */     this.y = Math.abs(this.y);
/* 564 */     this.z = Math.abs(this.z);
/* 565 */     this.w = Math.abs(this.w);
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
/* 579 */       return super.clone();
/* 580 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 582 */       throw new InternalError();
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
/*     */   public final int getX() {
/* 596 */     return this.x;
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
/*     */   public final void setX(int paramInt) {
/* 608 */     this.x = paramInt;
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
/*     */   public final int getY() {
/* 620 */     return this.y;
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
/*     */   public final void setY(int paramInt) {
/* 632 */     this.y = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getZ() {
/* 643 */     return this.z;
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
/*     */   public final void setZ(int paramInt) {
/* 655 */     this.z = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getW() {
/* 665 */     return this.w;
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
/*     */   public final void setW(int paramInt) {
/* 677 */     this.w = paramInt;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Tuple4i.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
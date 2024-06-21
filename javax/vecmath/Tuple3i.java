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
/*     */ public abstract class Tuple3i
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -732740491767276200L;
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   
/*     */   public Tuple3i(int paramInt1, int paramInt2, int paramInt3) {
/*  70 */     this.x = paramInt1;
/*  71 */     this.y = paramInt2;
/*  72 */     this.z = paramInt3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3i(int[] paramArrayOfint) {
/*  81 */     this.x = paramArrayOfint[0];
/*  82 */     this.y = paramArrayOfint[1];
/*  83 */     this.z = paramArrayOfint[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3i(Tuple3i paramTuple3i) {
/*  93 */     this.x = paramTuple3i.x;
/*  94 */     this.y = paramTuple3i.y;
/*  95 */     this.z = paramTuple3i.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3i() {
/* 103 */     this.x = 0;
/* 104 */     this.y = 0;
/* 105 */     this.z = 0;
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
/*     */   public final void set(int paramInt1, int paramInt2, int paramInt3) {
/* 117 */     this.x = paramInt1;
/* 118 */     this.y = paramInt2;
/* 119 */     this.z = paramInt3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(int[] paramArrayOfint) {
/* 129 */     this.x = paramArrayOfint[0];
/* 130 */     this.y = paramArrayOfint[1];
/* 131 */     this.z = paramArrayOfint[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3i paramTuple3i) {
/* 140 */     this.x = paramTuple3i.x;
/* 141 */     this.y = paramTuple3i.y;
/* 142 */     this.z = paramTuple3i.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(int[] paramArrayOfint) {
/* 151 */     paramArrayOfint[0] = this.x;
/* 152 */     paramArrayOfint[1] = this.y;
/* 153 */     paramArrayOfint[2] = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple3i paramTuple3i) {
/* 162 */     paramTuple3i.x = this.x;
/* 163 */     paramTuple3i.y = this.y;
/* 164 */     paramTuple3i.z = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3i paramTuple3i1, Tuple3i paramTuple3i2) {
/* 174 */     paramTuple3i1.x += paramTuple3i2.x;
/* 175 */     paramTuple3i1.y += paramTuple3i2.y;
/* 176 */     paramTuple3i1.z += paramTuple3i2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3i paramTuple3i) {
/* 185 */     this.x += paramTuple3i.x;
/* 186 */     this.y += paramTuple3i.y;
/* 187 */     this.z += paramTuple3i.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple3i paramTuple3i1, Tuple3i paramTuple3i2) {
/* 198 */     paramTuple3i1.x -= paramTuple3i2.x;
/* 199 */     paramTuple3i1.y -= paramTuple3i2.y;
/* 200 */     paramTuple3i1.z -= paramTuple3i2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple3i paramTuple3i) {
/* 210 */     this.x -= paramTuple3i.x;
/* 211 */     this.y -= paramTuple3i.y;
/* 212 */     this.z -= paramTuple3i.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple3i paramTuple3i) {
/* 221 */     this.x = -paramTuple3i.x;
/* 222 */     this.y = -paramTuple3i.y;
/* 223 */     this.z = -paramTuple3i.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 231 */     this.x = -this.x;
/* 232 */     this.y = -this.y;
/* 233 */     this.z = -this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(int paramInt, Tuple3i paramTuple3i) {
/* 244 */     this.x = paramInt * paramTuple3i.x;
/* 245 */     this.y = paramInt * paramTuple3i.y;
/* 246 */     this.z = paramInt * paramTuple3i.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(int paramInt) {
/* 256 */     this.x *= paramInt;
/* 257 */     this.y *= paramInt;
/* 258 */     this.z *= paramInt;
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
/*     */   public final void scaleAdd(int paramInt, Tuple3i paramTuple3i1, Tuple3i paramTuple3i2) {
/* 270 */     this.x = paramInt * paramTuple3i1.x + paramTuple3i2.x;
/* 271 */     this.y = paramInt * paramTuple3i1.y + paramTuple3i2.y;
/* 272 */     this.z = paramInt * paramTuple3i1.z + paramTuple3i2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(int paramInt, Tuple3i paramTuple3i) {
/* 283 */     this.x = paramInt * this.x + paramTuple3i.x;
/* 284 */     this.y = paramInt * this.y + paramTuple3i.y;
/* 285 */     this.z = paramInt * this.z + paramTuple3i.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 295 */     return "(" + this.x + ", " + this.y + ", " + this.z + ")";
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
/*     */     try {
/* 307 */       Tuple3i tuple3i = (Tuple3i)paramObject;
/* 308 */       return (this.x == tuple3i.x && this.y == tuple3i.y && this.z == tuple3i.z);
/*     */     }
/* 310 */     catch (NullPointerException nullPointerException) {
/* 311 */       return false;
/*     */     }
/* 313 */     catch (ClassCastException classCastException) {
/* 314 */       return false;
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
/* 328 */     long l = 1L;
/* 329 */     l = 31L * l + this.x;
/* 330 */     l = 31L * l + this.y;
/* 331 */     l = 31L * l + this.z;
/* 332 */     return (int)(l ^ l >> 32L);
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
/*     */   public final void clamp(int paramInt1, int paramInt2, Tuple3i paramTuple3i) {
/* 344 */     if (paramTuple3i.x > paramInt2) {
/* 345 */       this.x = paramInt2;
/* 346 */     } else if (paramTuple3i.x < paramInt1) {
/* 347 */       this.x = paramInt1;
/*     */     } else {
/* 349 */       this.x = paramTuple3i.x;
/*     */     } 
/*     */     
/* 352 */     if (paramTuple3i.y > paramInt2) {
/* 353 */       this.y = paramInt2;
/* 354 */     } else if (paramTuple3i.y < paramInt1) {
/* 355 */       this.y = paramInt1;
/*     */     } else {
/* 357 */       this.y = paramTuple3i.y;
/*     */     } 
/*     */     
/* 360 */     if (paramTuple3i.z > paramInt2) {
/* 361 */       this.z = paramInt2;
/* 362 */     } else if (paramTuple3i.z < paramInt1) {
/* 363 */       this.z = paramInt1;
/*     */     } else {
/* 365 */       this.z = paramTuple3i.z;
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
/*     */   public final void clampMin(int paramInt, Tuple3i paramTuple3i) {
/* 377 */     if (paramTuple3i.x < paramInt) {
/* 378 */       this.x = paramInt;
/*     */     } else {
/* 380 */       this.x = paramTuple3i.x;
/*     */     } 
/*     */     
/* 383 */     if (paramTuple3i.y < paramInt) {
/* 384 */       this.y = paramInt;
/*     */     } else {
/* 386 */       this.y = paramTuple3i.y;
/*     */     } 
/*     */     
/* 389 */     if (paramTuple3i.z < paramInt) {
/* 390 */       this.z = paramInt;
/*     */     } else {
/* 392 */       this.z = paramTuple3i.z;
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
/*     */   public final void clampMax(int paramInt, Tuple3i paramTuple3i) {
/* 404 */     if (paramTuple3i.x > paramInt) {
/* 405 */       this.x = paramInt;
/*     */     } else {
/* 407 */       this.x = paramTuple3i.x;
/*     */     } 
/*     */     
/* 410 */     if (paramTuple3i.y > paramInt) {
/* 411 */       this.y = paramInt;
/*     */     } else {
/* 413 */       this.y = paramTuple3i.y;
/*     */     } 
/*     */     
/* 416 */     if (paramTuple3i.z > paramInt) {
/* 417 */       this.z = paramInt;
/*     */     } else {
/* 419 */       this.z = paramTuple3i.z;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute(Tuple3i paramTuple3i) {
/* 430 */     this.x = Math.abs(paramTuple3i.x);
/* 431 */     this.y = Math.abs(paramTuple3i.y);
/* 432 */     this.z = Math.abs(paramTuple3i.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(int paramInt1, int paramInt2) {
/* 442 */     if (this.x > paramInt2) {
/* 443 */       this.x = paramInt2;
/* 444 */     } else if (this.x < paramInt1) {
/* 445 */       this.x = paramInt1;
/*     */     } 
/*     */     
/* 448 */     if (this.y > paramInt2) {
/* 449 */       this.y = paramInt2;
/* 450 */     } else if (this.y < paramInt1) {
/* 451 */       this.y = paramInt1;
/*     */     } 
/*     */     
/* 454 */     if (this.z > paramInt2) {
/* 455 */       this.z = paramInt2;
/* 456 */     } else if (this.z < paramInt1) {
/* 457 */       this.z = paramInt1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(int paramInt) {
/* 467 */     if (this.x < paramInt) {
/* 468 */       this.x = paramInt;
/*     */     }
/* 470 */     if (this.y < paramInt) {
/* 471 */       this.y = paramInt;
/*     */     }
/* 473 */     if (this.z < paramInt) {
/* 474 */       this.z = paramInt;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(int paramInt) {
/* 483 */     if (this.x > paramInt) {
/* 484 */       this.x = paramInt;
/*     */     }
/* 486 */     if (this.y > paramInt) {
/* 487 */       this.y = paramInt;
/*     */     }
/* 489 */     if (this.z > paramInt) {
/* 490 */       this.z = paramInt;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 498 */     this.x = Math.abs(this.x);
/* 499 */     this.y = Math.abs(this.y);
/* 500 */     this.z = Math.abs(this.z);
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
/* 514 */       return super.clone();
/* 515 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 517 */       throw new InternalError();
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
/*     */   public final int getX() {
/* 530 */     return this.x;
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
/* 542 */     this.x = paramInt;
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
/* 554 */     return this.y;
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
/* 566 */     this.y = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getZ() {
/* 576 */     return this.z;
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
/* 588 */     this.z = paramInt;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Tuple3i.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
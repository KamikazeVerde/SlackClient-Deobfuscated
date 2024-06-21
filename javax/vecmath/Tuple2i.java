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
/*     */ public abstract class Tuple2i
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -3555701650170169638L;
/*     */   public int x;
/*     */   public int y;
/*     */   
/*     */   public Tuple2i(int paramInt1, int paramInt2) {
/*  64 */     this.x = paramInt1;
/*  65 */     this.y = paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2i(int[] paramArrayOfint) {
/*  74 */     this.x = paramArrayOfint[0];
/*  75 */     this.y = paramArrayOfint[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2i(Tuple2i paramTuple2i) {
/*  85 */     this.x = paramTuple2i.x;
/*  86 */     this.y = paramTuple2i.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2i() {
/*  94 */     this.x = 0;
/*  95 */     this.y = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(int paramInt1, int paramInt2) {
/* 106 */     this.x = paramInt1;
/* 107 */     this.y = paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(int[] paramArrayOfint) {
/* 117 */     this.x = paramArrayOfint[0];
/* 118 */     this.y = paramArrayOfint[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2i paramTuple2i) {
/* 127 */     this.x = paramTuple2i.x;
/* 128 */     this.y = paramTuple2i.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(int[] paramArrayOfint) {
/* 137 */     paramArrayOfint[0] = this.x;
/* 138 */     paramArrayOfint[1] = this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple2i paramTuple2i) {
/* 147 */     paramTuple2i.x = this.x;
/* 148 */     paramTuple2i.y = this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2i paramTuple2i1, Tuple2i paramTuple2i2) {
/* 158 */     paramTuple2i1.x += paramTuple2i2.x;
/* 159 */     paramTuple2i1.y += paramTuple2i2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2i paramTuple2i) {
/* 168 */     this.x += paramTuple2i.x;
/* 169 */     this.y += paramTuple2i.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple2i paramTuple2i1, Tuple2i paramTuple2i2) {
/* 180 */     paramTuple2i1.x -= paramTuple2i2.x;
/* 181 */     paramTuple2i1.y -= paramTuple2i2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple2i paramTuple2i) {
/* 191 */     this.x -= paramTuple2i.x;
/* 192 */     this.y -= paramTuple2i.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple2i paramTuple2i) {
/* 201 */     this.x = -paramTuple2i.x;
/* 202 */     this.y = -paramTuple2i.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 210 */     this.x = -this.x;
/* 211 */     this.y = -this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(int paramInt, Tuple2i paramTuple2i) {
/* 222 */     this.x = paramInt * paramTuple2i.x;
/* 223 */     this.y = paramInt * paramTuple2i.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(int paramInt) {
/* 233 */     this.x *= paramInt;
/* 234 */     this.y *= paramInt;
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
/*     */   public final void scaleAdd(int paramInt, Tuple2i paramTuple2i1, Tuple2i paramTuple2i2) {
/* 246 */     this.x = paramInt * paramTuple2i1.x + paramTuple2i2.x;
/* 247 */     this.y = paramInt * paramTuple2i1.y + paramTuple2i2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(int paramInt, Tuple2i paramTuple2i) {
/* 258 */     this.x = paramInt * this.x + paramTuple2i.x;
/* 259 */     this.y = paramInt * this.y + paramTuple2i.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 269 */     return "(" + this.x + ", " + this.y + ")";
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
/* 281 */       Tuple2i tuple2i = (Tuple2i)paramObject;
/* 282 */       return (this.x == tuple2i.x && this.y == tuple2i.y);
/*     */     }
/* 284 */     catch (NullPointerException nullPointerException) {
/* 285 */       return false;
/*     */     }
/* 287 */     catch (ClassCastException classCastException) {
/* 288 */       return false;
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
/* 302 */     long l = 1L;
/* 303 */     l = 31L * l + this.x;
/* 304 */     l = 31L * l + this.y;
/* 305 */     return (int)(l ^ l >> 32L);
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
/*     */   public final void clamp(int paramInt1, int paramInt2, Tuple2i paramTuple2i) {
/* 317 */     if (paramTuple2i.x > paramInt2) {
/* 318 */       this.x = paramInt2;
/* 319 */     } else if (paramTuple2i.x < paramInt1) {
/* 320 */       this.x = paramInt1;
/*     */     } else {
/* 322 */       this.x = paramTuple2i.x;
/*     */     } 
/*     */     
/* 325 */     if (paramTuple2i.y > paramInt2) {
/* 326 */       this.y = paramInt2;
/* 327 */     } else if (paramTuple2i.y < paramInt1) {
/* 328 */       this.y = paramInt1;
/*     */     } else {
/* 330 */       this.y = paramTuple2i.y;
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
/*     */   public final void clampMin(int paramInt, Tuple2i paramTuple2i) {
/* 342 */     if (paramTuple2i.x < paramInt) {
/* 343 */       this.x = paramInt;
/*     */     } else {
/* 345 */       this.x = paramTuple2i.x;
/*     */     } 
/*     */     
/* 348 */     if (paramTuple2i.y < paramInt) {
/* 349 */       this.y = paramInt;
/*     */     } else {
/* 351 */       this.y = paramTuple2i.y;
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
/*     */   public final void clampMax(int paramInt, Tuple2i paramTuple2i) {
/* 363 */     if (paramTuple2i.x > paramInt) {
/* 364 */       this.x = paramInt;
/*     */     } else {
/* 366 */       this.x = paramTuple2i.x;
/*     */     } 
/*     */     
/* 369 */     if (paramTuple2i.y > paramInt) {
/* 370 */       this.y = paramInt;
/*     */     } else {
/* 372 */       this.y = paramTuple2i.y;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute(Tuple2i paramTuple2i) {
/* 383 */     this.x = Math.abs(paramTuple2i.x);
/* 384 */     this.y = Math.abs(paramTuple2i.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(int paramInt1, int paramInt2) {
/* 394 */     if (this.x > paramInt2) {
/* 395 */       this.x = paramInt2;
/* 396 */     } else if (this.x < paramInt1) {
/* 397 */       this.x = paramInt1;
/*     */     } 
/*     */     
/* 400 */     if (this.y > paramInt2) {
/* 401 */       this.y = paramInt2;
/* 402 */     } else if (this.y < paramInt1) {
/* 403 */       this.y = paramInt1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(int paramInt) {
/* 413 */     if (this.x < paramInt) {
/* 414 */       this.x = paramInt;
/*     */     }
/* 416 */     if (this.y < paramInt) {
/* 417 */       this.y = paramInt;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(int paramInt) {
/* 426 */     if (this.x > paramInt) {
/* 427 */       this.x = paramInt;
/*     */     }
/* 429 */     if (this.y > paramInt) {
/* 430 */       this.y = paramInt;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 438 */     this.x = Math.abs(this.x);
/* 439 */     this.y = Math.abs(this.y);
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
/*     */   public Object clone() {
/*     */     try {
/* 452 */       return super.clone();
/* 453 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 455 */       throw new InternalError();
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
/* 468 */     return this.x;
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
/* 480 */     this.x = paramInt;
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
/* 492 */     return this.y;
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
/* 504 */     this.y = paramInt;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Tuple2i.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
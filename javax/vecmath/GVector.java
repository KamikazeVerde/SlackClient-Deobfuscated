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
/*     */ public class GVector
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private int length;
/*     */   double[] values;
/*     */   static final long serialVersionUID = 1398850036893875112L;
/*     */   
/*     */   public GVector(int paramInt) {
/*  58 */     this.length = paramInt;
/*  59 */     this.values = new double[paramInt];
/*  60 */     for (byte b = 0; b < paramInt; ) { this.values[b] = 0.0D; b++; }
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
/*     */   public GVector(double[] paramArrayOfdouble) {
/*  74 */     this.length = paramArrayOfdouble.length;
/*  75 */     this.values = new double[paramArrayOfdouble.length];
/*  76 */     for (byte b = 0; b < this.length; ) { this.values[b] = paramArrayOfdouble[b]; b++; }
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
/*     */   public GVector(GVector paramGVector) {
/*  88 */     this.values = new double[paramGVector.length];
/*  89 */     this.length = paramGVector.length;
/*  90 */     for (byte b = 0; b < this.length; ) { this.values[b] = paramGVector.values[b]; b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple2f paramTuple2f) {
/* 100 */     this.values = new double[2];
/* 101 */     this.values[0] = paramTuple2f.x;
/* 102 */     this.values[1] = paramTuple2f.y;
/* 103 */     this.length = 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple3f paramTuple3f) {
/* 113 */     this.values = new double[3];
/* 114 */     this.values[0] = paramTuple3f.x;
/* 115 */     this.values[1] = paramTuple3f.y;
/* 116 */     this.values[2] = paramTuple3f.z;
/* 117 */     this.length = 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple3d paramTuple3d) {
/* 127 */     this.values = new double[3];
/* 128 */     this.values[0] = paramTuple3d.x;
/* 129 */     this.values[1] = paramTuple3d.y;
/* 130 */     this.values[2] = paramTuple3d.z;
/* 131 */     this.length = 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple4f paramTuple4f) {
/* 141 */     this.values = new double[4];
/* 142 */     this.values[0] = paramTuple4f.x;
/* 143 */     this.values[1] = paramTuple4f.y;
/* 144 */     this.values[2] = paramTuple4f.z;
/* 145 */     this.values[3] = paramTuple4f.w;
/* 146 */     this.length = 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple4d paramTuple4d) {
/* 156 */     this.values = new double[4];
/* 157 */     this.values[0] = paramTuple4d.x;
/* 158 */     this.values[1] = paramTuple4d.y;
/* 159 */     this.values[2] = paramTuple4d.z;
/* 160 */     this.values[3] = paramTuple4d.w;
/* 161 */     this.length = 4;
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
/*     */   public GVector(double[] paramArrayOfdouble, int paramInt) {
/* 177 */     this.length = paramInt;
/* 178 */     this.values = new double[paramInt];
/* 179 */     for (byte b = 0; b < paramInt; b++) {
/* 180 */       this.values[b] = paramArrayOfdouble[b];
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
/*     */   public final double norm() {
/* 192 */     double d = 0.0D;
/*     */ 
/*     */     
/* 195 */     for (byte b = 0; b < this.length; b++) {
/* 196 */       d += this.values[b] * this.values[b];
/*     */     }
/*     */     
/* 199 */     return Math.sqrt(d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double normSquared() {
/* 210 */     double d = 0.0D;
/*     */ 
/*     */     
/* 213 */     for (byte b = 0; b < this.length; b++) {
/* 214 */       d += this.values[b] * this.values[b];
/*     */     }
/*     */     
/* 217 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize(GVector paramGVector) {
/* 226 */     double d1 = 0.0D;
/*     */ 
/*     */     
/* 229 */     if (this.length != paramGVector.length)
/* 230 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector0")); 
/*     */     byte b;
/* 232 */     for (b = 0; b < this.length; b++) {
/* 233 */       d1 += paramGVector.values[b] * paramGVector.values[b];
/*     */     }
/*     */ 
/*     */     
/* 237 */     double d2 = 1.0D / Math.sqrt(d1);
/*     */     
/* 239 */     for (b = 0; b < this.length; b++) {
/* 240 */       this.values[b] = paramGVector.values[b] * d2;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize() {
/* 250 */     double d1 = 0.0D;
/*     */     
/*     */     byte b;
/* 253 */     for (b = 0; b < this.length; b++) {
/* 254 */       d1 += this.values[b] * this.values[b];
/*     */     }
/*     */ 
/*     */     
/* 258 */     double d2 = 1.0D / Math.sqrt(d1);
/*     */     
/* 260 */     for (b = 0; b < this.length; b++) {
/* 261 */       this.values[b] = this.values[b] * d2;
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
/*     */   public final void scale(double paramDouble, GVector paramGVector) {
/* 275 */     if (this.length != paramGVector.length) {
/* 276 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector1"));
/*     */     }
/* 278 */     for (byte b = 0; b < this.length; b++) {
/* 279 */       this.values[b] = paramGVector.values[b] * paramDouble;
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
/*     */   public final void scale(double paramDouble) {
/* 291 */     for (byte b = 0; b < this.length; b++) {
/* 292 */       this.values[b] = this.values[b] * paramDouble;
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
/*     */   public final void scaleAdd(double paramDouble, GVector paramGVector1, GVector paramGVector2) {
/* 308 */     if (paramGVector2.length != paramGVector1.length) {
/* 309 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector2"));
/*     */     }
/* 311 */     if (this.length != paramGVector1.length) {
/* 312 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector3"));
/*     */     }
/* 314 */     for (byte b = 0; b < this.length; b++) {
/* 315 */       this.values[b] = paramGVector1.values[b] * paramDouble + paramGVector2.values[b];
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
/*     */   public final void add(GVector paramGVector) {
/* 328 */     if (this.length != paramGVector.length) {
/* 329 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector4"));
/*     */     }
/* 331 */     for (byte b = 0; b < this.length; b++) {
/* 332 */       this.values[b] = this.values[b] + paramGVector.values[b];
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
/*     */   public final void add(GVector paramGVector1, GVector paramGVector2) {
/* 346 */     if (paramGVector1.length != paramGVector2.length) {
/* 347 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector5"));
/*     */     }
/* 349 */     if (this.length != paramGVector1.length) {
/* 350 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector6"));
/*     */     }
/* 352 */     for (byte b = 0; b < this.length; b++) {
/* 353 */       this.values[b] = paramGVector1.values[b] + paramGVector2.values[b];
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
/*     */   public final void sub(GVector paramGVector) {
/* 365 */     if (this.length != paramGVector.length) {
/* 366 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector7"));
/*     */     }
/* 368 */     for (byte b = 0; b < this.length; b++) {
/* 369 */       this.values[b] = this.values[b] - paramGVector.values[b];
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
/*     */   public final void sub(GVector paramGVector1, GVector paramGVector2) {
/* 384 */     if (paramGVector1.length != paramGVector2.length) {
/* 385 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector8"));
/*     */     }
/* 387 */     if (this.length != paramGVector1.length) {
/* 388 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector9"));
/*     */     }
/* 390 */     for (byte b = 0; b < this.length; b++) {
/* 391 */       this.values[b] = paramGVector1.values[b] - paramGVector2.values[b];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void mul(GMatrix paramGMatrix, GVector paramGVector) {
/*     */     double[] arrayOfDouble;
/* 401 */     if (paramGMatrix.getNumCol() != paramGVector.length) {
/* 402 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector10"));
/*     */     }
/* 404 */     if (this.length != paramGMatrix.getNumRow()) {
/* 405 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector11"));
/*     */     }
/*     */     
/* 408 */     if (paramGVector != this) {
/* 409 */       arrayOfDouble = paramGVector.values;
/*     */     } else {
/* 411 */       arrayOfDouble = (double[])this.values.clone();
/*     */     } 
/*     */     
/* 414 */     for (int i = this.length - 1; i >= 0; i--) {
/* 415 */       this.values[i] = 0.0D;
/* 416 */       for (int j = paramGVector.length - 1; j >= 0; j--) {
/* 417 */         this.values[i] = this.values[i] + paramGMatrix.values[i][j] * arrayOfDouble[j];
/*     */       }
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
/*     */   public final void mul(GVector paramGVector, GMatrix paramGMatrix) {
/*     */     double[] arrayOfDouble;
/* 433 */     if (paramGMatrix.getNumRow() != paramGVector.length) {
/* 434 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector12"));
/*     */     }
/* 436 */     if (this.length != paramGMatrix.getNumCol()) {
/* 437 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector13"));
/*     */     }
/*     */     
/* 440 */     if (paramGVector != this) {
/* 441 */       arrayOfDouble = paramGVector.values;
/*     */     } else {
/* 443 */       arrayOfDouble = (double[])this.values.clone();
/*     */     } 
/*     */     
/* 446 */     for (int i = this.length - 1; i >= 0; i--) {
/* 447 */       this.values[i] = 0.0D;
/* 448 */       for (int j = paramGVector.length - 1; j >= 0; j--) {
/* 449 */         this.values[i] = this.values[i] + paramGMatrix.values[j][i] * arrayOfDouble[j];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 458 */     for (int i = this.length - 1; i >= 0; i--) {
/* 459 */       this.values[i] = this.values[i] * -1.0D;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void zero() {
/* 467 */     for (byte b = 0; b < this.length; b++) {
/* 468 */       this.values[b] = 0.0D;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setSize(int paramInt) {
/*     */     int i;
/* 479 */     double[] arrayOfDouble = new double[paramInt];
/*     */ 
/*     */     
/* 482 */     if (this.length < paramInt) {
/* 483 */       i = this.length;
/*     */     } else {
/* 485 */       i = paramInt;
/*     */     } 
/* 487 */     for (byte b = 0; b < i; b++) {
/* 488 */       arrayOfDouble[b] = this.values[b];
/*     */     }
/* 490 */     this.length = paramInt;
/*     */     
/* 492 */     this.values = arrayOfDouble;
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
/* 503 */     for (int i = this.length - 1; i >= 0; i--) {
/* 504 */       this.values[i] = paramArrayOfdouble[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(GVector paramGVector) {
/* 514 */     if (this.length < paramGVector.length) {
/* 515 */       this.length = paramGVector.length;
/* 516 */       this.values = new double[this.length];
/* 517 */       for (byte b = 0; b < this.length; b++)
/* 518 */         this.values[b] = paramGVector.values[b]; 
/*     */     } else {
/* 520 */       int i; for (i = 0; i < paramGVector.length; i++)
/* 521 */         this.values[i] = paramGVector.values[i]; 
/* 522 */       for (i = paramGVector.length; i < this.length; i++) {
/* 523 */         this.values[i] = 0.0D;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2f paramTuple2f) {
/* 533 */     if (this.length < 2) {
/* 534 */       this.length = 2;
/* 535 */       this.values = new double[2];
/*     */     } 
/* 537 */     this.values[0] = paramTuple2f.x;
/* 538 */     this.values[1] = paramTuple2f.y;
/* 539 */     for (byte b = 2; b < this.length; ) { this.values[b] = 0.0D; b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3f paramTuple3f) {
/* 549 */     if (this.length < 3) {
/* 550 */       this.length = 3;
/* 551 */       this.values = new double[3];
/*     */     } 
/* 553 */     this.values[0] = paramTuple3f.x;
/* 554 */     this.values[1] = paramTuple3f.y;
/* 555 */     this.values[2] = paramTuple3f.z;
/* 556 */     for (byte b = 3; b < this.length; ) { this.values[b] = 0.0D; b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3d paramTuple3d) {
/* 565 */     if (this.length < 3) {
/* 566 */       this.length = 3;
/* 567 */       this.values = new double[3];
/*     */     } 
/* 569 */     this.values[0] = paramTuple3d.x;
/* 570 */     this.values[1] = paramTuple3d.y;
/* 571 */     this.values[2] = paramTuple3d.z;
/* 572 */     for (byte b = 3; b < this.length; ) { this.values[b] = 0.0D; b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4f paramTuple4f) {
/* 581 */     if (this.length < 4) {
/* 582 */       this.length = 4;
/* 583 */       this.values = new double[4];
/*     */     } 
/* 585 */     this.values[0] = paramTuple4f.x;
/* 586 */     this.values[1] = paramTuple4f.y;
/* 587 */     this.values[2] = paramTuple4f.z;
/* 588 */     this.values[3] = paramTuple4f.w;
/* 589 */     for (byte b = 4; b < this.length; ) { this.values[b] = 0.0D; b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4d paramTuple4d) {
/* 598 */     if (this.length < 4) {
/* 599 */       this.length = 4;
/* 600 */       this.values = new double[4];
/*     */     } 
/* 602 */     this.values[0] = paramTuple4d.x;
/* 603 */     this.values[1] = paramTuple4d.y;
/* 604 */     this.values[2] = paramTuple4d.z;
/* 605 */     this.values[3] = paramTuple4d.w;
/* 606 */     for (byte b = 4; b < this.length; ) { this.values[b] = 0.0D; b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getSize() {
/* 615 */     return this.values.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getElement(int paramInt) {
/* 625 */     return this.values[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setElement(int paramInt, double paramDouble) {
/* 636 */     this.values[paramInt] = paramDouble;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 644 */     StringBuffer stringBuffer = new StringBuffer(this.length * 8);
/*     */ 
/*     */ 
/*     */     
/* 648 */     for (byte b = 0; b < this.length; b++) {
/* 649 */       stringBuffer.append(this.values[b]).append(" ");
/*     */     }
/*     */     
/* 652 */     return stringBuffer.toString();
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
/*     */   public int hashCode() {
/* 667 */     long l = 1L;
/*     */     
/* 669 */     for (byte b = 0; b < this.length; b++) {
/* 670 */       l = 31L * l + VecMathUtil.doubleToLongBits(this.values[b]);
/*     */     }
/*     */     
/* 673 */     return (int)(l ^ l >> 32L);
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
/*     */   public boolean equals(GVector paramGVector) {
/*     */     try {
/* 686 */       if (this.length != paramGVector.length) return false;
/*     */       
/* 688 */       for (byte b = 0; b < this.length; b++) {
/* 689 */         if (this.values[b] != paramGVector.values[b]) return false;
/*     */       
/*     */       } 
/* 692 */       return true;
/*     */     } catch (NullPointerException nullPointerException) {
/* 694 */       return false;
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
/* 707 */     try { GVector gVector = (GVector)paramObject;
/*     */       
/* 709 */       if (this.length != gVector.length) return false;
/*     */       
/* 711 */       for (byte b = 0; b < this.length; b++) {
/* 712 */         if (this.values[b] != gVector.values[b]) return false; 
/*     */       } 
/* 714 */       return true; }
/*     */     catch (ClassCastException classCastException)
/* 716 */     { return false; }
/* 717 */     catch (NullPointerException nullPointerException) { return false; }
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
/*     */   public boolean epsilonEquals(GVector paramGVector, double paramDouble) {
/* 734 */     if (this.length != paramGVector.length) return false;
/*     */     
/* 736 */     for (byte b = 0; b < this.length; b++) {
/* 737 */       double d = this.values[b] - paramGVector.values[b];
/* 738 */       if (((d < 0.0D) ? -d : d) > paramDouble) return false; 
/*     */     } 
/* 740 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double dot(GVector paramGVector) {
/* 750 */     if (this.length != paramGVector.length) {
/* 751 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector14"));
/*     */     }
/* 753 */     double d = 0.0D;
/* 754 */     for (byte b = 0; b < this.length; b++) {
/* 755 */       d += this.values[b] * paramGVector.values[b];
/*     */     }
/* 757 */     return d;
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
/*     */   public final void SVDBackSolve(GMatrix paramGMatrix1, GMatrix paramGMatrix2, GMatrix paramGMatrix3, GVector paramGVector) {
/* 774 */     if (paramGMatrix1.nRow != paramGVector.getSize() || paramGMatrix1.nRow != paramGMatrix1.nCol || paramGMatrix1.nRow != paramGMatrix2.nRow)
/*     */     {
/*     */       
/* 777 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector15"));
/*     */     }
/*     */     
/* 780 */     if (paramGMatrix2.nCol != this.values.length || paramGMatrix2.nCol != paramGMatrix3.nCol || paramGMatrix2.nCol != paramGMatrix3.nRow)
/*     */     {
/*     */       
/* 783 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector23"));
/*     */     }
/*     */     
/* 786 */     GMatrix gMatrix = new GMatrix(paramGMatrix1.nRow, paramGMatrix2.nCol);
/* 787 */     gMatrix.mul(paramGMatrix1, paramGMatrix3);
/* 788 */     gMatrix.mulTransposeRight(paramGMatrix1, paramGMatrix2);
/* 789 */     gMatrix.invert();
/* 790 */     mul(gMatrix, paramGVector);
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
/*     */   
/*     */   public final void LUDBackSolve(GMatrix paramGMatrix, GVector paramGVector1, GVector paramGVector2) {
/* 808 */     int i = paramGMatrix.nRow * paramGMatrix.nCol;
/*     */     
/* 810 */     double[] arrayOfDouble1 = new double[i];
/* 811 */     double[] arrayOfDouble2 = new double[i];
/* 812 */     int[] arrayOfInt = new int[paramGVector1.getSize()];
/*     */ 
/*     */     
/* 815 */     if (paramGMatrix.nRow != paramGVector1.getSize()) {
/* 816 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector16"));
/*     */     }
/*     */     
/* 819 */     if (paramGMatrix.nRow != paramGVector2.getSize()) {
/* 820 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector24"));
/*     */     }
/*     */     
/* 823 */     if (paramGMatrix.nRow != paramGMatrix.nCol) {
/* 824 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector25"));
/*     */     }
/*     */     byte b;
/* 827 */     for (b = 0; b < paramGMatrix.nRow; b++) {
/* 828 */       for (byte b1 = 0; b1 < paramGMatrix.nCol; b1++) {
/* 829 */         arrayOfDouble1[b * paramGMatrix.nCol + b1] = paramGMatrix.values[b][b1];
/*     */       }
/*     */     } 
/*     */     
/* 833 */     for (b = 0; b < i; ) { arrayOfDouble2[b] = 0.0D; b++; }
/* 834 */      for (b = 0; b < paramGMatrix.nRow; ) { arrayOfDouble2[b * paramGMatrix.nCol] = paramGVector1.values[b]; b++; }
/* 835 */      for (b = 0; b < paramGMatrix.nCol; ) { arrayOfInt[b] = (int)paramGVector2.values[b]; b++; }
/*     */     
/* 837 */     GMatrix.luBacksubstitution(paramGMatrix.nRow, arrayOfDouble1, arrayOfInt, arrayOfDouble2);
/*     */     
/* 839 */     for (b = 0; b < paramGMatrix.nRow; ) { this.values[b] = arrayOfDouble2[b * paramGMatrix.nCol]; b++; }
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
/*     */   public final double angle(GVector paramGVector) {
/* 851 */     return Math.acos(dot(paramGVector) / norm() * paramGVector.norm());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(GVector paramGVector1, GVector paramGVector2, float paramFloat) {
/* 859 */     interpolate(paramGVector1, paramGVector2, paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(GVector paramGVector, float paramFloat) {
/* 867 */     interpolate(paramGVector, paramFloat);
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
/*     */   public final void interpolate(GVector paramGVector1, GVector paramGVector2, double paramDouble) {
/* 880 */     if (paramGVector2.length != paramGVector1.length) {
/* 881 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector20"));
/*     */     }
/* 883 */     if (this.length != paramGVector1.length) {
/* 884 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector21"));
/*     */     }
/* 886 */     for (byte b = 0; b < this.length; b++) {
/* 887 */       this.values[b] = (1.0D - paramDouble) * paramGVector1.values[b] + paramDouble * paramGVector2.values[b];
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
/*     */   public final void interpolate(GVector paramGVector, double paramDouble) {
/* 899 */     if (paramGVector.length != this.length) {
/* 900 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector22"));
/*     */     }
/* 902 */     for (byte b = 0; b < this.length; b++) {
/* 903 */       this.values[b] = (1.0D - paramDouble) * this.values[b] + paramDouble * paramGVector.values[b];
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
/*     */   public Object clone() {
/* 916 */     GVector gVector = null;
/*     */     try {
/* 918 */       gVector = (GVector)super.clone();
/* 919 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 921 */       throw new InternalError();
/*     */     } 
/*     */ 
/*     */     
/* 925 */     gVector.values = new double[this.length];
/* 926 */     for (byte b = 0; b < this.length; b++) {
/* 927 */       gVector.values[b] = this.values[b];
/*     */     }
/*     */     
/* 930 */     return gVector;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\GVector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
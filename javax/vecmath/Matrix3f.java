/*      */ package javax.vecmath;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Matrix3f
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   static final long serialVersionUID = 329697160112089834L;
/*      */   public float m00;
/*      */   public float m01;
/*      */   public float m02;
/*      */   public float m10;
/*      */   public float m11;
/*      */   public float m12;
/*      */   public float m20;
/*      */   public float m21;
/*      */   public float m22;
/*      */   private static final double EPS = 1.0E-8D;
/*      */   
/*      */   public Matrix3f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9) {
/*  115 */     this.m00 = paramFloat1;
/*  116 */     this.m01 = paramFloat2;
/*  117 */     this.m02 = paramFloat3;
/*      */     
/*  119 */     this.m10 = paramFloat4;
/*  120 */     this.m11 = paramFloat5;
/*  121 */     this.m12 = paramFloat6;
/*      */     
/*  123 */     this.m20 = paramFloat7;
/*  124 */     this.m21 = paramFloat8;
/*  125 */     this.m22 = paramFloat9;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3f(float[] paramArrayOffloat) {
/*  136 */     this.m00 = paramArrayOffloat[0];
/*  137 */     this.m01 = paramArrayOffloat[1];
/*  138 */     this.m02 = paramArrayOffloat[2];
/*      */     
/*  140 */     this.m10 = paramArrayOffloat[3];
/*  141 */     this.m11 = paramArrayOffloat[4];
/*  142 */     this.m12 = paramArrayOffloat[5];
/*      */     
/*  144 */     this.m20 = paramArrayOffloat[6];
/*  145 */     this.m21 = paramArrayOffloat[7];
/*  146 */     this.m22 = paramArrayOffloat[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3f(Matrix3d paramMatrix3d) {
/*  157 */     this.m00 = (float)paramMatrix3d.m00;
/*  158 */     this.m01 = (float)paramMatrix3d.m01;
/*  159 */     this.m02 = (float)paramMatrix3d.m02;
/*      */     
/*  161 */     this.m10 = (float)paramMatrix3d.m10;
/*  162 */     this.m11 = (float)paramMatrix3d.m11;
/*  163 */     this.m12 = (float)paramMatrix3d.m12;
/*      */     
/*  165 */     this.m20 = (float)paramMatrix3d.m20;
/*  166 */     this.m21 = (float)paramMatrix3d.m21;
/*  167 */     this.m22 = (float)paramMatrix3d.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3f(Matrix3f paramMatrix3f) {
/*  179 */     this.m00 = paramMatrix3f.m00;
/*  180 */     this.m01 = paramMatrix3f.m01;
/*  181 */     this.m02 = paramMatrix3f.m02;
/*      */     
/*  183 */     this.m10 = paramMatrix3f.m10;
/*  184 */     this.m11 = paramMatrix3f.m11;
/*  185 */     this.m12 = paramMatrix3f.m12;
/*      */     
/*  187 */     this.m20 = paramMatrix3f.m20;
/*  188 */     this.m21 = paramMatrix3f.m21;
/*  189 */     this.m22 = paramMatrix3f.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3f() {
/*  199 */     this.m00 = 0.0F;
/*  200 */     this.m01 = 0.0F;
/*  201 */     this.m02 = 0.0F;
/*      */     
/*  203 */     this.m10 = 0.0F;
/*  204 */     this.m11 = 0.0F;
/*  205 */     this.m12 = 0.0F;
/*      */     
/*  207 */     this.m20 = 0.0F;
/*  208 */     this.m21 = 0.0F;
/*  209 */     this.m22 = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  218 */     return this.m00 + ", " + this.m01 + ", " + this.m02 + "\n" + this.m10 + ", " + this.m11 + ", " + this.m12 + "\n" + this.m20 + ", " + this.m21 + ", " + this.m22 + "\n";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setIdentity() {
/*  229 */     this.m00 = 1.0F;
/*  230 */     this.m01 = 0.0F;
/*  231 */     this.m02 = 0.0F;
/*      */     
/*  233 */     this.m10 = 0.0F;
/*  234 */     this.m11 = 1.0F;
/*  235 */     this.m12 = 0.0F;
/*      */     
/*  237 */     this.m20 = 0.0F;
/*  238 */     this.m21 = 0.0F;
/*  239 */     this.m22 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setScale(float paramFloat) {
/*  250 */     double[] arrayOfDouble1 = new double[9];
/*  251 */     double[] arrayOfDouble2 = new double[3];
/*      */     
/*  253 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/*  255 */     this.m00 = (float)(arrayOfDouble1[0] * paramFloat);
/*  256 */     this.m01 = (float)(arrayOfDouble1[1] * paramFloat);
/*  257 */     this.m02 = (float)(arrayOfDouble1[2] * paramFloat);
/*      */     
/*  259 */     this.m10 = (float)(arrayOfDouble1[3] * paramFloat);
/*  260 */     this.m11 = (float)(arrayOfDouble1[4] * paramFloat);
/*  261 */     this.m12 = (float)(arrayOfDouble1[5] * paramFloat);
/*      */     
/*  263 */     this.m20 = (float)(arrayOfDouble1[6] * paramFloat);
/*  264 */     this.m21 = (float)(arrayOfDouble1[7] * paramFloat);
/*  265 */     this.m22 = (float)(arrayOfDouble1[8] * paramFloat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setElement(int paramInt1, int paramInt2, float paramFloat) {
/*  277 */     switch (paramInt1) {
/*      */       
/*      */       case 0:
/*  280 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  283 */             this.m00 = paramFloat;
/*      */             return;
/*      */           case 1:
/*  286 */             this.m01 = paramFloat;
/*      */             return;
/*      */           case 2:
/*  289 */             this.m02 = paramFloat;
/*      */             return;
/*      */         } 
/*  292 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  297 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  300 */             this.m10 = paramFloat;
/*      */             return;
/*      */           case 1:
/*  303 */             this.m11 = paramFloat;
/*      */             return;
/*      */           case 2:
/*  306 */             this.m12 = paramFloat;
/*      */             return;
/*      */         } 
/*  309 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  314 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  317 */             this.m20 = paramFloat;
/*      */             return;
/*      */           case 1:
/*  320 */             this.m21 = paramFloat;
/*      */             return;
/*      */           case 2:
/*  323 */             this.m22 = paramFloat;
/*      */             return;
/*      */         } 
/*      */         
/*  327 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  332 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int paramInt, Vector3f paramVector3f) {
/*  342 */     if (paramInt == 0) {
/*  343 */       paramVector3f.x = this.m00;
/*  344 */       paramVector3f.y = this.m01;
/*  345 */       paramVector3f.z = this.m02;
/*  346 */     } else if (paramInt == 1) {
/*  347 */       paramVector3f.x = this.m10;
/*  348 */       paramVector3f.y = this.m11;
/*  349 */       paramVector3f.z = this.m12;
/*  350 */     } else if (paramInt == 2) {
/*  351 */       paramVector3f.x = this.m20;
/*  352 */       paramVector3f.y = this.m21;
/*  353 */       paramVector3f.z = this.m22;
/*      */     } else {
/*  355 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f1"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int paramInt, float[] paramArrayOffloat) {
/*  366 */     if (paramInt == 0) {
/*  367 */       paramArrayOffloat[0] = this.m00;
/*  368 */       paramArrayOffloat[1] = this.m01;
/*  369 */       paramArrayOffloat[2] = this.m02;
/*  370 */     } else if (paramInt == 1) {
/*  371 */       paramArrayOffloat[0] = this.m10;
/*  372 */       paramArrayOffloat[1] = this.m11;
/*  373 */       paramArrayOffloat[2] = this.m12;
/*  374 */     } else if (paramInt == 2) {
/*  375 */       paramArrayOffloat[0] = this.m20;
/*  376 */       paramArrayOffloat[1] = this.m21;
/*  377 */       paramArrayOffloat[2] = this.m22;
/*      */     } else {
/*  379 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f1"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getColumn(int paramInt, Vector3f paramVector3f) {
/*  391 */     if (paramInt == 0) {
/*  392 */       paramVector3f.x = this.m00;
/*  393 */       paramVector3f.y = this.m10;
/*  394 */       paramVector3f.z = this.m20;
/*  395 */     } else if (paramInt == 1) {
/*  396 */       paramVector3f.x = this.m01;
/*  397 */       paramVector3f.y = this.m11;
/*  398 */       paramVector3f.z = this.m21;
/*  399 */     } else if (paramInt == 2) {
/*  400 */       paramVector3f.x = this.m02;
/*  401 */       paramVector3f.y = this.m12;
/*  402 */       paramVector3f.z = this.m22;
/*      */     } else {
/*  404 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f3"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getColumn(int paramInt, float[] paramArrayOffloat) {
/*  416 */     if (paramInt == 0) {
/*  417 */       paramArrayOffloat[0] = this.m00;
/*  418 */       paramArrayOffloat[1] = this.m10;
/*  419 */       paramArrayOffloat[2] = this.m20;
/*  420 */     } else if (paramInt == 1) {
/*  421 */       paramArrayOffloat[0] = this.m01;
/*  422 */       paramArrayOffloat[1] = this.m11;
/*  423 */       paramArrayOffloat[2] = this.m21;
/*  424 */     } else if (paramInt == 2) {
/*  425 */       paramArrayOffloat[0] = this.m02;
/*  426 */       paramArrayOffloat[1] = this.m12;
/*  427 */       paramArrayOffloat[2] = this.m22;
/*      */     } else {
/*  429 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f3"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getElement(int paramInt1, int paramInt2) {
/*  442 */     switch (paramInt1) {
/*      */       
/*      */       case 0:
/*  445 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  448 */             return this.m00;
/*      */           case 1:
/*  450 */             return this.m01;
/*      */           case 2:
/*  452 */             return this.m02;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/*  458 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  461 */             return this.m10;
/*      */           case 1:
/*  463 */             return this.m11;
/*      */           case 2:
/*  465 */             return this.m12;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/*  472 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  475 */             return this.m20;
/*      */           case 1:
/*  477 */             return this.m21;
/*      */           case 2:
/*  479 */             return this.m22;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  488 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f5"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3) {
/*  500 */     switch (paramInt) {
/*      */       case 0:
/*  502 */         this.m00 = paramFloat1;
/*  503 */         this.m01 = paramFloat2;
/*  504 */         this.m02 = paramFloat3;
/*      */         return;
/*      */       
/*      */       case 1:
/*  508 */         this.m10 = paramFloat1;
/*  509 */         this.m11 = paramFloat2;
/*  510 */         this.m12 = paramFloat3;
/*      */         return;
/*      */       
/*      */       case 2:
/*  514 */         this.m20 = paramFloat1;
/*  515 */         this.m21 = paramFloat2;
/*  516 */         this.m22 = paramFloat3;
/*      */         return;
/*      */     } 
/*      */     
/*  520 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int paramInt, Vector3f paramVector3f) {
/*  531 */     switch (paramInt) {
/*      */       case 0:
/*  533 */         this.m00 = paramVector3f.x;
/*  534 */         this.m01 = paramVector3f.y;
/*  535 */         this.m02 = paramVector3f.z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  539 */         this.m10 = paramVector3f.x;
/*  540 */         this.m11 = paramVector3f.y;
/*  541 */         this.m12 = paramVector3f.z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  545 */         this.m20 = paramVector3f.x;
/*  546 */         this.m21 = paramVector3f.y;
/*  547 */         this.m22 = paramVector3f.z;
/*      */         return;
/*      */     } 
/*      */     
/*  551 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int paramInt, float[] paramArrayOffloat) {
/*  562 */     switch (paramInt) {
/*      */       case 0:
/*  564 */         this.m00 = paramArrayOffloat[0];
/*  565 */         this.m01 = paramArrayOffloat[1];
/*  566 */         this.m02 = paramArrayOffloat[2];
/*      */         return;
/*      */       
/*      */       case 1:
/*  570 */         this.m10 = paramArrayOffloat[0];
/*  571 */         this.m11 = paramArrayOffloat[1];
/*  572 */         this.m12 = paramArrayOffloat[2];
/*      */         return;
/*      */       
/*      */       case 2:
/*  576 */         this.m20 = paramArrayOffloat[0];
/*  577 */         this.m21 = paramArrayOffloat[1];
/*  578 */         this.m22 = paramArrayOffloat[2];
/*      */         return;
/*      */     } 
/*      */     
/*  582 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3) {
/*  595 */     switch (paramInt) {
/*      */       case 0:
/*  597 */         this.m00 = paramFloat1;
/*  598 */         this.m10 = paramFloat2;
/*  599 */         this.m20 = paramFloat3;
/*      */         return;
/*      */       
/*      */       case 1:
/*  603 */         this.m01 = paramFloat1;
/*  604 */         this.m11 = paramFloat2;
/*  605 */         this.m21 = paramFloat3;
/*      */         return;
/*      */       
/*      */       case 2:
/*  609 */         this.m02 = paramFloat1;
/*  610 */         this.m12 = paramFloat2;
/*  611 */         this.m22 = paramFloat3;
/*      */         return;
/*      */     } 
/*      */     
/*  615 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int paramInt, Vector3f paramVector3f) {
/*  626 */     switch (paramInt) {
/*      */       case 0:
/*  628 */         this.m00 = paramVector3f.x;
/*  629 */         this.m10 = paramVector3f.y;
/*  630 */         this.m20 = paramVector3f.z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  634 */         this.m01 = paramVector3f.x;
/*  635 */         this.m11 = paramVector3f.y;
/*  636 */         this.m21 = paramVector3f.z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  640 */         this.m02 = paramVector3f.x;
/*  641 */         this.m12 = paramVector3f.y;
/*  642 */         this.m22 = paramVector3f.z;
/*      */         return;
/*      */     } 
/*      */     
/*  646 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int paramInt, float[] paramArrayOffloat) {
/*  657 */     switch (paramInt) {
/*      */       case 0:
/*  659 */         this.m00 = paramArrayOffloat[0];
/*  660 */         this.m10 = paramArrayOffloat[1];
/*  661 */         this.m20 = paramArrayOffloat[2];
/*      */         return;
/*      */       
/*      */       case 1:
/*  665 */         this.m01 = paramArrayOffloat[0];
/*  666 */         this.m11 = paramArrayOffloat[1];
/*  667 */         this.m21 = paramArrayOffloat[2];
/*      */         return;
/*      */       
/*      */       case 2:
/*  671 */         this.m02 = paramArrayOffloat[0];
/*  672 */         this.m12 = paramArrayOffloat[1];
/*  673 */         this.m22 = paramArrayOffloat[2];
/*      */         return;
/*      */     } 
/*      */     
/*  677 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getScale() {
/*  691 */     double[] arrayOfDouble1 = new double[9];
/*  692 */     double[] arrayOfDouble2 = new double[3];
/*  693 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/*  695 */     return (float)Matrix3d.max3(arrayOfDouble2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(float paramFloat) {
/*  705 */     this.m00 += paramFloat;
/*  706 */     this.m01 += paramFloat;
/*  707 */     this.m02 += paramFloat;
/*  708 */     this.m10 += paramFloat;
/*  709 */     this.m11 += paramFloat;
/*  710 */     this.m12 += paramFloat;
/*  711 */     this.m20 += paramFloat;
/*  712 */     this.m21 += paramFloat;
/*  713 */     this.m22 += paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(float paramFloat, Matrix3f paramMatrix3f) {
/*  724 */     paramMatrix3f.m00 += paramFloat;
/*  725 */     paramMatrix3f.m01 += paramFloat;
/*  726 */     paramMatrix3f.m02 += paramFloat;
/*  727 */     paramMatrix3f.m10 += paramFloat;
/*  728 */     paramMatrix3f.m11 += paramFloat;
/*  729 */     paramMatrix3f.m12 += paramFloat;
/*  730 */     paramMatrix3f.m20 += paramFloat;
/*  731 */     paramMatrix3f.m21 += paramFloat;
/*  732 */     paramMatrix3f.m22 += paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2) {
/*  742 */     paramMatrix3f1.m00 += paramMatrix3f2.m00;
/*  743 */     paramMatrix3f1.m01 += paramMatrix3f2.m01;
/*  744 */     paramMatrix3f1.m02 += paramMatrix3f2.m02;
/*      */     
/*  746 */     paramMatrix3f1.m10 += paramMatrix3f2.m10;
/*  747 */     paramMatrix3f1.m11 += paramMatrix3f2.m11;
/*  748 */     paramMatrix3f1.m12 += paramMatrix3f2.m12;
/*      */     
/*  750 */     paramMatrix3f1.m20 += paramMatrix3f2.m20;
/*  751 */     paramMatrix3f1.m21 += paramMatrix3f2.m21;
/*  752 */     paramMatrix3f1.m22 += paramMatrix3f2.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix3f paramMatrix3f) {
/*  762 */     this.m00 += paramMatrix3f.m00;
/*  763 */     this.m01 += paramMatrix3f.m01;
/*  764 */     this.m02 += paramMatrix3f.m02;
/*      */     
/*  766 */     this.m10 += paramMatrix3f.m10;
/*  767 */     this.m11 += paramMatrix3f.m11;
/*  768 */     this.m12 += paramMatrix3f.m12;
/*      */     
/*  770 */     this.m20 += paramMatrix3f.m20;
/*  771 */     this.m21 += paramMatrix3f.m21;
/*  772 */     this.m22 += paramMatrix3f.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2) {
/*  783 */     paramMatrix3f1.m00 -= paramMatrix3f2.m00;
/*  784 */     paramMatrix3f1.m01 -= paramMatrix3f2.m01;
/*  785 */     paramMatrix3f1.m02 -= paramMatrix3f2.m02;
/*      */     
/*  787 */     paramMatrix3f1.m10 -= paramMatrix3f2.m10;
/*  788 */     paramMatrix3f1.m11 -= paramMatrix3f2.m11;
/*  789 */     paramMatrix3f1.m12 -= paramMatrix3f2.m12;
/*      */     
/*  791 */     paramMatrix3f1.m20 -= paramMatrix3f2.m20;
/*  792 */     paramMatrix3f1.m21 -= paramMatrix3f2.m21;
/*  793 */     paramMatrix3f1.m22 -= paramMatrix3f2.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix3f paramMatrix3f) {
/*  803 */     this.m00 -= paramMatrix3f.m00;
/*  804 */     this.m01 -= paramMatrix3f.m01;
/*  805 */     this.m02 -= paramMatrix3f.m02;
/*      */     
/*  807 */     this.m10 -= paramMatrix3f.m10;
/*  808 */     this.m11 -= paramMatrix3f.m11;
/*  809 */     this.m12 -= paramMatrix3f.m12;
/*      */     
/*  811 */     this.m20 -= paramMatrix3f.m20;
/*  812 */     this.m21 -= paramMatrix3f.m21;
/*  813 */     this.m22 -= paramMatrix3f.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose() {
/*  823 */     float f = this.m10;
/*  824 */     this.m10 = this.m01;
/*  825 */     this.m01 = f;
/*      */     
/*  827 */     f = this.m20;
/*  828 */     this.m20 = this.m02;
/*  829 */     this.m02 = f;
/*      */     
/*  831 */     f = this.m21;
/*  832 */     this.m21 = this.m12;
/*  833 */     this.m12 = f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose(Matrix3f paramMatrix3f) {
/*  842 */     if (this != paramMatrix3f) {
/*  843 */       this.m00 = paramMatrix3f.m00;
/*  844 */       this.m01 = paramMatrix3f.m10;
/*  845 */       this.m02 = paramMatrix3f.m20;
/*      */       
/*  847 */       this.m10 = paramMatrix3f.m01;
/*  848 */       this.m11 = paramMatrix3f.m11;
/*  849 */       this.m12 = paramMatrix3f.m21;
/*      */       
/*  851 */       this.m20 = paramMatrix3f.m02;
/*  852 */       this.m21 = paramMatrix3f.m12;
/*  853 */       this.m22 = paramMatrix3f.m22;
/*      */     } else {
/*  855 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4f paramQuat4f) {
/*  865 */     this.m00 = 1.0F - 2.0F * paramQuat4f.y * paramQuat4f.y - 2.0F * paramQuat4f.z * paramQuat4f.z;
/*  866 */     this.m10 = 2.0F * (paramQuat4f.x * paramQuat4f.y + paramQuat4f.w * paramQuat4f.z);
/*  867 */     this.m20 = 2.0F * (paramQuat4f.x * paramQuat4f.z - paramQuat4f.w * paramQuat4f.y);
/*      */     
/*  869 */     this.m01 = 2.0F * (paramQuat4f.x * paramQuat4f.y - paramQuat4f.w * paramQuat4f.z);
/*  870 */     this.m11 = 1.0F - 2.0F * paramQuat4f.x * paramQuat4f.x - 2.0F * paramQuat4f.z * paramQuat4f.z;
/*  871 */     this.m21 = 2.0F * (paramQuat4f.y * paramQuat4f.z + paramQuat4f.w * paramQuat4f.x);
/*      */     
/*  873 */     this.m02 = 2.0F * (paramQuat4f.x * paramQuat4f.z + paramQuat4f.w * paramQuat4f.y);
/*  874 */     this.m12 = 2.0F * (paramQuat4f.y * paramQuat4f.z - paramQuat4f.w * paramQuat4f.x);
/*  875 */     this.m22 = 1.0F - 2.0F * paramQuat4f.x * paramQuat4f.x - 2.0F * paramQuat4f.y * paramQuat4f.y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4f paramAxisAngle4f) {
/*  885 */     float f = (float)Math.sqrt((paramAxisAngle4f.x * paramAxisAngle4f.x + paramAxisAngle4f.y * paramAxisAngle4f.y + paramAxisAngle4f.z * paramAxisAngle4f.z));
/*  886 */     if (f < 1.0E-8D) {
/*  887 */       this.m00 = 1.0F;
/*  888 */       this.m01 = 0.0F;
/*  889 */       this.m02 = 0.0F;
/*      */       
/*  891 */       this.m10 = 0.0F;
/*  892 */       this.m11 = 1.0F;
/*  893 */       this.m12 = 0.0F;
/*      */       
/*  895 */       this.m20 = 0.0F;
/*  896 */       this.m21 = 0.0F;
/*  897 */       this.m22 = 1.0F;
/*      */     } else {
/*  899 */       f = 1.0F / f;
/*  900 */       float f1 = paramAxisAngle4f.x * f;
/*  901 */       float f2 = paramAxisAngle4f.y * f;
/*  902 */       float f3 = paramAxisAngle4f.z * f;
/*      */       
/*  904 */       float f4 = (float)Math.sin(paramAxisAngle4f.angle);
/*  905 */       float f5 = (float)Math.cos(paramAxisAngle4f.angle);
/*  906 */       float f6 = 1.0F - f5;
/*      */       
/*  908 */       float f7 = f1 * f3;
/*  909 */       float f8 = f1 * f2;
/*  910 */       float f9 = f2 * f3;
/*      */       
/*  912 */       this.m00 = f6 * f1 * f1 + f5;
/*  913 */       this.m01 = f6 * f8 - f4 * f3;
/*  914 */       this.m02 = f6 * f7 + f4 * f2;
/*      */       
/*  916 */       this.m10 = f6 * f8 + f4 * f3;
/*  917 */       this.m11 = f6 * f2 * f2 + f5;
/*  918 */       this.m12 = f6 * f9 - f4 * f1;
/*      */       
/*  920 */       this.m20 = f6 * f7 - f4 * f2;
/*  921 */       this.m21 = f6 * f9 + f4 * f1;
/*  922 */       this.m22 = f6 * f3 * f3 + f5;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4d paramAxisAngle4d) {
/*  934 */     double d = Math.sqrt(paramAxisAngle4d.x * paramAxisAngle4d.x + paramAxisAngle4d.y * paramAxisAngle4d.y + paramAxisAngle4d.z * paramAxisAngle4d.z);
/*  935 */     if (d < 1.0E-8D) {
/*  936 */       this.m00 = 1.0F;
/*  937 */       this.m01 = 0.0F;
/*  938 */       this.m02 = 0.0F;
/*      */       
/*  940 */       this.m10 = 0.0F;
/*  941 */       this.m11 = 1.0F;
/*  942 */       this.m12 = 0.0F;
/*      */       
/*  944 */       this.m20 = 0.0F;
/*  945 */       this.m21 = 0.0F;
/*  946 */       this.m22 = 1.0F;
/*      */     } else {
/*  948 */       d = 1.0D / d;
/*  949 */       double d1 = paramAxisAngle4d.x * d;
/*  950 */       double d2 = paramAxisAngle4d.y * d;
/*  951 */       double d3 = paramAxisAngle4d.z * d;
/*      */       
/*  953 */       double d4 = Math.sin(paramAxisAngle4d.angle);
/*  954 */       double d5 = Math.cos(paramAxisAngle4d.angle);
/*  955 */       double d6 = 1.0D - d5;
/*      */       
/*  957 */       double d7 = d1 * d3;
/*  958 */       double d8 = d1 * d2;
/*  959 */       double d9 = d2 * d3;
/*      */       
/*  961 */       this.m00 = (float)(d6 * d1 * d1 + d5);
/*  962 */       this.m01 = (float)(d6 * d8 - d4 * d3);
/*  963 */       this.m02 = (float)(d6 * d7 + d4 * d2);
/*      */       
/*  965 */       this.m10 = (float)(d6 * d8 + d4 * d3);
/*  966 */       this.m11 = (float)(d6 * d2 * d2 + d5);
/*  967 */       this.m12 = (float)(d6 * d9 - d4 * d1);
/*      */       
/*  969 */       this.m20 = (float)(d6 * d7 - d4 * d2);
/*  970 */       this.m21 = (float)(d6 * d9 + d4 * d1);
/*  971 */       this.m22 = (float)(d6 * d3 * d3 + d5);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4d paramQuat4d) {
/*  983 */     this.m00 = (float)(1.0D - 2.0D * paramQuat4d.y * paramQuat4d.y - 2.0D * paramQuat4d.z * paramQuat4d.z);
/*  984 */     this.m10 = (float)(2.0D * (paramQuat4d.x * paramQuat4d.y + paramQuat4d.w * paramQuat4d.z));
/*  985 */     this.m20 = (float)(2.0D * (paramQuat4d.x * paramQuat4d.z - paramQuat4d.w * paramQuat4d.y));
/*      */     
/*  987 */     this.m01 = (float)(2.0D * (paramQuat4d.x * paramQuat4d.y - paramQuat4d.w * paramQuat4d.z));
/*  988 */     this.m11 = (float)(1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.z * paramQuat4d.z);
/*  989 */     this.m21 = (float)(2.0D * (paramQuat4d.y * paramQuat4d.z + paramQuat4d.w * paramQuat4d.x));
/*      */     
/*  991 */     this.m02 = (float)(2.0D * (paramQuat4d.x * paramQuat4d.z + paramQuat4d.w * paramQuat4d.y));
/*  992 */     this.m12 = (float)(2.0D * (paramQuat4d.y * paramQuat4d.z - paramQuat4d.w * paramQuat4d.x));
/*  993 */     this.m22 = (float)(1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.y * paramQuat4d.y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(float[] paramArrayOffloat) {
/* 1004 */     this.m00 = paramArrayOffloat[0];
/* 1005 */     this.m01 = paramArrayOffloat[1];
/* 1006 */     this.m02 = paramArrayOffloat[2];
/*      */     
/* 1008 */     this.m10 = paramArrayOffloat[3];
/* 1009 */     this.m11 = paramArrayOffloat[4];
/* 1010 */     this.m12 = paramArrayOffloat[5];
/*      */     
/* 1012 */     this.m20 = paramArrayOffloat[6];
/* 1013 */     this.m21 = paramArrayOffloat[7];
/* 1014 */     this.m22 = paramArrayOffloat[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3f paramMatrix3f) {
/* 1026 */     this.m00 = paramMatrix3f.m00;
/* 1027 */     this.m01 = paramMatrix3f.m01;
/* 1028 */     this.m02 = paramMatrix3f.m02;
/*      */     
/* 1030 */     this.m10 = paramMatrix3f.m10;
/* 1031 */     this.m11 = paramMatrix3f.m11;
/* 1032 */     this.m12 = paramMatrix3f.m12;
/*      */     
/* 1034 */     this.m20 = paramMatrix3f.m20;
/* 1035 */     this.m21 = paramMatrix3f.m21;
/* 1036 */     this.m22 = paramMatrix3f.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3d paramMatrix3d) {
/* 1048 */     this.m00 = (float)paramMatrix3d.m00;
/* 1049 */     this.m01 = (float)paramMatrix3d.m01;
/* 1050 */     this.m02 = (float)paramMatrix3d.m02;
/*      */     
/* 1052 */     this.m10 = (float)paramMatrix3d.m10;
/* 1053 */     this.m11 = (float)paramMatrix3d.m11;
/* 1054 */     this.m12 = (float)paramMatrix3d.m12;
/*      */     
/* 1056 */     this.m20 = (float)paramMatrix3d.m20;
/* 1057 */     this.m21 = (float)paramMatrix3d.m21;
/* 1058 */     this.m22 = (float)paramMatrix3d.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert(Matrix3f paramMatrix3f) {
/* 1070 */     invertGeneral(paramMatrix3f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/* 1078 */     invertGeneral(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void invertGeneral(Matrix3f paramMatrix3f) {
/* 1090 */     double[] arrayOfDouble1 = new double[9];
/* 1091 */     double[] arrayOfDouble2 = new double[9];
/* 1092 */     int[] arrayOfInt = new int[3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1099 */     arrayOfDouble1[0] = paramMatrix3f.m00;
/* 1100 */     arrayOfDouble1[1] = paramMatrix3f.m01;
/* 1101 */     arrayOfDouble1[2] = paramMatrix3f.m02;
/*      */     
/* 1103 */     arrayOfDouble1[3] = paramMatrix3f.m10;
/* 1104 */     arrayOfDouble1[4] = paramMatrix3f.m11;
/* 1105 */     arrayOfDouble1[5] = paramMatrix3f.m12;
/*      */     
/* 1107 */     arrayOfDouble1[6] = paramMatrix3f.m20;
/* 1108 */     arrayOfDouble1[7] = paramMatrix3f.m21;
/* 1109 */     arrayOfDouble1[8] = paramMatrix3f.m22;
/*      */ 
/*      */ 
/*      */     
/* 1113 */     if (!luDecomposition(arrayOfDouble1, arrayOfInt))
/*      */     {
/* 1115 */       throw new SingularMatrixException(VecMathI18N.getString("Matrix3f12"));
/*      */     }
/*      */ 
/*      */     
/* 1119 */     for (byte b = 0; b < 9; ) { arrayOfDouble2[b] = 0.0D; b++; }
/* 1120 */      arrayOfDouble2[0] = 1.0D; arrayOfDouble2[4] = 1.0D; arrayOfDouble2[8] = 1.0D;
/* 1121 */     luBacksubstitution(arrayOfDouble1, arrayOfInt, arrayOfDouble2);
/*      */     
/* 1123 */     this.m00 = (float)arrayOfDouble2[0];
/* 1124 */     this.m01 = (float)arrayOfDouble2[1];
/* 1125 */     this.m02 = (float)arrayOfDouble2[2];
/*      */     
/* 1127 */     this.m10 = (float)arrayOfDouble2[3];
/* 1128 */     this.m11 = (float)arrayOfDouble2[4];
/* 1129 */     this.m12 = (float)arrayOfDouble2[5];
/*      */     
/* 1131 */     this.m20 = (float)arrayOfDouble2[6];
/* 1132 */     this.m21 = (float)arrayOfDouble2[7];
/* 1133 */     this.m22 = (float)arrayOfDouble2[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean luDecomposition(double[] paramArrayOfdouble, int[] paramArrayOfint) {
/* 1160 */     double[] arrayOfDouble = new double[3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1168 */     int j = 0;
/* 1169 */     int k = 0;
/*      */ 
/*      */     
/* 1172 */     int i = 3;
/* 1173 */     while (i-- != 0) {
/* 1174 */       double d = 0.0D;
/*      */ 
/*      */       
/* 1177 */       int m = 3;
/* 1178 */       while (m-- != 0) {
/* 1179 */         double d1 = paramArrayOfdouble[j++];
/* 1180 */         d1 = Math.abs(d1);
/* 1181 */         if (d1 > d) {
/* 1182 */           d = d1;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1187 */       if (d == 0.0D) {
/* 1188 */         return false;
/*      */       }
/* 1190 */       arrayOfDouble[k++] = 1.0D / d;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1198 */     byte b = 0;
/*      */ 
/*      */     
/* 1201 */     for (i = 0; i < 3; i++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1207 */       for (j = 0; j < i; j++) {
/* 1208 */         int n = b + 3 * j + i;
/* 1209 */         double d1 = paramArrayOfdouble[n];
/* 1210 */         int m = j;
/* 1211 */         int i1 = b + 3 * j;
/* 1212 */         int i2 = b + i;
/* 1213 */         while (m-- != 0) {
/* 1214 */           d1 -= paramArrayOfdouble[i1] * paramArrayOfdouble[i2];
/* 1215 */           i1++;
/* 1216 */           i2 += 3;
/*      */         } 
/* 1218 */         paramArrayOfdouble[n] = d1;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1223 */       double d = 0.0D;
/* 1224 */       k = -1;
/* 1225 */       for (j = i; j < 3; j++) {
/* 1226 */         int n = b + 3 * j + i;
/* 1227 */         double d1 = paramArrayOfdouble[n];
/* 1228 */         int m = i;
/* 1229 */         int i1 = b + 3 * j;
/* 1230 */         int i2 = b + i;
/* 1231 */         while (m-- != 0) {
/* 1232 */           d1 -= paramArrayOfdouble[i1] * paramArrayOfdouble[i2];
/* 1233 */           i1++;
/* 1234 */           i2 += 3;
/*      */         } 
/* 1236 */         paramArrayOfdouble[n] = d1;
/*      */         
/*      */         double d2;
/* 1239 */         if ((d2 = arrayOfDouble[j] * Math.abs(d1)) >= d) {
/* 1240 */           d = d2;
/* 1241 */           k = j;
/*      */         } 
/*      */       } 
/*      */       
/* 1245 */       if (k < 0) {
/* 1246 */         throw new RuntimeException(VecMathI18N.getString("Matrix3f13"));
/*      */       }
/*      */ 
/*      */       
/* 1250 */       if (i != k) {
/*      */         
/* 1252 */         int m = 3;
/* 1253 */         int n = b + 3 * k;
/* 1254 */         int i1 = b + 3 * i;
/* 1255 */         while (m-- != 0) {
/* 1256 */           double d1 = paramArrayOfdouble[n];
/* 1257 */           paramArrayOfdouble[n++] = paramArrayOfdouble[i1];
/* 1258 */           paramArrayOfdouble[i1++] = d1;
/*      */         } 
/*      */ 
/*      */         
/* 1262 */         arrayOfDouble[k] = arrayOfDouble[i];
/*      */       } 
/*      */ 
/*      */       
/* 1266 */       paramArrayOfint[i] = k;
/*      */ 
/*      */       
/* 1269 */       if (paramArrayOfdouble[b + 3 * i + i] == 0.0D) {
/* 1270 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1274 */       if (i != 2) {
/* 1275 */         double d1 = 1.0D / paramArrayOfdouble[b + 3 * i + i];
/* 1276 */         int m = b + 3 * (i + 1) + i;
/* 1277 */         j = 2 - i;
/* 1278 */         while (j-- != 0) {
/* 1279 */           paramArrayOfdouble[m] = paramArrayOfdouble[m] * d1;
/* 1280 */           m += 3;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1286 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void luBacksubstitution(double[] paramArrayOfdouble1, int[] paramArrayOfint, double[] paramArrayOfdouble2) {
/* 1316 */     byte b2 = 0;
/*      */ 
/*      */     
/* 1319 */     for (byte b1 = 0; b1 < 3; b1++) {
/*      */       
/* 1321 */       byte b4 = b1;
/* 1322 */       byte b = -1;
/*      */ 
/*      */       
/* 1325 */       for (byte b3 = 0; b3 < 3; b3++) {
/*      */ 
/*      */         
/* 1328 */         int i = paramArrayOfint[b2 + b3];
/* 1329 */         double d = paramArrayOfdouble2[b4 + 3 * i];
/* 1330 */         paramArrayOfdouble2[b4 + 3 * i] = paramArrayOfdouble2[b4 + 3 * b3];
/* 1331 */         if (b >= 0) {
/*      */           
/* 1333 */           int j = b3 * 3;
/* 1334 */           for (byte b6 = b; b6 <= b3 - 1; b6++) {
/* 1335 */             d -= paramArrayOfdouble1[j + b6] * paramArrayOfdouble2[b4 + 3 * b6];
/*      */           }
/*      */         }
/* 1338 */         else if (d != 0.0D) {
/* 1339 */           b = b3;
/*      */         } 
/* 1341 */         paramArrayOfdouble2[b4 + 3 * b3] = d;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1346 */       byte b5 = 6;
/* 1347 */       paramArrayOfdouble2[b4 + 6] = paramArrayOfdouble2[b4 + 6] / paramArrayOfdouble1[b5 + 2];
/*      */       
/* 1349 */       b5 -= 3;
/* 1350 */       paramArrayOfdouble2[b4 + 3] = (paramArrayOfdouble2[b4 + 3] - paramArrayOfdouble1[b5 + 2] * paramArrayOfdouble2[b4 + 6]) / paramArrayOfdouble1[b5 + 1];
/*      */ 
/*      */       
/* 1353 */       b5 -= 3;
/* 1354 */       paramArrayOfdouble2[b4 + 0] = (paramArrayOfdouble2[b4 + 0] - paramArrayOfdouble1[b5 + 1] * paramArrayOfdouble2[b4 + 3] - paramArrayOfdouble1[b5 + 2] * paramArrayOfdouble2[b4 + 6]) / paramArrayOfdouble1[b5 + 0];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float determinant() {
/* 1367 */     return this.m00 * (this.m11 * this.m22 - this.m12 * this.m21) + this.m01 * (this.m12 * this.m20 - this.m10 * this.m22) + this.m02 * (this.m10 * this.m21 - this.m11 * this.m20);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(float paramFloat) {
/* 1380 */     this.m00 = paramFloat;
/* 1381 */     this.m01 = 0.0F;
/* 1382 */     this.m02 = 0.0F;
/*      */     
/* 1384 */     this.m10 = 0.0F;
/* 1385 */     this.m11 = paramFloat;
/* 1386 */     this.m12 = 0.0F;
/*      */     
/* 1388 */     this.m20 = 0.0F;
/* 1389 */     this.m21 = 0.0F;
/* 1390 */     this.m22 = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void rotX(float paramFloat) {
/* 1402 */     float f1 = (float)Math.sin(paramFloat);
/* 1403 */     float f2 = (float)Math.cos(paramFloat);
/*      */     
/* 1405 */     this.m00 = 1.0F;
/* 1406 */     this.m01 = 0.0F;
/* 1407 */     this.m02 = 0.0F;
/*      */     
/* 1409 */     this.m10 = 0.0F;
/* 1410 */     this.m11 = f2;
/* 1411 */     this.m12 = -f1;
/*      */     
/* 1413 */     this.m20 = 0.0F;
/* 1414 */     this.m21 = f1;
/* 1415 */     this.m22 = f2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void rotY(float paramFloat) {
/* 1427 */     float f1 = (float)Math.sin(paramFloat);
/* 1428 */     float f2 = (float)Math.cos(paramFloat);
/*      */     
/* 1430 */     this.m00 = f2;
/* 1431 */     this.m01 = 0.0F;
/* 1432 */     this.m02 = f1;
/*      */     
/* 1434 */     this.m10 = 0.0F;
/* 1435 */     this.m11 = 1.0F;
/* 1436 */     this.m12 = 0.0F;
/*      */     
/* 1438 */     this.m20 = -f1;
/* 1439 */     this.m21 = 0.0F;
/* 1440 */     this.m22 = f2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void rotZ(float paramFloat) {
/* 1452 */     float f1 = (float)Math.sin(paramFloat);
/* 1453 */     float f2 = (float)Math.cos(paramFloat);
/*      */     
/* 1455 */     this.m00 = f2;
/* 1456 */     this.m01 = -f1;
/* 1457 */     this.m02 = 0.0F;
/*      */     
/* 1459 */     this.m10 = f1;
/* 1460 */     this.m11 = f2;
/* 1461 */     this.m12 = 0.0F;
/*      */     
/* 1463 */     this.m20 = 0.0F;
/* 1464 */     this.m21 = 0.0F;
/* 1465 */     this.m22 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(float paramFloat) {
/* 1474 */     this.m00 *= paramFloat;
/* 1475 */     this.m01 *= paramFloat;
/* 1476 */     this.m02 *= paramFloat;
/*      */     
/* 1478 */     this.m10 *= paramFloat;
/* 1479 */     this.m11 *= paramFloat;
/* 1480 */     this.m12 *= paramFloat;
/*      */     
/* 1482 */     this.m20 *= paramFloat;
/* 1483 */     this.m21 *= paramFloat;
/* 1484 */     this.m22 *= paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(float paramFloat, Matrix3f paramMatrix3f) {
/* 1495 */     this.m00 = paramFloat * paramMatrix3f.m00;
/* 1496 */     this.m01 = paramFloat * paramMatrix3f.m01;
/* 1497 */     this.m02 = paramFloat * paramMatrix3f.m02;
/*      */     
/* 1499 */     this.m10 = paramFloat * paramMatrix3f.m10;
/* 1500 */     this.m11 = paramFloat * paramMatrix3f.m11;
/* 1501 */     this.m12 = paramFloat * paramMatrix3f.m12;
/*      */     
/* 1503 */     this.m20 = paramFloat * paramMatrix3f.m20;
/* 1504 */     this.m21 = paramFloat * paramMatrix3f.m21;
/* 1505 */     this.m22 = paramFloat * paramMatrix3f.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(Matrix3f paramMatrix3f) {
/* 1520 */     float f1 = this.m00 * paramMatrix3f.m00 + this.m01 * paramMatrix3f.m10 + this.m02 * paramMatrix3f.m20;
/* 1521 */     float f2 = this.m00 * paramMatrix3f.m01 + this.m01 * paramMatrix3f.m11 + this.m02 * paramMatrix3f.m21;
/* 1522 */     float f3 = this.m00 * paramMatrix3f.m02 + this.m01 * paramMatrix3f.m12 + this.m02 * paramMatrix3f.m22;
/*      */     
/* 1524 */     float f4 = this.m10 * paramMatrix3f.m00 + this.m11 * paramMatrix3f.m10 + this.m12 * paramMatrix3f.m20;
/* 1525 */     float f5 = this.m10 * paramMatrix3f.m01 + this.m11 * paramMatrix3f.m11 + this.m12 * paramMatrix3f.m21;
/* 1526 */     float f6 = this.m10 * paramMatrix3f.m02 + this.m11 * paramMatrix3f.m12 + this.m12 * paramMatrix3f.m22;
/*      */     
/* 1528 */     float f7 = this.m20 * paramMatrix3f.m00 + this.m21 * paramMatrix3f.m10 + this.m22 * paramMatrix3f.m20;
/* 1529 */     float f8 = this.m20 * paramMatrix3f.m01 + this.m21 * paramMatrix3f.m11 + this.m22 * paramMatrix3f.m21;
/* 1530 */     float f9 = this.m20 * paramMatrix3f.m02 + this.m21 * paramMatrix3f.m12 + this.m22 * paramMatrix3f.m22;
/*      */     
/* 1532 */     this.m00 = f1; this.m01 = f2; this.m02 = f3;
/* 1533 */     this.m10 = f4; this.m11 = f5; this.m12 = f6;
/* 1534 */     this.m20 = f7; this.m21 = f8; this.m22 = f9;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2) {
/* 1545 */     if (this != paramMatrix3f1 && this != paramMatrix3f2) {
/* 1546 */       this.m00 = paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m01 * paramMatrix3f2.m10 + paramMatrix3f1.m02 * paramMatrix3f2.m20;
/* 1547 */       this.m01 = paramMatrix3f1.m00 * paramMatrix3f2.m01 + paramMatrix3f1.m01 * paramMatrix3f2.m11 + paramMatrix3f1.m02 * paramMatrix3f2.m21;
/* 1548 */       this.m02 = paramMatrix3f1.m00 * paramMatrix3f2.m02 + paramMatrix3f1.m01 * paramMatrix3f2.m12 + paramMatrix3f1.m02 * paramMatrix3f2.m22;
/*      */       
/* 1550 */       this.m10 = paramMatrix3f1.m10 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m10 + paramMatrix3f1.m12 * paramMatrix3f2.m20;
/* 1551 */       this.m11 = paramMatrix3f1.m10 * paramMatrix3f2.m01 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m12 * paramMatrix3f2.m21;
/* 1552 */       this.m12 = paramMatrix3f1.m10 * paramMatrix3f2.m02 + paramMatrix3f1.m11 * paramMatrix3f2.m12 + paramMatrix3f1.m12 * paramMatrix3f2.m22;
/*      */       
/* 1554 */       this.m20 = paramMatrix3f1.m20 * paramMatrix3f2.m00 + paramMatrix3f1.m21 * paramMatrix3f2.m10 + paramMatrix3f1.m22 * paramMatrix3f2.m20;
/* 1555 */       this.m21 = paramMatrix3f1.m20 * paramMatrix3f2.m01 + paramMatrix3f1.m21 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m21;
/* 1556 */       this.m22 = paramMatrix3f1.m20 * paramMatrix3f2.m02 + paramMatrix3f1.m21 * paramMatrix3f2.m12 + paramMatrix3f1.m22 * paramMatrix3f2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1562 */       float f1 = paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m01 * paramMatrix3f2.m10 + paramMatrix3f1.m02 * paramMatrix3f2.m20;
/* 1563 */       float f2 = paramMatrix3f1.m00 * paramMatrix3f2.m01 + paramMatrix3f1.m01 * paramMatrix3f2.m11 + paramMatrix3f1.m02 * paramMatrix3f2.m21;
/* 1564 */       float f3 = paramMatrix3f1.m00 * paramMatrix3f2.m02 + paramMatrix3f1.m01 * paramMatrix3f2.m12 + paramMatrix3f1.m02 * paramMatrix3f2.m22;
/*      */       
/* 1566 */       float f4 = paramMatrix3f1.m10 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m10 + paramMatrix3f1.m12 * paramMatrix3f2.m20;
/* 1567 */       float f5 = paramMatrix3f1.m10 * paramMatrix3f2.m01 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m12 * paramMatrix3f2.m21;
/* 1568 */       float f6 = paramMatrix3f1.m10 * paramMatrix3f2.m02 + paramMatrix3f1.m11 * paramMatrix3f2.m12 + paramMatrix3f1.m12 * paramMatrix3f2.m22;
/*      */       
/* 1570 */       float f7 = paramMatrix3f1.m20 * paramMatrix3f2.m00 + paramMatrix3f1.m21 * paramMatrix3f2.m10 + paramMatrix3f1.m22 * paramMatrix3f2.m20;
/* 1571 */       float f8 = paramMatrix3f1.m20 * paramMatrix3f2.m01 + paramMatrix3f1.m21 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m21;
/* 1572 */       float f9 = paramMatrix3f1.m20 * paramMatrix3f2.m02 + paramMatrix3f1.m21 * paramMatrix3f2.m12 + paramMatrix3f1.m22 * paramMatrix3f2.m22;
/*      */       
/* 1574 */       this.m00 = f1; this.m01 = f2; this.m02 = f3;
/* 1575 */       this.m10 = f4; this.m11 = f5; this.m12 = f6;
/* 1576 */       this.m20 = f7; this.m21 = f8; this.m22 = f9;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mulNormalize(Matrix3f paramMatrix3f) {
/* 1588 */     double[] arrayOfDouble1 = new double[9];
/* 1589 */     double[] arrayOfDouble2 = new double[9];
/* 1590 */     double[] arrayOfDouble3 = new double[3];
/*      */     
/* 1592 */     arrayOfDouble1[0] = (this.m00 * paramMatrix3f.m00 + this.m01 * paramMatrix3f.m10 + this.m02 * paramMatrix3f.m20);
/* 1593 */     arrayOfDouble1[1] = (this.m00 * paramMatrix3f.m01 + this.m01 * paramMatrix3f.m11 + this.m02 * paramMatrix3f.m21);
/* 1594 */     arrayOfDouble1[2] = (this.m00 * paramMatrix3f.m02 + this.m01 * paramMatrix3f.m12 + this.m02 * paramMatrix3f.m22);
/*      */     
/* 1596 */     arrayOfDouble1[3] = (this.m10 * paramMatrix3f.m00 + this.m11 * paramMatrix3f.m10 + this.m12 * paramMatrix3f.m20);
/* 1597 */     arrayOfDouble1[4] = (this.m10 * paramMatrix3f.m01 + this.m11 * paramMatrix3f.m11 + this.m12 * paramMatrix3f.m21);
/* 1598 */     arrayOfDouble1[5] = (this.m10 * paramMatrix3f.m02 + this.m11 * paramMatrix3f.m12 + this.m12 * paramMatrix3f.m22);
/*      */     
/* 1600 */     arrayOfDouble1[6] = (this.m20 * paramMatrix3f.m00 + this.m21 * paramMatrix3f.m10 + this.m22 * paramMatrix3f.m20);
/* 1601 */     arrayOfDouble1[7] = (this.m20 * paramMatrix3f.m01 + this.m21 * paramMatrix3f.m11 + this.m22 * paramMatrix3f.m21);
/* 1602 */     arrayOfDouble1[8] = (this.m20 * paramMatrix3f.m02 + this.m21 * paramMatrix3f.m12 + this.m22 * paramMatrix3f.m22);
/*      */     
/* 1604 */     Matrix3d.compute_svd(arrayOfDouble1, arrayOfDouble3, arrayOfDouble2);
/*      */     
/* 1606 */     this.m00 = (float)arrayOfDouble2[0];
/* 1607 */     this.m01 = (float)arrayOfDouble2[1];
/* 1608 */     this.m02 = (float)arrayOfDouble2[2];
/*      */     
/* 1610 */     this.m10 = (float)arrayOfDouble2[3];
/* 1611 */     this.m11 = (float)arrayOfDouble2[4];
/* 1612 */     this.m12 = (float)arrayOfDouble2[5];
/*      */     
/* 1614 */     this.m20 = (float)arrayOfDouble2[6];
/* 1615 */     this.m21 = (float)arrayOfDouble2[7];
/* 1616 */     this.m22 = (float)arrayOfDouble2[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mulNormalize(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2) {
/* 1629 */     double[] arrayOfDouble1 = new double[9];
/* 1630 */     double[] arrayOfDouble2 = new double[9];
/* 1631 */     double[] arrayOfDouble3 = new double[3];
/*      */ 
/*      */     
/* 1634 */     arrayOfDouble1[0] = (paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m01 * paramMatrix3f2.m10 + paramMatrix3f1.m02 * paramMatrix3f2.m20);
/* 1635 */     arrayOfDouble1[1] = (paramMatrix3f1.m00 * paramMatrix3f2.m01 + paramMatrix3f1.m01 * paramMatrix3f2.m11 + paramMatrix3f1.m02 * paramMatrix3f2.m21);
/* 1636 */     arrayOfDouble1[2] = (paramMatrix3f1.m00 * paramMatrix3f2.m02 + paramMatrix3f1.m01 * paramMatrix3f2.m12 + paramMatrix3f1.m02 * paramMatrix3f2.m22);
/*      */     
/* 1638 */     arrayOfDouble1[3] = (paramMatrix3f1.m10 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m10 + paramMatrix3f1.m12 * paramMatrix3f2.m20);
/* 1639 */     arrayOfDouble1[4] = (paramMatrix3f1.m10 * paramMatrix3f2.m01 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m12 * paramMatrix3f2.m21);
/* 1640 */     arrayOfDouble1[5] = (paramMatrix3f1.m10 * paramMatrix3f2.m02 + paramMatrix3f1.m11 * paramMatrix3f2.m12 + paramMatrix3f1.m12 * paramMatrix3f2.m22);
/*      */     
/* 1642 */     arrayOfDouble1[6] = (paramMatrix3f1.m20 * paramMatrix3f2.m00 + paramMatrix3f1.m21 * paramMatrix3f2.m10 + paramMatrix3f1.m22 * paramMatrix3f2.m20);
/* 1643 */     arrayOfDouble1[7] = (paramMatrix3f1.m20 * paramMatrix3f2.m01 + paramMatrix3f1.m21 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m21);
/* 1644 */     arrayOfDouble1[8] = (paramMatrix3f1.m20 * paramMatrix3f2.m02 + paramMatrix3f1.m21 * paramMatrix3f2.m12 + paramMatrix3f1.m22 * paramMatrix3f2.m22);
/*      */     
/* 1646 */     Matrix3d.compute_svd(arrayOfDouble1, arrayOfDouble3, arrayOfDouble2);
/*      */     
/* 1648 */     this.m00 = (float)arrayOfDouble2[0];
/* 1649 */     this.m01 = (float)arrayOfDouble2[1];
/* 1650 */     this.m02 = (float)arrayOfDouble2[2];
/*      */     
/* 1652 */     this.m10 = (float)arrayOfDouble2[3];
/* 1653 */     this.m11 = (float)arrayOfDouble2[4];
/* 1654 */     this.m12 = (float)arrayOfDouble2[5];
/*      */     
/* 1656 */     this.m20 = (float)arrayOfDouble2[6];
/* 1657 */     this.m21 = (float)arrayOfDouble2[7];
/* 1658 */     this.m22 = (float)arrayOfDouble2[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mulTransposeBoth(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2) {
/* 1669 */     if (this != paramMatrix3f1 && this != paramMatrix3f2) {
/* 1670 */       this.m00 = paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m10 * paramMatrix3f2.m01 + paramMatrix3f1.m20 * paramMatrix3f2.m02;
/* 1671 */       this.m01 = paramMatrix3f1.m00 * paramMatrix3f2.m10 + paramMatrix3f1.m10 * paramMatrix3f2.m11 + paramMatrix3f1.m20 * paramMatrix3f2.m12;
/* 1672 */       this.m02 = paramMatrix3f1.m00 * paramMatrix3f2.m20 + paramMatrix3f1.m10 * paramMatrix3f2.m21 + paramMatrix3f1.m20 * paramMatrix3f2.m22;
/*      */       
/* 1674 */       this.m10 = paramMatrix3f1.m01 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m01 + paramMatrix3f1.m21 * paramMatrix3f2.m02;
/* 1675 */       this.m11 = paramMatrix3f1.m01 * paramMatrix3f2.m10 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m21 * paramMatrix3f2.m12;
/* 1676 */       this.m12 = paramMatrix3f1.m01 * paramMatrix3f2.m20 + paramMatrix3f1.m11 * paramMatrix3f2.m21 + paramMatrix3f1.m21 * paramMatrix3f2.m22;
/*      */       
/* 1678 */       this.m20 = paramMatrix3f1.m02 * paramMatrix3f2.m00 + paramMatrix3f1.m12 * paramMatrix3f2.m01 + paramMatrix3f1.m22 * paramMatrix3f2.m02;
/* 1679 */       this.m21 = paramMatrix3f1.m02 * paramMatrix3f2.m10 + paramMatrix3f1.m12 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m12;
/* 1680 */       this.m22 = paramMatrix3f1.m02 * paramMatrix3f2.m20 + paramMatrix3f1.m12 * paramMatrix3f2.m21 + paramMatrix3f1.m22 * paramMatrix3f2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1686 */       float f1 = paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m10 * paramMatrix3f2.m01 + paramMatrix3f1.m20 * paramMatrix3f2.m02;
/* 1687 */       float f2 = paramMatrix3f1.m00 * paramMatrix3f2.m10 + paramMatrix3f1.m10 * paramMatrix3f2.m11 + paramMatrix3f1.m20 * paramMatrix3f2.m12;
/* 1688 */       float f3 = paramMatrix3f1.m00 * paramMatrix3f2.m20 + paramMatrix3f1.m10 * paramMatrix3f2.m21 + paramMatrix3f1.m20 * paramMatrix3f2.m22;
/*      */       
/* 1690 */       float f4 = paramMatrix3f1.m01 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m01 + paramMatrix3f1.m21 * paramMatrix3f2.m02;
/* 1691 */       float f5 = paramMatrix3f1.m01 * paramMatrix3f2.m10 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m21 * paramMatrix3f2.m12;
/* 1692 */       float f6 = paramMatrix3f1.m01 * paramMatrix3f2.m20 + paramMatrix3f1.m11 * paramMatrix3f2.m21 + paramMatrix3f1.m21 * paramMatrix3f2.m22;
/*      */       
/* 1694 */       float f7 = paramMatrix3f1.m02 * paramMatrix3f2.m00 + paramMatrix3f1.m12 * paramMatrix3f2.m01 + paramMatrix3f1.m22 * paramMatrix3f2.m02;
/* 1695 */       float f8 = paramMatrix3f1.m02 * paramMatrix3f2.m10 + paramMatrix3f1.m12 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m12;
/* 1696 */       float f9 = paramMatrix3f1.m02 * paramMatrix3f2.m20 + paramMatrix3f1.m12 * paramMatrix3f2.m21 + paramMatrix3f1.m22 * paramMatrix3f2.m22;
/*      */       
/* 1698 */       this.m00 = f1; this.m01 = f2; this.m02 = f3;
/* 1699 */       this.m10 = f4; this.m11 = f5; this.m12 = f6;
/* 1700 */       this.m20 = f7; this.m21 = f8; this.m22 = f9;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mulTransposeRight(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2) {
/* 1714 */     if (this != paramMatrix3f1 && this != paramMatrix3f2) {
/* 1715 */       this.m00 = paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m01 * paramMatrix3f2.m01 + paramMatrix3f1.m02 * paramMatrix3f2.m02;
/* 1716 */       this.m01 = paramMatrix3f1.m00 * paramMatrix3f2.m10 + paramMatrix3f1.m01 * paramMatrix3f2.m11 + paramMatrix3f1.m02 * paramMatrix3f2.m12;
/* 1717 */       this.m02 = paramMatrix3f1.m00 * paramMatrix3f2.m20 + paramMatrix3f1.m01 * paramMatrix3f2.m21 + paramMatrix3f1.m02 * paramMatrix3f2.m22;
/*      */       
/* 1719 */       this.m10 = paramMatrix3f1.m10 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m01 + paramMatrix3f1.m12 * paramMatrix3f2.m02;
/* 1720 */       this.m11 = paramMatrix3f1.m10 * paramMatrix3f2.m10 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m12 * paramMatrix3f2.m12;
/* 1721 */       this.m12 = paramMatrix3f1.m10 * paramMatrix3f2.m20 + paramMatrix3f1.m11 * paramMatrix3f2.m21 + paramMatrix3f1.m12 * paramMatrix3f2.m22;
/*      */       
/* 1723 */       this.m20 = paramMatrix3f1.m20 * paramMatrix3f2.m00 + paramMatrix3f1.m21 * paramMatrix3f2.m01 + paramMatrix3f1.m22 * paramMatrix3f2.m02;
/* 1724 */       this.m21 = paramMatrix3f1.m20 * paramMatrix3f2.m10 + paramMatrix3f1.m21 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m12;
/* 1725 */       this.m22 = paramMatrix3f1.m20 * paramMatrix3f2.m20 + paramMatrix3f1.m21 * paramMatrix3f2.m21 + paramMatrix3f1.m22 * paramMatrix3f2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1731 */       float f1 = paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m01 * paramMatrix3f2.m01 + paramMatrix3f1.m02 * paramMatrix3f2.m02;
/* 1732 */       float f2 = paramMatrix3f1.m00 * paramMatrix3f2.m10 + paramMatrix3f1.m01 * paramMatrix3f2.m11 + paramMatrix3f1.m02 * paramMatrix3f2.m12;
/* 1733 */       float f3 = paramMatrix3f1.m00 * paramMatrix3f2.m20 + paramMatrix3f1.m01 * paramMatrix3f2.m21 + paramMatrix3f1.m02 * paramMatrix3f2.m22;
/*      */       
/* 1735 */       float f4 = paramMatrix3f1.m10 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m01 + paramMatrix3f1.m12 * paramMatrix3f2.m02;
/* 1736 */       float f5 = paramMatrix3f1.m10 * paramMatrix3f2.m10 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m12 * paramMatrix3f2.m12;
/* 1737 */       float f6 = paramMatrix3f1.m10 * paramMatrix3f2.m20 + paramMatrix3f1.m11 * paramMatrix3f2.m21 + paramMatrix3f1.m12 * paramMatrix3f2.m22;
/*      */       
/* 1739 */       float f7 = paramMatrix3f1.m20 * paramMatrix3f2.m00 + paramMatrix3f1.m21 * paramMatrix3f2.m01 + paramMatrix3f1.m22 * paramMatrix3f2.m02;
/* 1740 */       float f8 = paramMatrix3f1.m20 * paramMatrix3f2.m10 + paramMatrix3f1.m21 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m12;
/* 1741 */       float f9 = paramMatrix3f1.m20 * paramMatrix3f2.m20 + paramMatrix3f1.m21 * paramMatrix3f2.m21 + paramMatrix3f1.m22 * paramMatrix3f2.m22;
/*      */       
/* 1743 */       this.m00 = f1; this.m01 = f2; this.m02 = f3;
/* 1744 */       this.m10 = f4; this.m11 = f5; this.m12 = f6;
/* 1745 */       this.m20 = f7; this.m21 = f8; this.m22 = f9;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mulTransposeLeft(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2) {
/* 1757 */     if (this != paramMatrix3f1 && this != paramMatrix3f2) {
/* 1758 */       this.m00 = paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m10 * paramMatrix3f2.m10 + paramMatrix3f1.m20 * paramMatrix3f2.m20;
/* 1759 */       this.m01 = paramMatrix3f1.m00 * paramMatrix3f2.m01 + paramMatrix3f1.m10 * paramMatrix3f2.m11 + paramMatrix3f1.m20 * paramMatrix3f2.m21;
/* 1760 */       this.m02 = paramMatrix3f1.m00 * paramMatrix3f2.m02 + paramMatrix3f1.m10 * paramMatrix3f2.m12 + paramMatrix3f1.m20 * paramMatrix3f2.m22;
/*      */       
/* 1762 */       this.m10 = paramMatrix3f1.m01 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m10 + paramMatrix3f1.m21 * paramMatrix3f2.m20;
/* 1763 */       this.m11 = paramMatrix3f1.m01 * paramMatrix3f2.m01 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m21 * paramMatrix3f2.m21;
/* 1764 */       this.m12 = paramMatrix3f1.m01 * paramMatrix3f2.m02 + paramMatrix3f1.m11 * paramMatrix3f2.m12 + paramMatrix3f1.m21 * paramMatrix3f2.m22;
/*      */       
/* 1766 */       this.m20 = paramMatrix3f1.m02 * paramMatrix3f2.m00 + paramMatrix3f1.m12 * paramMatrix3f2.m10 + paramMatrix3f1.m22 * paramMatrix3f2.m20;
/* 1767 */       this.m21 = paramMatrix3f1.m02 * paramMatrix3f2.m01 + paramMatrix3f1.m12 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m21;
/* 1768 */       this.m22 = paramMatrix3f1.m02 * paramMatrix3f2.m02 + paramMatrix3f1.m12 * paramMatrix3f2.m12 + paramMatrix3f1.m22 * paramMatrix3f2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1774 */       float f1 = paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m10 * paramMatrix3f2.m10 + paramMatrix3f1.m20 * paramMatrix3f2.m20;
/* 1775 */       float f2 = paramMatrix3f1.m00 * paramMatrix3f2.m01 + paramMatrix3f1.m10 * paramMatrix3f2.m11 + paramMatrix3f1.m20 * paramMatrix3f2.m21;
/* 1776 */       float f3 = paramMatrix3f1.m00 * paramMatrix3f2.m02 + paramMatrix3f1.m10 * paramMatrix3f2.m12 + paramMatrix3f1.m20 * paramMatrix3f2.m22;
/*      */       
/* 1778 */       float f4 = paramMatrix3f1.m01 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m10 + paramMatrix3f1.m21 * paramMatrix3f2.m20;
/* 1779 */       float f5 = paramMatrix3f1.m01 * paramMatrix3f2.m01 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m21 * paramMatrix3f2.m21;
/* 1780 */       float f6 = paramMatrix3f1.m01 * paramMatrix3f2.m02 + paramMatrix3f1.m11 * paramMatrix3f2.m12 + paramMatrix3f1.m21 * paramMatrix3f2.m22;
/*      */       
/* 1782 */       float f7 = paramMatrix3f1.m02 * paramMatrix3f2.m00 + paramMatrix3f1.m12 * paramMatrix3f2.m10 + paramMatrix3f1.m22 * paramMatrix3f2.m20;
/* 1783 */       float f8 = paramMatrix3f1.m02 * paramMatrix3f2.m01 + paramMatrix3f1.m12 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m21;
/* 1784 */       float f9 = paramMatrix3f1.m02 * paramMatrix3f2.m02 + paramMatrix3f1.m12 * paramMatrix3f2.m12 + paramMatrix3f1.m22 * paramMatrix3f2.m22;
/*      */       
/* 1786 */       this.m00 = f1; this.m01 = f2; this.m02 = f3;
/* 1787 */       this.m10 = f4; this.m11 = f5; this.m12 = f6;
/* 1788 */       this.m20 = f7; this.m21 = f8; this.m22 = f9;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalize() {
/* 1797 */     double[] arrayOfDouble1 = new double[9];
/* 1798 */     double[] arrayOfDouble2 = new double[3];
/* 1799 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/* 1801 */     this.m00 = (float)arrayOfDouble1[0];
/* 1802 */     this.m01 = (float)arrayOfDouble1[1];
/* 1803 */     this.m02 = (float)arrayOfDouble1[2];
/*      */     
/* 1805 */     this.m10 = (float)arrayOfDouble1[3];
/* 1806 */     this.m11 = (float)arrayOfDouble1[4];
/* 1807 */     this.m12 = (float)arrayOfDouble1[5];
/*      */     
/* 1809 */     this.m20 = (float)arrayOfDouble1[6];
/* 1810 */     this.m21 = (float)arrayOfDouble1[7];
/* 1811 */     this.m22 = (float)arrayOfDouble1[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalize(Matrix3f paramMatrix3f) {
/* 1821 */     double[] arrayOfDouble1 = new double[9];
/* 1822 */     double[] arrayOfDouble2 = new double[9];
/* 1823 */     double[] arrayOfDouble3 = new double[3];
/*      */     
/* 1825 */     arrayOfDouble1[0] = paramMatrix3f.m00;
/* 1826 */     arrayOfDouble1[1] = paramMatrix3f.m01;
/* 1827 */     arrayOfDouble1[2] = paramMatrix3f.m02;
/*      */     
/* 1829 */     arrayOfDouble1[3] = paramMatrix3f.m10;
/* 1830 */     arrayOfDouble1[4] = paramMatrix3f.m11;
/* 1831 */     arrayOfDouble1[5] = paramMatrix3f.m12;
/*      */     
/* 1833 */     arrayOfDouble1[6] = paramMatrix3f.m20;
/* 1834 */     arrayOfDouble1[7] = paramMatrix3f.m21;
/* 1835 */     arrayOfDouble1[8] = paramMatrix3f.m22;
/*      */     
/* 1837 */     Matrix3d.compute_svd(arrayOfDouble1, arrayOfDouble3, arrayOfDouble2);
/*      */     
/* 1839 */     this.m00 = (float)arrayOfDouble2[0];
/* 1840 */     this.m01 = (float)arrayOfDouble2[1];
/* 1841 */     this.m02 = (float)arrayOfDouble2[2];
/*      */     
/* 1843 */     this.m10 = (float)arrayOfDouble2[3];
/* 1844 */     this.m11 = (float)arrayOfDouble2[4];
/* 1845 */     this.m12 = (float)arrayOfDouble2[5];
/*      */     
/* 1847 */     this.m20 = (float)arrayOfDouble2[6];
/* 1848 */     this.m21 = (float)arrayOfDouble2[7];
/* 1849 */     this.m22 = (float)arrayOfDouble2[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalizeCP() {
/* 1858 */     float f = 1.0F / (float)Math.sqrt((this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20));
/* 1859 */     this.m00 *= f;
/* 1860 */     this.m10 *= f;
/* 1861 */     this.m20 *= f;
/*      */     
/* 1863 */     f = 1.0F / (float)Math.sqrt((this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21));
/* 1864 */     this.m01 *= f;
/* 1865 */     this.m11 *= f;
/* 1866 */     this.m21 *= f;
/*      */     
/* 1868 */     this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
/* 1869 */     this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
/* 1870 */     this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalizeCP(Matrix3f paramMatrix3f) {
/* 1881 */     float f = 1.0F / (float)Math.sqrt((paramMatrix3f.m00 * paramMatrix3f.m00 + paramMatrix3f.m10 * paramMatrix3f.m10 + paramMatrix3f.m20 * paramMatrix3f.m20));
/* 1882 */     paramMatrix3f.m00 *= f;
/* 1883 */     paramMatrix3f.m10 *= f;
/* 1884 */     paramMatrix3f.m20 *= f;
/*      */     
/* 1886 */     f = 1.0F / (float)Math.sqrt((paramMatrix3f.m01 * paramMatrix3f.m01 + paramMatrix3f.m11 * paramMatrix3f.m11 + paramMatrix3f.m21 * paramMatrix3f.m21));
/* 1887 */     paramMatrix3f.m01 *= f;
/* 1888 */     paramMatrix3f.m11 *= f;
/* 1889 */     paramMatrix3f.m21 *= f;
/*      */     
/* 1891 */     this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
/* 1892 */     this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
/* 1893 */     this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Matrix3f paramMatrix3f) {
/*      */     try {
/* 1907 */       return (this.m00 == paramMatrix3f.m00 && this.m01 == paramMatrix3f.m01 && this.m02 == paramMatrix3f.m02 && this.m10 == paramMatrix3f.m10 && this.m11 == paramMatrix3f.m11 && this.m12 == paramMatrix3f.m12 && this.m20 == paramMatrix3f.m20 && this.m21 == paramMatrix3f.m21 && this.m22 == paramMatrix3f.m22);
/*      */     }
/*      */     catch (NullPointerException nullPointerException) {
/*      */       
/* 1911 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/*      */     
/* 1926 */     try { Matrix3f matrix3f = (Matrix3f)paramObject;
/* 1927 */       return (this.m00 == matrix3f.m00 && this.m01 == matrix3f.m01 && this.m02 == matrix3f.m02 && this.m10 == matrix3f.m10 && this.m11 == matrix3f.m11 && this.m12 == matrix3f.m12 && this.m20 == matrix3f.m20 && this.m21 == matrix3f.m21 && this.m22 == matrix3f.m22); }
/*      */     
/*      */     catch (ClassCastException classCastException)
/*      */     
/* 1931 */     { return false; }
/* 1932 */     catch (NullPointerException nullPointerException) { return false; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean epsilonEquals(Matrix3f paramMatrix3f, float paramFloat) {
/* 1946 */     boolean bool = true;
/*      */     
/* 1948 */     if (Math.abs(this.m00 - paramMatrix3f.m00) > paramFloat) bool = false; 
/* 1949 */     if (Math.abs(this.m01 - paramMatrix3f.m01) > paramFloat) bool = false; 
/* 1950 */     if (Math.abs(this.m02 - paramMatrix3f.m02) > paramFloat) bool = false;
/*      */     
/* 1952 */     if (Math.abs(this.m10 - paramMatrix3f.m10) > paramFloat) bool = false; 
/* 1953 */     if (Math.abs(this.m11 - paramMatrix3f.m11) > paramFloat) bool = false; 
/* 1954 */     if (Math.abs(this.m12 - paramMatrix3f.m12) > paramFloat) bool = false;
/*      */     
/* 1956 */     if (Math.abs(this.m20 - paramMatrix3f.m20) > paramFloat) bool = false; 
/* 1957 */     if (Math.abs(this.m21 - paramMatrix3f.m21) > paramFloat) bool = false; 
/* 1958 */     if (Math.abs(this.m22 - paramMatrix3f.m22) > paramFloat) bool = false;
/*      */     
/* 1960 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1974 */     long l = 1L;
/* 1975 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m00);
/* 1976 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m01);
/* 1977 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m02);
/* 1978 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m10);
/* 1979 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m11);
/* 1980 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m12);
/* 1981 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m20);
/* 1982 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m21);
/* 1983 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m22);
/* 1984 */     return (int)(l ^ l >> 32L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/* 1993 */     this.m00 = 0.0F;
/* 1994 */     this.m01 = 0.0F;
/* 1995 */     this.m02 = 0.0F;
/*      */     
/* 1997 */     this.m10 = 0.0F;
/* 1998 */     this.m11 = 0.0F;
/* 1999 */     this.m12 = 0.0F;
/*      */     
/* 2001 */     this.m20 = 0.0F;
/* 2002 */     this.m21 = 0.0F;
/* 2003 */     this.m22 = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/* 2012 */     this.m00 = -this.m00;
/* 2013 */     this.m01 = -this.m01;
/* 2014 */     this.m02 = -this.m02;
/*      */     
/* 2016 */     this.m10 = -this.m10;
/* 2017 */     this.m11 = -this.m11;
/* 2018 */     this.m12 = -this.m12;
/*      */     
/* 2020 */     this.m20 = -this.m20;
/* 2021 */     this.m21 = -this.m21;
/* 2022 */     this.m22 = -this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate(Matrix3f paramMatrix3f) {
/* 2033 */     this.m00 = -paramMatrix3f.m00;
/* 2034 */     this.m01 = -paramMatrix3f.m01;
/* 2035 */     this.m02 = -paramMatrix3f.m02;
/*      */     
/* 2037 */     this.m10 = -paramMatrix3f.m10;
/* 2038 */     this.m11 = -paramMatrix3f.m11;
/* 2039 */     this.m12 = -paramMatrix3f.m12;
/*      */     
/* 2041 */     this.m20 = -paramMatrix3f.m20;
/* 2042 */     this.m21 = -paramMatrix3f.m21;
/* 2043 */     this.m22 = -paramMatrix3f.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Tuple3f paramTuple3f) {
/* 2054 */     float f1 = this.m00 * paramTuple3f.x + this.m01 * paramTuple3f.y + this.m02 * paramTuple3f.z;
/* 2055 */     float f2 = this.m10 * paramTuple3f.x + this.m11 * paramTuple3f.y + this.m12 * paramTuple3f.z;
/* 2056 */     float f3 = this.m20 * paramTuple3f.x + this.m21 * paramTuple3f.y + this.m22 * paramTuple3f.z;
/* 2057 */     paramTuple3f.set(f1, f2, f3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Tuple3f paramTuple3f1, Tuple3f paramTuple3f2) {
/* 2068 */     float f1 = this.m00 * paramTuple3f1.x + this.m01 * paramTuple3f1.y + this.m02 * paramTuple3f1.z;
/* 2069 */     float f2 = this.m10 * paramTuple3f1.x + this.m11 * paramTuple3f1.y + this.m12 * paramTuple3f1.z;
/* 2070 */     paramTuple3f2.z = this.m20 * paramTuple3f1.x + this.m21 * paramTuple3f1.y + this.m22 * paramTuple3f1.z;
/* 2071 */     paramTuple3f2.x = f1;
/* 2072 */     paramTuple3f2.y = f2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void getScaleRotate(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
/* 2080 */     double[] arrayOfDouble = new double[9];
/* 2081 */     arrayOfDouble[0] = this.m00;
/* 2082 */     arrayOfDouble[1] = this.m01;
/* 2083 */     arrayOfDouble[2] = this.m02;
/* 2084 */     arrayOfDouble[3] = this.m10;
/* 2085 */     arrayOfDouble[4] = this.m11;
/* 2086 */     arrayOfDouble[5] = this.m12;
/* 2087 */     arrayOfDouble[6] = this.m20;
/* 2088 */     arrayOfDouble[7] = this.m21;
/* 2089 */     arrayOfDouble[8] = this.m22;
/* 2090 */     Matrix3d.compute_svd(arrayOfDouble, paramArrayOfdouble1, paramArrayOfdouble2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/* 2105 */     Matrix3f matrix3f = null;
/*      */     try {
/* 2107 */       matrix3f = (Matrix3f)super.clone();
/* 2108 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */       
/* 2110 */       throw new InternalError();
/*      */     } 
/* 2112 */     return matrix3f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM00() {
/* 2124 */     return this.m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM00(float paramFloat) {
/* 2135 */     this.m00 = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM01() {
/* 2147 */     return this.m01;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM01(float paramFloat) {
/* 2158 */     this.m01 = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM02() {
/* 2169 */     return this.m02;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM02(float paramFloat) {
/* 2180 */     this.m02 = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM10() {
/* 2191 */     return this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM10(float paramFloat) {
/* 2202 */     this.m10 = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM11() {
/* 2213 */     return this.m11;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM11(float paramFloat) {
/* 2224 */     this.m11 = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM12() {
/* 2235 */     return this.m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM12(float paramFloat) {
/* 2244 */     this.m12 = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM20() {
/* 2255 */     return this.m20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM20(float paramFloat) {
/* 2266 */     this.m20 = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM21() {
/* 2277 */     return this.m21;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM21(float paramFloat) {
/* 2288 */     this.m21 = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM22() {
/* 2299 */     return this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM22(float paramFloat) {
/* 2310 */     this.m22 = paramFloat;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Matrix3f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
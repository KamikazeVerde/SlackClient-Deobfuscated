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
/*      */ public class Matrix3d
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   static final long serialVersionUID = 6837536777072402710L;
/*      */   public double m00;
/*      */   public double m01;
/*      */   public double m02;
/*      */   public double m10;
/*      */   public double m11;
/*      */   public double m12;
/*      */   public double m20;
/*      */   public double m21;
/*      */   public double m22;
/*      */   private static final double EPS = 1.110223024E-16D;
/*      */   private static final double ERR_EPS = 1.0E-8D;
/*      */   private static double xin;
/*      */   private static double yin;
/*      */   private static double zin;
/*      */   private static double xout;
/*      */   private static double yout;
/*      */   private static double zout;
/*      */   
/*      */   public Matrix3d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9) {
/*  114 */     this.m00 = paramDouble1;
/*  115 */     this.m01 = paramDouble2;
/*  116 */     this.m02 = paramDouble3;
/*      */     
/*  118 */     this.m10 = paramDouble4;
/*  119 */     this.m11 = paramDouble5;
/*  120 */     this.m12 = paramDouble6;
/*      */     
/*  122 */     this.m20 = paramDouble7;
/*  123 */     this.m21 = paramDouble8;
/*  124 */     this.m22 = paramDouble9;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3d(double[] paramArrayOfdouble) {
/*  135 */     this.m00 = paramArrayOfdouble[0];
/*  136 */     this.m01 = paramArrayOfdouble[1];
/*  137 */     this.m02 = paramArrayOfdouble[2];
/*      */     
/*  139 */     this.m10 = paramArrayOfdouble[3];
/*  140 */     this.m11 = paramArrayOfdouble[4];
/*  141 */     this.m12 = paramArrayOfdouble[5];
/*      */     
/*  143 */     this.m20 = paramArrayOfdouble[6];
/*  144 */     this.m21 = paramArrayOfdouble[7];
/*  145 */     this.m22 = paramArrayOfdouble[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3d(Matrix3d paramMatrix3d) {
/*  156 */     this.m00 = paramMatrix3d.m00;
/*  157 */     this.m01 = paramMatrix3d.m01;
/*  158 */     this.m02 = paramMatrix3d.m02;
/*      */     
/*  160 */     this.m10 = paramMatrix3d.m10;
/*  161 */     this.m11 = paramMatrix3d.m11;
/*  162 */     this.m12 = paramMatrix3d.m12;
/*      */     
/*  164 */     this.m20 = paramMatrix3d.m20;
/*  165 */     this.m21 = paramMatrix3d.m21;
/*  166 */     this.m22 = paramMatrix3d.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3d(Matrix3f paramMatrix3f) {
/*  177 */     this.m00 = paramMatrix3f.m00;
/*  178 */     this.m01 = paramMatrix3f.m01;
/*  179 */     this.m02 = paramMatrix3f.m02;
/*      */     
/*  181 */     this.m10 = paramMatrix3f.m10;
/*  182 */     this.m11 = paramMatrix3f.m11;
/*  183 */     this.m12 = paramMatrix3f.m12;
/*      */     
/*  185 */     this.m20 = paramMatrix3f.m20;
/*  186 */     this.m21 = paramMatrix3f.m21;
/*  187 */     this.m22 = paramMatrix3f.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3d() {
/*  196 */     this.m00 = 0.0D;
/*  197 */     this.m01 = 0.0D;
/*  198 */     this.m02 = 0.0D;
/*      */     
/*  200 */     this.m10 = 0.0D;
/*  201 */     this.m11 = 0.0D;
/*  202 */     this.m12 = 0.0D;
/*      */     
/*  204 */     this.m20 = 0.0D;
/*  205 */     this.m21 = 0.0D;
/*  206 */     this.m22 = 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  215 */     return this.m00 + ", " + this.m01 + ", " + this.m02 + "\n" + this.m10 + ", " + this.m11 + ", " + this.m12 + "\n" + this.m20 + ", " + this.m21 + ", " + this.m22 + "\n";
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
/*  226 */     this.m00 = 1.0D;
/*  227 */     this.m01 = 0.0D;
/*  228 */     this.m02 = 0.0D;
/*      */     
/*  230 */     this.m10 = 0.0D;
/*  231 */     this.m11 = 1.0D;
/*  232 */     this.m12 = 0.0D;
/*      */     
/*  234 */     this.m20 = 0.0D;
/*  235 */     this.m21 = 0.0D;
/*  236 */     this.m22 = 1.0D;
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
/*      */   public final void setScale(double paramDouble) {
/*  248 */     double[] arrayOfDouble1 = new double[9];
/*  249 */     double[] arrayOfDouble2 = new double[3];
/*      */     
/*  251 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/*  253 */     this.m00 = arrayOfDouble1[0] * paramDouble;
/*  254 */     this.m01 = arrayOfDouble1[1] * paramDouble;
/*  255 */     this.m02 = arrayOfDouble1[2] * paramDouble;
/*      */     
/*  257 */     this.m10 = arrayOfDouble1[3] * paramDouble;
/*  258 */     this.m11 = arrayOfDouble1[4] * paramDouble;
/*  259 */     this.m12 = arrayOfDouble1[5] * paramDouble;
/*      */     
/*  261 */     this.m20 = arrayOfDouble1[6] * paramDouble;
/*  262 */     this.m21 = arrayOfDouble1[7] * paramDouble;
/*  263 */     this.m22 = arrayOfDouble1[8] * paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setElement(int paramInt1, int paramInt2, double paramDouble) {
/*  274 */     switch (paramInt1) {
/*      */       
/*      */       case 0:
/*  277 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  280 */             this.m00 = paramDouble;
/*      */             return;
/*      */           case 1:
/*  283 */             this.m01 = paramDouble;
/*      */             return;
/*      */           case 2:
/*  286 */             this.m02 = paramDouble;
/*      */             return;
/*      */         } 
/*  289 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  294 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  297 */             this.m10 = paramDouble;
/*      */             return;
/*      */           case 1:
/*  300 */             this.m11 = paramDouble;
/*      */             return;
/*      */           case 2:
/*  303 */             this.m12 = paramDouble;
/*      */             return;
/*      */         } 
/*  306 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d0"));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  312 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  315 */             this.m20 = paramDouble;
/*      */             return;
/*      */           case 1:
/*  318 */             this.m21 = paramDouble;
/*      */             return;
/*      */           case 2:
/*  321 */             this.m22 = paramDouble;
/*      */             return;
/*      */         } 
/*  324 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d0"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  329 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d0"));
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
/*      */   public final double getElement(int paramInt1, int paramInt2) {
/*  342 */     switch (paramInt1) {
/*      */       
/*      */       case 0:
/*  345 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  348 */             return this.m00;
/*      */           case 1:
/*  350 */             return this.m01;
/*      */           case 2:
/*  352 */             return this.m02;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/*  358 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  361 */             return this.m10;
/*      */           case 1:
/*  363 */             return this.m11;
/*      */           case 2:
/*  365 */             return this.m12;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/*  372 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  375 */             return this.m20;
/*      */           case 1:
/*  377 */             return this.m21;
/*      */           case 2:
/*  379 */             return this.m22;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  389 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d1"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int paramInt, Vector3d paramVector3d) {
/*  398 */     if (paramInt == 0) {
/*  399 */       paramVector3d.x = this.m00;
/*  400 */       paramVector3d.y = this.m01;
/*  401 */       paramVector3d.z = this.m02;
/*  402 */     } else if (paramInt == 1) {
/*  403 */       paramVector3d.x = this.m10;
/*  404 */       paramVector3d.y = this.m11;
/*  405 */       paramVector3d.z = this.m12;
/*  406 */     } else if (paramInt == 2) {
/*  407 */       paramVector3d.x = this.m20;
/*  408 */       paramVector3d.y = this.m21;
/*  409 */       paramVector3d.z = this.m22;
/*      */     } else {
/*  411 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d2"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int paramInt, double[] paramArrayOfdouble) {
/*  422 */     if (paramInt == 0) {
/*  423 */       paramArrayOfdouble[0] = this.m00;
/*  424 */       paramArrayOfdouble[1] = this.m01;
/*  425 */       paramArrayOfdouble[2] = this.m02;
/*  426 */     } else if (paramInt == 1) {
/*  427 */       paramArrayOfdouble[0] = this.m10;
/*  428 */       paramArrayOfdouble[1] = this.m11;
/*  429 */       paramArrayOfdouble[2] = this.m12;
/*  430 */     } else if (paramInt == 2) {
/*  431 */       paramArrayOfdouble[0] = this.m20;
/*  432 */       paramArrayOfdouble[1] = this.m21;
/*  433 */       paramArrayOfdouble[2] = this.m22;
/*      */     } else {
/*  435 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d2"));
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
/*      */   public final void getColumn(int paramInt, Vector3d paramVector3d) {
/*  447 */     if (paramInt == 0) {
/*  448 */       paramVector3d.x = this.m00;
/*  449 */       paramVector3d.y = this.m10;
/*  450 */       paramVector3d.z = this.m20;
/*  451 */     } else if (paramInt == 1) {
/*  452 */       paramVector3d.x = this.m01;
/*  453 */       paramVector3d.y = this.m11;
/*  454 */       paramVector3d.z = this.m21;
/*  455 */     } else if (paramInt == 2) {
/*  456 */       paramVector3d.x = this.m02;
/*  457 */       paramVector3d.y = this.m12;
/*  458 */       paramVector3d.z = this.m22;
/*      */     } else {
/*  460 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d4"));
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
/*      */   public final void getColumn(int paramInt, double[] paramArrayOfdouble) {
/*  472 */     if (paramInt == 0) {
/*  473 */       paramArrayOfdouble[0] = this.m00;
/*  474 */       paramArrayOfdouble[1] = this.m10;
/*  475 */       paramArrayOfdouble[2] = this.m20;
/*  476 */     } else if (paramInt == 1) {
/*  477 */       paramArrayOfdouble[0] = this.m01;
/*  478 */       paramArrayOfdouble[1] = this.m11;
/*  479 */       paramArrayOfdouble[2] = this.m21;
/*  480 */     } else if (paramInt == 2) {
/*  481 */       paramArrayOfdouble[0] = this.m02;
/*  482 */       paramArrayOfdouble[1] = this.m12;
/*  483 */       paramArrayOfdouble[2] = this.m22;
/*      */     } else {
/*  485 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d4"));
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
/*      */   
/*      */   public final void setRow(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3) {
/*  500 */     switch (paramInt) {
/*      */       case 0:
/*  502 */         this.m00 = paramDouble1;
/*  503 */         this.m01 = paramDouble2;
/*  504 */         this.m02 = paramDouble3;
/*      */         return;
/*      */       
/*      */       case 1:
/*  508 */         this.m10 = paramDouble1;
/*  509 */         this.m11 = paramDouble2;
/*  510 */         this.m12 = paramDouble3;
/*      */         return;
/*      */       
/*      */       case 2:
/*  514 */         this.m20 = paramDouble1;
/*  515 */         this.m21 = paramDouble2;
/*  516 */         this.m22 = paramDouble3;
/*      */         return;
/*      */     } 
/*      */     
/*  520 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int paramInt, Vector3d paramVector3d) {
/*  531 */     switch (paramInt) {
/*      */       case 0:
/*  533 */         this.m00 = paramVector3d.x;
/*  534 */         this.m01 = paramVector3d.y;
/*  535 */         this.m02 = paramVector3d.z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  539 */         this.m10 = paramVector3d.x;
/*  540 */         this.m11 = paramVector3d.y;
/*  541 */         this.m12 = paramVector3d.z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  545 */         this.m20 = paramVector3d.x;
/*  546 */         this.m21 = paramVector3d.y;
/*  547 */         this.m22 = paramVector3d.z;
/*      */         return;
/*      */     } 
/*      */     
/*  551 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int paramInt, double[] paramArrayOfdouble) {
/*  562 */     switch (paramInt) {
/*      */       case 0:
/*  564 */         this.m00 = paramArrayOfdouble[0];
/*  565 */         this.m01 = paramArrayOfdouble[1];
/*  566 */         this.m02 = paramArrayOfdouble[2];
/*      */         return;
/*      */       
/*      */       case 1:
/*  570 */         this.m10 = paramArrayOfdouble[0];
/*  571 */         this.m11 = paramArrayOfdouble[1];
/*  572 */         this.m12 = paramArrayOfdouble[2];
/*      */         return;
/*      */       
/*      */       case 2:
/*  576 */         this.m20 = paramArrayOfdouble[0];
/*  577 */         this.m21 = paramArrayOfdouble[1];
/*  578 */         this.m22 = paramArrayOfdouble[2];
/*      */         return;
/*      */     } 
/*      */     
/*  582 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d6"));
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
/*      */   public final void setColumn(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3) {
/*  595 */     switch (paramInt) {
/*      */       case 0:
/*  597 */         this.m00 = paramDouble1;
/*  598 */         this.m10 = paramDouble2;
/*  599 */         this.m20 = paramDouble3;
/*      */         return;
/*      */       
/*      */       case 1:
/*  603 */         this.m01 = paramDouble1;
/*  604 */         this.m11 = paramDouble2;
/*  605 */         this.m21 = paramDouble3;
/*      */         return;
/*      */       
/*      */       case 2:
/*  609 */         this.m02 = paramDouble1;
/*  610 */         this.m12 = paramDouble2;
/*  611 */         this.m22 = paramDouble3;
/*      */         return;
/*      */     } 
/*      */     
/*  615 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int paramInt, Vector3d paramVector3d) {
/*  626 */     switch (paramInt) {
/*      */       case 0:
/*  628 */         this.m00 = paramVector3d.x;
/*  629 */         this.m10 = paramVector3d.y;
/*  630 */         this.m20 = paramVector3d.z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  634 */         this.m01 = paramVector3d.x;
/*  635 */         this.m11 = paramVector3d.y;
/*  636 */         this.m21 = paramVector3d.z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  640 */         this.m02 = paramVector3d.x;
/*  641 */         this.m12 = paramVector3d.y;
/*  642 */         this.m22 = paramVector3d.z;
/*      */         return;
/*      */     } 
/*      */     
/*  646 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int paramInt, double[] paramArrayOfdouble) {
/*  657 */     switch (paramInt) {
/*      */       case 0:
/*  659 */         this.m00 = paramArrayOfdouble[0];
/*  660 */         this.m10 = paramArrayOfdouble[1];
/*  661 */         this.m20 = paramArrayOfdouble[2];
/*      */         return;
/*      */       
/*      */       case 1:
/*  665 */         this.m01 = paramArrayOfdouble[0];
/*  666 */         this.m11 = paramArrayOfdouble[1];
/*  667 */         this.m21 = paramArrayOfdouble[2];
/*      */         return;
/*      */       
/*      */       case 2:
/*  671 */         this.m02 = paramArrayOfdouble[0];
/*  672 */         this.m12 = paramArrayOfdouble[1];
/*  673 */         this.m22 = paramArrayOfdouble[2];
/*      */         return;
/*      */     } 
/*      */     
/*  677 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d9"));
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
/*      */   public final double getScale() {
/*  691 */     double[] arrayOfDouble1 = new double[3];
/*  692 */     double[] arrayOfDouble2 = new double[9];
/*  693 */     getScaleRotate(arrayOfDouble1, arrayOfDouble2);
/*      */     
/*  695 */     return max3(arrayOfDouble1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(double paramDouble) {
/*  705 */     this.m00 += paramDouble;
/*  706 */     this.m01 += paramDouble;
/*  707 */     this.m02 += paramDouble;
/*      */     
/*  709 */     this.m10 += paramDouble;
/*  710 */     this.m11 += paramDouble;
/*  711 */     this.m12 += paramDouble;
/*      */     
/*  713 */     this.m20 += paramDouble;
/*  714 */     this.m21 += paramDouble;
/*  715 */     this.m22 += paramDouble;
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
/*      */   public final void add(double paramDouble, Matrix3d paramMatrix3d) {
/*  727 */     paramMatrix3d.m00 += paramDouble;
/*  728 */     paramMatrix3d.m01 += paramDouble;
/*  729 */     paramMatrix3d.m02 += paramDouble;
/*      */     
/*  731 */     paramMatrix3d.m10 += paramDouble;
/*  732 */     paramMatrix3d.m11 += paramDouble;
/*  733 */     paramMatrix3d.m12 += paramDouble;
/*      */     
/*  735 */     paramMatrix3d.m20 += paramDouble;
/*  736 */     paramMatrix3d.m21 += paramDouble;
/*  737 */     paramMatrix3d.m22 += paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix3d paramMatrix3d1, Matrix3d paramMatrix3d2) {
/*  747 */     paramMatrix3d1.m00 += paramMatrix3d2.m00;
/*  748 */     paramMatrix3d1.m01 += paramMatrix3d2.m01;
/*  749 */     paramMatrix3d1.m02 += paramMatrix3d2.m02;
/*      */     
/*  751 */     paramMatrix3d1.m10 += paramMatrix3d2.m10;
/*  752 */     paramMatrix3d1.m11 += paramMatrix3d2.m11;
/*  753 */     paramMatrix3d1.m12 += paramMatrix3d2.m12;
/*      */     
/*  755 */     paramMatrix3d1.m20 += paramMatrix3d2.m20;
/*  756 */     paramMatrix3d1.m21 += paramMatrix3d2.m21;
/*  757 */     paramMatrix3d1.m22 += paramMatrix3d2.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix3d paramMatrix3d) {
/*  766 */     this.m00 += paramMatrix3d.m00;
/*  767 */     this.m01 += paramMatrix3d.m01;
/*  768 */     this.m02 += paramMatrix3d.m02;
/*      */     
/*  770 */     this.m10 += paramMatrix3d.m10;
/*  771 */     this.m11 += paramMatrix3d.m11;
/*  772 */     this.m12 += paramMatrix3d.m12;
/*      */     
/*  774 */     this.m20 += paramMatrix3d.m20;
/*  775 */     this.m21 += paramMatrix3d.m21;
/*  776 */     this.m22 += paramMatrix3d.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix3d paramMatrix3d1, Matrix3d paramMatrix3d2) {
/*  787 */     paramMatrix3d1.m00 -= paramMatrix3d2.m00;
/*  788 */     paramMatrix3d1.m01 -= paramMatrix3d2.m01;
/*  789 */     paramMatrix3d1.m02 -= paramMatrix3d2.m02;
/*      */     
/*  791 */     paramMatrix3d1.m10 -= paramMatrix3d2.m10;
/*  792 */     paramMatrix3d1.m11 -= paramMatrix3d2.m11;
/*  793 */     paramMatrix3d1.m12 -= paramMatrix3d2.m12;
/*      */     
/*  795 */     paramMatrix3d1.m20 -= paramMatrix3d2.m20;
/*  796 */     paramMatrix3d1.m21 -= paramMatrix3d2.m21;
/*  797 */     paramMatrix3d1.m22 -= paramMatrix3d2.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix3d paramMatrix3d) {
/*  807 */     this.m00 -= paramMatrix3d.m00;
/*  808 */     this.m01 -= paramMatrix3d.m01;
/*  809 */     this.m02 -= paramMatrix3d.m02;
/*      */     
/*  811 */     this.m10 -= paramMatrix3d.m10;
/*  812 */     this.m11 -= paramMatrix3d.m11;
/*  813 */     this.m12 -= paramMatrix3d.m12;
/*      */     
/*  815 */     this.m20 -= paramMatrix3d.m20;
/*  816 */     this.m21 -= paramMatrix3d.m21;
/*  817 */     this.m22 -= paramMatrix3d.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose() {
/*  827 */     double d = this.m10;
/*  828 */     this.m10 = this.m01;
/*  829 */     this.m01 = d;
/*      */     
/*  831 */     d = this.m20;
/*  832 */     this.m20 = this.m02;
/*  833 */     this.m02 = d;
/*      */     
/*  835 */     d = this.m21;
/*  836 */     this.m21 = this.m12;
/*  837 */     this.m12 = d;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose(Matrix3d paramMatrix3d) {
/*  846 */     if (this != paramMatrix3d) {
/*  847 */       this.m00 = paramMatrix3d.m00;
/*  848 */       this.m01 = paramMatrix3d.m10;
/*  849 */       this.m02 = paramMatrix3d.m20;
/*      */       
/*  851 */       this.m10 = paramMatrix3d.m01;
/*  852 */       this.m11 = paramMatrix3d.m11;
/*  853 */       this.m12 = paramMatrix3d.m21;
/*      */       
/*  855 */       this.m20 = paramMatrix3d.m02;
/*  856 */       this.m21 = paramMatrix3d.m12;
/*  857 */       this.m22 = paramMatrix3d.m22;
/*      */     } else {
/*  859 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4d paramQuat4d) {
/*  869 */     this.m00 = 1.0D - 2.0D * paramQuat4d.y * paramQuat4d.y - 2.0D * paramQuat4d.z * paramQuat4d.z;
/*  870 */     this.m10 = 2.0D * (paramQuat4d.x * paramQuat4d.y + paramQuat4d.w * paramQuat4d.z);
/*  871 */     this.m20 = 2.0D * (paramQuat4d.x * paramQuat4d.z - paramQuat4d.w * paramQuat4d.y);
/*      */     
/*  873 */     this.m01 = 2.0D * (paramQuat4d.x * paramQuat4d.y - paramQuat4d.w * paramQuat4d.z);
/*  874 */     this.m11 = 1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.z * paramQuat4d.z;
/*  875 */     this.m21 = 2.0D * (paramQuat4d.y * paramQuat4d.z + paramQuat4d.w * paramQuat4d.x);
/*      */     
/*  877 */     this.m02 = 2.0D * (paramQuat4d.x * paramQuat4d.z + paramQuat4d.w * paramQuat4d.y);
/*  878 */     this.m12 = 2.0D * (paramQuat4d.y * paramQuat4d.z - paramQuat4d.w * paramQuat4d.x);
/*  879 */     this.m22 = 1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.y * paramQuat4d.y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4d paramAxisAngle4d) {
/*  889 */     double d = Math.sqrt(paramAxisAngle4d.x * paramAxisAngle4d.x + paramAxisAngle4d.y * paramAxisAngle4d.y + paramAxisAngle4d.z * paramAxisAngle4d.z);
/*      */     
/*  891 */     if (d < 1.110223024E-16D) {
/*  892 */       this.m00 = 1.0D;
/*  893 */       this.m01 = 0.0D;
/*  894 */       this.m02 = 0.0D;
/*      */       
/*  896 */       this.m10 = 0.0D;
/*  897 */       this.m11 = 1.0D;
/*  898 */       this.m12 = 0.0D;
/*      */       
/*  900 */       this.m20 = 0.0D;
/*  901 */       this.m21 = 0.0D;
/*  902 */       this.m22 = 1.0D;
/*      */     } else {
/*  904 */       d = 1.0D / d;
/*  905 */       double d1 = paramAxisAngle4d.x * d;
/*  906 */       double d2 = paramAxisAngle4d.y * d;
/*  907 */       double d3 = paramAxisAngle4d.z * d;
/*      */       
/*  909 */       double d4 = Math.sin(paramAxisAngle4d.angle);
/*  910 */       double d5 = Math.cos(paramAxisAngle4d.angle);
/*  911 */       double d6 = 1.0D - d5;
/*      */       
/*  913 */       double d7 = d1 * d3;
/*  914 */       double d8 = d1 * d2;
/*  915 */       double d9 = d2 * d3;
/*      */       
/*  917 */       this.m00 = d6 * d1 * d1 + d5;
/*  918 */       this.m01 = d6 * d8 - d4 * d3;
/*  919 */       this.m02 = d6 * d7 + d4 * d2;
/*      */       
/*  921 */       this.m10 = d6 * d8 + d4 * d3;
/*  922 */       this.m11 = d6 * d2 * d2 + d5;
/*  923 */       this.m12 = d6 * d9 - d4 * d1;
/*      */       
/*  925 */       this.m20 = d6 * d7 - d4 * d2;
/*  926 */       this.m21 = d6 * d9 + d4 * d1;
/*  927 */       this.m22 = d6 * d3 * d3 + d5;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4f paramQuat4f) {
/*  938 */     this.m00 = 1.0D - 2.0D * paramQuat4f.y * paramQuat4f.y - 2.0D * paramQuat4f.z * paramQuat4f.z;
/*  939 */     this.m10 = 2.0D * (paramQuat4f.x * paramQuat4f.y + paramQuat4f.w * paramQuat4f.z);
/*  940 */     this.m20 = 2.0D * (paramQuat4f.x * paramQuat4f.z - paramQuat4f.w * paramQuat4f.y);
/*      */     
/*  942 */     this.m01 = 2.0D * (paramQuat4f.x * paramQuat4f.y - paramQuat4f.w * paramQuat4f.z);
/*  943 */     this.m11 = 1.0D - 2.0D * paramQuat4f.x * paramQuat4f.x - 2.0D * paramQuat4f.z * paramQuat4f.z;
/*  944 */     this.m21 = 2.0D * (paramQuat4f.y * paramQuat4f.z + paramQuat4f.w * paramQuat4f.x);
/*      */     
/*  946 */     this.m02 = 2.0D * (paramQuat4f.x * paramQuat4f.z + paramQuat4f.w * paramQuat4f.y);
/*  947 */     this.m12 = 2.0D * (paramQuat4f.y * paramQuat4f.z - paramQuat4f.w * paramQuat4f.x);
/*  948 */     this.m22 = 1.0D - 2.0D * paramQuat4f.x * paramQuat4f.x - 2.0D * paramQuat4f.y * paramQuat4f.y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4f paramAxisAngle4f) {
/*  958 */     double d = Math.sqrt((paramAxisAngle4f.x * paramAxisAngle4f.x + paramAxisAngle4f.y * paramAxisAngle4f.y + paramAxisAngle4f.z * paramAxisAngle4f.z));
/*  959 */     if (d < 1.110223024E-16D) {
/*  960 */       this.m00 = 1.0D;
/*  961 */       this.m01 = 0.0D;
/*  962 */       this.m02 = 0.0D;
/*      */       
/*  964 */       this.m10 = 0.0D;
/*  965 */       this.m11 = 1.0D;
/*  966 */       this.m12 = 0.0D;
/*      */       
/*  968 */       this.m20 = 0.0D;
/*  969 */       this.m21 = 0.0D;
/*  970 */       this.m22 = 1.0D;
/*      */     } else {
/*  972 */       d = 1.0D / d;
/*  973 */       double d1 = paramAxisAngle4f.x * d;
/*  974 */       double d2 = paramAxisAngle4f.y * d;
/*  975 */       double d3 = paramAxisAngle4f.z * d;
/*  976 */       double d4 = Math.sin(paramAxisAngle4f.angle);
/*  977 */       double d5 = Math.cos(paramAxisAngle4f.angle);
/*  978 */       double d6 = 1.0D - d5;
/*      */       
/*  980 */       double d7 = d1 * d3;
/*  981 */       double d8 = d1 * d2;
/*  982 */       double d9 = d2 * d3;
/*      */       
/*  984 */       this.m00 = d6 * d1 * d1 + d5;
/*  985 */       this.m01 = d6 * d8 - d4 * d3;
/*  986 */       this.m02 = d6 * d7 + d4 * d2;
/*      */       
/*  988 */       this.m10 = d6 * d8 + d4 * d3;
/*  989 */       this.m11 = d6 * d2 * d2 + d5;
/*  990 */       this.m12 = d6 * d9 - d4 * d1;
/*      */       
/*  992 */       this.m20 = d6 * d7 - d4 * d2;
/*  993 */       this.m21 = d6 * d9 + d4 * d1;
/*  994 */       this.m22 = d6 * d3 * d3 + d5;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3f paramMatrix3f) {
/* 1005 */     this.m00 = paramMatrix3f.m00;
/* 1006 */     this.m01 = paramMatrix3f.m01;
/* 1007 */     this.m02 = paramMatrix3f.m02;
/*      */     
/* 1009 */     this.m10 = paramMatrix3f.m10;
/* 1010 */     this.m11 = paramMatrix3f.m11;
/* 1011 */     this.m12 = paramMatrix3f.m12;
/*      */     
/* 1013 */     this.m20 = paramMatrix3f.m20;
/* 1014 */     this.m21 = paramMatrix3f.m21;
/* 1015 */     this.m22 = paramMatrix3f.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3d paramMatrix3d) {
/* 1025 */     this.m00 = paramMatrix3d.m00;
/* 1026 */     this.m01 = paramMatrix3d.m01;
/* 1027 */     this.m02 = paramMatrix3d.m02;
/*      */     
/* 1029 */     this.m10 = paramMatrix3d.m10;
/* 1030 */     this.m11 = paramMatrix3d.m11;
/* 1031 */     this.m12 = paramMatrix3d.m12;
/*      */     
/* 1033 */     this.m20 = paramMatrix3d.m20;
/* 1034 */     this.m21 = paramMatrix3d.m21;
/* 1035 */     this.m22 = paramMatrix3d.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(double[] paramArrayOfdouble) {
/* 1046 */     this.m00 = paramArrayOfdouble[0];
/* 1047 */     this.m01 = paramArrayOfdouble[1];
/* 1048 */     this.m02 = paramArrayOfdouble[2];
/*      */     
/* 1050 */     this.m10 = paramArrayOfdouble[3];
/* 1051 */     this.m11 = paramArrayOfdouble[4];
/* 1052 */     this.m12 = paramArrayOfdouble[5];
/*      */     
/* 1054 */     this.m20 = paramArrayOfdouble[6];
/* 1055 */     this.m21 = paramArrayOfdouble[7];
/* 1056 */     this.m22 = paramArrayOfdouble[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert(Matrix3d paramMatrix3d) {
/* 1067 */     invertGeneral(paramMatrix3d);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/* 1075 */     invertGeneral(this);
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
/*      */   private final void invertGeneral(Matrix3d paramMatrix3d) {
/* 1087 */     double[] arrayOfDouble1 = new double[9];
/* 1088 */     int[] arrayOfInt = new int[3];
/*      */     
/* 1090 */     double[] arrayOfDouble2 = new double[9];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1096 */     arrayOfDouble2[0] = paramMatrix3d.m00;
/* 1097 */     arrayOfDouble2[1] = paramMatrix3d.m01;
/* 1098 */     arrayOfDouble2[2] = paramMatrix3d.m02;
/*      */     
/* 1100 */     arrayOfDouble2[3] = paramMatrix3d.m10;
/* 1101 */     arrayOfDouble2[4] = paramMatrix3d.m11;
/* 1102 */     arrayOfDouble2[5] = paramMatrix3d.m12;
/*      */     
/* 1104 */     arrayOfDouble2[6] = paramMatrix3d.m20;
/* 1105 */     arrayOfDouble2[7] = paramMatrix3d.m21;
/* 1106 */     arrayOfDouble2[8] = paramMatrix3d.m22;
/*      */ 
/*      */ 
/*      */     
/* 1110 */     if (!luDecomposition(arrayOfDouble2, arrayOfInt))
/*      */     {
/* 1112 */       throw new SingularMatrixException(VecMathI18N.getString("Matrix3d12"));
/*      */     }
/*      */ 
/*      */     
/* 1116 */     for (byte b = 0; b < 9; ) { arrayOfDouble1[b] = 0.0D; b++; }
/* 1117 */      arrayOfDouble1[0] = 1.0D; arrayOfDouble1[4] = 1.0D; arrayOfDouble1[8] = 1.0D;
/* 1118 */     luBacksubstitution(arrayOfDouble2, arrayOfInt, arrayOfDouble1);
/*      */     
/* 1120 */     this.m00 = arrayOfDouble1[0];
/* 1121 */     this.m01 = arrayOfDouble1[1];
/* 1122 */     this.m02 = arrayOfDouble1[2];
/*      */     
/* 1124 */     this.m10 = arrayOfDouble1[3];
/* 1125 */     this.m11 = arrayOfDouble1[4];
/* 1126 */     this.m12 = arrayOfDouble1[5];
/*      */     
/* 1128 */     this.m20 = arrayOfDouble1[6];
/* 1129 */     this.m21 = arrayOfDouble1[7];
/* 1130 */     this.m22 = arrayOfDouble1[8];
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
/* 1157 */     double[] arrayOfDouble = new double[3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1165 */     int j = 0;
/* 1166 */     int k = 0;
/*      */ 
/*      */     
/* 1169 */     int i = 3;
/* 1170 */     while (i-- != 0) {
/* 1171 */       double d = 0.0D;
/*      */ 
/*      */       
/* 1174 */       int m = 3;
/* 1175 */       while (m-- != 0) {
/* 1176 */         double d1 = paramArrayOfdouble[j++];
/* 1177 */         d1 = Math.abs(d1);
/* 1178 */         if (d1 > d) {
/* 1179 */           d = d1;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1184 */       if (d == 0.0D) {
/* 1185 */         return false;
/*      */       }
/* 1187 */       arrayOfDouble[k++] = 1.0D / d;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1195 */     byte b = 0;
/*      */ 
/*      */     
/* 1198 */     for (i = 0; i < 3; i++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1204 */       for (j = 0; j < i; j++) {
/* 1205 */         int n = b + 3 * j + i;
/* 1206 */         double d1 = paramArrayOfdouble[n];
/* 1207 */         int m = j;
/* 1208 */         int i1 = b + 3 * j;
/* 1209 */         int i2 = b + i;
/* 1210 */         while (m-- != 0) {
/* 1211 */           d1 -= paramArrayOfdouble[i1] * paramArrayOfdouble[i2];
/* 1212 */           i1++;
/* 1213 */           i2 += 3;
/*      */         } 
/* 1215 */         paramArrayOfdouble[n] = d1;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1220 */       double d = 0.0D;
/* 1221 */       k = -1;
/* 1222 */       for (j = i; j < 3; j++) {
/* 1223 */         int n = b + 3 * j + i;
/* 1224 */         double d1 = paramArrayOfdouble[n];
/* 1225 */         int m = i;
/* 1226 */         int i1 = b + 3 * j;
/* 1227 */         int i2 = b + i;
/* 1228 */         while (m-- != 0) {
/* 1229 */           d1 -= paramArrayOfdouble[i1] * paramArrayOfdouble[i2];
/* 1230 */           i1++;
/* 1231 */           i2 += 3;
/*      */         } 
/* 1233 */         paramArrayOfdouble[n] = d1;
/*      */         
/*      */         double d2;
/* 1236 */         if ((d2 = arrayOfDouble[j] * Math.abs(d1)) >= d) {
/* 1237 */           d = d2;
/* 1238 */           k = j;
/*      */         } 
/*      */       } 
/*      */       
/* 1242 */       if (k < 0) {
/* 1243 */         throw new RuntimeException(VecMathI18N.getString("Matrix3d13"));
/*      */       }
/*      */ 
/*      */       
/* 1247 */       if (i != k) {
/*      */         
/* 1249 */         int m = 3;
/* 1250 */         int n = b + 3 * k;
/* 1251 */         int i1 = b + 3 * i;
/* 1252 */         while (m-- != 0) {
/* 1253 */           double d1 = paramArrayOfdouble[n];
/* 1254 */           paramArrayOfdouble[n++] = paramArrayOfdouble[i1];
/* 1255 */           paramArrayOfdouble[i1++] = d1;
/*      */         } 
/*      */ 
/*      */         
/* 1259 */         arrayOfDouble[k] = arrayOfDouble[i];
/*      */       } 
/*      */ 
/*      */       
/* 1263 */       paramArrayOfint[i] = k;
/*      */ 
/*      */       
/* 1266 */       if (paramArrayOfdouble[b + 3 * i + i] == 0.0D) {
/* 1267 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1271 */       if (i != 2) {
/* 1272 */         double d1 = 1.0D / paramArrayOfdouble[b + 3 * i + i];
/* 1273 */         int m = b + 3 * (i + 1) + i;
/* 1274 */         j = 2 - i;
/* 1275 */         while (j-- != 0) {
/* 1276 */           paramArrayOfdouble[m] = paramArrayOfdouble[m] * d1;
/* 1277 */           m += 3;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1283 */     return true;
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
/* 1313 */     byte b2 = 0;
/*      */ 
/*      */     
/* 1316 */     for (byte b1 = 0; b1 < 3; b1++) {
/*      */       
/* 1318 */       byte b4 = b1;
/* 1319 */       byte b = -1;
/*      */ 
/*      */       
/* 1322 */       for (byte b3 = 0; b3 < 3; b3++) {
/*      */ 
/*      */         
/* 1325 */         int i = paramArrayOfint[b2 + b3];
/* 1326 */         double d = paramArrayOfdouble2[b4 + 3 * i];
/* 1327 */         paramArrayOfdouble2[b4 + 3 * i] = paramArrayOfdouble2[b4 + 3 * b3];
/* 1328 */         if (b >= 0) {
/*      */           
/* 1330 */           int j = b3 * 3;
/* 1331 */           for (byte b6 = b; b6 <= b3 - 1; b6++) {
/* 1332 */             d -= paramArrayOfdouble1[j + b6] * paramArrayOfdouble2[b4 + 3 * b6];
/*      */           }
/*      */         }
/* 1335 */         else if (d != 0.0D) {
/* 1336 */           b = b3;
/*      */         } 
/* 1338 */         paramArrayOfdouble2[b4 + 3 * b3] = d;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1343 */       byte b5 = 6;
/* 1344 */       paramArrayOfdouble2[b4 + 6] = paramArrayOfdouble2[b4 + 6] / paramArrayOfdouble1[b5 + 2];
/*      */       
/* 1346 */       b5 -= 3;
/* 1347 */       paramArrayOfdouble2[b4 + 3] = (paramArrayOfdouble2[b4 + 3] - paramArrayOfdouble1[b5 + 2] * paramArrayOfdouble2[b4 + 6]) / paramArrayOfdouble1[b5 + 1];
/*      */ 
/*      */       
/* 1350 */       b5 -= 3;
/* 1351 */       paramArrayOfdouble2[b4 + 0] = (paramArrayOfdouble2[b4 + 0] - paramArrayOfdouble1[b5 + 1] * paramArrayOfdouble2[b4 + 3] - paramArrayOfdouble1[b5 + 2] * paramArrayOfdouble2[b4 + 6]) / paramArrayOfdouble1[b5 + 0];
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
/*      */   
/*      */   public final double determinant() {
/* 1366 */     return this.m00 * (this.m11 * this.m22 - this.m12 * this.m21) + this.m01 * (this.m12 * this.m20 - this.m10 * this.m22) + this.m02 * (this.m10 * this.m21 - this.m11 * this.m20);
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
/*      */   public final void set(double paramDouble) {
/* 1379 */     this.m00 = paramDouble;
/* 1380 */     this.m01 = 0.0D;
/* 1381 */     this.m02 = 0.0D;
/*      */     
/* 1383 */     this.m10 = 0.0D;
/* 1384 */     this.m11 = paramDouble;
/* 1385 */     this.m12 = 0.0D;
/*      */     
/* 1387 */     this.m20 = 0.0D;
/* 1388 */     this.m21 = 0.0D;
/* 1389 */     this.m22 = paramDouble;
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
/*      */   public final void rotX(double paramDouble) {
/* 1401 */     double d1 = Math.sin(paramDouble);
/* 1402 */     double d2 = Math.cos(paramDouble);
/*      */     
/* 1404 */     this.m00 = 1.0D;
/* 1405 */     this.m01 = 0.0D;
/* 1406 */     this.m02 = 0.0D;
/*      */     
/* 1408 */     this.m10 = 0.0D;
/* 1409 */     this.m11 = d2;
/* 1410 */     this.m12 = -d1;
/*      */     
/* 1412 */     this.m20 = 0.0D;
/* 1413 */     this.m21 = d1;
/* 1414 */     this.m22 = d2;
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
/*      */   public final void rotY(double paramDouble) {
/* 1426 */     double d1 = Math.sin(paramDouble);
/* 1427 */     double d2 = Math.cos(paramDouble);
/*      */     
/* 1429 */     this.m00 = d2;
/* 1430 */     this.m01 = 0.0D;
/* 1431 */     this.m02 = d1;
/*      */     
/* 1433 */     this.m10 = 0.0D;
/* 1434 */     this.m11 = 1.0D;
/* 1435 */     this.m12 = 0.0D;
/*      */     
/* 1437 */     this.m20 = -d1;
/* 1438 */     this.m21 = 0.0D;
/* 1439 */     this.m22 = d2;
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
/*      */   public final void rotZ(double paramDouble) {
/* 1451 */     double d1 = Math.sin(paramDouble);
/* 1452 */     double d2 = Math.cos(paramDouble);
/*      */     
/* 1454 */     this.m00 = d2;
/* 1455 */     this.m01 = -d1;
/* 1456 */     this.m02 = 0.0D;
/*      */     
/* 1458 */     this.m10 = d1;
/* 1459 */     this.m11 = d2;
/* 1460 */     this.m12 = 0.0D;
/*      */     
/* 1462 */     this.m20 = 0.0D;
/* 1463 */     this.m21 = 0.0D;
/* 1464 */     this.m22 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(double paramDouble) {
/* 1473 */     this.m00 *= paramDouble;
/* 1474 */     this.m01 *= paramDouble;
/* 1475 */     this.m02 *= paramDouble;
/*      */     
/* 1477 */     this.m10 *= paramDouble;
/* 1478 */     this.m11 *= paramDouble;
/* 1479 */     this.m12 *= paramDouble;
/*      */     
/* 1481 */     this.m20 *= paramDouble;
/* 1482 */     this.m21 *= paramDouble;
/* 1483 */     this.m22 *= paramDouble;
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
/*      */   public final void mul(double paramDouble, Matrix3d paramMatrix3d) {
/* 1495 */     this.m00 = paramDouble * paramMatrix3d.m00;
/* 1496 */     this.m01 = paramDouble * paramMatrix3d.m01;
/* 1497 */     this.m02 = paramDouble * paramMatrix3d.m02;
/*      */     
/* 1499 */     this.m10 = paramDouble * paramMatrix3d.m10;
/* 1500 */     this.m11 = paramDouble * paramMatrix3d.m11;
/* 1501 */     this.m12 = paramDouble * paramMatrix3d.m12;
/*      */     
/* 1503 */     this.m20 = paramDouble * paramMatrix3d.m20;
/* 1504 */     this.m21 = paramDouble * paramMatrix3d.m21;
/* 1505 */     this.m22 = paramDouble * paramMatrix3d.m22;
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
/*      */   public final void mul(Matrix3d paramMatrix3d) {
/* 1520 */     double d1 = this.m00 * paramMatrix3d.m00 + this.m01 * paramMatrix3d.m10 + this.m02 * paramMatrix3d.m20;
/* 1521 */     double d2 = this.m00 * paramMatrix3d.m01 + this.m01 * paramMatrix3d.m11 + this.m02 * paramMatrix3d.m21;
/* 1522 */     double d3 = this.m00 * paramMatrix3d.m02 + this.m01 * paramMatrix3d.m12 + this.m02 * paramMatrix3d.m22;
/*      */     
/* 1524 */     double d4 = this.m10 * paramMatrix3d.m00 + this.m11 * paramMatrix3d.m10 + this.m12 * paramMatrix3d.m20;
/* 1525 */     double d5 = this.m10 * paramMatrix3d.m01 + this.m11 * paramMatrix3d.m11 + this.m12 * paramMatrix3d.m21;
/* 1526 */     double d6 = this.m10 * paramMatrix3d.m02 + this.m11 * paramMatrix3d.m12 + this.m12 * paramMatrix3d.m22;
/*      */     
/* 1528 */     double d7 = this.m20 * paramMatrix3d.m00 + this.m21 * paramMatrix3d.m10 + this.m22 * paramMatrix3d.m20;
/* 1529 */     double d8 = this.m20 * paramMatrix3d.m01 + this.m21 * paramMatrix3d.m11 + this.m22 * paramMatrix3d.m21;
/* 1530 */     double d9 = this.m20 * paramMatrix3d.m02 + this.m21 * paramMatrix3d.m12 + this.m22 * paramMatrix3d.m22;
/*      */     
/* 1532 */     this.m00 = d1; this.m01 = d2; this.m02 = d3;
/* 1533 */     this.m10 = d4; this.m11 = d5; this.m12 = d6;
/* 1534 */     this.m20 = d7; this.m21 = d8; this.m22 = d9;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(Matrix3d paramMatrix3d1, Matrix3d paramMatrix3d2) {
/* 1545 */     if (this != paramMatrix3d1 && this != paramMatrix3d2) {
/* 1546 */       this.m00 = paramMatrix3d1.m00 * paramMatrix3d2.m00 + paramMatrix3d1.m01 * paramMatrix3d2.m10 + paramMatrix3d1.m02 * paramMatrix3d2.m20;
/* 1547 */       this.m01 = paramMatrix3d1.m00 * paramMatrix3d2.m01 + paramMatrix3d1.m01 * paramMatrix3d2.m11 + paramMatrix3d1.m02 * paramMatrix3d2.m21;
/* 1548 */       this.m02 = paramMatrix3d1.m00 * paramMatrix3d2.m02 + paramMatrix3d1.m01 * paramMatrix3d2.m12 + paramMatrix3d1.m02 * paramMatrix3d2.m22;
/*      */       
/* 1550 */       this.m10 = paramMatrix3d1.m10 * paramMatrix3d2.m00 + paramMatrix3d1.m11 * paramMatrix3d2.m10 + paramMatrix3d1.m12 * paramMatrix3d2.m20;
/* 1551 */       this.m11 = paramMatrix3d1.m10 * paramMatrix3d2.m01 + paramMatrix3d1.m11 * paramMatrix3d2.m11 + paramMatrix3d1.m12 * paramMatrix3d2.m21;
/* 1552 */       this.m12 = paramMatrix3d1.m10 * paramMatrix3d2.m02 + paramMatrix3d1.m11 * paramMatrix3d2.m12 + paramMatrix3d1.m12 * paramMatrix3d2.m22;
/*      */       
/* 1554 */       this.m20 = paramMatrix3d1.m20 * paramMatrix3d2.m00 + paramMatrix3d1.m21 * paramMatrix3d2.m10 + paramMatrix3d1.m22 * paramMatrix3d2.m20;
/* 1555 */       this.m21 = paramMatrix3d1.m20 * paramMatrix3d2.m01 + paramMatrix3d1.m21 * paramMatrix3d2.m11 + paramMatrix3d1.m22 * paramMatrix3d2.m21;
/* 1556 */       this.m22 = paramMatrix3d1.m20 * paramMatrix3d2.m02 + paramMatrix3d1.m21 * paramMatrix3d2.m12 + paramMatrix3d1.m22 * paramMatrix3d2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1562 */       double d1 = paramMatrix3d1.m00 * paramMatrix3d2.m00 + paramMatrix3d1.m01 * paramMatrix3d2.m10 + paramMatrix3d1.m02 * paramMatrix3d2.m20;
/* 1563 */       double d2 = paramMatrix3d1.m00 * paramMatrix3d2.m01 + paramMatrix3d1.m01 * paramMatrix3d2.m11 + paramMatrix3d1.m02 * paramMatrix3d2.m21;
/* 1564 */       double d3 = paramMatrix3d1.m00 * paramMatrix3d2.m02 + paramMatrix3d1.m01 * paramMatrix3d2.m12 + paramMatrix3d1.m02 * paramMatrix3d2.m22;
/*      */       
/* 1566 */       double d4 = paramMatrix3d1.m10 * paramMatrix3d2.m00 + paramMatrix3d1.m11 * paramMatrix3d2.m10 + paramMatrix3d1.m12 * paramMatrix3d2.m20;
/* 1567 */       double d5 = paramMatrix3d1.m10 * paramMatrix3d2.m01 + paramMatrix3d1.m11 * paramMatrix3d2.m11 + paramMatrix3d1.m12 * paramMatrix3d2.m21;
/* 1568 */       double d6 = paramMatrix3d1.m10 * paramMatrix3d2.m02 + paramMatrix3d1.m11 * paramMatrix3d2.m12 + paramMatrix3d1.m12 * paramMatrix3d2.m22;
/*      */       
/* 1570 */       double d7 = paramMatrix3d1.m20 * paramMatrix3d2.m00 + paramMatrix3d1.m21 * paramMatrix3d2.m10 + paramMatrix3d1.m22 * paramMatrix3d2.m20;
/* 1571 */       double d8 = paramMatrix3d1.m20 * paramMatrix3d2.m01 + paramMatrix3d1.m21 * paramMatrix3d2.m11 + paramMatrix3d1.m22 * paramMatrix3d2.m21;
/* 1572 */       double d9 = paramMatrix3d1.m20 * paramMatrix3d2.m02 + paramMatrix3d1.m21 * paramMatrix3d2.m12 + paramMatrix3d1.m22 * paramMatrix3d2.m22;
/*      */       
/* 1574 */       this.m00 = d1; this.m01 = d2; this.m02 = d3;
/* 1575 */       this.m10 = d4; this.m11 = d5; this.m12 = d6;
/* 1576 */       this.m20 = d7; this.m21 = d8; this.m22 = d9;
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
/*      */   public final void mulNormalize(Matrix3d paramMatrix3d) {
/* 1588 */     double[] arrayOfDouble1 = new double[9];
/* 1589 */     double[] arrayOfDouble2 = new double[9];
/* 1590 */     double[] arrayOfDouble3 = new double[3];
/*      */     
/* 1592 */     arrayOfDouble1[0] = this.m00 * paramMatrix3d.m00 + this.m01 * paramMatrix3d.m10 + this.m02 * paramMatrix3d.m20;
/* 1593 */     arrayOfDouble1[1] = this.m00 * paramMatrix3d.m01 + this.m01 * paramMatrix3d.m11 + this.m02 * paramMatrix3d.m21;
/* 1594 */     arrayOfDouble1[2] = this.m00 * paramMatrix3d.m02 + this.m01 * paramMatrix3d.m12 + this.m02 * paramMatrix3d.m22;
/*      */     
/* 1596 */     arrayOfDouble1[3] = this.m10 * paramMatrix3d.m00 + this.m11 * paramMatrix3d.m10 + this.m12 * paramMatrix3d.m20;
/* 1597 */     arrayOfDouble1[4] = this.m10 * paramMatrix3d.m01 + this.m11 * paramMatrix3d.m11 + this.m12 * paramMatrix3d.m21;
/* 1598 */     arrayOfDouble1[5] = this.m10 * paramMatrix3d.m02 + this.m11 * paramMatrix3d.m12 + this.m12 * paramMatrix3d.m22;
/*      */     
/* 1600 */     arrayOfDouble1[6] = this.m20 * paramMatrix3d.m00 + this.m21 * paramMatrix3d.m10 + this.m22 * paramMatrix3d.m20;
/* 1601 */     arrayOfDouble1[7] = this.m20 * paramMatrix3d.m01 + this.m21 * paramMatrix3d.m11 + this.m22 * paramMatrix3d.m21;
/* 1602 */     arrayOfDouble1[8] = this.m20 * paramMatrix3d.m02 + this.m21 * paramMatrix3d.m12 + this.m22 * paramMatrix3d.m22;
/*      */     
/* 1604 */     compute_svd(arrayOfDouble1, arrayOfDouble3, arrayOfDouble2);
/*      */     
/* 1606 */     this.m00 = arrayOfDouble2[0];
/* 1607 */     this.m01 = arrayOfDouble2[1];
/* 1608 */     this.m02 = arrayOfDouble2[2];
/*      */     
/* 1610 */     this.m10 = arrayOfDouble2[3];
/* 1611 */     this.m11 = arrayOfDouble2[4];
/* 1612 */     this.m12 = arrayOfDouble2[5];
/*      */     
/* 1614 */     this.m20 = arrayOfDouble2[6];
/* 1615 */     this.m21 = arrayOfDouble2[7];
/* 1616 */     this.m22 = arrayOfDouble2[8];
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
/*      */   public final void mulNormalize(Matrix3d paramMatrix3d1, Matrix3d paramMatrix3d2) {
/* 1630 */     double[] arrayOfDouble1 = new double[9];
/* 1631 */     double[] arrayOfDouble2 = new double[9];
/* 1632 */     double[] arrayOfDouble3 = new double[3];
/*      */     
/* 1634 */     arrayOfDouble1[0] = paramMatrix3d1.m00 * paramMatrix3d2.m00 + paramMatrix3d1.m01 * paramMatrix3d2.m10 + paramMatrix3d1.m02 * paramMatrix3d2.m20;
/* 1635 */     arrayOfDouble1[1] = paramMatrix3d1.m00 * paramMatrix3d2.m01 + paramMatrix3d1.m01 * paramMatrix3d2.m11 + paramMatrix3d1.m02 * paramMatrix3d2.m21;
/* 1636 */     arrayOfDouble1[2] = paramMatrix3d1.m00 * paramMatrix3d2.m02 + paramMatrix3d1.m01 * paramMatrix3d2.m12 + paramMatrix3d1.m02 * paramMatrix3d2.m22;
/*      */     
/* 1638 */     arrayOfDouble1[3] = paramMatrix3d1.m10 * paramMatrix3d2.m00 + paramMatrix3d1.m11 * paramMatrix3d2.m10 + paramMatrix3d1.m12 * paramMatrix3d2.m20;
/* 1639 */     arrayOfDouble1[4] = paramMatrix3d1.m10 * paramMatrix3d2.m01 + paramMatrix3d1.m11 * paramMatrix3d2.m11 + paramMatrix3d1.m12 * paramMatrix3d2.m21;
/* 1640 */     arrayOfDouble1[5] = paramMatrix3d1.m10 * paramMatrix3d2.m02 + paramMatrix3d1.m11 * paramMatrix3d2.m12 + paramMatrix3d1.m12 * paramMatrix3d2.m22;
/*      */     
/* 1642 */     arrayOfDouble1[6] = paramMatrix3d1.m20 * paramMatrix3d2.m00 + paramMatrix3d1.m21 * paramMatrix3d2.m10 + paramMatrix3d1.m22 * paramMatrix3d2.m20;
/* 1643 */     arrayOfDouble1[7] = paramMatrix3d1.m20 * paramMatrix3d2.m01 + paramMatrix3d1.m21 * paramMatrix3d2.m11 + paramMatrix3d1.m22 * paramMatrix3d2.m21;
/* 1644 */     arrayOfDouble1[8] = paramMatrix3d1.m20 * paramMatrix3d2.m02 + paramMatrix3d1.m21 * paramMatrix3d2.m12 + paramMatrix3d1.m22 * paramMatrix3d2.m22;
/*      */     
/* 1646 */     compute_svd(arrayOfDouble1, arrayOfDouble3, arrayOfDouble2);
/*      */     
/* 1648 */     this.m00 = arrayOfDouble2[0];
/* 1649 */     this.m01 = arrayOfDouble2[1];
/* 1650 */     this.m02 = arrayOfDouble2[2];
/*      */     
/* 1652 */     this.m10 = arrayOfDouble2[3];
/* 1653 */     this.m11 = arrayOfDouble2[4];
/* 1654 */     this.m12 = arrayOfDouble2[5];
/*      */     
/* 1656 */     this.m20 = arrayOfDouble2[6];
/* 1657 */     this.m21 = arrayOfDouble2[7];
/* 1658 */     this.m22 = arrayOfDouble2[8];
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
/*      */   public final void mulTransposeBoth(Matrix3d paramMatrix3d1, Matrix3d paramMatrix3d2) {
/* 1670 */     if (this != paramMatrix3d1 && this != paramMatrix3d2) {
/* 1671 */       this.m00 = paramMatrix3d1.m00 * paramMatrix3d2.m00 + paramMatrix3d1.m10 * paramMatrix3d2.m01 + paramMatrix3d1.m20 * paramMatrix3d2.m02;
/* 1672 */       this.m01 = paramMatrix3d1.m00 * paramMatrix3d2.m10 + paramMatrix3d1.m10 * paramMatrix3d2.m11 + paramMatrix3d1.m20 * paramMatrix3d2.m12;
/* 1673 */       this.m02 = paramMatrix3d1.m00 * paramMatrix3d2.m20 + paramMatrix3d1.m10 * paramMatrix3d2.m21 + paramMatrix3d1.m20 * paramMatrix3d2.m22;
/*      */       
/* 1675 */       this.m10 = paramMatrix3d1.m01 * paramMatrix3d2.m00 + paramMatrix3d1.m11 * paramMatrix3d2.m01 + paramMatrix3d1.m21 * paramMatrix3d2.m02;
/* 1676 */       this.m11 = paramMatrix3d1.m01 * paramMatrix3d2.m10 + paramMatrix3d1.m11 * paramMatrix3d2.m11 + paramMatrix3d1.m21 * paramMatrix3d2.m12;
/* 1677 */       this.m12 = paramMatrix3d1.m01 * paramMatrix3d2.m20 + paramMatrix3d1.m11 * paramMatrix3d2.m21 + paramMatrix3d1.m21 * paramMatrix3d2.m22;
/*      */       
/* 1679 */       this.m20 = paramMatrix3d1.m02 * paramMatrix3d2.m00 + paramMatrix3d1.m12 * paramMatrix3d2.m01 + paramMatrix3d1.m22 * paramMatrix3d2.m02;
/* 1680 */       this.m21 = paramMatrix3d1.m02 * paramMatrix3d2.m10 + paramMatrix3d1.m12 * paramMatrix3d2.m11 + paramMatrix3d1.m22 * paramMatrix3d2.m12;
/* 1681 */       this.m22 = paramMatrix3d1.m02 * paramMatrix3d2.m20 + paramMatrix3d1.m12 * paramMatrix3d2.m21 + paramMatrix3d1.m22 * paramMatrix3d2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1687 */       double d1 = paramMatrix3d1.m00 * paramMatrix3d2.m00 + paramMatrix3d1.m10 * paramMatrix3d2.m01 + paramMatrix3d1.m20 * paramMatrix3d2.m02;
/* 1688 */       double d2 = paramMatrix3d1.m00 * paramMatrix3d2.m10 + paramMatrix3d1.m10 * paramMatrix3d2.m11 + paramMatrix3d1.m20 * paramMatrix3d2.m12;
/* 1689 */       double d3 = paramMatrix3d1.m00 * paramMatrix3d2.m20 + paramMatrix3d1.m10 * paramMatrix3d2.m21 + paramMatrix3d1.m20 * paramMatrix3d2.m22;
/*      */       
/* 1691 */       double d4 = paramMatrix3d1.m01 * paramMatrix3d2.m00 + paramMatrix3d1.m11 * paramMatrix3d2.m01 + paramMatrix3d1.m21 * paramMatrix3d2.m02;
/* 1692 */       double d5 = paramMatrix3d1.m01 * paramMatrix3d2.m10 + paramMatrix3d1.m11 * paramMatrix3d2.m11 + paramMatrix3d1.m21 * paramMatrix3d2.m12;
/* 1693 */       double d6 = paramMatrix3d1.m01 * paramMatrix3d2.m20 + paramMatrix3d1.m11 * paramMatrix3d2.m21 + paramMatrix3d1.m21 * paramMatrix3d2.m22;
/*      */       
/* 1695 */       double d7 = paramMatrix3d1.m02 * paramMatrix3d2.m00 + paramMatrix3d1.m12 * paramMatrix3d2.m01 + paramMatrix3d1.m22 * paramMatrix3d2.m02;
/* 1696 */       double d8 = paramMatrix3d1.m02 * paramMatrix3d2.m10 + paramMatrix3d1.m12 * paramMatrix3d2.m11 + paramMatrix3d1.m22 * paramMatrix3d2.m12;
/* 1697 */       double d9 = paramMatrix3d1.m02 * paramMatrix3d2.m20 + paramMatrix3d1.m12 * paramMatrix3d2.m21 + paramMatrix3d1.m22 * paramMatrix3d2.m22;
/*      */       
/* 1699 */       this.m00 = d1; this.m01 = d2; this.m02 = d3;
/* 1700 */       this.m10 = d4; this.m11 = d5; this.m12 = d6;
/* 1701 */       this.m20 = d7; this.m21 = d8; this.m22 = d9;
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
/*      */   public final void mulTransposeRight(Matrix3d paramMatrix3d1, Matrix3d paramMatrix3d2) {
/* 1714 */     if (this != paramMatrix3d1 && this != paramMatrix3d2) {
/* 1715 */       this.m00 = paramMatrix3d1.m00 * paramMatrix3d2.m00 + paramMatrix3d1.m01 * paramMatrix3d2.m01 + paramMatrix3d1.m02 * paramMatrix3d2.m02;
/* 1716 */       this.m01 = paramMatrix3d1.m00 * paramMatrix3d2.m10 + paramMatrix3d1.m01 * paramMatrix3d2.m11 + paramMatrix3d1.m02 * paramMatrix3d2.m12;
/* 1717 */       this.m02 = paramMatrix3d1.m00 * paramMatrix3d2.m20 + paramMatrix3d1.m01 * paramMatrix3d2.m21 + paramMatrix3d1.m02 * paramMatrix3d2.m22;
/*      */       
/* 1719 */       this.m10 = paramMatrix3d1.m10 * paramMatrix3d2.m00 + paramMatrix3d1.m11 * paramMatrix3d2.m01 + paramMatrix3d1.m12 * paramMatrix3d2.m02;
/* 1720 */       this.m11 = paramMatrix3d1.m10 * paramMatrix3d2.m10 + paramMatrix3d1.m11 * paramMatrix3d2.m11 + paramMatrix3d1.m12 * paramMatrix3d2.m12;
/* 1721 */       this.m12 = paramMatrix3d1.m10 * paramMatrix3d2.m20 + paramMatrix3d1.m11 * paramMatrix3d2.m21 + paramMatrix3d1.m12 * paramMatrix3d2.m22;
/*      */       
/* 1723 */       this.m20 = paramMatrix3d1.m20 * paramMatrix3d2.m00 + paramMatrix3d1.m21 * paramMatrix3d2.m01 + paramMatrix3d1.m22 * paramMatrix3d2.m02;
/* 1724 */       this.m21 = paramMatrix3d1.m20 * paramMatrix3d2.m10 + paramMatrix3d1.m21 * paramMatrix3d2.m11 + paramMatrix3d1.m22 * paramMatrix3d2.m12;
/* 1725 */       this.m22 = paramMatrix3d1.m20 * paramMatrix3d2.m20 + paramMatrix3d1.m21 * paramMatrix3d2.m21 + paramMatrix3d1.m22 * paramMatrix3d2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1731 */       double d1 = paramMatrix3d1.m00 * paramMatrix3d2.m00 + paramMatrix3d1.m01 * paramMatrix3d2.m01 + paramMatrix3d1.m02 * paramMatrix3d2.m02;
/* 1732 */       double d2 = paramMatrix3d1.m00 * paramMatrix3d2.m10 + paramMatrix3d1.m01 * paramMatrix3d2.m11 + paramMatrix3d1.m02 * paramMatrix3d2.m12;
/* 1733 */       double d3 = paramMatrix3d1.m00 * paramMatrix3d2.m20 + paramMatrix3d1.m01 * paramMatrix3d2.m21 + paramMatrix3d1.m02 * paramMatrix3d2.m22;
/*      */       
/* 1735 */       double d4 = paramMatrix3d1.m10 * paramMatrix3d2.m00 + paramMatrix3d1.m11 * paramMatrix3d2.m01 + paramMatrix3d1.m12 * paramMatrix3d2.m02;
/* 1736 */       double d5 = paramMatrix3d1.m10 * paramMatrix3d2.m10 + paramMatrix3d1.m11 * paramMatrix3d2.m11 + paramMatrix3d1.m12 * paramMatrix3d2.m12;
/* 1737 */       double d6 = paramMatrix3d1.m10 * paramMatrix3d2.m20 + paramMatrix3d1.m11 * paramMatrix3d2.m21 + paramMatrix3d1.m12 * paramMatrix3d2.m22;
/*      */       
/* 1739 */       double d7 = paramMatrix3d1.m20 * paramMatrix3d2.m00 + paramMatrix3d1.m21 * paramMatrix3d2.m01 + paramMatrix3d1.m22 * paramMatrix3d2.m02;
/* 1740 */       double d8 = paramMatrix3d1.m20 * paramMatrix3d2.m10 + paramMatrix3d1.m21 * paramMatrix3d2.m11 + paramMatrix3d1.m22 * paramMatrix3d2.m12;
/* 1741 */       double d9 = paramMatrix3d1.m20 * paramMatrix3d2.m20 + paramMatrix3d1.m21 * paramMatrix3d2.m21 + paramMatrix3d1.m22 * paramMatrix3d2.m22;
/*      */       
/* 1743 */       this.m00 = d1; this.m01 = d2; this.m02 = d3;
/* 1744 */       this.m10 = d4; this.m11 = d5; this.m12 = d6;
/* 1745 */       this.m20 = d7; this.m21 = d8; this.m22 = d9;
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
/*      */   public final void mulTransposeLeft(Matrix3d paramMatrix3d1, Matrix3d paramMatrix3d2) {
/* 1757 */     if (this != paramMatrix3d1 && this != paramMatrix3d2) {
/* 1758 */       this.m00 = paramMatrix3d1.m00 * paramMatrix3d2.m00 + paramMatrix3d1.m10 * paramMatrix3d2.m10 + paramMatrix3d1.m20 * paramMatrix3d2.m20;
/* 1759 */       this.m01 = paramMatrix3d1.m00 * paramMatrix3d2.m01 + paramMatrix3d1.m10 * paramMatrix3d2.m11 + paramMatrix3d1.m20 * paramMatrix3d2.m21;
/* 1760 */       this.m02 = paramMatrix3d1.m00 * paramMatrix3d2.m02 + paramMatrix3d1.m10 * paramMatrix3d2.m12 + paramMatrix3d1.m20 * paramMatrix3d2.m22;
/*      */       
/* 1762 */       this.m10 = paramMatrix3d1.m01 * paramMatrix3d2.m00 + paramMatrix3d1.m11 * paramMatrix3d2.m10 + paramMatrix3d1.m21 * paramMatrix3d2.m20;
/* 1763 */       this.m11 = paramMatrix3d1.m01 * paramMatrix3d2.m01 + paramMatrix3d1.m11 * paramMatrix3d2.m11 + paramMatrix3d1.m21 * paramMatrix3d2.m21;
/* 1764 */       this.m12 = paramMatrix3d1.m01 * paramMatrix3d2.m02 + paramMatrix3d1.m11 * paramMatrix3d2.m12 + paramMatrix3d1.m21 * paramMatrix3d2.m22;
/*      */       
/* 1766 */       this.m20 = paramMatrix3d1.m02 * paramMatrix3d2.m00 + paramMatrix3d1.m12 * paramMatrix3d2.m10 + paramMatrix3d1.m22 * paramMatrix3d2.m20;
/* 1767 */       this.m21 = paramMatrix3d1.m02 * paramMatrix3d2.m01 + paramMatrix3d1.m12 * paramMatrix3d2.m11 + paramMatrix3d1.m22 * paramMatrix3d2.m21;
/* 1768 */       this.m22 = paramMatrix3d1.m02 * paramMatrix3d2.m02 + paramMatrix3d1.m12 * paramMatrix3d2.m12 + paramMatrix3d1.m22 * paramMatrix3d2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1774 */       double d1 = paramMatrix3d1.m00 * paramMatrix3d2.m00 + paramMatrix3d1.m10 * paramMatrix3d2.m10 + paramMatrix3d1.m20 * paramMatrix3d2.m20;
/* 1775 */       double d2 = paramMatrix3d1.m00 * paramMatrix3d2.m01 + paramMatrix3d1.m10 * paramMatrix3d2.m11 + paramMatrix3d1.m20 * paramMatrix3d2.m21;
/* 1776 */       double d3 = paramMatrix3d1.m00 * paramMatrix3d2.m02 + paramMatrix3d1.m10 * paramMatrix3d2.m12 + paramMatrix3d1.m20 * paramMatrix3d2.m22;
/*      */       
/* 1778 */       double d4 = paramMatrix3d1.m01 * paramMatrix3d2.m00 + paramMatrix3d1.m11 * paramMatrix3d2.m10 + paramMatrix3d1.m21 * paramMatrix3d2.m20;
/* 1779 */       double d5 = paramMatrix3d1.m01 * paramMatrix3d2.m01 + paramMatrix3d1.m11 * paramMatrix3d2.m11 + paramMatrix3d1.m21 * paramMatrix3d2.m21;
/* 1780 */       double d6 = paramMatrix3d1.m01 * paramMatrix3d2.m02 + paramMatrix3d1.m11 * paramMatrix3d2.m12 + paramMatrix3d1.m21 * paramMatrix3d2.m22;
/*      */       
/* 1782 */       double d7 = paramMatrix3d1.m02 * paramMatrix3d2.m00 + paramMatrix3d1.m12 * paramMatrix3d2.m10 + paramMatrix3d1.m22 * paramMatrix3d2.m20;
/* 1783 */       double d8 = paramMatrix3d1.m02 * paramMatrix3d2.m01 + paramMatrix3d1.m12 * paramMatrix3d2.m11 + paramMatrix3d1.m22 * paramMatrix3d2.m21;
/* 1784 */       double d9 = paramMatrix3d1.m02 * paramMatrix3d2.m02 + paramMatrix3d1.m12 * paramMatrix3d2.m12 + paramMatrix3d1.m22 * paramMatrix3d2.m22;
/*      */       
/* 1786 */       this.m00 = d1; this.m01 = d2; this.m02 = d3;
/* 1787 */       this.m10 = d4; this.m11 = d5; this.m12 = d6;
/* 1788 */       this.m20 = d7; this.m21 = d8; this.m22 = d9;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalize() {
/* 1798 */     double[] arrayOfDouble1 = new double[9];
/* 1799 */     double[] arrayOfDouble2 = new double[3];
/*      */     
/* 1801 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/* 1803 */     this.m00 = arrayOfDouble1[0];
/* 1804 */     this.m01 = arrayOfDouble1[1];
/* 1805 */     this.m02 = arrayOfDouble1[2];
/*      */     
/* 1807 */     this.m10 = arrayOfDouble1[3];
/* 1808 */     this.m11 = arrayOfDouble1[4];
/* 1809 */     this.m12 = arrayOfDouble1[5];
/*      */     
/* 1811 */     this.m20 = arrayOfDouble1[6];
/* 1812 */     this.m21 = arrayOfDouble1[7];
/* 1813 */     this.m22 = arrayOfDouble1[8];
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
/*      */   public final void normalize(Matrix3d paramMatrix3d) {
/* 1825 */     double[] arrayOfDouble1 = new double[9];
/* 1826 */     double[] arrayOfDouble2 = new double[9];
/* 1827 */     double[] arrayOfDouble3 = new double[3];
/*      */     
/* 1829 */     arrayOfDouble1[0] = paramMatrix3d.m00;
/* 1830 */     arrayOfDouble1[1] = paramMatrix3d.m01;
/* 1831 */     arrayOfDouble1[2] = paramMatrix3d.m02;
/*      */     
/* 1833 */     arrayOfDouble1[3] = paramMatrix3d.m10;
/* 1834 */     arrayOfDouble1[4] = paramMatrix3d.m11;
/* 1835 */     arrayOfDouble1[5] = paramMatrix3d.m12;
/*      */     
/* 1837 */     arrayOfDouble1[6] = paramMatrix3d.m20;
/* 1838 */     arrayOfDouble1[7] = paramMatrix3d.m21;
/* 1839 */     arrayOfDouble1[8] = paramMatrix3d.m22;
/*      */     
/* 1841 */     compute_svd(arrayOfDouble1, arrayOfDouble3, arrayOfDouble2);
/*      */     
/* 1843 */     this.m00 = arrayOfDouble2[0];
/* 1844 */     this.m01 = arrayOfDouble2[1];
/* 1845 */     this.m02 = arrayOfDouble2[2];
/*      */     
/* 1847 */     this.m10 = arrayOfDouble2[3];
/* 1848 */     this.m11 = arrayOfDouble2[4];
/* 1849 */     this.m12 = arrayOfDouble2[5];
/*      */     
/* 1851 */     this.m20 = arrayOfDouble2[6];
/* 1852 */     this.m21 = arrayOfDouble2[7];
/* 1853 */     this.m22 = arrayOfDouble2[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalizeCP() {
/* 1863 */     double d = 1.0D / Math.sqrt(this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20);
/* 1864 */     this.m00 *= d;
/* 1865 */     this.m10 *= d;
/* 1866 */     this.m20 *= d;
/*      */     
/* 1868 */     d = 1.0D / Math.sqrt(this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21);
/* 1869 */     this.m01 *= d;
/* 1870 */     this.m11 *= d;
/* 1871 */     this.m21 *= d;
/*      */     
/* 1873 */     this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
/* 1874 */     this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
/* 1875 */     this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalizeCP(Matrix3d paramMatrix3d) {
/* 1886 */     double d = 1.0D / Math.sqrt(paramMatrix3d.m00 * paramMatrix3d.m00 + paramMatrix3d.m10 * paramMatrix3d.m10 + paramMatrix3d.m20 * paramMatrix3d.m20);
/* 1887 */     paramMatrix3d.m00 *= d;
/* 1888 */     paramMatrix3d.m10 *= d;
/* 1889 */     paramMatrix3d.m20 *= d;
/*      */     
/* 1891 */     d = 1.0D / Math.sqrt(paramMatrix3d.m01 * paramMatrix3d.m01 + paramMatrix3d.m11 * paramMatrix3d.m11 + paramMatrix3d.m21 * paramMatrix3d.m21);
/* 1892 */     paramMatrix3d.m01 *= d;
/* 1893 */     paramMatrix3d.m11 *= d;
/* 1894 */     paramMatrix3d.m21 *= d;
/*      */     
/* 1896 */     this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
/* 1897 */     this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
/* 1898 */     this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Matrix3d paramMatrix3d) {
/*      */     try {
/* 1910 */       return (this.m00 == paramMatrix3d.m00 && this.m01 == paramMatrix3d.m01 && this.m02 == paramMatrix3d.m02 && this.m10 == paramMatrix3d.m10 && this.m11 == paramMatrix3d.m11 && this.m12 == paramMatrix3d.m12 && this.m20 == paramMatrix3d.m20 && this.m21 == paramMatrix3d.m21 && this.m22 == paramMatrix3d.m22);
/*      */     }
/*      */     catch (NullPointerException nullPointerException) {
/*      */       
/* 1914 */       return false;
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
/*      */   public boolean equals(Object paramObject) {
/*      */     
/* 1928 */     try { Matrix3d matrix3d = (Matrix3d)paramObject;
/* 1929 */       return (this.m00 == matrix3d.m00 && this.m01 == matrix3d.m01 && this.m02 == matrix3d.m02 && this.m10 == matrix3d.m10 && this.m11 == matrix3d.m11 && this.m12 == matrix3d.m12 && this.m20 == matrix3d.m20 && this.m21 == matrix3d.m21 && this.m22 == matrix3d.m22); }
/*      */     
/*      */     catch (ClassCastException classCastException)
/*      */     
/* 1933 */     { return false; }
/* 1934 */     catch (NullPointerException nullPointerException) { return false; }
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
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean epsilonEquals(Matrix3d paramMatrix3d, double paramDouble) {
/* 1951 */     double d = this.m00 - paramMatrix3d.m00;
/* 1952 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 1954 */     d = this.m01 - paramMatrix3d.m01;
/* 1955 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 1957 */     d = this.m02 - paramMatrix3d.m02;
/* 1958 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 1960 */     d = this.m10 - paramMatrix3d.m10;
/* 1961 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 1963 */     d = this.m11 - paramMatrix3d.m11;
/* 1964 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 1966 */     d = this.m12 - paramMatrix3d.m12;
/* 1967 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 1969 */     d = this.m20 - paramMatrix3d.m20;
/* 1970 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 1972 */     d = this.m21 - paramMatrix3d.m21;
/* 1973 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 1975 */     d = this.m22 - paramMatrix3d.m22;
/* 1976 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 1978 */     return true;
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
/*      */   public int hashCode() {
/* 1991 */     long l = 1L;
/* 1992 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m00);
/* 1993 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m01);
/* 1994 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m02);
/* 1995 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m10);
/* 1996 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m11);
/* 1997 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m12);
/* 1998 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m20);
/* 1999 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m21);
/* 2000 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m22);
/* 2001 */     return (int)(l ^ l >> 32L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/* 2010 */     this.m00 = 0.0D;
/* 2011 */     this.m01 = 0.0D;
/* 2012 */     this.m02 = 0.0D;
/*      */     
/* 2014 */     this.m10 = 0.0D;
/* 2015 */     this.m11 = 0.0D;
/* 2016 */     this.m12 = 0.0D;
/*      */     
/* 2018 */     this.m20 = 0.0D;
/* 2019 */     this.m21 = 0.0D;
/* 2020 */     this.m22 = 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/* 2029 */     this.m00 = -this.m00;
/* 2030 */     this.m01 = -this.m01;
/* 2031 */     this.m02 = -this.m02;
/*      */     
/* 2033 */     this.m10 = -this.m10;
/* 2034 */     this.m11 = -this.m11;
/* 2035 */     this.m12 = -this.m12;
/*      */     
/* 2037 */     this.m20 = -this.m20;
/* 2038 */     this.m21 = -this.m21;
/* 2039 */     this.m22 = -this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate(Matrix3d paramMatrix3d) {
/* 2050 */     this.m00 = -paramMatrix3d.m00;
/* 2051 */     this.m01 = -paramMatrix3d.m01;
/* 2052 */     this.m02 = -paramMatrix3d.m02;
/*      */     
/* 2054 */     this.m10 = -paramMatrix3d.m10;
/* 2055 */     this.m11 = -paramMatrix3d.m11;
/* 2056 */     this.m12 = -paramMatrix3d.m12;
/*      */     
/* 2058 */     this.m20 = -paramMatrix3d.m20;
/* 2059 */     this.m21 = -paramMatrix3d.m21;
/* 2060 */     this.m22 = -paramMatrix3d.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Tuple3d paramTuple3d) {
/* 2071 */     double d1 = this.m00 * paramTuple3d.x + this.m01 * paramTuple3d.y + this.m02 * paramTuple3d.z;
/* 2072 */     double d2 = this.m10 * paramTuple3d.x + this.m11 * paramTuple3d.y + this.m12 * paramTuple3d.z;
/* 2073 */     double d3 = this.m20 * paramTuple3d.x + this.m21 * paramTuple3d.y + this.m22 * paramTuple3d.z;
/* 2074 */     paramTuple3d.set(d1, d2, d3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Tuple3d paramTuple3d1, Tuple3d paramTuple3d2) {
/* 2085 */     double d1 = this.m00 * paramTuple3d1.x + this.m01 * paramTuple3d1.y + this.m02 * paramTuple3d1.z;
/* 2086 */     double d2 = this.m10 * paramTuple3d1.x + this.m11 * paramTuple3d1.y + this.m12 * paramTuple3d1.z;
/* 2087 */     paramTuple3d2.z = this.m20 * paramTuple3d1.x + this.m21 * paramTuple3d1.y + this.m22 * paramTuple3d1.z;
/* 2088 */     paramTuple3d2.x = d1;
/* 2089 */     paramTuple3d2.y = d2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void getScaleRotate(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
/* 2097 */     double[] arrayOfDouble = new double[9];
/*      */     
/* 2099 */     arrayOfDouble[0] = this.m00;
/* 2100 */     arrayOfDouble[1] = this.m01;
/* 2101 */     arrayOfDouble[2] = this.m02;
/*      */     
/* 2103 */     arrayOfDouble[3] = this.m10;
/* 2104 */     arrayOfDouble[4] = this.m11;
/* 2105 */     arrayOfDouble[5] = this.m12;
/*      */     
/* 2107 */     arrayOfDouble[6] = this.m20;
/* 2108 */     arrayOfDouble[7] = this.m21;
/* 2109 */     arrayOfDouble[8] = this.m22;
/* 2110 */     compute_svd(arrayOfDouble, paramArrayOfdouble1, paramArrayOfdouble2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void compute_svd(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, double[] paramArrayOfdouble3) {
/* 2118 */     double[] arrayOfDouble1 = new double[9];
/* 2119 */     double[] arrayOfDouble2 = new double[9];
/* 2120 */     double[] arrayOfDouble3 = new double[9];
/* 2121 */     double[] arrayOfDouble4 = new double[9];
/*      */     
/* 2123 */     double[] arrayOfDouble5 = arrayOfDouble3;
/* 2124 */     double[] arrayOfDouble6 = arrayOfDouble4;
/*      */     
/* 2126 */     double[] arrayOfDouble7 = new double[9];
/* 2127 */     double[] arrayOfDouble8 = new double[3];
/* 2128 */     double[] arrayOfDouble9 = new double[3];
/*      */     
/* 2130 */     byte b2 = 0;
/*      */ 
/*      */ 
/*      */     
/*      */     byte b1;
/*      */ 
/*      */     
/* 2137 */     for (b1 = 0; b1 < 9; b1++) {
/* 2138 */       arrayOfDouble7[b1] = paramArrayOfdouble1[b1];
/*      */     }
/*      */ 
/*      */     
/* 2142 */     if (paramArrayOfdouble1[3] * paramArrayOfdouble1[3] < 1.110223024E-16D) {
/* 2143 */       arrayOfDouble1[0] = 1.0D; arrayOfDouble1[1] = 0.0D; arrayOfDouble1[2] = 0.0D;
/* 2144 */       arrayOfDouble1[3] = 0.0D; arrayOfDouble1[4] = 1.0D; arrayOfDouble1[5] = 0.0D;
/* 2145 */       arrayOfDouble1[6] = 0.0D; arrayOfDouble1[7] = 0.0D; arrayOfDouble1[8] = 1.0D;
/* 2146 */     } else if (paramArrayOfdouble1[0] * paramArrayOfdouble1[0] < 1.110223024E-16D) {
/* 2147 */       arrayOfDouble5[0] = paramArrayOfdouble1[0];
/* 2148 */       arrayOfDouble5[1] = paramArrayOfdouble1[1];
/* 2149 */       arrayOfDouble5[2] = paramArrayOfdouble1[2];
/* 2150 */       paramArrayOfdouble1[0] = paramArrayOfdouble1[3];
/* 2151 */       paramArrayOfdouble1[1] = paramArrayOfdouble1[4];
/* 2152 */       paramArrayOfdouble1[2] = paramArrayOfdouble1[5];
/*      */       
/* 2154 */       paramArrayOfdouble1[3] = -arrayOfDouble5[0];
/* 2155 */       paramArrayOfdouble1[4] = -arrayOfDouble5[1];
/* 2156 */       paramArrayOfdouble1[5] = -arrayOfDouble5[2];
/*      */       
/* 2158 */       arrayOfDouble1[0] = 0.0D; arrayOfDouble1[1] = 1.0D; arrayOfDouble1[2] = 0.0D;
/* 2159 */       arrayOfDouble1[3] = -1.0D; arrayOfDouble1[4] = 0.0D; arrayOfDouble1[5] = 0.0D;
/* 2160 */       arrayOfDouble1[6] = 0.0D; arrayOfDouble1[7] = 0.0D; arrayOfDouble1[8] = 1.0D;
/*      */     } else {
/* 2162 */       double d1 = 1.0D / Math.sqrt(paramArrayOfdouble1[0] * paramArrayOfdouble1[0] + paramArrayOfdouble1[3] * paramArrayOfdouble1[3]);
/* 2163 */       double d2 = paramArrayOfdouble1[0] * d1;
/* 2164 */       double d3 = paramArrayOfdouble1[3] * d1;
/* 2165 */       arrayOfDouble5[0] = d2 * paramArrayOfdouble1[0] + d3 * paramArrayOfdouble1[3];
/* 2166 */       arrayOfDouble5[1] = d2 * paramArrayOfdouble1[1] + d3 * paramArrayOfdouble1[4];
/* 2167 */       arrayOfDouble5[2] = d2 * paramArrayOfdouble1[2] + d3 * paramArrayOfdouble1[5];
/*      */       
/* 2169 */       paramArrayOfdouble1[3] = -d3 * paramArrayOfdouble1[0] + d2 * paramArrayOfdouble1[3];
/* 2170 */       paramArrayOfdouble1[4] = -d3 * paramArrayOfdouble1[1] + d2 * paramArrayOfdouble1[4];
/* 2171 */       paramArrayOfdouble1[5] = -d3 * paramArrayOfdouble1[2] + d2 * paramArrayOfdouble1[5];
/*      */       
/* 2173 */       paramArrayOfdouble1[0] = arrayOfDouble5[0];
/* 2174 */       paramArrayOfdouble1[1] = arrayOfDouble5[1];
/* 2175 */       paramArrayOfdouble1[2] = arrayOfDouble5[2];
/* 2176 */       arrayOfDouble1[0] = d2; arrayOfDouble1[1] = d3; arrayOfDouble1[2] = 0.0D;
/* 2177 */       arrayOfDouble1[3] = -d3; arrayOfDouble1[4] = d2; arrayOfDouble1[5] = 0.0D;
/* 2178 */       arrayOfDouble1[6] = 0.0D; arrayOfDouble1[7] = 0.0D; arrayOfDouble1[8] = 1.0D;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2183 */     if (paramArrayOfdouble1[6] * paramArrayOfdouble1[6] >= 1.110223024E-16D) {
/* 2184 */       if (paramArrayOfdouble1[0] * paramArrayOfdouble1[0] < 1.110223024E-16D) {
/* 2185 */         arrayOfDouble5[0] = paramArrayOfdouble1[0];
/* 2186 */         arrayOfDouble5[1] = paramArrayOfdouble1[1];
/* 2187 */         arrayOfDouble5[2] = paramArrayOfdouble1[2];
/* 2188 */         paramArrayOfdouble1[0] = paramArrayOfdouble1[6];
/* 2189 */         paramArrayOfdouble1[1] = paramArrayOfdouble1[7];
/* 2190 */         paramArrayOfdouble1[2] = paramArrayOfdouble1[8];
/*      */         
/* 2192 */         paramArrayOfdouble1[6] = -arrayOfDouble5[0];
/* 2193 */         paramArrayOfdouble1[7] = -arrayOfDouble5[1];
/* 2194 */         paramArrayOfdouble1[8] = -arrayOfDouble5[2];
/*      */         
/* 2196 */         arrayOfDouble5[0] = arrayOfDouble1[0];
/* 2197 */         arrayOfDouble5[1] = arrayOfDouble1[1];
/* 2198 */         arrayOfDouble5[2] = arrayOfDouble1[2];
/* 2199 */         arrayOfDouble1[0] = arrayOfDouble1[6];
/* 2200 */         arrayOfDouble1[1] = arrayOfDouble1[7];
/* 2201 */         arrayOfDouble1[2] = arrayOfDouble1[8];
/*      */         
/* 2203 */         arrayOfDouble1[6] = -arrayOfDouble5[0];
/* 2204 */         arrayOfDouble1[7] = -arrayOfDouble5[1];
/* 2205 */         arrayOfDouble1[8] = -arrayOfDouble5[2];
/*      */       } else {
/* 2207 */         double d1 = 1.0D / Math.sqrt(paramArrayOfdouble1[0] * paramArrayOfdouble1[0] + paramArrayOfdouble1[6] * paramArrayOfdouble1[6]);
/* 2208 */         double d2 = paramArrayOfdouble1[0] * d1;
/* 2209 */         double d3 = paramArrayOfdouble1[6] * d1;
/* 2210 */         arrayOfDouble5[0] = d2 * paramArrayOfdouble1[0] + d3 * paramArrayOfdouble1[6];
/* 2211 */         arrayOfDouble5[1] = d2 * paramArrayOfdouble1[1] + d3 * paramArrayOfdouble1[7];
/* 2212 */         arrayOfDouble5[2] = d2 * paramArrayOfdouble1[2] + d3 * paramArrayOfdouble1[8];
/*      */         
/* 2214 */         paramArrayOfdouble1[6] = -d3 * paramArrayOfdouble1[0] + d2 * paramArrayOfdouble1[6];
/* 2215 */         paramArrayOfdouble1[7] = -d3 * paramArrayOfdouble1[1] + d2 * paramArrayOfdouble1[7];
/* 2216 */         paramArrayOfdouble1[8] = -d3 * paramArrayOfdouble1[2] + d2 * paramArrayOfdouble1[8];
/* 2217 */         paramArrayOfdouble1[0] = arrayOfDouble5[0];
/* 2218 */         paramArrayOfdouble1[1] = arrayOfDouble5[1];
/* 2219 */         paramArrayOfdouble1[2] = arrayOfDouble5[2];
/*      */         
/* 2221 */         arrayOfDouble5[0] = d2 * arrayOfDouble1[0];
/* 2222 */         arrayOfDouble5[1] = d2 * arrayOfDouble1[1];
/* 2223 */         arrayOfDouble1[2] = d3;
/*      */         
/* 2225 */         arrayOfDouble5[6] = -arrayOfDouble1[0] * d3;
/* 2226 */         arrayOfDouble5[7] = -arrayOfDouble1[1] * d3;
/* 2227 */         arrayOfDouble1[8] = d2;
/* 2228 */         arrayOfDouble1[0] = arrayOfDouble5[0];
/* 2229 */         arrayOfDouble1[1] = arrayOfDouble5[1];
/* 2230 */         arrayOfDouble1[6] = arrayOfDouble5[6];
/* 2231 */         arrayOfDouble1[7] = arrayOfDouble5[7];
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 2236 */     if (paramArrayOfdouble1[2] * paramArrayOfdouble1[2] < 1.110223024E-16D) {
/* 2237 */       arrayOfDouble2[0] = 1.0D; arrayOfDouble2[1] = 0.0D; arrayOfDouble2[2] = 0.0D;
/* 2238 */       arrayOfDouble2[3] = 0.0D; arrayOfDouble2[4] = 1.0D; arrayOfDouble2[5] = 0.0D;
/* 2239 */       arrayOfDouble2[6] = 0.0D; arrayOfDouble2[7] = 0.0D; arrayOfDouble2[8] = 1.0D;
/* 2240 */     } else if (paramArrayOfdouble1[1] * paramArrayOfdouble1[1] < 1.110223024E-16D) {
/* 2241 */       arrayOfDouble5[2] = paramArrayOfdouble1[2];
/* 2242 */       arrayOfDouble5[5] = paramArrayOfdouble1[5];
/* 2243 */       arrayOfDouble5[8] = paramArrayOfdouble1[8];
/* 2244 */       paramArrayOfdouble1[2] = -paramArrayOfdouble1[1];
/* 2245 */       paramArrayOfdouble1[5] = -paramArrayOfdouble1[4];
/* 2246 */       paramArrayOfdouble1[8] = -paramArrayOfdouble1[7];
/*      */       
/* 2248 */       paramArrayOfdouble1[1] = arrayOfDouble5[2];
/* 2249 */       paramArrayOfdouble1[4] = arrayOfDouble5[5];
/* 2250 */       paramArrayOfdouble1[7] = arrayOfDouble5[8];
/*      */       
/* 2252 */       arrayOfDouble2[0] = 1.0D; arrayOfDouble2[1] = 0.0D; arrayOfDouble2[2] = 0.0D;
/* 2253 */       arrayOfDouble2[3] = 0.0D; arrayOfDouble2[4] = 0.0D; arrayOfDouble2[5] = -1.0D;
/* 2254 */       arrayOfDouble2[6] = 0.0D; arrayOfDouble2[7] = 1.0D; arrayOfDouble2[8] = 0.0D;
/*      */     } else {
/* 2256 */       double d1 = 1.0D / Math.sqrt(paramArrayOfdouble1[1] * paramArrayOfdouble1[1] + paramArrayOfdouble1[2] * paramArrayOfdouble1[2]);
/* 2257 */       double d2 = paramArrayOfdouble1[1] * d1;
/* 2258 */       double d3 = paramArrayOfdouble1[2] * d1;
/* 2259 */       arrayOfDouble5[1] = d2 * paramArrayOfdouble1[1] + d3 * paramArrayOfdouble1[2];
/* 2260 */       paramArrayOfdouble1[2] = -d3 * paramArrayOfdouble1[1] + d2 * paramArrayOfdouble1[2];
/* 2261 */       paramArrayOfdouble1[1] = arrayOfDouble5[1];
/*      */       
/* 2263 */       arrayOfDouble5[4] = d2 * paramArrayOfdouble1[4] + d3 * paramArrayOfdouble1[5];
/* 2264 */       paramArrayOfdouble1[5] = -d3 * paramArrayOfdouble1[4] + d2 * paramArrayOfdouble1[5];
/* 2265 */       paramArrayOfdouble1[4] = arrayOfDouble5[4];
/*      */       
/* 2267 */       arrayOfDouble5[7] = d2 * paramArrayOfdouble1[7] + d3 * paramArrayOfdouble1[8];
/* 2268 */       paramArrayOfdouble1[8] = -d3 * paramArrayOfdouble1[7] + d2 * paramArrayOfdouble1[8];
/* 2269 */       paramArrayOfdouble1[7] = arrayOfDouble5[7];
/*      */       
/* 2271 */       arrayOfDouble2[0] = 1.0D; arrayOfDouble2[1] = 0.0D; arrayOfDouble2[2] = 0.0D;
/* 2272 */       arrayOfDouble2[3] = 0.0D; arrayOfDouble2[4] = d2; arrayOfDouble2[5] = -d3;
/* 2273 */       arrayOfDouble2[6] = 0.0D; arrayOfDouble2[7] = d3; arrayOfDouble2[8] = d2;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2278 */     if (paramArrayOfdouble1[7] * paramArrayOfdouble1[7] >= 1.110223024E-16D) {
/* 2279 */       if (paramArrayOfdouble1[4] * paramArrayOfdouble1[4] < 1.110223024E-16D) {
/* 2280 */         arrayOfDouble5[3] = paramArrayOfdouble1[3];
/* 2281 */         arrayOfDouble5[4] = paramArrayOfdouble1[4];
/* 2282 */         arrayOfDouble5[5] = paramArrayOfdouble1[5];
/* 2283 */         paramArrayOfdouble1[3] = paramArrayOfdouble1[6];
/* 2284 */         paramArrayOfdouble1[4] = paramArrayOfdouble1[7];
/* 2285 */         paramArrayOfdouble1[5] = paramArrayOfdouble1[8];
/*      */         
/* 2287 */         paramArrayOfdouble1[6] = -arrayOfDouble5[3];
/* 2288 */         paramArrayOfdouble1[7] = -arrayOfDouble5[4];
/* 2289 */         paramArrayOfdouble1[8] = -arrayOfDouble5[5];
/*      */         
/* 2291 */         arrayOfDouble5[3] = arrayOfDouble1[3];
/* 2292 */         arrayOfDouble5[4] = arrayOfDouble1[4];
/* 2293 */         arrayOfDouble5[5] = arrayOfDouble1[5];
/* 2294 */         arrayOfDouble1[3] = arrayOfDouble1[6];
/* 2295 */         arrayOfDouble1[4] = arrayOfDouble1[7];
/* 2296 */         arrayOfDouble1[5] = arrayOfDouble1[8];
/*      */         
/* 2298 */         arrayOfDouble1[6] = -arrayOfDouble5[3];
/* 2299 */         arrayOfDouble1[7] = -arrayOfDouble5[4];
/* 2300 */         arrayOfDouble1[8] = -arrayOfDouble5[5];
/*      */       } else {
/*      */         
/* 2303 */         double d1 = 1.0D / Math.sqrt(paramArrayOfdouble1[4] * paramArrayOfdouble1[4] + paramArrayOfdouble1[7] * paramArrayOfdouble1[7]);
/* 2304 */         double d2 = paramArrayOfdouble1[4] * d1;
/* 2305 */         double d3 = paramArrayOfdouble1[7] * d1;
/* 2306 */         arrayOfDouble5[3] = d2 * paramArrayOfdouble1[3] + d3 * paramArrayOfdouble1[6];
/* 2307 */         paramArrayOfdouble1[6] = -d3 * paramArrayOfdouble1[3] + d2 * paramArrayOfdouble1[6];
/* 2308 */         paramArrayOfdouble1[3] = arrayOfDouble5[3];
/*      */         
/* 2310 */         arrayOfDouble5[4] = d2 * paramArrayOfdouble1[4] + d3 * paramArrayOfdouble1[7];
/* 2311 */         paramArrayOfdouble1[7] = -d3 * paramArrayOfdouble1[4] + d2 * paramArrayOfdouble1[7];
/* 2312 */         paramArrayOfdouble1[4] = arrayOfDouble5[4];
/*      */         
/* 2314 */         arrayOfDouble5[5] = d2 * paramArrayOfdouble1[5] + d3 * paramArrayOfdouble1[8];
/* 2315 */         paramArrayOfdouble1[8] = -d3 * paramArrayOfdouble1[5] + d2 * paramArrayOfdouble1[8];
/* 2316 */         paramArrayOfdouble1[5] = arrayOfDouble5[5];
/*      */         
/* 2318 */         arrayOfDouble5[3] = d2 * arrayOfDouble1[3] + d3 * arrayOfDouble1[6];
/* 2319 */         arrayOfDouble1[6] = -d3 * arrayOfDouble1[3] + d2 * arrayOfDouble1[6];
/* 2320 */         arrayOfDouble1[3] = arrayOfDouble5[3];
/*      */         
/* 2322 */         arrayOfDouble5[4] = d2 * arrayOfDouble1[4] + d3 * arrayOfDouble1[7];
/* 2323 */         arrayOfDouble1[7] = -d3 * arrayOfDouble1[4] + d2 * arrayOfDouble1[7];
/* 2324 */         arrayOfDouble1[4] = arrayOfDouble5[4];
/*      */         
/* 2326 */         arrayOfDouble5[5] = d2 * arrayOfDouble1[5] + d3 * arrayOfDouble1[8];
/* 2327 */         arrayOfDouble1[8] = -d3 * arrayOfDouble1[5] + d2 * arrayOfDouble1[8];
/* 2328 */         arrayOfDouble1[5] = arrayOfDouble5[5];
/*      */       } 
/*      */     }
/* 2331 */     arrayOfDouble6[0] = paramArrayOfdouble1[0];
/* 2332 */     arrayOfDouble6[1] = paramArrayOfdouble1[4];
/* 2333 */     arrayOfDouble6[2] = paramArrayOfdouble1[8];
/* 2334 */     arrayOfDouble8[0] = paramArrayOfdouble1[1];
/* 2335 */     arrayOfDouble8[1] = paramArrayOfdouble1[5];
/*      */     
/* 2337 */     if (arrayOfDouble8[0] * arrayOfDouble8[0] >= 1.110223024E-16D || arrayOfDouble8[1] * arrayOfDouble8[1] >= 1.110223024E-16D)
/*      */     {
/*      */       
/* 2340 */       compute_qr(arrayOfDouble6, arrayOfDouble8, arrayOfDouble1, arrayOfDouble2);
/*      */     }
/*      */     
/* 2343 */     arrayOfDouble9[0] = arrayOfDouble6[0];
/* 2344 */     arrayOfDouble9[1] = arrayOfDouble6[1];
/* 2345 */     arrayOfDouble9[2] = arrayOfDouble6[2];
/*      */ 
/*      */ 
/*      */     
/* 2349 */     if (almostEqual(Math.abs(arrayOfDouble9[0]), 1.0D) && almostEqual(Math.abs(arrayOfDouble9[1]), 1.0D) && almostEqual(Math.abs(arrayOfDouble9[2]), 1.0D)) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2354 */       for (b1 = 0; b1 < 3; b1++) {
/* 2355 */         if (arrayOfDouble9[b1] < 0.0D)
/* 2356 */           b2++; 
/*      */       } 
/* 2358 */       if (b2 == 0 || b2 == 2) {
/*      */         
/* 2360 */         paramArrayOfdouble2[2] = 1.0D; paramArrayOfdouble2[1] = 1.0D; paramArrayOfdouble2[0] = 1.0D;
/* 2361 */         for (b1 = 0; b1 < 9; b1++) {
/* 2362 */           paramArrayOfdouble3[b1] = arrayOfDouble7[b1];
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/* 2369 */     transpose_mat(arrayOfDouble1, arrayOfDouble3);
/* 2370 */     transpose_mat(arrayOfDouble2, arrayOfDouble4);
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
/* 2384 */     svdReorder(paramArrayOfdouble1, arrayOfDouble3, arrayOfDouble4, arrayOfDouble9, paramArrayOfdouble3, paramArrayOfdouble2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void svdReorder(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, double[] paramArrayOfdouble3, double[] paramArrayOfdouble4, double[] paramArrayOfdouble5, double[] paramArrayOfdouble6) {
/* 2391 */     int[] arrayOfInt1 = new int[3];
/* 2392 */     int[] arrayOfInt2 = new int[3];
/*      */     
/* 2394 */     double[] arrayOfDouble1 = new double[3];
/* 2395 */     double[] arrayOfDouble2 = new double[9];
/*      */ 
/*      */ 
/*      */     
/* 2399 */     if (paramArrayOfdouble4[0] < 0.0D) {
/* 2400 */       paramArrayOfdouble4[0] = -paramArrayOfdouble4[0];
/* 2401 */       paramArrayOfdouble3[0] = -paramArrayOfdouble3[0];
/* 2402 */       paramArrayOfdouble3[1] = -paramArrayOfdouble3[1];
/* 2403 */       paramArrayOfdouble3[2] = -paramArrayOfdouble3[2];
/*      */     } 
/* 2405 */     if (paramArrayOfdouble4[1] < 0.0D) {
/* 2406 */       paramArrayOfdouble4[1] = -paramArrayOfdouble4[1];
/* 2407 */       paramArrayOfdouble3[3] = -paramArrayOfdouble3[3];
/* 2408 */       paramArrayOfdouble3[4] = -paramArrayOfdouble3[4];
/* 2409 */       paramArrayOfdouble3[5] = -paramArrayOfdouble3[5];
/*      */     } 
/* 2411 */     if (paramArrayOfdouble4[2] < 0.0D) {
/* 2412 */       paramArrayOfdouble4[2] = -paramArrayOfdouble4[2];
/* 2413 */       paramArrayOfdouble3[6] = -paramArrayOfdouble3[6];
/* 2414 */       paramArrayOfdouble3[7] = -paramArrayOfdouble3[7];
/* 2415 */       paramArrayOfdouble3[8] = -paramArrayOfdouble3[8];
/*      */     } 
/*      */     
/* 2418 */     mat_mul(paramArrayOfdouble2, paramArrayOfdouble3, arrayOfDouble2);
/*      */ 
/*      */     
/* 2421 */     if (almostEqual(Math.abs(paramArrayOfdouble4[0]), Math.abs(paramArrayOfdouble4[1])) && almostEqual(Math.abs(paramArrayOfdouble4[1]), Math.abs(paramArrayOfdouble4[2]))) {
/*      */       byte b;
/* 2423 */       for (b = 0; b < 9; b++) {
/* 2424 */         paramArrayOfdouble5[b] = arrayOfDouble2[b];
/*      */       }
/* 2426 */       for (b = 0; b < 3; b++) {
/* 2427 */         paramArrayOfdouble6[b] = paramArrayOfdouble4[b];
/*      */       }
/*      */     } else {
/*      */       byte b;
/*      */       
/*      */       boolean bool1, bool2;
/* 2433 */       if (paramArrayOfdouble4[0] > paramArrayOfdouble4[1]) {
/* 2434 */         if (paramArrayOfdouble4[0] > paramArrayOfdouble4[2]) {
/* 2435 */           if (paramArrayOfdouble4[2] > paramArrayOfdouble4[1]) {
/* 2436 */             arrayOfInt1[0] = 0; arrayOfInt1[1] = 2; arrayOfInt1[2] = 1;
/*      */           } else {
/* 2438 */             arrayOfInt1[0] = 0; arrayOfInt1[1] = 1; arrayOfInt1[2] = 2;
/*      */           } 
/*      */         } else {
/* 2441 */           arrayOfInt1[0] = 2; arrayOfInt1[1] = 0; arrayOfInt1[2] = 1;
/*      */         }
/*      */       
/* 2444 */       } else if (paramArrayOfdouble4[1] > paramArrayOfdouble4[2]) {
/* 2445 */         if (paramArrayOfdouble4[2] > paramArrayOfdouble4[0]) {
/* 2446 */           arrayOfInt1[0] = 1; arrayOfInt1[1] = 2; arrayOfInt1[2] = 0;
/*      */         } else {
/* 2448 */           arrayOfInt1[0] = 1; arrayOfInt1[1] = 0; arrayOfInt1[2] = 2;
/*      */         } 
/*      */       } else {
/* 2451 */         arrayOfInt1[0] = 2; arrayOfInt1[1] = 1; arrayOfInt1[2] = 0;
/*      */       } 
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
/* 2463 */       arrayOfDouble1[0] = paramArrayOfdouble1[0] * paramArrayOfdouble1[0] + paramArrayOfdouble1[1] * paramArrayOfdouble1[1] + paramArrayOfdouble1[2] * paramArrayOfdouble1[2];
/* 2464 */       arrayOfDouble1[1] = paramArrayOfdouble1[3] * paramArrayOfdouble1[3] + paramArrayOfdouble1[4] * paramArrayOfdouble1[4] + paramArrayOfdouble1[5] * paramArrayOfdouble1[5];
/* 2465 */       arrayOfDouble1[2] = paramArrayOfdouble1[6] * paramArrayOfdouble1[6] + paramArrayOfdouble1[7] * paramArrayOfdouble1[7] + paramArrayOfdouble1[8] * paramArrayOfdouble1[8];
/*      */       
/* 2467 */       if (arrayOfDouble1[0] > arrayOfDouble1[1]) {
/* 2468 */         if (arrayOfDouble1[0] > arrayOfDouble1[2]) {
/* 2469 */           if (arrayOfDouble1[2] > arrayOfDouble1[1]) {
/*      */             
/* 2471 */             b = 0; bool2 = true; bool1 = true;
/*      */           } else {
/*      */             
/* 2474 */             b = 0; bool1 = true; bool2 = true;
/*      */           } 
/*      */         } else {
/*      */           
/* 2478 */           bool2 = false; b = 1; bool1 = true;
/*      */         }
/*      */       
/* 2481 */       } else if (arrayOfDouble1[1] > arrayOfDouble1[2]) {
/* 2482 */         if (arrayOfDouble1[2] > arrayOfDouble1[0]) {
/*      */           
/* 2484 */           bool1 = false; bool2 = true; b = 2;
/*      */         } else {
/*      */           
/* 2487 */           bool1 = false; b = 1; bool2 = true;
/*      */         } 
/*      */       } else {
/*      */         
/* 2491 */         bool2 = false; bool1 = true; b = 2;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2496 */       int i = arrayOfInt1[b];
/* 2497 */       paramArrayOfdouble6[0] = paramArrayOfdouble4[i];
/*      */       
/* 2499 */       i = arrayOfInt1[bool1];
/* 2500 */       paramArrayOfdouble6[1] = paramArrayOfdouble4[i];
/*      */       
/* 2502 */       i = arrayOfInt1[bool2];
/* 2503 */       paramArrayOfdouble6[2] = paramArrayOfdouble4[i];
/*      */ 
/*      */       
/* 2506 */       i = arrayOfInt1[b];
/* 2507 */       paramArrayOfdouble5[0] = arrayOfDouble2[i];
/*      */       
/* 2509 */       i = arrayOfInt1[b] + 3;
/* 2510 */       paramArrayOfdouble5[3] = arrayOfDouble2[i];
/*      */       
/* 2512 */       i = arrayOfInt1[b] + 6;
/* 2513 */       paramArrayOfdouble5[6] = arrayOfDouble2[i];
/*      */       
/* 2515 */       i = arrayOfInt1[bool1];
/* 2516 */       paramArrayOfdouble5[1] = arrayOfDouble2[i];
/*      */       
/* 2518 */       i = arrayOfInt1[bool1] + 3;
/* 2519 */       paramArrayOfdouble5[4] = arrayOfDouble2[i];
/*      */       
/* 2521 */       i = arrayOfInt1[bool1] + 6;
/* 2522 */       paramArrayOfdouble5[7] = arrayOfDouble2[i];
/*      */       
/* 2524 */       i = arrayOfInt1[bool2];
/* 2525 */       paramArrayOfdouble5[2] = arrayOfDouble2[i];
/*      */       
/* 2527 */       i = arrayOfInt1[bool2] + 3;
/* 2528 */       paramArrayOfdouble5[5] = arrayOfDouble2[i];
/*      */       
/* 2530 */       i = arrayOfInt1[bool2] + 6;
/* 2531 */       paramArrayOfdouble5[8] = arrayOfDouble2[i];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int compute_qr(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, double[] paramArrayOfdouble3, double[] paramArrayOfdouble4) {
/* 2540 */     double[] arrayOfDouble1 = new double[2];
/* 2541 */     double[] arrayOfDouble2 = new double[2];
/* 2542 */     double[] arrayOfDouble3 = new double[2];
/* 2543 */     double[] arrayOfDouble4 = new double[2];
/* 2544 */     double[] arrayOfDouble5 = new double[9];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2552 */     double d1 = 1.0D;
/* 2553 */     double d2 = -1.0D;
/*      */     
/* 2555 */     boolean bool1 = false;
/*      */ 
/*      */     
/* 2558 */     boolean bool2 = true;
/*      */     
/* 2560 */     if (Math.abs(paramArrayOfdouble2[1]) < 4.89E-15D || Math.abs(paramArrayOfdouble2[0]) < 4.89E-15D) bool1 = true;
/*      */     
/* 2562 */     for (byte b = 0; b < 10 && !bool1; b++) {
/* 2563 */       double d3 = compute_shift(paramArrayOfdouble1[1], paramArrayOfdouble2[1], paramArrayOfdouble1[2]);
/* 2564 */       double d7 = (Math.abs(paramArrayOfdouble1[0]) - d3) * (d_sign(d1, paramArrayOfdouble1[0]) + d3 / paramArrayOfdouble1[0]);
/* 2565 */       double d8 = paramArrayOfdouble2[0];
/* 2566 */       double d4 = compute_rot(d7, d8, arrayOfDouble4, arrayOfDouble2, 0, bool2);
/* 2567 */       d7 = arrayOfDouble2[0] * paramArrayOfdouble1[0] + arrayOfDouble4[0] * paramArrayOfdouble2[0];
/* 2568 */       paramArrayOfdouble2[0] = arrayOfDouble2[0] * paramArrayOfdouble2[0] - arrayOfDouble4[0] * paramArrayOfdouble1[0];
/* 2569 */       d8 = arrayOfDouble4[0] * paramArrayOfdouble1[1];
/* 2570 */       paramArrayOfdouble1[1] = arrayOfDouble2[0] * paramArrayOfdouble1[1];
/*      */       
/* 2572 */       d4 = compute_rot(d7, d8, arrayOfDouble3, arrayOfDouble1, 0, bool2);
/* 2573 */       bool2 = false;
/* 2574 */       paramArrayOfdouble1[0] = d4;
/* 2575 */       d7 = arrayOfDouble1[0] * paramArrayOfdouble2[0] + arrayOfDouble3[0] * paramArrayOfdouble1[1];
/* 2576 */       paramArrayOfdouble1[1] = arrayOfDouble1[0] * paramArrayOfdouble1[1] - arrayOfDouble3[0] * paramArrayOfdouble2[0];
/* 2577 */       d8 = arrayOfDouble3[0] * paramArrayOfdouble2[1];
/* 2578 */       paramArrayOfdouble2[1] = arrayOfDouble1[0] * paramArrayOfdouble2[1];
/*      */       
/* 2580 */       d4 = compute_rot(d7, d8, arrayOfDouble4, arrayOfDouble2, 1, bool2);
/* 2581 */       paramArrayOfdouble2[0] = d4;
/* 2582 */       d7 = arrayOfDouble2[1] * paramArrayOfdouble1[1] + arrayOfDouble4[1] * paramArrayOfdouble2[1];
/* 2583 */       paramArrayOfdouble2[1] = arrayOfDouble2[1] * paramArrayOfdouble2[1] - arrayOfDouble4[1] * paramArrayOfdouble1[1];
/* 2584 */       d8 = arrayOfDouble4[1] * paramArrayOfdouble1[2];
/* 2585 */       paramArrayOfdouble1[2] = arrayOfDouble2[1] * paramArrayOfdouble1[2];
/*      */       
/* 2587 */       d4 = compute_rot(d7, d8, arrayOfDouble3, arrayOfDouble1, 1, bool2);
/* 2588 */       paramArrayOfdouble1[1] = d4;
/* 2589 */       d7 = arrayOfDouble1[1] * paramArrayOfdouble2[1] + arrayOfDouble3[1] * paramArrayOfdouble1[2];
/* 2590 */       paramArrayOfdouble1[2] = arrayOfDouble1[1] * paramArrayOfdouble1[2] - arrayOfDouble3[1] * paramArrayOfdouble2[1];
/* 2591 */       paramArrayOfdouble2[1] = d7;
/*      */ 
/*      */       
/* 2594 */       double d5 = paramArrayOfdouble3[0];
/* 2595 */       paramArrayOfdouble3[0] = arrayOfDouble1[0] * d5 + arrayOfDouble3[0] * paramArrayOfdouble3[3];
/* 2596 */       paramArrayOfdouble3[3] = -arrayOfDouble3[0] * d5 + arrayOfDouble1[0] * paramArrayOfdouble3[3];
/* 2597 */       d5 = paramArrayOfdouble3[1];
/* 2598 */       paramArrayOfdouble3[1] = arrayOfDouble1[0] * d5 + arrayOfDouble3[0] * paramArrayOfdouble3[4];
/* 2599 */       paramArrayOfdouble3[4] = -arrayOfDouble3[0] * d5 + arrayOfDouble1[0] * paramArrayOfdouble3[4];
/* 2600 */       d5 = paramArrayOfdouble3[2];
/* 2601 */       paramArrayOfdouble3[2] = arrayOfDouble1[0] * d5 + arrayOfDouble3[0] * paramArrayOfdouble3[5];
/* 2602 */       paramArrayOfdouble3[5] = -arrayOfDouble3[0] * d5 + arrayOfDouble1[0] * paramArrayOfdouble3[5];
/*      */       
/* 2604 */       d5 = paramArrayOfdouble3[3];
/* 2605 */       paramArrayOfdouble3[3] = arrayOfDouble1[1] * d5 + arrayOfDouble3[1] * paramArrayOfdouble3[6];
/* 2606 */       paramArrayOfdouble3[6] = -arrayOfDouble3[1] * d5 + arrayOfDouble1[1] * paramArrayOfdouble3[6];
/* 2607 */       d5 = paramArrayOfdouble3[4];
/* 2608 */       paramArrayOfdouble3[4] = arrayOfDouble1[1] * d5 + arrayOfDouble3[1] * paramArrayOfdouble3[7];
/* 2609 */       paramArrayOfdouble3[7] = -arrayOfDouble3[1] * d5 + arrayOfDouble1[1] * paramArrayOfdouble3[7];
/* 2610 */       d5 = paramArrayOfdouble3[5];
/* 2611 */       paramArrayOfdouble3[5] = arrayOfDouble1[1] * d5 + arrayOfDouble3[1] * paramArrayOfdouble3[8];
/* 2612 */       paramArrayOfdouble3[8] = -arrayOfDouble3[1] * d5 + arrayOfDouble1[1] * paramArrayOfdouble3[8];
/*      */ 
/*      */ 
/*      */       
/* 2616 */       double d6 = paramArrayOfdouble4[0];
/* 2617 */       paramArrayOfdouble4[0] = arrayOfDouble2[0] * d6 + arrayOfDouble4[0] * paramArrayOfdouble4[1];
/* 2618 */       paramArrayOfdouble4[1] = -arrayOfDouble4[0] * d6 + arrayOfDouble2[0] * paramArrayOfdouble4[1];
/* 2619 */       d6 = paramArrayOfdouble4[3];
/* 2620 */       paramArrayOfdouble4[3] = arrayOfDouble2[0] * d6 + arrayOfDouble4[0] * paramArrayOfdouble4[4];
/* 2621 */       paramArrayOfdouble4[4] = -arrayOfDouble4[0] * d6 + arrayOfDouble2[0] * paramArrayOfdouble4[4];
/* 2622 */       d6 = paramArrayOfdouble4[6];
/* 2623 */       paramArrayOfdouble4[6] = arrayOfDouble2[0] * d6 + arrayOfDouble4[0] * paramArrayOfdouble4[7];
/* 2624 */       paramArrayOfdouble4[7] = -arrayOfDouble4[0] * d6 + arrayOfDouble2[0] * paramArrayOfdouble4[7];
/*      */       
/* 2626 */       d6 = paramArrayOfdouble4[1];
/* 2627 */       paramArrayOfdouble4[1] = arrayOfDouble2[1] * d6 + arrayOfDouble4[1] * paramArrayOfdouble4[2];
/* 2628 */       paramArrayOfdouble4[2] = -arrayOfDouble4[1] * d6 + arrayOfDouble2[1] * paramArrayOfdouble4[2];
/* 2629 */       d6 = paramArrayOfdouble4[4];
/* 2630 */       paramArrayOfdouble4[4] = arrayOfDouble2[1] * d6 + arrayOfDouble4[1] * paramArrayOfdouble4[5];
/* 2631 */       paramArrayOfdouble4[5] = -arrayOfDouble4[1] * d6 + arrayOfDouble2[1] * paramArrayOfdouble4[5];
/* 2632 */       d6 = paramArrayOfdouble4[7];
/* 2633 */       paramArrayOfdouble4[7] = arrayOfDouble2[1] * d6 + arrayOfDouble4[1] * paramArrayOfdouble4[8];
/* 2634 */       paramArrayOfdouble4[8] = -arrayOfDouble4[1] * d6 + arrayOfDouble2[1] * paramArrayOfdouble4[8];
/*      */ 
/*      */       
/* 2637 */       arrayOfDouble5[0] = paramArrayOfdouble1[0]; arrayOfDouble5[1] = paramArrayOfdouble2[0]; arrayOfDouble5[2] = 0.0D;
/* 2638 */       arrayOfDouble5[3] = 0.0D; arrayOfDouble5[4] = paramArrayOfdouble1[1]; arrayOfDouble5[5] = paramArrayOfdouble2[1];
/* 2639 */       arrayOfDouble5[6] = 0.0D; arrayOfDouble5[7] = 0.0D; arrayOfDouble5[8] = paramArrayOfdouble1[2];
/*      */       
/* 2641 */       if (Math.abs(paramArrayOfdouble2[1]) < 4.89E-15D || Math.abs(paramArrayOfdouble2[0]) < 4.89E-15D) bool1 = true;
/*      */     
/*      */     } 
/* 2644 */     if (Math.abs(paramArrayOfdouble2[1]) < 4.89E-15D) {
/* 2645 */       compute_2X2(paramArrayOfdouble1[0], paramArrayOfdouble2[0], paramArrayOfdouble1[1], paramArrayOfdouble1, arrayOfDouble3, arrayOfDouble1, arrayOfDouble4, arrayOfDouble2, 0);
/*      */       
/* 2647 */       double d3 = paramArrayOfdouble3[0];
/* 2648 */       paramArrayOfdouble3[0] = arrayOfDouble1[0] * d3 + arrayOfDouble3[0] * paramArrayOfdouble3[3];
/* 2649 */       paramArrayOfdouble3[3] = -arrayOfDouble3[0] * d3 + arrayOfDouble1[0] * paramArrayOfdouble3[3];
/* 2650 */       d3 = paramArrayOfdouble3[1];
/* 2651 */       paramArrayOfdouble3[1] = arrayOfDouble1[0] * d3 + arrayOfDouble3[0] * paramArrayOfdouble3[4];
/* 2652 */       paramArrayOfdouble3[4] = -arrayOfDouble3[0] * d3 + arrayOfDouble1[0] * paramArrayOfdouble3[4];
/* 2653 */       d3 = paramArrayOfdouble3[2];
/* 2654 */       paramArrayOfdouble3[2] = arrayOfDouble1[0] * d3 + arrayOfDouble3[0] * paramArrayOfdouble3[5];
/* 2655 */       paramArrayOfdouble3[5] = -arrayOfDouble3[0] * d3 + arrayOfDouble1[0] * paramArrayOfdouble3[5];
/*      */ 
/*      */ 
/*      */       
/* 2659 */       double d4 = paramArrayOfdouble4[0];
/* 2660 */       paramArrayOfdouble4[0] = arrayOfDouble2[0] * d4 + arrayOfDouble4[0] * paramArrayOfdouble4[1];
/* 2661 */       paramArrayOfdouble4[1] = -arrayOfDouble4[0] * d4 + arrayOfDouble2[0] * paramArrayOfdouble4[1];
/* 2662 */       d4 = paramArrayOfdouble4[3];
/* 2663 */       paramArrayOfdouble4[3] = arrayOfDouble2[0] * d4 + arrayOfDouble4[0] * paramArrayOfdouble4[4];
/* 2664 */       paramArrayOfdouble4[4] = -arrayOfDouble4[0] * d4 + arrayOfDouble2[0] * paramArrayOfdouble4[4];
/* 2665 */       d4 = paramArrayOfdouble4[6];
/* 2666 */       paramArrayOfdouble4[6] = arrayOfDouble2[0] * d4 + arrayOfDouble4[0] * paramArrayOfdouble4[7];
/* 2667 */       paramArrayOfdouble4[7] = -arrayOfDouble4[0] * d4 + arrayOfDouble2[0] * paramArrayOfdouble4[7];
/*      */     } else {
/* 2669 */       compute_2X2(paramArrayOfdouble1[1], paramArrayOfdouble2[1], paramArrayOfdouble1[2], paramArrayOfdouble1, arrayOfDouble3, arrayOfDouble1, arrayOfDouble4, arrayOfDouble2, 1);
/*      */       
/* 2671 */       double d3 = paramArrayOfdouble3[3];
/* 2672 */       paramArrayOfdouble3[3] = arrayOfDouble1[0] * d3 + arrayOfDouble3[0] * paramArrayOfdouble3[6];
/* 2673 */       paramArrayOfdouble3[6] = -arrayOfDouble3[0] * d3 + arrayOfDouble1[0] * paramArrayOfdouble3[6];
/* 2674 */       d3 = paramArrayOfdouble3[4];
/* 2675 */       paramArrayOfdouble3[4] = arrayOfDouble1[0] * d3 + arrayOfDouble3[0] * paramArrayOfdouble3[7];
/* 2676 */       paramArrayOfdouble3[7] = -arrayOfDouble3[0] * d3 + arrayOfDouble1[0] * paramArrayOfdouble3[7];
/* 2677 */       d3 = paramArrayOfdouble3[5];
/* 2678 */       paramArrayOfdouble3[5] = arrayOfDouble1[0] * d3 + arrayOfDouble3[0] * paramArrayOfdouble3[8];
/* 2679 */       paramArrayOfdouble3[8] = -arrayOfDouble3[0] * d3 + arrayOfDouble1[0] * paramArrayOfdouble3[8];
/*      */ 
/*      */ 
/*      */       
/* 2683 */       double d4 = paramArrayOfdouble4[1];
/* 2684 */       paramArrayOfdouble4[1] = arrayOfDouble2[0] * d4 + arrayOfDouble4[0] * paramArrayOfdouble4[2];
/* 2685 */       paramArrayOfdouble4[2] = -arrayOfDouble4[0] * d4 + arrayOfDouble2[0] * paramArrayOfdouble4[2];
/* 2686 */       d4 = paramArrayOfdouble4[4];
/* 2687 */       paramArrayOfdouble4[4] = arrayOfDouble2[0] * d4 + arrayOfDouble4[0] * paramArrayOfdouble4[5];
/* 2688 */       paramArrayOfdouble4[5] = -arrayOfDouble4[0] * d4 + arrayOfDouble2[0] * paramArrayOfdouble4[5];
/* 2689 */       d4 = paramArrayOfdouble4[7];
/* 2690 */       paramArrayOfdouble4[7] = arrayOfDouble2[0] * d4 + arrayOfDouble4[0] * paramArrayOfdouble4[8];
/* 2691 */       paramArrayOfdouble4[8] = -arrayOfDouble4[0] * d4 + arrayOfDouble2[0] * paramArrayOfdouble4[8];
/*      */     } 
/*      */     
/* 2694 */     return 0;
/*      */   }
/*      */   static double max(double paramDouble1, double paramDouble2) {
/* 2697 */     if (paramDouble1 > paramDouble2) {
/* 2698 */       return paramDouble1;
/*      */     }
/* 2700 */     return paramDouble2;
/*      */   }
/*      */   static double min(double paramDouble1, double paramDouble2) {
/* 2703 */     if (paramDouble1 < paramDouble2) {
/* 2704 */       return paramDouble1;
/*      */     }
/* 2706 */     return paramDouble2;
/*      */   }
/*      */   
/*      */   static double d_sign(double paramDouble1, double paramDouble2) {
/* 2710 */     double d = (paramDouble1 >= 0.0D) ? paramDouble1 : -paramDouble1;
/* 2711 */     return (paramDouble2 >= 0.0D) ? d : -d;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static double compute_shift(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 2719 */     double d6, d3 = Math.abs(paramDouble1);
/* 2720 */     double d4 = Math.abs(paramDouble2);
/* 2721 */     double d5 = Math.abs(paramDouble3);
/* 2722 */     double d1 = min(d3, d5);
/* 2723 */     double d2 = max(d3, d5);
/* 2724 */     if (d1 == 0.0D) {
/* 2725 */       d6 = 0.0D;
/* 2726 */       if (d2 != 0.0D)
/*      */       {
/* 2728 */         double d = min(d2, d4) / max(d2, d4);
/*      */       }
/*      */     }
/* 2731 */     else if (d4 < d2) {
/* 2732 */       double d9 = d1 / d2 + 1.0D;
/* 2733 */       double d10 = (d2 - d1) / d2;
/* 2734 */       double d7 = d4 / d2;
/* 2735 */       double d11 = d7 * d7;
/* 2736 */       double d8 = 2.0D / (Math.sqrt(d9 * d9 + d11) + Math.sqrt(d10 * d10 + d11));
/* 2737 */       d6 = d1 * d8;
/*      */     } else {
/* 2739 */       double d = d2 / d4;
/* 2740 */       if (d == 0.0D) {
/* 2741 */         d6 = d1 * d2 / d4;
/*      */       } else {
/* 2743 */         double d10 = d1 / d2 + 1.0D;
/* 2744 */         double d11 = (d2 - d1) / d2;
/* 2745 */         double d7 = d10 * d;
/* 2746 */         double d8 = d11 * d;
/* 2747 */         double d9 = 1.0D / (Math.sqrt(d7 * d7 + 1.0D) + Math.sqrt(d8 * d8 + 1.0D));
/* 2748 */         d6 = d1 * d9 * d;
/* 2749 */         d6 += d6;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2754 */     return d6;
/*      */   }
/*      */   
/*      */   static int compute_2X2(double paramDouble1, double paramDouble2, double paramDouble3, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, double[] paramArrayOfdouble3, double[] paramArrayOfdouble4, double[] paramArrayOfdouble5, int paramInt) {
/*      */     boolean bool;
/* 2759 */     double d1 = 2.0D;
/* 2760 */     double d2 = 1.0D;
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
/* 2772 */     double d15 = paramArrayOfdouble1[0];
/* 2773 */     double d14 = paramArrayOfdouble1[1];
/* 2774 */     double d10 = 0.0D;
/* 2775 */     double d11 = 0.0D;
/* 2776 */     double d12 = 0.0D;
/* 2777 */     double d13 = 0.0D;
/* 2778 */     double d3 = 0.0D;
/*      */     
/* 2780 */     double d7 = paramDouble1;
/* 2781 */     double d4 = Math.abs(d7);
/* 2782 */     double d9 = paramDouble3;
/* 2783 */     double d6 = Math.abs(paramDouble3);
/*      */     
/* 2785 */     byte b = 1;
/* 2786 */     if (d6 > d4) {
/* 2787 */       bool = true;
/*      */     } else {
/* 2789 */       bool = false;
/*      */     } 
/* 2791 */     if (bool) {
/* 2792 */       b = 3;
/* 2793 */       double d = d7;
/* 2794 */       d7 = d9;
/* 2795 */       d9 = d;
/* 2796 */       d = d4;
/* 2797 */       d4 = d6;
/* 2798 */       d6 = d;
/*      */     } 
/*      */     
/* 2801 */     double d8 = paramDouble2;
/* 2802 */     double d5 = Math.abs(d8);
/* 2803 */     if (d5 == 0.0D) {
/*      */       
/* 2805 */       paramArrayOfdouble1[1] = d6;
/* 2806 */       paramArrayOfdouble1[0] = d4;
/* 2807 */       d10 = 1.0D;
/* 2808 */       d11 = 1.0D;
/* 2809 */       d12 = 0.0D;
/* 2810 */       d13 = 0.0D;
/*      */     } else {
/* 2812 */       boolean bool1 = true;
/*      */       
/* 2814 */       if (d5 > d4) {
/* 2815 */         b = 2;
/* 2816 */         if (d4 / d5 < 1.110223024E-16D) {
/*      */           
/* 2818 */           bool1 = false;
/* 2819 */           d15 = d5;
/* 2820 */           if (d6 > 1.0D) {
/* 2821 */             d14 = d4 / d5 / d6;
/*      */           } else {
/* 2823 */             d14 = d4 / d5 * d6;
/*      */           } 
/* 2825 */           d10 = 1.0D;
/* 2826 */           d12 = d9 / d8;
/* 2827 */           d13 = 1.0D;
/* 2828 */           d11 = d7 / d8;
/*      */         } 
/*      */       } 
/* 2831 */       if (bool1) {
/*      */         
/* 2833 */         double d18, d20, d17 = d4 - d6;
/* 2834 */         if (d17 == d4) {
/*      */           
/* 2836 */           d18 = 1.0D;
/*      */         } else {
/* 2838 */           d18 = d17 / d4;
/*      */         } 
/*      */         
/* 2841 */         double d19 = d8 / d7;
/*      */         
/* 2843 */         double d22 = 2.0D - d18;
/*      */         
/* 2845 */         double d23 = d19 * d19;
/* 2846 */         double d24 = d22 * d22;
/* 2847 */         double d21 = Math.sqrt(d24 + d23);
/*      */         
/* 2849 */         if (d18 == 0.0D) {
/* 2850 */           d20 = Math.abs(d19);
/*      */         } else {
/* 2852 */           d20 = Math.sqrt(d18 * d18 + d23);
/*      */         } 
/*      */         
/* 2855 */         double d16 = (d21 + d20) * 0.5D;
/*      */         
/* 2857 */         if (d5 > d4) {
/* 2858 */           b = 2;
/* 2859 */           if (d4 / d5 < 1.110223024E-16D) {
/*      */             
/* 2861 */             bool1 = false;
/* 2862 */             d15 = d5;
/* 2863 */             if (d6 > 1.0D) {
/* 2864 */               d14 = d4 / d5 / d6;
/*      */             } else {
/* 2866 */               d14 = d4 / d5 * d6;
/*      */             } 
/* 2868 */             d10 = 1.0D;
/* 2869 */             d12 = d9 / d8;
/* 2870 */             d13 = 1.0D;
/* 2871 */             d11 = d7 / d8;
/*      */           } 
/*      */         } 
/* 2874 */         if (bool1) {
/*      */           
/* 2876 */           d17 = d4 - d6;
/* 2877 */           if (d17 == d4) {
/*      */             
/* 2879 */             d18 = 1.0D;
/*      */           } else {
/* 2881 */             d18 = d17 / d4;
/*      */           } 
/*      */           
/* 2884 */           d19 = d8 / d7;
/*      */           
/* 2886 */           d22 = 2.0D - d18;
/*      */           
/* 2888 */           d23 = d19 * d19;
/* 2889 */           d24 = d22 * d22;
/* 2890 */           d21 = Math.sqrt(d24 + d23);
/*      */           
/* 2892 */           if (d18 == 0.0D) {
/* 2893 */             d20 = Math.abs(d19);
/*      */           } else {
/* 2895 */             d20 = Math.sqrt(d18 * d18 + d23);
/*      */           } 
/*      */           
/* 2898 */           d16 = (d21 + d20) * 0.5D;
/*      */ 
/*      */           
/* 2901 */           d14 = d6 / d16;
/* 2902 */           d15 = d4 * d16;
/* 2903 */           if (d23 == 0.0D) {
/*      */             
/* 2905 */             if (d18 == 0.0D) {
/* 2906 */               d22 = d_sign(d1, d7) * d_sign(d2, d8);
/*      */             } else {
/* 2908 */               d22 = d8 / d_sign(d17, d7) + d19 / d22;
/*      */             } 
/*      */           } else {
/* 2911 */             d22 = (d19 / (d21 + d22) + d19 / (d20 + d18)) * (d16 + 1.0D);
/*      */           } 
/* 2913 */           d18 = Math.sqrt(d22 * d22 + 4.0D);
/* 2914 */           d11 = 2.0D / d18;
/* 2915 */           d13 = d22 / d18;
/* 2916 */           d10 = (d11 + d13 * d19) / d16;
/* 2917 */           d12 = d9 / d7 * d13 / d16;
/*      */         } 
/*      */       } 
/* 2920 */       if (bool) {
/* 2921 */         paramArrayOfdouble3[0] = d13;
/* 2922 */         paramArrayOfdouble2[0] = d11;
/* 2923 */         paramArrayOfdouble5[0] = d12;
/* 2924 */         paramArrayOfdouble4[0] = d10;
/*      */       } else {
/* 2926 */         paramArrayOfdouble3[0] = d10;
/* 2927 */         paramArrayOfdouble2[0] = d12;
/* 2928 */         paramArrayOfdouble5[0] = d11;
/* 2929 */         paramArrayOfdouble4[0] = d13;
/*      */       } 
/*      */       
/* 2932 */       if (b == 1) {
/* 2933 */         d3 = d_sign(d2, paramArrayOfdouble5[0]) * d_sign(d2, paramArrayOfdouble3[0]) * d_sign(d2, paramDouble1);
/*      */       }
/* 2935 */       if (b == 2) {
/* 2936 */         d3 = d_sign(d2, paramArrayOfdouble4[0]) * d_sign(d2, paramArrayOfdouble3[0]) * d_sign(d2, paramDouble2);
/*      */       }
/* 2938 */       if (b == 3) {
/* 2939 */         d3 = d_sign(d2, paramArrayOfdouble4[0]) * d_sign(d2, paramArrayOfdouble2[0]) * d_sign(d2, paramDouble3);
/*      */       }
/* 2941 */       paramArrayOfdouble1[paramInt] = d_sign(d15, d3);
/* 2942 */       double d = d3 * d_sign(d2, paramDouble1) * d_sign(d2, paramDouble3);
/* 2943 */       paramArrayOfdouble1[paramInt + 1] = d_sign(d14, d);
/*      */     } 
/*      */ 
/*      */     
/* 2947 */     return 0;
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
/*      */   static double compute_rot(double paramDouble1, double paramDouble2, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, int paramInt1, int paramInt2) {
/*      */     double d1, d2, d3;
/* 2961 */     if (paramDouble2 == 0.0D) {
/* 2962 */       d1 = 1.0D;
/* 2963 */       d2 = 0.0D;
/* 2964 */       d3 = paramDouble1;
/* 2965 */     } else if (paramDouble1 == 0.0D) {
/* 2966 */       d1 = 0.0D;
/* 2967 */       d2 = 1.0D;
/* 2968 */       d3 = paramDouble2;
/*      */     } else {
/* 2970 */       double d5 = paramDouble1;
/* 2971 */       double d6 = paramDouble2;
/* 2972 */       double d4 = max(Math.abs(d5), Math.abs(d6));
/* 2973 */       if (d4 >= 4.994797680505588E145D) {
/* 2974 */         byte b3 = 0;
/* 2975 */         while (d4 >= 4.994797680505588E145D) {
/* 2976 */           b3++;
/* 2977 */           d5 *= 2.002083095183101E-146D;
/* 2978 */           d6 *= 2.002083095183101E-146D;
/* 2979 */           d4 = max(Math.abs(d5), Math.abs(d6));
/*      */         } 
/* 2981 */         d3 = Math.sqrt(d5 * d5 + d6 * d6);
/* 2982 */         d1 = d5 / d3;
/* 2983 */         d2 = d6 / d3;
/* 2984 */         byte b1 = b3;
/* 2985 */         for (byte b2 = 1; b2 <= b3; b2++) {
/* 2986 */           d3 *= 4.994797680505588E145D;
/*      */         }
/* 2988 */       } else if (d4 <= 2.002083095183101E-146D) {
/* 2989 */         byte b3 = 0;
/* 2990 */         while (d4 <= 2.002083095183101E-146D) {
/* 2991 */           b3++;
/* 2992 */           d5 *= 4.994797680505588E145D;
/* 2993 */           d6 *= 4.994797680505588E145D;
/* 2994 */           d4 = max(Math.abs(d5), Math.abs(d6));
/*      */         } 
/* 2996 */         d3 = Math.sqrt(d5 * d5 + d6 * d6);
/* 2997 */         d1 = d5 / d3;
/* 2998 */         d2 = d6 / d3;
/* 2999 */         byte b1 = b3;
/* 3000 */         for (byte b2 = 1; b2 <= b3; b2++) {
/* 3001 */           d3 *= 2.002083095183101E-146D;
/*      */         }
/*      */       } else {
/* 3004 */         d3 = Math.sqrt(d5 * d5 + d6 * d6);
/* 3005 */         d1 = d5 / d3;
/* 3006 */         d2 = d6 / d3;
/*      */       } 
/* 3008 */       if (Math.abs(paramDouble1) > Math.abs(paramDouble2) && d1 < 0.0D) {
/* 3009 */         d1 = -d1;
/* 3010 */         d2 = -d2;
/* 3011 */         d3 = -d3;
/*      */       } 
/*      */     } 
/* 3014 */     paramArrayOfdouble1[paramInt1] = d2;
/* 3015 */     paramArrayOfdouble2[paramInt1] = d1;
/* 3016 */     return d3;
/*      */   }
/*      */ 
/*      */   
/*      */   static void print_mat(double[] paramArrayOfdouble) {
/* 3021 */     for (byte b = 0; b < 3; b++) {
/* 3022 */       System.out.println(paramArrayOfdouble[b * 3 + 0] + " " + paramArrayOfdouble[b * 3 + 1] + " " + paramArrayOfdouble[b * 3 + 2] + "\n");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void print_det(double[] paramArrayOfdouble) {
/* 3029 */     double d = paramArrayOfdouble[0] * paramArrayOfdouble[4] * paramArrayOfdouble[8] + paramArrayOfdouble[1] * paramArrayOfdouble[5] * paramArrayOfdouble[6] + paramArrayOfdouble[2] * paramArrayOfdouble[3] * paramArrayOfdouble[7] - paramArrayOfdouble[2] * paramArrayOfdouble[4] * paramArrayOfdouble[6] - paramArrayOfdouble[0] * paramArrayOfdouble[5] * paramArrayOfdouble[7] - paramArrayOfdouble[1] * paramArrayOfdouble[3] * paramArrayOfdouble[8];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3035 */     System.out.println("det= " + d);
/*      */   }
/*      */   
/*      */   static void mat_mul(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, double[] paramArrayOfdouble3) {
/* 3039 */     double[] arrayOfDouble = new double[9];
/*      */     
/* 3041 */     arrayOfDouble[0] = paramArrayOfdouble1[0] * paramArrayOfdouble2[0] + paramArrayOfdouble1[1] * paramArrayOfdouble2[3] + paramArrayOfdouble1[2] * paramArrayOfdouble2[6];
/* 3042 */     arrayOfDouble[1] = paramArrayOfdouble1[0] * paramArrayOfdouble2[1] + paramArrayOfdouble1[1] * paramArrayOfdouble2[4] + paramArrayOfdouble1[2] * paramArrayOfdouble2[7];
/* 3043 */     arrayOfDouble[2] = paramArrayOfdouble1[0] * paramArrayOfdouble2[2] + paramArrayOfdouble1[1] * paramArrayOfdouble2[5] + paramArrayOfdouble1[2] * paramArrayOfdouble2[8];
/*      */     
/* 3045 */     arrayOfDouble[3] = paramArrayOfdouble1[3] * paramArrayOfdouble2[0] + paramArrayOfdouble1[4] * paramArrayOfdouble2[3] + paramArrayOfdouble1[5] * paramArrayOfdouble2[6];
/* 3046 */     arrayOfDouble[4] = paramArrayOfdouble1[3] * paramArrayOfdouble2[1] + paramArrayOfdouble1[4] * paramArrayOfdouble2[4] + paramArrayOfdouble1[5] * paramArrayOfdouble2[7];
/* 3047 */     arrayOfDouble[5] = paramArrayOfdouble1[3] * paramArrayOfdouble2[2] + paramArrayOfdouble1[4] * paramArrayOfdouble2[5] + paramArrayOfdouble1[5] * paramArrayOfdouble2[8];
/*      */     
/* 3049 */     arrayOfDouble[6] = paramArrayOfdouble1[6] * paramArrayOfdouble2[0] + paramArrayOfdouble1[7] * paramArrayOfdouble2[3] + paramArrayOfdouble1[8] * paramArrayOfdouble2[6];
/* 3050 */     arrayOfDouble[7] = paramArrayOfdouble1[6] * paramArrayOfdouble2[1] + paramArrayOfdouble1[7] * paramArrayOfdouble2[4] + paramArrayOfdouble1[8] * paramArrayOfdouble2[7];
/* 3051 */     arrayOfDouble[8] = paramArrayOfdouble1[6] * paramArrayOfdouble2[2] + paramArrayOfdouble1[7] * paramArrayOfdouble2[5] + paramArrayOfdouble1[8] * paramArrayOfdouble2[8];
/*      */     
/* 3053 */     for (byte b = 0; b < 9; b++)
/* 3054 */       paramArrayOfdouble3[b] = arrayOfDouble[b]; 
/*      */   }
/*      */   
/*      */   static void transpose_mat(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
/* 3058 */     paramArrayOfdouble2[0] = paramArrayOfdouble1[0];
/* 3059 */     paramArrayOfdouble2[1] = paramArrayOfdouble1[3];
/* 3060 */     paramArrayOfdouble2[2] = paramArrayOfdouble1[6];
/*      */     
/* 3062 */     paramArrayOfdouble2[3] = paramArrayOfdouble1[1];
/* 3063 */     paramArrayOfdouble2[4] = paramArrayOfdouble1[4];
/* 3064 */     paramArrayOfdouble2[5] = paramArrayOfdouble1[7];
/*      */     
/* 3066 */     paramArrayOfdouble2[6] = paramArrayOfdouble1[2];
/* 3067 */     paramArrayOfdouble2[7] = paramArrayOfdouble1[5];
/* 3068 */     paramArrayOfdouble2[8] = paramArrayOfdouble1[8];
/*      */   }
/*      */   static double max3(double[] paramArrayOfdouble) {
/* 3071 */     if (paramArrayOfdouble[0] > paramArrayOfdouble[1]) {
/* 3072 */       if (paramArrayOfdouble[0] > paramArrayOfdouble[2]) {
/* 3073 */         return paramArrayOfdouble[0];
/*      */       }
/* 3075 */       return paramArrayOfdouble[2];
/*      */     } 
/* 3077 */     if (paramArrayOfdouble[1] > paramArrayOfdouble[2]) {
/* 3078 */       return paramArrayOfdouble[1];
/*      */     }
/* 3080 */     return paramArrayOfdouble[2];
/*      */   }
/*      */ 
/*      */   
/*      */   private static final boolean almostEqual(double paramDouble1, double paramDouble2) {
/* 3085 */     if (paramDouble1 == paramDouble2) {
/* 3086 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 3090 */     double d1 = Math.abs(paramDouble1 - paramDouble2);
/* 3091 */     double d2 = Math.abs(paramDouble1);
/* 3092 */     double d3 = Math.abs(paramDouble2);
/* 3093 */     double d4 = (d2 >= d3) ? d2 : d3;
/*      */     
/* 3095 */     if (d1 < 1.0E-6D) {
/* 3096 */       return true;
/*      */     }
/* 3098 */     if (d1 / d4 < 1.0E-4D) {
/* 3099 */       return true;
/*      */     }
/* 3101 */     return false;
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
/*      */   public Object clone() {
/* 3113 */     Matrix3d matrix3d = null;
/*      */     try {
/* 3115 */       matrix3d = (Matrix3d)super.clone();
/* 3116 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */       
/* 3118 */       throw new InternalError();
/*      */     } 
/*      */ 
/*      */     
/* 3122 */     return matrix3d;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM00() {
/* 3131 */     return this.m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM00(double paramDouble) {
/* 3142 */     this.m00 = paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM01() {
/* 3153 */     return this.m01;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM01(double paramDouble) {
/* 3164 */     this.m01 = paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM02() {
/* 3175 */     return this.m02;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM02(double paramDouble) {
/* 3186 */     this.m02 = paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM10() {
/* 3197 */     return this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM10(double paramDouble) {
/* 3208 */     this.m10 = paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM11() {
/* 3219 */     return this.m11;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM11(double paramDouble) {
/* 3230 */     this.m11 = paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM12() {
/* 3241 */     return this.m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM12(double paramDouble) {
/* 3252 */     this.m12 = paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM20() {
/* 3263 */     return this.m20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM20(double paramDouble) {
/* 3274 */     this.m20 = paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM21() {
/* 3285 */     return this.m21;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM21(double paramDouble) {
/* 3296 */     this.m21 = paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM22() {
/* 3307 */     return this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM22(double paramDouble) {
/* 3318 */     this.m22 = paramDouble;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Matrix3d.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
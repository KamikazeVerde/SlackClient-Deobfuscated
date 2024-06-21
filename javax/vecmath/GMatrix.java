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
/*      */ public class GMatrix
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   static final long serialVersionUID = 2777097312029690941L;
/*      */   private static final boolean debug = false;
/*      */   int nRow;
/*      */   int nCol;
/*      */   double[][] values;
/*      */   private static final double EPS = 1.0E-10D;
/*      */   
/*      */   public GMatrix(int paramInt1, int paramInt2) {
/*      */     int i;
/*   66 */     this.values = new double[paramInt1][paramInt2];
/*   67 */     this.nRow = paramInt1;
/*   68 */     this.nCol = paramInt2;
/*      */     
/*      */     byte b;
/*   71 */     for (b = 0; b < paramInt1; b++) {
/*   72 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/*   73 */         this.values[b][b1] = 0.0D;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*   78 */     if (paramInt1 < paramInt2) {
/*   79 */       i = paramInt1;
/*      */     } else {
/*   81 */       i = paramInt2;
/*      */     } 
/*   83 */     for (b = 0; b < i; b++) {
/*   84 */       this.values[b][b] = 1.0D;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public GMatrix(int paramInt1, int paramInt2, double[] paramArrayOfdouble) {
/*  102 */     this.values = new double[paramInt1][paramInt2];
/*  103 */     this.nRow = paramInt1;
/*  104 */     this.nCol = paramInt2;
/*      */ 
/*      */     
/*  107 */     for (byte b = 0; b < paramInt1; b++) {
/*  108 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/*  109 */         this.values[b][b1] = paramArrayOfdouble[b * paramInt2 + b1];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GMatrix(GMatrix paramGMatrix) {
/*  121 */     this.nRow = paramGMatrix.nRow;
/*  122 */     this.nCol = paramGMatrix.nCol;
/*  123 */     this.values = new double[this.nRow][this.nCol];
/*      */ 
/*      */     
/*  126 */     for (byte b = 0; b < this.nRow; b++) {
/*  127 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/*  128 */         this.values[b][b1] = paramGMatrix.values[b][b1];
/*      */       }
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
/*      */   public final void mul(GMatrix paramGMatrix) {
/*  142 */     if (this.nCol != paramGMatrix.nRow || this.nCol != paramGMatrix.nCol) {
/*  143 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix0"));
/*      */     }
/*      */     
/*  146 */     double[][] arrayOfDouble = new double[this.nRow][this.nCol];
/*      */     
/*  148 */     for (byte b = 0; b < this.nRow; b++) {
/*  149 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/*  150 */         arrayOfDouble[b][b1] = 0.0D;
/*  151 */         for (byte b2 = 0; b2 < this.nCol; b2++) {
/*  152 */           arrayOfDouble[b][b1] = arrayOfDouble[b][b1] + this.values[b][b2] * paramGMatrix.values[b2][b1];
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  157 */     this.values = arrayOfDouble;
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
/*      */   public final void mul(GMatrix paramGMatrix1, GMatrix paramGMatrix2) {
/*  170 */     if (paramGMatrix1.nCol != paramGMatrix2.nRow || this.nRow != paramGMatrix1.nRow || this.nCol != paramGMatrix2.nCol) {
/*  171 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix1"));
/*      */     }
/*      */     
/*  174 */     double[][] arrayOfDouble = new double[this.nRow][this.nCol];
/*      */     
/*  176 */     for (byte b = 0; b < paramGMatrix1.nRow; b++) {
/*  177 */       for (byte b1 = 0; b1 < paramGMatrix2.nCol; b1++) {
/*  178 */         arrayOfDouble[b][b1] = 0.0D;
/*  179 */         for (byte b2 = 0; b2 < paramGMatrix1.nCol; b2++) {
/*  180 */           arrayOfDouble[b][b1] = arrayOfDouble[b][b1] + paramGMatrix1.values[b][b2] * paramGMatrix2.values[b2][b1];
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  185 */     this.values = arrayOfDouble;
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
/*      */   public final void mul(GVector paramGVector1, GVector paramGVector2) {
/*  200 */     if (this.nRow < paramGVector1.getSize()) {
/*  201 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix2"));
/*      */     }
/*      */     
/*  204 */     if (this.nCol < paramGVector2.getSize()) {
/*  205 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix3"));
/*      */     }
/*      */     
/*  208 */     for (byte b = 0; b < paramGVector1.getSize(); b++) {
/*  209 */       for (byte b1 = 0; b1 < paramGVector2.getSize(); b1++) {
/*  210 */         this.values[b][b1] = paramGVector1.values[b] * paramGVector2.values[b1];
/*      */       }
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
/*      */   public final void add(GMatrix paramGMatrix) {
/*  223 */     if (this.nRow != paramGMatrix.nRow) {
/*  224 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix4"));
/*      */     }
/*      */     
/*  227 */     if (this.nCol != paramGMatrix.nCol) {
/*  228 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix5"));
/*      */     }
/*      */     
/*  231 */     for (byte b = 0; b < this.nRow; b++) {
/*  232 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/*  233 */         this.values[b][b1] = this.values[b][b1] + paramGMatrix.values[b][b1];
/*      */       }
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
/*      */   public final void add(GMatrix paramGMatrix1, GMatrix paramGMatrix2) {
/*  247 */     if (paramGMatrix2.nRow != paramGMatrix1.nRow) {
/*  248 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix6"));
/*      */     }
/*      */     
/*  251 */     if (paramGMatrix2.nCol != paramGMatrix1.nCol) {
/*  252 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix7"));
/*      */     }
/*      */     
/*  255 */     if (this.nCol != paramGMatrix1.nCol || this.nRow != paramGMatrix1.nRow) {
/*  256 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix8"));
/*      */     }
/*      */     
/*  259 */     for (byte b = 0; b < this.nRow; b++) {
/*  260 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/*  261 */         this.values[b][b1] = paramGMatrix1.values[b][b1] + paramGMatrix2.values[b][b1];
/*      */       }
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
/*      */   public final void sub(GMatrix paramGMatrix) {
/*  274 */     if (this.nRow != paramGMatrix.nRow) {
/*  275 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix9"));
/*      */     }
/*      */     
/*  278 */     if (this.nCol != paramGMatrix.nCol) {
/*  279 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix28"));
/*      */     }
/*      */     
/*  282 */     for (byte b = 0; b < this.nRow; b++) {
/*  283 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/*  284 */         this.values[b][b1] = this.values[b][b1] - paramGMatrix.values[b][b1];
/*      */       }
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
/*      */   public final void sub(GMatrix paramGMatrix1, GMatrix paramGMatrix2) {
/*  298 */     if (paramGMatrix2.nRow != paramGMatrix1.nRow) {
/*  299 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix10"));
/*      */     }
/*      */     
/*  302 */     if (paramGMatrix2.nCol != paramGMatrix1.nCol) {
/*  303 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix11"));
/*      */     }
/*      */     
/*  306 */     if (this.nRow != paramGMatrix1.nRow || this.nCol != paramGMatrix1.nCol) {
/*  307 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix12"));
/*      */     }
/*      */     
/*  310 */     for (byte b = 0; b < this.nRow; b++) {
/*  311 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/*  312 */         this.values[b][b1] = paramGMatrix1.values[b][b1] - paramGMatrix2.values[b][b1];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/*  323 */     for (byte b = 0; b < this.nRow; b++) {
/*  324 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/*  325 */         this.values[b][b1] = -this.values[b][b1];
/*      */       }
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
/*      */   public final void negate(GMatrix paramGMatrix) {
/*  338 */     if (this.nRow != paramGMatrix.nRow || this.nCol != paramGMatrix.nCol) {
/*  339 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix13"));
/*      */     }
/*      */     
/*  342 */     for (byte b = 0; b < this.nRow; b++) {
/*  343 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/*  344 */         this.values[b][b1] = -paramGMatrix.values[b][b1];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setIdentity() {
/*      */     int i;
/*      */     byte b;
/*  355 */     for (b = 0; b < this.nRow; b++) {
/*  356 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/*  357 */         this.values[b][b1] = 0.0D;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  362 */     if (this.nRow < this.nCol) {
/*  363 */       i = this.nRow;
/*      */     } else {
/*  365 */       i = this.nCol;
/*      */     } 
/*  367 */     for (b = 0; b < i; b++) {
/*  368 */       this.values[b][b] = 1.0D;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/*  378 */     for (byte b = 0; b < this.nRow; b++) {
/*  379 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/*  380 */         this.values[b][b1] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void identityMinus() {
/*      */     int i;
/*      */     byte b;
/*  393 */     for (b = 0; b < this.nRow; b++) {
/*  394 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/*  395 */         this.values[b][b1] = -this.values[b][b1];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  400 */     if (this.nRow < this.nCol) {
/*  401 */       i = this.nRow;
/*      */     } else {
/*  403 */       i = this.nCol;
/*      */     } 
/*  405 */     for (b = 0; b < i; b++) {
/*  406 */       this.values[b][b] = this.values[b][b] + 1.0D;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/*  416 */     invertGeneral(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert(GMatrix paramGMatrix) {
/*  426 */     invertGeneral(paramGMatrix);
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
/*      */   public final void copySubMatrix(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, GMatrix paramGMatrix) {
/*  451 */     if (this != paramGMatrix) {
/*  452 */       for (byte b = 0; b < paramInt3; b++) {
/*  453 */         for (byte b1 = 0; b1 < paramInt4; b1++) {
/*  454 */           paramGMatrix.values[paramInt5 + b][paramInt6 + b1] = this.values[paramInt1 + b][paramInt2 + b1];
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       
/*  459 */       double[][] arrayOfDouble = new double[paramInt3][paramInt4]; byte b;
/*  460 */       for (b = 0; b < paramInt3; b++) {
/*  461 */         for (byte b1 = 0; b1 < paramInt4; b1++) {
/*  462 */           arrayOfDouble[b][b1] = this.values[paramInt1 + b][paramInt2 + b1];
/*      */         }
/*      */       } 
/*  465 */       for (b = 0; b < paramInt3; b++) {
/*  466 */         for (byte b1 = 0; b1 < paramInt4; b1++) {
/*  467 */           paramGMatrix.values[paramInt5 + b][paramInt6 + b1] = arrayOfDouble[b][b1];
/*      */         }
/*      */       } 
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
/*      */   public final void setSize(int paramInt1, int paramInt2) {
/*      */     int i, j;
/*  482 */     double[][] arrayOfDouble = new double[paramInt1][paramInt2];
/*      */ 
/*      */     
/*  485 */     if (this.nRow < paramInt1) {
/*  486 */       i = this.nRow;
/*      */     } else {
/*  488 */       i = paramInt1;
/*      */     } 
/*  490 */     if (this.nCol < paramInt2) {
/*  491 */       j = this.nCol;
/*      */     } else {
/*  493 */       j = paramInt2;
/*      */     } 
/*  495 */     for (byte b = 0; b < i; b++) {
/*  496 */       for (byte b1 = 0; b1 < j; b1++) {
/*  497 */         arrayOfDouble[b][b1] = this.values[b][b1];
/*      */       }
/*      */     } 
/*      */     
/*  501 */     this.nRow = paramInt1;
/*  502 */     this.nCol = paramInt2;
/*      */     
/*  504 */     this.values = arrayOfDouble;
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
/*      */   public final void set(double[] paramArrayOfdouble) {
/*  519 */     for (byte b = 0; b < this.nRow; b++) {
/*  520 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/*  521 */         this.values[b][b1] = paramArrayOfdouble[this.nCol * b + b1];
/*      */       }
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
/*      */   public final void set(Matrix3f paramMatrix3f) {
/*  534 */     if (this.nCol < 3 || this.nRow < 3) {
/*  535 */       this.nCol = 3;
/*  536 */       this.nRow = 3;
/*  537 */       this.values = new double[this.nRow][this.nCol];
/*      */     } 
/*      */     
/*  540 */     this.values[0][0] = paramMatrix3f.m00;
/*  541 */     this.values[0][1] = paramMatrix3f.m01;
/*  542 */     this.values[0][2] = paramMatrix3f.m02;
/*      */     
/*  544 */     this.values[1][0] = paramMatrix3f.m10;
/*  545 */     this.values[1][1] = paramMatrix3f.m11;
/*  546 */     this.values[1][2] = paramMatrix3f.m12;
/*      */     
/*  548 */     this.values[2][0] = paramMatrix3f.m20;
/*  549 */     this.values[2][1] = paramMatrix3f.m21;
/*  550 */     this.values[2][2] = paramMatrix3f.m22;
/*      */     
/*  552 */     for (byte b = 3; b < this.nRow; b++) {
/*  553 */       for (byte b1 = 3; b1 < this.nCol; b1++) {
/*  554 */         this.values[b][b1] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3d paramMatrix3d) {
/*  565 */     if (this.nRow < 3 || this.nCol < 3) {
/*  566 */       this.values = new double[3][3];
/*  567 */       this.nRow = 3;
/*  568 */       this.nCol = 3;
/*      */     } 
/*      */     
/*  571 */     this.values[0][0] = paramMatrix3d.m00;
/*  572 */     this.values[0][1] = paramMatrix3d.m01;
/*  573 */     this.values[0][2] = paramMatrix3d.m02;
/*      */     
/*  575 */     this.values[1][0] = paramMatrix3d.m10;
/*  576 */     this.values[1][1] = paramMatrix3d.m11;
/*  577 */     this.values[1][2] = paramMatrix3d.m12;
/*      */     
/*  579 */     this.values[2][0] = paramMatrix3d.m20;
/*  580 */     this.values[2][1] = paramMatrix3d.m21;
/*  581 */     this.values[2][2] = paramMatrix3d.m22;
/*      */     
/*  583 */     for (byte b = 3; b < this.nRow; b++) {
/*  584 */       for (byte b1 = 3; b1 < this.nCol; b1++) {
/*  585 */         this.values[b][b1] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4f paramMatrix4f) {
/*  597 */     if (this.nRow < 4 || this.nCol < 4) {
/*  598 */       this.values = new double[4][4];
/*  599 */       this.nRow = 4;
/*  600 */       this.nCol = 4;
/*      */     } 
/*      */     
/*  603 */     this.values[0][0] = paramMatrix4f.m00;
/*  604 */     this.values[0][1] = paramMatrix4f.m01;
/*  605 */     this.values[0][2] = paramMatrix4f.m02;
/*  606 */     this.values[0][3] = paramMatrix4f.m03;
/*      */     
/*  608 */     this.values[1][0] = paramMatrix4f.m10;
/*  609 */     this.values[1][1] = paramMatrix4f.m11;
/*  610 */     this.values[1][2] = paramMatrix4f.m12;
/*  611 */     this.values[1][3] = paramMatrix4f.m13;
/*      */     
/*  613 */     this.values[2][0] = paramMatrix4f.m20;
/*  614 */     this.values[2][1] = paramMatrix4f.m21;
/*  615 */     this.values[2][2] = paramMatrix4f.m22;
/*  616 */     this.values[2][3] = paramMatrix4f.m23;
/*      */     
/*  618 */     this.values[3][0] = paramMatrix4f.m30;
/*  619 */     this.values[3][1] = paramMatrix4f.m31;
/*  620 */     this.values[3][2] = paramMatrix4f.m32;
/*  621 */     this.values[3][3] = paramMatrix4f.m33;
/*      */     
/*  623 */     for (byte b = 4; b < this.nRow; b++) {
/*  624 */       for (byte b1 = 4; b1 < this.nCol; b1++) {
/*  625 */         this.values[b][b1] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4d paramMatrix4d) {
/*  636 */     if (this.nRow < 4 || this.nCol < 4) {
/*  637 */       this.values = new double[4][4];
/*  638 */       this.nRow = 4;
/*  639 */       this.nCol = 4;
/*      */     } 
/*      */     
/*  642 */     this.values[0][0] = paramMatrix4d.m00;
/*  643 */     this.values[0][1] = paramMatrix4d.m01;
/*  644 */     this.values[0][2] = paramMatrix4d.m02;
/*  645 */     this.values[0][3] = paramMatrix4d.m03;
/*      */     
/*  647 */     this.values[1][0] = paramMatrix4d.m10;
/*  648 */     this.values[1][1] = paramMatrix4d.m11;
/*  649 */     this.values[1][2] = paramMatrix4d.m12;
/*  650 */     this.values[1][3] = paramMatrix4d.m13;
/*      */     
/*  652 */     this.values[2][0] = paramMatrix4d.m20;
/*  653 */     this.values[2][1] = paramMatrix4d.m21;
/*  654 */     this.values[2][2] = paramMatrix4d.m22;
/*  655 */     this.values[2][3] = paramMatrix4d.m23;
/*      */     
/*  657 */     this.values[3][0] = paramMatrix4d.m30;
/*  658 */     this.values[3][1] = paramMatrix4d.m31;
/*  659 */     this.values[3][2] = paramMatrix4d.m32;
/*  660 */     this.values[3][3] = paramMatrix4d.m33;
/*      */     
/*  662 */     for (byte b = 4; b < this.nRow; b++) {
/*  663 */       for (byte b1 = 4; b1 < this.nCol; b1++) {
/*  664 */         this.values[b][b1] = 0.0D;
/*      */       }
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
/*      */   public final void set(GMatrix paramGMatrix) {
/*  677 */     if (this.nRow < paramGMatrix.nRow || this.nCol < paramGMatrix.nCol) {
/*  678 */       this.nRow = paramGMatrix.nRow;
/*  679 */       this.nCol = paramGMatrix.nCol;
/*  680 */       this.values = new double[this.nRow][this.nCol];
/*      */     } 
/*      */     int i;
/*  683 */     for (i = 0; i < Math.min(this.nRow, paramGMatrix.nRow); i++) {
/*  684 */       for (byte b = 0; b < Math.min(this.nCol, paramGMatrix.nCol); b++) {
/*  685 */         this.values[i][b] = paramGMatrix.values[i][b];
/*      */       }
/*      */     } 
/*      */     
/*  689 */     for (i = paramGMatrix.nRow; i < this.nRow; i++) {
/*  690 */       for (int j = paramGMatrix.nCol; j < this.nCol; j++) {
/*  691 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getNumRow() {
/*  702 */     return this.nRow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getNumCol() {
/*  711 */     return this.nCol;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getElement(int paramInt1, int paramInt2) {
/*  722 */     return this.values[paramInt1][paramInt2];
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
/*      */   public final void setElement(int paramInt1, int paramInt2, double paramDouble) {
/*  734 */     this.values[paramInt1][paramInt2] = paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int paramInt, double[] paramArrayOfdouble) {
/*  744 */     for (byte b = 0; b < this.nCol; b++) {
/*  745 */       paramArrayOfdouble[b] = this.values[paramInt][b];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int paramInt, GVector paramGVector) {
/*  756 */     if (paramGVector.getSize() < this.nCol) {
/*  757 */       paramGVector.setSize(this.nCol);
/*      */     }
/*  759 */     for (byte b = 0; b < this.nCol; b++) {
/*  760 */       paramGVector.values[b] = this.values[paramInt][b];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getColumn(int paramInt, double[] paramArrayOfdouble) {
/*  771 */     for (byte b = 0; b < this.nRow; b++) {
/*  772 */       paramArrayOfdouble[b] = this.values[b][paramInt];
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
/*      */   public final void getColumn(int paramInt, GVector paramGVector) {
/*  784 */     if (paramGVector.getSize() < this.nRow) {
/*  785 */       paramGVector.setSize(this.nRow);
/*      */     }
/*  787 */     for (byte b = 0; b < this.nRow; b++) {
/*  788 */       paramGVector.values[b] = this.values[b][paramInt];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Matrix3d paramMatrix3d) {
/*  799 */     if (this.nRow < 3 || this.nCol < 3) {
/*  800 */       paramMatrix3d.setZero();
/*  801 */       if (this.nCol > 0) {
/*  802 */         if (this.nRow > 0) {
/*  803 */           paramMatrix3d.m00 = this.values[0][0];
/*  804 */           if (this.nRow > 1) {
/*  805 */             paramMatrix3d.m10 = this.values[1][0];
/*  806 */             if (this.nRow > 2) {
/*  807 */               paramMatrix3d.m20 = this.values[2][0];
/*      */             }
/*      */           } 
/*      */         } 
/*  811 */         if (this.nCol > 1) {
/*  812 */           if (this.nRow > 0) {
/*  813 */             paramMatrix3d.m01 = this.values[0][1];
/*  814 */             if (this.nRow > 1) {
/*  815 */               paramMatrix3d.m11 = this.values[1][1];
/*  816 */               if (this.nRow > 2) {
/*  817 */                 paramMatrix3d.m21 = this.values[2][1];
/*      */               }
/*      */             } 
/*      */           } 
/*  821 */           if (this.nCol > 2 && 
/*  822 */             this.nRow > 0) {
/*  823 */             paramMatrix3d.m02 = this.values[0][2];
/*  824 */             if (this.nRow > 1) {
/*  825 */               paramMatrix3d.m12 = this.values[1][2];
/*  826 */               if (this.nRow > 2) {
/*  827 */                 paramMatrix3d.m22 = this.values[2][2];
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  835 */       paramMatrix3d.m00 = this.values[0][0];
/*  836 */       paramMatrix3d.m01 = this.values[0][1];
/*  837 */       paramMatrix3d.m02 = this.values[0][2];
/*      */       
/*  839 */       paramMatrix3d.m10 = this.values[1][0];
/*  840 */       paramMatrix3d.m11 = this.values[1][1];
/*  841 */       paramMatrix3d.m12 = this.values[1][2];
/*      */       
/*  843 */       paramMatrix3d.m20 = this.values[2][0];
/*  844 */       paramMatrix3d.m21 = this.values[2][1];
/*  845 */       paramMatrix3d.m22 = this.values[2][2];
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
/*      */   public final void get(Matrix3f paramMatrix3f) {
/*  857 */     if (this.nRow < 3 || this.nCol < 3) {
/*  858 */       paramMatrix3f.setZero();
/*  859 */       if (this.nCol > 0) {
/*  860 */         if (this.nRow > 0) {
/*  861 */           paramMatrix3f.m00 = (float)this.values[0][0];
/*  862 */           if (this.nRow > 1) {
/*  863 */             paramMatrix3f.m10 = (float)this.values[1][0];
/*  864 */             if (this.nRow > 2) {
/*  865 */               paramMatrix3f.m20 = (float)this.values[2][0];
/*      */             }
/*      */           } 
/*      */         } 
/*  869 */         if (this.nCol > 1) {
/*  870 */           if (this.nRow > 0) {
/*  871 */             paramMatrix3f.m01 = (float)this.values[0][1];
/*  872 */             if (this.nRow > 1) {
/*  873 */               paramMatrix3f.m11 = (float)this.values[1][1];
/*  874 */               if (this.nRow > 2) {
/*  875 */                 paramMatrix3f.m21 = (float)this.values[2][1];
/*      */               }
/*      */             } 
/*      */           } 
/*  879 */           if (this.nCol > 2 && 
/*  880 */             this.nRow > 0) {
/*  881 */             paramMatrix3f.m02 = (float)this.values[0][2];
/*  882 */             if (this.nRow > 1) {
/*  883 */               paramMatrix3f.m12 = (float)this.values[1][2];
/*  884 */               if (this.nRow > 2) {
/*  885 */                 paramMatrix3f.m22 = (float)this.values[2][2];
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  893 */       paramMatrix3f.m00 = (float)this.values[0][0];
/*  894 */       paramMatrix3f.m01 = (float)this.values[0][1];
/*  895 */       paramMatrix3f.m02 = (float)this.values[0][2];
/*      */       
/*  897 */       paramMatrix3f.m10 = (float)this.values[1][0];
/*  898 */       paramMatrix3f.m11 = (float)this.values[1][1];
/*  899 */       paramMatrix3f.m12 = (float)this.values[1][2];
/*      */       
/*  901 */       paramMatrix3f.m20 = (float)this.values[2][0];
/*  902 */       paramMatrix3f.m21 = (float)this.values[2][1];
/*  903 */       paramMatrix3f.m22 = (float)this.values[2][2];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Matrix4d paramMatrix4d) {
/*  914 */     if (this.nRow < 4 || this.nCol < 4) {
/*  915 */       paramMatrix4d.setZero();
/*  916 */       if (this.nCol > 0) {
/*  917 */         if (this.nRow > 0) {
/*  918 */           paramMatrix4d.m00 = this.values[0][0];
/*  919 */           if (this.nRow > 1) {
/*  920 */             paramMatrix4d.m10 = this.values[1][0];
/*  921 */             if (this.nRow > 2) {
/*  922 */               paramMatrix4d.m20 = this.values[2][0];
/*  923 */               if (this.nRow > 3) {
/*  924 */                 paramMatrix4d.m30 = this.values[3][0];
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*  929 */         if (this.nCol > 1) {
/*  930 */           if (this.nRow > 0) {
/*  931 */             paramMatrix4d.m01 = this.values[0][1];
/*  932 */             if (this.nRow > 1) {
/*  933 */               paramMatrix4d.m11 = this.values[1][1];
/*  934 */               if (this.nRow > 2) {
/*  935 */                 paramMatrix4d.m21 = this.values[2][1];
/*  936 */                 if (this.nRow > 3) {
/*  937 */                   paramMatrix4d.m31 = this.values[3][1];
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*  942 */           if (this.nCol > 2) {
/*  943 */             if (this.nRow > 0) {
/*  944 */               paramMatrix4d.m02 = this.values[0][2];
/*  945 */               if (this.nRow > 1) {
/*  946 */                 paramMatrix4d.m12 = this.values[1][2];
/*  947 */                 if (this.nRow > 2) {
/*  948 */                   paramMatrix4d.m22 = this.values[2][2];
/*  949 */                   if (this.nRow > 3) {
/*  950 */                     paramMatrix4d.m32 = this.values[3][2];
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*  955 */             if (this.nCol > 3 && 
/*  956 */               this.nRow > 0) {
/*  957 */               paramMatrix4d.m03 = this.values[0][3];
/*  958 */               if (this.nRow > 1) {
/*  959 */                 paramMatrix4d.m13 = this.values[1][3];
/*  960 */                 if (this.nRow > 2) {
/*  961 */                   paramMatrix4d.m23 = this.values[2][3];
/*  962 */                   if (this.nRow > 3) {
/*  963 */                     paramMatrix4d.m33 = this.values[3][3];
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  973 */       paramMatrix4d.m00 = this.values[0][0];
/*  974 */       paramMatrix4d.m01 = this.values[0][1];
/*  975 */       paramMatrix4d.m02 = this.values[0][2];
/*  976 */       paramMatrix4d.m03 = this.values[0][3];
/*      */       
/*  978 */       paramMatrix4d.m10 = this.values[1][0];
/*  979 */       paramMatrix4d.m11 = this.values[1][1];
/*  980 */       paramMatrix4d.m12 = this.values[1][2];
/*  981 */       paramMatrix4d.m13 = this.values[1][3];
/*      */       
/*  983 */       paramMatrix4d.m20 = this.values[2][0];
/*  984 */       paramMatrix4d.m21 = this.values[2][1];
/*  985 */       paramMatrix4d.m22 = this.values[2][2];
/*  986 */       paramMatrix4d.m23 = this.values[2][3];
/*      */       
/*  988 */       paramMatrix4d.m30 = this.values[3][0];
/*  989 */       paramMatrix4d.m31 = this.values[3][1];
/*  990 */       paramMatrix4d.m32 = this.values[3][2];
/*  991 */       paramMatrix4d.m33 = this.values[3][3];
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
/*      */   public final void get(Matrix4f paramMatrix4f) {
/* 1004 */     if (this.nRow < 4 || this.nCol < 4) {
/* 1005 */       paramMatrix4f.setZero();
/* 1006 */       if (this.nCol > 0) {
/* 1007 */         if (this.nRow > 0) {
/* 1008 */           paramMatrix4f.m00 = (float)this.values[0][0];
/* 1009 */           if (this.nRow > 1) {
/* 1010 */             paramMatrix4f.m10 = (float)this.values[1][0];
/* 1011 */             if (this.nRow > 2) {
/* 1012 */               paramMatrix4f.m20 = (float)this.values[2][0];
/* 1013 */               if (this.nRow > 3) {
/* 1014 */                 paramMatrix4f.m30 = (float)this.values[3][0];
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/* 1019 */         if (this.nCol > 1) {
/* 1020 */           if (this.nRow > 0) {
/* 1021 */             paramMatrix4f.m01 = (float)this.values[0][1];
/* 1022 */             if (this.nRow > 1) {
/* 1023 */               paramMatrix4f.m11 = (float)this.values[1][1];
/* 1024 */               if (this.nRow > 2) {
/* 1025 */                 paramMatrix4f.m21 = (float)this.values[2][1];
/* 1026 */                 if (this.nRow > 3) {
/* 1027 */                   paramMatrix4f.m31 = (float)this.values[3][1];
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/* 1032 */           if (this.nCol > 2) {
/* 1033 */             if (this.nRow > 0) {
/* 1034 */               paramMatrix4f.m02 = (float)this.values[0][2];
/* 1035 */               if (this.nRow > 1) {
/* 1036 */                 paramMatrix4f.m12 = (float)this.values[1][2];
/* 1037 */                 if (this.nRow > 2) {
/* 1038 */                   paramMatrix4f.m22 = (float)this.values[2][2];
/* 1039 */                   if (this.nRow > 3) {
/* 1040 */                     paramMatrix4f.m32 = (float)this.values[3][2];
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/* 1045 */             if (this.nCol > 3 && 
/* 1046 */               this.nRow > 0) {
/* 1047 */               paramMatrix4f.m03 = (float)this.values[0][3];
/* 1048 */               if (this.nRow > 1) {
/* 1049 */                 paramMatrix4f.m13 = (float)this.values[1][3];
/* 1050 */                 if (this.nRow > 2) {
/* 1051 */                   paramMatrix4f.m23 = (float)this.values[2][3];
/* 1052 */                   if (this.nRow > 3) {
/* 1053 */                     paramMatrix4f.m33 = (float)this.values[3][3];
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1063 */       paramMatrix4f.m00 = (float)this.values[0][0];
/* 1064 */       paramMatrix4f.m01 = (float)this.values[0][1];
/* 1065 */       paramMatrix4f.m02 = (float)this.values[0][2];
/* 1066 */       paramMatrix4f.m03 = (float)this.values[0][3];
/*      */       
/* 1068 */       paramMatrix4f.m10 = (float)this.values[1][0];
/* 1069 */       paramMatrix4f.m11 = (float)this.values[1][1];
/* 1070 */       paramMatrix4f.m12 = (float)this.values[1][2];
/* 1071 */       paramMatrix4f.m13 = (float)this.values[1][3];
/*      */       
/* 1073 */       paramMatrix4f.m20 = (float)this.values[2][0];
/* 1074 */       paramMatrix4f.m21 = (float)this.values[2][1];
/* 1075 */       paramMatrix4f.m22 = (float)this.values[2][2];
/* 1076 */       paramMatrix4f.m23 = (float)this.values[2][3];
/*      */       
/* 1078 */       paramMatrix4f.m30 = (float)this.values[3][0];
/* 1079 */       paramMatrix4f.m31 = (float)this.values[3][1];
/* 1080 */       paramMatrix4f.m32 = (float)this.values[3][2];
/* 1081 */       paramMatrix4f.m33 = (float)this.values[3][3];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(GMatrix paramGMatrix) {
/*      */     int k;
/*      */     int m;
/* 1094 */     if (this.nCol < paramGMatrix.nCol) {
/* 1095 */       k = this.nCol;
/*      */     } else {
/* 1097 */       k = paramGMatrix.nCol;
/*      */     } 
/* 1099 */     if (this.nRow < paramGMatrix.nRow) {
/* 1100 */       m = this.nRow;
/*      */     } else {
/* 1102 */       m = paramGMatrix.nRow;
/*      */     }  int i;
/* 1104 */     for (i = 0; i < m; i++) {
/* 1105 */       for (byte b = 0; b < k; b++) {
/* 1106 */         paramGMatrix.values[i][b] = this.values[i][b];
/*      */       }
/*      */     } 
/* 1109 */     for (i = m; i < paramGMatrix.nRow; i++) {
/* 1110 */       for (byte b = 0; b < paramGMatrix.nCol; b++) {
/* 1111 */         paramGMatrix.values[i][b] = 0.0D;
/*      */       }
/*      */     } 
/* 1114 */     for (int j = k; j < paramGMatrix.nCol; j++) {
/* 1115 */       for (i = 0; i < m; i++) {
/* 1116 */         paramGMatrix.values[i][j] = 0.0D;
/*      */       }
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
/*      */   public final void setRow(int paramInt, double[] paramArrayOfdouble) {
/* 1130 */     for (byte b = 0; b < this.nCol; b++) {
/* 1131 */       this.values[paramInt][b] = paramArrayOfdouble[b];
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
/*      */   public final void setRow(int paramInt, GVector paramGVector) {
/* 1144 */     for (byte b = 0; b < this.nCol; b++) {
/* 1145 */       this.values[paramInt][b] = paramGVector.values[b];
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
/*      */   public final void setColumn(int paramInt, double[] paramArrayOfdouble) {
/* 1158 */     for (byte b = 0; b < this.nRow; b++) {
/* 1159 */       this.values[b][paramInt] = paramArrayOfdouble[b];
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
/*      */   public final void setColumn(int paramInt, GVector paramGVector) {
/* 1172 */     for (byte b = 0; b < this.nRow; b++) {
/* 1173 */       this.values[b][paramInt] = paramGVector.values[b];
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
/*      */   public final void mulTransposeBoth(GMatrix paramGMatrix1, GMatrix paramGMatrix2) {
/* 1188 */     if (paramGMatrix1.nRow != paramGMatrix2.nCol || this.nRow != paramGMatrix1.nCol || this.nCol != paramGMatrix2.nRow) {
/* 1189 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix14"));
/*      */     }
/*      */     
/* 1192 */     if (paramGMatrix1 == this || paramGMatrix2 == this) {
/* 1193 */       double[][] arrayOfDouble = new double[this.nRow][this.nCol];
/* 1194 */       for (byte b = 0; b < this.nRow; b++) {
/* 1195 */         for (byte b1 = 0; b1 < this.nCol; b1++) {
/* 1196 */           arrayOfDouble[b][b1] = 0.0D;
/* 1197 */           for (byte b2 = 0; b2 < paramGMatrix1.nRow; b2++) {
/* 1198 */             arrayOfDouble[b][b1] = arrayOfDouble[b][b1] + paramGMatrix1.values[b2][b] * paramGMatrix2.values[b1][b2];
/*      */           }
/*      */         } 
/*      */       } 
/* 1202 */       this.values = arrayOfDouble;
/*      */     } else {
/* 1204 */       for (byte b = 0; b < this.nRow; b++) {
/* 1205 */         for (byte b1 = 0; b1 < this.nCol; b1++) {
/* 1206 */           this.values[b][b1] = 0.0D;
/* 1207 */           for (byte b2 = 0; b2 < paramGMatrix1.nRow; b2++) {
/* 1208 */             this.values[b][b1] = this.values[b][b1] + paramGMatrix1.values[b2][b] * paramGMatrix2.values[b1][b2];
/*      */           }
/*      */         } 
/*      */       } 
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
/*      */   public final void mulTransposeRight(GMatrix paramGMatrix1, GMatrix paramGMatrix2) {
/* 1225 */     if (paramGMatrix1.nCol != paramGMatrix2.nCol || this.nCol != paramGMatrix2.nRow || this.nRow != paramGMatrix1.nRow) {
/* 1226 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix15"));
/*      */     }
/*      */     
/* 1229 */     if (paramGMatrix1 == this || paramGMatrix2 == this) {
/* 1230 */       double[][] arrayOfDouble = new double[this.nRow][this.nCol];
/* 1231 */       for (byte b = 0; b < this.nRow; b++) {
/* 1232 */         for (byte b1 = 0; b1 < this.nCol; b1++) {
/* 1233 */           arrayOfDouble[b][b1] = 0.0D;
/* 1234 */           for (byte b2 = 0; b2 < paramGMatrix1.nCol; b2++) {
/* 1235 */             arrayOfDouble[b][b1] = arrayOfDouble[b][b1] + paramGMatrix1.values[b][b2] * paramGMatrix2.values[b1][b2];
/*      */           }
/*      */         } 
/*      */       } 
/* 1239 */       this.values = arrayOfDouble;
/*      */     } else {
/* 1241 */       for (byte b = 0; b < this.nRow; b++) {
/* 1242 */         for (byte b1 = 0; b1 < this.nCol; b1++) {
/* 1243 */           this.values[b][b1] = 0.0D;
/* 1244 */           for (byte b2 = 0; b2 < paramGMatrix1.nCol; b2++) {
/* 1245 */             this.values[b][b1] = this.values[b][b1] + paramGMatrix1.values[b][b2] * paramGMatrix2.values[b1][b2];
/*      */           }
/*      */         } 
/*      */       } 
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
/*      */   
/*      */   public final void mulTransposeLeft(GMatrix paramGMatrix1, GMatrix paramGMatrix2) {
/* 1264 */     if (paramGMatrix1.nRow != paramGMatrix2.nRow || this.nCol != paramGMatrix2.nCol || this.nRow != paramGMatrix1.nCol) {
/* 1265 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix16"));
/*      */     }
/*      */     
/* 1268 */     if (paramGMatrix1 == this || paramGMatrix2 == this) {
/* 1269 */       double[][] arrayOfDouble = new double[this.nRow][this.nCol];
/* 1270 */       for (byte b = 0; b < this.nRow; b++) {
/* 1271 */         for (byte b1 = 0; b1 < this.nCol; b1++) {
/* 1272 */           arrayOfDouble[b][b1] = 0.0D;
/* 1273 */           for (byte b2 = 0; b2 < paramGMatrix1.nRow; b2++) {
/* 1274 */             arrayOfDouble[b][b1] = arrayOfDouble[b][b1] + paramGMatrix1.values[b2][b] * paramGMatrix2.values[b2][b1];
/*      */           }
/*      */         } 
/*      */       } 
/* 1278 */       this.values = arrayOfDouble;
/*      */     } else {
/* 1280 */       for (byte b = 0; b < this.nRow; b++) {
/* 1281 */         for (byte b1 = 0; b1 < this.nCol; b1++) {
/* 1282 */           this.values[b][b1] = 0.0D;
/* 1283 */           for (byte b2 = 0; b2 < paramGMatrix1.nRow; b2++) {
/* 1284 */             this.values[b][b1] = this.values[b][b1] + paramGMatrix1.values[b2][b] * paramGMatrix2.values[b2][b1];
/*      */           }
/*      */         } 
/*      */       } 
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
/*      */   public final void transpose() {
/* 1299 */     if (this.nRow != this.nCol) {
/*      */       
/* 1301 */       int i = this.nRow;
/* 1302 */       this.nRow = this.nCol;
/* 1303 */       this.nCol = i;
/* 1304 */       double[][] arrayOfDouble = new double[this.nRow][this.nCol];
/* 1305 */       for (i = 0; i < this.nRow; i++) {
/* 1306 */         for (byte b = 0; b < this.nCol; b++) {
/* 1307 */           arrayOfDouble[i][b] = this.values[b][i];
/*      */         }
/*      */       } 
/* 1310 */       this.values = arrayOfDouble;
/*      */     } else {
/*      */       
/* 1313 */       for (byte b = 0; b < this.nRow; b++) {
/* 1314 */         for (byte b1 = 0; b1 < b; b1++) {
/* 1315 */           double d = this.values[b][b1];
/* 1316 */           this.values[b][b1] = this.values[b1][b];
/* 1317 */           this.values[b1][b] = d;
/*      */         } 
/*      */       } 
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
/*      */   public final void transpose(GMatrix paramGMatrix) {
/* 1331 */     if (this.nRow != paramGMatrix.nCol || this.nCol != paramGMatrix.nRow) {
/* 1332 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix17"));
/*      */     }
/*      */     
/* 1335 */     if (paramGMatrix != this) {
/* 1336 */       for (byte b = 0; b < this.nRow; b++) {
/* 1337 */         for (byte b1 = 0; b1 < this.nCol; b1++) {
/* 1338 */           this.values[b][b1] = paramGMatrix.values[b1][b];
/*      */         }
/*      */       } 
/*      */     } else {
/* 1342 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1352 */     StringBuffer stringBuffer = new StringBuffer(this.nRow * this.nCol * 8);
/*      */ 
/*      */ 
/*      */     
/* 1356 */     for (byte b = 0; b < this.nRow; b++) {
/* 1357 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/* 1358 */         stringBuffer.append(this.values[b][b1]).append(" ");
/*      */       }
/* 1360 */       stringBuffer.append("\n");
/*      */     } 
/*      */     
/* 1363 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkMatrix(GMatrix paramGMatrix) {
/* 1370 */     for (byte b = 0; b < paramGMatrix.nRow; b++) {
/* 1371 */       for (byte b1 = 0; b1 < paramGMatrix.nCol; b1++) {
/* 1372 */         if (Math.abs(paramGMatrix.values[b][b1]) < 1.0E-10D) {
/* 1373 */           System.out.print(" 0.0     ");
/*      */         } else {
/* 1375 */           System.out.print(" " + paramGMatrix.values[b][b1]);
/*      */         } 
/*      */       } 
/* 1378 */       System.out.print("\n");
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
/*      */   public int hashCode() {
/* 1393 */     long l = 1L;
/*      */     
/* 1395 */     l = 31L * l + this.nRow;
/* 1396 */     l = 31L * l + this.nCol;
/*      */     
/* 1398 */     for (byte b = 0; b < this.nRow; b++) {
/* 1399 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/* 1400 */         l = 31L * l + VecMathUtil.doubleToLongBits(this.values[b][b1]);
/*      */       }
/*      */     } 
/*      */     
/* 1404 */     return (int)(l ^ l >> 32L);
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
/*      */   public boolean equals(GMatrix paramGMatrix) {
/*      */     try {
/* 1419 */       if (this.nRow != paramGMatrix.nRow || this.nCol != paramGMatrix.nCol) {
/* 1420 */         return false;
/*      */       }
/* 1422 */       for (byte b = 0; b < this.nRow; b++) {
/* 1423 */         for (byte b1 = 0; b1 < this.nCol; b1++) {
/* 1424 */           if (this.values[b][b1] != paramGMatrix.values[b][b1])
/* 1425 */             return false; 
/*      */         } 
/*      */       } 
/* 1428 */       return true;
/*      */     }
/* 1430 */     catch (NullPointerException nullPointerException) {
/* 1431 */       return false;
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
/*      */     try {
/* 1445 */       GMatrix gMatrix = (GMatrix)paramObject;
/*      */       
/* 1447 */       if (this.nRow != gMatrix.nRow || this.nCol != gMatrix.nCol) {
/* 1448 */         return false;
/*      */       }
/* 1450 */       for (byte b = 0; b < this.nRow; b++) {
/* 1451 */         for (byte b1 = 0; b1 < this.nCol; b1++) {
/* 1452 */           if (this.values[b][b1] != gMatrix.values[b][b1])
/* 1453 */             return false; 
/*      */         } 
/*      */       } 
/* 1456 */       return true;
/*      */     }
/* 1458 */     catch (ClassCastException classCastException) {
/* 1459 */       return false;
/*      */     }
/* 1461 */     catch (NullPointerException nullPointerException) {
/* 1462 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean epsilonEquals(GMatrix paramGMatrix, float paramFloat) {
/* 1470 */     return epsilonEquals(paramGMatrix, paramFloat);
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
/*      */   public boolean epsilonEquals(GMatrix paramGMatrix, double paramDouble) {
/* 1486 */     if (this.nRow != paramGMatrix.nRow || this.nCol != paramGMatrix.nCol) {
/* 1487 */       return false;
/*      */     }
/* 1489 */     for (byte b = 0; b < this.nRow; b++) {
/* 1490 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/* 1491 */         double d = this.values[b][b1] - paramGMatrix.values[b][b1];
/* 1492 */         if (((d < 0.0D) ? -d : d) > paramDouble)
/* 1493 */           return false; 
/*      */       } 
/*      */     } 
/* 1496 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double trace() {
/*      */     int i;
/* 1508 */     if (this.nRow < this.nCol) {
/* 1509 */       i = this.nRow;
/*      */     } else {
/* 1511 */       i = this.nCol;
/*      */     } 
/* 1513 */     double d = 0.0D;
/* 1514 */     for (byte b = 0; b < i; b++) {
/* 1515 */       d += this.values[b][b];
/*      */     }
/* 1517 */     return d;
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
/*      */   public final int SVD(GMatrix paramGMatrix1, GMatrix paramGMatrix2, GMatrix paramGMatrix3) {
/* 1539 */     if (this.nCol != paramGMatrix3.nCol || this.nCol != paramGMatrix3.nRow) {
/* 1540 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix18"));
/*      */     }
/*      */ 
/*      */     
/* 1544 */     if (this.nRow != paramGMatrix1.nRow || this.nRow != paramGMatrix1.nCol) {
/* 1545 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix25"));
/*      */     }
/*      */ 
/*      */     
/* 1549 */     if (this.nRow != paramGMatrix2.nRow || this.nCol != paramGMatrix2.nCol) {
/* 1550 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix26"));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1564 */     if (this.nRow == 2 && this.nCol == 2 && 
/* 1565 */       this.values[1][0] == 0.0D) {
/* 1566 */       paramGMatrix1.setIdentity();
/* 1567 */       paramGMatrix3.setIdentity();
/*      */       
/* 1569 */       if (this.values[0][1] == 0.0D) {
/* 1570 */         return 2;
/*      */       }
/*      */       
/* 1573 */       double[] arrayOfDouble1 = new double[1];
/* 1574 */       double[] arrayOfDouble2 = new double[1];
/* 1575 */       double[] arrayOfDouble3 = new double[1];
/* 1576 */       double[] arrayOfDouble4 = new double[1];
/* 1577 */       double[] arrayOfDouble5 = new double[2];
/*      */       
/* 1579 */       arrayOfDouble5[0] = this.values[0][0];
/* 1580 */       arrayOfDouble5[1] = this.values[1][1];
/*      */       
/* 1582 */       compute_2X2(this.values[0][0], this.values[0][1], this.values[1][1], arrayOfDouble5, arrayOfDouble1, arrayOfDouble3, arrayOfDouble2, arrayOfDouble4, 0);
/*      */ 
/*      */       
/* 1585 */       update_u(0, paramGMatrix1, arrayOfDouble3, arrayOfDouble1);
/* 1586 */       update_v(0, paramGMatrix3, arrayOfDouble4, arrayOfDouble2);
/*      */       
/* 1588 */       return 2;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1593 */     return computeSVD(this, paramGMatrix1, paramGMatrix2, paramGMatrix3);
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
/*      */   public final int LUD(GMatrix paramGMatrix, GVector paramGVector) {
/* 1617 */     int i = paramGMatrix.nRow * paramGMatrix.nCol;
/* 1618 */     double[] arrayOfDouble = new double[i];
/* 1619 */     int[] arrayOfInt1 = new int[1];
/* 1620 */     int[] arrayOfInt2 = new int[paramGMatrix.nRow];
/*      */ 
/*      */     
/* 1623 */     if (this.nRow != this.nCol) {
/* 1624 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix19"));
/*      */     }
/*      */ 
/*      */     
/* 1628 */     if (this.nRow != paramGMatrix.nRow) {
/* 1629 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix27"));
/*      */     }
/*      */ 
/*      */     
/* 1633 */     if (this.nCol != paramGMatrix.nCol) {
/* 1634 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix27"));
/*      */     }
/*      */ 
/*      */     
/* 1638 */     if (paramGMatrix.nRow != paramGVector.getSize()) {
/* 1639 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix20"));
/*      */     }
/*      */     
/*      */     byte b;
/* 1643 */     for (b = 0; b < this.nRow; b++) {
/* 1644 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/* 1645 */         arrayOfDouble[b * this.nCol + b1] = this.values[b][b1];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1650 */     if (!luDecomposition(paramGMatrix.nRow, arrayOfDouble, arrayOfInt2, arrayOfInt1))
/*      */     {
/* 1652 */       throw new SingularMatrixException(VecMathI18N.getString("GMatrix21"));
/*      */     }
/*      */ 
/*      */     
/* 1656 */     for (b = 0; b < this.nRow; b++) {
/* 1657 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/* 1658 */         paramGMatrix.values[b][b1] = arrayOfDouble[b * this.nCol + b1];
/*      */       }
/*      */     } 
/*      */     
/* 1662 */     for (b = 0; b < paramGMatrix.nRow; b++) {
/* 1663 */       paramGVector.values[b] = arrayOfInt2[b];
/*      */     }
/*      */     
/* 1666 */     return arrayOfInt1[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setScale(double paramDouble) {
/*      */     int i;
/* 1678 */     if (this.nRow < this.nCol) {
/* 1679 */       i = this.nRow;
/*      */     } else {
/* 1681 */       i = this.nCol;
/*      */     }  byte b;
/* 1683 */     for (b = 0; b < this.nRow; b++) {
/* 1684 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/* 1685 */         this.values[b][b1] = 0.0D;
/*      */       }
/*      */     } 
/*      */     
/* 1689 */     for (b = 0; b < i; b++) {
/* 1690 */       this.values[b][b] = paramDouble;
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
/*      */   final void invertGeneral(GMatrix paramGMatrix) {
/* 1703 */     int i = paramGMatrix.nRow * paramGMatrix.nCol;
/* 1704 */     double[] arrayOfDouble1 = new double[i];
/* 1705 */     double[] arrayOfDouble2 = new double[i];
/* 1706 */     int[] arrayOfInt1 = new int[paramGMatrix.nRow];
/* 1707 */     int[] arrayOfInt2 = new int[1];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1712 */     if (paramGMatrix.nRow != paramGMatrix.nCol)
/*      */     {
/* 1714 */       throw new MismatchedSizeException(VecMathI18N.getString("GMatrix22"));
/*      */     }
/*      */     
/*      */     byte b;
/*      */     
/* 1719 */     for (b = 0; b < this.nRow; b++) {
/* 1720 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/* 1721 */         arrayOfDouble1[b * this.nCol + b1] = paramGMatrix.values[b][b1];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1726 */     if (!luDecomposition(paramGMatrix.nRow, arrayOfDouble1, arrayOfInt1, arrayOfInt2))
/*      */     {
/* 1728 */       throw new SingularMatrixException(VecMathI18N.getString("GMatrix21"));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1733 */     for (b = 0; b < i; b++) {
/* 1734 */       arrayOfDouble2[b] = 0.0D;
/*      */     }
/* 1736 */     for (b = 0; b < this.nCol; b++) {
/* 1737 */       arrayOfDouble2[b + b * this.nCol] = 1.0D;
/*      */     }
/* 1739 */     luBacksubstitution(paramGMatrix.nRow, arrayOfDouble1, arrayOfInt1, arrayOfDouble2);
/*      */     
/* 1741 */     for (b = 0; b < this.nRow; b++) {
/* 1742 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/* 1743 */         this.values[b][b1] = arrayOfDouble2[b * this.nCol + b1];
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean luDecomposition(int paramInt, double[] paramArrayOfdouble, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/* 1768 */     double[] arrayOfDouble = new double[paramInt];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1775 */     byte b2 = 0;
/* 1776 */     byte b3 = 0;
/* 1777 */     paramArrayOfint2[0] = 1;
/*      */ 
/*      */     
/* 1780 */     int i = paramInt;
/* 1781 */     while (i-- != 0) {
/* 1782 */       double d = 0.0D;
/*      */ 
/*      */       
/* 1785 */       int j = paramInt;
/* 1786 */       while (j-- != 0) {
/* 1787 */         double d1 = paramArrayOfdouble[b2++];
/* 1788 */         d1 = Math.abs(d1);
/* 1789 */         if (d1 > d) {
/* 1790 */           d = d1;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1795 */       if (d == 0.0D) {
/* 1796 */         return false;
/*      */       }
/* 1798 */       arrayOfDouble[b3++] = 1.0D / d;
/*      */     } 
/*      */ 
/*      */     
/* 1802 */     byte b4 = 0;
/* 1803 */     for (byte b1 = 0; b1 < paramInt; b1++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1809 */       for (i = 0; i < b1; i++) {
/* 1810 */         int m = b4 + paramInt * i + b1;
/* 1811 */         double d1 = paramArrayOfdouble[m];
/* 1812 */         int k = i;
/* 1813 */         int n = b4 + paramInt * i;
/* 1814 */         int i1 = b4 + b1;
/* 1815 */         while (k-- != 0) {
/* 1816 */           d1 -= paramArrayOfdouble[n] * paramArrayOfdouble[i1];
/* 1817 */           n++;
/* 1818 */           i1 += paramInt;
/*      */         } 
/* 1820 */         paramArrayOfdouble[m] = d1;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1825 */       double d = 0.0D;
/* 1826 */       int j = -1;
/* 1827 */       for (i = b1; i < paramInt; i++) {
/* 1828 */         int m = b4 + paramInt * i + b1;
/* 1829 */         double d2 = paramArrayOfdouble[m];
/* 1830 */         int k = b1;
/* 1831 */         int n = b4 + paramInt * i;
/* 1832 */         int i1 = b4 + b1;
/* 1833 */         while (k-- != 0) {
/* 1834 */           d2 -= paramArrayOfdouble[n] * paramArrayOfdouble[i1];
/* 1835 */           n++;
/* 1836 */           i1 += paramInt;
/*      */         } 
/* 1838 */         paramArrayOfdouble[m] = d2;
/*      */         
/*      */         double d1;
/* 1841 */         if ((d1 = arrayOfDouble[i] * Math.abs(d2)) >= d) {
/* 1842 */           d = d1;
/* 1843 */           j = i;
/*      */         } 
/*      */       } 
/*      */       
/* 1847 */       if (j < 0) {
/* 1848 */         throw new RuntimeException(VecMathI18N.getString("GMatrix24"));
/*      */       }
/*      */ 
/*      */       
/* 1852 */       if (b1 != j) {
/*      */         
/* 1854 */         int k = paramInt;
/* 1855 */         int m = b4 + paramInt * j;
/* 1856 */         int n = b4 + paramInt * b1;
/* 1857 */         while (k-- != 0) {
/* 1858 */           double d1 = paramArrayOfdouble[m];
/* 1859 */           paramArrayOfdouble[m++] = paramArrayOfdouble[n];
/* 1860 */           paramArrayOfdouble[n++] = d1;
/*      */         } 
/*      */ 
/*      */         
/* 1864 */         arrayOfDouble[j] = arrayOfDouble[b1];
/* 1865 */         paramArrayOfint2[0] = -paramArrayOfint2[0];
/*      */       } 
/*      */ 
/*      */       
/* 1869 */       paramArrayOfint1[b1] = j;
/*      */ 
/*      */       
/* 1872 */       if (paramArrayOfdouble[b4 + paramInt * b1 + b1] == 0.0D) {
/* 1873 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1877 */       if (b1 != paramInt - 1) {
/* 1878 */         double d1 = 1.0D / paramArrayOfdouble[b4 + paramInt * b1 + b1];
/* 1879 */         int k = b4 + paramInt * (b1 + 1) + b1;
/* 1880 */         i = paramInt - 1 - b1;
/* 1881 */         while (i-- != 0) {
/* 1882 */           paramArrayOfdouble[k] = paramArrayOfdouble[k] * d1;
/* 1883 */           k += paramInt;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1889 */     return true;
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
/*      */   
/*      */   static void luBacksubstitution(int paramInt, double[] paramArrayOfdouble1, int[] paramArrayOfint, double[] paramArrayOfdouble2) {
/* 1920 */     byte b2 = 0;
/*      */ 
/*      */     
/* 1923 */     for (byte b1 = 0; b1 < paramInt; b1++) {
/*      */       
/* 1925 */       byte b4 = b1;
/* 1926 */       byte b = -1;
/*      */       
/*      */       byte b3;
/* 1929 */       for (b3 = 0; b3 < paramInt; b3++) {
/*      */ 
/*      */         
/* 1932 */         int i = paramArrayOfint[b2 + b3];
/* 1933 */         double d = paramArrayOfdouble2[b4 + paramInt * i];
/* 1934 */         paramArrayOfdouble2[b4 + paramInt * i] = paramArrayOfdouble2[b4 + paramInt * b3];
/* 1935 */         if (b >= 0) {
/*      */           
/* 1937 */           int j = b3 * paramInt;
/* 1938 */           for (byte b5 = b; b5 <= b3 - 1; b5++) {
/* 1939 */             d -= paramArrayOfdouble1[j + b5] * paramArrayOfdouble2[b4 + paramInt * b5];
/*      */           }
/*      */         }
/* 1942 */         else if (d != 0.0D) {
/* 1943 */           b = b3;
/*      */         } 
/* 1945 */         paramArrayOfdouble2[b4 + paramInt * b3] = d;
/*      */       } 
/*      */ 
/*      */       
/* 1949 */       for (b3 = 0; b3 < paramInt; b3++) {
/* 1950 */         int j = paramInt - 1 - b3;
/* 1951 */         int i = paramInt * j;
/* 1952 */         double d = 0.0D;
/* 1953 */         for (byte b5 = 1; b5 <= b3; b5++) {
/* 1954 */           d += paramArrayOfdouble1[i + paramInt - b5] * paramArrayOfdouble2[b4 + paramInt * (paramInt - b5)];
/*      */         }
/* 1956 */         paramArrayOfdouble2[b4 + paramInt * j] = (paramArrayOfdouble2[b4 + paramInt * j] - d) / paramArrayOfdouble1[i + j];
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int computeSVD(GMatrix paramGMatrix1, GMatrix paramGMatrix2, GMatrix paramGMatrix3, GMatrix paramGMatrix4) {
/*      */     int m, n, i1;
/* 1969 */     GMatrix gMatrix1 = new GMatrix(paramGMatrix1.nRow, paramGMatrix1.nCol);
/* 1970 */     GMatrix gMatrix2 = new GMatrix(paramGMatrix1.nRow, paramGMatrix1.nCol);
/* 1971 */     GMatrix gMatrix3 = new GMatrix(paramGMatrix1.nRow, paramGMatrix1.nCol);
/* 1972 */     GMatrix gMatrix4 = new GMatrix(paramGMatrix1);
/*      */ 
/*      */     
/* 1975 */     if (gMatrix4.nRow >= gMatrix4.nCol) {
/* 1976 */       n = gMatrix4.nCol;
/* 1977 */       m = gMatrix4.nCol - 1;
/*      */     } else {
/* 1979 */       n = gMatrix4.nRow;
/* 1980 */       m = gMatrix4.nRow;
/*      */     } 
/*      */     
/* 1983 */     if (gMatrix4.nRow > gMatrix4.nCol) {
/* 1984 */       i1 = gMatrix4.nRow;
/*      */     } else {
/* 1986 */       i1 = gMatrix4.nCol;
/*      */     } 
/* 1988 */     double[] arrayOfDouble1 = new double[i1];
/* 1989 */     double[] arrayOfDouble2 = new double[n];
/* 1990 */     double[] arrayOfDouble3 = new double[m];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1996 */     int k = 0;
/*      */     
/* 1998 */     paramGMatrix2.setIdentity();
/* 1999 */     paramGMatrix4.setIdentity();
/*      */     
/* 2001 */     int i = gMatrix4.nRow;
/* 2002 */     int j = gMatrix4.nCol;
/*      */ 
/*      */     
/* 2005 */     for (byte b2 = 0; b2 < n; b2++) {
/*      */ 
/*      */       
/* 2008 */       if (i > 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2015 */         double d1 = 0.0D; byte b3;
/* 2016 */         for (b3 = 0; b3 < i; b3++) {
/* 2017 */           d1 += gMatrix4.values[b3 + b2][b2] * gMatrix4.values[b3 + b2][b2];
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2024 */         d1 = Math.sqrt(d1);
/* 2025 */         if (gMatrix4.values[b2][b2] == 0.0D) {
/* 2026 */           arrayOfDouble1[0] = d1;
/*      */         } else {
/* 2028 */           arrayOfDouble1[0] = gMatrix4.values[b2][b2] + d_sign(d1, gMatrix4.values[b2][b2]);
/*      */         } 
/*      */         
/* 2031 */         for (b3 = 1; b3 < i; b3++) {
/* 2032 */           arrayOfDouble1[b3] = gMatrix4.values[b2 + b3][b2];
/*      */         }
/*      */         
/* 2035 */         double d2 = 0.0D;
/* 2036 */         for (b3 = 0; b3 < i; b3++)
/*      */         {
/*      */ 
/*      */           
/* 2040 */           d2 += arrayOfDouble1[b3] * arrayOfDouble1[b3];
/*      */         }
/*      */         
/* 2043 */         d2 = 2.0D / d2;
/*      */         
/*      */         byte b4;
/*      */         
/* 2047 */         for (b4 = b2; b4 < gMatrix4.nRow; b4++) {
/* 2048 */           for (byte b = b2; b < gMatrix4.nRow; b++) {
/* 2049 */             gMatrix2.values[b4][b] = -d2 * arrayOfDouble1[b4 - b2] * arrayOfDouble1[b - b2];
/*      */           }
/*      */         } 
/*      */         
/* 2053 */         for (b3 = b2; b3 < gMatrix4.nRow; b3++) {
/* 2054 */           gMatrix2.values[b3][b3] = gMatrix2.values[b3][b3] + 1.0D;
/*      */         }
/*      */ 
/*      */         
/* 2058 */         double d3 = 0.0D;
/* 2059 */         for (b3 = b2; b3 < gMatrix4.nRow; b3++) {
/* 2060 */           d3 += gMatrix2.values[b2][b3] * gMatrix4.values[b3][b2];
/*      */         }
/* 2062 */         gMatrix4.values[b2][b2] = d3;
/*      */ 
/*      */         
/* 2065 */         for (b4 = b2; b4 < gMatrix4.nRow; b4++) {
/* 2066 */           for (int i2 = b2 + 1; i2 < gMatrix4.nCol; i2++) {
/* 2067 */             gMatrix1.values[b4][i2] = 0.0D;
/* 2068 */             for (b3 = b2; b3 < gMatrix4.nCol; b3++) {
/* 2069 */               gMatrix1.values[b4][i2] = gMatrix1.values[b4][i2] + gMatrix2.values[b4][b3] * gMatrix4.values[b3][i2];
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 2074 */         for (b4 = b2; b4 < gMatrix4.nRow; b4++) {
/* 2075 */           for (int i2 = b2 + 1; i2 < gMatrix4.nCol; i2++) {
/* 2076 */             gMatrix4.values[b4][i2] = gMatrix1.values[b4][i2];
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2086 */         for (b4 = b2; b4 < gMatrix4.nRow; b4++) {
/* 2087 */           for (byte b = 0; b < gMatrix4.nCol; b++) {
/* 2088 */             gMatrix1.values[b4][b] = 0.0D;
/* 2089 */             for (b3 = b2; b3 < gMatrix4.nCol; b3++) {
/* 2090 */               gMatrix1.values[b4][b] = gMatrix1.values[b4][b] + gMatrix2.values[b4][b3] * paramGMatrix2.values[b3][b];
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 2095 */         for (b4 = b2; b4 < gMatrix4.nRow; b4++) {
/* 2096 */           for (byte b = 0; b < gMatrix4.nCol; b++) {
/* 2097 */             paramGMatrix2.values[b4][b] = gMatrix1.values[b4][b];
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2108 */         i--;
/*      */       } 
/*      */       
/* 2111 */       if (j > 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2117 */         double d1 = 0.0D; int i2;
/* 2118 */         for (i2 = 1; i2 < j; i2++) {
/* 2119 */           d1 += gMatrix4.values[b2][b2 + i2] * gMatrix4.values[b2][b2 + i2];
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2127 */         d1 = Math.sqrt(d1);
/* 2128 */         if (gMatrix4.values[b2][b2 + 1] == 0.0D) {
/* 2129 */           arrayOfDouble1[0] = d1;
/*      */         } else {
/* 2131 */           arrayOfDouble1[0] = gMatrix4.values[b2][b2 + 1] + d_sign(d1, gMatrix4.values[b2][b2 + 1]);
/*      */         } 
/*      */ 
/*      */         
/* 2135 */         for (i2 = 1; i2 < j - 1; i2++) {
/* 2136 */           arrayOfDouble1[i2] = gMatrix4.values[b2][b2 + i2 + 1];
/*      */         }
/*      */ 
/*      */         
/* 2140 */         double d2 = 0.0D;
/* 2141 */         for (i2 = 0; i2 < j - 1; i2++)
/*      */         {
/* 2143 */           d2 += arrayOfDouble1[i2] * arrayOfDouble1[i2];
/*      */         }
/*      */         
/* 2146 */         d2 = 2.0D / d2;
/*      */         
/*      */         int i3;
/*      */         
/* 2150 */         for (i3 = b2 + 1; i3 < j; i3++) {
/* 2151 */           for (int i4 = b2 + 1; i4 < gMatrix4.nCol; i4++) {
/* 2152 */             gMatrix3.values[i3][i4] = -d2 * arrayOfDouble1[i3 - b2 - 1] * arrayOfDouble1[i4 - b2 - 1];
/*      */           }
/*      */         } 
/*      */         
/* 2156 */         for (i2 = b2 + 1; i2 < gMatrix4.nCol; i2++) {
/* 2157 */           gMatrix3.values[i2][i2] = gMatrix3.values[i2][i2] + 1.0D;
/*      */         }
/*      */         
/* 2160 */         double d3 = 0.0D;
/* 2161 */         for (i2 = b2; i2 < gMatrix4.nCol; i2++) {
/* 2162 */           d3 += gMatrix3.values[i2][b2 + 1] * gMatrix4.values[b2][i2];
/*      */         }
/* 2164 */         gMatrix4.values[b2][b2 + 1] = d3;
/*      */ 
/*      */         
/* 2167 */         for (i3 = b2 + 1; i3 < gMatrix4.nRow; i3++) {
/* 2168 */           for (int i4 = b2 + 1; i4 < gMatrix4.nCol; i4++) {
/* 2169 */             gMatrix1.values[i3][i4] = 0.0D;
/* 2170 */             for (i2 = b2 + 1; i2 < gMatrix4.nCol; i2++) {
/* 2171 */               gMatrix1.values[i3][i4] = gMatrix1.values[i3][i4] + gMatrix3.values[i2][i4] * gMatrix4.values[i3][i2];
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 2176 */         for (i3 = b2 + 1; i3 < gMatrix4.nRow; i3++) {
/* 2177 */           for (int i4 = b2 + 1; i4 < gMatrix4.nCol; i4++) {
/* 2178 */             gMatrix4.values[i3][i4] = gMatrix1.values[i3][i4];
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2189 */         for (i3 = 0; i3 < gMatrix4.nRow; i3++) {
/* 2190 */           for (int i4 = b2 + 1; i4 < gMatrix4.nCol; i4++) {
/* 2191 */             gMatrix1.values[i3][i4] = 0.0D;
/* 2192 */             for (i2 = b2 + 1; i2 < gMatrix4.nCol; i2++) {
/* 2193 */               gMatrix1.values[i3][i4] = gMatrix1.values[i3][i4] + gMatrix3.values[i2][i4] * paramGMatrix4.values[i3][i2];
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2201 */         for (i3 = 0; i3 < gMatrix4.nRow; i3++) {
/* 2202 */           for (int i4 = b2 + 1; i4 < gMatrix4.nCol; i4++) {
/* 2203 */             paramGMatrix4.values[i3][i4] = gMatrix1.values[i3][i4];
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2212 */         j--;
/*      */       } 
/*      */     } 
/*      */     byte b1;
/* 2216 */     for (b1 = 0; b1 < n; b1++) {
/* 2217 */       arrayOfDouble2[b1] = gMatrix4.values[b1][b1];
/*      */     }
/*      */     
/* 2220 */     for (b1 = 0; b1 < m; b1++) {
/* 2221 */       arrayOfDouble3[b1] = gMatrix4.values[b1][b1 + 1];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2234 */     if (gMatrix4.nRow == 2 && gMatrix4.nCol == 2) {
/* 2235 */       double[] arrayOfDouble4 = new double[1];
/* 2236 */       double[] arrayOfDouble5 = new double[1];
/* 2237 */       double[] arrayOfDouble6 = new double[1];
/* 2238 */       double[] arrayOfDouble7 = new double[1];
/*      */       
/* 2240 */       compute_2X2(arrayOfDouble2[0], arrayOfDouble3[0], arrayOfDouble2[1], arrayOfDouble2, arrayOfDouble6, arrayOfDouble4, arrayOfDouble7, arrayOfDouble5, 0);
/*      */ 
/*      */       
/* 2243 */       update_u(0, paramGMatrix2, arrayOfDouble4, arrayOfDouble6);
/* 2244 */       update_v(0, paramGMatrix4, arrayOfDouble5, arrayOfDouble7);
/*      */       
/* 2246 */       return 2;
/*      */     } 
/*      */ 
/*      */     
/* 2250 */     compute_qr(0, arrayOfDouble3.length - 1, arrayOfDouble2, arrayOfDouble3, paramGMatrix2, paramGMatrix4);
/*      */ 
/*      */     
/* 2253 */     k = arrayOfDouble2.length;
/*      */ 
/*      */ 
/*      */     
/* 2257 */     return k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void compute_qr(int paramInt1, int paramInt2, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, GMatrix paramGMatrix1, GMatrix paramGMatrix2) {
/* 2266 */     double[] arrayOfDouble1 = new double[1];
/* 2267 */     double[] arrayOfDouble2 = new double[1];
/* 2268 */     double[] arrayOfDouble3 = new double[1];
/* 2269 */     double[] arrayOfDouble4 = new double[1];
/* 2270 */     GMatrix gMatrix = new GMatrix(paramGMatrix1.nCol, paramGMatrix2.nRow);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2297 */     double d3 = 1.0D;
/* 2298 */     double d4 = -1.0D;
/* 2299 */     boolean bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2304 */     double d1 = 0.0D;
/* 2305 */     double d2 = 0.0D;
/*      */     
/* 2307 */     for (byte b = 0; b < 2 && !bool; b++) {
/* 2308 */       int j; for (j = paramInt1; j <= paramInt2; j++) {
/*      */ 
/*      */         
/* 2311 */         if (j == paramInt1) {
/* 2312 */           int m; if (paramArrayOfdouble2.length == paramArrayOfdouble1.length) {
/* 2313 */             m = paramInt2;
/*      */           } else {
/* 2315 */             m = paramInt2 + 1;
/*      */           } 
/* 2317 */           double d5 = compute_shift(paramArrayOfdouble1[m - 1], paramArrayOfdouble2[paramInt2], paramArrayOfdouble1[m]);
/*      */           
/* 2319 */           d1 = (Math.abs(paramArrayOfdouble1[j]) - d5) * (d_sign(d3, paramArrayOfdouble1[j]) + d5 / paramArrayOfdouble1[j]);
/*      */           
/* 2321 */           d2 = paramArrayOfdouble2[j];
/*      */         } 
/*      */         
/* 2324 */         double d = compute_rot(d1, d2, arrayOfDouble4, arrayOfDouble2);
/* 2325 */         if (j != paramInt1) {
/* 2326 */           paramArrayOfdouble2[j - 1] = d;
/*      */         }
/* 2328 */         d1 = arrayOfDouble2[0] * paramArrayOfdouble1[j] + arrayOfDouble4[0] * paramArrayOfdouble2[j];
/* 2329 */         paramArrayOfdouble2[j] = arrayOfDouble2[0] * paramArrayOfdouble2[j] - arrayOfDouble4[0] * paramArrayOfdouble1[j];
/* 2330 */         d2 = arrayOfDouble4[0] * paramArrayOfdouble1[j + 1];
/* 2331 */         paramArrayOfdouble1[j + 1] = arrayOfDouble2[0] * paramArrayOfdouble1[j + 1];
/*      */ 
/*      */         
/* 2334 */         update_v(j, paramGMatrix2, arrayOfDouble2, arrayOfDouble4);
/*      */ 
/*      */ 
/*      */         
/* 2338 */         d = compute_rot(d1, d2, arrayOfDouble3, arrayOfDouble1);
/* 2339 */         paramArrayOfdouble1[j] = d;
/* 2340 */         d1 = arrayOfDouble1[0] * paramArrayOfdouble2[j] + arrayOfDouble3[0] * paramArrayOfdouble1[j + 1];
/* 2341 */         paramArrayOfdouble1[j + 1] = arrayOfDouble1[0] * paramArrayOfdouble1[j + 1] - arrayOfDouble3[0] * paramArrayOfdouble2[j];
/*      */         
/* 2343 */         if (j < paramInt2) {
/*      */           
/* 2345 */           d2 = arrayOfDouble3[0] * paramArrayOfdouble2[j + 1];
/* 2346 */           paramArrayOfdouble2[j + 1] = arrayOfDouble1[0] * paramArrayOfdouble2[j + 1];
/*      */         } 
/*      */ 
/*      */         
/* 2350 */         update_u(j, paramGMatrix1, arrayOfDouble1, arrayOfDouble3);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2356 */       if (paramArrayOfdouble1.length == paramArrayOfdouble2.length) {
/* 2357 */         double d = compute_rot(d1, d2, arrayOfDouble4, arrayOfDouble2);
/* 2358 */         d1 = arrayOfDouble2[0] * paramArrayOfdouble1[j] + arrayOfDouble4[0] * paramArrayOfdouble2[j];
/* 2359 */         paramArrayOfdouble2[j] = arrayOfDouble2[0] * paramArrayOfdouble2[j] - arrayOfDouble4[0] * paramArrayOfdouble1[j];
/* 2360 */         paramArrayOfdouble1[j + 1] = arrayOfDouble2[0] * paramArrayOfdouble1[j + 1];
/*      */         
/* 2362 */         update_v(j, paramGMatrix2, arrayOfDouble2, arrayOfDouble4);
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
/*      */       
/* 2375 */       while (paramInt2 - paramInt1 > 1 && Math.abs(paramArrayOfdouble2[paramInt2]) < 4.89E-15D) {
/* 2376 */         paramInt2--;
/*      */       }
/*      */ 
/*      */       
/* 2380 */       for (int k = paramInt2 - 2; k > paramInt1; k--) {
/* 2381 */         if (Math.abs(paramArrayOfdouble2[k]) < 4.89E-15D) {
/* 2382 */           compute_qr(k + 1, paramInt2, paramArrayOfdouble1, paramArrayOfdouble2, paramGMatrix1, paramGMatrix2);
/* 2383 */           paramInt2 = k - 1;
/*      */ 
/*      */           
/* 2386 */           while (paramInt2 - paramInt1 > 1 && Math.abs(paramArrayOfdouble2[paramInt2]) < 4.89E-15D)
/*      */           {
/* 2388 */             paramInt2--;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2396 */       if (paramInt2 - paramInt1 <= 1 && Math.abs(paramArrayOfdouble2[paramInt1 + 1]) < 4.89E-15D) {
/* 2397 */         bool = true;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2407 */     if (Math.abs(paramArrayOfdouble2[1]) < 4.89E-15D) {
/* 2408 */       compute_2X2(paramArrayOfdouble1[paramInt1], paramArrayOfdouble2[paramInt1], paramArrayOfdouble1[paramInt1 + 1], paramArrayOfdouble1, arrayOfDouble3, arrayOfDouble1, arrayOfDouble4, arrayOfDouble2, 0);
/*      */       
/* 2410 */       paramArrayOfdouble2[paramInt1] = 0.0D;
/* 2411 */       paramArrayOfdouble2[paramInt1 + 1] = 0.0D;
/*      */     } 
/*      */ 
/*      */     
/* 2415 */     int i = paramInt1;
/* 2416 */     update_u(i, paramGMatrix1, arrayOfDouble1, arrayOfDouble3);
/* 2417 */     update_v(i, paramGMatrix2, arrayOfDouble2, arrayOfDouble4);
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
/*      */   private static void print_se(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
/* 2429 */     System.out.println("\ns =" + paramArrayOfdouble1[0] + " " + paramArrayOfdouble1[1] + " " + paramArrayOfdouble1[2]);
/* 2430 */     System.out.println("e =" + paramArrayOfdouble2[0] + " " + paramArrayOfdouble2[1]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void update_v(int paramInt, GMatrix paramGMatrix, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
/* 2438 */     for (byte b = 0; b < paramGMatrix.nRow; b++) {
/* 2439 */       double d = paramGMatrix.values[b][paramInt];
/* 2440 */       paramGMatrix.values[b][paramInt] = paramArrayOfdouble1[0] * d + paramArrayOfdouble2[0] * paramGMatrix.values[b][paramInt + 1];
/*      */       
/* 2442 */       paramGMatrix.values[b][paramInt + 1] = -paramArrayOfdouble2[0] * d + paramArrayOfdouble1[0] * paramGMatrix.values[b][paramInt + 1];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void chase_up(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, int paramInt, GMatrix paramGMatrix) {
/* 2449 */     double[] arrayOfDouble1 = new double[1];
/* 2450 */     double[] arrayOfDouble2 = new double[1];
/*      */     
/* 2452 */     GMatrix gMatrix1 = new GMatrix(paramGMatrix.nRow, paramGMatrix.nCol);
/* 2453 */     GMatrix gMatrix2 = new GMatrix(paramGMatrix.nRow, paramGMatrix.nCol);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2465 */     double d1 = paramArrayOfdouble2[paramInt];
/* 2466 */     double d2 = paramArrayOfdouble1[paramInt];
/*      */     int i;
/* 2468 */     for (i = paramInt; i > 0; i--) {
/* 2469 */       double d = compute_rot(d1, d2, arrayOfDouble2, arrayOfDouble1);
/* 2470 */       d1 = -paramArrayOfdouble2[i - 1] * arrayOfDouble2[0];
/* 2471 */       d2 = paramArrayOfdouble1[i - 1];
/* 2472 */       paramArrayOfdouble1[i] = d;
/* 2473 */       paramArrayOfdouble2[i - 1] = paramArrayOfdouble2[i - 1] * arrayOfDouble1[0];
/* 2474 */       update_v_split(i, paramInt + 1, paramGMatrix, arrayOfDouble1, arrayOfDouble2, gMatrix1, gMatrix2);
/*      */     } 
/*      */     
/* 2477 */     paramArrayOfdouble1[i + 1] = compute_rot(d1, d2, arrayOfDouble2, arrayOfDouble1);
/* 2478 */     update_v_split(i, paramInt + 1, paramGMatrix, arrayOfDouble1, arrayOfDouble2, gMatrix1, gMatrix2);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void chase_across(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, int paramInt, GMatrix paramGMatrix) {
/* 2483 */     double[] arrayOfDouble1 = new double[1];
/* 2484 */     double[] arrayOfDouble2 = new double[1];
/*      */     
/* 2486 */     GMatrix gMatrix1 = new GMatrix(paramGMatrix.nRow, paramGMatrix.nCol);
/* 2487 */     GMatrix gMatrix2 = new GMatrix(paramGMatrix.nRow, paramGMatrix.nCol);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2499 */     double d2 = paramArrayOfdouble2[paramInt];
/* 2500 */     double d1 = paramArrayOfdouble1[paramInt + 1];
/*      */     int i;
/* 2502 */     for (i = paramInt; i < paramGMatrix.nCol - 2; i++) {
/* 2503 */       double d = compute_rot(d1, d2, arrayOfDouble2, arrayOfDouble1);
/* 2504 */       d2 = -paramArrayOfdouble2[i + 1] * arrayOfDouble2[0];
/* 2505 */       d1 = paramArrayOfdouble1[i + 2];
/* 2506 */       paramArrayOfdouble1[i + 1] = d;
/* 2507 */       paramArrayOfdouble2[i + 1] = paramArrayOfdouble2[i + 1] * arrayOfDouble1[0];
/* 2508 */       update_u_split(paramInt, i + 1, paramGMatrix, arrayOfDouble1, arrayOfDouble2, gMatrix1, gMatrix2);
/*      */     } 
/*      */     
/* 2511 */     paramArrayOfdouble1[i + 1] = compute_rot(d1, d2, arrayOfDouble2, arrayOfDouble1);
/* 2512 */     update_u_split(paramInt, i + 1, paramGMatrix, arrayOfDouble1, arrayOfDouble2, gMatrix1, gMatrix2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void update_v_split(int paramInt1, int paramInt2, GMatrix paramGMatrix1, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, GMatrix paramGMatrix2, GMatrix paramGMatrix3) {
/* 2521 */     for (byte b = 0; b < paramGMatrix1.nRow; b++) {
/* 2522 */       double d = paramGMatrix1.values[b][paramInt1];
/* 2523 */       paramGMatrix1.values[b][paramInt1] = paramArrayOfdouble1[0] * d - paramArrayOfdouble2[0] * paramGMatrix1.values[b][paramInt2];
/* 2524 */       paramGMatrix1.values[b][paramInt2] = paramArrayOfdouble2[0] * d + paramArrayOfdouble1[0] * paramGMatrix1.values[b][paramInt2];
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2538 */     System.out.println("topr    =" + paramInt1);
/* 2539 */     System.out.println("bottomr =" + paramInt2);
/* 2540 */     System.out.println("cosr =" + paramArrayOfdouble1[0]);
/* 2541 */     System.out.println("sinr =" + paramArrayOfdouble2[0]);
/* 2542 */     System.out.println("\nm =");
/* 2543 */     checkMatrix(paramGMatrix3);
/* 2544 */     System.out.println("\nv =");
/* 2545 */     checkMatrix(paramGMatrix2);
/* 2546 */     paramGMatrix3.mul(paramGMatrix3, paramGMatrix2);
/* 2547 */     System.out.println("\nt*m =");
/* 2548 */     checkMatrix(paramGMatrix3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void update_u_split(int paramInt1, int paramInt2, GMatrix paramGMatrix1, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, GMatrix paramGMatrix2, GMatrix paramGMatrix3) {
/* 2557 */     for (byte b = 0; b < paramGMatrix1.nCol; b++) {
/* 2558 */       double d = paramGMatrix1.values[paramInt1][b];
/* 2559 */       paramGMatrix1.values[paramInt1][b] = paramArrayOfdouble1[0] * d - paramArrayOfdouble2[0] * paramGMatrix1.values[paramInt2][b];
/* 2560 */       paramGMatrix1.values[paramInt2][b] = paramArrayOfdouble2[0] * d + paramArrayOfdouble1[0] * paramGMatrix1.values[paramInt2][b];
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2573 */     System.out.println("\nm=");
/* 2574 */     checkMatrix(paramGMatrix3);
/* 2575 */     System.out.println("\nu=");
/* 2576 */     checkMatrix(paramGMatrix2);
/* 2577 */     paramGMatrix3.mul(paramGMatrix2, paramGMatrix3);
/* 2578 */     System.out.println("\nt*m=");
/* 2579 */     checkMatrix(paramGMatrix3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void update_u(int paramInt, GMatrix paramGMatrix, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
/* 2587 */     for (byte b = 0; b < paramGMatrix.nCol; b++) {
/* 2588 */       double d = paramGMatrix.values[paramInt][b];
/* 2589 */       paramGMatrix.values[paramInt][b] = paramArrayOfdouble1[0] * d + paramArrayOfdouble2[0] * paramGMatrix.values[paramInt + 1][b];
/*      */       
/* 2591 */       paramGMatrix.values[paramInt + 1][b] = -paramArrayOfdouble2[0] * d + paramArrayOfdouble1[0] * paramGMatrix.values[paramInt + 1][b];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void print_m(GMatrix paramGMatrix1, GMatrix paramGMatrix2, GMatrix paramGMatrix3) {
/* 2597 */     GMatrix gMatrix = new GMatrix(paramGMatrix1.nCol, paramGMatrix1.nRow);
/*      */     
/* 2599 */     gMatrix.mul(paramGMatrix2, gMatrix);
/* 2600 */     gMatrix.mul(gMatrix, paramGMatrix3);
/* 2601 */     System.out.println("\n m = \n" + toString(gMatrix));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String toString(GMatrix paramGMatrix) {
/* 2607 */     StringBuffer stringBuffer = new StringBuffer(paramGMatrix.nRow * paramGMatrix.nCol * 8);
/*      */ 
/*      */     
/* 2610 */     for (byte b = 0; b < paramGMatrix.nRow; b++) {
/* 2611 */       for (byte b1 = 0; b1 < paramGMatrix.nCol; b1++) {
/* 2612 */         if (Math.abs(paramGMatrix.values[b][b1]) < 1.0E-9D) {
/* 2613 */           stringBuffer.append("0.0000 ");
/*      */         } else {
/* 2615 */           stringBuffer.append(paramGMatrix.values[b][b1]).append(" ");
/*      */         } 
/*      */       } 
/* 2618 */       stringBuffer.append("\n");
/*      */     } 
/* 2620 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void print_svd(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, GMatrix paramGMatrix1, GMatrix paramGMatrix2) {
/* 2626 */     GMatrix gMatrix = new GMatrix(paramGMatrix1.nCol, paramGMatrix2.nRow);
/*      */     
/* 2628 */     System.out.println(" \ns = "); byte b;
/* 2629 */     for (b = 0; b < paramArrayOfdouble1.length; b++) {
/* 2630 */       System.out.println(" " + paramArrayOfdouble1[b]);
/*      */     }
/*      */     
/* 2633 */     System.out.println(" \ne = ");
/* 2634 */     for (b = 0; b < paramArrayOfdouble2.length; b++) {
/* 2635 */       System.out.println(" " + paramArrayOfdouble2[b]);
/*      */     }
/*      */     
/* 2638 */     System.out.println(" \nu  = \n" + paramGMatrix1.toString());
/* 2639 */     System.out.println(" \nv  = \n" + paramGMatrix2.toString());
/*      */     
/* 2641 */     gMatrix.setIdentity();
/* 2642 */     for (b = 0; b < paramArrayOfdouble1.length; b++) {
/* 2643 */       gMatrix.values[b][b] = paramArrayOfdouble1[b];
/*      */     }
/* 2645 */     for (b = 0; b < paramArrayOfdouble2.length; b++) {
/* 2646 */       gMatrix.values[b][b + 1] = paramArrayOfdouble2[b];
/*      */     }
/* 2648 */     System.out.println(" \nm  = \n" + gMatrix.toString());
/*      */     
/* 2650 */     gMatrix.mulTransposeLeft(paramGMatrix1, gMatrix);
/* 2651 */     gMatrix.mulTransposeRight(gMatrix, paramGMatrix2);
/*      */     
/* 2653 */     System.out.println(" \n u.transpose*m*v.transpose  = \n" + gMatrix.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   static double max(double paramDouble1, double paramDouble2) {
/* 2658 */     if (paramDouble1 > paramDouble2) {
/* 2659 */       return paramDouble1;
/*      */     }
/* 2661 */     return paramDouble2;
/*      */   }
/*      */   
/*      */   static double min(double paramDouble1, double paramDouble2) {
/* 2665 */     if (paramDouble1 < paramDouble2) {
/* 2666 */       return paramDouble1;
/*      */     }
/* 2668 */     return paramDouble2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static double compute_shift(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 2676 */     double d6, d3 = Math.abs(paramDouble1);
/* 2677 */     double d4 = Math.abs(paramDouble2);
/* 2678 */     double d5 = Math.abs(paramDouble3);
/* 2679 */     double d1 = min(d3, d5);
/* 2680 */     double d2 = max(d3, d5);
/*      */     
/* 2682 */     if (d1 == 0.0D) {
/* 2683 */       d6 = 0.0D;
/* 2684 */       if (d2 != 0.0D)
/*      */       {
/* 2686 */         double d = min(d2, d4) / max(d2, d4);
/*      */       }
/*      */     }
/* 2689 */     else if (d4 < d2) {
/* 2690 */       double d9 = d1 / d2 + 1.0D;
/* 2691 */       double d10 = (d2 - d1) / d2;
/* 2692 */       double d7 = d4 / d2;
/* 2693 */       double d11 = d7 * d7;
/* 2694 */       double d8 = 2.0D / (Math.sqrt(d9 * d9 + d11) + Math.sqrt(d10 * d10 + d11));
/* 2695 */       d6 = d1 * d8;
/*      */     } else {
/* 2697 */       double d = d2 / d4;
/* 2698 */       if (d == 0.0D) {
/* 2699 */         d6 = d1 * d2 / d4;
/*      */       } else {
/* 2701 */         double d10 = d1 / d2 + 1.0D;
/* 2702 */         double d11 = (d2 - d1) / d2;
/* 2703 */         double d7 = d10 * d;
/* 2704 */         double d8 = d11 * d;
/* 2705 */         double d9 = 1.0D / (Math.sqrt(d7 * d7 + 1.0D) + Math.sqrt(d8 * d8 + 1.0D));
/*      */         
/* 2707 */         d6 = d1 * d9 * d;
/* 2708 */         d6 += d6;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2713 */     return d6;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int compute_2X2(double paramDouble1, double paramDouble2, double paramDouble3, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, double[] paramArrayOfdouble3, double[] paramArrayOfdouble4, double[] paramArrayOfdouble5, int paramInt) {
/*      */     boolean bool;
/* 2720 */     double d1 = 2.0D;
/* 2721 */     double d2 = 1.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2733 */     double d15 = paramArrayOfdouble1[0];
/* 2734 */     double d14 = paramArrayOfdouble1[1];
/* 2735 */     double d10 = 0.0D;
/* 2736 */     double d11 = 0.0D;
/* 2737 */     double d12 = 0.0D;
/* 2738 */     double d13 = 0.0D;
/* 2739 */     double d3 = 0.0D;
/*      */     
/* 2741 */     double d7 = paramDouble1;
/* 2742 */     double d4 = Math.abs(d7);
/* 2743 */     double d9 = paramDouble3;
/* 2744 */     double d6 = Math.abs(paramDouble3);
/*      */     
/* 2746 */     byte b = 1;
/* 2747 */     if (d6 > d4) {
/* 2748 */       bool = true;
/*      */     } else {
/* 2750 */       bool = false;
/*      */     } 
/* 2752 */     if (bool) {
/* 2753 */       b = 3;
/* 2754 */       double d = d7;
/* 2755 */       d7 = d9;
/* 2756 */       d9 = d;
/* 2757 */       d = d4;
/* 2758 */       d4 = d6;
/* 2759 */       d6 = d;
/*      */     } 
/*      */ 
/*      */     
/* 2763 */     double d8 = paramDouble2;
/* 2764 */     double d5 = Math.abs(d8);
/* 2765 */     if (d5 == 0.0D) {
/* 2766 */       paramArrayOfdouble1[1] = d6;
/* 2767 */       paramArrayOfdouble1[0] = d4;
/* 2768 */       d10 = 1.0D;
/* 2769 */       d11 = 1.0D;
/* 2770 */       d12 = 0.0D;
/* 2771 */       d13 = 0.0D;
/*      */     } else {
/* 2773 */       boolean bool1 = true;
/* 2774 */       if (d5 > d4) {
/* 2775 */         b = 2;
/* 2776 */         if (d4 / d5 < 1.0E-10D) {
/* 2777 */           bool1 = false;
/* 2778 */           d15 = d5;
/*      */           
/* 2780 */           if (d6 > 1.0D) {
/* 2781 */             d14 = d4 / d5 / d6;
/*      */           } else {
/* 2783 */             d14 = d4 / d5 * d6;
/*      */           } 
/* 2785 */           d10 = 1.0D;
/* 2786 */           d12 = d9 / d8;
/* 2787 */           d13 = 1.0D;
/* 2788 */           d11 = d7 / d8;
/*      */         } 
/*      */       } 
/* 2791 */       if (bool1) {
/* 2792 */         double d18, d20, d17 = d4 - d6;
/* 2793 */         if (d17 == d4) {
/*      */           
/* 2795 */           d18 = 1.0D;
/*      */         } else {
/* 2797 */           d18 = d17 / d4;
/*      */         } 
/*      */         
/* 2800 */         double d19 = d8 / d7;
/* 2801 */         double d22 = 2.0D - d18;
/* 2802 */         double d23 = d19 * d19;
/* 2803 */         double d24 = d22 * d22;
/* 2804 */         double d21 = Math.sqrt(d24 + d23);
/*      */         
/* 2806 */         if (d18 == 0.0D) {
/* 2807 */           d20 = Math.abs(d19);
/*      */         } else {
/* 2809 */           d20 = Math.sqrt(d18 * d18 + d23);
/*      */         } 
/*      */         
/* 2812 */         double d16 = (d21 + d20) * 0.5D;
/* 2813 */         if (d5 > d4) {
/* 2814 */           b = 2;
/* 2815 */           if (d4 / d5 < 1.0E-10D) {
/* 2816 */             bool1 = false;
/* 2817 */             d15 = d5;
/* 2818 */             if (d6 > 1.0D) {
/* 2819 */               d14 = d4 / d5 / d6;
/*      */             } else {
/* 2821 */               d14 = d4 / d5 * d6;
/*      */             } 
/* 2823 */             d10 = 1.0D;
/* 2824 */             d12 = d9 / d8;
/* 2825 */             d13 = 1.0D;
/* 2826 */             d11 = d7 / d8;
/*      */           } 
/*      */         } 
/* 2829 */         if (bool1) {
/* 2830 */           d17 = d4 - d6;
/* 2831 */           if (d17 == d4) {
/* 2832 */             d18 = 1.0D;
/*      */           } else {
/* 2834 */             d18 = d17 / d4;
/*      */           } 
/*      */           
/* 2837 */           d19 = d8 / d7;
/* 2838 */           d22 = 2.0D - d18;
/*      */           
/* 2840 */           d23 = d19 * d19;
/* 2841 */           d24 = d22 * d22;
/* 2842 */           d21 = Math.sqrt(d24 + d23);
/*      */           
/* 2844 */           if (d18 == 0.0D) {
/* 2845 */             d20 = Math.abs(d19);
/*      */           } else {
/* 2847 */             d20 = Math.sqrt(d18 * d18 + d23);
/*      */           } 
/*      */           
/* 2850 */           d16 = (d21 + d20) * 0.5D;
/* 2851 */           d14 = d6 / d16;
/* 2852 */           d15 = d4 * d16;
/*      */           
/* 2854 */           if (d23 == 0.0D) {
/* 2855 */             if (d18 == 0.0D) {
/* 2856 */               d22 = d_sign(d1, d7) * d_sign(d2, d8);
/*      */             } else {
/* 2858 */               d22 = d8 / d_sign(d17, d7) + d19 / d22;
/*      */             } 
/*      */           } else {
/* 2861 */             d22 = (d19 / (d21 + d22) + d19 / (d20 + d18)) * (d16 + 1.0D);
/*      */           } 
/*      */           
/* 2864 */           d18 = Math.sqrt(d22 * d22 + 4.0D);
/* 2865 */           d11 = 2.0D / d18;
/* 2866 */           d13 = d22 / d18;
/* 2867 */           d10 = (d11 + d13 * d19) / d16;
/* 2868 */           d12 = d9 / d7 * d13 / d16;
/*      */         } 
/*      */       } 
/* 2871 */       if (bool) {
/* 2872 */         paramArrayOfdouble3[0] = d13;
/* 2873 */         paramArrayOfdouble2[0] = d11;
/* 2874 */         paramArrayOfdouble5[0] = d12;
/* 2875 */         paramArrayOfdouble4[0] = d10;
/*      */       } else {
/* 2877 */         paramArrayOfdouble3[0] = d10;
/* 2878 */         paramArrayOfdouble2[0] = d12;
/* 2879 */         paramArrayOfdouble5[0] = d11;
/* 2880 */         paramArrayOfdouble4[0] = d13;
/*      */       } 
/*      */       
/* 2883 */       if (b == 1) {
/* 2884 */         d3 = d_sign(d2, paramArrayOfdouble5[0]) * d_sign(d2, paramArrayOfdouble3[0]) * d_sign(d2, paramDouble1);
/*      */       }
/*      */       
/* 2887 */       if (b == 2) {
/* 2888 */         d3 = d_sign(d2, paramArrayOfdouble4[0]) * d_sign(d2, paramArrayOfdouble3[0]) * d_sign(d2, paramDouble2);
/*      */       }
/*      */       
/* 2891 */       if (b == 3) {
/* 2892 */         d3 = d_sign(d2, paramArrayOfdouble4[0]) * d_sign(d2, paramArrayOfdouble2[0]) * d_sign(d2, paramDouble3);
/*      */       }
/*      */ 
/*      */       
/* 2896 */       paramArrayOfdouble1[paramInt] = d_sign(d15, d3);
/* 2897 */       double d = d3 * d_sign(d2, paramDouble1) * d_sign(d2, paramDouble3);
/* 2898 */       paramArrayOfdouble1[paramInt + 1] = d_sign(d14, d);
/*      */     } 
/*      */     
/* 2901 */     return 0;
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
/*      */   static double compute_rot(double paramDouble1, double paramDouble2, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
/*      */     double d1, d2, d3;
/* 2916 */     if (paramDouble2 == 0.0D) {
/* 2917 */       d1 = 1.0D;
/* 2918 */       d2 = 0.0D;
/* 2919 */       d3 = paramDouble1;
/* 2920 */     } else if (paramDouble1 == 0.0D) {
/* 2921 */       d1 = 0.0D;
/* 2922 */       d2 = 1.0D;
/* 2923 */       d3 = paramDouble2;
/*      */     } else {
/* 2925 */       double d5 = paramDouble1;
/* 2926 */       double d6 = paramDouble2;
/* 2927 */       double d4 = max(Math.abs(d5), Math.abs(d6));
/* 2928 */       if (d4 >= 4.994797680505588E145D) {
/* 2929 */         byte b3 = 0;
/* 2930 */         while (d4 >= 4.994797680505588E145D) {
/* 2931 */           b3++;
/* 2932 */           d5 *= 2.002083095183101E-146D;
/* 2933 */           d6 *= 2.002083095183101E-146D;
/* 2934 */           d4 = max(Math.abs(d5), Math.abs(d6));
/*      */         } 
/* 2936 */         d3 = Math.sqrt(d5 * d5 + d6 * d6);
/* 2937 */         d1 = d5 / d3;
/* 2938 */         d2 = d6 / d3;
/* 2939 */         byte b1 = b3;
/* 2940 */         for (byte b2 = 1; b2 <= b3; b2++) {
/* 2941 */           d3 *= 4.994797680505588E145D;
/*      */         }
/* 2943 */       } else if (d4 <= 2.002083095183101E-146D) {
/* 2944 */         byte b3 = 0;
/* 2945 */         while (d4 <= 2.002083095183101E-146D) {
/* 2946 */           b3++;
/* 2947 */           d5 *= 4.994797680505588E145D;
/* 2948 */           d6 *= 4.994797680505588E145D;
/* 2949 */           d4 = max(Math.abs(d5), Math.abs(d6));
/*      */         } 
/* 2951 */         d3 = Math.sqrt(d5 * d5 + d6 * d6);
/* 2952 */         d1 = d5 / d3;
/* 2953 */         d2 = d6 / d3;
/* 2954 */         byte b1 = b3;
/* 2955 */         for (byte b2 = 1; b2 <= b3; b2++) {
/* 2956 */           d3 *= 2.002083095183101E-146D;
/*      */         }
/*      */       } else {
/* 2959 */         d3 = Math.sqrt(d5 * d5 + d6 * d6);
/* 2960 */         d1 = d5 / d3;
/* 2961 */         d2 = d6 / d3;
/*      */       } 
/* 2963 */       if (Math.abs(paramDouble1) > Math.abs(paramDouble2) && d1 < 0.0D) {
/* 2964 */         d1 = -d1;
/* 2965 */         d2 = -d2;
/* 2966 */         d3 = -d3;
/*      */       } 
/*      */     } 
/* 2969 */     paramArrayOfdouble1[0] = d2;
/* 2970 */     paramArrayOfdouble2[0] = d1;
/* 2971 */     return d3;
/*      */   }
/*      */ 
/*      */   
/*      */   static double d_sign(double paramDouble1, double paramDouble2) {
/* 2976 */     double d = (paramDouble1 >= 0.0D) ? paramDouble1 : -paramDouble1;
/* 2977 */     return (paramDouble2 >= 0.0D) ? d : -d;
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
/* 2989 */     GMatrix gMatrix = null;
/*      */     try {
/* 2991 */       gMatrix = (GMatrix)super.clone();
/* 2992 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */       
/* 2994 */       throw new InternalError();
/*      */     } 
/*      */ 
/*      */     
/* 2998 */     gMatrix.values = new double[this.nRow][this.nCol];
/* 2999 */     for (byte b = 0; b < this.nRow; b++) {
/* 3000 */       for (byte b1 = 0; b1 < this.nCol; b1++) {
/* 3001 */         gMatrix.values[b][b1] = this.values[b][b1];
/*      */       }
/*      */     } 
/*      */     
/* 3005 */     return gMatrix;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\GMatrix.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
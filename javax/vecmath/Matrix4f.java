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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Matrix4f
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   static final long serialVersionUID = -8405036035410109353L;
/*      */   public float m00;
/*      */   public float m01;
/*      */   public float m02;
/*      */   public float m03;
/*      */   public float m10;
/*      */   public float m11;
/*      */   public float m12;
/*      */   public float m13;
/*      */   public float m20;
/*      */   public float m21;
/*      */   public float m22;
/*      */   public float m23;
/*      */   public float m30;
/*      */   public float m31;
/*      */   public float m32;
/*      */   public float m33;
/*      */   private static final double EPS = 1.0E-8D;
/*      */   
/*      */   public Matrix4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16) {
/*  156 */     this.m00 = paramFloat1;
/*  157 */     this.m01 = paramFloat2;
/*  158 */     this.m02 = paramFloat3;
/*  159 */     this.m03 = paramFloat4;
/*      */     
/*  161 */     this.m10 = paramFloat5;
/*  162 */     this.m11 = paramFloat6;
/*  163 */     this.m12 = paramFloat7;
/*  164 */     this.m13 = paramFloat8;
/*      */     
/*  166 */     this.m20 = paramFloat9;
/*  167 */     this.m21 = paramFloat10;
/*  168 */     this.m22 = paramFloat11;
/*  169 */     this.m23 = paramFloat12;
/*      */     
/*  171 */     this.m30 = paramFloat13;
/*  172 */     this.m31 = paramFloat14;
/*  173 */     this.m32 = paramFloat15;
/*  174 */     this.m33 = paramFloat16;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4f(float[] paramArrayOffloat) {
/*  185 */     this.m00 = paramArrayOffloat[0];
/*  186 */     this.m01 = paramArrayOffloat[1];
/*  187 */     this.m02 = paramArrayOffloat[2];
/*  188 */     this.m03 = paramArrayOffloat[3];
/*      */     
/*  190 */     this.m10 = paramArrayOffloat[4];
/*  191 */     this.m11 = paramArrayOffloat[5];
/*  192 */     this.m12 = paramArrayOffloat[6];
/*  193 */     this.m13 = paramArrayOffloat[7];
/*      */     
/*  195 */     this.m20 = paramArrayOffloat[8];
/*  196 */     this.m21 = paramArrayOffloat[9];
/*  197 */     this.m22 = paramArrayOffloat[10];
/*  198 */     this.m23 = paramArrayOffloat[11];
/*      */     
/*  200 */     this.m30 = paramArrayOffloat[12];
/*  201 */     this.m31 = paramArrayOffloat[13];
/*  202 */     this.m32 = paramArrayOffloat[14];
/*  203 */     this.m33 = paramArrayOffloat[15];
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
/*      */   public Matrix4f(Quat4f paramQuat4f, Vector3f paramVector3f, float paramFloat) {
/*  218 */     this.m00 = (float)(paramFloat * (1.0D - 2.0D * paramQuat4f.y * paramQuat4f.y - 2.0D * paramQuat4f.z * paramQuat4f.z));
/*  219 */     this.m10 = (float)(paramFloat * 2.0D * (paramQuat4f.x * paramQuat4f.y + paramQuat4f.w * paramQuat4f.z));
/*  220 */     this.m20 = (float)(paramFloat * 2.0D * (paramQuat4f.x * paramQuat4f.z - paramQuat4f.w * paramQuat4f.y));
/*      */     
/*  222 */     this.m01 = (float)(paramFloat * 2.0D * (paramQuat4f.x * paramQuat4f.y - paramQuat4f.w * paramQuat4f.z));
/*  223 */     this.m11 = (float)(paramFloat * (1.0D - 2.0D * paramQuat4f.x * paramQuat4f.x - 2.0D * paramQuat4f.z * paramQuat4f.z));
/*  224 */     this.m21 = (float)(paramFloat * 2.0D * (paramQuat4f.y * paramQuat4f.z + paramQuat4f.w * paramQuat4f.x));
/*      */     
/*  226 */     this.m02 = (float)(paramFloat * 2.0D * (paramQuat4f.x * paramQuat4f.z + paramQuat4f.w * paramQuat4f.y));
/*  227 */     this.m12 = (float)(paramFloat * 2.0D * (paramQuat4f.y * paramQuat4f.z - paramQuat4f.w * paramQuat4f.x));
/*  228 */     this.m22 = (float)(paramFloat * (1.0D - 2.0D * paramQuat4f.x * paramQuat4f.x - 2.0D * paramQuat4f.y * paramQuat4f.y));
/*      */     
/*  230 */     this.m03 = paramVector3f.x;
/*  231 */     this.m13 = paramVector3f.y;
/*  232 */     this.m23 = paramVector3f.z;
/*      */     
/*  234 */     this.m30 = 0.0F;
/*  235 */     this.m31 = 0.0F;
/*  236 */     this.m32 = 0.0F;
/*  237 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4f(Matrix4d paramMatrix4d) {
/*  248 */     this.m00 = (float)paramMatrix4d.m00;
/*  249 */     this.m01 = (float)paramMatrix4d.m01;
/*  250 */     this.m02 = (float)paramMatrix4d.m02;
/*  251 */     this.m03 = (float)paramMatrix4d.m03;
/*      */     
/*  253 */     this.m10 = (float)paramMatrix4d.m10;
/*  254 */     this.m11 = (float)paramMatrix4d.m11;
/*  255 */     this.m12 = (float)paramMatrix4d.m12;
/*  256 */     this.m13 = (float)paramMatrix4d.m13;
/*      */     
/*  258 */     this.m20 = (float)paramMatrix4d.m20;
/*  259 */     this.m21 = (float)paramMatrix4d.m21;
/*  260 */     this.m22 = (float)paramMatrix4d.m22;
/*  261 */     this.m23 = (float)paramMatrix4d.m23;
/*      */     
/*  263 */     this.m30 = (float)paramMatrix4d.m30;
/*  264 */     this.m31 = (float)paramMatrix4d.m31;
/*  265 */     this.m32 = (float)paramMatrix4d.m32;
/*  266 */     this.m33 = (float)paramMatrix4d.m33;
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
/*      */   public Matrix4f(Matrix4f paramMatrix4f) {
/*  278 */     this.m00 = paramMatrix4f.m00;
/*  279 */     this.m01 = paramMatrix4f.m01;
/*  280 */     this.m02 = paramMatrix4f.m02;
/*  281 */     this.m03 = paramMatrix4f.m03;
/*      */     
/*  283 */     this.m10 = paramMatrix4f.m10;
/*  284 */     this.m11 = paramMatrix4f.m11;
/*  285 */     this.m12 = paramMatrix4f.m12;
/*  286 */     this.m13 = paramMatrix4f.m13;
/*      */     
/*  288 */     this.m20 = paramMatrix4f.m20;
/*  289 */     this.m21 = paramMatrix4f.m21;
/*  290 */     this.m22 = paramMatrix4f.m22;
/*  291 */     this.m23 = paramMatrix4f.m23;
/*      */     
/*  293 */     this.m30 = paramMatrix4f.m30;
/*  294 */     this.m31 = paramMatrix4f.m31;
/*  295 */     this.m32 = paramMatrix4f.m32;
/*  296 */     this.m33 = paramMatrix4f.m33;
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
/*      */   public Matrix4f(Matrix3f paramMatrix3f, Vector3f paramVector3f, float paramFloat) {
/*  312 */     this.m00 = paramMatrix3f.m00 * paramFloat;
/*  313 */     this.m01 = paramMatrix3f.m01 * paramFloat;
/*  314 */     this.m02 = paramMatrix3f.m02 * paramFloat;
/*  315 */     this.m03 = paramVector3f.x;
/*      */     
/*  317 */     this.m10 = paramMatrix3f.m10 * paramFloat;
/*  318 */     this.m11 = paramMatrix3f.m11 * paramFloat;
/*  319 */     this.m12 = paramMatrix3f.m12 * paramFloat;
/*  320 */     this.m13 = paramVector3f.y;
/*      */     
/*  322 */     this.m20 = paramMatrix3f.m20 * paramFloat;
/*  323 */     this.m21 = paramMatrix3f.m21 * paramFloat;
/*  324 */     this.m22 = paramMatrix3f.m22 * paramFloat;
/*  325 */     this.m23 = paramVector3f.z;
/*      */     
/*  327 */     this.m30 = 0.0F;
/*  328 */     this.m31 = 0.0F;
/*  329 */     this.m32 = 0.0F;
/*  330 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4f() {
/*  340 */     this.m00 = 0.0F;
/*  341 */     this.m01 = 0.0F;
/*  342 */     this.m02 = 0.0F;
/*  343 */     this.m03 = 0.0F;
/*      */     
/*  345 */     this.m10 = 0.0F;
/*  346 */     this.m11 = 0.0F;
/*  347 */     this.m12 = 0.0F;
/*  348 */     this.m13 = 0.0F;
/*      */     
/*  350 */     this.m20 = 0.0F;
/*  351 */     this.m21 = 0.0F;
/*  352 */     this.m22 = 0.0F;
/*  353 */     this.m23 = 0.0F;
/*      */     
/*  355 */     this.m30 = 0.0F;
/*  356 */     this.m31 = 0.0F;
/*  357 */     this.m32 = 0.0F;
/*  358 */     this.m33 = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  367 */     return this.m00 + ", " + this.m01 + ", " + this.m02 + ", " + this.m03 + "\n" + this.m10 + ", " + this.m11 + ", " + this.m12 + ", " + this.m13 + "\n" + this.m20 + ", " + this.m21 + ", " + this.m22 + ", " + this.m23 + "\n" + this.m30 + ", " + this.m31 + ", " + this.m32 + ", " + this.m33 + "\n";
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
/*      */   public final void setIdentity() {
/*  379 */     this.m00 = 1.0F;
/*  380 */     this.m01 = 0.0F;
/*  381 */     this.m02 = 0.0F;
/*  382 */     this.m03 = 0.0F;
/*      */     
/*  384 */     this.m10 = 0.0F;
/*  385 */     this.m11 = 1.0F;
/*  386 */     this.m12 = 0.0F;
/*  387 */     this.m13 = 0.0F;
/*      */     
/*  389 */     this.m20 = 0.0F;
/*  390 */     this.m21 = 0.0F;
/*  391 */     this.m22 = 1.0F;
/*  392 */     this.m23 = 0.0F;
/*      */     
/*  394 */     this.m30 = 0.0F;
/*  395 */     this.m31 = 0.0F;
/*  396 */     this.m32 = 0.0F;
/*  397 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setElement(int paramInt1, int paramInt2, float paramFloat) {
/*  408 */     switch (paramInt1) {
/*      */       
/*      */       case 0:
/*  411 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  414 */             this.m00 = paramFloat;
/*      */             return;
/*      */           case 1:
/*  417 */             this.m01 = paramFloat;
/*      */             return;
/*      */           case 2:
/*  420 */             this.m02 = paramFloat;
/*      */             return;
/*      */           case 3:
/*  423 */             this.m03 = paramFloat;
/*      */             return;
/*      */         } 
/*  426 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  431 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  434 */             this.m10 = paramFloat;
/*      */             return;
/*      */           case 1:
/*  437 */             this.m11 = paramFloat;
/*      */             return;
/*      */           case 2:
/*  440 */             this.m12 = paramFloat;
/*      */             return;
/*      */           case 3:
/*  443 */             this.m13 = paramFloat;
/*      */             return;
/*      */         } 
/*  446 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  451 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  454 */             this.m20 = paramFloat;
/*      */             return;
/*      */           case 1:
/*  457 */             this.m21 = paramFloat;
/*      */             return;
/*      */           case 2:
/*  460 */             this.m22 = paramFloat;
/*      */             return;
/*      */           case 3:
/*  463 */             this.m23 = paramFloat;
/*      */             return;
/*      */         } 
/*  466 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*  471 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  474 */             this.m30 = paramFloat;
/*      */             return;
/*      */           case 1:
/*  477 */             this.m31 = paramFloat;
/*      */             return;
/*      */           case 2:
/*  480 */             this.m32 = paramFloat;
/*      */             return;
/*      */           case 3:
/*  483 */             this.m33 = paramFloat;
/*      */             return;
/*      */         } 
/*  486 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  491 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
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
/*  503 */     switch (paramInt1) {
/*      */       
/*      */       case 0:
/*  506 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  509 */             return this.m00;
/*      */           case 1:
/*  511 */             return this.m01;
/*      */           case 2:
/*  513 */             return this.m02;
/*      */           case 3:
/*  515 */             return this.m03;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/*  521 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  524 */             return this.m10;
/*      */           case 1:
/*  526 */             return this.m11;
/*      */           case 2:
/*  528 */             return this.m12;
/*      */           case 3:
/*  530 */             return this.m13;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/*  537 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  540 */             return this.m20;
/*      */           case 1:
/*  542 */             return this.m21;
/*      */           case 2:
/*  544 */             return this.m22;
/*      */           case 3:
/*  546 */             return this.m23;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 3:
/*  553 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  556 */             return this.m30;
/*      */           case 1:
/*  558 */             return this.m31;
/*      */           case 2:
/*  560 */             return this.m32;
/*      */           case 3:
/*  562 */             return this.m33;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  571 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f1"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int paramInt, Vector4f paramVector4f) {
/*  580 */     if (paramInt == 0) {
/*  581 */       paramVector4f.x = this.m00;
/*  582 */       paramVector4f.y = this.m01;
/*  583 */       paramVector4f.z = this.m02;
/*  584 */       paramVector4f.w = this.m03;
/*  585 */     } else if (paramInt == 1) {
/*  586 */       paramVector4f.x = this.m10;
/*  587 */       paramVector4f.y = this.m11;
/*  588 */       paramVector4f.z = this.m12;
/*  589 */       paramVector4f.w = this.m13;
/*  590 */     } else if (paramInt == 2) {
/*  591 */       paramVector4f.x = this.m20;
/*  592 */       paramVector4f.y = this.m21;
/*  593 */       paramVector4f.z = this.m22;
/*  594 */       paramVector4f.w = this.m23;
/*  595 */     } else if (paramInt == 3) {
/*  596 */       paramVector4f.x = this.m30;
/*  597 */       paramVector4f.y = this.m31;
/*  598 */       paramVector4f.z = this.m32;
/*  599 */       paramVector4f.w = this.m33;
/*      */     } else {
/*  601 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f2"));
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
/*  612 */     if (paramInt == 0) {
/*  613 */       paramArrayOffloat[0] = this.m00;
/*  614 */       paramArrayOffloat[1] = this.m01;
/*  615 */       paramArrayOffloat[2] = this.m02;
/*  616 */       paramArrayOffloat[3] = this.m03;
/*  617 */     } else if (paramInt == 1) {
/*  618 */       paramArrayOffloat[0] = this.m10;
/*  619 */       paramArrayOffloat[1] = this.m11;
/*  620 */       paramArrayOffloat[2] = this.m12;
/*  621 */       paramArrayOffloat[3] = this.m13;
/*  622 */     } else if (paramInt == 2) {
/*  623 */       paramArrayOffloat[0] = this.m20;
/*  624 */       paramArrayOffloat[1] = this.m21;
/*  625 */       paramArrayOffloat[2] = this.m22;
/*  626 */       paramArrayOffloat[3] = this.m23;
/*  627 */     } else if (paramInt == 3) {
/*  628 */       paramArrayOffloat[0] = this.m30;
/*  629 */       paramArrayOffloat[1] = this.m31;
/*  630 */       paramArrayOffloat[2] = this.m32;
/*  631 */       paramArrayOffloat[3] = this.m33;
/*      */     } else {
/*  633 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f2"));
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
/*      */   public final void getColumn(int paramInt, Vector4f paramVector4f) {
/*  645 */     if (paramInt == 0) {
/*  646 */       paramVector4f.x = this.m00;
/*  647 */       paramVector4f.y = this.m10;
/*  648 */       paramVector4f.z = this.m20;
/*  649 */       paramVector4f.w = this.m30;
/*  650 */     } else if (paramInt == 1) {
/*  651 */       paramVector4f.x = this.m01;
/*  652 */       paramVector4f.y = this.m11;
/*  653 */       paramVector4f.z = this.m21;
/*  654 */       paramVector4f.w = this.m31;
/*  655 */     } else if (paramInt == 2) {
/*  656 */       paramVector4f.x = this.m02;
/*  657 */       paramVector4f.y = this.m12;
/*  658 */       paramVector4f.z = this.m22;
/*  659 */       paramVector4f.w = this.m32;
/*  660 */     } else if (paramInt == 3) {
/*  661 */       paramVector4f.x = this.m03;
/*  662 */       paramVector4f.y = this.m13;
/*  663 */       paramVector4f.z = this.m23;
/*  664 */       paramVector4f.w = this.m33;
/*      */     } else {
/*  666 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f4"));
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
/*  678 */     if (paramInt == 0) {
/*  679 */       paramArrayOffloat[0] = this.m00;
/*  680 */       paramArrayOffloat[1] = this.m10;
/*  681 */       paramArrayOffloat[2] = this.m20;
/*  682 */       paramArrayOffloat[3] = this.m30;
/*  683 */     } else if (paramInt == 1) {
/*  684 */       paramArrayOffloat[0] = this.m01;
/*  685 */       paramArrayOffloat[1] = this.m11;
/*  686 */       paramArrayOffloat[2] = this.m21;
/*  687 */       paramArrayOffloat[3] = this.m31;
/*  688 */     } else if (paramInt == 2) {
/*  689 */       paramArrayOffloat[0] = this.m02;
/*  690 */       paramArrayOffloat[1] = this.m12;
/*  691 */       paramArrayOffloat[2] = this.m22;
/*  692 */       paramArrayOffloat[3] = this.m32;
/*  693 */     } else if (paramInt == 3) {
/*  694 */       paramArrayOffloat[0] = this.m03;
/*  695 */       paramArrayOffloat[1] = this.m13;
/*  696 */       paramArrayOffloat[2] = this.m23;
/*  697 */       paramArrayOffloat[3] = this.m33;
/*      */     } else {
/*  699 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f4"));
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
/*      */   public final void setScale(float paramFloat) {
/*  713 */     double[] arrayOfDouble1 = new double[9];
/*  714 */     double[] arrayOfDouble2 = new double[3];
/*  715 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/*  717 */     this.m00 = (float)(arrayOfDouble1[0] * paramFloat);
/*  718 */     this.m01 = (float)(arrayOfDouble1[1] * paramFloat);
/*  719 */     this.m02 = (float)(arrayOfDouble1[2] * paramFloat);
/*      */     
/*  721 */     this.m10 = (float)(arrayOfDouble1[3] * paramFloat);
/*  722 */     this.m11 = (float)(arrayOfDouble1[4] * paramFloat);
/*  723 */     this.m12 = (float)(arrayOfDouble1[5] * paramFloat);
/*      */     
/*  725 */     this.m20 = (float)(arrayOfDouble1[6] * paramFloat);
/*  726 */     this.m21 = (float)(arrayOfDouble1[7] * paramFloat);
/*  727 */     this.m22 = (float)(arrayOfDouble1[8] * paramFloat);
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
/*      */   public final void get(Matrix3d paramMatrix3d) {
/*  739 */     double[] arrayOfDouble1 = new double[9];
/*  740 */     double[] arrayOfDouble2 = new double[3];
/*      */     
/*  742 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/*  744 */     paramMatrix3d.m00 = arrayOfDouble1[0];
/*  745 */     paramMatrix3d.m01 = arrayOfDouble1[1];
/*  746 */     paramMatrix3d.m02 = arrayOfDouble1[2];
/*      */     
/*  748 */     paramMatrix3d.m10 = arrayOfDouble1[3];
/*  749 */     paramMatrix3d.m11 = arrayOfDouble1[4];
/*  750 */     paramMatrix3d.m12 = arrayOfDouble1[5];
/*      */     
/*  752 */     paramMatrix3d.m20 = arrayOfDouble1[6];
/*  753 */     paramMatrix3d.m21 = arrayOfDouble1[7];
/*  754 */     paramMatrix3d.m22 = arrayOfDouble1[8];
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
/*      */   public final void get(Matrix3f paramMatrix3f) {
/*  766 */     double[] arrayOfDouble1 = new double[9];
/*  767 */     double[] arrayOfDouble2 = new double[3];
/*      */     
/*  769 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/*  771 */     paramMatrix3f.m00 = (float)arrayOfDouble1[0];
/*  772 */     paramMatrix3f.m01 = (float)arrayOfDouble1[1];
/*  773 */     paramMatrix3f.m02 = (float)arrayOfDouble1[2];
/*      */     
/*  775 */     paramMatrix3f.m10 = (float)arrayOfDouble1[3];
/*  776 */     paramMatrix3f.m11 = (float)arrayOfDouble1[4];
/*  777 */     paramMatrix3f.m12 = (float)arrayOfDouble1[5];
/*      */     
/*  779 */     paramMatrix3f.m20 = (float)arrayOfDouble1[6];
/*  780 */     paramMatrix3f.m21 = (float)arrayOfDouble1[7];
/*  781 */     paramMatrix3f.m22 = (float)arrayOfDouble1[8];
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
/*      */   public final float get(Matrix3f paramMatrix3f, Vector3f paramVector3f) {
/*  796 */     double[] arrayOfDouble1 = new double[9];
/*  797 */     double[] arrayOfDouble2 = new double[3];
/*      */     
/*  799 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/*  801 */     paramMatrix3f.m00 = (float)arrayOfDouble1[0];
/*  802 */     paramMatrix3f.m01 = (float)arrayOfDouble1[1];
/*  803 */     paramMatrix3f.m02 = (float)arrayOfDouble1[2];
/*      */     
/*  805 */     paramMatrix3f.m10 = (float)arrayOfDouble1[3];
/*  806 */     paramMatrix3f.m11 = (float)arrayOfDouble1[4];
/*  807 */     paramMatrix3f.m12 = (float)arrayOfDouble1[5];
/*      */     
/*  809 */     paramMatrix3f.m20 = (float)arrayOfDouble1[6];
/*  810 */     paramMatrix3f.m21 = (float)arrayOfDouble1[7];
/*  811 */     paramMatrix3f.m22 = (float)arrayOfDouble1[8];
/*      */     
/*  813 */     paramVector3f.x = this.m03;
/*  814 */     paramVector3f.y = this.m13;
/*  815 */     paramVector3f.z = this.m23;
/*      */     
/*  817 */     return (float)Matrix3d.max3(arrayOfDouble2);
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
/*      */   public final void get(Quat4f paramQuat4f) {
/*  829 */     double[] arrayOfDouble1 = new double[9];
/*  830 */     double[] arrayOfDouble2 = new double[3];
/*  831 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */ 
/*      */ 
/*      */     
/*  835 */     double d = 0.25D * (1.0D + arrayOfDouble1[0] + arrayOfDouble1[4] + arrayOfDouble1[8]);
/*  836 */     if (((d < 0.0D) ? -d : d) >= 1.0E-30D) {
/*  837 */       paramQuat4f.w = (float)Math.sqrt(d);
/*  838 */       d = 0.25D / paramQuat4f.w;
/*  839 */       paramQuat4f.x = (float)((arrayOfDouble1[7] - arrayOfDouble1[5]) * d);
/*  840 */       paramQuat4f.y = (float)((arrayOfDouble1[2] - arrayOfDouble1[6]) * d);
/*  841 */       paramQuat4f.z = (float)((arrayOfDouble1[3] - arrayOfDouble1[1]) * d);
/*      */       
/*      */       return;
/*      */     } 
/*  845 */     paramQuat4f.w = 0.0F;
/*  846 */     d = -0.5D * (arrayOfDouble1[4] + arrayOfDouble1[8]);
/*  847 */     if (((d < 0.0D) ? -d : d) >= 1.0E-30D) {
/*  848 */       paramQuat4f.x = (float)Math.sqrt(d);
/*  849 */       d = 0.5D / paramQuat4f.x;
/*  850 */       paramQuat4f.y = (float)(arrayOfDouble1[3] * d);
/*  851 */       paramQuat4f.z = (float)(arrayOfDouble1[6] * d);
/*      */       
/*      */       return;
/*      */     } 
/*  855 */     paramQuat4f.x = 0.0F;
/*  856 */     d = 0.5D * (1.0D - arrayOfDouble1[8]);
/*  857 */     if (((d < 0.0D) ? -d : d) >= 1.0E-30D) {
/*  858 */       paramQuat4f.y = (float)Math.sqrt(d);
/*  859 */       paramQuat4f.z = (float)(arrayOfDouble1[7] / 2.0D * paramQuat4f.y);
/*      */       
/*      */       return;
/*      */     } 
/*  863 */     paramQuat4f.y = 0.0F;
/*  864 */     paramQuat4f.z = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Vector3f paramVector3f) {
/*  875 */     paramVector3f.x = this.m03;
/*  876 */     paramVector3f.y = this.m13;
/*  877 */     paramVector3f.z = this.m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRotationScale(Matrix3f paramMatrix3f) {
/*  887 */     paramMatrix3f.m00 = this.m00; paramMatrix3f.m01 = this.m01; paramMatrix3f.m02 = this.m02;
/*  888 */     paramMatrix3f.m10 = this.m10; paramMatrix3f.m11 = this.m11; paramMatrix3f.m12 = this.m12;
/*  889 */     paramMatrix3f.m20 = this.m20; paramMatrix3f.m21 = this.m21; paramMatrix3f.m22 = this.m22;
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
/*      */   public final float getScale() {
/*  901 */     double[] arrayOfDouble1 = new double[9];
/*  902 */     double[] arrayOfDouble2 = new double[3];
/*      */     
/*  904 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/*  906 */     return (float)Matrix3d.max3(arrayOfDouble2);
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
/*      */   public final void setRotationScale(Matrix3f paramMatrix3f) {
/*  918 */     this.m00 = paramMatrix3f.m00; this.m01 = paramMatrix3f.m01; this.m02 = paramMatrix3f.m02;
/*  919 */     this.m10 = paramMatrix3f.m10; this.m11 = paramMatrix3f.m11; this.m12 = paramMatrix3f.m12;
/*  920 */     this.m20 = paramMatrix3f.m20; this.m21 = paramMatrix3f.m21; this.m22 = paramMatrix3f.m22;
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
/*      */   public final void setRow(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  934 */     switch (paramInt) {
/*      */       case 0:
/*  936 */         this.m00 = paramFloat1;
/*  937 */         this.m01 = paramFloat2;
/*  938 */         this.m02 = paramFloat3;
/*  939 */         this.m03 = paramFloat4;
/*      */         return;
/*      */       
/*      */       case 1:
/*  943 */         this.m10 = paramFloat1;
/*  944 */         this.m11 = paramFloat2;
/*  945 */         this.m12 = paramFloat3;
/*  946 */         this.m13 = paramFloat4;
/*      */         return;
/*      */       
/*      */       case 2:
/*  950 */         this.m20 = paramFloat1;
/*  951 */         this.m21 = paramFloat2;
/*  952 */         this.m22 = paramFloat3;
/*  953 */         this.m23 = paramFloat4;
/*      */         return;
/*      */       
/*      */       case 3:
/*  957 */         this.m30 = paramFloat1;
/*  958 */         this.m31 = paramFloat2;
/*  959 */         this.m32 = paramFloat3;
/*  960 */         this.m33 = paramFloat4;
/*      */         return;
/*      */     } 
/*      */     
/*  964 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int paramInt, Vector4f paramVector4f) {
/*  975 */     switch (paramInt) {
/*      */       case 0:
/*  977 */         this.m00 = paramVector4f.x;
/*  978 */         this.m01 = paramVector4f.y;
/*  979 */         this.m02 = paramVector4f.z;
/*  980 */         this.m03 = paramVector4f.w;
/*      */         return;
/*      */       
/*      */       case 1:
/*  984 */         this.m10 = paramVector4f.x;
/*  985 */         this.m11 = paramVector4f.y;
/*  986 */         this.m12 = paramVector4f.z;
/*  987 */         this.m13 = paramVector4f.w;
/*      */         return;
/*      */       
/*      */       case 2:
/*  991 */         this.m20 = paramVector4f.x;
/*  992 */         this.m21 = paramVector4f.y;
/*  993 */         this.m22 = paramVector4f.z;
/*  994 */         this.m23 = paramVector4f.w;
/*      */         return;
/*      */       
/*      */       case 3:
/*  998 */         this.m30 = paramVector4f.x;
/*  999 */         this.m31 = paramVector4f.y;
/* 1000 */         this.m32 = paramVector4f.z;
/* 1001 */         this.m33 = paramVector4f.w;
/*      */         return;
/*      */     } 
/*      */     
/* 1005 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f6"));
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
/*      */   public final void setRow(int paramInt, float[] paramArrayOffloat) {
/* 1017 */     switch (paramInt) {
/*      */       case 0:
/* 1019 */         this.m00 = paramArrayOffloat[0];
/* 1020 */         this.m01 = paramArrayOffloat[1];
/* 1021 */         this.m02 = paramArrayOffloat[2];
/* 1022 */         this.m03 = paramArrayOffloat[3];
/*      */         return;
/*      */       
/*      */       case 1:
/* 1026 */         this.m10 = paramArrayOffloat[0];
/* 1027 */         this.m11 = paramArrayOffloat[1];
/* 1028 */         this.m12 = paramArrayOffloat[2];
/* 1029 */         this.m13 = paramArrayOffloat[3];
/*      */         return;
/*      */       
/*      */       case 2:
/* 1033 */         this.m20 = paramArrayOffloat[0];
/* 1034 */         this.m21 = paramArrayOffloat[1];
/* 1035 */         this.m22 = paramArrayOffloat[2];
/* 1036 */         this.m23 = paramArrayOffloat[3];
/*      */         return;
/*      */       
/*      */       case 3:
/* 1040 */         this.m30 = paramArrayOffloat[0];
/* 1041 */         this.m31 = paramArrayOffloat[1];
/* 1042 */         this.m32 = paramArrayOffloat[2];
/* 1043 */         this.m33 = paramArrayOffloat[3];
/*      */         return;
/*      */     } 
/*      */     
/* 1047 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f6"));
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
/*      */   public final void setColumn(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 1061 */     switch (paramInt) {
/*      */       case 0:
/* 1063 */         this.m00 = paramFloat1;
/* 1064 */         this.m10 = paramFloat2;
/* 1065 */         this.m20 = paramFloat3;
/* 1066 */         this.m30 = paramFloat4;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1070 */         this.m01 = paramFloat1;
/* 1071 */         this.m11 = paramFloat2;
/* 1072 */         this.m21 = paramFloat3;
/* 1073 */         this.m31 = paramFloat4;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1077 */         this.m02 = paramFloat1;
/* 1078 */         this.m12 = paramFloat2;
/* 1079 */         this.m22 = paramFloat3;
/* 1080 */         this.m32 = paramFloat4;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1084 */         this.m03 = paramFloat1;
/* 1085 */         this.m13 = paramFloat2;
/* 1086 */         this.m23 = paramFloat3;
/* 1087 */         this.m33 = paramFloat4;
/*      */         return;
/*      */     } 
/*      */     
/* 1091 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int paramInt, Vector4f paramVector4f) {
/* 1102 */     switch (paramInt) {
/*      */       case 0:
/* 1104 */         this.m00 = paramVector4f.x;
/* 1105 */         this.m10 = paramVector4f.y;
/* 1106 */         this.m20 = paramVector4f.z;
/* 1107 */         this.m30 = paramVector4f.w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1111 */         this.m01 = paramVector4f.x;
/* 1112 */         this.m11 = paramVector4f.y;
/* 1113 */         this.m21 = paramVector4f.z;
/* 1114 */         this.m31 = paramVector4f.w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1118 */         this.m02 = paramVector4f.x;
/* 1119 */         this.m12 = paramVector4f.y;
/* 1120 */         this.m22 = paramVector4f.z;
/* 1121 */         this.m32 = paramVector4f.w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1125 */         this.m03 = paramVector4f.x;
/* 1126 */         this.m13 = paramVector4f.y;
/* 1127 */         this.m23 = paramVector4f.z;
/* 1128 */         this.m33 = paramVector4f.w;
/*      */         return;
/*      */     } 
/*      */     
/* 1132 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f9"));
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
/* 1143 */     switch (paramInt) {
/*      */       case 0:
/* 1145 */         this.m00 = paramArrayOffloat[0];
/* 1146 */         this.m10 = paramArrayOffloat[1];
/* 1147 */         this.m20 = paramArrayOffloat[2];
/* 1148 */         this.m30 = paramArrayOffloat[3];
/*      */         return;
/*      */       
/*      */       case 1:
/* 1152 */         this.m01 = paramArrayOffloat[0];
/* 1153 */         this.m11 = paramArrayOffloat[1];
/* 1154 */         this.m21 = paramArrayOffloat[2];
/* 1155 */         this.m31 = paramArrayOffloat[3];
/*      */         return;
/*      */       
/*      */       case 2:
/* 1159 */         this.m02 = paramArrayOffloat[0];
/* 1160 */         this.m12 = paramArrayOffloat[1];
/* 1161 */         this.m22 = paramArrayOffloat[2];
/* 1162 */         this.m32 = paramArrayOffloat[3];
/*      */         return;
/*      */       
/*      */       case 3:
/* 1166 */         this.m03 = paramArrayOffloat[0];
/* 1167 */         this.m13 = paramArrayOffloat[1];
/* 1168 */         this.m23 = paramArrayOffloat[2];
/* 1169 */         this.m33 = paramArrayOffloat[3];
/*      */         return;
/*      */     } 
/*      */     
/* 1173 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(float paramFloat) {
/* 1183 */     this.m00 += paramFloat;
/* 1184 */     this.m01 += paramFloat;
/* 1185 */     this.m02 += paramFloat;
/* 1186 */     this.m03 += paramFloat;
/* 1187 */     this.m10 += paramFloat;
/* 1188 */     this.m11 += paramFloat;
/* 1189 */     this.m12 += paramFloat;
/* 1190 */     this.m13 += paramFloat;
/* 1191 */     this.m20 += paramFloat;
/* 1192 */     this.m21 += paramFloat;
/* 1193 */     this.m22 += paramFloat;
/* 1194 */     this.m23 += paramFloat;
/* 1195 */     this.m30 += paramFloat;
/* 1196 */     this.m31 += paramFloat;
/* 1197 */     this.m32 += paramFloat;
/* 1198 */     this.m33 += paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(float paramFloat, Matrix4f paramMatrix4f) {
/* 1209 */     paramMatrix4f.m00 += paramFloat;
/* 1210 */     paramMatrix4f.m01 += paramFloat;
/* 1211 */     paramMatrix4f.m02 += paramFloat;
/* 1212 */     paramMatrix4f.m03 += paramFloat;
/* 1213 */     paramMatrix4f.m10 += paramFloat;
/* 1214 */     paramMatrix4f.m11 += paramFloat;
/* 1215 */     paramMatrix4f.m12 += paramFloat;
/* 1216 */     paramMatrix4f.m13 += paramFloat;
/* 1217 */     paramMatrix4f.m20 += paramFloat;
/* 1218 */     paramMatrix4f.m21 += paramFloat;
/* 1219 */     paramMatrix4f.m22 += paramFloat;
/* 1220 */     paramMatrix4f.m23 += paramFloat;
/* 1221 */     paramMatrix4f.m30 += paramFloat;
/* 1222 */     paramMatrix4f.m31 += paramFloat;
/* 1223 */     paramMatrix4f.m32 += paramFloat;
/* 1224 */     paramMatrix4f.m33 += paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix4f paramMatrix4f1, Matrix4f paramMatrix4f2) {
/* 1234 */     paramMatrix4f1.m00 += paramMatrix4f2.m00;
/* 1235 */     paramMatrix4f1.m01 += paramMatrix4f2.m01;
/* 1236 */     paramMatrix4f1.m02 += paramMatrix4f2.m02;
/* 1237 */     paramMatrix4f1.m03 += paramMatrix4f2.m03;
/*      */     
/* 1239 */     paramMatrix4f1.m10 += paramMatrix4f2.m10;
/* 1240 */     paramMatrix4f1.m11 += paramMatrix4f2.m11;
/* 1241 */     paramMatrix4f1.m12 += paramMatrix4f2.m12;
/* 1242 */     paramMatrix4f1.m13 += paramMatrix4f2.m13;
/*      */     
/* 1244 */     paramMatrix4f1.m20 += paramMatrix4f2.m20;
/* 1245 */     paramMatrix4f1.m21 += paramMatrix4f2.m21;
/* 1246 */     paramMatrix4f1.m22 += paramMatrix4f2.m22;
/* 1247 */     paramMatrix4f1.m23 += paramMatrix4f2.m23;
/*      */     
/* 1249 */     paramMatrix4f1.m30 += paramMatrix4f2.m30;
/* 1250 */     paramMatrix4f1.m31 += paramMatrix4f2.m31;
/* 1251 */     paramMatrix4f1.m32 += paramMatrix4f2.m32;
/* 1252 */     paramMatrix4f1.m33 += paramMatrix4f2.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix4f paramMatrix4f) {
/* 1262 */     this.m00 += paramMatrix4f.m00;
/* 1263 */     this.m01 += paramMatrix4f.m01;
/* 1264 */     this.m02 += paramMatrix4f.m02;
/* 1265 */     this.m03 += paramMatrix4f.m03;
/*      */     
/* 1267 */     this.m10 += paramMatrix4f.m10;
/* 1268 */     this.m11 += paramMatrix4f.m11;
/* 1269 */     this.m12 += paramMatrix4f.m12;
/* 1270 */     this.m13 += paramMatrix4f.m13;
/*      */     
/* 1272 */     this.m20 += paramMatrix4f.m20;
/* 1273 */     this.m21 += paramMatrix4f.m21;
/* 1274 */     this.m22 += paramMatrix4f.m22;
/* 1275 */     this.m23 += paramMatrix4f.m23;
/*      */     
/* 1277 */     this.m30 += paramMatrix4f.m30;
/* 1278 */     this.m31 += paramMatrix4f.m31;
/* 1279 */     this.m32 += paramMatrix4f.m32;
/* 1280 */     this.m33 += paramMatrix4f.m33;
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
/*      */   public final void sub(Matrix4f paramMatrix4f1, Matrix4f paramMatrix4f2) {
/* 1292 */     paramMatrix4f1.m00 -= paramMatrix4f2.m00;
/* 1293 */     paramMatrix4f1.m01 -= paramMatrix4f2.m01;
/* 1294 */     paramMatrix4f1.m02 -= paramMatrix4f2.m02;
/* 1295 */     paramMatrix4f1.m03 -= paramMatrix4f2.m03;
/*      */     
/* 1297 */     paramMatrix4f1.m10 -= paramMatrix4f2.m10;
/* 1298 */     paramMatrix4f1.m11 -= paramMatrix4f2.m11;
/* 1299 */     paramMatrix4f1.m12 -= paramMatrix4f2.m12;
/* 1300 */     paramMatrix4f1.m13 -= paramMatrix4f2.m13;
/*      */     
/* 1302 */     paramMatrix4f1.m20 -= paramMatrix4f2.m20;
/* 1303 */     paramMatrix4f1.m21 -= paramMatrix4f2.m21;
/* 1304 */     paramMatrix4f1.m22 -= paramMatrix4f2.m22;
/* 1305 */     paramMatrix4f1.m23 -= paramMatrix4f2.m23;
/*      */     
/* 1307 */     paramMatrix4f1.m30 -= paramMatrix4f2.m30;
/* 1308 */     paramMatrix4f1.m31 -= paramMatrix4f2.m31;
/* 1309 */     paramMatrix4f1.m32 -= paramMatrix4f2.m32;
/* 1310 */     paramMatrix4f1.m33 -= paramMatrix4f2.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix4f paramMatrix4f) {
/* 1320 */     this.m00 -= paramMatrix4f.m00;
/* 1321 */     this.m01 -= paramMatrix4f.m01;
/* 1322 */     this.m02 -= paramMatrix4f.m02;
/* 1323 */     this.m03 -= paramMatrix4f.m03;
/*      */     
/* 1325 */     this.m10 -= paramMatrix4f.m10;
/* 1326 */     this.m11 -= paramMatrix4f.m11;
/* 1327 */     this.m12 -= paramMatrix4f.m12;
/* 1328 */     this.m13 -= paramMatrix4f.m13;
/*      */     
/* 1330 */     this.m20 -= paramMatrix4f.m20;
/* 1331 */     this.m21 -= paramMatrix4f.m21;
/* 1332 */     this.m22 -= paramMatrix4f.m22;
/* 1333 */     this.m23 -= paramMatrix4f.m23;
/*      */     
/* 1335 */     this.m30 -= paramMatrix4f.m30;
/* 1336 */     this.m31 -= paramMatrix4f.m31;
/* 1337 */     this.m32 -= paramMatrix4f.m32;
/* 1338 */     this.m33 -= paramMatrix4f.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose() {
/* 1348 */     float f = this.m10;
/* 1349 */     this.m10 = this.m01;
/* 1350 */     this.m01 = f;
/*      */     
/* 1352 */     f = this.m20;
/* 1353 */     this.m20 = this.m02;
/* 1354 */     this.m02 = f;
/*      */     
/* 1356 */     f = this.m30;
/* 1357 */     this.m30 = this.m03;
/* 1358 */     this.m03 = f;
/*      */     
/* 1360 */     f = this.m21;
/* 1361 */     this.m21 = this.m12;
/* 1362 */     this.m12 = f;
/*      */     
/* 1364 */     f = this.m31;
/* 1365 */     this.m31 = this.m13;
/* 1366 */     this.m13 = f;
/*      */     
/* 1368 */     f = this.m32;
/* 1369 */     this.m32 = this.m23;
/* 1370 */     this.m23 = f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose(Matrix4f paramMatrix4f) {
/* 1379 */     if (this != paramMatrix4f) {
/* 1380 */       this.m00 = paramMatrix4f.m00;
/* 1381 */       this.m01 = paramMatrix4f.m10;
/* 1382 */       this.m02 = paramMatrix4f.m20;
/* 1383 */       this.m03 = paramMatrix4f.m30;
/*      */       
/* 1385 */       this.m10 = paramMatrix4f.m01;
/* 1386 */       this.m11 = paramMatrix4f.m11;
/* 1387 */       this.m12 = paramMatrix4f.m21;
/* 1388 */       this.m13 = paramMatrix4f.m31;
/*      */       
/* 1390 */       this.m20 = paramMatrix4f.m02;
/* 1391 */       this.m21 = paramMatrix4f.m12;
/* 1392 */       this.m22 = paramMatrix4f.m22;
/* 1393 */       this.m23 = paramMatrix4f.m32;
/*      */       
/* 1395 */       this.m30 = paramMatrix4f.m03;
/* 1396 */       this.m31 = paramMatrix4f.m13;
/* 1397 */       this.m32 = paramMatrix4f.m23;
/* 1398 */       this.m33 = paramMatrix4f.m33;
/*      */     } else {
/* 1400 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4f paramQuat4f) {
/* 1410 */     this.m00 = 1.0F - 2.0F * paramQuat4f.y * paramQuat4f.y - 2.0F * paramQuat4f.z * paramQuat4f.z;
/* 1411 */     this.m10 = 2.0F * (paramQuat4f.x * paramQuat4f.y + paramQuat4f.w * paramQuat4f.z);
/* 1412 */     this.m20 = 2.0F * (paramQuat4f.x * paramQuat4f.z - paramQuat4f.w * paramQuat4f.y);
/*      */     
/* 1414 */     this.m01 = 2.0F * (paramQuat4f.x * paramQuat4f.y - paramQuat4f.w * paramQuat4f.z);
/* 1415 */     this.m11 = 1.0F - 2.0F * paramQuat4f.x * paramQuat4f.x - 2.0F * paramQuat4f.z * paramQuat4f.z;
/* 1416 */     this.m21 = 2.0F * (paramQuat4f.y * paramQuat4f.z + paramQuat4f.w * paramQuat4f.x);
/*      */     
/* 1418 */     this.m02 = 2.0F * (paramQuat4f.x * paramQuat4f.z + paramQuat4f.w * paramQuat4f.y);
/* 1419 */     this.m12 = 2.0F * (paramQuat4f.y * paramQuat4f.z - paramQuat4f.w * paramQuat4f.x);
/* 1420 */     this.m22 = 1.0F - 2.0F * paramQuat4f.x * paramQuat4f.x - 2.0F * paramQuat4f.y * paramQuat4f.y;
/*      */     
/* 1422 */     this.m03 = 0.0F;
/* 1423 */     this.m13 = 0.0F;
/* 1424 */     this.m23 = 0.0F;
/*      */     
/* 1426 */     this.m30 = 0.0F;
/* 1427 */     this.m31 = 0.0F;
/* 1428 */     this.m32 = 0.0F;
/* 1429 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4f paramAxisAngle4f) {
/* 1439 */     float f = (float)Math.sqrt((paramAxisAngle4f.x * paramAxisAngle4f.x + paramAxisAngle4f.y * paramAxisAngle4f.y + paramAxisAngle4f.z * paramAxisAngle4f.z));
/* 1440 */     if (f < 1.0E-8D) {
/* 1441 */       this.m00 = 1.0F;
/* 1442 */       this.m01 = 0.0F;
/* 1443 */       this.m02 = 0.0F;
/*      */       
/* 1445 */       this.m10 = 0.0F;
/* 1446 */       this.m11 = 1.0F;
/* 1447 */       this.m12 = 0.0F;
/*      */       
/* 1449 */       this.m20 = 0.0F;
/* 1450 */       this.m21 = 0.0F;
/* 1451 */       this.m22 = 1.0F;
/*      */     } else {
/* 1453 */       f = 1.0F / f;
/* 1454 */       float f1 = paramAxisAngle4f.x * f;
/* 1455 */       float f2 = paramAxisAngle4f.y * f;
/* 1456 */       float f3 = paramAxisAngle4f.z * f;
/*      */       
/* 1458 */       float f4 = (float)Math.sin(paramAxisAngle4f.angle);
/* 1459 */       float f5 = (float)Math.cos(paramAxisAngle4f.angle);
/* 1460 */       float f6 = 1.0F - f5;
/*      */       
/* 1462 */       float f7 = f1 * f3;
/* 1463 */       float f8 = f1 * f2;
/* 1464 */       float f9 = f2 * f3;
/*      */       
/* 1466 */       this.m00 = f6 * f1 * f1 + f5;
/* 1467 */       this.m01 = f6 * f8 - f4 * f3;
/* 1468 */       this.m02 = f6 * f7 + f4 * f2;
/*      */       
/* 1470 */       this.m10 = f6 * f8 + f4 * f3;
/* 1471 */       this.m11 = f6 * f2 * f2 + f5;
/* 1472 */       this.m12 = f6 * f9 - f4 * f1;
/*      */       
/* 1474 */       this.m20 = f6 * f7 - f4 * f2;
/* 1475 */       this.m21 = f6 * f9 + f4 * f1;
/* 1476 */       this.m22 = f6 * f3 * f3 + f5;
/*      */     } 
/* 1478 */     this.m03 = 0.0F;
/* 1479 */     this.m13 = 0.0F;
/* 1480 */     this.m23 = 0.0F;
/*      */     
/* 1482 */     this.m30 = 0.0F;
/* 1483 */     this.m31 = 0.0F;
/* 1484 */     this.m32 = 0.0F;
/* 1485 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4d paramQuat4d) {
/* 1495 */     this.m00 = (float)(1.0D - 2.0D * paramQuat4d.y * paramQuat4d.y - 2.0D * paramQuat4d.z * paramQuat4d.z);
/* 1496 */     this.m10 = (float)(2.0D * (paramQuat4d.x * paramQuat4d.y + paramQuat4d.w * paramQuat4d.z));
/* 1497 */     this.m20 = (float)(2.0D * (paramQuat4d.x * paramQuat4d.z - paramQuat4d.w * paramQuat4d.y));
/*      */     
/* 1499 */     this.m01 = (float)(2.0D * (paramQuat4d.x * paramQuat4d.y - paramQuat4d.w * paramQuat4d.z));
/* 1500 */     this.m11 = (float)(1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.z * paramQuat4d.z);
/* 1501 */     this.m21 = (float)(2.0D * (paramQuat4d.y * paramQuat4d.z + paramQuat4d.w * paramQuat4d.x));
/*      */     
/* 1503 */     this.m02 = (float)(2.0D * (paramQuat4d.x * paramQuat4d.z + paramQuat4d.w * paramQuat4d.y));
/* 1504 */     this.m12 = (float)(2.0D * (paramQuat4d.y * paramQuat4d.z - paramQuat4d.w * paramQuat4d.x));
/* 1505 */     this.m22 = (float)(1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.y * paramQuat4d.y);
/*      */     
/* 1507 */     this.m03 = 0.0F;
/* 1508 */     this.m13 = 0.0F;
/* 1509 */     this.m23 = 0.0F;
/*      */     
/* 1511 */     this.m30 = 0.0F;
/* 1512 */     this.m31 = 0.0F;
/* 1513 */     this.m32 = 0.0F;
/* 1514 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4d paramAxisAngle4d) {
/* 1524 */     double d = Math.sqrt(paramAxisAngle4d.x * paramAxisAngle4d.x + paramAxisAngle4d.y * paramAxisAngle4d.y + paramAxisAngle4d.z * paramAxisAngle4d.z);
/*      */     
/* 1526 */     if (d < 1.0E-8D) {
/* 1527 */       this.m00 = 1.0F;
/* 1528 */       this.m01 = 0.0F;
/* 1529 */       this.m02 = 0.0F;
/*      */       
/* 1531 */       this.m10 = 0.0F;
/* 1532 */       this.m11 = 1.0F;
/* 1533 */       this.m12 = 0.0F;
/*      */       
/* 1535 */       this.m20 = 0.0F;
/* 1536 */       this.m21 = 0.0F;
/* 1537 */       this.m22 = 1.0F;
/*      */     } else {
/* 1539 */       d = 1.0D / d;
/* 1540 */       double d1 = paramAxisAngle4d.x * d;
/* 1541 */       double d2 = paramAxisAngle4d.y * d;
/* 1542 */       double d3 = paramAxisAngle4d.z * d;
/*      */       
/* 1544 */       float f1 = (float)Math.sin(paramAxisAngle4d.angle);
/* 1545 */       float f2 = (float)Math.cos(paramAxisAngle4d.angle);
/* 1546 */       float f3 = 1.0F - f2;
/*      */       
/* 1548 */       float f4 = (float)(d1 * d3);
/* 1549 */       float f5 = (float)(d1 * d2);
/* 1550 */       float f6 = (float)(d2 * d3);
/*      */       
/* 1552 */       this.m00 = f3 * (float)(d1 * d1) + f2;
/* 1553 */       this.m01 = f3 * f5 - f1 * (float)d3;
/* 1554 */       this.m02 = f3 * f4 + f1 * (float)d2;
/*      */       
/* 1556 */       this.m10 = f3 * f5 + f1 * (float)d3;
/* 1557 */       this.m11 = f3 * (float)(d2 * d2) + f2;
/* 1558 */       this.m12 = f3 * f6 - f1 * (float)d1;
/*      */       
/* 1560 */       this.m20 = f3 * f4 - f1 * (float)d2;
/* 1561 */       this.m21 = f3 * f6 + f1 * (float)d1;
/* 1562 */       this.m22 = f3 * (float)(d3 * d3) + f2;
/*      */     } 
/* 1564 */     this.m03 = 0.0F;
/* 1565 */     this.m13 = 0.0F;
/* 1566 */     this.m23 = 0.0F;
/*      */     
/* 1568 */     this.m30 = 0.0F;
/* 1569 */     this.m31 = 0.0F;
/* 1570 */     this.m32 = 0.0F;
/* 1571 */     this.m33 = 1.0F;
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
/*      */   public final void set(Quat4d paramQuat4d, Vector3d paramVector3d, double paramDouble) {
/* 1583 */     this.m00 = (float)(paramDouble * (1.0D - 2.0D * paramQuat4d.y * paramQuat4d.y - 2.0D * paramQuat4d.z * paramQuat4d.z));
/* 1584 */     this.m10 = (float)(paramDouble * 2.0D * (paramQuat4d.x * paramQuat4d.y + paramQuat4d.w * paramQuat4d.z));
/* 1585 */     this.m20 = (float)(paramDouble * 2.0D * (paramQuat4d.x * paramQuat4d.z - paramQuat4d.w * paramQuat4d.y));
/*      */     
/* 1587 */     this.m01 = (float)(paramDouble * 2.0D * (paramQuat4d.x * paramQuat4d.y - paramQuat4d.w * paramQuat4d.z));
/* 1588 */     this.m11 = (float)(paramDouble * (1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.z * paramQuat4d.z));
/* 1589 */     this.m21 = (float)(paramDouble * 2.0D * (paramQuat4d.y * paramQuat4d.z + paramQuat4d.w * paramQuat4d.x));
/*      */     
/* 1591 */     this.m02 = (float)(paramDouble * 2.0D * (paramQuat4d.x * paramQuat4d.z + paramQuat4d.w * paramQuat4d.y));
/* 1592 */     this.m12 = (float)(paramDouble * 2.0D * (paramQuat4d.y * paramQuat4d.z - paramQuat4d.w * paramQuat4d.x));
/* 1593 */     this.m22 = (float)(paramDouble * (1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.y * paramQuat4d.y));
/*      */     
/* 1595 */     this.m03 = (float)paramVector3d.x;
/* 1596 */     this.m13 = (float)paramVector3d.y;
/* 1597 */     this.m23 = (float)paramVector3d.z;
/*      */     
/* 1599 */     this.m30 = 0.0F;
/* 1600 */     this.m31 = 0.0F;
/* 1601 */     this.m32 = 0.0F;
/* 1602 */     this.m33 = 1.0F;
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
/*      */   public final void set(Quat4f paramQuat4f, Vector3f paramVector3f, float paramFloat) {
/* 1614 */     this.m00 = paramFloat * (1.0F - 2.0F * paramQuat4f.y * paramQuat4f.y - 2.0F * paramQuat4f.z * paramQuat4f.z);
/* 1615 */     this.m10 = paramFloat * 2.0F * (paramQuat4f.x * paramQuat4f.y + paramQuat4f.w * paramQuat4f.z);
/* 1616 */     this.m20 = paramFloat * 2.0F * (paramQuat4f.x * paramQuat4f.z - paramQuat4f.w * paramQuat4f.y);
/*      */     
/* 1618 */     this.m01 = paramFloat * 2.0F * (paramQuat4f.x * paramQuat4f.y - paramQuat4f.w * paramQuat4f.z);
/* 1619 */     this.m11 = paramFloat * (1.0F - 2.0F * paramQuat4f.x * paramQuat4f.x - 2.0F * paramQuat4f.z * paramQuat4f.z);
/* 1620 */     this.m21 = paramFloat * 2.0F * (paramQuat4f.y * paramQuat4f.z + paramQuat4f.w * paramQuat4f.x);
/*      */     
/* 1622 */     this.m02 = paramFloat * 2.0F * (paramQuat4f.x * paramQuat4f.z + paramQuat4f.w * paramQuat4f.y);
/* 1623 */     this.m12 = paramFloat * 2.0F * (paramQuat4f.y * paramQuat4f.z - paramQuat4f.w * paramQuat4f.x);
/* 1624 */     this.m22 = paramFloat * (1.0F - 2.0F * paramQuat4f.x * paramQuat4f.x - 2.0F * paramQuat4f.y * paramQuat4f.y);
/*      */     
/* 1626 */     this.m03 = paramVector3f.x;
/* 1627 */     this.m13 = paramVector3f.y;
/* 1628 */     this.m23 = paramVector3f.z;
/*      */     
/* 1630 */     this.m30 = 0.0F;
/* 1631 */     this.m31 = 0.0F;
/* 1632 */     this.m32 = 0.0F;
/* 1633 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4d paramMatrix4d) {
/* 1643 */     this.m00 = (float)paramMatrix4d.m00;
/* 1644 */     this.m01 = (float)paramMatrix4d.m01;
/* 1645 */     this.m02 = (float)paramMatrix4d.m02;
/* 1646 */     this.m03 = (float)paramMatrix4d.m03;
/*      */     
/* 1648 */     this.m10 = (float)paramMatrix4d.m10;
/* 1649 */     this.m11 = (float)paramMatrix4d.m11;
/* 1650 */     this.m12 = (float)paramMatrix4d.m12;
/* 1651 */     this.m13 = (float)paramMatrix4d.m13;
/*      */     
/* 1653 */     this.m20 = (float)paramMatrix4d.m20;
/* 1654 */     this.m21 = (float)paramMatrix4d.m21;
/* 1655 */     this.m22 = (float)paramMatrix4d.m22;
/* 1656 */     this.m23 = (float)paramMatrix4d.m23;
/*      */     
/* 1658 */     this.m30 = (float)paramMatrix4d.m30;
/* 1659 */     this.m31 = (float)paramMatrix4d.m31;
/* 1660 */     this.m32 = (float)paramMatrix4d.m32;
/* 1661 */     this.m33 = (float)paramMatrix4d.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4f paramMatrix4f) {
/* 1671 */     this.m00 = paramMatrix4f.m00;
/* 1672 */     this.m01 = paramMatrix4f.m01;
/* 1673 */     this.m02 = paramMatrix4f.m02;
/* 1674 */     this.m03 = paramMatrix4f.m03;
/*      */     
/* 1676 */     this.m10 = paramMatrix4f.m10;
/* 1677 */     this.m11 = paramMatrix4f.m11;
/* 1678 */     this.m12 = paramMatrix4f.m12;
/* 1679 */     this.m13 = paramMatrix4f.m13;
/*      */     
/* 1681 */     this.m20 = paramMatrix4f.m20;
/* 1682 */     this.m21 = paramMatrix4f.m21;
/* 1683 */     this.m22 = paramMatrix4f.m22;
/* 1684 */     this.m23 = paramMatrix4f.m23;
/*      */     
/* 1686 */     this.m30 = paramMatrix4f.m30;
/* 1687 */     this.m31 = paramMatrix4f.m31;
/* 1688 */     this.m32 = paramMatrix4f.m32;
/* 1689 */     this.m33 = paramMatrix4f.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert(Matrix4f paramMatrix4f) {
/* 1700 */     invertGeneral(paramMatrix4f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/* 1708 */     invertGeneral(this);
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
/*      */   final void invertGeneral(Matrix4f paramMatrix4f) {
/* 1720 */     double[] arrayOfDouble1 = new double[16];
/* 1721 */     double[] arrayOfDouble2 = new double[16];
/* 1722 */     int[] arrayOfInt = new int[4];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1729 */     arrayOfDouble1[0] = paramMatrix4f.m00;
/* 1730 */     arrayOfDouble1[1] = paramMatrix4f.m01;
/* 1731 */     arrayOfDouble1[2] = paramMatrix4f.m02;
/* 1732 */     arrayOfDouble1[3] = paramMatrix4f.m03;
/*      */     
/* 1734 */     arrayOfDouble1[4] = paramMatrix4f.m10;
/* 1735 */     arrayOfDouble1[5] = paramMatrix4f.m11;
/* 1736 */     arrayOfDouble1[6] = paramMatrix4f.m12;
/* 1737 */     arrayOfDouble1[7] = paramMatrix4f.m13;
/*      */     
/* 1739 */     arrayOfDouble1[8] = paramMatrix4f.m20;
/* 1740 */     arrayOfDouble1[9] = paramMatrix4f.m21;
/* 1741 */     arrayOfDouble1[10] = paramMatrix4f.m22;
/* 1742 */     arrayOfDouble1[11] = paramMatrix4f.m23;
/*      */     
/* 1744 */     arrayOfDouble1[12] = paramMatrix4f.m30;
/* 1745 */     arrayOfDouble1[13] = paramMatrix4f.m31;
/* 1746 */     arrayOfDouble1[14] = paramMatrix4f.m32;
/* 1747 */     arrayOfDouble1[15] = paramMatrix4f.m33;
/*      */ 
/*      */     
/* 1750 */     if (!luDecomposition(arrayOfDouble1, arrayOfInt))
/*      */     {
/* 1752 */       throw new SingularMatrixException(VecMathI18N.getString("Matrix4f12"));
/*      */     }
/*      */ 
/*      */     
/* 1756 */     for (byte b = 0; b < 16; ) { arrayOfDouble2[b] = 0.0D; b++; }
/* 1757 */      arrayOfDouble2[0] = 1.0D; arrayOfDouble2[5] = 1.0D; arrayOfDouble2[10] = 1.0D; arrayOfDouble2[15] = 1.0D;
/* 1758 */     luBacksubstitution(arrayOfDouble1, arrayOfInt, arrayOfDouble2);
/*      */     
/* 1760 */     this.m00 = (float)arrayOfDouble2[0];
/* 1761 */     this.m01 = (float)arrayOfDouble2[1];
/* 1762 */     this.m02 = (float)arrayOfDouble2[2];
/* 1763 */     this.m03 = (float)arrayOfDouble2[3];
/*      */     
/* 1765 */     this.m10 = (float)arrayOfDouble2[4];
/* 1766 */     this.m11 = (float)arrayOfDouble2[5];
/* 1767 */     this.m12 = (float)arrayOfDouble2[6];
/* 1768 */     this.m13 = (float)arrayOfDouble2[7];
/*      */     
/* 1770 */     this.m20 = (float)arrayOfDouble2[8];
/* 1771 */     this.m21 = (float)arrayOfDouble2[9];
/* 1772 */     this.m22 = (float)arrayOfDouble2[10];
/* 1773 */     this.m23 = (float)arrayOfDouble2[11];
/*      */     
/* 1775 */     this.m30 = (float)arrayOfDouble2[12];
/* 1776 */     this.m31 = (float)arrayOfDouble2[13];
/* 1777 */     this.m32 = (float)arrayOfDouble2[14];
/* 1778 */     this.m33 = (float)arrayOfDouble2[15];
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
/* 1805 */     double[] arrayOfDouble = new double[4];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1813 */     int j = 0;
/* 1814 */     int k = 0;
/*      */ 
/*      */     
/* 1817 */     int i = 4;
/* 1818 */     while (i-- != 0) {
/* 1819 */       double d = 0.0D;
/*      */ 
/*      */       
/* 1822 */       int m = 4;
/* 1823 */       while (m-- != 0) {
/* 1824 */         double d1 = paramArrayOfdouble[j++];
/* 1825 */         d1 = Math.abs(d1);
/* 1826 */         if (d1 > d) {
/* 1827 */           d = d1;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1832 */       if (d == 0.0D) {
/* 1833 */         return false;
/*      */       }
/* 1835 */       arrayOfDouble[k++] = 1.0D / d;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1843 */     byte b = 0;
/*      */ 
/*      */     
/* 1846 */     for (i = 0; i < 4; i++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1852 */       for (j = 0; j < i; j++) {
/* 1853 */         int n = b + 4 * j + i;
/* 1854 */         double d1 = paramArrayOfdouble[n];
/* 1855 */         int m = j;
/* 1856 */         int i1 = b + 4 * j;
/* 1857 */         int i2 = b + i;
/* 1858 */         while (m-- != 0) {
/* 1859 */           d1 -= paramArrayOfdouble[i1] * paramArrayOfdouble[i2];
/* 1860 */           i1++;
/* 1861 */           i2 += 4;
/*      */         } 
/* 1863 */         paramArrayOfdouble[n] = d1;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1868 */       double d = 0.0D;
/* 1869 */       k = -1;
/* 1870 */       for (j = i; j < 4; j++) {
/* 1871 */         int n = b + 4 * j + i;
/* 1872 */         double d1 = paramArrayOfdouble[n];
/* 1873 */         int m = i;
/* 1874 */         int i1 = b + 4 * j;
/* 1875 */         int i2 = b + i;
/* 1876 */         while (m-- != 0) {
/* 1877 */           d1 -= paramArrayOfdouble[i1] * paramArrayOfdouble[i2];
/* 1878 */           i1++;
/* 1879 */           i2 += 4;
/*      */         } 
/* 1881 */         paramArrayOfdouble[n] = d1;
/*      */         
/*      */         double d2;
/* 1884 */         if ((d2 = arrayOfDouble[j] * Math.abs(d1)) >= d) {
/* 1885 */           d = d2;
/* 1886 */           k = j;
/*      */         } 
/*      */       } 
/*      */       
/* 1890 */       if (k < 0) {
/* 1891 */         throw new RuntimeException(VecMathI18N.getString("Matrix4f13"));
/*      */       }
/*      */ 
/*      */       
/* 1895 */       if (i != k) {
/*      */         
/* 1897 */         int m = 4;
/* 1898 */         int n = b + 4 * k;
/* 1899 */         int i1 = b + 4 * i;
/* 1900 */         while (m-- != 0) {
/* 1901 */           double d1 = paramArrayOfdouble[n];
/* 1902 */           paramArrayOfdouble[n++] = paramArrayOfdouble[i1];
/* 1903 */           paramArrayOfdouble[i1++] = d1;
/*      */         } 
/*      */ 
/*      */         
/* 1907 */         arrayOfDouble[k] = arrayOfDouble[i];
/*      */       } 
/*      */ 
/*      */       
/* 1911 */       paramArrayOfint[i] = k;
/*      */ 
/*      */       
/* 1914 */       if (paramArrayOfdouble[b + 4 * i + i] == 0.0D) {
/* 1915 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1919 */       if (i != 3) {
/* 1920 */         double d1 = 1.0D / paramArrayOfdouble[b + 4 * i + i];
/* 1921 */         int m = b + 4 * (i + 1) + i;
/* 1922 */         j = 3 - i;
/* 1923 */         while (j-- != 0) {
/* 1924 */           paramArrayOfdouble[m] = paramArrayOfdouble[m] * d1;
/* 1925 */           m += 4;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1931 */     return true;
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
/* 1961 */     byte b2 = 0;
/*      */ 
/*      */     
/* 1964 */     for (byte b1 = 0; b1 < 4; b1++) {
/*      */       
/* 1966 */       byte b4 = b1;
/* 1967 */       byte b = -1;
/*      */ 
/*      */       
/* 1970 */       for (byte b3 = 0; b3 < 4; b3++) {
/*      */ 
/*      */         
/* 1973 */         int i = paramArrayOfint[b2 + b3];
/* 1974 */         double d = paramArrayOfdouble2[b4 + 4 * i];
/* 1975 */         paramArrayOfdouble2[b4 + 4 * i] = paramArrayOfdouble2[b4 + 4 * b3];
/* 1976 */         if (b >= 0) {
/*      */           
/* 1978 */           int j = b3 * 4;
/* 1979 */           for (byte b6 = b; b6 <= b3 - 1; b6++) {
/* 1980 */             d -= paramArrayOfdouble1[j + b6] * paramArrayOfdouble2[b4 + 4 * b6];
/*      */           }
/*      */         }
/* 1983 */         else if (d != 0.0D) {
/* 1984 */           b = b3;
/*      */         } 
/* 1986 */         paramArrayOfdouble2[b4 + 4 * b3] = d;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1991 */       byte b5 = 12;
/* 1992 */       paramArrayOfdouble2[b4 + 12] = paramArrayOfdouble2[b4 + 12] / paramArrayOfdouble1[b5 + 3];
/*      */       
/* 1994 */       b5 -= 4;
/* 1995 */       paramArrayOfdouble2[b4 + 8] = (paramArrayOfdouble2[b4 + 8] - paramArrayOfdouble1[b5 + 3] * paramArrayOfdouble2[b4 + 12]) / paramArrayOfdouble1[b5 + 2];
/*      */ 
/*      */       
/* 1998 */       b5 -= 4;
/* 1999 */       paramArrayOfdouble2[b4 + 4] = (paramArrayOfdouble2[b4 + 4] - paramArrayOfdouble1[b5 + 2] * paramArrayOfdouble2[b4 + 8] - paramArrayOfdouble1[b5 + 3] * paramArrayOfdouble2[b4 + 12]) / paramArrayOfdouble1[b5 + 1];
/*      */ 
/*      */ 
/*      */       
/* 2003 */       b5 -= 4;
/* 2004 */       paramArrayOfdouble2[b4 + 0] = (paramArrayOfdouble2[b4 + 0] - paramArrayOfdouble1[b5 + 1] * paramArrayOfdouble2[b4 + 4] - paramArrayOfdouble1[b5 + 2] * paramArrayOfdouble2[b4 + 8] - paramArrayOfdouble1[b5 + 3] * paramArrayOfdouble2[b4 + 12]) / paramArrayOfdouble1[b5 + 0];
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
/*      */   public final float determinant() {
/* 2021 */     float f = this.m00 * (this.m11 * this.m22 * this.m33 + this.m12 * this.m23 * this.m31 + this.m13 * this.m21 * this.m32 - this.m13 * this.m22 * this.m31 - this.m11 * this.m23 * this.m32 - this.m12 * this.m21 * this.m33);
/*      */     
/* 2023 */     f -= this.m01 * (this.m10 * this.m22 * this.m33 + this.m12 * this.m23 * this.m30 + this.m13 * this.m20 * this.m32 - this.m13 * this.m22 * this.m30 - this.m10 * this.m23 * this.m32 - this.m12 * this.m20 * this.m33);
/*      */     
/* 2025 */     f += this.m02 * (this.m10 * this.m21 * this.m33 + this.m11 * this.m23 * this.m30 + this.m13 * this.m20 * this.m31 - this.m13 * this.m21 * this.m30 - this.m10 * this.m23 * this.m31 - this.m11 * this.m20 * this.m33);
/*      */     
/* 2027 */     f -= this.m03 * (this.m10 * this.m21 * this.m32 + this.m11 * this.m22 * this.m30 + this.m12 * this.m20 * this.m31 - this.m12 * this.m21 * this.m30 - this.m10 * this.m22 * this.m31 - this.m11 * this.m20 * this.m32);
/*      */ 
/*      */     
/* 2030 */     return f;
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
/* 2042 */     this.m00 = paramMatrix3f.m00; this.m01 = paramMatrix3f.m01; this.m02 = paramMatrix3f.m02; this.m03 = 0.0F;
/* 2043 */     this.m10 = paramMatrix3f.m10; this.m11 = paramMatrix3f.m11; this.m12 = paramMatrix3f.m12; this.m13 = 0.0F;
/* 2044 */     this.m20 = paramMatrix3f.m20; this.m21 = paramMatrix3f.m21; this.m22 = paramMatrix3f.m22; this.m23 = 0.0F;
/* 2045 */     this.m30 = 0.0F; this.m31 = 0.0F; this.m32 = 0.0F; this.m33 = 1.0F;
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
/* 2057 */     this.m00 = (float)paramMatrix3d.m00; this.m01 = (float)paramMatrix3d.m01; this.m02 = (float)paramMatrix3d.m02; this.m03 = 0.0F;
/* 2058 */     this.m10 = (float)paramMatrix3d.m10; this.m11 = (float)paramMatrix3d.m11; this.m12 = (float)paramMatrix3d.m12; this.m13 = 0.0F;
/* 2059 */     this.m20 = (float)paramMatrix3d.m20; this.m21 = (float)paramMatrix3d.m21; this.m22 = (float)paramMatrix3d.m22; this.m23 = 0.0F;
/* 2060 */     this.m30 = 0.0F; this.m31 = 0.0F; this.m32 = 0.0F; this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(float paramFloat) {
/* 2070 */     this.m00 = paramFloat;
/* 2071 */     this.m01 = 0.0F;
/* 2072 */     this.m02 = 0.0F;
/* 2073 */     this.m03 = 0.0F;
/*      */     
/* 2075 */     this.m10 = 0.0F;
/* 2076 */     this.m11 = paramFloat;
/* 2077 */     this.m12 = 0.0F;
/* 2078 */     this.m13 = 0.0F;
/*      */     
/* 2080 */     this.m20 = 0.0F;
/* 2081 */     this.m21 = 0.0F;
/* 2082 */     this.m22 = paramFloat;
/* 2083 */     this.m23 = 0.0F;
/*      */     
/* 2085 */     this.m30 = 0.0F;
/* 2086 */     this.m31 = 0.0F;
/* 2087 */     this.m32 = 0.0F;
/* 2088 */     this.m33 = 1.0F;
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
/* 2099 */     this.m00 = paramArrayOffloat[0];
/* 2100 */     this.m01 = paramArrayOffloat[1];
/* 2101 */     this.m02 = paramArrayOffloat[2];
/* 2102 */     this.m03 = paramArrayOffloat[3];
/* 2103 */     this.m10 = paramArrayOffloat[4];
/* 2104 */     this.m11 = paramArrayOffloat[5];
/* 2105 */     this.m12 = paramArrayOffloat[6];
/* 2106 */     this.m13 = paramArrayOffloat[7];
/* 2107 */     this.m20 = paramArrayOffloat[8];
/* 2108 */     this.m21 = paramArrayOffloat[9];
/* 2109 */     this.m22 = paramArrayOffloat[10];
/* 2110 */     this.m23 = paramArrayOffloat[11];
/* 2111 */     this.m30 = paramArrayOffloat[12];
/* 2112 */     this.m31 = paramArrayOffloat[13];
/* 2113 */     this.m32 = paramArrayOffloat[14];
/* 2114 */     this.m33 = paramArrayOffloat[15];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Vector3f paramVector3f) {
/* 2124 */     this.m00 = 1.0F;
/* 2125 */     this.m01 = 0.0F;
/* 2126 */     this.m02 = 0.0F;
/* 2127 */     this.m03 = paramVector3f.x;
/*      */     
/* 2129 */     this.m10 = 0.0F;
/* 2130 */     this.m11 = 1.0F;
/* 2131 */     this.m12 = 0.0F;
/* 2132 */     this.m13 = paramVector3f.y;
/*      */     
/* 2134 */     this.m20 = 0.0F;
/* 2135 */     this.m21 = 0.0F;
/* 2136 */     this.m22 = 1.0F;
/* 2137 */     this.m23 = paramVector3f.z;
/*      */     
/* 2139 */     this.m30 = 0.0F;
/* 2140 */     this.m31 = 0.0F;
/* 2141 */     this.m32 = 0.0F;
/* 2142 */     this.m33 = 1.0F;
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
/*      */   public final void set(float paramFloat, Vector3f paramVector3f) {
/* 2154 */     this.m00 = paramFloat;
/* 2155 */     this.m01 = 0.0F;
/* 2156 */     this.m02 = 0.0F;
/* 2157 */     this.m03 = paramVector3f.x;
/*      */     
/* 2159 */     this.m10 = 0.0F;
/* 2160 */     this.m11 = paramFloat;
/* 2161 */     this.m12 = 0.0F;
/* 2162 */     this.m13 = paramVector3f.y;
/*      */     
/* 2164 */     this.m20 = 0.0F;
/* 2165 */     this.m21 = 0.0F;
/* 2166 */     this.m22 = paramFloat;
/* 2167 */     this.m23 = paramVector3f.z;
/*      */     
/* 2169 */     this.m30 = 0.0F;
/* 2170 */     this.m31 = 0.0F;
/* 2171 */     this.m32 = 0.0F;
/* 2172 */     this.m33 = 1.0F;
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
/*      */   public final void set(Vector3f paramVector3f, float paramFloat) {
/* 2184 */     this.m00 = paramFloat;
/* 2185 */     this.m01 = 0.0F;
/* 2186 */     this.m02 = 0.0F;
/* 2187 */     this.m03 = paramFloat * paramVector3f.x;
/*      */     
/* 2189 */     this.m10 = 0.0F;
/* 2190 */     this.m11 = paramFloat;
/* 2191 */     this.m12 = 0.0F;
/* 2192 */     this.m13 = paramFloat * paramVector3f.y;
/*      */     
/* 2194 */     this.m20 = 0.0F;
/* 2195 */     this.m21 = 0.0F;
/* 2196 */     this.m22 = paramFloat;
/* 2197 */     this.m23 = paramFloat * paramVector3f.z;
/*      */     
/* 2199 */     this.m30 = 0.0F;
/* 2200 */     this.m31 = 0.0F;
/* 2201 */     this.m32 = 0.0F;
/* 2202 */     this.m33 = 1.0F;
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
/*      */   public final void set(Matrix3f paramMatrix3f, Vector3f paramVector3f, float paramFloat) {
/* 2215 */     this.m00 = paramMatrix3f.m00 * paramFloat;
/* 2216 */     this.m01 = paramMatrix3f.m01 * paramFloat;
/* 2217 */     this.m02 = paramMatrix3f.m02 * paramFloat;
/* 2218 */     this.m03 = paramVector3f.x;
/*      */     
/* 2220 */     this.m10 = paramMatrix3f.m10 * paramFloat;
/* 2221 */     this.m11 = paramMatrix3f.m11 * paramFloat;
/* 2222 */     this.m12 = paramMatrix3f.m12 * paramFloat;
/* 2223 */     this.m13 = paramVector3f.y;
/*      */     
/* 2225 */     this.m20 = paramMatrix3f.m20 * paramFloat;
/* 2226 */     this.m21 = paramMatrix3f.m21 * paramFloat;
/* 2227 */     this.m22 = paramMatrix3f.m22 * paramFloat;
/* 2228 */     this.m23 = paramVector3f.z;
/*      */     
/* 2230 */     this.m30 = 0.0F;
/* 2231 */     this.m31 = 0.0F;
/* 2232 */     this.m32 = 0.0F;
/* 2233 */     this.m33 = 1.0F;
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
/*      */   public final void set(Matrix3d paramMatrix3d, Vector3d paramVector3d, double paramDouble) {
/* 2246 */     this.m00 = (float)(paramMatrix3d.m00 * paramDouble);
/* 2247 */     this.m01 = (float)(paramMatrix3d.m01 * paramDouble);
/* 2248 */     this.m02 = (float)(paramMatrix3d.m02 * paramDouble);
/* 2249 */     this.m03 = (float)paramVector3d.x;
/*      */     
/* 2251 */     this.m10 = (float)(paramMatrix3d.m10 * paramDouble);
/* 2252 */     this.m11 = (float)(paramMatrix3d.m11 * paramDouble);
/* 2253 */     this.m12 = (float)(paramMatrix3d.m12 * paramDouble);
/* 2254 */     this.m13 = (float)paramVector3d.y;
/*      */     
/* 2256 */     this.m20 = (float)(paramMatrix3d.m20 * paramDouble);
/* 2257 */     this.m21 = (float)(paramMatrix3d.m21 * paramDouble);
/* 2258 */     this.m22 = (float)(paramMatrix3d.m22 * paramDouble);
/* 2259 */     this.m23 = (float)paramVector3d.z;
/*      */     
/* 2261 */     this.m30 = 0.0F;
/* 2262 */     this.m31 = 0.0F;
/* 2263 */     this.m32 = 0.0F;
/* 2264 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setTranslation(Vector3f paramVector3f) {
/* 2275 */     this.m03 = paramVector3f.x;
/* 2276 */     this.m13 = paramVector3f.y;
/* 2277 */     this.m23 = paramVector3f.z;
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
/*      */   public final void rotX(float paramFloat) {
/* 2290 */     float f1 = (float)Math.sin(paramFloat);
/* 2291 */     float f2 = (float)Math.cos(paramFloat);
/*      */     
/* 2293 */     this.m00 = 1.0F;
/* 2294 */     this.m01 = 0.0F;
/* 2295 */     this.m02 = 0.0F;
/* 2296 */     this.m03 = 0.0F;
/*      */     
/* 2298 */     this.m10 = 0.0F;
/* 2299 */     this.m11 = f2;
/* 2300 */     this.m12 = -f1;
/* 2301 */     this.m13 = 0.0F;
/*      */     
/* 2303 */     this.m20 = 0.0F;
/* 2304 */     this.m21 = f1;
/* 2305 */     this.m22 = f2;
/* 2306 */     this.m23 = 0.0F;
/*      */     
/* 2308 */     this.m30 = 0.0F;
/* 2309 */     this.m31 = 0.0F;
/* 2310 */     this.m32 = 0.0F;
/* 2311 */     this.m33 = 1.0F;
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
/* 2323 */     float f1 = (float)Math.sin(paramFloat);
/* 2324 */     float f2 = (float)Math.cos(paramFloat);
/*      */     
/* 2326 */     this.m00 = f2;
/* 2327 */     this.m01 = 0.0F;
/* 2328 */     this.m02 = f1;
/* 2329 */     this.m03 = 0.0F;
/*      */     
/* 2331 */     this.m10 = 0.0F;
/* 2332 */     this.m11 = 1.0F;
/* 2333 */     this.m12 = 0.0F;
/* 2334 */     this.m13 = 0.0F;
/*      */     
/* 2336 */     this.m20 = -f1;
/* 2337 */     this.m21 = 0.0F;
/* 2338 */     this.m22 = f2;
/* 2339 */     this.m23 = 0.0F;
/*      */     
/* 2341 */     this.m30 = 0.0F;
/* 2342 */     this.m31 = 0.0F;
/* 2343 */     this.m32 = 0.0F;
/* 2344 */     this.m33 = 1.0F;
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
/* 2356 */     float f1 = (float)Math.sin(paramFloat);
/* 2357 */     float f2 = (float)Math.cos(paramFloat);
/*      */     
/* 2359 */     this.m00 = f2;
/* 2360 */     this.m01 = -f1;
/* 2361 */     this.m02 = 0.0F;
/* 2362 */     this.m03 = 0.0F;
/*      */     
/* 2364 */     this.m10 = f1;
/* 2365 */     this.m11 = f2;
/* 2366 */     this.m12 = 0.0F;
/* 2367 */     this.m13 = 0.0F;
/*      */     
/* 2369 */     this.m20 = 0.0F;
/* 2370 */     this.m21 = 0.0F;
/* 2371 */     this.m22 = 1.0F;
/* 2372 */     this.m23 = 0.0F;
/*      */     
/* 2374 */     this.m30 = 0.0F;
/* 2375 */     this.m31 = 0.0F;
/* 2376 */     this.m32 = 0.0F;
/* 2377 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(float paramFloat) {
/* 2386 */     this.m00 *= paramFloat;
/* 2387 */     this.m01 *= paramFloat;
/* 2388 */     this.m02 *= paramFloat;
/* 2389 */     this.m03 *= paramFloat;
/* 2390 */     this.m10 *= paramFloat;
/* 2391 */     this.m11 *= paramFloat;
/* 2392 */     this.m12 *= paramFloat;
/* 2393 */     this.m13 *= paramFloat;
/* 2394 */     this.m20 *= paramFloat;
/* 2395 */     this.m21 *= paramFloat;
/* 2396 */     this.m22 *= paramFloat;
/* 2397 */     this.m23 *= paramFloat;
/* 2398 */     this.m30 *= paramFloat;
/* 2399 */     this.m31 *= paramFloat;
/* 2400 */     this.m32 *= paramFloat;
/* 2401 */     this.m33 *= paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(float paramFloat, Matrix4f paramMatrix4f) {
/* 2412 */     paramMatrix4f.m00 *= paramFloat;
/* 2413 */     paramMatrix4f.m01 *= paramFloat;
/* 2414 */     paramMatrix4f.m02 *= paramFloat;
/* 2415 */     paramMatrix4f.m03 *= paramFloat;
/* 2416 */     paramMatrix4f.m10 *= paramFloat;
/* 2417 */     paramMatrix4f.m11 *= paramFloat;
/* 2418 */     paramMatrix4f.m12 *= paramFloat;
/* 2419 */     paramMatrix4f.m13 *= paramFloat;
/* 2420 */     paramMatrix4f.m20 *= paramFloat;
/* 2421 */     paramMatrix4f.m21 *= paramFloat;
/* 2422 */     paramMatrix4f.m22 *= paramFloat;
/* 2423 */     paramMatrix4f.m23 *= paramFloat;
/* 2424 */     paramMatrix4f.m30 *= paramFloat;
/* 2425 */     paramMatrix4f.m31 *= paramFloat;
/* 2426 */     paramMatrix4f.m32 *= paramFloat;
/* 2427 */     paramMatrix4f.m33 *= paramFloat;
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
/*      */   public final void mul(Matrix4f paramMatrix4f) {
/* 2442 */     float f1 = this.m00 * paramMatrix4f.m00 + this.m01 * paramMatrix4f.m10 + this.m02 * paramMatrix4f.m20 + this.m03 * paramMatrix4f.m30;
/*      */     
/* 2444 */     float f2 = this.m00 * paramMatrix4f.m01 + this.m01 * paramMatrix4f.m11 + this.m02 * paramMatrix4f.m21 + this.m03 * paramMatrix4f.m31;
/*      */     
/* 2446 */     float f3 = this.m00 * paramMatrix4f.m02 + this.m01 * paramMatrix4f.m12 + this.m02 * paramMatrix4f.m22 + this.m03 * paramMatrix4f.m32;
/*      */     
/* 2448 */     float f4 = this.m00 * paramMatrix4f.m03 + this.m01 * paramMatrix4f.m13 + this.m02 * paramMatrix4f.m23 + this.m03 * paramMatrix4f.m33;
/*      */ 
/*      */     
/* 2451 */     float f5 = this.m10 * paramMatrix4f.m00 + this.m11 * paramMatrix4f.m10 + this.m12 * paramMatrix4f.m20 + this.m13 * paramMatrix4f.m30;
/*      */     
/* 2453 */     float f6 = this.m10 * paramMatrix4f.m01 + this.m11 * paramMatrix4f.m11 + this.m12 * paramMatrix4f.m21 + this.m13 * paramMatrix4f.m31;
/*      */     
/* 2455 */     float f7 = this.m10 * paramMatrix4f.m02 + this.m11 * paramMatrix4f.m12 + this.m12 * paramMatrix4f.m22 + this.m13 * paramMatrix4f.m32;
/*      */     
/* 2457 */     float f8 = this.m10 * paramMatrix4f.m03 + this.m11 * paramMatrix4f.m13 + this.m12 * paramMatrix4f.m23 + this.m13 * paramMatrix4f.m33;
/*      */ 
/*      */     
/* 2460 */     float f9 = this.m20 * paramMatrix4f.m00 + this.m21 * paramMatrix4f.m10 + this.m22 * paramMatrix4f.m20 + this.m23 * paramMatrix4f.m30;
/*      */     
/* 2462 */     float f10 = this.m20 * paramMatrix4f.m01 + this.m21 * paramMatrix4f.m11 + this.m22 * paramMatrix4f.m21 + this.m23 * paramMatrix4f.m31;
/*      */     
/* 2464 */     float f11 = this.m20 * paramMatrix4f.m02 + this.m21 * paramMatrix4f.m12 + this.m22 * paramMatrix4f.m22 + this.m23 * paramMatrix4f.m32;
/*      */     
/* 2466 */     float f12 = this.m20 * paramMatrix4f.m03 + this.m21 * paramMatrix4f.m13 + this.m22 * paramMatrix4f.m23 + this.m23 * paramMatrix4f.m33;
/*      */ 
/*      */     
/* 2469 */     float f13 = this.m30 * paramMatrix4f.m00 + this.m31 * paramMatrix4f.m10 + this.m32 * paramMatrix4f.m20 + this.m33 * paramMatrix4f.m30;
/*      */     
/* 2471 */     float f14 = this.m30 * paramMatrix4f.m01 + this.m31 * paramMatrix4f.m11 + this.m32 * paramMatrix4f.m21 + this.m33 * paramMatrix4f.m31;
/*      */     
/* 2473 */     float f15 = this.m30 * paramMatrix4f.m02 + this.m31 * paramMatrix4f.m12 + this.m32 * paramMatrix4f.m22 + this.m33 * paramMatrix4f.m32;
/*      */     
/* 2475 */     float f16 = this.m30 * paramMatrix4f.m03 + this.m31 * paramMatrix4f.m13 + this.m32 * paramMatrix4f.m23 + this.m33 * paramMatrix4f.m33;
/*      */ 
/*      */     
/* 2478 */     this.m00 = f1; this.m01 = f2; this.m02 = f3; this.m03 = f4;
/* 2479 */     this.m10 = f5; this.m11 = f6; this.m12 = f7; this.m13 = f8;
/* 2480 */     this.m20 = f9; this.m21 = f10; this.m22 = f11; this.m23 = f12;
/* 2481 */     this.m30 = f13; this.m31 = f14; this.m32 = f15; this.m33 = f16;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(Matrix4f paramMatrix4f1, Matrix4f paramMatrix4f2) {
/* 2492 */     if (this != paramMatrix4f1 && this != paramMatrix4f2) {
/*      */       
/* 2494 */       this.m00 = paramMatrix4f1.m00 * paramMatrix4f2.m00 + paramMatrix4f1.m01 * paramMatrix4f2.m10 + paramMatrix4f1.m02 * paramMatrix4f2.m20 + paramMatrix4f1.m03 * paramMatrix4f2.m30;
/*      */       
/* 2496 */       this.m01 = paramMatrix4f1.m00 * paramMatrix4f2.m01 + paramMatrix4f1.m01 * paramMatrix4f2.m11 + paramMatrix4f1.m02 * paramMatrix4f2.m21 + paramMatrix4f1.m03 * paramMatrix4f2.m31;
/*      */       
/* 2498 */       this.m02 = paramMatrix4f1.m00 * paramMatrix4f2.m02 + paramMatrix4f1.m01 * paramMatrix4f2.m12 + paramMatrix4f1.m02 * paramMatrix4f2.m22 + paramMatrix4f1.m03 * paramMatrix4f2.m32;
/*      */       
/* 2500 */       this.m03 = paramMatrix4f1.m00 * paramMatrix4f2.m03 + paramMatrix4f1.m01 * paramMatrix4f2.m13 + paramMatrix4f1.m02 * paramMatrix4f2.m23 + paramMatrix4f1.m03 * paramMatrix4f2.m33;
/*      */ 
/*      */       
/* 2503 */       this.m10 = paramMatrix4f1.m10 * paramMatrix4f2.m00 + paramMatrix4f1.m11 * paramMatrix4f2.m10 + paramMatrix4f1.m12 * paramMatrix4f2.m20 + paramMatrix4f1.m13 * paramMatrix4f2.m30;
/*      */       
/* 2505 */       this.m11 = paramMatrix4f1.m10 * paramMatrix4f2.m01 + paramMatrix4f1.m11 * paramMatrix4f2.m11 + paramMatrix4f1.m12 * paramMatrix4f2.m21 + paramMatrix4f1.m13 * paramMatrix4f2.m31;
/*      */       
/* 2507 */       this.m12 = paramMatrix4f1.m10 * paramMatrix4f2.m02 + paramMatrix4f1.m11 * paramMatrix4f2.m12 + paramMatrix4f1.m12 * paramMatrix4f2.m22 + paramMatrix4f1.m13 * paramMatrix4f2.m32;
/*      */       
/* 2509 */       this.m13 = paramMatrix4f1.m10 * paramMatrix4f2.m03 + paramMatrix4f1.m11 * paramMatrix4f2.m13 + paramMatrix4f1.m12 * paramMatrix4f2.m23 + paramMatrix4f1.m13 * paramMatrix4f2.m33;
/*      */ 
/*      */       
/* 2512 */       this.m20 = paramMatrix4f1.m20 * paramMatrix4f2.m00 + paramMatrix4f1.m21 * paramMatrix4f2.m10 + paramMatrix4f1.m22 * paramMatrix4f2.m20 + paramMatrix4f1.m23 * paramMatrix4f2.m30;
/*      */       
/* 2514 */       this.m21 = paramMatrix4f1.m20 * paramMatrix4f2.m01 + paramMatrix4f1.m21 * paramMatrix4f2.m11 + paramMatrix4f1.m22 * paramMatrix4f2.m21 + paramMatrix4f1.m23 * paramMatrix4f2.m31;
/*      */       
/* 2516 */       this.m22 = paramMatrix4f1.m20 * paramMatrix4f2.m02 + paramMatrix4f1.m21 * paramMatrix4f2.m12 + paramMatrix4f1.m22 * paramMatrix4f2.m22 + paramMatrix4f1.m23 * paramMatrix4f2.m32;
/*      */       
/* 2518 */       this.m23 = paramMatrix4f1.m20 * paramMatrix4f2.m03 + paramMatrix4f1.m21 * paramMatrix4f2.m13 + paramMatrix4f1.m22 * paramMatrix4f2.m23 + paramMatrix4f1.m23 * paramMatrix4f2.m33;
/*      */ 
/*      */       
/* 2521 */       this.m30 = paramMatrix4f1.m30 * paramMatrix4f2.m00 + paramMatrix4f1.m31 * paramMatrix4f2.m10 + paramMatrix4f1.m32 * paramMatrix4f2.m20 + paramMatrix4f1.m33 * paramMatrix4f2.m30;
/*      */       
/* 2523 */       this.m31 = paramMatrix4f1.m30 * paramMatrix4f2.m01 + paramMatrix4f1.m31 * paramMatrix4f2.m11 + paramMatrix4f1.m32 * paramMatrix4f2.m21 + paramMatrix4f1.m33 * paramMatrix4f2.m31;
/*      */       
/* 2525 */       this.m32 = paramMatrix4f1.m30 * paramMatrix4f2.m02 + paramMatrix4f1.m31 * paramMatrix4f2.m12 + paramMatrix4f1.m32 * paramMatrix4f2.m22 + paramMatrix4f1.m33 * paramMatrix4f2.m32;
/*      */       
/* 2527 */       this.m33 = paramMatrix4f1.m30 * paramMatrix4f2.m03 + paramMatrix4f1.m31 * paramMatrix4f2.m13 + paramMatrix4f1.m32 * paramMatrix4f2.m23 + paramMatrix4f1.m33 * paramMatrix4f2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2534 */       float f1 = paramMatrix4f1.m00 * paramMatrix4f2.m00 + paramMatrix4f1.m01 * paramMatrix4f2.m10 + paramMatrix4f1.m02 * paramMatrix4f2.m20 + paramMatrix4f1.m03 * paramMatrix4f2.m30;
/* 2535 */       float f2 = paramMatrix4f1.m00 * paramMatrix4f2.m01 + paramMatrix4f1.m01 * paramMatrix4f2.m11 + paramMatrix4f1.m02 * paramMatrix4f2.m21 + paramMatrix4f1.m03 * paramMatrix4f2.m31;
/* 2536 */       float f3 = paramMatrix4f1.m00 * paramMatrix4f2.m02 + paramMatrix4f1.m01 * paramMatrix4f2.m12 + paramMatrix4f1.m02 * paramMatrix4f2.m22 + paramMatrix4f1.m03 * paramMatrix4f2.m32;
/* 2537 */       float f4 = paramMatrix4f1.m00 * paramMatrix4f2.m03 + paramMatrix4f1.m01 * paramMatrix4f2.m13 + paramMatrix4f1.m02 * paramMatrix4f2.m23 + paramMatrix4f1.m03 * paramMatrix4f2.m33;
/*      */       
/* 2539 */       float f5 = paramMatrix4f1.m10 * paramMatrix4f2.m00 + paramMatrix4f1.m11 * paramMatrix4f2.m10 + paramMatrix4f1.m12 * paramMatrix4f2.m20 + paramMatrix4f1.m13 * paramMatrix4f2.m30;
/* 2540 */       float f6 = paramMatrix4f1.m10 * paramMatrix4f2.m01 + paramMatrix4f1.m11 * paramMatrix4f2.m11 + paramMatrix4f1.m12 * paramMatrix4f2.m21 + paramMatrix4f1.m13 * paramMatrix4f2.m31;
/* 2541 */       float f7 = paramMatrix4f1.m10 * paramMatrix4f2.m02 + paramMatrix4f1.m11 * paramMatrix4f2.m12 + paramMatrix4f1.m12 * paramMatrix4f2.m22 + paramMatrix4f1.m13 * paramMatrix4f2.m32;
/* 2542 */       float f8 = paramMatrix4f1.m10 * paramMatrix4f2.m03 + paramMatrix4f1.m11 * paramMatrix4f2.m13 + paramMatrix4f1.m12 * paramMatrix4f2.m23 + paramMatrix4f1.m13 * paramMatrix4f2.m33;
/*      */       
/* 2544 */       float f9 = paramMatrix4f1.m20 * paramMatrix4f2.m00 + paramMatrix4f1.m21 * paramMatrix4f2.m10 + paramMatrix4f1.m22 * paramMatrix4f2.m20 + paramMatrix4f1.m23 * paramMatrix4f2.m30;
/* 2545 */       float f10 = paramMatrix4f1.m20 * paramMatrix4f2.m01 + paramMatrix4f1.m21 * paramMatrix4f2.m11 + paramMatrix4f1.m22 * paramMatrix4f2.m21 + paramMatrix4f1.m23 * paramMatrix4f2.m31;
/* 2546 */       float f11 = paramMatrix4f1.m20 * paramMatrix4f2.m02 + paramMatrix4f1.m21 * paramMatrix4f2.m12 + paramMatrix4f1.m22 * paramMatrix4f2.m22 + paramMatrix4f1.m23 * paramMatrix4f2.m32;
/* 2547 */       float f12 = paramMatrix4f1.m20 * paramMatrix4f2.m03 + paramMatrix4f1.m21 * paramMatrix4f2.m13 + paramMatrix4f1.m22 * paramMatrix4f2.m23 + paramMatrix4f1.m23 * paramMatrix4f2.m33;
/*      */       
/* 2549 */       float f13 = paramMatrix4f1.m30 * paramMatrix4f2.m00 + paramMatrix4f1.m31 * paramMatrix4f2.m10 + paramMatrix4f1.m32 * paramMatrix4f2.m20 + paramMatrix4f1.m33 * paramMatrix4f2.m30;
/* 2550 */       float f14 = paramMatrix4f1.m30 * paramMatrix4f2.m01 + paramMatrix4f1.m31 * paramMatrix4f2.m11 + paramMatrix4f1.m32 * paramMatrix4f2.m21 + paramMatrix4f1.m33 * paramMatrix4f2.m31;
/* 2551 */       float f15 = paramMatrix4f1.m30 * paramMatrix4f2.m02 + paramMatrix4f1.m31 * paramMatrix4f2.m12 + paramMatrix4f1.m32 * paramMatrix4f2.m22 + paramMatrix4f1.m33 * paramMatrix4f2.m32;
/* 2552 */       float f16 = paramMatrix4f1.m30 * paramMatrix4f2.m03 + paramMatrix4f1.m31 * paramMatrix4f2.m13 + paramMatrix4f1.m32 * paramMatrix4f2.m23 + paramMatrix4f1.m33 * paramMatrix4f2.m33;
/*      */       
/* 2554 */       this.m00 = f1; this.m01 = f2; this.m02 = f3; this.m03 = f4;
/* 2555 */       this.m10 = f5; this.m11 = f6; this.m12 = f7; this.m13 = f8;
/* 2556 */       this.m20 = f9; this.m21 = f10; this.m22 = f11; this.m23 = f12;
/* 2557 */       this.m30 = f13; this.m31 = f14; this.m32 = f15; this.m33 = f16;
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
/*      */   public final void mulTransposeBoth(Matrix4f paramMatrix4f1, Matrix4f paramMatrix4f2) {
/* 2569 */     if (this != paramMatrix4f1 && this != paramMatrix4f2) {
/* 2570 */       this.m00 = paramMatrix4f1.m00 * paramMatrix4f2.m00 + paramMatrix4f1.m10 * paramMatrix4f2.m01 + paramMatrix4f1.m20 * paramMatrix4f2.m02 + paramMatrix4f1.m30 * paramMatrix4f2.m03;
/* 2571 */       this.m01 = paramMatrix4f1.m00 * paramMatrix4f2.m10 + paramMatrix4f1.m10 * paramMatrix4f2.m11 + paramMatrix4f1.m20 * paramMatrix4f2.m12 + paramMatrix4f1.m30 * paramMatrix4f2.m13;
/* 2572 */       this.m02 = paramMatrix4f1.m00 * paramMatrix4f2.m20 + paramMatrix4f1.m10 * paramMatrix4f2.m21 + paramMatrix4f1.m20 * paramMatrix4f2.m22 + paramMatrix4f1.m30 * paramMatrix4f2.m23;
/* 2573 */       this.m03 = paramMatrix4f1.m00 * paramMatrix4f2.m30 + paramMatrix4f1.m10 * paramMatrix4f2.m31 + paramMatrix4f1.m20 * paramMatrix4f2.m32 + paramMatrix4f1.m30 * paramMatrix4f2.m33;
/*      */       
/* 2575 */       this.m10 = paramMatrix4f1.m01 * paramMatrix4f2.m00 + paramMatrix4f1.m11 * paramMatrix4f2.m01 + paramMatrix4f1.m21 * paramMatrix4f2.m02 + paramMatrix4f1.m31 * paramMatrix4f2.m03;
/* 2576 */       this.m11 = paramMatrix4f1.m01 * paramMatrix4f2.m10 + paramMatrix4f1.m11 * paramMatrix4f2.m11 + paramMatrix4f1.m21 * paramMatrix4f2.m12 + paramMatrix4f1.m31 * paramMatrix4f2.m13;
/* 2577 */       this.m12 = paramMatrix4f1.m01 * paramMatrix4f2.m20 + paramMatrix4f1.m11 * paramMatrix4f2.m21 + paramMatrix4f1.m21 * paramMatrix4f2.m22 + paramMatrix4f1.m31 * paramMatrix4f2.m23;
/* 2578 */       this.m13 = paramMatrix4f1.m01 * paramMatrix4f2.m30 + paramMatrix4f1.m11 * paramMatrix4f2.m31 + paramMatrix4f1.m21 * paramMatrix4f2.m32 + paramMatrix4f1.m31 * paramMatrix4f2.m33;
/*      */       
/* 2580 */       this.m20 = paramMatrix4f1.m02 * paramMatrix4f2.m00 + paramMatrix4f1.m12 * paramMatrix4f2.m01 + paramMatrix4f1.m22 * paramMatrix4f2.m02 + paramMatrix4f1.m32 * paramMatrix4f2.m03;
/* 2581 */       this.m21 = paramMatrix4f1.m02 * paramMatrix4f2.m10 + paramMatrix4f1.m12 * paramMatrix4f2.m11 + paramMatrix4f1.m22 * paramMatrix4f2.m12 + paramMatrix4f1.m32 * paramMatrix4f2.m13;
/* 2582 */       this.m22 = paramMatrix4f1.m02 * paramMatrix4f2.m20 + paramMatrix4f1.m12 * paramMatrix4f2.m21 + paramMatrix4f1.m22 * paramMatrix4f2.m22 + paramMatrix4f1.m32 * paramMatrix4f2.m23;
/* 2583 */       this.m23 = paramMatrix4f1.m02 * paramMatrix4f2.m30 + paramMatrix4f1.m12 * paramMatrix4f2.m31 + paramMatrix4f1.m22 * paramMatrix4f2.m32 + paramMatrix4f1.m32 * paramMatrix4f2.m33;
/*      */       
/* 2585 */       this.m30 = paramMatrix4f1.m03 * paramMatrix4f2.m00 + paramMatrix4f1.m13 * paramMatrix4f2.m01 + paramMatrix4f1.m23 * paramMatrix4f2.m02 + paramMatrix4f1.m33 * paramMatrix4f2.m03;
/* 2586 */       this.m31 = paramMatrix4f1.m03 * paramMatrix4f2.m10 + paramMatrix4f1.m13 * paramMatrix4f2.m11 + paramMatrix4f1.m23 * paramMatrix4f2.m12 + paramMatrix4f1.m33 * paramMatrix4f2.m13;
/* 2587 */       this.m32 = paramMatrix4f1.m03 * paramMatrix4f2.m20 + paramMatrix4f1.m13 * paramMatrix4f2.m21 + paramMatrix4f1.m23 * paramMatrix4f2.m22 + paramMatrix4f1.m33 * paramMatrix4f2.m23;
/* 2588 */       this.m33 = paramMatrix4f1.m03 * paramMatrix4f2.m30 + paramMatrix4f1.m13 * paramMatrix4f2.m31 + paramMatrix4f1.m23 * paramMatrix4f2.m32 + paramMatrix4f1.m33 * paramMatrix4f2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2595 */       float f1 = paramMatrix4f1.m00 * paramMatrix4f2.m00 + paramMatrix4f1.m10 * paramMatrix4f2.m01 + paramMatrix4f1.m20 * paramMatrix4f2.m02 + paramMatrix4f1.m30 * paramMatrix4f2.m03;
/* 2596 */       float f2 = paramMatrix4f1.m00 * paramMatrix4f2.m10 + paramMatrix4f1.m10 * paramMatrix4f2.m11 + paramMatrix4f1.m20 * paramMatrix4f2.m12 + paramMatrix4f1.m30 * paramMatrix4f2.m13;
/* 2597 */       float f3 = paramMatrix4f1.m00 * paramMatrix4f2.m20 + paramMatrix4f1.m10 * paramMatrix4f2.m21 + paramMatrix4f1.m20 * paramMatrix4f2.m22 + paramMatrix4f1.m30 * paramMatrix4f2.m23;
/* 2598 */       float f4 = paramMatrix4f1.m00 * paramMatrix4f2.m30 + paramMatrix4f1.m10 * paramMatrix4f2.m31 + paramMatrix4f1.m20 * paramMatrix4f2.m32 + paramMatrix4f1.m30 * paramMatrix4f2.m33;
/*      */       
/* 2600 */       float f5 = paramMatrix4f1.m01 * paramMatrix4f2.m00 + paramMatrix4f1.m11 * paramMatrix4f2.m01 + paramMatrix4f1.m21 * paramMatrix4f2.m02 + paramMatrix4f1.m31 * paramMatrix4f2.m03;
/* 2601 */       float f6 = paramMatrix4f1.m01 * paramMatrix4f2.m10 + paramMatrix4f1.m11 * paramMatrix4f2.m11 + paramMatrix4f1.m21 * paramMatrix4f2.m12 + paramMatrix4f1.m31 * paramMatrix4f2.m13;
/* 2602 */       float f7 = paramMatrix4f1.m01 * paramMatrix4f2.m20 + paramMatrix4f1.m11 * paramMatrix4f2.m21 + paramMatrix4f1.m21 * paramMatrix4f2.m22 + paramMatrix4f1.m31 * paramMatrix4f2.m23;
/* 2603 */       float f8 = paramMatrix4f1.m01 * paramMatrix4f2.m30 + paramMatrix4f1.m11 * paramMatrix4f2.m31 + paramMatrix4f1.m21 * paramMatrix4f2.m32 + paramMatrix4f1.m31 * paramMatrix4f2.m33;
/*      */       
/* 2605 */       float f9 = paramMatrix4f1.m02 * paramMatrix4f2.m00 + paramMatrix4f1.m12 * paramMatrix4f2.m01 + paramMatrix4f1.m22 * paramMatrix4f2.m02 + paramMatrix4f1.m32 * paramMatrix4f2.m03;
/* 2606 */       float f10 = paramMatrix4f1.m02 * paramMatrix4f2.m10 + paramMatrix4f1.m12 * paramMatrix4f2.m11 + paramMatrix4f1.m22 * paramMatrix4f2.m12 + paramMatrix4f1.m32 * paramMatrix4f2.m13;
/* 2607 */       float f11 = paramMatrix4f1.m02 * paramMatrix4f2.m20 + paramMatrix4f1.m12 * paramMatrix4f2.m21 + paramMatrix4f1.m22 * paramMatrix4f2.m22 + paramMatrix4f1.m32 * paramMatrix4f2.m23;
/* 2608 */       float f12 = paramMatrix4f1.m02 * paramMatrix4f2.m30 + paramMatrix4f1.m12 * paramMatrix4f2.m31 + paramMatrix4f1.m22 * paramMatrix4f2.m32 + paramMatrix4f1.m32 * paramMatrix4f2.m33;
/*      */       
/* 2610 */       float f13 = paramMatrix4f1.m03 * paramMatrix4f2.m00 + paramMatrix4f1.m13 * paramMatrix4f2.m01 + paramMatrix4f1.m23 * paramMatrix4f2.m02 + paramMatrix4f1.m33 * paramMatrix4f2.m03;
/* 2611 */       float f14 = paramMatrix4f1.m03 * paramMatrix4f2.m10 + paramMatrix4f1.m13 * paramMatrix4f2.m11 + paramMatrix4f1.m23 * paramMatrix4f2.m12 + paramMatrix4f1.m33 * paramMatrix4f2.m13;
/* 2612 */       float f15 = paramMatrix4f1.m03 * paramMatrix4f2.m20 + paramMatrix4f1.m13 * paramMatrix4f2.m21 + paramMatrix4f1.m23 * paramMatrix4f2.m22 + paramMatrix4f1.m33 * paramMatrix4f2.m23;
/* 2613 */       float f16 = paramMatrix4f1.m03 * paramMatrix4f2.m30 + paramMatrix4f1.m13 * paramMatrix4f2.m31 + paramMatrix4f1.m23 * paramMatrix4f2.m32 + paramMatrix4f1.m33 * paramMatrix4f2.m33;
/*      */       
/* 2615 */       this.m00 = f1; this.m01 = f2; this.m02 = f3; this.m03 = f4;
/* 2616 */       this.m10 = f5; this.m11 = f6; this.m12 = f7; this.m13 = f8;
/* 2617 */       this.m20 = f9; this.m21 = f10; this.m22 = f11; this.m23 = f12;
/* 2618 */       this.m30 = f13; this.m31 = f14; this.m32 = f15; this.m33 = f16;
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
/*      */   public final void mulTransposeRight(Matrix4f paramMatrix4f1, Matrix4f paramMatrix4f2) {
/* 2631 */     if (this != paramMatrix4f1 && this != paramMatrix4f2) {
/* 2632 */       this.m00 = paramMatrix4f1.m00 * paramMatrix4f2.m00 + paramMatrix4f1.m01 * paramMatrix4f2.m01 + paramMatrix4f1.m02 * paramMatrix4f2.m02 + paramMatrix4f1.m03 * paramMatrix4f2.m03;
/* 2633 */       this.m01 = paramMatrix4f1.m00 * paramMatrix4f2.m10 + paramMatrix4f1.m01 * paramMatrix4f2.m11 + paramMatrix4f1.m02 * paramMatrix4f2.m12 + paramMatrix4f1.m03 * paramMatrix4f2.m13;
/* 2634 */       this.m02 = paramMatrix4f1.m00 * paramMatrix4f2.m20 + paramMatrix4f1.m01 * paramMatrix4f2.m21 + paramMatrix4f1.m02 * paramMatrix4f2.m22 + paramMatrix4f1.m03 * paramMatrix4f2.m23;
/* 2635 */       this.m03 = paramMatrix4f1.m00 * paramMatrix4f2.m30 + paramMatrix4f1.m01 * paramMatrix4f2.m31 + paramMatrix4f1.m02 * paramMatrix4f2.m32 + paramMatrix4f1.m03 * paramMatrix4f2.m33;
/*      */       
/* 2637 */       this.m10 = paramMatrix4f1.m10 * paramMatrix4f2.m00 + paramMatrix4f1.m11 * paramMatrix4f2.m01 + paramMatrix4f1.m12 * paramMatrix4f2.m02 + paramMatrix4f1.m13 * paramMatrix4f2.m03;
/* 2638 */       this.m11 = paramMatrix4f1.m10 * paramMatrix4f2.m10 + paramMatrix4f1.m11 * paramMatrix4f2.m11 + paramMatrix4f1.m12 * paramMatrix4f2.m12 + paramMatrix4f1.m13 * paramMatrix4f2.m13;
/* 2639 */       this.m12 = paramMatrix4f1.m10 * paramMatrix4f2.m20 + paramMatrix4f1.m11 * paramMatrix4f2.m21 + paramMatrix4f1.m12 * paramMatrix4f2.m22 + paramMatrix4f1.m13 * paramMatrix4f2.m23;
/* 2640 */       this.m13 = paramMatrix4f1.m10 * paramMatrix4f2.m30 + paramMatrix4f1.m11 * paramMatrix4f2.m31 + paramMatrix4f1.m12 * paramMatrix4f2.m32 + paramMatrix4f1.m13 * paramMatrix4f2.m33;
/*      */       
/* 2642 */       this.m20 = paramMatrix4f1.m20 * paramMatrix4f2.m00 + paramMatrix4f1.m21 * paramMatrix4f2.m01 + paramMatrix4f1.m22 * paramMatrix4f2.m02 + paramMatrix4f1.m23 * paramMatrix4f2.m03;
/* 2643 */       this.m21 = paramMatrix4f1.m20 * paramMatrix4f2.m10 + paramMatrix4f1.m21 * paramMatrix4f2.m11 + paramMatrix4f1.m22 * paramMatrix4f2.m12 + paramMatrix4f1.m23 * paramMatrix4f2.m13;
/* 2644 */       this.m22 = paramMatrix4f1.m20 * paramMatrix4f2.m20 + paramMatrix4f1.m21 * paramMatrix4f2.m21 + paramMatrix4f1.m22 * paramMatrix4f2.m22 + paramMatrix4f1.m23 * paramMatrix4f2.m23;
/* 2645 */       this.m23 = paramMatrix4f1.m20 * paramMatrix4f2.m30 + paramMatrix4f1.m21 * paramMatrix4f2.m31 + paramMatrix4f1.m22 * paramMatrix4f2.m32 + paramMatrix4f1.m23 * paramMatrix4f2.m33;
/*      */       
/* 2647 */       this.m30 = paramMatrix4f1.m30 * paramMatrix4f2.m00 + paramMatrix4f1.m31 * paramMatrix4f2.m01 + paramMatrix4f1.m32 * paramMatrix4f2.m02 + paramMatrix4f1.m33 * paramMatrix4f2.m03;
/* 2648 */       this.m31 = paramMatrix4f1.m30 * paramMatrix4f2.m10 + paramMatrix4f1.m31 * paramMatrix4f2.m11 + paramMatrix4f1.m32 * paramMatrix4f2.m12 + paramMatrix4f1.m33 * paramMatrix4f2.m13;
/* 2649 */       this.m32 = paramMatrix4f1.m30 * paramMatrix4f2.m20 + paramMatrix4f1.m31 * paramMatrix4f2.m21 + paramMatrix4f1.m32 * paramMatrix4f2.m22 + paramMatrix4f1.m33 * paramMatrix4f2.m23;
/* 2650 */       this.m33 = paramMatrix4f1.m30 * paramMatrix4f2.m30 + paramMatrix4f1.m31 * paramMatrix4f2.m31 + paramMatrix4f1.m32 * paramMatrix4f2.m32 + paramMatrix4f1.m33 * paramMatrix4f2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2657 */       float f1 = paramMatrix4f1.m00 * paramMatrix4f2.m00 + paramMatrix4f1.m01 * paramMatrix4f2.m01 + paramMatrix4f1.m02 * paramMatrix4f2.m02 + paramMatrix4f1.m03 * paramMatrix4f2.m03;
/* 2658 */       float f2 = paramMatrix4f1.m00 * paramMatrix4f2.m10 + paramMatrix4f1.m01 * paramMatrix4f2.m11 + paramMatrix4f1.m02 * paramMatrix4f2.m12 + paramMatrix4f1.m03 * paramMatrix4f2.m13;
/* 2659 */       float f3 = paramMatrix4f1.m00 * paramMatrix4f2.m20 + paramMatrix4f1.m01 * paramMatrix4f2.m21 + paramMatrix4f1.m02 * paramMatrix4f2.m22 + paramMatrix4f1.m03 * paramMatrix4f2.m23;
/* 2660 */       float f4 = paramMatrix4f1.m00 * paramMatrix4f2.m30 + paramMatrix4f1.m01 * paramMatrix4f2.m31 + paramMatrix4f1.m02 * paramMatrix4f2.m32 + paramMatrix4f1.m03 * paramMatrix4f2.m33;
/*      */       
/* 2662 */       float f5 = paramMatrix4f1.m10 * paramMatrix4f2.m00 + paramMatrix4f1.m11 * paramMatrix4f2.m01 + paramMatrix4f1.m12 * paramMatrix4f2.m02 + paramMatrix4f1.m13 * paramMatrix4f2.m03;
/* 2663 */       float f6 = paramMatrix4f1.m10 * paramMatrix4f2.m10 + paramMatrix4f1.m11 * paramMatrix4f2.m11 + paramMatrix4f1.m12 * paramMatrix4f2.m12 + paramMatrix4f1.m13 * paramMatrix4f2.m13;
/* 2664 */       float f7 = paramMatrix4f1.m10 * paramMatrix4f2.m20 + paramMatrix4f1.m11 * paramMatrix4f2.m21 + paramMatrix4f1.m12 * paramMatrix4f2.m22 + paramMatrix4f1.m13 * paramMatrix4f2.m23;
/* 2665 */       float f8 = paramMatrix4f1.m10 * paramMatrix4f2.m30 + paramMatrix4f1.m11 * paramMatrix4f2.m31 + paramMatrix4f1.m12 * paramMatrix4f2.m32 + paramMatrix4f1.m13 * paramMatrix4f2.m33;
/*      */       
/* 2667 */       float f9 = paramMatrix4f1.m20 * paramMatrix4f2.m00 + paramMatrix4f1.m21 * paramMatrix4f2.m01 + paramMatrix4f1.m22 * paramMatrix4f2.m02 + paramMatrix4f1.m23 * paramMatrix4f2.m03;
/* 2668 */       float f10 = paramMatrix4f1.m20 * paramMatrix4f2.m10 + paramMatrix4f1.m21 * paramMatrix4f2.m11 + paramMatrix4f1.m22 * paramMatrix4f2.m12 + paramMatrix4f1.m23 * paramMatrix4f2.m13;
/* 2669 */       float f11 = paramMatrix4f1.m20 * paramMatrix4f2.m20 + paramMatrix4f1.m21 * paramMatrix4f2.m21 + paramMatrix4f1.m22 * paramMatrix4f2.m22 + paramMatrix4f1.m23 * paramMatrix4f2.m23;
/* 2670 */       float f12 = paramMatrix4f1.m20 * paramMatrix4f2.m30 + paramMatrix4f1.m21 * paramMatrix4f2.m31 + paramMatrix4f1.m22 * paramMatrix4f2.m32 + paramMatrix4f1.m23 * paramMatrix4f2.m33;
/*      */       
/* 2672 */       float f13 = paramMatrix4f1.m30 * paramMatrix4f2.m00 + paramMatrix4f1.m31 * paramMatrix4f2.m01 + paramMatrix4f1.m32 * paramMatrix4f2.m02 + paramMatrix4f1.m33 * paramMatrix4f2.m03;
/* 2673 */       float f14 = paramMatrix4f1.m30 * paramMatrix4f2.m10 + paramMatrix4f1.m31 * paramMatrix4f2.m11 + paramMatrix4f1.m32 * paramMatrix4f2.m12 + paramMatrix4f1.m33 * paramMatrix4f2.m13;
/* 2674 */       float f15 = paramMatrix4f1.m30 * paramMatrix4f2.m20 + paramMatrix4f1.m31 * paramMatrix4f2.m21 + paramMatrix4f1.m32 * paramMatrix4f2.m22 + paramMatrix4f1.m33 * paramMatrix4f2.m23;
/* 2675 */       float f16 = paramMatrix4f1.m30 * paramMatrix4f2.m30 + paramMatrix4f1.m31 * paramMatrix4f2.m31 + paramMatrix4f1.m32 * paramMatrix4f2.m32 + paramMatrix4f1.m33 * paramMatrix4f2.m33;
/*      */       
/* 2677 */       this.m00 = f1; this.m01 = f2; this.m02 = f3; this.m03 = f4;
/* 2678 */       this.m10 = f5; this.m11 = f6; this.m12 = f7; this.m13 = f8;
/* 2679 */       this.m20 = f9; this.m21 = f10; this.m22 = f11; this.m23 = f12;
/* 2680 */       this.m30 = f13; this.m31 = f14; this.m32 = f15; this.m33 = f16;
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
/*      */   public final void mulTransposeLeft(Matrix4f paramMatrix4f1, Matrix4f paramMatrix4f2) {
/* 2694 */     if (this != paramMatrix4f1 && this != paramMatrix4f2) {
/* 2695 */       this.m00 = paramMatrix4f1.m00 * paramMatrix4f2.m00 + paramMatrix4f1.m10 * paramMatrix4f2.m10 + paramMatrix4f1.m20 * paramMatrix4f2.m20 + paramMatrix4f1.m30 * paramMatrix4f2.m30;
/* 2696 */       this.m01 = paramMatrix4f1.m00 * paramMatrix4f2.m01 + paramMatrix4f1.m10 * paramMatrix4f2.m11 + paramMatrix4f1.m20 * paramMatrix4f2.m21 + paramMatrix4f1.m30 * paramMatrix4f2.m31;
/* 2697 */       this.m02 = paramMatrix4f1.m00 * paramMatrix4f2.m02 + paramMatrix4f1.m10 * paramMatrix4f2.m12 + paramMatrix4f1.m20 * paramMatrix4f2.m22 + paramMatrix4f1.m30 * paramMatrix4f2.m32;
/* 2698 */       this.m03 = paramMatrix4f1.m00 * paramMatrix4f2.m03 + paramMatrix4f1.m10 * paramMatrix4f2.m13 + paramMatrix4f1.m20 * paramMatrix4f2.m23 + paramMatrix4f1.m30 * paramMatrix4f2.m33;
/*      */       
/* 2700 */       this.m10 = paramMatrix4f1.m01 * paramMatrix4f2.m00 + paramMatrix4f1.m11 * paramMatrix4f2.m10 + paramMatrix4f1.m21 * paramMatrix4f2.m20 + paramMatrix4f1.m31 * paramMatrix4f2.m30;
/* 2701 */       this.m11 = paramMatrix4f1.m01 * paramMatrix4f2.m01 + paramMatrix4f1.m11 * paramMatrix4f2.m11 + paramMatrix4f1.m21 * paramMatrix4f2.m21 + paramMatrix4f1.m31 * paramMatrix4f2.m31;
/* 2702 */       this.m12 = paramMatrix4f1.m01 * paramMatrix4f2.m02 + paramMatrix4f1.m11 * paramMatrix4f2.m12 + paramMatrix4f1.m21 * paramMatrix4f2.m22 + paramMatrix4f1.m31 * paramMatrix4f2.m32;
/* 2703 */       this.m13 = paramMatrix4f1.m01 * paramMatrix4f2.m03 + paramMatrix4f1.m11 * paramMatrix4f2.m13 + paramMatrix4f1.m21 * paramMatrix4f2.m23 + paramMatrix4f1.m31 * paramMatrix4f2.m33;
/*      */       
/* 2705 */       this.m20 = paramMatrix4f1.m02 * paramMatrix4f2.m00 + paramMatrix4f1.m12 * paramMatrix4f2.m10 + paramMatrix4f1.m22 * paramMatrix4f2.m20 + paramMatrix4f1.m32 * paramMatrix4f2.m30;
/* 2706 */       this.m21 = paramMatrix4f1.m02 * paramMatrix4f2.m01 + paramMatrix4f1.m12 * paramMatrix4f2.m11 + paramMatrix4f1.m22 * paramMatrix4f2.m21 + paramMatrix4f1.m32 * paramMatrix4f2.m31;
/* 2707 */       this.m22 = paramMatrix4f1.m02 * paramMatrix4f2.m02 + paramMatrix4f1.m12 * paramMatrix4f2.m12 + paramMatrix4f1.m22 * paramMatrix4f2.m22 + paramMatrix4f1.m32 * paramMatrix4f2.m32;
/* 2708 */       this.m23 = paramMatrix4f1.m02 * paramMatrix4f2.m03 + paramMatrix4f1.m12 * paramMatrix4f2.m13 + paramMatrix4f1.m22 * paramMatrix4f2.m23 + paramMatrix4f1.m32 * paramMatrix4f2.m33;
/*      */       
/* 2710 */       this.m30 = paramMatrix4f1.m03 * paramMatrix4f2.m00 + paramMatrix4f1.m13 * paramMatrix4f2.m10 + paramMatrix4f1.m23 * paramMatrix4f2.m20 + paramMatrix4f1.m33 * paramMatrix4f2.m30;
/* 2711 */       this.m31 = paramMatrix4f1.m03 * paramMatrix4f2.m01 + paramMatrix4f1.m13 * paramMatrix4f2.m11 + paramMatrix4f1.m23 * paramMatrix4f2.m21 + paramMatrix4f1.m33 * paramMatrix4f2.m31;
/* 2712 */       this.m32 = paramMatrix4f1.m03 * paramMatrix4f2.m02 + paramMatrix4f1.m13 * paramMatrix4f2.m12 + paramMatrix4f1.m23 * paramMatrix4f2.m22 + paramMatrix4f1.m33 * paramMatrix4f2.m32;
/* 2713 */       this.m33 = paramMatrix4f1.m03 * paramMatrix4f2.m03 + paramMatrix4f1.m13 * paramMatrix4f2.m13 + paramMatrix4f1.m23 * paramMatrix4f2.m23 + paramMatrix4f1.m33 * paramMatrix4f2.m33;
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 2722 */       float f1 = paramMatrix4f1.m00 * paramMatrix4f2.m00 + paramMatrix4f1.m10 * paramMatrix4f2.m10 + paramMatrix4f1.m20 * paramMatrix4f2.m20 + paramMatrix4f1.m30 * paramMatrix4f2.m30;
/* 2723 */       float f2 = paramMatrix4f1.m00 * paramMatrix4f2.m01 + paramMatrix4f1.m10 * paramMatrix4f2.m11 + paramMatrix4f1.m20 * paramMatrix4f2.m21 + paramMatrix4f1.m30 * paramMatrix4f2.m31;
/* 2724 */       float f3 = paramMatrix4f1.m00 * paramMatrix4f2.m02 + paramMatrix4f1.m10 * paramMatrix4f2.m12 + paramMatrix4f1.m20 * paramMatrix4f2.m22 + paramMatrix4f1.m30 * paramMatrix4f2.m32;
/* 2725 */       float f4 = paramMatrix4f1.m00 * paramMatrix4f2.m03 + paramMatrix4f1.m10 * paramMatrix4f2.m13 + paramMatrix4f1.m20 * paramMatrix4f2.m23 + paramMatrix4f1.m30 * paramMatrix4f2.m33;
/*      */       
/* 2727 */       float f5 = paramMatrix4f1.m01 * paramMatrix4f2.m00 + paramMatrix4f1.m11 * paramMatrix4f2.m10 + paramMatrix4f1.m21 * paramMatrix4f2.m20 + paramMatrix4f1.m31 * paramMatrix4f2.m30;
/* 2728 */       float f6 = paramMatrix4f1.m01 * paramMatrix4f2.m01 + paramMatrix4f1.m11 * paramMatrix4f2.m11 + paramMatrix4f1.m21 * paramMatrix4f2.m21 + paramMatrix4f1.m31 * paramMatrix4f2.m31;
/* 2729 */       float f7 = paramMatrix4f1.m01 * paramMatrix4f2.m02 + paramMatrix4f1.m11 * paramMatrix4f2.m12 + paramMatrix4f1.m21 * paramMatrix4f2.m22 + paramMatrix4f1.m31 * paramMatrix4f2.m32;
/* 2730 */       float f8 = paramMatrix4f1.m01 * paramMatrix4f2.m03 + paramMatrix4f1.m11 * paramMatrix4f2.m13 + paramMatrix4f1.m21 * paramMatrix4f2.m23 + paramMatrix4f1.m31 * paramMatrix4f2.m33;
/*      */       
/* 2732 */       float f9 = paramMatrix4f1.m02 * paramMatrix4f2.m00 + paramMatrix4f1.m12 * paramMatrix4f2.m10 + paramMatrix4f1.m22 * paramMatrix4f2.m20 + paramMatrix4f1.m32 * paramMatrix4f2.m30;
/* 2733 */       float f10 = paramMatrix4f1.m02 * paramMatrix4f2.m01 + paramMatrix4f1.m12 * paramMatrix4f2.m11 + paramMatrix4f1.m22 * paramMatrix4f2.m21 + paramMatrix4f1.m32 * paramMatrix4f2.m31;
/* 2734 */       float f11 = paramMatrix4f1.m02 * paramMatrix4f2.m02 + paramMatrix4f1.m12 * paramMatrix4f2.m12 + paramMatrix4f1.m22 * paramMatrix4f2.m22 + paramMatrix4f1.m32 * paramMatrix4f2.m32;
/* 2735 */       float f12 = paramMatrix4f1.m02 * paramMatrix4f2.m03 + paramMatrix4f1.m12 * paramMatrix4f2.m13 + paramMatrix4f1.m22 * paramMatrix4f2.m23 + paramMatrix4f1.m32 * paramMatrix4f2.m33;
/*      */       
/* 2737 */       float f13 = paramMatrix4f1.m03 * paramMatrix4f2.m00 + paramMatrix4f1.m13 * paramMatrix4f2.m10 + paramMatrix4f1.m23 * paramMatrix4f2.m20 + paramMatrix4f1.m33 * paramMatrix4f2.m30;
/* 2738 */       float f14 = paramMatrix4f1.m03 * paramMatrix4f2.m01 + paramMatrix4f1.m13 * paramMatrix4f2.m11 + paramMatrix4f1.m23 * paramMatrix4f2.m21 + paramMatrix4f1.m33 * paramMatrix4f2.m31;
/* 2739 */       float f15 = paramMatrix4f1.m03 * paramMatrix4f2.m02 + paramMatrix4f1.m13 * paramMatrix4f2.m12 + paramMatrix4f1.m23 * paramMatrix4f2.m22 + paramMatrix4f1.m33 * paramMatrix4f2.m32;
/* 2740 */       float f16 = paramMatrix4f1.m03 * paramMatrix4f2.m03 + paramMatrix4f1.m13 * paramMatrix4f2.m13 + paramMatrix4f1.m23 * paramMatrix4f2.m23 + paramMatrix4f1.m33 * paramMatrix4f2.m33;
/*      */       
/* 2742 */       this.m00 = f1; this.m01 = f2; this.m02 = f3; this.m03 = f4;
/* 2743 */       this.m10 = f5; this.m11 = f6; this.m12 = f7; this.m13 = f8;
/* 2744 */       this.m20 = f9; this.m21 = f10; this.m22 = f11; this.m23 = f12;
/* 2745 */       this.m30 = f13; this.m31 = f14; this.m32 = f15; this.m33 = f16;
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
/*      */   public boolean equals(Matrix4f paramMatrix4f) {
/*      */     try {
/* 2760 */       return (this.m00 == paramMatrix4f.m00 && this.m01 == paramMatrix4f.m01 && this.m02 == paramMatrix4f.m02 && this.m03 == paramMatrix4f.m03 && this.m10 == paramMatrix4f.m10 && this.m11 == paramMatrix4f.m11 && this.m12 == paramMatrix4f.m12 && this.m13 == paramMatrix4f.m13 && this.m20 == paramMatrix4f.m20 && this.m21 == paramMatrix4f.m21 && this.m22 == paramMatrix4f.m22 && this.m23 == paramMatrix4f.m23 && this.m30 == paramMatrix4f.m30 && this.m31 == paramMatrix4f.m31 && this.m32 == paramMatrix4f.m32 && this.m33 == paramMatrix4f.m33);
/*      */ 
/*      */     
/*      */     }
/*      */     catch (NullPointerException nullPointerException) {
/*      */ 
/*      */       
/* 2767 */       return false;
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
/* 2781 */     try { Matrix4f matrix4f = (Matrix4f)paramObject;
/* 2782 */       return (this.m00 == matrix4f.m00 && this.m01 == matrix4f.m01 && this.m02 == matrix4f.m02 && this.m03 == matrix4f.m03 && this.m10 == matrix4f.m10 && this.m11 == matrix4f.m11 && this.m12 == matrix4f.m12 && this.m13 == matrix4f.m13 && this.m20 == matrix4f.m20 && this.m21 == matrix4f.m21 && this.m22 == matrix4f.m22 && this.m23 == matrix4f.m23 && this.m30 == matrix4f.m30 && this.m31 == matrix4f.m31 && this.m32 == matrix4f.m32 && this.m33 == matrix4f.m33);
/*      */       
/*      */        }
/*      */     
/*      */     catch (ClassCastException classCastException)
/*      */     
/*      */     { 
/* 2789 */       return false; }
/* 2790 */     catch (NullPointerException nullPointerException) { return false; }
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
/*      */   public boolean epsilonEquals(Matrix4f paramMatrix4f, float paramFloat) {
/* 2805 */     boolean bool = true;
/*      */     
/* 2807 */     if (Math.abs(this.m00 - paramMatrix4f.m00) > paramFloat) bool = false; 
/* 2808 */     if (Math.abs(this.m01 - paramMatrix4f.m01) > paramFloat) bool = false; 
/* 2809 */     if (Math.abs(this.m02 - paramMatrix4f.m02) > paramFloat) bool = false; 
/* 2810 */     if (Math.abs(this.m03 - paramMatrix4f.m03) > paramFloat) bool = false;
/*      */     
/* 2812 */     if (Math.abs(this.m10 - paramMatrix4f.m10) > paramFloat) bool = false; 
/* 2813 */     if (Math.abs(this.m11 - paramMatrix4f.m11) > paramFloat) bool = false; 
/* 2814 */     if (Math.abs(this.m12 - paramMatrix4f.m12) > paramFloat) bool = false; 
/* 2815 */     if (Math.abs(this.m13 - paramMatrix4f.m13) > paramFloat) bool = false;
/*      */     
/* 2817 */     if (Math.abs(this.m20 - paramMatrix4f.m20) > paramFloat) bool = false; 
/* 2818 */     if (Math.abs(this.m21 - paramMatrix4f.m21) > paramFloat) bool = false; 
/* 2819 */     if (Math.abs(this.m22 - paramMatrix4f.m22) > paramFloat) bool = false; 
/* 2820 */     if (Math.abs(this.m23 - paramMatrix4f.m23) > paramFloat) bool = false;
/*      */     
/* 2822 */     if (Math.abs(this.m30 - paramMatrix4f.m30) > paramFloat) bool = false; 
/* 2823 */     if (Math.abs(this.m31 - paramMatrix4f.m31) > paramFloat) bool = false; 
/* 2824 */     if (Math.abs(this.m32 - paramMatrix4f.m32) > paramFloat) bool = false; 
/* 2825 */     if (Math.abs(this.m33 - paramMatrix4f.m33) > paramFloat) bool = false;
/*      */     
/* 2827 */     return bool;
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
/* 2841 */     long l = 1L;
/* 2842 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m00);
/* 2843 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m01);
/* 2844 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m02);
/* 2845 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m03);
/* 2846 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m10);
/* 2847 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m11);
/* 2848 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m12);
/* 2849 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m13);
/* 2850 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m20);
/* 2851 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m21);
/* 2852 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m22);
/* 2853 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m23);
/* 2854 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m30);
/* 2855 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m31);
/* 2856 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m32);
/* 2857 */     l = 31L * l + VecMathUtil.floatToIntBits(this.m33);
/* 2858 */     return (int)(l ^ l >> 32L);
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
/*      */   public final void transform(Tuple4f paramTuple4f1, Tuple4f paramTuple4f2) {
/* 2871 */     float f1 = this.m00 * paramTuple4f1.x + this.m01 * paramTuple4f1.y + this.m02 * paramTuple4f1.z + this.m03 * paramTuple4f1.w;
/*      */     
/* 2873 */     float f2 = this.m10 * paramTuple4f1.x + this.m11 * paramTuple4f1.y + this.m12 * paramTuple4f1.z + this.m13 * paramTuple4f1.w;
/*      */     
/* 2875 */     float f3 = this.m20 * paramTuple4f1.x + this.m21 * paramTuple4f1.y + this.m22 * paramTuple4f1.z + this.m23 * paramTuple4f1.w;
/*      */     
/* 2877 */     paramTuple4f2.w = this.m30 * paramTuple4f1.x + this.m31 * paramTuple4f1.y + this.m32 * paramTuple4f1.z + this.m33 * paramTuple4f1.w;
/*      */     
/* 2879 */     paramTuple4f2.x = f1;
/* 2880 */     paramTuple4f2.y = f2;
/* 2881 */     paramTuple4f2.z = f3;
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
/*      */   public final void transform(Tuple4f paramTuple4f) {
/* 2894 */     float f1 = this.m00 * paramTuple4f.x + this.m01 * paramTuple4f.y + this.m02 * paramTuple4f.z + this.m03 * paramTuple4f.w;
/*      */     
/* 2896 */     float f2 = this.m10 * paramTuple4f.x + this.m11 * paramTuple4f.y + this.m12 * paramTuple4f.z + this.m13 * paramTuple4f.w;
/*      */     
/* 2898 */     float f3 = this.m20 * paramTuple4f.x + this.m21 * paramTuple4f.y + this.m22 * paramTuple4f.z + this.m23 * paramTuple4f.w;
/*      */     
/* 2900 */     paramTuple4f.w = this.m30 * paramTuple4f.x + this.m31 * paramTuple4f.y + this.m32 * paramTuple4f.z + this.m33 * paramTuple4f.w;
/*      */     
/* 2902 */     paramTuple4f.x = f1;
/* 2903 */     paramTuple4f.y = f2;
/* 2904 */     paramTuple4f.z = f3;
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
/*      */   public final void transform(Point3f paramPoint3f1, Point3f paramPoint3f2) {
/* 2917 */     float f1 = this.m00 * paramPoint3f1.x + this.m01 * paramPoint3f1.y + this.m02 * paramPoint3f1.z + this.m03;
/* 2918 */     float f2 = this.m10 * paramPoint3f1.x + this.m11 * paramPoint3f1.y + this.m12 * paramPoint3f1.z + this.m13;
/* 2919 */     paramPoint3f2.z = this.m20 * paramPoint3f1.x + this.m21 * paramPoint3f1.y + this.m22 * paramPoint3f1.z + this.m23;
/* 2920 */     paramPoint3f2.x = f1;
/* 2921 */     paramPoint3f2.y = f2;
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
/*      */   public final void transform(Point3f paramPoint3f) {
/* 2934 */     float f1 = this.m00 * paramPoint3f.x + this.m01 * paramPoint3f.y + this.m02 * paramPoint3f.z + this.m03;
/* 2935 */     float f2 = this.m10 * paramPoint3f.x + this.m11 * paramPoint3f.y + this.m12 * paramPoint3f.z + this.m13;
/* 2936 */     paramPoint3f.z = this.m20 * paramPoint3f.x + this.m21 * paramPoint3f.y + this.m22 * paramPoint3f.z + this.m23;
/* 2937 */     paramPoint3f.x = f1;
/* 2938 */     paramPoint3f.y = f2;
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
/*      */   public final void transform(Vector3f paramVector3f1, Vector3f paramVector3f2) {
/* 2951 */     float f1 = this.m00 * paramVector3f1.x + this.m01 * paramVector3f1.y + this.m02 * paramVector3f1.z;
/* 2952 */     float f2 = this.m10 * paramVector3f1.x + this.m11 * paramVector3f1.y + this.m12 * paramVector3f1.z;
/* 2953 */     paramVector3f2.z = this.m20 * paramVector3f1.x + this.m21 * paramVector3f1.y + this.m22 * paramVector3f1.z;
/* 2954 */     paramVector3f2.x = f1;
/* 2955 */     paramVector3f2.y = f2;
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
/*      */   public final void transform(Vector3f paramVector3f) {
/* 2968 */     float f1 = this.m00 * paramVector3f.x + this.m01 * paramVector3f.y + this.m02 * paramVector3f.z;
/* 2969 */     float f2 = this.m10 * paramVector3f.x + this.m11 * paramVector3f.y + this.m12 * paramVector3f.z;
/* 2970 */     paramVector3f.z = this.m20 * paramVector3f.x + this.m21 * paramVector3f.y + this.m22 * paramVector3f.z;
/* 2971 */     paramVector3f.x = f1;
/* 2972 */     paramVector3f.y = f2;
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
/*      */   public final void setRotation(Matrix3d paramMatrix3d) {
/* 2988 */     double[] arrayOfDouble1 = new double[9];
/* 2989 */     double[] arrayOfDouble2 = new double[3];
/*      */     
/* 2991 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/* 2993 */     this.m00 = (float)(paramMatrix3d.m00 * arrayOfDouble2[0]);
/* 2994 */     this.m01 = (float)(paramMatrix3d.m01 * arrayOfDouble2[1]);
/* 2995 */     this.m02 = (float)(paramMatrix3d.m02 * arrayOfDouble2[2]);
/*      */     
/* 2997 */     this.m10 = (float)(paramMatrix3d.m10 * arrayOfDouble2[0]);
/* 2998 */     this.m11 = (float)(paramMatrix3d.m11 * arrayOfDouble2[1]);
/* 2999 */     this.m12 = (float)(paramMatrix3d.m12 * arrayOfDouble2[2]);
/*      */     
/* 3001 */     this.m20 = (float)(paramMatrix3d.m20 * arrayOfDouble2[0]);
/* 3002 */     this.m21 = (float)(paramMatrix3d.m21 * arrayOfDouble2[1]);
/* 3003 */     this.m22 = (float)(paramMatrix3d.m22 * arrayOfDouble2[2]);
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
/*      */   public final void setRotation(Matrix3f paramMatrix3f) {
/* 3018 */     double[] arrayOfDouble1 = new double[9];
/* 3019 */     double[] arrayOfDouble2 = new double[3];
/*      */     
/* 3021 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/* 3023 */     this.m00 = (float)(paramMatrix3f.m00 * arrayOfDouble2[0]);
/* 3024 */     this.m01 = (float)(paramMatrix3f.m01 * arrayOfDouble2[1]);
/* 3025 */     this.m02 = (float)(paramMatrix3f.m02 * arrayOfDouble2[2]);
/*      */     
/* 3027 */     this.m10 = (float)(paramMatrix3f.m10 * arrayOfDouble2[0]);
/* 3028 */     this.m11 = (float)(paramMatrix3f.m11 * arrayOfDouble2[1]);
/* 3029 */     this.m12 = (float)(paramMatrix3f.m12 * arrayOfDouble2[2]);
/*      */     
/* 3031 */     this.m20 = (float)(paramMatrix3f.m20 * arrayOfDouble2[0]);
/* 3032 */     this.m21 = (float)(paramMatrix3f.m21 * arrayOfDouble2[1]);
/* 3033 */     this.m22 = (float)(paramMatrix3f.m22 * arrayOfDouble2[2]);
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
/*      */   public final void setRotation(Quat4f paramQuat4f) {
/* 3047 */     double[] arrayOfDouble1 = new double[9];
/* 3048 */     double[] arrayOfDouble2 = new double[3];
/* 3049 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/* 3051 */     this.m00 = (float)((1.0F - 2.0F * paramQuat4f.y * paramQuat4f.y - 2.0F * paramQuat4f.z * paramQuat4f.z) * arrayOfDouble2[0]);
/* 3052 */     this.m10 = (float)((2.0F * (paramQuat4f.x * paramQuat4f.y + paramQuat4f.w * paramQuat4f.z)) * arrayOfDouble2[0]);
/* 3053 */     this.m20 = (float)((2.0F * (paramQuat4f.x * paramQuat4f.z - paramQuat4f.w * paramQuat4f.y)) * arrayOfDouble2[0]);
/*      */     
/* 3055 */     this.m01 = (float)((2.0F * (paramQuat4f.x * paramQuat4f.y - paramQuat4f.w * paramQuat4f.z)) * arrayOfDouble2[1]);
/* 3056 */     this.m11 = (float)((1.0F - 2.0F * paramQuat4f.x * paramQuat4f.x - 2.0F * paramQuat4f.z * paramQuat4f.z) * arrayOfDouble2[1]);
/* 3057 */     this.m21 = (float)((2.0F * (paramQuat4f.y * paramQuat4f.z + paramQuat4f.w * paramQuat4f.x)) * arrayOfDouble2[1]);
/*      */     
/* 3059 */     this.m02 = (float)((2.0F * (paramQuat4f.x * paramQuat4f.z + paramQuat4f.w * paramQuat4f.y)) * arrayOfDouble2[2]);
/* 3060 */     this.m12 = (float)((2.0F * (paramQuat4f.y * paramQuat4f.z - paramQuat4f.w * paramQuat4f.x)) * arrayOfDouble2[2]);
/* 3061 */     this.m22 = (float)((1.0F - 2.0F * paramQuat4f.x * paramQuat4f.x - 2.0F * paramQuat4f.y * paramQuat4f.y) * arrayOfDouble2[2]);
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
/*      */   public final void setRotation(Quat4d paramQuat4d) {
/* 3077 */     double[] arrayOfDouble1 = new double[9];
/* 3078 */     double[] arrayOfDouble2 = new double[3];
/*      */     
/* 3080 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/* 3082 */     this.m00 = (float)((1.0D - 2.0D * paramQuat4d.y * paramQuat4d.y - 2.0D * paramQuat4d.z * paramQuat4d.z) * arrayOfDouble2[0]);
/* 3083 */     this.m10 = (float)(2.0D * (paramQuat4d.x * paramQuat4d.y + paramQuat4d.w * paramQuat4d.z) * arrayOfDouble2[0]);
/* 3084 */     this.m20 = (float)(2.0D * (paramQuat4d.x * paramQuat4d.z - paramQuat4d.w * paramQuat4d.y) * arrayOfDouble2[0]);
/*      */     
/* 3086 */     this.m01 = (float)(2.0D * (paramQuat4d.x * paramQuat4d.y - paramQuat4d.w * paramQuat4d.z) * arrayOfDouble2[1]);
/* 3087 */     this.m11 = (float)((1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.z * paramQuat4d.z) * arrayOfDouble2[1]);
/* 3088 */     this.m21 = (float)(2.0D * (paramQuat4d.y * paramQuat4d.z + paramQuat4d.w * paramQuat4d.x) * arrayOfDouble2[1]);
/*      */     
/* 3090 */     this.m02 = (float)(2.0D * (paramQuat4d.x * paramQuat4d.z + paramQuat4d.w * paramQuat4d.y) * arrayOfDouble2[2]);
/* 3091 */     this.m12 = (float)(2.0D * (paramQuat4d.y * paramQuat4d.z - paramQuat4d.w * paramQuat4d.x) * arrayOfDouble2[2]);
/* 3092 */     this.m22 = (float)((1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.y * paramQuat4d.y) * arrayOfDouble2[2]);
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
/*      */   public final void setRotation(AxisAngle4f paramAxisAngle4f) {
/* 3106 */     double[] arrayOfDouble1 = new double[9];
/* 3107 */     double[] arrayOfDouble2 = new double[3];
/*      */     
/* 3109 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/* 3111 */     double d = Math.sqrt((paramAxisAngle4f.x * paramAxisAngle4f.x + paramAxisAngle4f.y * paramAxisAngle4f.y + paramAxisAngle4f.z * paramAxisAngle4f.z));
/* 3112 */     if (d < 1.0E-8D) {
/* 3113 */       this.m00 = 1.0F;
/* 3114 */       this.m01 = 0.0F;
/* 3115 */       this.m02 = 0.0F;
/*      */       
/* 3117 */       this.m10 = 0.0F;
/* 3118 */       this.m11 = 1.0F;
/* 3119 */       this.m12 = 0.0F;
/*      */       
/* 3121 */       this.m20 = 0.0F;
/* 3122 */       this.m21 = 0.0F;
/* 3123 */       this.m22 = 1.0F;
/*      */     } else {
/* 3125 */       d = 1.0D / d;
/* 3126 */       double d1 = paramAxisAngle4f.x * d;
/* 3127 */       double d2 = paramAxisAngle4f.y * d;
/* 3128 */       double d3 = paramAxisAngle4f.z * d;
/*      */       
/* 3130 */       double d4 = Math.sin(paramAxisAngle4f.angle);
/* 3131 */       double d5 = Math.cos(paramAxisAngle4f.angle);
/* 3132 */       double d6 = 1.0D - d5;
/*      */       
/* 3134 */       double d7 = (paramAxisAngle4f.x * paramAxisAngle4f.z);
/* 3135 */       double d8 = (paramAxisAngle4f.x * paramAxisAngle4f.y);
/* 3136 */       double d9 = (paramAxisAngle4f.y * paramAxisAngle4f.z);
/*      */       
/* 3138 */       this.m00 = (float)((d6 * d1 * d1 + d5) * arrayOfDouble2[0]);
/* 3139 */       this.m01 = (float)((d6 * d8 - d4 * d3) * arrayOfDouble2[1]);
/* 3140 */       this.m02 = (float)((d6 * d7 + d4 * d2) * arrayOfDouble2[2]);
/*      */       
/* 3142 */       this.m10 = (float)((d6 * d8 + d4 * d3) * arrayOfDouble2[0]);
/* 3143 */       this.m11 = (float)((d6 * d2 * d2 + d5) * arrayOfDouble2[1]);
/* 3144 */       this.m12 = (float)((d6 * d9 - d4 * d1) * arrayOfDouble2[2]);
/*      */       
/* 3146 */       this.m20 = (float)((d6 * d7 - d4 * d2) * arrayOfDouble2[0]);
/* 3147 */       this.m21 = (float)((d6 * d9 + d4 * d1) * arrayOfDouble2[1]);
/* 3148 */       this.m22 = (float)((d6 * d3 * d3 + d5) * arrayOfDouble2[2]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/* 3159 */     this.m00 = 0.0F;
/* 3160 */     this.m01 = 0.0F;
/* 3161 */     this.m02 = 0.0F;
/* 3162 */     this.m03 = 0.0F;
/* 3163 */     this.m10 = 0.0F;
/* 3164 */     this.m11 = 0.0F;
/* 3165 */     this.m12 = 0.0F;
/* 3166 */     this.m13 = 0.0F;
/* 3167 */     this.m20 = 0.0F;
/* 3168 */     this.m21 = 0.0F;
/* 3169 */     this.m22 = 0.0F;
/* 3170 */     this.m23 = 0.0F;
/* 3171 */     this.m30 = 0.0F;
/* 3172 */     this.m31 = 0.0F;
/* 3173 */     this.m32 = 0.0F;
/* 3174 */     this.m33 = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/* 3182 */     this.m00 = -this.m00;
/* 3183 */     this.m01 = -this.m01;
/* 3184 */     this.m02 = -this.m02;
/* 3185 */     this.m03 = -this.m03;
/* 3186 */     this.m10 = -this.m10;
/* 3187 */     this.m11 = -this.m11;
/* 3188 */     this.m12 = -this.m12;
/* 3189 */     this.m13 = -this.m13;
/* 3190 */     this.m20 = -this.m20;
/* 3191 */     this.m21 = -this.m21;
/* 3192 */     this.m22 = -this.m22;
/* 3193 */     this.m23 = -this.m23;
/* 3194 */     this.m30 = -this.m30;
/* 3195 */     this.m31 = -this.m31;
/* 3196 */     this.m32 = -this.m32;
/* 3197 */     this.m33 = -this.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate(Matrix4f paramMatrix4f) {
/* 3207 */     this.m00 = -paramMatrix4f.m00;
/* 3208 */     this.m01 = -paramMatrix4f.m01;
/* 3209 */     this.m02 = -paramMatrix4f.m02;
/* 3210 */     this.m03 = -paramMatrix4f.m03;
/* 3211 */     this.m10 = -paramMatrix4f.m10;
/* 3212 */     this.m11 = -paramMatrix4f.m11;
/* 3213 */     this.m12 = -paramMatrix4f.m12;
/* 3214 */     this.m13 = -paramMatrix4f.m13;
/* 3215 */     this.m20 = -paramMatrix4f.m20;
/* 3216 */     this.m21 = -paramMatrix4f.m21;
/* 3217 */     this.m22 = -paramMatrix4f.m22;
/* 3218 */     this.m23 = -paramMatrix4f.m23;
/* 3219 */     this.m30 = -paramMatrix4f.m30;
/* 3220 */     this.m31 = -paramMatrix4f.m31;
/* 3221 */     this.m32 = -paramMatrix4f.m32;
/* 3222 */     this.m33 = -paramMatrix4f.m33;
/*      */   }
/*      */   
/*      */   private final void getScaleRotate(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
/* 3226 */     double[] arrayOfDouble = new double[9];
/* 3227 */     arrayOfDouble[0] = this.m00;
/* 3228 */     arrayOfDouble[1] = this.m01;
/* 3229 */     arrayOfDouble[2] = this.m02;
/*      */     
/* 3231 */     arrayOfDouble[3] = this.m10;
/* 3232 */     arrayOfDouble[4] = this.m11;
/* 3233 */     arrayOfDouble[5] = this.m12;
/*      */     
/* 3235 */     arrayOfDouble[6] = this.m20;
/* 3236 */     arrayOfDouble[7] = this.m21;
/* 3237 */     arrayOfDouble[8] = this.m22;
/*      */     
/* 3239 */     Matrix3d.compute_svd(arrayOfDouble, paramArrayOfdouble1, paramArrayOfdouble2);
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
/*      */   public Object clone() {
/* 3253 */     Matrix4f matrix4f = null;
/*      */     try {
/* 3255 */       matrix4f = (Matrix4f)super.clone();
/* 3256 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */       
/* 3258 */       throw new InternalError();
/*      */     } 
/*      */     
/* 3261 */     return matrix4f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM00() {
/* 3272 */     return this.m00;
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
/* 3283 */     this.m00 = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM01() {
/* 3294 */     return this.m01;
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
/* 3305 */     this.m01 = paramFloat;
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
/* 3316 */     return this.m02;
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
/* 3327 */     this.m02 = paramFloat;
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
/* 3338 */     return this.m10;
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
/* 3349 */     this.m10 = paramFloat;
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
/* 3360 */     return this.m11;
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
/* 3371 */     this.m11 = paramFloat;
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
/* 3382 */     return this.m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM12(float paramFloat) {
/* 3393 */     this.m12 = paramFloat;
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
/* 3404 */     return this.m20;
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
/* 3415 */     this.m20 = paramFloat;
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
/* 3426 */     return this.m21;
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
/* 3437 */     this.m21 = paramFloat;
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
/* 3448 */     return this.m22;
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
/* 3459 */     this.m22 = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM03() {
/* 3470 */     return this.m03;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM03(float paramFloat) {
/* 3481 */     this.m03 = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM13() {
/* 3492 */     return this.m13;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM13(float paramFloat) {
/* 3503 */     this.m13 = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM23() {
/* 3514 */     return this.m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM23(float paramFloat) {
/* 3525 */     this.m23 = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM30() {
/* 3536 */     return this.m30;
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
/*      */   public final void setM30(float paramFloat) {
/* 3548 */     this.m30 = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM31() {
/* 3559 */     return this.m31;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM31(float paramFloat) {
/* 3570 */     this.m31 = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM32() {
/* 3581 */     return this.m32;
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
/*      */   public final void setM32(float paramFloat) {
/* 3593 */     this.m32 = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM33() {
/* 3604 */     return this.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM33(float paramFloat) {
/* 3615 */     this.m33 = paramFloat;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Matrix4f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
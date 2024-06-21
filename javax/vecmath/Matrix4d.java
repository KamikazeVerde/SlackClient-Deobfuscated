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
/*      */ 
/*      */ public class Matrix4d
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   static final long serialVersionUID = 8223903484171633710L;
/*      */   public double m00;
/*      */   public double m01;
/*      */   public double m02;
/*      */   public double m03;
/*      */   public double m10;
/*      */   public double m11;
/*      */   public double m12;
/*      */   public double m13;
/*      */   public double m20;
/*      */   public double m21;
/*      */   public double m22;
/*      */   public double m23;
/*      */   public double m30;
/*      */   public double m31;
/*      */   public double m32;
/*      */   public double m33;
/*      */   private static final double EPS = 1.0E-10D;
/*      */   
/*      */   public Matrix4d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12, double paramDouble13, double paramDouble14, double paramDouble15, double paramDouble16) {
/*  157 */     this.m00 = paramDouble1;
/*  158 */     this.m01 = paramDouble2;
/*  159 */     this.m02 = paramDouble3;
/*  160 */     this.m03 = paramDouble4;
/*      */     
/*  162 */     this.m10 = paramDouble5;
/*  163 */     this.m11 = paramDouble6;
/*  164 */     this.m12 = paramDouble7;
/*  165 */     this.m13 = paramDouble8;
/*      */     
/*  167 */     this.m20 = paramDouble9;
/*  168 */     this.m21 = paramDouble10;
/*  169 */     this.m22 = paramDouble11;
/*  170 */     this.m23 = paramDouble12;
/*      */     
/*  172 */     this.m30 = paramDouble13;
/*  173 */     this.m31 = paramDouble14;
/*  174 */     this.m32 = paramDouble15;
/*  175 */     this.m33 = paramDouble16;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d(double[] paramArrayOfdouble) {
/*  186 */     this.m00 = paramArrayOfdouble[0];
/*  187 */     this.m01 = paramArrayOfdouble[1];
/*  188 */     this.m02 = paramArrayOfdouble[2];
/*  189 */     this.m03 = paramArrayOfdouble[3];
/*      */     
/*  191 */     this.m10 = paramArrayOfdouble[4];
/*  192 */     this.m11 = paramArrayOfdouble[5];
/*  193 */     this.m12 = paramArrayOfdouble[6];
/*  194 */     this.m13 = paramArrayOfdouble[7];
/*      */     
/*  196 */     this.m20 = paramArrayOfdouble[8];
/*  197 */     this.m21 = paramArrayOfdouble[9];
/*  198 */     this.m22 = paramArrayOfdouble[10];
/*  199 */     this.m23 = paramArrayOfdouble[11];
/*      */     
/*  201 */     this.m30 = paramArrayOfdouble[12];
/*  202 */     this.m31 = paramArrayOfdouble[13];
/*  203 */     this.m32 = paramArrayOfdouble[14];
/*  204 */     this.m33 = paramArrayOfdouble[15];
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
/*      */   public Matrix4d(Quat4d paramQuat4d, Vector3d paramVector3d, double paramDouble) {
/*  219 */     this.m00 = paramDouble * (1.0D - 2.0D * paramQuat4d.y * paramQuat4d.y - 2.0D * paramQuat4d.z * paramQuat4d.z);
/*  220 */     this.m10 = paramDouble * 2.0D * (paramQuat4d.x * paramQuat4d.y + paramQuat4d.w * paramQuat4d.z);
/*  221 */     this.m20 = paramDouble * 2.0D * (paramQuat4d.x * paramQuat4d.z - paramQuat4d.w * paramQuat4d.y);
/*      */     
/*  223 */     this.m01 = paramDouble * 2.0D * (paramQuat4d.x * paramQuat4d.y - paramQuat4d.w * paramQuat4d.z);
/*  224 */     this.m11 = paramDouble * (1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.z * paramQuat4d.z);
/*  225 */     this.m21 = paramDouble * 2.0D * (paramQuat4d.y * paramQuat4d.z + paramQuat4d.w * paramQuat4d.x);
/*      */     
/*  227 */     this.m02 = paramDouble * 2.0D * (paramQuat4d.x * paramQuat4d.z + paramQuat4d.w * paramQuat4d.y);
/*  228 */     this.m12 = paramDouble * 2.0D * (paramQuat4d.y * paramQuat4d.z - paramQuat4d.w * paramQuat4d.x);
/*  229 */     this.m22 = paramDouble * (1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.y * paramQuat4d.y);
/*      */     
/*  231 */     this.m03 = paramVector3d.x;
/*  232 */     this.m13 = paramVector3d.y;
/*  233 */     this.m23 = paramVector3d.z;
/*      */     
/*  235 */     this.m30 = 0.0D;
/*  236 */     this.m31 = 0.0D;
/*  237 */     this.m32 = 0.0D;
/*  238 */     this.m33 = 1.0D;
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
/*      */   public Matrix4d(Quat4f paramQuat4f, Vector3d paramVector3d, double paramDouble) {
/*  253 */     this.m00 = paramDouble * (1.0D - 2.0D * paramQuat4f.y * paramQuat4f.y - 2.0D * paramQuat4f.z * paramQuat4f.z);
/*  254 */     this.m10 = paramDouble * 2.0D * (paramQuat4f.x * paramQuat4f.y + paramQuat4f.w * paramQuat4f.z);
/*  255 */     this.m20 = paramDouble * 2.0D * (paramQuat4f.x * paramQuat4f.z - paramQuat4f.w * paramQuat4f.y);
/*      */     
/*  257 */     this.m01 = paramDouble * 2.0D * (paramQuat4f.x * paramQuat4f.y - paramQuat4f.w * paramQuat4f.z);
/*  258 */     this.m11 = paramDouble * (1.0D - 2.0D * paramQuat4f.x * paramQuat4f.x - 2.0D * paramQuat4f.z * paramQuat4f.z);
/*  259 */     this.m21 = paramDouble * 2.0D * (paramQuat4f.y * paramQuat4f.z + paramQuat4f.w * paramQuat4f.x);
/*      */     
/*  261 */     this.m02 = paramDouble * 2.0D * (paramQuat4f.x * paramQuat4f.z + paramQuat4f.w * paramQuat4f.y);
/*  262 */     this.m12 = paramDouble * 2.0D * (paramQuat4f.y * paramQuat4f.z - paramQuat4f.w * paramQuat4f.x);
/*  263 */     this.m22 = paramDouble * (1.0D - 2.0D * paramQuat4f.x * paramQuat4f.x - 2.0D * paramQuat4f.y * paramQuat4f.y);
/*      */     
/*  265 */     this.m03 = paramVector3d.x;
/*  266 */     this.m13 = paramVector3d.y;
/*  267 */     this.m23 = paramVector3d.z;
/*      */     
/*  269 */     this.m30 = 0.0D;
/*  270 */     this.m31 = 0.0D;
/*  271 */     this.m32 = 0.0D;
/*  272 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d(Matrix4d paramMatrix4d) {
/*  283 */     this.m00 = paramMatrix4d.m00;
/*  284 */     this.m01 = paramMatrix4d.m01;
/*  285 */     this.m02 = paramMatrix4d.m02;
/*  286 */     this.m03 = paramMatrix4d.m03;
/*      */     
/*  288 */     this.m10 = paramMatrix4d.m10;
/*  289 */     this.m11 = paramMatrix4d.m11;
/*  290 */     this.m12 = paramMatrix4d.m12;
/*  291 */     this.m13 = paramMatrix4d.m13;
/*      */     
/*  293 */     this.m20 = paramMatrix4d.m20;
/*  294 */     this.m21 = paramMatrix4d.m21;
/*  295 */     this.m22 = paramMatrix4d.m22;
/*  296 */     this.m23 = paramMatrix4d.m23;
/*      */     
/*  298 */     this.m30 = paramMatrix4d.m30;
/*  299 */     this.m31 = paramMatrix4d.m31;
/*  300 */     this.m32 = paramMatrix4d.m32;
/*  301 */     this.m33 = paramMatrix4d.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d(Matrix4f paramMatrix4f) {
/*  312 */     this.m00 = paramMatrix4f.m00;
/*  313 */     this.m01 = paramMatrix4f.m01;
/*  314 */     this.m02 = paramMatrix4f.m02;
/*  315 */     this.m03 = paramMatrix4f.m03;
/*      */     
/*  317 */     this.m10 = paramMatrix4f.m10;
/*  318 */     this.m11 = paramMatrix4f.m11;
/*  319 */     this.m12 = paramMatrix4f.m12;
/*  320 */     this.m13 = paramMatrix4f.m13;
/*      */     
/*  322 */     this.m20 = paramMatrix4f.m20;
/*  323 */     this.m21 = paramMatrix4f.m21;
/*  324 */     this.m22 = paramMatrix4f.m22;
/*  325 */     this.m23 = paramMatrix4f.m23;
/*      */     
/*  327 */     this.m30 = paramMatrix4f.m30;
/*  328 */     this.m31 = paramMatrix4f.m31;
/*  329 */     this.m32 = paramMatrix4f.m32;
/*  330 */     this.m33 = paramMatrix4f.m33;
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
/*      */   public Matrix4d(Matrix3f paramMatrix3f, Vector3d paramVector3d, double paramDouble) {
/*  345 */     this.m00 = paramMatrix3f.m00 * paramDouble;
/*  346 */     this.m01 = paramMatrix3f.m01 * paramDouble;
/*  347 */     this.m02 = paramMatrix3f.m02 * paramDouble;
/*  348 */     this.m03 = paramVector3d.x;
/*      */     
/*  350 */     this.m10 = paramMatrix3f.m10 * paramDouble;
/*  351 */     this.m11 = paramMatrix3f.m11 * paramDouble;
/*  352 */     this.m12 = paramMatrix3f.m12 * paramDouble;
/*  353 */     this.m13 = paramVector3d.y;
/*      */     
/*  355 */     this.m20 = paramMatrix3f.m20 * paramDouble;
/*  356 */     this.m21 = paramMatrix3f.m21 * paramDouble;
/*  357 */     this.m22 = paramMatrix3f.m22 * paramDouble;
/*  358 */     this.m23 = paramVector3d.z;
/*      */     
/*  360 */     this.m30 = 0.0D;
/*  361 */     this.m31 = 0.0D;
/*  362 */     this.m32 = 0.0D;
/*  363 */     this.m33 = 1.0D;
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
/*      */   public Matrix4d(Matrix3d paramMatrix3d, Vector3d paramVector3d, double paramDouble) {
/*  378 */     this.m00 = paramMatrix3d.m00 * paramDouble;
/*  379 */     this.m01 = paramMatrix3d.m01 * paramDouble;
/*  380 */     this.m02 = paramMatrix3d.m02 * paramDouble;
/*  381 */     this.m03 = paramVector3d.x;
/*      */     
/*  383 */     this.m10 = paramMatrix3d.m10 * paramDouble;
/*  384 */     this.m11 = paramMatrix3d.m11 * paramDouble;
/*  385 */     this.m12 = paramMatrix3d.m12 * paramDouble;
/*  386 */     this.m13 = paramVector3d.y;
/*      */     
/*  388 */     this.m20 = paramMatrix3d.m20 * paramDouble;
/*  389 */     this.m21 = paramMatrix3d.m21 * paramDouble;
/*  390 */     this.m22 = paramMatrix3d.m22 * paramDouble;
/*  391 */     this.m23 = paramVector3d.z;
/*      */     
/*  393 */     this.m30 = 0.0D;
/*  394 */     this.m31 = 0.0D;
/*  395 */     this.m32 = 0.0D;
/*  396 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d() {
/*  405 */     this.m00 = 0.0D;
/*  406 */     this.m01 = 0.0D;
/*  407 */     this.m02 = 0.0D;
/*  408 */     this.m03 = 0.0D;
/*      */     
/*  410 */     this.m10 = 0.0D;
/*  411 */     this.m11 = 0.0D;
/*  412 */     this.m12 = 0.0D;
/*  413 */     this.m13 = 0.0D;
/*      */     
/*  415 */     this.m20 = 0.0D;
/*  416 */     this.m21 = 0.0D;
/*  417 */     this.m22 = 0.0D;
/*  418 */     this.m23 = 0.0D;
/*      */     
/*  420 */     this.m30 = 0.0D;
/*  421 */     this.m31 = 0.0D;
/*  422 */     this.m32 = 0.0D;
/*  423 */     this.m33 = 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  432 */     return this.m00 + ", " + this.m01 + ", " + this.m02 + ", " + this.m03 + "\n" + this.m10 + ", " + this.m11 + ", " + this.m12 + ", " + this.m13 + "\n" + this.m20 + ", " + this.m21 + ", " + this.m22 + ", " + this.m23 + "\n" + this.m30 + ", " + this.m31 + ", " + this.m32 + ", " + this.m33 + "\n";
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
/*  444 */     this.m00 = 1.0D;
/*  445 */     this.m01 = 0.0D;
/*  446 */     this.m02 = 0.0D;
/*  447 */     this.m03 = 0.0D;
/*      */     
/*  449 */     this.m10 = 0.0D;
/*  450 */     this.m11 = 1.0D;
/*  451 */     this.m12 = 0.0D;
/*  452 */     this.m13 = 0.0D;
/*      */     
/*  454 */     this.m20 = 0.0D;
/*  455 */     this.m21 = 0.0D;
/*  456 */     this.m22 = 1.0D;
/*  457 */     this.m23 = 0.0D;
/*      */     
/*  459 */     this.m30 = 0.0D;
/*  460 */     this.m31 = 0.0D;
/*  461 */     this.m32 = 0.0D;
/*  462 */     this.m33 = 1.0D;
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
/*  473 */     switch (paramInt1) {
/*      */       
/*      */       case 0:
/*  476 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  479 */             this.m00 = paramDouble;
/*      */             return;
/*      */           case 1:
/*  482 */             this.m01 = paramDouble;
/*      */             return;
/*      */           case 2:
/*  485 */             this.m02 = paramDouble;
/*      */             return;
/*      */           case 3:
/*  488 */             this.m03 = paramDouble;
/*      */             return;
/*      */         } 
/*  491 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  496 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  499 */             this.m10 = paramDouble;
/*      */             return;
/*      */           case 1:
/*  502 */             this.m11 = paramDouble;
/*      */             return;
/*      */           case 2:
/*  505 */             this.m12 = paramDouble;
/*      */             return;
/*      */           case 3:
/*  508 */             this.m13 = paramDouble;
/*      */             return;
/*      */         } 
/*  511 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  516 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  519 */             this.m20 = paramDouble;
/*      */             return;
/*      */           case 1:
/*  522 */             this.m21 = paramDouble;
/*      */             return;
/*      */           case 2:
/*  525 */             this.m22 = paramDouble;
/*      */             return;
/*      */           case 3:
/*  528 */             this.m23 = paramDouble;
/*      */             return;
/*      */         } 
/*  531 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*  536 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  539 */             this.m30 = paramDouble;
/*      */             return;
/*      */           case 1:
/*  542 */             this.m31 = paramDouble;
/*      */             return;
/*      */           case 2:
/*  545 */             this.m32 = paramDouble;
/*      */             return;
/*      */           case 3:
/*  548 */             this.m33 = paramDouble;
/*      */             return;
/*      */         } 
/*  551 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  556 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
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
/*      */   public final double getElement(int paramInt1, int paramInt2) {
/*  568 */     switch (paramInt1) {
/*      */       
/*      */       case 0:
/*  571 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  574 */             return this.m00;
/*      */           case 1:
/*  576 */             return this.m01;
/*      */           case 2:
/*  578 */             return this.m02;
/*      */           case 3:
/*  580 */             return this.m03;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/*  586 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  589 */             return this.m10;
/*      */           case 1:
/*  591 */             return this.m11;
/*      */           case 2:
/*  593 */             return this.m12;
/*      */           case 3:
/*  595 */             return this.m13;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/*  602 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  605 */             return this.m20;
/*      */           case 1:
/*  607 */             return this.m21;
/*      */           case 2:
/*  609 */             return this.m22;
/*      */           case 3:
/*  611 */             return this.m23;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 3:
/*  618 */         switch (paramInt2) {
/*      */           
/*      */           case 0:
/*  621 */             return this.m30;
/*      */           case 1:
/*  623 */             return this.m31;
/*      */           case 2:
/*  625 */             return this.m32;
/*      */           case 3:
/*  627 */             return this.m33;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  636 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d1"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int paramInt, Vector4d paramVector4d) {
/*  645 */     if (paramInt == 0) {
/*  646 */       paramVector4d.x = this.m00;
/*  647 */       paramVector4d.y = this.m01;
/*  648 */       paramVector4d.z = this.m02;
/*  649 */       paramVector4d.w = this.m03;
/*  650 */     } else if (paramInt == 1) {
/*  651 */       paramVector4d.x = this.m10;
/*  652 */       paramVector4d.y = this.m11;
/*  653 */       paramVector4d.z = this.m12;
/*  654 */       paramVector4d.w = this.m13;
/*  655 */     } else if (paramInt == 2) {
/*  656 */       paramVector4d.x = this.m20;
/*  657 */       paramVector4d.y = this.m21;
/*  658 */       paramVector4d.z = this.m22;
/*  659 */       paramVector4d.w = this.m23;
/*  660 */     } else if (paramInt == 3) {
/*  661 */       paramVector4d.x = this.m30;
/*  662 */       paramVector4d.y = this.m31;
/*  663 */       paramVector4d.z = this.m32;
/*  664 */       paramVector4d.w = this.m33;
/*      */     } else {
/*  666 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d2"));
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
/*  677 */     if (paramInt == 0) {
/*  678 */       paramArrayOfdouble[0] = this.m00;
/*  679 */       paramArrayOfdouble[1] = this.m01;
/*  680 */       paramArrayOfdouble[2] = this.m02;
/*  681 */       paramArrayOfdouble[3] = this.m03;
/*  682 */     } else if (paramInt == 1) {
/*  683 */       paramArrayOfdouble[0] = this.m10;
/*  684 */       paramArrayOfdouble[1] = this.m11;
/*  685 */       paramArrayOfdouble[2] = this.m12;
/*  686 */       paramArrayOfdouble[3] = this.m13;
/*  687 */     } else if (paramInt == 2) {
/*  688 */       paramArrayOfdouble[0] = this.m20;
/*  689 */       paramArrayOfdouble[1] = this.m21;
/*  690 */       paramArrayOfdouble[2] = this.m22;
/*  691 */       paramArrayOfdouble[3] = this.m23;
/*  692 */     } else if (paramInt == 3) {
/*  693 */       paramArrayOfdouble[0] = this.m30;
/*  694 */       paramArrayOfdouble[1] = this.m31;
/*  695 */       paramArrayOfdouble[2] = this.m32;
/*  696 */       paramArrayOfdouble[3] = this.m33;
/*      */     } else {
/*      */       
/*  699 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d2"));
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
/*      */   public final void getColumn(int paramInt, Vector4d paramVector4d) {
/*  712 */     if (paramInt == 0) {
/*  713 */       paramVector4d.x = this.m00;
/*  714 */       paramVector4d.y = this.m10;
/*  715 */       paramVector4d.z = this.m20;
/*  716 */       paramVector4d.w = this.m30;
/*  717 */     } else if (paramInt == 1) {
/*  718 */       paramVector4d.x = this.m01;
/*  719 */       paramVector4d.y = this.m11;
/*  720 */       paramVector4d.z = this.m21;
/*  721 */       paramVector4d.w = this.m31;
/*  722 */     } else if (paramInt == 2) {
/*  723 */       paramVector4d.x = this.m02;
/*  724 */       paramVector4d.y = this.m12;
/*  725 */       paramVector4d.z = this.m22;
/*  726 */       paramVector4d.w = this.m32;
/*  727 */     } else if (paramInt == 3) {
/*  728 */       paramVector4d.x = this.m03;
/*  729 */       paramVector4d.y = this.m13;
/*  730 */       paramVector4d.z = this.m23;
/*  731 */       paramVector4d.w = this.m33;
/*      */     } else {
/*  733 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d3"));
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
/*      */   public final void getColumn(int paramInt, double[] paramArrayOfdouble) {
/*  748 */     if (paramInt == 0) {
/*  749 */       paramArrayOfdouble[0] = this.m00;
/*  750 */       paramArrayOfdouble[1] = this.m10;
/*  751 */       paramArrayOfdouble[2] = this.m20;
/*  752 */       paramArrayOfdouble[3] = this.m30;
/*  753 */     } else if (paramInt == 1) {
/*  754 */       paramArrayOfdouble[0] = this.m01;
/*  755 */       paramArrayOfdouble[1] = this.m11;
/*  756 */       paramArrayOfdouble[2] = this.m21;
/*  757 */       paramArrayOfdouble[3] = this.m31;
/*  758 */     } else if (paramInt == 2) {
/*  759 */       paramArrayOfdouble[0] = this.m02;
/*  760 */       paramArrayOfdouble[1] = this.m12;
/*  761 */       paramArrayOfdouble[2] = this.m22;
/*  762 */       paramArrayOfdouble[3] = this.m32;
/*  763 */     } else if (paramInt == 3) {
/*  764 */       paramArrayOfdouble[0] = this.m03;
/*  765 */       paramArrayOfdouble[1] = this.m13;
/*  766 */       paramArrayOfdouble[2] = this.m23;
/*  767 */       paramArrayOfdouble[3] = this.m33;
/*      */     } else {
/*  769 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d3"));
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
/*      */   public final void get(Matrix3d paramMatrix3d) {
/*  785 */     double[] arrayOfDouble1 = new double[9];
/*  786 */     double[] arrayOfDouble2 = new double[3];
/*  787 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/*  789 */     paramMatrix3d.m00 = arrayOfDouble1[0];
/*  790 */     paramMatrix3d.m01 = arrayOfDouble1[1];
/*  791 */     paramMatrix3d.m02 = arrayOfDouble1[2];
/*      */     
/*  793 */     paramMatrix3d.m10 = arrayOfDouble1[3];
/*  794 */     paramMatrix3d.m11 = arrayOfDouble1[4];
/*  795 */     paramMatrix3d.m12 = arrayOfDouble1[5];
/*      */     
/*  797 */     paramMatrix3d.m20 = arrayOfDouble1[6];
/*  798 */     paramMatrix3d.m21 = arrayOfDouble1[7];
/*  799 */     paramMatrix3d.m22 = arrayOfDouble1[8];
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
/*      */   public final void get(Matrix3f paramMatrix3f) {
/*  812 */     double[] arrayOfDouble1 = new double[9];
/*  813 */     double[] arrayOfDouble2 = new double[3];
/*      */     
/*  815 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/*  817 */     paramMatrix3f.m00 = (float)arrayOfDouble1[0];
/*  818 */     paramMatrix3f.m01 = (float)arrayOfDouble1[1];
/*  819 */     paramMatrix3f.m02 = (float)arrayOfDouble1[2];
/*      */     
/*  821 */     paramMatrix3f.m10 = (float)arrayOfDouble1[3];
/*  822 */     paramMatrix3f.m11 = (float)arrayOfDouble1[4];
/*  823 */     paramMatrix3f.m12 = (float)arrayOfDouble1[5];
/*      */     
/*  825 */     paramMatrix3f.m20 = (float)arrayOfDouble1[6];
/*  826 */     paramMatrix3f.m21 = (float)arrayOfDouble1[7];
/*  827 */     paramMatrix3f.m22 = (float)arrayOfDouble1[8];
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
/*      */   public final double get(Matrix3d paramMatrix3d, Vector3d paramVector3d) {
/*  841 */     double[] arrayOfDouble1 = new double[9];
/*  842 */     double[] arrayOfDouble2 = new double[3];
/*  843 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/*  845 */     paramMatrix3d.m00 = arrayOfDouble1[0];
/*  846 */     paramMatrix3d.m01 = arrayOfDouble1[1];
/*  847 */     paramMatrix3d.m02 = arrayOfDouble1[2];
/*      */     
/*  849 */     paramMatrix3d.m10 = arrayOfDouble1[3];
/*  850 */     paramMatrix3d.m11 = arrayOfDouble1[4];
/*  851 */     paramMatrix3d.m12 = arrayOfDouble1[5];
/*      */     
/*  853 */     paramMatrix3d.m20 = arrayOfDouble1[6];
/*  854 */     paramMatrix3d.m21 = arrayOfDouble1[7];
/*  855 */     paramMatrix3d.m22 = arrayOfDouble1[8];
/*      */     
/*  857 */     paramVector3d.x = this.m03;
/*  858 */     paramVector3d.y = this.m13;
/*  859 */     paramVector3d.z = this.m23;
/*      */     
/*  861 */     return Matrix3d.max3(arrayOfDouble2);
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
/*      */   public final double get(Matrix3f paramMatrix3f, Vector3d paramVector3d) {
/*  875 */     double[] arrayOfDouble1 = new double[9];
/*  876 */     double[] arrayOfDouble2 = new double[3];
/*  877 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/*  879 */     paramMatrix3f.m00 = (float)arrayOfDouble1[0];
/*  880 */     paramMatrix3f.m01 = (float)arrayOfDouble1[1];
/*  881 */     paramMatrix3f.m02 = (float)arrayOfDouble1[2];
/*      */     
/*  883 */     paramMatrix3f.m10 = (float)arrayOfDouble1[3];
/*  884 */     paramMatrix3f.m11 = (float)arrayOfDouble1[4];
/*  885 */     paramMatrix3f.m12 = (float)arrayOfDouble1[5];
/*      */     
/*  887 */     paramMatrix3f.m20 = (float)arrayOfDouble1[6];
/*  888 */     paramMatrix3f.m21 = (float)arrayOfDouble1[7];
/*  889 */     paramMatrix3f.m22 = (float)arrayOfDouble1[8];
/*      */     
/*  891 */     paramVector3d.x = this.m03;
/*  892 */     paramVector3d.y = this.m13;
/*  893 */     paramVector3d.z = this.m23;
/*      */     
/*  895 */     return Matrix3d.max3(arrayOfDouble2);
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
/*      */   public final void get(Quat4f paramQuat4f) {
/*  908 */     double[] arrayOfDouble1 = new double[9];
/*  909 */     double[] arrayOfDouble2 = new double[3];
/*  910 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */ 
/*      */ 
/*      */     
/*  914 */     double d = 0.25D * (1.0D + arrayOfDouble1[0] + arrayOfDouble1[4] + arrayOfDouble1[8]);
/*  915 */     if (((d < 0.0D) ? -d : d) >= 1.0E-30D) {
/*  916 */       paramQuat4f.w = (float)Math.sqrt(d);
/*  917 */       d = 0.25D / paramQuat4f.w;
/*  918 */       paramQuat4f.x = (float)((arrayOfDouble1[7] - arrayOfDouble1[5]) * d);
/*  919 */       paramQuat4f.y = (float)((arrayOfDouble1[2] - arrayOfDouble1[6]) * d);
/*  920 */       paramQuat4f.z = (float)((arrayOfDouble1[3] - arrayOfDouble1[1]) * d);
/*      */       
/*      */       return;
/*      */     } 
/*  924 */     paramQuat4f.w = 0.0F;
/*  925 */     d = -0.5D * (arrayOfDouble1[4] + arrayOfDouble1[8]);
/*  926 */     if (((d < 0.0D) ? -d : d) >= 1.0E-30D) {
/*  927 */       paramQuat4f.x = (float)Math.sqrt(d);
/*  928 */       d = 0.5D / paramQuat4f.x;
/*  929 */       paramQuat4f.y = (float)(arrayOfDouble1[3] * d);
/*  930 */       paramQuat4f.z = (float)(arrayOfDouble1[6] * d);
/*      */       
/*      */       return;
/*      */     } 
/*  934 */     paramQuat4f.x = 0.0F;
/*  935 */     d = 0.5D * (1.0D - arrayOfDouble1[8]);
/*  936 */     if (((d < 0.0D) ? -d : d) >= 1.0E-30D) {
/*  937 */       paramQuat4f.y = (float)Math.sqrt(d);
/*  938 */       paramQuat4f.z = (float)(arrayOfDouble1[7] / 2.0D * paramQuat4f.y);
/*      */       
/*      */       return;
/*      */     } 
/*  942 */     paramQuat4f.y = 0.0F;
/*  943 */     paramQuat4f.z = 1.0F;
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
/*      */   public final void get(Quat4d paramQuat4d) {
/*  955 */     double[] arrayOfDouble1 = new double[9];
/*  956 */     double[] arrayOfDouble2 = new double[3];
/*      */     
/*  958 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */ 
/*      */ 
/*      */     
/*  962 */     double d = 0.25D * (1.0D + arrayOfDouble1[0] + arrayOfDouble1[4] + arrayOfDouble1[8]);
/*  963 */     if (((d < 0.0D) ? -d : d) >= 1.0E-30D) {
/*  964 */       paramQuat4d.w = Math.sqrt(d);
/*  965 */       d = 0.25D / paramQuat4d.w;
/*  966 */       paramQuat4d.x = (arrayOfDouble1[7] - arrayOfDouble1[5]) * d;
/*  967 */       paramQuat4d.y = (arrayOfDouble1[2] - arrayOfDouble1[6]) * d;
/*  968 */       paramQuat4d.z = (arrayOfDouble1[3] - arrayOfDouble1[1]) * d;
/*      */       
/*      */       return;
/*      */     } 
/*  972 */     paramQuat4d.w = 0.0D;
/*  973 */     d = -0.5D * (arrayOfDouble1[4] + arrayOfDouble1[8]);
/*  974 */     if (((d < 0.0D) ? -d : d) >= 1.0E-30D) {
/*  975 */       paramQuat4d.x = Math.sqrt(d);
/*  976 */       d = 0.5D / paramQuat4d.x;
/*  977 */       paramQuat4d.y = arrayOfDouble1[3] * d;
/*  978 */       paramQuat4d.z = arrayOfDouble1[6] * d;
/*      */       
/*      */       return;
/*      */     } 
/*  982 */     paramQuat4d.x = 0.0D;
/*  983 */     d = 0.5D * (1.0D - arrayOfDouble1[8]);
/*  984 */     if (((d < 0.0D) ? -d : d) >= 1.0E-30D) {
/*  985 */       paramQuat4d.y = Math.sqrt(d);
/*  986 */       paramQuat4d.z = arrayOfDouble1[7] / 2.0D * paramQuat4d.y;
/*      */       
/*      */       return;
/*      */     } 
/*  990 */     paramQuat4d.y = 0.0D;
/*  991 */     paramQuat4d.z = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Vector3d paramVector3d) {
/* 1000 */     paramVector3d.x = this.m03;
/* 1001 */     paramVector3d.y = this.m13;
/* 1002 */     paramVector3d.z = this.m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRotationScale(Matrix3f paramMatrix3f) {
/* 1012 */     paramMatrix3f.m00 = (float)this.m00; paramMatrix3f.m01 = (float)this.m01; paramMatrix3f.m02 = (float)this.m02;
/* 1013 */     paramMatrix3f.m10 = (float)this.m10; paramMatrix3f.m11 = (float)this.m11; paramMatrix3f.m12 = (float)this.m12;
/* 1014 */     paramMatrix3f.m20 = (float)this.m20; paramMatrix3f.m21 = (float)this.m21; paramMatrix3f.m22 = (float)this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRotationScale(Matrix3d paramMatrix3d) {
/* 1024 */     paramMatrix3d.m00 = this.m00; paramMatrix3d.m01 = this.m01; paramMatrix3d.m02 = this.m02;
/* 1025 */     paramMatrix3d.m10 = this.m10; paramMatrix3d.m11 = this.m11; paramMatrix3d.m12 = this.m12;
/* 1026 */     paramMatrix3d.m20 = this.m20; paramMatrix3d.m21 = this.m21; paramMatrix3d.m22 = this.m22;
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
/*      */   public final double getScale() {
/* 1039 */     double[] arrayOfDouble1 = new double[9];
/* 1040 */     double[] arrayOfDouble2 = new double[3];
/* 1041 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/* 1043 */     return Matrix3d.max3(arrayOfDouble2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRotationScale(Matrix3d paramMatrix3d) {
/* 1054 */     this.m00 = paramMatrix3d.m00; this.m01 = paramMatrix3d.m01; this.m02 = paramMatrix3d.m02;
/* 1055 */     this.m10 = paramMatrix3d.m10; this.m11 = paramMatrix3d.m11; this.m12 = paramMatrix3d.m12;
/* 1056 */     this.m20 = paramMatrix3d.m20; this.m21 = paramMatrix3d.m21; this.m22 = paramMatrix3d.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRotationScale(Matrix3f paramMatrix3f) {
/* 1066 */     this.m00 = paramMatrix3f.m00; this.m01 = paramMatrix3f.m01; this.m02 = paramMatrix3f.m02;
/* 1067 */     this.m10 = paramMatrix3f.m10; this.m11 = paramMatrix3f.m11; this.m12 = paramMatrix3f.m12;
/* 1068 */     this.m20 = paramMatrix3f.m20; this.m21 = paramMatrix3f.m21; this.m22 = paramMatrix3f.m22;
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
/* 1079 */     double[] arrayOfDouble1 = new double[9];
/* 1080 */     double[] arrayOfDouble2 = new double[3];
/*      */     
/* 1082 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/* 1084 */     this.m00 = arrayOfDouble1[0] * paramDouble;
/* 1085 */     this.m01 = arrayOfDouble1[1] * paramDouble;
/* 1086 */     this.m02 = arrayOfDouble1[2] * paramDouble;
/*      */     
/* 1088 */     this.m10 = arrayOfDouble1[3] * paramDouble;
/* 1089 */     this.m11 = arrayOfDouble1[4] * paramDouble;
/* 1090 */     this.m12 = arrayOfDouble1[5] * paramDouble;
/*      */     
/* 1092 */     this.m20 = arrayOfDouble1[6] * paramDouble;
/* 1093 */     this.m21 = arrayOfDouble1[7] * paramDouble;
/* 1094 */     this.m22 = arrayOfDouble1[8] * paramDouble;
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
/*      */   public final void setRow(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 1108 */     switch (paramInt) {
/*      */       case 0:
/* 1110 */         this.m00 = paramDouble1;
/* 1111 */         this.m01 = paramDouble2;
/* 1112 */         this.m02 = paramDouble3;
/* 1113 */         this.m03 = paramDouble4;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1117 */         this.m10 = paramDouble1;
/* 1118 */         this.m11 = paramDouble2;
/* 1119 */         this.m12 = paramDouble3;
/* 1120 */         this.m13 = paramDouble4;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1124 */         this.m20 = paramDouble1;
/* 1125 */         this.m21 = paramDouble2;
/* 1126 */         this.m22 = paramDouble3;
/* 1127 */         this.m23 = paramDouble4;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1131 */         this.m30 = paramDouble1;
/* 1132 */         this.m31 = paramDouble2;
/* 1133 */         this.m32 = paramDouble3;
/* 1134 */         this.m33 = paramDouble4;
/*      */         return;
/*      */     } 
/*      */     
/* 1138 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d4"));
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
/*      */   public final void setRow(int paramInt, Vector4d paramVector4d) {
/* 1150 */     switch (paramInt) {
/*      */       case 0:
/* 1152 */         this.m00 = paramVector4d.x;
/* 1153 */         this.m01 = paramVector4d.y;
/* 1154 */         this.m02 = paramVector4d.z;
/* 1155 */         this.m03 = paramVector4d.w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1159 */         this.m10 = paramVector4d.x;
/* 1160 */         this.m11 = paramVector4d.y;
/* 1161 */         this.m12 = paramVector4d.z;
/* 1162 */         this.m13 = paramVector4d.w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1166 */         this.m20 = paramVector4d.x;
/* 1167 */         this.m21 = paramVector4d.y;
/* 1168 */         this.m22 = paramVector4d.z;
/* 1169 */         this.m23 = paramVector4d.w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1173 */         this.m30 = paramVector4d.x;
/* 1174 */         this.m31 = paramVector4d.y;
/* 1175 */         this.m32 = paramVector4d.z;
/* 1176 */         this.m33 = paramVector4d.w;
/*      */         return;
/*      */     } 
/*      */     
/* 1180 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d4"));
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
/* 1191 */     switch (paramInt) {
/*      */       case 0:
/* 1193 */         this.m00 = paramArrayOfdouble[0];
/* 1194 */         this.m01 = paramArrayOfdouble[1];
/* 1195 */         this.m02 = paramArrayOfdouble[2];
/* 1196 */         this.m03 = paramArrayOfdouble[3];
/*      */         return;
/*      */       
/*      */       case 1:
/* 1200 */         this.m10 = paramArrayOfdouble[0];
/* 1201 */         this.m11 = paramArrayOfdouble[1];
/* 1202 */         this.m12 = paramArrayOfdouble[2];
/* 1203 */         this.m13 = paramArrayOfdouble[3];
/*      */         return;
/*      */       
/*      */       case 2:
/* 1207 */         this.m20 = paramArrayOfdouble[0];
/* 1208 */         this.m21 = paramArrayOfdouble[1];
/* 1209 */         this.m22 = paramArrayOfdouble[2];
/* 1210 */         this.m23 = paramArrayOfdouble[3];
/*      */         return;
/*      */       
/*      */       case 3:
/* 1214 */         this.m30 = paramArrayOfdouble[0];
/* 1215 */         this.m31 = paramArrayOfdouble[1];
/* 1216 */         this.m32 = paramArrayOfdouble[2];
/* 1217 */         this.m33 = paramArrayOfdouble[3];
/*      */         return;
/*      */     } 
/*      */     
/* 1221 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d4"));
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
/*      */   public final void setColumn(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 1235 */     switch (paramInt) {
/*      */       case 0:
/* 1237 */         this.m00 = paramDouble1;
/* 1238 */         this.m10 = paramDouble2;
/* 1239 */         this.m20 = paramDouble3;
/* 1240 */         this.m30 = paramDouble4;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1244 */         this.m01 = paramDouble1;
/* 1245 */         this.m11 = paramDouble2;
/* 1246 */         this.m21 = paramDouble3;
/* 1247 */         this.m31 = paramDouble4;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1251 */         this.m02 = paramDouble1;
/* 1252 */         this.m12 = paramDouble2;
/* 1253 */         this.m22 = paramDouble3;
/* 1254 */         this.m32 = paramDouble4;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1258 */         this.m03 = paramDouble1;
/* 1259 */         this.m13 = paramDouble2;
/* 1260 */         this.m23 = paramDouble3;
/* 1261 */         this.m33 = paramDouble4;
/*      */         return;
/*      */     } 
/*      */     
/* 1265 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d7"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int paramInt, Vector4d paramVector4d) {
/* 1276 */     switch (paramInt) {
/*      */       case 0:
/* 1278 */         this.m00 = paramVector4d.x;
/* 1279 */         this.m10 = paramVector4d.y;
/* 1280 */         this.m20 = paramVector4d.z;
/* 1281 */         this.m30 = paramVector4d.w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1285 */         this.m01 = paramVector4d.x;
/* 1286 */         this.m11 = paramVector4d.y;
/* 1287 */         this.m21 = paramVector4d.z;
/* 1288 */         this.m31 = paramVector4d.w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1292 */         this.m02 = paramVector4d.x;
/* 1293 */         this.m12 = paramVector4d.y;
/* 1294 */         this.m22 = paramVector4d.z;
/* 1295 */         this.m32 = paramVector4d.w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1299 */         this.m03 = paramVector4d.x;
/* 1300 */         this.m13 = paramVector4d.y;
/* 1301 */         this.m23 = paramVector4d.z;
/* 1302 */         this.m33 = paramVector4d.w;
/*      */         return;
/*      */     } 
/*      */     
/* 1306 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d7"));
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
/* 1317 */     switch (paramInt) {
/*      */       case 0:
/* 1319 */         this.m00 = paramArrayOfdouble[0];
/* 1320 */         this.m10 = paramArrayOfdouble[1];
/* 1321 */         this.m20 = paramArrayOfdouble[2];
/* 1322 */         this.m30 = paramArrayOfdouble[3];
/*      */         return;
/*      */       
/*      */       case 1:
/* 1326 */         this.m01 = paramArrayOfdouble[0];
/* 1327 */         this.m11 = paramArrayOfdouble[1];
/* 1328 */         this.m21 = paramArrayOfdouble[2];
/* 1329 */         this.m31 = paramArrayOfdouble[3];
/*      */         return;
/*      */       
/*      */       case 2:
/* 1333 */         this.m02 = paramArrayOfdouble[0];
/* 1334 */         this.m12 = paramArrayOfdouble[1];
/* 1335 */         this.m22 = paramArrayOfdouble[2];
/* 1336 */         this.m32 = paramArrayOfdouble[3];
/*      */         return;
/*      */       
/*      */       case 3:
/* 1340 */         this.m03 = paramArrayOfdouble[0];
/* 1341 */         this.m13 = paramArrayOfdouble[1];
/* 1342 */         this.m23 = paramArrayOfdouble[2];
/* 1343 */         this.m33 = paramArrayOfdouble[3];
/*      */         return;
/*      */     } 
/*      */     
/* 1347 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d7"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(double paramDouble) {
/* 1357 */     this.m00 += paramDouble;
/* 1358 */     this.m01 += paramDouble;
/* 1359 */     this.m02 += paramDouble;
/* 1360 */     this.m03 += paramDouble;
/* 1361 */     this.m10 += paramDouble;
/* 1362 */     this.m11 += paramDouble;
/* 1363 */     this.m12 += paramDouble;
/* 1364 */     this.m13 += paramDouble;
/* 1365 */     this.m20 += paramDouble;
/* 1366 */     this.m21 += paramDouble;
/* 1367 */     this.m22 += paramDouble;
/* 1368 */     this.m23 += paramDouble;
/* 1369 */     this.m30 += paramDouble;
/* 1370 */     this.m31 += paramDouble;
/* 1371 */     this.m32 += paramDouble;
/* 1372 */     this.m33 += paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(double paramDouble, Matrix4d paramMatrix4d) {
/* 1383 */     paramMatrix4d.m00 += paramDouble;
/* 1384 */     paramMatrix4d.m01 += paramDouble;
/* 1385 */     paramMatrix4d.m02 += paramDouble;
/* 1386 */     paramMatrix4d.m03 += paramDouble;
/* 1387 */     paramMatrix4d.m10 += paramDouble;
/* 1388 */     paramMatrix4d.m11 += paramDouble;
/* 1389 */     paramMatrix4d.m12 += paramDouble;
/* 1390 */     paramMatrix4d.m13 += paramDouble;
/* 1391 */     paramMatrix4d.m20 += paramDouble;
/* 1392 */     paramMatrix4d.m21 += paramDouble;
/* 1393 */     paramMatrix4d.m22 += paramDouble;
/* 1394 */     paramMatrix4d.m23 += paramDouble;
/* 1395 */     paramMatrix4d.m30 += paramDouble;
/* 1396 */     paramMatrix4d.m31 += paramDouble;
/* 1397 */     paramMatrix4d.m32 += paramDouble;
/* 1398 */     paramMatrix4d.m33 += paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix4d paramMatrix4d1, Matrix4d paramMatrix4d2) {
/* 1408 */     paramMatrix4d1.m00 += paramMatrix4d2.m00;
/* 1409 */     paramMatrix4d1.m01 += paramMatrix4d2.m01;
/* 1410 */     paramMatrix4d1.m02 += paramMatrix4d2.m02;
/* 1411 */     paramMatrix4d1.m03 += paramMatrix4d2.m03;
/*      */     
/* 1413 */     paramMatrix4d1.m10 += paramMatrix4d2.m10;
/* 1414 */     paramMatrix4d1.m11 += paramMatrix4d2.m11;
/* 1415 */     paramMatrix4d1.m12 += paramMatrix4d2.m12;
/* 1416 */     paramMatrix4d1.m13 += paramMatrix4d2.m13;
/*      */     
/* 1418 */     paramMatrix4d1.m20 += paramMatrix4d2.m20;
/* 1419 */     paramMatrix4d1.m21 += paramMatrix4d2.m21;
/* 1420 */     paramMatrix4d1.m22 += paramMatrix4d2.m22;
/* 1421 */     paramMatrix4d1.m23 += paramMatrix4d2.m23;
/*      */     
/* 1423 */     paramMatrix4d1.m30 += paramMatrix4d2.m30;
/* 1424 */     paramMatrix4d1.m31 += paramMatrix4d2.m31;
/* 1425 */     paramMatrix4d1.m32 += paramMatrix4d2.m32;
/* 1426 */     paramMatrix4d1.m33 += paramMatrix4d2.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix4d paramMatrix4d) {
/* 1435 */     this.m00 += paramMatrix4d.m00;
/* 1436 */     this.m01 += paramMatrix4d.m01;
/* 1437 */     this.m02 += paramMatrix4d.m02;
/* 1438 */     this.m03 += paramMatrix4d.m03;
/*      */     
/* 1440 */     this.m10 += paramMatrix4d.m10;
/* 1441 */     this.m11 += paramMatrix4d.m11;
/* 1442 */     this.m12 += paramMatrix4d.m12;
/* 1443 */     this.m13 += paramMatrix4d.m13;
/*      */     
/* 1445 */     this.m20 += paramMatrix4d.m20;
/* 1446 */     this.m21 += paramMatrix4d.m21;
/* 1447 */     this.m22 += paramMatrix4d.m22;
/* 1448 */     this.m23 += paramMatrix4d.m23;
/*      */     
/* 1450 */     this.m30 += paramMatrix4d.m30;
/* 1451 */     this.m31 += paramMatrix4d.m31;
/* 1452 */     this.m32 += paramMatrix4d.m32;
/* 1453 */     this.m33 += paramMatrix4d.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix4d paramMatrix4d1, Matrix4d paramMatrix4d2) {
/* 1464 */     paramMatrix4d1.m00 -= paramMatrix4d2.m00;
/* 1465 */     paramMatrix4d1.m01 -= paramMatrix4d2.m01;
/* 1466 */     paramMatrix4d1.m02 -= paramMatrix4d2.m02;
/* 1467 */     paramMatrix4d1.m03 -= paramMatrix4d2.m03;
/*      */     
/* 1469 */     paramMatrix4d1.m10 -= paramMatrix4d2.m10;
/* 1470 */     paramMatrix4d1.m11 -= paramMatrix4d2.m11;
/* 1471 */     paramMatrix4d1.m12 -= paramMatrix4d2.m12;
/* 1472 */     paramMatrix4d1.m13 -= paramMatrix4d2.m13;
/*      */     
/* 1474 */     paramMatrix4d1.m20 -= paramMatrix4d2.m20;
/* 1475 */     paramMatrix4d1.m21 -= paramMatrix4d2.m21;
/* 1476 */     paramMatrix4d1.m22 -= paramMatrix4d2.m22;
/* 1477 */     paramMatrix4d1.m23 -= paramMatrix4d2.m23;
/*      */     
/* 1479 */     paramMatrix4d1.m30 -= paramMatrix4d2.m30;
/* 1480 */     paramMatrix4d1.m31 -= paramMatrix4d2.m31;
/* 1481 */     paramMatrix4d1.m32 -= paramMatrix4d2.m32;
/* 1482 */     paramMatrix4d1.m33 -= paramMatrix4d2.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix4d paramMatrix4d) {
/* 1493 */     this.m00 -= paramMatrix4d.m00;
/* 1494 */     this.m01 -= paramMatrix4d.m01;
/* 1495 */     this.m02 -= paramMatrix4d.m02;
/* 1496 */     this.m03 -= paramMatrix4d.m03;
/*      */     
/* 1498 */     this.m10 -= paramMatrix4d.m10;
/* 1499 */     this.m11 -= paramMatrix4d.m11;
/* 1500 */     this.m12 -= paramMatrix4d.m12;
/* 1501 */     this.m13 -= paramMatrix4d.m13;
/*      */     
/* 1503 */     this.m20 -= paramMatrix4d.m20;
/* 1504 */     this.m21 -= paramMatrix4d.m21;
/* 1505 */     this.m22 -= paramMatrix4d.m22;
/* 1506 */     this.m23 -= paramMatrix4d.m23;
/*      */     
/* 1508 */     this.m30 -= paramMatrix4d.m30;
/* 1509 */     this.m31 -= paramMatrix4d.m31;
/* 1510 */     this.m32 -= paramMatrix4d.m32;
/* 1511 */     this.m33 -= paramMatrix4d.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose() {
/* 1521 */     double d = this.m10;
/* 1522 */     this.m10 = this.m01;
/* 1523 */     this.m01 = d;
/*      */     
/* 1525 */     d = this.m20;
/* 1526 */     this.m20 = this.m02;
/* 1527 */     this.m02 = d;
/*      */     
/* 1529 */     d = this.m30;
/* 1530 */     this.m30 = this.m03;
/* 1531 */     this.m03 = d;
/*      */     
/* 1533 */     d = this.m21;
/* 1534 */     this.m21 = this.m12;
/* 1535 */     this.m12 = d;
/*      */     
/* 1537 */     d = this.m31;
/* 1538 */     this.m31 = this.m13;
/* 1539 */     this.m13 = d;
/*      */     
/* 1541 */     d = this.m32;
/* 1542 */     this.m32 = this.m23;
/* 1543 */     this.m23 = d;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose(Matrix4d paramMatrix4d) {
/* 1552 */     if (this != paramMatrix4d) {
/* 1553 */       this.m00 = paramMatrix4d.m00;
/* 1554 */       this.m01 = paramMatrix4d.m10;
/* 1555 */       this.m02 = paramMatrix4d.m20;
/* 1556 */       this.m03 = paramMatrix4d.m30;
/*      */       
/* 1558 */       this.m10 = paramMatrix4d.m01;
/* 1559 */       this.m11 = paramMatrix4d.m11;
/* 1560 */       this.m12 = paramMatrix4d.m21;
/* 1561 */       this.m13 = paramMatrix4d.m31;
/*      */       
/* 1563 */       this.m20 = paramMatrix4d.m02;
/* 1564 */       this.m21 = paramMatrix4d.m12;
/* 1565 */       this.m22 = paramMatrix4d.m22;
/* 1566 */       this.m23 = paramMatrix4d.m32;
/*      */       
/* 1568 */       this.m30 = paramMatrix4d.m03;
/* 1569 */       this.m31 = paramMatrix4d.m13;
/* 1570 */       this.m32 = paramMatrix4d.m23;
/* 1571 */       this.m33 = paramMatrix4d.m33;
/*      */     } else {
/* 1573 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(double[] paramArrayOfdouble) {
/* 1584 */     this.m00 = paramArrayOfdouble[0];
/* 1585 */     this.m01 = paramArrayOfdouble[1];
/* 1586 */     this.m02 = paramArrayOfdouble[2];
/* 1587 */     this.m03 = paramArrayOfdouble[3];
/* 1588 */     this.m10 = paramArrayOfdouble[4];
/* 1589 */     this.m11 = paramArrayOfdouble[5];
/* 1590 */     this.m12 = paramArrayOfdouble[6];
/* 1591 */     this.m13 = paramArrayOfdouble[7];
/* 1592 */     this.m20 = paramArrayOfdouble[8];
/* 1593 */     this.m21 = paramArrayOfdouble[9];
/* 1594 */     this.m22 = paramArrayOfdouble[10];
/* 1595 */     this.m23 = paramArrayOfdouble[11];
/* 1596 */     this.m30 = paramArrayOfdouble[12];
/* 1597 */     this.m31 = paramArrayOfdouble[13];
/* 1598 */     this.m32 = paramArrayOfdouble[14];
/* 1599 */     this.m33 = paramArrayOfdouble[15];
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
/* 1611 */     this.m00 = paramMatrix3f.m00; this.m01 = paramMatrix3f.m01; this.m02 = paramMatrix3f.m02; this.m03 = 0.0D;
/* 1612 */     this.m10 = paramMatrix3f.m10; this.m11 = paramMatrix3f.m11; this.m12 = paramMatrix3f.m12; this.m13 = 0.0D;
/* 1613 */     this.m20 = paramMatrix3f.m20; this.m21 = paramMatrix3f.m21; this.m22 = paramMatrix3f.m22; this.m23 = 0.0D;
/* 1614 */     this.m30 = 0.0D; this.m31 = 0.0D; this.m32 = 0.0D; this.m33 = 1.0D;
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
/* 1626 */     this.m00 = paramMatrix3d.m00; this.m01 = paramMatrix3d.m01; this.m02 = paramMatrix3d.m02; this.m03 = 0.0D;
/* 1627 */     this.m10 = paramMatrix3d.m10; this.m11 = paramMatrix3d.m11; this.m12 = paramMatrix3d.m12; this.m13 = 0.0D;
/* 1628 */     this.m20 = paramMatrix3d.m20; this.m21 = paramMatrix3d.m21; this.m22 = paramMatrix3d.m22; this.m23 = 0.0D;
/* 1629 */     this.m30 = 0.0D; this.m31 = 0.0D; this.m32 = 0.0D; this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4d paramQuat4d) {
/* 1639 */     this.m00 = 1.0D - 2.0D * paramQuat4d.y * paramQuat4d.y - 2.0D * paramQuat4d.z * paramQuat4d.z;
/* 1640 */     this.m10 = 2.0D * (paramQuat4d.x * paramQuat4d.y + paramQuat4d.w * paramQuat4d.z);
/* 1641 */     this.m20 = 2.0D * (paramQuat4d.x * paramQuat4d.z - paramQuat4d.w * paramQuat4d.y);
/*      */     
/* 1643 */     this.m01 = 2.0D * (paramQuat4d.x * paramQuat4d.y - paramQuat4d.w * paramQuat4d.z);
/* 1644 */     this.m11 = 1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.z * paramQuat4d.z;
/* 1645 */     this.m21 = 2.0D * (paramQuat4d.y * paramQuat4d.z + paramQuat4d.w * paramQuat4d.x);
/*      */     
/* 1647 */     this.m02 = 2.0D * (paramQuat4d.x * paramQuat4d.z + paramQuat4d.w * paramQuat4d.y);
/* 1648 */     this.m12 = 2.0D * (paramQuat4d.y * paramQuat4d.z - paramQuat4d.w * paramQuat4d.x);
/* 1649 */     this.m22 = 1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.y * paramQuat4d.y;
/*      */     
/* 1651 */     this.m03 = 0.0D;
/* 1652 */     this.m13 = 0.0D;
/* 1653 */     this.m23 = 0.0D;
/*      */     
/* 1655 */     this.m30 = 0.0D;
/* 1656 */     this.m31 = 0.0D;
/* 1657 */     this.m32 = 0.0D;
/* 1658 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4d paramAxisAngle4d) {
/* 1668 */     double d = Math.sqrt(paramAxisAngle4d.x * paramAxisAngle4d.x + paramAxisAngle4d.y * paramAxisAngle4d.y + paramAxisAngle4d.z * paramAxisAngle4d.z);
/*      */     
/* 1670 */     if (d < 1.0E-10D) {
/* 1671 */       this.m00 = 1.0D;
/* 1672 */       this.m01 = 0.0D;
/* 1673 */       this.m02 = 0.0D;
/*      */       
/* 1675 */       this.m10 = 0.0D;
/* 1676 */       this.m11 = 1.0D;
/* 1677 */       this.m12 = 0.0D;
/*      */       
/* 1679 */       this.m20 = 0.0D;
/* 1680 */       this.m21 = 0.0D;
/* 1681 */       this.m22 = 1.0D;
/*      */     } else {
/* 1683 */       d = 1.0D / d;
/* 1684 */       double d1 = paramAxisAngle4d.x * d;
/* 1685 */       double d2 = paramAxisAngle4d.y * d;
/* 1686 */       double d3 = paramAxisAngle4d.z * d;
/*      */       
/* 1688 */       double d4 = Math.sin(paramAxisAngle4d.angle);
/* 1689 */       double d5 = Math.cos(paramAxisAngle4d.angle);
/* 1690 */       double d6 = 1.0D - d5;
/*      */       
/* 1692 */       double d7 = d1 * d3;
/* 1693 */       double d8 = d1 * d2;
/* 1694 */       double d9 = d2 * d3;
/*      */       
/* 1696 */       this.m00 = d6 * d1 * d1 + d5;
/* 1697 */       this.m01 = d6 * d8 - d4 * d3;
/* 1698 */       this.m02 = d6 * d7 + d4 * d2;
/*      */       
/* 1700 */       this.m10 = d6 * d8 + d4 * d3;
/* 1701 */       this.m11 = d6 * d2 * d2 + d5;
/* 1702 */       this.m12 = d6 * d9 - d4 * d1;
/*      */       
/* 1704 */       this.m20 = d6 * d7 - d4 * d2;
/* 1705 */       this.m21 = d6 * d9 + d4 * d1;
/* 1706 */       this.m22 = d6 * d3 * d3 + d5;
/*      */     } 
/*      */     
/* 1709 */     this.m03 = 0.0D;
/* 1710 */     this.m13 = 0.0D;
/* 1711 */     this.m23 = 0.0D;
/*      */     
/* 1713 */     this.m30 = 0.0D;
/* 1714 */     this.m31 = 0.0D;
/* 1715 */     this.m32 = 0.0D;
/* 1716 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4f paramQuat4f) {
/* 1726 */     this.m00 = 1.0D - 2.0D * paramQuat4f.y * paramQuat4f.y - 2.0D * paramQuat4f.z * paramQuat4f.z;
/* 1727 */     this.m10 = 2.0D * (paramQuat4f.x * paramQuat4f.y + paramQuat4f.w * paramQuat4f.z);
/* 1728 */     this.m20 = 2.0D * (paramQuat4f.x * paramQuat4f.z - paramQuat4f.w * paramQuat4f.y);
/*      */     
/* 1730 */     this.m01 = 2.0D * (paramQuat4f.x * paramQuat4f.y - paramQuat4f.w * paramQuat4f.z);
/* 1731 */     this.m11 = 1.0D - 2.0D * paramQuat4f.x * paramQuat4f.x - 2.0D * paramQuat4f.z * paramQuat4f.z;
/* 1732 */     this.m21 = 2.0D * (paramQuat4f.y * paramQuat4f.z + paramQuat4f.w * paramQuat4f.x);
/*      */     
/* 1734 */     this.m02 = 2.0D * (paramQuat4f.x * paramQuat4f.z + paramQuat4f.w * paramQuat4f.y);
/* 1735 */     this.m12 = 2.0D * (paramQuat4f.y * paramQuat4f.z - paramQuat4f.w * paramQuat4f.x);
/* 1736 */     this.m22 = 1.0D - 2.0D * paramQuat4f.x * paramQuat4f.x - 2.0D * paramQuat4f.y * paramQuat4f.y;
/*      */     
/* 1738 */     this.m03 = 0.0D;
/* 1739 */     this.m13 = 0.0D;
/* 1740 */     this.m23 = 0.0D;
/*      */     
/* 1742 */     this.m30 = 0.0D;
/* 1743 */     this.m31 = 0.0D;
/* 1744 */     this.m32 = 0.0D;
/* 1745 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4f paramAxisAngle4f) {
/* 1755 */     double d = Math.sqrt((paramAxisAngle4f.x * paramAxisAngle4f.x + paramAxisAngle4f.y * paramAxisAngle4f.y + paramAxisAngle4f.z * paramAxisAngle4f.z));
/*      */     
/* 1757 */     if (d < 1.0E-10D) {
/* 1758 */       this.m00 = 1.0D;
/* 1759 */       this.m01 = 0.0D;
/* 1760 */       this.m02 = 0.0D;
/*      */       
/* 1762 */       this.m10 = 0.0D;
/* 1763 */       this.m11 = 1.0D;
/* 1764 */       this.m12 = 0.0D;
/*      */       
/* 1766 */       this.m20 = 0.0D;
/* 1767 */       this.m21 = 0.0D;
/* 1768 */       this.m22 = 1.0D;
/*      */     } else {
/* 1770 */       d = 1.0D / d;
/* 1771 */       double d1 = paramAxisAngle4f.x * d;
/* 1772 */       double d2 = paramAxisAngle4f.y * d;
/* 1773 */       double d3 = paramAxisAngle4f.z * d;
/*      */       
/* 1775 */       double d4 = Math.sin(paramAxisAngle4f.angle);
/* 1776 */       double d5 = Math.cos(paramAxisAngle4f.angle);
/* 1777 */       double d6 = 1.0D - d5;
/*      */       
/* 1779 */       double d7 = d1 * d3;
/* 1780 */       double d8 = d1 * d2;
/* 1781 */       double d9 = d2 * d3;
/*      */       
/* 1783 */       this.m00 = d6 * d1 * d1 + d5;
/* 1784 */       this.m01 = d6 * d8 - d4 * d3;
/* 1785 */       this.m02 = d6 * d7 + d4 * d2;
/*      */       
/* 1787 */       this.m10 = d6 * d8 + d4 * d3;
/* 1788 */       this.m11 = d6 * d2 * d2 + d5;
/* 1789 */       this.m12 = d6 * d9 - d4 * d1;
/*      */       
/* 1791 */       this.m20 = d6 * d7 - d4 * d2;
/* 1792 */       this.m21 = d6 * d9 + d4 * d1;
/* 1793 */       this.m22 = d6 * d3 * d3 + d5;
/*      */     } 
/* 1795 */     this.m03 = 0.0D;
/* 1796 */     this.m13 = 0.0D;
/* 1797 */     this.m23 = 0.0D;
/*      */     
/* 1799 */     this.m30 = 0.0D;
/* 1800 */     this.m31 = 0.0D;
/* 1801 */     this.m32 = 0.0D;
/* 1802 */     this.m33 = 1.0D;
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
/* 1814 */     this.m00 = paramDouble * (1.0D - 2.0D * paramQuat4d.y * paramQuat4d.y - 2.0D * paramQuat4d.z * paramQuat4d.z);
/* 1815 */     this.m10 = paramDouble * 2.0D * (paramQuat4d.x * paramQuat4d.y + paramQuat4d.w * paramQuat4d.z);
/* 1816 */     this.m20 = paramDouble * 2.0D * (paramQuat4d.x * paramQuat4d.z - paramQuat4d.w * paramQuat4d.y);
/*      */     
/* 1818 */     this.m01 = paramDouble * 2.0D * (paramQuat4d.x * paramQuat4d.y - paramQuat4d.w * paramQuat4d.z);
/* 1819 */     this.m11 = paramDouble * (1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.z * paramQuat4d.z);
/* 1820 */     this.m21 = paramDouble * 2.0D * (paramQuat4d.y * paramQuat4d.z + paramQuat4d.w * paramQuat4d.x);
/*      */     
/* 1822 */     this.m02 = paramDouble * 2.0D * (paramQuat4d.x * paramQuat4d.z + paramQuat4d.w * paramQuat4d.y);
/* 1823 */     this.m12 = paramDouble * 2.0D * (paramQuat4d.y * paramQuat4d.z - paramQuat4d.w * paramQuat4d.x);
/* 1824 */     this.m22 = paramDouble * (1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.y * paramQuat4d.y);
/*      */     
/* 1826 */     this.m03 = paramVector3d.x;
/* 1827 */     this.m13 = paramVector3d.y;
/* 1828 */     this.m23 = paramVector3d.z;
/*      */     
/* 1830 */     this.m30 = 0.0D;
/* 1831 */     this.m31 = 0.0D;
/* 1832 */     this.m32 = 0.0D;
/* 1833 */     this.m33 = 1.0D;
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
/*      */   public final void set(Quat4f paramQuat4f, Vector3d paramVector3d, double paramDouble) {
/* 1845 */     this.m00 = paramDouble * (1.0D - 2.0D * paramQuat4f.y * paramQuat4f.y - 2.0D * paramQuat4f.z * paramQuat4f.z);
/* 1846 */     this.m10 = paramDouble * 2.0D * (paramQuat4f.x * paramQuat4f.y + paramQuat4f.w * paramQuat4f.z);
/* 1847 */     this.m20 = paramDouble * 2.0D * (paramQuat4f.x * paramQuat4f.z - paramQuat4f.w * paramQuat4f.y);
/*      */     
/* 1849 */     this.m01 = paramDouble * 2.0D * (paramQuat4f.x * paramQuat4f.y - paramQuat4f.w * paramQuat4f.z);
/* 1850 */     this.m11 = paramDouble * (1.0D - 2.0D * paramQuat4f.x * paramQuat4f.x - 2.0D * paramQuat4f.z * paramQuat4f.z);
/* 1851 */     this.m21 = paramDouble * 2.0D * (paramQuat4f.y * paramQuat4f.z + paramQuat4f.w * paramQuat4f.x);
/*      */     
/* 1853 */     this.m02 = paramDouble * 2.0D * (paramQuat4f.x * paramQuat4f.z + paramQuat4f.w * paramQuat4f.y);
/* 1854 */     this.m12 = paramDouble * 2.0D * (paramQuat4f.y * paramQuat4f.z - paramQuat4f.w * paramQuat4f.x);
/* 1855 */     this.m22 = paramDouble * (1.0D - 2.0D * paramQuat4f.x * paramQuat4f.x - 2.0D * paramQuat4f.y * paramQuat4f.y);
/*      */     
/* 1857 */     this.m03 = paramVector3d.x;
/* 1858 */     this.m13 = paramVector3d.y;
/* 1859 */     this.m23 = paramVector3d.z;
/*      */     
/* 1861 */     this.m30 = 0.0D;
/* 1862 */     this.m31 = 0.0D;
/* 1863 */     this.m32 = 0.0D;
/* 1864 */     this.m33 = 1.0D;
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
/* 1876 */     this.m00 = paramFloat * (1.0D - 2.0D * paramQuat4f.y * paramQuat4f.y - 2.0D * paramQuat4f.z * paramQuat4f.z);
/* 1877 */     this.m10 = paramFloat * 2.0D * (paramQuat4f.x * paramQuat4f.y + paramQuat4f.w * paramQuat4f.z);
/* 1878 */     this.m20 = paramFloat * 2.0D * (paramQuat4f.x * paramQuat4f.z - paramQuat4f.w * paramQuat4f.y);
/*      */     
/* 1880 */     this.m01 = paramFloat * 2.0D * (paramQuat4f.x * paramQuat4f.y - paramQuat4f.w * paramQuat4f.z);
/* 1881 */     this.m11 = paramFloat * (1.0D - 2.0D * paramQuat4f.x * paramQuat4f.x - 2.0D * paramQuat4f.z * paramQuat4f.z);
/* 1882 */     this.m21 = paramFloat * 2.0D * (paramQuat4f.y * paramQuat4f.z + paramQuat4f.w * paramQuat4f.x);
/*      */     
/* 1884 */     this.m02 = paramFloat * 2.0D * (paramQuat4f.x * paramQuat4f.z + paramQuat4f.w * paramQuat4f.y);
/* 1885 */     this.m12 = paramFloat * 2.0D * (paramQuat4f.y * paramQuat4f.z - paramQuat4f.w * paramQuat4f.x);
/* 1886 */     this.m22 = paramFloat * (1.0D - 2.0D * paramQuat4f.x * paramQuat4f.x - 2.0D * paramQuat4f.y * paramQuat4f.y);
/*      */     
/* 1888 */     this.m03 = paramVector3f.x;
/* 1889 */     this.m13 = paramVector3f.y;
/* 1890 */     this.m23 = paramVector3f.z;
/*      */     
/* 1892 */     this.m30 = 0.0D;
/* 1893 */     this.m31 = 0.0D;
/* 1894 */     this.m32 = 0.0D;
/* 1895 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4f paramMatrix4f) {
/* 1905 */     this.m00 = paramMatrix4f.m00;
/* 1906 */     this.m01 = paramMatrix4f.m01;
/* 1907 */     this.m02 = paramMatrix4f.m02;
/* 1908 */     this.m03 = paramMatrix4f.m03;
/*      */     
/* 1910 */     this.m10 = paramMatrix4f.m10;
/* 1911 */     this.m11 = paramMatrix4f.m11;
/* 1912 */     this.m12 = paramMatrix4f.m12;
/* 1913 */     this.m13 = paramMatrix4f.m13;
/*      */     
/* 1915 */     this.m20 = paramMatrix4f.m20;
/* 1916 */     this.m21 = paramMatrix4f.m21;
/* 1917 */     this.m22 = paramMatrix4f.m22;
/* 1918 */     this.m23 = paramMatrix4f.m23;
/*      */     
/* 1920 */     this.m30 = paramMatrix4f.m30;
/* 1921 */     this.m31 = paramMatrix4f.m31;
/* 1922 */     this.m32 = paramMatrix4f.m32;
/* 1923 */     this.m33 = paramMatrix4f.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4d paramMatrix4d) {
/* 1933 */     this.m00 = paramMatrix4d.m00;
/* 1934 */     this.m01 = paramMatrix4d.m01;
/* 1935 */     this.m02 = paramMatrix4d.m02;
/* 1936 */     this.m03 = paramMatrix4d.m03;
/*      */     
/* 1938 */     this.m10 = paramMatrix4d.m10;
/* 1939 */     this.m11 = paramMatrix4d.m11;
/* 1940 */     this.m12 = paramMatrix4d.m12;
/* 1941 */     this.m13 = paramMatrix4d.m13;
/*      */     
/* 1943 */     this.m20 = paramMatrix4d.m20;
/* 1944 */     this.m21 = paramMatrix4d.m21;
/* 1945 */     this.m22 = paramMatrix4d.m22;
/* 1946 */     this.m23 = paramMatrix4d.m23;
/*      */     
/* 1948 */     this.m30 = paramMatrix4d.m30;
/* 1949 */     this.m31 = paramMatrix4d.m31;
/* 1950 */     this.m32 = paramMatrix4d.m32;
/* 1951 */     this.m33 = paramMatrix4d.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert(Matrix4d paramMatrix4d) {
/* 1962 */     invertGeneral(paramMatrix4d);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/* 1970 */     invertGeneral(this);
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
/*      */   final void invertGeneral(Matrix4d paramMatrix4d) {
/* 1982 */     double[] arrayOfDouble1 = new double[16];
/* 1983 */     int[] arrayOfInt = new int[4];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1988 */     double[] arrayOfDouble2 = new double[16];
/*      */     
/* 1990 */     arrayOfDouble2[0] = paramMatrix4d.m00;
/* 1991 */     arrayOfDouble2[1] = paramMatrix4d.m01;
/* 1992 */     arrayOfDouble2[2] = paramMatrix4d.m02;
/* 1993 */     arrayOfDouble2[3] = paramMatrix4d.m03;
/*      */     
/* 1995 */     arrayOfDouble2[4] = paramMatrix4d.m10;
/* 1996 */     arrayOfDouble2[5] = paramMatrix4d.m11;
/* 1997 */     arrayOfDouble2[6] = paramMatrix4d.m12;
/* 1998 */     arrayOfDouble2[7] = paramMatrix4d.m13;
/*      */     
/* 2000 */     arrayOfDouble2[8] = paramMatrix4d.m20;
/* 2001 */     arrayOfDouble2[9] = paramMatrix4d.m21;
/* 2002 */     arrayOfDouble2[10] = paramMatrix4d.m22;
/* 2003 */     arrayOfDouble2[11] = paramMatrix4d.m23;
/*      */     
/* 2005 */     arrayOfDouble2[12] = paramMatrix4d.m30;
/* 2006 */     arrayOfDouble2[13] = paramMatrix4d.m31;
/* 2007 */     arrayOfDouble2[14] = paramMatrix4d.m32;
/* 2008 */     arrayOfDouble2[15] = paramMatrix4d.m33;
/*      */ 
/*      */     
/* 2011 */     if (!luDecomposition(arrayOfDouble2, arrayOfInt))
/*      */     {
/* 2013 */       throw new SingularMatrixException(VecMathI18N.getString("Matrix4d10"));
/*      */     }
/*      */ 
/*      */     
/* 2017 */     for (byte b = 0; b < 16; ) { arrayOfDouble1[b] = 0.0D; b++; }
/* 2018 */      arrayOfDouble1[0] = 1.0D; arrayOfDouble1[5] = 1.0D; arrayOfDouble1[10] = 1.0D; arrayOfDouble1[15] = 1.0D;
/* 2019 */     luBacksubstitution(arrayOfDouble2, arrayOfInt, arrayOfDouble1);
/*      */     
/* 2021 */     this.m00 = arrayOfDouble1[0];
/* 2022 */     this.m01 = arrayOfDouble1[1];
/* 2023 */     this.m02 = arrayOfDouble1[2];
/* 2024 */     this.m03 = arrayOfDouble1[3];
/*      */     
/* 2026 */     this.m10 = arrayOfDouble1[4];
/* 2027 */     this.m11 = arrayOfDouble1[5];
/* 2028 */     this.m12 = arrayOfDouble1[6];
/* 2029 */     this.m13 = arrayOfDouble1[7];
/*      */     
/* 2031 */     this.m20 = arrayOfDouble1[8];
/* 2032 */     this.m21 = arrayOfDouble1[9];
/* 2033 */     this.m22 = arrayOfDouble1[10];
/* 2034 */     this.m23 = arrayOfDouble1[11];
/*      */     
/* 2036 */     this.m30 = arrayOfDouble1[12];
/* 2037 */     this.m31 = arrayOfDouble1[13];
/* 2038 */     this.m32 = arrayOfDouble1[14];
/* 2039 */     this.m33 = arrayOfDouble1[15];
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
/* 2066 */     double[] arrayOfDouble = new double[4];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2074 */     int j = 0;
/* 2075 */     int k = 0;
/*      */ 
/*      */     
/* 2078 */     int i = 4;
/* 2079 */     while (i-- != 0) {
/* 2080 */       double d = 0.0D;
/*      */ 
/*      */       
/* 2083 */       int m = 4;
/* 2084 */       while (m-- != 0) {
/* 2085 */         double d1 = paramArrayOfdouble[j++];
/* 2086 */         d1 = Math.abs(d1);
/* 2087 */         if (d1 > d) {
/* 2088 */           d = d1;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 2093 */       if (d == 0.0D) {
/* 2094 */         return false;
/*      */       }
/* 2096 */       arrayOfDouble[k++] = 1.0D / d;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2104 */     byte b = 0;
/*      */ 
/*      */     
/* 2107 */     for (i = 0; i < 4; i++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2113 */       for (j = 0; j < i; j++) {
/* 2114 */         int n = b + 4 * j + i;
/* 2115 */         double d1 = paramArrayOfdouble[n];
/* 2116 */         int m = j;
/* 2117 */         int i1 = b + 4 * j;
/* 2118 */         int i2 = b + i;
/* 2119 */         while (m-- != 0) {
/* 2120 */           d1 -= paramArrayOfdouble[i1] * paramArrayOfdouble[i2];
/* 2121 */           i1++;
/* 2122 */           i2 += 4;
/*      */         } 
/* 2124 */         paramArrayOfdouble[n] = d1;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2129 */       double d = 0.0D;
/* 2130 */       k = -1;
/* 2131 */       for (j = i; j < 4; j++) {
/* 2132 */         int n = b + 4 * j + i;
/* 2133 */         double d1 = paramArrayOfdouble[n];
/* 2134 */         int m = i;
/* 2135 */         int i1 = b + 4 * j;
/* 2136 */         int i2 = b + i;
/* 2137 */         while (m-- != 0) {
/* 2138 */           d1 -= paramArrayOfdouble[i1] * paramArrayOfdouble[i2];
/* 2139 */           i1++;
/* 2140 */           i2 += 4;
/*      */         } 
/* 2142 */         paramArrayOfdouble[n] = d1;
/*      */         
/*      */         double d2;
/* 2145 */         if ((d2 = arrayOfDouble[j] * Math.abs(d1)) >= d) {
/* 2146 */           d = d2;
/* 2147 */           k = j;
/*      */         } 
/*      */       } 
/*      */       
/* 2151 */       if (k < 0) {
/* 2152 */         throw new RuntimeException(VecMathI18N.getString("Matrix4d11"));
/*      */       }
/*      */ 
/*      */       
/* 2156 */       if (i != k) {
/*      */         
/* 2158 */         int m = 4;
/* 2159 */         int n = b + 4 * k;
/* 2160 */         int i1 = b + 4 * i;
/* 2161 */         while (m-- != 0) {
/* 2162 */           double d1 = paramArrayOfdouble[n];
/* 2163 */           paramArrayOfdouble[n++] = paramArrayOfdouble[i1];
/* 2164 */           paramArrayOfdouble[i1++] = d1;
/*      */         } 
/*      */ 
/*      */         
/* 2168 */         arrayOfDouble[k] = arrayOfDouble[i];
/*      */       } 
/*      */ 
/*      */       
/* 2172 */       paramArrayOfint[i] = k;
/*      */ 
/*      */       
/* 2175 */       if (paramArrayOfdouble[b + 4 * i + i] == 0.0D) {
/* 2176 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 2180 */       if (i != 3) {
/* 2181 */         double d1 = 1.0D / paramArrayOfdouble[b + 4 * i + i];
/* 2182 */         int m = b + 4 * (i + 1) + i;
/* 2183 */         j = 3 - i;
/* 2184 */         while (j-- != 0) {
/* 2185 */           paramArrayOfdouble[m] = paramArrayOfdouble[m] * d1;
/* 2186 */           m += 4;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2192 */     return true;
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
/* 2222 */     byte b2 = 0;
/*      */ 
/*      */     
/* 2225 */     for (byte b1 = 0; b1 < 4; b1++) {
/*      */       
/* 2227 */       byte b4 = b1;
/* 2228 */       byte b = -1;
/*      */ 
/*      */       
/* 2231 */       for (byte b3 = 0; b3 < 4; b3++) {
/*      */ 
/*      */         
/* 2234 */         int i = paramArrayOfint[b2 + b3];
/* 2235 */         double d = paramArrayOfdouble2[b4 + 4 * i];
/* 2236 */         paramArrayOfdouble2[b4 + 4 * i] = paramArrayOfdouble2[b4 + 4 * b3];
/* 2237 */         if (b >= 0) {
/*      */           
/* 2239 */           int j = b3 * 4;
/* 2240 */           for (byte b6 = b; b6 <= b3 - 1; b6++) {
/* 2241 */             d -= paramArrayOfdouble1[j + b6] * paramArrayOfdouble2[b4 + 4 * b6];
/*      */           }
/*      */         }
/* 2244 */         else if (d != 0.0D) {
/* 2245 */           b = b3;
/*      */         } 
/* 2247 */         paramArrayOfdouble2[b4 + 4 * b3] = d;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2252 */       byte b5 = 12;
/* 2253 */       paramArrayOfdouble2[b4 + 12] = paramArrayOfdouble2[b4 + 12] / paramArrayOfdouble1[b5 + 3];
/*      */       
/* 2255 */       b5 -= 4;
/* 2256 */       paramArrayOfdouble2[b4 + 8] = (paramArrayOfdouble2[b4 + 8] - paramArrayOfdouble1[b5 + 3] * paramArrayOfdouble2[b4 + 12]) / paramArrayOfdouble1[b5 + 2];
/*      */ 
/*      */       
/* 2259 */       b5 -= 4;
/* 2260 */       paramArrayOfdouble2[b4 + 4] = (paramArrayOfdouble2[b4 + 4] - paramArrayOfdouble1[b5 + 2] * paramArrayOfdouble2[b4 + 8] - paramArrayOfdouble1[b5 + 3] * paramArrayOfdouble2[b4 + 12]) / paramArrayOfdouble1[b5 + 1];
/*      */ 
/*      */ 
/*      */       
/* 2264 */       b5 -= 4;
/* 2265 */       paramArrayOfdouble2[b4 + 0] = (paramArrayOfdouble2[b4 + 0] - paramArrayOfdouble1[b5 + 1] * paramArrayOfdouble2[b4 + 4] - paramArrayOfdouble1[b5 + 2] * paramArrayOfdouble2[b4 + 8] - paramArrayOfdouble1[b5 + 3] * paramArrayOfdouble2[b4 + 12]) / paramArrayOfdouble1[b5 + 0];
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
/*      */   public final double determinant() {
/* 2282 */     double d = this.m00 * (this.m11 * this.m22 * this.m33 + this.m12 * this.m23 * this.m31 + this.m13 * this.m21 * this.m32 - this.m13 * this.m22 * this.m31 - this.m11 * this.m23 * this.m32 - this.m12 * this.m21 * this.m33);
/*      */     
/* 2284 */     d -= this.m01 * (this.m10 * this.m22 * this.m33 + this.m12 * this.m23 * this.m30 + this.m13 * this.m20 * this.m32 - this.m13 * this.m22 * this.m30 - this.m10 * this.m23 * this.m32 - this.m12 * this.m20 * this.m33);
/*      */     
/* 2286 */     d += this.m02 * (this.m10 * this.m21 * this.m33 + this.m11 * this.m23 * this.m30 + this.m13 * this.m20 * this.m31 - this.m13 * this.m21 * this.m30 - this.m10 * this.m23 * this.m31 - this.m11 * this.m20 * this.m33);
/*      */     
/* 2288 */     d -= this.m03 * (this.m10 * this.m21 * this.m32 + this.m11 * this.m22 * this.m30 + this.m12 * this.m20 * this.m31 - this.m12 * this.m21 * this.m30 - this.m10 * this.m22 * this.m31 - this.m11 * this.m20 * this.m32);
/*      */ 
/*      */     
/* 2291 */     return d;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(double paramDouble) {
/* 2301 */     this.m00 = paramDouble;
/* 2302 */     this.m01 = 0.0D;
/* 2303 */     this.m02 = 0.0D;
/* 2304 */     this.m03 = 0.0D;
/*      */     
/* 2306 */     this.m10 = 0.0D;
/* 2307 */     this.m11 = paramDouble;
/* 2308 */     this.m12 = 0.0D;
/* 2309 */     this.m13 = 0.0D;
/*      */     
/* 2311 */     this.m20 = 0.0D;
/* 2312 */     this.m21 = 0.0D;
/* 2313 */     this.m22 = paramDouble;
/* 2314 */     this.m23 = 0.0D;
/*      */     
/* 2316 */     this.m30 = 0.0D;
/* 2317 */     this.m31 = 0.0D;
/* 2318 */     this.m32 = 0.0D;
/* 2319 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Vector3d paramVector3d) {
/* 2329 */     this.m00 = 1.0D;
/* 2330 */     this.m01 = 0.0D;
/* 2331 */     this.m02 = 0.0D;
/* 2332 */     this.m03 = paramVector3d.x;
/*      */     
/* 2334 */     this.m10 = 0.0D;
/* 2335 */     this.m11 = 1.0D;
/* 2336 */     this.m12 = 0.0D;
/* 2337 */     this.m13 = paramVector3d.y;
/*      */     
/* 2339 */     this.m20 = 0.0D;
/* 2340 */     this.m21 = 0.0D;
/* 2341 */     this.m22 = 1.0D;
/* 2342 */     this.m23 = paramVector3d.z;
/*      */     
/* 2344 */     this.m30 = 0.0D;
/* 2345 */     this.m31 = 0.0D;
/* 2346 */     this.m32 = 0.0D;
/* 2347 */     this.m33 = 1.0D;
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
/*      */   public final void set(double paramDouble, Vector3d paramVector3d) {
/* 2359 */     this.m00 = paramDouble;
/* 2360 */     this.m01 = 0.0D;
/* 2361 */     this.m02 = 0.0D;
/* 2362 */     this.m03 = paramVector3d.x;
/*      */     
/* 2364 */     this.m10 = 0.0D;
/* 2365 */     this.m11 = paramDouble;
/* 2366 */     this.m12 = 0.0D;
/* 2367 */     this.m13 = paramVector3d.y;
/*      */     
/* 2369 */     this.m20 = 0.0D;
/* 2370 */     this.m21 = 0.0D;
/* 2371 */     this.m22 = paramDouble;
/* 2372 */     this.m23 = paramVector3d.z;
/*      */     
/* 2374 */     this.m30 = 0.0D;
/* 2375 */     this.m31 = 0.0D;
/* 2376 */     this.m32 = 0.0D;
/* 2377 */     this.m33 = 1.0D;
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
/*      */   public final void set(Vector3d paramVector3d, double paramDouble) {
/* 2389 */     this.m00 = paramDouble;
/* 2390 */     this.m01 = 0.0D;
/* 2391 */     this.m02 = 0.0D;
/* 2392 */     this.m03 = paramDouble * paramVector3d.x;
/*      */     
/* 2394 */     this.m10 = 0.0D;
/* 2395 */     this.m11 = paramDouble;
/* 2396 */     this.m12 = 0.0D;
/* 2397 */     this.m13 = paramDouble * paramVector3d.y;
/*      */     
/* 2399 */     this.m20 = 0.0D;
/* 2400 */     this.m21 = 0.0D;
/* 2401 */     this.m22 = paramDouble;
/* 2402 */     this.m23 = paramDouble * paramVector3d.z;
/*      */     
/* 2404 */     this.m30 = 0.0D;
/* 2405 */     this.m31 = 0.0D;
/* 2406 */     this.m32 = 0.0D;
/* 2407 */     this.m33 = 1.0D;
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
/* 2420 */     this.m00 = (paramMatrix3f.m00 * paramFloat);
/* 2421 */     this.m01 = (paramMatrix3f.m01 * paramFloat);
/* 2422 */     this.m02 = (paramMatrix3f.m02 * paramFloat);
/* 2423 */     this.m03 = paramVector3f.x;
/*      */     
/* 2425 */     this.m10 = (paramMatrix3f.m10 * paramFloat);
/* 2426 */     this.m11 = (paramMatrix3f.m11 * paramFloat);
/* 2427 */     this.m12 = (paramMatrix3f.m12 * paramFloat);
/* 2428 */     this.m13 = paramVector3f.y;
/*      */     
/* 2430 */     this.m20 = (paramMatrix3f.m20 * paramFloat);
/* 2431 */     this.m21 = (paramMatrix3f.m21 * paramFloat);
/* 2432 */     this.m22 = (paramMatrix3f.m22 * paramFloat);
/* 2433 */     this.m23 = paramVector3f.z;
/*      */     
/* 2435 */     this.m30 = 0.0D;
/* 2436 */     this.m31 = 0.0D;
/* 2437 */     this.m32 = 0.0D;
/* 2438 */     this.m33 = 1.0D;
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
/*      */   public final void set(Matrix3d paramMatrix3d, Vector3d paramVector3d, double paramDouble) {
/* 2452 */     this.m00 = paramMatrix3d.m00 * paramDouble;
/* 2453 */     this.m01 = paramMatrix3d.m01 * paramDouble;
/* 2454 */     this.m02 = paramMatrix3d.m02 * paramDouble;
/* 2455 */     this.m03 = paramVector3d.x;
/*      */     
/* 2457 */     this.m10 = paramMatrix3d.m10 * paramDouble;
/* 2458 */     this.m11 = paramMatrix3d.m11 * paramDouble;
/* 2459 */     this.m12 = paramMatrix3d.m12 * paramDouble;
/* 2460 */     this.m13 = paramVector3d.y;
/*      */     
/* 2462 */     this.m20 = paramMatrix3d.m20 * paramDouble;
/* 2463 */     this.m21 = paramMatrix3d.m21 * paramDouble;
/* 2464 */     this.m22 = paramMatrix3d.m22 * paramDouble;
/* 2465 */     this.m23 = paramVector3d.z;
/*      */     
/* 2467 */     this.m30 = 0.0D;
/* 2468 */     this.m31 = 0.0D;
/* 2469 */     this.m32 = 0.0D;
/* 2470 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setTranslation(Vector3d paramVector3d) {
/* 2481 */     this.m03 = paramVector3d.x;
/* 2482 */     this.m13 = paramVector3d.y;
/* 2483 */     this.m23 = paramVector3d.z;
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
/* 2495 */     double d1 = Math.sin(paramDouble);
/* 2496 */     double d2 = Math.cos(paramDouble);
/*      */     
/* 2498 */     this.m00 = 1.0D;
/* 2499 */     this.m01 = 0.0D;
/* 2500 */     this.m02 = 0.0D;
/* 2501 */     this.m03 = 0.0D;
/*      */     
/* 2503 */     this.m10 = 0.0D;
/* 2504 */     this.m11 = d2;
/* 2505 */     this.m12 = -d1;
/* 2506 */     this.m13 = 0.0D;
/*      */     
/* 2508 */     this.m20 = 0.0D;
/* 2509 */     this.m21 = d1;
/* 2510 */     this.m22 = d2;
/* 2511 */     this.m23 = 0.0D;
/*      */     
/* 2513 */     this.m30 = 0.0D;
/* 2514 */     this.m31 = 0.0D;
/* 2515 */     this.m32 = 0.0D;
/* 2516 */     this.m33 = 1.0D;
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
/* 2528 */     double d1 = Math.sin(paramDouble);
/* 2529 */     double d2 = Math.cos(paramDouble);
/*      */     
/* 2531 */     this.m00 = d2;
/* 2532 */     this.m01 = 0.0D;
/* 2533 */     this.m02 = d1;
/* 2534 */     this.m03 = 0.0D;
/*      */     
/* 2536 */     this.m10 = 0.0D;
/* 2537 */     this.m11 = 1.0D;
/* 2538 */     this.m12 = 0.0D;
/* 2539 */     this.m13 = 0.0D;
/*      */     
/* 2541 */     this.m20 = -d1;
/* 2542 */     this.m21 = 0.0D;
/* 2543 */     this.m22 = d2;
/* 2544 */     this.m23 = 0.0D;
/*      */     
/* 2546 */     this.m30 = 0.0D;
/* 2547 */     this.m31 = 0.0D;
/* 2548 */     this.m32 = 0.0D;
/* 2549 */     this.m33 = 1.0D;
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
/* 2561 */     double d1 = Math.sin(paramDouble);
/* 2562 */     double d2 = Math.cos(paramDouble);
/*      */     
/* 2564 */     this.m00 = d2;
/* 2565 */     this.m01 = -d1;
/* 2566 */     this.m02 = 0.0D;
/* 2567 */     this.m03 = 0.0D;
/*      */     
/* 2569 */     this.m10 = d1;
/* 2570 */     this.m11 = d2;
/* 2571 */     this.m12 = 0.0D;
/* 2572 */     this.m13 = 0.0D;
/*      */     
/* 2574 */     this.m20 = 0.0D;
/* 2575 */     this.m21 = 0.0D;
/* 2576 */     this.m22 = 1.0D;
/* 2577 */     this.m23 = 0.0D;
/*      */     
/* 2579 */     this.m30 = 0.0D;
/* 2580 */     this.m31 = 0.0D;
/* 2581 */     this.m32 = 0.0D;
/* 2582 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(double paramDouble) {
/* 2591 */     this.m00 *= paramDouble;
/* 2592 */     this.m01 *= paramDouble;
/* 2593 */     this.m02 *= paramDouble;
/* 2594 */     this.m03 *= paramDouble;
/* 2595 */     this.m10 *= paramDouble;
/* 2596 */     this.m11 *= paramDouble;
/* 2597 */     this.m12 *= paramDouble;
/* 2598 */     this.m13 *= paramDouble;
/* 2599 */     this.m20 *= paramDouble;
/* 2600 */     this.m21 *= paramDouble;
/* 2601 */     this.m22 *= paramDouble;
/* 2602 */     this.m23 *= paramDouble;
/* 2603 */     this.m30 *= paramDouble;
/* 2604 */     this.m31 *= paramDouble;
/* 2605 */     this.m32 *= paramDouble;
/* 2606 */     this.m33 *= paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(double paramDouble, Matrix4d paramMatrix4d) {
/* 2617 */     paramMatrix4d.m00 *= paramDouble;
/* 2618 */     paramMatrix4d.m01 *= paramDouble;
/* 2619 */     paramMatrix4d.m02 *= paramDouble;
/* 2620 */     paramMatrix4d.m03 *= paramDouble;
/* 2621 */     paramMatrix4d.m10 *= paramDouble;
/* 2622 */     paramMatrix4d.m11 *= paramDouble;
/* 2623 */     paramMatrix4d.m12 *= paramDouble;
/* 2624 */     paramMatrix4d.m13 *= paramDouble;
/* 2625 */     paramMatrix4d.m20 *= paramDouble;
/* 2626 */     paramMatrix4d.m21 *= paramDouble;
/* 2627 */     paramMatrix4d.m22 *= paramDouble;
/* 2628 */     paramMatrix4d.m23 *= paramDouble;
/* 2629 */     paramMatrix4d.m30 *= paramDouble;
/* 2630 */     paramMatrix4d.m31 *= paramDouble;
/* 2631 */     paramMatrix4d.m32 *= paramDouble;
/* 2632 */     paramMatrix4d.m33 *= paramDouble;
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
/*      */   public final void mul(Matrix4d paramMatrix4d) {
/* 2647 */     double d1 = this.m00 * paramMatrix4d.m00 + this.m01 * paramMatrix4d.m10 + this.m02 * paramMatrix4d.m20 + this.m03 * paramMatrix4d.m30;
/*      */     
/* 2649 */     double d2 = this.m00 * paramMatrix4d.m01 + this.m01 * paramMatrix4d.m11 + this.m02 * paramMatrix4d.m21 + this.m03 * paramMatrix4d.m31;
/*      */     
/* 2651 */     double d3 = this.m00 * paramMatrix4d.m02 + this.m01 * paramMatrix4d.m12 + this.m02 * paramMatrix4d.m22 + this.m03 * paramMatrix4d.m32;
/*      */     
/* 2653 */     double d4 = this.m00 * paramMatrix4d.m03 + this.m01 * paramMatrix4d.m13 + this.m02 * paramMatrix4d.m23 + this.m03 * paramMatrix4d.m33;
/*      */ 
/*      */     
/* 2656 */     double d5 = this.m10 * paramMatrix4d.m00 + this.m11 * paramMatrix4d.m10 + this.m12 * paramMatrix4d.m20 + this.m13 * paramMatrix4d.m30;
/*      */     
/* 2658 */     double d6 = this.m10 * paramMatrix4d.m01 + this.m11 * paramMatrix4d.m11 + this.m12 * paramMatrix4d.m21 + this.m13 * paramMatrix4d.m31;
/*      */     
/* 2660 */     double d7 = this.m10 * paramMatrix4d.m02 + this.m11 * paramMatrix4d.m12 + this.m12 * paramMatrix4d.m22 + this.m13 * paramMatrix4d.m32;
/*      */     
/* 2662 */     double d8 = this.m10 * paramMatrix4d.m03 + this.m11 * paramMatrix4d.m13 + this.m12 * paramMatrix4d.m23 + this.m13 * paramMatrix4d.m33;
/*      */ 
/*      */     
/* 2665 */     double d9 = this.m20 * paramMatrix4d.m00 + this.m21 * paramMatrix4d.m10 + this.m22 * paramMatrix4d.m20 + this.m23 * paramMatrix4d.m30;
/*      */     
/* 2667 */     double d10 = this.m20 * paramMatrix4d.m01 + this.m21 * paramMatrix4d.m11 + this.m22 * paramMatrix4d.m21 + this.m23 * paramMatrix4d.m31;
/*      */     
/* 2669 */     double d11 = this.m20 * paramMatrix4d.m02 + this.m21 * paramMatrix4d.m12 + this.m22 * paramMatrix4d.m22 + this.m23 * paramMatrix4d.m32;
/*      */     
/* 2671 */     double d12 = this.m20 * paramMatrix4d.m03 + this.m21 * paramMatrix4d.m13 + this.m22 * paramMatrix4d.m23 + this.m23 * paramMatrix4d.m33;
/*      */ 
/*      */     
/* 2674 */     double d13 = this.m30 * paramMatrix4d.m00 + this.m31 * paramMatrix4d.m10 + this.m32 * paramMatrix4d.m20 + this.m33 * paramMatrix4d.m30;
/*      */     
/* 2676 */     double d14 = this.m30 * paramMatrix4d.m01 + this.m31 * paramMatrix4d.m11 + this.m32 * paramMatrix4d.m21 + this.m33 * paramMatrix4d.m31;
/*      */     
/* 2678 */     double d15 = this.m30 * paramMatrix4d.m02 + this.m31 * paramMatrix4d.m12 + this.m32 * paramMatrix4d.m22 + this.m33 * paramMatrix4d.m32;
/*      */     
/* 2680 */     double d16 = this.m30 * paramMatrix4d.m03 + this.m31 * paramMatrix4d.m13 + this.m32 * paramMatrix4d.m23 + this.m33 * paramMatrix4d.m33;
/*      */ 
/*      */     
/* 2683 */     this.m00 = d1; this.m01 = d2; this.m02 = d3; this.m03 = d4;
/* 2684 */     this.m10 = d5; this.m11 = d6; this.m12 = d7; this.m13 = d8;
/* 2685 */     this.m20 = d9; this.m21 = d10; this.m22 = d11; this.m23 = d12;
/* 2686 */     this.m30 = d13; this.m31 = d14; this.m32 = d15; this.m33 = d16;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(Matrix4d paramMatrix4d1, Matrix4d paramMatrix4d2) {
/* 2697 */     if (this != paramMatrix4d1 && this != paramMatrix4d2) {
/*      */       
/* 2699 */       this.m00 = paramMatrix4d1.m00 * paramMatrix4d2.m00 + paramMatrix4d1.m01 * paramMatrix4d2.m10 + paramMatrix4d1.m02 * paramMatrix4d2.m20 + paramMatrix4d1.m03 * paramMatrix4d2.m30;
/*      */       
/* 2701 */       this.m01 = paramMatrix4d1.m00 * paramMatrix4d2.m01 + paramMatrix4d1.m01 * paramMatrix4d2.m11 + paramMatrix4d1.m02 * paramMatrix4d2.m21 + paramMatrix4d1.m03 * paramMatrix4d2.m31;
/*      */       
/* 2703 */       this.m02 = paramMatrix4d1.m00 * paramMatrix4d2.m02 + paramMatrix4d1.m01 * paramMatrix4d2.m12 + paramMatrix4d1.m02 * paramMatrix4d2.m22 + paramMatrix4d1.m03 * paramMatrix4d2.m32;
/*      */       
/* 2705 */       this.m03 = paramMatrix4d1.m00 * paramMatrix4d2.m03 + paramMatrix4d1.m01 * paramMatrix4d2.m13 + paramMatrix4d1.m02 * paramMatrix4d2.m23 + paramMatrix4d1.m03 * paramMatrix4d2.m33;
/*      */ 
/*      */       
/* 2708 */       this.m10 = paramMatrix4d1.m10 * paramMatrix4d2.m00 + paramMatrix4d1.m11 * paramMatrix4d2.m10 + paramMatrix4d1.m12 * paramMatrix4d2.m20 + paramMatrix4d1.m13 * paramMatrix4d2.m30;
/*      */       
/* 2710 */       this.m11 = paramMatrix4d1.m10 * paramMatrix4d2.m01 + paramMatrix4d1.m11 * paramMatrix4d2.m11 + paramMatrix4d1.m12 * paramMatrix4d2.m21 + paramMatrix4d1.m13 * paramMatrix4d2.m31;
/*      */       
/* 2712 */       this.m12 = paramMatrix4d1.m10 * paramMatrix4d2.m02 + paramMatrix4d1.m11 * paramMatrix4d2.m12 + paramMatrix4d1.m12 * paramMatrix4d2.m22 + paramMatrix4d1.m13 * paramMatrix4d2.m32;
/*      */       
/* 2714 */       this.m13 = paramMatrix4d1.m10 * paramMatrix4d2.m03 + paramMatrix4d1.m11 * paramMatrix4d2.m13 + paramMatrix4d1.m12 * paramMatrix4d2.m23 + paramMatrix4d1.m13 * paramMatrix4d2.m33;
/*      */ 
/*      */       
/* 2717 */       this.m20 = paramMatrix4d1.m20 * paramMatrix4d2.m00 + paramMatrix4d1.m21 * paramMatrix4d2.m10 + paramMatrix4d1.m22 * paramMatrix4d2.m20 + paramMatrix4d1.m23 * paramMatrix4d2.m30;
/*      */       
/* 2719 */       this.m21 = paramMatrix4d1.m20 * paramMatrix4d2.m01 + paramMatrix4d1.m21 * paramMatrix4d2.m11 + paramMatrix4d1.m22 * paramMatrix4d2.m21 + paramMatrix4d1.m23 * paramMatrix4d2.m31;
/*      */       
/* 2721 */       this.m22 = paramMatrix4d1.m20 * paramMatrix4d2.m02 + paramMatrix4d1.m21 * paramMatrix4d2.m12 + paramMatrix4d1.m22 * paramMatrix4d2.m22 + paramMatrix4d1.m23 * paramMatrix4d2.m32;
/*      */       
/* 2723 */       this.m23 = paramMatrix4d1.m20 * paramMatrix4d2.m03 + paramMatrix4d1.m21 * paramMatrix4d2.m13 + paramMatrix4d1.m22 * paramMatrix4d2.m23 + paramMatrix4d1.m23 * paramMatrix4d2.m33;
/*      */ 
/*      */       
/* 2726 */       this.m30 = paramMatrix4d1.m30 * paramMatrix4d2.m00 + paramMatrix4d1.m31 * paramMatrix4d2.m10 + paramMatrix4d1.m32 * paramMatrix4d2.m20 + paramMatrix4d1.m33 * paramMatrix4d2.m30;
/*      */       
/* 2728 */       this.m31 = paramMatrix4d1.m30 * paramMatrix4d2.m01 + paramMatrix4d1.m31 * paramMatrix4d2.m11 + paramMatrix4d1.m32 * paramMatrix4d2.m21 + paramMatrix4d1.m33 * paramMatrix4d2.m31;
/*      */       
/* 2730 */       this.m32 = paramMatrix4d1.m30 * paramMatrix4d2.m02 + paramMatrix4d1.m31 * paramMatrix4d2.m12 + paramMatrix4d1.m32 * paramMatrix4d2.m22 + paramMatrix4d1.m33 * paramMatrix4d2.m32;
/*      */       
/* 2732 */       this.m33 = paramMatrix4d1.m30 * paramMatrix4d2.m03 + paramMatrix4d1.m31 * paramMatrix4d2.m13 + paramMatrix4d1.m32 * paramMatrix4d2.m23 + paramMatrix4d1.m33 * paramMatrix4d2.m33;
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 2741 */       double d1 = paramMatrix4d1.m00 * paramMatrix4d2.m00 + paramMatrix4d1.m01 * paramMatrix4d2.m10 + paramMatrix4d1.m02 * paramMatrix4d2.m20 + paramMatrix4d1.m03 * paramMatrix4d2.m30;
/* 2742 */       double d2 = paramMatrix4d1.m00 * paramMatrix4d2.m01 + paramMatrix4d1.m01 * paramMatrix4d2.m11 + paramMatrix4d1.m02 * paramMatrix4d2.m21 + paramMatrix4d1.m03 * paramMatrix4d2.m31;
/* 2743 */       double d3 = paramMatrix4d1.m00 * paramMatrix4d2.m02 + paramMatrix4d1.m01 * paramMatrix4d2.m12 + paramMatrix4d1.m02 * paramMatrix4d2.m22 + paramMatrix4d1.m03 * paramMatrix4d2.m32;
/* 2744 */       double d4 = paramMatrix4d1.m00 * paramMatrix4d2.m03 + paramMatrix4d1.m01 * paramMatrix4d2.m13 + paramMatrix4d1.m02 * paramMatrix4d2.m23 + paramMatrix4d1.m03 * paramMatrix4d2.m33;
/*      */       
/* 2746 */       double d5 = paramMatrix4d1.m10 * paramMatrix4d2.m00 + paramMatrix4d1.m11 * paramMatrix4d2.m10 + paramMatrix4d1.m12 * paramMatrix4d2.m20 + paramMatrix4d1.m13 * paramMatrix4d2.m30;
/* 2747 */       double d6 = paramMatrix4d1.m10 * paramMatrix4d2.m01 + paramMatrix4d1.m11 * paramMatrix4d2.m11 + paramMatrix4d1.m12 * paramMatrix4d2.m21 + paramMatrix4d1.m13 * paramMatrix4d2.m31;
/* 2748 */       double d7 = paramMatrix4d1.m10 * paramMatrix4d2.m02 + paramMatrix4d1.m11 * paramMatrix4d2.m12 + paramMatrix4d1.m12 * paramMatrix4d2.m22 + paramMatrix4d1.m13 * paramMatrix4d2.m32;
/* 2749 */       double d8 = paramMatrix4d1.m10 * paramMatrix4d2.m03 + paramMatrix4d1.m11 * paramMatrix4d2.m13 + paramMatrix4d1.m12 * paramMatrix4d2.m23 + paramMatrix4d1.m13 * paramMatrix4d2.m33;
/*      */       
/* 2751 */       double d9 = paramMatrix4d1.m20 * paramMatrix4d2.m00 + paramMatrix4d1.m21 * paramMatrix4d2.m10 + paramMatrix4d1.m22 * paramMatrix4d2.m20 + paramMatrix4d1.m23 * paramMatrix4d2.m30;
/* 2752 */       double d10 = paramMatrix4d1.m20 * paramMatrix4d2.m01 + paramMatrix4d1.m21 * paramMatrix4d2.m11 + paramMatrix4d1.m22 * paramMatrix4d2.m21 + paramMatrix4d1.m23 * paramMatrix4d2.m31;
/* 2753 */       double d11 = paramMatrix4d1.m20 * paramMatrix4d2.m02 + paramMatrix4d1.m21 * paramMatrix4d2.m12 + paramMatrix4d1.m22 * paramMatrix4d2.m22 + paramMatrix4d1.m23 * paramMatrix4d2.m32;
/* 2754 */       double d12 = paramMatrix4d1.m20 * paramMatrix4d2.m03 + paramMatrix4d1.m21 * paramMatrix4d2.m13 + paramMatrix4d1.m22 * paramMatrix4d2.m23 + paramMatrix4d1.m23 * paramMatrix4d2.m33;
/*      */       
/* 2756 */       double d13 = paramMatrix4d1.m30 * paramMatrix4d2.m00 + paramMatrix4d1.m31 * paramMatrix4d2.m10 + paramMatrix4d1.m32 * paramMatrix4d2.m20 + paramMatrix4d1.m33 * paramMatrix4d2.m30;
/* 2757 */       double d14 = paramMatrix4d1.m30 * paramMatrix4d2.m01 + paramMatrix4d1.m31 * paramMatrix4d2.m11 + paramMatrix4d1.m32 * paramMatrix4d2.m21 + paramMatrix4d1.m33 * paramMatrix4d2.m31;
/* 2758 */       double d15 = paramMatrix4d1.m30 * paramMatrix4d2.m02 + paramMatrix4d1.m31 * paramMatrix4d2.m12 + paramMatrix4d1.m32 * paramMatrix4d2.m22 + paramMatrix4d1.m33 * paramMatrix4d2.m32;
/* 2759 */       double d16 = paramMatrix4d1.m30 * paramMatrix4d2.m03 + paramMatrix4d1.m31 * paramMatrix4d2.m13 + paramMatrix4d1.m32 * paramMatrix4d2.m23 + paramMatrix4d1.m33 * paramMatrix4d2.m33;
/*      */       
/* 2761 */       this.m00 = d1; this.m01 = d2; this.m02 = d3; this.m03 = d4;
/* 2762 */       this.m10 = d5; this.m11 = d6; this.m12 = d7; this.m13 = d8;
/* 2763 */       this.m20 = d9; this.m21 = d10; this.m22 = d11; this.m23 = d12;
/* 2764 */       this.m30 = d13; this.m31 = d14; this.m32 = d15; this.m33 = d16;
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
/*      */   public final void mulTransposeBoth(Matrix4d paramMatrix4d1, Matrix4d paramMatrix4d2) {
/* 2777 */     if (this != paramMatrix4d1 && this != paramMatrix4d2) {
/* 2778 */       this.m00 = paramMatrix4d1.m00 * paramMatrix4d2.m00 + paramMatrix4d1.m10 * paramMatrix4d2.m01 + paramMatrix4d1.m20 * paramMatrix4d2.m02 + paramMatrix4d1.m30 * paramMatrix4d2.m03;
/* 2779 */       this.m01 = paramMatrix4d1.m00 * paramMatrix4d2.m10 + paramMatrix4d1.m10 * paramMatrix4d2.m11 + paramMatrix4d1.m20 * paramMatrix4d2.m12 + paramMatrix4d1.m30 * paramMatrix4d2.m13;
/* 2780 */       this.m02 = paramMatrix4d1.m00 * paramMatrix4d2.m20 + paramMatrix4d1.m10 * paramMatrix4d2.m21 + paramMatrix4d1.m20 * paramMatrix4d2.m22 + paramMatrix4d1.m30 * paramMatrix4d2.m23;
/* 2781 */       this.m03 = paramMatrix4d1.m00 * paramMatrix4d2.m30 + paramMatrix4d1.m10 * paramMatrix4d2.m31 + paramMatrix4d1.m20 * paramMatrix4d2.m32 + paramMatrix4d1.m30 * paramMatrix4d2.m33;
/*      */       
/* 2783 */       this.m10 = paramMatrix4d1.m01 * paramMatrix4d2.m00 + paramMatrix4d1.m11 * paramMatrix4d2.m01 + paramMatrix4d1.m21 * paramMatrix4d2.m02 + paramMatrix4d1.m31 * paramMatrix4d2.m03;
/* 2784 */       this.m11 = paramMatrix4d1.m01 * paramMatrix4d2.m10 + paramMatrix4d1.m11 * paramMatrix4d2.m11 + paramMatrix4d1.m21 * paramMatrix4d2.m12 + paramMatrix4d1.m31 * paramMatrix4d2.m13;
/* 2785 */       this.m12 = paramMatrix4d1.m01 * paramMatrix4d2.m20 + paramMatrix4d1.m11 * paramMatrix4d2.m21 + paramMatrix4d1.m21 * paramMatrix4d2.m22 + paramMatrix4d1.m31 * paramMatrix4d2.m23;
/* 2786 */       this.m13 = paramMatrix4d1.m01 * paramMatrix4d2.m30 + paramMatrix4d1.m11 * paramMatrix4d2.m31 + paramMatrix4d1.m21 * paramMatrix4d2.m32 + paramMatrix4d1.m31 * paramMatrix4d2.m33;
/*      */       
/* 2788 */       this.m20 = paramMatrix4d1.m02 * paramMatrix4d2.m00 + paramMatrix4d1.m12 * paramMatrix4d2.m01 + paramMatrix4d1.m22 * paramMatrix4d2.m02 + paramMatrix4d1.m32 * paramMatrix4d2.m03;
/* 2789 */       this.m21 = paramMatrix4d1.m02 * paramMatrix4d2.m10 + paramMatrix4d1.m12 * paramMatrix4d2.m11 + paramMatrix4d1.m22 * paramMatrix4d2.m12 + paramMatrix4d1.m32 * paramMatrix4d2.m13;
/* 2790 */       this.m22 = paramMatrix4d1.m02 * paramMatrix4d2.m20 + paramMatrix4d1.m12 * paramMatrix4d2.m21 + paramMatrix4d1.m22 * paramMatrix4d2.m22 + paramMatrix4d1.m32 * paramMatrix4d2.m23;
/* 2791 */       this.m23 = paramMatrix4d1.m02 * paramMatrix4d2.m30 + paramMatrix4d1.m12 * paramMatrix4d2.m31 + paramMatrix4d1.m22 * paramMatrix4d2.m32 + paramMatrix4d1.m32 * paramMatrix4d2.m33;
/*      */       
/* 2793 */       this.m30 = paramMatrix4d1.m03 * paramMatrix4d2.m00 + paramMatrix4d1.m13 * paramMatrix4d2.m01 + paramMatrix4d1.m23 * paramMatrix4d2.m02 + paramMatrix4d1.m33 * paramMatrix4d2.m03;
/* 2794 */       this.m31 = paramMatrix4d1.m03 * paramMatrix4d2.m10 + paramMatrix4d1.m13 * paramMatrix4d2.m11 + paramMatrix4d1.m23 * paramMatrix4d2.m12 + paramMatrix4d1.m33 * paramMatrix4d2.m13;
/* 2795 */       this.m32 = paramMatrix4d1.m03 * paramMatrix4d2.m20 + paramMatrix4d1.m13 * paramMatrix4d2.m21 + paramMatrix4d1.m23 * paramMatrix4d2.m22 + paramMatrix4d1.m33 * paramMatrix4d2.m23;
/* 2796 */       this.m33 = paramMatrix4d1.m03 * paramMatrix4d2.m30 + paramMatrix4d1.m13 * paramMatrix4d2.m31 + paramMatrix4d1.m23 * paramMatrix4d2.m32 + paramMatrix4d1.m33 * paramMatrix4d2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2803 */       double d1 = paramMatrix4d1.m00 * paramMatrix4d2.m00 + paramMatrix4d1.m10 * paramMatrix4d2.m01 + paramMatrix4d1.m20 * paramMatrix4d2.m02 + paramMatrix4d1.m30 * paramMatrix4d2.m03;
/* 2804 */       double d2 = paramMatrix4d1.m00 * paramMatrix4d2.m10 + paramMatrix4d1.m10 * paramMatrix4d2.m11 + paramMatrix4d1.m20 * paramMatrix4d2.m12 + paramMatrix4d1.m30 * paramMatrix4d2.m13;
/* 2805 */       double d3 = paramMatrix4d1.m00 * paramMatrix4d2.m20 + paramMatrix4d1.m10 * paramMatrix4d2.m21 + paramMatrix4d1.m20 * paramMatrix4d2.m22 + paramMatrix4d1.m30 * paramMatrix4d2.m23;
/* 2806 */       double d4 = paramMatrix4d1.m00 * paramMatrix4d2.m30 + paramMatrix4d1.m10 * paramMatrix4d2.m31 + paramMatrix4d1.m20 * paramMatrix4d2.m32 + paramMatrix4d1.m30 * paramMatrix4d2.m33;
/*      */       
/* 2808 */       double d5 = paramMatrix4d1.m01 * paramMatrix4d2.m00 + paramMatrix4d1.m11 * paramMatrix4d2.m01 + paramMatrix4d1.m21 * paramMatrix4d2.m02 + paramMatrix4d1.m31 * paramMatrix4d2.m03;
/* 2809 */       double d6 = paramMatrix4d1.m01 * paramMatrix4d2.m10 + paramMatrix4d1.m11 * paramMatrix4d2.m11 + paramMatrix4d1.m21 * paramMatrix4d2.m12 + paramMatrix4d1.m31 * paramMatrix4d2.m13;
/* 2810 */       double d7 = paramMatrix4d1.m01 * paramMatrix4d2.m20 + paramMatrix4d1.m11 * paramMatrix4d2.m21 + paramMatrix4d1.m21 * paramMatrix4d2.m22 + paramMatrix4d1.m31 * paramMatrix4d2.m23;
/* 2811 */       double d8 = paramMatrix4d1.m01 * paramMatrix4d2.m30 + paramMatrix4d1.m11 * paramMatrix4d2.m31 + paramMatrix4d1.m21 * paramMatrix4d2.m32 + paramMatrix4d1.m31 * paramMatrix4d2.m33;
/*      */       
/* 2813 */       double d9 = paramMatrix4d1.m02 * paramMatrix4d2.m00 + paramMatrix4d1.m12 * paramMatrix4d2.m01 + paramMatrix4d1.m22 * paramMatrix4d2.m02 + paramMatrix4d1.m32 * paramMatrix4d2.m03;
/* 2814 */       double d10 = paramMatrix4d1.m02 * paramMatrix4d2.m10 + paramMatrix4d1.m12 * paramMatrix4d2.m11 + paramMatrix4d1.m22 * paramMatrix4d2.m12 + paramMatrix4d1.m32 * paramMatrix4d2.m13;
/* 2815 */       double d11 = paramMatrix4d1.m02 * paramMatrix4d2.m20 + paramMatrix4d1.m12 * paramMatrix4d2.m21 + paramMatrix4d1.m22 * paramMatrix4d2.m22 + paramMatrix4d1.m32 * paramMatrix4d2.m23;
/* 2816 */       double d12 = paramMatrix4d1.m02 * paramMatrix4d2.m30 + paramMatrix4d1.m12 * paramMatrix4d2.m31 + paramMatrix4d1.m22 * paramMatrix4d2.m32 + paramMatrix4d1.m32 * paramMatrix4d2.m33;
/*      */       
/* 2818 */       double d13 = paramMatrix4d1.m03 * paramMatrix4d2.m00 + paramMatrix4d1.m13 * paramMatrix4d2.m01 + paramMatrix4d1.m23 * paramMatrix4d2.m02 + paramMatrix4d1.m33 * paramMatrix4d2.m03;
/* 2819 */       double d14 = paramMatrix4d1.m03 * paramMatrix4d2.m10 + paramMatrix4d1.m13 * paramMatrix4d2.m11 + paramMatrix4d1.m23 * paramMatrix4d2.m12 + paramMatrix4d1.m33 * paramMatrix4d2.m13;
/* 2820 */       double d15 = paramMatrix4d1.m03 * paramMatrix4d2.m20 + paramMatrix4d1.m13 * paramMatrix4d2.m21 + paramMatrix4d1.m23 * paramMatrix4d2.m22 + paramMatrix4d1.m33 * paramMatrix4d2.m23;
/* 2821 */       double d16 = paramMatrix4d1.m03 * paramMatrix4d2.m30 + paramMatrix4d1.m13 * paramMatrix4d2.m31 + paramMatrix4d1.m23 * paramMatrix4d2.m32 + paramMatrix4d1.m33 * paramMatrix4d2.m33;
/*      */       
/* 2823 */       this.m00 = d1; this.m01 = d2; this.m02 = d3; this.m03 = d4;
/* 2824 */       this.m10 = d5; this.m11 = d6; this.m12 = d7; this.m13 = d8;
/* 2825 */       this.m20 = d9; this.m21 = d10; this.m22 = d11; this.m23 = d12;
/* 2826 */       this.m30 = d13; this.m31 = d14; this.m32 = d15; this.m33 = d16;
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
/*      */   public final void mulTransposeRight(Matrix4d paramMatrix4d1, Matrix4d paramMatrix4d2) {
/* 2841 */     if (this != paramMatrix4d1 && this != paramMatrix4d2) {
/* 2842 */       this.m00 = paramMatrix4d1.m00 * paramMatrix4d2.m00 + paramMatrix4d1.m01 * paramMatrix4d2.m01 + paramMatrix4d1.m02 * paramMatrix4d2.m02 + paramMatrix4d1.m03 * paramMatrix4d2.m03;
/* 2843 */       this.m01 = paramMatrix4d1.m00 * paramMatrix4d2.m10 + paramMatrix4d1.m01 * paramMatrix4d2.m11 + paramMatrix4d1.m02 * paramMatrix4d2.m12 + paramMatrix4d1.m03 * paramMatrix4d2.m13;
/* 2844 */       this.m02 = paramMatrix4d1.m00 * paramMatrix4d2.m20 + paramMatrix4d1.m01 * paramMatrix4d2.m21 + paramMatrix4d1.m02 * paramMatrix4d2.m22 + paramMatrix4d1.m03 * paramMatrix4d2.m23;
/* 2845 */       this.m03 = paramMatrix4d1.m00 * paramMatrix4d2.m30 + paramMatrix4d1.m01 * paramMatrix4d2.m31 + paramMatrix4d1.m02 * paramMatrix4d2.m32 + paramMatrix4d1.m03 * paramMatrix4d2.m33;
/*      */       
/* 2847 */       this.m10 = paramMatrix4d1.m10 * paramMatrix4d2.m00 + paramMatrix4d1.m11 * paramMatrix4d2.m01 + paramMatrix4d1.m12 * paramMatrix4d2.m02 + paramMatrix4d1.m13 * paramMatrix4d2.m03;
/* 2848 */       this.m11 = paramMatrix4d1.m10 * paramMatrix4d2.m10 + paramMatrix4d1.m11 * paramMatrix4d2.m11 + paramMatrix4d1.m12 * paramMatrix4d2.m12 + paramMatrix4d1.m13 * paramMatrix4d2.m13;
/* 2849 */       this.m12 = paramMatrix4d1.m10 * paramMatrix4d2.m20 + paramMatrix4d1.m11 * paramMatrix4d2.m21 + paramMatrix4d1.m12 * paramMatrix4d2.m22 + paramMatrix4d1.m13 * paramMatrix4d2.m23;
/* 2850 */       this.m13 = paramMatrix4d1.m10 * paramMatrix4d2.m30 + paramMatrix4d1.m11 * paramMatrix4d2.m31 + paramMatrix4d1.m12 * paramMatrix4d2.m32 + paramMatrix4d1.m13 * paramMatrix4d2.m33;
/*      */       
/* 2852 */       this.m20 = paramMatrix4d1.m20 * paramMatrix4d2.m00 + paramMatrix4d1.m21 * paramMatrix4d2.m01 + paramMatrix4d1.m22 * paramMatrix4d2.m02 + paramMatrix4d1.m23 * paramMatrix4d2.m03;
/* 2853 */       this.m21 = paramMatrix4d1.m20 * paramMatrix4d2.m10 + paramMatrix4d1.m21 * paramMatrix4d2.m11 + paramMatrix4d1.m22 * paramMatrix4d2.m12 + paramMatrix4d1.m23 * paramMatrix4d2.m13;
/* 2854 */       this.m22 = paramMatrix4d1.m20 * paramMatrix4d2.m20 + paramMatrix4d1.m21 * paramMatrix4d2.m21 + paramMatrix4d1.m22 * paramMatrix4d2.m22 + paramMatrix4d1.m23 * paramMatrix4d2.m23;
/* 2855 */       this.m23 = paramMatrix4d1.m20 * paramMatrix4d2.m30 + paramMatrix4d1.m21 * paramMatrix4d2.m31 + paramMatrix4d1.m22 * paramMatrix4d2.m32 + paramMatrix4d1.m23 * paramMatrix4d2.m33;
/*      */       
/* 2857 */       this.m30 = paramMatrix4d1.m30 * paramMatrix4d2.m00 + paramMatrix4d1.m31 * paramMatrix4d2.m01 + paramMatrix4d1.m32 * paramMatrix4d2.m02 + paramMatrix4d1.m33 * paramMatrix4d2.m03;
/* 2858 */       this.m31 = paramMatrix4d1.m30 * paramMatrix4d2.m10 + paramMatrix4d1.m31 * paramMatrix4d2.m11 + paramMatrix4d1.m32 * paramMatrix4d2.m12 + paramMatrix4d1.m33 * paramMatrix4d2.m13;
/* 2859 */       this.m32 = paramMatrix4d1.m30 * paramMatrix4d2.m20 + paramMatrix4d1.m31 * paramMatrix4d2.m21 + paramMatrix4d1.m32 * paramMatrix4d2.m22 + paramMatrix4d1.m33 * paramMatrix4d2.m23;
/* 2860 */       this.m33 = paramMatrix4d1.m30 * paramMatrix4d2.m30 + paramMatrix4d1.m31 * paramMatrix4d2.m31 + paramMatrix4d1.m32 * paramMatrix4d2.m32 + paramMatrix4d1.m33 * paramMatrix4d2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2867 */       double d1 = paramMatrix4d1.m00 * paramMatrix4d2.m00 + paramMatrix4d1.m01 * paramMatrix4d2.m01 + paramMatrix4d1.m02 * paramMatrix4d2.m02 + paramMatrix4d1.m03 * paramMatrix4d2.m03;
/* 2868 */       double d2 = paramMatrix4d1.m00 * paramMatrix4d2.m10 + paramMatrix4d1.m01 * paramMatrix4d2.m11 + paramMatrix4d1.m02 * paramMatrix4d2.m12 + paramMatrix4d1.m03 * paramMatrix4d2.m13;
/* 2869 */       double d3 = paramMatrix4d1.m00 * paramMatrix4d2.m20 + paramMatrix4d1.m01 * paramMatrix4d2.m21 + paramMatrix4d1.m02 * paramMatrix4d2.m22 + paramMatrix4d1.m03 * paramMatrix4d2.m23;
/* 2870 */       double d4 = paramMatrix4d1.m00 * paramMatrix4d2.m30 + paramMatrix4d1.m01 * paramMatrix4d2.m31 + paramMatrix4d1.m02 * paramMatrix4d2.m32 + paramMatrix4d1.m03 * paramMatrix4d2.m33;
/*      */       
/* 2872 */       double d5 = paramMatrix4d1.m10 * paramMatrix4d2.m00 + paramMatrix4d1.m11 * paramMatrix4d2.m01 + paramMatrix4d1.m12 * paramMatrix4d2.m02 + paramMatrix4d1.m13 * paramMatrix4d2.m03;
/* 2873 */       double d6 = paramMatrix4d1.m10 * paramMatrix4d2.m10 + paramMatrix4d1.m11 * paramMatrix4d2.m11 + paramMatrix4d1.m12 * paramMatrix4d2.m12 + paramMatrix4d1.m13 * paramMatrix4d2.m13;
/* 2874 */       double d7 = paramMatrix4d1.m10 * paramMatrix4d2.m20 + paramMatrix4d1.m11 * paramMatrix4d2.m21 + paramMatrix4d1.m12 * paramMatrix4d2.m22 + paramMatrix4d1.m13 * paramMatrix4d2.m23;
/* 2875 */       double d8 = paramMatrix4d1.m10 * paramMatrix4d2.m30 + paramMatrix4d1.m11 * paramMatrix4d2.m31 + paramMatrix4d1.m12 * paramMatrix4d2.m32 + paramMatrix4d1.m13 * paramMatrix4d2.m33;
/*      */       
/* 2877 */       double d9 = paramMatrix4d1.m20 * paramMatrix4d2.m00 + paramMatrix4d1.m21 * paramMatrix4d2.m01 + paramMatrix4d1.m22 * paramMatrix4d2.m02 + paramMatrix4d1.m23 * paramMatrix4d2.m03;
/* 2878 */       double d10 = paramMatrix4d1.m20 * paramMatrix4d2.m10 + paramMatrix4d1.m21 * paramMatrix4d2.m11 + paramMatrix4d1.m22 * paramMatrix4d2.m12 + paramMatrix4d1.m23 * paramMatrix4d2.m13;
/* 2879 */       double d11 = paramMatrix4d1.m20 * paramMatrix4d2.m20 + paramMatrix4d1.m21 * paramMatrix4d2.m21 + paramMatrix4d1.m22 * paramMatrix4d2.m22 + paramMatrix4d1.m23 * paramMatrix4d2.m23;
/* 2880 */       double d12 = paramMatrix4d1.m20 * paramMatrix4d2.m30 + paramMatrix4d1.m21 * paramMatrix4d2.m31 + paramMatrix4d1.m22 * paramMatrix4d2.m32 + paramMatrix4d1.m23 * paramMatrix4d2.m33;
/*      */       
/* 2882 */       double d13 = paramMatrix4d1.m30 * paramMatrix4d2.m00 + paramMatrix4d1.m31 * paramMatrix4d2.m01 + paramMatrix4d1.m32 * paramMatrix4d2.m02 + paramMatrix4d1.m33 * paramMatrix4d2.m03;
/* 2883 */       double d14 = paramMatrix4d1.m30 * paramMatrix4d2.m10 + paramMatrix4d1.m31 * paramMatrix4d2.m11 + paramMatrix4d1.m32 * paramMatrix4d2.m12 + paramMatrix4d1.m33 * paramMatrix4d2.m13;
/* 2884 */       double d15 = paramMatrix4d1.m30 * paramMatrix4d2.m20 + paramMatrix4d1.m31 * paramMatrix4d2.m21 + paramMatrix4d1.m32 * paramMatrix4d2.m22 + paramMatrix4d1.m33 * paramMatrix4d2.m23;
/* 2885 */       double d16 = paramMatrix4d1.m30 * paramMatrix4d2.m30 + paramMatrix4d1.m31 * paramMatrix4d2.m31 + paramMatrix4d1.m32 * paramMatrix4d2.m32 + paramMatrix4d1.m33 * paramMatrix4d2.m33;
/*      */       
/* 2887 */       this.m00 = d1; this.m01 = d2; this.m02 = d3; this.m03 = d4;
/* 2888 */       this.m10 = d5; this.m11 = d6; this.m12 = d7; this.m13 = d8;
/* 2889 */       this.m20 = d9; this.m21 = d10; this.m22 = d11; this.m23 = d12;
/* 2890 */       this.m30 = d13; this.m31 = d14; this.m32 = d15; this.m33 = d16;
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
/*      */   public final void mulTransposeLeft(Matrix4d paramMatrix4d1, Matrix4d paramMatrix4d2) {
/* 2903 */     if (this != paramMatrix4d1 && this != paramMatrix4d2) {
/* 2904 */       this.m00 = paramMatrix4d1.m00 * paramMatrix4d2.m00 + paramMatrix4d1.m10 * paramMatrix4d2.m10 + paramMatrix4d1.m20 * paramMatrix4d2.m20 + paramMatrix4d1.m30 * paramMatrix4d2.m30;
/* 2905 */       this.m01 = paramMatrix4d1.m00 * paramMatrix4d2.m01 + paramMatrix4d1.m10 * paramMatrix4d2.m11 + paramMatrix4d1.m20 * paramMatrix4d2.m21 + paramMatrix4d1.m30 * paramMatrix4d2.m31;
/* 2906 */       this.m02 = paramMatrix4d1.m00 * paramMatrix4d2.m02 + paramMatrix4d1.m10 * paramMatrix4d2.m12 + paramMatrix4d1.m20 * paramMatrix4d2.m22 + paramMatrix4d1.m30 * paramMatrix4d2.m32;
/* 2907 */       this.m03 = paramMatrix4d1.m00 * paramMatrix4d2.m03 + paramMatrix4d1.m10 * paramMatrix4d2.m13 + paramMatrix4d1.m20 * paramMatrix4d2.m23 + paramMatrix4d1.m30 * paramMatrix4d2.m33;
/*      */       
/* 2909 */       this.m10 = paramMatrix4d1.m01 * paramMatrix4d2.m00 + paramMatrix4d1.m11 * paramMatrix4d2.m10 + paramMatrix4d1.m21 * paramMatrix4d2.m20 + paramMatrix4d1.m31 * paramMatrix4d2.m30;
/* 2910 */       this.m11 = paramMatrix4d1.m01 * paramMatrix4d2.m01 + paramMatrix4d1.m11 * paramMatrix4d2.m11 + paramMatrix4d1.m21 * paramMatrix4d2.m21 + paramMatrix4d1.m31 * paramMatrix4d2.m31;
/* 2911 */       this.m12 = paramMatrix4d1.m01 * paramMatrix4d2.m02 + paramMatrix4d1.m11 * paramMatrix4d2.m12 + paramMatrix4d1.m21 * paramMatrix4d2.m22 + paramMatrix4d1.m31 * paramMatrix4d2.m32;
/* 2912 */       this.m13 = paramMatrix4d1.m01 * paramMatrix4d2.m03 + paramMatrix4d1.m11 * paramMatrix4d2.m13 + paramMatrix4d1.m21 * paramMatrix4d2.m23 + paramMatrix4d1.m31 * paramMatrix4d2.m33;
/*      */       
/* 2914 */       this.m20 = paramMatrix4d1.m02 * paramMatrix4d2.m00 + paramMatrix4d1.m12 * paramMatrix4d2.m10 + paramMatrix4d1.m22 * paramMatrix4d2.m20 + paramMatrix4d1.m32 * paramMatrix4d2.m30;
/* 2915 */       this.m21 = paramMatrix4d1.m02 * paramMatrix4d2.m01 + paramMatrix4d1.m12 * paramMatrix4d2.m11 + paramMatrix4d1.m22 * paramMatrix4d2.m21 + paramMatrix4d1.m32 * paramMatrix4d2.m31;
/* 2916 */       this.m22 = paramMatrix4d1.m02 * paramMatrix4d2.m02 + paramMatrix4d1.m12 * paramMatrix4d2.m12 + paramMatrix4d1.m22 * paramMatrix4d2.m22 + paramMatrix4d1.m32 * paramMatrix4d2.m32;
/* 2917 */       this.m23 = paramMatrix4d1.m02 * paramMatrix4d2.m03 + paramMatrix4d1.m12 * paramMatrix4d2.m13 + paramMatrix4d1.m22 * paramMatrix4d2.m23 + paramMatrix4d1.m32 * paramMatrix4d2.m33;
/*      */       
/* 2919 */       this.m30 = paramMatrix4d1.m03 * paramMatrix4d2.m00 + paramMatrix4d1.m13 * paramMatrix4d2.m10 + paramMatrix4d1.m23 * paramMatrix4d2.m20 + paramMatrix4d1.m33 * paramMatrix4d2.m30;
/* 2920 */       this.m31 = paramMatrix4d1.m03 * paramMatrix4d2.m01 + paramMatrix4d1.m13 * paramMatrix4d2.m11 + paramMatrix4d1.m23 * paramMatrix4d2.m21 + paramMatrix4d1.m33 * paramMatrix4d2.m31;
/* 2921 */       this.m32 = paramMatrix4d1.m03 * paramMatrix4d2.m02 + paramMatrix4d1.m13 * paramMatrix4d2.m12 + paramMatrix4d1.m23 * paramMatrix4d2.m22 + paramMatrix4d1.m33 * paramMatrix4d2.m32;
/* 2922 */       this.m33 = paramMatrix4d1.m03 * paramMatrix4d2.m03 + paramMatrix4d1.m13 * paramMatrix4d2.m13 + paramMatrix4d1.m23 * paramMatrix4d2.m23 + paramMatrix4d1.m33 * paramMatrix4d2.m33;
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 2931 */       double d1 = paramMatrix4d1.m00 * paramMatrix4d2.m00 + paramMatrix4d1.m10 * paramMatrix4d2.m10 + paramMatrix4d1.m20 * paramMatrix4d2.m20 + paramMatrix4d1.m30 * paramMatrix4d2.m30;
/* 2932 */       double d2 = paramMatrix4d1.m00 * paramMatrix4d2.m01 + paramMatrix4d1.m10 * paramMatrix4d2.m11 + paramMatrix4d1.m20 * paramMatrix4d2.m21 + paramMatrix4d1.m30 * paramMatrix4d2.m31;
/* 2933 */       double d3 = paramMatrix4d1.m00 * paramMatrix4d2.m02 + paramMatrix4d1.m10 * paramMatrix4d2.m12 + paramMatrix4d1.m20 * paramMatrix4d2.m22 + paramMatrix4d1.m30 * paramMatrix4d2.m32;
/* 2934 */       double d4 = paramMatrix4d1.m00 * paramMatrix4d2.m03 + paramMatrix4d1.m10 * paramMatrix4d2.m13 + paramMatrix4d1.m20 * paramMatrix4d2.m23 + paramMatrix4d1.m30 * paramMatrix4d2.m33;
/*      */       
/* 2936 */       double d5 = paramMatrix4d1.m01 * paramMatrix4d2.m00 + paramMatrix4d1.m11 * paramMatrix4d2.m10 + paramMatrix4d1.m21 * paramMatrix4d2.m20 + paramMatrix4d1.m31 * paramMatrix4d2.m30;
/* 2937 */       double d6 = paramMatrix4d1.m01 * paramMatrix4d2.m01 + paramMatrix4d1.m11 * paramMatrix4d2.m11 + paramMatrix4d1.m21 * paramMatrix4d2.m21 + paramMatrix4d1.m31 * paramMatrix4d2.m31;
/* 2938 */       double d7 = paramMatrix4d1.m01 * paramMatrix4d2.m02 + paramMatrix4d1.m11 * paramMatrix4d2.m12 + paramMatrix4d1.m21 * paramMatrix4d2.m22 + paramMatrix4d1.m31 * paramMatrix4d2.m32;
/* 2939 */       double d8 = paramMatrix4d1.m01 * paramMatrix4d2.m03 + paramMatrix4d1.m11 * paramMatrix4d2.m13 + paramMatrix4d1.m21 * paramMatrix4d2.m23 + paramMatrix4d1.m31 * paramMatrix4d2.m33;
/*      */       
/* 2941 */       double d9 = paramMatrix4d1.m02 * paramMatrix4d2.m00 + paramMatrix4d1.m12 * paramMatrix4d2.m10 + paramMatrix4d1.m22 * paramMatrix4d2.m20 + paramMatrix4d1.m32 * paramMatrix4d2.m30;
/* 2942 */       double d10 = paramMatrix4d1.m02 * paramMatrix4d2.m01 + paramMatrix4d1.m12 * paramMatrix4d2.m11 + paramMatrix4d1.m22 * paramMatrix4d2.m21 + paramMatrix4d1.m32 * paramMatrix4d2.m31;
/* 2943 */       double d11 = paramMatrix4d1.m02 * paramMatrix4d2.m02 + paramMatrix4d1.m12 * paramMatrix4d2.m12 + paramMatrix4d1.m22 * paramMatrix4d2.m22 + paramMatrix4d1.m32 * paramMatrix4d2.m32;
/* 2944 */       double d12 = paramMatrix4d1.m02 * paramMatrix4d2.m03 + paramMatrix4d1.m12 * paramMatrix4d2.m13 + paramMatrix4d1.m22 * paramMatrix4d2.m23 + paramMatrix4d1.m32 * paramMatrix4d2.m33;
/*      */       
/* 2946 */       double d13 = paramMatrix4d1.m03 * paramMatrix4d2.m00 + paramMatrix4d1.m13 * paramMatrix4d2.m10 + paramMatrix4d1.m23 * paramMatrix4d2.m20 + paramMatrix4d1.m33 * paramMatrix4d2.m30;
/* 2947 */       double d14 = paramMatrix4d1.m03 * paramMatrix4d2.m01 + paramMatrix4d1.m13 * paramMatrix4d2.m11 + paramMatrix4d1.m23 * paramMatrix4d2.m21 + paramMatrix4d1.m33 * paramMatrix4d2.m31;
/* 2948 */       double d15 = paramMatrix4d1.m03 * paramMatrix4d2.m02 + paramMatrix4d1.m13 * paramMatrix4d2.m12 + paramMatrix4d1.m23 * paramMatrix4d2.m22 + paramMatrix4d1.m33 * paramMatrix4d2.m32;
/* 2949 */       double d16 = paramMatrix4d1.m03 * paramMatrix4d2.m03 + paramMatrix4d1.m13 * paramMatrix4d2.m13 + paramMatrix4d1.m23 * paramMatrix4d2.m23 + paramMatrix4d1.m33 * paramMatrix4d2.m33;
/*      */       
/* 2951 */       this.m00 = d1; this.m01 = d2; this.m02 = d3; this.m03 = d4;
/* 2952 */       this.m10 = d5; this.m11 = d6; this.m12 = d7; this.m13 = d8;
/* 2953 */       this.m20 = d9; this.m21 = d10; this.m22 = d11; this.m23 = d12;
/* 2954 */       this.m30 = d13; this.m31 = d14; this.m32 = d15; this.m33 = d16;
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
/*      */   public boolean equals(Matrix4d paramMatrix4d) {
/*      */     try {
/* 2969 */       return (this.m00 == paramMatrix4d.m00 && this.m01 == paramMatrix4d.m01 && this.m02 == paramMatrix4d.m02 && this.m03 == paramMatrix4d.m03 && this.m10 == paramMatrix4d.m10 && this.m11 == paramMatrix4d.m11 && this.m12 == paramMatrix4d.m12 && this.m13 == paramMatrix4d.m13 && this.m20 == paramMatrix4d.m20 && this.m21 == paramMatrix4d.m21 && this.m22 == paramMatrix4d.m22 && this.m23 == paramMatrix4d.m23 && this.m30 == paramMatrix4d.m30 && this.m31 == paramMatrix4d.m31 && this.m32 == paramMatrix4d.m32 && this.m33 == paramMatrix4d.m33);
/*      */ 
/*      */     
/*      */     }
/*      */     catch (NullPointerException nullPointerException) {
/*      */ 
/*      */       
/* 2976 */       return false;
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
/* 2990 */     try { Matrix4d matrix4d = (Matrix4d)paramObject;
/* 2991 */       return (this.m00 == matrix4d.m00 && this.m01 == matrix4d.m01 && this.m02 == matrix4d.m02 && this.m03 == matrix4d.m03 && this.m10 == matrix4d.m10 && this.m11 == matrix4d.m11 && this.m12 == matrix4d.m12 && this.m13 == matrix4d.m13 && this.m20 == matrix4d.m20 && this.m21 == matrix4d.m21 && this.m22 == matrix4d.m22 && this.m23 == matrix4d.m23 && this.m30 == matrix4d.m30 && this.m31 == matrix4d.m31 && this.m32 == matrix4d.m32 && this.m33 == matrix4d.m33);
/*      */       
/*      */        }
/*      */     
/*      */     catch (ClassCastException classCastException)
/*      */     
/*      */     { 
/* 2998 */       return false; }
/* 2999 */     catch (NullPointerException nullPointerException) { return false; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean epsilonEquals(Matrix4d paramMatrix4d, float paramFloat) {
/* 3006 */     return epsilonEquals(paramMatrix4d, paramFloat);
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
/*      */   public boolean epsilonEquals(Matrix4d paramMatrix4d, double paramDouble) {
/* 3021 */     double d = this.m00 - paramMatrix4d.m00;
/* 3022 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 3024 */     d = this.m01 - paramMatrix4d.m01;
/* 3025 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 3027 */     d = this.m02 - paramMatrix4d.m02;
/* 3028 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 3030 */     d = this.m03 - paramMatrix4d.m03;
/* 3031 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 3033 */     d = this.m10 - paramMatrix4d.m10;
/* 3034 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 3036 */     d = this.m11 - paramMatrix4d.m11;
/* 3037 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 3039 */     d = this.m12 - paramMatrix4d.m12;
/* 3040 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 3042 */     d = this.m13 - paramMatrix4d.m13;
/* 3043 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 3045 */     d = this.m20 - paramMatrix4d.m20;
/* 3046 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 3048 */     d = this.m21 - paramMatrix4d.m21;
/* 3049 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 3051 */     d = this.m22 - paramMatrix4d.m22;
/* 3052 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 3054 */     d = this.m23 - paramMatrix4d.m23;
/* 3055 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 3057 */     d = this.m30 - paramMatrix4d.m30;
/* 3058 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 3060 */     d = this.m31 - paramMatrix4d.m31;
/* 3061 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 3063 */     d = this.m32 - paramMatrix4d.m32;
/* 3064 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 3066 */     d = this.m33 - paramMatrix4d.m33;
/* 3067 */     if (((d < 0.0D) ? -d : d) > paramDouble) return false;
/*      */     
/* 3069 */     return true;
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
/*      */   public int hashCode() {
/* 3081 */     long l = 1L;
/* 3082 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m00);
/* 3083 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m01);
/* 3084 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m02);
/* 3085 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m03);
/* 3086 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m10);
/* 3087 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m11);
/* 3088 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m12);
/* 3089 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m13);
/* 3090 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m20);
/* 3091 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m21);
/* 3092 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m22);
/* 3093 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m23);
/* 3094 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m30);
/* 3095 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m31);
/* 3096 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m32);
/* 3097 */     l = 31L * l + VecMathUtil.doubleToLongBits(this.m33);
/* 3098 */     return (int)(l ^ l >> 32L);
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
/*      */   public final void transform(Tuple4d paramTuple4d1, Tuple4d paramTuple4d2) {
/* 3111 */     double d1 = this.m00 * paramTuple4d1.x + this.m01 * paramTuple4d1.y + this.m02 * paramTuple4d1.z + this.m03 * paramTuple4d1.w;
/*      */     
/* 3113 */     double d2 = this.m10 * paramTuple4d1.x + this.m11 * paramTuple4d1.y + this.m12 * paramTuple4d1.z + this.m13 * paramTuple4d1.w;
/*      */     
/* 3115 */     double d3 = this.m20 * paramTuple4d1.x + this.m21 * paramTuple4d1.y + this.m22 * paramTuple4d1.z + this.m23 * paramTuple4d1.w;
/*      */     
/* 3117 */     paramTuple4d2.w = this.m30 * paramTuple4d1.x + this.m31 * paramTuple4d1.y + this.m32 * paramTuple4d1.z + this.m33 * paramTuple4d1.w;
/*      */     
/* 3119 */     paramTuple4d2.x = d1;
/* 3120 */     paramTuple4d2.y = d2;
/* 3121 */     paramTuple4d2.z = d3;
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
/*      */   public final void transform(Tuple4d paramTuple4d) {
/* 3133 */     double d1 = this.m00 * paramTuple4d.x + this.m01 * paramTuple4d.y + this.m02 * paramTuple4d.z + this.m03 * paramTuple4d.w;
/*      */     
/* 3135 */     double d2 = this.m10 * paramTuple4d.x + this.m11 * paramTuple4d.y + this.m12 * paramTuple4d.z + this.m13 * paramTuple4d.w;
/*      */     
/* 3137 */     double d3 = this.m20 * paramTuple4d.x + this.m21 * paramTuple4d.y + this.m22 * paramTuple4d.z + this.m23 * paramTuple4d.w;
/*      */     
/* 3139 */     paramTuple4d.w = this.m30 * paramTuple4d.x + this.m31 * paramTuple4d.y + this.m32 * paramTuple4d.z + this.m33 * paramTuple4d.w;
/*      */     
/* 3141 */     paramTuple4d.x = d1;
/* 3142 */     paramTuple4d.y = d2;
/* 3143 */     paramTuple4d.z = d3;
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
/*      */   public final void transform(Tuple4f paramTuple4f1, Tuple4f paramTuple4f2) {
/* 3155 */     float f1 = (float)(this.m00 * paramTuple4f1.x + this.m01 * paramTuple4f1.y + this.m02 * paramTuple4f1.z + this.m03 * paramTuple4f1.w);
/*      */     
/* 3157 */     float f2 = (float)(this.m10 * paramTuple4f1.x + this.m11 * paramTuple4f1.y + this.m12 * paramTuple4f1.z + this.m13 * paramTuple4f1.w);
/*      */     
/* 3159 */     float f3 = (float)(this.m20 * paramTuple4f1.x + this.m21 * paramTuple4f1.y + this.m22 * paramTuple4f1.z + this.m23 * paramTuple4f1.w);
/*      */     
/* 3161 */     paramTuple4f2.w = (float)(this.m30 * paramTuple4f1.x + this.m31 * paramTuple4f1.y + this.m32 * paramTuple4f1.z + this.m33 * paramTuple4f1.w);
/*      */     
/* 3163 */     paramTuple4f2.x = f1;
/* 3164 */     paramTuple4f2.y = f2;
/* 3165 */     paramTuple4f2.z = f3;
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
/*      */   public final void transform(Tuple4f paramTuple4f) {
/* 3177 */     float f1 = (float)(this.m00 * paramTuple4f.x + this.m01 * paramTuple4f.y + this.m02 * paramTuple4f.z + this.m03 * paramTuple4f.w);
/*      */     
/* 3179 */     float f2 = (float)(this.m10 * paramTuple4f.x + this.m11 * paramTuple4f.y + this.m12 * paramTuple4f.z + this.m13 * paramTuple4f.w);
/*      */     
/* 3181 */     float f3 = (float)(this.m20 * paramTuple4f.x + this.m21 * paramTuple4f.y + this.m22 * paramTuple4f.z + this.m23 * paramTuple4f.w);
/*      */     
/* 3183 */     paramTuple4f.w = (float)(this.m30 * paramTuple4f.x + this.m31 * paramTuple4f.y + this.m32 * paramTuple4f.z + this.m33 * paramTuple4f.w);
/*      */     
/* 3185 */     paramTuple4f.x = f1;
/* 3186 */     paramTuple4f.y = f2;
/* 3187 */     paramTuple4f.z = f3;
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
/*      */   public final void transform(Point3d paramPoint3d1, Point3d paramPoint3d2) {
/* 3201 */     double d1 = this.m00 * paramPoint3d1.x + this.m01 * paramPoint3d1.y + this.m02 * paramPoint3d1.z + this.m03;
/* 3202 */     double d2 = this.m10 * paramPoint3d1.x + this.m11 * paramPoint3d1.y + this.m12 * paramPoint3d1.z + this.m13;
/* 3203 */     paramPoint3d2.z = this.m20 * paramPoint3d1.x + this.m21 * paramPoint3d1.y + this.m22 * paramPoint3d1.z + this.m23;
/* 3204 */     paramPoint3d2.x = d1;
/* 3205 */     paramPoint3d2.y = d2;
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
/*      */   public final void transform(Point3d paramPoint3d) {
/* 3219 */     double d1 = this.m00 * paramPoint3d.x + this.m01 * paramPoint3d.y + this.m02 * paramPoint3d.z + this.m03;
/* 3220 */     double d2 = this.m10 * paramPoint3d.x + this.m11 * paramPoint3d.y + this.m12 * paramPoint3d.z + this.m13;
/* 3221 */     paramPoint3d.z = this.m20 * paramPoint3d.x + this.m21 * paramPoint3d.y + this.m22 * paramPoint3d.z + this.m23;
/* 3222 */     paramPoint3d.x = d1;
/* 3223 */     paramPoint3d.y = d2;
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
/*      */   public final void transform(Point3f paramPoint3f1, Point3f paramPoint3f2) {
/* 3238 */     float f1 = (float)(this.m00 * paramPoint3f1.x + this.m01 * paramPoint3f1.y + this.m02 * paramPoint3f1.z + this.m03);
/* 3239 */     float f2 = (float)(this.m10 * paramPoint3f1.x + this.m11 * paramPoint3f1.y + this.m12 * paramPoint3f1.z + this.m13);
/* 3240 */     paramPoint3f2.z = (float)(this.m20 * paramPoint3f1.x + this.m21 * paramPoint3f1.y + this.m22 * paramPoint3f1.z + this.m23);
/* 3241 */     paramPoint3f2.x = f1;
/* 3242 */     paramPoint3f2.y = f2;
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
/* 3255 */     float f1 = (float)(this.m00 * paramPoint3f.x + this.m01 * paramPoint3f.y + this.m02 * paramPoint3f.z + this.m03);
/* 3256 */     float f2 = (float)(this.m10 * paramPoint3f.x + this.m11 * paramPoint3f.y + this.m12 * paramPoint3f.z + this.m13);
/* 3257 */     paramPoint3f.z = (float)(this.m20 * paramPoint3f.x + this.m21 * paramPoint3f.y + this.m22 * paramPoint3f.z + this.m23);
/* 3258 */     paramPoint3f.x = f1;
/* 3259 */     paramPoint3f.y = f2;
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
/*      */   public final void transform(Vector3d paramVector3d1, Vector3d paramVector3d2) {
/* 3272 */     double d1 = this.m00 * paramVector3d1.x + this.m01 * paramVector3d1.y + this.m02 * paramVector3d1.z;
/* 3273 */     double d2 = this.m10 * paramVector3d1.x + this.m11 * paramVector3d1.y + this.m12 * paramVector3d1.z;
/* 3274 */     paramVector3d2.z = this.m20 * paramVector3d1.x + this.m21 * paramVector3d1.y + this.m22 * paramVector3d1.z;
/* 3275 */     paramVector3d2.x = d1;
/* 3276 */     paramVector3d2.y = d2;
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
/*      */   public final void transform(Vector3d paramVector3d) {
/* 3289 */     double d1 = this.m00 * paramVector3d.x + this.m01 * paramVector3d.y + this.m02 * paramVector3d.z;
/* 3290 */     double d2 = this.m10 * paramVector3d.x + this.m11 * paramVector3d.y + this.m12 * paramVector3d.z;
/* 3291 */     paramVector3d.z = this.m20 * paramVector3d.x + this.m21 * paramVector3d.y + this.m22 * paramVector3d.z;
/* 3292 */     paramVector3d.x = d1;
/* 3293 */     paramVector3d.y = d2;
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
/* 3306 */     float f1 = (float)(this.m00 * paramVector3f1.x + this.m01 * paramVector3f1.y + this.m02 * paramVector3f1.z);
/* 3307 */     float f2 = (float)(this.m10 * paramVector3f1.x + this.m11 * paramVector3f1.y + this.m12 * paramVector3f1.z);
/* 3308 */     paramVector3f2.z = (float)(this.m20 * paramVector3f1.x + this.m21 * paramVector3f1.y + this.m22 * paramVector3f1.z);
/* 3309 */     paramVector3f2.x = f1;
/* 3310 */     paramVector3f2.y = f2;
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
/* 3323 */     float f1 = (float)(this.m00 * paramVector3f.x + this.m01 * paramVector3f.y + this.m02 * paramVector3f.z);
/* 3324 */     float f2 = (float)(this.m10 * paramVector3f.x + this.m11 * paramVector3f.y + this.m12 * paramVector3f.z);
/* 3325 */     paramVector3f.z = (float)(this.m20 * paramVector3f.x + this.m21 * paramVector3f.y + this.m22 * paramVector3f.z);
/* 3326 */     paramVector3f.x = f1;
/* 3327 */     paramVector3f.y = f2;
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
/*      */   public final void setRotation(Matrix3d paramMatrix3d) {
/* 3341 */     double[] arrayOfDouble1 = new double[9];
/* 3342 */     double[] arrayOfDouble2 = new double[3];
/*      */     
/* 3344 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/* 3346 */     this.m00 = paramMatrix3d.m00 * arrayOfDouble2[0];
/* 3347 */     this.m01 = paramMatrix3d.m01 * arrayOfDouble2[1];
/* 3348 */     this.m02 = paramMatrix3d.m02 * arrayOfDouble2[2];
/*      */     
/* 3350 */     this.m10 = paramMatrix3d.m10 * arrayOfDouble2[0];
/* 3351 */     this.m11 = paramMatrix3d.m11 * arrayOfDouble2[1];
/* 3352 */     this.m12 = paramMatrix3d.m12 * arrayOfDouble2[2];
/*      */     
/* 3354 */     this.m20 = paramMatrix3d.m20 * arrayOfDouble2[0];
/* 3355 */     this.m21 = paramMatrix3d.m21 * arrayOfDouble2[1];
/* 3356 */     this.m22 = paramMatrix3d.m22 * arrayOfDouble2[2];
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
/*      */   public final void setRotation(Matrix3f paramMatrix3f) {
/* 3374 */     double[] arrayOfDouble1 = new double[9];
/* 3375 */     double[] arrayOfDouble2 = new double[3];
/* 3376 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/* 3378 */     this.m00 = paramMatrix3f.m00 * arrayOfDouble2[0];
/* 3379 */     this.m01 = paramMatrix3f.m01 * arrayOfDouble2[1];
/* 3380 */     this.m02 = paramMatrix3f.m02 * arrayOfDouble2[2];
/*      */     
/* 3382 */     this.m10 = paramMatrix3f.m10 * arrayOfDouble2[0];
/* 3383 */     this.m11 = paramMatrix3f.m11 * arrayOfDouble2[1];
/* 3384 */     this.m12 = paramMatrix3f.m12 * arrayOfDouble2[2];
/*      */     
/* 3386 */     this.m20 = paramMatrix3f.m20 * arrayOfDouble2[0];
/* 3387 */     this.m21 = paramMatrix3f.m21 * arrayOfDouble2[1];
/* 3388 */     this.m22 = paramMatrix3f.m22 * arrayOfDouble2[2];
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
/* 3402 */     double[] arrayOfDouble1 = new double[9];
/* 3403 */     double[] arrayOfDouble2 = new double[3];
/* 3404 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/* 3406 */     this.m00 = (1.0D - (2.0F * paramQuat4f.y * paramQuat4f.y) - (2.0F * paramQuat4f.z * paramQuat4f.z)) * arrayOfDouble2[0];
/* 3407 */     this.m10 = 2.0D * (paramQuat4f.x * paramQuat4f.y + paramQuat4f.w * paramQuat4f.z) * arrayOfDouble2[0];
/* 3408 */     this.m20 = 2.0D * (paramQuat4f.x * paramQuat4f.z - paramQuat4f.w * paramQuat4f.y) * arrayOfDouble2[0];
/*      */     
/* 3410 */     this.m01 = 2.0D * (paramQuat4f.x * paramQuat4f.y - paramQuat4f.w * paramQuat4f.z) * arrayOfDouble2[1];
/* 3411 */     this.m11 = (1.0D - (2.0F * paramQuat4f.x * paramQuat4f.x) - (2.0F * paramQuat4f.z * paramQuat4f.z)) * arrayOfDouble2[1];
/* 3412 */     this.m21 = 2.0D * (paramQuat4f.y * paramQuat4f.z + paramQuat4f.w * paramQuat4f.x) * arrayOfDouble2[1];
/*      */     
/* 3414 */     this.m02 = 2.0D * (paramQuat4f.x * paramQuat4f.z + paramQuat4f.w * paramQuat4f.y) * arrayOfDouble2[2];
/* 3415 */     this.m12 = 2.0D * (paramQuat4f.y * paramQuat4f.z - paramQuat4f.w * paramQuat4f.x) * arrayOfDouble2[2];
/* 3416 */     this.m22 = (1.0D - (2.0F * paramQuat4f.x * paramQuat4f.x) - (2.0F * paramQuat4f.y * paramQuat4f.y)) * arrayOfDouble2[2];
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
/*      */   public final void setRotation(Quat4d paramQuat4d) {
/* 3433 */     double[] arrayOfDouble1 = new double[9];
/* 3434 */     double[] arrayOfDouble2 = new double[3];
/* 3435 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/* 3437 */     this.m00 = (1.0D - 2.0D * paramQuat4d.y * paramQuat4d.y - 2.0D * paramQuat4d.z * paramQuat4d.z) * arrayOfDouble2[0];
/* 3438 */     this.m10 = 2.0D * (paramQuat4d.x * paramQuat4d.y + paramQuat4d.w * paramQuat4d.z) * arrayOfDouble2[0];
/* 3439 */     this.m20 = 2.0D * (paramQuat4d.x * paramQuat4d.z - paramQuat4d.w * paramQuat4d.y) * arrayOfDouble2[0];
/*      */     
/* 3441 */     this.m01 = 2.0D * (paramQuat4d.x * paramQuat4d.y - paramQuat4d.w * paramQuat4d.z) * arrayOfDouble2[1];
/* 3442 */     this.m11 = (1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.z * paramQuat4d.z) * arrayOfDouble2[1];
/* 3443 */     this.m21 = 2.0D * (paramQuat4d.y * paramQuat4d.z + paramQuat4d.w * paramQuat4d.x) * arrayOfDouble2[1];
/*      */     
/* 3445 */     this.m02 = 2.0D * (paramQuat4d.x * paramQuat4d.z + paramQuat4d.w * paramQuat4d.y) * arrayOfDouble2[2];
/* 3446 */     this.m12 = 2.0D * (paramQuat4d.y * paramQuat4d.z - paramQuat4d.w * paramQuat4d.x) * arrayOfDouble2[2];
/* 3447 */     this.m22 = (1.0D - 2.0D * paramQuat4d.x * paramQuat4d.x - 2.0D * paramQuat4d.y * paramQuat4d.y) * arrayOfDouble2[2];
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
/*      */   public final void setRotation(AxisAngle4d paramAxisAngle4d) {
/* 3463 */     double[] arrayOfDouble1 = new double[9];
/* 3464 */     double[] arrayOfDouble2 = new double[3];
/*      */     
/* 3466 */     getScaleRotate(arrayOfDouble2, arrayOfDouble1);
/*      */     
/* 3468 */     double d1 = 1.0D / Math.sqrt(paramAxisAngle4d.x * paramAxisAngle4d.x + paramAxisAngle4d.y * paramAxisAngle4d.y + paramAxisAngle4d.z * paramAxisAngle4d.z);
/* 3469 */     double d2 = paramAxisAngle4d.x * d1;
/* 3470 */     double d3 = paramAxisAngle4d.y * d1;
/* 3471 */     double d4 = paramAxisAngle4d.z * d1;
/*      */     
/* 3473 */     double d5 = Math.sin(paramAxisAngle4d.angle);
/* 3474 */     double d6 = Math.cos(paramAxisAngle4d.angle);
/* 3475 */     double d7 = 1.0D - d6;
/*      */     
/* 3477 */     double d8 = paramAxisAngle4d.x * paramAxisAngle4d.z;
/* 3478 */     double d9 = paramAxisAngle4d.x * paramAxisAngle4d.y;
/* 3479 */     double d10 = paramAxisAngle4d.y * paramAxisAngle4d.z;
/*      */     
/* 3481 */     this.m00 = (d7 * d2 * d2 + d6) * arrayOfDouble2[0];
/* 3482 */     this.m01 = (d7 * d9 - d5 * d4) * arrayOfDouble2[1];
/* 3483 */     this.m02 = (d7 * d8 + d5 * d3) * arrayOfDouble2[2];
/*      */     
/* 3485 */     this.m10 = (d7 * d9 + d5 * d4) * arrayOfDouble2[0];
/* 3486 */     this.m11 = (d7 * d3 * d3 + d6) * arrayOfDouble2[1];
/* 3487 */     this.m12 = (d7 * d10 - d5 * d2) * arrayOfDouble2[2];
/*      */     
/* 3489 */     this.m20 = (d7 * d8 - d5 * d3) * arrayOfDouble2[0];
/* 3490 */     this.m21 = (d7 * d10 + d5 * d2) * arrayOfDouble2[1];
/* 3491 */     this.m22 = (d7 * d4 * d4 + d6) * arrayOfDouble2[2];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/* 3500 */     this.m00 = 0.0D;
/* 3501 */     this.m01 = 0.0D;
/* 3502 */     this.m02 = 0.0D;
/* 3503 */     this.m03 = 0.0D;
/* 3504 */     this.m10 = 0.0D;
/* 3505 */     this.m11 = 0.0D;
/* 3506 */     this.m12 = 0.0D;
/* 3507 */     this.m13 = 0.0D;
/* 3508 */     this.m20 = 0.0D;
/* 3509 */     this.m21 = 0.0D;
/* 3510 */     this.m22 = 0.0D;
/* 3511 */     this.m23 = 0.0D;
/* 3512 */     this.m30 = 0.0D;
/* 3513 */     this.m31 = 0.0D;
/* 3514 */     this.m32 = 0.0D;
/* 3515 */     this.m33 = 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/* 3523 */     this.m00 = -this.m00;
/* 3524 */     this.m01 = -this.m01;
/* 3525 */     this.m02 = -this.m02;
/* 3526 */     this.m03 = -this.m03;
/* 3527 */     this.m10 = -this.m10;
/* 3528 */     this.m11 = -this.m11;
/* 3529 */     this.m12 = -this.m12;
/* 3530 */     this.m13 = -this.m13;
/* 3531 */     this.m20 = -this.m20;
/* 3532 */     this.m21 = -this.m21;
/* 3533 */     this.m22 = -this.m22;
/* 3534 */     this.m23 = -this.m23;
/* 3535 */     this.m30 = -this.m30;
/* 3536 */     this.m31 = -this.m31;
/* 3537 */     this.m32 = -this.m32;
/* 3538 */     this.m33 = -this.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate(Matrix4d paramMatrix4d) {
/* 3548 */     this.m00 = -paramMatrix4d.m00;
/* 3549 */     this.m01 = -paramMatrix4d.m01;
/* 3550 */     this.m02 = -paramMatrix4d.m02;
/* 3551 */     this.m03 = -paramMatrix4d.m03;
/* 3552 */     this.m10 = -paramMatrix4d.m10;
/* 3553 */     this.m11 = -paramMatrix4d.m11;
/* 3554 */     this.m12 = -paramMatrix4d.m12;
/* 3555 */     this.m13 = -paramMatrix4d.m13;
/* 3556 */     this.m20 = -paramMatrix4d.m20;
/* 3557 */     this.m21 = -paramMatrix4d.m21;
/* 3558 */     this.m22 = -paramMatrix4d.m22;
/* 3559 */     this.m23 = -paramMatrix4d.m23;
/* 3560 */     this.m30 = -paramMatrix4d.m30;
/* 3561 */     this.m31 = -paramMatrix4d.m31;
/* 3562 */     this.m32 = -paramMatrix4d.m32;
/* 3563 */     this.m33 = -paramMatrix4d.m33;
/*      */   }
/*      */   private final void getScaleRotate(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
/* 3566 */     double[] arrayOfDouble = new double[9];
/* 3567 */     arrayOfDouble[0] = this.m00;
/* 3568 */     arrayOfDouble[1] = this.m01;
/* 3569 */     arrayOfDouble[2] = this.m02;
/*      */     
/* 3571 */     arrayOfDouble[3] = this.m10;
/* 3572 */     arrayOfDouble[4] = this.m11;
/* 3573 */     arrayOfDouble[5] = this.m12;
/*      */     
/* 3575 */     arrayOfDouble[6] = this.m20;
/* 3576 */     arrayOfDouble[7] = this.m21;
/* 3577 */     arrayOfDouble[8] = this.m22;
/*      */     
/* 3579 */     Matrix3d.compute_svd(arrayOfDouble, paramArrayOfdouble1, paramArrayOfdouble2);
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
/* 3593 */     Matrix4d matrix4d = null;
/*      */     try {
/* 3595 */       matrix4d = (Matrix4d)super.clone();
/* 3596 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */       
/* 3598 */       throw new InternalError();
/*      */     } 
/*      */     
/* 3601 */     return matrix4d;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM00() {
/* 3612 */     return this.m00;
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
/*      */   public final void setM00(double paramDouble) {
/* 3624 */     this.m00 = paramDouble;
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
/* 3635 */     return this.m01;
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
/* 3646 */     this.m01 = paramDouble;
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
/* 3657 */     return this.m02;
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
/* 3668 */     this.m02 = paramDouble;
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
/* 3679 */     return this.m10;
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
/* 3690 */     this.m10 = paramDouble;
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
/* 3701 */     return this.m11;
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
/* 3712 */     this.m11 = paramDouble;
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
/* 3723 */     return this.m12;
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
/*      */   public final void setM12(double paramDouble) {
/* 3735 */     this.m12 = paramDouble;
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
/* 3746 */     return this.m20;
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
/* 3757 */     this.m20 = paramDouble;
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
/* 3768 */     return this.m21;
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
/* 3779 */     this.m21 = paramDouble;
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
/* 3790 */     return this.m22;
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
/* 3801 */     this.m22 = paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM03() {
/* 3812 */     return this.m03;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM03(double paramDouble) {
/* 3823 */     this.m03 = paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM13() {
/* 3834 */     return this.m13;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM13(double paramDouble) {
/* 3845 */     this.m13 = paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM23() {
/* 3856 */     return this.m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM23(double paramDouble) {
/* 3867 */     this.m23 = paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM30() {
/* 3878 */     return this.m30;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM30(double paramDouble) {
/* 3889 */     this.m30 = paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM31() {
/* 3900 */     return this.m31;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM31(double paramDouble) {
/* 3911 */     this.m31 = paramDouble;
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
/*      */   public final double getM32() {
/* 3923 */     return this.m32;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM32(double paramDouble) {
/* 3934 */     this.m32 = paramDouble;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM33() {
/* 3945 */     return this.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM33(double paramDouble) {
/* 3956 */     this.m33 = paramDouble;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Matrix4d.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
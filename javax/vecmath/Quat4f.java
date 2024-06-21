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
/*     */ public class Quat4f
/*     */   extends Tuple4f
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 2675933778405442383L;
/*     */   static final double EPS = 1.0E-6D;
/*     */   static final double EPS2 = 1.0E-30D;
/*     */   static final double PIO2 = 1.57079632679D;
/*     */   
/*     */   public Quat4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  60 */     float f = (float)(1.0D / Math.sqrt((paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2 + paramFloat3 * paramFloat3 + paramFloat4 * paramFloat4)));
/*  61 */     this.x = paramFloat1 * f;
/*  62 */     this.y = paramFloat2 * f;
/*  63 */     this.z = paramFloat3 * f;
/*  64 */     this.w = paramFloat4 * f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4f(float[] paramArrayOffloat) {
/*  75 */     float f = (float)(1.0D / Math.sqrt((paramArrayOffloat[0] * paramArrayOffloat[0] + paramArrayOffloat[1] * paramArrayOffloat[1] + paramArrayOffloat[2] * paramArrayOffloat[2] + paramArrayOffloat[3] * paramArrayOffloat[3])));
/*  76 */     this.x = paramArrayOffloat[0] * f;
/*  77 */     this.y = paramArrayOffloat[1] * f;
/*  78 */     this.z = paramArrayOffloat[2] * f;
/*  79 */     this.w = paramArrayOffloat[3] * f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4f(Quat4f paramQuat4f) {
/*  90 */     super(paramQuat4f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4f(Quat4d paramQuat4d) {
/*  99 */     super(paramQuat4d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4f(Tuple4f paramTuple4f) {
/* 110 */     float f = (float)(1.0D / Math.sqrt((paramTuple4f.x * paramTuple4f.x + paramTuple4f.y * paramTuple4f.y + paramTuple4f.z * paramTuple4f.z + paramTuple4f.w * paramTuple4f.w)));
/* 111 */     this.x = paramTuple4f.x * f;
/* 112 */     this.y = paramTuple4f.y * f;
/* 113 */     this.z = paramTuple4f.z * f;
/* 114 */     this.w = paramTuple4f.w * f;
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
/*     */   public Quat4f(Tuple4d paramTuple4d) {
/* 126 */     double d = 1.0D / Math.sqrt(paramTuple4d.x * paramTuple4d.x + paramTuple4d.y * paramTuple4d.y + paramTuple4d.z * paramTuple4d.z + paramTuple4d.w * paramTuple4d.w);
/* 127 */     this.x = (float)(paramTuple4d.x * d);
/* 128 */     this.y = (float)(paramTuple4d.y * d);
/* 129 */     this.z = (float)(paramTuple4d.z * d);
/* 130 */     this.w = (float)(paramTuple4d.w * d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void conjugate(Quat4f paramQuat4f) {
/* 149 */     this.x = -paramQuat4f.x;
/* 150 */     this.y = -paramQuat4f.y;
/* 151 */     this.z = -paramQuat4f.z;
/* 152 */     this.w = paramQuat4f.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void conjugate() {
/* 160 */     this.x = -this.x;
/* 161 */     this.y = -this.y;
/* 162 */     this.z = -this.z;
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
/*     */   public final void mul(Quat4f paramQuat4f1, Quat4f paramQuat4f2) {
/* 175 */     if (this != paramQuat4f1 && this != paramQuat4f2) {
/* 176 */       this.w = paramQuat4f1.w * paramQuat4f2.w - paramQuat4f1.x * paramQuat4f2.x - paramQuat4f1.y * paramQuat4f2.y - paramQuat4f1.z * paramQuat4f2.z;
/* 177 */       this.x = paramQuat4f1.w * paramQuat4f2.x + paramQuat4f2.w * paramQuat4f1.x + paramQuat4f1.y * paramQuat4f2.z - paramQuat4f1.z * paramQuat4f2.y;
/* 178 */       this.y = paramQuat4f1.w * paramQuat4f2.y + paramQuat4f2.w * paramQuat4f1.y - paramQuat4f1.x * paramQuat4f2.z + paramQuat4f1.z * paramQuat4f2.x;
/* 179 */       this.z = paramQuat4f1.w * paramQuat4f2.z + paramQuat4f2.w * paramQuat4f1.z + paramQuat4f1.x * paramQuat4f2.y - paramQuat4f1.y * paramQuat4f2.x;
/*     */     }
/*     */     else {
/*     */       
/* 183 */       float f3 = paramQuat4f1.w * paramQuat4f2.w - paramQuat4f1.x * paramQuat4f2.x - paramQuat4f1.y * paramQuat4f2.y - paramQuat4f1.z * paramQuat4f2.z;
/* 184 */       float f1 = paramQuat4f1.w * paramQuat4f2.x + paramQuat4f2.w * paramQuat4f1.x + paramQuat4f1.y * paramQuat4f2.z - paramQuat4f1.z * paramQuat4f2.y;
/* 185 */       float f2 = paramQuat4f1.w * paramQuat4f2.y + paramQuat4f2.w * paramQuat4f1.y - paramQuat4f1.x * paramQuat4f2.z + paramQuat4f1.z * paramQuat4f2.x;
/* 186 */       this.z = paramQuat4f1.w * paramQuat4f2.z + paramQuat4f2.w * paramQuat4f1.z + paramQuat4f1.x * paramQuat4f2.y - paramQuat4f1.y * paramQuat4f2.x;
/* 187 */       this.w = f3;
/* 188 */       this.x = f1;
/* 189 */       this.y = f2;
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
/*     */   public final void mul(Quat4f paramQuat4f) {
/* 203 */     float f3 = this.w * paramQuat4f.w - this.x * paramQuat4f.x - this.y * paramQuat4f.y - this.z * paramQuat4f.z;
/* 204 */     float f1 = this.w * paramQuat4f.x + paramQuat4f.w * this.x + this.y * paramQuat4f.z - this.z * paramQuat4f.y;
/* 205 */     float f2 = this.w * paramQuat4f.y + paramQuat4f.w * this.y - this.x * paramQuat4f.z + this.z * paramQuat4f.x;
/* 206 */     this.z = this.w * paramQuat4f.z + paramQuat4f.w * this.z + this.x * paramQuat4f.y - this.y * paramQuat4f.x;
/* 207 */     this.w = f3;
/* 208 */     this.x = f1;
/* 209 */     this.y = f2;
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
/*     */   public final void mulInverse(Quat4f paramQuat4f1, Quat4f paramQuat4f2) {
/* 222 */     Quat4f quat4f = new Quat4f(paramQuat4f2);
/*     */     
/* 224 */     quat4f.inverse();
/* 225 */     mul(paramQuat4f1, quat4f);
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
/*     */   public final void mulInverse(Quat4f paramQuat4f) {
/* 238 */     Quat4f quat4f = new Quat4f(paramQuat4f);
/*     */     
/* 240 */     quat4f.inverse();
/* 241 */     mul(quat4f);
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
/*     */   public final void inverse(Quat4f paramQuat4f) {
/* 254 */     float f = 1.0F / (paramQuat4f.w * paramQuat4f.w + paramQuat4f.x * paramQuat4f.x + paramQuat4f.y * paramQuat4f.y + paramQuat4f.z * paramQuat4f.z);
/* 255 */     this.w = f * paramQuat4f.w;
/* 256 */     this.x = -f * paramQuat4f.x;
/* 257 */     this.y = -f * paramQuat4f.y;
/* 258 */     this.z = -f * paramQuat4f.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void inverse() {
/* 269 */     float f = 1.0F / (this.w * this.w + this.x * this.x + this.y * this.y + this.z * this.z);
/* 270 */     this.w *= f;
/* 271 */     this.x *= -f;
/* 272 */     this.y *= -f;
/* 273 */     this.z *= -f;
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
/*     */   public final void normalize(Quat4f paramQuat4f) {
/* 286 */     float f = paramQuat4f.x * paramQuat4f.x + paramQuat4f.y * paramQuat4f.y + paramQuat4f.z * paramQuat4f.z + paramQuat4f.w * paramQuat4f.w;
/*     */     
/* 288 */     if (f > 0.0F) {
/* 289 */       f = 1.0F / (float)Math.sqrt(f);
/* 290 */       this.x = f * paramQuat4f.x;
/* 291 */       this.y = f * paramQuat4f.y;
/* 292 */       this.z = f * paramQuat4f.z;
/* 293 */       this.w = f * paramQuat4f.w;
/*     */     } else {
/* 295 */       this.x = 0.0F;
/* 296 */       this.y = 0.0F;
/* 297 */       this.z = 0.0F;
/* 298 */       this.w = 0.0F;
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
/*     */   public final void normalize() {
/* 310 */     float f = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
/*     */     
/* 312 */     if (f > 0.0F) {
/* 313 */       f = 1.0F / (float)Math.sqrt(f);
/* 314 */       this.x *= f;
/* 315 */       this.y *= f;
/* 316 */       this.z *= f;
/* 317 */       this.w *= f;
/*     */     } else {
/* 319 */       this.x = 0.0F;
/* 320 */       this.y = 0.0F;
/* 321 */       this.z = 0.0F;
/* 322 */       this.w = 0.0F;
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
/*     */   public final void set(Matrix4f paramMatrix4f) {
/* 334 */     float f = 0.25F * (paramMatrix4f.m00 + paramMatrix4f.m11 + paramMatrix4f.m22 + paramMatrix4f.m33);
/*     */     
/* 336 */     if (f >= 0.0F) {
/* 337 */       if (f >= 1.0E-30D) {
/* 338 */         this.w = (float)Math.sqrt(f);
/* 339 */         f = 0.25F / this.w;
/* 340 */         this.x = (paramMatrix4f.m21 - paramMatrix4f.m12) * f;
/* 341 */         this.y = (paramMatrix4f.m02 - paramMatrix4f.m20) * f;
/* 342 */         this.z = (paramMatrix4f.m10 - paramMatrix4f.m01) * f;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 346 */       this.w = 0.0F;
/* 347 */       this.x = 0.0F;
/* 348 */       this.y = 0.0F;
/* 349 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 353 */     this.w = 0.0F;
/* 354 */     f = -0.5F * (paramMatrix4f.m11 + paramMatrix4f.m22);
/*     */     
/* 356 */     if (f >= 0.0F) {
/* 357 */       if (f >= 1.0E-30D) {
/* 358 */         this.x = (float)Math.sqrt(f);
/* 359 */         f = 1.0F / 2.0F * this.x;
/* 360 */         this.y = paramMatrix4f.m10 * f;
/* 361 */         this.z = paramMatrix4f.m20 * f;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 365 */       this.x = 0.0F;
/* 366 */       this.y = 0.0F;
/* 367 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 371 */     this.x = 0.0F;
/* 372 */     f = 0.5F * (1.0F - paramMatrix4f.m22);
/*     */     
/* 374 */     if (f >= 1.0E-30D) {
/* 375 */       this.y = (float)Math.sqrt(f);
/* 376 */       this.z = paramMatrix4f.m21 / 2.0F * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 380 */     this.y = 0.0F;
/* 381 */     this.z = 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Matrix4d paramMatrix4d) {
/* 392 */     double d = 0.25D * (paramMatrix4d.m00 + paramMatrix4d.m11 + paramMatrix4d.m22 + paramMatrix4d.m33);
/*     */     
/* 394 */     if (d >= 0.0D) {
/* 395 */       if (d >= 1.0E-30D) {
/* 396 */         this.w = (float)Math.sqrt(d);
/* 397 */         d = 0.25D / this.w;
/* 398 */         this.x = (float)((paramMatrix4d.m21 - paramMatrix4d.m12) * d);
/* 399 */         this.y = (float)((paramMatrix4d.m02 - paramMatrix4d.m20) * d);
/* 400 */         this.z = (float)((paramMatrix4d.m10 - paramMatrix4d.m01) * d);
/*     */         return;
/*     */       } 
/*     */     } else {
/* 404 */       this.w = 0.0F;
/* 405 */       this.x = 0.0F;
/* 406 */       this.y = 0.0F;
/* 407 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 411 */     this.w = 0.0F;
/* 412 */     d = -0.5D * (paramMatrix4d.m11 + paramMatrix4d.m22);
/* 413 */     if (d >= 0.0D) {
/* 414 */       if (d >= 1.0E-30D) {
/* 415 */         this.x = (float)Math.sqrt(d);
/* 416 */         d = 0.5D / this.x;
/* 417 */         this.y = (float)(paramMatrix4d.m10 * d);
/* 418 */         this.z = (float)(paramMatrix4d.m20 * d);
/*     */         return;
/*     */       } 
/*     */     } else {
/* 422 */       this.x = 0.0F;
/* 423 */       this.y = 0.0F;
/* 424 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 428 */     this.x = 0.0F;
/* 429 */     d = 0.5D * (1.0D - paramMatrix4d.m22);
/* 430 */     if (d >= 1.0E-30D) {
/* 431 */       this.y = (float)Math.sqrt(d);
/* 432 */       this.z = (float)(paramMatrix4d.m21 / 2.0D * this.y);
/*     */       
/*     */       return;
/*     */     } 
/* 436 */     this.y = 0.0F;
/* 437 */     this.z = 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Matrix3f paramMatrix3f) {
/* 448 */     float f = 0.25F * (paramMatrix3f.m00 + paramMatrix3f.m11 + paramMatrix3f.m22 + 1.0F);
/*     */     
/* 450 */     if (f >= 0.0F) {
/* 451 */       if (f >= 1.0E-30D) {
/* 452 */         this.w = (float)Math.sqrt(f);
/* 453 */         f = 0.25F / this.w;
/* 454 */         this.x = (paramMatrix3f.m21 - paramMatrix3f.m12) * f;
/* 455 */         this.y = (paramMatrix3f.m02 - paramMatrix3f.m20) * f;
/* 456 */         this.z = (paramMatrix3f.m10 - paramMatrix3f.m01) * f;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 460 */       this.w = 0.0F;
/* 461 */       this.x = 0.0F;
/* 462 */       this.y = 0.0F;
/* 463 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 467 */     this.w = 0.0F;
/* 468 */     f = -0.5F * (paramMatrix3f.m11 + paramMatrix3f.m22);
/* 469 */     if (f >= 0.0F) {
/* 470 */       if (f >= 1.0E-30D) {
/* 471 */         this.x = (float)Math.sqrt(f);
/* 472 */         f = 0.5F / this.x;
/* 473 */         this.y = paramMatrix3f.m10 * f;
/* 474 */         this.z = paramMatrix3f.m20 * f;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 478 */       this.x = 0.0F;
/* 479 */       this.y = 0.0F;
/* 480 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 484 */     this.x = 0.0F;
/* 485 */     f = 0.5F * (1.0F - paramMatrix3f.m22);
/* 486 */     if (f >= 1.0E-30D) {
/* 487 */       this.y = (float)Math.sqrt(f);
/* 488 */       this.z = paramMatrix3f.m21 / 2.0F * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 492 */     this.y = 0.0F;
/* 493 */     this.z = 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Matrix3d paramMatrix3d) {
/* 504 */     double d = 0.25D * (paramMatrix3d.m00 + paramMatrix3d.m11 + paramMatrix3d.m22 + 1.0D);
/*     */     
/* 506 */     if (d >= 0.0D) {
/* 507 */       if (d >= 1.0E-30D) {
/* 508 */         this.w = (float)Math.sqrt(d);
/* 509 */         d = 0.25D / this.w;
/* 510 */         this.x = (float)((paramMatrix3d.m21 - paramMatrix3d.m12) * d);
/* 511 */         this.y = (float)((paramMatrix3d.m02 - paramMatrix3d.m20) * d);
/* 512 */         this.z = (float)((paramMatrix3d.m10 - paramMatrix3d.m01) * d);
/*     */         return;
/*     */       } 
/*     */     } else {
/* 516 */       this.w = 0.0F;
/* 517 */       this.x = 0.0F;
/* 518 */       this.y = 0.0F;
/* 519 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 523 */     this.w = 0.0F;
/* 524 */     d = -0.5D * (paramMatrix3d.m11 + paramMatrix3d.m22);
/* 525 */     if (d >= 0.0D) {
/* 526 */       if (d >= 1.0E-30D) {
/* 527 */         this.x = (float)Math.sqrt(d);
/* 528 */         d = 0.5D / this.x;
/* 529 */         this.y = (float)(paramMatrix3d.m10 * d);
/* 530 */         this.z = (float)(paramMatrix3d.m20 * d);
/*     */         return;
/*     */       } 
/*     */     } else {
/* 534 */       this.x = 0.0F;
/* 535 */       this.y = 0.0F;
/* 536 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 540 */     this.x = 0.0F;
/* 541 */     d = 0.5D * (1.0D - paramMatrix3d.m22);
/* 542 */     if (d >= 1.0E-30D) {
/* 543 */       this.y = (float)Math.sqrt(d);
/* 544 */       this.z = (float)(paramMatrix3d.m21 / 2.0D * this.y);
/*     */       
/*     */       return;
/*     */     } 
/* 548 */     this.y = 0.0F;
/* 549 */     this.z = 1.0F;
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
/*     */   public final void set(AxisAngle4f paramAxisAngle4f) {
/* 562 */     float f = (float)Math.sqrt((paramAxisAngle4f.x * paramAxisAngle4f.x + paramAxisAngle4f.y * paramAxisAngle4f.y + paramAxisAngle4f.z * paramAxisAngle4f.z));
/* 563 */     if (f < 1.0E-6D) {
/* 564 */       this.w = 0.0F;
/* 565 */       this.x = 0.0F;
/* 566 */       this.y = 0.0F;
/* 567 */       this.z = 0.0F;
/*     */     } else {
/* 569 */       f = 1.0F / f;
/* 570 */       float f1 = (float)Math.sin(paramAxisAngle4f.angle / 2.0D);
/* 571 */       this.w = (float)Math.cos(paramAxisAngle4f.angle / 2.0D);
/* 572 */       this.x = paramAxisAngle4f.x * f * f1;
/* 573 */       this.y = paramAxisAngle4f.y * f * f1;
/* 574 */       this.z = paramAxisAngle4f.z * f * f1;
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
/*     */   public final void set(AxisAngle4d paramAxisAngle4d) {
/* 589 */     float f = (float)(1.0D / Math.sqrt(paramAxisAngle4d.x * paramAxisAngle4d.x + paramAxisAngle4d.y * paramAxisAngle4d.y + paramAxisAngle4d.z * paramAxisAngle4d.z));
/*     */     
/* 591 */     if (f < 1.0E-6D) {
/* 592 */       this.w = 0.0F;
/* 593 */       this.x = 0.0F;
/* 594 */       this.y = 0.0F;
/* 595 */       this.z = 0.0F;
/*     */     } else {
/* 597 */       f = 1.0F / f;
/* 598 */       float f1 = (float)Math.sin(paramAxisAngle4d.angle / 2.0D);
/* 599 */       this.w = (float)Math.cos(paramAxisAngle4d.angle / 2.0D);
/* 600 */       this.x = (float)paramAxisAngle4d.x * f * f1;
/* 601 */       this.y = (float)paramAxisAngle4d.y * f * f1;
/* 602 */       this.z = (float)paramAxisAngle4d.z * f * f1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Quat4f paramQuat4f, float paramFloat) {
/* 625 */     double d2, d3, d1 = (this.x * paramQuat4f.x + this.y * paramQuat4f.y + this.z * paramQuat4f.z + this.w * paramQuat4f.w);
/*     */     
/* 627 */     if (d1 < 0.0D) {
/*     */       
/* 629 */       paramQuat4f.x = -paramQuat4f.x; paramQuat4f.y = -paramQuat4f.y; paramQuat4f.z = -paramQuat4f.z; paramQuat4f.w = -paramQuat4f.w;
/* 630 */       d1 = -d1;
/*     */     } 
/*     */     
/* 633 */     if (1.0D - d1 > 1.0E-6D) {
/* 634 */       double d4 = Math.acos(d1);
/* 635 */       double d5 = Math.sin(d4);
/* 636 */       d2 = Math.sin((1.0D - paramFloat) * d4) / d5;
/* 637 */       d3 = Math.sin(paramFloat * d4) / d5;
/*     */     } else {
/* 639 */       d2 = 1.0D - paramFloat;
/* 640 */       d3 = paramFloat;
/*     */     } 
/*     */     
/* 643 */     this.w = (float)(d2 * this.w + d3 * paramQuat4f.w);
/* 644 */     this.x = (float)(d2 * this.x + d3 * paramQuat4f.x);
/* 645 */     this.y = (float)(d2 * this.y + d3 * paramQuat4f.y);
/* 646 */     this.z = (float)(d2 * this.z + d3 * paramQuat4f.z);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Quat4f paramQuat4f1, Quat4f paramQuat4f2, float paramFloat) {
/* 668 */     double d2, d3, d1 = (paramQuat4f2.x * paramQuat4f1.x + paramQuat4f2.y * paramQuat4f1.y + paramQuat4f2.z * paramQuat4f1.z + paramQuat4f2.w * paramQuat4f1.w);
/*     */     
/* 670 */     if (d1 < 0.0D) {
/*     */       
/* 672 */       paramQuat4f1.x = -paramQuat4f1.x; paramQuat4f1.y = -paramQuat4f1.y; paramQuat4f1.z = -paramQuat4f1.z; paramQuat4f1.w = -paramQuat4f1.w;
/* 673 */       d1 = -d1;
/*     */     } 
/*     */     
/* 676 */     if (1.0D - d1 > 1.0E-6D) {
/* 677 */       double d4 = Math.acos(d1);
/* 678 */       double d5 = Math.sin(d4);
/* 679 */       d2 = Math.sin((1.0D - paramFloat) * d4) / d5;
/* 680 */       d3 = Math.sin(paramFloat * d4) / d5;
/*     */     } else {
/* 682 */       d2 = 1.0D - paramFloat;
/* 683 */       d3 = paramFloat;
/*     */     } 
/* 685 */     this.w = (float)(d2 * paramQuat4f1.w + d3 * paramQuat4f2.w);
/* 686 */     this.x = (float)(d2 * paramQuat4f1.x + d3 * paramQuat4f2.x);
/* 687 */     this.y = (float)(d2 * paramQuat4f1.y + d3 * paramQuat4f2.y);
/* 688 */     this.z = (float)(d2 * paramQuat4f1.z + d3 * paramQuat4f2.z);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Quat4f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
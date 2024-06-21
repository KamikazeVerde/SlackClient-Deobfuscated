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
/*     */ public class Quat4d
/*     */   extends Tuple4d
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 7577479888820201099L;
/*     */   static final double EPS = 1.0E-12D;
/*     */   static final double EPS2 = 1.0E-30D;
/*     */   static final double PIO2 = 1.57079632679D;
/*     */   
/*     */   public Quat4d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  59 */     double d = 1.0D / Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2 + paramDouble3 * paramDouble3 + paramDouble4 * paramDouble4);
/*  60 */     this.x = paramDouble1 * d;
/*  61 */     this.y = paramDouble2 * d;
/*  62 */     this.z = paramDouble3 * d;
/*  63 */     this.w = paramDouble4 * d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4d(double[] paramArrayOfdouble) {
/*  74 */     double d = 1.0D / Math.sqrt(paramArrayOfdouble[0] * paramArrayOfdouble[0] + paramArrayOfdouble[1] * paramArrayOfdouble[1] + paramArrayOfdouble[2] * paramArrayOfdouble[2] + paramArrayOfdouble[3] * paramArrayOfdouble[3]);
/*  75 */     this.x = paramArrayOfdouble[0] * d;
/*  76 */     this.y = paramArrayOfdouble[1] * d;
/*  77 */     this.z = paramArrayOfdouble[2] * d;
/*  78 */     this.w = paramArrayOfdouble[3] * d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4d(Quat4d paramQuat4d) {
/*  88 */     super(paramQuat4d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4d(Quat4f paramQuat4f) {
/*  97 */     super(paramQuat4f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4d(Tuple4f paramTuple4f) {
/* 108 */     double d = 1.0D / Math.sqrt((paramTuple4f.x * paramTuple4f.x + paramTuple4f.y * paramTuple4f.y + paramTuple4f.z * paramTuple4f.z + paramTuple4f.w * paramTuple4f.w));
/* 109 */     this.x = paramTuple4f.x * d;
/* 110 */     this.y = paramTuple4f.y * d;
/* 111 */     this.z = paramTuple4f.z * d;
/* 112 */     this.w = paramTuple4f.w * d;
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
/*     */   public Quat4d(Tuple4d paramTuple4d) {
/* 124 */     double d = 1.0D / Math.sqrt(paramTuple4d.x * paramTuple4d.x + paramTuple4d.y * paramTuple4d.y + paramTuple4d.z * paramTuple4d.z + paramTuple4d.w * paramTuple4d.w);
/* 125 */     this.x = paramTuple4d.x * d;
/* 126 */     this.y = paramTuple4d.y * d;
/* 127 */     this.z = paramTuple4d.z * d;
/* 128 */     this.w = paramTuple4d.w * d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4d() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void conjugate(Quat4d paramQuat4d) {
/* 147 */     this.x = -paramQuat4d.x;
/* 148 */     this.y = -paramQuat4d.y;
/* 149 */     this.z = -paramQuat4d.z;
/* 150 */     this.w = paramQuat4d.w;
/*     */   }
/*     */ 
/*     */ 
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
/*     */   public final void mul(Quat4d paramQuat4d1, Quat4d paramQuat4d2) {
/* 175 */     if (this != paramQuat4d1 && this != paramQuat4d2) {
/* 176 */       this.w = paramQuat4d1.w * paramQuat4d2.w - paramQuat4d1.x * paramQuat4d2.x - paramQuat4d1.y * paramQuat4d2.y - paramQuat4d1.z * paramQuat4d2.z;
/* 177 */       this.x = paramQuat4d1.w * paramQuat4d2.x + paramQuat4d2.w * paramQuat4d1.x + paramQuat4d1.y * paramQuat4d2.z - paramQuat4d1.z * paramQuat4d2.y;
/* 178 */       this.y = paramQuat4d1.w * paramQuat4d2.y + paramQuat4d2.w * paramQuat4d1.y - paramQuat4d1.x * paramQuat4d2.z + paramQuat4d1.z * paramQuat4d2.x;
/* 179 */       this.z = paramQuat4d1.w * paramQuat4d2.z + paramQuat4d2.w * paramQuat4d1.z + paramQuat4d1.x * paramQuat4d2.y - paramQuat4d1.y * paramQuat4d2.x;
/*     */     }
/*     */     else {
/*     */       
/* 183 */       double d3 = paramQuat4d1.w * paramQuat4d2.w - paramQuat4d1.x * paramQuat4d2.x - paramQuat4d1.y * paramQuat4d2.y - paramQuat4d1.z * paramQuat4d2.z;
/* 184 */       double d1 = paramQuat4d1.w * paramQuat4d2.x + paramQuat4d2.w * paramQuat4d1.x + paramQuat4d1.y * paramQuat4d2.z - paramQuat4d1.z * paramQuat4d2.y;
/* 185 */       double d2 = paramQuat4d1.w * paramQuat4d2.y + paramQuat4d2.w * paramQuat4d1.y - paramQuat4d1.x * paramQuat4d2.z + paramQuat4d1.z * paramQuat4d2.x;
/* 186 */       this.z = paramQuat4d1.w * paramQuat4d2.z + paramQuat4d2.w * paramQuat4d1.z + paramQuat4d1.x * paramQuat4d2.y - paramQuat4d1.y * paramQuat4d2.x;
/* 187 */       this.w = d3;
/* 188 */       this.x = d1;
/* 189 */       this.y = d2;
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
/*     */   public final void mul(Quat4d paramQuat4d) {
/* 203 */     double d3 = this.w * paramQuat4d.w - this.x * paramQuat4d.x - this.y * paramQuat4d.y - this.z * paramQuat4d.z;
/* 204 */     double d1 = this.w * paramQuat4d.x + paramQuat4d.w * this.x + this.y * paramQuat4d.z - this.z * paramQuat4d.y;
/* 205 */     double d2 = this.w * paramQuat4d.y + paramQuat4d.w * this.y - this.x * paramQuat4d.z + this.z * paramQuat4d.x;
/* 206 */     this.z = this.w * paramQuat4d.z + paramQuat4d.w * this.z + this.x * paramQuat4d.y - this.y * paramQuat4d.x;
/* 207 */     this.w = d3;
/* 208 */     this.x = d1;
/* 209 */     this.y = d2;
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
/*     */   public final void mulInverse(Quat4d paramQuat4d1, Quat4d paramQuat4d2) {
/* 222 */     Quat4d quat4d = new Quat4d(paramQuat4d2);
/*     */     
/* 224 */     quat4d.inverse();
/* 225 */     mul(paramQuat4d1, quat4d);
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
/*     */   public final void mulInverse(Quat4d paramQuat4d) {
/* 238 */     Quat4d quat4d = new Quat4d(paramQuat4d);
/*     */     
/* 240 */     quat4d.inverse();
/* 241 */     mul(quat4d);
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
/*     */   public final void inverse(Quat4d paramQuat4d) {
/* 253 */     double d = 1.0D / (paramQuat4d.w * paramQuat4d.w + paramQuat4d.x * paramQuat4d.x + paramQuat4d.y * paramQuat4d.y + paramQuat4d.z * paramQuat4d.z);
/* 254 */     this.w = d * paramQuat4d.w;
/* 255 */     this.x = -d * paramQuat4d.x;
/* 256 */     this.y = -d * paramQuat4d.y;
/* 257 */     this.z = -d * paramQuat4d.z;
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
/* 268 */     double d = 1.0D / (this.w * this.w + this.x * this.x + this.y * this.y + this.z * this.z);
/* 269 */     this.w *= d;
/* 270 */     this.x *= -d;
/* 271 */     this.y *= -d;
/* 272 */     this.z *= -d;
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
/*     */   public final void normalize(Quat4d paramQuat4d) {
/* 285 */     double d = paramQuat4d.x * paramQuat4d.x + paramQuat4d.y * paramQuat4d.y + paramQuat4d.z * paramQuat4d.z + paramQuat4d.w * paramQuat4d.w;
/*     */     
/* 287 */     if (d > 0.0D) {
/* 288 */       d = 1.0D / Math.sqrt(d);
/* 289 */       this.x = d * paramQuat4d.x;
/* 290 */       this.y = d * paramQuat4d.y;
/* 291 */       this.z = d * paramQuat4d.z;
/* 292 */       this.w = d * paramQuat4d.w;
/*     */     } else {
/* 294 */       this.x = 0.0D;
/* 295 */       this.y = 0.0D;
/* 296 */       this.z = 0.0D;
/* 297 */       this.w = 0.0D;
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
/* 309 */     double d = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
/*     */     
/* 311 */     if (d > 0.0D) {
/* 312 */       d = 1.0D / Math.sqrt(d);
/* 313 */       this.x *= d;
/* 314 */       this.y *= d;
/* 315 */       this.z *= d;
/* 316 */       this.w *= d;
/*     */     } else {
/* 318 */       this.x = 0.0D;
/* 319 */       this.y = 0.0D;
/* 320 */       this.z = 0.0D;
/* 321 */       this.w = 0.0D;
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
/* 333 */     double d = 0.25D * (paramMatrix4f.m00 + paramMatrix4f.m11 + paramMatrix4f.m22 + paramMatrix4f.m33);
/*     */     
/* 335 */     if (d >= 0.0D) {
/* 336 */       if (d >= 1.0E-30D) {
/* 337 */         this.w = Math.sqrt(d);
/* 338 */         d = 0.25D / this.w;
/* 339 */         this.x = (paramMatrix4f.m21 - paramMatrix4f.m12) * d;
/* 340 */         this.y = (paramMatrix4f.m02 - paramMatrix4f.m20) * d;
/* 341 */         this.z = (paramMatrix4f.m10 - paramMatrix4f.m01) * d;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 345 */       this.w = 0.0D;
/* 346 */       this.x = 0.0D;
/* 347 */       this.y = 0.0D;
/* 348 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 352 */     this.w = 0.0D;
/* 353 */     d = -0.5D * (paramMatrix4f.m11 + paramMatrix4f.m22);
/* 354 */     if (d >= 0.0D) {
/* 355 */       if (d >= 1.0E-30D) {
/* 356 */         this.x = Math.sqrt(d);
/* 357 */         d = 1.0D / 2.0D * this.x;
/* 358 */         this.y = paramMatrix4f.m10 * d;
/* 359 */         this.z = paramMatrix4f.m20 * d;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 363 */       this.x = 0.0D;
/* 364 */       this.y = 0.0D;
/* 365 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 369 */     this.x = 0.0D;
/* 370 */     d = 0.5D * (1.0D - paramMatrix4f.m22);
/* 371 */     if (d >= 1.0E-30D) {
/* 372 */       this.y = Math.sqrt(d);
/* 373 */       this.z = paramMatrix4f.m21 / 2.0D * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 377 */     this.y = 0.0D;
/* 378 */     this.z = 1.0D;
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
/* 389 */     double d = 0.25D * (paramMatrix4d.m00 + paramMatrix4d.m11 + paramMatrix4d.m22 + paramMatrix4d.m33);
/*     */     
/* 391 */     if (d >= 0.0D) {
/* 392 */       if (d >= 1.0E-30D) {
/* 393 */         this.w = Math.sqrt(d);
/* 394 */         d = 0.25D / this.w;
/* 395 */         this.x = (paramMatrix4d.m21 - paramMatrix4d.m12) * d;
/* 396 */         this.y = (paramMatrix4d.m02 - paramMatrix4d.m20) * d;
/* 397 */         this.z = (paramMatrix4d.m10 - paramMatrix4d.m01) * d;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 401 */       this.w = 0.0D;
/* 402 */       this.x = 0.0D;
/* 403 */       this.y = 0.0D;
/* 404 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 408 */     this.w = 0.0D;
/* 409 */     d = -0.5D * (paramMatrix4d.m11 + paramMatrix4d.m22);
/* 410 */     if (d >= 0.0D) {
/* 411 */       if (d >= 1.0E-30D) {
/* 412 */         this.x = Math.sqrt(d);
/* 413 */         d = 0.5D / this.x;
/* 414 */         this.y = paramMatrix4d.m10 * d;
/* 415 */         this.z = paramMatrix4d.m20 * d;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 419 */       this.x = 0.0D;
/* 420 */       this.y = 0.0D;
/* 421 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 425 */     this.x = 0.0D;
/* 426 */     d = 0.5D * (1.0D - paramMatrix4d.m22);
/* 427 */     if (d >= 1.0E-30D) {
/* 428 */       this.y = Math.sqrt(d);
/* 429 */       this.z = paramMatrix4d.m21 / 2.0D * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 433 */     this.y = 0.0D;
/* 434 */     this.z = 1.0D;
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
/* 445 */     double d = 0.25D * ((paramMatrix3f.m00 + paramMatrix3f.m11 + paramMatrix3f.m22) + 1.0D);
/*     */     
/* 447 */     if (d >= 0.0D) {
/* 448 */       if (d >= 1.0E-30D) {
/* 449 */         this.w = Math.sqrt(d);
/* 450 */         d = 0.25D / this.w;
/* 451 */         this.x = (paramMatrix3f.m21 - paramMatrix3f.m12) * d;
/* 452 */         this.y = (paramMatrix3f.m02 - paramMatrix3f.m20) * d;
/* 453 */         this.z = (paramMatrix3f.m10 - paramMatrix3f.m01) * d;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 457 */       this.w = 0.0D;
/* 458 */       this.x = 0.0D;
/* 459 */       this.y = 0.0D;
/* 460 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 464 */     this.w = 0.0D;
/* 465 */     d = -0.5D * (paramMatrix3f.m11 + paramMatrix3f.m22);
/* 466 */     if (d >= 0.0D) {
/* 467 */       if (d >= 1.0E-30D) {
/* 468 */         this.x = Math.sqrt(d);
/* 469 */         d = 0.5D / this.x;
/* 470 */         this.y = paramMatrix3f.m10 * d;
/* 471 */         this.z = paramMatrix3f.m20 * d;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 475 */       this.x = 0.0D;
/* 476 */       this.y = 0.0D;
/* 477 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 481 */     this.x = 0.0D;
/* 482 */     d = 0.5D * (1.0D - paramMatrix3f.m22);
/* 483 */     if (d >= 1.0E-30D) {
/* 484 */       this.y = Math.sqrt(d);
/* 485 */       this.z = paramMatrix3f.m21 / 2.0D * this.y;
/*     */     } 
/*     */     
/* 488 */     this.y = 0.0D;
/* 489 */     this.z = 1.0D;
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
/* 500 */     double d = 0.25D * (paramMatrix3d.m00 + paramMatrix3d.m11 + paramMatrix3d.m22 + 1.0D);
/*     */     
/* 502 */     if (d >= 0.0D) {
/* 503 */       if (d >= 1.0E-30D) {
/* 504 */         this.w = Math.sqrt(d);
/* 505 */         d = 0.25D / this.w;
/* 506 */         this.x = (paramMatrix3d.m21 - paramMatrix3d.m12) * d;
/* 507 */         this.y = (paramMatrix3d.m02 - paramMatrix3d.m20) * d;
/* 508 */         this.z = (paramMatrix3d.m10 - paramMatrix3d.m01) * d;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 512 */       this.w = 0.0D;
/* 513 */       this.x = 0.0D;
/* 514 */       this.y = 0.0D;
/* 515 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 519 */     this.w = 0.0D;
/* 520 */     d = -0.5D * (paramMatrix3d.m11 + paramMatrix3d.m22);
/* 521 */     if (d >= 0.0D) {
/* 522 */       if (d >= 1.0E-30D) {
/* 523 */         this.x = Math.sqrt(d);
/* 524 */         d = 0.5D / this.x;
/* 525 */         this.y = paramMatrix3d.m10 * d;
/* 526 */         this.z = paramMatrix3d.m20 * d;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 530 */       this.x = 0.0D;
/* 531 */       this.y = 0.0D;
/* 532 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 536 */     this.x = 0.0D;
/* 537 */     d = 0.5D * (1.0D - paramMatrix3d.m22);
/* 538 */     if (d >= 1.0E-30D) {
/* 539 */       this.y = Math.sqrt(d);
/* 540 */       this.z = paramMatrix3d.m21 / 2.0D * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 544 */     this.y = 0.0D;
/* 545 */     this.z = 1.0D;
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
/*     */   public final void set(AxisAngle4f paramAxisAngle4f) {
/* 559 */     double d = Math.sqrt((paramAxisAngle4f.x * paramAxisAngle4f.x + paramAxisAngle4f.y * paramAxisAngle4f.y + paramAxisAngle4f.z * paramAxisAngle4f.z));
/* 560 */     if (d < 1.0E-12D) {
/* 561 */       this.w = 0.0D;
/* 562 */       this.x = 0.0D;
/* 563 */       this.y = 0.0D;
/* 564 */       this.z = 0.0D;
/*     */     } else {
/* 566 */       double d1 = Math.sin(paramAxisAngle4f.angle / 2.0D);
/* 567 */       d = 1.0D / d;
/* 568 */       this.w = Math.cos(paramAxisAngle4f.angle / 2.0D);
/* 569 */       this.x = paramAxisAngle4f.x * d * d1;
/* 570 */       this.y = paramAxisAngle4f.y * d * d1;
/* 571 */       this.z = paramAxisAngle4f.z * d * d1;
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
/* 586 */     double d = Math.sqrt(paramAxisAngle4d.x * paramAxisAngle4d.x + paramAxisAngle4d.y * paramAxisAngle4d.y + paramAxisAngle4d.z * paramAxisAngle4d.z);
/* 587 */     if (d < 1.0E-12D) {
/* 588 */       this.w = 0.0D;
/* 589 */       this.x = 0.0D;
/* 590 */       this.y = 0.0D;
/* 591 */       this.z = 0.0D;
/*     */     } else {
/* 593 */       d = 1.0D / d;
/* 594 */       double d1 = Math.sin(paramAxisAngle4d.angle / 2.0D);
/* 595 */       this.w = Math.cos(paramAxisAngle4d.angle / 2.0D);
/* 596 */       this.x = paramAxisAngle4d.x * d * d1;
/* 597 */       this.y = paramAxisAngle4d.y * d * d1;
/* 598 */       this.z = paramAxisAngle4d.z * d * d1;
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
/*     */   public final void interpolate(Quat4d paramQuat4d, double paramDouble) {
/* 619 */     double d2, d3, d1 = this.x * paramQuat4d.x + this.y * paramQuat4d.y + this.z * paramQuat4d.z + this.w * paramQuat4d.w;
/*     */     
/* 621 */     if (d1 < 0.0D) {
/*     */       
/* 623 */       paramQuat4d.x = -paramQuat4d.x; paramQuat4d.y = -paramQuat4d.y; paramQuat4d.z = -paramQuat4d.z; paramQuat4d.w = -paramQuat4d.w;
/* 624 */       d1 = -d1;
/*     */     } 
/*     */     
/* 627 */     if (1.0D - d1 > 1.0E-12D) {
/* 628 */       double d4 = Math.acos(d1);
/* 629 */       double d5 = Math.sin(d4);
/* 630 */       d2 = Math.sin((1.0D - paramDouble) * d4) / d5;
/* 631 */       d3 = Math.sin(paramDouble * d4) / d5;
/*     */     } else {
/* 633 */       d2 = 1.0D - paramDouble;
/* 634 */       d3 = paramDouble;
/*     */     } 
/*     */     
/* 637 */     this.w = d2 * this.w + d3 * paramQuat4d.w;
/* 638 */     this.x = d2 * this.x + d3 * paramQuat4d.x;
/* 639 */     this.y = d2 * this.y + d3 * paramQuat4d.y;
/* 640 */     this.z = d2 * this.z + d3 * paramQuat4d.z;
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
/*     */   public final void interpolate(Quat4d paramQuat4d1, Quat4d paramQuat4d2, double paramDouble) {
/* 659 */     double d2, d3, d1 = paramQuat4d2.x * paramQuat4d1.x + paramQuat4d2.y * paramQuat4d1.y + paramQuat4d2.z * paramQuat4d1.z + paramQuat4d2.w * paramQuat4d1.w;
/*     */     
/* 661 */     if (d1 < 0.0D) {
/*     */       
/* 663 */       paramQuat4d1.x = -paramQuat4d1.x; paramQuat4d1.y = -paramQuat4d1.y; paramQuat4d1.z = -paramQuat4d1.z; paramQuat4d1.w = -paramQuat4d1.w;
/* 664 */       d1 = -d1;
/*     */     } 
/*     */     
/* 667 */     if (1.0D - d1 > 1.0E-12D) {
/* 668 */       double d4 = Math.acos(d1);
/* 669 */       double d5 = Math.sin(d4);
/* 670 */       d2 = Math.sin((1.0D - paramDouble) * d4) / d5;
/* 671 */       d3 = Math.sin(paramDouble * d4) / d5;
/*     */     } else {
/* 673 */       d2 = 1.0D - paramDouble;
/* 674 */       d3 = paramDouble;
/*     */     } 
/* 676 */     this.w = d2 * paramQuat4d1.w + d3 * paramQuat4d2.w;
/* 677 */     this.x = d2 * paramQuat4d1.x + d3 * paramQuat4d2.x;
/* 678 */     this.y = d2 * paramQuat4d1.y + d3 * paramQuat4d2.y;
/* 679 */     this.z = d2 * paramQuat4d1.z + d3 * paramQuat4d2.z;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Quat4d.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
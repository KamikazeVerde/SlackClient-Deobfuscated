/*     */ package de.gerrygames.viarewind.utils.math;
/*     */ 
/*     */ import java.util.Objects;
/*     */ 
/*     */ public class Vector3d {
/*     */   double x;
/*     */   
/*     */   public Vector3d(double x, double y, double z) {
/*   9 */     this.x = x;
/*  10 */     this.y = y;
/*  11 */     this.z = z;
/*     */   }
/*     */   double y; double z;
/*     */   
/*     */   public Vector3d() {}
/*     */   
/*     */   public void set(Vector3d vec) {
/*  18 */     this.x = vec.x;
/*  19 */     this.y = vec.y;
/*  20 */     this.z = vec.z;
/*     */   }
/*     */   
/*     */   public Vector3d set(double x, double y, double z) {
/*  24 */     this.x = x;
/*  25 */     this.y = y;
/*  26 */     this.z = z;
/*  27 */     return this;
/*     */   }
/*     */   
/*     */   public Vector3d multiply(double a) {
/*  31 */     this.x *= a;
/*  32 */     this.y *= a;
/*  33 */     this.z *= a;
/*  34 */     return this;
/*     */   }
/*     */   
/*     */   public Vector3d add(Vector3d vec) {
/*  38 */     this.x += vec.x;
/*  39 */     this.y += vec.y;
/*  40 */     this.z += vec.z;
/*  41 */     return this;
/*     */   }
/*     */   
/*     */   public Vector3d substract(Vector3d vec) {
/*  45 */     this.x -= vec.x;
/*  46 */     this.y -= vec.y;
/*  47 */     this.z -= vec.z;
/*  48 */     return this;
/*     */   }
/*     */   
/*     */   public double length() {
/*  52 */     return Math.sqrt(lengthSquared());
/*     */   }
/*     */   
/*     */   public double lengthSquared() {
/*  56 */     return this.x * this.x + this.y * this.y + this.z * this.z;
/*     */   }
/*     */   
/*     */   public Vector3d normalize() {
/*  60 */     double length = length();
/*  61 */     multiply(1.0D / length);
/*  62 */     return this;
/*     */   }
/*     */   
/*     */   public Vector3d clone() {
/*  66 */     return new Vector3d(this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  71 */     if (this == o) return true; 
/*  72 */     if (o == null || getClass() != o.getClass()) return false; 
/*  73 */     Vector3d vector3d = (Vector3d)o;
/*  74 */     return (Double.compare(vector3d.x, this.x) == 0 && Double.compare(vector3d.y, this.y) == 0 && Double.compare(vector3d.z, this.z) == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  79 */     return Objects.hash(new Object[] { Double.valueOf(this.x), Double.valueOf(this.y), Double.valueOf(this.z) });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  84 */     return "Vector3d{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
/*     */   }
/*     */   
/*     */   public double getX() {
/*  88 */     return this.x;
/*     */   }
/*     */   
/*     */   public double getY() {
/*  92 */     return this.y;
/*     */   }
/*     */   
/*     */   public double getZ() {
/*  96 */     return this.z;
/*     */   }
/*     */   
/*     */   public void setX(double x) {
/* 100 */     this.x = x;
/*     */   }
/*     */   
/*     */   public void setY(double y) {
/* 104 */     this.y = y;
/*     */   }
/*     */   
/*     */   public void setZ(double z) {
/* 108 */     this.z = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewin\\utils\math\Vector3d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
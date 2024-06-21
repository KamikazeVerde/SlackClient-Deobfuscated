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
/*     */ public class Vector2d
/*     */   extends Tuple2d
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 8572646365302599857L;
/*     */   
/*     */   public Vector2d(double paramDouble1, double paramDouble2) {
/*  53 */     super(paramDouble1, paramDouble2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2d(double[] paramArrayOfdouble) {
/*  63 */     super(paramArrayOfdouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2d(Vector2d paramVector2d) {
/*  73 */     super(paramVector2d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2d(Vector2f paramVector2f) {
/*  83 */     super(paramVector2f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2d(Tuple2d paramTuple2d) {
/*  93 */     super(paramTuple2d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2d(Tuple2f paramTuple2f) {
/* 103 */     super(paramTuple2f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2d() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double dot(Vector2d paramVector2d) {
/* 122 */     return this.x * paramVector2d.x + this.y * paramVector2d.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double length() {
/* 132 */     return Math.sqrt(this.x * this.x + this.y * this.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double lengthSquared() {
/* 141 */     return this.x * this.x + this.y * this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize(Vector2d paramVector2d) {
/* 152 */     double d = 1.0D / Math.sqrt(paramVector2d.x * paramVector2d.x + paramVector2d.y * paramVector2d.y);
/* 153 */     paramVector2d.x *= d;
/* 154 */     paramVector2d.y *= d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize() {
/* 164 */     double d = 1.0D / Math.sqrt(this.x * this.x + this.y * this.y);
/*     */     
/* 166 */     this.x *= d;
/* 167 */     this.y *= d;
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
/*     */   public final double angle(Vector2d paramVector2d) {
/* 179 */     double d = dot(paramVector2d) / length() * paramVector2d.length();
/* 180 */     if (d < -1.0D) d = -1.0D; 
/* 181 */     if (d > 1.0D) d = 1.0D; 
/* 182 */     return Math.acos(d);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Vector2d.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
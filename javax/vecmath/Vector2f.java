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
/*     */ public class Vector2f
/*     */   extends Tuple2f
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -2168194326883512320L;
/*     */   
/*     */   public Vector2f(float paramFloat1, float paramFloat2) {
/*  53 */     super(paramFloat1, paramFloat2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f(float[] paramArrayOffloat) {
/*  63 */     super(paramArrayOffloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f(Vector2f paramVector2f) {
/*  73 */     super(paramVector2f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f(Vector2d paramVector2d) {
/*  83 */     super(paramVector2d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f(Tuple2f paramTuple2f) {
/*  93 */     super(paramTuple2f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f(Tuple2d paramTuple2d) {
/* 103 */     super(paramTuple2d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float dot(Vector2f paramVector2f) {
/* 123 */     return this.x * paramVector2f.x + this.y * paramVector2f.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float length() {
/* 133 */     return (float)Math.sqrt((this.x * this.x + this.y * this.y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float lengthSquared() {
/* 142 */     return this.x * this.x + this.y * this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize(Vector2f paramVector2f) {
/* 153 */     float f = (float)(1.0D / Math.sqrt((paramVector2f.x * paramVector2f.x + paramVector2f.y * paramVector2f.y)));
/* 154 */     paramVector2f.x *= f;
/* 155 */     paramVector2f.y *= f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize() {
/* 165 */     float f = (float)(1.0D / Math.sqrt((this.x * this.x + this.y * this.y)));
/*     */     
/* 167 */     this.x *= f;
/* 168 */     this.y *= f;
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
/*     */   public final float angle(Vector2f paramVector2f) {
/* 180 */     double d = (dot(paramVector2f) / length() * paramVector2f.length());
/* 181 */     if (d < -1.0D) d = -1.0D; 
/* 182 */     if (d > 1.0D) d = 1.0D; 
/* 183 */     return (float)Math.acos(d);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Vector2f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
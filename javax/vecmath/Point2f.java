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
/*     */ public class Point2f
/*     */   extends Tuple2f
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -4801347926528714435L;
/*     */   
/*     */   public Point2f(float paramFloat1, float paramFloat2) {
/*  53 */     super(paramFloat1, paramFloat2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2f(float[] paramArrayOffloat) {
/*  63 */     super(paramArrayOffloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2f(Point2f paramPoint2f) {
/*  73 */     super(paramPoint2f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2f(Point2d paramPoint2d) {
/*  82 */     super(paramPoint2d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2f(Tuple2d paramTuple2d) {
/*  93 */     super(paramTuple2d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2f(Tuple2f paramTuple2f) {
/* 104 */     super(paramTuple2f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float distanceSquared(Point2f paramPoint2f) {
/* 124 */     float f1 = this.x - paramPoint2f.x;
/* 125 */     float f2 = this.y - paramPoint2f.y;
/* 126 */     return f1 * f1 + f2 * f2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float distance(Point2f paramPoint2f) {
/* 137 */     float f1 = this.x - paramPoint2f.x;
/* 138 */     float f2 = this.y - paramPoint2f.y;
/* 139 */     return (float)Math.sqrt((f1 * f1 + f2 * f2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float distanceL1(Point2f paramPoint2f) {
/* 150 */     return Math.abs(this.x - paramPoint2f.x) + Math.abs(this.y - paramPoint2f.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float distanceLinf(Point2f paramPoint2f) {
/* 161 */     return Math.max(Math.abs(this.x - paramPoint2f.x), Math.abs(this.y - paramPoint2f.y));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Point2f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
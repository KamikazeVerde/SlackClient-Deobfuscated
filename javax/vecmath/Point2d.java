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
/*     */ public class Point2d
/*     */   extends Tuple2d
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 1133748791492571954L;
/*     */   
/*     */   public Point2d(double paramDouble1, double paramDouble2) {
/*  53 */     super(paramDouble1, paramDouble2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2d(double[] paramArrayOfdouble) {
/*  63 */     super(paramArrayOfdouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2d(Point2d paramPoint2d) {
/*  73 */     super(paramPoint2d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2d(Point2f paramPoint2f) {
/*  83 */     super(paramPoint2f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2d(Tuple2d paramTuple2d) {
/*  93 */     super(paramTuple2d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2d(Tuple2f paramTuple2f) {
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
/*     */   public Point2d() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double distanceSquared(Point2d paramPoint2d) {
/* 123 */     double d1 = this.x - paramPoint2d.x;
/* 124 */     double d2 = this.y - paramPoint2d.y;
/* 125 */     return d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double distance(Point2d paramPoint2d) {
/* 136 */     double d1 = this.x - paramPoint2d.x;
/* 137 */     double d2 = this.y - paramPoint2d.y;
/* 138 */     return Math.sqrt(d1 * d1 + d2 * d2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double distanceL1(Point2d paramPoint2d) {
/* 149 */     return Math.abs(this.x - paramPoint2d.x) + Math.abs(this.y - paramPoint2d.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double distanceLinf(Point2d paramPoint2d) {
/* 160 */     return Math.max(Math.abs(this.x - paramPoint2d.x), Math.abs(this.y - paramPoint2d.y));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Point2d.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package javax.vecmath;
/*     */ 
/*     */ import java.awt.Color;
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
/*     */ 
/*     */ public class Color3f
/*     */   extends Tuple3f
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -1861792981817493659L;
/*     */   
/*     */   public Color3f(float paramFloat1, float paramFloat2, float paramFloat3) {
/*  59 */     super(paramFloat1, paramFloat2, paramFloat3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color3f(float[] paramArrayOffloat) {
/*  68 */     super(paramArrayOffloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color3f(Color3f paramColor3f) {
/*  77 */     super(paramColor3f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color3f(Tuple3f paramTuple3f) {
/*  86 */     super(paramTuple3f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color3f(Tuple3d paramTuple3d) {
/*  95 */     super(paramTuple3d);
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
/*     */   public Color3f(Color paramColor) {
/* 111 */     super(paramColor.getRed() / 255.0F, paramColor.getGreen() / 255.0F, paramColor.getBlue() / 255.0F);
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
/*     */   public Color3f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Color paramColor) {
/* 136 */     this.x = paramColor.getRed() / 255.0F;
/* 137 */     this.y = paramColor.getGreen() / 255.0F;
/* 138 */     this.z = paramColor.getBlue() / 255.0F;
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
/*     */   public final Color get() {
/* 151 */     int i = Math.round(this.x * 255.0F);
/* 152 */     int j = Math.round(this.y * 255.0F);
/* 153 */     int k = Math.round(this.z * 255.0F);
/*     */     
/* 155 */     return new Color(i, j, k);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Color3f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
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
/*     */ 
/*     */ 
/*     */ public class Color4f
/*     */   extends Tuple4f
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 8577680141580006740L;
/*     */   
/*     */   public Color4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  61 */     super(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color4f(float[] paramArrayOffloat) {
/*  70 */     super(paramArrayOffloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color4f(Color4f paramColor4f) {
/*  79 */     super(paramColor4f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color4f(Tuple4f paramTuple4f) {
/*  88 */     super(paramTuple4f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color4f(Tuple4d paramTuple4d) {
/*  97 */     super(paramTuple4d);
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
/*     */   public Color4f(Color paramColor) {
/* 113 */     super(paramColor.getRed() / 255.0F, paramColor.getGreen() / 255.0F, paramColor.getBlue() / 255.0F, paramColor.getAlpha() / 255.0F);
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
/*     */   public Color4f() {}
/*     */ 
/*     */ 
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
/* 139 */     this.x = paramColor.getRed() / 255.0F;
/* 140 */     this.y = paramColor.getGreen() / 255.0F;
/* 141 */     this.z = paramColor.getBlue() / 255.0F;
/* 142 */     this.w = paramColor.getAlpha() / 255.0F;
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
/* 155 */     int i = Math.round(this.x * 255.0F);
/* 156 */     int j = Math.round(this.y * 255.0F);
/* 157 */     int k = Math.round(this.z * 255.0F);
/* 158 */     int m = Math.round(this.w * 255.0F);
/*     */     
/* 160 */     return new Color(i, j, k, m);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Color4f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
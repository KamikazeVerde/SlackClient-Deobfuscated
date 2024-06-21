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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Color4b
/*     */   extends Tuple4b
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -105080578052502155L;
/*     */   
/*     */   public Color4b(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4) {
/*  65 */     super(paramByte1, paramByte2, paramByte3, paramByte4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color4b(byte[] paramArrayOfbyte) {
/*  74 */     super(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color4b(Color4b paramColor4b) {
/*  84 */     super(paramColor4b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color4b(Tuple4b paramTuple4b) {
/*  94 */     super(paramTuple4b);
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
/*     */   public Color4b(Color paramColor) {
/* 110 */     super((byte)paramColor.getRed(), (byte)paramColor.getGreen(), (byte)paramColor.getBlue(), (byte)paramColor.getAlpha());
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
/*     */   public Color4b() {}
/*     */ 
/*     */ 
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
/* 136 */     this.x = (byte)paramColor.getRed();
/* 137 */     this.y = (byte)paramColor.getGreen();
/* 138 */     this.z = (byte)paramColor.getBlue();
/* 139 */     this.w = (byte)paramColor.getAlpha();
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
/* 152 */     int i = this.x & 0xFF;
/* 153 */     int j = this.y & 0xFF;
/* 154 */     int k = this.z & 0xFF;
/* 155 */     int m = this.w & 0xFF;
/*     */     
/* 157 */     return new Color(i, j, k, m);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Color4b.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
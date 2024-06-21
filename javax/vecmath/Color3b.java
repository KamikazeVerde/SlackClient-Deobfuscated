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
/*     */ public class Color3b
/*     */   extends Tuple3b
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 6632576088353444794L;
/*     */   
/*     */   public Color3b(byte paramByte1, byte paramByte2, byte paramByte3) {
/*  64 */     super(paramByte1, paramByte2, paramByte3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color3b(byte[] paramArrayOfbyte) {
/*  73 */     super(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color3b(Color3b paramColor3b) {
/*  82 */     super(paramColor3b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color3b(Tuple3b paramTuple3b) {
/*  91 */     super(paramTuple3b);
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
/*     */   public Color3b(Color paramColor) {
/* 107 */     super((byte)paramColor.getRed(), (byte)paramColor.getGreen(), (byte)paramColor.getBlue());
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
/*     */   public Color3b() {}
/*     */ 
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
/* 132 */     this.x = (byte)paramColor.getRed();
/* 133 */     this.y = (byte)paramColor.getGreen();
/* 134 */     this.z = (byte)paramColor.getBlue();
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
/* 147 */     int i = this.x & 0xFF;
/* 148 */     int j = this.y & 0xFF;
/* 149 */     int k = this.z & 0xFF;
/*     */     
/* 151 */     return new Color(i, j, k);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Color3b.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.client.renderer.vertex;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.optifine.render.VboRange;
/*     */ import net.optifine.render.VboRegion;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class VertexBuffer
/*     */ {
/*     */   private int glBufferId;
/*     */   private final VertexFormat vertexFormat;
/*     */   private int count;
/*     */   private VboRegion vboRegion;
/*     */   private VboRange vboRange;
/*     */   private int drawMode;
/*     */   
/*     */   public VertexBuffer(VertexFormat vertexFormatIn) {
/*  20 */     this.vertexFormat = vertexFormatIn;
/*  21 */     this.glBufferId = OpenGlHelper.glGenBuffers();
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindBuffer() {
/*  26 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, this.glBufferId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_181722_a(ByteBuffer p_181722_1_) {
/*  31 */     if (this.vboRegion != null) {
/*     */       
/*  33 */       this.vboRegion.bufferData(p_181722_1_, this.vboRange);
/*     */     }
/*     */     else {
/*     */       
/*  37 */       bindBuffer();
/*  38 */       OpenGlHelper.glBufferData(OpenGlHelper.GL_ARRAY_BUFFER, p_181722_1_, 35044);
/*  39 */       unbindBuffer();
/*  40 */       this.count = p_181722_1_.limit() / this.vertexFormat.getNextOffset();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawArrays(int mode) {
/*  46 */     if (this.drawMode > 0)
/*     */     {
/*  48 */       mode = this.drawMode;
/*     */     }
/*     */     
/*  51 */     if (this.vboRegion != null) {
/*     */       
/*  53 */       this.vboRegion.drawArrays(mode, this.vboRange);
/*     */     }
/*     */     else {
/*     */       
/*  57 */       GL11.glDrawArrays(mode, 0, this.count);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void unbindBuffer() {
/*  63 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteGlBuffers() {
/*  68 */     if (this.glBufferId >= 0) {
/*     */       
/*  70 */       OpenGlHelper.glDeleteBuffers(this.glBufferId);
/*  71 */       this.glBufferId = -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVboRegion(VboRegion p_setVboRegion_1_) {
/*  77 */     if (p_setVboRegion_1_ != null) {
/*     */       
/*  79 */       deleteGlBuffers();
/*  80 */       this.vboRegion = p_setVboRegion_1_;
/*  81 */       this.vboRange = new VboRange();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public VboRegion getVboRegion() {
/*  87 */     return this.vboRegion;
/*     */   }
/*     */ 
/*     */   
/*     */   public VboRange getVboRange() {
/*  92 */     return this.vboRange;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDrawMode() {
/*  97 */     return this.drawMode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDrawMode(int p_setDrawMode_1_) {
/* 102 */     this.drawMode = p_setDrawMode_1_;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\vertex\VertexBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
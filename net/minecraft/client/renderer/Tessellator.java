/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import net.optifine.SmartAnimations;
/*    */ 
/*    */ public class Tessellator
/*    */ {
/*    */   private WorldRenderer worldRenderer;
/*  8 */   private WorldVertexBufferUploader vboUploader = new WorldVertexBufferUploader();
/*    */ 
/*    */   
/* 11 */   private static final Tessellator instance = new Tessellator(2097152);
/*    */ 
/*    */   
/*    */   public static Tessellator getInstance() {
/* 15 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public Tessellator(int bufferSize) {
/* 20 */     this.worldRenderer = new WorldRenderer(bufferSize);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void draw() {
/* 28 */     if (this.worldRenderer.animatedSprites != null)
/*    */     {
/* 30 */       SmartAnimations.spritesRendered(this.worldRenderer.animatedSprites);
/*    */     }
/*    */     
/* 33 */     this.worldRenderer.finishDrawing();
/* 34 */     this.vboUploader.func_181679_a(this.worldRenderer);
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldRenderer getWorldRenderer() {
/* 39 */     return this.worldRenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\Tessellator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
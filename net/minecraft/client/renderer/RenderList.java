/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import java.nio.IntBuffer;
/*    */ import net.minecraft.client.renderer.chunk.ListedRenderChunk;
/*    */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.EnumWorldBlockLayer;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class RenderList
/*    */   extends ChunkRenderContainer {
/*    */   private double viewEntityX;
/*    */   private double viewEntityY;
/*    */   private double viewEntityZ;
/* 15 */   IntBuffer bufferLists = GLAllocation.createDirectIntBuffer(16);
/*    */ 
/*    */   
/*    */   public void renderChunkLayer(EnumWorldBlockLayer layer) {
/* 19 */     if (this.initialized) {
/*    */       
/* 21 */       if (!Config.isRenderRegions()) {
/*    */         
/* 23 */         for (RenderChunk renderchunk1 : this.renderChunks)
/*    */         {
/* 25 */           ListedRenderChunk listedrenderchunk1 = (ListedRenderChunk)renderchunk1;
/* 26 */           GlStateManager.pushMatrix();
/* 27 */           preRenderChunk(renderchunk1);
/* 28 */           GL11.glCallList(listedrenderchunk1.getDisplayList(layer, listedrenderchunk1.getCompiledChunk()));
/* 29 */           GlStateManager.popMatrix();
/*    */         }
/*    */       
/*    */       } else {
/*    */         
/* 34 */         int i = Integer.MIN_VALUE;
/* 35 */         int j = Integer.MIN_VALUE;
/*    */         
/* 37 */         for (RenderChunk renderchunk : this.renderChunks) {
/*    */           
/* 39 */           ListedRenderChunk listedrenderchunk = (ListedRenderChunk)renderchunk;
/*    */           
/* 41 */           if (i != renderchunk.regionX || j != renderchunk.regionZ) {
/*    */             
/* 43 */             if (this.bufferLists.position() > 0)
/*    */             {
/* 45 */               drawRegion(i, j, this.bufferLists);
/*    */             }
/*    */             
/* 48 */             i = renderchunk.regionX;
/* 49 */             j = renderchunk.regionZ;
/*    */           } 
/*    */           
/* 52 */           if (this.bufferLists.position() >= this.bufferLists.capacity()) {
/*    */             
/* 54 */             IntBuffer intbuffer = GLAllocation.createDirectIntBuffer(this.bufferLists.capacity() * 2);
/* 55 */             this.bufferLists.flip();
/* 56 */             intbuffer.put(this.bufferLists);
/* 57 */             this.bufferLists = intbuffer;
/*    */           } 
/*    */           
/* 60 */           this.bufferLists.put(listedrenderchunk.getDisplayList(layer, listedrenderchunk.getCompiledChunk()));
/*    */         } 
/*    */         
/* 63 */         if (this.bufferLists.position() > 0)
/*    */         {
/* 65 */           drawRegion(i, j, this.bufferLists);
/*    */         }
/*    */       } 
/*    */       
/* 69 */       if (Config.isMultiTexture())
/*    */       {
/* 71 */         GlStateManager.bindCurrentTexture();
/*    */       }
/*    */       
/* 74 */       GlStateManager.resetColor();
/* 75 */       this.renderChunks.clear();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void initialize(double viewEntityXIn, double viewEntityYIn, double viewEntityZIn) {
/* 81 */     this.viewEntityX = viewEntityXIn;
/* 82 */     this.viewEntityY = viewEntityYIn;
/* 83 */     this.viewEntityZ = viewEntityZIn;
/* 84 */     super.initialize(viewEntityXIn, viewEntityYIn, viewEntityZIn);
/*    */   }
/*    */ 
/*    */   
/*    */   private void drawRegion(int p_drawRegion_1_, int p_drawRegion_2_, IntBuffer p_drawRegion_3_) {
/* 89 */     GlStateManager.pushMatrix();
/* 90 */     preRenderRegion(p_drawRegion_1_, 0, p_drawRegion_2_);
/* 91 */     p_drawRegion_3_.flip();
/* 92 */     GlStateManager.callLists(p_drawRegion_3_);
/* 93 */     p_drawRegion_3_.clear();
/* 94 */     GlStateManager.popMatrix();
/*    */   }
/*    */ 
/*    */   
/*    */   public void preRenderRegion(int p_preRenderRegion_1_, int p_preRenderRegion_2_, int p_preRenderRegion_3_) {
/* 99 */     GlStateManager.translate((float)(p_preRenderRegion_1_ - this.viewEntityX), (float)(p_preRenderRegion_2_ - this.viewEntityY), (float)(p_preRenderRegion_3_ - this.viewEntityZ));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\RenderList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
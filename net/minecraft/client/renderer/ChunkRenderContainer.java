/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.BitSet;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumWorldBlockLayer;
/*    */ import net.optifine.SmartAnimations;
/*    */ 
/*    */ public abstract class ChunkRenderContainer
/*    */ {
/*    */   private double viewEntityX;
/*    */   private double viewEntityY;
/*    */   private double viewEntityZ;
/* 16 */   protected List<RenderChunk> renderChunks = Lists.newArrayListWithCapacity(17424);
/*    */   protected boolean initialized;
/*    */   private BitSet animatedSpritesRendered;
/* 19 */   private final BitSet animatedSpritesCached = new BitSet();
/*    */ 
/*    */   
/*    */   public void initialize(double viewEntityXIn, double viewEntityYIn, double viewEntityZIn) {
/* 23 */     this.initialized = true;
/* 24 */     this.renderChunks.clear();
/* 25 */     this.viewEntityX = viewEntityXIn;
/* 26 */     this.viewEntityY = viewEntityYIn;
/* 27 */     this.viewEntityZ = viewEntityZIn;
/*    */     
/* 29 */     if (SmartAnimations.isActive()) {
/*    */       
/* 31 */       if (this.animatedSpritesRendered != null) {
/*    */         
/* 33 */         SmartAnimations.spritesRendered(this.animatedSpritesRendered);
/*    */       }
/*    */       else {
/*    */         
/* 37 */         this.animatedSpritesRendered = this.animatedSpritesCached;
/*    */       } 
/*    */       
/* 40 */       this.animatedSpritesRendered.clear();
/*    */     }
/* 42 */     else if (this.animatedSpritesRendered != null) {
/*    */       
/* 44 */       SmartAnimations.spritesRendered(this.animatedSpritesRendered);
/* 45 */       this.animatedSpritesRendered = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void preRenderChunk(RenderChunk renderChunkIn) {
/* 51 */     BlockPos blockpos = renderChunkIn.getPosition();
/* 52 */     GlStateManager.translate((float)(blockpos.getX() - this.viewEntityX), (float)(blockpos.getY() - this.viewEntityY), (float)(blockpos.getZ() - this.viewEntityZ));
/*    */   }
/*    */ 
/*    */   
/*    */   public void addRenderChunk(RenderChunk renderChunkIn, EnumWorldBlockLayer layer) {
/* 57 */     this.renderChunks.add(renderChunkIn);
/*    */     
/* 59 */     if (this.animatedSpritesRendered != null) {
/*    */       
/* 61 */       BitSet bitset = renderChunkIn.compiledChunk.getAnimatedSprites(layer);
/*    */       
/* 63 */       if (bitset != null)
/*    */       {
/* 65 */         this.animatedSpritesRendered.or(bitset);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract void renderChunkLayer(EnumWorldBlockLayer paramEnumWorldBlockLayer);
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\ChunkRenderContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
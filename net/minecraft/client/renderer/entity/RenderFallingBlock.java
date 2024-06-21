/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.client.resources.model.IBakedModel;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityFallingBlock;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class RenderFallingBlock extends Render<EntityFallingBlock> {
/*    */   public RenderFallingBlock(RenderManager renderManagerIn) {
/* 22 */     super(renderManagerIn);
/* 23 */     this.shadowSize = 0.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityFallingBlock entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 36 */     if (entity.getBlock() != null) {
/*    */       
/* 38 */       bindTexture(TextureMap.locationBlocksTexture);
/* 39 */       IBlockState iblockstate = entity.getBlock();
/* 40 */       Block block = iblockstate.getBlock();
/* 41 */       BlockPos blockpos = new BlockPos((Entity)entity);
/* 42 */       World world = entity.getWorldObj();
/*    */       
/* 44 */       if (iblockstate != world.getBlockState(blockpos) && block.getRenderType() != -1)
/*    */       {
/* 46 */         if (block.getRenderType() == 3) {
/*    */           
/* 48 */           GlStateManager.pushMatrix();
/* 49 */           GlStateManager.translate((float)x, (float)y, (float)z);
/* 50 */           GlStateManager.disableLighting();
/* 51 */           Tessellator tessellator = Tessellator.getInstance();
/* 52 */           WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 53 */           worldrenderer.begin(7, DefaultVertexFormats.BLOCK);
/* 54 */           int i = blockpos.getX();
/* 55 */           int j = blockpos.getY();
/* 56 */           int k = blockpos.getZ();
/* 57 */           worldrenderer.setTranslation((-i - 0.5F), -j, (-k - 0.5F));
/* 58 */           BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 59 */           IBakedModel ibakedmodel = blockrendererdispatcher.getModelFromBlockState(iblockstate, (IBlockAccess)world, null);
/* 60 */           blockrendererdispatcher.getBlockModelRenderer().renderModel((IBlockAccess)world, ibakedmodel, iblockstate, blockpos, worldrenderer, false);
/* 61 */           worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 62 */           tessellator.draw();
/* 63 */           GlStateManager.enableLighting();
/* 64 */           GlStateManager.popMatrix();
/* 65 */           super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityFallingBlock entity) {
/* 76 */     return TextureMap.locationBlocksTexture;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\RenderFallingBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
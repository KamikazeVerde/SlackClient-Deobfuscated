/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityTNTPrimed;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderTNTPrimed
/*    */   extends Render<EntityTNTPrimed> {
/*    */   public RenderTNTPrimed(RenderManager renderManagerIn) {
/* 16 */     super(renderManagerIn);
/* 17 */     this.shadowSize = 0.5F;
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
/*    */   public void doRender(EntityTNTPrimed entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 30 */     BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 31 */     GlStateManager.pushMatrix();
/* 32 */     GlStateManager.translate((float)x, (float)y + 0.5F, (float)z);
/*    */     
/* 34 */     if (entity.fuse - partialTicks + 1.0F < 10.0F) {
/*    */       
/* 36 */       float f = 1.0F - (entity.fuse - partialTicks + 1.0F) / 10.0F;
/* 37 */       f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 38 */       f *= f;
/* 39 */       f *= f;
/* 40 */       float f1 = 1.0F + f * 0.3F;
/* 41 */       GlStateManager.scale(f1, f1, f1);
/*    */     } 
/*    */     
/* 44 */     float f2 = (1.0F - (entity.fuse - partialTicks + 1.0F) / 100.0F) * 0.8F;
/* 45 */     bindEntityTexture(entity);
/* 46 */     GlStateManager.translate(-0.5F, -0.5F, 0.5F);
/* 47 */     blockrendererdispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), entity.getBrightness(partialTicks));
/* 48 */     GlStateManager.translate(0.0F, 0.0F, 1.0F);
/*    */     
/* 50 */     if (entity.fuse / 5 % 2 == 0) {
/*    */       
/* 52 */       GlStateManager.disableTexture2D();
/* 53 */       GlStateManager.disableLighting();
/* 54 */       GlStateManager.enableBlend();
/* 55 */       GlStateManager.blendFunc(770, 772);
/* 56 */       GlStateManager.color(1.0F, 1.0F, 1.0F, f2);
/* 57 */       GlStateManager.doPolygonOffset(-3.0F, -3.0F);
/* 58 */       GlStateManager.enablePolygonOffset();
/* 59 */       blockrendererdispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), 1.0F);
/* 60 */       GlStateManager.doPolygonOffset(0.0F, 0.0F);
/* 61 */       GlStateManager.disablePolygonOffset();
/* 62 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 63 */       GlStateManager.disableBlend();
/* 64 */       GlStateManager.enableLighting();
/* 65 */       GlStateManager.enableTexture2D();
/*    */     } 
/*    */     
/* 68 */     GlStateManager.popMatrix();
/* 69 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityTNTPrimed entity) {
/* 77 */     return TextureMap.locationBlocksTexture;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\RenderTNTPrimed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
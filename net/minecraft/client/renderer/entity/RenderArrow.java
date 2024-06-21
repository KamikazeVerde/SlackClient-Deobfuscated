/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.projectile.EntityArrow;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class RenderArrow extends Render<EntityArrow> {
/* 14 */   private static final ResourceLocation arrowTextures = new ResourceLocation("textures/entity/arrow.png");
/*    */ 
/*    */   
/*    */   public RenderArrow(RenderManager renderManagerIn) {
/* 18 */     super(renderManagerIn);
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
/*    */   public void doRender(EntityArrow entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 31 */     bindEntityTexture(entity);
/* 32 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 33 */     GlStateManager.pushMatrix();
/* 34 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 35 */     GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
/* 36 */     GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
/* 37 */     Tessellator tessellator = Tessellator.getInstance();
/* 38 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 39 */     int i = 0;
/* 40 */     float f = 0.0F;
/* 41 */     float f1 = 0.5F;
/* 42 */     float f2 = (0 + i * 10) / 32.0F;
/* 43 */     float f3 = (5 + i * 10) / 32.0F;
/* 44 */     float f4 = 0.0F;
/* 45 */     float f5 = 0.15625F;
/* 46 */     float f6 = (5 + i * 10) / 32.0F;
/* 47 */     float f7 = (10 + i * 10) / 32.0F;
/* 48 */     float f8 = 0.05625F;
/* 49 */     GlStateManager.enableRescaleNormal();
/* 50 */     float f9 = entity.arrowShake - partialTicks;
/*    */     
/* 52 */     if (f9 > 0.0F) {
/*    */       
/* 54 */       float f10 = -MathHelper.sin(f9 * 3.0F) * f9;
/* 55 */       GlStateManager.rotate(f10, 0.0F, 0.0F, 1.0F);
/*    */     } 
/*    */     
/* 58 */     GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
/* 59 */     GlStateManager.scale(f8, f8, f8);
/* 60 */     GlStateManager.translate(-4.0F, 0.0F, 0.0F);
/* 61 */     GL11.glNormal3f(f8, 0.0F, 0.0F);
/* 62 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 63 */     worldrenderer.pos(-7.0D, -2.0D, -2.0D).tex(f4, f6).endVertex();
/* 64 */     worldrenderer.pos(-7.0D, -2.0D, 2.0D).tex(f5, f6).endVertex();
/* 65 */     worldrenderer.pos(-7.0D, 2.0D, 2.0D).tex(f5, f7).endVertex();
/* 66 */     worldrenderer.pos(-7.0D, 2.0D, -2.0D).tex(f4, f7).endVertex();
/* 67 */     tessellator.draw();
/* 68 */     GL11.glNormal3f(-f8, 0.0F, 0.0F);
/* 69 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 70 */     worldrenderer.pos(-7.0D, 2.0D, -2.0D).tex(f4, f6).endVertex();
/* 71 */     worldrenderer.pos(-7.0D, 2.0D, 2.0D).tex(f5, f6).endVertex();
/* 72 */     worldrenderer.pos(-7.0D, -2.0D, 2.0D).tex(f5, f7).endVertex();
/* 73 */     worldrenderer.pos(-7.0D, -2.0D, -2.0D).tex(f4, f7).endVertex();
/* 74 */     tessellator.draw();
/*    */     
/* 76 */     for (int j = 0; j < 4; j++) {
/*    */       
/* 78 */       GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 79 */       GL11.glNormal3f(0.0F, 0.0F, f8);
/* 80 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 81 */       worldrenderer.pos(-8.0D, -2.0D, 0.0D).tex(f, f2).endVertex();
/* 82 */       worldrenderer.pos(8.0D, -2.0D, 0.0D).tex(f1, f2).endVertex();
/* 83 */       worldrenderer.pos(8.0D, 2.0D, 0.0D).tex(f1, f3).endVertex();
/* 84 */       worldrenderer.pos(-8.0D, 2.0D, 0.0D).tex(f, f3).endVertex();
/* 85 */       tessellator.draw();
/*    */     } 
/*    */     
/* 88 */     GlStateManager.disableRescaleNormal();
/* 89 */     GlStateManager.popMatrix();
/* 90 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityArrow entity) {
/* 98 */     return arrowTextures;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\RenderArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
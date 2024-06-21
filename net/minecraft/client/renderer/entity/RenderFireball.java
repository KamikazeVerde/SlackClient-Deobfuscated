/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.projectile.EntityFireball;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderFireball
/*    */   extends Render<EntityFireball> {
/*    */   private float scale;
/*    */   
/*    */   public RenderFireball(RenderManager renderManagerIn, float scaleIn) {
/* 20 */     super(renderManagerIn);
/* 21 */     this.scale = scaleIn;
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
/*    */   public void doRender(EntityFireball entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 34 */     GlStateManager.pushMatrix();
/* 35 */     bindEntityTexture(entity);
/* 36 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 37 */     GlStateManager.enableRescaleNormal();
/* 38 */     GlStateManager.scale(this.scale, this.scale, this.scale);
/* 39 */     TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(Items.fire_charge);
/* 40 */     Tessellator tessellator = Tessellator.getInstance();
/* 41 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 42 */     float f = textureatlassprite.getMinU();
/* 43 */     float f1 = textureatlassprite.getMaxU();
/* 44 */     float f2 = textureatlassprite.getMinV();
/* 45 */     float f3 = textureatlassprite.getMaxV();
/* 46 */     float f4 = 1.0F;
/* 47 */     float f5 = 0.5F;
/* 48 */     float f6 = 0.25F;
/* 49 */     GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 50 */     GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 51 */     worldrenderer.begin(7, DefaultVertexFormats.field_181710_j);
/* 52 */     worldrenderer.pos(-0.5D, -0.25D, 0.0D).tex(f, f3).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/* 53 */     worldrenderer.pos(0.5D, -0.25D, 0.0D).tex(f1, f3).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/* 54 */     worldrenderer.pos(0.5D, 0.75D, 0.0D).tex(f1, f2).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/* 55 */     worldrenderer.pos(-0.5D, 0.75D, 0.0D).tex(f, f2).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/* 56 */     tessellator.draw();
/* 57 */     GlStateManager.disableRescaleNormal();
/* 58 */     GlStateManager.popMatrix();
/* 59 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityFireball entity) {
/* 67 */     return TextureMap.locationBlocksTexture;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\RenderFireball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
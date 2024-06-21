/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class LayerCape
/*    */   implements LayerRenderer<AbstractClientPlayer> {
/*    */   private final RenderPlayer playerRenderer;
/*    */   
/*    */   public LayerCape(RenderPlayer playerRendererIn) {
/* 15 */     this.playerRenderer = playerRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 20 */     if (entitylivingbaseIn.hasPlayerInfo() && !entitylivingbaseIn.isInvisible() && entitylivingbaseIn.isWearing(EnumPlayerModelParts.CAPE) && entitylivingbaseIn.getLocationCape() != null) {
/*    */       
/* 22 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 23 */       this.playerRenderer.bindTexture(entitylivingbaseIn.getLocationCape());
/* 24 */       GlStateManager.pushMatrix();
/* 25 */       GlStateManager.translate(0.0F, 0.0F, 0.125F);
/* 26 */       double d0 = entitylivingbaseIn.prevChasingPosX + (entitylivingbaseIn.chasingPosX - entitylivingbaseIn.prevChasingPosX) * partialTicks - entitylivingbaseIn.prevPosX + (entitylivingbaseIn.posX - entitylivingbaseIn.prevPosX) * partialTicks;
/* 27 */       double d1 = entitylivingbaseIn.prevChasingPosY + (entitylivingbaseIn.chasingPosY - entitylivingbaseIn.prevChasingPosY) * partialTicks - entitylivingbaseIn.prevPosY + (entitylivingbaseIn.posY - entitylivingbaseIn.prevPosY) * partialTicks;
/* 28 */       double d2 = entitylivingbaseIn.prevChasingPosZ + (entitylivingbaseIn.chasingPosZ - entitylivingbaseIn.prevChasingPosZ) * partialTicks - entitylivingbaseIn.prevPosZ + (entitylivingbaseIn.posZ - entitylivingbaseIn.prevPosZ) * partialTicks;
/* 29 */       float f = entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks;
/* 30 */       double d3 = MathHelper.sin(f * 3.1415927F / 180.0F);
/* 31 */       double d4 = -MathHelper.cos(f * 3.1415927F / 180.0F);
/* 32 */       float f1 = (float)d1 * 10.0F;
/* 33 */       f1 = MathHelper.clamp_float(f1, -6.0F, 32.0F);
/* 34 */       float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
/* 35 */       float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
/*    */       
/* 37 */       if (f2 < 0.0F)
/*    */       {
/* 39 */         f2 = 0.0F;
/*    */       }
/*    */       
/* 42 */       if (f2 > 165.0F)
/*    */       {
/* 44 */         f2 = 165.0F;
/*    */       }
/*    */       
/* 47 */       if (f1 < -5.0F)
/*    */       {
/* 49 */         f1 = -5.0F;
/*    */       }
/*    */       
/* 52 */       float f4 = entitylivingbaseIn.prevCameraYaw + (entitylivingbaseIn.cameraYaw - entitylivingbaseIn.prevCameraYaw) * partialTicks;
/* 53 */       f1 += MathHelper.sin((entitylivingbaseIn.prevDistanceWalkedModified + (entitylivingbaseIn.distanceWalkedModified - entitylivingbaseIn.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;
/*    */       
/* 55 */       if (entitylivingbaseIn.isSneaking()) {
/*    */         
/* 57 */         f1 += 25.0F;
/* 58 */         GlStateManager.translate(0.0F, 0.142F, -0.0178F);
/*    */       } 
/*    */       
/* 61 */       GlStateManager.rotate(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
/* 62 */       GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
/* 63 */       GlStateManager.rotate(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
/* 64 */       GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/* 65 */       this.playerRenderer.getMainModel().renderCape(0.0625F);
/* 66 */       GlStateManager.popMatrix();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 72 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\layers\LayerCape.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
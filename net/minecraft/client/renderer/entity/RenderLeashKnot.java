/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelLeashKnot;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderLeashKnot extends Render<EntityLeashKnot> {
/* 10 */   private static final ResourceLocation leashKnotTextures = new ResourceLocation("textures/entity/lead_knot.png");
/* 11 */   private ModelLeashKnot leashKnotModel = new ModelLeashKnot();
/*    */ 
/*    */   
/*    */   public RenderLeashKnot(RenderManager renderManagerIn) {
/* 15 */     super(renderManagerIn);
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
/*    */   public void doRender(EntityLeashKnot entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 28 */     GlStateManager.pushMatrix();
/* 29 */     GlStateManager.disableCull();
/* 30 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 31 */     float f = 0.0625F;
/* 32 */     GlStateManager.enableRescaleNormal();
/* 33 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 34 */     GlStateManager.enableAlpha();
/* 35 */     bindEntityTexture(entity);
/* 36 */     this.leashKnotModel.render((Entity)entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, f);
/* 37 */     GlStateManager.popMatrix();
/* 38 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityLeashKnot entity) {
/* 46 */     return leashKnotTextures;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\RenderLeashKnot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
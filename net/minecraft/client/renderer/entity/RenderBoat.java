/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBoat;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityBoat;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderBoat extends Render<EntityBoat> {
/* 12 */   private static final ResourceLocation boatTextures = new ResourceLocation("textures/entity/boat.png");
/*    */ 
/*    */   
/* 15 */   protected ModelBase modelBoat = (ModelBase)new ModelBoat();
/*    */ 
/*    */   
/*    */   public RenderBoat(RenderManager renderManagerIn) {
/* 19 */     super(renderManagerIn);
/* 20 */     this.shadowSize = 0.5F;
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
/*    */   public void doRender(EntityBoat entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 33 */     GlStateManager.pushMatrix();
/* 34 */     GlStateManager.translate((float)x, (float)y + 0.25F, (float)z);
/* 35 */     GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
/* 36 */     float f = entity.getTimeSinceHit() - partialTicks;
/* 37 */     float f1 = entity.getDamageTaken() - partialTicks;
/*    */     
/* 39 */     if (f1 < 0.0F)
/*    */     {
/* 41 */       f1 = 0.0F;
/*    */     }
/*    */     
/* 44 */     if (f > 0.0F)
/*    */     {
/* 46 */       GlStateManager.rotate(MathHelper.sin(f) * f * f1 / 10.0F * entity.getForwardDirection(), 1.0F, 0.0F, 0.0F);
/*    */     }
/*    */     
/* 49 */     float f2 = 0.75F;
/* 50 */     GlStateManager.scale(f2, f2, f2);
/* 51 */     GlStateManager.scale(1.0F / f2, 1.0F / f2, 1.0F / f2);
/* 52 */     bindEntityTexture(entity);
/* 53 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 54 */     this.modelBoat.render((Entity)entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
/* 55 */     GlStateManager.popMatrix();
/* 56 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityBoat entity) {
/* 64 */     return boatTextures;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\RenderBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
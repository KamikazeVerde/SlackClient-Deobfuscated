/*    */ package net.minecraft.client.renderer.tileentity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelEnderCrystal;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderEnderCrystal extends Render<EntityEnderCrystal> {
/* 14 */   private static final ResourceLocation enderCrystalTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
/* 15 */   private ModelBase modelEnderCrystal = (ModelBase)new ModelEnderCrystal(0.0F, true);
/*    */ 
/*    */   
/*    */   public RenderEnderCrystal(RenderManager renderManagerIn) {
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
/*    */   public void doRender(EntityEnderCrystal entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 33 */     float f = entity.innerRotation + partialTicks;
/* 34 */     GlStateManager.pushMatrix();
/* 35 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 36 */     bindTexture(enderCrystalTextures);
/* 37 */     float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
/* 38 */     f1 = f1 * f1 + f1;
/* 39 */     this.modelEnderCrystal.render((Entity)entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
/* 40 */     GlStateManager.popMatrix();
/* 41 */     super.doRender((Entity)entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityEnderCrystal entity) {
/* 49 */     return enderCrystalTextures;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\tileentity\RenderEnderCrystal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
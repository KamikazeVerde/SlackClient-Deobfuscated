/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityFootStepFX
/*    */   extends EntityFX {
/* 16 */   private static final ResourceLocation FOOTPRINT_TEXTURE = new ResourceLocation("textures/particle/footprint.png");
/*    */   
/*    */   private int footstepAge;
/*    */   private int footstepMaxAge;
/*    */   private TextureManager currentFootSteps;
/*    */   
/*    */   protected EntityFootStepFX(TextureManager currentFootStepsIn, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn) {
/* 23 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 24 */     this.currentFootSteps = currentFootStepsIn;
/* 25 */     this.motionX = this.motionY = this.motionZ = 0.0D;
/* 26 */     this.footstepMaxAge = 200;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
/* 36 */     float f = (this.footstepAge + partialTicks) / this.footstepMaxAge;
/* 37 */     f *= f;
/* 38 */     float f1 = 2.0F - f * 2.0F;
/*    */     
/* 40 */     if (f1 > 1.0F)
/*    */     {
/* 42 */       f1 = 1.0F;
/*    */     }
/*    */     
/* 45 */     f1 *= 0.2F;
/* 46 */     GlStateManager.disableLighting();
/* 47 */     float f2 = 0.125F;
/* 48 */     float f3 = (float)(this.posX - interpPosX);
/* 49 */     float f4 = (float)(this.posY - interpPosY);
/* 50 */     float f5 = (float)(this.posZ - interpPosZ);
/* 51 */     float f6 = this.worldObj.getLightBrightness(new BlockPos(this));
/* 52 */     this.currentFootSteps.bindTexture(FOOTPRINT_TEXTURE);
/* 53 */     GlStateManager.enableBlend();
/* 54 */     GlStateManager.blendFunc(770, 771);
/* 55 */     worldRendererIn.begin(7, DefaultVertexFormats.field_181709_i);
/* 56 */     worldRendererIn.pos((f3 - 0.125F), f4, (f5 + 0.125F)).tex(0.0D, 1.0D).func_181666_a(f6, f6, f6, f1).endVertex();
/* 57 */     worldRendererIn.pos((f3 + 0.125F), f4, (f5 + 0.125F)).tex(1.0D, 1.0D).func_181666_a(f6, f6, f6, f1).endVertex();
/* 58 */     worldRendererIn.pos((f3 + 0.125F), f4, (f5 - 0.125F)).tex(1.0D, 0.0D).func_181666_a(f6, f6, f6, f1).endVertex();
/* 59 */     worldRendererIn.pos((f3 - 0.125F), f4, (f5 - 0.125F)).tex(0.0D, 0.0D).func_181666_a(f6, f6, f6, f1).endVertex();
/* 60 */     Tessellator.getInstance().draw();
/* 61 */     GlStateManager.disableBlend();
/* 62 */     GlStateManager.enableLighting();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 70 */     this.footstepAge++;
/*    */     
/* 72 */     if (this.footstepAge == this.footstepMaxAge)
/*    */     {
/* 74 */       setDead();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFXLayer() {
/* 80 */     return 3;
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 87 */       return new EntityFootStepFX(Minecraft.getMinecraft().getTextureManager(), worldIn, xCoordIn, yCoordIn, zCoordIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\particle\EntityFootStepFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
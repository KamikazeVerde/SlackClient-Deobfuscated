/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityLargeExplodeFX
/*    */   extends EntityFX {
/* 17 */   private static final ResourceLocation EXPLOSION_TEXTURE = new ResourceLocation("textures/entity/explosion.png");
/* 18 */   private static final VertexFormat field_181549_az = (new VertexFormat()).func_181721_a(DefaultVertexFormats.field_181713_m).func_181721_a(DefaultVertexFormats.field_181715_o).func_181721_a(DefaultVertexFormats.field_181714_n).func_181721_a(DefaultVertexFormats.field_181716_p).func_181721_a(DefaultVertexFormats.field_181717_q).func_181721_a(DefaultVertexFormats.field_181718_r);
/*    */   
/*    */   private int field_70581_a;
/*    */   
/*    */   private int field_70584_aq;
/*    */   
/*    */   private TextureManager theRenderEngine;
/*    */   private float field_70582_as;
/*    */   
/*    */   protected EntityLargeExplodeFX(TextureManager renderEngine, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i1213_9_, double p_i1213_11_, double p_i1213_13_) {
/* 28 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 29 */     this.theRenderEngine = renderEngine;
/* 30 */     this.field_70584_aq = 6 + this.rand.nextInt(4);
/* 31 */     this.particleRed = this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.6F + 0.4F;
/* 32 */     this.field_70582_as = 1.0F - (float)p_i1213_9_ * 0.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
/* 42 */     int i = (int)((this.field_70581_a + partialTicks) * 15.0F / this.field_70584_aq);
/*    */     
/* 44 */     if (i <= 15) {
/*    */       
/* 46 */       this.theRenderEngine.bindTexture(EXPLOSION_TEXTURE);
/* 47 */       float f = (i % 4) / 4.0F;
/* 48 */       float f1 = f + 0.24975F;
/* 49 */       float f2 = (i / 4) / 4.0F;
/* 50 */       float f3 = f2 + 0.24975F;
/* 51 */       float f4 = 2.0F * this.field_70582_as;
/* 52 */       float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/* 53 */       float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/* 54 */       float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/* 55 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 56 */       GlStateManager.disableLighting();
/* 57 */       RenderHelper.disableStandardItemLighting();
/* 58 */       worldRendererIn.begin(7, field_181549_az);
/* 59 */       worldRendererIn.pos((f5 - p_180434_4_ * f4 - p_180434_7_ * f4), (f6 - p_180434_5_ * f4), (f7 - p_180434_6_ * f4 - p_180434_8_ * f4)).tex(f1, f3).func_181666_a(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).func_181671_a(0, 240).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/* 60 */       worldRendererIn.pos((f5 - p_180434_4_ * f4 + p_180434_7_ * f4), (f6 + p_180434_5_ * f4), (f7 - p_180434_6_ * f4 + p_180434_8_ * f4)).tex(f1, f2).func_181666_a(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).func_181671_a(0, 240).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/* 61 */       worldRendererIn.pos((f5 + p_180434_4_ * f4 + p_180434_7_ * f4), (f6 + p_180434_5_ * f4), (f7 + p_180434_6_ * f4 + p_180434_8_ * f4)).tex(f, f2).func_181666_a(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).func_181671_a(0, 240).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/* 62 */       worldRendererIn.pos((f5 + p_180434_4_ * f4 - p_180434_7_ * f4), (f6 - p_180434_5_ * f4), (f7 + p_180434_6_ * f4 - p_180434_8_ * f4)).tex(f, f3).func_181666_a(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).func_181671_a(0, 240).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/* 63 */       Tessellator.getInstance().draw();
/* 64 */       GlStateManager.enableLighting();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBrightnessForRender(float partialTicks) {
/* 70 */     return 61680;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 78 */     this.prevPosX = this.posX;
/* 79 */     this.prevPosY = this.posY;
/* 80 */     this.prevPosZ = this.posZ;
/* 81 */     this.field_70581_a++;
/*    */     
/* 83 */     if (this.field_70581_a == this.field_70584_aq)
/*    */     {
/* 85 */       setDead();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFXLayer() {
/* 91 */     return 3;
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 98 */       return new EntityLargeExplodeFX(Minecraft.getMinecraft().getTextureManager(), worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\particle\EntityLargeExplodeFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityPortalFX
/*     */   extends EntityFX
/*     */ {
/*     */   private float portalParticleScale;
/*     */   private double portalPosX;
/*     */   private double portalPosY;
/*     */   private double portalPosZ;
/*     */   
/*     */   protected EntityPortalFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
/*  16 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*  17 */     this.motionX = xSpeedIn;
/*  18 */     this.motionY = ySpeedIn;
/*  19 */     this.motionZ = zSpeedIn;
/*  20 */     this.portalPosX = this.posX = xCoordIn;
/*  21 */     this.portalPosY = this.posY = yCoordIn;
/*  22 */     this.portalPosZ = this.posZ = zCoordIn;
/*  23 */     float f = this.rand.nextFloat() * 0.6F + 0.4F;
/*  24 */     this.portalParticleScale = this.particleScale = this.rand.nextFloat() * 0.2F + 0.5F;
/*  25 */     this.particleRed = this.particleGreen = this.particleBlue = 1.0F * f;
/*  26 */     this.particleGreen *= 0.3F;
/*  27 */     this.particleRed *= 0.9F;
/*  28 */     this.particleMaxAge = (int)(Math.random() * 10.0D) + 40;
/*  29 */     this.noClip = true;
/*  30 */     setParticleTextureIndex((int)(Math.random() * 8.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
/*  40 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge;
/*  41 */     f = 1.0F - f;
/*  42 */     f *= f;
/*  43 */     f = 1.0F - f;
/*  44 */     this.particleScale = this.portalParticleScale * f;
/*  45 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender(float partialTicks) {
/*  50 */     int i = super.getBrightnessForRender(partialTicks);
/*  51 */     float f = this.particleAge / this.particleMaxAge;
/*  52 */     f *= f;
/*  53 */     f *= f;
/*  54 */     int j = i & 0xFF;
/*  55 */     int k = i >> 16 & 0xFF;
/*  56 */     k += (int)(f * 15.0F * 16.0F);
/*     */     
/*  58 */     if (k > 240)
/*     */     {
/*  60 */       k = 240;
/*     */     }
/*     */     
/*  63 */     return j | k << 16;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBrightness(float partialTicks) {
/*  71 */     float f = super.getBrightness(partialTicks);
/*  72 */     float f1 = this.particleAge / this.particleMaxAge;
/*  73 */     f1 = f1 * f1 * f1 * f1;
/*  74 */     return f * (1.0F - f1) + f1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  82 */     this.prevPosX = this.posX;
/*  83 */     this.prevPosY = this.posY;
/*  84 */     this.prevPosZ = this.posZ;
/*  85 */     float f = this.particleAge / this.particleMaxAge;
/*  86 */     f = -f + f * f * 2.0F;
/*  87 */     f = 1.0F - f;
/*  88 */     this.posX = this.portalPosX + this.motionX * f;
/*  89 */     this.posY = this.portalPosY + this.motionY * f + (1.0F - f);
/*  90 */     this.posZ = this.portalPosZ + this.motionZ * f;
/*     */     
/*  92 */     if (this.particleAge++ >= this.particleMaxAge)
/*     */     {
/*  94 */       setDead();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Factory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 102 */       return new EntityPortalFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\particle\EntityPortalFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
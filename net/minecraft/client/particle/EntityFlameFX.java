/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class EntityFlameFX
/*    */   extends EntityFX
/*    */ {
/*    */   private float flameScale;
/*    */   
/*    */   protected EntityFlameFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
/* 15 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 16 */     this.motionX = this.motionX * 0.009999999776482582D + xSpeedIn;
/* 17 */     this.motionY = this.motionY * 0.009999999776482582D + ySpeedIn;
/* 18 */     this.motionZ = this.motionZ * 0.009999999776482582D + zSpeedIn;
/* 19 */     this.posX += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
/* 20 */     this.posY += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
/* 21 */     this.posZ += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
/* 22 */     this.flameScale = this.particleScale;
/* 23 */     this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
/* 24 */     this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
/* 25 */     this.noClip = true;
/* 26 */     setParticleTextureIndex(48);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
/* 36 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge;
/* 37 */     this.particleScale = this.flameScale * (1.0F - f * f * 0.5F);
/* 38 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBrightnessForRender(float partialTicks) {
/* 43 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge;
/* 44 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 45 */     int i = super.getBrightnessForRender(partialTicks);
/* 46 */     int j = i & 0xFF;
/* 47 */     int k = i >> 16 & 0xFF;
/* 48 */     j += (int)(f * 15.0F * 16.0F);
/*    */     
/* 50 */     if (j > 240)
/*    */     {
/* 52 */       j = 240;
/*    */     }
/*    */     
/* 55 */     return j | k << 16;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getBrightness(float partialTicks) {
/* 63 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge;
/* 64 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 65 */     float f1 = super.getBrightness(partialTicks);
/* 66 */     return f1 * f + 1.0F - f;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 74 */     this.prevPosX = this.posX;
/* 75 */     this.prevPosY = this.posY;
/* 76 */     this.prevPosZ = this.posZ;
/*    */     
/* 78 */     if (this.particleAge++ >= this.particleMaxAge)
/*    */     {
/* 80 */       setDead();
/*    */     }
/*    */     
/* 83 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 84 */     this.motionX *= 0.9599999785423279D;
/* 85 */     this.motionY *= 0.9599999785423279D;
/* 86 */     this.motionZ *= 0.9599999785423279D;
/*    */     
/* 88 */     if (this.onGround) {
/*    */       
/* 90 */       this.motionX *= 0.699999988079071D;
/* 91 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 99 */       return new EntityFlameFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\particle\EntityFlameFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
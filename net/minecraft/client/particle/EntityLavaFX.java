/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityLavaFX
/*    */   extends EntityFX
/*    */ {
/*    */   private float lavaParticleScale;
/*    */   
/*    */   protected EntityLavaFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn) {
/* 15 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 16 */     this.motionX *= 0.800000011920929D;
/* 17 */     this.motionY *= 0.800000011920929D;
/* 18 */     this.motionZ *= 0.800000011920929D;
/* 19 */     this.motionY = (this.rand.nextFloat() * 0.4F + 0.05F);
/* 20 */     this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
/* 21 */     this.particleScale *= this.rand.nextFloat() * 2.0F + 0.2F;
/* 22 */     this.lavaParticleScale = this.particleScale;
/* 23 */     this.particleMaxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
/* 24 */     this.noClip = false;
/* 25 */     setParticleTextureIndex(49);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBrightnessForRender(float partialTicks) {
/* 30 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge;
/* 31 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 32 */     int i = super.getBrightnessForRender(partialTicks);
/* 33 */     int j = 240;
/* 34 */     int k = i >> 16 & 0xFF;
/* 35 */     return j | k << 16;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getBrightness(float partialTicks) {
/* 43 */     return 1.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
/* 53 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge;
/* 54 */     this.particleScale = this.lavaParticleScale * (1.0F - f * f);
/* 55 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 63 */     this.prevPosX = this.posX;
/* 64 */     this.prevPosY = this.posY;
/* 65 */     this.prevPosZ = this.posZ;
/*    */     
/* 67 */     if (this.particleAge++ >= this.particleMaxAge)
/*    */     {
/* 69 */       setDead();
/*    */     }
/*    */     
/* 72 */     float f = this.particleAge / this.particleMaxAge;
/*    */     
/* 74 */     if (this.rand.nextFloat() > f)
/*    */     {
/* 76 */       this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, new int[0]);
/*    */     }
/*    */     
/* 79 */     this.motionY -= 0.03D;
/* 80 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 81 */     this.motionX *= 0.9990000128746033D;
/* 82 */     this.motionY *= 0.9990000128746033D;
/* 83 */     this.motionZ *= 0.9990000128746033D;
/*    */     
/* 85 */     if (this.onGround) {
/*    */       
/* 87 */       this.motionX *= 0.699999988079071D;
/* 88 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 96 */       return new EntityLavaFX(worldIn, xCoordIn, yCoordIn, zCoordIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\particle\EntityLavaFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
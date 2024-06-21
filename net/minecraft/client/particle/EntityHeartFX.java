/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityHeartFX
/*    */   extends EntityFX
/*    */ {
/*    */   float particleScaleOverTime;
/*    */   
/*    */   protected EntityHeartFX(World worldIn, double p_i1211_2_, double p_i1211_4_, double p_i1211_6_, double p_i1211_8_, double p_i1211_10_, double p_i1211_12_) {
/* 14 */     this(worldIn, p_i1211_2_, p_i1211_4_, p_i1211_6_, p_i1211_8_, p_i1211_10_, p_i1211_12_, 2.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected EntityHeartFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46354_8_, double p_i46354_10_, double p_i46354_12_, float scale) {
/* 19 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 20 */     this.motionX *= 0.009999999776482582D;
/* 21 */     this.motionY *= 0.009999999776482582D;
/* 22 */     this.motionZ *= 0.009999999776482582D;
/* 23 */     this.motionY += 0.1D;
/* 24 */     this.particleScale *= 0.75F;
/* 25 */     this.particleScale *= scale;
/* 26 */     this.particleScaleOverTime = this.particleScale;
/* 27 */     this.particleMaxAge = 16;
/* 28 */     this.noClip = false;
/* 29 */     setParticleTextureIndex(80);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
/* 39 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
/* 40 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 41 */     this.particleScale = this.particleScaleOverTime * f;
/* 42 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 50 */     this.prevPosX = this.posX;
/* 51 */     this.prevPosY = this.posY;
/* 52 */     this.prevPosZ = this.posZ;
/*    */     
/* 54 */     if (this.particleAge++ >= this.particleMaxAge)
/*    */     {
/* 56 */       setDead();
/*    */     }
/*    */     
/* 59 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*    */     
/* 61 */     if (this.posY == this.prevPosY) {
/*    */       
/* 63 */       this.motionX *= 1.1D;
/* 64 */       this.motionZ *= 1.1D;
/*    */     } 
/*    */     
/* 67 */     this.motionX *= 0.8600000143051147D;
/* 68 */     this.motionY *= 0.8600000143051147D;
/* 69 */     this.motionZ *= 0.8600000143051147D;
/*    */     
/* 71 */     if (this.onGround) {
/*    */       
/* 73 */       this.motionX *= 0.699999988079071D;
/* 74 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class AngryVillagerFactory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 82 */       EntityFX entityfx = new EntityHeartFX(worldIn, xCoordIn, yCoordIn + 0.5D, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 83 */       entityfx.setParticleTextureIndex(81);
/* 84 */       entityfx.setRBGColorF(1.0F, 1.0F, 1.0F);
/* 85 */       return entityfx;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 93 */       return new EntityHeartFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\particle\EntityHeartFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
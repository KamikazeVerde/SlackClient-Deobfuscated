/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityCrit2FX
/*    */   extends EntityFX
/*    */ {
/*    */   float field_174839_a;
/*    */   
/*    */   protected EntityCrit2FX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46284_8_, double p_i46284_10_, double p_i46284_12_) {
/* 14 */     this(worldIn, xCoordIn, yCoordIn, zCoordIn, p_i46284_8_, p_i46284_10_, p_i46284_12_, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected EntityCrit2FX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46285_8_, double p_i46285_10_, double p_i46285_12_, float p_i46285_14_) {
/* 19 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 20 */     this.motionX *= 0.10000000149011612D;
/* 21 */     this.motionY *= 0.10000000149011612D;
/* 22 */     this.motionZ *= 0.10000000149011612D;
/* 23 */     this.motionX += p_i46285_8_ * 0.4D;
/* 24 */     this.motionY += p_i46285_10_ * 0.4D;
/* 25 */     this.motionZ += p_i46285_12_ * 0.4D;
/* 26 */     this.particleRed = this.particleGreen = this.particleBlue = (float)(Math.random() * 0.30000001192092896D + 0.6000000238418579D);
/* 27 */     this.particleScale *= 0.75F;
/* 28 */     this.particleScale *= p_i46285_14_;
/* 29 */     this.field_174839_a = this.particleScale;
/* 30 */     this.particleMaxAge = (int)(6.0D / (Math.random() * 0.8D + 0.6D));
/* 31 */     this.particleMaxAge = (int)(this.particleMaxAge * p_i46285_14_);
/* 32 */     this.noClip = false;
/* 33 */     setParticleTextureIndex(65);
/* 34 */     onUpdate();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
/* 44 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
/* 45 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 46 */     this.particleScale = this.field_174839_a * f;
/* 47 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 55 */     this.prevPosX = this.posX;
/* 56 */     this.prevPosY = this.posY;
/* 57 */     this.prevPosZ = this.posZ;
/*    */     
/* 59 */     if (this.particleAge++ >= this.particleMaxAge)
/*    */     {
/* 61 */       setDead();
/*    */     }
/*    */     
/* 64 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 65 */     this.particleGreen = (float)(this.particleGreen * 0.96D);
/* 66 */     this.particleBlue = (float)(this.particleBlue * 0.9D);
/* 67 */     this.motionX *= 0.699999988079071D;
/* 68 */     this.motionY *= 0.699999988079071D;
/* 69 */     this.motionZ *= 0.699999988079071D;
/* 70 */     this.motionY -= 0.019999999552965164D;
/*    */     
/* 72 */     if (this.onGround) {
/*    */       
/* 74 */       this.motionX *= 0.699999988079071D;
/* 75 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 83 */       return new EntityCrit2FX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class MagicFactory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 91 */       EntityFX entityfx = new EntityCrit2FX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 92 */       entityfx.setRBGColorF(entityfx.getRedColorF() * 0.3F, entityfx.getGreenColorF() * 0.8F, entityfx.getBlueColorF());
/* 93 */       entityfx.nextTextureIndexX();
/* 94 */       return entityfx;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\particle\EntityCrit2FX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
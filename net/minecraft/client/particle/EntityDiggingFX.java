/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityDiggingFX
/*     */   extends EntityFX {
/*     */   private IBlockState field_174847_a;
/*     */   private BlockPos field_181019_az;
/*     */   
/*     */   protected EntityDiggingFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, IBlockState state) {
/*  19 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*  20 */     this.field_174847_a = state;
/*  21 */     setParticleIcon(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state));
/*  22 */     this.particleGravity = (state.getBlock()).blockParticleGravity;
/*  23 */     this.particleRed = this.particleGreen = this.particleBlue = 0.6F;
/*  24 */     this.particleScale /= 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityDiggingFX func_174846_a(BlockPos pos) {
/*  29 */     this.field_181019_az = pos;
/*     */     
/*  31 */     if (this.field_174847_a.getBlock() == Blocks.grass)
/*     */     {
/*  33 */       return this;
/*     */     }
/*     */ 
/*     */     
/*  37 */     int i = this.field_174847_a.getBlock().colorMultiplier((IBlockAccess)this.worldObj, pos);
/*  38 */     this.particleRed *= (i >> 16 & 0xFF) / 255.0F;
/*  39 */     this.particleGreen *= (i >> 8 & 0xFF) / 255.0F;
/*  40 */     this.particleBlue *= (i & 0xFF) / 255.0F;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityDiggingFX func_174845_l() {
/*  47 */     this.field_181019_az = new BlockPos(this.posX, this.posY, this.posZ);
/*  48 */     Block block = this.field_174847_a.getBlock();
/*     */     
/*  50 */     if (block == Blocks.grass)
/*     */     {
/*  52 */       return this;
/*     */     }
/*     */ 
/*     */     
/*  56 */     int i = block.getRenderColor(this.field_174847_a);
/*  57 */     this.particleRed *= (i >> 16 & 0xFF) / 255.0F;
/*  58 */     this.particleGreen *= (i >> 8 & 0xFF) / 255.0F;
/*  59 */     this.particleBlue *= (i & 0xFF) / 255.0F;
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFXLayer() {
/*  66 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
/*  76 */     float f = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
/*  77 */     float f1 = f + 0.015609375F;
/*  78 */     float f2 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
/*  79 */     float f3 = f2 + 0.015609375F;
/*  80 */     float f4 = 0.1F * this.particleScale;
/*     */     
/*  82 */     if (this.particleIcon != null) {
/*     */       
/*  84 */       f = this.particleIcon.getInterpolatedU((this.particleTextureJitterX / 4.0F * 16.0F));
/*  85 */       f1 = this.particleIcon.getInterpolatedU(((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F));
/*  86 */       f2 = this.particleIcon.getInterpolatedV((this.particleTextureJitterY / 4.0F * 16.0F));
/*  87 */       f3 = this.particleIcon.getInterpolatedV(((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F));
/*     */     } 
/*     */     
/*  90 */     float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/*  91 */     float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/*  92 */     float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/*  93 */     int i = getBrightnessForRender(partialTicks);
/*  94 */     int j = i >> 16 & 0xFFFF;
/*  95 */     int k = i & 0xFFFF;
/*  96 */     worldRendererIn.pos((f5 - p_180434_4_ * f4 - p_180434_7_ * f4), (f6 - p_180434_5_ * f4), (f7 - p_180434_6_ * f4 - p_180434_8_ * f4)).tex(f, f3).func_181666_a(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).func_181671_a(j, k).endVertex();
/*  97 */     worldRendererIn.pos((f5 - p_180434_4_ * f4 + p_180434_7_ * f4), (f6 + p_180434_5_ * f4), (f7 - p_180434_6_ * f4 + p_180434_8_ * f4)).tex(f, f2).func_181666_a(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).func_181671_a(j, k).endVertex();
/*  98 */     worldRendererIn.pos((f5 + p_180434_4_ * f4 + p_180434_7_ * f4), (f6 + p_180434_5_ * f4), (f7 + p_180434_6_ * f4 + p_180434_8_ * f4)).tex(f1, f2).func_181666_a(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).func_181671_a(j, k).endVertex();
/*  99 */     worldRendererIn.pos((f5 + p_180434_4_ * f4 - p_180434_7_ * f4), (f6 - p_180434_5_ * f4), (f7 + p_180434_6_ * f4 - p_180434_8_ * f4)).tex(f1, f3).func_181666_a(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).func_181671_a(j, k).endVertex();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender(float partialTicks) {
/* 104 */     int i = super.getBrightnessForRender(partialTicks);
/* 105 */     int j = 0;
/*     */     
/* 107 */     if (this.worldObj.isBlockLoaded(this.field_181019_az))
/*     */     {
/* 109 */       j = this.worldObj.getCombinedLight(this.field_181019_az, 0);
/*     */     }
/*     */     
/* 112 */     return (i == 0) ? j : i;
/*     */   }
/*     */   
/*     */   public static class Factory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 119 */       return (new EntityDiggingFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, Block.getStateById(p_178902_15_[0]))).func_174845_l();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\particle\EntityDiggingFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ public class EffectRenderer {
/*  36 */   private static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
/*     */   
/*     */   protected World worldObj;
/*     */   
/*  40 */   private List<EntityFX>[][] fxLayers = (List<EntityFX>[][])new List[4][];
/*  41 */   private List<EntityParticleEmitter> particleEmitters = Lists.newArrayList();
/*     */   
/*     */   private TextureManager renderer;
/*     */   
/*  45 */   private Random rand = new Random();
/*  46 */   private Map<Integer, IParticleFactory> particleTypes = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public EffectRenderer(World worldIn, TextureManager rendererIn) {
/*  50 */     this.worldObj = worldIn;
/*  51 */     this.renderer = rendererIn;
/*     */     
/*  53 */     for (int i = 0; i < 4; i++) {
/*     */       
/*  55 */       this.fxLayers[i] = (List<EntityFX>[])new List[2];
/*     */       
/*  57 */       for (int j = 0; j < 2; j++)
/*     */       {
/*  59 */         this.fxLayers[i][j] = Lists.newArrayList();
/*     */       }
/*     */     } 
/*     */     
/*  63 */     registerVanillaParticles();
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerVanillaParticles() {
/*  68 */     registerParticle(EnumParticleTypes.EXPLOSION_NORMAL.getParticleID(), new EntityExplodeFX.Factory());
/*  69 */     registerParticle(EnumParticleTypes.WATER_BUBBLE.getParticleID(), new EntityBubbleFX.Factory());
/*  70 */     registerParticle(EnumParticleTypes.WATER_SPLASH.getParticleID(), new EntitySplashFX.Factory());
/*  71 */     registerParticle(EnumParticleTypes.WATER_WAKE.getParticleID(), new EntityFishWakeFX.Factory());
/*  72 */     registerParticle(EnumParticleTypes.WATER_DROP.getParticleID(), new EntityRainFX.Factory());
/*  73 */     registerParticle(EnumParticleTypes.SUSPENDED.getParticleID(), new EntitySuspendFX.Factory());
/*  74 */     registerParticle(EnumParticleTypes.SUSPENDED_DEPTH.getParticleID(), new EntityAuraFX.Factory());
/*  75 */     registerParticle(EnumParticleTypes.CRIT.getParticleID(), new EntityCrit2FX.Factory());
/*  76 */     registerParticle(EnumParticleTypes.CRIT_MAGIC.getParticleID(), new EntityCrit2FX.MagicFactory());
/*  77 */     registerParticle(EnumParticleTypes.SMOKE_NORMAL.getParticleID(), new EntitySmokeFX.Factory());
/*  78 */     registerParticle(EnumParticleTypes.SMOKE_LARGE.getParticleID(), new EntityCritFX.Factory());
/*  79 */     registerParticle(EnumParticleTypes.SPELL.getParticleID(), new EntitySpellParticleFX.Factory());
/*  80 */     registerParticle(EnumParticleTypes.SPELL_INSTANT.getParticleID(), new EntitySpellParticleFX.InstantFactory());
/*  81 */     registerParticle(EnumParticleTypes.SPELL_MOB.getParticleID(), new EntitySpellParticleFX.MobFactory());
/*  82 */     registerParticle(EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID(), new EntitySpellParticleFX.AmbientMobFactory());
/*  83 */     registerParticle(EnumParticleTypes.SPELL_WITCH.getParticleID(), new EntitySpellParticleFX.WitchFactory());
/*  84 */     registerParticle(EnumParticleTypes.DRIP_WATER.getParticleID(), new EntityDropParticleFX.WaterFactory());
/*  85 */     registerParticle(EnumParticleTypes.DRIP_LAVA.getParticleID(), new EntityDropParticleFX.LavaFactory());
/*  86 */     registerParticle(EnumParticleTypes.VILLAGER_ANGRY.getParticleID(), new EntityHeartFX.AngryVillagerFactory());
/*  87 */     registerParticle(EnumParticleTypes.VILLAGER_HAPPY.getParticleID(), new EntityAuraFX.HappyVillagerFactory());
/*  88 */     registerParticle(EnumParticleTypes.TOWN_AURA.getParticleID(), new EntityAuraFX.Factory());
/*  89 */     registerParticle(EnumParticleTypes.NOTE.getParticleID(), new EntityNoteFX.Factory());
/*  90 */     registerParticle(EnumParticleTypes.PORTAL.getParticleID(), new EntityPortalFX.Factory());
/*  91 */     registerParticle(EnumParticleTypes.ENCHANTMENT_TABLE.getParticleID(), new EntityEnchantmentTableParticleFX.EnchantmentTable());
/*  92 */     registerParticle(EnumParticleTypes.FLAME.getParticleID(), new EntityFlameFX.Factory());
/*  93 */     registerParticle(EnumParticleTypes.LAVA.getParticleID(), new EntityLavaFX.Factory());
/*  94 */     registerParticle(EnumParticleTypes.FOOTSTEP.getParticleID(), new EntityFootStepFX.Factory());
/*  95 */     registerParticle(EnumParticleTypes.CLOUD.getParticleID(), new EntityCloudFX.Factory());
/*  96 */     registerParticle(EnumParticleTypes.REDSTONE.getParticleID(), new EntityReddustFX.Factory());
/*  97 */     registerParticle(EnumParticleTypes.SNOWBALL.getParticleID(), new EntityBreakingFX.SnowballFactory());
/*  98 */     registerParticle(EnumParticleTypes.SNOW_SHOVEL.getParticleID(), new EntitySnowShovelFX.Factory());
/*  99 */     registerParticle(EnumParticleTypes.SLIME.getParticleID(), new EntityBreakingFX.SlimeFactory());
/* 100 */     registerParticle(EnumParticleTypes.HEART.getParticleID(), new EntityHeartFX.Factory());
/* 101 */     registerParticle(EnumParticleTypes.BARRIER.getParticleID(), new Barrier.Factory());
/* 102 */     registerParticle(EnumParticleTypes.ITEM_CRACK.getParticleID(), new EntityBreakingFX.Factory());
/* 103 */     registerParticle(EnumParticleTypes.BLOCK_CRACK.getParticleID(), new EntityDiggingFX.Factory());
/* 104 */     registerParticle(EnumParticleTypes.BLOCK_DUST.getParticleID(), new EntityBlockDustFX.Factory());
/* 105 */     registerParticle(EnumParticleTypes.EXPLOSION_HUGE.getParticleID(), new EntityHugeExplodeFX.Factory());
/* 106 */     registerParticle(EnumParticleTypes.EXPLOSION_LARGE.getParticleID(), new EntityLargeExplodeFX.Factory());
/* 107 */     registerParticle(EnumParticleTypes.FIREWORKS_SPARK.getParticleID(), new EntityFirework.Factory());
/* 108 */     registerParticle(EnumParticleTypes.MOB_APPEARANCE.getParticleID(), new MobAppearance.Factory());
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerParticle(int id, IParticleFactory particleFactory) {
/* 113 */     this.particleTypes.put(Integer.valueOf(id), particleFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   public void emitParticleAtEntity(Entity entityIn, EnumParticleTypes particleTypes) {
/* 118 */     this.particleEmitters.add(new EntityParticleEmitter(this.worldObj, entityIn, particleTypes));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityFX spawnEffectParticle(int particleId, double p_178927_2_, double p_178927_4_, double p_178927_6_, double p_178927_8_, double p_178927_10_, double p_178927_12_, int... p_178927_14_) {
/* 128 */     IParticleFactory iparticlefactory = this.particleTypes.get(Integer.valueOf(particleId));
/*     */     
/* 130 */     if (iparticlefactory != null) {
/*     */       
/* 132 */       EntityFX entityfx = iparticlefactory.getEntityFX(particleId, this.worldObj, p_178927_2_, p_178927_4_, p_178927_6_, p_178927_8_, p_178927_10_, p_178927_12_, p_178927_14_);
/*     */       
/* 134 */       if (entityfx != null) {
/*     */         
/* 136 */         addEffect(entityfx);
/* 137 */         return entityfx;
/*     */       } 
/*     */     } 
/*     */     
/* 141 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addEffect(EntityFX effect) {
/* 146 */     if (effect != null)
/*     */     {
/* 148 */       if (!(effect instanceof EntityFirework.SparkFX) || Config.isFireworkParticles()) {
/*     */         
/* 150 */         int i = effect.getFXLayer();
/* 151 */         int j = (effect.getAlpha() != 1.0F) ? 0 : 1;
/*     */         
/* 153 */         if (this.fxLayers[i][j].size() >= 4000)
/*     */         {
/* 155 */           this.fxLayers[i][j].remove(0);
/*     */         }
/*     */         
/* 158 */         this.fxLayers[i][j].add(effect);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEffects() {
/* 165 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 167 */       updateEffectLayer(i);
/*     */     }
/*     */     
/* 170 */     List<EntityParticleEmitter> list = Lists.newArrayList();
/*     */     
/* 172 */     for (EntityParticleEmitter entityparticleemitter : this.particleEmitters) {
/*     */       
/* 174 */       entityparticleemitter.onUpdate();
/*     */       
/* 176 */       if (entityparticleemitter.isDead)
/*     */       {
/* 178 */         list.add(entityparticleemitter);
/*     */       }
/*     */     } 
/*     */     
/* 182 */     this.particleEmitters.removeAll(list);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateEffectLayer(int p_178922_1_) {
/* 187 */     for (int i = 0; i < 2; i++)
/*     */     {
/* 189 */       updateEffectAlphaLayer(this.fxLayers[p_178922_1_][i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateEffectAlphaLayer(List<EntityFX> entitiesFX) {
/* 195 */     List<EntityFX> list = Lists.newArrayList();
/* 196 */     long i = System.currentTimeMillis();
/* 197 */     int j = entitiesFX.size();
/*     */     
/* 199 */     for (int k = 0; k < entitiesFX.size(); k++) {
/*     */       
/* 201 */       EntityFX entityfx = entitiesFX.get(k);
/* 202 */       tickParticle(entityfx);
/*     */       
/* 204 */       if (entityfx.isDead)
/*     */       {
/* 206 */         list.add(entityfx);
/*     */       }
/*     */       
/* 209 */       j--;
/*     */       
/* 211 */       if (System.currentTimeMillis() > i + 20L) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 217 */     if (j > 0) {
/*     */       
/* 219 */       int l = j;
/*     */       
/* 221 */       for (Iterator<EntityFX> iterator = entitiesFX.iterator(); iterator.hasNext() && l > 0; l--) {
/*     */         
/* 223 */         EntityFX entityfx1 = iterator.next();
/* 224 */         entityfx1.setDead();
/* 225 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */     
/* 229 */     entitiesFX.removeAll(list);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void tickParticle(final EntityFX p_178923_1_) {
/*     */     try {
/* 236 */       p_178923_1_.onUpdate();
/*     */     }
/* 238 */     catch (Throwable throwable) {
/*     */       
/* 240 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Ticking Particle");
/* 241 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being ticked");
/* 242 */       final int i = p_178923_1_.getFXLayer();
/* 243 */       crashreportcategory.addCrashSectionCallable("Particle", new Callable<String>()
/*     */           {
/*     */             public String call() throws Exception
/*     */             {
/* 247 */               return p_178923_1_.toString();
/*     */             }
/*     */           });
/* 250 */       crashreportcategory.addCrashSectionCallable("Particle Type", new Callable<String>()
/*     */           {
/*     */             public String call() throws Exception
/*     */             {
/* 254 */               return (i == 0) ? "MISC_TEXTURE" : ((i == 1) ? "TERRAIN_TEXTURE" : ((i == 3) ? "ENTITY_PARTICLE_TEXTURE" : ("Unknown - " + i)));
/*     */             }
/*     */           });
/* 257 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticles(Entity entityIn, float partialTicks) {
/* 266 */     float f = ActiveRenderInfo.getRotationX();
/* 267 */     float f1 = ActiveRenderInfo.getRotationZ();
/* 268 */     float f2 = ActiveRenderInfo.getRotationYZ();
/* 269 */     float f3 = ActiveRenderInfo.getRotationXY();
/* 270 */     float f4 = ActiveRenderInfo.getRotationXZ();
/* 271 */     EntityFX.interpPosX = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 272 */     EntityFX.interpPosY = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 273 */     EntityFX.interpPosZ = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/* 274 */     GlStateManager.enableBlend();
/* 275 */     GlStateManager.blendFunc(770, 771);
/* 276 */     GlStateManager.alphaFunc(516, 0.003921569F);
/*     */     
/* 278 */     for (int i = 0; i < 3; i++) {
/*     */       
/* 280 */       for (int j = 0; j < 2; j++) {
/*     */         
/* 282 */         final int i_f = i;
/*     */         
/* 284 */         if (!this.fxLayers[i][j].isEmpty()) {
/*     */           
/* 286 */           switch (j) {
/*     */             
/*     */             case 0:
/* 289 */               GlStateManager.depthMask(false);
/*     */               break;
/*     */             
/*     */             case 1:
/* 293 */               GlStateManager.depthMask(true);
/*     */               break;
/*     */           } 
/* 296 */           switch (i) {
/*     */ 
/*     */             
/*     */             default:
/* 300 */               this.renderer.bindTexture(particleTextures);
/*     */               break;
/*     */             
/*     */             case 1:
/* 304 */               this.renderer.bindTexture(TextureMap.locationBlocksTexture);
/*     */               break;
/*     */           } 
/* 307 */           GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 308 */           Tessellator tessellator = Tessellator.getInstance();
/* 309 */           WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 310 */           worldrenderer.begin(7, DefaultVertexFormats.field_181704_d);
/*     */           
/* 312 */           for (int k = 0; k < this.fxLayers[i][j].size(); k++) {
/*     */             
/* 314 */             final EntityFX entityfx = this.fxLayers[i][j].get(k);
/*     */ 
/*     */             
/*     */             try {
/* 318 */               entityfx.renderParticle(worldrenderer, entityIn, partialTicks, f, f4, f1, f2, f3);
/*     */             }
/* 320 */             catch (Throwable throwable) {
/*     */               
/* 322 */               CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering Particle");
/* 323 */               CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being rendered");
/* 324 */               crashreportcategory.addCrashSectionCallable("Particle", new Callable<String>()
/*     */                   {
/*     */                     public String call() throws Exception
/*     */                     {
/* 328 */                       return entityfx.toString();
/*     */                     }
/*     */                   });
/* 331 */               crashreportcategory.addCrashSectionCallable("Particle Type", new Callable<String>()
/*     */                   {
/*     */                     public String call() throws Exception
/*     */                     {
/* 335 */                       return (i_f == 0) ? "MISC_TEXTURE" : ((i_f == 1) ? "TERRAIN_TEXTURE" : ((i_f == 3) ? "ENTITY_PARTICLE_TEXTURE" : ("Unknown - " + i_f)));
/*     */                     }
/*     */                   });
/* 338 */               throw new ReportedException(crashreport);
/*     */             } 
/*     */           } 
/*     */           
/* 342 */           tessellator.draw();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 347 */     GlStateManager.depthMask(true);
/* 348 */     GlStateManager.disableBlend();
/* 349 */     GlStateManager.alphaFunc(516, 0.1F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderLitParticles(Entity entityIn, float p_78872_2_) {
/* 354 */     float f = 0.017453292F;
/* 355 */     float f1 = MathHelper.cos(entityIn.rotationYaw * 0.017453292F);
/* 356 */     float f2 = MathHelper.sin(entityIn.rotationYaw * 0.017453292F);
/* 357 */     float f3 = -f2 * MathHelper.sin(entityIn.rotationPitch * 0.017453292F);
/* 358 */     float f4 = f1 * MathHelper.sin(entityIn.rotationPitch * 0.017453292F);
/* 359 */     float f5 = MathHelper.cos(entityIn.rotationPitch * 0.017453292F);
/*     */     
/* 361 */     for (int i = 0; i < 2; i++) {
/*     */       
/* 363 */       List<EntityFX> list = this.fxLayers[3][i];
/*     */       
/* 365 */       if (!list.isEmpty()) {
/*     */         
/* 367 */         Tessellator tessellator = Tessellator.getInstance();
/* 368 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*     */         
/* 370 */         for (int j = 0; j < list.size(); j++) {
/*     */           
/* 372 */           EntityFX entityfx = list.get(j);
/* 373 */           entityfx.renderParticle(worldrenderer, entityIn, p_78872_2_, f1, f5, f2, f3, f4);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearEffects(World worldIn) {
/* 381 */     this.worldObj = worldIn;
/*     */     
/* 383 */     for (int i = 0; i < 4; i++) {
/*     */       
/* 385 */       for (int j = 0; j < 2; j++)
/*     */       {
/* 387 */         this.fxLayers[i][j].clear();
/*     */       }
/*     */     } 
/*     */     
/* 391 */     this.particleEmitters.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBlockDestroyEffects(BlockPos pos, IBlockState state) {
/*     */     boolean flag;
/* 398 */     if (Reflector.ForgeBlock_addDestroyEffects.exists() && Reflector.ForgeBlock_isAir.exists()) {
/*     */       
/* 400 */       Block block = state.getBlock();
/* 401 */       flag = (!Reflector.callBoolean(block, Reflector.ForgeBlock_isAir, new Object[] { this.worldObj, pos }) && !Reflector.callBoolean(block, Reflector.ForgeBlock_addDestroyEffects, new Object[] { this.worldObj, pos, this }));
/*     */     }
/*     */     else {
/*     */       
/* 405 */       flag = (state.getBlock().getMaterial() != Material.air);
/*     */     } 
/*     */     
/* 408 */     if (flag) {
/*     */       
/* 410 */       state = state.getBlock().getActualState(state, (IBlockAccess)this.worldObj, pos);
/* 411 */       int l = 4;
/*     */       
/* 413 */       for (int i = 0; i < l; i++) {
/*     */         
/* 415 */         for (int j = 0; j < l; j++) {
/*     */           
/* 417 */           for (int k = 0; k < l; k++) {
/*     */             
/* 419 */             double d0 = pos.getX() + (i + 0.5D) / l;
/* 420 */             double d1 = pos.getY() + (j + 0.5D) / l;
/* 421 */             double d2 = pos.getZ() + (k + 0.5D) / l;
/* 422 */             addEffect((new EntityDiggingFX(this.worldObj, d0, d1, d2, d0 - pos.getX() - 0.5D, d1 - pos.getY() - 0.5D, d2 - pos.getZ() - 0.5D, state)).func_174846_a(pos));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBlockHitEffects(BlockPos pos, EnumFacing side) {
/* 437 */     IBlockState iblockstate = this.worldObj.getBlockState(pos);
/* 438 */     Block block = iblockstate.getBlock();
/*     */     
/* 440 */     if (block.getRenderType() != -1) {
/*     */       
/* 442 */       int i = pos.getX();
/* 443 */       int j = pos.getY();
/* 444 */       int k = pos.getZ();
/* 445 */       float f = 0.1F;
/* 446 */       double d0 = i + this.rand.nextDouble() * (block.getBlockBoundsMaxX() - block.getBlockBoundsMinX() - (f * 2.0F)) + f + block.getBlockBoundsMinX();
/* 447 */       double d1 = j + this.rand.nextDouble() * (block.getBlockBoundsMaxY() - block.getBlockBoundsMinY() - (f * 2.0F)) + f + block.getBlockBoundsMinY();
/* 448 */       double d2 = k + this.rand.nextDouble() * (block.getBlockBoundsMaxZ() - block.getBlockBoundsMinZ() - (f * 2.0F)) + f + block.getBlockBoundsMinZ();
/*     */       
/* 450 */       if (side == EnumFacing.DOWN)
/*     */       {
/* 452 */         d1 = j + block.getBlockBoundsMinY() - f;
/*     */       }
/*     */       
/* 455 */       if (side == EnumFacing.UP)
/*     */       {
/* 457 */         d1 = j + block.getBlockBoundsMaxY() + f;
/*     */       }
/*     */       
/* 460 */       if (side == EnumFacing.NORTH)
/*     */       {
/* 462 */         d2 = k + block.getBlockBoundsMinZ() - f;
/*     */       }
/*     */       
/* 465 */       if (side == EnumFacing.SOUTH)
/*     */       {
/* 467 */         d2 = k + block.getBlockBoundsMaxZ() + f;
/*     */       }
/*     */       
/* 470 */       if (side == EnumFacing.WEST)
/*     */       {
/* 472 */         d0 = i + block.getBlockBoundsMinX() - f;
/*     */       }
/*     */       
/* 475 */       if (side == EnumFacing.EAST)
/*     */       {
/* 477 */         d0 = i + block.getBlockBoundsMaxX() + f;
/*     */       }
/*     */       
/* 480 */       addEffect((new EntityDiggingFX(this.worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D, iblockstate)).func_174846_a(pos).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveToAlphaLayer(EntityFX effect) {
/* 486 */     moveToLayer(effect, 1, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveToNoAlphaLayer(EntityFX effect) {
/* 491 */     moveToLayer(effect, 0, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   private void moveToLayer(EntityFX effect, int p_178924_2_, int p_178924_3_) {
/* 496 */     for (int i = 0; i < 4; i++) {
/*     */       
/* 498 */       if (this.fxLayers[i][p_178924_2_].contains(effect)) {
/*     */         
/* 500 */         this.fxLayers[i][p_178924_2_].remove(effect);
/* 501 */         this.fxLayers[i][p_178924_3_].add(effect);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStatistics() {
/* 508 */     int i = 0;
/*     */     
/* 510 */     for (int j = 0; j < 4; j++) {
/*     */       
/* 512 */       for (int k = 0; k < 2; k++)
/*     */       {
/* 514 */         i += this.fxLayers[j][k].size();
/*     */       }
/*     */     } 
/*     */     
/* 518 */     return "" + i;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBlockHitEffects(BlockPos p_addBlockHitEffects_1_, MovingObjectPosition p_addBlockHitEffects_2_) {
/* 523 */     IBlockState iblockstate = this.worldObj.getBlockState(p_addBlockHitEffects_1_);
/*     */     
/* 525 */     if (iblockstate != null) {
/*     */       
/* 527 */       boolean flag = Reflector.callBoolean(iblockstate.getBlock(), Reflector.ForgeBlock_addHitEffects, new Object[] { this.worldObj, p_addBlockHitEffects_2_, this });
/*     */       
/* 529 */       if (iblockstate != null && !flag)
/*     */       {
/* 531 */         addBlockHitEffects(p_addBlockHitEffects_1_, p_addBlockHitEffects_2_.sideHit);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\particle\EffectRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
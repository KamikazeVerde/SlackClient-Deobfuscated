/*     */ package net.minecraft.client.renderer.entity;
/*     */ import cc.slack.utils.render.FreeLookUtil;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockBed;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelChicken;
/*     */ import net.minecraft.client.model.ModelCow;
/*     */ import net.minecraft.client.model.ModelHorse;
/*     */ import net.minecraft.client.model.ModelOcelot;
/*     */ import net.minecraft.client.model.ModelPig;
/*     */ import net.minecraft.client.model.ModelRabbit;
/*     */ import net.minecraft.client.model.ModelSheep2;
/*     */ import net.minecraft.client.model.ModelSlime;
/*     */ import net.minecraft.client.model.ModelSquid;
/*     */ import net.minecraft.client.model.ModelWolf;
/*     */ import net.minecraft.client.model.ModelZombie;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
/*     */ import net.minecraft.client.renderer.tileentity.RenderItemFrame;
/*     */ import net.minecraft.client.renderer.tileentity.RenderWitherSkull;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLeashKnot;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.ai.EntityMinecartMobSpawner;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.entity.boss.EntityWither;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.entity.item.EntityArmorStand;
/*     */ import net.minecraft.entity.item.EntityBoat;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.item.EntityEnderEye;
/*     */ import net.minecraft.entity.item.EntityEnderPearl;
/*     */ import net.minecraft.entity.item.EntityExpBottle;
/*     */ import net.minecraft.entity.item.EntityFallingBlock;
/*     */ import net.minecraft.entity.item.EntityFireworkRocket;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.item.EntityMinecartTNT;
/*     */ import net.minecraft.entity.item.EntityPainting;
/*     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.monster.EntityBlaze;
/*     */ import net.minecraft.entity.monster.EntityCaveSpider;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.entity.monster.EntityEnderman;
/*     */ import net.minecraft.entity.monster.EntityEndermite;
/*     */ import net.minecraft.entity.monster.EntityGhast;
/*     */ import net.minecraft.entity.monster.EntityGiantZombie;
/*     */ import net.minecraft.entity.monster.EntityGuardian;
/*     */ import net.minecraft.entity.monster.EntityIronGolem;
/*     */ import net.minecraft.entity.monster.EntityMagmaCube;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.monster.EntitySilverfish;
/*     */ import net.minecraft.entity.monster.EntitySkeleton;
/*     */ import net.minecraft.entity.monster.EntitySlime;
/*     */ import net.minecraft.entity.monster.EntitySnowman;
/*     */ import net.minecraft.entity.monster.EntitySpider;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.entity.passive.EntityBat;
/*     */ import net.minecraft.entity.passive.EntityChicken;
/*     */ import net.minecraft.entity.passive.EntityCow;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.entity.passive.EntityMooshroom;
/*     */ import net.minecraft.entity.passive.EntityOcelot;
/*     */ import net.minecraft.entity.passive.EntityPig;
/*     */ import net.minecraft.entity.passive.EntityRabbit;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.passive.EntitySquid;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityEgg;
/*     */ import net.minecraft.entity.projectile.EntityFishHook;
/*     */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*     */ import net.minecraft.entity.projectile.EntitySnowball;
/*     */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.entity.model.CustomEntityModels;
/*     */ import net.optifine.player.PlayerItemsLayer;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ public class RenderManager {
/* 111 */   private Map<Class, Render> entityRenderMap = Maps.newHashMap();
/* 112 */   private Map<String, RenderPlayer> skinMap = Maps.newHashMap();
/*     */   
/*     */   private RenderPlayer playerRenderer;
/*     */   
/*     */   private FontRenderer textRenderer;
/*     */   
/*     */   public double renderPosX;
/*     */   
/*     */   public double renderPosY;
/*     */   
/*     */   public double renderPosZ;
/*     */   
/*     */   public TextureManager renderEngine;
/*     */   
/*     */   public World worldObj;
/*     */   
/*     */   public Entity livingPlayer;
/*     */   
/*     */   public Entity pointedEntity;
/*     */   
/*     */   public float playerViewY;
/*     */   public float playerViewX;
/*     */   public GameSettings options;
/*     */   public double viewerPosX;
/*     */   public double viewerPosY;
/*     */   public double viewerPosZ;
/*     */   private boolean renderOutlines = false;
/*     */   private boolean renderShadow = true;
/*     */   private boolean debugBoundingBox = false;
/* 141 */   public Render renderRender = null;
/*     */ 
/*     */   
/*     */   public RenderManager(TextureManager renderEngineIn, RenderItem itemRendererIn) {
/* 145 */     this.renderEngine = renderEngineIn;
/* 146 */     this.entityRenderMap.put(EntityCaveSpider.class, new RenderCaveSpider(this));
/* 147 */     this.entityRenderMap.put(EntitySpider.class, new RenderSpider<>(this));
/* 148 */     this.entityRenderMap.put(EntityPig.class, new RenderPig(this, (ModelBase)new ModelPig(), 0.7F));
/* 149 */     this.entityRenderMap.put(EntitySheep.class, new RenderSheep(this, (ModelBase)new ModelSheep2(), 0.7F));
/* 150 */     this.entityRenderMap.put(EntityCow.class, new RenderCow(this, (ModelBase)new ModelCow(), 0.7F));
/* 151 */     this.entityRenderMap.put(EntityMooshroom.class, new RenderMooshroom(this, (ModelBase)new ModelCow(), 0.7F));
/* 152 */     this.entityRenderMap.put(EntityWolf.class, new RenderWolf(this, (ModelBase)new ModelWolf(), 0.5F));
/* 153 */     this.entityRenderMap.put(EntityChicken.class, new RenderChicken(this, (ModelBase)new ModelChicken(), 0.3F));
/* 154 */     this.entityRenderMap.put(EntityOcelot.class, new RenderOcelot(this, (ModelBase)new ModelOcelot(), 0.4F));
/* 155 */     this.entityRenderMap.put(EntityRabbit.class, new RenderRabbit(this, (ModelBase)new ModelRabbit(), 0.3F));
/* 156 */     this.entityRenderMap.put(EntitySilverfish.class, new RenderSilverfish(this));
/* 157 */     this.entityRenderMap.put(EntityEndermite.class, new RenderEndermite(this));
/* 158 */     this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper(this));
/* 159 */     this.entityRenderMap.put(EntityEnderman.class, new RenderEnderman(this));
/* 160 */     this.entityRenderMap.put(EntitySnowman.class, new RenderSnowMan(this));
/* 161 */     this.entityRenderMap.put(EntitySkeleton.class, new RenderSkeleton(this));
/* 162 */     this.entityRenderMap.put(EntityWitch.class, new RenderWitch(this));
/* 163 */     this.entityRenderMap.put(EntityBlaze.class, new RenderBlaze(this));
/* 164 */     this.entityRenderMap.put(EntityPigZombie.class, new RenderPigZombie(this));
/* 165 */     this.entityRenderMap.put(EntityZombie.class, new RenderZombie(this));
/* 166 */     this.entityRenderMap.put(EntitySlime.class, new RenderSlime(this, (ModelBase)new ModelSlime(16), 0.25F));
/* 167 */     this.entityRenderMap.put(EntityMagmaCube.class, new RenderMagmaCube(this));
/* 168 */     this.entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(this, (ModelBase)new ModelZombie(), 0.5F, 6.0F));
/* 169 */     this.entityRenderMap.put(EntityGhast.class, new RenderGhast(this));
/* 170 */     this.entityRenderMap.put(EntitySquid.class, new RenderSquid(this, (ModelBase)new ModelSquid(), 0.7F));
/* 171 */     this.entityRenderMap.put(EntityVillager.class, new RenderVillager(this));
/* 172 */     this.entityRenderMap.put(EntityIronGolem.class, new RenderIronGolem(this));
/* 173 */     this.entityRenderMap.put(EntityBat.class, new RenderBat(this));
/* 174 */     this.entityRenderMap.put(EntityGuardian.class, new RenderGuardian(this));
/* 175 */     this.entityRenderMap.put(EntityDragon.class, new RenderDragon(this));
/* 176 */     this.entityRenderMap.put(EntityEnderCrystal.class, new RenderEnderCrystal(this));
/* 177 */     this.entityRenderMap.put(EntityWither.class, new RenderWither(this));
/* 178 */     this.entityRenderMap.put(Entity.class, new RenderEntity(this));
/* 179 */     this.entityRenderMap.put(EntityPainting.class, new RenderPainting(this));
/* 180 */     this.entityRenderMap.put(EntityItemFrame.class, new RenderItemFrame(this, itemRendererIn));
/* 181 */     this.entityRenderMap.put(EntityLeashKnot.class, new RenderLeashKnot(this));
/* 182 */     this.entityRenderMap.put(EntityArrow.class, new RenderArrow(this));
/* 183 */     this.entityRenderMap.put(EntitySnowball.class, new RenderSnowball<>(this, Items.snowball, itemRendererIn));
/* 184 */     this.entityRenderMap.put(EntityEnderPearl.class, new RenderSnowball<>(this, Items.ender_pearl, itemRendererIn));
/* 185 */     this.entityRenderMap.put(EntityEnderEye.class, new RenderSnowball<>(this, Items.ender_eye, itemRendererIn));
/* 186 */     this.entityRenderMap.put(EntityEgg.class, new RenderSnowball<>(this, Items.egg, itemRendererIn));
/* 187 */     this.entityRenderMap.put(EntityPotion.class, new RenderPotion(this, itemRendererIn));
/* 188 */     this.entityRenderMap.put(EntityExpBottle.class, new RenderSnowball<>(this, Items.experience_bottle, itemRendererIn));
/* 189 */     this.entityRenderMap.put(EntityFireworkRocket.class, new RenderSnowball<>(this, Items.fireworks, itemRendererIn));
/* 190 */     this.entityRenderMap.put(EntityLargeFireball.class, new RenderFireball(this, 2.0F));
/* 191 */     this.entityRenderMap.put(EntitySmallFireball.class, new RenderFireball(this, 0.5F));
/* 192 */     this.entityRenderMap.put(EntityWitherSkull.class, new RenderWitherSkull(this));
/* 193 */     this.entityRenderMap.put(EntityItem.class, new RenderEntityItem(this, itemRendererIn));
/* 194 */     this.entityRenderMap.put(EntityXPOrb.class, new RenderXPOrb(this));
/* 195 */     this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed(this));
/* 196 */     this.entityRenderMap.put(EntityFallingBlock.class, new RenderFallingBlock(this));
/* 197 */     this.entityRenderMap.put(EntityArmorStand.class, new ArmorStandRenderer(this));
/* 198 */     this.entityRenderMap.put(EntityMinecartTNT.class, new RenderTntMinecart(this));
/* 199 */     this.entityRenderMap.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner(this));
/* 200 */     this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart<>(this));
/* 201 */     this.entityRenderMap.put(EntityBoat.class, new RenderBoat(this));
/* 202 */     this.entityRenderMap.put(EntityFishHook.class, new RenderFish(this));
/* 203 */     this.entityRenderMap.put(EntityHorse.class, new RenderHorse(this, new ModelHorse(), 0.75F));
/* 204 */     this.entityRenderMap.put(EntityLightningBolt.class, new RenderLightningBolt(this));
/* 205 */     this.playerRenderer = new RenderPlayer(this);
/* 206 */     this.skinMap.put("default", this.playerRenderer);
/* 207 */     this.skinMap.put("slim", new RenderPlayer(this, true));
/* 208 */     PlayerItemsLayer.register(this.skinMap);
/*     */     
/* 210 */     if (Reflector.RenderingRegistry_loadEntityRenderers.exists())
/*     */     {
/* 212 */       Reflector.call(Reflector.RenderingRegistry_loadEntityRenderers, new Object[] { this, this.entityRenderMap });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRenderPosition(double renderPosXIn, double renderPosYIn, double renderPosZIn) {
/* 218 */     this.renderPosX = renderPosXIn;
/* 219 */     this.renderPosY = renderPosYIn;
/* 220 */     this.renderPosZ = renderPosZIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Entity> Render<T> getEntityClassRenderObject(Class<? extends Entity> p_78715_1_) {
/* 225 */     Render<? extends Entity> render = this.entityRenderMap.get(p_78715_1_);
/*     */     
/* 227 */     if (render == null && p_78715_1_ != Entity.class) {
/*     */       
/* 229 */       render = getEntityClassRenderObject((Class)p_78715_1_.getSuperclass());
/* 230 */       this.entityRenderMap.put(p_78715_1_, render);
/*     */     } 
/*     */     
/* 233 */     return (Render)render;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Entity> Render<T> getEntityRenderObject(Entity entityIn) {
/* 238 */     if (entityIn instanceof AbstractClientPlayer) {
/*     */       
/* 240 */       String s = ((AbstractClientPlayer)entityIn).getSkinType();
/* 241 */       RenderPlayer renderplayer = this.skinMap.get(s);
/* 242 */       return (renderplayer != null) ? renderplayer : this.playerRenderer;
/*     */     } 
/*     */ 
/*     */     
/* 246 */     return getEntityClassRenderObject((Class)entityIn.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void cacheActiveRenderInfo(World worldIn, FontRenderer textRendererIn, Entity livingPlayerIn, Entity pointedEntityIn, GameSettings optionsIn, float partialTicks) {
/* 252 */     this.worldObj = worldIn;
/* 253 */     this.options = optionsIn;
/* 254 */     this.livingPlayer = livingPlayerIn;
/* 255 */     this.pointedEntity = pointedEntityIn;
/* 256 */     this.textRenderer = textRendererIn;
/*     */     
/* 258 */     if (livingPlayerIn instanceof EntityLivingBase && ((EntityLivingBase)livingPlayerIn).isPlayerSleeping()) {
/*     */       
/* 260 */       IBlockState iblockstate = worldIn.getBlockState(new BlockPos(livingPlayerIn));
/* 261 */       Block block = iblockstate.getBlock();
/*     */       
/* 263 */       if (Reflector.callBoolean(block, Reflector.ForgeBlock_isBed, new Object[] { iblockstate, worldIn, new BlockPos(livingPlayerIn), livingPlayerIn }))
/*     */       {
/* 265 */         EnumFacing enumfacing = (EnumFacing)Reflector.call(block, Reflector.ForgeBlock_getBedDirection, new Object[] { iblockstate, worldIn, new BlockPos(livingPlayerIn) });
/* 266 */         int i = enumfacing.getHorizontalIndex();
/* 267 */         this.playerViewY = (i * 90 + 180);
/* 268 */         this.playerViewX = 0.0F;
/*     */       }
/* 270 */       else if (block == Blocks.bed)
/*     */       {
/* 272 */         int j = ((EnumFacing)iblockstate.getValue((IProperty)BlockBed.FACING)).getHorizontalIndex();
/* 273 */         this.playerViewY = (j * 90 + 180);
/* 274 */         this.playerViewX = 0.0F;
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 279 */     else if (FreeLookUtil.freelooking) {
/* 280 */       this.playerViewY = FreeLookUtil.cameraYaw;
/* 281 */       this.playerViewX = FreeLookUtil.cameraPitch;
/*     */     } else {
/* 283 */       this.playerViewY = livingPlayerIn.prevRotationYaw + (livingPlayerIn.rotationYaw - livingPlayerIn.prevRotationYaw) * partialTicks;
/* 284 */       this.playerViewX = livingPlayerIn.prevRotationPitch + (livingPlayerIn.rotationPitch - livingPlayerIn.prevRotationPitch) * partialTicks;
/*     */     } 
/*     */ 
/*     */     
/* 288 */     if (optionsIn.thirdPersonView == 2)
/*     */     {
/* 290 */       this.playerViewY += 180.0F;
/*     */     }
/*     */     
/* 293 */     this.viewerPosX = livingPlayerIn.lastTickPosX + (livingPlayerIn.posX - livingPlayerIn.lastTickPosX) * partialTicks;
/* 294 */     this.viewerPosY = livingPlayerIn.lastTickPosY + (livingPlayerIn.posY - livingPlayerIn.lastTickPosY) * partialTicks;
/* 295 */     this.viewerPosZ = livingPlayerIn.lastTickPosZ + (livingPlayerIn.posZ - livingPlayerIn.lastTickPosZ) * partialTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayerViewY(float playerViewYIn) {
/* 300 */     this.playerViewY = playerViewYIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRenderShadow() {
/* 305 */     return this.renderShadow;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRenderShadow(boolean renderShadowIn) {
/* 310 */     this.renderShadow = renderShadowIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDebugBoundingBox(boolean debugBoundingBoxIn) {
/* 315 */     this.debugBoundingBox = debugBoundingBoxIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDebugBoundingBox() {
/* 320 */     return this.debugBoundingBox;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderEntitySimple(Entity entityIn, float partialTicks) {
/* 325 */     return renderEntityStatic(entityIn, partialTicks, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender(Entity entityIn, ICamera camera, double camX, double camY, double camZ) {
/* 330 */     Render<Entity> render = getEntityRenderObject(entityIn);
/* 331 */     return (render != null && render.shouldRender(entityIn, camera, camX, camY, camZ));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderEntityStatic(Entity entity, float partialTicks, boolean p_147936_3_) {
/* 336 */     if (entity.ticksExisted == 0) {
/*     */       
/* 338 */       entity.lastTickPosX = entity.posX;
/* 339 */       entity.lastTickPosY = entity.posY;
/* 340 */       entity.lastTickPosZ = entity.posZ;
/*     */     } 
/*     */     
/* 343 */     double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 344 */     double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 345 */     double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 346 */     float f = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;
/* 347 */     int i = entity.getBrightnessForRender(partialTicks);
/*     */     
/* 349 */     if (entity.isBurning())
/*     */     {
/* 351 */       i = 15728880;
/*     */     }
/*     */     
/* 354 */     int j = i % 65536;
/* 355 */     int k = i / 65536;
/* 356 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
/* 357 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 358 */     return doRenderEntity(entity, d0 - this.renderPosX, d1 - this.renderPosY, d2 - this.renderPosZ, f, partialTicks, p_147936_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderWitherSkull(Entity entityIn, float partialTicks) {
/* 363 */     double d0 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 364 */     double d1 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 365 */     double d2 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/* 366 */     Render<Entity> render = getEntityRenderObject(entityIn);
/*     */     
/* 368 */     if (render != null && this.renderEngine != null) {
/*     */       
/* 370 */       int i = entityIn.getBrightnessForRender(partialTicks);
/* 371 */       int j = i % 65536;
/* 372 */       int k = i / 65536;
/* 373 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
/* 374 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 375 */       render.renderName(entityIn, d0 - this.renderPosX, d1 - this.renderPosY, d2 - this.renderPosZ);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renderEntityWithPosYaw(Entity entityIn, double x, double y, double z, float entityYaw, float partialTicks) {
/* 381 */     return doRenderEntity(entityIn, x, y, z, entityYaw, partialTicks, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doRenderEntity(Entity entity, double x, double y, double z, float entityYaw, float partialTicks, boolean p_147939_10_) {
/* 386 */     Render<Entity> render = null;
/*     */ 
/*     */     
/*     */     try {
/* 390 */       render = getEntityRenderObject(entity);
/*     */       
/* 392 */       if (render != null && this.renderEngine != null) {
/*     */ 
/*     */         
/*     */         try {
/* 396 */           if (render instanceof RendererLivingEntity)
/*     */           {
/* 398 */             ((RendererLivingEntity)render).setRenderOutlines(this.renderOutlines);
/*     */           }
/*     */           
/* 401 */           if (CustomEntityModels.isActive())
/*     */           {
/* 403 */             this.renderRender = render;
/*     */           }
/*     */           
/* 406 */           render.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */         }
/* 408 */         catch (Throwable throwable2) {
/*     */           
/* 410 */           throw new ReportedException(CrashReport.makeCrashReport(throwable2, "Rendering entity in world"));
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 415 */           if (!this.renderOutlines)
/*     */           {
/* 417 */             render.doRenderShadowAndFire(entity, x, y, z, entityYaw, partialTicks);
/*     */           }
/*     */         }
/* 420 */         catch (Throwable throwable1) {
/*     */           
/* 422 */           throw new ReportedException(CrashReport.makeCrashReport(throwable1, "Post-rendering entity in world"));
/*     */         } 
/*     */         
/* 425 */         if (this.debugBoundingBox && !entity.isInvisible() && !p_147939_10_) {
/*     */           try
/*     */           {
/*     */             
/* 429 */             renderDebugBoundingBox(entity, x, y, z, entityYaw, partialTicks);
/*     */           }
/* 431 */           catch (Throwable throwable)
/*     */           {
/* 433 */             throw new ReportedException(CrashReport.makeCrashReport(throwable, "Rendering entity hitbox in world"));
/*     */           }
/*     */         
/*     */         }
/* 437 */       } else if (this.renderEngine != null) {
/*     */         
/* 439 */         return false;
/*     */       } 
/*     */       
/* 442 */       return true;
/*     */     }
/* 444 */     catch (Throwable throwable3) {
/*     */       
/* 446 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable3, "Rendering entity in world");
/* 447 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being rendered");
/* 448 */       entity.addEntityCrashInfo(crashreportcategory);
/* 449 */       CrashReportCategory crashreportcategory1 = crashreport.makeCategory("Renderer details");
/* 450 */       crashreportcategory1.addCrashSection("Assigned renderer", render);
/* 451 */       crashreportcategory1.addCrashSection("Location", CrashReportCategory.getCoordinateInfo(x, y, z));
/* 452 */       crashreportcategory1.addCrashSection("Rotation", Float.valueOf(entityYaw));
/* 453 */       crashreportcategory1.addCrashSection("Delta", Float.valueOf(partialTicks));
/* 454 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderDebugBoundingBox(Entity entityIn, double p_85094_2_, double p_85094_4_, double p_85094_6_, float p_85094_8_, float p_85094_9_) {
/* 463 */     GlStateManager.depthMask(false);
/* 464 */     GlStateManager.disableTexture2D();
/* 465 */     GlStateManager.disableLighting();
/* 466 */     GlStateManager.disableCull();
/* 467 */     GlStateManager.disableBlend();
/* 468 */     float f = entityIn.width / 2.0F;
/* 469 */     AxisAlignedBB axisalignedbb = entityIn.getEntityBoundingBox();
/* 470 */     AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(axisalignedbb.minX - entityIn.posX + p_85094_2_, axisalignedbb.minY - entityIn.posY + p_85094_4_, axisalignedbb.minZ - entityIn.posZ + p_85094_6_, axisalignedbb.maxX - entityIn.posX + p_85094_2_, axisalignedbb.maxY - entityIn.posY + p_85094_4_, axisalignedbb.maxZ - entityIn.posZ + p_85094_6_);
/* 471 */     RenderGlobal.func_181563_a(axisalignedbb1, 255, 255, 255, 255);
/*     */     
/* 473 */     if (entityIn instanceof EntityLivingBase) {
/*     */       
/* 475 */       float f1 = 0.01F;
/* 476 */       RenderGlobal.func_181563_a(new AxisAlignedBB(p_85094_2_ - f, p_85094_4_ + entityIn.getEyeHeight() - 0.009999999776482582D, p_85094_6_ - f, p_85094_2_ + f, p_85094_4_ + entityIn.getEyeHeight() + 0.009999999776482582D, p_85094_6_ + f), 255, 0, 0, 255);
/*     */     } 
/*     */     
/* 479 */     Tessellator tessellator = Tessellator.getInstance();
/* 480 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 481 */     Vec3 vec3 = entityIn.getLook(p_85094_9_);
/* 482 */     worldrenderer.begin(3, DefaultVertexFormats.field_181706_f);
/* 483 */     worldrenderer.pos(p_85094_2_, p_85094_4_ + entityIn.getEyeHeight(), p_85094_6_).func_181669_b(0, 0, 255, 255).endVertex();
/* 484 */     worldrenderer.pos(p_85094_2_ + vec3.xCoord * 2.0D, p_85094_4_ + entityIn.getEyeHeight() + vec3.yCoord * 2.0D, p_85094_6_ + vec3.zCoord * 2.0D).func_181669_b(0, 0, 255, 255).endVertex();
/* 485 */     tessellator.draw();
/* 486 */     GlStateManager.enableTexture2D();
/* 487 */     GlStateManager.enableLighting();
/* 488 */     GlStateManager.enableCull();
/* 489 */     GlStateManager.disableBlend();
/* 490 */     GlStateManager.depthMask(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(World worldIn) {
/* 498 */     this.worldObj = worldIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDistanceToCamera(double p_78714_1_, double p_78714_3_, double p_78714_5_) {
/* 503 */     double d0 = p_78714_1_ - this.viewerPosX;
/* 504 */     double d1 = p_78714_3_ - this.viewerPosY;
/* 505 */     double d2 = p_78714_5_ - this.viewerPosZ;
/* 506 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontRenderer getFontRenderer() {
/* 514 */     return this.textRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRenderOutlines(boolean renderOutlinesIn) {
/* 519 */     this.renderOutlines = renderOutlinesIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Class, Render> getEntityRenderMap() {
/* 524 */     return this.entityRenderMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEntityRenderMap(Map<Class, Render> p_setEntityRenderMap_1_) {
/* 529 */     this.entityRenderMap = p_setEntityRenderMap_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, RenderPlayer> getSkinMap() {
/* 534 */     return Collections.unmodifiableMap(this.skinMap);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\RenderManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
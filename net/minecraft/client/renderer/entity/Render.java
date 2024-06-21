/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.entity.model.IEntityRenderer;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public abstract class Render<T extends Entity>
/*     */   implements IEntityRenderer {
/*  30 */   private static final ResourceLocation shadowTextures = new ResourceLocation("textures/misc/shadow.png");
/*     */ 
/*     */   
/*     */   protected final RenderManager renderManager;
/*     */   
/*     */   public float shadowSize;
/*     */   
/*  37 */   protected float shadowOpaque = 1.0F;
/*  38 */   private Class entityClass = null;
/*  39 */   private ResourceLocation locationTextureCustom = null;
/*     */ 
/*     */   
/*     */   protected Render(RenderManager renderManager) {
/*  43 */     this.renderManager = renderManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender(T livingEntity, ICamera camera, double camX, double camY, double camZ) {
/*  48 */     AxisAlignedBB axisalignedbb = livingEntity.getEntityBoundingBox();
/*     */     
/*  50 */     if (axisalignedbb.func_181656_b() || axisalignedbb.getAverageEdgeLength() == 0.0D)
/*     */     {
/*  52 */       axisalignedbb = new AxisAlignedBB(((Entity)livingEntity).posX - 2.0D, ((Entity)livingEntity).posY - 2.0D, ((Entity)livingEntity).posZ - 2.0D, ((Entity)livingEntity).posX + 2.0D, ((Entity)livingEntity).posY + 2.0D, ((Entity)livingEntity).posZ + 2.0D);
/*     */     }
/*     */     
/*  55 */     return (livingEntity.isInRangeToRender3d(camX, camY, camZ) && (((Entity)livingEntity).ignoreFrustumCheck || camera.isBoundingBoxInFrustum(axisalignedbb)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  68 */     renderName(entity, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderName(T entity, double x, double y, double z) {
/*  73 */     if (canRenderName(entity))
/*     */     {
/*  75 */       renderLivingLabel(entity, entity.getDisplayName().getFormattedText(), x, y, z, 64);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canRenderName(T entity) {
/*  81 */     return (entity.getAlwaysRenderNameTagForRender() && entity.hasCustomName());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderOffsetLivingLabel(T entityIn, double x, double y, double z, String str, float p_177069_9_, double p_177069_10_) {
/*  86 */     renderLivingLabel(entityIn, str, x, y, z, 64);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ResourceLocation getEntityTexture(T paramT);
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean bindEntityTexture(T entity) {
/*  96 */     ResourceLocation resourcelocation = getEntityTexture(entity);
/*     */     
/*  98 */     if (this.locationTextureCustom != null)
/*     */     {
/* 100 */       resourcelocation = this.locationTextureCustom;
/*     */     }
/*     */     
/* 103 */     if (resourcelocation == null)
/*     */     {
/* 105 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 109 */     bindTexture(resourcelocation);
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindTexture(ResourceLocation location) {
/* 116 */     this.renderManager.renderEngine.bindTexture(location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderEntityOnFire(Entity entity, double x, double y, double z, float partialTicks) {
/* 124 */     GlStateManager.disableLighting();
/* 125 */     TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
/* 126 */     TextureAtlasSprite textureatlassprite = texturemap.getAtlasSprite("minecraft:blocks/fire_layer_0");
/* 127 */     TextureAtlasSprite textureatlassprite1 = texturemap.getAtlasSprite("minecraft:blocks/fire_layer_1");
/* 128 */     GlStateManager.pushMatrix();
/* 129 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 130 */     float f = entity.width * 1.4F;
/* 131 */     GlStateManager.scale(f, f, f);
/* 132 */     Tessellator tessellator = Tessellator.getInstance();
/* 133 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 134 */     float f1 = 0.5F;
/* 135 */     float f2 = 0.0F;
/* 136 */     float f3 = entity.height / f;
/* 137 */     float f4 = (float)(entity.posY - (entity.getEntityBoundingBox()).minY);
/* 138 */     GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 139 */     GlStateManager.translate(0.0F, 0.0F, -0.3F + (int)f3 * 0.02F);
/* 140 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 141 */     float f5 = 0.0F;
/* 142 */     int i = 0;
/* 143 */     boolean flag = Config.isMultiTexture();
/*     */     
/* 145 */     if (flag)
/*     */     {
/* 147 */       worldrenderer.setBlockLayer(EnumWorldBlockLayer.SOLID);
/*     */     }
/*     */     
/* 150 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*     */     
/* 152 */     while (f3 > 0.0F) {
/*     */       
/* 154 */       TextureAtlasSprite textureatlassprite2 = (i % 2 == 0) ? textureatlassprite : textureatlassprite1;
/* 155 */       worldrenderer.setSprite(textureatlassprite2);
/* 156 */       bindTexture(TextureMap.locationBlocksTexture);
/* 157 */       float f6 = textureatlassprite2.getMinU();
/* 158 */       float f7 = textureatlassprite2.getMinV();
/* 159 */       float f8 = textureatlassprite2.getMaxU();
/* 160 */       float f9 = textureatlassprite2.getMaxV();
/*     */       
/* 162 */       if (i / 2 % 2 == 0) {
/*     */         
/* 164 */         float f10 = f8;
/* 165 */         f8 = f6;
/* 166 */         f6 = f10;
/*     */       } 
/*     */       
/* 169 */       worldrenderer.pos((f1 - f2), (0.0F - f4), f5).tex(f8, f9).endVertex();
/* 170 */       worldrenderer.pos((-f1 - f2), (0.0F - f4), f5).tex(f6, f9).endVertex();
/* 171 */       worldrenderer.pos((-f1 - f2), (1.4F - f4), f5).tex(f6, f7).endVertex();
/* 172 */       worldrenderer.pos((f1 - f2), (1.4F - f4), f5).tex(f8, f7).endVertex();
/* 173 */       f3 -= 0.45F;
/* 174 */       f4 -= 0.45F;
/* 175 */       f1 *= 0.9F;
/* 176 */       f5 += 0.03F;
/* 177 */       i++;
/*     */     } 
/*     */     
/* 180 */     tessellator.draw();
/*     */     
/* 182 */     if (flag) {
/*     */       
/* 184 */       worldrenderer.setBlockLayer(null);
/* 185 */       GlStateManager.bindCurrentTexture();
/*     */     } 
/*     */     
/* 188 */     GlStateManager.popMatrix();
/* 189 */     GlStateManager.enableLighting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderShadow(Entity entityIn, double x, double y, double z, float shadowAlpha, float partialTicks) {
/* 198 */     if (!Config.isShaders() || !Shaders.shouldSkipDefaultShadow) {
/*     */       
/* 200 */       GlStateManager.enableBlend();
/* 201 */       GlStateManager.blendFunc(770, 771);
/* 202 */       this.renderManager.renderEngine.bindTexture(shadowTextures);
/* 203 */       World world = getWorldFromRenderManager();
/* 204 */       GlStateManager.depthMask(false);
/* 205 */       float f = this.shadowSize;
/*     */       
/* 207 */       if (entityIn instanceof EntityLiving) {
/*     */         
/* 209 */         EntityLiving entityliving = (EntityLiving)entityIn;
/* 210 */         f *= entityliving.getRenderSizeModifier();
/*     */         
/* 212 */         if (entityliving.isChild())
/*     */         {
/* 214 */           f *= 0.5F;
/*     */         }
/*     */       } 
/*     */       
/* 218 */       double d5 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 219 */       double d0 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 220 */       double d1 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/* 221 */       int i = MathHelper.floor_double(d5 - f);
/* 222 */       int j = MathHelper.floor_double(d5 + f);
/* 223 */       int k = MathHelper.floor_double(d0 - f);
/* 224 */       int l = MathHelper.floor_double(d0);
/* 225 */       int i1 = MathHelper.floor_double(d1 - f);
/* 226 */       int j1 = MathHelper.floor_double(d1 + f);
/* 227 */       double d2 = x - d5;
/* 228 */       double d3 = y - d0;
/* 229 */       double d4 = z - d1;
/* 230 */       Tessellator tessellator = Tessellator.getInstance();
/* 231 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 232 */       worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/*     */       
/* 234 */       for (BlockPos blockpos : BlockPos.getAllInBoxMutable(new BlockPos(i, k, i1), new BlockPos(j, l, j1))) {
/*     */         
/* 236 */         Block block = world.getBlockState(blockpos.down()).getBlock();
/*     */         
/* 238 */         if (block.getRenderType() != -1 && world.getLightFromNeighbors(blockpos) > 3)
/*     */         {
/* 240 */           func_180549_a(block, x, y, z, blockpos, shadowAlpha, f, d2, d3, d4);
/*     */         }
/*     */       } 
/*     */       
/* 244 */       tessellator.draw();
/* 245 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 246 */       GlStateManager.disableBlend();
/* 247 */       GlStateManager.depthMask(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private World getWorldFromRenderManager() {
/* 256 */     return this.renderManager.worldObj;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_180549_a(Block blockIn, double p_180549_2_, double p_180549_4_, double p_180549_6_, BlockPos pos, float p_180549_9_, float p_180549_10_, double p_180549_11_, double p_180549_13_, double p_180549_15_) {
/* 261 */     if (blockIn.isFullCube()) {
/*     */       
/* 263 */       Tessellator tessellator = Tessellator.getInstance();
/* 264 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 265 */       double d0 = (p_180549_9_ - (p_180549_4_ - pos.getY() + p_180549_13_) / 2.0D) * 0.5D * getWorldFromRenderManager().getLightBrightness(pos);
/*     */       
/* 267 */       if (d0 >= 0.0D) {
/*     */         
/* 269 */         if (d0 > 1.0D)
/*     */         {
/* 271 */           d0 = 1.0D;
/*     */         }
/*     */         
/* 274 */         double d1 = pos.getX() + blockIn.getBlockBoundsMinX() + p_180549_11_;
/* 275 */         double d2 = pos.getX() + blockIn.getBlockBoundsMaxX() + p_180549_11_;
/* 276 */         double d3 = pos.getY() + blockIn.getBlockBoundsMinY() + p_180549_13_ + 0.015625D;
/* 277 */         double d4 = pos.getZ() + blockIn.getBlockBoundsMinZ() + p_180549_15_;
/* 278 */         double d5 = pos.getZ() + blockIn.getBlockBoundsMaxZ() + p_180549_15_;
/* 279 */         float f = (float)((p_180549_2_ - d1) / 2.0D / p_180549_10_ + 0.5D);
/* 280 */         float f1 = (float)((p_180549_2_ - d2) / 2.0D / p_180549_10_ + 0.5D);
/* 281 */         float f2 = (float)((p_180549_6_ - d4) / 2.0D / p_180549_10_ + 0.5D);
/* 282 */         float f3 = (float)((p_180549_6_ - d5) / 2.0D / p_180549_10_ + 0.5D);
/* 283 */         worldrenderer.pos(d1, d3, d4).tex(f, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/* 284 */         worldrenderer.pos(d1, d3, d5).tex(f, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/* 285 */         worldrenderer.pos(d2, d3, d5).tex(f1, f3).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/* 286 */         worldrenderer.pos(d2, d3, d4).tex(f1, f2).func_181666_a(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderOffsetAABB(AxisAlignedBB boundingBox, double x, double y, double z) {
/* 296 */     GlStateManager.disableTexture2D();
/* 297 */     Tessellator tessellator = Tessellator.getInstance();
/* 298 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 299 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 300 */     worldrenderer.setTranslation(x, y, z);
/* 301 */     worldrenderer.begin(7, DefaultVertexFormats.field_181708_h);
/* 302 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).func_181663_c(0.0F, 0.0F, -1.0F).endVertex();
/* 303 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).func_181663_c(0.0F, 0.0F, -1.0F).endVertex();
/* 304 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).func_181663_c(0.0F, 0.0F, -1.0F).endVertex();
/* 305 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).func_181663_c(0.0F, 0.0F, -1.0F).endVertex();
/* 306 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).func_181663_c(0.0F, 0.0F, 1.0F).endVertex();
/* 307 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).func_181663_c(0.0F, 0.0F, 1.0F).endVertex();
/* 308 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).func_181663_c(0.0F, 0.0F, 1.0F).endVertex();
/* 309 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).func_181663_c(0.0F, 0.0F, 1.0F).endVertex();
/* 310 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).func_181663_c(0.0F, -1.0F, 0.0F).endVertex();
/* 311 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).func_181663_c(0.0F, -1.0F, 0.0F).endVertex();
/* 312 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).func_181663_c(0.0F, -1.0F, 0.0F).endVertex();
/* 313 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).func_181663_c(0.0F, -1.0F, 0.0F).endVertex();
/* 314 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/* 315 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/* 316 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/* 317 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/* 318 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).func_181663_c(-1.0F, 0.0F, 0.0F).endVertex();
/* 319 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).func_181663_c(-1.0F, 0.0F, 0.0F).endVertex();
/* 320 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).func_181663_c(-1.0F, 0.0F, 0.0F).endVertex();
/* 321 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).func_181663_c(-1.0F, 0.0F, 0.0F).endVertex();
/* 322 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).func_181663_c(1.0F, 0.0F, 0.0F).endVertex();
/* 323 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).func_181663_c(1.0F, 0.0F, 0.0F).endVertex();
/* 324 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).func_181663_c(1.0F, 0.0F, 0.0F).endVertex();
/* 325 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).func_181663_c(1.0F, 0.0F, 0.0F).endVertex();
/* 326 */     tessellator.draw();
/* 327 */     worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 328 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
/* 336 */     if (this.renderManager.options != null) {
/*     */       
/* 338 */       if (this.renderManager.options.field_181151_V && this.shadowSize > 0.0F && !entityIn.isInvisible() && this.renderManager.isRenderShadow()) {
/*     */         
/* 340 */         double d0 = this.renderManager.getDistanceToCamera(entityIn.posX, entityIn.posY, entityIn.posZ);
/* 341 */         float f = (float)((1.0D - d0 / 256.0D) * this.shadowOpaque);
/*     */         
/* 343 */         if (f > 0.0F)
/*     */         {
/* 345 */           renderShadow(entityIn, x, y, z, f, partialTicks);
/*     */         }
/*     */       } 
/*     */       
/* 349 */       if (entityIn.canRenderOnFire() && (!(entityIn instanceof EntityPlayer) || !((EntityPlayer)entityIn).isSpectator()))
/*     */       {
/* 351 */         renderEntityOnFire(entityIn, x, y, z, partialTicks);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontRenderer getFontRendererFromRenderManager() {
/* 361 */     return this.renderManager.getFontRenderer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderLivingLabel(T entityIn, String str, double x, double y, double z, int maxDistance) {
/* 369 */     double d0 = entityIn.getDistanceSqToEntity(this.renderManager.livingPlayer);
/*     */     
/* 371 */     if (d0 <= (maxDistance * maxDistance)) {
/*     */       
/* 373 */       FontRenderer fontrenderer = getFontRendererFromRenderManager();
/* 374 */       float f = 1.6F;
/* 375 */       float f1 = 0.016666668F * f;
/* 376 */       GlStateManager.pushMatrix();
/* 377 */       GlStateManager.translate((float)x + 0.0F, (float)y + ((Entity)entityIn).height + 0.5F, (float)z);
/* 378 */       GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 379 */       GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 380 */       GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 381 */       GlStateManager.scale(-f1, -f1, f1);
/* 382 */       GlStateManager.disableLighting();
/* 383 */       GlStateManager.depthMask(false);
/* 384 */       GlStateManager.disableDepth();
/* 385 */       GlStateManager.enableBlend();
/* 386 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 387 */       Tessellator tessellator = Tessellator.getInstance();
/* 388 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 389 */       int i = 0;
/*     */       
/* 391 */       if (str.equals("deadmau5"))
/*     */       {
/* 393 */         i = -10;
/*     */       }
/*     */       
/* 396 */       int j = fontrenderer.getStringWidth(str) / 2;
/* 397 */       GlStateManager.disableTexture2D();
/* 398 */       worldrenderer.begin(7, DefaultVertexFormats.field_181706_f);
/* 399 */       worldrenderer.pos((-j - 1), (-1 + i), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 400 */       worldrenderer.pos((-j - 1), (8 + i), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 401 */       worldrenderer.pos((j + 1), (8 + i), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 402 */       worldrenderer.pos((j + 1), (-1 + i), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 403 */       tessellator.draw();
/* 404 */       GlStateManager.enableTexture2D();
/* 405 */       fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, 553648127);
/* 406 */       GlStateManager.enableDepth();
/* 407 */       GlStateManager.depthMask(true);
/* 408 */       fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, -1);
/* 409 */       GlStateManager.enableLighting();
/* 410 */       GlStateManager.disableBlend();
/* 411 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 412 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderManager getRenderManager() {
/* 418 */     return this.renderManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMultipass() {
/* 423 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderMultipass(T p_renderMultipass_1_, double p_renderMultipass_2_, double p_renderMultipass_4_, double p_renderMultipass_6_, float p_renderMultipass_8_, float p_renderMultipass_9_) {}
/*     */ 
/*     */   
/*     */   public Class getEntityClass() {
/* 432 */     return this.entityClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEntityClass(Class p_setEntityClass_1_) {
/* 437 */     this.entityClass = p_setEntityClass_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getLocationTextureCustom() {
/* 442 */     return this.locationTextureCustom;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLocationTextureCustom(ResourceLocation p_setLocationTextureCustom_1_) {
/* 447 */     this.locationTextureCustom = p_setLocationTextureCustom_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setModelBipedMain(RenderBiped p_setModelBipedMain_0_, ModelBiped p_setModelBipedMain_1_) {
/* 452 */     p_setModelBipedMain_0_.modelBipedMain = p_setModelBipedMain_1_;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\Render.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
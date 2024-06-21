/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ChatFormatting;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.optifine.EmissiveTextures;
/*     */ import net.optifine.entity.model.CustomEntityModels;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public abstract class RendererLivingEntity<T extends EntityLivingBase>
/*     */   extends Render<T>
/*     */ {
/*  38 */   private static final Logger logger = LogManager.getLogger();
/*  39 */   private static final DynamicTexture field_177096_e = new DynamicTexture(16, 16);
/*     */   public ModelBase mainModel;
/*  41 */   protected FloatBuffer brightnessBuffer = GLAllocation.createDirectFloatBuffer(4);
/*  42 */   protected List<LayerRenderer<T>> layerRenderers = Lists.newArrayList();
/*     */   protected boolean renderOutlines = false;
/*  44 */   public static float NAME_TAG_RANGE = 64.0F;
/*  45 */   public static float NAME_TAG_RANGE_SNEAK = 32.0F;
/*     */   public EntityLivingBase renderEntity;
/*     */   public float renderLimbSwing;
/*     */   public float renderLimbSwingAmount;
/*     */   public float renderAgeInTicks;
/*     */   public float renderHeadYaw;
/*     */   public float renderHeadPitch;
/*     */   public float renderScaleFactor;
/*     */   public float renderPartialTicks;
/*     */   private boolean renderModelPushMatrix;
/*     */   private boolean renderLayersPushMatrix;
/*  56 */   public static final boolean animateModelLiving = Boolean.getBoolean("animate.model.living");
/*     */ 
/*     */   
/*     */   public RendererLivingEntity(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
/*  60 */     super(renderManagerIn);
/*  61 */     this.mainModel = modelBaseIn;
/*  62 */     this.shadowSize = shadowSizeIn;
/*  63 */     this.renderModelPushMatrix = this.mainModel instanceof net.minecraft.client.model.ModelSpider;
/*     */   }
/*     */ 
/*     */   
/*     */   public <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean addLayer(U layer) {
/*  68 */     return this.layerRenderers.add((LayerRenderer<T>)layer);
/*     */   }
/*     */ 
/*     */   
/*     */   protected <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean removeLayer(U layer) {
/*  73 */     return this.layerRenderers.remove(layer);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBase getMainModel() {
/*  78 */     return this.mainModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float interpolateRotation(float par1, float par2, float par3) {
/*     */     float f;
/*  90 */     for (f = par2 - par1; f < -180.0F; f += 360.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     while (f >= 180.0F)
/*     */     {
/*  97 */       f -= 360.0F;
/*     */     }
/*     */     
/* 100 */     return par1 + par3 * f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transformHeldFull3DItemLayer() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 117 */     if (!Reflector.RenderLivingEvent_Pre_Constructor.exists() || !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Pre_Constructor, new Object[] { entity, this, Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) })) {
/*     */       
/* 119 */       if (animateModelLiving)
/*     */       {
/* 121 */         ((EntityLivingBase)entity).limbSwingAmount = 1.0F;
/*     */       }
/*     */       
/* 124 */       GlStateManager.pushMatrix();
/* 125 */       GlStateManager.disableCull();
/* 126 */       this.mainModel.swingProgress = getSwingProgress(entity, partialTicks);
/* 127 */       this.mainModel.isRiding = entity.isRiding();
/*     */       
/* 129 */       if (Reflector.ForgeEntity_shouldRiderSit.exists())
/*     */       {
/* 131 */         this.mainModel.isRiding = (entity.isRiding() && ((EntityLivingBase)entity).ridingEntity != null && Reflector.callBoolean(((EntityLivingBase)entity).ridingEntity, Reflector.ForgeEntity_shouldRiderSit, new Object[0]));
/*     */       }
/*     */       
/* 134 */       this.mainModel.isChild = entity.isChild();
/*     */ 
/*     */       
/*     */       try {
/* 138 */         float f7, f = interpolateRotation(((EntityLivingBase)entity).prevRenderYawOffset, ((EntityLivingBase)entity).renderYawOffset, partialTicks);
/* 139 */         float f1 = interpolateRotation(((EntityLivingBase)entity).prevRotationYawHead, ((EntityLivingBase)entity).rotationYawHead, partialTicks);
/* 140 */         float f2 = f1 - f;
/*     */         
/* 142 */         if (this.mainModel.isRiding && ((EntityLivingBase)entity).ridingEntity instanceof EntityLivingBase) {
/*     */           
/* 144 */           EntityLivingBase entitylivingbase = (EntityLivingBase)((EntityLivingBase)entity).ridingEntity;
/* 145 */           f = interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
/* 146 */           f2 = f1 - f;
/* 147 */           float f3 = MathHelper.wrapAngleTo180_float(f2);
/*     */           
/* 149 */           if (f3 < -85.0F)
/*     */           {
/* 151 */             f3 = -85.0F;
/*     */           }
/*     */           
/* 154 */           if (f3 >= 85.0F)
/*     */           {
/* 156 */             f3 = 85.0F;
/*     */           }
/*     */           
/* 159 */           f = f1 - f3;
/*     */           
/* 161 */           if (f3 * f3 > 2500.0F)
/*     */           {
/* 163 */             f += f3 * 0.2F;
/*     */           }
/*     */           
/* 166 */           f2 = f1 - f;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 171 */         if (entity == (Minecraft.getMinecraft()).thePlayer) {
/* 172 */           f7 = ((EntityLivingBase)entity).prevRenderPitch + (((EntityLivingBase)entity).renderPitch - ((EntityLivingBase)entity).prevRenderPitch) * partialTicks;
/*     */         } else {
/* 174 */           f7 = ((EntityLivingBase)entity).prevRotationPitch + (((EntityLivingBase)entity).rotationPitch - ((EntityLivingBase)entity).prevRotationPitch) * partialTicks;
/*     */         } 
/* 176 */         renderLivingAt(entity, x, y, z);
/* 177 */         float f8 = handleRotationFloat(entity, partialTicks);
/* 178 */         rotateCorpse(entity, f8, f, partialTicks);
/* 179 */         GlStateManager.enableRescaleNormal();
/* 180 */         GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 181 */         preRenderCallback(entity, partialTicks);
/* 182 */         float f4 = 0.0625F;
/* 183 */         GlStateManager.translate(0.0F, -1.5078125F, 0.0F);
/* 184 */         float f5 = ((EntityLivingBase)entity).prevLimbSwingAmount + (((EntityLivingBase)entity).limbSwingAmount - ((EntityLivingBase)entity).prevLimbSwingAmount) * partialTicks;
/* 185 */         float f6 = ((EntityLivingBase)entity).limbSwing - ((EntityLivingBase)entity).limbSwingAmount * (1.0F - partialTicks);
/*     */         
/* 187 */         if (entity.isChild())
/*     */         {
/* 189 */           f6 *= 3.0F;
/*     */         }
/*     */         
/* 192 */         if (f5 > 1.0F)
/*     */         {
/* 194 */           f5 = 1.0F;
/*     */         }
/*     */         
/* 197 */         GlStateManager.enableAlpha();
/* 198 */         this.mainModel.setLivingAnimations((EntityLivingBase)entity, f6, f5, partialTicks);
/* 199 */         this.mainModel.setRotationAngles(f6, f5, f8, f2, f7, 0.0625F, (Entity)entity);
/*     */         
/* 201 */         if (CustomEntityModels.isActive()) {
/*     */           
/* 203 */           this.renderEntity = (EntityLivingBase)entity;
/* 204 */           this.renderLimbSwing = f6;
/* 205 */           this.renderLimbSwingAmount = f5;
/* 206 */           this.renderAgeInTicks = f8;
/* 207 */           this.renderHeadYaw = f2;
/* 208 */           this.renderHeadPitch = f7;
/* 209 */           this.renderScaleFactor = f4;
/* 210 */           this.renderPartialTicks = partialTicks;
/*     */         } 
/*     */         
/* 213 */         if (this.renderOutlines) {
/*     */           
/* 215 */           boolean flag1 = setScoreTeamColor(entity);
/* 216 */           renderModel(entity, f6, f5, f8, f2, f7, 0.0625F);
/*     */           
/* 218 */           if (flag1)
/*     */           {
/* 220 */             unsetScoreTeamColor();
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 225 */           boolean flag = setDoRenderBrightness(entity, partialTicks);
/*     */           
/* 227 */           if (EmissiveTextures.isActive())
/*     */           {
/* 229 */             EmissiveTextures.beginRender();
/*     */           }
/*     */           
/* 232 */           if (this.renderModelPushMatrix)
/*     */           {
/* 234 */             GlStateManager.pushMatrix();
/*     */           }
/*     */           
/* 237 */           renderModel(entity, f6, f5, f8, f2, f7, 0.0625F);
/*     */           
/* 239 */           if (this.renderModelPushMatrix)
/*     */           {
/* 241 */             GlStateManager.popMatrix();
/*     */           }
/*     */           
/* 244 */           if (EmissiveTextures.isActive()) {
/*     */             
/* 246 */             if (EmissiveTextures.hasEmissive()) {
/*     */               
/* 248 */               this.renderModelPushMatrix = true;
/* 249 */               EmissiveTextures.beginRenderEmissive();
/* 250 */               GlStateManager.pushMatrix();
/* 251 */               renderModel(entity, f6, f5, f8, f2, f7, f4);
/* 252 */               GlStateManager.popMatrix();
/* 253 */               EmissiveTextures.endRenderEmissive();
/*     */             } 
/*     */             
/* 256 */             EmissiveTextures.endRender();
/*     */           } 
/*     */           
/* 259 */           if (flag)
/*     */           {
/* 261 */             unsetBrightness();
/*     */           }
/*     */           
/* 264 */           GlStateManager.depthMask(true);
/*     */           
/* 266 */           if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator())
/*     */           {
/* 268 */             renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, 0.0625F);
/*     */           }
/*     */         } 
/*     */         
/* 272 */         if (CustomEntityModels.isActive())
/*     */         {
/* 274 */           this.renderEntity = null;
/*     */         }
/*     */         
/* 277 */         GlStateManager.disableRescaleNormal();
/*     */       }
/* 279 */       catch (Exception exception) {
/*     */         
/* 281 */         logger.error("Couldn't render entity", exception);
/*     */       } 
/*     */       
/* 284 */       GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 285 */       GlStateManager.enableTexture2D();
/* 286 */       GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 287 */       GlStateManager.enableCull();
/* 288 */       GlStateManager.popMatrix();
/*     */       
/* 290 */       if (!this.renderOutlines)
/*     */       {
/* 292 */         super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */       }
/*     */       
/* 295 */       if (Reflector.RenderLivingEvent_Post_Constructor.exists())
/*     */       {
/* 297 */         Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Post_Constructor, new Object[] { entity, this, Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) });
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean setScoreTeamColor(T entityLivingBaseIn) {
/* 304 */     int i = 16777215;
/*     */     
/* 306 */     if (entityLivingBaseIn instanceof EntityPlayer) {
/*     */       
/* 308 */       ScorePlayerTeam scoreplayerteam = (ScorePlayerTeam)entityLivingBaseIn.getTeam();
/*     */       
/* 310 */       if (scoreplayerteam != null) {
/*     */         
/* 312 */         String s = FontRenderer.getFormatFromString(scoreplayerteam.getColorPrefix());
/*     */         
/* 314 */         if (s.length() >= 2)
/*     */         {
/* 316 */           i = getFontRendererFromRenderManager().getColorCode(s.charAt(1));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 321 */     float f1 = (i >> 16 & 0xFF) / 255.0F;
/* 322 */     float f2 = (i >> 8 & 0xFF) / 255.0F;
/* 323 */     float f = (i & 0xFF) / 255.0F;
/* 324 */     GlStateManager.disableLighting();
/* 325 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 326 */     GlStateManager.color(f1, f2, f, 1.0F);
/* 327 */     GlStateManager.disableTexture2D();
/* 328 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 329 */     GlStateManager.disableTexture2D();
/* 330 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 331 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void unsetScoreTeamColor() {
/* 336 */     GlStateManager.enableLighting();
/* 337 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 338 */     GlStateManager.enableTexture2D();
/* 339 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 340 */     GlStateManager.enableTexture2D();
/* 341 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderModel(T entitylivingbaseIn, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_) {
/* 349 */     boolean flag = !entitylivingbaseIn.isInvisible();
/* 350 */     boolean flag1 = (!flag && !entitylivingbaseIn.isInvisibleToPlayer((EntityPlayer)(Minecraft.getMinecraft()).thePlayer));
/*     */     
/* 352 */     if (flag || flag1) {
/*     */       
/* 354 */       if (!bindEntityTexture(entitylivingbaseIn)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 359 */       if (flag1) {
/*     */         
/* 361 */         GlStateManager.pushMatrix();
/* 362 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
/* 363 */         GlStateManager.depthMask(false);
/* 364 */         GlStateManager.enableBlend();
/* 365 */         GlStateManager.blendFunc(770, 771);
/* 366 */         GlStateManager.alphaFunc(516, 0.003921569F);
/*     */       } 
/*     */       
/* 369 */       this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
/*     */       
/* 371 */       if (flag1) {
/*     */         
/* 373 */         GlStateManager.disableBlend();
/* 374 */         GlStateManager.alphaFunc(516, 0.1F);
/* 375 */         GlStateManager.popMatrix();
/* 376 */         GlStateManager.depthMask(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean setDoRenderBrightness(T entityLivingBaseIn, float partialTicks) {
/* 383 */     return setBrightness(entityLivingBaseIn, partialTicks, true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean setBrightness(T entitylivingbaseIn, float partialTicks, boolean combineTextures) {
/* 388 */     float f = entitylivingbaseIn.getBrightness(partialTicks);
/* 389 */     int i = getColorMultiplier(entitylivingbaseIn, f, partialTicks);
/* 390 */     boolean flag = ((i >> 24 & 0xFF) > 0);
/* 391 */     boolean flag1 = (((EntityLivingBase)entitylivingbaseIn).hurtTime > 0 || ((EntityLivingBase)entitylivingbaseIn).deathTime > 0);
/*     */     
/* 393 */     if (!flag && !flag1)
/*     */     {
/* 395 */       return false;
/*     */     }
/* 397 */     if (!flag && !combineTextures)
/*     */     {
/* 399 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 403 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 404 */     GlStateManager.enableTexture2D();
/* 405 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 406 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 407 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
/* 408 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
/* 409 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 410 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 411 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
/* 412 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
/* 413 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 414 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 415 */     GlStateManager.enableTexture2D();
/* 416 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 417 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
/* 418 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
/* 419 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
/* 420 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE2_RGB, OpenGlHelper.GL_CONSTANT);
/* 421 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 422 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 423 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND2_RGB, 770);
/* 424 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
/* 425 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
/* 426 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 427 */     this.brightnessBuffer.position(0);
/*     */     
/* 429 */     if (flag1) {
/*     */       
/* 431 */       this.brightnessBuffer.put(1.0F);
/* 432 */       this.brightnessBuffer.put(0.0F);
/* 433 */       this.brightnessBuffer.put(0.0F);
/* 434 */       this.brightnessBuffer.put(0.3F);
/*     */       
/* 436 */       if (Config.isShaders())
/*     */       {
/* 438 */         Shaders.setEntityColor(1.0F, 0.0F, 0.0F, 0.3F);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 443 */       float f1 = (i >> 24 & 0xFF) / 255.0F;
/* 444 */       float f2 = (i >> 16 & 0xFF) / 255.0F;
/* 445 */       float f3 = (i >> 8 & 0xFF) / 255.0F;
/* 446 */       float f4 = (i & 0xFF) / 255.0F;
/* 447 */       this.brightnessBuffer.put(f2);
/* 448 */       this.brightnessBuffer.put(f3);
/* 449 */       this.brightnessBuffer.put(f4);
/* 450 */       this.brightnessBuffer.put(1.0F - f1);
/*     */       
/* 452 */       if (Config.isShaders())
/*     */       {
/* 454 */         Shaders.setEntityColor(f2, f3, f4, 1.0F - f1);
/*     */       }
/*     */     } 
/*     */     
/* 458 */     this.brightnessBuffer.flip();
/* 459 */     GL11.glTexEnv(8960, 8705, this.brightnessBuffer);
/* 460 */     GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
/* 461 */     GlStateManager.enableTexture2D();
/* 462 */     GlStateManager.bindTexture(field_177096_e.getGlTextureId());
/* 463 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 464 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 465 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_PREVIOUS);
/* 466 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.lightmapTexUnit);
/* 467 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 468 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 469 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
/* 470 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
/* 471 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 472 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 473 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void unsetBrightness() {
/* 479 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 480 */     GlStateManager.enableTexture2D();
/* 481 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 482 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 483 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
/* 484 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
/* 485 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 486 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 487 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
/* 488 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
/* 489 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_ALPHA, OpenGlHelper.GL_PRIMARY_COLOR);
/* 490 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 491 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_ALPHA, 770);
/* 492 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 493 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 494 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 495 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 496 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 497 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
/* 498 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
/* 499 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
/* 500 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 501 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
/* 502 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 503 */     GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
/* 504 */     GlStateManager.disableTexture2D();
/* 505 */     GlStateManager.bindTexture(0);
/* 506 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 507 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 508 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 509 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 510 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
/* 511 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
/* 512 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
/* 513 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 514 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
/* 515 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*     */     
/* 517 */     if (Config.isShaders())
/*     */     {
/* 519 */       Shaders.setEntityColor(0.0F, 0.0F, 0.0F, 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderLivingAt(T entityLivingBaseIn, double x, double y, double z) {
/* 528 */     GlStateManager.translate((float)x, (float)y, (float)z);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void rotateCorpse(T bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 533 */     GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
/*     */     
/* 535 */     if (((EntityLivingBase)bat).deathTime > 0) {
/*     */       
/* 537 */       float f = (((EntityLivingBase)bat).deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
/* 538 */       f = MathHelper.sqrt_float(f);
/*     */       
/* 540 */       if (f > 1.0F)
/*     */       {
/* 542 */         f = 1.0F;
/*     */       }
/*     */       
/* 545 */       GlStateManager.rotate(f * getDeathMaxRotation(bat), 0.0F, 0.0F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/* 549 */       String s = ChatFormatting.getTextWithoutFormattingCodes(bat.getCommandSenderName());
/*     */       
/* 551 */       if (s != null && (s.equals("Dinnerbone") || s.equals("Grumm")) && (!(bat instanceof EntityPlayer) || ((EntityPlayer)bat).isWearing(EnumPlayerModelParts.CAPE))) {
/*     */         
/* 553 */         GlStateManager.translate(0.0F, ((EntityLivingBase)bat).height + 0.1F, 0.0F);
/* 554 */         GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSwingProgress(T livingBase, float partialTickTime) {
/* 564 */     return livingBase.getSwingProgress(partialTickTime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float handleRotationFloat(T livingBase, float partialTicks) {
/* 572 */     return ((EntityLivingBase)livingBase).ticksExisted + partialTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderLayers(T entitylivingbaseIn, float p_177093_2_, float p_177093_3_, float partialTicks, float p_177093_5_, float p_177093_6_, float p_177093_7_, float p_177093_8_) {
/* 577 */     for (LayerRenderer<T> layerrenderer : this.layerRenderers) {
/*     */       
/* 579 */       boolean flag = setBrightness(entitylivingbaseIn, partialTicks, layerrenderer.shouldCombineTextures());
/*     */       
/* 581 */       if (EmissiveTextures.isActive())
/*     */       {
/* 583 */         EmissiveTextures.beginRender();
/*     */       }
/*     */       
/* 586 */       if (this.renderLayersPushMatrix)
/*     */       {
/* 588 */         GlStateManager.pushMatrix();
/*     */       }
/*     */       
/* 591 */       layerrenderer.doRenderLayer((EntityLivingBase)entitylivingbaseIn, p_177093_2_, p_177093_3_, partialTicks, p_177093_5_, p_177093_6_, p_177093_7_, p_177093_8_);
/*     */       
/* 593 */       if (this.renderLayersPushMatrix)
/*     */       {
/* 595 */         GlStateManager.popMatrix();
/*     */       }
/*     */       
/* 598 */       if (EmissiveTextures.isActive()) {
/*     */         
/* 600 */         if (EmissiveTextures.hasEmissive()) {
/*     */           
/* 602 */           this.renderLayersPushMatrix = true;
/* 603 */           EmissiveTextures.beginRenderEmissive();
/* 604 */           GlStateManager.pushMatrix();
/* 605 */           layerrenderer.doRenderLayer((EntityLivingBase)entitylivingbaseIn, p_177093_2_, p_177093_3_, partialTicks, p_177093_5_, p_177093_6_, p_177093_7_, p_177093_8_);
/* 606 */           GlStateManager.popMatrix();
/* 607 */           EmissiveTextures.endRenderEmissive();
/*     */         } 
/*     */         
/* 610 */         EmissiveTextures.endRender();
/*     */       } 
/*     */       
/* 613 */       if (flag)
/*     */       {
/* 615 */         unsetBrightness();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getDeathMaxRotation(T entityLivingBaseIn) {
/* 622 */     return 90.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getColorMultiplier(T entitylivingbaseIn, float lightBrightness, float partialTickTime) {
/* 630 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preRenderCallback(T entitylivingbaseIn, float partialTickTime) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderName(T entity, double x, double y, double z) {
/* 643 */     if (!Reflector.RenderLivingEvent_Specials_Pre_Constructor.exists() || !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Specials_Pre_Constructor, new Object[] { entity, this, Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) })) {
/*     */       
/* 645 */       if (canRenderName(entity)) {
/*     */         
/* 647 */         double d0 = entity.getDistanceSqToEntity(this.renderManager.livingPlayer);
/* 648 */         float f = entity.isSneaking() ? NAME_TAG_RANGE_SNEAK : NAME_TAG_RANGE;
/*     */         
/* 650 */         if (d0 < (f * f)) {
/*     */           
/* 652 */           String s = entity.getDisplayName().getFormattedText();
/* 653 */           float f1 = 0.02666667F;
/* 654 */           GlStateManager.alphaFunc(516, 0.1F);
/*     */           
/* 656 */           if (entity.isSneaking()) {
/*     */             
/* 658 */             FontRenderer fontrenderer = getFontRendererFromRenderManager();
/* 659 */             GlStateManager.pushMatrix();
/* 660 */             GlStateManager.translate((float)x, (float)y + ((EntityLivingBase)entity).height + 0.5F - (entity.isChild() ? (((EntityLivingBase)entity).height / 2.0F) : 0.0F), (float)z);
/* 661 */             GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 662 */             GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 663 */             GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 664 */             GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
/* 665 */             GlStateManager.translate(0.0F, 9.374999F, 0.0F);
/* 666 */             GlStateManager.disableLighting();
/* 667 */             GlStateManager.depthMask(false);
/* 668 */             GlStateManager.enableBlend();
/* 669 */             GlStateManager.disableTexture2D();
/* 670 */             GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 671 */             int i = fontrenderer.getStringWidth(s) / 2;
/* 672 */             Tessellator tessellator = Tessellator.getInstance();
/* 673 */             WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 674 */             worldrenderer.begin(7, DefaultVertexFormats.field_181706_f);
/* 675 */             worldrenderer.pos((-i - 1), -1.0D, 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 676 */             worldrenderer.pos((-i - 1), 8.0D, 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 677 */             worldrenderer.pos((i + 1), 8.0D, 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 678 */             worldrenderer.pos((i + 1), -1.0D, 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 679 */             tessellator.draw();
/* 680 */             GlStateManager.enableTexture2D();
/* 681 */             GlStateManager.depthMask(true);
/* 682 */             fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 553648127);
/* 683 */             GlStateManager.enableLighting();
/* 684 */             GlStateManager.disableBlend();
/* 685 */             GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 686 */             GlStateManager.popMatrix();
/*     */           }
/*     */           else {
/*     */             
/* 690 */             renderOffsetLivingLabel(entity, x, y - (entity.isChild() ? (((EntityLivingBase)entity).height / 2.0F) : 0.0D), z, s, 0.02666667F, d0);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 695 */       if (Reflector.RenderLivingEvent_Specials_Post_Constructor.exists())
/*     */       {
/* 697 */         Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Specials_Post_Constructor, new Object[] { entity, this, Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) });
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canRenderName(T entity) {
/* 704 */     EntityPlayerSP entityplayersp = (Minecraft.getMinecraft()).thePlayer;
/*     */     
/* 706 */     if (entity instanceof EntityPlayer && entity != entityplayersp) {
/*     */       
/* 708 */       Team team = entity.getTeam();
/* 709 */       Team team1 = entityplayersp.getTeam();
/*     */       
/* 711 */       if (team != null) {
/*     */         
/* 713 */         Team.EnumVisible team$enumvisible = team.getNameTagVisibility();
/*     */         
/* 715 */         switch (team$enumvisible) {
/*     */           
/*     */           case ALWAYS:
/* 718 */             return true;
/*     */           
/*     */           case NEVER:
/* 721 */             return false;
/*     */           
/*     */           case HIDE_FOR_OTHER_TEAMS:
/* 724 */             return (team1 == null || team.isSameTeam(team1));
/*     */           
/*     */           case HIDE_FOR_OWN_TEAM:
/* 727 */             return (team1 == null || !team.isSameTeam(team1));
/*     */         } 
/*     */         
/* 730 */         return true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 735 */     return (Minecraft.isGuiEnabled() && entity != this.renderManager.livingPlayer && !entity.isInvisibleToPlayer((EntityPlayer)entityplayersp) && ((EntityLivingBase)entity).riddenByEntity == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRenderOutlines(boolean renderOutlinesIn) {
/* 740 */     this.renderOutlines = renderOutlinesIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<LayerRenderer<T>> getLayerRenderers() {
/* 745 */     return this.layerRenderers;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 750 */     int[] aint = field_177096_e.getTextureData();
/*     */     
/* 752 */     for (int i = 0; i < 256; i++)
/*     */     {
/* 754 */       aint[i] = -1;
/*     */     }
/*     */     
/* 757 */     field_177096_e.updateDynamicTexture();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\RendererLivingEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
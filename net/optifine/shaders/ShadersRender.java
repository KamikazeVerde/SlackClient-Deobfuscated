/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.ItemRenderer;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.culling.ClippingHelper;
/*     */ import net.minecraft.client.renderer.culling.Frustum;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntityEndPortal;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import org.lwjgl.opengl.EXTFramebufferObject;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ import org.lwjgl.opengl.GL30;
/*     */ 
/*     */ public class ShadersRender
/*     */ {
/*  31 */   private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
/*     */ 
/*     */   
/*     */   public static void setFrustrumPosition(ICamera frustum, double x, double y, double z) {
/*  35 */     frustum.setPosition(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setupTerrain(RenderGlobal renderGlobal, Entity viewEntity, double partialTicks, ICamera camera, int frameCount, boolean playerSpectator) {
/*  40 */     renderGlobal.setupTerrain(viewEntity, partialTicks, camera, frameCount, playerSpectator);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginTerrainSolid() {
/*  45 */     if (Shaders.isRenderingWorld) {
/*     */       
/*  47 */       Shaders.fogEnabled = true;
/*  48 */       Shaders.useProgram(Shaders.ProgramTerrain);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginTerrainCutoutMipped() {
/*  54 */     if (Shaders.isRenderingWorld)
/*     */     {
/*  56 */       Shaders.useProgram(Shaders.ProgramTerrain);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginTerrainCutout() {
/*  62 */     if (Shaders.isRenderingWorld)
/*     */     {
/*  64 */       Shaders.useProgram(Shaders.ProgramTerrain);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endTerrain() {
/*  70 */     if (Shaders.isRenderingWorld)
/*     */     {
/*  72 */       Shaders.useProgram(Shaders.ProgramTexturedLit);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginTranslucent() {
/*  78 */     if (Shaders.isRenderingWorld) {
/*     */       
/*  80 */       if (Shaders.usedDepthBuffers >= 2) {
/*     */         
/*  82 */         GlStateManager.setActiveTexture(33995);
/*  83 */         Shaders.checkGLError("pre copy depth");
/*  84 */         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.renderWidth, Shaders.renderHeight);
/*  85 */         Shaders.checkGLError("copy depth");
/*  86 */         GlStateManager.setActiveTexture(33984);
/*     */       } 
/*     */       
/*  89 */       Shaders.useProgram(Shaders.ProgramWater);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endTranslucent() {
/*  95 */     if (Shaders.isRenderingWorld)
/*     */     {
/*  97 */       Shaders.useProgram(Shaders.ProgramTexturedLit);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderHand0(EntityRenderer er, float par1, int par2) {
/* 103 */     if (!Shaders.isShadowPass) {
/*     */       
/* 105 */       boolean flag = Shaders.isItemToRenderMainTranslucent();
/* 106 */       boolean flag1 = Shaders.isItemToRenderOffTranslucent();
/*     */       
/* 108 */       if (!flag || !flag1) {
/*     */         
/* 110 */         Shaders.readCenterDepth();
/* 111 */         Shaders.beginHand(false);
/* 112 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 113 */         Shaders.setSkipRenderHands(flag, flag1);
/* 114 */         er.renderHand(par1, par2, true, false, false);
/* 115 */         Shaders.endHand();
/* 116 */         Shaders.setHandsRendered(!flag, !flag1);
/* 117 */         Shaders.setSkipRenderHands(false, false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderHand1(EntityRenderer er, float par1, int par2) {
/* 124 */     if (!Shaders.isShadowPass && !Shaders.isBothHandsRendered()) {
/*     */       
/* 126 */       Shaders.readCenterDepth();
/* 127 */       GlStateManager.enableBlend();
/* 128 */       Shaders.beginHand(true);
/* 129 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 130 */       Shaders.setSkipRenderHands(Shaders.isHandRenderedMain(), Shaders.isHandRenderedOff());
/* 131 */       er.renderHand(par1, par2, true, false, true);
/* 132 */       Shaders.endHand();
/* 133 */       Shaders.setHandsRendered(true, true);
/* 134 */       Shaders.setSkipRenderHands(false, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderItemFP(ItemRenderer itemRenderer, float par1, boolean renderTranslucent) {
/* 140 */     Shaders.setRenderingFirstPersonHand(true);
/* 141 */     GlStateManager.depthMask(true);
/*     */     
/* 143 */     if (renderTranslucent) {
/*     */       
/* 145 */       GlStateManager.depthFunc(519);
/* 146 */       GL11.glPushMatrix();
/* 147 */       IntBuffer intbuffer = Shaders.activeDrawBuffers;
/* 148 */       Shaders.setDrawBuffers(Shaders.drawBuffersNone);
/* 149 */       Shaders.renderItemKeepDepthMask = true;
/* 150 */       itemRenderer.renderItemInFirstPerson(par1);
/* 151 */       Shaders.renderItemKeepDepthMask = false;
/* 152 */       Shaders.setDrawBuffers(intbuffer);
/* 153 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/* 156 */     GlStateManager.depthFunc(515);
/* 157 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 158 */     itemRenderer.renderItemInFirstPerson(par1);
/* 159 */     Shaders.setRenderingFirstPersonHand(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderFPOverlay(EntityRenderer er, float par1, int par2) {
/* 164 */     if (!Shaders.isShadowPass) {
/*     */       
/* 166 */       Shaders.beginFPOverlay();
/* 167 */       er.renderHand(par1, par2, false, true, false);
/* 168 */       Shaders.endFPOverlay();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginBlockDamage() {
/* 174 */     if (Shaders.isRenderingWorld) {
/*     */       
/* 176 */       Shaders.useProgram(Shaders.ProgramDamagedBlock);
/*     */       
/* 178 */       if (Shaders.ProgramDamagedBlock.getId() == Shaders.ProgramTerrain.getId()) {
/*     */         
/* 180 */         Shaders.setDrawBuffers(Shaders.drawBuffersColorAtt0);
/* 181 */         GlStateManager.depthMask(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endBlockDamage() {
/* 188 */     if (Shaders.isRenderingWorld) {
/*     */       
/* 190 */       GlStateManager.depthMask(true);
/* 191 */       Shaders.useProgram(Shaders.ProgramTexturedLit);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderShadowMap(EntityRenderer entityRenderer, int pass, float partialTicks, long finishTimeNano) {
/* 197 */     if (Shaders.usedShadowDepthBuffers > 0 && --Shaders.shadowPassCounter <= 0) {
/*     */       
/* 199 */       Minecraft minecraft = Minecraft.getMinecraft();
/* 200 */       minecraft.mcProfiler.endStartSection("shadow pass");
/* 201 */       RenderGlobal renderglobal = minecraft.renderGlobal;
/* 202 */       Shaders.isShadowPass = true;
/* 203 */       Shaders.shadowPassCounter = Shaders.shadowPassInterval;
/* 204 */       Shaders.preShadowPassThirdPersonView = minecraft.gameSettings.thirdPersonView;
/* 205 */       minecraft.gameSettings.thirdPersonView = 1;
/* 206 */       Shaders.checkGLError("pre shadow");
/* 207 */       GL11.glMatrixMode(5889);
/* 208 */       GL11.glPushMatrix();
/* 209 */       GL11.glMatrixMode(5888);
/* 210 */       GL11.glPushMatrix();
/* 211 */       minecraft.mcProfiler.endStartSection("shadow clear");
/* 212 */       EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.sfb);
/* 213 */       Shaders.checkGLError("shadow bind sfb");
/* 214 */       minecraft.mcProfiler.endStartSection("shadow camera");
/* 215 */       entityRenderer.setupCameraTransform(partialTicks, 2);
/* 216 */       Shaders.setCameraShadow(partialTicks);
/* 217 */       Shaders.checkGLError("shadow camera");
/* 218 */       Shaders.useProgram(Shaders.ProgramShadow);
/* 219 */       GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
/* 220 */       Shaders.checkGLError("shadow drawbuffers");
/* 221 */       GL11.glReadBuffer(0);
/* 222 */       Shaders.checkGLError("shadow readbuffer");
/* 223 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, Shaders.sfbDepthTextures.get(0), 0);
/*     */       
/* 225 */       if (Shaders.usedShadowColorBuffers != 0)
/*     */       {
/* 227 */         EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, Shaders.sfbColorTextures.get(0), 0);
/*     */       }
/*     */       
/* 230 */       Shaders.checkFramebufferStatus("shadow fb");
/* 231 */       GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 232 */       GL11.glClear((Shaders.usedShadowColorBuffers != 0) ? 16640 : 256);
/* 233 */       Shaders.checkGLError("shadow clear");
/* 234 */       minecraft.mcProfiler.endStartSection("shadow frustum");
/* 235 */       ClippingHelper clippinghelper = ClippingHelperShadow.getInstance();
/* 236 */       minecraft.mcProfiler.endStartSection("shadow culling");
/* 237 */       Frustum frustum = new Frustum(clippinghelper);
/* 238 */       Entity entity = minecraft.getRenderViewEntity();
/* 239 */       double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 240 */       double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 241 */       double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 242 */       frustum.setPosition(d0, d1, d2);
/* 243 */       GlStateManager.shadeModel(7425);
/* 244 */       GlStateManager.enableDepth();
/* 245 */       GlStateManager.depthFunc(515);
/* 246 */       GlStateManager.depthMask(true);
/* 247 */       GlStateManager.colorMask(true, true, true, true);
/* 248 */       GlStateManager.disableCull();
/* 249 */       minecraft.mcProfiler.endStartSection("shadow prepareterrain");
/* 250 */       minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 251 */       minecraft.mcProfiler.endStartSection("shadow setupterrain");
/* 252 */       int i = 0;
/* 253 */       i = entityRenderer.frameCount;
/* 254 */       entityRenderer.frameCount = i + 1;
/* 255 */       renderglobal.setupTerrain(entity, partialTicks, (ICamera)frustum, i, minecraft.thePlayer.isSpectator());
/* 256 */       minecraft.mcProfiler.endStartSection("shadow updatechunks");
/* 257 */       minecraft.mcProfiler.endStartSection("shadow terrain");
/* 258 */       GlStateManager.matrixMode(5888);
/* 259 */       GlStateManager.pushMatrix();
/* 260 */       GlStateManager.disableAlpha();
/* 261 */       renderglobal.renderBlockLayer(EnumWorldBlockLayer.SOLID, partialTicks, 2, entity);
/* 262 */       Shaders.checkGLError("shadow terrain solid");
/* 263 */       GlStateManager.enableAlpha();
/* 264 */       renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT_MIPPED, partialTicks, 2, entity);
/* 265 */       Shaders.checkGLError("shadow terrain cutoutmipped");
/* 266 */       minecraft.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
/* 267 */       renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT, partialTicks, 2, entity);
/* 268 */       Shaders.checkGLError("shadow terrain cutout");
/* 269 */       minecraft.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/* 270 */       GlStateManager.shadeModel(7424);
/* 271 */       GlStateManager.alphaFunc(516, 0.1F);
/* 272 */       GlStateManager.matrixMode(5888);
/* 273 */       GlStateManager.popMatrix();
/* 274 */       GlStateManager.pushMatrix();
/* 275 */       minecraft.mcProfiler.endStartSection("shadow entities");
/*     */       
/* 277 */       if (Reflector.ForgeHooksClient_setRenderPass.exists())
/*     */       {
/* 279 */         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(0) });
/*     */       }
/*     */       
/* 282 */       renderglobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/* 283 */       Shaders.checkGLError("shadow entities");
/* 284 */       GlStateManager.matrixMode(5888);
/* 285 */       GlStateManager.popMatrix();
/* 286 */       GlStateManager.depthMask(true);
/* 287 */       GlStateManager.disableBlend();
/* 288 */       GlStateManager.enableCull();
/* 289 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 290 */       GlStateManager.alphaFunc(516, 0.1F);
/*     */       
/* 292 */       if (Shaders.usedShadowDepthBuffers >= 2) {
/*     */         
/* 294 */         GlStateManager.setActiveTexture(33989);
/* 295 */         Shaders.checkGLError("pre copy shadow depth");
/* 296 */         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.shadowMapWidth, Shaders.shadowMapHeight);
/* 297 */         Shaders.checkGLError("copy shadow depth");
/* 298 */         GlStateManager.setActiveTexture(33984);
/*     */       } 
/*     */       
/* 301 */       GlStateManager.disableBlend();
/* 302 */       GlStateManager.depthMask(true);
/* 303 */       minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 304 */       GlStateManager.shadeModel(7425);
/* 305 */       Shaders.checkGLError("shadow pre-translucent");
/* 306 */       GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
/* 307 */       Shaders.checkGLError("shadow drawbuffers pre-translucent");
/* 308 */       Shaders.checkFramebufferStatus("shadow pre-translucent");
/*     */       
/* 310 */       if (Shaders.isRenderShadowTranslucent()) {
/*     */         
/* 312 */         minecraft.mcProfiler.endStartSection("shadow translucent");
/* 313 */         renderglobal.renderBlockLayer(EnumWorldBlockLayer.TRANSLUCENT, partialTicks, 2, entity);
/* 314 */         Shaders.checkGLError("shadow translucent");
/*     */       } 
/*     */       
/* 317 */       if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
/*     */         
/* 319 */         RenderHelper.enableStandardItemLighting();
/* 320 */         Reflector.call(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(1) });
/* 321 */         renderglobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/* 322 */         Reflector.call(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
/* 323 */         RenderHelper.disableStandardItemLighting();
/* 324 */         Shaders.checkGLError("shadow entities 1");
/*     */       } 
/*     */       
/* 327 */       GlStateManager.shadeModel(7424);
/* 328 */       GlStateManager.depthMask(true);
/* 329 */       GlStateManager.enableCull();
/* 330 */       GlStateManager.disableBlend();
/* 331 */       GL11.glFlush();
/* 332 */       Shaders.checkGLError("shadow flush");
/* 333 */       Shaders.isShadowPass = false;
/* 334 */       minecraft.gameSettings.thirdPersonView = Shaders.preShadowPassThirdPersonView;
/* 335 */       minecraft.mcProfiler.endStartSection("shadow postprocess");
/*     */       
/* 337 */       if (Shaders.hasGlGenMipmap) {
/*     */         
/* 339 */         if (Shaders.usedShadowDepthBuffers >= 1) {
/*     */           
/* 341 */           if (Shaders.shadowMipmapEnabled[0]) {
/*     */             
/* 343 */             GlStateManager.setActiveTexture(33988);
/* 344 */             GlStateManager.bindTexture(Shaders.sfbDepthTextures.get(0));
/* 345 */             GL30.glGenerateMipmap(3553);
/* 346 */             GL11.glTexParameteri(3553, 10241, Shaders.shadowFilterNearest[0] ? 9984 : 9987);
/*     */           } 
/*     */           
/* 349 */           if (Shaders.usedShadowDepthBuffers >= 2 && Shaders.shadowMipmapEnabled[1]) {
/*     */             
/* 351 */             GlStateManager.setActiveTexture(33989);
/* 352 */             GlStateManager.bindTexture(Shaders.sfbDepthTextures.get(1));
/* 353 */             GL30.glGenerateMipmap(3553);
/* 354 */             GL11.glTexParameteri(3553, 10241, Shaders.shadowFilterNearest[1] ? 9984 : 9987);
/*     */           } 
/*     */           
/* 357 */           GlStateManager.setActiveTexture(33984);
/*     */         } 
/*     */         
/* 360 */         if (Shaders.usedShadowColorBuffers >= 1) {
/*     */           
/* 362 */           if (Shaders.shadowColorMipmapEnabled[0]) {
/*     */             
/* 364 */             GlStateManager.setActiveTexture(33997);
/* 365 */             GlStateManager.bindTexture(Shaders.sfbColorTextures.get(0));
/* 366 */             GL30.glGenerateMipmap(3553);
/* 367 */             GL11.glTexParameteri(3553, 10241, Shaders.shadowColorFilterNearest[0] ? 9984 : 9987);
/*     */           } 
/*     */           
/* 370 */           if (Shaders.usedShadowColorBuffers >= 2 && Shaders.shadowColorMipmapEnabled[1]) {
/*     */             
/* 372 */             GlStateManager.setActiveTexture(33998);
/* 373 */             GlStateManager.bindTexture(Shaders.sfbColorTextures.get(1));
/* 374 */             GL30.glGenerateMipmap(3553);
/* 375 */             GL11.glTexParameteri(3553, 10241, Shaders.shadowColorFilterNearest[1] ? 9984 : 9987);
/*     */           } 
/*     */           
/* 378 */           GlStateManager.setActiveTexture(33984);
/*     */         } 
/*     */       } 
/*     */       
/* 382 */       Shaders.checkGLError("shadow postprocess");
/* 383 */       EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.dfb);
/* 384 */       GL11.glViewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
/* 385 */       Shaders.activeDrawBuffers = null;
/* 386 */       minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 387 */       Shaders.useProgram(Shaders.ProgramTerrain);
/* 388 */       GL11.glMatrixMode(5888);
/* 389 */       GL11.glPopMatrix();
/* 390 */       GL11.glMatrixMode(5889);
/* 391 */       GL11.glPopMatrix();
/* 392 */       GL11.glMatrixMode(5888);
/* 393 */       Shaders.checkGLError("shadow end");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void preRenderChunkLayer(EnumWorldBlockLayer blockLayerIn) {
/* 399 */     if (Shaders.isRenderBackFace(blockLayerIn))
/*     */     {
/* 401 */       GlStateManager.disableCull();
/*     */     }
/*     */     
/* 404 */     if (OpenGlHelper.useVbo()) {
/*     */       
/* 406 */       GL11.glEnableClientState(32885);
/* 407 */       GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
/* 408 */       GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
/* 409 */       GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void postRenderChunkLayer(EnumWorldBlockLayer blockLayerIn) {
/* 415 */     if (OpenGlHelper.useVbo()) {
/*     */       
/* 417 */       GL11.glDisableClientState(32885);
/* 418 */       GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
/* 419 */       GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
/* 420 */       GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
/*     */     } 
/*     */     
/* 423 */     if (Shaders.isRenderBackFace(blockLayerIn))
/*     */     {
/* 425 */       GlStateManager.enableCull();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setupArrayPointersVbo() {
/* 431 */     int i = 14;
/* 432 */     GL11.glVertexPointer(3, 5126, 56, 0L);
/* 433 */     GL11.glColorPointer(4, 5121, 56, 12L);
/* 434 */     GL11.glTexCoordPointer(2, 5126, 56, 16L);
/* 435 */     OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 436 */     GL11.glTexCoordPointer(2, 5122, 56, 24L);
/* 437 */     OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/* 438 */     GL11.glNormalPointer(5120, 56, 28L);
/* 439 */     GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, 56, 32L);
/* 440 */     GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, 56, 40L);
/* 441 */     GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, 56, 48L);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beaconBeamBegin() {
/* 446 */     Shaders.useProgram(Shaders.ProgramBeaconBeam);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void beaconBeamStartQuad1() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public static void beaconBeamStartQuad2() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public static void beaconBeamDraw1() {}
/*     */ 
/*     */   
/*     */   public static void beaconBeamDraw2() {
/* 463 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderEnchantedGlintBegin() {
/* 468 */     Shaders.useProgram(Shaders.ProgramArmorGlint);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderEnchantedGlintEnd() {
/* 473 */     if (Shaders.isRenderingWorld) {
/*     */       
/* 475 */       if (Shaders.isRenderingFirstPersonHand() && Shaders.isRenderBothHands())
/*     */       {
/* 477 */         Shaders.useProgram(Shaders.ProgramHand);
/*     */       }
/*     */       else
/*     */       {
/* 481 */         Shaders.useProgram(Shaders.ProgramEntities);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 486 */       Shaders.useProgram(Shaders.ProgramNone);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean renderEndPortal(TileEntityEndPortal te, double x, double y, double z, float partialTicks, int destroyStage, float offset) {
/* 492 */     if (!Shaders.isShadowPass && Shaders.activeProgram.getId() == 0)
/*     */     {
/* 494 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 498 */     GlStateManager.disableLighting();
/* 499 */     Config.getTextureManager().bindTexture(END_PORTAL_TEXTURE);
/* 500 */     Tessellator tessellator = Tessellator.getInstance();
/* 501 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 502 */     worldrenderer.begin(7, DefaultVertexFormats.BLOCK);
/* 503 */     float f = 0.5F;
/* 504 */     float f1 = f * 0.15F;
/* 505 */     float f2 = f * 0.3F;
/* 506 */     float f3 = f * 0.4F;
/* 507 */     float f4 = 0.0F;
/* 508 */     float f5 = 0.2F;
/* 509 */     float f6 = (float)(System.currentTimeMillis() % 100000L) / 100000.0F;
/* 510 */     int i = 240;
/* 511 */     worldrenderer.pos(x, y + offset, z + 1.0D).func_181666_a(f1, f2, f3, 1.0F).tex((f4 + f6), (f4 + f6)).func_181671_a(i, i).endVertex();
/* 512 */     worldrenderer.pos(x + 1.0D, y + offset, z + 1.0D).func_181666_a(f1, f2, f3, 1.0F).tex((f4 + f6), (f5 + f6)).func_181671_a(i, i).endVertex();
/* 513 */     worldrenderer.pos(x + 1.0D, y + offset, z).func_181666_a(f1, f2, f3, 1.0F).tex((f5 + f6), (f5 + f6)).func_181671_a(i, i).endVertex();
/* 514 */     worldrenderer.pos(x, y + offset, z).func_181666_a(f1, f2, f3, 1.0F).tex((f5 + f6), (f4 + f6)).func_181671_a(i, i).endVertex();
/* 515 */     tessellator.draw();
/* 516 */     GlStateManager.enableLighting();
/* 517 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\ShadersRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
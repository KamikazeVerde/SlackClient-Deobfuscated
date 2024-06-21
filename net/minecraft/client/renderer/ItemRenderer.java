/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import cc.slack.Slack;
/*     */ import cc.slack.features.modules.impl.combat.KillAura;
/*     */ import cc.slack.features.modules.impl.render.Animations;
/*     */ import cc.slack.utils.player.ItemSpoofUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ import net.optifine.DynamicLights;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ItemRenderer {
/*  41 */   private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
/*  42 */   private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
/*     */ 
/*     */   
/*     */   private final Minecraft mc;
/*     */   
/*     */   private ItemStack itemToRender;
/*     */   
/*     */   private float equippedProgress;
/*     */   
/*     */   private float prevEquippedProgress;
/*     */   
/*     */   private final RenderManager renderManager;
/*     */   
/*     */   private final RenderItem itemRenderer;
/*     */   
/*  57 */   private int equippedItemSlot = -1;
/*     */ 
/*     */   
/*     */   public ItemRenderer(Minecraft mcIn) {
/*  61 */     this.mc = mcIn;
/*  62 */     this.renderManager = mcIn.getRenderManager();
/*  63 */     this.itemRenderer = mcIn.getRenderItem();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderItem(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform) {
/*  68 */     if (heldStack != null) {
/*     */       
/*  70 */       Item item = heldStack.getItem();
/*  71 */       Block block = Block.getBlockFromItem(item);
/*  72 */       GlStateManager.pushMatrix();
/*     */       
/*  74 */       if (this.itemRenderer.shouldRenderItemIn3D(heldStack)) {
/*     */         
/*  76 */         GlStateManager.scale(2.0F, 2.0F, 2.0F);
/*     */         
/*  78 */         if (isBlockTranslucent(block) && (!Config.isShaders() || !Shaders.renderItemKeepDepthMask))
/*     */         {
/*  80 */           GlStateManager.depthMask(false);
/*     */         }
/*     */       } 
/*     */       
/*  84 */       this.itemRenderer.renderItemModelForEntity(heldStack, entityIn, transform);
/*     */       
/*  86 */       if (isBlockTranslucent(block))
/*     */       {
/*  88 */         GlStateManager.depthMask(true);
/*     */       }
/*     */       
/*  91 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isBlockTranslucent(Block blockIn) {
/* 100 */     return (blockIn != null && blockIn.getBlockLayer() == EnumWorldBlockLayer.TRANSLUCENT);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178101_a(float angle, float p_178101_2_) {
/* 105 */     GlStateManager.pushMatrix();
/* 106 */     GlStateManager.rotate(angle, 1.0F, 0.0F, 0.0F);
/* 107 */     GlStateManager.rotate(p_178101_2_, 0.0F, 1.0F, 0.0F);
/* 108 */     RenderHelper.enableStandardItemLighting();
/* 109 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178109_a(AbstractClientPlayer clientPlayer) {
/* 114 */     int i = this.mc.theWorld.getCombinedLight(new BlockPos(clientPlayer.posX, clientPlayer.posY + clientPlayer.getEyeHeight(), clientPlayer.posZ), 0);
/*     */     
/* 116 */     if (Config.isDynamicLights())
/*     */     {
/* 118 */       i = DynamicLights.getCombinedLight(this.mc.getRenderViewEntity(), i);
/*     */     }
/*     */     
/* 121 */     float f = (i & 0xFFFF);
/* 122 */     float f1 = (i >> 16);
/* 123 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178110_a(EntityPlayerSP entityplayerspIn, float partialTicks) {
/* 128 */     float f = entityplayerspIn.prevRenderArmPitch + (entityplayerspIn.renderArmPitch - entityplayerspIn.prevRenderArmPitch) * partialTicks;
/* 129 */     float f1 = entityplayerspIn.prevRenderArmYaw + (entityplayerspIn.renderArmYaw - entityplayerspIn.prevRenderArmYaw) * partialTicks;
/* 130 */     GlStateManager.rotate((entityplayerspIn.rotationPitch - f) * 0.1F, 1.0F, 0.0F, 0.0F);
/* 131 */     GlStateManager.rotate((entityplayerspIn.rotationYaw - f1) * 0.1F, 0.0F, 1.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private float func_178100_c(float p_178100_1_) {
/* 136 */     float f = 1.0F - p_178100_1_ / 45.0F + 0.1F;
/* 137 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 138 */     f = -MathHelper.cos(f * 3.1415927F) * 0.5F + 0.5F;
/* 139 */     return f;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderRightArm(RenderPlayer renderPlayerIn) {
/* 144 */     GlStateManager.pushMatrix();
/* 145 */     GlStateManager.rotate(54.0F, 0.0F, 1.0F, 0.0F);
/* 146 */     GlStateManager.rotate(64.0F, 1.0F, 0.0F, 0.0F);
/* 147 */     GlStateManager.rotate(-62.0F, 0.0F, 0.0F, 1.0F);
/* 148 */     GlStateManager.translate(0.25F, -0.85F, 0.75F);
/* 149 */     renderPlayerIn.renderRightArm((AbstractClientPlayer)this.mc.thePlayer);
/* 150 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderLeftArm(RenderPlayer renderPlayerIn) {
/* 155 */     GlStateManager.pushMatrix();
/* 156 */     GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
/* 157 */     GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
/* 158 */     GlStateManager.rotate(41.0F, 0.0F, 0.0F, 1.0F);
/* 159 */     GlStateManager.translate(-0.3F, -1.1F, 0.45F);
/* 160 */     renderPlayerIn.renderLeftArm((AbstractClientPlayer)this.mc.thePlayer);
/* 161 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderPlayerArms(AbstractClientPlayer clientPlayer) {
/* 166 */     this.mc.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
/* 167 */     Render<AbstractClientPlayer> render = this.renderManager.getEntityRenderObject((Entity)this.mc.thePlayer);
/* 168 */     RenderPlayer renderplayer = (RenderPlayer)render;
/*     */     
/* 170 */     if (!clientPlayer.isInvisible()) {
/*     */       
/* 172 */       GlStateManager.disableCull();
/* 173 */       renderRightArm(renderplayer);
/* 174 */       renderLeftArm(renderplayer);
/* 175 */       GlStateManager.enableCull();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderItemMap(AbstractClientPlayer clientPlayer, float p_178097_2_, float p_178097_3_, float p_178097_4_) {
/* 181 */     float f = -0.4F * MathHelper.sin(MathHelper.sqrt_float(p_178097_4_) * 3.1415927F);
/* 182 */     float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(p_178097_4_) * 3.1415927F * 2.0F);
/* 183 */     float f2 = -0.2F * MathHelper.sin(p_178097_4_ * 3.1415927F);
/* 184 */     GlStateManager.translate(f, f1, f2);
/* 185 */     float f3 = func_178100_c(p_178097_2_);
/* 186 */     GlStateManager.translate(0.0F, 0.04F, -0.72F);
/* 187 */     GlStateManager.translate(0.0F, p_178097_3_ * -1.2F, 0.0F);
/* 188 */     GlStateManager.translate(0.0F, f3 * -0.5F, 0.0F);
/* 189 */     GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/* 190 */     GlStateManager.rotate(f3 * -85.0F, 0.0F, 0.0F, 1.0F);
/* 191 */     GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
/* 192 */     renderPlayerArms(clientPlayer);
/* 193 */     float f4 = MathHelper.sin(p_178097_4_ * p_178097_4_ * 3.1415927F);
/* 194 */     float f5 = MathHelper.sin(MathHelper.sqrt_float(p_178097_4_) * 3.1415927F);
/* 195 */     GlStateManager.rotate(f4 * -20.0F, 0.0F, 1.0F, 0.0F);
/* 196 */     GlStateManager.rotate(f5 * -20.0F, 0.0F, 0.0F, 1.0F);
/* 197 */     GlStateManager.rotate(f5 * -80.0F, 1.0F, 0.0F, 0.0F);
/* 198 */     GlStateManager.scale(0.38F, 0.38F, 0.38F);
/* 199 */     GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/* 200 */     GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/* 201 */     GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
/* 202 */     GlStateManager.translate(-1.0F, -1.0F, 0.0F);
/* 203 */     GlStateManager.scale(0.015625F, 0.015625F, 0.015625F);
/* 204 */     this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
/* 205 */     Tessellator tessellator = Tessellator.getInstance();
/* 206 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 207 */     GL11.glNormal3f(0.0F, 0.0F, -1.0F);
/* 208 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 209 */     worldrenderer.pos(-7.0D, 135.0D, 0.0D).tex(0.0D, 1.0D).endVertex();
/* 210 */     worldrenderer.pos(135.0D, 135.0D, 0.0D).tex(1.0D, 1.0D).endVertex();
/* 211 */     worldrenderer.pos(135.0D, -7.0D, 0.0D).tex(1.0D, 0.0D).endVertex();
/* 212 */     worldrenderer.pos(-7.0D, -7.0D, 0.0D).tex(0.0D, 0.0D).endVertex();
/* 213 */     tessellator.draw();
/* 214 */     MapData mapdata = Items.filled_map.getMapData(this.itemToRender, (World)this.mc.theWorld);
/*     */     
/* 216 */     if (mapdata != null)
/*     */     {
/* 218 */       this.mc.entityRenderer.getMapItemRenderer().renderMap(mapdata, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178095_a(AbstractClientPlayer clientPlayer, float p_178095_2_, float p_178095_3_) {
/* 224 */     float f = -0.3F * MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * 3.1415927F);
/* 225 */     float f1 = 0.4F * MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * 3.1415927F * 2.0F);
/* 226 */     float f2 = -0.4F * MathHelper.sin(p_178095_3_ * 3.1415927F);
/* 227 */     GlStateManager.translate(f, f1, f2);
/* 228 */     GlStateManager.translate(0.64000005F, -0.6F, -0.71999997F);
/* 229 */     GlStateManager.translate(0.0F, p_178095_2_ * -0.6F, 0.0F);
/* 230 */     GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
/* 231 */     float f3 = MathHelper.sin(p_178095_3_ * p_178095_3_ * 3.1415927F);
/* 232 */     float f4 = MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * 3.1415927F);
/* 233 */     GlStateManager.rotate(f4 * 70.0F, 0.0F, 1.0F, 0.0F);
/* 234 */     GlStateManager.rotate(f3 * -20.0F, 0.0F, 0.0F, 1.0F);
/* 235 */     this.mc.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
/* 236 */     GlStateManager.translate(-1.0F, 3.6F, 3.5F);
/* 237 */     GlStateManager.rotate(120.0F, 0.0F, 0.0F, 1.0F);
/* 238 */     GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
/* 239 */     GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
/* 240 */     GlStateManager.scale(1.0F, 1.0F, 1.0F);
/* 241 */     GlStateManager.translate(5.6F, 0.0F, 0.0F);
/* 242 */     Render<AbstractClientPlayer> render = this.renderManager.getEntityRenderObject((Entity)this.mc.thePlayer);
/* 243 */     GlStateManager.disableCull();
/* 244 */     RenderPlayer renderplayer = (RenderPlayer)render;
/* 245 */     renderplayer.renderRightArm((AbstractClientPlayer)this.mc.thePlayer);
/* 246 */     GlStateManager.enableCull();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178105_d(float p_178105_1_) {
/* 251 */     float f = -0.4F * MathHelper.sin(MathHelper.sqrt_float(p_178105_1_) * 3.1415927F);
/* 252 */     float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(p_178105_1_) * 3.1415927F * 2.0F);
/* 253 */     float f2 = -0.2F * MathHelper.sin(p_178105_1_ * 3.1415927F);
/* 254 */     GlStateManager.translate(f, f1, f2);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178104_a(AbstractClientPlayer clientPlayer, float p_178104_2_) {
/* 259 */     float f = clientPlayer.getItemInUseCount() - p_178104_2_ + 1.0F;
/* 260 */     float f1 = f / this.itemToRender.getMaxItemUseDuration();
/* 261 */     float f2 = MathHelper.abs(MathHelper.cos(f / 4.0F * 3.1415927F) * 0.1F);
/*     */     
/* 263 */     if (f1 >= 0.8F)
/*     */     {
/* 265 */       f2 = 0.0F;
/*     */     }
/*     */     
/* 268 */     GlStateManager.translate(0.0F, f2, 0.0F);
/* 269 */     float f3 = 1.0F - (float)Math.pow(f1, 27.0D);
/* 270 */     GlStateManager.translate(f3 * 0.6F, f3 * -0.5F, f3 * 0.0F);
/* 271 */     GlStateManager.rotate(f3 * 90.0F, 0.0F, 1.0F, 0.0F);
/* 272 */     GlStateManager.rotate(f3 * 10.0F, 1.0F, 0.0F, 0.0F);
/* 273 */     GlStateManager.rotate(f3 * 30.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void transformFirstPersonItem(float equipProgress, float swingProgress) {
/* 284 */     GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
/* 285 */     GlStateManager.translate(0.0F, equipProgress * -0.6F, 0.0F);
/* 286 */     GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
/* 287 */     float f = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
/* 288 */     float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
/* 289 */     GlStateManager.rotate(f * -20.0F, 0.0F, 1.0F, 0.0F);
/* 290 */     GlStateManager.rotate(f1 * -20.0F, 0.0F, 0.0F, 1.0F);
/* 291 */     GlStateManager.rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
/* 292 */     GlStateManager.scale(0.4F, 0.4F, 0.4F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178098_a(float p_178098_1_, AbstractClientPlayer clientPlayer) {
/* 297 */     GlStateManager.rotate(-18.0F, 0.0F, 0.0F, 1.0F);
/* 298 */     GlStateManager.rotate(-12.0F, 0.0F, 1.0F, 0.0F);
/* 299 */     GlStateManager.rotate(-8.0F, 1.0F, 0.0F, 0.0F);
/* 300 */     GlStateManager.translate(-0.9F, 0.2F, 0.0F);
/* 301 */     float f = this.itemToRender.getMaxItemUseDuration() - clientPlayer.getItemInUseCount() - p_178098_1_ + 1.0F;
/* 302 */     float f1 = f / 20.0F;
/* 303 */     f1 = (f1 * f1 + f1 * 2.0F) / 3.0F;
/*     */     
/* 305 */     if (f1 > 1.0F)
/*     */     {
/* 307 */       f1 = 1.0F;
/*     */     }
/*     */     
/* 310 */     if (f1 > 0.1F) {
/*     */       
/* 312 */       float f2 = MathHelper.sin((f - 0.1F) * 1.3F);
/* 313 */       float f3 = f1 - 0.1F;
/* 314 */       float f4 = f2 * f3;
/* 315 */       GlStateManager.translate(f4 * 0.0F, f4 * 0.01F, f4 * 0.0F);
/*     */     } 
/*     */     
/* 318 */     GlStateManager.translate(f1 * 0.0F, f1 * 0.0F, f1 * 0.1F);
/* 319 */     GlStateManager.scale(1.0F, 1.0F, 1.0F + f1 * 0.2F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178103_d() {
/* 324 */     GlStateManager.translate(-0.5F, 0.2F, 0.0F);
/* 325 */     GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
/* 326 */     GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
/* 327 */     GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderItemInFirstPerson(float partialTicks) {
/* 337 */     if (!Config.isShaders() || !Shaders.isSkipRenderHand()) {
/*     */       
/* 339 */       float f = 1.0F - this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks;
/* 340 */       EntityPlayerSP entityPlayerSP = this.mc.thePlayer;
/* 341 */       float f1 = entityPlayerSP.getSwingProgress(partialTicks);
/* 342 */       float f2 = ((AbstractClientPlayer)entityPlayerSP).prevRotationPitch + (((AbstractClientPlayer)entityPlayerSP).rotationPitch - ((AbstractClientPlayer)entityPlayerSP).prevRotationPitch) * partialTicks;
/* 343 */       float f3 = ((AbstractClientPlayer)entityPlayerSP).prevRotationYaw + (((AbstractClientPlayer)entityPlayerSP).rotationYaw - ((AbstractClientPlayer)entityPlayerSP).prevRotationYaw) * partialTicks;
/* 344 */       func_178101_a(f2, f3);
/* 345 */       func_178109_a((AbstractClientPlayer)entityPlayerSP);
/* 346 */       func_178110_a(entityPlayerSP, partialTicks);
/* 347 */       GlStateManager.enableRescaleNormal();
/* 348 */       GlStateManager.pushMatrix();
/*     */       
/* 350 */       boolean killauraBlock = false;
/* 351 */       if (((KillAura)Slack.getInstance().getModuleManager().getInstance(KillAura.class)).isToggle() && 
/* 352 */         ((KillAura)Slack.getInstance().getModuleManager().getInstance(KillAura.class)).renderBlock) {
/* 353 */         killauraBlock = true;
/*     */       }
/*     */ 
/*     */       
/* 357 */       if (this.itemToRender != null) {
/*     */         
/* 359 */         if (this.itemToRender.getItem() instanceof net.minecraft.item.ItemMap) {
/*     */           
/* 361 */           renderItemMap((AbstractClientPlayer)entityPlayerSP, f2, f, f1);
/*     */         }
/* 363 */         else if (entityPlayerSP.getItemInUseCount() > 0 || killauraBlock) {
/*     */           
/* 365 */           EnumAction enumaction = this.itemToRender.getItemUseAction();
/* 366 */           if (killauraBlock) enumaction = EnumAction.BLOCK;
/*     */           
/* 368 */           switch (enumaction) {
/*     */             
/*     */             case NONE:
/* 371 */               transformFirstPersonItem(f, 0.0F);
/*     */               break;
/*     */             
/*     */             case EAT:
/*     */             case DRINK:
/* 376 */               func_178104_a((AbstractClientPlayer)entityPlayerSP, partialTicks);
/* 377 */               transformFirstPersonItem(f, 0.0F);
/*     */               break;
/*     */             
/*     */             case BLOCK:
/* 381 */               if (((Animations)Slack.getInstance().getModuleManager().getInstance(Animations.class)).isToggle()) {
/* 382 */                 switch ((String)((Animations)Slack.getInstance().getModuleManager().getInstance(Animations.class)).blockStyle.getValue()) {
/*     */                   case "1.8":
/* 384 */                     transformFirstPersonItem(f, 0.0F);
/* 385 */                     func_178103_d();
/*     */                     break;
/*     */                   case "1.7":
/* 388 */                     transformFirstPersonItem(f, f1);
/* 389 */                     func_178103_d();
/*     */                     break;
/*     */                   case "Slide":
/* 392 */                     transformFirstPersonItem(0.7F, f1);
/* 393 */                     func_178103_d(); break;
/*     */                 } 
/*     */                 break;
/*     */               } 
/* 397 */               transformFirstPersonItem(f, f1);
/* 398 */               func_178103_d();
/*     */               break;
/*     */ 
/*     */ 
/*     */             
/*     */             case BOW:
/* 404 */               transformFirstPersonItem(f, 0.0F);
/* 405 */               func_178098_a(partialTicks, (AbstractClientPlayer)entityPlayerSP);
/*     */               break;
/*     */           } 
/*     */         
/*     */         } else {
/* 410 */           func_178105_d(f1);
/* 411 */           transformFirstPersonItem(f, f1);
/*     */         } 
/*     */         
/* 414 */         renderItem((EntityLivingBase)entityPlayerSP, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
/*     */       }
/* 416 */       else if (!entityPlayerSP.isInvisible()) {
/*     */         
/* 418 */         func_178095_a((AbstractClientPlayer)entityPlayerSP, f, f1);
/*     */       } 
/*     */       
/* 421 */       GlStateManager.popMatrix();
/* 422 */       GlStateManager.disableRescaleNormal();
/* 423 */       RenderHelper.disableStandardItemLighting();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderOverlays(float partialTicks) {
/* 432 */     GlStateManager.disableAlpha();
/*     */     
/* 434 */     if (this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
/*     */       
/* 436 */       IBlockState iblockstate = this.mc.theWorld.getBlockState(new BlockPos((Entity)this.mc.thePlayer));
/* 437 */       BlockPos blockpos = new BlockPos((Entity)this.mc.thePlayer);
/* 438 */       EntityPlayerSP entityPlayerSP = this.mc.thePlayer;
/*     */       
/* 440 */       for (int i = 0; i < 8; i++) {
/*     */         
/* 442 */         double d0 = ((EntityPlayer)entityPlayerSP).posX + ((((i >> 0) % 2) - 0.5F) * ((EntityPlayer)entityPlayerSP).width * 0.8F);
/* 443 */         double d1 = ((EntityPlayer)entityPlayerSP).posY + ((((i >> 1) % 2) - 0.5F) * 0.1F);
/* 444 */         double d2 = ((EntityPlayer)entityPlayerSP).posZ + ((((i >> 2) % 2) - 0.5F) * ((EntityPlayer)entityPlayerSP).width * 0.8F);
/* 445 */         BlockPos blockpos1 = new BlockPos(d0, d1 + entityPlayerSP.getEyeHeight(), d2);
/* 446 */         IBlockState iblockstate1 = this.mc.theWorld.getBlockState(blockpos1);
/*     */         
/* 448 */         if (iblockstate1.getBlock().isVisuallyOpaque()) {
/*     */           
/* 450 */           iblockstate = iblockstate1;
/* 451 */           blockpos = blockpos1;
/*     */         } 
/*     */       } 
/*     */       
/* 455 */       if (iblockstate.getBlock().getRenderType() != -1) {
/*     */         
/* 457 */         Object object = Reflector.getFieldValue(Reflector.RenderBlockOverlayEvent_OverlayType_BLOCK);
/*     */         
/* 459 */         if (!Reflector.callBoolean(Reflector.ForgeEventFactory_renderBlockOverlay, new Object[] { this.mc.thePlayer, Float.valueOf(partialTicks), object, iblockstate, blockpos }))
/*     */         {
/* 461 */           func_178108_a(partialTicks, this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(iblockstate));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 466 */     if (!this.mc.thePlayer.isSpectator()) {
/*     */       
/* 468 */       if (this.mc.thePlayer.isInsideOfMaterial(Material.water) && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderWaterOverlay, new Object[] { this.mc.thePlayer, Float.valueOf(partialTicks) }))
/*     */       {
/* 470 */         renderWaterOverlayTexture(partialTicks);
/*     */       }
/*     */       
/* 473 */       if (this.mc.thePlayer.isBurning() && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderFireOverlay, new Object[] { this.mc.thePlayer, Float.valueOf(partialTicks) }))
/*     */       {
/* 475 */         renderFireInFirstPerson(partialTicks);
/*     */       }
/*     */     } 
/*     */     
/* 479 */     GlStateManager.enableAlpha();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178108_a(float p_178108_1_, TextureAtlasSprite p_178108_2_) {
/* 484 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 485 */     Tessellator tessellator = Tessellator.getInstance();
/* 486 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 487 */     float f = 0.1F;
/* 488 */     GlStateManager.color(0.1F, 0.1F, 0.1F, 0.5F);
/* 489 */     GlStateManager.pushMatrix();
/* 490 */     float f1 = -1.0F;
/* 491 */     float f2 = 1.0F;
/* 492 */     float f3 = -1.0F;
/* 493 */     float f4 = 1.0F;
/* 494 */     float f5 = -0.5F;
/* 495 */     float f6 = p_178108_2_.getMinU();
/* 496 */     float f7 = p_178108_2_.getMaxU();
/* 497 */     float f8 = p_178108_2_.getMinV();
/* 498 */     float f9 = p_178108_2_.getMaxV();
/* 499 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 500 */     worldrenderer.pos(-1.0D, -1.0D, -0.5D).tex(f7, f9).endVertex();
/* 501 */     worldrenderer.pos(1.0D, -1.0D, -0.5D).tex(f6, f9).endVertex();
/* 502 */     worldrenderer.pos(1.0D, 1.0D, -0.5D).tex(f6, f8).endVertex();
/* 503 */     worldrenderer.pos(-1.0D, 1.0D, -0.5D).tex(f7, f8).endVertex();
/* 504 */     tessellator.draw();
/* 505 */     GlStateManager.popMatrix();
/* 506 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderWaterOverlayTexture(float p_78448_1_) {
/* 515 */     if (!Config.isShaders() || Shaders.isUnderwaterOverlay()) {
/*     */       
/* 517 */       this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
/* 518 */       Tessellator tessellator = Tessellator.getInstance();
/* 519 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 520 */       float f = this.mc.thePlayer.getBrightness(p_78448_1_);
/* 521 */       GlStateManager.color(f, f, f, 0.5F);
/* 522 */       GlStateManager.enableBlend();
/* 523 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 524 */       GlStateManager.pushMatrix();
/* 525 */       float f1 = 4.0F;
/* 526 */       float f2 = -1.0F;
/* 527 */       float f3 = 1.0F;
/* 528 */       float f4 = -1.0F;
/* 529 */       float f5 = 1.0F;
/* 530 */       float f6 = -0.5F;
/* 531 */       float f7 = -this.mc.thePlayer.rotationYaw / 64.0F;
/* 532 */       float f8 = this.mc.thePlayer.rotationPitch / 64.0F;
/* 533 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 534 */       worldrenderer.pos(-1.0D, -1.0D, -0.5D).tex((4.0F + f7), (4.0F + f8)).endVertex();
/* 535 */       worldrenderer.pos(1.0D, -1.0D, -0.5D).tex((0.0F + f7), (4.0F + f8)).endVertex();
/* 536 */       worldrenderer.pos(1.0D, 1.0D, -0.5D).tex((0.0F + f7), (0.0F + f8)).endVertex();
/* 537 */       worldrenderer.pos(-1.0D, 1.0D, -0.5D).tex((4.0F + f7), (0.0F + f8)).endVertex();
/* 538 */       tessellator.draw();
/* 539 */       GlStateManager.popMatrix();
/* 540 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 541 */       GlStateManager.disableBlend();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderFireInFirstPerson(float p_78442_1_) {
/* 550 */     Tessellator tessellator = Tessellator.getInstance();
/* 551 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 552 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
/* 553 */     GlStateManager.depthFunc(519);
/* 554 */     GlStateManager.depthMask(false);
/* 555 */     GlStateManager.enableBlend();
/* 556 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 557 */     float f = 1.0F;
/*     */     
/* 559 */     for (int i = 0; i < 2; i++) {
/*     */       
/* 561 */       GlStateManager.pushMatrix();
/* 562 */       TextureAtlasSprite textureatlassprite = this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1");
/* 563 */       this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 564 */       float f1 = textureatlassprite.getMinU();
/* 565 */       float f2 = textureatlassprite.getMaxU();
/* 566 */       float f3 = textureatlassprite.getMinV();
/* 567 */       float f4 = textureatlassprite.getMaxV();
/* 568 */       float f5 = (0.0F - f) / 2.0F;
/* 569 */       float f6 = f5 + f;
/* 570 */       float f7 = 0.0F - f / 2.0F;
/* 571 */       float f8 = f7 + f;
/* 572 */       float f9 = -0.5F;
/* 573 */       GlStateManager.translate(-(i * 2 - 1) * 0.24F, -0.3F, 0.0F);
/* 574 */       GlStateManager.rotate((i * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
/* 575 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 576 */       worldrenderer.setSprite(textureatlassprite);
/* 577 */       worldrenderer.pos(f5, f7, f9).tex(f2, f4).endVertex();
/* 578 */       worldrenderer.pos(f6, f7, f9).tex(f1, f4).endVertex();
/* 579 */       worldrenderer.pos(f6, f8, f9).tex(f1, f3).endVertex();
/* 580 */       worldrenderer.pos(f5, f8, f9).tex(f2, f3).endVertex();
/* 581 */       tessellator.draw();
/* 582 */       GlStateManager.popMatrix();
/*     */     } 
/*     */     
/* 585 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 586 */     GlStateManager.disableBlend();
/* 587 */     GlStateManager.depthMask(true);
/* 588 */     GlStateManager.depthFunc(515);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEquippedItem() {
/* 593 */     this.prevEquippedProgress = this.equippedProgress;
/* 594 */     EntityPlayerSP entityPlayerSP = this.mc.thePlayer;
/* 595 */     ItemStack itemstack = ((EntityPlayer)entityPlayerSP).inventory.getCurrentItem();
/* 596 */     if (ItemSpoofUtil.isEnabled) itemstack = this.mc.thePlayer.inventory.getStackInSlot(ItemSpoofUtil.renderSlot); 
/* 597 */     boolean flag = false;
/*     */     
/* 599 */     if (this.itemToRender != null && itemstack != null) {
/*     */       
/* 601 */       if (!this.itemToRender.getIsItemStackEqual(itemstack))
/*     */       {
/* 603 */         if (Reflector.ForgeItem_shouldCauseReequipAnimation.exists()) {
/*     */           
/* 605 */           boolean flag1 = Reflector.callBoolean(this.itemToRender.getItem(), Reflector.ForgeItem_shouldCauseReequipAnimation, new Object[] { this.itemToRender, itemstack, Boolean.valueOf((this.equippedItemSlot != ((EntityPlayer)entityPlayerSP).inventory.currentItem)) });
/*     */           
/* 607 */           if (!flag1) {
/*     */             
/* 609 */             this.itemToRender = itemstack;
/* 610 */             this.equippedItemSlot = ((EntityPlayer)entityPlayerSP).inventory.currentItem;
/* 611 */             if (ItemSpoofUtil.isEnabled) this.equippedItemSlot = ItemSpoofUtil.renderSlot;
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/* 616 */         flag = true;
/*     */       }
/*     */     
/* 619 */     } else if (this.itemToRender == null && itemstack == null) {
/*     */       
/* 621 */       flag = false;
/*     */     }
/*     */     else {
/*     */       
/* 625 */       flag = true;
/*     */     } 
/*     */     
/* 628 */     float f2 = 0.4F;
/* 629 */     float f = flag ? 0.0F : 1.0F;
/* 630 */     float f1 = MathHelper.clamp_float(f - this.equippedProgress, -f2, f2);
/* 631 */     this.equippedProgress += f1;
/*     */     
/* 633 */     if (this.equippedProgress < 0.1F) {
/*     */       
/* 635 */       this.itemToRender = itemstack;
/* 636 */       this.equippedItemSlot = ((EntityPlayer)entityPlayerSP).inventory.currentItem;
/*     */       
/* 638 */       if (Config.isShaders())
/*     */       {
/* 640 */         Shaders.setItemToRenderMain(itemstack);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetEquippedProgress() {
/* 650 */     this.equippedProgress = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetEquippedProgress2() {
/* 658 */     this.equippedProgress = 0.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\ItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
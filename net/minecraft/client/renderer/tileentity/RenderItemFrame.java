/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureCompass;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.client.resources.model.ModelManager;
/*     */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class RenderItemFrame
/*     */   extends Render<EntityItemFrame>
/*     */ {
/*  40 */   private static final ResourceLocation mapBackgroundTextures = new ResourceLocation("textures/map/map_background.png");
/*  41 */   private final Minecraft mc = Minecraft.getMinecraft();
/*  42 */   private final ModelResourceLocation itemFrameModel = new ModelResourceLocation("item_frame", "normal");
/*  43 */   private final ModelResourceLocation mapModel = new ModelResourceLocation("item_frame", "map");
/*     */   private RenderItem itemRenderer;
/*  45 */   private static double itemRenderDistanceSq = 4096.0D;
/*     */ 
/*     */   
/*     */   public RenderItemFrame(RenderManager renderManagerIn, RenderItem itemRendererIn) {
/*  49 */     super(renderManagerIn);
/*  50 */     this.itemRenderer = itemRendererIn;
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
/*     */   public void doRender(EntityItemFrame entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*     */     IBakedModel ibakedmodel;
/*  63 */     GlStateManager.pushMatrix();
/*  64 */     BlockPos blockpos = entity.getHangingPosition();
/*  65 */     double d0 = blockpos.getX() - entity.posX + x;
/*  66 */     double d1 = blockpos.getY() - entity.posY + y;
/*  67 */     double d2 = blockpos.getZ() - entity.posZ + z;
/*  68 */     GlStateManager.translate(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D);
/*  69 */     GlStateManager.rotate(180.0F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
/*  70 */     this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
/*  71 */     BlockRendererDispatcher blockrendererdispatcher = this.mc.getBlockRendererDispatcher();
/*  72 */     ModelManager modelmanager = blockrendererdispatcher.getBlockModelShapes().getModelManager();
/*     */ 
/*     */     
/*  75 */     if (entity.getDisplayedItem() != null && entity.getDisplayedItem().getItem() == Items.filled_map) {
/*     */       
/*  77 */       ibakedmodel = modelmanager.getModel(this.mapModel);
/*     */     }
/*     */     else {
/*     */       
/*  81 */       ibakedmodel = modelmanager.getModel(this.itemFrameModel);
/*     */     } 
/*     */     
/*  84 */     GlStateManager.pushMatrix();
/*  85 */     GlStateManager.translate(-0.5F, -0.5F, -0.5F);
/*  86 */     blockrendererdispatcher.getBlockModelRenderer().renderModelBrightnessColor(ibakedmodel, 1.0F, 1.0F, 1.0F, 1.0F);
/*  87 */     GlStateManager.popMatrix();
/*  88 */     GlStateManager.translate(0.0F, 0.0F, 0.4375F);
/*  89 */     renderItem(entity);
/*  90 */     GlStateManager.popMatrix();
/*  91 */     renderName(entity, x + (entity.facingDirection.getFrontOffsetX() * 0.3F), y - 0.25D, z + (entity.facingDirection.getFrontOffsetZ() * 0.3F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityItemFrame entity) {
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderItem(EntityItemFrame itemFrame) {
/* 104 */     ItemStack itemstack = itemFrame.getDisplayedItem();
/*     */     
/* 106 */     if (itemstack != null) {
/*     */       
/* 108 */       if (!isRenderItem(itemFrame)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 113 */       if (!Config.zoomMode) {
/*     */         
/* 115 */         EntityPlayerSP entityPlayerSP = this.mc.thePlayer;
/* 116 */         double d0 = itemFrame.getDistanceSq(((Entity)entityPlayerSP).posX, ((Entity)entityPlayerSP).posY, ((Entity)entityPlayerSP).posZ);
/*     */         
/* 118 */         if (d0 > 4096.0D) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 124 */       EntityItem entityitem = new EntityItem(itemFrame.worldObj, 0.0D, 0.0D, 0.0D, itemstack);
/* 125 */       Item item = entityitem.getEntityItem().getItem();
/* 126 */       (entityitem.getEntityItem()).stackSize = 1;
/* 127 */       entityitem.hoverStart = 0.0F;
/* 128 */       GlStateManager.pushMatrix();
/* 129 */       GlStateManager.disableLighting();
/* 130 */       int i = itemFrame.getRotation();
/*     */       
/* 132 */       if (item instanceof net.minecraft.item.ItemMap)
/*     */       {
/* 134 */         i = i % 4 * 2;
/*     */       }
/*     */       
/* 137 */       GlStateManager.rotate(i * 360.0F / 8.0F, 0.0F, 0.0F, 1.0F);
/*     */       
/* 139 */       if (!Reflector.postForgeBusEvent(Reflector.RenderItemInFrameEvent_Constructor, new Object[] { itemFrame, this }))
/*     */       {
/* 141 */         if (item instanceof net.minecraft.item.ItemMap) {
/*     */           
/* 143 */           this.renderManager.renderEngine.bindTexture(mapBackgroundTextures);
/* 144 */           GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/* 145 */           float f = 0.0078125F;
/* 146 */           GlStateManager.scale(f, f, f);
/* 147 */           GlStateManager.translate(-64.0F, -64.0F, 0.0F);
/* 148 */           MapData mapdata = Items.filled_map.getMapData(entityitem.getEntityItem(), itemFrame.worldObj);
/* 149 */           GlStateManager.translate(0.0F, 0.0F, -1.0F);
/*     */           
/* 151 */           if (mapdata != null)
/*     */           {
/* 153 */             this.mc.entityRenderer.getMapItemRenderer().renderMap(mapdata, true);
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 158 */           TextureAtlasSprite textureatlassprite = null;
/*     */           
/* 160 */           if (item == Items.compass) {
/*     */             
/* 162 */             textureatlassprite = this.mc.getTextureMapBlocks().getAtlasSprite(TextureCompass.field_176608_l);
/* 163 */             this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/*     */             
/* 165 */             if (textureatlassprite instanceof TextureCompass) {
/*     */               
/* 167 */               TextureCompass texturecompass = (TextureCompass)textureatlassprite;
/* 168 */               double d1 = texturecompass.currentAngle;
/* 169 */               double d2 = texturecompass.angleDelta;
/* 170 */               texturecompass.currentAngle = 0.0D;
/* 171 */               texturecompass.angleDelta = 0.0D;
/* 172 */               texturecompass.updateCompass(itemFrame.worldObj, itemFrame.posX, itemFrame.posZ, MathHelper.wrapAngleTo180_float((180 + itemFrame.facingDirection.getHorizontalIndex() * 90)), false, true);
/* 173 */               texturecompass.currentAngle = d1;
/* 174 */               texturecompass.angleDelta = d2;
/*     */             }
/*     */             else {
/*     */               
/* 178 */               textureatlassprite = null;
/*     */             } 
/*     */           } 
/*     */           
/* 182 */           GlStateManager.scale(0.5F, 0.5F, 0.5F);
/*     */           
/* 184 */           if (!this.itemRenderer.shouldRenderItemIn3D(entityitem.getEntityItem()) || item instanceof net.minecraft.item.ItemSkull)
/*     */           {
/* 186 */             GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*     */           }
/*     */           
/* 189 */           GlStateManager.pushAttrib();
/* 190 */           RenderHelper.enableStandardItemLighting();
/* 191 */           this.itemRenderer.func_181564_a(entityitem.getEntityItem(), ItemCameraTransforms.TransformType.FIXED);
/* 192 */           RenderHelper.disableStandardItemLighting();
/* 193 */           GlStateManager.popAttrib();
/*     */           
/* 195 */           if (textureatlassprite != null && textureatlassprite.getFrameCount() > 0)
/*     */           {
/* 197 */             textureatlassprite.updateAnimation();
/*     */           }
/*     */         } 
/*     */       }
/* 201 */       GlStateManager.enableLighting();
/* 202 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderName(EntityItemFrame entity, double x, double y, double z) {
/* 208 */     if (Minecraft.isGuiEnabled() && entity.getDisplayedItem() != null && entity.getDisplayedItem().hasDisplayName() && this.renderManager.pointedEntity == entity) {
/*     */       
/* 210 */       float f = 1.6F;
/* 211 */       float f1 = 0.016666668F * f;
/* 212 */       double d0 = entity.getDistanceSqToEntity(this.renderManager.livingPlayer);
/* 213 */       float f2 = entity.isSneaking() ? 32.0F : 64.0F;
/*     */       
/* 215 */       if (d0 < (f2 * f2)) {
/*     */         
/* 217 */         String s = entity.getDisplayedItem().getDisplayName();
/*     */         
/* 219 */         if (entity.isSneaking()) {
/*     */           
/* 221 */           FontRenderer fontrenderer = getFontRendererFromRenderManager();
/* 222 */           GlStateManager.pushMatrix();
/* 223 */           GlStateManager.translate((float)x + 0.0F, (float)y + entity.height + 0.5F, (float)z);
/* 224 */           GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 225 */           GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 226 */           GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 227 */           GlStateManager.scale(-f1, -f1, f1);
/* 228 */           GlStateManager.disableLighting();
/* 229 */           GlStateManager.translate(0.0F, 0.25F / f1, 0.0F);
/* 230 */           GlStateManager.depthMask(false);
/* 231 */           GlStateManager.enableBlend();
/* 232 */           GlStateManager.blendFunc(770, 771);
/* 233 */           Tessellator tessellator = Tessellator.getInstance();
/* 234 */           WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 235 */           int i = fontrenderer.getStringWidth(s) / 2;
/* 236 */           GlStateManager.disableTexture2D();
/* 237 */           worldrenderer.begin(7, DefaultVertexFormats.field_181706_f);
/* 238 */           worldrenderer.pos((-i - 1), -1.0D, 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 239 */           worldrenderer.pos((-i - 1), 8.0D, 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 240 */           worldrenderer.pos((i + 1), 8.0D, 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 241 */           worldrenderer.pos((i + 1), -1.0D, 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 242 */           tessellator.draw();
/* 243 */           GlStateManager.enableTexture2D();
/* 244 */           GlStateManager.depthMask(true);
/* 245 */           fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 553648127);
/* 246 */           GlStateManager.enableLighting();
/* 247 */           GlStateManager.disableBlend();
/* 248 */           GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 249 */           GlStateManager.popMatrix();
/*     */         }
/*     */         else {
/*     */           
/* 253 */           renderLivingLabel((Entity)entity, s, x, y, z, 64);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isRenderItem(EntityItemFrame p_isRenderItem_1_) {
/* 261 */     if (Shaders.isShadowPass)
/*     */     {
/* 263 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 267 */     if (!Config.zoomMode) {
/*     */       
/* 269 */       Entity entity = this.mc.getRenderViewEntity();
/* 270 */       double d0 = p_isRenderItem_1_.getDistanceSq(entity.posX, entity.posY, entity.posZ);
/*     */       
/* 272 */       if (d0 > itemRenderDistanceSq)
/*     */       {
/* 274 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 278 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateItemRenderDistance() {
/* 284 */     Minecraft minecraft = Config.getMinecraft();
/* 285 */     double d0 = Config.limit(minecraft.gameSettings.fovSetting, 1.0F, 120.0F);
/* 286 */     double d1 = Math.max(6.0D * minecraft.displayHeight / d0, 16.0D);
/* 287 */     itemRenderDistanceSq = d1 * d1;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\tileentity\RenderItemFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
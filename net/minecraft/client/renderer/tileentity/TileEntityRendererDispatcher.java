/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.tileentity.TileEntityEnchantmentTable;
/*     */ import net.minecraft.tileentity.TileEntityEndPortal;
/*     */ import net.minecraft.tileentity.TileEntityEnderChest;
/*     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*     */ import net.minecraft.tileentity.TileEntityPiston;
/*     */ import net.minecraft.tileentity.TileEntitySign;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.EmissiveTextures;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ public class TileEntityRendererDispatcher
/*     */ {
/*  36 */   public Map<Class, TileEntitySpecialRenderer> mapSpecialRenderers = Maps.newHashMap();
/*  37 */   public static TileEntityRendererDispatcher instance = new TileEntityRendererDispatcher();
/*     */   
/*     */   public FontRenderer fontRenderer;
/*     */   
/*     */   public static double staticPlayerX;
/*     */   
/*     */   public static double staticPlayerY;
/*     */   
/*     */   public static double staticPlayerZ;
/*     */   
/*     */   public TextureManager renderEngine;
/*     */   
/*     */   public World worldObj;
/*     */   public Entity entity;
/*     */   public float entityYaw;
/*     */   public float entityPitch;
/*     */   public double entityX;
/*     */   public double entityY;
/*     */   public double entityZ;
/*     */   public TileEntity tileEntityRendered;
/*  57 */   private Tessellator batchBuffer = new Tessellator(2097152);
/*     */   
/*     */   private boolean drawingBatch = false;
/*     */   
/*     */   private TileEntityRendererDispatcher() {
/*  62 */     this.mapSpecialRenderers.put(TileEntitySign.class, new TileEntitySignRenderer());
/*  63 */     this.mapSpecialRenderers.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
/*  64 */     this.mapSpecialRenderers.put(TileEntityPiston.class, new TileEntityPistonRenderer());
/*  65 */     this.mapSpecialRenderers.put(TileEntityChest.class, new TileEntityChestRenderer());
/*  66 */     this.mapSpecialRenderers.put(TileEntityEnderChest.class, new TileEntityEnderChestRenderer());
/*  67 */     this.mapSpecialRenderers.put(TileEntityEnchantmentTable.class, new TileEntityEnchantmentTableRenderer());
/*  68 */     this.mapSpecialRenderers.put(TileEntityEndPortal.class, new TileEntityEndPortalRenderer());
/*  69 */     this.mapSpecialRenderers.put(TileEntityBeacon.class, new TileEntityBeaconRenderer());
/*  70 */     this.mapSpecialRenderers.put(TileEntitySkull.class, new TileEntitySkullRenderer());
/*  71 */     this.mapSpecialRenderers.put(TileEntityBanner.class, new TileEntityBannerRenderer());
/*     */     
/*  73 */     for (TileEntitySpecialRenderer<?> tileentityspecialrenderer : this.mapSpecialRenderers.values())
/*     */     {
/*  75 */       tileentityspecialrenderer.setRendererDispatcher(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends TileEntity> TileEntitySpecialRenderer<T> getSpecialRendererByClass(Class<? extends TileEntity> teClass) {
/*  81 */     TileEntitySpecialRenderer<? extends TileEntity> tileentityspecialrenderer = this.mapSpecialRenderers.get(teClass);
/*     */     
/*  83 */     if (tileentityspecialrenderer == null && teClass != TileEntity.class) {
/*     */       
/*  85 */       tileentityspecialrenderer = getSpecialRendererByClass((Class)teClass.getSuperclass());
/*  86 */       this.mapSpecialRenderers.put(teClass, tileentityspecialrenderer);
/*     */     } 
/*     */     
/*  89 */     return (TileEntitySpecialRenderer)tileentityspecialrenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends TileEntity> TileEntitySpecialRenderer<T> getSpecialRenderer(TileEntity tileEntityIn) {
/*  94 */     return (tileEntityIn != null && !tileEntityIn.isInvalid()) ? getSpecialRendererByClass((Class)tileEntityIn.getClass()) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void cacheActiveRenderInfo(World worldIn, TextureManager textureManagerIn, FontRenderer fontrendererIn, Entity entityIn, float partialTicks) {
/*  99 */     if (this.worldObj != worldIn)
/*     */     {
/* 101 */       setWorld(worldIn);
/*     */     }
/*     */     
/* 104 */     this.renderEngine = textureManagerIn;
/* 105 */     this.entity = entityIn;
/* 106 */     this.fontRenderer = fontrendererIn;
/* 107 */     this.entityYaw = entityIn.prevRotationYaw + (entityIn.rotationYaw - entityIn.prevRotationYaw) * partialTicks;
/* 108 */     this.entityPitch = entityIn.prevRotationPitch + (entityIn.rotationPitch - entityIn.prevRotationPitch) * partialTicks;
/* 109 */     this.entityX = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 110 */     this.entityY = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 111 */     this.entityZ = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderTileEntity(TileEntity tileentityIn, float partialTicks, int destroyStage) {
/* 116 */     if (tileentityIn.getDistanceSq(this.entityX, this.entityY, this.entityZ) < tileentityIn.getMaxRenderDistanceSquared()) {
/*     */       
/* 118 */       boolean flag = true;
/*     */       
/* 120 */       if (Reflector.ForgeTileEntity_hasFastRenderer.exists())
/*     */       {
/* 122 */         flag = (!this.drawingBatch || !Reflector.callBoolean(tileentityIn, Reflector.ForgeTileEntity_hasFastRenderer, new Object[0]));
/*     */       }
/*     */       
/* 125 */       if (flag) {
/*     */         
/* 127 */         RenderHelper.enableStandardItemLighting();
/* 128 */         int i = this.worldObj.getCombinedLight(tileentityIn.getPos(), 0);
/* 129 */         int j = i % 65536;
/* 130 */         int k = i / 65536;
/* 131 */         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
/* 132 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       } 
/*     */       
/* 135 */       BlockPos blockpos = tileentityIn.getPos();
/*     */       
/* 137 */       if (!this.worldObj.isBlockLoaded(blockpos, false)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 142 */       if (EmissiveTextures.isActive())
/*     */       {
/* 144 */         EmissiveTextures.beginRender();
/*     */       }
/*     */       
/* 147 */       renderTileEntityAt(tileentityIn, blockpos.getX() - staticPlayerX, blockpos.getY() - staticPlayerY, blockpos.getZ() - staticPlayerZ, partialTicks, destroyStage);
/*     */       
/* 149 */       if (EmissiveTextures.isActive()) {
/*     */         
/* 151 */         if (EmissiveTextures.hasEmissive()) {
/*     */           
/* 153 */           EmissiveTextures.beginRenderEmissive();
/* 154 */           renderTileEntityAt(tileentityIn, blockpos.getX() - staticPlayerX, blockpos.getY() - staticPlayerY, blockpos.getZ() - staticPlayerZ, partialTicks, destroyStage);
/* 155 */           EmissiveTextures.endRenderEmissive();
/*     */         } 
/*     */         
/* 158 */         EmissiveTextures.endRender();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderTileEntityAt(TileEntity tileEntityIn, double x, double y, double z, float partialTicks) {
/* 168 */     renderTileEntityAt(tileEntityIn, x, y, z, partialTicks, -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderTileEntityAt(TileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
/* 173 */     TileEntitySpecialRenderer<TileEntity> tileentityspecialrenderer = getSpecialRenderer(tileEntityIn);
/*     */     
/* 175 */     if (tileentityspecialrenderer != null) {
/*     */       
/*     */       try {
/*     */         
/* 179 */         this.tileEntityRendered = tileEntityIn;
/*     */         
/* 181 */         if (this.drawingBatch && Reflector.callBoolean(tileEntityIn, Reflector.ForgeTileEntity_hasFastRenderer, new Object[0])) {
/*     */           
/* 183 */           tileentityspecialrenderer.renderTileEntityFast(tileEntityIn, x, y, z, partialTicks, destroyStage, this.batchBuffer.getWorldRenderer());
/*     */         }
/*     */         else {
/*     */           
/* 187 */           tileentityspecialrenderer.renderTileEntityAt(tileEntityIn, x, y, z, partialTicks, destroyStage);
/*     */         } 
/*     */         
/* 190 */         this.tileEntityRendered = null;
/*     */       }
/* 192 */       catch (Throwable throwable) {
/*     */         
/* 194 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering Block Entity");
/* 195 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Block Entity Details");
/* 196 */         tileEntityIn.addInfoToCrashReport(crashreportcategory);
/* 197 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWorld(World worldIn) {
/* 204 */     this.worldObj = worldIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public FontRenderer getFontRenderer() {
/* 209 */     return this.fontRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void preDrawBatch() {
/* 214 */     this.batchBuffer.getWorldRenderer().begin(7, DefaultVertexFormats.BLOCK);
/* 215 */     this.drawingBatch = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawBatch(int p_drawBatch_1_) {
/* 220 */     this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
/* 221 */     RenderHelper.disableStandardItemLighting();
/* 222 */     GlStateManager.blendFunc(770, 771);
/* 223 */     GlStateManager.enableBlend();
/* 224 */     GlStateManager.disableCull();
/*     */     
/* 226 */     if (Minecraft.isAmbientOcclusionEnabled()) {
/*     */       
/* 228 */       GlStateManager.shadeModel(7425);
/*     */     }
/*     */     else {
/*     */       
/* 232 */       GlStateManager.shadeModel(7424);
/*     */     } 
/*     */     
/* 235 */     if (p_drawBatch_1_ > 0)
/*     */     {
/* 237 */       this.batchBuffer.getWorldRenderer().func_181674_a((float)staticPlayerX, (float)staticPlayerY, (float)staticPlayerZ);
/*     */     }
/*     */     
/* 240 */     this.batchBuffer.draw();
/* 241 */     RenderHelper.enableStandardItemLighting();
/* 242 */     this.drawingBatch = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\tileentity\TileEntityRendererDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
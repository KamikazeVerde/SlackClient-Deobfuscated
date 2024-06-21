/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.CustomColors;
/*     */ 
/*     */ public class RenderXPOrb extends Render<EntityXPOrb> {
/*  16 */   private static final ResourceLocation experienceOrbTextures = new ResourceLocation("textures/entity/experience_orb.png");
/*     */ 
/*     */   
/*     */   public RenderXPOrb(RenderManager renderManagerIn) {
/*  20 */     super(renderManagerIn);
/*  21 */     this.shadowSize = 0.15F;
/*  22 */     this.shadowOpaque = 0.75F;
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
/*     */   public void doRender(EntityXPOrb entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  35 */     GlStateManager.pushMatrix();
/*  36 */     GlStateManager.translate((float)x, (float)y, (float)z);
/*  37 */     bindEntityTexture(entity);
/*  38 */     int i = entity.getTextureByXP();
/*  39 */     float f = (i % 4 * 16 + 0) / 64.0F;
/*  40 */     float f1 = (i % 4 * 16 + 16) / 64.0F;
/*  41 */     float f2 = (i / 4 * 16 + 0) / 64.0F;
/*  42 */     float f3 = (i / 4 * 16 + 16) / 64.0F;
/*  43 */     float f4 = 1.0F;
/*  44 */     float f5 = 0.5F;
/*  45 */     float f6 = 0.25F;
/*  46 */     int j = entity.getBrightnessForRender(partialTicks);
/*  47 */     int k = j % 65536;
/*  48 */     int l = j / 65536;
/*  49 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k / 1.0F, l / 1.0F);
/*  50 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  51 */     float f7 = 255.0F;
/*  52 */     float f8 = (entity.xpColor + partialTicks) / 2.0F;
/*     */     
/*  54 */     if (Config.isCustomColors())
/*     */     {
/*  56 */       f8 = CustomColors.getXpOrbTimer(f8);
/*     */     }
/*     */     
/*  59 */     l = (int)((MathHelper.sin(f8 + 0.0F) + 1.0F) * 0.5F * 255.0F);
/*  60 */     int i1 = 255;
/*  61 */     int j1 = (int)((MathHelper.sin(f8 + 4.1887903F) + 1.0F) * 0.1F * 255.0F);
/*  62 */     GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/*  63 */     GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/*  64 */     float f9 = 0.3F;
/*  65 */     GlStateManager.scale(0.3F, 0.3F, 0.3F);
/*  66 */     Tessellator tessellator = Tessellator.getInstance();
/*  67 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  68 */     worldrenderer.begin(7, DefaultVertexFormats.field_181712_l);
/*  69 */     int k1 = l;
/*  70 */     int l1 = 255;
/*  71 */     int i2 = j1;
/*     */     
/*  73 */     if (Config.isCustomColors()) {
/*     */       
/*  75 */       int j2 = CustomColors.getXpOrbColor(f8);
/*     */       
/*  77 */       if (j2 >= 0) {
/*     */         
/*  79 */         k1 = j2 >> 16 & 0xFF;
/*  80 */         l1 = j2 >> 8 & 0xFF;
/*  81 */         i2 = j2 >> 0 & 0xFF;
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     worldrenderer.pos((0.0F - f5), (0.0F - f6), 0.0D).tex(f, f3).func_181669_b(k1, l1, i2, 128).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/*  86 */     worldrenderer.pos((f4 - f5), (0.0F - f6), 0.0D).tex(f1, f3).func_181669_b(k1, l1, i2, 128).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/*  87 */     worldrenderer.pos((f4 - f5), (1.0F - f6), 0.0D).tex(f1, f2).func_181669_b(k1, l1, i2, 128).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/*  88 */     worldrenderer.pos((0.0F - f5), (1.0F - f6), 0.0D).tex(f, f2).func_181669_b(k1, l1, i2, 128).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/*  89 */     tessellator.draw();
/*  90 */     GlStateManager.disableBlend();
/*  91 */     GlStateManager.disableRescaleNormal();
/*  92 */     GlStateManager.popMatrix();
/*  93 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityXPOrb entity) {
/* 101 */     return experienceOrbTextures;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\RenderXPOrb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
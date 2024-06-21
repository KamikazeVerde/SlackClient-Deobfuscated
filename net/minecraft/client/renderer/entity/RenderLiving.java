/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ public abstract class RenderLiving<T extends EntityLiving>
/*     */   extends RendererLivingEntity<T>
/*     */ {
/*     */   public RenderLiving(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
/*  20 */     super(rendermanagerIn, modelbaseIn, shadowsizeIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canRenderName(T entity) {
/*  25 */     return (super.canRenderName(entity) && (entity.getAlwaysRenderNameTagForRender() || (entity.hasCustomName() && entity == this.renderManager.pointedEntity)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender(T livingEntity, ICamera camera, double camX, double camY, double camZ) {
/*  30 */     if (super.shouldRender(livingEntity, camera, camX, camY, camZ))
/*     */     {
/*  32 */       return true;
/*     */     }
/*  34 */     if (livingEntity.getLeashed() && livingEntity.getLeashedToEntity() != null) {
/*     */       
/*  36 */       Entity entity = livingEntity.getLeashedToEntity();
/*  37 */       return camera.isBoundingBoxInFrustum(entity.getEntityBoundingBox());
/*     */     } 
/*     */ 
/*     */     
/*  41 */     return false;
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
/*     */   
/*     */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  55 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*  56 */     renderLeash(entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_177105_a(T entityLivingIn, float partialTicks) {
/*  61 */     int i = entityLivingIn.getBrightnessForRender(partialTicks);
/*  62 */     int j = i % 65536;
/*  63 */     int k = i / 65536;
/*  64 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double interpolateValue(double start, double end, double pct) {
/*  72 */     return start + (end - start) * pct;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderLeash(T entityLivingIn, double x, double y, double z, float entityYaw, float partialTicks) {
/*  77 */     if (!Config.isShaders() || !Shaders.isShadowPass) {
/*     */       
/*  79 */       Entity entity = entityLivingIn.getLeashedToEntity();
/*     */       
/*  81 */       if (entity != null) {
/*     */         
/*  83 */         y -= (1.6D - ((EntityLiving)entityLivingIn).height) * 0.5D;
/*  84 */         Tessellator tessellator = Tessellator.getInstance();
/*  85 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  86 */         double d0 = interpolateValue(entity.prevRotationYaw, entity.rotationYaw, (partialTicks * 0.5F)) * 0.01745329238474369D;
/*  87 */         double d1 = interpolateValue(entity.prevRotationPitch, entity.rotationPitch, (partialTicks * 0.5F)) * 0.01745329238474369D;
/*  88 */         double d2 = Math.cos(d0);
/*  89 */         double d3 = Math.sin(d0);
/*  90 */         double d4 = Math.sin(d1);
/*     */         
/*  92 */         if (entity instanceof net.minecraft.entity.EntityHanging) {
/*     */           
/*  94 */           d2 = 0.0D;
/*  95 */           d3 = 0.0D;
/*  96 */           d4 = -1.0D;
/*     */         } 
/*     */         
/*  99 */         double d5 = Math.cos(d1);
/* 100 */         double d6 = interpolateValue(entity.prevPosX, entity.posX, partialTicks) - d2 * 0.7D - d3 * 0.5D * d5;
/* 101 */         double d7 = interpolateValue(entity.prevPosY + entity.getEyeHeight() * 0.7D, entity.posY + entity.getEyeHeight() * 0.7D, partialTicks) - d4 * 0.5D - 0.25D;
/* 102 */         double d8 = interpolateValue(entity.prevPosZ, entity.posZ, partialTicks) - d3 * 0.7D + d2 * 0.5D * d5;
/* 103 */         double d9 = interpolateValue(((EntityLiving)entityLivingIn).prevRenderYawOffset, ((EntityLiving)entityLivingIn).renderYawOffset, partialTicks) * 0.01745329238474369D + 1.5707963267948966D;
/* 104 */         d2 = Math.cos(d9) * ((EntityLiving)entityLivingIn).width * 0.4D;
/* 105 */         d3 = Math.sin(d9) * ((EntityLiving)entityLivingIn).width * 0.4D;
/* 106 */         double d10 = interpolateValue(((EntityLiving)entityLivingIn).prevPosX, ((EntityLiving)entityLivingIn).posX, partialTicks) + d2;
/* 107 */         double d11 = interpolateValue(((EntityLiving)entityLivingIn).prevPosY, ((EntityLiving)entityLivingIn).posY, partialTicks);
/* 108 */         double d12 = interpolateValue(((EntityLiving)entityLivingIn).prevPosZ, ((EntityLiving)entityLivingIn).posZ, partialTicks) + d3;
/* 109 */         x += d2;
/* 110 */         z += d3;
/* 111 */         double d13 = (float)(d6 - d10);
/* 112 */         double d14 = (float)(d7 - d11);
/* 113 */         double d15 = (float)(d8 - d12);
/* 114 */         GlStateManager.disableTexture2D();
/* 115 */         GlStateManager.disableLighting();
/* 116 */         GlStateManager.disableCull();
/*     */         
/* 118 */         if (Config.isShaders())
/*     */         {
/* 120 */           Shaders.beginLeash();
/*     */         }
/*     */         
/* 123 */         int i = 24;
/* 124 */         double d16 = 0.025D;
/* 125 */         worldrenderer.begin(5, DefaultVertexFormats.field_181706_f);
/*     */         
/* 127 */         for (int j = 0; j <= 24; j++) {
/*     */           
/* 129 */           float f = 0.5F;
/* 130 */           float f1 = 0.4F;
/* 131 */           float f2 = 0.3F;
/*     */           
/* 133 */           if (j % 2 == 0) {
/*     */             
/* 135 */             f *= 0.7F;
/* 136 */             f1 *= 0.7F;
/* 137 */             f2 *= 0.7F;
/*     */           } 
/*     */           
/* 140 */           float f3 = j / 24.0F;
/* 141 */           worldrenderer.pos(x + d13 * f3 + 0.0D, y + d14 * (f3 * f3 + f3) * 0.5D + ((24.0F - j) / 18.0F + 0.125F), z + d15 * f3).func_181666_a(f, f1, f2, 1.0F).endVertex();
/* 142 */           worldrenderer.pos(x + d13 * f3 + 0.025D, y + d14 * (f3 * f3 + f3) * 0.5D + ((24.0F - j) / 18.0F + 0.125F) + 0.025D, z + d15 * f3).func_181666_a(f, f1, f2, 1.0F).endVertex();
/*     */         } 
/*     */         
/* 145 */         tessellator.draw();
/* 146 */         worldrenderer.begin(5, DefaultVertexFormats.field_181706_f);
/*     */         
/* 148 */         for (int k = 0; k <= 24; k++) {
/*     */           
/* 150 */           float f4 = 0.5F;
/* 151 */           float f5 = 0.4F;
/* 152 */           float f6 = 0.3F;
/*     */           
/* 154 */           if (k % 2 == 0) {
/*     */             
/* 156 */             f4 *= 0.7F;
/* 157 */             f5 *= 0.7F;
/* 158 */             f6 *= 0.7F;
/*     */           } 
/*     */           
/* 161 */           float f7 = k / 24.0F;
/* 162 */           worldrenderer.pos(x + d13 * f7 + 0.0D, y + d14 * (f7 * f7 + f7) * 0.5D + ((24.0F - k) / 18.0F + 0.125F) + 0.025D, z + d15 * f7).func_181666_a(f4, f5, f6, 1.0F).endVertex();
/* 163 */           worldrenderer.pos(x + d13 * f7 + 0.025D, y + d14 * (f7 * f7 + f7) * 0.5D + ((24.0F - k) / 18.0F + 0.125F), z + d15 * f7 + 0.025D).func_181666_a(f4, f5, f6, 1.0F).endVertex();
/*     */         } 
/*     */         
/* 166 */         tessellator.draw();
/*     */         
/* 168 */         if (Config.isShaders())
/*     */         {
/* 170 */           Shaders.endLeash();
/*     */         }
/*     */         
/* 173 */         GlStateManager.enableLighting();
/* 174 */         GlStateManager.enableTexture2D();
/* 175 */         GlStateManager.enableCull();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\RenderLiving.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
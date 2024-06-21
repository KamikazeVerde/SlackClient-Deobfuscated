/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.projectile.EntityFishHook;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ public class RenderFish extends Render<EntityFishHook> {
/*  15 */   private static final ResourceLocation FISH_PARTICLES = new ResourceLocation("textures/particle/particles.png");
/*     */ 
/*     */   
/*     */   public RenderFish(RenderManager renderManagerIn) {
/*  19 */     super(renderManagerIn);
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
/*     */   public void doRender(EntityFishHook entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  32 */     GlStateManager.pushMatrix();
/*  33 */     GlStateManager.translate((float)x, (float)y, (float)z);
/*  34 */     GlStateManager.enableRescaleNormal();
/*  35 */     GlStateManager.scale(0.5F, 0.5F, 0.5F);
/*  36 */     bindEntityTexture(entity);
/*  37 */     Tessellator tessellator = Tessellator.getInstance();
/*  38 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  39 */     int i = 1;
/*  40 */     int j = 2;
/*  41 */     float f = 0.0625F;
/*  42 */     float f1 = 0.125F;
/*  43 */     float f2 = 0.125F;
/*  44 */     float f3 = 0.1875F;
/*  45 */     float f4 = 1.0F;
/*  46 */     float f5 = 0.5F;
/*  47 */     float f6 = 0.5F;
/*  48 */     GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/*  49 */     GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/*  50 */     worldrenderer.begin(7, DefaultVertexFormats.field_181710_j);
/*  51 */     worldrenderer.pos(-0.5D, -0.5D, 0.0D).tex(0.0625D, 0.1875D).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/*  52 */     worldrenderer.pos(0.5D, -0.5D, 0.0D).tex(0.125D, 0.1875D).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/*  53 */     worldrenderer.pos(0.5D, 0.5D, 0.0D).tex(0.125D, 0.125D).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/*  54 */     worldrenderer.pos(-0.5D, 0.5D, 0.0D).tex(0.0625D, 0.125D).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/*  55 */     tessellator.draw();
/*  56 */     GlStateManager.disableRescaleNormal();
/*  57 */     GlStateManager.popMatrix();
/*     */     
/*  59 */     if (entity.angler != null) {
/*     */       
/*  61 */       float f7 = entity.angler.getSwingProgress(partialTicks);
/*  62 */       float f8 = MathHelper.sin(MathHelper.sqrt_float(f7) * 3.1415927F);
/*  63 */       Vec3 vec3 = new Vec3(-0.36D, 0.03D, 0.35D);
/*  64 */       vec3 = vec3.rotatePitch(-(entity.angler.prevRotationPitch + (entity.angler.rotationPitch - entity.angler.prevRotationPitch) * partialTicks) * 3.1415927F / 180.0F);
/*  65 */       vec3 = vec3.rotateYaw(-(entity.angler.prevRotationYaw + (entity.angler.rotationYaw - entity.angler.prevRotationYaw) * partialTicks) * 3.1415927F / 180.0F);
/*  66 */       vec3 = vec3.rotateYaw(f8 * 0.5F);
/*  67 */       vec3 = vec3.rotatePitch(-f8 * 0.7F);
/*  68 */       double d0 = entity.angler.prevPosX + (entity.angler.posX - entity.angler.prevPosX) * partialTicks + vec3.xCoord;
/*  69 */       double d1 = entity.angler.prevPosY + (entity.angler.posY - entity.angler.prevPosY) * partialTicks + vec3.yCoord;
/*  70 */       double d2 = entity.angler.prevPosZ + (entity.angler.posZ - entity.angler.prevPosZ) * partialTicks + vec3.zCoord;
/*  71 */       double d3 = entity.angler.getEyeHeight();
/*     */       
/*  73 */       if ((this.renderManager.options != null && this.renderManager.options.thirdPersonView > 0) || entity.angler != (Minecraft.getMinecraft()).thePlayer) {
/*     */         
/*  75 */         float f9 = (entity.angler.prevRenderYawOffset + (entity.angler.renderYawOffset - entity.angler.prevRenderYawOffset) * partialTicks) * 3.1415927F / 180.0F;
/*  76 */         double d4 = MathHelper.sin(f9);
/*  77 */         double d6 = MathHelper.cos(f9);
/*  78 */         double d8 = 0.35D;
/*  79 */         double d10 = 0.8D;
/*  80 */         d0 = entity.angler.prevPosX + (entity.angler.posX - entity.angler.prevPosX) * partialTicks - d6 * 0.35D - d4 * 0.8D;
/*  81 */         d1 = entity.angler.prevPosY + d3 + (entity.angler.posY - entity.angler.prevPosY) * partialTicks - 0.45D;
/*  82 */         d2 = entity.angler.prevPosZ + (entity.angler.posZ - entity.angler.prevPosZ) * partialTicks - d4 * 0.35D + d6 * 0.8D;
/*  83 */         d3 = entity.angler.isSneaking() ? -0.1875D : 0.0D;
/*     */       } 
/*     */       
/*  86 */       double d13 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
/*  87 */       double d5 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + 0.25D;
/*  88 */       double d7 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
/*  89 */       double d9 = (float)(d0 - d13);
/*  90 */       double d11 = (float)(d1 - d5) + d3;
/*  91 */       double d12 = (float)(d2 - d7);
/*  92 */       GlStateManager.disableTexture2D();
/*  93 */       GlStateManager.disableLighting();
/*  94 */       worldrenderer.begin(3, DefaultVertexFormats.field_181706_f);
/*  95 */       int k = 16;
/*     */       
/*  97 */       for (int l = 0; l <= 16; l++) {
/*     */         
/*  99 */         float f10 = l / 16.0F;
/* 100 */         worldrenderer.pos(x + d9 * f10, y + d11 * (f10 * f10 + f10) * 0.5D + 0.25D, z + d12 * f10).func_181669_b(0, 0, 0, 255).endVertex();
/*     */       } 
/*     */       
/* 103 */       tessellator.draw();
/* 104 */       GlStateManager.enableLighting();
/* 105 */       GlStateManager.enableTexture2D();
/* 106 */       super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityFishHook entity) {
/* 115 */     return FISH_PARTICLES;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\RenderFish.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
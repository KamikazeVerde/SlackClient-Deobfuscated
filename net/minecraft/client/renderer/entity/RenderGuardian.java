/*     */ package net.minecraft.client.renderer.entity;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelGuardian;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.monster.EntityGuardian;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class RenderGuardian extends RenderLiving<EntityGuardian> {
/*  19 */   private static final ResourceLocation GUARDIAN_TEXTURE = new ResourceLocation("textures/entity/guardian.png");
/*  20 */   private static final ResourceLocation GUARDIAN_ELDER_TEXTURE = new ResourceLocation("textures/entity/guardian_elder.png");
/*  21 */   private static final ResourceLocation GUARDIAN_BEAM_TEXTURE = new ResourceLocation("textures/entity/guardian_beam.png");
/*     */   
/*     */   int field_177115_a;
/*     */   
/*     */   public RenderGuardian(RenderManager renderManagerIn) {
/*  26 */     super(renderManagerIn, (ModelBase)new ModelGuardian(), 0.5F);
/*  27 */     this.field_177115_a = ((ModelGuardian)this.mainModel).func_178706_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender(EntityGuardian livingEntity, ICamera camera, double camX, double camY, double camZ) {
/*  32 */     if (super.shouldRender(livingEntity, camera, camX, camY, camZ))
/*     */     {
/*  34 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  38 */     if (livingEntity.hasTargetedEntity()) {
/*     */       
/*  40 */       EntityLivingBase entitylivingbase = livingEntity.getTargetedEntity();
/*     */       
/*  42 */       if (entitylivingbase != null) {
/*     */         
/*  44 */         Vec3 vec3 = func_177110_a(entitylivingbase, entitylivingbase.height * 0.5D, 1.0F);
/*  45 */         Vec3 vec31 = func_177110_a((EntityLivingBase)livingEntity, livingEntity.getEyeHeight(), 1.0F);
/*     */         
/*  47 */         if (camera.isBoundingBoxInFrustum(AxisAlignedBB.fromBounds(vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord, vec3.zCoord)))
/*     */         {
/*  49 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  54 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Vec3 func_177110_a(EntityLivingBase entityLivingBaseIn, double p_177110_2_, float p_177110_4_) {
/*  60 */     double d0 = entityLivingBaseIn.lastTickPosX + (entityLivingBaseIn.posX - entityLivingBaseIn.lastTickPosX) * p_177110_4_;
/*  61 */     double d1 = p_177110_2_ + entityLivingBaseIn.lastTickPosY + (entityLivingBaseIn.posY - entityLivingBaseIn.lastTickPosY) * p_177110_4_;
/*  62 */     double d2 = entityLivingBaseIn.lastTickPosZ + (entityLivingBaseIn.posZ - entityLivingBaseIn.lastTickPosZ) * p_177110_4_;
/*  63 */     return new Vec3(d0, d1, d2);
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
/*     */   public void doRender(EntityGuardian entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  76 */     if (this.field_177115_a != ((ModelGuardian)this.mainModel).func_178706_a()) {
/*     */       
/*  78 */       this.mainModel = (ModelBase)new ModelGuardian();
/*  79 */       this.field_177115_a = ((ModelGuardian)this.mainModel).func_178706_a();
/*     */     } 
/*     */     
/*  82 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*  83 */     EntityLivingBase entitylivingbase = entity.getTargetedEntity();
/*     */     
/*  85 */     if (entitylivingbase != null) {
/*     */       
/*  87 */       float f = entity.func_175477_p(partialTicks);
/*  88 */       Tessellator tessellator = Tessellator.getInstance();
/*  89 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  90 */       bindTexture(GUARDIAN_BEAM_TEXTURE);
/*  91 */       GL11.glTexParameterf(3553, 10242, 10497.0F);
/*  92 */       GL11.glTexParameterf(3553, 10243, 10497.0F);
/*  93 */       GlStateManager.disableLighting();
/*  94 */       GlStateManager.disableCull();
/*  95 */       GlStateManager.disableBlend();
/*  96 */       GlStateManager.depthMask(true);
/*  97 */       float f1 = 240.0F;
/*  98 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f1, f1);
/*  99 */       GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
/* 100 */       float f2 = (float)entity.worldObj.getTotalWorldTime() + partialTicks;
/* 101 */       float f3 = f2 * 0.5F % 1.0F;
/* 102 */       float f4 = entity.getEyeHeight();
/* 103 */       GlStateManager.pushMatrix();
/* 104 */       GlStateManager.translate((float)x, (float)y + f4, (float)z);
/* 105 */       Vec3 vec3 = func_177110_a(entitylivingbase, entitylivingbase.height * 0.5D, partialTicks);
/* 106 */       Vec3 vec31 = func_177110_a((EntityLivingBase)entity, f4, partialTicks);
/* 107 */       Vec3 vec32 = vec3.subtract(vec31);
/* 108 */       double d0 = vec32.lengthVector() + 1.0D;
/* 109 */       vec32 = vec32.normalize();
/* 110 */       float f5 = (float)Math.acos(vec32.yCoord);
/* 111 */       float f6 = (float)Math.atan2(vec32.zCoord, vec32.xCoord);
/* 112 */       GlStateManager.rotate((1.5707964F + -f6) * 57.295776F, 0.0F, 1.0F, 0.0F);
/* 113 */       GlStateManager.rotate(f5 * 57.295776F, 1.0F, 0.0F, 0.0F);
/* 114 */       int i = 1;
/* 115 */       double d1 = f2 * 0.05D * (1.0D - (i & 0x1) * 2.5D);
/* 116 */       worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/* 117 */       float f7 = f * f;
/* 118 */       int j = 64 + (int)(f7 * 240.0F);
/* 119 */       int k = 32 + (int)(f7 * 192.0F);
/* 120 */       int l = 128 - (int)(f7 * 64.0F);
/* 121 */       double d2 = i * 0.2D;
/* 122 */       double d3 = d2 * 1.41D;
/* 123 */       double d4 = 0.0D + Math.cos(d1 + 2.356194490192345D) * d3;
/* 124 */       double d5 = 0.0D + Math.sin(d1 + 2.356194490192345D) * d3;
/* 125 */       double d6 = 0.0D + Math.cos(d1 + 0.7853981633974483D) * d3;
/* 126 */       double d7 = 0.0D + Math.sin(d1 + 0.7853981633974483D) * d3;
/* 127 */       double d8 = 0.0D + Math.cos(d1 + 3.9269908169872414D) * d3;
/* 128 */       double d9 = 0.0D + Math.sin(d1 + 3.9269908169872414D) * d3;
/* 129 */       double d10 = 0.0D + Math.cos(d1 + 5.497787143782138D) * d3;
/* 130 */       double d11 = 0.0D + Math.sin(d1 + 5.497787143782138D) * d3;
/* 131 */       double d12 = 0.0D + Math.cos(d1 + Math.PI) * d2;
/* 132 */       double d13 = 0.0D + Math.sin(d1 + Math.PI) * d2;
/* 133 */       double d14 = 0.0D + Math.cos(d1 + 0.0D) * d2;
/* 134 */       double d15 = 0.0D + Math.sin(d1 + 0.0D) * d2;
/* 135 */       double d16 = 0.0D + Math.cos(d1 + 1.5707963267948966D) * d2;
/* 136 */       double d17 = 0.0D + Math.sin(d1 + 1.5707963267948966D) * d2;
/* 137 */       double d18 = 0.0D + Math.cos(d1 + 4.71238898038469D) * d2;
/* 138 */       double d19 = 0.0D + Math.sin(d1 + 4.71238898038469D) * d2;
/* 139 */       double d20 = 0.0D;
/* 140 */       double d21 = 0.4999D;
/* 141 */       double d22 = (-1.0F + f3);
/* 142 */       double d23 = d0 * 0.5D / d2 + d22;
/* 143 */       worldrenderer.pos(d12, d0, d13).tex(0.4999D, d23).func_181669_b(j, k, l, 255).endVertex();
/* 144 */       worldrenderer.pos(d12, 0.0D, d13).tex(0.4999D, d22).func_181669_b(j, k, l, 255).endVertex();
/* 145 */       worldrenderer.pos(d14, 0.0D, d15).tex(0.0D, d22).func_181669_b(j, k, l, 255).endVertex();
/* 146 */       worldrenderer.pos(d14, d0, d15).tex(0.0D, d23).func_181669_b(j, k, l, 255).endVertex();
/* 147 */       worldrenderer.pos(d16, d0, d17).tex(0.4999D, d23).func_181669_b(j, k, l, 255).endVertex();
/* 148 */       worldrenderer.pos(d16, 0.0D, d17).tex(0.4999D, d22).func_181669_b(j, k, l, 255).endVertex();
/* 149 */       worldrenderer.pos(d18, 0.0D, d19).tex(0.0D, d22).func_181669_b(j, k, l, 255).endVertex();
/* 150 */       worldrenderer.pos(d18, d0, d19).tex(0.0D, d23).func_181669_b(j, k, l, 255).endVertex();
/* 151 */       double d24 = 0.0D;
/*     */       
/* 153 */       if (entity.ticksExisted % 2 == 0)
/*     */       {
/* 155 */         d24 = 0.5D;
/*     */       }
/*     */       
/* 158 */       worldrenderer.pos(d4, d0, d5).tex(0.5D, d24 + 0.5D).func_181669_b(j, k, l, 255).endVertex();
/* 159 */       worldrenderer.pos(d6, d0, d7).tex(1.0D, d24 + 0.5D).func_181669_b(j, k, l, 255).endVertex();
/* 160 */       worldrenderer.pos(d10, d0, d11).tex(1.0D, d24).func_181669_b(j, k, l, 255).endVertex();
/* 161 */       worldrenderer.pos(d8, d0, d9).tex(0.5D, d24).func_181669_b(j, k, l, 255).endVertex();
/* 162 */       tessellator.draw();
/* 163 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preRenderCallback(EntityGuardian entitylivingbaseIn, float partialTickTime) {
/* 173 */     if (entitylivingbaseIn.isElder())
/*     */     {
/* 175 */       GlStateManager.scale(2.35F, 2.35F, 2.35F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityGuardian entity) {
/* 184 */     return entity.isElder() ? GUARDIAN_ELDER_TEXTURE : GUARDIAN_TEXTURE;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\RenderGuardian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
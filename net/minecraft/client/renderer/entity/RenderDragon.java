/*     */ package net.minecraft.client.renderer.entity;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelDragon;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerEnderDragonDeath;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class RenderDragon extends RenderLiving<EntityDragon> {
/*  18 */   private static final ResourceLocation enderDragonCrystalBeamTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
/*  19 */   private static final ResourceLocation enderDragonExplodingTextures = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
/*  20 */   private static final ResourceLocation enderDragonTextures = new ResourceLocation("textures/entity/enderdragon/dragon.png");
/*     */ 
/*     */   
/*     */   protected ModelDragon modelDragon;
/*     */ 
/*     */   
/*     */   public RenderDragon(RenderManager renderManagerIn) {
/*  27 */     super(renderManagerIn, (ModelBase)new ModelDragon(0.0F), 0.5F);
/*  28 */     this.modelDragon = (ModelDragon)this.mainModel;
/*  29 */     addLayer(new LayerEnderDragonEyes(this));
/*  30 */     addLayer(new LayerEnderDragonDeath());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void rotateCorpse(EntityDragon bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
/*  35 */     float f = (float)bat.getMovementOffsets(7, partialTicks)[0];
/*  36 */     float f1 = (float)(bat.getMovementOffsets(5, partialTicks)[1] - bat.getMovementOffsets(10, partialTicks)[1]);
/*  37 */     GlStateManager.rotate(-f, 0.0F, 1.0F, 0.0F);
/*  38 */     GlStateManager.rotate(f1 * 10.0F, 1.0F, 0.0F, 0.0F);
/*  39 */     GlStateManager.translate(0.0F, 0.0F, 1.0F);
/*     */     
/*  41 */     if (bat.deathTime > 0) {
/*     */       
/*  43 */       float f2 = (bat.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
/*  44 */       f2 = MathHelper.sqrt_float(f2);
/*     */       
/*  46 */       if (f2 > 1.0F)
/*     */       {
/*  48 */         f2 = 1.0F;
/*     */       }
/*     */       
/*  51 */       GlStateManager.rotate(f2 * getDeathMaxRotation(bat), 0.0F, 0.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderModel(EntityDragon entitylivingbaseIn, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_) {
/*  60 */     if (entitylivingbaseIn.deathTicks > 0) {
/*     */       
/*  62 */       float f = entitylivingbaseIn.deathTicks / 200.0F;
/*  63 */       GlStateManager.depthFunc(515);
/*  64 */       GlStateManager.enableAlpha();
/*  65 */       GlStateManager.alphaFunc(516, f);
/*  66 */       bindTexture(enderDragonExplodingTextures);
/*  67 */       this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
/*  68 */       GlStateManager.alphaFunc(516, 0.1F);
/*  69 */       GlStateManager.depthFunc(514);
/*     */     } 
/*     */     
/*  72 */     bindEntityTexture(entitylivingbaseIn);
/*  73 */     this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
/*     */     
/*  75 */     if (entitylivingbaseIn.hurtTime > 0) {
/*     */       
/*  77 */       GlStateManager.depthFunc(514);
/*  78 */       GlStateManager.disableTexture2D();
/*  79 */       GlStateManager.enableBlend();
/*  80 */       GlStateManager.blendFunc(770, 771);
/*  81 */       GlStateManager.color(1.0F, 0.0F, 0.0F, 0.5F);
/*  82 */       this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
/*  83 */       GlStateManager.enableTexture2D();
/*  84 */       GlStateManager.disableBlend();
/*  85 */       GlStateManager.depthFunc(515);
/*     */     } 
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
/*     */   public void doRender(EntityDragon entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  99 */     BossStatus.setBossStatus((IBossDisplayData)entity, false);
/* 100 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */     
/* 102 */     if (entity.healingEnderCrystal != null)
/*     */     {
/* 104 */       drawRechargeRay(entity, x, y, z, partialTicks);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawRechargeRay(EntityDragon dragon, double p_180574_2_, double p_180574_4_, double p_180574_6_, float p_180574_8_) {
/* 113 */     float f = dragon.healingEnderCrystal.innerRotation + p_180574_8_;
/* 114 */     float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
/* 115 */     f1 = (f1 * f1 + f1) * 0.2F;
/* 116 */     float f2 = (float)(dragon.healingEnderCrystal.posX - dragon.posX - (dragon.prevPosX - dragon.posX) * (1.0F - p_180574_8_));
/* 117 */     float f3 = (float)(f1 + dragon.healingEnderCrystal.posY - 1.0D - dragon.posY - (dragon.prevPosY - dragon.posY) * (1.0F - p_180574_8_));
/* 118 */     float f4 = (float)(dragon.healingEnderCrystal.posZ - dragon.posZ - (dragon.prevPosZ - dragon.posZ) * (1.0F - p_180574_8_));
/* 119 */     float f5 = MathHelper.sqrt_float(f2 * f2 + f4 * f4);
/* 120 */     float f6 = MathHelper.sqrt_float(f2 * f2 + f3 * f3 + f4 * f4);
/* 121 */     GlStateManager.pushMatrix();
/* 122 */     GlStateManager.translate((float)p_180574_2_, (float)p_180574_4_ + 2.0F, (float)p_180574_6_);
/* 123 */     GlStateManager.rotate((float)-Math.atan2(f4, f2) * 180.0F / 3.1415927F - 90.0F, 0.0F, 1.0F, 0.0F);
/* 124 */     GlStateManager.rotate((float)-Math.atan2(f5, f3) * 180.0F / 3.1415927F - 90.0F, 1.0F, 0.0F, 0.0F);
/* 125 */     Tessellator tessellator = Tessellator.getInstance();
/* 126 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 127 */     RenderHelper.disableStandardItemLighting();
/* 128 */     GlStateManager.disableCull();
/* 129 */     bindTexture(enderDragonCrystalBeamTextures);
/* 130 */     GlStateManager.shadeModel(7425);
/* 131 */     float f7 = 0.0F - (dragon.ticksExisted + p_180574_8_) * 0.01F;
/* 132 */     float f8 = MathHelper.sqrt_float(f2 * f2 + f3 * f3 + f4 * f4) / 32.0F - (dragon.ticksExisted + p_180574_8_) * 0.01F;
/* 133 */     worldrenderer.begin(5, DefaultVertexFormats.field_181709_i);
/* 134 */     int i = 8;
/*     */     
/* 136 */     for (int j = 0; j <= 8; j++) {
/*     */       
/* 138 */       float f9 = MathHelper.sin((j % 8) * 3.1415927F * 2.0F / 8.0F) * 0.75F;
/* 139 */       float f10 = MathHelper.cos((j % 8) * 3.1415927F * 2.0F / 8.0F) * 0.75F;
/* 140 */       float f11 = (j % 8) * 1.0F / 8.0F;
/* 141 */       worldrenderer.pos((f9 * 0.2F), (f10 * 0.2F), 0.0D).tex(f11, f8).func_181669_b(0, 0, 0, 255).endVertex();
/* 142 */       worldrenderer.pos(f9, f10, f6).tex(f11, f7).func_181669_b(255, 255, 255, 255).endVertex();
/*     */     } 
/*     */     
/* 145 */     tessellator.draw();
/* 146 */     GlStateManager.enableCull();
/* 147 */     GlStateManager.shadeModel(7424);
/* 148 */     RenderHelper.enableStandardItemLighting();
/* 149 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityDragon entity) {
/* 157 */     return enderDragonTextures;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\RenderDragon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
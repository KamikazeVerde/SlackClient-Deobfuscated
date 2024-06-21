/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ 
/*     */ public class ModelPlayer
/*     */   extends ModelBiped
/*     */ {
/*     */   public ModelRenderer bipedLeftArmwear;
/*     */   public ModelRenderer bipedRightArmwear;
/*     */   public ModelRenderer bipedLeftLegwear;
/*     */   public ModelRenderer bipedRightLegwear;
/*     */   public ModelRenderer bipedBodyWear;
/*     */   private ModelRenderer bipedCape;
/*     */   private ModelRenderer bipedDeadmau5Head;
/*     */   private boolean smallArms;
/*     */   
/*     */   public ModelPlayer(float p_i46304_1_, boolean p_i46304_2_) {
/*  19 */     super(p_i46304_1_, 0.0F, 64, 64);
/*  20 */     this.smallArms = p_i46304_2_;
/*  21 */     this.bipedDeadmau5Head = new ModelRenderer(this, 24, 0);
/*  22 */     this.bipedDeadmau5Head.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, p_i46304_1_);
/*  23 */     this.bipedCape = new ModelRenderer(this, 0, 0);
/*  24 */     this.bipedCape.setTextureSize(64, 32);
/*  25 */     this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, p_i46304_1_);
/*     */     
/*  27 */     if (p_i46304_2_) {
/*     */       
/*  29 */       this.bipedLeftArm = new ModelRenderer(this, 32, 48);
/*  30 */       this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_);
/*  31 */       this.bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
/*  32 */       this.bipedRightArm = new ModelRenderer(this, 40, 16);
/*  33 */       this.bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_);
/*  34 */       this.bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
/*  35 */       this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
/*  36 */       this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_ + 0.25F);
/*  37 */       this.bipedLeftArmwear.setRotationPoint(5.0F, 2.5F, 0.0F);
/*  38 */       this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
/*  39 */       this.bipedRightArmwear.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_ + 0.25F);
/*  40 */       this.bipedRightArmwear.setRotationPoint(-5.0F, 2.5F, 10.0F);
/*     */     }
/*     */     else {
/*     */       
/*  44 */       this.bipedLeftArm = new ModelRenderer(this, 32, 48);
/*  45 */       this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_);
/*  46 */       this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
/*  47 */       this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
/*  48 */       this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
/*  49 */       this.bipedLeftArmwear.setRotationPoint(5.0F, 2.0F, 0.0F);
/*  50 */       this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
/*  51 */       this.bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
/*  52 */       this.bipedRightArmwear.setRotationPoint(-5.0F, 2.0F, 10.0F);
/*     */     } 
/*     */     
/*  55 */     this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
/*  56 */     this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_);
/*  57 */     this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
/*  58 */     this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
/*  59 */     this.bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
/*  60 */     this.bipedLeftLegwear.setRotationPoint(1.9F, 12.0F, 0.0F);
/*  61 */     this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
/*  62 */     this.bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
/*  63 */     this.bipedRightLegwear.setRotationPoint(-1.9F, 12.0F, 0.0F);
/*  64 */     this.bipedBodyWear = new ModelRenderer(this, 16, 32);
/*  65 */     this.bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i46304_1_ + 0.25F);
/*  66 */     this.bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/*  74 */     super.render(entityIn, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale);
/*  75 */     GlStateManager.pushMatrix();
/*     */     
/*  77 */     if (this.isChild) {
/*     */       
/*  79 */       float f = 2.0F;
/*  80 */       GlStateManager.scale(1.0F / f, 1.0F / f, 1.0F / f);
/*  81 */       GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
/*  82 */       this.bipedLeftLegwear.render(scale);
/*  83 */       this.bipedRightLegwear.render(scale);
/*  84 */       this.bipedLeftArmwear.render(scale);
/*  85 */       this.bipedRightArmwear.render(scale);
/*  86 */       this.bipedBodyWear.render(scale);
/*     */     }
/*     */     else {
/*     */       
/*  90 */       if (entityIn.isSneaking())
/*     */       {
/*  92 */         GlStateManager.translate(0.0F, 0.2F, 0.0F);
/*     */       }
/*     */       
/*  95 */       this.bipedLeftLegwear.render(scale);
/*  96 */       this.bipedRightLegwear.render(scale);
/*  97 */       this.bipedLeftArmwear.render(scale);
/*  98 */       this.bipedRightArmwear.render(scale);
/*  99 */       this.bipedBodyWear.render(scale);
/*     */     } 
/*     */     
/* 102 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderDeadmau5Head(float p_178727_1_) {
/* 107 */     copyModelAngles(this.bipedHead, this.bipedDeadmau5Head);
/* 108 */     this.bipedDeadmau5Head.rotationPointX = 0.0F;
/* 109 */     this.bipedDeadmau5Head.rotationPointY = 0.0F;
/* 110 */     this.bipedDeadmau5Head.render(p_178727_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderCape(float p_178728_1_) {
/* 115 */     this.bipedCape.render(p_178728_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity entityIn) {
/* 125 */     super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, entityIn);
/* 126 */     copyModelAngles(this.bipedLeftLeg, this.bipedLeftLegwear);
/* 127 */     copyModelAngles(this.bipedRightLeg, this.bipedRightLegwear);
/* 128 */     copyModelAngles(this.bipedLeftArm, this.bipedLeftArmwear);
/* 129 */     copyModelAngles(this.bipedRightArm, this.bipedRightArmwear);
/* 130 */     copyModelAngles(this.bipedBody, this.bipedBodyWear);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderRightArm() {
/* 135 */     this.bipedRightArm.render(0.0625F);
/* 136 */     this.bipedRightArmwear.render(0.0625F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderLeftArm() {
/* 141 */     this.bipedLeftArm.render(0.0625F);
/* 142 */     this.bipedLeftArmwear.render(0.0625F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInvisible(boolean invisible) {
/* 147 */     super.setInvisible(invisible);
/* 148 */     this.bipedLeftArmwear.showModel = invisible;
/* 149 */     this.bipedRightArmwear.showModel = invisible;
/* 150 */     this.bipedLeftLegwear.showModel = invisible;
/* 151 */     this.bipedRightLegwear.showModel = invisible;
/* 152 */     this.bipedBodyWear.showModel = invisible;
/* 153 */     this.bipedCape.showModel = invisible;
/* 154 */     this.bipedDeadmau5Head.showModel = invisible;
/*     */   }
/*     */ 
/*     */   
/*     */   public void postRenderArm(float scale) {
/* 159 */     if (this.smallArms) {
/*     */       
/* 161 */       this.bipedRightArm.rotationPointX++;
/* 162 */       this.bipedRightArm.postRender(scale);
/* 163 */       this.bipedRightArm.rotationPointX--;
/*     */     }
/*     */     else {
/*     */       
/* 167 */       this.bipedRightArm.postRender(scale);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\model\ModelPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
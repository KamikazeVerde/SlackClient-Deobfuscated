/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.entity.model.anim.ModelUpdater;
/*     */ import net.optifine.model.ModelSprite;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ 
/*     */ 
/*     */ public class ModelRenderer
/*     */ {
/*     */   public float textureWidth;
/*     */   public float textureHeight;
/*     */   private int textureOffsetX;
/*     */   private int textureOffsetY;
/*     */   public float rotationPointX;
/*     */   public float rotationPointY;
/*     */   public float rotationPointZ;
/*     */   public float rotateAngleX;
/*     */   public float rotateAngleY;
/*     */   public float rotateAngleZ;
/*     */   private boolean compiled;
/*     */   private int displayList;
/*     */   public boolean mirror;
/*     */   public boolean showModel;
/*     */   public boolean isHidden;
/*     */   public List<ModelBox> cubeList;
/*     */   public List<ModelRenderer> childModels;
/*     */   public final String boxName;
/*     */   private ModelBase baseModel;
/*     */   public float offsetX;
/*     */   public float offsetY;
/*     */   public float offsetZ;
/*     */   public List spriteList;
/*     */   public boolean mirrorV;
/*     */   public float scaleX;
/*     */   public float scaleY;
/*     */   public float scaleZ;
/*     */   private int countResetDisplayList;
/*     */   private ResourceLocation textureLocation;
/*     */   private String id;
/*     */   private ModelUpdater modelUpdater;
/*     */   private RenderGlobal renderGlobal;
/*     */   
/*     */   public ModelRenderer(ModelBase model, String boxNameIn) {
/*  66 */     this.spriteList = new ArrayList();
/*  67 */     this.mirrorV = false;
/*  68 */     this.scaleX = 1.0F;
/*  69 */     this.scaleY = 1.0F;
/*  70 */     this.scaleZ = 1.0F;
/*  71 */     this.textureLocation = null;
/*  72 */     this.id = null;
/*  73 */     this.renderGlobal = Config.getRenderGlobal();
/*  74 */     this.textureWidth = 64.0F;
/*  75 */     this.textureHeight = 32.0F;
/*  76 */     this.showModel = true;
/*  77 */     this.cubeList = Lists.newArrayList();
/*  78 */     this.baseModel = model;
/*  79 */     model.boxList.add(this);
/*  80 */     this.boxName = boxNameIn;
/*  81 */     setTextureSize(model.textureWidth, model.textureHeight);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer(ModelBase model) {
/*  86 */     this(model, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer(ModelBase model, int texOffX, int texOffY) {
/*  91 */     this(model);
/*  92 */     setTextureOffset(texOffX, texOffY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChild(ModelRenderer renderer) {
/* 100 */     if (this.childModels == null)
/*     */     {
/* 102 */       this.childModels = Lists.newArrayList();
/*     */     }
/*     */     
/* 105 */     this.childModels.add(renderer);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer setTextureOffset(int x, int y) {
/* 110 */     this.textureOffsetX = x;
/* 111 */     this.textureOffsetY = y;
/* 112 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer addBox(String partName, float offX, float offY, float offZ, int width, int height, int depth) {
/* 117 */     partName = this.boxName + "." + partName;
/* 118 */     TextureOffset textureoffset = this.baseModel.getTextureOffset(partName);
/* 119 */     setTextureOffset(textureoffset.textureOffsetX, textureoffset.textureOffsetY);
/* 120 */     this.cubeList.add((new ModelBox(this, this.textureOffsetX, this.textureOffsetY, offX, offY, offZ, width, height, depth, 0.0F)).setBoxName(partName));
/* 121 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer addBox(float offX, float offY, float offZ, int width, int height, int depth) {
/* 126 */     this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, offX, offY, offZ, width, height, depth, 0.0F));
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer addBox(float p_178769_1_, float p_178769_2_, float p_178769_3_, int p_178769_4_, int p_178769_5_, int p_178769_6_, boolean p_178769_7_) {
/* 132 */     this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, p_178769_1_, p_178769_2_, p_178769_3_, p_178769_4_, p_178769_5_, p_178769_6_, 0.0F, p_178769_7_));
/* 133 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBox(float p_78790_1_, float p_78790_2_, float p_78790_3_, int width, int height, int depth, float scaleFactor) {
/* 141 */     this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, p_78790_1_, p_78790_2_, p_78790_3_, width, height, depth, scaleFactor));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRotationPoint(float rotationPointXIn, float rotationPointYIn, float rotationPointZIn) {
/* 146 */     this.rotationPointX = rotationPointXIn;
/* 147 */     this.rotationPointY = rotationPointYIn;
/* 148 */     this.rotationPointZ = rotationPointZIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(float p_78785_1_) {
/* 153 */     if (!this.isHidden && this.showModel) {
/*     */       
/* 155 */       checkResetDisplayList();
/*     */       
/* 157 */       if (!this.compiled)
/*     */       {
/* 159 */         compileDisplayList(p_78785_1_);
/*     */       }
/*     */       
/* 162 */       int i = 0;
/*     */       
/* 164 */       if (this.textureLocation != null && !this.renderGlobal.renderOverlayDamaged) {
/*     */         
/* 166 */         if (this.renderGlobal.renderOverlayEyes) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 171 */         i = GlStateManager.getBoundTexture();
/* 172 */         Config.getTextureManager().bindTexture(this.textureLocation);
/*     */       } 
/*     */       
/* 175 */       if (this.modelUpdater != null)
/*     */       {
/* 177 */         this.modelUpdater.update();
/*     */       }
/*     */       
/* 180 */       boolean flag = (this.scaleX != 1.0F || this.scaleY != 1.0F || this.scaleZ != 1.0F);
/* 181 */       GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);
/*     */       
/* 183 */       if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
/*     */         
/* 185 */         if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F)
/*     */         {
/* 187 */           if (flag)
/*     */           {
/* 189 */             GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
/*     */           }
/*     */           
/* 192 */           GlStateManager.callList(this.displayList);
/*     */           
/* 194 */           if (this.childModels != null)
/*     */           {
/* 196 */             for (int l = 0; l < this.childModels.size(); l++)
/*     */             {
/* 198 */               ((ModelRenderer)this.childModels.get(l)).render(p_78785_1_);
/*     */             }
/*     */           }
/*     */           
/* 202 */           if (flag)
/*     */           {
/* 204 */             GlStateManager.scale(1.0F / this.scaleX, 1.0F / this.scaleY, 1.0F / this.scaleZ);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 209 */           GlStateManager.translate(this.rotationPointX * p_78785_1_, this.rotationPointY * p_78785_1_, this.rotationPointZ * p_78785_1_);
/*     */           
/* 211 */           if (flag)
/*     */           {
/* 213 */             GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
/*     */           }
/*     */           
/* 216 */           GlStateManager.callList(this.displayList);
/*     */           
/* 218 */           if (this.childModels != null)
/*     */           {
/* 220 */             for (int k = 0; k < this.childModels.size(); k++)
/*     */             {
/* 222 */               ((ModelRenderer)this.childModels.get(k)).render(p_78785_1_);
/*     */             }
/*     */           }
/*     */           
/* 226 */           if (flag)
/*     */           {
/* 228 */             GlStateManager.scale(1.0F / this.scaleX, 1.0F / this.scaleY, 1.0F / this.scaleZ);
/*     */           }
/*     */           
/* 231 */           GlStateManager.translate(-this.rotationPointX * p_78785_1_, -this.rotationPointY * p_78785_1_, -this.rotationPointZ * p_78785_1_);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 236 */         GlStateManager.pushMatrix();
/* 237 */         GlStateManager.translate(this.rotationPointX * p_78785_1_, this.rotationPointY * p_78785_1_, this.rotationPointZ * p_78785_1_);
/*     */         
/* 239 */         if (this.rotateAngleZ != 0.0F)
/*     */         {
/* 241 */           GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */         }
/*     */         
/* 244 */         if (this.rotateAngleY != 0.0F)
/*     */         {
/* 246 */           GlStateManager.rotate(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 249 */         if (this.rotateAngleX != 0.0F)
/*     */         {
/* 251 */           GlStateManager.rotate(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */         }
/*     */         
/* 254 */         if (flag)
/*     */         {
/* 256 */           GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
/*     */         }
/*     */         
/* 259 */         GlStateManager.callList(this.displayList);
/*     */         
/* 261 */         if (this.childModels != null)
/*     */         {
/* 263 */           for (int j = 0; j < this.childModels.size(); j++)
/*     */           {
/* 265 */             ((ModelRenderer)this.childModels.get(j)).render(p_78785_1_);
/*     */           }
/*     */         }
/*     */         
/* 269 */         GlStateManager.popMatrix();
/*     */       } 
/*     */       
/* 272 */       GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);
/*     */       
/* 274 */       if (i != 0)
/*     */       {
/* 276 */         GlStateManager.bindTexture(i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderWithRotation(float p_78791_1_) {
/* 283 */     if (!this.isHidden && this.showModel) {
/*     */       
/* 285 */       checkResetDisplayList();
/*     */       
/* 287 */       if (!this.compiled)
/*     */       {
/* 289 */         compileDisplayList(p_78791_1_);
/*     */       }
/*     */       
/* 292 */       int i = 0;
/*     */       
/* 294 */       if (this.textureLocation != null && !this.renderGlobal.renderOverlayDamaged) {
/*     */         
/* 296 */         if (this.renderGlobal.renderOverlayEyes) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 301 */         i = GlStateManager.getBoundTexture();
/* 302 */         Config.getTextureManager().bindTexture(this.textureLocation);
/*     */       } 
/*     */       
/* 305 */       if (this.modelUpdater != null)
/*     */       {
/* 307 */         this.modelUpdater.update();
/*     */       }
/*     */       
/* 310 */       boolean flag = (this.scaleX != 1.0F || this.scaleY != 1.0F || this.scaleZ != 1.0F);
/* 311 */       GlStateManager.pushMatrix();
/* 312 */       GlStateManager.translate(this.rotationPointX * p_78791_1_, this.rotationPointY * p_78791_1_, this.rotationPointZ * p_78791_1_);
/*     */       
/* 314 */       if (this.rotateAngleY != 0.0F)
/*     */       {
/* 316 */         GlStateManager.rotate(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */       }
/*     */       
/* 319 */       if (this.rotateAngleX != 0.0F)
/*     */       {
/* 321 */         GlStateManager.rotate(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */       }
/*     */       
/* 324 */       if (this.rotateAngleZ != 0.0F)
/*     */       {
/* 326 */         GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */       }
/*     */       
/* 329 */       if (flag)
/*     */       {
/* 331 */         GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
/*     */       }
/*     */       
/* 334 */       GlStateManager.callList(this.displayList);
/*     */       
/* 336 */       if (this.childModels != null)
/*     */       {
/* 338 */         for (int j = 0; j < this.childModels.size(); j++)
/*     */         {
/* 340 */           ((ModelRenderer)this.childModels.get(j)).render(p_78791_1_);
/*     */         }
/*     */       }
/*     */       
/* 344 */       GlStateManager.popMatrix();
/*     */       
/* 346 */       if (i != 0)
/*     */       {
/* 348 */         GlStateManager.bindTexture(i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postRender(float scale) {
/* 358 */     if (!this.isHidden && this.showModel) {
/*     */       
/* 360 */       checkResetDisplayList();
/*     */       
/* 362 */       if (!this.compiled)
/*     */       {
/* 364 */         compileDisplayList(scale);
/*     */       }
/*     */       
/* 367 */       if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
/*     */         
/* 369 */         if (this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F)
/*     */         {
/* 371 */           GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 376 */         GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
/*     */         
/* 378 */         if (this.rotateAngleZ != 0.0F)
/*     */         {
/* 380 */           GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */         }
/*     */         
/* 383 */         if (this.rotateAngleY != 0.0F)
/*     */         {
/* 385 */           GlStateManager.rotate(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 388 */         if (this.rotateAngleX != 0.0F)
/*     */         {
/* 390 */           GlStateManager.rotate(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void compileDisplayList(float scale) {
/* 401 */     if (this.displayList == 0)
/*     */     {
/* 403 */       this.displayList = GLAllocation.generateDisplayLists(1);
/*     */     }
/*     */     
/* 406 */     GL11.glNewList(this.displayList, 4864);
/* 407 */     WorldRenderer worldrenderer = Tessellator.getInstance().getWorldRenderer();
/*     */     
/* 409 */     for (int i = 0; i < this.cubeList.size(); i++)
/*     */     {
/* 411 */       ((ModelBox)this.cubeList.get(i)).render(worldrenderer, scale);
/*     */     }
/*     */     
/* 414 */     for (int j = 0; j < this.spriteList.size(); j++) {
/*     */       
/* 416 */       ModelSprite modelsprite = this.spriteList.get(j);
/* 417 */       modelsprite.render(Tessellator.getInstance(), scale);
/*     */     } 
/*     */     
/* 420 */     GL11.glEndList();
/* 421 */     this.compiled = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelRenderer setTextureSize(int textureWidthIn, int textureHeightIn) {
/* 429 */     this.textureWidth = textureWidthIn;
/* 430 */     this.textureHeight = textureHeightIn;
/* 431 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSprite(float p_addSprite_1_, float p_addSprite_2_, float p_addSprite_3_, int p_addSprite_4_, int p_addSprite_5_, int p_addSprite_6_, float p_addSprite_7_) {
/* 436 */     this.spriteList.add(new ModelSprite(this, this.textureOffsetX, this.textureOffsetY, p_addSprite_1_, p_addSprite_2_, p_addSprite_3_, p_addSprite_4_, p_addSprite_5_, p_addSprite_6_, p_addSprite_7_));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getCompiled() {
/* 441 */     return this.compiled;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDisplayList() {
/* 446 */     return this.displayList;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkResetDisplayList() {
/* 451 */     if (this.countResetDisplayList != Shaders.countResetDisplayLists) {
/*     */       
/* 453 */       this.compiled = false;
/* 454 */       this.countResetDisplayList = Shaders.countResetDisplayLists;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation() {
/* 460 */     return this.textureLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextureLocation(ResourceLocation p_setTextureLocation_1_) {
/* 465 */     this.textureLocation = p_setTextureLocation_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 470 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setId(String p_setId_1_) {
/* 475 */     this.id = p_setId_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBox(int[][] p_addBox_1_, float p_addBox_2_, float p_addBox_3_, float p_addBox_4_, float p_addBox_5_, float p_addBox_6_, float p_addBox_7_, float p_addBox_8_) {
/* 480 */     this.cubeList.add(new ModelBox(this, p_addBox_1_, p_addBox_2_, p_addBox_3_, p_addBox_4_, p_addBox_5_, p_addBox_6_, p_addBox_7_, p_addBox_8_, this.mirror));
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer getChild(String p_getChild_1_) {
/* 485 */     if (p_getChild_1_ == null)
/*     */     {
/* 487 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 491 */     if (this.childModels != null)
/*     */     {
/* 493 */       for (int i = 0; i < this.childModels.size(); i++) {
/*     */         
/* 495 */         ModelRenderer modelrenderer = this.childModels.get(i);
/*     */         
/* 497 */         if (p_getChild_1_.equals(modelrenderer.getId()))
/*     */         {
/* 499 */           return modelrenderer;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 504 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelRenderer getChildDeep(String p_getChildDeep_1_) {
/* 510 */     if (p_getChildDeep_1_ == null)
/*     */     {
/* 512 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 516 */     ModelRenderer modelrenderer = getChild(p_getChildDeep_1_);
/*     */     
/* 518 */     if (modelrenderer != null)
/*     */     {
/* 520 */       return modelrenderer;
/*     */     }
/*     */ 
/*     */     
/* 524 */     if (this.childModels != null)
/*     */     {
/* 526 */       for (int i = 0; i < this.childModels.size(); i++) {
/*     */         
/* 528 */         ModelRenderer modelrenderer1 = this.childModels.get(i);
/* 529 */         ModelRenderer modelrenderer2 = modelrenderer1.getChildDeep(p_getChildDeep_1_);
/*     */         
/* 531 */         if (modelrenderer2 != null)
/*     */         {
/* 533 */           return modelrenderer2;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 538 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModelUpdater(ModelUpdater p_setModelUpdater_1_) {
/* 545 */     this.modelUpdater = p_setModelUpdater_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 550 */     StringBuffer stringbuffer = new StringBuffer();
/* 551 */     stringbuffer.append("id: " + this.id + ", boxes: " + ((this.cubeList != null) ? (String)Integer.valueOf(this.cubeList.size()) : null) + ", submodels: " + ((this.childModels != null) ? (String)Integer.valueOf(this.childModels.size()) : null));
/* 552 */     return stringbuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\model\ModelRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.optifine.player;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.image.BufferedImage;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class PlayerItemModel {
/*  17 */   private Dimension textureSize = null;
/*     */   private boolean usePlayerTexture = false;
/*  19 */   private PlayerItemRenderer[] modelRenderers = new PlayerItemRenderer[0];
/*  20 */   private ResourceLocation textureLocation = null;
/*  21 */   private BufferedImage textureImage = null;
/*  22 */   private DynamicTexture texture = null;
/*  23 */   private ResourceLocation locationMissing = new ResourceLocation("textures/blocks/wool_colored_red.png");
/*     */   
/*     */   public static final int ATTACH_BODY = 0;
/*     */   public static final int ATTACH_HEAD = 1;
/*     */   public static final int ATTACH_LEFT_ARM = 2;
/*     */   public static final int ATTACH_RIGHT_ARM = 3;
/*     */   public static final int ATTACH_LEFT_LEG = 4;
/*     */   public static final int ATTACH_RIGHT_LEG = 5;
/*     */   public static final int ATTACH_CAPE = 6;
/*     */   
/*     */   public PlayerItemModel(Dimension textureSize, boolean usePlayerTexture, PlayerItemRenderer[] modelRenderers) {
/*  34 */     this.textureSize = textureSize;
/*  35 */     this.usePlayerTexture = usePlayerTexture;
/*  36 */     this.modelRenderers = modelRenderers;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(ModelBiped modelBiped, AbstractClientPlayer player, float scale, float partialTicks) {
/*  41 */     TextureManager texturemanager = Config.getTextureManager();
/*     */     
/*  43 */     if (this.usePlayerTexture) {
/*     */       
/*  45 */       texturemanager.bindTexture(player.getLocationSkin());
/*     */     }
/*  47 */     else if (this.textureLocation != null) {
/*     */       
/*  49 */       if (this.texture == null && this.textureImage != null) {
/*     */         
/*  51 */         this.texture = new DynamicTexture(this.textureImage);
/*  52 */         Minecraft.getMinecraft().getTextureManager().loadTexture(this.textureLocation, (ITextureObject)this.texture);
/*     */       } 
/*     */       
/*  55 */       texturemanager.bindTexture(this.textureLocation);
/*     */     }
/*     */     else {
/*     */       
/*  59 */       texturemanager.bindTexture(this.locationMissing);
/*     */     } 
/*     */     
/*  62 */     for (int i = 0; i < this.modelRenderers.length; i++) {
/*     */       
/*  64 */       PlayerItemRenderer playeritemrenderer = this.modelRenderers[i];
/*  65 */       GlStateManager.pushMatrix();
/*     */       
/*  67 */       if (player.isSneaking())
/*     */       {
/*  69 */         GlStateManager.translate(0.0F, 0.2F, 0.0F);
/*     */       }
/*     */       
/*  72 */       playeritemrenderer.render(modelBiped, scale);
/*  73 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ModelRenderer getAttachModel(ModelBiped modelBiped, int attachTo) {
/*  79 */     switch (attachTo) {
/*     */       
/*     */       case 0:
/*  82 */         return modelBiped.bipedBody;
/*     */       
/*     */       case 1:
/*  85 */         return modelBiped.bipedHead;
/*     */       
/*     */       case 2:
/*  88 */         return modelBiped.bipedLeftArm;
/*     */       
/*     */       case 3:
/*  91 */         return modelBiped.bipedRightArm;
/*     */       
/*     */       case 4:
/*  94 */         return modelBiped.bipedLeftLeg;
/*     */       
/*     */       case 5:
/*  97 */         return modelBiped.bipedRightLeg;
/*     */     } 
/*     */     
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedImage getTextureImage() {
/* 106 */     return this.textureImage;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextureImage(BufferedImage textureImage) {
/* 111 */     this.textureImage = textureImage;
/*     */   }
/*     */ 
/*     */   
/*     */   public DynamicTexture getTexture() {
/* 116 */     return this.texture;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation() {
/* 121 */     return this.textureLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextureLocation(ResourceLocation textureLocation) {
/* 126 */     this.textureLocation = textureLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUsePlayerTexture() {
/* 131 */     return this.usePlayerTexture;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\player\PlayerItemModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
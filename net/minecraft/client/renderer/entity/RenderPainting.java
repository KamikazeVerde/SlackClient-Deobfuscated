/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityPainting;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class RenderPainting extends Render<EntityPainting> {
/*  16 */   private static final ResourceLocation KRISTOFFER_PAINTING_TEXTURE = new ResourceLocation("textures/painting/paintings_kristoffer_zetterstrand.png");
/*     */ 
/*     */   
/*     */   public RenderPainting(RenderManager renderManagerIn) {
/*  20 */     super(renderManagerIn);
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
/*     */   public void doRender(EntityPainting entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  33 */     GlStateManager.pushMatrix();
/*  34 */     GlStateManager.translate(x, y, z);
/*  35 */     GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
/*  36 */     GlStateManager.enableRescaleNormal();
/*  37 */     bindEntityTexture(entity);
/*  38 */     EntityPainting.EnumArt entitypainting$enumart = entity.art;
/*  39 */     float f = 0.0625F;
/*  40 */     GlStateManager.scale(f, f, f);
/*  41 */     renderPainting(entity, entitypainting$enumart.sizeX, entitypainting$enumart.sizeY, entitypainting$enumart.offsetX, entitypainting$enumart.offsetY);
/*  42 */     GlStateManager.disableRescaleNormal();
/*  43 */     GlStateManager.popMatrix();
/*  44 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityPainting entity) {
/*  52 */     return KRISTOFFER_PAINTING_TEXTURE;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderPainting(EntityPainting painting, int width, int height, int textureU, int textureV) {
/*  57 */     float f = -width / 2.0F;
/*  58 */     float f1 = -height / 2.0F;
/*  59 */     float f2 = 0.5F;
/*  60 */     float f3 = 0.75F;
/*  61 */     float f4 = 0.8125F;
/*  62 */     float f5 = 0.0F;
/*  63 */     float f6 = 0.0625F;
/*  64 */     float f7 = 0.75F;
/*  65 */     float f8 = 0.8125F;
/*  66 */     float f9 = 0.001953125F;
/*  67 */     float f10 = 0.001953125F;
/*  68 */     float f11 = 0.7519531F;
/*  69 */     float f12 = 0.7519531F;
/*  70 */     float f13 = 0.0F;
/*  71 */     float f14 = 0.0625F;
/*     */     
/*  73 */     for (int i = 0; i < width / 16; i++) {
/*     */       
/*  75 */       for (int j = 0; j < height / 16; j++) {
/*     */         
/*  77 */         float f15 = f + ((i + 1) * 16);
/*  78 */         float f16 = f + (i * 16);
/*  79 */         float f17 = f1 + ((j + 1) * 16);
/*  80 */         float f18 = f1 + (j * 16);
/*  81 */         setLightmap(painting, (f15 + f16) / 2.0F, (f17 + f18) / 2.0F);
/*  82 */         float f19 = (textureU + width - i * 16) / 256.0F;
/*  83 */         float f20 = (textureU + width - (i + 1) * 16) / 256.0F;
/*  84 */         float f21 = (textureV + height - j * 16) / 256.0F;
/*  85 */         float f22 = (textureV + height - (j + 1) * 16) / 256.0F;
/*  86 */         Tessellator tessellator = Tessellator.getInstance();
/*  87 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  88 */         worldrenderer.begin(7, DefaultVertexFormats.field_181710_j);
/*  89 */         worldrenderer.pos(f15, f18, -f2).tex(f20, f21).func_181663_c(0.0F, 0.0F, -1.0F).endVertex();
/*  90 */         worldrenderer.pos(f16, f18, -f2).tex(f19, f21).func_181663_c(0.0F, 0.0F, -1.0F).endVertex();
/*  91 */         worldrenderer.pos(f16, f17, -f2).tex(f19, f22).func_181663_c(0.0F, 0.0F, -1.0F).endVertex();
/*  92 */         worldrenderer.pos(f15, f17, -f2).tex(f20, f22).func_181663_c(0.0F, 0.0F, -1.0F).endVertex();
/*  93 */         worldrenderer.pos(f15, f17, f2).tex(f3, f5).func_181663_c(0.0F, 0.0F, 1.0F).endVertex();
/*  94 */         worldrenderer.pos(f16, f17, f2).tex(f4, f5).func_181663_c(0.0F, 0.0F, 1.0F).endVertex();
/*  95 */         worldrenderer.pos(f16, f18, f2).tex(f4, f6).func_181663_c(0.0F, 0.0F, 1.0F).endVertex();
/*  96 */         worldrenderer.pos(f15, f18, f2).tex(f3, f6).func_181663_c(0.0F, 0.0F, 1.0F).endVertex();
/*  97 */         worldrenderer.pos(f15, f17, -f2).tex(f7, f9).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/*  98 */         worldrenderer.pos(f16, f17, -f2).tex(f8, f9).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/*  99 */         worldrenderer.pos(f16, f17, f2).tex(f8, f10).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/* 100 */         worldrenderer.pos(f15, f17, f2).tex(f7, f10).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/* 101 */         worldrenderer.pos(f15, f18, f2).tex(f7, f9).func_181663_c(0.0F, -1.0F, 0.0F).endVertex();
/* 102 */         worldrenderer.pos(f16, f18, f2).tex(f8, f9).func_181663_c(0.0F, -1.0F, 0.0F).endVertex();
/* 103 */         worldrenderer.pos(f16, f18, -f2).tex(f8, f10).func_181663_c(0.0F, -1.0F, 0.0F).endVertex();
/* 104 */         worldrenderer.pos(f15, f18, -f2).tex(f7, f10).func_181663_c(0.0F, -1.0F, 0.0F).endVertex();
/* 105 */         worldrenderer.pos(f15, f17, f2).tex(f12, f13).func_181663_c(-1.0F, 0.0F, 0.0F).endVertex();
/* 106 */         worldrenderer.pos(f15, f18, f2).tex(f12, f14).func_181663_c(-1.0F, 0.0F, 0.0F).endVertex();
/* 107 */         worldrenderer.pos(f15, f18, -f2).tex(f11, f14).func_181663_c(-1.0F, 0.0F, 0.0F).endVertex();
/* 108 */         worldrenderer.pos(f15, f17, -f2).tex(f11, f13).func_181663_c(-1.0F, 0.0F, 0.0F).endVertex();
/* 109 */         worldrenderer.pos(f16, f17, -f2).tex(f12, f13).func_181663_c(1.0F, 0.0F, 0.0F).endVertex();
/* 110 */         worldrenderer.pos(f16, f18, -f2).tex(f12, f14).func_181663_c(1.0F, 0.0F, 0.0F).endVertex();
/* 111 */         worldrenderer.pos(f16, f18, f2).tex(f11, f14).func_181663_c(1.0F, 0.0F, 0.0F).endVertex();
/* 112 */         worldrenderer.pos(f16, f17, f2).tex(f11, f13).func_181663_c(1.0F, 0.0F, 0.0F).endVertex();
/* 113 */         tessellator.draw();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setLightmap(EntityPainting painting, float p_77008_2_, float p_77008_3_) {
/* 120 */     int i = MathHelper.floor_double(painting.posX);
/* 121 */     int j = MathHelper.floor_double(painting.posY + (p_77008_3_ / 16.0F));
/* 122 */     int k = MathHelper.floor_double(painting.posZ);
/* 123 */     EnumFacing enumfacing = painting.facingDirection;
/*     */     
/* 125 */     if (enumfacing == EnumFacing.NORTH)
/*     */     {
/* 127 */       i = MathHelper.floor_double(painting.posX + (p_77008_2_ / 16.0F));
/*     */     }
/*     */     
/* 130 */     if (enumfacing == EnumFacing.WEST)
/*     */     {
/* 132 */       k = MathHelper.floor_double(painting.posZ - (p_77008_2_ / 16.0F));
/*     */     }
/*     */     
/* 135 */     if (enumfacing == EnumFacing.SOUTH)
/*     */     {
/* 137 */       i = MathHelper.floor_double(painting.posX - (p_77008_2_ / 16.0F));
/*     */     }
/*     */     
/* 140 */     if (enumfacing == EnumFacing.EAST)
/*     */     {
/* 142 */       k = MathHelper.floor_double(painting.posZ + (p_77008_2_ / 16.0F));
/*     */     }
/*     */     
/* 145 */     int l = this.renderManager.worldObj.getCombinedLight(new BlockPos(i, j, k), 0);
/* 146 */     int i1 = l % 65536;
/* 147 */     int j1 = l / 65536;
/* 148 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i1, j1);
/* 149 */     GlStateManager.color(1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\RenderPainting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
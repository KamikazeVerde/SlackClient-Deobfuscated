/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityEndPortal;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.shaders.ShadersRender;
/*     */ 
/*     */ public class TileEntityEndPortalRenderer extends TileEntitySpecialRenderer<TileEntityEndPortal> {
/*  19 */   private static final ResourceLocation END_SKY_TEXTURE = new ResourceLocation("textures/environment/end_sky.png");
/*  20 */   private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
/*  21 */   private static final Random field_147527_e = new Random(31100L);
/*  22 */   FloatBuffer field_147528_b = GLAllocation.createDirectFloatBuffer(16);
/*     */ 
/*     */   
/*     */   public void renderTileEntityAt(TileEntityEndPortal te, double x, double y, double z, float partialTicks, int destroyStage) {
/*  26 */     if (!Config.isShaders() || !ShadersRender.renderEndPortal(te, x, y, z, partialTicks, destroyStage, 0.75F)) {
/*     */       
/*  28 */       float f = (float)this.rendererDispatcher.entityX;
/*  29 */       float f1 = (float)this.rendererDispatcher.entityY;
/*  30 */       float f2 = (float)this.rendererDispatcher.entityZ;
/*  31 */       GlStateManager.disableLighting();
/*  32 */       field_147527_e.setSeed(31100L);
/*  33 */       float f3 = 0.75F;
/*     */       
/*  35 */       for (int i = 0; i < 16; i++) {
/*     */         
/*  37 */         GlStateManager.pushMatrix();
/*  38 */         float f4 = (16 - i);
/*  39 */         float f5 = 0.0625F;
/*  40 */         float f6 = 1.0F / (f4 + 1.0F);
/*     */         
/*  42 */         if (i == 0) {
/*     */           
/*  44 */           bindTexture(END_SKY_TEXTURE);
/*  45 */           f6 = 0.1F;
/*  46 */           f4 = 65.0F;
/*  47 */           f5 = 0.125F;
/*  48 */           GlStateManager.enableBlend();
/*  49 */           GlStateManager.blendFunc(770, 771);
/*     */         } 
/*     */         
/*  52 */         if (i >= 1)
/*     */         {
/*  54 */           bindTexture(END_PORTAL_TEXTURE);
/*     */         }
/*     */         
/*  57 */         if (i == 1) {
/*     */           
/*  59 */           GlStateManager.enableBlend();
/*  60 */           GlStateManager.blendFunc(1, 1);
/*  61 */           f5 = 0.5F;
/*     */         } 
/*     */         
/*  64 */         float f7 = (float)-(y + f3);
/*  65 */         float f8 = f7 + (float)(ActiveRenderInfo.getPosition()).yCoord;
/*  66 */         float f9 = f7 + f4 + (float)(ActiveRenderInfo.getPosition()).yCoord;
/*  67 */         float f10 = f8 / f9;
/*  68 */         f10 = (float)(y + f3) + f10;
/*  69 */         GlStateManager.translate(f, f10, f2);
/*  70 */         GlStateManager.texGen(GlStateManager.TexGen.S, 9217);
/*  71 */         GlStateManager.texGen(GlStateManager.TexGen.T, 9217);
/*  72 */         GlStateManager.texGen(GlStateManager.TexGen.R, 9217);
/*  73 */         GlStateManager.texGen(GlStateManager.TexGen.Q, 9216);
/*  74 */         GlStateManager.func_179105_a(GlStateManager.TexGen.S, 9473, func_147525_a(1.0F, 0.0F, 0.0F, 0.0F));
/*  75 */         GlStateManager.func_179105_a(GlStateManager.TexGen.T, 9473, func_147525_a(0.0F, 0.0F, 1.0F, 0.0F));
/*  76 */         GlStateManager.func_179105_a(GlStateManager.TexGen.R, 9473, func_147525_a(0.0F, 0.0F, 0.0F, 1.0F));
/*  77 */         GlStateManager.func_179105_a(GlStateManager.TexGen.Q, 9474, func_147525_a(0.0F, 1.0F, 0.0F, 0.0F));
/*  78 */         GlStateManager.enableTexGenCoord(GlStateManager.TexGen.S);
/*  79 */         GlStateManager.enableTexGenCoord(GlStateManager.TexGen.T);
/*  80 */         GlStateManager.enableTexGenCoord(GlStateManager.TexGen.R);
/*  81 */         GlStateManager.enableTexGenCoord(GlStateManager.TexGen.Q);
/*  82 */         GlStateManager.popMatrix();
/*  83 */         GlStateManager.matrixMode(5890);
/*  84 */         GlStateManager.pushMatrix();
/*  85 */         GlStateManager.loadIdentity();
/*  86 */         GlStateManager.translate(0.0F, (float)(Minecraft.getSystemTime() % 700000L) / 700000.0F, 0.0F);
/*  87 */         GlStateManager.scale(f5, f5, f5);
/*  88 */         GlStateManager.translate(0.5F, 0.5F, 0.0F);
/*  89 */         GlStateManager.rotate((i * i * 4321 + i * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
/*  90 */         GlStateManager.translate(-0.5F, -0.5F, 0.0F);
/*  91 */         GlStateManager.translate(-f, -f2, -f1);
/*  92 */         f8 = f7 + (float)(ActiveRenderInfo.getPosition()).yCoord;
/*  93 */         GlStateManager.translate((float)(ActiveRenderInfo.getPosition()).xCoord * f4 / f8, (float)(ActiveRenderInfo.getPosition()).zCoord * f4 / f8, -f1);
/*  94 */         Tessellator tessellator = Tessellator.getInstance();
/*  95 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  96 */         worldrenderer.begin(7, DefaultVertexFormats.field_181706_f);
/*  97 */         float f11 = (field_147527_e.nextFloat() * 0.5F + 0.1F) * f6;
/*  98 */         float f12 = (field_147527_e.nextFloat() * 0.5F + 0.4F) * f6;
/*  99 */         float f13 = (field_147527_e.nextFloat() * 0.5F + 0.5F) * f6;
/*     */         
/* 101 */         if (i == 0)
/*     */         {
/* 103 */           f11 = f12 = f13 = 1.0F * f6;
/*     */         }
/*     */         
/* 106 */         worldrenderer.pos(x, y + f3, z).func_181666_a(f11, f12, f13, 1.0F).endVertex();
/* 107 */         worldrenderer.pos(x, y + f3, z + 1.0D).func_181666_a(f11, f12, f13, 1.0F).endVertex();
/* 108 */         worldrenderer.pos(x + 1.0D, y + f3, z + 1.0D).func_181666_a(f11, f12, f13, 1.0F).endVertex();
/* 109 */         worldrenderer.pos(x + 1.0D, y + f3, z).func_181666_a(f11, f12, f13, 1.0F).endVertex();
/* 110 */         tessellator.draw();
/* 111 */         GlStateManager.popMatrix();
/* 112 */         GlStateManager.matrixMode(5888);
/* 113 */         bindTexture(END_SKY_TEXTURE);
/*     */       } 
/*     */       
/* 116 */       GlStateManager.disableBlend();
/* 117 */       GlStateManager.disableTexGenCoord(GlStateManager.TexGen.S);
/* 118 */       GlStateManager.disableTexGenCoord(GlStateManager.TexGen.T);
/* 119 */       GlStateManager.disableTexGenCoord(GlStateManager.TexGen.R);
/* 120 */       GlStateManager.disableTexGenCoord(GlStateManager.TexGen.Q);
/* 121 */       GlStateManager.enableLighting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private FloatBuffer func_147525_a(float p_147525_1_, float p_147525_2_, float p_147525_3_, float p_147525_4_) {
/* 127 */     this.field_147528_b.clear();
/* 128 */     this.field_147528_b.put(p_147525_1_).put(p_147525_2_).put(p_147525_3_).put(p_147525_4_);
/* 129 */     this.field_147528_b.flip();
/* 130 */     return this.field_147528_b;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\tileentity\TileEntityEndPortalRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
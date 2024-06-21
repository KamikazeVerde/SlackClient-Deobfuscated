/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class TileEntityBeaconRenderer extends TileEntitySpecialRenderer<TileEntityBeacon> {
/*  17 */   private static final ResourceLocation beaconBeam = new ResourceLocation("textures/entity/beacon_beam.png");
/*     */ 
/*     */   
/*     */   public void renderTileEntityAt(TileEntityBeacon te, double x, double y, double z, float partialTicks, int destroyStage) {
/*  21 */     float f = te.shouldBeamRender();
/*     */     
/*  23 */     if (f > 0.0D) {
/*     */       
/*  25 */       if (Config.isShaders())
/*     */       {
/*  27 */         Shaders.beginBeacon();
/*     */       }
/*     */       
/*  30 */       GlStateManager.alphaFunc(516, 0.1F);
/*     */       
/*  32 */       if (f > 0.0F) {
/*     */         
/*  34 */         Tessellator tessellator = Tessellator.getInstance();
/*  35 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  36 */         GlStateManager.disableFog();
/*  37 */         List<TileEntityBeacon.BeamSegment> list = te.getBeamSegments();
/*  38 */         int i = 0;
/*     */         
/*  40 */         for (int j = 0; j < list.size(); j++) {
/*     */           
/*  42 */           TileEntityBeacon.BeamSegment tileentitybeacon$beamsegment = list.get(j);
/*  43 */           int k = i + tileentitybeacon$beamsegment.getHeight();
/*  44 */           bindTexture(beaconBeam);
/*  45 */           GL11.glTexParameterf(3553, 10242, 10497.0F);
/*  46 */           GL11.glTexParameterf(3553, 10243, 10497.0F);
/*  47 */           GlStateManager.disableLighting();
/*  48 */           GlStateManager.disableCull();
/*  49 */           GlStateManager.disableBlend();
/*  50 */           GlStateManager.depthMask(true);
/*  51 */           GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
/*  52 */           double d0 = te.getWorld().getTotalWorldTime() + partialTicks;
/*  53 */           double d1 = MathHelper.func_181162_h(-d0 * 0.2D - MathHelper.floor_double(-d0 * 0.1D));
/*  54 */           float f1 = tileentitybeacon$beamsegment.getColors()[0];
/*  55 */           float f2 = tileentitybeacon$beamsegment.getColors()[1];
/*  56 */           float f3 = tileentitybeacon$beamsegment.getColors()[2];
/*  57 */           double d2 = d0 * 0.025D * -1.5D;
/*  58 */           double d3 = 0.2D;
/*  59 */           double d4 = 0.5D + Math.cos(d2 + 2.356194490192345D) * 0.2D;
/*  60 */           double d5 = 0.5D + Math.sin(d2 + 2.356194490192345D) * 0.2D;
/*  61 */           double d6 = 0.5D + Math.cos(d2 + 0.7853981633974483D) * 0.2D;
/*  62 */           double d7 = 0.5D + Math.sin(d2 + 0.7853981633974483D) * 0.2D;
/*  63 */           double d8 = 0.5D + Math.cos(d2 + 3.9269908169872414D) * 0.2D;
/*  64 */           double d9 = 0.5D + Math.sin(d2 + 3.9269908169872414D) * 0.2D;
/*  65 */           double d10 = 0.5D + Math.cos(d2 + 5.497787143782138D) * 0.2D;
/*  66 */           double d11 = 0.5D + Math.sin(d2 + 5.497787143782138D) * 0.2D;
/*  67 */           double d12 = 0.0D;
/*  68 */           double d13 = 1.0D;
/*  69 */           double d14 = -1.0D + d1;
/*  70 */           double d15 = (tileentitybeacon$beamsegment.getHeight() * f) * 2.5D + d14;
/*  71 */           worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/*  72 */           worldrenderer.pos(x + d4, y + k, z + d5).tex(1.0D, d15).func_181666_a(f1, f2, f3, 1.0F).endVertex();
/*  73 */           worldrenderer.pos(x + d4, y + i, z + d5).tex(1.0D, d14).func_181666_a(f1, f2, f3, 1.0F).endVertex();
/*  74 */           worldrenderer.pos(x + d6, y + i, z + d7).tex(0.0D, d14).func_181666_a(f1, f2, f3, 1.0F).endVertex();
/*  75 */           worldrenderer.pos(x + d6, y + k, z + d7).tex(0.0D, d15).func_181666_a(f1, f2, f3, 1.0F).endVertex();
/*  76 */           worldrenderer.pos(x + d10, y + k, z + d11).tex(1.0D, d15).func_181666_a(f1, f2, f3, 1.0F).endVertex();
/*  77 */           worldrenderer.pos(x + d10, y + i, z + d11).tex(1.0D, d14).func_181666_a(f1, f2, f3, 1.0F).endVertex();
/*  78 */           worldrenderer.pos(x + d8, y + i, z + d9).tex(0.0D, d14).func_181666_a(f1, f2, f3, 1.0F).endVertex();
/*  79 */           worldrenderer.pos(x + d8, y + k, z + d9).tex(0.0D, d15).func_181666_a(f1, f2, f3, 1.0F).endVertex();
/*  80 */           worldrenderer.pos(x + d6, y + k, z + d7).tex(1.0D, d15).func_181666_a(f1, f2, f3, 1.0F).endVertex();
/*  81 */           worldrenderer.pos(x + d6, y + i, z + d7).tex(1.0D, d14).func_181666_a(f1, f2, f3, 1.0F).endVertex();
/*  82 */           worldrenderer.pos(x + d10, y + i, z + d11).tex(0.0D, d14).func_181666_a(f1, f2, f3, 1.0F).endVertex();
/*  83 */           worldrenderer.pos(x + d10, y + k, z + d11).tex(0.0D, d15).func_181666_a(f1, f2, f3, 1.0F).endVertex();
/*  84 */           worldrenderer.pos(x + d8, y + k, z + d9).tex(1.0D, d15).func_181666_a(f1, f2, f3, 1.0F).endVertex();
/*  85 */           worldrenderer.pos(x + d8, y + i, z + d9).tex(1.0D, d14).func_181666_a(f1, f2, f3, 1.0F).endVertex();
/*  86 */           worldrenderer.pos(x + d4, y + i, z + d5).tex(0.0D, d14).func_181666_a(f1, f2, f3, 1.0F).endVertex();
/*  87 */           worldrenderer.pos(x + d4, y + k, z + d5).tex(0.0D, d15).func_181666_a(f1, f2, f3, 1.0F).endVertex();
/*  88 */           tessellator.draw();
/*  89 */           GlStateManager.enableBlend();
/*  90 */           GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  91 */           GlStateManager.depthMask(false);
/*  92 */           d2 = 0.2D;
/*  93 */           d3 = 0.2D;
/*  94 */           d4 = 0.8D;
/*  95 */           d5 = 0.2D;
/*  96 */           d6 = 0.2D;
/*  97 */           d7 = 0.8D;
/*  98 */           d8 = 0.8D;
/*  99 */           d9 = 0.8D;
/* 100 */           d10 = 0.0D;
/* 101 */           d11 = 1.0D;
/* 102 */           d12 = -1.0D + d1;
/* 103 */           d13 = (tileentitybeacon$beamsegment.getHeight() * f) + d12;
/* 104 */           worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/* 105 */           worldrenderer.pos(x + 0.2D, y + k, z + 0.2D).tex(1.0D, d13).func_181666_a(f1, f2, f3, 0.125F).endVertex();
/* 106 */           worldrenderer.pos(x + 0.2D, y + i, z + 0.2D).tex(1.0D, d12).func_181666_a(f1, f2, f3, 0.125F).endVertex();
/* 107 */           worldrenderer.pos(x + 0.8D, y + i, z + 0.2D).tex(0.0D, d12).func_181666_a(f1, f2, f3, 0.125F).endVertex();
/* 108 */           worldrenderer.pos(x + 0.8D, y + k, z + 0.2D).tex(0.0D, d13).func_181666_a(f1, f2, f3, 0.125F).endVertex();
/* 109 */           worldrenderer.pos(x + 0.8D, y + k, z + 0.8D).tex(1.0D, d13).func_181666_a(f1, f2, f3, 0.125F).endVertex();
/* 110 */           worldrenderer.pos(x + 0.8D, y + i, z + 0.8D).tex(1.0D, d12).func_181666_a(f1, f2, f3, 0.125F).endVertex();
/* 111 */           worldrenderer.pos(x + 0.2D, y + i, z + 0.8D).tex(0.0D, d12).func_181666_a(f1, f2, f3, 0.125F).endVertex();
/* 112 */           worldrenderer.pos(x + 0.2D, y + k, z + 0.8D).tex(0.0D, d13).func_181666_a(f1, f2, f3, 0.125F).endVertex();
/* 113 */           worldrenderer.pos(x + 0.8D, y + k, z + 0.2D).tex(1.0D, d13).func_181666_a(f1, f2, f3, 0.125F).endVertex();
/* 114 */           worldrenderer.pos(x + 0.8D, y + i, z + 0.2D).tex(1.0D, d12).func_181666_a(f1, f2, f3, 0.125F).endVertex();
/* 115 */           worldrenderer.pos(x + 0.8D, y + i, z + 0.8D).tex(0.0D, d12).func_181666_a(f1, f2, f3, 0.125F).endVertex();
/* 116 */           worldrenderer.pos(x + 0.8D, y + k, z + 0.8D).tex(0.0D, d13).func_181666_a(f1, f2, f3, 0.125F).endVertex();
/* 117 */           worldrenderer.pos(x + 0.2D, y + k, z + 0.8D).tex(1.0D, d13).func_181666_a(f1, f2, f3, 0.125F).endVertex();
/* 118 */           worldrenderer.pos(x + 0.2D, y + i, z + 0.8D).tex(1.0D, d12).func_181666_a(f1, f2, f3, 0.125F).endVertex();
/* 119 */           worldrenderer.pos(x + 0.2D, y + i, z + 0.2D).tex(0.0D, d12).func_181666_a(f1, f2, f3, 0.125F).endVertex();
/* 120 */           worldrenderer.pos(x + 0.2D, y + k, z + 0.2D).tex(0.0D, d13).func_181666_a(f1, f2, f3, 0.125F).endVertex();
/* 121 */           tessellator.draw();
/* 122 */           GlStateManager.enableLighting();
/* 123 */           GlStateManager.enableTexture2D();
/* 124 */           GlStateManager.depthMask(true);
/* 125 */           i = k;
/*     */         } 
/*     */         
/* 128 */         GlStateManager.enableFog();
/*     */       } 
/*     */       
/* 131 */       if (Config.isShaders())
/*     */       {
/* 133 */         Shaders.endBeacon();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_181055_a() {
/* 140 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\tileentity\TileEntityBeaconRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
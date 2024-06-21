/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class Gui {
/*  11 */   public static final ResourceLocation optionsBackground = new ResourceLocation("textures/gui/options_background.png");
/*  12 */   public static final ResourceLocation statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
/*  13 */   public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
/*     */ 
/*     */   
/*     */   protected float zLevel;
/*     */ 
/*     */   
/*     */   protected void drawHorizontalLine(int startX, int endX, int y, int color) {
/*  20 */     if (endX < startX) {
/*  21 */       int i = startX;
/*  22 */       startX = endX;
/*  23 */       endX = i;
/*     */     } 
/*     */     
/*  26 */     drawRect(startX, y, endX + 1, y + 1, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawVerticalLine(int x, int startY, int endY, int color) {
/*  33 */     if (endY < startY) {
/*  34 */       int i = startY;
/*  35 */       startY = endY;
/*  36 */       endY = i;
/*     */     } 
/*     */     
/*  39 */     drawRect(x, startY + 1, x + 1, endY, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawRect(int left, int top, int right, int bottom, int color) {
/*  46 */     if (left < right) {
/*  47 */       int i = left;
/*  48 */       left = right;
/*  49 */       right = i;
/*     */     } 
/*     */     
/*  52 */     if (top < bottom) {
/*  53 */       int j = top;
/*  54 */       top = bottom;
/*  55 */       bottom = j;
/*     */     } 
/*     */     
/*  58 */     float f3 = (color >> 24 & 0xFF) / 255.0F;
/*  59 */     float f = (color >> 16 & 0xFF) / 255.0F;
/*  60 */     float f1 = (color >> 8 & 0xFF) / 255.0F;
/*  61 */     float f2 = (color & 0xFF) / 255.0F;
/*  62 */     Tessellator tessellator = Tessellator.getInstance();
/*  63 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  64 */     GlStateManager.enableBlend();
/*  65 */     GlStateManager.disableTexture2D();
/*  66 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  67 */     GlStateManager.color(f, f1, f2, f3);
/*  68 */     worldrenderer.begin(7, DefaultVertexFormats.field_181705_e);
/*  69 */     worldrenderer.pos(left, bottom, 0.0D).endVertex();
/*  70 */     worldrenderer.pos(right, bottom, 0.0D).endVertex();
/*  71 */     worldrenderer.pos(right, top, 0.0D).endVertex();
/*  72 */     worldrenderer.pos(left, top, 0.0D).endVertex();
/*  73 */     tessellator.draw();
/*  74 */     GlStateManager.enableTexture2D();
/*  75 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   public static void drawRectCentered(int x, int y, int width, int height, int color) {
/*  79 */     drawRect(x, y, x - width, y - height, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
/*  87 */     float f = (startColor >> 24 & 0xFF) / 255.0F;
/*  88 */     float f1 = (startColor >> 16 & 0xFF) / 255.0F;
/*  89 */     float f2 = (startColor >> 8 & 0xFF) / 255.0F;
/*  90 */     float f3 = (startColor & 0xFF) / 255.0F;
/*  91 */     float f4 = (endColor >> 24 & 0xFF) / 255.0F;
/*  92 */     float f5 = (endColor >> 16 & 0xFF) / 255.0F;
/*  93 */     float f6 = (endColor >> 8 & 0xFF) / 255.0F;
/*  94 */     float f7 = (endColor & 0xFF) / 255.0F;
/*  95 */     GlStateManager.disableTexture2D();
/*  96 */     GlStateManager.enableBlend();
/*  97 */     GlStateManager.disableAlpha();
/*  98 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  99 */     GlStateManager.shadeModel(7425);
/* 100 */     Tessellator tessellator = Tessellator.getInstance();
/* 101 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 102 */     worldrenderer.begin(7, DefaultVertexFormats.field_181706_f);
/* 103 */     worldrenderer.pos(right, top, this.zLevel).func_181666_a(f1, f2, f3, f).endVertex();
/* 104 */     worldrenderer.pos(left, top, this.zLevel).func_181666_a(f1, f2, f3, f).endVertex();
/* 105 */     worldrenderer.pos(left, bottom, this.zLevel).func_181666_a(f5, f6, f7, f4).endVertex();
/* 106 */     worldrenderer.pos(right, bottom, this.zLevel).func_181666_a(f5, f6, f7, f4).endVertex();
/* 107 */     tessellator.draw();
/* 108 */     GlStateManager.shadeModel(7424);
/* 109 */     GlStateManager.disableBlend();
/* 110 */     GlStateManager.enableAlpha();
/* 111 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
/* 118 */     fontRendererIn.drawStringWithShadow(text, (x - fontRendererIn.getStringWidth(text) / 2), y, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
/* 125 */     fontRendererIn.drawStringWithShadow(text, x, y, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
/* 132 */     float f = 0.00390625F;
/* 133 */     float f1 = 0.00390625F;
/* 134 */     Tessellator tessellator = Tessellator.getInstance();
/* 135 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 136 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 137 */     worldrenderer.pos(x, (y + height), this.zLevel).tex((textureX * f), ((textureY + height) * f1)).endVertex();
/* 138 */     worldrenderer.pos((x + width), (y + height), this.zLevel).tex(((textureX + width) * f), ((textureY + height) * f1)).endVertex();
/* 139 */     worldrenderer.pos((x + width), y, this.zLevel).tex(((textureX + width) * f), (textureY * f1)).endVertex();
/* 140 */     worldrenderer.pos(x, y, this.zLevel).tex((textureX * f), (textureY * f1)).endVertex();
/* 141 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV) {
/* 148 */     float f = 0.00390625F;
/* 149 */     float f1 = 0.00390625F;
/* 150 */     Tessellator tessellator = Tessellator.getInstance();
/* 151 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 152 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 153 */     worldrenderer.pos((xCoord + 0.0F), (yCoord + maxV), this.zLevel).tex(((minU + 0) * f), ((minV + maxV) * f1)).endVertex();
/* 154 */     worldrenderer.pos((xCoord + maxU), (yCoord + maxV), this.zLevel).tex(((minU + maxU) * f), ((minV + maxV) * f1)).endVertex();
/* 155 */     worldrenderer.pos((xCoord + maxU), (yCoord + 0.0F), this.zLevel).tex(((minU + maxU) * f), ((minV + 0) * f1)).endVertex();
/* 156 */     worldrenderer.pos((xCoord + 0.0F), (yCoord + 0.0F), this.zLevel).tex(((minU + 0) * f), ((minV + 0) * f1)).endVertex();
/* 157 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn) {
/* 164 */     Tessellator tessellator = Tessellator.getInstance();
/* 165 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 166 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 167 */     worldrenderer.pos(xCoord, (yCoord + heightIn), this.zLevel).tex(textureSprite.getMinU(), textureSprite.getMaxV()).endVertex();
/* 168 */     worldrenderer.pos((xCoord + widthIn), (yCoord + heightIn), this.zLevel).tex(textureSprite.getMaxU(), textureSprite.getMaxV()).endVertex();
/* 169 */     worldrenderer.pos((xCoord + widthIn), yCoord, this.zLevel).tex(textureSprite.getMaxU(), textureSprite.getMinV()).endVertex();
/* 170 */     worldrenderer.pos(xCoord, yCoord, this.zLevel).tex(textureSprite.getMinU(), textureSprite.getMinV()).endVertex();
/* 171 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
/* 178 */     float f = 1.0F / textureWidth;
/* 179 */     float f1 = 1.0F / textureHeight;
/* 180 */     Tessellator tessellator = Tessellator.getInstance();
/* 181 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 182 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 183 */     worldrenderer.pos(x, (y + height), 0.0D).tex((u * f), ((v + height) * f1)).endVertex();
/* 184 */     worldrenderer.pos((x + width), (y + height), 0.0D).tex(((u + width) * f), ((v + height) * f1)).endVertex();
/* 185 */     worldrenderer.pos((x + width), y, 0.0D).tex(((u + width) * f), (v * f1)).endVertex();
/* 186 */     worldrenderer.pos(x, y, 0.0D).tex((u * f), (v * f1)).endVertex();
/* 187 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
/* 194 */     float f = 1.0F / tileWidth;
/* 195 */     float f1 = 1.0F / tileHeight;
/* 196 */     Tessellator tessellator = Tessellator.getInstance();
/* 197 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 198 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 199 */     worldrenderer.pos(x, (y + height), 0.0D).tex((u * f), ((v + vHeight) * f1)).endVertex();
/* 200 */     worldrenderer.pos((x + width), (y + height), 0.0D).tex(((u + uWidth) * f), ((v + vHeight) * f1)).endVertex();
/* 201 */     worldrenderer.pos((x + width), y, 0.0D).tex(((u + uWidth) * f), (v * f1)).endVertex();
/* 202 */     worldrenderer.pos(x, y, 0.0D).tex((u * f), (v * f1)).endVertex();
/* 203 */     tessellator.draw();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\Gui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
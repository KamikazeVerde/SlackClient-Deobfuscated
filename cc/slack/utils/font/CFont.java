/*     */ package cc.slack.utils.font;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class CFont {
/*  11 */   private final float imgSize = 512.0F;
/*  12 */   protected CharData[] charData = new CharData[256];
/*     */   protected Font font;
/*     */   protected boolean antiAlias;
/*     */   protected boolean fractionalMetrics;
/*  16 */   protected int fontHeight = -1;
/*  17 */   protected int charOffset = 0;
/*     */   protected DynamicTexture tex;
/*     */   
/*     */   public CFont(Font font, boolean antiAlias, boolean fractionalMetrics) {
/*  21 */     this.font = font;
/*  22 */     this.antiAlias = antiAlias;
/*  23 */     this.fractionalMetrics = fractionalMetrics;
/*  24 */     this.tex = setupTexture(font, antiAlias, fractionalMetrics, this.charData);
/*     */   }
/*     */   
/*     */   protected DynamicTexture setupTexture(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars) {
/*  28 */     BufferedImage img = generateFontImage(font, antiAlias, fractionalMetrics, chars);
/*     */     
/*     */     try {
/*  31 */       return new DynamicTexture(img);
/*  32 */     } catch (Exception e) {
/*  33 */       e.printStackTrace();
/*     */ 
/*     */       
/*  36 */       return null;
/*     */     } 
/*     */   }
/*     */   protected BufferedImage generateFontImage(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars) {
/*  40 */     getClass(); int imgSize = 512;
/*  41 */     BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize, 2);
/*  42 */     Graphics2D g = (Graphics2D)bufferedImage.getGraphics();
/*  43 */     g.setFont(font);
/*  44 */     g.setColor(new Color(255, 255, 255, 0));
/*  45 */     g.fillRect(0, 0, imgSize, imgSize);
/*  46 */     g.setColor(Color.WHITE);
/*  47 */     g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
/*  48 */     g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, antiAlias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
/*  49 */     g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
/*  50 */     FontMetrics fontMetrics = g.getFontMetrics();
/*  51 */     int charHeight = 0;
/*  52 */     int positionX = 0;
/*  53 */     int positionY = 1;
/*     */     
/*  55 */     for (int i = 0; i < chars.length; i++) {
/*  56 */       char ch = (char)i;
/*  57 */       CharData charData = new CharData();
/*  58 */       Rectangle2D dimensions = fontMetrics.getStringBounds(String.valueOf(ch), g);
/*  59 */       charData.width = (dimensions.getBounds()).width + 8;
/*  60 */       charData.height = (dimensions.getBounds()).height;
/*     */       
/*  62 */       if (positionX + charData.width >= imgSize) {
/*  63 */         positionX = 0;
/*  64 */         positionY += charHeight;
/*  65 */         charHeight = 0;
/*     */       } 
/*     */       
/*  68 */       if (charData.height > charHeight) {
/*  69 */         charHeight = charData.height;
/*     */       }
/*     */       
/*  72 */       charData.storedX = positionX;
/*  73 */       charData.storedY = positionY;
/*     */       
/*  75 */       if (charData.height > this.fontHeight) {
/*  76 */         this.fontHeight = charData.height;
/*     */       }
/*     */       
/*  79 */       chars[i] = charData;
/*  80 */       g.drawString(String.valueOf(ch), positionX + 2, positionY + fontMetrics.getAscent());
/*  81 */       positionX += charData.width;
/*     */     } 
/*     */     
/*  84 */     return bufferedImage;
/*     */   }
/*     */   
/*     */   public void drawChar(CharData[] chars, char c, float x, float y) throws ArrayIndexOutOfBoundsException {
/*     */     try {
/*  89 */       drawQuad(x, y, (chars[c]).width, (chars[c]).height, (chars[c]).storedX, (chars[c]).storedY, (chars[c]).width, (chars[c]).height);
/*  90 */     } catch (Exception e) {
/*  91 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void drawQuad(float x, float y, float width, float height, float srcX, float srcY, float srcWidth, float srcHeight) {
/*  96 */     float renderSRCX = srcX / 512.0F;
/*  97 */     float renderSRCY = srcY / 512.0F;
/*  98 */     float renderSRCWidth = srcWidth / 512.0F;
/*  99 */     float renderSRCHeight = srcHeight / 512.0F;
/* 100 */     GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
/* 101 */     GL11.glVertex2d((x + width), y);
/* 102 */     GL11.glTexCoord2f(renderSRCX, renderSRCY);
/* 103 */     GL11.glVertex2d(x, y);
/* 104 */     GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
/* 105 */     GL11.glVertex2d(x, (y + height));
/* 106 */     GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
/* 107 */     GL11.glVertex2d(x, (y + height));
/* 108 */     GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
/* 109 */     GL11.glVertex2d((x + width), (y + height));
/* 110 */     GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
/* 111 */     GL11.glVertex2d((x + width), y);
/*     */   }
/*     */   
/*     */   public int getStringHeight(String text) {
/* 115 */     return getHeight();
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 119 */     return (this.fontHeight - 8) / 2;
/*     */   }
/*     */   
/*     */   public int getStringWidth(String text) {
/* 123 */     int width = 0;
/*     */     
/* 125 */     for (char c : text.toCharArray()) {
/* 126 */       if (c < this.charData.length) {
/* 127 */         width += (this.charData[c]).width - 8 + this.charOffset;
/*     */       }
/*     */     } 
/*     */     
/* 131 */     return width / 2;
/*     */   }
/*     */   
/*     */   public boolean isAntiAlias() {
/* 135 */     return this.antiAlias;
/*     */   }
/*     */   
/*     */   public void setAntiAlias(boolean antiAlias) {
/* 139 */     if (this.antiAlias != antiAlias) {
/* 140 */       this.antiAlias = antiAlias;
/* 141 */       this.tex = setupTexture(this.font, antiAlias, this.fractionalMetrics, this.charData);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isFractionalMetrics() {
/* 146 */     return this.fractionalMetrics;
/*     */   }
/*     */   
/*     */   public void setFractionalMetrics(boolean fractionalMetrics) {
/* 150 */     if (this.fractionalMetrics != fractionalMetrics) {
/* 151 */       this.fractionalMetrics = fractionalMetrics;
/* 152 */       this.tex = setupTexture(this.font, this.antiAlias, fractionalMetrics, this.charData);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Font getFont() {
/* 157 */     return this.font;
/*     */   }
/*     */   
/*     */   public void setFont(Font font) {
/* 161 */     this.font = font;
/* 162 */     this.tex = setupTexture(font, this.antiAlias, this.fractionalMetrics, this.charData);
/*     */   }
/*     */   
/*     */   protected static class CharData {
/*     */     public int width;
/*     */     public int height;
/*     */     public int storedX;
/*     */     public int storedY;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\font\CFont.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
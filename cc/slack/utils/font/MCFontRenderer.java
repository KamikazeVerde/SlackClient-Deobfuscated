/*     */ package cc.slack.utils.font;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class MCFontRenderer
/*     */   extends CFont
/*     */ {
/*     */   private static final char COLOR_CHAR = '§';
/*  15 */   protected CFont.CharData[] boldChars = new CFont.CharData[256];
/*  16 */   protected CFont.CharData[] italicChars = new CFont.CharData[256];
/*  17 */   protected CFont.CharData[] boldItalicChars = new CFont.CharData[256];
/*     */   
/*  19 */   private final int[] colorCode = new int[32]; protected DynamicTexture texBold;
/*     */   
/*     */   public MCFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
/*  22 */     super(font, antiAlias, fractionalMetrics);
/*  23 */     setupMinecraftColorcodes();
/*  24 */     setupBoldItalicIDs();
/*     */   }
/*     */   protected DynamicTexture texItalic; protected DynamicTexture texItalicBold;
/*     */   public float drawString(String text, float x, float y, int color) {
/*  28 */     return drawString(text, x, y, color, false);
/*     */   }
/*     */   
/*     */   public float drawString(String text, double x, double y, int color) {
/*  32 */     return drawString(text, x, y, color, false);
/*     */   }
/*     */   
/*     */   public float drawStringWithShadow(String text, float x, float y, int color) {
/*  36 */     float shadowWidth = drawString(text, x + 1.0D, y + 0.5D, color, true);
/*  37 */     return Math.max(shadowWidth, drawString(text, x, y, color, false));
/*     */   }
/*     */   
/*     */   public float drawStringWithShadow(String text, double x, double y, int color) {
/*  41 */     float shadowWidth = drawString(text, x + 1.0D, y + 0.5D, color, true);
/*  42 */     return Math.max(shadowWidth, drawString(text, x, y, color, false));
/*     */   }
/*     */   
/*     */   public float drawCenteredString(String text, float x, float y, int color) {
/*  46 */     return drawString(text, x - getStringWidth(text) / 2.0D, y, color);
/*     */   }
/*     */   
/*     */   public float drawCenteredString(String text, double x, double y, int color) {
/*  50 */     return drawString(text, x - getStringWidth(text) / 2.0D, y, color);
/*     */   }
/*     */   
/*     */   public float drawCenteredStringWithShadow(String text, float x, float y, int color) {
/*  54 */     float shadowWidth = drawString(text, x - getStringWidth(text) / 2.0D + 0.45D, y + 0.5D, color, true);
/*  55 */     return drawString(text, x - getStringWidth(text) / 2.0D, y, color);
/*     */   }
/*     */   
/*     */   public float drawCenteredStringWithShadow(String text, double x, double y, int color) {
/*  59 */     float shadowWidth = drawString(text, x - getStringWidth(text) / 2.0D + 0.45D, y + 0.5D, color, true);
/*  60 */     return drawString(text, x - getStringWidth(text) / 2.0D, y, color);
/*     */   }
/*     */   
/*     */   public float drawString(String text, double x, double y, int color, boolean shadow) {
/*  64 */     x--;
/*     */     
/*  66 */     if (text == null) {
/*  67 */       return 0.0F;
/*     */     }
/*     */     
/*  70 */     if (color == 553648127) {
/*  71 */       color = 16777215;
/*     */     }
/*     */     
/*  74 */     if ((color & 0xFC000000) == 0) {
/*  75 */       color |= 0xFF000000;
/*     */     }
/*     */     
/*  78 */     if (shadow) {
/*  79 */       color = (color & 0xFCFCFC) >> 2 | color & (new Color(20, 20, 20, 200)).getRGB();
/*     */     }
/*     */     
/*  82 */     CFont.CharData[] currentData = this.charData;
/*  83 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/*  84 */     boolean randomCase = false;
/*  85 */     boolean bold = false;
/*  86 */     boolean italic = false;
/*  87 */     boolean strikethrough = false;
/*  88 */     boolean underline = false;
/*  89 */     x *= 2.0D;
/*  90 */     y = (y - 3.0D) * 2.0D;
/*     */     
/*  92 */     GL11.glPushMatrix();
/*  93 */     GlStateManager.scale(0.5D, 0.5D, 0.5D);
/*  94 */     GlStateManager.enableBlend();
/*  95 */     GlStateManager.blendFunc(770, 771);
/*  96 */     GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
/*  97 */     int size = text.length();
/*  98 */     GlStateManager.enableTexture2D();
/*  99 */     GlStateManager.bindTexture(this.tex.getGlTextureId());
/*     */     
/* 101 */     GL11.glBindTexture(3553, this.tex.getGlTextureId());
/*     */     
/* 103 */     for (int i = 0; i < size; i++) {
/* 104 */       char character = text.charAt(i);
/*     */       
/* 106 */       if (character == '§') {
/* 107 */         int colorIndex = 21;
/*     */         
/*     */         try {
/* 110 */           colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
/* 111 */         } catch (Exception e) {
/* 112 */           e.printStackTrace();
/*     */         } 
/*     */         
/* 115 */         if (colorIndex < 16) {
/* 116 */           bold = false;
/* 117 */           italic = false;
/* 118 */           randomCase = false;
/* 119 */           underline = false;
/* 120 */           strikethrough = false;
/* 121 */           GlStateManager.bindTexture(this.tex.getGlTextureId());
/*     */ 
/*     */           
/* 124 */           currentData = this.charData;
/*     */           
/* 126 */           if (colorIndex < 0 || colorIndex > 15) {
/* 127 */             colorIndex = 15;
/*     */           }
/*     */           
/* 130 */           if (shadow) {
/* 131 */             colorIndex += 16;
/*     */           }
/*     */           
/* 134 */           int colorcode = this.colorCode[colorIndex];
/* 135 */           GlStateManager.color((colorcode >> 16 & 0xFF) / 255.0F, (colorcode >> 8 & 0xFF) / 255.0F, (colorcode & 0xFF) / 255.0F, alpha);
/* 136 */         } else if (colorIndex == 16) {
/* 137 */           randomCase = true;
/* 138 */         } else if (colorIndex == 17) {
/* 139 */           bold = true;
/*     */           
/* 141 */           if (italic) {
/* 142 */             GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
/*     */ 
/*     */             
/* 145 */             currentData = this.boldItalicChars;
/*     */           } else {
/* 147 */             GlStateManager.bindTexture(this.texBold.getGlTextureId());
/*     */ 
/*     */             
/* 150 */             currentData = this.boldChars;
/*     */           } 
/* 152 */         } else if (colorIndex == 18) {
/* 153 */           strikethrough = true;
/* 154 */         } else if (colorIndex == 19) {
/* 155 */           underline = true;
/* 156 */         } else if (colorIndex == 20) {
/* 157 */           italic = true;
/*     */           
/* 159 */           if (bold) {
/* 160 */             GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
/*     */ 
/*     */             
/* 163 */             currentData = this.boldItalicChars;
/*     */           } else {
/* 165 */             GlStateManager.bindTexture(this.texItalic.getGlTextureId());
/*     */ 
/*     */             
/* 168 */             currentData = this.italicChars;
/*     */           } 
/* 170 */         } else if (colorIndex == 21) {
/* 171 */           bold = false;
/* 172 */           italic = false;
/* 173 */           randomCase = false;
/* 174 */           underline = false;
/* 175 */           strikethrough = false;
/* 176 */           GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
/* 177 */           GlStateManager.bindTexture(this.tex.getGlTextureId());
/*     */ 
/*     */           
/* 180 */           currentData = this.charData;
/*     */         } 
/*     */         
/* 183 */         i++;
/* 184 */       } else if (character < currentData.length && character >= '\000') {
/* 185 */         GL11.glBegin(4);
/* 186 */         drawChar(currentData, character, (float)x, (float)y);
/* 187 */         GL11.glEnd();
/*     */         
/* 189 */         if (strikethrough) {
/* 190 */           drawLine(x, y + (currentData[character]).height / 2.0D, x + (currentData[character]).width - 8.0D, y + (currentData[character]).height / 2.0D, 1.0F);
/*     */         }
/*     */         
/* 193 */         if (underline) {
/* 194 */           drawLine(x, y + (currentData[character]).height - 2.0D, x + (currentData[character]).width - 8.0D, y + (currentData[character]).height - 2.0D, 1.0F);
/*     */         }
/*     */         
/* 197 */         x += ((currentData[character]).width - 8 + this.charOffset);
/*     */       } 
/*     */     } 
/*     */     
/* 201 */     GL11.glHint(3155, 4352);
/* 202 */     GL11.glPopMatrix();
/*     */     
/* 204 */     return (float)x / 3.0F;
/*     */   }
/*     */   
/*     */   public int getStringWidth(String text) {
/* 208 */     if (text == null) {
/* 209 */       return 0;
/*     */     }
/* 211 */     int width = 0;
/* 212 */     CFont.CharData[] currentData = this.charData;
/* 213 */     boolean bold = false;
/* 214 */     boolean italic = false;
/* 215 */     int size = text.length();
/*     */     
/* 217 */     for (int i = 0; i < size; i++) {
/* 218 */       char character = text.charAt(i);
/*     */       
/* 220 */       if (character == '§') {
/* 221 */         int colorIndex = "0123456789abcdefklmnor".indexOf(character);
/*     */         
/* 223 */         if (colorIndex < 16) {
/* 224 */           bold = false;
/* 225 */           italic = false;
/* 226 */         } else if (colorIndex == 17) {
/* 227 */           bold = true;
/*     */           
/* 229 */           if (italic) {
/* 230 */             currentData = this.boldItalicChars;
/*     */           } else {
/* 232 */             currentData = this.boldChars;
/*     */           } 
/* 234 */         } else if (colorIndex == 20) {
/* 235 */           italic = true;
/*     */           
/* 237 */           if (bold) {
/* 238 */             currentData = this.boldItalicChars;
/*     */           } else {
/* 240 */             currentData = this.italicChars;
/*     */           } 
/* 242 */         } else if (colorIndex == 21) {
/* 243 */           bold = false;
/* 244 */           italic = false;
/* 245 */           currentData = this.charData;
/*     */         } 
/*     */         
/* 248 */         i++;
/* 249 */       } else if (character < currentData.length && character >= '\000') {
/* 250 */         width += (currentData[character]).width - 8 + this.charOffset;
/*     */       } 
/*     */     } 
/*     */     
/* 254 */     return width / 2;
/*     */   }
/*     */   
/*     */   public int getStringWidthCust(String text) {
/* 258 */     if (text == null) {
/* 259 */       return 0;
/*     */     }
/*     */     
/* 262 */     int width = 0;
/* 263 */     CFont.CharData[] currentData = this.charData;
/* 264 */     boolean bold = false;
/* 265 */     boolean italic = false;
/* 266 */     int size = text.length();
/*     */     
/* 268 */     for (int i = 0; i < size; i++) {
/* 269 */       char character = text.charAt(i);
/*     */       
/* 271 */       if (character == '§') {
/* 272 */         int colorIndex = "0123456789abcdefklmnor".indexOf(character);
/*     */         
/* 274 */         if (colorIndex < 16) {
/* 275 */           bold = false;
/* 276 */           italic = false;
/* 277 */         } else if (colorIndex == 17) {
/* 278 */           bold = true;
/*     */           
/* 280 */           if (italic) {
/* 281 */             currentData = this.boldItalicChars;
/*     */           } else {
/* 283 */             currentData = this.boldChars;
/*     */           } 
/* 285 */         } else if (colorIndex == 20) {
/* 286 */           italic = true;
/*     */           
/* 288 */           if (bold) {
/* 289 */             currentData = this.boldItalicChars;
/*     */           } else {
/* 291 */             currentData = this.italicChars;
/*     */           } 
/* 293 */         } else if (colorIndex == 21) {
/* 294 */           bold = false;
/* 295 */           italic = false;
/* 296 */           currentData = this.charData;
/*     */         } 
/*     */         
/* 299 */         i++;
/* 300 */       } else if (character < currentData.length && character >= '\000') {
/* 301 */         width += (currentData[character]).width - 8 + this.charOffset;
/*     */       } 
/*     */     } 
/*     */     
/* 305 */     return (width - this.charOffset) / 2;
/*     */   }
/*     */   
/*     */   public void setFont(Font font) {
/* 309 */     super.setFont(font);
/* 310 */     setupBoldItalicIDs();
/*     */   }
/*     */   
/*     */   public void setAntiAlias(boolean antiAlias) {
/* 314 */     super.setAntiAlias(antiAlias);
/* 315 */     setupBoldItalicIDs();
/*     */   }
/*     */   
/*     */   public void setFractionalMetrics(boolean fractionalMetrics) {
/* 319 */     super.setFractionalMetrics(fractionalMetrics);
/* 320 */     setupBoldItalicIDs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupBoldItalicIDs() {
/* 328 */     this.texBold = setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
/* 329 */     this.texItalic = setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
/* 330 */     this.texItalicBold = setupTexture(this.font.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
/*     */   }
/*     */   
/*     */   private void drawLine(double x, double y, double x1, double y1, float width) {
/* 334 */     GL11.glDisable(3553);
/* 335 */     GL11.glLineWidth(width);
/* 336 */     GL11.glBegin(1);
/* 337 */     GL11.glVertex2d(x, y);
/* 338 */     GL11.glVertex2d(x1, y1);
/* 339 */     GL11.glEnd();
/* 340 */     GL11.glEnable(3553);
/*     */   }
/*     */   
/*     */   public List<String> wrapWords(String text, double width) {
/* 344 */     List<String> finalWords = new ArrayList();
/*     */     
/* 346 */     if (getStringWidth(text) > width) {
/* 347 */       String[] words = text.split(" ");
/* 348 */       String currentWord = "";
/* 349 */       char lastColorCode = Character.MAX_VALUE;
/*     */       
/* 351 */       for (String word : words) {
/* 352 */         for (int i = 0; i < (word.toCharArray()).length; i++) {
/* 353 */           char c = word.toCharArray()[i];
/*     */           
/* 355 */           if (c == '§' && i < (word.toCharArray()).length - 1) {
/* 356 */             lastColorCode = word.toCharArray()[i + 1];
/*     */           }
/*     */         } 
/*     */         
/* 360 */         if (getStringWidth(currentWord + word + " ") < width) {
/* 361 */           currentWord = currentWord + word + " ";
/*     */         } else {
/* 363 */           finalWords.add(currentWord);
/* 364 */           currentWord = "" + lastColorCode + word + " ";
/*     */         } 
/*     */       } 
/*     */       
/* 368 */       if (currentWord.length() > 0) if (getStringWidth(currentWord) < width) {
/* 369 */           finalWords.add("" + lastColorCode + currentWord + " ");
/* 370 */           currentWord = "";
/*     */         } else {
/* 372 */           formatString(currentWord, width).forEach(finalWords::add);
/*     */         }  
/*     */     } else {
/* 375 */       finalWords.add(text);
/*     */     } 
/*     */     
/* 378 */     return finalWords;
/*     */   }
/*     */   
/*     */   public List<String> formatString(String string, double width) {
/* 382 */     List<String> finalWords = new ArrayList();
/* 383 */     String currentWord = "";
/* 384 */     char lastColorCode = Character.MAX_VALUE;
/* 385 */     char[] chars = string.toCharArray();
/*     */     
/* 387 */     for (int i = 0; i < chars.length; i++) {
/* 388 */       char c = chars[i];
/*     */       
/* 390 */       if (c == '§' && i < chars.length - 1) {
/* 391 */         lastColorCode = chars[i + 1];
/*     */       }
/*     */       
/* 394 */       if (getStringWidth(currentWord + c) < width) {
/* 395 */         currentWord = currentWord + c;
/*     */       } else {
/* 397 */         finalWords.add(currentWord);
/* 398 */         currentWord = "" + lastColorCode + String.valueOf(c);
/*     */       } 
/*     */     } 
/*     */     
/* 402 */     if (currentWord.length() > 0) {
/* 403 */       finalWords.add(currentWord);
/*     */     }
/*     */     
/* 406 */     return finalWords;
/*     */   }
/*     */   
/*     */   private void setupMinecraftColorcodes() {
/* 410 */     for (int index = 0; index < 32; index++) {
/* 411 */       int noClue = (index >> 3 & 0x1) * 85;
/* 412 */       int red = (index >> 2 & 0x1) * 170 + noClue;
/* 413 */       int green = (index >> 1 & 0x1) * 170 + noClue;
/* 414 */       int blue = (index & 0x1) * 170 + noClue;
/*     */       
/* 416 */       if (index == 6) {
/* 417 */         red += 85;
/*     */       }
/*     */       
/* 420 */       if (index >= 16) {
/* 421 */         red /= 4;
/* 422 */         green /= 4;
/* 423 */         blue /= 4;
/*     */       } 
/*     */       
/* 426 */       this.colorCode[index] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\font\MCFontRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
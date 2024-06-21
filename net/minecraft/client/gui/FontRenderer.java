/*      */ package net.minecraft.client.gui;
/*      */ 
/*      */ import com.ibm.icu.text.ArabicShaping;
/*      */ import com.ibm.icu.text.ArabicShapingException;
/*      */ import com.ibm.icu.text.Bidi;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Properties;
/*      */ import java.util.Random;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureUtil;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.optifine.CustomColors;
/*      */ import net.optifine.render.GlBlendState;
/*      */ import net.optifine.util.FontUtils;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ 
/*      */ public class FontRenderer
/*      */   implements IResourceManagerReloadListener {
/*   34 */   private static final ResourceLocation[] unicodePageLocations = new ResourceLocation[256];
/*      */ 
/*      */   
/*   37 */   private final int[] charWidth = new int[256];
/*      */ 
/*      */   
/*   40 */   public int FONT_HEIGHT = 9;
/*   41 */   public Random fontRandom = new Random();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   46 */   private byte[] glyphWidth = new byte[65536];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   52 */   private int[] colorCode = new int[32];
/*      */ 
/*      */   
/*      */   private ResourceLocation locationFontTexture;
/*      */ 
/*      */   
/*      */   private final TextureManager renderEngine;
/*      */ 
/*      */   
/*      */   private float posX;
/*      */ 
/*      */   
/*      */   private float posY;
/*      */ 
/*      */   
/*      */   private boolean unicodeFlag;
/*      */ 
/*      */   
/*      */   private boolean bidiFlag;
/*      */ 
/*      */   
/*      */   private float red;
/*      */ 
/*      */   
/*      */   private float blue;
/*      */ 
/*      */   
/*      */   private float green;
/*      */ 
/*      */   
/*      */   private float alpha;
/*      */ 
/*      */   
/*      */   private int textColor;
/*      */ 
/*      */   
/*      */   private boolean randomStyle;
/*      */ 
/*      */   
/*      */   private boolean boldStyle;
/*      */ 
/*      */   
/*      */   private boolean italicStyle;
/*      */ 
/*      */   
/*      */   private boolean underlineStyle;
/*      */ 
/*      */   
/*      */   private boolean strikethroughStyle;
/*      */ 
/*      */   
/*      */   public GameSettings gameSettings;
/*      */ 
/*      */   
/*      */   public ResourceLocation locationFontTextureBase;
/*      */ 
/*      */   
/*  109 */   public float offsetBold = 1.0F;
/*  110 */   private float[] charWidthFloat = new float[256];
/*      */   private boolean blend = false;
/*  112 */   private GlBlendState oldBlendState = new GlBlendState();
/*      */ 
/*      */   
/*      */   public FontRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn, boolean unicode) {
/*  116 */     this.gameSettings = gameSettingsIn;
/*  117 */     this.locationFontTextureBase = location;
/*  118 */     this.locationFontTexture = location;
/*  119 */     this.renderEngine = textureManagerIn;
/*  120 */     this.unicodeFlag = unicode;
/*  121 */     this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
/*  122 */     bindTexture(this.locationFontTexture);
/*      */     
/*  124 */     for (int i = 0; i < 32; i++) {
/*      */       
/*  126 */       int j = (i >> 3 & 0x1) * 85;
/*  127 */       int k = (i >> 2 & 0x1) * 170 + j;
/*  128 */       int l = (i >> 1 & 0x1) * 170 + j;
/*  129 */       int i1 = (i >> 0 & 0x1) * 170 + j;
/*      */       
/*  131 */       if (i == 6)
/*      */       {
/*  133 */         k += 85;
/*      */       }
/*      */       
/*  136 */       if (gameSettingsIn.anaglyph) {
/*      */         
/*  138 */         int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
/*  139 */         int k1 = (k * 30 + l * 70) / 100;
/*  140 */         int l1 = (k * 30 + i1 * 70) / 100;
/*  141 */         k = j1;
/*  142 */         l = k1;
/*  143 */         i1 = l1;
/*      */       } 
/*      */       
/*  146 */       if (i >= 16) {
/*      */         
/*  148 */         k /= 4;
/*  149 */         l /= 4;
/*  150 */         i1 /= 4;
/*      */       } 
/*      */       
/*  153 */       this.colorCode[i] = (k & 0xFF) << 16 | (l & 0xFF) << 8 | i1 & 0xFF;
/*      */     } 
/*      */     
/*  156 */     readGlyphSizes();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*  161 */     this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
/*      */     
/*  163 */     for (int i = 0; i < unicodePageLocations.length; i++)
/*      */     {
/*  165 */       unicodePageLocations[i] = null;
/*      */     }
/*      */     
/*  168 */     readFontTexture();
/*  169 */     readGlyphSizes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readFontTexture() {
/*      */     BufferedImage bufferedimage;
/*      */     try {
/*  178 */       bufferedimage = TextureUtil.readBufferedImage(getResourceInputStream(this.locationFontTexture));
/*      */     }
/*  180 */     catch (IOException ioexception1) {
/*      */       
/*  182 */       throw new RuntimeException(ioexception1);
/*      */     } 
/*      */     
/*  185 */     Properties properties = FontUtils.readFontProperties(this.locationFontTexture);
/*  186 */     this.blend = FontUtils.readBoolean(properties, "blend", false);
/*  187 */     int i = bufferedimage.getWidth();
/*  188 */     int j = bufferedimage.getHeight();
/*  189 */     int k = i / 16;
/*  190 */     int l = j / 16;
/*  191 */     float f = i / 128.0F;
/*  192 */     float f1 = Config.limit(f, 1.0F, 2.0F);
/*  193 */     this.offsetBold = 1.0F / f1;
/*  194 */     float f2 = FontUtils.readFloat(properties, "offsetBold", -1.0F);
/*      */     
/*  196 */     if (f2 >= 0.0F)
/*      */     {
/*  198 */       this.offsetBold = f2;
/*      */     }
/*      */     
/*  201 */     int[] aint = new int[i * j];
/*  202 */     bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
/*      */     
/*  204 */     for (int i1 = 0; i1 < 256; i1++) {
/*      */       
/*  206 */       int j1 = i1 % 16;
/*  207 */       int k1 = i1 / 16;
/*  208 */       int l1 = 0;
/*      */       
/*  210 */       for (l1 = k - 1; l1 >= 0; l1--) {
/*      */         
/*  212 */         int i2 = j1 * k + l1;
/*  213 */         boolean flag = true;
/*      */         
/*  215 */         for (int j2 = 0; j2 < l && flag; j2++) {
/*      */           
/*  217 */           int k2 = (k1 * l + j2) * i;
/*  218 */           int l2 = aint[i2 + k2];
/*  219 */           int i3 = l2 >> 24 & 0xFF;
/*      */           
/*  221 */           if (i3 > 16)
/*      */           {
/*  223 */             flag = false;
/*      */           }
/*      */         } 
/*      */         
/*  227 */         if (!flag) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  233 */       if (i1 == 65)
/*      */       {
/*  235 */         i1 = i1;
/*      */       }
/*      */       
/*  238 */       if (i1 == 32)
/*      */       {
/*  240 */         if (k <= 8) {
/*      */           
/*  242 */           l1 = (int)(2.0F * f);
/*      */         }
/*      */         else {
/*      */           
/*  246 */           l1 = (int)(1.5F * f);
/*      */         } 
/*      */       }
/*      */       
/*  250 */       this.charWidthFloat[i1] = (l1 + 1) / f + 1.0F;
/*      */     } 
/*      */     
/*  253 */     FontUtils.readCustomCharWidths(properties, this.charWidthFloat);
/*      */     
/*  255 */     for (int j3 = 0; j3 < this.charWidth.length; j3++)
/*      */     {
/*  257 */       this.charWidth[j3] = Math.round(this.charWidthFloat[j3]);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void readGlyphSizes() {
/*  263 */     InputStream inputstream = null;
/*      */ 
/*      */     
/*      */     try {
/*  267 */       inputstream = getResourceInputStream(new ResourceLocation("font/glyph_sizes.bin"));
/*  268 */       inputstream.read(this.glyphWidth);
/*      */     }
/*  270 */     catch (IOException ioexception) {
/*      */       
/*  272 */       throw new RuntimeException(ioexception);
/*      */     }
/*      */     finally {
/*      */       
/*  276 */       IOUtils.closeQuietly(inputstream);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private float func_181559_a(char p_181559_1_, boolean p_181559_2_) {
/*  282 */     if (p_181559_1_ != ' ' && p_181559_1_ != ' ') {
/*      */       
/*  284 */       int i = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(p_181559_1_);
/*  285 */       return (i != -1 && !this.unicodeFlag) ? renderDefaultChar(i, p_181559_2_) : renderUnicodeChar(p_181559_1_, p_181559_2_);
/*      */     } 
/*      */ 
/*      */     
/*  289 */     return !this.unicodeFlag ? this.charWidthFloat[p_181559_1_] : 4.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float renderDefaultChar(int p_78266_1_, boolean p_78266_2_) {
/*  298 */     int i = p_78266_1_ % 16 * 8;
/*  299 */     int j = p_78266_1_ / 16 * 8;
/*  300 */     int k = p_78266_2_ ? 1 : 0;
/*  301 */     bindTexture(this.locationFontTexture);
/*  302 */     float f = this.charWidthFloat[p_78266_1_];
/*  303 */     float f1 = 7.99F;
/*  304 */     GL11.glBegin(5);
/*  305 */     GL11.glTexCoord2f(i / 128.0F, j / 128.0F);
/*  306 */     GL11.glVertex3f(this.posX + k, this.posY, 0.0F);
/*  307 */     GL11.glTexCoord2f(i / 128.0F, (j + 7.99F) / 128.0F);
/*  308 */     GL11.glVertex3f(this.posX - k, this.posY + 7.99F, 0.0F);
/*  309 */     GL11.glTexCoord2f((i + f1 - 1.0F) / 128.0F, j / 128.0F);
/*  310 */     GL11.glVertex3f(this.posX + f1 - 1.0F + k, this.posY, 0.0F);
/*  311 */     GL11.glTexCoord2f((i + f1 - 1.0F) / 128.0F, (j + 7.99F) / 128.0F);
/*  312 */     GL11.glVertex3f(this.posX + f1 - 1.0F - k, this.posY + 7.99F, 0.0F);
/*  313 */     GL11.glEnd();
/*  314 */     return f;
/*      */   }
/*      */ 
/*      */   
/*      */   private ResourceLocation getUnicodePageLocation(int p_111271_1_) {
/*  319 */     if (unicodePageLocations[p_111271_1_] == null) {
/*      */       
/*  321 */       unicodePageLocations[p_111271_1_] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", new Object[] { Integer.valueOf(p_111271_1_) }));
/*  322 */       unicodePageLocations[p_111271_1_] = FontUtils.getHdFontLocation(unicodePageLocations[p_111271_1_]);
/*      */     } 
/*      */     
/*  325 */     return unicodePageLocations[p_111271_1_];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadGlyphTexture(int p_78257_1_) {
/*  333 */     bindTexture(getUnicodePageLocation(p_78257_1_));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float renderUnicodeChar(char p_78277_1_, boolean p_78277_2_) {
/*  341 */     if (this.glyphWidth[p_78277_1_] == 0)
/*      */     {
/*  343 */       return 0.0F;
/*      */     }
/*      */ 
/*      */     
/*  347 */     int i = p_78277_1_ / 256;
/*  348 */     loadGlyphTexture(i);
/*  349 */     int j = this.glyphWidth[p_78277_1_] >>> 4;
/*  350 */     int k = this.glyphWidth[p_78277_1_] & 0xF;
/*  351 */     float f = j;
/*  352 */     float f1 = (k + 1);
/*  353 */     float f2 = (p_78277_1_ % 16 * 16) + f;
/*  354 */     float f3 = ((p_78277_1_ & 0xFF) / 16 * 16);
/*  355 */     float f4 = f1 - f - 0.02F;
/*  356 */     float f5 = p_78277_2_ ? 1.0F : 0.0F;
/*  357 */     GL11.glBegin(5);
/*  358 */     GL11.glTexCoord2f(f2 / 256.0F, f3 / 256.0F);
/*  359 */     GL11.glVertex3f(this.posX + f5, this.posY, 0.0F);
/*  360 */     GL11.glTexCoord2f(f2 / 256.0F, (f3 + 15.98F) / 256.0F);
/*  361 */     GL11.glVertex3f(this.posX - f5, this.posY + 7.99F, 0.0F);
/*  362 */     GL11.glTexCoord2f((f2 + f4) / 256.0F, f3 / 256.0F);
/*  363 */     GL11.glVertex3f(this.posX + f4 / 2.0F + f5, this.posY, 0.0F);
/*  364 */     GL11.glTexCoord2f((f2 + f4) / 256.0F, (f3 + 15.98F) / 256.0F);
/*  365 */     GL11.glVertex3f(this.posX + f4 / 2.0F - f5, this.posY + 7.99F, 0.0F);
/*  366 */     GL11.glEnd();
/*  367 */     return (f1 - f) / 2.0F + 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drawStringWithShadow(String text, float x, float y, int color) {
/*  376 */     return drawString(text, x, y, color, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drawString(String text, int x, int y, int color) {
/*  384 */     return drawString(text, x, y, color, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drawString(String text, float x, float y, int color, boolean dropShadow) {
/*      */     int i;
/*  392 */     enableAlpha();
/*      */     
/*  394 */     if (this.blend) {
/*      */       
/*  396 */       GlStateManager.getBlendState(this.oldBlendState);
/*  397 */       GlStateManager.enableBlend();
/*  398 */       GlStateManager.blendFunc(770, 771);
/*      */     } 
/*      */     
/*  401 */     resetStyles();
/*      */ 
/*      */     
/*  404 */     if (dropShadow) {
/*      */       
/*  406 */       i = renderString(text, x + 1.0F, y + 1.0F, color, true);
/*  407 */       i = Math.max(i, renderString(text, x, y, color, false));
/*      */     }
/*      */     else {
/*      */       
/*  411 */       i = renderString(text, x, y, color, false);
/*      */     } 
/*      */     
/*  414 */     if (this.blend)
/*      */     {
/*  416 */       GlStateManager.setBlendState(this.oldBlendState);
/*      */     }
/*      */     
/*  419 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String bidiReorder(String p_147647_1_) {
/*      */     try {
/*  429 */       Bidi bidi = new Bidi((new ArabicShaping(8)).shape(p_147647_1_), 127);
/*  430 */       bidi.setReorderingMode(0);
/*  431 */       return bidi.writeReordered(2);
/*      */     }
/*  433 */     catch (ArabicShapingException var3) {
/*      */       
/*  435 */       return p_147647_1_;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetStyles() {
/*  444 */     this.randomStyle = false;
/*  445 */     this.boldStyle = false;
/*  446 */     this.italicStyle = false;
/*  447 */     this.underlineStyle = false;
/*  448 */     this.strikethroughStyle = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderStringAtPos(String p_78255_1_, boolean p_78255_2_) {
/*  456 */     for (int i = 0; i < p_78255_1_.length(); i++) {
/*      */       
/*  458 */       char c0 = p_78255_1_.charAt(i);
/*      */       
/*  460 */       if (c0 == '§' && i + 1 < p_78255_1_.length()) {
/*      */         
/*  462 */         int l = "0123456789abcdefklmnor".indexOf(p_78255_1_.toLowerCase(Locale.ENGLISH).charAt(i + 1));
/*      */         
/*  464 */         if (l < 16) {
/*      */           
/*  466 */           this.randomStyle = false;
/*  467 */           this.boldStyle = false;
/*  468 */           this.strikethroughStyle = false;
/*  469 */           this.underlineStyle = false;
/*  470 */           this.italicStyle = false;
/*      */           
/*  472 */           if (l < 0 || l > 15)
/*      */           {
/*  474 */             l = 15;
/*      */           }
/*      */           
/*  477 */           if (p_78255_2_)
/*      */           {
/*  479 */             l += 16;
/*      */           }
/*      */           
/*  482 */           int i1 = this.colorCode[l];
/*      */           
/*  484 */           if (Config.isCustomColors())
/*      */           {
/*  486 */             i1 = CustomColors.getTextColor(l, i1);
/*      */           }
/*      */           
/*  489 */           this.textColor = i1;
/*  490 */           setColor((i1 >> 16) / 255.0F, (i1 >> 8 & 0xFF) / 255.0F, (i1 & 0xFF) / 255.0F, this.alpha);
/*      */         }
/*  492 */         else if (l == 16) {
/*      */           
/*  494 */           this.randomStyle = true;
/*      */         }
/*  496 */         else if (l == 17) {
/*      */           
/*  498 */           this.boldStyle = true;
/*      */         }
/*  500 */         else if (l == 18) {
/*      */           
/*  502 */           this.strikethroughStyle = true;
/*      */         }
/*  504 */         else if (l == 19) {
/*      */           
/*  506 */           this.underlineStyle = true;
/*      */         }
/*  508 */         else if (l == 20) {
/*      */           
/*  510 */           this.italicStyle = true;
/*      */         }
/*  512 */         else if (l == 21) {
/*      */           
/*  514 */           this.randomStyle = false;
/*  515 */           this.boldStyle = false;
/*  516 */           this.strikethroughStyle = false;
/*  517 */           this.underlineStyle = false;
/*  518 */           this.italicStyle = false;
/*  519 */           setColor(this.red, this.blue, this.green, this.alpha);
/*      */         } 
/*      */         
/*  522 */         i++;
/*      */       }
/*      */       else {
/*      */         
/*  526 */         int j = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(c0);
/*      */         
/*  528 */         if (this.randomStyle && j != -1) {
/*      */           char c1;
/*  530 */           int k = getCharWidth(c0);
/*      */ 
/*      */ 
/*      */           
/*      */           do {
/*  535 */             j = this.fontRandom.nextInt("ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".length());
/*  536 */             c1 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".charAt(j);
/*      */           }
/*  538 */           while (k != getCharWidth(c1));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  544 */           c0 = c1;
/*      */         } 
/*      */         
/*  547 */         float f1 = (j != -1 && !this.unicodeFlag) ? this.offsetBold : 0.5F;
/*  548 */         boolean flag = ((c0 == '\000' || j == -1 || this.unicodeFlag) && p_78255_2_);
/*      */         
/*  550 */         if (flag) {
/*      */           
/*  552 */           this.posX -= f1;
/*  553 */           this.posY -= f1;
/*      */         } 
/*      */         
/*  556 */         float f = func_181559_a(c0, this.italicStyle);
/*      */         
/*  558 */         if (flag) {
/*      */           
/*  560 */           this.posX += f1;
/*  561 */           this.posY += f1;
/*      */         } 
/*      */         
/*  564 */         if (this.boldStyle) {
/*      */           
/*  566 */           this.posX += f1;
/*      */           
/*  568 */           if (flag) {
/*      */             
/*  570 */             this.posX -= f1;
/*  571 */             this.posY -= f1;
/*      */           } 
/*      */           
/*  574 */           func_181559_a(c0, this.italicStyle);
/*  575 */           this.posX -= f1;
/*      */           
/*  577 */           if (flag) {
/*      */             
/*  579 */             this.posX += f1;
/*  580 */             this.posY += f1;
/*      */           } 
/*      */           
/*  583 */           f += f1;
/*      */         } 
/*      */         
/*  586 */         doDraw(f);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doDraw(float p_doDraw_1_) {
/*  593 */     if (this.strikethroughStyle) {
/*      */       
/*  595 */       Tessellator tessellator = Tessellator.getInstance();
/*  596 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  597 */       GlStateManager.disableTexture2D();
/*  598 */       worldrenderer.begin(7, DefaultVertexFormats.field_181705_e);
/*  599 */       worldrenderer.pos(this.posX, (this.posY + (this.FONT_HEIGHT / 2)), 0.0D).endVertex();
/*  600 */       worldrenderer.pos((this.posX + p_doDraw_1_), (this.posY + (this.FONT_HEIGHT / 2)), 0.0D).endVertex();
/*  601 */       worldrenderer.pos((this.posX + p_doDraw_1_), (this.posY + (this.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
/*  602 */       worldrenderer.pos(this.posX, (this.posY + (this.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
/*  603 */       tessellator.draw();
/*  604 */       GlStateManager.enableTexture2D();
/*      */     } 
/*      */     
/*  607 */     if (this.underlineStyle) {
/*      */       
/*  609 */       Tessellator tessellator1 = Tessellator.getInstance();
/*  610 */       WorldRenderer worldrenderer1 = tessellator1.getWorldRenderer();
/*  611 */       GlStateManager.disableTexture2D();
/*  612 */       worldrenderer1.begin(7, DefaultVertexFormats.field_181705_e);
/*  613 */       int i = this.underlineStyle ? -1 : 0;
/*  614 */       worldrenderer1.pos((this.posX + i), (this.posY + this.FONT_HEIGHT), 0.0D).endVertex();
/*  615 */       worldrenderer1.pos((this.posX + p_doDraw_1_), (this.posY + this.FONT_HEIGHT), 0.0D).endVertex();
/*  616 */       worldrenderer1.pos((this.posX + p_doDraw_1_), (this.posY + this.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
/*  617 */       worldrenderer1.pos((this.posX + i), (this.posY + this.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
/*  618 */       tessellator1.draw();
/*  619 */       GlStateManager.enableTexture2D();
/*      */     } 
/*      */     
/*  622 */     this.posX += p_doDraw_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int renderStringAligned(String text, int x, int y, int p_78274_4_, int color, boolean dropShadow) {
/*  630 */     if (this.bidiFlag) {
/*      */       
/*  632 */       int i = getStringWidth(bidiReorder(text));
/*  633 */       x = x + p_78274_4_ - i;
/*      */     } 
/*      */     
/*  636 */     return renderString(text, x, y, color, dropShadow);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int renderString(String text, float x, float y, int color, boolean dropShadow) {
/*  644 */     if (text == null)
/*      */     {
/*  646 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  650 */     if (this.bidiFlag)
/*      */     {
/*  652 */       text = bidiReorder(text);
/*      */     }
/*      */     
/*  655 */     if ((color & 0xFC000000) == 0)
/*      */     {
/*  657 */       color |= 0xFF000000;
/*      */     }
/*      */     
/*  660 */     if (dropShadow)
/*      */     {
/*  662 */       color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
/*      */     }
/*      */     
/*  665 */     this.red = (color >> 16 & 0xFF) / 255.0F;
/*  666 */     this.blue = (color >> 8 & 0xFF) / 255.0F;
/*  667 */     this.green = (color & 0xFF) / 255.0F;
/*  668 */     this.alpha = (color >> 24 & 0xFF) / 255.0F;
/*  669 */     setColor(this.red, this.blue, this.green, this.alpha);
/*  670 */     this.posX = x;
/*  671 */     this.posY = y;
/*  672 */     renderStringAtPos(text, dropShadow);
/*  673 */     return (int)this.posX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStringWidth(String text) {
/*  682 */     if (text == null)
/*      */     {
/*  684 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  688 */     float f = 0.0F;
/*  689 */     boolean flag = false;
/*      */     
/*  691 */     for (int i = 0; i < text.length(); i++) {
/*      */       
/*  693 */       char c0 = text.charAt(i);
/*  694 */       float f1 = getCharWidthFloat(c0);
/*      */       
/*  696 */       if (f1 < 0.0F && i < text.length() - 1) {
/*      */         
/*  698 */         i++;
/*  699 */         c0 = text.charAt(i);
/*      */         
/*  701 */         if (c0 != 'l' && c0 != 'L') {
/*      */           
/*  703 */           if (c0 == 'r' || c0 == 'R')
/*      */           {
/*  705 */             flag = false;
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  710 */           flag = true;
/*      */         } 
/*      */         
/*  713 */         f1 = 0.0F;
/*      */       } 
/*      */       
/*  716 */       f += f1;
/*      */       
/*  718 */       if (flag && f1 > 0.0F)
/*      */       {
/*  720 */         f += this.unicodeFlag ? 1.0F : this.offsetBold;
/*      */       }
/*      */     } 
/*      */     
/*  724 */     return Math.round(f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCharWidth(char character) {
/*  733 */     return Math.round(getCharWidthFloat(character));
/*      */   }
/*      */ 
/*      */   
/*      */   private float getCharWidthFloat(char p_getCharWidthFloat_1_) {
/*  738 */     if (p_getCharWidthFloat_1_ == '§')
/*      */     {
/*  740 */       return -1.0F;
/*      */     }
/*  742 */     if (p_getCharWidthFloat_1_ != ' ' && p_getCharWidthFloat_1_ != ' ') {
/*      */       
/*  744 */       int i = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(p_getCharWidthFloat_1_);
/*      */       
/*  746 */       if (p_getCharWidthFloat_1_ > '\000' && i != -1 && !this.unicodeFlag)
/*      */       {
/*  748 */         return this.charWidthFloat[i];
/*      */       }
/*  750 */       if (this.glyphWidth[p_getCharWidthFloat_1_] != 0) {
/*      */         
/*  752 */         int j = this.glyphWidth[p_getCharWidthFloat_1_] >>> 4;
/*  753 */         int k = this.glyphWidth[p_getCharWidthFloat_1_] & 0xF;
/*      */         
/*  755 */         if (k > 7) {
/*      */           
/*  757 */           k = 15;
/*  758 */           j = 0;
/*      */         } 
/*      */         
/*  761 */         k++;
/*  762 */         return ((k - j) / 2 + 1);
/*      */       } 
/*      */ 
/*      */       
/*  766 */       return 0.0F;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  771 */     return this.charWidthFloat[32];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String trimStringToWidth(String text, int width) {
/*  780 */     return trimStringToWidth(text, width, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String trimStringToWidth(String text, int width, boolean reverse) {
/*  788 */     StringBuilder stringbuilder = new StringBuilder();
/*  789 */     float f = 0.0F;
/*  790 */     int i = reverse ? (text.length() - 1) : 0;
/*  791 */     int j = reverse ? -1 : 1;
/*  792 */     boolean flag = false;
/*  793 */     boolean flag1 = false;
/*      */     int k;
/*  795 */     for (k = i; k >= 0 && k < text.length() && f < width; k += j) {
/*      */       
/*  797 */       char c0 = text.charAt(k);
/*  798 */       float f1 = getCharWidthFloat(c0);
/*      */       
/*  800 */       if (flag) {
/*      */         
/*  802 */         flag = false;
/*      */         
/*  804 */         if (c0 != 'l' && c0 != 'L')
/*      */         {
/*  806 */           if (c0 == 'r' || c0 == 'R')
/*      */           {
/*  808 */             flag1 = false;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  813 */           flag1 = true;
/*      */         }
/*      */       
/*  816 */       } else if (f1 < 0.0F) {
/*      */         
/*  818 */         flag = true;
/*      */       }
/*      */       else {
/*      */         
/*  822 */         f += f1;
/*      */         
/*  824 */         if (flag1)
/*      */         {
/*  826 */           f++;
/*      */         }
/*      */       } 
/*      */       
/*  830 */       if (f > width) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/*  835 */       if (reverse) {
/*      */         
/*  837 */         stringbuilder.insert(0, c0);
/*      */       }
/*      */       else {
/*      */         
/*  841 */         stringbuilder.append(c0);
/*      */       } 
/*      */     } 
/*      */     
/*  845 */     return stringbuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String trimStringNewline(String text) {
/*  853 */     while (text != null && text.endsWith("\n"))
/*      */     {
/*  855 */       text = text.substring(0, text.length() - 1);
/*      */     }
/*      */     
/*  858 */     return text;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawSplitString(String str, int x, int y, int wrapWidth, int textColor) {
/*  866 */     if (this.blend) {
/*      */       
/*  868 */       GlStateManager.getBlendState(this.oldBlendState);
/*  869 */       GlStateManager.enableBlend();
/*  870 */       GlStateManager.blendFunc(770, 771);
/*      */     } 
/*      */     
/*  873 */     resetStyles();
/*  874 */     this.textColor = textColor;
/*  875 */     str = trimStringNewline(str);
/*  876 */     renderSplitString(str, x, y, wrapWidth, false);
/*      */     
/*  878 */     if (this.blend)
/*      */     {
/*  880 */       GlStateManager.setBlendState(this.oldBlendState);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderSplitString(String str, int x, int y, int wrapWidth, boolean addShadow) {
/*  890 */     for (String s : listFormattedStringToWidth(str, wrapWidth)) {
/*      */       
/*  892 */       renderStringAligned(s, x, y, wrapWidth, this.textColor, addShadow);
/*  893 */       y += this.FONT_HEIGHT;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int splitStringWidth(String p_78267_1_, int p_78267_2_) {
/*  902 */     return this.FONT_HEIGHT * listFormattedStringToWidth(p_78267_1_, p_78267_2_).size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUnicodeFlag(boolean unicodeFlagIn) {
/*  911 */     this.unicodeFlag = unicodeFlagIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUnicodeFlag() {
/*  920 */     return this.unicodeFlag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBidiFlag(boolean bidiFlagIn) {
/*  928 */     this.bidiFlag = bidiFlagIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<String> listFormattedStringToWidth(String str, int wrapWidth) {
/*  933 */     return Arrays.asList(wrapFormattedStringToWidth(str, wrapWidth).split("\n"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String wrapFormattedStringToWidth(String str, int wrapWidth) {
/*  941 */     if (str.length() <= 1)
/*      */     {
/*  943 */       return str;
/*      */     }
/*      */ 
/*      */     
/*  947 */     int i = sizeStringToWidth(str, wrapWidth);
/*      */     
/*  949 */     if (str.length() <= i)
/*      */     {
/*  951 */       return str;
/*      */     }
/*      */ 
/*      */     
/*  955 */     String s = str.substring(0, i);
/*  956 */     char c0 = str.charAt(i);
/*  957 */     boolean flag = (c0 == ' ' || c0 == '\n');
/*  958 */     String s1 = getFormatFromString(s) + str.substring(i + (flag ? 1 : 0));
/*  959 */     return s + "\n" + wrapFormattedStringToWidth(s1, wrapWidth);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int sizeStringToWidth(String str, int wrapWidth) {
/*  969 */     int i = str.length();
/*  970 */     float f = 0.0F;
/*  971 */     int j = 0;
/*  972 */     int k = -1;
/*      */     
/*  974 */     for (boolean flag = false; j < i; j++) {
/*      */       
/*  976 */       char c0 = str.charAt(j);
/*      */       
/*  978 */       switch (c0) {
/*      */         
/*      */         case '\n':
/*  981 */           j--;
/*      */           break;
/*      */         
/*      */         case ' ':
/*  985 */           k = j;
/*      */         
/*      */         default:
/*  988 */           f += getCharWidth(c0);
/*      */           
/*  990 */           if (flag)
/*      */           {
/*  992 */             f++;
/*      */           }
/*      */           break;
/*      */ 
/*      */         
/*      */         case '§':
/*  998 */           if (j < i - 1) {
/*      */             
/* 1000 */             j++;
/* 1001 */             char c1 = str.charAt(j);
/*      */             
/* 1003 */             if (c1 != 'l' && c1 != 'L') {
/*      */               
/* 1005 */               if (c1 == 'r' || c1 == 'R' || isFormatColor(c1))
/*      */               {
/* 1007 */                 flag = false;
/*      */               }
/*      */               
/*      */               break;
/*      */             } 
/* 1012 */             flag = true;
/*      */           } 
/*      */           break;
/*      */       } 
/*      */       
/* 1017 */       if (c0 == '\n') {
/*      */ 
/*      */         
/* 1020 */         k = ++j;
/*      */         
/*      */         break;
/*      */       } 
/* 1024 */       if (Math.round(f) > wrapWidth) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1030 */     return (j != i && k != -1 && k < j) ? k : j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isFormatColor(char colorChar) {
/* 1038 */     return ((colorChar >= '0' && colorChar <= '9') || (colorChar >= 'a' && colorChar <= 'f') || (colorChar >= 'A' && colorChar <= 'F'));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isFormatSpecial(char formatChar) {
/* 1046 */     return ((formatChar >= 'k' && formatChar <= 'o') || (formatChar >= 'K' && formatChar <= 'O') || formatChar == 'r' || formatChar == 'R');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getFormatFromString(String text) {
/* 1054 */     String s = "";
/* 1055 */     int i = -1;
/* 1056 */     int j = text.length();
/*      */     
/* 1058 */     while ((i = text.indexOf('§', i + 1)) != -1) {
/*      */       
/* 1060 */       if (i < j - 1) {
/*      */         
/* 1062 */         char c0 = text.charAt(i + 1);
/*      */         
/* 1064 */         if (isFormatColor(c0)) {
/*      */           
/* 1066 */           s = "§" + c0; continue;
/*      */         } 
/* 1068 */         if (isFormatSpecial(c0))
/*      */         {
/* 1070 */           s = s + "§" + c0;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1075 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBidiFlag() {
/* 1083 */     return this.bidiFlag;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getColorCode(char character) {
/* 1088 */     int i = "0123456789abcdef".indexOf(character);
/*      */     
/* 1090 */     if (i >= 0 && i < this.colorCode.length) {
/*      */       
/* 1092 */       int j = this.colorCode[i];
/*      */       
/* 1094 */       if (Config.isCustomColors())
/*      */       {
/* 1096 */         j = CustomColors.getTextColor(i, j);
/*      */       }
/*      */       
/* 1099 */       return j;
/*      */     } 
/*      */ 
/*      */     
/* 1103 */     return 16777215;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setColor(float p_setColor_1_, float p_setColor_2_, float p_setColor_3_, float p_setColor_4_) {
/* 1109 */     GlStateManager.color(p_setColor_1_, p_setColor_2_, p_setColor_3_, p_setColor_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void enableAlpha() {
/* 1114 */     GlStateManager.enableAlpha();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void bindTexture(ResourceLocation p_bindTexture_1_) {
/* 1119 */     this.renderEngine.bindTexture(p_bindTexture_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   protected InputStream getResourceInputStream(ResourceLocation p_getResourceInputStream_1_) throws IOException {
/* 1124 */     return Minecraft.getMinecraft().getResourceManager().getResource(p_getResourceInputStream_1_).getInputStream();
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\FontRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
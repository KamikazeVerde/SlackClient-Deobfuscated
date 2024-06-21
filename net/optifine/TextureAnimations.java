/*     */ package net.optifine;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.resources.IResourcePack;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.ResUtils;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ 
/*     */ public class TextureAnimations
/*     */ {
/*  25 */   private static TextureAnimation[] textureAnimations = null;
/*  26 */   private static int countAnimationsActive = 0;
/*  27 */   private static int frameCountAnimations = 0;
/*     */ 
/*     */   
/*     */   public static void reset() {
/*  31 */     textureAnimations = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update() {
/*  36 */     textureAnimations = null;
/*  37 */     countAnimationsActive = 0;
/*  38 */     IResourcePack[] airesourcepack = Config.getResourcePacks();
/*  39 */     textureAnimations = getTextureAnimations(airesourcepack);
/*  40 */     updateAnimations();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateAnimations() {
/*  45 */     if (textureAnimations != null && Config.isAnimatedTextures()) {
/*     */       
/*  47 */       int i = 0;
/*     */       
/*  49 */       for (int j = 0; j < textureAnimations.length; j++) {
/*     */         
/*  51 */         TextureAnimation textureanimation = textureAnimations[j];
/*  52 */         textureanimation.updateTexture();
/*     */         
/*  54 */         if (textureanimation.isActive())
/*     */         {
/*  56 */           i++;
/*     */         }
/*     */       } 
/*     */       
/*  60 */       int k = (Config.getMinecraft()).entityRenderer.frameCount;
/*     */       
/*  62 */       if (k != frameCountAnimations) {
/*     */         
/*  64 */         countAnimationsActive = i;
/*  65 */         frameCountAnimations = k;
/*     */       } 
/*     */       
/*  68 */       if (SmartAnimations.isActive())
/*     */       {
/*  70 */         SmartAnimations.resetTexturesRendered();
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  75 */       countAnimationsActive = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static TextureAnimation[] getTextureAnimations(IResourcePack[] rps) {
/*  81 */     List list = new ArrayList();
/*     */     
/*  83 */     for (int i = 0; i < rps.length; i++) {
/*     */       
/*  85 */       IResourcePack iresourcepack = rps[i];
/*  86 */       TextureAnimation[] atextureanimation = getTextureAnimations(iresourcepack);
/*     */       
/*  88 */       if (atextureanimation != null)
/*     */       {
/*  90 */         list.addAll(Arrays.asList(atextureanimation));
/*     */       }
/*     */     } 
/*     */     
/*  94 */     TextureAnimation[] atextureanimation1 = (TextureAnimation[])list.toArray((Object[])new TextureAnimation[list.size()]);
/*  95 */     return atextureanimation1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static TextureAnimation[] getTextureAnimations(IResourcePack rp) {
/* 100 */     String[] astring = ResUtils.collectFiles(rp, "mcpatcher/anim/", ".properties", null);
/*     */     
/* 102 */     if (astring.length <= 0)
/*     */     {
/* 104 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 108 */     List<TextureAnimation> list = new ArrayList();
/*     */     
/* 110 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 112 */       String s = astring[i];
/* 113 */       Config.dbg("Texture animation: " + s);
/*     */ 
/*     */       
/*     */       try {
/* 117 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/* 118 */         InputStream inputstream = rp.getInputStream(resourcelocation);
/* 119 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 120 */         propertiesOrdered.load(inputstream);
/* 121 */         TextureAnimation textureanimation = makeTextureAnimation((Properties)propertiesOrdered, resourcelocation);
/*     */         
/* 123 */         if (textureanimation != null) {
/*     */           
/* 125 */           ResourceLocation resourcelocation1 = new ResourceLocation(textureanimation.getDstTex());
/*     */           
/* 127 */           if (Config.getDefiningResourcePack(resourcelocation1) != rp)
/*     */           {
/* 129 */             Config.dbg("Skipped: " + s + ", target texture not loaded from same resource pack");
/*     */           }
/*     */           else
/*     */           {
/* 133 */             list.add(textureanimation);
/*     */           }
/*     */         
/*     */         } 
/* 137 */       } catch (FileNotFoundException filenotfoundexception) {
/*     */         
/* 139 */         Config.warn("File not found: " + filenotfoundexception.getMessage());
/*     */       }
/* 141 */       catch (IOException ioexception) {
/*     */         
/* 143 */         ioexception.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     TextureAnimation[] atextureanimation = list.<TextureAnimation>toArray(new TextureAnimation[list.size()]);
/* 148 */     return atextureanimation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static TextureAnimation makeTextureAnimation(Properties props, ResourceLocation propLoc) {
/* 154 */     String s = props.getProperty("from");
/* 155 */     String s1 = props.getProperty("to");
/* 156 */     int i = Config.parseInt(props.getProperty("x"), -1);
/* 157 */     int j = Config.parseInt(props.getProperty("y"), -1);
/* 158 */     int k = Config.parseInt(props.getProperty("w"), -1);
/* 159 */     int l = Config.parseInt(props.getProperty("h"), -1);
/*     */     
/* 161 */     if (s != null && s1 != null) {
/*     */       
/* 163 */       if (i >= 0 && j >= 0 && k >= 0 && l >= 0) {
/*     */         
/* 165 */         s = s.trim();
/* 166 */         s1 = s1.trim();
/* 167 */         String s2 = TextureUtils.getBasePath(propLoc.getResourcePath());
/* 168 */         s = TextureUtils.fixResourcePath(s, s2);
/* 169 */         s1 = TextureUtils.fixResourcePath(s1, s2);
/* 170 */         byte[] abyte = getCustomTextureData(s, k);
/*     */         
/* 172 */         if (abyte == null) {
/*     */           
/* 174 */           Config.warn("TextureAnimation: Source texture not found: " + s1);
/* 175 */           return null;
/*     */         } 
/*     */ 
/*     */         
/* 179 */         int i1 = abyte.length / 4;
/* 180 */         int j1 = i1 / k * l;
/* 181 */         int k1 = j1 * k * l;
/*     */         
/* 183 */         if (i1 != k1) {
/*     */           
/* 185 */           Config.warn("TextureAnimation: Source texture has invalid number of frames: " + s + ", frames: " + (i1 / (k * l)));
/* 186 */           return null;
/*     */         } 
/*     */ 
/*     */         
/* 190 */         ResourceLocation resourcelocation = new ResourceLocation(s1);
/*     */ 
/*     */         
/*     */         try {
/* 194 */           InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */           
/* 196 */           if (inputstream == null) {
/*     */             
/* 198 */             Config.warn("TextureAnimation: Target texture not found: " + s1);
/* 199 */             return null;
/*     */           } 
/*     */ 
/*     */           
/* 203 */           BufferedImage bufferedimage = readTextureImage(inputstream);
/*     */           
/* 205 */           if (i + k <= bufferedimage.getWidth() && j + l <= bufferedimage.getHeight()) {
/*     */             
/* 207 */             TextureAnimation textureanimation = new TextureAnimation(s, abyte, s1, resourcelocation, i, j, k, l, props);
/* 208 */             return textureanimation;
/*     */           } 
/*     */ 
/*     */           
/* 212 */           Config.warn("TextureAnimation: Animation coordinates are outside the target texture: " + s1);
/* 213 */           return null;
/*     */ 
/*     */         
/*     */         }
/* 217 */         catch (IOException var17) {
/*     */           
/* 219 */           Config.warn("TextureAnimation: Target texture not found: " + s1);
/* 220 */           return null;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 227 */       Config.warn("TextureAnimation: Invalid coordinates");
/* 228 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 233 */     Config.warn("TextureAnimation: Source or target texture not specified");
/* 234 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] getCustomTextureData(String imagePath, int tileWidth) {
/* 240 */     byte[] abyte = loadImage(imagePath, tileWidth);
/*     */     
/* 242 */     if (abyte == null)
/*     */     {
/* 244 */       abyte = loadImage("/anim" + imagePath, tileWidth);
/*     */     }
/*     */     
/* 247 */     return abyte;
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte[] loadImage(String name, int targetWidth) {
/* 252 */     GameSettings gamesettings = Config.getGameSettings();
/*     */ 
/*     */     
/*     */     try {
/* 256 */       ResourceLocation resourcelocation = new ResourceLocation(name);
/* 257 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */       
/* 259 */       if (inputstream == null)
/*     */       {
/* 261 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 265 */       BufferedImage bufferedimage = readTextureImage(inputstream);
/* 266 */       inputstream.close();
/*     */       
/* 268 */       if (bufferedimage == null)
/*     */       {
/* 270 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 274 */       if (targetWidth > 0 && bufferedimage.getWidth() != targetWidth) {
/*     */         
/* 276 */         double d0 = (bufferedimage.getHeight() / bufferedimage.getWidth());
/* 277 */         int j = (int)(targetWidth * d0);
/* 278 */         bufferedimage = scaleBufferedImage(bufferedimage, targetWidth, j);
/*     */       } 
/*     */       
/* 281 */       int k2 = bufferedimage.getWidth();
/* 282 */       int i = bufferedimage.getHeight();
/* 283 */       int[] aint = new int[k2 * i];
/* 284 */       byte[] abyte = new byte[k2 * i * 4];
/* 285 */       bufferedimage.getRGB(0, 0, k2, i, aint, 0, k2);
/*     */       
/* 287 */       for (int k = 0; k < aint.length; k++) {
/*     */         
/* 289 */         int l = aint[k] >> 24 & 0xFF;
/* 290 */         int i1 = aint[k] >> 16 & 0xFF;
/* 291 */         int j1 = aint[k] >> 8 & 0xFF;
/* 292 */         int k1 = aint[k] & 0xFF;
/*     */         
/* 294 */         if (gamesettings != null && gamesettings.anaglyph) {
/*     */           
/* 296 */           int l1 = (i1 * 30 + j1 * 59 + k1 * 11) / 100;
/* 297 */           int i2 = (i1 * 30 + j1 * 70) / 100;
/* 298 */           int j2 = (i1 * 30 + k1 * 70) / 100;
/* 299 */           i1 = l1;
/* 300 */           j1 = i2;
/* 301 */           k1 = j2;
/*     */         } 
/*     */         
/* 304 */         abyte[k * 4 + 0] = (byte)i1;
/* 305 */         abyte[k * 4 + 1] = (byte)j1;
/* 306 */         abyte[k * 4 + 2] = (byte)k1;
/* 307 */         abyte[k * 4 + 3] = (byte)l;
/*     */       } 
/*     */       
/* 310 */       return abyte;
/*     */ 
/*     */     
/*     */     }
/* 314 */     catch (FileNotFoundException var18) {
/*     */       
/* 316 */       return null;
/*     */     }
/* 318 */     catch (Exception exception) {
/*     */       
/* 320 */       exception.printStackTrace();
/* 321 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static BufferedImage readTextureImage(InputStream par1InputStream) throws IOException {
/* 327 */     BufferedImage bufferedimage = ImageIO.read(par1InputStream);
/* 328 */     par1InputStream.close();
/* 329 */     return bufferedimage;
/*     */   }
/*     */ 
/*     */   
/*     */   private static BufferedImage scaleBufferedImage(BufferedImage image, int width, int height) {
/* 334 */     BufferedImage bufferedimage = new BufferedImage(width, height, 2);
/* 335 */     Graphics2D graphics2d = bufferedimage.createGraphics();
/* 336 */     graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/* 337 */     graphics2d.drawImage(image, 0, 0, width, height, null);
/* 338 */     return bufferedimage;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getCountAnimations() {
/* 343 */     return (textureAnimations == null) ? 0 : textureAnimations.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getCountAnimationsActive() {
/* 348 */     return countAnimationsActive;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\TextureAnimations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
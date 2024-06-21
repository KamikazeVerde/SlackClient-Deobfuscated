/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.data.AnimationFrame;
/*     */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.SmartAnimations;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import net.optifine.util.CounterInt;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ public class TextureAtlasSprite
/*     */ {
/*     */   private final String iconName;
/*  26 */   protected List<int[][]> framesTextureData = Lists.newArrayList();
/*     */   protected int[][] interpolatedFrameData;
/*     */   private AnimationMetadataSection animationMetadata;
/*     */   protected boolean rotated;
/*     */   protected int originX;
/*     */   protected int originY;
/*     */   protected int width;
/*     */   protected int height;
/*     */   private float minU;
/*     */   private float maxU;
/*     */   private float minV;
/*     */   private float maxV;
/*     */   protected int frameCounter;
/*     */   protected int tickCounter;
/*  40 */   private static String locationNameClock = "builtin/clock";
/*  41 */   private static String locationNameCompass = "builtin/compass";
/*  42 */   private int indexInMap = -1;
/*     */   public float baseU;
/*     */   public float baseV;
/*     */   public int sheetWidth;
/*     */   public int sheetHeight;
/*  47 */   public int glSpriteTextureId = -1;
/*  48 */   public TextureAtlasSprite spriteSingle = null;
/*     */   public boolean isSpriteSingle = false;
/*  50 */   public int mipmapLevels = 0;
/*  51 */   public TextureAtlasSprite spriteNormal = null;
/*  52 */   public TextureAtlasSprite spriteSpecular = null;
/*     */   public boolean isShadersSprite = false;
/*     */   public boolean isEmissive = false;
/*  55 */   public TextureAtlasSprite spriteEmissive = null;
/*  56 */   private int animationIndex = -1;
/*     */   
/*     */   private boolean animationActive = false;
/*     */   
/*     */   private TextureAtlasSprite(String p_i7_1_, boolean p_i7_2_) {
/*  61 */     this.iconName = p_i7_1_;
/*  62 */     this.isSpriteSingle = p_i7_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite(String spriteName) {
/*  67 */     this.iconName = spriteName;
/*     */     
/*  69 */     if (Config.isMultiTexture())
/*     */     {
/*  71 */       this.spriteSingle = new TextureAtlasSprite(getIconName() + ".spriteSingle", true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected static TextureAtlasSprite makeAtlasSprite(ResourceLocation spriteResourceLocation) {
/*  77 */     String s = spriteResourceLocation.toString();
/*  78 */     return locationNameClock.equals(s) ? new TextureClock(s) : (locationNameCompass.equals(s) ? new TextureCompass(s) : new TextureAtlasSprite(s));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setLocationNameClock(String clockName) {
/*  83 */     locationNameClock = clockName;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setLocationNameCompass(String compassName) {
/*  88 */     locationNameCompass = compassName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initSprite(int inX, int inY, int originInX, int originInY, boolean rotatedIn) {
/*  93 */     this.originX = originInX;
/*  94 */     this.originY = originInY;
/*  95 */     this.rotated = rotatedIn;
/*  96 */     float f = (float)(0.009999999776482582D / inX);
/*  97 */     float f1 = (float)(0.009999999776482582D / inY);
/*  98 */     this.minU = originInX / (float)inX + f;
/*  99 */     this.maxU = (originInX + this.width) / (float)inX - f;
/* 100 */     this.minV = originInY / inY + f1;
/* 101 */     this.maxV = (originInY + this.height) / inY - f1;
/* 102 */     this.baseU = Math.min(this.minU, this.maxU);
/* 103 */     this.baseV = Math.min(this.minV, this.maxV);
/*     */     
/* 105 */     if (this.spriteSingle != null)
/*     */     {
/* 107 */       this.spriteSingle.initSprite(this.width, this.height, 0, 0, false);
/*     */     }
/*     */     
/* 110 */     if (this.spriteNormal != null)
/*     */     {
/* 112 */       this.spriteNormal.copyFrom(this);
/*     */     }
/*     */     
/* 115 */     if (this.spriteSpecular != null)
/*     */     {
/* 117 */       this.spriteSpecular.copyFrom(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void copyFrom(TextureAtlasSprite atlasSpirit) {
/* 123 */     this.originX = atlasSpirit.originX;
/* 124 */     this.originY = atlasSpirit.originY;
/* 125 */     this.width = atlasSpirit.width;
/* 126 */     this.height = atlasSpirit.height;
/* 127 */     this.rotated = atlasSpirit.rotated;
/* 128 */     this.minU = atlasSpirit.minU;
/* 129 */     this.maxU = atlasSpirit.maxU;
/* 130 */     this.minV = atlasSpirit.minV;
/* 131 */     this.maxV = atlasSpirit.maxV;
/*     */     
/* 133 */     if (atlasSpirit != Config.getTextureMap().getMissingSprite())
/*     */     {
/* 135 */       this.indexInMap = atlasSpirit.indexInMap;
/*     */     }
/*     */     
/* 138 */     this.baseU = atlasSpirit.baseU;
/* 139 */     this.baseV = atlasSpirit.baseV;
/* 140 */     this.sheetWidth = atlasSpirit.sheetWidth;
/* 141 */     this.sheetHeight = atlasSpirit.sheetHeight;
/* 142 */     this.glSpriteTextureId = atlasSpirit.glSpriteTextureId;
/* 143 */     this.mipmapLevels = atlasSpirit.mipmapLevels;
/*     */     
/* 145 */     if (this.spriteSingle != null)
/*     */     {
/* 147 */       this.spriteSingle.initSprite(this.width, this.height, 0, 0, false);
/*     */     }
/*     */     
/* 150 */     this.animationIndex = atlasSpirit.animationIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOriginX() {
/* 158 */     return this.originX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOriginY() {
/* 166 */     return this.originY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconWidth() {
/* 174 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconHeight() {
/* 182 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinU() {
/* 190 */     return this.minU;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxU() {
/* 198 */     return this.maxU;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getInterpolatedU(double u) {
/* 206 */     float f = this.maxU - this.minU;
/* 207 */     return this.minU + f * (float)u / 16.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinV() {
/* 215 */     return this.minV;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxV() {
/* 223 */     return this.maxV;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getInterpolatedV(double v) {
/* 231 */     float f = this.maxV - this.minV;
/* 232 */     return this.minV + f * (float)v / 16.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIconName() {
/* 237 */     return this.iconName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAnimation() {
/* 242 */     if (this.animationMetadata != null) {
/*     */       
/* 244 */       this.animationActive = SmartAnimations.isActive() ? SmartAnimations.isSpriteRendered(this.animationIndex) : true;
/* 245 */       this.tickCounter++;
/*     */       
/* 247 */       if (this.tickCounter >= this.animationMetadata.getFrameTimeSingle(this.frameCounter)) {
/*     */         
/* 249 */         int i = this.animationMetadata.getFrameIndex(this.frameCounter);
/* 250 */         int j = (this.animationMetadata.getFrameCount() == 0) ? this.framesTextureData.size() : this.animationMetadata.getFrameCount();
/* 251 */         this.frameCounter = (this.frameCounter + 1) % j;
/* 252 */         this.tickCounter = 0;
/* 253 */         int k = this.animationMetadata.getFrameIndex(this.frameCounter);
/* 254 */         boolean flag = false;
/* 255 */         boolean flag1 = this.isSpriteSingle;
/*     */         
/* 257 */         if (!this.animationActive) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 262 */         if (i != k && k >= 0 && k < this.framesTextureData.size())
/*     */         {
/* 264 */           TextureUtil.uploadTextureMipmap(this.framesTextureData.get(k), this.width, this.height, this.originX, this.originY, flag, flag1);
/*     */         }
/*     */       }
/* 267 */       else if (this.animationMetadata.isInterpolate()) {
/*     */         
/* 269 */         if (!this.animationActive) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 274 */         updateAnimationInterpolated();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateAnimationInterpolated() {
/* 281 */     double d0 = 1.0D - this.tickCounter / this.animationMetadata.getFrameTimeSingle(this.frameCounter);
/* 282 */     int i = this.animationMetadata.getFrameIndex(this.frameCounter);
/* 283 */     int j = (this.animationMetadata.getFrameCount() == 0) ? this.framesTextureData.size() : this.animationMetadata.getFrameCount();
/* 284 */     int k = this.animationMetadata.getFrameIndex((this.frameCounter + 1) % j);
/*     */     
/* 286 */     if (i != k && k >= 0 && k < this.framesTextureData.size()) {
/*     */       
/* 288 */       int[][] aint = this.framesTextureData.get(i);
/* 289 */       int[][] aint1 = this.framesTextureData.get(k);
/*     */       
/* 291 */       if (this.interpolatedFrameData == null || this.interpolatedFrameData.length != aint.length)
/*     */       {
/* 293 */         this.interpolatedFrameData = new int[aint.length][];
/*     */       }
/*     */       
/* 296 */       for (int l = 0; l < aint.length; l++) {
/*     */         
/* 298 */         if (this.interpolatedFrameData[l] == null)
/*     */         {
/* 300 */           this.interpolatedFrameData[l] = new int[(aint[l]).length];
/*     */         }
/*     */         
/* 303 */         if (l < aint1.length && (aint1[l]).length == (aint[l]).length)
/*     */         {
/* 305 */           for (int i1 = 0; i1 < (aint[l]).length; i1++) {
/*     */             
/* 307 */             int j1 = aint[l][i1];
/* 308 */             int k1 = aint1[l][i1];
/* 309 */             int l1 = (int)(((j1 & 0xFF0000) >> 16) * d0 + ((k1 & 0xFF0000) >> 16) * (1.0D - d0));
/* 310 */             int i2 = (int)(((j1 & 0xFF00) >> 8) * d0 + ((k1 & 0xFF00) >> 8) * (1.0D - d0));
/* 311 */             int j2 = (int)((j1 & 0xFF) * d0 + (k1 & 0xFF) * (1.0D - d0));
/* 312 */             this.interpolatedFrameData[l][i1] = j1 & 0xFF000000 | l1 << 16 | i2 << 8 | j2;
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 317 */       TextureUtil.uploadTextureMipmap(this.interpolatedFrameData, this.width, this.height, this.originX, this.originY, false, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int[][] getFrameTextureData(int index) {
/* 323 */     return this.framesTextureData.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFrameCount() {
/* 328 */     return this.framesTextureData.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIconWidth(int newWidth) {
/* 333 */     this.width = newWidth;
/*     */     
/* 335 */     if (this.spriteSingle != null)
/*     */     {
/* 337 */       this.spriteSingle.setIconWidth(this.width);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIconHeight(int newHeight) {
/* 343 */     this.height = newHeight;
/*     */     
/* 345 */     if (this.spriteSingle != null)
/*     */     {
/* 347 */       this.spriteSingle.setIconHeight(this.height);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSprite(BufferedImage[] images, AnimationMetadataSection meta) throws IOException {
/* 353 */     resetSprite();
/* 354 */     int i = images[0].getWidth();
/* 355 */     int j = images[0].getHeight();
/* 356 */     this.width = i;
/* 357 */     this.height = j;
/*     */     
/* 359 */     if (this.spriteSingle != null) {
/*     */       
/* 361 */       this.spriteSingle.width = this.width;
/* 362 */       this.spriteSingle.height = this.height;
/*     */     } 
/*     */     
/* 365 */     int[][] aint = new int[images.length][];
/*     */     
/* 367 */     for (int k = 0; k < images.length; k++) {
/*     */       
/* 369 */       BufferedImage bufferedimage = images[k];
/*     */       
/* 371 */       if (bufferedimage != null) {
/*     */         
/* 373 */         if (this.width >> k != bufferedimage.getWidth())
/*     */         {
/* 375 */           bufferedimage = TextureUtils.scaleImage(bufferedimage, this.width >> k);
/*     */         }
/*     */         
/* 378 */         if (k > 0 && (bufferedimage.getWidth() != i >> k || bufferedimage.getHeight() != j >> k))
/*     */         {
/* 380 */           throw new RuntimeException(String.format("Unable to load miplevel: %d, image is size: %dx%d, expected %dx%d", new Object[] { Integer.valueOf(k), Integer.valueOf(bufferedimage.getWidth()), Integer.valueOf(bufferedimage.getHeight()), Integer.valueOf(i >> k), Integer.valueOf(j >> k) }));
/*     */         }
/*     */         
/* 383 */         aint[k] = new int[bufferedimage.getWidth() * bufferedimage.getHeight()];
/* 384 */         bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), aint[k], 0, bufferedimage.getWidth());
/*     */       } 
/*     */     } 
/*     */     
/* 388 */     if (meta == null) {
/*     */       
/* 390 */       if (j != i)
/*     */       {
/* 392 */         throw new RuntimeException("broken aspect ratio and not an animation");
/*     */       }
/*     */       
/* 395 */       this.framesTextureData.add(aint);
/*     */     }
/*     */     else {
/*     */       
/* 399 */       int j1 = j / i;
/* 400 */       int l1 = i;
/* 401 */       int l = i;
/* 402 */       this.height = this.width;
/*     */       
/* 404 */       if (meta.getFrameCount() > 0) {
/*     */         
/* 406 */         Iterator<Integer> iterator = meta.getFrameIndexSet().iterator();
/*     */         
/* 408 */         while (iterator.hasNext()) {
/*     */           
/* 410 */           int i1 = ((Integer)iterator.next()).intValue();
/*     */           
/* 412 */           if (i1 >= j1)
/*     */           {
/* 414 */             throw new RuntimeException("invalid frameindex " + i1);
/*     */           }
/*     */           
/* 417 */           allocateFrameTextureData(i1);
/* 418 */           this.framesTextureData.set(i1, getFrameTextureData(aint, l1, l, i1));
/*     */         } 
/*     */         
/* 421 */         this.animationMetadata = meta;
/*     */       }
/*     */       else {
/*     */         
/* 425 */         List<AnimationFrame> list = Lists.newArrayList();
/*     */         
/* 427 */         for (int j2 = 0; j2 < j1; j2++) {
/*     */           
/* 429 */           this.framesTextureData.add(getFrameTextureData(aint, l1, l, j2));
/* 430 */           list.add(new AnimationFrame(j2, -1));
/*     */         } 
/*     */         
/* 433 */         this.animationMetadata = new AnimationMetadataSection(list, this.width, this.height, meta.getFrameTime(), meta.isInterpolate());
/*     */       } 
/*     */     } 
/*     */     
/* 437 */     if (!this.isShadersSprite) {
/*     */       
/* 439 */       if (Config.isShaders())
/*     */       {
/* 441 */         loadShadersSprites();
/*     */       }
/*     */       
/* 444 */       for (int k1 = 0; k1 < this.framesTextureData.size(); k1++) {
/*     */         
/* 446 */         int[][] aint1 = this.framesTextureData.get(k1);
/*     */         
/* 448 */         if (aint1 != null && !this.iconName.startsWith("minecraft:blocks/leaves_"))
/*     */         {
/* 450 */           for (int i2 = 0; i2 < aint1.length; i2++) {
/*     */             
/* 452 */             int[] aint2 = aint1[i2];
/* 453 */             fixTransparentColor(aint2);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 458 */       if (this.spriteSingle != null)
/*     */       {
/* 460 */         this.spriteSingle.loadSprite(images, meta);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void generateMipmaps(int level) {
/* 467 */     List<int[][]> list = Lists.newArrayList();
/*     */     
/* 469 */     for (int i = 0; i < this.framesTextureData.size(); i++) {
/*     */       
/* 471 */       final int[][] aint = this.framesTextureData.get(i);
/*     */       
/* 473 */       if (aint != null) {
/*     */         
/*     */         try {
/*     */           
/* 477 */           list.add(TextureUtil.generateMipmapData(level, this.width, aint));
/*     */         }
/* 479 */         catch (Throwable throwable) {
/*     */           
/* 481 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Generating mipmaps for frame");
/* 482 */           CrashReportCategory crashreportcategory = crashreport.makeCategory("Frame being iterated");
/* 483 */           crashreportcategory.addCrashSection("Frame index", Integer.valueOf(i));
/* 484 */           crashreportcategory.addCrashSectionCallable("Frame sizes", new Callable<String>()
/*     */               {
/*     */                 public String call() throws Exception
/*     */                 {
/* 488 */                   StringBuilder stringbuilder = new StringBuilder();
/*     */                   
/* 490 */                   for (int[] aint1 : aint) {
/*     */                     
/* 492 */                     if (stringbuilder.length() > 0)
/*     */                     {
/* 494 */                       stringbuilder.append(", ");
/*     */                     }
/*     */                     
/* 497 */                     stringbuilder.append((aint1 == null) ? "null" : Integer.valueOf(aint1.length));
/*     */                   } 
/*     */                   
/* 500 */                   return stringbuilder.toString();
/*     */                 }
/*     */               });
/* 503 */           throw new ReportedException(crashreport);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 508 */     setFramesTextureData(list);
/*     */     
/* 510 */     if (this.spriteSingle != null)
/*     */     {
/* 512 */       this.spriteSingle.generateMipmaps(level);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void allocateFrameTextureData(int index) {
/* 518 */     if (this.framesTextureData.size() <= index)
/*     */     {
/* 520 */       for (int i = this.framesTextureData.size(); i <= index; i++)
/*     */       {
/* 522 */         this.framesTextureData.add(null);
/*     */       }
/*     */     }
/*     */     
/* 526 */     if (this.spriteSingle != null)
/*     */     {
/* 528 */       this.spriteSingle.allocateFrameTextureData(index);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[][] getFrameTextureData(int[][] data, int rows, int columns, int p_147962_3_) {
/* 534 */     int[][] aint = new int[data.length][];
/*     */     
/* 536 */     for (int i = 0; i < data.length; i++) {
/*     */       
/* 538 */       int[] aint1 = data[i];
/*     */       
/* 540 */       if (aint1 != null) {
/*     */         
/* 542 */         aint[i] = new int[(rows >> i) * (columns >> i)];
/* 543 */         System.arraycopy(aint1, p_147962_3_ * (aint[i]).length, aint[i], 0, (aint[i]).length);
/*     */       } 
/*     */     } 
/*     */     
/* 547 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearFramesTextureData() {
/* 552 */     this.framesTextureData.clear();
/*     */     
/* 554 */     if (this.spriteSingle != null)
/*     */     {
/* 556 */       this.spriteSingle.clearFramesTextureData();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnimationMetadata() {
/* 562 */     return (this.animationMetadata != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFramesTextureData(List<int[][]> newFramesTextureData) {
/* 567 */     this.framesTextureData = newFramesTextureData;
/*     */     
/* 569 */     if (this.spriteSingle != null)
/*     */     {
/* 571 */       this.spriteSingle.setFramesTextureData(newFramesTextureData);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void resetSprite() {
/* 577 */     this.animationMetadata = null;
/* 578 */     setFramesTextureData(Lists.newArrayList());
/* 579 */     this.frameCounter = 0;
/* 580 */     this.tickCounter = 0;
/*     */     
/* 582 */     if (this.spriteSingle != null)
/*     */     {
/* 584 */       this.spriteSingle.resetSprite();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 590 */     return "TextureAtlasSprite{name='" + this.iconName + '\'' + ", frameCount=" + this.framesTextureData.size() + ", rotated=" + this.rotated + ", x=" + this.originX + ", y=" + this.originY + ", height=" + this.height + ", width=" + this.width + ", u0=" + this.minU + ", u1=" + this.maxU + ", v0=" + this.minV + ", v1=" + this.maxV + '}';
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasCustomLoader(IResourceManager p_hasCustomLoader_1_, ResourceLocation p_hasCustomLoader_2_) {
/* 595 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean load(IResourceManager p_load_1_, ResourceLocation p_load_2_) {
/* 600 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIndexInMap() {
/* 605 */     return this.indexInMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIndexInMap(int p_setIndexInMap_1_) {
/* 610 */     this.indexInMap = p_setIndexInMap_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateIndexInMap(CounterInt p_updateIndexInMap_1_) {
/* 615 */     if (this.indexInMap < 0)
/*     */     {
/* 617 */       this.indexInMap = p_updateIndexInMap_1_.nextValue();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnimationIndex() {
/* 623 */     return this.animationIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAnimationIndex(int p_setAnimationIndex_1_) {
/* 628 */     this.animationIndex = p_setAnimationIndex_1_;
/*     */     
/* 630 */     if (this.spriteNormal != null)
/*     */     {
/* 632 */       this.spriteNormal.setAnimationIndex(p_setAnimationIndex_1_);
/*     */     }
/*     */     
/* 635 */     if (this.spriteSpecular != null)
/*     */     {
/* 637 */       this.spriteSpecular.setAnimationIndex(p_setAnimationIndex_1_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAnimationActive() {
/* 643 */     return this.animationActive;
/*     */   }
/*     */ 
/*     */   
/*     */   private void fixTransparentColor(int[] p_fixTransparentColor_1_) {
/* 648 */     if (p_fixTransparentColor_1_ != null) {
/*     */       
/* 650 */       long i = 0L;
/* 651 */       long j = 0L;
/* 652 */       long k = 0L;
/* 653 */       long l = 0L;
/*     */       
/* 655 */       for (int i1 = 0; i1 < p_fixTransparentColor_1_.length; i1++) {
/*     */         
/* 657 */         int j1 = p_fixTransparentColor_1_[i1];
/* 658 */         int k1 = j1 >> 24 & 0xFF;
/*     */         
/* 660 */         if (k1 >= 16) {
/*     */           
/* 662 */           int l1 = j1 >> 16 & 0xFF;
/* 663 */           int i2 = j1 >> 8 & 0xFF;
/* 664 */           int j2 = j1 & 0xFF;
/* 665 */           i += l1;
/* 666 */           j += i2;
/* 667 */           k += j2;
/* 668 */           l++;
/*     */         } 
/*     */       } 
/*     */       
/* 672 */       if (l > 0L) {
/*     */         
/* 674 */         int l2 = (int)(i / l);
/* 675 */         int i3 = (int)(j / l);
/* 676 */         int j3 = (int)(k / l);
/* 677 */         int k3 = l2 << 16 | i3 << 8 | j3;
/*     */         
/* 679 */         for (int l3 = 0; l3 < p_fixTransparentColor_1_.length; l3++) {
/*     */           
/* 681 */           int i4 = p_fixTransparentColor_1_[l3];
/* 682 */           int k2 = i4 >> 24 & 0xFF;
/*     */           
/* 684 */           if (k2 <= 16)
/*     */           {
/* 686 */             p_fixTransparentColor_1_[l3] = k3;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double getSpriteU16(float p_getSpriteU16_1_) {
/* 695 */     float f = this.maxU - this.minU;
/* 696 */     return ((p_getSpriteU16_1_ - this.minU) / f * 16.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getSpriteV16(float p_getSpriteV16_1_) {
/* 701 */     float f = this.maxV - this.minV;
/* 702 */     return ((p_getSpriteV16_1_ - this.minV) / f * 16.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindSpriteTexture() {
/* 707 */     if (this.glSpriteTextureId < 0) {
/*     */       
/* 709 */       this.glSpriteTextureId = TextureUtil.glGenTextures();
/* 710 */       TextureUtil.allocateTextureImpl(this.glSpriteTextureId, this.mipmapLevels, this.width, this.height);
/* 711 */       TextureUtils.applyAnisotropicLevel();
/*     */     } 
/*     */     
/* 714 */     TextureUtils.bindTexture(this.glSpriteTextureId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteSpriteTexture() {
/* 719 */     if (this.glSpriteTextureId >= 0) {
/*     */       
/* 721 */       TextureUtil.deleteTexture(this.glSpriteTextureId);
/* 722 */       this.glSpriteTextureId = -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float toSingleU(float p_toSingleU_1_) {
/* 728 */     p_toSingleU_1_ -= this.baseU;
/* 729 */     float f = this.sheetWidth / this.width;
/* 730 */     p_toSingleU_1_ *= f;
/* 731 */     return p_toSingleU_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public float toSingleV(float p_toSingleV_1_) {
/* 736 */     p_toSingleV_1_ -= this.baseV;
/* 737 */     float f = this.sheetHeight / this.height;
/* 738 */     p_toSingleV_1_ *= f;
/* 739 */     return p_toSingleV_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<int[][]> getFramesTextureData() {
/* 744 */     List<int[][]> list = (List)new ArrayList<>();
/* 745 */     list.addAll(this.framesTextureData);
/* 746 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnimationMetadataSection getAnimationMetadata() {
/* 751 */     return this.animationMetadata;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAnimationMetadata(AnimationMetadataSection p_setAnimationMetadata_1_) {
/* 756 */     this.animationMetadata = p_setAnimationMetadata_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadShadersSprites() {
/* 761 */     if (Shaders.configNormalMap) {
/*     */       
/* 763 */       String s = this.iconName + "_n";
/* 764 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/* 765 */       resourcelocation = Config.getTextureMap().completeResourceLocation(resourcelocation);
/*     */       
/* 767 */       if (Config.hasResource(resourcelocation)) {
/*     */         
/* 769 */         this.spriteNormal = new TextureAtlasSprite(s);
/* 770 */         this.spriteNormal.isShadersSprite = true;
/* 771 */         this.spriteNormal.copyFrom(this);
/* 772 */         this.spriteNormal.generateMipmaps(this.mipmapLevels);
/*     */       } 
/*     */     } 
/*     */     
/* 776 */     if (Shaders.configSpecularMap) {
/*     */       
/* 778 */       String s1 = this.iconName + "_s";
/* 779 */       ResourceLocation resourcelocation1 = new ResourceLocation(s1);
/* 780 */       resourcelocation1 = Config.getTextureMap().completeResourceLocation(resourcelocation1);
/*     */       
/* 782 */       if (Config.hasResource(resourcelocation1)) {
/*     */         
/* 784 */         this.spriteSpecular = new TextureAtlasSprite(s1);
/* 785 */         this.spriteSpecular.isShadersSprite = true;
/* 786 */         this.spriteSpecular.copyFrom(this);
/* 787 */         this.spriteSpecular.generateMipmaps(this.mipmapLevels);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\texture\TextureAtlasSprite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
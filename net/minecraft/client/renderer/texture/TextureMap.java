/*      */ package net.minecraft.client.renderer.texture;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import java.util.concurrent.Callable;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.StitcherException;
/*      */ import net.minecraft.client.resources.IResource;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*      */ import net.minecraft.client.resources.data.TextureMetadataSection;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.optifine.BetterGrass;
/*      */ import net.optifine.ConnectedTextures;
/*      */ import net.optifine.CustomItems;
/*      */ import net.optifine.EmissiveTextures;
/*      */ import net.optifine.SmartAnimations;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.reflect.ReflectorForge;
/*      */ import net.optifine.shaders.ShadersTex;
/*      */ import net.optifine.util.CounterInt;
/*      */ import net.optifine.util.TextureUtils;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public class TextureMap
/*      */   extends AbstractTexture
/*      */   implements ITickableTextureObject {
/*   44 */   private static final boolean ENABLE_SKIP = Boolean.parseBoolean(System.getProperty("fml.skipFirstTextureLoad", "true"));
/*   45 */   private static final Logger logger = LogManager.getLogger();
/*   46 */   public static final ResourceLocation LOCATION_MISSING_TEXTURE = new ResourceLocation("missingno");
/*   47 */   public static final ResourceLocation locationBlocksTexture = new ResourceLocation("textures/atlas/blocks.png");
/*      */   
/*      */   private final List<TextureAtlasSprite> listAnimatedSprites;
/*      */   private final Map<String, TextureAtlasSprite> mapRegisteredSprites;
/*      */   private final Map<String, TextureAtlasSprite> mapUploadedSprites;
/*      */   private final String basePath;
/*      */   private final IIconCreator iconCreator;
/*      */   private int mipmapLevels;
/*      */   private final TextureAtlasSprite missingImage;
/*      */   private boolean skipFirst;
/*      */   private TextureAtlasSprite[] iconGrid;
/*      */   private int iconGridSize;
/*      */   private int iconGridCountX;
/*      */   private int iconGridCountY;
/*      */   private double iconGridSizeU;
/*      */   private double iconGridSizeV;
/*      */   private CounterInt counterIndexInMap;
/*      */   public int atlasWidth;
/*      */   public int atlasHeight;
/*      */   private int countAnimationsActive;
/*      */   private int frameCountAnimations;
/*      */   
/*      */   public TextureMap(String p_i46099_1_) {
/*   70 */     this(p_i46099_1_, (IIconCreator)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public TextureMap(String p_i5_1_, boolean p_i5_2_) {
/*   75 */     this(p_i5_1_, (IIconCreator)null, p_i5_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public TextureMap(String p_i46100_1_, IIconCreator iconCreatorIn) {
/*   80 */     this(p_i46100_1_, iconCreatorIn, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public TextureMap(String p_i6_1_, IIconCreator p_i6_2_, boolean p_i6_3_) {
/*   85 */     this.skipFirst = false;
/*   86 */     this.iconGrid = null;
/*   87 */     this.iconGridSize = -1;
/*   88 */     this.iconGridCountX = -1;
/*   89 */     this.iconGridCountY = -1;
/*   90 */     this.iconGridSizeU = -1.0D;
/*   91 */     this.iconGridSizeV = -1.0D;
/*   92 */     this.counterIndexInMap = new CounterInt(0);
/*   93 */     this.atlasWidth = 0;
/*   94 */     this.atlasHeight = 0;
/*   95 */     this.listAnimatedSprites = Lists.newArrayList();
/*   96 */     this.mapRegisteredSprites = Maps.newHashMap();
/*   97 */     this.mapUploadedSprites = Maps.newHashMap();
/*   98 */     this.missingImage = new TextureAtlasSprite("missingno");
/*   99 */     this.basePath = p_i6_1_;
/*  100 */     this.iconCreator = p_i6_2_;
/*  101 */     this.skipFirst = (p_i6_3_ && ENABLE_SKIP);
/*      */   }
/*      */ 
/*      */   
/*      */   private void initMissingImage() {
/*  106 */     int i = getMinSpriteSize();
/*  107 */     int[] aint = getMissingImageData(i);
/*  108 */     this.missingImage.setIconWidth(i);
/*  109 */     this.missingImage.setIconHeight(i);
/*  110 */     int[][] aint1 = new int[this.mipmapLevels + 1][];
/*  111 */     aint1[0] = aint;
/*  112 */     this.missingImage.setFramesTextureData(Lists.newArrayList((Object[])new int[][][] { aint1 }));
/*  113 */     this.missingImage.setIndexInMap(this.counterIndexInMap.nextValue());
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadTexture(IResourceManager resourceManager) throws IOException {
/*  118 */     ShadersTex.resManager = resourceManager;
/*      */     
/*  120 */     if (this.iconCreator != null)
/*      */     {
/*  122 */       loadSprites(resourceManager, this.iconCreator);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadSprites(IResourceManager resourceManager, IIconCreator p_174943_2_) {
/*  128 */     this.mapRegisteredSprites.clear();
/*  129 */     this.counterIndexInMap.reset();
/*  130 */     p_174943_2_.registerSprites(this);
/*      */     
/*  132 */     if (this.mipmapLevels >= 4) {
/*      */       
/*  134 */       this.mipmapLevels = detectMaxMipmapLevel(this.mapRegisteredSprites, resourceManager);
/*  135 */       Config.log("Mipmap levels: " + this.mipmapLevels);
/*      */     } 
/*      */     
/*  138 */     initMissingImage();
/*  139 */     deleteGlTexture();
/*  140 */     loadTextureAtlas(resourceManager);
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadTextureAtlas(IResourceManager resourceManager) {
/*  145 */     ShadersTex.resManager = resourceManager;
/*  146 */     Config.dbg("Multitexture: " + Config.isMultiTexture());
/*      */     
/*  148 */     if (Config.isMultiTexture())
/*      */     {
/*  150 */       for (TextureAtlasSprite textureatlassprite : this.mapUploadedSprites.values())
/*      */       {
/*  152 */         textureatlassprite.deleteSpriteTexture();
/*      */       }
/*      */     }
/*      */     
/*  156 */     ConnectedTextures.updateIcons(this);
/*  157 */     CustomItems.updateIcons(this);
/*  158 */     BetterGrass.updateIcons(this);
/*  159 */     int i2 = TextureUtils.getGLMaximumTextureSize();
/*  160 */     Stitcher stitcher = new Stitcher(i2, i2, true, 0, this.mipmapLevels);
/*  161 */     this.mapUploadedSprites.clear();
/*  162 */     this.listAnimatedSprites.clear();
/*  163 */     int i = Integer.MAX_VALUE;
/*  164 */     Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPre, new Object[] { this });
/*  165 */     int j = getMinSpriteSize();
/*  166 */     this.iconGridSize = j;
/*  167 */     int k = 1 << this.mipmapLevels;
/*  168 */     int l = 0;
/*  169 */     int i1 = 0;
/*  170 */     Iterator<Map.Entry<String, TextureAtlasSprite>> iterator = this.mapRegisteredSprites.entrySet().iterator();
/*      */ 
/*      */ 
/*      */     
/*  174 */     while (iterator.hasNext()) {
/*      */       
/*  176 */       Map.Entry<String, TextureAtlasSprite> entry = iterator.next();
/*      */       
/*  178 */       if (!this.skipFirst) {
/*      */         
/*  180 */         TextureAtlasSprite textureatlassprite3 = entry.getValue();
/*  181 */         ResourceLocation resourcelocation1 = new ResourceLocation(textureatlassprite3.getIconName());
/*  182 */         ResourceLocation resourcelocation2 = completeResourceLocation(resourcelocation1, 0);
/*  183 */         textureatlassprite3.updateIndexInMap(this.counterIndexInMap);
/*      */         
/*  185 */         if (textureatlassprite3.hasCustomLoader(resourceManager, resourcelocation1)) {
/*      */           
/*  187 */           if (!textureatlassprite3.load(resourceManager, resourcelocation1)) {
/*      */             
/*  189 */             i = Math.min(i, Math.min(textureatlassprite3.getIconWidth(), textureatlassprite3.getIconHeight()));
/*  190 */             stitcher.addSprite(textureatlassprite3);
/*  191 */             Config.detail("Custom loader (skipped): " + textureatlassprite3);
/*  192 */             i1++;
/*      */           } 
/*      */           
/*  195 */           Config.detail("Custom loader: " + textureatlassprite3);
/*  196 */           l++;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*      */         try {
/*  202 */           IResource iresource = resourceManager.getResource(resourcelocation2);
/*  203 */           BufferedImage[] abufferedimage = new BufferedImage[1 + this.mipmapLevels];
/*  204 */           abufferedimage[0] = TextureUtil.readBufferedImage(iresource.getInputStream());
/*  205 */           int k3 = abufferedimage[0].getWidth();
/*  206 */           int l3 = abufferedimage[0].getHeight();
/*      */           
/*  208 */           if (k3 < 1 || l3 < 1) {
/*      */             
/*  210 */             Config.warn("Invalid sprite size: " + textureatlassprite3);
/*      */             
/*      */             continue;
/*      */           } 
/*  214 */           if (k3 < j || this.mipmapLevels > 0) {
/*      */             
/*  216 */             int i4 = (this.mipmapLevels > 0) ? TextureUtils.scaleToGrid(k3, j) : TextureUtils.scaleToMin(k3, j);
/*      */             
/*  218 */             if (i4 != k3) {
/*      */               
/*  220 */               if (!TextureUtils.isPowerOfTwo(k3)) {
/*      */                 
/*  222 */                 Config.log("Scaled non power of 2: " + textureatlassprite3.getIconName() + ", " + k3 + " -> " + i4);
/*      */               }
/*      */               else {
/*      */                 
/*  226 */                 Config.log("Scaled too small texture: " + textureatlassprite3.getIconName() + ", " + k3 + " -> " + i4);
/*      */               } 
/*      */               
/*  229 */               int j1 = l3 * i4 / k3;
/*  230 */               abufferedimage[0] = TextureUtils.scaleImage(abufferedimage[0], i4);
/*      */             } 
/*      */           } 
/*      */           
/*  234 */           TextureMetadataSection texturemetadatasection = (TextureMetadataSection)iresource.getMetadata("texture");
/*      */           
/*  236 */           if (texturemetadatasection != null) {
/*      */             
/*  238 */             List<Integer> list1 = texturemetadatasection.getListMipmaps();
/*      */             
/*  240 */             if (!list1.isEmpty()) {
/*      */               
/*  242 */               int k1 = abufferedimage[0].getWidth();
/*  243 */               int l1 = abufferedimage[0].getHeight();
/*      */               
/*  245 */               if (MathHelper.roundUpToPowerOfTwo(k1) != k1 || MathHelper.roundUpToPowerOfTwo(l1) != l1)
/*      */               {
/*  247 */                 throw new RuntimeException("Unable to load extra miplevels, source-texture is not power of two");
/*      */               }
/*      */             } 
/*      */             
/*  251 */             Iterator<Integer> iterator1 = list1.iterator();
/*      */             
/*  253 */             while (iterator1.hasNext()) {
/*      */               
/*  255 */               int j4 = ((Integer)iterator1.next()).intValue();
/*      */               
/*  257 */               if (j4 > 0 && j4 < abufferedimage.length - 1 && abufferedimage[j4] == null) {
/*      */                 
/*  259 */                 ResourceLocation resourcelocation = completeResourceLocation(resourcelocation1, j4);
/*      */ 
/*      */                 
/*      */                 try {
/*  263 */                   abufferedimage[j4] = TextureUtil.readBufferedImage(resourceManager.getResource(resourcelocation).getInputStream());
/*      */                 }
/*  265 */                 catch (IOException ioexception) {
/*      */                   
/*  267 */                   logger.error("Unable to load miplevel {} from: {}", new Object[] { Integer.valueOf(j4), resourcelocation, ioexception });
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/*  273 */           AnimationMetadataSection animationmetadatasection = (AnimationMetadataSection)iresource.getMetadata("animation");
/*  274 */           textureatlassprite3.loadSprite(abufferedimage, animationmetadatasection);
/*      */         }
/*  276 */         catch (RuntimeException runtimeexception) {
/*      */           
/*  278 */           logger.error("Unable to parse metadata from " + resourcelocation2, runtimeexception);
/*  279 */           ReflectorForge.FMLClientHandler_trackBrokenTexture(resourcelocation2, runtimeexception.getMessage());
/*      */           
/*      */           continue;
/*  282 */         } catch (IOException ioexception1) {
/*      */           
/*  284 */           logger.error("Using missing texture, unable to load " + resourcelocation2 + ", " + ioexception1.getClass().getName());
/*  285 */           ReflectorForge.FMLClientHandler_trackMissingTexture(resourcelocation2);
/*      */           
/*      */           continue;
/*      */         } 
/*  289 */         i = Math.min(i, Math.min(textureatlassprite3.getIconWidth(), textureatlassprite3.getIconHeight()));
/*  290 */         int j3 = Math.min(Integer.lowestOneBit(textureatlassprite3.getIconWidth()), Integer.lowestOneBit(textureatlassprite3.getIconHeight()));
/*      */         
/*  292 */         if (j3 < k) {
/*      */           
/*  294 */           logger.warn("Texture {} with size {}x{} limits mip level from {} to {}", new Object[] { resourcelocation2, Integer.valueOf(textureatlassprite3.getIconWidth()), Integer.valueOf(textureatlassprite3.getIconHeight()), Integer.valueOf(MathHelper.calculateLogBaseTwo(k)), Integer.valueOf(MathHelper.calculateLogBaseTwo(j3)) });
/*  295 */           k = j3;
/*      */         } 
/*      */         
/*  298 */         stitcher.addSprite(textureatlassprite3);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  303 */     if (l > 0)
/*      */     {
/*  305 */       Config.dbg("Custom loader sprites: " + l);
/*      */     }
/*      */     
/*  308 */     if (i1 > 0)
/*      */     {
/*  310 */       Config.dbg("Custom loader sprites (skipped): " + i1);
/*      */     }
/*      */     
/*  313 */     int j2 = Math.min(i, k);
/*  314 */     int k2 = MathHelper.calculateLogBaseTwo(j2);
/*      */     
/*  316 */     if (k2 < 0)
/*      */     {
/*  318 */       k2 = 0;
/*      */     }
/*      */     
/*  321 */     if (k2 < this.mipmapLevels) {
/*      */       
/*  323 */       logger.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", new Object[] { this.basePath, Integer.valueOf(this.mipmapLevels), Integer.valueOf(k2), Integer.valueOf(j2) });
/*  324 */       this.mipmapLevels = k2;
/*      */     } 
/*      */     
/*  327 */     for (TextureAtlasSprite textureatlassprite1 : this.mapRegisteredSprites.values()) {
/*      */       
/*  329 */       if (this.skipFirst) {
/*      */         break;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  336 */         textureatlassprite1.generateMipmaps(this.mipmapLevels);
/*      */       }
/*  338 */       catch (Throwable throwable1) {
/*      */         
/*  340 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Applying mipmap");
/*  341 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Sprite being mipmapped");
/*  342 */         crashreportcategory.addCrashSectionCallable("Sprite name", new Callable<String>()
/*      */             {
/*      */               public String call() throws Exception
/*      */               {
/*  346 */                 return textureatlassprite1.getIconName();
/*      */               }
/*      */             });
/*  349 */         crashreportcategory.addCrashSectionCallable("Sprite size", new Callable<String>()
/*      */             {
/*      */               public String call() throws Exception
/*      */               {
/*  353 */                 return textureatlassprite1.getIconWidth() + " x " + textureatlassprite1.getIconHeight();
/*      */               }
/*      */             });
/*  356 */         crashreportcategory.addCrashSectionCallable("Sprite frames", new Callable<String>()
/*      */             {
/*      */               public String call() throws Exception
/*      */               {
/*  360 */                 return textureatlassprite1.getFrameCount() + " frames";
/*      */               }
/*      */             });
/*  363 */         crashreportcategory.addCrashSection("Mipmap levels", Integer.valueOf(this.mipmapLevels));
/*  364 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */     } 
/*      */     
/*  368 */     this.missingImage.generateMipmaps(this.mipmapLevels);
/*  369 */     stitcher.addSprite(this.missingImage);
/*  370 */     this.skipFirst = false;
/*      */ 
/*      */     
/*      */     try {
/*  374 */       stitcher.doStitch();
/*      */     }
/*  376 */     catch (StitcherException stitcherexception) {
/*      */       
/*  378 */       throw stitcherexception;
/*      */     } 
/*      */     
/*  381 */     logger.info("Created: {}x{} {}-atlas", new Object[] { Integer.valueOf(stitcher.getCurrentWidth()), Integer.valueOf(stitcher.getCurrentHeight()), this.basePath });
/*      */     
/*  383 */     if (Config.isShaders()) {
/*      */       
/*  385 */       ShadersTex.allocateTextureMap(getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight(), stitcher, this);
/*      */     }
/*      */     else {
/*      */       
/*  389 */       TextureUtil.allocateTextureImpl(getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
/*      */     } 
/*      */     
/*  392 */     Map<String, TextureAtlasSprite> map = Maps.newHashMap(this.mapRegisteredSprites);
/*      */     
/*  394 */     for (TextureAtlasSprite textureatlassprite2 : stitcher.getStichSlots()) {
/*      */       
/*  396 */       if (Config.isShaders())
/*      */       {
/*  398 */         ShadersTex.setIconName(ShadersTex.setSprite(textureatlassprite2).getIconName());
/*      */       }
/*      */       
/*  401 */       String s = textureatlassprite2.getIconName();
/*  402 */       map.remove(s);
/*  403 */       this.mapUploadedSprites.put(s, textureatlassprite2);
/*      */ 
/*      */       
/*      */       try {
/*  407 */         if (Config.isShaders())
/*      */         {
/*  409 */           ShadersTex.uploadTexSubForLoadAtlas(textureatlassprite2.getFrameTextureData(0), textureatlassprite2.getIconWidth(), textureatlassprite2.getIconHeight(), textureatlassprite2.getOriginX(), textureatlassprite2.getOriginY(), false, false);
/*      */         }
/*      */         else
/*      */         {
/*  413 */           TextureUtil.uploadTextureMipmap(textureatlassprite2.getFrameTextureData(0), textureatlassprite2.getIconWidth(), textureatlassprite2.getIconHeight(), textureatlassprite2.getOriginX(), textureatlassprite2.getOriginY(), false, false);
/*      */         }
/*      */       
/*  416 */       } catch (Throwable throwable) {
/*      */         
/*  418 */         CrashReport crashreport1 = CrashReport.makeCrashReport(throwable, "Stitching texture atlas");
/*  419 */         CrashReportCategory crashreportcategory1 = crashreport1.makeCategory("Texture being stitched together");
/*  420 */         crashreportcategory1.addCrashSection("Atlas path", this.basePath);
/*  421 */         crashreportcategory1.addCrashSection("Sprite", textureatlassprite2);
/*  422 */         throw new ReportedException(crashreport1);
/*      */       } 
/*      */       
/*  425 */       if (textureatlassprite2.hasAnimationMetadata()) {
/*      */         
/*  427 */         textureatlassprite2.setAnimationIndex(this.listAnimatedSprites.size());
/*  428 */         this.listAnimatedSprites.add(textureatlassprite2);
/*      */       } 
/*      */     } 
/*      */     
/*  432 */     for (TextureAtlasSprite textureatlassprite4 : map.values())
/*      */     {
/*  434 */       textureatlassprite4.copyFrom(this.missingImage);
/*      */     }
/*      */     
/*  437 */     Config.log("Animated sprites: " + this.listAnimatedSprites.size());
/*      */     
/*  439 */     if (Config.isMultiTexture()) {
/*      */       
/*  441 */       int l2 = stitcher.getCurrentWidth();
/*  442 */       int i3 = stitcher.getCurrentHeight();
/*      */       
/*  444 */       for (TextureAtlasSprite textureatlassprite5 : stitcher.getStichSlots()) {
/*      */         
/*  446 */         textureatlassprite5.sheetWidth = l2;
/*  447 */         textureatlassprite5.sheetHeight = i3;
/*  448 */         textureatlassprite5.mipmapLevels = this.mipmapLevels;
/*  449 */         TextureAtlasSprite textureatlassprite6 = textureatlassprite5.spriteSingle;
/*      */         
/*  451 */         if (textureatlassprite6 != null) {
/*      */           
/*  453 */           if (textureatlassprite6.getIconWidth() <= 0) {
/*      */             
/*  455 */             textureatlassprite6.setIconWidth(textureatlassprite5.getIconWidth());
/*  456 */             textureatlassprite6.setIconHeight(textureatlassprite5.getIconHeight());
/*  457 */             textureatlassprite6.initSprite(textureatlassprite5.getIconWidth(), textureatlassprite5.getIconHeight(), 0, 0, false);
/*  458 */             textureatlassprite6.clearFramesTextureData();
/*  459 */             List<int[][]> list = textureatlassprite5.getFramesTextureData();
/*  460 */             textureatlassprite6.setFramesTextureData(list);
/*  461 */             textureatlassprite6.setAnimationMetadata(textureatlassprite5.getAnimationMetadata());
/*      */           } 
/*      */           
/*  464 */           textureatlassprite6.sheetWidth = l2;
/*  465 */           textureatlassprite6.sheetHeight = i3;
/*  466 */           textureatlassprite6.mipmapLevels = this.mipmapLevels;
/*  467 */           textureatlassprite6.setAnimationIndex(textureatlassprite5.getAnimationIndex());
/*  468 */           textureatlassprite5.bindSpriteTexture();
/*  469 */           boolean flag1 = false;
/*  470 */           boolean flag = true;
/*      */ 
/*      */           
/*      */           try {
/*  474 */             TextureUtil.uploadTextureMipmap(textureatlassprite6.getFrameTextureData(0), textureatlassprite6.getIconWidth(), textureatlassprite6.getIconHeight(), textureatlassprite6.getOriginX(), textureatlassprite6.getOriginY(), flag1, flag);
/*      */           }
/*  476 */           catch (Exception exception) {
/*      */             
/*  478 */             Config.dbg("Error uploading sprite single: " + textureatlassprite6 + ", parent: " + textureatlassprite5);
/*  479 */             exception.printStackTrace();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  484 */       Config.getMinecraft().getTextureManager().bindTexture(locationBlocksTexture);
/*      */     } 
/*      */     
/*  487 */     Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPost, new Object[] { this });
/*  488 */     updateIconGrid(stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
/*      */     
/*  490 */     if (Config.equals(System.getProperty("saveTextureMap"), "true")) {
/*      */       
/*  492 */       Config.dbg("Exporting texture map: " + this.basePath);
/*  493 */       TextureUtils.saveGlTexture("debug/" + this.basePath.replaceAll("/", "_"), getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResourceLocation completeResourceLocation(ResourceLocation p_completeResourceLocation_1_) {
/*  502 */     return completeResourceLocation(p_completeResourceLocation_1_, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public ResourceLocation completeResourceLocation(ResourceLocation location, int p_147634_2_) {
/*  507 */     return isAbsoluteLocation(location) ? new ResourceLocation(location.getResourceDomain(), location.getResourcePath() + ".png") : ((p_147634_2_ == 0) ? new ResourceLocation(location.getResourceDomain(), String.format("%s/%s%s", new Object[] { this.basePath, location.getResourcePath(), ".png" })) : new ResourceLocation(location.getResourceDomain(), String.format("%s/mipmaps/%s.%d%s", new Object[] { this.basePath, location.getResourcePath(), Integer.valueOf(p_147634_2_), ".png" })));
/*      */   }
/*      */ 
/*      */   
/*      */   public TextureAtlasSprite getAtlasSprite(String iconName) {
/*  512 */     TextureAtlasSprite textureatlassprite = this.mapUploadedSprites.get(iconName);
/*      */     
/*  514 */     if (textureatlassprite == null)
/*      */     {
/*  516 */       textureatlassprite = this.missingImage;
/*      */     }
/*      */     
/*  519 */     return textureatlassprite;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateAnimations() {
/*  524 */     if (Config.isShaders())
/*      */     {
/*  526 */       ShadersTex.updatingTex = getMultiTexID();
/*      */     }
/*      */     
/*  529 */     boolean flag = false;
/*  530 */     boolean flag1 = false;
/*  531 */     TextureUtil.bindTexture(getGlTextureId());
/*  532 */     int i = 0;
/*      */     
/*  534 */     for (TextureAtlasSprite textureatlassprite : this.listAnimatedSprites) {
/*      */       
/*  536 */       if (isTerrainAnimationActive(textureatlassprite)) {
/*      */         
/*  538 */         textureatlassprite.updateAnimation();
/*      */         
/*  540 */         if (textureatlassprite.isAnimationActive())
/*      */         {
/*  542 */           i++;
/*      */         }
/*      */         
/*  545 */         if (textureatlassprite.spriteNormal != null)
/*      */         {
/*  547 */           flag = true;
/*      */         }
/*      */         
/*  550 */         if (textureatlassprite.spriteSpecular != null)
/*      */         {
/*  552 */           flag1 = true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  557 */     if (Config.isMultiTexture()) {
/*      */       
/*  559 */       for (TextureAtlasSprite textureatlassprite2 : this.listAnimatedSprites) {
/*      */         
/*  561 */         if (isTerrainAnimationActive(textureatlassprite2)) {
/*      */           
/*  563 */           TextureAtlasSprite textureatlassprite1 = textureatlassprite2.spriteSingle;
/*      */           
/*  565 */           if (textureatlassprite1 != null) {
/*      */             
/*  567 */             if (textureatlassprite2 == TextureUtils.iconClock || textureatlassprite2 == TextureUtils.iconCompass)
/*      */             {
/*  569 */               textureatlassprite1.frameCounter = textureatlassprite2.frameCounter;
/*      */             }
/*      */             
/*  572 */             textureatlassprite2.bindSpriteTexture();
/*  573 */             textureatlassprite1.updateAnimation();
/*      */             
/*  575 */             if (textureatlassprite1.isAnimationActive())
/*      */             {
/*  577 */               i++;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  583 */       TextureUtil.bindTexture(getGlTextureId());
/*      */     } 
/*      */     
/*  586 */     if (Config.isShaders()) {
/*      */       
/*  588 */       if (flag) {
/*      */         
/*  590 */         TextureUtil.bindTexture((getMultiTexID()).norm);
/*      */         
/*  592 */         for (TextureAtlasSprite textureatlassprite3 : this.listAnimatedSprites) {
/*      */           
/*  594 */           if (textureatlassprite3.spriteNormal != null && isTerrainAnimationActive(textureatlassprite3)) {
/*      */             
/*  596 */             if (textureatlassprite3 == TextureUtils.iconClock || textureatlassprite3 == TextureUtils.iconCompass)
/*      */             {
/*  598 */               textureatlassprite3.spriteNormal.frameCounter = textureatlassprite3.frameCounter;
/*      */             }
/*      */             
/*  601 */             textureatlassprite3.spriteNormal.updateAnimation();
/*      */             
/*  603 */             if (textureatlassprite3.spriteNormal.isAnimationActive())
/*      */             {
/*  605 */               i++;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  611 */       if (flag1) {
/*      */         
/*  613 */         TextureUtil.bindTexture((getMultiTexID()).spec);
/*      */         
/*  615 */         for (TextureAtlasSprite textureatlassprite4 : this.listAnimatedSprites) {
/*      */           
/*  617 */           if (textureatlassprite4.spriteSpecular != null && isTerrainAnimationActive(textureatlassprite4)) {
/*      */             
/*  619 */             if (textureatlassprite4 == TextureUtils.iconClock || textureatlassprite4 == TextureUtils.iconCompass)
/*      */             {
/*  621 */               textureatlassprite4.spriteNormal.frameCounter = textureatlassprite4.frameCounter;
/*      */             }
/*      */             
/*  624 */             textureatlassprite4.spriteSpecular.updateAnimation();
/*      */             
/*  626 */             if (textureatlassprite4.spriteSpecular.isAnimationActive())
/*      */             {
/*  628 */               i++;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  634 */       if (flag || flag1)
/*      */       {
/*  636 */         TextureUtil.bindTexture(getGlTextureId());
/*      */       }
/*      */     } 
/*      */     
/*  640 */     int j = (Config.getMinecraft()).entityRenderer.frameCount;
/*      */     
/*  642 */     if (j != this.frameCountAnimations) {
/*      */       
/*  644 */       this.countAnimationsActive = i;
/*  645 */       this.frameCountAnimations = j;
/*      */     } 
/*      */     
/*  648 */     if (SmartAnimations.isActive())
/*      */     {
/*  650 */       SmartAnimations.resetSpritesRendered();
/*      */     }
/*      */     
/*  653 */     if (Config.isShaders())
/*      */     {
/*  655 */       ShadersTex.updatingTex = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public TextureAtlasSprite registerSprite(ResourceLocation location) {
/*  661 */     if (location == null)
/*      */     {
/*  663 */       throw new IllegalArgumentException("Location cannot be null!");
/*      */     }
/*      */ 
/*      */     
/*  667 */     TextureAtlasSprite textureatlassprite = this.mapRegisteredSprites.get(location.toString());
/*      */     
/*  669 */     if (textureatlassprite == null) {
/*      */       
/*  671 */       textureatlassprite = TextureAtlasSprite.makeAtlasSprite(location);
/*  672 */       this.mapRegisteredSprites.put(location.toString(), textureatlassprite);
/*  673 */       textureatlassprite.updateIndexInMap(this.counterIndexInMap);
/*      */       
/*  675 */       if (Config.isEmissiveTextures())
/*      */       {
/*  677 */         checkEmissive(location, textureatlassprite);
/*      */       }
/*      */     } 
/*      */     
/*  681 */     return textureatlassprite;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void tick() {
/*  687 */     updateAnimations();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMipmapLevels(int mipmapLevelsIn) {
/*  692 */     this.mipmapLevels = mipmapLevelsIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public TextureAtlasSprite getMissingSprite() {
/*  697 */     return this.missingImage;
/*      */   }
/*      */ 
/*      */   
/*      */   public TextureAtlasSprite getTextureExtry(String p_getTextureExtry_1_) {
/*  702 */     return this.mapRegisteredSprites.get(p_getTextureExtry_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean setTextureEntry(String p_setTextureEntry_1_, TextureAtlasSprite p_setTextureEntry_2_) {
/*  707 */     if (!this.mapRegisteredSprites.containsKey(p_setTextureEntry_1_)) {
/*      */       
/*  709 */       this.mapRegisteredSprites.put(p_setTextureEntry_1_, p_setTextureEntry_2_);
/*  710 */       p_setTextureEntry_2_.updateIndexInMap(this.counterIndexInMap);
/*  711 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  715 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setTextureEntry(TextureAtlasSprite p_setTextureEntry_1_) {
/*  721 */     return setTextureEntry(p_setTextureEntry_1_.getIconName(), p_setTextureEntry_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getBasePath() {
/*  726 */     return this.basePath;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMipmapLevels() {
/*  731 */     return this.mipmapLevels;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isAbsoluteLocation(ResourceLocation p_isAbsoluteLocation_1_) {
/*  736 */     String s = p_isAbsoluteLocation_1_.getResourcePath();
/*  737 */     return isAbsoluteLocationPath(s);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isAbsoluteLocationPath(String p_isAbsoluteLocationPath_1_) {
/*  742 */     String s = p_isAbsoluteLocationPath_1_.toLowerCase();
/*  743 */     return (s.startsWith("mcpatcher/") || s.startsWith("optifine/"));
/*      */   }
/*      */ 
/*      */   
/*      */   public TextureAtlasSprite getSpriteSafe(String p_getSpriteSafe_1_) {
/*  748 */     ResourceLocation resourcelocation = new ResourceLocation(p_getSpriteSafe_1_);
/*  749 */     return this.mapRegisteredSprites.get(resourcelocation.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public TextureAtlasSprite getRegisteredSprite(ResourceLocation p_getRegisteredSprite_1_) {
/*  754 */     return this.mapRegisteredSprites.get(p_getRegisteredSprite_1_.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isTerrainAnimationActive(TextureAtlasSprite p_isTerrainAnimationActive_1_) {
/*  759 */     return (p_isTerrainAnimationActive_1_ != TextureUtils.iconWaterStill && p_isTerrainAnimationActive_1_ != TextureUtils.iconWaterFlow) ? ((p_isTerrainAnimationActive_1_ != TextureUtils.iconLavaStill && p_isTerrainAnimationActive_1_ != TextureUtils.iconLavaFlow) ? ((p_isTerrainAnimationActive_1_ != TextureUtils.iconFireLayer0 && p_isTerrainAnimationActive_1_ != TextureUtils.iconFireLayer1) ? ((p_isTerrainAnimationActive_1_ == TextureUtils.iconPortal) ? Config.isAnimatedPortal() : ((p_isTerrainAnimationActive_1_ != TextureUtils.iconClock && p_isTerrainAnimationActive_1_ != TextureUtils.iconCompass) ? Config.isAnimatedTerrain() : true)) : Config.isAnimatedFire()) : Config.isAnimatedLava()) : Config.isAnimatedWater();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountRegisteredSprites() {
/*  764 */     return this.counterIndexInMap.getValue();
/*      */   }
/*      */ 
/*      */   
/*      */   private int detectMaxMipmapLevel(Map p_detectMaxMipmapLevel_1_, IResourceManager p_detectMaxMipmapLevel_2_) {
/*  769 */     int i = detectMinimumSpriteSize(p_detectMaxMipmapLevel_1_, p_detectMaxMipmapLevel_2_, 20);
/*      */     
/*  771 */     if (i < 16)
/*      */     {
/*  773 */       i = 16;
/*      */     }
/*      */     
/*  776 */     i = MathHelper.roundUpToPowerOfTwo(i);
/*      */     
/*  778 */     if (i > 16)
/*      */     {
/*  780 */       Config.log("Sprite size: " + i);
/*      */     }
/*      */     
/*  783 */     int j = MathHelper.calculateLogBaseTwo(i);
/*      */     
/*  785 */     if (j < 4)
/*      */     {
/*  787 */       j = 4;
/*      */     }
/*      */     
/*  790 */     return j;
/*      */   }
/*      */ 
/*      */   
/*      */   private int detectMinimumSpriteSize(Map p_detectMinimumSpriteSize_1_, IResourceManager p_detectMinimumSpriteSize_2_, int p_detectMinimumSpriteSize_3_) {
/*  795 */     Map<Object, Object> map = new HashMap<>();
/*      */     
/*  797 */     for (Object e : p_detectMinimumSpriteSize_1_.entrySet()) {
/*      */       
/*  799 */       Map.Entry entry = (Map.Entry)e;
/*  800 */       TextureAtlasSprite textureatlassprite = (TextureAtlasSprite)entry.getValue();
/*  801 */       ResourceLocation resourcelocation = new ResourceLocation(textureatlassprite.getIconName());
/*  802 */       ResourceLocation resourcelocation1 = completeResourceLocation(resourcelocation);
/*      */       
/*  804 */       if (!textureatlassprite.hasCustomLoader(p_detectMinimumSpriteSize_2_, resourcelocation)) {
/*      */         
/*      */         try {
/*      */           
/*  808 */           IResource iresource = p_detectMinimumSpriteSize_2_.getResource(resourcelocation1);
/*      */           
/*  810 */           if (iresource != null) {
/*      */             
/*  812 */             InputStream inputstream = iresource.getInputStream();
/*      */             
/*  814 */             if (inputstream != null)
/*      */             {
/*  816 */               Dimension dimension = TextureUtils.getImageSize(inputstream, "png");
/*      */               
/*  818 */               if (dimension != null)
/*      */               {
/*  820 */                 int i = dimension.width;
/*  821 */                 int j = MathHelper.roundUpToPowerOfTwo(i);
/*      */                 
/*  823 */                 if (!map.containsKey(Integer.valueOf(j))) {
/*      */                   
/*  825 */                   map.put(Integer.valueOf(j), Integer.valueOf(1));
/*      */                   
/*      */                   continue;
/*      */                 } 
/*  829 */                 int k = ((Integer)map.get(Integer.valueOf(j))).intValue();
/*  830 */                 map.put(Integer.valueOf(j), Integer.valueOf(k + 1));
/*      */               }
/*      */             
/*      */             }
/*      */           
/*      */           } 
/*  836 */         } catch (Exception exception) {}
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  843 */     int l = 0;
/*  844 */     Set<?> set = map.keySet();
/*  845 */     Set set1 = new TreeSet(set);
/*      */ 
/*      */     
/*  848 */     for (Iterator<Integer> iterator = set1.iterator(); iterator.hasNext(); l += i) {
/*      */       
/*  850 */       int j1 = ((Integer)iterator.next()).intValue();
/*  851 */       int i = ((Integer)map.get(Integer.valueOf(j1))).intValue();
/*      */     } 
/*      */     
/*  854 */     int i1 = 16;
/*  855 */     int k1 = 0;
/*  856 */     int l1 = l * p_detectMinimumSpriteSize_3_ / 100;
/*  857 */     Iterator<Integer> iterator1 = set1.iterator();
/*      */     
/*  859 */     while (iterator1.hasNext()) {
/*      */       
/*  861 */       int i2 = ((Integer)iterator1.next()).intValue();
/*  862 */       int j2 = ((Integer)map.get(Integer.valueOf(i2))).intValue();
/*  863 */       k1 += j2;
/*      */       
/*  865 */       if (i2 > i1)
/*      */       {
/*  867 */         i1 = i2;
/*      */       }
/*      */       
/*  870 */       if (k1 > l1)
/*      */       {
/*  872 */         return i1;
/*      */       }
/*      */     } 
/*      */     
/*  876 */     return i1;
/*      */   }
/*      */ 
/*      */   
/*      */   private int getMinSpriteSize() {
/*  881 */     int i = 1 << this.mipmapLevels;
/*      */     
/*  883 */     if (i < 8)
/*      */     {
/*  885 */       i = 8;
/*      */     }
/*      */     
/*  888 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private int[] getMissingImageData(int p_getMissingImageData_1_) {
/*  893 */     BufferedImage bufferedimage = new BufferedImage(16, 16, 2);
/*  894 */     bufferedimage.setRGB(0, 0, 16, 16, TextureUtil.missingTextureData, 0, 16);
/*  895 */     BufferedImage bufferedimage1 = TextureUtils.scaleImage(bufferedimage, p_getMissingImageData_1_);
/*  896 */     int[] aint = new int[p_getMissingImageData_1_ * p_getMissingImageData_1_];
/*  897 */     bufferedimage1.getRGB(0, 0, p_getMissingImageData_1_, p_getMissingImageData_1_, aint, 0, p_getMissingImageData_1_);
/*  898 */     return aint;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isTextureBound() {
/*  903 */     int i = GlStateManager.getBoundTexture();
/*  904 */     int j = getGlTextureId();
/*  905 */     return (i == j);
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateIconGrid(int p_updateIconGrid_1_, int p_updateIconGrid_2_) {
/*  910 */     this.iconGridCountX = -1;
/*  911 */     this.iconGridCountY = -1;
/*  912 */     this.iconGrid = null;
/*      */     
/*  914 */     if (this.iconGridSize > 0) {
/*      */       
/*  916 */       this.iconGridCountX = p_updateIconGrid_1_ / this.iconGridSize;
/*  917 */       this.iconGridCountY = p_updateIconGrid_2_ / this.iconGridSize;
/*  918 */       this.iconGrid = new TextureAtlasSprite[this.iconGridCountX * this.iconGridCountY];
/*  919 */       this.iconGridSizeU = 1.0D / this.iconGridCountX;
/*  920 */       this.iconGridSizeV = 1.0D / this.iconGridCountY;
/*      */       
/*  922 */       for (TextureAtlasSprite textureatlassprite : this.mapUploadedSprites.values()) {
/*      */         
/*  924 */         double d0 = 0.5D / p_updateIconGrid_1_;
/*  925 */         double d1 = 0.5D / p_updateIconGrid_2_;
/*  926 */         double d2 = Math.min(textureatlassprite.getMinU(), textureatlassprite.getMaxU()) + d0;
/*  927 */         double d3 = Math.min(textureatlassprite.getMinV(), textureatlassprite.getMaxV()) + d1;
/*  928 */         double d4 = Math.max(textureatlassprite.getMinU(), textureatlassprite.getMaxU()) - d0;
/*  929 */         double d5 = Math.max(textureatlassprite.getMinV(), textureatlassprite.getMaxV()) - d1;
/*  930 */         int i = (int)(d2 / this.iconGridSizeU);
/*  931 */         int j = (int)(d3 / this.iconGridSizeV);
/*  932 */         int k = (int)(d4 / this.iconGridSizeU);
/*  933 */         int l = (int)(d5 / this.iconGridSizeV);
/*      */         
/*  935 */         for (int i1 = i; i1 <= k; i1++) {
/*      */           
/*  937 */           if (i1 >= 0 && i1 < this.iconGridCountX) {
/*      */             
/*  939 */             for (int j1 = j; j1 <= l; j1++) {
/*      */               
/*  941 */               if (j1 >= 0 && j1 < this.iconGridCountX)
/*      */               {
/*  943 */                 int k1 = j1 * this.iconGridCountX + i1;
/*  944 */                 this.iconGrid[k1] = textureatlassprite;
/*      */               }
/*      */               else
/*      */               {
/*  948 */                 Config.warn("Invalid grid V: " + j1 + ", icon: " + textureatlassprite.getIconName());
/*      */               }
/*      */             
/*      */             } 
/*      */           } else {
/*      */             
/*  954 */             Config.warn("Invalid grid U: " + i1 + ", icon: " + textureatlassprite.getIconName());
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public TextureAtlasSprite getIconByUV(double p_getIconByUV_1_, double p_getIconByUV_3_) {
/*  963 */     if (this.iconGrid == null)
/*      */     {
/*  965 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  969 */     int i = (int)(p_getIconByUV_1_ / this.iconGridSizeU);
/*  970 */     int j = (int)(p_getIconByUV_3_ / this.iconGridSizeV);
/*  971 */     int k = j * this.iconGridCountX + i;
/*  972 */     return (k >= 0 && k <= this.iconGrid.length) ? this.iconGrid[k] : null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkEmissive(ResourceLocation p_checkEmissive_1_, TextureAtlasSprite p_checkEmissive_2_) {
/*  978 */     String s = EmissiveTextures.getSuffixEmissive();
/*      */     
/*  980 */     if (s != null)
/*      */     {
/*  982 */       if (!p_checkEmissive_1_.getResourcePath().endsWith(s)) {
/*      */         
/*  984 */         ResourceLocation resourcelocation = new ResourceLocation(p_checkEmissive_1_.getResourceDomain(), p_checkEmissive_1_.getResourcePath() + s);
/*  985 */         ResourceLocation resourcelocation1 = completeResourceLocation(resourcelocation);
/*      */         
/*  987 */         if (Config.hasResource(resourcelocation1)) {
/*      */           
/*  989 */           TextureAtlasSprite textureatlassprite = registerSprite(resourcelocation);
/*  990 */           textureatlassprite.isEmissive = true;
/*  991 */           p_checkEmissive_2_.spriteEmissive = textureatlassprite;
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountAnimations() {
/*  999 */     return this.listAnimatedSprites.size();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountAnimationsActive() {
/* 1004 */     return this.countAnimationsActive;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\texture\TextureMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.imageio.ImageReader;
/*     */ import javax.imageio.stream.ImageInputStream;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerMooshroomMushroom;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.ITickableTextureObject;
/*     */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.IReloadableResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.BetterGrass;
/*     */ import net.optifine.BetterSnow;
/*     */ import net.optifine.CustomBlockLayers;
/*     */ import net.optifine.CustomColors;
/*     */ import net.optifine.CustomGuis;
/*     */ import net.optifine.CustomItems;
/*     */ import net.optifine.CustomLoadingScreens;
/*     */ import net.optifine.CustomPanorama;
/*     */ import net.optifine.CustomSky;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.NaturalTextures;
/*     */ import net.optifine.RandomEntities;
/*     */ import net.optifine.SmartLeaves;
/*     */ import net.optifine.TextureAnimations;
/*     */ import net.optifine.entity.model.CustomEntityModels;
/*     */ import net.optifine.shaders.MultiTexID;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextureUtils
/*     */ {
/*     */   public static final String texGrassTop = "grass_top";
/*     */   public static final String texStone = "stone";
/*     */   public static final String texDirt = "dirt";
/*     */   public static final String texCoarseDirt = "coarse_dirt";
/*     */   public static final String texGrassSide = "grass_side";
/*     */   public static final String texStoneslabSide = "stone_slab_side";
/*     */   public static final String texStoneslabTop = "stone_slab_top";
/*     */   public static final String texBedrock = "bedrock";
/*     */   public static final String texSand = "sand";
/*     */   public static final String texGravel = "gravel";
/*     */   public static final String texLogOak = "log_oak";
/*     */   public static final String texLogBigOak = "log_big_oak";
/*     */   public static final String texLogAcacia = "log_acacia";
/*     */   public static final String texLogSpruce = "log_spruce";
/*     */   public static final String texLogBirch = "log_birch";
/*     */   public static final String texLogJungle = "log_jungle";
/*     */   public static final String texLogOakTop = "log_oak_top";
/*     */   public static final String texLogBigOakTop = "log_big_oak_top";
/*     */   public static final String texLogAcaciaTop = "log_acacia_top";
/*     */   public static final String texLogSpruceTop = "log_spruce_top";
/*     */   public static final String texLogBirchTop = "log_birch_top";
/*     */   public static final String texLogJungleTop = "log_jungle_top";
/*     */   public static final String texLeavesOak = "leaves_oak";
/*     */   public static final String texLeavesBigOak = "leaves_big_oak";
/*     */   public static final String texLeavesAcacia = "leaves_acacia";
/*     */   public static final String texLeavesBirch = "leaves_birch";
/*     */   public static final String texLeavesSpuce = "leaves_spruce";
/*     */   public static final String texLeavesJungle = "leaves_jungle";
/*     */   public static final String texGoldOre = "gold_ore";
/*     */   public static final String texIronOre = "iron_ore";
/*     */   public static final String texCoalOre = "coal_ore";
/*     */   public static final String texObsidian = "obsidian";
/*     */   public static final String texGrassSideOverlay = "grass_side_overlay";
/*     */   public static final String texSnow = "snow";
/*     */   public static final String texGrassSideSnowed = "grass_side_snowed";
/*     */   public static final String texMyceliumSide = "mycelium_side";
/*     */   public static final String texMyceliumTop = "mycelium_top";
/*     */   public static final String texDiamondOre = "diamond_ore";
/*     */   public static final String texRedstoneOre = "redstone_ore";
/*     */   public static final String texLapisOre = "lapis_ore";
/*     */   public static final String texCactusSide = "cactus_side";
/*     */   public static final String texClay = "clay";
/*     */   public static final String texFarmlandWet = "farmland_wet";
/*     */   public static final String texFarmlandDry = "farmland_dry";
/*     */   public static final String texNetherrack = "netherrack";
/*     */   public static final String texSoulSand = "soul_sand";
/*     */   public static final String texGlowstone = "glowstone";
/*     */   public static final String texLeavesSpruce = "leaves_spruce";
/*     */   public static final String texLeavesSpruceOpaque = "leaves_spruce_opaque";
/*     */   public static final String texEndStone = "end_stone";
/*     */   public static final String texSandstoneTop = "sandstone_top";
/*     */   public static final String texSandstoneBottom = "sandstone_bottom";
/*     */   public static final String texRedstoneLampOff = "redstone_lamp_off";
/*     */   public static final String texRedstoneLampOn = "redstone_lamp_on";
/*     */   public static final String texWaterStill = "water_still";
/*     */   public static final String texWaterFlow = "water_flow";
/*     */   public static final String texLavaStill = "lava_still";
/*     */   public static final String texLavaFlow = "lava_flow";
/*     */   public static final String texFireLayer0 = "fire_layer_0";
/*     */   public static final String texFireLayer1 = "fire_layer_1";
/*     */   public static final String texPortal = "portal";
/*     */   public static final String texGlass = "glass";
/*     */   public static final String texGlassPaneTop = "glass_pane_top";
/*     */   public static final String texCompass = "compass";
/*     */   public static final String texClock = "clock";
/*     */   public static TextureAtlasSprite iconGrassTop;
/*     */   public static TextureAtlasSprite iconGrassSide;
/*     */   public static TextureAtlasSprite iconGrassSideOverlay;
/*     */   public static TextureAtlasSprite iconSnow;
/*     */   public static TextureAtlasSprite iconGrassSideSnowed;
/*     */   public static TextureAtlasSprite iconMyceliumSide;
/*     */   public static TextureAtlasSprite iconMyceliumTop;
/*     */   public static TextureAtlasSprite iconWaterStill;
/*     */   public static TextureAtlasSprite iconWaterFlow;
/*     */   public static TextureAtlasSprite iconLavaStill;
/*     */   public static TextureAtlasSprite iconLavaFlow;
/*     */   public static TextureAtlasSprite iconPortal;
/*     */   public static TextureAtlasSprite iconFireLayer0;
/*     */   public static TextureAtlasSprite iconFireLayer1;
/*     */   public static TextureAtlasSprite iconGlass;
/*     */   public static TextureAtlasSprite iconGlassPaneTop;
/*     */   public static TextureAtlasSprite iconCompass;
/*     */   public static TextureAtlasSprite iconClock;
/*     */   public static final String SPRITE_PREFIX_BLOCKS = "minecraft:blocks/";
/*     */   public static final String SPRITE_PREFIX_ITEMS = "minecraft:items/";
/* 142 */   private static IntBuffer staticBuffer = GLAllocation.createDirectIntBuffer(256);
/*     */ 
/*     */   
/*     */   public static void update() {
/* 146 */     TextureMap texturemap = getTextureMapBlocks();
/*     */     
/* 148 */     if (texturemap != null) {
/*     */       
/* 150 */       String s = "minecraft:blocks/";
/* 151 */       iconGrassTop = texturemap.getSpriteSafe(s + "grass_top");
/* 152 */       iconGrassSide = texturemap.getSpriteSafe(s + "grass_side");
/* 153 */       iconGrassSideOverlay = texturemap.getSpriteSafe(s + "grass_side_overlay");
/* 154 */       iconSnow = texturemap.getSpriteSafe(s + "snow");
/* 155 */       iconGrassSideSnowed = texturemap.getSpriteSafe(s + "grass_side_snowed");
/* 156 */       iconMyceliumSide = texturemap.getSpriteSafe(s + "mycelium_side");
/* 157 */       iconMyceliumTop = texturemap.getSpriteSafe(s + "mycelium_top");
/* 158 */       iconWaterStill = texturemap.getSpriteSafe(s + "water_still");
/* 159 */       iconWaterFlow = texturemap.getSpriteSafe(s + "water_flow");
/* 160 */       iconLavaStill = texturemap.getSpriteSafe(s + "lava_still");
/* 161 */       iconLavaFlow = texturemap.getSpriteSafe(s + "lava_flow");
/* 162 */       iconFireLayer0 = texturemap.getSpriteSafe(s + "fire_layer_0");
/* 163 */       iconFireLayer1 = texturemap.getSpriteSafe(s + "fire_layer_1");
/* 164 */       iconPortal = texturemap.getSpriteSafe(s + "portal");
/* 165 */       iconGlass = texturemap.getSpriteSafe(s + "glass");
/* 166 */       iconGlassPaneTop = texturemap.getSpriteSafe(s + "glass_pane_top");
/* 167 */       String s1 = "minecraft:items/";
/* 168 */       iconCompass = texturemap.getSpriteSafe(s1 + "compass");
/* 169 */       iconClock = texturemap.getSpriteSafe(s1 + "clock");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static BufferedImage fixTextureDimensions(String name, BufferedImage bi) {
/* 175 */     if (name.startsWith("/mob/zombie") || name.startsWith("/mob/pigzombie")) {
/*     */       
/* 177 */       int i = bi.getWidth();
/* 178 */       int j = bi.getHeight();
/*     */       
/* 180 */       if (i == j * 2) {
/*     */         
/* 182 */         BufferedImage bufferedimage = new BufferedImage(i, j * 2, 2);
/* 183 */         Graphics2D graphics2d = bufferedimage.createGraphics();
/* 184 */         graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/* 185 */         graphics2d.drawImage(bi, 0, 0, i, j, null);
/* 186 */         return bufferedimage;
/*     */       } 
/*     */     } 
/*     */     
/* 190 */     return bi;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int ceilPowerOfTwo(int val) {
/*     */     int i;
/* 197 */     for (i = 1; i < val; i *= 2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getPowerOfTwo(int val) {
/* 207 */     int i = 1;
/*     */     
/*     */     int j;
/* 210 */     for (j = 0; i < val; j++)
/*     */     {
/* 212 */       i *= 2;
/*     */     }
/*     */     
/* 215 */     return j;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int twoToPower(int power) {
/* 220 */     int i = 1;
/*     */     
/* 222 */     for (int j = 0; j < power; j++)
/*     */     {
/* 224 */       i *= 2;
/*     */     }
/*     */     
/* 227 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ITextureObject getTexture(ResourceLocation loc) {
/* 232 */     ITextureObject itextureobject = Config.getTextureManager().getTexture(loc);
/*     */     
/* 234 */     if (itextureobject != null)
/*     */     {
/* 236 */       return itextureobject;
/*     */     }
/* 238 */     if (!Config.hasResource(loc))
/*     */     {
/* 240 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 244 */     SimpleTexture simpletexture = new SimpleTexture(loc);
/* 245 */     Config.getTextureManager().loadTexture(loc, (ITextureObject)simpletexture);
/* 246 */     return (ITextureObject)simpletexture;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resourcesReloaded(IResourceManager rm) {
/* 252 */     if (getTextureMapBlocks() != null) {
/*     */       
/* 254 */       Config.dbg("*** Reloading custom textures ***");
/* 255 */       CustomSky.reset();
/* 256 */       TextureAnimations.reset();
/* 257 */       update();
/* 258 */       NaturalTextures.update();
/* 259 */       BetterGrass.update();
/* 260 */       BetterSnow.update();
/* 261 */       TextureAnimations.update();
/* 262 */       CustomColors.update();
/* 263 */       CustomSky.update();
/* 264 */       RandomEntities.update();
/* 265 */       CustomItems.updateModels();
/* 266 */       CustomEntityModels.update();
/* 267 */       Shaders.resourcesReloaded();
/* 268 */       Lang.resourcesReloaded();
/* 269 */       Config.updateTexturePackClouds();
/* 270 */       SmartLeaves.updateLeavesModels();
/* 271 */       CustomPanorama.update();
/* 272 */       CustomGuis.update();
/* 273 */       LayerMooshroomMushroom.update();
/* 274 */       CustomLoadingScreens.update();
/* 275 */       CustomBlockLayers.update();
/* 276 */       Config.getTextureManager().tick();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static TextureMap getTextureMapBlocks() {
/* 282 */     return Minecraft.getMinecraft().getTextureMapBlocks();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerResourceListener() {
/* 287 */     IResourceManager iresourcemanager = Config.getResourceManager();
/*     */     
/* 289 */     if (iresourcemanager instanceof IReloadableResourceManager) {
/*     */       
/* 291 */       IReloadableResourceManager ireloadableresourcemanager = (IReloadableResourceManager)iresourcemanager;
/* 292 */       IResourceManagerReloadListener iresourcemanagerreloadlistener = new IResourceManagerReloadListener()
/*     */         {
/*     */           public void onResourceManagerReload(IResourceManager var1)
/*     */           {
/* 296 */             TextureUtils.resourcesReloaded(var1);
/*     */           }
/*     */         };
/* 299 */       ireloadableresourcemanager.registerReloadListener(iresourcemanagerreloadlistener);
/*     */     } 
/*     */     
/* 302 */     ITickableTextureObject itickabletextureobject = new ITickableTextureObject()
/*     */       {
/*     */         public void tick()
/*     */         {
/* 306 */           TextureAnimations.updateAnimations();
/*     */         }
/*     */ 
/*     */         
/*     */         public void loadTexture(IResourceManager var1) throws IOException {}
/*     */         
/*     */         public int getGlTextureId() {
/* 313 */           return 0;
/*     */         }
/*     */ 
/*     */         
/*     */         public void setBlurMipmap(boolean p_174936_1, boolean p_174936_2) {}
/*     */ 
/*     */         
/*     */         public void restoreLastBlurMipmap() {}
/*     */         
/*     */         public MultiTexID getMultiTexID() {
/* 323 */           return null;
/*     */         }
/*     */       };
/* 326 */     ResourceLocation resourcelocation = new ResourceLocation("optifine/TickableTextures");
/* 327 */     Config.getTextureManager().loadTickableTexture(resourcelocation, itickabletextureobject);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ResourceLocation fixResourceLocation(ResourceLocation loc, String basePath) {
/* 332 */     if (!loc.getResourceDomain().equals("minecraft"))
/*     */     {
/* 334 */       return loc;
/*     */     }
/*     */ 
/*     */     
/* 338 */     String s = loc.getResourcePath();
/* 339 */     String s1 = fixResourcePath(s, basePath);
/*     */     
/* 341 */     if (s1 != s)
/*     */     {
/* 343 */       loc = new ResourceLocation(loc.getResourceDomain(), s1);
/*     */     }
/*     */     
/* 346 */     return loc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String fixResourcePath(String path, String basePath) {
/* 352 */     String s = "assets/minecraft/";
/*     */     
/* 354 */     if (path.startsWith(s)) {
/*     */       
/* 356 */       path = path.substring(s.length());
/* 357 */       return path;
/*     */     } 
/* 359 */     if (path.startsWith("./")) {
/*     */       
/* 361 */       path = path.substring(2);
/*     */       
/* 363 */       if (!basePath.endsWith("/"))
/*     */       {
/* 365 */         basePath = basePath + "/";
/*     */       }
/*     */       
/* 368 */       path = basePath + path;
/* 369 */       return path;
/*     */     } 
/*     */ 
/*     */     
/* 373 */     if (path.startsWith("/~"))
/*     */     {
/* 375 */       path = path.substring(1);
/*     */     }
/*     */     
/* 378 */     String s1 = "mcpatcher/";
/*     */     
/* 380 */     if (path.startsWith("~/")) {
/*     */       
/* 382 */       path = path.substring(2);
/* 383 */       path = s1 + path;
/* 384 */       return path;
/*     */     } 
/* 386 */     if (path.startsWith("/")) {
/*     */       
/* 388 */       path = s1 + path.substring(1);
/* 389 */       return path;
/*     */     } 
/*     */ 
/*     */     
/* 393 */     return path;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getBasePath(String path) {
/* 400 */     int i = path.lastIndexOf('/');
/* 401 */     return (i < 0) ? "" : path.substring(0, i);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void applyAnisotropicLevel() {
/* 406 */     if ((GLContext.getCapabilities()).GL_EXT_texture_filter_anisotropic) {
/*     */       
/* 408 */       float f = GL11.glGetFloat(34047);
/* 409 */       float f1 = Config.getAnisotropicFilterLevel();
/* 410 */       f1 = Math.min(f1, f);
/* 411 */       GL11.glTexParameterf(3553, 34046, f1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindTexture(int glTexId) {
/* 417 */     GlStateManager.bindTexture(glTexId);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isPowerOfTwo(int x) {
/* 422 */     int i = MathHelper.roundUpToPowerOfTwo(x);
/* 423 */     return (i == x);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BufferedImage scaleImage(BufferedImage bi, int w2) {
/* 428 */     int i = bi.getWidth();
/* 429 */     int j = bi.getHeight();
/* 430 */     int k = j * w2 / i;
/* 431 */     BufferedImage bufferedimage = new BufferedImage(w2, k, 2);
/* 432 */     Graphics2D graphics2d = bufferedimage.createGraphics();
/* 433 */     Object object = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
/*     */     
/* 435 */     if (w2 < i || w2 % i != 0)
/*     */     {
/* 437 */       object = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
/*     */     }
/*     */     
/* 440 */     graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, object);
/* 441 */     graphics2d.drawImage(bi, 0, 0, w2, k, null);
/* 442 */     return bufferedimage;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int scaleToGrid(int size, int sizeGrid) {
/* 447 */     if (size == sizeGrid)
/*     */     {
/* 449 */       return size;
/*     */     }
/*     */ 
/*     */     
/*     */     int i;
/*     */     
/* 455 */     for (i = size / sizeGrid * sizeGrid; i < size; i += sizeGrid);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 460 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int scaleToMin(int size, int sizeMin) {
/* 466 */     if (size >= sizeMin)
/*     */     {
/* 468 */       return size;
/*     */     }
/*     */ 
/*     */     
/*     */     int i;
/*     */     
/* 474 */     for (i = sizeMin / size * size; i < sizeMin; i += size);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 479 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Dimension getImageSize(InputStream in, String suffix) {
/* 485 */     Iterator<ImageReader> iterator = ImageIO.getImageReadersBySuffix(suffix);
/*     */ 
/*     */ 
/*     */     
/* 489 */     while (iterator.hasNext()) {
/*     */       Dimension dimension;
/* 491 */       ImageReader imagereader = iterator.next();
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 496 */         ImageInputStream imageinputstream = ImageIO.createImageInputStream(in);
/* 497 */         imagereader.setInput(imageinputstream);
/* 498 */         int i = imagereader.getWidth(imagereader.getMinIndex());
/* 499 */         int j = imagereader.getHeight(imagereader.getMinIndex());
/* 500 */         dimension = new Dimension(i, j);
/*     */       }
/* 502 */       catch (IOException var11) {
/*     */         
/*     */         continue;
/*     */       }
/*     */       finally {
/*     */         
/* 508 */         imagereader.dispose();
/*     */       } 
/*     */       
/* 511 */       return dimension;
/*     */     } 
/*     */     
/* 514 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void dbgMipmaps(TextureAtlasSprite textureatlassprite) {
/* 520 */     int[][] aint = textureatlassprite.getFrameTextureData(0);
/*     */     
/* 522 */     for (int i = 0; i < aint.length; i++) {
/*     */       
/* 524 */       int[] aint1 = aint[i];
/*     */       
/* 526 */       if (aint1 == null) {
/*     */         
/* 528 */         Config.dbg("" + i + ": " + aint1);
/*     */       }
/*     */       else {
/*     */         
/* 532 */         Config.dbg("" + i + ": " + aint1.length);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void saveGlTexture(String name, int textureId, int mipmapLevels, int width, int height) {
/* 539 */     bindTexture(textureId);
/* 540 */     GL11.glPixelStorei(3333, 1);
/* 541 */     GL11.glPixelStorei(3317, 1);
/* 542 */     File file1 = new File(name);
/* 543 */     File file2 = file1.getParentFile();
/*     */     
/* 545 */     if (file2 != null)
/*     */     {
/* 547 */       file2.mkdirs();
/*     */     }
/*     */     
/* 550 */     for (int i = 0; i < 16; i++) {
/*     */       
/* 552 */       File file3 = new File(name + "_" + i + ".png");
/* 553 */       file3.delete();
/*     */     } 
/*     */     
/* 556 */     for (int i1 = 0; i1 <= mipmapLevels; i1++) {
/*     */       
/* 558 */       File file4 = new File(name + "_" + i1 + ".png");
/* 559 */       int j = width >> i1;
/* 560 */       int k = height >> i1;
/* 561 */       int l = j * k;
/* 562 */       IntBuffer intbuffer = BufferUtils.createIntBuffer(l);
/* 563 */       int[] aint = new int[l];
/* 564 */       GL11.glGetTexImage(3553, i1, 32993, 33639, intbuffer);
/* 565 */       intbuffer.get(aint);
/* 566 */       BufferedImage bufferedimage = new BufferedImage(j, k, 2);
/* 567 */       bufferedimage.setRGB(0, 0, j, k, aint, 0, j);
/*     */ 
/*     */       
/*     */       try {
/* 571 */         ImageIO.write(bufferedimage, "png", file4);
/* 572 */         Config.dbg("Exported: " + file4);
/*     */       }
/* 574 */       catch (Exception exception) {
/*     */         
/* 576 */         Config.warn("Error writing: " + file4);
/* 577 */         Config.warn("" + exception.getClass().getName() + ": " + exception.getMessage());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void generateCustomMipmaps(TextureAtlasSprite tas, int mipmaps) {
/* 584 */     int i = tas.getIconWidth();
/* 585 */     int j = tas.getIconHeight();
/*     */     
/* 587 */     if (tas.getFrameCount() < 1) {
/*     */       
/* 589 */       List<int[][]> list = (List)new ArrayList<>();
/* 590 */       int[][] aint = new int[mipmaps + 1][];
/* 591 */       int[] aint1 = new int[i * j];
/* 592 */       aint[0] = aint1;
/* 593 */       list.add(aint);
/* 594 */       tas.setFramesTextureData(list);
/*     */     } 
/*     */     
/* 597 */     List<int[][]> list1 = (List)new ArrayList<>();
/* 598 */     int l = tas.getFrameCount();
/*     */     
/* 600 */     for (int i1 = 0; i1 < l; i1++) {
/*     */       
/* 602 */       int[] aint2 = getFrameData(tas, i1, 0);
/*     */       
/* 604 */       if (aint2 == null || aint2.length < 1)
/*     */       {
/* 606 */         aint2 = new int[i * j];
/*     */       }
/*     */       
/* 609 */       if (aint2.length != i * j) {
/*     */         
/* 611 */         int k = (int)Math.round(Math.sqrt(aint2.length));
/*     */         
/* 613 */         if (k * k != aint2.length) {
/*     */           
/* 615 */           aint2 = new int[1];
/* 616 */           k = 1;
/*     */         } 
/*     */         
/* 619 */         BufferedImage bufferedimage = new BufferedImage(k, k, 2);
/* 620 */         bufferedimage.setRGB(0, 0, k, k, aint2, 0, k);
/* 621 */         BufferedImage bufferedimage1 = scaleImage(bufferedimage, i);
/* 622 */         int[] aint3 = new int[i * j];
/* 623 */         bufferedimage1.getRGB(0, 0, i, j, aint3, 0, i);
/* 624 */         aint2 = aint3;
/*     */       } 
/*     */       
/* 627 */       int[][] aint4 = new int[mipmaps + 1][];
/* 628 */       aint4[0] = aint2;
/* 629 */       list1.add(aint4);
/*     */     } 
/*     */     
/* 632 */     tas.setFramesTextureData(list1);
/* 633 */     tas.generateMipmaps(mipmaps);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] getFrameData(TextureAtlasSprite tas, int frame, int level) {
/* 638 */     List<int[][]> list = tas.getFramesTextureData();
/*     */     
/* 640 */     if (list.size() <= frame)
/*     */     {
/* 642 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 646 */     int[][] aint = list.get(frame);
/*     */     
/* 648 */     if (aint != null && aint.length > level) {
/*     */       
/* 650 */       int[] aint1 = aint[level];
/* 651 */       return aint1;
/*     */     } 
/*     */ 
/*     */     
/* 655 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getGLMaximumTextureSize() {
/* 662 */     for (int i = 65536; i > 0; i >>= 1) {
/*     */       
/* 664 */       GlStateManager.glTexImage2D(32868, 0, 6408, i, i, 0, 6408, 5121, null);
/* 665 */       int j = GL11.glGetError();
/* 666 */       int k = GlStateManager.glGetTexLevelParameteri(32868, 0, 4096);
/*     */       
/* 668 */       if (k != 0)
/*     */       {
/* 670 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 674 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifin\\util\TextureUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
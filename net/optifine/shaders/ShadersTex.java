/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.LayeredTexture;
/*     */ import net.minecraft.client.renderer.texture.Stitcher;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class ShadersTex
/*     */ {
/*     */   public static final int initialBufferSize = 1048576;
/*  35 */   public static ByteBuffer byteBuffer = BufferUtils.createByteBuffer(4194304);
/*  36 */   public static IntBuffer intBuffer = byteBuffer.asIntBuffer();
/*  37 */   public static int[] intArray = new int[1048576];
/*     */   public static final int defBaseTexColor = 0;
/*     */   public static final int defNormTexColor = -8421377;
/*     */   public static final int defSpecTexColor = 0;
/*  41 */   public static Map<Integer, MultiTexID> multiTexMap = new HashMap<>();
/*  42 */   public static TextureMap updatingTextureMap = null;
/*  43 */   public static TextureAtlasSprite updatingSprite = null;
/*  44 */   public static MultiTexID updatingTex = null;
/*  45 */   public static MultiTexID boundTex = null;
/*  46 */   public static int updatingPage = 0;
/*  47 */   public static String iconName = null;
/*  48 */   public static IResourceManager resManager = null;
/*     */ 
/*     */   
/*     */   public static IntBuffer getIntBuffer(int size) {
/*  52 */     if (intBuffer.capacity() < size) {
/*     */       
/*  54 */       int i = roundUpPOT(size);
/*  55 */       byteBuffer = BufferUtils.createByteBuffer(i * 4);
/*  56 */       intBuffer = byteBuffer.asIntBuffer();
/*     */     } 
/*     */     
/*  59 */     return intBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] getIntArray(int size) {
/*  64 */     if (intArray == null)
/*     */     {
/*  66 */       intArray = new int[1048576];
/*     */     }
/*     */     
/*  69 */     if (intArray.length < size)
/*     */     {
/*  71 */       intArray = new int[roundUpPOT(size)];
/*     */     }
/*     */     
/*  74 */     return intArray;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int roundUpPOT(int x) {
/*  79 */     int i = x - 1;
/*  80 */     i |= i >> 1;
/*  81 */     i |= i >> 2;
/*  82 */     i |= i >> 4;
/*  83 */     i |= i >> 8;
/*  84 */     i |= i >> 16;
/*  85 */     return i + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int log2(int x) {
/*  90 */     int i = 0;
/*     */     
/*  92 */     if ((x & 0xFFFF0000) != 0) {
/*     */       
/*  94 */       i += 16;
/*  95 */       x >>= 16;
/*     */     } 
/*     */     
/*  98 */     if ((x & 0xFF00) != 0) {
/*     */       
/* 100 */       i += 8;
/* 101 */       x >>= 8;
/*     */     } 
/*     */     
/* 104 */     if ((x & 0xF0) != 0) {
/*     */       
/* 106 */       i += 4;
/* 107 */       x >>= 4;
/*     */     } 
/*     */     
/* 110 */     if ((x & 0x6) != 0) {
/*     */       
/* 112 */       i += 2;
/* 113 */       x >>= 2;
/*     */     } 
/*     */     
/* 116 */     if ((x & 0x2) != 0)
/*     */     {
/* 118 */       i++;
/*     */     }
/*     */     
/* 121 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static IntBuffer fillIntBuffer(int size, int value) {
/* 126 */     int[] aint = getIntArray(size);
/* 127 */     IntBuffer intbuffer = getIntBuffer(size);
/* 128 */     Arrays.fill(intArray, 0, size, value);
/* 129 */     intBuffer.put(intArray, 0, size);
/* 130 */     return intBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] createAIntImage(int size) {
/* 135 */     int[] aint = new int[size * 3];
/* 136 */     Arrays.fill(aint, 0, size, 0);
/* 137 */     Arrays.fill(aint, size, size * 2, -8421377);
/* 138 */     Arrays.fill(aint, size * 2, size * 3, 0);
/* 139 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] createAIntImage(int size, int color) {
/* 144 */     int[] aint = new int[size * 3];
/* 145 */     Arrays.fill(aint, 0, size, color);
/* 146 */     Arrays.fill(aint, size, size * 2, -8421377);
/* 147 */     Arrays.fill(aint, size * 2, size * 3, 0);
/* 148 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static MultiTexID getMultiTexID(AbstractTexture tex) {
/* 153 */     MultiTexID multitexid = tex.multiTex;
/*     */     
/* 155 */     if (multitexid == null) {
/*     */       
/* 157 */       int i = tex.getGlTextureId();
/* 158 */       multitexid = multiTexMap.get(Integer.valueOf(i));
/*     */       
/* 160 */       if (multitexid == null) {
/*     */         
/* 162 */         multitexid = new MultiTexID(i, GL11.glGenTextures(), GL11.glGenTextures());
/* 163 */         multiTexMap.put(Integer.valueOf(i), multitexid);
/*     */       } 
/*     */       
/* 166 */       tex.multiTex = multitexid;
/*     */     } 
/*     */     
/* 169 */     return multitexid;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void deleteTextures(AbstractTexture atex, int texid) {
/* 174 */     MultiTexID multitexid = atex.multiTex;
/*     */     
/* 176 */     if (multitexid != null) {
/*     */       
/* 178 */       atex.multiTex = null;
/* 179 */       multiTexMap.remove(Integer.valueOf(multitexid.base));
/* 180 */       GlStateManager.deleteTexture(multitexid.norm);
/* 181 */       GlStateManager.deleteTexture(multitexid.spec);
/*     */       
/* 183 */       if (multitexid.base != texid) {
/*     */         
/* 185 */         SMCLog.warning("Error : MultiTexID.base mismatch: " + multitexid.base + ", texid: " + texid);
/* 186 */         GlStateManager.deleteTexture(multitexid.base);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindNSTextures(int normTex, int specTex) {
/* 193 */     if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
/*     */       
/* 195 */       GlStateManager.setActiveTexture(33986);
/* 196 */       GlStateManager.bindTexture(normTex);
/* 197 */       GlStateManager.setActiveTexture(33987);
/* 198 */       GlStateManager.bindTexture(specTex);
/* 199 */       GlStateManager.setActiveTexture(33984);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindNSTextures(MultiTexID multiTex) {
/* 205 */     bindNSTextures(multiTex.norm, multiTex.spec);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindTextures(int baseTex, int normTex, int specTex) {
/* 210 */     if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
/*     */       
/* 212 */       GlStateManager.setActiveTexture(33986);
/* 213 */       GlStateManager.bindTexture(normTex);
/* 214 */       GlStateManager.setActiveTexture(33987);
/* 215 */       GlStateManager.bindTexture(specTex);
/* 216 */       GlStateManager.setActiveTexture(33984);
/*     */     } 
/*     */     
/* 219 */     GlStateManager.bindTexture(baseTex);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindTextures(MultiTexID multiTex) {
/* 224 */     boundTex = multiTex;
/*     */     
/* 226 */     if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
/*     */       
/* 228 */       if (Shaders.configNormalMap) {
/*     */         
/* 230 */         GlStateManager.setActiveTexture(33986);
/* 231 */         GlStateManager.bindTexture(multiTex.norm);
/*     */       } 
/*     */       
/* 234 */       if (Shaders.configSpecularMap) {
/*     */         
/* 236 */         GlStateManager.setActiveTexture(33987);
/* 237 */         GlStateManager.bindTexture(multiTex.spec);
/*     */       } 
/*     */       
/* 240 */       GlStateManager.setActiveTexture(33984);
/*     */     } 
/*     */     
/* 243 */     GlStateManager.bindTexture(multiTex.base);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindTexture(ITextureObject tex) {
/* 248 */     int i = tex.getGlTextureId();
/* 249 */     bindTextures(tex.getMultiTexID());
/*     */     
/* 251 */     if (GlStateManager.getActiveTextureUnit() == 33984) {
/*     */       
/* 253 */       int j = Shaders.atlasSizeX;
/* 254 */       int k = Shaders.atlasSizeY;
/*     */       
/* 256 */       if (tex instanceof TextureMap) {
/*     */         
/* 258 */         Shaders.atlasSizeX = ((TextureMap)tex).atlasWidth;
/* 259 */         Shaders.atlasSizeY = ((TextureMap)tex).atlasHeight;
/*     */       }
/*     */       else {
/*     */         
/* 263 */         Shaders.atlasSizeX = 0;
/* 264 */         Shaders.atlasSizeY = 0;
/*     */       } 
/*     */       
/* 267 */       if (Shaders.atlasSizeX != j || Shaders.atlasSizeY != k)
/*     */       {
/* 269 */         Shaders.uniform_atlasSize.setValue(Shaders.atlasSizeX, Shaders.atlasSizeY);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindTextureMapForUpdateAndRender(TextureManager tm, ResourceLocation resLoc) {
/* 276 */     TextureMap texturemap = (TextureMap)tm.getTexture(resLoc);
/* 277 */     Shaders.atlasSizeX = texturemap.atlasWidth;
/* 278 */     Shaders.atlasSizeY = texturemap.atlasHeight;
/* 279 */     bindTextures(updatingTex = texturemap.getMultiTexID());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindTextures(int baseTex) {
/* 284 */     MultiTexID multitexid = multiTexMap.get(Integer.valueOf(baseTex));
/* 285 */     bindTextures(multitexid);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void initDynamicTexture(int texID, int width, int height, DynamicTexture tex) {
/* 290 */     MultiTexID multitexid = tex.getMultiTexID();
/* 291 */     int[] aint = tex.getTextureData();
/* 292 */     int i = width * height;
/* 293 */     Arrays.fill(aint, i, i * 2, -8421377);
/* 294 */     Arrays.fill(aint, i * 2, i * 3, 0);
/* 295 */     TextureUtil.allocateTexture(multitexid.base, width, height);
/* 296 */     TextureUtil.setTextureBlurMipmap(false, false);
/* 297 */     TextureUtil.setTextureClamped(false);
/* 298 */     TextureUtil.allocateTexture(multitexid.norm, width, height);
/* 299 */     TextureUtil.setTextureBlurMipmap(false, false);
/* 300 */     TextureUtil.setTextureClamped(false);
/* 301 */     TextureUtil.allocateTexture(multitexid.spec, width, height);
/* 302 */     TextureUtil.setTextureBlurMipmap(false, false);
/* 303 */     TextureUtil.setTextureClamped(false);
/* 304 */     GlStateManager.bindTexture(multitexid.base);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateDynamicTexture(int texID, int[] src, int width, int height, DynamicTexture tex) {
/* 309 */     MultiTexID multitexid = tex.getMultiTexID();
/* 310 */     GlStateManager.bindTexture(multitexid.base);
/* 311 */     updateDynTexSubImage1(src, width, height, 0, 0, 0);
/* 312 */     GlStateManager.bindTexture(multitexid.norm);
/* 313 */     updateDynTexSubImage1(src, width, height, 0, 0, 1);
/* 314 */     GlStateManager.bindTexture(multitexid.spec);
/* 315 */     updateDynTexSubImage1(src, width, height, 0, 0, 2);
/* 316 */     GlStateManager.bindTexture(multitexid.base);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateDynTexSubImage1(int[] src, int width, int height, int posX, int posY, int page) {
/* 321 */     int i = width * height;
/* 322 */     IntBuffer intbuffer = getIntBuffer(i);
/* 323 */     intbuffer.clear();
/* 324 */     int j = page * i;
/*     */     
/* 326 */     if (src.length >= j + i) {
/*     */       
/* 328 */       intbuffer.put(src, j, i).position(0).limit(i);
/* 329 */       GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intbuffer);
/* 330 */       intbuffer.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ITextureObject createDefaultTexture() {
/* 336 */     DynamicTexture dynamictexture = new DynamicTexture(1, 1);
/* 337 */     dynamictexture.getTextureData()[0] = -1;
/* 338 */     dynamictexture.updateDynamicTexture();
/* 339 */     return (ITextureObject)dynamictexture;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void allocateTextureMap(int texID, int mipmapLevels, int width, int height, Stitcher stitcher, TextureMap tex) {
/* 344 */     SMCLog.info("allocateTextureMap " + mipmapLevels + " " + width + " " + height + " ");
/* 345 */     updatingTextureMap = tex;
/* 346 */     tex.atlasWidth = width;
/* 347 */     tex.atlasHeight = height;
/* 348 */     MultiTexID multitexid = getMultiTexID((AbstractTexture)tex);
/* 349 */     updatingTex = multitexid;
/* 350 */     TextureUtil.allocateTextureImpl(multitexid.base, mipmapLevels, width, height);
/*     */     
/* 352 */     if (Shaders.configNormalMap)
/*     */     {
/* 354 */       TextureUtil.allocateTextureImpl(multitexid.norm, mipmapLevels, width, height);
/*     */     }
/*     */     
/* 357 */     if (Shaders.configSpecularMap)
/*     */     {
/* 359 */       TextureUtil.allocateTextureImpl(multitexid.spec, mipmapLevels, width, height);
/*     */     }
/*     */     
/* 362 */     GlStateManager.bindTexture(texID);
/*     */   }
/*     */ 
/*     */   
/*     */   public static TextureAtlasSprite setSprite(TextureAtlasSprite tas) {
/* 367 */     updatingSprite = tas;
/* 368 */     return tas;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String setIconName(String name) {
/* 373 */     iconName = name;
/* 374 */     return name;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void uploadTexSubForLoadAtlas(int[][] data, int width, int height, int xoffset, int yoffset, boolean linear, boolean clamp) {
/* 379 */     TextureUtil.uploadTextureMipmap(data, width, height, xoffset, yoffset, linear, clamp);
/* 380 */     boolean flag = false;
/*     */     
/* 382 */     if (Shaders.configNormalMap) {
/*     */       
/* 384 */       int[][] aint = readImageAndMipmaps(iconName + "_n", width, height, data.length, flag, -8421377);
/* 385 */       GlStateManager.bindTexture(updatingTex.norm);
/* 386 */       TextureUtil.uploadTextureMipmap(aint, width, height, xoffset, yoffset, linear, clamp);
/*     */     } 
/*     */     
/* 389 */     if (Shaders.configSpecularMap) {
/*     */       
/* 391 */       int[][] aint1 = readImageAndMipmaps(iconName + "_s", width, height, data.length, flag, 0);
/* 392 */       GlStateManager.bindTexture(updatingTex.spec);
/* 393 */       TextureUtil.uploadTextureMipmap(aint1, width, height, xoffset, yoffset, linear, clamp);
/*     */     } 
/*     */     
/* 396 */     GlStateManager.bindTexture(updatingTex.base);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[][] readImageAndMipmaps(String name, int width, int height, int numLevels, boolean border, int defColor) {
/* 401 */     int[][] aint = new int[numLevels][];
/*     */     
/* 403 */     int[] aint1 = new int[width * height];
/* 404 */     boolean flag = false;
/* 405 */     BufferedImage bufferedimage = readImage(updatingTextureMap.completeResourceLocation(new ResourceLocation(name)));
/*     */     
/* 407 */     if (bufferedimage != null) {
/*     */       
/* 409 */       int i = bufferedimage.getWidth();
/* 410 */       int j = bufferedimage.getHeight();
/*     */       
/* 412 */       if (i + (border ? 16 : 0) == width) {
/*     */         
/* 414 */         flag = true;
/* 415 */         bufferedimage.getRGB(0, 0, i, i, aint1, 0, i);
/*     */       } 
/*     */     } 
/*     */     
/* 419 */     if (!flag)
/*     */     {
/* 421 */       Arrays.fill(aint1, defColor);
/*     */     }
/*     */     
/* 424 */     GlStateManager.bindTexture(updatingTex.spec);
/* 425 */     aint = genMipmapsSimple(aint.length - 1, width, aint);
/* 426 */     return aint;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage readImage(ResourceLocation resLoc) {
/*     */     try {
/* 433 */       if (!Config.hasResource(resLoc))
/*     */       {
/* 435 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 439 */       InputStream inputstream = Config.getResourceStream(resLoc);
/*     */       
/* 441 */       if (inputstream == null)
/*     */       {
/* 443 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 447 */       BufferedImage bufferedimage = ImageIO.read(inputstream);
/* 448 */       inputstream.close();
/* 449 */       return bufferedimage;
/*     */ 
/*     */     
/*     */     }
/* 453 */     catch (IOException var3) {
/*     */       
/* 455 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[][] genMipmapsSimple(int maxLevel, int width, int[][] data) {
/* 461 */     for (int i = 1; i <= maxLevel; i++) {
/*     */       
/* 463 */       if (data[i] == null) {
/*     */         
/* 465 */         int j = width >> i;
/* 466 */         int k = j * 2;
/* 467 */         int[] aint = data[i - 1];
/* 468 */         int[] aint1 = data[i] = new int[j * j];
/*     */         
/* 470 */         for (int i1 = 0; i1 < j; i1++) {
/*     */           
/* 472 */           for (int l = 0; l < j; l++) {
/*     */             
/* 474 */             int j1 = i1 * 2 * k + l * 2;
/* 475 */             aint1[i1 * j + l] = blend4Simple(aint[j1], aint[j1 + 1], aint[j1 + k], aint[j1 + k + 1]);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 481 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void uploadTexSub(int[][] data, int width, int height, int xoffset, int yoffset, boolean linear, boolean clamp) {
/* 486 */     TextureUtil.uploadTextureMipmap(data, width, height, xoffset, yoffset, linear, clamp);
/*     */     
/* 488 */     if (Shaders.configNormalMap || Shaders.configSpecularMap) {
/*     */       
/* 490 */       if (Shaders.configNormalMap) {
/*     */         
/* 492 */         GlStateManager.bindTexture(updatingTex.norm);
/* 493 */         uploadTexSub1(data, width, height, xoffset, yoffset, 1);
/*     */       } 
/*     */       
/* 496 */       if (Shaders.configSpecularMap) {
/*     */         
/* 498 */         GlStateManager.bindTexture(updatingTex.spec);
/* 499 */         uploadTexSub1(data, width, height, xoffset, yoffset, 2);
/*     */       } 
/*     */       
/* 502 */       GlStateManager.bindTexture(updatingTex.base);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void uploadTexSub1(int[][] src, int width, int height, int posX, int posY, int page) {
/* 508 */     int i = width * height;
/* 509 */     IntBuffer intbuffer = getIntBuffer(i);
/* 510 */     int j = src.length;
/* 511 */     int k = 0;
/* 512 */     int l = width;
/* 513 */     int i1 = height;
/* 514 */     int j1 = posX;
/*     */     
/* 516 */     for (int k1 = posY; l > 0 && i1 > 0 && k < j; k++) {
/*     */       
/* 518 */       int l1 = l * i1;
/* 519 */       int[] aint = src[k];
/* 520 */       intbuffer.clear();
/*     */       
/* 522 */       if (aint.length >= l1 * (page + 1)) {
/*     */         
/* 524 */         intbuffer.put(aint, l1 * page, l1).position(0).limit(l1);
/* 525 */         GL11.glTexSubImage2D(3553, k, j1, k1, l, i1, 32993, 33639, intbuffer);
/*     */       } 
/*     */       
/* 528 */       l >>= 1;
/* 529 */       i1 >>= 1;
/* 530 */       j1 >>= 1;
/* 531 */       k1 >>= 1;
/*     */     } 
/*     */     
/* 534 */     intbuffer.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int blend4Alpha(int c0, int c1, int c2, int c3) {
/* 539 */     int k1, i = c0 >>> 24 & 0xFF;
/* 540 */     int j = c1 >>> 24 & 0xFF;
/* 541 */     int k = c2 >>> 24 & 0xFF;
/* 542 */     int l = c3 >>> 24 & 0xFF;
/* 543 */     int i1 = i + j + k + l;
/* 544 */     int j1 = (i1 + 2) / 4;
/*     */ 
/*     */     
/* 547 */     if (i1 != 0) {
/*     */       
/* 549 */       k1 = i1;
/*     */     }
/*     */     else {
/*     */       
/* 553 */       k1 = 4;
/* 554 */       i = 1;
/* 555 */       j = 1;
/* 556 */       k = 1;
/* 557 */       l = 1;
/*     */     } 
/*     */     
/* 560 */     int l1 = (k1 + 1) / 2;
/* 561 */     int i2 = j1 << 24 | ((c0 >>> 16 & 0xFF) * i + (c1 >>> 16 & 0xFF) * j + (c2 >>> 16 & 0xFF) * k + (c3 >>> 16 & 0xFF) * l + l1) / k1 << 16 | ((c0 >>> 8 & 0xFF) * i + (c1 >>> 8 & 0xFF) * j + (c2 >>> 8 & 0xFF) * k + (c3 >>> 8 & 0xFF) * l + l1) / k1 << 8 | ((c0 >>> 0 & 0xFF) * i + (c1 >>> 0 & 0xFF) * j + (c2 >>> 0 & 0xFF) * k + (c3 >>> 0 & 0xFF) * l + l1) / k1 << 0;
/* 562 */     return i2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int blend4Simple(int c0, int c1, int c2, int c3) {
/* 567 */     int i = ((c0 >>> 24 & 0xFF) + (c1 >>> 24 & 0xFF) + (c2 >>> 24 & 0xFF) + (c3 >>> 24 & 0xFF) + 2) / 4 << 24 | ((c0 >>> 16 & 0xFF) + (c1 >>> 16 & 0xFF) + (c2 >>> 16 & 0xFF) + (c3 >>> 16 & 0xFF) + 2) / 4 << 16 | ((c0 >>> 8 & 0xFF) + (c1 >>> 8 & 0xFF) + (c2 >>> 8 & 0xFF) + (c3 >>> 8 & 0xFF) + 2) / 4 << 8 | ((c0 >>> 0 & 0xFF) + (c1 >>> 0 & 0xFF) + (c2 >>> 0 & 0xFF) + (c3 >>> 0 & 0xFF) + 2) / 4 << 0;
/* 568 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void genMipmapAlpha(int[] aint, int offset, int width, int height) {
/* 573 */     Math.min(width, height);
/* 574 */     int o2 = offset;
/* 575 */     int w2 = width;
/* 576 */     int h2 = height;
/* 577 */     int o1 = 0;
/* 578 */     int w1 = 0;
/* 579 */     int h1 = 0;
/*     */     
/*     */     int i;
/* 582 */     for (i = 0; w2 > 1 && h2 > 1; o2 = o1) {
/*     */       
/* 584 */       o1 = o2 + w2 * h2;
/* 585 */       w1 = w2 / 2;
/* 586 */       h1 = h2 / 2;
/*     */       
/* 588 */       for (int l1 = 0; l1 < h1; l1++) {
/*     */         
/* 590 */         int i2 = o1 + l1 * w1;
/* 591 */         int j2 = o2 + l1 * 2 * w2;
/*     */         
/* 593 */         for (int k2 = 0; k2 < w1; k2++)
/*     */         {
/* 595 */           aint[i2 + k2] = blend4Alpha(aint[j2 + k2 * 2], aint[j2 + k2 * 2 + 1], aint[j2 + w2 + k2 * 2], aint[j2 + w2 + k2 * 2 + 1]);
/*     */         }
/*     */       } 
/*     */       
/* 599 */       i++;
/* 600 */       w2 = w1;
/* 601 */       h2 = h1;
/*     */     } 
/*     */     
/* 604 */     while (i > 0) {
/*     */       
/* 606 */       i--;
/* 607 */       w2 = width >> i;
/* 608 */       h2 = height >> i;
/* 609 */       o2 = o1 - w2 * h2;
/* 610 */       int l2 = o2;
/*     */       
/* 612 */       for (int i3 = 0; i3 < h2; i3++) {
/*     */         
/* 614 */         for (int j3 = 0; j3 < w2; j3++) {
/*     */           
/* 616 */           if (aint[l2] == 0)
/*     */           {
/* 618 */             aint[l2] = aint[o1 + i3 / 2 * w1 + j3 / 2] & 0xFFFFFF;
/*     */           }
/*     */           
/* 621 */           l2++;
/*     */         } 
/*     */       } 
/*     */       
/* 625 */       o1 = o2;
/* 626 */       w1 = w2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void genMipmapSimple(int[] aint, int offset, int width, int height) {
/* 632 */     Math.min(width, height);
/* 633 */     int o2 = offset;
/* 634 */     int w2 = width;
/* 635 */     int h2 = height;
/* 636 */     int o1 = 0;
/* 637 */     int w1 = 0;
/* 638 */     int h1 = 0;
/*     */     
/*     */     int i;
/* 641 */     for (i = 0; w2 > 1 && h2 > 1; o2 = o1) {
/*     */       
/* 643 */       o1 = o2 + w2 * h2;
/* 644 */       w1 = w2 / 2;
/* 645 */       h1 = h2 / 2;
/*     */       
/* 647 */       for (int l1 = 0; l1 < h1; l1++) {
/*     */         
/* 649 */         int i2 = o1 + l1 * w1;
/* 650 */         int j2 = o2 + l1 * 2 * w2;
/*     */         
/* 652 */         for (int k2 = 0; k2 < w1; k2++)
/*     */         {
/* 654 */           aint[i2 + k2] = blend4Simple(aint[j2 + k2 * 2], aint[j2 + k2 * 2 + 1], aint[j2 + w2 + k2 * 2], aint[j2 + w2 + k2 * 2 + 1]);
/*     */         }
/*     */       } 
/*     */       
/* 658 */       i++;
/* 659 */       w2 = w1;
/* 660 */       h2 = h1;
/*     */     } 
/*     */     
/* 663 */     while (i > 0) {
/*     */       
/* 665 */       i--;
/* 666 */       w2 = width >> i;
/* 667 */       h2 = height >> i;
/* 668 */       o2 = o1 - w2 * h2;
/* 669 */       int l2 = o2;
/*     */       
/* 671 */       for (int i3 = 0; i3 < h2; i3++) {
/*     */         
/* 673 */         for (int j3 = 0; j3 < w2; j3++) {
/*     */           
/* 675 */           if (aint[l2] == 0)
/*     */           {
/* 677 */             aint[l2] = aint[o1 + i3 / 2 * w1 + j3 / 2] & 0xFFFFFF;
/*     */           }
/*     */           
/* 680 */           l2++;
/*     */         } 
/*     */       } 
/*     */       
/* 684 */       o1 = o2;
/* 685 */       w1 = w2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSemiTransparent(int[] aint, int width, int height) {
/* 691 */     int i = width * height;
/*     */     
/* 693 */     if (aint[0] >>> 24 == 255 && aint[i - 1] == 0)
/*     */     {
/* 695 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 699 */     for (int j = 0; j < i; j++) {
/*     */       
/* 701 */       int k = aint[j] >>> 24;
/*     */       
/* 703 */       if (k != 0 && k != 255)
/*     */       {
/* 705 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 709 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateSubTex1(int[] src, int width, int height, int posX, int posY) {
/* 715 */     int i = 0;
/* 716 */     int j = width;
/* 717 */     int k = height;
/* 718 */     int l = posX;
/*     */     int i1;
/* 720 */     for (i1 = posY; j > 0 && k > 0; i1 /= 2) {
/*     */       
/* 722 */       GL11.glCopyTexSubImage2D(3553, i, l, i1, 0, 0, j, k);
/* 723 */       i++;
/* 724 */       j /= 2;
/* 725 */       k /= 2;
/* 726 */       l /= 2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setupTexture(MultiTexID multiTex, int[] src, int width, int height, boolean linear, boolean clamp) {
/* 732 */     int i = linear ? 9729 : 9728;
/* 733 */     int j = clamp ? 33071 : 10497;
/* 734 */     int k = width * height;
/* 735 */     IntBuffer intbuffer = getIntBuffer(k);
/* 736 */     intbuffer.clear();
/* 737 */     intbuffer.put(src, 0, k).position(0).limit(k);
/* 738 */     GlStateManager.bindTexture(multiTex.base);
/* 739 */     GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, intbuffer);
/* 740 */     GL11.glTexParameteri(3553, 10241, i);
/* 741 */     GL11.glTexParameteri(3553, 10240, i);
/* 742 */     GL11.glTexParameteri(3553, 10242, j);
/* 743 */     GL11.glTexParameteri(3553, 10243, j);
/* 744 */     intbuffer.put(src, k, k).position(0).limit(k);
/* 745 */     GlStateManager.bindTexture(multiTex.norm);
/* 746 */     GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, intbuffer);
/* 747 */     GL11.glTexParameteri(3553, 10241, i);
/* 748 */     GL11.glTexParameteri(3553, 10240, i);
/* 749 */     GL11.glTexParameteri(3553, 10242, j);
/* 750 */     GL11.glTexParameteri(3553, 10243, j);
/* 751 */     intbuffer.put(src, k * 2, k).position(0).limit(k);
/* 752 */     GlStateManager.bindTexture(multiTex.spec);
/* 753 */     GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, intbuffer);
/* 754 */     GL11.glTexParameteri(3553, 10241, i);
/* 755 */     GL11.glTexParameteri(3553, 10240, i);
/* 756 */     GL11.glTexParameteri(3553, 10242, j);
/* 757 */     GL11.glTexParameteri(3553, 10243, j);
/* 758 */     GlStateManager.bindTexture(multiTex.base);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateSubImage(MultiTexID multiTex, int[] src, int width, int height, int posX, int posY, boolean linear, boolean clamp) {
/* 763 */     int i = width * height;
/* 764 */     IntBuffer intbuffer = getIntBuffer(i);
/* 765 */     intbuffer.clear();
/* 766 */     intbuffer.put(src, 0, i);
/* 767 */     intbuffer.position(0).limit(i);
/* 768 */     GlStateManager.bindTexture(multiTex.base);
/* 769 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 770 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 771 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 772 */     GL11.glTexParameteri(3553, 10243, 10497);
/* 773 */     GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intbuffer);
/*     */     
/* 775 */     if (src.length == i * 3) {
/*     */       
/* 777 */       intbuffer.clear();
/* 778 */       intbuffer.put(src, i, i).position(0);
/* 779 */       intbuffer.position(0).limit(i);
/*     */     } 
/*     */     
/* 782 */     GlStateManager.bindTexture(multiTex.norm);
/* 783 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 784 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 785 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 786 */     GL11.glTexParameteri(3553, 10243, 10497);
/* 787 */     GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intbuffer);
/*     */     
/* 789 */     if (src.length == i * 3) {
/*     */       
/* 791 */       intbuffer.clear();
/* 792 */       intbuffer.put(src, i * 2, i);
/* 793 */       intbuffer.position(0).limit(i);
/*     */     } 
/*     */     
/* 796 */     GlStateManager.bindTexture(multiTex.spec);
/* 797 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 798 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 799 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 800 */     GL11.glTexParameteri(3553, 10243, 10497);
/* 801 */     GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intbuffer);
/* 802 */     GlStateManager.setActiveTexture(33984);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ResourceLocation getNSMapLocation(ResourceLocation location, String mapName) {
/* 807 */     if (location == null)
/*     */     {
/* 809 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 813 */     String s = location.getResourcePath();
/* 814 */     String[] astring = s.split(".png");
/* 815 */     String s1 = astring[0];
/* 816 */     return new ResourceLocation(location.getResourceDomain(), s1 + "_" + mapName + ".png");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void loadNSMap(IResourceManager manager, ResourceLocation location, int width, int height, int[] aint) {
/* 822 */     if (Shaders.configNormalMap)
/*     */     {
/* 824 */       loadNSMap1(manager, getNSMapLocation(location, "n"), width, height, aint, width * height, -8421377);
/*     */     }
/*     */     
/* 827 */     if (Shaders.configSpecularMap)
/*     */     {
/* 829 */       loadNSMap1(manager, getNSMapLocation(location, "s"), width, height, aint, width * height * 2, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadNSMap1(IResourceManager manager, ResourceLocation location, int width, int height, int[] aint, int offset, int defaultColor) {
/* 835 */     if (!loadNSMapFile(manager, location, width, height, aint, offset))
/*     */     {
/* 837 */       Arrays.fill(aint, offset, offset + width * height, defaultColor);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean loadNSMapFile(IResourceManager manager, ResourceLocation location, int width, int height, int[] aint, int offset) {
/* 843 */     if (location == null)
/*     */     {
/* 845 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 851 */       IResource iresource = manager.getResource(location);
/* 852 */       BufferedImage bufferedimage = ImageIO.read(iresource.getInputStream());
/*     */       
/* 854 */       if (bufferedimage == null)
/*     */       {
/* 856 */         return false;
/*     */       }
/* 858 */       if (bufferedimage.getWidth() == width && bufferedimage.getHeight() == height) {
/*     */         
/* 860 */         bufferedimage.getRGB(0, 0, width, height, aint, offset, width);
/* 861 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 865 */       return false;
/*     */     
/*     */     }
/* 868 */     catch (IOException var8) {
/*     */       
/* 870 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int loadSimpleTexture(int textureID, BufferedImage bufferedimage, boolean linear, boolean clamp, IResourceManager resourceManager, ResourceLocation location, MultiTexID multiTex) {
/* 877 */     int i = bufferedimage.getWidth();
/* 878 */     int j = bufferedimage.getHeight();
/* 879 */     int k = i * j;
/* 880 */     int[] aint = getIntArray(k * 3);
/* 881 */     bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
/* 882 */     loadNSMap(resourceManager, location, i, j, aint);
/* 883 */     setupTexture(multiTex, aint, i, j, linear, clamp);
/* 884 */     return textureID;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void mergeImage(int[] aint, int dstoff, int srcoff, int size) {}
/*     */ 
/*     */   
/*     */   public static int blendColor(int color1, int color2, int factor1) {
/* 893 */     int i = 255 - factor1;
/* 894 */     return ((color1 >>> 24 & 0xFF) * factor1 + (color2 >>> 24 & 0xFF) * i) / 255 << 24 | ((color1 >>> 16 & 0xFF) * factor1 + (color2 >>> 16 & 0xFF) * i) / 255 << 16 | ((color1 >>> 8 & 0xFF) * factor1 + (color2 >>> 8 & 0xFF) * i) / 255 << 8 | ((color1 >>> 0 & 0xFF) * factor1 + (color2 >>> 0 & 0xFF) * i) / 255 << 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadLayeredTexture(LayeredTexture tex, IResourceManager manager, List list) {
/* 899 */     int i = 0;
/* 900 */     int j = 0;
/* 901 */     int k = 0;
/* 902 */     int[] aint = null;
/*     */     
/* 904 */     for (Object e : list) {
/*     */       
/* 906 */       String s = (String)e;
/* 907 */       if (s != null) {
/*     */         
/*     */         try {
/*     */           
/* 911 */           ResourceLocation resourcelocation = new ResourceLocation(s);
/* 912 */           InputStream inputstream = manager.getResource(resourcelocation).getInputStream();
/* 913 */           BufferedImage bufferedimage = ImageIO.read(inputstream);
/*     */           
/* 915 */           if (k == 0) {
/*     */             
/* 917 */             i = bufferedimage.getWidth();
/* 918 */             j = bufferedimage.getHeight();
/* 919 */             k = i * j;
/* 920 */             aint = createAIntImage(k, 0);
/*     */           } 
/*     */           
/* 923 */           int[] aint1 = getIntArray(k * 3);
/* 924 */           bufferedimage.getRGB(0, 0, i, j, aint1, 0, i);
/* 925 */           loadNSMap(manager, resourcelocation, i, j, aint1);
/*     */           
/* 927 */           for (int l = 0; l < k; l++)
/*     */           {
/* 929 */             int i1 = aint1[l] >>> 24 & 0xFF;
/* 930 */             aint[k * 0 + l] = blendColor(aint1[k * 0 + l], aint[k * 0 + l], i1);
/* 931 */             aint[k * 1 + l] = blendColor(aint1[k * 1 + l], aint[k * 1 + l], i1);
/* 932 */             aint[k * 2 + l] = blendColor(aint1[k * 2 + l], aint[k * 2 + l], i1);
/*     */           }
/*     */         
/* 935 */         } catch (IOException ioexception) {
/*     */           
/* 937 */           ioexception.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 942 */     setupTexture(tex.getMultiTexID(), aint, i, j, false, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateTextureMinMagFilter() {
/* 947 */     TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
/* 948 */     ITextureObject itextureobject = texturemanager.getTexture(TextureMap.locationBlocksTexture);
/*     */     
/* 950 */     if (itextureobject != null) {
/*     */       
/* 952 */       MultiTexID multitexid = itextureobject.getMultiTexID();
/* 953 */       GlStateManager.bindTexture(multitexid.base);
/* 954 */       GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilB]);
/* 955 */       GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilB]);
/* 956 */       GlStateManager.bindTexture(multitexid.norm);
/* 957 */       GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilN]);
/* 958 */       GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilN]);
/* 959 */       GlStateManager.bindTexture(multitexid.spec);
/* 960 */       GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilS]);
/* 961 */       GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilS]);
/* 962 */       GlStateManager.bindTexture(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[][] getFrameTexData(int[][] src, int width, int height, int frameIndex) {
/* 968 */     int i = src.length;
/* 969 */     int[][] aint = new int[i][];
/*     */     
/* 971 */     for (int j = 0; j < i; j++) {
/*     */       
/* 973 */       int[] aint1 = src[j];
/*     */       
/* 975 */       if (aint1 != null) {
/*     */         
/* 977 */         int k = (width >> j) * (height >> j);
/* 978 */         int[] aint2 = new int[k * 3];
/* 979 */         aint[j] = aint2;
/* 980 */         int l = aint1.length / 3;
/* 981 */         int i1 = k * frameIndex;
/* 982 */         int j1 = 0;
/* 983 */         System.arraycopy(aint1, i1, aint2, j1, k);
/* 984 */         i1 += l;
/* 985 */         j1 += k;
/* 986 */         System.arraycopy(aint1, i1, aint2, j1, k);
/* 987 */         i1 += l;
/* 988 */         j1 += k;
/* 989 */         System.arraycopy(aint1, i1, aint2, j1, k);
/*     */       } 
/*     */     } 
/*     */     
/* 993 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[][] prepareAF(TextureAtlasSprite tas, int[][] src, int width, int height) {
/* 998 */     boolean flag = true;
/* 999 */     return src;
/*     */   }
/*     */   
/*     */   public static void fixTransparentColor(TextureAtlasSprite tas, int[] aint) {}
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\ShadersTex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ 
/*     */ 
/*     */ public class EmissiveTextures
/*     */ {
/*  18 */   private static String suffixEmissive = null;
/*  19 */   private static String suffixEmissivePng = null;
/*     */   private static boolean active = false;
/*     */   private static boolean render = false;
/*     */   private static boolean hasEmissive = false;
/*     */   private static boolean renderEmissive = false;
/*     */   private static float lightMapX;
/*     */   private static float lightMapY;
/*     */   private static final String SUFFIX_PNG = ".png";
/*  27 */   private static final ResourceLocation LOCATION_EMPTY = new ResourceLocation("mcpatcher/ctm/default/empty.png");
/*     */ 
/*     */   
/*     */   public static boolean isActive() {
/*  31 */     return active;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getSuffixEmissive() {
/*  36 */     return suffixEmissive;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginRender() {
/*  41 */     render = true;
/*  42 */     hasEmissive = false;
/*     */   }
/*     */   
/*     */   public static ITextureObject getEmissiveTexture(ITextureObject texture, Map<ResourceLocation, ITextureObject> mapTextures) {
/*     */     SimpleTexture simpleTexture1;
/*  47 */     if (!render)
/*     */     {
/*  49 */       return texture;
/*     */     }
/*  51 */     if (!(texture instanceof SimpleTexture))
/*     */     {
/*  53 */       return texture;
/*     */     }
/*     */ 
/*     */     
/*  57 */     SimpleTexture simpletexture = (SimpleTexture)texture;
/*  58 */     ResourceLocation resourcelocation = simpletexture.locationEmissive;
/*     */     
/*  60 */     if (!renderEmissive) {
/*     */       
/*  62 */       if (resourcelocation != null)
/*     */       {
/*  64 */         hasEmissive = true;
/*     */       }
/*     */       
/*  67 */       return texture;
/*     */     } 
/*     */ 
/*     */     
/*  71 */     if (resourcelocation == null)
/*     */     {
/*  73 */       resourcelocation = LOCATION_EMPTY;
/*     */     }
/*     */     
/*  76 */     ITextureObject itextureobject = mapTextures.get(resourcelocation);
/*     */     
/*  78 */     if (itextureobject == null) {
/*     */       
/*  80 */       simpleTexture1 = new SimpleTexture(resourcelocation);
/*  81 */       TextureManager texturemanager = Config.getTextureManager();
/*  82 */       texturemanager.loadTexture(resourcelocation, (ITextureObject)simpleTexture1);
/*     */     } 
/*     */     
/*  85 */     return (ITextureObject)simpleTexture1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasEmissive() {
/*  92 */     return hasEmissive;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginRenderEmissive() {
/*  97 */     lightMapX = OpenGlHelper.lastBrightnessX;
/*  98 */     lightMapY = OpenGlHelper.lastBrightnessY;
/*  99 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, lightMapY);
/* 100 */     renderEmissive = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endRenderEmissive() {
/* 105 */     renderEmissive = false;
/* 106 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightMapX, lightMapY);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endRender() {
/* 111 */     render = false;
/* 112 */     hasEmissive = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update() {
/* 117 */     active = false;
/* 118 */     suffixEmissive = null;
/* 119 */     suffixEmissivePng = null;
/*     */     
/* 121 */     if (Config.isEmissiveTextures()) {
/*     */       
/*     */       try {
/*     */         
/* 125 */         String s = "optifine/emissive.properties";
/* 126 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/* 127 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */         
/* 129 */         if (inputstream == null) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 134 */         dbg("Loading " + s);
/* 135 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 136 */         propertiesOrdered.load(inputstream);
/* 137 */         inputstream.close();
/* 138 */         suffixEmissive = propertiesOrdered.getProperty("suffix.emissive");
/*     */         
/* 140 */         if (suffixEmissive != null)
/*     */         {
/* 142 */           suffixEmissivePng = suffixEmissive + ".png";
/*     */         }
/*     */         
/* 145 */         active = (suffixEmissive != null);
/*     */       }
/* 147 */       catch (FileNotFoundException var4) {
/*     */         
/*     */         return;
/*     */       }
/* 151 */       catch (IOException ioexception) {
/*     */         
/* 153 */         ioexception.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void dbg(String str) {
/* 160 */     Config.dbg("EmissiveTextures: " + str);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void warn(String str) {
/* 165 */     Config.warn("EmissiveTextures: " + str);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isEmissive(ResourceLocation loc) {
/* 170 */     return (suffixEmissivePng == null) ? false : loc.getResourcePath().endsWith(suffixEmissivePng);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadTexture(ResourceLocation loc, SimpleTexture tex) {
/* 175 */     if (loc != null && tex != null) {
/*     */       
/* 177 */       tex.isEmissive = false;
/* 178 */       tex.locationEmissive = null;
/*     */       
/* 180 */       if (suffixEmissivePng != null) {
/*     */         
/* 182 */         String s = loc.getResourcePath();
/*     */         
/* 184 */         if (s.endsWith(".png"))
/*     */         {
/* 186 */           if (s.endsWith(suffixEmissivePng)) {
/*     */             
/* 188 */             tex.isEmissive = true;
/*     */           }
/*     */           else {
/*     */             
/* 192 */             String s1 = s.substring(0, s.length() - ".png".length()) + suffixEmissivePng;
/* 193 */             ResourceLocation resourcelocation = new ResourceLocation(loc.getResourceDomain(), s1);
/*     */             
/* 195 */             if (Config.hasResource(resourcelocation))
/*     */             {
/* 197 */               tex.locationEmissive = resourcelocation;
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\EmissiveTextures.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
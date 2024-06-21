/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.render.Blender;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ public class CustomSky
/*     */ {
/*  20 */   private static CustomSkyLayer[][] worldSkyLayers = (CustomSkyLayer[][])null;
/*     */ 
/*     */   
/*     */   public static void reset() {
/*  24 */     worldSkyLayers = (CustomSkyLayer[][])null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update() {
/*  29 */     reset();
/*     */     
/*  31 */     if (Config.isCustomSky())
/*     */     {
/*  33 */       worldSkyLayers = readCustomSkies();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static CustomSkyLayer[][] readCustomSkies() {
/*  39 */     CustomSkyLayer[][] acustomskylayer = new CustomSkyLayer[10][0];
/*  40 */     String s = "mcpatcher/sky/world";
/*  41 */     int i = -1;
/*     */     
/*  43 */     for (int j = 0; j < acustomskylayer.length; j++) {
/*     */       
/*  45 */       String s1 = s + j + "/sky";
/*  46 */       List<CustomSkyLayer> list = new ArrayList();
/*     */       
/*  48 */       for (int k = 1; k < 1000; k++) {
/*     */         
/*  50 */         String s2 = s1 + k + ".properties";
/*     */ 
/*     */         
/*     */         try {
/*  54 */           ResourceLocation resourcelocation = new ResourceLocation(s2);
/*  55 */           InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */           
/*  57 */           if (inputstream == null) {
/*     */             break;
/*     */           }
/*     */ 
/*     */           
/*  62 */           PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  63 */           propertiesOrdered.load(inputstream);
/*  64 */           inputstream.close();
/*  65 */           Config.dbg("CustomSky properties: " + s2);
/*  66 */           String s3 = s1 + k + ".png";
/*  67 */           CustomSkyLayer customskylayer = new CustomSkyLayer((Properties)propertiesOrdered, s3);
/*     */           
/*  69 */           if (customskylayer.isValid(s2)) {
/*     */             
/*  71 */             ResourceLocation resourcelocation1 = new ResourceLocation(customskylayer.source);
/*  72 */             ITextureObject itextureobject = TextureUtils.getTexture(resourcelocation1);
/*     */             
/*  74 */             if (itextureobject == null)
/*     */             {
/*  76 */               Config.log("CustomSky: Texture not found: " + resourcelocation1);
/*     */             }
/*     */             else
/*     */             {
/*  80 */               customskylayer.textureId = itextureobject.getGlTextureId();
/*  81 */               list.add(customskylayer);
/*  82 */               inputstream.close();
/*     */             }
/*     */           
/*     */           } 
/*  86 */         } catch (FileNotFoundException var15) {
/*     */           
/*     */           break;
/*     */         }
/*  90 */         catch (IOException ioexception) {
/*     */           
/*  92 */           ioexception.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/*  96 */       if (list.size() > 0) {
/*     */         
/*  98 */         CustomSkyLayer[] acustomskylayer2 = list.<CustomSkyLayer>toArray(new CustomSkyLayer[list.size()]);
/*  99 */         acustomskylayer[j] = acustomskylayer2;
/* 100 */         i = j;
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     if (i < 0)
/*     */     {
/* 106 */       return (CustomSkyLayer[][])null;
/*     */     }
/*     */ 
/*     */     
/* 110 */     int l = i + 1;
/* 111 */     CustomSkyLayer[][] acustomskylayer1 = new CustomSkyLayer[l][0];
/*     */     
/* 113 */     for (int i1 = 0; i1 < acustomskylayer1.length; i1++)
/*     */     {
/* 115 */       acustomskylayer1[i1] = acustomskylayer[i1];
/*     */     }
/*     */     
/* 118 */     return acustomskylayer1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderSky(World world, TextureManager re, float partialTicks) {
/* 124 */     if (worldSkyLayers != null) {
/*     */       
/* 126 */       int i = world.provider.getDimensionId();
/*     */       
/* 128 */       if (i >= 0 && i < worldSkyLayers.length) {
/*     */         
/* 130 */         CustomSkyLayer[] acustomskylayer = worldSkyLayers[i];
/*     */         
/* 132 */         if (acustomskylayer != null) {
/*     */           
/* 134 */           long j = world.getWorldTime();
/* 135 */           int k = (int)(j % 24000L);
/* 136 */           float f = world.getCelestialAngle(partialTicks);
/* 137 */           float f1 = world.getRainStrength(partialTicks);
/* 138 */           float f2 = world.getThunderStrength(partialTicks);
/*     */           
/* 140 */           if (f1 > 0.0F)
/*     */           {
/* 142 */             f2 /= f1;
/*     */           }
/*     */           
/* 145 */           for (int l = 0; l < acustomskylayer.length; l++) {
/*     */             
/* 147 */             CustomSkyLayer customskylayer = acustomskylayer[l];
/*     */             
/* 149 */             if (customskylayer.isActive(world, k))
/*     */             {
/* 151 */               customskylayer.render(world, k, f, f1, f2);
/*     */             }
/*     */           } 
/*     */           
/* 155 */           float f3 = 1.0F - f1;
/* 156 */           Blender.clearBlend(f3);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean hasSkyLayers(World world) {
/* 164 */     if (worldSkyLayers == null)
/*     */     {
/* 166 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 170 */     int i = world.provider.getDimensionId();
/*     */     
/* 172 */     if (i >= 0 && i < worldSkyLayers.length) {
/*     */       
/* 174 */       CustomSkyLayer[] acustomskylayer = worldSkyLayers[i];
/* 175 */       return (acustomskylayer == null) ? false : ((acustomskylayer.length > 0));
/*     */     } 
/*     */ 
/*     */     
/* 179 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\CustomSky.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Random;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.util.MathUtils;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ 
/*     */ public class CustomPanorama
/*     */ {
/*  16 */   private static CustomPanoramaProperties customPanoramaProperties = null;
/*  17 */   private static final Random random = new Random();
/*     */ 
/*     */   
/*     */   public static CustomPanoramaProperties getCustomPanoramaProperties() {
/*  21 */     return customPanoramaProperties;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update() {
/*  26 */     customPanoramaProperties = null;
/*  27 */     String[] astring = getPanoramaFolders();
/*     */     
/*  29 */     if (astring.length > 1) {
/*     */       PropertiesOrdered propertiesOrdered;
/*  31 */       Properties[] aproperties = getPanoramaProperties(astring);
/*  32 */       int[] aint = getWeights(aproperties);
/*  33 */       int i = getRandomIndex(aint);
/*  34 */       String s = astring[i];
/*  35 */       Properties properties = aproperties[i];
/*     */       
/*  37 */       if (properties == null)
/*     */       {
/*  39 */         properties = aproperties[0];
/*     */       }
/*     */       
/*  42 */       if (properties == null)
/*     */       {
/*  44 */         propertiesOrdered = new PropertiesOrdered();
/*     */       }
/*     */       
/*  47 */       CustomPanoramaProperties custompanoramaproperties = new CustomPanoramaProperties(s, (Properties)propertiesOrdered);
/*  48 */       customPanoramaProperties = custompanoramaproperties;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String[] getPanoramaFolders() {
/*  54 */     List<String> list = new ArrayList<>();
/*  55 */     list.add("textures/gui/title/background");
/*     */     
/*  57 */     for (int i = 0; i < 100; i++) {
/*     */       
/*  59 */       String s = "optifine/gui/background" + i;
/*  60 */       String s1 = s + "/panorama_0.png";
/*  61 */       ResourceLocation resourcelocation = new ResourceLocation(s1);
/*     */       
/*  63 */       if (Config.hasResource(resourcelocation))
/*     */       {
/*  65 */         list.add(s);
/*     */       }
/*     */     } 
/*     */     
/*  69 */     String[] astring = list.<String>toArray(new String[list.size()]);
/*  70 */     return astring;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Properties[] getPanoramaProperties(String[] folders) {
/*  75 */     Properties[] aproperties = new Properties[folders.length];
/*     */     
/*  77 */     for (int i = 0; i < folders.length; i++) {
/*     */       
/*  79 */       String s = folders[i];
/*     */       
/*  81 */       if (i == 0) {
/*     */         
/*  83 */         s = "optifine/gui";
/*     */       }
/*     */       else {
/*     */         
/*  87 */         Config.dbg("CustomPanorama: " + s);
/*     */       } 
/*     */       
/*  90 */       ResourceLocation resourcelocation = new ResourceLocation(s + "/background.properties");
/*     */ 
/*     */       
/*     */       try {
/*  94 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */         
/*  96 */         if (inputstream != null)
/*     */         {
/*  98 */           PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  99 */           propertiesOrdered.load(inputstream);
/* 100 */           Config.dbg("CustomPanorama: " + resourcelocation.getResourcePath());
/* 101 */           aproperties[i] = (Properties)propertiesOrdered;
/* 102 */           inputstream.close();
/*     */         }
/*     */       
/* 105 */       } catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     return aproperties;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] getWeights(Properties[] propertiess) {
/* 116 */     int[] aint = new int[propertiess.length];
/*     */     
/* 118 */     for (int i = 0; i < aint.length; i++) {
/*     */       
/* 120 */       Properties properties = propertiess[i];
/*     */       
/* 122 */       if (properties == null)
/*     */       {
/* 124 */         properties = propertiess[0];
/*     */       }
/*     */       
/* 127 */       if (properties == null) {
/*     */         
/* 129 */         aint[i] = 1;
/*     */       }
/*     */       else {
/*     */         
/* 133 */         String s = properties.getProperty("weight", null);
/* 134 */         aint[i] = Config.parseInt(s, 1);
/*     */       } 
/*     */     } 
/*     */     
/* 138 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getRandomIndex(int[] weights) {
/* 143 */     int i = MathUtils.getSum(weights);
/* 144 */     int j = random.nextInt(i);
/* 145 */     int k = 0;
/*     */     
/* 147 */     for (int l = 0; l < weights.length; l++) {
/*     */       
/* 149 */       k += weights[l];
/*     */       
/* 151 */       if (k > j)
/*     */       {
/* 153 */         return l;
/*     */       }
/*     */     } 
/*     */     
/* 157 */     return weights.length - 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\CustomPanorama.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
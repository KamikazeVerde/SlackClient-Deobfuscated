/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class FontUtils
/*     */ {
/*     */   public static Properties readFontProperties(ResourceLocation locationFontTexture) {
/*  14 */     String s = locationFontTexture.getResourcePath();
/*  15 */     Properties properties = new PropertiesOrdered();
/*  16 */     String s1 = ".png";
/*     */     
/*  18 */     if (!s.endsWith(s1))
/*     */     {
/*  20 */       return properties;
/*     */     }
/*     */ 
/*     */     
/*  24 */     String s2 = s.substring(0, s.length() - s1.length()) + ".properties";
/*     */ 
/*     */     
/*     */     try {
/*  28 */       ResourceLocation resourcelocation = new ResourceLocation(locationFontTexture.getResourceDomain(), s2);
/*  29 */       InputStream inputstream = Config.getResourceStream(Config.getResourceManager(), resourcelocation);
/*     */       
/*  31 */       if (inputstream == null)
/*     */       {
/*  33 */         return properties;
/*     */       }
/*     */       
/*  36 */       Config.log("Loading " + s2);
/*  37 */       properties.load(inputstream);
/*     */     }
/*  39 */     catch (FileNotFoundException fileNotFoundException) {
/*     */ 
/*     */     
/*     */     }
/*  43 */     catch (IOException ioexception) {
/*     */       
/*  45 */       ioexception.printStackTrace();
/*     */     } 
/*     */     
/*  48 */     return properties;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void readCustomCharWidths(Properties props, float[] charWidth) {
/*  54 */     for (Object e : props.keySet()) {
/*     */       
/*  56 */       String s = (String)e;
/*  57 */       String s1 = "width.";
/*     */       
/*  59 */       if (s.startsWith(s1)) {
/*     */         
/*  61 */         String s2 = s.substring(s1.length());
/*  62 */         int i = Config.parseInt(s2, -1);
/*     */         
/*  64 */         if (i >= 0 && i < charWidth.length) {
/*     */           
/*  66 */           String s3 = props.getProperty(s);
/*  67 */           float f = Config.parseFloat(s3, -1.0F);
/*     */           
/*  69 */           if (f >= 0.0F)
/*     */           {
/*  71 */             charWidth[i] = f;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static float readFloat(Properties props, String key, float defOffset) {
/*  80 */     String s = props.getProperty(key);
/*     */     
/*  82 */     if (s == null)
/*     */     {
/*  84 */       return defOffset;
/*     */     }
/*     */ 
/*     */     
/*  88 */     float f = Config.parseFloat(s, Float.MIN_VALUE);
/*     */     
/*  90 */     if (f == Float.MIN_VALUE) {
/*     */       
/*  92 */       Config.warn("Invalid value for " + key + ": " + s);
/*  93 */       return defOffset;
/*     */     } 
/*     */ 
/*     */     
/*  97 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean readBoolean(Properties props, String key, boolean defVal) {
/* 104 */     String s = props.getProperty(key);
/*     */     
/* 106 */     if (s == null)
/*     */     {
/* 108 */       return defVal;
/*     */     }
/*     */ 
/*     */     
/* 112 */     String s1 = s.toLowerCase().trim();
/*     */     
/* 114 */     if (!s1.equals("true") && !s1.equals("on")) {
/*     */       
/* 116 */       if (!s1.equals("false") && !s1.equals("off")) {
/*     */         
/* 118 */         Config.warn("Invalid value for " + key + ": " + s);
/* 119 */         return defVal;
/*     */       } 
/*     */ 
/*     */       
/* 123 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 128 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceLocation getHdFontLocation(ResourceLocation fontLoc) {
/* 135 */     if (!Config.isCustomFonts())
/*     */     {
/* 137 */       return fontLoc;
/*     */     }
/* 139 */     if (fontLoc == null)
/*     */     {
/* 141 */       return fontLoc;
/*     */     }
/* 143 */     if (!Config.isMinecraftThread())
/*     */     {
/* 145 */       return fontLoc;
/*     */     }
/*     */ 
/*     */     
/* 149 */     String s = fontLoc.getResourcePath();
/* 150 */     String s1 = "textures/";
/* 151 */     String s2 = "mcpatcher/";
/*     */     
/* 153 */     if (!s.startsWith(s1))
/*     */     {
/* 155 */       return fontLoc;
/*     */     }
/*     */ 
/*     */     
/* 159 */     s = s.substring(s1.length());
/* 160 */     s = s2 + s;
/* 161 */     ResourceLocation resourcelocation = new ResourceLocation(fontLoc.getResourceDomain(), s);
/* 162 */     return Config.hasResource(Config.getResourceManager(), resourcelocation) ? resourcelocation : fontLoc;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifin\\util\FontUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
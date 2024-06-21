/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Enumeration;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import net.minecraft.client.resources.AbstractResourcePack;
/*     */ import net.minecraft.client.resources.IResourcePack;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResUtils
/*     */ {
/*     */   public static String[] collectFiles(String prefix, String suffix) {
/*  27 */     return collectFiles(new String[] { prefix }, new String[] { suffix });
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] collectFiles(String[] prefixes, String[] suffixes) {
/*  32 */     Set<String> set = new LinkedHashSet<>();
/*  33 */     IResourcePack[] airesourcepack = Config.getResourcePacks();
/*     */     
/*  35 */     for (int i = 0; i < airesourcepack.length; i++) {
/*     */       
/*  37 */       IResourcePack iresourcepack = airesourcepack[i];
/*  38 */       String[] astring = collectFiles(iresourcepack, prefixes, suffixes, (String[])null);
/*  39 */       set.addAll(Arrays.asList(astring));
/*     */     } 
/*     */     
/*  42 */     String[] astring1 = set.<String>toArray(new String[set.size()]);
/*  43 */     return astring1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] collectFiles(IResourcePack rp, String prefix, String suffix, String[] defaultPaths) {
/*  48 */     return collectFiles(rp, new String[] { prefix }, new String[] { suffix }, defaultPaths);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] collectFiles(IResourcePack rp, String[] prefixes, String[] suffixes) {
/*  53 */     return collectFiles(rp, prefixes, suffixes, (String[])null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] collectFiles(IResourcePack rp, String[] prefixes, String[] suffixes, String[] defaultPaths) {
/*  58 */     if (rp instanceof net.minecraft.client.resources.DefaultResourcePack)
/*     */     {
/*  60 */       return collectFilesFixed(rp, defaultPaths);
/*     */     }
/*  62 */     if (!(rp instanceof AbstractResourcePack)) {
/*     */       
/*  64 */       Config.warn("Unknown resource pack type: " + rp);
/*  65 */       return new String[0];
/*     */     } 
/*     */ 
/*     */     
/*  69 */     AbstractResourcePack abstractresourcepack = (AbstractResourcePack)rp;
/*  70 */     File file1 = abstractresourcepack.resourcePackFile;
/*     */     
/*  72 */     if (file1 == null)
/*     */     {
/*  74 */       return new String[0];
/*     */     }
/*  76 */     if (file1.isDirectory())
/*     */     {
/*  78 */       return collectFilesFolder(file1, "", prefixes, suffixes);
/*     */     }
/*  80 */     if (file1.isFile())
/*     */     {
/*  82 */       return collectFilesZIP(file1, prefixes, suffixes);
/*     */     }
/*     */ 
/*     */     
/*  86 */     Config.warn("Unknown resource pack file: " + file1);
/*  87 */     return new String[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] collectFilesFixed(IResourcePack rp, String[] paths) {
/*  94 */     if (paths == null)
/*     */     {
/*  96 */       return new String[0];
/*     */     }
/*     */ 
/*     */     
/* 100 */     List<String> list = new ArrayList();
/*     */     
/* 102 */     for (int i = 0; i < paths.length; i++) {
/*     */       
/* 104 */       String s = paths[i];
/* 105 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/*     */       
/* 107 */       if (rp.resourceExists(resourcelocation))
/*     */       {
/* 109 */         list.add(s);
/*     */       }
/*     */     } 
/*     */     
/* 113 */     String[] astring = list.<String>toArray(new String[list.size()]);
/* 114 */     return astring;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] collectFilesFolder(File tpFile, String basePath, String[] prefixes, String[] suffixes) {
/* 120 */     List<String> list = new ArrayList();
/* 121 */     String s = "assets/minecraft/";
/* 122 */     File[] afile = tpFile.listFiles();
/*     */     
/* 124 */     if (afile == null)
/*     */     {
/* 126 */       return new String[0];
/*     */     }
/*     */ 
/*     */     
/* 130 */     for (int i = 0; i < afile.length; i++) {
/*     */       
/* 132 */       File file1 = afile[i];
/*     */       
/* 134 */       if (file1.isFile()) {
/*     */         
/* 136 */         String s3 = basePath + file1.getName();
/*     */         
/* 138 */         if (s3.startsWith(s))
/*     */         {
/* 140 */           s3 = s3.substring(s.length());
/*     */           
/* 142 */           if (StrUtils.startsWith(s3, prefixes) && StrUtils.endsWith(s3, suffixes))
/*     */           {
/* 144 */             list.add(s3);
/*     */           }
/*     */         }
/*     */       
/* 148 */       } else if (file1.isDirectory()) {
/*     */         
/* 150 */         String s1 = basePath + file1.getName() + "/";
/* 151 */         String[] astring = collectFilesFolder(file1, s1, prefixes, suffixes);
/*     */         
/* 153 */         for (int j = 0; j < astring.length; j++) {
/*     */           
/* 155 */           String s2 = astring[j];
/* 156 */           list.add(s2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 161 */     String[] astring1 = list.<String>toArray(new String[list.size()]);
/* 162 */     return astring1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] collectFilesZIP(File tpFile, String[] prefixes, String[] suffixes) {
/* 168 */     List<String> list = new ArrayList();
/* 169 */     String s = "assets/minecraft/";
/*     */ 
/*     */     
/*     */     try {
/* 173 */       ZipFile zipfile = new ZipFile(tpFile);
/* 174 */       Enumeration<? extends ZipEntry> enumeration = zipfile.entries();
/*     */       
/* 176 */       while (enumeration.hasMoreElements()) {
/*     */         
/* 178 */         ZipEntry zipentry = enumeration.nextElement();
/* 179 */         String s1 = zipentry.getName();
/*     */         
/* 181 */         if (s1.startsWith(s)) {
/*     */           
/* 183 */           s1 = s1.substring(s.length());
/*     */           
/* 185 */           if (StrUtils.startsWith(s1, prefixes) && StrUtils.endsWith(s1, suffixes))
/*     */           {
/* 187 */             list.add(s1);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 192 */       zipfile.close();
/* 193 */       String[] astring = list.<String>toArray(new String[list.size()]);
/* 194 */       return astring;
/*     */     }
/* 196 */     catch (IOException ioexception) {
/*     */       
/* 198 */       ioexception.printStackTrace();
/* 199 */       return new String[0];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isLowercase(String str) {
/* 205 */     return str.equals(str.toLowerCase(Locale.ROOT));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Properties readProperties(String path, String module) {
/* 210 */     ResourceLocation resourcelocation = new ResourceLocation(path);
/*     */ 
/*     */     
/*     */     try {
/* 214 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */       
/* 216 */       if (inputstream == null)
/*     */       {
/* 218 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 222 */       Properties properties = new PropertiesOrdered();
/* 223 */       properties.load(inputstream);
/* 224 */       inputstream.close();
/* 225 */       Config.dbg("" + module + ": Loading " + path);
/* 226 */       return properties;
/*     */     
/*     */     }
/* 229 */     catch (FileNotFoundException var5) {
/*     */       
/* 231 */       return null;
/*     */     }
/* 233 */     catch (IOException var6) {
/*     */       
/* 235 */       Config.warn("" + module + ": Error reading " + path);
/* 236 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Properties readProperties(InputStream in, String module) {
/* 242 */     if (in == null)
/*     */     {
/* 244 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 250 */       Properties properties = new PropertiesOrdered();
/* 251 */       properties.load(in);
/* 252 */       in.close();
/* 253 */       return properties;
/*     */     }
/* 255 */     catch (FileNotFoundException var3) {
/*     */       
/* 257 */       return null;
/*     */     }
/* 259 */     catch (IOException var4) {
/*     */       
/* 261 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifin\\util\ResUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
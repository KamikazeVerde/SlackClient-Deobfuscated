/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import java.util.Enumeration;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ public class ShaderPackZip
/*     */   implements IShaderPack
/*     */ {
/*     */   protected File packFile;
/*     */   protected ZipFile packZipFile;
/*     */   protected String baseFolder;
/*     */   
/*     */   public ShaderPackZip(String name, File file) {
/*  25 */     this.packFile = file;
/*  26 */     this.packZipFile = null;
/*  27 */     this.baseFolder = "";
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*  32 */     if (this.packZipFile != null) {
/*     */ 
/*     */       
/*     */       try {
/*  36 */         this.packZipFile.close();
/*     */       }
/*  38 */       catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  43 */       this.packZipFile = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getResourceAsStream(String resName) {
/*     */     try {
/*  51 */       if (this.packZipFile == null) {
/*     */         
/*  53 */         this.packZipFile = new ZipFile(this.packFile);
/*  54 */         this.baseFolder = detectBaseFolder(this.packZipFile);
/*     */       } 
/*     */       
/*  57 */       String s = StrUtils.removePrefix(resName, "/");
/*     */       
/*  59 */       if (s.contains(".."))
/*     */       {
/*  61 */         s = resolveRelative(s);
/*     */       }
/*     */       
/*  64 */       ZipEntry zipentry = this.packZipFile.getEntry(this.baseFolder + s);
/*  65 */       return (zipentry == null) ? null : this.packZipFile.getInputStream(zipentry);
/*     */     }
/*  67 */     catch (Exception var4) {
/*     */       
/*  69 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String resolveRelative(String name) {
/*  75 */     Deque<String> deque = new ArrayDeque<>();
/*  76 */     String[] astring = Config.tokenize(name, "/");
/*     */     
/*  78 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/*  80 */       String s = astring[i];
/*     */       
/*  82 */       if (s.equals("..")) {
/*     */         
/*  84 */         if (deque.isEmpty())
/*     */         {
/*  86 */           return "";
/*     */         }
/*     */         
/*  89 */         deque.removeLast();
/*     */       }
/*     */       else {
/*     */         
/*  93 */         deque.add(s);
/*     */       } 
/*     */     } 
/*     */     
/*  97 */     String s1 = Joiner.on('/').join(deque);
/*  98 */     return s1;
/*     */   }
/*     */ 
/*     */   
/*     */   private String detectBaseFolder(ZipFile zip) {
/* 103 */     ZipEntry zipentry = zip.getEntry("shaders/");
/*     */     
/* 105 */     if (zipentry != null && zipentry.isDirectory())
/*     */     {
/* 107 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 111 */     Pattern pattern = Pattern.compile("([^/]+/)shaders/");
/* 112 */     Enumeration<? extends ZipEntry> enumeration = zip.entries();
/*     */     
/* 114 */     while (enumeration.hasMoreElements()) {
/*     */       
/* 116 */       ZipEntry zipentry1 = enumeration.nextElement();
/* 117 */       String s = zipentry1.getName();
/* 118 */       Matcher matcher = pattern.matcher(s);
/*     */       
/* 120 */       if (matcher.matches()) {
/*     */         
/* 122 */         String s1 = matcher.group(1);
/*     */         
/* 124 */         if (s1 != null) {
/*     */           
/* 126 */           if (s1.equals("shaders/"))
/*     */           {
/* 128 */             return "";
/*     */           }
/*     */           
/* 131 */           return s1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 136 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasDirectory(String resName) {
/*     */     try {
/* 144 */       if (this.packZipFile == null) {
/*     */         
/* 146 */         this.packZipFile = new ZipFile(this.packFile);
/* 147 */         this.baseFolder = detectBaseFolder(this.packZipFile);
/*     */       } 
/*     */       
/* 150 */       String s = StrUtils.removePrefix(resName, "/");
/* 151 */       ZipEntry zipentry = this.packZipFile.getEntry(this.baseFolder + s);
/* 152 */       return (zipentry != null);
/*     */     }
/* 154 */     catch (IOException var4) {
/*     */       
/* 156 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 162 */     return this.packFile.getName();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\ShaderPackZip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
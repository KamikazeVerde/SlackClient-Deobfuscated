/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.StringReader;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ 
/*     */ public class MacroProcessor
/*     */ {
/*     */   public static InputStream process(InputStream in, String path) throws IOException {
/*  20 */     String s = Config.readInputStream(in, "ASCII");
/*  21 */     String s1 = getMacroHeader(s);
/*     */     
/*  23 */     if (!s1.isEmpty()) {
/*     */       
/*  25 */       s = s1 + s;
/*     */       
/*  27 */       if (Shaders.saveFinalShaders) {
/*     */         
/*  29 */         String s2 = path.replace(':', '/') + ".pre";
/*  30 */         Shaders.saveShader(s2, s);
/*     */       } 
/*     */       
/*  33 */       s = process(s);
/*     */     } 
/*     */     
/*  36 */     if (Shaders.saveFinalShaders) {
/*     */       
/*  38 */       String s3 = path.replace(':', '/');
/*  39 */       Shaders.saveShader(s3, s);
/*     */     } 
/*     */     
/*  42 */     byte[] abyte = s.getBytes(StandardCharsets.US_ASCII);
/*  43 */     ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte);
/*  44 */     return bytearrayinputstream;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String process(String strIn) throws IOException {
/*  49 */     StringReader stringreader = new StringReader(strIn);
/*  50 */     BufferedReader bufferedreader = new BufferedReader(stringreader);
/*  51 */     MacroState macrostate = new MacroState();
/*  52 */     StringBuilder stringbuilder = new StringBuilder();
/*     */ 
/*     */     
/*     */     while (true) {
/*  56 */       String s = bufferedreader.readLine();
/*     */       
/*  58 */       if (s == null) {
/*     */         
/*  60 */         s = stringbuilder.toString();
/*  61 */         return s;
/*     */       } 
/*     */       
/*  64 */       if (macrostate.processLine(s) && !MacroState.isMacroLine(s)) {
/*     */         
/*  66 */         stringbuilder.append(s);
/*  67 */         stringbuilder.append("\n");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getMacroHeader(String str) throws IOException {
/*  74 */     StringBuilder stringbuilder = new StringBuilder();
/*  75 */     List<ShaderOption> list = null;
/*  76 */     List<ShaderMacro> list1 = null;
/*  77 */     StringReader stringreader = new StringReader(str);
/*  78 */     BufferedReader bufferedreader = new BufferedReader(stringreader);
/*     */ 
/*     */     
/*     */     while (true) {
/*  82 */       String s = bufferedreader.readLine();
/*     */       
/*  84 */       if (s == null)
/*     */       {
/*  86 */         return stringbuilder.toString();
/*     */       }
/*     */       
/*  89 */       if (MacroState.isMacroLine(s)) {
/*     */         
/*  91 */         if (stringbuilder.length() == 0)
/*     */         {
/*  93 */           stringbuilder.append(ShaderMacros.getFixedMacroLines());
/*     */         }
/*     */         
/*  96 */         if (list1 == null)
/*     */         {
/*  98 */           list1 = new ArrayList<>(Arrays.asList(ShaderMacros.getExtensions()));
/*     */         }
/*     */         
/* 101 */         Iterator<ShaderMacro> iterator = list1.iterator();
/*     */         
/* 103 */         while (iterator.hasNext()) {
/*     */           
/* 105 */           ShaderMacro shadermacro = iterator.next();
/*     */           
/* 107 */           if (s.contains(shadermacro.getName())) {
/*     */             
/* 109 */             stringbuilder.append(shadermacro.getSourceLine());
/* 110 */             stringbuilder.append("\n");
/* 111 */             iterator.remove();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<ShaderOption> getMacroOptions() {
/* 120 */     List<ShaderOption> list = new ArrayList<>();
/* 121 */     ShaderOption[] ashaderoption = Shaders.getShaderPackOptions();
/*     */     
/* 123 */     for (int i = 0; i < ashaderoption.length; i++) {
/*     */       
/* 125 */       ShaderOption shaderoption = ashaderoption[i];
/* 126 */       String s = shaderoption.getSourceLine();
/*     */       
/* 128 */       if (s != null && s.startsWith("#"))
/*     */       {
/* 130 */         list.add(shaderoption);
/*     */       }
/*     */     } 
/*     */     
/* 134 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\config\MacroProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
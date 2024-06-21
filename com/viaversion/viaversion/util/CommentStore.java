/*     */ package com.viaversion.viaversion.util;
/*     */ 
/*     */ import com.google.common.io.CharStreams;
/*     */ import com.google.common.io.Files;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommentStore
/*     */ {
/*  35 */   private final Map<String, List<String>> headers = new HashMap<>();
/*     */   private final String pathSeparator;
/*     */   private final String pathSeparatorQuoted;
/*     */   private final int indents;
/*  39 */   private List<String> mainHeader = new ArrayList<>();
/*     */   
/*     */   public CommentStore(char pathSeparator, int indents) {
/*  42 */     this.pathSeparator = Character.toString(pathSeparator);
/*  43 */     this.pathSeparatorQuoted = Pattern.quote(this.pathSeparator);
/*  44 */     this.indents = indents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mainHeader(String... header) {
/*  53 */     this.mainHeader = Arrays.asList(header);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> mainHeader() {
/*  62 */     return this.mainHeader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void header(String key, String... header) {
/*  72 */     this.headers.put(key, Arrays.asList(header));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> header(String key) {
/*  82 */     return this.headers.get(key);
/*     */   }
/*     */   public void storeComments(InputStream inputStream) throws IOException {
/*     */     String data;
/*  86 */     this.mainHeader.clear();
/*  87 */     this.headers.clear();
/*     */ 
/*     */     
/*  90 */     try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
/*  91 */       data = CharStreams.toString(reader);
/*     */     } 
/*     */     
/*  94 */     List<String> currentComments = new ArrayList<>();
/*  95 */     boolean header = true;
/*  96 */     boolean multiLineValue = false;
/*  97 */     int currentIndents = 0;
/*  98 */     String key = "";
/*  99 */     for (String line : data.split("\n")) {
/* 100 */       String s = line.trim();
/*     */       
/* 102 */       if (s.startsWith("#")) {
/* 103 */         currentComments.add(s);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 108 */       if (header) {
/* 109 */         if (!currentComments.isEmpty()) {
/* 110 */           currentComments.add("");
/* 111 */           this.mainHeader.addAll(currentComments);
/* 112 */           currentComments.clear();
/*     */         } 
/* 114 */         header = false;
/*     */       } 
/*     */ 
/*     */       
/* 118 */       if (s.isEmpty()) {
/* 119 */         currentComments.add(s);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 124 */       if (s.startsWith("- |")) {
/* 125 */         multiLineValue = true;
/*     */         
/*     */         continue;
/*     */       } 
/* 129 */       int indent = getIndents(line);
/* 130 */       int indents = indent / this.indents;
/*     */       
/* 132 */       if (multiLineValue) {
/* 133 */         if (indents > currentIndents)
/*     */           continue; 
/* 135 */         multiLineValue = false;
/*     */       } 
/*     */ 
/*     */       
/* 139 */       if (indents <= currentIndents) {
/* 140 */         String[] array = key.split(this.pathSeparatorQuoted);
/* 141 */         int backspace = currentIndents - indents + 1;
/* 142 */         int delta = array.length - backspace;
/* 143 */         key = (delta >= 0) ? join(array, delta) : key;
/*     */       } 
/*     */ 
/*     */       
/* 147 */       String separator = key.isEmpty() ? "" : this.pathSeparator;
/* 148 */       String lineKey = (line.indexOf(':') != -1) ? line.split(Pattern.quote(":"))[0] : line;
/* 149 */       key = key + separator + lineKey.substring(indent);
/* 150 */       currentIndents = indents;
/*     */       
/* 152 */       if (!currentComments.isEmpty()) {
/* 153 */         this.headers.put(key, new ArrayList<>(currentComments));
/* 154 */         currentComments.clear();
/*     */       } 
/*     */       continue;
/*     */     } 
/*     */   }
/*     */   public void writeComments(String rawYaml, File output) throws IOException {
/* 160 */     StringBuilder fileData = new StringBuilder();
/* 161 */     for (String mainHeaderLine : this.mainHeader) {
/* 162 */       fileData.append(mainHeaderLine).append('\n');
/*     */     }
/*     */ 
/*     */     
/* 166 */     fileData.deleteCharAt(fileData.length() - 1);
/*     */     
/* 168 */     int currentKeyIndents = 0;
/* 169 */     String key = "";
/* 170 */     for (String line : rawYaml.split("\n")) {
/* 171 */       if (!line.isEmpty()) {
/*     */         boolean keyLine;
/*     */ 
/*     */         
/* 175 */         int indent = getIndents(line);
/* 176 */         int indents = indent / this.indents;
/*     */         
/* 178 */         String substring = line.substring(indent);
/* 179 */         if (substring.trim().isEmpty() || substring.charAt(0) == '-') {
/* 180 */           keyLine = false;
/* 181 */         } else if (indents <= currentKeyIndents) {
/* 182 */           String[] array = key.split(this.pathSeparatorQuoted);
/* 183 */           int backspace = currentKeyIndents - indents + 1;
/* 184 */           key = join(array, array.length - backspace);
/* 185 */           keyLine = true;
/*     */         } else {
/* 187 */           keyLine = (line.indexOf(':') != -1);
/*     */         } 
/*     */         
/* 190 */         if (!keyLine) {
/*     */           
/* 192 */           fileData.append(line).append('\n');
/*     */         }
/*     */         else {
/*     */           
/* 196 */           String newKey = substring.split(Pattern.quote(":"))[0];
/* 197 */           if (!key.isEmpty()) {
/* 198 */             key = key + this.pathSeparator;
/*     */           }
/* 200 */           key = key + newKey;
/*     */ 
/*     */           
/* 203 */           List<String> strings = this.headers.get(key);
/* 204 */           if (strings != null && !strings.isEmpty()) {
/* 205 */             String indentText = (indent > 0) ? line.substring(0, indent) : "";
/* 206 */             for (String comment : strings) {
/* 207 */               if (comment.isEmpty()) {
/* 208 */                 fileData.append('\n'); continue;
/*     */               } 
/* 210 */               fileData.append(indentText).append(comment).append('\n');
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 215 */           currentKeyIndents = indents;
/* 216 */           fileData.append(line).append('\n');
/*     */         } 
/*     */       } 
/*     */     } 
/* 220 */     Files.write(fileData.toString(), output, StandardCharsets.UTF_8);
/*     */   }
/*     */   
/*     */   private int getIndents(String line) {
/* 224 */     int count = 0;
/* 225 */     for (int i = 0; i < line.length() && 
/* 226 */       line.charAt(i) == ' '; i++)
/*     */     {
/*     */ 
/*     */       
/* 230 */       count++;
/*     */     }
/* 232 */     return count;
/*     */   }
/*     */   
/*     */   private String join(String[] array, int length) {
/* 236 */     String[] copy = new String[length];
/* 237 */     System.arraycopy(array, 0, copy, 0, length);
/* 238 */     return String.join(this.pathSeparator, (CharSequence[])copy);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\util\CommentStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
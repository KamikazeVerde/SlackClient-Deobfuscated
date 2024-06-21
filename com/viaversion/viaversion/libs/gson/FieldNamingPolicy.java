/*     */ package com.viaversion.viaversion.libs.gson;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Locale;
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
/*     */ public enum FieldNamingPolicy
/*     */   implements FieldNamingStrategy
/*     */ {
/*  37 */   IDENTITY {
/*     */     public String translateName(Field f) {
/*  39 */       return f.getName();
/*     */     }
/*     */   },
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
/*  53 */   UPPER_CAMEL_CASE {
/*     */     public String translateName(Field f) {
/*  55 */       return null.upperCaseFirstLetter(f.getName());
/*     */     }
/*     */   },
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
/*  72 */   UPPER_CAMEL_CASE_WITH_SPACES {
/*     */     public String translateName(Field f) {
/*  74 */       return null.upperCaseFirstLetter(null.separateCamelCase(f.getName(), ' '));
/*     */     }
/*     */   },
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
/*  92 */   UPPER_CASE_WITH_UNDERSCORES {
/*     */     public String translateName(Field f) {
/*  94 */       return null.separateCamelCase(f.getName(), '_').toUpperCase(Locale.ENGLISH);
/*     */     }
/*     */   },
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
/* 110 */   LOWER_CASE_WITH_UNDERSCORES {
/*     */     public String translateName(Field f) {
/* 112 */       return null.separateCamelCase(f.getName(), '_').toLowerCase(Locale.ENGLISH);
/*     */     }
/*     */   },
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
/*     */ 
/*     */   
/* 134 */   LOWER_CASE_WITH_DASHES {
/*     */     public String translateName(Field f) {
/* 136 */       return null.separateCamelCase(f.getName(), '-').toLowerCase(Locale.ENGLISH);
/*     */     }
/*     */   },
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
/*     */ 
/*     */   
/* 158 */   LOWER_CASE_WITH_DOTS {
/*     */     public String translateName(Field f) {
/* 160 */       return null.separateCamelCase(f.getName(), '.').toLowerCase(Locale.ENGLISH);
/*     */     }
/*     */   };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String separateCamelCase(String name, char separator) {
/* 169 */     StringBuilder translation = new StringBuilder();
/* 170 */     for (int i = 0, length = name.length(); i < length; i++) {
/* 171 */       char character = name.charAt(i);
/* 172 */       if (Character.isUpperCase(character) && translation.length() != 0) {
/* 173 */         translation.append(separator);
/*     */       }
/* 175 */       translation.append(character);
/*     */     } 
/* 177 */     return translation.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String upperCaseFirstLetter(String s) {
/* 184 */     int length = s.length();
/* 185 */     for (int i = 0; i < length; i++) {
/* 186 */       char c = s.charAt(i);
/* 187 */       if (Character.isLetter(c)) {
/* 188 */         if (Character.isUpperCase(c)) {
/* 189 */           return s;
/*     */         }
/*     */         
/* 192 */         char uppercased = Character.toUpperCase(c);
/*     */         
/* 194 */         if (i == 0) {
/* 195 */           return uppercased + s.substring(1);
/*     */         }
/* 197 */         return s.substring(0, i) + uppercased + s.substring(i + 1);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 202 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\FieldNamingPolicy.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
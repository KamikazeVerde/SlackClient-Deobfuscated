/*    */ package com.viaversion.viabackwards.utils;
/*    */ 
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatUtil
/*    */ {
/* 23 */   private static final Pattern UNUSED_COLOR_PATTERN = Pattern.compile("(?>(?>§[0-fk-or])*(§r|\\Z))|(?>(?>§[0-f])*(§[0-f]))");
/* 24 */   private static final Pattern UNUSED_COLOR_PATTERN_PREFIX = Pattern.compile("(?>(?>§[0-fk-or])*(§r))|(?>(?>§[0-f])*(§[0-f]))");
/*    */   
/*    */   public static String removeUnusedColor(String legacy, char defaultColor) {
/* 27 */     return removeUnusedColor(legacy, defaultColor, false);
/*    */   }
/*    */   
/*    */   public static String removeUnusedColor(String legacy, char defaultColor, boolean isPrefix) {
/* 31 */     if (legacy == null) return null;
/*    */     
/* 33 */     Pattern pattern = isPrefix ? UNUSED_COLOR_PATTERN_PREFIX : UNUSED_COLOR_PATTERN;
/* 34 */     legacy = pattern.matcher(legacy).replaceAll("$1$2");
/* 35 */     StringBuilder builder = new StringBuilder();
/* 36 */     char last = defaultColor;
/* 37 */     for (int i = 0; i < legacy.length(); i++) {
/* 38 */       char current = legacy.charAt(i);
/* 39 */       if (current != '§' || i == legacy.length() - 1) {
/* 40 */         builder.append(current);
/*    */       }
/*    */       else {
/*    */         
/* 44 */         current = legacy.charAt(++i);
/* 45 */         if (current != last) {
/* 46 */           builder.append('§').append(current);
/* 47 */           last = current;
/*    */         } 
/*    */       } 
/* 50 */     }  return builder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackward\\utils\ChatUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
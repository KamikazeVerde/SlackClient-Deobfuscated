/*    */ package com.viaversion.viaversion.util;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatColorUtil
/*    */ {
/*    */   public static final String ALL_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx";
/*    */   public static final char COLOR_CHAR = '§';
/* 34 */   public static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-ORX]");
/* 35 */   private static final Int2IntMap COLOR_ORDINALS = (Int2IntMap)new Int2IntOpenHashMap();
/*    */   private static int ordinalCounter;
/*    */   
/*    */   static {
/* 39 */     addColorOrdinal(48, 57);
/* 40 */     addColorOrdinal(97, 102);
/* 41 */     addColorOrdinal(107, 111);
/* 42 */     addColorOrdinal(114);
/*    */   }
/*    */   
/*    */   public static boolean isColorCode(char c) {
/* 46 */     return COLOR_ORDINALS.containsKey(c);
/*    */   }
/*    */   
/*    */   public static int getColorOrdinal(char c) {
/* 50 */     return COLOR_ORDINALS.getOrDefault(c, -1);
/*    */   }
/*    */   
/*    */   public static String translateAlternateColorCodes(String s) {
/* 54 */     char[] chars = s.toCharArray();
/* 55 */     for (int i = 0; i < chars.length - 1; i++) {
/* 56 */       if (chars[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(chars[i + 1]) > -1) {
/* 57 */         chars[i] = '§';
/* 58 */         chars[i + 1] = Character.toLowerCase(chars[i + 1]);
/*    */       } 
/*    */     } 
/* 61 */     return new String(chars);
/*    */   }
/*    */   
/*    */   public static String stripColor(String input) {
/* 65 */     return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
/*    */   }
/*    */   
/*    */   private static void addColorOrdinal(int from, int to) {
/* 69 */     for (int c = from; c <= to; c++) {
/* 70 */       addColorOrdinal(c);
/*    */     }
/*    */   }
/*    */   
/*    */   private static void addColorOrdinal(int colorChar) {
/* 75 */     COLOR_ORDINALS.put(colorChar, ordinalCounter++);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\util\ChatColorUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
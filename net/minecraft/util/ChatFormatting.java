/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public enum ChatFormatting
/*     */ {
/*  12 */   BLACK("BLACK", '0', 0),
/*  13 */   DARK_BLUE("DARK_BLUE", '1', 1),
/*  14 */   DARK_GREEN("DARK_GREEN", '2', 2),
/*  15 */   DARK_AQUA("DARK_AQUA", '3', 3),
/*  16 */   DARK_RED("DARK_RED", '4', 4),
/*  17 */   DARK_PURPLE("DARK_PURPLE", '5', 5),
/*  18 */   GOLD("GOLD", '6', 6),
/*  19 */   GRAY("GRAY", '7', 7),
/*  20 */   DARK_GRAY("DARK_GRAY", '8', 8),
/*  21 */   BLUE("BLUE", '9', 9),
/*  22 */   GREEN("GREEN", 'a', 10),
/*  23 */   AQUA("AQUA", 'b', 11),
/*  24 */   RED("RED", 'c', 12),
/*  25 */   LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13),
/*  26 */   YELLOW("YELLOW", 'e', 14),
/*  27 */   WHITE("WHITE", 'f', 15),
/*  28 */   OBFUSCATED("OBFUSCATED", 'k', true),
/*  29 */   BOLD("BOLD", 'l', true),
/*  30 */   STRIKETHROUGH("STRIKETHROUGH", 'm', true),
/*  31 */   UNDERLINE("UNDERLINE", 'n', true),
/*  32 */   ITALIC("ITALIC", 'o', true),
/*  33 */   RESET("RESET", 'r', -1);
/*     */   static {
/*  35 */     nameMapping = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  41 */     formattingCodePattern = Pattern.compile("(?i)§[0-9A-FK-OR]");
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
/* 176 */     for (ChatFormatting enumchatformatting : values())
/*     */     {
/* 178 */       nameMapping.put(func_175745_c(enumchatformatting.name), enumchatformatting); } 
/*     */   }
/*     */   
/*     */   private static final Map<String, ChatFormatting> nameMapping;
/*     */   private static final Pattern formattingCodePattern;
/*     */   private final String name;
/*     */   private final char formattingCode;
/*     */   private final boolean fancyStyling;
/*     */   private final String controlString;
/*     */   private final int colorIndex;
/*     */   
/*     */   private static String func_175745_c(String p_175745_0_) {
/*     */     return p_175745_0_.toLowerCase().replaceAll("[^a-z]", "");
/*     */   }
/*     */   
/*     */   ChatFormatting(String formattingName, char formattingCodeIn, boolean fancyStylingIn, int colorIndex) {
/*     */     this.name = formattingName;
/*     */     this.formattingCode = formattingCodeIn;
/*     */     this.fancyStyling = fancyStylingIn;
/*     */     this.colorIndex = colorIndex;
/*     */     this.controlString = "§" + formattingCodeIn;
/*     */   }
/*     */   
/*     */   public int getColorIndex() {
/*     */     return this.colorIndex;
/*     */   }
/*     */   
/*     */   public boolean isFancyStyling() {
/*     */     return this.fancyStyling;
/*     */   }
/*     */   
/*     */   public boolean isColor() {
/*     */     return (!this.fancyStyling && this != RESET);
/*     */   }
/*     */   
/*     */   public String getFriendlyName() {
/*     */     return name().toLowerCase();
/*     */   }
/*     */   
/*     */   public String toString() {
/*     */     return this.controlString;
/*     */   }
/*     */   
/*     */   public static String getTextWithoutFormattingCodes(String text) {
/*     */     return (text == null) ? null : formattingCodePattern.matcher(text).replaceAll("");
/*     */   }
/*     */   
/*     */   public static ChatFormatting getValueByName(String friendlyName) {
/*     */     return (friendlyName == null) ? null : nameMapping.get(func_175745_c(friendlyName));
/*     */   }
/*     */   
/*     */   public static ChatFormatting func_175744_a(int p_175744_0_) {
/*     */     if (p_175744_0_ < 0)
/*     */       return RESET; 
/*     */     for (ChatFormatting enumchatformatting : values()) {
/*     */       if (enumchatformatting.getColorIndex() == p_175744_0_)
/*     */         return enumchatformatting; 
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public static Collection<String> getValidValues(boolean p_96296_0_, boolean p_96296_1_) {
/*     */     List<String> list = Lists.newArrayList();
/*     */     for (ChatFormatting enumchatformatting : values()) {
/*     */       if ((!enumchatformatting.isColor() || p_96296_0_) && (!enumchatformatting.isFancyStyling() || p_96296_1_))
/*     */         list.add(enumchatformatting.getFriendlyName()); 
/*     */     } 
/*     */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraf\\util\ChatFormatting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
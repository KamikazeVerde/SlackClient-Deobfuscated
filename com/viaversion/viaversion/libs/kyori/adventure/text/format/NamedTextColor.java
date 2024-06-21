/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text.format;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.HSVLike;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Stream;
/*     */ import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ public final class NamedTextColor
/*     */   implements TextColor
/*     */ {
/*     */   private static final int BLACK_VALUE = 0;
/*     */   private static final int DARK_BLUE_VALUE = 170;
/*     */   private static final int DARK_GREEN_VALUE = 43520;
/*     */   private static final int DARK_AQUA_VALUE = 43690;
/*     */   private static final int DARK_RED_VALUE = 11141120;
/*     */   private static final int DARK_PURPLE_VALUE = 11141290;
/*     */   private static final int GOLD_VALUE = 16755200;
/*     */   private static final int GRAY_VALUE = 11184810;
/*     */   private static final int DARK_GRAY_VALUE = 5592405;
/*     */   private static final int BLUE_VALUE = 5592575;
/*     */   private static final int GREEN_VALUE = 5635925;
/*     */   private static final int AQUA_VALUE = 5636095;
/*     */   private static final int RED_VALUE = 16733525;
/*     */   private static final int LIGHT_PURPLE_VALUE = 16733695;
/*     */   private static final int YELLOW_VALUE = 16777045;
/*     */   private static final int WHITE_VALUE = 16777215;
/*  67 */   public static final NamedTextColor BLACK = new NamedTextColor("black", 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public static final NamedTextColor DARK_BLUE = new NamedTextColor("dark_blue", 170);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public static final NamedTextColor DARK_GREEN = new NamedTextColor("dark_green", 43520);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public static final NamedTextColor DARK_AQUA = new NamedTextColor("dark_aqua", 43690);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public static final NamedTextColor DARK_RED = new NamedTextColor("dark_red", 11141120);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public static final NamedTextColor DARK_PURPLE = new NamedTextColor("dark_purple", 11141290);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public static final NamedTextColor GOLD = new NamedTextColor("gold", 16755200);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public static final NamedTextColor GRAY = new NamedTextColor("gray", 11184810);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public static final NamedTextColor DARK_GRAY = new NamedTextColor("dark_gray", 5592405);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   public static final NamedTextColor BLUE = new NamedTextColor("blue", 5592575);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public static final NamedTextColor GREEN = new NamedTextColor("green", 5635925);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   public static final NamedTextColor AQUA = new NamedTextColor("aqua", 5636095);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   public static final NamedTextColor RED = new NamedTextColor("red", 16733525);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   public static final NamedTextColor LIGHT_PURPLE = new NamedTextColor("light_purple", 16733695);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   public static final NamedTextColor YELLOW = new NamedTextColor("yellow", 16777045);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   public static final NamedTextColor WHITE = new NamedTextColor("white", 16777215);
/*     */   
/* 159 */   private static final List<NamedTextColor> VALUES = Collections.unmodifiableList(Arrays.asList(new NamedTextColor[] { BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE })); public static final Index<String, NamedTextColor> NAMES;
/*     */   private final String name;
/*     */   private final int value;
/*     */   private final HSVLike hsv;
/*     */   
/*     */   static {
/* 165 */     NAMES = Index.create(constant -> constant.name, VALUES);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static NamedTextColor namedColor(int value) {
/* 175 */     switch (value) { case 0:
/* 176 */         return BLACK;
/* 177 */       case 170: return DARK_BLUE;
/* 178 */       case 43520: return DARK_GREEN;
/* 179 */       case 43690: return DARK_AQUA;
/* 180 */       case 11141120: return DARK_RED;
/* 181 */       case 11141290: return DARK_PURPLE;
/* 182 */       case 16755200: return GOLD;
/* 183 */       case 11184810: return GRAY;
/* 184 */       case 5592405: return DARK_GRAY;
/* 185 */       case 5592575: return BLUE;
/* 186 */       case 5635925: return GREEN;
/* 187 */       case 5636095: return AQUA;
/* 188 */       case 16733525: return RED;
/* 189 */       case 16733695: return LIGHT_PURPLE;
/* 190 */       case 16777045: return YELLOW;
/* 191 */       case 16777215: return WHITE; }
/* 192 */      return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @ScheduledForRemoval(inVersion = "5.0.0")
/*     */   @Nullable
/*     */   public static NamedTextColor ofExact(int value) {
/* 207 */     switch (value) { case 0:
/* 208 */         return BLACK;
/* 209 */       case 170: return DARK_BLUE;
/* 210 */       case 43520: return DARK_GREEN;
/* 211 */       case 43690: return DARK_AQUA;
/* 212 */       case 11141120: return DARK_RED;
/* 213 */       case 11141290: return DARK_PURPLE;
/* 214 */       case 16755200: return GOLD;
/* 215 */       case 11184810: return GRAY;
/* 216 */       case 5592405: return DARK_GRAY;
/* 217 */       case 5592575: return BLUE;
/* 218 */       case 5635925: return GREEN;
/* 219 */       case 5636095: return AQUA;
/* 220 */       case 16733525: return RED;
/* 221 */       case 16733695: return LIGHT_PURPLE;
/* 222 */       case 16777045: return YELLOW;
/* 223 */       case 16777215: return WHITE; }
/* 224 */      return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static NamedTextColor nearestTo(@NotNull TextColor any) {
/* 236 */     if (any instanceof NamedTextColor) {
/* 237 */       return (NamedTextColor)any;
/*     */     }
/*     */     
/* 240 */     Objects.requireNonNull(any, "color");
/*     */     
/* 242 */     float matchedDistance = Float.MAX_VALUE;
/* 243 */     NamedTextColor match = VALUES.get(0);
/* 244 */     for (int i = 0, length = VALUES.size(); i < length; i++) {
/* 245 */       NamedTextColor potential = VALUES.get(i);
/* 246 */       float distance = distance(any.asHSV(), potential.asHSV());
/* 247 */       if (distance < matchedDistance) {
/* 248 */         match = potential;
/* 249 */         matchedDistance = distance;
/*     */       } 
/* 251 */       if (distance == 0.0F) {
/*     */         break;
/*     */       }
/*     */     } 
/* 255 */     return match;
/*     */   }
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
/*     */   private static float distance(@NotNull HSVLike self, @NotNull HSVLike other) {
/* 269 */     float hueDistance = 3.0F * Math.min(Math.abs(self.h() - other.h()), 1.0F - Math.abs(self.h() - other.h()));
/* 270 */     float saturationDiff = self.s() - other.s();
/* 271 */     float valueDiff = self.v() - other.v();
/* 272 */     return hueDistance * hueDistance + saturationDiff * saturationDiff + valueDiff * valueDiff;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private NamedTextColor(String name, int value) {
/* 280 */     this.name = name;
/* 281 */     this.value = value;
/* 282 */     this.hsv = HSVLike.fromRGB(red(), green(), blue());
/*     */   }
/*     */ 
/*     */   
/*     */   public int value() {
/* 287 */     return this.value;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public HSVLike asHSV() {
/* 292 */     return this.hsv;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String toString() {
/* 297 */     return this.name;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<? extends ExaminableProperty> examinableProperties() {
/* 302 */     return Stream.concat(
/* 303 */         Stream.of(ExaminableProperty.of("name", this.name)), super
/* 304 */         .examinableProperties());
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\format\NamedTextColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
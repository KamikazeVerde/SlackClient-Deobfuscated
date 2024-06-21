/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text.format;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.HSVLike;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.RGBLike;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.Examinable;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*     */ import java.util.stream.Stream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface TextColor
/*     */   extends Comparable<TextColor>, Examinable, RGBLike, StyleBuilderApplicable, TextFormat
/*     */ {
/*     */   @NotNull
/*     */   static TextColor color(int value) {
/*  55 */     int truncatedValue = value & 0xFFFFFF;
/*  56 */     NamedTextColor named = NamedTextColor.namedColor(truncatedValue);
/*  57 */     return (named != null) ? named : new TextColorImpl(truncatedValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static TextColor color(@NotNull RGBLike rgb) {
/*  68 */     if (rgb instanceof TextColor) return (TextColor)rgb; 
/*  69 */     return color(rgb.red(), rgb.green(), rgb.blue());
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
/*     */   static TextColor color(@NotNull HSVLike hsv) {
/*  81 */     float s = hsv.s();
/*  82 */     float v = hsv.v();
/*  83 */     if (s == 0.0F)
/*     */     {
/*  85 */       return color(v, v, v);
/*     */     }
/*     */     
/*  88 */     float h = hsv.h() * 6.0F;
/*  89 */     int i = (int)Math.floor(h);
/*  90 */     float f = h - i;
/*  91 */     float p = v * (1.0F - s);
/*  92 */     float q = v * (1.0F - s * f);
/*  93 */     float t = v * (1.0F - s * (1.0F - f));
/*     */     
/*  95 */     if (i == 0)
/*  96 */       return color(v, t, p); 
/*  97 */     if (i == 1)
/*  98 */       return color(q, v, p); 
/*  99 */     if (i == 2)
/* 100 */       return color(p, v, t); 
/* 101 */     if (i == 3)
/* 102 */       return color(p, q, v); 
/* 103 */     if (i == 4) {
/* 104 */       return color(t, p, v);
/*     */     }
/* 106 */     return color(v, p, q);
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
/*     */   @NotNull
/*     */   static TextColor color(int r, int g, int b) {
/* 120 */     return color((r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF);
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
/*     */   @NotNull
/*     */   static TextColor color(float r, float g, float b) {
/* 133 */     return color((int)(r * 255.0F), (int)(g * 255.0F), (int)(b * 255.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   static TextColor fromHexString(@NotNull String string) {
/* 144 */     if (string.startsWith("#")) {
/*     */       try {
/* 146 */         int hex = Integer.parseInt(string.substring(1), 16);
/* 147 */         return color(hex);
/* 148 */       } catch (NumberFormatException e) {
/* 149 */         return null;
/*     */       } 
/*     */     }
/* 152 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   static TextColor fromCSSHexString(@NotNull String string) {
/* 163 */     if (string.startsWith("#")) {
/* 164 */       int hex; String hexString = string.substring(1);
/* 165 */       if (hexString.length() != 3 && hexString.length() != 6) {
/* 166 */         return null;
/*     */       }
/*     */       
/*     */       try {
/* 170 */         hex = Integer.parseInt(hexString, 16);
/* 171 */       } catch (NumberFormatException e) {
/* 172 */         return null;
/*     */       } 
/*     */       
/* 175 */       if (hexString.length() == 6) {
/* 176 */         return color(hex);
/*     */       }
/* 178 */       int red = (hex & 0xF00) >> 8 | (hex & 0xF00) >> 4;
/* 179 */       int green = (hex & 0xF0) >> 4 | hex & 0xF0;
/* 180 */       int blue = (hex & 0xF) << 4 | hex & 0xF;
/* 181 */       return color(red, green, blue);
/*     */     } 
/*     */     
/* 184 */     return null;
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
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default String asHexString() {
/* 202 */     return String.format("#%06x", new Object[] { Integer.valueOf(value()) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default int red() {
/* 213 */     return value() >> 16 & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default int green() {
/* 224 */     return value() >> 8 & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default int blue() {
/* 235 */     return value() & 0xFF;
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
/*     */   @NotNull
/*     */   static TextColor lerp(float t, @NotNull RGBLike a, @NotNull RGBLike b) {
/* 250 */     float clampedT = Math.min(1.0F, Math.max(0.0F, t));
/* 251 */     int ar = a.red();
/* 252 */     int br = b.red();
/* 253 */     int ag = a.green();
/* 254 */     int bg = b.green();
/* 255 */     int ab = a.blue();
/* 256 */     int bb = b.blue();
/* 257 */     return color(
/* 258 */         Math.round(ar + clampedT * (br - ar)), 
/* 259 */         Math.round(ag + clampedT * (bg - ag)), 
/* 260 */         Math.round(ab + clampedT * (bb - ab)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default void styleApply(Style.Builder style) {
/* 266 */     style.color(this);
/*     */   }
/*     */ 
/*     */   
/*     */   default int compareTo(TextColor that) {
/* 271 */     return Integer.compare(value(), that.value());
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   default Stream<? extends ExaminableProperty> examinableProperties() {
/* 276 */     return Stream.of(ExaminableProperty.of("value", asHexString()));
/*     */   }
/*     */   
/*     */   int value();
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\format\TextColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfig;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.flattener.ComponentFlattener;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.flattener.FlattenerListener;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.NamedTextColor;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextFormat;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Services;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.regex.Pattern;
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
/*     */ final class LegacyComponentSerializerImpl
/*     */   implements LegacyComponentSerializer
/*     */ {
/*  55 */   static final Pattern DEFAULT_URL_PATTERN = Pattern.compile("(?:(https?)://)?([-\\w_.]+\\.\\w{2,})(/\\S*)?");
/*  56 */   static final Pattern URL_SCHEME_PATTERN = Pattern.compile("^[a-z][a-z0-9+\\-.]*:");
/*  57 */   private static final TextDecoration[] DECORATIONS = TextDecoration.values();
/*     */   
/*     */   private static final char LEGACY_BUNGEE_HEX_CHAR = 'x';
/*     */   
/*     */   private static final List<TextFormat> FORMATS;
/*     */   private static final String LEGACY_CHARS;
/*     */   
/*     */   static {
/*  65 */     Map<TextFormat, String> formats = new LinkedHashMap<>(22);
/*     */     
/*  67 */     formats.put(NamedTextColor.BLACK, "0");
/*  68 */     formats.put(NamedTextColor.DARK_BLUE, "1");
/*  69 */     formats.put(NamedTextColor.DARK_GREEN, "2");
/*  70 */     formats.put(NamedTextColor.DARK_AQUA, "3");
/*  71 */     formats.put(NamedTextColor.DARK_RED, "4");
/*  72 */     formats.put(NamedTextColor.DARK_PURPLE, "5");
/*  73 */     formats.put(NamedTextColor.GOLD, "6");
/*  74 */     formats.put(NamedTextColor.GRAY, "7");
/*  75 */     formats.put(NamedTextColor.DARK_GRAY, "8");
/*  76 */     formats.put(NamedTextColor.BLUE, "9");
/*  77 */     formats.put(NamedTextColor.GREEN, "a");
/*  78 */     formats.put(NamedTextColor.AQUA, "b");
/*  79 */     formats.put(NamedTextColor.RED, "c");
/*  80 */     formats.put(NamedTextColor.LIGHT_PURPLE, "d");
/*  81 */     formats.put(NamedTextColor.YELLOW, "e");
/*  82 */     formats.put(NamedTextColor.WHITE, "f");
/*     */     
/*  84 */     formats.put(TextDecoration.OBFUSCATED, "k");
/*  85 */     formats.put(TextDecoration.BOLD, "l");
/*  86 */     formats.put(TextDecoration.STRIKETHROUGH, "m");
/*  87 */     formats.put(TextDecoration.UNDERLINED, "n");
/*  88 */     formats.put(TextDecoration.ITALIC, "o");
/*     */     
/*  90 */     formats.put(Reset.INSTANCE, "r");
/*     */     
/*  92 */     FORMATS = Collections.unmodifiableList(new ArrayList<>(formats.keySet()));
/*  93 */     LEGACY_CHARS = String.join("", formats.values());
/*     */ 
/*     */     
/*  96 */     if (FORMATS.size() != LEGACY_CHARS.length()) {
/*  97 */       throw new IllegalStateException("FORMATS length differs from LEGACY_CHARS length");
/*     */     }
/*     */   }
/*     */   
/* 101 */   private static final Optional<LegacyComponentSerializer.Provider> SERVICE = Services.service(LegacyComponentSerializer.Provider.class);
/* 102 */   static final Consumer<LegacyComponentSerializer.Builder> BUILDER = SERVICE
/* 103 */     .<Consumer<LegacyComponentSerializer.Builder>>map(LegacyComponentSerializer.Provider::legacy)
/* 104 */     .orElseGet(() -> ()); private final char character; private final char hexCharacter; @Nullable
/*     */   private final TextReplacementConfig urlReplacementConfig;
/*     */   private final boolean hexColours;
/*     */   private final boolean useTerriblyStupidHexFormat;
/*     */   private final ComponentFlattener flattener;
/*     */   
/* 110 */   static final class Instances { static final LegacyComponentSerializer SECTION = LegacyComponentSerializerImpl.SERVICE
/* 111 */       .map(LegacyComponentSerializer.Provider::legacySection)
/* 112 */       .orElseGet(() -> new LegacyComponentSerializerImpl('§', '#', null, false, false, ComponentFlattener.basic()));
/* 113 */     static final LegacyComponentSerializer AMPERSAND = LegacyComponentSerializerImpl.SERVICE
/* 114 */       .map(LegacyComponentSerializer.Provider::legacyAmpersand)
/* 115 */       .orElseGet(() -> new LegacyComponentSerializerImpl('&', '#', null, false, false, ComponentFlattener.basic())); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LegacyComponentSerializerImpl(char character, char hexCharacter, @Nullable TextReplacementConfig urlReplacementConfig, boolean hexColours, boolean useTerriblyStupidHexFormat, ComponentFlattener flattener) {
/* 126 */     this.character = character;
/* 127 */     this.hexCharacter = hexCharacter;
/* 128 */     this.urlReplacementConfig = urlReplacementConfig;
/* 129 */     this.hexColours = hexColours;
/* 130 */     this.useTerriblyStupidHexFormat = useTerriblyStupidHexFormat;
/* 131 */     this.flattener = flattener;
/*     */   }
/*     */   @Nullable
/*     */   private FormatCodeType determineFormatType(char legacy, String input, int pos) {
/* 135 */     if (pos >= 14) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 140 */       int expectedCharacterPosition = pos - 14;
/* 141 */       int expectedIndicatorPosition = pos - 13;
/* 142 */       if (input.charAt(expectedCharacterPosition) == this.character && input.charAt(expectedIndicatorPosition) == 'x') {
/* 143 */         return FormatCodeType.BUNGEECORD_UNUSUAL_HEX;
/*     */       }
/*     */     } 
/* 146 */     if (legacy == this.hexCharacter && input.length() - pos >= 6)
/* 147 */       return FormatCodeType.KYORI_HEX; 
/* 148 */     if (LEGACY_CHARS.indexOf(legacy) != -1) {
/* 149 */       return FormatCodeType.MOJANG_LEGACY;
/*     */     }
/* 151 */     return null;
/*     */   }
/*     */   @Nullable
/*     */   static LegacyFormat legacyFormat(char character) {
/* 155 */     int index = LEGACY_CHARS.indexOf(character);
/* 156 */     if (index != -1) {
/* 157 */       TextFormat format = FORMATS.get(index);
/* 158 */       if (format instanceof NamedTextColor)
/* 159 */         return new LegacyFormat((NamedTextColor)format); 
/* 160 */       if (format instanceof TextDecoration)
/* 161 */         return new LegacyFormat((TextDecoration)format); 
/* 162 */       if (format instanceof Reset) {
/* 163 */         return LegacyFormat.RESET;
/*     */       }
/*     */     } 
/* 166 */     return null;
/*     */   }
/*     */   @Nullable
/*     */   private DecodedFormat decodeTextFormat(char legacy, String input, int pos) {
/* 170 */     FormatCodeType foundFormat = determineFormatType(legacy, input, pos);
/* 171 */     if (foundFormat == null) {
/* 172 */       return null;
/*     */     }
/* 174 */     if (foundFormat == FormatCodeType.KYORI_HEX) {
/* 175 */       TextColor parsed = tryParseHexColor(input.substring(pos, pos + 6));
/* 176 */       if (parsed != null)
/* 177 */         return new DecodedFormat(foundFormat, (TextFormat)parsed); 
/*     */     } else {
/* 179 */       if (foundFormat == FormatCodeType.MOJANG_LEGACY)
/* 180 */         return new DecodedFormat(foundFormat, FORMATS.get(LEGACY_CHARS.indexOf(legacy))); 
/* 181 */       if (foundFormat == FormatCodeType.BUNGEECORD_UNUSUAL_HEX) {
/* 182 */         StringBuilder foundHex = new StringBuilder(6);
/* 183 */         for (int i = pos - 1; i >= pos - 11; i -= 2) {
/* 184 */           foundHex.append(input.charAt(i));
/*     */         }
/* 186 */         TextColor parsed = tryParseHexColor(foundHex.reverse().toString());
/* 187 */         if (parsed != null)
/* 188 */           return new DecodedFormat(foundFormat, (TextFormat)parsed); 
/*     */       } 
/*     */     } 
/* 191 */     return null;
/*     */   }
/*     */   @Nullable
/*     */   private static TextColor tryParseHexColor(String hexDigits) {
/*     */     try {
/* 196 */       int color = Integer.parseInt(hexDigits, 16);
/* 197 */       return TextColor.color(color);
/* 198 */     } catch (NumberFormatException ex) {
/* 199 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isHexTextColor(TextFormat format) {
/* 204 */     return (format instanceof TextColor && !(format instanceof NamedTextColor));
/*     */   }
/*     */   private String toLegacyCode(TextFormat format) {
/*     */     NamedTextColor namedTextColor;
/* 208 */     if (isHexTextColor(format)) {
/* 209 */       TextColor color = (TextColor)format;
/* 210 */       if (this.hexColours) {
/* 211 */         String hex = String.format("%06x", new Object[] { Integer.valueOf(color.value()) });
/* 212 */         if (this.useTerriblyStupidHexFormat) {
/*     */           
/* 214 */           StringBuilder legacy = new StringBuilder(String.valueOf('x'));
/* 215 */           for (int i = 0, length = hex.length(); i < length; i++) {
/* 216 */             legacy.append(this.character).append(hex.charAt(i));
/*     */           }
/* 218 */           return legacy.toString();
/*     */         } 
/*     */         
/* 221 */         return this.hexCharacter + hex;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 226 */       namedTextColor = NamedTextColor.nearestTo(color);
/*     */     } 
/*     */     
/* 229 */     int index = FORMATS.indexOf(namedTextColor);
/* 230 */     return Character.toString(LEGACY_CHARS.charAt(index));
/*     */   }
/*     */   
/*     */   private TextComponent extractUrl(TextComponent component) {
/* 234 */     if (this.urlReplacementConfig == null) return component; 
/* 235 */     Component newComponent = component.replaceText(this.urlReplacementConfig);
/* 236 */     if (newComponent instanceof TextComponent) return (TextComponent)newComponent; 
/* 237 */     return (TextComponent)((TextComponent.Builder)Component.text().append(newComponent)).build();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TextComponent deserialize(@NotNull String input) {
/* 242 */     int next = input.lastIndexOf(this.character, input.length() - 2);
/* 243 */     if (next == -1) {
/* 244 */       return extractUrl(Component.text(input));
/*     */     }
/*     */     
/* 247 */     List<TextComponent> parts = new ArrayList<>();
/*     */     
/* 249 */     TextComponent.Builder current = null;
/* 250 */     boolean reset = false;
/*     */     
/* 252 */     int pos = input.length();
/*     */     do {
/* 254 */       DecodedFormat decoded = decodeTextFormat(input.charAt(next + 1), input, next + 2);
/* 255 */       if (decoded != null) {
/* 256 */         int from = next + ((decoded.encodedFormat == FormatCodeType.KYORI_HEX) ? 8 : 2);
/* 257 */         if (from != pos) {
/* 258 */           if (current != null) {
/* 259 */             if (reset) {
/* 260 */               parts.add((TextComponent)current.build());
/* 261 */               reset = false;
/* 262 */               current = Component.text();
/*     */             } else {
/* 264 */               current = (TextComponent.Builder)Component.text().append((Component)current.build());
/*     */             } 
/*     */           } else {
/* 267 */             current = Component.text();
/*     */           } 
/*     */           
/* 270 */           current.content(input.substring(from, pos));
/* 271 */         } else if (current == null) {
/* 272 */           current = Component.text();
/*     */         } 
/*     */         
/* 275 */         if (!reset) {
/* 276 */           reset = applyFormat(current, decoded.format);
/*     */         }
/* 278 */         if (decoded.encodedFormat == FormatCodeType.BUNGEECORD_UNUSUAL_HEX)
/*     */         {
/*     */ 
/*     */           
/* 282 */           next -= 12;
/*     */         }
/* 284 */         pos = next;
/*     */       } 
/*     */       
/* 287 */       next = input.lastIndexOf(this.character, next - 1);
/* 288 */     } while (next != -1);
/*     */     
/* 290 */     if (current != null) {
/* 291 */       parts.add((TextComponent)current.build());
/*     */     }
/*     */     
/* 294 */     String remaining = (pos > 0) ? input.substring(0, pos) : "";
/* 295 */     if (parts.size() == 1 && remaining.isEmpty()) {
/* 296 */       return extractUrl(parts.get(0));
/*     */     }
/* 298 */     Collections.reverse(parts);
/* 299 */     return extractUrl((TextComponent)((TextComponent.Builder)Component.text().content(remaining).append(parts)).build());
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String serialize(@NotNull Component component) {
/* 305 */     Cereal state = new Cereal();
/* 306 */     this.flattener.flatten(component, state);
/* 307 */     return state.toString();
/*     */   }
/*     */   
/*     */   private static boolean applyFormat(TextComponent.Builder builder, @NotNull TextFormat format) {
/* 311 */     if (format instanceof TextColor) {
/* 312 */       builder.colorIfAbsent((TextColor)format);
/* 313 */       return true;
/* 314 */     }  if (format instanceof TextDecoration) {
/* 315 */       builder.decoration((TextDecoration)format, TextDecoration.State.TRUE);
/* 316 */       return false;
/* 317 */     }  if (format instanceof Reset) {
/* 318 */       return true;
/*     */     }
/* 320 */     throw new IllegalArgumentException(String.format("unknown format '%s'", new Object[] { format.getClass() }));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public LegacyComponentSerializer.Builder toBuilder() {
/* 325 */     return new BuilderImpl(this);
/*     */   }
/*     */   
/*     */   private enum Reset implements TextFormat {
/* 329 */     INSTANCE;
/*     */   }
/*     */   
/*     */   private final class Cereal
/*     */     implements FlattenerListener {
/* 334 */     private final StringBuilder sb = new StringBuilder();
/* 335 */     private final StyleState style = new StyleState(); @Nullable
/*     */     private TextFormat lastWritten;
/* 337 */     private StyleState[] styles = new StyleState[8];
/* 338 */     private int head = -1;
/*     */ 
/*     */     
/*     */     public void pushStyle(@NotNull Style pushed) {
/* 342 */       int idx = ++this.head;
/* 343 */       if (idx >= this.styles.length) {
/* 344 */         this.styles = Arrays.<StyleState>copyOf(this.styles, this.styles.length * 2);
/*     */       }
/* 346 */       StyleState state = this.styles[idx];
/*     */       
/* 348 */       if (state == null) {
/* 349 */         this.styles[idx] = state = new StyleState();
/*     */       }
/*     */       
/* 352 */       if (idx > 0) {
/*     */ 
/*     */         
/* 355 */         state.set(this.styles[idx - 1]);
/*     */       } else {
/* 357 */         state.clear();
/*     */       } 
/*     */       
/* 360 */       state.apply(pushed);
/*     */     }
/*     */ 
/*     */     
/*     */     public void component(@NotNull String text) {
/* 365 */       if (!text.isEmpty()) {
/* 366 */         if (this.head < 0) throw new IllegalStateException("No style has been pushed!");
/*     */         
/* 368 */         this.styles[this.head].applyFormat();
/* 369 */         this.sb.append(text);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void popStyle(@NotNull Style style) {
/* 375 */       if (this.head-- < 0) {
/* 376 */         throw new IllegalStateException("Tried to pop beyond what was pushed!");
/*     */       }
/*     */     }
/*     */     
/*     */     void append(@NotNull TextFormat format) {
/* 381 */       if (this.lastWritten != format) {
/* 382 */         this.sb.append(LegacyComponentSerializerImpl.this.character).append(LegacyComponentSerializerImpl.this.toLegacyCode(format));
/*     */       }
/* 384 */       this.lastWritten = format;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 389 */       return this.sb.toString();
/*     */     }
/*     */     
/*     */     private Cereal() {}
/*     */     
/*     */     private final class StyleState
/*     */     {
/*     */       @Nullable
/*     */       private TextColor color;
/* 398 */       private final Set<TextDecoration> decorations = EnumSet.noneOf(TextDecoration.class);
/*     */       private boolean needsReset;
/*     */       
/*     */       void set(@NotNull StyleState that) {
/* 402 */         this.color = that.color;
/* 403 */         this.decorations.clear();
/* 404 */         this.decorations.addAll(that.decorations);
/*     */       }
/*     */       
/*     */       public void clear() {
/* 408 */         this.color = null;
/* 409 */         this.decorations.clear();
/*     */       }
/*     */       
/*     */       void apply(@NotNull Style component) {
/* 413 */         TextColor color = component.color();
/* 414 */         if (color != null) {
/* 415 */           this.color = color;
/*     */         }
/*     */         
/* 418 */         for (int i = 0, length = LegacyComponentSerializerImpl.DECORATIONS.length; i < length; i++) {
/* 419 */           TextDecoration decoration = LegacyComponentSerializerImpl.DECORATIONS[i];
/* 420 */           switch (component.decoration(decoration)) {
/*     */             case TRUE:
/* 422 */               this.decorations.add(decoration);
/*     */               break;
/*     */             case FALSE:
/* 425 */               if (this.decorations.remove(decoration)) {
/* 426 */                 this.needsReset = true;
/*     */               }
/*     */               break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       void applyFormat() {
/* 435 */         boolean colorChanged = (this.color != LegacyComponentSerializerImpl.Cereal.this.style.color);
/* 436 */         if (this.needsReset) {
/* 437 */           if (!colorChanged) {
/* 438 */             LegacyComponentSerializerImpl.Cereal.this.append(LegacyComponentSerializerImpl.Reset.INSTANCE);
/*     */           }
/* 440 */           this.needsReset = false;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 445 */         if (colorChanged || LegacyComponentSerializerImpl.Cereal.this.lastWritten == LegacyComponentSerializerImpl.Reset.INSTANCE) {
/* 446 */           applyFullFormat();
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 452 */         if (!this.decorations.containsAll(LegacyComponentSerializerImpl.Cereal.this.style.decorations)) {
/* 453 */           applyFullFormat();
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 458 */         for (TextDecoration decoration : this.decorations) {
/* 459 */           if (LegacyComponentSerializerImpl.Cereal.this.style.decorations.add(decoration)) {
/* 460 */             LegacyComponentSerializerImpl.Cereal.this.append((TextFormat)decoration);
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/*     */       private void applyFullFormat() {
/* 466 */         if (this.color != null) {
/* 467 */           LegacyComponentSerializerImpl.Cereal.this.append((TextFormat)this.color);
/*     */         } else {
/* 469 */           LegacyComponentSerializerImpl.Cereal.this.append(LegacyComponentSerializerImpl.Reset.INSTANCE);
/*     */         } 
/* 471 */         LegacyComponentSerializerImpl.Cereal.this.style.color = this.color;
/*     */         
/* 473 */         for (TextDecoration decoration : this.decorations) {
/* 474 */           LegacyComponentSerializerImpl.Cereal.this.append((TextFormat)decoration);
/*     */         }
/*     */         
/* 477 */         LegacyComponentSerializerImpl.Cereal.this.style.decorations.clear();
/* 478 */         LegacyComponentSerializerImpl.Cereal.this.style.decorations.addAll(this.decorations);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   static final class BuilderImpl implements LegacyComponentSerializer.Builder {
/* 484 */     private char character = '§';
/* 485 */     private char hexCharacter = '#';
/* 486 */     private TextReplacementConfig urlReplacementConfig = null;
/*     */     private boolean hexColours = false;
/*     */     private boolean useTerriblyStupidHexFormat = false;
/* 489 */     private ComponentFlattener flattener = ComponentFlattener.basic();
/*     */     
/*     */     BuilderImpl() {
/* 492 */       LegacyComponentSerializerImpl.BUILDER.accept(this);
/*     */     }
/*     */     
/*     */     BuilderImpl(@NotNull LegacyComponentSerializerImpl serializer) {
/* 496 */       this();
/* 497 */       this.character = serializer.character;
/* 498 */       this.hexCharacter = serializer.hexCharacter;
/* 499 */       this.urlReplacementConfig = serializer.urlReplacementConfig;
/* 500 */       this.hexColours = serializer.hexColours;
/* 501 */       this.useTerriblyStupidHexFormat = serializer.useTerriblyStupidHexFormat;
/* 502 */       this.flattener = serializer.flattener;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public LegacyComponentSerializer.Builder character(char legacyCharacter) {
/* 507 */       this.character = legacyCharacter;
/* 508 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public LegacyComponentSerializer.Builder hexCharacter(char legacyHexCharacter) {
/* 513 */       this.hexCharacter = legacyHexCharacter;
/* 514 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public LegacyComponentSerializer.Builder extractUrls() {
/* 519 */       return extractUrls(LegacyComponentSerializerImpl.DEFAULT_URL_PATTERN, null);
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public LegacyComponentSerializer.Builder extractUrls(@NotNull Pattern pattern) {
/* 524 */       return extractUrls(pattern, null);
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public LegacyComponentSerializer.Builder extractUrls(@Nullable Style style) {
/* 529 */       return extractUrls(LegacyComponentSerializerImpl.DEFAULT_URL_PATTERN, style);
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public LegacyComponentSerializer.Builder extractUrls(@NotNull Pattern pattern, @Nullable Style style) {
/* 534 */       Objects.requireNonNull(pattern, "pattern");
/* 535 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 544 */         .urlReplacementConfig = (TextReplacementConfig)TextReplacementConfig.builder().match(pattern).replacement(url -> { String clickUrl = url.content(); if (!LegacyComponentSerializerImpl.URL_SCHEME_PATTERN.matcher(clickUrl).find()) clickUrl = "http://" + clickUrl;  return (ComponentLike)((style == null) ? url : (TextComponent.Builder)url.style(style)).clickEvent(ClickEvent.openUrl(clickUrl)); }).build();
/* 545 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public LegacyComponentSerializer.Builder hexColors() {
/* 550 */       this.hexColours = true;
/* 551 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public LegacyComponentSerializer.Builder useUnusualXRepeatedCharacterHexFormat() {
/* 556 */       this.useTerriblyStupidHexFormat = true;
/* 557 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public LegacyComponentSerializer.Builder flattener(@NotNull ComponentFlattener flattener) {
/* 562 */       this.flattener = Objects.<ComponentFlattener>requireNonNull(flattener, "flattener");
/* 563 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public LegacyComponentSerializer build() {
/* 568 */       return new LegacyComponentSerializerImpl(this.character, this.hexCharacter, this.urlReplacementConfig, this.hexColours, this.useTerriblyStupidHexFormat, this.flattener);
/*     */     }
/*     */   }
/*     */   
/*     */   enum FormatCodeType {
/* 573 */     MOJANG_LEGACY,
/* 574 */     KYORI_HEX,
/* 575 */     BUNGEECORD_UNUSUAL_HEX;
/*     */   }
/*     */   
/*     */   static final class DecodedFormat {
/*     */     final LegacyComponentSerializerImpl.FormatCodeType encodedFormat;
/*     */     final TextFormat format;
/*     */     
/*     */     private DecodedFormat(LegacyComponentSerializerImpl.FormatCodeType encodedFormat, TextFormat format) {
/* 583 */       if (format == null) {
/* 584 */         throw new IllegalStateException("No format found");
/*     */       }
/* 586 */       this.encodedFormat = encodedFormat;
/* 587 */       this.format = format;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\serializer\legacy\LegacyComponentSerializerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
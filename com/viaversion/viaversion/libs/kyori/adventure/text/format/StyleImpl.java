/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text.format;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
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
/*     */ final class StyleImpl
/*     */   implements Style
/*     */ {
/*  43 */   static final StyleImpl EMPTY = new StyleImpl(null, null, DecorationMap.EMPTY, null, null, null);
/*     */   
/*     */   @Nullable
/*     */   final Key font;
/*     */   
/*     */   @Nullable
/*     */   final TextColor color;
/*     */   @NotNull
/*     */   final DecorationMap decorations;
/*     */   @Nullable
/*     */   final ClickEvent clickEvent;
/*     */   @Nullable
/*     */   final HoverEvent<?> hoverEvent;
/*     */   @Nullable
/*     */   final String insertion;
/*     */   
/*     */   StyleImpl(@Nullable Key font, @Nullable TextColor color, @NotNull Map<TextDecoration, TextDecoration.State> decorations, @Nullable ClickEvent clickEvent, @Nullable HoverEvent<?> hoverEvent, @Nullable String insertion) {
/*  60 */     this.font = font;
/*  61 */     this.color = color;
/*  62 */     this.decorations = DecorationMap.fromMap(decorations);
/*  63 */     this.clickEvent = clickEvent;
/*  64 */     this.hoverEvent = hoverEvent;
/*  65 */     this.insertion = insertion;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Key font() {
/*  70 */     return this.font;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style font(@Nullable Key font) {
/*  75 */     if (Objects.equals(this.font, font)) return this; 
/*  76 */     return new StyleImpl(font, this.color, this.decorations, this.clickEvent, this.hoverEvent, this.insertion);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public TextColor color() {
/*  81 */     return this.color;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style color(@Nullable TextColor color) {
/*  86 */     if (Objects.equals(this.color, color)) return this; 
/*  87 */     return new StyleImpl(this.font, color, this.decorations, this.clickEvent, this.hoverEvent, this.insertion);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style colorIfAbsent(@Nullable TextColor color) {
/*  92 */     if (this.color == null) {
/*  93 */       return color(color);
/*     */     }
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TextDecoration.State decoration(@NotNull TextDecoration decoration) {
/* 101 */     TextDecoration.State state = this.decorations.get(decoration);
/* 102 */     if (state != null) {
/* 103 */       return state;
/*     */     }
/* 105 */     throw new IllegalArgumentException(String.format("unknown decoration '%s'", new Object[] { decoration }));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style decoration(@NotNull TextDecoration decoration, TextDecoration.State state) {
/* 110 */     Objects.requireNonNull(state, "state");
/* 111 */     if (decoration(decoration) == state) return this; 
/* 112 */     return new StyleImpl(this.font, this.color, this.decorations.with(decoration, state), this.clickEvent, this.hoverEvent, this.insertion);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style decorationIfAbsent(@NotNull TextDecoration decoration, TextDecoration.State state) {
/* 117 */     Objects.requireNonNull(state, "state");
/* 118 */     TextDecoration.State oldState = this.decorations.get(decoration);
/* 119 */     if (oldState == TextDecoration.State.NOT_SET) {
/* 120 */       return new StyleImpl(this.font, this.color, this.decorations.with(decoration, state), this.clickEvent, this.hoverEvent, this.insertion);
/*     */     }
/* 122 */     if (oldState != null) {
/* 123 */       return this;
/*     */     }
/* 125 */     throw new IllegalArgumentException(String.format("unknown decoration '%s'", new Object[] { decoration }));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Map<TextDecoration, TextDecoration.State> decorations() {
/* 130 */     return this.decorations;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style decorations(@NotNull Map<TextDecoration, TextDecoration.State> decorations) {
/* 135 */     return new StyleImpl(this.font, this.color, DecorationMap.merge(decorations, this.decorations), this.clickEvent, this.hoverEvent, this.insertion);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ClickEvent clickEvent() {
/* 140 */     return this.clickEvent;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style clickEvent(@Nullable ClickEvent event) {
/* 145 */     return new StyleImpl(this.font, this.color, this.decorations, event, this.hoverEvent, this.insertion);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public HoverEvent<?> hoverEvent() {
/* 150 */     return this.hoverEvent;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style hoverEvent(@Nullable HoverEventSource<?> source) {
/* 155 */     return new StyleImpl(this.font, this.color, this.decorations, this.clickEvent, HoverEventSource.unbox(source), this.insertion);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String insertion() {
/* 160 */     return this.insertion;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style insertion(@Nullable String insertion) {
/* 165 */     if (Objects.equals(this.insertion, insertion)) return this; 
/* 166 */     return new StyleImpl(this.font, this.color, this.decorations, this.clickEvent, this.hoverEvent, insertion);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style merge(@NotNull Style that, Style.Merge.Strategy strategy, @NotNull Set<Style.Merge> merges) {
/* 171 */     if (nothingToMerge(that, strategy, merges)) {
/* 172 */       return this;
/*     */     }
/*     */     
/* 175 */     if (isEmpty() && Style.Merge.hasAll(merges))
/*     */     {
/*     */       
/* 178 */       return that;
/*     */     }
/*     */     
/* 181 */     Style.Builder builder = toBuilder();
/* 182 */     builder.merge(that, strategy, merges);
/* 183 */     return builder.build();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style unmerge(@NotNull Style that) {
/* 188 */     if (isEmpty())
/*     */     {
/* 190 */       return this;
/*     */     }
/*     */     
/* 193 */     Style.Builder builder = new BuilderImpl(this);
/*     */     
/* 195 */     if (Objects.equals(font(), that.font())) {
/* 196 */       builder.font((Key)null);
/*     */     }
/*     */     
/* 199 */     if (Objects.equals(color(), that.color())) {
/* 200 */       builder.color((TextColor)null);
/*     */     }
/*     */     
/* 203 */     for (int i = 0, length = DecorationMap.DECORATIONS.length; i < length; i++) {
/* 204 */       TextDecoration decoration = DecorationMap.DECORATIONS[i];
/* 205 */       if (decoration(decoration) == that.decoration(decoration)) {
/* 206 */         builder.decoration(decoration, TextDecoration.State.NOT_SET);
/*     */       }
/*     */     } 
/*     */     
/* 210 */     if (Objects.equals(clickEvent(), that.clickEvent())) {
/* 211 */       builder.clickEvent((ClickEvent)null);
/*     */     }
/*     */     
/* 214 */     if (Objects.equals(hoverEvent(), that.hoverEvent())) {
/* 215 */       builder.hoverEvent((HoverEventSource<?>)null);
/*     */     }
/*     */     
/* 218 */     if (Objects.equals(insertion(), that.insertion())) {
/* 219 */       builder.insertion((String)null);
/*     */     }
/*     */     
/* 222 */     return builder.build();
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean nothingToMerge(@NotNull Style mergeFrom, Style.Merge.Strategy strategy, @NotNull Set<Style.Merge> merges) {
/* 227 */     if (strategy == Style.Merge.Strategy.NEVER) return true; 
/* 228 */     if (mergeFrom.isEmpty()) return true; 
/* 229 */     if (merges.isEmpty()) return true; 
/* 230 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 235 */     return (this == EMPTY);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Style.Builder toBuilder() {
/* 240 */     return new BuilderImpl(this);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<? extends ExaminableProperty> examinableProperties() {
/* 245 */     return Stream.concat(this.decorations
/* 246 */         .examinableProperties(), 
/* 247 */         Stream.of(new ExaminableProperty[] {
/* 248 */             ExaminableProperty.of("color", this.color), 
/* 249 */             ExaminableProperty.of("clickEvent", this.clickEvent), 
/* 250 */             ExaminableProperty.of("hoverEvent", this.hoverEvent), 
/* 251 */             ExaminableProperty.of("insertion", this.insertion), 
/* 252 */             ExaminableProperty.of("font", this.font)
/*     */           }));
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String toString() {
/* 259 */     return Internals.toString(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/* 264 */     if (this == other) return true; 
/* 265 */     if (!(other instanceof StyleImpl)) return false; 
/* 266 */     StyleImpl that = (StyleImpl)other;
/* 267 */     return (Objects.equals(this.color, that.color) && this.decorations
/* 268 */       .equals(that.decorations) && 
/* 269 */       Objects.equals(this.clickEvent, that.clickEvent) && 
/* 270 */       Objects.equals(this.hoverEvent, that.hoverEvent) && 
/* 271 */       Objects.equals(this.insertion, that.insertion) && 
/* 272 */       Objects.equals(this.font, that.font));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 277 */     int result = Objects.hashCode(this.color);
/* 278 */     result = 31 * result + this.decorations.hashCode();
/* 279 */     result = 31 * result + Objects.hashCode(this.clickEvent);
/* 280 */     result = 31 * result + Objects.hashCode(this.hoverEvent);
/* 281 */     result = 31 * result + Objects.hashCode(this.insertion);
/* 282 */     result = 31 * result + Objects.hashCode(this.font);
/* 283 */     return result;
/*     */   }
/*     */   
/*     */   static final class BuilderImpl
/*     */     implements Style.Builder {
/*     */     @Nullable
/*     */     Key font;
/*     */     @Nullable
/*     */     TextColor color;
/*     */     final Map<TextDecoration, TextDecoration.State> decorations;
/*     */     
/*     */     BuilderImpl() {
/* 295 */       this.decorations = new EnumMap<>(DecorationMap.EMPTY); } @Nullable
/*     */     ClickEvent clickEvent; @Nullable
/*     */     HoverEvent<?> hoverEvent; @Nullable
/*     */     String insertion; BuilderImpl(@NotNull StyleImpl style) {
/* 299 */       this.color = style.color;
/* 300 */       this.decorations = new EnumMap<>(style.decorations);
/* 301 */       this.clickEvent = style.clickEvent;
/* 302 */       this.hoverEvent = style.hoverEvent;
/* 303 */       this.insertion = style.insertion;
/* 304 */       this.font = style.font;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Style.Builder font(@Nullable Key font) {
/* 309 */       this.font = font;
/* 310 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Style.Builder color(@Nullable TextColor color) {
/* 315 */       this.color = color;
/* 316 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Style.Builder colorIfAbsent(@Nullable TextColor color) {
/* 321 */       if (this.color == null) {
/* 322 */         this.color = color;
/*     */       }
/* 324 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Style.Builder decoration(@NotNull TextDecoration decoration, TextDecoration.State state) {
/* 329 */       Objects.requireNonNull(state, "state");
/* 330 */       Objects.requireNonNull(decoration, "decoration");
/* 331 */       this.decorations.put(decoration, state);
/* 332 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Style.Builder decorationIfAbsent(@NotNull TextDecoration decoration, TextDecoration.State state) {
/* 337 */       Objects.requireNonNull(state, "state");
/* 338 */       TextDecoration.State oldState = this.decorations.get(decoration);
/* 339 */       if (oldState == TextDecoration.State.NOT_SET) {
/* 340 */         this.decorations.put(decoration, state);
/*     */       }
/* 342 */       if (oldState != null) {
/* 343 */         return this;
/*     */       }
/* 345 */       throw new IllegalArgumentException(String.format("unknown decoration '%s'", new Object[] { decoration }));
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Style.Builder clickEvent(@Nullable ClickEvent event) {
/* 350 */       this.clickEvent = event;
/* 351 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Style.Builder hoverEvent(@Nullable HoverEventSource<?> source) {
/* 356 */       this.hoverEvent = HoverEventSource.unbox(source);
/* 357 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Style.Builder insertion(@Nullable String insertion) {
/* 362 */       this.insertion = insertion;
/* 363 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Style.Builder merge(@NotNull Style that, Style.Merge.Strategy strategy, @NotNull Set<Style.Merge> merges) {
/* 368 */       Objects.requireNonNull(that, "style");
/* 369 */       Objects.requireNonNull(strategy, "strategy");
/* 370 */       Objects.requireNonNull(merges, "merges");
/*     */       
/* 372 */       if (StyleImpl.nothingToMerge(that, strategy, merges)) {
/* 373 */         return this;
/*     */       }
/*     */       
/* 376 */       if (merges.contains(Style.Merge.COLOR)) {
/* 377 */         TextColor color = that.color();
/* 378 */         if (color != null && (
/* 379 */           strategy == Style.Merge.Strategy.ALWAYS || (strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET && this.color == null))) {
/* 380 */           color(color);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 385 */       if (merges.contains(Style.Merge.DECORATIONS)) {
/* 386 */         for (int i = 0, length = DecorationMap.DECORATIONS.length; i < length; i++) {
/* 387 */           TextDecoration decoration = DecorationMap.DECORATIONS[i];
/* 388 */           TextDecoration.State state = that.decoration(decoration);
/* 389 */           if (state != TextDecoration.State.NOT_SET) {
/* 390 */             if (strategy == Style.Merge.Strategy.ALWAYS) {
/* 391 */               decoration(decoration, state);
/* 392 */             } else if (strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET) {
/* 393 */               decorationIfAbsent(decoration, state);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 399 */       if (merges.contains(Style.Merge.EVENTS)) {
/* 400 */         ClickEvent clickEvent = that.clickEvent();
/* 401 */         if (clickEvent != null && (
/* 402 */           strategy == Style.Merge.Strategy.ALWAYS || (strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET && this.clickEvent == null))) {
/* 403 */           clickEvent(clickEvent);
/*     */         }
/*     */ 
/*     */         
/* 407 */         HoverEvent<?> hoverEvent = that.hoverEvent();
/* 408 */         if (hoverEvent != null && (
/* 409 */           strategy == Style.Merge.Strategy.ALWAYS || (strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET && this.hoverEvent == null))) {
/* 410 */           hoverEvent((HoverEventSource<?>)hoverEvent);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 415 */       if (merges.contains(Style.Merge.INSERTION)) {
/* 416 */         String insertion = that.insertion();
/* 417 */         if (insertion != null && (
/* 418 */           strategy == Style.Merge.Strategy.ALWAYS || (strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET && this.insertion == null))) {
/* 419 */           insertion(insertion);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 424 */       if (merges.contains(Style.Merge.FONT)) {
/* 425 */         Key font = that.font();
/* 426 */         if (font != null && (
/* 427 */           strategy == Style.Merge.Strategy.ALWAYS || (strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET && this.font == null))) {
/* 428 */           font(font);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 433 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public StyleImpl build() {
/* 438 */       if (isEmpty()) {
/* 439 */         return StyleImpl.EMPTY;
/*     */       }
/* 441 */       return new StyleImpl(this.font, this.color, this.decorations, this.clickEvent, this.hoverEvent, this.insertion);
/*     */     }
/*     */     
/*     */     private boolean isEmpty() {
/* 445 */       return (this.color == null && this.decorations
/* 446 */         .values().stream().allMatch(state -> (state == TextDecoration.State.NOT_SET)) && this.clickEvent == null && this.hoverEvent == null && this.insertion == null && this.font == null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\format\StyleImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
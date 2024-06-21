/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text.format;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import org.jetbrains.annotations.ApiStatus.NonExtendable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @NonExtendable
/*     */ public interface StyleSetter<T extends StyleSetter<?>>
/*     */ {
/*     */   @NotNull
/*     */   T font(@Nullable Key paramKey);
/*     */   
/*     */   @NotNull
/*     */   T color(@Nullable TextColor paramTextColor);
/*     */   
/*     */   @NotNull
/*     */   T colorIfAbsent(@Nullable TextColor paramTextColor);
/*     */   
/*     */   @NotNull
/*     */   default T decorate(@NotNull TextDecoration decoration) {
/*  84 */     return decoration(decoration, TextDecoration.State.TRUE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   T decorate(@NotNull TextDecoration... decorations) {
/*  95 */     Map<TextDecoration, TextDecoration.State> map = new EnumMap<>(TextDecoration.class);
/*  96 */     for (int i = 0, length = decorations.length; i < length; i++) {
/*  97 */       map.put(decorations[i], TextDecoration.State.TRUE);
/*     */     }
/*  99 */     return decorations(map);
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
/*     */   default T decoration(@NotNull TextDecoration decoration, boolean flag) {
/* 112 */     return decoration(decoration, TextDecoration.State.byBoolean(flag));
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
/*     */   T decoration(@NotNull TextDecoration paramTextDecoration, TextDecoration.State paramState);
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
/*     */   T decorationIfAbsent(@NotNull TextDecoration paramTextDecoration, TextDecoration.State paramState);
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
/*     */   T decorations(@NotNull Map<TextDecoration, TextDecoration.State> paramMap);
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
/*     */   default T decorations(@NotNull Set<TextDecoration> decorations, boolean flag) {
/* 159 */     return decorations((Map<TextDecoration, TextDecoration.State>)decorations.stream().collect(Collectors.toMap(Function.identity(), decoration -> TextDecoration.State.byBoolean(flag))));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   T clickEvent(@Nullable ClickEvent paramClickEvent);
/*     */   
/*     */   @NotNull
/*     */   T hoverEvent(@Nullable HoverEventSource<?> paramHoverEventSource);
/*     */   
/*     */   @NotNull
/*     */   T insertion(@Nullable String paramString);
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\format\StyleSetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
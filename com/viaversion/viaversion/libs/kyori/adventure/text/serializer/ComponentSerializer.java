/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text.serializer;
/*     */ 
/*     */ import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
/*     */ import org.jetbrains.annotations.Contract;
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
/*     */ public interface ComponentSerializer<I extends com.viaversion.viaversion.libs.kyori.adventure.text.Component, O extends com.viaversion.viaversion.libs.kyori.adventure.text.Component, R>
/*     */ {
/*     */   @NotNull
/*     */   O deserialize(@NotNull R paramR);
/*     */   
/*     */   @Deprecated
/*     */   @ScheduledForRemoval(inVersion = "5.0.0")
/*     */   @Contract(value = "!null -> !null; null -> null", pure = true)
/*     */   @Nullable
/*     */   default O deseializeOrNull(@Nullable R input) {
/*  64 */     return deserializeOrNull(input);
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
/*     */   @Contract(value = "!null -> !null; null -> null", pure = true)
/*     */   @Nullable
/*     */   default O deserializeOrNull(@Nullable R input) {
/*  78 */     return deserializeOr(input, null);
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
/*     */   @Contract(value = "!null, _ -> !null; null, _ -> param2", pure = true)
/*     */   @Nullable
/*     */   default O deserializeOr(@Nullable R input, @Nullable O fallback) {
/*  93 */     if (input == null) return fallback;
/*     */     
/*  95 */     return deserialize(input);
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
/*     */   R serialize(@NotNull I paramI);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract(value = "!null -> !null; null -> null", pure = true)
/*     */   @Nullable
/*     */   default R serializeOrNull(@Nullable I component) {
/* 118 */     return serializeOr(component, null);
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
/*     */   @Contract(value = "!null, _ -> !null; null, _ -> param2", pure = true)
/*     */   @Nullable
/*     */   default R serializeOr(@Nullable I component, @Nullable R fallback) {
/* 133 */     if (component == null) return fallback;
/*     */     
/* 135 */     return serialize(component);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\serializer\ComponentSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
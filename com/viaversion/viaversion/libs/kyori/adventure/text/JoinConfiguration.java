/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.builder.AbstractBuilder;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.Examinable;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import org.jetbrains.annotations.ApiStatus.NonExtendable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public interface JoinConfiguration
/*     */   extends Buildable<JoinConfiguration, JoinConfiguration.Builder>, Examinable
/*     */ {
/*     */   @NotNull
/*     */   static Builder builder() {
/*  95 */     return new JoinConfigurationImpl.BuilderImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static JoinConfiguration noSeparators() {
/* 105 */     return JoinConfigurationImpl.NULL;
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
/*     */   static JoinConfiguration newlines() {
/* 118 */     return JoinConfigurationImpl.STANDARD_NEW_LINES;
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
/*     */   static JoinConfiguration commas(boolean spaces) {
/* 132 */     return spaces ? JoinConfigurationImpl.STANDARD_COMMA_SPACE_SEPARATED : JoinConfigurationImpl.STANDARD_COMMA_SEPARATED;
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
/*     */   static JoinConfiguration arrayLike() {
/* 147 */     return JoinConfigurationImpl.STANDARD_ARRAY_LIKE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static JoinConfiguration separator(@Nullable ComponentLike separator) {
/* 158 */     if (separator == null) return JoinConfigurationImpl.NULL; 
/* 159 */     return (JoinConfiguration)builder().separator(separator).build();
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
/*     */   static JoinConfiguration separators(@Nullable ComponentLike separator, @Nullable ComponentLike lastSeparator) {
/* 171 */     if (separator == null && lastSeparator == null) return JoinConfigurationImpl.NULL; 
/* 172 */     return (JoinConfiguration)builder().separator(separator).lastSeparator(lastSeparator).build();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   Component prefix();
/*     */   
/*     */   @Nullable
/*     */   Component suffix();
/*     */   
/*     */   @Nullable
/*     */   Component separator();
/*     */   
/*     */   @Nullable
/*     */   Component lastSeparator();
/*     */   
/*     */   @Nullable
/*     */   Component lastSeparatorIfSerial();
/*     */   
/*     */   @NotNull
/*     */   Function<ComponentLike, Component> convertor();
/*     */   
/*     */   @NotNull
/*     */   Predicate<ComponentLike> predicate();
/*     */   
/*     */   @NotNull
/*     */   Style parentStyle();
/*     */   
/*     */   public static interface Builder extends AbstractBuilder<JoinConfiguration>, Buildable.Builder<JoinConfiguration> {
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder prefix(@Nullable ComponentLike param1ComponentLike);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder suffix(@Nullable ComponentLike param1ComponentLike);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder separator(@Nullable ComponentLike param1ComponentLike);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder lastSeparator(@Nullable ComponentLike param1ComponentLike);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder lastSeparatorIfSerial(@Nullable ComponentLike param1ComponentLike);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder convertor(@NotNull Function<ComponentLike, Component> param1Function);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder predicate(@NotNull Predicate<ComponentLike> param1Predicate);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder parentStyle(@NotNull Style param1Style);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\JoinConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
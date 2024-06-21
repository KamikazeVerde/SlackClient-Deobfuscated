/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.builder.AbstractBuilder;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.IntFunction2;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.Examinable;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.regex.MatchResult;
/*     */ import java.util.regex.Pattern;
/*     */ import org.intellij.lang.annotations.RegExp;
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
/*     */ public interface TextReplacementConfig
/*     */   extends Buildable<TextReplacementConfig, TextReplacementConfig.Builder>, Examinable
/*     */ {
/*     */   @NotNull
/*     */   static Builder builder() {
/*  57 */     return new TextReplacementConfigImpl.Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   Pattern matchPattern();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface Condition
/*     */   {
/*     */     @NotNull
/*     */     PatternReplacementResult shouldReplace(@NotNull MatchResult param1MatchResult, int param1Int1, int param1Int2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Builder
/*     */     extends AbstractBuilder<TextReplacementConfig>, Buildable.Builder<TextReplacementConfig>
/*     */   {
/*     */     @Contract("_ -> this")
/*     */     default Builder matchLiteral(String literal) {
/*  91 */       return match(Pattern.compile(literal, 16));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     default Builder match(@NotNull @RegExp String pattern) {
/* 103 */       return match(Pattern.compile(pattern));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     default Builder once() {
/* 129 */       return times(1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     default Builder times(int times) {
/* 141 */       return condition((index, replaced) -> (replaced < times) ? PatternReplacementResult.REPLACE : PatternReplacementResult.STOP);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     default Builder condition(@NotNull IntFunction2<PatternReplacementResult> condition) {
/* 154 */       return condition((result, matchCount, replaced) -> (PatternReplacementResult)condition.apply(matchCount, replaced));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     default Builder replacement(@NotNull String replacement) {
/* 183 */       Objects.requireNonNull(replacement, "replacement");
/* 184 */       return replacement(builder -> builder.content(replacement));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     default Builder replacement(@Nullable ComponentLike replacement) {
/* 196 */       Component baked = ComponentLike.unbox(replacement);
/* 197 */       return replacement((result, input) -> baked);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     default Builder replacement(@NotNull Function<TextComponent.Builder, ComponentLike> replacement) {
/* 209 */       Objects.requireNonNull(replacement, "replacement");
/* 210 */       return replacement((result, input) -> (ComponentLike)replacement.apply(input));
/*     */     }
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder match(@NotNull Pattern param1Pattern);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder condition(@NotNull TextReplacementConfig.Condition param1Condition);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder replacement(@NotNull BiFunction<MatchResult, TextComponent.Builder, ComponentLike> param1BiFunction);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\TextReplacementConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
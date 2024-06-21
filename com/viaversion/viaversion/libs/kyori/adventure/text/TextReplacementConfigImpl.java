/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.regex.MatchResult;
/*     */ import java.util.regex.Pattern;
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
/*     */ final class TextReplacementConfigImpl
/*     */   implements TextReplacementConfig
/*     */ {
/*     */   private final Pattern matchPattern;
/*     */   private final BiFunction<MatchResult, TextComponent.Builder, ComponentLike> replacement;
/*     */   private final TextReplacementConfig.Condition continuer;
/*     */   
/*     */   TextReplacementConfigImpl(Builder builder) {
/*  43 */     this.matchPattern = builder.matchPattern;
/*  44 */     this.replacement = builder.replacement;
/*  45 */     this.continuer = builder.continuer;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Pattern matchPattern() {
/*  50 */     return this.matchPattern;
/*     */   }
/*     */   
/*     */   TextReplacementRenderer.State createState() {
/*  54 */     return new TextReplacementRenderer.State(this.matchPattern, this.replacement, this.continuer);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextReplacementConfig.Builder toBuilder() {
/*  59 */     return new Builder(this);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<? extends ExaminableProperty> examinableProperties() {
/*  64 */     return Stream.of(new ExaminableProperty[] {
/*  65 */           ExaminableProperty.of("matchPattern", this.matchPattern), 
/*  66 */           ExaminableProperty.of("replacement", this.replacement), 
/*  67 */           ExaminableProperty.of("continuer", this.continuer)
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  73 */     return Internals.toString(this);
/*     */   }
/*     */   
/*     */   static final class Builder
/*     */     implements TextReplacementConfig.Builder {
/*     */     @Nullable
/*     */     Pattern matchPattern;
/*     */     @Nullable
/*     */     BiFunction<MatchResult, TextComponent.Builder, ComponentLike> replacement;
/*     */     TextReplacementConfig.Condition continuer = (matchResult, index, replacement) -> PatternReplacementResult.REPLACE;
/*     */     
/*     */     Builder(TextReplacementConfigImpl instance) {
/*  85 */       this.matchPattern = instance.matchPattern;
/*  86 */       this.replacement = instance.replacement;
/*  87 */       this.continuer = instance.continuer;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Builder match(@NotNull Pattern pattern) {
/*  92 */       this.matchPattern = Objects.<Pattern>requireNonNull(pattern, "pattern");
/*  93 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Builder condition(TextReplacementConfig.Condition condition) {
/*  98 */       this.continuer = Objects.<TextReplacementConfig.Condition>requireNonNull(condition, "continuation");
/*  99 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Builder replacement(@NotNull BiFunction<MatchResult, TextComponent.Builder, ComponentLike> replacement) {
/* 104 */       this.replacement = Objects.<BiFunction<MatchResult, TextComponent.Builder, ComponentLike>>requireNonNull(replacement, "replacement");
/* 105 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public TextReplacementConfig build() {
/* 110 */       if (this.matchPattern == null) throw new IllegalStateException("A pattern must be provided to match against"); 
/* 111 */       if (this.replacement == null) throw new IllegalStateException("A replacement action must be provided"); 
/* 112 */       return new TextReplacementConfigImpl(this);
/*     */     }
/*     */     
/*     */     Builder() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\TextReplacementConfigImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
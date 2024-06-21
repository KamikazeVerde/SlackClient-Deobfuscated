/*     */ package com.viaversion.viaversion.libs.kyori.adventure.title;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Ticks;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.Examinable;
/*     */ import java.time.Duration;
/*     */ import org.jetbrains.annotations.ApiStatus.NonExtendable;
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
/*     */ @NonExtendable
/*     */ public interface Title
/*     */   extends Examinable
/*     */ {
/*  48 */   public static final Times DEFAULT_TIMES = Times.times(Ticks.duration(10L), Ticks.duration(70L), Ticks.duration(20L));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Title title(@NotNull Component title, @NotNull Component subtitle) {
/*  59 */     return title(title, subtitle, DEFAULT_TIMES);
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
/*     */   static Title title(@NotNull Component title, @NotNull Component subtitle, @Nullable Times times) {
/*  72 */     return new TitleImpl(title, subtitle, times);
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
/*     */   Component title();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   Component subtitle();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   Times times();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <T> T part(@NotNull TitlePart<T> paramTitlePart);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Times
/*     */     extends Examinable
/*     */   {
/*     */     @Deprecated
/*     */     @ScheduledForRemoval(inVersion = "5.0.0")
/*     */     @NotNull
/*     */     static Times of(@NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut) {
/* 128 */       return times(fadeIn, stay, fadeOut);
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
/*     */     @NotNull
/*     */     static Times times(@NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut) {
/* 141 */       return new TitleImpl.TimesImpl(fadeIn, stay, fadeOut);
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     Duration fadeIn();
/*     */     
/*     */     @NotNull
/*     */     Duration stay();
/*     */     
/*     */     @NotNull
/*     */     Duration fadeOut();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\title\Title.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
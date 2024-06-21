/*    */ package com.viaversion.viaversion.libs.kyori.adventure.internal.properties;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.ApiStatus.NonExtendable;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class AdventureProperties
/*    */ {
/* 43 */   public static final Property<Boolean> DEBUG = property("debug", Boolean::parseBoolean, Boolean.valueOf(false));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   public static final Property<String> DEFAULT_TRANSLATION_LOCALE = property("defaultTranslationLocale", Function.identity(), null);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public static final Property<Boolean> SERVICE_LOAD_FAILURES_ARE_FATAL = property("serviceLoadFailuresAreFatal", Boolean::parseBoolean, Boolean.TRUE);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 61 */   public static final Property<Boolean> TEXT_WARN_WHEN_LEGACY_FORMATTING_DETECTED = property("text.warnWhenLegacyFormattingDetected", Boolean::parseBoolean, Boolean.FALSE);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public static <T> Property<T> property(@NotNull String name, @NotNull Function<String, T> parser, @Nullable T defaultValue) {
/* 77 */     return AdventurePropertiesImpl.property(name, parser, defaultValue);
/*    */   }
/*    */   
/*    */   @Internal
/*    */   @NonExtendable
/*    */   public static interface Property<T> {
/*    */     @Nullable
/*    */     T value();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\internal\properties\AdventureProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
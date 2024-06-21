/*    */ package com.viaversion.viaversion.libs.kyori.adventure.translation;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.renderer.TranslatableComponentRenderer;
/*    */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*    */ import java.text.MessageFormat;
/*    */ import java.util.Collections;
/*    */ import java.util.Locale;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.stream.Stream;
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
/*    */ final class GlobalTranslatorImpl
/*    */   implements GlobalTranslator
/*    */ {
/* 41 */   private static final Key NAME = Key.key("adventure", "global");
/* 42 */   static final GlobalTranslatorImpl INSTANCE = new GlobalTranslatorImpl();
/* 43 */   final TranslatableComponentRenderer<Locale> renderer = TranslatableComponentRenderer.usingTranslationSource(this);
/* 44 */   private final Set<Translator> sources = Collections.newSetFromMap(new ConcurrentHashMap<>());
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public Key name() {
/* 51 */     return NAME;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Iterable<? extends Translator> sources() {
/* 56 */     return Collections.unmodifiableSet(this.sources);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean addSource(@NotNull Translator source) {
/* 61 */     Objects.requireNonNull(source, "source");
/* 62 */     if (source == this) throw new IllegalArgumentException("GlobalTranslationSource"); 
/* 63 */     return this.sources.add(source);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean removeSource(@NotNull Translator source) {
/* 68 */     Objects.requireNonNull(source, "source");
/* 69 */     return this.sources.remove(source);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public MessageFormat translate(@NotNull String key, @NotNull Locale locale) {
/* 74 */     Objects.requireNonNull(key, "key");
/* 75 */     Objects.requireNonNull(locale, "locale");
/* 76 */     for (Translator source : this.sources) {
/* 77 */       MessageFormat translation = source.translate(key, locale);
/* 78 */       if (translation != null) return translation; 
/*    */     } 
/* 80 */     return null;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Stream<? extends ExaminableProperty> examinableProperties() {
/* 85 */     return Stream.of(ExaminableProperty.of("sources", this.sources));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\translation\GlobalTranslatorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
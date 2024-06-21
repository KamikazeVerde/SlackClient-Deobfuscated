/*     */ package com.viaversion.viaversion.libs.kyori.adventure.translation;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.Examinable;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ 
/*     */ final class TranslationRegistryImpl
/*     */   implements Examinable, TranslationRegistry
/*     */ {
/*     */   private final Key name;
/*  43 */   private final Map<String, Translation> translations = new ConcurrentHashMap<>();
/*  44 */   private Locale defaultLocale = Locale.US;
/*     */   
/*     */   TranslationRegistryImpl(Key name) {
/*  47 */     this.name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void register(@NotNull String key, @NotNull Locale locale, @NotNull MessageFormat format) {
/*  52 */     ((Translation)this.translations.computeIfAbsent(key, x$0 -> new Translation(x$0))).register(locale, format);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unregister(@NotNull String key) {
/*  57 */     this.translations.remove(key);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Key name() {
/*  62 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(@NotNull String key) {
/*  67 */     return this.translations.containsKey(key);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MessageFormat translate(@NotNull String key, @NotNull Locale locale) {
/*  72 */     Translation translation = this.translations.get(key);
/*  73 */     if (translation == null) return null; 
/*  74 */     return translation.translate(locale);
/*     */   }
/*     */ 
/*     */   
/*     */   public void defaultLocale(@NotNull Locale defaultLocale) {
/*  79 */     this.defaultLocale = Objects.<Locale>requireNonNull(defaultLocale, "defaultLocale");
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<? extends ExaminableProperty> examinableProperties() {
/*  84 */     return Stream.of(ExaminableProperty.of("translations", this.translations));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/*  89 */     if (this == other) return true; 
/*  90 */     if (!(other instanceof TranslationRegistryImpl)) return false;
/*     */     
/*  92 */     TranslationRegistryImpl that = (TranslationRegistryImpl)other;
/*     */     
/*  94 */     return (this.name.equals(that.name) && this.translations
/*  95 */       .equals(that.translations) && this.defaultLocale
/*  96 */       .equals(that.defaultLocale));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 101 */     return Objects.hash(new Object[] { this.name, this.translations, this.defaultLocale });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 106 */     return Internals.toString(this);
/*     */   }
/*     */   
/*     */   final class Translation implements Examinable {
/*     */     private final String key;
/*     */     private final Map<Locale, MessageFormat> formats;
/*     */     
/*     */     Translation(String key) {
/* 114 */       this.key = Objects.<String>requireNonNull(key, "translation key");
/* 115 */       this.formats = new ConcurrentHashMap<>();
/*     */     }
/*     */     
/*     */     void register(@NotNull Locale locale, @NotNull MessageFormat format) {
/* 119 */       if (this.formats.putIfAbsent(Objects.<Locale>requireNonNull(locale, "locale"), Objects.<MessageFormat>requireNonNull(format, "message format")) != null)
/* 120 */         throw new IllegalArgumentException(String.format("Translation already exists: %s for %s", new Object[] { this.key, locale })); 
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     MessageFormat translate(@NotNull Locale locale) {
/* 125 */       MessageFormat format = this.formats.get(Objects.requireNonNull(locale, "locale"));
/* 126 */       if (format == null) {
/* 127 */         format = this.formats.get(new Locale(locale.getLanguage()));
/* 128 */         if (format == null) {
/* 129 */           format = this.formats.get(TranslationRegistryImpl.this.defaultLocale);
/* 130 */           if (format == null) {
/* 131 */             format = this.formats.get(TranslationLocales.global());
/*     */           }
/*     */         } 
/*     */       } 
/* 135 */       return format;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Stream<? extends ExaminableProperty> examinableProperties() {
/* 140 */       return Stream.of(new ExaminableProperty[] {
/* 141 */             ExaminableProperty.of("key", this.key), 
/* 142 */             ExaminableProperty.of("formats", this.formats)
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object other) {
/* 148 */       if (this == other) return true; 
/* 149 */       if (!(other instanceof Translation)) return false; 
/* 150 */       Translation that = (Translation)other;
/* 151 */       return (this.key.equals(that.key) && this.formats
/* 152 */         .equals(that.formats));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 157 */       return Objects.hash(new Object[] { this.key, this.formats });
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 162 */       return Internals.toString(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\translation\TranslationRegistryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
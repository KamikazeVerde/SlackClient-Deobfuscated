/*     */ package com.viaversion.viaversion.libs.kyori.adventure.translation;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.PropertyResourceBundle;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.regex.Pattern;
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
/*     */ public interface TranslationRegistry
/*     */   extends Translator
/*     */ {
/*  60 */   public static final Pattern SINGLE_QUOTE_PATTERN = Pattern.compile("'");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static TranslationRegistry create(Key name) {
/*  70 */     return new TranslationRegistryImpl(Objects.<Key>requireNonNull(name, "name"));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void registerAll(@NotNull Locale locale, @NotNull Map<String, MessageFormat> formats) {
/* 139 */     Objects.requireNonNull(formats); registerAll(locale, formats.keySet(), formats::get);
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
/*     */   default void registerAll(@NotNull Locale locale, @NotNull Path path, boolean escapeSingleQuotes) {
/*     */     
/* 153 */     try { BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8); 
/* 154 */       try { registerAll(locale, new PropertyResourceBundle(reader), escapeSingleQuotes);
/* 155 */         if (reader != null) reader.close();  } catch (Throwable throwable) { if (reader != null) try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException iOException) {}
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void registerAll(@NotNull Locale locale, @NotNull ResourceBundle bundle, boolean escapeSingleQuotes) {
/* 178 */     registerAll(locale, bundle.keySet(), key -> {
/*     */           String format = bundle.getString(key);
/*     */           return new MessageFormat(escapeSingleQuotes ? SINGLE_QUOTE_PATTERN.matcher(format).replaceAll("''") : format, locale);
/*     */         });
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void registerAll(@NotNull Locale locale, @NotNull Set<String> keys, Function<String, MessageFormat> function) {
/* 199 */     IllegalArgumentException firstError = null;
/* 200 */     int errorCount = 0;
/* 201 */     for (String key : keys) {
/*     */       try {
/* 203 */         register(key, locale, function.apply(key));
/* 204 */       } catch (IllegalArgumentException e) {
/* 205 */         if (firstError == null) {
/* 206 */           firstError = e;
/*     */         }
/* 208 */         errorCount++;
/*     */       } 
/*     */     } 
/* 211 */     if (firstError != null) {
/* 212 */       if (errorCount == 1)
/* 213 */         throw firstError; 
/* 214 */       if (errorCount > 1)
/* 215 */         throw new IllegalArgumentException(String.format("Invalid key (and %d more)", new Object[] { Integer.valueOf(errorCount - 1) }), firstError); 
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean contains(@NotNull String paramString);
/*     */   
/*     */   @Nullable
/*     */   MessageFormat translate(@NotNull String paramString, @NotNull Locale paramLocale);
/*     */   
/*     */   void defaultLocale(@NotNull Locale paramLocale);
/*     */   
/*     */   void register(@NotNull String paramString, @NotNull Locale paramLocale, @NotNull MessageFormat paramMessageFormat);
/*     */   
/*     */   void unregister(@NotNull String paramString);
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\translation\TranslationRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
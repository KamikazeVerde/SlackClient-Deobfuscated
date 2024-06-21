/*    */ package com.viaversion.viaversion.libs.kyori.adventure.translation;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*    */ import java.text.MessageFormat;
/*    */ import java.util.Locale;
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
/*    */ public interface Translator
/*    */ {
/*    */   @Nullable
/*    */   static Locale parseLocale(@NotNull String string) {
/* 54 */     String[] segments = string.split("_", 3);
/* 55 */     int length = segments.length;
/* 56 */     if (length == 1)
/* 57 */       return new Locale(string); 
/* 58 */     if (length == 2)
/* 59 */       return new Locale(segments[0], segments[1]); 
/* 60 */     if (length == 3) {
/* 61 */       return new Locale(segments[0], segments[1], segments[2]);
/*    */     }
/* 63 */     return null;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   Key name();
/*    */   
/*    */   @Nullable
/*    */   MessageFormat translate(@NotNull String paramString, @NotNull Locale paramLocale);
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\translation\Translator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
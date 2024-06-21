/*     */ package com.viaversion.viaversion.libs.kyori.adventure.key;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.examination.Examinable;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*     */ import java.util.Comparator;
/*     */ import java.util.stream.Stream;
/*     */ import org.intellij.lang.annotations.Pattern;
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
/*     */ public interface Key
/*     */   extends Comparable<Key>, Examinable, Namespaced, Keyed
/*     */ {
/*     */   public static final String MINECRAFT_NAMESPACE = "minecraft";
/*     */   public static final char DEFAULT_SEPARATOR = ':';
/*     */   
/*     */   @NotNull
/*     */   static Key key(@NotNull @Pattern("([a-z0-9_\\-.]+:)?[a-z0-9_\\-./]+") String string) {
/*  88 */     return key(string, ':');
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
/*     */   @NotNull
/*     */   static Key key(@NotNull String string, char character) {
/* 108 */     int index = string.indexOf(character);
/* 109 */     String namespace = (index >= 1) ? string.substring(0, index) : "minecraft";
/* 110 */     String value = (index >= 0) ? string.substring(index + 1) : string;
/* 111 */     return key(namespace, value);
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
/*     */   static Key key(@NotNull Namespaced namespaced, @NotNull @Pattern("[a-z0-9_\\-./]+") String value) {
/* 124 */     return key(namespaced.namespace(), value);
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
/*     */   static Key key(@NotNull @Pattern("[a-z0-9_\\-.]+") String namespace, @NotNull @Pattern("[a-z0-9_\\-./]+") String value) {
/* 137 */     return new KeyImpl(namespace, value);
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
/*     */   static Comparator<? super Key> comparator() {
/* 149 */     return KeyImpl.COMPARATOR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean parseable(@Nullable String string) {
/* 160 */     if (string == null) {
/* 161 */       return false;
/*     */     }
/* 163 */     int index = string.indexOf(':');
/* 164 */     String namespace = (index >= 1) ? string.substring(0, index) : "minecraft";
/* 165 */     String value = (index >= 0) ? string.substring(index + 1) : string;
/* 166 */     return (parseableNamespace(namespace) && parseableValue(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean parseableNamespace(@NotNull String namespace) {
/* 177 */     for (int i = 0, length = namespace.length(); i < length; i++) {
/* 178 */       if (!allowedInNamespace(namespace.charAt(i))) {
/* 179 */         return false;
/*     */       }
/*     */     } 
/* 182 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean parseableValue(@NotNull String value) {
/* 193 */     for (int i = 0, length = value.length(); i < length; i++) {
/* 194 */       if (!allowedInValue(value.charAt(i))) {
/* 195 */         return false;
/*     */       }
/*     */     } 
/* 198 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean allowedInNamespace(char character) {
/* 209 */     return KeyImpl.allowedInNamespace(character);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean allowedInValue(char character) {
/* 220 */     return KeyImpl.allowedInValue(character);
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
/*     */   @NotNull
/*     */   default Stream<? extends ExaminableProperty> examinableProperties() {
/* 250 */     return Stream.of(new ExaminableProperty[] {
/* 251 */           ExaminableProperty.of("namespace", namespace()), 
/* 252 */           ExaminableProperty.of("value", value())
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   default int compareTo(@NotNull Key that) {
/* 258 */     return comparator().compare(this, that);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   default Key key() {
/* 263 */     return this;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   String namespace();
/*     */   
/*     */   @NotNull
/*     */   String value();
/*     */   
/*     */   @NotNull
/*     */   String asString();
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\key\Key.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viaversion.libs.kyori.adventure.key;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*     */ import java.util.Comparator;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Stream;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class KeyImpl
/*     */   implements Key
/*     */ {
/*  35 */   static final Comparator<? super Key> COMPARATOR = Comparator.comparing(Key::value).thenComparing(Key::namespace);
/*     */   
/*     */   static final String NAMESPACE_PATTERN = "[a-z0-9_\\-.]+";
/*     */   
/*     */   static final String VALUE_PATTERN = "[a-z0-9_\\-./]+";
/*     */   private final String namespace;
/*     */   private final String value;
/*     */   
/*     */   KeyImpl(@NotNull String namespace, @NotNull String value) {
/*  44 */     if (!Key.parseableNamespace(namespace)) throw new InvalidKeyException(namespace, value, String.format("Non [a-z0-9_.-] character in namespace of Key[%s]", new Object[] { asString(namespace, value) })); 
/*  45 */     if (!Key.parseableValue(value)) throw new InvalidKeyException(namespace, value, String.format("Non [a-z0-9/._-] character in value of Key[%s]", new Object[] { asString(namespace, value) })); 
/*  46 */     this.namespace = Objects.<String>requireNonNull(namespace, "namespace");
/*  47 */     this.value = Objects.<String>requireNonNull(value, "value");
/*     */   }
/*     */   
/*     */   static boolean allowedInNamespace(char character) {
/*  51 */     return (character == '_' || character == '-' || (character >= 'a' && character <= 'z') || (character >= '0' && character <= '9') || character == '.');
/*     */   }
/*     */   
/*     */   static boolean allowedInValue(char character) {
/*  55 */     return (character == '_' || character == '-' || (character >= 'a' && character <= 'z') || (character >= '0' && character <= '9') || character == '.' || character == '/');
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String namespace() {
/*  60 */     return this.namespace;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String value() {
/*  65 */     return this.value;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String asString() {
/*  70 */     return asString(this.namespace, this.value);
/*     */   }
/*     */   @NotNull
/*     */   private static String asString(@NotNull String namespace, @NotNull String value) {
/*  74 */     return namespace + ':' + value;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String toString() {
/*  79 */     return asString();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<? extends ExaminableProperty> examinableProperties() {
/*  84 */     return Stream.of(new ExaminableProperty[] {
/*  85 */           ExaminableProperty.of("namespace", this.namespace), 
/*  86 */           ExaminableProperty.of("value", this.value)
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/*  92 */     if (this == other) return true; 
/*  93 */     if (!(other instanceof Key)) return false; 
/*  94 */     Key that = (Key)other;
/*  95 */     return (Objects.equals(this.namespace, that.namespace()) && Objects.equals(this.value, that.value()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 100 */     int result = this.namespace.hashCode();
/* 101 */     result = 31 * result + this.value.hashCode();
/* 102 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(@NotNull Key that) {
/* 107 */     return super.compareTo(that);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\key\KeyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
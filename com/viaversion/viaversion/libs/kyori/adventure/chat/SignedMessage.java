/*     */ package com.viaversion.viaversion.libs.kyori.adventure.chat;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.identity.Identified;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.identity.Identity;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.Examinable;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*     */ import java.time.Instant;
/*     */ import java.util.stream.Stream;
/*     */ import org.jetbrains.annotations.ApiStatus.NonExtendable;
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
/*     */ @NonExtendable
/*     */ public interface SignedMessage
/*     */   extends Identified, Examinable
/*     */ {
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   @NotNull
/*     */   static Signature signature(byte[] signature) {
/*  58 */     return new SignedMessageImpl.SignatureImpl(signature);
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
/*     */   @Contract(value = "_, _ -> new", pure = true)
/*     */   @NotNull
/*     */   static SignedMessage system(@NotNull String message, @Nullable ComponentLike unsignedContent) {
/*  72 */     return new SignedMessageImpl(message, ComponentLike.unbox(unsignedContent));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   Instant timestamp();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract(pure = true)
/*     */   long salt();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract(pure = true)
/*     */   @Nullable
/*     */   Signature signature();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract(pure = true)
/*     */   @Nullable
/*     */   Component unsignedContent();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract(pure = true)
/*     */   @NotNull
/*     */   String message();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract(pure = true)
/*     */   default boolean isSystem() {
/* 134 */     return (identity() == Identity.nil());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract(pure = true)
/*     */   default boolean canDelete() {
/* 146 */     return (signature() != null);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   default Stream<? extends ExaminableProperty> examinableProperties() {
/* 151 */     return Stream.of(new ExaminableProperty[] {
/* 152 */           ExaminableProperty.of("timestamp", timestamp()), 
/* 153 */           ExaminableProperty.of("salt", salt()), 
/* 154 */           ExaminableProperty.of("signature", signature()), 
/* 155 */           ExaminableProperty.of("unsignedContent", unsignedContent()), 
/* 156 */           ExaminableProperty.of("message", message())
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonExtendable
/*     */   public static interface Signature
/*     */     extends Examinable
/*     */   {
/*     */     @Contract(pure = true)
/*     */     byte[] bytes();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     default Stream<? extends ExaminableProperty> examinableProperties() {
/* 181 */       return Stream.of(ExaminableProperty.of("bytes", bytes()));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\chat\SignedMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
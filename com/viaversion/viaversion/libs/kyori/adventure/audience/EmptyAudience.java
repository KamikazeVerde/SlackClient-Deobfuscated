/*     */ package com.viaversion.viaversion.libs.kyori.adventure.audience;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.chat.ChatType;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.chat.SignedMessage;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.identity.Identified;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.identity.Identity;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.inventory.Book;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointer;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
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
/*     */ final class EmptyAudience
/*     */   implements Audience
/*     */ {
/*  44 */   static final EmptyAudience INSTANCE = new EmptyAudience();
/*     */   
/*     */   @NotNull
/*     */   public <T> Optional<T> get(@NotNull Pointer<T> pointer) {
/*  48 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   @Contract("_, null -> null; _, !null -> !null")
/*     */   @Nullable
/*     */   public <T> T getOrDefault(@NotNull Pointer<T> pointer, @Nullable T defaultValue) {
/*  54 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T getOrDefaultFrom(@NotNull Pointer<T> pointer, @NotNull Supplier<? extends T> defaultValue) {
/*  59 */     return defaultValue.get();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Audience filterAudience(@NotNull Predicate<? super Audience> filter) {
/*  64 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEachAudience(@NotNull Consumer<? super Audience> action) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessage(@NotNull ComponentLike message) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessage(@NotNull Component message) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void sendMessage(@NotNull Identified source, @NotNull Component message, @NotNull MessageType type) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessage(@NotNull Component message, ChatType.Bound boundChatType) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessage(@NotNull SignedMessage signedMessage, ChatType.Bound boundChatType) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteMessage(SignedMessage.Signature signature) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendActionBar(@NotNull ComponentLike message) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPlayerListHeader(@NotNull ComponentLike header) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPlayerListFooter(@NotNull ComponentLike footer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPlayerListHeaderAndFooter(@NotNull ComponentLike header, @NotNull ComponentLike footer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void openBook(Book.Builder book) {}
/*     */ 
/*     */   
/*     */   public boolean equals(Object that) {
/* 123 */     return (this == that);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 128 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 133 */     return "EmptyAudience";
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\audience\EmptyAudience.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
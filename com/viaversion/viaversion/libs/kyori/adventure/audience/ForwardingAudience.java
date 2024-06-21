/*     */ package com.viaversion.viaversion.libs.kyori.adventure.audience;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.chat.ChatType;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.chat.SignedMessage;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.identity.Identified;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.identity.Identity;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.inventory.Book;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointer;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointers;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.sound.Sound;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.title.TitlePart;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import org.jetbrains.annotations.ApiStatus.OverrideOnly;
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
/*     */ 
/*     */ @FunctionalInterface
/*     */ public interface ForwardingAudience
/*     */   extends Audience
/*     */ {
/*     */   @OverrideOnly
/*     */   @NotNull
/*     */   Iterable<? extends Audience> audiences();
/*     */   
/*     */   @NotNull
/*     */   default Pointers pointers() {
/*  73 */     return Pointers.empty();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   default Audience filterAudience(@NotNull Predicate<? super Audience> filter) {
/*  78 */     List<Audience> audiences = null;
/*  79 */     for (Audience audience : audiences()) {
/*  80 */       if (filter.test(audience)) {
/*  81 */         Audience filtered = audience.filterAudience(filter);
/*  82 */         if (filtered != Audience.empty()) {
/*  83 */           if (audiences == null) {
/*  84 */             audiences = new ArrayList<>();
/*     */           }
/*  86 */           audiences.add(filtered);
/*     */         } 
/*     */       } 
/*     */     } 
/*  90 */     return (audiences != null) ? 
/*  91 */       Audience.audience(audiences) : 
/*  92 */       Audience.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   default void forEachAudience(@NotNull Consumer<? super Audience> action) {
/*  97 */     for (Audience audience : audiences()) audience.forEachAudience(action);
/*     */   
/*     */   }
/*     */   
/*     */   default void sendMessage(@NotNull Component message) {
/* 102 */     for (Audience audience : audiences()) audience.sendMessage(message);
/*     */   
/*     */   }
/*     */   
/*     */   default void sendMessage(@NotNull Component message, ChatType.Bound boundChatType) {
/* 107 */     for (Audience audience : audiences()) audience.sendMessage(message, boundChatType);
/*     */   
/*     */   }
/*     */   
/*     */   default void sendMessage(@NotNull SignedMessage signedMessage, ChatType.Bound boundChatType) {
/* 112 */     for (Audience audience : audiences()) audience.sendMessage(signedMessage, boundChatType);
/*     */   
/*     */   }
/*     */   
/*     */   default void deleteMessage(SignedMessage.Signature signature) {
/* 117 */     for (Audience audience : audiences()) audience.deleteMessage(signature);
/*     */   
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void sendMessage(@NotNull Identified source, @NotNull Component message, @NotNull MessageType type) {
/* 123 */     for (Audience audience : audiences()) audience.sendMessage(source, message, type);
/*     */   
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {
/* 129 */     for (Audience audience : audiences()) audience.sendMessage(source, message, type);
/*     */   
/*     */   }
/*     */   
/*     */   default void sendActionBar(@NotNull Component message) {
/* 134 */     for (Audience audience : audiences()) audience.sendActionBar(message);
/*     */   
/*     */   }
/*     */   
/*     */   default void sendPlayerListHeader(@NotNull Component header) {
/* 139 */     for (Audience audience : audiences()) audience.sendPlayerListHeader(header);
/*     */   
/*     */   }
/*     */   
/*     */   default void sendPlayerListFooter(@NotNull Component footer) {
/* 144 */     for (Audience audience : audiences()) audience.sendPlayerListFooter(footer);
/*     */   
/*     */   }
/*     */   
/*     */   default void sendPlayerListHeaderAndFooter(@NotNull Component header, @NotNull Component footer) {
/* 149 */     for (Audience audience : audiences()) audience.sendPlayerListHeaderAndFooter(header, footer);
/*     */   
/*     */   }
/*     */   
/*     */   default <T> void sendTitlePart(@NotNull TitlePart<T> part, @NotNull T value) {
/* 154 */     for (Audience audience : audiences()) audience.sendTitlePart(part, value);
/*     */   
/*     */   }
/*     */   
/*     */   default void clearTitle() {
/* 159 */     for (Audience audience : audiences()) audience.clearTitle();
/*     */   
/*     */   }
/*     */   
/*     */   default void resetTitle() {
/* 164 */     for (Audience audience : audiences()) audience.resetTitle();
/*     */   
/*     */   }
/*     */   
/*     */   default void showBossBar(@NotNull BossBar bar) {
/* 169 */     for (Audience audience : audiences()) audience.showBossBar(bar);
/*     */   
/*     */   }
/*     */   
/*     */   default void hideBossBar(@NotNull BossBar bar) {
/* 174 */     for (Audience audience : audiences()) audience.hideBossBar(bar);
/*     */   
/*     */   }
/*     */   
/*     */   default void playSound(@NotNull Sound sound) {
/* 179 */     for (Audience audience : audiences()) audience.playSound(sound);
/*     */   
/*     */   }
/*     */   
/*     */   default void playSound(@NotNull Sound sound, double x, double y, double z) {
/* 184 */     for (Audience audience : audiences()) audience.playSound(sound, x, y, z);
/*     */   
/*     */   }
/*     */   
/*     */   default void playSound(@NotNull Sound sound, Sound.Emitter emitter) {
/* 189 */     for (Audience audience : audiences()) audience.playSound(sound, emitter);
/*     */   
/*     */   }
/*     */   
/*     */   default void stopSound(@NotNull SoundStop stop) {
/* 194 */     for (Audience audience : audiences()) audience.stopSound(stop);
/*     */   
/*     */   }
/*     */   
/*     */   default void openBook(@NotNull Book book) {
/* 199 */     for (Audience audience : audiences()) audience.openBook(book);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Single
/*     */     extends ForwardingAudience
/*     */   {
/*     */     @OverrideOnly
/*     */     @NotNull
/*     */     Audience audience();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     @NotNull
/*     */     default Iterable<? extends Audience> audiences() {
/* 226 */       return Collections.singleton(audience());
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     default <T> Optional<T> get(@NotNull Pointer<T> pointer) {
/* 231 */       return audience().get(pointer);
/*     */     }
/*     */     
/*     */     @Contract("_, null -> null; _, !null -> !null")
/*     */     @Nullable
/*     */     default <T> T getOrDefault(@NotNull Pointer<T> pointer, @Nullable T defaultValue) {
/* 237 */       return (T)audience().getOrDefault(pointer, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     default <T> T getOrDefaultFrom(@NotNull Pointer<T> pointer, @NotNull Supplier<? extends T> defaultValue) {
/* 242 */       return (T)audience().getOrDefaultFrom(pointer, defaultValue);
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     default Audience filterAudience(@NotNull Predicate<? super Audience> filter) {
/* 247 */       Audience audience = audience();
/* 248 */       return filter.test(audience) ? 
/* 249 */         this : 
/* 250 */         Audience.empty();
/*     */     }
/*     */ 
/*     */     
/*     */     default void forEachAudience(@NotNull Consumer<? super Audience> action) {
/* 255 */       audience().forEachAudience(action);
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     default Pointers pointers() {
/* 260 */       return audience().pointers();
/*     */     }
/*     */ 
/*     */     
/*     */     default void sendMessage(@NotNull Component message) {
/* 265 */       audience().sendMessage(message);
/*     */     }
/*     */ 
/*     */     
/*     */     default void sendMessage(@NotNull Component message, ChatType.Bound boundChatType) {
/* 270 */       audience().sendMessage(message, boundChatType);
/*     */     }
/*     */ 
/*     */     
/*     */     default void sendMessage(@NotNull SignedMessage signedMessage, ChatType.Bound boundChatType) {
/* 275 */       audience().sendMessage(signedMessage, boundChatType);
/*     */     }
/*     */ 
/*     */     
/*     */     default void deleteMessage(SignedMessage.Signature signature) {
/* 280 */       audience().deleteMessage(signature);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default void sendMessage(@NotNull Identified source, @NotNull Component message, @NotNull MessageType type) {
/* 286 */       audience().sendMessage(source, message, type);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {
/* 292 */       audience().sendMessage(source, message, type);
/*     */     }
/*     */ 
/*     */     
/*     */     default void sendActionBar(@NotNull Component message) {
/* 297 */       audience().sendActionBar(message);
/*     */     }
/*     */ 
/*     */     
/*     */     default void sendPlayerListHeader(@NotNull Component header) {
/* 302 */       audience().sendPlayerListHeader(header);
/*     */     }
/*     */ 
/*     */     
/*     */     default void sendPlayerListFooter(@NotNull Component footer) {
/* 307 */       audience().sendPlayerListFooter(footer);
/*     */     }
/*     */ 
/*     */     
/*     */     default void sendPlayerListHeaderAndFooter(@NotNull Component header, @NotNull Component footer) {
/* 312 */       audience().sendPlayerListHeaderAndFooter(header, footer);
/*     */     }
/*     */ 
/*     */     
/*     */     default <T> void sendTitlePart(@NotNull TitlePart<T> part, @NotNull T value) {
/* 317 */       audience().sendTitlePart(part, value);
/*     */     }
/*     */ 
/*     */     
/*     */     default void clearTitle() {
/* 322 */       audience().clearTitle();
/*     */     }
/*     */ 
/*     */     
/*     */     default void resetTitle() {
/* 327 */       audience().resetTitle();
/*     */     }
/*     */ 
/*     */     
/*     */     default void showBossBar(@NotNull BossBar bar) {
/* 332 */       audience().showBossBar(bar);
/*     */     }
/*     */ 
/*     */     
/*     */     default void hideBossBar(@NotNull BossBar bar) {
/* 337 */       audience().hideBossBar(bar);
/*     */     }
/*     */ 
/*     */     
/*     */     default void playSound(@NotNull Sound sound) {
/* 342 */       audience().playSound(sound);
/*     */     }
/*     */ 
/*     */     
/*     */     default void playSound(@NotNull Sound sound, double x, double y, double z) {
/* 347 */       audience().playSound(sound, x, y, z);
/*     */     }
/*     */ 
/*     */     
/*     */     default void playSound(@NotNull Sound sound, Sound.Emitter emitter) {
/* 352 */       audience().playSound(sound, emitter);
/*     */     }
/*     */ 
/*     */     
/*     */     default void stopSound(@NotNull SoundStop stop) {
/* 357 */       audience().stopSound(stop);
/*     */     }
/*     */ 
/*     */     
/*     */     default void openBook(@NotNull Book book) {
/* 362 */       audience().openBook(book);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\audience\ForwardingAudience.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
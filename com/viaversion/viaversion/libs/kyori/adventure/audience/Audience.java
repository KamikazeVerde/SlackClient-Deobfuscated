/*     */ package com.viaversion.viaversion.libs.kyori.adventure.audience;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.bossbar.BossBar;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.chat.ChatType;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.chat.SignedMessage;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.identity.Identified;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.identity.Identity;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.inventory.Book;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointered;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.sound.Sound;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.title.Title;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.title.TitlePart;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Collector;
/*     */ import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Audience
/*     */   extends Pointered
/*     */ {
/*     */   @NotNull
/*     */   static Audience empty() {
/*  96 */     return EmptyAudience.INSTANCE;
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
/*     */   static Audience audience(@NotNull Audience... audiences) {
/* 108 */     int length = audiences.length;
/* 109 */     if (length == 0)
/* 110 */       return empty(); 
/* 111 */     if (length == 1) {
/* 112 */       return audiences[0];
/*     */     }
/* 114 */     return audience(Arrays.asList(audiences));
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
/*     */   @NotNull
/*     */   static ForwardingAudience audience(@NotNull Iterable<? extends Audience> audiences) {
/* 129 */     return () -> audiences;
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
/*     */   static Collector<? super Audience, ?, ForwardingAudience> toAudience() {
/* 141 */     return Audiences.COLLECTOR;
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
/*     */   @NotNull
/*     */   default Audience filterAudience(@NotNull Predicate<? super Audience> filter) {
/* 158 */     return filter.test(this) ? 
/* 159 */       this : 
/* 160 */       empty();
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
/*     */   default void forEachAudience(@NotNull Consumer<? super Audience> action) {
/* 177 */     action.accept(this);
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
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendMessage(@NotNull ComponentLike message) {
/* 192 */     sendMessage(message.asComponent());
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
/*     */   default void sendMessage(@NotNull Component message) {
/* 206 */     sendMessage(message, MessageType.SYSTEM);
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
/*     */   @Deprecated
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   @ScheduledForRemoval(inVersion = "5.0.0")
/*     */   default void sendMessage(@NotNull ComponentLike message, @NotNull MessageType type) {
/* 224 */     sendMessage(message.asComponent(), type);
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
/*     */   @Deprecated
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   @ScheduledForRemoval(inVersion = "5.0.0")
/*     */   default void sendMessage(@NotNull Component message, @NotNull MessageType type) {
/* 242 */     sendMessage(Identity.nil(), message, type);
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
/*     */   @Deprecated
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendMessage(@NotNull Identified source, @NotNull ComponentLike message) {
/* 259 */     sendMessage(source, message.asComponent());
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
/*     */   @Deprecated
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendMessage(@NotNull Identity source, @NotNull ComponentLike message) {
/* 274 */     sendMessage(source, message.asComponent());
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
/*     */   @Deprecated
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendMessage(@NotNull Identified source, @NotNull Component message) {
/* 289 */     sendMessage(source, message, MessageType.CHAT);
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
/*     */   @Deprecated
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendMessage(@NotNull Identity source, @NotNull Component message) {
/* 304 */     sendMessage(source, message, MessageType.CHAT);
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
/*     */   @Deprecated
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   @ScheduledForRemoval(inVersion = "5.0.0")
/*     */   default void sendMessage(@NotNull Identified source, @NotNull ComponentLike message, @NotNull MessageType type) {
/* 321 */     sendMessage(source, message.asComponent(), type);
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
/*     */   @Deprecated
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   @ScheduledForRemoval(inVersion = "5.0.0")
/*     */   default void sendMessage(@NotNull Identity source, @NotNull ComponentLike message, @NotNull MessageType type) {
/* 338 */     sendMessage(source, message.asComponent(), type);
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
/*     */   @Deprecated
/*     */   @ScheduledForRemoval(inVersion = "5.0.0")
/*     */   default void sendMessage(@NotNull Identified source, @NotNull Component message, @NotNull MessageType type) {
/* 354 */     sendMessage(source.identity(), message, type);
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
/*     */   @Deprecated
/*     */   @ScheduledForRemoval(inVersion = "5.0.0")
/*     */   default void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void sendMessage(@NotNull Component message, ChatType.Bound boundChatType) {
/* 385 */     sendMessage(message, MessageType.CHAT);
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
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendMessage(@NotNull ComponentLike message, ChatType.Bound boundChatType) {
/* 398 */     sendMessage(message.asComponent(), boundChatType);
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
/*     */   default void sendMessage(@NotNull SignedMessage signedMessage, ChatType.Bound boundChatType) {
/* 413 */     Component content = (signedMessage.unsignedContent() != null) ? signedMessage.unsignedContent() : (Component)Component.text(signedMessage.message());
/* 414 */     if (signedMessage.isSystem()) {
/* 415 */       sendMessage(content);
/*     */     } else {
/* 417 */       sendMessage(signedMessage.identity(), content, MessageType.CHAT);
/*     */     } 
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
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void deleteMessage(@NotNull SignedMessage signedMessage) {
/* 431 */     if (signedMessage.canDelete()) {
/* 432 */       deleteMessage(Objects.<SignedMessage.Signature>requireNonNull(signedMessage.signature()));
/*     */     }
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
/*     */   default void deleteMessage(SignedMessage.Signature signature) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendActionBar(@NotNull ComponentLike message) {
/* 456 */     sendActionBar(message.asComponent());
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
/*     */   default void sendActionBar(@NotNull Component message) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendPlayerListHeader(@NotNull ComponentLike header) {
/* 480 */     sendPlayerListHeader(header.asComponent());
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
/*     */   default void sendPlayerListHeader(@NotNull Component header) {
/* 493 */     sendPlayerListHeaderAndFooter(header, (Component)Component.empty());
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
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendPlayerListFooter(@NotNull ComponentLike footer) {
/* 507 */     sendPlayerListFooter(footer.asComponent());
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
/*     */   default void sendPlayerListFooter(@NotNull Component footer) {
/* 520 */     sendPlayerListHeaderAndFooter((Component)Component.empty(), footer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void sendPlayerListHeaderAndFooter(@NotNull ComponentLike header, @NotNull ComponentLike footer) {
/* 532 */     sendPlayerListHeaderAndFooter(header.asComponent(), footer.asComponent());
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
/*     */   default void sendPlayerListHeaderAndFooter(@NotNull Component header, @NotNull Component footer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void showTitle(@NotNull Title title) {
/* 554 */     Title.Times times = title.times();
/* 555 */     if (times != null) sendTitlePart(TitlePart.TIMES, times);
/*     */     
/* 557 */     sendTitlePart(TitlePart.SUBTITLE, title.subtitle());
/* 558 */     sendTitlePart(TitlePart.TITLE, title.title());
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
/*     */   default <T> void sendTitlePart(@NotNull TitlePart<T> part, @NotNull T value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void clearTitle() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void resetTitle() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void showBossBar(@NotNull BossBar bar) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void hideBossBar(@NotNull BossBar bar) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void playSound(@NotNull Sound sound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void playSound(@NotNull Sound sound, double x, double y, double z) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void playSound(@NotNull Sound sound, Sound.Emitter emitter) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void stopSound(@NotNull Sound sound) {
/* 663 */     stopSound(((Sound)Objects.<Sound>requireNonNull(sound, "sound")).asStop());
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
/*     */   default void stopSound(@NotNull SoundStop stop) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ForwardingAudienceOverrideNotRequired
/*     */   default void openBook(Book.Builder book) {
/* 687 */     openBook(book.build());
/*     */   }
/*     */   
/*     */   default void openBook(@NotNull Book book) {}
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\audience\Audience.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
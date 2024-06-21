/*     */ package com.viaversion.viaversion.libs.kyori.adventure.chat;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.key.Keyed;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.Examinable;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Stream;
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
/*     */ public interface ChatType
/*     */   extends Examinable, Keyed
/*     */ {
/*  52 */   public static final ChatType CHAT = new ChatTypeImpl(Key.key("chat"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public static final ChatType SAY_COMMAND = new ChatTypeImpl(Key.key("say_command"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static final ChatType MSG_COMMAND_INCOMING = new ChatTypeImpl(Key.key("msg_command_incoming"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public static final ChatType MSG_COMMAND_OUTGOING = new ChatTypeImpl(Key.key("msg_command_outgoing"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public static final ChatType TEAM_MSG_COMMAND_INCOMING = new ChatTypeImpl(Key.key("team_msg_command_incoming"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static final ChatType TEAM_MSG_COMMAND_OUTGOING = new ChatTypeImpl(Key.key("team_msg_command_outgoing"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public static final ChatType EMOTE_COMMAND = new ChatTypeImpl(Key.key("emote_command"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static ChatType chatType(@NotNull Keyed key) {
/* 110 */     return (key instanceof ChatType) ? (ChatType)key : new ChatTypeImpl(((Keyed)Objects.<Keyed>requireNonNull(key, "key")).key());
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
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   default Bound bind(@NotNull ComponentLike name) {
/* 123 */     return bind(name, null);
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
/*     */   @Contract(value = "_, _ -> new", pure = true)
/*     */   default Bound bind(@NotNull ComponentLike name, @Nullable ComponentLike target) {
/* 137 */     return new ChatTypeImpl.BoundImpl(this, Objects.<Component>requireNonNull(name.asComponent(), "name"), ComponentLike.unbox(target));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   default Stream<? extends ExaminableProperty> examinableProperties() {
/* 142 */     return Stream.of(ExaminableProperty.of("key", key()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Bound
/*     */     extends Examinable
/*     */   {
/*     */     @Contract(pure = true)
/*     */     @NotNull
/*     */     ChatType type();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract(pure = true)
/*     */     @NotNull
/*     */     Component name();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract(pure = true)
/*     */     @Nullable
/*     */     Component target();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     default Stream<? extends ExaminableProperty> examinableProperties() {
/* 185 */       return Stream.of(new ExaminableProperty[] {
/* 186 */             ExaminableProperty.of("type", type()), 
/* 187 */             ExaminableProperty.of("name", name()), 
/* 188 */             ExaminableProperty.of("target", target())
/*     */           });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\chat\ChatType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
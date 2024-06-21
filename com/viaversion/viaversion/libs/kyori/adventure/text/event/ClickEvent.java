/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text.event;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleBuilderApplicable;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.Examinable;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*     */ import java.net.URL;
/*     */ import java.util.Objects;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ClickEvent
/*     */   implements Examinable, StyleBuilderApplicable
/*     */ {
/*     */   private final Action action;
/*     */   private final String value;
/*     */   
/*     */   @NotNull
/*     */   public static ClickEvent openUrl(@NotNull String url) {
/*  56 */     return new ClickEvent(Action.OPEN_URL, url);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static ClickEvent openUrl(@NotNull URL url) {
/*  67 */     return openUrl(url.toExternalForm());
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
/*     */   public static ClickEvent openFile(@NotNull String file) {
/*  80 */     return new ClickEvent(Action.OPEN_FILE, file);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static ClickEvent runCommand(@NotNull String command) {
/*  91 */     return new ClickEvent(Action.RUN_COMMAND, command);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static ClickEvent suggestCommand(@NotNull String command) {
/* 102 */     return new ClickEvent(Action.SUGGEST_COMMAND, command);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static ClickEvent changePage(@NotNull String page) {
/* 113 */     return new ClickEvent(Action.CHANGE_PAGE, page);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static ClickEvent changePage(int page) {
/* 124 */     return changePage(String.valueOf(page));
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
/*     */   public static ClickEvent copyToClipboard(@NotNull String text) {
/* 136 */     return new ClickEvent(Action.COPY_TO_CLIPBOARD, text);
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
/*     */   public static ClickEvent clickEvent(@NotNull Action action, @NotNull String value) {
/* 148 */     return new ClickEvent(action, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClickEvent(@NotNull Action action, @NotNull String value) {
/* 155 */     this.action = Objects.<Action>requireNonNull(action, "action");
/* 156 */     this.value = Objects.<String>requireNonNull(value, "value");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Action action() {
/* 166 */     return this.action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String value() {
/* 176 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void styleApply(Style.Builder style) {
/* 181 */     style.clickEvent(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/* 186 */     if (this == other) return true; 
/* 187 */     if (other == null || getClass() != other.getClass()) return false; 
/* 188 */     ClickEvent that = (ClickEvent)other;
/* 189 */     return (this.action == that.action && Objects.equals(this.value, that.value));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 194 */     int result = this.action.hashCode();
/* 195 */     result = 31 * result + this.value.hashCode();
/* 196 */     return result;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<? extends ExaminableProperty> examinableProperties() {
/* 201 */     return Stream.of(new ExaminableProperty[] {
/* 202 */           ExaminableProperty.of("action", this.action), 
/* 203 */           ExaminableProperty.of("value", this.value)
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 209 */     return Internals.toString(this);
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
/*     */   public enum Action
/*     */   {
/* 223 */     OPEN_URL("open_url", true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     OPEN_FILE("open_file", false),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     RUN_COMMAND("run_command", true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     SUGGEST_COMMAND("suggest_command", true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     CHANGE_PAGE("change_page", true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 256 */     COPY_TO_CLIPBOARD("copy_to_clipboard", true);
/*     */     
/*     */     public static final Index<String, Action> NAMES;
/*     */     private final String name;
/*     */     private final boolean readable;
/*     */     
/*     */     static {
/* 263 */       NAMES = Index.create(Action.class, constant -> constant.name);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Action(String name, boolean readable) {
/* 273 */       this.name = name;
/* 274 */       this.readable = readable;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean readable() {
/* 285 */       return this.readable;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public String toString() {
/* 290 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\event\ClickEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
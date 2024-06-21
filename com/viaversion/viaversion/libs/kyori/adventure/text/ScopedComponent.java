/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleSetter;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
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
/*     */ public interface ScopedComponent<C extends Component>
/*     */   extends Component
/*     */ {
/*     */   @NotNull
/*     */   default C style(@NotNull Consumer<Style.Builder> style) {
/*  53 */     return (C)super.style(style);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C style(Style.Builder style) {
/*  59 */     return (C)super.style(style);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C mergeStyle(@NotNull Component that) {
/*  65 */     return (C)super.mergeStyle(that);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   C mergeStyle(@NotNull Component that, Style.Merge... merges) {
/*  71 */     return (C)super.mergeStyle(that, merges);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C append(@NotNull Component component) {
/*  77 */     return (C)super.append(component);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C append(@NotNull ComponentLike like) {
/*  83 */     return (C)super.append(like);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C append(@NotNull ComponentBuilder<?, ?> builder) {
/*  89 */     return (C)super.append(builder);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C mergeStyle(@NotNull Component that, @NotNull Set<Style.Merge> merges) {
/*  95 */     return (C)super.mergeStyle(that, merges);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C color(@Nullable TextColor color) {
/* 101 */     return (C)super.color(color);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C colorIfAbsent(@Nullable TextColor color) {
/* 107 */     return (C)super.colorIfAbsent(color);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C decorate(@NotNull TextDecoration decoration) {
/* 113 */     return (C)super.decorate(decoration);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C decoration(@NotNull TextDecoration decoration, boolean flag) {
/* 119 */     return (C)super.decoration(decoration, flag);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C decoration(@NotNull TextDecoration decoration, TextDecoration.State state) {
/* 125 */     return (C)super.decoration(decoration, state);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C clickEvent(@Nullable ClickEvent event) {
/* 131 */     return (C)super.clickEvent(event);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C hoverEvent(@Nullable HoverEventSource<?> event) {
/* 137 */     return (C)super.hoverEvent(event);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default C insertion(@Nullable String insertion) {
/* 143 */     return (C)super.insertion(insertion);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   C children(@NotNull List<? extends ComponentLike> paramList);
/*     */   
/*     */   @NotNull
/*     */   C style(@NotNull Style paramStyle);
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\ScopedComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
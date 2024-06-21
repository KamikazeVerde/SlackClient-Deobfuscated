/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text.format;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.builder.AbstractBuilder;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.MonkeyBars;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.Examinable;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import org.jetbrains.annotations.ApiStatus.NonExtendable;
/*     */ import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
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
/*     */ 
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
/*     */ public interface Style
/*     */   extends Buildable<Style, Style.Builder>, Examinable, StyleGetter, StyleSetter<Style>
/*     */ {
/*  71 */   public static final Key DEFAULT_FONT = Key.key("default");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Style empty() {
/*  80 */     return StyleImpl.EMPTY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Builder style() {
/*  90 */     return new StyleImpl.BuilderImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Style style(@NotNull Consumer<Builder> consumer) {
/* 101 */     return (Style)AbstractBuilder.configureAndBuild(style(), consumer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Style style(@Nullable TextColor color) {
/* 112 */     return empty().color(color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Style style(@NotNull TextDecoration decoration) {
/* 123 */     return style().decoration(decoration, true).build();
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
/*     */   static Style style(@Nullable TextColor color, TextDecoration... decorations) {
/* 135 */     Builder builder = style();
/* 136 */     builder.color(color);
/* 137 */     builder.decorate(decorations);
/* 138 */     return builder.build();
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
/*     */   static Style style(@Nullable TextColor color, Set<TextDecoration> decorations) {
/* 150 */     Builder builder = style();
/* 151 */     builder.color(color);
/* 152 */     if (!decorations.isEmpty()) {
/* 153 */       for (TextDecoration decoration : decorations) {
/* 154 */         builder.decoration(decoration, true);
/*     */       }
/*     */     }
/* 157 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Style style(StyleBuilderApplicable... applicables) {
/* 168 */     int length = applicables.length;
/* 169 */     if (length == 0) return empty(); 
/* 170 */     Builder builder = style();
/* 171 */     for (int i = 0; i < length; i++) {
/* 172 */       StyleBuilderApplicable applicable = applicables[i];
/* 173 */       if (applicable != null) {
/* 174 */         applicable.styleApply(builder);
/*     */       }
/*     */     } 
/* 177 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Style style(@NotNull Iterable<? extends StyleBuilderApplicable> applicables) {
/* 188 */     Builder builder = style();
/* 189 */     for (StyleBuilderApplicable applicable : applicables) {
/* 190 */       applicable.styleApply(builder);
/*     */     }
/* 192 */     return builder.build();
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
/*     */   default Style edit(@NotNull Consumer<Builder> consumer) {
/* 205 */     return edit(consumer, Merge.Strategy.ALWAYS);
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
/*     */   default Style edit(@NotNull Consumer<Builder> consumer, Merge.Strategy strategy) {
/* 217 */     return style(style -> {
/*     */           if (strategy == Merge.Strategy.ALWAYS) {
/*     */             style.merge(this, strategy);
/*     */           }
/*     */           consumer.accept(style);
/*     */           if (strategy == Merge.Strategy.IF_ABSENT_ON_TARGET) {
/*     */             style.merge(this, strategy);
/*     */           }
/*     */         });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean hasDecoration(@NotNull TextDecoration decoration) {
/* 289 */     return super.hasDecoration(decoration);
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
/*     */   @NotNull
/*     */   default Style decorate(@NotNull TextDecoration decoration) {
/* 313 */     return super.decorate(decoration);
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
/*     */   @NotNull
/*     */   default Style decoration(@NotNull TextDecoration decoration, boolean flag) {
/* 327 */     return super.decoration(decoration, flag);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default Map<TextDecoration, TextDecoration.State> decorations() {
/* 364 */     return super.decorations();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   default Style merge(@NotNull Style that) {
/* 444 */     return merge(that, Merge.all());
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
/*     */   default Style merge(@NotNull Style that, Merge.Strategy strategy) {
/* 456 */     return merge(that, strategy, Merge.all());
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
/*     */   default Style merge(@NotNull Style that, @NotNull Merge merge) {
/* 468 */     return merge(that, Collections.singleton(merge));
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
/*     */   default Style merge(@NotNull Style that, Merge.Strategy strategy, @NotNull Merge merge) {
/* 481 */     return merge(that, strategy, Collections.singleton(merge));
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
/*     */   Style merge(@NotNull Style that, @NotNull Merge... merges) {
/* 493 */     return merge(that, Merge.merges(merges));
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
/*     */   Style merge(@NotNull Style that, Merge.Strategy strategy, @NotNull Merge... merges) {
/* 506 */     return merge(that, strategy, Merge.merges(merges));
/*     */   } @Nullable
/*     */   Key font(); @NotNull
/*     */   Style font(@Nullable Key paramKey); @Nullable
/*     */   TextColor color(); @NotNull
/*     */   Style color(@Nullable TextColor paramTextColor); @NotNull
/*     */   Style colorIfAbsent(@Nullable TextColor paramTextColor); TextDecoration.State decoration(@NotNull TextDecoration paramTextDecoration); @NotNull
/*     */   Style decoration(@NotNull TextDecoration paramTextDecoration, TextDecoration.State paramState); @NotNull
/*     */   Style decorationIfAbsent(@NotNull TextDecoration paramTextDecoration, TextDecoration.State paramState); @NotNull
/*     */   Style decorations(@NotNull Map<TextDecoration, TextDecoration.State> paramMap); @Nullable
/*     */   ClickEvent clickEvent(); @NotNull
/*     */   default Style merge(@NotNull Style that, @NotNull Set<Merge> merges) {
/* 518 */     return merge(that, Merge.Strategy.ALWAYS, merges);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   Style clickEvent(@Nullable ClickEvent paramClickEvent);
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   HoverEvent<?> hoverEvent();
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   Style hoverEvent(@Nullable HoverEventSource<?> paramHoverEventSource);
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   String insertion();
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   Style insertion(@Nullable String paramString);
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   Style merge(@NotNull Style paramStyle, Merge.Strategy paramStrategy, @NotNull Set<Merge> paramSet);
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   Style unmerge(@NotNull Style paramStyle);
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isEmpty();
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   Builder toBuilder();
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Merge
/*     */   {
/* 569 */     COLOR,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 575 */     DECORATIONS,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 581 */     EVENTS,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 587 */     INSERTION,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 593 */     FONT;
/*     */     
/* 595 */     static final Set<Merge> ALL = merges(values());
/* 596 */     static final Set<Merge> COLOR_AND_DECORATIONS = merges(new Merge[] { COLOR, DECORATIONS });
/*     */ 
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public static Set<Merge> all() {
/* 605 */       return ALL;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public static Set<Merge> colorAndDecorations() {
/* 615 */       return COLOR_AND_DECORATIONS;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public static Set<Merge> merges(Merge... merges) {
/* 626 */       return MonkeyBars.enumSet(Merge.class, (Enum[])merges);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     @ScheduledForRemoval(inVersion = "5.0.0")
/*     */     @NotNull
/*     */     public static Set<Merge> of(Merge... merges) {
/* 640 */       return MonkeyBars.enumSet(Merge.class, (Enum[])merges);
/*     */     }
/*     */     
/*     */     static boolean hasAll(@NotNull Set<Merge> merges) {
/* 644 */       return (merges.size() == ALL.size());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public enum Strategy
/*     */     {
/* 658 */       ALWAYS,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 664 */       NEVER,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 670 */       IF_ABSENT_ON_TARGET; } } public enum Strategy { ALWAYS, NEVER, IF_ABSENT_ON_TARGET; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Builder
/*     */     extends AbstractBuilder<Style>, Buildable.Builder<Style>, MutableStyleSetter<Builder>
/*     */   {
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     default Builder decorate(@NotNull TextDecoration decoration) {
/* 724 */       return super.decorate(decoration);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder decorate(@NotNull TextDecoration... decorations) {
/* 737 */       return super.decorate(decorations);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_, _ -> this")
/*     */     @NotNull
/*     */     default Builder decoration(@NotNull TextDecoration decoration, boolean flag) {
/* 752 */       return super.decoration(decoration, flag);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     default Builder decorations(@NotNull Map<TextDecoration, TextDecoration.State> decorations) {
/* 767 */       return super.decorations(decorations);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     default Builder merge(@NotNull Style that) {
/* 839 */       return merge(that, Style.Merge.all());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_, _ -> this")
/*     */     @NotNull
/*     */     default Builder merge(@NotNull Style that, Style.Merge.Strategy strategy) {
/* 852 */       return merge(that, strategy, Style.Merge.all());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_, _ -> this")
/*     */     @NotNull
/*     */     Builder merge(@NotNull Style that, @NotNull Style.Merge... merges) {
/* 865 */       if (merges.length == 0) return this; 
/* 866 */       return merge(that, Style.Merge.merges(merges));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_, _, _ -> this")
/*     */     @NotNull
/*     */     Builder merge(@NotNull Style that, Style.Merge.Strategy strategy, @NotNull Style.Merge... merges) {
/* 880 */       if (merges.length == 0) return this; 
/* 881 */       return merge(that, strategy, Style.Merge.merges(merges));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_, _ -> this")
/*     */     @NotNull
/*     */     default Builder merge(@NotNull Style that, @NotNull Set<Style.Merge> merges) {
/* 894 */       return merge(that, Style.Merge.Strategy.ALWAYS, merges);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     default Builder apply(@NotNull StyleBuilderApplicable applicable) {
/* 918 */       applicable.styleApply(this);
/* 919 */       return this;
/*     */     }
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder font(@Nullable Key param1Key);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder color(@Nullable TextColor param1TextColor);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder colorIfAbsent(@Nullable TextColor param1TextColor);
/*     */     
/*     */     @Contract("_, _ -> this")
/*     */     @NotNull
/*     */     Builder decoration(@NotNull TextDecoration param1TextDecoration, TextDecoration.State param1State);
/*     */     
/*     */     @Contract("_, _ -> this")
/*     */     @NotNull
/*     */     Builder decorationIfAbsent(@NotNull TextDecoration param1TextDecoration, TextDecoration.State param1State);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder clickEvent(@Nullable ClickEvent param1ClickEvent);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder hoverEvent(@Nullable HoverEventSource<?> param1HoverEventSource);
/*     */     
/*     */     @Contract("_ -> this")
/*     */     @NotNull
/*     */     Builder insertion(@Nullable String param1String);
/*     */     
/*     */     @Contract("_, _, _ -> this")
/*     */     @NotNull
/*     */     Builder merge(@NotNull Style param1Style, Style.Merge.Strategy param1Strategy, @NotNull Set<Style.Merge> param1Set);
/*     */     
/*     */     @NotNull
/*     */     Style build();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\format\Style.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
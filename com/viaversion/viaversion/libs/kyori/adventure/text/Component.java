/*      */ package com.viaversion.viaversion.libs.kyori.adventure.text;
/*      */ 
/*      */ import com.viaversion.viaversion.libs.kyori.adventure.builder.AbstractBuilder;
/*      */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*      */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
/*      */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
/*      */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
/*      */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
/*      */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleBuilderApplicable;
/*      */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleGetter;
/*      */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleSetter;
/*      */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
/*      */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
/*      */ import com.viaversion.viaversion.libs.kyori.adventure.translation.Translatable;
/*      */ import com.viaversion.viaversion.libs.kyori.adventure.util.ForwardingIterator;
/*      */ import com.viaversion.viaversion.libs.kyori.adventure.util.IntFunction2;
/*      */ import com.viaversion.viaversion.libs.kyori.adventure.util.MonkeyBars;
/*      */ import com.viaversion.viaversion.libs.kyori.examination.Examinable;
/*      */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.Spliterator;
/*      */ import java.util.Spliterators;
/*      */ import java.util.function.BiPredicate;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.function.UnaryOperator;
/*      */ import java.util.regex.Pattern;
/*      */ import java.util.stream.Collector;
/*      */ import java.util.stream.Stream;
/*      */ import org.jetbrains.annotations.ApiStatus.NonExtendable;
/*      */ import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
/*      */ import org.jetbrains.annotations.Contract;
/*      */ import org.jetbrains.annotations.NotNull;
/*      */ import org.jetbrains.annotations.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @NonExtendable
/*      */ public interface Component
/*      */   extends ComponentBuilderApplicable, ComponentLike, Examinable, HoverEventSource<Component>, StyleGetter, StyleSetter<Component>
/*      */ {
/*  116 */   public static final BiPredicate<? super Component, ? super Component> EQUALS = Objects::equals;
/*      */   
/*      */   public static final BiPredicate<? super Component, ? super Component> EQUALS_IDENTITY;
/*      */   public static final Predicate<? super Component> IS_NOT_EMPTY;
/*      */   
/*      */   static {
/*  122 */     EQUALS_IDENTITY = ((a, b) -> (a == b));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  128 */     IS_NOT_EMPTY = (component -> (component != empty()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   static TextComponent empty() {
/*  137 */     return TextComponentImpl.EMPTY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   static TextComponent newline() {
/*  147 */     return TextComponentImpl.NEWLINE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   static TextComponent space() {
/*  157 */     return TextComponentImpl.SPACE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @ScheduledForRemoval(inVersion = "5.0.0")
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent join(@NotNull ComponentLike separator, @NotNull ComponentLike... components) {
/*  173 */     return join(separator, Arrays.asList(components));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @ScheduledForRemoval(inVersion = "5.0.0")
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent join(@NotNull ComponentLike separator, Iterable<? extends ComponentLike> components) {
/*  189 */     Component component = join(JoinConfiguration.separator(separator), components);
/*      */     
/*  191 */     if (component instanceof TextComponent) return (TextComponent)component; 
/*  192 */     return text().append(component).build();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   static Component join(@NotNull JoinConfiguration config, @NotNull ComponentLike... components) {
/*  208 */     return join(config, Arrays.asList(components));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   static Component join(@NotNull JoinConfiguration config, @NotNull Iterable<? extends ComponentLike> components) {
/*  224 */     return JoinConfigurationImpl.join(config, components);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   static Collector<Component, ? extends ComponentBuilder<?, ?>, Component> toComponent() {
/*  234 */     return toComponent(empty());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   static Collector<Component, ? extends ComponentBuilder<?, ?>, Component> toComponent(@NotNull Component separator) {
/*  245 */     return Collector.of(Component::text, (builder, add) -> { if (separator != empty() && !builder.children().isEmpty()) builder.append(separator);  builder.append(add); }(a, b) -> { List<Component> aChildren = a.children(); TextComponent.Builder ret = text().append((Iterable)aChildren); if (!aChildren.isEmpty()) ret.append(separator);  ret.append((Iterable)b.children()); return ret; }ComponentBuilder::build, new Collector.Characteristics[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   static BlockNBTComponent.Builder blockNBT() {
/*  279 */     return new BlockNBTComponentImpl.BuilderImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract("_ -> new")
/*      */   @NotNull
/*      */   static BlockNBTComponent blockNBT(@NotNull Consumer<? super BlockNBTComponent.Builder> consumer) {
/*  291 */     return (BlockNBTComponent)AbstractBuilder.configureAndBuild(blockNBT(), consumer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static BlockNBTComponent blockNBT(@NotNull String nbtPath, BlockNBTComponent.Pos pos) {
/*  304 */     return blockNBT(nbtPath, false, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static BlockNBTComponent blockNBT(@NotNull String nbtPath, boolean interpret, BlockNBTComponent.Pos pos) {
/*  318 */     return blockNBT(nbtPath, interpret, null, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static BlockNBTComponent blockNBT(@NotNull String nbtPath, boolean interpret, @Nullable ComponentLike separator, BlockNBTComponent.Pos pos) {
/*  333 */     return BlockNBTComponentImpl.create(Collections.emptyList(), Style.empty(), nbtPath, interpret, separator, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   static EntityNBTComponent.Builder entityNBT() {
/*  350 */     return new EntityNBTComponentImpl.BuilderImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract("_ -> new")
/*      */   @NotNull
/*      */   static EntityNBTComponent entityNBT(@NotNull Consumer<? super EntityNBTComponent.Builder> consumer) {
/*  362 */     return (EntityNBTComponent)AbstractBuilder.configureAndBuild(entityNBT(), consumer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract("_, _ -> new")
/*      */   @NotNull
/*      */   static EntityNBTComponent entityNBT(@NotNull String nbtPath, @NotNull String selector) {
/*  375 */     return entityNBT().nbtPath(nbtPath).selector(selector).build();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   static KeybindComponent.Builder keybind() {
/*  392 */     return new KeybindComponentImpl.BuilderImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract("_ -> new")
/*      */   @NotNull
/*      */   static KeybindComponent keybind(@NotNull Consumer<? super KeybindComponent.Builder> consumer) {
/*  404 */     return (KeybindComponent)AbstractBuilder.configureAndBuild(keybind(), consumer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(@NotNull String keybind) {
/*  416 */     return keybind(keybind, Style.empty());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(KeybindComponent.KeybindLike keybind) {
/*  428 */     return keybind(((KeybindComponent.KeybindLike)Objects.<KeybindComponent.KeybindLike>requireNonNull(keybind, "keybind")).asKeybind(), Style.empty());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(@NotNull String keybind, @NotNull Style style) {
/*  441 */     return KeybindComponentImpl.create(Collections.emptyList(), Objects.<Style>requireNonNull(style, "style"), keybind);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(KeybindComponent.KeybindLike keybind, @NotNull Style style) {
/*  454 */     return KeybindComponentImpl.create(Collections.emptyList(), Objects.<Style>requireNonNull(style, "style"), ((KeybindComponent.KeybindLike)Objects.<KeybindComponent.KeybindLike>requireNonNull(keybind, "keybind")).asKeybind());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(@NotNull String keybind, @Nullable TextColor color) {
/*  467 */     return keybind(keybind, Style.style(color));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(KeybindComponent.KeybindLike keybind, @Nullable TextColor color) {
/*  480 */     return keybind(((KeybindComponent.KeybindLike)Objects.<KeybindComponent.KeybindLike>requireNonNull(keybind, "keybind")).asKeybind(), Style.style(color));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(@NotNull String keybind, @Nullable TextColor color, TextDecoration... decorations) {
/*  494 */     return keybind(keybind, Style.style(color, decorations));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(KeybindComponent.KeybindLike keybind, @Nullable TextColor color, TextDecoration... decorations) {
/*  508 */     return keybind(((KeybindComponent.KeybindLike)Objects.<KeybindComponent.KeybindLike>requireNonNull(keybind, "keybind")).asKeybind(), Style.style(color, decorations));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(@NotNull String keybind, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/*  522 */     return keybind(keybind, Style.style(color, decorations));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static KeybindComponent keybind(KeybindComponent.KeybindLike keybind, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/*  536 */     return keybind(((KeybindComponent.KeybindLike)Objects.<KeybindComponent.KeybindLike>requireNonNull(keybind, "keybind")).asKeybind(), Style.style(color, decorations));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   static ScoreComponent.Builder score() {
/*  553 */     return new ScoreComponentImpl.BuilderImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract("_ -> new")
/*      */   @NotNull
/*      */   static ScoreComponent score(@NotNull Consumer<? super ScoreComponent.Builder> consumer) {
/*  565 */     return (ScoreComponent)AbstractBuilder.configureAndBuild(score(), consumer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static ScoreComponent score(@NotNull String name, @NotNull String objective) {
/*  578 */     return score(name, objective, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static ScoreComponent score(@NotNull String name, @NotNull String objective, @Nullable String value) {
/*  594 */     return ScoreComponentImpl.create(Collections.emptyList(), Style.empty(), name, objective, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   static SelectorComponent.Builder selector() {
/*  611 */     return new SelectorComponentImpl.BuilderImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract("_ -> new")
/*      */   @NotNull
/*      */   static SelectorComponent selector(@NotNull Consumer<? super SelectorComponent.Builder> consumer) {
/*  623 */     return (SelectorComponent)AbstractBuilder.configureAndBuild(selector(), consumer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static SelectorComponent selector(@NotNull String pattern) {
/*  635 */     return selector(pattern, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static SelectorComponent selector(@NotNull String pattern, @Nullable ComponentLike separator) {
/*  648 */     return SelectorComponentImpl.create(Collections.emptyList(), Style.empty(), pattern, separator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   static StorageNBTComponent.Builder storageNBT() {
/*  665 */     return new StorageNBTComponentImpl.BuilderImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract("_ -> new")
/*      */   @NotNull
/*      */   static StorageNBTComponent storageNBT(@NotNull Consumer<? super StorageNBTComponent.Builder> consumer) {
/*  677 */     return (StorageNBTComponent)AbstractBuilder.configureAndBuild(storageNBT(), consumer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static StorageNBTComponent storageNBT(@NotNull String nbtPath, @NotNull Key storage) {
/*  690 */     return storageNBT(nbtPath, false, storage);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static StorageNBTComponent storageNBT(@NotNull String nbtPath, boolean interpret, @NotNull Key storage) {
/*  704 */     return storageNBT(nbtPath, interpret, null, storage);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static StorageNBTComponent storageNBT(@NotNull String nbtPath, boolean interpret, @Nullable ComponentLike separator, @NotNull Key storage) {
/*  719 */     return StorageNBTComponentImpl.create(Collections.emptyList(), Style.empty(), nbtPath, interpret, separator, storage);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   static TextComponent.Builder text() {
/*  736 */     return new TextComponentImpl.BuilderImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   static TextComponent textOfChildren(@NotNull ComponentLike... components) {
/*  747 */     if (components.length == 0) return empty(); 
/*  748 */     return TextComponentImpl.create(Arrays.asList(components), Style.empty(), "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract("_ -> new")
/*      */   @NotNull
/*      */   static TextComponent text(@NotNull Consumer<? super TextComponent.Builder> consumer) {
/*  760 */     return (TextComponent)AbstractBuilder.configureAndBuild(text(), consumer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(@NotNull String content) {
/*  772 */     if (content.isEmpty()) return empty(); 
/*  773 */     return text(content, Style.empty());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(@NotNull String content, @NotNull Style style) {
/*  786 */     return TextComponentImpl.create(Collections.emptyList(), Objects.<Style>requireNonNull(style, "style"), content);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(@NotNull String content, @Nullable TextColor color) {
/*  799 */     return text(content, Style.style(color));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(@NotNull String content, @Nullable TextColor color, TextDecoration... decorations) {
/*  813 */     return text(content, Style.style(color, decorations));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(@NotNull String content, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/*  827 */     return text(content, Style.style(color, decorations));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(boolean value) {
/*  839 */     return text(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(boolean value, @NotNull Style style) {
/*  852 */     return text(String.valueOf(value), style);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(boolean value, @Nullable TextColor color) {
/*  865 */     return text(String.valueOf(value), color);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(boolean value, @Nullable TextColor color, TextDecoration... decorations) {
/*  879 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(boolean value, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/*  893 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(char value) {
/*  905 */     if (value == '\n') return newline(); 
/*  906 */     if (value == ' ') return space(); 
/*  907 */     return text(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(char value, @NotNull Style style) {
/*  920 */     return text(String.valueOf(value), style);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(char value, @Nullable TextColor color) {
/*  933 */     return text(String.valueOf(value), color);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(char value, @Nullable TextColor color, TextDecoration... decorations) {
/*  947 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(char value, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/*  961 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(double value) {
/*  973 */     return text(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(double value, @NotNull Style style) {
/*  986 */     return text(String.valueOf(value), style);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(double value, @Nullable TextColor color) {
/*  999 */     return text(String.valueOf(value), color);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(double value, @Nullable TextColor color, TextDecoration... decorations) {
/* 1013 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(double value, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/* 1027 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(float value) {
/* 1039 */     return text(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(float value, @NotNull Style style) {
/* 1052 */     return text(String.valueOf(value), style);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(float value, @Nullable TextColor color) {
/* 1065 */     return text(String.valueOf(value), color);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(float value, @Nullable TextColor color, TextDecoration... decorations) {
/* 1079 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(float value, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/* 1093 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(int value) {
/* 1105 */     return text(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(int value, @NotNull Style style) {
/* 1118 */     return text(String.valueOf(value), style);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(int value, @Nullable TextColor color) {
/* 1131 */     return text(String.valueOf(value), color);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(int value, @Nullable TextColor color, TextDecoration... decorations) {
/* 1145 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(int value, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/* 1159 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(long value) {
/* 1171 */     return text(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(long value, @NotNull Style style) {
/* 1184 */     return text(String.valueOf(value), style);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(long value, @Nullable TextColor color) {
/* 1197 */     return text(String.valueOf(value), color);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(long value, @Nullable TextColor color, TextDecoration... decorations) {
/* 1211 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TextComponent text(long value, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/* 1225 */     return text(String.valueOf(value), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   static TranslatableComponent.Builder translatable() {
/* 1242 */     return new TranslatableComponentImpl.BuilderImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract("_ -> new")
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Consumer<? super TranslatableComponent.Builder> consumer) {
/* 1254 */     return (TranslatableComponent)AbstractBuilder.configureAndBuild(translatable(), consumer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key) {
/* 1266 */     return translatable(key, Style.empty());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable) {
/* 1278 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), Style.empty());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @NotNull Style style) {
/* 1291 */     return TranslatableComponentImpl.create(Collections.emptyList(), Objects.<Style>requireNonNull(style, "style"), key, Collections.emptyList());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @NotNull Style style) {
/* 1304 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), style);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @Nullable TextColor color) {
/* 1317 */     return translatable(key, Style.style(color));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor color) {
/* 1330 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), color);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @Nullable TextColor color, TextDecoration... decorations) {
/* 1344 */     return translatable(key, Style.style(color, decorations));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor color, TextDecoration... decorations) {
/* 1358 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/* 1372 */     return translatable(key, Style.style(color, decorations));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations) {
/* 1386 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), color, decorations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @NotNull ComponentLike... args) {
/* 1399 */     return translatable(key, Style.empty(), args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @NotNull ComponentLike... args) {
/* 1412 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @NotNull Style style, @NotNull ComponentLike... args) {
/* 1426 */     return TranslatableComponentImpl.create(Collections.emptyList(), Objects.<Style>requireNonNull(style, "style"), key, Objects.<ComponentLike[]>requireNonNull(args, "args"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @NotNull Style style, @NotNull ComponentLike... args) {
/* 1440 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), style, args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @Nullable TextColor color, @NotNull ComponentLike... args) {
/* 1454 */     return translatable(key, Style.style(color), args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor color, @NotNull ComponentLike... args) {
/* 1468 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), color, args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations, @NotNull ComponentLike... args) {
/* 1483 */     return translatable(key, Style.style(color, decorations), args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations, @NotNull ComponentLike... args) {
/* 1498 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), color, decorations, args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @NotNull List<? extends ComponentLike> args) {
/* 1511 */     return TranslatableComponentImpl.create(Collections.emptyList(), Style.empty(), key, Objects.<List<? extends ComponentLike>>requireNonNull(args, "args"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @NotNull List<? extends ComponentLike> args) {
/* 1524 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @NotNull Style style, @NotNull List<? extends ComponentLike> args) {
/* 1538 */     return TranslatableComponentImpl.create(Collections.emptyList(), Objects.<Style>requireNonNull(style, "style"), key, Objects.<List<? extends ComponentLike>>requireNonNull(args, "args"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @NotNull Style style, @NotNull List<? extends ComponentLike> args) {
/* 1552 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), style, args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   static TranslatableComponent translatable(@NotNull String key, @Nullable TextColor color, @NotNull List<? extends ComponentLike> args) {
/* 1566 */     return translatable(key, Style.style(color), args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _ -> new", pure = true)
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor color, @NotNull List<? extends ComponentLike> args) {
/* 1580 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), color, args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull String key, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations, @NotNull List<? extends ComponentLike> args) {
/* 1595 */     return translatable(key, Style.style(color, decorations), args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(value = "_, _, _, _ -> new", pure = true)
/*      */   @NotNull
/*      */   static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor color, @NotNull Set<TextDecoration> decorations, @NotNull List<? extends ComponentLike> args) {
/* 1610 */     return translatable(((Translatable)Objects.<Translatable>requireNonNull(translatable, "translatable")).translationKey(), color, decorations, args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   default boolean contains(@NotNull Component that) {
/* 1645 */     return contains(that, EQUALS_IDENTITY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   default boolean contains(@NotNull Component that, @NotNull BiPredicate<? super Component, ? super Component> equals) {
/* 1658 */     if (equals.test(this, that)) return true; 
/* 1659 */     for (Component child : children()) {
/* 1660 */       if (child.contains(that, equals)) return true; 
/*      */     } 
/* 1662 */     HoverEvent<?> hoverEvent = hoverEvent();
/* 1663 */     if (hoverEvent != null) {
/* 1664 */       Object value = hoverEvent.value();
/* 1665 */       Component component = null;
/* 1666 */       if (value instanceof Component) {
/* 1667 */         component = (Component)hoverEvent.value();
/* 1668 */       } else if (value instanceof HoverEvent.ShowEntity) {
/* 1669 */         component = ((HoverEvent.ShowEntity)value).name();
/*      */       } 
/* 1671 */       if (component != null) {
/* 1672 */         if (equals.test(that, component)) return true; 
/* 1673 */         for (Component child : component.children()) {
/* 1674 */           if (child.contains(that, equals)) return true; 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1678 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @ScheduledForRemoval(inVersion = "5.0.0")
/*      */   default void detectCycle(@NotNull Component that) {
/* 1691 */     if (that.contains(this)) {
/* 1692 */       throw new IllegalStateException("Component cycle detected between " + this + " and " + that);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component append(@NotNull Component component) {
/* 1705 */     return append(component);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   default Component append(@NotNull ComponentLike like) {
/* 1716 */     Objects.requireNonNull(like, "like");
/* 1717 */     Component component = like.asComponent();
/* 1718 */     Objects.requireNonNull(component, "component");
/* 1719 */     if (component == empty()) return this; 
/* 1720 */     List<Component> oldChildren = children();
/* 1721 */     return children(MonkeyBars.addOne(oldChildren, component));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component append(@NotNull ComponentBuilder<?, ?> builder) {
/* 1733 */     return append((Component)builder.build());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component appendNewline() {
/* 1744 */     return append(newline());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component appendSpace() {
/* 1755 */     return append(space());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component applyFallbackStyle(@NotNull Style style) {
/* 1769 */     Objects.requireNonNull(style, "style");
/* 1770 */     return style(style().merge(style, Style.Merge.Strategy.IF_ABSENT_ON_TARGET));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   Component applyFallbackStyle(@NotNull StyleBuilderApplicable... style) {
/* 1784 */     return applyFallbackStyle(Style.style(style));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component style(@NotNull Consumer<Style.Builder> consumer) {
/* 1814 */     return style(style().edit(consumer));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component style(@NotNull Consumer<Style.Builder> consumer, Style.Merge.Strategy strategy) {
/* 1827 */     return style(style().edit(consumer, strategy));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component style(Style.Builder style) {
/* 1839 */     return style(style.build());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component mergeStyle(@NotNull Component that) {
/* 1851 */     return mergeStyle(that, Style.Merge.all());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   Component mergeStyle(@NotNull Component that, Style.Merge... merges) {
/* 1864 */     return mergeStyle(that, Style.Merge.merges(merges));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component mergeStyle(@NotNull Component that, @NotNull Set<Style.Merge> merges) {
/* 1877 */     return style(style().merge(that.style(), merges));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   default Key font() {
/* 1888 */     return style().font();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   default Component font(@Nullable Key key) {
/* 1900 */     return style(style().font(key));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   default TextColor color() {
/* 1911 */     return style().color();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component color(@Nullable TextColor color) {
/* 1924 */     return style(style().color(color));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component colorIfAbsent(@Nullable TextColor color) {
/* 1937 */     if (color() == null) return color(color); 
/* 1938 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   default boolean hasDecoration(@NotNull TextDecoration decoration) {
/* 1951 */     return super.hasDecoration(decoration);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component decorate(@NotNull TextDecoration decoration) {
/* 1964 */     return (Component)super.decorate(decoration);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   default TextDecoration.State decoration(@NotNull TextDecoration decoration) {
/* 1978 */     return style().decoration(decoration);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component decoration(@NotNull TextDecoration decoration, boolean flag) {
/* 1993 */     return (Component)super.decoration(decoration, flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component decoration(@NotNull TextDecoration decoration, TextDecoration.State state) {
/* 2010 */     return style(style().decoration(decoration, state));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   default Component decorationIfAbsent(@NotNull TextDecoration decoration, TextDecoration.State state) {
/* 2024 */     Objects.requireNonNull(state, "state");
/*      */     
/* 2026 */     TextDecoration.State oldState = decoration(decoration);
/* 2027 */     if (oldState == TextDecoration.State.NOT_SET) {
/* 2028 */       return style(style().decoration(decoration, state));
/*      */     }
/* 2030 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   default Map<TextDecoration, TextDecoration.State> decorations() {
/* 2041 */     return style().decorations();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component decorations(@NotNull Map<TextDecoration, TextDecoration.State> decorations) {
/* 2056 */     return style(style().decorations(decorations));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   default ClickEvent clickEvent() {
/* 2067 */     return style().clickEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component clickEvent(@Nullable ClickEvent event) {
/* 2080 */     return style(style().clickEvent(event));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   default HoverEvent<?> hoverEvent() {
/* 2091 */     return style().hoverEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component hoverEvent(@Nullable HoverEventSource<?> source) {
/* 2104 */     return style(style().hoverEvent(source));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   default String insertion() {
/* 2115 */     return style().insertion();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component insertion(@Nullable String insertion) {
/* 2128 */     return style(style().insertion(insertion));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   default boolean hasStyling() {
/* 2139 */     return !style().isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component replaceText(@NotNull Consumer<TextReplacementConfig.Builder> configurer) {
/* 2151 */     Objects.requireNonNull(configurer, "configurer");
/* 2152 */     return replaceText((TextReplacementConfig)AbstractBuilder.configureAndBuild(TextReplacementConfig.builder(), configurer));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component replaceText(@NotNull TextReplacementConfig config) {
/* 2164 */     Objects.requireNonNull(config, "replacement");
/* 2165 */     if (!(config instanceof TextReplacementConfigImpl)) {
/* 2166 */       throw new IllegalArgumentException("Provided replacement was a custom TextReplacementConfig implementation, which is not supported.");
/*      */     }
/* 2168 */     return TextReplacementRenderer.INSTANCE.render(this, ((TextReplacementConfigImpl)config).createState());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   default Component compact() {
/* 2178 */     return ComponentCompaction.compact(this, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   Iterable<Component> iterable(@NotNull ComponentIteratorType type, @NotNull ComponentIteratorFlag... flags) {
/* 2190 */     return iterable(type, (flags == null) ? Collections.<ComponentIteratorFlag>emptySet() : MonkeyBars.enumSet(ComponentIteratorFlag.class, (Enum[])flags));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   default Iterable<Component> iterable(@NotNull ComponentIteratorType type, @NotNull Set<ComponentIteratorFlag> flags) {
/* 2202 */     Objects.requireNonNull(type, "type");
/* 2203 */     Objects.requireNonNull(flags, "flags");
/* 2204 */     return (Iterable<Component>)new ForwardingIterator(() -> iterator(type, flags), () -> spliterator(type, flags));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   Iterator<Component> iterator(@NotNull ComponentIteratorType type, @NotNull ComponentIteratorFlag... flags) {
/* 2218 */     return iterator(type, (flags == null) ? Collections.<ComponentIteratorFlag>emptySet() : MonkeyBars.enumSet(ComponentIteratorFlag.class, (Enum[])flags));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   default Iterator<Component> iterator(@NotNull ComponentIteratorType type, @NotNull Set<ComponentIteratorFlag> flags) {
/* 2232 */     return new ComponentIterator(this, Objects.<ComponentIteratorType>requireNonNull(type, "type"), Objects.<Set<ComponentIteratorFlag>>requireNonNull(flags, "flags"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   Spliterator<Component> spliterator(@NotNull ComponentIteratorType type, @NotNull ComponentIteratorFlag... flags) {
/* 2246 */     return spliterator(type, (flags == null) ? Collections.<ComponentIteratorFlag>emptySet() : MonkeyBars.enumSet(ComponentIteratorFlag.class, (Enum[])flags));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   default Spliterator<Component> spliterator(@NotNull ComponentIteratorType type, @NotNull Set<ComponentIteratorFlag> flags) {
/* 2260 */     return Spliterators.spliteratorUnknownSize(iterator(type, flags), 1296);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @ScheduledForRemoval(inVersion = "5.0.0")
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component replaceText(@NotNull String search, @Nullable ComponentLike replacement) {
/* 2276 */     return replaceText(b -> b.matchLiteral(search).replacement(replacement));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @ScheduledForRemoval(inVersion = "5.0.0")
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component replaceText(@NotNull Pattern pattern, @NotNull Function<TextComponent.Builder, ComponentLike> replacement) {
/* 2292 */     return replaceText(b -> b.match(pattern).replacement(replacement));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @ScheduledForRemoval(inVersion = "5.0.0")
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component replaceFirstText(@NotNull String search, @Nullable ComponentLike replacement) {
/* 2308 */     return replaceText(b -> b.matchLiteral(search).once().replacement(replacement));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @ScheduledForRemoval(inVersion = "5.0.0")
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component replaceFirstText(@NotNull Pattern pattern, @NotNull Function<TextComponent.Builder, ComponentLike> replacement) {
/* 2324 */     return replaceText(b -> b.match(pattern).once().replacement(replacement));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @ScheduledForRemoval(inVersion = "5.0.0")
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component replaceText(@NotNull String search, @Nullable ComponentLike replacement, int numberOfReplacements) {
/* 2341 */     return replaceText(b -> b.matchLiteral(search).times(numberOfReplacements).replacement(replacement));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @ScheduledForRemoval(inVersion = "5.0.0")
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component replaceText(@NotNull Pattern pattern, @NotNull Function<TextComponent.Builder, ComponentLike> replacement, int numberOfReplacements) {
/* 2358 */     return replaceText(b -> b.match(pattern).times(numberOfReplacements).replacement(replacement));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @ScheduledForRemoval(inVersion = "5.0.0")
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component replaceText(@NotNull String search, @Nullable ComponentLike replacement, @NotNull IntFunction2<PatternReplacementResult> fn) {
/* 2377 */     return replaceText(b -> b.matchLiteral(search).replacement(replacement).condition(fn));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @ScheduledForRemoval(inVersion = "5.0.0")
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   default Component replaceText(@NotNull Pattern pattern, @NotNull Function<TextComponent.Builder, ComponentLike> replacement, @NotNull IntFunction2<PatternReplacementResult> fn) {
/* 2396 */     return replaceText(b -> b.match(pattern).replacement(replacement).condition(fn));
/*      */   }
/*      */ 
/*      */   
/*      */   default void componentBuilderApply(@NotNull ComponentBuilder<?, ?> component) {
/* 2401 */     component.append(this);
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   default Component asComponent() {
/* 2406 */     return this;
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   default HoverEvent<Component> asHoverEvent(@NotNull UnaryOperator<Component> op) {
/* 2411 */     return HoverEvent.showText(op.apply(this));
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   default Stream<? extends ExaminableProperty> examinableProperties() {
/* 2416 */     return Stream.of(new ExaminableProperty[] {
/* 2417 */           ExaminableProperty.of("style", style()), 
/* 2418 */           ExaminableProperty.of("children", children())
/*      */         });
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   List<Component> children();
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   Component children(@NotNull List<? extends ComponentLike> paramList);
/*      */   
/*      */   @NotNull
/*      */   Style style();
/*      */   
/*      */   @Contract(pure = true)
/*      */   @NotNull
/*      */   Component style(@NotNull Style paramStyle);
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\Component.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
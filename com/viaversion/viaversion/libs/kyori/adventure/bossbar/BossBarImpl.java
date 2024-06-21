/*     */ package com.viaversion.viaversion.libs.kyori.adventure.bossbar;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Services;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Stream;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
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
/*     */ final class BossBarImpl
/*     */   extends HackyBossBarPlatformBridge
/*     */   implements BossBar
/*     */ {
/*  49 */   private final List<BossBar.Listener> listeners = new CopyOnWriteArrayList<>();
/*     */   private Component name;
/*     */   private float progress;
/*     */   private BossBar.Color color;
/*     */   private BossBar.Overlay overlay;
/*  54 */   private final Set<BossBar.Flag> flags = EnumSet.noneOf(BossBar.Flag.class);
/*     */   @Nullable
/*     */   BossBarImplementation implementation;
/*     */   
/*     */   @Internal
/*     */   static final class ImplementationAccessor {
/*  60 */     private static final Optional<BossBarImplementation.Provider> SERVICE = Services.service(BossBarImplementation.Provider.class);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     static <I extends BossBarImplementation> I get(@NotNull BossBar bar, @NotNull Class<I> type) {
/*  67 */       BossBarImplementation implementation = ((BossBarImpl)bar).implementation;
/*  68 */       if (implementation == null) {
/*  69 */         implementation = ((BossBarImplementation.Provider)SERVICE.get()).create(bar);
/*  70 */         ((BossBarImpl)bar).implementation = implementation;
/*     */       } 
/*  72 */       return type.cast(implementation);
/*     */     }
/*     */   }
/*     */   
/*     */   BossBarImpl(@NotNull Component name, float progress, @NotNull BossBar.Color color, @NotNull BossBar.Overlay overlay) {
/*  77 */     this.name = Objects.<Component>requireNonNull(name, "name");
/*  78 */     this.progress = progress;
/*  79 */     this.color = Objects.<BossBar.Color>requireNonNull(color, "color");
/*  80 */     this.overlay = Objects.<BossBar.Overlay>requireNonNull(overlay, "overlay");
/*     */   }
/*     */   
/*     */   BossBarImpl(@NotNull Component name, float progress, @NotNull BossBar.Color color, @NotNull BossBar.Overlay overlay, @NotNull Set<BossBar.Flag> flags) {
/*  84 */     this(name, progress, color, overlay);
/*  85 */     this.flags.addAll(flags);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Component name() {
/*  90 */     return this.name;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar name(@NotNull Component newName) {
/*  95 */     Objects.requireNonNull(newName, "name");
/*  96 */     Component oldName = this.name;
/*  97 */     if (!Objects.equals(newName, oldName)) {
/*  98 */       this.name = newName;
/*  99 */       forEachListener(listener -> listener.bossBarNameChanged(this, oldName, newName));
/*     */     } 
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public float progress() {
/* 106 */     return this.progress;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar progress(float newProgress) {
/* 111 */     checkProgress(newProgress);
/* 112 */     float oldProgress = this.progress;
/* 113 */     if (newProgress != oldProgress) {
/* 114 */       this.progress = newProgress;
/* 115 */       forEachListener(listener -> listener.bossBarProgressChanged(this, oldProgress, newProgress));
/*     */     } 
/* 117 */     return this;
/*     */   }
/*     */   
/*     */   static void checkProgress(float progress) {
/* 121 */     if (progress < 0.0F || progress > 1.0F) {
/* 122 */       throw new IllegalArgumentException("progress must be between 0.0 and 1.0, was " + progress);
/*     */     }
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar.Color color() {
/* 128 */     return this.color;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar color(@NotNull BossBar.Color newColor) {
/* 133 */     Objects.requireNonNull(newColor, "color");
/* 134 */     BossBar.Color oldColor = this.color;
/* 135 */     if (newColor != oldColor) {
/* 136 */       this.color = newColor;
/* 137 */       forEachListener(listener -> listener.bossBarColorChanged(this, oldColor, newColor));
/*     */     } 
/* 139 */     return this;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar.Overlay overlay() {
/* 144 */     return this.overlay;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar overlay(@NotNull BossBar.Overlay newOverlay) {
/* 149 */     Objects.requireNonNull(newOverlay, "overlay");
/* 150 */     BossBar.Overlay oldOverlay = this.overlay;
/* 151 */     if (newOverlay != oldOverlay) {
/* 152 */       this.overlay = newOverlay;
/* 153 */       forEachListener(listener -> listener.bossBarOverlayChanged(this, oldOverlay, newOverlay));
/*     */     } 
/* 155 */     return this;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Set<BossBar.Flag> flags() {
/* 160 */     return Collections.unmodifiableSet(this.flags);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar flags(@NotNull Set<BossBar.Flag> newFlags) {
/* 165 */     if (newFlags.isEmpty()) {
/* 166 */       Set<BossBar.Flag> oldFlags = EnumSet.copyOf(this.flags);
/* 167 */       this.flags.clear();
/* 168 */       forEachListener(listener -> listener.bossBarFlagsChanged(this, Collections.emptySet(), oldFlags));
/* 169 */     } else if (!this.flags.equals(newFlags)) {
/* 170 */       Set<BossBar.Flag> oldFlags = EnumSet.copyOf(this.flags);
/* 171 */       this.flags.clear();
/* 172 */       this.flags.addAll(newFlags);
/* 173 */       Set<BossBar.Flag> added = EnumSet.copyOf(newFlags);
/* 174 */       Objects.requireNonNull(oldFlags); added.removeIf(oldFlags::contains);
/* 175 */       Set<BossBar.Flag> removed = EnumSet.copyOf(oldFlags);
/* 176 */       Objects.requireNonNull(this.flags); removed.removeIf(this.flags::contains);
/* 177 */       forEachListener(listener -> listener.bossBarFlagsChanged(this, added, removed));
/*     */     } 
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasFlag(@NotNull BossBar.Flag flag) {
/* 184 */     return this.flags.contains(flag);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar addFlag(@NotNull BossBar.Flag flag) {
/* 189 */     return editFlags(flag, Set::add, BossBarImpl::onFlagsAdded);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar removeFlag(@NotNull BossBar.Flag flag) {
/* 194 */     return editFlags(flag, Set::remove, BossBarImpl::onFlagsRemoved);
/*     */   }
/*     */   @NotNull
/*     */   private BossBar editFlags(@NotNull BossBar.Flag flag, @NotNull BiPredicate<Set<BossBar.Flag>, BossBar.Flag> predicate, BiConsumer<BossBarImpl, Set<BossBar.Flag>> onChange) {
/* 198 */     if (predicate.test(this.flags, flag)) {
/* 199 */       onChange.accept(this, Collections.singleton(flag));
/*     */     }
/* 201 */     return this;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar addFlags(@NotNull BossBar.Flag... flags) {
/* 206 */     return editFlags(flags, Set::add, BossBarImpl::onFlagsAdded);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar removeFlags(@NotNull BossBar.Flag... flags) {
/* 211 */     return editFlags(flags, Set::remove, BossBarImpl::onFlagsRemoved);
/*     */   }
/*     */   @NotNull
/*     */   private BossBar editFlags(BossBar.Flag[] flags, BiPredicate<Set<BossBar.Flag>, BossBar.Flag> predicate, BiConsumer<BossBarImpl, Set<BossBar.Flag>> onChange) {
/* 215 */     if (flags.length == 0) return this; 
/* 216 */     Set<BossBar.Flag> changes = null;
/* 217 */     for (int i = 0, length = flags.length; i < length; i++) {
/* 218 */       if (predicate.test(this.flags, flags[i])) {
/* 219 */         if (changes == null) {
/* 220 */           changes = EnumSet.noneOf(BossBar.Flag.class);
/*     */         }
/* 222 */         changes.add(flags[i]);
/*     */       } 
/*     */     } 
/* 225 */     if (changes != null) {
/* 226 */       onChange.accept(this, changes);
/*     */     }
/* 228 */     return this;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar addFlags(@NotNull Iterable<BossBar.Flag> flags) {
/* 233 */     return editFlags(flags, Set::add, BossBarImpl::onFlagsAdded);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar removeFlags(@NotNull Iterable<BossBar.Flag> flags) {
/* 238 */     return editFlags(flags, Set::remove, BossBarImpl::onFlagsRemoved);
/*     */   }
/*     */   @NotNull
/*     */   private BossBar editFlags(Iterable<BossBar.Flag> flags, BiPredicate<Set<BossBar.Flag>, BossBar.Flag> predicate, BiConsumer<BossBarImpl, Set<BossBar.Flag>> onChange) {
/* 242 */     Set<BossBar.Flag> changes = null;
/* 243 */     for (BossBar.Flag flag : flags) {
/* 244 */       if (predicate.test(this.flags, flag)) {
/* 245 */         if (changes == null) {
/* 246 */           changes = EnumSet.noneOf(BossBar.Flag.class);
/*     */         }
/* 248 */         changes.add(flag);
/*     */       } 
/*     */     } 
/* 251 */     if (changes != null) {
/* 252 */       onChange.accept(this, changes);
/*     */     }
/* 254 */     return this;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar addListener(@NotNull BossBar.Listener listener) {
/* 259 */     this.listeners.add(listener);
/* 260 */     return this;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BossBar removeListener(@NotNull BossBar.Listener listener) {
/* 265 */     this.listeners.remove(listener);
/* 266 */     return this;
/*     */   }
/*     */   
/*     */   private void forEachListener(@NotNull Consumer<BossBar.Listener> consumer) {
/* 270 */     for (BossBar.Listener listener : this.listeners) {
/* 271 */       consumer.accept(listener);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void onFlagsAdded(BossBarImpl bar, Set<BossBar.Flag> flagsAdded) {
/* 276 */     bar.forEachListener(listener -> listener.bossBarFlagsChanged(bar, flagsAdded, Collections.emptySet()));
/*     */   }
/*     */   
/*     */   private static void onFlagsRemoved(BossBarImpl bar, Set<BossBar.Flag> flagsRemoved) {
/* 280 */     bar.forEachListener(listener -> listener.bossBarFlagsChanged(bar, Collections.emptySet(), flagsRemoved));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<? extends ExaminableProperty> examinableProperties() {
/* 285 */     return Stream.of(new ExaminableProperty[] {
/* 286 */           ExaminableProperty.of("name", this.name), 
/* 287 */           ExaminableProperty.of("progress", this.progress), 
/* 288 */           ExaminableProperty.of("color", this.color), 
/* 289 */           ExaminableProperty.of("overlay", this.overlay), 
/* 290 */           ExaminableProperty.of("flags", this.flags)
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 296 */     return Internals.toString(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\bossbar\BossBarImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
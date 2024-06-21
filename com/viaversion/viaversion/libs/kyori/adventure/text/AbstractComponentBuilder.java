/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleSetter;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
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
/*     */ abstract class AbstractComponentBuilder<C extends BuildableComponent<C, B>, B extends ComponentBuilder<C, B>>
/*     */   implements ComponentBuilder<C, B>
/*     */ {
/*  52 */   protected List<Component> children = Collections.emptyList();
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Style style;
/*     */ 
/*     */   
/*     */   private Style.Builder styleBuilder;
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractComponentBuilder(@NotNull C component) {
/*  65 */     List<Component> children = component.children();
/*  66 */     if (!children.isEmpty()) {
/*  67 */       this.children = new ArrayList<>(children);
/*     */     }
/*  69 */     if (component.hasStyling()) {
/*  70 */       this.style = component.style();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B append(@NotNull Component component) {
/*  77 */     if (component == Component.empty()) return (B)this; 
/*  78 */     prepareChildren();
/*  79 */     this.children.add(Objects.<Component>requireNonNull(component, "component"));
/*  80 */     return (B)this;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public B append(@NotNull Component... components) {
/*  85 */     return append((ComponentLike[])components);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B append(@NotNull ComponentLike... components) {
/*  91 */     Objects.requireNonNull(components, "components");
/*  92 */     boolean prepared = false;
/*  93 */     for (int i = 0, length = components.length; i < length; i++) {
/*  94 */       Component component = ((ComponentLike)Objects.<ComponentLike>requireNonNull(components[i], "components[?]")).asComponent();
/*  95 */       if (component != Component.empty()) {
/*  96 */         if (!prepared) {
/*  97 */           prepareChildren();
/*  98 */           prepared = true;
/*     */         } 
/* 100 */         this.children.add(Objects.<Component>requireNonNull(component, "components[?]"));
/*     */       } 
/*     */     } 
/* 103 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B append(@NotNull Iterable<? extends ComponentLike> components) {
/* 109 */     Objects.requireNonNull(components, "components");
/* 110 */     boolean prepared = false;
/* 111 */     for (ComponentLike like : components) {
/* 112 */       Component component = ((ComponentLike)Objects.<ComponentLike>requireNonNull(like, "components[?]")).asComponent();
/* 113 */       if (component != Component.empty()) {
/* 114 */         if (!prepared) {
/* 115 */           prepareChildren();
/* 116 */           prepared = true;
/*     */         } 
/* 118 */         this.children.add(Objects.<Component>requireNonNull(component, "components[?]"));
/*     */       } 
/*     */     } 
/* 121 */     return (B)this;
/*     */   }
/*     */   
/*     */   private void prepareChildren() {
/* 125 */     if (this.children == Collections.emptyList()) {
/* 126 */       this.children = new ArrayList<>();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B applyDeep(@NotNull Consumer<? super ComponentBuilder<?, ?>> consumer) {
/* 133 */     apply(consumer);
/* 134 */     if (this.children == Collections.emptyList()) {
/* 135 */       return (B)this;
/*     */     }
/* 137 */     ListIterator<Component> it = this.children.listIterator();
/* 138 */     while (it.hasNext()) {
/* 139 */       Component child = it.next();
/* 140 */       if (!(child instanceof BuildableComponent)) {
/*     */         continue;
/*     */       }
/* 143 */       ComponentBuilder<?, ?> childBuilder = (ComponentBuilder<?, ?>)((BuildableComponent)child).toBuilder();
/* 144 */       childBuilder.applyDeep(consumer);
/* 145 */       it.set((Component)childBuilder.build());
/*     */     } 
/* 147 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B mapChildren(@NotNull Function<BuildableComponent<?, ?>, ? extends BuildableComponent<?, ?>> function) {
/* 153 */     if (this.children == Collections.emptyList()) {
/* 154 */       return (B)this;
/*     */     }
/* 156 */     ListIterator<Component> it = this.children.listIterator();
/* 157 */     while (it.hasNext()) {
/* 158 */       Component child = it.next();
/* 159 */       if (!(child instanceof BuildableComponent)) {
/*     */         continue;
/*     */       }
/* 162 */       BuildableComponent<?, ?> mappedChild = Objects.<BuildableComponent<?, ?>>requireNonNull(function.apply((BuildableComponent<?, ?>)child), "mappedChild");
/* 163 */       if (child == mappedChild) {
/*     */         continue;
/*     */       }
/* 166 */       it.set(mappedChild);
/*     */     } 
/* 168 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B mapChildrenDeep(@NotNull Function<BuildableComponent<?, ?>, ? extends BuildableComponent<?, ?>> function) {
/* 174 */     if (this.children == Collections.emptyList()) {
/* 175 */       return (B)this;
/*     */     }
/* 177 */     ListIterator<Component> it = this.children.listIterator();
/* 178 */     while (it.hasNext()) {
/* 179 */       Component child = it.next();
/* 180 */       if (!(child instanceof BuildableComponent)) {
/*     */         continue;
/*     */       }
/* 183 */       BuildableComponent<?, ?> mappedChild = Objects.<BuildableComponent<?, ?>>requireNonNull(function.apply((BuildableComponent<?, ?>)child), "mappedChild");
/* 184 */       if (mappedChild.children().isEmpty()) {
/* 185 */         if (child == mappedChild) {
/*     */           continue;
/*     */         }
/* 188 */         it.set(mappedChild); continue;
/*     */       } 
/* 190 */       ComponentBuilder<?, ?> builder = (ComponentBuilder<?, ?>)mappedChild.toBuilder();
/* 191 */       builder.mapChildrenDeep(function);
/* 192 */       it.set((Component)builder.build());
/*     */     } 
/*     */     
/* 195 */     return (B)this;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public List<Component> children() {
/* 200 */     return Collections.unmodifiableList(this.children);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B style(@NotNull Style style) {
/* 206 */     this.style = style;
/* 207 */     this.styleBuilder = null;
/* 208 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B style(@NotNull Consumer<Style.Builder> consumer) {
/* 214 */     consumer.accept(styleBuilder());
/* 215 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B font(@Nullable Key font) {
/* 221 */     styleBuilder().font(font);
/* 222 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B color(@Nullable TextColor color) {
/* 228 */     styleBuilder().color(color);
/* 229 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B colorIfAbsent(@Nullable TextColor color) {
/* 235 */     styleBuilder().colorIfAbsent(color);
/* 236 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B decoration(@NotNull TextDecoration decoration, TextDecoration.State state) {
/* 242 */     styleBuilder().decoration(decoration, state);
/* 243 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B decorationIfAbsent(@NotNull TextDecoration decoration, TextDecoration.State state) {
/* 249 */     styleBuilder().decorationIfAbsent(decoration, state);
/* 250 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B clickEvent(@Nullable ClickEvent event) {
/* 256 */     styleBuilder().clickEvent(event);
/* 257 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B hoverEvent(@Nullable HoverEventSource<?> source) {
/* 263 */     styleBuilder().hoverEvent(source);
/* 264 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B insertion(@Nullable String insertion) {
/* 270 */     styleBuilder().insertion(insertion);
/* 271 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B mergeStyle(@NotNull Component that, @NotNull Set<Style.Merge> merges) {
/* 277 */     styleBuilder().merge(((Component)Objects.<Component>requireNonNull(that, "component")).style(), merges);
/* 278 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public B resetStyle() {
/* 284 */     this.style = null;
/* 285 */     this.styleBuilder = null;
/* 286 */     return (B)this;
/*     */   }
/*     */   
/*     */   private Style.Builder styleBuilder() {
/* 290 */     if (this.styleBuilder == null) {
/* 291 */       if (this.style != null) {
/* 292 */         this.styleBuilder = this.style.toBuilder();
/* 293 */         this.style = null;
/*     */       } else {
/* 295 */         this.styleBuilder = Style.style();
/*     */       } 
/*     */     }
/* 298 */     return this.styleBuilder;
/*     */   }
/*     */   
/*     */   protected final boolean hasStyle() {
/* 302 */     return (this.styleBuilder != null || this.style != null);
/*     */   }
/*     */   @NotNull
/*     */   protected Style buildStyle() {
/* 306 */     if (this.styleBuilder != null)
/* 307 */       return this.styleBuilder.build(); 
/* 308 */     if (this.style != null) {
/* 309 */       return this.style;
/*     */     }
/* 311 */     return Style.empty();
/*     */   }
/*     */   
/*     */   protected AbstractComponentBuilder() {}
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\AbstractComponentBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
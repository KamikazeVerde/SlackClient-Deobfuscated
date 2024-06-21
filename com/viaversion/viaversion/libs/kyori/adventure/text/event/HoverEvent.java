/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text.event;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.key.Keyed;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.nbt.api.BinaryTagHolder;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleBuilderApplicable;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.renderer.ComponentRenderer;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.Examinable;
/*     */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.function.UnaryOperator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HoverEvent<V>
/*     */   implements Examinable, HoverEventSource<V>, StyleBuilderApplicable
/*     */ {
/*     */   private final Action<V> action;
/*     */   private final V value;
/*     */   
/*     */   @NotNull
/*     */   public static HoverEvent<Component> showText(@NotNull ComponentLike text) {
/*  66 */     return showText(text.asComponent());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static HoverEvent<Component> showText(@NotNull Component text) {
/*  77 */     return new HoverEvent<>(Action.SHOW_TEXT, text);
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
/*     */   public static HoverEvent<ShowItem> showItem(@NotNull Key item, int count) {
/*  89 */     return showItem(item, count, (BinaryTagHolder)null);
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
/*     */   public static HoverEvent<ShowItem> showItem(@NotNull Keyed item, int count) {
/* 101 */     return showItem(item, count, (BinaryTagHolder)null);
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
/*     */   public static HoverEvent<ShowItem> showItem(@NotNull Key item, int count, @Nullable BinaryTagHolder nbt) {
/* 114 */     return showItem(ShowItem.of(item, count, nbt));
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
/*     */   public static HoverEvent<ShowItem> showItem(@NotNull Keyed item, int count, @Nullable BinaryTagHolder nbt) {
/* 127 */     return showItem(ShowItem.of(item, count, nbt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static HoverEvent<ShowItem> showItem(@NotNull ShowItem item) {
/* 138 */     return new HoverEvent<>(Action.SHOW_ITEM, item);
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
/*     */   public static HoverEvent<ShowEntity> showEntity(@NotNull Key type, @NotNull UUID id) {
/* 152 */     return showEntity(type, id, (Component)null);
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
/*     */   public static HoverEvent<ShowEntity> showEntity(@NotNull Keyed type, @NotNull UUID id) {
/* 166 */     return showEntity(type, id, (Component)null);
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
/*     */   public static HoverEvent<ShowEntity> showEntity(@NotNull Key type, @NotNull UUID id, @Nullable Component name) {
/* 181 */     return showEntity(ShowEntity.of(type, id, name));
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
/*     */   public static HoverEvent<ShowEntity> showEntity(@NotNull Keyed type, @NotNull UUID id, @Nullable Component name) {
/* 196 */     return showEntity(ShowEntity.of(type, id, name));
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
/*     */   public static HoverEvent<ShowEntity> showEntity(@NotNull ShowEntity entity) {
/* 209 */     return new HoverEvent<>(Action.SHOW_ENTITY, entity);
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
/*     */   public static <V> HoverEvent<V> hoverEvent(@NotNull Action<V> action, @NotNull V value) {
/* 222 */     return new HoverEvent<>(action, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private HoverEvent(@NotNull Action<V> action, @NotNull V value) {
/* 229 */     this.action = Objects.<Action<V>>requireNonNull(action, "action");
/* 230 */     this.value = Objects.requireNonNull(value, "value");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Action<V> action() {
/* 240 */     return this.action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public V value() {
/* 250 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public HoverEvent<V> value(@NotNull V value) {
/* 261 */     return new HoverEvent(this.action, value);
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
/*     */   public <C> HoverEvent<V> withRenderedValue(@NotNull ComponentRenderer<C> renderer, @NotNull C context) {
/* 274 */     V oldValue = this.value;
/* 275 */     V newValue = this.action.renderer.render(renderer, context, oldValue);
/* 276 */     if (newValue != oldValue) return new HoverEvent(this.action, newValue); 
/* 277 */     return this;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public HoverEvent<V> asHoverEvent() {
/* 282 */     return this;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public HoverEvent<V> asHoverEvent(@NotNull UnaryOperator<V> op) {
/* 287 */     if (op == UnaryOperator.identity()) return this; 
/* 288 */     return new HoverEvent(this.action, op.apply(this.value));
/*     */   }
/*     */ 
/*     */   
/*     */   public void styleApply(Style.Builder style) {
/* 293 */     style.hoverEvent(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/* 298 */     if (this == other) return true; 
/* 299 */     if (other == null || getClass() != other.getClass()) return false; 
/* 300 */     HoverEvent<?> that = (HoverEvent)other;
/* 301 */     return (this.action == that.action && this.value.equals(that.value));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 306 */     int result = this.action.hashCode();
/* 307 */     result = 31 * result + this.value.hashCode();
/* 308 */     return result;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<? extends ExaminableProperty> examinableProperties() {
/* 313 */     return Stream.of(new ExaminableProperty[] {
/* 314 */           ExaminableProperty.of("action", this.action), 
/* 315 */           ExaminableProperty.of("value", this.value)
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 321 */     return Internals.toString(this);
/*     */   }
/*     */ 
/*     */   
/*     */   @FunctionalInterface
/*     */   static interface Renderer<V>
/*     */   {
/*     */     @NotNull
/*     */     <C> V render(@NotNull ComponentRenderer<C> param1ComponentRenderer, @NotNull C param1C, @NotNull V param1V);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class ShowItem
/*     */     implements Examinable
/*     */   {
/*     */     private final Key item;
/*     */     private final int count;
/*     */     @Nullable
/*     */     private final BinaryTagHolder nbt;
/*     */     
/*     */     @NotNull
/*     */     public static ShowItem of(@NotNull Key item, int count) {
/* 343 */       return of(item, count, (BinaryTagHolder)null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public static ShowItem of(@NotNull Keyed item, int count) {
/* 355 */       return of(item, count, (BinaryTagHolder)null);
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
/*     */     @NotNull
/*     */     public static ShowItem of(@NotNull Key item, int count, @Nullable BinaryTagHolder nbt) {
/* 368 */       return new ShowItem(Objects.<Key>requireNonNull(item, "item"), count, nbt);
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
/*     */     @NotNull
/*     */     public static ShowItem of(@NotNull Keyed item, int count, @Nullable BinaryTagHolder nbt) {
/* 381 */       return new ShowItem(((Keyed)Objects.<Keyed>requireNonNull(item, "item")).key(), count, nbt);
/*     */     }
/*     */     
/*     */     private ShowItem(@NotNull Key item, int count, @Nullable BinaryTagHolder nbt) {
/* 385 */       this.item = item;
/* 386 */       this.count = count;
/* 387 */       this.nbt = nbt;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public Key item() {
/* 397 */       return this.item;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public ShowItem item(@NotNull Key item) {
/* 408 */       if (((Key)Objects.<Key>requireNonNull(item, "item")).equals(this.item)) return this; 
/* 409 */       return new ShowItem(item, this.count, this.nbt);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int count() {
/* 419 */       return this.count;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public ShowItem count(int count) {
/* 430 */       if (count == this.count) return this; 
/* 431 */       return new ShowItem(this.item, count, this.nbt);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public BinaryTagHolder nbt() {
/* 441 */       return this.nbt;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public ShowItem nbt(@Nullable BinaryTagHolder nbt) {
/* 452 */       if (Objects.equals(nbt, this.nbt)) return this; 
/* 453 */       return new ShowItem(this.item, this.count, nbt);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object other) {
/* 458 */       if (this == other) return true; 
/* 459 */       if (other == null || getClass() != other.getClass()) return false; 
/* 460 */       ShowItem that = (ShowItem)other;
/* 461 */       return (this.item.equals(that.item) && this.count == that.count && Objects.equals(this.nbt, that.nbt));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 466 */       int result = this.item.hashCode();
/* 467 */       result = 31 * result + Integer.hashCode(this.count);
/* 468 */       result = 31 * result + Objects.hashCode(this.nbt);
/* 469 */       return result;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Stream<? extends ExaminableProperty> examinableProperties() {
/* 474 */       return Stream.of(new ExaminableProperty[] {
/* 475 */             ExaminableProperty.of("item", this.item), 
/* 476 */             ExaminableProperty.of("count", this.count), 
/* 477 */             ExaminableProperty.of("nbt", this.nbt)
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 483 */       return Internals.toString(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class ShowEntity
/*     */     implements Examinable
/*     */   {
/*     */     private final Key type;
/*     */ 
/*     */ 
/*     */     
/*     */     private final UUID id;
/*     */ 
/*     */     
/*     */     private final Component name;
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public static ShowEntity of(@NotNull Key type, @NotNull UUID id) {
/* 506 */       return of(type, id, (Component)null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public static ShowEntity of(@NotNull Keyed type, @NotNull UUID id) {
/* 518 */       return of(type, id, (Component)null);
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
/*     */     @NotNull
/*     */     public static ShowEntity of(@NotNull Key type, @NotNull UUID id, @Nullable Component name) {
/* 531 */       return new ShowEntity(Objects.<Key>requireNonNull(type, "type"), Objects.<UUID>requireNonNull(id, "id"), name);
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
/*     */     @NotNull
/*     */     public static ShowEntity of(@NotNull Keyed type, @NotNull UUID id, @Nullable Component name) {
/* 544 */       return new ShowEntity(((Keyed)Objects.<Keyed>requireNonNull(type, "type")).key(), Objects.<UUID>requireNonNull(id, "id"), name);
/*     */     }
/*     */     
/*     */     private ShowEntity(@NotNull Key type, @NotNull UUID id, @Nullable Component name) {
/* 548 */       this.type = type;
/* 549 */       this.id = id;
/* 550 */       this.name = name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public Key type() {
/* 560 */       return this.type;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public ShowEntity type(@NotNull Key type) {
/* 571 */       if (((Key)Objects.<Key>requireNonNull(type, "type")).equals(this.type)) return this; 
/* 572 */       return new ShowEntity(type, this.id, this.name);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public ShowEntity type(@NotNull Keyed type) {
/* 583 */       return type(((Keyed)Objects.<Keyed>requireNonNull(type, "type")).key());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public UUID id() {
/* 593 */       return this.id;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public ShowEntity id(@NotNull UUID id) {
/* 604 */       if (((UUID)Objects.<UUID>requireNonNull(id)).equals(this.id)) return this; 
/* 605 */       return new ShowEntity(this.type, id, this.name);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public Component name() {
/* 615 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public ShowEntity name(@Nullable Component name) {
/* 626 */       if (Objects.equals(name, this.name)) return this; 
/* 627 */       return new ShowEntity(this.type, this.id, name);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object other) {
/* 632 */       if (this == other) return true; 
/* 633 */       if (other == null || getClass() != other.getClass()) return false; 
/* 634 */       ShowEntity that = (ShowEntity)other;
/* 635 */       return (this.type.equals(that.type) && this.id.equals(that.id) && Objects.equals(this.name, that.name));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 640 */       int result = this.type.hashCode();
/* 641 */       result = 31 * result + this.id.hashCode();
/* 642 */       result = 31 * result + Objects.hashCode(this.name);
/* 643 */       return result;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Stream<? extends ExaminableProperty> examinableProperties() {
/* 648 */       return Stream.of(new ExaminableProperty[] {
/* 649 */             ExaminableProperty.of("type", this.type), 
/* 650 */             ExaminableProperty.of("id", this.id), 
/* 651 */             ExaminableProperty.of("name", this.name)
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 657 */       return Internals.toString(this);
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
/*     */ 
/*     */   
/*     */   public static final class Action<V>
/*     */   {
/* 673 */     public static final Action<Component> SHOW_TEXT = new Action("show_text", (Class)Component.class, true, (Renderer)new Renderer<Component>() {
/*     */           @NotNull
/*     */           public <C> Component render(@NotNull ComponentRenderer<C> renderer, @NotNull C context, @NotNull Component value) {
/* 676 */             return renderer.render(value, context);
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 684 */     public static final Action<HoverEvent.ShowItem> SHOW_ITEM = new Action("show_item", (Class)HoverEvent.ShowItem.class, true, (Renderer)new Renderer<HoverEvent.ShowItem>() {
/*     */           @NotNull
/*     */           public <C> HoverEvent.ShowItem render(@NotNull ComponentRenderer<C> renderer, @NotNull C context, @NotNull HoverEvent.ShowItem value) {
/* 687 */             return value;
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 695 */     public static final Action<HoverEvent.ShowEntity> SHOW_ENTITY = new Action("show_entity", (Class)HoverEvent.ShowEntity.class, true, (Renderer)new Renderer<HoverEvent.ShowEntity>() {
/*     */           @NotNull
/*     */           public <C> HoverEvent.ShowEntity render(@NotNull ComponentRenderer<C> renderer, @NotNull C context, @NotNull HoverEvent.ShowEntity value) {
/* 698 */             if (value.name == null) return value; 
/* 699 */             return value.name(renderer.render(value.name, context));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*     */     public static final Index<String, Action<?>> NAMES;
/*     */     private final String name;
/*     */     
/*     */     static {
/* 708 */       NAMES = Index.create(constant -> constant.name, (Object[])new Action[] { SHOW_TEXT, SHOW_ITEM, SHOW_ENTITY });
/*     */     }
/*     */ 
/*     */     
/*     */     private final Class<V> type;
/*     */     
/*     */     private final boolean readable;
/*     */     
/*     */     private final Renderer<V> renderer;
/*     */ 
/*     */     
/*     */     Action(String name, Class<V> type, boolean readable, Renderer<V> renderer) {
/* 720 */       this.name = name;
/* 721 */       this.type = type;
/* 722 */       this.readable = readable;
/* 723 */       this.renderer = renderer;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public Class<V> type() {
/* 733 */       return this.type;
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
/* 744 */       return this.readable;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public String toString() {
/* 749 */       return this.name;
/*     */     }
/*     */     
/*     */     @FunctionalInterface
/*     */     static interface Renderer<V> {
/*     */       @NotNull
/*     */       <C> V render(@NotNull ComponentRenderer<C> param2ComponentRenderer, @NotNull C param2C, @NotNull V param2V);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\event\HoverEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
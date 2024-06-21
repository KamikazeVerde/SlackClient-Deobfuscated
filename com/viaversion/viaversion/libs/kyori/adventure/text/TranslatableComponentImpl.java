/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Collectors;
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
/*     */ final class TranslatableComponentImpl
/*     */   extends AbstractComponent
/*     */   implements TranslatableComponent
/*     */ {
/*     */   private final String key;
/*     */   private final List<Component> args;
/*     */   
/*     */   static TranslatableComponent create(@NotNull List<Component> children, @NotNull Style style, @NotNull String key, @NotNull ComponentLike[] args) {
/*  41 */     Objects.requireNonNull(args, "args");
/*  42 */     return create((List)children, style, key, Arrays.asList(args));
/*     */   }
/*     */   
/*     */   static TranslatableComponent create(@NotNull List<? extends ComponentLike> children, @NotNull Style style, @NotNull String key, @NotNull List<? extends ComponentLike> args) {
/*  46 */     return new TranslatableComponentImpl(
/*  47 */         ComponentLike.asComponents(children, IS_NOT_EMPTY), 
/*  48 */         Objects.<Style>requireNonNull(style, "style"), 
/*  49 */         Objects.<String>requireNonNull(key, "key"), 
/*  50 */         ComponentLike.asComponents(args));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   TranslatableComponentImpl(@NotNull List<Component> children, @NotNull Style style, @NotNull String key, @NotNull List<Component> args) {
/*  58 */     super((List)children, style);
/*  59 */     this.key = key;
/*  60 */     this.args = args;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String key() {
/*  65 */     return this.key;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TranslatableComponent key(@NotNull String key) {
/*  70 */     if (Objects.equals(this.key, key)) return this; 
/*  71 */     return create((List)this.children, this.style, key, (List)this.args);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public List<Component> args() {
/*  76 */     return this.args;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TranslatableComponent args(@NotNull ComponentLike... args) {
/*  81 */     return create(this.children, this.style, this.key, args);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TranslatableComponent args(@NotNull List<? extends ComponentLike> args) {
/*  86 */     return create((List)this.children, this.style, this.key, args);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TranslatableComponent children(@NotNull List<? extends ComponentLike> children) {
/*  91 */     return create(children, this.style, this.key, (List)this.args);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TranslatableComponent style(@NotNull Style style) {
/*  96 */     return create((List)this.children, style, this.key, (List)this.args);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/* 101 */     if (this == other) return true; 
/* 102 */     if (!(other instanceof TranslatableComponent)) return false; 
/* 103 */     if (!super.equals(other)) return false; 
/* 104 */     TranslatableComponent that = (TranslatableComponent)other;
/* 105 */     return (Objects.equals(this.key, that.key()) && Objects.equals(this.args, that.args()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 110 */     int result = super.hashCode();
/* 111 */     result = 31 * result + this.key.hashCode();
/* 112 */     result = 31 * result + this.args.hashCode();
/* 113 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 118 */     return Internals.toString(this);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TranslatableComponent.Builder toBuilder() {
/* 123 */     return new BuilderImpl(this);
/*     */   }
/*     */   
/*     */   static final class BuilderImpl extends AbstractComponentBuilder<TranslatableComponent, TranslatableComponent.Builder> implements TranslatableComponent.Builder { @Nullable
/*     */     private String key;
/* 128 */     private List<? extends Component> args = Collections.emptyList();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     BuilderImpl(@NotNull TranslatableComponent component) {
/* 134 */       super(component);
/* 135 */       this.key = component.key();
/* 136 */       this.args = component.args();
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public TranslatableComponent.Builder key(@NotNull String key) {
/* 141 */       this.key = key;
/* 142 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public TranslatableComponent.Builder args(@NotNull ComponentBuilder<?, ?> arg) {
/* 147 */       return args(Collections.singletonList((ComponentLike)((ComponentBuilder)Objects.<ComponentBuilder>requireNonNull(arg, "arg")).build()));
/*     */     }
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public TranslatableComponent.Builder args(@NotNull ComponentBuilder<?, ?>... args) {
/* 153 */       Objects.requireNonNull(args, "args");
/* 154 */       if (args.length == 0) return args(Collections.emptyList()); 
/* 155 */       return args((List<? extends ComponentLike>)Stream.<ComponentBuilder<?, ?>>of(args).map(ComponentBuilder::build).collect(Collectors.toList()));
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public TranslatableComponent.Builder args(@NotNull Component arg) {
/* 160 */       return args(Collections.singletonList(Objects.<Component>requireNonNull(arg, "arg")));
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public TranslatableComponent.Builder args(@NotNull ComponentLike... args) {
/* 165 */       Objects.requireNonNull(args, "args");
/* 166 */       if (args.length == 0) return args(Collections.emptyList()); 
/* 167 */       return args(Arrays.asList(args));
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public TranslatableComponent.Builder args(@NotNull List<? extends ComponentLike> args) {
/* 172 */       this.args = ComponentLike.asComponents(Objects.<List<? extends ComponentLike>>requireNonNull(args, "args"));
/* 173 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public TranslatableComponent build() {
/* 178 */       if (this.key == null) throw new IllegalStateException("key must be set"); 
/* 179 */       return TranslatableComponentImpl.create((List)this.children, buildStyle(), this.key, (List)this.args);
/*     */     }
/*     */     
/*     */     BuilderImpl() {} }
/*     */ 
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\TranslatableComponentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
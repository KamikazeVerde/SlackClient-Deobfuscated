/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.internal.properties.AdventureProperties;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Nag;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.jetbrains.annotations.VisibleForTesting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class TextComponentImpl
/*     */   extends AbstractComponent
/*     */   implements TextComponent
/*     */ {
/*  40 */   private static final boolean WARN_WHEN_LEGACY_FORMATTING_DETECTED = Boolean.TRUE.equals(AdventureProperties.TEXT_WARN_WHEN_LEGACY_FORMATTING_DETECTED.value());
/*     */   
/*     */   @VisibleForTesting
/*     */   static final char SECTION_CHAR = '§';
/*  44 */   static final TextComponent EMPTY = createDirect("");
/*  45 */   static final TextComponent NEWLINE = createDirect("\n");
/*  46 */   static final TextComponent SPACE = createDirect(" ");
/*     */   
/*     */   static TextComponent create(@NotNull List<? extends ComponentLike> children, @NotNull Style style, @NotNull String content) {
/*  49 */     List<Component> filteredChildren = ComponentLike.asComponents(children, IS_NOT_EMPTY);
/*  50 */     if (filteredChildren.isEmpty() && style.isEmpty() && content.isEmpty()) return Component.empty();
/*     */     
/*  52 */     return new TextComponentImpl(filteredChildren, 
/*     */         
/*  54 */         Objects.<Style>requireNonNull(style, "style"), 
/*  55 */         Objects.<String>requireNonNull(content, "content"));
/*     */   }
/*     */   private final String content;
/*     */   @NotNull
/*     */   private static TextComponent createDirect(@NotNull String content) {
/*  60 */     return new TextComponentImpl(Collections.emptyList(), Style.empty(), content);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   TextComponentImpl(@NotNull List<Component> children, @NotNull Style style, @NotNull String content) {
/*  66 */     super((List)children, style);
/*  67 */     this.content = content;
/*     */     
/*  69 */     if (WARN_WHEN_LEGACY_FORMATTING_DETECTED) {
/*  70 */       LegacyFormattingDetected nag = warnWhenLegacyFormattingDetected();
/*  71 */       if (nag != null)
/*  72 */         Nag.print(nag); 
/*     */     } 
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   @Nullable
/*     */   final LegacyFormattingDetected warnWhenLegacyFormattingDetected() {
/*  79 */     if (this.content.indexOf('§') != -1) {
/*  80 */       return new LegacyFormattingDetected(this);
/*     */     }
/*  82 */     return null;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String content() {
/*  87 */     return this.content;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TextComponent content(@NotNull String content) {
/*  92 */     if (Objects.equals(this.content, content)) return this; 
/*  93 */     return create((List)this.children, this.style, content);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TextComponent children(@NotNull List<? extends ComponentLike> children) {
/*  98 */     return create(children, this.style, this.content);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TextComponent style(@NotNull Style style) {
/* 103 */     return create((List)this.children, style, this.content);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/* 108 */     if (this == other) return true; 
/* 109 */     if (!(other instanceof TextComponentImpl)) return false; 
/* 110 */     if (!super.equals(other)) return false; 
/* 111 */     TextComponentImpl that = (TextComponentImpl)other;
/* 112 */     return Objects.equals(this.content, that.content);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 117 */     int result = super.hashCode();
/* 118 */     result = 31 * result + this.content.hashCode();
/* 119 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 124 */     return Internals.toString(this);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TextComponent.Builder toBuilder() {
/* 129 */     return new BuilderImpl(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class BuilderImpl
/*     */     extends AbstractComponentBuilder<TextComponent, TextComponent.Builder>
/*     */     implements TextComponent.Builder
/*     */   {
/* 138 */     private String content = "";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     BuilderImpl(@NotNull TextComponent component) {
/* 144 */       super(component);
/* 145 */       this.content = component.content();
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public TextComponent.Builder content(@NotNull String content) {
/* 150 */       this.content = Objects.<String>requireNonNull(content, "content");
/* 151 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public String content() {
/* 156 */       return this.content;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public TextComponent build() {
/* 161 */       if (isEmpty()) {
/* 162 */         return Component.empty();
/*     */       }
/* 164 */       return TextComponentImpl.create((List)this.children, buildStyle(), this.content);
/*     */     }
/*     */     
/*     */     private boolean isEmpty() {
/* 168 */       return (this.content.isEmpty() && this.children.isEmpty() && !hasStyle());
/*     */     }
/*     */     
/*     */     BuilderImpl() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\TextComponentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
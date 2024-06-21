/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.Gson;
/*     */ import com.viaversion.viaversion.libs.gson.GsonBuilder;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Services;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.UnaryOperator;
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
/*     */ final class GsonComponentSerializerImpl
/*     */   implements GsonComponentSerializer
/*     */ {
/*  38 */   private static final Optional<GsonComponentSerializer.Provider> SERVICE = Services.service(GsonComponentSerializer.Provider.class); private final Gson serializer; private final UnaryOperator<GsonBuilder> populator; private final boolean downsampleColor;
/*  39 */   static final Consumer<GsonComponentSerializer.Builder> BUILDER = SERVICE
/*  40 */     .<Consumer<GsonComponentSerializer.Builder>>map(GsonComponentSerializer.Provider::builder)
/*  41 */     .orElseGet(() -> ());
/*     */   @Nullable
/*     */   private final LegacyHoverEventSerializer legacyHoverSerializer;
/*     */   private final boolean emitLegacyHover;
/*     */   
/*     */   static final class Instances {
/*  47 */     static final GsonComponentSerializer INSTANCE = GsonComponentSerializerImpl.SERVICE
/*  48 */       .map(GsonComponentSerializer.Provider::gson)
/*  49 */       .orElseGet(() -> new GsonComponentSerializerImpl(false, null, false));
/*  50 */     static final GsonComponentSerializer LEGACY_INSTANCE = GsonComponentSerializerImpl.SERVICE
/*  51 */       .map(GsonComponentSerializer.Provider::gsonLegacy)
/*  52 */       .orElseGet(() -> new GsonComponentSerializerImpl(true, null, true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GsonComponentSerializerImpl(boolean downsampleColor, @Nullable LegacyHoverEventSerializer legacyHoverSerializer, boolean emitLegacyHover) {
/*  62 */     this.downsampleColor = downsampleColor;
/*  63 */     this.legacyHoverSerializer = legacyHoverSerializer;
/*  64 */     this.emitLegacyHover = emitLegacyHover;
/*  65 */     this.populator = (builder -> {
/*     */         builder.registerTypeAdapterFactory(new SerializerFactory(downsampleColor, legacyHoverSerializer, emitLegacyHover));
/*     */         return builder;
/*     */       });
/*  69 */     this
/*     */ 
/*     */       
/*  72 */       .serializer = ((GsonBuilder)this.populator.apply((new GsonBuilder()).disableHtmlEscaping())).create();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Gson serializer() {
/*  77 */     return this.serializer;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public UnaryOperator<GsonBuilder> populator() {
/*  82 */     return this.populator;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Component deserialize(@NotNull String string) {
/*  87 */     Component component = (Component)serializer().fromJson(string, Component.class);
/*  88 */     if (component == null) throw ComponentSerializerImpl.notSureHowToDeserialize(string); 
/*  89 */     return component;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Component deserializeOr(@Nullable String input, @Nullable Component fallback) {
/*  94 */     if (input == null) return fallback; 
/*  95 */     Component component = (Component)serializer().fromJson(input, Component.class);
/*  96 */     if (component == null) return fallback; 
/*  97 */     return component;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String serialize(@NotNull Component component) {
/* 102 */     return serializer().toJson(component);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Component deserializeFromTree(@NotNull JsonElement input) {
/* 107 */     Component component = (Component)serializer().fromJson(input, Component.class);
/* 108 */     if (component == null) throw ComponentSerializerImpl.notSureHowToDeserialize(input); 
/* 109 */     return component;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public JsonElement serializeToTree(@NotNull Component component) {
/* 114 */     return serializer().toJsonTree(component);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public GsonComponentSerializer.Builder toBuilder() {
/* 119 */     return new BuilderImpl(this);
/*     */   }
/*     */   
/*     */   static final class BuilderImpl implements GsonComponentSerializer.Builder { private boolean downsampleColor = false;
/*     */     @Nullable
/*     */     private LegacyHoverEventSerializer legacyHoverSerializer;
/*     */     private boolean emitLegacyHover = false;
/*     */     
/*     */     BuilderImpl() {
/* 128 */       GsonComponentSerializerImpl.BUILDER.accept(this);
/*     */     }
/*     */     
/*     */     BuilderImpl(GsonComponentSerializerImpl serializer) {
/* 132 */       this();
/* 133 */       this.downsampleColor = serializer.downsampleColor;
/* 134 */       this.emitLegacyHover = serializer.emitLegacyHover;
/* 135 */       this.legacyHoverSerializer = serializer.legacyHoverSerializer;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public GsonComponentSerializer.Builder downsampleColors() {
/* 140 */       this.downsampleColor = true;
/* 141 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public GsonComponentSerializer.Builder legacyHoverEventSerializer(@Nullable LegacyHoverEventSerializer serializer) {
/* 146 */       this.legacyHoverSerializer = serializer;
/* 147 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public GsonComponentSerializer.Builder emitLegacyHoverEvent() {
/* 152 */       this.emitLegacyHover = true;
/* 153 */       return this;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public GsonComponentSerializer build() {
/* 158 */       if (this.legacyHoverSerializer == null) {
/* 159 */         return this.downsampleColor ? GsonComponentSerializerImpl.Instances.LEGACY_INSTANCE : GsonComponentSerializerImpl.Instances.INSTANCE;
/*     */       }
/* 161 */       return new GsonComponentSerializerImpl(this.downsampleColor, this.legacyHoverSerializer, this.emitLegacyHover);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\serializer\gson\GsonComponentSerializerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.gson.Gson;
/*    */ import com.viaversion.viaversion.libs.gson.GsonBuilder;
/*    */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.builder.AbstractBuilder;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.ComponentSerializer;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.util.PlatformAPI;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.UnaryOperator;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface GsonComponentSerializer
/*    */   extends ComponentSerializer<Component, Component, String>, Buildable<GsonComponentSerializer, GsonComponentSerializer.Builder>
/*    */ {
/*    */   @NotNull
/*    */   static GsonComponentSerializer gson() {
/* 56 */     return GsonComponentSerializerImpl.Instances.INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   static GsonComponentSerializer colorDownsamplingGson() {
/* 69 */     return GsonComponentSerializerImpl.Instances.LEGACY_INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static Builder builder() {
/* 79 */     return new GsonComponentSerializerImpl.BuilderImpl();
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   Gson serializer();
/*    */   
/*    */   @NotNull
/*    */   UnaryOperator<GsonBuilder> populator();
/*    */   
/*    */   @NotNull
/*    */   Component deserializeFromTree(@NotNull JsonElement paramJsonElement);
/*    */   
/*    */   @NotNull
/*    */   JsonElement serializeToTree(@NotNull Component paramComponent);
/*    */   
/*    */   @PlatformAPI
/*    */   @Internal
/*    */   public static interface Provider {
/*    */     @PlatformAPI
/*    */     @Internal
/*    */     @NotNull
/*    */     GsonComponentSerializer gson();
/*    */     
/*    */     @PlatformAPI
/*    */     @Internal
/*    */     @NotNull
/*    */     GsonComponentSerializer gsonLegacy();
/*    */     
/*    */     @PlatformAPI
/*    */     @Internal
/*    */     @NotNull
/*    */     Consumer<GsonComponentSerializer.Builder> builder();
/*    */   }
/*    */   
/*    */   public static interface Builder extends AbstractBuilder<GsonComponentSerializer>, Buildable.Builder<GsonComponentSerializer> {
/*    */     @NotNull
/*    */     Builder downsampleColors();
/*    */     
/*    */     @NotNull
/*    */     Builder legacyHoverEventSerializer(@Nullable LegacyHoverEventSerializer param1LegacyHoverEventSerializer);
/*    */     
/*    */     @NotNull
/*    */     Builder emitLegacyHoverEvent();
/*    */     
/*    */     @NotNull
/*    */     GsonComponentSerializer build();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\serializer\gson\GsonComponentSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
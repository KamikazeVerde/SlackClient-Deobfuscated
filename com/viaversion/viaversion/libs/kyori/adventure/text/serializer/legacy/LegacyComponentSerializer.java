/*     */ package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.builder.AbstractBuilder;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.flattener.ComponentFlattener;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.ComponentSerializer;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.util.PlatformAPI;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.regex.Pattern;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface LegacyComponentSerializer
/*     */   extends ComponentSerializer<Component, TextComponent, String>, Buildable<LegacyComponentSerializer, LegacyComponentSerializer.Builder>
/*     */ {
/*     */   public static final char SECTION_CHAR = '§';
/*     */   public static final char AMPERSAND_CHAR = '&';
/*     */   public static final char HEX_CHAR = '#';
/*     */   
/*     */   @NotNull
/*     */   static LegacyComponentSerializer legacySection() {
/*  62 */     return LegacyComponentSerializerImpl.Instances.SECTION;
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
/*     */   static LegacyComponentSerializer legacyAmpersand() {
/*  76 */     return LegacyComponentSerializerImpl.Instances.AMPERSAND;
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
/*     */   static LegacyComponentSerializer legacy(char legacyCharacter) {
/*  89 */     if (legacyCharacter == '§')
/*  90 */       return legacySection(); 
/*  91 */     if (legacyCharacter == '&') {
/*  92 */       return legacyAmpersand();
/*     */     }
/*  94 */     return builder().character(legacyCharacter).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   static LegacyFormat parseChar(char character) {
/* 105 */     return LegacyComponentSerializerImpl.legacyFormat(character);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static Builder builder() {
/* 115 */     return new LegacyComponentSerializerImpl.BuilderImpl();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   TextComponent deserialize(@NotNull String paramString);
/*     */   
/*     */   @NotNull
/*     */   String serialize(@NotNull Component paramComponent);
/*     */   
/*     */   @PlatformAPI
/*     */   @Internal
/*     */   public static interface Provider {
/*     */     @PlatformAPI
/*     */     @Internal
/*     */     @NotNull
/*     */     LegacyComponentSerializer legacyAmpersand();
/*     */     
/*     */     @PlatformAPI
/*     */     @Internal
/*     */     @NotNull
/*     */     LegacyComponentSerializer legacySection();
/*     */     
/*     */     @PlatformAPI
/*     */     @Internal
/*     */     @NotNull
/*     */     Consumer<LegacyComponentSerializer.Builder> legacy();
/*     */   }
/*     */   
/*     */   public static interface Builder extends AbstractBuilder<LegacyComponentSerializer>, Buildable.Builder<LegacyComponentSerializer> {
/*     */     @NotNull
/*     */     Builder character(char param1Char);
/*     */     
/*     */     @NotNull
/*     */     Builder hexCharacter(char param1Char);
/*     */     
/*     */     @NotNull
/*     */     Builder extractUrls();
/*     */     
/*     */     @NotNull
/*     */     Builder extractUrls(@NotNull Pattern param1Pattern);
/*     */     
/*     */     @NotNull
/*     */     Builder extractUrls(@Nullable Style param1Style);
/*     */     
/*     */     @NotNull
/*     */     Builder extractUrls(@NotNull Pattern param1Pattern, @Nullable Style param1Style);
/*     */     
/*     */     @NotNull
/*     */     Builder hexColors();
/*     */     
/*     */     @NotNull
/*     */     Builder useUnusualXRepeatedCharacterHexFormat();
/*     */     
/*     */     @NotNull
/*     */     Builder flattener(@NotNull ComponentFlattener param1ComponentFlattener);
/*     */     
/*     */     @NotNull
/*     */     LegacyComponentSerializer build();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\serializer\legacy\LegacyComponentSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
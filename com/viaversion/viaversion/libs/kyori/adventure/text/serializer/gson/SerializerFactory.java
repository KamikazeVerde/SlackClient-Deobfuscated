/*    */ package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.gson.Gson;
/*    */ import com.viaversion.viaversion.libs.gson.TypeAdapter;
/*    */ import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
/*    */ import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
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
/*    */ final class SerializerFactory
/*    */   implements TypeAdapterFactory
/*    */ {
/* 41 */   static final Class<Key> KEY_TYPE = Key.class;
/* 42 */   static final Class<Component> COMPONENT_TYPE = Component.class;
/* 43 */   static final Class<Style> STYLE_TYPE = Style.class;
/* 44 */   static final Class<ClickEvent.Action> CLICK_ACTION_TYPE = ClickEvent.Action.class;
/* 45 */   static final Class<HoverEvent.Action> HOVER_ACTION_TYPE = HoverEvent.Action.class;
/* 46 */   static final Class<HoverEvent.ShowItem> SHOW_ITEM_TYPE = HoverEvent.ShowItem.class;
/* 47 */   static final Class<HoverEvent.ShowEntity> SHOW_ENTITY_TYPE = HoverEvent.ShowEntity.class;
/* 48 */   static final Class<TextColorWrapper> COLOR_WRAPPER_TYPE = TextColorWrapper.class;
/* 49 */   static final Class<TextColor> COLOR_TYPE = TextColor.class;
/* 50 */   static final Class<TextDecoration> TEXT_DECORATION_TYPE = TextDecoration.class;
/* 51 */   static final Class<BlockNBTComponent.Pos> BLOCK_NBT_POS_TYPE = BlockNBTComponent.Pos.class;
/*    */   
/*    */   private final boolean downsampleColors;
/*    */   private final LegacyHoverEventSerializer legacyHoverSerializer;
/*    */   private final boolean emitLegacyHover;
/*    */   
/*    */   SerializerFactory(boolean downsampleColors, @Nullable LegacyHoverEventSerializer legacyHoverSerializer, boolean emitLegacyHover) {
/* 58 */     this.downsampleColors = downsampleColors;
/* 59 */     this.legacyHoverSerializer = legacyHoverSerializer;
/* 60 */     this.emitLegacyHover = emitLegacyHover;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
/* 66 */     Class<? super T> rawType = type.getRawType();
/* 67 */     if (COMPONENT_TYPE.isAssignableFrom(rawType))
/* 68 */       return (TypeAdapter)ComponentSerializerImpl.create(gson); 
/* 69 */     if (KEY_TYPE.isAssignableFrom(rawType))
/* 70 */       return (TypeAdapter)KeySerializer.INSTANCE; 
/* 71 */     if (STYLE_TYPE.isAssignableFrom(rawType))
/* 72 */       return (TypeAdapter)StyleSerializer.create(this.legacyHoverSerializer, this.emitLegacyHover, gson); 
/* 73 */     if (CLICK_ACTION_TYPE.isAssignableFrom(rawType))
/* 74 */       return (TypeAdapter)ClickEventActionSerializer.INSTANCE; 
/* 75 */     if (HOVER_ACTION_TYPE.isAssignableFrom(rawType))
/* 76 */       return (TypeAdapter)HoverEventActionSerializer.INSTANCE; 
/* 77 */     if (SHOW_ITEM_TYPE.isAssignableFrom(rawType))
/* 78 */       return (TypeAdapter)ShowItemSerializer.create(gson); 
/* 79 */     if (SHOW_ENTITY_TYPE.isAssignableFrom(rawType))
/* 80 */       return (TypeAdapter)ShowEntitySerializer.create(gson); 
/* 81 */     if (COLOR_WRAPPER_TYPE.isAssignableFrom(rawType))
/* 82 */       return TextColorWrapper.Serializer.INSTANCE; 
/* 83 */     if (COLOR_TYPE.isAssignableFrom(rawType))
/* 84 */       return this.downsampleColors ? (TypeAdapter)TextColorSerializer.DOWNSAMPLE_COLOR : (TypeAdapter)TextColorSerializer.INSTANCE; 
/* 85 */     if (TEXT_DECORATION_TYPE.isAssignableFrom(rawType))
/* 86 */       return (TypeAdapter)TextDecorationSerializer.INSTANCE; 
/* 87 */     if (BLOCK_NBT_POS_TYPE.isAssignableFrom(rawType)) {
/* 88 */       return (TypeAdapter)BlockNBTComponentPosSerializer.INSTANCE;
/*    */     }
/* 90 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\serializer\gson\SerializerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
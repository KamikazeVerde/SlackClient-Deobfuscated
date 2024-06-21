/*    */ package com.viaversion.viaversion.libs.kyori.adventure.text.format;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
/*    */ import java.util.EnumMap;
/*    */ import java.util.Map;
/*    */ import org.jetbrains.annotations.ApiStatus.NonExtendable;
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
/*    */ @NonExtendable
/*    */ public interface StyleGetter
/*    */ {
/*    */   @Nullable
/*    */   Key font();
/*    */   
/*    */   @Nullable
/*    */   TextColor color();
/*    */   
/*    */   default boolean hasDecoration(@NotNull TextDecoration decoration) {
/* 70 */     return (decoration(decoration) == TextDecoration.State.TRUE);
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
/*    */   TextDecoration.State decoration(@NotNull TextDecoration paramTextDecoration);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   default Map<TextDecoration, TextDecoration.State> decorations() {
/* 92 */     Map<TextDecoration, TextDecoration.State> decorations = new EnumMap<>(TextDecoration.class);
/* 93 */     for (int i = 0, length = DecorationMap.DECORATIONS.length; i < length; i++) {
/* 94 */       TextDecoration decoration = DecorationMap.DECORATIONS[i];
/* 95 */       TextDecoration.State value = decoration(decoration);
/* 96 */       decorations.put(decoration, value);
/*    */     } 
/* 98 */     return decorations;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   ClickEvent clickEvent();
/*    */   
/*    */   @Nullable
/*    */   HoverEvent<?> hoverEvent();
/*    */   
/*    */   @Nullable
/*    */   String insertion();
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\format\StyleGetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
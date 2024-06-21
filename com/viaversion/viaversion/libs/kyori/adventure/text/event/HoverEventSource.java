/*    */ package com.viaversion.viaversion.libs.kyori.adventure.text.event;
/*    */ 
/*    */ import java.util.function.UnaryOperator;
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
/*    */ public interface HoverEventSource<V>
/*    */ {
/*    */   @Nullable
/*    */   static <V> HoverEvent<V> unbox(@Nullable HoverEventSource<V> source) {
/* 46 */     return (source != null) ? source.asHoverEvent() : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   default HoverEvent<V> asHoverEvent() {
/* 56 */     return asHoverEvent(UnaryOperator.identity());
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   HoverEvent<V> asHoverEvent(@NotNull UnaryOperator<V> paramUnaryOperator);
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\event\HoverEventSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
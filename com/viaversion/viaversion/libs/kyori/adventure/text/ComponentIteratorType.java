/*    */ package com.viaversion.viaversion.libs.kyori.adventure.text;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
/*    */ import java.util.Deque;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import org.jetbrains.annotations.ApiStatus.NonExtendable;
/*    */ import org.jetbrains.annotations.NotNull;
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
/*    */ @FunctionalInterface
/*    */ @NonExtendable
/*    */ public interface ComponentIteratorType
/*    */ {
/*    */   public static final ComponentIteratorType DEPTH_FIRST;
/*    */   public static final ComponentIteratorType BREADTH_FIRST;
/*    */   
/*    */   static {
/* 49 */     DEPTH_FIRST = ((component, deque, flags) -> {
/*    */         if (flags.contains(ComponentIteratorFlag.INCLUDE_TRANSLATABLE_COMPONENT_ARGUMENTS) && component instanceof TranslatableComponent) {
/*    */           TranslatableComponent translatable = (TranslatableComponent)component;
/*    */ 
/*    */           
/*    */           List<Component> args = translatable.args();
/*    */           
/*    */           for (int j = args.size() - 1; j >= 0; j--) {
/*    */             deque.addFirst(args.get(j));
/*    */           }
/*    */         } 
/*    */         
/*    */         HoverEvent<?> hoverEvent = component.hoverEvent();
/*    */         
/*    */         if (hoverEvent != null) {
/*    */           HoverEvent.Action<?> action = hoverEvent.action();
/*    */           
/*    */           if (flags.contains(ComponentIteratorFlag.INCLUDE_HOVER_SHOW_ENTITY_NAME) && action == HoverEvent.Action.SHOW_ENTITY) {
/*    */             deque.addFirst(((HoverEvent.ShowEntity)hoverEvent.value()).name());
/*    */           } else if (flags.contains(ComponentIteratorFlag.INCLUDE_HOVER_SHOW_TEXT_COMPONENT) && action == HoverEvent.Action.SHOW_TEXT) {
/*    */             deque.addFirst((Component)hoverEvent.value());
/*    */           } 
/*    */         } 
/*    */         
/*    */         List<Component> children = component.children();
/*    */         
/*    */         for (int i = children.size() - 1; i >= 0; i--) {
/*    */           deque.addFirst(children.get(i));
/*    */         }
/*    */       });
/*    */     
/* 80 */     BREADTH_FIRST = ((component, deque, flags) -> {
/*    */         if (flags.contains(ComponentIteratorFlag.INCLUDE_TRANSLATABLE_COMPONENT_ARGUMENTS) && component instanceof TranslatableComponent)
/*    */           deque.addAll(((TranslatableComponent)component).args()); 
/*    */         HoverEvent<?> hoverEvent = component.hoverEvent();
/*    */         if (hoverEvent != null) {
/*    */           HoverEvent.Action<?> action = hoverEvent.action();
/*    */           if (flags.contains(ComponentIteratorFlag.INCLUDE_HOVER_SHOW_ENTITY_NAME) && action == HoverEvent.Action.SHOW_ENTITY) {
/*    */             deque.addLast(((HoverEvent.ShowEntity)hoverEvent.value()).name());
/*    */           } else if (flags.contains(ComponentIteratorFlag.INCLUDE_HOVER_SHOW_TEXT_COMPONENT) && action == HoverEvent.Action.SHOW_TEXT) {
/*    */             deque.addLast((Component)hoverEvent.value());
/*    */           } 
/*    */         } 
/*    */         deque.addAll(component.children());
/*    */       });
/*    */   }
/*    */   
/*    */   void populate(@NotNull Component paramComponent, @NotNull Deque<Component> paramDeque, @NotNull Set<ComponentIteratorFlag> paramSet);
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\ComponentIteratorType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
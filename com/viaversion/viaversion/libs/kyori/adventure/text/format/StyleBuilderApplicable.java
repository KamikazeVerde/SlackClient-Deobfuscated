/*    */ package com.viaversion.viaversion.libs.kyori.adventure.text.format;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilderApplicable;
/*    */ import org.jetbrains.annotations.Contract;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface StyleBuilderApplicable
/*    */   extends ComponentBuilderApplicable
/*    */ {
/*    */   @Contract(mutates = "param")
/*    */   void styleApply(Style.Builder paramBuilder);
/*    */   
/*    */   default void componentBuilderApply(@NotNull ComponentBuilder<?, ?> component) {
/* 50 */     component.style(this::styleApply);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\format\StyleBuilderApplicable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
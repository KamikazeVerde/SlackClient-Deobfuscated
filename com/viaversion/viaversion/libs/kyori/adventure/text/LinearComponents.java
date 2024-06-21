/*    */ package com.viaversion.viaversion.libs.kyori.adventure.text;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleBuilderApplicable;
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
/*    */ public final class LinearComponents
/*    */ {
/*    */   @NotNull
/*    */   public static Component linear(@NotNull ComponentBuilderApplicable... applicables) {
/* 55 */     int length = applicables.length;
/* 56 */     if (length == 0) return Component.empty(); 
/* 57 */     if (length == 1) {
/* 58 */       ComponentBuilderApplicable ap0 = applicables[0];
/* 59 */       if (ap0 instanceof ComponentLike) {
/* 60 */         return ((ComponentLike)ap0).asComponent();
/*    */       }
/* 62 */       throw nothingComponentLike();
/*    */     } 
/* 64 */     TextComponentImpl.BuilderImpl builder = new TextComponentImpl.BuilderImpl();
/* 65 */     Style.Builder style = null;
/* 66 */     for (int i = 0; i < length; i++) {
/* 67 */       ComponentBuilderApplicable applicable = applicables[i];
/* 68 */       if (applicable instanceof StyleBuilderApplicable) {
/* 69 */         if (style == null) {
/* 70 */           style = Style.style();
/*    */         }
/* 72 */         style.apply((StyleBuilderApplicable)applicable);
/* 73 */       } else if (style != null && applicable instanceof ComponentLike) {
/* 74 */         builder.applicableApply(((ComponentLike)applicable).asComponent().style(style));
/*    */       } else {
/* 76 */         builder.applicableApply(applicable);
/*    */       } 
/*    */     } 
/* 79 */     int size = builder.children.size();
/* 80 */     if (size == 0)
/* 81 */       throw nothingComponentLike(); 
/* 82 */     if (size == 1 && !builder.hasStyle()) {
/* 83 */       return builder.children.get(0);
/*    */     }
/* 85 */     return builder.build();
/*    */   }
/*    */ 
/*    */   
/*    */   private static IllegalStateException nothingComponentLike() {
/* 90 */     return new IllegalStateException("Cannot build component linearly - nothing component-like was given");
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\text\LinearComponents.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
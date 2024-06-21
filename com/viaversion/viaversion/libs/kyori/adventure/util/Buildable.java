/*    */ package com.viaversion.viaversion.libs.kyori.adventure.util;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.builder.AbstractBuilder;
/*    */ import java.util.function.Consumer;
/*    */ import org.jetbrains.annotations.Contract;
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
/*    */ public interface Buildable<R, B extends Buildable.Builder<R>>
/*    */ {
/*    */   @Deprecated
/*    */   @Contract(mutates = "param1")
/*    */   @NotNull
/*    */   static <R extends Buildable<R, B>, B extends Builder<R>> R configureAndBuild(@NotNull B builder, @Nullable Consumer<? super B> consumer) {
/* 54 */     return (R)AbstractBuilder.configureAndBuild((AbstractBuilder)builder, consumer);
/*    */   }
/*    */   
/*    */   @Contract(value = "-> new", pure = true)
/*    */   @NotNull
/*    */   B toBuilder();
/*    */   
/*    */   @Deprecated
/*    */   public static interface Builder<R> extends AbstractBuilder<R> {
/*    */     @Contract(value = "-> new", pure = true)
/*    */     @NotNull
/*    */     R build();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventur\\util\Buildable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.viaversion.viaversion.libs.kyori.adventure.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Objects;
/*    */ import java.util.Spliterator;
/*    */ import java.util.function.Supplier;
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
/*    */ public final class ForwardingIterator<T>
/*    */   implements Iterable<T>
/*    */ {
/*    */   private final Supplier<Iterator<T>> iterator;
/*    */   private final Supplier<Spliterator<T>> spliterator;
/*    */   
/*    */   public ForwardingIterator(@NotNull Supplier<Iterator<T>> iterator, @NotNull Supplier<Spliterator<T>> spliterator) {
/* 50 */     this.iterator = Objects.<Supplier<Iterator<T>>>requireNonNull(iterator, "iterator");
/* 51 */     this.spliterator = Objects.<Supplier<Spliterator<T>>>requireNonNull(spliterator, "spliterator");
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Iterator<T> iterator() {
/* 56 */     return this.iterator.get();
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Spliterator<T> spliterator() {
/* 61 */     return this.spliterator.get();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventur\\util\ForwardingIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
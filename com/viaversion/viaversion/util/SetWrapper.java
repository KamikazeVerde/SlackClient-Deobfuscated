/*    */ package com.viaversion.viaversion.util;
/*    */ 
/*    */ import com.google.common.collect.ForwardingSet;
/*    */ import java.util.Collection;
/*    */ import java.util.Set;
/*    */ import java.util.function.Consumer;
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
/*    */ public class SetWrapper<E>
/*    */   extends ForwardingSet<E>
/*    */ {
/*    */   private final Set<E> set;
/*    */   private final Consumer<E> addListener;
/*    */   
/*    */   public SetWrapper(Set<E> set, Consumer<E> addListener) {
/* 32 */     this.set = set;
/* 33 */     this.addListener = addListener;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean add(E element) {
/* 38 */     this.addListener.accept(element);
/*    */     
/* 40 */     return super.add(element);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean addAll(Collection<? extends E> collection) {
/* 45 */     for (E element : collection) {
/* 46 */       this.addListener.accept(element);
/*    */     }
/*    */     
/* 49 */     return super.addAll(collection);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Set<E> delegate() {
/* 54 */     return originalSet();
/*    */   }
/*    */   
/*    */   public Set<E> originalSet() {
/* 58 */     return this.set;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\util\SetWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
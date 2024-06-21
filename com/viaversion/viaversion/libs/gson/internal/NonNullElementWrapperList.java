/*    */ package com.viaversion.viaversion.libs.gson.internal;
/*    */ 
/*    */ import java.util.AbstractList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Objects;
/*    */ import java.util.RandomAccess;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NonNullElementWrapperList<E>
/*    */   extends AbstractList<E>
/*    */   implements RandomAccess
/*    */ {
/*    */   private final ArrayList<E> delegate;
/*    */   
/*    */   public NonNullElementWrapperList(ArrayList<E> delegate) {
/* 21 */     this.delegate = Objects.<ArrayList<E>>requireNonNull(delegate);
/*    */   }
/*    */   
/*    */   public E get(int index) {
/* 25 */     return this.delegate.get(index);
/*    */   }
/*    */   
/*    */   public int size() {
/* 29 */     return this.delegate.size();
/*    */   }
/*    */   
/*    */   private E nonNull(E element) {
/* 33 */     if (element == null) {
/* 34 */       throw new NullPointerException("Element must be non-null");
/*    */     }
/* 36 */     return element;
/*    */   }
/*    */   
/*    */   public E set(int index, E element) {
/* 40 */     return this.delegate.set(index, nonNull(element));
/*    */   }
/*    */   
/*    */   public void add(int index, E element) {
/* 44 */     this.delegate.add(index, nonNull(element));
/*    */   }
/*    */   
/*    */   public E remove(int index) {
/* 48 */     return this.delegate.remove(index);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void clear() {
/* 54 */     this.delegate.clear();
/*    */   }
/*    */   
/*    */   public boolean remove(Object o) {
/* 58 */     return this.delegate.remove(o);
/*    */   }
/*    */   
/*    */   public boolean removeAll(Collection<?> c) {
/* 62 */     return this.delegate.removeAll(c);
/*    */   }
/*    */   
/*    */   public boolean retainAll(Collection<?> c) {
/* 66 */     return this.delegate.retainAll(c);
/*    */   }
/*    */   
/*    */   public boolean contains(Object o) {
/* 70 */     return this.delegate.contains(o);
/*    */   }
/*    */   
/*    */   public int indexOf(Object o) {
/* 74 */     return this.delegate.indexOf(o);
/*    */   }
/*    */   
/*    */   public int lastIndexOf(Object o) {
/* 78 */     return this.delegate.lastIndexOf(o);
/*    */   }
/*    */   
/*    */   public Object[] toArray() {
/* 82 */     return this.delegate.toArray();
/*    */   }
/*    */   
/*    */   public <T> T[] toArray(T[] a) {
/* 86 */     return this.delegate.toArray(a);
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/* 90 */     return this.delegate.equals(o);
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 94 */     return this.delegate.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\NonNullElementWrapperList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
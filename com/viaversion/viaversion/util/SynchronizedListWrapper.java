/*     */ package com.viaversion.viaversion.util;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SynchronizedListWrapper<E>
/*     */   implements List<E>
/*     */ {
/*     */   private final List<E> list;
/*     */   private final Consumer<E> addHandler;
/*     */   
/*     */   public SynchronizedListWrapper(List<E> inputList, Consumer<E> addHandler) {
/*  39 */     this.list = inputList;
/*  40 */     this.addHandler = addHandler;
/*     */   }
/*     */   
/*     */   public List<E> originalList() {
/*  44 */     return this.list;
/*     */   }
/*     */   
/*     */   private void handleAdd(E o) {
/*  48 */     this.addHandler.accept(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  53 */     synchronized (this) {
/*  54 */       return this.list.size();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  60 */     synchronized (this) {
/*  61 */       return this.list.isEmpty();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/*  67 */     synchronized (this) {
/*  68 */       return this.list.contains(o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<E> iterator() {
/*  75 */     return listIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/*  80 */     synchronized (this) {
/*  81 */       return this.list.toArray();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(E o) {
/*  87 */     synchronized (this) {
/*  88 */       handleAdd(o);
/*  89 */       return this.list.add(o);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/*  95 */     synchronized (this) {
/*  96 */       return this.list.remove(o);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends E> c) {
/* 102 */     synchronized (this) {
/* 103 */       for (E o : c) {
/* 104 */         handleAdd(o);
/*     */       }
/* 106 */       return this.list.addAll(c);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends E> c) {
/* 112 */     synchronized (this) {
/* 113 */       for (E o : c) {
/* 114 */         handleAdd(o);
/*     */       }
/* 116 */       return this.list.addAll(index, c);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 122 */     synchronized (this) {
/* 123 */       this.list.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public E get(int index) {
/* 129 */     synchronized (this) {
/* 130 */       return this.list.get(index);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public E set(int index, E element) {
/* 136 */     synchronized (this) {
/* 137 */       return this.list.set(index, element);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int index, E element) {
/* 143 */     synchronized (this) {
/* 144 */       this.list.add(index, element);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public E remove(int index) {
/* 150 */     synchronized (this) {
/* 151 */       return this.list.remove(index);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(Object o) {
/* 157 */     synchronized (this) {
/* 158 */       return this.list.indexOf(o);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(Object o) {
/* 164 */     synchronized (this) {
/* 165 */       return this.list.lastIndexOf(o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ListIterator<E> listIterator() {
/* 172 */     return this.list.listIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ListIterator<E> listIterator(int index) {
/* 178 */     return this.list.listIterator(index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<E> subList(int fromIndex, int toIndex) {
/* 184 */     synchronized (this) {
/* 185 */       return this.list.subList(fromIndex, toIndex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection<?> c) {
/* 191 */     synchronized (this) {
/* 192 */       return this.list.retainAll(c);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 198 */     synchronized (this) {
/* 199 */       return this.list.removeAll(c);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(Collection<?> c) {
/* 205 */     synchronized (this) {
/* 206 */       return this.list.containsAll(c);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] a) {
/* 212 */     synchronized (this) {
/* 213 */       return this.list.toArray(a);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sort(Comparator<? super E> c) {
/* 219 */     synchronized (this) {
/* 220 */       this.list.sort(c);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(Consumer<? super E> consumer) {
/* 226 */     synchronized (this) {
/* 227 */       this.list.forEach(consumer);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeIf(Predicate<? super E> filter) {
/* 233 */     synchronized (this) {
/* 234 */       return this.list.removeIf(filter);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 240 */     if (this == o) return true; 
/* 241 */     synchronized (this) {
/* 242 */       return this.list.equals(o);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 248 */     synchronized (this) {
/* 249 */       return this.list.hashCode();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 255 */     synchronized (this) {
/* 256 */       return this.list.toString();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\util\SynchronizedListWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
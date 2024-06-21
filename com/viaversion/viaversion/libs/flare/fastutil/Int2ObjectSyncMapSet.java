/*     */ package com.viaversion.viaversion.libs.flare.fastutil;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Spliterator;
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
/*     */ final class Int2ObjectSyncMapSet
/*     */   extends AbstractIntSet
/*     */   implements IntSet
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final Int2ObjectSyncMap<Boolean> map;
/*     */   private final IntSet set;
/*     */   
/*     */   Int2ObjectSyncMapSet(Int2ObjectSyncMap<Boolean> map) {
/*  41 */     this.map = map;
/*  42 */     this.set = map.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  47 */     this.map.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  52 */     return this.map.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  57 */     return this.map.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(int key) {
/*  62 */     return this.map.containsKey(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(int key) {
/*  67 */     return (this.map.remove(key) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(int key) {
/*  72 */     return (this.map.put(key, Boolean.TRUE) == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(IntCollection collection) {
/*  77 */     return this.set.containsAll(collection);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(IntCollection collection) {
/*  82 */     return this.set.removeAll(collection);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(IntCollection collection) {
/*  87 */     return this.set.retainAll(collection);
/*     */   }
/*     */ 
/*     */   
/*     */   public IntIterator iterator() {
/*  92 */     return this.set.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public IntSpliterator spliterator() {
/*  97 */     return this.set.spliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] toArray(int[] original) {
/* 102 */     return this.set.toArray(original);
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] toIntArray() {
/* 107 */     return this.set.toIntArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 112 */     return (other == this || this.set.equals(other));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 117 */     return this.set.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 122 */     return this.set.hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\flare\fastutil\Int2ObjectSyncMapSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viaversion.libs.flare.fastutil;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.IntFunction;
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
/*     */ public interface Int2ObjectSyncMap<V>
/*     */   extends Int2ObjectMap<V>
/*     */ {
/*     */   static <V> Int2ObjectSyncMap<V> hashmap() {
/*  79 */     return of(com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap::new, 16);
/*     */   }
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
/*     */   static <V> Int2ObjectSyncMap<V> hashmap(int initialCapacity) {
/*  93 */     return of(com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap::new, initialCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static IntSet hashset() {
/* 104 */     return setOf(com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap::new, 16);
/*     */   }
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
/*     */   static IntSet hashset(int initialCapacity) {
/* 117 */     return setOf(com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap::new, initialCapacity);
/*     */   }
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
/*     */   static <V> Int2ObjectSyncMap<V> of(IntFunction<Int2ObjectMap<ExpungingEntry<V>>> function, int initialCapacity) {
/* 131 */     return new Int2ObjectSyncMapImpl<>(function, initialCapacity);
/*     */   }
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
/*     */   static IntSet setOf(IntFunction<Int2ObjectMap<ExpungingEntry<Boolean>>> function, int initialCapacity) {
/* 144 */     return new Int2ObjectSyncMapSet(new Int2ObjectSyncMapImpl<>(function, initialCapacity));
/*     */   }
/*     */   
/*     */   ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet();
/*     */   
/*     */   int size();
/*     */   
/*     */   void clear();
/*     */   
/*     */   public static interface InsertionResult<V> {
/*     */     byte operation();
/*     */     
/*     */     V previous();
/*     */     
/*     */     V current();
/*     */   }
/*     */   
/*     */   public static interface ExpungingEntry<V> {
/*     */     boolean exists();
/*     */     
/*     */     V get();
/*     */     
/*     */     V getOr(V param1V);
/*     */     
/*     */     Int2ObjectSyncMap.InsertionResult<V> setIfAbsent(V param1V);
/*     */     
/*     */     Int2ObjectSyncMap.InsertionResult<V> computeIfAbsent(int param1Int, IntFunction<? extends V> param1IntFunction);
/*     */     
/*     */     Int2ObjectSyncMap.InsertionResult<V> computeIfAbsentPrimitive(int param1Int, Int2ObjectFunction<? extends V> param1Int2ObjectFunction);
/*     */     
/*     */     Int2ObjectSyncMap.InsertionResult<V> computeIfPresent(int param1Int, BiFunction<? super Integer, ? super V, ? extends V> param1BiFunction);
/*     */     
/*     */     Int2ObjectSyncMap.InsertionResult<V> compute(int param1Int, BiFunction<? super Integer, ? super V, ? extends V> param1BiFunction);
/*     */     
/*     */     void set(V param1V);
/*     */     
/*     */     boolean replace(Object param1Object, V param1V);
/*     */     
/*     */     V clear();
/*     */     
/*     */     boolean trySet(V param1V);
/*     */     
/*     */     V tryReplace(V param1V);
/*     */     
/*     */     boolean tryExpunge();
/*     */     
/*     */     boolean tryUnexpungeAndSet(V param1V);
/*     */     
/*     */     boolean tryUnexpungeAndCompute(int param1Int, IntFunction<? extends V> param1IntFunction);
/*     */     
/*     */     boolean tryUnexpungeAndComputePrimitive(int param1Int, Int2ObjectFunction<? extends V> param1Int2ObjectFunction);
/*     */     
/*     */     boolean tryUnexpungeAndCompute(int param1Int, BiFunction<? super Integer, ? super V, ? extends V> param1BiFunction);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\flare\fastutil\Int2ObjectSyncMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
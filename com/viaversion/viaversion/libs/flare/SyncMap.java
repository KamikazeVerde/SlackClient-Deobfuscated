/*     */ package com.viaversion.viaversion.libs.flare;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
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
/*     */ public interface SyncMap<K, V>
/*     */   extends ConcurrentMap<K, V>
/*     */ {
/*     */   static <K, V> SyncMap<K, V> hashmap() {
/*  79 */     return of(java.util.HashMap::new, 16);
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
/*     */   
/*     */   static <K, V> SyncMap<K, V> hashmap(int initialCapacity) {
/*  94 */     return of(java.util.HashMap::new, initialCapacity);
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
/*     */   static <K> Set<K> hashset() {
/* 106 */     return setOf(java.util.HashMap::new, 16);
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
/*     */   static <K> Set<K> hashset(int initialCapacity) {
/* 120 */     return setOf(java.util.HashMap::new, initialCapacity);
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
/*     */   
/*     */   static <K, V> SyncMap<K, V> of(IntFunction<Map<K, ExpungingEntry<V>>> function, int initialCapacity) {
/* 135 */     return new SyncMapImpl<>(function, initialCapacity);
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
/*     */   static <K> Set<K> setOf(IntFunction<Map<K, ExpungingEntry<Boolean>>> function, int initialCapacity) {
/* 149 */     return Collections.newSetFromMap(new SyncMapImpl<>(function, initialCapacity));
/*     */   }
/*     */   
/*     */   Set<Map.Entry<K, V>> entrySet();
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
/*     */     SyncMap.InsertionResult<V> setIfAbsent(V param1V);
/*     */     
/*     */     <K> SyncMap.InsertionResult<V> computeIfAbsent(K param1K, Function<? super K, ? extends V> param1Function);
/*     */     
/*     */     <K> SyncMap.InsertionResult<V> computeIfPresent(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction);
/*     */     
/*     */     <K> SyncMap.InsertionResult<V> compute(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction);
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
/*     */     <K> boolean tryUnexpungeAndCompute(K param1K, Function<? super K, ? extends V> param1Function);
/*     */     
/*     */     <K> boolean tryUnexpungeAndCompute(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\flare\SyncMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
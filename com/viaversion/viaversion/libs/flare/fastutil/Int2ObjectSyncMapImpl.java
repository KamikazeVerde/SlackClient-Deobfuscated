/*     */ package com.viaversion.viaversion.libs.flare.fastutil;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMaps;
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
/*     */ import java.util.function.BiConsumer;
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
/*     */ final class Int2ObjectSyncMapImpl<V>
/*     */   extends AbstractInt2ObjectMap<V>
/*     */   implements Int2ObjectSyncMap<V>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  55 */   private final transient Object lock = new Object();
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile transient Int2ObjectMap<Int2ObjectSyncMap.ExpungingEntry<V>> read;
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile transient boolean amended;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Int2ObjectMap<Int2ObjectSyncMap.ExpungingEntry<V>> dirty;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient int misses;
/*     */ 
/*     */ 
/*     */   
/*     */   private final transient IntFunction<Int2ObjectMap<Int2ObjectSyncMap.ExpungingEntry<V>>> function;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient EntrySetView entrySet;
/*     */ 
/*     */ 
/*     */   
/*     */   Int2ObjectSyncMapImpl(IntFunction<Int2ObjectMap<Int2ObjectSyncMap.ExpungingEntry<V>>> function, int initialCapacity) {
/*  84 */     if (initialCapacity < 0) throw new IllegalArgumentException("Initial capacity must be greater than 0"); 
/*  85 */     this.function = function;
/*  86 */     this.read = function.apply(initialCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  93 */     promote();
/*  94 */     int size = 0;
/*  95 */     for (ObjectIterator<Int2ObjectSyncMap.ExpungingEntry<V>> objectIterator = this.read.values().iterator(); objectIterator.hasNext(); ) { Int2ObjectSyncMap.ExpungingEntry<V> value = objectIterator.next();
/*  96 */       if (value.exists()) size++;  }
/*     */     
/*  98 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 103 */     promote();
/* 104 */     for (ObjectIterator<Int2ObjectSyncMap.ExpungingEntry<V>> objectIterator = this.read.values().iterator(); objectIterator.hasNext(); ) { Int2ObjectSyncMap.ExpungingEntry<V> value = objectIterator.next();
/* 105 */       if (value.exists()) return false;  }
/*     */     
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object value) {
/* 112 */     for (ObjectIterator<Int2ObjectMap.Entry<V>> objectIterator = int2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry<V> entry = objectIterator.next();
/* 113 */       if (Objects.equals(entry.getValue(), value)) return true;  }
/*     */     
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(int key) {
/* 120 */     Int2ObjectSyncMap.ExpungingEntry<V> entry = getEntry(key);
/* 121 */     return (entry != null && entry.exists());
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(int key) {
/* 126 */     Int2ObjectSyncMap.ExpungingEntry<V> entry = getEntry(key);
/* 127 */     return (entry != null) ? entry.get() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public V getOrDefault(int key, V defaultValue) {
/* 132 */     Objects.requireNonNull(defaultValue, "defaultValue");
/* 133 */     Int2ObjectSyncMap.ExpungingEntry<V> entry = getEntry(key);
/* 134 */     return (entry != null) ? entry.getOr(defaultValue) : defaultValue;
/*     */   }
/*     */   
/*     */   public Int2ObjectSyncMap.ExpungingEntry<V> getEntry(int key) {
/* 138 */     Int2ObjectSyncMap.ExpungingEntry<V> entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.read.get(key);
/* 139 */     if (entry == null && this.amended) {
/* 140 */       synchronized (this.lock) {
/* 141 */         if ((entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.read.get(key)) == null && this.amended && this.dirty != null) {
/* 142 */           entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.dirty.get(key);
/*     */ 
/*     */ 
/*     */           
/* 146 */           missLocked();
/*     */         } 
/*     */       } 
/*     */     }
/* 150 */     return entry;
/*     */   }
/*     */ 
/*     */   
/*     */   public V computeIfAbsent(int key, IntFunction<? extends V> mappingFunction) {
/* 155 */     Objects.requireNonNull(mappingFunction, "mappingFunction");
/* 156 */     Int2ObjectSyncMap.ExpungingEntry<V> entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.read.get(key);
/* 157 */     Int2ObjectSyncMap.InsertionResult<V> result = (entry != null) ? entry.computeIfAbsent(key, mappingFunction) : null;
/* 158 */     if (result != null && result.operation() == 1) return result.current(); 
/* 159 */     synchronized (this.lock) {
/* 160 */       if ((entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.read.get(key)) != null) {
/*     */ 
/*     */         
/* 163 */         if (entry.tryUnexpungeAndCompute(key, mappingFunction)) {
/* 164 */           if (entry.exists()) this.dirty.put(key, entry); 
/* 165 */           return entry.get();
/*     */         } 
/* 167 */         result = entry.computeIfAbsent(key, mappingFunction);
/*     */       }
/* 169 */       else if (this.dirty != null && (entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.dirty.get(key)) != null) {
/* 170 */         result = entry.computeIfAbsent(key, mappingFunction);
/* 171 */         if (result.current() == null) this.dirty.remove(key);
/*     */ 
/*     */ 
/*     */         
/* 175 */         missLocked();
/*     */       } else {
/* 177 */         if (!this.amended) {
/*     */ 
/*     */           
/* 180 */           dirtyLocked();
/* 181 */           this.amended = true;
/*     */         } 
/* 183 */         V computed = mappingFunction.apply(key);
/* 184 */         if (computed != null) this.dirty.put(key, new ExpungingEntryImpl<>(computed)); 
/* 185 */         return computed;
/*     */       } 
/*     */     } 
/* 188 */     return result.current();
/*     */   }
/*     */ 
/*     */   
/*     */   public V computeIfAbsent(int key, Int2ObjectFunction<? extends V> mappingFunction) {
/* 193 */     Objects.requireNonNull(mappingFunction, "mappingFunction");
/* 194 */     Int2ObjectSyncMap.ExpungingEntry<V> entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.read.get(key);
/* 195 */     Int2ObjectSyncMap.InsertionResult<V> result = (entry != null) ? entry.computeIfAbsentPrimitive(key, mappingFunction) : null;
/* 196 */     if (result != null && result.operation() == 1) return result.current(); 
/* 197 */     synchronized (this.lock) {
/* 198 */       if ((entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.read.get(key)) != null) {
/*     */ 
/*     */         
/* 201 */         if (entry.tryUnexpungeAndComputePrimitive(key, mappingFunction)) {
/* 202 */           if (entry.exists()) this.dirty.put(key, entry); 
/* 203 */           return entry.get();
/*     */         } 
/* 205 */         result = entry.computeIfAbsentPrimitive(key, mappingFunction);
/*     */       }
/* 207 */       else if (this.dirty != null && (entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.dirty.get(key)) != null) {
/* 208 */         result = entry.computeIfAbsentPrimitive(key, mappingFunction);
/* 209 */         if (result.current() == null) this.dirty.remove(key);
/*     */ 
/*     */ 
/*     */         
/* 213 */         missLocked();
/*     */       } else {
/* 215 */         if (!this.amended) {
/*     */ 
/*     */           
/* 218 */           dirtyLocked();
/* 219 */           this.amended = true;
/*     */         } 
/* 221 */         V computed = (V)mappingFunction.get(key);
/* 222 */         if (computed != null) this.dirty.put(key, new ExpungingEntryImpl<>(computed)); 
/* 223 */         return computed;
/*     */       } 
/*     */     } 
/* 226 */     return result.current();
/*     */   }
/*     */ 
/*     */   
/*     */   public V computeIfPresent(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 231 */     Objects.requireNonNull(remappingFunction, "remappingFunction");
/* 232 */     Int2ObjectSyncMap.ExpungingEntry<V> entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.read.get(key);
/* 233 */     Int2ObjectSyncMap.InsertionResult<V> result = (entry != null) ? entry.computeIfPresent(key, remappingFunction) : null;
/* 234 */     if (result != null && result.operation() == 1) return result.current(); 
/* 235 */     synchronized (this.lock) {
/* 236 */       if ((entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.read.get(key)) != null) {
/* 237 */         result = entry.computeIfPresent(key, remappingFunction);
/* 238 */       } else if (this.dirty != null && (entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.dirty.get(key)) != null) {
/* 239 */         result = entry.computeIfPresent(key, remappingFunction);
/* 240 */         if (result.current() == null) this.dirty.remove(key);
/*     */ 
/*     */ 
/*     */         
/* 244 */         missLocked();
/*     */       } 
/*     */     } 
/* 247 */     return (result != null) ? result.current() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public V compute(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 252 */     Objects.requireNonNull(remappingFunction, "remappingFunction");
/* 253 */     Int2ObjectSyncMap.ExpungingEntry<V> entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.read.get(key);
/* 254 */     Int2ObjectSyncMap.InsertionResult<V> result = (entry != null) ? entry.compute(key, remappingFunction) : null;
/* 255 */     if (result != null && result.operation() == 1) return result.current(); 
/* 256 */     synchronized (this.lock) {
/* 257 */       if ((entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.read.get(key)) != null) {
/*     */ 
/*     */         
/* 260 */         if (entry.tryUnexpungeAndCompute(key, remappingFunction)) {
/* 261 */           if (entry.exists()) this.dirty.put(key, entry); 
/* 262 */           return entry.get();
/*     */         } 
/* 264 */         result = entry.compute(key, remappingFunction);
/*     */       }
/* 266 */       else if (this.dirty != null && (entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.dirty.get(key)) != null) {
/* 267 */         result = entry.compute(key, remappingFunction);
/* 268 */         if (result.current() == null) this.dirty.remove(key);
/*     */ 
/*     */ 
/*     */         
/* 272 */         missLocked();
/*     */       } else {
/* 274 */         if (!this.amended) {
/*     */ 
/*     */           
/* 277 */           dirtyLocked();
/* 278 */           this.amended = true;
/*     */         } 
/* 280 */         V computed = remappingFunction.apply(Integer.valueOf(key), null);
/* 281 */         if (computed != null) this.dirty.put(key, new ExpungingEntryImpl<>(computed)); 
/* 282 */         return computed;
/*     */       } 
/*     */     } 
/* 285 */     return result.current();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V putIfAbsent(int key, V value) {
/* 291 */     Objects.requireNonNull(value, "value");
/* 292 */     Int2ObjectSyncMap.ExpungingEntry<V> entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.read.get(key);
/* 293 */     Int2ObjectSyncMap.InsertionResult<V> result = (entry != null) ? entry.setIfAbsent(value) : null;
/* 294 */     if (result != null && result.operation() == 1) return result.previous(); 
/* 295 */     synchronized (this.lock) {
/* 296 */       if ((entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.read.get(key)) != null) {
/*     */ 
/*     */ 
/*     */         
/* 300 */         if (entry.tryUnexpungeAndSet(value)) {
/* 301 */           this.dirty.put(key, entry);
/* 302 */           return null;
/*     */         } 
/* 304 */         result = entry.setIfAbsent(value);
/*     */       }
/* 306 */       else if (this.dirty != null && (entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.dirty.get(key)) != null) {
/* 307 */         result = entry.setIfAbsent(value);
/*     */ 
/*     */ 
/*     */         
/* 311 */         missLocked();
/*     */       } else {
/* 313 */         if (!this.amended) {
/*     */ 
/*     */           
/* 316 */           dirtyLocked();
/* 317 */           this.amended = true;
/*     */         } 
/* 319 */         this.dirty.put(key, new ExpungingEntryImpl<>(value));
/* 320 */         return null;
/*     */       } 
/*     */     } 
/* 323 */     return result.previous();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V put(int key, V value) {
/* 329 */     Objects.requireNonNull(value, "value");
/* 330 */     Int2ObjectSyncMap.ExpungingEntry<V> entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.read.get(key);
/* 331 */     V previous = (entry != null) ? entry.get() : null;
/* 332 */     if (entry != null && entry.trySet(value)) return previous; 
/* 333 */     synchronized (this.lock) {
/* 334 */       if ((entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.read.get(key)) != null) {
/* 335 */         previous = entry.get();
/*     */ 
/*     */         
/* 338 */         if (entry.tryUnexpungeAndSet(value)) {
/* 339 */           this.dirty.put(key, entry);
/*     */         } else {
/* 341 */           entry.set(value);
/*     */         } 
/* 343 */       } else if (this.dirty != null && (entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.dirty.get(key)) != null) {
/* 344 */         previous = entry.get();
/* 345 */         entry.set(value);
/*     */       } else {
/* 347 */         if (!this.amended) {
/*     */ 
/*     */           
/* 350 */           dirtyLocked();
/* 351 */           this.amended = true;
/*     */         } 
/* 353 */         this.dirty.put(key, new ExpungingEntryImpl<>(value));
/* 354 */         return null;
/*     */       } 
/*     */     } 
/* 357 */     return previous;
/*     */   }
/*     */ 
/*     */   
/*     */   public V remove(int key) {
/* 362 */     Int2ObjectSyncMap.ExpungingEntry<V> entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.read.get(key);
/* 363 */     if (entry == null && this.amended) {
/* 364 */       synchronized (this.lock) {
/* 365 */         if ((entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.read.get(key)) == null && this.amended && this.dirty != null) {
/* 366 */           entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.dirty.remove(key);
/*     */ 
/*     */ 
/*     */           
/* 370 */           missLocked();
/*     */         } 
/*     */       } 
/*     */     }
/* 374 */     return (entry != null) ? entry.clear() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(int key, Object value) {
/* 379 */     Objects.requireNonNull(value, "value");
/* 380 */     Int2ObjectSyncMap.ExpungingEntry<V> entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.read.get(key);
/* 381 */     if (entry == null && this.amended) {
/* 382 */       synchronized (this.lock) {
/* 383 */         if ((entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.read.get(key)) == null && this.amended && this.dirty != null) {
/* 384 */           boolean present = ((entry = (Int2ObjectSyncMap.ExpungingEntry<V>)this.dirty.get(key)) != null && entry.replace(value, null));
/* 385 */           if (present) this.dirty.remove(key);
/*     */ 
/*     */ 
/*     */           
/* 389 */           missLocked();
/* 390 */           return present;
/*     */         } 
/*     */       } 
/*     */     }
/* 394 */     return (entry != null && entry.replace(value, null));
/*     */   }
/*     */ 
/*     */   
/*     */   public V replace(int key, V value) {
/* 399 */     Objects.requireNonNull(value, "value");
/* 400 */     Int2ObjectSyncMap.ExpungingEntry<V> entry = getEntry(key);
/* 401 */     return (entry != null) ? entry.tryReplace(value) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean replace(int key, V oldValue, V newValue) {
/* 406 */     Objects.requireNonNull(oldValue, "oldValue");
/* 407 */     Objects.requireNonNull(newValue, "newValue");
/* 408 */     Int2ObjectSyncMap.ExpungingEntry<V> entry = getEntry(key);
/* 409 */     return (entry != null && entry.replace(oldValue, newValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEach(BiConsumer<? super Integer, ? super V> action) {
/* 416 */     Objects.requireNonNull(action, "action");
/* 417 */     promote();
/*     */     
/* 419 */     for (ObjectIterator<Int2ObjectMap.Entry<Int2ObjectSyncMap.ExpungingEntry<V>>> objectIterator = this.read.int2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry<Int2ObjectSyncMap.ExpungingEntry<V>> that = objectIterator.next(); V value;
/* 420 */       if ((value = ((Int2ObjectSyncMap.ExpungingEntry<V>)that.getValue()).get()) != null) {
/* 421 */         action.accept(Integer.valueOf(that.getIntKey()), value);
/*     */       } }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Integer, ? extends V> map) {
/* 428 */     Objects.requireNonNull(map, "map");
/* 429 */     for (Map.Entry<? extends Integer, ? extends V> entry : map.entrySet()) {
/* 430 */       put(((Integer)entry.getKey()).intValue(), entry.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void replaceAll(BiFunction<? super Integer, ? super V, ? extends V> function) {
/* 436 */     Objects.requireNonNull(function, "function");
/* 437 */     promote();
/*     */     
/* 439 */     for (ObjectIterator<Int2ObjectMap.Entry<Int2ObjectSyncMap.ExpungingEntry<V>>> objectIterator = this.read.int2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry<Int2ObjectSyncMap.ExpungingEntry<V>> that = objectIterator.next(); Int2ObjectSyncMap.ExpungingEntry<V> entry; V value;
/* 440 */       if ((value = (entry = (Int2ObjectSyncMap.ExpungingEntry<V>)that.getValue()).get()) != null) {
/* 441 */         entry.tryReplace(function.apply(Integer.valueOf(that.getIntKey()), value));
/*     */       } }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 448 */     synchronized (this.lock) {
/* 449 */       this.read = this.function.apply(this.read.size());
/* 450 */       this.dirty = null;
/* 451 */       this.amended = false;
/* 452 */       this.misses = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
/* 460 */     if (this.entrySet != null) return (ObjectSet<Int2ObjectMap.Entry<V>>)this.entrySet; 
/* 461 */     return (ObjectSet<Int2ObjectMap.Entry<V>>)(this.entrySet = new EntrySetView());
/*     */   }
/*     */   
/*     */   private void promote() {
/* 465 */     if (this.amended) {
/* 466 */       synchronized (this.lock) {
/* 467 */         if (this.amended) {
/* 468 */           promoteLocked();
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void missLocked() {
/* 475 */     this.misses++;
/* 476 */     if (this.misses < this.dirty.size())
/* 477 */       return;  promoteLocked();
/*     */   }
/*     */   
/*     */   private void promoteLocked() {
/* 481 */     this.read = this.dirty;
/* 482 */     this.amended = false;
/* 483 */     this.dirty = null;
/* 484 */     this.misses = 0;
/*     */   }
/*     */   
/*     */   private void dirtyLocked() {
/* 488 */     if (this.dirty != null)
/* 489 */       return;  this.dirty = this.function.apply(this.read.size());
/* 490 */     Int2ObjectMaps.fastForEach(this.read, entry -> {
/*     */           if (!((Int2ObjectSyncMap.ExpungingEntry)entry.getValue()).tryExpunge()) {
/*     */             this.dirty.put(entry.getIntKey(), entry.getValue());
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   static final class ExpungingEntryImpl<V>
/*     */     implements Int2ObjectSyncMap.ExpungingEntry<V>
/*     */   {
/* 500 */     private static final AtomicReferenceFieldUpdater<ExpungingEntryImpl, Object> UPDATER = AtomicReferenceFieldUpdater.newUpdater(ExpungingEntryImpl.class, Object.class, "value");
/* 501 */     private static final Object EXPUNGED = new Object();
/*     */     private volatile Object value;
/*     */     
/*     */     ExpungingEntryImpl(V value) {
/* 505 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean exists() {
/* 510 */       return (this.value != null && this.value != EXPUNGED);
/*     */     }
/*     */ 
/*     */     
/*     */     public V get() {
/* 515 */       return (this.value == EXPUNGED) ? null : (V)this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOr(V other) {
/* 520 */       Object value = this.value;
/* 521 */       return (value != null && value != EXPUNGED) ? (V)this.value : other;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ObjectSyncMap.InsertionResult<V> setIfAbsent(V value) {
/*     */       while (true) {
/* 527 */         Object previous = this.value;
/* 528 */         if (previous == EXPUNGED) return new Int2ObjectSyncMapImpl.InsertionResultImpl<>((byte)2, null, null); 
/* 529 */         if (previous != null) return new Int2ObjectSyncMapImpl.InsertionResultImpl<>((byte)0, (V)previous, (V)previous); 
/* 530 */         if (UPDATER.compareAndSet(this, null, value)) {
/* 531 */           return new Int2ObjectSyncMapImpl.InsertionResultImpl<>((byte)1, null, value);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ObjectSyncMap.InsertionResult<V> computeIfAbsent(int key, IntFunction<? extends V> function) {
/* 538 */       V next = null;
/*     */       while (true) {
/* 540 */         Object previous = this.value;
/* 541 */         if (previous == EXPUNGED) return new Int2ObjectSyncMapImpl.InsertionResultImpl<>((byte)2, null, null); 
/* 542 */         if (previous != null) return new Int2ObjectSyncMapImpl.InsertionResultImpl<>((byte)0, (V)previous, (V)previous); 
/* 543 */         if (UPDATER.compareAndSet(this, null, (next != null) ? next : (next = function.apply(key)))) {
/* 544 */           return new Int2ObjectSyncMapImpl.InsertionResultImpl<>((byte)1, null, next);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ObjectSyncMap.InsertionResult<V> computeIfAbsentPrimitive(int key, Int2ObjectFunction<? extends V> function) {
/* 551 */       V next = null;
/*     */       while (true) {
/* 553 */         Object previous = this.value;
/* 554 */         if (previous == EXPUNGED) return new Int2ObjectSyncMapImpl.InsertionResultImpl<>((byte)2, null, null); 
/* 555 */         if (previous != null) return new Int2ObjectSyncMapImpl.InsertionResultImpl<>((byte)0, (V)previous, (V)previous); 
/* 556 */         if (UPDATER.compareAndSet(this, null, (next != null) ? next : (next = (V)(function.containsKey(key) ? function.get(key) : null)))) {
/* 557 */           return new Int2ObjectSyncMapImpl.InsertionResultImpl<>((byte)1, null, next);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ObjectSyncMap.InsertionResult<V> computeIfPresent(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 564 */       V next = null;
/*     */       while (true) {
/* 566 */         Object previous = this.value;
/* 567 */         if (previous == EXPUNGED) return new Int2ObjectSyncMapImpl.InsertionResultImpl<>((byte)2, null, null); 
/* 568 */         if (previous == null) return new Int2ObjectSyncMapImpl.InsertionResultImpl<>((byte)0, null, null); 
/* 569 */         if (UPDATER.compareAndSet(this, previous, (next != null) ? next : (next = remappingFunction.apply(Integer.valueOf(key), (V)previous)))) {
/* 570 */           return new Int2ObjectSyncMapImpl.InsertionResultImpl<>((byte)1, (V)previous, next);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ObjectSyncMap.InsertionResult<V> compute(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 577 */       V next = null;
/*     */       while (true) {
/* 579 */         Object previous = this.value;
/* 580 */         if (previous == EXPUNGED) return new Int2ObjectSyncMapImpl.InsertionResultImpl<>((byte)2, null, null); 
/* 581 */         if (UPDATER.compareAndSet(this, previous, (next != null) ? next : (next = remappingFunction.apply(Integer.valueOf(key), (V)previous)))) {
/* 582 */           return new Int2ObjectSyncMapImpl.InsertionResultImpl<>((byte)1, (V)previous, next);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void set(V value) {
/* 589 */       UPDATER.set(this, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(Object compare, V value) {
/*     */       while (true) {
/* 595 */         Object previous = this.value;
/* 596 */         if (previous == EXPUNGED || !Objects.equals(previous, compare)) return false; 
/* 597 */         if (UPDATER.compareAndSet(this, previous, value)) return true;
/*     */       
/*     */       } 
/*     */     }
/*     */     
/*     */     public V clear() {
/*     */       while (true) {
/* 604 */         Object previous = this.value;
/* 605 */         if (previous == null || previous == EXPUNGED) return null; 
/* 606 */         if (UPDATER.compareAndSet(this, previous, null)) return (V)previous;
/*     */       
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean trySet(V value) {
/*     */       while (true) {
/* 613 */         Object previous = this.value;
/* 614 */         if (previous == EXPUNGED) return false; 
/* 615 */         if (UPDATER.compareAndSet(this, previous, value)) return true;
/*     */       
/*     */       } 
/*     */     }
/*     */     
/*     */     public V tryReplace(V value) {
/*     */       while (true) {
/* 622 */         Object previous = this.value;
/* 623 */         if (previous == null || previous == EXPUNGED) return null; 
/* 624 */         if (UPDATER.compareAndSet(this, previous, value)) return (V)previous;
/*     */       
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean tryExpunge() {
/* 630 */       while (this.value == null) {
/* 631 */         if (UPDATER.compareAndSet(this, null, EXPUNGED)) return true; 
/*     */       } 
/* 633 */       return (this.value == EXPUNGED);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryUnexpungeAndSet(V value) {
/* 638 */       return UPDATER.compareAndSet(this, EXPUNGED, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryUnexpungeAndCompute(int key, IntFunction<? extends V> function) {
/* 643 */       if (this.value == EXPUNGED) {
/* 644 */         Object value = function.apply(key);
/* 645 */         return UPDATER.compareAndSet(this, EXPUNGED, value);
/*     */       } 
/* 647 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryUnexpungeAndComputePrimitive(int key, Int2ObjectFunction<? extends V> function) {
/* 652 */       if (this.value == EXPUNGED) {
/* 653 */         Object value = function.containsKey(key) ? function.get(key) : null;
/* 654 */         return UPDATER.compareAndSet(this, EXPUNGED, value);
/*     */       } 
/* 656 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryUnexpungeAndCompute(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 661 */       if (this.value == EXPUNGED) {
/* 662 */         Object value = remappingFunction.apply(Integer.valueOf(key), null);
/* 663 */         return UPDATER.compareAndSet(this, EXPUNGED, value);
/*     */       } 
/* 665 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class InsertionResultImpl<V>
/*     */     implements Int2ObjectSyncMap.InsertionResult<V> {
/*     */     private static final byte UNCHANGED = 0;
/*     */     private static final byte UPDATED = 1;
/*     */     private static final byte EXPUNGED = 2;
/*     */     private final byte operation;
/*     */     private final V previous;
/*     */     private final V current;
/*     */     
/*     */     InsertionResultImpl(byte operation, V previous, V current) {
/* 679 */       this.operation = operation;
/* 680 */       this.previous = previous;
/* 681 */       this.current = current;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte operation() {
/* 686 */       return this.operation;
/*     */     }
/*     */ 
/*     */     
/*     */     public V previous() {
/* 691 */       return this.previous;
/*     */     }
/*     */ 
/*     */     
/*     */     public V current() {
/* 696 */       return this.current;
/*     */     }
/*     */   }
/*     */   
/*     */   final class MapEntry implements Int2ObjectMap.Entry<V> {
/*     */     private final int key;
/*     */     private V value;
/*     */     
/*     */     MapEntry(int key, V value) {
/* 705 */       this.key = key;
/* 706 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getIntKey() {
/* 711 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public V getValue() {
/* 716 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public V setValue(V value) {
/* 721 */       Objects.requireNonNull(value, "value");
/* 722 */       V previous = Int2ObjectSyncMapImpl.this.put(this.key, value);
/* 723 */       this.value = value;
/* 724 */       return previous;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 729 */       return "Int2ObjectSyncMapImpl.MapEntry{key=" + getIntKey() + ", value=" + getValue() + "}";
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object other) {
/* 734 */       if (this == other) return true; 
/* 735 */       if (!(other instanceof Int2ObjectMap.Entry)) return false; 
/* 736 */       Int2ObjectMap.Entry<?> that = (Int2ObjectMap.Entry)other;
/* 737 */       return (Objects.equals(Integer.valueOf(getIntKey()), Integer.valueOf(that.getIntKey())) && 
/* 738 */         Objects.equals(getValue(), that.getValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 743 */       return Objects.hash(new Object[] { Integer.valueOf(getIntKey()), getValue() });
/*     */     }
/*     */   }
/*     */   
/*     */   final class EntrySetView
/*     */     extends AbstractObjectSet<Int2ObjectMap.Entry<V>> {
/*     */     public int size() {
/* 750 */       return Int2ObjectSyncMapImpl.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object entry) {
/* 755 */       if (!(entry instanceof Int2ObjectMap.Entry)) return false; 
/* 756 */       Int2ObjectMap.Entry<?> mapEntry = (Int2ObjectMap.Entry)entry;
/* 757 */       V value = Int2ObjectSyncMapImpl.this.get(mapEntry.getIntKey());
/* 758 */       return (value != null && Objects.equals(value, mapEntry.getValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(Int2ObjectMap.Entry<V> entry) {
/* 763 */       Objects.requireNonNull(entry, "entry");
/* 764 */       return (Int2ObjectSyncMapImpl.this.put(entry.getIntKey(), entry.getValue()) == null);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object entry) {
/* 769 */       if (!(entry instanceof Int2ObjectMap.Entry)) return false; 
/* 770 */       Int2ObjectMap.Entry<?> mapEntry = (Int2ObjectMap.Entry)entry;
/* 771 */       return Int2ObjectSyncMapImpl.this.remove(mapEntry.getIntKey(), mapEntry.getValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 776 */       Int2ObjectSyncMapImpl.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Int2ObjectMap.Entry<V>> iterator() {
/* 781 */       Int2ObjectSyncMapImpl.this.promote();
/* 782 */       return new Int2ObjectSyncMapImpl.EntryIterator((Iterator<Int2ObjectMap.Entry<Int2ObjectSyncMap.ExpungingEntry<V>>>)Int2ObjectSyncMapImpl.this.read.int2ObjectEntrySet().iterator());
/*     */     }
/*     */   }
/*     */   
/*     */   final class EntryIterator implements ObjectIterator<Int2ObjectMap.Entry<V>> {
/*     */     private final Iterator<Int2ObjectMap.Entry<Int2ObjectSyncMap.ExpungingEntry<V>>> backingIterator;
/*     */     private Int2ObjectMap.Entry<V> next;
/*     */     private Int2ObjectMap.Entry<V> current;
/*     */     
/*     */     EntryIterator(Iterator<Int2ObjectMap.Entry<Int2ObjectSyncMap.ExpungingEntry<V>>> backingIterator) {
/* 792 */       this.backingIterator = backingIterator;
/* 793 */       advance();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 798 */       return (this.next != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ObjectMap.Entry<V> next() {
/*     */       Int2ObjectMap.Entry<V> current;
/* 804 */       if ((current = this.next) == null) throw new NoSuchElementException(); 
/* 805 */       this.current = current;
/* 806 */       advance();
/* 807 */       return current;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/*     */       Int2ObjectMap.Entry<V> current;
/* 813 */       if ((current = this.current) == null) throw new IllegalStateException(); 
/* 814 */       this.current = null;
/* 815 */       Int2ObjectSyncMapImpl.this.remove(current.getIntKey());
/*     */     }
/*     */     
/*     */     private void advance() {
/* 819 */       this.next = null;
/* 820 */       while (this.backingIterator.hasNext()) {
/*     */         Int2ObjectMap.Entry<Int2ObjectSyncMap.ExpungingEntry<V>> entry; V value;
/* 822 */         if ((value = ((Int2ObjectSyncMap.ExpungingEntry<V>)(entry = this.backingIterator.next()).getValue()).get()) != null) {
/* 823 */           this.next = new Int2ObjectSyncMapImpl.MapEntry(entry.getIntKey(), value);
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\flare\fastutil\Int2ObjectSyncMapImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
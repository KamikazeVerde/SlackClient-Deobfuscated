/*     */ package com.viaversion.viaversion.libs.flare;
/*     */ 
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
/*     */ import java.util.function.BiConsumer;
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
/*     */ final class SyncMapImpl<K, V>
/*     */   extends AbstractMap<K, V>
/*     */   implements SyncMap<K, V>
/*     */ {
/*  49 */   private final transient Object lock = new Object();
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile transient Map<K, SyncMap.ExpungingEntry<V>> read;
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile transient boolean amended;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Map<K, SyncMap.ExpungingEntry<V>> dirty;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient int misses;
/*     */ 
/*     */ 
/*     */   
/*     */   private final transient IntFunction<Map<K, SyncMap.ExpungingEntry<V>>> function;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient EntrySetView entrySet;
/*     */ 
/*     */ 
/*     */   
/*     */   SyncMapImpl(IntFunction<Map<K, SyncMap.ExpungingEntry<V>>> function, int initialCapacity) {
/*  78 */     if (initialCapacity < 0) throw new IllegalArgumentException("Initial capacity must be greater than 0"); 
/*  79 */     this.function = function;
/*  80 */     this.read = function.apply(initialCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  87 */     promote();
/*  88 */     int size = 0;
/*  89 */     for (SyncMap.ExpungingEntry<V> value : this.read.values()) {
/*  90 */       if (value.exists()) size++; 
/*     */     } 
/*  92 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  97 */     promote();
/*  98 */     for (SyncMap.ExpungingEntry<V> value : this.read.values()) {
/*  99 */       if (value.exists()) return false; 
/*     */     } 
/* 101 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 106 */     SyncMap.ExpungingEntry<V> entry = getEntry(key);
/* 107 */     return (entry != null && entry.exists());
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(Object key) {
/* 112 */     SyncMap.ExpungingEntry<V> entry = getEntry(key);
/* 113 */     return (entry != null) ? entry.get() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public V getOrDefault(Object key, V defaultValue) {
/* 118 */     Objects.requireNonNull(defaultValue, "defaultValue");
/* 119 */     SyncMap.ExpungingEntry<V> entry = getEntry(key);
/* 120 */     return (entry != null) ? entry.getOr(defaultValue) : defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   private SyncMap.ExpungingEntry<V> getEntry(Object key) {
/* 125 */     SyncMap.ExpungingEntry<V> entry = this.read.get(key);
/* 126 */     if (entry == null && this.amended) {
/* 127 */       synchronized (this.lock) {
/* 128 */         if ((entry = (SyncMap.ExpungingEntry<V>)this.read.get(key)) == null && this.amended && this.dirty != null) {
/* 129 */           entry = this.dirty.get(key);
/*     */ 
/*     */ 
/*     */           
/* 133 */           missLocked();
/*     */         } 
/*     */       } 
/*     */     }
/* 137 */     return entry;
/*     */   }
/*     */ 
/*     */   
/*     */   public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
/* 142 */     Objects.requireNonNull(mappingFunction, "mappingFunction");
/* 143 */     SyncMap.ExpungingEntry<V> entry = this.read.get(key);
/* 144 */     SyncMap.InsertionResult<V> result = (entry != null) ? entry.<K>computeIfAbsent(key, mappingFunction) : null;
/* 145 */     if (result != null && result.operation() == 1) return result.current(); 
/* 146 */     synchronized (this.lock) {
/* 147 */       if ((entry = (SyncMap.ExpungingEntry<V>)this.read.get(key)) != null) {
/*     */ 
/*     */         
/* 150 */         if (entry.tryUnexpungeAndCompute(key, mappingFunction)) {
/* 151 */           if (entry.exists()) this.dirty.put(key, entry); 
/* 152 */           return entry.get();
/*     */         } 
/* 154 */         result = entry.computeIfAbsent(key, mappingFunction);
/*     */       }
/* 156 */       else if (this.dirty != null && (entry = (SyncMap.ExpungingEntry<V>)this.dirty.get(key)) != null) {
/* 157 */         result = entry.computeIfAbsent(key, mappingFunction);
/* 158 */         if (result.current() == null) this.dirty.remove(key);
/*     */ 
/*     */ 
/*     */         
/* 162 */         missLocked();
/*     */       } else {
/* 164 */         if (!this.amended) {
/*     */ 
/*     */           
/* 167 */           dirtyLocked();
/* 168 */           this.amended = true;
/*     */         } 
/* 170 */         V computed = mappingFunction.apply(key);
/* 171 */         if (computed != null) this.dirty.put(key, new ExpungingEntryImpl<>(computed)); 
/* 172 */         return computed;
/*     */       } 
/*     */     } 
/* 175 */     return result.current();
/*     */   }
/*     */ 
/*     */   
/*     */   public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 180 */     Objects.requireNonNull(remappingFunction, "remappingFunction");
/* 181 */     SyncMap.ExpungingEntry<V> entry = this.read.get(key);
/* 182 */     SyncMap.InsertionResult<V> result = (entry != null) ? entry.<K>computeIfPresent(key, remappingFunction) : null;
/* 183 */     if (result != null && result.operation() == 1) return result.current(); 
/* 184 */     synchronized (this.lock) {
/* 185 */       if ((entry = (SyncMap.ExpungingEntry<V>)this.read.get(key)) != null) {
/* 186 */         result = entry.computeIfPresent(key, remappingFunction);
/* 187 */       } else if (this.dirty != null && (entry = (SyncMap.ExpungingEntry<V>)this.dirty.get(key)) != null) {
/* 188 */         result = entry.computeIfPresent(key, remappingFunction);
/* 189 */         if (result.current() == null) this.dirty.remove(key);
/*     */ 
/*     */ 
/*     */         
/* 193 */         missLocked();
/*     */       } 
/*     */     } 
/* 196 */     return (result != null) ? result.current() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 201 */     Objects.requireNonNull(remappingFunction, "remappingFunction");
/* 202 */     SyncMap.ExpungingEntry<V> entry = this.read.get(key);
/* 203 */     SyncMap.InsertionResult<V> result = (entry != null) ? entry.<K>compute(key, remappingFunction) : null;
/* 204 */     if (result != null && result.operation() == 1) return result.current(); 
/* 205 */     synchronized (this.lock) {
/* 206 */       if ((entry = (SyncMap.ExpungingEntry<V>)this.read.get(key)) != null) {
/*     */ 
/*     */         
/* 209 */         if (entry.tryUnexpungeAndCompute(key, remappingFunction)) {
/* 210 */           if (entry.exists()) this.dirty.put(key, entry); 
/* 211 */           return entry.get();
/*     */         } 
/* 213 */         result = entry.compute(key, remappingFunction);
/*     */       }
/* 215 */       else if (this.dirty != null && (entry = (SyncMap.ExpungingEntry<V>)this.dirty.get(key)) != null) {
/* 216 */         result = entry.compute(key, remappingFunction);
/* 217 */         if (result.current() == null) this.dirty.remove(key);
/*     */ 
/*     */ 
/*     */         
/* 221 */         missLocked();
/*     */       } else {
/* 223 */         if (!this.amended) {
/*     */ 
/*     */           
/* 226 */           dirtyLocked();
/* 227 */           this.amended = true;
/*     */         } 
/* 229 */         V computed = remappingFunction.apply(key, null);
/* 230 */         if (computed != null) this.dirty.put(key, new ExpungingEntryImpl<>(computed)); 
/* 231 */         return computed;
/*     */       } 
/*     */     } 
/* 234 */     return result.current();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V putIfAbsent(K key, V value) {
/* 240 */     Objects.requireNonNull(value, "value");
/* 241 */     SyncMap.ExpungingEntry<V> entry = this.read.get(key);
/* 242 */     SyncMap.InsertionResult<V> result = (entry != null) ? entry.setIfAbsent(value) : null;
/* 243 */     if (result != null && result.operation() == 1) return result.previous(); 
/* 244 */     synchronized (this.lock) {
/* 245 */       if ((entry = (SyncMap.ExpungingEntry<V>)this.read.get(key)) != null) {
/*     */ 
/*     */ 
/*     */         
/* 249 */         if (entry.tryUnexpungeAndSet(value)) {
/* 250 */           this.dirty.put(key, entry);
/* 251 */           return null;
/*     */         } 
/* 253 */         result = entry.setIfAbsent(value);
/*     */       }
/* 255 */       else if (this.dirty != null && (entry = (SyncMap.ExpungingEntry<V>)this.dirty.get(key)) != null) {
/* 256 */         result = entry.setIfAbsent(value);
/*     */ 
/*     */ 
/*     */         
/* 260 */         missLocked();
/*     */       } else {
/* 262 */         if (!this.amended) {
/*     */ 
/*     */           
/* 265 */           dirtyLocked();
/* 266 */           this.amended = true;
/*     */         } 
/* 268 */         this.dirty.put(key, new ExpungingEntryImpl<>(value));
/* 269 */         return null;
/*     */       } 
/*     */     } 
/* 272 */     return result.previous();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V put(K key, V value) {
/* 278 */     Objects.requireNonNull(value, "value");
/* 279 */     SyncMap.ExpungingEntry<V> entry = this.read.get(key);
/* 280 */     V previous = (entry != null) ? entry.get() : null;
/* 281 */     if (entry != null && entry.trySet(value)) return previous; 
/* 282 */     synchronized (this.lock) {
/* 283 */       if ((entry = (SyncMap.ExpungingEntry<V>)this.read.get(key)) != null) {
/* 284 */         previous = entry.get();
/*     */ 
/*     */         
/* 287 */         if (entry.tryUnexpungeAndSet(value)) {
/* 288 */           this.dirty.put(key, entry);
/*     */         } else {
/* 290 */           entry.set(value);
/*     */         } 
/* 292 */       } else if (this.dirty != null && (entry = (SyncMap.ExpungingEntry<V>)this.dirty.get(key)) != null) {
/* 293 */         previous = entry.get();
/* 294 */         entry.set(value);
/*     */       } else {
/* 296 */         if (!this.amended) {
/*     */ 
/*     */           
/* 299 */           dirtyLocked();
/* 300 */           this.amended = true;
/*     */         } 
/* 302 */         this.dirty.put(key, new ExpungingEntryImpl<>(value));
/* 303 */         return null;
/*     */       } 
/*     */     } 
/* 306 */     return previous;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V remove(Object key) {
/* 312 */     SyncMap.ExpungingEntry<V> entry = this.read.get(key);
/* 313 */     if (entry == null && this.amended) {
/* 314 */       synchronized (this.lock) {
/* 315 */         if ((entry = (SyncMap.ExpungingEntry<V>)this.read.get(key)) == null && this.amended && this.dirty != null) {
/* 316 */           entry = this.dirty.remove(key);
/*     */ 
/*     */ 
/*     */           
/* 320 */           missLocked();
/*     */         } 
/*     */       } 
/*     */     }
/* 324 */     return (entry != null) ? entry.clear() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Object key, Object value) {
/* 330 */     Objects.requireNonNull(value, "value");
/* 331 */     SyncMap.ExpungingEntry<V> entry = this.read.get(key);
/* 332 */     if (entry == null && this.amended) {
/* 333 */       synchronized (this.lock) {
/* 334 */         if ((entry = (SyncMap.ExpungingEntry<V>)this.read.get(key)) == null && this.amended && this.dirty != null) {
/* 335 */           boolean present = ((entry = (SyncMap.ExpungingEntry<V>)this.dirty.get(key)) != null && entry.replace(value, null));
/* 336 */           if (present) this.dirty.remove(key);
/*     */ 
/*     */ 
/*     */           
/* 340 */           missLocked();
/* 341 */           return present;
/*     */         } 
/*     */       } 
/*     */     }
/* 345 */     return (entry != null && entry.replace(value, null));
/*     */   }
/*     */ 
/*     */   
/*     */   public V replace(K key, V value) {
/* 350 */     Objects.requireNonNull(value, "value");
/* 351 */     SyncMap.ExpungingEntry<V> entry = getEntry(key);
/* 352 */     return (entry != null) ? entry.tryReplace(value) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean replace(K key, V oldValue, V newValue) {
/* 357 */     Objects.requireNonNull(oldValue, "oldValue");
/* 358 */     Objects.requireNonNull(newValue, "newValue");
/* 359 */     SyncMap.ExpungingEntry<V> entry = getEntry(key);
/* 360 */     return (entry != null && entry.replace(oldValue, newValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEach(BiConsumer<? super K, ? super V> action) {
/* 367 */     Objects.requireNonNull(action, "action");
/* 368 */     promote();
/*     */     
/* 370 */     for (Map.Entry<K, SyncMap.ExpungingEntry<V>> that : this.read.entrySet()) {
/* 371 */       V value; if ((value = ((SyncMap.ExpungingEntry<V>)that.getValue()).get()) != null) {
/* 372 */         action.accept(that.getKey(), value);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
/* 379 */     Objects.requireNonNull(function, "function");
/* 380 */     promote();
/*     */     
/* 382 */     for (Map.Entry<K, SyncMap.ExpungingEntry<V>> that : this.read.entrySet()) {
/* 383 */       SyncMap.ExpungingEntry<V> entry; V value; if ((value = (entry = (SyncMap.ExpungingEntry<V>)that.getValue()).get()) != null) {
/* 384 */         entry.tryReplace(function.apply(that.getKey(), value));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 391 */     synchronized (this.lock) {
/* 392 */       this.read = this.function.apply(this.read.size());
/* 393 */       this.dirty = null;
/* 394 */       this.amended = false;
/* 395 */       this.misses = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<K, V>> entrySet() {
/* 403 */     if (this.entrySet != null) return this.entrySet; 
/* 404 */     return this.entrySet = new EntrySetView();
/*     */   }
/*     */   
/*     */   private void promote() {
/* 408 */     if (this.amended) {
/* 409 */       synchronized (this.lock) {
/* 410 */         if (this.amended) {
/* 411 */           promoteLocked();
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void missLocked() {
/* 418 */     this.misses++;
/* 419 */     if (this.misses < this.dirty.size())
/* 420 */       return;  promoteLocked();
/*     */   }
/*     */   
/*     */   private void promoteLocked() {
/* 424 */     this.read = this.dirty;
/* 425 */     this.amended = false;
/* 426 */     this.dirty = null;
/* 427 */     this.misses = 0;
/*     */   }
/*     */   
/*     */   private void dirtyLocked() {
/* 431 */     if (this.dirty != null)
/* 432 */       return;  this.dirty = this.function.apply(this.read.size());
/* 433 */     for (Map.Entry<K, SyncMap.ExpungingEntry<V>> entry : this.read.entrySet()) {
/* 434 */       if (!((SyncMap.ExpungingEntry)entry.getValue()).tryExpunge()) {
/* 435 */         this.dirty.put(entry.getKey(), entry.getValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   static final class ExpungingEntryImpl<V>
/*     */     implements SyncMap.ExpungingEntry<V>
/*     */   {
/* 443 */     private static final AtomicReferenceFieldUpdater<ExpungingEntryImpl, Object> UPDATER = AtomicReferenceFieldUpdater.newUpdater(ExpungingEntryImpl.class, Object.class, "value");
/* 444 */     private static final Object EXPUNGED = new Object();
/*     */     private volatile Object value;
/*     */     
/*     */     ExpungingEntryImpl(V value) {
/* 448 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean exists() {
/* 453 */       return (this.value != null && this.value != EXPUNGED);
/*     */     }
/*     */ 
/*     */     
/*     */     public V get() {
/* 458 */       return (this.value == EXPUNGED) ? null : (V)this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOr(V other) {
/* 463 */       Object value = this.value;
/* 464 */       return (value != null && value != EXPUNGED) ? (V)this.value : other;
/*     */     }
/*     */ 
/*     */     
/*     */     public SyncMap.InsertionResult<V> setIfAbsent(V value) {
/*     */       while (true) {
/* 470 */         Object previous = this.value;
/* 471 */         if (previous == EXPUNGED) return new SyncMapImpl.InsertionResultImpl<>((byte)2, null, null); 
/* 472 */         if (previous != null) return new SyncMapImpl.InsertionResultImpl<>((byte)0, (V)previous, (V)previous); 
/* 473 */         if (UPDATER.compareAndSet(this, null, value)) {
/* 474 */           return new SyncMapImpl.InsertionResultImpl<>((byte)1, null, value);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public <K> SyncMap.InsertionResult<V> computeIfAbsent(K key, Function<? super K, ? extends V> function) {
/* 481 */       V next = null;
/*     */       while (true) {
/* 483 */         Object previous = this.value;
/* 484 */         if (previous == EXPUNGED) return new SyncMapImpl.InsertionResultImpl<>((byte)2, null, null); 
/* 485 */         if (previous != null) return new SyncMapImpl.InsertionResultImpl<>((byte)0, (V)previous, (V)previous); 
/* 486 */         if (UPDATER.compareAndSet(this, null, (next != null) ? next : (next = function.apply(key)))) {
/* 487 */           return new SyncMapImpl.InsertionResultImpl<>((byte)1, null, next);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public <K> SyncMap.InsertionResult<V> computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 494 */       V next = null;
/*     */       while (true) {
/* 496 */         Object previous = this.value;
/* 497 */         if (previous == EXPUNGED) return new SyncMapImpl.InsertionResultImpl<>((byte)2, null, null); 
/* 498 */         if (previous == null) return new SyncMapImpl.InsertionResultImpl<>((byte)0, null, null); 
/* 499 */         if (UPDATER.compareAndSet(this, previous, (next != null) ? next : (next = remappingFunction.apply(key, (V)previous)))) {
/* 500 */           return new SyncMapImpl.InsertionResultImpl<>((byte)1, (V)previous, next);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public <K> SyncMap.InsertionResult<V> compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 507 */       V next = null;
/*     */       while (true) {
/* 509 */         Object previous = this.value;
/* 510 */         if (previous == EXPUNGED) return new SyncMapImpl.InsertionResultImpl<>((byte)2, null, null); 
/* 511 */         if (UPDATER.compareAndSet(this, previous, (next != null) ? next : (next = remappingFunction.apply(key, (V)previous)))) {
/* 512 */           return new SyncMapImpl.InsertionResultImpl<>((byte)1, (V)previous, next);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void set(V value) {
/* 519 */       UPDATER.set(this, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(Object compare, V value) {
/*     */       while (true) {
/* 525 */         Object previous = this.value;
/* 526 */         if (previous == EXPUNGED || !Objects.equals(previous, compare)) return false; 
/* 527 */         if (UPDATER.compareAndSet(this, previous, value)) return true;
/*     */       
/*     */       } 
/*     */     }
/*     */     
/*     */     public V clear() {
/*     */       while (true) {
/* 534 */         Object previous = this.value;
/* 535 */         if (previous == null || previous == EXPUNGED) return null; 
/* 536 */         if (UPDATER.compareAndSet(this, previous, null)) return (V)previous;
/*     */       
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean trySet(V value) {
/*     */       while (true) {
/* 543 */         Object previous = this.value;
/* 544 */         if (previous == EXPUNGED) return false; 
/* 545 */         if (UPDATER.compareAndSet(this, previous, value)) return true;
/*     */       
/*     */       } 
/*     */     }
/*     */     
/*     */     public V tryReplace(V value) {
/*     */       while (true) {
/* 552 */         Object previous = this.value;
/* 553 */         if (previous == null || previous == EXPUNGED) return null; 
/* 554 */         if (UPDATER.compareAndSet(this, previous, value)) return (V)previous;
/*     */       
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean tryExpunge() {
/* 560 */       while (this.value == null) {
/* 561 */         if (UPDATER.compareAndSet(this, null, EXPUNGED)) return true; 
/*     */       } 
/* 563 */       return (this.value == EXPUNGED);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryUnexpungeAndSet(V value) {
/* 568 */       return UPDATER.compareAndSet(this, EXPUNGED, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public <K> boolean tryUnexpungeAndCompute(K key, Function<? super K, ? extends V> function) {
/* 573 */       if (this.value == EXPUNGED) {
/* 574 */         Object value = function.apply(key);
/* 575 */         return UPDATER.compareAndSet(this, EXPUNGED, value);
/*     */       } 
/* 577 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public <K> boolean tryUnexpungeAndCompute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 582 */       if (this.value == EXPUNGED) {
/* 583 */         Object value = remappingFunction.apply(key, null);
/* 584 */         return UPDATER.compareAndSet(this, EXPUNGED, value);
/*     */       } 
/* 586 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class InsertionResultImpl<V>
/*     */     implements SyncMap.InsertionResult<V> {
/*     */     private static final byte UNCHANGED = 0;
/*     */     private static final byte UPDATED = 1;
/*     */     private static final byte EXPUNGED = 2;
/*     */     private final byte operation;
/*     */     private final V previous;
/*     */     private final V current;
/*     */     
/*     */     InsertionResultImpl(byte operation, V previous, V current) {
/* 600 */       this.operation = operation;
/* 601 */       this.previous = previous;
/* 602 */       this.current = current;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte operation() {
/* 607 */       return this.operation;
/*     */     }
/*     */ 
/*     */     
/*     */     public V previous() {
/* 612 */       return this.previous;
/*     */     }
/*     */ 
/*     */     
/*     */     public V current() {
/* 617 */       return this.current;
/*     */     }
/*     */   }
/*     */   
/*     */   final class MapEntry implements Map.Entry<K, V> {
/*     */     private final K key;
/*     */     private V value;
/*     */     
/*     */     MapEntry(K key, V value) {
/* 626 */       this.key = key;
/* 627 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public K getKey() {
/* 632 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public V getValue() {
/* 637 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public V setValue(V value) {
/* 642 */       Objects.requireNonNull(value, "value");
/* 643 */       V previous = SyncMapImpl.this.put(this.key, value);
/* 644 */       this.value = value;
/* 645 */       return previous;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 650 */       return "SyncMapImpl.MapEntry{key=" + getKey() + ", value=" + getValue() + "}";
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object other) {
/* 655 */       if (this == other) return true; 
/* 656 */       if (!(other instanceof Map.Entry)) return false; 
/* 657 */       Map.Entry<?, ?> that = (Map.Entry<?, ?>)other;
/* 658 */       return (Objects.equals(getKey(), that.getKey()) && 
/* 659 */         Objects.equals(getValue(), that.getValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 664 */       return Objects.hash(new Object[] { getKey(), getValue() });
/*     */     }
/*     */   }
/*     */   
/*     */   final class EntrySetView
/*     */     extends AbstractSet<Map.Entry<K, V>> {
/*     */     public int size() {
/* 671 */       return SyncMapImpl.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object entry) {
/* 676 */       if (!(entry instanceof Map.Entry)) return false; 
/* 677 */       Map.Entry<?, ?> mapEntry = (Map.Entry<?, ?>)entry;
/* 678 */       V value = (V)SyncMapImpl.this.get(mapEntry.getKey());
/* 679 */       return (value != null && Objects.equals(value, mapEntry.getValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(Map.Entry<K, V> entry) {
/* 684 */       Objects.requireNonNull(entry, "entry");
/* 685 */       return (SyncMapImpl.this.put(entry.getKey(), entry.getValue()) == null);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object entry) {
/* 690 */       if (!(entry instanceof Map.Entry)) return false; 
/* 691 */       Map.Entry<?, ?> mapEntry = (Map.Entry<?, ?>)entry;
/* 692 */       return SyncMapImpl.this.remove(mapEntry.getKey(), mapEntry.getValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 697 */       SyncMapImpl.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<Map.Entry<K, V>> iterator() {
/* 702 */       SyncMapImpl.this.promote();
/* 703 */       return new SyncMapImpl.EntryIterator(SyncMapImpl.this.read.entrySet().iterator());
/*     */     }
/*     */   }
/*     */   
/*     */   final class EntryIterator implements Iterator<Map.Entry<K, V>> {
/*     */     private final Iterator<Map.Entry<K, SyncMap.ExpungingEntry<V>>> backingIterator;
/*     */     private Map.Entry<K, V> next;
/*     */     private Map.Entry<K, V> current;
/*     */     
/*     */     EntryIterator(Iterator<Map.Entry<K, SyncMap.ExpungingEntry<V>>> backingIterator) {
/* 713 */       this.backingIterator = backingIterator;
/* 714 */       advance();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 719 */       return (this.next != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public Map.Entry<K, V> next() {
/*     */       Map.Entry<K, V> current;
/* 725 */       if ((current = this.next) == null) throw new NoSuchElementException(); 
/* 726 */       this.current = current;
/* 727 */       advance();
/* 728 */       return current;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/*     */       Map.Entry<K, V> current;
/* 734 */       if ((current = this.current) == null) throw new IllegalStateException(); 
/* 735 */       this.current = null;
/* 736 */       SyncMapImpl.this.remove(current.getKey());
/*     */     }
/*     */     
/*     */     private void advance() {
/* 740 */       this.next = null;
/* 741 */       while (this.backingIterator.hasNext()) {
/*     */         Map.Entry<K, SyncMap.ExpungingEntry<V>> entry; V value;
/* 743 */         if ((value = ((SyncMap.ExpungingEntry<V>)(entry = this.backingIterator.next()).getValue()).get()) != null) {
/* 744 */           this.next = new SyncMapImpl.MapEntry(entry.getKey(), value);
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\flare\SyncMapImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
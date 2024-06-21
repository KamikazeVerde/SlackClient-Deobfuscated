/*     */ package com.viaversion.viaversion.util;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Set;
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
/*     */ public class Int2IntBiHashMap
/*     */   implements Int2IntBiMap
/*     */ {
/*     */   private final Int2IntMap map;
/*     */   private final Int2IntBiHashMap inverse;
/*     */   
/*     */   public Int2IntBiHashMap() {
/*  38 */     this.map = (Int2IntMap)new Int2IntOpenHashMap();
/*  39 */     this.inverse = new Int2IntBiHashMap(this, -1);
/*     */   }
/*     */   
/*     */   public Int2IntBiHashMap(int expected) {
/*  43 */     this.map = (Int2IntMap)new Int2IntOpenHashMap(expected);
/*  44 */     this.inverse = new Int2IntBiHashMap(this, expected);
/*     */   }
/*     */   
/*     */   private Int2IntBiHashMap(Int2IntBiHashMap inverse, int expected) {
/*  48 */     this.map = (expected != -1) ? (Int2IntMap)new Int2IntOpenHashMap(expected) : (Int2IntMap)new Int2IntOpenHashMap();
/*  49 */     this.inverse = inverse;
/*     */   }
/*     */ 
/*     */   
/*     */   public Int2IntBiMap inverse() {
/*  54 */     return this.inverse;
/*     */   }
/*     */ 
/*     */   
/*     */   public int put(int key, int value) {
/*  59 */     if (containsKey(key) && value == get(key)) return value;
/*     */     
/*  61 */     Preconditions.checkArgument(!containsValue(value), "value already present: %s", new Object[] { Integer.valueOf(value) });
/*  62 */     this.map.put(key, value);
/*  63 */     this.inverse.map.put(value, key);
/*  64 */     return defaultReturnValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(int key, int value) {
/*  69 */     this.map.remove(key, value);
/*  70 */     return this.inverse.map.remove(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int get(int key) {
/*  75 */     return this.map.get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  80 */     this.map.clear();
/*  81 */     this.inverse.map.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  86 */     return this.map.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  91 */     return this.map.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void defaultReturnValue(int rv) {
/*  96 */     this.map.defaultReturnValue(rv);
/*  97 */     this.inverse.map.defaultReturnValue(rv);
/*     */   }
/*     */ 
/*     */   
/*     */   public int defaultReturnValue() {
/* 102 */     return this.map.defaultReturnValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectSet<Int2IntMap.Entry> int2IntEntrySet() {
/* 107 */     return this.map.int2IntEntrySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public IntSet keySet() {
/* 112 */     return this.map.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public IntSet values() {
/* 117 */     return this.inverse.map.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(int key) {
/* 122 */     return this.map.containsKey(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(int value) {
/* 127 */     return this.inverse.map.containsKey(value);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\util\Int2IntBiHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
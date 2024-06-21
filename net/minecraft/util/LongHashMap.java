/*     */ package net.minecraft.util;
/*     */ 
/*     */ public class LongHashMap<V>
/*     */ {
/*   5 */   private transient Entry<V>[] hashArray = (Entry<V>[])new Entry[4096];
/*     */ 
/*     */   
/*     */   private transient int numHashElements;
/*     */ 
/*     */   
/*     */   private int mask;
/*     */ 
/*     */   
/*  14 */   private int capacity = 3072;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  19 */   private final float percentUseable = 0.75F;
/*     */ 
/*     */   
/*     */   private volatile transient int modCount;
/*     */ 
/*     */   
/*     */   public LongHashMap() {
/*  26 */     this.mask = this.hashArray.length - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getHashedKey(long originalKey) {
/*  34 */     return (int)(originalKey ^ originalKey >>> 27L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int hash(int integer) {
/*  44 */     integer = integer ^ integer >>> 20 ^ integer >>> 12;
/*  45 */     return integer ^ integer >>> 7 ^ integer >>> 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getHashIndex(int p_76158_0_, int p_76158_1_) {
/*  53 */     return p_76158_0_ & p_76158_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumHashElements() {
/*  58 */     return this.numHashElements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V getValueByKey(long p_76164_1_) {
/*  66 */     int i = getHashedKey(p_76164_1_);
/*     */     
/*  68 */     for (Entry<V> entry = this.hashArray[getHashIndex(i, this.mask)]; entry != null; entry = entry.nextEntry) {
/*     */       
/*  70 */       if (entry.key == p_76164_1_)
/*     */       {
/*  72 */         return entry.value;
/*     */       }
/*     */     } 
/*     */     
/*  76 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsItem(long p_76161_1_) {
/*  81 */     return (getEntry(p_76161_1_) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   final Entry<V> getEntry(long p_76160_1_) {
/*  86 */     int i = getHashedKey(p_76160_1_);
/*     */     
/*  88 */     for (Entry<V> entry = this.hashArray[getHashIndex(i, this.mask)]; entry != null; entry = entry.nextEntry) {
/*     */       
/*  90 */       if (entry.key == p_76160_1_)
/*     */       {
/*  92 */         return entry;
/*     */       }
/*     */     } 
/*     */     
/*  96 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(long p_76163_1_, V p_76163_3_) {
/* 104 */     int i = getHashedKey(p_76163_1_);
/* 105 */     int j = getHashIndex(i, this.mask);
/*     */     
/* 107 */     for (Entry<V> entry = this.hashArray[j]; entry != null; entry = entry.nextEntry) {
/*     */       
/* 109 */       if (entry.key == p_76163_1_) {
/*     */         
/* 111 */         entry.value = p_76163_3_;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 116 */     this.modCount++;
/* 117 */     createKey(i, p_76163_1_, p_76163_3_, j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void resizeTable(int p_76153_1_) {
/* 125 */     Entry<V>[] entry = this.hashArray;
/* 126 */     int i = entry.length;
/*     */     
/* 128 */     if (i == 1073741824) {
/*     */       
/* 130 */       this.capacity = Integer.MAX_VALUE;
/*     */     }
/*     */     else {
/*     */       
/* 134 */       Entry[] arrayOfEntry = new Entry[p_76153_1_];
/* 135 */       copyHashTableTo((Entry<V>[])arrayOfEntry);
/* 136 */       this.hashArray = (Entry<V>[])arrayOfEntry;
/* 137 */       this.mask = this.hashArray.length - 1;
/* 138 */       float f = p_76153_1_;
/* 139 */       getClass();
/* 140 */       this.capacity = (int)(f * 0.75F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void copyHashTableTo(Entry<V>[] p_76154_1_) {
/* 149 */     Entry<V>[] entry = this.hashArray;
/* 150 */     int i = p_76154_1_.length;
/*     */     
/* 152 */     for (int j = 0; j < entry.length; j++) {
/*     */       
/* 154 */       Entry<V> entry1 = entry[j];
/*     */       
/* 156 */       if (entry1 != null) {
/*     */         Entry<V> entry2;
/* 158 */         entry[j] = null;
/*     */ 
/*     */         
/*     */         do {
/* 162 */           entry2 = entry1.nextEntry;
/* 163 */           int k = getHashIndex(entry1.hash, i - 1);
/* 164 */           entry1.nextEntry = p_76154_1_[k];
/* 165 */           p_76154_1_[k] = entry1;
/* 166 */           entry1 = entry2;
/*     */         }
/* 168 */         while (entry2 != null);
/*     */       } 
/*     */     } 
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
/*     */   public V remove(long p_76159_1_) {
/* 182 */     Entry<V> entry = removeKey(p_76159_1_);
/* 183 */     return (entry == null) ? null : entry.value;
/*     */   }
/*     */ 
/*     */   
/*     */   final Entry<V> removeKey(long p_76152_1_) {
/* 188 */     int i = getHashedKey(p_76152_1_);
/* 189 */     int j = getHashIndex(i, this.mask);
/* 190 */     Entry<V> entry = this.hashArray[j];
/*     */     
/*     */     Entry<V> entry1;
/*     */     
/* 194 */     for (entry1 = entry; entry1 != null; entry1 = entry2) {
/*     */       
/* 196 */       Entry<V> entry2 = entry1.nextEntry;
/*     */       
/* 198 */       if (entry1.key == p_76152_1_) {
/*     */         
/* 200 */         this.modCount++;
/* 201 */         this.numHashElements--;
/*     */         
/* 203 */         if (entry == entry1) {
/*     */           
/* 205 */           this.hashArray[j] = entry2;
/*     */         }
/*     */         else {
/*     */           
/* 209 */           entry.nextEntry = entry2;
/*     */         } 
/*     */         
/* 212 */         return entry1;
/*     */       } 
/*     */       
/* 215 */       entry = entry1;
/*     */     } 
/*     */     
/* 218 */     return entry1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createKey(int p_76156_1_, long p_76156_2_, V p_76156_4_, int p_76156_5_) {
/* 226 */     Entry<V> entry = this.hashArray[p_76156_5_];
/* 227 */     this.hashArray[p_76156_5_] = new Entry<>(p_76156_1_, p_76156_2_, p_76156_4_, entry);
/*     */     
/* 229 */     if (this.numHashElements++ >= this.capacity)
/*     */     {
/* 231 */       resizeTable(2 * this.hashArray.length);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double getKeyDistribution() {
/* 237 */     int i = 0;
/*     */     
/* 239 */     for (int j = 0; j < this.hashArray.length; j++) {
/*     */       
/* 241 */       if (this.hashArray[j] != null)
/*     */       {
/* 243 */         i++;
/*     */       }
/*     */     } 
/*     */     
/* 247 */     return 1.0D * i / this.numHashElements;
/*     */   }
/*     */ 
/*     */   
/*     */   static class Entry<V>
/*     */   {
/*     */     final long key;
/*     */     V value;
/*     */     Entry<V> nextEntry;
/*     */     final int hash;
/*     */     
/*     */     Entry(int p_i1553_1_, long p_i1553_2_, V p_i1553_4_, Entry<V> p_i1553_5_) {
/* 259 */       this.value = p_i1553_4_;
/* 260 */       this.nextEntry = p_i1553_5_;
/* 261 */       this.key = p_i1553_2_;
/* 262 */       this.hash = p_i1553_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public final long getKey() {
/* 267 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public final V getValue() {
/* 272 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean equals(Object p_equals_1_) {
/* 277 */       if (!(p_equals_1_ instanceof Entry))
/*     */       {
/* 279 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 283 */       Entry<V> entry = (Entry<V>)p_equals_1_;
/* 284 */       Object object = Long.valueOf(getKey());
/* 285 */       Object object1 = Long.valueOf(entry.getKey());
/*     */       
/* 287 */       if (object == object1 || (object != null && object.equals(object1))) {
/*     */         
/* 289 */         Object object2 = getValue();
/* 290 */         Object object3 = entry.getValue();
/*     */         
/* 292 */         if (object2 == object3 || (object2 != null && object2.equals(object3)))
/*     */         {
/* 294 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 298 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final int hashCode() {
/* 304 */       return LongHashMap.getHashedKey(this.key);
/*     */     }
/*     */ 
/*     */     
/*     */     public final String toString() {
/* 309 */       return getKey() + "=" + getValue();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraf\\util\LongHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
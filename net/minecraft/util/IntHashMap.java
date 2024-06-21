/*     */ package net.minecraft.util;
/*     */ 
/*     */ public class IntHashMap<V>
/*     */ {
/*   5 */   private transient Entry<V>[] slots = (Entry<V>[])new Entry[16];
/*     */ 
/*     */   
/*     */   private transient int count;
/*     */ 
/*     */   
/*  11 */   private int threshold = 12;
/*     */ 
/*     */   
/*  14 */   private final float growFactor = 0.75F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int computeHash(int integer) {
/*  23 */     integer = integer ^ integer >>> 20 ^ integer >>> 12;
/*  24 */     return integer ^ integer >>> 7 ^ integer >>> 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getSlotIndex(int hash, int slotCount) {
/*  32 */     return hash & slotCount - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V lookup(int p_76041_1_) {
/*  40 */     int i = computeHash(p_76041_1_);
/*     */     
/*  42 */     for (Entry<V> entry = this.slots[getSlotIndex(i, this.slots.length)]; entry != null; entry = entry.nextEntry) {
/*     */       
/*  44 */       if (entry.hashEntry == p_76041_1_)
/*     */       {
/*  46 */         return entry.valueEntry;
/*     */       }
/*     */     } 
/*     */     
/*  50 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsItem(int p_76037_1_) {
/*  58 */     return (lookupEntry(p_76037_1_) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   final Entry<V> lookupEntry(int p_76045_1_) {
/*  63 */     int i = computeHash(p_76045_1_);
/*     */     
/*  65 */     for (Entry<V> entry = this.slots[getSlotIndex(i, this.slots.length)]; entry != null; entry = entry.nextEntry) {
/*     */       
/*  67 */       if (entry.hashEntry == p_76045_1_)
/*     */       {
/*  69 */         return entry;
/*     */       }
/*     */     } 
/*     */     
/*  73 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addKey(int p_76038_1_, V p_76038_2_) {
/*  81 */     int i = computeHash(p_76038_1_);
/*  82 */     int j = getSlotIndex(i, this.slots.length);
/*     */     
/*  84 */     for (Entry<V> entry = this.slots[j]; entry != null; entry = entry.nextEntry) {
/*     */       
/*  86 */       if (entry.hashEntry == p_76038_1_) {
/*     */         
/*  88 */         entry.valueEntry = p_76038_2_;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  93 */     insert(i, p_76038_1_, p_76038_2_, j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void grow(int p_76047_1_) {
/* 101 */     Entry<V>[] entry = this.slots;
/* 102 */     int i = entry.length;
/*     */     
/* 104 */     if (i == 1073741824) {
/*     */       
/* 106 */       this.threshold = Integer.MAX_VALUE;
/*     */     }
/*     */     else {
/*     */       
/* 110 */       Entry[] arrayOfEntry = new Entry[p_76047_1_];
/* 111 */       copyTo((Entry<V>[])arrayOfEntry);
/* 112 */       this.slots = (Entry<V>[])arrayOfEntry;
/* 113 */       getClass(); this.threshold = (int)(p_76047_1_ * 0.75F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void copyTo(Entry<V>[] p_76048_1_) {
/* 122 */     Entry<V>[] entry = this.slots;
/* 123 */     int i = p_76048_1_.length;
/*     */     
/* 125 */     for (int j = 0; j < entry.length; j++) {
/*     */       
/* 127 */       Entry<V> entry1 = entry[j];
/*     */       
/* 129 */       if (entry1 != null) {
/*     */         Entry<V> entry2;
/* 131 */         entry[j] = null;
/*     */ 
/*     */         
/*     */         do {
/* 135 */           entry2 = entry1.nextEntry;
/* 136 */           int k = getSlotIndex(entry1.slotHash, i);
/* 137 */           entry1.nextEntry = p_76048_1_[k];
/* 138 */           p_76048_1_[k] = entry1;
/* 139 */           entry1 = entry2;
/*     */         }
/* 141 */         while (entry2 != null);
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
/*     */   public V removeObject(int p_76049_1_) {
/* 155 */     Entry<V> entry = removeEntry(p_76049_1_);
/* 156 */     return (entry == null) ? null : entry.valueEntry;
/*     */   }
/*     */ 
/*     */   
/*     */   final Entry<V> removeEntry(int p_76036_1_) {
/* 161 */     int i = computeHash(p_76036_1_);
/* 162 */     int j = getSlotIndex(i, this.slots.length);
/* 163 */     Entry<V> entry = this.slots[j];
/*     */     
/*     */     Entry<V> entry1;
/*     */     
/* 167 */     for (entry1 = entry; entry1 != null; entry1 = entry2) {
/*     */       
/* 169 */       Entry<V> entry2 = entry1.nextEntry;
/*     */       
/* 171 */       if (entry1.hashEntry == p_76036_1_) {
/*     */         
/* 173 */         this.count--;
/*     */         
/* 175 */         if (entry == entry1) {
/*     */           
/* 177 */           this.slots[j] = entry2;
/*     */         }
/*     */         else {
/*     */           
/* 181 */           entry.nextEntry = entry2;
/*     */         } 
/*     */         
/* 184 */         return entry1;
/*     */       } 
/*     */       
/* 187 */       entry = entry1;
/*     */     } 
/*     */     
/* 190 */     return entry1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearMap() {
/* 198 */     Entry<V>[] entry = this.slots;
/*     */     
/* 200 */     for (int i = 0; i < entry.length; i++)
/*     */     {
/* 202 */       entry[i] = null;
/*     */     }
/*     */     
/* 205 */     this.count = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void insert(int p_76040_1_, int p_76040_2_, V p_76040_3_, int p_76040_4_) {
/* 213 */     Entry<V> entry = this.slots[p_76040_4_];
/* 214 */     this.slots[p_76040_4_] = new Entry<>(p_76040_1_, p_76040_2_, p_76040_3_, entry);
/*     */     
/* 216 */     if (this.count++ >= this.threshold)
/*     */     {
/* 218 */       grow(2 * this.slots.length);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Entry<V>
/*     */   {
/*     */     final int hashEntry;
/*     */     V valueEntry;
/*     */     Entry<V> nextEntry;
/*     */     final int slotHash;
/*     */     
/*     */     Entry(int p_i1552_1_, int p_i1552_2_, V p_i1552_3_, Entry<V> p_i1552_4_) {
/* 231 */       this.valueEntry = p_i1552_3_;
/* 232 */       this.nextEntry = p_i1552_4_;
/* 233 */       this.hashEntry = p_i1552_2_;
/* 234 */       this.slotHash = p_i1552_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public final int getHash() {
/* 239 */       return this.hashEntry;
/*     */     }
/*     */ 
/*     */     
/*     */     public final V getValue() {
/* 244 */       return this.valueEntry;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean equals(Object p_equals_1_) {
/* 249 */       if (!(p_equals_1_ instanceof Entry))
/*     */       {
/* 251 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 255 */       Entry<V> entry = (Entry<V>)p_equals_1_;
/* 256 */       Object object = Integer.valueOf(getHash());
/* 257 */       Object object1 = Integer.valueOf(entry.getHash());
/*     */       
/* 259 */       if (object == object1 || (object != null && object.equals(object1))) {
/*     */         
/* 261 */         Object object2 = getValue();
/* 262 */         Object object3 = entry.getValue();
/*     */         
/* 264 */         if (object2 == object3 || (object2 != null && object2.equals(object3)))
/*     */         {
/* 266 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 270 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final int hashCode() {
/* 276 */       return IntHashMap.computeHash(this.hashEntry);
/*     */     }
/*     */ 
/*     */     
/*     */     public final String toString() {
/* 281 */       return getHash() + "=" + getValue();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraf\\util\IntHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
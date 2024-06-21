/*     */ package net.optifine;
/*     */ 
/*     */ import com.google.common.collect.Iterators;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.LongHashMap;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.NextTickListEntry;
/*     */ 
/*     */ public class NextTickHashSet
/*     */   extends TreeSet {
/*  17 */   private LongHashMap longHashMap = new LongHashMap();
/*  18 */   private int minX = Integer.MIN_VALUE;
/*  19 */   private int minZ = Integer.MIN_VALUE;
/*  20 */   private int maxX = Integer.MIN_VALUE;
/*  21 */   private int maxZ = Integer.MIN_VALUE;
/*     */   
/*     */   private static final int UNDEFINED = -2147483648;
/*     */   
/*     */   public NextTickHashSet(Set oldSet) {
/*  26 */     for (Object object : oldSet)
/*     */     {
/*  28 */       add(object);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object obj) {
/*  34 */     if (!(obj instanceof NextTickListEntry))
/*     */     {
/*  36 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  40 */     NextTickListEntry nextticklistentry = (NextTickListEntry)obj;
/*  41 */     Set set = getSubSet(nextticklistentry, false);
/*  42 */     return (set == null) ? false : set.contains(nextticklistentry);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(Object obj) {
/*  48 */     if (!(obj instanceof NextTickListEntry))
/*     */     {
/*  50 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  54 */     NextTickListEntry nextticklistentry = (NextTickListEntry)obj;
/*     */     
/*  56 */     if (nextticklistentry == null)
/*     */     {
/*  58 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  62 */     Set<NextTickListEntry> set = getSubSet(nextticklistentry, true);
/*  63 */     boolean flag = set.add(nextticklistentry);
/*  64 */     boolean flag1 = super.add(obj);
/*     */     
/*  66 */     if (flag != flag1)
/*     */     {
/*  68 */       throw new IllegalStateException("Added: " + flag + ", addedParent: " + flag1);
/*     */     }
/*     */ 
/*     */     
/*  72 */     return flag1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Object obj) {
/*  80 */     if (!(obj instanceof NextTickListEntry))
/*     */     {
/*  82 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  86 */     NextTickListEntry nextticklistentry = (NextTickListEntry)obj;
/*  87 */     Set set = getSubSet(nextticklistentry, false);
/*     */     
/*  89 */     if (set == null)
/*     */     {
/*  91 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  95 */     boolean flag = set.remove(nextticklistentry);
/*  96 */     boolean flag1 = super.remove(nextticklistentry);
/*     */     
/*  98 */     if (flag != flag1)
/*     */     {
/* 100 */       throw new IllegalStateException("Added: " + flag + ", addedParent: " + flag1);
/*     */     }
/*     */ 
/*     */     
/* 104 */     return flag1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Set getSubSet(NextTickListEntry entry, boolean autoCreate) {
/* 112 */     if (entry == null)
/*     */     {
/* 114 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 118 */     BlockPos blockpos = entry.position;
/* 119 */     int i = blockpos.getX() >> 4;
/* 120 */     int j = blockpos.getZ() >> 4;
/* 121 */     return getSubSet(i, j, autoCreate);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Set getSubSet(int cx, int cz, boolean autoCreate) {
/* 127 */     long i = ChunkCoordIntPair.chunkXZ2Int(cx, cz);
/* 128 */     HashSet hashset = (HashSet)this.longHashMap.getValueByKey(i);
/*     */     
/* 130 */     if (hashset == null && autoCreate) {
/*     */       
/* 132 */       hashset = new HashSet();
/* 133 */       this.longHashMap.add(i, hashset);
/*     */     } 
/*     */     
/* 136 */     return hashset;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator iterator() {
/* 141 */     if (this.minX == Integer.MIN_VALUE)
/*     */     {
/* 143 */       return super.iterator();
/*     */     }
/* 145 */     if (size() <= 0)
/*     */     {
/* 147 */       return (Iterator)Iterators.emptyIterator();
/*     */     }
/*     */ 
/*     */     
/* 151 */     int i = this.minX >> 4;
/* 152 */     int j = this.minZ >> 4;
/* 153 */     int k = this.maxX >> 4;
/* 154 */     int l = this.maxZ >> 4;
/* 155 */     List<Iterator> list = new ArrayList();
/*     */     
/* 157 */     for (int i1 = i; i1 <= k; i1++) {
/*     */       
/* 159 */       for (int j1 = j; j1 <= l; j1++) {
/*     */         
/* 161 */         Set set = getSubSet(i1, j1, false);
/*     */         
/* 163 */         if (set != null)
/*     */         {
/* 165 */           list.add(set.iterator());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 170 */     if (list.size() <= 0)
/*     */     {
/* 172 */       return (Iterator)Iterators.emptyIterator();
/*     */     }
/* 174 */     if (list.size() == 1)
/*     */     {
/* 176 */       return list.get(0);
/*     */     }
/*     */ 
/*     */     
/* 180 */     return Iterators.concat(list.iterator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIteratorLimits(int minX, int minZ, int maxX, int maxZ) {
/* 187 */     this.minX = Math.min(minX, maxX);
/* 188 */     this.minZ = Math.min(minZ, maxZ);
/* 189 */     this.maxX = Math.max(minX, maxX);
/* 190 */     this.maxZ = Math.max(minZ, maxZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearIteratorLimits() {
/* 195 */     this.minX = Integer.MIN_VALUE;
/* 196 */     this.minZ = Integer.MIN_VALUE;
/* 197 */     this.maxX = Integer.MIN_VALUE;
/* 198 */     this.maxZ = Integer.MIN_VALUE;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\NextTickHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
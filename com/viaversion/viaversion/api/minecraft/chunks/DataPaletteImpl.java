/*     */ package com.viaversion.viaversion.api.minecraft.chunks;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntList;
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
/*     */ public final class DataPaletteImpl
/*     */   implements DataPalette
/*     */ {
/*     */   private final IntList palette;
/*     */   private final Int2IntMap inversePalette;
/*     */   private final int[] values;
/*     */   private final int sizeBits;
/*     */   
/*     */   public DataPaletteImpl(int valuesLength) {
/*  38 */     this(valuesLength, 8);
/*     */   }
/*     */   
/*     */   public DataPaletteImpl(int valuesLength, int expectedPaletteLength) {
/*  42 */     this.values = new int[valuesLength];
/*  43 */     this.sizeBits = Integer.numberOfTrailingZeros(valuesLength) / 3;
/*     */     
/*  45 */     this.palette = (IntList)new IntArrayList(expectedPaletteLength);
/*  46 */     this.inversePalette = (Int2IntMap)new Int2IntOpenHashMap(expectedPaletteLength);
/*  47 */     this.inversePalette.defaultReturnValue(-1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int index(int x, int y, int z) {
/*  52 */     return (y << this.sizeBits | z) << this.sizeBits | x;
/*     */   }
/*     */ 
/*     */   
/*     */   public int idAt(int sectionCoordinate) {
/*  57 */     int index = this.values[sectionCoordinate];
/*  58 */     return this.palette.getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIdAt(int sectionCoordinate, int id) {
/*  63 */     int index = this.inversePalette.get(id);
/*  64 */     if (index == -1) {
/*  65 */       index = this.palette.size();
/*  66 */       this.palette.add(id);
/*  67 */       this.inversePalette.put(id, index);
/*     */     } 
/*     */     
/*  70 */     this.values[sectionCoordinate] = index;
/*     */   }
/*     */ 
/*     */   
/*     */   public int paletteIndexAt(int packedCoordinate) {
/*  75 */     return this.values[packedCoordinate];
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPaletteIndexAt(int sectionCoordinate, int index) {
/*  80 */     this.values[sectionCoordinate] = index;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  85 */     return this.palette.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public int idByIndex(int index) {
/*  90 */     return this.palette.getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIdByIndex(int index, int id) {
/*  95 */     int oldId = this.palette.set(index, id);
/*  96 */     if (oldId == id)
/*     */       return; 
/*  98 */     this.inversePalette.put(id, index);
/*  99 */     if (this.inversePalette.get(oldId) == index) {
/* 100 */       this.inversePalette.remove(oldId);
/* 101 */       for (int i = 0; i < this.palette.size(); i++) {
/* 102 */         if (this.palette.getInt(i) == oldId) {
/* 103 */           this.inversePalette.put(oldId, i);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void replaceId(int oldId, int newId) {
/* 112 */     int index = this.inversePalette.remove(oldId);
/* 113 */     if (index == -1)
/*     */       return; 
/* 115 */     this.inversePalette.put(newId, index);
/* 116 */     for (int i = 0; i < this.palette.size(); i++) {
/* 117 */       if (this.palette.getInt(i) == oldId) {
/* 118 */         this.palette.set(i, newId);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addId(int id) {
/* 125 */     this.inversePalette.put(id, this.palette.size());
/* 126 */     this.palette.add(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 131 */     this.palette.clear();
/* 132 */     this.inversePalette.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\chunks\DataPaletteImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
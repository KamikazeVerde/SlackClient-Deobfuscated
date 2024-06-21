/*     */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class BlockConnectionStorage
/*     */   implements StorableObject
/*     */ {
/*     */   private static Constructor<?> fastUtilLongObjectHashMap;
/*  31 */   private final Map<Long, SectionData> blockStorage = createLongObjectMap();
/*     */   
/*     */   private Long lastIndex;
/*     */   
/*     */   private SectionData lastSection;
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  40 */       String className = "it" + ".unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap";
/*  41 */       fastUtilLongObjectHashMap = Class.forName(className).getConstructor(new Class[0]);
/*  42 */       Via.getPlatform().getLogger().info("Using FastUtil Long2ObjectOpenHashMap for block connections");
/*  43 */     } catch (ClassNotFoundException|NoSuchMethodException classNotFoundException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init() {}
/*     */ 
/*     */   
/*     */   public void store(int x, int y, int z, int blockState) {
/*  51 */     long index = getChunkSectionIndex(x, y, z);
/*  52 */     SectionData section = getSection(index);
/*  53 */     if (section == null) {
/*  54 */       if (blockState == 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  59 */       this.blockStorage.put(Long.valueOf(index), section = new SectionData());
/*  60 */       this.lastSection = section;
/*  61 */       this.lastIndex = Long.valueOf(index);
/*     */     } 
/*     */     
/*  64 */     section.setBlockAt(x, y, z, blockState);
/*     */   }
/*     */   
/*     */   public int get(int x, int y, int z) {
/*  68 */     long pair = getChunkSectionIndex(x, y, z);
/*  69 */     SectionData section = getSection(pair);
/*  70 */     if (section == null) {
/*  71 */       return 0;
/*     */     }
/*     */     
/*  74 */     return section.blockAt(x, y, z);
/*     */   }
/*     */   
/*     */   public void remove(int x, int y, int z) {
/*  78 */     long index = getChunkSectionIndex(x, y, z);
/*  79 */     SectionData section = getSection(index);
/*  80 */     if (section == null) {
/*     */       return;
/*     */     }
/*     */     
/*  84 */     section.setBlockAt(x, y, z, 0);
/*     */     
/*  86 */     if (section.nonEmptyBlocks() == 0) {
/*  87 */       removeSection(index);
/*     */     }
/*     */   }
/*     */   
/*     */   public void clear() {
/*  92 */     this.blockStorage.clear();
/*  93 */     this.lastSection = null;
/*  94 */     this.lastIndex = null;
/*     */   }
/*     */   
/*     */   public void unloadChunk(int x, int z) {
/*  98 */     for (int y = 0; y < 16; y++) {
/*  99 */       unloadSection(x, y, z);
/*     */     }
/*     */   }
/*     */   
/*     */   public void unloadSection(int x, int y, int z) {
/* 104 */     removeSection(getChunkSectionIndex(x << 4, y << 4, z << 4));
/*     */   }
/*     */   
/*     */   private SectionData getSection(long index) {
/* 108 */     if (this.lastIndex != null && this.lastIndex.longValue() == index) {
/* 109 */       return this.lastSection;
/*     */     }
/* 111 */     this.lastIndex = Long.valueOf(index);
/* 112 */     return this.lastSection = this.blockStorage.get(Long.valueOf(index));
/*     */   }
/*     */   
/*     */   private void removeSection(long index) {
/* 116 */     this.blockStorage.remove(Long.valueOf(index));
/* 117 */     if (this.lastIndex != null && this.lastIndex.longValue() == index) {
/* 118 */       this.lastIndex = null;
/* 119 */       this.lastSection = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static long getChunkSectionIndex(int x, int y, int z) {
/* 124 */     return ((x >> 4) & 0x3FFFFFFL) << 38L | ((y >> 4) & 0xFFFL) << 26L | (z >> 4) & 0x3FFFFFFL;
/*     */   }
/*     */   
/*     */   private <T> Map<Long, T> createLongObjectMap() {
/* 128 */     if (fastUtilLongObjectHashMap != null) {
/*     */       
/*     */       try {
/* 131 */         return (Map<Long, T>)fastUtilLongObjectHashMap.newInstance(new Object[0]);
/* 132 */       } catch (IllegalAccessException|InstantiationException|java.lang.reflect.InvocationTargetException e) {
/* 133 */         e.printStackTrace();
/*     */       } 
/*     */     }
/* 136 */     return new HashMap<>();
/*     */   }
/*     */   
/*     */   private static final class SectionData {
/* 140 */     private final short[] blockStates = new short[4096];
/*     */     private short nonEmptyBlocks;
/*     */     
/*     */     public int blockAt(int x, int y, int z) {
/* 144 */       return this.blockStates[encodeBlockPos(x, y, z)];
/*     */     }
/*     */     
/*     */     public void setBlockAt(int x, int y, int z, int blockState) {
/* 148 */       int index = encodeBlockPos(x, y, z);
/* 149 */       if (blockState == this.blockStates[index]) {
/*     */         return;
/*     */       }
/*     */       
/* 153 */       this.blockStates[index] = (short)blockState;
/* 154 */       if (blockState == 0) {
/* 155 */         this.nonEmptyBlocks = (short)(this.nonEmptyBlocks - 1);
/*     */       } else {
/* 157 */         this.nonEmptyBlocks = (short)(this.nonEmptyBlocks + 1);
/*     */       } 
/*     */     }
/*     */     
/*     */     public short nonEmptyBlocks() {
/* 162 */       return this.nonEmptyBlocks;
/*     */     }
/*     */     
/*     */     private static int encodeBlockPos(int x, int y, int z) {
/* 166 */       return (y & 0xF) << 8 | (x & 0xF) << 4 | z & 0xF;
/*     */     }
/*     */     
/*     */     private SectionData() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\storage\BlockConnectionStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
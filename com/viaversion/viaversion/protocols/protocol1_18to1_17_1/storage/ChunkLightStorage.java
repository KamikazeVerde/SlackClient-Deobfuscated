/*     */ package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.storage;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
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
/*     */ public final class ChunkLightStorage
/*     */   implements StorableObject
/*     */ {
/*  29 */   private final Map<Long, ChunkLight> lightPackets = new HashMap<>();
/*  30 */   private final Set<Long> loadedChunks = new HashSet<>();
/*     */   
/*     */   public void storeLight(int x, int z, ChunkLight chunkLight) {
/*  33 */     this.lightPackets.put(Long.valueOf(getChunkSectionIndex(x, z)), chunkLight);
/*     */   }
/*     */   
/*     */   public ChunkLight removeLight(int x, int z) {
/*  37 */     return this.lightPackets.remove(Long.valueOf(getChunkSectionIndex(x, z)));
/*     */   }
/*     */   
/*     */   public ChunkLight getLight(int x, int z) {
/*  41 */     return this.lightPackets.get(Long.valueOf(getChunkSectionIndex(x, z)));
/*     */   }
/*     */   
/*     */   public boolean addLoadedChunk(int x, int z) {
/*  45 */     return this.loadedChunks.add(Long.valueOf(getChunkSectionIndex(x, z)));
/*     */   }
/*     */   
/*     */   public boolean isLoaded(int x, int z) {
/*  49 */     return this.loadedChunks.contains(Long.valueOf(getChunkSectionIndex(x, z)));
/*     */   }
/*     */   
/*     */   public void clear(int x, int z) {
/*  53 */     long index = getChunkSectionIndex(x, z);
/*  54 */     this.lightPackets.remove(Long.valueOf(index));
/*  55 */     this.loadedChunks.remove(Long.valueOf(index));
/*     */   }
/*     */   
/*     */   public void clear() {
/*  59 */     this.loadedChunks.clear();
/*  60 */     this.lightPackets.clear();
/*     */   }
/*     */   
/*     */   private long getChunkSectionIndex(int x, int z) {
/*  64 */     return (x & 0x3FFFFFFL) << 38L | z & 0x3FFFFFFL;
/*     */   }
/*     */   
/*     */   public static final class ChunkLight
/*     */   {
/*     */     private final boolean trustEdges;
/*     */     private final long[] skyLightMask;
/*     */     private final long[] blockLightMask;
/*     */     private final long[] emptySkyLightMask;
/*     */     private final long[] emptyBlockLightMask;
/*     */     private final byte[][] skyLight;
/*     */     private final byte[][] blockLight;
/*     */     
/*     */     public ChunkLight(boolean trustEdges, long[] skyLightMask, long[] blockLightMask, long[] emptySkyLightMask, long[] emptyBlockLightMask, byte[][] skyLight, byte[][] blockLight) {
/*  78 */       this.trustEdges = trustEdges;
/*  79 */       this.skyLightMask = skyLightMask;
/*  80 */       this.emptySkyLightMask = emptySkyLightMask;
/*  81 */       this.blockLightMask = blockLightMask;
/*  82 */       this.emptyBlockLightMask = emptyBlockLightMask;
/*  83 */       this.skyLight = skyLight;
/*  84 */       this.blockLight = blockLight;
/*     */     }
/*     */     
/*     */     public boolean trustEdges() {
/*  88 */       return this.trustEdges;
/*     */     }
/*     */     
/*     */     public long[] skyLightMask() {
/*  92 */       return this.skyLightMask;
/*     */     }
/*     */     
/*     */     public long[] emptySkyLightMask() {
/*  96 */       return this.emptySkyLightMask;
/*     */     }
/*     */     
/*     */     public long[] blockLightMask() {
/* 100 */       return this.blockLightMask;
/*     */     }
/*     */     
/*     */     public long[] emptyBlockLightMask() {
/* 104 */       return this.emptyBlockLightMask;
/*     */     }
/*     */     
/*     */     public byte[][] skyLight() {
/* 108 */       return this.skyLight;
/*     */     }
/*     */     
/*     */     public byte[][] blockLight() {
/* 112 */       return this.blockLight;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_18to1_17_1\storage\ChunkLightStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
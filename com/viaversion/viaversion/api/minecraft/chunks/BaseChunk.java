/*     */ package com.viaversion.viaversion.api.minecraft.chunks;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
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
/*     */ public class BaseChunk
/*     */   implements Chunk
/*     */ {
/*     */   protected final int x;
/*     */   protected final int z;
/*     */   protected final boolean fullChunk;
/*     */   protected boolean ignoreOldLightData;
/*     */   protected BitSet chunkSectionBitSet;
/*     */   protected int bitmask;
/*     */   protected ChunkSection[] sections;
/*     */   protected int[] biomeData;
/*     */   protected CompoundTag heightMap;
/*     */   protected final List<CompoundTag> blockEntities;
/*     */   
/*     */   public BaseChunk(int x, int z, boolean fullChunk, boolean ignoreOldLightData, BitSet chunkSectionBitSet, ChunkSection[] sections, int[] biomeData, CompoundTag heightMap, List<CompoundTag> blockEntities) {
/*  46 */     this.x = x;
/*  47 */     this.z = z;
/*  48 */     this.fullChunk = fullChunk;
/*  49 */     this.ignoreOldLightData = ignoreOldLightData;
/*  50 */     this.chunkSectionBitSet = chunkSectionBitSet;
/*  51 */     this.sections = sections;
/*  52 */     this.biomeData = biomeData;
/*  53 */     this.heightMap = heightMap;
/*  54 */     this.blockEntities = blockEntities;
/*     */   }
/*     */   
/*     */   public BaseChunk(int x, int z, boolean fullChunk, boolean ignoreOldLightData, int bitmask, ChunkSection[] sections, int[] biomeData, CompoundTag heightMap, List<CompoundTag> blockEntities) {
/*  58 */     this(x, z, fullChunk, ignoreOldLightData, (BitSet)null, sections, biomeData, heightMap, blockEntities);
/*  59 */     this.bitmask = bitmask;
/*     */   }
/*     */   
/*     */   public BaseChunk(int x, int z, boolean fullChunk, boolean ignoreOldLightData, int bitmask, ChunkSection[] sections, int[] biomeData, List<CompoundTag> blockEntities) {
/*  63 */     this(x, z, fullChunk, ignoreOldLightData, bitmask, sections, biomeData, (CompoundTag)null, blockEntities);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBiomeData() {
/*  68 */     return (this.biomeData != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getX() {
/*  73 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getZ() {
/*  78 */     return this.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullChunk() {
/*  83 */     return this.fullChunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIgnoreOldLightData() {
/*  88 */     return this.ignoreOldLightData;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIgnoreOldLightData(boolean ignoreOldLightData) {
/*  93 */     this.ignoreOldLightData = ignoreOldLightData;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBitmask() {
/*  98 */     return this.bitmask;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBitmask(int bitmask) {
/* 103 */     this.bitmask = bitmask;
/*     */   }
/*     */ 
/*     */   
/*     */   public BitSet getChunkMask() {
/* 108 */     return this.chunkSectionBitSet;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChunkMask(BitSet chunkSectionMask) {
/* 113 */     this.chunkSectionBitSet = chunkSectionMask;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkSection[] getSections() {
/* 118 */     return this.sections;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSections(ChunkSection[] sections) {
/* 123 */     this.sections = sections;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getBiomeData() {
/* 128 */     return this.biomeData;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBiomeData(int[] biomeData) {
/* 133 */     this.biomeData = biomeData;
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag getHeightMap() {
/* 138 */     return this.heightMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHeightMap(CompoundTag heightMap) {
/* 143 */     this.heightMap = heightMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<CompoundTag> getBlockEntities() {
/* 148 */     return this.blockEntities;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BlockEntity> blockEntities() {
/* 153 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\chunks\BaseChunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
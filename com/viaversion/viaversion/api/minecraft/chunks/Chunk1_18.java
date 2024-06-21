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
/*     */ public class Chunk1_18
/*     */   implements Chunk
/*     */ {
/*     */   protected final int x;
/*     */   protected final int z;
/*     */   protected ChunkSection[] sections;
/*     */   protected CompoundTag heightMap;
/*     */   protected final List<BlockEntity> blockEntities;
/*     */   
/*     */   public Chunk1_18(int x, int z, ChunkSection[] sections, CompoundTag heightMap, List<BlockEntity> blockEntities) {
/*  39 */     this.x = x;
/*  40 */     this.z = z;
/*  41 */     this.sections = sections;
/*  42 */     this.heightMap = heightMap;
/*  43 */     this.blockEntities = blockEntities;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBiomeData() {
/*  48 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getX() {
/*  53 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getZ() {
/*  58 */     return this.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullChunk() {
/*  63 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIgnoreOldLightData() {
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIgnoreOldLightData(boolean ignoreOldLightData) {
/*  73 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBitmask() {
/*  78 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBitmask(int bitmask) {
/*  83 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public BitSet getChunkMask() {
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChunkMask(BitSet chunkSectionMask) {
/*  93 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkSection[] getSections() {
/*  98 */     return this.sections;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSections(ChunkSection[] sections) {
/* 103 */     this.sections = sections;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getBiomeData() {
/* 108 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBiomeData(int[] biomeData) {
/* 113 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag getHeightMap() {
/* 118 */     return this.heightMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHeightMap(CompoundTag heightMap) {
/* 123 */     this.heightMap = heightMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<CompoundTag> getBlockEntities() {
/* 128 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BlockEntity> blockEntities() {
/* 133 */     return this.blockEntities;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\chunks\Chunk1_18.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viaversion.api.minecraft.chunks;
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
/*     */ 
/*     */ 
/*     */ public interface ChunkSection
/*     */ {
/*     */   public static final int SIZE = 4096;
/*     */   public static final int BIOME_SIZE = 64;
/*     */   
/*     */   static int index(int x, int y, int z) {
/*  40 */     return y << 8 | z << 4 | x;
/*     */   }
/*     */   
/*     */   static int xFromIndex(int idx) {
/*  44 */     return idx & 0xF;
/*     */   }
/*     */   
/*     */   static int yFromIndex(int idx) {
/*  48 */     return idx >> 8 & 0xF;
/*     */   }
/*     */   
/*     */   static int zFromIndex(int idx) {
/*  52 */     return idx >> 4 & 0xF;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default int getFlatBlock(int idx) {
/*  57 */     return palette(PaletteType.BLOCKS).idAt(idx);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default int getFlatBlock(int x, int y, int z) {
/*  62 */     return getFlatBlock(index(x, y, z));
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void setFlatBlock(int idx, int id) {
/*  67 */     palette(PaletteType.BLOCKS).setIdAt(idx, id);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void setFlatBlock(int x, int y, int z, int id) {
/*  72 */     setFlatBlock(index(x, y, z), id);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default int getBlockWithoutData(int x, int y, int z) {
/*  77 */     return getFlatBlock(x, y, z) >> 4;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default int getBlockData(int x, int y, int z) {
/*  82 */     return getFlatBlock(x, y, z) & 0xF;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void setBlockWithData(int x, int y, int z, int type, int data) {
/*  87 */     setFlatBlock(index(x, y, z), type << 4 | data & 0xF);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void setBlockWithData(int idx, int type, int data) {
/*  92 */     setFlatBlock(idx, type << 4 | data & 0xF);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void setPaletteIndex(int idx, int index) {
/*  97 */     palette(PaletteType.BLOCKS).setPaletteIndexAt(idx, index);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default int getPaletteIndex(int idx) {
/* 102 */     return palette(PaletteType.BLOCKS).paletteIndexAt(idx);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default int getPaletteSize() {
/* 107 */     return palette(PaletteType.BLOCKS).size();
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default int getPaletteEntry(int index) {
/* 112 */     return palette(PaletteType.BLOCKS).idByIndex(index);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void setPaletteEntry(int index, int id) {
/* 117 */     palette(PaletteType.BLOCKS).setIdByIndex(index, id);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void replacePaletteEntry(int oldId, int newId) {
/* 122 */     palette(PaletteType.BLOCKS).replaceId(oldId, newId);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void addPaletteEntry(int id) {
/* 127 */     palette(PaletteType.BLOCKS).addId(id);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void clearPalette() {
/* 132 */     palette(PaletteType.BLOCKS).clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getNonAirBlocksCount();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setNonAirBlocksCount(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean hasLight() {
/* 151 */     return (getLight() != null);
/*     */   }
/*     */   
/*     */   ChunkSectionLight getLight();
/*     */   
/*     */   void setLight(ChunkSectionLight paramChunkSectionLight);
/*     */   
/*     */   DataPalette palette(PaletteType paramPaletteType);
/*     */   
/*     */   void addPalette(PaletteType paramPaletteType, DataPalette paramDataPalette);
/*     */   
/*     */   void removePalette(PaletteType paramPaletteType);
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\chunks\ChunkSection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
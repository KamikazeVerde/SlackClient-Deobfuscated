/*     */ package com.viaversion.viaversion.api.type.types.version;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.util.CompactArrayUtil;
/*     */ import io.netty.buffer.ByteBuf;
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
/*     */ public class ChunkSectionType1_16
/*     */   extends Type<ChunkSection>
/*     */ {
/*     */   private static final int GLOBAL_PALETTE = 15;
/*     */   
/*     */   public ChunkSectionType1_16() {
/*  37 */     super("Chunk Section Type", ChunkSection.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkSection read(ByteBuf buffer) throws Exception {
/*     */     ChunkSectionImpl chunkSectionImpl;
/*  43 */     int bitsPerBlock = buffer.readUnsignedByte();
/*  44 */     if (bitsPerBlock > 8) {
/*  45 */       bitsPerBlock = 15;
/*  46 */     } else if (bitsPerBlock < 4) {
/*  47 */       bitsPerBlock = 4;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  52 */     if (bitsPerBlock != 15) {
/*  53 */       int paletteLength = Type.VAR_INT.readPrimitive(buffer);
/*  54 */       chunkSectionImpl = new ChunkSectionImpl(false, paletteLength);
/*  55 */       DataPalette blockPalette = chunkSectionImpl.palette(PaletteType.BLOCKS);
/*  56 */       for (int i = 0; i < paletteLength; i++) {
/*  57 */         blockPalette.addId(Type.VAR_INT.readPrimitive(buffer));
/*     */       }
/*     */     } else {
/*  60 */       chunkSectionImpl = new ChunkSectionImpl(false);
/*     */     } 
/*     */ 
/*     */     
/*  64 */     long[] blockData = (long[])Type.LONG_ARRAY_PRIMITIVE.read(buffer);
/*  65 */     if (blockData.length > 0) {
/*  66 */       char valuesPerLong = (char)(64 / bitsPerBlock);
/*  67 */       int expectedLength = (4096 + valuesPerLong - 1) / valuesPerLong;
/*  68 */       if (blockData.length == expectedLength) {
/*  69 */         DataPalette blockPalette = chunkSectionImpl.palette(PaletteType.BLOCKS);
/*  70 */         CompactArrayUtil.iterateCompactArrayWithPadding(bitsPerBlock, 4096, blockData, (bitsPerBlock == 15) ? blockPalette::setIdAt : blockPalette::setPaletteIndexAt);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  75 */     return (ChunkSection)chunkSectionImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ByteBuf buffer, ChunkSection chunkSection) throws Exception {
/*  80 */     int bitsPerBlock = 4;
/*  81 */     DataPalette blockPalette = chunkSection.palette(PaletteType.BLOCKS);
/*  82 */     while (blockPalette.size() > 1 << bitsPerBlock) {
/*  83 */       bitsPerBlock++;
/*     */     }
/*     */     
/*  86 */     if (bitsPerBlock > 8) {
/*  87 */       bitsPerBlock = 15;
/*     */     }
/*     */     
/*  90 */     buffer.writeByte(bitsPerBlock);
/*     */ 
/*     */     
/*  93 */     if (bitsPerBlock != 15) {
/*  94 */       Type.VAR_INT.writePrimitive(buffer, blockPalette.size());
/*  95 */       for (int i = 0; i < blockPalette.size(); i++) {
/*  96 */         Type.VAR_INT.writePrimitive(buffer, blockPalette.idByIndex(i));
/*     */       }
/*     */     } 
/*     */     
/* 100 */     long[] data = CompactArrayUtil.createCompactArrayWithPadding(bitsPerBlock, 4096, (bitsPerBlock == 15) ? blockPalette::idAt : blockPalette::paletteIndexAt);
/*     */     
/* 102 */     Type.LONG_ARRAY_PRIMITIVE.write(buffer, data);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\version\ChunkSectionType1_16.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
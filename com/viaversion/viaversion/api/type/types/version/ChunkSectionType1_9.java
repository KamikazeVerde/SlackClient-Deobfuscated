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
/*     */ public class ChunkSectionType1_9
/*     */   extends Type<ChunkSection>
/*     */ {
/*     */   private static final int GLOBAL_PALETTE = 13;
/*     */   
/*     */   public ChunkSectionType1_9() {
/*  37 */     super("Chunk Section Type", ChunkSection.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkSection read(ByteBuf buffer) throws Exception {
/*  43 */     int bitsPerBlock = buffer.readUnsignedByte();
/*  44 */     if (bitsPerBlock < 4) {
/*  45 */       bitsPerBlock = 4;
/*     */     }
/*  47 */     if (bitsPerBlock > 8) {
/*  48 */       bitsPerBlock = 13;
/*     */     }
/*     */ 
/*     */     
/*  52 */     int paletteLength = Type.VAR_INT.readPrimitive(buffer);
/*  53 */     ChunkSectionImpl chunkSectionImpl = (bitsPerBlock != 13) ? new ChunkSectionImpl(true, paletteLength) : new ChunkSectionImpl(true);
/*  54 */     DataPalette blockPalette = chunkSectionImpl.palette(PaletteType.BLOCKS);
/*  55 */     for (int i = 0; i < paletteLength; i++) {
/*  56 */       if (bitsPerBlock != 13) {
/*  57 */         blockPalette.addId(Type.VAR_INT.readPrimitive(buffer));
/*     */       } else {
/*  59 */         Type.VAR_INT.readPrimitive(buffer);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  64 */     long[] blockData = (long[])Type.LONG_ARRAY_PRIMITIVE.read(buffer);
/*  65 */     if (blockData.length > 0) {
/*  66 */       int expectedLength = (int)Math.ceil((4096 * bitsPerBlock) / 64.0D);
/*  67 */       if (blockData.length == expectedLength) {
/*  68 */         CompactArrayUtil.iterateCompactArray(bitsPerBlock, 4096, blockData, (bitsPerBlock == 13) ? blockPalette::setIdAt : blockPalette::setPaletteIndexAt);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  73 */     return (ChunkSection)chunkSectionImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ByteBuf buffer, ChunkSection chunkSection) throws Exception {
/*  78 */     int bitsPerBlock = 4;
/*  79 */     DataPalette blockPalette = chunkSection.palette(PaletteType.BLOCKS);
/*  80 */     while (blockPalette.size() > 1 << bitsPerBlock) {
/*  81 */       bitsPerBlock++;
/*     */     }
/*     */     
/*  84 */     if (bitsPerBlock > 8) {
/*  85 */       bitsPerBlock = 13;
/*     */     }
/*     */     
/*  88 */     buffer.writeByte(bitsPerBlock);
/*     */ 
/*     */     
/*  91 */     if (bitsPerBlock != 13) {
/*  92 */       Type.VAR_INT.writePrimitive(buffer, blockPalette.size());
/*  93 */       for (int i = 0; i < blockPalette.size(); i++) {
/*  94 */         Type.VAR_INT.writePrimitive(buffer, blockPalette.idByIndex(i));
/*     */       }
/*     */     } else {
/*  97 */       Type.VAR_INT.writePrimitive(buffer, 0);
/*     */     } 
/*     */     
/* 100 */     long[] data = CompactArrayUtil.createCompactArray(bitsPerBlock, 4096, (bitsPerBlock == 13) ? blockPalette::idAt : blockPalette::paletteIndexAt);
/*     */     
/* 102 */     Type.LONG_ARRAY_PRIMITIVE.write(buffer, data);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\version\ChunkSectionType1_9.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
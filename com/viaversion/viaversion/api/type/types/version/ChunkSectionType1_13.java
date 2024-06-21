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
/*     */ public class ChunkSectionType1_13
/*     */   extends Type<ChunkSection>
/*     */ {
/*     */   private static final int GLOBAL_PALETTE = 14;
/*     */   
/*     */   public ChunkSectionType1_13() {
/*  37 */     super("Chunk Section Type", ChunkSection.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkSection read(ByteBuf buffer) throws Exception {
/*     */     ChunkSectionImpl chunkSectionImpl;
/*  43 */     int bitsPerBlock = buffer.readUnsignedByte();
/*  44 */     if (bitsPerBlock > 8) {
/*  45 */       bitsPerBlock = 14;
/*  46 */     } else if (bitsPerBlock < 4) {
/*  47 */       bitsPerBlock = 4;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  52 */     if (bitsPerBlock != 14) {
/*  53 */       int paletteLength = Type.VAR_INT.readPrimitive(buffer);
/*  54 */       chunkSectionImpl = new ChunkSectionImpl(true, paletteLength);
/*  55 */       DataPalette blockPalette = chunkSectionImpl.palette(PaletteType.BLOCKS);
/*  56 */       for (int i = 0; i < paletteLength; i++) {
/*  57 */         blockPalette.addId(Type.VAR_INT.readPrimitive(buffer));
/*     */       }
/*     */     } else {
/*  60 */       chunkSectionImpl = new ChunkSectionImpl(true);
/*     */     } 
/*     */ 
/*     */     
/*  64 */     long[] blockData = (long[])Type.LONG_ARRAY_PRIMITIVE.read(buffer);
/*  65 */     if (blockData.length > 0) {
/*  66 */       int expectedLength = (int)Math.ceil((4096 * bitsPerBlock) / 64.0D);
/*  67 */       if (blockData.length == expectedLength) {
/*  68 */         DataPalette blockPalette = chunkSectionImpl.palette(PaletteType.BLOCKS);
/*  69 */         CompactArrayUtil.iterateCompactArray(bitsPerBlock, 4096, blockData, (bitsPerBlock == 14) ? blockPalette::setIdAt : blockPalette::setPaletteIndexAt);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  74 */     return (ChunkSection)chunkSectionImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ByteBuf buffer, ChunkSection chunkSection) throws Exception {
/*  79 */     int bitsPerBlock = 4;
/*  80 */     DataPalette blockPalette = chunkSection.palette(PaletteType.BLOCKS);
/*  81 */     while (blockPalette.size() > 1 << bitsPerBlock) {
/*  82 */       bitsPerBlock++;
/*     */     }
/*     */     
/*  85 */     if (bitsPerBlock > 8) {
/*  86 */       bitsPerBlock = 14;
/*     */     }
/*     */     
/*  89 */     buffer.writeByte(bitsPerBlock);
/*     */ 
/*     */     
/*  92 */     if (bitsPerBlock != 14) {
/*  93 */       Type.VAR_INT.writePrimitive(buffer, blockPalette.size());
/*  94 */       for (int i = 0; i < blockPalette.size(); i++) {
/*  95 */         Type.VAR_INT.writePrimitive(buffer, blockPalette.idByIndex(i));
/*     */       }
/*     */     } 
/*     */     
/*  99 */     long[] data = CompactArrayUtil.createCompactArray(bitsPerBlock, 4096, (bitsPerBlock == 14) ? blockPalette::idAt : blockPalette::paletteIndexAt);
/*     */     
/* 101 */     Type.LONG_ARRAY_PRIMITIVE.write(buffer, data);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\version\ChunkSectionType1_13.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
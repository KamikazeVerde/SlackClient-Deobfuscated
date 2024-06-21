/*     */ package com.viaversion.viaversion.api.type.types.version;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPaletteImpl;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.util.CompactArrayUtil;
/*     */ import com.viaversion.viaversion.util.MathUtil;
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
/*     */ public final class PaletteType1_18
/*     */   extends Type<DataPalette>
/*     */ {
/*     */   private final int globalPaletteBits;
/*     */   private final PaletteType type;
/*     */   
/*     */   public PaletteType1_18(PaletteType type, int globalPaletteBits) {
/*  38 */     super(DataPalette.class);
/*  39 */     this.globalPaletteBits = globalPaletteBits;
/*  40 */     this.type = type;
/*     */   }
/*     */   
/*     */   public DataPalette read(ByteBuf buffer) throws Exception {
/*     */     DataPaletteImpl palette;
/*  45 */     int originalBitsPerValue = buffer.readByte();
/*  46 */     int bitsPerValue = originalBitsPerValue;
/*     */ 
/*     */     
/*  49 */     if (bitsPerValue == 0) {
/*     */       
/*  51 */       palette = new DataPaletteImpl(this.type.size(), 1);
/*  52 */       palette.addId(Type.VAR_INT.readPrimitive(buffer));
/*  53 */       Type.LONG_ARRAY_PRIMITIVE.read(buffer);
/*  54 */       return (DataPalette)palette;
/*     */     } 
/*     */     
/*  57 */     if (bitsPerValue < 0 || bitsPerValue > this.type.highestBitsPerValue()) {
/*  58 */       bitsPerValue = this.globalPaletteBits;
/*  59 */     } else if (this.type == PaletteType.BLOCKS && bitsPerValue < 4) {
/*  60 */       bitsPerValue = 4;
/*     */     } 
/*     */ 
/*     */     
/*  64 */     if (bitsPerValue != this.globalPaletteBits) {
/*  65 */       int paletteLength = Type.VAR_INT.readPrimitive(buffer);
/*  66 */       palette = new DataPaletteImpl(this.type.size(), paletteLength);
/*  67 */       for (int i = 0; i < paletteLength; i++) {
/*  68 */         palette.addId(Type.VAR_INT.readPrimitive(buffer));
/*     */       }
/*     */     } else {
/*  71 */       palette = new DataPaletteImpl(this.type.size());
/*     */     } 
/*     */ 
/*     */     
/*  75 */     long[] values = (long[])Type.LONG_ARRAY_PRIMITIVE.read(buffer);
/*  76 */     if (values.length > 0) {
/*  77 */       int valuesPerLong = (char)(64 / bitsPerValue);
/*  78 */       int expectedLength = (this.type.size() + valuesPerLong - 1) / valuesPerLong;
/*  79 */       if (values.length == expectedLength) {
/*  80 */         CompactArrayUtil.iterateCompactArrayWithPadding(bitsPerValue, this.type.size(), values, (bitsPerValue == this.globalPaletteBits) ? palette::setIdAt : palette::setPaletteIndexAt);
/*     */       }
/*     */     } 
/*     */     
/*  84 */     return (DataPalette)palette;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ByteBuf buffer, DataPalette palette) throws Exception {
/*  89 */     int size = palette.size();
/*  90 */     if (size == 1) {
/*     */       
/*  92 */       buffer.writeByte(0);
/*  93 */       Type.VAR_INT.writePrimitive(buffer, palette.idByIndex(0));
/*  94 */       Type.VAR_INT.writePrimitive(buffer, 0);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  99 */     int min = (this.type == PaletteType.BLOCKS) ? 4 : 1;
/* 100 */     int bitsPerValue = Math.max(min, MathUtil.ceilLog2(size));
/* 101 */     if (bitsPerValue > this.type.highestBitsPerValue()) {
/* 102 */       bitsPerValue = this.globalPaletteBits;
/*     */     }
/*     */     
/* 105 */     buffer.writeByte(bitsPerValue);
/*     */     
/* 107 */     if (bitsPerValue != this.globalPaletteBits) {
/*     */       
/* 109 */       Type.VAR_INT.writePrimitive(buffer, size);
/* 110 */       for (int i = 0; i < size; i++) {
/* 111 */         Type.VAR_INT.writePrimitive(buffer, palette.idByIndex(i));
/*     */       }
/*     */     } 
/*     */     
/* 115 */     Type.LONG_ARRAY_PRIMITIVE.write(buffer, CompactArrayUtil.createCompactArrayWithPadding(bitsPerValue, this.type.size(), (bitsPerValue == this.globalPaletteBits) ? palette::idAt : palette::paletteIndexAt));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\version\PaletteType1_18.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
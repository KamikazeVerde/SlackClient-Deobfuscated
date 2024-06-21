/*    */ package com.viaversion.viaversion.api.type.types.version;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ChunkSectionType1_18
/*    */   extends Type<ChunkSection>
/*    */ {
/*    */   private final PaletteType1_18 blockPaletteType;
/*    */   private final PaletteType1_18 biomePaletteType;
/*    */   
/*    */   public ChunkSectionType1_18(int globalPaletteBlockBits, int globalPaletteBiomeBits) {
/* 37 */     super("Chunk Section Type", ChunkSection.class);
/* 38 */     this.blockPaletteType = new PaletteType1_18(PaletteType.BLOCKS, globalPaletteBlockBits);
/* 39 */     this.biomePaletteType = new PaletteType1_18(PaletteType.BIOMES, globalPaletteBiomeBits);
/*    */   }
/*    */ 
/*    */   
/*    */   public ChunkSection read(ByteBuf buffer) throws Exception {
/* 44 */     ChunkSectionImpl chunkSectionImpl = new ChunkSectionImpl();
/* 45 */     chunkSectionImpl.setNonAirBlocksCount(buffer.readShort());
/* 46 */     chunkSectionImpl.addPalette(PaletteType.BLOCKS, this.blockPaletteType.read(buffer));
/* 47 */     chunkSectionImpl.addPalette(PaletteType.BIOMES, this.biomePaletteType.read(buffer));
/* 48 */     return (ChunkSection)chunkSectionImpl;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, ChunkSection section) throws Exception {
/* 53 */     buffer.writeShort(section.getNonAirBlocksCount());
/* 54 */     this.blockPaletteType.write(buffer, section.palette(PaletteType.BLOCKS));
/* 55 */     this.biomePaletteType.write(buffer, section.palette(PaletteType.BIOMES));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\version\ChunkSectionType1_18.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
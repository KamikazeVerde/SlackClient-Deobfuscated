/*    */ package com.viaversion.viaversion.api.type.types.version;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.nio.ByteOrder;
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
/*    */ public class ChunkSectionType1_8
/*    */   extends Type<ChunkSection>
/*    */ {
/*    */   public ChunkSectionType1_8() {
/* 36 */     super("Chunk Section Type", ChunkSection.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public ChunkSection read(ByteBuf buffer) throws Exception {
/* 41 */     ChunkSectionImpl chunkSectionImpl = new ChunkSectionImpl(true);
/* 42 */     DataPalette blocks = chunkSectionImpl.palette(PaletteType.BLOCKS);
/*    */ 
/*    */     
/* 45 */     blocks.addId(0);
/*    */     
/* 47 */     ByteBuf littleEndianView = buffer.order(ByteOrder.LITTLE_ENDIAN);
/* 48 */     for (int idx = 0; idx < 4096; idx++) {
/* 49 */       blocks.setIdAt(idx, littleEndianView.readShort());
/*    */     }
/*    */     
/* 52 */     return (ChunkSection)chunkSectionImpl;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, ChunkSection chunkSection) throws Exception {
/* 57 */     DataPalette blocks = chunkSection.palette(PaletteType.BLOCKS);
/*    */     
/* 59 */     ByteBuf littleEndianView = buffer.order(ByteOrder.LITTLE_ENDIAN);
/* 60 */     for (int idx = 0; idx < 4096; idx++)
/* 61 */       littleEndianView.writeShort(blocks.idAt(idx)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\version\ChunkSectionType1_8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.Environment;
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*    */ import com.viaversion.viaversion.api.type.PartialType;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.util.zip.DeflaterOutputStream;
/*    */ 
/*    */ public class Chunk1_7_10Type
/*    */   extends PartialType<Chunk, ClientWorld>
/*    */ {
/*    */   public Chunk1_7_10Type(ClientWorld param) {
/* 19 */     super(param, Chunk.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Chunk read(ByteBuf byteBuf, ClientWorld clientWorld) throws Exception {
/* 24 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf output, ClientWorld clientWorld, Chunk chunk) throws Exception {
/* 29 */     output.writeInt(chunk.getX());
/* 30 */     output.writeInt(chunk.getZ());
/* 31 */     output.writeBoolean(chunk.isFullChunk());
/* 32 */     output.writeShort(chunk.getBitmask());
/* 33 */     output.writeShort(0);
/*    */     
/* 35 */     ByteBuf dataToCompress = output.alloc().buffer(); try {
/*    */       int i;
/* 37 */       for (i = 0; i < (chunk.getSections()).length; i++) {
/* 38 */         if ((chunk.getBitmask() & 1 << i) != 0) {
/* 39 */           ChunkSection section = chunk.getSections()[i];
/* 40 */           DataPalette palette = section.palette(PaletteType.BLOCKS);
/* 41 */           for (int j = 0; j < 4096; j++) {
/* 42 */             int block = palette.idAt(j);
/* 43 */             dataToCompress.writeByte(block >> 4);
/*    */           } 
/*    */         } 
/*    */       } 
/* 47 */       for (i = 0; i < (chunk.getSections()).length; i++) {
/* 48 */         if ((chunk.getBitmask() & 1 << i) != 0) {
/* 49 */           ChunkSection section = chunk.getSections()[i];
/* 50 */           DataPalette palette = section.palette(PaletteType.BLOCKS);
/* 51 */           for (int j = 0; j < 4096; j += 2) {
/* 52 */             int data0 = palette.idAt(j) & 0xF;
/* 53 */             int data1 = palette.idAt(j + 1) & 0xF;
/*    */             
/* 55 */             dataToCompress.writeByte(data1 << 4 | data0);
/*    */           } 
/*    */         } 
/*    */       } 
/* 59 */       for (i = 0; i < (chunk.getSections()).length; i++) {
/* 60 */         if ((chunk.getBitmask() & 1 << i) != 0) {
/* 61 */           chunk.getSections()[i].getLight().writeBlockLight(dataToCompress);
/*    */         }
/*    */       } 
/* 64 */       boolean skyLight = (clientWorld != null && clientWorld.getEnvironment() == Environment.NORMAL);
/* 65 */       if (skyLight) {
/* 66 */         for (int j = 0; j < (chunk.getSections()).length; j++) {
/* 67 */           if ((chunk.getBitmask() & 1 << j) != 0) {
/* 68 */             chunk.getSections()[j].getLight().writeSkyLight(dataToCompress);
/*    */           }
/*    */         } 
/*    */       }
/* 72 */       if (chunk.isFullChunk() && chunk.isBiomeData()) {
/* 73 */         for (int biome : chunk.getBiomeData()) {
/* 74 */           dataToCompress.writeByte(biome);
/*    */         }
/*    */       }
/*    */       
/* 78 */       ByteArrayOutputStream compressedStream = new ByteArrayOutputStream();
/*    */       
/* 80 */       DeflaterOutputStream compressorStream = new DeflaterOutputStream(compressedStream); 
/* 81 */       try { compressorStream.write((byte[])Type.REMAINING_BYTES.read(dataToCompress));
/* 82 */         compressorStream.close(); } catch (Throwable throwable) { try { compressorStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */          throw throwable; }
/* 84 */        output.writeInt(compressedStream.size());
/* 85 */       output.writeBytes(compressedStream.toByteArray());
/*    */     } finally {
/* 87 */       dataToCompress.release();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\types\Chunk1_7_10Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
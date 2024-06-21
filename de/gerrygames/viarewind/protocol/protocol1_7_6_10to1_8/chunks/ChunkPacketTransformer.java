/*     */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.chunks;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.CustomByteType;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_8;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ReplacementRegistry1_7_6_10to1_8;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.util.zip.DeflaterOutputStream;
/*     */ 
/*     */ 
/*     */ public class ChunkPacketTransformer
/*     */ {
/*     */   private static byte[] transformChunkData(byte[] data, int primaryBitMask, boolean skyLight, boolean groundUp) throws Exception {
/*  21 */     ByteBuf inputData = Unpooled.wrappedBuffer(data);
/*     */     
/*  23 */     ByteBuf finalBuf = ByteBufAllocator.DEFAULT.buffer();
/*     */     try {
/*  25 */       ChunkSection[] sections = new ChunkSection[16]; int i;
/*  26 */       for (i = 0; i < 16; i++) {
/*  27 */         if ((primaryBitMask & 1 << i) != 0) {
/*  28 */           sections[i] = (ChunkSection)Types1_8.CHUNK_SECTION.read(inputData);
/*     */         }
/*     */       } 
/*  31 */       for (i = 0; i < 16; i++) {
/*  32 */         if ((primaryBitMask & 1 << i) != 0) {
/*  33 */           ChunkSection section = sections[i];
/*  34 */           DataPalette palette = section.palette(PaletteType.BLOCKS);
/*  35 */           for (int k = 0; k < palette.size(); k++) {
/*  36 */             int blockData = palette.idByIndex(k);
/*     */             
/*  38 */             blockData = ReplacementRegistry1_7_6_10to1_8.replace(blockData);
/*     */             
/*  40 */             palette.setIdByIndex(k, blockData);
/*     */           } 
/*     */         } 
/*     */       } 
/*  44 */       for (i = 0; i < 16; i++) {
/*  45 */         if ((primaryBitMask & 1 << i) != 0) {
/*  46 */           ChunkSection section = sections[i];
/*  47 */           DataPalette palette = section.palette(PaletteType.BLOCKS);
/*  48 */           for (int j = 0; j < 4096; j++) {
/*  49 */             int raw = palette.idAt(j);
/*  50 */             finalBuf.writeByte(raw >> 4);
/*     */           } 
/*     */         } 
/*     */       } 
/*  54 */       for (i = 0; i < 16; i++) {
/*  55 */         if ((primaryBitMask & 1 << i) != 0) {
/*  56 */           ChunkSection section = sections[i];
/*  57 */           DataPalette palette = section.palette(PaletteType.BLOCKS);
/*  58 */           for (int j = 0; j < 4096; j += 2) {
/*  59 */             int meta0 = palette.idAt(j) & 0xF;
/*  60 */             int meta1 = palette.idAt(j + 1) & 0xF;
/*     */             
/*  62 */             finalBuf.writeByte(meta1 << 4 | meta0);
/*     */           } 
/*     */         } 
/*     */       } 
/*  66 */       int columnCount = Integer.bitCount(primaryBitMask);
/*     */ 
/*     */       
/*  69 */       finalBuf.writeBytes(inputData, 2048 * columnCount);
/*     */ 
/*     */       
/*  72 */       if (skyLight) {
/*  73 */         finalBuf.writeBytes(inputData, 2048 * columnCount);
/*     */       }
/*     */       
/*  76 */       if (groundUp && inputData.isReadable(256)) {
/*  77 */         finalBuf.writeBytes(inputData, 256);
/*     */       }
/*     */       
/*  80 */       return (byte[])Type.REMAINING_BYTES.read(finalBuf);
/*     */     } finally {
/*  82 */       finalBuf.release();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int calcSize(int i, boolean hasSkyLight, boolean hasBiome) {
/*  87 */     int blocks = i * 2 * 16 * 16 * 16;
/*  88 */     int blockLight = i * 16 * 16 * 16 / 2;
/*  89 */     int skyLight = hasSkyLight ? (i * 16 * 16 * 16 / 2) : 0;
/*  90 */     int biome = hasBiome ? 256 : 0;
/*     */     
/*  92 */     return blocks + blockLight + skyLight + biome;
/*     */   }
/*     */   
/*     */   public static void transformChunkBulk(PacketWrapper packetWrapper) throws Exception {
/*  96 */     boolean skyLightSent = ((Boolean)packetWrapper.read((Type)Type.BOOLEAN)).booleanValue();
/*  97 */     int columnCount = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*  98 */     int[] chunkX = new int[columnCount];
/*  99 */     int[] chunkZ = new int[columnCount];
/* 100 */     int[] primaryBitMask = new int[columnCount];
/* 101 */     byte[][] data = new byte[columnCount][];
/*     */     int i;
/* 103 */     for (i = 0; i < columnCount; i++) {
/* 104 */       chunkX[i] = ((Integer)packetWrapper.read((Type)Type.INT)).intValue();
/* 105 */       chunkZ[i] = ((Integer)packetWrapper.read((Type)Type.INT)).intValue();
/* 106 */       primaryBitMask[i] = ((Integer)packetWrapper.read((Type)Type.UNSIGNED_SHORT)).intValue();
/*     */     } 
/*     */     
/* 109 */     for (i = 0; i < columnCount; i++) {
/* 110 */       int size = calcSize(Integer.bitCount(primaryBitMask[i]), skyLightSent, true);
/* 111 */       CustomByteType customByteType1 = new CustomByteType(Integer.valueOf(size));
/* 112 */       data[i] = transformChunkData((byte[])packetWrapper.read((Type)customByteType1), primaryBitMask[i], skyLightSent, true);
/*     */     } 
/*     */     
/* 115 */     ByteArrayOutputStream compressedData = new ByteArrayOutputStream();
/*     */     
/* 117 */     DeflaterOutputStream deflaterStream = new DeflaterOutputStream(compressedData); 
/* 118 */     try { for (int k = 0; k < columnCount; k++) {
/* 119 */         deflaterStream.write(data[k]);
/*     */       }
/* 121 */       deflaterStream.close(); } catch (Throwable throwable) { try { deflaterStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 123 */      packetWrapper.write((Type)Type.SHORT, Short.valueOf((short)columnCount));
/* 124 */     packetWrapper.write((Type)Type.INT, Integer.valueOf(compressedData.size()));
/* 125 */     packetWrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(skyLightSent));
/*     */     
/* 127 */     CustomByteType customByteType = new CustomByteType(Integer.valueOf(compressedData.size()));
/* 128 */     packetWrapper.write((Type)customByteType, compressedData.toByteArray());
/*     */     
/* 130 */     for (int j = 0; j < columnCount; j++) {
/* 131 */       packetWrapper.write((Type)Type.INT, Integer.valueOf(chunkX[j]));
/* 132 */       packetWrapper.write((Type)Type.INT, Integer.valueOf(chunkZ[j]));
/* 133 */       packetWrapper.write((Type)Type.SHORT, Short.valueOf((short)primaryBitMask[j]));
/* 134 */       packetWrapper.write((Type)Type.SHORT, Short.valueOf((short)0));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\chunks\ChunkPacketTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
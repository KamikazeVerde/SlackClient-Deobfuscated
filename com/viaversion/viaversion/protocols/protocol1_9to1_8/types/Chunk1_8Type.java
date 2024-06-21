/*     */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.types;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.Environment;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.type.PartialType;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.util.ArrayList;
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
/*     */ public class Chunk1_8Type
/*     */   extends PartialType<Chunk, ClientWorld>
/*     */ {
/*     */   public Chunk1_8Type(ClientWorld param) {
/*  36 */     super(param, Chunk.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Type> getBaseClass() {
/*  41 */     return (Class)BaseChunkType.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk read(ByteBuf input, ClientWorld world) throws Exception {
/*  46 */     int chunkX = input.readInt();
/*  47 */     int chunkZ = input.readInt();
/*  48 */     boolean fullChunk = input.readBoolean();
/*  49 */     int bitmask = input.readUnsignedShort();
/*  50 */     int dataLength = Type.VAR_INT.readPrimitive(input);
/*  51 */     byte[] data = new byte[dataLength];
/*  52 */     input.readBytes(data);
/*     */ 
/*     */     
/*  55 */     if (fullChunk && bitmask == 0) {
/*  56 */       return (Chunk)new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], null, new ArrayList());
/*     */     }
/*     */     
/*  59 */     return deserialize(chunkX, chunkZ, fullChunk, (world.getEnvironment() == Environment.NORMAL), bitmask, data);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ByteBuf output, ClientWorld world, Chunk chunk) throws Exception {
/*  64 */     output.writeInt(chunk.getX());
/*  65 */     output.writeInt(chunk.getZ());
/*  66 */     output.writeBoolean(chunk.isFullChunk());
/*  67 */     output.writeShort(chunk.getBitmask());
/*  68 */     byte[] data = serialize(chunk);
/*  69 */     Type.VAR_INT.writePrimitive(output, data.length);
/*  70 */     output.writeBytes(data);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Chunk deserialize(int chunkX, int chunkZ, boolean fullChunk, boolean skyLight, int bitmask, byte[] data) throws Exception {
/*  75 */     ByteBuf input = Unpooled.wrappedBuffer(data);
/*     */     
/*  77 */     ChunkSection[] sections = new ChunkSection[16];
/*  78 */     int[] biomeData = null;
/*     */     
/*     */     int i;
/*  81 */     for (i = 0; i < sections.length; i++) {
/*  82 */       if ((bitmask & 1 << i) != 0) {
/*  83 */         sections[i] = (ChunkSection)Types1_8.CHUNK_SECTION.read(input);
/*     */       }
/*     */     } 
/*     */     
/*  87 */     for (i = 0; i < sections.length; i++) {
/*  88 */       if ((bitmask & 1 << i) != 0) {
/*  89 */         sections[i].getLight().readBlockLight(input);
/*     */       }
/*     */     } 
/*     */     
/*  93 */     if (skyLight) {
/*  94 */       for (i = 0; i < sections.length; i++) {
/*  95 */         if ((bitmask & 1 << i) != 0) {
/*  96 */           sections[i].getLight().readSkyLight(input);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 101 */     if (fullChunk) {
/* 102 */       biomeData = new int[256];
/* 103 */       for (i = 0; i < 256; i++) {
/* 104 */         biomeData[i] = input.readUnsignedByte();
/*     */       }
/*     */     } 
/* 107 */     input.release();
/*     */     
/* 109 */     return (Chunk)new BaseChunk(chunkX, chunkZ, fullChunk, false, bitmask, sections, biomeData, new ArrayList());
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] serialize(Chunk chunk) throws Exception {
/* 114 */     ByteBuf output = Unpooled.buffer();
/*     */     
/*     */     int i;
/* 117 */     for (i = 0; i < (chunk.getSections()).length; i++) {
/* 118 */       if ((chunk.getBitmask() & 1 << i) != 0) {
/* 119 */         Types1_8.CHUNK_SECTION.write(output, chunk.getSections()[i]);
/*     */       }
/*     */     } 
/*     */     
/* 123 */     for (i = 0; i < (chunk.getSections()).length; i++) {
/* 124 */       if ((chunk.getBitmask() & 1 << i) != 0) {
/* 125 */         chunk.getSections()[i].getLight().writeBlockLight(output);
/*     */       }
/*     */     } 
/*     */     
/* 129 */     for (i = 0; i < (chunk.getSections()).length; i++) {
/* 130 */       if ((chunk.getBitmask() & 1 << i) != 0 && 
/* 131 */         chunk.getSections()[i].getLight().hasSkyLight()) {
/* 132 */         chunk.getSections()[i].getLight().writeSkyLight(output);
/*     */       }
/*     */     } 
/*     */     
/* 136 */     if (chunk.isFullChunk() && chunk.getBiomeData() != null) {
/* 137 */       for (int biome : chunk.getBiomeData()) {
/* 138 */         output.writeByte((byte)biome);
/*     */       }
/*     */     }
/* 141 */     byte[] data = new byte[output.readableBytes()];
/* 142 */     output.readBytes(data);
/* 143 */     output.release();
/*     */     
/* 145 */     return data;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\types\Chunk1_8Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
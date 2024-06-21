/*     */ package com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.Environment;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.type.PartialType;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
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
/*     */ public class Chunk1_9_1_2Type
/*     */   extends PartialType<Chunk, ClientWorld>
/*     */ {
/*     */   public Chunk1_9_1_2Type(ClientWorld clientWorld) {
/*  36 */     super(clientWorld, Chunk.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk read(ByteBuf input, ClientWorld world) throws Exception {
/*  41 */     int chunkX = input.readInt();
/*  42 */     int chunkZ = input.readInt();
/*     */     
/*  44 */     boolean groundUp = input.readBoolean();
/*  45 */     int primaryBitmask = Type.VAR_INT.readPrimitive(input);
/*     */     
/*  47 */     Type.VAR_INT.readPrimitive(input);
/*     */     
/*  49 */     BitSet usedSections = new BitSet(16);
/*  50 */     ChunkSection[] sections = new ChunkSection[16];
/*     */     int i;
/*  52 */     for (i = 0; i < 16; i++) {
/*  53 */       if ((primaryBitmask & 1 << i) != 0) {
/*  54 */         usedSections.set(i);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  59 */     for (i = 0; i < 16; i++) {
/*  60 */       if (usedSections.get(i)) {
/*  61 */         ChunkSection section = (ChunkSection)Types1_9.CHUNK_SECTION.read(input);
/*  62 */         sections[i] = section;
/*  63 */         section.getLight().readBlockLight(input);
/*  64 */         if (world.getEnvironment() == Environment.NORMAL) {
/*  65 */           section.getLight().readSkyLight(input);
/*     */         }
/*     */       } 
/*     */     } 
/*  69 */     int[] biomeData = groundUp ? new int[256] : null;
/*  70 */     if (groundUp) {
/*  71 */       for (int j = 0; j < 256; j++) {
/*  72 */         biomeData[j] = input.readByte() & 0xFF;
/*     */       }
/*     */     }
/*     */     
/*  76 */     return (Chunk)new BaseChunk(chunkX, chunkZ, groundUp, false, primaryBitmask, sections, biomeData, new ArrayList());
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ByteBuf output, ClientWorld world, Chunk chunk) throws Exception {
/*  81 */     output.writeInt(chunk.getX());
/*  82 */     output.writeInt(chunk.getZ());
/*     */     
/*  84 */     output.writeBoolean(chunk.isFullChunk());
/*  85 */     Type.VAR_INT.writePrimitive(output, chunk.getBitmask());
/*     */     
/*  87 */     ByteBuf buf = output.alloc().buffer();
/*     */     try {
/*  89 */       for (int i = 0; i < 16; i++) {
/*  90 */         ChunkSection section = chunk.getSections()[i];
/*  91 */         if (section != null) {
/*  92 */           Types1_9.CHUNK_SECTION.write(buf, section);
/*  93 */           section.getLight().writeBlockLight(buf);
/*     */           
/*  95 */           if (section.getLight().hasSkyLight())
/*  96 */             section.getLight().writeSkyLight(buf); 
/*     */         } 
/*     */       } 
/*  99 */       buf.readerIndex(0);
/* 100 */       Type.VAR_INT.writePrimitive(output, buf.readableBytes() + (chunk.isBiomeData() ? 256 : 0));
/* 101 */       output.writeBytes(buf);
/*     */     } finally {
/* 103 */       buf.release();
/*     */     } 
/*     */ 
/*     */     
/* 107 */     if (chunk.isBiomeData()) {
/* 108 */       for (int biome : chunk.getBiomeData()) {
/* 109 */         output.writeByte((byte)biome);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Type> getBaseClass() {
/* 116 */     return (Class)BaseChunkType.class;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9_3to1_9_1_2\types\Chunk1_9_1_2Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
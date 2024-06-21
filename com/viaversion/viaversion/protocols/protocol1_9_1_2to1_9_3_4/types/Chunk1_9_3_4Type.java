/*     */ package com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.Environment;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.type.PartialType;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_9;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
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
/*     */ public class Chunk1_9_3_4Type
/*     */   extends PartialType<Chunk, ClientWorld>
/*     */ {
/*     */   public Chunk1_9_3_4Type(ClientWorld param) {
/*  39 */     super(param, Chunk.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk read(ByteBuf input, ClientWorld world) throws Exception {
/*  44 */     int chunkX = input.readInt();
/*  45 */     int chunkZ = input.readInt();
/*     */     
/*  47 */     boolean fullChunk = input.readBoolean();
/*  48 */     int primaryBitmask = Type.VAR_INT.readPrimitive(input);
/*  49 */     Type.VAR_INT.readPrimitive(input);
/*     */ 
/*     */     
/*  52 */     ChunkSection[] sections = new ChunkSection[16];
/*  53 */     for (int i = 0; i < 16; i++) {
/*  54 */       if ((primaryBitmask & 1 << i) != 0) {
/*     */         
/*  56 */         ChunkSection section = (ChunkSection)Types1_9.CHUNK_SECTION.read(input);
/*  57 */         sections[i] = section;
/*  58 */         section.getLight().readBlockLight(input);
/*  59 */         if (world.getEnvironment() == Environment.NORMAL) {
/*  60 */           section.getLight().readSkyLight(input);
/*     */         }
/*     */       } 
/*     */     } 
/*  64 */     int[] biomeData = fullChunk ? new int[256] : null;
/*  65 */     if (fullChunk) {
/*  66 */       for (int j = 0; j < 256; j++) {
/*  67 */         biomeData[j] = input.readByte() & 0xFF;
/*     */       }
/*     */     }
/*     */     
/*  71 */     List<CompoundTag> nbtData = new ArrayList<>(Arrays.asList((Object[])Type.NBT_ARRAY.read(input)));
/*     */ 
/*     */     
/*  74 */     if (input.readableBytes() > 0) {
/*  75 */       byte[] array = (byte[])Type.REMAINING_BYTES.read(input);
/*  76 */       if (Via.getManager().isDebug()) {
/*  77 */         Via.getPlatform().getLogger().warning("Found " + array.length + " more bytes than expected while reading the chunk: " + chunkX + "/" + chunkZ);
/*     */       }
/*     */     } 
/*     */     
/*  81 */     return (Chunk)new BaseChunk(chunkX, chunkZ, fullChunk, false, primaryBitmask, sections, biomeData, nbtData);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ByteBuf output, ClientWorld world, Chunk chunk) throws Exception {
/*  86 */     output.writeInt(chunk.getX());
/*  87 */     output.writeInt(chunk.getZ());
/*     */     
/*  89 */     output.writeBoolean(chunk.isFullChunk());
/*  90 */     Type.VAR_INT.writePrimitive(output, chunk.getBitmask());
/*     */     
/*  92 */     ByteBuf buf = output.alloc().buffer();
/*     */     try {
/*  94 */       for (int i = 0; i < 16; i++) {
/*  95 */         ChunkSection section = chunk.getSections()[i];
/*  96 */         if (section != null) {
/*  97 */           Types1_9.CHUNK_SECTION.write(buf, section);
/*  98 */           section.getLight().writeBlockLight(buf);
/*     */           
/* 100 */           if (section.getLight().hasSkyLight())
/* 101 */             section.getLight().writeSkyLight(buf); 
/*     */         } 
/* 103 */       }  buf.readerIndex(0);
/* 104 */       Type.VAR_INT.writePrimitive(output, buf.readableBytes() + (chunk.isBiomeData() ? 256 : 0));
/* 105 */       output.writeBytes(buf);
/*     */     } finally {
/* 107 */       buf.release();
/*     */     } 
/*     */ 
/*     */     
/* 111 */     if (chunk.isBiomeData()) {
/* 112 */       for (int biome : chunk.getBiomeData()) {
/* 113 */         output.writeByte((byte)biome);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 118 */     Type.NBT_ARRAY.write(output, chunk.getBlockEntities().toArray((Object[])new CompoundTag[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Type> getBaseClass() {
/* 123 */     return (Class)BaseChunkType.class;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9_1_2to1_9_3_4\types\Chunk1_9_3_4Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
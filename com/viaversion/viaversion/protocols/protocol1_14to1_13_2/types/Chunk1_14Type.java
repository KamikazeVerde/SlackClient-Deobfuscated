/*     */ package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_13;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
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
/*     */ public class Chunk1_14Type
/*     */   extends Type<Chunk>
/*     */ {
/*     */   public Chunk1_14Type() {
/*  36 */     super(Chunk.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk read(ByteBuf input) throws Exception {
/*  41 */     int chunkX = input.readInt();
/*  42 */     int chunkZ = input.readInt();
/*     */     
/*  44 */     boolean fullChunk = input.readBoolean();
/*  45 */     int primaryBitmask = Type.VAR_INT.readPrimitive(input);
/*  46 */     CompoundTag heightMap = (CompoundTag)Type.NBT.read(input);
/*     */     
/*  48 */     Type.VAR_INT.readPrimitive(input);
/*     */ 
/*     */     
/*  51 */     ChunkSection[] sections = new ChunkSection[16];
/*  52 */     for (int i = 0; i < 16; i++) {
/*  53 */       if ((primaryBitmask & 1 << i) != 0) {
/*     */         
/*  55 */         short nonAirBlocksCount = input.readShort();
/*  56 */         ChunkSection section = (ChunkSection)Types1_13.CHUNK_SECTION.read(input);
/*  57 */         section.setNonAirBlocksCount(nonAirBlocksCount);
/*  58 */         sections[i] = section;
/*     */       } 
/*     */     } 
/*  61 */     int[] biomeData = fullChunk ? new int[256] : null;
/*  62 */     if (fullChunk) {
/*  63 */       for (int j = 0; j < 256; j++) {
/*  64 */         biomeData[j] = input.readInt();
/*     */       }
/*     */     }
/*     */     
/*  68 */     List<CompoundTag> nbtData = new ArrayList<>(Arrays.asList((Object[])Type.NBT_ARRAY.read(input)));
/*     */ 
/*     */     
/*  71 */     if (input.readableBytes() > 0) {
/*  72 */       byte[] array = (byte[])Type.REMAINING_BYTES.read(input);
/*  73 */       if (Via.getManager().isDebug()) {
/*  74 */         Via.getPlatform().getLogger().warning("Found " + array.length + " more bytes than expected while reading the chunk: " + chunkX + "/" + chunkZ);
/*     */       }
/*     */     } 
/*     */     
/*  78 */     return (Chunk)new BaseChunk(chunkX, chunkZ, fullChunk, false, primaryBitmask, sections, biomeData, heightMap, nbtData);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ByteBuf output, Chunk chunk) throws Exception {
/*  83 */     output.writeInt(chunk.getX());
/*  84 */     output.writeInt(chunk.getZ());
/*     */     
/*  86 */     output.writeBoolean(chunk.isFullChunk());
/*  87 */     Type.VAR_INT.writePrimitive(output, chunk.getBitmask());
/*  88 */     Type.NBT.write(output, chunk.getHeightMap());
/*     */     
/*  90 */     ByteBuf buf = output.alloc().buffer();
/*     */     try {
/*  92 */       for (int i = 0; i < 16; i++) {
/*  93 */         ChunkSection section = chunk.getSections()[i];
/*  94 */         if (section != null) {
/*     */           
/*  96 */           buf.writeShort(section.getNonAirBlocksCount());
/*  97 */           Types1_13.CHUNK_SECTION.write(buf, section);
/*     */         } 
/*  99 */       }  buf.readerIndex(0);
/* 100 */       Type.VAR_INT.writePrimitive(output, buf.readableBytes() + (chunk.isBiomeData() ? 1024 : 0));
/* 101 */       output.writeBytes(buf);
/*     */     } finally {
/* 103 */       buf.release();
/*     */     } 
/*     */ 
/*     */     
/* 107 */     if (chunk.isBiomeData()) {
/* 108 */       for (int value : chunk.getBiomeData()) {
/* 109 */         output.writeInt(value & 0xFF);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 114 */     Type.NBT_ARRAY.write(output, chunk.getBlockEntities().toArray((Object[])new CompoundTag[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Type> getBaseClass() {
/* 119 */     return (Class)BaseChunkType.class;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_14to1_13_2\types\Chunk1_14Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
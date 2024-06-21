/*     */ package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.types;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_16;
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
/*     */ public class Chunk1_16Type
/*     */   extends Type<Chunk>
/*     */ {
/*  34 */   private static final CompoundTag[] EMPTY_COMPOUNDS = new CompoundTag[0];
/*     */   
/*     */   public Chunk1_16Type() {
/*  37 */     super(Chunk.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk read(ByteBuf input) throws Exception {
/*  42 */     int chunkX = input.readInt();
/*  43 */     int chunkZ = input.readInt();
/*     */     
/*  45 */     boolean fullChunk = input.readBoolean();
/*  46 */     boolean ignoreOldLightData = input.readBoolean();
/*  47 */     int primaryBitmask = Type.VAR_INT.readPrimitive(input);
/*  48 */     CompoundTag heightMap = (CompoundTag)Type.NBT.read(input);
/*     */     
/*  50 */     int[] biomeData = fullChunk ? new int[1024] : null;
/*  51 */     if (fullChunk) {
/*  52 */       for (int j = 0; j < 1024; j++) {
/*  53 */         biomeData[j] = input.readInt();
/*     */       }
/*     */     }
/*     */     
/*  57 */     Type.VAR_INT.readPrimitive(input);
/*     */ 
/*     */     
/*  60 */     ChunkSection[] sections = new ChunkSection[16];
/*  61 */     for (int i = 0; i < 16; i++) {
/*  62 */       if ((primaryBitmask & 1 << i) != 0) {
/*     */         
/*  64 */         short nonAirBlocksCount = input.readShort();
/*  65 */         ChunkSection section = (ChunkSection)Types1_16.CHUNK_SECTION.read(input);
/*  66 */         section.setNonAirBlocksCount(nonAirBlocksCount);
/*  67 */         sections[i] = section;
/*     */       } 
/*     */     } 
/*  70 */     List<CompoundTag> nbtData = new ArrayList<>(Arrays.asList((Object[])Type.NBT_ARRAY.read(input)));
/*     */ 
/*     */     
/*  73 */     if (input.readableBytes() > 0) {
/*  74 */       byte[] array = (byte[])Type.REMAINING_BYTES.read(input);
/*  75 */       if (Via.getManager().isDebug()) {
/*  76 */         Via.getPlatform().getLogger().warning("Found " + array.length + " more bytes than expected while reading the chunk: " + chunkX + "/" + chunkZ);
/*     */       }
/*     */     } 
/*     */     
/*  80 */     return (Chunk)new BaseChunk(chunkX, chunkZ, fullChunk, ignoreOldLightData, primaryBitmask, sections, biomeData, heightMap, nbtData);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ByteBuf output, Chunk chunk) throws Exception {
/*  85 */     output.writeInt(chunk.getX());
/*  86 */     output.writeInt(chunk.getZ());
/*     */     
/*  88 */     output.writeBoolean(chunk.isFullChunk());
/*  89 */     output.writeBoolean(chunk.isIgnoreOldLightData());
/*  90 */     Type.VAR_INT.writePrimitive(output, chunk.getBitmask());
/*  91 */     Type.NBT.write(output, chunk.getHeightMap());
/*     */ 
/*     */     
/*  94 */     if (chunk.isBiomeData()) {
/*  95 */       for (int value : chunk.getBiomeData()) {
/*  96 */         output.writeInt(value);
/*     */       }
/*     */     }
/*     */     
/* 100 */     ByteBuf buf = output.alloc().buffer();
/*     */     try {
/* 102 */       for (int i = 0; i < 16; i++) {
/* 103 */         ChunkSection section = chunk.getSections()[i];
/* 104 */         if (section != null) {
/*     */           
/* 106 */           buf.writeShort(section.getNonAirBlocksCount());
/* 107 */           Types1_16.CHUNK_SECTION.write(buf, section);
/*     */         } 
/* 109 */       }  buf.readerIndex(0);
/* 110 */       Type.VAR_INT.writePrimitive(output, buf.readableBytes());
/* 111 */       output.writeBytes(buf);
/*     */     } finally {
/* 113 */       buf.release();
/*     */     } 
/*     */ 
/*     */     
/* 117 */     Type.NBT_ARRAY.write(output, chunk.getBlockEntities().toArray((Object[])EMPTY_COMPOUNDS));
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Type> getBaseClass() {
/* 122 */     return (Class)BaseChunkType.class;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_16to1_15_2\types\Chunk1_16Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
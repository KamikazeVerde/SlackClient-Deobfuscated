/*     */ package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types;
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
/*     */ public class Chunk1_15Type
/*     */   extends Type<Chunk>
/*     */ {
/*  34 */   private static final CompoundTag[] EMPTY_COMPOUNDS = new CompoundTag[0];
/*     */   
/*     */   public Chunk1_15Type() {
/*  37 */     super(Chunk.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk read(ByteBuf input) throws Exception {
/*  42 */     int chunkX = input.readInt();
/*  43 */     int chunkZ = input.readInt();
/*     */     
/*  45 */     boolean fullChunk = input.readBoolean();
/*  46 */     int primaryBitmask = Type.VAR_INT.readPrimitive(input);
/*  47 */     CompoundTag heightMap = (CompoundTag)Type.NBT.read(input);
/*     */     
/*  49 */     int[] biomeData = fullChunk ? new int[1024] : null;
/*  50 */     if (fullChunk) {
/*  51 */       for (int j = 0; j < 1024; j++) {
/*  52 */         biomeData[j] = input.readInt();
/*     */       }
/*     */     }
/*     */     
/*  56 */     Type.VAR_INT.readPrimitive(input);
/*     */ 
/*     */     
/*  59 */     ChunkSection[] sections = new ChunkSection[16];
/*  60 */     for (int i = 0; i < 16; i++) {
/*  61 */       if ((primaryBitmask & 1 << i) != 0) {
/*     */         
/*  63 */         short nonAirBlocksCount = input.readShort();
/*  64 */         ChunkSection section = (ChunkSection)Types1_13.CHUNK_SECTION.read(input);
/*  65 */         section.setNonAirBlocksCount(nonAirBlocksCount);
/*  66 */         sections[i] = section;
/*     */       } 
/*     */     } 
/*  69 */     List<CompoundTag> nbtData = new ArrayList<>(Arrays.asList((Object[])Type.NBT_ARRAY.read(input)));
/*     */ 
/*     */     
/*  72 */     if (input.readableBytes() > 0) {
/*  73 */       byte[] array = (byte[])Type.REMAINING_BYTES.read(input);
/*  74 */       if (Via.getManager().isDebug()) {
/*  75 */         Via.getPlatform().getLogger().warning("Found " + array.length + " more bytes than expected while reading the chunk: " + chunkX + "/" + chunkZ);
/*     */       }
/*     */     } 
/*     */     
/*  79 */     return (Chunk)new BaseChunk(chunkX, chunkZ, fullChunk, false, primaryBitmask, sections, biomeData, heightMap, nbtData);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ByteBuf output, Chunk chunk) throws Exception {
/*  84 */     output.writeInt(chunk.getX());
/*  85 */     output.writeInt(chunk.getZ());
/*     */     
/*  87 */     output.writeBoolean(chunk.isFullChunk());
/*  88 */     Type.VAR_INT.writePrimitive(output, chunk.getBitmask());
/*  89 */     Type.NBT.write(output, chunk.getHeightMap());
/*     */ 
/*     */     
/*  92 */     if (chunk.isBiomeData()) {
/*  93 */       for (int value : chunk.getBiomeData()) {
/*  94 */         output.writeInt(value);
/*     */       }
/*     */     }
/*     */     
/*  98 */     ByteBuf buf = output.alloc().buffer();
/*     */     try {
/* 100 */       for (int i = 0; i < 16; i++) {
/* 101 */         ChunkSection section = chunk.getSections()[i];
/* 102 */         if (section != null) {
/*     */           
/* 104 */           buf.writeShort(section.getNonAirBlocksCount());
/* 105 */           Types1_13.CHUNK_SECTION.write(buf, section);
/*     */         } 
/* 107 */       }  buf.readerIndex(0);
/* 108 */       Type.VAR_INT.writePrimitive(output, buf.readableBytes());
/* 109 */       output.writeBytes(buf);
/*     */     } finally {
/* 111 */       buf.release();
/*     */     } 
/*     */ 
/*     */     
/* 115 */     Type.NBT_ARRAY.write(output, chunk.getBlockEntities().toArray((Object[])EMPTY_COMPOUNDS));
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Type> getBaseClass() {
/* 120 */     return (Class)BaseChunkType.class;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_15to1_14_4\types\Chunk1_15Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
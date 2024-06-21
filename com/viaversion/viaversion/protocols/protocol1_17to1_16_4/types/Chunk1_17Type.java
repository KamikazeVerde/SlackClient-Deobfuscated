/*     */ package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
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
/*     */ import java.util.BitSet;
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
/*     */ public final class Chunk1_17Type
/*     */   extends Type<Chunk>
/*     */ {
/*  36 */   private static final CompoundTag[] EMPTY_COMPOUNDS = new CompoundTag[0];
/*     */   private final int ySectionCount;
/*     */   
/*     */   public Chunk1_17Type(int ySectionCount) {
/*  40 */     super(Chunk.class);
/*  41 */     Preconditions.checkArgument((ySectionCount > 0));
/*  42 */     this.ySectionCount = ySectionCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk read(ByteBuf input) throws Exception {
/*  47 */     int chunkX = input.readInt();
/*  48 */     int chunkZ = input.readInt();
/*     */     
/*  50 */     BitSet sectionsMask = BitSet.valueOf((long[])Type.LONG_ARRAY_PRIMITIVE.read(input));
/*  51 */     CompoundTag heightMap = (CompoundTag)Type.NBT.read(input);
/*     */     
/*  53 */     int[] biomeData = (int[])Type.VAR_INT_ARRAY_PRIMITIVE.read(input);
/*     */     
/*  55 */     Type.VAR_INT.readPrimitive(input);
/*     */ 
/*     */     
/*  58 */     ChunkSection[] sections = new ChunkSection[this.ySectionCount];
/*  59 */     for (int i = 0; i < this.ySectionCount; i++) {
/*  60 */       if (sectionsMask.get(i)) {
/*     */         
/*  62 */         short nonAirBlocksCount = input.readShort();
/*  63 */         ChunkSection section = (ChunkSection)Types1_16.CHUNK_SECTION.read(input);
/*  64 */         section.setNonAirBlocksCount(nonAirBlocksCount);
/*  65 */         sections[i] = section;
/*     */       } 
/*     */     } 
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
/*  78 */     return (Chunk)new BaseChunk(chunkX, chunkZ, true, false, sectionsMask, sections, biomeData, heightMap, nbtData);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ByteBuf output, Chunk chunk) throws Exception {
/*  83 */     output.writeInt(chunk.getX());
/*  84 */     output.writeInt(chunk.getZ());
/*     */     
/*  86 */     Type.LONG_ARRAY_PRIMITIVE.write(output, chunk.getChunkMask().toLongArray());
/*  87 */     Type.NBT.write(output, chunk.getHeightMap());
/*     */ 
/*     */     
/*  90 */     Type.VAR_INT_ARRAY_PRIMITIVE.write(output, chunk.getBiomeData());
/*     */     
/*  92 */     ByteBuf buf = output.alloc().buffer();
/*     */     try {
/*  94 */       ChunkSection[] sections = chunk.getSections();
/*  95 */       for (ChunkSection section : sections) {
/*  96 */         if (section != null) {
/*     */           
/*  98 */           buf.writeShort(section.getNonAirBlocksCount());
/*  99 */           Types1_16.CHUNK_SECTION.write(buf, section);
/*     */         } 
/* 101 */       }  buf.readerIndex(0);
/* 102 */       Type.VAR_INT.writePrimitive(output, buf.readableBytes());
/* 103 */       output.writeBytes(buf);
/*     */     } finally {
/* 105 */       buf.release();
/*     */     } 
/*     */ 
/*     */     
/* 109 */     Type.NBT_ARRAY.write(output, chunk.getBlockEntities().toArray((Object[])EMPTY_COMPOUNDS));
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Type> getBaseClass() {
/* 114 */     return (Class)BaseChunkType.class;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_17to1_16_4\types\Chunk1_17Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
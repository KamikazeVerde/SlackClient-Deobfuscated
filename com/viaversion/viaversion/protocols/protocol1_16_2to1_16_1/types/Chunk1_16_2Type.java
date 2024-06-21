/*     */ package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types;
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
/*     */ public class Chunk1_16_2Type
/*     */   extends Type<Chunk>
/*     */ {
/*  34 */   private static final CompoundTag[] EMPTY_COMPOUNDS = new CompoundTag[0];
/*     */   
/*     */   public Chunk1_16_2Type() {
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
/*  49 */     int[] biomeData = null;
/*  50 */     if (fullChunk) {
/*  51 */       biomeData = (int[])Type.VAR_INT_ARRAY_PRIMITIVE.read(input);
/*     */     }
/*     */     
/*  54 */     Type.VAR_INT.readPrimitive(input);
/*     */ 
/*     */     
/*  57 */     ChunkSection[] sections = new ChunkSection[16];
/*  58 */     for (int i = 0; i < 16; i++) {
/*  59 */       if ((primaryBitmask & 1 << i) != 0) {
/*     */         
/*  61 */         short nonAirBlocksCount = input.readShort();
/*  62 */         ChunkSection section = (ChunkSection)Types1_16.CHUNK_SECTION.read(input);
/*  63 */         section.setNonAirBlocksCount(nonAirBlocksCount);
/*  64 */         sections[i] = section;
/*     */       } 
/*     */     } 
/*  67 */     List<CompoundTag> nbtData = new ArrayList<>(Arrays.asList((Object[])Type.NBT_ARRAY.read(input)));
/*     */ 
/*     */     
/*  70 */     if (input.readableBytes() > 0) {
/*  71 */       byte[] array = (byte[])Type.REMAINING_BYTES.read(input);
/*  72 */       if (Via.getManager().isDebug()) {
/*  73 */         Via.getPlatform().getLogger().warning("Found " + array.length + " more bytes than expected while reading the chunk: " + chunkX + "/" + chunkZ);
/*     */       }
/*     */     } 
/*     */     
/*  77 */     return (Chunk)new BaseChunk(chunkX, chunkZ, fullChunk, false, primaryBitmask, sections, biomeData, heightMap, nbtData);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ByteBuf output, Chunk chunk) throws Exception {
/*  82 */     output.writeInt(chunk.getX());
/*  83 */     output.writeInt(chunk.getZ());
/*     */     
/*  85 */     output.writeBoolean(chunk.isFullChunk());
/*  86 */     Type.VAR_INT.writePrimitive(output, chunk.getBitmask());
/*  87 */     Type.NBT.write(output, chunk.getHeightMap());
/*     */ 
/*     */     
/*  90 */     if (chunk.isBiomeData()) {
/*  91 */       Type.VAR_INT_ARRAY_PRIMITIVE.write(output, chunk.getBiomeData());
/*     */     }
/*     */     
/*  94 */     ByteBuf buf = output.alloc().buffer();
/*     */     try {
/*  96 */       for (int i = 0; i < 16; i++) {
/*  97 */         ChunkSection section = chunk.getSections()[i];
/*  98 */         if (section != null) {
/*     */           
/* 100 */           buf.writeShort(section.getNonAirBlocksCount());
/* 101 */           Types1_16.CHUNK_SECTION.write(buf, section);
/*     */         } 
/* 103 */       }  buf.readerIndex(0);
/* 104 */       Type.VAR_INT.writePrimitive(output, buf.readableBytes());
/* 105 */       output.writeBytes(buf);
/*     */     } finally {
/* 107 */       buf.release();
/*     */     } 
/*     */ 
/*     */     
/* 111 */     Type.NBT_ARRAY.write(output, chunk.getBlockEntities().toArray((Object[])EMPTY_COMPOUNDS));
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Type> getBaseClass() {
/* 116 */     return (Class)BaseChunkType.class;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_16_2to1_16_1\types\Chunk1_16_2Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
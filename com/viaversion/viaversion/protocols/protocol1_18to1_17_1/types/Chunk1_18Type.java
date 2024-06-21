/*     */ package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk1_18;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
/*     */ import com.viaversion.viaversion.api.type.types.version.ChunkSectionType1_18;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_18;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.ArrayList;
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
/*     */ public final class Chunk1_18Type
/*     */   extends Type<Chunk>
/*     */ {
/*     */   private final ChunkSectionType1_18 sectionType;
/*     */   private final int ySectionCount;
/*     */   
/*     */   public Chunk1_18Type(int ySectionCount, int globalPaletteBlockBits, int globalPaletteBiomeBits) {
/*  39 */     super(Chunk.class);
/*  40 */     Preconditions.checkArgument((ySectionCount > 0));
/*  41 */     this.sectionType = new ChunkSectionType1_18(globalPaletteBlockBits, globalPaletteBiomeBits);
/*  42 */     this.ySectionCount = ySectionCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk read(ByteBuf buffer) throws Exception {
/*  47 */     int chunkX = buffer.readInt();
/*  48 */     int chunkZ = buffer.readInt();
/*  49 */     CompoundTag heightMap = (CompoundTag)Type.NBT.read(buffer);
/*     */ 
/*     */     
/*  52 */     ByteBuf sectionsBuf = buffer.readBytes(Type.VAR_INT.readPrimitive(buffer));
/*  53 */     ChunkSection[] sections = new ChunkSection[this.ySectionCount];
/*     */     try {
/*  55 */       for (int j = 0; j < this.ySectionCount; j++) {
/*  56 */         sections[j] = this.sectionType.read(sectionsBuf);
/*     */       
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/*  62 */       sectionsBuf.release();
/*     */     } 
/*     */     
/*  65 */     int blockEntitiesLength = Type.VAR_INT.readPrimitive(buffer);
/*  66 */     List<BlockEntity> blockEntities = new ArrayList<>(blockEntitiesLength);
/*  67 */     for (int i = 0; i < blockEntitiesLength; i++) {
/*  68 */       blockEntities.add(Types1_18.BLOCK_ENTITY.read(buffer));
/*     */     }
/*     */     
/*  71 */     return (Chunk)new Chunk1_18(chunkX, chunkZ, sections, heightMap, blockEntities);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ByteBuf buffer, Chunk chunk) throws Exception {
/*  76 */     buffer.writeInt(chunk.getX());
/*  77 */     buffer.writeInt(chunk.getZ());
/*     */     
/*  79 */     Type.NBT.write(buffer, chunk.getHeightMap());
/*     */     
/*  81 */     ByteBuf sectionBuffer = buffer.alloc().buffer();
/*     */     try {
/*  83 */       for (ChunkSection section : chunk.getSections()) {
/*  84 */         this.sectionType.write(sectionBuffer, section);
/*     */       }
/*  86 */       sectionBuffer.readerIndex(0);
/*  87 */       Type.VAR_INT.writePrimitive(buffer, sectionBuffer.readableBytes());
/*  88 */       buffer.writeBytes(sectionBuffer);
/*     */     } finally {
/*  90 */       sectionBuffer.release();
/*     */     } 
/*     */     
/*  93 */     Type.VAR_INT.writePrimitive(buffer, chunk.blockEntities().size());
/*  94 */     for (BlockEntity blockEntity : chunk.blockEntities()) {
/*  95 */       Types1_18.BLOCK_ENTITY.write(buffer, blockEntity);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Type> getBaseClass() {
/* 101 */     return (Class)BaseChunkType.class;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_18to1_17_1\types\Chunk1_18Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
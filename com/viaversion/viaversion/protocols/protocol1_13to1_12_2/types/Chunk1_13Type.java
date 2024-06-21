/*     */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.Environment;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.type.PartialType;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_13;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
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
/*     */ public class Chunk1_13Type
/*     */   extends PartialType<Chunk, ClientWorld>
/*     */ {
/*     */   public Chunk1_13Type(ClientWorld param) {
/*  40 */     super(param, Chunk.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk read(ByteBuf input, ClientWorld world) throws Exception {
/*  45 */     int chunkX = input.readInt();
/*  46 */     int chunkZ = input.readInt();
/*     */     
/*  48 */     boolean fullChunk = input.readBoolean();
/*  49 */     int primaryBitmask = Type.VAR_INT.readPrimitive(input);
/*  50 */     ByteBuf data = input.readSlice(Type.VAR_INT.readPrimitive(input));
/*     */ 
/*     */     
/*  53 */     ChunkSection[] sections = new ChunkSection[16];
/*  54 */     for (int i = 0; i < 16; i++) {
/*  55 */       if ((primaryBitmask & 1 << i) != 0) {
/*     */         
/*  57 */         ChunkSection section = (ChunkSection)Types1_13.CHUNK_SECTION.read(data);
/*  58 */         sections[i] = section;
/*  59 */         section.getLight().readBlockLight(data);
/*  60 */         if (world.getEnvironment() == Environment.NORMAL) {
/*  61 */           section.getLight().readSkyLight(data);
/*     */         }
/*     */       } 
/*     */     } 
/*  65 */     int[] biomeData = fullChunk ? new int[256] : null;
/*  66 */     if (fullChunk) {
/*  67 */       if (data.readableBytes() >= 1024) {
/*  68 */         for (int j = 0; j < 256; j++) {
/*  69 */           biomeData[j] = data.readInt();
/*     */         }
/*     */       } else {
/*  72 */         Via.getPlatform().getLogger().log(Level.WARNING, "Chunk x=" + chunkX + " z=" + chunkZ + " doesn't have biome data!");
/*     */       } 
/*     */     }
/*     */     
/*  76 */     List<CompoundTag> nbtData = new ArrayList<>(Arrays.asList((Object[])Type.NBT_ARRAY.read(input)));
/*     */ 
/*     */     
/*  79 */     if (input.readableBytes() > 0) {
/*  80 */       byte[] array = (byte[])Type.REMAINING_BYTES.read(input);
/*  81 */       if (Via.getManager().isDebug()) {
/*  82 */         Via.getPlatform().getLogger().warning("Found " + array.length + " more bytes than expected while reading the chunk: " + chunkX + "/" + chunkZ);
/*     */       }
/*     */     } 
/*     */     
/*  86 */     return (Chunk)new BaseChunk(chunkX, chunkZ, fullChunk, false, primaryBitmask, sections, biomeData, nbtData);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ByteBuf output, ClientWorld world, Chunk chunk) throws Exception {
/*  91 */     output.writeInt(chunk.getX());
/*  92 */     output.writeInt(chunk.getZ());
/*     */     
/*  94 */     output.writeBoolean(chunk.isFullChunk());
/*  95 */     Type.VAR_INT.writePrimitive(output, chunk.getBitmask());
/*     */     
/*  97 */     ByteBuf buf = output.alloc().buffer();
/*     */     try {
/*  99 */       for (int i = 0; i < 16; i++) {
/* 100 */         ChunkSection section = chunk.getSections()[i];
/* 101 */         if (section != null) {
/* 102 */           Types1_13.CHUNK_SECTION.write(buf, section);
/* 103 */           section.getLight().writeBlockLight(buf);
/*     */           
/* 105 */           if (section.getLight().hasSkyLight())
/* 106 */             section.getLight().writeSkyLight(buf); 
/*     */         } 
/*     */       } 
/* 109 */       buf.readerIndex(0);
/* 110 */       Type.VAR_INT.writePrimitive(output, buf.readableBytes() + (chunk.isBiomeData() ? 1024 : 0));
/* 111 */       output.writeBytes(buf);
/*     */     } finally {
/* 113 */       buf.release();
/*     */     } 
/*     */ 
/*     */     
/* 117 */     if (chunk.isBiomeData()) {
/* 118 */       for (int value : chunk.getBiomeData()) {
/* 119 */         output.writeInt(value);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 124 */     Type.NBT_ARRAY.write(output, chunk.getBlockEntities().toArray((Object[])new CompoundTag[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Type> getBaseClass() {
/* 129 */     return (Class)BaseChunkType.class;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\types\Chunk1_13Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.types;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.type.PartialType;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkBulkType;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import io.netty.buffer.ByteBuf;
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
/*     */ public class ChunkBulk1_8Type
/*     */   extends PartialType<Chunk[], ClientWorld>
/*     */ {
/*     */   private static final int BLOCKS_PER_SECTION = 4096;
/*     */   private static final int BLOCKS_BYTES = 8192;
/*     */   private static final int LIGHT_BYTES = 2048;
/*     */   private static final int BIOME_BYTES = 256;
/*     */   
/*     */   public ChunkBulk1_8Type(ClientWorld clientWorld) {
/*  36 */     super(clientWorld, Chunk[].class);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Type> getBaseClass() {
/*  41 */     return (Class)BaseChunkBulkType.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk[] read(ByteBuf input, ClientWorld world) throws Exception {
/*  46 */     boolean skyLight = input.readBoolean();
/*  47 */     int count = Type.VAR_INT.readPrimitive(input);
/*  48 */     Chunk[] chunks = new Chunk[count];
/*  49 */     ChunkBulkSection[] chunkInfo = new ChunkBulkSection[count];
/*     */     
/*     */     int i;
/*  52 */     for (i = 0; i < chunkInfo.length; i++) {
/*  53 */       chunkInfo[i] = new ChunkBulkSection(input, skyLight);
/*     */     }
/*     */     
/*  56 */     for (i = 0; i < chunks.length; i++) {
/*  57 */       ChunkBulkSection chunkBulkSection = chunkInfo[i];
/*  58 */       chunkBulkSection.readData(input);
/*  59 */       chunks[i] = Chunk1_8Type.deserialize(chunkBulkSection.chunkX, chunkBulkSection.chunkZ, true, skyLight, chunkBulkSection.bitmask, chunkBulkSection.getData());
/*     */     } 
/*     */     
/*  62 */     return chunks;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ByteBuf output, ClientWorld world, Chunk[] chunks) throws Exception {
/*  67 */     boolean skyLight = false;
/*     */     
/*  69 */     label29: for (Chunk c : chunks) {
/*  70 */       for (ChunkSection section : c.getSections()) {
/*  71 */         if (section != null && section.getLight().hasSkyLight()) {
/*  72 */           skyLight = true;
/*     */           break label29;
/*     */         } 
/*     */       } 
/*     */     } 
/*  77 */     output.writeBoolean(skyLight);
/*  78 */     Type.VAR_INT.writePrimitive(output, chunks.length);
/*     */ 
/*     */     
/*  81 */     for (Chunk c : chunks) {
/*  82 */       output.writeInt(c.getX());
/*  83 */       output.writeInt(c.getZ());
/*  84 */       output.writeShort(c.getBitmask());
/*     */     } 
/*     */     
/*  87 */     for (Chunk c : chunks)
/*  88 */       output.writeBytes(Chunk1_8Type.serialize(c)); 
/*     */   }
/*     */   
/*     */   public static final class ChunkBulkSection
/*     */   {
/*     */     private final int chunkX;
/*     */     private final int chunkZ;
/*     */     private final int bitmask;
/*     */     private final byte[] data;
/*     */     
/*     */     public ChunkBulkSection(ByteBuf input, boolean skyLight) {
/*  99 */       this.chunkX = input.readInt();
/* 100 */       this.chunkZ = input.readInt();
/* 101 */       this.bitmask = input.readUnsignedShort();
/* 102 */       int setSections = Integer.bitCount(this.bitmask);
/* 103 */       this.data = new byte[setSections * (8192 + (skyLight ? 4096 : 2048)) + 256];
/*     */     }
/*     */     
/*     */     public void readData(ByteBuf input) {
/* 107 */       input.readBytes(this.data);
/*     */     }
/*     */     
/*     */     public int getChunkX() {
/* 111 */       return this.chunkX;
/*     */     }
/*     */     
/*     */     public int getChunkZ() {
/* 115 */       return this.chunkZ;
/*     */     }
/*     */     
/*     */     public int getBitmask() {
/* 119 */       return this.bitmask;
/*     */     }
/*     */     
/*     */     public byte[] getData() {
/* 123 */       return this.data;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\types\ChunkBulk1_8Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
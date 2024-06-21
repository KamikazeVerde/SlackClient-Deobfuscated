/*     */ package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
/*     */ import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_16_2;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types.Chunk1_16_2Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.types.Chunk1_16Type;
/*     */ import com.viaversion.viaversion.rewriter.BlockRewriter;
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
/*     */ public class WorldPackets
/*     */ {
/*  39 */   private static final BlockChangeRecord[] EMPTY_RECORDS = new BlockChangeRecord[0];
/*     */   
/*     */   public static void register(Protocol1_16_2To1_16_1 protocol) {
/*  42 */     BlockRewriter<ClientboundPackets1_16> blockRewriter = new BlockRewriter((Protocol)protocol, Type.POSITION1_14);
/*     */     
/*  44 */     blockRewriter.registerBlockAction((ClientboundPacketType)ClientboundPackets1_16.BLOCK_ACTION);
/*  45 */     blockRewriter.registerBlockChange((ClientboundPacketType)ClientboundPackets1_16.BLOCK_CHANGE);
/*  46 */     blockRewriter.registerAcknowledgePlayerDigging((ClientboundPacketType)ClientboundPackets1_16.ACKNOWLEDGE_PLAYER_DIGGING);
/*     */     
/*  48 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_16.CHUNK_DATA, wrapper -> {
/*     */           Chunk chunk = (Chunk)wrapper.read((Type)new Chunk1_16Type());
/*     */           
/*     */           wrapper.write((Type)new Chunk1_16_2Type(), chunk);
/*     */           
/*     */           for (int s = 0; s < (chunk.getSections()).length; s++) {
/*     */             ChunkSection section = chunk.getSections()[s];
/*     */             
/*     */             if (section != null) {
/*     */               DataPalette palette = section.palette(PaletteType.BLOCKS);
/*     */               
/*     */               for (int i = 0; i < palette.size(); i++) {
/*     */                 int mappedBlockStateId = protocol.getMappingData().getNewBlockStateId(palette.idByIndex(i));
/*     */                 palette.setIdByIndex(i, mappedBlockStateId);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         });
/*  66 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_16.MULTI_BLOCK_CHANGE, wrapper -> {
/*     */           wrapper.cancel();
/*     */           
/*     */           int chunkX = ((Integer)wrapper.read((Type)Type.INT)).intValue();
/*     */           
/*     */           int chunkZ = ((Integer)wrapper.read((Type)Type.INT)).intValue();
/*     */           
/*     */           long chunkPosition = 0L;
/*     */           
/*     */           chunkPosition |= (chunkX & 0x3FFFFFL) << 42L;
/*     */           
/*     */           chunkPosition |= (chunkZ & 0x3FFFFFL) << 20L;
/*     */           
/*     */           List[] arrayOfList = new List[16];
/*     */           
/*     */           BlockChangeRecord[] blockChangeRecord = (BlockChangeRecord[])wrapper.read(Type.BLOCK_CHANGE_RECORD_ARRAY);
/*     */           
/*     */           for (BlockChangeRecord record : blockChangeRecord) {
/*     */             int i = record.getY() >> 4;
/*     */             List<BlockChangeRecord> list = arrayOfList[i];
/*     */             if (list == null) {
/*     */               arrayOfList[i] = list = new ArrayList<>();
/*     */             }
/*     */             int blockId = protocol.getMappingData().getNewBlockStateId(record.getBlockId());
/*     */             list.add(new BlockChangeRecord1_16_2(record.getSectionX(), record.getSectionY(), record.getSectionZ(), blockId));
/*     */           } 
/*     */           for (int chunkY = 0; chunkY < arrayOfList.length; chunkY++) {
/*     */             List<BlockChangeRecord> sectionRecord = arrayOfList[chunkY];
/*     */             if (sectionRecord != null) {
/*     */               PacketWrapper newPacket = wrapper.create((PacketType)ClientboundPackets1_16_2.MULTI_BLOCK_CHANGE);
/*     */               newPacket.write((Type)Type.LONG, Long.valueOf(chunkPosition | chunkY & 0xFFFFFL));
/*     */               newPacket.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */               newPacket.write(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY, sectionRecord.toArray(EMPTY_RECORDS));
/*     */               newPacket.send(Protocol1_16_2To1_16_1.class);
/*     */             } 
/*     */           } 
/*     */         });
/* 103 */     blockRewriter.registerEffect((ClientboundPacketType)ClientboundPackets1_16.EFFECT, 1010, 2001);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_16_2to1_16_1\packets\WorldPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
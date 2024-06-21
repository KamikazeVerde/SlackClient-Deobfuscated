/*     */ package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets;
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
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types.Chunk1_16_2Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types.Chunk1_17Type;
/*     */ import com.viaversion.viaversion.rewriter.BlockRewriter;
/*     */ import java.util.ArrayList;
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
/*     */ public final class WorldPackets
/*     */ {
/*     */   public static void register(Protocol1_17To1_16_4 protocol) {
/*  43 */     BlockRewriter<ClientboundPackets1_16_2> blockRewriter = new BlockRewriter((Protocol)protocol, Type.POSITION1_14);
/*     */     
/*  45 */     blockRewriter.registerBlockAction((ClientboundPacketType)ClientboundPackets1_16_2.BLOCK_ACTION);
/*  46 */     blockRewriter.registerBlockChange((ClientboundPacketType)ClientboundPackets1_16_2.BLOCK_CHANGE);
/*  47 */     blockRewriter.registerVarLongMultiBlockChange((ClientboundPacketType)ClientboundPackets1_16_2.MULTI_BLOCK_CHANGE);
/*  48 */     blockRewriter.registerAcknowledgePlayerDigging((ClientboundPacketType)ClientboundPackets1_16_2.ACKNOWLEDGE_PLAYER_DIGGING);
/*     */     
/*  50 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.WORLD_BORDER, null, wrapper -> {
/*     */           ClientboundPackets1_17 clientboundPackets1_176; ClientboundPackets1_17 clientboundPackets1_175; ClientboundPackets1_17 clientboundPackets1_174;
/*     */           ClientboundPackets1_17 clientboundPackets1_173;
/*     */           ClientboundPackets1_17 clientboundPackets1_172;
/*     */           ClientboundPackets1_17 clientboundPackets1_171;
/*     */           int type = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           switch (type) {
/*     */             case 0:
/*     */               clientboundPackets1_176 = ClientboundPackets1_17.WORLD_BORDER_SIZE;
/*     */               break;
/*     */             case 1:
/*     */               clientboundPackets1_175 = ClientboundPackets1_17.WORLD_BORDER_LERP_SIZE;
/*     */               break;
/*     */             case 2:
/*     */               clientboundPackets1_174 = ClientboundPackets1_17.WORLD_BORDER_CENTER;
/*     */               break;
/*     */             case 3:
/*     */               clientboundPackets1_173 = ClientboundPackets1_17.WORLD_BORDER_INIT;
/*     */               break;
/*     */             case 4:
/*     */               clientboundPackets1_172 = ClientboundPackets1_17.WORLD_BORDER_WARNING_DELAY;
/*     */               break;
/*     */             case 5:
/*     */               clientboundPackets1_171 = ClientboundPackets1_17.WORLD_BORDER_WARNING_DISTANCE;
/*     */               break;
/*     */             default:
/*     */               throw new IllegalArgumentException("Invalid world border type received: " + type);
/*     */           } 
/*     */           wrapper.setPacketType((PacketType)clientboundPackets1_171);
/*     */         });
/*  80 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.UPDATE_LIGHT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  83 */             map((Type)Type.VAR_INT);
/*  84 */             map((Type)Type.VAR_INT);
/*  85 */             map((Type)Type.BOOLEAN);
/*  86 */             handler(wrapper -> {
/*     */                   int skyLightMask = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   int blockLightMask = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   wrapper.write(Type.LONG_ARRAY_PRIMITIVE, toBitSetLongArray(skyLightMask));
/*     */                   wrapper.write(Type.LONG_ARRAY_PRIMITIVE, toBitSetLongArray(blockLightMask));
/*     */                   wrapper.write(Type.LONG_ARRAY_PRIMITIVE, toBitSetLongArray(((Integer)wrapper.read((Type)Type.VAR_INT)).intValue()));
/*     */                   wrapper.write(Type.LONG_ARRAY_PRIMITIVE, toBitSetLongArray(((Integer)wrapper.read((Type)Type.VAR_INT)).intValue()));
/*     */                   writeLightArrays(wrapper, skyLightMask);
/*     */                   writeLightArrays(wrapper, blockLightMask);
/*     */                 });
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           private void writeLightArrays(PacketWrapper wrapper, int bitMask) throws Exception {
/* 101 */             List<byte[]> light = (List)new ArrayList<>();
/* 102 */             for (int i = 0; i < 18; i++) {
/* 103 */               if (isSet(bitMask, i)) {
/* 104 */                 light.add(wrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
/*     */               }
/*     */             } 
/*     */ 
/*     */             
/* 109 */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(light.size()));
/* 110 */             for (byte[] bytes : light) {
/* 111 */               wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, bytes);
/*     */             }
/*     */           }
/*     */           
/*     */           private long[] toBitSetLongArray(int bitmask) {
/* 116 */             return new long[] { bitmask };
/*     */           }
/*     */           
/*     */           private boolean isSet(int mask, int i) {
/* 120 */             return ((mask & 1 << i) != 0);
/*     */           }
/*     */         });
/*     */     
/* 124 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.CHUNK_DATA, wrapper -> {
/*     */           Chunk chunk = (Chunk)wrapper.read((Type)new Chunk1_16_2Type());
/*     */ 
/*     */           
/*     */           if (!chunk.isFullChunk()) {
/*     */             writeMultiBlockChangePacket(wrapper, chunk);
/*     */ 
/*     */             
/*     */             wrapper.cancel();
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           wrapper.write((Type)new Chunk1_17Type((chunk.getSections()).length), chunk);
/*     */           
/*     */           chunk.setChunkMask(BitSet.valueOf(new long[] { chunk.getBitmask() }));
/*     */           
/*     */           for (int s = 0; s < (chunk.getSections()).length; s++) {
/*     */             ChunkSection section = chunk.getSections()[s];
/*     */             
/*     */             if (section != null) {
/*     */               DataPalette palette = section.palette(PaletteType.BLOCKS);
/*     */               
/*     */               for (int i = 0; i < palette.size(); i++) {
/*     */                 int mappedBlockStateId = protocol.getMappingData().getNewBlockStateId(palette.idByIndex(i));
/*     */                 
/*     */                 palette.setIdByIndex(i, mappedBlockStateId);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         });
/*     */     
/* 156 */     blockRewriter.registerEffect((ClientboundPacketType)ClientboundPackets1_16_2.EFFECT, 1010, 2001);
/*     */   }
/*     */   
/*     */   private static void writeMultiBlockChangePacket(PacketWrapper wrapper, Chunk chunk) throws Exception {
/* 160 */     long chunkPosition = (chunk.getX() & 0x3FFFFFL) << 42L;
/* 161 */     chunkPosition |= (chunk.getZ() & 0x3FFFFFL) << 20L;
/*     */     
/* 163 */     ChunkSection[] sections = chunk.getSections();
/* 164 */     for (int chunkY = 0; chunkY < sections.length; chunkY++) {
/* 165 */       ChunkSection section = sections[chunkY];
/* 166 */       if (section != null) {
/*     */         
/* 168 */         PacketWrapper blockChangePacket = wrapper.create((PacketType)ClientboundPackets1_17.MULTI_BLOCK_CHANGE);
/* 169 */         blockChangePacket.write((Type)Type.LONG, Long.valueOf(chunkPosition | chunkY & 0xFFFFFL));
/* 170 */         blockChangePacket.write((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */ 
/*     */         
/* 173 */         BlockChangeRecord[] blockChangeRecords = new BlockChangeRecord[4096];
/* 174 */         DataPalette palette = section.palette(PaletteType.BLOCKS);
/* 175 */         int j = 0;
/* 176 */         for (int x = 0; x < 16; x++) {
/* 177 */           for (int y = 0; y < 16; y++) {
/* 178 */             for (int z = 0; z < 16; z++) {
/* 179 */               int blockStateId = Protocol1_17To1_16_4.MAPPINGS.getNewBlockStateId(palette.idAt(x, y, z));
/* 180 */               blockChangeRecords[j++] = (BlockChangeRecord)new BlockChangeRecord1_16_2(x, y, z, blockStateId);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 185 */         blockChangePacket.write(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY, blockChangeRecords);
/* 186 */         blockChangePacket.send(Protocol1_17To1_16_4.class);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_17to1_16_4\packets\WorldPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
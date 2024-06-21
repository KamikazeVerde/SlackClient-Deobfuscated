/*    */ package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
/*    */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types.Chunk1_14Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
/*    */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types.Chunk1_15Type;
/*    */ import com.viaversion.viaversion.rewriter.BlockRewriter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldPackets
/*    */ {
/*    */   public static void register(final Protocol1_15To1_14_4 protocol) {
/* 35 */     BlockRewriter<ClientboundPackets1_14> blockRewriter = new BlockRewriter((Protocol)protocol, Type.POSITION1_14);
/*    */     
/* 37 */     blockRewriter.registerBlockAction((ClientboundPacketType)ClientboundPackets1_14.BLOCK_ACTION);
/* 38 */     blockRewriter.registerBlockChange((ClientboundPacketType)ClientboundPackets1_14.BLOCK_CHANGE);
/* 39 */     blockRewriter.registerMultiBlockChange((ClientboundPacketType)ClientboundPackets1_14.MULTI_BLOCK_CHANGE);
/* 40 */     blockRewriter.registerAcknowledgePlayerDigging((ClientboundPacketType)ClientboundPackets1_14.ACKNOWLEDGE_PLAYER_DIGGING);
/*    */     
/* 42 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_14.CHUNK_DATA, wrapper -> {
/*    */           Chunk chunk = (Chunk)wrapper.read((Type)new Chunk1_14Type());
/*    */           
/*    */           wrapper.write((Type)new Chunk1_15Type(), chunk);
/*    */           
/*    */           if (chunk.isFullChunk()) {
/*    */             int[] biomeData = chunk.getBiomeData();
/*    */             
/*    */             int[] newBiomeData = new int[1024];
/*    */             
/*    */             if (biomeData != null) {
/*    */               int i;
/*    */               
/*    */               for (i = 0; i < 4; i++) {
/*    */                 for (int j = 0; j < 4; j++) {
/*    */                   int x = (j << 2) + 2;
/*    */                   
/*    */                   int z = (i << 2) + 2;
/*    */                   
/*    */                   int oldIndex = z << 4 | x;
/*    */                   newBiomeData[i << 2 | j] = biomeData[oldIndex];
/*    */                 } 
/*    */               } 
/*    */               for (i = 1; i < 64; i++) {
/*    */                 System.arraycopy(newBiomeData, 0, newBiomeData, i * 16, 16);
/*    */               }
/*    */             } 
/*    */             chunk.setBiomeData(newBiomeData);
/*    */           } 
/*    */           for (int s = 0; s < (chunk.getSections()).length; s++) {
/*    */             ChunkSection section = chunk.getSections()[s];
/*    */             if (section != null) {
/*    */               DataPalette palette = section.palette(PaletteType.BLOCKS);
/*    */               for (int i = 0; i < palette.size(); i++) {
/*    */                 int mappedBlockStateId = protocol.getMappingData().getNewBlockStateId(palette.idByIndex(i));
/*    */                 palette.setIdByIndex(i, mappedBlockStateId);
/*    */               } 
/*    */             } 
/*    */           } 
/*    */         });
/* 82 */     blockRewriter.registerEffect((ClientboundPacketType)ClientboundPackets1_14.EFFECT, 1010, 2001);
/* 83 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_14.SPAWN_PARTICLE, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 86 */             map((Type)Type.INT);
/* 87 */             map((Type)Type.BOOLEAN);
/* 88 */             map((Type)Type.FLOAT, (Type)Type.DOUBLE);
/* 89 */             map((Type)Type.FLOAT, (Type)Type.DOUBLE);
/* 90 */             map((Type)Type.FLOAT, (Type)Type.DOUBLE);
/* 91 */             map((Type)Type.FLOAT);
/* 92 */             map((Type)Type.FLOAT);
/* 93 */             map((Type)Type.FLOAT);
/* 94 */             map((Type)Type.FLOAT);
/* 95 */             map((Type)Type.INT);
/* 96 */             handler(wrapper -> {
/*    */                   int id = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*    */                   if (id == 3 || id == 23) {
/*    */                     int data = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*    */                     wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(protocol.getMappingData().getNewBlockStateId(data)));
/*    */                   } else if (id == 32) {
/*    */                     protocol.getItemRewriter().handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*    */                   } 
/*    */                 });
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_15to1_14_4\packets\WorldPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
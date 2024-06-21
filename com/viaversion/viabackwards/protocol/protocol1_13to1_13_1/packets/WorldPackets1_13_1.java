/*    */ package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;
/*    */ 
/*    */ import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
/*    */ import com.viaversion.viaversion.api.minecraft.BlockFace;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*    */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
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
/*    */ 
/*    */ public class WorldPackets1_13_1
/*    */ {
/*    */   public static void register(final Protocol1_13To1_13_1 protocol) {
/* 37 */     BlockRewriter<ClientboundPackets1_13> blockRewriter = new BlockRewriter((Protocol)protocol, Type.POSITION);
/*    */     
/* 39 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.CHUNK_DATA, wrapper -> {
/*    */           ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*    */           
/*    */           Chunk chunk = (Chunk)wrapper.passthrough((Type)new Chunk1_13Type(clientWorld));
/*    */           
/*    */           for (ChunkSection section : chunk.getSections()) {
/*    */             if (section != null) {
/*    */               DataPalette palette = section.palette(PaletteType.BLOCKS);
/*    */               
/*    */               for (int i = 0; i < palette.size(); i++) {
/*    */                 int mappedBlockStateId = protocol.getMappingData().getNewBlockStateId(palette.idByIndex(i));
/*    */                 
/*    */                 palette.setIdByIndex(i, mappedBlockStateId);
/*    */               } 
/*    */             } 
/*    */           } 
/*    */         });
/* 56 */     blockRewriter.registerBlockAction((ClientboundPacketType)ClientboundPackets1_13.BLOCK_ACTION);
/* 57 */     blockRewriter.registerBlockChange((ClientboundPacketType)ClientboundPackets1_13.BLOCK_CHANGE);
/* 58 */     blockRewriter.registerMultiBlockChange((ClientboundPacketType)ClientboundPackets1_13.MULTI_BLOCK_CHANGE);
/* 59 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.EFFECT, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 62 */             map((Type)Type.INT);
/* 63 */             map(Type.POSITION);
/* 64 */             map((Type)Type.INT);
/* 65 */             handler(wrapper -> {
/*    */                   int id = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*    */                   int data = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*    */                   if (id == 1010) {
/*    */                     wrapper.set((Type)Type.INT, 1, Integer.valueOf(protocol.getMappingData().getNewItemId(data)));
/*    */                   } else if (id == 2001) {
/*    */                     wrapper.set((Type)Type.INT, 1, Integer.valueOf(protocol.getMappingData().getNewBlockStateId(data)));
/*    */                   } else if (id == 2000) {
/*    */                     Position pos;
/*    */                     BlockFace relative;
/*    */                     switch (data) {
/*    */                       case 0:
/*    */                       case 1:
/*    */                         pos = (Position)wrapper.get(Type.POSITION, 0);
/*    */                         relative = (data == 0) ? BlockFace.BOTTOM : BlockFace.TOP;
/*    */                         wrapper.set(Type.POSITION, 0, pos.getRelative(relative));
/*    */                         wrapper.set((Type)Type.INT, 1, Integer.valueOf(4));
/*    */                         break;
/*    */                       case 2:
/*    */                         wrapper.set((Type)Type.INT, 1, Integer.valueOf(1));
/*    */                         break;
/*    */                       case 3:
/*    */                         wrapper.set((Type)Type.INT, 1, Integer.valueOf(7));
/*    */                         break;
/*    */                       case 4:
/*    */                         wrapper.set((Type)Type.INT, 1, Integer.valueOf(3));
/*    */                         break;
/*    */                       case 5:
/*    */                         wrapper.set((Type)Type.INT, 1, Integer.valueOf(5));
/*    */                         break;
/*    */                     } 
/*    */                   } 
/*    */                 });
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_13to1_13_1\packets\WorldPackets1_13_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
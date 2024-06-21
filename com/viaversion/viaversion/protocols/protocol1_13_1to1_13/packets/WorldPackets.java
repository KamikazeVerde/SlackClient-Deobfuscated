/*     */ package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.rewriter.BlockRewriter;
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
/*     */   public static void register(final Protocol1_13_1To1_13 protocol) {
/*  35 */     BlockRewriter<ClientboundPackets1_13> blockRewriter = new BlockRewriter((Protocol)protocol, Type.POSITION);
/*     */     
/*  37 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.CHUNK_DATA, wrapper -> {
/*     */           ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */           
/*     */           Chunk chunk = (Chunk)wrapper.passthrough((Type)new Chunk1_13Type(clientWorld));
/*     */           
/*     */           for (ChunkSection section : chunk.getSections()) {
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
/*  54 */     blockRewriter.registerBlockAction((ClientboundPacketType)ClientboundPackets1_13.BLOCK_ACTION);
/*  55 */     blockRewriter.registerBlockChange((ClientboundPacketType)ClientboundPackets1_13.BLOCK_CHANGE);
/*  56 */     blockRewriter.registerMultiBlockChange((ClientboundPacketType)ClientboundPackets1_13.MULTI_BLOCK_CHANGE);
/*     */     
/*  58 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  61 */             map((Type)Type.INT);
/*  62 */             map(Type.POSITION);
/*  63 */             map((Type)Type.INT);
/*  64 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   if (id == 2000) {
/*     */                     int data = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                     switch (data) {
/*     */                       case 1:
/*     */                         wrapper.set((Type)Type.INT, 1, Integer.valueOf(2));
/*     */                         return;
/*     */                       
/*     */                       case 0:
/*     */                       case 3:
/*     */                       case 6:
/*     */                         wrapper.set((Type)Type.INT, 1, Integer.valueOf(4));
/*     */                         return;
/*     */                       
/*     */                       case 2:
/*     */                       case 5:
/*     */                       case 8:
/*     */                         wrapper.set((Type)Type.INT, 1, Integer.valueOf(5));
/*     */                         return;
/*     */                       
/*     */                       case 7:
/*     */                         wrapper.set((Type)Type.INT, 1, Integer.valueOf(3));
/*     */                         return;
/*     */                     } 
/*     */                     wrapper.set((Type)Type.INT, 1, Integer.valueOf(0));
/*     */                   } else if (id == 1010) {
/*     */                     wrapper.set((Type)Type.INT, 1, Integer.valueOf(protocol.getMappingData().getNewItemId(((Integer)wrapper.get((Type)Type.INT, 1)).intValue())));
/*     */                   } else if (id == 2001) {
/*     */                     wrapper.set((Type)Type.INT, 1, Integer.valueOf(protocol.getMappingData().getNewBlockStateId(((Integer)wrapper.get((Type)Type.INT, 1)).intValue())));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  98 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 101 */             map((Type)Type.INT);
/* 102 */             map((Type)Type.UNSIGNED_BYTE);
/* 103 */             map((Type)Type.INT);
/*     */             
/* 105 */             handler(wrapper -> {
/*     */                   ClientWorld clientChunks = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   
/*     */                   int dimensionId = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   
/*     */                   clientChunks.setEnvironment(dimensionId);
/*     */                 });
/*     */           }
/*     */         });
/* 114 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 117 */             map((Type)Type.INT);
/* 118 */             handler(wrapper -> {
/*     */                   ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   int dimensionId = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   clientWorld.setEnvironment(dimensionId);
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13_1to1_13\packets\WorldPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
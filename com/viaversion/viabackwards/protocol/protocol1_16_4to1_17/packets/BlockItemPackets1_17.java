/*     */ package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
/*     */ import com.viaversion.viabackwards.api.rewriters.MapColorRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.data.MapColorRewrites;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.PingRequests;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.PlayerLastCursorItem;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types.Chunk1_16_2Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types.Chunk1_17Type;
/*     */ import com.viaversion.viaversion.rewriter.BlockRewriter;
/*     */ import com.viaversion.viaversion.rewriter.RecipeRewriter;
/*     */ import com.viaversion.viaversion.util.CompactArrayUtil;
/*     */ import com.viaversion.viaversion.util.MathUtil;
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
/*     */ public final class BlockItemPackets1_17
/*     */   extends ItemRewriter<ClientboundPackets1_17, ServerboundPackets1_16_2, Protocol1_16_4To1_17>
/*     */ {
/*     */   public BlockItemPackets1_17(Protocol1_16_4To1_17 protocol) {
/*  59 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  64 */     BlockRewriter<ClientboundPackets1_17> blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
/*     */     
/*  66 */     (new RecipeRewriter(this.protocol)).register((ClientboundPacketType)ClientboundPackets1_17.DECLARE_RECIPES);
/*     */     
/*  68 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_17.COOLDOWN);
/*  69 */     registerWindowItems((ClientboundPacketType)ClientboundPackets1_17.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
/*  70 */     registerEntityEquipmentArray((ClientboundPacketType)ClientboundPackets1_17.ENTITY_EQUIPMENT);
/*  71 */     registerTradeList((ClientboundPacketType)ClientboundPackets1_17.TRADE_LIST);
/*  72 */     registerAdvancements((ClientboundPacketType)ClientboundPackets1_17.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
/*     */     
/*  74 */     blockRewriter.registerAcknowledgePlayerDigging((ClientboundPacketType)ClientboundPackets1_17.ACKNOWLEDGE_PLAYER_DIGGING);
/*  75 */     blockRewriter.registerBlockAction((ClientboundPacketType)ClientboundPackets1_17.BLOCK_ACTION);
/*  76 */     blockRewriter.registerEffect((ClientboundPacketType)ClientboundPackets1_17.EFFECT, 1010, 2001);
/*     */ 
/*     */     
/*  79 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_16_2.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
/*  80 */     ((Protocol1_16_4To1_17)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_16_2.EDIT_BOOK, wrapper -> handleItemToServer((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     ((Protocol1_16_4To1_17)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_16_2.CLICK_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  92 */             map((Type)Type.UNSIGNED_BYTE);
/*  93 */             handler(wrapper -> {
/*     */                   short slot = ((Short)wrapper.passthrough((Type)Type.SHORT)).shortValue();
/*     */ 
/*     */                   
/*     */                   byte button = ((Byte)wrapper.passthrough((Type)Type.BYTE)).byteValue();
/*     */ 
/*     */                   
/*     */                   wrapper.read((Type)Type.SHORT);
/*     */ 
/*     */                   
/*     */                   int mode = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */ 
/*     */                   
/*     */                   Item clicked = BlockItemPackets1_17.this.handleItemToServer((Item)wrapper.read(Type.FLAT_VAR_INT_ITEM));
/*     */ 
/*     */                   
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */ 
/*     */                   
/*     */                   PlayerLastCursorItem state = (PlayerLastCursorItem)wrapper.user().get(PlayerLastCursorItem.class);
/*     */ 
/*     */                   
/*     */                   if (mode == 0 && button == 0 && clicked != null) {
/*     */                     state.setLastCursorItem(clicked);
/*     */                   } else if (mode == 0 && button == 1 && clicked != null) {
/*     */                     if (state.isSet()) {
/*     */                       state.setLastCursorItem(clicked);
/*     */                     } else {
/*     */                       state.setLastCursorItem(clicked, (clicked.amount() + 1) / 2);
/*     */                     } 
/*     */                   } else if (mode != 5 || slot != -999 || (button != 0 && button != 4)) {
/*     */                     state.setLastCursorItem(null);
/*     */                   } 
/*     */ 
/*     */                   
/*     */                   Item carried = state.getLastCursorItem();
/*     */                   
/*     */                   if (carried == null) {
/*     */                     wrapper.write(Type.FLAT_VAR_INT_ITEM, clicked);
/*     */                   } else {
/*     */                     wrapper.write(Type.FLAT_VAR_INT_ITEM, carried);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 139 */     ((Protocol1_16_4To1_17)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_17.SET_SLOT, wrapper -> {
/*     */           short windowId = ((Short)wrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */ 
/*     */           
/*     */           short slot = ((Short)wrapper.passthrough((Type)Type.SHORT)).shortValue();
/*     */ 
/*     */           
/*     */           Item carried = (Item)wrapper.read(Type.FLAT_VAR_INT_ITEM);
/*     */ 
/*     */           
/*     */           if (carried != null && windowId == -1 && slot == -1) {
/*     */             ((PlayerLastCursorItem)wrapper.user().get(PlayerLastCursorItem.class)).setLastCursorItem(carried);
/*     */           }
/*     */ 
/*     */           
/*     */           wrapper.write(Type.FLAT_VAR_INT_ITEM, handleItemToClient(carried));
/*     */         });
/*     */ 
/*     */     
/* 158 */     ((Protocol1_16_4To1_17)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_16_2.WINDOW_CONFIRMATION, null, wrapper -> {
/*     */           wrapper.cancel();
/*     */           
/*     */           if (!ViaBackwards.getConfig().handlePingsAsInvAcknowledgements()) {
/*     */             return;
/*     */           }
/*     */           
/*     */           short inventoryId = ((Short)wrapper.read((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */           
/*     */           short confirmationId = ((Short)wrapper.read((Type)Type.SHORT)).shortValue();
/*     */           boolean accepted = ((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue();
/*     */           if (inventoryId == 0 && accepted && ((PingRequests)wrapper.user().get(PingRequests.class)).removeId(confirmationId)) {
/*     */             PacketWrapper pongPacket = wrapper.create((PacketType)ServerboundPackets1_17.PONG);
/*     */             pongPacket.write((Type)Type.INT, Integer.valueOf(confirmationId));
/*     */             pongPacket.sendToServer(Protocol1_16_4To1_17.class);
/*     */           } 
/*     */         });
/* 175 */     ((Protocol1_16_4To1_17)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_17.SPAWN_PARTICLE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 178 */             map((Type)Type.INT);
/* 179 */             map((Type)Type.BOOLEAN);
/* 180 */             map((Type)Type.DOUBLE);
/* 181 */             map((Type)Type.DOUBLE);
/* 182 */             map((Type)Type.DOUBLE);
/* 183 */             map((Type)Type.FLOAT);
/* 184 */             map((Type)Type.FLOAT);
/* 185 */             map((Type)Type.FLOAT);
/* 186 */             map((Type)Type.FLOAT);
/* 187 */             map((Type)Type.INT);
/* 188 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   if (id == 16) {
/*     */                     wrapper.passthrough((Type)Type.FLOAT);
/*     */                     
/*     */                     wrapper.passthrough((Type)Type.FLOAT);
/*     */                     
/*     */                     wrapper.passthrough((Type)Type.FLOAT);
/*     */                     wrapper.passthrough((Type)Type.FLOAT);
/*     */                     wrapper.read((Type)Type.FLOAT);
/*     */                     wrapper.read((Type)Type.FLOAT);
/*     */                     wrapper.read((Type)Type.FLOAT);
/*     */                   } else if (id == 37) {
/*     */                     wrapper.set((Type)Type.INT, 0, Integer.valueOf(-1));
/*     */                     wrapper.cancel();
/*     */                   } 
/*     */                 });
/* 206 */             handler(BlockItemPackets1_17.this.getSpawnParticleHandler(Type.FLAT_VAR_INT_ITEM));
/*     */           }
/*     */         });
/*     */     
/* 210 */     ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_SIZE, ClientboundPackets1_16_2.WORLD_BORDER, 0);
/* 211 */     ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_LERP_SIZE, ClientboundPackets1_16_2.WORLD_BORDER, 1);
/* 212 */     ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_CENTER, ClientboundPackets1_16_2.WORLD_BORDER, 2);
/* 213 */     ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_INIT, ClientboundPackets1_16_2.WORLD_BORDER, 3);
/* 214 */     ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_WARNING_DELAY, ClientboundPackets1_16_2.WORLD_BORDER, 4);
/* 215 */     ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_WARNING_DISTANCE, ClientboundPackets1_16_2.WORLD_BORDER, 5);
/*     */ 
/*     */ 
/*     */     
/* 219 */     ((Protocol1_16_4To1_17)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_17.UPDATE_LIGHT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 222 */             map((Type)Type.VAR_INT);
/* 223 */             map((Type)Type.VAR_INT);
/* 224 */             map((Type)Type.BOOLEAN);
/* 225 */             handler(wrapper -> {
/*     */                   EntityTracker tracker = wrapper.user().getEntityTracker(Protocol1_16_4To1_17.class);
/*     */                   int startFromSection = Math.max(0, -(tracker.currentMinY() >> 4));
/*     */                   long[] skyLightMask = (long[])wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
/*     */                   long[] blockLightMask = (long[])wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
/*     */                   int cutSkyLightMask = BlockItemPackets1_17.this.cutLightMask(skyLightMask, startFromSection);
/*     */                   int cutBlockLightMask = BlockItemPackets1_17.this.cutLightMask(blockLightMask, startFromSection);
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(cutSkyLightMask));
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(cutBlockLightMask));
/*     */                   long[] emptySkyLightMask = (long[])wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
/*     */                   long[] emptyBlockLightMask = (long[])wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(BlockItemPackets1_17.this.cutLightMask(emptySkyLightMask, startFromSection)));
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(BlockItemPackets1_17.this.cutLightMask(emptyBlockLightMask, startFromSection)));
/*     */                   writeLightArrays(wrapper, BitSet.valueOf(skyLightMask), cutSkyLightMask, startFromSection, tracker.currentWorldSectionHeight());
/*     */                   writeLightArrays(wrapper, BitSet.valueOf(blockLightMask), cutBlockLightMask, startFromSection, tracker.currentWorldSectionHeight());
/*     */                 });
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           private void writeLightArrays(PacketWrapper wrapper, BitSet bitMask, int cutBitMask, int startFromSection, int sectionHeight) throws Exception {
/* 248 */             wrapper.read((Type)Type.VAR_INT);
/*     */             
/* 250 */             List<byte[]> light = (List)new ArrayList<>();
/*     */             
/*     */             int i;
/* 253 */             for (i = 0; i < startFromSection; i++) {
/* 254 */               if (bitMask.get(i)) {
/* 255 */                 wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
/*     */               }
/*     */             } 
/*     */ 
/*     */             
/* 260 */             for (i = 0; i < 18; i++) {
/* 261 */               if (isSet(cutBitMask, i)) {
/* 262 */                 light.add((byte[])wrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
/*     */               }
/*     */             } 
/*     */ 
/*     */             
/* 267 */             for (i = startFromSection + 18; i < sectionHeight + 2; i++) {
/* 268 */               if (bitMask.get(i)) {
/* 269 */                 wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
/*     */               }
/*     */             } 
/*     */ 
/*     */             
/* 274 */             for (byte[] bytes : light) {
/* 275 */               wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, bytes);
/*     */             }
/*     */           }
/*     */           
/*     */           private boolean isSet(int mask, int i) {
/* 280 */             return ((mask & 1 << i) != 0);
/*     */           }
/*     */         });
/*     */     
/* 284 */     ((Protocol1_16_4To1_17)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_17.MULTI_BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 287 */             map((Type)Type.LONG);
/* 288 */             map((Type)Type.BOOLEAN);
/* 289 */             handler(wrapper -> {
/*     */                   long chunkPos = ((Long)wrapper.get((Type)Type.LONG, 0)).longValue();
/*     */                   
/*     */                   int chunkY = (int)(chunkPos << 44L >> 44L);
/*     */                   
/*     */                   if (chunkY < 0 || chunkY > 15) {
/*     */                     wrapper.cancel();
/*     */                     
/*     */                     return;
/*     */                   } 
/*     */                   BlockChangeRecord[] records = (BlockChangeRecord[])wrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY);
/*     */                   for (BlockChangeRecord record : records) {
/*     */                     record.setBlockId(((Protocol1_16_4To1_17)BlockItemPackets1_17.this.protocol).getMappingData().getNewBlockStateId(record.getBlockId()));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 306 */     ((Protocol1_16_4To1_17)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_17.BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 309 */             map(Type.POSITION1_14);
/* 310 */             map((Type)Type.VAR_INT);
/* 311 */             handler(wrapper -> {
/*     */                   int y = ((Position)wrapper.get(Type.POSITION1_14, 0)).y();
/*     */                   
/*     */                   if (y < 0 || y > 255) {
/*     */                     wrapper.cancel();
/*     */                     
/*     */                     return;
/*     */                   } 
/*     */                   wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(((Protocol1_16_4To1_17)BlockItemPackets1_17.this.protocol).getMappingData().getNewBlockStateId(((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue())));
/*     */                 });
/*     */           }
/*     */         });
/* 323 */     ((Protocol1_16_4To1_17)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_17.CHUNK_DATA, wrapper -> {
/*     */           EntityTracker tracker = wrapper.user().getEntityTracker(Protocol1_16_4To1_17.class);
/*     */           
/*     */           int currentWorldSectionHeight = tracker.currentWorldSectionHeight();
/*     */           
/*     */           Chunk chunk = (Chunk)wrapper.read((Type)new Chunk1_17Type(currentWorldSectionHeight));
/*     */           
/*     */           wrapper.write((Type)new Chunk1_16_2Type(), chunk);
/*     */           
/*     */           int startFromSection = Math.max(0, -(tracker.currentMinY() >> 4));
/*     */           
/*     */           chunk.setBiomeData(Arrays.copyOfRange(chunk.getBiomeData(), startFromSection * 64, startFromSection * 64 + 1024));
/*     */           
/*     */           chunk.setBitmask(cutMask(chunk.getChunkMask(), startFromSection, false));
/*     */           
/*     */           chunk.setChunkMask(null);
/*     */           
/*     */           ChunkSection[] sections = Arrays.<ChunkSection>copyOfRange(chunk.getSections(), startFromSection, startFromSection + 16);
/*     */           
/*     */           chunk.setSections(sections);
/*     */           
/*     */           CompoundTag heightMaps = chunk.getHeightMap();
/*     */           
/*     */           for (Tag heightMapTag : heightMaps.values()) {
/*     */             LongArrayTag heightMap = (LongArrayTag)heightMapTag;
/*     */             
/*     */             int[] heightMapData = new int[256];
/*     */             
/*     */             int bitsPerEntry = MathUtil.ceilLog2((currentWorldSectionHeight << 4) + 1);
/*     */             
/*     */             CompactArrayUtil.iterateCompactArrayWithPadding(bitsPerEntry, heightMapData.length, heightMap.getValue(), ());
/*     */             
/*     */             heightMap.setValue(CompactArrayUtil.createCompactArrayWithPadding(9, heightMapData.length, ()));
/*     */           } 
/*     */           for (int i = 0; i < 16; i++) {
/*     */             ChunkSection section = sections[i];
/*     */             if (section != null) {
/*     */               DataPalette palette = section.palette(PaletteType.BLOCKS);
/*     */               for (int j = 0; j < palette.size(); j++) {
/*     */                 int mappedBlockStateId = ((Protocol1_16_4To1_17)this.protocol).getMappingData().getNewBlockStateId(palette.idByIndex(j));
/*     */                 palette.setIdByIndex(j, mappedBlockStateId);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           chunk.getBlockEntities().removeIf(());
/*     */         });
/* 369 */     ((Protocol1_16_4To1_17)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_17.BLOCK_ENTITY_DATA, wrapper -> {
/*     */           int y = ((Position)wrapper.passthrough(Type.POSITION1_14)).y();
/*     */           
/*     */           if (y < 0 || y > 255) {
/*     */             wrapper.cancel();
/*     */           }
/*     */         });
/* 376 */     ((Protocol1_16_4To1_17)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_17.BLOCK_BREAK_ANIMATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 379 */             map((Type)Type.VAR_INT);
/* 380 */             handler(wrapper -> {
/*     */                   int y = ((Position)wrapper.passthrough(Type.POSITION1_14)).y();
/*     */                   
/*     */                   if (y < 0 || y > 255) {
/*     */                     wrapper.cancel();
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 389 */     ((Protocol1_16_4To1_17)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_17.MAP_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 392 */             map((Type)Type.VAR_INT);
/* 393 */             map((Type)Type.BYTE);
/* 394 */             handler(wrapper -> wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(true)));
/* 395 */             map((Type)Type.BOOLEAN);
/* 396 */             handler(wrapper -> {
/*     */                   boolean hasMarkers = ((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue();
/*     */                   if (!hasMarkers) {
/*     */                     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                   } else {
/*     */                     MapColorRewriter.getRewriteHandler(MapColorRewrites::getMappedColor).handle(wrapper);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private int cutLightMask(long[] mask, int startFromSection) {
/* 409 */     if (mask.length == 0) return 0; 
/* 410 */     return cutMask(BitSet.valueOf(mask), startFromSection, true);
/*     */   }
/*     */   
/*     */   private int cutMask(BitSet mask, int startFromSection, boolean lightMask) {
/* 414 */     int cutMask = 0;
/*     */     
/* 416 */     int to = startFromSection + (lightMask ? 18 : 16);
/* 417 */     for (int i = startFromSection, j = 0; i < to; i++, j++) {
/* 418 */       if (mask.get(i)) {
/* 419 */         cutMask |= 1 << j;
/*     */       }
/*     */     } 
/* 422 */     return cutMask;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_16_4to1_17\packets\BlockItemPackets1_17.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
/*     */ import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntityImpl;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk1_18;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPaletteImpl;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types.Chunk1_17Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.BlockEntityIds;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.Protocol1_18To1_17_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.storage.ChunkLightStorage;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type;
/*     */ import com.viaversion.viaversion.util.Key;
/*     */ import com.viaversion.viaversion.util.MathUtil;
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
/*     */ public final class WorldPackets
/*     */ {
/*     */   public static void register(Protocol1_18To1_17_1 protocol) {
/*  51 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_17_1.BLOCK_ENTITY_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  54 */             map(Type.POSITION1_14);
/*  55 */             handler(wrapper -> {
/*     */                   short id = ((Short)wrapper.read((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                   
/*     */                   int newId = BlockEntityIds.newId(id);
/*     */                   
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(newId));
/*     */                   WorldPackets.handleSpawners(newId, (CompoundTag)wrapper.passthrough(Type.NBT));
/*     */                 });
/*     */           }
/*     */         });
/*  65 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_17_1.UPDATE_LIGHT, wrapper -> {
/*     */           int chunkX = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           int chunkZ = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           if (((ChunkLightStorage)wrapper.user().get(ChunkLightStorage.class)).isLoaded(chunkX, chunkZ)) {
/*     */             if (!Via.getConfig().cache1_17Light()) {
/*     */               return;
/*     */             }
/*     */           } else {
/*     */             wrapper.cancel();
/*     */           } 
/*     */           
/*     */           boolean trustEdges = ((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue();
/*     */           
/*     */           long[] skyLightMask = (long[])wrapper.passthrough(Type.LONG_ARRAY_PRIMITIVE);
/*     */           
/*     */           long[] blockLightMask = (long[])wrapper.passthrough(Type.LONG_ARRAY_PRIMITIVE);
/*     */           
/*     */           long[] emptySkyLightMask = (long[])wrapper.passthrough(Type.LONG_ARRAY_PRIMITIVE);
/*     */           
/*     */           long[] emptyBlockLightMask = (long[])wrapper.passthrough(Type.LONG_ARRAY_PRIMITIVE);
/*     */           
/*     */           int skyLightLenght = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           byte[][] skyLight = new byte[skyLightLenght][];
/*     */           
/*     */           for (int i = 0; i < skyLightLenght; i++) {
/*     */             skyLight[i] = (byte[])wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
/*     */           }
/*     */           
/*     */           int blockLightLength = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           byte[][] blockLight = new byte[blockLightLength][];
/*     */           for (int j = 0; j < blockLightLength; j++) {
/*     */             blockLight[j] = (byte[])wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
/*     */           }
/*     */           ChunkLightStorage lightStorage = (ChunkLightStorage)wrapper.user().get(ChunkLightStorage.class);
/*     */           lightStorage.storeLight(chunkX, chunkZ, new ChunkLightStorage.ChunkLight(trustEdges, skyLightMask, blockLightMask, emptySkyLightMask, emptyBlockLightMask, skyLight, blockLight));
/*     */         });
/* 104 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_17_1.CHUNK_DATA, wrapper -> {
/*     */           EntityTracker tracker = protocol.getEntityRewriter().tracker(wrapper.user());
/*     */           
/*     */           Chunk oldChunk = (Chunk)wrapper.read((Type)new Chunk1_17Type(tracker.currentWorldSectionHeight()));
/*     */           
/*     */           List<BlockEntity> blockEntities = new ArrayList<>(oldChunk.getBlockEntities().size());
/*     */           
/*     */           for (CompoundTag tag : oldChunk.getBlockEntities()) {
/*     */             NumberTag xTag = (NumberTag)tag.get("x");
/*     */             
/*     */             NumberTag yTag = (NumberTag)tag.get("y");
/*     */             
/*     */             NumberTag zTag = (NumberTag)tag.get("z");
/*     */             
/*     */             StringTag idTag = (StringTag)tag.get("id");
/*     */             
/*     */             if (xTag == null || yTag == null || zTag == null || idTag == null) {
/*     */               continue;
/*     */             }
/*     */             
/*     */             String id = idTag.getValue();
/*     */             
/*     */             int typeId = protocol.getMappingData().blockEntityIds().getInt(Key.stripMinecraftNamespace(id));
/*     */             
/*     */             if (typeId == -1) {
/*     */               Via.getPlatform().getLogger().warning("Unknown block entity: " + id);
/*     */             }
/*     */             
/*     */             handleSpawners(typeId, tag);
/*     */             
/*     */             byte packedXZ = (byte)((xTag.asInt() & 0xF) << 4 | zTag.asInt() & 0xF);
/*     */             
/*     */             blockEntities.add(new BlockEntityImpl(packedXZ, yTag.asShort(), typeId, tag));
/*     */           } 
/*     */           
/*     */           int[] biomeData = oldChunk.getBiomeData();
/*     */           
/*     */           ChunkSection[] sections = oldChunk.getSections();
/*     */           
/*     */           for (int i = 0; i < sections.length; i++) {
/*     */             ChunkSectionImpl chunkSectionImpl;
/*     */             
/*     */             ChunkSection section = sections[i];
/*     */             if (section == null) {
/*     */               chunkSectionImpl = new ChunkSectionImpl();
/*     */               sections[i] = (ChunkSection)chunkSectionImpl;
/*     */               chunkSectionImpl.setNonAirBlocksCount(0);
/*     */               DataPaletteImpl blockPalette = new DataPaletteImpl(4096);
/*     */               blockPalette.addId(0);
/*     */               chunkSectionImpl.addPalette(PaletteType.BLOCKS, (DataPalette)blockPalette);
/*     */             } 
/*     */             DataPaletteImpl biomePalette = new DataPaletteImpl(64);
/*     */             chunkSectionImpl.addPalette(PaletteType.BIOMES, (DataPalette)biomePalette);
/*     */             int offset = i * 64;
/*     */             int biomeIndex = 0;
/*     */             for (int biomeArrayIndex = offset; biomeIndex < 64; biomeArrayIndex++) {
/*     */               int biome = biomeData[biomeArrayIndex];
/*     */               biomePalette.setIdAt(biomeIndex, (biome != -1) ? biome : 0);
/*     */               biomeIndex++;
/*     */             } 
/*     */           } 
/*     */           Chunk1_18 chunk1_18 = new Chunk1_18(oldChunk.getX(), oldChunk.getZ(), sections, oldChunk.getHeightMap(), blockEntities);
/*     */           wrapper.write((Type)new Chunk1_18Type(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(protocol.getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(tracker.biomesSent())), chunk1_18);
/*     */           ChunkLightStorage lightStorage = (ChunkLightStorage)wrapper.user().get(ChunkLightStorage.class);
/*     */           boolean alreadyLoaded = !lightStorage.addLoadedChunk(chunk1_18.getX(), chunk1_18.getZ());
/*     */           ChunkLightStorage.ChunkLight light = Via.getConfig().cache1_17Light() ? lightStorage.getLight(chunk1_18.getX(), chunk1_18.getZ()) : lightStorage.removeLight(chunk1_18.getX(), chunk1_18.getZ());
/*     */           if (light == null) {
/*     */             Via.getPlatform().getLogger().warning("No light data found for chunk at " + chunk1_18.getX() + ", " + chunk1_18.getZ() + ". Chunk was already loaded: " + alreadyLoaded);
/*     */             BitSet emptyLightMask = new BitSet();
/*     */             emptyLightMask.set(0, tracker.currentWorldSectionHeight() + 2);
/*     */             wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */             wrapper.write(Type.LONG_ARRAY_PRIMITIVE, new long[0]);
/*     */             wrapper.write(Type.LONG_ARRAY_PRIMITIVE, new long[0]);
/*     */             wrapper.write(Type.LONG_ARRAY_PRIMITIVE, emptyLightMask.toLongArray());
/*     */             wrapper.write(Type.LONG_ARRAY_PRIMITIVE, emptyLightMask.toLongArray());
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */           } else {
/*     */             wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(light.trustEdges()));
/*     */             wrapper.write(Type.LONG_ARRAY_PRIMITIVE, light.skyLightMask());
/*     */             wrapper.write(Type.LONG_ARRAY_PRIMITIVE, light.blockLightMask());
/*     */             wrapper.write(Type.LONG_ARRAY_PRIMITIVE, light.emptySkyLightMask());
/*     */             wrapper.write(Type.LONG_ARRAY_PRIMITIVE, light.emptyBlockLightMask());
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf((light.skyLight()).length));
/*     */             for (byte[] skyLight : light.skyLight()) {
/*     */               wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, skyLight);
/*     */             }
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf((light.blockLight()).length));
/*     */             for (byte[] blockLight : light.blockLight()) {
/*     */               wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, blockLight);
/*     */             }
/*     */           } 
/*     */         });
/* 197 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_17_1.UNLOAD_CHUNK, wrapper -> {
/*     */           int chunkX = ((Integer)wrapper.passthrough((Type)Type.INT)).intValue();
/*     */           int chunkZ = ((Integer)wrapper.passthrough((Type)Type.INT)).intValue();
/*     */           ((ChunkLightStorage)wrapper.user().get(ChunkLightStorage.class)).clear(chunkX, chunkZ);
/*     */         });
/*     */   }
/*     */   
/*     */   private static void handleSpawners(int typeId, CompoundTag tag) {
/* 205 */     if (typeId == 8) {
/* 206 */       Tag entity = tag.get("SpawnData");
/* 207 */       if (entity instanceof CompoundTag) {
/* 208 */         CompoundTag spawnData = new CompoundTag();
/* 209 */         tag.put("SpawnData", (Tag)spawnData);
/* 210 */         spawnData.put("entity", entity);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_18to1_17_1\packets\WorldPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
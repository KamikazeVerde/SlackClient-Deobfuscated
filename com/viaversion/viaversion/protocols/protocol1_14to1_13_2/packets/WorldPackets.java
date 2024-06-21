/*     */ package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.BlockFace;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.NibbleArray;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types.Chunk1_14Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.rewriter.BlockRewriter;
/*     */ import com.viaversion.viaversion.util.CompactArrayUtil;
/*     */ import java.util.Arrays;
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
/*     */   public static final int SERVERSIDE_VIEW_DISTANCE = 64;
/*  46 */   private static final byte[] FULL_LIGHT = new byte[2048];
/*     */   public static int air;
/*     */   public static int voidAir;
/*     */   public static int caveAir;
/*     */   
/*     */   static {
/*  52 */     Arrays.fill(FULL_LIGHT, (byte)-1);
/*     */   }
/*     */   
/*     */   public static void register(final Protocol1_14To1_13_2 protocol) {
/*  56 */     BlockRewriter<ClientboundPackets1_13> blockRewriter = new BlockRewriter((Protocol)protocol, null);
/*     */     
/*  58 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.BLOCK_BREAK_ANIMATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  61 */             map((Type)Type.VAR_INT);
/*  62 */             map(Type.POSITION, Type.POSITION1_14);
/*  63 */             map((Type)Type.BYTE);
/*     */           }
/*     */         });
/*  66 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.BLOCK_ENTITY_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  69 */             map(Type.POSITION, Type.POSITION1_14);
/*     */           }
/*     */         });
/*  72 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.BLOCK_ACTION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  75 */             map(Type.POSITION, Type.POSITION1_14);
/*  76 */             map((Type)Type.UNSIGNED_BYTE);
/*  77 */             map((Type)Type.UNSIGNED_BYTE);
/*  78 */             map((Type)Type.VAR_INT);
/*  79 */             handler(wrapper -> wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(protocol.getMappingData().getNewBlockId(((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue()))));
/*     */           }
/*     */         });
/*  82 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  85 */             map(Type.POSITION, Type.POSITION1_14);
/*  86 */             map((Type)Type.VAR_INT);
/*  87 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(protocol.getMappingData().getNewBlockStateId(id)));
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/*  95 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SERVER_DIFFICULTY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  98 */             map((Type)Type.UNSIGNED_BYTE);
/*  99 */             handler(wrapper -> wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false)));
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 105 */     blockRewriter.registerMultiBlockChange((ClientboundPacketType)ClientboundPackets1_13.MULTI_BLOCK_CHANGE);
/*     */     
/* 107 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.EXPLOSION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 110 */             map((Type)Type.FLOAT);
/* 111 */             map((Type)Type.FLOAT);
/* 112 */             map((Type)Type.FLOAT);
/* 113 */             map((Type)Type.FLOAT);
/* 114 */             handler(wrapper -> {
/*     */                   for (int i = 0; i < 3; i++) {
/*     */                     float coord = ((Float)wrapper.get((Type)Type.FLOAT, i)).floatValue();
/*     */                     
/*     */                     if (coord < 0.0F) {
/*     */                       coord = (int)coord;
/*     */                       
/*     */                       wrapper.set((Type)Type.FLOAT, i, Float.valueOf(coord));
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 127 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.CHUNK_DATA, wrapper -> {
/*     */           ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */           
/*     */           Chunk chunk = (Chunk)wrapper.read((Type)new Chunk1_13Type(clientWorld));
/*     */           
/*     */           wrapper.write((Type)new Chunk1_14Type(), chunk);
/*     */           
/*     */           int[] motionBlocking = new int[256];
/*     */           
/*     */           int[] worldSurface = new int[256];
/*     */           
/*     */           for (int s = 0; s < (chunk.getSections()).length; s++) {
/*     */             ChunkSection section = chunk.getSections()[s];
/*     */             
/*     */             if (section != null) {
/*     */               DataPalette blocks = section.palette(PaletteType.BLOCKS);
/*     */               
/*     */               boolean hasBlock = false;
/*     */               
/*     */               for (int j = 0; j < blocks.size(); j++) {
/*     */                 int old = blocks.idByIndex(j);
/*     */                 
/*     */                 int newId = protocol.getMappingData().getNewBlockStateId(old);
/*     */                 
/*     */                 if (!hasBlock && newId != air && newId != voidAir && newId != caveAir) {
/*     */                   hasBlock = true;
/*     */                 }
/*     */                 
/*     */                 blocks.setIdByIndex(j, newId);
/*     */               } 
/*     */               
/*     */               if (!hasBlock) {
/*     */                 section.setNonAirBlocksCount(0);
/*     */               } else {
/*     */                 int nonAirBlockCount = 0;
/*     */                 
/*     */                 int sy = s << 4;
/*     */                 
/*     */                 for (int idx = 0; idx < 4096; idx++) {
/*     */                   int id = blocks.idAt(idx);
/*     */                   
/*     */                   if (id != air && id != voidAir && id != caveAir) {
/*     */                     nonAirBlockCount++;
/*     */                     
/*     */                     int xz = idx & 0xFF;
/*     */                     
/*     */                     int y = ChunkSection.yFromIndex(idx);
/*     */                     
/*     */                     worldSurface[xz] = sy + y + 1;
/*     */                     if (protocol.getMappingData().getMotionBlocking().contains(id)) {
/*     */                       motionBlocking[xz] = sy + y + 1;
/*     */                     }
/*     */                     if (Via.getConfig().isNonFullBlockLightFix() && protocol.getMappingData().getNonFullBlocks().contains(id)) {
/*     */                       int x = ChunkSection.xFromIndex(idx);
/*     */                       int z = ChunkSection.zFromIndex(idx);
/*     */                       setNonFullLight(chunk, section, s, x, y, z);
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */                 section.setNonAirBlocksCount(nonAirBlockCount);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           CompoundTag heightMap = new CompoundTag();
/*     */           heightMap.put("MOTION_BLOCKING", (Tag)new LongArrayTag(encodeHeightMap(motionBlocking)));
/*     */           heightMap.put("WORLD_SURFACE", (Tag)new LongArrayTag(encodeHeightMap(worldSurface)));
/*     */           chunk.setHeightMap(heightMap);
/*     */           PacketWrapper lightPacket = wrapper.create((PacketType)ClientboundPackets1_14.UPDATE_LIGHT);
/*     */           lightPacket.write((Type)Type.VAR_INT, Integer.valueOf(chunk.getX()));
/*     */           lightPacket.write((Type)Type.VAR_INT, Integer.valueOf(chunk.getZ()));
/*     */           int skyLightMask = chunk.isFullChunk() ? 262143 : 0;
/*     */           int blockLightMask = 0;
/*     */           for (int i = 0; i < (chunk.getSections()).length; i++) {
/*     */             ChunkSection sec = chunk.getSections()[i];
/*     */             if (sec != null) {
/*     */               if (!chunk.isFullChunk() && sec.getLight().hasSkyLight()) {
/*     */                 skyLightMask |= 1 << i + 1;
/*     */               }
/*     */               blockLightMask |= 1 << i + 1;
/*     */             } 
/*     */           } 
/*     */           lightPacket.write((Type)Type.VAR_INT, Integer.valueOf(skyLightMask));
/*     */           lightPacket.write((Type)Type.VAR_INT, Integer.valueOf(blockLightMask));
/*     */           lightPacket.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */           lightPacket.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */           if (chunk.isFullChunk()) {
/*     */             lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, FULL_LIGHT);
/*     */           }
/*     */           for (ChunkSection section : chunk.getSections()) {
/*     */             if (section == null || !section.getLight().hasSkyLight()) {
/*     */               if (chunk.isFullChunk()) {
/*     */                 lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, FULL_LIGHT);
/*     */               }
/*     */             } else {
/*     */               lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, section.getLight().getSkyLight());
/*     */             } 
/*     */           } 
/*     */           if (chunk.isFullChunk()) {
/*     */             lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, FULL_LIGHT);
/*     */           }
/*     */           for (ChunkSection section : chunk.getSections()) {
/*     */             if (section != null) {
/*     */               lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, section.getLight().getBlockLight());
/*     */             }
/*     */           } 
/*     */           EntityTracker1_14 entityTracker = (EntityTracker1_14)wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
/*     */           int diffX = Math.abs(entityTracker.getChunkCenterX() - chunk.getX());
/*     */           int diffZ = Math.abs(entityTracker.getChunkCenterZ() - chunk.getZ());
/*     */           if (entityTracker.isForceSendCenterChunk() || diffX >= 64 || diffZ >= 64) {
/*     */             PacketWrapper fakePosLook = wrapper.create((PacketType)ClientboundPackets1_14.UPDATE_VIEW_POSITION);
/*     */             fakePosLook.write((Type)Type.VAR_INT, Integer.valueOf(chunk.getX()));
/*     */             fakePosLook.write((Type)Type.VAR_INT, Integer.valueOf(chunk.getZ()));
/*     */             fakePosLook.send(Protocol1_14To1_13_2.class);
/*     */             entityTracker.setChunkCenterX(chunk.getX());
/*     */             entityTracker.setChunkCenterZ(chunk.getZ());
/*     */           } 
/*     */           lightPacket.send(Protocol1_14To1_13_2.class);
/*     */           for (ChunkSection section : chunk.getSections()) {
/*     */             if (section != null) {
/*     */               section.setLight(null);
/*     */             }
/*     */           } 
/*     */         });
/* 250 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 253 */             map((Type)Type.INT);
/* 254 */             map(Type.POSITION, Type.POSITION1_14);
/* 255 */             map((Type)Type.INT);
/* 256 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   int data = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   if (id == 1010) {
/*     */                     wrapper.set((Type)Type.INT, 1, Integer.valueOf(protocol.getMappingData().getNewItemId(data)));
/*     */                   } else if (id == 2001) {
/*     */                     wrapper.set((Type)Type.INT, 1, Integer.valueOf(protocol.getMappingData().getNewBlockStateId(data)));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 268 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.MAP_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 271 */             map((Type)Type.VAR_INT);
/* 272 */             map((Type)Type.BYTE);
/* 273 */             map((Type)Type.BOOLEAN);
/* 274 */             handler(wrapper -> wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false)));
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 280 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 283 */             map((Type)Type.INT);
/* 284 */             handler(wrapper -> {
/*     */                   ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   
/*     */                   int dimensionId = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   clientWorld.setEnvironment(dimensionId);
/*     */                   EntityTracker1_14 entityTracker = (EntityTracker1_14)wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
/*     */                   entityTracker.setForceSendCenterChunk(true);
/*     */                 });
/* 292 */             handler(wrapper -> {
/*     */                   short difficulty = ((Short)wrapper.read((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                   PacketWrapper difficultyPacket = wrapper.create((PacketType)ClientboundPackets1_14.SERVER_DIFFICULTY);
/*     */                   difficultyPacket.write((Type)Type.UNSIGNED_BYTE, Short.valueOf(difficulty));
/*     */                   difficultyPacket.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                   difficultyPacket.scheduleSend(protocol.getClass());
/*     */                 });
/* 299 */             handler(wrapper -> {
/*     */                   wrapper.send(Protocol1_14To1_13_2.class);
/*     */                   
/*     */                   wrapper.cancel();
/*     */                   
/*     */                   WorldPackets.sendViewDistancePacket(wrapper.user());
/*     */                 });
/*     */           }
/*     */         });
/* 308 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_POSITION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 311 */             map(Type.POSITION, Type.POSITION1_14);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   static void sendViewDistancePacket(UserConnection connection) throws Exception {
/* 317 */     PacketWrapper setViewDistance = PacketWrapper.create((PacketType)ClientboundPackets1_14.UPDATE_VIEW_DISTANCE, null, connection);
/* 318 */     setViewDistance.write((Type)Type.VAR_INT, Integer.valueOf(64));
/* 319 */     setViewDistance.send(Protocol1_14To1_13_2.class);
/*     */   }
/*     */   
/*     */   private static long[] encodeHeightMap(int[] heightMap) {
/* 323 */     return CompactArrayUtil.createCompactArray(9, heightMap.length, i -> heightMap[i]);
/*     */   }
/*     */   
/*     */   private static void setNonFullLight(Chunk chunk, ChunkSection section, int ySection, int x, int y, int z) {
/* 327 */     int skyLight = 0;
/* 328 */     int blockLight = 0;
/* 329 */     for (BlockFace blockFace : BlockFace.values()) {
/* 330 */       NibbleArray skyLightArray = section.getLight().getSkyLightNibbleArray();
/* 331 */       NibbleArray blockLightArray = section.getLight().getBlockLightNibbleArray();
/* 332 */       int neighbourX = x + blockFace.modX();
/* 333 */       int neighbourY = y + blockFace.modY();
/* 334 */       int neighbourZ = z + blockFace.modZ();
/*     */       
/* 336 */       if (blockFace.modX() != 0)
/*     */       
/* 338 */       { if (neighbourX == 16 || neighbourX == -1)
/* 339 */           continue;  } else if (blockFace.modY() != 0)
/* 340 */       { if (neighbourY == 16 || neighbourY == -1) {
/* 341 */           if (neighbourY == 16) {
/* 342 */             ySection++;
/* 343 */             neighbourY = 0;
/*     */           } else {
/* 345 */             ySection--;
/* 346 */             neighbourY = 15;
/*     */           } 
/*     */           
/* 349 */           if (ySection == (chunk.getSections()).length || ySection == -1)
/*     */             continue; 
/* 351 */           ChunkSection newSection = chunk.getSections()[ySection];
/* 352 */           if (newSection == null)
/*     */             continue; 
/* 354 */           skyLightArray = newSection.getLight().getSkyLightNibbleArray();
/* 355 */           blockLightArray = newSection.getLight().getBlockLightNibbleArray();
/*     */         }  }
/* 357 */       else if (blockFace.modZ() != 0)
/*     */       
/* 359 */       { if (neighbourZ == 16 || neighbourZ == -1)
/*     */           continue;  }
/*     */       
/* 362 */       if (blockLightArray != null && blockLight != 15) {
/* 363 */         int neighbourBlockLight = blockLightArray.get(neighbourX, neighbourY, neighbourZ);
/* 364 */         if (neighbourBlockLight == 15) {
/* 365 */           blockLight = 14;
/* 366 */         } else if (neighbourBlockLight > blockLight) {
/* 367 */           blockLight = neighbourBlockLight - 1;
/*     */         } 
/*     */       } 
/* 370 */       if (skyLightArray != null && skyLight != 15) {
/* 371 */         int neighbourSkyLight = skyLightArray.get(neighbourX, neighbourY, neighbourZ);
/* 372 */         if (neighbourSkyLight == 15) {
/* 373 */           if (blockFace.modY() == 1)
/*     */           
/* 375 */           { skyLight = 15; }
/*     */           
/*     */           else
/*     */           
/* 379 */           { skyLight = 14; } 
/* 380 */         } else if (neighbourSkyLight > skyLight) {
/* 381 */           skyLight = neighbourSkyLight - 1;
/*     */         } 
/*     */       } 
/*     */       continue;
/*     */     } 
/* 386 */     if (skyLight != 0) {
/* 387 */       if (!section.getLight().hasSkyLight()) {
/* 388 */         byte[] newSkyLight = new byte[2028];
/* 389 */         section.getLight().setSkyLight(newSkyLight);
/*     */       } 
/*     */       
/* 392 */       section.getLight().getSkyLightNibbleArray().set(x, y, z, skyLight);
/*     */     } 
/* 394 */     if (blockLight != 0) {
/* 395 */       section.getLight().getBlockLightNibbleArray().set(x, y, z, blockLight);
/*     */     }
/*     */   }
/*     */   
/*     */   private static long getChunkIndex(int x, int z) {
/* 400 */     return (x & 0x3FFFFFFL) << 38L | z & 0x3FFFFFFL;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_14to1_13_2\packets\WorldPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
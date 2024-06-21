/*     */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.BlockFace;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.item.DataItem;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.Chunk1_9_1_2Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.CommandBlockProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.sounds.Effect;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.sounds.SoundEffect;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.ClientChunks;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.types.Chunk1_8Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.types.ChunkBulk1_8Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Optional;
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
/*     */   public static void register(Protocol1_9To1_8 protocol) {
/*  53 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.UPDATE_SIGN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  56 */             map(Type.POSITION);
/*  57 */             map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
/*  58 */             map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
/*  59 */             map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
/*  60 */             map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
/*     */           }
/*     */         });
/*     */     
/*  64 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  67 */             map((Type)Type.INT);
/*  68 */             map(Type.POSITION);
/*  69 */             map((Type)Type.INT);
/*  70 */             map((Type)Type.BOOLEAN);
/*     */             
/*  72 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   id = Effect.getNewId(id);
/*     */                   
/*     */                   wrapper.set((Type)Type.INT, 0, Integer.valueOf(id));
/*     */                 });
/*  79 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   if (id == 2002) {
/*     */                     int data = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                     int newData = ItemRewriter.getNewEffectID(data);
/*     */                     wrapper.set((Type)Type.INT, 1, Integer.valueOf(newData));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  90 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.NAMED_SOUND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  93 */             map(Type.STRING);
/*     */ 
/*     */ 
/*     */             
/*  97 */             handler(wrapper -> {
/*     */                   String name = (String)wrapper.get(Type.STRING, 0);
/*     */                   
/*     */                   SoundEffect effect = SoundEffect.getByName(name);
/*     */                   
/*     */                   int catid = 0;
/*     */                   String newname = name;
/*     */                   if (effect != null) {
/*     */                     catid = effect.getCategory().getId();
/*     */                     newname = effect.getNewName();
/*     */                   } 
/*     */                   wrapper.set(Type.STRING, 0, newname);
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(catid));
/*     */                   if (effect != null && effect.isBreaksound()) {
/*     */                     EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                     int x = ((Integer)wrapper.passthrough((Type)Type.INT)).intValue();
/*     */                     int y = ((Integer)wrapper.passthrough((Type)Type.INT)).intValue();
/*     */                     int z = ((Integer)wrapper.passthrough((Type)Type.INT)).intValue();
/*     */                     if (tracker.interactedBlockRecently((int)Math.floor(x / 8.0D), (int)Math.floor(y / 8.0D), (int)Math.floor(z / 8.0D))) {
/*     */                       wrapper.cancel();
/*     */                     }
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 122 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.CHUNK_DATA, wrapper -> {
/*     */           ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */           
/*     */           ClientChunks clientChunks = (ClientChunks)wrapper.user().get(ClientChunks.class);
/*     */           
/*     */           Chunk chunk = (Chunk)wrapper.read((Type)new Chunk1_8Type(clientWorld));
/*     */           
/*     */           long chunkHash = ClientChunks.toLong(chunk.getX(), chunk.getZ());
/*     */           
/*     */           if (chunk.isFullChunk() && chunk.getBitmask() == 0) {
/*     */             wrapper.setPacketType((PacketType)ClientboundPackets1_9.UNLOAD_CHUNK);
/*     */             
/*     */             wrapper.write((Type)Type.INT, Integer.valueOf(chunk.getX()));
/*     */             
/*     */             wrapper.write((Type)Type.INT, Integer.valueOf(chunk.getZ()));
/*     */             
/*     */             CommandBlockProvider provider = (CommandBlockProvider)Via.getManager().getProviders().get(CommandBlockProvider.class);
/*     */             
/*     */             provider.unloadChunk(wrapper.user(), chunk.getX(), chunk.getZ());
/*     */             
/*     */             clientChunks.getLoadedChunks().remove(Long.valueOf(chunkHash));
/*     */             
/*     */             if (Via.getConfig().isChunkBorderFix()) {
/*     */               for (BlockFace face : BlockFace.HORIZONTAL) {
/*     */                 int chunkX = chunk.getX() + face.modX();
/*     */                 
/*     */                 int chunkZ = chunk.getZ() + face.modZ();
/*     */                 
/*     */                 if (!clientChunks.getLoadedChunks().contains(Long.valueOf(ClientChunks.toLong(chunkX, chunkZ)))) {
/*     */                   PacketWrapper unloadChunk = wrapper.create((PacketType)ClientboundPackets1_9.UNLOAD_CHUNK);
/*     */                   unloadChunk.write((Type)Type.INT, Integer.valueOf(chunkX));
/*     */                   unloadChunk.write((Type)Type.INT, Integer.valueOf(chunkZ));
/*     */                   unloadChunk.send(Protocol1_9To1_8.class);
/*     */                 } 
/*     */               } 
/*     */             }
/*     */           } else {
/*     */             Chunk1_9_1_2Type chunk1_9_1_2Type = new Chunk1_9_1_2Type(clientWorld);
/*     */             wrapper.write((Type)chunk1_9_1_2Type, chunk);
/*     */             clientChunks.getLoadedChunks().add(Long.valueOf(chunkHash));
/*     */             if (Via.getConfig().isChunkBorderFix()) {
/*     */               for (BlockFace face : BlockFace.HORIZONTAL) {
/*     */                 int chunkX = chunk.getX() + face.modX();
/*     */                 int chunkZ = chunk.getZ() + face.modZ();
/*     */                 if (!clientChunks.getLoadedChunks().contains(Long.valueOf(ClientChunks.toLong(chunkX, chunkZ)))) {
/*     */                   PacketWrapper emptyChunk = wrapper.create((PacketType)ClientboundPackets1_9.CHUNK_DATA);
/*     */                   BaseChunk baseChunk = new BaseChunk(chunkX, chunkZ, true, false, 0, new com.viaversion.viaversion.api.minecraft.chunks.ChunkSection[16], new int[256], new ArrayList());
/*     */                   emptyChunk.write((Type)chunk1_9_1_2Type, baseChunk);
/*     */                   emptyChunk.send(Protocol1_9To1_8.class);
/*     */                 } 
/*     */               } 
/*     */             }
/*     */           } 
/*     */         });
/* 176 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.MAP_BULK_CHUNK, null, wrapper -> {
/*     */           wrapper.cancel();
/*     */           
/*     */           ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */           
/*     */           ClientChunks clientChunks = (ClientChunks)wrapper.user().get(ClientChunks.class);
/*     */           
/*     */           Chunk[] chunks = (Chunk[])wrapper.read((Type)new ChunkBulk1_8Type(clientWorld));
/*     */           
/*     */           Chunk1_9_1_2Type chunk1_9_1_2Type = new Chunk1_9_1_2Type(clientWorld);
/*     */           
/*     */           for (Chunk chunk : chunks) {
/*     */             PacketWrapper chunkData = wrapper.create((PacketType)ClientboundPackets1_9.CHUNK_DATA);
/*     */             
/*     */             chunkData.write((Type)chunk1_9_1_2Type, chunk);
/*     */             chunkData.send(Protocol1_9To1_8.class);
/*     */             clientChunks.getLoadedChunks().add(Long.valueOf(ClientChunks.toLong(chunk.getX(), chunk.getZ())));
/*     */             if (Via.getConfig().isChunkBorderFix()) {
/*     */               for (BlockFace face : BlockFace.HORIZONTAL) {
/*     */                 int chunkX = chunk.getX() + face.modX();
/*     */                 int chunkZ = chunk.getZ() + face.modZ();
/*     */                 if (!clientChunks.getLoadedChunks().contains(Long.valueOf(ClientChunks.toLong(chunkX, chunkZ)))) {
/*     */                   PacketWrapper emptyChunk = wrapper.create((PacketType)ClientboundPackets1_9.CHUNK_DATA);
/*     */                   BaseChunk baseChunk = new BaseChunk(chunkX, chunkZ, true, false, 0, new com.viaversion.viaversion.api.minecraft.chunks.ChunkSection[16], new int[256], new ArrayList());
/*     */                   emptyChunk.write((Type)chunk1_9_1_2Type, baseChunk);
/*     */                   emptyChunk.send(Protocol1_9To1_8.class);
/*     */                 } 
/*     */               } 
/*     */             }
/*     */           } 
/*     */         });
/* 207 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.BLOCK_ENTITY_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 210 */             map(Type.POSITION);
/* 211 */             map((Type)Type.UNSIGNED_BYTE);
/* 212 */             map(Type.NBT);
/* 213 */             handler(wrapper -> {
/*     */                   int action = ((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   
/*     */                   if (action == 1) {
/*     */                     CompoundTag tag = (CompoundTag)wrapper.get(Type.NBT, 0);
/*     */                     
/*     */                     if (tag != null) {
/*     */                       if (tag.contains("EntityId")) {
/*     */                         String entity = (String)tag.get("EntityId").getValue();
/*     */                         
/*     */                         CompoundTag spawn = new CompoundTag();
/*     */                         
/*     */                         spawn.put("id", (Tag)new StringTag(entity));
/*     */                         
/*     */                         tag.put("SpawnData", (Tag)spawn);
/*     */                       } else {
/*     */                         CompoundTag spawn = new CompoundTag();
/*     */                         spawn.put("id", (Tag)new StringTag("AreaEffectCloud"));
/*     */                         tag.put("SpawnData", (Tag)spawn);
/*     */                       } 
/*     */                     }
/*     */                   } 
/*     */                   if (action == 2) {
/*     */                     CommandBlockProvider provider = (CommandBlockProvider)Via.getManager().getProviders().get(CommandBlockProvider.class);
/*     */                     provider.addOrUpdateBlock(wrapper.user(), (Position)wrapper.get(Type.POSITION, 0), (CompoundTag)wrapper.get(Type.NBT, 0));
/*     */                     wrapper.cancel();
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 243 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9.UPDATE_SIGN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 246 */             map(Type.POSITION);
/* 247 */             map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
/* 248 */             map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
/* 249 */             map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
/* 250 */             map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
/*     */           }
/*     */         });
/*     */     
/* 254 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9.PLAYER_DIGGING, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 257 */             map((Type)Type.VAR_INT);
/* 258 */             map(Type.POSITION);
/* 259 */             handler(wrapper -> {
/*     */                   int status = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   if (status == 6) {
/*     */                     wrapper.cancel();
/*     */                   }
/*     */                 });
/* 265 */             handler(wrapper -> {
/*     */                   int status = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   if (status == 5 || status == 4 || status == 3) {
/*     */                     EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                     if (entityTracker.isBlocking()) {
/*     */                       entityTracker.setBlocking(false);
/*     */                       if (!Via.getConfig().isShowShieldWhenSwordInHand()) {
/*     */                         entityTracker.setSecondHand(null);
/*     */                       }
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 280 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9.USE_ITEM, null, wrapper -> {
/*     */           int hand = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           wrapper.clearInputBuffer();
/*     */           
/*     */           wrapper.setPacketType((PacketType)ServerboundPackets1_8.PLAYER_BLOCK_PLACEMENT);
/*     */           
/*     */           wrapper.write(Type.POSITION, new Position(-1, (short)-1, -1));
/*     */           
/*     */           wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)255));
/*     */           
/*     */           Item item = Protocol1_9To1_8.getHandItem(wrapper.user());
/*     */           
/*     */           if (Via.getConfig().isShieldBlocking()) {
/*     */             EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */             
/*     */             boolean showShieldWhenSwordInHand = Via.getConfig().isShowShieldWhenSwordInHand();
/* 297 */             boolean isSword = showShieldWhenSwordInHand ? tracker.hasSwordInHand() : ((item != null && Protocol1_9To1_8.isSword(item.identifier())));
/*     */ 
/*     */ 
/*     */             
/*     */             if (isSword) {
/*     */               if (hand == 0 && !tracker.isBlocking()) {
/*     */                 tracker.setBlocking(true);
/*     */ 
/*     */ 
/*     */                 
/*     */                 if (!showShieldWhenSwordInHand && tracker.getItemInSecondHand() == null) {
/*     */                   DataItem dataItem = new DataItem(442, (byte)1, (short)0, null);
/*     */ 
/*     */                   
/*     */                   tracker.setSecondHand((Item)dataItem);
/*     */                 } 
/*     */               } 
/*     */ 
/*     */               
/* 316 */               boolean blockUsingMainHand = (Via.getConfig().isNoDelayShieldBlocking() && !showShieldWhenSwordInHand);
/*     */               
/*     */               if ((blockUsingMainHand && hand == 1) || (!blockUsingMainHand && hand == 0)) {
/*     */                 wrapper.cancel();
/*     */               }
/*     */             } else {
/*     */               if (!showShieldWhenSwordInHand) {
/*     */                 tracker.setSecondHand(null);
/*     */               }
/*     */               
/*     */               tracker.setBlocking(false);
/*     */             } 
/*     */           } 
/*     */           
/*     */           wrapper.write(Type.ITEM, item);
/*     */           
/*     */           wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)0));
/*     */           
/*     */           wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)0));
/*     */           wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)0));
/*     */         });
/* 337 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9.PLAYER_BLOCK_PLACEMENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 340 */             map(Type.POSITION);
/* 341 */             map((Type)Type.VAR_INT, (Type)Type.UNSIGNED_BYTE);
/* 342 */             handler(wrapper -> {
/*     */                   int hand = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue(); if (hand != 0)
/*     */                     wrapper.cancel(); 
/*     */                 });
/* 346 */             handler(wrapper -> {
/*     */                   Item item = Protocol1_9To1_8.getHandItem(wrapper.user());
/*     */                   wrapper.write(Type.ITEM, item);
/*     */                 });
/* 350 */             map((Type)Type.UNSIGNED_BYTE);
/* 351 */             map((Type)Type.UNSIGNED_BYTE);
/* 352 */             map((Type)Type.UNSIGNED_BYTE);
/*     */ 
/*     */             
/* 355 */             handler(wrapper -> {
/*     */                   int face = ((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   if (face == 255) {
/*     */                     return;
/*     */                   }
/*     */                   Position p = (Position)wrapper.get(Type.POSITION, 0);
/*     */                   int x = p.x();
/*     */                   int y = p.y();
/*     */                   int z = p.z();
/*     */                   switch (face) {
/*     */                     case 0:
/*     */                       y--;
/*     */                       break;
/*     */                     
/*     */                     case 1:
/*     */                       y++;
/*     */                       break;
/*     */                     case 2:
/*     */                       z--;
/*     */                       break;
/*     */                     case 3:
/*     */                       z++;
/*     */                       break;
/*     */                     case 4:
/*     */                       x--;
/*     */                       break;
/*     */                     case 5:
/*     */                       x++;
/*     */                       break;
/*     */                   } 
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   tracker.addBlockInteraction(new Position(x, y, z));
/*     */                 });
/* 388 */             handler(wrapper -> {
/*     */                   CommandBlockProvider provider = (CommandBlockProvider)Via.getManager().getProviders().get(CommandBlockProvider.class);
/*     */                   Position pos = (Position)wrapper.get(Type.POSITION, 0);
/*     */                   Optional<CompoundTag> tag = provider.get(wrapper.user(), pos);
/*     */                   if (tag.isPresent()) {
/*     */                     PacketWrapper updateBlockEntity = PacketWrapper.create((PacketType)ClientboundPackets1_9.BLOCK_ENTITY_DATA, null, wrapper.user());
/*     */                     updateBlockEntity.write(Type.POSITION, pos);
/*     */                     updateBlockEntity.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)2));
/*     */                     updateBlockEntity.write(Type.NBT, tag.get());
/*     */                     updateBlockEntity.scheduleSend(Protocol1_9To1_8.class);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\packets\WorldPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
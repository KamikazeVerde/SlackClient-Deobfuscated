/*     */ package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;
/*     */ 
/*     */ import com.google.common.primitives.Ints;
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
/*     */ import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.FlowerPotHandler;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
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
/*     */ import com.viaversion.viaversion.libs.opennbt.conversion.ConverterRegistry;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.BlockIdData;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.SpawnEggRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.util.Key;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
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
/*     */ public class BlockItemPackets1_13
/*     */   extends ItemRewriter<ClientboundPackets1_13, ServerboundPackets1_12_1, Protocol1_12_2To1_13>
/*     */ {
/*  69 */   private final Map<String, String> enchantmentMappings = new HashMap<>();
/*     */   private final String extraNbtTag;
/*     */   
/*     */   public BlockItemPackets1_13(Protocol1_12_2To1_13 protocol) {
/*  73 */     super((BackwardsProtocol)protocol);
/*  74 */     this.extraNbtTag = "VB|" + protocol.getClass().getSimpleName() + "|2";
/*     */   }
/*     */   
/*     */   public static boolean isDamageable(int id) {
/*  78 */     return ((id >= 256 && id <= 259) || id == 261 || (id >= 267 && id <= 279) || (id >= 283 && id <= 286) || (id >= 290 && id <= 294) || (id >= 298 && id <= 317) || id == 346 || id == 359 || id == 398 || id == 442 || id == 443);
/*     */   }
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
/*     */   protected void registerPackets() {
/*  93 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.COOLDOWN, wrapper -> {
/*     */           int itemId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           int oldId = ((Protocol1_12_2To1_13)this.protocol).getMappingData().getItemMappings().getNewId(itemId);
/*     */           
/*     */           if (oldId == -1) {
/*     */             wrapper.cancel();
/*     */             
/*     */             return;
/*     */           } 
/*     */           if (SpawnEggRewriter.getEntityId(oldId).isPresent()) {
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(6128));
/*     */             return;
/*     */           } 
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(oldId >> 4));
/*     */         });
/* 109 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.BLOCK_ACTION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 112 */             map(Type.POSITION);
/* 113 */             map((Type)Type.UNSIGNED_BYTE);
/* 114 */             map((Type)Type.UNSIGNED_BYTE);
/* 115 */             map((Type)Type.VAR_INT);
/* 116 */             handler(wrapper -> {
/*     */                   int blockId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   if (blockId == 73) {
/*     */                     blockId = 25;
/*     */                   } else if (blockId == 99) {
/*     */                     blockId = 33;
/*     */                   } else if (blockId == 92) {
/*     */                     blockId = 29;
/*     */                   } else if (blockId == 142) {
/*     */                     blockId = 54;
/*     */                   } else if (blockId == 305) {
/*     */                     blockId = 146;
/*     */                   } else if (blockId == 249) {
/*     */                     blockId = 130;
/*     */                   } else if (blockId == 257) {
/*     */                     blockId = 138;
/*     */                   } else if (blockId == 140) {
/*     */                     blockId = 52;
/*     */                   } else if (blockId == 472) {
/*     */                     blockId = 209;
/*     */                   } else if (blockId >= 483 && blockId <= 498) {
/*     */                     blockId = blockId - 483 + 219;
/*     */                   } 
/*     */                   
/*     */                   wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(blockId));
/*     */                 });
/*     */           }
/*     */         });
/* 145 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.BLOCK_ENTITY_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 148 */             map(Type.POSITION);
/* 149 */             map((Type)Type.UNSIGNED_BYTE);
/* 150 */             map(Type.NBT);
/*     */             
/* 152 */             handler(wrapper -> {
/*     */                   BackwardsBlockEntityProvider provider = (BackwardsBlockEntityProvider)Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
/*     */ 
/*     */ 
/*     */                   
/*     */                   if (((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue() == 5) {
/*     */                     wrapper.cancel();
/*     */                   }
/*     */ 
/*     */ 
/*     */                   
/*     */                   wrapper.set(Type.NBT, 0, provider.transform(wrapper.user(), (Position)wrapper.get(Type.POSITION, 0), (CompoundTag)wrapper.get(Type.NBT, 0)));
/*     */                 });
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 170 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.UNLOAD_CHUNK, wrapper -> {
/*     */           int chunkMinX = ((Integer)wrapper.passthrough((Type)Type.INT)).intValue() << 4;
/*     */           
/*     */           int chunkMinZ = ((Integer)wrapper.passthrough((Type)Type.INT)).intValue() << 4;
/*     */           
/*     */           int chunkMaxX = chunkMinX + 15;
/*     */           
/*     */           int chunkMaxZ = chunkMinZ + 15;
/*     */           
/*     */           BackwardsBlockStorage blockStorage = (BackwardsBlockStorage)wrapper.user().get(BackwardsBlockStorage.class);
/*     */           
/*     */           blockStorage.getBlocks().entrySet().removeIf(());
/*     */         });
/*     */     
/* 184 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 187 */             map(Type.POSITION);
/*     */             
/* 189 */             handler(wrapper -> {
/*     */                   int blockState = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */ 
/*     */                   
/*     */                   Position position = (Position)wrapper.get(Type.POSITION, 0);
/*     */                   
/*     */                   BackwardsBlockStorage storage = (BackwardsBlockStorage)wrapper.user().get(BackwardsBlockStorage.class);
/*     */                   
/*     */                   storage.checkAndStore(position, blockState);
/*     */                   
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(((Protocol1_12_2To1_13)BlockItemPackets1_13.this.protocol).getMappingData().getNewBlockStateId(blockState)));
/*     */                   
/*     */                   BlockItemPackets1_13.flowerPotSpecialTreatment(wrapper.user(), blockState, position);
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 206 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.MULTI_BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 209 */             map((Type)Type.INT);
/* 210 */             map((Type)Type.INT);
/* 211 */             map(Type.BLOCK_CHANGE_RECORD_ARRAY);
/* 212 */             handler(wrapper -> {
/*     */                   BackwardsBlockStorage storage = (BackwardsBlockStorage)wrapper.user().get(BackwardsBlockStorage.class);
/*     */ 
/*     */                   
/*     */                   for (BlockChangeRecord record : (BlockChangeRecord[])wrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
/*     */                     int chunkX = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */ 
/*     */                     
/*     */                     int chunkZ = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */ 
/*     */                     
/*     */                     int block = record.getBlockId();
/*     */                     
/*     */                     Position position = new Position(record.getSectionX() + chunkX * 16, record.getY(), record.getSectionZ() + chunkZ * 16);
/*     */                     
/*     */                     storage.checkAndStore(position, block);
/*     */                     
/*     */                     BlockItemPackets1_13.flowerPotSpecialTreatment(wrapper.user(), block, position);
/*     */                     
/*     */                     record.setBlockId(((Protocol1_12_2To1_13)BlockItemPackets1_13.this.protocol).getMappingData().getNewBlockStateId(block));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 237 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.WINDOW_ITEMS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 240 */             map((Type)Type.UNSIGNED_BYTE);
/* 241 */             map(Type.FLAT_ITEM_ARRAY, Type.ITEM_ARRAY);
/*     */             
/* 243 */             handler(BlockItemPackets1_13.this.itemArrayHandler(Type.ITEM_ARRAY));
/*     */           }
/*     */         });
/*     */     
/* 247 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SET_SLOT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 250 */             map((Type)Type.UNSIGNED_BYTE);
/* 251 */             map((Type)Type.SHORT);
/* 252 */             map(Type.FLAT_ITEM, Type.ITEM);
/*     */             
/* 254 */             handler(BlockItemPackets1_13.this.itemToClientHandler(Type.ITEM));
/*     */           }
/*     */         });
/*     */     
/* 258 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.CHUNK_DATA, wrapper -> {
/*     */           ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */           Chunk1_9_3_4Type type_old = new Chunk1_9_3_4Type(clientWorld);
/*     */           Chunk1_13Type type = new Chunk1_13Type(clientWorld);
/*     */           Chunk chunk = (Chunk)wrapper.read((Type)type);
/*     */           BackwardsBlockEntityProvider provider = (BackwardsBlockEntityProvider)Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
/*     */           BackwardsBlockStorage storage = (BackwardsBlockStorage)wrapper.user().get(BackwardsBlockStorage.class);
/*     */           for (CompoundTag tag : chunk.getBlockEntities()) {
/*     */             Tag idTag = tag.get("id");
/*     */             if (idTag == null) {
/*     */               continue;
/*     */             }
/*     */             String id = (String)idTag.getValue();
/*     */             if (!provider.isHandled(id)) {
/*     */               continue;
/*     */             }
/*     */             int sectionIndex = ((NumberTag)tag.get("y")).asInt() >> 4;
/*     */             if (sectionIndex < 0 || sectionIndex > 15) {
/*     */               continue;
/*     */             }
/*     */             ChunkSection section = chunk.getSections()[sectionIndex];
/*     */             int x = ((NumberTag)tag.get("x")).asInt();
/*     */             int y = ((NumberTag)tag.get("y")).asInt();
/*     */             int z = ((NumberTag)tag.get("z")).asInt();
/*     */             Position position = new Position(x, (short)y, z);
/*     */             int block = section.palette(PaletteType.BLOCKS).idAt(x & 0xF, y & 0xF, z & 0xF);
/*     */             storage.checkAndStore(position, block);
/*     */             provider.transform(wrapper.user(), position, tag);
/*     */           } 
/*     */           int i;
/*     */           for (i = 0; i < (chunk.getSections()).length; i++) {
/*     */             ChunkSection section = chunk.getSections()[i];
/*     */             if (section != null) {
/*     */               DataPalette palette = section.palette(PaletteType.BLOCKS);
/*     */               for (int y = 0; y < 16; y++) {
/*     */                 for (int z = 0; z < 16; z++) {
/*     */                   for (int x = 0; x < 16; x++) {
/*     */                     int block = palette.idAt(x, y, z);
/*     */                     if (FlowerPotHandler.isFlowah(block)) {
/*     */                       Position pos = new Position(x + (chunk.getX() << 4), (short)(y + (i << 4)), z + (chunk.getZ() << 4));
/*     */                       storage.checkAndStore(pos, block);
/*     */                       CompoundTag nbt = provider.transform(wrapper.user(), pos, "minecraft:flower_pot");
/*     */                       chunk.getBlockEntities().add(nbt);
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */               for (int j = 0; j < palette.size(); j++) {
/*     */                 int mappedBlockStateId = ((Protocol1_12_2To1_13)this.protocol).getMappingData().getNewBlockStateId(palette.idByIndex(j));
/*     */                 palette.setIdByIndex(j, mappedBlockStateId);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           if (chunk.isBiomeData()) {
/*     */             for (i = 0; i < 256; i++) {
/*     */               int biome = chunk.getBiomeData()[i];
/*     */               int newId = -1;
/*     */               switch (biome) {
/*     */                 case 40:
/*     */                 case 41:
/*     */                 case 42:
/*     */                 case 43:
/*     */                   newId = 9;
/*     */                   break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 case 47:
/*     */                 case 48:
/*     */                 case 49:
/*     */                   newId = 24;
/*     */                   break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 case 50:
/*     */                   newId = 10;
/*     */                   break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 case 44:
/*     */                 case 45:
/*     */                 case 46:
/*     */                   newId = 0;
/*     */                   break;
/*     */               } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               if (newId != -1) {
/*     */                 chunk.getBiomeData()[i] = newId;
/*     */               }
/*     */             } 
/*     */           }
/*     */           wrapper.write((Type)type_old, chunk);
/*     */         });
/* 370 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 373 */             map((Type)Type.INT);
/* 374 */             map(Type.POSITION);
/* 375 */             map((Type)Type.INT);
/* 376 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   int data = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   if (id == 1010) {
/*     */                     wrapper.set((Type)Type.INT, 1, Integer.valueOf(((Protocol1_12_2To1_13)BlockItemPackets1_13.this.protocol).getMappingData().getItemMappings().getNewId(data) >> 4));
/*     */                   } else if (id == 2001) {
/*     */                     data = ((Protocol1_12_2To1_13)BlockItemPackets1_13.this.protocol).getMappingData().getNewBlockStateId(data);
/*     */                     int blockId = data >> 4;
/*     */                     int blockData = data & 0xF;
/*     */                     wrapper.set((Type)Type.INT, 1, Integer.valueOf(blockId & 0xFFF | blockData << 12));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 391 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.MAP_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 394 */             map((Type)Type.VAR_INT);
/* 395 */             map((Type)Type.BYTE);
/* 396 */             map((Type)Type.BOOLEAN);
/* 397 */             handler(wrapper -> {
/*     */                   int iconCount = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   for (int i = 0; i < iconCount; i++) {
/*     */                     int type = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                     byte x = ((Byte)wrapper.read((Type)Type.BYTE)).byteValue();
/*     */                     byte z = ((Byte)wrapper.read((Type)Type.BYTE)).byteValue();
/*     */                     byte direction = ((Byte)wrapper.read((Type)Type.BYTE)).byteValue();
/*     */                     if (((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue()) {
/*     */                       wrapper.read(Type.COMPONENT);
/*     */                     }
/*     */                     if (type > 9) {
/*     */                       wrapper.set((Type)Type.VAR_INT, 1, Integer.valueOf(((Integer)wrapper.get((Type)Type.VAR_INT, 1)).intValue() - 1));
/*     */                     } else {
/*     */                       wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)(type << 4 | direction & 0xF)));
/*     */                       wrapper.write((Type)Type.BYTE, Byte.valueOf(x));
/*     */                       wrapper.write((Type)Type.BYTE, Byte.valueOf(z));
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 419 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.ENTITY_EQUIPMENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 422 */             map((Type)Type.VAR_INT);
/* 423 */             map((Type)Type.VAR_INT);
/* 424 */             map(Type.FLAT_ITEM, Type.ITEM);
/*     */             
/* 426 */             handler(BlockItemPackets1_13.this.itemToClientHandler(Type.ITEM));
/*     */           }
/*     */         });
/*     */     
/* 430 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.WINDOW_PROPERTY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 433 */             map((Type)Type.UNSIGNED_BYTE);
/* 434 */             map((Type)Type.SHORT);
/* 435 */             map((Type)Type.SHORT);
/* 436 */             handler(wrapper -> {
/*     */                   short property = ((Short)wrapper.get((Type)Type.SHORT, 0)).shortValue();
/*     */                   
/*     */                   if (property >= 4 && property <= 6) {
/*     */                     short oldId = ((Short)wrapper.get((Type)Type.SHORT, 1)).shortValue();
/*     */                     
/*     */                     wrapper.set((Type)Type.SHORT, 1, Short.valueOf((short)((Protocol1_12_2To1_13)BlockItemPackets1_13.this.protocol).getMappingData().getEnchantmentMappings().getNewId(oldId)));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 448 */     ((Protocol1_12_2To1_13)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_12_1.CREATIVE_INVENTORY_ACTION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 451 */             map((Type)Type.SHORT);
/* 452 */             map(Type.ITEM, Type.FLAT_ITEM);
/*     */             
/* 454 */             handler(BlockItemPackets1_13.this.itemToServerHandler(Type.FLAT_ITEM));
/*     */           }
/*     */         });
/*     */     
/* 458 */     ((Protocol1_12_2To1_13)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_12_1.CLICK_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 461 */             map((Type)Type.UNSIGNED_BYTE);
/* 462 */             map((Type)Type.SHORT);
/* 463 */             map((Type)Type.BYTE);
/* 464 */             map((Type)Type.SHORT);
/* 465 */             map((Type)Type.VAR_INT);
/* 466 */             map(Type.ITEM, Type.FLAT_ITEM);
/*     */             
/* 468 */             handler(BlockItemPackets1_13.this.itemToServerHandler(Type.FLAT_ITEM));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerRewrites() {
/* 475 */     this.enchantmentMappings.put("minecraft:loyalty", "§7Loyalty");
/* 476 */     this.enchantmentMappings.put("minecraft:impaling", "§7Impaling");
/* 477 */     this.enchantmentMappings.put("minecraft:riptide", "§7Riptide");
/* 478 */     this.enchantmentMappings.put("minecraft:channeling", "§7Channeling");
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToClient(Item item) {
/* 483 */     if (item == null) return null;
/*     */ 
/*     */     
/* 486 */     int originalId = item.identifier();
/*     */     
/* 488 */     Integer rawId = null;
/* 489 */     boolean gotRawIdFromTag = false;
/*     */     
/* 491 */     CompoundTag tag = item.tag();
/*     */     
/*     */     Tag originalIdTag;
/*     */     
/* 495 */     if (tag != null && (originalIdTag = tag.remove(this.extraNbtTag)) != null) {
/* 496 */       rawId = Integer.valueOf(((NumberTag)originalIdTag).asInt());
/* 497 */       gotRawIdFromTag = true;
/*     */     } 
/*     */     
/* 500 */     if (rawId == null) {
/*     */       
/* 502 */       super.handleItemToClient(item);
/*     */ 
/*     */       
/* 505 */       if (item.identifier() == -1) {
/* 506 */         if (originalId == 362) {
/* 507 */           rawId = Integer.valueOf(15007744);
/*     */         } else {
/* 509 */           if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
/* 510 */             ViaBackwards.getPlatform().getLogger().warning("Failed to get 1.12 item for " + originalId);
/*     */           }
/*     */           
/* 513 */           rawId = Integer.valueOf(65536);
/*     */         } 
/*     */       } else {
/*     */         
/* 517 */         if (tag == null) {
/* 518 */           tag = item.tag();
/*     */         }
/*     */         
/* 521 */         rawId = Integer.valueOf(itemIdToRaw(item.identifier(), item, tag));
/*     */       } 
/*     */     } 
/*     */     
/* 525 */     item.setIdentifier(rawId.intValue() >> 16);
/* 526 */     item.setData((short)(rawId.intValue() & 0xFFFF));
/*     */ 
/*     */     
/* 529 */     if (tag != null) {
/* 530 */       if (isDamageable(item.identifier())) {
/* 531 */         Tag damageTag = tag.remove("Damage");
/* 532 */         if (!gotRawIdFromTag && damageTag instanceof IntTag) {
/* 533 */           item.setData((short)((Integer)damageTag.getValue()).intValue());
/*     */         }
/*     */       } 
/*     */       
/* 537 */       if (item.identifier() == 358) {
/* 538 */         Tag mapTag = tag.remove("map");
/* 539 */         if (!gotRawIdFromTag && mapTag instanceof IntTag) {
/* 540 */           item.setData((short)((Integer)mapTag.getValue()).intValue());
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 545 */       invertShieldAndBannerId(item, tag);
/*     */ 
/*     */       
/* 548 */       CompoundTag display = (CompoundTag)tag.get("display");
/* 549 */       if (display != null) {
/* 550 */         StringTag name = (StringTag)display.get("Name");
/* 551 */         if (name != null) {
/* 552 */           display.put(this.extraNbtTag + "|Name", (Tag)new StringTag(name.getValue()));
/* 553 */           name.setValue(((Protocol1_12_2To1_13)this.protocol).jsonToLegacy(name.getValue()));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 558 */       rewriteEnchantmentsToClient(tag, false);
/* 559 */       rewriteEnchantmentsToClient(tag, true);
/*     */       
/* 561 */       rewriteCanPlaceToClient(tag, "CanPlaceOn");
/* 562 */       rewriteCanPlaceToClient(tag, "CanDestroy");
/*     */     } 
/* 564 */     return item;
/*     */   }
/*     */   
/*     */   private int itemIdToRaw(int oldId, Item item, CompoundTag tag) {
/* 568 */     Optional<String> eggEntityId = SpawnEggRewriter.getEntityId(oldId);
/* 569 */     if (eggEntityId.isPresent()) {
/* 570 */       if (tag == null) {
/* 571 */         item.setTag(tag = new CompoundTag());
/*     */       }
/* 573 */       if (!tag.contains("EntityTag")) {
/* 574 */         CompoundTag entityTag = new CompoundTag();
/* 575 */         entityTag.put("id", (Tag)new StringTag(eggEntityId.get()));
/* 576 */         tag.put("EntityTag", (Tag)entityTag);
/*     */       } 
/* 578 */       return 25100288;
/*     */     } 
/*     */     
/* 581 */     return oldId >> 4 << 16 | oldId & 0xF;
/*     */   }
/*     */ 
/*     */   
/*     */   private void rewriteCanPlaceToClient(CompoundTag tag, String tagName) {
/* 586 */     if (!(tag.get(tagName) instanceof ListTag))
/* 587 */       return;  ListTag blockTag = (ListTag)tag.get(tagName);
/* 588 */     if (blockTag == null)
/*     */       return; 
/* 590 */     ListTag newCanPlaceOn = new ListTag(StringTag.class);
/* 591 */     tag.put(this.extraNbtTag + "|" + tagName, ConverterRegistry.convertToTag(ConverterRegistry.convertToValue((Tag)blockTag)));
/* 592 */     for (Tag oldTag : blockTag) {
/* 593 */       Object value = oldTag.getValue();
/*     */       
/* 595 */       String[] newValues = (value instanceof String) ? (String[])BlockIdData.fallbackReverseMapping.get(((String)value).replace("minecraft:", "")) : null;
/* 596 */       if (newValues != null) {
/* 597 */         for (String newValue : newValues)
/* 598 */           newCanPlaceOn.add((Tag)new StringTag(newValue)); 
/*     */         continue;
/*     */       } 
/* 601 */       newCanPlaceOn.add(oldTag);
/*     */     } 
/*     */     
/* 604 */     tag.put(tagName, (Tag)newCanPlaceOn);
/*     */   }
/*     */ 
/*     */   
/*     */   private void rewriteEnchantmentsToClient(CompoundTag tag, boolean storedEnch) {
/* 609 */     String key = storedEnch ? "StoredEnchantments" : "Enchantments";
/* 610 */     ListTag enchantments = (ListTag)tag.get(key);
/* 611 */     if (enchantments == null)
/*     */       return; 
/* 613 */     ListTag noMapped = new ListTag(CompoundTag.class);
/* 614 */     ListTag newEnchantments = new ListTag(CompoundTag.class);
/* 615 */     List<Tag> lore = new ArrayList<>();
/* 616 */     boolean hasValidEnchants = false;
/* 617 */     for (Tag enchantmentEntryTag : enchantments.clone()) {
/* 618 */       CompoundTag enchantmentEntry = (CompoundTag)enchantmentEntryTag;
/* 619 */       Tag idTag = enchantmentEntry.get("id");
/* 620 */       if (!(idTag instanceof StringTag)) {
/*     */         continue;
/*     */       }
/*     */       
/* 624 */       String newId = (String)idTag.getValue();
/* 625 */       NumberTag levelTag = (NumberTag)enchantmentEntry.get("lvl");
/* 626 */       if (levelTag == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 630 */       int levelValue = levelTag.asInt();
/* 631 */       short level = (levelValue < 32767) ? (short)levelValue : Short.MAX_VALUE;
/*     */       
/* 633 */       String mappedEnchantmentId = this.enchantmentMappings.get(newId);
/* 634 */       if (mappedEnchantmentId != null) {
/* 635 */         lore.add(new StringTag(mappedEnchantmentId + " " + EnchantmentRewriter.getRomanNumber(level)));
/* 636 */         noMapped.add((Tag)enchantmentEntry); continue;
/* 637 */       }  if (!newId.isEmpty()) {
/* 638 */         Short oldId = (Short)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().inverse().get(Key.stripMinecraftNamespace(newId));
/* 639 */         if (oldId == null) {
/* 640 */           if (!newId.startsWith("viaversion:legacy/")) {
/*     */             
/* 642 */             noMapped.add((Tag)enchantmentEntry);
/*     */ 
/*     */             
/* 645 */             if (ViaBackwards.getConfig().addCustomEnchantsToLore()) {
/* 646 */               String name = newId;
/* 647 */               int index = name.indexOf(':') + 1;
/* 648 */               if (index != 0 && index != name.length()) {
/* 649 */                 name = name.substring(index);
/*     */               }
/* 651 */               name = "§7" + Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase(Locale.ENGLISH);
/*     */               
/* 653 */               lore.add(new StringTag(name + " " + EnchantmentRewriter.getRomanNumber(level)));
/*     */             } 
/*     */             
/* 656 */             if (Via.getManager().isDebug()) {
/* 657 */               ViaBackwards.getPlatform().getLogger().warning("Found unknown enchant: " + newId);
/*     */             }
/*     */             continue;
/*     */           } 
/* 661 */           oldId = Short.valueOf(newId.substring(18));
/*     */         } 
/*     */ 
/*     */         
/* 665 */         if (level != 0) {
/* 666 */           hasValidEnchants = true;
/*     */         }
/*     */         
/* 669 */         CompoundTag newEntry = new CompoundTag();
/* 670 */         newEntry.put("id", (Tag)new ShortTag(oldId.shortValue()));
/* 671 */         newEntry.put("lvl", (Tag)new ShortTag(level));
/* 672 */         newEnchantments.add((Tag)newEntry);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 677 */     if (!storedEnch && !hasValidEnchants) {
/* 678 */       IntTag hideFlags = (IntTag)tag.get("HideFlags");
/* 679 */       if (hideFlags == null) {
/* 680 */         hideFlags = new IntTag();
/* 681 */         tag.put(this.extraNbtTag + "|DummyEnchant", (Tag)new ByteTag());
/*     */       } else {
/* 683 */         tag.put(this.extraNbtTag + "|OldHideFlags", (Tag)new IntTag(hideFlags.asByte()));
/*     */       } 
/*     */       
/* 686 */       if (newEnchantments.size() == 0) {
/* 687 */         CompoundTag enchEntry = new CompoundTag();
/* 688 */         enchEntry.put("id", (Tag)new ShortTag((short)0));
/* 689 */         enchEntry.put("lvl", (Tag)new ShortTag((short)0));
/* 690 */         newEnchantments.add((Tag)enchEntry);
/*     */       } 
/*     */       
/* 693 */       int value = hideFlags.asByte() | 0x1;
/* 694 */       hideFlags.setValue(value);
/* 695 */       tag.put("HideFlags", (Tag)hideFlags);
/*     */     } 
/*     */     
/* 698 */     if (noMapped.size() != 0) {
/* 699 */       tag.put(this.extraNbtTag + "|" + key, (Tag)noMapped);
/*     */       
/* 701 */       if (!lore.isEmpty()) {
/* 702 */         CompoundTag display = (CompoundTag)tag.get("display");
/* 703 */         if (display == null) {
/* 704 */           tag.put("display", (Tag)(display = new CompoundTag()));
/*     */         }
/*     */         
/* 707 */         ListTag loreTag = (ListTag)display.get("Lore");
/* 708 */         if (loreTag == null) {
/* 709 */           display.put("Lore", (Tag)(loreTag = new ListTag(StringTag.class)));
/* 710 */           tag.put(this.extraNbtTag + "|DummyLore", (Tag)new ByteTag());
/* 711 */         } else if (loreTag.size() != 0) {
/* 712 */           ListTag oldLore = new ListTag(StringTag.class);
/* 713 */           for (Tag value : loreTag) {
/* 714 */             oldLore.add(value.clone());
/*     */           }
/* 716 */           tag.put(this.extraNbtTag + "|OldLore", (Tag)oldLore);
/*     */           
/* 718 */           lore.addAll(loreTag.getValue());
/*     */         } 
/*     */         
/* 721 */         loreTag.setValue(lore);
/*     */       } 
/*     */     } 
/*     */     
/* 725 */     tag.remove("Enchantments");
/* 726 */     tag.put(storedEnch ? key : "ench", (Tag)newEnchantments);
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToServer(Item item) {
/* 731 */     if (item == null) return null; 
/* 732 */     CompoundTag tag = item.tag();
/*     */ 
/*     */     
/* 735 */     int originalId = item.identifier() << 16 | item.data() & 0xFFFF;
/*     */     
/* 737 */     int rawId = item.identifier() << 4 | item.data() & 0xF;
/*     */ 
/*     */     
/* 740 */     if (isDamageable(item.identifier())) {
/* 741 */       if (tag == null) item.setTag(tag = new CompoundTag()); 
/* 742 */       tag.put("Damage", (Tag)new IntTag(item.data()));
/*     */     } 
/* 744 */     if (item.identifier() == 358) {
/* 745 */       if (tag == null) item.setTag(tag = new CompoundTag()); 
/* 746 */       tag.put("map", (Tag)new IntTag(item.data()));
/*     */     } 
/*     */ 
/*     */     
/* 750 */     if (tag != null) {
/*     */       
/* 752 */       invertShieldAndBannerId(item, tag);
/*     */ 
/*     */       
/* 755 */       Tag display = tag.get("display");
/* 756 */       if (display instanceof CompoundTag) {
/* 757 */         CompoundTag displayTag = (CompoundTag)display;
/* 758 */         StringTag name = (StringTag)displayTag.get("Name");
/* 759 */         if (name != null) {
/* 760 */           StringTag via = (StringTag)displayTag.remove(this.extraNbtTag + "|Name");
/* 761 */           name.setValue((via != null) ? via.getValue() : ChatRewriter.legacyTextToJsonString(name.getValue()));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 766 */       rewriteEnchantmentsToServer(tag, false);
/* 767 */       rewriteEnchantmentsToServer(tag, true);
/*     */       
/* 769 */       rewriteCanPlaceToServer(tag, "CanPlaceOn");
/* 770 */       rewriteCanPlaceToServer(tag, "CanDestroy");
/*     */ 
/*     */       
/* 773 */       if (item.identifier() == 383) {
/* 774 */         CompoundTag entityTag = (CompoundTag)tag.get("EntityTag");
/*     */         StringTag stringTag;
/* 776 */         if (entityTag != null && (stringTag = (StringTag)entityTag.get("id")) != null) {
/* 777 */           rawId = SpawnEggRewriter.getSpawnEggId(stringTag.getValue());
/* 778 */           if (rawId == -1) {
/* 779 */             rawId = 25100288;
/*     */           } else {
/* 781 */             entityTag.remove("id");
/* 782 */             if (entityTag.isEmpty()) {
/* 783 */               tag.remove("EntityTag");
/*     */             }
/*     */           } 
/*     */         } else {
/*     */           
/* 788 */           rawId = 25100288;
/*     */         } 
/*     */       } 
/* 791 */       if (tag.isEmpty()) {
/* 792 */         item.setTag(tag = null);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 797 */     int identifier = item.identifier();
/* 798 */     item.setIdentifier(rawId);
/* 799 */     super.handleItemToServer(item);
/*     */ 
/*     */     
/* 802 */     if (item.identifier() != rawId && item.identifier() != -1) return item;
/*     */ 
/*     */     
/* 805 */     item.setIdentifier(identifier);
/*     */     
/* 807 */     int newId = -1;
/* 808 */     if (((Protocol1_12_2To1_13)this.protocol).getMappingData().getItemMappings().inverse().getNewId(rawId) == -1) {
/* 809 */       if (!isDamageable(item.identifier()) && item.identifier() != 358) {
/* 810 */         if (tag == null) item.setTag(tag = new CompoundTag()); 
/* 811 */         tag.put(this.extraNbtTag, (Tag)new IntTag(originalId));
/*     */       } 
/*     */       
/* 814 */       if (item.identifier() == 229) {
/* 815 */         newId = 362;
/* 816 */       } else if (item.identifier() == 31 && item.data() == 0) {
/* 817 */         rawId = 512;
/* 818 */       } else if (((Protocol1_12_2To1_13)this.protocol).getMappingData().getItemMappings().inverse().getNewId(rawId & 0xFFFFFFF0) != -1) {
/* 819 */         rawId &= 0xFFFFFFF0;
/*     */       } else {
/* 821 */         if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
/* 822 */           ViaBackwards.getPlatform().getLogger().warning("Failed to get 1.13 item for " + item.identifier());
/*     */         }
/* 824 */         rawId = 16;
/*     */       } 
/*     */     } 
/*     */     
/* 828 */     if (newId == -1) {
/* 829 */       newId = ((Protocol1_12_2To1_13)this.protocol).getMappingData().getItemMappings().inverse().getNewId(rawId);
/*     */     }
/*     */     
/* 832 */     item.setIdentifier(newId);
/* 833 */     item.setData((short)0);
/* 834 */     return item;
/*     */   }
/*     */   
/*     */   private void rewriteCanPlaceToServer(CompoundTag tag, String tagName) {
/* 838 */     if (!(tag.get(tagName) instanceof ListTag))
/* 839 */       return;  ListTag blockTag = (ListTag)tag.remove(this.extraNbtTag + "|" + tagName);
/* 840 */     if (blockTag != null) {
/* 841 */       tag.put(tagName, ConverterRegistry.convertToTag(ConverterRegistry.convertToValue((Tag)blockTag)));
/* 842 */     } else if ((blockTag = (ListTag)tag.get(tagName)) != null) {
/* 843 */       ListTag newCanPlaceOn = new ListTag(StringTag.class);
/* 844 */       for (Tag oldTag : blockTag) {
/* 845 */         Object value = oldTag.getValue();
/* 846 */         String oldId = value.toString().replace("minecraft:", "");
/* 847 */         int key = Ints.tryParse(oldId).intValue();
/* 848 */         String numberConverted = (String)BlockIdData.numberIdToString.get(key);
/* 849 */         if (numberConverted != null) {
/* 850 */           oldId = numberConverted;
/*     */         }
/*     */         
/* 853 */         String lowerCaseId = oldId.toLowerCase(Locale.ROOT);
/* 854 */         String[] newValues = (String[])BlockIdData.blockIdMapping.get(lowerCaseId);
/* 855 */         if (newValues != null) {
/* 856 */           for (String newValue : newValues)
/* 857 */             newCanPlaceOn.add((Tag)new StringTag(newValue)); 
/*     */           continue;
/*     */         } 
/* 860 */         newCanPlaceOn.add((Tag)new StringTag(lowerCaseId));
/*     */       } 
/*     */       
/* 863 */       tag.put(tagName, (Tag)newCanPlaceOn);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void rewriteEnchantmentsToServer(CompoundTag tag, boolean storedEnch) {
/* 868 */     String key = storedEnch ? "StoredEnchantments" : "Enchantments";
/* 869 */     ListTag enchantments = (ListTag)tag.get(storedEnch ? key : "ench");
/* 870 */     if (enchantments == null)
/*     */       return; 
/* 872 */     ListTag newEnchantments = new ListTag(CompoundTag.class);
/* 873 */     boolean dummyEnchant = false;
/* 874 */     if (!storedEnch) {
/* 875 */       IntTag hideFlags = (IntTag)tag.remove(this.extraNbtTag + "|OldHideFlags");
/* 876 */       if (hideFlags != null) {
/* 877 */         tag.put("HideFlags", (Tag)new IntTag(hideFlags.asByte()));
/* 878 */         dummyEnchant = true;
/* 879 */       } else if (tag.remove(this.extraNbtTag + "|DummyEnchant") != null) {
/* 880 */         tag.remove("HideFlags");
/* 881 */         dummyEnchant = true;
/*     */       } 
/*     */     } 
/*     */     
/* 885 */     for (Tag enchEntry : enchantments) {
/* 886 */       CompoundTag enchantmentEntry = new CompoundTag();
/* 887 */       short oldId = ((NumberTag)((CompoundTag)enchEntry).get("id")).asShort();
/* 888 */       short level = ((NumberTag)((CompoundTag)enchEntry).get("lvl")).asShort();
/* 889 */       if (dummyEnchant && oldId == 0 && level == 0) {
/*     */         continue;
/*     */       }
/* 892 */       String newId = (String)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().get(Short.valueOf(oldId));
/* 893 */       if (newId == null) {
/* 894 */         newId = "viaversion:legacy/" + oldId;
/*     */       }
/* 896 */       enchantmentEntry.put("id", (Tag)new StringTag(newId));
/*     */       
/* 898 */       enchantmentEntry.put("lvl", (Tag)new ShortTag(level));
/* 899 */       newEnchantments.add((Tag)enchantmentEntry);
/*     */     } 
/*     */     
/* 902 */     ListTag noMapped = (ListTag)tag.remove(this.extraNbtTag + "|Enchantments");
/* 903 */     if (noMapped != null) {
/* 904 */       for (Tag value : noMapped) {
/* 905 */         newEnchantments.add(value);
/*     */       }
/*     */     }
/*     */     
/* 909 */     CompoundTag display = (CompoundTag)tag.get("display");
/* 910 */     if (display == null) {
/* 911 */       tag.put("display", (Tag)(display = new CompoundTag()));
/*     */     }
/*     */     
/* 914 */     ListTag oldLore = (ListTag)tag.remove(this.extraNbtTag + "|OldLore");
/* 915 */     if (oldLore != null) {
/* 916 */       ListTag lore = (ListTag)display.get("Lore");
/* 917 */       if (lore == null) {
/* 918 */         tag.put("Lore", (Tag)(lore = new ListTag()));
/*     */       }
/*     */       
/* 921 */       lore.setValue(oldLore.getValue());
/* 922 */     } else if (tag.remove(this.extraNbtTag + "|DummyLore") != null) {
/* 923 */       display.remove("Lore");
/* 924 */       if (display.isEmpty()) {
/* 925 */         tag.remove("display");
/*     */       }
/*     */     } 
/*     */     
/* 929 */     if (!storedEnch) {
/* 930 */       tag.remove("ench");
/*     */     }
/* 932 */     tag.put(key, (Tag)newEnchantments);
/*     */   }
/*     */   
/*     */   private void invertShieldAndBannerId(Item item, CompoundTag tag) {
/* 936 */     if (item.identifier() != 442 && item.identifier() != 425)
/*     */       return; 
/* 938 */     Tag blockEntityTag = tag.get("BlockEntityTag");
/* 939 */     if (!(blockEntityTag instanceof CompoundTag))
/*     */       return; 
/* 941 */     CompoundTag blockEntityCompoundTag = (CompoundTag)blockEntityTag;
/* 942 */     Tag base = blockEntityCompoundTag.get("Base");
/* 943 */     if (base instanceof IntTag) {
/* 944 */       IntTag baseTag = (IntTag)base;
/* 945 */       baseTag.setValue(15 - baseTag.asInt());
/*     */     } 
/*     */     
/* 948 */     Tag patterns = blockEntityCompoundTag.get("Patterns");
/* 949 */     if (patterns instanceof ListTag) {
/* 950 */       ListTag patternsTag = (ListTag)patterns;
/* 951 */       for (Tag pattern : patternsTag) {
/* 952 */         if (!(pattern instanceof CompoundTag))
/*     */           continue; 
/* 954 */         IntTag colorTag = (IntTag)((CompoundTag)pattern).get("Color");
/* 955 */         colorTag.setValue(15 - colorTag.asInt());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void flowerPotSpecialTreatment(UserConnection user, int blockState, Position position) throws Exception {
/* 962 */     if (FlowerPotHandler.isFlowah(blockState)) {
/* 963 */       BackwardsBlockEntityProvider beProvider = (BackwardsBlockEntityProvider)Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
/*     */       
/* 965 */       CompoundTag nbt = beProvider.transform(user, position, "minecraft:flower_pot");
/*     */ 
/*     */       
/* 968 */       PacketWrapper blockUpdateRemove = PacketWrapper.create((PacketType)ClientboundPackets1_12_1.BLOCK_CHANGE, user);
/* 969 */       blockUpdateRemove.write(Type.POSITION, position);
/* 970 */       blockUpdateRemove.write((Type)Type.VAR_INT, Integer.valueOf(0));
/* 971 */       blockUpdateRemove.scheduleSend(Protocol1_12_2To1_13.class);
/*     */ 
/*     */       
/* 974 */       PacketWrapper blockCreate = PacketWrapper.create((PacketType)ClientboundPackets1_12_1.BLOCK_CHANGE, user);
/* 975 */       blockCreate.write(Type.POSITION, position);
/* 976 */       blockCreate.write((Type)Type.VAR_INT, Integer.valueOf(Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(blockState)));
/* 977 */       blockCreate.scheduleSend(Protocol1_12_2To1_13.class);
/*     */ 
/*     */       
/* 980 */       PacketWrapper wrapper = PacketWrapper.create((PacketType)ClientboundPackets1_12_1.BLOCK_ENTITY_DATA, user);
/* 981 */       wrapper.write(Type.POSITION, position);
/* 982 */       wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)5));
/* 983 */       wrapper.write(Type.NBT, nbt);
/* 984 */       wrapper.scheduleSend(Protocol1_12_2To1_13.class);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_12_2to1_13\packets\BlockItemPackets1_13.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
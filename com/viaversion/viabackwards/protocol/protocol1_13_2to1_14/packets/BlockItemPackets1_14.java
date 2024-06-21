/*     */ package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
/*     */ import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.ChunkLightStorage;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.Environment;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLight;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLightImpl;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_13;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types.Chunk1_14Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.rewriter.BlockRewriter;
/*     */ import com.viaversion.viaversion.rewriter.RecipeRewriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockItemPackets1_14
/*     */   extends ItemRewriter<ClientboundPackets1_14, ServerboundPackets1_13, Protocol1_13_2To1_14>
/*     */ {
/*     */   private EnchantmentRewriter enchantmentRewriter;
/*     */   
/*     */   public BlockItemPackets1_14(Protocol1_13_2To1_14 protocol) {
/*  66 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  71 */     ((Protocol1_13_2To1_14)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_13.EDIT_BOOK, wrapper -> handleItemToServer((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM)));
/*     */     
/*  73 */     ((Protocol1_13_2To1_14)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.OPEN_WINDOW, wrapper -> {
/*     */           int windowId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)windowId));
/*     */           int type = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           String stringType = null;
/*     */           String containerTitle = null;
/*     */           int slotSize = 0;
/*     */           if (type < 6) {
/*     */             if (type == 2) {
/*     */               containerTitle = "Barrel";
/*     */             }
/*     */             stringType = "minecraft:container";
/*     */             slotSize = (type + 1) * 9;
/*     */           } else {
/*     */             switch (type) {
/*     */               case 11:
/*     */                 stringType = "minecraft:crafting_table";
/*     */                 break;
/*     */               
/*     */               case 9:
/*     */               case 13:
/*     */               case 14:
/*     */               case 20:
/*     */                 if (type == 9) {
/*     */                   containerTitle = "Blast Furnace";
/*     */                 } else if (type == 20) {
/*     */                   containerTitle = "Smoker";
/*     */                 } else if (type == 14) {
/*     */                   containerTitle = "Grindstone";
/*     */                 } 
/*     */                 stringType = "minecraft:furnace";
/*     */                 slotSize = 3;
/*     */                 break;
/*     */               case 6:
/*     */                 stringType = "minecraft:dropper";
/*     */                 slotSize = 9;
/*     */                 break;
/*     */               case 12:
/*     */                 stringType = "minecraft:enchanting_table";
/*     */                 break;
/*     */               case 10:
/*     */                 stringType = "minecraft:brewing_stand";
/*     */                 slotSize = 5;
/*     */                 break;
/*     */               case 18:
/*     */                 stringType = "minecraft:villager";
/*     */                 break;
/*     */               case 8:
/*     */                 stringType = "minecraft:beacon";
/*     */                 slotSize = 1;
/*     */                 break;
/*     */               case 7:
/*     */               case 21:
/*     */                 if (type == 21) {
/*     */                   containerTitle = "Cartography Table";
/*     */                 }
/*     */                 stringType = "minecraft:anvil";
/*     */                 break;
/*     */               case 15:
/*     */                 stringType = "minecraft:hopper";
/*     */                 slotSize = 5;
/*     */                 break;
/*     */               case 19:
/*     */                 stringType = "minecraft:shulker_box";
/*     */                 slotSize = 27;
/*     */                 break;
/*     */             } 
/*     */           } 
/*     */           if (stringType == null) {
/*     */             ViaBackwards.getPlatform().getLogger().warning("Can't open inventory for 1.13 player! Type: " + type);
/*     */             wrapper.cancel();
/*     */             return;
/*     */           } 
/*     */           wrapper.write(Type.STRING, stringType);
/*     */           JsonElement title = (JsonElement)wrapper.read(Type.COMPONENT);
/*     */           if (containerTitle != null) {
/*     */             JsonObject object;
/*     */             if (title.isJsonObject() && (object = title.getAsJsonObject()).has("translate")) {
/*     */               if (type != 2 || object.getAsJsonPrimitive("translate").getAsString().equals("container.barrel")) {
/*     */                 title = ChatRewriter.legacyTextToJson(containerTitle);
/*     */               }
/*     */             }
/*     */           } 
/*     */           wrapper.write(Type.COMPONENT, title);
/*     */           wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)slotSize));
/*     */         });
/* 159 */     ((Protocol1_13_2To1_14)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.OPEN_HORSE_WINDOW, (ClientboundPacketType)ClientboundPackets1_13.OPEN_WINDOW, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.UNSIGNED_BYTE);
/*     */           
/*     */           wrapper.write(Type.STRING, "EntityHorse");
/*     */           JsonObject object = new JsonObject();
/*     */           object.addProperty("translate", "minecraft.horse");
/*     */           wrapper.write(Type.COMPONENT, object);
/*     */           wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf(((Integer)wrapper.read((Type)Type.VAR_INT)).shortValue()));
/*     */           wrapper.passthrough((Type)Type.INT);
/*     */         });
/* 169 */     BlockRewriter<ClientboundPackets1_14> blockRewriter = new BlockRewriter(this.protocol, Type.POSITION);
/*     */     
/* 171 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_14.COOLDOWN);
/* 172 */     registerWindowItems((ClientboundPacketType)ClientboundPackets1_14.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
/* 173 */     registerSetSlot((ClientboundPacketType)ClientboundPackets1_14.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
/* 174 */     registerAdvancements((ClientboundPacketType)ClientboundPackets1_14.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
/*     */ 
/*     */     
/* 177 */     ((Protocol1_13_2To1_14)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.TRADE_LIST, (ClientboundPacketType)ClientboundPackets1_13.PLUGIN_MESSAGE, wrapper -> {
/*     */           wrapper.write(Type.STRING, "minecraft:trader_list");
/*     */           
/*     */           int windowId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           wrapper.write((Type)Type.INT, Integer.valueOf(windowId));
/*     */           
/*     */           int size = ((Short)wrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */           
/*     */           for (int i = 0; i < size; i++) {
/*     */             Item input = (Item)wrapper.read(Type.FLAT_VAR_INT_ITEM);
/*     */             
/*     */             input = handleItemToClient(input);
/*     */             
/*     */             wrapper.write(Type.FLAT_VAR_INT_ITEM, input);
/*     */             
/*     */             Item output = (Item)wrapper.read(Type.FLAT_VAR_INT_ITEM);
/*     */             
/*     */             output = handleItemToClient(output);
/*     */             
/*     */             wrapper.write(Type.FLAT_VAR_INT_ITEM, output);
/*     */             
/*     */             boolean secondItem = ((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue();
/*     */             
/*     */             if (secondItem) {
/*     */               Item second = (Item)wrapper.read(Type.FLAT_VAR_INT_ITEM);
/*     */               
/*     */               second = handleItemToClient(second);
/*     */               wrapper.write(Type.FLAT_VAR_INT_ITEM, second);
/*     */             } 
/*     */             wrapper.passthrough((Type)Type.BOOLEAN);
/*     */             wrapper.passthrough((Type)Type.INT);
/*     */             wrapper.passthrough((Type)Type.INT);
/*     */             wrapper.read((Type)Type.INT);
/*     */             wrapper.read((Type)Type.INT);
/*     */             wrapper.read((Type)Type.FLOAT);
/*     */           } 
/*     */           wrapper.read((Type)Type.VAR_INT);
/*     */           wrapper.read((Type)Type.VAR_INT);
/*     */           wrapper.read((Type)Type.BOOLEAN);
/*     */         });
/* 218 */     ((Protocol1_13_2To1_14)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.OPEN_BOOK, (ClientboundPacketType)ClientboundPackets1_13.PLUGIN_MESSAGE, wrapper -> {
/*     */           wrapper.write(Type.STRING, "minecraft:book_open");
/*     */           
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */         });
/* 223 */     ((Protocol1_13_2To1_14)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.ENTITY_EQUIPMENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 226 */             map((Type)Type.VAR_INT);
/* 227 */             map((Type)Type.VAR_INT);
/* 228 */             map(Type.FLAT_VAR_INT_ITEM);
/*     */             
/* 230 */             handler(BlockItemPackets1_14.this.itemToClientHandler(Type.FLAT_VAR_INT_ITEM));
/*     */             
/* 232 */             handler(wrapper -> {
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   EntityType entityType = wrapper.user().getEntityTracker(Protocol1_13_2To1_14.class).entityType(entityId);
/*     */                   if (entityType == null)
/*     */                     return; 
/*     */                   if (entityType.isOrHasParent((EntityType)Entity1_14Types.ABSTRACT_HORSE)) {
/*     */                     wrapper.setPacketType((PacketType)ClientboundPackets1_13.ENTITY_METADATA);
/*     */                     wrapper.resetReader();
/*     */                     wrapper.passthrough((Type)Type.VAR_INT);
/*     */                     wrapper.read((Type)Type.VAR_INT);
/*     */                     Item item = (Item)wrapper.read(Type.FLAT_VAR_INT_ITEM);
/* 243 */                     int armorType = (item == null || item.identifier() == 0) ? 0 : (item.identifier() - 726);
/*     */                     
/*     */                     if (armorType < 0 || armorType > 3) {
/*     */                       wrapper.cancel();
/*     */                       return;
/*     */                     } 
/*     */                     List<Metadata> metadataList = new ArrayList<>();
/*     */                     metadataList.add(new Metadata(16, Types1_13_2.META_TYPES.varIntType, Integer.valueOf(armorType)));
/*     */                     wrapper.write(Types1_13.METADATA_LIST, metadataList);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 256 */     RecipeRewriter<ClientboundPackets1_14> recipeHandler = new RecipeRewriter(this.protocol);
/* 257 */     ImmutableSet immutableSet = ImmutableSet.of("crafting_special_suspiciousstew", "blasting", "smoking", "campfire_cooking", "stonecutting");
/* 258 */     ((Protocol1_13_2To1_14)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.DECLARE_RECIPES, wrapper -> {
/*     */           int size = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           int deleted = 0;
/*     */           for (int i = 0; i < size; i++) {
/*     */             String type = (String)wrapper.read(Type.STRING);
/*     */             String id = (String)wrapper.read(Type.STRING);
/*     */             type = type.replace("minecraft:", "");
/*     */             if (removedTypes.contains(type)) {
/*     */               switch (type) {
/*     */                 case "blasting":
/*     */                 case "smoking":
/*     */                 case "campfire_cooking":
/*     */                   wrapper.read(Type.STRING);
/*     */                   wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
/*     */                   wrapper.read(Type.FLAT_VAR_INT_ITEM);
/*     */                   wrapper.read((Type)Type.FLOAT);
/*     */                   wrapper.read((Type)Type.VAR_INT);
/*     */                   break;
/*     */ 
/*     */                 
/*     */                 case "stonecutting":
/*     */                   wrapper.read(Type.STRING);
/*     */                   wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
/*     */                   wrapper.read(Type.FLAT_VAR_INT_ITEM);
/*     */                   break;
/*     */               } 
/*     */ 
/*     */               
/*     */               deleted++;
/*     */             } else {
/*     */               wrapper.write(Type.STRING, id);
/*     */               wrapper.write(Type.STRING, type);
/*     */               recipeHandler.handleRecipeType(wrapper, type);
/*     */             } 
/*     */           } 
/*     */           wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(size - deleted));
/*     */         });
/* 295 */     registerClickWindow((ServerboundPacketType)ServerboundPackets1_13.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
/* 296 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
/*     */     
/* 298 */     ((Protocol1_13_2To1_14)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.BLOCK_BREAK_ANIMATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 301 */             map((Type)Type.VAR_INT);
/* 302 */             map(Type.POSITION1_14, Type.POSITION);
/* 303 */             map((Type)Type.BYTE);
/*     */           }
/*     */         });
/*     */     
/* 307 */     ((Protocol1_13_2To1_14)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.BLOCK_ENTITY_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 310 */             map(Type.POSITION1_14, Type.POSITION);
/*     */           }
/*     */         });
/*     */     
/* 314 */     ((Protocol1_13_2To1_14)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.BLOCK_ACTION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 317 */             map(Type.POSITION1_14, Type.POSITION);
/* 318 */             map((Type)Type.UNSIGNED_BYTE);
/* 319 */             map((Type)Type.UNSIGNED_BYTE);
/* 320 */             map((Type)Type.VAR_INT);
/* 321 */             handler(wrapper -> {
/*     */                   int mappedId = ((Protocol1_13_2To1_14)BlockItemPackets1_14.this.protocol).getMappingData().getNewBlockId(((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue());
/*     */                   
/*     */                   if (mappedId == -1) {
/*     */                     wrapper.cancel();
/*     */                     return;
/*     */                   } 
/*     */                   wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(mappedId));
/*     */                 });
/*     */           }
/*     */         });
/* 332 */     ((Protocol1_13_2To1_14)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 335 */             map(Type.POSITION1_14, Type.POSITION);
/* 336 */             map((Type)Type.VAR_INT);
/* 337 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(((Protocol1_13_2To1_14)BlockItemPackets1_14.this.protocol).getMappingData().getNewBlockStateId(id)));
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 345 */     blockRewriter.registerMultiBlockChange((ClientboundPacketType)ClientboundPackets1_14.MULTI_BLOCK_CHANGE);
/*     */     
/* 347 */     ((Protocol1_13_2To1_14)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.EXPLOSION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 350 */             map((Type)Type.FLOAT);
/* 351 */             map((Type)Type.FLOAT);
/* 352 */             map((Type)Type.FLOAT);
/* 353 */             map((Type)Type.FLOAT);
/* 354 */             handler(wrapper -> {
/*     */                   for (int i = 0; i < 3; i++) {
/*     */                     float coord = ((Float)wrapper.get((Type)Type.FLOAT, i)).floatValue();
/*     */                     
/*     */                     if (coord < 0.0F) {
/*     */                       coord = (float)Math.floor(coord);
/*     */                       
/*     */                       wrapper.set((Type)Type.FLOAT, i, Float.valueOf(coord));
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 367 */     ((Protocol1_13_2To1_14)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.CHUNK_DATA, wrapper -> {
/*     */           ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */           
/*     */           Chunk chunk = (Chunk)wrapper.read((Type)new Chunk1_14Type());
/*     */           
/*     */           wrapper.write((Type)new Chunk1_13Type(clientWorld), chunk);
/*     */           
/*     */           ChunkLightStorage.ChunkLight chunkLight = ((ChunkLightStorage)wrapper.user().get(ChunkLightStorage.class)).getStoredLight(chunk.getX(), chunk.getZ());
/*     */           
/*     */           for (int i = 0; i < (chunk.getSections()).length; i++) {
/*     */             ChunkSection section = chunk.getSections()[i];
/*     */             if (section != null) {
/*     */               ChunkSectionLightImpl chunkSectionLightImpl = new ChunkSectionLightImpl();
/*     */               section.setLight((ChunkSectionLight)chunkSectionLightImpl);
/*     */               if (chunkLight == null) {
/*     */                 chunkSectionLightImpl.setBlockLight(ChunkLightStorage.FULL_LIGHT);
/*     */                 if (clientWorld.getEnvironment() == Environment.NORMAL) {
/*     */                   chunkSectionLightImpl.setSkyLight(ChunkLightStorage.FULL_LIGHT);
/*     */                 }
/*     */               } else {
/*     */                 byte[] blockLight = chunkLight.getBlockLight()[i];
/*     */                 chunkSectionLightImpl.setBlockLight((blockLight != null) ? blockLight : ChunkLightStorage.FULL_LIGHT);
/*     */                 if (clientWorld.getEnvironment() == Environment.NORMAL) {
/*     */                   byte[] skyLight = chunkLight.getSkyLight()[i];
/*     */                   chunkSectionLightImpl.setSkyLight((skyLight != null) ? skyLight : ChunkLightStorage.FULL_LIGHT);
/*     */                 } 
/*     */               } 
/*     */               DataPalette palette = section.palette(PaletteType.BLOCKS);
/*     */               if (Via.getConfig().isNonFullBlockLightFix() && section.getNonAirBlocksCount() != 0 && chunkSectionLightImpl.hasBlockLight()) {
/*     */                 for (int x = 0; x < 16; x++) {
/*     */                   for (int y = 0; y < 16; y++) {
/*     */                     for (int z = 0; z < 16; z++) {
/*     */                       int id = palette.idAt(x, y, z);
/*     */                       if (Protocol1_14To1_13_2.MAPPINGS.getNonFullBlocks().contains(id)) {
/*     */                         chunkSectionLightImpl.getBlockLightNibbleArray().set(x, y, z, 0);
/*     */                       }
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */               for (int j = 0; j < palette.size(); j++) {
/*     */                 int mappedBlockStateId = ((Protocol1_13_2To1_14)this.protocol).getMappingData().getNewBlockStateId(palette.idByIndex(j));
/*     */                 palette.setIdByIndex(j, mappedBlockStateId);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         });
/* 414 */     ((Protocol1_13_2To1_14)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.UNLOAD_CHUNK, wrapper -> {
/*     */           int x = ((Integer)wrapper.passthrough((Type)Type.INT)).intValue();
/*     */           
/*     */           int z = ((Integer)wrapper.passthrough((Type)Type.INT)).intValue();
/*     */           ((ChunkLightStorage)wrapper.user().get(ChunkLightStorage.class)).unloadChunk(x, z);
/*     */         });
/* 420 */     ((Protocol1_13_2To1_14)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 423 */             map((Type)Type.INT);
/* 424 */             map(Type.POSITION1_14, Type.POSITION);
/* 425 */             map((Type)Type.INT);
/* 426 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   int data = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   if (id == 1010) {
/*     */                     wrapper.set((Type)Type.INT, 1, Integer.valueOf(((Protocol1_13_2To1_14)BlockItemPackets1_14.this.protocol).getMappingData().getNewItemId(data)));
/*     */                   } else if (id == 2001) {
/*     */                     wrapper.set((Type)Type.INT, 1, Integer.valueOf(((Protocol1_13_2To1_14)BlockItemPackets1_14.this.protocol).getMappingData().getNewBlockStateId(data)));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 438 */     registerSpawnParticle((ClientboundPacketType)ClientboundPackets1_14.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, (Type)Type.FLOAT);
/*     */     
/* 440 */     ((Protocol1_13_2To1_14)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.MAP_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 443 */             map((Type)Type.VAR_INT);
/* 444 */             map((Type)Type.BYTE);
/* 445 */             map((Type)Type.BOOLEAN);
/* 446 */             map((Type)Type.BOOLEAN, (Type)Type.NOTHING);
/*     */           }
/*     */         });
/*     */     
/* 450 */     ((Protocol1_13_2To1_14)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.SPAWN_POSITION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 453 */             map(Type.POSITION1_14, Type.POSITION);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerRewrites() {
/* 460 */     this.enchantmentRewriter = new EnchantmentRewriter(this, false);
/* 461 */     this.enchantmentRewriter.registerEnchantment("minecraft:multishot", "§7Multishot");
/* 462 */     this.enchantmentRewriter.registerEnchantment("minecraft:quick_charge", "§7Quick Charge");
/* 463 */     this.enchantmentRewriter.registerEnchantment("minecraft:piercing", "§7Piercing");
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToClient(Item item) {
/* 468 */     if (item == null) return null; 
/* 469 */     super.handleItemToClient(item);
/*     */ 
/*     */     
/* 472 */     CompoundTag tag = item.tag();
/*     */     CompoundTag display;
/* 474 */     if (tag != null && (display = (CompoundTag)tag.get("display")) != null) {
/* 475 */       ListTag lore = (ListTag)display.get("Lore");
/* 476 */       if (lore != null) {
/* 477 */         saveListTag(display, lore, "Lore");
/*     */         
/* 479 */         for (Tag loreEntry : lore) {
/* 480 */           if (!(loreEntry instanceof StringTag))
/*     */             continue; 
/* 482 */           StringTag loreEntryTag = (StringTag)loreEntry;
/* 483 */           String value = loreEntryTag.getValue();
/* 484 */           if (value != null && !value.isEmpty()) {
/* 485 */             loreEntryTag.setValue(ChatRewriter.jsonToLegacyText(value));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 491 */     this.enchantmentRewriter.handleToClient(item);
/* 492 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToServer(Item item) {
/* 497 */     if (item == null) return null;
/*     */ 
/*     */     
/* 500 */     CompoundTag tag = item.tag();
/*     */     CompoundTag display;
/* 502 */     if (tag != null && (display = (CompoundTag)tag.get("display")) != null) {
/*     */       
/* 504 */       ListTag lore = (ListTag)display.get("Lore");
/* 505 */       if (lore != null && !hasBackupTag(display, "Lore")) {
/* 506 */         for (Tag loreEntry : lore) {
/* 507 */           if (loreEntry instanceof StringTag) {
/* 508 */             StringTag loreEntryTag = (StringTag)loreEntry;
/* 509 */             loreEntryTag.setValue(ChatRewriter.legacyTextToJsonString(loreEntryTag.getValue()));
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 515 */     this.enchantmentRewriter.handleToServer(item);
/*     */ 
/*     */     
/* 518 */     super.handleItemToServer(item);
/* 519 */     return item;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_13_2to1_14\packets\BlockItemPackets1_14.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
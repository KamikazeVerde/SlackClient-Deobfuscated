/*     */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingDataLoader;
/*     */ import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
/*     */ import com.viaversion.viaversion.api.minecraft.BlockFace;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.PacketBlockConnectionProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.UserBlockData;
/*     */ import com.viaversion.viaversion.util.Key;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public final class ConnectionData
/*     */ {
/*     */   public static BlockConnectionProvider blockConnectionProvider;
/*  56 */   static final Object2IntMap<String> KEY_TO_ID = (Object2IntMap<String>)new Object2IntOpenHashMap(8582, 0.99F);
/*  57 */   static final IntSet OCCLUDING_STATES = (IntSet)new IntOpenHashSet(377, 0.99F);
/*  58 */   static Int2ObjectMap<ConnectionHandler> connectionHandlerMap = (Int2ObjectMap<ConnectionHandler>)new Int2ObjectOpenHashMap();
/*  59 */   static Int2ObjectMap<BlockData> blockConnectionData = (Int2ObjectMap<BlockData>)new Int2ObjectOpenHashMap();
/*  60 */   private static final BlockChangeRecord1_8[] EMPTY_RECORDS = new BlockChangeRecord1_8[0];
/*     */   
/*     */   static {
/*  63 */     KEY_TO_ID.defaultReturnValue(-1);
/*     */   }
/*     */   
/*     */   public static void update(UserConnection user, Position position) throws Exception {
/*  67 */     for (BlockFace face : BlockFace.values()) {
/*  68 */       Position pos = position.getRelative(face);
/*  69 */       int blockState = blockConnectionProvider.getBlockData(user, pos.x(), pos.y(), pos.z());
/*  70 */       ConnectionHandler handler = (ConnectionHandler)connectionHandlerMap.get(blockState);
/*  71 */       if (handler != null) {
/*     */ 
/*     */ 
/*     */         
/*  75 */         int newBlockState = handler.connect(user, pos, blockState);
/*  76 */         if (newBlockState != blockState || !blockConnectionProvider.storesBlocks()) {
/*     */ 
/*     */ 
/*     */           
/*  80 */           updateBlockStorage(user, pos.x(), pos.y(), pos.z(), newBlockState);
/*     */           
/*  82 */           PacketWrapper blockUpdatePacket = PacketWrapper.create((PacketType)ClientboundPackets1_13.BLOCK_CHANGE, null, user);
/*  83 */           blockUpdatePacket.write(Type.POSITION, pos);
/*  84 */           blockUpdatePacket.write((Type)Type.VAR_INT, Integer.valueOf(newBlockState));
/*  85 */           blockUpdatePacket.send(Protocol1_13To1_12_2.class);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } public static void updateBlockStorage(UserConnection userConnection, int x, int y, int z, int blockState) {
/*  90 */     if (!needStoreBlocks())
/*  91 */       return;  if (isWelcome(blockState)) {
/*  92 */       blockConnectionProvider.storeBlock(userConnection, x, y, z, blockState);
/*     */     } else {
/*  94 */       blockConnectionProvider.removeBlock(userConnection, x, y, z);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void clearBlockStorage(UserConnection connection) {
/*  99 */     if (!needStoreBlocks())
/* 100 */       return;  blockConnectionProvider.clearStorage(connection);
/*     */   }
/*     */   
/*     */   public static boolean needStoreBlocks() {
/* 104 */     return blockConnectionProvider.storesBlocks();
/*     */   }
/*     */   
/*     */   public static void connectBlocks(UserConnection user, Chunk chunk) {
/* 108 */     int xOff = chunk.getX() << 4;
/* 109 */     int zOff = chunk.getZ() << 4;
/*     */     
/* 111 */     for (int s = 0; s < (chunk.getSections()).length; s++) {
/* 112 */       ChunkSection section = chunk.getSections()[s];
/* 113 */       if (section != null) {
/*     */ 
/*     */ 
/*     */         
/* 117 */         DataPalette blocks = section.palette(PaletteType.BLOCKS);
/*     */         
/* 119 */         boolean willConnect = false;
/* 120 */         for (int p = 0; p < blocks.size(); p++) {
/* 121 */           int id = blocks.idByIndex(p);
/* 122 */           if (connects(id)) {
/* 123 */             willConnect = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 127 */         if (willConnect) {
/*     */ 
/*     */ 
/*     */           
/* 131 */           int yOff = s << 4;
/*     */           
/* 133 */           for (int idx = 0; idx < 4096; idx++) {
/* 134 */             int id = blocks.idAt(idx);
/* 135 */             ConnectionHandler handler = getConnectionHandler(id);
/* 136 */             if (handler != null) {
/*     */ 
/*     */ 
/*     */               
/* 140 */               Position position = new Position(xOff + ChunkSection.xFromIndex(idx), yOff + ChunkSection.yFromIndex(idx), zOff + ChunkSection.zFromIndex(idx));
/* 141 */               int connectedId = handler.connect(user, position, id);
/* 142 */               if (connectedId != id) {
/* 143 */                 blocks.setIdAt(idx, connectedId);
/* 144 */                 updateBlockStorage(user, position.x(), position.y(), position.z(), connectedId);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }  } public static void init() {
/* 151 */     if (!Via.getConfig().isServersideBlockConnections()) {
/*     */       return;
/*     */     }
/*     */     
/* 155 */     Via.getPlatform().getLogger().info("Loading block connection mappings ...");
/* 156 */     ListTag blockStates = (ListTag)MappingDataLoader.loadNBT("blockstates-1.13.nbt").get("blockstates");
/* 157 */     for (int id = 0; id < blockStates.size(); id++) {
/* 158 */       String key = (String)blockStates.get(id).getValue();
/* 159 */       KEY_TO_ID.put(key, id);
/*     */     } 
/*     */     
/* 162 */     connectionHandlerMap = (Int2ObjectMap<ConnectionHandler>)new Int2ObjectOpenHashMap(3650, 0.99F);
/*     */     
/* 164 */     if (!Via.getConfig().isReduceBlockStorageMemory()) {
/* 165 */       blockConnectionData = (Int2ObjectMap<BlockData>)new Int2ObjectOpenHashMap(2048);
/*     */       
/* 167 */       ListTag blockConnectionMappings = (ListTag)MappingDataLoader.loadNBT("blockConnections.nbt").get("data");
/* 168 */       for (Tag blockTag : blockConnectionMappings) {
/* 169 */         CompoundTag blockCompoundTag = (CompoundTag)blockTag;
/* 170 */         BlockData blockData = new BlockData();
/* 171 */         for (Map.Entry<String, Tag> entry : (Iterable<Map.Entry<String, Tag>>)blockCompoundTag.entrySet()) {
/* 172 */           String key = entry.getKey();
/* 173 */           if (key.equals("id") || key.equals("ids")) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */           
/* 178 */           boolean[] attachingFaces = new boolean[4];
/* 179 */           ByteArrayTag connections = (ByteArrayTag)entry.getValue();
/* 180 */           for (byte blockFaceId : connections.getValue()) {
/* 181 */             attachingFaces[blockFaceId] = true;
/*     */           }
/*     */           
/* 184 */           int connectionTypeId = Integer.parseInt(key);
/* 185 */           blockData.put(connectionTypeId, attachingFaces);
/*     */         } 
/*     */         
/* 188 */         NumberTag idTag = (NumberTag)blockCompoundTag.get("id");
/* 189 */         if (idTag != null) {
/* 190 */           blockConnectionData.put(idTag.asInt(), blockData); continue;
/*     */         } 
/* 192 */         IntArrayTag idsTag = (IntArrayTag)blockCompoundTag.get("ids");
/* 193 */         for (int i : idsTag.getValue()) {
/* 194 */           blockConnectionData.put(i, blockData);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 200 */     for (String state : occludingBlockStates()) {
/* 201 */       OCCLUDING_STATES.add(KEY_TO_ID.getInt(state));
/*     */     }
/*     */     
/* 204 */     List<ConnectorInitAction> initActions = new ArrayList<>();
/* 205 */     initActions.add(PumpkinConnectionHandler.init());
/* 206 */     initActions.addAll(BasicFenceConnectionHandler.init());
/* 207 */     initActions.add(NetherFenceConnectionHandler.init());
/* 208 */     initActions.addAll(WallConnectionHandler.init());
/* 209 */     initActions.add(MelonConnectionHandler.init());
/* 210 */     initActions.addAll(GlassConnectionHandler.init());
/* 211 */     initActions.add(ChestConnectionHandler.init());
/* 212 */     initActions.add(DoorConnectionHandler.init());
/* 213 */     initActions.add(RedstoneConnectionHandler.init());
/* 214 */     initActions.add(StairConnectionHandler.init());
/* 215 */     initActions.add(FlowerConnectionHandler.init());
/* 216 */     initActions.addAll(ChorusPlantConnectionHandler.init());
/* 217 */     initActions.add(TripwireConnectionHandler.init());
/* 218 */     initActions.add(SnowyGrassConnectionHandler.init());
/* 219 */     initActions.add(FireConnectionHandler.init());
/* 220 */     if (Via.getConfig().isVineClimbFix()) {
/* 221 */       initActions.add(VineConnectionHandler.init());
/*     */     }
/*     */     
/* 224 */     for (ObjectIterator<String> objectIterator = KEY_TO_ID.keySet().iterator(); objectIterator.hasNext(); ) { String key = objectIterator.next();
/* 225 */       WrappedBlockData wrappedBlockData = WrappedBlockData.fromString(key);
/* 226 */       for (ConnectorInitAction action : initActions) {
/* 227 */         action.check(wrappedBlockData);
/*     */       } }
/*     */ 
/*     */     
/* 231 */     if (Via.getConfig().getBlockConnectionMethod().equalsIgnoreCase("packet")) {
/* 232 */       blockConnectionProvider = (BlockConnectionProvider)new PacketBlockConnectionProvider();
/* 233 */       Via.getManager().getProviders().register(BlockConnectionProvider.class, (Provider)blockConnectionProvider);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isWelcome(int blockState) {
/* 238 */     return (blockConnectionData.containsKey(blockState) || connectionHandlerMap.containsKey(blockState));
/*     */   }
/*     */   
/*     */   public static boolean connects(int blockState) {
/* 242 */     return connectionHandlerMap.containsKey(blockState);
/*     */   }
/*     */   
/*     */   public static int connect(UserConnection user, Position position, int blockState) {
/* 246 */     ConnectionHandler handler = (ConnectionHandler)connectionHandlerMap.get(blockState);
/* 247 */     return (handler != null) ? handler.connect(user, position, blockState) : blockState;
/*     */   }
/*     */   
/*     */   public static ConnectionHandler getConnectionHandler(int blockstate) {
/* 251 */     return (ConnectionHandler)connectionHandlerMap.get(blockstate);
/*     */   }
/*     */   
/*     */   public static int getId(String key) {
/* 255 */     return KEY_TO_ID.getOrDefault(Key.stripMinecraftNamespace(key), -1);
/*     */   }
/*     */   
/*     */   private static String[] occludingBlockStates() {
/* 259 */     return new String[] { "stone", "granite", "polished_granite", "diorite", "polished_diorite", "andesite", "polished_andesite", "grass_block[snowy=false]", "dirt", "coarse_dirt", "podzol[snowy=false]", "cobblestone", "oak_planks", "spruce_planks", "birch_planks", "jungle_planks", "acacia_planks", "dark_oak_planks", "bedrock", "sand", "red_sand", "gravel", "gold_ore", "iron_ore", "coal_ore", "oak_log[axis=x]", "oak_log[axis=y]", "oak_log[axis=z]", "spruce_log[axis=x]", "spruce_log[axis=y]", "spruce_log[axis=z]", "birch_log[axis=x]", "birch_log[axis=y]", "birch_log[axis=z]", "jungle_log[axis=x]", "jungle_log[axis=y]", "jungle_log[axis=z]", "acacia_log[axis=x]", "acacia_log[axis=y]", "acacia_log[axis=z]", "dark_oak_log[axis=x]", "dark_oak_log[axis=y]", "dark_oak_log[axis=z]", "oak_wood[axis=y]", "spruce_wood[axis=y]", "birch_wood[axis=y]", "jungle_wood[axis=y]", "acacia_wood[axis=y]", "dark_oak_wood[axis=y]", "sponge", "wet_sponge", "lapis_ore", "lapis_block", "dispenser[facing=north,triggered=true]", "dispenser[facing=north,triggered=false]", "dispenser[facing=east,triggered=true]", "dispenser[facing=east,triggered=false]", "dispenser[facing=south,triggered=true]", "dispenser[facing=south,triggered=false]", "dispenser[facing=west,triggered=true]", "dispenser[facing=west,triggered=false]", "dispenser[facing=up,triggered=true]", "dispenser[facing=up,triggered=false]", "dispenser[facing=down,triggered=true]", "dispenser[facing=down,triggered=false]", "sandstone", "chiseled_sandstone", "cut_sandstone", "note_block[instrument=harp,note=0,powered=false]", "white_wool", "orange_wool", "magenta_wool", "light_blue_wool", "yellow_wool", "lime_wool", "pink_wool", "gray_wool", "light_gray_wool", "cyan_wool", "purple_wool", "blue_wool", "brown_wool", "green_wool", "red_wool", "black_wool", "gold_block", "iron_block", "bricks", "bookshelf", "mossy_cobblestone", "obsidian", "spawner", "diamond_ore", "diamond_block", "crafting_table", "furnace[facing=north,lit=true]", "furnace[facing=north,lit=false]", "furnace[facing=south,lit=true]", "furnace[facing=south,lit=false]", "furnace[facing=west,lit=true]", "furnace[facing=west,lit=false]", "furnace[facing=east,lit=true]", "furnace[facing=east,lit=false]", "redstone_ore[lit=true]", "redstone_ore[lit=false]", "snow_block", "clay", "jukebox[has_record=true]", "jukebox[has_record=false]", "netherrack", "soul_sand", "carved_pumpkin[facing=north]", "carved_pumpkin[facing=south]", "carved_pumpkin[facing=west]", "carved_pumpkin[facing=east]", "jack_o_lantern[facing=north]", "jack_o_lantern[facing=south]", "jack_o_lantern[facing=west]", "jack_o_lantern[facing=east]", "infested_stone", "infested_cobblestone", "infested_stone_bricks", "infested_mossy_stone_bricks", "infested_cracked_stone_bricks", "infested_chiseled_stone_bricks", "stone_bricks", "mossy_stone_bricks", "cracked_stone_bricks", "chiseled_stone_bricks", "brown_mushroom_block[down=true,east=true,north=true,south=true,up=true,west=true]", "brown_mushroom_block[down=false,east=true,north=true,south=false,up=true,west=false]", "brown_mushroom_block[down=false,east=true,north=false,south=true,up=true,west=false]", "brown_mushroom_block[down=false,east=true,north=false,south=false,up=true,west=false]", "brown_mushroom_block[down=false,east=false,north=true,south=false,up=true,west=true]", "brown_mushroom_block[down=false,east=false,north=true,south=false,up=true,west=false]", "brown_mushroom_block[down=false,east=false,north=false,south=true,up=true,west=true]", "brown_mushroom_block[down=false,east=false,north=false,south=true,up=true,west=false]", "brown_mushroom_block[down=false,east=false,north=false,south=false,up=true,west=true]", "brown_mushroom_block[down=false,east=false,north=false,south=false,up=true,west=false]", "brown_mushroom_block[down=false,east=false,north=false,south=false,up=false,west=false]", "red_mushroom_block[down=true,east=true,north=true,south=true,up=true,west=true]", "red_mushroom_block[down=false,east=true,north=true,south=false,up=true,west=false]", "red_mushroom_block[down=false,east=true,north=false,south=true,up=true,west=false]", "red_mushroom_block[down=false,east=true,north=false,south=false,up=true,west=false]", "red_mushroom_block[down=false,east=false,north=true,south=false,up=true,west=true]", "red_mushroom_block[down=false,east=false,north=true,south=false,up=true,west=false]", "red_mushroom_block[down=false,east=false,north=false,south=true,up=true,west=true]", "red_mushroom_block[down=false,east=false,north=false,south=true,up=true,west=false]", "red_mushroom_block[down=false,east=false,north=false,south=false,up=true,west=true]", "red_mushroom_block[down=false,east=false,north=false,south=false,up=true,west=false]", "red_mushroom_block[down=false,east=false,north=false,south=false,up=false,west=false]", "mushroom_stem[down=true,east=true,north=true,south=true,up=true,west=true]", "mushroom_stem[down=false,east=true,north=true,south=true,up=false,west=true]", "melon", "mycelium[snowy=false]", "nether_bricks", "end_stone", "redstone_lamp[lit=true]", "redstone_lamp[lit=false]", "emerald_ore", "emerald_block", "command_block[conditional=true,facing=north]", "command_block[conditional=true,facing=east]", "command_block[conditional=true,facing=south]", "command_block[conditional=true,facing=west]", "command_block[conditional=true,facing=up]", "command_block[conditional=true,facing=down]", "command_block[conditional=false,facing=north]", "command_block[conditional=false,facing=east]", "command_block[conditional=false,facing=south]", "command_block[conditional=false,facing=west]", "command_block[conditional=false,facing=up]", "command_block[conditional=false,facing=down]", "nether_quartz_ore", "quartz_block", "chiseled_quartz_block", "quartz_pillar[axis=x]", "quartz_pillar[axis=y]", "quartz_pillar[axis=z]", "dropper[facing=north,triggered=true]", "dropper[facing=north,triggered=false]", "dropper[facing=east,triggered=true]", "dropper[facing=east,triggered=false]", "dropper[facing=south,triggered=true]", "dropper[facing=south,triggered=false]", "dropper[facing=west,triggered=true]", "dropper[facing=west,triggered=false]", "dropper[facing=up,triggered=true]", "dropper[facing=up,triggered=false]", "dropper[facing=down,triggered=true]", "dropper[facing=down,triggered=false]", "white_terracotta", "orange_terracotta", "magenta_terracotta", "light_blue_terracotta", "yellow_terracotta", "lime_terracotta", "pink_terracotta", "gray_terracotta", "light_gray_terracotta", "cyan_terracotta", "purple_terracotta", "blue_terracotta", "brown_terracotta", "green_terracotta", "red_terracotta", "black_terracotta", "slime_block", "barrier", "prismarine", "prismarine_bricks", "dark_prismarine", "hay_block[axis=x]", "hay_block[axis=y]", "hay_block[axis=z]", "terracotta", "coal_block", "packed_ice", "red_sandstone", "chiseled_red_sandstone", "cut_red_sandstone", "oak_slab[type=double,waterlogged=false]", "spruce_slab[type=double,waterlogged=false]", "birch_slab[type=double,waterlogged=false]", "jungle_slab[type=double,waterlogged=false]", "acacia_slab[type=double,waterlogged=false]", "dark_oak_slab[type=double,waterlogged=false]", "stone_slab[type=double,waterlogged=false]", "sandstone_slab[type=double,waterlogged=false]", "petrified_oak_slab[type=double,waterlogged=false]", "cobblestone_slab[type=double,waterlogged=false]", "brick_slab[type=double,waterlogged=false]", "stone_brick_slab[type=double,waterlogged=false]", "nether_brick_slab[type=double,waterlogged=false]", "quartz_slab[type=double,waterlogged=false]", "red_sandstone_slab[type=double,waterlogged=false]", "purpur_slab[type=double,waterlogged=false]", "smooth_stone", "smooth_sandstone", "smooth_quartz", "smooth_red_sandstone", "purpur_block", "purpur_pillar[axis=x]", "purpur_pillar[axis=y]", "purpur_pillar[axis=z]", "end_stone_bricks", "repeating_command_block[conditional=true,facing=north]", "repeating_command_block[conditional=true,facing=east]", "repeating_command_block[conditional=true,facing=south]", "repeating_command_block[conditional=true,facing=west]", "repeating_command_block[conditional=true,facing=up]", "repeating_command_block[conditional=true,facing=down]", "repeating_command_block[conditional=false,facing=north]", "repeating_command_block[conditional=false,facing=east]", "repeating_command_block[conditional=false,facing=south]", "repeating_command_block[conditional=false,facing=west]", "repeating_command_block[conditional=false,facing=up]", "repeating_command_block[conditional=false,facing=down]", "chain_command_block[conditional=true,facing=north]", "chain_command_block[conditional=true,facing=east]", "chain_command_block[conditional=true,facing=south]", "chain_command_block[conditional=true,facing=west]", "chain_command_block[conditional=true,facing=up]", "chain_command_block[conditional=true,facing=down]", "chain_command_block[conditional=false,facing=north]", "chain_command_block[conditional=false,facing=east]", "chain_command_block[conditional=false,facing=south]", "chain_command_block[conditional=false,facing=west]", "chain_command_block[conditional=false,facing=up]", "chain_command_block[conditional=false,facing=down]", "magma_block", "nether_wart_block", "red_nether_bricks", "bone_block[axis=x]", "bone_block[axis=y]", "bone_block[axis=z]", "white_glazed_terracotta[facing=north]", "white_glazed_terracotta[facing=south]", "white_glazed_terracotta[facing=west]", "white_glazed_terracotta[facing=east]", "orange_glazed_terracotta[facing=north]", "orange_glazed_terracotta[facing=south]", "orange_glazed_terracotta[facing=west]", "orange_glazed_terracotta[facing=east]", "magenta_glazed_terracotta[facing=north]", "magenta_glazed_terracotta[facing=south]", "magenta_glazed_terracotta[facing=west]", "magenta_glazed_terracotta[facing=east]", "light_blue_glazed_terracotta[facing=north]", "light_blue_glazed_terracotta[facing=south]", "light_blue_glazed_terracotta[facing=west]", "light_blue_glazed_terracotta[facing=east]", "yellow_glazed_terracotta[facing=north]", "yellow_glazed_terracotta[facing=south]", "yellow_glazed_terracotta[facing=west]", "yellow_glazed_terracotta[facing=east]", "lime_glazed_terracotta[facing=north]", "lime_glazed_terracotta[facing=south]", "lime_glazed_terracotta[facing=west]", "lime_glazed_terracotta[facing=east]", "pink_glazed_terracotta[facing=north]", "pink_glazed_terracotta[facing=south]", "pink_glazed_terracotta[facing=west]", "pink_glazed_terracotta[facing=east]", "gray_glazed_terracotta[facing=north]", "gray_glazed_terracotta[facing=south]", "gray_glazed_terracotta[facing=west]", "gray_glazed_terracotta[facing=east]", "light_gray_glazed_terracotta[facing=north]", "light_gray_glazed_terracotta[facing=south]", "light_gray_glazed_terracotta[facing=west]", "light_gray_glazed_terracotta[facing=east]", "cyan_glazed_terracotta[facing=north]", "cyan_glazed_terracotta[facing=south]", "cyan_glazed_terracotta[facing=west]", "cyan_glazed_terracotta[facing=east]", "purple_glazed_terracotta[facing=north]", "purple_glazed_terracotta[facing=south]", "purple_glazed_terracotta[facing=west]", "purple_glazed_terracotta[facing=east]", "blue_glazed_terracotta[facing=north]", "blue_glazed_terracotta[facing=south]", "blue_glazed_terracotta[facing=west]", "blue_glazed_terracotta[facing=east]", "brown_glazed_terracotta[facing=north]", "brown_glazed_terracotta[facing=south]", "brown_glazed_terracotta[facing=west]", "brown_glazed_terracotta[facing=east]", "green_glazed_terracotta[facing=north]", "green_glazed_terracotta[facing=south]", "green_glazed_terracotta[facing=west]", "green_glazed_terracotta[facing=east]", "red_glazed_terracotta[facing=north]", "red_glazed_terracotta[facing=south]", "red_glazed_terracotta[facing=west]", "red_glazed_terracotta[facing=east]", "black_glazed_terracotta[facing=north]", "black_glazed_terracotta[facing=south]", "black_glazed_terracotta[facing=west]", "black_glazed_terracotta[facing=east]", "white_concrete", "orange_concrete", "magenta_concrete", "light_blue_concrete", "yellow_concrete", "lime_concrete", "pink_concrete", "gray_concrete", "light_gray_concrete", "cyan_concrete", "purple_concrete", "blue_concrete", "brown_concrete", "green_concrete", "red_concrete", "black_concrete", "white_concrete_powder", "orange_concrete_powder", "magenta_concrete_powder", "light_blue_concrete_powder", "yellow_concrete_powder", "lime_concrete_powder", "pink_concrete_powder", "gray_concrete_powder", "light_gray_concrete_powder", "cyan_concrete_powder", "purple_concrete_powder", "blue_concrete_powder", "brown_concrete_powder", "green_concrete_powder", "red_concrete_powder", "black_concrete_powder", "structure_block[mode=save]", "structure_block[mode=load]", "structure_block[mode=corner]", "structure_block[mode=data]", "glowstone" };
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
/*     */   public static Object2IntMap<String> getKeyToId() {
/* 647 */     return KEY_TO_ID;
/*     */   }
/*     */   
/*     */   public static final class NeighbourUpdater {
/*     */     private final UserConnection user;
/*     */     private final UserBlockData userBlockData;
/*     */     
/*     */     public NeighbourUpdater(UserConnection user) {
/* 655 */       this.user = user;
/* 656 */       this.userBlockData = ConnectionData.blockConnectionProvider.forUser(user);
/*     */     }
/*     */     
/*     */     public void updateChunkSectionNeighbours(int chunkX, int chunkZ, int chunkSectionY) throws Exception {
/* 660 */       int chunkMinY = chunkSectionY << 4;
/* 661 */       List<BlockChangeRecord1_8> updates = new ArrayList<>();
/* 662 */       for (int chunkDeltaX = -1; chunkDeltaX <= 1; chunkDeltaX++) {
/* 663 */         for (int chunkDeltaZ = -1; chunkDeltaZ <= 1; chunkDeltaZ++) {
/* 664 */           int distance = Math.abs(chunkDeltaX) + Math.abs(chunkDeltaZ);
/* 665 */           if (distance != 0) {
/*     */             
/* 667 */             int chunkMinX = chunkX + chunkDeltaX << 4;
/* 668 */             int chunkMinZ = chunkZ + chunkDeltaZ << 4;
/* 669 */             if (distance == 2) {
/* 670 */               for (int blockY = chunkMinY; blockY < chunkMinY + 16; blockY++) {
/* 671 */                 int blockPosX = (chunkDeltaX == 1) ? 0 : 15;
/* 672 */                 int blockPosZ = (chunkDeltaZ == 1) ? 0 : 15;
/* 673 */                 updateBlock(chunkMinX + blockPosX, blockY, chunkMinZ + blockPosZ, updates);
/*     */               } 
/*     */             } else {
/* 676 */               for (int blockY = chunkMinY; blockY < chunkMinY + 16; blockY++) {
/*     */                 int xStart; int xEnd; int zStart;
/*     */                 int zEnd;
/* 679 */                 if (chunkDeltaX == 1) {
/* 680 */                   xStart = 0;
/* 681 */                   xEnd = 2;
/* 682 */                   zStart = 0;
/* 683 */                   zEnd = 16;
/* 684 */                 } else if (chunkDeltaX == -1) {
/* 685 */                   xStart = 14;
/* 686 */                   xEnd = 16;
/* 687 */                   zStart = 0;
/* 688 */                   zEnd = 16;
/* 689 */                 } else if (chunkDeltaZ == 1) {
/* 690 */                   xStart = 0;
/* 691 */                   xEnd = 16;
/* 692 */                   zStart = 0;
/* 693 */                   zEnd = 2;
/*     */                 } else {
/* 695 */                   xStart = 0;
/* 696 */                   xEnd = 16;
/* 697 */                   zStart = 14;
/* 698 */                   zEnd = 16;
/*     */                 } 
/* 700 */                 for (int blockX = xStart; blockX < xEnd; blockX++) {
/* 701 */                   for (int blockZ = zStart; blockZ < zEnd; blockZ++) {
/* 702 */                     updateBlock(chunkMinX + blockX, blockY, chunkMinZ + blockZ, updates);
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             
/* 708 */             if (!updates.isEmpty()) {
/* 709 */               PacketWrapper wrapper = PacketWrapper.create((PacketType)ClientboundPackets1_13.MULTI_BLOCK_CHANGE, null, this.user);
/* 710 */               wrapper.write((Type)Type.INT, Integer.valueOf(chunkX + chunkDeltaX));
/* 711 */               wrapper.write((Type)Type.INT, Integer.valueOf(chunkZ + chunkDeltaZ));
/* 712 */               wrapper.write(Type.BLOCK_CHANGE_RECORD_ARRAY, updates.toArray(ConnectionData.EMPTY_RECORDS));
/* 713 */               wrapper.send(Protocol1_13To1_12_2.class);
/* 714 */               updates.clear();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     private void updateBlock(int x, int y, int z, List<BlockChangeRecord1_8> records) {
/* 721 */       int blockState = this.userBlockData.getBlockData(x, y, z);
/* 722 */       ConnectionHandler handler = ConnectionData.getConnectionHandler(blockState);
/* 723 */       if (handler == null) {
/*     */         return;
/*     */       }
/*     */       
/* 727 */       Position pos = new Position(x, y, z);
/* 728 */       int newBlockState = handler.connect(this.user, pos, blockState);
/* 729 */       if (blockState != newBlockState || !ConnectionData.blockConnectionProvider.storesBlocks()) {
/* 730 */         records.add(new BlockChangeRecord1_8(x & 0xF, y, z & 0xF, newBlockState));
/* 731 */         ConnectionData.updateBlockStorage(this.user, x, y, z, newBlockState);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   static interface ConnectorInitAction {
/*     */     void check(WrappedBlockData param1WrappedBlockData);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\ConnectionData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
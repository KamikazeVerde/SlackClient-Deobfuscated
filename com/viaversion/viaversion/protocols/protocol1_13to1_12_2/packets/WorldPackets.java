/*     */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.Particle;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.NamedSoundRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.PaintingProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockStorage;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
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
/*  56 */   private static final IntSet VALID_BIOMES = (IntSet)new IntOpenHashSet(70, 0.99F);
/*     */   
/*     */   static {
/*     */     int i;
/*  60 */     for (i = 0; i < 50; i++) {
/*  61 */       VALID_BIOMES.add(i);
/*     */     }
/*  63 */     VALID_BIOMES.add(127);
/*  64 */     for (i = 129; i <= 134; i++) {
/*  65 */       VALID_BIOMES.add(i);
/*     */     }
/*  67 */     VALID_BIOMES.add(140);
/*  68 */     VALID_BIOMES.add(149);
/*  69 */     VALID_BIOMES.add(151);
/*  70 */     for (i = 155; i <= 158; i++) {
/*  71 */       VALID_BIOMES.add(i);
/*     */     }
/*  73 */     for (i = 160; i <= 167; i++) {
/*  74 */       VALID_BIOMES.add(i);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(Protocol1_13To1_12_2 protocol) {
/*  80 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.SPAWN_PAINTING, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  83 */             map((Type)Type.VAR_INT);
/*  84 */             map(Type.UUID);
/*     */             
/*  86 */             handler(wrapper -> {
/*     */                   PaintingProvider provider = (PaintingProvider)Via.getManager().getProviders().get(PaintingProvider.class);
/*     */                   
/*     */                   String motive = (String)wrapper.read(Type.STRING);
/*     */                   
/*     */                   Optional<Integer> id = provider.getIntByIdentifier(motive);
/*     */                   
/*     */                   if (!id.isPresent() && (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug())) {
/*     */                     Via.getPlatform().getLogger().warning("Could not find painting motive: " + motive + " falling back to default (0)");
/*     */                   }
/*     */                   wrapper.write((Type)Type.VAR_INT, id.orElse(Integer.valueOf(0)));
/*     */                 });
/*     */           }
/*     */         });
/* 100 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.BLOCK_ENTITY_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 103 */             map(Type.POSITION);
/* 104 */             map((Type)Type.UNSIGNED_BYTE);
/* 105 */             map(Type.NBT);
/*     */             
/* 107 */             handler(wrapper -> {
/*     */                   Position position = (Position)wrapper.get(Type.POSITION, 0);
/*     */                   
/*     */                   short action = ((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   
/*     */                   CompoundTag tag = (CompoundTag)wrapper.get(Type.NBT, 0);
/*     */                   
/*     */                   BlockEntityProvider provider = (BlockEntityProvider)Via.getManager().getProviders().get(BlockEntityProvider.class);
/*     */                   
/*     */                   int newId = provider.transform(wrapper.user(), position, tag, true);
/*     */                   if (newId != -1) {
/*     */                     BlockStorage storage = (BlockStorage)wrapper.user().get(BlockStorage.class);
/*     */                     BlockStorage.ReplacementData replacementData = storage.get(position);
/*     */                     if (replacementData != null) {
/*     */                       replacementData.setReplacement(newId);
/*     */                     }
/*     */                   } 
/*     */                   if (action == 5) {
/*     */                     wrapper.cancel();
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 130 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.BLOCK_ACTION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 133 */             map(Type.POSITION);
/* 134 */             map((Type)Type.UNSIGNED_BYTE);
/* 135 */             map((Type)Type.UNSIGNED_BYTE);
/* 136 */             map((Type)Type.VAR_INT);
/* 137 */             handler(wrapper -> {
/*     */                   Position pos = (Position)wrapper.get(Type.POSITION, 0);
/*     */                   
/*     */                   short action = ((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   
/*     */                   short param = ((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 1)).shortValue();
/*     */                   int blockId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   if (blockId == 25) {
/*     */                     blockId = 73;
/*     */                   } else if (blockId == 33) {
/*     */                     blockId = 99;
/*     */                   } else if (blockId == 29) {
/*     */                     blockId = 92;
/*     */                   } else if (blockId == 54) {
/*     */                     blockId = 142;
/*     */                   } else if (blockId == 146) {
/*     */                     blockId = 305;
/*     */                   } else if (blockId == 130) {
/*     */                     blockId = 249;
/*     */                   } else if (blockId == 138) {
/*     */                     blockId = 257;
/*     */                   } else if (blockId == 52) {
/*     */                     blockId = 140;
/*     */                   } else if (blockId == 209) {
/*     */                     blockId = 472;
/*     */                   } else if (blockId >= 219 && blockId <= 234) {
/*     */                     blockId = blockId - 219 + 483;
/*     */                   } 
/*     */                   if (blockId == 73) {
/*     */                     PacketWrapper blockChange = wrapper.create((PacketType)ClientboundPackets1_13.BLOCK_CHANGE);
/*     */                     blockChange.write(Type.POSITION, pos);
/*     */                     blockChange.write((Type)Type.VAR_INT, Integer.valueOf(249 + action * 24 * 2 + param * 2));
/*     */                     blockChange.send(Protocol1_13To1_12_2.class);
/*     */                   } 
/*     */                   wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(blockId));
/*     */                 });
/*     */           }
/*     */         });
/* 175 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 178 */             map(Type.POSITION);
/* 179 */             map((Type)Type.VAR_INT);
/* 180 */             handler(wrapper -> {
/*     */                   Position position = (Position)wrapper.get(Type.POSITION, 0);
/*     */                   
/*     */                   int newId = WorldPackets.toNewId(((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue());
/*     */                   
/*     */                   UserConnection userConnection = wrapper.user();
/*     */                   
/*     */                   if (Via.getConfig().isServersideBlockConnections()) {
/*     */                     newId = ConnectionData.connect(userConnection, position, newId);
/*     */                     
/*     */                     ConnectionData.updateBlockStorage(userConnection, position.x(), position.y(), position.z(), newId);
/*     */                   } 
/*     */                   
/*     */                   wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(WorldPackets.checkStorage(wrapper.user(), position, newId)));
/*     */                   if (Via.getConfig().isServersideBlockConnections()) {
/*     */                     wrapper.send(Protocol1_13To1_12_2.class);
/*     */                     wrapper.cancel();
/*     */                     ConnectionData.update(userConnection, position);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 202 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.MULTI_BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 205 */             map((Type)Type.INT);
/* 206 */             map((Type)Type.INT);
/* 207 */             map(Type.BLOCK_CHANGE_RECORD_ARRAY);
/* 208 */             handler(wrapper -> {
/*     */                   int chunkX = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   int chunkZ = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   
/*     */                   UserConnection userConnection = wrapper.user();
/*     */                   
/*     */                   BlockChangeRecord[] records = (BlockChangeRecord[])wrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0);
/*     */                   
/*     */                   for (BlockChangeRecord record : records) {
/*     */                     int newBlock = WorldPackets.toNewId(record.getBlockId());
/*     */                     
/*     */                     Position position = new Position(record.getSectionX() + (chunkX << 4), record.getY(), record.getSectionZ() + (chunkZ << 4));
/*     */                     
/*     */                     record.setBlockId(WorldPackets.checkStorage(wrapper.user(), position, newBlock));
/*     */                     
/*     */                     if (Via.getConfig().isServersideBlockConnections()) {
/*     */                       ConnectionData.updateBlockStorage(userConnection, position.x(), position.y(), position.z(), newBlock);
/*     */                     }
/*     */                   } 
/*     */                   
/*     */                   if (Via.getConfig().isServersideBlockConnections()) {
/*     */                     for (BlockChangeRecord record : records) {
/*     */                       int blockState = record.getBlockId();
/*     */                       
/*     */                       Position position = new Position(record.getSectionX() + chunkX * 16, record.getY(), record.getSectionZ() + chunkZ * 16);
/*     */                       
/*     */                       ConnectionHandler handler = ConnectionData.getConnectionHandler(blockState);
/*     */                       
/*     */                       if (handler != null) {
/*     */                         blockState = handler.connect(userConnection, position, blockState);
/*     */                         
/*     */                         record.setBlockId(blockState);
/*     */                         
/*     */                         ConnectionData.updateBlockStorage(userConnection, position.x(), position.y(), position.z(), blockState);
/*     */                       } 
/*     */                     } 
/*     */                     
/*     */                     wrapper.send(Protocol1_13To1_12_2.class);
/*     */                     
/*     */                     wrapper.cancel();
/*     */                     
/*     */                     for (BlockChangeRecord record : records) {
/*     */                       Position position = new Position(record.getSectionX() + chunkX * 16, record.getY(), record.getSectionZ() + chunkZ * 16);
/*     */                       
/*     */                       ConnectionData.update(userConnection, position);
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 260 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.EXPLOSION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 263 */             if (!Via.getConfig().isServersideBlockConnections()) {
/*     */               return;
/*     */             }
/*     */             
/* 267 */             map((Type)Type.FLOAT);
/* 268 */             map((Type)Type.FLOAT);
/* 269 */             map((Type)Type.FLOAT);
/* 270 */             map((Type)Type.FLOAT);
/* 271 */             map((Type)Type.INT);
/*     */             
/* 273 */             handler(wrapper -> {
/*     */                   UserConnection userConnection = wrapper.user();
/*     */                   
/*     */                   int x = (int)Math.floor(((Float)wrapper.get((Type)Type.FLOAT, 0)).floatValue());
/*     */                   
/*     */                   int y = (int)Math.floor(((Float)wrapper.get((Type)Type.FLOAT, 1)).floatValue());
/*     */                   
/*     */                   int z = (int)Math.floor(((Float)wrapper.get((Type)Type.FLOAT, 2)).floatValue());
/*     */                   
/*     */                   int recordCount = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   Position[] records = new Position[recordCount];
/*     */                   
/*     */                   int i;
/*     */                   
/*     */                   for (i = 0; i < recordCount; i++) {
/*     */                     Position position = new Position(x + ((Byte)wrapper.passthrough((Type)Type.BYTE)).byteValue(), (short)(y + ((Byte)wrapper.passthrough((Type)Type.BYTE)).byteValue()), z + ((Byte)wrapper.passthrough((Type)Type.BYTE)).byteValue());
/*     */                     
/*     */                     records[i] = position;
/*     */                     
/*     */                     ConnectionData.updateBlockStorage(userConnection, position.x(), position.y(), position.z(), 0);
/*     */                   } 
/*     */                   wrapper.send(Protocol1_13To1_12_2.class);
/*     */                   wrapper.cancel();
/*     */                   for (i = 0; i < recordCount; i++) {
/*     */                     ConnectionData.update(userConnection, records[i]);
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 303 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.UNLOAD_CHUNK, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 306 */             if (Via.getConfig().isServersideBlockConnections()) {
/* 307 */               handler(wrapper -> {
/*     */                     int x = ((Integer)wrapper.passthrough((Type)Type.INT)).intValue();
/*     */                     
/*     */                     int z = ((Integer)wrapper.passthrough((Type)Type.INT)).intValue();
/*     */                     ConnectionData.blockConnectionProvider.unloadChunk(wrapper.user(), x, z);
/*     */                   });
/*     */             }
/*     */           }
/*     */         });
/* 316 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.NAMED_SOUND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 319 */             map(Type.STRING);
/* 320 */             handler(wrapper -> {
/*     */                   String sound = ((String)wrapper.get(Type.STRING, 0)).replace("minecraft:", "");
/*     */                   
/*     */                   String newSoundId = NamedSoundRewriter.getNewId(sound);
/*     */                   wrapper.set(Type.STRING, 0, newSoundId);
/*     */                 });
/*     */           }
/*     */         });
/* 328 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.CHUNK_DATA, wrapper -> {
/*     */           // Byte code:
/*     */           //   0: aload_0
/*     */           //   1: invokeinterface user : ()Lcom/viaversion/viaversion/api/connection/UserConnection;
/*     */           //   6: ldc com/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/storage/ClientWorld
/*     */           //   8: invokeinterface get : (Ljava/lang/Class;)Lcom/viaversion/viaversion/api/connection/StorableObject;
/*     */           //   13: checkcast com/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/storage/ClientWorld
/*     */           //   16: astore_1
/*     */           //   17: aload_0
/*     */           //   18: invokeinterface user : ()Lcom/viaversion/viaversion/api/connection/UserConnection;
/*     */           //   23: ldc com/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockStorage
/*     */           //   25: invokeinterface get : (Ljava/lang/Class;)Lcom/viaversion/viaversion/api/connection/StorableObject;
/*     */           //   30: checkcast com/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockStorage
/*     */           //   33: astore_2
/*     */           //   34: new com/viaversion/viaversion/protocols/protocol1_9_1_2to1_9_3_4/types/Chunk1_9_3_4Type
/*     */           //   37: dup
/*     */           //   38: aload_1
/*     */           //   39: invokespecial <init> : (Lcom/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/storage/ClientWorld;)V
/*     */           //   42: astore_3
/*     */           //   43: new com/viaversion/viaversion/protocols/protocol1_13to1_12_2/types/Chunk1_13Type
/*     */           //   46: dup
/*     */           //   47: aload_1
/*     */           //   48: invokespecial <init> : (Lcom/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/storage/ClientWorld;)V
/*     */           //   51: astore #4
/*     */           //   53: aload_0
/*     */           //   54: aload_3
/*     */           //   55: invokeinterface read : (Lcom/viaversion/viaversion/api/type/Type;)Ljava/lang/Object;
/*     */           //   60: checkcast com/viaversion/viaversion/api/minecraft/chunks/Chunk
/*     */           //   63: astore #5
/*     */           //   65: aload_0
/*     */           //   66: aload #4
/*     */           //   68: aload #5
/*     */           //   70: invokeinterface write : (Lcom/viaversion/viaversion/api/type/Type;Ljava/lang/Object;)V
/*     */           //   75: iconst_0
/*     */           //   76: istore #6
/*     */           //   78: iload #6
/*     */           //   80: aload #5
/*     */           //   82: invokeinterface getSections : ()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
/*     */           //   87: arraylength
/*     */           //   88: if_icmpge -> 570
/*     */           //   91: aload #5
/*     */           //   93: invokeinterface getSections : ()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
/*     */           //   98: iload #6
/*     */           //   100: aaload
/*     */           //   101: astore #7
/*     */           //   103: aload #7
/*     */           //   105: ifnonnull -> 111
/*     */           //   108: goto -> 564
/*     */           //   111: aload #7
/*     */           //   113: getstatic com/viaversion/viaversion/api/minecraft/chunks/PaletteType.BLOCKS : Lcom/viaversion/viaversion/api/minecraft/chunks/PaletteType;
/*     */           //   116: invokeinterface palette : (Lcom/viaversion/viaversion/api/minecraft/chunks/PaletteType;)Lcom/viaversion/viaversion/api/minecraft/chunks/DataPalette;
/*     */           //   121: astore #8
/*     */           //   123: iconst_0
/*     */           //   124: istore #9
/*     */           //   126: iload #9
/*     */           //   128: aload #8
/*     */           //   130: invokeinterface size : ()I
/*     */           //   135: if_icmpge -> 173
/*     */           //   138: aload #8
/*     */           //   140: iload #9
/*     */           //   142: invokeinterface idByIndex : (I)I
/*     */           //   147: istore #10
/*     */           //   149: iload #10
/*     */           //   151: invokestatic toNewId : (I)I
/*     */           //   154: istore #11
/*     */           //   156: aload #8
/*     */           //   158: iload #9
/*     */           //   160: iload #11
/*     */           //   162: invokeinterface setIdByIndex : (II)V
/*     */           //   167: iinc #9, 1
/*     */           //   170: goto -> 126
/*     */           //   173: aload #5
/*     */           //   175: invokeinterface isFullChunk : ()Z
/*     */           //   180: ifeq -> 237
/*     */           //   183: iconst_0
/*     */           //   184: istore #9
/*     */           //   186: iconst_0
/*     */           //   187: istore #10
/*     */           //   189: iload #10
/*     */           //   191: aload #8
/*     */           //   193: invokeinterface size : ()I
/*     */           //   198: if_icmpge -> 229
/*     */           //   201: aload_2
/*     */           //   202: aload #8
/*     */           //   204: iload #10
/*     */           //   206: invokeinterface idByIndex : (I)I
/*     */           //   211: invokevirtual isWelcome : (I)Z
/*     */           //   214: ifeq -> 223
/*     */           //   217: iconst_1
/*     */           //   218: istore #9
/*     */           //   220: goto -> 229
/*     */           //   223: iinc #10, 1
/*     */           //   226: goto -> 189
/*     */           //   229: iload #9
/*     */           //   231: ifne -> 237
/*     */           //   234: goto -> 351
/*     */           //   237: iconst_0
/*     */           //   238: istore #9
/*     */           //   240: iload #9
/*     */           //   242: sipush #4096
/*     */           //   245: if_icmpge -> 351
/*     */           //   248: aload #8
/*     */           //   250: iload #9
/*     */           //   252: invokeinterface idAt : (I)I
/*     */           //   257: istore #10
/*     */           //   259: new com/viaversion/viaversion/api/minecraft/Position
/*     */           //   262: dup
/*     */           //   263: iload #9
/*     */           //   265: invokestatic xFromIndex : (I)I
/*     */           //   268: aload #5
/*     */           //   270: invokeinterface getX : ()I
/*     */           //   275: iconst_4
/*     */           //   276: ishl
/*     */           //   277: iadd
/*     */           //   278: iload #9
/*     */           //   280: invokestatic yFromIndex : (I)I
/*     */           //   283: iload #6
/*     */           //   285: iconst_4
/*     */           //   286: ishl
/*     */           //   287: iadd
/*     */           //   288: iload #9
/*     */           //   290: invokestatic zFromIndex : (I)I
/*     */           //   293: aload #5
/*     */           //   295: invokeinterface getZ : ()I
/*     */           //   300: iconst_4
/*     */           //   301: ishl
/*     */           //   302: iadd
/*     */           //   303: invokespecial <init> : (III)V
/*     */           //   306: astore #11
/*     */           //   308: aload_2
/*     */           //   309: iload #10
/*     */           //   311: invokevirtual isWelcome : (I)Z
/*     */           //   314: ifeq -> 328
/*     */           //   317: aload_2
/*     */           //   318: aload #11
/*     */           //   320: iload #10
/*     */           //   322: invokevirtual store : (Lcom/viaversion/viaversion/api/minecraft/Position;I)V
/*     */           //   325: goto -> 345
/*     */           //   328: aload #5
/*     */           //   330: invokeinterface isFullChunk : ()Z
/*     */           //   335: ifne -> 345
/*     */           //   338: aload_2
/*     */           //   339: aload #11
/*     */           //   341: invokevirtual remove : (Lcom/viaversion/viaversion/api/minecraft/Position;)Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockStorage$ReplacementData;
/*     */           //   344: pop
/*     */           //   345: iinc #9, 1
/*     */           //   348: goto -> 240
/*     */           //   351: invokestatic getConfig : ()Lcom/viaversion/viaversion/api/configuration/ViaVersionConfig;
/*     */           //   354: invokeinterface isServersideBlockConnections : ()Z
/*     */           //   359: ifeq -> 564
/*     */           //   362: invokestatic needStoreBlocks : ()Z
/*     */           //   365: ifne -> 371
/*     */           //   368: goto -> 564
/*     */           //   371: aload #5
/*     */           //   373: invokeinterface isFullChunk : ()Z
/*     */           //   378: ifne -> 409
/*     */           //   381: getstatic com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/ConnectionData.blockConnectionProvider : Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/providers/BlockConnectionProvider;
/*     */           //   384: aload_0
/*     */           //   385: invokeinterface user : ()Lcom/viaversion/viaversion/api/connection/UserConnection;
/*     */           //   390: aload #5
/*     */           //   392: invokeinterface getX : ()I
/*     */           //   397: iload #6
/*     */           //   399: aload #5
/*     */           //   401: invokeinterface getZ : ()I
/*     */           //   406: invokevirtual unloadChunkSection : (Lcom/viaversion/viaversion/api/connection/UserConnection;III)V
/*     */           //   409: iconst_0
/*     */           //   410: istore #9
/*     */           //   412: iconst_0
/*     */           //   413: istore #10
/*     */           //   415: iload #10
/*     */           //   417: aload #8
/*     */           //   419: invokeinterface size : ()I
/*     */           //   424: if_icmpge -> 454
/*     */           //   427: aload #8
/*     */           //   429: iload #10
/*     */           //   431: invokeinterface idByIndex : (I)I
/*     */           //   436: invokestatic isWelcome : (I)Z
/*     */           //   439: ifeq -> 448
/*     */           //   442: iconst_1
/*     */           //   443: istore #9
/*     */           //   445: goto -> 454
/*     */           //   448: iinc #10, 1
/*     */           //   451: goto -> 415
/*     */           //   454: iload #9
/*     */           //   456: ifne -> 462
/*     */           //   459: goto -> 564
/*     */           //   462: iconst_0
/*     */           //   463: istore #10
/*     */           //   465: iload #10
/*     */           //   467: sipush #4096
/*     */           //   470: if_icmpge -> 564
/*     */           //   473: aload #8
/*     */           //   475: iload #10
/*     */           //   477: invokeinterface idAt : (I)I
/*     */           //   482: istore #11
/*     */           //   484: iload #11
/*     */           //   486: invokestatic isWelcome : (I)Z
/*     */           //   489: ifeq -> 558
/*     */           //   492: iload #10
/*     */           //   494: invokestatic xFromIndex : (I)I
/*     */           //   497: aload #5
/*     */           //   499: invokeinterface getX : ()I
/*     */           //   504: iconst_4
/*     */           //   505: ishl
/*     */           //   506: iadd
/*     */           //   507: istore #12
/*     */           //   509: iload #10
/*     */           //   511: invokestatic yFromIndex : (I)I
/*     */           //   514: iload #6
/*     */           //   516: iconst_4
/*     */           //   517: ishl
/*     */           //   518: iadd
/*     */           //   519: istore #13
/*     */           //   521: iload #10
/*     */           //   523: invokestatic zFromIndex : (I)I
/*     */           //   526: aload #5
/*     */           //   528: invokeinterface getZ : ()I
/*     */           //   533: iconst_4
/*     */           //   534: ishl
/*     */           //   535: iadd
/*     */           //   536: istore #14
/*     */           //   538: getstatic com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/ConnectionData.blockConnectionProvider : Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/providers/BlockConnectionProvider;
/*     */           //   541: aload_0
/*     */           //   542: invokeinterface user : ()Lcom/viaversion/viaversion/api/connection/UserConnection;
/*     */           //   547: iload #12
/*     */           //   549: iload #13
/*     */           //   551: iload #14
/*     */           //   553: iload #11
/*     */           //   555: invokevirtual storeBlock : (Lcom/viaversion/viaversion/api/connection/UserConnection;IIII)V
/*     */           //   558: iinc #10, 1
/*     */           //   561: goto -> 465
/*     */           //   564: iinc #6, 1
/*     */           //   567: goto -> 78
/*     */           //   570: aload #5
/*     */           //   572: invokeinterface isBiomeData : ()Z
/*     */           //   577: ifeq -> 711
/*     */           //   580: ldc_w -2147483648
/*     */           //   583: istore #6
/*     */           //   585: iconst_0
/*     */           //   586: istore #7
/*     */           //   588: iload #7
/*     */           //   590: sipush #256
/*     */           //   593: if_icmpge -> 711
/*     */           //   596: aload #5
/*     */           //   598: invokeinterface getBiomeData : ()[I
/*     */           //   603: iload #7
/*     */           //   605: iaload
/*     */           //   606: istore #8
/*     */           //   608: getstatic com/viaversion/viaversion/protocols/protocol1_13to1_12_2/packets/WorldPackets.VALID_BIOMES : Lcom/viaversion/viaversion/libs/fastutil/ints/IntSet;
/*     */           //   611: iload #8
/*     */           //   613: invokeinterface contains : (I)Z
/*     */           //   618: ifne -> 705
/*     */           //   621: iload #8
/*     */           //   623: sipush #255
/*     */           //   626: if_icmpeq -> 694
/*     */           //   629: iload #6
/*     */           //   631: iload #8
/*     */           //   633: if_icmpeq -> 694
/*     */           //   636: invokestatic getConfig : ()Lcom/viaversion/viaversion/api/configuration/ViaVersionConfig;
/*     */           //   639: invokeinterface isSuppressConversionWarnings : ()Z
/*     */           //   644: ifeq -> 658
/*     */           //   647: invokestatic getManager : ()Lcom/viaversion/viaversion/api/ViaManager;
/*     */           //   650: invokeinterface isDebug : ()Z
/*     */           //   655: ifeq -> 690
/*     */           //   658: invokestatic getPlatform : ()Lcom/viaversion/viaversion/api/platform/ViaPlatform;
/*     */           //   661: invokeinterface getLogger : ()Ljava/util/logging/Logger;
/*     */           //   666: new java/lang/StringBuilder
/*     */           //   669: dup
/*     */           //   670: invokespecial <init> : ()V
/*     */           //   673: ldc_w 'Received invalid biome id '
/*     */           //   676: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */           //   679: iload #8
/*     */           //   681: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */           //   684: invokevirtual toString : ()Ljava/lang/String;
/*     */           //   687: invokevirtual warning : (Ljava/lang/String;)V
/*     */           //   690: iload #8
/*     */           //   692: istore #6
/*     */           //   694: aload #5
/*     */           //   696: invokeinterface getBiomeData : ()[I
/*     */           //   701: iload #7
/*     */           //   703: iconst_1
/*     */           //   704: iastore
/*     */           //   705: iinc #7, 1
/*     */           //   708: goto -> 588
/*     */           //   711: invokestatic getManager : ()Lcom/viaversion/viaversion/api/ViaManager;
/*     */           //   714: invokeinterface getProviders : ()Lcom/viaversion/viaversion/api/platform/providers/ViaProviders;
/*     */           //   719: ldc_w com/viaversion/viaversion/protocols/protocol1_13to1_12_2/providers/BlockEntityProvider
/*     */           //   722: invokevirtual get : (Ljava/lang/Class;)Lcom/viaversion/viaversion/api/platform/providers/Provider;
/*     */           //   725: checkcast com/viaversion/viaversion/protocols/protocol1_13to1_12_2/providers/BlockEntityProvider
/*     */           //   728: astore #6
/*     */           //   730: aload #5
/*     */           //   732: invokeinterface getBlockEntities : ()Ljava/util/List;
/*     */           //   737: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */           //   742: astore #7
/*     */           //   744: aload #7
/*     */           //   746: invokeinterface hasNext : ()Z
/*     */           //   751: ifeq -> 975
/*     */           //   754: aload #7
/*     */           //   756: invokeinterface next : ()Ljava/lang/Object;
/*     */           //   761: checkcast com/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag
/*     */           //   764: astore #8
/*     */           //   766: aload #6
/*     */           //   768: aload_0
/*     */           //   769: invokeinterface user : ()Lcom/viaversion/viaversion/api/connection/UserConnection;
/*     */           //   774: aconst_null
/*     */           //   775: aload #8
/*     */           //   777: iconst_0
/*     */           //   778: invokevirtual transform : (Lcom/viaversion/viaversion/api/connection/UserConnection;Lcom/viaversion/viaversion/api/minecraft/Position;Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;Z)I
/*     */           //   781: istore #9
/*     */           //   783: iload #9
/*     */           //   785: iconst_m1
/*     */           //   786: if_icmpeq -> 915
/*     */           //   789: aload #8
/*     */           //   791: ldc_w 'x'
/*     */           //   794: invokevirtual get : (Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
/*     */           //   797: checkcast com/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag
/*     */           //   800: invokevirtual asInt : ()I
/*     */           //   803: istore #10
/*     */           //   805: aload #8
/*     */           //   807: ldc_w 'y'
/*     */           //   810: invokevirtual get : (Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
/*     */           //   813: checkcast com/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag
/*     */           //   816: invokevirtual asInt : ()I
/*     */           //   819: istore #11
/*     */           //   821: aload #8
/*     */           //   823: ldc_w 'z'
/*     */           //   826: invokevirtual get : (Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
/*     */           //   829: checkcast com/viaversion/viaversion/libs/opennbt/tag/builtin/NumberTag
/*     */           //   832: invokevirtual asInt : ()I
/*     */           //   835: istore #12
/*     */           //   837: new com/viaversion/viaversion/api/minecraft/Position
/*     */           //   840: dup
/*     */           //   841: iload #10
/*     */           //   843: iload #11
/*     */           //   845: i2s
/*     */           //   846: iload #12
/*     */           //   848: invokespecial <init> : (ISI)V
/*     */           //   851: astore #13
/*     */           //   853: aload_2
/*     */           //   854: aload #13
/*     */           //   856: invokevirtual get : (Lcom/viaversion/viaversion/api/minecraft/Position;)Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockStorage$ReplacementData;
/*     */           //   859: astore #14
/*     */           //   861: aload #14
/*     */           //   863: ifnull -> 873
/*     */           //   866: aload #14
/*     */           //   868: iload #9
/*     */           //   870: invokevirtual setReplacement : (I)V
/*     */           //   873: aload #5
/*     */           //   875: invokeinterface getSections : ()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
/*     */           //   880: iload #11
/*     */           //   882: iconst_4
/*     */           //   883: ishr
/*     */           //   884: aaload
/*     */           //   885: getstatic com/viaversion/viaversion/api/minecraft/chunks/PaletteType.BLOCKS : Lcom/viaversion/viaversion/api/minecraft/chunks/PaletteType;
/*     */           //   888: invokeinterface palette : (Lcom/viaversion/viaversion/api/minecraft/chunks/PaletteType;)Lcom/viaversion/viaversion/api/minecraft/chunks/DataPalette;
/*     */           //   893: iload #10
/*     */           //   895: bipush #15
/*     */           //   897: iand
/*     */           //   898: iload #11
/*     */           //   900: bipush #15
/*     */           //   902: iand
/*     */           //   903: iload #12
/*     */           //   905: bipush #15
/*     */           //   907: iand
/*     */           //   908: iload #9
/*     */           //   910: invokeinterface setIdAt : (IIII)V
/*     */           //   915: aload #8
/*     */           //   917: ldc_w 'id'
/*     */           //   920: invokevirtual get : (Ljava/lang/String;)Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
/*     */           //   923: astore #10
/*     */           //   925: aload #10
/*     */           //   927: instanceof com/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag
/*     */           //   930: ifeq -> 972
/*     */           //   933: aload #10
/*     */           //   935: checkcast com/viaversion/viaversion/libs/opennbt/tag/builtin/StringTag
/*     */           //   938: invokevirtual getValue : ()Ljava/lang/String;
/*     */           //   941: astore #11
/*     */           //   943: aload #11
/*     */           //   945: ldc_w 'minecraft:noteblock'
/*     */           //   948: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */           //   951: ifne -> 965
/*     */           //   954: aload #11
/*     */           //   956: ldc_w 'minecraft:flower_pot'
/*     */           //   959: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */           //   962: ifeq -> 972
/*     */           //   965: aload #7
/*     */           //   967: invokeinterface remove : ()V
/*     */           //   972: goto -> 744
/*     */           //   975: invokestatic getConfig : ()Lcom/viaversion/viaversion/api/configuration/ViaVersionConfig;
/*     */           //   978: invokeinterface isServersideBlockConnections : ()Z
/*     */           //   983: ifeq -> 1089
/*     */           //   986: aload_0
/*     */           //   987: invokeinterface user : ()Lcom/viaversion/viaversion/api/connection/UserConnection;
/*     */           //   992: aload #5
/*     */           //   994: invokestatic connectBlocks : (Lcom/viaversion/viaversion/api/connection/UserConnection;Lcom/viaversion/viaversion/api/minecraft/chunks/Chunk;)V
/*     */           //   997: aload_0
/*     */           //   998: ldc com/viaversion/viaversion/protocols/protocol1_13to1_12_2/Protocol1_13To1_12_2
/*     */           //   1000: invokeinterface send : (Ljava/lang/Class;)V
/*     */           //   1005: aload_0
/*     */           //   1006: invokeinterface cancel : ()V
/*     */           //   1011: new com/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/ConnectionData$NeighbourUpdater
/*     */           //   1014: dup
/*     */           //   1015: aload_0
/*     */           //   1016: invokeinterface user : ()Lcom/viaversion/viaversion/api/connection/UserConnection;
/*     */           //   1021: invokespecial <init> : (Lcom/viaversion/viaversion/api/connection/UserConnection;)V
/*     */           //   1024: astore #8
/*     */           //   1026: iconst_0
/*     */           //   1027: istore #9
/*     */           //   1029: iload #9
/*     */           //   1031: aload #5
/*     */           //   1033: invokeinterface getSections : ()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
/*     */           //   1038: arraylength
/*     */           //   1039: if_icmpge -> 1089
/*     */           //   1042: aload #5
/*     */           //   1044: invokeinterface getSections : ()[Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
/*     */           //   1049: iload #9
/*     */           //   1051: aaload
/*     */           //   1052: astore #10
/*     */           //   1054: aload #10
/*     */           //   1056: ifnonnull -> 1062
/*     */           //   1059: goto -> 1083
/*     */           //   1062: aload #8
/*     */           //   1064: aload #5
/*     */           //   1066: invokeinterface getX : ()I
/*     */           //   1071: aload #5
/*     */           //   1073: invokeinterface getZ : ()I
/*     */           //   1078: iload #9
/*     */           //   1080: invokevirtual updateChunkSectionNeighbours : (III)V
/*     */           //   1083: iinc #9, 1
/*     */           //   1086: goto -> 1029
/*     */           //   1089: return
/*     */           // Line number table:
/*     */           //   Java source line number -> byte code offset
/*     */           //   #329	-> 0
/*     */           //   #330	-> 17
/*     */           //   #332	-> 34
/*     */           //   #333	-> 43
/*     */           //   #334	-> 53
/*     */           //   #335	-> 65
/*     */           //   #337	-> 75
/*     */           //   #338	-> 91
/*     */           //   #339	-> 103
/*     */           //   #340	-> 111
/*     */           //   #342	-> 123
/*     */           //   #343	-> 138
/*     */           //   #344	-> 149
/*     */           //   #345	-> 156
/*     */           //   #342	-> 167
/*     */           //   #350	-> 173
/*     */           //   #351	-> 183
/*     */           //   #352	-> 186
/*     */           //   #353	-> 201
/*     */           //   #354	-> 217
/*     */           //   #355	-> 220
/*     */           //   #352	-> 223
/*     */           //   #358	-> 229
/*     */           //   #360	-> 237
/*     */           //   #361	-> 248
/*     */           //   #362	-> 259
/*     */           //   #363	-> 308
/*     */           //   #364	-> 317
/*     */           //   #365	-> 328
/*     */           //   #366	-> 338
/*     */           //   #360	-> 345
/*     */           //   #373	-> 351
/*     */           //   #374	-> 368
/*     */           //   #377	-> 371
/*     */           //   #378	-> 381
/*     */           //   #381	-> 409
/*     */           //   #382	-> 412
/*     */           //   #383	-> 427
/*     */           //   #384	-> 442
/*     */           //   #385	-> 445
/*     */           //   #382	-> 448
/*     */           //   #388	-> 454
/*     */           //   #389	-> 459
/*     */           //   #392	-> 462
/*     */           //   #393	-> 473
/*     */           //   #394	-> 484
/*     */           //   #395	-> 492
/*     */           //   #396	-> 509
/*     */           //   #397	-> 521
/*     */           //   #398	-> 538
/*     */           //   #392	-> 558
/*     */           //   #337	-> 564
/*     */           //   #405	-> 570
/*     */           //   #406	-> 580
/*     */           //   #407	-> 585
/*     */           //   #408	-> 596
/*     */           //   #409	-> 608
/*     */           //   #410	-> 621
/*     */           //   #412	-> 636
/*     */           //   #413	-> 658
/*     */           //   #415	-> 690
/*     */           //   #417	-> 694
/*     */           //   #407	-> 705
/*     */           //   #423	-> 711
/*     */           //   #424	-> 730
/*     */           //   #425	-> 744
/*     */           //   #426	-> 754
/*     */           //   #427	-> 766
/*     */           //   #428	-> 783
/*     */           //   #429	-> 789
/*     */           //   #430	-> 805
/*     */           //   #431	-> 821
/*     */           //   #433	-> 837
/*     */           //   #435	-> 853
/*     */           //   #436	-> 861
/*     */           //   #437	-> 866
/*     */           //   #440	-> 873
/*     */           //   #443	-> 915
/*     */           //   #444	-> 925
/*     */           //   #446	-> 933
/*     */           //   #447	-> 943
/*     */           //   #448	-> 965
/*     */           //   #451	-> 972
/*     */           //   #453	-> 975
/*     */           //   #454	-> 986
/*     */           //   #456	-> 997
/*     */           //   #457	-> 1005
/*     */           //   #458	-> 1011
/*     */           //   #459	-> 1026
/*     */           //   #460	-> 1042
/*     */           //   #461	-> 1054
/*     */           //   #462	-> 1059
/*     */           //   #465	-> 1062
/*     */           //   #459	-> 1083
/*     */           //   #468	-> 1089
/*     */           // Local variable table:
/*     */           //   start	length	slot	name	descriptor
/*     */           //   149	18	10	old	I
/*     */           //   156	11	11	newId	I
/*     */           //   126	47	9	p	I
/*     */           //   189	40	10	p	I
/*     */           //   186	51	9	willSave	Z
/*     */           //   259	86	10	id	I
/*     */           //   308	37	11	position	Lcom/viaversion/viaversion/api/minecraft/Position;
/*     */           //   240	111	9	idx	I
/*     */           //   415	39	10	p	I
/*     */           //   509	49	12	globalX	I
/*     */           //   521	37	13	globalY	I
/*     */           //   538	20	14	globalZ	I
/*     */           //   484	74	11	id	I
/*     */           //   465	99	10	idx	I
/*     */           //   412	152	9	willSave	Z
/*     */           //   103	461	7	section	Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
/*     */           //   123	441	8	blocks	Lcom/viaversion/viaversion/api/minecraft/chunks/DataPalette;
/*     */           //   78	492	6	s	I
/*     */           //   608	97	8	biome	I
/*     */           //   588	123	7	i	I
/*     */           //   585	126	6	latestBiomeWarn	I
/*     */           //   805	110	10	x	I
/*     */           //   821	94	11	y	I
/*     */           //   837	78	12	z	I
/*     */           //   853	62	13	position	Lcom/viaversion/viaversion/api/minecraft/Position;
/*     */           //   861	54	14	replacementData	Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockStorage$ReplacementData;
/*     */           //   943	29	11	id	Ljava/lang/String;
/*     */           //   766	206	8	tag	Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;
/*     */           //   783	189	9	newId	I
/*     */           //   925	47	10	idTag	Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/Tag;
/*     */           //   1054	29	10	section	Lcom/viaversion/viaversion/api/minecraft/chunks/ChunkSection;
/*     */           //   1029	60	9	i	I
/*     */           //   1026	63	8	updater	Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/blockconnections/ConnectionData$NeighbourUpdater;
/*     */           //   0	1090	0	wrapper	Lcom/viaversion/viaversion/api/protocol/packet/PacketWrapper;
/*     */           //   17	1073	1	clientWorld	Lcom/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/storage/ClientWorld;
/*     */           //   34	1056	2	storage	Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/storage/BlockStorage;
/*     */           //   43	1047	3	type	Lcom/viaversion/viaversion/protocols/protocol1_9_1_2to1_9_3_4/types/Chunk1_9_3_4Type;
/*     */           //   53	1037	4	type1_13	Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/types/Chunk1_13Type;
/*     */           //   65	1025	5	chunk	Lcom/viaversion/viaversion/api/minecraft/chunks/Chunk;
/*     */           //   730	360	6	provider	Lcom/viaversion/viaversion/protocols/protocol1_13to1_12_2/providers/BlockEntityProvider;
/*     */           //   744	346	7	iterator	Ljava/util/Iterator;
/*     */           // Local variable type table:
/*     */           //   start	length	slot	name	signature
/*     */           //   744	346	7	iterator	Ljava/util/Iterator<Lcom/viaversion/viaversion/libs/opennbt/tag/builtin/CompoundTag;>;
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 470 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.SPAWN_PARTICLE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 473 */             map((Type)Type.INT);
/* 474 */             map((Type)Type.BOOLEAN);
/* 475 */             map((Type)Type.FLOAT);
/* 476 */             map((Type)Type.FLOAT);
/* 477 */             map((Type)Type.FLOAT);
/* 478 */             map((Type)Type.FLOAT);
/* 479 */             map((Type)Type.FLOAT);
/* 480 */             map((Type)Type.FLOAT);
/* 481 */             map((Type)Type.FLOAT);
/* 482 */             map((Type)Type.INT);
/*     */             
/* 484 */             handler(wrapper -> {
/*     */                   int particleId = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   int dataCount = 0;
/*     */                   if (particleId == 37 || particleId == 38 || particleId == 46) {
/*     */                     dataCount = 1;
/*     */                   } else if (particleId == 36) {
/*     */                     dataCount = 2;
/*     */                   } 
/*     */                   Integer[] data = new Integer[dataCount];
/*     */                   for (int i = 0; i < data.length; i++) {
/*     */                     data[i] = (Integer)wrapper.read((Type)Type.VAR_INT);
/*     */                   }
/*     */                   Particle particle = ParticleRewriter.rewriteParticle(particleId, data);
/*     */                   if (particle == null || particle.getId() == -1) {
/*     */                     wrapper.cancel();
/*     */                     return;
/*     */                   } 
/*     */                   if (particle.getId() == 11) {
/*     */                     int count = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                     float speed = ((Float)wrapper.get((Type)Type.FLOAT, 6)).floatValue();
/*     */                     if (count == 0) {
/*     */                       wrapper.set((Type)Type.INT, 1, Integer.valueOf(1));
/*     */                       wrapper.set((Type)Type.FLOAT, 6, Float.valueOf(0.0F));
/*     */                       List<Particle.ParticleData> arguments = particle.getArguments();
/*     */                       for (int j = 0; j < 3; j++) {
/*     */                         float colorValue = ((Float)wrapper.get((Type)Type.FLOAT, j + 3)).floatValue() * speed;
/*     */                         if (colorValue == 0.0F && j == 0) {
/*     */                           colorValue = 1.0F;
/*     */                         }
/*     */                         ((Particle.ParticleData)arguments.get(j)).setValue(Float.valueOf(colorValue));
/*     */                         wrapper.set((Type)Type.FLOAT, j + 3, Float.valueOf(0.0F));
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                   wrapper.set((Type)Type.INT, 0, Integer.valueOf(particle.getId()));
/*     */                   for (Particle.ParticleData particleData : particle.getArguments()) {
/*     */                     wrapper.write(particleData.getType(), particleData.getValue());
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
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
/*     */   public static int toNewId(int oldId) {
/* 541 */     if (oldId < 0) {
/* 542 */       oldId = 0;
/*     */     }
/* 544 */     int newId = Protocol1_13To1_12_2.MAPPINGS.getBlockMappings().getNewId(oldId);
/* 545 */     if (newId != -1) {
/* 546 */       return newId;
/*     */     }
/* 548 */     newId = Protocol1_13To1_12_2.MAPPINGS.getBlockMappings().getNewId(oldId & 0xFFFFFFF0);
/* 549 */     if (newId != -1) {
/* 550 */       if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
/* 551 */         Via.getPlatform().getLogger().warning("Missing block " + oldId);
/*     */       }
/* 553 */       return newId;
/*     */     } 
/* 555 */     if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
/* 556 */       Via.getPlatform().getLogger().warning("Missing block completely " + oldId);
/*     */     }
/*     */     
/* 559 */     return 1;
/*     */   }
/*     */   
/*     */   private static int checkStorage(UserConnection user, Position position, int newId) {
/* 563 */     BlockStorage storage = (BlockStorage)user.get(BlockStorage.class);
/* 564 */     if (storage.contains(position)) {
/* 565 */       BlockStorage.ReplacementData data = storage.get(position);
/*     */       
/* 567 */       if (data.getOriginal() == newId) {
/* 568 */         if (data.getReplacement() != -1) {
/* 569 */           return data.getReplacement();
/*     */         }
/*     */       } else {
/* 572 */         storage.remove(position);
/*     */         
/* 574 */         if (storage.isWelcome(newId))
/* 575 */           storage.store(position, newId); 
/*     */       } 
/* 577 */     } else if (storage.isWelcome(newId)) {
/* 578 */       storage.store(position, newId);
/* 579 */     }  return newId;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\packets\WorldPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
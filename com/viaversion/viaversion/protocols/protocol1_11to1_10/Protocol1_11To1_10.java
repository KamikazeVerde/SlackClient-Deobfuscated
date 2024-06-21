/*     */ package com.viaversion.viaversion.protocols.protocol1_11to1_10;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_11Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_9;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_11to1_10.data.PotionColorMapping;
/*     */ import com.viaversion.viaversion.protocols.protocol1_11to1_10.metadata.MetadataRewriter1_11To1_10;
/*     */ import com.viaversion.viaversion.protocols.protocol1_11to1_10.packets.InventoryPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_11to1_10.storage.EntityTracker1_11;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.rewriter.SoundRewriter;
/*     */ import com.viaversion.viaversion.util.Pair;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Protocol1_11To1_10
/*     */   extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3>
/*     */ {
/*  44 */   private static final ValueTransformer<Float, Short> toOldByte = new ValueTransformer<Float, Short>((Type)Type.UNSIGNED_BYTE)
/*     */     {
/*     */       public Short transform(PacketWrapper wrapper, Float inputValue) throws Exception {
/*  47 */         return Short.valueOf((short)(int)(inputValue.floatValue() * 16.0F));
/*     */       }
/*     */     };
/*     */   
/*  51 */   private final MetadataRewriter1_11To1_10 entityRewriter = new MetadataRewriter1_11To1_10(this);
/*  52 */   private final InventoryPackets itemRewriter = new InventoryPackets(this);
/*     */   
/*     */   public Protocol1_11To1_10() {
/*  55 */     super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  60 */     this.entityRewriter.register();
/*  61 */     this.itemRewriter.register();
/*     */     
/*  63 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.SPAWN_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  66 */             map((Type)Type.VAR_INT);
/*  67 */             map(Type.UUID);
/*  68 */             map((Type)Type.BYTE);
/*     */ 
/*     */             
/*  71 */             handler(Protocol1_11To1_10.this.entityRewriter.objectTrackerHandler());
/*     */           }
/*     */         });
/*     */     
/*  75 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  78 */             map((Type)Type.VAR_INT);
/*  79 */             map(Type.UUID);
/*  80 */             map((Type)Type.UNSIGNED_BYTE, (Type)Type.VAR_INT);
/*  81 */             map((Type)Type.DOUBLE);
/*  82 */             map((Type)Type.DOUBLE);
/*  83 */             map((Type)Type.DOUBLE);
/*  84 */             map((Type)Type.BYTE);
/*  85 */             map((Type)Type.BYTE);
/*  86 */             map((Type)Type.BYTE);
/*  87 */             map((Type)Type.SHORT);
/*  88 */             map((Type)Type.SHORT);
/*  89 */             map((Type)Type.SHORT);
/*  90 */             map(Types1_9.METADATA_LIST);
/*     */             
/*  92 */             handler(wrapper -> {
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   int type = ((Integer)wrapper.get((Type)Type.VAR_INT, 1)).intValue();
/*     */                   
/*     */                   Entity1_11Types.EntityType entType = MetadataRewriter1_11To1_10.rewriteEntityType(type, (List)wrapper.get(Types1_9.METADATA_LIST, 0));
/*     */                   
/*     */                   if (entType != null) {
/*     */                     wrapper.set((Type)Type.VAR_INT, 1, Integer.valueOf(entType.getId()));
/*     */                     
/*     */                     wrapper.user().getEntityTracker(Protocol1_11To1_10.class).addEntity(entityId, (EntityType)entType);
/*     */                     
/*     */                     Protocol1_11To1_10.this.entityRewriter.handleMetadata(entityId, (List)wrapper.get(Types1_9.METADATA_LIST, 0), wrapper.user());
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 109 */     (new SoundRewriter((Protocol)this, this::getNewSoundId)).registerSound((ClientboundPacketType)ClientboundPackets1_9_3.SOUND);
/*     */     
/* 111 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.COLLECT_ITEM, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 114 */             map((Type)Type.VAR_INT);
/* 115 */             map((Type)Type.VAR_INT);
/*     */             
/* 117 */             handler(wrapper -> wrapper.write((Type)Type.VAR_INT, Integer.valueOf(1)));
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 123 */     this.entityRewriter.registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
/*     */     
/* 125 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.ENTITY_TELEPORT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 128 */             map((Type)Type.VAR_INT);
/* 129 */             map((Type)Type.DOUBLE);
/* 130 */             map((Type)Type.DOUBLE);
/* 131 */             map((Type)Type.DOUBLE);
/* 132 */             map((Type)Type.BYTE);
/* 133 */             map((Type)Type.BYTE);
/* 134 */             map((Type)Type.BOOLEAN);
/*     */             
/* 136 */             handler(wrapper -> {
/*     */                   int entityID = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   if (Via.getConfig().isHologramPatch()) {
/*     */                     EntityTracker1_11 tracker = (EntityTracker1_11)wrapper.user().getEntityTracker(Protocol1_11To1_10.class);
/*     */                     if (tracker.isHologram(entityID)) {
/*     */                       Double newValue = (Double)wrapper.get((Type)Type.DOUBLE, 1);
/*     */                       newValue = Double.valueOf(newValue.doubleValue() - Via.getConfig().getHologramYOffset());
/*     */                       wrapper.set((Type)Type.DOUBLE, 1, newValue);
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 150 */     this.entityRewriter.registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_9_3.DESTROY_ENTITIES);
/*     */     
/* 152 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.TITLE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 155 */             map((Type)Type.VAR_INT);
/*     */             
/* 157 */             handler(wrapper -> {
/*     */                   int action = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */ 
/*     */                   
/*     */                   if (action >= 2) {
/*     */                     wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(action + 1));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 168 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.BLOCK_ACTION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 171 */             map(Type.POSITION);
/* 172 */             map((Type)Type.UNSIGNED_BYTE);
/* 173 */             map((Type)Type.UNSIGNED_BYTE);
/* 174 */             map((Type)Type.VAR_INT);
/*     */ 
/*     */             
/* 177 */             handler(actionWrapper -> {
/*     */                   if (Via.getConfig().isPistonAnimationPatch()) {
/*     */                     int id = ((Integer)actionWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                     
/*     */                     if (id == 33 || id == 29) {
/*     */                       actionWrapper.cancel();
/*     */                     }
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 188 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 191 */             map(Type.POSITION);
/* 192 */             map((Type)Type.UNSIGNED_BYTE);
/* 193 */             map(Type.NBT);
/*     */             
/* 195 */             handler(wrapper -> {
/*     */                   CompoundTag tag = (CompoundTag)wrapper.get(Type.NBT, 0);
/*     */                   
/*     */                   if (((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue() == 1) {
/*     */                     EntityIdRewriter.toClientSpawner(tag);
/*     */                   }
/*     */                   
/*     */                   if (tag.contains("id")) {
/*     */                     ((StringTag)tag.get("id")).setValue(BlockEntityRewriter.toNewIdentifier((String)tag.get("id").getValue()));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 208 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.CHUNK_DATA, wrapper -> {
/*     */           ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */           
/*     */           Chunk chunk = (Chunk)wrapper.passthrough((Type)new Chunk1_9_3_4Type(clientWorld));
/*     */           
/*     */           if (chunk.getBlockEntities() == null) {
/*     */             return;
/*     */           }
/*     */           
/*     */           for (CompoundTag tag : chunk.getBlockEntities()) {
/*     */             if (tag.contains("id")) {
/*     */               String identifier = ((StringTag)tag.get("id")).getValue();
/*     */               if (identifier.equals("MobSpawner")) {
/*     */                 EntityIdRewriter.toClientSpawner(tag);
/*     */               }
/*     */               ((StringTag)tag.get("id")).setValue(BlockEntityRewriter.toNewIdentifier(identifier));
/*     */             } 
/*     */           } 
/*     */         });
/* 227 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 230 */             map((Type)Type.INT);
/* 231 */             map((Type)Type.UNSIGNED_BYTE);
/* 232 */             map((Type)Type.INT);
/* 233 */             handler(wrapper -> {
/*     */                   ClientWorld clientChunks = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   int dimensionId = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   clientChunks.setEnvironment(dimensionId);
/*     */                 });
/*     */           }
/*     */         });
/* 240 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 243 */             map((Type)Type.INT);
/* 244 */             handler(wrapper -> {
/*     */                   ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   
/*     */                   int dimensionId = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   clientWorld.setEnvironment(dimensionId);
/*     */                 });
/*     */           }
/*     */         });
/* 252 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 255 */             map((Type)Type.INT);
/* 256 */             map(Type.POSITION);
/* 257 */             map((Type)Type.INT);
/* 258 */             map((Type)Type.BOOLEAN);
/* 259 */             handler(packetWrapper -> {
/*     */                   int effectID = ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   if (effectID == 2002) {
/*     */                     int data = ((Integer)packetWrapper.get((Type)Type.INT, 1)).intValue();
/*     */                     
/*     */                     boolean isInstant = false;
/*     */                     
/*     */                     Pair<Integer, Boolean> newData = PotionColorMapping.getNewData(data);
/*     */                     
/*     */                     if (newData == null) {
/*     */                       Via.getPlatform().getLogger().warning("Received unknown 1.11 -> 1.10.2 potion data (" + data + ")");
/*     */                       
/*     */                       data = 0;
/*     */                     } else {
/*     */                       data = ((Integer)newData.key()).intValue();
/*     */                       isInstant = ((Boolean)newData.value()).booleanValue();
/*     */                     } 
/*     */                     if (isInstant) {
/*     */                       packetWrapper.set((Type)Type.INT, 0, Integer.valueOf(2007));
/*     */                     }
/*     */                     packetWrapper.set((Type)Type.INT, 1, Integer.valueOf(data));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 285 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_9_3.PLAYER_BLOCK_PLACEMENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 288 */             map(Type.POSITION);
/* 289 */             map((Type)Type.VAR_INT);
/* 290 */             map((Type)Type.VAR_INT);
/*     */             
/* 292 */             map((Type)Type.FLOAT, Protocol1_11To1_10.toOldByte);
/* 293 */             map((Type)Type.FLOAT, Protocol1_11To1_10.toOldByte);
/* 294 */             map((Type)Type.FLOAT, Protocol1_11To1_10.toOldByte);
/*     */           }
/*     */         });
/*     */     
/* 298 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_9_3.CHAT_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 301 */             map(Type.STRING);
/* 302 */             handler(wrapper -> {
/*     */                   String msg = (String)wrapper.get(Type.STRING, 0);
/*     */                   if (msg.length() > 100) {
/*     */                     wrapper.set(Type.STRING, 0, msg.substring(0, 100));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private int getNewSoundId(int id) {
/* 314 */     if (id == 196) {
/* 315 */       return -1;
/*     */     }
/* 317 */     if (id >= 85)
/* 318 */       id += 2; 
/* 319 */     if (id >= 176)
/* 320 */       id++; 
/* 321 */     if (id >= 197)
/* 322 */       id += 8; 
/* 323 */     if (id >= 207)
/* 324 */       id--; 
/* 325 */     if (id >= 279)
/* 326 */       id += 9; 
/* 327 */     if (id >= 296)
/* 328 */       id++; 
/* 329 */     if (id >= 390)
/* 330 */       id += 4; 
/* 331 */     if (id >= 400)
/* 332 */       id += 3; 
/* 333 */     if (id >= 450)
/* 334 */       id++; 
/* 335 */     if (id >= 455)
/* 336 */       id++; 
/* 337 */     if (id >= 470)
/* 338 */       id++; 
/* 339 */     return id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(UserConnection userConnection) {
/* 346 */     userConnection.addEntityTracker(getClass(), (EntityTracker)new EntityTracker1_11(userConnection));
/*     */     
/* 348 */     if (!userConnection.has(ClientWorld.class)) {
/* 349 */       userConnection.put((StorableObject)new ClientWorld(userConnection));
/*     */     }
/*     */   }
/*     */   
/*     */   public MetadataRewriter1_11To1_10 getEntityRewriter() {
/* 354 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryPackets getItemRewriter() {
/* 359 */     return this.itemRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_11to1_10\Protocol1_11To1_10.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
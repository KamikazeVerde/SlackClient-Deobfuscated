/*     */ package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.entities.storage.EntityPositionHandler;
/*     */ import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
/*     */ import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.EntityTypeMapping;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.PaintingMapping;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.PlayerPositionStorage1_13;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_12;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.Particle;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_12;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_13;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*     */ import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
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
/*     */ public class EntityPackets1_13
/*     */   extends LegacyEntityRewriter<ClientboundPackets1_13, Protocol1_12_2To1_13>
/*     */ {
/*     */   public EntityPackets1_13(Protocol1_12_2To1_13 protocol) {
/*  49 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  54 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.PLAYER_POSITION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  57 */             map((Type)Type.DOUBLE);
/*  58 */             map((Type)Type.DOUBLE);
/*  59 */             map((Type)Type.DOUBLE);
/*  60 */             map((Type)Type.FLOAT);
/*  61 */             map((Type)Type.FLOAT);
/*  62 */             map((Type)Type.BYTE);
/*  63 */             handler(wrapper -> {
/*     */                   if (!ViaBackwards.getConfig().isFix1_13FacePlayer()) {
/*     */                     return;
/*     */                   }
/*     */                   PlayerPositionStorage1_13 playerStorage = (PlayerPositionStorage1_13)wrapper.user().get(PlayerPositionStorage1_13.class);
/*     */                   byte bitField = ((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   playerStorage.setX(EntityPackets1_13.toSet(bitField, 0, playerStorage.getX(), ((Double)wrapper.get((Type)Type.DOUBLE, 0)).doubleValue()));
/*     */                   playerStorage.setY(EntityPackets1_13.toSet(bitField, 1, playerStorage.getY(), ((Double)wrapper.get((Type)Type.DOUBLE, 1)).doubleValue()));
/*     */                   playerStorage.setZ(EntityPackets1_13.toSet(bitField, 2, playerStorage.getZ(), ((Double)wrapper.get((Type)Type.DOUBLE, 2)).doubleValue()));
/*     */                 });
/*     */           }
/*     */         });
/*  75 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  78 */             map((Type)Type.VAR_INT);
/*  79 */             map(Type.UUID);
/*  80 */             map((Type)Type.BYTE);
/*  81 */             map((Type)Type.DOUBLE);
/*  82 */             map((Type)Type.DOUBLE);
/*  83 */             map((Type)Type.DOUBLE);
/*  84 */             map((Type)Type.BYTE);
/*  85 */             map((Type)Type.BYTE);
/*  86 */             map((Type)Type.INT);
/*     */             
/*  88 */             handler(EntityPackets1_13.this.getObjectTrackerHandler());
/*     */             
/*  90 */             handler(wrapper -> {
/*     */                   Optional<Entity1_13Types.ObjectType> optionalType = Entity1_13Types.ObjectType.findById(((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue());
/*     */                   if (!optionalType.isPresent()) {
/*     */                     return;
/*     */                   }
/*     */                   Entity1_13Types.ObjectType type = optionalType.get();
/*     */                   if (type == Entity1_13Types.ObjectType.FALLING_BLOCK) {
/*     */                     int blockState = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                     int combined = Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(blockState);
/*     */                     combined = combined >> 4 & 0xFFF | (combined & 0xF) << 12;
/*     */                     wrapper.set((Type)Type.INT, 0, Integer.valueOf(combined));
/*     */                   } else if (type == Entity1_13Types.ObjectType.ITEM_FRAME) {
/*     */                     int data = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                     switch (data) {
/*     */                       case 3:
/*     */                         data = 0;
/*     */                         break;
/*     */                       case 4:
/*     */                         data = 1;
/*     */                         break;
/*     */                       case 5:
/*     */                         data = 3;
/*     */                         break;
/*     */                     } 
/*     */                     wrapper.set((Type)Type.INT, 0, Integer.valueOf(data));
/*     */                   } else if (type == Entity1_13Types.ObjectType.TRIDENT) {
/*     */                     wrapper.set((Type)Type.BYTE, 0, Byte.valueOf((byte)Entity1_13Types.ObjectType.TIPPED_ARROW.getId()));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 121 */     registerTracker((ClientboundPacketType)ClientboundPackets1_13.SPAWN_EXPERIENCE_ORB, (EntityType)Entity1_13Types.EntityType.EXPERIENCE_ORB);
/* 122 */     registerTracker((ClientboundPacketType)ClientboundPackets1_13.SPAWN_GLOBAL_ENTITY, (EntityType)Entity1_13Types.EntityType.LIGHTNING_BOLT);
/*     */     
/* 124 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 127 */             map((Type)Type.VAR_INT);
/* 128 */             map(Type.UUID);
/* 129 */             map((Type)Type.VAR_INT);
/* 130 */             map((Type)Type.DOUBLE);
/* 131 */             map((Type)Type.DOUBLE);
/* 132 */             map((Type)Type.DOUBLE);
/* 133 */             map((Type)Type.BYTE);
/* 134 */             map((Type)Type.BYTE);
/* 135 */             map((Type)Type.BYTE);
/* 136 */             map((Type)Type.SHORT);
/* 137 */             map((Type)Type.SHORT);
/* 138 */             map((Type)Type.SHORT);
/* 139 */             map(Types1_13.METADATA_LIST, Types1_12.METADATA_LIST);
/*     */             
/* 141 */             handler(wrapper -> {
/*     */                   int type = ((Integer)wrapper.get((Type)Type.VAR_INT, 1)).intValue();
/*     */                   
/*     */                   Entity1_13Types.EntityType entityType = Entity1_13Types.getTypeFromId(type, false);
/*     */                   
/*     */                   EntityPackets1_13.this.tracker(wrapper.user()).addEntity(((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue(), (EntityType)entityType);
/*     */                   
/*     */                   int oldId = EntityTypeMapping.getOldId(type);
/*     */                   if (oldId == -1) {
/*     */                     if (!EntityPackets1_13.this.hasData((EntityType)entityType)) {
/*     */                       ViaBackwards.getPlatform().getLogger().warning("Could not find 1.12 entity type for 1.13 entity type " + type + "/" + entityType);
/*     */                     }
/*     */                   } else {
/*     */                     wrapper.set((Type)Type.VAR_INT, 1, Integer.valueOf(oldId));
/*     */                   } 
/*     */                 });
/* 157 */             handler(EntityPackets1_13.this.getMobSpawnRewriter(Types1_12.METADATA_LIST));
/*     */           }
/*     */         });
/*     */     
/* 161 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_PLAYER, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 164 */             map((Type)Type.VAR_INT);
/* 165 */             map(Type.UUID);
/* 166 */             map((Type)Type.DOUBLE);
/* 167 */             map((Type)Type.DOUBLE);
/* 168 */             map((Type)Type.DOUBLE);
/* 169 */             map((Type)Type.BYTE);
/* 170 */             map((Type)Type.BYTE);
/* 171 */             map(Types1_13.METADATA_LIST, Types1_12.METADATA_LIST);
/*     */             
/* 173 */             handler(EntityPackets1_13.this.getTrackerAndMetaHandler(Types1_12.METADATA_LIST, (EntityType)Entity1_13Types.EntityType.PLAYER));
/*     */           }
/*     */         });
/*     */     
/* 177 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_PAINTING, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 180 */             map((Type)Type.VAR_INT);
/* 181 */             map(Type.UUID);
/*     */             
/* 183 */             handler(EntityPackets1_13.this.getTrackerHandler((EntityType)Entity1_13Types.EntityType.PAINTING, (Type)Type.VAR_INT));
/* 184 */             handler(wrapper -> {
/*     */                   int motive = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   String title = PaintingMapping.getStringId(motive);
/*     */                   wrapper.write(Type.STRING, title);
/*     */                 });
/*     */           }
/*     */         });
/* 192 */     registerJoinGame((ClientboundPacketType)ClientboundPackets1_13.JOIN_GAME, (EntityType)Entity1_13Types.EntityType.PLAYER);
/*     */     
/* 194 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 197 */             map((Type)Type.INT);
/*     */             
/* 199 */             handler(EntityPackets1_13.this.getDimensionHandler(0));
/* 200 */             handler(wrapper -> ((BackwardsBlockStorage)wrapper.user().get(BackwardsBlockStorage.class)).clear());
/*     */           }
/*     */         });
/*     */     
/* 204 */     registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_13.DESTROY_ENTITIES);
/* 205 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_13.ENTITY_METADATA, Types1_13.METADATA_LIST, Types1_12.METADATA_LIST);
/*     */ 
/*     */     
/* 208 */     ((Protocol1_12_2To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.FACE_PLAYER, null, wrapper -> {
/*     */           wrapper.cancel();
/*     */           
/*     */           if (!ViaBackwards.getConfig().isFix1_13FacePlayer()) {
/*     */             return;
/*     */           }
/*     */           
/*     */           int anchor = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           double x = ((Double)wrapper.read((Type)Type.DOUBLE)).doubleValue();
/*     */           
/*     */           double y = ((Double)wrapper.read((Type)Type.DOUBLE)).doubleValue();
/*     */           
/*     */           double z = ((Double)wrapper.read((Type)Type.DOUBLE)).doubleValue();
/*     */           
/*     */           PlayerPositionStorage1_13 positionStorage = (PlayerPositionStorage1_13)wrapper.user().get(PlayerPositionStorage1_13.class);
/*     */           
/*     */           PacketWrapper positionAndLook = wrapper.create((PacketType)ClientboundPackets1_12_1.PLAYER_POSITION);
/*     */           
/*     */           positionAndLook.write((Type)Type.DOUBLE, Double.valueOf(0.0D));
/*     */           
/*     */           positionAndLook.write((Type)Type.DOUBLE, Double.valueOf(0.0D));
/*     */           
/*     */           positionAndLook.write((Type)Type.DOUBLE, Double.valueOf(0.0D));
/*     */           
/*     */           EntityPositionHandler.writeFacingDegrees(positionAndLook, positionStorage.getX(), (anchor == 1) ? (positionStorage.getY() + 1.62D) : positionStorage.getY(), positionStorage.getZ(), x, y, z);
/*     */           positionAndLook.write((Type)Type.BYTE, Byte.valueOf((byte)7));
/*     */           positionAndLook.write((Type)Type.VAR_INT, Integer.valueOf(-1));
/*     */           positionAndLook.send(Protocol1_12_2To1_13.class);
/*     */         });
/* 238 */     if (ViaBackwards.getConfig().isFix1_13FacePlayer()) {
/* 239 */       PacketHandlers movementRemapper = new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 242 */             map((Type)Type.DOUBLE);
/* 243 */             map((Type)Type.DOUBLE);
/* 244 */             map((Type)Type.DOUBLE);
/* 245 */             handler(wrapper -> ((PlayerPositionStorage1_13)wrapper.user().get(PlayerPositionStorage1_13.class)).setCoordinates(wrapper, false));
/*     */           }
/*     */         };
/* 248 */       ((Protocol1_12_2To1_13)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_12_1.PLAYER_POSITION, (PacketHandler)movementRemapper);
/* 249 */       ((Protocol1_12_2To1_13)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_12_1.PLAYER_POSITION_AND_ROTATION, (PacketHandler)movementRemapper);
/* 250 */       ((Protocol1_12_2To1_13)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_12_1.VEHICLE_MOVE, (PacketHandler)movementRemapper);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerRewrites() {
/* 257 */     mapEntityTypeWithData((EntityType)Entity1_13Types.EntityType.DROWNED, (EntityType)Entity1_13Types.EntityType.ZOMBIE_VILLAGER).plainName();
/*     */ 
/*     */     
/* 260 */     mapEntityTypeWithData((EntityType)Entity1_13Types.EntityType.COD, (EntityType)Entity1_13Types.EntityType.SQUID).plainName();
/* 261 */     mapEntityTypeWithData((EntityType)Entity1_13Types.EntityType.SALMON, (EntityType)Entity1_13Types.EntityType.SQUID).plainName();
/* 262 */     mapEntityTypeWithData((EntityType)Entity1_13Types.EntityType.PUFFERFISH, (EntityType)Entity1_13Types.EntityType.SQUID).plainName();
/* 263 */     mapEntityTypeWithData((EntityType)Entity1_13Types.EntityType.TROPICAL_FISH, (EntityType)Entity1_13Types.EntityType.SQUID).plainName();
/*     */ 
/*     */     
/* 266 */     mapEntityTypeWithData((EntityType)Entity1_13Types.EntityType.PHANTOM, (EntityType)Entity1_13Types.EntityType.PARROT).plainName().spawnMetadata(storage -> storage.add(new Metadata(15, (MetaType)MetaType1_12.VarInt, Integer.valueOf(3))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     mapEntityTypeWithData((EntityType)Entity1_13Types.EntityType.DOLPHIN, (EntityType)Entity1_13Types.EntityType.SQUID).plainName();
/*     */ 
/*     */     
/* 275 */     mapEntityTypeWithData((EntityType)Entity1_13Types.EntityType.TURTLE, (EntityType)Entity1_13Types.EntityType.OCELOT).plainName();
/*     */ 
/*     */     
/* 278 */     filter().handler((event, meta) -> {
/*     */           int typeId = meta.metaType().typeId();
/*     */           
/*     */           if (typeId == 4) {
/*     */             JsonElement element = (JsonElement)meta.value();
/*     */             
/*     */             ((Protocol1_12_2To1_13)this.protocol).translatableRewriter().processText(element);
/*     */             
/*     */             meta.setMetaType((MetaType)MetaType1_12.Chat);
/*     */           } else if (typeId == 5) {
/*     */             JsonElement element = (JsonElement)meta.value();
/*     */             
/*     */             meta.setTypeAndValue((MetaType)MetaType1_12.String, ((Protocol1_12_2To1_13)this.protocol).jsonToLegacy(element));
/*     */           } else if (typeId == 6) {
/*     */             Item item = (Item)meta.getValue();
/*     */             meta.setTypeAndValue((MetaType)MetaType1_12.Slot, ((Protocol1_12_2To1_13)this.protocol).getItemRewriter().handleItemToClient(item));
/*     */           } else if (typeId == 15) {
/*     */             event.cancel();
/*     */           } else {
/*     */             meta.setMetaType((MetaType)MetaType1_12.byId((typeId > 5) ? (typeId - 1) : typeId));
/*     */           } 
/*     */         });
/* 300 */     filter().filterFamily((EntityType)Entity1_13Types.EntityType.ZOMBIE).removeIndex(15);
/*     */ 
/*     */     
/* 303 */     filter().type((EntityType)Entity1_13Types.EntityType.TURTLE).cancel(13);
/* 304 */     filter().type((EntityType)Entity1_13Types.EntityType.TURTLE).cancel(14);
/* 305 */     filter().type((EntityType)Entity1_13Types.EntityType.TURTLE).cancel(15);
/* 306 */     filter().type((EntityType)Entity1_13Types.EntityType.TURTLE).cancel(16);
/* 307 */     filter().type((EntityType)Entity1_13Types.EntityType.TURTLE).cancel(17);
/* 308 */     filter().type((EntityType)Entity1_13Types.EntityType.TURTLE).cancel(18);
/*     */ 
/*     */     
/* 311 */     filter().filterFamily((EntityType)Entity1_13Types.EntityType.ABSTRACT_FISHES).cancel(12);
/* 312 */     filter().filterFamily((EntityType)Entity1_13Types.EntityType.ABSTRACT_FISHES).cancel(13);
/*     */ 
/*     */     
/* 315 */     filter().type((EntityType)Entity1_13Types.EntityType.PHANTOM).cancel(12);
/*     */ 
/*     */     
/* 318 */     filter().type((EntityType)Entity1_13Types.EntityType.BOAT).cancel(12);
/*     */ 
/*     */     
/* 321 */     filter().type((EntityType)Entity1_13Types.EntityType.TRIDENT).cancel(7);
/*     */ 
/*     */     
/* 324 */     filter().type((EntityType)Entity1_13Types.EntityType.WOLF).index(17).handler((event, meta) -> meta.setValue(Integer.valueOf(15 - ((Integer)meta.getValue()).intValue())));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 329 */     filter().type((EntityType)Entity1_13Types.EntityType.AREA_EFFECT_CLOUD).index(9).handler((event, meta) -> {
/*     */           Particle particle = (Particle)meta.getValue();
/*     */           ParticleMapping.ParticleData data = ParticleMapping.getMapping(particle.getId());
/*     */           int firstArg = 0;
/*     */           int secondArg = 0;
/*     */           int[] particleArgs = data.rewriteMeta((Protocol1_12_2To1_13)this.protocol, particle.getArguments());
/*     */           if (particleArgs != null && particleArgs.length != 0) {
/*     */             if (data.getHandler().isBlockHandler() && particleArgs[0] == 0) {
/*     */               particleArgs[0] = 102;
/*     */             }
/*     */             firstArg = particleArgs[0];
/*     */             secondArg = (particleArgs.length == 2) ? particleArgs[1] : 0;
/*     */           } 
/*     */           event.createExtraMeta(new Metadata(9, (MetaType)MetaType1_12.VarInt, Integer.valueOf(data.getHistoryId())));
/*     */           event.createExtraMeta(new Metadata(10, (MetaType)MetaType1_12.VarInt, Integer.valueOf(firstArg)));
/*     */           event.createExtraMeta(new Metadata(11, (MetaType)MetaType1_12.VarInt, Integer.valueOf(secondArg)));
/*     */           event.cancel();
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
/*     */   public EntityType typeFromId(int typeId) {
/* 357 */     return (EntityType)Entity1_13Types.getTypeFromId(typeId, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected EntityType getObjectTypeFromId(int typeId) {
/* 362 */     return (EntityType)Entity1_13Types.getTypeFromId(typeId, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public int newEntityId(int newId) {
/* 367 */     return EntityTypeMapping.getOldId(newId);
/*     */   }
/*     */ 
/*     */   
/*     */   private static double toSet(int field, int bitIndex, double origin, double packetValue) {
/* 372 */     return ((field & 1 << bitIndex) != 0) ? (origin + packetValue) : packetValue;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_12_2to1_13\packets\EntityPackets1_13.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
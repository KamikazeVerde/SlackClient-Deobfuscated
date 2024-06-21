/*     */ package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.Protocol1_18_2To1_19;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage.DimensionRegistryStorage;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage.StoredPainting;
/*     */ import com.viaversion.viaversion.api.data.ParticleMappings;
/*     */ import com.viaversion.viaversion.api.data.entity.StoredEntityData;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_19Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.Particle;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_18;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_19;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
/*     */ import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EntityPackets1_19
/*     */   extends EntityRewriter<ClientboundPackets1_19, Protocol1_18_2To1_19>
/*     */ {
/*     */   public EntityPackets1_19(Protocol1_18_2To1_19 protocol) {
/*  47 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  52 */     registerTracker((ClientboundPacketType)ClientboundPackets1_19.SPAWN_EXPERIENCE_ORB, (EntityType)Entity1_19Types.EXPERIENCE_ORB);
/*  53 */     registerTracker((ClientboundPacketType)ClientboundPackets1_19.SPAWN_PLAYER, (EntityType)Entity1_19Types.PLAYER);
/*  54 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_19.ENTITY_METADATA, Types1_19.METADATA_LIST, Types1_18.METADATA_LIST);
/*  55 */     registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_19.REMOVE_ENTITIES);
/*     */     
/*  57 */     ((Protocol1_18_2To1_19)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19.SPAWN_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  60 */             map((Type)Type.VAR_INT);
/*  61 */             map(Type.UUID);
/*  62 */             map((Type)Type.VAR_INT);
/*  63 */             map((Type)Type.DOUBLE);
/*  64 */             map((Type)Type.DOUBLE);
/*  65 */             map((Type)Type.DOUBLE);
/*  66 */             map((Type)Type.BYTE);
/*  67 */             map((Type)Type.BYTE);
/*  68 */             handler(wrapper -> {
/*     */                   byte headYaw = ((Byte)wrapper.read((Type)Type.BYTE)).byteValue();
/*     */                   
/*     */                   int data = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   EntityType entityType = EntityPackets1_19.this.trackAndMapEntity(wrapper);
/*     */                   
/*     */                   if (entityType.isOrHasParent((EntityType)Entity1_19Types.LIVINGENTITY)) {
/*     */                     wrapper.write((Type)Type.BYTE, Byte.valueOf(headYaw));
/*     */                     
/*     */                     byte pitch = ((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                     
/*     */                     byte yaw = ((Byte)wrapper.get((Type)Type.BYTE, 1)).byteValue();
/*     */                     wrapper.set((Type)Type.BYTE, 0, Byte.valueOf(yaw));
/*     */                     wrapper.set((Type)Type.BYTE, 1, Byte.valueOf(pitch));
/*     */                     wrapper.setPacketType((PacketType)ClientboundPackets1_18.SPAWN_MOB);
/*     */                     return;
/*     */                   } 
/*     */                   if (entityType == Entity1_19Types.PAINTING) {
/*     */                     wrapper.cancel();
/*     */                     int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                     StoredEntityData entityData = EntityPackets1_19.this.tracker(wrapper.user()).entityData(entityId);
/*     */                     Position position = new Position(((Double)wrapper.get((Type)Type.DOUBLE, 0)).intValue(), ((Double)wrapper.get((Type)Type.DOUBLE, 1)).intValue(), ((Double)wrapper.get((Type)Type.DOUBLE, 2)).intValue());
/*     */                     entityData.put(new StoredPainting(entityId, (UUID)wrapper.get(Type.UUID, 0), position, data));
/*     */                     return;
/*     */                   } 
/*     */                   if (entityType == Entity1_19Types.FALLING_BLOCK) {
/*     */                     data = ((Protocol1_18_2To1_19)EntityPackets1_19.this.protocol).getMappingData().getNewBlockStateId(data);
/*     */                   }
/*     */                   wrapper.write((Type)Type.INT, Integer.valueOf(data));
/*     */                 });
/*     */           }
/*     */         });
/* 101 */     ((Protocol1_18_2To1_19)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19.ENTITY_EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 104 */             map((Type)Type.VAR_INT);
/* 105 */             map((Type)Type.VAR_INT);
/* 106 */             map((Type)Type.BYTE);
/* 107 */             map((Type)Type.VAR_INT);
/* 108 */             map((Type)Type.BYTE);
/* 109 */             handler(wrapper -> {
/*     */                   if (((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue()) {
/*     */                     wrapper.read(Type.NBT);
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 118 */     ((Protocol1_18_2To1_19)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 121 */             map((Type)Type.INT);
/* 122 */             map((Type)Type.BOOLEAN);
/* 123 */             map((Type)Type.UNSIGNED_BYTE);
/* 124 */             map((Type)Type.BYTE);
/* 125 */             map(Type.STRING_ARRAY);
/* 126 */             map(Type.NBT);
/* 127 */             handler(wrapper -> {
/*     */                   DimensionRegistryStorage dimensionRegistryStorage = (DimensionRegistryStorage)wrapper.user().get(DimensionRegistryStorage.class);
/*     */                   
/*     */                   dimensionRegistryStorage.clear();
/*     */                   
/*     */                   String dimensionKey = (String)wrapper.read(Type.STRING);
/*     */                   
/*     */                   CompoundTag registry = (CompoundTag)wrapper.get(Type.NBT, 0);
/*     */                   
/*     */                   ListTag dimensions = (ListTag)((CompoundTag)registry.get("minecraft:dimension_type")).get("value");
/*     */                   
/*     */                   boolean found = false;
/*     */                   
/*     */                   for (Tag dimension : dimensions) {
/*     */                     CompoundTag dimensionCompound = (CompoundTag)dimension;
/*     */                     
/*     */                     StringTag nameTag = (StringTag)dimensionCompound.get("name");
/*     */                     CompoundTag dimensionData = (CompoundTag)dimensionCompound.get("element");
/*     */                     dimensionRegistryStorage.addDimension(nameTag.getValue(), dimensionData.clone());
/*     */                     if (!found && nameTag.getValue().equals(dimensionKey)) {
/*     */                       wrapper.write(Type.NBT, dimensionData);
/*     */                       found = true;
/*     */                     } 
/*     */                   } 
/*     */                   if (!found) {
/*     */                     throw new IllegalStateException("Could not find dimension " + dimensionKey + " in dimension registry");
/*     */                   }
/*     */                   CompoundTag biomeRegistry = (CompoundTag)registry.get("minecraft:worldgen/biome");
/*     */                   ListTag biomes = (ListTag)biomeRegistry.get("value");
/*     */                   for (Tag biome : biomes.getValue()) {
/*     */                     CompoundTag biomeCompound = (CompoundTag)((CompoundTag)biome).get("element");
/*     */                     biomeCompound.put("category", (Tag)new StringTag("none"));
/*     */                   } 
/*     */                   EntityPackets1_19.this.tracker(wrapper.user()).setBiomesSent(biomes.size());
/*     */                   ListTag chatTypes = (ListTag)((CompoundTag)registry.remove("minecraft:chat_type")).get("value");
/*     */                   for (Tag chatType : chatTypes) {
/*     */                     CompoundTag chatTypeCompound = (CompoundTag)chatType;
/*     */                     NumberTag idTag = (NumberTag)chatTypeCompound.get("id");
/*     */                     dimensionRegistryStorage.addChatType(idTag.asInt(), chatTypeCompound);
/*     */                   } 
/*     */                 });
/* 168 */             map(Type.STRING);
/* 169 */             map((Type)Type.LONG);
/* 170 */             map((Type)Type.VAR_INT);
/* 171 */             map((Type)Type.VAR_INT);
/* 172 */             map((Type)Type.VAR_INT);
/* 173 */             map((Type)Type.BOOLEAN);
/* 174 */             map((Type)Type.BOOLEAN);
/* 175 */             map((Type)Type.BOOLEAN);
/* 176 */             map((Type)Type.BOOLEAN);
/* 177 */             read(Type.OPTIONAL_GLOBAL_POSITION);
/* 178 */             handler(EntityPackets1_19.this.worldDataTrackerHandler(1));
/* 179 */             handler(EntityPackets1_19.this.playerTrackerHandler());
/*     */           }
/*     */         });
/*     */     
/* 183 */     ((Protocol1_18_2To1_19)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 186 */             handler(wrapper -> {
/*     */                   String dimensionKey = (String)wrapper.read(Type.STRING);
/*     */                   
/*     */                   CompoundTag dimension = ((DimensionRegistryStorage)wrapper.user().get(DimensionRegistryStorage.class)).dimension(dimensionKey);
/*     */                   if (dimension == null) {
/*     */                     throw new IllegalArgumentException("Could not find dimension " + dimensionKey + " in dimension registry");
/*     */                   }
/*     */                   wrapper.write(Type.NBT, dimension);
/*     */                 });
/* 195 */             map(Type.STRING);
/* 196 */             map((Type)Type.LONG);
/* 197 */             map((Type)Type.UNSIGNED_BYTE);
/* 198 */             map((Type)Type.BYTE);
/* 199 */             map((Type)Type.BOOLEAN);
/* 200 */             map((Type)Type.BOOLEAN);
/* 201 */             map((Type)Type.BOOLEAN);
/* 202 */             read(Type.OPTIONAL_GLOBAL_POSITION);
/* 203 */             handler(EntityPackets1_19.this.worldDataTrackerHandler(0));
/*     */           }
/*     */         });
/*     */     
/* 207 */     ((Protocol1_18_2To1_19)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19.PLAYER_INFO, wrapper -> {
/*     */           int action = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           int entries = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           for (int i = 0; i < entries; i++) {
/*     */             wrapper.passthrough(Type.UUID);
/*     */             if (action == 0) {
/*     */               wrapper.passthrough(Type.STRING);
/*     */               int properties = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */               for (int j = 0; j < properties; j++) {
/*     */                 wrapper.passthrough(Type.STRING);
/*     */                 wrapper.passthrough(Type.STRING);
/*     */                 wrapper.passthrough(Type.OPTIONAL_STRING);
/*     */               } 
/*     */               wrapper.passthrough((Type)Type.VAR_INT);
/*     */               wrapper.passthrough((Type)Type.VAR_INT);
/*     */               wrapper.passthrough(Type.OPTIONAL_COMPONENT);
/*     */               wrapper.read(Type.OPTIONAL_PROFILE_KEY);
/*     */             } else if (action == 1 || action == 2) {
/*     */               wrapper.passthrough((Type)Type.VAR_INT);
/*     */             } else if (action == 3) {
/*     */               wrapper.passthrough(Type.OPTIONAL_COMPONENT);
/*     */             } 
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerRewrites() {
/* 239 */     filter().handler((event, meta) -> {
/*     */           if (meta.metaType().typeId() <= Types1_18.META_TYPES.poseType.typeId()) {
/*     */             meta.setMetaType(Types1_18.META_TYPES.byId(meta.metaType().typeId()));
/*     */           }
/*     */           
/*     */           MetaType type = meta.metaType();
/*     */           
/*     */           if (type == Types1_18.META_TYPES.particleType) {
/*     */             Particle particle = (Particle)meta.getValue();
/*     */             
/*     */             ParticleMappings particleMappings = ((Protocol1_18_2To1_19)this.protocol).getMappingData().getParticleMappings();
/*     */             
/*     */             if (particle.getId() == particleMappings.id("sculk_charge")) {
/*     */               event.cancel();
/*     */               
/*     */               return;
/*     */             } 
/*     */             if (particle.getId() == particleMappings.id("shriek")) {
/*     */               event.cancel();
/*     */               return;
/*     */             } 
/*     */             if (particle.getId() == particleMappings.id("vibration")) {
/*     */               event.cancel();
/*     */               return;
/*     */             } 
/*     */             rewriteParticle(particle);
/*     */           } else if (type == Types1_18.META_TYPES.poseType) {
/*     */             int pose = ((Integer)meta.value()).intValue();
/*     */             if (pose >= 8) {
/*     */               meta.setValue(Integer.valueOf(0));
/*     */             }
/*     */           } 
/*     */         });
/* 272 */     registerMetaTypeHandler(Types1_18.META_TYPES.itemType, Types1_18.META_TYPES.blockStateType, null, null, Types1_18.META_TYPES.componentType, Types1_18.META_TYPES.optionalComponentType);
/*     */ 
/*     */     
/* 275 */     filter().filterFamily((EntityType)Entity1_19Types.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
/*     */           int data = ((Integer)meta.getValue()).intValue();
/*     */           
/*     */           meta.setValue(Integer.valueOf(((Protocol1_18_2To1_19)this.protocol).getMappingData().getNewBlockStateId(data)));
/*     */         });
/* 280 */     filter().type((EntityType)Entity1_19Types.PAINTING).index(8).handler((event, meta) -> {
/*     */           event.cancel();
/*     */           
/*     */           StoredEntityData entityData = tracker(event.user()).entityDataIfPresent(event.entityId());
/*     */           
/*     */           StoredPainting storedPainting = (StoredPainting)entityData.remove(StoredPainting.class);
/*     */           if (storedPainting != null) {
/*     */             PacketWrapper packet = PacketWrapper.create((PacketType)ClientboundPackets1_18.SPAWN_PAINTING, event.user());
/*     */             packet.write((Type)Type.VAR_INT, Integer.valueOf(storedPainting.entityId()));
/*     */             packet.write(Type.UUID, storedPainting.uuid());
/*     */             packet.write((Type)Type.VAR_INT, meta.value());
/*     */             packet.write(Type.POSITION1_14, storedPainting.position());
/*     */             packet.write((Type)Type.BYTE, Byte.valueOf(storedPainting.direction()));
/*     */             try {
/*     */               packet.send(Protocol1_18_2To1_19.class);
/* 295 */             } catch (Exception e) {
/*     */               throw new RuntimeException(e);
/*     */             } 
/*     */           } 
/*     */         });
/*     */     
/* 301 */     filter().type((EntityType)Entity1_19Types.CAT).index(19).handler((event, meta) -> meta.setMetaType(Types1_18.META_TYPES.varIntType));
/*     */     
/* 303 */     filter().type((EntityType)Entity1_19Types.FROG).cancel(16);
/* 304 */     filter().type((EntityType)Entity1_19Types.FROG).cancel(17);
/* 305 */     filter().type((EntityType)Entity1_19Types.FROG).cancel(18);
/*     */     
/* 307 */     filter().type((EntityType)Entity1_19Types.WARDEN).cancel(16);
/*     */     
/* 309 */     filter().type((EntityType)Entity1_19Types.GOAT).cancel(18);
/* 310 */     filter().type((EntityType)Entity1_19Types.GOAT).cancel(19);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onMappingDataLoaded() {
/* 315 */     mapTypes();
/* 316 */     mapEntityTypeWithData((EntityType)Entity1_19Types.FROG, (EntityType)Entity1_19Types.RABBIT).jsonName();
/* 317 */     mapEntityTypeWithData((EntityType)Entity1_19Types.TADPOLE, (EntityType)Entity1_19Types.PUFFERFISH).jsonName();
/* 318 */     mapEntityTypeWithData((EntityType)Entity1_19Types.CHEST_BOAT, (EntityType)Entity1_19Types.BOAT);
/* 319 */     mapEntityTypeWithData((EntityType)Entity1_19Types.WARDEN, (EntityType)Entity1_19Types.IRON_GOLEM).jsonName();
/* 320 */     mapEntityTypeWithData((EntityType)Entity1_19Types.ALLAY, (EntityType)Entity1_19Types.VEX).jsonName();
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int typeId) {
/* 325 */     return Entity1_19Types.getTypeFromId(typeId);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_18_2to1_19\packets\EntityPackets1_19.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
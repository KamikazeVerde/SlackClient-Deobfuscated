/*     */ package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_4Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_19_3;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.Protocol1_19_4To1_19_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.storage.PlayerVehicleTracker;
/*     */ import com.viaversion.viaversion.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EntityPackets
/*     */   extends EntityRewriter<ClientboundPackets1_19_3, Protocol1_19_4To1_19_3>
/*     */ {
/*     */   public EntityPackets(Protocol1_19_4To1_19_3 protocol) {
/*  41 */     super((Protocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerPackets() {
/*  46 */     ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  49 */             map((Type)Type.INT);
/*  50 */             map((Type)Type.BOOLEAN);
/*  51 */             map((Type)Type.UNSIGNED_BYTE);
/*  52 */             map((Type)Type.BYTE);
/*  53 */             map(Type.STRING_ARRAY);
/*  54 */             map(Type.NBT);
/*  55 */             map(Type.STRING);
/*  56 */             map(Type.STRING);
/*  57 */             handler(EntityPackets.this.dimensionDataHandler());
/*  58 */             handler(EntityPackets.this.biomeSizeTracker());
/*  59 */             handler(EntityPackets.this.worldDataTrackerHandlerByKey());
/*  60 */             handler(EntityPackets.this.playerTrackerHandler());
/*  61 */             handler(wrapper -> {
/*     */                   CompoundTag registry = (CompoundTag)wrapper.get(Type.NBT, 0);
/*     */                   
/*     */                   CompoundTag damageTypeRegistry = ((Protocol1_19_4To1_19_3)EntityPackets.this.protocol).getMappingData().damageTypesRegistry();
/*     */                   
/*     */                   registry.put("minecraft:damage_type", (Tag)damageTypeRegistry);
/*     */                   CompoundTag biomeRegistry = (CompoundTag)registry.get("minecraft:worldgen/biome");
/*     */                   ListTag biomes = (ListTag)biomeRegistry.get("value");
/*     */                   for (Tag biomeTag : biomes) {
/*     */                     CompoundTag biomeData = (CompoundTag)((CompoundTag)biomeTag).get("element");
/*     */                     StringTag precipitation = (StringTag)biomeData.get("precipitation");
/*     */                     byte precipitationByte = precipitation.getValue().equals("none") ? 0 : 1;
/*     */                     biomeData.put("has_precipitation", (Tag)new ByteTag(precipitationByte));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  78 */     ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.PLAYER_POSITION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           protected void register() {
/*  81 */             map((Type)Type.DOUBLE);
/*  82 */             map((Type)Type.DOUBLE);
/*  83 */             map((Type)Type.DOUBLE);
/*  84 */             map((Type)Type.FLOAT);
/*  85 */             map((Type)Type.FLOAT);
/*  86 */             map((Type)Type.BYTE);
/*  87 */             map((Type)Type.VAR_INT);
/*  88 */             handler(wrapper -> {
/*     */                   if (((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue()) {
/*     */                     PlayerVehicleTracker playerVehicleTracker = (PlayerVehicleTracker)wrapper.user().get(PlayerVehicleTracker.class);
/*     */                     
/*     */                     if (playerVehicleTracker.getVehicleId() != -1) {
/*     */                       PacketWrapper bundleStart = wrapper.create((PacketType)ClientboundPackets1_19_4.BUNDLE);
/*     */                       
/*     */                       bundleStart.send(Protocol1_19_4To1_19_3.class);
/*     */                       PacketWrapper setPassengers = wrapper.create((PacketType)ClientboundPackets1_19_4.SET_PASSENGERS);
/*     */                       setPassengers.write((Type)Type.VAR_INT, Integer.valueOf(playerVehicleTracker.getVehicleId()));
/*     */                       setPassengers.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[0]);
/*     */                       setPassengers.send(Protocol1_19_4To1_19_3.class);
/*     */                       wrapper.send(Protocol1_19_4To1_19_3.class);
/*     */                       wrapper.cancel();
/*     */                       PacketWrapper bundleEnd = wrapper.create((PacketType)ClientboundPackets1_19_4.BUNDLE);
/*     */                       bundleEnd.send(Protocol1_19_4To1_19_3.class);
/*     */                       playerVehicleTracker.setVehicleId(-1);
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 110 */     ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.SET_PASSENGERS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           protected void register() {
/* 113 */             map((Type)Type.VAR_INT);
/* 114 */             map(Type.VAR_INT_ARRAY_PRIMITIVE);
/* 115 */             handler(wrapper -> {
/*     */                   PlayerVehicleTracker playerVehicleTracker = (PlayerVehicleTracker)wrapper.user().get(PlayerVehicleTracker.class);
/*     */                   
/*     */                   int clientEntityId = wrapper.user().getEntityTracker(Protocol1_19_4To1_19_3.class).clientEntityId();
/*     */                   
/*     */                   int vehicleId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   if (playerVehicleTracker.getVehicleId() == vehicleId) {
/*     */                     playerVehicleTracker.setVehicleId(-1);
/*     */                   }
/*     */                   int[] passengerIds = (int[])wrapper.get(Type.VAR_INT_ARRAY_PRIMITIVE, 0);
/*     */                   for (int passengerId : passengerIds) {
/*     */                     if (passengerId == clientEntityId) {
/*     */                       playerVehicleTracker.setVehicleId(vehicleId);
/*     */                       break;
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 135 */     ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.ENTITY_TELEPORT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           protected void register() {
/* 138 */             handler(wrapper -> {
/*     */                   int entityId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   int clientEntityId = wrapper.user().getEntityTracker(Protocol1_19_4To1_19_3.class).clientEntityId();
/*     */                   
/*     */                   if (entityId != clientEntityId) {
/*     */                     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/*     */                     return;
/*     */                   } 
/*     */                   wrapper.setPacketType((PacketType)ClientboundPackets1_19_4.PLAYER_POSITION);
/*     */                   wrapper.passthrough((Type)Type.DOUBLE);
/*     */                   wrapper.passthrough((Type)Type.DOUBLE);
/*     */                   wrapper.passthrough((Type)Type.DOUBLE);
/*     */                   wrapper.write((Type)Type.FLOAT, Float.valueOf(((Byte)wrapper.read((Type)Type.BYTE)).byteValue() * 360.0F / 256.0F));
/*     */                   wrapper.write((Type)Type.FLOAT, Float.valueOf(((Byte)wrapper.read((Type)Type.BYTE)).byteValue() * 360.0F / 256.0F));
/*     */                   wrapper.read((Type)Type.BOOLEAN);
/*     */                   wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(-1));
/*     */                 });
/*     */           }
/*     */         });
/* 159 */     ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.ENTITY_ANIMATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 162 */             map((Type)Type.VAR_INT);
/* 163 */             handler(wrapper -> {
/*     */                   short action = ((Short)wrapper.read((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                   
/*     */                   if (action != 1) {
/*     */                     wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf(action));
/*     */                     
/*     */                     return;
/*     */                   } 
/*     */                   wrapper.setPacketType((PacketType)ClientboundPackets1_19_4.HIT_ANIMATION);
/*     */                   wrapper.write((Type)Type.FLOAT, Float.valueOf(0.0F));
/*     */                 });
/*     */           }
/*     */         });
/* 176 */     ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 179 */             map(Type.STRING);
/* 180 */             map(Type.STRING);
/* 181 */             handler(EntityPackets.this.worldDataTrackerHandlerByKey());
/* 182 */             handler(wrapper -> wrapper.user().put((StorableObject)new PlayerVehicleTracker(wrapper.user())));
/*     */           }
/*     */         });
/*     */     
/* 186 */     ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.ENTITY_STATUS, wrapper -> {
/*     */           int entityId = ((Integer)wrapper.read((Type)Type.INT)).intValue();
/*     */           
/*     */           byte event = ((Byte)wrapper.read((Type)Type.BYTE)).byteValue();
/*     */           
/*     */           int damageType = damageTypeFromEntityEvent(event);
/*     */           
/*     */           if (damageType != -1) {
/*     */             wrapper.setPacketType((PacketType)ClientboundPackets1_19_4.DAMAGE_EVENT);
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(damageType));
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */             wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */             return;
/*     */           } 
/*     */           wrapper.write((Type)Type.INT, Integer.valueOf(entityId));
/*     */           wrapper.write((Type)Type.BYTE, Byte.valueOf(event));
/*     */         });
/* 205 */     registerTrackerWithData1_19((ClientboundPacketType)ClientboundPackets1_19_3.SPAWN_ENTITY, (EntityType)Entity1_19_4Types.FALLING_BLOCK);
/* 206 */     registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_19_3.REMOVE_ENTITIES);
/* 207 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_19_3.ENTITY_METADATA, Types1_19_3.METADATA_LIST, Types1_19_4.METADATA_LIST);
/*     */   }
/*     */   
/*     */   private int damageTypeFromEntityEvent(byte entityEvent) {
/* 211 */     switch (entityEvent) {
/*     */       case 33:
/* 213 */         return 36;
/*     */       case 36:
/* 215 */         return 5;
/*     */       case 37:
/* 217 */         return 27;
/*     */       case 57:
/* 219 */         return 15;
/*     */       case 2:
/*     */       case 44:
/* 222 */         return 16;
/*     */     } 
/* 224 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerRewrites() {
/* 229 */     filter().handler((event, meta) -> {
/*     */           int id = meta.metaType().typeId();
/*     */           if (id >= 14) {
/*     */             id++;
/*     */           }
/*     */           meta.setMetaType(Types1_19_4.META_TYPES.byId(id));
/*     */         });
/* 236 */     registerMetaTypeHandler(Types1_19_4.META_TYPES.itemType, Types1_19_4.META_TYPES.blockStateType, Types1_19_4.META_TYPES.optionalBlockStateType, Types1_19_4.META_TYPES.particleType);
/*     */     
/* 238 */     filter().filterFamily((EntityType)Entity1_19_4Types.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
/*     */           int blockState = ((Integer)meta.value()).intValue();
/*     */           
/*     */           meta.setValue(Integer.valueOf(((Protocol1_19_4To1_19_3)this.protocol).getMappingData().getNewBlockStateId(blockState)));
/*     */         });
/* 243 */     filter().filterFamily((EntityType)Entity1_19_4Types.BOAT).index(11).handler((event, meta) -> {
/*     */           int boatType = ((Integer)meta.value()).intValue();
/*     */           
/*     */           if (boatType > 4) {
/*     */             meta.setValue(Integer.valueOf(boatType + 1));
/*     */           }
/*     */         });
/* 250 */     filter().filterFamily((EntityType)Entity1_19_4Types.ABSTRACT_HORSE).removeIndex(18);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onMappingDataLoaded() {
/* 255 */     mapTypes();
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int type) {
/* 260 */     return Entity1_19_4Types.getTypeFromId(type);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19_4to1_19_3\packets\EntityPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
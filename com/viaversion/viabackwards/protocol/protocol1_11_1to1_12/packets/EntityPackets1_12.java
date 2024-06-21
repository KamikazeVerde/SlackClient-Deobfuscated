/*     */ package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
/*     */ import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.ParrotStorage;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.ShoulderTracker;
/*     */ import com.viaversion.viabackwards.utils.Block;
/*     */ import com.viaversion.viaversion.api.data.entity.StoredEntityData;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_12Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.ObjectType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_12;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_12;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityPackets1_12
/*     */   extends LegacyEntityRewriter<ClientboundPackets1_12, Protocol1_11_1To1_12>
/*     */ {
/*     */   public EntityPackets1_12(Protocol1_11_1To1_12 protocol) {
/*  45 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  50 */     ((Protocol1_11_1To1_12)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_12.SPAWN_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  53 */             map((Type)Type.VAR_INT);
/*  54 */             map(Type.UUID);
/*  55 */             map((Type)Type.BYTE);
/*  56 */             map((Type)Type.DOUBLE);
/*  57 */             map((Type)Type.DOUBLE);
/*  58 */             map((Type)Type.DOUBLE);
/*  59 */             map((Type)Type.BYTE);
/*  60 */             map((Type)Type.BYTE);
/*  61 */             map((Type)Type.INT);
/*     */ 
/*     */             
/*  64 */             handler(EntityPackets1_12.this.getObjectTrackerHandler());
/*  65 */             handler(EntityPackets1_12.this.getObjectRewriter(id -> (ObjectType)Entity1_12Types.ObjectType.findById(id.byteValue()).orElse(null)));
/*     */ 
/*     */             
/*  68 */             handler(wrapper -> {
/*     */                   Optional<Entity1_12Types.ObjectType> type = Entity1_12Types.ObjectType.findById(((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue());
/*     */                   
/*     */                   if (type.isPresent() && type.get() == Entity1_12Types.ObjectType.FALLING_BLOCK) {
/*     */                     int objectData = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                     
/*     */                     int objType = objectData & 0xFFF;
/*     */                     
/*     */                     int data = objectData >> 12 & 0xF;
/*     */                     Block block = ((Protocol1_11_1To1_12)EntityPackets1_12.this.protocol).getItemRewriter().handleBlock(objType, data);
/*     */                     if (block == null) {
/*     */                       return;
/*     */                     }
/*     */                     wrapper.set((Type)Type.INT, 0, Integer.valueOf(block.getId() | block.getData() << 12));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  86 */     registerTracker((ClientboundPacketType)ClientboundPackets1_12.SPAWN_EXPERIENCE_ORB, (EntityType)Entity1_12Types.EntityType.EXPERIENCE_ORB);
/*  87 */     registerTracker((ClientboundPacketType)ClientboundPackets1_12.SPAWN_GLOBAL_ENTITY, (EntityType)Entity1_12Types.EntityType.WEATHER);
/*     */     
/*  89 */     ((Protocol1_11_1To1_12)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_12.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  92 */             map((Type)Type.VAR_INT);
/*  93 */             map(Type.UUID);
/*  94 */             map((Type)Type.VAR_INT);
/*  95 */             map((Type)Type.DOUBLE);
/*  96 */             map((Type)Type.DOUBLE);
/*  97 */             map((Type)Type.DOUBLE);
/*  98 */             map((Type)Type.BYTE);
/*  99 */             map((Type)Type.BYTE);
/* 100 */             map((Type)Type.BYTE);
/* 101 */             map((Type)Type.SHORT);
/* 102 */             map((Type)Type.SHORT);
/* 103 */             map((Type)Type.SHORT);
/* 104 */             map(Types1_12.METADATA_LIST);
/*     */ 
/*     */             
/* 107 */             handler(EntityPackets1_12.this.getTrackerHandler());
/*     */ 
/*     */             
/* 110 */             handler(EntityPackets1_12.this.getMobSpawnRewriter(Types1_12.METADATA_LIST));
/*     */           }
/*     */         });
/*     */     
/* 114 */     registerTracker((ClientboundPacketType)ClientboundPackets1_12.SPAWN_PAINTING, (EntityType)Entity1_12Types.EntityType.PAINTING);
/*     */     
/* 116 */     ((Protocol1_11_1To1_12)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_12.SPAWN_PLAYER, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 119 */             map((Type)Type.VAR_INT);
/* 120 */             map(Type.UUID);
/* 121 */             map((Type)Type.DOUBLE);
/* 122 */             map((Type)Type.DOUBLE);
/* 123 */             map((Type)Type.DOUBLE);
/* 124 */             map((Type)Type.BYTE);
/* 125 */             map((Type)Type.BYTE);
/* 126 */             map(Types1_12.METADATA_LIST);
/*     */             
/* 128 */             handler(EntityPackets1_12.this.getTrackerAndMetaHandler(Types1_12.METADATA_LIST, (EntityType)Entity1_12Types.EntityType.PLAYER));
/*     */           }
/*     */         });
/*     */     
/* 132 */     ((Protocol1_11_1To1_12)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_12.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 135 */             map((Type)Type.INT);
/* 136 */             map((Type)Type.UNSIGNED_BYTE);
/* 137 */             map((Type)Type.INT);
/*     */             
/* 139 */             handler(EntityPackets1_12.this.getTrackerHandler((EntityType)Entity1_12Types.EntityType.PLAYER, (Type)Type.INT));
/*     */             
/* 141 */             handler(EntityPackets1_12.this.getDimensionHandler(1));
/*     */             
/* 143 */             handler(wrapper -> {
/*     */                   ShoulderTracker tracker = (ShoulderTracker)wrapper.user().get(ShoulderTracker.class);
/*     */                   
/*     */                   tracker.setEntityId(((Integer)wrapper.get((Type)Type.INT, 0)).intValue());
/*     */                 });
/*     */             
/* 149 */             handler(packetWrapper -> {
/*     */                   PacketWrapper wrapper = PacketWrapper.create((PacketType)ClientboundPackets1_9_3.STATISTICS, packetWrapper.user());
/*     */                   
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(1));
/*     */                   
/*     */                   wrapper.write(Type.STRING, "achievement.openInventory");
/*     */                   
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(1));
/*     */                   wrapper.scheduleSend(Protocol1_11_1To1_12.class);
/*     */                 });
/*     */           }
/*     */         });
/* 161 */     registerRespawn((ClientboundPacketType)ClientboundPackets1_12.RESPAWN);
/* 162 */     registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_12.DESTROY_ENTITIES);
/* 163 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_12.ENTITY_METADATA, Types1_12.METADATA_LIST);
/*     */     
/* 165 */     ((Protocol1_11_1To1_12)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_12.ENTITY_PROPERTIES, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 168 */             map((Type)Type.VAR_INT);
/* 169 */             map((Type)Type.INT);
/* 170 */             handler(wrapper -> {
/*     */                   int size = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   int newSize = size;
/*     */                   for (int i = 0; i < size; i++) {
/*     */                     String key = (String)wrapper.read(Type.STRING);
/*     */                     if (key.equals("generic.flyingSpeed")) {
/*     */                       newSize--;
/*     */                       wrapper.read((Type)Type.DOUBLE);
/*     */                       int modSize = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                       for (int j = 0; j < modSize; j++) {
/*     */                         wrapper.read(Type.UUID);
/*     */                         wrapper.read((Type)Type.DOUBLE);
/*     */                         wrapper.read((Type)Type.BYTE);
/*     */                       } 
/*     */                     } else {
/*     */                       wrapper.write(Type.STRING, key);
/*     */                       wrapper.passthrough((Type)Type.DOUBLE);
/*     */                       int modSize = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */                       for (int j = 0; j < modSize; j++) {
/*     */                         wrapper.passthrough(Type.UUID);
/*     */                         wrapper.passthrough((Type)Type.DOUBLE);
/*     */                         wrapper.passthrough((Type)Type.BYTE);
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                   if (newSize != size) {
/*     */                     wrapper.set((Type)Type.INT, 0, Integer.valueOf(newSize));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerRewrites() {
/* 207 */     mapEntityTypeWithData((EntityType)Entity1_12Types.EntityType.PARROT, (EntityType)Entity1_12Types.EntityType.BAT).plainName().spawnMetadata(storage -> storage.add(new Metadata(12, (MetaType)MetaType1_12.Byte, Byte.valueOf((byte)0))));
/* 208 */     mapEntityTypeWithData((EntityType)Entity1_12Types.EntityType.ILLUSION_ILLAGER, (EntityType)Entity1_12Types.EntityType.EVOCATION_ILLAGER).plainName();
/*     */     
/* 210 */     filter().handler((event, meta) -> {
/*     */           if (meta.metaType() == MetaType1_12.Chat) {
/*     */             ChatPackets1_12.COMPONENT_REWRITER.processText((JsonElement)meta.getValue());
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 217 */     filter().filterFamily((EntityType)Entity1_12Types.EntityType.EVOCATION_ILLAGER).cancel(12);
/* 218 */     filter().filterFamily((EntityType)Entity1_12Types.EntityType.EVOCATION_ILLAGER).index(13).toIndex(12);
/*     */     
/* 220 */     filter().type((EntityType)Entity1_12Types.EntityType.ILLUSION_ILLAGER).index(0).handler((event, meta) -> {
/*     */           byte mask = ((Byte)meta.getValue()).byteValue();
/*     */ 
/*     */           
/*     */           if ((mask & 0x20) == 32) {
/*     */             mask = (byte)(mask & 0xFFFFFFDF);
/*     */           }
/*     */           
/*     */           meta.setValue(Byte.valueOf(mask));
/*     */         });
/*     */     
/* 231 */     filter().filterFamily((EntityType)Entity1_12Types.EntityType.PARROT).handler((event, meta) -> {
/*     */           StoredEntityData data = storedEntityData(event);
/*     */           
/*     */           if (!data.has(ParrotStorage.class)) {
/*     */             data.put(new ParrotStorage());
/*     */           }
/*     */         });
/* 238 */     filter().type((EntityType)Entity1_12Types.EntityType.PARROT).cancel(12);
/* 239 */     filter().type((EntityType)Entity1_12Types.EntityType.PARROT).index(13).handler((event, meta) -> {
/*     */           StoredEntityData data = storedEntityData(event);
/*     */           
/*     */           ParrotStorage storage = (ParrotStorage)data.get(ParrotStorage.class);
/*     */           
/*     */           boolean isSitting = ((((Byte)meta.getValue()).byteValue() & 0x1) == 1);
/*     */           
/*     */           boolean isTamed = ((((Byte)meta.getValue()).byteValue() & 0x4) == 4);
/*     */           
/*     */           if (storage.isTamed() || isTamed);
/*     */           
/*     */           storage.setTamed(isTamed);
/*     */           if (isSitting) {
/*     */             event.setIndex(12);
/*     */             meta.setValue(Byte.valueOf((byte)1));
/*     */             storage.setSitting(true);
/*     */           } else if (storage.isSitting()) {
/*     */             event.setIndex(12);
/*     */             meta.setValue(Byte.valueOf((byte)0));
/*     */             storage.setSitting(false);
/*     */           } else {
/*     */             event.cancel();
/*     */           } 
/*     */         });
/* 263 */     filter().type((EntityType)Entity1_12Types.EntityType.PARROT).cancel(14);
/* 264 */     filter().type((EntityType)Entity1_12Types.EntityType.PARROT).cancel(15);
/*     */ 
/*     */     
/* 267 */     filter().type((EntityType)Entity1_12Types.EntityType.PLAYER).index(15).handler((event, meta) -> {
/*     */           CompoundTag tag = (CompoundTag)meta.getValue();
/*     */           
/*     */           ShoulderTracker tracker = (ShoulderTracker)event.user().get(ShoulderTracker.class);
/*     */           
/*     */           if (tag.isEmpty() && tracker.getLeftShoulder() != null) {
/*     */             tracker.setLeftShoulder(null);
/*     */             
/*     */             tracker.update();
/*     */           } else if (tag.contains("id") && event.entityId() == tracker.getEntityId()) {
/*     */             String id = (String)tag.get("id").getValue();
/*     */             
/*     */             if (tracker.getLeftShoulder() == null || !tracker.getLeftShoulder().equals(id)) {
/*     */               tracker.setLeftShoulder(id);
/*     */               tracker.update();
/*     */             } 
/*     */           } 
/*     */           event.cancel();
/*     */         });
/* 286 */     filter().type((EntityType)Entity1_12Types.EntityType.PLAYER).index(16).handler((event, meta) -> {
/*     */           CompoundTag tag = (CompoundTag)event.meta().getValue();
/*     */           ShoulderTracker tracker = (ShoulderTracker)event.user().get(ShoulderTracker.class);
/*     */           if (tag.isEmpty() && tracker.getRightShoulder() != null) {
/*     */             tracker.setRightShoulder(null);
/*     */             tracker.update();
/*     */           } else if (tag.contains("id") && event.entityId() == tracker.getEntityId()) {
/*     */             String id = (String)tag.get("id").getValue();
/*     */             if (tracker.getRightShoulder() == null || !tracker.getRightShoulder().equals(id)) {
/*     */               tracker.setRightShoulder(id);
/*     */               tracker.update();
/*     */             } 
/*     */           } 
/*     */           event.cancel();
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int typeId) {
/* 307 */     return (EntityType)Entity1_12Types.getTypeFromId(typeId, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected EntityType getObjectTypeFromId(int typeId) {
/* 312 */     return (EntityType)Entity1_12Types.getTypeFromId(typeId, true);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_11_1to1_12\packets\EntityPackets1_12.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.entities.storage.EntityData;
/*     */ import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
/*     */ import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_10to1_11.PotionSplashHandler;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_10to1_11.Protocol1_10To1_11;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.ChestedHorseStorage;
/*     */ import com.viaversion.viabackwards.utils.Block;
/*     */ import com.viaversion.viaversion.api.data.entity.StoredEntityData;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_11Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_12Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.ObjectType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
/*     */ import java.util.List;
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
/*     */ public class EntityPackets1_11
/*     */   extends LegacyEntityRewriter<ClientboundPackets1_9_3, Protocol1_10To1_11>
/*     */ {
/*     */   public EntityPackets1_11(Protocol1_10To1_11 protocol) {
/*  44 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  49 */     ((Protocol1_10To1_11)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  52 */             map((Type)Type.INT);
/*  53 */             map(Type.POSITION);
/*  54 */             map((Type)Type.INT);
/*  55 */             handler(wrapper -> {
/*     */                   int type = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   if (type == 2002 || type == 2007) {
/*     */                     if (type == 2007) {
/*     */                       wrapper.set((Type)Type.INT, 0, Integer.valueOf(2002));
/*     */                     }
/*     */                     
/*     */                     int mappedData = PotionSplashHandler.getOldData(((Integer)wrapper.get((Type)Type.INT, 1)).intValue());
/*     */                     
/*     */                     if (mappedData != -1) {
/*     */                       wrapper.set((Type)Type.INT, 1, Integer.valueOf(mappedData));
/*     */                     }
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  72 */     ((Protocol1_10To1_11)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.SPAWN_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  75 */             map((Type)Type.VAR_INT);
/*  76 */             map(Type.UUID);
/*  77 */             map((Type)Type.BYTE);
/*  78 */             map((Type)Type.DOUBLE);
/*  79 */             map((Type)Type.DOUBLE);
/*  80 */             map((Type)Type.DOUBLE);
/*  81 */             map((Type)Type.BYTE);
/*  82 */             map((Type)Type.BYTE);
/*  83 */             map((Type)Type.INT);
/*     */ 
/*     */             
/*  86 */             handler(EntityPackets1_11.this.getObjectTrackerHandler());
/*  87 */             handler(EntityPackets1_11.this.getObjectRewriter(id -> (ObjectType)Entity1_11Types.ObjectType.findById(id.byteValue()).orElse(null)));
/*     */ 
/*     */             
/*  90 */             handler(wrapper -> {
/*     */                   Optional<Entity1_12Types.ObjectType> type = Entity1_12Types.ObjectType.findById(((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue());
/*     */                   
/*     */                   if (type.isPresent() && type.get() == Entity1_12Types.ObjectType.FALLING_BLOCK) {
/*     */                     int objectData = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                     
/*     */                     int objType = objectData & 0xFFF;
/*     */                     int data = objectData >> 12 & 0xF;
/*     */                     Block block = ((Protocol1_10To1_11)EntityPackets1_11.this.protocol).getItemRewriter().handleBlock(objType, data);
/*     */                     if (block == null) {
/*     */                       return;
/*     */                     }
/*     */                     wrapper.set((Type)Type.INT, 0, Integer.valueOf(block.getId() | block.getData() << 12));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 107 */     registerTracker((ClientboundPacketType)ClientboundPackets1_9_3.SPAWN_EXPERIENCE_ORB, (EntityType)Entity1_11Types.EntityType.EXPERIENCE_ORB);
/* 108 */     registerTracker((ClientboundPacketType)ClientboundPackets1_9_3.SPAWN_GLOBAL_ENTITY, (EntityType)Entity1_11Types.EntityType.WEATHER);
/*     */     
/* 110 */     ((Protocol1_10To1_11)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 113 */             map((Type)Type.VAR_INT);
/* 114 */             map(Type.UUID);
/* 115 */             map((Type)Type.VAR_INT, (Type)Type.UNSIGNED_BYTE);
/* 116 */             map((Type)Type.DOUBLE);
/* 117 */             map((Type)Type.DOUBLE);
/* 118 */             map((Type)Type.DOUBLE);
/* 119 */             map((Type)Type.BYTE);
/* 120 */             map((Type)Type.BYTE);
/* 121 */             map((Type)Type.BYTE);
/* 122 */             map((Type)Type.SHORT);
/* 123 */             map((Type)Type.SHORT);
/* 124 */             map((Type)Type.SHORT);
/* 125 */             map(Types1_9.METADATA_LIST);
/*     */ 
/*     */             
/* 128 */             handler(EntityPackets1_11.this.getTrackerHandler((Type)Type.UNSIGNED_BYTE, 0));
/*     */ 
/*     */             
/* 131 */             handler(wrapper -> {
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   EntityType type = EntityPackets1_11.this.tracker(wrapper.user()).entityType(entityId);
/*     */                   
/*     */                   List<Metadata> list = (List<Metadata>)wrapper.get(Types1_9.METADATA_LIST, 0);
/*     */                   
/*     */                   EntityPackets1_11.this.handleMetadata(((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue(), list, wrapper.user());
/*     */                   
/*     */                   EntityData entityData = EntityPackets1_11.this.entityDataForType(type);
/*     */                   
/*     */                   if (entityData != null) {
/*     */                     wrapper.set((Type)Type.UNSIGNED_BYTE, 0, Short.valueOf((short)entityData.replacementId()));
/*     */                     if (entityData.hasBaseMeta()) {
/*     */                       entityData.defaultMeta().createMeta(new WrappedMetadata(list));
/*     */                     }
/*     */                   } 
/*     */                   if (list.isEmpty()) {
/*     */                     list.add(new Metadata(0, (MetaType)MetaType1_9.Byte, Byte.valueOf((byte)0)));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 154 */     registerTracker((ClientboundPacketType)ClientboundPackets1_9_3.SPAWN_PAINTING, (EntityType)Entity1_11Types.EntityType.PAINTING);
/* 155 */     registerJoinGame((ClientboundPacketType)ClientboundPackets1_9_3.JOIN_GAME, (EntityType)Entity1_11Types.EntityType.PLAYER);
/* 156 */     registerRespawn((ClientboundPacketType)ClientboundPackets1_9_3.RESPAWN);
/*     */     
/* 158 */     ((Protocol1_10To1_11)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.SPAWN_PLAYER, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 161 */             map((Type)Type.VAR_INT);
/* 162 */             map(Type.UUID);
/* 163 */             map((Type)Type.DOUBLE);
/* 164 */             map((Type)Type.DOUBLE);
/* 165 */             map((Type)Type.DOUBLE);
/* 166 */             map((Type)Type.BYTE);
/* 167 */             map((Type)Type.BYTE);
/* 168 */             map(Types1_9.METADATA_LIST);
/*     */             
/* 170 */             handler(EntityPackets1_11.this.getTrackerAndMetaHandler(Types1_9.METADATA_LIST, (EntityType)Entity1_11Types.EntityType.PLAYER));
/* 171 */             handler(wrapper -> {
/*     */                   List<Metadata> metadata = (List<Metadata>)wrapper.get(Types1_9.METADATA_LIST, 0);
/*     */                   
/*     */                   if (metadata.isEmpty()) {
/*     */                     metadata.add(new Metadata(0, (MetaType)MetaType1_9.Byte, Byte.valueOf((byte)0)));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 181 */     registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_9_3.DESTROY_ENTITIES);
/* 182 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
/*     */     
/* 184 */     ((Protocol1_10To1_11)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.ENTITY_STATUS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 187 */             map((Type)Type.INT);
/* 188 */             map((Type)Type.BYTE);
/*     */             
/* 190 */             handler(wrapper -> {
/*     */                   byte b = ((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   if (b == 35) {
/*     */                     wrapper.clearPacket();
/*     */                     wrapper.setPacketType((PacketType)ClientboundPackets1_9_3.GAME_EVENT);
/*     */                     wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)10));
/*     */                     wrapper.write((Type)Type.FLOAT, Float.valueOf(0.0F));
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
/* 207 */     mapEntityTypeWithData((EntityType)Entity1_11Types.EntityType.ELDER_GUARDIAN, (EntityType)Entity1_11Types.EntityType.GUARDIAN);
/*     */     
/* 209 */     mapEntityTypeWithData((EntityType)Entity1_11Types.EntityType.WITHER_SKELETON, (EntityType)Entity1_11Types.EntityType.SKELETON).spawnMetadata(storage -> storage.add(getSkeletonTypeMeta(1)));
/* 210 */     mapEntityTypeWithData((EntityType)Entity1_11Types.EntityType.STRAY, (EntityType)Entity1_11Types.EntityType.SKELETON).plainName().spawnMetadata(storage -> storage.add(getSkeletonTypeMeta(2)));
/*     */     
/* 212 */     mapEntityTypeWithData((EntityType)Entity1_11Types.EntityType.HUSK, (EntityType)Entity1_11Types.EntityType.ZOMBIE).plainName().spawnMetadata(storage -> handleZombieType(storage, 6));
/* 213 */     mapEntityTypeWithData((EntityType)Entity1_11Types.EntityType.ZOMBIE_VILLAGER, (EntityType)Entity1_11Types.EntityType.ZOMBIE).spawnMetadata(storage -> handleZombieType(storage, 1));
/*     */     
/* 215 */     mapEntityTypeWithData((EntityType)Entity1_11Types.EntityType.HORSE, (EntityType)Entity1_11Types.EntityType.HORSE).spawnMetadata(storage -> storage.add(getHorseMetaType(0)));
/* 216 */     mapEntityTypeWithData((EntityType)Entity1_11Types.EntityType.DONKEY, (EntityType)Entity1_11Types.EntityType.HORSE).spawnMetadata(storage -> storage.add(getHorseMetaType(1)));
/* 217 */     mapEntityTypeWithData((EntityType)Entity1_11Types.EntityType.MULE, (EntityType)Entity1_11Types.EntityType.HORSE).spawnMetadata(storage -> storage.add(getHorseMetaType(2)));
/* 218 */     mapEntityTypeWithData((EntityType)Entity1_11Types.EntityType.SKELETON_HORSE, (EntityType)Entity1_11Types.EntityType.HORSE).spawnMetadata(storage -> storage.add(getHorseMetaType(4)));
/* 219 */     mapEntityTypeWithData((EntityType)Entity1_11Types.EntityType.ZOMBIE_HORSE, (EntityType)Entity1_11Types.EntityType.HORSE).spawnMetadata(storage -> storage.add(getHorseMetaType(3)));
/*     */     
/* 221 */     mapEntityTypeWithData((EntityType)Entity1_11Types.EntityType.EVOCATION_FANGS, (EntityType)Entity1_11Types.EntityType.SHULKER);
/* 222 */     mapEntityTypeWithData((EntityType)Entity1_11Types.EntityType.EVOCATION_ILLAGER, (EntityType)Entity1_11Types.EntityType.VILLAGER).plainName();
/* 223 */     mapEntityTypeWithData((EntityType)Entity1_11Types.EntityType.VEX, (EntityType)Entity1_11Types.EntityType.BAT).plainName();
/* 224 */     mapEntityTypeWithData((EntityType)Entity1_11Types.EntityType.VINDICATION_ILLAGER, (EntityType)Entity1_11Types.EntityType.VILLAGER).plainName().spawnMetadata(storage -> storage.add(new Metadata(13, (MetaType)MetaType1_9.VarInt, Integer.valueOf(4))));
/* 225 */     mapEntityTypeWithData((EntityType)Entity1_11Types.EntityType.LIAMA, (EntityType)Entity1_11Types.EntityType.HORSE).plainName().spawnMetadata(storage -> storage.add(getHorseMetaType(1)));
/* 226 */     mapEntityTypeWithData((EntityType)Entity1_11Types.EntityType.LIAMA_SPIT, (EntityType)Entity1_11Types.EntityType.SNOWBALL);
/*     */     
/* 228 */     mapObjectType((ObjectType)Entity1_11Types.ObjectType.LIAMA_SPIT, (ObjectType)Entity1_11Types.ObjectType.SNOWBALL, -1);
/*     */     
/* 230 */     mapObjectType((ObjectType)Entity1_11Types.ObjectType.EVOCATION_FANGS, (ObjectType)Entity1_11Types.ObjectType.FALLING_BLOCK, 4294);
/*     */ 
/*     */     
/* 233 */     filter().filterFamily((EntityType)Entity1_11Types.EntityType.GUARDIAN).index(12).handler((event, meta) -> {
/*     */           boolean b = ((Boolean)meta.getValue()).booleanValue();
/*     */           
/*     */           int bitmask = b ? 2 : 0;
/*     */           
/*     */           if (event.entityType() == Entity1_11Types.EntityType.ELDER_GUARDIAN) {
/*     */             bitmask |= 0x4;
/*     */           }
/*     */           
/*     */           meta.setTypeAndValue((MetaType)MetaType1_9.Byte, Byte.valueOf((byte)bitmask));
/*     */         });
/*     */     
/* 245 */     filter().filterFamily((EntityType)Entity1_11Types.EntityType.ABSTRACT_SKELETON).index(12).toIndex(13);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 250 */     filter().filterFamily((EntityType)Entity1_11Types.EntityType.ZOMBIE).handler((event, meta) -> {
/*     */           switch (meta.id()) {
/*     */             case 13:
/*     */               event.cancel();
/*     */               return;
/*     */             
/*     */             case 14:
/*     */               event.setIndex(15);
/*     */               break;
/*     */             
/*     */             case 15:
/*     */               event.setIndex(14);
/*     */               break;
/*     */             
/*     */             case 16:
/*     */               event.setIndex(13);
/*     */               meta.setValue(Integer.valueOf(1 + ((Integer)meta.getValue()).intValue()));
/*     */               break;
/*     */           } 
/*     */         });
/* 270 */     filter().type((EntityType)Entity1_11Types.EntityType.EVOCATION_ILLAGER).index(12).handler((event, meta) -> {
/*     */           event.setIndex(13);
/*     */           
/*     */           meta.setTypeAndValue((MetaType)MetaType1_9.VarInt, Integer.valueOf(((Byte)meta.getValue()).intValue()));
/*     */         });
/*     */     
/* 276 */     filter().type((EntityType)Entity1_11Types.EntityType.VEX).index(12).handler((event, meta) -> meta.setValue(Byte.valueOf((byte)0)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 281 */     filter().type((EntityType)Entity1_11Types.EntityType.VINDICATION_ILLAGER).index(12).handler((event, meta) -> {
/*     */           event.setIndex(13);
/*     */ 
/*     */ 
/*     */           
/*     */           meta.setTypeAndValue((MetaType)MetaType1_9.VarInt, Integer.valueOf((((Number)meta.getValue()).intValue() == 1) ? 2 : 4));
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 291 */     filter().filterFamily((EntityType)Entity1_11Types.EntityType.ABSTRACT_HORSE).index(13).handler((event, meta) -> {
/*     */           StoredEntityData data = storedEntityData(event);
/*     */           
/*     */           byte b = ((Byte)meta.getValue()).byteValue();
/*     */           
/*     */           if (data.has(ChestedHorseStorage.class) && ((ChestedHorseStorage)data.get(ChestedHorseStorage.class)).isChested()) {
/*     */             b = (byte)(b | 0x8);
/*     */             meta.setValue(Byte.valueOf(b));
/*     */           } 
/*     */         });
/* 301 */     filter().filterFamily((EntityType)Entity1_11Types.EntityType.CHESTED_HORSE).handler((event, meta) -> {
/*     */           StoredEntityData data = storedEntityData(event);
/*     */           
/*     */           if (!data.has(ChestedHorseStorage.class)) {
/*     */             data.put(new ChestedHorseStorage());
/*     */           }
/*     */         });
/*     */     
/* 309 */     filter().type((EntityType)Entity1_11Types.EntityType.HORSE).index(16).toIndex(17);
/*     */ 
/*     */     
/* 312 */     filter().filterFamily((EntityType)Entity1_11Types.EntityType.CHESTED_HORSE).index(15).handler((event, meta) -> {
/*     */           StoredEntityData data = storedEntityData(event);
/*     */           
/*     */           ChestedHorseStorage storage = (ChestedHorseStorage)data.get(ChestedHorseStorage.class);
/*     */           
/*     */           boolean b = ((Boolean)meta.getValue()).booleanValue();
/*     */           storage.setChested(b);
/*     */           event.cancel();
/*     */         });
/* 321 */     filter().type((EntityType)Entity1_11Types.EntityType.LIAMA).handler((event, meta) -> {
/*     */           StoredEntityData data = storedEntityData(event);
/*     */           ChestedHorseStorage storage = (ChestedHorseStorage)data.get(ChestedHorseStorage.class);
/*     */           int index = event.index();
/*     */           switch (index) {
/*     */             case 16:
/*     */               storage.setLiamaStrength(((Integer)meta.getValue()).intValue());
/*     */               event.cancel();
/*     */               break;
/*     */ 
/*     */             
/*     */             case 17:
/*     */               storage.setLiamaCarpetColor(((Integer)meta.getValue()).intValue());
/*     */               event.cancel();
/*     */               break;
/*     */             
/*     */             case 18:
/*     */               storage.setLiamaVariant(((Integer)meta.getValue()).intValue());
/*     */               event.cancel();
/*     */               break;
/*     */           } 
/*     */         
/*     */         });
/* 344 */     filter().filterFamily((EntityType)Entity1_11Types.EntityType.ABSTRACT_HORSE).index(14).toIndex(16);
/*     */ 
/*     */     
/* 347 */     filter().type((EntityType)Entity1_11Types.EntityType.VILLAGER).index(13).handler((event, meta) -> {
/*     */           if (((Integer)meta.getValue()).intValue() == 5) {
/*     */             meta.setValue(Integer.valueOf(0));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 354 */     filter().type((EntityType)Entity1_11Types.EntityType.SHULKER).cancel(15);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Metadata getSkeletonTypeMeta(int type) {
/* 364 */     return new Metadata(12, (MetaType)MetaType1_9.VarInt, Integer.valueOf(type));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Metadata getZombieTypeMeta(int type) {
/* 373 */     return new Metadata(13, (MetaType)MetaType1_9.VarInt, Integer.valueOf(type));
/*     */   }
/*     */   
/*     */   private void handleZombieType(WrappedMetadata storage, int type) {
/* 377 */     Metadata meta = storage.get(13);
/* 378 */     if (meta == null) {
/* 379 */       storage.add(getZombieTypeMeta(type));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Metadata getHorseMetaType(int type) {
/* 391 */     return new Metadata(14, (MetaType)MetaType1_9.VarInt, Integer.valueOf(type));
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int typeId) {
/* 396 */     return (EntityType)Entity1_11Types.getTypeFromId(typeId, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected EntityType getObjectTypeFromId(int typeId) {
/* 401 */     return (EntityType)Entity1_11Types.getTypeFromId(typeId, true);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_10to1_11\packets\EntityPackets1_11.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
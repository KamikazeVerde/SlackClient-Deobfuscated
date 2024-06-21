/*     */ package com.viaversion.viabackwards.api.rewriters;
/*     */ 
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.entities.storage.EntityData;
/*     */ import com.viaversion.viabackwards.api.entities.storage.EntityObjectData;
/*     */ import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.ObjectType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class LegacyEntityRewriter<C extends ClientboundPacketType, T extends BackwardsProtocol<C, ?, ?, ?>>
/*     */   extends EntityRewriterBase<C, T>
/*     */ {
/*  43 */   private final Map<ObjectType, EntityData> objectTypes = new HashMap<>();
/*     */   
/*     */   protected LegacyEntityRewriter(T protocol) {
/*  46 */     this(protocol, (MetaType)MetaType1_9.String, (MetaType)MetaType1_9.Boolean);
/*     */   }
/*     */   
/*     */   protected LegacyEntityRewriter(T protocol, MetaType displayType, MetaType displayVisibilityType) {
/*  50 */     super(protocol, displayType, 2, displayVisibilityType, 3);
/*     */   }
/*     */   
/*     */   protected EntityObjectData mapObjectType(ObjectType oldObjectType, ObjectType replacement, int data) {
/*  54 */     EntityObjectData entData = new EntityObjectData((BackwardsProtocol)this.protocol, oldObjectType.getType().name(), oldObjectType.getId(), replacement.getId(), data);
/*  55 */     this.objectTypes.put(oldObjectType, entData);
/*  56 */     return entData;
/*     */   }
/*     */   
/*     */   protected EntityData getObjectData(ObjectType type) {
/*  60 */     return this.objectTypes.get(type);
/*     */   }
/*     */   
/*     */   protected void registerRespawn(C packetType) {
/*  64 */     ((BackwardsProtocol)this.protocol).registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  67 */             map((Type)Type.INT);
/*  68 */             handler(wrapper -> {
/*     */                   ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   clientWorld.setEnvironment(((Integer)wrapper.get((Type)Type.INT, 0)).intValue());
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   protected void registerJoinGame(C packetType, final EntityType playerType) {
/*  77 */     ((BackwardsProtocol)this.protocol).registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  80 */             map((Type)Type.INT);
/*  81 */             map((Type)Type.UNSIGNED_BYTE);
/*  82 */             map((Type)Type.INT);
/*  83 */             handler(wrapper -> {
/*     */                   ClientWorld clientChunks = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   clientChunks.setEnvironment(((Integer)wrapper.get((Type)Type.INT, 1)).intValue());
/*     */                   LegacyEntityRewriter.this.addTrackedEntity(wrapper, ((Integer)wrapper.get((Type)Type.INT, 0)).intValue(), playerType);
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerMetadataRewriter(C packetType, final Type<List<Metadata>> oldMetaType, final Type<List<Metadata>> newMetaType) {
/*  94 */     ((BackwardsProtocol)this.protocol).registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  97 */             map((Type)Type.VAR_INT);
/*  98 */             if (oldMetaType != null) {
/*  99 */               map(oldMetaType, newMetaType);
/*     */             } else {
/* 101 */               map(newMetaType);
/*     */             } 
/* 103 */             handler(wrapper -> {
/*     */                   List<Metadata> metadata = (List<Metadata>)wrapper.get(newMetaType, 0);
/*     */                   LegacyEntityRewriter.this.handleMetadata(((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue(), metadata, wrapper.user());
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerMetadataRewriter(C packetType, Type<List<Metadata>> metaType) {
/* 113 */     registerMetadataRewriter(packetType, (Type<List<Metadata>>)null, metaType);
/*     */   }
/*     */   
/*     */   protected PacketHandler getMobSpawnRewriter(Type<List<Metadata>> metaType) {
/* 117 */     return wrapper -> {
/*     */         int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */         EntityType type = tracker(wrapper.user()).entityType(entityId);
/*     */         List<Metadata> metadata = (List<Metadata>)wrapper.get(metaType, 0);
/*     */         handleMetadata(entityId, metadata, wrapper.user());
/*     */         EntityData entityData = entityDataForType(type);
/*     */         if (entityData != null) {
/*     */           wrapper.set((Type)Type.VAR_INT, 1, Integer.valueOf(entityData.replacementId()));
/*     */           if (entityData.hasBaseMeta()) {
/*     */             entityData.defaultMeta().createMeta(new WrappedMetadata(metadata));
/*     */           }
/*     */         } 
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected PacketHandler getObjectTrackerHandler() {
/* 135 */     return wrapper -> addTrackedEntity(wrapper, ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue(), getObjectTypeFromId(((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue()));
/*     */   }
/*     */   
/*     */   protected PacketHandler getTrackerAndMetaHandler(Type<List<Metadata>> metaType, EntityType entityType) {
/* 139 */     return wrapper -> {
/*     */         addTrackedEntity(wrapper, ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue(), entityType);
/*     */         List<Metadata> metadata = (List<Metadata>)wrapper.get(metaType, 0);
/*     */         handleMetadata(((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue(), metadata, wrapper.user());
/*     */       };
/*     */   }
/*     */   
/*     */   protected PacketHandler getObjectRewriter(Function<Byte, ObjectType> objectGetter) {
/* 147 */     return wrapper -> {
/*     */         ObjectType type = objectGetter.apply((Byte)wrapper.get((Type)Type.BYTE, 0));
/*     */         if (type == null) {
/*     */           ViaBackwards.getPlatform().getLogger().warning("Could not find Entity Type" + wrapper.get((Type)Type.BYTE, 0));
/*     */           return;
/*     */         } 
/*     */         EntityData data = getObjectData(type);
/*     */         if (data != null) {
/*     */           wrapper.set((Type)Type.BYTE, 0, Byte.valueOf((byte)data.replacementId()));
/*     */           if (data.objectData() != -1) {
/*     */             wrapper.set((Type)Type.INT, 0, Integer.valueOf(data.objectData()));
/*     */           }
/*     */         } 
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   protected EntityType getObjectTypeFromId(int typeId) {
/* 165 */     return typeFromId(typeId);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   protected void addTrackedEntity(PacketWrapper wrapper, int entityId, EntityType type) throws Exception {
/* 170 */     tracker(wrapper.user()).addEntity(entityId, type);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\rewriters\LegacyEntityRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
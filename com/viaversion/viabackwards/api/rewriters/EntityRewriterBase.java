/*     */ package com.viaversion.viabackwards.api.rewriters;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.entities.storage.EntityData;
/*     */ import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.Int2IntMapMappings;
/*     */ import com.viaversion.viaversion.api.data.Mappings;
/*     */ import com.viaversion.viaversion.api.data.entity.StoredEntityData;
/*     */ import com.viaversion.viaversion.api.data.entity.TrackedEntity;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.Particle;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EntityRewriterBase<C extends ClientboundPacketType, T extends BackwardsProtocol<C, ?, ?, ?>>
/*     */   extends EntityRewriter<C, T>
/*     */ {
/*  51 */   private final Int2ObjectMap<EntityData> entityDataMappings = (Int2ObjectMap<EntityData>)new Int2ObjectOpenHashMap();
/*     */   
/*     */   private final MetaType displayNameMetaType;
/*     */   private final MetaType displayVisibilityMetaType;
/*     */   private final int displayNameIndex;
/*     */   private final int displayVisibilityIndex;
/*     */   
/*     */   EntityRewriterBase(T protocol, MetaType displayNameMetaType, int displayNameIndex, MetaType displayVisibilityMetaType, int displayVisibilityIndex) {
/*  59 */     super((Protocol)protocol, false);
/*  60 */     this.displayNameMetaType = displayNameMetaType;
/*  61 */     this.displayNameIndex = displayNameIndex;
/*  62 */     this.displayVisibilityMetaType = displayVisibilityMetaType;
/*  63 */     this.displayVisibilityIndex = displayVisibilityIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleMetadata(int entityId, List<Metadata> metadataList, UserConnection connection) {
/*  68 */     TrackedEntity entity = tracker(connection).entity(entityId);
/*  69 */     boolean initialMetadata = (entity == null || !entity.hasSentMetadata());
/*     */     
/*  71 */     super.handleMetadata(entityId, metadataList, connection);
/*     */     
/*  73 */     if (entity == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  78 */     EntityData entityData = entityDataForType(entity.entityType());
/*  79 */     if (entityData != null && entityData.mobName() != null) {
/*  80 */       Metadata displayName = getMeta(this.displayNameIndex, metadataList);
/*  81 */       if (initialMetadata) {
/*  82 */         if (displayName == null) {
/*     */           
/*  84 */           metadataList.add(new Metadata(this.displayNameIndex, this.displayNameMetaType, entityData.mobName()));
/*  85 */           addDisplayVisibilityMeta(metadataList);
/*  86 */         } else if (displayName.getValue() == null || displayName.getValue().toString().isEmpty()) {
/*     */           
/*  88 */           displayName.setValue(entityData.mobName());
/*  89 */           addDisplayVisibilityMeta(metadataList);
/*     */         } 
/*  91 */       } else if (displayName != null && (displayName.getValue() == null || displayName.getValue().toString().isEmpty())) {
/*     */         
/*  93 */         displayName.setValue(entityData.mobName());
/*  94 */         addDisplayVisibilityMeta(metadataList);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  99 */     if (entityData != null && entityData.hasBaseMeta() && initialMetadata) {
/* 100 */       entityData.defaultMeta().createMeta(new WrappedMetadata(metadataList));
/*     */     }
/*     */   }
/*     */   
/*     */   private void addDisplayVisibilityMeta(List<Metadata> metadataList) {
/* 105 */     if (ViaBackwards.getConfig().alwaysShowOriginalMobName()) {
/* 106 */       removeMeta(this.displayVisibilityIndex, metadataList);
/* 107 */       metadataList.add(new Metadata(this.displayVisibilityIndex, this.displayVisibilityMetaType, Boolean.valueOf(true)));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Metadata getMeta(int metaIndex, List<Metadata> metadataList) {
/* 112 */     for (Metadata metadata : metadataList) {
/* 113 */       if (metadata.id() == metaIndex) {
/* 114 */         return metadata;
/*     */       }
/*     */     } 
/* 117 */     return null;
/*     */   }
/*     */   
/*     */   protected void removeMeta(int metaIndex, List<Metadata> metadataList) {
/* 121 */     metadataList.removeIf(meta -> (meta.id() == metaIndex));
/*     */   }
/*     */   
/*     */   protected boolean hasData(EntityType type) {
/* 125 */     return this.entityDataMappings.containsKey(type.getId());
/*     */   }
/*     */   
/*     */   protected EntityData entityDataForType(EntityType type) {
/* 129 */     return (EntityData)this.entityDataMappings.get(type.getId());
/*     */   }
/*     */   
/*     */   protected StoredEntityData storedEntityData(MetaHandlerEvent event) {
/* 133 */     return tracker(event.user()).entityData(event.entityId());
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
/*     */   protected EntityData mapEntityTypeWithData(EntityType type, EntityType mappedType) {
/* 146 */     Preconditions.checkArgument((type.getClass() == mappedType.getClass()), "Both entity types need to be of the same class");
/*     */ 
/*     */     
/* 149 */     int mappedReplacementId = newEntityId(mappedType.getId());
/* 150 */     EntityData data = new EntityData((BackwardsProtocol)this.protocol, type, mappedReplacementId);
/* 151 */     mapEntityType(type.getId(), mappedReplacementId);
/* 152 */     this.entityDataMappings.put(type.getId(), data);
/* 153 */     return data;
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
/*     */   public <E extends Enum<E> & EntityType> void mapTypes(EntityType[] oldTypes, Class<E> newTypeClass) {
/* 165 */     if (this.typeMappings == null) {
/* 166 */       this.typeMappings = (Mappings)Int2IntMapMappings.of();
/*     */     }
/* 168 */     for (EntityType oldType : oldTypes) {
/*     */       try {
/* 170 */         E newType = Enum.valueOf(newTypeClass, oldType.name());
/* 171 */         this.typeMappings.setNewId(oldType.getId(), ((EntityType)newType).getId());
/* 172 */       } catch (IllegalArgumentException illegalArgumentException) {}
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
/*     */ 
/*     */   
/*     */   public void registerMetaTypeHandler(MetaType itemType, MetaType blockStateType, MetaType optionalBlockStateType, MetaType particleType, MetaType componentType, MetaType optionalComponentType) {
/* 186 */     filter().handler((event, meta) -> {
/*     */           MetaType type = meta.metaType();
/*     */           if (type == itemType) {
/*     */             ((BackwardsProtocol)this.protocol).getItemRewriter().handleItemToClient((Item)meta.value());
/*     */           } else if (type == blockStateType) {
/*     */             int data = ((Integer)meta.value()).intValue();
/*     */             meta.setValue(Integer.valueOf(((BackwardsProtocol)this.protocol).getMappingData().getNewBlockStateId(data)));
/*     */           } else if (type == optionalBlockStateType) {
/*     */             int data = ((Integer)meta.value()).intValue();
/*     */             if (data != 0) {
/*     */               meta.setValue(Integer.valueOf(((BackwardsProtocol)this.protocol).getMappingData().getNewBlockStateId(data)));
/*     */             }
/*     */           } else if (type == particleType) {
/*     */             rewriteParticle((Particle)meta.value());
/*     */           } else if (type == optionalComponentType || type == componentType) {
/*     */             JsonElement text = (JsonElement)meta.value();
/*     */             if (text != null) {
/*     */               ((BackwardsProtocol)this.protocol).getTranslatableRewriter().processText(text);
/*     */             }
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected PacketHandler getTrackerHandler(Type<? extends Number> intType, int typeIndex) {
/* 211 */     return wrapper -> {
/*     */         Number id = (Number)wrapper.get(intType, typeIndex);
/*     */         tracker(wrapper.user()).addEntity(((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue(), typeFromId(id.intValue()));
/*     */       };
/*     */   }
/*     */   
/*     */   protected PacketHandler getTrackerHandler() {
/* 218 */     return getTrackerHandler((Type<? extends Number>)Type.VAR_INT, 1);
/*     */   }
/*     */   
/*     */   protected PacketHandler getTrackerHandler(EntityType entityType, Type<? extends Number> intType) {
/* 222 */     return wrapper -> tracker(wrapper.user()).addEntity(((Integer)wrapper.get(intType, 0)).intValue(), entityType);
/*     */   }
/*     */   
/*     */   protected PacketHandler getDimensionHandler(int index) {
/* 226 */     return wrapper -> {
/*     */         ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */         int dimensionId = ((Integer)wrapper.get((Type)Type.INT, index)).intValue();
/*     */         clientWorld.setEnvironment(dimensionId);
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\rewriters\EntityRewriterBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
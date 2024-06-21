/*     */ package com.viaversion.viaversion.rewriter;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.Int2IntMapMappings;
/*     */ import com.viaversion.viaversion.api.data.Mappings;
/*     */ import com.viaversion.viaversion.api.data.ParticleMappings;
/*     */ import com.viaversion.viaversion.api.data.entity.DimensionData;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.data.entity.TrackedEntity;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.RewriterBase;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.Particle;
/*     */ import com.viaversion.viaversion.data.entity.DimensionDataImpl;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.rewriter.meta.MetaFilter;
/*     */ import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
/*     */ import com.viaversion.viaversion.rewriter.meta.MetaHandlerEventImpl;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.stream.Collectors;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EntityRewriter<C extends ClientboundPacketType, T extends Protocol<C, ?, ?, ?>>
/*     */   extends RewriterBase<T>
/*     */   implements EntityRewriter<T>
/*     */ {
/*  59 */   private static final Metadata[] EMPTY_ARRAY = new Metadata[0];
/*  60 */   protected final List<MetaFilter> metadataFilters = new ArrayList<>();
/*     */   protected final boolean trackMappedType;
/*     */   protected Mappings typeMappings;
/*     */   
/*     */   protected EntityRewriter(T protocol) {
/*  65 */     this(protocol, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EntityRewriter(T protocol, boolean trackMappedType) {
/*  75 */     super((Protocol)protocol);
/*  76 */     this.trackMappedType = trackMappedType;
/*  77 */     protocol.put(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetaFilter.Builder filter() {
/*  88 */     return new MetaFilter.Builder(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerFilter(MetaFilter filter) {
/*  99 */     Preconditions.checkArgument(!this.metadataFilters.contains(filter));
/* 100 */     this.metadataFilters.add(filter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleMetadata(int entityId, List<Metadata> metadataList, UserConnection connection) {
/* 105 */     TrackedEntity entity = tracker(connection).entity(entityId);
/* 106 */     EntityType type = (entity != null) ? entity.entityType() : null;
/* 107 */     int i = 0;
/* 108 */     for (Metadata metadata : (Metadata[])metadataList.<Metadata>toArray(EMPTY_ARRAY)) {
/*     */       
/* 110 */       if (!callOldMetaHandler(entityId, type, metadata, metadataList, connection)) {
/* 111 */         metadataList.remove(i--);
/*     */       } else {
/*     */         MetaHandlerEventImpl metaHandlerEventImpl;
/*     */         
/* 115 */         MetaHandlerEvent event = null;
/* 116 */         for (MetaFilter filter : this.metadataFilters) {
/* 117 */           if (!filter.isFiltered(type, metadata)) {
/*     */             continue;
/*     */           }
/* 120 */           if (event == null)
/*     */           {
/* 122 */             metaHandlerEventImpl = new MetaHandlerEventImpl(connection, type, entityId, metadata, metadataList);
/*     */           }
/*     */           
/*     */           try {
/* 126 */             filter.handler().handle((MetaHandlerEvent)metaHandlerEventImpl, metadata);
/* 127 */           } catch (Exception e) {
/* 128 */             logException(e, type, metadataList, metadata);
/* 129 */             metadataList.remove(i--);
/*     */             
/*     */             break;
/*     */           } 
/* 133 */           if (metaHandlerEventImpl.cancelled()) {
/*     */             
/* 135 */             metadataList.remove(i--);
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 140 */         if (metaHandlerEventImpl != null && metaHandlerEventImpl.extraMeta() != null)
/*     */         {
/* 142 */           metadataList.addAll(metaHandlerEventImpl.extraMeta());
/*     */         }
/* 144 */         i++;
/*     */       } 
/*     */     } 
/* 147 */     if (entity != null) {
/* 148 */       entity.sentMetadata(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   private boolean callOldMetaHandler(int entityId, EntityType type, Metadata metadata, List<Metadata> metadataList, UserConnection connection) {
/*     */     try {
/* 155 */       handleMetadata(entityId, type, metadata, metadataList, connection);
/* 156 */       return true;
/* 157 */     } catch (Exception e) {
/* 158 */       logException(e, type, metadataList, metadata);
/* 159 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int newEntityId(int id) {
/* 179 */     return (this.typeMappings != null) ? this.typeMappings.getNewIdOrDefault(id, id) : id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mapEntityType(EntityType type, EntityType mappedType) {
/* 190 */     Preconditions.checkArgument((type.getClass() != mappedType.getClass()), "EntityTypes should not be of the same class/enum");
/* 191 */     mapEntityType(type.getId(), mappedType.getId());
/*     */   }
/*     */   
/*     */   protected void mapEntityType(int id, int mappedId) {
/* 195 */     if (this.typeMappings == null) {
/* 196 */       this.typeMappings = (Mappings)Int2IntMapMappings.of();
/*     */     }
/* 198 */     this.typeMappings.setNewId(id, mappedId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <E extends Enum<E> & EntityType> void mapTypes(EntityType[] oldTypes, Class<E> newTypeClass) {
/* 209 */     if (this.typeMappings == null) {
/* 210 */       this.typeMappings = (Mappings)Int2IntMapMappings.of();
/*     */     }
/* 212 */     for (EntityType oldType : oldTypes) {
/*     */       try {
/* 214 */         E newType = Enum.valueOf(newTypeClass, oldType.name());
/* 215 */         this.typeMappings.setNewId(oldType.getId(), ((EntityType)newType).getId());
/* 216 */       } catch (IllegalArgumentException notFound) {
/* 217 */         if (!this.typeMappings.contains(oldType.getId())) {
/* 218 */           Via.getPlatform().getLogger().warning("Could not find new entity type for " + oldType + "! Old type: " + oldType
/* 219 */               .getClass().getEnclosingClass().getSimpleName() + ", new type: " + newTypeClass.getEnclosingClass().getSimpleName());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mapTypes() {
/* 229 */     Preconditions.checkArgument((this.typeMappings == null), "Type mappings have already been set - manual type mappings should be set *after* this");
/* 230 */     Preconditions.checkNotNull(this.protocol.getMappingData().getEntityMappings(), "Protocol does not have entity mappings");
/* 231 */     this.typeMappings = (Mappings)this.protocol.getMappingData().getEntityMappings();
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
/*     */   public void registerMetaTypeHandler(MetaType itemType, MetaType blockStateType, MetaType optionalBlockStateType, MetaType particleType) {
/* 243 */     filter().handler((event, meta) -> {
/*     */           MetaType type = meta.metaType();
/*     */           if (type == itemType) {
/*     */             this.protocol.getItemRewriter().handleItemToClient((Item)meta.value());
/*     */           } else if (type == blockStateType) {
/*     */             int data = ((Integer)meta.value()).intValue();
/*     */             meta.setValue(Integer.valueOf(this.protocol.getMappingData().getNewBlockStateId(data)));
/*     */           } else if (type == optionalBlockStateType) {
/*     */             int data = ((Integer)meta.value()).intValue();
/*     */             if (data != 0) {
/*     */               meta.setValue(Integer.valueOf(this.protocol.getMappingData().getNewBlockStateId(data)));
/*     */             }
/*     */           } else if (type == particleType) {
/*     */             rewriteParticle((Particle)meta.value());
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public void registerTracker(C packetType) {
/* 262 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 265 */             map((Type)Type.VAR_INT);
/* 266 */             map(Type.UUID);
/* 267 */             map((Type)Type.VAR_INT);
/* 268 */             handler(EntityRewriter.this.trackerHandler());
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void registerTrackerWithData(C packetType, final EntityType fallingBlockType) {
/* 274 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 277 */             map((Type)Type.VAR_INT);
/* 278 */             map(Type.UUID);
/* 279 */             map((Type)Type.VAR_INT);
/* 280 */             map((Type)Type.DOUBLE);
/* 281 */             map((Type)Type.DOUBLE);
/* 282 */             map((Type)Type.DOUBLE);
/* 283 */             map((Type)Type.BYTE);
/* 284 */             map((Type)Type.BYTE);
/* 285 */             map((Type)Type.INT);
/* 286 */             handler(EntityRewriter.this.trackerHandler());
/* 287 */             handler(wrapper -> {
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   EntityType entityType = EntityRewriter.this.tracker(wrapper.user()).entityType(entityId);
/*     */                   if (entityType == fallingBlockType) {
/*     */                     wrapper.set((Type)Type.INT, 0, Integer.valueOf(EntityRewriter.this.protocol.getMappingData().getNewBlockStateId(((Integer)wrapper.get((Type)Type.INT, 0)).intValue())));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void registerTrackerWithData1_19(C packetType, final EntityType fallingBlockType) {
/* 299 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 302 */             map((Type)Type.VAR_INT);
/* 303 */             map(Type.UUID);
/* 304 */             map((Type)Type.VAR_INT);
/* 305 */             map((Type)Type.DOUBLE);
/* 306 */             map((Type)Type.DOUBLE);
/* 307 */             map((Type)Type.DOUBLE);
/* 308 */             map((Type)Type.BYTE);
/* 309 */             map((Type)Type.BYTE);
/* 310 */             map((Type)Type.BYTE);
/* 311 */             map((Type)Type.VAR_INT);
/* 312 */             handler(EntityRewriter.this.trackerHandler());
/* 313 */             handler(wrapper -> {
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   EntityType entityType = EntityRewriter.this.tracker(wrapper.user()).entityType(entityId);
/*     */                   if (entityType == fallingBlockType) {
/*     */                     wrapper.set((Type)Type.VAR_INT, 2, Integer.valueOf(EntityRewriter.this.protocol.getMappingData().getNewBlockStateId(((Integer)wrapper.get((Type)Type.VAR_INT, 2)).intValue())));
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
/*     */   public void registerTracker(C packetType, EntityType entityType, Type<Integer> intType) {
/* 332 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, wrapper -> {
/*     */           int entityId = ((Integer)wrapper.passthrough(intType)).intValue();
/*     */           tracker(wrapper.user()).addEntity(entityId, entityType);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerTracker(C packetType, EntityType entityType) {
/* 345 */     registerTracker(packetType, entityType, (Type<Integer>)Type.VAR_INT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerRemoveEntities(C packetType) {
/* 354 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, wrapper -> {
/*     */           int[] entityIds = (int[])wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
/*     */           EntityTracker entityTracker = tracker(wrapper.user());
/*     */           for (int entity : entityIds) {
/*     */             entityTracker.removeEntity(entity);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerRemoveEntity(C packetType) {
/* 369 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, wrapper -> {
/*     */           int entityId = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           tracker(wrapper.user()).removeEntity(entityId);
/*     */         });
/*     */   }
/*     */   
/*     */   public void registerMetadataRewriter(C packetType, final Type<List<Metadata>> oldMetaType, final Type<List<Metadata>> newMetaType) {
/* 376 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 379 */             map((Type)Type.VAR_INT);
/* 380 */             if (oldMetaType != null) {
/* 381 */               map(oldMetaType, newMetaType);
/*     */             } else {
/* 383 */               map(newMetaType);
/*     */             } 
/* 385 */             handler(wrapper -> {
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   List<Metadata> metadata = (List<Metadata>)wrapper.get(newMetaType, 0);
/*     */                   EntityRewriter.this.handleMetadata(entityId, metadata, wrapper.user());
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void registerMetadataRewriter(C packetType, Type<List<Metadata>> metaType) {
/* 395 */     registerMetadataRewriter(packetType, (Type<List<Metadata>>)null, metaType);
/*     */   }
/*     */   
/*     */   public PacketHandler trackerHandler() {
/* 399 */     return trackerAndRewriterHandler((Type<List<Metadata>>)null);
/*     */   }
/*     */   
/*     */   public PacketHandler playerTrackerHandler() {
/* 403 */     return wrapper -> {
/*     */         EntityTracker tracker = tracker(wrapper.user());
/*     */         int entityId = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */         tracker.setClientEntityId(entityId);
/*     */         tracker.addEntity(entityId, tracker.playerType());
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PacketHandler worldDataTrackerHandler(int nbtIndex) {
/* 419 */     return wrapper -> {
/*     */         EntityTracker tracker = tracker(wrapper.user());
/*     */         CompoundTag registryData = (CompoundTag)wrapper.get(Type.NBT, nbtIndex);
/*     */         Tag height = registryData.get("height");
/*     */         if (height instanceof IntTag) {
/*     */           int blockHeight = ((IntTag)height).asInt();
/*     */           tracker.setCurrentWorldSectionHeight(blockHeight >> 4);
/*     */         } else {
/*     */           Via.getPlatform().getLogger().warning("Height missing in dimension data: " + registryData);
/*     */         } 
/*     */         Tag minY = registryData.get("min_y");
/*     */         if (minY instanceof IntTag) {
/*     */           tracker.setCurrentMinY(((IntTag)minY).asInt());
/*     */         } else {
/*     */           Via.getPlatform().getLogger().warning("Min Y missing in dimension data: " + registryData);
/*     */         } 
/*     */         String world = (String)wrapper.get(Type.STRING, 0);
/*     */         if (tracker.currentWorld() != null && !tracker.currentWorld().equals(world)) {
/*     */           tracker.clearEntities();
/*     */           tracker.trackClientEntity();
/*     */         } 
/*     */         tracker.setCurrentWorld(world);
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PacketHandler worldDataTrackerHandlerByKey() {
/* 448 */     return wrapper -> {
/*     */         EntityTracker tracker = tracker(wrapper.user());
/*     */         String dimensionKey = (String)wrapper.get(Type.STRING, 0);
/*     */         DimensionData dimensionData = tracker.dimensionData(dimensionKey);
/*     */         if (dimensionData == null) {
/*     */           Via.getPlatform().getLogger().severe("Dimension data missing for dimension: " + dimensionKey + ", falling back to overworld");
/*     */           dimensionData = tracker.dimensionData("minecraft:overworld");
/*     */           Preconditions.checkNotNull(dimensionData, "Overworld data missing");
/*     */         } 
/*     */         tracker.setCurrentWorldSectionHeight(dimensionData.height() >> 4);
/*     */         tracker.setCurrentMinY(dimensionData.minY());
/*     */         String world = (String)wrapper.get(Type.STRING, 1);
/*     */         if (tracker.currentWorld() != null && !tracker.currentWorld().equals(world)) {
/*     */           tracker.clearEntities();
/*     */           tracker.trackClientEntity();
/*     */         } 
/*     */         tracker.setCurrentWorld(world);
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PacketHandler biomeSizeTracker() {
/* 471 */     return wrapper -> {
/*     */         CompoundTag registry = (CompoundTag)wrapper.get(Type.NBT, 0);
/*     */         CompoundTag biomeRegistry = (CompoundTag)registry.get("minecraft:worldgen/biome");
/*     */         ListTag biomes = (ListTag)biomeRegistry.get("value");
/*     */         tracker(wrapper.user()).setBiomesSent(biomes.size());
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PacketHandler dimensionDataHandler() {
/* 485 */     return wrapper -> {
/*     */         CompoundTag tag = (CompoundTag)wrapper.get(Type.NBT, 0);
/*     */         ListTag dimensions = (ListTag)((CompoundTag)tag.get("minecraft:dimension_type")).get("value");
/*     */         Map<String, DimensionData> dimensionDataMap = new HashMap<>(dimensions.size());
/*     */         for (Tag dimension : dimensions) {
/*     */           CompoundTag dimensionCompound = (CompoundTag)dimension;
/*     */           CompoundTag element = (CompoundTag)dimensionCompound.get("element");
/*     */           String name = (String)dimensionCompound.get("name").getValue();
/*     */           dimensionDataMap.put(name, new DimensionDataImpl(element));
/*     */         } 
/*     */         tracker(wrapper.user()).setDimensions(dimensionDataMap);
/*     */       };
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
/*     */   public PacketHandler trackerAndRewriterHandler(Type<List<Metadata>> metaType) {
/* 509 */     return wrapper -> {
/*     */         int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */         int type = ((Integer)wrapper.get((Type)Type.VAR_INT, 1)).intValue();
/*     */         int newType = newEntityId(type);
/*     */         if (newType != type) {
/*     */           wrapper.set((Type)Type.VAR_INT, 1, Integer.valueOf(newType));
/*     */         }
/*     */         EntityType entType = typeFromId(this.trackMappedType ? newType : type);
/*     */         tracker(wrapper.user()).addEntity(entityId, entType);
/*     */         if (metaType != null) {
/*     */           handleMetadata(entityId, (List<Metadata>)wrapper.get(metaType, 0), wrapper.user());
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PacketHandler trackerAndRewriterHandler(Type<List<Metadata>> metaType, EntityType entityType) {
/* 529 */     return wrapper -> {
/*     */         int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */         tracker(wrapper.user()).addEntity(entityId, entityType);
/*     */         if (metaType != null) {
/*     */           handleMetadata(entityId, (List<Metadata>)wrapper.get(metaType, 0), wrapper.user());
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PacketHandler objectTrackerHandler() {
/* 546 */     return wrapper -> {
/*     */         int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */         byte type = ((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */         EntityType entType = objectTypeFromId(type);
/*     */         tracker(wrapper.user()).addEntity(entityId, entType);
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected Metadata metaByIndex(int index, List<Metadata> metadataList) {
/* 560 */     for (Metadata metadata : metadataList) {
/* 561 */       if (metadata.id() == index) {
/* 562 */         return metadata;
/*     */       }
/*     */     } 
/* 565 */     return null;
/*     */   }
/*     */   
/*     */   protected void rewriteParticle(Particle particle) {
/* 569 */     ParticleMappings mappings = this.protocol.getMappingData().getParticleMappings();
/* 570 */     int id = particle.getId();
/* 571 */     if (mappings.isBlockParticle(id)) {
/* 572 */       Particle.ParticleData data = particle.getArguments().get(0);
/* 573 */       data.setValue(Integer.valueOf(this.protocol.getMappingData().getNewBlockStateId(((Integer)data.get()).intValue())));
/* 574 */     } else if (mappings.isItemParticle(id) && this.protocol.getItemRewriter() != null) {
/* 575 */       Particle.ParticleData data = particle.getArguments().get(0);
/* 576 */       Item item = (Item)data.get();
/* 577 */       this.protocol.getItemRewriter().handleItemToClient(item);
/*     */     } 
/*     */     
/* 580 */     particle.setId(this.protocol.getMappingData().getNewParticleId(id));
/*     */   }
/*     */   
/*     */   private void logException(Exception e, EntityType type, List<Metadata> metadataList, Metadata metadata) {
/* 584 */     if (!Via.getConfig().isSuppressMetadataErrors() || Via.getManager().isDebug()) {
/* 585 */       Logger logger = Via.getPlatform().getLogger();
/* 586 */       logger.severe("An error occurred in metadata handler " + getClass().getSimpleName() + " for " + ((type != null) ? type
/* 587 */           .name() : "untracked") + " entity type: " + metadata);
/* 588 */       logger.severe(metadataList.stream().sorted(Comparator.comparingInt(Metadata::id))
/* 589 */           .map(Metadata::toString).collect(Collectors.joining("\n", "Full metadata: ", "")));
/* 590 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\rewriter\EntityRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
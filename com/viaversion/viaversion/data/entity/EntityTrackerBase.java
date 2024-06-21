/*     */ package com.viaversion.viaversion.data.entity;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.entity.ClientEntityIdChangeListener;
/*     */ import com.viaversion.viaversion.api.data.entity.DimensionData;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.data.entity.StoredEntityData;
/*     */ import com.viaversion.viaversion.api.data.entity.TrackedEntity;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*     */ import com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
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
/*     */ public class EntityTrackerBase
/*     */   implements EntityTracker, ClientEntityIdChangeListener
/*     */ {
/*  36 */   private final Int2ObjectMap<TrackedEntity> entities = (Int2ObjectMap<TrackedEntity>)Int2ObjectSyncMap.hashmap();
/*     */   private final UserConnection connection;
/*     */   private final EntityType playerType;
/*  39 */   private int clientEntityId = -1;
/*  40 */   private int currentWorldSectionHeight = 16;
/*     */   private int currentMinY;
/*     */   private String currentWorld;
/*  43 */   private int biomesSent = -1;
/*  44 */   private Map<String, DimensionData> dimensions = Collections.emptyMap();
/*     */   
/*     */   public EntityTrackerBase(UserConnection connection, EntityType playerType) {
/*  47 */     this.connection = connection;
/*  48 */     this.playerType = playerType;
/*     */   }
/*     */ 
/*     */   
/*     */   public UserConnection user() {
/*  53 */     return this.connection;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addEntity(int id, EntityType type) {
/*  58 */     this.entities.put(id, new TrackedEntityImpl(type));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasEntity(int id) {
/*  63 */     return this.entities.containsKey(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public TrackedEntity entity(int entityId) {
/*  68 */     return (TrackedEntity)this.entities.get(entityId);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType entityType(int id) {
/*  73 */     TrackedEntity entity = (TrackedEntity)this.entities.get(id);
/*  74 */     return (entity != null) ? entity.entityType() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public StoredEntityData entityData(int id) {
/*  79 */     TrackedEntity entity = (TrackedEntity)this.entities.get(id);
/*  80 */     return (entity != null) ? entity.data() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public StoredEntityData entityDataIfPresent(int id) {
/*  85 */     TrackedEntity entity = (TrackedEntity)this.entities.get(id);
/*  86 */     return (entity != null && entity.hasData()) ? entity.data() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeEntity(int id) {
/*  92 */     this.entities.remove(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearEntities() {
/*  97 */     this.entities.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public int clientEntityId() {
/* 102 */     return this.clientEntityId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClientEntityId(int clientEntityId) {
/* 107 */     Preconditions.checkNotNull(this.playerType);
/*     */     TrackedEntity oldEntity;
/* 109 */     if (this.clientEntityId != -1 && (oldEntity = (TrackedEntity)this.entities.remove(this.clientEntityId)) != null) {
/* 110 */       this.entities.put(clientEntityId, oldEntity);
/*     */     } else {
/* 112 */       this.entities.put(clientEntityId, new TrackedEntityImpl(this.playerType));
/*     */     } 
/*     */     
/* 115 */     this.clientEntityId = clientEntityId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean trackClientEntity() {
/* 120 */     if (this.clientEntityId != -1) {
/* 121 */       this.entities.put(this.clientEntityId, new TrackedEntityImpl(this.playerType));
/* 122 */       return true;
/*     */     } 
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int currentWorldSectionHeight() {
/* 129 */     return this.currentWorldSectionHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentWorldSectionHeight(int currentWorldSectionHeight) {
/* 134 */     this.currentWorldSectionHeight = currentWorldSectionHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public int currentMinY() {
/* 139 */     return this.currentMinY;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentMinY(int currentMinY) {
/* 144 */     this.currentMinY = currentMinY;
/*     */   }
/*     */ 
/*     */   
/*     */   public String currentWorld() {
/* 149 */     return this.currentWorld;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentWorld(String currentWorld) {
/* 154 */     this.currentWorld = currentWorld;
/*     */   }
/*     */ 
/*     */   
/*     */   public int biomesSent() {
/* 159 */     return this.biomesSent;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBiomesSent(int biomesSent) {
/* 164 */     this.biomesSent = biomesSent;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType playerType() {
/* 169 */     return this.playerType;
/*     */   }
/*     */ 
/*     */   
/*     */   public DimensionData dimensionData(String dimension) {
/* 174 */     return this.dimensions.get(dimension);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDimensions(Map<String, DimensionData> dimensions) {
/* 179 */     this.dimensions = dimensions;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\data\entity\EntityTrackerBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
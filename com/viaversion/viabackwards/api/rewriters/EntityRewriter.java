/*     */ package com.viaversion.viabackwards.api.rewriters;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_14;
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
/*     */ public abstract class EntityRewriter<C extends ClientboundPacketType, T extends BackwardsProtocol<C, ?, ?, ?>>
/*     */   extends EntityRewriterBase<C, T>
/*     */ {
/*     */   protected EntityRewriter(T protocol) {
/*  34 */     this(protocol, Types1_14.META_TYPES.optionalComponentType, Types1_14.META_TYPES.booleanType);
/*     */   }
/*     */   
/*     */   protected EntityRewriter(T protocol, MetaType displayType, MetaType displayVisibilityType) {
/*  38 */     super(protocol, displayType, 2, displayVisibilityType, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerTrackerWithData(C packetType, final EntityType fallingBlockType) {
/*  43 */     ((BackwardsProtocol)this.protocol).registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  46 */             map((Type)Type.VAR_INT);
/*  47 */             map(Type.UUID);
/*  48 */             map((Type)Type.VAR_INT);
/*  49 */             map((Type)Type.DOUBLE);
/*  50 */             map((Type)Type.DOUBLE);
/*  51 */             map((Type)Type.DOUBLE);
/*  52 */             map((Type)Type.BYTE);
/*  53 */             map((Type)Type.BYTE);
/*  54 */             map((Type)Type.INT);
/*  55 */             handler(EntityRewriter.this.getSpawnTrackerWithDataHandler(fallingBlockType));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerTrackerWithData1_19(C packetType, final EntityType fallingBlockType) {
/*  62 */     ((BackwardsProtocol)this.protocol).registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  65 */             map((Type)Type.VAR_INT);
/*  66 */             map(Type.UUID);
/*  67 */             map((Type)Type.VAR_INT);
/*  68 */             map((Type)Type.DOUBLE);
/*  69 */             map((Type)Type.DOUBLE);
/*  70 */             map((Type)Type.DOUBLE);
/*  71 */             map((Type)Type.BYTE);
/*  72 */             map((Type)Type.BYTE);
/*  73 */             map((Type)Type.BYTE);
/*  74 */             map((Type)Type.VAR_INT);
/*  75 */             handler(EntityRewriter.this.getSpawnTrackerWithDataHandler1_19(fallingBlockType));
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public PacketHandler getSpawnTrackerWithDataHandler(EntityType fallingBlockType) {
/*  81 */     return wrapper -> {
/*     */         EntityType entityType = trackAndMapEntity(wrapper);
/*     */         if (entityType == fallingBlockType) {
/*     */           int blockState = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */           wrapper.set((Type)Type.INT, 0, Integer.valueOf(((BackwardsProtocol)this.protocol).getMappingData().getNewBlockStateId(blockState)));
/*     */         } 
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public PacketHandler getSpawnTrackerWithDataHandler1_19(EntityType fallingBlockType) {
/*  92 */     return wrapper -> {
/*     */         EntityType entityType = trackAndMapEntity(wrapper);
/*     */         if (entityType == fallingBlockType) {
/*     */           int blockState = ((Integer)wrapper.get((Type)Type.VAR_INT, 2)).intValue();
/*     */           wrapper.set((Type)Type.VAR_INT, 2, Integer.valueOf(((BackwardsProtocol)this.protocol).getMappingData().getNewBlockStateId(blockState)));
/*     */         } 
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerSpawnTracker(C packetType) {
/* 103 */     ((BackwardsProtocol)this.protocol).registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 106 */             map((Type)Type.VAR_INT);
/* 107 */             map(Type.UUID);
/* 108 */             map((Type)Type.VAR_INT);
/* 109 */             handler(wrapper -> EntityRewriter.this.trackAndMapEntity(wrapper));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PacketHandler worldTrackerHandlerByKey() {
/* 120 */     return wrapper -> {
/*     */         EntityTracker tracker = tracker(wrapper.user());
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EntityType trackAndMapEntity(PacketWrapper wrapper) throws Exception {
/* 138 */     int typeId = ((Integer)wrapper.get((Type)Type.VAR_INT, 1)).intValue();
/* 139 */     EntityType entityType = typeFromId(typeId);
/* 140 */     tracker(wrapper.user()).addEntity(((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue(), entityType);
/*     */     
/* 142 */     int mappedTypeId = newEntityId(entityType.getId());
/* 143 */     if (typeId != mappedTypeId) {
/* 144 */       wrapper.set((Type)Type.VAR_INT, 1, Integer.valueOf(mappedTypeId));
/*     */     }
/*     */     
/* 147 */     return entityType;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\rewriters\EntityRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
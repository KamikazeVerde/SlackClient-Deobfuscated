/*     */ package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
/*     */ import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data.EntityTypeMapping;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data.ImmediateRespawn;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
/*     */ import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
/*     */ import java.util.ArrayList;
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
/*     */ public class EntityPackets1_15
/*     */   extends EntityRewriter<ClientboundPackets1_15, Protocol1_14_4To1_15>
/*     */ {
/*     */   public EntityPackets1_15(Protocol1_14_4To1_15 protocol) {
/*  38 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  43 */     ((Protocol1_14_4To1_15)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_15.UPDATE_HEALTH, wrapper -> {
/*     */           float health = ((Float)wrapper.passthrough((Type)Type.FLOAT)).floatValue();
/*     */           if (health > 0.0F) {
/*     */             return;
/*     */           }
/*     */           if (!((ImmediateRespawn)wrapper.user().get(ImmediateRespawn.class)).isImmediateRespawn())
/*     */             return; 
/*     */           PacketWrapper statusPacket = wrapper.create((PacketType)ServerboundPackets1_14.CLIENT_STATUS);
/*     */           statusPacket.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */           statusPacket.sendToServer(Protocol1_14_4To1_15.class);
/*     */         });
/*  54 */     ((Protocol1_14_4To1_15)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_15.GAME_EVENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  57 */             map((Type)Type.UNSIGNED_BYTE);
/*  58 */             map((Type)Type.FLOAT);
/*  59 */             handler(wrapper -> {
/*     */                   if (((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue() == 11) {
/*     */                     ((ImmediateRespawn)wrapper.user().get(ImmediateRespawn.class)).setImmediateRespawn((((Float)wrapper.get((Type)Type.FLOAT, 0)).floatValue() == 1.0F));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/*  67 */     registerTrackerWithData((ClientboundPacketType)ClientboundPackets1_15.SPAWN_ENTITY, (EntityType)Entity1_15Types.FALLING_BLOCK);
/*     */     
/*  69 */     ((Protocol1_14_4To1_15)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_15.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  72 */             map((Type)Type.VAR_INT);
/*  73 */             map(Type.UUID);
/*  74 */             map((Type)Type.VAR_INT);
/*  75 */             map((Type)Type.DOUBLE);
/*  76 */             map((Type)Type.DOUBLE);
/*  77 */             map((Type)Type.DOUBLE);
/*  78 */             map((Type)Type.BYTE);
/*  79 */             map((Type)Type.BYTE);
/*  80 */             map((Type)Type.BYTE);
/*  81 */             map((Type)Type.SHORT);
/*  82 */             map((Type)Type.SHORT);
/*  83 */             map((Type)Type.SHORT);
/*  84 */             handler(wrapper -> wrapper.write(Types1_14.METADATA_LIST, new ArrayList()));
/*     */             
/*  86 */             handler(wrapper -> {
/*     */                   int type = ((Integer)wrapper.get((Type)Type.VAR_INT, 1)).intValue();
/*     */                   
/*     */                   EntityType entityType = Entity1_15Types.getTypeFromId(type);
/*     */                   EntityPackets1_15.this.tracker(wrapper.user()).addEntity(((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue(), entityType);
/*     */                   wrapper.set((Type)Type.VAR_INT, 1, Integer.valueOf(EntityTypeMapping.getOldEntityId(type)));
/*     */                 });
/*     */           }
/*     */         });
/*  95 */     ((Protocol1_14_4To1_15)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_15.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  98 */             map((Type)Type.INT);
/*  99 */             map((Type)Type.LONG, (Type)Type.NOTHING);
/*     */           }
/*     */         });
/*     */     
/* 103 */     ((Protocol1_14_4To1_15)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_15.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 106 */             map((Type)Type.INT);
/* 107 */             map((Type)Type.UNSIGNED_BYTE);
/* 108 */             map((Type)Type.INT);
/*     */             
/* 110 */             map((Type)Type.LONG, (Type)Type.NOTHING);
/*     */             
/* 112 */             map((Type)Type.UNSIGNED_BYTE);
/* 113 */             map(Type.STRING);
/* 114 */             map((Type)Type.VAR_INT);
/* 115 */             map((Type)Type.BOOLEAN);
/*     */             
/* 117 */             handler(EntityPackets1_15.this.getTrackerHandler((EntityType)Entity1_15Types.PLAYER, (Type)Type.INT));
/*     */             
/* 119 */             handler(wrapper -> {
/*     */                   boolean immediateRespawn = !((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue();
/*     */                   
/*     */                   ((ImmediateRespawn)wrapper.user().get(ImmediateRespawn.class)).setImmediateRespawn(immediateRespawn);
/*     */                 });
/*     */           }
/*     */         });
/* 126 */     registerTracker((ClientboundPacketType)ClientboundPackets1_15.SPAWN_EXPERIENCE_ORB, (EntityType)Entity1_15Types.EXPERIENCE_ORB);
/* 127 */     registerTracker((ClientboundPacketType)ClientboundPackets1_15.SPAWN_GLOBAL_ENTITY, (EntityType)Entity1_15Types.LIGHTNING_BOLT);
/* 128 */     registerTracker((ClientboundPacketType)ClientboundPackets1_15.SPAWN_PAINTING, (EntityType)Entity1_15Types.PAINTING);
/*     */     
/* 130 */     ((Protocol1_14_4To1_15)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_15.SPAWN_PLAYER, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 133 */             map((Type)Type.VAR_INT);
/* 134 */             map(Type.UUID);
/* 135 */             map((Type)Type.DOUBLE);
/* 136 */             map((Type)Type.DOUBLE);
/* 137 */             map((Type)Type.DOUBLE);
/* 138 */             map((Type)Type.BYTE);
/* 139 */             map((Type)Type.BYTE);
/* 140 */             handler(wrapper -> wrapper.write(Types1_14.METADATA_LIST, new ArrayList()));
/*     */             
/* 142 */             handler(EntityPackets1_15.this.getTrackerHandler((EntityType)Entity1_15Types.PLAYER, (Type)Type.VAR_INT));
/*     */           }
/*     */         });
/*     */     
/* 146 */     registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_15.DESTROY_ENTITIES);
/* 147 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_15.ENTITY_METADATA, Types1_14.METADATA_LIST);
/*     */ 
/*     */     
/* 150 */     ((Protocol1_14_4To1_15)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_15.ENTITY_PROPERTIES, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 153 */             map((Type)Type.VAR_INT);
/* 154 */             map((Type)Type.INT);
/* 155 */             handler(wrapper -> {
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   EntityType entityType = EntityPackets1_15.this.tracker(wrapper.user()).entityType(entityId);
/*     */                   if (entityType != Entity1_15Types.BEE) {
/*     */                     return;
/*     */                   }
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
/*     */   protected void registerRewrites() {
/* 195 */     registerMetaTypeHandler(Types1_14.META_TYPES.itemType, Types1_14.META_TYPES.blockStateType, null, Types1_14.META_TYPES.particleType, Types1_14.META_TYPES.componentType, Types1_14.META_TYPES.optionalComponentType);
/*     */ 
/*     */     
/* 198 */     filter().filterFamily((EntityType)Entity1_15Types.LIVINGENTITY).removeIndex(12);
/*     */     
/* 200 */     filter().type((EntityType)Entity1_15Types.BEE).cancel(15);
/* 201 */     filter().type((EntityType)Entity1_15Types.BEE).cancel(16);
/*     */     
/* 203 */     mapEntityTypeWithData((EntityType)Entity1_15Types.BEE, (EntityType)Entity1_15Types.PUFFERFISH).jsonName().spawnMetadata(storage -> {
/*     */           storage.add(new Metadata(14, Types1_14.META_TYPES.booleanType, Boolean.valueOf(false)));
/*     */           
/*     */           storage.add(new Metadata(15, Types1_14.META_TYPES.varIntType, Integer.valueOf(2)));
/*     */         });
/* 208 */     filter().type((EntityType)Entity1_15Types.ENDERMAN).cancel(16);
/* 209 */     filter().type((EntityType)Entity1_15Types.TRIDENT).cancel(10);
/*     */ 
/*     */     
/* 212 */     filter().type((EntityType)Entity1_15Types.WOLF).addIndex(17);
/* 213 */     filter().type((EntityType)Entity1_15Types.WOLF).index(8).handler((event, meta) -> event.createExtraMeta(new Metadata(17, Types1_14.META_TYPES.floatType, event.meta().value())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int typeId) {
/* 220 */     return Entity1_15Types.getTypeFromId(typeId);
/*     */   }
/*     */ 
/*     */   
/*     */   public int newEntityId(int newId) {
/* 225 */     return EntityTypeMapping.getOldEntityId(newId);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_14_4to1_15\packets\EntityPackets1_15.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
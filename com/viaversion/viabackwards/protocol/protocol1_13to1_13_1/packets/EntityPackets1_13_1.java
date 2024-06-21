/*     */ package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.Particle;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_13;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
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
/*     */ public class EntityPackets1_13_1
/*     */   extends LegacyEntityRewriter<ClientboundPackets1_13, Protocol1_13To1_13_1>
/*     */ {
/*     */   public EntityPackets1_13_1(Protocol1_13To1_13_1 protocol) {
/*  38 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  43 */     ((Protocol1_13To1_13_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  46 */             map((Type)Type.VAR_INT);
/*  47 */             map(Type.UUID);
/*  48 */             map((Type)Type.BYTE);
/*  49 */             map((Type)Type.DOUBLE);
/*  50 */             map((Type)Type.DOUBLE);
/*  51 */             map((Type)Type.DOUBLE);
/*  52 */             map((Type)Type.BYTE);
/*  53 */             map((Type)Type.BYTE);
/*  54 */             map((Type)Type.INT);
/*     */             
/*  56 */             handler(wrapper -> {
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   byte type = ((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   
/*     */                   Entity1_13Types.EntityType entType = Entity1_13Types.getTypeFromId(type, true);
/*     */                   
/*     */                   if (entType == null) {
/*     */                     ViaBackwards.getPlatform().getLogger().warning("Could not find 1.13 entity type " + type);
/*     */                     
/*     */                     return;
/*     */                   } 
/*     */                   
/*     */                   if (entType.is((EntityType)Entity1_13Types.EntityType.FALLING_BLOCK)) {
/*     */                     int data = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                     wrapper.set((Type)Type.INT, 0, Integer.valueOf(((Protocol1_13To1_13_1)EntityPackets1_13_1.this.protocol).getMappingData().getNewBlockStateId(data)));
/*     */                   } 
/*     */                   EntityPackets1_13_1.this.tracker(wrapper.user()).addEntity(entityId, (EntityType)entType);
/*     */                 });
/*     */           }
/*     */         });
/*  77 */     registerTracker((ClientboundPacketType)ClientboundPackets1_13.SPAWN_EXPERIENCE_ORB, (EntityType)Entity1_13Types.EntityType.EXPERIENCE_ORB);
/*  78 */     registerTracker((ClientboundPacketType)ClientboundPackets1_13.SPAWN_GLOBAL_ENTITY, (EntityType)Entity1_13Types.EntityType.LIGHTNING_BOLT);
/*     */     
/*  80 */     ((Protocol1_13To1_13_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  83 */             map((Type)Type.VAR_INT);
/*  84 */             map(Type.UUID);
/*  85 */             map((Type)Type.VAR_INT);
/*  86 */             map((Type)Type.DOUBLE);
/*  87 */             map((Type)Type.DOUBLE);
/*  88 */             map((Type)Type.DOUBLE);
/*  89 */             map((Type)Type.BYTE);
/*  90 */             map((Type)Type.BYTE);
/*  91 */             map((Type)Type.BYTE);
/*  92 */             map((Type)Type.SHORT);
/*  93 */             map((Type)Type.SHORT);
/*  94 */             map((Type)Type.SHORT);
/*  95 */             map(Types1_13.METADATA_LIST);
/*     */ 
/*     */             
/*  98 */             handler(EntityPackets1_13_1.this.getTrackerHandler());
/*     */ 
/*     */             
/* 101 */             handler(wrapper -> {
/*     */                   List<Metadata> metadata = (List<Metadata>)wrapper.get(Types1_13.METADATA_LIST, 0);
/*     */                   
/*     */                   EntityPackets1_13_1.this.handleMetadata(((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue(), metadata, wrapper.user());
/*     */                 });
/*     */           }
/*     */         });
/* 108 */     ((Protocol1_13To1_13_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_PLAYER, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 111 */             map((Type)Type.VAR_INT);
/* 112 */             map(Type.UUID);
/* 113 */             map((Type)Type.DOUBLE);
/* 114 */             map((Type)Type.DOUBLE);
/* 115 */             map((Type)Type.DOUBLE);
/* 116 */             map((Type)Type.BYTE);
/* 117 */             map((Type)Type.BYTE);
/* 118 */             map(Types1_13.METADATA_LIST);
/*     */             
/* 120 */             handler(EntityPackets1_13_1.this.getTrackerAndMetaHandler(Types1_13.METADATA_LIST, (EntityType)Entity1_13Types.EntityType.PLAYER));
/*     */           }
/*     */         });
/*     */     
/* 124 */     registerTracker((ClientboundPacketType)ClientboundPackets1_13.SPAWN_PAINTING, (EntityType)Entity1_13Types.EntityType.PAINTING);
/* 125 */     registerJoinGame((ClientboundPacketType)ClientboundPackets1_13.JOIN_GAME, (EntityType)Entity1_13Types.EntityType.PLAYER);
/* 126 */     registerRespawn((ClientboundPacketType)ClientboundPackets1_13.RESPAWN);
/* 127 */     registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_13.DESTROY_ENTITIES);
/* 128 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_13.ENTITY_METADATA, Types1_13.METADATA_LIST);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerRewrites() {
/* 134 */     filter().handler((event, meta) -> {
/*     */           if (meta.metaType() == Types1_13.META_TYPES.itemType) {
/*     */             ((Protocol1_13To1_13_1)this.protocol).getItemRewriter().handleItemToClient((Item)meta.getValue());
/*     */           } else if (meta.metaType() == Types1_13.META_TYPES.blockStateType) {
/*     */             int data = ((Integer)meta.getValue()).intValue();
/*     */             
/*     */             meta.setValue(Integer.valueOf(((Protocol1_13To1_13_1)this.protocol).getMappingData().getNewBlockStateId(data)));
/*     */           } else if (meta.metaType() == Types1_13.META_TYPES.particleType) {
/*     */             rewriteParticle((Particle)meta.getValue());
/*     */           } else if (meta.metaType() == Types1_13.META_TYPES.optionalComponentType || meta.metaType() == Types1_13.META_TYPES.componentType) {
/*     */             JsonElement element = (JsonElement)meta.value();
/*     */             
/*     */             ((Protocol1_13To1_13_1)this.protocol).translatableRewriter().processText(element);
/*     */           } 
/*     */         });
/*     */     
/* 150 */     filter().filterFamily((EntityType)Entity1_13Types.EntityType.ABSTRACT_ARROW).cancel(7);
/*     */ 
/*     */     
/* 153 */     filter().type((EntityType)Entity1_13Types.EntityType.SPECTRAL_ARROW).index(8).toIndex(7);
/*     */ 
/*     */     
/* 156 */     filter().type((EntityType)Entity1_13Types.EntityType.TRIDENT).index(8).toIndex(7);
/*     */ 
/*     */     
/* 159 */     filter().filterFamily((EntityType)Entity1_13Types.EntityType.MINECART_ABSTRACT).index(9).handler((event, meta) -> {
/*     */           int data = ((Integer)meta.getValue()).intValue();
/*     */           meta.setValue(Integer.valueOf(((Protocol1_13To1_13_1)this.protocol).getMappingData().getNewBlockStateId(data)));
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int typeId) {
/* 167 */     return (EntityType)Entity1_13Types.getTypeFromId(typeId, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected EntityType getObjectTypeFromId(int typeId) {
/* 172 */     return (EntityType)Entity1_13Types.getTypeFromId(typeId, true);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_13to1_13_1\packets\EntityPackets1_13_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
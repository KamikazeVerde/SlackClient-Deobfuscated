/*     */ package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_16;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_17;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
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
/*     */ 
/*     */ 
/*     */ public final class EntityPackets
/*     */   extends EntityRewriter<ClientboundPackets1_16_2, Protocol1_17To1_16_4>
/*     */ {
/*     */   public EntityPackets(Protocol1_17To1_16_4 protocol) {
/*  42 */     super((Protocol)protocol);
/*  43 */     mapTypes((EntityType[])Entity1_16_2Types.values(), Entity1_17Types.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerPackets() {
/*  48 */     registerTrackerWithData((ClientboundPacketType)ClientboundPackets1_16_2.SPAWN_ENTITY, (EntityType)Entity1_17Types.FALLING_BLOCK);
/*  49 */     registerTracker((ClientboundPacketType)ClientboundPackets1_16_2.SPAWN_MOB);
/*  50 */     registerTracker((ClientboundPacketType)ClientboundPackets1_16_2.SPAWN_PLAYER, (EntityType)Entity1_17Types.PLAYER);
/*  51 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_16_2.ENTITY_METADATA, Types1_16.METADATA_LIST, Types1_17.METADATA_LIST);
/*     */     
/*  53 */     ((Protocol1_17To1_16_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.DESTROY_ENTITIES, null, wrapper -> {
/*     */           int[] entityIds = (int[])wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
/*     */           
/*     */           wrapper.cancel();
/*     */           
/*     */           EntityTracker entityTracker = wrapper.user().getEntityTracker(Protocol1_17To1_16_4.class);
/*     */           
/*     */           for (int entityId : entityIds) {
/*     */             entityTracker.removeEntity(entityId);
/*     */             
/*     */             PacketWrapper newPacket = wrapper.create((PacketType)ClientboundPackets1_17.REMOVE_ENTITY);
/*     */             newPacket.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/*     */             newPacket.send(Protocol1_17To1_16_4.class);
/*     */           } 
/*     */         });
/*  68 */     ((Protocol1_17To1_16_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  71 */             map((Type)Type.INT);
/*  72 */             map((Type)Type.BOOLEAN);
/*  73 */             map((Type)Type.UNSIGNED_BYTE);
/*  74 */             map((Type)Type.BYTE);
/*  75 */             map(Type.STRING_ARRAY);
/*  76 */             map(Type.NBT);
/*  77 */             map(Type.NBT);
/*  78 */             handler(wrapper -> {
/*     */                   CompoundTag dimensionRegistry = (CompoundTag)((CompoundTag)wrapper.get(Type.NBT, 0)).get("minecraft:dimension_type");
/*     */                   
/*     */                   ListTag dimensions = (ListTag)dimensionRegistry.get("value");
/*     */                   
/*     */                   for (Tag dimension : dimensions) {
/*     */                     CompoundTag dimensionCompound = (CompoundTag)((CompoundTag)dimension).get("element");
/*     */                     EntityPackets.addNewDimensionData(dimensionCompound);
/*     */                   } 
/*     */                   CompoundTag currentDimensionTag = (CompoundTag)wrapper.get(Type.NBT, 1);
/*     */                   EntityPackets.addNewDimensionData(currentDimensionTag);
/*     */                 });
/*  90 */             handler(EntityPackets.this.playerTrackerHandler());
/*     */           }
/*     */         });
/*     */     
/*  94 */     ((Protocol1_17To1_16_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.RESPAWN, wrapper -> {
/*     */           CompoundTag dimensionData = (CompoundTag)wrapper.passthrough(Type.NBT);
/*     */           
/*     */           addNewDimensionData(dimensionData);
/*     */         });
/*  99 */     ((Protocol1_17To1_16_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.ENTITY_PROPERTIES, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 102 */             map((Type)Type.VAR_INT);
/* 103 */             handler(wrapper -> wrapper.write((Type)Type.VAR_INT, wrapper.read((Type)Type.INT)));
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     ((Protocol1_17To1_16_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.PLAYER_POSITION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 113 */             map((Type)Type.DOUBLE);
/* 114 */             map((Type)Type.DOUBLE);
/* 115 */             map((Type)Type.DOUBLE);
/* 116 */             map((Type)Type.FLOAT);
/* 117 */             map((Type)Type.FLOAT);
/* 118 */             map((Type)Type.BYTE);
/* 119 */             map((Type)Type.VAR_INT);
/* 120 */             handler(wrapper -> wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false)));
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     ((Protocol1_17To1_16_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.COMBAT_EVENT, null, wrapper -> {
/*     */           ClientboundPackets1_17 clientboundPackets1_173;
/*     */           ClientboundPackets1_17 clientboundPackets1_172;
/*     */           ClientboundPackets1_17 clientboundPackets1_171;
/*     */           int type = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           switch (type) {
/*     */             case 0:
/*     */               clientboundPackets1_173 = ClientboundPackets1_17.COMBAT_ENTER;
/*     */               break;
/*     */             
/*     */             case 1:
/*     */               clientboundPackets1_172 = ClientboundPackets1_17.COMBAT_END;
/*     */               break;
/*     */             
/*     */             case 2:
/*     */               clientboundPackets1_171 = ClientboundPackets1_17.COMBAT_KILL;
/*     */               break;
/*     */             default:
/*     */               throw new IllegalArgumentException("Invalid combat type received: " + type);
/*     */           } 
/*     */           wrapper.setPacketType((PacketType)clientboundPackets1_171);
/*     */         });
/* 149 */     ((Protocol1_17To1_16_4)this.protocol).cancelClientbound((ClientboundPacketType)ClientboundPackets1_16_2.ENTITY_MOVEMENT);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerRewrites() {
/* 154 */     filter().handler((event, meta) -> {
/*     */           meta.setMetaType(Types1_17.META_TYPES.byId(meta.metaType().typeId()));
/*     */           
/*     */           if (meta.metaType() == Types1_17.META_TYPES.poseType) {
/*     */             int pose = ((Integer)meta.value()).intValue();
/*     */             
/*     */             if (pose > 5) {
/*     */               meta.setValue(Integer.valueOf(pose + 1));
/*     */             }
/*     */           } 
/*     */         });
/* 165 */     registerMetaTypeHandler(Types1_17.META_TYPES.itemType, Types1_17.META_TYPES.blockStateType, null, Types1_17.META_TYPES.particleType);
/*     */ 
/*     */     
/* 168 */     filter().filterFamily((EntityType)Entity1_17Types.ENTITY).addIndex(7);
/*     */     
/* 170 */     filter().filterFamily((EntityType)Entity1_17Types.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
/*     */           int data = ((Integer)meta.getValue()).intValue();
/*     */ 
/*     */           
/*     */           meta.setValue(Integer.valueOf(((Protocol1_17To1_16_4)this.protocol).getMappingData().getNewBlockStateId(data)));
/*     */         });
/*     */     
/* 177 */     filter().type((EntityType)Entity1_17Types.SHULKER).removeIndex(17);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int type) {
/* 182 */     return Entity1_17Types.getTypeFromId(type);
/*     */   }
/*     */   
/*     */   private static void addNewDimensionData(CompoundTag tag) {
/* 186 */     tag.put("min_y", (Tag)new IntTag(0));
/* 187 */     tag.put("height", (Tag)new IntTag(256));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_17to1_16_4\packets\EntityPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.metadata.MetadataRewriter1_14To1_13_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import java.util.LinkedList;
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
/*     */ public class EntityPackets
/*     */ {
/*     */   public static void register(final Protocol1_14To1_13_2 protocol) {
/*  42 */     final MetadataRewriter1_14To1_13_2 metadataRewriter = (MetadataRewriter1_14To1_13_2)protocol.get(MetadataRewriter1_14To1_13_2.class);
/*     */     
/*  44 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  47 */             map((Type)Type.VAR_INT);
/*  48 */             map(Type.UUID);
/*  49 */             map((Type)Type.BYTE, (Type)Type.VAR_INT);
/*  50 */             map((Type)Type.DOUBLE);
/*  51 */             map((Type)Type.DOUBLE);
/*  52 */             map((Type)Type.DOUBLE);
/*  53 */             map((Type)Type.BYTE);
/*  54 */             map((Type)Type.BYTE);
/*  55 */             map((Type)Type.INT);
/*  56 */             map((Type)Type.SHORT);
/*  57 */             map((Type)Type.SHORT);
/*  58 */             map((Type)Type.SHORT);
/*     */ 
/*     */             
/*  61 */             handler(wrapper -> {
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   int typeId = ((Integer)wrapper.get((Type)Type.VAR_INT, 1)).intValue();
/*     */                   Entity1_13Types.EntityType type1_13 = Entity1_13Types.getTypeFromId(typeId, true);
/*     */                   typeId = metadataRewriter.newEntityId(type1_13.getId());
/*     */                   EntityType type1_14 = Entity1_14Types.getTypeFromId(typeId);
/*     */                   if (type1_14 != null) {
/*     */                     int data = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                     if (type1_14.is((EntityType)Entity1_14Types.FALLING_BLOCK)) {
/*     */                       wrapper.set((Type)Type.INT, 0, Integer.valueOf(protocol.getMappingData().getNewBlockStateId(data)));
/*     */                     } else if (type1_14.is((EntityType)Entity1_14Types.MINECART)) {
/*     */                       switch (data) {
/*     */                         case 1:
/*     */                           typeId = Entity1_14Types.CHEST_MINECART.getId();
/*     */                           break;
/*     */ 
/*     */                         
/*     */                         case 2:
/*     */                           typeId = Entity1_14Types.FURNACE_MINECART.getId();
/*     */                           break;
/*     */ 
/*     */                         
/*     */                         case 3:
/*     */                           typeId = Entity1_14Types.TNT_MINECART.getId();
/*     */                           break;
/*     */ 
/*     */                         
/*     */                         case 4:
/*     */                           typeId = Entity1_14Types.SPAWNER_MINECART.getId();
/*     */                           break;
/*     */                         
/*     */                         case 5:
/*     */                           typeId = Entity1_14Types.HOPPER_MINECART.getId();
/*     */                           break;
/*     */                         
/*     */                         case 6:
/*     */                           typeId = Entity1_14Types.COMMAND_BLOCK_MINECART.getId();
/*     */                           break;
/*     */                       } 
/*     */                     
/*     */                     } else if ((type1_14.is((EntityType)Entity1_14Types.ITEM) && data > 0) || type1_14.isOrHasParent((EntityType)Entity1_14Types.ABSTRACT_ARROW)) {
/*     */                       if (type1_14.isOrHasParent((EntityType)Entity1_14Types.ABSTRACT_ARROW)) {
/*     */                         wrapper.set((Type)Type.INT, 0, Integer.valueOf(data - 1));
/*     */                       }
/*     */                       PacketWrapper velocity = wrapper.create(69);
/*     */                       velocity.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/*     */                       velocity.write((Type)Type.SHORT, wrapper.get((Type)Type.SHORT, 0));
/*     */                       velocity.write((Type)Type.SHORT, wrapper.get((Type)Type.SHORT, 1));
/*     */                       velocity.write((Type)Type.SHORT, wrapper.get((Type)Type.SHORT, 2));
/*     */                       velocity.scheduleSend(Protocol1_14To1_13_2.class);
/*     */                     } 
/*     */                     wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class).addEntity(entityId, type1_14);
/*     */                   } 
/*     */                   wrapper.set((Type)Type.VAR_INT, 1, Integer.valueOf(typeId));
/*     */                 });
/*     */           }
/*     */         });
/* 118 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 121 */             map((Type)Type.VAR_INT);
/* 122 */             map(Type.UUID);
/* 123 */             map((Type)Type.VAR_INT);
/* 124 */             map((Type)Type.DOUBLE);
/* 125 */             map((Type)Type.DOUBLE);
/* 126 */             map((Type)Type.DOUBLE);
/* 127 */             map((Type)Type.BYTE);
/* 128 */             map((Type)Type.BYTE);
/* 129 */             map((Type)Type.BYTE);
/* 130 */             map((Type)Type.SHORT);
/* 131 */             map((Type)Type.SHORT);
/* 132 */             map((Type)Type.SHORT);
/* 133 */             map(Types1_13_2.METADATA_LIST, Types1_14.METADATA_LIST);
/*     */             
/* 135 */             handler(metadataRewriter.trackerAndRewriterHandler(Types1_14.METADATA_LIST));
/*     */           }
/*     */         });
/*     */     
/* 139 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_PAINTING, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 142 */             map((Type)Type.VAR_INT);
/* 143 */             map(Type.UUID);
/* 144 */             map((Type)Type.VAR_INT);
/* 145 */             map(Type.POSITION, Type.POSITION1_14);
/* 146 */             map((Type)Type.BYTE);
/*     */           }
/*     */         });
/*     */     
/* 150 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_PLAYER, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 153 */             map((Type)Type.VAR_INT);
/* 154 */             map(Type.UUID);
/* 155 */             map((Type)Type.DOUBLE);
/* 156 */             map((Type)Type.DOUBLE);
/* 157 */             map((Type)Type.DOUBLE);
/* 158 */             map((Type)Type.BYTE);
/* 159 */             map((Type)Type.BYTE);
/* 160 */             map(Types1_13_2.METADATA_LIST, Types1_14.METADATA_LIST);
/*     */             
/* 162 */             handler(metadataRewriter.trackerAndRewriterHandler(Types1_14.METADATA_LIST, (EntityType)Entity1_14Types.PLAYER));
/*     */           }
/*     */         });
/*     */     
/* 166 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.ENTITY_ANIMATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 169 */             map((Type)Type.VAR_INT);
/* 170 */             handler(wrapper -> {
/*     */                   short animation = ((Short)wrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                   
/*     */                   if (animation == 2) {
/*     */                     EntityTracker1_14 tracker = (EntityTracker1_14)wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
/*     */                     
/*     */                     int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                     tracker.setSleeping(entityId, false);
/*     */                     PacketWrapper metadataPacket = wrapper.create((PacketType)ClientboundPackets1_14.ENTITY_METADATA);
/*     */                     metadataPacket.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/*     */                     List<Metadata> metadataList = new LinkedList<>();
/*     */                     if (tracker.clientEntityId() != entityId) {
/*     */                       metadataList.add(new Metadata(6, Types1_14.META_TYPES.poseType, Integer.valueOf(MetadataRewriter1_14To1_13_2.recalculatePlayerPose(entityId, tracker))));
/*     */                     }
/*     */                     metadataList.add(new Metadata(12, Types1_14.META_TYPES.optionalPositionType, null));
/*     */                     metadataPacket.write(Types1_14.METADATA_LIST, metadataList);
/*     */                     metadataPacket.scheduleSend(Protocol1_14To1_13_2.class);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 191 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 194 */             map((Type)Type.INT);
/* 195 */             map((Type)Type.UNSIGNED_BYTE);
/* 196 */             map((Type)Type.INT);
/* 197 */             handler(wrapper -> {
/*     */                   ClientWorld clientChunks = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   
/*     */                   int dimensionId = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   clientChunks.setEnvironment(dimensionId);
/*     */                 });
/* 203 */             handler(metadataRewriter.playerTrackerHandler());
/* 204 */             handler(wrapper -> {
/*     */                   short difficulty = ((Short)wrapper.read((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                   
/*     */                   PacketWrapper difficultyPacket = wrapper.create((PacketType)ClientboundPackets1_14.SERVER_DIFFICULTY);
/*     */                   
/*     */                   difficultyPacket.write((Type)Type.UNSIGNED_BYTE, Short.valueOf(difficulty));
/*     */                   difficultyPacket.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                   difficultyPacket.scheduleSend(protocol.getClass());
/*     */                   wrapper.passthrough((Type)Type.UNSIGNED_BYTE);
/*     */                   wrapper.passthrough(Type.STRING);
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(64));
/*     */                 });
/* 216 */             handler(wrapper -> {
/*     */                   wrapper.send(Protocol1_14To1_13_2.class);
/*     */ 
/*     */                   
/*     */                   wrapper.cancel();
/*     */                   
/*     */                   WorldPackets.sendViewDistancePacket(wrapper.user());
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 227 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.USE_BED, (ClientboundPacketType)ClientboundPackets1_14.ENTITY_METADATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 230 */             map((Type)Type.VAR_INT);
/* 231 */             handler(wrapper -> {
/*     */                   EntityTracker1_14 tracker = (EntityTracker1_14)wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
/*     */                   
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   tracker.setSleeping(entityId, true);
/*     */                   Position position = (Position)wrapper.read(Type.POSITION);
/*     */                   List<Metadata> metadataList = new LinkedList<>();
/*     */                   metadataList.add(new Metadata(12, Types1_14.META_TYPES.optionalPositionType, position));
/*     */                   if (tracker.clientEntityId() != entityId) {
/*     */                     metadataList.add(new Metadata(6, Types1_14.META_TYPES.poseType, Integer.valueOf(MetadataRewriter1_14To1_13_2.recalculatePlayerPose(entityId, tracker))));
/*     */                   }
/*     */                   wrapper.write(Types1_14.METADATA_LIST, metadataList);
/*     */                 });
/*     */           }
/*     */         });
/* 247 */     metadataRewriter.registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_13.DESTROY_ENTITIES);
/* 248 */     metadataRewriter.registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_13.ENTITY_METADATA, Types1_13_2.METADATA_LIST, Types1_14.METADATA_LIST);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_14to1_13_2\packets\EntityPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
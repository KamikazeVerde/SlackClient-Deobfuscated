/*     */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_12;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.metadata.MetadataRewriter1_13To1_12_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
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
/*     */   public static void register(Protocol1_13To1_12_2 protocol) {
/*  34 */     final MetadataRewriter1_13To1_12_2 metadataRewriter = (MetadataRewriter1_13To1_12_2)protocol.get(MetadataRewriter1_13To1_12_2.class);
/*     */     
/*  36 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.SPAWN_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  39 */             map((Type)Type.VAR_INT);
/*  40 */             map(Type.UUID);
/*  41 */             map((Type)Type.BYTE);
/*  42 */             map((Type)Type.DOUBLE);
/*  43 */             map((Type)Type.DOUBLE);
/*  44 */             map((Type)Type.DOUBLE);
/*  45 */             map((Type)Type.BYTE);
/*  46 */             map((Type)Type.BYTE);
/*  47 */             map((Type)Type.INT);
/*     */ 
/*     */             
/*  50 */             handler(wrapper -> {
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   byte type = ((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   Entity1_13Types.EntityType entType = Entity1_13Types.getTypeFromId(type, true);
/*     */                   if (entType == null) {
/*     */                     return;
/*     */                   }
/*     */                   wrapper.user().getEntityTracker(Protocol1_13To1_12_2.class).addEntity(entityId, (EntityType)entType);
/*     */                   if (entType.is((EntityType)Entity1_13Types.EntityType.FALLING_BLOCK)) {
/*     */                     int oldId = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                     int combined = (oldId & 0xFFF) << 4 | oldId >> 12 & 0xF;
/*     */                     wrapper.set((Type)Type.INT, 0, Integer.valueOf(WorldPackets.toNewId(combined)));
/*     */                   } 
/*     */                   if (entType.is((EntityType)Entity1_13Types.EntityType.ITEM_FRAME)) {
/*     */                     int data = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                     switch (data) {
/*     */                       case 0:
/*     */                         data = 3;
/*     */                         break;
/*     */ 
/*     */ 
/*     */ 
/*     */                       
/*     */                       case 1:
/*     */                         data = 4;
/*     */                         break;
/*     */ 
/*     */ 
/*     */                       
/*     */                       case 3:
/*     */                         data = 5;
/*     */                         break;
/*     */                     } 
/*     */ 
/*     */ 
/*     */                     
/*     */                     wrapper.set((Type)Type.INT, 0, Integer.valueOf(data));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  91 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  94 */             map((Type)Type.VAR_INT);
/*  95 */             map(Type.UUID);
/*  96 */             map((Type)Type.VAR_INT);
/*  97 */             map((Type)Type.DOUBLE);
/*  98 */             map((Type)Type.DOUBLE);
/*  99 */             map((Type)Type.DOUBLE);
/* 100 */             map((Type)Type.BYTE);
/* 101 */             map((Type)Type.BYTE);
/* 102 */             map((Type)Type.BYTE);
/* 103 */             map((Type)Type.SHORT);
/* 104 */             map((Type)Type.SHORT);
/* 105 */             map((Type)Type.SHORT);
/* 106 */             map(Types1_12.METADATA_LIST, Types1_13.METADATA_LIST);
/*     */             
/* 108 */             handler(metadataRewriter.trackerAndRewriterHandler(Types1_13.METADATA_LIST));
/*     */           }
/*     */         });
/*     */     
/* 112 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.SPAWN_PLAYER, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 115 */             map((Type)Type.VAR_INT);
/* 116 */             map(Type.UUID);
/* 117 */             map((Type)Type.DOUBLE);
/* 118 */             map((Type)Type.DOUBLE);
/* 119 */             map((Type)Type.DOUBLE);
/* 120 */             map((Type)Type.BYTE);
/* 121 */             map((Type)Type.BYTE);
/* 122 */             map(Types1_12.METADATA_LIST, Types1_13.METADATA_LIST);
/*     */             
/* 124 */             handler(metadataRewriter.trackerAndRewriterHandler(Types1_13.METADATA_LIST, (EntityType)Entity1_13Types.EntityType.PLAYER));
/*     */           }
/*     */         });
/*     */     
/* 128 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 131 */             map((Type)Type.INT);
/* 132 */             map((Type)Type.UNSIGNED_BYTE);
/* 133 */             map((Type)Type.INT);
/*     */             
/* 135 */             handler(wrapper -> {
/*     */                   ClientWorld clientChunks = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   int dimensionId = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   clientChunks.setEnvironment(dimensionId);
/*     */                 });
/* 140 */             handler(metadataRewriter.playerTrackerHandler());
/* 141 */             handler(Protocol1_13To1_12_2.SEND_DECLARE_COMMANDS_AND_TAGS);
/*     */           }
/*     */         });
/*     */     
/* 145 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.ENTITY_EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 148 */             map((Type)Type.VAR_INT);
/* 149 */             map((Type)Type.BYTE);
/* 150 */             map((Type)Type.BYTE);
/* 151 */             map((Type)Type.VAR_INT);
/*     */             
/* 153 */             handler(packetWrapper -> {
/*     */                   byte flags = ((Byte)packetWrapper.read((Type)Type.BYTE)).byteValue();
/*     */                   
/*     */                   if (Via.getConfig().isNewEffectIndicator()) {
/*     */                     flags = (byte)(flags | 0x4);
/*     */                   }
/*     */                   
/*     */                   packetWrapper.write((Type)Type.BYTE, Byte.valueOf(flags));
/*     */                 });
/*     */           }
/*     */         });
/* 164 */     metadataRewriter.registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_12_1.DESTROY_ENTITIES);
/* 165 */     metadataRewriter.registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_12_1.ENTITY_METADATA, Types1_12.METADATA_LIST, Types1_13.METADATA_LIST);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\packets\EntityPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
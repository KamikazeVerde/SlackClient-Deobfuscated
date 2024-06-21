/*     */ package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
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
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
/*     */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
/*     */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.metadata.MetadataRewriter1_15To1_14_4;
/*     */ import com.viaversion.viaversion.rewriter.EntityRewriter;
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
/*     */ public class EntityPackets
/*     */ {
/*     */   public static void register(Protocol1_15To1_14_4 protocol) {
/*  37 */     final MetadataRewriter1_15To1_14_4 metadataRewriter = (MetadataRewriter1_15To1_14_4)protocol.get(MetadataRewriter1_15To1_14_4.class);
/*     */     
/*  39 */     metadataRewriter.registerTrackerWithData((ClientboundPacketType)ClientboundPackets1_14.SPAWN_ENTITY, (EntityType)Entity1_15Types.FALLING_BLOCK);
/*     */     
/*  41 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_14.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  44 */             map((Type)Type.VAR_INT);
/*  45 */             map(Type.UUID);
/*  46 */             map((Type)Type.VAR_INT);
/*  47 */             map((Type)Type.DOUBLE);
/*  48 */             map((Type)Type.DOUBLE);
/*  49 */             map((Type)Type.DOUBLE);
/*  50 */             map((Type)Type.BYTE);
/*  51 */             map((Type)Type.BYTE);
/*  52 */             map((Type)Type.BYTE);
/*  53 */             map((Type)Type.SHORT);
/*  54 */             map((Type)Type.SHORT);
/*  55 */             map((Type)Type.SHORT);
/*     */             
/*  57 */             handler(metadataRewriter.trackerHandler());
/*  58 */             handler(wrapper -> EntityPackets.sendMetadataPacket(wrapper, ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue(), (EntityRewriter<?, ?>)metadataRewriter));
/*     */           }
/*     */         });
/*     */     
/*  62 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_14.SPAWN_PLAYER, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  65 */             map((Type)Type.VAR_INT);
/*  66 */             map(Type.UUID);
/*  67 */             map((Type)Type.DOUBLE);
/*  68 */             map((Type)Type.DOUBLE);
/*  69 */             map((Type)Type.DOUBLE);
/*  70 */             map((Type)Type.BYTE);
/*  71 */             map((Type)Type.BYTE);
/*     */             
/*  73 */             handler(wrapper -> {
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   wrapper.user().getEntityTracker(Protocol1_15To1_14_4.class).addEntity(entityId, (EntityType)Entity1_15Types.PLAYER);
/*     */                   
/*     */                   EntityPackets.sendMetadataPacket(wrapper, entityId, (EntityRewriter<?, ?>)metadataRewriter);
/*     */                 });
/*     */           }
/*     */         });
/*  82 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_14.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  85 */             map((Type)Type.INT);
/*  86 */             handler(wrapper -> wrapper.write((Type)Type.LONG, Long.valueOf(0L)));
/*     */           }
/*     */         });
/*     */     
/*  90 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_14.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  93 */             map((Type)Type.INT);
/*  94 */             map((Type)Type.UNSIGNED_BYTE);
/*  95 */             map((Type)Type.INT);
/*  96 */             handler(metadataRewriter.playerTrackerHandler());
/*  97 */             handler(wrapper -> wrapper.write((Type)Type.LONG, Long.valueOf(0L)));
/*     */             
/*  99 */             map((Type)Type.UNSIGNED_BYTE);
/* 100 */             map(Type.STRING);
/* 101 */             map((Type)Type.VAR_INT);
/* 102 */             map((Type)Type.BOOLEAN);
/*     */             
/* 104 */             handler(wrapper -> wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(!Via.getConfig().is1_15InstantRespawn())));
/*     */           }
/*     */         });
/*     */     
/* 108 */     metadataRewriter.registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_14.ENTITY_METADATA, Types1_14.METADATA_LIST);
/* 109 */     metadataRewriter.registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_14.DESTROY_ENTITIES);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void sendMetadataPacket(PacketWrapper wrapper, int entityId, EntityRewriter<?, ?> rewriter) throws Exception {
/* 114 */     List<Metadata> metadata = (List<Metadata>)wrapper.read(Types1_14.METADATA_LIST);
/* 115 */     if (metadata.isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 120 */     wrapper.send(Protocol1_15To1_14_4.class);
/* 121 */     wrapper.cancel();
/*     */ 
/*     */     
/* 124 */     rewriter.handleMetadata(entityId, metadata, wrapper.user());
/*     */     
/* 126 */     PacketWrapper metadataPacket = PacketWrapper.create((PacketType)ClientboundPackets1_15.ENTITY_METADATA, wrapper.user());
/* 127 */     metadataPacket.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/* 128 */     metadataPacket.write(Types1_14.METADATA_LIST, metadata);
/* 129 */     metadataPacket.send(Protocol1_15To1_14_4.class);
/*     */   }
/*     */   
/*     */   public static int getNewEntityId(int oldId) {
/* 133 */     return (oldId >= 4) ? (oldId + 1) : oldId;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_15to1_14_4\packets\EntityPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
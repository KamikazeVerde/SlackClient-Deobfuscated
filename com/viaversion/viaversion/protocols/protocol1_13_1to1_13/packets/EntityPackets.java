/*     */ package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.metadata.MetadataRewriter1_13_1To1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
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
/*     */   public static void register(final Protocol1_13_1To1_13 protocol) {
/*  31 */     final MetadataRewriter1_13_1To1_13 metadataRewriter = (MetadataRewriter1_13_1To1_13)protocol.get(MetadataRewriter1_13_1To1_13.class);
/*     */     
/*  33 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  36 */             map((Type)Type.VAR_INT);
/*  37 */             map(Type.UUID);
/*  38 */             map((Type)Type.BYTE);
/*  39 */             map((Type)Type.DOUBLE);
/*  40 */             map((Type)Type.DOUBLE);
/*  41 */             map((Type)Type.DOUBLE);
/*  42 */             map((Type)Type.BYTE);
/*  43 */             map((Type)Type.BYTE);
/*  44 */             map((Type)Type.INT);
/*     */ 
/*     */             
/*  47 */             handler(wrapper -> {
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   byte type = ((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   
/*     */                   Entity1_13Types.EntityType entType = Entity1_13Types.getTypeFromId(type, true);
/*     */                   
/*     */                   if (entType != null) {
/*     */                     if (entType.is((EntityType)Entity1_13Types.EntityType.FALLING_BLOCK)) {
/*     */                       int data = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                       wrapper.set((Type)Type.INT, 0, Integer.valueOf(protocol.getMappingData().getNewBlockStateId(data)));
/*     */                     } 
/*     */                     wrapper.user().getEntityTracker(Protocol1_13_1To1_13.class).addEntity(entityId, (EntityType)entType);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  64 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  67 */             map((Type)Type.VAR_INT);
/*  68 */             map(Type.UUID);
/*  69 */             map((Type)Type.VAR_INT);
/*  70 */             map((Type)Type.DOUBLE);
/*  71 */             map((Type)Type.DOUBLE);
/*  72 */             map((Type)Type.DOUBLE);
/*  73 */             map((Type)Type.BYTE);
/*  74 */             map((Type)Type.BYTE);
/*  75 */             map((Type)Type.BYTE);
/*  76 */             map((Type)Type.SHORT);
/*  77 */             map((Type)Type.SHORT);
/*  78 */             map((Type)Type.SHORT);
/*  79 */             map(Types1_13.METADATA_LIST);
/*     */             
/*  81 */             handler(metadataRewriter.trackerAndRewriterHandler(Types1_13.METADATA_LIST));
/*     */           }
/*     */         });
/*     */     
/*  85 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_PLAYER, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  88 */             map((Type)Type.VAR_INT);
/*  89 */             map(Type.UUID);
/*  90 */             map((Type)Type.DOUBLE);
/*  91 */             map((Type)Type.DOUBLE);
/*  92 */             map((Type)Type.DOUBLE);
/*  93 */             map((Type)Type.BYTE);
/*  94 */             map((Type)Type.BYTE);
/*  95 */             map(Types1_13.METADATA_LIST);
/*     */             
/*  97 */             handler(metadataRewriter.trackerAndRewriterHandler(Types1_13.METADATA_LIST, (EntityType)Entity1_13Types.EntityType.PLAYER));
/*     */           }
/*     */         });
/*     */     
/* 101 */     metadataRewriter.registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_13.DESTROY_ENTITIES);
/* 102 */     metadataRewriter.registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_13.ENTITY_METADATA, Types1_13.METADATA_LIST);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13_1to1_13\packets\EntityPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
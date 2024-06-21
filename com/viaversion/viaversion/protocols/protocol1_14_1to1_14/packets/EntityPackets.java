/*    */ package com.viaversion.viaversion.protocols.protocol1_14_1to1_14.packets;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.api.type.types.version.Types1_14;
/*    */ import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.Protocol1_14_1To1_14;
/*    */ import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.metadata.MetadataRewriter1_14_1To1_14;
/*    */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityPackets
/*    */ {
/*    */   public static void register(Protocol1_14_1To1_14 protocol) {
/* 31 */     final MetadataRewriter1_14_1To1_14 metadataRewriter = (MetadataRewriter1_14_1To1_14)protocol.get(MetadataRewriter1_14_1To1_14.class);
/*    */     
/* 33 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_14.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 36 */             map((Type)Type.VAR_INT);
/* 37 */             map(Type.UUID);
/* 38 */             map((Type)Type.VAR_INT);
/* 39 */             map((Type)Type.DOUBLE);
/* 40 */             map((Type)Type.DOUBLE);
/* 41 */             map((Type)Type.DOUBLE);
/* 42 */             map((Type)Type.BYTE);
/* 43 */             map((Type)Type.BYTE);
/* 44 */             map((Type)Type.BYTE);
/* 45 */             map((Type)Type.SHORT);
/* 46 */             map((Type)Type.SHORT);
/* 47 */             map((Type)Type.SHORT);
/* 48 */             map(Types1_14.METADATA_LIST);
/*    */             
/* 50 */             handler(metadataRewriter.trackerAndRewriterHandler(Types1_14.METADATA_LIST));
/*    */           }
/*    */         });
/*    */     
/* 54 */     metadataRewriter.registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_14.DESTROY_ENTITIES);
/*    */     
/* 56 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_14.SPAWN_PLAYER, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 59 */             map((Type)Type.VAR_INT);
/* 60 */             map(Type.UUID);
/* 61 */             map((Type)Type.DOUBLE);
/* 62 */             map((Type)Type.DOUBLE);
/* 63 */             map((Type)Type.DOUBLE);
/* 64 */             map((Type)Type.BYTE);
/* 65 */             map((Type)Type.BYTE);
/* 66 */             map(Types1_14.METADATA_LIST);
/*    */             
/* 68 */             handler(metadataRewriter.trackerAndRewriterHandler(Types1_14.METADATA_LIST, (EntityType)Entity1_14Types.PLAYER));
/*    */           }
/*    */         });
/*    */     
/* 72 */     metadataRewriter.registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_14.ENTITY_METADATA, Types1_14.METADATA_LIST);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_14_1to1_14\packets\EntityPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
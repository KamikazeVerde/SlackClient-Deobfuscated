/*    */ package com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets;
/*    */ 
/*    */ import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.Protocol1_13_1To1_13_2;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.api.type.types.version.Types1_13;
/*    */ import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
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
/*    */ 
/*    */ public class EntityPackets1_13_2
/*    */ {
/*    */   public static void register(Protocol1_13_1To1_13_2 protocol) {
/* 32 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 35 */             map((Type)Type.VAR_INT);
/* 36 */             map(Type.UUID);
/* 37 */             map((Type)Type.VAR_INT);
/* 38 */             map((Type)Type.DOUBLE);
/* 39 */             map((Type)Type.DOUBLE);
/* 40 */             map((Type)Type.DOUBLE);
/* 41 */             map((Type)Type.BYTE);
/* 42 */             map((Type)Type.BYTE);
/* 43 */             map((Type)Type.BYTE);
/* 44 */             map((Type)Type.SHORT);
/* 45 */             map((Type)Type.SHORT);
/* 46 */             map((Type)Type.SHORT);
/* 47 */             map(Types1_13_2.METADATA_LIST, Types1_13.METADATA_LIST);
/*    */             
/* 49 */             handler(wrapper -> {
/*    */                   for (Metadata metadata : wrapper.get(Types1_13.METADATA_LIST, 0)) {
/*    */                     metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId()));
/*    */                   }
/*    */                 });
/*    */           }
/*    */         });
/*    */     
/* 57 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_PLAYER, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 60 */             map((Type)Type.VAR_INT);
/* 61 */             map(Type.UUID);
/* 62 */             map((Type)Type.DOUBLE);
/* 63 */             map((Type)Type.DOUBLE);
/* 64 */             map((Type)Type.DOUBLE);
/* 65 */             map((Type)Type.BYTE);
/* 66 */             map((Type)Type.BYTE);
/* 67 */             map(Types1_13_2.METADATA_LIST, Types1_13.METADATA_LIST);
/*    */             
/* 69 */             handler(wrapper -> {
/*    */                   for (Metadata metadata : wrapper.get(Types1_13.METADATA_LIST, 0)) {
/*    */                     metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId()));
/*    */                   }
/*    */                 });
/*    */           }
/*    */         });
/*    */     
/* 77 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.ENTITY_METADATA, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 80 */             map((Type)Type.VAR_INT);
/* 81 */             map(Types1_13_2.METADATA_LIST, Types1_13.METADATA_LIST);
/*    */             
/* 83 */             handler(wrapper -> {
/*    */                   for (Metadata metadata : wrapper.get(Types1_13.METADATA_LIST, 0))
/*    */                     metadata.setMetaType(Types1_13.META_TYPES.byId(metadata.metaType().typeId())); 
/*    */                 });
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_13_1to1_13_2\packets\EntityPackets1_13_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
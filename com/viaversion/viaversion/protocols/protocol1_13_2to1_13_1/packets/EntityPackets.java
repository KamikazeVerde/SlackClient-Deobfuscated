/*    */ package com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
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
/*    */ public class EntityPackets
/*    */ {
/*    */   public static void register(Protocol protocol) {
/* 32 */     final PacketHandler metaTypeHandler = wrapper -> {
/*    */         for (Metadata metadata : wrapper.get(Types1_13_2.METADATA_LIST, 0)) {
/*    */           metadata.setMetaType(Types1_13_2.META_TYPES.byId(metadata.metaType().typeId()));
/*    */         }
/*    */       };
/*    */     
/* 38 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 41 */             map((Type)Type.VAR_INT);
/* 42 */             map(Type.UUID);
/* 43 */             map((Type)Type.VAR_INT);
/* 44 */             map((Type)Type.DOUBLE);
/* 45 */             map((Type)Type.DOUBLE);
/* 46 */             map((Type)Type.DOUBLE);
/* 47 */             map((Type)Type.BYTE);
/* 48 */             map((Type)Type.BYTE);
/* 49 */             map((Type)Type.BYTE);
/* 50 */             map((Type)Type.SHORT);
/* 51 */             map((Type)Type.SHORT);
/* 52 */             map((Type)Type.SHORT);
/* 53 */             map(Types1_13.METADATA_LIST, Types1_13_2.METADATA_LIST);
/*    */             
/* 55 */             handler(metaTypeHandler);
/*    */           }
/*    */         });
/*    */     
/* 59 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SPAWN_PLAYER, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 62 */             map((Type)Type.VAR_INT);
/* 63 */             map(Type.UUID);
/* 64 */             map((Type)Type.DOUBLE);
/* 65 */             map((Type)Type.DOUBLE);
/* 66 */             map((Type)Type.DOUBLE);
/* 67 */             map((Type)Type.BYTE);
/* 68 */             map((Type)Type.BYTE);
/* 69 */             map(Types1_13.METADATA_LIST, Types1_13_2.METADATA_LIST);
/*    */             
/* 71 */             handler(metaTypeHandler);
/*    */           }
/*    */         });
/*    */     
/* 75 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.ENTITY_METADATA, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 78 */             map((Type)Type.VAR_INT);
/* 79 */             map(Types1_13.METADATA_LIST, Types1_13_2.METADATA_LIST);
/*    */             
/* 81 */             handler(metaTypeHandler);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13_2to1_13_1\packets\EntityPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
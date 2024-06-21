/*    */ package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.api.type.types.version.Types1_16;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
/*    */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.metadata.MetadataRewriter1_16_2To1_16_1;
/*    */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
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
/*    */   public static void register(final Protocol1_16_2To1_16_1 protocol) {
/* 33 */     final MetadataRewriter1_16_2To1_16_1 metadataRewriter = (MetadataRewriter1_16_2To1_16_1)protocol.get(MetadataRewriter1_16_2To1_16_1.class);
/* 34 */     metadataRewriter.registerTrackerWithData((ClientboundPacketType)ClientboundPackets1_16.SPAWN_ENTITY, (EntityType)Entity1_16_2Types.FALLING_BLOCK);
/* 35 */     metadataRewriter.registerTracker((ClientboundPacketType)ClientboundPackets1_16.SPAWN_MOB);
/* 36 */     metadataRewriter.registerTracker((ClientboundPacketType)ClientboundPackets1_16.SPAWN_PLAYER, (EntityType)Entity1_16_2Types.PLAYER);
/* 37 */     metadataRewriter.registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_16.ENTITY_METADATA, Types1_16.METADATA_LIST);
/* 38 */     metadataRewriter.registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_16.DESTROY_ENTITIES);
/*    */     
/* 40 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_16.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 43 */             map((Type)Type.INT);
/* 44 */             handler(wrapper -> {
/*    */                   short gamemode = ((Short)wrapper.read((Type)Type.UNSIGNED_BYTE)).shortValue();
/*    */                   
/*    */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(((gamemode & 0x8) != 0)));
/*    */                   gamemode = (short)(gamemode & 0xFFFFFFF7);
/*    */                   wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf(gamemode));
/*    */                 });
/* 51 */             map((Type)Type.BYTE);
/* 52 */             map(Type.STRING_ARRAY);
/* 53 */             handler(wrapper -> {
/*    */                   wrapper.read(Type.NBT);
/*    */                   
/*    */                   wrapper.write(Type.NBT, protocol.getMappingData().getDimensionRegistry());
/*    */                   
/*    */                   String dimensionType = (String)wrapper.read(Type.STRING);
/*    */                   
/*    */                   wrapper.write(Type.NBT, EntityPackets.getDimensionData(dimensionType));
/*    */                 });
/* 62 */             map(Type.STRING);
/* 63 */             map((Type)Type.LONG);
/* 64 */             map((Type)Type.UNSIGNED_BYTE, (Type)Type.VAR_INT);
/*    */             
/* 66 */             handler(metadataRewriter.playerTrackerHandler());
/*    */           }
/*    */         });
/*    */     
/* 70 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_16.RESPAWN, wrapper -> {
/*    */           String dimensionType = (String)wrapper.read(Type.STRING);
/*    */           wrapper.write(Type.NBT, getDimensionData(dimensionType));
/*    */         });
/*    */   }
/*    */   
/*    */   public static CompoundTag getDimensionData(String dimensionType) {
/* 77 */     CompoundTag tag = (CompoundTag)Protocol1_16_2To1_16_1.MAPPINGS.getDimensionDataMap().get(dimensionType);
/* 78 */     if (tag == null) {
/* 79 */       Via.getPlatform().getLogger().severe("Could not get dimension data of " + dimensionType);
/* 80 */       throw new NullPointerException("Dimension data for " + dimensionType + " is null!");
/*    */     } 
/* 82 */     return tag.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_16_2to1_16_1\packets\EntityPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
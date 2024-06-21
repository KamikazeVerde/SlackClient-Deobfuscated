/*    */ package com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.packets;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*    */ import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.Protocol1_19To1_19_1;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_19Types;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.type.types.version.Types1_19;
/*    */ import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ClientboundPackets1_19_1;
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
/*    */ public final class EntityPackets1_19_1
/*    */   extends EntityRewriter<ClientboundPackets1_19_1, Protocol1_19To1_19_1>
/*    */ {
/*    */   public EntityPackets1_19_1(Protocol1_19To1_19_1 protocol) {
/* 30 */     super((BackwardsProtocol)protocol, Types1_19.META_TYPES.optionalComponentType, Types1_19.META_TYPES.booleanType);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 35 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_19_1.ENTITY_METADATA, Types1_19.METADATA_LIST);
/* 36 */     registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_19_1.REMOVE_ENTITIES);
/* 37 */     registerSpawnTracker((ClientboundPacketType)ClientboundPackets1_19_1.SPAWN_ENTITY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerRewrites() {
/* 42 */     filter().type((EntityType)Entity1_19Types.ALLAY).cancel(16);
/* 43 */     filter().type((EntityType)Entity1_19Types.ALLAY).cancel(17);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityType typeFromId(int typeId) {
/* 48 */     return Entity1_19Types.getTypeFromId(typeId);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_19to1_19_1\packets\EntityPackets1_19_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
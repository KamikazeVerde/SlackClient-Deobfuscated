/*     */ package com.viaversion.viabackwards.protocol.protocol1_14to1_14_1.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_14to1_14_1.Protocol1_14To1_14_1;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
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
/*     */ public class EntityPackets1_14_1
/*     */   extends LegacyEntityRewriter<ClientboundPackets1_14, Protocol1_14To1_14_1>
/*     */ {
/*     */   public EntityPackets1_14_1(Protocol1_14To1_14_1 protocol) {
/*  34 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  39 */     registerTracker((ClientboundPacketType)ClientboundPackets1_14.SPAWN_EXPERIENCE_ORB, (EntityType)Entity1_14Types.EXPERIENCE_ORB);
/*  40 */     registerTracker((ClientboundPacketType)ClientboundPackets1_14.SPAWN_GLOBAL_ENTITY, (EntityType)Entity1_14Types.LIGHTNING_BOLT);
/*  41 */     registerTracker((ClientboundPacketType)ClientboundPackets1_14.SPAWN_PAINTING, (EntityType)Entity1_14Types.PAINTING);
/*  42 */     registerTracker((ClientboundPacketType)ClientboundPackets1_14.SPAWN_PLAYER, (EntityType)Entity1_14Types.PLAYER);
/*  43 */     registerTracker((ClientboundPacketType)ClientboundPackets1_14.JOIN_GAME, (EntityType)Entity1_14Types.PLAYER, (Type)Type.INT);
/*  44 */     registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_14.DESTROY_ENTITIES);
/*     */     
/*  46 */     ((Protocol1_14To1_14_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.SPAWN_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  49 */             map((Type)Type.VAR_INT);
/*  50 */             map(Type.UUID);
/*  51 */             map((Type)Type.VAR_INT);
/*     */             
/*  53 */             handler(EntityPackets1_14_1.this.getTrackerHandler());
/*     */           }
/*     */         });
/*     */     
/*  57 */     ((Protocol1_14To1_14_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  60 */             map((Type)Type.VAR_INT);
/*  61 */             map(Type.UUID);
/*  62 */             map((Type)Type.VAR_INT);
/*  63 */             map((Type)Type.DOUBLE);
/*  64 */             map((Type)Type.DOUBLE);
/*  65 */             map((Type)Type.DOUBLE);
/*  66 */             map((Type)Type.BYTE);
/*  67 */             map((Type)Type.BYTE);
/*  68 */             map((Type)Type.BYTE);
/*  69 */             map((Type)Type.SHORT);
/*  70 */             map((Type)Type.SHORT);
/*  71 */             map((Type)Type.SHORT);
/*  72 */             map(Types1_14.METADATA_LIST);
/*     */             
/*  74 */             handler(wrapper -> {
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   int type = ((Integer)wrapper.get((Type)Type.VAR_INT, 1)).intValue();
/*     */                   
/*     */                   EntityPackets1_14_1.this.tracker(wrapper.user()).addEntity(entityId, Entity1_14Types.getTypeFromId(type));
/*     */                   
/*     */                   List<Metadata> metadata = (List<Metadata>)wrapper.get(Types1_14.METADATA_LIST, 0);
/*     */                   
/*     */                   EntityPackets1_14_1.this.handleMetadata(entityId, metadata, wrapper.user());
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/*  88 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_14.ENTITY_METADATA, Types1_14.METADATA_LIST);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerRewrites() {
/*  93 */     filter().type((EntityType)Entity1_14Types.VILLAGER).cancel(15);
/*  94 */     filter().type((EntityType)Entity1_14Types.VILLAGER).index(16).toIndex(15);
/*  95 */     filter().type((EntityType)Entity1_14Types.WANDERING_TRADER).cancel(15);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int typeId) {
/* 100 */     return Entity1_14Types.getTypeFromId(typeId);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_14to1_14_1\packets\EntityPackets1_14_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
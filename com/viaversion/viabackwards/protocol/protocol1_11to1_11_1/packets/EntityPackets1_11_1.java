/*     */ package com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.Protocol1_11To1_11_1;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_11Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.ObjectType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*     */ import java.util.function.Function;
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
/*     */ public class EntityPackets1_11_1
/*     */   extends LegacyEntityRewriter<ClientboundPackets1_9_3, Protocol1_11To1_11_1>
/*     */ {
/*     */   public EntityPackets1_11_1(Protocol1_11To1_11_1 protocol) {
/*  33 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  38 */     ((Protocol1_11To1_11_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.SPAWN_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  41 */             map((Type)Type.VAR_INT);
/*  42 */             map(Type.UUID);
/*  43 */             map((Type)Type.BYTE);
/*  44 */             map((Type)Type.DOUBLE);
/*  45 */             map((Type)Type.DOUBLE);
/*  46 */             map((Type)Type.DOUBLE);
/*  47 */             map((Type)Type.BYTE);
/*  48 */             map((Type)Type.BYTE);
/*  49 */             map((Type)Type.INT);
/*     */ 
/*     */             
/*  52 */             handler(EntityPackets1_11_1.this.getObjectTrackerHandler());
/*  53 */             handler(EntityPackets1_11_1.this.getObjectRewriter(id -> (ObjectType)Entity1_11Types.ObjectType.findById(id.byteValue()).orElse(null)));
/*     */           }
/*     */         });
/*     */     
/*  57 */     registerTracker((ClientboundPacketType)ClientboundPackets1_9_3.SPAWN_EXPERIENCE_ORB, (EntityType)Entity1_11Types.EntityType.EXPERIENCE_ORB);
/*  58 */     registerTracker((ClientboundPacketType)ClientboundPackets1_9_3.SPAWN_GLOBAL_ENTITY, (EntityType)Entity1_11Types.EntityType.WEATHER);
/*     */     
/*  60 */     ((Protocol1_11To1_11_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  63 */             map((Type)Type.VAR_INT);
/*  64 */             map(Type.UUID);
/*  65 */             map((Type)Type.VAR_INT);
/*  66 */             map((Type)Type.DOUBLE);
/*  67 */             map((Type)Type.DOUBLE);
/*  68 */             map((Type)Type.DOUBLE);
/*  69 */             map((Type)Type.BYTE);
/*  70 */             map((Type)Type.BYTE);
/*  71 */             map((Type)Type.BYTE);
/*  72 */             map((Type)Type.SHORT);
/*  73 */             map((Type)Type.SHORT);
/*  74 */             map((Type)Type.SHORT);
/*  75 */             map(Types1_9.METADATA_LIST);
/*     */ 
/*     */             
/*  78 */             handler(EntityPackets1_11_1.this.getTrackerHandler());
/*     */ 
/*     */             
/*  81 */             handler(EntityPackets1_11_1.this.getMobSpawnRewriter(Types1_9.METADATA_LIST));
/*     */           }
/*     */         });
/*     */     
/*  85 */     registerTracker((ClientboundPacketType)ClientboundPackets1_9_3.SPAWN_PAINTING, (EntityType)Entity1_11Types.EntityType.PAINTING);
/*  86 */     registerJoinGame((ClientboundPacketType)ClientboundPackets1_9_3.JOIN_GAME, (EntityType)Entity1_11Types.EntityType.PLAYER);
/*  87 */     registerRespawn((ClientboundPacketType)ClientboundPackets1_9_3.RESPAWN);
/*     */     
/*  89 */     ((Protocol1_11To1_11_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.SPAWN_PLAYER, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  92 */             map((Type)Type.VAR_INT);
/*  93 */             map(Type.UUID);
/*  94 */             map((Type)Type.DOUBLE);
/*  95 */             map((Type)Type.DOUBLE);
/*  96 */             map((Type)Type.DOUBLE);
/*  97 */             map((Type)Type.BYTE);
/*  98 */             map((Type)Type.BYTE);
/*  99 */             map(Types1_9.METADATA_LIST);
/*     */             
/* 101 */             handler(EntityPackets1_11_1.this.getTrackerAndMetaHandler(Types1_9.METADATA_LIST, (EntityType)Entity1_11Types.EntityType.PLAYER));
/*     */           }
/*     */         });
/*     */     
/* 105 */     registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_9_3.DESTROY_ENTITIES);
/* 106 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerRewrites() {
/* 112 */     filter().type((EntityType)Entity1_11Types.EntityType.FIREWORK).cancel(7);
/*     */ 
/*     */     
/* 115 */     filter().type((EntityType)Entity1_11Types.EntityType.PIG).cancel(14);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int typeId) {
/* 120 */     return (EntityType)Entity1_11Types.getTypeFromId(typeId, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected EntityType getObjectTypeFromId(int typeId) {
/* 125 */     return (EntityType)Entity1_11Types.getTypeFromId(typeId, true);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_11to1_11_1\packets\EntityPackets1_11_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
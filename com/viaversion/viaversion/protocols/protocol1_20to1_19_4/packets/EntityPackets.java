/*     */ package com.viaversion.viaversion.protocols.protocol1_20to1_19_4.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_4Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_20;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
/*     */ import com.viaversion.viaversion.protocols.protocol1_20to1_19_4.Protocol1_20To1_19_4;
/*     */ import com.viaversion.viaversion.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
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
/*     */ public final class EntityPackets
/*     */   extends EntityRewriter<ClientboundPackets1_19_4, Protocol1_20To1_19_4>
/*     */ {
/*     */   public EntityPackets(Protocol1_20To1_19_4 protocol) {
/*  39 */     super((Protocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerPackets() {
/*  44 */     registerTrackerWithData1_19((ClientboundPacketType)ClientboundPackets1_19_4.SPAWN_ENTITY, (EntityType)Entity1_19_4Types.FALLING_BLOCK);
/*  45 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_19_4.ENTITY_METADATA, Types1_19_4.METADATA_LIST, Types1_20.METADATA_LIST);
/*  46 */     registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_19_4.REMOVE_ENTITIES);
/*     */     
/*  48 */     ((Protocol1_20To1_19_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  51 */             map((Type)Type.INT);
/*  52 */             map((Type)Type.BOOLEAN);
/*  53 */             map((Type)Type.UNSIGNED_BYTE);
/*  54 */             map((Type)Type.BYTE);
/*  55 */             map(Type.STRING_ARRAY);
/*  56 */             map(Type.NBT);
/*  57 */             map(Type.STRING);
/*  58 */             map(Type.STRING);
/*  59 */             map((Type)Type.LONG);
/*  60 */             map((Type)Type.VAR_INT);
/*  61 */             map((Type)Type.VAR_INT);
/*  62 */             map((Type)Type.VAR_INT);
/*  63 */             map((Type)Type.BOOLEAN);
/*  64 */             map((Type)Type.BOOLEAN);
/*  65 */             map((Type)Type.BOOLEAN);
/*  66 */             map((Type)Type.BOOLEAN);
/*  67 */             map(Type.OPTIONAL_GLOBAL_POSITION);
/*  68 */             create((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */             
/*  70 */             handler(EntityPackets.this.dimensionDataHandler());
/*  71 */             handler(EntityPackets.this.biomeSizeTracker());
/*  72 */             handler(EntityPackets.this.worldDataTrackerHandlerByKey());
/*  73 */             handler(wrapper -> {
/*     */                   CompoundTag registry = (CompoundTag)wrapper.get(Type.NBT, 0);
/*     */                   
/*     */                   CompoundTag damageTypeRegistry = (CompoundTag)registry.get("minecraft:damage_type");
/*     */                   
/*     */                   ListTag damageTypes = (ListTag)damageTypeRegistry.get("value");
/*     */                   
/*     */                   int highestId = -1;
/*     */                   
/*     */                   for (Tag damageType : damageTypes) {
/*     */                     IntTag id = (IntTag)((CompoundTag)damageType).get("id");
/*     */                     highestId = Math.max(highestId, id.asInt());
/*     */                   } 
/*     */                   CompoundTag outsideBorderReason = new CompoundTag();
/*     */                   CompoundTag outsideBorderElement = new CompoundTag();
/*     */                   outsideBorderElement.put("scaling", (Tag)new StringTag("always"));
/*     */                   outsideBorderElement.put("exhaustion", (Tag)new FloatTag(0.0F));
/*     */                   outsideBorderElement.put("message_id", (Tag)new StringTag("badRespawnPoint"));
/*     */                   outsideBorderReason.put("id", (Tag)new IntTag(highestId + 1));
/*     */                   outsideBorderReason.put("name", (Tag)new StringTag("minecraft:outside_border"));
/*     */                   outsideBorderReason.put("element", (Tag)outsideBorderElement);
/*     */                   damageTypes.add((Tag)outsideBorderReason);
/*     */                   CompoundTag genericKillReason = new CompoundTag();
/*     */                   CompoundTag genericKillElement = new CompoundTag();
/*     */                   genericKillElement.put("scaling", (Tag)new StringTag("always"));
/*     */                   genericKillElement.put("exhaustion", (Tag)new FloatTag(0.0F));
/*     */                   genericKillElement.put("message_id", (Tag)new StringTag("badRespawnPoint"));
/*     */                   genericKillReason.put("id", (Tag)new IntTag(highestId + 2));
/*     */                   genericKillReason.put("name", (Tag)new StringTag("minecraft:generic_kill"));
/*     */                   genericKillReason.put("element", (Tag)genericKillElement);
/*     */                   damageTypes.add((Tag)genericKillReason);
/*     */                 });
/*     */           }
/*     */         });
/* 107 */     ((Protocol1_20To1_19_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 110 */             map(Type.STRING);
/* 111 */             map(Type.STRING);
/* 112 */             map((Type)Type.LONG);
/* 113 */             map((Type)Type.UNSIGNED_BYTE);
/* 114 */             map((Type)Type.BYTE);
/* 115 */             map((Type)Type.BOOLEAN);
/* 116 */             map((Type)Type.BOOLEAN);
/* 117 */             map((Type)Type.BYTE);
/* 118 */             map(Type.OPTIONAL_GLOBAL_POSITION);
/* 119 */             create((Type)Type.VAR_INT, Integer.valueOf(0));
/* 120 */             handler(EntityPackets.this.worldDataTrackerHandlerByKey());
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerRewrites() {
/* 127 */     filter().handler((event, meta) -> meta.setMetaType(Types1_20.META_TYPES.byId(meta.metaType().typeId())));
/* 128 */     registerMetaTypeHandler(Types1_20.META_TYPES.itemType, Types1_20.META_TYPES.blockStateType, Types1_20.META_TYPES.optionalBlockStateType, Types1_20.META_TYPES.particleType);
/*     */     
/* 130 */     filter().filterFamily((EntityType)Entity1_19_4Types.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
/*     */           int blockState = ((Integer)meta.value()).intValue();
/*     */           meta.setValue(Integer.valueOf(((Protocol1_20To1_19_4)this.protocol).getMappingData().getNewBlockStateId(blockState)));
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int type) {
/* 138 */     return Entity1_19_4Types.getTypeFromId(type);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_20to1_19_4\packets\EntityPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
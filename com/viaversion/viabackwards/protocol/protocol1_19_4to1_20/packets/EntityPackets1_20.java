/*     */ package com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.packets;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.Protocol1_19_4To1_20;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_4Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_20;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
/*     */ import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
/*     */ import com.viaversion.viaversion.util.Key;
/*     */ import java.util.Set;
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
/*     */ public final class EntityPackets1_20
/*     */   extends EntityRewriter<ClientboundPackets1_19_4, Protocol1_19_4To1_20>
/*     */ {
/*  39 */   private final Set<String> newTrimPatterns = Sets.newHashSet((Object[])new String[] { "host_armor_trim_smithing_template", "raiser_armor_trim_smithing_template", "silence_armor_trim_smithing_template", "shaper_armor_trim_smithing_template", "wayfinder_armor_trim_smithing_template" });
/*     */ 
/*     */   
/*     */   public EntityPackets1_20(Protocol1_19_4To1_20 protocol) {
/*  43 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerPackets() {
/*  48 */     registerTrackerWithData1_19((ClientboundPacketType)ClientboundPackets1_19_4.SPAWN_ENTITY, (EntityType)Entity1_19_4Types.FALLING_BLOCK);
/*  49 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_19_4.ENTITY_METADATA, Types1_20.METADATA_LIST, Types1_19_4.METADATA_LIST);
/*  50 */     registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_19_4.REMOVE_ENTITIES);
/*     */     
/*  52 */     ((Protocol1_19_4To1_20)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  55 */             map((Type)Type.INT);
/*  56 */             map((Type)Type.BOOLEAN);
/*  57 */             map((Type)Type.UNSIGNED_BYTE);
/*  58 */             map((Type)Type.BYTE);
/*  59 */             map(Type.STRING_ARRAY);
/*  60 */             map(Type.NBT);
/*  61 */             map(Type.STRING);
/*  62 */             map(Type.STRING);
/*  63 */             map((Type)Type.LONG);
/*  64 */             map((Type)Type.VAR_INT);
/*  65 */             map((Type)Type.VAR_INT);
/*  66 */             map((Type)Type.VAR_INT);
/*  67 */             map((Type)Type.BOOLEAN);
/*  68 */             map((Type)Type.BOOLEAN);
/*  69 */             map((Type)Type.BOOLEAN);
/*  70 */             map((Type)Type.BOOLEAN);
/*  71 */             map(Type.OPTIONAL_GLOBAL_POSITION);
/*  72 */             read((Type)Type.VAR_INT);
/*     */             
/*  74 */             handler(EntityPackets1_20.this.dimensionDataHandler());
/*  75 */             handler(EntityPackets1_20.this.biomeSizeTracker());
/*  76 */             handler(EntityPackets1_20.this.worldDataTrackerHandlerByKey());
/*  77 */             handler(wrapper -> {
/*     */                   CompoundTag registry = (CompoundTag)wrapper.get(Type.NBT, 0);
/*     */                   
/*     */                   ListTag values = (ListTag)((CompoundTag)registry.get("minecraft:trim_pattern")).get("value");
/*     */                   for (Tag entry : values) {
/*     */                     CompoundTag element = (CompoundTag)((CompoundTag)entry).get("element");
/*     */                     StringTag templateItem = (StringTag)element.get("template_item");
/*     */                     if (EntityPackets1_20.this.newTrimPatterns.contains(Key.stripMinecraftNamespace(templateItem.getValue()))) {
/*     */                       templateItem.setValue("minecraft:spire_armor_trim_smithing_template");
/*     */                     }
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  91 */     ((Protocol1_19_4To1_20)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  94 */             map(Type.STRING);
/*  95 */             map(Type.STRING);
/*  96 */             map((Type)Type.LONG);
/*  97 */             map((Type)Type.UNSIGNED_BYTE);
/*  98 */             map((Type)Type.BYTE);
/*  99 */             map((Type)Type.BOOLEAN);
/* 100 */             map((Type)Type.BOOLEAN);
/* 101 */             map((Type)Type.BYTE);
/* 102 */             map(Type.OPTIONAL_GLOBAL_POSITION);
/* 103 */             read((Type)Type.VAR_INT);
/* 104 */             handler(EntityPackets1_20.this.worldDataTrackerHandlerByKey());
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerRewrites() {
/* 111 */     filter().handler((event, meta) -> meta.setMetaType(Types1_19_4.META_TYPES.byId(meta.metaType().typeId())));
/* 112 */     registerMetaTypeHandler(Types1_19_4.META_TYPES.itemType, Types1_19_4.META_TYPES.blockStateType, Types1_19_4.META_TYPES.optionalBlockStateType, Types1_19_4.META_TYPES.particleType, Types1_19_4.META_TYPES.componentType, Types1_19_4.META_TYPES.optionalComponentType);
/*     */ 
/*     */     
/* 115 */     filter().filterFamily((EntityType)Entity1_19_4Types.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
/*     */           int blockState = ((Integer)meta.value()).intValue();
/*     */           meta.setValue(Integer.valueOf(((Protocol1_19_4To1_20)this.protocol).getMappingData().getNewBlockStateId(blockState)));
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int type) {
/* 123 */     return Entity1_19_4Types.getTypeFromId(type);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_19_4to1_20\packets\EntityPackets1_20.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
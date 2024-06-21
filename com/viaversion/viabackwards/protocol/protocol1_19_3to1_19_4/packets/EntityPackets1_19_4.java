/*     */ package com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.entities.storage.EntityData;
/*     */ import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
/*     */ import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.Protocol1_19_3To1_19_4;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_4Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_19_3;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
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
/*     */ public final class EntityPackets1_19_4
/*     */   extends EntityRewriter<ClientboundPackets1_19_4, Protocol1_19_3To1_19_4>
/*     */ {
/*     */   public EntityPackets1_19_4(Protocol1_19_3To1_19_4 protocol) {
/*  42 */     super((BackwardsProtocol)protocol, Types1_19_3.META_TYPES.optionalComponentType, Types1_19_3.META_TYPES.booleanType);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerPackets() {
/*  47 */     registerTrackerWithData1_19((ClientboundPacketType)ClientboundPackets1_19_4.SPAWN_ENTITY, (EntityType)Entity1_19_4Types.FALLING_BLOCK);
/*  48 */     registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_19_4.REMOVE_ENTITIES);
/*  49 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_19_4.ENTITY_METADATA, Types1_19_4.METADATA_LIST, Types1_19_3.METADATA_LIST);
/*     */     
/*  51 */     ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  54 */             map((Type)Type.INT);
/*  55 */             map((Type)Type.BOOLEAN);
/*  56 */             map((Type)Type.UNSIGNED_BYTE);
/*  57 */             map((Type)Type.BYTE);
/*  58 */             map(Type.STRING_ARRAY);
/*  59 */             map(Type.NBT);
/*  60 */             map(Type.STRING);
/*  61 */             map(Type.STRING);
/*  62 */             handler(EntityPackets1_19_4.this.dimensionDataHandler());
/*  63 */             handler(EntityPackets1_19_4.this.biomeSizeTracker());
/*  64 */             handler(EntityPackets1_19_4.this.worldDataTrackerHandlerByKey());
/*  65 */             handler(wrapper -> {
/*     */                   CompoundTag registry = (CompoundTag)wrapper.get(Type.NBT, 0);
/*     */                   
/*     */                   registry.remove("minecraft:trim_pattern");
/*     */                   
/*     */                   registry.remove("minecraft:trim_material");
/*     */                   registry.remove("minecraft:damage_type");
/*     */                   CompoundTag biomeRegistry = (CompoundTag)registry.get("minecraft:worldgen/biome");
/*     */                   ListTag biomes = (ListTag)biomeRegistry.get("value");
/*     */                   for (Tag biomeTag : biomes) {
/*     */                     CompoundTag biomeData = (CompoundTag)((CompoundTag)biomeTag).get("element");
/*     */                     ByteTag hasPrecipitation = (ByteTag)biomeData.get("has_precipitation");
/*     */                     biomeData.put("precipitation", (Tag)new StringTag((hasPrecipitation.asByte() == 1) ? "rain" : "none"));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  82 */     ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.PLAYER_POSITION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           protected void register() {
/*  85 */             map((Type)Type.DOUBLE);
/*  86 */             map((Type)Type.DOUBLE);
/*  87 */             map((Type)Type.DOUBLE);
/*  88 */             map((Type)Type.FLOAT);
/*  89 */             map((Type)Type.FLOAT);
/*  90 */             map((Type)Type.BYTE);
/*  91 */             map((Type)Type.VAR_INT);
/*  92 */             create((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */           }
/*     */         });
/*     */     
/*  96 */     ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.DAMAGE_EVENT, (ClientboundPacketType)ClientboundPackets1_19_3.ENTITY_STATUS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  99 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/* 100 */             read((Type)Type.VAR_INT);
/* 101 */             read((Type)Type.VAR_INT);
/* 102 */             read((Type)Type.VAR_INT);
/* 103 */             handler(wrapper -> {
/*     */                   if (((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue()) {
/*     */                     wrapper.read((Type)Type.DOUBLE);
/*     */                     
/*     */                     wrapper.read((Type)Type.DOUBLE);
/*     */                     wrapper.read((Type)Type.DOUBLE);
/*     */                   } 
/*     */                 });
/* 111 */             create((Type)Type.BYTE, Byte.valueOf((byte)2));
/*     */           }
/*     */         });
/*     */     
/* 115 */     ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.HIT_ANIMATION, (ClientboundPacketType)ClientboundPackets1_19_3.ENTITY_ANIMATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 118 */             map((Type)Type.VAR_INT);
/* 119 */             read((Type)Type.FLOAT);
/* 120 */             create((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)1));
/*     */           }
/*     */         });
/*     */     
/* 124 */     ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 127 */             map(Type.STRING);
/* 128 */             map(Type.STRING);
/* 129 */             handler(EntityPackets1_19_4.this.worldDataTrackerHandlerByKey());
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerRewrites() {
/* 136 */     filter().handler((event, meta) -> {
/*     */           int id = meta.metaType().typeId();
/*     */           if (id >= 25) {
/*     */             return;
/*     */           }
/*     */           if (id >= 15) {
/*     */             id--;
/*     */           }
/*     */           meta.setMetaType(Types1_19_3.META_TYPES.byId(id));
/*     */         });
/* 146 */     registerMetaTypeHandler(Types1_19_3.META_TYPES.itemType, Types1_19_3.META_TYPES.blockStateType, null, Types1_19_3.META_TYPES.particleType, Types1_19_3.META_TYPES.componentType, Types1_19_3.META_TYPES.optionalComponentType);
/*     */ 
/*     */     
/* 149 */     filter().filterFamily((EntityType)Entity1_19_4Types.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
/*     */           int blockState = ((Integer)meta.value()).intValue();
/*     */           
/*     */           meta.setValue(Integer.valueOf(((Protocol1_19_3To1_19_4)this.protocol).getMappingData().getNewBlockStateId(blockState)));
/*     */         });
/* 154 */     filter().filterFamily((EntityType)Entity1_19_4Types.BOAT).index(11).handler((event, meta) -> {
/*     */           int boatType = ((Integer)meta.value()).intValue();
/*     */           
/*     */           if (boatType > 4) {
/*     */             meta.setValue(Integer.valueOf(boatType - 1));
/*     */           }
/*     */         });
/* 161 */     filter().type((EntityType)Entity1_19_4Types.TEXT_DISPLAY).index(22).handler((event, meta) -> {
/*     */           event.setIndex(2);
/*     */           
/*     */           meta.setMetaType(Types1_19_3.META_TYPES.optionalComponentType);
/*     */           
/*     */           event.createExtraMeta(new Metadata(3, Types1_19_3.META_TYPES.booleanType, Boolean.valueOf(true)));
/*     */           JsonElement element = (JsonElement)meta.value();
/*     */           ((Protocol1_19_3To1_19_4)this.protocol).getTranslatableRewriter().processText(element);
/*     */         });
/* 170 */     filter().filterFamily((EntityType)Entity1_19_4Types.DISPLAY).handler((event, meta) -> {
/*     */           if (event.index() > 7) {
/*     */             event.cancel();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 178 */     filter().type((EntityType)Entity1_19_4Types.INTERACTION).removeIndex(8);
/* 179 */     filter().type((EntityType)Entity1_19_4Types.INTERACTION).removeIndex(9);
/* 180 */     filter().type((EntityType)Entity1_19_4Types.INTERACTION).removeIndex(10);
/*     */     
/* 182 */     filter().type((EntityType)Entity1_19_4Types.SNIFFER).removeIndex(17);
/* 183 */     filter().type((EntityType)Entity1_19_4Types.SNIFFER).removeIndex(18);
/*     */     
/* 185 */     filter().filterFamily((EntityType)Entity1_19_4Types.ABSTRACT_HORSE).addIndex(18);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onMappingDataLoaded() {
/* 190 */     mapTypes();
/*     */     
/* 192 */     EntityData.MetaCreator displayMetaCreator = storage -> {
/*     */         storage.add(new Metadata(0, Types1_19_3.META_TYPES.byteType, Byte.valueOf((byte)32)));
/*     */         storage.add(new Metadata(5, Types1_19_3.META_TYPES.booleanType, Boolean.valueOf(true)));
/*     */         storage.add(new Metadata(15, Types1_19_3.META_TYPES.byteType, Byte.valueOf((byte)17)));
/*     */       };
/* 197 */     mapEntityTypeWithData((EntityType)Entity1_19_4Types.TEXT_DISPLAY, (EntityType)Entity1_19_4Types.ARMOR_STAND).spawnMetadata(displayMetaCreator);
/* 198 */     mapEntityTypeWithData((EntityType)Entity1_19_4Types.ITEM_DISPLAY, (EntityType)Entity1_19_4Types.ARMOR_STAND).spawnMetadata(displayMetaCreator);
/* 199 */     mapEntityTypeWithData((EntityType)Entity1_19_4Types.BLOCK_DISPLAY, (EntityType)Entity1_19_4Types.ARMOR_STAND).spawnMetadata(displayMetaCreator);
/*     */     
/* 201 */     mapEntityTypeWithData((EntityType)Entity1_19_4Types.INTERACTION, (EntityType)Entity1_19_4Types.ARMOR_STAND).spawnMetadata(displayMetaCreator);
/*     */     
/* 203 */     mapEntityTypeWithData((EntityType)Entity1_19_4Types.SNIFFER, (EntityType)Entity1_19_4Types.RAVAGER).jsonName();
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int type) {
/* 208 */     return Entity1_19_4Types.getTypeFromId(type);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_19_3to1_19_4\packets\EntityPackets1_19_4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
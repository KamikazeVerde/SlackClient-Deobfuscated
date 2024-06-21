/*     */ package com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_3Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.BitSetType;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_19;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_19_3;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ClientboundPackets1_19_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.Protocol1_19_3To1_19_1;
/*     */ import com.viaversion.viaversion.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
/*     */ import java.util.BitSet;
/*     */ import java.util.UUID;
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
/*     */   extends EntityRewriter<ClientboundPackets1_19_1, Protocol1_19_3To1_19_1>
/*     */ {
/*  38 */   private static final BitSetType PROFILE_ACTIONS_ENUM_TYPE = new BitSetType(6);
/*     */   
/*     */   public EntityPackets(Protocol1_19_3To1_19_1 protocol) {
/*  41 */     super((Protocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerPackets() {
/*  46 */     registerTrackerWithData1_19((ClientboundPacketType)ClientboundPackets1_19_1.SPAWN_ENTITY, (EntityType)Entity1_19_3Types.FALLING_BLOCK);
/*  47 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_19_1.ENTITY_METADATA, Types1_19.METADATA_LIST, Types1_19_3.METADATA_LIST);
/*  48 */     registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_19_1.REMOVE_ENTITIES);
/*     */     
/*  50 */     ((Protocol1_19_3To1_19_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_1.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  53 */             map((Type)Type.INT);
/*  54 */             map((Type)Type.BOOLEAN);
/*  55 */             map((Type)Type.UNSIGNED_BYTE);
/*  56 */             map((Type)Type.BYTE);
/*  57 */             map(Type.STRING_ARRAY);
/*  58 */             map(Type.NBT);
/*  59 */             map(Type.STRING);
/*  60 */             map(Type.STRING);
/*  61 */             handler(EntityPackets.this.dimensionDataHandler());
/*  62 */             handler(EntityPackets.this.biomeSizeTracker());
/*  63 */             handler(EntityPackets.this.worldDataTrackerHandlerByKey());
/*  64 */             handler(wrapper -> {
/*     */                   PacketWrapper enableFeaturesPacket = wrapper.create((PacketType)ClientboundPackets1_19_3.UPDATE_ENABLED_FEATURES);
/*     */                   
/*     */                   enableFeaturesPacket.write((Type)Type.VAR_INT, Integer.valueOf(1));
/*     */                   
/*     */                   enableFeaturesPacket.write(Type.STRING, "minecraft:vanilla");
/*     */                   enableFeaturesPacket.scheduleSend(Protocol1_19_3To1_19_1.class);
/*     */                 });
/*     */           }
/*     */         });
/*  74 */     ((Protocol1_19_3To1_19_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_1.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  77 */             map(Type.STRING);
/*  78 */             map(Type.STRING);
/*  79 */             map((Type)Type.LONG);
/*  80 */             map((Type)Type.UNSIGNED_BYTE);
/*  81 */             map((Type)Type.BYTE);
/*  82 */             map((Type)Type.BOOLEAN);
/*  83 */             map((Type)Type.BOOLEAN);
/*  84 */             handler(EntityPackets.this.worldDataTrackerHandlerByKey());
/*  85 */             handler(wrapper -> {
/*     */                   boolean keepAttributes = ((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue();
/*     */                   
/*     */                   byte keepDataMask = 2;
/*     */                   if (keepAttributes) {
/*     */                     keepDataMask = (byte)(keepDataMask | 0x1);
/*     */                   }
/*     */                   wrapper.write((Type)Type.BYTE, Byte.valueOf(keepDataMask));
/*     */                 });
/*     */           }
/*     */         });
/*  96 */     ((Protocol1_19_3To1_19_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_1.PLAYER_INFO, (ClientboundPacketType)ClientboundPackets1_19_3.PLAYER_INFO_UPDATE, wrapper -> {
/*     */           int action = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           if (action == 4) {
/*     */             int j = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */             UUID[] uuidsToRemove = new UUID[j];
/*     */             for (int k = 0; k < j; k++) {
/*     */               uuidsToRemove[k] = (UUID)wrapper.read(Type.UUID);
/*     */             }
/*     */             wrapper.write(Type.UUID_ARRAY, uuidsToRemove);
/*     */             wrapper.setPacketType((PacketType)ClientboundPackets1_19_3.PLAYER_INFO_REMOVE);
/*     */             return;
/*     */           } 
/*     */           BitSet set = new BitSet(6);
/*     */           if (action == 0) {
/*     */             set.set(0, 6);
/*     */           } else {
/*     */             set.set((action == 1) ? (action + 1) : (action + 2));
/*     */           } 
/*     */           wrapper.write((Type)PROFILE_ACTIONS_ENUM_TYPE, set);
/*     */           int entries = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           for (int i = 0; i < entries; i++) {
/*     */             wrapper.passthrough(Type.UUID);
/*     */             if (action == 0) {
/*     */               wrapper.passthrough(Type.STRING);
/*     */               int properties = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */               for (int j = 0; j < properties; j++) {
/*     */                 wrapper.passthrough(Type.STRING);
/*     */                 wrapper.passthrough(Type.STRING);
/*     */                 wrapper.passthrough(Type.OPTIONAL_STRING);
/*     */               } 
/*     */               int gamemode = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */               int ping = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */               JsonElement displayName = (JsonElement)wrapper.read(Type.OPTIONAL_COMPONENT);
/*     */               wrapper.read(Type.OPTIONAL_PROFILE_KEY);
/*     */               wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */               wrapper.write((Type)Type.VAR_INT, Integer.valueOf(gamemode));
/*     */               wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */               wrapper.write((Type)Type.VAR_INT, Integer.valueOf(ping));
/*     */               wrapper.write(Type.OPTIONAL_COMPONENT, displayName);
/*     */             } else if (action == 1 || action == 2) {
/*     */               wrapper.passthrough((Type)Type.VAR_INT);
/*     */             } else if (action == 3) {
/*     */               wrapper.passthrough(Type.OPTIONAL_COMPONENT);
/*     */             } 
/*     */           } 
/*     */         });
/*     */   }
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
/*     */   protected void registerRewrites() {
/* 154 */     filter().handler((event, meta) -> {
/*     */           int id = meta.metaType().typeId();
/*     */           meta.setMetaType(Types1_19_3.META_TYPES.byId((id >= 2) ? (id + 1) : id));
/*     */         });
/* 158 */     registerMetaTypeHandler(Types1_19_3.META_TYPES.itemType, Types1_19_3.META_TYPES.blockStateType, null, Types1_19_3.META_TYPES.particleType);
/*     */     
/* 160 */     filter().index(6).handler((event, meta) -> {
/*     */           int pose = ((Integer)meta.value()).intValue();
/*     */           
/*     */           if (pose >= 10) {
/*     */             meta.setValue(Integer.valueOf(pose + 1));
/*     */           }
/*     */         });
/* 167 */     filter().filterFamily((EntityType)Entity1_19_3Types.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
/*     */           int data = ((Integer)meta.getValue()).intValue();
/*     */           meta.setValue(Integer.valueOf(((Protocol1_19_3To1_19_1)this.protocol).getMappingData().getNewBlockStateId(data)));
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onMappingDataLoaded() {
/* 176 */     mapTypes();
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int type) {
/* 181 */     return Entity1_19_3Types.getTypeFromId(type);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19_3to1_19_1\packets\EntityPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
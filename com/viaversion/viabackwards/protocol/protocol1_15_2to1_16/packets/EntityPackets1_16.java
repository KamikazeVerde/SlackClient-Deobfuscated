/*     */ package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.WorldNameTracker;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.storage.WolfDataMaskStorage;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.data.entity.StoredEntityData;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.Particle;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_14;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_16;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
/*     */ import com.viaversion.viaversion.util.Key;
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
/*     */ public class EntityPackets1_16
/*     */   extends EntityRewriter<ClientboundPackets1_16, Protocol1_15_2To1_16>
/*     */ {
/*  48 */   private final ValueTransformer<String, Integer> dimensionTransformer = new ValueTransformer<String, Integer>(Type.STRING, (Type)Type.INT)
/*     */     {
/*     */       public Integer transform(PacketWrapper wrapper, String input) {
/*  51 */         switch (input)
/*     */         { case "minecraft:the_nether":
/*  53 */             return Integer.valueOf(-1);
/*     */           
/*     */           default:
/*  56 */             return Integer.valueOf(0);
/*     */           case "minecraft:the_end":
/*  58 */             break; }  return Integer.valueOf(1);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public EntityPackets1_16(Protocol1_15_2To1_16 protocol) {
/*  64 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  69 */     ((Protocol1_15_2To1_16)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_16.SPAWN_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  72 */             map((Type)Type.VAR_INT);
/*  73 */             map(Type.UUID);
/*  74 */             map((Type)Type.VAR_INT);
/*  75 */             map((Type)Type.DOUBLE);
/*  76 */             map((Type)Type.DOUBLE);
/*  77 */             map((Type)Type.DOUBLE);
/*  78 */             map((Type)Type.BYTE);
/*  79 */             map((Type)Type.BYTE);
/*  80 */             map((Type)Type.INT);
/*  81 */             handler(wrapper -> {
/*     */                   EntityType entityType = EntityPackets1_16.this.typeFromId(((Integer)wrapper.get((Type)Type.VAR_INT, 1)).intValue());
/*     */                   
/*     */                   if (entityType == Entity1_16Types.LIGHTNING_BOLT) {
/*     */                     wrapper.cancel();
/*     */                     
/*     */                     PacketWrapper spawnLightningPacket = wrapper.create((PacketType)ClientboundPackets1_15.SPAWN_GLOBAL_ENTITY);
/*     */                     spawnLightningPacket.write((Type)Type.VAR_INT, wrapper.get((Type)Type.VAR_INT, 0));
/*     */                     spawnLightningPacket.write((Type)Type.BYTE, Byte.valueOf((byte)1));
/*     */                     spawnLightningPacket.write((Type)Type.DOUBLE, wrapper.get((Type)Type.DOUBLE, 0));
/*     */                     spawnLightningPacket.write((Type)Type.DOUBLE, wrapper.get((Type)Type.DOUBLE, 1));
/*     */                     spawnLightningPacket.write((Type)Type.DOUBLE, wrapper.get((Type)Type.DOUBLE, 2));
/*     */                     spawnLightningPacket.send(Protocol1_15_2To1_16.class);
/*     */                   } 
/*     */                 });
/*  96 */             handler(EntityPackets1_16.this.getSpawnTrackerWithDataHandler((EntityType)Entity1_16Types.FALLING_BLOCK));
/*     */           }
/*     */         });
/*     */     
/* 100 */     registerSpawnTracker((ClientboundPacketType)ClientboundPackets1_16.SPAWN_MOB);
/*     */     
/* 102 */     ((Protocol1_15_2To1_16)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_16.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 105 */             map(EntityPackets1_16.this.dimensionTransformer);
/* 106 */             handler(wrapper -> {
/*     */                   WorldNameTracker worldNameTracker = (WorldNameTracker)wrapper.user().get(WorldNameTracker.class);
/*     */                   
/*     */                   String nextWorldName = (String)wrapper.read(Type.STRING);
/*     */                   
/*     */                   wrapper.passthrough((Type)Type.LONG);
/*     */                   
/*     */                   wrapper.passthrough((Type)Type.UNSIGNED_BYTE);
/*     */                   
/*     */                   wrapper.read((Type)Type.BYTE);
/*     */                   
/*     */                   ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   
/*     */                   int dimension = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   if (clientWorld.getEnvironment() != null && dimension == clientWorld.getEnvironment().id() && (wrapper.user().isClientSide() || Via.getPlatform().isProxy() || wrapper.user().getProtocolInfo().getProtocolVersion() <= ProtocolVersion.v1_12_2.getVersion() || !nextWorldName.equals(worldNameTracker.getWorldName()))) {
/*     */                     PacketWrapper packet = wrapper.create((PacketType)ClientboundPackets1_15.RESPAWN);
/*     */                     
/*     */                     packet.write((Type)Type.INT, Integer.valueOf((dimension == 0) ? -1 : 0));
/*     */                     
/*     */                     packet.write((Type)Type.LONG, Long.valueOf(0L));
/*     */                     
/*     */                     packet.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)0));
/*     */                     
/*     */                     packet.write(Type.STRING, "default");
/*     */                     
/*     */                     packet.send(Protocol1_15_2To1_16.class);
/*     */                   } 
/*     */                   
/*     */                   clientWorld.setEnvironment(dimension);
/*     */                   
/*     */                   wrapper.write(Type.STRING, "default");
/*     */                   wrapper.read((Type)Type.BOOLEAN);
/*     */                   if (((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue()) {
/*     */                     wrapper.set(Type.STRING, 0, "flat");
/*     */                   }
/*     */                   wrapper.read((Type)Type.BOOLEAN);
/*     */                   worldNameTracker.setWorldName(nextWorldName);
/*     */                 });
/*     */           }
/*     */         });
/* 147 */     ((Protocol1_15_2To1_16)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_16.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 150 */             map((Type)Type.INT);
/* 151 */             map((Type)Type.UNSIGNED_BYTE);
/* 152 */             map((Type)Type.BYTE, (Type)Type.NOTHING);
/* 153 */             map(Type.STRING_ARRAY, (Type)Type.NOTHING);
/* 154 */             map(Type.NBT, (Type)Type.NOTHING);
/* 155 */             map(EntityPackets1_16.this.dimensionTransformer);
/* 156 */             handler(wrapper -> {
/*     */                   WorldNameTracker worldNameTracker = (WorldNameTracker)wrapper.user().get(WorldNameTracker.class);
/*     */                   worldNameTracker.setWorldName((String)wrapper.read(Type.STRING));
/*     */                 });
/* 160 */             map((Type)Type.LONG);
/* 161 */             map((Type)Type.UNSIGNED_BYTE);
/* 162 */             handler(wrapper -> {
/*     */                   ClientWorld clientChunks = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   
/*     */                   clientChunks.setEnvironment(((Integer)wrapper.get((Type)Type.INT, 1)).intValue());
/*     */                   
/*     */                   EntityPackets1_16.this.tracker(wrapper.user()).addEntity(((Integer)wrapper.get((Type)Type.INT, 0)).intValue(), (EntityType)Entity1_16Types.PLAYER);
/*     */                   
/*     */                   wrapper.write(Type.STRING, "default");
/*     */                   
/*     */                   wrapper.passthrough((Type)Type.VAR_INT);
/*     */                   wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                   wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                   wrapper.read((Type)Type.BOOLEAN);
/*     */                   if (((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue()) {
/*     */                     wrapper.set(Type.STRING, 0, "flat");
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 181 */     registerTracker((ClientboundPacketType)ClientboundPackets1_16.SPAWN_EXPERIENCE_ORB, (EntityType)Entity1_16Types.EXPERIENCE_ORB);
/*     */     
/* 183 */     registerTracker((ClientboundPacketType)ClientboundPackets1_16.SPAWN_PAINTING, (EntityType)Entity1_16Types.PAINTING);
/* 184 */     registerTracker((ClientboundPacketType)ClientboundPackets1_16.SPAWN_PLAYER, (EntityType)Entity1_16Types.PLAYER);
/* 185 */     registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_16.DESTROY_ENTITIES);
/* 186 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_16.ENTITY_METADATA, Types1_16.METADATA_LIST, Types1_14.METADATA_LIST);
/*     */     
/* 188 */     ((Protocol1_15_2To1_16)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_16.ENTITY_PROPERTIES, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */           
/*     */           int size = ((Integer)wrapper.passthrough((Type)Type.INT)).intValue();
/*     */           
/*     */           for (int i = 0; i < size; i++) {
/*     */             String attributeIdentifier = (String)wrapper.read(Type.STRING);
/*     */             String oldKey = (String)((Protocol1_15_2To1_16)this.protocol).getMappingData().getAttributeMappings().get(attributeIdentifier);
/*     */             wrapper.write(Type.STRING, (oldKey != null) ? oldKey : Key.stripMinecraftNamespace(attributeIdentifier));
/*     */             wrapper.passthrough((Type)Type.DOUBLE);
/*     */             int modifierSize = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */             for (int j = 0; j < modifierSize; j++) {
/*     */               wrapper.passthrough(Type.UUID);
/*     */               wrapper.passthrough((Type)Type.DOUBLE);
/*     */               wrapper.passthrough((Type)Type.BYTE);
/*     */             } 
/*     */           } 
/*     */         });
/* 206 */     ((Protocol1_15_2To1_16)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_16.PLAYER_INFO, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 209 */             handler(packetWrapper -> {
/*     */                   int action = ((Integer)packetWrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */                   int playerCount = ((Integer)packetWrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */                   for (int i = 0; i < playerCount; i++) {
/*     */                     packetWrapper.passthrough(Type.UUID);
/*     */                     if (action == 0) {
/*     */                       packetWrapper.passthrough(Type.STRING);
/*     */                       int properties = ((Integer)packetWrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */                       for (int j = 0; j < properties; j++) {
/*     */                         packetWrapper.passthrough(Type.STRING);
/*     */                         packetWrapper.passthrough(Type.STRING);
/*     */                         packetWrapper.passthrough(Type.OPTIONAL_STRING);
/*     */                       } 
/*     */                       packetWrapper.passthrough((Type)Type.VAR_INT);
/*     */                       packetWrapper.passthrough((Type)Type.VAR_INT);
/*     */                       ((Protocol1_15_2To1_16)EntityPackets1_16.this.protocol).getTranslatableRewriter().processText((JsonElement)packetWrapper.passthrough(Type.OPTIONAL_COMPONENT));
/*     */                     } else if (action == 1) {
/*     */                       packetWrapper.passthrough((Type)Type.VAR_INT);
/*     */                     } else if (action == 2) {
/*     */                       packetWrapper.passthrough((Type)Type.VAR_INT);
/*     */                     } else if (action == 3) {
/*     */                       ((Protocol1_15_2To1_16)EntityPackets1_16.this.protocol).getTranslatableRewriter().processText((JsonElement)packetWrapper.passthrough(Type.OPTIONAL_COMPONENT));
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerRewrites() {
/* 242 */     filter().handler((event, meta) -> {
/*     */           meta.setMetaType(Types1_14.META_TYPES.byId(meta.metaType().typeId()));
/*     */           
/*     */           MetaType type = meta.metaType();
/*     */           
/*     */           if (type == Types1_14.META_TYPES.itemType) {
/*     */             meta.setValue(((Protocol1_15_2To1_16)this.protocol).getItemRewriter().handleItemToClient((Item)meta.getValue()));
/*     */           } else if (type == Types1_14.META_TYPES.blockStateType) {
/*     */             meta.setValue(Integer.valueOf(((Protocol1_15_2To1_16)this.protocol).getMappingData().getNewBlockStateId(((Integer)meta.getValue()).intValue())));
/*     */           } else if (type == Types1_14.META_TYPES.particleType) {
/*     */             rewriteParticle((Particle)meta.getValue());
/*     */           } else if (type == Types1_14.META_TYPES.optionalComponentType) {
/*     */             JsonElement text = (JsonElement)meta.value();
/*     */             if (text != null) {
/*     */               ((Protocol1_15_2To1_16)this.protocol).getTranslatableRewriter().processText(text);
/*     */             }
/*     */           } 
/*     */         });
/* 260 */     mapEntityType((EntityType)Entity1_16Types.ZOMBIFIED_PIGLIN, (EntityType)Entity1_15Types.ZOMBIE_PIGMAN);
/* 261 */     mapTypes((EntityType[])Entity1_16Types.values(), Entity1_15Types.class);
/*     */     
/* 263 */     mapEntityTypeWithData((EntityType)Entity1_16Types.HOGLIN, (EntityType)Entity1_16Types.COW).jsonName();
/* 264 */     mapEntityTypeWithData((EntityType)Entity1_16Types.ZOGLIN, (EntityType)Entity1_16Types.COW).jsonName();
/* 265 */     mapEntityTypeWithData((EntityType)Entity1_16Types.PIGLIN, (EntityType)Entity1_16Types.ZOMBIFIED_PIGLIN).jsonName();
/* 266 */     mapEntityTypeWithData((EntityType)Entity1_16Types.STRIDER, (EntityType)Entity1_16Types.MAGMA_CUBE).jsonName();
/*     */     
/* 268 */     filter().type((EntityType)Entity1_16Types.ZOGLIN).cancel(16);
/* 269 */     filter().type((EntityType)Entity1_16Types.HOGLIN).cancel(15);
/*     */     
/* 271 */     filter().type((EntityType)Entity1_16Types.PIGLIN).cancel(16);
/* 272 */     filter().type((EntityType)Entity1_16Types.PIGLIN).cancel(17);
/* 273 */     filter().type((EntityType)Entity1_16Types.PIGLIN).cancel(18);
/*     */     
/* 275 */     filter().type((EntityType)Entity1_16Types.STRIDER).index(15).handler((event, meta) -> {
/*     */           boolean baby = ((Boolean)meta.value()).booleanValue();
/*     */           meta.setTypeAndValue(Types1_14.META_TYPES.varIntType, Integer.valueOf(baby ? 1 : 3));
/*     */         });
/* 279 */     filter().type((EntityType)Entity1_16Types.STRIDER).cancel(16);
/* 280 */     filter().type((EntityType)Entity1_16Types.STRIDER).cancel(17);
/* 281 */     filter().type((EntityType)Entity1_16Types.STRIDER).cancel(18);
/*     */     
/* 283 */     filter().type((EntityType)Entity1_16Types.FISHING_BOBBER).cancel(8);
/*     */     
/* 285 */     filter().filterFamily((EntityType)Entity1_16Types.ABSTRACT_ARROW).cancel(8);
/* 286 */     filter().filterFamily((EntityType)Entity1_16Types.ABSTRACT_ARROW).handler((event, meta) -> {
/*     */           if (event.index() >= 8) {
/*     */             event.setIndex(event.index() + 1);
/*     */           }
/*     */         });
/*     */     
/* 292 */     filter().type((EntityType)Entity1_16Types.WOLF).index(16).handler((event, meta) -> {
/*     */           byte mask = ((Byte)meta.value()).byteValue();
/*     */           
/*     */           StoredEntityData data = tracker(event.user()).entityData(event.entityId());
/*     */           data.put(new WolfDataMaskStorage(mask));
/*     */         });
/* 298 */     filter().type((EntityType)Entity1_16Types.WOLF).index(20).handler((event, meta) -> {
/*     */           StoredEntityData data = tracker(event.user()).entityDataIfPresent(event.entityId());
/*     */           byte previousMask = 0;
/*     */           if (data != null) {
/*     */             WolfDataMaskStorage wolfData = (WolfDataMaskStorage)data.get(WolfDataMaskStorage.class);
/*     */             if (wolfData != null) {
/*     */               previousMask = wolfData.tameableMask();
/*     */             }
/*     */           } 
/*     */           int angerTime = ((Integer)meta.value()).intValue();
/*     */           byte tameableMask = (byte)((angerTime > 0) ? (previousMask | 0x2) : (previousMask & 0xFFFFFFFD));
/*     */           event.createExtraMeta(new Metadata(16, Types1_14.META_TYPES.byteType, Byte.valueOf(tameableMask)));
/*     */           event.cancel();
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int typeId) {
/* 317 */     return Entity1_16Types.getTypeFromId(typeId);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_15_2to1_16\packets\EntityPackets1_16.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
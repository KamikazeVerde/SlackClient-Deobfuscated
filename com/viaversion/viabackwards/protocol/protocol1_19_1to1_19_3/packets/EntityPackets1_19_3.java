/*     */ package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.Protocol1_19_1To1_19_3;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.storage.ChatTypeStorage1_19_3;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.ProfileKey;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_3Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
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
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ClientboundPackets1_19_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
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
/*     */ 
/*     */ public final class EntityPackets1_19_3
/*     */   extends EntityRewriter<ClientboundPackets1_19_3, Protocol1_19_1To1_19_3>
/*     */ {
/*  46 */   private static final BitSetType PROFILE_ACTIONS_ENUM_TYPE = new BitSetType(6);
/*  47 */   private static final int[] PROFILE_ACTIONS = new int[] { 2, 4, 5 };
/*     */   private static final int ADD_PLAYER = 0;
/*     */   private static final int INITIALIZE_CHAT = 1;
/*     */   private static final int UPDATE_GAMEMODE = 2;
/*     */   private static final int UPDATE_LISTED = 3;
/*     */   private static final int UPDATE_LATENCY = 4;
/*     */   private static final int UPDATE_DISPLAYNAME = 5;
/*     */   
/*     */   public EntityPackets1_19_3(Protocol1_19_1To1_19_3 protocol) {
/*  56 */     super((BackwardsProtocol)protocol, Types1_19.META_TYPES.optionalComponentType, Types1_19.META_TYPES.booleanType);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  61 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_19_3.ENTITY_METADATA, Types1_19_3.METADATA_LIST, Types1_19.METADATA_LIST);
/*  62 */     registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_19_3.REMOVE_ENTITIES);
/*  63 */     registerTrackerWithData1_19((ClientboundPacketType)ClientboundPackets1_19_3.SPAWN_ENTITY, (EntityType)Entity1_19_3Types.FALLING_BLOCK);
/*     */     
/*  65 */     ((Protocol1_19_1To1_19_3)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  68 */             map((Type)Type.INT);
/*  69 */             map((Type)Type.BOOLEAN);
/*  70 */             map((Type)Type.UNSIGNED_BYTE);
/*  71 */             map((Type)Type.BYTE);
/*  72 */             map(Type.STRING_ARRAY);
/*  73 */             map(Type.NBT);
/*  74 */             map(Type.STRING);
/*  75 */             map(Type.STRING);
/*  76 */             handler(EntityPackets1_19_3.this.dimensionDataHandler());
/*  77 */             handler(EntityPackets1_19_3.this.biomeSizeTracker());
/*  78 */             handler(EntityPackets1_19_3.this.worldDataTrackerHandlerByKey());
/*  79 */             handler(wrapper -> {
/*     */                   ChatTypeStorage1_19_3 chatTypeStorage = (ChatTypeStorage1_19_3)wrapper.user().get(ChatTypeStorage1_19_3.class);
/*     */                   
/*     */                   chatTypeStorage.clear();
/*     */                   CompoundTag registry = (CompoundTag)wrapper.get(Type.NBT, 0);
/*     */                   ListTag chatTypes = (ListTag)((CompoundTag)registry.get("minecraft:chat_type")).get("value");
/*     */                   for (Tag chatType : chatTypes) {
/*     */                     CompoundTag chatTypeCompound = (CompoundTag)chatType;
/*     */                     NumberTag idTag = (NumberTag)chatTypeCompound.get("id");
/*     */                     chatTypeStorage.addChatType(idTag.asInt(), chatTypeCompound);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  93 */     ((Protocol1_19_1To1_19_3)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  96 */             map(Type.STRING);
/*  97 */             map(Type.STRING);
/*  98 */             map((Type)Type.LONG);
/*  99 */             map((Type)Type.UNSIGNED_BYTE);
/* 100 */             map((Type)Type.BYTE);
/* 101 */             map((Type)Type.BOOLEAN);
/* 102 */             map((Type)Type.BOOLEAN);
/* 103 */             handler(EntityPackets1_19_3.this.worldDataTrackerHandlerByKey());
/* 104 */             handler(wrapper -> {
/*     */                   byte keepDataMask = ((Byte)wrapper.read((Type)Type.BYTE)).byteValue();
/*     */                   
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(((keepDataMask & 0x1) != 0)));
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 112 */     ((Protocol1_19_1To1_19_3)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.PLAYER_INFO_UPDATE, (ClientboundPacketType)ClientboundPackets1_19_1.PLAYER_INFO, wrapper -> {
/*     */           wrapper.cancel();
/*     */           BitSet actions = (BitSet)wrapper.read((Type)PROFILE_ACTIONS_ENUM_TYPE);
/*     */           int entries = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           if (actions.get(0)) {
/*     */             PacketWrapper playerInfoPacket = wrapper.create((PacketType)ClientboundPackets1_19_1.PLAYER_INFO);
/*     */             playerInfoPacket.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */             playerInfoPacket.write((Type)Type.VAR_INT, Integer.valueOf(entries));
/*     */             for (int j = 0; j < entries; j++) {
/*     */               ProfileKey profileKey;
/*     */               playerInfoPacket.write(Type.UUID, wrapper.read(Type.UUID));
/*     */               playerInfoPacket.write(Type.STRING, wrapper.read(Type.STRING));
/*     */               int properties = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */               playerInfoPacket.write((Type)Type.VAR_INT, Integer.valueOf(properties));
/*     */               for (int k = 0; k < properties; k++) {
/*     */                 playerInfoPacket.write(Type.STRING, wrapper.read(Type.STRING));
/*     */                 playerInfoPacket.write(Type.STRING, wrapper.read(Type.STRING));
/*     */                 playerInfoPacket.write(Type.OPTIONAL_STRING, wrapper.read(Type.OPTIONAL_STRING));
/*     */               } 
/*     */               if (actions.get(1) && ((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue()) {
/*     */                 wrapper.read(Type.UUID);
/*     */                 profileKey = (ProfileKey)wrapper.read(Type.PROFILE_KEY);
/*     */               } else {
/*     */                 profileKey = null;
/*     */               } 
/*     */               int gamemode = actions.get(2) ? ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue() : 0;
/*     */               if (actions.get(3)) {
/*     */                 wrapper.read((Type)Type.BOOLEAN);
/*     */               }
/*     */               int latency = actions.get(4) ? ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue() : 0;
/*     */               JsonElement displayName = actions.get(5) ? (JsonElement)wrapper.read(Type.OPTIONAL_COMPONENT) : null;
/*     */               playerInfoPacket.write((Type)Type.VAR_INT, Integer.valueOf(gamemode));
/*     */               playerInfoPacket.write((Type)Type.VAR_INT, Integer.valueOf(latency));
/*     */               playerInfoPacket.write(Type.OPTIONAL_COMPONENT, displayName);
/*     */               playerInfoPacket.write(Type.OPTIONAL_PROFILE_KEY, profileKey);
/*     */             } 
/*     */             playerInfoPacket.send(Protocol1_19_1To1_19_3.class);
/*     */             return;
/*     */           } 
/*     */           PlayerProfileUpdate[] updates = new PlayerProfileUpdate[entries];
/*     */           for (int i = 0; i < entries; i++) {
/*     */             UUID uuid = (UUID)wrapper.read(Type.UUID);
/*     */             int gamemode = 0;
/*     */             int latency = 0;
/*     */             JsonElement displayName = null;
/*     */             for (int action : PROFILE_ACTIONS) {
/*     */               if (actions.get(action)) {
/*     */                 switch (action) {
/*     */                   case 2:
/*     */                     gamemode = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                     break;
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   case 4:
/*     */                     latency = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                     break;
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   case 5:
/*     */                     displayName = (JsonElement)wrapper.read(Type.OPTIONAL_COMPONENT);
/*     */                     break;
/*     */                 } 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               }
/*     */             } 
/*     */             updates[i] = new PlayerProfileUpdate(uuid, gamemode, latency, displayName);
/*     */           } 
/*     */           if (actions.get(2)) {
/*     */             sendPlayerProfileUpdate(wrapper.user(), 1, updates);
/*     */           } else if (actions.get(4)) {
/*     */             sendPlayerProfileUpdate(wrapper.user(), 2, updates);
/*     */           } else if (actions.get(5)) {
/*     */             sendPlayerProfileUpdate(wrapper.user(), 3, updates);
/*     */           } 
/*     */         });
/* 194 */     ((Protocol1_19_1To1_19_3)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.PLAYER_INFO_REMOVE, (ClientboundPacketType)ClientboundPackets1_19_1.PLAYER_INFO, wrapper -> {
/*     */           UUID[] uuids = (UUID[])wrapper.read(Type.UUID_ARRAY);
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(4));
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(uuids.length));
/*     */           for (UUID uuid : uuids) {
/*     */             wrapper.write(Type.UUID, uuid);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void sendPlayerProfileUpdate(UserConnection connection, int action, PlayerProfileUpdate[] updates) throws Exception {
/* 205 */     PacketWrapper playerInfoPacket = PacketWrapper.create((PacketType)ClientboundPackets1_19_1.PLAYER_INFO, connection);
/* 206 */     playerInfoPacket.write((Type)Type.VAR_INT, Integer.valueOf(action));
/* 207 */     playerInfoPacket.write((Type)Type.VAR_INT, Integer.valueOf(updates.length));
/* 208 */     for (PlayerProfileUpdate update : updates) {
/* 209 */       playerInfoPacket.write(Type.UUID, update.uuid());
/* 210 */       if (action == 1) {
/* 211 */         playerInfoPacket.write((Type)Type.VAR_INT, Integer.valueOf(update.gamemode()));
/* 212 */       } else if (action == 2) {
/* 213 */         playerInfoPacket.write((Type)Type.VAR_INT, Integer.valueOf(update.latency()));
/* 214 */       } else if (action == 3) {
/* 215 */         playerInfoPacket.write(Type.OPTIONAL_COMPONENT, update.displayName());
/*     */       } else {
/* 217 */         throw new IllegalArgumentException("Invalid action: " + action);
/*     */       } 
/*     */     } 
/* 220 */     playerInfoPacket.send(Protocol1_19_1To1_19_3.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerRewrites() {
/* 225 */     filter().handler((event, meta) -> {
/*     */           int id = meta.metaType().typeId();
/*     */           if (id > 2) {
/*     */             meta.setMetaType(Types1_19.META_TYPES.byId(id - 1));
/*     */           } else if (id != 2) {
/*     */             meta.setMetaType(Types1_19.META_TYPES.byId(id));
/*     */           } 
/*     */         });
/* 233 */     registerMetaTypeHandler(Types1_19.META_TYPES.itemType, Types1_19.META_TYPES.blockStateType, null, Types1_19.META_TYPES.particleType, Types1_19.META_TYPES.componentType, Types1_19.META_TYPES.optionalComponentType);
/*     */ 
/*     */     
/* 236 */     filter().index(6).handler((event, meta) -> {
/*     */           int pose = ((Integer)meta.value()).intValue();
/*     */           
/*     */           if (pose == 10) {
/*     */             meta.setValue(Integer.valueOf(0));
/*     */           } else if (pose > 10) {
/*     */             meta.setValue(Integer.valueOf(pose - 1));
/*     */           } 
/*     */         });
/* 245 */     filter().filterFamily((EntityType)Entity1_19_3Types.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
/*     */           int data = ((Integer)meta.getValue()).intValue();
/*     */           
/*     */           meta.setValue(Integer.valueOf(((Protocol1_19_1To1_19_3)this.protocol).getMappingData().getNewBlockStateId(data)));
/*     */         });
/* 250 */     filter().type((EntityType)Entity1_19_3Types.CAMEL).cancel(19);
/* 251 */     filter().type((EntityType)Entity1_19_3Types.CAMEL).cancel(20);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onMappingDataLoaded() {
/* 256 */     mapTypes();
/* 257 */     mapEntityTypeWithData((EntityType)Entity1_19_3Types.CAMEL, (EntityType)Entity1_19_3Types.DONKEY).jsonName();
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int typeId) {
/* 262 */     return Entity1_19_3Types.getTypeFromId(typeId);
/*     */   }
/*     */   
/*     */   private static final class PlayerProfileUpdate {
/*     */     private final UUID uuid;
/*     */     private final int gamemode;
/*     */     private final int latency;
/*     */     private final JsonElement displayName;
/*     */     
/*     */     private PlayerProfileUpdate(UUID uuid, int gamemode, int latency, JsonElement displayName) {
/* 272 */       this.uuid = uuid;
/* 273 */       this.gamemode = gamemode;
/* 274 */       this.latency = latency;
/* 275 */       this.displayName = displayName;
/*     */     }
/*     */     
/*     */     public UUID uuid() {
/* 279 */       return this.uuid;
/*     */     }
/*     */     
/*     */     public int gamemode() {
/* 283 */       return this.gamemode;
/*     */     }
/*     */     
/*     */     public int latency() {
/* 287 */       return this.latency;
/*     */     }
/*     */     
/*     */     public JsonElement displayName() {
/* 291 */       return this.displayName;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_19_1to1_19_3\packets\EntityPackets1_19_3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
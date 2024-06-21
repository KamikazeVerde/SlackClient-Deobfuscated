/*     */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import de.gerrygames.viarewind.ViaRewind;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ClientboundPackets1_7;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ServerboundPackets1_7;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.ArmorStandReplacement;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ItemRewriter;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.provider.TitleRenderProvider;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerAbilities;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerPosition;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.Scoreboard;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.Windows;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
/*     */ import de.gerrygames.viarewind.replacement.EntityReplacement;
/*     */ import de.gerrygames.viarewind.utils.ChatUtil;
/*     */ import de.gerrygames.viarewind.utils.PacketUtil;
/*     */ import de.gerrygames.viarewind.utils.math.AABB;
/*     */ import de.gerrygames.viarewind.utils.math.Ray3d;
/*     */ import de.gerrygames.viarewind.utils.math.RayTracing;
/*     */ import de.gerrygames.viarewind.utils.math.Vector3d;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ 
/*     */ public class PlayerPackets {
/*     */   public static void register(Protocol1_7_6_10TO1_8 protocol) {
/*  48 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  51 */             map((Type)Type.INT);
/*  52 */             map((Type)Type.UNSIGNED_BYTE);
/*  53 */             map((Type)Type.BYTE);
/*  54 */             map((Type)Type.UNSIGNED_BYTE);
/*  55 */             map((Type)Type.UNSIGNED_BYTE);
/*  56 */             map(Type.STRING);
/*  57 */             map((Type)Type.BOOLEAN, (Type)Type.NOTHING);
/*  58 */             handler(packetWrapper -> {
/*     */                   if (!ViaRewind.getConfig().isReplaceAdventureMode())
/*     */                     return; 
/*     */                   if (((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue() == 2)
/*     */                     packetWrapper.set((Type)Type.UNSIGNED_BYTE, 0, Short.valueOf((short)0)); 
/*     */                 });
/*  64 */             handler(packetWrapper -> {
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   tracker.setGamemode(((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue());
/*     */                   tracker.setPlayerId(((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue());
/*     */                   tracker.getClientEntityTypes().put(Integer.valueOf(tracker.getPlayerId()), Entity1_10Types.EntityType.ENTITY_HUMAN);
/*     */                   tracker.setDimension(((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue());
/*     */                   tracker.addPlayer(Integer.valueOf(tracker.getPlayerId()), packetWrapper.user().getProtocolInfo().getUuid());
/*     */                 });
/*  72 */             handler(packetWrapper -> {
/*     */                   ClientWorld world = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
/*     */                   world.setEnvironment(((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue());
/*     */                 });
/*  76 */             handler(wrapper -> wrapper.user().put((StorableObject)new Scoreboard(wrapper.user())));
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.CHAT_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  86 */             map(Type.COMPONENT);
/*  87 */             handler(packetWrapper -> {
/*     */                   int position = ((Byte)packetWrapper.read((Type)Type.BYTE)).byteValue();
/*     */                   if (position == 2)
/*     */                     packetWrapper.cancel(); 
/*     */                 });
/*     */           }
/*     */         });
/*  94 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_POSITION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  97 */             handler(packetWrapper -> {
/*     */                   Position position = (Position)packetWrapper.read(Type.POSITION);
/*     */                   
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.x()));
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.y()));
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.z()));
/*     */                 });
/*     */           }
/*     */         });
/* 106 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.UPDATE_HEALTH, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 109 */             map((Type)Type.FLOAT);
/* 110 */             map((Type)Type.VAR_INT, (Type)Type.SHORT);
/* 111 */             map((Type)Type.FLOAT);
/*     */           }
/*     */         });
/*     */     
/* 115 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 118 */             map((Type)Type.INT);
/* 119 */             map((Type)Type.UNSIGNED_BYTE);
/* 120 */             map((Type)Type.UNSIGNED_BYTE);
/* 121 */             map(Type.STRING);
/* 122 */             handler(packetWrapper -> {
/*     */                   if (!ViaRewind.getConfig().isReplaceAdventureMode())
/*     */                     return; 
/*     */                   if (((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 1)).shortValue() == 2)
/*     */                     packetWrapper.set((Type)Type.UNSIGNED_BYTE, 1, Short.valueOf((short)0)); 
/*     */                 });
/* 128 */             handler(packetWrapper -> {
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   tracker.setGamemode(((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 1)).shortValue());
/*     */                   if (tracker.getDimension() != ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue()) {
/*     */                     tracker.setDimension(((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue());
/*     */                     tracker.clearEntities();
/*     */                     tracker.getClientEntityTypes().put(Integer.valueOf(tracker.getPlayerId()), Entity1_10Types.EntityType.ENTITY_HUMAN);
/*     */                   } 
/*     */                 });
/* 137 */             handler(packetWrapper -> {
/*     */                   ClientWorld world = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
/*     */                   
/*     */                   world.setEnvironment(((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue());
/*     */                 });
/*     */           }
/*     */         });
/* 144 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.PLAYER_POSITION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 147 */             map((Type)Type.DOUBLE);
/* 148 */             map((Type)Type.DOUBLE);
/* 149 */             map((Type)Type.DOUBLE);
/* 150 */             map((Type)Type.FLOAT);
/* 151 */             map((Type)Type.FLOAT);
/* 152 */             handler(packetWrapper -> {
/*     */                   PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
/*     */                   
/*     */                   playerPosition.setPositionPacketReceived(true);
/*     */                   int flags = ((Byte)packetWrapper.read((Type)Type.BYTE)).byteValue();
/*     */                   if ((flags & 0x1) == 1) {
/*     */                     double x = ((Double)packetWrapper.get((Type)Type.DOUBLE, 0)).doubleValue();
/*     */                     x += playerPosition.getPosX();
/*     */                     packetWrapper.set((Type)Type.DOUBLE, 0, Double.valueOf(x));
/*     */                   } 
/*     */                   double y = ((Double)packetWrapper.get((Type)Type.DOUBLE, 1)).doubleValue();
/*     */                   if ((flags & 0x2) == 2) {
/*     */                     y += playerPosition.getPosY();
/*     */                   }
/*     */                   playerPosition.setReceivedPosY(y);
/*     */                   y += 1.6200000047683716D;
/*     */                   packetWrapper.set((Type)Type.DOUBLE, 1, Double.valueOf(y));
/*     */                   if ((flags & 0x4) == 4) {
/*     */                     double z = ((Double)packetWrapper.get((Type)Type.DOUBLE, 2)).doubleValue();
/*     */                     z += playerPosition.getPosZ();
/*     */                     packetWrapper.set((Type)Type.DOUBLE, 2, Double.valueOf(z));
/*     */                   } 
/*     */                   if ((flags & 0x8) == 8) {
/*     */                     float yaw = ((Float)packetWrapper.get((Type)Type.FLOAT, 0)).floatValue();
/*     */                     yaw += playerPosition.getYaw();
/*     */                     packetWrapper.set((Type)Type.FLOAT, 0, Float.valueOf(yaw));
/*     */                   } 
/*     */                   if ((flags & 0x10) == 16) {
/*     */                     float pitch = ((Float)packetWrapper.get((Type)Type.FLOAT, 1)).floatValue();
/*     */                     pitch += playerPosition.getPitch();
/*     */                     packetWrapper.set((Type)Type.FLOAT, 1, Float.valueOf(pitch));
/*     */                   } 
/*     */                 });
/* 185 */             handler(packetWrapper -> {
/*     */                   PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
/*     */                   packetWrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(playerPosition.isOnGround()));
/*     */                 });
/* 189 */             handler(packetWrapper -> {
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   
/*     */                   if (tracker.getSpectating() != tracker.getPlayerId()) {
/*     */                     packetWrapper.cancel();
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 198 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SET_EXPERIENCE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 201 */             map((Type)Type.FLOAT);
/* 202 */             map((Type)Type.VAR_INT, (Type)Type.SHORT);
/* 203 */             map((Type)Type.VAR_INT, (Type)Type.SHORT);
/*     */           }
/*     */         });
/*     */     
/* 207 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.GAME_EVENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 210 */             map((Type)Type.UNSIGNED_BYTE);
/* 211 */             map((Type)Type.FLOAT);
/* 212 */             handler(packetWrapper -> {
/*     */                   int mode = ((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   if (mode != 3) {
/*     */                     return;
/*     */                   }
/*     */                   int gamemode = ((Float)packetWrapper.get((Type)Type.FLOAT, 0)).intValue();
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   if (gamemode == 3 || tracker.getGamemode() == 3) {
/*     */                     UUID myId = packetWrapper.user().getProtocolInfo().getUuid();
/*     */                     Item[] equipment = new Item[4];
/*     */                     if (gamemode == 3) {
/*     */                       GameProfileStorage.GameProfile profile = ((GameProfileStorage)packetWrapper.user().get(GameProfileStorage.class)).get(myId);
/*     */                       equipment[3] = profile.getSkull();
/*     */                     } else {
/*     */                       for (int j = 0; j < equipment.length; j++)
/*     */                         equipment[j] = tracker.getPlayerEquipment(myId, j); 
/*     */                     } 
/*     */                     for (int i = 0; i < equipment.length; i++) {
/*     */                       PacketWrapper setSlot = PacketWrapper.create((PacketType)ClientboundPackets1_7.SET_SLOT, packetWrapper.user());
/*     */                       setSlot.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/*     */                       setSlot.write((Type)Type.SHORT, Short.valueOf((short)(8 - i)));
/*     */                       setSlot.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, equipment[i]);
/*     */                       PacketUtil.sendPacket(setSlot, Protocol1_7_6_10TO1_8.class);
/*     */                     } 
/*     */                   } 
/*     */                 });
/* 238 */             handler(packetWrapper -> {
/*     */                   int mode = ((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   
/*     */                   if (mode == 3) {
/*     */                     int gamemode = ((Float)packetWrapper.get((Type)Type.FLOAT, 0)).intValue();
/*     */                     if (gamemode == 2 && ViaRewind.getConfig().isReplaceAdventureMode()) {
/*     */                       gamemode = 0;
/*     */                       packetWrapper.set((Type)Type.FLOAT, 0, Float.valueOf(0.0F));
/*     */                     } 
/*     */                     ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).setGamemode(gamemode);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 252 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.OPEN_SIGN_EDITOR, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 255 */             handler(packetWrapper -> {
/*     */                   Position position = (Position)packetWrapper.read(Type.POSITION);
/*     */                   
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.x()));
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.y()));
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.z()));
/*     */                 });
/*     */           }
/*     */         });
/* 264 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.PLAYER_INFO, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 267 */             handler(packetWrapper -> {
/*     */                   packetWrapper.cancel();
/*     */                   
/*     */                   int action = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   int count = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   GameProfileStorage gameProfileStorage = (GameProfileStorage)packetWrapper.user().get(GameProfileStorage.class);
/*     */                   
/*     */                   for (int i = 0; i < count; i++) {
/*     */                     UUID uuid = (UUID)packetWrapper.read(Type.UUID);
/*     */                     
/*     */                     if (action == 0) {
/*     */                       String name = (String)packetWrapper.read(Type.STRING);
/*     */                       
/*     */                       GameProfileStorage.GameProfile gameProfile = gameProfileStorage.get(uuid);
/*     */                       
/*     */                       if (gameProfile == null) {
/*     */                         gameProfile = gameProfileStorage.put(uuid, name);
/*     */                       }
/*     */                       
/*     */                       int propertyCount = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                       
/*     */                       while (propertyCount-- > 0) {
/*     */                         String propertyName = (String)packetWrapper.read(Type.STRING);
/*     */                         
/*     */                         String propertyValue = (String)packetWrapper.read(Type.STRING);
/*     */                         
/*     */                         String propertySignature = (String)packetWrapper.read(Type.OPTIONAL_STRING);
/*     */                         
/*     */                         gameProfile.properties.add(new GameProfileStorage.Property(propertyName, propertyValue, propertySignature));
/*     */                       } 
/*     */                       int gamemode = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                       int ping = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                       gameProfile.ping = ping;
/*     */                       gameProfile.gamemode = gamemode;
/*     */                       JsonElement displayName = (JsonElement)packetWrapper.read(Type.OPTIONAL_COMPONENT);
/*     */                       if (displayName != null) {
/*     */                         gameProfile.setDisplayName(ChatUtil.jsonToLegacy(displayName));
/*     */                       }
/*     */                       PacketWrapper packet = PacketWrapper.create(56, null, packetWrapper.user());
/*     */                       packet.write(Type.STRING, gameProfile.getDisplayName());
/*     */                       packet.write((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */                       packet.write((Type)Type.SHORT, Short.valueOf((short)ping));
/*     */                       PacketUtil.sendPacket(packet, Protocol1_7_6_10TO1_8.class);
/*     */                     } else if (action == 1) {
/*     */                       int gamemode = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                       GameProfileStorage.GameProfile gameProfile = gameProfileStorage.get(uuid);
/*     */                       if (gameProfile != null && gameProfile.gamemode != gamemode) {
/*     */                         if (gamemode == 3 || gameProfile.gamemode == 3) {
/*     */                           EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                           int entityId = tracker.getPlayerEntityId(uuid);
/*     */                           boolean isOwnPlayer = (entityId == tracker.getPlayerId());
/*     */                           if (entityId != -1) {
/*     */                             Item[] equipment = new Item[isOwnPlayer ? 4 : 5];
/*     */                             if (gamemode == 3) {
/*     */                               equipment[isOwnPlayer ? 3 : 4] = gameProfile.getSkull();
/*     */                             } else {
/*     */                               for (int j = 0; j < equipment.length; j++) {
/*     */                                 equipment[j] = tracker.getPlayerEquipment(uuid, j);
/*     */                               }
/*     */                             } 
/*     */                             short slot;
/*     */                             for (slot = 0; slot < equipment.length; slot = (short)(slot + 1)) {
/*     */                               PacketWrapper equipmentPacket = PacketWrapper.create((PacketType)ClientboundPackets1_7.ENTITY_EQUIPMENT, packetWrapper.user());
/*     */                               equipmentPacket.write((Type)Type.INT, Integer.valueOf(entityId));
/*     */                               equipmentPacket.write((Type)Type.SHORT, Short.valueOf(slot));
/*     */                               equipmentPacket.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, equipment[slot]);
/*     */                               PacketUtil.sendPacket(equipmentPacket, Protocol1_7_6_10TO1_8.class);
/*     */                             } 
/*     */                           } 
/*     */                         } 
/*     */                         gameProfile.gamemode = gamemode;
/*     */                       } 
/*     */                     } else if (action == 2) {
/*     */                       int ping = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                       GameProfileStorage.GameProfile gameProfile = gameProfileStorage.get(uuid);
/*     */                       if (gameProfile != null) {
/*     */                         PacketWrapper packet = PacketWrapper.create(56, null, packetWrapper.user());
/*     */                         packet.write(Type.STRING, gameProfile.getDisplayName());
/*     */                         packet.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                         packet.write((Type)Type.SHORT, Short.valueOf((short)gameProfile.ping));
/*     */                         PacketUtil.sendPacket(packet, Protocol1_7_6_10TO1_8.class);
/*     */                         gameProfile.ping = ping;
/*     */                         packet = PacketWrapper.create(56, null, packetWrapper.user());
/*     */                         packet.write(Type.STRING, gameProfile.getDisplayName());
/*     */                         packet.write((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */                         packet.write((Type)Type.SHORT, Short.valueOf((short)ping));
/*     */                         PacketUtil.sendPacket(packet, Protocol1_7_6_10TO1_8.class);
/*     */                       } 
/*     */                     } else if (action == 3) {
/*     */                       JsonElement displayNameComponent = (JsonElement)packetWrapper.read(Type.OPTIONAL_COMPONENT);
/*     */                       String displayName = (displayNameComponent != null) ? ChatUtil.jsonToLegacy(displayNameComponent) : null;
/*     */                       GameProfileStorage.GameProfile gameProfile = gameProfileStorage.get(uuid);
/*     */                       if (gameProfile != null && (gameProfile.displayName != null || displayName != null)) {
/*     */                         PacketWrapper packet = PacketWrapper.create(56, null, packetWrapper.user());
/*     */                         packet.write(Type.STRING, gameProfile.getDisplayName());
/*     */                         packet.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                         packet.write((Type)Type.SHORT, Short.valueOf((short)gameProfile.ping));
/*     */                         PacketUtil.sendPacket(packet, Protocol1_7_6_10TO1_8.class);
/*     */                         if ((gameProfile.displayName == null && displayName != null) || (gameProfile.displayName != null && displayName == null) || !gameProfile.displayName.equals(displayName)) {
/*     */                           gameProfile.setDisplayName(displayName);
/*     */                         }
/*     */                         packet = PacketWrapper.create(56, null, packetWrapper.user());
/*     */                         packet.write(Type.STRING, gameProfile.getDisplayName());
/*     */                         packet.write((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */                         packet.write((Type)Type.SHORT, Short.valueOf((short)gameProfile.ping));
/*     */                         PacketUtil.sendPacket(packet, Protocol1_7_6_10TO1_8.class);
/*     */                       } 
/*     */                     } else if (action == 4) {
/*     */                       GameProfileStorage.GameProfile gameProfile = gameProfileStorage.remove(uuid);
/*     */                       if (gameProfile != null) {
/*     */                         PacketWrapper packet = PacketWrapper.create(56, null, packetWrapper.user());
/*     */                         packet.write(Type.STRING, gameProfile.getDisplayName());
/*     */                         packet.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                         packet.write((Type)Type.SHORT, Short.valueOf((short)gameProfile.ping));
/*     */                         PacketUtil.sendPacket(packet, Protocol1_7_6_10TO1_8.class);
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 390 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.PLAYER_ABILITIES, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 393 */             map((Type)Type.BYTE);
/* 394 */             map((Type)Type.FLOAT);
/* 395 */             map((Type)Type.FLOAT);
/* 396 */             handler(packetWrapper -> {
/*     */                   byte flags = ((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   
/*     */                   float flySpeed = ((Float)packetWrapper.get((Type)Type.FLOAT, 0)).floatValue();
/*     */                   float walkSpeed = ((Float)packetWrapper.get((Type)Type.FLOAT, 1)).floatValue();
/*     */                   PlayerAbilities abilities = (PlayerAbilities)packetWrapper.user().get(PlayerAbilities.class);
/*     */                   abilities.setInvincible(((flags & 0x8) == 8));
/*     */                   abilities.setAllowFly(((flags & 0x4) == 4));
/*     */                   abilities.setFlying(((flags & 0x2) == 2));
/*     */                   abilities.setCreative(((flags & 0x1) == 1));
/*     */                   abilities.setFlySpeed(flySpeed);
/*     */                   abilities.setWalkSpeed(walkSpeed);
/*     */                   if (abilities.isSprinting() && abilities.isFlying()) {
/*     */                     packetWrapper.set((Type)Type.FLOAT, 0, Float.valueOf(abilities.getFlySpeed() * 2.0F));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 414 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 417 */             map(Type.STRING);
/* 418 */             handler(packetWrapper -> {
/*     */                   String channel = (String)packetWrapper.get(Type.STRING, 0);
/*     */                   
/*     */                   if (channel.equalsIgnoreCase("MC|TrList")) {
/*     */                     int size;
/*     */                     
/*     */                     packetWrapper.passthrough((Type)Type.INT);
/*     */                     
/*     */                     if (packetWrapper.isReadable((Type)Type.BYTE, 0)) {
/*     */                       size = ((Byte)packetWrapper.passthrough((Type)Type.BYTE)).byteValue();
/*     */                     } else {
/*     */                       size = ((Short)packetWrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                     } 
/*     */                     
/*     */                     for (int i = 0; i < size; i++) {
/*     */                       Item item = ItemRewriter.toClient((Item)packetWrapper.read(Type.ITEM));
/*     */                       
/*     */                       packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, item);
/*     */                       
/*     */                       item = ItemRewriter.toClient((Item)packetWrapper.read(Type.ITEM));
/*     */                       
/*     */                       packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, item);
/*     */                       
/*     */                       boolean has3Items = ((Boolean)packetWrapper.passthrough((Type)Type.BOOLEAN)).booleanValue();
/*     */                       if (has3Items) {
/*     */                         item = ItemRewriter.toClient((Item)packetWrapper.read(Type.ITEM));
/*     */                         packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, item);
/*     */                       } 
/*     */                       packetWrapper.passthrough((Type)Type.BOOLEAN);
/*     */                       packetWrapper.read((Type)Type.INT);
/*     */                       packetWrapper.read((Type)Type.INT);
/*     */                     } 
/*     */                   } else if (channel.equalsIgnoreCase("MC|Brand")) {
/*     */                     packetWrapper.write(Type.REMAINING_BYTES, ((String)packetWrapper.read(Type.STRING)).getBytes(StandardCharsets.UTF_8));
/*     */                   } 
/*     */                   packetWrapper.cancel();
/*     */                   packetWrapper.setId(-1);
/*     */                   ByteBuf newPacketBuf = Unpooled.buffer();
/*     */                   packetWrapper.writeToBuffer(newPacketBuf);
/*     */                   PacketWrapper newWrapper = PacketWrapper.create(63, newPacketBuf, packetWrapper.user());
/*     */                   newWrapper.passthrough(Type.STRING);
/*     */                   if (newPacketBuf.readableBytes() <= 32767) {
/*     */                     newWrapper.write((Type)Type.SHORT, Short.valueOf((short)newPacketBuf.readableBytes()));
/*     */                     newWrapper.send(Protocol1_7_6_10TO1_8.class);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 466 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.CAMERA, null, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 469 */             handler(packetWrapper -> {
/*     */                   packetWrapper.cancel();
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   
/*     */                   int entityId = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   int spectating = tracker.getSpectating();
/*     */                   
/*     */                   if (spectating != entityId) {
/*     */                     tracker.setSpectating(entityId);
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 485 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.TITLE, null, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 488 */             handler(packetWrapper -> {
/*     */                   packetWrapper.cancel();
/*     */                   TitleRenderProvider titleRenderProvider = (TitleRenderProvider)Via.getManager().getProviders().get(TitleRenderProvider.class);
/*     */                   if (titleRenderProvider == null) {
/*     */                     return;
/*     */                   }
/*     */                   int action = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   UUID uuid = Utils.getUUID(packetWrapper.user());
/*     */                   switch (action) {
/*     */                     case 0:
/*     */                       titleRenderProvider.setTitle(uuid, (String)packetWrapper.read(Type.STRING));
/*     */                       break;
/*     */                     case 1:
/*     */                       titleRenderProvider.setSubTitle(uuid, (String)packetWrapper.read(Type.STRING));
/*     */                       break;
/*     */                     case 2:
/*     */                       titleRenderProvider.setTimings(uuid, ((Integer)packetWrapper.read((Type)Type.INT)).intValue(), ((Integer)packetWrapper.read((Type)Type.INT)).intValue(), ((Integer)packetWrapper.read((Type)Type.INT)).intValue());
/*     */                       break;
/*     */                     case 3:
/*     */                       titleRenderProvider.clear(uuid);
/*     */                       break;
/*     */                     case 4:
/*     */                       titleRenderProvider.reset(uuid);
/*     */                       break;
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 516 */     protocol.cancelClientbound((ClientboundPacketType)ClientboundPackets1_8.TAB_LIST);
/*     */ 
/*     */     
/* 519 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.RESOURCE_PACK, (ClientboundPacketType)ClientboundPackets1_7.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 522 */             create(Type.STRING, "MC|RPack");
/* 523 */             handler(packetWrapper -> {
/*     */                   ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
/*     */                   
/*     */                   try {
/*     */                     Type.STRING.write(buf, packetWrapper.read(Type.STRING));
/*     */                     
/*     */                     packetWrapper.write(Type.SHORT_BYTE_ARRAY, Type.REMAINING_BYTES.read(buf));
/*     */                   } finally {
/*     */                     buf.release();
/*     */                   } 
/*     */                 });
/* 534 */             map(Type.STRING, (Type)Type.NOTHING);
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 540 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_7.CHAT_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 543 */             map(Type.STRING);
/* 544 */             handler(packetWrapper -> {
/*     */                   String msg = (String)packetWrapper.get(Type.STRING, 0);
/*     */                   
/*     */                   int gamemode = ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getGamemode();
/*     */                   
/*     */                   if (gamemode == 3 && msg.toLowerCase().startsWith("/stp ")) {
/*     */                     String username = msg.split(" ")[1];
/*     */                     
/*     */                     GameProfileStorage storage = (GameProfileStorage)packetWrapper.user().get(GameProfileStorage.class);
/*     */                     GameProfileStorage.GameProfile profile = storage.get(username, true);
/*     */                     if (profile != null && profile.uuid != null) {
/*     */                       packetWrapper.cancel();
/*     */                       PacketWrapper teleportPacket = PacketWrapper.create(24, null, packetWrapper.user());
/*     */                       teleportPacket.write(Type.UUID, profile.uuid);
/*     */                       PacketUtil.sendToServer(teleportPacket, Protocol1_7_6_10TO1_8.class, true, true);
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 564 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_7.INTERACT_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 567 */             map((Type)Type.INT, (Type)Type.VAR_INT);
/* 568 */             map((Type)Type.BYTE, (Type)Type.VAR_INT);
/* 569 */             handler(packetWrapper -> {
/*     */                   int mode = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 1)).intValue();
/*     */                   if (mode != 0)
/*     */                     return; 
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   EntityReplacement replacement = tracker.getEntityReplacement(entityId);
/*     */                   if (!(replacement instanceof ArmorStandReplacement))
/*     */                     return; 
/*     */                   ArmorStandReplacement armorStand = (ArmorStandReplacement)replacement;
/*     */                   AABB boundingBox = armorStand.getBoundingBox();
/*     */                   PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
/*     */                   Vector3d pos = new Vector3d(playerPosition.getPosX(), playerPosition.getPosY() + 1.8D, playerPosition.getPosZ());
/*     */                   double yaw = Math.toRadians(playerPosition.getYaw());
/*     */                   double pitch = Math.toRadians(playerPosition.getPitch());
/*     */                   Vector3d dir = new Vector3d(-Math.cos(pitch) * Math.sin(yaw), -Math.sin(pitch), Math.cos(pitch) * Math.cos(yaw));
/*     */                   Ray3d ray = new Ray3d(pos, dir);
/*     */                   Vector3d intersection = RayTracing.trace(ray, boundingBox, 5.0D);
/*     */                   if (intersection == null)
/*     */                     return; 
/*     */                   intersection.substract(boundingBox.getMin());
/*     */                   mode = 2;
/*     */                   packetWrapper.set((Type)Type.VAR_INT, 1, Integer.valueOf(mode));
/*     */                   packetWrapper.write((Type)Type.FLOAT, Float.valueOf((float)intersection.getX()));
/*     */                   packetWrapper.write((Type)Type.FLOAT, Float.valueOf((float)intersection.getY()));
/*     */                   packetWrapper.write((Type)Type.FLOAT, Float.valueOf((float)intersection.getZ()));
/*     */                 }); } });
/* 596 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_7.PLAYER_MOVEMENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 599 */             map((Type)Type.BOOLEAN);
/* 600 */             handler(packetWrapper -> {
/*     */                   PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
/*     */                   
/*     */                   playerPosition.setOnGround(((Boolean)packetWrapper.get((Type)Type.BOOLEAN, 0)).booleanValue());
/*     */                 });
/*     */           }
/*     */         });
/* 607 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_7.PLAYER_POSITION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 610 */             map((Type)Type.DOUBLE);
/* 611 */             map((Type)Type.DOUBLE);
/* 612 */             map((Type)Type.DOUBLE, (Type)Type.NOTHING);
/* 613 */             map((Type)Type.DOUBLE);
/* 614 */             map((Type)Type.BOOLEAN);
/* 615 */             handler(packetWrapper -> {
/*     */                   double x = ((Double)packetWrapper.get((Type)Type.DOUBLE, 0)).doubleValue();
/*     */                   
/*     */                   double feetY = ((Double)packetWrapper.get((Type)Type.DOUBLE, 1)).doubleValue();
/*     */                   
/*     */                   double z = ((Double)packetWrapper.get((Type)Type.DOUBLE, 2)).doubleValue();
/*     */                   
/*     */                   PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
/*     */                   
/*     */                   if (playerPosition.isPositionPacketReceived()) {
/*     */                     playerPosition.setPositionPacketReceived(false);
/*     */                     feetY -= 0.01D;
/*     */                     packetWrapper.set((Type)Type.DOUBLE, 1, Double.valueOf(feetY));
/*     */                   } 
/*     */                   playerPosition.setOnGround(((Boolean)packetWrapper.get((Type)Type.BOOLEAN, 0)).booleanValue());
/*     */                   playerPosition.setPos(x, feetY, z);
/*     */                 });
/*     */           }
/*     */         });
/* 634 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_7.PLAYER_ROTATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 637 */             map((Type)Type.FLOAT);
/* 638 */             map((Type)Type.FLOAT);
/* 639 */             map((Type)Type.BOOLEAN);
/* 640 */             handler(packetWrapper -> {
/*     */                   PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
/*     */                   
/*     */                   playerPosition.setYaw(((Float)packetWrapper.get((Type)Type.FLOAT, 0)).floatValue());
/*     */                   playerPosition.setPitch(((Float)packetWrapper.get((Type)Type.FLOAT, 1)).floatValue());
/*     */                   playerPosition.setOnGround(((Boolean)packetWrapper.get((Type)Type.BOOLEAN, 0)).booleanValue());
/*     */                 });
/*     */           }
/*     */         });
/* 649 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_7.PLAYER_POSITION_AND_ROTATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 652 */             map((Type)Type.DOUBLE);
/* 653 */             map((Type)Type.DOUBLE);
/* 654 */             map((Type)Type.DOUBLE, (Type)Type.NOTHING);
/* 655 */             map((Type)Type.DOUBLE);
/* 656 */             map((Type)Type.FLOAT);
/* 657 */             map((Type)Type.FLOAT);
/* 658 */             map((Type)Type.BOOLEAN);
/* 659 */             handler(packetWrapper -> {
/*     */                   double x = ((Double)packetWrapper.get((Type)Type.DOUBLE, 0)).doubleValue();
/*     */                   
/*     */                   double feetY = ((Double)packetWrapper.get((Type)Type.DOUBLE, 1)).doubleValue();
/*     */                   
/*     */                   double z = ((Double)packetWrapper.get((Type)Type.DOUBLE, 2)).doubleValue();
/*     */                   
/*     */                   float yaw = ((Float)packetWrapper.get((Type)Type.FLOAT, 0)).floatValue();
/*     */                   
/*     */                   float pitch = ((Float)packetWrapper.get((Type)Type.FLOAT, 1)).floatValue();
/*     */                   
/*     */                   PlayerPosition playerPosition = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
/*     */                   if (playerPosition.isPositionPacketReceived()) {
/*     */                     playerPosition.setPositionPacketReceived(false);
/*     */                     feetY = playerPosition.getReceivedPosY();
/*     */                     packetWrapper.set((Type)Type.DOUBLE, 1, Double.valueOf(feetY));
/*     */                   } 
/*     */                   playerPosition.setOnGround(((Boolean)packetWrapper.get((Type)Type.BOOLEAN, 0)).booleanValue());
/*     */                   playerPosition.setPos(x, feetY, z);
/*     */                   playerPosition.setYaw(yaw);
/*     */                   playerPosition.setPitch(pitch);
/*     */                 });
/*     */           }
/*     */         });
/* 683 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_7.PLAYER_DIGGING, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 686 */             map((Type)Type.VAR_INT);
/* 687 */             handler(packetWrapper -> {
/*     */                   int x = ((Integer)packetWrapper.read((Type)Type.INT)).intValue();
/*     */                   
/*     */                   int y = ((Short)packetWrapper.read((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                   int z = ((Integer)packetWrapper.read((Type)Type.INT)).intValue();
/*     */                   packetWrapper.write(Type.POSITION, new Position(x, y, z));
/*     */                 });
/*     */           }
/*     */         });
/* 696 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_7.PLAYER_BLOCK_PLACEMENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 699 */             handler(packetWrapper -> {
/*     */                   int x = ((Integer)packetWrapper.read((Type)Type.INT)).intValue();
/*     */                   
/*     */                   int y = ((Short)packetWrapper.read((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                   
/*     */                   int z = ((Integer)packetWrapper.read((Type)Type.INT)).intValue();
/*     */                   
/*     */                   packetWrapper.write(Type.POSITION, new Position(x, y, z));
/*     */                   packetWrapper.passthrough((Type)Type.BYTE);
/*     */                   Item item = (Item)packetWrapper.read(Types1_7_6_10.COMPRESSED_NBT_ITEM);
/*     */                   item = ItemRewriter.toServer(item);
/*     */                   packetWrapper.write(Type.ITEM, item);
/*     */                   for (int i = 0; i < 3; i++) {
/*     */                     packetWrapper.passthrough((Type)Type.BYTE);
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 717 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_7.ANIMATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 720 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.read((Type)Type.INT)).intValue();
/*     */                   int animation = ((Byte)packetWrapper.read((Type)Type.BYTE)).byteValue();
/*     */                   if (animation == 1) {
/*     */                     return;
/*     */                   }
/*     */                   packetWrapper.cancel();
/*     */                   switch (animation) {
/*     */                     case 104:
/*     */                       animation = 0;
/*     */                       break;
/*     */                     case 105:
/*     */                       animation = 1;
/*     */                       break;
/*     */                     case 3:
/*     */                       animation = 2;
/*     */                       break;
/*     */                     default:
/*     */                       return;
/*     */                   } 
/*     */                   PacketWrapper entityAction = PacketWrapper.create(11, null, packetWrapper.user());
/*     */                   entityAction.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/*     */                   entityAction.write((Type)Type.VAR_INT, Integer.valueOf(animation));
/*     */                   entityAction.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                   PacketUtil.sendPacket(entityAction, Protocol1_7_6_10TO1_8.class, true, true);
/*     */                 });
/*     */           }
/*     */         });
/* 748 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_7.ENTITY_ACTION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 751 */             map((Type)Type.INT, (Type)Type.VAR_INT);
/* 752 */             handler(packetWrapper -> packetWrapper.write((Type)Type.VAR_INT, Integer.valueOf(((Byte)packetWrapper.read((Type)Type.BYTE)).byteValue() - 1)));
/* 753 */             map((Type)Type.INT, (Type)Type.VAR_INT);
/* 754 */             handler(packetWrapper -> {
/*     */                   int action = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 1)).intValue();
/*     */                   
/*     */                   if (action == 3 || action == 4) {
/*     */                     PlayerAbilities abilities = (PlayerAbilities)packetWrapper.user().get(PlayerAbilities.class);
/*     */                     abilities.setSprinting((action == 3));
/*     */                     PacketWrapper abilitiesPacket = PacketWrapper.create(57, null, packetWrapper.user());
/*     */                     abilitiesPacket.write((Type)Type.BYTE, Byte.valueOf(abilities.getFlags()));
/*     */                     abilitiesPacket.write((Type)Type.FLOAT, Float.valueOf(abilities.isSprinting() ? (abilities.getFlySpeed() * 2.0F) : abilities.getFlySpeed()));
/*     */                     abilitiesPacket.write((Type)Type.FLOAT, Float.valueOf(abilities.getWalkSpeed()));
/*     */                     PacketUtil.sendPacket(abilitiesPacket, Protocol1_7_6_10TO1_8.class);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 769 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_7.STEER_VEHICLE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 772 */             map((Type)Type.FLOAT);
/* 773 */             map((Type)Type.FLOAT);
/* 774 */             handler(packetWrapper -> {
/*     */                   boolean jump = ((Boolean)packetWrapper.read((Type)Type.BOOLEAN)).booleanValue();
/*     */                   boolean unmount = ((Boolean)packetWrapper.read((Type)Type.BOOLEAN)).booleanValue();
/*     */                   short flags = 0;
/*     */                   if (jump) {
/*     */                     flags = (short)(flags + 1);
/*     */                   }
/*     */                   if (unmount) {
/*     */                     flags = (short)(flags + 2);
/*     */                   }
/*     */                   packetWrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf(flags));
/*     */                   if (unmount) {
/*     */                     EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                     if (tracker.getSpectating() != tracker.getPlayerId()) {
/*     */                       PacketWrapper sneakPacket = PacketWrapper.create(11, null, packetWrapper.user());
/*     */                       sneakPacket.write((Type)Type.VAR_INT, Integer.valueOf(tracker.getPlayerId()));
/*     */                       sneakPacket.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                       sneakPacket.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                       PacketWrapper unsneakPacket = PacketWrapper.create(11, null, packetWrapper.user());
/*     */                       unsneakPacket.write((Type)Type.VAR_INT, Integer.valueOf(tracker.getPlayerId()));
/*     */                       unsneakPacket.write((Type)Type.VAR_INT, Integer.valueOf(1));
/*     */                       unsneakPacket.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                       PacketUtil.sendToServer(sneakPacket, Protocol1_7_6_10TO1_8.class);
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 802 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_7.UPDATE_SIGN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 805 */             handler(packetWrapper -> {
/*     */                   int x = ((Integer)packetWrapper.read((Type)Type.INT)).intValue();
/*     */                   
/*     */                   int y = ((Short)packetWrapper.read((Type)Type.SHORT)).shortValue();
/*     */                   int z = ((Integer)packetWrapper.read((Type)Type.INT)).intValue();
/*     */                   packetWrapper.write(Type.POSITION, new Position(x, y, z));
/*     */                   for (int i = 0; i < 4; i++) {
/*     */                     String line = (String)packetWrapper.read(Type.STRING);
/*     */                     line = ChatUtil.legacyToJson(line);
/*     */                     packetWrapper.write(Type.COMPONENT, JsonParser.parseString(line));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 819 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_7.PLAYER_ABILITIES, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 822 */             map((Type)Type.BYTE);
/* 823 */             map((Type)Type.FLOAT);
/* 824 */             map((Type)Type.FLOAT);
/* 825 */             handler(packetWrapper -> {
/*     */                   PlayerAbilities abilities = (PlayerAbilities)packetWrapper.user().get(PlayerAbilities.class);
/*     */                   
/*     */                   if (abilities.isAllowFly()) {
/*     */                     byte flags = ((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                     abilities.setFlying(((flags & 0x2) == 2));
/*     */                   } 
/*     */                   packetWrapper.set((Type)Type.FLOAT, 0, Float.valueOf(abilities.getFlySpeed()));
/*     */                 });
/*     */           }
/*     */         });
/* 836 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_7.TAB_COMPLETE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 839 */             map(Type.STRING);
/* 840 */             create(Type.OPTIONAL_POSITION, null);
/* 841 */             handler(packetWrapper -> {
/*     */                   String msg = (String)packetWrapper.get(Type.STRING, 0);
/*     */                   
/*     */                   if (msg.toLowerCase().startsWith("/stp ")) {
/*     */                     packetWrapper.cancel();
/*     */                     
/*     */                     String[] args = msg.split(" ");
/*     */                     
/*     */                     if (args.length <= 2) {
/*     */                       String prefix = (args.length == 1) ? "" : args[1];
/*     */                       
/*     */                       GameProfileStorage storage = (GameProfileStorage)packetWrapper.user().get(GameProfileStorage.class);
/*     */                       List<GameProfileStorage.GameProfile> profiles = storage.getAllWithPrefix(prefix, true);
/*     */                       PacketWrapper tabComplete = PacketWrapper.create(58, null, packetWrapper.user());
/*     */                       tabComplete.write((Type)Type.VAR_INT, Integer.valueOf(profiles.size()));
/*     */                       for (GameProfileStorage.GameProfile profile : profiles) {
/*     */                         tabComplete.write(Type.STRING, profile.name);
/*     */                       }
/*     */                       PacketUtil.sendPacket(tabComplete, Protocol1_7_6_10TO1_8.class);
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 865 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_7.CLIENT_SETTINGS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 868 */             map(Type.STRING);
/* 869 */             map((Type)Type.BYTE);
/* 870 */             map((Type)Type.BYTE);
/* 871 */             map((Type)Type.BOOLEAN);
/* 872 */             map((Type)Type.BYTE, (Type)Type.NOTHING);
/* 873 */             handler(packetWrapper -> {
/*     */                   boolean cape = ((Boolean)packetWrapper.read((Type)Type.BOOLEAN)).booleanValue();
/*     */                   
/*     */                   packetWrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)(cape ? 127 : 126)));
/*     */                 });
/*     */           }
/*     */         });
/* 880 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_7.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 883 */             map(Type.STRING);
/* 884 */             map((Type)Type.SHORT, (Type)Type.NOTHING);
/* 885 */             handler(packetWrapper -> {
/*     */                   byte[] data;
/*     */                   Item book;
/*     */                   String name;
/*     */                   CompoundTag tag;
/*     */                   Windows windows;
/*     */                   PacketWrapper updateCost;
/*     */                   String channel = (String)packetWrapper.get(Type.STRING, 0);
/*     */                   switch (channel) {
/*     */                     case "MC|TrSel":
/*     */                       packetWrapper.passthrough((Type)Type.INT);
/*     */                       packetWrapper.read(Type.REMAINING_BYTES);
/*     */                       break;
/*     */                     case "MC|ItemName":
/*     */                       data = (byte[])packetWrapper.read(Type.REMAINING_BYTES);
/*     */                       name = new String(data, StandardCharsets.UTF_8);
/*     */                       packetWrapper.write(Type.STRING, name);
/*     */                       windows = (Windows)packetWrapper.user().get(Windows.class);
/*     */                       updateCost = PacketWrapper.create(49, null, packetWrapper.user());
/*     */                       updateCost.write((Type)Type.UNSIGNED_BYTE, Short.valueOf(windows.anvilId));
/*     */                       updateCost.write((Type)Type.SHORT, Short.valueOf((short)0));
/*     */                       updateCost.write((Type)Type.SHORT, Short.valueOf(windows.levelCost));
/*     */                       PacketUtil.sendPacket(updateCost, Protocol1_7_6_10TO1_8.class, true, true);
/*     */                       break;
/*     */                     case "MC|BEdit":
/*     */                     case "MC|BSign":
/*     */                       book = (Item)packetWrapper.read(Types1_7_6_10.COMPRESSED_NBT_ITEM);
/*     */                       tag = book.tag();
/*     */                       if (tag != null && tag.contains("pages")) {
/*     */                         ListTag pages = (ListTag)tag.get("pages");
/*     */                         for (int i = 0; i < pages.size(); i++) {
/*     */                           StringTag page = (StringTag)pages.get(i);
/*     */                           String value = page.getValue();
/*     */                           value = ChatUtil.legacyToJson(value);
/*     */                           page.setValue(value);
/*     */                         } 
/*     */                       } 
/*     */                       packetWrapper.write(Type.ITEM, book);
/*     */                       break;
/*     */                     case "MC|Brand":
/*     */                       packetWrapper.write(Type.STRING, new String((byte[])packetWrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8));
/*     */                       break;
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\packets\PlayerPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
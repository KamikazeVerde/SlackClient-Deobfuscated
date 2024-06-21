/*     */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_8;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
/*     */ import de.gerrygames.viarewind.ViaRewind;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ItemRewriter;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.BlockPlaceDestroyTracker;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.BossBarStorage;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.PlayerPosition;
/*     */ import de.gerrygames.viarewind.utils.ChatUtil;
/*     */ import de.gerrygames.viarewind.utils.PacketUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.TimerTask;
/*     */ import java.util.UUID;
/*     */ 
/*     */ public class PlayerPackets {
/*     */   public static void register(Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8> protocol) {
/*  42 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.BOSSBAR, null, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  45 */             handler(packetWrapper -> {
/*     */                   packetWrapper.cancel();
/*     */                   
/*     */                   UUID uuid = (UUID)packetWrapper.read(Type.UUID);
/*     */                   
/*     */                   int action = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   BossBarStorage bossBarStorage = (BossBarStorage)packetWrapper.user().get(BossBarStorage.class);
/*     */                   
/*     */                   if (action == 0) {
/*     */                     bossBarStorage.add(uuid, ChatUtil.jsonToLegacy((JsonElement)packetWrapper.read(Type.COMPONENT)), ((Float)packetWrapper.read((Type)Type.FLOAT)).floatValue());
/*     */                     
/*     */                     packetWrapper.read((Type)Type.VAR_INT);
/*     */                     
/*     */                     packetWrapper.read((Type)Type.VAR_INT);
/*     */                     packetWrapper.read((Type)Type.UNSIGNED_BYTE);
/*     */                   } else if (action == 1) {
/*     */                     bossBarStorage.remove(uuid);
/*     */                   } else if (action == 2) {
/*     */                     bossBarStorage.updateHealth(uuid, ((Float)packetWrapper.read((Type)Type.FLOAT)).floatValue());
/*     */                   } else if (action == 3) {
/*     */                     String title = ChatUtil.jsonToLegacy((JsonElement)packetWrapper.read(Type.COMPONENT));
/*     */                     bossBarStorage.updateTitle(uuid, title);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  72 */     protocol.cancelClientbound((ClientboundPacketType)ClientboundPackets1_9.COOLDOWN);
/*     */ 
/*     */     
/*  75 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  78 */             map(Type.STRING);
/*  79 */             handler(packetWrapper -> {
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
/*     */                       packetWrapper.write(Type.ITEM, ItemRewriter.toClient((Item)packetWrapper.read(Type.ITEM)));
/*     */                       
/*     */                       packetWrapper.write(Type.ITEM, ItemRewriter.toClient((Item)packetWrapper.read(Type.ITEM)));
/*     */                       
/*     */                       boolean has3Items = ((Boolean)packetWrapper.passthrough((Type)Type.BOOLEAN)).booleanValue();
/*     */                       
/*     */                       if (has3Items) {
/*     */                         packetWrapper.write(Type.ITEM, ItemRewriter.toClient((Item)packetWrapper.read(Type.ITEM)));
/*     */                       }
/*     */                       packetWrapper.passthrough((Type)Type.BOOLEAN);
/*     */                       packetWrapper.passthrough((Type)Type.INT);
/*     */                       packetWrapper.passthrough((Type)Type.INT);
/*     */                     } 
/*     */                   } else if (channel.equalsIgnoreCase("MC|BOpen")) {
/*     */                     packetWrapper.read((Type)Type.VAR_INT);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 113 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.GAME_EVENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 116 */             map((Type)Type.UNSIGNED_BYTE);
/* 117 */             map((Type)Type.FLOAT);
/* 118 */             handler(packetWrapper -> {
/*     */                   int reason = ((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   
/*     */                   if (reason == 3) {
/*     */                     ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).setPlayerGamemode(((Float)packetWrapper.get((Type)Type.FLOAT, 0)).intValue());
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 127 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 130 */             map((Type)Type.INT);
/* 131 */             map((Type)Type.UNSIGNED_BYTE);
/* 132 */             map((Type)Type.BYTE);
/* 133 */             map((Type)Type.UNSIGNED_BYTE);
/* 134 */             map((Type)Type.UNSIGNED_BYTE);
/* 135 */             map(Type.STRING);
/* 136 */             map((Type)Type.BOOLEAN);
/* 137 */             handler(packetWrapper -> {
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   tracker.setPlayerId(((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue());
/*     */                   tracker.setPlayerGamemode(((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue());
/*     */                   tracker.getClientEntityTypes().put(Integer.valueOf(tracker.getPlayerId()), Entity1_10Types.EntityType.ENTITY_HUMAN);
/*     */                 });
/* 143 */             handler(packetWrapper -> {
/*     */                   ClientWorld world = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
/*     */ 
/*     */ 
/*     */                   
/*     */                   world.setEnvironment(((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue());
/*     */                 });
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 155 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.PLAYER_POSITION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 158 */             map((Type)Type.DOUBLE);
/* 159 */             map((Type)Type.DOUBLE);
/* 160 */             map((Type)Type.DOUBLE);
/* 161 */             map((Type)Type.FLOAT);
/* 162 */             map((Type)Type.FLOAT);
/* 163 */             map((Type)Type.BYTE);
/* 164 */             handler(packetWrapper -> {
/*     */                   PlayerPosition pos = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
/*     */                   
/*     */                   int teleportId = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   pos.setConfirmId(teleportId);
/*     */                   
/*     */                   byte flags = ((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   
/*     */                   double x = ((Double)packetWrapper.get((Type)Type.DOUBLE, 0)).doubleValue();
/*     */                   
/*     */                   double y = ((Double)packetWrapper.get((Type)Type.DOUBLE, 1)).doubleValue();
/*     */                   
/*     */                   double z = ((Double)packetWrapper.get((Type)Type.DOUBLE, 2)).doubleValue();
/*     */                   
/*     */                   float yaw = ((Float)packetWrapper.get((Type)Type.FLOAT, 0)).floatValue();
/*     */                   
/*     */                   float pitch = ((Float)packetWrapper.get((Type)Type.FLOAT, 1)).floatValue();
/*     */                   
/*     */                   packetWrapper.set((Type)Type.BYTE, 0, Byte.valueOf((byte)0));
/*     */                   if (flags != 0) {
/*     */                     if ((flags & 0x1) != 0) {
/*     */                       x += pos.getPosX();
/*     */                       packetWrapper.set((Type)Type.DOUBLE, 0, Double.valueOf(x));
/*     */                     } 
/*     */                     if ((flags & 0x2) != 0) {
/*     */                       y += pos.getPosY();
/*     */                       packetWrapper.set((Type)Type.DOUBLE, 1, Double.valueOf(y));
/*     */                     } 
/*     */                     if ((flags & 0x4) != 0) {
/*     */                       z += pos.getPosZ();
/*     */                       packetWrapper.set((Type)Type.DOUBLE, 2, Double.valueOf(z));
/*     */                     } 
/*     */                     if ((flags & 0x8) != 0) {
/*     */                       yaw += pos.getYaw();
/*     */                       packetWrapper.set((Type)Type.FLOAT, 0, Float.valueOf(yaw));
/*     */                     } 
/*     */                     if ((flags & 0x10) != 0) {
/*     */                       pitch += pos.getPitch();
/*     */                       packetWrapper.set((Type)Type.FLOAT, 1, Float.valueOf(pitch));
/*     */                     } 
/*     */                   } 
/*     */                   pos.setPos(x, y, z);
/*     */                   pos.setYaw(yaw);
/*     */                   pos.setPitch(pitch);
/*     */                 });
/*     */           }
/*     */         });
/* 212 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 215 */             map((Type)Type.INT);
/* 216 */             map((Type)Type.UNSIGNED_BYTE);
/* 217 */             map((Type)Type.UNSIGNED_BYTE);
/* 218 */             map(Type.STRING);
/* 219 */             handler(packetWrapper -> ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).setPlayerGamemode(((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 1)).shortValue()));
/* 220 */             handler(packetWrapper -> {
/*     */                   ((BossBarStorage)packetWrapper.user().get(BossBarStorage.class)).updateLocation();
/*     */                   ((BossBarStorage)packetWrapper.user().get(BossBarStorage.class)).changeWorld();
/*     */                 });
/* 224 */             handler(packetWrapper -> {
/*     */                   ClientWorld world = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   world.setEnvironment(((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue());
/*     */                 });
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 242 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.CHAT_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 245 */             map(Type.STRING);
/* 246 */             handler(packetWrapper -> {
/*     */                   String msg = (String)packetWrapper.get(Type.STRING, 0);
/*     */                   
/*     */                   if (msg.toLowerCase().startsWith("/offhand")) {
/*     */                     packetWrapper.cancel();
/*     */                     
/*     */                     PacketWrapper swapItems = PacketWrapper.create(19, null, packetWrapper.user());
/*     */                     
/*     */                     swapItems.write((Type)Type.VAR_INT, Integer.valueOf(6));
/*     */                     
/*     */                     swapItems.write(Type.POSITION, new Position(0, 0, 0));
/*     */                     
/*     */                     swapItems.write((Type)Type.BYTE, Byte.valueOf((byte)-1));
/*     */                     PacketUtil.sendToServer(swapItems, Protocol1_8TO1_9.class, true, true);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 264 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.INTERACT_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 267 */             map((Type)Type.VAR_INT);
/* 268 */             map((Type)Type.VAR_INT);
/* 269 */             handler(packetWrapper -> {
/*     */                   int type = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 1)).intValue();
/*     */                   
/*     */                   if (type == 2) {
/*     */                     packetWrapper.passthrough((Type)Type.FLOAT);
/*     */                     
/*     */                     packetWrapper.passthrough((Type)Type.FLOAT);
/*     */                     packetWrapper.passthrough((Type)Type.FLOAT);
/*     */                   } 
/*     */                   if (type == 2 || type == 0) {
/*     */                     packetWrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 284 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.PLAYER_MOVEMENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 287 */             map((Type)Type.BOOLEAN);
/* 288 */             handler(packetWrapper -> {
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   int playerId = tracker.getPlayerId();
/*     */                   if (tracker.isInsideVehicle(playerId)) {
/*     */                     packetWrapper.cancel();
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 297 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.PLAYER_POSITION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 300 */             map((Type)Type.DOUBLE);
/* 301 */             map((Type)Type.DOUBLE);
/* 302 */             map((Type)Type.DOUBLE);
/* 303 */             map((Type)Type.BOOLEAN);
/* 304 */             handler(packetWrapper -> {
/*     */                   PlayerPosition pos = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class); if (pos.getConfirmId() != -1)
/*     */                     return; 
/*     */                   pos.setPos(((Double)packetWrapper.get((Type)Type.DOUBLE, 0)).doubleValue(), ((Double)packetWrapper.get((Type)Type.DOUBLE, 1)).doubleValue(), ((Double)packetWrapper.get((Type)Type.DOUBLE, 2)).doubleValue());
/*     */                   pos.setOnGround(((Boolean)packetWrapper.get((Type)Type.BOOLEAN, 0)).booleanValue());
/*     */                 });
/* 310 */             handler(packetWrapper -> ((BossBarStorage)packetWrapper.user().get(BossBarStorage.class)).updateLocation());
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 315 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.PLAYER_ROTATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 318 */             map((Type)Type.FLOAT);
/* 319 */             map((Type)Type.FLOAT);
/* 320 */             map((Type)Type.BOOLEAN);
/* 321 */             handler(packetWrapper -> {
/*     */                   PlayerPosition pos = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class); if (pos.getConfirmId() != -1)
/*     */                     return; 
/*     */                   pos.setYaw(((Float)packetWrapper.get((Type)Type.FLOAT, 0)).floatValue());
/*     */                   pos.setPitch(((Float)packetWrapper.get((Type)Type.FLOAT, 1)).floatValue());
/*     */                   pos.setOnGround(((Boolean)packetWrapper.get((Type)Type.BOOLEAN, 0)).booleanValue());
/*     */                 });
/* 328 */             handler(packetWrapper -> ((BossBarStorage)packetWrapper.user().get(BossBarStorage.class)).updateLocation());
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 333 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.PLAYER_POSITION_AND_ROTATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 336 */             map((Type)Type.DOUBLE);
/* 337 */             map((Type)Type.DOUBLE);
/* 338 */             map((Type)Type.DOUBLE);
/* 339 */             map((Type)Type.FLOAT);
/* 340 */             map((Type)Type.FLOAT);
/* 341 */             map((Type)Type.BOOLEAN);
/* 342 */             handler(packetWrapper -> {
/*     */                   double x = ((Double)packetWrapper.get((Type)Type.DOUBLE, 0)).doubleValue();
/*     */                   
/*     */                   double y = ((Double)packetWrapper.get((Type)Type.DOUBLE, 1)).doubleValue();
/*     */                   
/*     */                   double z = ((Double)packetWrapper.get((Type)Type.DOUBLE, 2)).doubleValue();
/*     */                   
/*     */                   float yaw = ((Float)packetWrapper.get((Type)Type.FLOAT, 0)).floatValue();
/*     */                   
/*     */                   float pitch = ((Float)packetWrapper.get((Type)Type.FLOAT, 1)).floatValue();
/*     */                   boolean onGround = ((Boolean)packetWrapper.get((Type)Type.BOOLEAN, 0)).booleanValue();
/*     */                   PlayerPosition pos = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
/*     */                   if (pos.getConfirmId() != -1) {
/*     */                     if (pos.getPosX() == x && pos.getPosY() == y && pos.getPosZ() == z && pos.getYaw() == yaw && pos.getPitch() == pitch) {
/*     */                       PacketWrapper confirmTeleport = packetWrapper.create(0);
/*     */                       confirmTeleport.write((Type)Type.VAR_INT, Integer.valueOf(pos.getConfirmId()));
/*     */                       PacketUtil.sendToServer(confirmTeleport, Protocol1_8TO1_9.class, true, true);
/*     */                       pos.setConfirmId(-1);
/*     */                     } 
/*     */                   } else {
/*     */                     pos.setPos(x, y, z);
/*     */                     pos.setYaw(yaw);
/*     */                     pos.setPitch(pitch);
/*     */                     pos.setOnGround(onGround);
/*     */                     ((BossBarStorage)packetWrapper.user().get(BossBarStorage.class)).updateLocation();
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 371 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.PLAYER_DIGGING, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 374 */             map((Type)Type.VAR_INT);
/* 375 */             map(Type.POSITION);
/* 376 */             handler(packetWrapper -> {
/*     */                   int state = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   if (state == 0) {
/*     */                     ((BlockPlaceDestroyTracker)packetWrapper.user().get(BlockPlaceDestroyTracker.class)).setMining(true);
/*     */                   } else if (state == 2) {
/*     */                     BlockPlaceDestroyTracker tracker = (BlockPlaceDestroyTracker)packetWrapper.user().get(BlockPlaceDestroyTracker.class);
/*     */                     
/*     */                     tracker.setMining(false);
/*     */                     tracker.setLastMining(System.currentTimeMillis() + 100L);
/*     */                     ((Cooldown)packetWrapper.user().get(Cooldown.class)).setLastHit(0L);
/*     */                   } else if (state == 1) {
/*     */                     BlockPlaceDestroyTracker tracker = (BlockPlaceDestroyTracker)packetWrapper.user().get(BlockPlaceDestroyTracker.class);
/*     */                     tracker.setMining(false);
/*     */                     tracker.setLastMining(0L);
/*     */                     ((Cooldown)packetWrapper.user().get(Cooldown.class)).hit();
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 396 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.PLAYER_BLOCK_PLACEMENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 399 */             map(Type.POSITION);
/* 400 */             map((Type)Type.BYTE, (Type)Type.VAR_INT);
/* 401 */             map(Type.ITEM, (Type)Type.NOTHING);
/* 402 */             create((Type)Type.VAR_INT, Integer.valueOf(0));
/* 403 */             map((Type)Type.BYTE, (Type)Type.UNSIGNED_BYTE);
/* 404 */             map((Type)Type.BYTE, (Type)Type.UNSIGNED_BYTE);
/* 405 */             map((Type)Type.BYTE, (Type)Type.UNSIGNED_BYTE);
/* 406 */             handler(packetWrapper -> {
/*     */                   if (((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue() == -1) {
/*     */                     packetWrapper.cancel();
/*     */                     
/*     */                     PacketWrapper useItem = PacketWrapper.create(29, null, packetWrapper.user());
/*     */                     useItem.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                     PacketUtil.sendToServer(useItem, Protocol1_8TO1_9.class, true, true);
/*     */                   } 
/*     */                 });
/* 415 */             handler(packetWrapper -> {
/*     */                   if (((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue() != -1) {
/*     */                     ((BlockPlaceDestroyTracker)packetWrapper.user().get(BlockPlaceDestroyTracker.class)).place();
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 424 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.HELD_ITEM_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 427 */             handler(packetWrapper -> ((Cooldown)packetWrapper.user().get(Cooldown.class)).hit());
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 432 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.ANIMATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 435 */             handler(packetWrapper -> {
/*     */                   packetWrapper.cancel();
/*     */                   
/*     */                   final PacketWrapper delayedPacket = PacketWrapper.create(26, null, packetWrapper.user());
/*     */                   
/*     */                   delayedPacket.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                   
/*     */                   Protocol1_8TO1_9.TIMER.schedule(new TimerTask()
/*     */                       {
/*     */                         public void run()
/*     */                         {
/* 446 */                           PacketUtil.sendToServer(delayedPacket, Protocol1_8TO1_9.class);
/*     */                         }
/*     */                       }5L);
/*     */                 });
/* 450 */             handler(packetWrapper -> {
/*     */                   ((BlockPlaceDestroyTracker)packetWrapper.user().get(BlockPlaceDestroyTracker.class)).updateMining();
/*     */                   
/*     */                   ((Cooldown)packetWrapper.user().get(Cooldown.class)).hit();
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 458 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.ENTITY_ACTION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 461 */             map((Type)Type.VAR_INT);
/* 462 */             map((Type)Type.VAR_INT);
/* 463 */             map((Type)Type.VAR_INT);
/* 464 */             handler(packetWrapper -> {
/*     */                   int action = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 1)).intValue();
/*     */                   
/*     */                   if (action == 6) {
/*     */                     packetWrapper.set((Type)Type.VAR_INT, 1, Integer.valueOf(7));
/*     */                   } else if (action == 0) {
/*     */                     PlayerPosition pos = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
/*     */                     
/*     */                     if (!pos.isOnGround()) {
/*     */                       PacketWrapper elytra = PacketWrapper.create(20, null, packetWrapper.user());
/*     */                       elytra.write((Type)Type.VAR_INT, packetWrapper.get((Type)Type.VAR_INT, 0));
/*     */                       elytra.write((Type)Type.VAR_INT, Integer.valueOf(8));
/*     */                       elytra.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                       PacketUtil.sendToServer(elytra, Protocol1_8TO1_9.class, true, false);
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 483 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.STEER_VEHICLE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 486 */             map((Type)Type.FLOAT);
/* 487 */             map((Type)Type.FLOAT);
/* 488 */             map((Type)Type.UNSIGNED_BYTE);
/* 489 */             handler(packetWrapper -> {
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   int playerId = tracker.getPlayerId();
/*     */                   int vehicle = tracker.getVehicle(playerId);
/*     */                   if (vehicle != -1 && tracker.getClientEntityTypes().get(Integer.valueOf(vehicle)) == Entity1_10Types.EntityType.BOAT) {
/*     */                     PacketWrapper steerBoat = PacketWrapper.create(17, null, packetWrapper.user());
/*     */                     float left = ((Float)packetWrapper.get((Type)Type.FLOAT, 0)).floatValue();
/*     */                     float forward = ((Float)packetWrapper.get((Type)Type.FLOAT, 1)).floatValue();
/* 497 */                     steerBoat.write((Type)Type.BOOLEAN, Boolean.valueOf((forward != 0.0F || left < 0.0F)));
/* 498 */                     steerBoat.write((Type)Type.BOOLEAN, Boolean.valueOf((forward != 0.0F || left > 0.0F)));
/*     */                     
/*     */                     PacketUtil.sendToServer(steerBoat, Protocol1_8TO1_9.class);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 506 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.UPDATE_SIGN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 509 */             map(Type.POSITION);
/* 510 */             handler(packetWrapper -> {
/*     */                   for (int i = 0; i < 4; i++) {
/*     */                     packetWrapper.write(Type.STRING, ChatUtil.jsonToLegacy((JsonElement)packetWrapper.read(Type.COMPONENT)));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 521 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.TAB_COMPLETE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 524 */             map(Type.STRING);
/* 525 */             handler(packetWrapper -> packetWrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false)));
/* 526 */             map(Type.OPTIONAL_POSITION);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 531 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.CLIENT_SETTINGS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 534 */             map(Type.STRING);
/* 535 */             map((Type)Type.BYTE);
/* 536 */             map((Type)Type.BYTE, (Type)Type.VAR_INT);
/* 537 */             map((Type)Type.BOOLEAN);
/* 538 */             map((Type)Type.UNSIGNED_BYTE);
/* 539 */             create((Type)Type.VAR_INT, Integer.valueOf(1));
/* 540 */             handler(packetWrapper -> {
/*     */                   short flags = ((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */ 
/*     */                   
/*     */                   PacketWrapper updateSkin = PacketWrapper.create(28, null, packetWrapper.user());
/*     */                   
/*     */                   updateSkin.write((Type)Type.VAR_INT, Integer.valueOf(((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getPlayerId()));
/*     */                   
/*     */                   ArrayList<Metadata> metadata = new ArrayList<>();
/*     */                   
/*     */                   metadata.add(new Metadata(10, (MetaType)MetaType1_8.Byte, Byte.valueOf((byte)flags)));
/*     */                   
/*     */                   updateSkin.write(Types1_8.METADATA_LIST, metadata);
/*     */                   
/*     */                   PacketUtil.sendPacket(updateSkin, Protocol1_8TO1_9.class);
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 559 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 562 */             map(Type.STRING);
/* 563 */             handler(packetWrapper -> {
/*     */                   String channel = (String)packetWrapper.get(Type.STRING, 0);
/*     */                   if (channel.equalsIgnoreCase("MC|BEdit") || channel.equalsIgnoreCase("MC|BSign")) {
/*     */                     Item book = (Item)packetWrapper.passthrough(Type.ITEM);
/*     */                     book.setIdentifier(386);
/*     */                     CompoundTag tag = book.tag();
/*     */                     if (tag.contains("pages")) {
/*     */                       ListTag pages = (ListTag)tag.get("pages");
/*     */                       if (pages.size() > ViaRewind.getConfig().getMaxBookPages()) {
/*     */                         packetWrapper.user().disconnect("Too many book pages");
/*     */                         return;
/*     */                       } 
/*     */                       for (int i = 0; i < pages.size(); i++) {
/*     */                         StringTag page = (StringTag)pages.get(i);
/*     */                         String value = page.getValue();
/*     */                         if (value.length() > ViaRewind.getConfig().getMaxBookPageSize()) {
/*     */                           packetWrapper.user().disconnect("Book page too large");
/*     */                           return;
/*     */                         } 
/*     */                         value = ChatUtil.jsonToLegacy(value);
/*     */                         page.setValue(value);
/*     */                       } 
/*     */                     } 
/*     */                   } else if (channel.equalsIgnoreCase("MC|AdvCdm")) {
/*     */                     packetWrapper.set(Type.STRING, 0, channel = "MC|AdvCmd");
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\packets\PlayerPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
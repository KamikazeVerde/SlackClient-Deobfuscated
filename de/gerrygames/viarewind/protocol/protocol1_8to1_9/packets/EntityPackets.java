/*     */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.Vector;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_8;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
/*     */ import com.viaversion.viaversion.util.Pair;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ItemRewriter;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata.MetadataRewriter;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Levitation;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.PlayerPosition;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.util.RelativeMoveUtil;
/*     */ import de.gerrygames.viarewind.replacement.EntityReplacement;
/*     */ import de.gerrygames.viarewind.utils.PacketUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityPackets
/*     */ {
/*     */   public static void register(Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8> protocol) {
/*  41 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_STATUS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  44 */             map((Type)Type.INT);
/*  45 */             handler(packetWrapper -> {
/*     */                   byte status = ((Byte)packetWrapper.read((Type)Type.BYTE)).byteValue();
/*     */                   
/*     */                   if (status > 23) {
/*     */                     packetWrapper.cancel();
/*     */                     
/*     */                     return;
/*     */                   } 
/*     */                   packetWrapper.write((Type)Type.BYTE, Byte.valueOf(status));
/*     */                 });
/*     */           }
/*     */         });
/*  57 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_POSITION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  60 */             map((Type)Type.VAR_INT);
/*  61 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   int relX = ((Short)packetWrapper.read((Type)Type.SHORT)).shortValue();
/*     */                   
/*     */                   int relY = ((Short)packetWrapper.read((Type)Type.SHORT)).shortValue();
/*     */                   
/*     */                   int relZ = ((Short)packetWrapper.read((Type)Type.SHORT)).shortValue();
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   
/*     */                   EntityReplacement replacement = tracker.getEntityReplacement(entityId);
/*     */                   
/*     */                   if (replacement != null) {
/*     */                     packetWrapper.cancel();
/*     */                     
/*     */                     replacement.relMove(relX / 4096.0D, relY / 4096.0D, relZ / 4096.0D);
/*     */                     
/*     */                     return;
/*     */                   } 
/*     */                   Vector[] moves = RelativeMoveUtil.calculateRelativeMoves(packetWrapper.user(), entityId, relX, relY, relZ);
/*     */                   packetWrapper.write((Type)Type.BYTE, Byte.valueOf((byte)moves[0].blockX()));
/*     */                   packetWrapper.write((Type)Type.BYTE, Byte.valueOf((byte)moves[0].blockY()));
/*     */                   packetWrapper.write((Type)Type.BYTE, Byte.valueOf((byte)moves[0].blockZ()));
/*     */                   boolean onGround = ((Boolean)packetWrapper.passthrough((Type)Type.BOOLEAN)).booleanValue();
/*     */                   if (moves.length > 1) {
/*     */                     PacketWrapper secondPacket = PacketWrapper.create(21, null, packetWrapper.user());
/*     */                     secondPacket.write((Type)Type.VAR_INT, packetWrapper.get((Type)Type.VAR_INT, 0));
/*     */                     secondPacket.write((Type)Type.BYTE, Byte.valueOf((byte)moves[1].blockX()));
/*     */                     secondPacket.write((Type)Type.BYTE, Byte.valueOf((byte)moves[1].blockY()));
/*     */                     secondPacket.write((Type)Type.BYTE, Byte.valueOf((byte)moves[1].blockZ()));
/*     */                     secondPacket.write((Type)Type.BOOLEAN, Boolean.valueOf(onGround));
/*     */                     PacketUtil.sendPacket(secondPacket, Protocol1_8TO1_9.class);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  98 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_POSITION_AND_ROTATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 101 */             map((Type)Type.VAR_INT);
/* 102 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   int relX = ((Short)packetWrapper.read((Type)Type.SHORT)).shortValue();
/*     */                   
/*     */                   int relY = ((Short)packetWrapper.read((Type)Type.SHORT)).shortValue();
/*     */                   
/*     */                   int relZ = ((Short)packetWrapper.read((Type)Type.SHORT)).shortValue();
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   
/*     */                   EntityReplacement replacement = tracker.getEntityReplacement(entityId);
/*     */                   
/*     */                   if (replacement != null) {
/*     */                     packetWrapper.cancel();
/*     */                     
/*     */                     replacement.relMove(relX / 4096.0D, relY / 4096.0D, relZ / 4096.0D);
/*     */                     
/*     */                     replacement.setYawPitch(((Byte)packetWrapper.read((Type)Type.BYTE)).byteValue() * 360.0F / 256.0F, ((Byte)packetWrapper.read((Type)Type.BYTE)).byteValue() * 360.0F / 256.0F);
/*     */                     
/*     */                     return;
/*     */                   } 
/*     */                   Vector[] moves = RelativeMoveUtil.calculateRelativeMoves(packetWrapper.user(), entityId, relX, relY, relZ);
/*     */                   packetWrapper.write((Type)Type.BYTE, Byte.valueOf((byte)moves[0].blockX()));
/*     */                   packetWrapper.write((Type)Type.BYTE, Byte.valueOf((byte)moves[0].blockY()));
/*     */                   packetWrapper.write((Type)Type.BYTE, Byte.valueOf((byte)moves[0].blockZ()));
/*     */                   byte yaw = ((Byte)packetWrapper.passthrough((Type)Type.BYTE)).byteValue();
/*     */                   byte pitch = ((Byte)packetWrapper.passthrough((Type)Type.BYTE)).byteValue();
/*     */                   boolean onGround = ((Boolean)packetWrapper.passthrough((Type)Type.BOOLEAN)).booleanValue();
/*     */                   Entity1_10Types.EntityType type = (Entity1_10Types.EntityType)((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getClientEntityTypes().get(Integer.valueOf(entityId));
/*     */                   if (type == Entity1_10Types.EntityType.BOAT) {
/*     */                     yaw = (byte)(yaw - 64);
/*     */                     packetWrapper.set((Type)Type.BYTE, 3, Byte.valueOf(yaw));
/*     */                   } 
/*     */                   if (moves.length > 1) {
/*     */                     PacketWrapper secondPacket = PacketWrapper.create(23, null, packetWrapper.user());
/*     */                     secondPacket.write((Type)Type.VAR_INT, packetWrapper.get((Type)Type.VAR_INT, 0));
/*     */                     secondPacket.write((Type)Type.BYTE, Byte.valueOf((byte)moves[1].blockX()));
/*     */                     secondPacket.write((Type)Type.BYTE, Byte.valueOf((byte)moves[1].blockY()));
/*     */                     secondPacket.write((Type)Type.BYTE, Byte.valueOf((byte)moves[1].blockZ()));
/*     */                     secondPacket.write((Type)Type.BYTE, Byte.valueOf(yaw));
/*     */                     secondPacket.write((Type)Type.BYTE, Byte.valueOf(pitch));
/*     */                     secondPacket.write((Type)Type.BOOLEAN, Boolean.valueOf(onGround));
/*     */                     PacketUtil.sendPacket(secondPacket, Protocol1_8TO1_9.class);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 150 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_ROTATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 153 */             map((Type)Type.VAR_INT);
/* 154 */             map((Type)Type.BYTE);
/* 155 */             map((Type)Type.BYTE);
/* 156 */             map((Type)Type.BOOLEAN);
/* 157 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   EntityReplacement replacement = tracker.getEntityReplacement(entityId);
/*     */                   if (replacement != null) {
/*     */                     packetWrapper.cancel();
/*     */                     int yaw = ((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                     int pitch = ((Byte)packetWrapper.get((Type)Type.BYTE, 1)).byteValue();
/*     */                     replacement.setYawPitch(yaw * 360.0F / 256.0F, pitch * 360.0F / 256.0F);
/*     */                   } 
/*     */                 });
/* 168 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   Entity1_10Types.EntityType type = (Entity1_10Types.EntityType)((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getClientEntityTypes().get(Integer.valueOf(entityId));
/*     */                   
/*     */                   if (type == Entity1_10Types.EntityType.BOAT) {
/*     */                     byte yaw = ((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                     
/*     */                     yaw = (byte)(yaw - 64);
/*     */                     
/*     */                     packetWrapper.set((Type)Type.BYTE, 0, Byte.valueOf(yaw));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 183 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.VEHICLE_MOVE, (ClientboundPacketType)ClientboundPackets1_8.ENTITY_TELEPORT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 186 */             handler(packetWrapper -> {
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class); int vehicle = tracker.getVehicle(tracker.getPlayerId());
/*     */                   if (vehicle == -1)
/*     */                     packetWrapper.cancel(); 
/*     */                   packetWrapper.write((Type)Type.VAR_INT, Integer.valueOf(vehicle));
/*     */                 });
/* 192 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/* 193 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/* 194 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/* 195 */             map((Type)Type.FLOAT, Protocol1_8TO1_9.DEGREES_TO_ANGLE);
/* 196 */             map((Type)Type.FLOAT, Protocol1_8TO1_9.DEGREES_TO_ANGLE);
/* 197 */             handler(packetWrapper -> {
/*     */                   if (packetWrapper.isCancelled())
/*     */                     return;  PlayerPosition position = (PlayerPosition)packetWrapper.user().get(PlayerPosition.class);
/*     */                   double x = ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue() / 32.0D;
/*     */                   double y = ((Integer)packetWrapper.get((Type)Type.INT, 1)).intValue() / 32.0D;
/*     */                   double z = ((Integer)packetWrapper.get((Type)Type.INT, 2)).intValue() / 32.0D;
/*     */                   position.setPos(x, y, z);
/*     */                 });
/* 205 */             create((Type)Type.BOOLEAN, Boolean.valueOf(true));
/* 206 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   Entity1_10Types.EntityType type = (Entity1_10Types.EntityType)((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getClientEntityTypes().get(Integer.valueOf(entityId));
/*     */                   
/*     */                   if (type == Entity1_10Types.EntityType.BOAT) {
/*     */                     byte yaw = ((Byte)packetWrapper.get((Type)Type.BYTE, 1)).byteValue();
/*     */                     
/*     */                     yaw = (byte)(yaw - 64);
/*     */                     
/*     */                     packetWrapper.set((Type)Type.BYTE, 0, Byte.valueOf(yaw));
/*     */                     int y = ((Integer)packetWrapper.get((Type)Type.INT, 1)).intValue();
/*     */                     y += 10;
/*     */                     packetWrapper.set((Type)Type.INT, 1, Integer.valueOf(y));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 224 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.DESTROY_ENTITIES, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 227 */             map(Type.VAR_INT_ARRAY_PRIMITIVE);
/* 228 */             handler(packetWrapper -> {
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   
/*     */                   for (int entityId : (int[])packetWrapper.get(Type.VAR_INT_ARRAY_PRIMITIVE, 0)) {
/*     */                     tracker.removeEntity(entityId);
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 237 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.REMOVE_ENTITY_EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 240 */             map((Type)Type.VAR_INT);
/* 241 */             map((Type)Type.BYTE);
/* 242 */             handler(packetWrapper -> {
/*     */                   int id = ((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   if (id > 23) {
/*     */                     packetWrapper.cancel();
/*     */                   }
/*     */                   if (id == 25) {
/*     */                     if (((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue() != ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getPlayerId())
/*     */                       return; 
/*     */                     Levitation levitation = (Levitation)packetWrapper.user().get(Levitation.class);
/*     */                     levitation.setActive(false);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 256 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_HEAD_LOOK, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 259 */             map((Type)Type.VAR_INT);
/* 260 */             map((Type)Type.BYTE);
/* 261 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   
/*     */                   EntityReplacement replacement = tracker.getEntityReplacement(entityId);
/*     */                   if (replacement != null) {
/*     */                     packetWrapper.cancel();
/*     */                     int yaw = ((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                     replacement.setHeadYaw(yaw * 360.0F / 256.0F);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 275 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_METADATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 278 */             map((Type)Type.VAR_INT);
/* 279 */             map(Types1_9.METADATA_LIST, Types1_8.METADATA_LIST);
/* 280 */             handler(wrapper -> {
/*     */                   List<Metadata> metadataList = (List<Metadata>)wrapper.get(Types1_8.METADATA_LIST, 0);
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
/*     */                   if (tracker.getClientEntityTypes().containsKey(Integer.valueOf(entityId))) {
/*     */                     MetadataRewriter.transform((Entity1_10Types.EntityType)tracker.getClientEntityTypes().get(Integer.valueOf(entityId)), metadataList);
/*     */                     if (metadataList.isEmpty()) {
/*     */                       wrapper.cancel();
/*     */                     }
/*     */                   } else {
/*     */                     tracker.addMetadataToBuffer(entityId, metadataList);
/*     */                     wrapper.cancel();
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 296 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ATTACH_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 299 */             map((Type)Type.INT);
/* 300 */             map((Type)Type.INT);
/* 301 */             create((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 308 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_EQUIPMENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 311 */             map((Type)Type.VAR_INT);
/* 312 */             handler(packetWrapper -> {
/*     */                   int slot = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   if (slot == 1) {
/*     */                     packetWrapper.cancel();
/*     */                   } else if (slot > 1) {
/*     */                     slot--;
/*     */                   } 
/*     */                   packetWrapper.write((Type)Type.SHORT, Short.valueOf((short)slot));
/*     */                 });
/* 322 */             map(Type.ITEM);
/* 323 */             handler(packetWrapper -> packetWrapper.set(Type.ITEM, 0, ItemRewriter.toClient((Item)packetWrapper.get(Type.ITEM, 0))));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 328 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SET_PASSENGERS, null, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 331 */             handler(packetWrapper -> {
/*     */                   packetWrapper.cancel();
/*     */                   
/*     */                   EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   
/*     */                   int vehicle = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   int count = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   ArrayList<Integer> passengers = new ArrayList<>();
/*     */                   for (int i = 0; i < count; i++) {
/*     */                     passengers.add((Integer)packetWrapper.read((Type)Type.VAR_INT));
/*     */                   }
/*     */                   List<Integer> oldPassengers = entityTracker.getPassengers(vehicle);
/*     */                   entityTracker.setPassengers(vehicle, passengers);
/*     */                   if (!oldPassengers.isEmpty()) {
/*     */                     for (Integer passenger : oldPassengers) {
/*     */                       PacketWrapper detach = PacketWrapper.create(27, null, packetWrapper.user());
/*     */                       detach.write((Type)Type.INT, passenger);
/*     */                       detach.write((Type)Type.INT, Integer.valueOf(-1));
/*     */                       detach.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                       PacketUtil.sendPacket(detach, Protocol1_8TO1_9.class);
/*     */                     } 
/*     */                   }
/*     */                   for (int j = 0; j < count; j++) {
/*     */                     int v = (j == 0) ? vehicle : ((Integer)passengers.get(j - 1)).intValue();
/*     */                     int p = ((Integer)passengers.get(j)).intValue();
/*     */                     PacketWrapper attach = PacketWrapper.create(27, null, packetWrapper.user());
/*     */                     attach.write((Type)Type.INT, Integer.valueOf(p));
/*     */                     attach.write((Type)Type.INT, Integer.valueOf(v));
/*     */                     attach.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                     PacketUtil.sendPacket(attach, Protocol1_8TO1_9.class);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 365 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_TELEPORT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 368 */             map((Type)Type.VAR_INT);
/* 369 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/* 370 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/* 371 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/* 372 */             map((Type)Type.BYTE);
/* 373 */             map((Type)Type.BYTE);
/* 374 */             map((Type)Type.BOOLEAN);
/* 375 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   Entity1_10Types.EntityType type = (Entity1_10Types.EntityType)((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getClientEntityTypes().get(Integer.valueOf(entityId));
/*     */                   if (type == Entity1_10Types.EntityType.BOAT) {
/*     */                     byte yaw = ((Byte)packetWrapper.get((Type)Type.BYTE, 1)).byteValue();
/*     */                     yaw = (byte)(yaw - 64);
/*     */                     packetWrapper.set((Type)Type.BYTE, 0, Byte.valueOf(yaw));
/*     */                     int y = ((Integer)packetWrapper.get((Type)Type.INT, 1)).intValue();
/*     */                     y += 10;
/*     */                     packetWrapper.set((Type)Type.INT, 1, Integer.valueOf(y));
/*     */                   } 
/*     */                 });
/* 387 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).resetEntityOffset(entityId);
/*     */                 });
/* 391 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   
/*     */                   EntityReplacement replacement = tracker.getEntityReplacement(entityId);
/*     */                   if (replacement != null) {
/*     */                     packetWrapper.cancel();
/*     */                     int x = ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue();
/*     */                     int y = ((Integer)packetWrapper.get((Type)Type.INT, 1)).intValue();
/*     */                     int z = ((Integer)packetWrapper.get((Type)Type.INT, 2)).intValue();
/*     */                     int yaw = ((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                     int pitch = ((Byte)packetWrapper.get((Type)Type.BYTE, 1)).byteValue();
/*     */                     replacement.setLocation(x / 32.0D, y / 32.0D, z / 32.0D);
/*     */                     replacement.setYawPitch(yaw * 360.0F / 256.0F, pitch * 360.0F / 256.0F);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 410 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_PROPERTIES, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 413 */             map((Type)Type.VAR_INT);
/* 414 */             map((Type)Type.INT);
/* 415 */             handler(packetWrapper -> {
/*     */                   boolean player = (((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue() == ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getPlayerId());
/*     */                   
/*     */                   int size = ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   int removed = 0;
/*     */                   for (int i = 0; i < size; i++) {
/*     */                     String key = (String)packetWrapper.read(Type.STRING);
/*     */                     boolean skip = !Protocol1_8TO1_9.VALID_ATTRIBUTES.contains(key);
/*     */                     double value = ((Double)packetWrapper.read((Type)Type.DOUBLE)).doubleValue();
/*     */                     int modifiersize = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                     if (!skip) {
/*     */                       packetWrapper.write(Type.STRING, key);
/*     */                       packetWrapper.write((Type)Type.DOUBLE, Double.valueOf(value));
/*     */                       packetWrapper.write((Type)Type.VAR_INT, Integer.valueOf(modifiersize));
/*     */                     } else {
/*     */                       removed++;
/*     */                     } 
/*     */                     ArrayList<Pair<Byte, Double>> modifiers = new ArrayList<>();
/*     */                     for (int j = 0; j < modifiersize; j++) {
/*     */                       UUID uuid = (UUID)packetWrapper.read(Type.UUID);
/*     */                       double amount = ((Double)packetWrapper.read((Type)Type.DOUBLE)).doubleValue();
/*     */                       byte operation = ((Byte)packetWrapper.read((Type)Type.BYTE)).byteValue();
/*     */                       modifiers.add(new Pair(Byte.valueOf(operation), Double.valueOf(amount)));
/*     */                       if (!skip) {
/*     */                         packetWrapper.write(Type.UUID, uuid);
/*     */                         packetWrapper.write((Type)Type.DOUBLE, Double.valueOf(amount));
/*     */                         packetWrapper.write((Type)Type.BYTE, Byte.valueOf(operation));
/*     */                       } 
/*     */                     } 
/*     */                     if (player && key.equals("generic.attackSpeed")) {
/*     */                       ((Cooldown)packetWrapper.user().get(Cooldown.class)).setAttackSpeed(value, modifiers);
/*     */                     }
/*     */                   } 
/*     */                   packetWrapper.set((Type)Type.INT, 0, Integer.valueOf(size - removed));
/*     */                 });
/*     */           }
/*     */         });
/* 452 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.ENTITY_EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 455 */             map((Type)Type.VAR_INT);
/* 456 */             map((Type)Type.BYTE);
/* 457 */             map((Type)Type.BYTE);
/* 458 */             map((Type)Type.VAR_INT);
/* 459 */             map((Type)Type.BYTE);
/* 460 */             handler(packetWrapper -> {
/*     */                   int id = ((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   if (id > 23)
/*     */                     packetWrapper.cancel(); 
/*     */                   if (id == 25) {
/*     */                     if (((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue() != ((EntityTracker)packetWrapper.user().get(EntityTracker.class)).getPlayerId())
/*     */                       return; 
/*     */                     Levitation levitation = (Levitation)packetWrapper.user().get(Levitation.class);
/*     */                     levitation.setActive(true);
/*     */                     levitation.setAmplifier(((Byte)packetWrapper.get((Type)Type.BYTE, 1)).byteValue());
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\packets\EntityPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
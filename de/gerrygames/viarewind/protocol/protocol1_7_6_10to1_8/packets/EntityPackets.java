/*     */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.primitives.Ints;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ClientboundPackets1_7;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ItemRewriter;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata.MetadataRewriter;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
/*     */ import de.gerrygames.viarewind.replacement.EntityReplacement;
/*     */ import de.gerrygames.viarewind.utils.PacketUtil;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ public class EntityPackets
/*     */ {
/*     */   public static void register(Protocol1_7_6_10TO1_8 protocol) {
/*  33 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_EQUIPMENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  36 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/*  37 */             map((Type)Type.SHORT);
/*  38 */             map(Type.ITEM, Types1_7_6_10.COMPRESSED_NBT_ITEM);
/*  39 */             handler(packetWrapper -> {
/*     */                   Item item = (Item)packetWrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
/*     */                   ItemRewriter.toClient(item);
/*     */                   packetWrapper.set(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0, item);
/*     */                 });
/*  44 */             handler(packetWrapper -> {
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class); int id = ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   int limit = (tracker.getPlayerId() == id) ? 3 : 4;
/*     */                   if (((Short)packetWrapper.get((Type)Type.SHORT, 0)).shortValue() > limit)
/*     */                     packetWrapper.cancel(); 
/*     */                 });
/*  50 */             handler(packetWrapper -> {
/*     */                   short slot = ((Short)packetWrapper.get((Type)Type.SHORT, 0)).shortValue();
/*     */                   if (packetWrapper.isCancelled())
/*     */                     return; 
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   UUID uuid = tracker.getPlayerUUID(((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue());
/*     */                   if (uuid == null)
/*     */                     return; 
/*     */                   Item item = (Item)packetWrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
/*     */                   tracker.setPlayerEquipment(uuid, item, slot);
/*     */                   GameProfileStorage storage = (GameProfileStorage)packetWrapper.user().get(GameProfileStorage.class);
/*     */                   GameProfileStorage.GameProfile profile = storage.get(uuid);
/*     */                   if (profile != null && profile.gamemode == 3)
/*     */                     packetWrapper.cancel(); 
/*     */                 });
/*     */           }
/*     */         });
/*  67 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.USE_BED, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  70 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/*  71 */             handler(packetWrapper -> {
/*     */                   Position position = (Position)packetWrapper.read(Type.POSITION);
/*     */                   
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.x()));
/*     */                   packetWrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)position.y()));
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.z()));
/*     */                 });
/*     */           }
/*     */         });
/*  80 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.COLLECT_ITEM, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  83 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/*  84 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/*     */           }
/*     */         });
/*     */     
/*  88 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_VELOCITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  91 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/*  92 */             map((Type)Type.SHORT);
/*  93 */             map((Type)Type.SHORT);
/*  94 */             map((Type)Type.SHORT);
/*     */           }
/*     */         });
/*     */     
/*  98 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.DESTROY_ENTITIES, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 101 */             handler(packetWrapper -> {
/*     */                   int[] entityIds = (int[])packetWrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   
/*     */                   for (int entityId : entityIds) {
/*     */                     tracker.removeEntity(entityId);
/*     */                   }
/*     */                   
/*     */                   List<List<Integer>> parts = Lists.partition(Ints.asList(entityIds), 127);
/*     */                   
/*     */                   for (int i = 0; i < parts.size() - 1; i++) {
/*     */                     PacketWrapper destroy = PacketWrapper.create((PacketType)ClientboundPackets1_7.DESTROY_ENTITIES, packetWrapper.user());
/*     */                     
/*     */                     destroy.write(Types1_7_6_10.INT_ARRAY, ((List)parts.get(i)).stream().mapToInt(Integer::intValue).toArray());
/*     */                     
/*     */                     PacketUtil.sendPacket(destroy, Protocol1_7_6_10TO1_8.class);
/*     */                   } 
/*     */                   packetWrapper.write(Types1_7_6_10.INT_ARRAY, ((List)parts.get(parts.size() - 1)).stream().mapToInt(Integer::intValue).toArray());
/*     */                 });
/*     */           }
/*     */         });
/* 123 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_MOVEMENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 126 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/*     */           }
/*     */         });
/*     */     
/* 130 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_POSITION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 133 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/* 134 */             map((Type)Type.BYTE);
/* 135 */             map((Type)Type.BYTE);
/* 136 */             map((Type)Type.BYTE);
/* 137 */             map((Type)Type.BOOLEAN, (Type)Type.NOTHING);
/* 138 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   EntityReplacement replacement = tracker.getEntityReplacement(entityId);
/*     */                   if (replacement != null) {
/*     */                     packetWrapper.cancel();
/*     */                     int x = ((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                     int y = ((Byte)packetWrapper.get((Type)Type.BYTE, 1)).byteValue();
/*     */                     int z = ((Byte)packetWrapper.get((Type)Type.BYTE, 2)).byteValue();
/*     */                     replacement.relMove(x / 32.0D, y / 32.0D, z / 32.0D);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 153 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_ROTATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 156 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/* 157 */             map((Type)Type.BYTE);
/* 158 */             map((Type)Type.BYTE);
/* 159 */             map((Type)Type.BOOLEAN, (Type)Type.NOTHING);
/* 160 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   EntityReplacement replacement = tracker.getEntityReplacement(entityId);
/*     */                   if (replacement != null) {
/*     */                     packetWrapper.cancel();
/*     */                     int yaw = ((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                     int pitch = ((Byte)packetWrapper.get((Type)Type.BYTE, 1)).byteValue();
/*     */                     replacement.setYawPitch(yaw * 360.0F / 256.0F, pitch * 360.0F / 256.0F);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 174 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_POSITION_AND_ROTATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 177 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/* 178 */             map((Type)Type.BYTE);
/* 179 */             map((Type)Type.BYTE);
/* 180 */             map((Type)Type.BYTE);
/* 181 */             map((Type)Type.BYTE);
/* 182 */             map((Type)Type.BYTE);
/* 183 */             map((Type)Type.BOOLEAN, (Type)Type.NOTHING);
/* 184 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   EntityReplacement replacement = tracker.getEntityReplacement(entityId);
/*     */                   if (replacement != null) {
/*     */                     packetWrapper.cancel();
/*     */                     int x = ((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                     int y = ((Byte)packetWrapper.get((Type)Type.BYTE, 1)).byteValue();
/*     */                     int z = ((Byte)packetWrapper.get((Type)Type.BYTE, 2)).byteValue();
/*     */                     int yaw = ((Byte)packetWrapper.get((Type)Type.BYTE, 3)).byteValue();
/*     */                     int pitch = ((Byte)packetWrapper.get((Type)Type.BYTE, 4)).byteValue();
/*     */                     replacement.relMove(x / 32.0D, y / 32.0D, z / 32.0D);
/*     */                     replacement.setYawPitch(yaw * 360.0F / 256.0F, pitch * 360.0F / 256.0F);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 202 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_TELEPORT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 205 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/* 206 */             map((Type)Type.INT);
/* 207 */             map((Type)Type.INT);
/* 208 */             map((Type)Type.INT);
/* 209 */             map((Type)Type.BYTE);
/* 210 */             map((Type)Type.BYTE);
/* 211 */             map((Type)Type.BOOLEAN, (Type)Type.NOTHING);
/* 212 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   Entity1_10Types.EntityType type = (Entity1_10Types.EntityType)tracker.getClientEntityTypes().get(Integer.valueOf(entityId));
/*     */                   if (type == Entity1_10Types.EntityType.MINECART_ABSTRACT) {
/*     */                     int y = ((Integer)packetWrapper.get((Type)Type.INT, 2)).intValue();
/*     */                     y += 12;
/*     */                     packetWrapper.set((Type)Type.INT, 2, Integer.valueOf(y));
/*     */                   } 
/*     */                 });
/* 222 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   EntityReplacement replacement = tracker.getEntityReplacement(entityId);
/*     */                   if (replacement != null) {
/*     */                     packetWrapper.cancel();
/*     */                     int x = ((Integer)packetWrapper.get((Type)Type.INT, 1)).intValue();
/*     */                     int y = ((Integer)packetWrapper.get((Type)Type.INT, 2)).intValue();
/*     */                     int z = ((Integer)packetWrapper.get((Type)Type.INT, 3)).intValue();
/*     */                     int yaw = ((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                     int pitch = ((Byte)packetWrapper.get((Type)Type.BYTE, 1)).byteValue();
/*     */                     replacement.setLocation(x / 32.0D, y / 32.0D, z / 32.0D);
/*     */                     replacement.setYawPitch(yaw * 360.0F / 256.0F, pitch * 360.0F / 256.0F);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 240 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_HEAD_LOOK, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 243 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/* 244 */             map((Type)Type.BYTE);
/* 245 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   EntityReplacement replacement = tracker.getEntityReplacement(entityId);
/*     */                   if (replacement != null) {
/*     */                     packetWrapper.cancel();
/*     */                     int yaw = ((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                     replacement.setHeadYaw(yaw * 360.0F / 256.0F);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 258 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ATTACH_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 261 */             map((Type)Type.INT);
/* 262 */             map((Type)Type.INT);
/* 263 */             map((Type)Type.BOOLEAN);
/* 264 */             handler(packetWrapper -> {
/*     */                   boolean leash = ((Boolean)packetWrapper.get((Type)Type.BOOLEAN, 0)).booleanValue();
/*     */                   if (leash)
/*     */                     return; 
/*     */                   int passenger = ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   int vehicle = ((Integer)packetWrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   tracker.setPassenger(vehicle, passenger);
/*     */                 });
/*     */           }
/*     */         });
/* 275 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_METADATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 278 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/* 279 */             map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
/* 280 */             handler(wrapper -> {
/*     */                   List<Metadata> metadataList = (List<Metadata>)wrapper.get(Types1_7_6_10.METADATA_LIST, 0);
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
/*     */                   if (tracker.getClientEntityTypes().containsKey(Integer.valueOf(entityId))) {
/*     */                     EntityReplacement replacement = tracker.getEntityReplacement(entityId);
/*     */                     if (replacement != null) {
/*     */                       wrapper.cancel();
/*     */                       replacement.updateMetadata(metadataList);
/*     */                     } else {
/*     */                       MetadataRewriter.transform((Entity1_10Types.EntityType)tracker.getClientEntityTypes().get(Integer.valueOf(entityId)), metadataList);
/*     */                       if (metadataList.isEmpty())
/*     */                         wrapper.cancel(); 
/*     */                     } 
/*     */                   } else {
/*     */                     tracker.addMetadataToBuffer(entityId, metadataList);
/*     */                     wrapper.cancel();
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 301 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 304 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/* 305 */             map((Type)Type.BYTE);
/* 306 */             map((Type)Type.BYTE);
/* 307 */             map((Type)Type.VAR_INT, (Type)Type.SHORT);
/* 308 */             map((Type)Type.BYTE, (Type)Type.NOTHING);
/*     */           }
/*     */         });
/*     */     
/* 312 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.REMOVE_ENTITY_EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 315 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/* 316 */             map((Type)Type.BYTE);
/*     */           }
/*     */         });
/*     */     
/* 320 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_PROPERTIES, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 323 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/* 324 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   
/*     */                   if (tracker.getEntityReplacement(entityId) != null) {
/*     */                     packetWrapper.cancel();
/*     */                     return;
/*     */                   } 
/*     */                   int amount = ((Integer)packetWrapper.passthrough((Type)Type.INT)).intValue();
/*     */                   for (int i = 0; i < amount; i++) {
/*     */                     packetWrapper.passthrough(Type.STRING);
/*     */                     packetWrapper.passthrough((Type)Type.DOUBLE);
/*     */                     int modifierlength = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                     packetWrapper.write((Type)Type.SHORT, Short.valueOf((short)modifierlength));
/*     */                     for (int j = 0; j < modifierlength; j++) {
/*     */                       packetWrapper.passthrough(Type.UUID);
/*     */                       packetWrapper.passthrough((Type)Type.DOUBLE);
/*     */                       packetWrapper.passthrough((Type)Type.BYTE);
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 348 */     protocol.cancelClientbound((ClientboundPacketType)ClientboundPackets1_8.UPDATE_ENTITY_NBT);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\packets\EntityPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
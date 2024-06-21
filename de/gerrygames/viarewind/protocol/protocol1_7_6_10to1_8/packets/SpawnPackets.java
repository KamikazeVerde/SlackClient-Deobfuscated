/*     */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
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
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.ArmorStandReplacement;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.EndermiteReplacement;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.GuardianReplacement;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements.RabbitReplacement;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ReplacementRegistry1_7_6_10to1_8;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata.MetadataRewriter;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
/*     */ import de.gerrygames.viarewind.replacement.EntityReplacement;
/*     */ import de.gerrygames.viarewind.replacement.Replacement;
/*     */ import de.gerrygames.viarewind.utils.PacketUtil;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ 
/*     */ public class SpawnPackets
/*     */ {
/*     */   public static void register(Protocol1_7_6_10TO1_8 protocol) {
/*  35 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_PLAYER, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  38 */             map((Type)Type.VAR_INT);
/*  39 */             handler(packetWrapper -> {
/*     */                   UUID uuid = (UUID)packetWrapper.read(Type.UUID);
/*     */                   
/*     */                   packetWrapper.write(Type.STRING, uuid.toString());
/*     */                   
/*     */                   GameProfileStorage gameProfileStorage = (GameProfileStorage)packetWrapper.user().get(GameProfileStorage.class);
/*     */                   
/*     */                   GameProfileStorage.GameProfile gameProfile = gameProfileStorage.get(uuid);
/*     */                   if (gameProfile == null) {
/*     */                     packetWrapper.write(Type.STRING, "");
/*     */                     packetWrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                   } else {
/*     */                     packetWrapper.write(Type.STRING, (gameProfile.name.length() > 16) ? gameProfile.name.substring(0, 16) : gameProfile.name);
/*     */                     packetWrapper.write((Type)Type.VAR_INT, Integer.valueOf(gameProfile.properties.size()));
/*     */                     for (GameProfileStorage.Property property : gameProfile.properties) {
/*     */                       packetWrapper.write(Type.STRING, property.name);
/*     */                       packetWrapper.write(Type.STRING, property.value);
/*     */                       packetWrapper.write(Type.STRING, (property.signature == null) ? "" : property.signature);
/*     */                     } 
/*     */                   } 
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   if (gameProfile != null && gameProfile.gamemode == 3) {
/*     */                     int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                     short i;
/*     */                     for (i = 0; i < 5; i = (short)(i + 1)) {
/*     */                       PacketWrapper equipmentPacket = PacketWrapper.create((PacketType)ClientboundPackets1_7.ENTITY_EQUIPMENT, packetWrapper.user());
/*     */                       equipmentPacket.write((Type)Type.INT, Integer.valueOf(entityId));
/*     */                       equipmentPacket.write((Type)Type.SHORT, Short.valueOf(i));
/*     */                       equipmentPacket.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, (i == 4) ? gameProfile.getSkull() : null);
/*     */                       PacketUtil.sendPacket(equipmentPacket, Protocol1_7_6_10TO1_8.class);
/*     */                     } 
/*     */                   } 
/*     */                   tracker.addPlayer((Integer)packetWrapper.get((Type)Type.VAR_INT, 0), uuid);
/*     */                 });
/*  73 */             map((Type)Type.INT);
/*  74 */             map((Type)Type.INT);
/*  75 */             map((Type)Type.INT);
/*  76 */             map((Type)Type.BYTE);
/*  77 */             map((Type)Type.BYTE);
/*  78 */             map((Type)Type.SHORT);
/*  79 */             map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
/*  80 */             handler(packetWrapper -> {
/*     */                   List<Metadata> metadata = (List<Metadata>)packetWrapper.get(Types1_7_6_10.METADATA_LIST, 0);
/*     */                   MetadataRewriter.transform(Entity1_10Types.EntityType.PLAYER, metadata);
/*     */                 });
/*  84 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.EntityType.PLAYER);
/*     */                   tracker.sendMetadataBuffer(entityId);
/*     */                 });
/*     */           }
/*     */         });
/*  93 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  96 */             map((Type)Type.VAR_INT);
/*  97 */             map((Type)Type.BYTE);
/*  98 */             map((Type)Type.INT);
/*  99 */             map((Type)Type.INT);
/* 100 */             map((Type)Type.INT);
/* 101 */             map((Type)Type.BYTE);
/* 102 */             map((Type)Type.BYTE);
/* 103 */             map((Type)Type.INT);
/*     */             
/* 105 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   byte typeId = ((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   int x = ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   int y = ((Integer)packetWrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   int z = ((Integer)packetWrapper.get((Type)Type.INT, 2)).intValue();
/*     */                   byte pitch = ((Byte)packetWrapper.get((Type)Type.BYTE, 1)).byteValue();
/*     */                   byte yaw = ((Byte)packetWrapper.get((Type)Type.BYTE, 2)).byteValue();
/*     */                   if (typeId == 71) {
/*     */                     switch (yaw) {
/*     */                       case -128:
/*     */                         z += 32;
/*     */                         yaw = 0;
/*     */                         break;
/*     */ 
/*     */                       
/*     */                       case -64:
/*     */                         x -= 32;
/*     */                         yaw = -64;
/*     */                         break;
/*     */ 
/*     */                       
/*     */                       case 0:
/*     */                         z -= 32;
/*     */                         yaw = Byte.MIN_VALUE;
/*     */                         break;
/*     */ 
/*     */                       
/*     */                       case 64:
/*     */                         x += 32;
/*     */                         yaw = 64;
/*     */                         break;
/*     */                     } 
/*     */                   
/*     */                   } else if (typeId == 78) {
/*     */                     packetWrapper.cancel();
/*     */                     EntityTracker entityTracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                     ArmorStandReplacement armorStand = new ArmorStandReplacement(entityId, packetWrapper.user());
/*     */                     armorStand.setLocation(x / 32.0D, y / 32.0D, z / 32.0D);
/*     */                     armorStand.setYawPitch(yaw * 360.0F / 256.0F, pitch * 360.0F / 256.0F);
/*     */                     armorStand.setHeadYaw(yaw * 360.0F / 256.0F);
/*     */                     entityTracker.addEntityReplacement((EntityReplacement)armorStand);
/*     */                   } else if (typeId == 10) {
/*     */                     y += 12;
/*     */                   } 
/*     */                   packetWrapper.set((Type)Type.BYTE, 0, Byte.valueOf(typeId));
/*     */                   packetWrapper.set((Type)Type.INT, 0, Integer.valueOf(x));
/*     */                   packetWrapper.set((Type)Type.INT, 1, Integer.valueOf(y));
/*     */                   packetWrapper.set((Type)Type.INT, 2, Integer.valueOf(z));
/*     */                   packetWrapper.set((Type)Type.BYTE, 1, Byte.valueOf(pitch));
/*     */                   packetWrapper.set((Type)Type.BYTE, 2, Byte.valueOf(yaw));
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   Entity1_10Types.EntityType type = Entity1_10Types.getTypeFromId(typeId, true);
/*     */                   tracker.getClientEntityTypes().put(Integer.valueOf(entityId), type);
/*     */                   tracker.sendMetadataBuffer(entityId);
/*     */                   int data = ((Integer)packetWrapper.get((Type)Type.INT, 3)).intValue();
/*     */                   if (type != null && type.isOrHasParent((EntityType)Entity1_10Types.EntityType.FALLING_BLOCK)) {
/*     */                     int blockId = data & 0xFFF;
/*     */                     int blockData = data >> 12 & 0xF;
/*     */                     Replacement replace = ReplacementRegistry1_7_6_10to1_8.getReplacement(blockId, blockData);
/*     */                     if (replace != null) {
/*     */                       blockId = replace.getId();
/*     */                       blockData = replace.replaceData(blockData);
/*     */                     } 
/*     */                     packetWrapper.set((Type)Type.INT, 3, Integer.valueOf(data = blockId | blockData << 16));
/*     */                   } 
/*     */                   if (data > 0) {
/*     */                     packetWrapper.passthrough((Type)Type.SHORT);
/*     */                     packetWrapper.passthrough((Type)Type.SHORT);
/*     */                     packetWrapper.passthrough((Type)Type.SHORT);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 179 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 182 */             map((Type)Type.VAR_INT);
/* 183 */             map((Type)Type.UNSIGNED_BYTE);
/* 184 */             map((Type)Type.INT);
/* 185 */             map((Type)Type.INT);
/* 186 */             map((Type)Type.INT);
/* 187 */             map((Type)Type.BYTE);
/* 188 */             map((Type)Type.BYTE);
/* 189 */             map((Type)Type.BYTE);
/* 190 */             map((Type)Type.SHORT);
/* 191 */             map((Type)Type.SHORT);
/* 192 */             map((Type)Type.SHORT);
/* 193 */             map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
/* 194 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   int typeId = ((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   int x = ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   int y = ((Integer)packetWrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   int z = ((Integer)packetWrapper.get((Type)Type.INT, 2)).intValue();
/*     */                   byte pitch = ((Byte)packetWrapper.get((Type)Type.BYTE, 1)).byteValue();
/*     */                   byte yaw = ((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   byte headYaw = ((Byte)packetWrapper.get((Type)Type.BYTE, 2)).byteValue();
/*     */                   if (typeId == 30 || typeId == 68 || typeId == 67 || typeId == 101) {
/*     */                     RabbitReplacement rabbitReplacement;
/*     */                     packetWrapper.cancel();
/*     */                     EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                     EntityReplacement replacement = null;
/*     */                     if (typeId == 30) {
/*     */                       ArmorStandReplacement armorStandReplacement = new ArmorStandReplacement(entityId, packetWrapper.user());
/*     */                     } else if (typeId == 68) {
/*     */                       GuardianReplacement guardianReplacement = new GuardianReplacement(entityId, packetWrapper.user());
/*     */                     } else if (typeId == 67) {
/*     */                       EndermiteReplacement endermiteReplacement = new EndermiteReplacement(entityId, packetWrapper.user());
/*     */                     } else if (typeId == 101) {
/*     */                       rabbitReplacement = new RabbitReplacement(entityId, packetWrapper.user());
/*     */                     } 
/*     */                     rabbitReplacement.setLocation(x / 32.0D, y / 32.0D, z / 32.0D);
/*     */                     rabbitReplacement.setYawPitch(yaw * 360.0F / 256.0F, pitch * 360.0F / 256.0F);
/*     */                     rabbitReplacement.setHeadYaw(headYaw * 360.0F / 256.0F);
/*     */                     tracker.addEntityReplacement((EntityReplacement)rabbitReplacement);
/*     */                   } else if (typeId == 255 || typeId == -1) {
/*     */                     packetWrapper.cancel();
/*     */                   } 
/*     */                 });
/* 226 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   int typeId = ((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.getTypeFromId(typeId, false));
/*     */                   tracker.sendMetadataBuffer(entityId);
/*     */                 });
/* 233 */             handler(wrapper -> {
/*     */                   List<Metadata> metadataList = (List<Metadata>)wrapper.get(Types1_7_6_10.METADATA_LIST, 0);
/*     */                   
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
/*     */                   if (tracker.getEntityReplacement(entityId) != null) {
/*     */                     tracker.getEntityReplacement(entityId).updateMetadata(metadataList);
/*     */                   } else if (tracker.getClientEntityTypes().containsKey(Integer.valueOf(entityId))) {
/*     */                     MetadataRewriter.transform((Entity1_10Types.EntityType)tracker.getClientEntityTypes().get(Integer.valueOf(entityId)), metadataList);
/*     */                   } else {
/*     */                     wrapper.cancel();
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 248 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_PAINTING, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 251 */             map((Type)Type.VAR_INT);
/* 252 */             map(Type.STRING);
/* 253 */             handler(packetWrapper -> {
/*     */                   Position position = (Position)packetWrapper.read(Type.POSITION);
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.x()));
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.y()));
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.z()));
/*     */                 });
/* 259 */             map((Type)Type.UNSIGNED_BYTE, (Type)Type.INT);
/* 260 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.EntityType.PAINTING);
/*     */                   tracker.sendMetadataBuffer(entityId);
/*     */                 });
/*     */           }
/*     */         });
/* 269 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_EXPERIENCE_ORB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 272 */             map((Type)Type.VAR_INT);
/* 273 */             map((Type)Type.INT);
/* 274 */             map((Type)Type.INT);
/* 275 */             map((Type)Type.INT);
/* 276 */             map((Type)Type.SHORT);
/* 277 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.EntityType.EXPERIENCE_ORB);
/*     */                   tracker.sendMetadataBuffer(entityId);
/*     */                 });
/*     */           }
/*     */         });
/* 286 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_GLOBAL_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 289 */             map((Type)Type.VAR_INT);
/* 290 */             map((Type)Type.BYTE);
/* 291 */             map((Type)Type.INT);
/* 292 */             map((Type)Type.INT);
/* 293 */             map((Type)Type.INT);
/* 294 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.EntityType.LIGHTNING);
/*     */                   tracker.sendMetadataBuffer(entityId);
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\packets\SpawnPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
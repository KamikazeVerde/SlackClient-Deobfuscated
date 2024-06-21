/*     */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
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
/*     */ import de.gerrygames.viarewind.ViaRewind;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.entityreplacement.ShulkerBulletReplacement;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.entityreplacement.ShulkerReplacement;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ReplacementRegistry1_8to1_9;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata.MetadataRewriter;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
/*     */ import de.gerrygames.viarewind.replacement.EntityReplacement;
/*     */ import de.gerrygames.viarewind.replacement.Replacement;
/*     */ import de.gerrygames.viarewind.utils.PacketUtil;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpawnPackets
/*     */ {
/*     */   public static void register(Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8> protocol) {
/*  35 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SPAWN_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  38 */             map((Type)Type.VAR_INT);
/*  39 */             map(Type.UUID, (Type)Type.NOTHING);
/*  40 */             map((Type)Type.BYTE);
/*  41 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/*  42 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/*  43 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/*  44 */             map((Type)Type.BYTE);
/*  45 */             map((Type)Type.BYTE);
/*  46 */             map((Type)Type.INT);
/*     */             
/*  48 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   int typeId = ((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   
/*     */                   Entity1_10Types.EntityType type = Entity1_10Types.getTypeFromId(typeId, true);
/*     */                   
/*     */                   if (typeId == 3 || typeId == 91 || typeId == 92 || typeId == 93) {
/*     */                     packetWrapper.cancel();
/*     */                     
/*     */                     return;
/*     */                   } 
/*     */                   
/*     */                   if (type == null) {
/*     */                     ViaRewind.getPlatform().getLogger().warning("[ViaRewind] Unhandled Spawn Object Type: " + typeId);
/*     */                     
/*     */                     packetWrapper.cancel();
/*     */                     
/*     */                     return;
/*     */                   } 
/*     */                   
/*     */                   int x = ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   int y = ((Integer)packetWrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   
/*     */                   int z = ((Integer)packetWrapper.get((Type)Type.INT, 2)).intValue();
/*     */                   
/*     */                   if (type.is((EntityType)Entity1_10Types.EntityType.BOAT)) {
/*     */                     byte yaw = ((Byte)packetWrapper.get((Type)Type.BYTE, 1)).byteValue();
/*     */                     yaw = (byte)(yaw - 64);
/*     */                     packetWrapper.set((Type)Type.BYTE, 1, Byte.valueOf(yaw));
/*     */                     y += 10;
/*     */                     packetWrapper.set((Type)Type.INT, 1, Integer.valueOf(y));
/*     */                   } else if (type.is((EntityType)Entity1_10Types.EntityType.SHULKER_BULLET)) {
/*     */                     packetWrapper.cancel();
/*     */                     ShulkerBulletReplacement shulkerBulletReplacement = new ShulkerBulletReplacement(entityId, packetWrapper.user());
/*     */                     shulkerBulletReplacement.setLocation(x / 32.0D, y / 32.0D, z / 32.0D);
/*     */                     tracker.addEntityReplacement((EntityReplacement)shulkerBulletReplacement);
/*     */                     return;
/*     */                   } 
/*     */                   int data = ((Integer)packetWrapper.get((Type)Type.INT, 3)).intValue();
/*     */                   if (type.isOrHasParent((EntityType)Entity1_10Types.EntityType.ARROW) && data != 0) {
/*     */                     packetWrapper.set((Type)Type.INT, 3, Integer.valueOf(--data));
/*     */                   }
/*     */                   if (type.is((EntityType)Entity1_10Types.EntityType.FALLING_BLOCK)) {
/*     */                     int blockId = data & 0xFFF;
/*     */                     int blockData = data >> 12 & 0xF;
/*     */                     Replacement replace = ReplacementRegistry1_8to1_9.getReplacement(blockId, blockData);
/*     */                     if (replace != null) {
/*     */                       packetWrapper.set((Type)Type.INT, 3, Integer.valueOf(replace.getId() | replace.replaceData(data) << 12));
/*     */                     }
/*     */                   } 
/*     */                   if (data > 0) {
/*     */                     packetWrapper.passthrough((Type)Type.SHORT);
/*     */                     packetWrapper.passthrough((Type)Type.SHORT);
/*     */                     packetWrapper.passthrough((Type)Type.SHORT);
/*     */                   } else {
/*     */                     short vX = ((Short)packetWrapper.read((Type)Type.SHORT)).shortValue();
/*     */                     short vY = ((Short)packetWrapper.read((Type)Type.SHORT)).shortValue();
/*     */                     short vZ = ((Short)packetWrapper.read((Type)Type.SHORT)).shortValue();
/*     */                     PacketWrapper velocityPacket = PacketWrapper.create(18, null, packetWrapper.user());
/*     */                     velocityPacket.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/*     */                     velocityPacket.write((Type)Type.SHORT, Short.valueOf(vX));
/*     */                     velocityPacket.write((Type)Type.SHORT, Short.valueOf(vY));
/*     */                     velocityPacket.write((Type)Type.SHORT, Short.valueOf(vZ));
/*     */                     PacketUtil.sendPacket(velocityPacket, Protocol1_8TO1_9.class);
/*     */                   } 
/*     */                   tracker.getClientEntityTypes().put(Integer.valueOf(entityId), type);
/*     */                   tracker.sendMetadataBuffer(entityId);
/*     */                 });
/*     */           }
/*     */         });
/* 122 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SPAWN_EXPERIENCE_ORB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 125 */             map((Type)Type.VAR_INT);
/* 126 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/* 127 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/* 128 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/* 129 */             map((Type)Type.SHORT);
/* 130 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   
/*     */                   tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.EntityType.EXPERIENCE_ORB);
/*     */                   tracker.sendMetadataBuffer(entityId);
/*     */                 });
/*     */           }
/*     */         });
/* 140 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SPAWN_GLOBAL_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 143 */             map((Type)Type.VAR_INT);
/* 144 */             map((Type)Type.BYTE);
/* 145 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/* 146 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/* 147 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/* 148 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   
/*     */                   tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.EntityType.LIGHTNING);
/*     */                   tracker.sendMetadataBuffer(entityId);
/*     */                 });
/*     */           }
/*     */         });
/* 158 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 161 */             map((Type)Type.VAR_INT);
/* 162 */             map(Type.UUID, (Type)Type.NOTHING);
/* 163 */             map((Type)Type.UNSIGNED_BYTE);
/* 164 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/* 165 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/* 166 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/* 167 */             map((Type)Type.BYTE);
/* 168 */             map((Type)Type.BYTE);
/* 169 */             map((Type)Type.BYTE);
/* 170 */             map((Type)Type.SHORT);
/* 171 */             map((Type)Type.SHORT);
/* 172 */             map((Type)Type.SHORT);
/* 173 */             map(Types1_9.METADATA_LIST, Types1_8.METADATA_LIST);
/* 174 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   int typeId = ((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   int x = ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   int y = ((Integer)packetWrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   int z = ((Integer)packetWrapper.get((Type)Type.INT, 2)).intValue();
/*     */                   byte pitch = ((Byte)packetWrapper.get((Type)Type.BYTE, 1)).byteValue();
/*     */                   byte yaw = ((Byte)packetWrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   byte headYaw = ((Byte)packetWrapper.get((Type)Type.BYTE, 2)).byteValue();
/*     */                   if (typeId == 69) {
/*     */                     packetWrapper.cancel();
/*     */                     EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                     ShulkerReplacement shulkerReplacement = new ShulkerReplacement(entityId, packetWrapper.user());
/*     */                     shulkerReplacement.setLocation(x / 32.0D, y / 32.0D, z / 32.0D);
/*     */                     shulkerReplacement.setYawPitch(yaw * 360.0F / 256.0F, pitch * 360.0F / 256.0F);
/*     */                     shulkerReplacement.setHeadYaw(headYaw * 360.0F / 256.0F);
/*     */                     tracker.addEntityReplacement((EntityReplacement)shulkerReplacement);
/*     */                   } else if (typeId == -1 || typeId == 255) {
/*     */                     packetWrapper.cancel();
/*     */                   } 
/*     */                 });
/* 196 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   int typeId = ((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.getTypeFromId(typeId, false));
/*     */                   tracker.sendMetadataBuffer(entityId);
/*     */                 });
/* 203 */             handler(wrapper -> {
/*     */                   List<Metadata> metadataList = (List<Metadata>)wrapper.get(Types1_8.METADATA_LIST, 0);
/*     */                   
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
/*     */                   EntityReplacement replacement;
/*     */                   if ((replacement = tracker.getEntityReplacement(entityId)) != null) {
/*     */                     replacement.updateMetadata(metadataList);
/*     */                   } else if (tracker.getClientEntityTypes().containsKey(Integer.valueOf(entityId))) {
/*     */                     MetadataRewriter.transform((Entity1_10Types.EntityType)tracker.getClientEntityTypes().get(Integer.valueOf(entityId)), metadataList);
/*     */                   } else {
/*     */                     wrapper.cancel();
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 220 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SPAWN_PAINTING, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 223 */             map((Type)Type.VAR_INT);
/* 224 */             map(Type.UUID, (Type)Type.NOTHING);
/* 225 */             map(Type.STRING);
/* 226 */             map(Type.POSITION);
/* 227 */             map((Type)Type.BYTE, (Type)Type.UNSIGNED_BYTE);
/* 228 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   
/*     */                   tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.EntityType.PAINTING);
/*     */                   tracker.sendMetadataBuffer(entityId);
/*     */                 });
/*     */           }
/*     */         });
/* 238 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SPAWN_PLAYER, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 241 */             map((Type)Type.VAR_INT);
/* 242 */             map(Type.UUID);
/* 243 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/* 244 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/* 245 */             map((Type)Type.DOUBLE, Protocol1_8TO1_9.TO_OLD_INT);
/* 246 */             map((Type)Type.BYTE);
/* 247 */             map((Type)Type.BYTE);
/* 248 */             handler(packetWrapper -> packetWrapper.write((Type)Type.SHORT, Short.valueOf((short)0)));
/* 249 */             map(Types1_9.METADATA_LIST, Types1_8.METADATA_LIST);
/* 250 */             handler(wrapper -> {
/*     */                   List<Metadata> metadataList = (List<Metadata>)wrapper.get(Types1_8.METADATA_LIST, 0);
/*     */                   MetadataRewriter.transform(Entity1_10Types.EntityType.PLAYER, metadataList);
/*     */                 });
/* 254 */             handler(packetWrapper -> {
/*     */                   int entityId = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   EntityTracker tracker = (EntityTracker)packetWrapper.user().get(EntityTracker.class);
/*     */                   tracker.getClientEntityTypes().put(Integer.valueOf(entityId), Entity1_10Types.EntityType.PLAYER);
/*     */                   tracker.sendMetadataBuffer(entityId);
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\packets\SpawnPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
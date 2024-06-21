/*     */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_10Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.DataItem;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_8;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetadataRewriter1_9To1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class SpawnPackets
/*     */ {
/*  43 */   public static final ValueTransformer<Integer, Double> toNewDouble = new ValueTransformer<Integer, Double>((Type)Type.DOUBLE)
/*     */     {
/*     */       public Double transform(PacketWrapper wrapper, Integer inputValue) {
/*  46 */         return Double.valueOf(inputValue.intValue() / 32.0D);
/*     */       }
/*     */     };
/*     */   
/*     */   public static void register(final Protocol1_9To1_8 protocol) {
/*  51 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  54 */             map((Type)Type.VAR_INT);
/*     */             
/*  56 */             handler(wrapper -> {
/*     */                   int entityID = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   wrapper.write(Type.UUID, tracker.getEntityUUID(entityID));
/*     */                 });
/*  61 */             map((Type)Type.BYTE);
/*     */ 
/*     */             
/*  64 */             handler(wrapper -> {
/*     */                   int entityID = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   int typeID = ((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   tracker.addEntity(entityID, (EntityType)Entity1_10Types.getTypeFromId(typeID, true));
/*     */                   tracker.sendMetadataBuffer(entityID);
/*     */                 });
/*  72 */             map((Type)Type.INT, SpawnPackets.toNewDouble);
/*  73 */             map((Type)Type.INT, SpawnPackets.toNewDouble);
/*  74 */             map((Type)Type.INT, SpawnPackets.toNewDouble);
/*     */             
/*  76 */             map((Type)Type.BYTE);
/*  77 */             map((Type)Type.BYTE);
/*     */             
/*  79 */             map((Type)Type.INT);
/*     */ 
/*     */             
/*  82 */             handler(wrapper -> {
/*     */                   int data = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   short vX = 0;
/*     */                   
/*     */                   short vY = 0;
/*     */                   short vZ = 0;
/*     */                   if (data > 0) {
/*     */                     vX = ((Short)wrapper.read((Type)Type.SHORT)).shortValue();
/*     */                     vY = ((Short)wrapper.read((Type)Type.SHORT)).shortValue();
/*     */                     vZ = ((Short)wrapper.read((Type)Type.SHORT)).shortValue();
/*     */                   } 
/*     */                   wrapper.write((Type)Type.SHORT, Short.valueOf(vX));
/*     */                   wrapper.write((Type)Type.SHORT, Short.valueOf(vY));
/*     */                   wrapper.write((Type)Type.SHORT, Short.valueOf(vZ));
/*     */                 });
/*  98 */             handler(wrapper -> {
/*     */                   int entityID = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */ 
/*     */                   
/*     */                   int data = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */ 
/*     */                   
/*     */                   int typeID = ((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */ 
/*     */                   
/*     */                   if (Entity1_10Types.getTypeFromId(typeID, true) == Entity1_10Types.EntityType.SPLASH_POTION) {
/*     */                     PacketWrapper metaPacket = wrapper.create((PacketType)ClientboundPackets1_9.ENTITY_METADATA, ());
/*     */ 
/*     */                     
/*     */                     wrapper.send(Protocol1_9To1_8.class);
/*     */ 
/*     */                     
/*     */                     metaPacket.send(Protocol1_9To1_8.class);
/*     */ 
/*     */                     
/*     */                     wrapper.cancel();
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 124 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_EXPERIENCE_ORB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 127 */             map((Type)Type.VAR_INT);
/*     */ 
/*     */             
/* 130 */             handler(wrapper -> {
/*     */                   int entityID = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   tracker.addEntity(entityID, (EntityType)Entity1_10Types.EntityType.EXPERIENCE_ORB);
/*     */                   tracker.sendMetadataBuffer(entityID);
/*     */                 });
/* 137 */             map((Type)Type.INT, SpawnPackets.toNewDouble);
/* 138 */             map((Type)Type.INT, SpawnPackets.toNewDouble);
/* 139 */             map((Type)Type.INT, SpawnPackets.toNewDouble);
/*     */             
/* 141 */             map((Type)Type.SHORT);
/*     */           }
/*     */         });
/*     */     
/* 145 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_GLOBAL_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 148 */             map((Type)Type.VAR_INT);
/* 149 */             map((Type)Type.BYTE);
/*     */             
/* 151 */             handler(wrapper -> {
/*     */                   int entityID = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   
/*     */                   tracker.addEntity(entityID, (EntityType)Entity1_10Types.EntityType.LIGHTNING);
/*     */                   tracker.sendMetadataBuffer(entityID);
/*     */                 });
/* 159 */             map((Type)Type.INT, SpawnPackets.toNewDouble);
/* 160 */             map((Type)Type.INT, SpawnPackets.toNewDouble);
/* 161 */             map((Type)Type.INT, SpawnPackets.toNewDouble);
/*     */           }
/*     */         });
/*     */     
/* 165 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 168 */             map((Type)Type.VAR_INT);
/*     */             
/* 170 */             handler(wrapper -> {
/*     */                   int entityID = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   wrapper.write(Type.UUID, tracker.getEntityUUID(entityID));
/*     */                 });
/* 175 */             map((Type)Type.UNSIGNED_BYTE);
/*     */ 
/*     */             
/* 178 */             handler(wrapper -> {
/*     */                   int entityID = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   int typeID = ((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   tracker.addEntity(entityID, (EntityType)Entity1_10Types.getTypeFromId(typeID, false));
/*     */                   tracker.sendMetadataBuffer(entityID);
/*     */                 });
/* 186 */             map((Type)Type.INT, SpawnPackets.toNewDouble);
/* 187 */             map((Type)Type.INT, SpawnPackets.toNewDouble);
/* 188 */             map((Type)Type.INT, SpawnPackets.toNewDouble);
/*     */             
/* 190 */             map((Type)Type.BYTE);
/* 191 */             map((Type)Type.BYTE);
/* 192 */             map((Type)Type.BYTE);
/*     */             
/* 194 */             map((Type)Type.SHORT);
/* 195 */             map((Type)Type.SHORT);
/* 196 */             map((Type)Type.SHORT);
/*     */             
/* 198 */             map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
/* 199 */             handler(wrapper -> {
/*     */                   List<Metadata> metadataList = (List<Metadata>)wrapper.get(Types1_9.METADATA_LIST, 0);
/*     */                   
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   if (tracker.hasEntity(entityId)) {
/*     */                     ((MetadataRewriter1_9To1_8)protocol.get(MetadataRewriter1_9To1_8.class)).handleMetadata(entityId, metadataList, wrapper.user());
/*     */                   } else {
/*     */                     Via.getPlatform().getLogger().warning("Unable to find entity for metadata, entity ID: " + entityId);
/*     */                     metadataList.clear();
/*     */                   } 
/*     */                 });
/* 211 */             handler(wrapper -> {
/*     */                   List<Metadata> metadataList = (List<Metadata>)wrapper.get(Types1_9.METADATA_LIST, 0);
/*     */                   
/*     */                   int entityID = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   tracker.handleMetadata(entityID, metadataList);
/*     */                 });
/*     */           }
/*     */         });
/* 220 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_PAINTING, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 223 */             map((Type)Type.VAR_INT);
/*     */ 
/*     */             
/* 226 */             handler(wrapper -> {
/*     */                   int entityID = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   tracker.addEntity(entityID, (EntityType)Entity1_10Types.EntityType.PAINTING);
/*     */                   tracker.sendMetadataBuffer(entityID);
/*     */                 });
/* 232 */             handler(wrapper -> {
/*     */                   int entityID = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   wrapper.write(Type.UUID, tracker.getEntityUUID(entityID));
/*     */                 });
/* 238 */             map(Type.STRING);
/* 239 */             map(Type.POSITION);
/* 240 */             map((Type)Type.BYTE);
/*     */           }
/*     */         });
/*     */     
/* 244 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_PLAYER, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 247 */             map((Type)Type.VAR_INT);
/* 248 */             map(Type.UUID);
/*     */ 
/*     */             
/* 251 */             handler(wrapper -> {
/*     */                   int entityID = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   tracker.addEntity(entityID, (EntityType)Entity1_10Types.EntityType.PLAYER);
/*     */                   tracker.sendMetadataBuffer(entityID);
/*     */                 });
/* 258 */             map((Type)Type.INT, SpawnPackets.toNewDouble);
/* 259 */             map((Type)Type.INT, SpawnPackets.toNewDouble);
/* 260 */             map((Type)Type.INT, SpawnPackets.toNewDouble);
/*     */             
/* 262 */             map((Type)Type.BYTE);
/* 263 */             map((Type)Type.BYTE);
/*     */ 
/*     */             
/* 266 */             handler(wrapper -> {
/*     */                   short item = ((Short)wrapper.read((Type)Type.SHORT)).shortValue();
/*     */                   if (item != 0) {
/*     */                     PacketWrapper packet = PacketWrapper.create((PacketType)ClientboundPackets1_9.ENTITY_EQUIPMENT, null, wrapper.user());
/*     */                     packet.write((Type)Type.VAR_INT, wrapper.get((Type)Type.VAR_INT, 0));
/*     */                     packet.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                     packet.write(Type.ITEM, new DataItem(item, (byte)1, (short)0, null));
/*     */                     try {
/*     */                       packet.send(Protocol1_9To1_8.class);
/* 275 */                     } catch (Exception e) {
/*     */                       e.printStackTrace();
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */             
/* 281 */             map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
/*     */             
/* 283 */             handler(wrapper -> {
/*     */                   List<Metadata> metadataList = (List<Metadata>)wrapper.get(Types1_9.METADATA_LIST, 0);
/*     */                   
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   if (tracker.hasEntity(entityId)) {
/*     */                     ((MetadataRewriter1_9To1_8)protocol.get(MetadataRewriter1_9To1_8.class)).handleMetadata(entityId, metadataList, wrapper.user());
/*     */                   } else {
/*     */                     Via.getPlatform().getLogger().warning("Unable to find entity for metadata, entity ID: " + entityId);
/*     */                     metadataList.clear();
/*     */                   } 
/*     */                 });
/* 296 */             handler(wrapper -> {
/*     */                   List<Metadata> metadataList = (List<Metadata>)wrapper.get(Types1_9.METADATA_LIST, 0);
/*     */                   
/*     */                   int entityID = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   tracker.handleMetadata(entityID, metadataList);
/*     */                 });
/*     */           }
/*     */         });
/* 305 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.DESTROY_ENTITIES, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register()
/*     */           {
/* 309 */             map(Type.VAR_INT_ARRAY_PRIMITIVE);
/*     */             
/* 311 */             handler(wrapper -> {
/*     */                   int[] entities = (int[])wrapper.get(Type.VAR_INT_ARRAY_PRIMITIVE, 0);
/*     */                   EntityTracker tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   for (int entity : entities)
/*     */                     tracker.removeEntity(entity); 
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\packets\SpawnPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
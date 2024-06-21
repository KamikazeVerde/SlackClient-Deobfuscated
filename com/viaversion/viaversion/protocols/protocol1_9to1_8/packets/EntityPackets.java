/*     */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
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
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetadataRewriter1_9To1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
/*     */ import com.viaversion.viaversion.util.Pair;
/*     */ import com.viaversion.viaversion.util.Triple;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ public class EntityPackets
/*     */ {
/*  46 */   public static final ValueTransformer<Byte, Short> toNewShort = new ValueTransformer<Byte, Short>((Type)Type.SHORT)
/*     */     {
/*     */       public Short transform(PacketWrapper wrapper, Byte inputValue) {
/*  49 */         return Short.valueOf((short)(inputValue.byteValue() * 128));
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public static void register(final Protocol1_9To1_8 protocol) {
/*  55 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ATTACH_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register()
/*     */           {
/*  59 */             map((Type)Type.INT);
/*  60 */             map((Type)Type.INT);
/*     */ 
/*     */             
/*  63 */             map((Type)Type.UNSIGNED_BYTE, new ValueTransformer<Short, Void>((Type)Type.NOTHING)
/*     */                 {
/*     */                   public Void transform(PacketWrapper wrapper, Short inputValue) throws Exception {
/*  66 */                     EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*  67 */                     if (inputValue.shortValue() == 0) {
/*  68 */                       int passenger = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*  69 */                       int vehicle = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                       
/*  71 */                       wrapper.cancel();
/*     */                       
/*  73 */                       PacketWrapper passengerPacket = wrapper.create((PacketType)ClientboundPackets1_9.SET_PASSENGERS);
/*  74 */                       if (vehicle == -1) {
/*  75 */                         if (!tracker.getVehicleMap().containsKey(Integer.valueOf(passenger)))
/*  76 */                           return null; 
/*  77 */                         passengerPacket.write((Type)Type.VAR_INT, tracker.getVehicleMap().remove(Integer.valueOf(passenger)));
/*  78 */                         passengerPacket.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[0]);
/*     */                       } else {
/*  80 */                         passengerPacket.write((Type)Type.VAR_INT, Integer.valueOf(vehicle));
/*  81 */                         passengerPacket.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { passenger });
/*  82 */                         tracker.getVehicleMap().put(Integer.valueOf(passenger), Integer.valueOf(vehicle));
/*     */                       } 
/*  84 */                       passengerPacket.send(Protocol1_9To1_8.class);
/*     */                     } 
/*  86 */                     return null;
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*  91 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_TELEPORT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register()
/*     */           {
/*  95 */             map((Type)Type.VAR_INT);
/*  96 */             map((Type)Type.INT, SpawnPackets.toNewDouble);
/*  97 */             map((Type)Type.INT, SpawnPackets.toNewDouble);
/*  98 */             map((Type)Type.INT, SpawnPackets.toNewDouble);
/*     */             
/* 100 */             map((Type)Type.BYTE);
/* 101 */             map((Type)Type.BYTE);
/*     */             
/* 103 */             map((Type)Type.BOOLEAN);
/*     */             
/* 105 */             handler(wrapper -> {
/*     */                   int entityID = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   if (Via.getConfig().isHologramPatch()) {
/*     */                     EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                     
/*     */                     if (tracker.getKnownHolograms().contains(Integer.valueOf(entityID))) {
/*     */                       Double newValue = (Double)wrapper.get((Type)Type.DOUBLE, 1);
/*     */                       newValue = Double.valueOf(newValue.doubleValue() + Via.getConfig().getHologramYOffset());
/*     */                       wrapper.set((Type)Type.DOUBLE, 1, newValue);
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 120 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_POSITION_AND_ROTATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register()
/*     */           {
/* 124 */             map((Type)Type.VAR_INT);
/* 125 */             map((Type)Type.BYTE, EntityPackets.toNewShort);
/* 126 */             map((Type)Type.BYTE, EntityPackets.toNewShort);
/* 127 */             map((Type)Type.BYTE, EntityPackets.toNewShort);
/*     */             
/* 129 */             map((Type)Type.BYTE);
/* 130 */             map((Type)Type.BYTE);
/*     */             
/* 132 */             map((Type)Type.BOOLEAN);
/*     */           }
/*     */         });
/* 135 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_POSITION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register()
/*     */           {
/* 139 */             map((Type)Type.VAR_INT);
/* 140 */             map((Type)Type.BYTE, EntityPackets.toNewShort);
/* 141 */             map((Type)Type.BYTE, EntityPackets.toNewShort);
/* 142 */             map((Type)Type.BYTE, EntityPackets.toNewShort);
/*     */             
/* 144 */             map((Type)Type.BOOLEAN);
/*     */           }
/*     */         });
/* 147 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_EQUIPMENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register()
/*     */           {
/* 151 */             map((Type)Type.VAR_INT);
/*     */             
/* 153 */             map((Type)Type.SHORT, new ValueTransformer<Short, Integer>((Type)Type.VAR_INT)
/*     */                 {
/*     */                   public Integer transform(PacketWrapper wrapper, Short slot) throws Exception {
/* 156 */                     int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/* 157 */                     int receiverId = wrapper.user().getEntityTracker(Protocol1_9To1_8.class).clientEntityId();
/*     */ 
/*     */ 
/*     */                     
/* 161 */                     if (entityId == receiverId) {
/* 162 */                       return Integer.valueOf(slot.intValue() + 2);
/*     */                     }
/* 164 */                     return Integer.valueOf((slot.shortValue() > 0) ? (slot.intValue() + 1) : slot.intValue());
/*     */                   }
/*     */                 });
/* 167 */             map(Type.ITEM);
/*     */             
/* 169 */             handler(wrapper -> {
/*     */                   Item stack = (Item)wrapper.get(Type.ITEM, 0);
/*     */                   
/*     */                   ItemRewriter.toClient(stack);
/*     */                 });
/* 174 */             handler(wrapper -> {
/*     */                   EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   
/*     */                   int entityID = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   Item stack = (Item)wrapper.get(Type.ITEM, 0);
/*     */                   
/*     */                   if (stack != null && Protocol1_9To1_8.isSword(stack.identifier())) {
/*     */                     entityTracker.getValidBlocking().add(Integer.valueOf(entityID));
/*     */                     return;
/*     */                   } 
/*     */                   entityTracker.getValidBlocking().remove(Integer.valueOf(entityID));
/*     */                 });
/*     */           }
/*     */         });
/* 189 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_METADATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register()
/*     */           {
/* 193 */             map((Type)Type.VAR_INT);
/* 194 */             map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
/* 195 */             handler(wrapper -> {
/*     */                   List<Metadata> metadataList = (List<Metadata>)wrapper.get(Types1_9.METADATA_LIST, 0);
/*     */                   
/*     */                   int entityId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   
/*     */                   if (tracker.hasEntity(entityId)) {
/*     */                     ((MetadataRewriter1_9To1_8)protocol.get(MetadataRewriter1_9To1_8.class)).handleMetadata(entityId, metadataList, wrapper.user());
/*     */                   } else {
/*     */                     tracker.addMetadataToBuffer(entityId, metadataList);
/*     */                     wrapper.cancel();
/*     */                   } 
/*     */                 });
/* 209 */             handler(wrapper -> {
/*     */                   List<Metadata> metadataList = (List<Metadata>)wrapper.get(Types1_9.METADATA_LIST, 0);
/*     */                   
/*     */                   int entityID = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   tracker.handleMetadata(entityID, metadataList);
/*     */                 });
/* 217 */             handler(wrapper -> {
/*     */                   List<Metadata> metadataList = (List<Metadata>)wrapper.get(Types1_9.METADATA_LIST, 0);
/*     */                   
/*     */                   if (metadataList.isEmpty()) {
/*     */                     wrapper.cancel();
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 226 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 229 */             map((Type)Type.VAR_INT);
/* 230 */             map((Type)Type.BYTE);
/* 231 */             map((Type)Type.BYTE);
/* 232 */             map((Type)Type.VAR_INT);
/*     */             
/* 234 */             handler(wrapper -> {
/*     */                   boolean showParticles = ((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue();
/*     */                   
/*     */                   boolean newEffect = Via.getConfig().isNewEffectIndicator();
/*     */                   
/*     */                   wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)(showParticles ? (newEffect ? 2 : 1) : 0)));
/*     */                 });
/*     */           }
/*     */         });
/* 243 */     protocol.cancelClientbound((ClientboundPacketType)ClientboundPackets1_8.UPDATE_ENTITY_NBT);
/*     */     
/* 245 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.COMBAT_EVENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 248 */             map((Type)Type.VAR_INT);
/* 249 */             handler(wrapper -> {
/*     */                   if (((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue() == 2) {
/*     */                     wrapper.passthrough((Type)Type.VAR_INT);
/*     */                     
/*     */                     wrapper.passthrough((Type)Type.INT);
/*     */                     Protocol1_9To1_8.FIX_JSON.write(wrapper, wrapper.read(Type.STRING));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 259 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_PROPERTIES, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 262 */             map((Type)Type.VAR_INT);
/* 263 */             handler(wrapper -> {
/*     */                   if (!Via.getConfig().isMinimizeCooldown()) {
/*     */                     return;
/*     */                   }
/*     */                   
/*     */                   EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   
/*     */                   if (((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue() != tracker.getProvidedEntityId()) {
/*     */                     return;
/*     */                   }
/*     */                   
/*     */                   int propertiesToRead = ((Integer)wrapper.read((Type)Type.INT)).intValue();
/*     */                   
/*     */                   Map<String, Pair<Double, List<Triple<UUID, Double, Byte>>>> properties = new HashMap<>(propertiesToRead);
/*     */                   
/*     */                   for (int i = 0; i < propertiesToRead; i++) {
/*     */                     String key = (String)wrapper.read(Type.STRING);
/*     */                     
/*     */                     Double value = (Double)wrapper.read((Type)Type.DOUBLE);
/*     */                     
/*     */                     int modifiersToRead = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                     
/*     */                     List<Triple<UUID, Double, Byte>> modifiers = new ArrayList<>(modifiersToRead);
/*     */                     
/*     */                     for (int j = 0; j < modifiersToRead; j++) {
/*     */                       modifiers.add(new Triple(wrapper.read(Type.UUID), wrapper.read((Type)Type.DOUBLE), wrapper.read((Type)Type.BYTE)));
/*     */                     }
/*     */                     
/*     */                     properties.put(key, new Pair(value, modifiers));
/*     */                   } 
/*     */                   
/*     */                   properties.put("generic.attackSpeed", new Pair(Double.valueOf(15.9D), ImmutableList.of(new Triple(UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3"), Double.valueOf(0.0D), Byte.valueOf((byte)0)), new Triple(UUID.fromString("AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3"), Double.valueOf(0.0D), Byte.valueOf((byte)2)), new Triple(UUID.fromString("55FCED67-E92A-486E-9800-B47F202C4386"), Double.valueOf(0.0D), Byte.valueOf((byte)2)))));
/*     */                   
/*     */                   wrapper.write((Type)Type.INT, Integer.valueOf(properties.size()));
/*     */                   
/*     */                   for (Map.Entry<String, Pair<Double, List<Triple<UUID, Double, Byte>>>> entry : properties.entrySet()) {
/*     */                     wrapper.write(Type.STRING, entry.getKey());
/*     */                     
/*     */                     wrapper.write((Type)Type.DOUBLE, ((Pair)entry.getValue()).key());
/*     */                     
/*     */                     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(((List)((Pair)entry.getValue()).value()).size()));
/*     */                     for (Triple<UUID, Double, Byte> modifier : (Iterable<Triple<UUID, Double, Byte>>)((Pair)entry.getValue()).value()) {
/*     */                       wrapper.write(Type.UUID, modifier.first());
/*     */                       wrapper.write((Type)Type.DOUBLE, modifier.second());
/*     */                       wrapper.write((Type)Type.BYTE, modifier.third());
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 313 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.ENTITY_ANIMATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 316 */             map((Type)Type.VAR_INT);
/* 317 */             map((Type)Type.UNSIGNED_BYTE);
/* 318 */             handler(wrapper -> {
/*     */                   if (((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue() == 3) {
/*     */                     wrapper.cancel();
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 328 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9.ENTITY_ACTION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register()
/*     */           {
/* 332 */             map((Type)Type.VAR_INT);
/* 333 */             map((Type)Type.VAR_INT);
/* 334 */             map((Type)Type.VAR_INT);
/* 335 */             handler(wrapper -> {
/*     */                   int action = ((Integer)wrapper.get((Type)Type.VAR_INT, 1)).intValue();
/*     */                   if (action == 6 || action == 8) {
/*     */                     wrapper.cancel();
/*     */                   }
/*     */                   if (action == 7) {
/*     */                     wrapper.set((Type)Type.VAR_INT, 1, Integer.valueOf(6));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 346 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9.INTERACT_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register()
/*     */           {
/* 350 */             map((Type)Type.VAR_INT);
/* 351 */             map((Type)Type.VAR_INT);
/*     */ 
/*     */             
/* 354 */             handler(wrapper -> {
/*     */                   int type = ((Integer)wrapper.get((Type)Type.VAR_INT, 1)).intValue();
/*     */                   if (type == 2) {
/*     */                     wrapper.passthrough((Type)Type.FLOAT);
/*     */                     wrapper.passthrough((Type)Type.FLOAT);
/*     */                     wrapper.passthrough((Type)Type.FLOAT);
/*     */                   } 
/*     */                   if (type == 0 || type == 2) {
/*     */                     int hand = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                     if (hand == 1)
/*     */                       wrapper.cancel(); 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\packets\EntityPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
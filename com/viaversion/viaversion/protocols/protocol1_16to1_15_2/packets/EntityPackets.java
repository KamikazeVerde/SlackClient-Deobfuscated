/*     */ package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.WorldIdentifiers;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_14;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_16;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.MappingData;
/*     */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.metadata.MetadataRewriter1_16To1_15_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.storage.InventoryTracker1_16;
/*     */ import java.util.Arrays;
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
/*     */ public class EntityPackets
/*     */ {
/*     */   private static final PacketHandler DIMENSION_HANDLER;
/*     */   
/*     */   static {
/*  46 */     DIMENSION_HANDLER = (wrapper -> {
/*     */         String dimensionName;
/*     */         String outputName;
/*     */         WorldIdentifiers map = Via.getConfig().get1_16WorldNamesMap();
/*     */         WorldIdentifiers userMap = (WorldIdentifiers)wrapper.user().get(WorldIdentifiers.class);
/*     */         if (userMap != null)
/*     */           map = userMap; 
/*     */         int dimension = ((Integer)wrapper.read((Type)Type.INT)).intValue();
/*     */         switch (dimension) {
/*     */           case -1:
/*     */             dimensionName = "minecraft:the_nether";
/*     */             outputName = map.nether();
/*     */             break;
/*     */           case 0:
/*     */             dimensionName = "minecraft:overworld";
/*     */             outputName = map.overworld();
/*     */             break;
/*     */           case 1:
/*     */             dimensionName = "minecraft:the_end";
/*     */             outputName = map.end();
/*     */             break;
/*     */           default:
/*     */             Via.getPlatform().getLogger().warning("Invalid dimension id: " + dimension);
/*     */             dimensionName = "minecraft:overworld";
/*     */             outputName = map.overworld();
/*     */             break;
/*     */         } 
/*     */         wrapper.write(Type.STRING, dimensionName);
/*     */         wrapper.write(Type.STRING, outputName);
/*     */       });
/*     */   }
/*  77 */   public static final CompoundTag DIMENSIONS_TAG = new CompoundTag();
/*  78 */   private static final String[] WORLD_NAMES = new String[] { "minecraft:overworld", "minecraft:the_nether", "minecraft:the_end" };
/*     */   
/*     */   static {
/*  81 */     ListTag list = new ListTag(CompoundTag.class);
/*  82 */     list.add((Tag)createOverworldEntry());
/*  83 */     list.add((Tag)createOverworldCavesEntry());
/*  84 */     list.add((Tag)createNetherEntry());
/*  85 */     list.add((Tag)createEndEntry());
/*  86 */     DIMENSIONS_TAG.put("dimension", (Tag)list);
/*     */   }
/*     */   
/*     */   private static CompoundTag createOverworldEntry() {
/*  90 */     CompoundTag tag = new CompoundTag();
/*  91 */     tag.put("name", (Tag)new StringTag("minecraft:overworld"));
/*  92 */     tag.put("has_ceiling", (Tag)new ByteTag((byte)0));
/*  93 */     addSharedOverwaldEntries(tag);
/*  94 */     return tag;
/*     */   }
/*     */   
/*     */   private static CompoundTag createOverworldCavesEntry() {
/*  98 */     CompoundTag tag = new CompoundTag();
/*  99 */     tag.put("name", (Tag)new StringTag("minecraft:overworld_caves"));
/* 100 */     tag.put("has_ceiling", (Tag)new ByteTag((byte)1));
/* 101 */     addSharedOverwaldEntries(tag);
/* 102 */     return tag;
/*     */   }
/*     */   
/*     */   private static void addSharedOverwaldEntries(CompoundTag tag) {
/* 106 */     tag.put("piglin_safe", (Tag)new ByteTag((byte)0));
/* 107 */     tag.put("natural", (Tag)new ByteTag((byte)1));
/* 108 */     tag.put("ambient_light", (Tag)new FloatTag(0.0F));
/* 109 */     tag.put("infiniburn", (Tag)new StringTag("minecraft:infiniburn_overworld"));
/* 110 */     tag.put("respawn_anchor_works", (Tag)new ByteTag((byte)0));
/* 111 */     tag.put("has_skylight", (Tag)new ByteTag((byte)1));
/* 112 */     tag.put("bed_works", (Tag)new ByteTag((byte)1));
/* 113 */     tag.put("has_raids", (Tag)new ByteTag((byte)1));
/* 114 */     tag.put("logical_height", (Tag)new IntTag(256));
/* 115 */     tag.put("shrunk", (Tag)new ByteTag((byte)0));
/* 116 */     tag.put("ultrawarm", (Tag)new ByteTag((byte)0));
/*     */   }
/*     */   
/*     */   private static CompoundTag createNetherEntry() {
/* 120 */     CompoundTag tag = new CompoundTag();
/* 121 */     tag.put("piglin_safe", (Tag)new ByteTag((byte)1));
/* 122 */     tag.put("natural", (Tag)new ByteTag((byte)0));
/* 123 */     tag.put("ambient_light", (Tag)new FloatTag(0.1F));
/* 124 */     tag.put("infiniburn", (Tag)new StringTag("minecraft:infiniburn_nether"));
/* 125 */     tag.put("respawn_anchor_works", (Tag)new ByteTag((byte)1));
/* 126 */     tag.put("has_skylight", (Tag)new ByteTag((byte)0));
/* 127 */     tag.put("bed_works", (Tag)new ByteTag((byte)0));
/* 128 */     tag.put("fixed_time", (Tag)new LongTag(18000L));
/* 129 */     tag.put("has_raids", (Tag)new ByteTag((byte)0));
/* 130 */     tag.put("name", (Tag)new StringTag("minecraft:the_nether"));
/* 131 */     tag.put("logical_height", (Tag)new IntTag(128));
/* 132 */     tag.put("shrunk", (Tag)new ByteTag((byte)1));
/* 133 */     tag.put("ultrawarm", (Tag)new ByteTag((byte)1));
/* 134 */     tag.put("has_ceiling", (Tag)new ByteTag((byte)1));
/* 135 */     return tag;
/*     */   }
/*     */   
/*     */   private static CompoundTag createEndEntry() {
/* 139 */     CompoundTag tag = new CompoundTag();
/* 140 */     tag.put("piglin_safe", (Tag)new ByteTag((byte)0));
/* 141 */     tag.put("natural", (Tag)new ByteTag((byte)0));
/* 142 */     tag.put("ambient_light", (Tag)new FloatTag(0.0F));
/* 143 */     tag.put("infiniburn", (Tag)new StringTag("minecraft:infiniburn_end"));
/* 144 */     tag.put("respawn_anchor_works", (Tag)new ByteTag((byte)0));
/* 145 */     tag.put("has_skylight", (Tag)new ByteTag((byte)0));
/* 146 */     tag.put("bed_works", (Tag)new ByteTag((byte)0));
/* 147 */     tag.put("fixed_time", (Tag)new LongTag(6000L));
/* 148 */     tag.put("has_raids", (Tag)new ByteTag((byte)1));
/* 149 */     tag.put("name", (Tag)new StringTag("minecraft:the_end"));
/* 150 */     tag.put("logical_height", (Tag)new IntTag(256));
/* 151 */     tag.put("shrunk", (Tag)new ByteTag((byte)0));
/* 152 */     tag.put("ultrawarm", (Tag)new ByteTag((byte)0));
/* 153 */     tag.put("has_ceiling", (Tag)new ByteTag((byte)0));
/* 154 */     return tag;
/*     */   }
/*     */   
/*     */   public static void register(Protocol1_16To1_15_2 protocol) {
/* 158 */     MetadataRewriter1_16To1_15_2 metadataRewriter = (MetadataRewriter1_16To1_15_2)protocol.get(MetadataRewriter1_16To1_15_2.class);
/*     */ 
/*     */     
/* 161 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_15.SPAWN_GLOBAL_ENTITY, (ClientboundPacketType)ClientboundPackets1_16.SPAWN_ENTITY, wrapper -> {
/*     */           int entityId = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           byte type = ((Byte)wrapper.read((Type)Type.BYTE)).byteValue();
/*     */           
/*     */           if (type != 1) {
/*     */             wrapper.cancel();
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           wrapper.user().getEntityTracker(Protocol1_16To1_15_2.class).addEntity(entityId, (EntityType)Entity1_16Types.LIGHTNING_BOLT);
/*     */           
/*     */           wrapper.write(Type.UUID, UUID.randomUUID());
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(Entity1_16Types.LIGHTNING_BOLT.getId()));
/*     */           wrapper.passthrough((Type)Type.DOUBLE);
/*     */           wrapper.passthrough((Type)Type.DOUBLE);
/*     */           wrapper.passthrough((Type)Type.DOUBLE);
/*     */           wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/*     */           wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)0));
/*     */           wrapper.write((Type)Type.INT, Integer.valueOf(0));
/*     */           wrapper.write((Type)Type.SHORT, Short.valueOf((short)0));
/*     */           wrapper.write((Type)Type.SHORT, Short.valueOf((short)0));
/*     */           wrapper.write((Type)Type.SHORT, Short.valueOf((short)0));
/*     */         });
/* 186 */     metadataRewriter.registerTrackerWithData((ClientboundPacketType)ClientboundPackets1_15.SPAWN_ENTITY, (EntityType)Entity1_16Types.FALLING_BLOCK);
/* 187 */     metadataRewriter.registerTracker((ClientboundPacketType)ClientboundPackets1_15.SPAWN_MOB);
/* 188 */     metadataRewriter.registerTracker((ClientboundPacketType)ClientboundPackets1_15.SPAWN_PLAYER, (EntityType)Entity1_16Types.PLAYER);
/* 189 */     metadataRewriter.registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_15.ENTITY_METADATA, Types1_14.METADATA_LIST, Types1_16.METADATA_LIST);
/* 190 */     metadataRewriter.registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_15.DESTROY_ENTITIES);
/*     */     
/* 192 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_15.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 195 */             handler(EntityPackets.DIMENSION_HANDLER);
/* 196 */             map((Type)Type.LONG);
/* 197 */             map((Type)Type.UNSIGNED_BYTE);
/* 198 */             handler(wrapper -> {
/*     */                   wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)-1));
/*     */                   
/*     */                   String levelType = (String)wrapper.read(Type.STRING);
/*     */                   
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(levelType.equals("flat")));
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */                 });
/*     */           }
/*     */         });
/* 209 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_15.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 212 */             map((Type)Type.INT);
/* 213 */             map((Type)Type.UNSIGNED_BYTE);
/* 214 */             handler(wrapper -> {
/*     */                   wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)-1));
/*     */                   wrapper.write(Type.STRING_ARRAY, Arrays.copyOf(EntityPackets.WORLD_NAMES, EntityPackets.WORLD_NAMES.length));
/*     */                   wrapper.write(Type.NBT, EntityPackets.DIMENSIONS_TAG.clone());
/*     */                 });
/* 219 */             handler(EntityPackets.DIMENSION_HANDLER);
/* 220 */             map((Type)Type.LONG);
/* 221 */             map((Type)Type.UNSIGNED_BYTE);
/* 222 */             handler(wrapper -> {
/*     */                   wrapper.user().getEntityTracker(Protocol1_16To1_15_2.class).addEntity(((Integer)wrapper.get((Type)Type.INT, 0)).intValue(), (EntityType)Entity1_16Types.PLAYER);
/*     */                   
/*     */                   String type = (String)wrapper.read(Type.STRING);
/*     */                   
/*     */                   wrapper.passthrough((Type)Type.VAR_INT);
/*     */                   
/*     */                   wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                   wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(type.equals("flat")));
/*     */                 });
/*     */           }
/*     */         });
/* 236 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_15.ENTITY_PROPERTIES, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */           
/*     */           int size = ((Integer)wrapper.passthrough((Type)Type.INT)).intValue();
/*     */           
/*     */           int actualSize = size;
/*     */           
/*     */           for (int i = 0; i < size; i++) {
/*     */             String key = (String)wrapper.read(Type.STRING);
/*     */             String attributeIdentifier = (String)protocol.getMappingData().getAttributeMappings().get(key);
/*     */             if (attributeIdentifier == null) {
/*     */               attributeIdentifier = "minecraft:" + key;
/*     */               if (!MappingData.isValid1_13Channel(attributeIdentifier)) {
/*     */                 if (!Via.getConfig().isSuppressConversionWarnings()) {
/*     */                   Via.getPlatform().getLogger().warning("Invalid attribute: " + key);
/*     */                 }
/*     */                 actualSize--;
/*     */                 wrapper.read((Type)Type.DOUBLE);
/*     */                 int k = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                 for (int m = 0; m < k; m++) {
/*     */                   wrapper.read(Type.UUID);
/*     */                   wrapper.read((Type)Type.DOUBLE);
/*     */                   wrapper.read((Type)Type.BYTE);
/*     */                 } 
/*     */                 continue;
/*     */               } 
/*     */             } 
/*     */             wrapper.write(Type.STRING, attributeIdentifier);
/*     */             wrapper.passthrough((Type)Type.DOUBLE);
/*     */             int modifierSize = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */             for (int j = 0; j < modifierSize; j++) {
/*     */               wrapper.passthrough(Type.UUID);
/*     */               wrapper.passthrough((Type)Type.DOUBLE);
/*     */               wrapper.passthrough((Type)Type.BYTE);
/*     */             } 
/*     */             continue;
/*     */           } 
/*     */           if (size != actualSize) {
/*     */             wrapper.set((Type)Type.INT, 0, Integer.valueOf(actualSize));
/*     */           }
/*     */         });
/* 277 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_16.ANIMATION, wrapper -> {
/*     */           InventoryTracker1_16 inventoryTracker = (InventoryTracker1_16)wrapper.user().get(InventoryTracker1_16.class);
/*     */           if (inventoryTracker.isInventoryOpen())
/*     */             wrapper.cancel(); 
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_16to1_15_2\packets\EntityPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.Protocol1_16_1To1_16_2;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.storage.BiomeStorage;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_16;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.EntityPackets;
/*     */ import java.util.Set;
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
/*     */ public class EntityPackets1_16_2
/*     */   extends EntityRewriter<ClientboundPackets1_16_2, Protocol1_16_1To1_16_2>
/*     */ {
/*  43 */   private final Set<String> oldDimensions = Sets.newHashSet((Object[])new String[] { "minecraft:overworld", "minecraft:the_nether", "minecraft:the_end" });
/*     */   private boolean warned;
/*     */   
/*     */   public EntityPackets1_16_2(Protocol1_16_1To1_16_2 protocol) {
/*  47 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  52 */     registerTrackerWithData((ClientboundPacketType)ClientboundPackets1_16_2.SPAWN_ENTITY, (EntityType)Entity1_16_2Types.FALLING_BLOCK);
/*  53 */     registerSpawnTracker((ClientboundPacketType)ClientboundPackets1_16_2.SPAWN_MOB);
/*  54 */     registerTracker((ClientboundPacketType)ClientboundPackets1_16_2.SPAWN_EXPERIENCE_ORB, (EntityType)Entity1_16_2Types.EXPERIENCE_ORB);
/*  55 */     registerTracker((ClientboundPacketType)ClientboundPackets1_16_2.SPAWN_PAINTING, (EntityType)Entity1_16_2Types.PAINTING);
/*  56 */     registerTracker((ClientboundPacketType)ClientboundPackets1_16_2.SPAWN_PLAYER, (EntityType)Entity1_16_2Types.PLAYER);
/*  57 */     registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_16_2.DESTROY_ENTITIES);
/*  58 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_16_2.ENTITY_METADATA, Types1_16.METADATA_LIST);
/*     */     
/*  60 */     ((Protocol1_16_1To1_16_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  63 */             map((Type)Type.INT);
/*  64 */             handler(wrapper -> {
/*     */                   boolean hardcore = ((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue();
/*     */                   short gamemode = ((Short)wrapper.read((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                   if (hardcore) {
/*     */                     gamemode = (short)(gamemode | 0x8);
/*     */                   }
/*     */                   wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf(gamemode));
/*     */                 });
/*  72 */             map((Type)Type.BYTE);
/*  73 */             map(Type.STRING_ARRAY);
/*  74 */             handler(wrapper -> {
/*     */                   CompoundTag registry = (CompoundTag)wrapper.read(Type.NBT);
/*     */                   
/*     */                   if (wrapper.user().getProtocolInfo().getProtocolVersion() <= ProtocolVersion.v1_15_2.getVersion()) {
/*     */                     CompoundTag biomeRegistry = (CompoundTag)registry.get("minecraft:worldgen/biome");
/*     */                     
/*     */                     ListTag biomes = (ListTag)biomeRegistry.get("value");
/*     */                     
/*     */                     BiomeStorage biomeStorage = (BiomeStorage)wrapper.user().get(BiomeStorage.class);
/*     */                     
/*     */                     biomeStorage.clear();
/*     */                     for (Tag biome : biomes) {
/*     */                       CompoundTag biomeCompound = (CompoundTag)biome;
/*     */                       StringTag name = (StringTag)biomeCompound.get("name");
/*     */                       NumberTag id = (NumberTag)biomeCompound.get("id");
/*     */                       biomeStorage.addBiome(name.getValue(), id.asInt());
/*     */                     } 
/*     */                   } else if (!EntityPackets1_16_2.this.warned) {
/*     */                     EntityPackets1_16_2.this.warned = true;
/*     */                     ViaBackwards.getPlatform().getLogger().warning("1.16 and 1.16.1 clients are only partially supported and may have wrong biomes displayed.");
/*     */                   } 
/*     */                   wrapper.write(Type.NBT, EntityPackets.DIMENSIONS_TAG);
/*     */                   CompoundTag dimensionData = (CompoundTag)wrapper.read(Type.NBT);
/*     */                   wrapper.write(Type.STRING, EntityPackets1_16_2.this.getDimensionFromData(dimensionData));
/*     */                 });
/*  99 */             map(Type.STRING);
/* 100 */             map((Type)Type.LONG);
/* 101 */             handler(wrapper -> {
/*     */                   int maxPlayers = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)Math.min(maxPlayers, 255)));
/*     */                 });
/* 106 */             handler(EntityPackets1_16_2.this.getTrackerHandler((EntityType)Entity1_16_2Types.PLAYER, (Type)Type.INT));
/*     */           }
/*     */         });
/*     */     
/* 110 */     ((Protocol1_16_1To1_16_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.RESPAWN, wrapper -> {
/*     */           CompoundTag dimensionData = (CompoundTag)wrapper.read(Type.NBT);
/*     */           wrapper.write(Type.STRING, getDimensionFromData(dimensionData));
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private String getDimensionFromData(CompoundTag dimensionData) {
/* 118 */     StringTag effectsLocation = (StringTag)dimensionData.get("effects");
/* 119 */     return (effectsLocation != null && this.oldDimensions.contains(effectsLocation.getValue())) ? 
/* 120 */       effectsLocation.getValue() : "minecraft:overworld";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerRewrites() {
/* 125 */     registerMetaTypeHandler(Types1_16.META_TYPES.itemType, Types1_16.META_TYPES.blockStateType, null, Types1_16.META_TYPES.particleType, Types1_16.META_TYPES.componentType, Types1_16.META_TYPES.optionalComponentType);
/*     */ 
/*     */     
/* 128 */     mapTypes((EntityType[])Entity1_16_2Types.values(), Entity1_16Types.class);
/* 129 */     mapEntityTypeWithData((EntityType)Entity1_16_2Types.PIGLIN_BRUTE, (EntityType)Entity1_16_2Types.PIGLIN).jsonName();
/*     */     
/* 131 */     filter().filterFamily((EntityType)Entity1_16_2Types.ABSTRACT_PIGLIN).index(15).toIndex(16);
/* 132 */     filter().filterFamily((EntityType)Entity1_16_2Types.ABSTRACT_PIGLIN).index(16).toIndex(15);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int typeId) {
/* 137 */     return Entity1_16_2Types.getTypeFromId(typeId);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_16_1to1_16_2\packets\EntityPackets1_16_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
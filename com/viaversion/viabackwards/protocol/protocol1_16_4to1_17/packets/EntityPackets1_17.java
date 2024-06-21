/*     */ package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.Particle;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_16;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_17;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
/*     */ import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
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
/*     */ public final class EntityPackets1_17
/*     */   extends EntityRewriter<ClientboundPackets1_17, Protocol1_16_4To1_17>
/*     */ {
/*     */   private boolean warned;
/*     */   
/*     */   public EntityPackets1_17(Protocol1_16_4To1_17 protocol) {
/*  45 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  50 */     registerTrackerWithData((ClientboundPacketType)ClientboundPackets1_17.SPAWN_ENTITY, (EntityType)Entity1_17Types.FALLING_BLOCK);
/*  51 */     registerSpawnTracker((ClientboundPacketType)ClientboundPackets1_17.SPAWN_MOB);
/*  52 */     registerTracker((ClientboundPacketType)ClientboundPackets1_17.SPAWN_EXPERIENCE_ORB, (EntityType)Entity1_17Types.EXPERIENCE_ORB);
/*  53 */     registerTracker((ClientboundPacketType)ClientboundPackets1_17.SPAWN_PAINTING, (EntityType)Entity1_17Types.PAINTING);
/*  54 */     registerTracker((ClientboundPacketType)ClientboundPackets1_17.SPAWN_PLAYER, (EntityType)Entity1_17Types.PLAYER);
/*  55 */     registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_17.ENTITY_METADATA, Types1_17.METADATA_LIST, Types1_16.METADATA_LIST);
/*     */     
/*  57 */     ((Protocol1_16_4To1_17)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_17.REMOVE_ENTITY, (ClientboundPacketType)ClientboundPackets1_16_2.DESTROY_ENTITIES, wrapper -> {
/*     */           int entityId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           tracker(wrapper.user()).removeEntity(entityId);
/*     */           
/*     */           int[] array = { entityId };
/*     */           
/*     */           wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, array);
/*     */         });
/*  66 */     ((Protocol1_16_4To1_17)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_17.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  69 */             map((Type)Type.INT);
/*  70 */             map((Type)Type.BOOLEAN);
/*  71 */             map((Type)Type.UNSIGNED_BYTE);
/*  72 */             map((Type)Type.BYTE);
/*  73 */             map(Type.STRING_ARRAY);
/*  74 */             map(Type.NBT);
/*  75 */             map(Type.NBT);
/*  76 */             map(Type.STRING);
/*  77 */             handler(wrapper -> {
/*     */                   byte previousGamemode = ((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   if (previousGamemode == -1) {
/*     */                     wrapper.set((Type)Type.BYTE, 0, Byte.valueOf((byte)0));
/*     */                   }
/*     */                 });
/*  83 */             handler(EntityPackets1_17.this.getTrackerHandler((EntityType)Entity1_17Types.PLAYER, (Type)Type.INT));
/*  84 */             handler(EntityPackets1_17.this.worldDataTrackerHandler(1));
/*  85 */             handler(wrapper -> {
/*     */                   CompoundTag registry = (CompoundTag)wrapper.get(Type.NBT, 0);
/*     */                   
/*     */                   CompoundTag biomeRegistry = (CompoundTag)registry.get("minecraft:worldgen/biome");
/*     */                   
/*     */                   ListTag biomes = (ListTag)biomeRegistry.get("value");
/*     */                   
/*     */                   for (Tag biome : biomes) {
/*     */                     CompoundTag biomeCompound = (CompoundTag)((CompoundTag)biome).get("element");
/*     */                     StringTag category = (StringTag)biomeCompound.get("category");
/*     */                     if (category.getValue().equalsIgnoreCase("underground")) {
/*     */                       category.setValue("none");
/*     */                     }
/*     */                   } 
/*     */                   CompoundTag dimensionRegistry = (CompoundTag)registry.get("minecraft:dimension_type");
/*     */                   ListTag dimensions = (ListTag)dimensionRegistry.get("value");
/*     */                   for (Tag dimension : dimensions) {
/*     */                     CompoundTag dimensionCompound = (CompoundTag)((CompoundTag)dimension).get("element");
/*     */                     EntityPackets1_17.this.reduceExtendedHeight(dimensionCompound, false);
/*     */                   } 
/*     */                   EntityPackets1_17.this.reduceExtendedHeight((CompoundTag)wrapper.get(Type.NBT, 1), true);
/*     */                 });
/*     */           }
/*     */         });
/* 109 */     ((Protocol1_16_4To1_17)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_17.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 112 */             map(Type.NBT);
/* 113 */             map(Type.STRING);
/* 114 */             handler(EntityPackets1_17.this.worldDataTrackerHandler(0));
/* 115 */             handler(wrapper -> EntityPackets1_17.this.reduceExtendedHeight((CompoundTag)wrapper.get(Type.NBT, 0), true));
/*     */           }
/*     */         });
/*     */     
/* 119 */     ((Protocol1_16_4To1_17)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_17.PLAYER_POSITION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 122 */             map((Type)Type.DOUBLE);
/* 123 */             map((Type)Type.DOUBLE);
/* 124 */             map((Type)Type.DOUBLE);
/* 125 */             map((Type)Type.FLOAT);
/* 126 */             map((Type)Type.FLOAT);
/* 127 */             map((Type)Type.BYTE);
/* 128 */             map((Type)Type.VAR_INT);
/* 129 */             handler(wrapper -> wrapper.read((Type)Type.BOOLEAN));
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     ((Protocol1_16_4To1_17)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_17.ENTITY_PROPERTIES, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 139 */             map((Type)Type.VAR_INT);
/* 140 */             handler(wrapper -> wrapper.write((Type)Type.INT, wrapper.read((Type)Type.VAR_INT)));
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.COMBAT_ENTER, ClientboundPackets1_16_2.COMBAT_EVENT, 0);
/* 148 */     ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.COMBAT_END, ClientboundPackets1_16_2.COMBAT_EVENT, 1);
/* 149 */     ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.COMBAT_KILL, ClientboundPackets1_16_2.COMBAT_EVENT, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerRewrites() {
/* 154 */     filter().handler((event, meta) -> {
/*     */           meta.setMetaType(Types1_16.META_TYPES.byId(meta.metaType().typeId()));
/*     */           
/*     */           MetaType type = meta.metaType();
/*     */           
/*     */           if (type == Types1_16.META_TYPES.particleType) {
/*     */             Particle particle = (Particle)meta.getValue();
/*     */             
/*     */             if (particle.getId() == 16) {
/*     */               particle.getArguments().subList(4, 7).clear();
/*     */             } else if (particle.getId() == 37) {
/*     */               particle.setId(0);
/*     */               
/*     */               particle.getArguments().clear();
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/*     */             rewriteParticle(particle);
/*     */           } else if (type == Types1_16.META_TYPES.poseType) {
/*     */             int pose = ((Integer)meta.value()).intValue();
/*     */             
/*     */             if (pose == 6) {
/*     */               meta.setValue(Integer.valueOf(1));
/*     */             } else if (pose > 6) {
/*     */               meta.setValue(Integer.valueOf(pose - 1));
/*     */             } 
/*     */           } 
/*     */         });
/* 183 */     registerMetaTypeHandler(Types1_16.META_TYPES.itemType, Types1_16.META_TYPES.blockStateType, null, null, Types1_16.META_TYPES.componentType, Types1_16.META_TYPES.optionalComponentType);
/*     */ 
/*     */     
/* 186 */     mapTypes((EntityType[])Entity1_17Types.values(), Entity1_16_2Types.class);
/* 187 */     filter().type((EntityType)Entity1_17Types.AXOLOTL).cancel(17);
/* 188 */     filter().type((EntityType)Entity1_17Types.AXOLOTL).cancel(18);
/* 189 */     filter().type((EntityType)Entity1_17Types.AXOLOTL).cancel(19);
/*     */     
/* 191 */     filter().type((EntityType)Entity1_17Types.GLOW_SQUID).cancel(16);
/*     */     
/* 193 */     filter().type((EntityType)Entity1_17Types.GOAT).cancel(17);
/*     */     
/* 195 */     mapEntityTypeWithData((EntityType)Entity1_17Types.AXOLOTL, (EntityType)Entity1_17Types.TROPICAL_FISH).jsonName();
/* 196 */     mapEntityTypeWithData((EntityType)Entity1_17Types.GOAT, (EntityType)Entity1_17Types.SHEEP).jsonName();
/*     */     
/* 198 */     mapEntityTypeWithData((EntityType)Entity1_17Types.GLOW_SQUID, (EntityType)Entity1_17Types.SQUID).jsonName();
/* 199 */     mapEntityTypeWithData((EntityType)Entity1_17Types.GLOW_ITEM_FRAME, (EntityType)Entity1_17Types.ITEM_FRAME);
/*     */     
/* 201 */     filter().type((EntityType)Entity1_17Types.SHULKER).addIndex(17);
/*     */     
/* 203 */     filter().removeIndex(7);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int typeId) {
/* 208 */     return Entity1_17Types.getTypeFromId(typeId);
/*     */   }
/*     */   
/*     */   private void reduceExtendedHeight(CompoundTag tag, boolean warn) {
/* 212 */     IntTag minY = (IntTag)tag.get("min_y");
/* 213 */     IntTag height = (IntTag)tag.get("height");
/* 214 */     IntTag logicalHeight = (IntTag)tag.get("logical_height");
/* 215 */     if (minY.asInt() != 0 || height.asInt() > 256 || logicalHeight.asInt() > 256) {
/* 216 */       if (warn && !this.warned) {
/* 217 */         ViaBackwards.getPlatform().getLogger().warning("Custom worlds heights are NOT SUPPORTED for 1.16 players and older and may lead to errors!");
/* 218 */         ViaBackwards.getPlatform().getLogger().warning("You have min/max set to " + minY.asInt() + "/" + height.asInt());
/* 219 */         this.warned = true;
/*     */       } 
/*     */       
/* 222 */       height.setValue(Math.min(256, height.asInt()));
/* 223 */       logicalHeight.setValue(Math.min(256, logicalHeight.asInt()));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_16_4to1_17\packets\EntityPackets1_17.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
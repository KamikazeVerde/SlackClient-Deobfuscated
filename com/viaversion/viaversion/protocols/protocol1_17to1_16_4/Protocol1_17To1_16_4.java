/*     */ package com.viaversion.viaversion.protocols.protocol1_17to1_16_4;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.MappingDataBase;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.RegistryType;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_17;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.EntityPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.InventoryPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.WorldPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.storage.InventoryAcknowledgements;
/*     */ import com.viaversion.viaversion.rewriter.SoundRewriter;
/*     */ import com.viaversion.viaversion.rewriter.StatisticsRewriter;
/*     */ import com.viaversion.viaversion.rewriter.TagRewriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Protocol1_17To1_16_4
/*     */   extends AbstractProtocol<ClientboundPackets1_16_2, ClientboundPackets1_17, ServerboundPackets1_16_2, ServerboundPackets1_17>
/*     */ {
/*  45 */   public static final MappingData MAPPINGS = (MappingData)new MappingDataBase("1.16.2", "1.17");
/*  46 */   private static final String[] NEW_GAME_EVENT_TAGS = new String[] { "minecraft:ignore_vibrations_sneaking", "minecraft:vibrations" };
/*  47 */   private final EntityPackets entityRewriter = new EntityPackets(this);
/*  48 */   private final InventoryPackets itemRewriter = new InventoryPackets(this);
/*  49 */   private final TagRewriter<ClientboundPackets1_16_2> tagRewriter = new TagRewriter((Protocol)this);
/*     */   
/*     */   public Protocol1_17To1_16_4() {
/*  52 */     super(ClientboundPackets1_16_2.class, ClientboundPackets1_17.class, ServerboundPackets1_16_2.class, ServerboundPackets1_17.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  57 */     this.entityRewriter.register();
/*  58 */     this.itemRewriter.register();
/*     */     
/*  60 */     WorldPackets.register(this);
/*     */     
/*  62 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.TAGS, wrapper -> {
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(5));
/*     */ 
/*     */           
/*     */           for (RegistryType type : RegistryType.getValues()) {
/*     */             wrapper.write(Type.STRING, type.resourceLocation());
/*     */             
/*     */             this.tagRewriter.handle(wrapper, this.tagRewriter.getRewriter(type), this.tagRewriter.getNewTags(type));
/*     */             
/*     */             if (type == RegistryType.ENTITY) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */           
/*     */           wrapper.write(Type.STRING, RegistryType.GAME_EVENT.resourceLocation());
/*     */           
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(NEW_GAME_EVENT_TAGS.length));
/*     */           
/*     */           for (String tag : NEW_GAME_EVENT_TAGS) {
/*     */             wrapper.write(Type.STRING, tag);
/*     */             
/*     */             wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[0]);
/*     */           } 
/*     */         });
/*     */     
/*  87 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_16_2.STATISTICS);
/*     */     
/*  89 */     SoundRewriter<ClientboundPackets1_16_2> soundRewriter = new SoundRewriter((Protocol)this);
/*  90 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_16_2.SOUND);
/*  91 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_16_2.ENTITY_SOUND);
/*     */     
/*  93 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.RESOURCE_PACK, wrapper -> {
/*     */           wrapper.passthrough(Type.STRING);
/*     */           
/*     */           wrapper.passthrough(Type.STRING);
/*     */           wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(Via.getConfig().isForcedUse1_17ResourcePack()));
/*     */           wrapper.write(Type.OPTIONAL_COMPONENT, Via.getConfig().get1_17ResourcePackPrompt());
/*     */         });
/* 100 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.MAP_DATA, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */           
/*     */           wrapper.passthrough((Type)Type.BYTE);
/*     */           
/*     */           wrapper.read((Type)Type.BOOLEAN);
/*     */           
/*     */           wrapper.passthrough((Type)Type.BOOLEAN);
/*     */           int size = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           if (size != 0) {
/*     */             wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(size));
/*     */           } else {
/*     */             wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */           } 
/*     */         });
/* 116 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.TITLE, null, wrapper -> {
/*     */           ClientboundPacketType packetType;
/*     */           int type = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           switch (type) {
/*     */             case 0:
/*     */               packetType = ClientboundPackets1_17.TITLE_TEXT;
/*     */               break;
/*     */             
/*     */             case 1:
/*     */               packetType = ClientboundPackets1_17.TITLE_SUBTITLE;
/*     */               break;
/*     */             
/*     */             case 2:
/*     */               packetType = ClientboundPackets1_17.ACTIONBAR;
/*     */               break;
/*     */             
/*     */             case 3:
/*     */               packetType = ClientboundPackets1_17.TITLE_TIMES;
/*     */               break;
/*     */             case 4:
/*     */               packetType = ClientboundPackets1_17.CLEAR_TITLES;
/*     */               wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */               break;
/*     */             case 5:
/*     */               packetType = ClientboundPackets1_17.CLEAR_TITLES;
/*     */               wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */               break;
/*     */             default:
/*     */               throw new IllegalArgumentException("Invalid title type received: " + type);
/*     */           } 
/*     */           wrapper.setPacketType((PacketType)packetType);
/*     */         });
/* 148 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.EXPLOSION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 151 */             map((Type)Type.FLOAT);
/* 152 */             map((Type)Type.FLOAT);
/* 153 */             map((Type)Type.FLOAT);
/* 154 */             map((Type)Type.FLOAT);
/* 155 */             handler(wrapper -> wrapper.write((Type)Type.VAR_INT, wrapper.read((Type)Type.INT)));
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.SPAWN_POSITION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 165 */             map(Type.POSITION1_14);
/* 166 */             handler(wrapper -> wrapper.write((Type)Type.FLOAT, Float.valueOf(0.0F)));
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     registerServerbound(ServerboundPackets1_17.CLIENT_SETTINGS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 176 */             map(Type.STRING);
/* 177 */             map((Type)Type.BYTE);
/* 178 */             map((Type)Type.VAR_INT);
/* 179 */             map((Type)Type.BOOLEAN);
/* 180 */             map((Type)Type.UNSIGNED_BYTE);
/* 181 */             map((Type)Type.VAR_INT);
/* 182 */             handler(wrapper -> wrapper.read((Type)Type.BOOLEAN));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMappingDataLoaded() {
/* 191 */     this.tagRewriter.loadFromMappingData();
/*     */     
/* 193 */     this.tagRewriter.addEmptyTags(RegistryType.ITEM, new String[] { "minecraft:candles", "minecraft:ignored_by_piglin_babies", "minecraft:piglin_food", "minecraft:freeze_immune_wearables", "minecraft:axolotl_tempt_items", "minecraft:occludes_vibration_signals", "minecraft:fox_food", "minecraft:diamond_ores", "minecraft:iron_ores", "minecraft:lapis_ores", "minecraft:redstone_ores", "minecraft:coal_ores", "minecraft:copper_ores", "minecraft:emerald_ores", "minecraft:cluster_max_harvestables" });
/*     */ 
/*     */ 
/*     */     
/* 197 */     this.tagRewriter.addEmptyTags(RegistryType.BLOCK, new String[] { "minecraft:crystal_sound_blocks", "minecraft:candle_cakes", "minecraft:candles", "minecraft:snow_step_sound_blocks", "minecraft:inside_step_sound_blocks", "minecraft:occludes_vibration_signals", "minecraft:dripstone_replaceable_blocks", "minecraft:cave_vines", "minecraft:moss_replaceable", "minecraft:deepslate_ore_replaceables", "minecraft:lush_ground_replaceable", "minecraft:diamond_ores", "minecraft:iron_ores", "minecraft:lapis_ores", "minecraft:redstone_ores", "minecraft:stone_ore_replaceables", "minecraft:coal_ores", "minecraft:copper_ores", "minecraft:emerald_ores", "minecraft:snow", "minecraft:small_dripleaf_placeable", "minecraft:features_cannot_replace", "minecraft:lava_pool_stone_replaceables", "minecraft:geode_invalid_blocks" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     this.tagRewriter.addEmptyTags(RegistryType.ENTITY, new String[] { "minecraft:powder_snow_walkable_mobs", "minecraft:axolotl_always_hostiles", "minecraft:axolotl_tempted_hostiles", "minecraft:axolotl_hunt_targets", "minecraft:freeze_hurts_extra_types", "minecraft:freeze_immune_entity_types" });
/*     */ 
/*     */     
/* 206 */     Types1_17.PARTICLE.filler((Protocol)this)
/* 207 */       .reader("block", ParticleType.Readers.BLOCK)
/* 208 */       .reader("dust", ParticleType.Readers.DUST)
/* 209 */       .reader("falling_dust", ParticleType.Readers.BLOCK)
/* 210 */       .reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION)
/* 211 */       .reader("item", ParticleType.Readers.VAR_INT_ITEM)
/* 212 */       .reader("vibration", ParticleType.Readers.VIBRATION);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection user) {
/* 217 */     addEntityTracker(user, (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_17Types.PLAYER));
/* 218 */     user.put((StorableObject)new InventoryAcknowledgements());
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingData getMappingData() {
/* 223 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets getEntityRewriter() {
/* 228 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryPackets getItemRewriter() {
/* 233 */     return this.itemRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_17to1_16_4\Protocol1_17To1_16_4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
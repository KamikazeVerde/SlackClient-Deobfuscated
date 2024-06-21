/*     */ package com.viaversion.viaversion.protocols.protocol1_18to1_17_1;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.RegistryType;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_18;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.data.MappingData;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets.EntityPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets.InventoryPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets.WorldPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.storage.ChunkLightStorage;
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
/*     */ public final class Protocol1_18To1_17_1
/*     */   extends AbstractProtocol<ClientboundPackets1_17_1, ClientboundPackets1_18, ServerboundPackets1_17, ServerboundPackets1_17>
/*     */ {
/*  42 */   public static final MappingData MAPPINGS = new MappingData();
/*  43 */   private final EntityPackets entityRewriter = new EntityPackets(this);
/*  44 */   private final InventoryPackets itemRewriter = new InventoryPackets(this);
/*     */   
/*     */   public Protocol1_18To1_17_1() {
/*  47 */     super(ClientboundPackets1_17_1.class, ClientboundPackets1_18.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  52 */     this.entityRewriter.register();
/*  53 */     this.itemRewriter.register();
/*  54 */     WorldPackets.register(this);
/*     */     
/*  56 */     SoundRewriter<ClientboundPackets1_17_1> soundRewriter = new SoundRewriter((Protocol)this);
/*  57 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_17_1.SOUND);
/*  58 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_17_1.ENTITY_SOUND);
/*     */     
/*  60 */     TagRewriter<ClientboundPackets1_17_1> tagRewriter = new TagRewriter((Protocol)this);
/*  61 */     tagRewriter.registerGeneric((ClientboundPacketType)ClientboundPackets1_17_1.TAGS);
/*  62 */     tagRewriter.addEmptyTags(RegistryType.BLOCK, new String[] { "minecraft:lava_pool_stone_cannot_replace", "minecraft:big_dripleaf_placeable", "minecraft:wolves_spawnable_on", "minecraft:rabbits_spawnable_on", "minecraft:polar_bears_spawnable_on_in_frozen_ocean", "minecraft:parrots_spawnable_on", "minecraft:mooshrooms_spawnable_on", "minecraft:goats_spawnable_on", "minecraft:foxes_spawnable_on", "minecraft:axolotls_spawnable_on", "minecraft:animals_spawnable_on", "minecraft:azalea_grows_on", "minecraft:azalea_root_replaceable", "minecraft:replaceable_plants", "minecraft:terracotta" });
/*     */ 
/*     */ 
/*     */     
/*  66 */     tagRewriter.addEmptyTags(RegistryType.ITEM, new String[] { "minecraft:dirt", "minecraft:terracotta" });
/*     */     
/*  68 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_17_1.STATISTICS);
/*     */     
/*  70 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_17.CLIENT_SETTINGS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  73 */             map(Type.STRING);
/*  74 */             map((Type)Type.BYTE);
/*  75 */             map((Type)Type.VAR_INT);
/*  76 */             map((Type)Type.BOOLEAN);
/*  77 */             map((Type)Type.UNSIGNED_BYTE);
/*  78 */             map((Type)Type.VAR_INT);
/*  79 */             map((Type)Type.BOOLEAN);
/*  80 */             read((Type)Type.BOOLEAN);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onMappingDataLoaded() {
/*  87 */     Types1_18.PARTICLE.filler((Protocol)this)
/*  88 */       .reader("block", ParticleType.Readers.BLOCK)
/*  89 */       .reader("block_marker", ParticleType.Readers.BLOCK)
/*  90 */       .reader("dust", ParticleType.Readers.DUST)
/*  91 */       .reader("falling_dust", ParticleType.Readers.BLOCK)
/*  92 */       .reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION)
/*  93 */       .reader("item", ParticleType.Readers.VAR_INT_ITEM)
/*  94 */       .reader("vibration", ParticleType.Readers.VIBRATION);
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingData getMappingData() {
/*  99 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection connection) {
/* 104 */     addEntityTracker(connection, (EntityTracker)new EntityTrackerBase(connection, (EntityType)Entity1_17Types.PLAYER));
/* 105 */     connection.put((StorableObject)new ChunkLightStorage());
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets getEntityRewriter() {
/* 110 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryPackets getItemRewriter() {
/* 115 */     return this.itemRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_18to1_17_1\Protocol1_18To1_17_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
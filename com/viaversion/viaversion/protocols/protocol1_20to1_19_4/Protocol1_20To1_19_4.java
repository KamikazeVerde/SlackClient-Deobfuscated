/*     */ package com.viaversion.viaversion.protocols.protocol1_20to1_19_4;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.MappingDataBase;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_4Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_20;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ServerboundPackets1_19_4;
/*     */ import com.viaversion.viaversion.protocols.protocol1_20to1_19_4.packets.EntityPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_20to1_19_4.packets.InventoryPackets;
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
/*     */ 
/*     */ 
/*     */ public final class Protocol1_20To1_19_4
/*     */   extends AbstractProtocol<ClientboundPackets1_19_4, ClientboundPackets1_19_4, ServerboundPackets1_19_4, ServerboundPackets1_19_4>
/*     */ {
/*  39 */   public static final MappingData MAPPINGS = (MappingData)new MappingDataBase("1.19.4", "1.20");
/*  40 */   private final EntityPackets entityRewriter = new EntityPackets(this);
/*  41 */   private final InventoryPackets itemRewriter = new InventoryPackets(this);
/*     */   
/*     */   public Protocol1_20To1_19_4() {
/*  44 */     super(ClientboundPackets1_19_4.class, ClientboundPackets1_19_4.class, ServerboundPackets1_19_4.class, ServerboundPackets1_19_4.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  49 */     super.registerPackets();
/*     */     
/*  51 */     TagRewriter<ClientboundPackets1_19_4> tagRewriter = new TagRewriter((Protocol)this);
/*  52 */     tagRewriter.registerGeneric((ClientboundPacketType)ClientboundPackets1_19_4.TAGS);
/*     */     
/*  54 */     SoundRewriter<ClientboundPackets1_19_4> soundRewriter = new SoundRewriter((Protocol)this);
/*  55 */     soundRewriter.register1_19_3Sound((ClientboundPacketType)ClientboundPackets1_19_4.SOUND);
/*  56 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_19_4.ENTITY_SOUND);
/*     */     
/*  58 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_19_4.STATISTICS);
/*     */     
/*  60 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.COMBAT_END, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */           wrapper.read((Type)Type.INT);
/*     */         });
/*  64 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.COMBAT_KILL, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */           wrapper.read((Type)Type.INT);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onMappingDataLoaded() {
/*  72 */     super.onMappingDataLoaded();
/*  73 */     Types1_20.PARTICLE.filler((Protocol)this)
/*  74 */       .reader("block", ParticleType.Readers.BLOCK)
/*  75 */       .reader("block_marker", ParticleType.Readers.BLOCK)
/*  76 */       .reader("dust", ParticleType.Readers.DUST)
/*  77 */       .reader("falling_dust", ParticleType.Readers.BLOCK)
/*  78 */       .reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION)
/*  79 */       .reader("item", ParticleType.Readers.VAR_INT_ITEM)
/*  80 */       .reader("vibration", ParticleType.Readers.VIBRATION)
/*  81 */       .reader("sculk_charge", ParticleType.Readers.SCULK_CHARGE)
/*  82 */       .reader("shriek", ParticleType.Readers.SHRIEK);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection user) {
/*  87 */     addEntityTracker(user, (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_19_4Types.PLAYER));
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingData getMappingData() {
/*  92 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets getEntityRewriter() {
/*  97 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryPackets getItemRewriter() {
/* 102 */     return this.itemRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_20to1_19_4\Protocol1_20To1_19_4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
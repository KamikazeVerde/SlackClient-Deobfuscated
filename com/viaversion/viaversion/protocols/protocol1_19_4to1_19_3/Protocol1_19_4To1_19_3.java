/*     */ package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
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
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ServerboundPackets1_19_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.data.MappingData;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.packets.EntityPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.packets.InventoryPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.storage.PlayerVehicleTracker;
/*     */ import com.viaversion.viaversion.rewriter.CommandRewriter;
/*     */ import com.viaversion.viaversion.rewriter.SoundRewriter;
/*     */ import com.viaversion.viaversion.rewriter.StatisticsRewriter;
/*     */ import com.viaversion.viaversion.rewriter.TagRewriter;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Base64;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Protocol1_19_4To1_19_3
/*     */   extends AbstractProtocol<ClientboundPackets1_19_3, ClientboundPackets1_19_4, ServerboundPackets1_19_3, ServerboundPackets1_19_4>
/*     */ {
/*  45 */   public static final MappingData MAPPINGS = new MappingData();
/*  46 */   private final EntityPackets entityRewriter = new EntityPackets(this);
/*  47 */   private final InventoryPackets itemRewriter = new InventoryPackets(this);
/*     */   
/*     */   public Protocol1_19_4To1_19_3() {
/*  50 */     super(ClientboundPackets1_19_3.class, ClientboundPackets1_19_4.class, ServerboundPackets1_19_3.class, ServerboundPackets1_19_4.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  55 */     super.registerPackets();
/*     */     
/*  57 */     (new TagRewriter((Protocol)this)).registerGeneric((ClientboundPacketType)ClientboundPackets1_19_3.TAGS);
/*  58 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_19_3.STATISTICS);
/*     */     
/*  60 */     SoundRewriter<ClientboundPackets1_19_3> soundRewriter = new SoundRewriter((Protocol)this);
/*  61 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_19_3.ENTITY_SOUND);
/*  62 */     soundRewriter.register1_19_3Sound((ClientboundPacketType)ClientboundPackets1_19_3.SOUND);
/*     */     
/*  64 */     (new CommandRewriter<ClientboundPackets1_19_3>((Protocol)this)
/*     */       {
/*     */         public void handleArgument(PacketWrapper wrapper, String argumentType) throws Exception {
/*  67 */           if (argumentType.equals("minecraft:time")) {
/*     */             
/*  69 */             wrapper.write((Type)Type.INT, Integer.valueOf(0));
/*     */           } else {
/*  71 */             super.handleArgument(wrapper, argumentType);
/*     */           } 
/*     */         }
/*  74 */       }).registerDeclareCommands1_19((ClientboundPacketType)ClientboundPackets1_19_3.DECLARE_COMMANDS);
/*     */     
/*  76 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.SERVER_DATA, wrapper -> {
/*     */           JsonElement element = (JsonElement)wrapper.read(Type.OPTIONAL_COMPONENT);
/*     */           if (element != null) {
/*     */             wrapper.write(Type.COMPONENT, element);
/*     */           } else {
/*     */             wrapper.write(Type.COMPONENT, ChatRewriter.emptyComponent());
/*     */           } 
/*     */           String iconBase64 = (String)wrapper.read(Type.OPTIONAL_STRING);
/*     */           byte[] iconBytes = null;
/*     */           if (iconBase64 != null && iconBase64.startsWith("data:image/png;base64,")) {
/*     */             iconBytes = Base64.getDecoder().decode(iconBase64.substring("data:image/png;base64,".length()).getBytes(StandardCharsets.UTF_8));
/*     */           }
/*     */           wrapper.write(Type.OPTIONAL_BYTE_ARRAY_PRIMITIVE, iconBytes);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMappingDataLoaded() {
/*  95 */     super.onMappingDataLoaded();
/*  96 */     Entity1_19_4Types.initialize((Protocol)this);
/*  97 */     Types1_19_4.PARTICLE.filler((Protocol)this)
/*  98 */       .reader("block", ParticleType.Readers.BLOCK)
/*  99 */       .reader("block_marker", ParticleType.Readers.BLOCK)
/* 100 */       .reader("dust", ParticleType.Readers.DUST)
/* 101 */       .reader("falling_dust", ParticleType.Readers.BLOCK)
/* 102 */       .reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION)
/* 103 */       .reader("item", ParticleType.Readers.VAR_INT_ITEM)
/* 104 */       .reader("vibration", ParticleType.Readers.VIBRATION)
/* 105 */       .reader("sculk_charge", ParticleType.Readers.SCULK_CHARGE)
/* 106 */       .reader("shriek", ParticleType.Readers.SHRIEK);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection user) {
/* 111 */     addEntityTracker(user, (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_19_4Types.PLAYER));
/*     */     
/* 113 */     user.put((StorableObject)new PlayerVehicleTracker(user));
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingData getMappingData() {
/* 118 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets getEntityRewriter() {
/* 123 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryPackets getItemRewriter() {
/* 128 */     return this.itemRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19_4to1_19_3\Protocol1_19_4To1_19_3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
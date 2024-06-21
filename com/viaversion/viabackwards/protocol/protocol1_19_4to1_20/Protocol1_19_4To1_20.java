/*     */ package com.viaversion.viabackwards.protocol.protocol1_19_4to1_20;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*     */ import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
/*     */ import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.packets.BlockItemPackets1_20;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.packets.EntityPackets1_20;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_4Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ServerboundPackets1_19_4;
/*     */ import com.viaversion.viaversion.protocols.protocol1_20to1_19_4.Protocol1_20To1_19_4;
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
/*     */ public final class Protocol1_19_4To1_20
/*     */   extends BackwardsProtocol<ClientboundPackets1_19_4, ClientboundPackets1_19_4, ServerboundPackets1_19_4, ServerboundPackets1_19_4>
/*     */ {
/*  38 */   public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.20", "1.19.4", Protocol1_20To1_19_4.class);
/*  39 */   private final TranslatableRewriter<ClientboundPackets1_19_4> translatableRewriter = new TranslatableRewriter(this);
/*  40 */   private final EntityPackets1_20 entityRewriter = new EntityPackets1_20(this);
/*  41 */   private final BlockItemPackets1_20 itemRewriter = new BlockItemPackets1_20(this);
/*     */   
/*     */   public Protocol1_19_4To1_20() {
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
/*  54 */     SoundRewriter<ClientboundPackets1_19_4> soundRewriter = new SoundRewriter(this);
/*  55 */     soundRewriter.registerStopSound((ClientboundPacketType)ClientboundPackets1_19_4.STOP_SOUND);
/*  56 */     soundRewriter.register1_19_3Sound((ClientboundPacketType)ClientboundPackets1_19_4.SOUND);
/*  57 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_19_4.ENTITY_SOUND);
/*     */     
/*  59 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_19_4.STATISTICS);
/*     */ 
/*     */     
/*  62 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19_4.ACTIONBAR);
/*  63 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19_4.TITLE_TEXT);
/*  64 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19_4.TITLE_SUBTITLE);
/*  65 */     this.translatableRewriter.registerBossBar((ClientboundPacketType)ClientboundPackets1_19_4.BOSSBAR);
/*  66 */     this.translatableRewriter.registerDisconnect((ClientboundPacketType)ClientboundPackets1_19_4.DISCONNECT);
/*  67 */     this.translatableRewriter.registerTabList((ClientboundPacketType)ClientboundPackets1_19_4.TAB_LIST);
/*  68 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19_4.SYSTEM_CHAT);
/*  69 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19_4.DISGUISED_CHAT);
/*  70 */     this.translatableRewriter.registerPing();
/*     */     
/*  72 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.COMBAT_END, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */           wrapper.write((Type)Type.INT, Integer.valueOf(-1));
/*     */         });
/*  76 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.COMBAT_KILL, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */           wrapper.write((Type)Type.INT, Integer.valueOf(-1));
/*     */           this.translatableRewriter.processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection user) {
/*  85 */     addEntityTracker(user, (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_19_4Types.PLAYER));
/*     */   }
/*     */ 
/*     */   
/*     */   public BackwardsMappings getMappingData() {
/*  90 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets1_20 getEntityRewriter() {
/*  95 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockItemPackets1_20 getItemRewriter() {
/* 100 */     return this.itemRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public TranslatableRewriter<ClientboundPackets1_19_4> getTranslatableRewriter() {
/* 105 */     return this.translatableRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_19_4to1_20\Protocol1_19_4To1_20.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
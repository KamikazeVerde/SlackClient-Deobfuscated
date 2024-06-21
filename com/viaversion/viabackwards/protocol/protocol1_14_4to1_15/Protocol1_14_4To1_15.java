/*     */ package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*     */ import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
/*     */ import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data.ImmediateRespawn;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.BlockItemPackets1_15;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.EntityPackets1_15;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.RegistryType;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
/*     */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
/*     */ import com.viaversion.viaversion.rewriter.StatisticsRewriter;
/*     */ import com.viaversion.viaversion.rewriter.TagRewriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Protocol1_14_4To1_15
/*     */   extends BackwardsProtocol<ClientboundPackets1_15, ClientboundPackets1_14, ServerboundPackets1_14, ServerboundPackets1_14>
/*     */ {
/*  43 */   public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.15", "1.14", Protocol1_15To1_14_4.class);
/*  44 */   private final EntityPackets1_15 entityRewriter = new EntityPackets1_15(this);
/*  45 */   private final BlockItemPackets1_15 blockItemPackets = new BlockItemPackets1_15(this);
/*  46 */   private final TranslatableRewriter<ClientboundPackets1_15> translatableRewriter = new TranslatableRewriter(this);
/*     */   
/*     */   public Protocol1_14_4To1_15() {
/*  49 */     super(ClientboundPackets1_15.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  54 */     super.registerPackets();
/*     */     
/*  56 */     this.translatableRewriter.registerBossBar((ClientboundPacketType)ClientboundPackets1_15.BOSSBAR);
/*  57 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_15.CHAT_MESSAGE);
/*  58 */     this.translatableRewriter.registerCombatEvent((ClientboundPacketType)ClientboundPackets1_15.COMBAT_EVENT);
/*  59 */     this.translatableRewriter.registerDisconnect((ClientboundPacketType)ClientboundPackets1_15.DISCONNECT);
/*  60 */     this.translatableRewriter.registerOpenWindow((ClientboundPacketType)ClientboundPackets1_15.OPEN_WINDOW);
/*  61 */     this.translatableRewriter.registerTabList((ClientboundPacketType)ClientboundPackets1_15.TAB_LIST);
/*  62 */     this.translatableRewriter.registerTitle((ClientboundPacketType)ClientboundPackets1_15.TITLE);
/*  63 */     this.translatableRewriter.registerPing();
/*     */     
/*  65 */     SoundRewriter<ClientboundPackets1_15> soundRewriter = new SoundRewriter(this);
/*  66 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_15.SOUND);
/*  67 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_15.ENTITY_SOUND);
/*  68 */     soundRewriter.registerNamedSound((ClientboundPacketType)ClientboundPackets1_15.NAMED_SOUND);
/*  69 */     soundRewriter.registerStopSound((ClientboundPacketType)ClientboundPackets1_15.STOP_SOUND);
/*     */ 
/*     */     
/*  72 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_15.EXPLOSION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  75 */             map((Type)Type.FLOAT);
/*  76 */             map((Type)Type.FLOAT);
/*  77 */             map((Type)Type.FLOAT);
/*  78 */             handler(wrapper -> {
/*     */                   PacketWrapper soundPacket = wrapper.create((PacketType)ClientboundPackets1_14.SOUND);
/*     */                   soundPacket.write((Type)Type.VAR_INT, Integer.valueOf(243));
/*     */                   soundPacket.write((Type)Type.VAR_INT, Integer.valueOf(4));
/*     */                   soundPacket.write((Type)Type.INT, Integer.valueOf(toEffectCoordinate(((Float)wrapper.get((Type)Type.FLOAT, 0)).floatValue())));
/*     */                   soundPacket.write((Type)Type.INT, Integer.valueOf(toEffectCoordinate(((Float)wrapper.get((Type)Type.FLOAT, 1)).floatValue())));
/*     */                   soundPacket.write((Type)Type.INT, Integer.valueOf(toEffectCoordinate(((Float)wrapper.get((Type)Type.FLOAT, 2)).floatValue())));
/*     */                   soundPacket.write((Type)Type.FLOAT, Float.valueOf(4.0F));
/*     */                   soundPacket.write((Type)Type.FLOAT, Float.valueOf(1.0F));
/*     */                   soundPacket.send(Protocol1_14_4To1_15.class);
/*     */                 });
/*     */           }
/*     */           
/*     */           private int toEffectCoordinate(float coordinate) {
/*  92 */             return (int)(coordinate * 8.0F);
/*     */           }
/*     */         });
/*     */     
/*  96 */     (new TagRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_15.TAGS, RegistryType.ENTITY);
/*     */     
/*  98 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_15.STATISTICS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection user) {
/* 103 */     user.put((StorableObject)new ImmediateRespawn());
/* 104 */     user.addEntityTracker(getClass(), (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_15Types.PLAYER));
/*     */   }
/*     */ 
/*     */   
/*     */   public BackwardsMappings getMappingData() {
/* 109 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets1_15 getEntityRewriter() {
/* 114 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockItemPackets1_15 getItemRewriter() {
/* 119 */     return this.blockItemPackets;
/*     */   }
/*     */ 
/*     */   
/*     */   public TranslatableRewriter<ClientboundPackets1_15> getTranslatableRewriter() {
/* 124 */     return this.translatableRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_14_4to1_15\Protocol1_14_4To1_15.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*     */ import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
/*     */ import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.data.BackwardsMappings;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets.BlockItemPackets1_18;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets.EntityPackets1_18;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.RegistryType;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
/*     */ import com.viaversion.viaversion.rewriter.TagRewriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Protocol1_17_1To1_18
/*     */   extends BackwardsProtocol<ClientboundPackets1_18, ClientboundPackets1_17_1, ServerboundPackets1_17, ServerboundPackets1_17>
/*     */ {
/*  40 */   private static final BackwardsMappings MAPPINGS = new BackwardsMappings();
/*  41 */   private final EntityPackets1_18 entityRewriter = new EntityPackets1_18(this);
/*  42 */   private final BlockItemPackets1_18 itemRewriter = new BlockItemPackets1_18(this);
/*  43 */   private final TranslatableRewriter<ClientboundPackets1_18> translatableRewriter = new TranslatableRewriter(this);
/*     */   
/*     */   public Protocol1_17_1To1_18() {
/*  46 */     super(ClientboundPackets1_18.class, ClientboundPackets1_17_1.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  51 */     super.registerPackets();
/*     */     
/*  53 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_18.CHAT_MESSAGE);
/*  54 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_18.ACTIONBAR);
/*  55 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_18.TITLE_TEXT);
/*  56 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_18.TITLE_SUBTITLE);
/*  57 */     this.translatableRewriter.registerBossBar((ClientboundPacketType)ClientboundPackets1_18.BOSSBAR);
/*  58 */     this.translatableRewriter.registerDisconnect((ClientboundPacketType)ClientboundPackets1_18.DISCONNECT);
/*  59 */     this.translatableRewriter.registerTabList((ClientboundPacketType)ClientboundPackets1_18.TAB_LIST);
/*  60 */     this.translatableRewriter.registerOpenWindow((ClientboundPacketType)ClientboundPackets1_18.OPEN_WINDOW);
/*  61 */     this.translatableRewriter.registerCombatKill((ClientboundPacketType)ClientboundPackets1_18.COMBAT_KILL);
/*  62 */     this.translatableRewriter.registerPing();
/*     */     
/*  64 */     SoundRewriter<ClientboundPackets1_18> soundRewriter = new SoundRewriter(this);
/*  65 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_18.SOUND);
/*  66 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_18.ENTITY_SOUND);
/*  67 */     soundRewriter.registerStopSound((ClientboundPacketType)ClientboundPackets1_18.STOP_SOUND);
/*  68 */     soundRewriter.registerNamedSound((ClientboundPacketType)ClientboundPackets1_18.NAMED_SOUND);
/*     */     
/*  70 */     TagRewriter<ClientboundPackets1_18> tagRewriter = new TagRewriter((Protocol)this);
/*  71 */     tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:lava_pool_stone_replaceables");
/*  72 */     tagRewriter.registerGeneric((ClientboundPacketType)ClientboundPackets1_18.TAGS);
/*     */     
/*  74 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_17.CLIENT_SETTINGS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  77 */             map(Type.STRING);
/*  78 */             map((Type)Type.BYTE);
/*  79 */             map((Type)Type.VAR_INT);
/*  80 */             map((Type)Type.BOOLEAN);
/*  81 */             map((Type)Type.UNSIGNED_BYTE);
/*  82 */             map((Type)Type.VAR_INT);
/*  83 */             map((Type)Type.BOOLEAN);
/*  84 */             create((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */           }
/*     */         });
/*     */     
/*  88 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_18.SCOREBOARD_OBJECTIVE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  91 */             map(Type.STRING);
/*  92 */             handler(Protocol1_17_1To1_18.this.cutName(0, 16));
/*     */           }
/*     */         });
/*  95 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_18.DISPLAY_SCOREBOARD, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  98 */             map((Type)Type.BYTE);
/*  99 */             map(Type.STRING);
/* 100 */             handler(Protocol1_17_1To1_18.this.cutName(0, 16));
/*     */           }
/*     */         });
/* 103 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_18.TEAMS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 106 */             map(Type.STRING);
/* 107 */             handler(Protocol1_17_1To1_18.this.cutName(0, 16));
/*     */           }
/*     */         });
/* 110 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_18.UPDATE_SCORE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 113 */             map(Type.STRING);
/* 114 */             map((Type)Type.VAR_INT);
/* 115 */             map(Type.STRING);
/* 116 */             handler(Protocol1_17_1To1_18.this.cutName(0, 40));
/* 117 */             handler(Protocol1_17_1To1_18.this.cutName(1, 16));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private PacketHandler cutName(int index, int maxLength) {
/* 124 */     return wrapper -> {
/*     */         String s = (String)wrapper.get(Type.STRING, index);
/*     */         if (s.length() > maxLength) {
/*     */           wrapper.set(Type.STRING, index, s.substring(0, maxLength));
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection connection) {
/* 134 */     addEntityTracker(connection, (EntityTracker)new EntityTrackerBase(connection, (EntityType)Entity1_17Types.PLAYER));
/*     */   }
/*     */ 
/*     */   
/*     */   public BackwardsMappings getMappingData() {
/* 139 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets1_18 getEntityRewriter() {
/* 144 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockItemPackets1_18 getItemRewriter() {
/* 149 */     return this.itemRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public TranslatableRewriter<ClientboundPackets1_18> getTranslatableRewriter() {
/* 154 */     return this.translatableRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_17_1to1_18\Protocol1_17_1To1_18.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*     */ import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
/*     */ import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.packets.BlockItemPackets1_19_4;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.packets.EntityPackets1_19_4;
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
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ServerboundPackets1_19_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.Protocol1_19_4To1_19_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ServerboundPackets1_19_4;
/*     */ import com.viaversion.viaversion.rewriter.CommandRewriter;
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
/*     */ 
/*     */ public final class Protocol1_19_3To1_19_4
/*     */   extends BackwardsProtocol<ClientboundPackets1_19_4, ClientboundPackets1_19_3, ServerboundPackets1_19_4, ServerboundPackets1_19_3>
/*     */ {
/*  45 */   public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.19.4", "1.19.3", Protocol1_19_4To1_19_3.class);
/*  46 */   private final EntityPackets1_19_4 entityRewriter = new EntityPackets1_19_4(this);
/*  47 */   private final BlockItemPackets1_19_4 itemRewriter = new BlockItemPackets1_19_4(this);
/*  48 */   private final TranslatableRewriter<ClientboundPackets1_19_4> translatableRewriter = new TranslatableRewriter(this);
/*     */   
/*     */   public Protocol1_19_3To1_19_4() {
/*  51 */     super(ClientboundPackets1_19_4.class, ClientboundPackets1_19_3.class, ServerboundPackets1_19_4.class, ServerboundPackets1_19_3.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  56 */     super.registerPackets();
/*     */     
/*  58 */     SoundRewriter<ClientboundPackets1_19_4> soundRewriter = new SoundRewriter(this);
/*  59 */     soundRewriter.registerStopSound((ClientboundPacketType)ClientboundPackets1_19_4.STOP_SOUND);
/*  60 */     soundRewriter.register1_19_3Sound((ClientboundPacketType)ClientboundPackets1_19_4.SOUND);
/*  61 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_19_4.ENTITY_SOUND);
/*     */ 
/*     */     
/*  64 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19_4.ACTIONBAR);
/*  65 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19_4.TITLE_TEXT);
/*  66 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19_4.TITLE_SUBTITLE);
/*  67 */     this.translatableRewriter.registerBossBar((ClientboundPacketType)ClientboundPackets1_19_4.BOSSBAR);
/*  68 */     this.translatableRewriter.registerDisconnect((ClientboundPacketType)ClientboundPackets1_19_4.DISCONNECT);
/*  69 */     this.translatableRewriter.registerTabList((ClientboundPacketType)ClientboundPackets1_19_4.TAB_LIST);
/*  70 */     this.translatableRewriter.registerCombatKill((ClientboundPacketType)ClientboundPackets1_19_4.COMBAT_KILL);
/*  71 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19_4.SYSTEM_CHAT);
/*  72 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19_4.DISGUISED_CHAT);
/*  73 */     this.translatableRewriter.registerPing();
/*     */     
/*  75 */     (new CommandRewriter<ClientboundPackets1_19_4>((Protocol)this) {
/*     */         public void handleArgument(PacketWrapper wrapper, String argumentType) throws Exception {
/*     */           String resource;
/*  78 */           switch (argumentType) {
/*     */             case "minecraft:heightmap":
/*  80 */               wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */               return;
/*     */             case "minecraft:time":
/*  83 */               wrapper.read((Type)Type.INT);
/*     */               return;
/*     */             case "minecraft:resource":
/*     */             case "minecraft:resource_or_tag":
/*  87 */               resource = (String)wrapper.read(Type.STRING);
/*     */               
/*  89 */               wrapper.write(Type.STRING, resource.equals("minecraft:damage_type") ? "minecraft:mob_effect" : resource);
/*     */               return;
/*     */           } 
/*  92 */           super.handleArgument(wrapper, argumentType);
/*     */         }
/*  96 */       }).registerDeclareCommands1_19((ClientboundPacketType)ClientboundPackets1_19_4.DECLARE_COMMANDS);
/*     */     
/*  98 */     TagRewriter<ClientboundPackets1_19_4> tagRewriter = new TagRewriter((Protocol)this);
/*  99 */     tagRewriter.removeTags("minecraft:damage_type");
/* 100 */     tagRewriter.registerGeneric((ClientboundPacketType)ClientboundPackets1_19_4.TAGS);
/*     */     
/* 102 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_19_4.STATISTICS);
/*     */     
/* 104 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.SERVER_DATA, wrapper -> {
/*     */           JsonElement element = (JsonElement)wrapper.read(Type.COMPONENT);
/*     */           
/*     */           wrapper.write(Type.OPTIONAL_COMPONENT, element);
/*     */           
/*     */           byte[] iconBytes = (byte[])wrapper.read(Type.OPTIONAL_BYTE_ARRAY_PRIMITIVE);
/*     */           String iconBase64 = (iconBytes != null) ? ("data:image/png;base64," + new String(Base64.getEncoder().encode(iconBytes), StandardCharsets.UTF_8)) : null;
/*     */           wrapper.write(Type.OPTIONAL_STRING, iconBase64);
/*     */         });
/* 113 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_19_4.BUNDLE);
/* 114 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_19_4.CHUNK_BIOMES);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection user) {
/* 119 */     addEntityTracker(user, (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_19_4Types.PLAYER));
/*     */   }
/*     */ 
/*     */   
/*     */   public BackwardsMappings getMappingData() {
/* 124 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockItemPackets1_19_4 getItemRewriter() {
/* 129 */     return this.itemRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets1_19_4 getEntityRewriter() {
/* 134 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public TranslatableRewriter<ClientboundPackets1_19_4> getTranslatableRewriter() {
/* 139 */     return this.translatableRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_19_3to1_19_4\Protocol1_19_3To1_19_4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19;
/*     */ 
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*     */ import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
/*     */ import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.data.BackwardsMappings;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.data.CommandRewriter1_19;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets.BlockItemPackets1_19;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets.EntityPackets1_19;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage.DimensionRegistryStorage;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.RegistryType;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_19Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.State;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
/*     */ import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ChatDecorationResult;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.Protocol1_19_1To1_19;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ServerboundPackets1_19;
/*     */ import com.viaversion.viaversion.rewriter.CommandRewriter;
/*     */ import com.viaversion.viaversion.rewriter.StatisticsRewriter;
/*     */ import com.viaversion.viaversion.rewriter.TagRewriter;
/*     */ import java.time.Instant;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Protocol1_18_2To1_19
/*     */   extends BackwardsProtocol<ClientboundPackets1_19, ClientboundPackets1_18, ServerboundPackets1_19, ServerboundPackets1_17>
/*     */ {
/*  55 */   public static final BackwardsMappings MAPPINGS = new BackwardsMappings();
/*  56 */   private static final UUID ZERO_UUID = new UUID(0L, 0L);
/*  57 */   private static final byte[] EMPTY_BYTES = new byte[0];
/*  58 */   private final EntityPackets1_19 entityRewriter = new EntityPackets1_19(this);
/*  59 */   private final BlockItemPackets1_19 blockItemPackets = new BlockItemPackets1_19(this);
/*  60 */   private final TranslatableRewriter<ClientboundPackets1_19> translatableRewriter = new TranslatableRewriter(this);
/*     */   
/*     */   public Protocol1_18_2To1_19() {
/*  63 */     super(ClientboundPackets1_19.class, ClientboundPackets1_18.class, ServerboundPackets1_19.class, ServerboundPackets1_17.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  68 */     super.registerPackets();
/*     */     
/*  70 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19.ACTIONBAR);
/*  71 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19.TITLE_TEXT);
/*  72 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19.TITLE_SUBTITLE);
/*  73 */     this.translatableRewriter.registerBossBar((ClientboundPacketType)ClientboundPackets1_19.BOSSBAR);
/*  74 */     this.translatableRewriter.registerDisconnect((ClientboundPacketType)ClientboundPackets1_19.DISCONNECT);
/*  75 */     this.translatableRewriter.registerTabList((ClientboundPacketType)ClientboundPackets1_19.TAB_LIST);
/*  76 */     this.translatableRewriter.registerOpenWindow((ClientboundPacketType)ClientboundPackets1_19.OPEN_WINDOW);
/*  77 */     this.translatableRewriter.registerCombatKill((ClientboundPacketType)ClientboundPackets1_19.COMBAT_KILL);
/*  78 */     this.translatableRewriter.registerPing();
/*     */     
/*  80 */     final SoundRewriter<ClientboundPackets1_19> soundRewriter = new SoundRewriter(this);
/*  81 */     soundRewriter.registerStopSound((ClientboundPacketType)ClientboundPackets1_19.STOP_SOUND);
/*  82 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19.SOUND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  85 */             map((Type)Type.VAR_INT);
/*  86 */             map((Type)Type.VAR_INT);
/*  87 */             map((Type)Type.INT);
/*  88 */             map((Type)Type.INT);
/*  89 */             map((Type)Type.INT);
/*  90 */             map((Type)Type.FLOAT);
/*  91 */             map((Type)Type.FLOAT);
/*  92 */             read((Type)Type.LONG);
/*  93 */             handler(soundRewriter.getSoundHandler());
/*     */           }
/*     */         });
/*  96 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19.ENTITY_SOUND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  99 */             map((Type)Type.VAR_INT);
/* 100 */             map((Type)Type.VAR_INT);
/* 101 */             map((Type)Type.VAR_INT);
/* 102 */             map((Type)Type.FLOAT);
/* 103 */             map((Type)Type.FLOAT);
/* 104 */             read((Type)Type.LONG);
/* 105 */             handler(soundRewriter.getSoundHandler());
/*     */           }
/*     */         });
/* 108 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19.NAMED_SOUND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 111 */             map(Type.STRING);
/* 112 */             map((Type)Type.VAR_INT);
/* 113 */             map((Type)Type.INT);
/* 114 */             map((Type)Type.INT);
/* 115 */             map((Type)Type.INT);
/* 116 */             map((Type)Type.FLOAT);
/* 117 */             map((Type)Type.FLOAT);
/* 118 */             read((Type)Type.LONG);
/* 119 */             handler(soundRewriter.getNamedSoundHandler());
/*     */           }
/*     */         });
/*     */     
/* 123 */     TagRewriter<ClientboundPackets1_19> tagRewriter = new TagRewriter((Protocol)this);
/* 124 */     tagRewriter.removeTags("minecraft:banner_pattern");
/* 125 */     tagRewriter.removeTags("minecraft:instrument");
/* 126 */     tagRewriter.removeTags("minecraft:cat_variant");
/* 127 */     tagRewriter.removeTags("minecraft:painting_variant");
/* 128 */     tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:polar_bears_spawnable_on_in_frozen_ocean");
/* 129 */     tagRewriter.renameTag(RegistryType.BLOCK, "minecraft:wool_carpets", "minecraft:carpets");
/* 130 */     tagRewriter.renameTag(RegistryType.ITEM, "minecraft:wool_carpets", "minecraft:carpets");
/* 131 */     tagRewriter.addEmptyTag(RegistryType.ITEM, "minecraft:occludes_vibration_signals");
/* 132 */     tagRewriter.registerGeneric((ClientboundPacketType)ClientboundPackets1_19.TAGS);
/*     */     
/* 134 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_19.STATISTICS);
/*     */     
/* 136 */     CommandRewriter1_19 commandRewriter1_19 = new CommandRewriter1_19(this);
/* 137 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19.DECLARE_COMMANDS, wrapper -> {
/*     */           int size = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           for (int i = 0; i < size; i++) {
/*     */             byte flags = ((Byte)wrapper.passthrough((Type)Type.BYTE)).byteValue();
/*     */             
/*     */             wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
/*     */             
/*     */             if ((flags & 0x8) != 0) {
/*     */               wrapper.passthrough((Type)Type.VAR_INT);
/*     */             }
/*     */             
/*     */             int nodeType = flags & 0x3;
/*     */             
/*     */             if (nodeType == 1 || nodeType == 2) {
/*     */               wrapper.passthrough(Type.STRING);
/*     */             }
/*     */             
/*     */             if (nodeType == 2) {
/*     */               int argumentTypeId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */               String argumentType = MAPPINGS.getArgumentTypeMappings().identifier(argumentTypeId);
/*     */               if (argumentType == null) {
/*     */                 ViaBackwards.getPlatform().getLogger().warning("Unknown command argument type id: " + argumentTypeId);
/*     */                 argumentType = "minecraft:no";
/*     */               } 
/*     */               wrapper.write(Type.STRING, commandRewriter.handleArgumentType(argumentType));
/*     */               commandRewriter.handleArgument(wrapper, argumentType);
/*     */               if ((flags & 0x10) != 0) {
/*     */                 wrapper.passthrough(Type.STRING);
/*     */               }
/*     */             } 
/*     */           } 
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */         });
/* 171 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_19.SERVER_DATA);
/* 172 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_19.CHAT_PREVIEW);
/* 173 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_19.SET_DISPLAY_CHAT_PREVIEW);
/* 174 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19.PLAYER_CHAT, (ClientboundPacketType)ClientboundPackets1_18.CHAT_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 177 */             handler(wrapper -> {
/*     */                   JsonElement signedContent = (JsonElement)wrapper.read(Type.COMPONENT);
/*     */                   
/*     */                   JsonElement unsignedContent = (JsonElement)wrapper.read(Type.OPTIONAL_COMPONENT);
/*     */                   
/*     */                   int chatTypeId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   UUID sender = (UUID)wrapper.read(Type.UUID);
/*     */                   JsonElement senderName = (JsonElement)wrapper.read(Type.COMPONENT);
/*     */                   JsonElement teamName = (JsonElement)wrapper.read(Type.OPTIONAL_COMPONENT);
/*     */                   CompoundTag chatType = ((DimensionRegistryStorage)wrapper.user().get(DimensionRegistryStorage.class)).chatType(chatTypeId);
/*     */                   ChatDecorationResult decorationResult = Protocol1_19_1To1_19.decorateChatMessage(chatType, chatTypeId, senderName, teamName, (unsignedContent != null) ? unsignedContent : signedContent);
/*     */                   if (decorationResult == null) {
/*     */                     wrapper.cancel();
/*     */                     return;
/*     */                   } 
/*     */                   Protocol1_18_2To1_19.this.translatableRewriter.processText(decorationResult.content());
/*     */                   wrapper.write(Type.COMPONENT, decorationResult.content());
/*     */                   wrapper.write((Type)Type.BYTE, Byte.valueOf(decorationResult.overlay() ? 2 : 1));
/*     */                   wrapper.write(Type.UUID, sender);
/*     */                 });
/* 197 */             read((Type)Type.LONG);
/* 198 */             read((Type)Type.LONG);
/* 199 */             read(Type.BYTE_ARRAY_PRIMITIVE);
/*     */           }
/*     */         });
/*     */     
/* 203 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19.SYSTEM_CHAT, (ClientboundPacketType)ClientboundPackets1_18.CHAT_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 206 */             handler(wrapper -> {
/*     */                   JsonElement content = (JsonElement)wrapper.passthrough(Type.COMPONENT);
/*     */                   
/*     */                   Protocol1_18_2To1_19.this.translatableRewriter.processText(content);
/*     */                   
/*     */                   int typeId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   wrapper.write((Type)Type.BYTE, Byte.valueOf((typeId == 2) ? 2 : 0));
/*     */                 });
/* 214 */             create(Type.UUID, Protocol1_18_2To1_19.ZERO_UUID);
/*     */           }
/*     */         });
/*     */     
/* 218 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_17.CHAT_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 221 */             map(Type.STRING);
/* 222 */             handler(wrapper -> wrapper.write((Type)Type.LONG, Long.valueOf(Instant.now().toEpochMilli())));
/* 223 */             create((Type)Type.LONG, Long.valueOf(0L));
/* 224 */             handler(wrapper -> {
/*     */                   String message = (String)wrapper.get(Type.STRING, 0);
/*     */                   
/*     */                   if (!message.isEmpty() && message.charAt(0) == '/') {
/*     */                     wrapper.setPacketType((PacketType)ServerboundPackets1_19.CHAT_COMMAND);
/*     */                     
/*     */                     wrapper.set(Type.STRING, 0, message.substring(1));
/*     */                     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                   } else {
/*     */                     wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, Protocol1_18_2To1_19.EMPTY_BYTES);
/*     */                   } 
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                 });
/*     */           }
/*     */         });
/* 239 */     registerClientbound(State.LOGIN, ClientboundLoginPackets.GAME_PROFILE.getId(), ClientboundLoginPackets.GAME_PROFILE.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 242 */             map(Type.UUID);
/* 243 */             map(Type.STRING);
/* 244 */             handler(wrapper -> {
/*     */                   int properties = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   for (int i = 0; i < properties; i++) {
/*     */                     wrapper.read(Type.STRING);
/*     */                     wrapper.read(Type.STRING);
/*     */                     if (((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue()) {
/*     */                       wrapper.read(Type.STRING);
/*     */                     }
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 257 */     registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO.getId(), ServerboundLoginPackets.HELLO.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 260 */             map(Type.STRING);
/*     */             
/* 262 */             create(Type.OPTIONAL_PROFILE_KEY, null);
/*     */           }
/*     */         });
/*     */     
/* 266 */     registerServerbound(State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY.getId(), ServerboundLoginPackets.ENCRYPTION_KEY.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 269 */             map(Type.BYTE_ARRAY_PRIMITIVE);
/* 270 */             create((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection user) {
/* 277 */     user.put((StorableObject)new DimensionRegistryStorage());
/* 278 */     addEntityTracker(user, (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_19Types.PLAYER));
/*     */   }
/*     */ 
/*     */   
/*     */   public BackwardsMappings getMappingData() {
/* 283 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public TranslatableRewriter<ClientboundPackets1_19> getTranslatableRewriter() {
/* 288 */     return this.translatableRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets1_19 getEntityRewriter() {
/* 293 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockItemPackets1_19 getItemRewriter() {
/* 298 */     return this.blockItemPackets;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_18_2to1_19\Protocol1_18_2To1_19.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
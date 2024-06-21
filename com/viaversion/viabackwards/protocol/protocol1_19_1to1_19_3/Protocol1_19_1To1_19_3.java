/*     */ package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*     */ import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
/*     */ import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.data.BackwardsMappings;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.packets.BlockItemPackets1_19_3;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.packets.EntityPackets1_19_3;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.storage.ChatSessionStorage;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.storage.ChatTypeStorage1_19_3;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.storage.NonceStorage;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.Protocol1_19To1_19_1;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage.ChatRegistryStorage;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.ProfileKey;
/*     */ import com.viaversion.viaversion.api.minecraft.RegistryType;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_3Types;
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
/*     */ import com.viaversion.viaversion.api.type.types.BitSetType;
/*     */ import com.viaversion.viaversion.api.type.types.ByteArrayType;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
/*     */ import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
/*     */ import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ClientboundPackets1_19_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ServerboundPackets1_19_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ServerboundPackets1_19_3;
/*     */ import com.viaversion.viaversion.rewriter.CommandRewriter;
/*     */ import com.viaversion.viaversion.rewriter.StatisticsRewriter;
/*     */ import com.viaversion.viaversion.rewriter.TagRewriter;
/*     */ import com.viaversion.viaversion.util.CipherUtil;
/*     */ import java.util.BitSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Protocol1_19_1To1_19_3
/*     */   extends BackwardsProtocol<ClientboundPackets1_19_3, ClientboundPackets1_19_1, ServerboundPackets1_19_3, ServerboundPackets1_19_1>
/*     */ {
/*  58 */   public static final BackwardsMappings MAPPINGS = new BackwardsMappings();
/*  59 */   public static final ByteArrayType.OptionalByteArrayType OPTIONAL_SIGNATURE_BYTES_TYPE = new ByteArrayType.OptionalByteArrayType(256);
/*  60 */   public static final ByteArrayType SIGNATURE_BYTES_TYPE = new ByteArrayType(256);
/*  61 */   private final EntityPackets1_19_3 entityRewriter = new EntityPackets1_19_3(this);
/*  62 */   private final BlockItemPackets1_19_3 itemRewriter = new BlockItemPackets1_19_3(this);
/*  63 */   private final TranslatableRewriter<ClientboundPackets1_19_3> translatableRewriter = new TranslatableRewriter(this);
/*     */   
/*     */   public Protocol1_19_1To1_19_3() {
/*  66 */     super(ClientboundPackets1_19_3.class, ClientboundPackets1_19_1.class, ServerboundPackets1_19_3.class, ServerboundPackets1_19_1.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  71 */     super.registerPackets();
/*     */     
/*  73 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19_3.SYSTEM_CHAT);
/*  74 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19_3.ACTIONBAR);
/*  75 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19_3.TITLE_TEXT);
/*  76 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19_3.TITLE_SUBTITLE);
/*  77 */     this.translatableRewriter.registerBossBar((ClientboundPacketType)ClientboundPackets1_19_3.BOSSBAR);
/*  78 */     this.translatableRewriter.registerDisconnect((ClientboundPacketType)ClientboundPackets1_19_3.DISCONNECT);
/*  79 */     this.translatableRewriter.registerTabList((ClientboundPacketType)ClientboundPackets1_19_3.TAB_LIST);
/*  80 */     this.translatableRewriter.registerOpenWindow((ClientboundPacketType)ClientboundPackets1_19_3.OPEN_WINDOW);
/*  81 */     this.translatableRewriter.registerCombatKill((ClientboundPacketType)ClientboundPackets1_19_3.COMBAT_KILL);
/*  82 */     this.translatableRewriter.registerPing();
/*     */     
/*  84 */     SoundRewriter<ClientboundPackets1_19_3> soundRewriter = new SoundRewriter(this);
/*  85 */     soundRewriter.registerStopSound((ClientboundPacketType)ClientboundPackets1_19_3.STOP_SOUND);
/*  86 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.SOUND, wrapper -> {
/*     */           int soundId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue() - 1;
/*     */           
/*     */           if (soundId != -1) {
/*     */             int mappedId = MAPPINGS.getSoundMappings().getNewId(soundId);
/*     */             
/*     */             if (mappedId == -1) {
/*     */               wrapper.cancel();
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(mappedId));
/*     */             return;
/*     */           } 
/*     */           String soundIdentifier = (String)wrapper.read(Type.STRING);
/*     */           wrapper.read((Type)Type.OPTIONAL_FLOAT);
/*     */           String mappedIdentifier = MAPPINGS.getMappedNamedSound(soundIdentifier);
/*     */           if (mappedIdentifier != null) {
/*     */             if (mappedIdentifier.isEmpty()) {
/*     */               wrapper.cancel();
/*     */               return;
/*     */             } 
/*     */             soundIdentifier = mappedIdentifier;
/*     */           } 
/*     */           wrapper.write(Type.STRING, soundIdentifier);
/*     */           wrapper.setPacketType((PacketType)ClientboundPackets1_19_1.NAMED_SOUND);
/*     */         });
/* 114 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.ENTITY_SOUND, wrapper -> {
/*     */           int soundId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue() - 1;
/*     */           
/*     */           if (soundId != -1) {
/*     */             int i = MAPPINGS.getSoundMappings().getNewId(soundId);
/*     */             
/*     */             if (i == -1) {
/*     */               wrapper.cancel();
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(i));
/*     */           } 
/*     */           
/*     */           String soundIdentifier = (String)wrapper.read(Type.STRING);
/*     */           
/*     */           wrapper.read((Type)Type.OPTIONAL_FLOAT);
/*     */           
/*     */           String mappedIdentifier = MAPPINGS.getMappedNamedSound(soundIdentifier);
/*     */           if (mappedIdentifier != null) {
/*     */             if (mappedIdentifier.isEmpty()) {
/*     */               wrapper.cancel();
/*     */               return;
/*     */             } 
/*     */             soundIdentifier = mappedIdentifier;
/*     */           } 
/*     */           int mappedId = MAPPINGS.mappedSound(soundIdentifier);
/*     */           if (mappedId == -1) {
/*     */             wrapper.cancel();
/*     */             return;
/*     */           } 
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(mappedId));
/*     */         });
/* 148 */     TagRewriter<ClientboundPackets1_19_3> tagRewriter = new TagRewriter((Protocol)this);
/* 149 */     tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:non_flammable_wood");
/* 150 */     tagRewriter.addEmptyTag(RegistryType.ITEM, "minecraft:overworld_natural_logs");
/* 151 */     tagRewriter.registerGeneric((ClientboundPacketType)ClientboundPackets1_19_3.TAGS);
/*     */     
/* 153 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_19_3.STATISTICS);
/*     */     
/* 155 */     CommandRewriter<ClientboundPackets1_19_3> commandRewriter = new CommandRewriter((Protocol)this);
/* 156 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.DECLARE_COMMANDS, wrapper -> {
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
/*     */               int mappedArgumentTypeId = MAPPINGS.getArgumentTypeMappings().mappings().getNewId(argumentTypeId);
/*     */               Preconditions.checkArgument((mappedArgumentTypeId != -1), "Unknown command argument type id: " + argumentTypeId);
/*     */               wrapper.write((Type)Type.VAR_INT, Integer.valueOf(mappedArgumentTypeId));
/*     */               String identifier = MAPPINGS.getArgumentTypeMappings().identifier(argumentTypeId);
/*     */               commandRewriter.handleArgument(wrapper, identifier);
/*     */               if (identifier.equals("minecraft:gamemode")) {
/*     */                 wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */               }
/*     */               if ((flags & 0x10) != 0) {
/*     */                 wrapper.passthrough(Type.STRING);
/*     */               }
/*     */             } 
/*     */           } 
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */         });
/* 191 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.SERVER_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 194 */             map(Type.OPTIONAL_COMPONENT);
/* 195 */             map(Type.OPTIONAL_STRING);
/* 196 */             create((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 201 */     registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO.getId(), ServerboundLoginPackets.HELLO.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 204 */             map(Type.STRING);
/* 205 */             handler(wrapper -> {
/*     */                   ProfileKey profileKey = (ProfileKey)wrapper.read(Type.OPTIONAL_PROFILE_KEY);
/*     */                   if (profileKey == null) {
/*     */                     wrapper.user().put((StorableObject)new NonceStorage(null));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 213 */     registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO.getId(), ClientboundLoginPackets.HELLO.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 216 */             map(Type.STRING);
/* 217 */             handler(wrapper -> {
/*     */                   if (wrapper.user().has(NonceStorage.class)) {
/*     */                     return;
/*     */                   }
/*     */                   byte[] publicKey = (byte[])wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                   byte[] nonce = (byte[])wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                   wrapper.user().put((StorableObject)new NonceStorage(CipherUtil.encryptNonce(publicKey, nonce)));
/*     */                 });
/*     */           }
/*     */         });
/* 227 */     registerServerbound(State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY.getId(), ServerboundLoginPackets.ENCRYPTION_KEY.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 230 */             map(Type.BYTE_ARRAY_PRIMITIVE);
/* 231 */             handler(wrapper -> {
/*     */                   NonceStorage nonceStorage = (NonceStorage)wrapper.user().remove(NonceStorage.class);
/*     */                   
/*     */                   boolean isNonce = ((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue();
/*     */                   if (!isNonce) {
/*     */                     wrapper.read((Type)Type.LONG);
/*     */                     wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                     wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, (nonceStorage.nonce() != null) ? nonceStorage.nonce() : new byte[0]);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 243 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_19_1.CHAT_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 246 */             map(Type.STRING);
/* 247 */             map((Type)Type.LONG);
/* 248 */             map((Type)Type.LONG);
/* 249 */             read(Type.BYTE_ARRAY_PRIMITIVE);
/* 250 */             create((Type)Protocol1_19_1To1_19_3.OPTIONAL_SIGNATURE_BYTES_TYPE, null);
/* 251 */             read((Type)Type.BOOLEAN);
/* 252 */             read(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY);
/* 253 */             read(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE);
/* 254 */             handler(wrapper -> {
/*     */                   int offset = 0;
/*     */                   
/*     */                   BitSet acknowledged = new BitSet(20);
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                   wrapper.write((Type)new BitSetType(20), acknowledged);
/*     */                 });
/*     */           }
/*     */         });
/* 263 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_19_1.CHAT_COMMAND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 266 */             map(Type.STRING);
/* 267 */             map((Type)Type.LONG);
/* 268 */             map((Type)Type.LONG);
/* 269 */             handler(wrapper -> {
/*     */                   int signatures = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                   for (int i = 0; i < signatures; i++) {
/*     */                     wrapper.read(Type.STRING);
/*     */                     wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                   } 
/*     */                   wrapper.read((Type)Type.BOOLEAN);
/*     */                   int offset = 0;
/*     */                   BitSet acknowledged = new BitSet(20);
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                   wrapper.write((Type)new BitSetType(20), acknowledged);
/*     */                 });
/* 283 */             read(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY);
/* 284 */             read(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE);
/*     */           }
/*     */         });
/* 287 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.PLAYER_CHAT, (ClientboundPacketType)ClientboundPackets1_19_1.SYSTEM_CHAT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 290 */             read(Type.UUID);
/* 291 */             read((Type)Type.VAR_INT);
/* 292 */             read((Type)Protocol1_19_1To1_19_3.OPTIONAL_SIGNATURE_BYTES_TYPE);
/* 293 */             handler(wrapper -> {
/*     */                   String plainContent = (String)wrapper.read(Type.STRING);
/*     */                   
/*     */                   wrapper.read((Type)Type.LONG);
/*     */                   
/*     */                   wrapper.read((Type)Type.LONG);
/*     */                   
/*     */                   int lastSeen = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   for (int i = 0; i < lastSeen; i++) {
/*     */                     int index = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                     if (index == 0) {
/*     */                       wrapper.read((Type)Protocol1_19_1To1_19_3.SIGNATURE_BYTES_TYPE);
/*     */                     }
/*     */                   } 
/*     */                   JsonElement unsignedContent = (JsonElement)wrapper.read(Type.OPTIONAL_COMPONENT);
/*     */                   JsonElement content = (unsignedContent != null) ? unsignedContent : GsonComponentSerializer.gson().serializeToTree((Component)Component.text(plainContent));
/*     */                   Protocol1_19_1To1_19_3.this.translatableRewriter.processText(content);
/*     */                   int filterMaskType = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   if (filterMaskType == 2) {
/*     */                     wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
/*     */                   }
/*     */                   int chatTypeId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   JsonElement senderName = (JsonElement)wrapper.read(Type.COMPONENT);
/*     */                   JsonElement targetName = (JsonElement)wrapper.read(Type.OPTIONAL_COMPONENT);
/*     */                   JsonElement result = Protocol1_19To1_19_1.decorateChatMessage((ChatRegistryStorage)wrapper.user().get(ChatTypeStorage1_19_3.class), chatTypeId, senderName, targetName, content);
/*     */                   if (result == null) {
/*     */                     wrapper.cancel();
/*     */                     return;
/*     */                   } 
/*     */                   wrapper.write(Type.COMPONENT, result);
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                 });
/*     */           }
/*     */         });
/* 327 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.DISGUISED_CHAT, (ClientboundPacketType)ClientboundPackets1_19_1.SYSTEM_CHAT, wrapper -> {
/*     */           JsonElement content = (JsonElement)wrapper.read(Type.COMPONENT);
/*     */           
/*     */           this.translatableRewriter.processText(content);
/*     */           
/*     */           int chatTypeId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           JsonElement senderName = (JsonElement)wrapper.read(Type.COMPONENT);
/*     */           JsonElement targetName = (JsonElement)wrapper.read(Type.OPTIONAL_COMPONENT);
/*     */           JsonElement result = Protocol1_19To1_19_1.decorateChatMessage((ChatRegistryStorage)wrapper.user().get(ChatTypeStorage1_19_3.class), chatTypeId, senderName, targetName, content);
/*     */           if (result == null) {
/*     */             wrapper.cancel();
/*     */             return;
/*     */           } 
/*     */           wrapper.write(Type.COMPONENT, result);
/*     */           wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */         });
/* 343 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_19_3.UPDATE_ENABLED_FEATURES);
/* 344 */     cancelServerbound((ServerboundPacketType)ServerboundPackets1_19_1.CHAT_PREVIEW);
/* 345 */     cancelServerbound((ServerboundPacketType)ServerboundPackets1_19_1.CHAT_ACK);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection user) {
/* 350 */     user.put((StorableObject)new ChatSessionStorage());
/* 351 */     user.put((StorableObject)new ChatTypeStorage1_19_3());
/* 352 */     addEntityTracker(user, (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_19_3Types.PLAYER));
/*     */   }
/*     */ 
/*     */   
/*     */   public BackwardsMappings getMappingData() {
/* 357 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public TranslatableRewriter<ClientboundPackets1_19_3> getTranslatableRewriter() {
/* 362 */     return this.translatableRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockItemPackets1_19_3 getItemRewriter() {
/* 367 */     return this.itemRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets1_19_3 getEntityRewriter() {
/* 372 */     return this.entityRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_19_1to1_19_3\Protocol1_19_1To1_19_3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
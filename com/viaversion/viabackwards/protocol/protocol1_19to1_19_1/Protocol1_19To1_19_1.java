/*     */ package com.viaversion.viabackwards.protocol.protocol1_19to1_19_1;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.packets.EntityPackets1_19_1;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage.ChatRegistryStorage;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage.ChatRegistryStorage1_19_1;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage.NonceStorage;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage.ReceivedMessagesStorage;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
/*     */ import com.viaversion.viaversion.api.minecraft.ProfileKey;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_19Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.State;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.NamedTextColor;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
/*     */ import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ClientboundPackets1_19_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ServerboundPackets1_19_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ServerboundPackets1_19;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.packets.EntityPackets;
/*     */ import com.viaversion.viaversion.util.CipherUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Protocol1_19To1_19_1
/*     */   extends BackwardsProtocol<ClientboundPackets1_19_1, ClientboundPackets1_19, ServerboundPackets1_19_1, ServerboundPackets1_19>
/*     */ {
/*     */   public static final int SYSTEM_CHAT_ID = 1;
/*     */   public static final int GAME_INFO_ID = 2;
/*  68 */   private static final UUID ZERO_UUID = new UUID(0L, 0L);
/*  69 */   private static final byte[] EMPTY_BYTES = new byte[0];
/*  70 */   private final EntityPackets1_19_1 entityRewriter = new EntityPackets1_19_1(this);
/*  71 */   private final TranslatableRewriter<ClientboundPackets1_19_1> translatableRewriter = new TranslatableRewriter(this);
/*     */   
/*     */   public Protocol1_19To1_19_1() {
/*  74 */     super(ClientboundPackets1_19_1.class, ClientboundPackets1_19.class, ServerboundPackets1_19_1.class, ServerboundPackets1_19.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  79 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19_1.ACTIONBAR);
/*  80 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19_1.TITLE_TEXT);
/*  81 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_19_1.TITLE_SUBTITLE);
/*  82 */     this.translatableRewriter.registerBossBar((ClientboundPacketType)ClientboundPackets1_19_1.BOSSBAR);
/*  83 */     this.translatableRewriter.registerDisconnect((ClientboundPacketType)ClientboundPackets1_19_1.DISCONNECT);
/*  84 */     this.translatableRewriter.registerTabList((ClientboundPacketType)ClientboundPackets1_19_1.TAB_LIST);
/*  85 */     this.translatableRewriter.registerOpenWindow((ClientboundPacketType)ClientboundPackets1_19_1.OPEN_WINDOW);
/*  86 */     this.translatableRewriter.registerCombatKill((ClientboundPacketType)ClientboundPackets1_19_1.COMBAT_KILL);
/*  87 */     this.translatableRewriter.registerPing();
/*     */     
/*  89 */     this.entityRewriter.register();
/*     */     
/*  91 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_1.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  94 */             map((Type)Type.INT);
/*  95 */             map((Type)Type.BOOLEAN);
/*  96 */             map((Type)Type.UNSIGNED_BYTE);
/*  97 */             map((Type)Type.BYTE);
/*  98 */             map(Type.STRING_ARRAY);
/*  99 */             map(Type.NBT);
/* 100 */             map(Type.STRING);
/* 101 */             map(Type.STRING);
/* 102 */             handler(wrapper -> {
/*     */                   ChatRegistryStorage chatTypeStorage = (ChatRegistryStorage)wrapper.user().get(ChatRegistryStorage1_19_1.class);
/*     */                   
/*     */                   chatTypeStorage.clear();
/*     */                   
/*     */                   CompoundTag registry = (CompoundTag)wrapper.get(Type.NBT, 0);
/*     */                   
/*     */                   ListTag chatTypes = (ListTag)((CompoundTag)registry.get("minecraft:chat_type")).get("value");
/*     */                   
/*     */                   for (Tag chatType : chatTypes) {
/*     */                     CompoundTag chatTypeCompound = (CompoundTag)chatType;
/*     */                     NumberTag idTag = (NumberTag)chatTypeCompound.get("id");
/*     */                     chatTypeStorage.addChatType(idTag.asInt(), chatTypeCompound);
/*     */                   } 
/*     */                   registry.put("minecraft:chat_type", (Tag)EntityPackets.CHAT_REGISTRY.clone());
/*     */                 });
/* 118 */             handler(Protocol1_19To1_19_1.this.entityRewriter.worldTrackerHandlerByKey());
/*     */           }
/*     */         });
/*     */     
/* 122 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_1.PLAYER_CHAT, (ClientboundPacketType)ClientboundPackets1_19.SYSTEM_CHAT, wrapper -> {
/*     */           wrapper.read(Type.OPTIONAL_BYTE_ARRAY_PRIMITIVE);
/*     */           
/*     */           PlayerMessageSignature signature = (PlayerMessageSignature)wrapper.read(Type.PLAYER_MESSAGE_SIGNATURE);
/*     */           
/*     */           if (!signature.uuid().equals(ZERO_UUID) && (signature.signatureBytes()).length != 0) {
/*     */             ReceivedMessagesStorage messagesStorage = (ReceivedMessagesStorage)wrapper.user().get(ReceivedMessagesStorage.class);
/*     */             
/*     */             messagesStorage.add(signature);
/*     */             
/*     */             if (messagesStorage.tickUnacknowledged() > 64) {
/*     */               messagesStorage.resetUnacknowledgedCount();
/*     */               
/*     */               PacketWrapper chatAckPacket = wrapper.create((PacketType)ServerboundPackets1_19_1.CHAT_ACK);
/*     */               
/*     */               chatAckPacket.write(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
/*     */               
/*     */               chatAckPacket.write(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
/*     */               
/*     */               chatAckPacket.sendToServer(Protocol1_19To1_19_1.class);
/*     */             } 
/*     */           } 
/*     */           
/*     */           String plainMessage = (String)wrapper.read(Type.STRING);
/*     */           
/*     */           JsonElement message = null;
/*     */           
/*     */           JsonElement decoratedMessage = (JsonElement)wrapper.read(Type.OPTIONAL_COMPONENT);
/*     */           
/*     */           if (decoratedMessage != null) {
/*     */             message = decoratedMessage;
/*     */           }
/*     */           
/*     */           wrapper.read((Type)Type.LONG);
/*     */           
/*     */           wrapper.read((Type)Type.LONG);
/*     */           wrapper.read(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY);
/*     */           JsonElement unsignedMessage = (JsonElement)wrapper.read(Type.OPTIONAL_COMPONENT);
/*     */           if (unsignedMessage != null) {
/*     */             message = unsignedMessage;
/*     */           }
/*     */           if (message == null) {
/*     */             message = GsonComponentSerializer.gson().serializeToTree((Component)Component.text(plainMessage));
/*     */           }
/*     */           int filterMaskType = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           if (filterMaskType == 2) {
/*     */             wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
/*     */           }
/*     */           int chatTypeId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           JsonElement senderName = (JsonElement)wrapper.read(Type.COMPONENT);
/*     */           JsonElement targetName = (JsonElement)wrapper.read(Type.OPTIONAL_COMPONENT);
/*     */           decoratedMessage = decorateChatMessage((ChatRegistryStorage)wrapper.user().get(ChatRegistryStorage1_19_1.class), chatTypeId, senderName, targetName, message);
/*     */           if (decoratedMessage == null) {
/*     */             wrapper.cancel();
/*     */             return;
/*     */           } 
/*     */           this.translatableRewriter.processText(decoratedMessage);
/*     */           wrapper.write(Type.COMPONENT, decoratedMessage);
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(1));
/*     */         });
/* 182 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_1.SYSTEM_CHAT, wrapper -> {
/*     */           JsonElement content = (JsonElement)wrapper.passthrough(Type.COMPONENT);
/*     */           
/*     */           this.translatableRewriter.processText(content);
/*     */           
/*     */           boolean overlay = ((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue();
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(overlay ? 2 : 1));
/*     */         });
/* 190 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_19.CHAT_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 193 */             map(Type.STRING);
/* 194 */             map((Type)Type.LONG);
/* 195 */             map((Type)Type.LONG);
/*     */             
/* 197 */             read(Type.BYTE_ARRAY_PRIMITIVE);
/* 198 */             create(Type.BYTE_ARRAY_PRIMITIVE, Protocol1_19To1_19_1.EMPTY_BYTES);
/* 199 */             map((Type)Type.BOOLEAN);
/* 200 */             handler(wrapper -> {
/*     */                   ReceivedMessagesStorage messagesStorage = (ReceivedMessagesStorage)wrapper.user().get(ReceivedMessagesStorage.class);
/*     */                   
/*     */                   messagesStorage.resetUnacknowledgedCount();
/*     */                   wrapper.write(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
/*     */                   wrapper.write(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
/*     */                 });
/*     */           }
/*     */         });
/* 209 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_19.CHAT_COMMAND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 212 */             map(Type.STRING);
/* 213 */             map((Type)Type.LONG);
/* 214 */             map((Type)Type.LONG);
/* 215 */             handler(wrapper -> {
/*     */                   int signatures = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   for (int i = 0; i < signatures; i++) {
/*     */                     wrapper.passthrough(Type.STRING);
/*     */                     
/*     */                     wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                     
/*     */                     wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, Protocol1_19To1_19_1.EMPTY_BYTES);
/*     */                   } 
/*     */                   
/*     */                   wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                   
/*     */                   ReceivedMessagesStorage messagesStorage = (ReceivedMessagesStorage)wrapper.user().get(ReceivedMessagesStorage.class);
/*     */                   messagesStorage.resetUnacknowledgedCount();
/*     */                   wrapper.write(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
/*     */                   wrapper.write(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
/*     */                 });
/*     */           }
/*     */         });
/* 235 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_1.SERVER_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 238 */             map(Type.OPTIONAL_COMPONENT);
/* 239 */             map(Type.OPTIONAL_STRING);
/* 240 */             map((Type)Type.BOOLEAN);
/* 241 */             read((Type)Type.BOOLEAN);
/*     */           }
/*     */         });
/*     */     
/* 245 */     registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO.getId(), ServerboundLoginPackets.HELLO.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 248 */             map(Type.STRING);
/* 249 */             handler(wrapper -> {
/*     */                   ProfileKey profileKey = (ProfileKey)wrapper.read(Type.OPTIONAL_PROFILE_KEY);
/*     */                   
/*     */                   if (profileKey == null) {
/*     */                     wrapper.user().put((StorableObject)new NonceStorage(null));
/*     */                   }
/*     */                 });
/* 256 */             create(Type.OPTIONAL_PROFILE_KEY, null);
/* 257 */             create(Type.OPTIONAL_UUID, null);
/*     */           }
/*     */         });
/*     */     
/* 261 */     registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO.getId(), ClientboundLoginPackets.HELLO.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 264 */             map(Type.STRING);
/* 265 */             handler(wrapper -> {
/*     */                   if (wrapper.user().get(NonceStorage.class) != null) {
/*     */                     return;
/*     */                   }
/*     */                   
/*     */                   byte[] publicKey = (byte[])wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                   
/*     */                   byte[] nonce = (byte[])wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                   
/*     */                   wrapper.user().put((StorableObject)new NonceStorage(CipherUtil.encryptNonce(publicKey, nonce)));
/*     */                 });
/*     */           }
/*     */         });
/* 278 */     registerServerbound(State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY.getId(), ServerboundLoginPackets.ENCRYPTION_KEY.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 281 */             map(Type.BYTE_ARRAY_PRIMITIVE);
/* 282 */             handler(wrapper -> {
/*     */                   NonceStorage nonceStorage = (NonceStorage)wrapper.user().remove(NonceStorage.class);
/*     */                   
/*     */                   boolean isNonce = ((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue();
/*     */                   
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */                   
/*     */                   if (isNonce) {
/*     */                     wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                     
/*     */                     return;
/*     */                   } 
/*     */                   if (nonceStorage == null || nonceStorage.nonce() == null) {
/*     */                     throw new IllegalArgumentException("Server sent nonce is missing");
/*     */                   }
/*     */                   wrapper.read((Type)Type.LONG);
/*     */                   wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                   wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, nonceStorage.nonce());
/*     */                 });
/*     */           }
/*     */         });
/* 303 */     registerClientbound(State.LOGIN, ClientboundLoginPackets.CUSTOM_QUERY.getId(), ClientboundLoginPackets.CUSTOM_QUERY.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 306 */             map((Type)Type.VAR_INT);
/* 307 */             map(Type.STRING);
/* 308 */             handler(wrapper -> {
/*     */                   String identifier = (String)wrapper.get(Type.STRING, 0);
/*     */ 
/*     */ 
/*     */                   
/*     */                   if (identifier.equals("velocity:player_info")) {
/*     */                     byte[] data = (byte[])wrapper.passthrough(Type.REMAINING_BYTES);
/*     */ 
/*     */                     
/*     */                     if (data.length == 1 && data[0] > 1) {
/*     */                       data[0] = 1;
/*     */                     } else if (data.length == 0) {
/*     */                       data = new byte[] { 1 };
/*     */ 
/*     */                       
/*     */                       wrapper.set(Type.REMAINING_BYTES, 0, data);
/*     */                     } else {
/*     */                       ViaBackwards.getPlatform().getLogger().warning("Received unexpected data in velocity:player_info (length=" + data.length + ")");
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 333 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_19_1.CUSTOM_CHAT_COMPLETIONS);
/* 334 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_19_1.DELETE_CHAT_MESSAGE);
/* 335 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_19_1.PLAYER_CHAT_HEADER);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection user) {
/* 340 */     user.put((StorableObject)new ChatRegistryStorage1_19_1());
/* 341 */     user.put((StorableObject)new ReceivedMessagesStorage());
/* 342 */     addEntityTracker(user, (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_19Types.PLAYER));
/*     */   }
/*     */ 
/*     */   
/*     */   public TranslatableRewriter<ClientboundPackets1_19_1> getTranslatableRewriter() {
/* 347 */     return this.translatableRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets1_19_1 getEntityRewriter() {
/* 352 */     return this.entityRewriter;
/*     */   }
/*     */   
/*     */   public static JsonElement decorateChatMessage(ChatRegistryStorage chatRegistryStorage, int chatTypeId, JsonElement senderName, JsonElement targetName, JsonElement message) {
/* 356 */     CompoundTag chatType = chatRegistryStorage.chatType(chatTypeId);
/* 357 */     if (chatType == null) {
/* 358 */       ViaBackwards.getPlatform().getLogger().warning("Chat message has unknown chat type id " + chatTypeId + ". Message: " + message);
/* 359 */       return null;
/*     */     } 
/*     */     
/* 362 */     chatType = (CompoundTag)((CompoundTag)chatType.get("element")).get("chat");
/* 363 */     if (chatType == null) {
/* 364 */       return null;
/*     */     }
/*     */     
/* 367 */     String translationKey = (String)chatType.get("translation_key").getValue();
/* 368 */     TranslatableComponent.Builder componentBuilder = Component.translatable().key(translationKey);
/*     */ 
/*     */     
/* 371 */     CompoundTag style = (CompoundTag)chatType.get("style");
/* 372 */     if (style != null) {
/* 373 */       Style.Builder styleBuilder = Style.style();
/* 374 */       StringTag color = (StringTag)style.get("color");
/* 375 */       if (color != null) {
/* 376 */         NamedTextColor textColor = (NamedTextColor)NamedTextColor.NAMES.value(color.getValue());
/* 377 */         if (textColor != null) {
/* 378 */           styleBuilder.color((TextColor)NamedTextColor.NAMES.value(color.getValue()));
/*     */         }
/*     */       } 
/*     */       
/* 382 */       for (String key : TextDecoration.NAMES.keys()) {
/* 383 */         if (style.contains(key)) {
/* 384 */           styleBuilder.decoration((TextDecoration)TextDecoration.NAMES.value(key), (((ByteTag)style.get(key)).asByte() == 1));
/*     */         }
/*     */       } 
/* 387 */       componentBuilder.style(styleBuilder.build());
/*     */     } 
/*     */ 
/*     */     
/* 391 */     ListTag parameters = (ListTag)chatType.get("parameters");
/* 392 */     if (parameters != null) {
/* 393 */       List<Component> arguments = new ArrayList<>();
/* 394 */       for (Tag element : parameters) {
/* 395 */         JsonElement argument = null;
/* 396 */         switch ((String)element.getValue()) {
/*     */           case "sender":
/* 398 */             argument = senderName;
/*     */             break;
/*     */           case "content":
/* 401 */             argument = message;
/*     */             break;
/*     */           case "target":
/* 404 */             Preconditions.checkNotNull(targetName, "Target name is null");
/* 405 */             argument = targetName;
/*     */             break;
/*     */           default:
/* 408 */             ViaBackwards.getPlatform().getLogger().warning("Unknown parameter for chat decoration: " + element.getValue()); break;
/*     */         } 
/* 410 */         if (argument != null) {
/* 411 */           arguments.add(GsonComponentSerializer.gson().deserializeFromTree(argument));
/*     */         }
/*     */       } 
/* 414 */       componentBuilder.args(arguments);
/*     */     } 
/*     */     
/* 417 */     return GsonComponentSerializer.gson().serializeToTree((Component)componentBuilder.build());
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_19to1_19_1\Protocol1_19To1_19_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viaversion.protocols.protocol1_19_1to1_19;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.ProfileKey;
/*     */ import com.viaversion.viaversion.api.minecraft.nbt.BinaryTagIO;
/*     */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.State;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
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
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.storage.ChatTypeStorage;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.storage.NonceStorage;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ServerboundPackets1_19;
/*     */ import com.viaversion.viaversion.util.CipherUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Protocol1_19_1To1_19
/*     */   extends AbstractProtocol<ClientboundPackets1_19, ClientboundPackets1_19_1, ServerboundPackets1_19, ServerboundPackets1_19_1>
/*     */ {
/*     */   private static final String CHAT_REGISTRY_SNBT = "{\n  \"minecraft:chat_type\": {\n    \"type\": \"minecraft:chat_type\",\n    \"value\": [\n         {\n            \"name\":\"minecraft:chat\",\n            \"id\":1,\n            \"element\":{\n               \"chat\":{\n                  \"translation_key\":\"chat.type.text\",\n                  \"parameters\":[\n                     \"sender\",\n                     \"content\"\n                  ]\n               },\n               \"narration\":{\n                  \"translation_key\":\"chat.type.text.narrate\",\n                  \"parameters\":[\n                     \"sender\",\n                     \"content\"\n                  ]\n               }\n            }\n         }    ]\n  }\n}";
/*     */   private static final CompoundTag CHAT_REGISTRY;
/*     */   
/*     */   static {
/*     */     try {
/*  87 */       CHAT_REGISTRY = (CompoundTag)BinaryTagIO.readString("{\n  \"minecraft:chat_type\": {\n    \"type\": \"minecraft:chat_type\",\n    \"value\": [\n         {\n            \"name\":\"minecraft:chat\",\n            \"id\":1,\n            \"element\":{\n               \"chat\":{\n                  \"translation_key\":\"chat.type.text\",\n                  \"parameters\":[\n                     \"sender\",\n                     \"content\"\n                  ]\n               },\n               \"narration\":{\n                  \"translation_key\":\"chat.type.text.narrate\",\n                  \"parameters\":[\n                     \"sender\",\n                     \"content\"\n                  ]\n               }\n            }\n         }    ]\n  }\n}").get("minecraft:chat_type");
/*  88 */     } catch (IOException e) {
/*  89 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Protocol1_19_1To1_19() {
/*  94 */     super(ClientboundPackets1_19.class, ClientboundPackets1_19_1.class, ServerboundPackets1_19.class, ServerboundPackets1_19_1.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  99 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19.SYSTEM_CHAT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 102 */             map(Type.COMPONENT);
/* 103 */             handler(wrapper -> {
/*     */                   int type = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   boolean overlay = (type == 2);
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(overlay));
/*     */                 });
/*     */           }
/*     */         });
/* 110 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19.PLAYER_CHAT, ClientboundPackets1_19_1.SYSTEM_CHAT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 113 */             handler(wrapper -> {
/*     */                   JsonElement signedContent = (JsonElement)wrapper.read(Type.COMPONENT);
/*     */                   
/*     */                   JsonElement unsignedContent = (JsonElement)wrapper.read(Type.OPTIONAL_COMPONENT);
/*     */                   
/*     */                   int chatTypeId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   wrapper.read(Type.UUID);
/*     */                   JsonElement senderName = (JsonElement)wrapper.read(Type.COMPONENT);
/*     */                   JsonElement teamName = (JsonElement)wrapper.read(Type.OPTIONAL_COMPONENT);
/*     */                   CompoundTag chatType = ((ChatTypeStorage)wrapper.user().get(ChatTypeStorage.class)).chatType(chatTypeId);
/*     */                   ChatDecorationResult decorationResult = Protocol1_19_1To1_19.decorateChatMessage(chatType, chatTypeId, senderName, teamName, (unsignedContent != null) ? unsignedContent : signedContent);
/*     */                   if (decorationResult == null) {
/*     */                     wrapper.cancel();
/*     */                     return;
/*     */                   } 
/*     */                   wrapper.write(Type.COMPONENT, decorationResult.content());
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(decorationResult.overlay()));
/*     */                 });
/* 132 */             read((Type)Type.LONG);
/* 133 */             read((Type)Type.LONG);
/* 134 */             read(Type.BYTE_ARRAY_PRIMITIVE);
/*     */           }
/*     */         });
/* 137 */     registerServerbound(ServerboundPackets1_19_1.CHAT_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 140 */             map(Type.STRING);
/* 141 */             map((Type)Type.LONG);
/* 142 */             map((Type)Type.LONG);
/* 143 */             map(Type.BYTE_ARRAY_PRIMITIVE);
/* 144 */             map((Type)Type.BOOLEAN);
/* 145 */             read(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY);
/* 146 */             read(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE);
/*     */           }
/*     */         });
/* 149 */     registerServerbound(ServerboundPackets1_19_1.CHAT_COMMAND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 152 */             map(Type.STRING);
/* 153 */             map((Type)Type.LONG);
/* 154 */             map((Type)Type.LONG);
/* 155 */             handler(wrapper -> {
/*     */                   int signatures = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */                   for (int i = 0; i < signatures; i++) {
/*     */                     wrapper.passthrough(Type.STRING);
/*     */                     wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                   } 
/*     */                 });
/* 162 */             map((Type)Type.BOOLEAN);
/* 163 */             read(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY);
/* 164 */             read(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE);
/*     */           }
/*     */         });
/* 167 */     cancelServerbound(ServerboundPackets1_19_1.CHAT_ACK);
/*     */     
/* 169 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 172 */             map((Type)Type.INT);
/* 173 */             map((Type)Type.BOOLEAN);
/* 174 */             map((Type)Type.UNSIGNED_BYTE);
/* 175 */             map((Type)Type.BYTE);
/* 176 */             map(Type.STRING_ARRAY);
/* 177 */             handler(wrapper -> {
/*     */                   ChatTypeStorage chatTypeStorage = (ChatTypeStorage)wrapper.user().get(ChatTypeStorage.class);
/*     */                   
/*     */                   chatTypeStorage.clear();
/*     */                   
/*     */                   CompoundTag registry = (CompoundTag)wrapper.passthrough(Type.NBT);
/*     */                   
/*     */                   ListTag chatTypes = (ListTag)((CompoundTag)registry.get("minecraft:chat_type")).get("value");
/*     */                   
/*     */                   for (Tag chatType : chatTypes) {
/*     */                     CompoundTag chatTypeCompound = (CompoundTag)chatType;
/*     */                     NumberTag idTag = (NumberTag)chatTypeCompound.get("id");
/*     */                     chatTypeStorage.addChatType(idTag.asInt(), chatTypeCompound);
/*     */                   } 
/*     */                   registry.put("minecraft:chat_type", (Tag)Protocol1_19_1To1_19.CHAT_REGISTRY.clone());
/*     */                 });
/*     */           }
/*     */         });
/* 195 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19.SERVER_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 198 */             map(Type.OPTIONAL_COMPONENT);
/* 199 */             map(Type.OPTIONAL_STRING);
/* 200 */             map((Type)Type.BOOLEAN);
/* 201 */             create((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */           }
/*     */         });
/*     */     
/* 205 */     registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO.getId(), ServerboundLoginPackets.HELLO.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 208 */             map(Type.STRING);
/* 209 */             handler(wrapper -> {
/*     */                   ProfileKey profileKey = (ProfileKey)wrapper.read(Type.OPTIONAL_PROFILE_KEY);
/*     */                   
/*     */                   wrapper.write(Type.OPTIONAL_PROFILE_KEY, null);
/*     */                   
/*     */                   if (profileKey == null) {
/*     */                     wrapper.user().put((StorableObject)new NonceStorage(null));
/*     */                   }
/*     */                 });
/* 218 */             read(Type.OPTIONAL_UUID);
/*     */           }
/*     */         });
/* 221 */     registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO.getId(), ClientboundLoginPackets.HELLO.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 224 */             map(Type.STRING);
/* 225 */             handler(wrapper -> {
/*     */                   if (wrapper.user().has(NonceStorage.class)) {
/*     */                     return;
/*     */                   }
/*     */                   
/*     */                   byte[] publicKey = (byte[])wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                   byte[] nonce = (byte[])wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                   wrapper.user().put((StorableObject)new NonceStorage(CipherUtil.encryptNonce(publicKey, nonce)));
/*     */                 });
/*     */           }
/*     */         });
/* 236 */     registerServerbound(State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY.getId(), ServerboundLoginPackets.ENCRYPTION_KEY.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 239 */             map(Type.BYTE_ARRAY_PRIMITIVE);
/* 240 */             handler(wrapper -> {
/*     */                   NonceStorage nonceStorage = (NonceStorage)wrapper.user().remove(NonceStorage.class);
/*     */                   
/*     */                   if (nonceStorage.nonce() == null) {
/*     */                     return;
/*     */                   }
/*     */                   boolean isNonce = ((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue();
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */                   if (!isNonce) {
/*     */                     wrapper.read((Type)Type.LONG);
/*     */                     wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                     wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, nonceStorage.nonce());
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 256 */     registerClientbound(State.LOGIN, ClientboundLoginPackets.CUSTOM_QUERY.getId(), ClientboundLoginPackets.CUSTOM_QUERY.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 259 */             map((Type)Type.VAR_INT);
/* 260 */             map(Type.STRING);
/* 261 */             handler(wrapper -> {
/*     */                   String identifier = (String)wrapper.get(Type.STRING, 0);
/*     */                   if (identifier.equals("velocity:player_info")) {
/*     */                     byte[] data = (byte[])wrapper.passthrough(Type.REMAINING_BYTES);
/*     */                     if (data.length == 1 && data[0] > 1) {
/*     */                       data[0] = 1;
/*     */                     } else if (data.length == 0) {
/*     */                       data = new byte[] { 1 };
/*     */                       wrapper.set(Type.REMAINING_BYTES, 0, data);
/*     */                     } else {
/*     */                       Via.getPlatform().getLogger().warning("Received unexpected data in velocity:player_info (length=" + data.length + ")");
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(UserConnection connection) {
/* 285 */     connection.put((StorableObject)new ChatTypeStorage());
/*     */   }
/*     */   
/*     */   public static ChatDecorationResult decorateChatMessage(CompoundTag chatType, int chatTypeId, JsonElement senderName, JsonElement teamName, JsonElement message) {
/* 289 */     if (chatType == null) {
/* 290 */       Via.getPlatform().getLogger().warning("Chat message has unknown chat type id " + chatTypeId + ". Message: " + message);
/* 291 */       return null;
/*     */     } 
/*     */     
/* 294 */     CompoundTag chatData = (CompoundTag)((CompoundTag)chatType.get("element")).get("chat");
/* 295 */     boolean overlay = false;
/* 296 */     if (chatData == null) {
/* 297 */       chatData = (CompoundTag)((CompoundTag)chatType.get("element")).get("overlay");
/* 298 */       if (chatData == null)
/*     */       {
/* 300 */         return null;
/*     */       }
/*     */       
/* 303 */       overlay = true;
/*     */     } 
/*     */     
/* 306 */     CompoundTag decoaration = (CompoundTag)chatData.get("decoration");
/* 307 */     if (decoaration == null) {
/* 308 */       return new ChatDecorationResult(message, overlay);
/*     */     }
/*     */     
/* 311 */     String translationKey = (String)decoaration.get("translation_key").getValue();
/* 312 */     TranslatableComponent.Builder componentBuilder = Component.translatable().key(translationKey);
/*     */ 
/*     */     
/* 315 */     CompoundTag style = (CompoundTag)decoaration.get("style");
/* 316 */     if (style != null) {
/* 317 */       Style.Builder styleBuilder = Style.style();
/* 318 */       StringTag color = (StringTag)style.get("color");
/* 319 */       if (color != null) {
/* 320 */         NamedTextColor textColor = (NamedTextColor)NamedTextColor.NAMES.value(color.getValue());
/* 321 */         if (textColor != null) {
/* 322 */           styleBuilder.color((TextColor)NamedTextColor.NAMES.value(color.getValue()));
/*     */         }
/*     */       } 
/*     */       
/* 326 */       for (String key : TextDecoration.NAMES.keys()) {
/* 327 */         if (style.contains(key)) {
/* 328 */           styleBuilder.decoration((TextDecoration)TextDecoration.NAMES.value(key), (((ByteTag)style.get(key)).asByte() == 1));
/*     */         }
/*     */       } 
/* 331 */       componentBuilder.style(styleBuilder.build());
/*     */     } 
/*     */ 
/*     */     
/* 335 */     ListTag parameters = (ListTag)decoaration.get("parameters");
/* 336 */     if (parameters != null) {
/* 337 */       List<Component> arguments = new ArrayList<>();
/* 338 */       for (Tag element : parameters) {
/* 339 */         JsonElement argument = null;
/* 340 */         switch ((String)element.getValue()) {
/*     */           case "sender":
/* 342 */             argument = senderName;
/*     */             break;
/*     */           case "content":
/* 345 */             argument = message;
/*     */             break;
/*     */           case "team_name":
/* 348 */             Preconditions.checkNotNull(teamName, "Team name is null");
/* 349 */             argument = teamName;
/*     */             break;
/*     */           default:
/* 352 */             Via.getPlatform().getLogger().warning("Unknown parameter for chat decoration: " + element.getValue()); break;
/*     */         } 
/* 354 */         if (argument != null) {
/* 355 */           arguments.add(GsonComponentSerializer.gson().deserializeFromTree(argument));
/*     */         }
/*     */       } 
/* 358 */       componentBuilder.args(arguments);
/*     */     } 
/* 360 */     return new ChatDecorationResult(GsonComponentSerializer.gson().serializeToTree((Component)componentBuilder.build()), overlay);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19_1to1_19\Protocol1_19_1To1_19.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
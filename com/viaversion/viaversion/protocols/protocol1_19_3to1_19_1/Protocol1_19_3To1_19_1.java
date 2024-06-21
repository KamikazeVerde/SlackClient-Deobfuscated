/*     */ package com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.MappingDataBase;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
/*     */ import com.viaversion.viaversion.api.minecraft.RegistryType;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_3Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.State;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.BitSetType;
/*     */ import com.viaversion.viaversion.api.type.types.ByteArrayType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_19_3;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
/*     */ import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ClientboundPackets1_19_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ServerboundPackets1_19_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.packets.EntityPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.packets.InventoryPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.storage.ReceivedMessagesStorage;
/*     */ import com.viaversion.viaversion.rewriter.CommandRewriter;
/*     */ import com.viaversion.viaversion.rewriter.SoundRewriter;
/*     */ import com.viaversion.viaversion.rewriter.StatisticsRewriter;
/*     */ import com.viaversion.viaversion.rewriter.TagRewriter;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Protocol1_19_3To1_19_1
/*     */   extends AbstractProtocol<ClientboundPackets1_19_1, ClientboundPackets1_19_3, ServerboundPackets1_19_1, ServerboundPackets1_19_3>
/*     */ {
/*  53 */   public static final MappingData MAPPINGS = (MappingData)new MappingDataBase("1.19", "1.19.3");
/*  54 */   private static final ByteArrayType.OptionalByteArrayType OPTIONAL_MESSAGE_SIGNATURE_BYTES_TYPE = new ByteArrayType.OptionalByteArrayType(256);
/*  55 */   private static final ByteArrayType MESSAGE_SIGNATURE_BYTES_TYPE = new ByteArrayType(256);
/*  56 */   private static final BitSetType ACKNOWLEDGED_BIT_SET_TYPE = new BitSetType(20);
/*  57 */   private static final UUID ZERO_UUID = new UUID(0L, 0L);
/*  58 */   private static final byte[] EMPTY_BYTES = new byte[0];
/*  59 */   private final EntityPackets entityRewriter = new EntityPackets(this);
/*  60 */   private final InventoryPackets itemRewriter = new InventoryPackets(this);
/*     */   
/*     */   public Protocol1_19_3To1_19_1() {
/*  63 */     super(ClientboundPackets1_19_1.class, ClientboundPackets1_19_3.class, ServerboundPackets1_19_1.class, ServerboundPackets1_19_3.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  68 */     TagRewriter<ClientboundPackets1_19_1> tagRewriter = new TagRewriter((Protocol)this);
/*     */ 
/*     */     
/*  71 */     tagRewriter.addTagRaw(RegistryType.ITEM, "minecraft:creeper_igniters", new int[] { 733 });
/*     */     
/*  73 */     tagRewriter.addEmptyTags(RegistryType.ITEM, new String[] { "minecraft:bookshelf_books", "minecraft:hanging_signs", "minecraft:stripped_logs" });
/*  74 */     tagRewriter.addEmptyTags(RegistryType.BLOCK, new String[] { "minecraft:all_hanging_signs", "minecraft:ceiling_hanging_signs", "minecraft:invalid_spawn_inside", "minecraft:stripped_logs", "minecraft:wall_hanging_signs" });
/*     */     
/*  76 */     tagRewriter.registerGeneric((ClientboundPacketType)ClientboundPackets1_19_1.TAGS);
/*     */     
/*  78 */     this.entityRewriter.register();
/*  79 */     this.itemRewriter.register();
/*     */     
/*  81 */     final SoundRewriter<ClientboundPackets1_19_1> soundRewriter = new SoundRewriter((Protocol)this);
/*  82 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_1.ENTITY_SOUND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  85 */             map((Type)Type.VAR_INT);
/*  86 */             handler(soundRewriter.getSoundHandler());
/*  87 */             handler(wrapper -> {
/*     */                   int soundId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(soundId + 1));
/*     */                 });
/*     */           }
/*     */         });
/*  94 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_1.SOUND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  97 */             map((Type)Type.VAR_INT);
/*  98 */             handler(soundRewriter.getSoundHandler());
/*  99 */             handler(wrapper -> {
/*     */                   int soundId = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(soundId + 1));
/*     */                 });
/*     */           }
/*     */         });
/* 106 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_1.NAMED_SOUND, ClientboundPackets1_19_3.SOUND, wrapper -> {
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */           
/*     */           wrapper.passthrough(Type.STRING);
/*     */           wrapper.write((Type)Type.OPTIONAL_FLOAT, null);
/*     */         });
/* 112 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_19_1.STATISTICS);
/*     */     
/* 114 */     CommandRewriter<ClientboundPackets1_19_1> commandRewriter = new CommandRewriter<ClientboundPackets1_19_1>((Protocol)this)
/*     */       {
/*     */         public void handleArgument(PacketWrapper wrapper, String argumentType) throws Exception {
/* 117 */           switch (argumentType) {
/*     */             case "minecraft:item_enchantment":
/* 119 */               wrapper.write(Type.STRING, "minecraft:enchantment");
/*     */               return;
/*     */             case "minecraft:mob_effect":
/* 122 */               wrapper.write(Type.STRING, "minecraft:mob_effect");
/*     */               return;
/*     */             case "minecraft:entity_summon":
/* 125 */               wrapper.write(Type.STRING, "minecraft:entity_type");
/*     */               return;
/*     */           } 
/* 128 */           super.handleArgument(wrapper, argumentType);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public String handleArgumentType(String argumentType) {
/* 135 */           switch (argumentType) {
/*     */             case "minecraft:resource":
/* 137 */               return "minecraft:resource_key";
/*     */             case "minecraft:resource_or_tag":
/* 139 */               return "minecraft:resource_or_tag_key";
/*     */             case "minecraft:entity_summon":
/*     */             case "minecraft:item_enchantment":
/*     */             case "minecraft:mob_effect":
/* 143 */               return "minecraft:resource";
/*     */           } 
/* 145 */           return argumentType;
/*     */         }
/*     */       };
/* 148 */     commandRewriter.registerDeclareCommands1_19((ClientboundPacketType)ClientboundPackets1_19_1.DECLARE_COMMANDS);
/*     */     
/* 150 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_1.SERVER_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 153 */             map(Type.OPTIONAL_COMPONENT);
/* 154 */             map(Type.OPTIONAL_STRING);
/* 155 */             read((Type)Type.BOOLEAN);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 160 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_19_1.PLAYER_CHAT, ClientboundPackets1_19_3.DISGUISED_CHAT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 163 */             read(Type.OPTIONAL_BYTE_ARRAY_PRIMITIVE);
/* 164 */             handler(wrapper -> {
/*     */                   PlayerMessageSignature signature = (PlayerMessageSignature)wrapper.read(Type.PLAYER_MESSAGE_SIGNATURE);
/*     */                   
/*     */                   if (!signature.uuid().equals(Protocol1_19_3To1_19_1.ZERO_UUID) && (signature.signatureBytes()).length != 0) {
/*     */                     ReceivedMessagesStorage messagesStorage = (ReceivedMessagesStorage)wrapper.user().get(ReceivedMessagesStorage.class);
/*     */                     
/*     */                     messagesStorage.add(signature);
/*     */                     
/*     */                     if (messagesStorage.tickUnacknowledged() > 64) {
/*     */                       messagesStorage.resetUnacknowledgedCount();
/*     */                       
/*     */                       PacketWrapper chatAckPacket = wrapper.create((PacketType)ServerboundPackets1_19_1.CHAT_ACK);
/*     */                       
/*     */                       chatAckPacket.write(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
/*     */                       
/*     */                       chatAckPacket.write(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
/*     */                       
/*     */                       chatAckPacket.sendToServer(Protocol1_19_3To1_19_1.class);
/*     */                     } 
/*     */                   } 
/*     */                   
/*     */                   String plainMessage = (String)wrapper.read(Type.STRING);
/*     */                   
/*     */                   JsonElement decoratedMessage = (JsonElement)wrapper.read(Type.OPTIONAL_COMPONENT);
/*     */                   
/*     */                   wrapper.read((Type)Type.LONG);
/*     */                   
/*     */                   wrapper.read((Type)Type.LONG);
/*     */                   wrapper.read(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY);
/*     */                   JsonElement unsignedMessage = (JsonElement)wrapper.read(Type.OPTIONAL_COMPONENT);
/*     */                   if (unsignedMessage != null) {
/*     */                     decoratedMessage = unsignedMessage;
/*     */                   }
/*     */                   if (decoratedMessage == null) {
/*     */                     decoratedMessage = GsonComponentSerializer.gson().serializeToTree((Component)Component.text(plainMessage));
/*     */                   }
/*     */                   int filterMaskType = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   if (filterMaskType == 2) {
/*     */                     wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
/*     */                   }
/*     */                   wrapper.write(Type.COMPONENT, decoratedMessage);
/*     */                 });
/*     */           }
/*     */         });
/* 208 */     registerServerbound(ServerboundPackets1_19_3.CHAT_COMMAND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 211 */             map(Type.STRING);
/* 212 */             map((Type)Type.LONG);
/* 213 */             map((Type)Type.LONG);
/* 214 */             handler(wrapper -> {
/*     */                   int signatures = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                   
/*     */                   for (int i = 0; i < signatures; i++) {
/*     */                     wrapper.read(Type.STRING);
/*     */                     wrapper.read((Type)Protocol1_19_3To1_19_1.MESSAGE_SIGNATURE_BYTES_TYPE);
/*     */                   } 
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                   ReceivedMessagesStorage messagesStorage = (ReceivedMessagesStorage)wrapper.user().get(ReceivedMessagesStorage.class);
/*     */                   messagesStorage.resetUnacknowledgedCount();
/*     */                   wrapper.write(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
/*     */                   wrapper.write(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
/*     */                 });
/* 229 */             read((Type)Type.VAR_INT);
/* 230 */             read((Type)Protocol1_19_3To1_19_1.ACKNOWLEDGED_BIT_SET_TYPE);
/*     */           }
/*     */         });
/* 233 */     registerServerbound(ServerboundPackets1_19_3.CHAT_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 236 */             map(Type.STRING);
/* 237 */             map((Type)Type.LONG);
/*     */             
/* 239 */             read((Type)Type.LONG);
/* 240 */             create((Type)Type.LONG, Long.valueOf(0L));
/* 241 */             handler(wrapper -> {
/*     */                   wrapper.read((Type)Protocol1_19_3To1_19_1.OPTIONAL_MESSAGE_SIGNATURE_BYTES_TYPE);
/*     */                   
/*     */                   wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, Protocol1_19_3To1_19_1.EMPTY_BYTES);
/*     */                   
/*     */                   wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                   ReceivedMessagesStorage messagesStorage = (ReceivedMessagesStorage)wrapper.user().get(ReceivedMessagesStorage.class);
/*     */                   messagesStorage.resetUnacknowledgedCount();
/*     */                   wrapper.write(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
/*     */                   wrapper.write(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
/*     */                 });
/* 252 */             read((Type)Type.VAR_INT);
/* 253 */             read((Type)Protocol1_19_3To1_19_1.ACKNOWLEDGED_BIT_SET_TYPE);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 258 */     registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO.getId(), ServerboundLoginPackets.HELLO.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 261 */             map(Type.STRING);
/* 262 */             create(Type.OPTIONAL_PROFILE_KEY, null);
/*     */           }
/*     */         });
/* 265 */     registerServerbound(State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY.getId(), ServerboundLoginPackets.ENCRYPTION_KEY.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 268 */             map(Type.BYTE_ARRAY_PRIMITIVE);
/* 269 */             create((Type)Type.BOOLEAN, Boolean.valueOf(true));
/* 270 */             map(Type.BYTE_ARRAY_PRIMITIVE);
/*     */           }
/*     */         });
/*     */     
/* 274 */     cancelServerbound(ServerboundPackets1_19_3.CHAT_SESSION_UPDATE);
/* 275 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_19_1.DELETE_CHAT_MESSAGE);
/* 276 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_19_1.PLAYER_CHAT_HEADER);
/* 277 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_19_1.CHAT_PREVIEW);
/* 278 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_19_1.SET_DISPLAY_CHAT_PREVIEW);
/* 279 */     cancelServerbound(ServerboundPackets1_19_3.CHAT_ACK);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onMappingDataLoaded() {
/* 284 */     super.onMappingDataLoaded();
/* 285 */     Types1_19_3.PARTICLE.filler((Protocol)this)
/* 286 */       .reader("block", ParticleType.Readers.BLOCK)
/* 287 */       .reader("block_marker", ParticleType.Readers.BLOCK)
/* 288 */       .reader("dust", ParticleType.Readers.DUST)
/* 289 */       .reader("falling_dust", ParticleType.Readers.BLOCK)
/* 290 */       .reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION)
/* 291 */       .reader("item", ParticleType.Readers.VAR_INT_ITEM)
/* 292 */       .reader("vibration", ParticleType.Readers.VIBRATION)
/* 293 */       .reader("sculk_charge", ParticleType.Readers.SCULK_CHARGE)
/* 294 */       .reader("shriek", ParticleType.Readers.SHRIEK);
/* 295 */     Entity1_19_3Types.initialize((Protocol)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection user) {
/* 300 */     user.put((StorableObject)new ReceivedMessagesStorage());
/* 301 */     addEntityTracker(user, (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_19_3Types.PLAYER));
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingData getMappingData() {
/* 306 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets getEntityRewriter() {
/* 311 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryPackets getItemRewriter() {
/* 316 */     return this.itemRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19_3to1_19_1\Protocol1_19_3To1_19_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
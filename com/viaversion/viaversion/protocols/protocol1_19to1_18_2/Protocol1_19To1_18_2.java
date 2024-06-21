/*     */ package com.viaversion.viaversion.protocols.protocol1_19to1_18_2;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_19Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*     */ import com.viaversion.viaversion.api.platform.providers.ViaProviders;
/*     */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.State;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_19;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
/*     */ import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.data.MappingData;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.packets.EntityPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.packets.InventoryPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.packets.WorldPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.provider.AckSequenceProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.storage.DimensionRegistryStorage;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.storage.NonceStorage;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.storage.SequenceStorage;
/*     */ import com.viaversion.viaversion.rewriter.CommandRewriter;
/*     */ import com.viaversion.viaversion.rewriter.SoundRewriter;
/*     */ import com.viaversion.viaversion.rewriter.StatisticsRewriter;
/*     */ import com.viaversion.viaversion.rewriter.TagRewriter;
/*     */ import com.viaversion.viaversion.util.CipherUtil;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Protocol1_19To1_18_2
/*     */   extends AbstractProtocol<ClientboundPackets1_18, ClientboundPackets1_19, ServerboundPackets1_17, ServerboundPackets1_19>
/*     */ {
/*  55 */   public static final MappingData MAPPINGS = new MappingData();
/*  56 */   private final EntityPackets entityRewriter = new EntityPackets(this);
/*  57 */   private final InventoryPackets itemRewriter = new InventoryPackets(this);
/*     */   
/*     */   public Protocol1_19To1_18_2() {
/*  60 */     super(ClientboundPackets1_18.class, ClientboundPackets1_19.class, ServerboundPackets1_17.class, ServerboundPackets1_19.class);
/*     */   }
/*     */   
/*     */   public static boolean isTextComponentNull(JsonElement element) {
/*  64 */     return (element == null || element.isJsonNull() || (element.isJsonArray() && element.getAsJsonArray().size() == 0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  69 */     TagRewriter<ClientboundPackets1_18> tagRewriter = new TagRewriter((Protocol)this);
/*  70 */     tagRewriter.registerGeneric((ClientboundPacketType)ClientboundPackets1_18.TAGS);
/*     */     
/*  72 */     this.entityRewriter.register();
/*  73 */     this.itemRewriter.register();
/*  74 */     WorldPackets.register(this);
/*     */     
/*  76 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_18.ADD_VIBRATION_SIGNAL);
/*     */     
/*  78 */     final SoundRewriter<ClientboundPackets1_18> soundRewriter = new SoundRewriter((Protocol)this);
/*  79 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_18.SOUND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  82 */             map((Type)Type.VAR_INT);
/*  83 */             map((Type)Type.VAR_INT);
/*  84 */             map((Type)Type.INT);
/*  85 */             map((Type)Type.INT);
/*  86 */             map((Type)Type.INT);
/*  87 */             map((Type)Type.FLOAT);
/*  88 */             map((Type)Type.FLOAT);
/*  89 */             handler(wrapper -> wrapper.write((Type)Type.LONG, Long.valueOf(Protocol1_19To1_18_2.randomLong())));
/*  90 */             handler(soundRewriter.getSoundHandler());
/*     */           }
/*     */         });
/*  93 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_18.ENTITY_SOUND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  96 */             map((Type)Type.VAR_INT);
/*  97 */             map((Type)Type.VAR_INT);
/*  98 */             map((Type)Type.VAR_INT);
/*  99 */             map((Type)Type.FLOAT);
/* 100 */             map((Type)Type.FLOAT);
/* 101 */             handler(wrapper -> wrapper.write((Type)Type.LONG, Long.valueOf(Protocol1_19To1_18_2.randomLong())));
/* 102 */             handler(soundRewriter.getSoundHandler());
/*     */           }
/*     */         });
/* 105 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_18.NAMED_SOUND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 108 */             map(Type.STRING);
/* 109 */             map((Type)Type.VAR_INT);
/* 110 */             map((Type)Type.INT);
/* 111 */             map((Type)Type.INT);
/* 112 */             map((Type)Type.INT);
/* 113 */             map((Type)Type.FLOAT);
/* 114 */             map((Type)Type.FLOAT);
/* 115 */             handler(wrapper -> wrapper.write((Type)Type.LONG, Long.valueOf(Protocol1_19To1_18_2.randomLong())));
/*     */           }
/*     */         });
/*     */     
/* 119 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_18.STATISTICS);
/*     */     
/* 121 */     PacketHandler titleHandler = wrapper -> {
/*     */         JsonElement component = (JsonElement)wrapper.read(Type.COMPONENT);
/*     */         if (!isTextComponentNull(component)) {
/*     */           wrapper.write(Type.COMPONENT, component);
/*     */         } else {
/*     */           wrapper.write(Type.COMPONENT, ChatRewriter.emptyComponent());
/*     */         } 
/*     */       };
/* 129 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_18.TITLE_TEXT, titleHandler);
/* 130 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_18.TITLE_SUBTITLE, titleHandler);
/*     */     
/* 132 */     CommandRewriter<ClientboundPackets1_18> commandRewriter = new CommandRewriter((Protocol)this);
/* 133 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_18.DECLARE_COMMANDS, wrapper -> {
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
/*     */               String argumentType = (String)wrapper.read(Type.STRING);
/*     */               
/*     */               int argumentTypeId = MAPPINGS.getArgumentTypeMappings().mappedId(argumentType);
/*     */               if (argumentTypeId == -1) {
/*     */                 Via.getPlatform().getLogger().warning("Unknown command argument type: " + argumentType);
/*     */               }
/*     */               wrapper.write((Type)Type.VAR_INT, Integer.valueOf(argumentTypeId));
/*     */               commandRewriter.handleArgument(wrapper, argumentType);
/*     */               if ((flags & 0x10) != 0) {
/*     */                 wrapper.passthrough(Type.STRING);
/*     */               }
/*     */             } 
/*     */           } 
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */         });
/* 167 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_18.CHAT_MESSAGE, ClientboundPackets1_19.SYSTEM_CHAT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 170 */             map(Type.COMPONENT);
/* 171 */             handler(wrapper -> {
/*     */                   int type = ((Byte)wrapper.read((Type)Type.BYTE)).byteValue();
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf((type == 0) ? 1 : type));
/*     */                 });
/* 175 */             read(Type.UUID);
/*     */           }
/*     */         });
/*     */     
/* 179 */     registerServerbound(ServerboundPackets1_19.CHAT_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 182 */             map(Type.STRING);
/* 183 */             read((Type)Type.LONG);
/* 184 */             read((Type)Type.LONG);
/* 185 */             read(Type.BYTE_ARRAY_PRIMITIVE);
/* 186 */             read((Type)Type.BOOLEAN);
/*     */           }
/*     */         });
/* 189 */     registerServerbound(ServerboundPackets1_19.CHAT_COMMAND, (ServerboundPacketType)ServerboundPackets1_17.CHAT_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 192 */             map(Type.STRING);
/* 193 */             read((Type)Type.LONG);
/* 194 */             read((Type)Type.LONG);
/* 195 */             handler(wrapper -> {
/*     */                   String command = (String)wrapper.get(Type.STRING, 0);
/*     */                   
/*     */                   wrapper.set(Type.STRING, 0, "/" + command);
/*     */                   int signatures = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   for (int i = 0; i < signatures; i++) {
/*     */                     wrapper.read(Type.STRING);
/*     */                     wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                   } 
/*     */                 });
/* 205 */             read((Type)Type.BOOLEAN);
/*     */           }
/*     */         });
/* 208 */     cancelServerbound(ServerboundPackets1_19.CHAT_PREVIEW);
/*     */ 
/*     */     
/* 211 */     registerClientbound(State.LOGIN, ClientboundLoginPackets.GAME_PROFILE.getId(), ClientboundLoginPackets.GAME_PROFILE.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 214 */             map(Type.UUID);
/* 215 */             map(Type.STRING);
/* 216 */             create((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */           }
/*     */         });
/*     */     
/* 220 */     registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO.getId(), ClientboundLoginPackets.HELLO.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 223 */             map(Type.STRING);
/* 224 */             handler(wrapper -> {
/*     */                   byte[] publicKey = (byte[])wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                   
/*     */                   byte[] nonce = (byte[])wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                   wrapper.user().put((StorableObject)new NonceStorage(CipherUtil.encryptNonce(publicKey, nonce)));
/*     */                 });
/*     */           }
/*     */         });
/* 232 */     registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO.getId(), ServerboundLoginPackets.HELLO.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 235 */             map(Type.STRING);
/* 236 */             read(Type.OPTIONAL_PROFILE_KEY);
/*     */           }
/*     */         });
/*     */     
/* 240 */     registerServerbound(State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY.getId(), ServerboundLoginPackets.ENCRYPTION_KEY.getId(), (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 243 */             map(Type.BYTE_ARRAY_PRIMITIVE);
/* 244 */             handler(wrapper -> {
/*     */                   if (((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue()) {
/*     */                     wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                   } else {
/*     */                     NonceStorage nonceStorage = (NonceStorage)wrapper.user().remove(NonceStorage.class);
/*     */                     if (nonceStorage == null) {
/*     */                       throw new IllegalArgumentException("Server sent nonce is missing");
/*     */                     }
/*     */                     wrapper.read((Type)Type.LONG);
/*     */                     wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                     wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, nonceStorage.nonce());
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long randomLong() {
/* 265 */     return ThreadLocalRandom.current().nextLong();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onMappingDataLoaded() {
/* 270 */     super.onMappingDataLoaded();
/* 271 */     Types1_19.PARTICLE.filler((Protocol)this)
/* 272 */       .reader("block", ParticleType.Readers.BLOCK)
/* 273 */       .reader("block_marker", ParticleType.Readers.BLOCK)
/* 274 */       .reader("dust", ParticleType.Readers.DUST)
/* 275 */       .reader("falling_dust", ParticleType.Readers.BLOCK)
/* 276 */       .reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION)
/* 277 */       .reader("item", ParticleType.Readers.VAR_INT_ITEM)
/* 278 */       .reader("vibration", ParticleType.Readers.VIBRATION)
/* 279 */       .reader("sculk_charge", ParticleType.Readers.SCULK_CHARGE)
/* 280 */       .reader("shriek", ParticleType.Readers.SHRIEK);
/* 281 */     Entity1_19Types.initialize((Protocol)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void register(ViaProviders providers) {
/* 286 */     providers.register(AckSequenceProvider.class, (Provider)new AckSequenceProvider());
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection user) {
/* 291 */     if (!user.has(DimensionRegistryStorage.class)) {
/* 292 */       user.put((StorableObject)new DimensionRegistryStorage());
/*     */     }
/* 294 */     user.put((StorableObject)new SequenceStorage());
/* 295 */     addEntityTracker(user, (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_19Types.PLAYER));
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingData getMappingData() {
/* 300 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets getEntityRewriter() {
/* 305 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryPackets getItemRewriter() {
/* 310 */     return this.itemRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19to1_18_2\Protocol1_19To1_18_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
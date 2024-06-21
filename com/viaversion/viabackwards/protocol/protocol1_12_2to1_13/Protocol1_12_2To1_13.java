/*     */ package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13;
/*     */ 
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*     */ import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.BackwardsMappings;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.PaintingMapping;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.PlayerPacket1_13;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.SoundPackets1_13;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.PlayerPositionStorage1_13;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.TabCompleteStorage;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.libs.gson.JsonParser;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Protocol1_12_2To1_13
/*     */   extends BackwardsProtocol<ClientboundPackets1_13, ClientboundPackets1_12_1, ServerboundPackets1_13, ServerboundPackets1_12_1>
/*     */ {
/*  54 */   public static final BackwardsMappings MAPPINGS = new BackwardsMappings();
/*  55 */   private final EntityPackets1_13 entityRewriter = new EntityPackets1_13(this);
/*  56 */   private final BlockItemPackets1_13 blockItemPackets = new BlockItemPackets1_13(this);
/*  57 */   private final TranslatableRewriter<ClientboundPackets1_13> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_13>(this)
/*     */     {
/*     */       protected void handleTranslate(JsonObject root, String translate) {
/*  60 */         String newTranslate = mappedTranslationKey(translate);
/*  61 */         if (newTranslate != null || (newTranslate = (String)Protocol1_12_2To1_13.this.getMappingData().getTranslateMappings().get(translate)) != null)
/*  62 */           root.addProperty("translate", newTranslate); 
/*     */       }
/*     */     };
/*     */   
/*  66 */   private final TranslatableRewriter<ClientboundPackets1_13> translatableToLegacyRewriter = new TranslatableRewriter<ClientboundPackets1_13>(this)
/*     */     {
/*     */       protected void handleTranslate(JsonObject root, String translate) {
/*  69 */         String newTranslate = mappedTranslationKey(translate);
/*  70 */         if (newTranslate != null || (newTranslate = (String)Protocol1_12_2To1_13.this.getMappingData().getTranslateMappings().get(translate)) != null) {
/*  71 */           root.addProperty("translate", (String)Protocol1_13To1_12_2.MAPPINGS.getMojangTranslation().getOrDefault(newTranslate, newTranslate));
/*     */         }
/*     */       }
/*     */     };
/*     */   
/*     */   public Protocol1_12_2To1_13() {
/*  77 */     super(ClientboundPackets1_13.class, ClientboundPackets1_12_1.class, ServerboundPackets1_13.class, ServerboundPackets1_12_1.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  82 */     executeAsyncAfterLoaded(Protocol1_13To1_12_2.class, () -> {
/*     */           MAPPINGS.load();
/*     */           
/*     */           PaintingMapping.init();
/*     */           Via.getManager().getProviders().register(BackwardsBlockEntityProvider.class, (Provider)new BackwardsBlockEntityProvider());
/*     */         });
/*  88 */     this.translatableRewriter.registerPing();
/*  89 */     this.translatableRewriter.registerBossBar((ClientboundPacketType)ClientboundPackets1_13.BOSSBAR);
/*  90 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_13.CHAT_MESSAGE);
/*  91 */     this.translatableRewriter.registerLegacyOpenWindow((ClientboundPacketType)ClientboundPackets1_13.OPEN_WINDOW);
/*  92 */     this.translatableRewriter.registerDisconnect((ClientboundPacketType)ClientboundPackets1_13.DISCONNECT);
/*  93 */     this.translatableRewriter.registerCombatEvent((ClientboundPacketType)ClientboundPackets1_13.COMBAT_EVENT);
/*  94 */     this.translatableRewriter.registerTitle((ClientboundPacketType)ClientboundPackets1_13.TITLE);
/*  95 */     this.translatableRewriter.registerTabList((ClientboundPacketType)ClientboundPackets1_13.TAB_LIST);
/*     */     
/*  97 */     this.blockItemPackets.register();
/*  98 */     this.entityRewriter.register();
/*  99 */     (new PlayerPacket1_13(this)).register();
/* 100 */     (new SoundPackets1_13(this)).register();
/*     */     
/* 102 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_13.NBT_QUERY);
/* 103 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_13.CRAFT_RECIPE_RESPONSE);
/* 104 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_13.UNLOCK_RECIPES);
/* 105 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_13.ADVANCEMENTS);
/* 106 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_13.DECLARE_RECIPES);
/* 107 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_13.TAGS);
/*     */     
/* 109 */     cancelServerbound((ServerboundPacketType)ServerboundPackets1_12_1.CRAFT_RECIPE_REQUEST);
/* 110 */     cancelServerbound((ServerboundPacketType)ServerboundPackets1_12_1.RECIPE_BOOK_DATA);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(UserConnection user) {
/* 116 */     if (!user.has(ClientWorld.class)) {
/* 117 */       user.put((StorableObject)new ClientWorld(user));
/*     */     }
/*     */     
/* 120 */     user.addEntityTracker(getClass(), (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_13Types.EntityType.PLAYER));
/*     */     
/* 122 */     user.put((StorableObject)new BackwardsBlockStorage());
/* 123 */     user.put((StorableObject)new TabCompleteStorage());
/*     */     
/* 125 */     if (ViaBackwards.getConfig().isFix1_13FacePlayer() && !user.has(PlayerPositionStorage1_13.class)) {
/* 126 */       user.put((StorableObject)new PlayerPositionStorage1_13());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public BackwardsMappings getMappingData() {
/* 132 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets1_13 getEntityRewriter() {
/* 137 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockItemPackets1_13 getItemRewriter() {
/* 142 */     return this.blockItemPackets;
/*     */   }
/*     */ 
/*     */   
/*     */   public TranslatableRewriter<ClientboundPackets1_13> translatableRewriter() {
/* 147 */     return this.translatableRewriter;
/*     */   }
/*     */   
/*     */   public String jsonToLegacy(String value) {
/* 151 */     if (value.isEmpty()) {
/* 152 */       return "";
/*     */     }
/*     */     
/*     */     try {
/* 156 */       return jsonToLegacy(JsonParser.parseString(value));
/* 157 */     } catch (Exception e) {
/* 158 */       e.printStackTrace();
/*     */       
/* 160 */       return "";
/*     */     } 
/*     */   }
/*     */   public String jsonToLegacy(JsonElement value) {
/* 164 */     if (value == null || value.isJsonNull()) {
/* 165 */       return "";
/*     */     }
/*     */     
/* 168 */     this.translatableToLegacyRewriter.processText(value);
/*     */     
/*     */     try {
/* 171 */       Component component = ChatRewriter.HOVER_GSON_SERIALIZER.deserializeFromTree(value);
/* 172 */       return LegacyComponentSerializer.legacySection().serialize(component);
/* 173 */     } catch (Exception e) {
/* 174 */       ViaBackwards.getPlatform().getLogger().warning("Error converting json text to legacy: " + value);
/* 175 */       e.printStackTrace();
/*     */       
/* 177 */       return "";
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_12_2to1_13\Protocol1_12_2To1_13.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
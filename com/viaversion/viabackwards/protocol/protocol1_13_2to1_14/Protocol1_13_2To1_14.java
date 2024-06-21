/*     */ package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*     */ import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.data.CommandRewriter1_14;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.PlayerPackets1_14;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.SoundPackets1_14;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.ChunkLightStorage;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.DifficultyStorage;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.rewriter.StatisticsRewriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Protocol1_13_2To1_14
/*     */   extends BackwardsProtocol<ClientboundPackets1_14, ClientboundPackets1_13, ServerboundPackets1_14, ServerboundPackets1_13>
/*     */ {
/*  44 */   public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.14", "1.13.2", Protocol1_14To1_13_2.class);
/*  45 */   private final EntityPackets1_14 entityRewriter = new EntityPackets1_14(this);
/*  46 */   private final BlockItemPackets1_14 blockItemPackets = new BlockItemPackets1_14(this);
/*  47 */   private final TranslatableRewriter<ClientboundPackets1_14> translatableRewriter = new TranslatableRewriter(this);
/*     */   
/*     */   public Protocol1_13_2To1_14() {
/*  50 */     super(ClientboundPackets1_14.class, ClientboundPackets1_13.class, ServerboundPackets1_14.class, ServerboundPackets1_13.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  55 */     super.registerPackets();
/*     */     
/*  57 */     this.translatableRewriter.registerBossBar((ClientboundPacketType)ClientboundPackets1_14.BOSSBAR);
/*  58 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_14.CHAT_MESSAGE);
/*  59 */     this.translatableRewriter.registerCombatEvent((ClientboundPacketType)ClientboundPackets1_14.COMBAT_EVENT);
/*  60 */     this.translatableRewriter.registerDisconnect((ClientboundPacketType)ClientboundPackets1_14.DISCONNECT);
/*  61 */     this.translatableRewriter.registerTabList((ClientboundPacketType)ClientboundPackets1_14.TAB_LIST);
/*  62 */     this.translatableRewriter.registerTitle((ClientboundPacketType)ClientboundPackets1_14.TITLE);
/*  63 */     this.translatableRewriter.registerPing();
/*     */     
/*  65 */     (new CommandRewriter1_14(this)).registerDeclareCommands((ClientboundPacketType)ClientboundPackets1_14.DECLARE_COMMANDS);
/*  66 */     (new PlayerPackets1_14(this)).register();
/*  67 */     (new SoundPackets1_14(this)).register();
/*     */     
/*  69 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_14.STATISTICS);
/*     */     
/*  71 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_14.UPDATE_VIEW_POSITION);
/*  72 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_14.UPDATE_VIEW_DISTANCE);
/*  73 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_14.ACKNOWLEDGE_PLAYER_DIGGING);
/*     */     
/*  75 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_14.TAGS, wrapper -> {
/*     */           int blockTagsSize = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           for (int i = 0; i < blockTagsSize; i++) {
/*     */             wrapper.passthrough(Type.STRING);
/*     */             
/*     */             int[] blockIds = (int[])wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
/*     */             
/*     */             for (int n = 0; n < blockIds.length; n++) {
/*     */               int id = blockIds[n];
/*     */               
/*     */               int blockId = MAPPINGS.getNewBlockId(id);
/*     */               
/*     */               blockIds[n] = blockId;
/*     */             } 
/*     */           } 
/*     */           
/*     */           int itemTagsSize = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           for (int j = 0; j < itemTagsSize; j++) {
/*     */             wrapper.passthrough(Type.STRING);
/*     */             int[] itemIds = (int[])wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
/*     */             for (int n = 0; n < itemIds.length; n++) {
/*     */               int itemId = itemIds[n];
/*     */               int oldId = MAPPINGS.getItemMappings().getNewId(itemId);
/*     */               itemIds[n] = oldId;
/*     */             } 
/*     */           } 
/*     */           int fluidTagsSize = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           for (int k = 0; k < fluidTagsSize; k++) {
/*     */             wrapper.passthrough(Type.STRING);
/*     */             wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
/*     */           } 
/*     */           int entityTagsSize = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           for (int m = 0; m < entityTagsSize; m++) {
/*     */             wrapper.read(Type.STRING);
/*     */             wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
/*     */           } 
/*     */         });
/* 114 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_14.UPDATE_LIGHT, null, wrapper -> {
/*     */           int x = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           int z = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           int skyLightMask = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           int blockLightMask = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           int emptySkyLightMask = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           int emptyBlockLightMask = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           byte[][] skyLight = new byte[16][];
/*     */           if (isSet(skyLightMask, 0)) {
/*     */             wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
/*     */           }
/*     */           for (int i = 0; i < 16; i++) {
/*     */             if (isSet(skyLightMask, i + 1)) {
/*     */               skyLight[i] = (byte[])wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
/*     */             } else if (isSet(emptySkyLightMask, i + 1)) {
/*     */               skyLight[i] = ChunkLightStorage.EMPTY_LIGHT;
/*     */             } 
/*     */           } 
/*     */           if (isSet(skyLightMask, 17)) {
/*     */             wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
/*     */           }
/*     */           byte[][] blockLight = new byte[16][];
/*     */           if (isSet(blockLightMask, 0)) {
/*     */             wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
/*     */           }
/*     */           for (int j = 0; j < 16; j++) {
/*     */             if (isSet(blockLightMask, j + 1)) {
/*     */               blockLight[j] = (byte[])wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
/*     */             } else if (isSet(emptyBlockLightMask, j + 1)) {
/*     */               blockLight[j] = ChunkLightStorage.EMPTY_LIGHT;
/*     */             } 
/*     */           } 
/*     */           if (isSet(blockLightMask, 17)) {
/*     */             wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
/*     */           }
/*     */           ((ChunkLightStorage)wrapper.user().get(ChunkLightStorage.class)).setStoredLight(skyLight, blockLight, x, z);
/*     */           wrapper.cancel();
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isSet(int mask, int i) {
/* 160 */     return ((mask & 1 << i) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(UserConnection user) {
/* 166 */     if (!user.has(ClientWorld.class)) {
/* 167 */       user.put((StorableObject)new ClientWorld(user));
/*     */     }
/*     */     
/* 170 */     user.addEntityTracker(getClass(), (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_14Types.PLAYER));
/*     */     
/* 172 */     if (!user.has(ChunkLightStorage.class)) {
/* 173 */       user.put((StorableObject)new ChunkLightStorage(user));
/*     */     }
/*     */     
/* 176 */     user.put((StorableObject)new DifficultyStorage(user));
/*     */   }
/*     */ 
/*     */   
/*     */   public BackwardsMappings getMappingData() {
/* 181 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets1_14 getEntityRewriter() {
/* 186 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockItemPackets1_14 getItemRewriter() {
/* 191 */     return this.blockItemPackets;
/*     */   }
/*     */ 
/*     */   
/*     */   public TranslatableRewriter<ClientboundPackets1_14> getTranslatableRewriter() {
/* 196 */     return this.translatableRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_13_2to1_14\Protocol1_13_2To1_14.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
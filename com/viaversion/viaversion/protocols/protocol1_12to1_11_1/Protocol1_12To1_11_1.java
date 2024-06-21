/*     */ package com.viaversion.viaversion.protocols.protocol1_12to1_11_1;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_12Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*     */ import com.viaversion.viaversion.api.platform.providers.ViaProviders;
/*     */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_12;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.metadata.MetadataRewriter1_12To1_11_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.packets.InventoryPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.providers.InventoryQuickMoveProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*     */ import com.viaversion.viaversion.rewriter.SoundRewriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Protocol1_12To1_11_1
/*     */   extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_12, ServerboundPackets1_9_3, ServerboundPackets1_12>
/*     */ {
/*  52 */   private final MetadataRewriter1_12To1_11_1 metadataRewriter = new MetadataRewriter1_12To1_11_1(this);
/*  53 */   private final InventoryPackets itemRewriter = new InventoryPackets(this);
/*     */   
/*     */   public Protocol1_12To1_11_1() {
/*  56 */     super(ClientboundPackets1_9_3.class, ClientboundPackets1_12.class, ServerboundPackets1_9_3.class, ServerboundPackets1_12.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  61 */     this.metadataRewriter.register();
/*  62 */     this.itemRewriter.register();
/*     */     
/*  64 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.SPAWN_ENTITY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  67 */             map((Type)Type.VAR_INT);
/*  68 */             map(Type.UUID);
/*  69 */             map((Type)Type.BYTE);
/*     */ 
/*     */             
/*  72 */             handler(Protocol1_12To1_11_1.this.metadataRewriter.objectTrackerHandler());
/*     */           }
/*     */         });
/*     */     
/*  76 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  79 */             map((Type)Type.VAR_INT);
/*  80 */             map(Type.UUID);
/*  81 */             map((Type)Type.VAR_INT);
/*  82 */             map((Type)Type.DOUBLE);
/*  83 */             map((Type)Type.DOUBLE);
/*  84 */             map((Type)Type.DOUBLE);
/*  85 */             map((Type)Type.BYTE);
/*  86 */             map((Type)Type.BYTE);
/*  87 */             map((Type)Type.BYTE);
/*  88 */             map((Type)Type.SHORT);
/*  89 */             map((Type)Type.SHORT);
/*  90 */             map((Type)Type.SHORT);
/*  91 */             map(Types1_12.METADATA_LIST);
/*     */ 
/*     */             
/*  94 */             handler(Protocol1_12To1_11_1.this.metadataRewriter.trackerAndRewriterHandler(Types1_12.METADATA_LIST));
/*     */           }
/*     */         });
/*     */     
/*  98 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.CHAT_MESSAGE, wrapper -> {
/*     */           if (!Via.getConfig().is1_12NBTArrayFix())
/*     */             return;  try {
/*     */             JsonElement obj = (JsonElement)Protocol1_9To1_8.FIX_JSON.transform(null, ((JsonElement)wrapper.passthrough(Type.COMPONENT)).toString());
/*     */             TranslateRewriter.toClient(obj, wrapper.user());
/*     */             ChatItemRewriter.toClient(obj, wrapper.user());
/*     */             wrapper.set(Type.COMPONENT, 0, obj);
/* 105 */           } catch (Exception e) {
/*     */             e.printStackTrace();
/*     */           } 
/*     */         });
/*     */     
/* 110 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.CHUNK_DATA, wrapper -> {
/*     */           ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */           
/*     */           Chunk1_9_3_4Type type = new Chunk1_9_3_4Type(clientWorld);
/*     */           
/*     */           Chunk chunk = (Chunk)wrapper.passthrough((Type)type);
/*     */           
/*     */           for (int s = 0; s < (chunk.getSections()).length; s++) {
/*     */             ChunkSection section = chunk.getSections()[s];
/*     */             
/*     */             if (section != null) {
/*     */               DataPalette blocks = section.palette(PaletteType.BLOCKS);
/*     */               
/*     */               for (int idx = 0; idx < 4096; idx++) {
/*     */                 int id = blocks.idAt(idx) >> 4;
/*     */                 
/*     */                 if (id == 26) {
/*     */                   CompoundTag tag = new CompoundTag();
/*     */                   
/*     */                   tag.put("color", (Tag)new IntTag(14));
/*     */                   tag.put("x", (Tag)new IntTag(ChunkSection.xFromIndex(idx) + (chunk.getX() << 4)));
/*     */                   tag.put("y", (Tag)new IntTag(ChunkSection.yFromIndex(idx) + (s << 4)));
/*     */                   tag.put("z", (Tag)new IntTag(ChunkSection.zFromIndex(idx) + (chunk.getZ() << 4)));
/*     */                   tag.put("id", (Tag)new StringTag("minecraft:bed"));
/*     */                   chunk.getBlockEntities().add(tag);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         });
/* 140 */     this.metadataRewriter.registerRemoveEntities((ClientboundPacketType)ClientboundPackets1_9_3.DESTROY_ENTITIES);
/* 141 */     this.metadataRewriter.registerMetadataRewriter((ClientboundPacketType)ClientboundPackets1_9_3.ENTITY_METADATA, Types1_12.METADATA_LIST);
/*     */     
/* 143 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 146 */             map((Type)Type.INT);
/* 147 */             map((Type)Type.UNSIGNED_BYTE);
/* 148 */             map((Type)Type.INT);
/* 149 */             handler(wrapper -> {
/*     */                   UserConnection user = wrapper.user();
/*     */                   
/*     */                   ClientWorld clientChunks = (ClientWorld)user.get(ClientWorld.class);
/*     */                   
/*     */                   int dimensionId = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   
/*     */                   clientChunks.setEnvironment(dimensionId);
/*     */                   if (user.getProtocolInfo().getProtocolVersion() >= ProtocolVersion.v1_13.getVersion()) {
/*     */                     wrapper.create((PacketType)ClientboundPackets1_13.DECLARE_RECIPES, ()).scheduleSend(Protocol1_13To1_12_2.class);
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 163 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 166 */             map((Type)Type.INT);
/* 167 */             handler(wrapper -> {
/*     */                   ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   
/*     */                   int dimensionId = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   clientWorld.setEnvironment(dimensionId);
/*     */                 });
/*     */           }
/*     */         });
/* 175 */     (new SoundRewriter((Protocol)this, this::getNewSoundId)).registerSound((ClientboundPacketType)ClientboundPackets1_9_3.SOUND);
/*     */ 
/*     */ 
/*     */     
/* 179 */     cancelServerbound(ServerboundPackets1_12.PREPARE_CRAFTING_GRID);
/*     */ 
/*     */     
/* 182 */     registerServerbound(ServerboundPackets1_12.CLIENT_SETTINGS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 185 */             map(Type.STRING);
/* 186 */             map((Type)Type.BYTE);
/* 187 */             map((Type)Type.VAR_INT);
/* 188 */             map((Type)Type.BOOLEAN);
/* 189 */             map((Type)Type.UNSIGNED_BYTE);
/* 190 */             map((Type)Type.VAR_INT);
/* 191 */             handler(wrapper -> {
/*     */                   String locale = (String)wrapper.get(Type.STRING, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   if (locale.length() > 7) {
/*     */                     wrapper.set(Type.STRING, 0, locale.substring(0, 7));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 210 */     cancelServerbound(ServerboundPackets1_12.RECIPE_BOOK_DATA);
/*     */ 
/*     */     
/* 213 */     cancelServerbound(ServerboundPackets1_12.ADVANCEMENT_TAB);
/*     */   }
/*     */   
/*     */   private int getNewSoundId(int id) {
/* 217 */     int newId = id;
/* 218 */     if (id >= 26)
/* 219 */       newId += 2; 
/* 220 */     if (id >= 70)
/* 221 */       newId += 4; 
/* 222 */     if (id >= 74)
/* 223 */       newId++; 
/* 224 */     if (id >= 143)
/* 225 */       newId += 3; 
/* 226 */     if (id >= 185)
/* 227 */       newId++; 
/* 228 */     if (id >= 263)
/* 229 */       newId += 7; 
/* 230 */     if (id >= 301)
/* 231 */       newId += 33; 
/* 232 */     if (id >= 317)
/* 233 */       newId += 2; 
/* 234 */     if (id >= 491)
/* 235 */       newId += 3; 
/* 236 */     return newId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void register(ViaProviders providers) {
/* 241 */     providers.register(InventoryQuickMoveProvider.class, (Provider)new InventoryQuickMoveProvider());
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection userConnection) {
/* 246 */     userConnection.addEntityTracker(getClass(), (EntityTracker)new EntityTrackerBase(userConnection, (EntityType)Entity1_12Types.EntityType.PLAYER));
/* 247 */     if (!userConnection.has(ClientWorld.class)) {
/* 248 */       userConnection.put((StorableObject)new ClientWorld(userConnection));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public MetadataRewriter1_12To1_11_1 getEntityRewriter() {
/* 254 */     return this.metadataRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryPackets getItemRewriter() {
/* 259 */     return this.itemRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_12to1_11_1\Protocol1_12To1_11_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
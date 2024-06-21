/*     */ package com.viaversion.viaversion.protocols.protocol1_10to1_9_3;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_10to1_9_3.packets.InventoryPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_10to1_9_3.storage.ResourcePackTracker;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
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
/*     */ public class Protocol1_10To1_9_3_4
/*     */   extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3>
/*     */ {
/*  43 */   public static final ValueTransformer<Short, Float> TO_NEW_PITCH = new ValueTransformer<Short, Float>((Type)Type.FLOAT)
/*     */     {
/*     */       public Float transform(PacketWrapper wrapper, Short inputValue) throws Exception {
/*  46 */         return Float.valueOf(inputValue.shortValue() / 63.0F);
/*     */       }
/*     */     };
/*  49 */   public static final ValueTransformer<List<Metadata>, List<Metadata>> TRANSFORM_METADATA = new ValueTransformer<List<Metadata>, List<Metadata>>(Types1_9.METADATA_LIST)
/*     */     {
/*     */       public List<Metadata> transform(PacketWrapper wrapper, List<Metadata> inputValue) throws Exception {
/*  52 */         List<Metadata> metaList = new CopyOnWriteArrayList<>(inputValue);
/*  53 */         for (Metadata m : metaList) {
/*  54 */           if (m.id() >= 5)
/*  55 */             m.setId(m.id() + 1); 
/*     */         } 
/*  57 */         return metaList;
/*     */       }
/*     */     };
/*  60 */   private final InventoryPackets itemRewriter = new InventoryPackets(this);
/*     */   
/*     */   public Protocol1_10To1_9_3_4() {
/*  63 */     super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  68 */     this.itemRewriter.register();
/*     */ 
/*     */     
/*  71 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.NAMED_SOUND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  74 */             map(Type.STRING);
/*  75 */             map((Type)Type.VAR_INT);
/*  76 */             map((Type)Type.INT);
/*  77 */             map((Type)Type.INT);
/*  78 */             map((Type)Type.INT);
/*  79 */             map((Type)Type.FLOAT);
/*  80 */             map((Type)Type.UNSIGNED_BYTE, Protocol1_10To1_9_3_4.TO_NEW_PITCH);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  85 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.SOUND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  88 */             map((Type)Type.VAR_INT);
/*  89 */             map((Type)Type.VAR_INT);
/*  90 */             map((Type)Type.INT);
/*  91 */             map((Type)Type.INT);
/*  92 */             map((Type)Type.INT);
/*  93 */             map((Type)Type.FLOAT);
/*  94 */             map((Type)Type.UNSIGNED_BYTE, Protocol1_10To1_9_3_4.TO_NEW_PITCH);
/*     */             
/*  96 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(Protocol1_10To1_9_3_4.this.getNewSoundId(id)));
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 104 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.ENTITY_METADATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 107 */             map((Type)Type.VAR_INT);
/* 108 */             map(Types1_9.METADATA_LIST, Protocol1_10To1_9_3_4.TRANSFORM_METADATA);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 113 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.SPAWN_MOB, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 116 */             map((Type)Type.VAR_INT);
/* 117 */             map(Type.UUID);
/* 118 */             map((Type)Type.UNSIGNED_BYTE);
/* 119 */             map((Type)Type.DOUBLE);
/* 120 */             map((Type)Type.DOUBLE);
/* 121 */             map((Type)Type.DOUBLE);
/* 122 */             map((Type)Type.BYTE);
/* 123 */             map((Type)Type.BYTE);
/* 124 */             map((Type)Type.BYTE);
/* 125 */             map((Type)Type.SHORT);
/* 126 */             map((Type)Type.SHORT);
/* 127 */             map((Type)Type.SHORT);
/* 128 */             map(Types1_9.METADATA_LIST, Protocol1_10To1_9_3_4.TRANSFORM_METADATA);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 133 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.SPAWN_PLAYER, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 136 */             map((Type)Type.VAR_INT);
/* 137 */             map(Type.UUID);
/* 138 */             map((Type)Type.DOUBLE);
/* 139 */             map((Type)Type.DOUBLE);
/* 140 */             map((Type)Type.DOUBLE);
/* 141 */             map((Type)Type.BYTE);
/* 142 */             map((Type)Type.BYTE);
/* 143 */             map(Types1_9.METADATA_LIST, Protocol1_10To1_9_3_4.TRANSFORM_METADATA);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 148 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 151 */             map((Type)Type.INT);
/* 152 */             map((Type)Type.UNSIGNED_BYTE);
/* 153 */             map((Type)Type.INT);
/*     */             
/* 155 */             handler(wrapper -> {
/*     */                   ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   
/*     */                   int dimensionId = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   
/*     */                   clientWorld.setEnvironment(dimensionId);
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 165 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 168 */             map((Type)Type.INT);
/*     */             
/* 170 */             handler(wrapper -> {
/*     */                   ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   
/*     */                   int dimensionId = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   clientWorld.setEnvironment(dimensionId);
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 180 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.CHUNK_DATA, wrapper -> {
/*     */           ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */           
/*     */           Chunk chunk = (Chunk)wrapper.passthrough((Type)new Chunk1_9_3_4Type(clientWorld));
/*     */           
/*     */           if (Via.getConfig().isReplacePistons()) {
/*     */             int replacementId = Via.getConfig().getPistonReplacementId();
/*     */             for (ChunkSection section : chunk.getSections()) {
/*     */               if (section != null) {
/*     */                 section.palette(PaletteType.BLOCKS).replaceId(36, replacementId);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         });
/* 194 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.RESOURCE_PACK, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 197 */             map(Type.STRING);
/* 198 */             map(Type.STRING);
/*     */             
/* 200 */             handler(wrapper -> {
/*     */                   ResourcePackTracker tracker = (ResourcePackTracker)wrapper.user().get(ResourcePackTracker.class);
/*     */                   
/*     */                   tracker.setLastHash((String)wrapper.get(Type.STRING, 1));
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 208 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_9_3.RESOURCE_PACK_STATUS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 211 */             handler(wrapper -> {
/*     */                   ResourcePackTracker tracker = (ResourcePackTracker)wrapper.user().get(ResourcePackTracker.class);
/*     */                   wrapper.write(Type.STRING, tracker.getLastHash());
/*     */                   wrapper.write((Type)Type.VAR_INT, wrapper.read((Type)Type.VAR_INT));
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public int getNewSoundId(int id) {
/* 221 */     int newId = id;
/* 222 */     if (id >= 24)
/* 223 */       newId++; 
/* 224 */     if (id >= 248)
/* 225 */       newId += 4; 
/* 226 */     if (id >= 296)
/* 227 */       newId += 6; 
/* 228 */     if (id >= 354)
/* 229 */       newId += 4; 
/* 230 */     if (id >= 372)
/* 231 */       newId += 4; 
/* 232 */     return newId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection userConnection) {
/* 237 */     userConnection.put((StorableObject)new ResourcePackTracker());
/*     */     
/* 239 */     if (!userConnection.has(ClientWorld.class)) {
/* 240 */       userConnection.put((StorableObject)new ClientWorld(userConnection));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryPackets getItemRewriter() {
/* 246 */     return this.itemRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_10to1_9_3\Protocol1_10To1_9_3_4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viaversion.protocols.protocol1_13_1to1_13;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.MappingDataBase;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.RegistryType;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.metadata.MetadataRewriter1_13_1To1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.EntityPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.InventoryPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.WorldPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.rewriter.StatisticsRewriter;
/*     */ import com.viaversion.viaversion.rewriter.TagRewriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Protocol1_13_1To1_13
/*     */   extends AbstractProtocol<ClientboundPackets1_13, ClientboundPackets1_13, ServerboundPackets1_13, ServerboundPackets1_13>
/*     */ {
/*  44 */   public static final MappingData MAPPINGS = (MappingData)new MappingDataBase("1.13", "1.13.2");
/*  45 */   private final MetadataRewriter1_13_1To1_13 entityRewriter = new MetadataRewriter1_13_1To1_13(this);
/*  46 */   private final InventoryPackets itemRewriter = new InventoryPackets(this);
/*     */   
/*     */   public Protocol1_13_1To1_13() {
/*  49 */     super(ClientboundPackets1_13.class, ClientboundPackets1_13.class, ServerboundPackets1_13.class, ServerboundPackets1_13.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  54 */     this.entityRewriter.register();
/*  55 */     this.itemRewriter.register();
/*     */     
/*  57 */     EntityPackets.register(this);
/*  58 */     WorldPackets.register(this);
/*     */     
/*  60 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_13.TAB_COMPLETE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  63 */             map((Type)Type.VAR_INT);
/*  64 */             map(Type.STRING, new ValueTransformer<String, String>(Type.STRING)
/*     */                 {
/*     */                   public String transform(PacketWrapper wrapper, String inputValue)
/*     */                   {
/*  68 */                     return inputValue.startsWith("/") ? inputValue.substring(1) : inputValue;
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/*  74 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_13.EDIT_BOOK, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  77 */             map(Type.FLAT_ITEM);
/*  78 */             map((Type)Type.BOOLEAN);
/*  79 */             handler(wrapper -> {
/*     */                   Item item = (Item)wrapper.get(Type.FLAT_ITEM, 0);
/*     */                   Protocol1_13_1To1_13.this.itemRewriter.handleItemToServer(item);
/*     */                 });
/*  83 */             handler(wrapper -> {
/*     */                   int hand = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   if (hand == 1) {
/*     */                     wrapper.cancel();
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*  92 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_13.TAB_COMPLETE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  95 */             map((Type)Type.VAR_INT);
/*  96 */             map((Type)Type.VAR_INT);
/*  97 */             map((Type)Type.VAR_INT);
/*  98 */             map((Type)Type.VAR_INT);
/*  99 */             handler(wrapper -> {
/*     */                   int start = ((Integer)wrapper.get((Type)Type.VAR_INT, 1)).intValue();
/*     */                   
/*     */                   wrapper.set((Type)Type.VAR_INT, 1, Integer.valueOf(start + 1));
/*     */                   
/*     */                   int count = ((Integer)wrapper.get((Type)Type.VAR_INT, 3)).intValue();
/*     */                   for (int i = 0; i < count; i++) {
/*     */                     wrapper.passthrough(Type.STRING);
/*     */                     boolean hasTooltip = ((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue();
/*     */                     if (hasTooltip) {
/*     */                       wrapper.passthrough(Type.STRING);
/*     */                     }
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 115 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_13.BOSSBAR, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 118 */             map(Type.UUID);
/* 119 */             map((Type)Type.VAR_INT);
/* 120 */             handler(wrapper -> {
/*     */                   int action = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   if (action == 0) {
/*     */                     wrapper.passthrough(Type.COMPONENT);
/*     */                     wrapper.passthrough((Type)Type.FLOAT);
/*     */                     wrapper.passthrough((Type)Type.VAR_INT);
/*     */                     wrapper.passthrough((Type)Type.VAR_INT);
/*     */                     short flags = (short)((Byte)wrapper.read((Type)Type.BYTE)).byteValue();
/*     */                     if ((flags & 0x2) != 0)
/*     */                       flags = (short)(flags | 0x4); 
/*     */                     wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf(flags));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 135 */     (new TagRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_13.TAGS, RegistryType.ITEM);
/* 136 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_13.STATISTICS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection userConnection) {
/* 141 */     userConnection.addEntityTracker(getClass(), (EntityTracker)new EntityTrackerBase(userConnection, (EntityType)Entity1_13Types.EntityType.PLAYER));
/* 142 */     if (!userConnection.has(ClientWorld.class)) {
/* 143 */       userConnection.put((StorableObject)new ClientWorld(userConnection));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingData getMappingData() {
/* 149 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public MetadataRewriter1_13_1To1_13 getEntityRewriter() {
/* 154 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryPackets getItemRewriter() {
/* 159 */     return this.itemRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13_1to1_13\Protocol1_13_1To1_13.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
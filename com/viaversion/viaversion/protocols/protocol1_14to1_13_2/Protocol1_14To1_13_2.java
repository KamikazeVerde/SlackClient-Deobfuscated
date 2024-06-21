/*     */ package com.viaversion.viaversion.protocols.protocol1_14to1_13_2;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
/*     */ import com.viaversion.viaversion.api.type.types.version.Types1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.ComponentRewriter1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.MappingData;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.metadata.MetadataRewriter1_14To1_13_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.EntityPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.InventoryPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.PlayerPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.rewriter.CommandRewriter;
/*     */ import com.viaversion.viaversion.rewriter.SoundRewriter;
/*     */ import com.viaversion.viaversion.rewriter.StatisticsRewriter;
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
/*     */ public class Protocol1_14To1_13_2
/*     */   extends AbstractProtocol<ClientboundPackets1_13, ClientboundPackets1_14, ServerboundPackets1_13, ServerboundPackets1_14>
/*     */ {
/*  45 */   public static final MappingData MAPPINGS = new MappingData();
/*  46 */   private final MetadataRewriter1_14To1_13_2 metadataRewriter = new MetadataRewriter1_14To1_13_2(this);
/*  47 */   private final InventoryPackets itemRewriter = new InventoryPackets(this);
/*     */   
/*     */   public Protocol1_14To1_13_2() {
/*  50 */     super(ClientboundPackets1_13.class, ClientboundPackets1_14.class, ServerboundPackets1_13.class, ServerboundPackets1_14.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  55 */     this.metadataRewriter.register();
/*  56 */     this.itemRewriter.register();
/*     */     
/*  58 */     EntityPackets.register(this);
/*  59 */     WorldPackets.register(this);
/*  60 */     PlayerPackets.register(this);
/*     */     
/*  62 */     (new SoundRewriter((Protocol)this)).registerSound((ClientboundPacketType)ClientboundPackets1_13.SOUND);
/*  63 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_13.STATISTICS);
/*     */     
/*  65 */     ComponentRewriter1_14 componentRewriter1_14 = new ComponentRewriter1_14((Protocol)this);
/*  66 */     componentRewriter1_14.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_13.CHAT_MESSAGE);
/*     */     
/*  68 */     CommandRewriter<ClientboundPackets1_13> commandRewriter = new CommandRewriter<ClientboundPackets1_13>((Protocol)this)
/*     */       {
/*     */         public String handleArgumentType(String argumentType) {
/*  71 */           if (argumentType.equals("minecraft:nbt")) {
/*  72 */             return "minecraft:nbt_compound_tag";
/*     */           }
/*  74 */           return super.handleArgumentType(argumentType);
/*     */         }
/*     */       };
/*  77 */     commandRewriter.registerDeclareCommands((ClientboundPacketType)ClientboundPackets1_13.DECLARE_COMMANDS);
/*     */     
/*  79 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_13.TAGS, wrapper -> {
/*     */           int blockTagsSize = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(blockTagsSize + 6));
/*     */           
/*     */           for (int i = 0; i < blockTagsSize; i++) {
/*     */             wrapper.passthrough(Type.STRING);
/*     */             
/*     */             int[] blockIds = (int[])wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
/*     */             
/*     */             for (int m = 0; m < blockIds.length; m++) {
/*     */               blockIds[m] = MAPPINGS.getNewBlockId(blockIds[m]);
/*     */             }
/*     */           } 
/*     */           
/*     */           wrapper.write(Type.STRING, "minecraft:signs");
/*     */           
/*     */           wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { MAPPINGS.getNewBlockId(150), MAPPINGS.getNewBlockId(155) });
/*     */           
/*     */           wrapper.write(Type.STRING, "minecraft:wall_signs");
/*     */           
/*     */           wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { MAPPINGS.getNewBlockId(155) });
/*     */           
/*     */           wrapper.write(Type.STRING, "minecraft:standing_signs");
/*     */           
/*     */           wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { MAPPINGS.getNewBlockId(150) });
/*     */           
/*     */           wrapper.write(Type.STRING, "minecraft:fences");
/*     */           
/*     */           wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { 189, 248, 472, 473, 474, 475 });
/*     */           
/*     */           wrapper.write(Type.STRING, "minecraft:walls");
/*     */           
/*     */           wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { 271, 272 });
/*     */           wrapper.write(Type.STRING, "minecraft:wooden_fences");
/*     */           wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { 189, 472, 473, 474, 475 });
/*     */           int itemTagsSize = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(itemTagsSize + 2));
/*     */           for (int j = 0; j < itemTagsSize; j++) {
/*     */             wrapper.passthrough(Type.STRING);
/*     */             int[] itemIds = (int[])wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
/*     */             for (int m = 0; m < itemIds.length; m++) {
/*     */               itemIds[m] = MAPPINGS.getNewItemId(itemIds[m]);
/*     */             }
/*     */           } 
/*     */           wrapper.write(Type.STRING, "minecraft:signs");
/*     */           wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { MAPPINGS.getNewItemId(541) });
/*     */           wrapper.write(Type.STRING, "minecraft:arrows");
/*     */           wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { 526, 825, 826 });
/*     */           int fluidTagsSize = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           for (int k = 0; k < fluidTagsSize; k++) {
/*     */             wrapper.passthrough(Type.STRING);
/*     */             wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
/*     */           } 
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */         });
/* 135 */     cancelServerbound(ServerboundPackets1_14.SET_DIFFICULTY);
/*     */     
/* 137 */     cancelServerbound(ServerboundPackets1_14.LOCK_DIFFICULTY);
/*     */     
/* 139 */     cancelServerbound(ServerboundPackets1_14.UPDATE_JIGSAW_BLOCK);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onMappingDataLoaded() {
/* 144 */     WorldPackets.air = MAPPINGS.getBlockStateMappings().getNewId(0);
/* 145 */     WorldPackets.voidAir = MAPPINGS.getBlockStateMappings().getNewId(8591);
/* 146 */     WorldPackets.caveAir = MAPPINGS.getBlockStateMappings().getNewId(8592);
/*     */     
/* 148 */     Types1_13_2.PARTICLE.filler((Protocol)this, false)
/* 149 */       .reader("block", ParticleType.Readers.BLOCK)
/* 150 */       .reader("dust", ParticleType.Readers.DUST)
/* 151 */       .reader("falling_dust", ParticleType.Readers.BLOCK)
/* 152 */       .reader("item", ParticleType.Readers.VAR_INT_ITEM);
/* 153 */     Types1_14.PARTICLE.filler((Protocol)this)
/* 154 */       .reader("block", ParticleType.Readers.BLOCK)
/* 155 */       .reader("dust", ParticleType.Readers.DUST)
/* 156 */       .reader("falling_dust", ParticleType.Readers.BLOCK)
/* 157 */       .reader("item", ParticleType.Readers.VAR_INT_ITEM);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection userConnection) {
/* 162 */     userConnection.addEntityTracker(getClass(), (EntityTracker)new EntityTracker1_14(userConnection));
/* 163 */     if (!userConnection.has(ClientWorld.class)) {
/* 164 */       userConnection.put((StorableObject)new ClientWorld(userConnection));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingData getMappingData() {
/* 170 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public MetadataRewriter1_14To1_13_2 getEntityRewriter() {
/* 175 */     return this.metadataRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryPackets getItemRewriter() {
/* 180 */     return this.itemRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_14to1_13_2\Protocol1_14To1_13_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
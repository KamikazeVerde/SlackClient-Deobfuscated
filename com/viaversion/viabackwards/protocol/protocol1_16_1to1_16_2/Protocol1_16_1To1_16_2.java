/*     */ package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*     */ import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
/*     */ import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.data.CommandRewriter1_16_2;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets.BlockItemPackets1_16_2;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets.EntityPackets1_16_2;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.storage.BiomeStorage;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.RegistryType;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
/*     */ import com.viaversion.viaversion.rewriter.StatisticsRewriter;
/*     */ import com.viaversion.viaversion.rewriter.TagRewriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Protocol1_16_1To1_16_2
/*     */   extends BackwardsProtocol<ClientboundPackets1_16_2, ClientboundPackets1_16, ServerboundPackets1_16_2, ServerboundPackets1_16>
/*     */ {
/*  45 */   public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.16.2", "1.16", Protocol1_16_2To1_16_1.class);
/*  46 */   private final EntityPackets1_16_2 entityRewriter = new EntityPackets1_16_2(this);
/*  47 */   private final BlockItemPackets1_16_2 blockItemPackets = new BlockItemPackets1_16_2(this);
/*  48 */   private final TranslatableRewriter<ClientboundPackets1_16_2> translatableRewriter = new TranslatableRewriter(this);
/*     */   
/*     */   public Protocol1_16_1To1_16_2() {
/*  51 */     super(ClientboundPackets1_16_2.class, ClientboundPackets1_16.class, ServerboundPackets1_16_2.class, ServerboundPackets1_16.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  56 */     super.registerPackets();
/*     */     
/*  58 */     this.translatableRewriter.registerBossBar((ClientboundPacketType)ClientboundPackets1_16_2.BOSSBAR);
/*  59 */     this.translatableRewriter.registerCombatEvent((ClientboundPacketType)ClientboundPackets1_16_2.COMBAT_EVENT);
/*  60 */     this.translatableRewriter.registerDisconnect((ClientboundPacketType)ClientboundPackets1_16_2.DISCONNECT);
/*  61 */     this.translatableRewriter.registerTabList((ClientboundPacketType)ClientboundPackets1_16_2.TAB_LIST);
/*  62 */     this.translatableRewriter.registerTitle((ClientboundPacketType)ClientboundPackets1_16_2.TITLE);
/*  63 */     this.translatableRewriter.registerOpenWindow((ClientboundPacketType)ClientboundPackets1_16_2.OPEN_WINDOW);
/*  64 */     this.translatableRewriter.registerPing();
/*     */     
/*  66 */     (new CommandRewriter1_16_2(this)).registerDeclareCommands((ClientboundPacketType)ClientboundPackets1_16_2.DECLARE_COMMANDS);
/*     */     
/*  68 */     SoundRewriter<ClientboundPackets1_16_2> soundRewriter = new SoundRewriter(this);
/*  69 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_16_2.SOUND);
/*  70 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_16_2.ENTITY_SOUND);
/*  71 */     soundRewriter.registerNamedSound((ClientboundPacketType)ClientboundPackets1_16_2.NAMED_SOUND);
/*  72 */     soundRewriter.registerStopSound((ClientboundPacketType)ClientboundPackets1_16_2.STOP_SOUND);
/*     */     
/*  74 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.CHAT_MESSAGE, wrapper -> {
/*     */           JsonElement message = (JsonElement)wrapper.passthrough(Type.COMPONENT);
/*     */           
/*     */           this.translatableRewriter.processText(message);
/*     */           
/*     */           byte position = ((Byte)wrapper.passthrough((Type)Type.BYTE)).byteValue();
/*     */           if (position == 2) {
/*     */             wrapper.clearPacket();
/*     */             wrapper.setPacketType((PacketType)ClientboundPackets1_16.TITLE);
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(2));
/*     */             wrapper.write(Type.COMPONENT, message);
/*     */           } 
/*     */         });
/*  87 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_16.RECIPE_BOOK_DATA, wrapper -> {
/*     */           int type = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           if (type == 0) {
/*     */             wrapper.passthrough(Type.STRING);
/*     */             
/*     */             wrapper.setPacketType((PacketType)ServerboundPackets1_16_2.SEEN_RECIPE);
/*     */           } else {
/*     */             wrapper.cancel();
/*     */             
/*     */             for (int i = 0; i < 3; i++) {
/*     */               sendSeenRecipePacket(i, wrapper);
/*     */             }
/*     */           } 
/*     */         });
/*     */     
/* 103 */     (new TagRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_16_2.TAGS, RegistryType.ENTITY);
/*     */     
/* 105 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_16_2.STATISTICS);
/*     */   }
/*     */   
/*     */   private static void sendSeenRecipePacket(int recipeType, PacketWrapper wrapper) throws Exception {
/* 109 */     boolean open = ((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue();
/* 110 */     boolean filter = ((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue();
/*     */     
/* 112 */     PacketWrapper newPacket = wrapper.create((PacketType)ServerboundPackets1_16_2.RECIPE_BOOK_DATA);
/* 113 */     newPacket.write((Type)Type.VAR_INT, Integer.valueOf(recipeType));
/* 114 */     newPacket.write((Type)Type.BOOLEAN, Boolean.valueOf(open));
/* 115 */     newPacket.write((Type)Type.BOOLEAN, Boolean.valueOf(filter));
/* 116 */     newPacket.sendToServer(Protocol1_16_1To1_16_2.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection user) {
/* 121 */     user.put((StorableObject)new BiomeStorage());
/* 122 */     user.addEntityTracker(getClass(), (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_16_2Types.PLAYER));
/*     */   }
/*     */ 
/*     */   
/*     */   public TranslatableRewriter<ClientboundPackets1_16_2> getTranslatableRewriter() {
/* 127 */     return this.translatableRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public BackwardsMappings getMappingData() {
/* 132 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets1_16_2 getEntityRewriter() {
/* 137 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockItemPackets1_16_2 getItemRewriter() {
/* 142 */     return this.blockItemPackets;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_16_1to1_16_2\Protocol1_16_1To1_16_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
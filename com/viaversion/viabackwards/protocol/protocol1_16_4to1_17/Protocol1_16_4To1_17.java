/*     */ package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17;
/*     */ 
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*     */ import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
/*     */ import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.BlockItemPackets1_17;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.EntityPackets1_17;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.PingRequests;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.PlayerLastCursorItem;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.RegistryType;
/*     */ import com.viaversion.viaversion.api.minecraft.TagData;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
/*     */ import com.viaversion.viaversion.rewriter.IdRewriteFunction;
/*     */ import com.viaversion.viaversion.rewriter.StatisticsRewriter;
/*     */ import com.viaversion.viaversion.rewriter.TagRewriter;
/*     */ import com.viaversion.viaversion.util.Key;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Protocol1_16_4To1_17
/*     */   extends BackwardsProtocol<ClientboundPackets1_17, ClientboundPackets1_16_2, ServerboundPackets1_17, ServerboundPackets1_16_2>
/*     */ {
/*  55 */   public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.17", "1.16.2", Protocol1_17To1_16_4.class);
/*  56 */   private static final RegistryType[] TAG_REGISTRY_TYPES = new RegistryType[] { RegistryType.BLOCK, RegistryType.ITEM, RegistryType.FLUID, RegistryType.ENTITY };
/*  57 */   private static final int[] EMPTY_ARRAY = new int[0];
/*  58 */   private final EntityPackets1_17 entityRewriter = new EntityPackets1_17(this);
/*  59 */   private final BlockItemPackets1_17 blockItemPackets = new BlockItemPackets1_17(this);
/*  60 */   private final TranslatableRewriter<ClientboundPackets1_17> translatableRewriter = new TranslatableRewriter(this);
/*     */   
/*     */   public Protocol1_16_4To1_17() {
/*  63 */     super(ClientboundPackets1_17.class, ClientboundPackets1_16_2.class, ServerboundPackets1_17.class, ServerboundPackets1_16_2.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  68 */     super.registerPackets();
/*     */     
/*  70 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_17.CHAT_MESSAGE);
/*  71 */     this.translatableRewriter.registerBossBar((ClientboundPacketType)ClientboundPackets1_17.BOSSBAR);
/*  72 */     this.translatableRewriter.registerDisconnect((ClientboundPacketType)ClientboundPackets1_17.DISCONNECT);
/*  73 */     this.translatableRewriter.registerTabList((ClientboundPacketType)ClientboundPackets1_17.TAB_LIST);
/*  74 */     this.translatableRewriter.registerOpenWindow((ClientboundPacketType)ClientboundPackets1_17.OPEN_WINDOW);
/*  75 */     this.translatableRewriter.registerPing();
/*     */     
/*  77 */     SoundRewriter<ClientboundPackets1_17> soundRewriter = new SoundRewriter(this);
/*  78 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_17.SOUND);
/*  79 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_17.ENTITY_SOUND);
/*  80 */     soundRewriter.registerNamedSound((ClientboundPacketType)ClientboundPackets1_17.NAMED_SOUND);
/*  81 */     soundRewriter.registerStopSound((ClientboundPacketType)ClientboundPackets1_17.STOP_SOUND);
/*     */     
/*  83 */     TagRewriter<ClientboundPackets1_17> tagRewriter = new TagRewriter((Protocol)this);
/*  84 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_17.TAGS, wrapper -> {
/*     */           Map<String, List<TagData>> tags = new HashMap<>();
/*     */           
/*     */           int length = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           for (int i = 0; i < length; i++) {
/*     */             String resourceKey = Key.stripMinecraftNamespace((String)wrapper.read(Type.STRING));
/*     */             
/*     */             List<TagData> tagList = new ArrayList<>();
/*     */             
/*     */             tags.put(resourceKey, tagList);
/*     */             
/*     */             int tagLength = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */             
/*     */             for (int j = 0; j < tagLength; j++) {
/*     */               String identifier = (String)wrapper.read(Type.STRING);
/*     */               
/*     */               int[] entries = (int[])wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
/*     */               
/*     */               tagList.add(new TagData(identifier, entries));
/*     */             } 
/*     */           } 
/*     */           
/*     */           for (RegistryType type : TAG_REGISTRY_TYPES) {
/*     */             List<TagData> tagList = tags.get(type.resourceLocation());
/*     */             
/*     */             if (tagList == null) {
/*     */               wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */             } else {
/*     */               IdRewriteFunction rewriter = tagRewriter.getRewriter(type);
/*     */               wrapper.write((Type)Type.VAR_INT, Integer.valueOf(tagList.size()));
/*     */               for (TagData tagData : tagList) {
/*     */                 int[] entries = tagData.entries();
/*     */                 if (rewriter != null) {
/*     */                   IntArrayList intArrayList = new IntArrayList(entries.length);
/*     */                   for (int id : entries) {
/*     */                     int mappedId = rewriter.rewrite(id);
/*     */                     if (mappedId != -1) {
/*     */                       intArrayList.add(mappedId);
/*     */                     }
/*     */                   } 
/*     */                   entries = intArrayList.toArray(EMPTY_ARRAY);
/*     */                 } 
/*     */                 wrapper.write(Type.STRING, tagData.identifier());
/*     */                 wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, entries);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         });
/* 133 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_17.STATISTICS);
/*     */     
/* 135 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_17.RESOURCE_PACK, wrapper -> {
/*     */           wrapper.passthrough(Type.STRING);
/*     */           
/*     */           wrapper.passthrough(Type.STRING);
/*     */           wrapper.read((Type)Type.BOOLEAN);
/*     */           wrapper.read(Type.OPTIONAL_COMPONENT);
/*     */         });
/* 142 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_17.EXPLOSION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 145 */             map((Type)Type.FLOAT);
/* 146 */             map((Type)Type.FLOAT);
/* 147 */             map((Type)Type.FLOAT);
/* 148 */             map((Type)Type.FLOAT);
/* 149 */             handler(wrapper -> wrapper.write((Type)Type.INT, wrapper.read((Type)Type.VAR_INT)));
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 155 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_17.SPAWN_POSITION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 158 */             map(Type.POSITION1_14);
/* 159 */             handler(wrapper -> wrapper.read((Type)Type.FLOAT));
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_17.PING, null, wrapper -> {
/*     */           wrapper.cancel();
/*     */           
/*     */           int id = ((Integer)wrapper.read((Type)Type.INT)).intValue();
/*     */           
/*     */           short shortId = (short)id;
/*     */           
/*     */           if (id == shortId && ViaBackwards.getConfig().handlePingsAsInvAcknowledgements()) {
/*     */             ((PingRequests)wrapper.user().get(PingRequests.class)).addId(shortId);
/*     */             
/*     */             PacketWrapper acknowledgementPacket = wrapper.create((PacketType)ClientboundPackets1_16_2.WINDOW_CONFIRMATION);
/*     */             
/*     */             acknowledgementPacket.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)0));
/*     */             
/*     */             acknowledgementPacket.write((Type)Type.SHORT, Short.valueOf(shortId));
/*     */             acknowledgementPacket.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */             acknowledgementPacket.send(Protocol1_16_4To1_17.class);
/*     */             return;
/*     */           } 
/*     */           PacketWrapper pongPacket = wrapper.create((PacketType)ServerboundPackets1_17.PONG);
/*     */           pongPacket.write((Type)Type.INT, Integer.valueOf(id));
/*     */           pongPacket.sendToServer(Protocol1_16_4To1_17.class);
/*     */         });
/* 189 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_16_2.CLIENT_SETTINGS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 192 */             map(Type.STRING);
/* 193 */             map((Type)Type.BYTE);
/* 194 */             map((Type)Type.VAR_INT);
/* 195 */             map((Type)Type.BOOLEAN);
/* 196 */             map((Type)Type.UNSIGNED_BYTE);
/* 197 */             map((Type)Type.VAR_INT);
/* 198 */             handler(wrapper -> wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false)));
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     mergePacket(ClientboundPackets1_17.TITLE_TEXT, ClientboundPackets1_16_2.TITLE, 0);
/* 206 */     mergePacket(ClientboundPackets1_17.TITLE_SUBTITLE, ClientboundPackets1_16_2.TITLE, 1);
/* 207 */     mergePacket(ClientboundPackets1_17.ACTIONBAR, ClientboundPackets1_16_2.TITLE, 2);
/* 208 */     mergePacket(ClientboundPackets1_17.TITLE_TIMES, ClientboundPackets1_16_2.TITLE, 3);
/* 209 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_17.CLEAR_TITLES, (ClientboundPacketType)ClientboundPackets1_16_2.TITLE, wrapper -> {
/*     */           if (((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue()) {
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(5));
/*     */           } else {
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(4));
/*     */           } 
/*     */         });
/*     */     
/* 217 */     cancelClientbound((ClientboundPacketType)ClientboundPackets1_17.ADD_VIBRATION_SIGNAL);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection user) {
/* 222 */     addEntityTracker(user, (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_17Types.PLAYER));
/* 223 */     user.put((StorableObject)new PingRequests());
/* 224 */     user.put((StorableObject)new PlayerLastCursorItem());
/*     */   }
/*     */ 
/*     */   
/*     */   public BackwardsMappings getMappingData() {
/* 229 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public TranslatableRewriter<ClientboundPackets1_17> getTranslatableRewriter() {
/* 234 */     return this.translatableRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public void mergePacket(ClientboundPackets1_17 newPacketType, ClientboundPackets1_16_2 oldPacketType, int type) {
/* 239 */     registerClientbound((ClientboundPacketType)newPacketType, (ClientboundPacketType)oldPacketType, wrapper -> wrapper.write((Type)Type.VAR_INT, Integer.valueOf(type)));
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets1_17 getEntityRewriter() {
/* 244 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockItemPackets1_17 getItemRewriter() {
/* 249 */     return this.blockItemPackets;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_16_4to1_17\Protocol1_16_4To1_17.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
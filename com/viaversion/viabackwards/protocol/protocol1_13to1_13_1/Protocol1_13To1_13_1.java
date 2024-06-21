/*     */ package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1;
/*     */ 
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*     */ import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.data.CommandRewriter1_13_1;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets.EntityPackets1_13_1;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets.InventoryPackets1_13_1;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets.WorldPackets1_13_1;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.MappingData;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.RegistryType;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
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
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
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
/*     */ public class Protocol1_13To1_13_1
/*     */   extends BackwardsProtocol<ClientboundPackets1_13, ClientboundPackets1_13, ServerboundPackets1_13, ServerboundPackets1_13>
/*     */ {
/*  49 */   public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.13.2", "1.13", Protocol1_13_1To1_13.class);
/*  50 */   private final EntityPackets1_13_1 entityRewriter = new EntityPackets1_13_1(this);
/*  51 */   private final InventoryPackets1_13_1 itemRewriter = new InventoryPackets1_13_1(this);
/*  52 */   private final TranslatableRewriter<ClientboundPackets1_13> translatableRewriter = new TranslatableRewriter(this);
/*     */   
/*     */   public Protocol1_13To1_13_1() {
/*  55 */     super(ClientboundPackets1_13.class, ClientboundPackets1_13.class, ServerboundPackets1_13.class, ServerboundPackets1_13.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  60 */     super.registerPackets();
/*     */     
/*  62 */     WorldPackets1_13_1.register(this);
/*     */     
/*  64 */     this.translatableRewriter.registerComponentPacket((ClientboundPacketType)ClientboundPackets1_13.CHAT_MESSAGE);
/*  65 */     this.translatableRewriter.registerCombatEvent((ClientboundPacketType)ClientboundPackets1_13.COMBAT_EVENT);
/*  66 */     this.translatableRewriter.registerDisconnect((ClientboundPacketType)ClientboundPackets1_13.DISCONNECT);
/*  67 */     this.translatableRewriter.registerTabList((ClientboundPacketType)ClientboundPackets1_13.TAB_LIST);
/*  68 */     this.translatableRewriter.registerTitle((ClientboundPacketType)ClientboundPackets1_13.TITLE);
/*  69 */     this.translatableRewriter.registerPing();
/*     */     
/*  71 */     (new CommandRewriter1_13_1(this)).registerDeclareCommands((ClientboundPacketType)ClientboundPackets1_13.DECLARE_COMMANDS);
/*     */     
/*  73 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_13.TAB_COMPLETE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  76 */             map((Type)Type.VAR_INT);
/*  77 */             map(Type.STRING, new ValueTransformer<String, String>(Type.STRING)
/*     */                 {
/*     */                   public String transform(PacketWrapper wrapper, String inputValue)
/*     */                   {
/*  81 */                     return !inputValue.startsWith("/") ? ("/" + inputValue) : inputValue;
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/*  87 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_13.EDIT_BOOK, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  90 */             map(Type.FLAT_ITEM);
/*  91 */             map((Type)Type.BOOLEAN);
/*  92 */             handler(wrapper -> {
/*     */                   Protocol1_13To1_13_1.this.itemRewriter.handleItemToServer((Item)wrapper.get(Type.FLAT_ITEM, 0));
/*     */                   
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                 });
/*     */           }
/*     */         });
/*  99 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_13.OPEN_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 102 */             map((Type)Type.UNSIGNED_BYTE);
/* 103 */             map(Type.STRING);
/* 104 */             handler(wrapper -> {
/*     */                   JsonElement title = (JsonElement)wrapper.passthrough(Type.COMPONENT);
/*     */                   
/*     */                   Protocol1_13To1_13_1.this.translatableRewriter.processText(title);
/*     */                   
/*     */                   if (ViaBackwards.getConfig().fix1_13FormattedInventoryTitle()) {
/*     */                     if (title.isJsonObject() && title.getAsJsonObject().size() == 1 && title.getAsJsonObject().has("translate")) {
/*     */                       return;
/*     */                     }
/*     */                     
/*     */                     JsonObject legacyComponent = new JsonObject();
/*     */                     
/*     */                     legacyComponent.addProperty("text", ChatRewriter.jsonToLegacyText(title.toString()));
/*     */                     
/*     */                     wrapper.set(Type.COMPONENT, 0, legacyComponent);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 124 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_13.TAB_COMPLETE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 127 */             map((Type)Type.VAR_INT);
/* 128 */             map((Type)Type.VAR_INT);
/* 129 */             map((Type)Type.VAR_INT);
/* 130 */             map((Type)Type.VAR_INT);
/* 131 */             handler(wrapper -> {
/*     */                   int start = ((Integer)wrapper.get((Type)Type.VAR_INT, 1)).intValue();
/*     */                   
/*     */                   wrapper.set((Type)Type.VAR_INT, 1, Integer.valueOf(start - 1));
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
/* 147 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_13.BOSSBAR, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 150 */             map(Type.UUID);
/* 151 */             map((Type)Type.VAR_INT);
/* 152 */             handler(wrapper -> {
/*     */                   int action = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   if (action == 0 || action == 3) {
/*     */                     Protocol1_13To1_13_1.this.translatableRewriter.processText((JsonElement)wrapper.passthrough(Type.COMPONENT));
/*     */                     if (action == 0) {
/*     */                       wrapper.passthrough((Type)Type.FLOAT);
/*     */                       wrapper.passthrough((Type)Type.VAR_INT);
/*     */                       wrapper.passthrough((Type)Type.VAR_INT);
/*     */                       short flags = ((Short)wrapper.read((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                       if ((flags & 0x4) != 0)
/*     */                         flags = (short)(flags | 0x2); 
/*     */                       wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf(flags));
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 169 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_13.ADVANCEMENTS, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.BOOLEAN);
/*     */           
/*     */           int size = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           for (int i = 0; i < size; i++) {
/*     */             wrapper.passthrough(Type.STRING);
/*     */             
/*     */             if (((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue()) {
/*     */               wrapper.passthrough(Type.STRING);
/*     */             }
/*     */             
/*     */             if (((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue()) {
/*     */               wrapper.passthrough(Type.COMPONENT);
/*     */               
/*     */               wrapper.passthrough(Type.COMPONENT);
/*     */               
/*     */               Item icon = (Item)wrapper.passthrough(Type.FLAT_ITEM);
/*     */               this.itemRewriter.handleItemToClient(icon);
/*     */               wrapper.passthrough((Type)Type.VAR_INT);
/*     */               int flags = ((Integer)wrapper.passthrough((Type)Type.INT)).intValue();
/*     */               if ((flags & 0x1) != 0) {
/*     */                 wrapper.passthrough(Type.STRING);
/*     */               }
/*     */               wrapper.passthrough((Type)Type.FLOAT);
/*     */               wrapper.passthrough((Type)Type.FLOAT);
/*     */             } 
/*     */             wrapper.passthrough(Type.STRING_ARRAY);
/*     */             int arrayLength = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */             for (int array = 0; array < arrayLength; array++) {
/*     */               wrapper.passthrough(Type.STRING_ARRAY);
/*     */             }
/*     */           } 
/*     */         });
/* 203 */     (new TagRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_13.TAGS, RegistryType.ITEM);
/* 204 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_13.STATISTICS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection user) {
/* 209 */     user.addEntityTracker(getClass(), (EntityTracker)new EntityTrackerBase(user, (EntityType)Entity1_13Types.EntityType.PLAYER));
/*     */     
/* 211 */     if (!user.has(ClientWorld.class)) {
/* 212 */       user.put((StorableObject)new ClientWorld(user));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public BackwardsMappings getMappingData() {
/* 218 */     return MAPPINGS;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPackets1_13_1 getEntityRewriter() {
/* 223 */     return this.entityRewriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryPackets1_13_1 getItemRewriter() {
/* 228 */     return this.itemRewriter;
/*     */   }
/*     */   
/*     */   public TranslatableRewriter<ClientboundPackets1_13> translatableRewriter() {
/* 232 */     return this.translatableRewriter;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_13to1_13_1\Protocol1_13To1_13_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
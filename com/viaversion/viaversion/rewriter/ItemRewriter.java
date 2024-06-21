/*     */ package com.viaversion.viaversion.rewriter;
/*     */ 
/*     */ import com.viaversion.viaversion.api.data.Mappings;
/*     */ import com.viaversion.viaversion.api.data.ParticleMappings;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.api.rewriter.RewriterBase;
/*     */ import com.viaversion.viaversion.api.type.Type;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ItemRewriter<C extends ClientboundPacketType, S extends ServerboundPacketType, T extends Protocol<C, ?, ?, S>>
/*     */   extends RewriterBase<T>
/*     */   implements ItemRewriter<T>
/*     */ {
/*     */   protected ItemRewriter(T protocol) {
/*  36 */     super((Protocol)protocol);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item handleItemToClient(Item item) {
/*  43 */     if (item == null) return null; 
/*  44 */     if (this.protocol.getMappingData() != null && this.protocol.getMappingData().getItemMappings() != null) {
/*  45 */       item.setIdentifier(this.protocol.getMappingData().getNewItemId(item.identifier()));
/*     */     }
/*  47 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToServer(Item item) {
/*  52 */     if (item == null) return null; 
/*  53 */     if (this.protocol.getMappingData() != null && this.protocol.getMappingData().getItemMappings() != null) {
/*  54 */       item.setIdentifier(this.protocol.getMappingData().getOldItemId(item.identifier()));
/*     */     }
/*  56 */     return item;
/*     */   }
/*     */   
/*     */   public void registerWindowItems(C packetType, final Type<Item[]> type) {
/*  60 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  63 */             map((Type)Type.UNSIGNED_BYTE);
/*  64 */             map(type);
/*  65 */             handler(ItemRewriter.this.itemArrayHandler(type));
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void registerWindowItems1_17_1(C packetType) {
/*  71 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  74 */             map((Type)Type.UNSIGNED_BYTE);
/*  75 */             map((Type)Type.VAR_INT);
/*  76 */             handler(wrapper -> {
/*     */                   Item[] items = (Item[])wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
/*     */                   for (Item item : items) {
/*     */                     ItemRewriter.this.handleItemToClient(item);
/*     */                   }
/*     */                   ItemRewriter.this.handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerOpenWindow(C packetType) {
/*  89 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  92 */             map((Type)Type.VAR_INT);
/*  93 */             handler(wrapper -> {
/*     */                   int windowType = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   int mappedId = ItemRewriter.this.protocol.getMappingData().getMenuMappings().getNewId(windowType);
/*     */                   if (mappedId == -1) {
/*     */                     wrapper.cancel();
/*     */                     return;
/*     */                   } 
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(mappedId));
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerSetSlot(C packetType, final Type<Item> type) {
/* 108 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 111 */             map((Type)Type.UNSIGNED_BYTE);
/* 112 */             map((Type)Type.SHORT);
/* 113 */             map(type);
/* 114 */             handler(ItemRewriter.this.itemToClientHandler(type));
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void registerSetSlot1_17_1(C packetType) {
/* 120 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 123 */             map((Type)Type.UNSIGNED_BYTE);
/* 124 */             map((Type)Type.VAR_INT);
/* 125 */             map((Type)Type.SHORT);
/* 126 */             map(Type.FLAT_VAR_INT_ITEM);
/* 127 */             handler(ItemRewriter.this.itemToClientHandler(Type.FLAT_VAR_INT_ITEM));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerEntityEquipment(C packetType, final Type<Item> type) {
/* 134 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 137 */             map((Type)Type.VAR_INT);
/* 138 */             map((Type)Type.VAR_INT);
/* 139 */             map(type);
/*     */             
/* 141 */             handler(ItemRewriter.this.itemToClientHandler(type));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerEntityEquipmentArray(C packetType) {
/* 148 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 151 */             map((Type)Type.VAR_INT);
/*     */             
/* 153 */             handler(wrapper -> {
/*     */                   byte slot;
/*     */                   do {
/*     */                     slot = ((Byte)wrapper.passthrough((Type)Type.BYTE)).byteValue();
/*     */                     ItemRewriter.this.handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */                   } while ((slot & Byte.MIN_VALUE) != 0);
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerCreativeInvAction(S packetType, final Type<Item> type) {
/* 166 */     this.protocol.registerServerbound((ServerboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 169 */             map((Type)Type.SHORT);
/* 170 */             map(type);
/*     */             
/* 172 */             handler(ItemRewriter.this.itemToServerHandler(type));
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void registerClickWindow(S packetType, final Type<Item> type) {
/* 178 */     this.protocol.registerServerbound((ServerboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 181 */             map((Type)Type.UNSIGNED_BYTE);
/* 182 */             map((Type)Type.SHORT);
/* 183 */             map((Type)Type.BYTE);
/* 184 */             map((Type)Type.SHORT);
/* 185 */             map((Type)Type.VAR_INT);
/* 186 */             map(type);
/*     */             
/* 188 */             handler(ItemRewriter.this.itemToServerHandler(type));
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void registerClickWindow1_17_1(S packetType) {
/* 194 */     this.protocol.registerServerbound((ServerboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 197 */             map((Type)Type.UNSIGNED_BYTE);
/* 198 */             map((Type)Type.VAR_INT);
/* 199 */             map((Type)Type.SHORT);
/* 200 */             map((Type)Type.BYTE);
/* 201 */             map((Type)Type.VAR_INT);
/*     */             
/* 203 */             handler(wrapper -> {
/*     */                   int length = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */                   for (int i = 0; i < length; i++) {
/*     */                     wrapper.passthrough((Type)Type.SHORT);
/*     */                     ItemRewriter.this.handleItemToServer((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */                   } 
/*     */                   ItemRewriter.this.handleItemToServer((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerSetCooldown(C packetType) {
/* 219 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, wrapper -> {
/*     */           int itemId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(this.protocol.getMappingData().getNewItemId(itemId)));
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerTradeList(C packetType) {
/* 227 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */           int size = ((Short)wrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */           for (int i = 0; i < size; i++) {
/*     */             handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */             handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */             if (((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue()) {
/*     */               handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */             }
/*     */             wrapper.passthrough((Type)Type.BOOLEAN);
/*     */             wrapper.passthrough((Type)Type.INT);
/*     */             wrapper.passthrough((Type)Type.INT);
/*     */             wrapper.passthrough((Type)Type.INT);
/*     */             wrapper.passthrough((Type)Type.INT);
/*     */             wrapper.passthrough((Type)Type.FLOAT);
/*     */             wrapper.passthrough((Type)Type.INT);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerTradeList1_19(C packetType) {
/* 252 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */           int size = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           for (int i = 0; i < size; i++) {
/*     */             handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */             handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */             handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */             wrapper.passthrough((Type)Type.BOOLEAN);
/*     */             wrapper.passthrough((Type)Type.INT);
/*     */             wrapper.passthrough((Type)Type.INT);
/*     */             wrapper.passthrough((Type)Type.INT);
/*     */             wrapper.passthrough((Type)Type.INT);
/*     */             wrapper.passthrough((Type)Type.FLOAT);
/*     */             wrapper.passthrough((Type)Type.INT);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerAdvancements(C packetType, Type<Item> type) {
/* 273 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.BOOLEAN);
/*     */           int size = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           for (int i = 0; i < size; i++) {
/*     */             wrapper.passthrough(Type.STRING);
/*     */             if (((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue()) {
/*     */               wrapper.passthrough(Type.STRING);
/*     */             }
/*     */             if (((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue()) {
/*     */               wrapper.passthrough(Type.COMPONENT);
/*     */               wrapper.passthrough(Type.COMPONENT);
/*     */               handleItemToClient((Item)wrapper.passthrough(type));
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
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerWindowPropertyEnchantmentHandler(C packetType) {
/* 308 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 311 */             map((Type)Type.UNSIGNED_BYTE);
/* 312 */             handler(wrapper -> {
/*     */                   Mappings mappings = ItemRewriter.this.protocol.getMappingData().getEnchantmentMappings();
/*     */                   if (mappings == null) {
/*     */                     return;
/*     */                   }
/*     */                   short property = ((Short)wrapper.passthrough((Type)Type.SHORT)).shortValue();
/*     */                   if (property >= 4 && property <= 6) {
/*     */                     short enchantmentId = (short)mappings.getNewId(((Short)wrapper.read((Type)Type.SHORT)).shortValue());
/*     */                     wrapper.write((Type)Type.SHORT, Short.valueOf(enchantmentId));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerSpawnParticle(C packetType, final Type<Item> itemType, final Type<?> coordType) {
/* 330 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 333 */             map((Type)Type.INT);
/* 334 */             map((Type)Type.BOOLEAN);
/* 335 */             map(coordType);
/* 336 */             map(coordType);
/* 337 */             map(coordType);
/* 338 */             map((Type)Type.FLOAT);
/* 339 */             map((Type)Type.FLOAT);
/* 340 */             map((Type)Type.FLOAT);
/* 341 */             map((Type)Type.FLOAT);
/* 342 */             map((Type)Type.INT);
/* 343 */             handler(ItemRewriter.this.getSpawnParticleHandler(itemType));
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void registerSpawnParticle1_19(C packetType) {
/* 349 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 352 */             map((Type)Type.VAR_INT);
/* 353 */             map((Type)Type.BOOLEAN);
/* 354 */             map((Type)Type.DOUBLE);
/* 355 */             map((Type)Type.DOUBLE);
/* 356 */             map((Type)Type.DOUBLE);
/* 357 */             map((Type)Type.FLOAT);
/* 358 */             map((Type)Type.FLOAT);
/* 359 */             map((Type)Type.FLOAT);
/* 360 */             map((Type)Type.FLOAT);
/* 361 */             map((Type)Type.INT);
/* 362 */             handler(ItemRewriter.this.getSpawnParticleHandler((Type<Integer>)Type.VAR_INT, Type.FLAT_VAR_INT_ITEM));
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public PacketHandler getSpawnParticleHandler(Type<Item> itemType) {
/* 368 */     return getSpawnParticleHandler((Type<Integer>)Type.INT, itemType);
/*     */   }
/*     */   
/*     */   public PacketHandler getSpawnParticleHandler(Type<Integer> idType, Type<Item> itemType) {
/* 372 */     return wrapper -> {
/*     */         int id = ((Integer)wrapper.get(idType, 0)).intValue();
/*     */         if (id == -1) {
/*     */           return;
/*     */         }
/*     */         ParticleMappings mappings = this.protocol.getMappingData().getParticleMappings();
/*     */         if (mappings.isBlockParticle(id)) {
/*     */           int data = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(this.protocol.getMappingData().getNewBlockStateId(data)));
/*     */         } else if (mappings.isItemParticle(id)) {
/*     */           handleItemToClient((Item)wrapper.passthrough(itemType));
/*     */         } 
/*     */         int newId = this.protocol.getMappingData().getNewParticleId(id);
/*     */         if (newId != id) {
/*     */           wrapper.set(idType, 0, Integer.valueOf(newId));
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public PacketHandler itemArrayHandler(Type<Item[]> type) {
/* 393 */     return wrapper -> {
/*     */         Item[] items = (Item[])wrapper.get(type, 0);
/*     */         for (Item item : items) {
/*     */           handleItemToClient(item);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public PacketHandler itemToClientHandler(Type<Item> type) {
/* 402 */     return wrapper -> handleItemToClient((Item)wrapper.get(type, 0));
/*     */   }
/*     */   
/*     */   public PacketHandler itemToServerHandler(Type<Item> type) {
/* 406 */     return wrapper -> handleItemToServer((Item)wrapper.get(type, 0));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\rewriter\ItemRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.data.MappedLegacyBlockItem;
/*     */ import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter;
/*     */ import com.viaversion.viabackwards.api.rewriters.LegacyEnchantmentRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_10to1_11.Protocol1_10To1_11;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.ChestedHorseStorage;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage.WindowTracker;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.data.entity.StoredEntityData;
/*     */ import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_11Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.DataItem;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_11to1_10.EntityIdRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
/*     */ import java.util.Arrays;
/*     */ import java.util.Optional;
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
/*     */ public class BlockItemPackets1_11
/*     */   extends LegacyBlockItemRewriter<ClientboundPackets1_9_3, ServerboundPackets1_9_3, Protocol1_10To1_11>
/*     */ {
/*     */   private LegacyEnchantmentRewriter enchantmentRewriter;
/*     */   
/*     */   public BlockItemPackets1_11(Protocol1_10To1_11 protocol) {
/*  56 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  61 */     ((Protocol1_10To1_11)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.SET_SLOT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  64 */             map((Type)Type.UNSIGNED_BYTE);
/*  65 */             map((Type)Type.SHORT);
/*  66 */             map(Type.ITEM);
/*     */             
/*  68 */             handler(BlockItemPackets1_11.this.itemToClientHandler(Type.ITEM));
/*     */ 
/*     */             
/*  71 */             handler(new PacketHandler()
/*     */                 {
/*     */                   public void handle(PacketWrapper wrapper) throws Exception {
/*  74 */                     if (BlockItemPackets1_11.this.isLlama(wrapper.user())) {
/*  75 */                       Optional<ChestedHorseStorage> horse = BlockItemPackets1_11.this.getChestedHorse(wrapper.user());
/*  76 */                       if (!horse.isPresent())
/*     */                         return; 
/*  78 */                       ChestedHorseStorage storage = horse.get();
/*  79 */                       int currentSlot = ((Short)wrapper.get((Type)Type.SHORT, 0)).shortValue();
/*  80 */                       wrapper.set((Type)Type.SHORT, 0, Short.valueOf(Integer.valueOf(currentSlot = BlockItemPackets1_11.this.getNewSlotId(storage, currentSlot)).shortValue()));
/*  81 */                       wrapper.set(Type.ITEM, 0, BlockItemPackets1_11.this.getNewItem(storage, currentSlot, (Item)wrapper.get(Type.ITEM, 0)));
/*     */                     } 
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/*  88 */     ((Protocol1_10To1_11)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.WINDOW_ITEMS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  91 */             map((Type)Type.UNSIGNED_BYTE);
/*  92 */             map(Type.ITEM_ARRAY);
/*     */             
/*  94 */             handler(wrapper -> {
/*     */                   Item[] stacks = (Item[])wrapper.get(Type.ITEM_ARRAY, 0);
/*     */                   
/*     */                   for (int i = 0; i < stacks.length; i++) {
/*     */                     stacks[i] = BlockItemPackets1_11.this.handleItemToClient(stacks[i]);
/*     */                   }
/*     */                   if (BlockItemPackets1_11.this.isLlama(wrapper.user())) {
/*     */                     Optional<ChestedHorseStorage> horse = BlockItemPackets1_11.this.getChestedHorse(wrapper.user());
/*     */                     if (!horse.isPresent()) {
/*     */                       return;
/*     */                     }
/*     */                     ChestedHorseStorage storage = horse.get();
/*     */                     stacks = Arrays.<Item>copyOf(stacks, !storage.isChested() ? 38 : 53);
/*     */                     for (int j = stacks.length - 1; j >= 0; j--) {
/*     */                       stacks[BlockItemPackets1_11.this.getNewSlotId(storage, j)] = stacks[j];
/*     */                       stacks[j] = BlockItemPackets1_11.this.getNewItem(storage, j, stacks[j]);
/*     */                     } 
/*     */                     wrapper.set(Type.ITEM_ARRAY, 0, stacks);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 116 */     registerEntityEquipment((ClientboundPacketType)ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
/*     */ 
/*     */     
/* 119 */     ((Protocol1_10To1_11)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 122 */             map(Type.STRING);
/*     */             
/* 124 */             handler(wrapper -> {
/*     */                   if (((String)wrapper.get(Type.STRING, 0)).equalsIgnoreCase("MC|TrList")) {
/*     */                     wrapper.passthrough((Type)Type.INT);
/*     */                     
/*     */                     int size = ((Short)wrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                     
/*     */                     for (int i = 0; i < size; i++) {
/*     */                       wrapper.write(Type.ITEM, BlockItemPackets1_11.this.handleItemToClient((Item)wrapper.read(Type.ITEM)));
/*     */                       
/*     */                       wrapper.write(Type.ITEM, BlockItemPackets1_11.this.handleItemToClient((Item)wrapper.read(Type.ITEM)));
/*     */                       
/*     */                       boolean secondItem = ((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue();
/*     */                       if (secondItem) {
/*     */                         wrapper.write(Type.ITEM, BlockItemPackets1_11.this.handleItemToClient((Item)wrapper.read(Type.ITEM)));
/*     */                       }
/*     */                       wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                       wrapper.passthrough((Type)Type.INT);
/*     */                       wrapper.passthrough((Type)Type.INT);
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 147 */     ((Protocol1_10To1_11)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_9_3.CLICK_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 150 */             map((Type)Type.UNSIGNED_BYTE);
/* 151 */             map((Type)Type.SHORT);
/* 152 */             map((Type)Type.BYTE);
/* 153 */             map((Type)Type.SHORT);
/* 154 */             map((Type)Type.VAR_INT);
/* 155 */             map(Type.ITEM);
/*     */             
/* 157 */             handler(BlockItemPackets1_11.this.itemToServerHandler(Type.ITEM));
/*     */ 
/*     */             
/* 160 */             handler(wrapper -> {
/*     */                   if (BlockItemPackets1_11.this.isLlama(wrapper.user())) {
/*     */                     Optional<ChestedHorseStorage> horse = BlockItemPackets1_11.this.getChestedHorse(wrapper.user());
/*     */                     
/*     */                     if (!horse.isPresent()) {
/*     */                       return;
/*     */                     }
/*     */                     ChestedHorseStorage storage = horse.get();
/*     */                     int clickSlot = ((Short)wrapper.get((Type)Type.SHORT, 0)).shortValue();
/*     */                     int correctSlot = BlockItemPackets1_11.this.getOldSlotId(storage, clickSlot);
/*     */                     wrapper.set((Type)Type.SHORT, 0, Short.valueOf(Integer.valueOf(correctSlot).shortValue()));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 175 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
/*     */     
/* 177 */     ((Protocol1_10To1_11)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.CHUNK_DATA, wrapper -> {
/*     */           ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */           
/*     */           Chunk1_9_3_4Type type = new Chunk1_9_3_4Type(clientWorld);
/*     */           
/*     */           Chunk chunk = (Chunk)wrapper.passthrough((Type)type);
/*     */           
/*     */           handleChunk(chunk);
/*     */           
/*     */           for (CompoundTag tag : chunk.getBlockEntities()) {
/*     */             Tag idTag = tag.get("id");
/*     */             if (!(idTag instanceof StringTag)) {
/*     */               continue;
/*     */             }
/*     */             String id = (String)idTag.getValue();
/*     */             if (id.equals("minecraft:sign")) {
/*     */               ((StringTag)idTag).setValue("Sign");
/*     */             }
/*     */           } 
/*     */         });
/* 197 */     ((Protocol1_10To1_11)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 200 */             map(Type.POSITION);
/* 201 */             map((Type)Type.VAR_INT);
/*     */             
/* 203 */             handler(wrapper -> {
/*     */                   int idx = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(BlockItemPackets1_11.this.handleBlockID(idx)));
/*     */                 });
/*     */           }
/*     */         });
/* 210 */     ((Protocol1_10To1_11)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.MULTI_BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 213 */             map((Type)Type.INT);
/* 214 */             map((Type)Type.INT);
/* 215 */             map(Type.BLOCK_CHANGE_RECORD_ARRAY);
/*     */             
/* 217 */             handler(wrapper -> {
/*     */                   for (BlockChangeRecord record : (BlockChangeRecord[])wrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
/*     */                     record.setBlockId(BlockItemPackets1_11.this.handleBlockID(record.getBlockId()));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 225 */     ((Protocol1_10To1_11)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 228 */             map(Type.POSITION);
/* 229 */             map((Type)Type.UNSIGNED_BYTE);
/* 230 */             map(Type.NBT);
/*     */             
/* 232 */             handler(wrapper -> {
/*     */                   if (((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue() == 10) {
/*     */                     wrapper.cancel();
/*     */                   }
/*     */                   
/*     */                   if (((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue() == 1) {
/*     */                     CompoundTag tag = (CompoundTag)wrapper.get(Type.NBT, 0);
/*     */                     
/*     */                     EntityIdRewriter.toClientSpawner(tag, true);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 246 */     ((Protocol1_10To1_11)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.OPEN_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 249 */             map((Type)Type.UNSIGNED_BYTE);
/* 250 */             map(Type.STRING);
/* 251 */             map(Type.COMPONENT);
/* 252 */             map((Type)Type.UNSIGNED_BYTE);
/*     */             
/* 254 */             handler(wrapper -> {
/*     */                   int entityId = -1;
/*     */                   
/*     */                   if (((String)wrapper.get(Type.STRING, 0)).equals("EntityHorse")) {
/*     */                     entityId = ((Integer)wrapper.passthrough((Type)Type.INT)).intValue();
/*     */                   }
/*     */                   
/*     */                   String inventory = (String)wrapper.get(Type.STRING, 0);
/*     */                   
/*     */                   WindowTracker windowTracker = (WindowTracker)wrapper.user().get(WindowTracker.class);
/*     */                   
/*     */                   windowTracker.setInventory(inventory);
/*     */                   
/*     */                   windowTracker.setEntityId(entityId);
/*     */                   
/*     */                   if (BlockItemPackets1_11.this.isLlama(wrapper.user())) {
/*     */                     wrapper.set((Type)Type.UNSIGNED_BYTE, 1, Short.valueOf((short)17));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 275 */     ((Protocol1_10To1_11)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.CLOSE_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register()
/*     */           {
/* 279 */             handler(wrapper -> {
/*     */                   WindowTracker windowTracker = (WindowTracker)wrapper.user().get(WindowTracker.class);
/*     */                   
/*     */                   windowTracker.setInventory(null);
/*     */                   
/*     */                   windowTracker.setEntityId(-1);
/*     */                 });
/*     */           }
/*     */         });
/* 288 */     ((Protocol1_10To1_11)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_9_3.CLOSE_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register()
/*     */           {
/* 292 */             handler(wrapper -> {
/*     */                   WindowTracker windowTracker = (WindowTracker)wrapper.user().get(WindowTracker.class);
/*     */                   
/*     */                   windowTracker.setInventory(null);
/*     */                   windowTracker.setEntityId(-1);
/*     */                 });
/*     */           }
/*     */         });
/* 300 */     ((Protocol1_10To1_11)this.protocol).getEntityRewriter().filter().handler((event, meta) -> {
/*     */           if (meta.metaType().type().equals(Type.ITEM)) {
/*     */             meta.setValue(handleItemToClient((Item)meta.getValue()));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerRewrites() {
/* 309 */     MappedLegacyBlockItem data = (MappedLegacyBlockItem)this.replacementData.computeIfAbsent(52, s -> new MappedLegacyBlockItem(52, (short)-1, null, false));
/* 310 */     data.setBlockEntityHandler((b, tag) -> {
/*     */           EntityIdRewriter.toClientSpawner(tag, true);
/*     */           
/*     */           return tag;
/*     */         });
/* 315 */     this.enchantmentRewriter = new LegacyEnchantmentRewriter(this.nbtTagName);
/* 316 */     this.enchantmentRewriter.registerEnchantment(71, "§cCurse of Vanishing");
/* 317 */     this.enchantmentRewriter.registerEnchantment(10, "§cCurse of Binding");
/*     */     
/* 319 */     this.enchantmentRewriter.setHideLevelForEnchants(new int[] { 71, 10 });
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToClient(Item item) {
/* 324 */     if (item == null) return null; 
/* 325 */     super.handleItemToClient(item);
/*     */     
/* 327 */     CompoundTag tag = item.tag();
/* 328 */     if (tag == null) return item;
/*     */ 
/*     */     
/* 331 */     EntityIdRewriter.toClientItem(item, true);
/*     */     
/* 333 */     if (tag.get("ench") instanceof com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag) {
/* 334 */       this.enchantmentRewriter.rewriteEnchantmentsToClient(tag, false);
/*     */     }
/* 336 */     if (tag.get("StoredEnchantments") instanceof com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag) {
/* 337 */       this.enchantmentRewriter.rewriteEnchantmentsToClient(tag, true);
/*     */     }
/* 339 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToServer(Item item) {
/* 344 */     if (item == null) return null; 
/* 345 */     super.handleItemToServer(item);
/*     */     
/* 347 */     CompoundTag tag = item.tag();
/* 348 */     if (tag == null) return item;
/*     */ 
/*     */     
/* 351 */     EntityIdRewriter.toServerItem(item, true);
/*     */     
/* 353 */     if (tag.contains(this.nbtTagName + "|ench")) {
/* 354 */       this.enchantmentRewriter.rewriteEnchantmentsToServer(tag, false);
/*     */     }
/* 356 */     if (tag.contains(this.nbtTagName + "|StoredEnchantments")) {
/* 357 */       this.enchantmentRewriter.rewriteEnchantmentsToServer(tag, true);
/*     */     }
/* 359 */     return item;
/*     */   }
/*     */   
/*     */   private boolean isLlama(UserConnection user) {
/* 363 */     WindowTracker tracker = (WindowTracker)user.get(WindowTracker.class);
/* 364 */     if (tracker.getInventory() != null && tracker.getInventory().equals("EntityHorse")) {
/* 365 */       EntityTracker entTracker = user.getEntityTracker(Protocol1_10To1_11.class);
/* 366 */       StoredEntityData entityData = entTracker.entityData(tracker.getEntityId());
/* 367 */       return (entityData != null && entityData.type().is((EntityType)Entity1_11Types.EntityType.LIAMA));
/*     */     } 
/* 369 */     return false;
/*     */   }
/*     */   
/*     */   private Optional<ChestedHorseStorage> getChestedHorse(UserConnection user) {
/* 373 */     WindowTracker tracker = (WindowTracker)user.get(WindowTracker.class);
/* 374 */     if (tracker.getInventory() != null && tracker.getInventory().equals("EntityHorse")) {
/* 375 */       EntityTracker entTracker = user.getEntityTracker(Protocol1_10To1_11.class);
/* 376 */       StoredEntityData entityData = entTracker.entityData(tracker.getEntityId());
/* 377 */       if (entityData != null)
/* 378 */         return Optional.of((ChestedHorseStorage)entityData.get(ChestedHorseStorage.class)); 
/*     */     } 
/* 380 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   private int getNewSlotId(ChestedHorseStorage storage, int slotId) {
/* 384 */     int totalSlots = !storage.isChested() ? 38 : 53;
/* 385 */     int strength = storage.isChested() ? storage.getLiamaStrength() : 0;
/* 386 */     int startNonExistingFormula = 2 + 3 * strength;
/* 387 */     int offsetForm = 15 - 3 * strength;
/*     */     
/* 389 */     if (slotId >= startNonExistingFormula && totalSlots > slotId + offsetForm)
/* 390 */       return offsetForm + slotId; 
/* 391 */     if (slotId == 1)
/* 392 */       return 0; 
/* 393 */     return slotId;
/*     */   }
/*     */   
/*     */   private int getOldSlotId(ChestedHorseStorage storage, int slotId) {
/* 397 */     int strength = storage.isChested() ? storage.getLiamaStrength() : 0;
/* 398 */     int startNonExistingFormula = 2 + 3 * strength;
/* 399 */     int endNonExistingFormula = 2 + 3 * (storage.isChested() ? 5 : 0);
/* 400 */     int offsetForm = endNonExistingFormula - startNonExistingFormula;
/*     */     
/* 402 */     if (slotId == 1 || (slotId >= startNonExistingFormula && slotId < endNonExistingFormula))
/* 403 */       return 0; 
/* 404 */     if (slotId >= endNonExistingFormula)
/* 405 */       return slotId - offsetForm; 
/* 406 */     if (slotId == 0)
/* 407 */       return 1; 
/* 408 */     return slotId;
/*     */   }
/*     */   
/*     */   private Item getNewItem(ChestedHorseStorage storage, int slotId, Item current) {
/* 412 */     int strength = storage.isChested() ? storage.getLiamaStrength() : 0;
/* 413 */     int startNonExistingFormula = 2 + 3 * strength;
/* 414 */     int endNonExistingFormula = 2 + 3 * (storage.isChested() ? 5 : 0);
/*     */     
/* 416 */     if (slotId >= startNonExistingFormula && slotId < endNonExistingFormula)
/* 417 */       return (Item)new DataItem(166, (byte)1, (short)0, getNamedTag("§4SLOT DISABLED")); 
/* 418 */     if (slotId == 1)
/* 419 */       return null; 
/* 420 */     return current;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_10to1_11\packets\BlockItemPackets1_11.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
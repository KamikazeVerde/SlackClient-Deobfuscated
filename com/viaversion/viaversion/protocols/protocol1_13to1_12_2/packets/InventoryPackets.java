/*     */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.primitives.Ints;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.opennbt.conversion.ConverterRegistry;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.BlockIdData;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.MappingData;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.SoundSource;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.SpawnEggRewriter;
/*     */ import com.viaversion.viaversion.rewriter.ItemRewriter;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
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
/*     */ public class InventoryPackets
/*     */   extends ItemRewriter<ClientboundPackets1_12_1, ServerboundPackets1_13, Protocol1_13To1_12_2>
/*     */ {
/*  51 */   private static final String NBT_TAG_NAME = "ViaVersion|" + Protocol1_13To1_12_2.class.getSimpleName();
/*     */   
/*     */   public InventoryPackets(Protocol1_13To1_12_2 protocol) {
/*  54 */     super((Protocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerPackets() {
/*  59 */     ((Protocol1_13To1_12_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.SET_SLOT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  62 */             map((Type)Type.UNSIGNED_BYTE);
/*  63 */             map((Type)Type.SHORT);
/*  64 */             map(Type.ITEM, Type.FLAT_ITEM);
/*     */             
/*  66 */             handler(InventoryPackets.this.itemToClientHandler(Type.FLAT_ITEM));
/*     */           }
/*     */         });
/*  69 */     ((Protocol1_13To1_12_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.WINDOW_ITEMS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  72 */             map((Type)Type.UNSIGNED_BYTE);
/*  73 */             map(Type.ITEM_ARRAY, Type.FLAT_ITEM_ARRAY);
/*     */             
/*  75 */             handler(InventoryPackets.this.itemArrayHandler(Type.FLAT_ITEM_ARRAY));
/*     */           }
/*     */         });
/*  78 */     ((Protocol1_13To1_12_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.WINDOW_PROPERTY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  81 */             map((Type)Type.UNSIGNED_BYTE);
/*  82 */             map((Type)Type.SHORT);
/*  83 */             map((Type)Type.SHORT);
/*     */             
/*  85 */             handler(wrapper -> {
/*     */                   short property = ((Short)wrapper.get((Type)Type.SHORT, 0)).shortValue();
/*     */                   
/*     */                   if (property >= 4 && property <= 6) {
/*     */                     wrapper.set((Type)Type.SHORT, 1, Short.valueOf((short)((Protocol1_13To1_12_2)InventoryPackets.this.protocol).getMappingData().getEnchantmentMappings().getNewId(((Short)wrapper.get((Type)Type.SHORT, 1)).shortValue())));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/*  95 */     ((Protocol1_13To1_12_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  98 */             map(Type.STRING);
/*     */             
/* 100 */             handler(wrapper -> {
/*     */                   String channel = (String)wrapper.get(Type.STRING, 0);
/*     */                   
/*     */                   if (channel.equalsIgnoreCase("MC|StopSound")) {
/*     */                     String originalSource = (String)wrapper.read(Type.STRING);
/*     */                     
/*     */                     String originalSound = (String)wrapper.read(Type.STRING);
/*     */                     
/*     */                     wrapper.clearPacket();
/*     */                     
/*     */                     wrapper.setPacketType((PacketType)ClientboundPackets1_13.STOP_SOUND);
/*     */                     
/*     */                     byte flags = 0;
/*     */                     
/*     */                     wrapper.write((Type)Type.BYTE, Byte.valueOf(flags));
/*     */                     
/*     */                     if (!originalSource.isEmpty()) {
/*     */                       flags = (byte)(flags | 0x1);
/*     */                       
/*     */                       Optional<SoundSource> finalSource = SoundSource.findBySource(originalSource);
/*     */                       
/*     */                       if (!finalSource.isPresent()) {
/*     */                         if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
/*     */                           Via.getPlatform().getLogger().info("Could not handle unknown sound source " + originalSource + " falling back to default: master");
/*     */                         }
/*     */                         
/*     */                         finalSource = Optional.of(SoundSource.MASTER);
/*     */                       } 
/*     */                       
/*     */                       wrapper.write((Type)Type.VAR_INT, Integer.valueOf(((SoundSource)finalSource.get()).getId()));
/*     */                     } 
/*     */                     
/*     */                     if (!originalSound.isEmpty()) {
/*     */                       flags = (byte)(flags | 0x2);
/*     */                       wrapper.write(Type.STRING, originalSound);
/*     */                     } 
/*     */                     wrapper.set((Type)Type.BYTE, 0, Byte.valueOf(flags));
/*     */                     return;
/*     */                   } 
/*     */                   if (channel.equalsIgnoreCase("MC|TrList")) {
/*     */                     channel = "minecraft:trader_list";
/*     */                     wrapper.passthrough((Type)Type.INT);
/*     */                     int size = ((Short)wrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                     for (int i = 0; i < size; i++) {
/*     */                       Item input = (Item)wrapper.read(Type.ITEM);
/*     */                       InventoryPackets.this.handleItemToClient(input);
/*     */                       wrapper.write(Type.FLAT_ITEM, input);
/*     */                       Item output = (Item)wrapper.read(Type.ITEM);
/*     */                       InventoryPackets.this.handleItemToClient(output);
/*     */                       wrapper.write(Type.FLAT_ITEM, output);
/*     */                       boolean secondItem = ((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue();
/*     */                       if (secondItem) {
/*     */                         Item second = (Item)wrapper.read(Type.ITEM);
/*     */                         InventoryPackets.this.handleItemToClient(second);
/*     */                         wrapper.write(Type.FLAT_ITEM, second);
/*     */                       } 
/*     */                       wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                       wrapper.passthrough((Type)Type.INT);
/*     */                       wrapper.passthrough((Type)Type.INT);
/*     */                     } 
/*     */                   } else {
/*     */                     String old = channel;
/*     */                     channel = InventoryPackets.getNewPluginChannelId(channel);
/*     */                     if (channel == null) {
/*     */                       if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
/*     */                         Via.getPlatform().getLogger().warning("Ignoring outgoing plugin message with channel: " + old);
/*     */                       }
/*     */                       wrapper.cancel();
/*     */                       return;
/*     */                     } 
/*     */                     if (channel.equals("minecraft:register") || channel.equals("minecraft:unregister")) {
/*     */                       String[] channels = (new String((byte[])wrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8)).split("\000");
/*     */                       List<String> rewrittenChannels = new ArrayList<>();
/*     */                       for (String s : channels) {
/*     */                         String rewritten = InventoryPackets.getNewPluginChannelId(s);
/*     */                         if (rewritten != null) {
/*     */                           rewrittenChannels.add(rewritten);
/*     */                         } else if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
/*     */                           Via.getPlatform().getLogger().warning("Ignoring plugin channel in outgoing REGISTER: " + s);
/*     */                         } 
/*     */                       } 
/*     */                       if (!rewrittenChannels.isEmpty()) {
/*     */                         wrapper.write(Type.REMAINING_BYTES, Joiner.on(false).join(rewrittenChannels).getBytes(StandardCharsets.UTF_8));
/*     */                       } else {
/*     */                         wrapper.cancel();
/*     */                         return;
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                   wrapper.set(Type.STRING, 0, channel);
/*     */                 });
/*     */           }
/*     */         });
/* 193 */     ((Protocol1_13To1_12_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_12_1.ENTITY_EQUIPMENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 196 */             map((Type)Type.VAR_INT);
/* 197 */             map((Type)Type.VAR_INT);
/* 198 */             map(Type.ITEM, Type.FLAT_ITEM);
/*     */             
/* 200 */             handler(InventoryPackets.this.itemToClientHandler(Type.FLAT_ITEM));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 205 */     ((Protocol1_13To1_12_2)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_13.CLICK_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 208 */             map((Type)Type.UNSIGNED_BYTE);
/* 209 */             map((Type)Type.SHORT);
/* 210 */             map((Type)Type.BYTE);
/* 211 */             map((Type)Type.SHORT);
/* 212 */             map((Type)Type.VAR_INT);
/* 213 */             map(Type.FLAT_ITEM, Type.ITEM);
/*     */             
/* 215 */             handler(InventoryPackets.this.itemToServerHandler(Type.ITEM));
/*     */           }
/*     */         });
/*     */     
/* 219 */     ((Protocol1_13To1_12_2)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_13.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 222 */             map(Type.STRING);
/* 223 */             handler(wrapper -> {
/*     */                   String channel = (String)wrapper.get(Type.STRING, 0);
/*     */                   String old = channel;
/*     */                   channel = InventoryPackets.getOldPluginChannelId(channel);
/*     */                   if (channel == null) {
/*     */                     if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
/*     */                       Via.getPlatform().getLogger().warning("Ignoring incoming plugin message with channel: " + old);
/*     */                     }
/*     */                     wrapper.cancel();
/*     */                     return;
/*     */                   } 
/*     */                   if (channel.equals("REGISTER") || channel.equals("UNREGISTER")) {
/*     */                     String[] channels = (new String((byte[])wrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8)).split("\000");
/*     */                     List<String> rewrittenChannels = new ArrayList<>();
/*     */                     for (String s : channels) {
/*     */                       String rewritten = InventoryPackets.getOldPluginChannelId(s);
/*     */                       if (rewritten != null) {
/*     */                         rewrittenChannels.add(rewritten);
/*     */                       } else if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
/*     */                         Via.getPlatform().getLogger().warning("Ignoring plugin channel in incoming REGISTER: " + s);
/*     */                       } 
/*     */                     } 
/*     */                     wrapper.write(Type.REMAINING_BYTES, Joiner.on(false).join(rewrittenChannels).getBytes(StandardCharsets.UTF_8));
/*     */                   } 
/*     */                   wrapper.set(Type.STRING, 0, channel);
/*     */                 });
/*     */           }
/*     */         });
/* 251 */     ((Protocol1_13To1_12_2)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 254 */             map((Type)Type.SHORT);
/* 255 */             map(Type.FLAT_ITEM, Type.ITEM);
/*     */             
/* 257 */             handler(InventoryPackets.this.itemToServerHandler(Type.ITEM));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToClient(Item item) {
/* 264 */     if (item == null) return null; 
/* 265 */     CompoundTag tag = item.tag();
/*     */ 
/*     */     
/* 268 */     int originalId = item.identifier() << 16 | item.data() & 0xFFFF;
/*     */     
/* 270 */     int rawId = item.identifier() << 4 | item.data() & 0xF;
/*     */ 
/*     */     
/* 273 */     if (isDamageable(item.identifier())) {
/* 274 */       if (tag == null) item.setTag(tag = new CompoundTag()); 
/* 275 */       tag.put("Damage", (Tag)new IntTag(item.data()));
/*     */     } 
/* 277 */     if (item.identifier() == 358) {
/* 278 */       if (tag == null) item.setTag(tag = new CompoundTag()); 
/* 279 */       tag.put("map", (Tag)new IntTag(item.data()));
/*     */     } 
/*     */ 
/*     */     
/* 283 */     if (tag != null) {
/*     */       
/* 285 */       boolean banner = (item.identifier() == 425);
/* 286 */       if ((banner || item.identifier() == 442) && 
/* 287 */         tag.get("BlockEntityTag") instanceof CompoundTag) {
/* 288 */         CompoundTag blockEntityTag = (CompoundTag)tag.get("BlockEntityTag");
/* 289 */         if (blockEntityTag.get("Base") instanceof IntTag) {
/* 290 */           IntTag base = (IntTag)blockEntityTag.get("Base");
/*     */           
/* 292 */           if (banner) {
/* 293 */             rawId = 6800 + base.asInt();
/*     */           }
/*     */           
/* 296 */           base.setValue(15 - base.asInt());
/*     */         } 
/* 298 */         if (blockEntityTag.get("Patterns") instanceof ListTag) {
/* 299 */           for (Tag pattern : blockEntityTag.get("Patterns")) {
/* 300 */             if (pattern instanceof CompoundTag) {
/* 301 */               Tag c = ((CompoundTag)pattern).get("Color");
/* 302 */               if (c instanceof NumberTag)
/*     */               {
/* 304 */                 ((CompoundTag)pattern).put("Color", (Tag)new IntTag(15 - ((NumberTag)c)
/* 305 */                       .asInt()));
/*     */               }
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 313 */       if (tag.get("display") instanceof CompoundTag) {
/* 314 */         CompoundTag display = (CompoundTag)tag.get("display");
/* 315 */         if (display.get("Name") instanceof StringTag) {
/* 316 */           StringTag name = (StringTag)display.get("Name");
/* 317 */           display.put(NBT_TAG_NAME + "|Name", (Tag)new StringTag(name.getValue()));
/* 318 */           name.setValue(ChatRewriter.legacyTextToJsonString(name.getValue(), true));
/*     */         } 
/*     */       } 
/*     */       
/* 322 */       if (tag.get("ench") instanceof ListTag) {
/* 323 */         ListTag ench = (ListTag)tag.get("ench");
/* 324 */         ListTag enchantments = new ListTag(CompoundTag.class);
/* 325 */         for (Tag enchEntry : ench) {
/*     */           NumberTag idTag;
/* 327 */           if (enchEntry instanceof CompoundTag && (idTag = (NumberTag)((CompoundTag)enchEntry).get("id")) != null) {
/* 328 */             CompoundTag enchantmentEntry = new CompoundTag();
/* 329 */             short oldId = idTag.asShort();
/* 330 */             String newId = (String)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().get(Short.valueOf(oldId));
/* 331 */             if (newId == null) {
/* 332 */               newId = "viaversion:legacy/" + oldId;
/*     */             }
/* 334 */             enchantmentEntry.put("id", (Tag)new StringTag(newId));
/* 335 */             enchantmentEntry.put("lvl", (Tag)new ShortTag(((NumberTag)((CompoundTag)enchEntry).get("lvl")).asShort()));
/* 336 */             enchantments.add((Tag)enchantmentEntry);
/*     */           } 
/*     */         } 
/* 339 */         tag.remove("ench");
/* 340 */         tag.put("Enchantments", (Tag)enchantments);
/*     */       } 
/* 342 */       if (tag.get("StoredEnchantments") instanceof ListTag) {
/* 343 */         ListTag storedEnch = (ListTag)tag.get("StoredEnchantments");
/* 344 */         ListTag newStoredEnch = new ListTag(CompoundTag.class);
/* 345 */         for (Tag enchEntry : storedEnch) {
/* 346 */           if (enchEntry instanceof CompoundTag) {
/* 347 */             CompoundTag enchantmentEntry = new CompoundTag();
/* 348 */             short oldId = ((NumberTag)((CompoundTag)enchEntry).get("id")).asShort();
/* 349 */             String newId = (String)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().get(Short.valueOf(oldId));
/* 350 */             if (newId == null) {
/* 351 */               newId = "viaversion:legacy/" + oldId;
/*     */             }
/* 353 */             enchantmentEntry.put("id", (Tag)new StringTag(newId));
/* 354 */             enchantmentEntry.put("lvl", (Tag)new ShortTag(((NumberTag)((CompoundTag)enchEntry).get("lvl")).asShort()));
/* 355 */             newStoredEnch.add((Tag)enchantmentEntry);
/*     */           } 
/*     */         } 
/* 358 */         tag.remove("StoredEnchantments");
/* 359 */         tag.put("StoredEnchantments", (Tag)newStoredEnch);
/*     */       } 
/* 361 */       if (tag.get("CanPlaceOn") instanceof ListTag) {
/* 362 */         ListTag old = (ListTag)tag.get("CanPlaceOn");
/* 363 */         ListTag newCanPlaceOn = new ListTag(StringTag.class);
/* 364 */         tag.put(NBT_TAG_NAME + "|CanPlaceOn", ConverterRegistry.convertToTag(ConverterRegistry.convertToValue((Tag)old)));
/* 365 */         for (Tag oldTag : old) {
/* 366 */           Object value = oldTag.getValue();
/* 367 */           String oldId = value.toString().replace("minecraft:", "");
/* 368 */           String numberConverted = (String)BlockIdData.numberIdToString.get(Ints.tryParse(oldId));
/* 369 */           if (numberConverted != null) {
/* 370 */             oldId = numberConverted;
/*     */           }
/* 372 */           String[] newValues = (String[])BlockIdData.blockIdMapping.get(oldId.toLowerCase(Locale.ROOT));
/* 373 */           if (newValues != null) {
/* 374 */             for (String newValue : newValues)
/* 375 */               newCanPlaceOn.add((Tag)new StringTag(newValue)); 
/*     */             continue;
/*     */           } 
/* 378 */           newCanPlaceOn.add((Tag)new StringTag(oldId.toLowerCase(Locale.ROOT)));
/*     */         } 
/*     */         
/* 381 */         tag.put("CanPlaceOn", (Tag)newCanPlaceOn);
/*     */       } 
/* 383 */       if (tag.get("CanDestroy") instanceof ListTag) {
/* 384 */         ListTag old = (ListTag)tag.get("CanDestroy");
/* 385 */         ListTag newCanDestroy = new ListTag(StringTag.class);
/* 386 */         tag.put(NBT_TAG_NAME + "|CanDestroy", ConverterRegistry.convertToTag(ConverterRegistry.convertToValue((Tag)old)));
/* 387 */         for (Tag oldTag : old) {
/* 388 */           Object value = oldTag.getValue();
/* 389 */           String oldId = value.toString().replace("minecraft:", "");
/* 390 */           String numberConverted = (String)BlockIdData.numberIdToString.get(Ints.tryParse(oldId));
/* 391 */           if (numberConverted != null) {
/* 392 */             oldId = numberConverted;
/*     */           }
/* 394 */           String[] newValues = (String[])BlockIdData.blockIdMapping.get(oldId.toLowerCase(Locale.ROOT));
/* 395 */           if (newValues != null) {
/* 396 */             for (String newValue : newValues)
/* 397 */               newCanDestroy.add((Tag)new StringTag(newValue)); 
/*     */             continue;
/*     */           } 
/* 400 */           newCanDestroy.add((Tag)new StringTag(oldId.toLowerCase(Locale.ROOT)));
/*     */         } 
/*     */         
/* 403 */         tag.put("CanDestroy", (Tag)newCanDestroy);
/*     */       } 
/*     */       
/* 406 */       if (item.identifier() == 383) {
/* 407 */         if (tag.get("EntityTag") instanceof CompoundTag) {
/* 408 */           CompoundTag entityTag = (CompoundTag)tag.get("EntityTag");
/* 409 */           if (entityTag.get("id") instanceof StringTag) {
/* 410 */             StringTag identifier = (StringTag)entityTag.get("id");
/* 411 */             rawId = SpawnEggRewriter.getSpawnEggId(identifier.getValue());
/* 412 */             if (rawId == -1) {
/* 413 */               rawId = 25100288;
/*     */             } else {
/* 415 */               entityTag.remove("id");
/* 416 */               if (entityTag.isEmpty()) {
/* 417 */                 tag.remove("EntityTag");
/*     */               }
/*     */             } 
/*     */           } else {
/* 421 */             rawId = 25100288;
/*     */           } 
/*     */         } else {
/*     */           
/* 425 */           rawId = 25100288;
/*     */         } 
/*     */       }
/* 428 */       if (tag.isEmpty()) {
/* 429 */         item.setTag(tag = null);
/*     */       }
/*     */     } 
/*     */     
/* 433 */     if (Protocol1_13To1_12_2.MAPPINGS.getItemMappings().getNewId(rawId) == -1) {
/* 434 */       if (!isDamageable(item.identifier()) && item.identifier() != 358) {
/* 435 */         if (tag == null) item.setTag(tag = new CompoundTag()); 
/* 436 */         tag.put(NBT_TAG_NAME, (Tag)new IntTag(originalId));
/*     */       } 
/* 438 */       if (item.identifier() == 31 && item.data() == 0) {
/* 439 */         rawId = 512;
/* 440 */       } else if (Protocol1_13To1_12_2.MAPPINGS.getItemMappings().getNewId(rawId & 0xFFFFFFF0) != -1) {
/* 441 */         rawId &= 0xFFFFFFF0;
/*     */       } else {
/* 443 */         if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
/* 444 */           Via.getPlatform().getLogger().warning("Failed to get 1.13 item for " + item.identifier());
/*     */         }
/* 446 */         rawId = 16;
/*     */       } 
/*     */     } 
/*     */     
/* 450 */     item.setIdentifier(Protocol1_13To1_12_2.MAPPINGS.getItemMappings().getNewId(rawId));
/* 451 */     item.setData((short)0);
/* 452 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getNewPluginChannelId(String old) {
/* 457 */     switch (old) {
/*     */       case "MC|TrList":
/* 459 */         return "minecraft:trader_list";
/*     */       case "MC|Brand":
/* 461 */         return "minecraft:brand";
/*     */       case "MC|BOpen":
/* 463 */         return "minecraft:book_open";
/*     */       case "MC|DebugPath":
/* 465 */         return "minecraft:debug/paths";
/*     */       case "MC|DebugNeighborsUpdate":
/* 467 */         return "minecraft:debug/neighbors_update";
/*     */       case "REGISTER":
/* 469 */         return "minecraft:register";
/*     */       case "UNREGISTER":
/* 471 */         return "minecraft:unregister";
/*     */       case "BungeeCord":
/* 473 */         return "bungeecord:main";
/*     */       case "bungeecord:main":
/* 475 */         return null;
/*     */     } 
/* 477 */     String mappedChannel = (String)Protocol1_13To1_12_2.MAPPINGS.getChannelMappings().get(old);
/* 478 */     if (mappedChannel != null) return mappedChannel; 
/* 479 */     return MappingData.validateNewChannel(old);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item handleItemToServer(Item item) {
/* 485 */     if (item == null) return null;
/*     */     
/* 487 */     Integer rawId = null;
/* 488 */     boolean gotRawIdFromTag = false;
/*     */     
/* 490 */     CompoundTag tag = item.tag();
/*     */ 
/*     */     
/* 493 */     if (tag != null)
/*     */     {
/* 495 */       if (tag.get(NBT_TAG_NAME) instanceof IntTag) {
/* 496 */         rawId = Integer.valueOf(((NumberTag)tag.get(NBT_TAG_NAME)).asInt());
/*     */         
/* 498 */         tag.remove(NBT_TAG_NAME);
/* 499 */         gotRawIdFromTag = true;
/*     */       } 
/*     */     }
/*     */     
/* 503 */     if (rawId == null) {
/* 504 */       int oldId = Protocol1_13To1_12_2.MAPPINGS.getItemMappings().inverse().getNewId(item.identifier());
/* 505 */       if (oldId != -1) {
/*     */         
/* 507 */         Optional<String> eggEntityId = SpawnEggRewriter.getEntityId(oldId);
/* 508 */         if (eggEntityId.isPresent()) {
/* 509 */           rawId = Integer.valueOf(25100288);
/* 510 */           if (tag == null)
/* 511 */             item.setTag(tag = new CompoundTag()); 
/* 512 */           if (!tag.contains("EntityTag")) {
/* 513 */             CompoundTag entityTag = new CompoundTag();
/* 514 */             entityTag.put("id", (Tag)new StringTag(eggEntityId.get()));
/* 515 */             tag.put("EntityTag", (Tag)entityTag);
/*     */           } 
/*     */         } else {
/* 518 */           rawId = Integer.valueOf(oldId >> 4 << 16 | oldId & 0xF);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 523 */     if (rawId == null) {
/* 524 */       if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
/* 525 */         Via.getPlatform().getLogger().warning("Failed to get 1.12 item for " + item.identifier());
/*     */       }
/* 527 */       rawId = Integer.valueOf(65536);
/*     */     } 
/*     */     
/* 530 */     item.setIdentifier((short)(rawId.intValue() >> 16));
/* 531 */     item.setData((short)(rawId.intValue() & 0xFFFF));
/*     */ 
/*     */     
/* 534 */     if (tag != null) {
/* 535 */       if (isDamageable(item.identifier()) && 
/* 536 */         tag.get("Damage") instanceof IntTag) {
/* 537 */         if (!gotRawIdFromTag) {
/* 538 */           item.setData((short)((Integer)tag.get("Damage").getValue()).intValue());
/*     */         }
/* 540 */         tag.remove("Damage");
/*     */       } 
/*     */ 
/*     */       
/* 544 */       if (item.identifier() == 358 && 
/* 545 */         tag.get("map") instanceof IntTag) {
/* 546 */         if (!gotRawIdFromTag) {
/* 547 */           item.setData((short)((Integer)tag.get("map").getValue()).intValue());
/*     */         }
/* 549 */         tag.remove("map");
/*     */       } 
/*     */ 
/*     */       
/* 553 */       if ((item.identifier() == 442 || item.identifier() == 425) && 
/* 554 */         tag.get("BlockEntityTag") instanceof CompoundTag) {
/* 555 */         CompoundTag blockEntityTag = (CompoundTag)tag.get("BlockEntityTag");
/* 556 */         if (blockEntityTag.get("Base") instanceof IntTag) {
/* 557 */           IntTag base = (IntTag)blockEntityTag.get("Base");
/* 558 */           base.setValue(15 - base.asInt());
/*     */         } 
/* 560 */         if (blockEntityTag.get("Patterns") instanceof ListTag) {
/* 561 */           for (Tag pattern : blockEntityTag.get("Patterns")) {
/* 562 */             if (pattern instanceof CompoundTag) {
/* 563 */               IntTag c = (IntTag)((CompoundTag)pattern).get("Color");
/* 564 */               c.setValue(15 - c.asInt());
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 571 */       if (tag.get("display") instanceof CompoundTag) {
/* 572 */         CompoundTag display = (CompoundTag)tag.get("display");
/* 573 */         if (display.get("Name") instanceof StringTag) {
/* 574 */           StringTag name = (StringTag)display.get("Name");
/* 575 */           StringTag via = (StringTag)display.remove(NBT_TAG_NAME + "|Name");
/* 576 */           name.setValue((via != null) ? via.getValue() : ChatRewriter.jsonToLegacyText(name.getValue()));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 581 */       if (tag.get("Enchantments") instanceof ListTag) {
/* 582 */         ListTag enchantments = (ListTag)tag.get("Enchantments");
/* 583 */         ListTag ench = new ListTag(CompoundTag.class);
/* 584 */         for (Tag enchantmentEntry : enchantments) {
/* 585 */           if (enchantmentEntry instanceof CompoundTag) {
/* 586 */             CompoundTag enchEntry = new CompoundTag();
/* 587 */             String newId = (String)((CompoundTag)enchantmentEntry).get("id").getValue();
/* 588 */             Short oldId = (Short)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().inverse().get(newId);
/* 589 */             if (oldId == null && newId.startsWith("viaversion:legacy/")) {
/* 590 */               oldId = Short.valueOf(newId.substring(18));
/*     */             }
/* 592 */             if (oldId != null) {
/* 593 */               enchEntry.put("id", (Tag)new ShortTag(oldId.shortValue()));
/* 594 */               enchEntry.put("lvl", (Tag)new ShortTag(((NumberTag)((CompoundTag)enchantmentEntry).get("lvl")).asShort()));
/* 595 */               ench.add((Tag)enchEntry);
/*     */             } 
/*     */           } 
/*     */         } 
/* 599 */         tag.remove("Enchantments");
/* 600 */         tag.put("ench", (Tag)ench);
/*     */       } 
/* 602 */       if (tag.get("StoredEnchantments") instanceof ListTag) {
/* 603 */         ListTag storedEnch = (ListTag)tag.get("StoredEnchantments");
/* 604 */         ListTag newStoredEnch = new ListTag(CompoundTag.class);
/* 605 */         for (Tag enchantmentEntry : storedEnch) {
/* 606 */           if (enchantmentEntry instanceof CompoundTag) {
/* 607 */             CompoundTag enchEntry = new CompoundTag();
/* 608 */             String newId = (String)((CompoundTag)enchantmentEntry).get("id").getValue();
/* 609 */             Short oldId = (Short)Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().inverse().get(newId);
/* 610 */             if (oldId == null && newId.startsWith("viaversion:legacy/")) {
/* 611 */               oldId = Short.valueOf(newId.substring(18));
/*     */             }
/* 613 */             if (oldId != null) {
/* 614 */               enchEntry.put("id", (Tag)new ShortTag(oldId.shortValue()));
/* 615 */               enchEntry.put("lvl", (Tag)new ShortTag(((NumberTag)((CompoundTag)enchantmentEntry).get("lvl")).asShort()));
/* 616 */               newStoredEnch.add((Tag)enchEntry);
/*     */             } 
/*     */           } 
/*     */         } 
/* 620 */         tag.remove("StoredEnchantments");
/* 621 */         tag.put("StoredEnchantments", (Tag)newStoredEnch);
/*     */       } 
/* 623 */       if (tag.get(NBT_TAG_NAME + "|CanPlaceOn") instanceof ListTag) {
/* 624 */         tag.put("CanPlaceOn", ConverterRegistry.convertToTag(ConverterRegistry.convertToValue(tag.get(NBT_TAG_NAME + "|CanPlaceOn"))));
/* 625 */         tag.remove(NBT_TAG_NAME + "|CanPlaceOn");
/* 626 */       } else if (tag.get("CanPlaceOn") instanceof ListTag) {
/* 627 */         ListTag old = (ListTag)tag.get("CanPlaceOn");
/* 628 */         ListTag newCanPlaceOn = new ListTag(StringTag.class);
/* 629 */         for (Tag oldTag : old) {
/* 630 */           Object value = oldTag.getValue();
/* 631 */           String[] newValues = (String[])BlockIdData.fallbackReverseMapping.get((value instanceof String) ? ((String)value)
/* 632 */               .replace("minecraft:", "") : null);
/*     */           
/* 634 */           if (newValues != null) {
/* 635 */             for (String newValue : newValues)
/* 636 */               newCanPlaceOn.add((Tag)new StringTag(newValue)); 
/*     */             continue;
/*     */           } 
/* 639 */           newCanPlaceOn.add(oldTag);
/*     */         } 
/*     */         
/* 642 */         tag.put("CanPlaceOn", (Tag)newCanPlaceOn);
/*     */       } 
/* 644 */       if (tag.get(NBT_TAG_NAME + "|CanDestroy") instanceof ListTag) {
/* 645 */         tag.put("CanDestroy", ConverterRegistry.convertToTag(
/* 646 */               ConverterRegistry.convertToValue(tag.get(NBT_TAG_NAME + "|CanDestroy"))));
/*     */         
/* 648 */         tag.remove(NBT_TAG_NAME + "|CanDestroy");
/* 649 */       } else if (tag.get("CanDestroy") instanceof ListTag) {
/* 650 */         ListTag old = (ListTag)tag.get("CanDestroy");
/* 651 */         ListTag newCanDestroy = new ListTag(StringTag.class);
/* 652 */         for (Tag oldTag : old) {
/* 653 */           Object value = oldTag.getValue();
/* 654 */           String[] newValues = (String[])BlockIdData.fallbackReverseMapping.get((value instanceof String) ? ((String)value)
/* 655 */               .replace("minecraft:", "") : null);
/*     */           
/* 657 */           if (newValues != null) {
/* 658 */             for (String newValue : newValues)
/* 659 */               newCanDestroy.add((Tag)new StringTag(newValue)); 
/*     */             continue;
/*     */           } 
/* 662 */           newCanDestroy.add(oldTag);
/*     */         } 
/*     */         
/* 665 */         tag.put("CanDestroy", (Tag)newCanDestroy);
/*     */       } 
/*     */     } 
/* 668 */     return item;
/*     */   }
/*     */   
/*     */   public static String getOldPluginChannelId(String newId) {
/* 672 */     newId = MappingData.validateNewChannel(newId);
/* 673 */     if (newId == null) return null;
/*     */ 
/*     */     
/* 676 */     switch (newId) {
/*     */       case "minecraft:trader_list":
/* 678 */         return "MC|TrList";
/*     */       case "minecraft:book_open":
/* 680 */         return "MC|BOpen";
/*     */       case "minecraft:debug/paths":
/* 682 */         return "MC|DebugPath";
/*     */       case "minecraft:debug/neighbors_update":
/* 684 */         return "MC|DebugNeighborsUpdate";
/*     */       case "minecraft:register":
/* 686 */         return "REGISTER";
/*     */       case "minecraft:unregister":
/* 688 */         return "UNREGISTER";
/*     */       case "minecraft:brand":
/* 690 */         return "MC|Brand";
/*     */       case "bungeecord:main":
/* 692 */         return "BungeeCord";
/*     */     } 
/* 694 */     String mappedChannel = (String)Protocol1_13To1_12_2.MAPPINGS.getChannelMappings().inverse().get(newId);
/* 695 */     if (mappedChannel != null) return mappedChannel; 
/* 696 */     return (newId.length() > 20) ? newId.substring(0, 20) : newId;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isDamageable(int id) {
/* 701 */     return ((id >= 256 && id <= 259) || id == 261 || (id >= 267 && id <= 279) || (id >= 283 && id <= 286) || (id >= 290 && id <= 294) || (id >= 298 && id <= 317) || id == 346 || id == 359 || id == 398 || id == 442 || id == 443);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\packets\InventoryPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
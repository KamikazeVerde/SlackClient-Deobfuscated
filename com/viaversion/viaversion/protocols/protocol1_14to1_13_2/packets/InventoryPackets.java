/*     */ package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.item.DataItem;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.DoubleTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
/*     */ import com.viaversion.viaversion.rewriter.ComponentRewriter;
/*     */ import com.viaversion.viaversion.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.rewriter.RecipeRewriter;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ThreadLocalRandom;
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
/*     */ public class InventoryPackets
/*     */   extends ItemRewriter<ClientboundPackets1_13, ServerboundPackets1_14, Protocol1_14To1_13_2>
/*     */ {
/*  48 */   private static final String NBT_TAG_NAME = "ViaVersion|" + Protocol1_14To1_13_2.class.getSimpleName();
/*  49 */   private static final Set<String> REMOVED_RECIPE_TYPES = Sets.newHashSet((Object[])new String[] { "crafting_special_banneraddpattern", "crafting_special_repairitem" });
/*  50 */   private static final ComponentRewriter<ClientboundPackets1_13> COMPONENT_REWRITER = new ComponentRewriter<ClientboundPackets1_13>()
/*     */     {
/*     */       protected void handleTranslate(JsonObject object, String translate) {
/*  53 */         super.handleTranslate(object, translate);
/*     */         
/*  55 */         if (translate.startsWith("block.") && translate.endsWith(".name")) {
/*  56 */           object.addProperty("translate", translate.substring(0, translate.length() - 5));
/*     */         }
/*     */       }
/*     */     };
/*     */   
/*     */   public InventoryPackets(Protocol1_14To1_13_2 protocol) {
/*  62 */     super((Protocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerPackets() {
/*  67 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_13.COOLDOWN);
/*  68 */     registerAdvancements((ClientboundPacketType)ClientboundPackets1_13.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
/*     */     
/*  70 */     ((Protocol1_14To1_13_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.OPEN_WINDOW, null, wrapper -> {
/*     */           Short windowId = (Short)wrapper.read((Type)Type.UNSIGNED_BYTE);
/*     */           String type = (String)wrapper.read(Type.STRING);
/*     */           JsonElement title = (JsonElement)wrapper.read(Type.COMPONENT);
/*     */           COMPONENT_REWRITER.processText(title);
/*     */           Short slots = (Short)wrapper.read((Type)Type.UNSIGNED_BYTE);
/*     */           if (type.equals("EntityHorse")) {
/*     */             wrapper.setPacketType((PacketType)ClientboundPackets1_14.OPEN_HORSE_WINDOW);
/*     */             int entityId = ((Integer)wrapper.read((Type)Type.INT)).intValue();
/*     */             wrapper.write((Type)Type.UNSIGNED_BYTE, windowId);
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(slots.intValue()));
/*     */             wrapper.write((Type)Type.INT, Integer.valueOf(entityId));
/*     */           } else {
/*     */             wrapper.setPacketType((PacketType)ClientboundPackets1_14.OPEN_WINDOW);
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(windowId.intValue()));
/*     */             int typeId = -1;
/*     */             switch (type) {
/*     */               case "minecraft:crafting_table":
/*     */                 typeId = 11;
/*     */                 break;
/*     */               
/*     */               case "minecraft:furnace":
/*     */                 typeId = 13;
/*     */                 break;
/*     */               
/*     */               case "minecraft:dropper":
/*     */               case "minecraft:dispenser":
/*     */                 typeId = 6;
/*     */                 break;
/*     */               
/*     */               case "minecraft:enchanting_table":
/*     */                 typeId = 12;
/*     */                 break;
/*     */               
/*     */               case "minecraft:brewing_stand":
/*     */                 typeId = 10;
/*     */                 break;
/*     */               
/*     */               case "minecraft:villager":
/*     */                 typeId = 18;
/*     */                 break;
/*     */               
/*     */               case "minecraft:beacon":
/*     */                 typeId = 8;
/*     */                 break;
/*     */               
/*     */               case "minecraft:anvil":
/*     */                 typeId = 7;
/*     */                 break;
/*     */               case "minecraft:hopper":
/*     */                 typeId = 15;
/*     */                 break;
/*     */               case "minecraft:shulker_box":
/*     */                 typeId = 19;
/*     */                 break;
/*     */               default:
/*     */                 if (slots.shortValue() > 0 && slots.shortValue() <= 54) {
/*     */                   typeId = slots.shortValue() / 9 - 1;
/*     */                 }
/*     */                 break;
/*     */             } 
/*     */             if (typeId == -1) {
/*     */               Via.getPlatform().getLogger().warning("Can't open inventory for 1.14 player! Type: " + type + " Size: " + slots);
/*     */             }
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(typeId));
/*     */             wrapper.write(Type.COMPONENT, title);
/*     */           } 
/*     */         });
/* 138 */     registerWindowItems((ClientboundPacketType)ClientboundPackets1_13.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
/* 139 */     registerSetSlot((ClientboundPacketType)ClientboundPackets1_13.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
/*     */     
/* 141 */     ((Protocol1_14To1_13_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 144 */             map(Type.STRING);
/* 145 */             handler(wrapper -> {
/*     */                   String channel = (String)wrapper.get(Type.STRING, 0);
/*     */                   
/*     */                   if (channel.equals("minecraft:trader_list") || channel.equals("trader_list")) {
/*     */                     wrapper.setPacketType((PacketType)ClientboundPackets1_14.TRADE_LIST);
/*     */                     
/*     */                     wrapper.resetReader();
/*     */                     
/*     */                     wrapper.read(Type.STRING);
/*     */                     
/*     */                     int windowId = ((Integer)wrapper.read((Type)Type.INT)).intValue();
/*     */                     
/*     */                     EntityTracker1_14 tracker = (EntityTracker1_14)wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
/*     */                     
/*     */                     tracker.setLatestTradeWindowId(windowId);
/*     */                     
/*     */                     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(windowId));
/*     */                     
/*     */                     int size = ((Short)wrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                     
/*     */                     for (int i = 0; i < size; i++) {
/*     */                       InventoryPackets.this.handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */                       InventoryPackets.this.handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */                       boolean secondItem = ((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue();
/*     */                       if (secondItem) {
/*     */                         InventoryPackets.this.handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */                       }
/*     */                       wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                       wrapper.passthrough((Type)Type.INT);
/*     */                       wrapper.passthrough((Type)Type.INT);
/*     */                       wrapper.write((Type)Type.INT, Integer.valueOf(0));
/*     */                       wrapper.write((Type)Type.INT, Integer.valueOf(0));
/*     */                       wrapper.write((Type)Type.FLOAT, Float.valueOf(0.0F));
/*     */                     } 
/*     */                     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                     wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                   } else if (channel.equals("minecraft:book_open") || channel.equals("book_open")) {
/*     */                     int hand = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                     wrapper.clearPacket();
/*     */                     wrapper.setPacketType((PacketType)ClientboundPackets1_14.OPEN_BOOK);
/*     */                     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(hand));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 191 */     registerEntityEquipment((ClientboundPacketType)ClientboundPackets1_13.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
/*     */     
/* 193 */     RecipeRewriter<ClientboundPackets1_13> recipeRewriter = new RecipeRewriter(this.protocol);
/* 194 */     ((Protocol1_14To1_13_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.DECLARE_RECIPES, wrapper -> {
/*     */           int size = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           int deleted = 0;
/*     */           
/*     */           for (int i = 0; i < size; i++) {
/*     */             String id = (String)wrapper.read(Type.STRING);
/*     */             
/*     */             String type = (String)wrapper.read(Type.STRING);
/*     */             if (REMOVED_RECIPE_TYPES.contains(type)) {
/*     */               deleted++;
/*     */             } else {
/*     */               wrapper.write(Type.STRING, type);
/*     */               wrapper.write(Type.STRING, id);
/*     */               recipeRewriter.handleRecipeType(wrapper, type);
/*     */             } 
/*     */           } 
/*     */           wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(size - deleted));
/*     */         });
/* 213 */     registerClickWindow((ServerboundPacketType)ServerboundPackets1_14.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
/*     */     
/* 215 */     ((Protocol1_14To1_13_2)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_14.SELECT_TRADE, wrapper -> {
/*     */           PacketWrapper resyncPacket = wrapper.create((PacketType)ServerboundPackets1_13.CLICK_WINDOW);
/*     */           
/*     */           EntityTracker1_14 tracker = (EntityTracker1_14)wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
/*     */           
/*     */           resyncPacket.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)tracker.getLatestTradeWindowId()));
/*     */           resyncPacket.write((Type)Type.SHORT, Short.valueOf((short)-999));
/*     */           resyncPacket.write((Type)Type.BYTE, Byte.valueOf((byte)2));
/*     */           resyncPacket.write((Type)Type.SHORT, Short.valueOf((short)ThreadLocalRandom.current().nextInt()));
/*     */           resyncPacket.write((Type)Type.VAR_INT, Integer.valueOf(5));
/*     */           CompoundTag tag = new CompoundTag();
/*     */           tag.put("force_resync", (Tag)new DoubleTag(Double.NaN));
/*     */           resyncPacket.write(Type.FLAT_VAR_INT_ITEM, new DataItem(1, (byte)1, (short)0, tag));
/*     */           resyncPacket.scheduleSendToServer(Protocol1_14To1_13_2.class);
/*     */         });
/* 230 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
/*     */     
/* 232 */     registerSpawnParticle((ClientboundPacketType)ClientboundPackets1_13.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, (Type)Type.FLOAT);
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToClient(Item item) {
/* 237 */     if (item == null) return null; 
/* 238 */     item.setIdentifier(Protocol1_14To1_13_2.MAPPINGS.getNewItemId(item.identifier()));
/*     */     
/* 240 */     if (item.tag() == null) return item;
/*     */ 
/*     */     
/* 243 */     Tag displayTag = item.tag().get("display");
/* 244 */     if (displayTag instanceof CompoundTag) {
/* 245 */       CompoundTag display = (CompoundTag)displayTag;
/* 246 */       Tag loreTag = display.get("Lore");
/* 247 */       if (loreTag instanceof ListTag) {
/* 248 */         ListTag lore = (ListTag)loreTag;
/* 249 */         display.put(NBT_TAG_NAME + "|Lore", (Tag)new ListTag(lore.clone().getValue()));
/* 250 */         for (Tag loreEntry : lore) {
/* 251 */           if (loreEntry instanceof StringTag) {
/* 252 */             String jsonText = ChatRewriter.legacyTextToJsonString(((StringTag)loreEntry).getValue(), true);
/* 253 */             ((StringTag)loreEntry).setValue(jsonText);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 258 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToServer(Item item) {
/* 263 */     if (item == null) return null; 
/* 264 */     item.setIdentifier(Protocol1_14To1_13_2.MAPPINGS.getOldItemId(item.identifier()));
/*     */     
/* 266 */     if (item.tag() == null) return item;
/*     */ 
/*     */     
/* 269 */     Tag displayTag = item.tag().get("display");
/* 270 */     if (displayTag instanceof CompoundTag) {
/* 271 */       CompoundTag display = (CompoundTag)displayTag;
/* 272 */       Tag loreTag = display.get("Lore");
/* 273 */       if (loreTag instanceof ListTag) {
/* 274 */         ListTag lore = (ListTag)loreTag;
/* 275 */         ListTag savedLore = (ListTag)display.remove(NBT_TAG_NAME + "|Lore");
/* 276 */         if (savedLore != null) {
/* 277 */           display.put("Lore", (Tag)new ListTag(savedLore.getValue()));
/*     */         } else {
/* 279 */           for (Tag loreEntry : lore) {
/* 280 */             if (loreEntry instanceof StringTag) {
/* 281 */               ((StringTag)loreEntry).setValue(ChatRewriter.jsonToLegacyText(((StringTag)loreEntry).getValue()));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 287 */     return item;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_14to1_13_2\packets\InventoryPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
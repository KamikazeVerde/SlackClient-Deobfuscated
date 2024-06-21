/*     */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.InventoryTracker;
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
/*     */ public class InventoryPackets
/*     */ {
/*     */   public static void register(Protocol1_9To1_8 protocol) {
/*  37 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.WINDOW_PROPERTY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register()
/*     */           {
/*  41 */             map((Type)Type.UNSIGNED_BYTE);
/*  42 */             map((Type)Type.SHORT);
/*  43 */             map((Type)Type.SHORT);
/*     */             
/*  45 */             handler(wrapper -> {
/*     */                   short windowId = ((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */ 
/*     */                   
/*     */                   short property = ((Short)wrapper.get((Type)Type.SHORT, 0)).shortValue();
/*     */                   
/*     */                   short value = ((Short)wrapper.get((Type)Type.SHORT, 1)).shortValue();
/*     */                   
/*     */                   InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
/*     */                   
/*     */                   if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equalsIgnoreCase("minecraft:enchanting_table") && property > 3 && property < 7) {
/*     */                     short level = (short)(value >> 8);
/*     */                     
/*     */                     short enchantID = (short)(value & 0xFF);
/*     */                     
/*     */                     wrapper.create(wrapper.getId(), ()).scheduleSend(Protocol1_9To1_8.class);
/*     */                     
/*     */                     wrapper.set((Type)Type.SHORT, 0, Short.valueOf((short)(property + 3)));
/*     */                     
/*     */                     wrapper.set((Type)Type.SHORT, 1, Short.valueOf(level));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/*  70 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.OPEN_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register()
/*     */           {
/*  74 */             map((Type)Type.UNSIGNED_BYTE);
/*  75 */             map(Type.STRING);
/*  76 */             map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
/*  77 */             map((Type)Type.UNSIGNED_BYTE);
/*     */ 
/*     */             
/*  80 */             handler(wrapper -> {
/*     */                   String inventory = (String)wrapper.get(Type.STRING, 0);
/*     */                   
/*     */                   InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
/*     */                   inventoryTracker.setInventory(inventory);
/*     */                 });
/*  86 */             handler(wrapper -> {
/*     */                   String inventory = (String)wrapper.get(Type.STRING, 0);
/*     */                   if (inventory.equals("minecraft:brewing_stand")) {
/*     */                     wrapper.set((Type)Type.UNSIGNED_BYTE, 1, Short.valueOf((short)(((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 1)).shortValue() + 1)));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*  94 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SET_SLOT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  97 */             map((Type)Type.UNSIGNED_BYTE);
/*  98 */             map((Type)Type.SHORT);
/*  99 */             map(Type.ITEM);
/* 100 */             handler(wrapper -> {
/*     */                   Item stack = (Item)wrapper.get(Type.ITEM, 0);
/*     */                   
/* 103 */                   boolean showShieldWhenSwordInHand = (Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking());
/*     */ 
/*     */                   
/*     */                   if (showShieldWhenSwordInHand) {
/*     */                     InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
/*     */ 
/*     */                     
/*     */                     EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                     
/*     */                     short slotID = ((Short)wrapper.get((Type)Type.SHORT, 0)).shortValue();
/*     */                     
/*     */                     byte windowId = ((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).byteValue();
/*     */                     
/*     */                     inventoryTracker.setItemId((short)windowId, slotID, (stack == null) ? 0 : stack.identifier());
/*     */                     
/*     */                     entityTracker.syncShieldWithSword();
/*     */                   } 
/*     */                   
/*     */                   ItemRewriter.toClient(stack);
/*     */                 });
/*     */             
/* 124 */             handler(wrapper -> {
/*     */                   InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
/*     */ 
/*     */                   
/*     */                   short slotID = ((Short)wrapper.get((Type)Type.SHORT, 0)).shortValue();
/*     */ 
/*     */                   
/*     */                   if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equals("minecraft:brewing_stand") && slotID >= 4) {
/*     */                     wrapper.set((Type)Type.SHORT, 0, Short.valueOf((short)(slotID + 1)));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 138 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.WINDOW_ITEMS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register()
/*     */           {
/* 142 */             map((Type)Type.UNSIGNED_BYTE);
/* 143 */             map(Type.ITEM_ARRAY);
/*     */             
/* 145 */             handler(wrapper -> {
/*     */                   Item[] stacks = (Item[])wrapper.get(Type.ITEM_ARRAY, 0);
/*     */                   
/*     */                   Short windowId = (Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0);
/*     */                   
/*     */                   InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
/*     */                   EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/* 152 */                   boolean showShieldWhenSwordInHand = (Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking());
/*     */ 
/*     */                   
/*     */                   short i;
/*     */                   
/*     */                   for (i = 0; i < stacks.length; i = (short)(i + 1)) {
/*     */                     Item stack = stacks[i];
/*     */                     
/*     */                     if (showShieldWhenSwordInHand) {
/*     */                       inventoryTracker.setItemId(windowId.shortValue(), i, (stack == null) ? 0 : stack.identifier());
/*     */                     }
/*     */                     
/*     */                     ItemRewriter.toClient(stack);
/*     */                   } 
/*     */                   
/*     */                   if (showShieldWhenSwordInHand) {
/*     */                     entityTracker.syncShieldWithSword();
/*     */                   }
/*     */                 });
/*     */             
/* 172 */             handler(wrapper -> {
/*     */                   InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
/*     */                   
/*     */                   if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equals("minecraft:brewing_stand")) {
/*     */                     Item[] oldStack = (Item[])wrapper.get(Type.ITEM_ARRAY, 0);
/*     */                     
/*     */                     Item[] newStack = new Item[oldStack.length + 1];
/*     */                     
/*     */                     for (int i = 0; i < newStack.length; i++) {
/*     */                       if (i > 4) {
/*     */                         newStack[i] = oldStack[i - 1];
/*     */                       } else if (i != 4) {
/*     */                         newStack[i] = oldStack[i];
/*     */                       } 
/*     */                     } 
/*     */                     
/*     */                     wrapper.set(Type.ITEM_ARRAY, 0, newStack);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 193 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.CLOSE_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register()
/*     */           {
/* 197 */             map((Type)Type.UNSIGNED_BYTE);
/*     */             
/* 199 */             handler(wrapper -> {
/*     */                   InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
/*     */                   
/*     */                   inventoryTracker.setInventory(null);
/*     */                   inventoryTracker.resetInventory(((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue());
/*     */                 });
/*     */           }
/*     */         });
/* 207 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.MAP_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register()
/*     */           {
/* 211 */             map((Type)Type.VAR_INT);
/* 212 */             map((Type)Type.BYTE);
/* 213 */             handler(wrapper -> wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(true)));
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 222 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9.CREATIVE_INVENTORY_ACTION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register()
/*     */           {
/* 226 */             map((Type)Type.SHORT);
/* 227 */             map(Type.ITEM);
/* 228 */             handler(wrapper -> {
/*     */                   Item stack = (Item)wrapper.get(Type.ITEM, 0);
/*     */                   
/* 231 */                   boolean showShieldWhenSwordInHand = (Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking());
/*     */ 
/*     */                   
/*     */                   if (showShieldWhenSwordInHand) {
/*     */                     InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
/*     */                     
/*     */                     EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                     
/*     */                     short slotID = ((Short)wrapper.get((Type)Type.SHORT, 0)).shortValue();
/*     */                     
/*     */                     inventoryTracker.setItemId((short)0, slotID, (stack == null) ? 0 : stack.identifier());
/*     */                     
/*     */                     entityTracker.syncShieldWithSword();
/*     */                   } 
/*     */                   
/*     */                   ItemRewriter.toServer(stack);
/*     */                 });
/*     */             
/* 249 */             handler(wrapper -> {
/*     */                   final short slot = ((Short)wrapper.get((Type)Type.SHORT, 0)).shortValue();
/*     */                   boolean throwItem = (slot == 45);
/*     */                   if (throwItem) {
/*     */                     wrapper.create((PacketType)ClientboundPackets1_9.SET_SLOT, new PacketHandler()
/*     */                         {
/*     */                           public void handle(PacketWrapper wrapper) throws Exception
/*     */                           {
/* 257 */                             wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)0));
/* 258 */                             wrapper.write((Type)Type.SHORT, Short.valueOf(slot));
/* 259 */                             wrapper.write(Type.ITEM, null);
/*     */                           }
/*     */                         }).send(Protocol1_9To1_8.class);
/*     */                     
/*     */                     wrapper.set((Type)Type.SHORT, 0, Short.valueOf((short)-999));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 269 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9.CLICK_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register()
/*     */           {
/* 273 */             map((Type)Type.UNSIGNED_BYTE);
/* 274 */             map((Type)Type.SHORT);
/* 275 */             map((Type)Type.BYTE);
/* 276 */             map((Type)Type.SHORT);
/* 277 */             map((Type)Type.VAR_INT, (Type)Type.BYTE);
/* 278 */             map(Type.ITEM);
/* 279 */             handler(wrapper -> {
/*     */                   Item stack = (Item)wrapper.get(Type.ITEM, 0);
/*     */                   
/*     */                   if (Via.getConfig().isShowShieldWhenSwordInHand()) {
/*     */                     Short windowId = (Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0);
/*     */                     
/*     */                     byte mode = ((Byte)wrapper.get((Type)Type.BYTE, 1)).byteValue();
/*     */                     
/*     */                     short hoverSlot = ((Short)wrapper.get((Type)Type.SHORT, 0)).shortValue();
/*     */                     
/*     */                     byte button = ((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                     
/*     */                     InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
/*     */                     inventoryTracker.handleWindowClick(wrapper.user(), windowId.shortValue(), mode, hoverSlot, button);
/*     */                   } 
/*     */                   ItemRewriter.toServer(stack);
/*     */                 });
/* 296 */             handler(wrapper -> {
/*     */                   final short windowID = ((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   final short slot = ((Short)wrapper.get((Type)Type.SHORT, 0)).shortValue();
/* 299 */                   boolean throwItem = (slot == 45 && windowID == 0);
/*     */                   
/*     */                   InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
/*     */                   
/*     */                   if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equals("minecraft:brewing_stand")) {
/*     */                     if (slot == 4) {
/*     */                       throwItem = true;
/*     */                     }
/*     */                     
/*     */                     if (slot > 4) {
/*     */                       wrapper.set((Type)Type.SHORT, 0, Short.valueOf((short)(slot - 1)));
/*     */                     }
/*     */                   } 
/*     */                   if (throwItem) {
/*     */                     wrapper.create((PacketType)ClientboundPackets1_9.SET_SLOT, new PacketHandler()
/*     */                         {
/*     */                           public void handle(PacketWrapper wrapper) throws Exception
/*     */                           {
/* 317 */                             wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf(windowID));
/* 318 */                             wrapper.write((Type)Type.SHORT, Short.valueOf(slot));
/* 319 */                             wrapper.write(Type.ITEM, null);
/*     */                           }
/*     */                         }).scheduleSend(Protocol1_9To1_8.class);
/*     */                     
/*     */                     wrapper.set((Type)Type.BYTE, 0, Byte.valueOf((byte)0));
/*     */                     
/*     */                     wrapper.set((Type)Type.BYTE, 1, Byte.valueOf((byte)0));
/*     */                     wrapper.set((Type)Type.SHORT, 0, Short.valueOf((short)-999));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 331 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9.CLOSE_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register()
/*     */           {
/* 335 */             map((Type)Type.UNSIGNED_BYTE);
/*     */ 
/*     */             
/* 338 */             handler(wrapper -> {
/*     */                   InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
/*     */                   
/*     */                   inventoryTracker.setInventory(null);
/*     */                   inventoryTracker.resetInventory(((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue());
/*     */                 });
/*     */           }
/*     */         });
/* 346 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_9.HELD_ITEM_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 349 */             map((Type)Type.SHORT);
/*     */ 
/*     */             
/* 352 */             handler(wrapper -> {
/* 353 */                   boolean showShieldWhenSwordInHand = (Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking());
/*     */                   EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
/*     */                   if (entityTracker.isBlocking()) {
/*     */                     entityTracker.setBlocking(false);
/*     */                     if (!showShieldWhenSwordInHand)
/*     */                       entityTracker.setSecondHand(null); 
/*     */                   } 
/*     */                   if (showShieldWhenSwordInHand) {
/*     */                     entityTracker.setHeldItemSlot(((Short)wrapper.get((Type)Type.SHORT, 0)).shortValue());
/*     */                     entityTracker.syncShieldWithSword();
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\packets\InventoryPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
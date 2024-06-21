/*     */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonParser;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ItemRewriter;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Windows;
/*     */ 
/*     */ 
/*     */ public class InventoryPackets
/*     */ {
/*     */   public static void register(Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8> protocol) {
/*  24 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.CLOSE_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  27 */             map((Type)Type.UNSIGNED_BYTE);
/*  28 */             handler(packetWrapper -> {
/*     */                   short windowsId = ((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   
/*     */                   ((Windows)packetWrapper.user().get(Windows.class)).remove(windowsId);
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/*  36 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.OPEN_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  39 */             map((Type)Type.UNSIGNED_BYTE);
/*  40 */             map(Type.STRING);
/*  41 */             map(Type.COMPONENT);
/*  42 */             map((Type)Type.UNSIGNED_BYTE);
/*  43 */             handler(packetWrapper -> {
/*     */                   String type = (String)packetWrapper.get(Type.STRING, 0); if (type.equals("EntityHorse"))
/*     */                     packetWrapper.passthrough((Type)Type.INT); 
/*     */                 });
/*  47 */             handler(packetWrapper -> {
/*     */                   short windowId = ((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   String windowType = (String)packetWrapper.get(Type.STRING, 0);
/*     */                   ((Windows)packetWrapper.user().get(Windows.class)).put(windowId, windowType);
/*     */                 });
/*  52 */             handler(packetWrapper -> {
/*     */                   String type = (String)packetWrapper.get(Type.STRING, 0);
/*     */                   
/*     */                   if (type.equalsIgnoreCase("minecraft:shulker_box")) {
/*     */                     packetWrapper.set(Type.STRING, 0, type = "minecraft:container");
/*     */                   }
/*     */                   
/*     */                   String name = ((JsonElement)packetWrapper.get(Type.COMPONENT, 0)).toString();
/*     */                   if (name.equalsIgnoreCase("{\"translate\":\"container.shulkerBox\"}")) {
/*     */                     packetWrapper.set(Type.COMPONENT, 0, JsonParser.parseString("{\"text\":\"Shulker Box\"}"));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*  66 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.WINDOW_ITEMS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  69 */             map((Type)Type.UNSIGNED_BYTE);
/*  70 */             handler(packetWrapper -> {
/*     */                   short windowId = ((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   
/*     */                   Item[] items = (Item[])packetWrapper.read(Type.ITEM_ARRAY);
/*     */                   
/*     */                   for (int i = 0; i < items.length; i++) {
/*     */                     items[i] = ItemRewriter.toClient(items[i]);
/*     */                   }
/*     */                   
/*     */                   if (windowId == 0 && items.length == 46) {
/*     */                     Item[] old = items;
/*     */                     
/*     */                     items = new Item[45];
/*     */                     System.arraycopy(old, 0, items, 0, 45);
/*     */                   } else {
/*     */                     String type = ((Windows)packetWrapper.user().get(Windows.class)).get(windowId);
/*     */                     if (type != null && type.equalsIgnoreCase("minecraft:brewing_stand")) {
/*     */                       System.arraycopy(items, 0, ((Windows)packetWrapper.user().get(Windows.class)).getBrewingItems(windowId), 0, 4);
/*     */                       Windows.updateBrewingStand(packetWrapper.user(), items[4], windowId);
/*     */                       Item[] old = items;
/*     */                       items = new Item[old.length - 1];
/*     */                       System.arraycopy(old, 0, items, 0, 4);
/*     */                       System.arraycopy(old, 5, items, 4, old.length - 5);
/*     */                     } 
/*     */                   } 
/*     */                   packetWrapper.write(Type.ITEM_ARRAY, items);
/*     */                 });
/*     */           }
/*     */         });
/*  99 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SET_SLOT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 102 */             map((Type)Type.UNSIGNED_BYTE);
/* 103 */             map((Type)Type.SHORT);
/* 104 */             map(Type.ITEM);
/* 105 */             handler(packetWrapper -> {
/*     */                   packetWrapper.set(Type.ITEM, 0, ItemRewriter.toClient((Item)packetWrapper.get(Type.ITEM, 0)));
/*     */                   
/*     */                   byte windowId = ((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0)).byteValue();
/*     */                   short slot = ((Short)packetWrapper.get((Type)Type.SHORT, 0)).shortValue();
/*     */                   if (windowId == 0 && slot == 45) {
/*     */                     packetWrapper.cancel();
/*     */                     return;
/*     */                   } 
/*     */                   String type = ((Windows)packetWrapper.user().get(Windows.class)).get((short)windowId);
/*     */                   if (type == null) {
/*     */                     return;
/*     */                   }
/*     */                   if (type.equalsIgnoreCase("minecraft:brewing_stand")) {
/*     */                     if (slot > 4) {
/*     */                       slot = (short)(slot - 1);
/*     */                       packetWrapper.set((Type)Type.SHORT, 0, Short.valueOf(slot));
/*     */                     } else if (slot == 4) {
/*     */                       packetWrapper.cancel();
/*     */                       Windows.updateBrewingStand(packetWrapper.user(), (Item)packetWrapper.get(Type.ITEM, 0), (short)windowId);
/*     */                     } else {
/*     */                       ((Windows)packetWrapper.user().get(Windows.class)).getBrewingItems((short)windowId)[slot] = (Item)packetWrapper.get(Type.ITEM, 0);
/*     */                     } 
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 132 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.CLOSE_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 135 */             map((Type)Type.UNSIGNED_BYTE);
/* 136 */             handler(packetWrapper -> {
/*     */                   short windowsId = ((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   
/*     */                   ((Windows)packetWrapper.user().get(Windows.class)).remove(windowsId);
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 144 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.CLICK_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 147 */             map((Type)Type.UNSIGNED_BYTE);
/* 148 */             map((Type)Type.SHORT);
/* 149 */             map((Type)Type.BYTE);
/* 150 */             map((Type)Type.SHORT);
/* 151 */             map((Type)Type.BYTE, (Type)Type.VAR_INT);
/* 152 */             map(Type.ITEM);
/* 153 */             handler(packetWrapper -> packetWrapper.set(Type.ITEM, 0, ItemRewriter.toServer((Item)packetWrapper.get(Type.ITEM, 0))));
/* 154 */             handler(packetWrapper -> {
/*     */                   short windowId = ((Short)packetWrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                   Windows windows = (Windows)packetWrapper.user().get(Windows.class);
/*     */                   String type = windows.get(windowId);
/*     */                   if (type == null)
/*     */                     return; 
/*     */                   if (type.equalsIgnoreCase("minecraft:brewing_stand")) {
/*     */                     short slot = ((Short)packetWrapper.get((Type)Type.SHORT, 0)).shortValue();
/*     */                     if (slot > 3) {
/*     */                       slot = (short)(slot + 1);
/*     */                       packetWrapper.set((Type)Type.SHORT, 0, Short.valueOf(slot));
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 170 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_8.CREATIVE_INVENTORY_ACTION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 173 */             map((Type)Type.SHORT);
/* 174 */             map(Type.ITEM);
/* 175 */             handler(packetWrapper -> packetWrapper.set(Type.ITEM, 0, ItemRewriter.toServer((Item)packetWrapper.get(Type.ITEM, 0))));
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\packets\InventoryPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viaversion.protocols.protocol1_12to1_11_1.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ServerboundPackets1_12;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.providers.InventoryQuickMoveProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.rewriter.ItemRewriter;
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
/*     */   extends ItemRewriter<ClientboundPackets1_9_3, ServerboundPackets1_12, Protocol1_12To1_11_1>
/*     */ {
/*     */   public InventoryPackets(Protocol1_12To1_11_1 protocol) {
/*  34 */     super((Protocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerPackets() {
/*  39 */     registerSetSlot((ClientboundPacketType)ClientboundPackets1_9_3.SET_SLOT, Type.ITEM);
/*  40 */     registerWindowItems((ClientboundPacketType)ClientboundPackets1_9_3.WINDOW_ITEMS, Type.ITEM_ARRAY);
/*  41 */     registerEntityEquipment((ClientboundPacketType)ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
/*     */ 
/*     */     
/*  44 */     ((Protocol1_12To1_11_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  47 */             map(Type.STRING);
/*     */             
/*  49 */             handler(wrapper -> {
/*     */                   if (((String)wrapper.get(Type.STRING, 0)).equalsIgnoreCase("MC|TrList")) {
/*     */                     wrapper.passthrough((Type)Type.INT);
/*     */                     
/*     */                     int size = ((Short)wrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                     
/*     */                     for (int i = 0; i < size; i++) {
/*     */                       InventoryPackets.this.handleItemToClient((Item)wrapper.passthrough(Type.ITEM));
/*     */                       
/*     */                       InventoryPackets.this.handleItemToClient((Item)wrapper.passthrough(Type.ITEM));
/*     */                       
/*     */                       boolean secondItem = ((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue();
/*     */                       
/*     */                       if (secondItem) {
/*     */                         InventoryPackets.this.handleItemToClient((Item)wrapper.passthrough(Type.ITEM));
/*     */                       }
/*     */                       wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                       wrapper.passthrough((Type)Type.INT);
/*     */                       wrapper.passthrough((Type)Type.INT);
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  73 */     ((Protocol1_12To1_11_1)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_12.CLICK_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  76 */             map((Type)Type.UNSIGNED_BYTE);
/*  77 */             map((Type)Type.SHORT);
/*  78 */             map((Type)Type.BYTE);
/*  79 */             map((Type)Type.SHORT);
/*  80 */             map((Type)Type.VAR_INT);
/*  81 */             map(Type.ITEM);
/*     */             
/*  83 */             handler(wrapper -> {
/*     */                   Item item = (Item)wrapper.get(Type.ITEM, 0);
/*     */                   
/*     */                   if (!Via.getConfig().is1_12QuickMoveActionFix()) {
/*     */                     InventoryPackets.this.handleItemToServer(item);
/*     */                     
/*     */                     return;
/*     */                   } 
/*     */                   
/*     */                   byte button = ((Byte)wrapper.get((Type)Type.BYTE, 0)).byteValue();
/*     */                   
/*     */                   int mode = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   if (mode == 1 && button == 0 && item == null) {
/*     */                     short windowId = ((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue();
/*     */                     short slotId = ((Short)wrapper.get((Type)Type.SHORT, 0)).shortValue();
/*     */                     short actionId = ((Short)wrapper.get((Type)Type.SHORT, 1)).shortValue();
/*     */                     InventoryQuickMoveProvider provider = (InventoryQuickMoveProvider)Via.getManager().getProviders().get(InventoryQuickMoveProvider.class);
/*     */                     boolean succeed = provider.registerQuickMoveAction(windowId, slotId, actionId, wrapper.user());
/*     */                     if (succeed) {
/*     */                       wrapper.cancel();
/*     */                     }
/*     */                   } else {
/*     */                     InventoryPackets.this.handleItemToServer(item);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 111 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_12.CREATIVE_INVENTORY_ACTION, Type.ITEM);
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToServer(Item item) {
/* 116 */     if (item == null) return null;
/*     */     
/* 118 */     if (item.identifier() == 355) {
/* 119 */       item.setData((short)0);
/*     */     }
/*     */     
/* 122 */     boolean newItem = (item.identifier() >= 235 && item.identifier() <= 252);
/* 123 */     int i = newItem | ((item.identifier() == 453) ? 1 : 0);
/* 124 */     if (i != 0) {
/* 125 */       item.setIdentifier(1);
/* 126 */       item.setData((short)0);
/*     */     } 
/* 128 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToClient(Item item) {
/* 133 */     if (item == null) return null; 
/* 134 */     if (item.identifier() == 355) {
/* 135 */       item.setData((short)14);
/*     */     }
/* 137 */     return item;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_12to1_11_1\packets\InventoryPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
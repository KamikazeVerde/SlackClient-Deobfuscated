/*    */ package com.viaversion.viaversion.protocols.protocol1_11to1_10.packets;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_11to1_10.EntityIdRewriter;
/*    */ import com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
/*    */ import com.viaversion.viaversion.rewriter.ItemRewriter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InventoryPackets
/*    */   extends ItemRewriter<ClientboundPackets1_9_3, ServerboundPackets1_9_3, Protocol1_11To1_10>
/*    */ {
/*    */   public InventoryPackets(Protocol1_11To1_10 protocol) {
/* 32 */     super((Protocol)protocol);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerPackets() {
/* 37 */     registerSetSlot((ClientboundPacketType)ClientboundPackets1_9_3.SET_SLOT, Type.ITEM);
/* 38 */     registerWindowItems((ClientboundPacketType)ClientboundPackets1_9_3.WINDOW_ITEMS, Type.ITEM_ARRAY);
/* 39 */     registerEntityEquipment((ClientboundPacketType)ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
/*    */ 
/*    */     
/* 42 */     ((Protocol1_11To1_10)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 45 */             map(Type.STRING);
/*    */             
/* 47 */             handler(wrapper -> {
/*    */                   if (((String)wrapper.get(Type.STRING, 0)).equalsIgnoreCase("MC|TrList")) {
/*    */                     wrapper.passthrough((Type)Type.INT);
/*    */                     
/*    */                     int size = ((Short)wrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
/*    */                     
/*    */                     for (int i = 0; i < size; i++) {
/*    */                       EntityIdRewriter.toClientItem((Item)wrapper.passthrough(Type.ITEM));
/*    */                       
/*    */                       EntityIdRewriter.toClientItem((Item)wrapper.passthrough(Type.ITEM));
/*    */                       boolean secondItem = ((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue();
/*    */                       if (secondItem) {
/*    */                         EntityIdRewriter.toClientItem((Item)wrapper.passthrough(Type.ITEM));
/*    */                       }
/*    */                       wrapper.passthrough((Type)Type.BOOLEAN);
/*    */                       wrapper.passthrough((Type)Type.INT);
/*    */                       wrapper.passthrough((Type)Type.INT);
/*    */                     } 
/*    */                   } 
/*    */                 });
/*    */           }
/*    */         });
/* 69 */     registerClickWindow((ServerboundPacketType)ServerboundPackets1_9_3.CLICK_WINDOW, Type.ITEM);
/* 70 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
/*    */   }
/*    */ 
/*    */   
/*    */   public Item handleItemToClient(Item item) {
/* 75 */     EntityIdRewriter.toClientItem(item);
/* 76 */     return item;
/*    */   }
/*    */ 
/*    */   
/*    */   public Item handleItemToServer(Item item) {
/* 81 */     EntityIdRewriter.toServerItem(item);
/* 82 */     if (item == null) return null; 
/* 83 */     boolean newItem = (item.identifier() >= 218 && item.identifier() <= 234);
/* 84 */     int i = newItem | ((item.identifier() == 449 || item.identifier() == 450) ? 1 : 0);
/* 85 */     if (i != 0) {
/* 86 */       item.setIdentifier(1);
/* 87 */       item.setData((short)0);
/*    */     } 
/* 89 */     return item;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_11to1_10\packets\InventoryPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
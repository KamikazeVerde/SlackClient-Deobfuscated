/*    */ package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;
/*    */ 
/*    */ import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
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
/*    */ 
/*    */ public class InventoryPackets1_13_1
/*    */   extends ItemRewriter<ClientboundPackets1_13, ServerboundPackets1_13, Protocol1_13To1_13_1>
/*    */ {
/*    */   public InventoryPackets1_13_1(Protocol1_13To1_13_1 protocol) {
/* 30 */     super((Protocol)protocol);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerPackets() {
/* 35 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_13.COOLDOWN);
/* 36 */     registerWindowItems((ClientboundPacketType)ClientboundPackets1_13.WINDOW_ITEMS, Type.FLAT_ITEM_ARRAY);
/* 37 */     registerSetSlot((ClientboundPacketType)ClientboundPackets1_13.SET_SLOT, Type.FLAT_ITEM);
/*    */     
/* 39 */     ((Protocol1_13To1_13_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.PLUGIN_MESSAGE, wrapper -> {
/*    */           String channel = (String)wrapper.passthrough(Type.STRING);
/*    */           
/*    */           if (channel.equals("minecraft:trader_list")) {
/*    */             wrapper.passthrough((Type)Type.INT);
/*    */             
/*    */             int size = ((Short)wrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
/*    */             
/*    */             for (int i = 0; i < size; i++) {
/*    */               Item input = (Item)wrapper.passthrough(Type.FLAT_ITEM);
/*    */               
/*    */               handleItemToClient(input);
/*    */               
/*    */               Item output = (Item)wrapper.passthrough(Type.FLAT_ITEM);
/*    */               
/*    */               handleItemToClient(output);
/*    */               
/*    */               boolean secondItem = ((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue();
/*    */               if (secondItem) {
/*    */                 Item second = (Item)wrapper.passthrough(Type.FLAT_ITEM);
/*    */                 handleItemToClient(second);
/*    */               } 
/*    */               wrapper.passthrough((Type)Type.BOOLEAN);
/*    */               wrapper.passthrough((Type)Type.INT);
/*    */               wrapper.passthrough((Type)Type.INT);
/*    */             } 
/*    */           } 
/*    */         });
/* 67 */     registerEntityEquipment((ClientboundPacketType)ClientboundPackets1_13.ENTITY_EQUIPMENT, Type.FLAT_ITEM);
/* 68 */     registerClickWindow((ServerboundPacketType)ServerboundPackets1_13.CLICK_WINDOW, Type.FLAT_ITEM);
/* 69 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, Type.FLAT_ITEM);
/*    */     
/* 71 */     registerSpawnParticle((ClientboundPacketType)ClientboundPackets1_13.SPAWN_PARTICLE, Type.FLAT_ITEM, (Type)Type.FLOAT);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_13to1_13_1\packets\InventoryPackets1_13_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
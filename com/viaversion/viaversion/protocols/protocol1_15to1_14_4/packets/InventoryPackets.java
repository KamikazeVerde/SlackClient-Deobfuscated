/*    */ package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
/*    */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
/*    */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
/*    */ import com.viaversion.viaversion.rewriter.ItemRewriter;
/*    */ import com.viaversion.viaversion.rewriter.RecipeRewriter;
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
/*    */ 
/*    */ public class InventoryPackets
/*    */   extends ItemRewriter<ClientboundPackets1_14, ServerboundPackets1_14, Protocol1_15To1_14_4>
/*    */ {
/*    */   public InventoryPackets(Protocol1_15To1_14_4 protocol) {
/* 30 */     super((Protocol)protocol);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerPackets() {
/* 35 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_14.COOLDOWN);
/* 36 */     registerWindowItems((ClientboundPacketType)ClientboundPackets1_14.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
/* 37 */     registerTradeList((ClientboundPacketType)ClientboundPackets1_14.TRADE_LIST);
/* 38 */     registerSetSlot((ClientboundPacketType)ClientboundPackets1_14.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
/* 39 */     registerEntityEquipment((ClientboundPacketType)ClientboundPackets1_14.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
/* 40 */     registerAdvancements((ClientboundPacketType)ClientboundPackets1_14.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
/*    */     
/* 42 */     (new RecipeRewriter(this.protocol)).register((ClientboundPacketType)ClientboundPackets1_14.DECLARE_RECIPES);
/*    */     
/* 44 */     registerClickWindow((ServerboundPacketType)ServerboundPackets1_14.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
/* 45 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_15to1_14_4\packets\InventoryPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
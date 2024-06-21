/*    */ package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
/*    */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
/*    */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
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
/*    */ public class InventoryPackets
/*    */   extends ItemRewriter<ClientboundPackets1_16, ServerboundPackets1_16_2, Protocol1_16_2To1_16_1>
/*    */ {
/*    */   public InventoryPackets(Protocol1_16_2To1_16_1 protocol) {
/* 30 */     super((Protocol)protocol);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerPackets() {
/* 35 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_16.COOLDOWN);
/* 36 */     registerWindowItems((ClientboundPacketType)ClientboundPackets1_16.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
/* 37 */     registerTradeList((ClientboundPacketType)ClientboundPackets1_16.TRADE_LIST);
/* 38 */     registerSetSlot((ClientboundPacketType)ClientboundPackets1_16.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
/* 39 */     registerEntityEquipmentArray((ClientboundPacketType)ClientboundPackets1_16.ENTITY_EQUIPMENT);
/* 40 */     registerAdvancements((ClientboundPacketType)ClientboundPackets1_16.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
/*    */     
/* 42 */     ((Protocol1_16_2To1_16_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_16.UNLOCK_RECIPES, wrapper -> {
/*    */           wrapper.passthrough((Type)Type.VAR_INT);
/*    */           
/*    */           wrapper.passthrough((Type)Type.BOOLEAN);
/*    */           
/*    */           wrapper.passthrough((Type)Type.BOOLEAN);
/*    */           wrapper.passthrough((Type)Type.BOOLEAN);
/*    */           wrapper.passthrough((Type)Type.BOOLEAN);
/*    */           wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*    */           wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*    */           wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*    */           wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*    */         });
/* 55 */     (new RecipeRewriter(this.protocol)).register((ClientboundPacketType)ClientboundPackets1_16.DECLARE_RECIPES);
/*    */     
/* 57 */     registerClickWindow((ServerboundPacketType)ServerboundPackets1_16_2.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
/* 58 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_16_2.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
/* 59 */     ((Protocol1_16_2To1_16_1)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_16_2.EDIT_BOOK, wrapper -> handleItemToServer((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM)));
/*    */     
/* 61 */     registerSpawnParticle((ClientboundPacketType)ClientboundPackets1_16.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, (Type)Type.DOUBLE);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_16_2to1_16_1\packets\InventoryPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
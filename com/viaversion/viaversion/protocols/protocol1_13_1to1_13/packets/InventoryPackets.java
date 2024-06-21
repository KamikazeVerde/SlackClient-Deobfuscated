/*    */ package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
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
/*    */ public class InventoryPackets
/*    */   extends ItemRewriter<ClientboundPackets1_13, ServerboundPackets1_13, Protocol1_13_1To1_13>
/*    */ {
/*    */   public InventoryPackets(Protocol1_13_1To1_13 protocol) {
/* 31 */     super((Protocol)protocol);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerPackets() {
/* 36 */     registerSetSlot((ClientboundPacketType)ClientboundPackets1_13.SET_SLOT, Type.FLAT_ITEM);
/* 37 */     registerWindowItems((ClientboundPacketType)ClientboundPackets1_13.WINDOW_ITEMS, Type.FLAT_ITEM_ARRAY);
/* 38 */     registerAdvancements((ClientboundPacketType)ClientboundPackets1_13.ADVANCEMENTS, Type.FLAT_ITEM);
/* 39 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_13.COOLDOWN);
/*    */     
/* 41 */     ((Protocol1_13_1To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 44 */             map(Type.STRING);
/* 45 */             handler(wrapper -> {
/*    */                   String channel = (String)wrapper.get(Type.STRING, 0);
/*    */                   
/*    */                   if (channel.equals("minecraft:trader_list") || channel.equals("trader_list")) {
/*    */                     wrapper.passthrough((Type)Type.INT);
/*    */                     
/*    */                     int size = ((Short)wrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
/*    */                     
/*    */                     for (int i = 0; i < size; i++) {
/*    */                       InventoryPackets.this.handleItemToClient((Item)wrapper.passthrough(Type.FLAT_ITEM));
/*    */                       
/*    */                       InventoryPackets.this.handleItemToClient((Item)wrapper.passthrough(Type.FLAT_ITEM));
/*    */                       
/*    */                       boolean secondItem = ((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue();
/*    */                       
/*    */                       if (secondItem) {
/*    */                         InventoryPackets.this.handleItemToClient((Item)wrapper.passthrough(Type.FLAT_ITEM));
/*    */                       }
/*    */                       
/*    */                       wrapper.passthrough((Type)Type.BOOLEAN);
/*    */                       wrapper.passthrough((Type)Type.INT);
/*    */                       wrapper.passthrough((Type)Type.INT);
/*    */                     } 
/*    */                   } 
/*    */                 });
/*    */           }
/*    */         });
/* 72 */     registerEntityEquipment((ClientboundPacketType)ClientboundPackets1_13.ENTITY_EQUIPMENT, Type.FLAT_ITEM);
/*    */     
/* 74 */     RecipeRewriter<ClientboundPackets1_13> recipeRewriter = new RecipeRewriter(this.protocol);
/* 75 */     ((Protocol1_13_1To1_13)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_13.DECLARE_RECIPES, wrapper -> {
/*    */           int size = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*    */           
/*    */           for (int i = 0; i < size; i++) {
/*    */             wrapper.passthrough(Type.STRING);
/*    */             
/*    */             String type = ((String)wrapper.passthrough(Type.STRING)).replace("minecraft:", "");
/*    */             recipeRewriter.handleRecipeType(wrapper, type);
/*    */           } 
/*    */         });
/* 85 */     registerClickWindow((ServerboundPacketType)ServerboundPackets1_13.CLICK_WINDOW, Type.FLAT_ITEM);
/* 86 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, Type.FLAT_ITEM);
/*    */     
/* 88 */     registerSpawnParticle((ClientboundPacketType)ClientboundPackets1_13.SPAWN_PARTICLE, Type.FLAT_ITEM, (Type)Type.FLOAT);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13_1to1_13\packets\InventoryPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
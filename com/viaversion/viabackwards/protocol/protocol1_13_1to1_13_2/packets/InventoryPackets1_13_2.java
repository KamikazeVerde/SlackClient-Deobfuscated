/*     */ package com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.Protocol1_13_1To1_13_2;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
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
/*     */ public class InventoryPackets1_13_2
/*     */ {
/*     */   public static void register(Protocol1_13_1To1_13_2 protocol) {
/*  29 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.SET_SLOT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  32 */             map((Type)Type.UNSIGNED_BYTE);
/*  33 */             map((Type)Type.SHORT);
/*  34 */             map(Type.FLAT_VAR_INT_ITEM, Type.FLAT_ITEM);
/*     */           }
/*     */         });
/*     */     
/*  38 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.WINDOW_ITEMS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  41 */             map((Type)Type.UNSIGNED_BYTE);
/*  42 */             map(Type.FLAT_VAR_INT_ITEM_ARRAY, Type.FLAT_ITEM_ARRAY);
/*     */           }
/*     */         });
/*     */     
/*  46 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  49 */             map(Type.STRING);
/*  50 */             handler(wrapper -> {
/*     */                   String channel = (String)wrapper.get(Type.STRING, 0);
/*     */                   
/*     */                   if (channel.equals("minecraft:trader_list") || channel.equals("trader_list")) {
/*     */                     wrapper.passthrough((Type)Type.INT);
/*     */                     
/*     */                     int size = ((Short)wrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                     
/*     */                     for (int i = 0; i < size; i++) {
/*     */                       wrapper.write(Type.FLAT_ITEM, wrapper.read(Type.FLAT_VAR_INT_ITEM));
/*     */                       
/*     */                       wrapper.write(Type.FLAT_ITEM, wrapper.read(Type.FLAT_VAR_INT_ITEM));
/*     */                       
/*     */                       boolean secondItem = ((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue();
/*     */                       
/*     */                       if (secondItem) {
/*     */                         wrapper.write(Type.FLAT_ITEM, wrapper.read(Type.FLAT_VAR_INT_ITEM));
/*     */                       }
/*     */                       wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                       wrapper.passthrough((Type)Type.INT);
/*     */                       wrapper.passthrough((Type)Type.INT);
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  76 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.ENTITY_EQUIPMENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  79 */             map((Type)Type.VAR_INT);
/*  80 */             map((Type)Type.VAR_INT);
/*  81 */             map(Type.FLAT_VAR_INT_ITEM, Type.FLAT_ITEM);
/*     */           }
/*     */         });
/*     */     
/*  85 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.DECLARE_RECIPES, wrapper -> {
/*     */           int recipesNo = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           for (int i = 0; i < recipesNo; i++) {
/*     */             wrapper.passthrough(Type.STRING);
/*     */             
/*     */             String type = (String)wrapper.passthrough(Type.STRING);
/*     */             
/*     */             if (type.equals("crafting_shapeless")) {
/*     */               wrapper.passthrough(Type.STRING);
/*     */               int ingredientsNo = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */               for (int i1 = 0; i1 < ingredientsNo; i1++) {
/*     */                 wrapper.write(Type.FLAT_ITEM_ARRAY_VAR_INT, wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT));
/*     */               }
/*     */               wrapper.write(Type.FLAT_ITEM, wrapper.read(Type.FLAT_VAR_INT_ITEM));
/*     */             } else if (type.equals("crafting_shaped")) {
/*     */               int ingredientsNo = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue() * ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */               wrapper.passthrough(Type.STRING);
/*     */               for (int i1 = 0; i1 < ingredientsNo; i1++) {
/*     */                 wrapper.write(Type.FLAT_ITEM_ARRAY_VAR_INT, wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT));
/*     */               }
/*     */               wrapper.write(Type.FLAT_ITEM, wrapper.read(Type.FLAT_VAR_INT_ITEM));
/*     */             } else if (type.equals("smelting")) {
/*     */               wrapper.passthrough(Type.STRING);
/*     */               wrapper.write(Type.FLAT_ITEM_ARRAY_VAR_INT, wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT));
/*     */               wrapper.write(Type.FLAT_ITEM, wrapper.read(Type.FLAT_VAR_INT_ITEM));
/*     */               wrapper.passthrough((Type)Type.FLOAT);
/*     */               wrapper.passthrough((Type)Type.VAR_INT);
/*     */             } 
/*     */           } 
/*     */         });
/* 116 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_13.CLICK_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 119 */             map((Type)Type.UNSIGNED_BYTE);
/* 120 */             map((Type)Type.SHORT);
/* 121 */             map((Type)Type.BYTE);
/* 122 */             map((Type)Type.SHORT);
/* 123 */             map((Type)Type.VAR_INT);
/* 124 */             map(Type.FLAT_ITEM, Type.FLAT_VAR_INT_ITEM);
/*     */           }
/*     */         });
/*     */     
/* 128 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 131 */             map((Type)Type.SHORT);
/* 132 */             map(Type.FLAT_ITEM, Type.FLAT_VAR_INT_ITEM);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_13_1to1_13_2\packets\InventoryPackets1_13_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
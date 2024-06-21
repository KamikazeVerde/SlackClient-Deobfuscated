/*    */ package com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.packets;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*    */ import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.Protocol1_19_3To1_19_4;
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*    */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ServerboundPackets1_19_3;
/*    */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.rewriter.RecipeRewriter1_19_3;
/*    */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
/*    */ import com.viaversion.viaversion.rewriter.BlockRewriter;
/*    */ import com.viaversion.viaversion.util.Key;
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
/*    */ public final class BlockItemPackets1_19_4
/*    */   extends ItemRewriter<ClientboundPackets1_19_4, ServerboundPackets1_19_3, Protocol1_19_3To1_19_4>
/*    */ {
/*    */   public BlockItemPackets1_19_4(Protocol1_19_3To1_19_4 protocol) {
/* 35 */     super((BackwardsProtocol)protocol);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerPackets() {
/* 40 */     BlockRewriter<ClientboundPackets1_19_4> blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
/* 41 */     blockRewriter.registerBlockAction((ClientboundPacketType)ClientboundPackets1_19_4.BLOCK_ACTION);
/* 42 */     blockRewriter.registerBlockChange((ClientboundPacketType)ClientboundPackets1_19_4.BLOCK_CHANGE);
/* 43 */     blockRewriter.registerVarLongMultiBlockChange((ClientboundPacketType)ClientboundPackets1_19_4.MULTI_BLOCK_CHANGE);
/* 44 */     blockRewriter.registerEffect((ClientboundPacketType)ClientboundPackets1_19_4.EFFECT, 1010, 2001);
/* 45 */     blockRewriter.registerChunkData1_19((ClientboundPacketType)ClientboundPackets1_19_4.CHUNK_DATA, com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type::new);
/* 46 */     blockRewriter.registerBlockEntityData((ClientboundPacketType)ClientboundPackets1_19_4.BLOCK_ENTITY_DATA);
/*    */     
/* 48 */     ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.OPEN_WINDOW, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 51 */             map((Type)Type.VAR_INT);
/* 52 */             map((Type)Type.VAR_INT);
/* 53 */             map(Type.COMPONENT);
/* 54 */             handler(wrapper -> {
/*    */                   int windowType = ((Integer)wrapper.get((Type)Type.VAR_INT, 1)).intValue();
/*    */                   
/*    */                   if (windowType == 21) {
/*    */                     wrapper.cancel();
/*    */                   } else if (windowType > 21) {
/*    */                     wrapper.set((Type)Type.VAR_INT, 1, Integer.valueOf(windowType - 1));
/*    */                   } 
/*    */                   
/*    */                   ((Protocol1_19_3To1_19_4)BlockItemPackets1_19_4.this.protocol).getTranslatableRewriter().processText((JsonElement)wrapper.get(Type.COMPONENT, 0));
/*    */                 });
/*    */           }
/*    */         });
/* 67 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_19_4.COOLDOWN);
/* 68 */     registerWindowItems1_17_1((ClientboundPacketType)ClientboundPackets1_19_4.WINDOW_ITEMS);
/* 69 */     registerSetSlot1_17_1((ClientboundPacketType)ClientboundPackets1_19_4.SET_SLOT);
/* 70 */     registerAdvancements((ClientboundPacketType)ClientboundPackets1_19_4.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
/* 71 */     registerEntityEquipmentArray((ClientboundPacketType)ClientboundPackets1_19_4.ENTITY_EQUIPMENT);
/* 72 */     registerClickWindow1_17_1((ServerboundPacketType)ServerboundPackets1_19_3.CLICK_WINDOW);
/* 73 */     registerTradeList1_19((ClientboundPacketType)ClientboundPackets1_19_4.TRADE_LIST);
/* 74 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_19_3.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
/* 75 */     registerWindowPropertyEnchantmentHandler((ClientboundPacketType)ClientboundPackets1_19_4.WINDOW_PROPERTY);
/* 76 */     registerSpawnParticle1_19((ClientboundPacketType)ClientboundPackets1_19_4.SPAWN_PARTICLE);
/*    */     
/* 78 */     RecipeRewriter1_19_3<ClientboundPackets1_19_4> recipeRewriter = new RecipeRewriter1_19_3<ClientboundPackets1_19_4>(this.protocol)
/*    */       {
/*    */         public void handleCraftingShaped(PacketWrapper wrapper) throws Exception {
/* 81 */           int ingredients = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue() * ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/* 82 */           wrapper.passthrough(Type.STRING);
/* 83 */           wrapper.passthrough((Type)Type.VAR_INT);
/* 84 */           for (int i = 0; i < ingredients; i++) {
/* 85 */             handleIngredient(wrapper);
/*    */           }
/* 87 */           rewrite((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*    */ 
/*    */           
/* 90 */           wrapper.read((Type)Type.BOOLEAN);
/*    */         }
/*    */       };
/* 93 */     ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.DECLARE_RECIPES, wrapper -> {
/*    */           int size = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*    */           int newSize = size;
/*    */           for (int i = 0; i < size; i++) {
/*    */             String type = (String)wrapper.read(Type.STRING);
/*    */             String cutType = Key.stripMinecraftNamespace(type);
/*    */             if (cutType.equals("smithing_transform") || cutType.equals("smithing_trim")) {
/*    */               newSize--;
/*    */               wrapper.read(Type.STRING);
/*    */               wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
/*    */               wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
/*    */               wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
/*    */               if (cutType.equals("smithing_transform"))
/*    */                 wrapper.read(Type.FLAT_VAR_INT_ITEM); 
/*    */             } else if (cutType.equals("crafting_decorated_pot")) {
/*    */               newSize--;
/*    */               wrapper.read(Type.STRING);
/*    */               wrapper.read((Type)Type.VAR_INT);
/*    */             } else {
/*    */               wrapper.write(Type.STRING, type);
/*    */               wrapper.passthrough(Type.STRING);
/*    */               recipeRewriter.handleRecipeType(wrapper, cutType);
/*    */             } 
/*    */           } 
/*    */           wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(newSize));
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_19_3to1_19_4\packets\BlockItemPackets1_19_4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
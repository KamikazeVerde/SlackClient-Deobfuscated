/*    */ package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.packets;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*    */ import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
/*    */ import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.Protocol1_19_1To1_19_3;
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*    */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ServerboundPackets1_19_1;
/*    */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
/*    */ import com.viaversion.viaversion.rewriter.BlockRewriter;
/*    */ import com.viaversion.viaversion.rewriter.RecipeRewriter;
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
/*    */ 
/*    */ 
/*    */ public final class BlockItemPackets1_19_3
/*    */   extends ItemRewriter<ClientboundPackets1_19_3, ServerboundPackets1_19_1, Protocol1_19_1To1_19_3>
/*    */ {
/*    */   public BlockItemPackets1_19_3(Protocol1_19_1To1_19_3 protocol) {
/* 35 */     super((BackwardsProtocol)protocol);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 40 */     BlockRewriter<ClientboundPackets1_19_3> blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
/* 41 */     blockRewriter.registerBlockAction((ClientboundPacketType)ClientboundPackets1_19_3.BLOCK_ACTION);
/* 42 */     blockRewriter.registerBlockChange((ClientboundPacketType)ClientboundPackets1_19_3.BLOCK_CHANGE);
/* 43 */     blockRewriter.registerVarLongMultiBlockChange((ClientboundPacketType)ClientboundPackets1_19_3.MULTI_BLOCK_CHANGE);
/* 44 */     blockRewriter.registerEffect((ClientboundPacketType)ClientboundPackets1_19_3.EFFECT, 1010, 2001);
/* 45 */     blockRewriter.registerChunkData1_19((ClientboundPacketType)ClientboundPackets1_19_3.CHUNK_DATA, com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type::new);
/* 46 */     blockRewriter.registerBlockEntityData((ClientboundPacketType)ClientboundPackets1_19_3.BLOCK_ENTITY_DATA);
/*    */     
/* 48 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_19_3.COOLDOWN);
/* 49 */     registerWindowItems1_17_1((ClientboundPacketType)ClientboundPackets1_19_3.WINDOW_ITEMS);
/* 50 */     registerSetSlot1_17_1((ClientboundPacketType)ClientboundPackets1_19_3.SET_SLOT);
/* 51 */     registerEntityEquipmentArray((ClientboundPacketType)ClientboundPackets1_19_3.ENTITY_EQUIPMENT);
/* 52 */     registerAdvancements((ClientboundPacketType)ClientboundPackets1_19_3.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
/* 53 */     registerClickWindow1_17_1((ServerboundPacketType)ServerboundPackets1_19_1.CLICK_WINDOW);
/* 54 */     registerTradeList1_19((ClientboundPacketType)ClientboundPackets1_19_3.TRADE_LIST);
/* 55 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_19_1.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
/* 56 */     registerWindowPropertyEnchantmentHandler((ClientboundPacketType)ClientboundPackets1_19_3.WINDOW_PROPERTY);
/* 57 */     registerSpawnParticle1_19((ClientboundPacketType)ClientboundPackets1_19_3.SPAWN_PARTICLE);
/*    */     
/* 59 */     ((Protocol1_19_1To1_19_3)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.EXPLOSION, (PacketHandler)new PacketHandlers()
/*    */         {
/*    */           public void register() {
/* 62 */             map((Type)Type.DOUBLE, (Type)Type.FLOAT);
/* 63 */             map((Type)Type.DOUBLE, (Type)Type.FLOAT);
/* 64 */             map((Type)Type.DOUBLE, (Type)Type.FLOAT);
/*    */           }
/*    */         });
/*    */     
/* 68 */     RecipeRewriter<ClientboundPackets1_19_3> recipeRewriter = new RecipeRewriter(this.protocol);
/* 69 */     ((Protocol1_19_1To1_19_3)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.DECLARE_RECIPES, wrapper -> {
/*    */           int size = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*    */           for (int i = 0; i < size; i++) {
/*    */             int ingredients;
/*    */             Item[] items;
/*    */             int j;
/*    */             String type = Key.stripMinecraftNamespace((String)wrapper.passthrough(Type.STRING));
/*    */             wrapper.passthrough(Type.STRING);
/*    */             switch (type) {
/*    */               case "crafting_shapeless":
/*    */                 wrapper.passthrough(Type.STRING);
/*    */                 wrapper.read((Type)Type.VAR_INT);
/*    */                 ingredients = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*    */                 for (j = 0; j < ingredients; j++) {
/*    */                   Item[] arrayOfItem = (Item[])wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
/*    */                   for (Item item : arrayOfItem)
/*    */                     handleItemToClient(item); 
/*    */                 } 
/*    */                 handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*    */                 break;
/*    */               case "crafting_shaped":
/*    */                 ingredients = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue() * ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*    */                 wrapper.passthrough(Type.STRING);
/*    */                 wrapper.read((Type)Type.VAR_INT);
/*    */                 for (j = 0; j < ingredients; j++) {
/*    */                   Item[] arrayOfItem = (Item[])wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
/*    */                   for (Item item : arrayOfItem)
/*    */                     handleItemToClient(item); 
/*    */                 } 
/*    */                 handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*    */                 break;
/*    */               case "smelting":
/*    */               case "campfire_cooking":
/*    */               case "blasting":
/*    */               case "smoking":
/*    */                 wrapper.passthrough(Type.STRING);
/*    */                 wrapper.read((Type)Type.VAR_INT);
/*    */                 items = (Item[])wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
/*    */                 for (Item item : items)
/*    */                   handleItemToClient(item); 
/*    */                 handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*    */                 wrapper.passthrough((Type)Type.FLOAT);
/*    */                 wrapper.passthrough((Type)Type.VAR_INT);
/*    */                 break;
/*    */               case "crafting_special_armordye":
/*    */               case "crafting_special_bookcloning":
/*    */               case "crafting_special_mapcloning":
/*    */               case "crafting_special_mapextending":
/*    */               case "crafting_special_firework_rocket":
/*    */               case "crafting_special_firework_star":
/*    */               case "crafting_special_firework_star_fade":
/*    */               case "crafting_special_tippedarrow":
/*    */               case "crafting_special_bannerduplicate":
/*    */               case "crafting_special_shielddecoration":
/*    */               case "crafting_special_shulkerboxcoloring":
/*    */               case "crafting_special_suspiciousstew":
/*    */               case "crafting_special_repairitem":
/*    */                 wrapper.read((Type)Type.VAR_INT);
/*    */                 break;
/*    */               default:
/*    */                 recipeRewriter.handleRecipeType(wrapper, type);
/*    */                 break;
/*    */             } 
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_19_1to1_19_3\packets\BlockItemPackets1_19_3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
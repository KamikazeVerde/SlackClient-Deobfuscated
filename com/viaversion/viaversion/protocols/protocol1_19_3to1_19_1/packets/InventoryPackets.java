/*     */ package com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ClientboundPackets1_19_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.Protocol1_19_3To1_19_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ServerboundPackets1_19_3;
/*     */ import com.viaversion.viaversion.rewriter.BlockRewriter;
/*     */ import com.viaversion.viaversion.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.rewriter.RecipeRewriter;
/*     */ import com.viaversion.viaversion.util.Key;
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
/*     */ public final class InventoryPackets
/*     */   extends ItemRewriter<ClientboundPackets1_19_1, ServerboundPackets1_19_3, Protocol1_19_3To1_19_1>
/*     */ {
/*     */   private static final int MISC_CRAFTING_BOOK_CATEGORY = 0;
/*     */   
/*     */   public InventoryPackets(Protocol1_19_3To1_19_1 protocol) {
/*  37 */     super((Protocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerPackets() {
/*  42 */     BlockRewriter<ClientboundPackets1_19_1> blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
/*  43 */     blockRewriter.registerBlockAction((ClientboundPacketType)ClientboundPackets1_19_1.BLOCK_ACTION);
/*  44 */     blockRewriter.registerBlockChange((ClientboundPacketType)ClientboundPackets1_19_1.BLOCK_CHANGE);
/*  45 */     blockRewriter.registerVarLongMultiBlockChange((ClientboundPacketType)ClientboundPackets1_19_1.MULTI_BLOCK_CHANGE);
/*  46 */     blockRewriter.registerEffect((ClientboundPacketType)ClientboundPackets1_19_1.EFFECT, 1010, 2001);
/*  47 */     blockRewriter.registerChunkData1_19((ClientboundPacketType)ClientboundPackets1_19_1.CHUNK_DATA, com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type::new);
/*  48 */     blockRewriter.registerBlockEntityData((ClientboundPacketType)ClientboundPackets1_19_1.BLOCK_ENTITY_DATA);
/*     */     
/*  50 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_19_1.COOLDOWN);
/*  51 */     registerWindowItems1_17_1((ClientboundPacketType)ClientboundPackets1_19_1.WINDOW_ITEMS);
/*  52 */     registerSetSlot1_17_1((ClientboundPacketType)ClientboundPackets1_19_1.SET_SLOT);
/*  53 */     registerAdvancements((ClientboundPacketType)ClientboundPackets1_19_1.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
/*  54 */     registerEntityEquipmentArray((ClientboundPacketType)ClientboundPackets1_19_1.ENTITY_EQUIPMENT);
/*  55 */     registerClickWindow1_17_1((ServerboundPacketType)ServerboundPackets1_19_3.CLICK_WINDOW);
/*  56 */     registerTradeList1_19((ClientboundPacketType)ClientboundPackets1_19_1.TRADE_LIST);
/*  57 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_19_3.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
/*  58 */     registerWindowPropertyEnchantmentHandler((ClientboundPacketType)ClientboundPackets1_19_1.WINDOW_PROPERTY);
/*  59 */     registerSpawnParticle1_19((ClientboundPacketType)ClientboundPackets1_19_1.SPAWN_PARTICLE);
/*     */     
/*  61 */     RecipeRewriter<ClientboundPackets1_19_1> recipeRewriter = new RecipeRewriter(this.protocol);
/*  62 */     ((Protocol1_19_3To1_19_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_1.DECLARE_RECIPES, wrapper -> {
/*     */           int size = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           for (int i = 0; i < size; i++) {
/*     */             int ingredients;
/*     */             Item[] items;
/*     */             int j;
/*     */             String type = Key.stripMinecraftNamespace((String)wrapper.passthrough(Type.STRING));
/*     */             wrapper.passthrough(Type.STRING);
/*     */             switch (type) {
/*     */               case "crafting_shapeless":
/*     */                 wrapper.passthrough(Type.STRING);
/*     */                 wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                 ingredients = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */                 for (j = 0; j < ingredients; j++) {
/*     */                   Item[] arrayOfItem = (Item[])wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
/*     */                   for (Item item : arrayOfItem) {
/*     */                     handleItemToClient(item);
/*     */                   }
/*     */                 } 
/*     */                 handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */                 break;
/*     */               case "crafting_shaped":
/*     */                 ingredients = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue() * ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */                 wrapper.passthrough(Type.STRING);
/*     */                 wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                 for (j = 0; j < ingredients; j++) {
/*     */                   Item[] arrayOfItem = (Item[])wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
/*     */                   for (Item item : arrayOfItem) {
/*     */                     handleItemToClient(item);
/*     */                   }
/*     */                 } 
/*     */                 handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */                 break;
/*     */               case "smelting":
/*     */               case "campfire_cooking":
/*     */               case "blasting":
/*     */               case "smoking":
/*     */                 wrapper.passthrough(Type.STRING);
/*     */                 wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                 items = (Item[])wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
/*     */                 for (Item item : items) {
/*     */                   handleItemToClient(item);
/*     */                 }
/*     */                 handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */                 wrapper.passthrough((Type)Type.FLOAT);
/*     */                 wrapper.passthrough((Type)Type.VAR_INT);
/*     */                 break;
/*     */               case "crafting_special_armordye":
/*     */               case "crafting_special_bookcloning":
/*     */               case "crafting_special_mapcloning":
/*     */               case "crafting_special_mapextending":
/*     */               case "crafting_special_firework_rocket":
/*     */               case "crafting_special_firework_star":
/*     */               case "crafting_special_firework_star_fade":
/*     */               case "crafting_special_tippedarrow":
/*     */               case "crafting_special_bannerduplicate":
/*     */               case "crafting_special_shielddecoration":
/*     */               case "crafting_special_shulkerboxcoloring":
/*     */               case "crafting_special_suspiciousstew":
/*     */               case "crafting_special_repairitem":
/*     */                 wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */                 break;
/*     */               default:
/*     */                 recipeRewriter.handleRecipeType(wrapper, type);
/*     */                 break;
/*     */             } 
/*     */           } 
/*     */         });
/* 130 */     ((Protocol1_19_3To1_19_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_1.EXPLOSION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 133 */             map((Type)Type.FLOAT, (Type)Type.DOUBLE);
/* 134 */             map((Type)Type.FLOAT, (Type)Type.DOUBLE);
/* 135 */             map((Type)Type.FLOAT, (Type)Type.DOUBLE);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19_3to1_19_1\packets\InventoryPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
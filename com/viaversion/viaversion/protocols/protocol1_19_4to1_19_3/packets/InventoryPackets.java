/*     */ package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.rewriter.RecipeRewriter1_19_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.Protocol1_19_4To1_19_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ServerboundPackets1_19_4;
/*     */ import com.viaversion.viaversion.rewriter.BlockRewriter;
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
/*     */ 
/*     */ public final class InventoryPackets
/*     */   extends ItemRewriter<ClientboundPackets1_19_3, ServerboundPackets1_19_4, Protocol1_19_4To1_19_3>
/*     */ {
/*     */   public InventoryPackets(Protocol1_19_4To1_19_3 protocol) {
/*  34 */     super((Protocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerPackets() {
/*  39 */     BlockRewriter<ClientboundPackets1_19_3> blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
/*  40 */     blockRewriter.registerBlockAction((ClientboundPacketType)ClientboundPackets1_19_3.BLOCK_ACTION);
/*  41 */     blockRewriter.registerBlockChange((ClientboundPacketType)ClientboundPackets1_19_3.BLOCK_CHANGE);
/*  42 */     blockRewriter.registerVarLongMultiBlockChange((ClientboundPacketType)ClientboundPackets1_19_3.MULTI_BLOCK_CHANGE);
/*  43 */     blockRewriter.registerChunkData1_19((ClientboundPacketType)ClientboundPackets1_19_3.CHUNK_DATA, com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type::new);
/*  44 */     blockRewriter.registerBlockEntityData((ClientboundPacketType)ClientboundPackets1_19_3.BLOCK_ENTITY_DATA);
/*     */     
/*  46 */     ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  49 */             map((Type)Type.INT);
/*  50 */             map(Type.POSITION1_14);
/*  51 */             map((Type)Type.INT);
/*  52 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   int data = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   
/*     */                   if (id == 1010) {
/*     */                     if (data >= 1092 && data <= 1106) {
/*     */                       wrapper.set((Type)Type.INT, 1, Integer.valueOf(((Protocol1_19_4To1_19_3)InventoryPackets.this.protocol).getMappingData().getNewItemId(data)));
/*     */                     } else {
/*     */                       wrapper.set((Type)Type.INT, 0, Integer.valueOf(1011));
/*     */                       
/*     */                       wrapper.set((Type)Type.INT, 1, Integer.valueOf(0));
/*     */                     } 
/*     */                   } else if (id == 2001) {
/*     */                     wrapper.set((Type)Type.INT, 1, Integer.valueOf(((Protocol1_19_4To1_19_3)InventoryPackets.this.protocol).getMappingData().getNewBlockStateId(data)));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  71 */     ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_3.OPEN_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  74 */             map((Type)Type.VAR_INT);
/*  75 */             map((Type)Type.VAR_INT);
/*  76 */             map(Type.COMPONENT);
/*  77 */             handler(wrapper -> {
/*     */                   int windowType = ((Integer)wrapper.get((Type)Type.VAR_INT, 1)).intValue();
/*     */                   
/*     */                   if (windowType >= 21) {
/*     */                     wrapper.set((Type)Type.VAR_INT, 1, Integer.valueOf(windowType + 1));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*  86 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_19_3.COOLDOWN);
/*  87 */     registerWindowItems1_17_1((ClientboundPacketType)ClientboundPackets1_19_3.WINDOW_ITEMS);
/*  88 */     registerSetSlot1_17_1((ClientboundPacketType)ClientboundPackets1_19_3.SET_SLOT);
/*  89 */     registerAdvancements((ClientboundPacketType)ClientboundPackets1_19_3.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
/*  90 */     registerEntityEquipmentArray((ClientboundPacketType)ClientboundPackets1_19_3.ENTITY_EQUIPMENT);
/*  91 */     registerTradeList1_19((ClientboundPacketType)ClientboundPackets1_19_3.TRADE_LIST);
/*  92 */     registerWindowPropertyEnchantmentHandler((ClientboundPacketType)ClientboundPackets1_19_3.WINDOW_PROPERTY);
/*  93 */     registerSpawnParticle1_19((ClientboundPacketType)ClientboundPackets1_19_3.SPAWN_PARTICLE);
/*  94 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_19_4.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
/*  95 */     registerClickWindow1_17_1((ServerboundPacketType)ServerboundPackets1_19_4.CLICK_WINDOW);
/*     */     
/*  97 */     (new RecipeRewriter1_19_3<ClientboundPackets1_19_3>(this.protocol)
/*     */       {
/*     */         public void handleCraftingShaped(PacketWrapper wrapper) throws Exception {
/* 100 */           super.handleCraftingShaped(wrapper);
/* 101 */           wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */         }
/* 103 */       }).register((ClientboundPacketType)ClientboundPackets1_19_3.DECLARE_RECIPES);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19_4to1_19_3\packets\InventoryPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
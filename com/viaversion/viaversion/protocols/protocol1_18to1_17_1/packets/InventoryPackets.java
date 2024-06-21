/*     */ package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.data.ParticleMappings;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.Protocol1_18To1_17_1;
/*     */ import com.viaversion.viaversion.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.rewriter.RecipeRewriter;
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
/*     */   extends ItemRewriter<ClientboundPackets1_17_1, ServerboundPackets1_17, Protocol1_18To1_17_1>
/*     */ {
/*     */   public InventoryPackets(Protocol1_18To1_17_1 protocol) {
/*  32 */     super((Protocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerPackets() {
/*  37 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_17_1.COOLDOWN);
/*  38 */     registerWindowItems1_17_1((ClientboundPacketType)ClientboundPackets1_17_1.WINDOW_ITEMS);
/*  39 */     registerTradeList((ClientboundPacketType)ClientboundPackets1_17_1.TRADE_LIST);
/*  40 */     registerSetSlot1_17_1((ClientboundPacketType)ClientboundPackets1_17_1.SET_SLOT);
/*  41 */     registerAdvancements((ClientboundPacketType)ClientboundPackets1_17_1.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
/*  42 */     registerEntityEquipmentArray((ClientboundPacketType)ClientboundPackets1_17_1.ENTITY_EQUIPMENT);
/*     */     
/*  44 */     ((Protocol1_18To1_17_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_17_1.EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  47 */             map((Type)Type.INT);
/*  48 */             map(Type.POSITION1_14);
/*  49 */             map((Type)Type.INT);
/*  50 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   int data = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   if (id == 1010) {
/*     */                     wrapper.set((Type)Type.INT, 1, Integer.valueOf(((Protocol1_18To1_17_1)InventoryPackets.this.protocol).getMappingData().getNewItemId(data)));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*  60 */     ((Protocol1_18To1_17_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_17_1.SPAWN_PARTICLE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  63 */             map((Type)Type.INT);
/*  64 */             map((Type)Type.BOOLEAN);
/*  65 */             map((Type)Type.DOUBLE);
/*  66 */             map((Type)Type.DOUBLE);
/*  67 */             map((Type)Type.DOUBLE);
/*  68 */             map((Type)Type.FLOAT);
/*  69 */             map((Type)Type.FLOAT);
/*  70 */             map((Type)Type.FLOAT);
/*  71 */             map((Type)Type.FLOAT);
/*  72 */             map((Type)Type.INT);
/*  73 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   if (id == 2) {
/*     */                     wrapper.set((Type)Type.INT, 0, Integer.valueOf(3));
/*     */                     
/*     */                     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(7754));
/*     */                     return;
/*     */                   } 
/*     */                   if (id == 3) {
/*     */                     wrapper.write((Type)Type.VAR_INT, Integer.valueOf(7786));
/*     */                     return;
/*     */                   } 
/*     */                   ParticleMappings mappings = ((Protocol1_18To1_17_1)InventoryPackets.this.protocol).getMappingData().getParticleMappings();
/*     */                   if (mappings.isBlockParticle(id)) {
/*     */                     int data = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */                     wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(((Protocol1_18To1_17_1)InventoryPackets.this.protocol).getMappingData().getNewBlockStateId(data)));
/*     */                   } else if (mappings.isItemParticle(id)) {
/*     */                     InventoryPackets.this.handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */                   } 
/*     */                   int newId = ((Protocol1_18To1_17_1)InventoryPackets.this.protocol).getMappingData().getNewParticleId(id);
/*     */                   if (newId != id) {
/*     */                     wrapper.set((Type)Type.INT, 0, Integer.valueOf(newId));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 100 */     (new RecipeRewriter(this.protocol)).register((ClientboundPacketType)ClientboundPackets1_17_1.DECLARE_RECIPES);
/*     */     
/* 102 */     registerClickWindow1_17_1((ServerboundPacketType)ServerboundPackets1_17.CLICK_WINDOW);
/* 103 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_17.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_18to1_17_1\packets\InventoryPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
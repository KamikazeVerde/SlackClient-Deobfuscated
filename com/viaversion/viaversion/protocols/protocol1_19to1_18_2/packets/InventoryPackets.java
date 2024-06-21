/*     */ package com.viaversion.viaversion.protocols.protocol1_19to1_18_2.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.Protocol1_19To1_18_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ServerboundPackets1_19;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.provider.AckSequenceProvider;
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
/*     */ 
/*     */ public final class InventoryPackets
/*     */   extends ItemRewriter<ClientboundPackets1_18, ServerboundPackets1_19, Protocol1_19To1_18_2>
/*     */ {
/*     */   public InventoryPackets(Protocol1_19To1_18_2 protocol) {
/*  34 */     super((Protocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerPackets() {
/*  39 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_18.COOLDOWN);
/*  40 */     registerWindowItems1_17_1((ClientboundPacketType)ClientboundPackets1_18.WINDOW_ITEMS);
/*  41 */     registerSetSlot1_17_1((ClientboundPacketType)ClientboundPackets1_18.SET_SLOT);
/*  42 */     registerAdvancements((ClientboundPacketType)ClientboundPackets1_18.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
/*  43 */     registerEntityEquipmentArray((ClientboundPacketType)ClientboundPackets1_18.ENTITY_EQUIPMENT);
/*  44 */     ((Protocol1_19To1_18_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_18.SPAWN_PARTICLE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  47 */             map((Type)Type.INT, (Type)Type.VAR_INT);
/*  48 */             map((Type)Type.BOOLEAN);
/*  49 */             map((Type)Type.DOUBLE);
/*  50 */             map((Type)Type.DOUBLE);
/*  51 */             map((Type)Type.DOUBLE);
/*  52 */             map((Type)Type.FLOAT);
/*  53 */             map((Type)Type.FLOAT);
/*  54 */             map((Type)Type.FLOAT);
/*  55 */             map((Type)Type.FLOAT);
/*  56 */             map((Type)Type.INT);
/*  57 */             handler(InventoryPackets.this.getSpawnParticleHandler((Type)Type.VAR_INT, Type.FLAT_VAR_INT_ITEM));
/*     */           }
/*     */         });
/*     */     
/*  61 */     registerClickWindow1_17_1((ServerboundPacketType)ServerboundPackets1_19.CLICK_WINDOW);
/*  62 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_19.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
/*     */     
/*  64 */     registerWindowPropertyEnchantmentHandler((ClientboundPacketType)ClientboundPackets1_18.WINDOW_PROPERTY);
/*     */     
/*  66 */     ((Protocol1_19To1_18_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_18.TRADE_LIST, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  69 */             map((Type)Type.VAR_INT);
/*  70 */             handler(wrapper -> {
/*     */                   int size = ((Short)wrapper.read((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                   
/*     */                   wrapper.write((Type)Type.VAR_INT, Integer.valueOf(size));
/*     */                   
/*     */                   for (int i = 0; i < size; i++) {
/*     */                     InventoryPackets.this.handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */                     
/*     */                     InventoryPackets.this.handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */                     if (((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue()) {
/*     */                       InventoryPackets.this.handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */                     } else {
/*     */                       wrapper.write(Type.FLAT_VAR_INT_ITEM, null);
/*     */                     } 
/*     */                     wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                     wrapper.passthrough((Type)Type.INT);
/*     */                     wrapper.passthrough((Type)Type.INT);
/*     */                     wrapper.passthrough((Type)Type.INT);
/*     */                     wrapper.passthrough((Type)Type.INT);
/*     */                     wrapper.passthrough((Type)Type.FLOAT);
/*     */                     wrapper.passthrough((Type)Type.INT);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  95 */     ((Protocol1_19To1_18_2)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_19.PLAYER_DIGGING, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  98 */             map((Type)Type.VAR_INT);
/*  99 */             map(Type.POSITION1_14);
/* 100 */             map((Type)Type.UNSIGNED_BYTE);
/* 101 */             handler(InventoryPackets.this.sequenceHandler());
/*     */           }
/*     */         });
/* 104 */     ((Protocol1_19To1_18_2)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_19.PLAYER_BLOCK_PLACEMENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 107 */             map((Type)Type.VAR_INT);
/* 108 */             map(Type.POSITION1_14);
/* 109 */             map((Type)Type.VAR_INT);
/* 110 */             map((Type)Type.FLOAT);
/* 111 */             map((Type)Type.FLOAT);
/* 112 */             map((Type)Type.FLOAT);
/* 113 */             map((Type)Type.BOOLEAN);
/* 114 */             handler(InventoryPackets.this.sequenceHandler());
/*     */           }
/*     */         });
/* 117 */     ((Protocol1_19To1_18_2)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_19.USE_ITEM, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 120 */             map((Type)Type.VAR_INT);
/* 121 */             handler(InventoryPackets.this.sequenceHandler());
/*     */           }
/*     */         });
/*     */     
/* 125 */     (new RecipeRewriter(this.protocol)).register((ClientboundPacketType)ClientboundPackets1_18.DECLARE_RECIPES);
/*     */   }
/*     */   
/*     */   private PacketHandler sequenceHandler() {
/* 129 */     return wrapper -> {
/*     */         int sequence = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */         AckSequenceProvider provider = (AckSequenceProvider)Via.getManager().getProviders().get(AckSequenceProvider.class);
/*     */         provider.handleSequence(wrapper.user(), sequence);
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19to1_18_2\packets\InventoryPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter;
/*     */ import com.viaversion.viabackwards.api.rewriters.LegacyEnchantmentRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.Protocol1_11To1_11_1;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
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
/*     */ public class ItemPackets1_11_1
/*     */   extends LegacyBlockItemRewriter<ClientboundPackets1_9_3, ServerboundPackets1_9_3, Protocol1_11To1_11_1>
/*     */ {
/*     */   private LegacyEnchantmentRewriter enchantmentRewriter;
/*     */   
/*     */   public ItemPackets1_11_1(Protocol1_11To1_11_1 protocol) {
/*  37 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  42 */     registerSetSlot((ClientboundPacketType)ClientboundPackets1_9_3.SET_SLOT, Type.ITEM);
/*  43 */     registerWindowItems((ClientboundPacketType)ClientboundPackets1_9_3.WINDOW_ITEMS, Type.ITEM_ARRAY);
/*  44 */     registerEntityEquipment((ClientboundPacketType)ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
/*     */ 
/*     */     
/*  47 */     ((Protocol1_11To1_11_1)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  50 */             map(Type.STRING);
/*     */             
/*  52 */             handler(wrapper -> {
/*     */                   if (((String)wrapper.get(Type.STRING, 0)).equalsIgnoreCase("MC|TrList")) {
/*     */                     wrapper.passthrough((Type)Type.INT);
/*     */                     
/*     */                     int size = ((Short)wrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                     
/*     */                     for (int i = 0; i < size; i++) {
/*     */                       wrapper.write(Type.ITEM, ItemPackets1_11_1.this.handleItemToClient((Item)wrapper.read(Type.ITEM)));
/*     */                       
/*     */                       wrapper.write(Type.ITEM, ItemPackets1_11_1.this.handleItemToClient((Item)wrapper.read(Type.ITEM)));
/*     */                       
/*     */                       boolean secondItem = ((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue();
/*     */                       if (secondItem) {
/*     */                         wrapper.write(Type.ITEM, ItemPackets1_11_1.this.handleItemToClient((Item)wrapper.read(Type.ITEM)));
/*     */                       }
/*     */                       wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                       wrapper.passthrough((Type)Type.INT);
/*     */                       wrapper.passthrough((Type)Type.INT);
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  75 */     registerClickWindow((ServerboundPacketType)ServerboundPackets1_9_3.CLICK_WINDOW, Type.ITEM);
/*  76 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
/*     */ 
/*     */     
/*  79 */     ((Protocol1_11To1_11_1)this.protocol).getEntityRewriter().filter().handler((event, meta) -> {
/*     */           if (meta.metaType().type().equals(Type.ITEM)) {
/*     */             meta.setValue(handleItemToClient((Item)meta.getValue()));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerRewrites() {
/*  88 */     this.enchantmentRewriter = new LegacyEnchantmentRewriter(this.nbtTagName);
/*  89 */     this.enchantmentRewriter.registerEnchantment(22, "§7Sweeping Edge");
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToClient(Item item) {
/*  94 */     if (item == null) return null; 
/*  95 */     super.handleItemToClient(item);
/*     */     
/*  97 */     CompoundTag tag = item.tag();
/*  98 */     if (tag == null) return item;
/*     */     
/* 100 */     if (tag.get("ench") instanceof com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag) {
/* 101 */       this.enchantmentRewriter.rewriteEnchantmentsToClient(tag, false);
/*     */     }
/* 103 */     if (tag.get("StoredEnchantments") instanceof com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag) {
/* 104 */       this.enchantmentRewriter.rewriteEnchantmentsToClient(tag, true);
/*     */     }
/* 106 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToServer(Item item) {
/* 111 */     if (item == null) return null; 
/* 112 */     super.handleItemToServer(item);
/*     */     
/* 114 */     CompoundTag tag = item.tag();
/* 115 */     if (tag == null) return item;
/*     */     
/* 117 */     if (tag.contains(this.nbtTagName + "|ench")) {
/* 118 */       this.enchantmentRewriter.rewriteEnchantmentsToServer(tag, false);
/*     */     }
/* 120 */     if (tag.contains(this.nbtTagName + "|StoredEnchantments")) {
/* 121 */       this.enchantmentRewriter.rewriteEnchantmentsToServer(tag, true);
/*     */     }
/* 123 */     return item;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_11to1_11_1\packets\ItemPackets1_11_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
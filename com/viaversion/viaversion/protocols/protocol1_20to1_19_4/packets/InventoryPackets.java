/*     */ package com.viaversion.viaversion.protocols.protocol1_20to1_19_4.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
/*     */ import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ServerboundPackets1_19_4;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.rewriter.RecipeRewriter1_19_4;
/*     */ import com.viaversion.viaversion.protocols.protocol1_20to1_19_4.Protocol1_20To1_19_4;
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
/*     */ public final class InventoryPackets
/*     */   extends ItemRewriter<ClientboundPackets1_19_4, ServerboundPackets1_19_4, Protocol1_20To1_19_4>
/*     */ {
/*     */   public InventoryPackets(Protocol1_20To1_19_4 protocol) {
/*  42 */     super((Protocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerPackets() {
/*  47 */     final BlockRewriter<ClientboundPackets1_19_4> blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
/*  48 */     blockRewriter.registerBlockAction((ClientboundPacketType)ClientboundPackets1_19_4.BLOCK_ACTION);
/*  49 */     blockRewriter.registerBlockChange((ClientboundPacketType)ClientboundPackets1_19_4.BLOCK_CHANGE);
/*  50 */     blockRewriter.registerEffect((ClientboundPacketType)ClientboundPackets1_19_4.EFFECT, 1010, 2001);
/*  51 */     blockRewriter.registerBlockEntityData((ClientboundPacketType)ClientboundPackets1_19_4.BLOCK_ENTITY_DATA, this::handleBlockEntity);
/*     */     
/*  53 */     registerOpenWindow((ClientboundPacketType)ClientboundPackets1_19_4.OPEN_WINDOW);
/*  54 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_19_4.COOLDOWN);
/*  55 */     registerWindowItems1_17_1((ClientboundPacketType)ClientboundPackets1_19_4.WINDOW_ITEMS);
/*  56 */     registerSetSlot1_17_1((ClientboundPacketType)ClientboundPackets1_19_4.SET_SLOT);
/*  57 */     registerEntityEquipmentArray((ClientboundPacketType)ClientboundPackets1_19_4.ENTITY_EQUIPMENT);
/*  58 */     registerClickWindow1_17_1((ServerboundPacketType)ServerboundPackets1_19_4.CLICK_WINDOW);
/*  59 */     registerTradeList1_19((ClientboundPacketType)ClientboundPackets1_19_4.TRADE_LIST);
/*  60 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_19_4.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
/*  61 */     registerWindowPropertyEnchantmentHandler((ClientboundPacketType)ClientboundPackets1_19_4.WINDOW_PROPERTY);
/*  62 */     registerSpawnParticle1_19((ClientboundPacketType)ClientboundPackets1_19_4.SPAWN_PARTICLE);
/*     */     
/*  64 */     ((Protocol1_20To1_19_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.ADVANCEMENTS, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.BOOLEAN);
/*     */           
/*     */           int size = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           
/*     */           for (int i = 0; i < size; i++) {
/*     */             wrapper.passthrough(Type.STRING);
/*     */             
/*     */             if (((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue()) {
/*     */               wrapper.passthrough(Type.STRING);
/*     */             }
/*     */             
/*     */             if (((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue()) {
/*     */               wrapper.passthrough(Type.COMPONENT);
/*     */               
/*     */               wrapper.passthrough(Type.COMPONENT);
/*     */               
/*     */               handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */               
/*     */               wrapper.passthrough((Type)Type.VAR_INT);
/*     */               int flags = ((Integer)wrapper.passthrough((Type)Type.INT)).intValue();
/*     */               if ((flags & 0x1) != 0) {
/*     */                 wrapper.passthrough(Type.STRING);
/*     */               }
/*     */               wrapper.passthrough((Type)Type.FLOAT);
/*     */               wrapper.passthrough((Type)Type.FLOAT);
/*     */             } 
/*     */             wrapper.passthrough(Type.STRING_ARRAY);
/*     */             int arrayLength = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */             for (int array = 0; array < arrayLength; array++) {
/*     */               wrapper.passthrough(Type.STRING_ARRAY);
/*     */             }
/*     */             wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */           } 
/*     */         });
/*  99 */     ((Protocol1_20To1_19_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.OPEN_SIGN_EDITOR, wrapper -> {
/*     */           wrapper.passthrough(Type.POSITION1_14);
/*     */           wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */         });
/* 103 */     ((Protocol1_20To1_19_4)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_19_4.UPDATE_SIGN, wrapper -> {
/*     */           wrapper.passthrough(Type.POSITION1_14);
/*     */           
/*     */           boolean frontText = ((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue();
/*     */           if (!frontText) {
/*     */             wrapper.cancel();
/*     */           }
/*     */         });
/* 111 */     ((Protocol1_20To1_19_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.CHUNK_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           protected void register() {
/* 114 */             handler(blockRewriter.chunkDataHandler1_19(com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type::new, x$0 -> rec$.handleBlockEntity(x$0)));
/* 115 */             read((Type)Type.BOOLEAN);
/*     */           }
/*     */         });
/*     */     
/* 119 */     ((Protocol1_20To1_19_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.UPDATE_LIGHT, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */           
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */           wrapper.read((Type)Type.BOOLEAN);
/*     */         });
/* 125 */     ((Protocol1_20To1_19_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.MULTI_BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 128 */             map((Type)Type.LONG);
/* 129 */             read((Type)Type.BOOLEAN);
/* 130 */             handler(wrapper -> {
/*     */                   for (BlockChangeRecord record : (BlockChangeRecord[])wrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY)) {
/*     */                     record.setBlockId(((Protocol1_20To1_19_4)InventoryPackets.this.protocol).getMappingData().getNewBlockStateId(record.getBlockId()));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 138 */     RecipeRewriter1_19_4 recipeRewriter1_19_4 = new RecipeRewriter1_19_4(this.protocol);
/* 139 */     ((Protocol1_20To1_19_4)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.DECLARE_RECIPES, wrapper -> {
/*     */           int size = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           int newSize = size;
/*     */           for (int i = 0; i < size; i++) {
/*     */             String type = (String)wrapper.read(Type.STRING);
/*     */             String cutType = Key.stripMinecraftNamespace(type);
/*     */             if (cutType.equals("smithing")) {
/*     */               newSize--;
/*     */               wrapper.read(Type.STRING);
/*     */               wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
/*     */               wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
/*     */               wrapper.read(Type.FLAT_VAR_INT_ITEM);
/*     */             } else {
/*     */               wrapper.write(Type.STRING, type);
/*     */               wrapper.passthrough(Type.STRING);
/*     */               recipeRewriter.handleRecipeType(wrapper, cutType);
/*     */             } 
/*     */           } 
/*     */           wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(newSize));
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleBlockEntity(BlockEntity blockEntity) {
/* 165 */     if (blockEntity.typeId() != 7 && blockEntity.typeId() != 8) {
/*     */       return;
/*     */     }
/*     */     
/* 169 */     CompoundTag tag = blockEntity.tag();
/* 170 */     CompoundTag frontText = new CompoundTag();
/* 171 */     tag.put("front_text", (Tag)frontText);
/*     */     
/* 173 */     ListTag messages = new ListTag(StringTag.class);
/* 174 */     for (int i = 1; i < 5; i++) {
/* 175 */       Tag text = tag.get("Text" + i);
/* 176 */       messages.add((text != null) ? text : (Tag)new StringTag(ChatRewriter.emptyComponentString()));
/*     */     } 
/* 178 */     frontText.put("messages", (Tag)messages);
/*     */     
/* 180 */     ListTag filteredMessages = new ListTag(StringTag.class);
/* 181 */     for (int j = 1; j < 5; j++) {
/* 182 */       Tag text = tag.get("FilteredText" + j);
/* 183 */       filteredMessages.add((text != null) ? text : (Tag)new StringTag(ChatRewriter.emptyComponentString()));
/*     */     } 
/* 185 */     frontText.put("filtered_messages", (Tag)filteredMessages);
/*     */     
/* 187 */     Tag color = tag.remove("Color");
/* 188 */     if (color != null) {
/* 189 */       frontText.put("color", color);
/*     */     }
/*     */     
/* 192 */     Tag glowing = tag.remove("GlowingText");
/* 193 */     if (glowing != null)
/* 194 */       frontText.put("has_glowing_text", glowing); 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_20to1_19_4\packets\InventoryPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
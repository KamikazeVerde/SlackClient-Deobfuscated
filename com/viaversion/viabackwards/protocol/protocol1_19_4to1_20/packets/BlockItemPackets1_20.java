/*     */ package com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.Protocol1_19_4To1_20;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.storage.BackSignEditStorage;
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
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
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ServerboundPackets1_19_4;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.rewriter.RecipeRewriter1_19_4;
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
/*     */ public final class BlockItemPackets1_20
/*     */   extends ItemRewriter<ClientboundPackets1_19_4, ServerboundPackets1_19_4, Protocol1_19_4To1_20>
/*     */ {
/*     */   public BlockItemPackets1_20(Protocol1_19_4To1_20 protocol) {
/*  40 */     super((Protocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerPackets() {
/*  45 */     final BlockRewriter<ClientboundPackets1_19_4> blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
/*  46 */     blockRewriter.registerBlockAction((ClientboundPacketType)ClientboundPackets1_19_4.BLOCK_ACTION);
/*  47 */     blockRewriter.registerBlockChange((ClientboundPacketType)ClientboundPackets1_19_4.BLOCK_CHANGE);
/*  48 */     blockRewriter.registerEffect((ClientboundPacketType)ClientboundPackets1_19_4.EFFECT, 1010, 2001);
/*  49 */     blockRewriter.registerBlockEntityData((ClientboundPacketType)ClientboundPackets1_19_4.BLOCK_ENTITY_DATA, this::handleBlockEntity);
/*     */     
/*  51 */     ((Protocol1_19_4To1_20)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.CHUNK_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           protected void register() {
/*  54 */             handler(blockRewriter.chunkDataHandler1_19(com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type::new, x$0 -> rec$.handleBlockEntity(x$0)));
/*  55 */             create((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */           }
/*     */         });
/*     */     
/*  59 */     ((Protocol1_19_4To1_20)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.UPDATE_LIGHT, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */           
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */           wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */         });
/*  65 */     ((Protocol1_19_4To1_20)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.MULTI_BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  68 */             map((Type)Type.LONG);
/*  69 */             create((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*  70 */             handler(wrapper -> {
/*     */                   for (BlockChangeRecord record : (BlockChangeRecord[])wrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY)) {
/*     */                     record.setBlockId(((Protocol1_19_4To1_20)BlockItemPackets1_20.this.protocol).getMappingData().getNewBlockStateId(record.getBlockId()));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/*  78 */     registerOpenWindow((ClientboundPacketType)ClientboundPackets1_19_4.OPEN_WINDOW);
/*  79 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_19_4.COOLDOWN);
/*  80 */     registerWindowItems1_17_1((ClientboundPacketType)ClientboundPackets1_19_4.WINDOW_ITEMS);
/*  81 */     registerSetSlot1_17_1((ClientboundPacketType)ClientboundPackets1_19_4.SET_SLOT);
/*  82 */     registerEntityEquipmentArray((ClientboundPacketType)ClientboundPackets1_19_4.ENTITY_EQUIPMENT);
/*  83 */     registerClickWindow1_17_1((ServerboundPacketType)ServerboundPackets1_19_4.CLICK_WINDOW);
/*  84 */     registerTradeList1_19((ClientboundPacketType)ClientboundPackets1_19_4.TRADE_LIST);
/*  85 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_19_4.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
/*  86 */     registerWindowPropertyEnchantmentHandler((ClientboundPacketType)ClientboundPackets1_19_4.WINDOW_PROPERTY);
/*  87 */     registerSpawnParticle1_19((ClientboundPacketType)ClientboundPackets1_19_4.SPAWN_PARTICLE);
/*     */     
/*  89 */     ((Protocol1_19_4To1_20)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.ADVANCEMENTS, wrapper -> {
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
/*     */             wrapper.read((Type)Type.BOOLEAN);
/*     */           } 
/*     */         });
/* 124 */     ((Protocol1_19_4To1_20)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19_4.OPEN_SIGN_EDITOR, wrapper -> {
/*     */           Position position = (Position)wrapper.passthrough(Type.POSITION1_14);
/*     */           boolean frontSide = ((Boolean)wrapper.read((Type)Type.BOOLEAN)).booleanValue();
/*     */           if (frontSide) {
/*     */             wrapper.user().remove(BackSignEditStorage.class);
/*     */           } else {
/*     */             wrapper.user().put((StorableObject)new BackSignEditStorage(position));
/*     */           } 
/*     */         });
/* 133 */     ((Protocol1_19_4To1_20)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_19_4.UPDATE_SIGN, wrapper -> {
/*     */           Position position = (Position)wrapper.passthrough(Type.POSITION1_14);
/*     */           BackSignEditStorage backSignEditStorage = (BackSignEditStorage)wrapper.user().remove(BackSignEditStorage.class);
/* 136 */           boolean frontSide = (backSignEditStorage == null || !backSignEditStorage.position().equals(position));
/*     */           
/*     */           wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(frontSide));
/*     */         });
/* 140 */     (new RecipeRewriter1_19_4(this.protocol)).register((ClientboundPacketType)ClientboundPackets1_19_4.DECLARE_RECIPES);
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleBlockEntity(BlockEntity blockEntity) {
/* 145 */     if (blockEntity.typeId() != 7 && blockEntity.typeId() != 8) {
/*     */       return;
/*     */     }
/*     */     
/* 149 */     CompoundTag tag = blockEntity.tag();
/* 150 */     CompoundTag frontText = (CompoundTag)tag.remove("front_text");
/* 151 */     tag.remove("back_text");
/*     */     
/* 153 */     if (frontText != null) {
/* 154 */       writeMessages(frontText, tag, false);
/* 155 */       writeMessages(frontText, tag, true);
/*     */       
/* 157 */       Tag color = frontText.remove("color");
/* 158 */       if (color != null) {
/* 159 */         tag.put("Color", color);
/*     */       }
/*     */       
/* 162 */       Tag glowing = frontText.remove("has_glowing_text");
/* 163 */       if (glowing != null) {
/* 164 */         tag.put("GlowingText", glowing);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeMessages(CompoundTag frontText, CompoundTag tag, boolean filtered) {
/* 170 */     ListTag messages = (ListTag)frontText.get(filtered ? "filtered_messages" : "messages");
/* 171 */     if (messages == null) {
/*     */       return;
/*     */     }
/*     */     
/* 175 */     int i = 0;
/* 176 */     for (Tag message : messages)
/* 177 */       tag.put((filtered ? "FilteredText" : "Text") + ++i, message); 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_19_4to1_20\packets\BlockItemPackets1_20.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
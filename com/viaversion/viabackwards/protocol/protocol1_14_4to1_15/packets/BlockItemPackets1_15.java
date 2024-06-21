/*     */ package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types.Chunk1_14Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
/*     */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types.Chunk1_15Type;
/*     */ import com.viaversion.viaversion.rewriter.BlockRewriter;
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
/*     */ public class BlockItemPackets1_15
/*     */   extends ItemRewriter<ClientboundPackets1_15, ServerboundPackets1_14, Protocol1_14_4To1_15>
/*     */ {
/*     */   public BlockItemPackets1_15(Protocol1_14_4To1_15 protocol) {
/*  38 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  43 */     BlockRewriter<ClientboundPackets1_15> blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
/*     */     
/*  45 */     (new RecipeRewriter(this.protocol)).register((ClientboundPacketType)ClientboundPackets1_15.DECLARE_RECIPES);
/*     */     
/*  47 */     ((Protocol1_14_4To1_15)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_14.EDIT_BOOK, wrapper -> handleItemToServer((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM)));
/*     */     
/*  49 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_15.COOLDOWN);
/*  50 */     registerWindowItems((ClientboundPacketType)ClientboundPackets1_15.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
/*  51 */     registerSetSlot((ClientboundPacketType)ClientboundPackets1_15.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
/*  52 */     registerTradeList((ClientboundPacketType)ClientboundPackets1_15.TRADE_LIST);
/*  53 */     registerEntityEquipment((ClientboundPacketType)ClientboundPackets1_15.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
/*  54 */     registerAdvancements((ClientboundPacketType)ClientboundPackets1_15.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
/*  55 */     registerClickWindow((ServerboundPacketType)ServerboundPackets1_14.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
/*  56 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
/*     */     
/*  58 */     blockRewriter.registerAcknowledgePlayerDigging((ClientboundPacketType)ClientboundPackets1_15.ACKNOWLEDGE_PLAYER_DIGGING);
/*  59 */     blockRewriter.registerBlockAction((ClientboundPacketType)ClientboundPackets1_15.BLOCK_ACTION);
/*  60 */     blockRewriter.registerBlockChange((ClientboundPacketType)ClientboundPackets1_15.BLOCK_CHANGE);
/*  61 */     blockRewriter.registerMultiBlockChange((ClientboundPacketType)ClientboundPackets1_15.MULTI_BLOCK_CHANGE);
/*     */     
/*  63 */     ((Protocol1_14_4To1_15)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_15.CHUNK_DATA, wrapper -> {
/*     */           Chunk chunk = (Chunk)wrapper.read((Type)new Chunk1_15Type());
/*     */           
/*     */           wrapper.write((Type)new Chunk1_14Type(), chunk);
/*     */           
/*     */           if (chunk.isFullChunk()) {
/*     */             int[] biomeData = chunk.getBiomeData();
/*     */             
/*     */             int[] newBiomeData = new int[256];
/*     */             
/*     */             for (int j = 0; j < 4; j++) {
/*     */               for (int k = 0; k < 4; k++) {
/*     */                 int x = k << 2;
/*     */                 
/*     */                 int z = j << 2;
/*     */                 
/*     */                 int newIndex = z << 4 | x;
/*     */                 
/*     */                 int oldIndex = j << 2 | k;
/*     */                 int biome = biomeData[oldIndex];
/*     */                 for (int m = 0; m < 4; m++) {
/*     */                   int offX = newIndex + (m << 4);
/*     */                   for (int l = 0; l < 4; l++) {
/*     */                     newBiomeData[offX + l] = biome;
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             chunk.setBiomeData(newBiomeData);
/*     */           } 
/*     */           for (int i = 0; i < (chunk.getSections()).length; i++) {
/*     */             ChunkSection section = chunk.getSections()[i];
/*     */             if (section != null) {
/*     */               DataPalette palette = section.palette(PaletteType.BLOCKS);
/*     */               for (int j = 0; j < palette.size(); j++) {
/*     */                 int mappedBlockStateId = ((Protocol1_14_4To1_15)this.protocol).getMappingData().getNewBlockStateId(palette.idByIndex(j));
/*     */                 palette.setIdByIndex(j, mappedBlockStateId);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         });
/* 104 */     blockRewriter.registerEffect((ClientboundPacketType)ClientboundPackets1_15.EFFECT, 1010, 2001);
/*     */     
/* 106 */     ((Protocol1_14_4To1_15)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_15.SPAWN_PARTICLE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 109 */             map((Type)Type.INT);
/* 110 */             map((Type)Type.BOOLEAN);
/* 111 */             map((Type)Type.DOUBLE, (Type)Type.FLOAT);
/* 112 */             map((Type)Type.DOUBLE, (Type)Type.FLOAT);
/* 113 */             map((Type)Type.DOUBLE, (Type)Type.FLOAT);
/* 114 */             map((Type)Type.FLOAT);
/* 115 */             map((Type)Type.FLOAT);
/* 116 */             map((Type)Type.FLOAT);
/* 117 */             map((Type)Type.FLOAT);
/* 118 */             map((Type)Type.INT);
/* 119 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   if (id == 3 || id == 23) {
/*     */                     int data = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */                     wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(((Protocol1_14_4To1_15)BlockItemPackets1_15.this.protocol).getMappingData().getNewBlockStateId(data)));
/*     */                   } else if (id == 32) {
/*     */                     Item item = BlockItemPackets1_15.this.handleItemToClient((Item)wrapper.read(Type.FLAT_VAR_INT_ITEM));
/*     */                     wrapper.write(Type.FLAT_VAR_INT_ITEM, item);
/*     */                   } 
/*     */                   int mappedId = ((Protocol1_14_4To1_15)BlockItemPackets1_15.this.protocol).getMappingData().getNewParticleId(id);
/*     */                   if (id != mappedId)
/*     */                     wrapper.set((Type)Type.INT, 0, Integer.valueOf(mappedId)); 
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_14_4to1_15\packets\BlockItemPackets1_15.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
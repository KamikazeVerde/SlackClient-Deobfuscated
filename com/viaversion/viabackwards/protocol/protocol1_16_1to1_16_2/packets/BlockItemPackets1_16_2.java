/*     */ package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.Protocol1_16_1To1_16_2;
/*     */ import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
/*     */ import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types.Chunk1_16_2Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.types.Chunk1_16Type;
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
/*     */ public class BlockItemPackets1_16_2
/*     */   extends ItemRewriter<ClientboundPackets1_16_2, ServerboundPackets1_16, Protocol1_16_1To1_16_2>
/*     */ {
/*     */   public BlockItemPackets1_16_2(Protocol1_16_1To1_16_2 protocol) {
/*  44 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  49 */     BlockRewriter<ClientboundPackets1_16_2> blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
/*     */     
/*  51 */     (new RecipeRewriter(this.protocol)).register((ClientboundPacketType)ClientboundPackets1_16_2.DECLARE_RECIPES);
/*     */     
/*  53 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_16_2.COOLDOWN);
/*  54 */     registerWindowItems((ClientboundPacketType)ClientboundPackets1_16_2.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
/*  55 */     registerSetSlot((ClientboundPacketType)ClientboundPackets1_16_2.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
/*  56 */     registerEntityEquipmentArray((ClientboundPacketType)ClientboundPackets1_16_2.ENTITY_EQUIPMENT);
/*  57 */     registerTradeList((ClientboundPacketType)ClientboundPackets1_16_2.TRADE_LIST);
/*  58 */     registerAdvancements((ClientboundPacketType)ClientboundPackets1_16_2.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
/*     */     
/*  60 */     ((Protocol1_16_1To1_16_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.UNLOCK_RECIPES, wrapper -> {
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */           
/*     */           wrapper.passthrough((Type)Type.BOOLEAN);
/*     */           
/*     */           wrapper.passthrough((Type)Type.BOOLEAN);
/*     */           wrapper.passthrough((Type)Type.BOOLEAN);
/*     */           wrapper.passthrough((Type)Type.BOOLEAN);
/*     */           wrapper.read((Type)Type.BOOLEAN);
/*     */           wrapper.read((Type)Type.BOOLEAN);
/*     */           wrapper.read((Type)Type.BOOLEAN);
/*     */           wrapper.read((Type)Type.BOOLEAN);
/*     */         });
/*  73 */     blockRewriter.registerAcknowledgePlayerDigging((ClientboundPacketType)ClientboundPackets1_16_2.ACKNOWLEDGE_PLAYER_DIGGING);
/*  74 */     blockRewriter.registerBlockAction((ClientboundPacketType)ClientboundPackets1_16_2.BLOCK_ACTION);
/*  75 */     blockRewriter.registerBlockChange((ClientboundPacketType)ClientboundPackets1_16_2.BLOCK_CHANGE);
/*     */     
/*  77 */     ((Protocol1_16_1To1_16_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.CHUNK_DATA, wrapper -> {
/*     */           Chunk chunk = (Chunk)wrapper.read((Type)new Chunk1_16_2Type());
/*     */           
/*     */           wrapper.write((Type)new Chunk1_16Type(), chunk);
/*     */           
/*     */           chunk.setIgnoreOldLightData(true);
/*     */           
/*     */           for (int i = 0; i < (chunk.getSections()).length; i++) {
/*     */             ChunkSection section = chunk.getSections()[i];
/*     */             
/*     */             if (section != null) {
/*     */               DataPalette palette = section.palette(PaletteType.BLOCKS);
/*     */               
/*     */               for (int j = 0; j < palette.size(); j++) {
/*     */                 int mappedBlockStateId = ((Protocol1_16_1To1_16_2)this.protocol).getMappingData().getNewBlockStateId(palette.idByIndex(j));
/*     */                 palette.setIdByIndex(j, mappedBlockStateId);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           for (CompoundTag blockEntity : chunk.getBlockEntities()) {
/*     */             if (blockEntity != null) {
/*     */               handleBlockEntity(blockEntity);
/*     */             }
/*     */           } 
/*     */         });
/* 102 */     ((Protocol1_16_1To1_16_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.BLOCK_ENTITY_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 105 */             map(Type.POSITION1_14);
/* 106 */             map((Type)Type.UNSIGNED_BYTE);
/* 107 */             handler(wrapper -> BlockItemPackets1_16_2.this.handleBlockEntity((CompoundTag)wrapper.passthrough(Type.NBT)));
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 113 */     ((Protocol1_16_1To1_16_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_16_2.MULTI_BLOCK_CHANGE, wrapper -> {
/*     */           long chunkPosition = ((Long)wrapper.read((Type)Type.LONG)).longValue();
/*     */           
/*     */           wrapper.read((Type)Type.BOOLEAN);
/*     */           
/*     */           int chunkX = (int)(chunkPosition >> 42L);
/*     */           
/*     */           int chunkY = (int)(chunkPosition << 44L >> 44L);
/*     */           
/*     */           int chunkZ = (int)(chunkPosition << 22L >> 42L);
/*     */           wrapper.write((Type)Type.INT, Integer.valueOf(chunkX));
/*     */           wrapper.write((Type)Type.INT, Integer.valueOf(chunkZ));
/*     */           BlockChangeRecord[] blockChangeRecord = (BlockChangeRecord[])wrapper.read(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY);
/*     */           wrapper.write(Type.BLOCK_CHANGE_RECORD_ARRAY, blockChangeRecord);
/*     */           for (int i = 0; i < blockChangeRecord.length; i++) {
/*     */             BlockChangeRecord record = blockChangeRecord[i];
/*     */             int blockId = ((Protocol1_16_1To1_16_2)this.protocol).getMappingData().getNewBlockStateId(record.getBlockId());
/*     */             blockChangeRecord[i] = (BlockChangeRecord)new BlockChangeRecord1_8(record.getSectionX(), record.getY(chunkY), record.getSectionZ(), blockId);
/*     */           } 
/*     */         });
/* 133 */     blockRewriter.registerEffect((ClientboundPacketType)ClientboundPackets1_16_2.EFFECT, 1010, 2001);
/*     */     
/* 135 */     registerSpawnParticle((ClientboundPacketType)ClientboundPackets1_16_2.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, (Type)Type.DOUBLE);
/*     */     
/* 137 */     registerClickWindow((ServerboundPacketType)ServerboundPackets1_16.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
/* 138 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_16.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
/* 139 */     ((Protocol1_16_1To1_16_2)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_16.EDIT_BOOK, wrapper -> handleItemToServer((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM)));
/*     */   }
/*     */   
/*     */   private void handleBlockEntity(CompoundTag tag) {
/* 143 */     StringTag idTag = (StringTag)tag.get("id");
/* 144 */     if (idTag == null)
/* 145 */       return;  if (idTag.getValue().equals("minecraft:skull")) {
/*     */       
/* 147 */       Tag skullOwnerTag = tag.get("SkullOwner");
/* 148 */       if (!(skullOwnerTag instanceof CompoundTag))
/*     */         return; 
/* 150 */       CompoundTag skullOwnerCompoundTag = (CompoundTag)skullOwnerTag;
/* 151 */       if (!skullOwnerCompoundTag.contains("Id"))
/*     */         return; 
/* 153 */       CompoundTag properties = (CompoundTag)skullOwnerCompoundTag.get("Properties");
/* 154 */       if (properties == null)
/*     */         return; 
/* 156 */       ListTag textures = (ListTag)properties.get("textures");
/* 157 */       if (textures == null)
/*     */         return; 
/* 159 */       CompoundTag first = (textures.size() > 0) ? (CompoundTag)textures.get(0) : null;
/* 160 */       if (first == null) {
/*     */         return;
/*     */       }
/* 163 */       int hashCode = first.get("Value").getValue().hashCode();
/* 164 */       int[] uuidIntArray = { hashCode, 0, 0, 0 };
/* 165 */       skullOwnerCompoundTag.put("Id", (Tag)new IntArrayTag(uuidIntArray));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_16_1to1_16_2\packets\BlockItemPackets1_16_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
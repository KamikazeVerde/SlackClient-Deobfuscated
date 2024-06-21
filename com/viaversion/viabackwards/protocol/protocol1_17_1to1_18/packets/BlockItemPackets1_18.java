/*     */ package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.Protocol1_17_1To1_18;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.data.BlockEntityIds;
/*     */ import com.viaversion.viaversion.api.data.ParticleMappings;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types.Chunk1_17Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type;
/*     */ import com.viaversion.viaversion.rewriter.RecipeRewriter;
/*     */ import com.viaversion.viaversion.util.Key;
/*     */ import com.viaversion.viaversion.util.MathUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BlockItemPackets1_18
/*     */   extends ItemRewriter<ClientboundPackets1_18, ServerboundPackets1_17, Protocol1_17_1To1_18>
/*     */ {
/*     */   public BlockItemPackets1_18(Protocol1_17_1To1_18 protocol) {
/*  53 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  58 */     (new RecipeRewriter(this.protocol)).register((ClientboundPacketType)ClientboundPackets1_18.DECLARE_RECIPES);
/*     */     
/*  60 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_18.COOLDOWN);
/*  61 */     registerWindowItems1_17_1((ClientboundPacketType)ClientboundPackets1_18.WINDOW_ITEMS);
/*  62 */     registerSetSlot1_17_1((ClientboundPacketType)ClientboundPackets1_18.SET_SLOT);
/*  63 */     registerEntityEquipmentArray((ClientboundPacketType)ClientboundPackets1_18.ENTITY_EQUIPMENT);
/*  64 */     registerTradeList((ClientboundPacketType)ClientboundPackets1_18.TRADE_LIST);
/*  65 */     registerAdvancements((ClientboundPacketType)ClientboundPackets1_18.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
/*  66 */     registerClickWindow1_17_1((ServerboundPacketType)ServerboundPackets1_17.CLICK_WINDOW);
/*     */     
/*  68 */     ((Protocol1_17_1To1_18)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_18.EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  71 */             map((Type)Type.INT);
/*  72 */             map(Type.POSITION1_14);
/*  73 */             map((Type)Type.INT);
/*  74 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   int data = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   if (id == 1010) {
/*     */                     wrapper.set((Type)Type.INT, 1, Integer.valueOf(((Protocol1_17_1To1_18)BlockItemPackets1_18.this.protocol).getMappingData().getNewItemId(data)));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*  84 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_17.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
/*     */     
/*  86 */     ((Protocol1_17_1To1_18)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_18.SPAWN_PARTICLE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  89 */             map((Type)Type.INT);
/*  90 */             map((Type)Type.BOOLEAN);
/*  91 */             map((Type)Type.DOUBLE);
/*  92 */             map((Type)Type.DOUBLE);
/*  93 */             map((Type)Type.DOUBLE);
/*  94 */             map((Type)Type.FLOAT);
/*  95 */             map((Type)Type.FLOAT);
/*  96 */             map((Type)Type.FLOAT);
/*  97 */             map((Type)Type.FLOAT);
/*  98 */             map((Type)Type.INT);
/*  99 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   if (id == 3) {
/*     */                     int blockState = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                     
/*     */                     if (blockState == 7786) {
/*     */                       wrapper.set((Type)Type.INT, 0, Integer.valueOf(3));
/*     */                     } else {
/*     */                       wrapper.set((Type)Type.INT, 0, Integer.valueOf(2));
/*     */                     } 
/*     */                     
/*     */                     return;
/*     */                   } 
/*     */                   
/*     */                   ParticleMappings mappings = ((Protocol1_17_1To1_18)BlockItemPackets1_18.this.protocol).getMappingData().getParticleMappings();
/*     */                   if (mappings.isBlockParticle(id)) {
/*     */                     int data = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */                     wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(((Protocol1_17_1To1_18)BlockItemPackets1_18.this.protocol).getMappingData().getNewBlockStateId(data)));
/*     */                   } else if (mappings.isItemParticle(id)) {
/*     */                     BlockItemPackets1_18.this.handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */                   } 
/*     */                   int newId = ((Protocol1_17_1To1_18)BlockItemPackets1_18.this.protocol).getMappingData().getNewParticleId(id);
/*     */                   if (newId != id) {
/*     */                     wrapper.set((Type)Type.INT, 0, Integer.valueOf(newId));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 128 */     ((Protocol1_17_1To1_18)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_18.BLOCK_ENTITY_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 131 */             map(Type.POSITION1_14);
/* 132 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   CompoundTag tag = (CompoundTag)wrapper.read(Type.NBT);
/*     */                   
/*     */                   int mappedId = BlockEntityIds.mappedId(id);
/*     */                   
/*     */                   if (mappedId == -1) {
/*     */                     wrapper.cancel();
/*     */                     
/*     */                     return;
/*     */                   } 
/*     */                   
/*     */                   String identifier = (String)((Protocol1_17_1To1_18)BlockItemPackets1_18.this.protocol).getMappingData().blockEntities().get(id);
/*     */                   
/*     */                   if (identifier == null) {
/*     */                     wrapper.cancel();
/*     */                     
/*     */                     return;
/*     */                   } 
/*     */                   
/*     */                   CompoundTag newTag = (tag == null) ? new CompoundTag() : tag;
/*     */                   
/*     */                   Position pos = (Position)wrapper.get(Type.POSITION1_14, 0);
/*     */                   
/*     */                   newTag.put("id", (Tag)new StringTag(Key.namespaced(identifier)));
/*     */                   
/*     */                   newTag.put("x", (Tag)new IntTag(pos.x()));
/*     */                   
/*     */                   newTag.put("y", (Tag)new IntTag(pos.y()));
/*     */                   
/*     */                   newTag.put("z", (Tag)new IntTag(pos.z()));
/*     */                   BlockItemPackets1_18.this.handleSpawner(id, newTag);
/*     */                   wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)mappedId));
/*     */                   wrapper.write(Type.NBT, newTag);
/*     */                 });
/*     */           }
/*     */         });
/* 170 */     ((Protocol1_17_1To1_18)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_18.CHUNK_DATA, wrapper -> {
/*     */           EntityTracker tracker = ((Protocol1_17_1To1_18)this.protocol).getEntityRewriter().tracker(wrapper.user());
/*     */           
/*     */           Chunk1_18Type chunkType = new Chunk1_18Type(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(((Protocol1_17_1To1_18)this.protocol).getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(tracker.biomesSent()));
/*     */           
/*     */           Chunk oldChunk = (Chunk)wrapper.read((Type)chunkType);
/*     */           
/*     */           ChunkSection[] sections = oldChunk.getSections();
/*     */           
/*     */           BitSet mask = new BitSet((oldChunk.getSections()).length);
/*     */           
/*     */           int[] biomeData = new int[sections.length * 64];
/*     */           
/*     */           int biomeIndex = 0;
/*     */           
/*     */           for (int j = 0; j < sections.length; j++) {
/*     */             ChunkSection section = sections[j];
/*     */             
/*     */             DataPalette biomePalette = section.palette(PaletteType.BIOMES);
/*     */             
/*     */             for (int m = 0; m < 64; m++) {
/*     */               biomeData[biomeIndex++] = biomePalette.idAt(m);
/*     */             }
/*     */             
/*     */             if (section.getNonAirBlocksCount() == 0) {
/*     */               sections[j] = null;
/*     */             } else {
/*     */               mask.set(j);
/*     */             } 
/*     */           } 
/*     */           
/*     */           List<CompoundTag> blockEntityTags = new ArrayList<>(oldChunk.blockEntities().size());
/*     */           
/*     */           for (BlockEntity blockEntity : oldChunk.blockEntities()) {
/*     */             CompoundTag tag;
/*     */             
/*     */             String id = (String)((Protocol1_17_1To1_18)this.protocol).getMappingData().blockEntities().get(blockEntity.typeId());
/*     */             
/*     */             if (id == null) {
/*     */               continue;
/*     */             }
/*     */             
/*     */             if (blockEntity.tag() != null) {
/*     */               tag = blockEntity.tag();
/*     */               
/*     */               handleSpawner(blockEntity.typeId(), tag);
/*     */             } else {
/*     */               tag = new CompoundTag();
/*     */             } 
/*     */             
/*     */             blockEntityTags.add(tag);
/*     */             tag.put("x", (Tag)new IntTag((oldChunk.getX() << 4) + blockEntity.sectionX()));
/*     */             tag.put("y", (Tag)new IntTag(blockEntity.y()));
/*     */             tag.put("z", (Tag)new IntTag((oldChunk.getZ() << 4) + blockEntity.sectionZ()));
/*     */             tag.put("id", (Tag)new StringTag(Key.namespaced(id)));
/*     */           } 
/*     */           BaseChunk baseChunk = new BaseChunk(oldChunk.getX(), oldChunk.getZ(), true, false, mask, oldChunk.getSections(), biomeData, oldChunk.getHeightMap(), blockEntityTags);
/*     */           wrapper.write((Type)new Chunk1_17Type(tracker.currentWorldSectionHeight()), baseChunk);
/*     */           PacketWrapper lightPacket = wrapper.create((PacketType)ClientboundPackets1_17_1.UPDATE_LIGHT);
/*     */           lightPacket.write((Type)Type.VAR_INT, Integer.valueOf(baseChunk.getX()));
/*     */           lightPacket.write((Type)Type.VAR_INT, Integer.valueOf(baseChunk.getZ()));
/*     */           lightPacket.write((Type)Type.BOOLEAN, wrapper.read((Type)Type.BOOLEAN));
/*     */           lightPacket.write(Type.LONG_ARRAY_PRIMITIVE, wrapper.read(Type.LONG_ARRAY_PRIMITIVE));
/*     */           lightPacket.write(Type.LONG_ARRAY_PRIMITIVE, wrapper.read(Type.LONG_ARRAY_PRIMITIVE));
/*     */           lightPacket.write(Type.LONG_ARRAY_PRIMITIVE, wrapper.read(Type.LONG_ARRAY_PRIMITIVE));
/*     */           lightPacket.write(Type.LONG_ARRAY_PRIMITIVE, wrapper.read(Type.LONG_ARRAY_PRIMITIVE));
/*     */           int skyLightLength = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           lightPacket.write((Type)Type.VAR_INT, Integer.valueOf(skyLightLength));
/*     */           for (int i = 0; i < skyLightLength; i++) {
/*     */             lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, wrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
/*     */           }
/*     */           int blockLightLength = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           lightPacket.write((Type)Type.VAR_INT, Integer.valueOf(blockLightLength));
/*     */           for (int k = 0; k < blockLightLength; k++) {
/*     */             lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, wrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
/*     */           }
/*     */           lightPacket.send(Protocol1_17_1To1_18.class);
/*     */         });
/* 248 */     ((Protocol1_17_1To1_18)this.protocol).cancelClientbound((ClientboundPacketType)ClientboundPackets1_18.SET_SIMULATION_DISTANCE);
/*     */   }
/*     */   
/*     */   private void handleSpawner(int typeId, CompoundTag tag) {
/* 252 */     if (typeId == 8) {
/* 253 */       CompoundTag spawnData = (CompoundTag)tag.get("SpawnData");
/*     */       CompoundTag entity;
/* 255 */       if (spawnData != null && (entity = (CompoundTag)spawnData.get("entity")) != null)
/* 256 */         tag.put("SpawnData", (Tag)entity); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_17_1to1_18\packets\BlockItemPackets1_18.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
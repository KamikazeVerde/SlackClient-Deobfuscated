/*     */ package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.Protocol1_18_2To1_19;
/*     */ import com.viaversion.viaversion.api.data.ParticleMappings;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
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
/*     */ import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
/*     */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
/*     */ import com.viaversion.viaversion.rewriter.BlockRewriter;
/*     */ import com.viaversion.viaversion.rewriter.RecipeRewriter;
/*     */ import com.viaversion.viaversion.util.MathUtil;
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
/*     */ public final class BlockItemPackets1_19
/*     */   extends ItemRewriter<ClientboundPackets1_19, ServerboundPackets1_17, Protocol1_18_2To1_19>
/*     */ {
/*     */   public BlockItemPackets1_19(Protocol1_18_2To1_19 protocol) {
/*  42 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  47 */     BlockRewriter<ClientboundPackets1_19> blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
/*     */     
/*  49 */     (new RecipeRewriter(this.protocol)).register((ClientboundPacketType)ClientboundPackets1_19.DECLARE_RECIPES);
/*     */     
/*  51 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_19.COOLDOWN);
/*  52 */     registerWindowItems1_17_1((ClientboundPacketType)ClientboundPackets1_19.WINDOW_ITEMS);
/*  53 */     registerSetSlot1_17_1((ClientboundPacketType)ClientboundPackets1_19.SET_SLOT);
/*  54 */     registerEntityEquipmentArray((ClientboundPacketType)ClientboundPackets1_19.ENTITY_EQUIPMENT);
/*  55 */     registerAdvancements((ClientboundPacketType)ClientboundPackets1_19.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
/*  56 */     registerClickWindow1_17_1((ServerboundPacketType)ServerboundPackets1_17.CLICK_WINDOW);
/*     */     
/*  58 */     blockRewriter.registerBlockAction((ClientboundPacketType)ClientboundPackets1_19.BLOCK_ACTION);
/*  59 */     blockRewriter.registerBlockChange((ClientboundPacketType)ClientboundPackets1_19.BLOCK_CHANGE);
/*  60 */     blockRewriter.registerVarLongMultiBlockChange((ClientboundPacketType)ClientboundPackets1_19.MULTI_BLOCK_CHANGE);
/*  61 */     blockRewriter.registerEffect((ClientboundPacketType)ClientboundPackets1_19.EFFECT, 1010, 2001);
/*     */     
/*  63 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_17.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
/*     */     
/*  65 */     ((Protocol1_18_2To1_19)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19.TRADE_LIST, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  68 */             map((Type)Type.VAR_INT);
/*  69 */             handler(wrapper -> {
/*     */                   int size = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)size));
/*     */                   
/*     */                   for (int i = 0; i < size; i++) {
/*     */                     BlockItemPackets1_19.this.handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */                     
/*     */                     BlockItemPackets1_19.this.handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */                     Item secondItem = (Item)wrapper.read(Type.FLAT_VAR_INT_ITEM);
/*     */                     if (secondItem != null) {
/*     */                       BlockItemPackets1_19.this.handleItemToClient(secondItem);
/*     */                       wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */                       wrapper.write(Type.FLAT_VAR_INT_ITEM, secondItem);
/*     */                     } else {
/*     */                       wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
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
/*  97 */     registerWindowPropertyEnchantmentHandler((ClientboundPacketType)ClientboundPackets1_19.WINDOW_PROPERTY);
/*     */     
/*  99 */     ((Protocol1_18_2To1_19)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19.BLOCK_CHANGED_ACK, null, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 102 */             read((Type)Type.VAR_INT);
/* 103 */             handler(PacketWrapper::cancel);
/*     */           }
/*     */         });
/*     */     
/* 107 */     ((Protocol1_18_2To1_19)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19.SPAWN_PARTICLE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 110 */             map((Type)Type.VAR_INT, (Type)Type.INT);
/* 111 */             map((Type)Type.BOOLEAN);
/* 112 */             map((Type)Type.DOUBLE);
/* 113 */             map((Type)Type.DOUBLE);
/* 114 */             map((Type)Type.DOUBLE);
/* 115 */             map((Type)Type.FLOAT);
/* 116 */             map((Type)Type.FLOAT);
/* 117 */             map((Type)Type.FLOAT);
/* 118 */             map((Type)Type.FLOAT);
/* 119 */             map((Type)Type.INT);
/* 120 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   ParticleMappings particleMappings = ((Protocol1_18_2To1_19)BlockItemPackets1_19.this.protocol).getMappingData().getParticleMappings();
/*     */                   
/*     */                   if (id == particleMappings.id("sculk_charge")) {
/*     */                     wrapper.set((Type)Type.INT, 0, Integer.valueOf(-1));
/*     */                     
/*     */                     wrapper.cancel();
/*     */                   } else if (id == particleMappings.id("shriek")) {
/*     */                     wrapper.set((Type)Type.INT, 0, Integer.valueOf(-1));
/*     */                     wrapper.cancel();
/*     */                   } else if (id == particleMappings.id("vibration")) {
/*     */                     wrapper.set((Type)Type.INT, 0, Integer.valueOf(-1));
/*     */                     wrapper.cancel();
/*     */                   } 
/*     */                 });
/* 137 */             handler(BlockItemPackets1_19.this.getSpawnParticleHandler(Type.FLAT_VAR_INT_ITEM));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 142 */     ((Protocol1_18_2To1_19)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_19.CHUNK_DATA, wrapper -> {
/*     */           EntityTracker tracker = ((Protocol1_18_2To1_19)this.protocol).getEntityRewriter().tracker(wrapper.user());
/*     */           
/*     */           Chunk1_18Type chunkType = new Chunk1_18Type(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(((Protocol1_18_2To1_19)this.protocol).getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(tracker.biomesSent()));
/*     */           
/*     */           Chunk chunk = (Chunk)wrapper.passthrough((Type)chunkType);
/*     */           
/*     */           for (ChunkSection section : chunk.getSections()) {
/*     */             DataPalette blockPalette = section.palette(PaletteType.BLOCKS);
/*     */             
/*     */             for (int i = 0; i < blockPalette.size(); i++) {
/*     */               int id = blockPalette.idByIndex(i);
/*     */               blockPalette.setIdByIndex(i, ((Protocol1_18_2To1_19)this.protocol).getMappingData().getNewBlockStateId(id));
/*     */             } 
/*     */           } 
/*     */         });
/* 158 */     ((Protocol1_18_2To1_19)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_17.PLAYER_DIGGING, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 161 */             map((Type)Type.VAR_INT);
/* 162 */             map(Type.POSITION1_14);
/* 163 */             map((Type)Type.UNSIGNED_BYTE);
/* 164 */             create((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */           }
/*     */         });
/* 167 */     ((Protocol1_18_2To1_19)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_17.PLAYER_BLOCK_PLACEMENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 170 */             map((Type)Type.VAR_INT);
/* 171 */             map(Type.POSITION1_14);
/* 172 */             map((Type)Type.VAR_INT);
/* 173 */             map((Type)Type.FLOAT);
/* 174 */             map((Type)Type.FLOAT);
/* 175 */             map((Type)Type.FLOAT);
/* 176 */             map((Type)Type.BOOLEAN);
/* 177 */             create((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */           }
/*     */         });
/* 180 */     ((Protocol1_18_2To1_19)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_17.USE_ITEM, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 183 */             map((Type)Type.VAR_INT);
/* 184 */             create((Type)Type.VAR_INT, Integer.valueOf(0));
/*     */           }
/*     */         });
/*     */     
/* 188 */     ((Protocol1_18_2To1_19)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_17.SET_BEACON_EFFECT, wrapper -> {
/*     */           int primaryEffect = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           if (primaryEffect != -1) {
/*     */             wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(primaryEffect));
/*     */           } else {
/*     */             wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */           } 
/*     */           int secondaryEffect = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           if (secondaryEffect != -1) {
/*     */             wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */             wrapper.write((Type)Type.VAR_INT, Integer.valueOf(secondaryEffect));
/*     */           } else {
/*     */             wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */           } 
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_18_2to1_19\packets\BlockItemPackets1_19.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
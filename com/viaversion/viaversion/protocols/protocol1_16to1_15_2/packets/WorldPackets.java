/*     */ package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.UUIDIntArrayType;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
/*     */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types.Chunk1_15Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.types.Chunk1_16Type;
/*     */ import com.viaversion.viaversion.rewriter.BlockRewriter;
/*     */ import com.viaversion.viaversion.util.CompactArrayUtil;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
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
/*     */ public class WorldPackets
/*     */ {
/*     */   public static void register(Protocol1_16To1_15_2 protocol) {
/*  45 */     BlockRewriter<ClientboundPackets1_15> blockRewriter = new BlockRewriter((Protocol)protocol, Type.POSITION1_14);
/*     */     
/*  47 */     blockRewriter.registerBlockAction((ClientboundPacketType)ClientboundPackets1_15.BLOCK_ACTION);
/*  48 */     blockRewriter.registerBlockChange((ClientboundPacketType)ClientboundPackets1_15.BLOCK_CHANGE);
/*  49 */     blockRewriter.registerMultiBlockChange((ClientboundPacketType)ClientboundPackets1_15.MULTI_BLOCK_CHANGE);
/*  50 */     blockRewriter.registerAcknowledgePlayerDigging((ClientboundPacketType)ClientboundPackets1_15.ACKNOWLEDGE_PLAYER_DIGGING);
/*     */     
/*  52 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_15.UPDATE_LIGHT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  55 */             map((Type)Type.VAR_INT);
/*  56 */             map((Type)Type.VAR_INT);
/*  57 */             handler(wrapper -> wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(true)));
/*     */           }
/*     */         });
/*     */     
/*  61 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_15.CHUNK_DATA, wrapper -> {
/*     */           Chunk chunk = (Chunk)wrapper.read((Type)new Chunk1_15Type());
/*     */           
/*     */           wrapper.write((Type)new Chunk1_16Type(), chunk);
/*     */           
/*     */           chunk.setIgnoreOldLightData(chunk.isFullChunk());
/*     */           
/*     */           for (int s = 0; s < (chunk.getSections()).length; s++) {
/*     */             ChunkSection section = chunk.getSections()[s];
/*     */             
/*     */             if (section != null) {
/*     */               DataPalette palette = section.palette(PaletteType.BLOCKS);
/*     */               
/*     */               for (int i = 0; i < palette.size(); i++) {
/*     */                 int mappedBlockStateId = protocol.getMappingData().getNewBlockStateId(palette.idByIndex(i));
/*     */                 palette.setIdByIndex(i, mappedBlockStateId);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           CompoundTag heightMaps = chunk.getHeightMap();
/*     */           for (Tag heightMapTag : heightMaps.values()) {
/*     */             LongArrayTag heightMap = (LongArrayTag)heightMapTag;
/*     */             int[] heightMapData = new int[256];
/*     */             CompactArrayUtil.iterateCompactArray(9, heightMapData.length, heightMap.getValue(), ());
/*     */             heightMap.setValue(CompactArrayUtil.createCompactArrayWithPadding(9, heightMapData.length, ()));
/*     */           } 
/*     */           if (chunk.getBlockEntities() == null) {
/*     */             return;
/*     */           }
/*     */           for (CompoundTag blockEntity : chunk.getBlockEntities()) {
/*     */             handleBlockEntity(protocol, blockEntity);
/*     */           }
/*     */         });
/*  94 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_15.BLOCK_ENTITY_DATA, wrapper -> {
/*     */           wrapper.passthrough(Type.POSITION1_14);
/*     */           
/*     */           wrapper.passthrough((Type)Type.UNSIGNED_BYTE);
/*     */           CompoundTag tag = (CompoundTag)wrapper.passthrough(Type.NBT);
/*     */           handleBlockEntity(protocol, tag);
/*     */         });
/* 101 */     blockRewriter.registerEffect((ClientboundPacketType)ClientboundPackets1_15.EFFECT, 1010, 2001);
/*     */   }
/*     */   
/*     */   private static void handleBlockEntity(Protocol1_16To1_15_2 protocol, CompoundTag compoundTag) {
/* 105 */     StringTag idTag = (StringTag)compoundTag.get("id");
/* 106 */     if (idTag == null)
/*     */       return; 
/* 108 */     String id = idTag.getValue();
/* 109 */     if (id.equals("minecraft:conduit")) {
/* 110 */       Tag targetUuidTag = compoundTag.remove("target_uuid");
/* 111 */       if (!(targetUuidTag instanceof StringTag)) {
/*     */         return;
/*     */       }
/* 114 */       UUID targetUuid = UUID.fromString((String)targetUuidTag.getValue());
/* 115 */       compoundTag.put("Target", (Tag)new IntArrayTag(UUIDIntArrayType.uuidToIntArray(targetUuid)));
/* 116 */     } else if (id.equals("minecraft:skull") && compoundTag.get("Owner") instanceof CompoundTag) {
/* 117 */       CompoundTag ownerTag = (CompoundTag)compoundTag.remove("Owner");
/* 118 */       StringTag ownerUuidTag = (StringTag)ownerTag.remove("Id");
/* 119 */       if (ownerUuidTag != null) {
/* 120 */         UUID ownerUuid = UUID.fromString(ownerUuidTag.getValue());
/* 121 */         ownerTag.put("Id", (Tag)new IntArrayTag(UUIDIntArrayType.uuidToIntArray(ownerUuid)));
/*     */       } 
/*     */ 
/*     */       
/* 125 */       CompoundTag skullOwnerTag = new CompoundTag();
/* 126 */       for (Map.Entry<String, Tag> entry : (Iterable<Map.Entry<String, Tag>>)ownerTag.entrySet()) {
/* 127 */         skullOwnerTag.put(entry.getKey(), entry.getValue());
/*     */       }
/* 129 */       compoundTag.put("SkullOwner", (Tag)skullOwnerTag);
/* 130 */     } else if (id.equals("minecraft:sign")) {
/* 131 */       for (int i = 1; i <= 4; i++) {
/* 132 */         Tag line = compoundTag.get("Text" + i);
/* 133 */         if (line instanceof StringTag) {
/* 134 */           JsonElement text = protocol.getComponentRewriter().processText(((StringTag)line).getValue());
/* 135 */           compoundTag.put("Text" + i, (Tag)new StringTag(text.toString()));
/*     */         } 
/*     */       } 
/* 138 */     } else if (id.equals("minecraft:mob_spawner")) {
/* 139 */       Tag spawnDataTag = compoundTag.get("SpawnData");
/* 140 */       if (spawnDataTag instanceof CompoundTag) {
/* 141 */         Tag spawnDataIdTag = ((CompoundTag)spawnDataTag).get("id");
/* 142 */         if (spawnDataIdTag instanceof StringTag) {
/* 143 */           StringTag spawnDataIdStringTag = (StringTag)spawnDataIdTag;
/* 144 */           if (spawnDataIdStringTag.getValue().equals("minecraft:zombie_pigman"))
/* 145 */             spawnDataIdStringTag.setValue("minecraft:zombified_piglin"); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_16to1_15_2\packets\WorldPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10;
/*     */ import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
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
/*     */ public class BlockItemPackets1_10
/*     */   extends LegacyBlockItemRewriter<ClientboundPackets1_9_3, ServerboundPackets1_9_3, Protocol1_9_4To1_10>
/*     */ {
/*     */   public BlockItemPackets1_10(Protocol1_9_4To1_10 protocol) {
/*  36 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  41 */     registerSetSlot((ClientboundPacketType)ClientboundPackets1_9_3.SET_SLOT, Type.ITEM);
/*  42 */     registerWindowItems((ClientboundPacketType)ClientboundPackets1_9_3.WINDOW_ITEMS, Type.ITEM_ARRAY);
/*     */ 
/*     */     
/*  45 */     registerEntityEquipment((ClientboundPacketType)ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
/*     */     
/*  47 */     ((Protocol1_9_4To1_10)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
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
/*     */                       wrapper.write(Type.ITEM, BlockItemPackets1_10.this.handleItemToClient((Item)wrapper.read(Type.ITEM)));
/*     */                       
/*     */                       wrapper.write(Type.ITEM, BlockItemPackets1_10.this.handleItemToClient((Item)wrapper.read(Type.ITEM)));
/*     */                       
/*     */                       boolean secondItem = ((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue();
/*     */                       if (secondItem) {
/*     */                         wrapper.write(Type.ITEM, BlockItemPackets1_10.this.handleItemToClient((Item)wrapper.read(Type.ITEM)));
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
/*  78 */     ((Protocol1_9_4To1_10)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.CHUNK_DATA, wrapper -> {
/*     */           ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */           
/*     */           Chunk1_9_3_4Type type = new Chunk1_9_3_4Type(clientWorld);
/*     */           
/*     */           Chunk chunk = (Chunk)wrapper.passthrough((Type)type);
/*     */           
/*     */           handleChunk(chunk);
/*     */         });
/*     */     
/*  88 */     ((Protocol1_9_4To1_10)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  91 */             map(Type.POSITION);
/*  92 */             map((Type)Type.VAR_INT);
/*     */             
/*  94 */             handler(wrapper -> {
/*     */                   int idx = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(BlockItemPackets1_10.this.handleBlockID(idx)));
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 102 */     ((Protocol1_9_4To1_10)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.MULTI_BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 105 */             map((Type)Type.INT);
/* 106 */             map((Type)Type.INT);
/* 107 */             map(Type.BLOCK_CHANGE_RECORD_ARRAY);
/*     */             
/* 109 */             handler(wrapper -> {
/*     */                   for (BlockChangeRecord record : (BlockChangeRecord[])wrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
/*     */                     record.setBlockId(BlockItemPackets1_10.this.handleBlockID(record.getBlockId()));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 118 */     ((Protocol1_9_4To1_10)this.protocol).getEntityRewriter().filter().handler((event, meta) -> {
/*     */           if (meta.metaType().type().equals(Type.ITEM)) {
/*     */             meta.setValue(handleItemToClient((Item)meta.getValue()));
/*     */           }
/*     */         });
/*     */     
/* 124 */     ((Protocol1_9_4To1_10)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.SPAWN_PARTICLE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 127 */             map((Type)Type.INT);
/* 128 */             map((Type)Type.BOOLEAN);
/* 129 */             map((Type)Type.FLOAT);
/* 130 */             map((Type)Type.FLOAT);
/* 131 */             map((Type)Type.FLOAT);
/* 132 */             map((Type)Type.FLOAT);
/* 133 */             map((Type)Type.FLOAT);
/* 134 */             map((Type)Type.FLOAT);
/* 135 */             map((Type)Type.FLOAT);
/* 136 */             map((Type)Type.INT);
/*     */             
/* 138 */             handler(wrapper -> {
/*     */                   int id = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   if (id == 46)
/*     */                     wrapper.set((Type)Type.INT, 0, Integer.valueOf(38)); 
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_9_4to1_10\packets\BlockItemPackets1_10.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
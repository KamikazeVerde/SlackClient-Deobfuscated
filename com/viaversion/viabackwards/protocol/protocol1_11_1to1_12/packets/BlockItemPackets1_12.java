/*     */ package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.MapColorMapping;
/*     */ import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ServerboundPackets1_12;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
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
/*     */ public class BlockItemPackets1_12
/*     */   extends LegacyBlockItemRewriter<ClientboundPackets1_12, ServerboundPackets1_9_3, Protocol1_11_1To1_12>
/*     */ {
/*     */   public BlockItemPackets1_12(Protocol1_11_1To1_12 protocol) {
/*  46 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  51 */     ((Protocol1_11_1To1_12)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_12.MAP_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  54 */             map((Type)Type.VAR_INT);
/*  55 */             map((Type)Type.BYTE);
/*  56 */             map((Type)Type.BOOLEAN);
/*  57 */             handler(wrapper -> {
/*     */                   int count = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */                   for (int i = 0; i < count * 3; i++) {
/*     */                     wrapper.passthrough((Type)Type.BYTE);
/*     */                   }
/*     */                 });
/*  63 */             handler(wrapper -> {
/*     */                   short columns = ((Short)wrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                   if (columns <= 0) {
/*     */                     return;
/*     */                   }
/*     */                   wrapper.passthrough((Type)Type.UNSIGNED_BYTE);
/*     */                   wrapper.passthrough((Type)Type.UNSIGNED_BYTE);
/*     */                   wrapper.passthrough((Type)Type.UNSIGNED_BYTE);
/*     */                   byte[] data = (byte[])wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                   for (int i = 0; i < data.length; i++) {
/*     */                     short color = (short)(data[i] & 0xFF);
/*     */                     if (color > 143) {
/*     */                       color = (short)MapColorMapping.getNearestOldColor(color);
/*     */                       data[i] = (byte)color;
/*     */                     } 
/*     */                   } 
/*     */                   wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, data);
/*     */                 });
/*     */           }
/*     */         });
/*  83 */     registerSetSlot((ClientboundPacketType)ClientboundPackets1_12.SET_SLOT, Type.ITEM);
/*  84 */     registerWindowItems((ClientboundPacketType)ClientboundPackets1_12.WINDOW_ITEMS, Type.ITEM_ARRAY);
/*  85 */     registerEntityEquipment((ClientboundPacketType)ClientboundPackets1_12.ENTITY_EQUIPMENT, Type.ITEM);
/*     */ 
/*     */     
/*  88 */     ((Protocol1_11_1To1_12)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_12.PLUGIN_MESSAGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  91 */             map(Type.STRING);
/*     */             
/*  93 */             handler(wrapper -> {
/*     */                   if (((String)wrapper.get(Type.STRING, 0)).equalsIgnoreCase("MC|TrList")) {
/*     */                     wrapper.passthrough((Type)Type.INT);
/*     */                     
/*     */                     int size = ((Short)wrapper.passthrough((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                     
/*     */                     for (int i = 0; i < size; i++) {
/*     */                       wrapper.write(Type.ITEM, BlockItemPackets1_12.this.handleItemToClient((Item)wrapper.read(Type.ITEM)));
/*     */                       
/*     */                       wrapper.write(Type.ITEM, BlockItemPackets1_12.this.handleItemToClient((Item)wrapper.read(Type.ITEM)));
/*     */                       boolean secondItem = ((Boolean)wrapper.passthrough((Type)Type.BOOLEAN)).booleanValue();
/*     */                       if (secondItem) {
/*     */                         wrapper.write(Type.ITEM, BlockItemPackets1_12.this.handleItemToClient((Item)wrapper.read(Type.ITEM)));
/*     */                       }
/*     */                       wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                       wrapper.passthrough((Type)Type.INT);
/*     */                       wrapper.passthrough((Type)Type.INT);
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 115 */     ((Protocol1_11_1To1_12)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_9_3.CLICK_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 118 */             map((Type)Type.UNSIGNED_BYTE);
/* 119 */             map((Type)Type.SHORT);
/* 120 */             map((Type)Type.BYTE);
/* 121 */             map((Type)Type.SHORT);
/* 122 */             map((Type)Type.VAR_INT);
/* 123 */             map(Type.ITEM);
/*     */             
/* 125 */             handler(wrapper -> {
/*     */                   if (((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue() == 1) {
/*     */                     wrapper.set(Type.ITEM, 0, null);
/*     */                     
/*     */                     PacketWrapper confirm = wrapper.create((PacketType)ServerboundPackets1_12.WINDOW_CONFIRMATION);
/*     */                     
/*     */                     confirm.write((Type)Type.UNSIGNED_BYTE, wrapper.get((Type)Type.UNSIGNED_BYTE, 0));
/*     */                     
/*     */                     confirm.write((Type)Type.SHORT, wrapper.get((Type)Type.SHORT, 1));
/*     */                     
/*     */                     confirm.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                     
/*     */                     wrapper.sendToServer(Protocol1_11_1To1_12.class);
/*     */                     
/*     */                     wrapper.cancel();
/*     */                     
/*     */                     confirm.sendToServer(Protocol1_11_1To1_12.class);
/*     */                     
/*     */                     return;
/*     */                   } 
/*     */                   
/*     */                   Item item = (Item)wrapper.get(Type.ITEM, 0);
/*     */                   BlockItemPackets1_12.this.handleItemToServer(item);
/*     */                 });
/*     */           }
/*     */         });
/* 151 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
/*     */     
/* 153 */     ((Protocol1_11_1To1_12)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_12.CHUNK_DATA, wrapper -> {
/*     */           ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */           
/*     */           Chunk1_9_3_4Type type = new Chunk1_9_3_4Type(clientWorld);
/*     */           
/*     */           Chunk chunk = (Chunk)wrapper.passthrough((Type)type);
/*     */           
/*     */           handleChunk(chunk);
/*     */         });
/* 162 */     ((Protocol1_11_1To1_12)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_12.BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 165 */             map(Type.POSITION);
/* 166 */             map((Type)Type.VAR_INT);
/*     */             
/* 168 */             handler(wrapper -> {
/*     */                   int idx = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   wrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(BlockItemPackets1_12.this.handleBlockID(idx)));
/*     */                 });
/*     */           }
/*     */         });
/* 175 */     ((Protocol1_11_1To1_12)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_12.MULTI_BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 178 */             map((Type)Type.INT);
/* 179 */             map((Type)Type.INT);
/* 180 */             map(Type.BLOCK_CHANGE_RECORD_ARRAY);
/*     */             
/* 182 */             handler(wrapper -> {
/*     */                   for (BlockChangeRecord record : (BlockChangeRecord[])wrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
/*     */                     record.setBlockId(BlockItemPackets1_12.this.handleBlockID(record.getBlockId()));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 190 */     ((Protocol1_11_1To1_12)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_12.BLOCK_ENTITY_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 193 */             map(Type.POSITION);
/* 194 */             map((Type)Type.UNSIGNED_BYTE);
/* 195 */             map(Type.NBT);
/*     */             
/* 197 */             handler(wrapper -> {
/*     */                   if (((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue() == 11) {
/*     */                     wrapper.cancel();
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 205 */     ((Protocol1_11_1To1_12)this.protocol).getEntityRewriter().filter().handler((event, meta) -> {
/*     */           if (meta.metaType().type().equals(Type.ITEM)) {
/*     */             meta.setValue(handleItemToClient((Item)meta.getValue()));
/*     */           }
/*     */         });
/* 210 */     ((Protocol1_11_1To1_12)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_9_3.CLIENT_STATUS, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 213 */             map((Type)Type.VAR_INT);
/*     */             
/* 215 */             handler(wrapper -> {
/*     */                   if (((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue() == 2) {
/*     */                     wrapper.cancel();
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item handleItemToClient(Item item) {
/* 227 */     if (item == null) return null; 
/* 228 */     super.handleItemToClient(item);
/*     */     
/* 230 */     if (item.tag() != null) {
/* 231 */       CompoundTag backupTag = new CompoundTag();
/* 232 */       if (handleNbtToClient(item.tag(), backupTag)) {
/* 233 */         item.tag().put("Via|LongArrayTags", (Tag)backupTag);
/*     */       }
/*     */     } 
/*     */     
/* 237 */     return item;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean handleNbtToClient(CompoundTag compoundTag, CompoundTag backupTag) {
/* 243 */     Iterator<Map.Entry<String, Tag>> iterator = compoundTag.iterator();
/* 244 */     boolean hasLongArrayTag = false;
/* 245 */     while (iterator.hasNext()) {
/* 246 */       Map.Entry<String, Tag> entry = iterator.next();
/* 247 */       if (entry.getValue() instanceof CompoundTag) {
/* 248 */         CompoundTag nestedBackupTag = new CompoundTag();
/* 249 */         backupTag.put(entry.getKey(), (Tag)nestedBackupTag);
/* 250 */         hasLongArrayTag |= handleNbtToClient((CompoundTag)entry.getValue(), nestedBackupTag); continue;
/* 251 */       }  if (entry.getValue() instanceof LongArrayTag) {
/* 252 */         backupTag.put(entry.getKey(), (Tag)fromLongArrayTag((LongArrayTag)entry.getValue()));
/* 253 */         iterator.remove();
/* 254 */         hasLongArrayTag = true;
/*     */       } 
/*     */     } 
/* 257 */     return hasLongArrayTag;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToServer(Item item) {
/* 262 */     if (item == null) return null; 
/* 263 */     super.handleItemToServer(item);
/*     */     
/* 265 */     if (item.tag() != null) {
/* 266 */       Tag tag = item.tag().remove("Via|LongArrayTags");
/* 267 */       if (tag instanceof CompoundTag) {
/* 268 */         handleNbtToServer(item.tag(), (CompoundTag)tag);
/*     */       }
/*     */     } 
/*     */     
/* 272 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleNbtToServer(CompoundTag compoundTag, CompoundTag backupTag) {
/* 277 */     for (Map.Entry<String, Tag> entry : (Iterable<Map.Entry<String, Tag>>)backupTag) {
/* 278 */       if (entry.getValue() instanceof CompoundTag) {
/* 279 */         CompoundTag nestedTag = (CompoundTag)compoundTag.get(entry.getKey());
/* 280 */         handleNbtToServer(nestedTag, (CompoundTag)entry.getValue()); continue;
/*     */       } 
/* 282 */       compoundTag.put(entry.getKey(), (Tag)fromIntArrayTag((IntArrayTag)entry.getValue()));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private IntArrayTag fromLongArrayTag(LongArrayTag tag) {
/* 288 */     int[] intArray = new int[tag.length() * 2];
/* 289 */     long[] longArray = tag.getValue();
/* 290 */     int i = 0;
/* 291 */     for (long l : longArray) {
/* 292 */       intArray[i++] = (int)(l >> 32L);
/* 293 */       intArray[i++] = (int)l;
/*     */     } 
/* 295 */     return new IntArrayTag(intArray);
/*     */   }
/*     */   
/*     */   private LongArrayTag fromIntArrayTag(IntArrayTag tag) {
/* 299 */     long[] longArray = new long[tag.length() / 2];
/* 300 */     int[] intArray = tag.getValue();
/* 301 */     for (int i = 0, j = 0; i < intArray.length; i += 2, j++) {
/* 302 */       longArray[j] = intArray[i] << 32L | intArray[i + 1] & 0xFFFFFFFFL;
/*     */     }
/* 304 */     return new LongArrayTag(longArray);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_11_1to1_12\packets\BlockItemPackets1_12.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
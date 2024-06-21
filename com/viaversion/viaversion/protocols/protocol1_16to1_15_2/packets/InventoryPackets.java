/*     */ package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.UUIDIntArrayType;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.storage.InventoryTracker1_16;
/*     */ import com.viaversion.viaversion.rewriter.ItemRewriter;
/*     */ import com.viaversion.viaversion.rewriter.RecipeRewriter;
/*     */ import com.viaversion.viaversion.util.Key;
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
/*     */ public class InventoryPackets
/*     */   extends ItemRewriter<ClientboundPackets1_15, ServerboundPackets1_16, Protocol1_16To1_15_2>
/*     */ {
/*     */   public InventoryPackets(Protocol1_16To1_15_2 protocol) {
/*  46 */     super((Protocol)protocol);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerPackets() {
/*  52 */     final PacketHandler cursorRemapper = wrapper -> {
/*     */         PacketWrapper clearPacket = wrapper.create((PacketType)ClientboundPackets1_16.SET_SLOT);
/*     */         
/*     */         clearPacket.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)-1));
/*     */         clearPacket.write((Type)Type.SHORT, Short.valueOf((short)-1));
/*     */         clearPacket.write(Type.FLAT_VAR_INT_ITEM, null);
/*     */         clearPacket.send(Protocol1_16To1_15_2.class);
/*     */       };
/*  60 */     ((Protocol1_16To1_15_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_15.OPEN_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  63 */             map((Type)Type.VAR_INT);
/*  64 */             map((Type)Type.VAR_INT);
/*  65 */             map(Type.COMPONENT);
/*     */             
/*  67 */             handler(cursorRemapper);
/*  68 */             handler(wrapper -> {
/*     */                   InventoryTracker1_16 inventoryTracker = (InventoryTracker1_16)wrapper.user().get(InventoryTracker1_16.class);
/*     */                   
/*     */                   int windowType = ((Integer)wrapper.get((Type)Type.VAR_INT, 1)).intValue();
/*     */                   if (windowType >= 20) {
/*     */                     wrapper.set((Type)Type.VAR_INT, 1, Integer.valueOf(++windowType));
/*     */                   }
/*     */                   inventoryTracker.setInventoryOpen(true);
/*     */                 });
/*     */           }
/*     */         });
/*  79 */     ((Protocol1_16To1_15_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_15.CLOSE_WINDOW, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  82 */             handler(cursorRemapper);
/*  83 */             handler(wrapper -> {
/*     */                   InventoryTracker1_16 inventoryTracker = (InventoryTracker1_16)wrapper.user().get(InventoryTracker1_16.class);
/*     */                   
/*     */                   inventoryTracker.setInventoryOpen(false);
/*     */                 });
/*     */           }
/*     */         });
/*  90 */     ((Protocol1_16To1_15_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_15.WINDOW_PROPERTY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  93 */             map((Type)Type.UNSIGNED_BYTE);
/*  94 */             map((Type)Type.SHORT);
/*  95 */             map((Type)Type.SHORT);
/*     */             
/*  97 */             handler(wrapper -> {
/*     */                   short property = ((Short)wrapper.get((Type)Type.SHORT, 0)).shortValue();
/*     */                   if (property >= 4 && property <= 6) {
/*     */                     short enchantmentId = ((Short)wrapper.get((Type)Type.SHORT, 1)).shortValue();
/*     */                     if (enchantmentId >= 11) {
/*     */                       enchantmentId = (short)(enchantmentId + 1);
/*     */                       wrapper.set((Type)Type.SHORT, 1, Short.valueOf(enchantmentId));
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 109 */     registerSetCooldown((ClientboundPacketType)ClientboundPackets1_15.COOLDOWN);
/* 110 */     registerWindowItems((ClientboundPacketType)ClientboundPackets1_15.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
/* 111 */     registerTradeList((ClientboundPacketType)ClientboundPackets1_15.TRADE_LIST);
/* 112 */     registerSetSlot((ClientboundPacketType)ClientboundPackets1_15.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
/* 113 */     registerAdvancements((ClientboundPacketType)ClientboundPackets1_15.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
/*     */     
/* 115 */     ((Protocol1_16To1_15_2)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_15.ENTITY_EQUIPMENT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 118 */             map((Type)Type.VAR_INT);
/*     */             
/* 120 */             handler(wrapper -> {
/*     */                   int slot = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)slot));
/*     */                   InventoryPackets.this.handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
/*     */                 });
/*     */           }
/*     */         });
/* 128 */     (new RecipeRewriter(this.protocol)).register((ClientboundPacketType)ClientboundPackets1_15.DECLARE_RECIPES);
/*     */     
/* 130 */     registerClickWindow((ServerboundPacketType)ServerboundPackets1_16.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
/* 131 */     registerCreativeInvAction((ServerboundPacketType)ServerboundPackets1_16.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
/*     */     
/* 133 */     ((Protocol1_16To1_15_2)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_16.CLOSE_WINDOW, wrapper -> {
/*     */           InventoryTracker1_16 inventoryTracker = (InventoryTracker1_16)wrapper.user().get(InventoryTracker1_16.class);
/*     */           
/*     */           inventoryTracker.setInventoryOpen(false);
/*     */         });
/* 138 */     ((Protocol1_16To1_15_2)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_16.EDIT_BOOK, wrapper -> handleItemToServer((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM)));
/*     */     
/* 140 */     registerSpawnParticle((ClientboundPacketType)ClientboundPackets1_15.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, (Type)Type.DOUBLE);
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToClient(Item item) {
/* 145 */     if (item == null) return null;
/*     */     
/* 147 */     CompoundTag tag = item.tag();
/*     */     
/* 149 */     if (item.identifier() == 771 && tag != null) {
/* 150 */       Tag ownerTag = tag.get("SkullOwner");
/* 151 */       if (ownerTag instanceof CompoundTag) {
/* 152 */         CompoundTag ownerCompundTag = (CompoundTag)ownerTag;
/* 153 */         Tag idTag = ownerCompundTag.get("Id");
/* 154 */         if (idTag instanceof StringTag) {
/* 155 */           UUID id = UUID.fromString((String)idTag.getValue());
/* 156 */           ownerCompundTag.put("Id", (Tag)new IntArrayTag(UUIDIntArrayType.uuidToIntArray(id)));
/*     */         } 
/*     */       } 
/* 159 */     } else if (item.identifier() == 759 && tag != null) {
/* 160 */       Tag pages = tag.get("pages");
/* 161 */       if (pages instanceof ListTag) {
/* 162 */         for (Tag pageTag : pages) {
/* 163 */           if (!(pageTag instanceof StringTag)) {
/*     */             continue;
/*     */           }
/* 166 */           StringTag page = (StringTag)pageTag;
/* 167 */           page.setValue(((Protocol1_16To1_15_2)this.protocol).getComponentRewriter().processText(page.getValue()).toString());
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 172 */     oldToNewAttributes(item);
/* 173 */     item.setIdentifier(Protocol1_16To1_15_2.MAPPINGS.getNewItemId(item.identifier()));
/* 174 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item handleItemToServer(Item item) {
/* 179 */     if (item == null) return null;
/*     */     
/* 181 */     item.setIdentifier(Protocol1_16To1_15_2.MAPPINGS.getOldItemId(item.identifier()));
/*     */     
/* 183 */     if (item.identifier() == 771 && item.tag() != null) {
/* 184 */       CompoundTag tag = item.tag();
/* 185 */       Tag ownerTag = tag.get("SkullOwner");
/* 186 */       if (ownerTag instanceof CompoundTag) {
/* 187 */         CompoundTag ownerCompundTag = (CompoundTag)ownerTag;
/* 188 */         Tag idTag = ownerCompundTag.get("Id");
/* 189 */         if (idTag instanceof IntArrayTag) {
/* 190 */           UUID id = UUIDIntArrayType.uuidFromIntArray((int[])idTag.getValue());
/* 191 */           ownerCompundTag.put("Id", (Tag)new StringTag(id.toString()));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 196 */     newToOldAttributes(item);
/* 197 */     return item;
/*     */   }
/*     */   
/*     */   public static void oldToNewAttributes(Item item) {
/* 201 */     if (item.tag() == null)
/*     */       return; 
/* 203 */     ListTag attributes = (ListTag)item.tag().get("AttributeModifiers");
/* 204 */     if (attributes == null)
/*     */       return; 
/* 206 */     for (Tag tag : attributes) {
/* 207 */       CompoundTag attribute = (CompoundTag)tag;
/* 208 */       rewriteAttributeName(attribute, "AttributeName", false);
/* 209 */       rewriteAttributeName(attribute, "Name", false);
/* 210 */       Tag leastTag = attribute.get("UUIDLeast");
/* 211 */       if (leastTag != null) {
/* 212 */         Tag mostTag = attribute.get("UUIDMost");
/* 213 */         int[] uuidIntArray = UUIDIntArrayType.bitsToIntArray(((NumberTag)leastTag).asLong(), ((NumberTag)mostTag).asLong());
/* 214 */         attribute.put("UUID", (Tag)new IntArrayTag(uuidIntArray));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void newToOldAttributes(Item item) {
/* 220 */     if (item.tag() == null)
/*     */       return; 
/* 222 */     ListTag attributes = (ListTag)item.tag().get("AttributeModifiers");
/* 223 */     if (attributes == null)
/*     */       return; 
/* 225 */     for (Tag tag : attributes) {
/* 226 */       CompoundTag attribute = (CompoundTag)tag;
/* 227 */       rewriteAttributeName(attribute, "AttributeName", true);
/* 228 */       rewriteAttributeName(attribute, "Name", true);
/* 229 */       IntArrayTag uuidTag = (IntArrayTag)attribute.get("UUID");
/* 230 */       if (uuidTag != null && (uuidTag.getValue()).length == 4) {
/* 231 */         UUID uuid = UUIDIntArrayType.uuidFromIntArray(uuidTag.getValue());
/* 232 */         attribute.put("UUIDLeast", (Tag)new LongTag(uuid.getLeastSignificantBits()));
/* 233 */         attribute.put("UUIDMost", (Tag)new LongTag(uuid.getMostSignificantBits()));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void rewriteAttributeName(CompoundTag compoundTag, String entryName, boolean inverse) {
/* 239 */     StringTag attributeNameTag = (StringTag)compoundTag.get(entryName);
/* 240 */     if (attributeNameTag == null)
/*     */       return; 
/* 242 */     String attributeName = attributeNameTag.getValue();
/* 243 */     if (inverse) {
/* 244 */       attributeName = Key.namespaced(attributeName);
/*     */     }
/*     */ 
/*     */     
/* 248 */     String mappedAttribute = (String)(inverse ? Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings().inverse() : Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings()).get(attributeName);
/* 249 */     if (mappedAttribute == null)
/*     */       return; 
/* 251 */     attributeNameTag.setValue(mappedAttribute);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_16to1_15_2\packets\InventoryPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
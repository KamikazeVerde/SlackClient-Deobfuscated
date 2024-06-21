/*     */ package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
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
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
/*     */ import java.util.Collections;
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
/*     */ public class PlayerPackets
/*     */ {
/*     */   public static void register(Protocol1_14To1_13_2 protocol) {
/*  37 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_13.OPEN_SIGN_EDITOR, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  40 */             map(Type.POSITION, Type.POSITION1_14);
/*     */           }
/*     */         });
/*     */     
/*  44 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_14.QUERY_BLOCK_NBT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  47 */             map((Type)Type.VAR_INT);
/*  48 */             map(Type.POSITION1_14, Type.POSITION);
/*     */           }
/*     */         });
/*     */     
/*  52 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_14.EDIT_BOOK, wrapper -> {
/*     */           Item item = (Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM);
/*     */           
/*     */           protocol.getItemRewriter().handleItemToServer(item);
/*     */           
/*     */           if (item == null) {
/*     */             return;
/*     */           }
/*     */           
/*     */           CompoundTag tag = item.tag();
/*     */           
/*     */           if (tag == null) {
/*     */             return;
/*     */           }
/*     */           Tag pages = tag.get("pages");
/*     */           if (pages == null) {
/*     */             tag.put("pages", (Tag)new ListTag(Collections.singletonList(new StringTag())));
/*     */           }
/*     */           if (Via.getConfig().isTruncate1_14Books() && pages instanceof ListTag) {
/*     */             ListTag listTag = (ListTag)pages;
/*     */             if (listTag.size() > 50) {
/*     */               listTag.setValue(listTag.getValue().subList(0, 50));
/*     */             }
/*     */           } 
/*     */         });
/*  77 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_14.PLAYER_DIGGING, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  80 */             map((Type)Type.VAR_INT);
/*  81 */             map(Type.POSITION1_14, Type.POSITION);
/*     */           }
/*     */         });
/*     */     
/*  85 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_14.RECIPE_BOOK_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  88 */             map((Type)Type.VAR_INT);
/*  89 */             handler(wrapper -> {
/*     */                   int type = ((Integer)wrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   if (type == 0) {
/*     */                     wrapper.passthrough(Type.STRING);
/*     */                   } else if (type == 1) {
/*     */                     wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                     
/*     */                     wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                     
/*     */                     wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                     wrapper.passthrough((Type)Type.BOOLEAN);
/*     */                     wrapper.read((Type)Type.BOOLEAN);
/*     */                     wrapper.read((Type)Type.BOOLEAN);
/*     */                     wrapper.read((Type)Type.BOOLEAN);
/*     */                     wrapper.read((Type)Type.BOOLEAN);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 109 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_14.UPDATE_COMMAND_BLOCK, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 112 */             map(Type.POSITION1_14, Type.POSITION);
/*     */           }
/*     */         });
/* 115 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_14.UPDATE_STRUCTURE_BLOCK, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 118 */             map(Type.POSITION1_14, Type.POSITION);
/*     */           }
/*     */         });
/* 121 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_14.UPDATE_SIGN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 124 */             map(Type.POSITION1_14, Type.POSITION);
/*     */           }
/*     */         });
/*     */     
/* 128 */     protocol.registerServerbound((ServerboundPacketType)ServerboundPackets1_14.PLAYER_BLOCK_PLACEMENT, wrapper -> {
/*     */           int hand = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           Position position = (Position)wrapper.read(Type.POSITION1_14);
/*     */           int face = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           float x = ((Float)wrapper.read((Type)Type.FLOAT)).floatValue();
/*     */           float y = ((Float)wrapper.read((Type)Type.FLOAT)).floatValue();
/*     */           float z = ((Float)wrapper.read((Type)Type.FLOAT)).floatValue();
/*     */           wrapper.read((Type)Type.BOOLEAN);
/*     */           wrapper.write(Type.POSITION, position);
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(face));
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(hand));
/*     */           wrapper.write((Type)Type.FLOAT, Float.valueOf(x));
/*     */           wrapper.write((Type)Type.FLOAT, Float.valueOf(y));
/*     */           wrapper.write((Type)Type.FLOAT, Float.valueOf(z));
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_14to1_13_2\packets\PlayerPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
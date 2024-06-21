/*     */ package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;
/*     */ 
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.DifficultyStorage;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.rewriter.RewriterBase;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
/*     */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
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
/*     */ public class PlayerPackets1_14
/*     */   extends RewriterBase<Protocol1_13_2To1_14>
/*     */ {
/*     */   public PlayerPackets1_14(Protocol1_13_2To1_14 protocol) {
/*  32 */     super((Protocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  37 */     ((Protocol1_13_2To1_14)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.SERVER_DIFFICULTY, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  40 */             map((Type)Type.UNSIGNED_BYTE);
/*  41 */             map((Type)Type.BOOLEAN, (Type)Type.NOTHING);
/*  42 */             handler(wrapper -> {
/*     */                   byte difficulty = ((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).byteValue();
/*     */                   
/*     */                   ((DifficultyStorage)wrapper.user().get(DifficultyStorage.class)).setDifficulty(difficulty);
/*     */                 });
/*     */           }
/*     */         });
/*  49 */     ((Protocol1_13_2To1_14)this.protocol).registerClientbound((ClientboundPacketType)ClientboundPackets1_14.OPEN_SIGN_EDITOR, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  52 */             map(Type.POSITION1_14, Type.POSITION);
/*     */           }
/*     */         });
/*  55 */     ((Protocol1_13_2To1_14)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_13.QUERY_BLOCK_NBT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  58 */             map((Type)Type.VAR_INT);
/*  59 */             map(Type.POSITION, Type.POSITION1_14);
/*     */           }
/*     */         });
/*  62 */     ((Protocol1_13_2To1_14)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_13.PLAYER_DIGGING, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  65 */             map((Type)Type.VAR_INT);
/*  66 */             map(Type.POSITION, Type.POSITION1_14);
/*     */           }
/*     */         });
/*     */     
/*  70 */     ((Protocol1_13_2To1_14)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_13.RECIPE_BOOK_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  73 */             map((Type)Type.VAR_INT);
/*  74 */             handler(wrapper -> {
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
/*     */                     wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                     wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                     wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                     wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  94 */     ((Protocol1_13_2To1_14)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_13.UPDATE_COMMAND_BLOCK, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  97 */             map(Type.POSITION, Type.POSITION1_14);
/*     */           }
/*     */         });
/* 100 */     ((Protocol1_13_2To1_14)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_13.UPDATE_STRUCTURE_BLOCK, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 103 */             map(Type.POSITION, Type.POSITION1_14);
/*     */           }
/*     */         });
/* 106 */     ((Protocol1_13_2To1_14)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_13.UPDATE_SIGN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 109 */             map(Type.POSITION, Type.POSITION1_14);
/*     */           }
/*     */         });
/*     */     
/* 113 */     ((Protocol1_13_2To1_14)this.protocol).registerServerbound((ServerboundPacketType)ServerboundPackets1_13.PLAYER_BLOCK_PLACEMENT, wrapper -> {
/*     */           Position position = (Position)wrapper.read(Type.POSITION);
/*     */           int face = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           int hand = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */           float x = ((Float)wrapper.read((Type)Type.FLOAT)).floatValue();
/*     */           float y = ((Float)wrapper.read((Type)Type.FLOAT)).floatValue();
/*     */           float z = ((Float)wrapper.read((Type)Type.FLOAT)).floatValue();
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(hand));
/*     */           wrapper.write(Type.POSITION1_14, position);
/*     */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(face));
/*     */           wrapper.write((Type)Type.FLOAT, Float.valueOf(x));
/*     */           wrapper.write((Type)Type.FLOAT, Float.valueOf(y));
/*     */           wrapper.write((Type)Type.FLOAT, Float.valueOf(z));
/*     */           wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(false));
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_13_2to1_14\packets\PlayerPackets1_14.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.chunks.BlockEntity;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.Chunk1_9_1_2Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
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
/*     */ public class Protocol1_9_1_2To1_9_3_4
/*     */   extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9, ServerboundPackets1_9_3, ServerboundPackets1_9>
/*     */ {
/*     */   public Protocol1_9_1_2To1_9_3_4() {
/*  42 */     super(ClientboundPackets1_9_3.class, ClientboundPackets1_9.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  47 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  50 */             map(Type.POSITION);
/*  51 */             map((Type)Type.UNSIGNED_BYTE);
/*  52 */             map(Type.NBT);
/*  53 */             handler(wrapper -> {
/*     */                   if (((Short)wrapper.get((Type)Type.UNSIGNED_BYTE, 0)).shortValue() == 9) {
/*     */                     Position position = (Position)wrapper.get(Type.POSITION, 0);
/*     */                     
/*     */                     CompoundTag tag = (CompoundTag)wrapper.get(Type.NBT, 0);
/*     */                     
/*     */                     wrapper.clearPacket();
/*     */                     
/*     */                     wrapper.setPacketType((PacketType)ClientboundPackets1_9.UPDATE_SIGN);
/*     */                     
/*     */                     wrapper.write(Type.POSITION, position);
/*     */                     for (int i = 1; i < 5; i++) {
/*     */                       Tag textTag = tag.get("Text" + i);
/*     */                       String line = (textTag instanceof StringTag) ? ((StringTag)textTag).getValue() : "";
/*     */                       wrapper.write(Type.STRING, line);
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  73 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.CHUNK_DATA, wrapper -> {
/*     */           ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */           
/*     */           Chunk1_9_3_4Type newType = new Chunk1_9_3_4Type(clientWorld);
/*     */           
/*     */           Chunk1_9_1_2Type oldType = new Chunk1_9_1_2Type(clientWorld);
/*     */           
/*     */           Chunk chunk = (Chunk)wrapper.read((Type)newType);
/*     */           wrapper.write((Type)oldType, chunk);
/*     */           BlockEntity.handle(chunk.getBlockEntities(), wrapper.user());
/*     */         });
/*  84 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  87 */             map((Type)Type.INT);
/*  88 */             map((Type)Type.UNSIGNED_BYTE);
/*  89 */             map((Type)Type.INT);
/*     */             
/*  91 */             handler(wrapper -> {
/*     */                   ClientWorld clientChunks = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   
/*     */                   int dimensionId = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   
/*     */                   clientChunks.setEnvironment(dimensionId);
/*     */                 });
/*     */           }
/*     */         });
/* 100 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9_3.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 103 */             map((Type)Type.INT);
/*     */             
/* 105 */             handler(wrapper -> {
/*     */                   ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   int dimensionId = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   clientWorld.setEnvironment(dimensionId);
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(UserConnection userConnection) {
/* 117 */     if (!userConnection.has(ClientWorld.class))
/* 118 */       userConnection.put((StorableObject)new ClientWorld(userConnection)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9_1_2to1_9_3_4\Protocol1_9_1_2To1_9_3_4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
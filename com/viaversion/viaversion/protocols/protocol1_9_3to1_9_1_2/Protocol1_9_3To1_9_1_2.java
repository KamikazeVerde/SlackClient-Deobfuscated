/*     */ package com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.chunks.FakeTileEntity;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.Chunk1_9_1_2Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Protocol1_9_3To1_9_1_2
/*     */   extends AbstractProtocol<ClientboundPackets1_9, ClientboundPackets1_9_3, ServerboundPackets1_9, ServerboundPackets1_9_3>
/*     */ {
/*  45 */   public static final ValueTransformer<Short, Short> ADJUST_PITCH = new ValueTransformer<Short, Short>((Type)Type.UNSIGNED_BYTE, (Type)Type.UNSIGNED_BYTE)
/*     */     {
/*     */       public Short transform(PacketWrapper wrapper, Short inputValue) throws Exception {
/*  48 */         return Short.valueOf((short)Math.round(inputValue.shortValue() / 63.5F * 63.0F));
/*     */       }
/*     */     };
/*     */   
/*     */   public Protocol1_9_3To1_9_1_2() {
/*  53 */     super(ClientboundPackets1_9.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9.class, ServerboundPackets1_9_3.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/*  59 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9.UPDATE_SIGN, null, wrapper -> {
/*     */           Position position = (Position)wrapper.read(Type.POSITION);
/*     */           
/*     */           JsonElement[] lines = new JsonElement[4];
/*     */           
/*     */           for (int i = 0; i < 4; i++) {
/*     */             lines[i] = (JsonElement)wrapper.read(Type.COMPONENT);
/*     */           }
/*     */           
/*     */           wrapper.clearInputBuffer();
/*     */           
/*     */           wrapper.setPacketType((PacketType)ClientboundPackets1_9_3.BLOCK_ENTITY_DATA);
/*     */           
/*     */           wrapper.write(Type.POSITION, position);
/*     */           
/*     */           wrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)9));
/*     */           
/*     */           CompoundTag tag = new CompoundTag();
/*     */           
/*     */           tag.put("id", (Tag)new StringTag("Sign"));
/*     */           tag.put("x", (Tag)new IntTag(position.x()));
/*     */           tag.put("y", (Tag)new IntTag(position.y()));
/*     */           tag.put("z", (Tag)new IntTag(position.z()));
/*     */           for (int j = 0; j < lines.length; j++) {
/*     */             tag.put("Text" + (j + 1), (Tag)new StringTag(lines[j].toString()));
/*     */           }
/*     */           wrapper.write(Type.NBT, tag);
/*     */         });
/*  87 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9.CHUNK_DATA, wrapper -> {
/*     */           ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */           
/*     */           Chunk chunk = (Chunk)wrapper.read((Type)new Chunk1_9_1_2Type(clientWorld));
/*     */           
/*     */           wrapper.write((Type)new Chunk1_9_3_4Type(clientWorld), chunk);
/*     */           
/*     */           List<CompoundTag> tags = chunk.getBlockEntities();
/*     */           
/*     */           for (int s = 0; s < (chunk.getSections()).length; s++) {
/*     */             ChunkSection section = chunk.getSections()[s];
/*     */             
/*     */             if (section != null) {
/*     */               DataPalette blocks = section.palette(PaletteType.BLOCKS);
/*     */               
/*     */               for (int idx = 0; idx < 4096; idx++) {
/*     */                 int id = blocks.idAt(idx) >> 4;
/*     */                 
/*     */                 if (FakeTileEntity.isTileEntity(id)) {
/*     */                   tags.add(FakeTileEntity.createTileEntity(ChunkSection.xFromIndex(idx) + (chunk.getX() << 4), ChunkSection.yFromIndex(idx) + (s << 4), ChunkSection.zFromIndex(idx) + (chunk.getZ() << 4), id));
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         });
/*     */     
/* 113 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9.JOIN_GAME, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 116 */             map((Type)Type.INT);
/* 117 */             map((Type)Type.UNSIGNED_BYTE);
/* 118 */             map((Type)Type.INT);
/*     */             
/* 120 */             handler(wrapper -> {
/*     */                   ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   
/*     */                   int dimensionId = ((Integer)wrapper.get((Type)Type.INT, 1)).intValue();
/*     */                   clientWorld.setEnvironment(dimensionId);
/*     */                 });
/*     */           }
/*     */         });
/* 128 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9.RESPAWN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 131 */             map((Type)Type.INT);
/* 132 */             handler(wrapper -> {
/*     */                   ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
/*     */                   
/*     */                   int dimensionId = ((Integer)wrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   clientWorld.setEnvironment(dimensionId);
/*     */                 });
/*     */           }
/*     */         });
/* 141 */     registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SOUND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 144 */             map((Type)Type.VAR_INT);
/* 145 */             map((Type)Type.VAR_INT);
/* 146 */             map((Type)Type.INT);
/* 147 */             map((Type)Type.INT);
/* 148 */             map((Type)Type.INT);
/* 149 */             map((Type)Type.FLOAT);
/* 150 */             map(Protocol1_9_3To1_9_1_2.ADJUST_PITCH);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(UserConnection user) {
/* 157 */     if (!user.has(ClientWorld.class))
/* 158 */       user.put((StorableObject)new ClientWorld(user)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9_3to1_9_1_2\Protocol1_9_3To1_9_1_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
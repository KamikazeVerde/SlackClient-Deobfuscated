/*     */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.types.CustomByteType;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.types.Chunk1_8Type;
/*     */ import com.viaversion.viaversion.util.ChatColorUtil;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.chunks.ChunkPacketTransformer;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ReplacementRegistry1_7_6_10to1_8;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.WorldBorder;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Chunk1_7_10Type;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Particle;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
/*     */ import de.gerrygames.viarewind.utils.ChatUtil;
/*     */ import de.gerrygames.viarewind.utils.PacketUtil;
/*     */ 
/*     */ 
/*     */ public class WorldPackets
/*     */ {
/*     */   public static void register(Protocol1_7_6_10TO1_8 protocol) {
/*  33 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.CHUNK_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  36 */             handler(packetWrapper -> {
/*     */                   ClientWorld world = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
/*     */                   Chunk chunk = (Chunk)packetWrapper.read((Type)new Chunk1_8Type(world));
/*     */                   packetWrapper.write((Type)new Chunk1_7_10Type(world), chunk);
/*     */                   for (ChunkSection section : chunk.getSections()) {
/*     */                     if (section != null) {
/*     */                       DataPalette palette = section.palette(PaletteType.BLOCKS);
/*     */                       for (int i = 0; i < palette.size(); i++) {
/*     */                         int block = palette.idByIndex(i);
/*     */                         int replacedBlock = ReplacementRegistry1_7_6_10to1_8.replace(block);
/*     */                         palette.setIdByIndex(i, replacedBlock);
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  53 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.MULTI_BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  56 */             map((Type)Type.INT);
/*  57 */             map((Type)Type.INT);
/*  58 */             handler(packetWrapper -> {
/*     */                   BlockChangeRecord[] records = (BlockChangeRecord[])packetWrapper.read(Type.BLOCK_CHANGE_RECORD_ARRAY);
/*     */                   
/*     */                   packetWrapper.write((Type)Type.SHORT, Short.valueOf((short)records.length));
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(records.length * 4));
/*     */                   for (BlockChangeRecord record : records) {
/*     */                     short data = (short)(record.getSectionX() << 12 | record.getSectionZ() << 8 | record.getY());
/*     */                     packetWrapper.write((Type)Type.SHORT, Short.valueOf(data));
/*     */                     int replacedBlock = ReplacementRegistry1_7_6_10to1_8.replace(record.getBlockId());
/*     */                     packetWrapper.write((Type)Type.SHORT, Short.valueOf((short)replacedBlock));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  72 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  75 */             handler(packetWrapper -> {
/*     */                   Position position = (Position)packetWrapper.read(Type.POSITION);
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.x()));
/*     */                   packetWrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)position.y()));
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.z()));
/*     */                 });
/*  81 */             handler(packetWrapper -> {
/*     */                   int data = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   data = ReplacementRegistry1_7_6_10to1_8.replace(data);
/*     */                   
/*     */                   packetWrapper.write((Type)Type.VAR_INT, Integer.valueOf(data >> 4));
/*     */                   
/*     */                   packetWrapper.write((Type)Type.UNSIGNED_BYTE, Short.valueOf((short)(data & 0xF)));
/*     */                 });
/*     */           }
/*     */         });
/*  92 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.BLOCK_ACTION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  95 */             handler(packetWrapper -> {
/*     */                   Position position = (Position)packetWrapper.read(Type.POSITION);
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.x()));
/*     */                   packetWrapper.write((Type)Type.SHORT, Short.valueOf((short)position.y()));
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.z()));
/*     */                 });
/* 101 */             map((Type)Type.UNSIGNED_BYTE);
/* 102 */             map((Type)Type.UNSIGNED_BYTE);
/* 103 */             map((Type)Type.VAR_INT);
/*     */           }
/*     */         });
/*     */     
/* 107 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.BLOCK_BREAK_ANIMATION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 110 */             map((Type)Type.VAR_INT);
/* 111 */             handler(packetWrapper -> {
/*     */                   Position position = (Position)packetWrapper.read(Type.POSITION);
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.x()));
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.y()));
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.z()));
/*     */                 });
/* 117 */             map((Type)Type.BYTE);
/*     */           }
/*     */         });
/*     */     
/* 121 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.MAP_BULK_CHUNK, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 124 */             handler(ChunkPacketTransformer::transformChunkBulk);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 129 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 132 */             map((Type)Type.INT);
/* 133 */             handler(packetWrapper -> {
/*     */                   Position position = (Position)packetWrapper.read(Type.POSITION);
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.x()));
/*     */                   packetWrapper.write((Type)Type.BYTE, Byte.valueOf((byte)position.y()));
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.z()));
/*     */                 });
/* 139 */             map((Type)Type.INT);
/* 140 */             map((Type)Type.BOOLEAN);
/*     */           }
/*     */         });
/*     */     
/* 144 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.SPAWN_PARTICLE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 147 */             handler(packetWrapper -> {
/*     */                   int particleId = ((Integer)packetWrapper.read((Type)Type.INT)).intValue();
/*     */                   Particle particle = Particle.find(particleId);
/*     */                   if (particle == null)
/*     */                     particle = Particle.CRIT; 
/*     */                   packetWrapper.write(Type.STRING, particle.name);
/*     */                   packetWrapper.read((Type)Type.BOOLEAN);
/*     */                 });
/* 155 */             map((Type)Type.FLOAT);
/* 156 */             map((Type)Type.FLOAT);
/* 157 */             map((Type)Type.FLOAT);
/* 158 */             map((Type)Type.FLOAT);
/* 159 */             map((Type)Type.FLOAT);
/* 160 */             map((Type)Type.FLOAT);
/* 161 */             map((Type)Type.FLOAT);
/* 162 */             map((Type)Type.INT);
/* 163 */             handler(packetWrapper -> {
/*     */                   String name = (String)packetWrapper.get(Type.STRING, 0);
/*     */                   
/*     */                   Particle particle = Particle.find(name);
/*     */                   if (particle == Particle.ICON_CRACK || particle == Particle.BLOCK_CRACK || particle == Particle.BLOCK_DUST) {
/*     */                     int id = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                     int data = (particle == Particle.ICON_CRACK) ? ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue() : 0;
/*     */                     if ((id >= 256 && id <= 422) || (id >= 2256 && id <= 2267)) {
/*     */                       particle = Particle.ICON_CRACK;
/*     */                     } else if ((id >= 0 && id <= 164) || (id >= 170 && id <= 175)) {
/*     */                       if (particle == Particle.ICON_CRACK) {
/*     */                         particle = Particle.BLOCK_CRACK;
/*     */                       }
/*     */                     } else {
/*     */                       packetWrapper.cancel();
/*     */                       return;
/*     */                     } 
/*     */                     name = particle.name + "_" + id + "_" + data;
/*     */                   } 
/*     */                   packetWrapper.set(Type.STRING, 0, name);
/*     */                 });
/*     */           }
/*     */         });
/* 186 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.UPDATE_SIGN, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 189 */             handler(packetWrapper -> {
/*     */                   Position position = (Position)packetWrapper.read(Type.POSITION);
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.x()));
/*     */                   packetWrapper.write((Type)Type.SHORT, Short.valueOf((short)position.y()));
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.z()));
/*     */                 });
/* 195 */             handler(packetWrapper -> {
/*     */                   for (int i = 0; i < 4; i++) {
/*     */                     String line = (String)packetWrapper.read(Type.STRING);
/*     */                     line = ChatUtil.jsonToLegacy(line);
/*     */                     line = ChatUtil.removeUnusedColor(line, '0');
/*     */                     if (line.length() > 15) {
/*     */                       line = ChatColorUtil.stripColor(line);
/*     */                       if (line.length() > 15)
/*     */                         line = line.substring(0, 15); 
/*     */                     } 
/*     */                     packetWrapper.write(Type.STRING, line);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 210 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.MAP_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 213 */             handler(packetWrapper -> {
/*     */                   packetWrapper.cancel();
/*     */                   
/*     */                   int id = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   byte scale = ((Byte)packetWrapper.read((Type)Type.BYTE)).byteValue();
/*     */                   
/*     */                   int count = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   
/*     */                   byte[] icons = new byte[count * 4];
/*     */                   
/*     */                   for (int i = 0; i < count; i++) {
/*     */                     int j = ((Byte)packetWrapper.read((Type)Type.BYTE)).byteValue();
/*     */                     
/*     */                     icons[i * 4] = (byte)(j >> 4 & 0xF);
/*     */                     
/*     */                     icons[i * 4 + 1] = ((Byte)packetWrapper.read((Type)Type.BYTE)).byteValue();
/*     */                     
/*     */                     icons[i * 4 + 2] = ((Byte)packetWrapper.read((Type)Type.BYTE)).byteValue();
/*     */                     icons[i * 4 + 3] = (byte)(j & 0xF);
/*     */                   } 
/*     */                   short columns = ((Short)packetWrapper.read((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                   if (columns > 0) {
/*     */                     short rows = ((Short)packetWrapper.read((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                     short x = ((Short)packetWrapper.read((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                     short z = ((Short)packetWrapper.read((Type)Type.UNSIGNED_BYTE)).shortValue();
/*     */                     byte[] data = (byte[])packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
/*     */                     for (int column = 0; column < columns; column++) {
/*     */                       byte[] columnData = new byte[rows + 3];
/*     */                       columnData[0] = 0;
/*     */                       columnData[1] = (byte)(x + column);
/*     */                       columnData[2] = (byte)z;
/*     */                       for (int j = 0; j < rows; j++) {
/*     */                         columnData[j + 3] = data[column + j * columns];
/*     */                       }
/*     */                       PacketWrapper columnUpdate = PacketWrapper.create(52, null, packetWrapper.user());
/*     */                       columnUpdate.write((Type)Type.VAR_INT, Integer.valueOf(id));
/*     */                       columnUpdate.write((Type)Type.SHORT, Short.valueOf((short)columnData.length));
/*     */                       columnUpdate.write((Type)new CustomByteType(Integer.valueOf(columnData.length)), columnData);
/*     */                       PacketUtil.sendPacket(columnUpdate, Protocol1_7_6_10TO1_8.class, true, true);
/*     */                     } 
/*     */                   } 
/*     */                   if (count > 0) {
/*     */                     byte[] iconData = new byte[count * 3 + 1];
/*     */                     iconData[0] = 1;
/*     */                     for (int j = 0; j < count; j++) {
/*     */                       iconData[j * 3 + 1] = (byte)(icons[j * 4] << 4 | icons[j * 4 + 3] & 0xF);
/*     */                       iconData[j * 3 + 2] = icons[j * 4 + 1];
/*     */                       iconData[j * 3 + 3] = icons[j * 4 + 2];
/*     */                     } 
/*     */                     PacketWrapper iconUpdate = PacketWrapper.create(52, null, packetWrapper.user());
/*     */                     iconUpdate.write((Type)Type.VAR_INT, Integer.valueOf(id));
/*     */                     iconUpdate.write((Type)Type.SHORT, Short.valueOf((short)iconData.length));
/*     */                     CustomByteType customByteType = new CustomByteType(Integer.valueOf(iconData.length));
/*     */                     iconUpdate.write((Type)customByteType, iconData);
/*     */                     PacketUtil.sendPacket(iconUpdate, Protocol1_7_6_10TO1_8.class, true, true);
/*     */                   } 
/*     */                   PacketWrapper scaleUpdate = PacketWrapper.create(52, null, packetWrapper.user());
/*     */                   scaleUpdate.write((Type)Type.VAR_INT, Integer.valueOf(id));
/*     */                   scaleUpdate.write((Type)Type.SHORT, Short.valueOf((short)2));
/*     */                   scaleUpdate.write((Type)new CustomByteType(Integer.valueOf(2)), new byte[] { 2, scale });
/*     */                   PacketUtil.sendPacket(scaleUpdate, Protocol1_7_6_10TO1_8.class, true, true);
/*     */                 });
/*     */           }
/*     */         });
/* 278 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.BLOCK_ENTITY_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 281 */             handler(packetWrapper -> {
/*     */                   Position position = (Position)packetWrapper.read(Type.POSITION);
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.x()));
/*     */                   packetWrapper.write((Type)Type.SHORT, Short.valueOf((short)position.y()));
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(position.z()));
/*     */                 });
/* 287 */             map((Type)Type.UNSIGNED_BYTE);
/* 288 */             map(Type.NBT, Types1_7_6_10.COMPRESSED_NBT);
/*     */           }
/*     */         });
/*     */     
/* 292 */     protocol.cancelClientbound((ClientboundPacketType)ClientboundPackets1_8.SERVER_DIFFICULTY);
/* 293 */     protocol.cancelClientbound((ClientboundPacketType)ClientboundPackets1_8.COMBAT_EVENT);
/*     */     
/* 295 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_8.WORLD_BORDER, null, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 298 */             handler(packetWrapper -> {
/*     */                   int action = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   WorldBorder worldBorder = (WorldBorder)packetWrapper.user().get(WorldBorder.class);
/*     */                   if (action == 0) {
/*     */                     worldBorder.setSize(((Double)packetWrapper.read((Type)Type.DOUBLE)).doubleValue());
/*     */                   } else if (action == 1) {
/*     */                     worldBorder.lerpSize(((Double)packetWrapper.read((Type)Type.DOUBLE)).doubleValue(), ((Double)packetWrapper.read((Type)Type.DOUBLE)).doubleValue(), ((Long)packetWrapper.read((Type)Type.VAR_LONG)).longValue());
/*     */                   } else if (action == 2) {
/*     */                     worldBorder.setCenter(((Double)packetWrapper.read((Type)Type.DOUBLE)).doubleValue(), ((Double)packetWrapper.read((Type)Type.DOUBLE)).doubleValue());
/*     */                   } else if (action == 3) {
/*     */                     worldBorder.init(((Double)packetWrapper.read((Type)Type.DOUBLE)).doubleValue(), ((Double)packetWrapper.read((Type)Type.DOUBLE)).doubleValue(), ((Double)packetWrapper.read((Type)Type.DOUBLE)).doubleValue(), ((Double)packetWrapper.read((Type)Type.DOUBLE)).doubleValue(), ((Long)packetWrapper.read((Type)Type.VAR_LONG)).longValue(), ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue(), ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue(), ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue());
/*     */                   } else if (action == 4) {
/*     */                     worldBorder.setWarningTime(((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue());
/*     */                   } else if (action == 5) {
/*     */                     worldBorder.setWarningBlocks(((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue());
/*     */                   } 
/*     */                   packetWrapper.cancel();
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\packets\WorldPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
/*     */ import com.viaversion.viaversion.api.minecraft.Environment;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
/*     */ import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.Chunk1_9_1_2Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.types.Chunk1_8Type;
/*     */ import de.gerrygames.viarewind.ViaRewind;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ReplacementRegistry1_8to1_9;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.sound.Effect;
/*     */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.sound.SoundRemapper;
/*     */ import de.gerrygames.viarewind.utils.PacketUtil;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldPackets
/*     */ {
/*     */   public static void register(Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8> protocol) {
/*  44 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.BLOCK_ENTITY_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  47 */             map(Type.POSITION);
/*  48 */             map((Type)Type.UNSIGNED_BYTE);
/*  49 */             map(Type.NBT);
/*  50 */             handler(packetWrapper -> {
/*     */                   CompoundTag tag = (CompoundTag)packetWrapper.get(Type.NBT, 0);
/*     */                   
/*     */                   if (tag != null && tag.contains("SpawnData")) {
/*     */                     CompoundTag spawnData = (CompoundTag)tag.get("SpawnData");
/*     */                     
/*     */                     if (spawnData.contains("id")) {
/*     */                       String entity = (String)spawnData.get("id").getValue();
/*     */                       tag.remove("SpawnData");
/*     */                       tag.put("entityId", (Tag)new StringTag(entity));
/*     */                     } 
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*  65 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.BLOCK_ACTION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  68 */             map(Type.POSITION);
/*  69 */             map((Type)Type.UNSIGNED_BYTE);
/*  70 */             map((Type)Type.UNSIGNED_BYTE);
/*  71 */             map((Type)Type.VAR_INT);
/*  72 */             handler(packetWrapper -> {
/*     */                   int block = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */                   
/*     */                   if (block >= 219 && block <= 234) {
/*     */                     packetWrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(block = 130));
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/*  82 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/*  85 */             map(Type.POSITION);
/*  86 */             map((Type)Type.VAR_INT);
/*  87 */             handler(packetWrapper -> {
/*     */                   int combined = ((Integer)packetWrapper.get((Type)Type.VAR_INT, 0)).intValue();
/*     */ 
/*     */                   
/*     */                   int replacedCombined = ReplacementRegistry1_8to1_9.replace(combined);
/*     */                   
/*     */                   packetWrapper.set((Type)Type.VAR_INT, 0, Integer.valueOf(replacedCombined));
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/*  98 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.MULTI_BLOCK_CHANGE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 101 */             map((Type)Type.INT);
/* 102 */             map((Type)Type.INT);
/* 103 */             map(Type.BLOCK_CHANGE_RECORD_ARRAY);
/* 104 */             handler(packetWrapper -> {
/*     */                   for (BlockChangeRecord record : (BlockChangeRecord[])packetWrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
/*     */                     int replacedCombined = ReplacementRegistry1_8to1_9.replace(record.getBlockId());
/*     */                     
/*     */                     record.setBlockId(replacedCombined);
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 114 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.NAMED_SOUND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 117 */             map(Type.STRING);
/* 118 */             handler(packetWrapper -> {
/*     */                   String name = (String)packetWrapper.get(Type.STRING, 0);
/*     */                   name = SoundRemapper.getOldName(name);
/*     */                   if (name == null) {
/*     */                     packetWrapper.cancel();
/*     */                   } else {
/*     */                     packetWrapper.set(Type.STRING, 0, name);
/*     */                   } 
/*     */                 });
/* 127 */             map((Type)Type.VAR_INT, (Type)Type.NOTHING);
/* 128 */             map((Type)Type.INT);
/* 129 */             map((Type)Type.INT);
/* 130 */             map((Type)Type.INT);
/* 131 */             map((Type)Type.FLOAT);
/* 132 */             map((Type)Type.UNSIGNED_BYTE);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 137 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.EXPLOSION, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 140 */             map((Type)Type.FLOAT);
/* 141 */             map((Type)Type.FLOAT);
/* 142 */             map((Type)Type.FLOAT);
/* 143 */             map((Type)Type.FLOAT);
/* 144 */             handler(packetWrapper -> {
/*     */                   int count = ((Integer)packetWrapper.read((Type)Type.INT)).intValue();
/*     */                   packetWrapper.write((Type)Type.INT, Integer.valueOf(count));
/*     */                   for (int i = 0; i < count; i++) {
/*     */                     packetWrapper.passthrough((Type)Type.UNSIGNED_BYTE);
/*     */                     packetWrapper.passthrough((Type)Type.UNSIGNED_BYTE);
/*     */                     packetWrapper.passthrough((Type)Type.UNSIGNED_BYTE);
/*     */                   } 
/*     */                 });
/* 153 */             map((Type)Type.FLOAT);
/* 154 */             map((Type)Type.FLOAT);
/* 155 */             map((Type)Type.FLOAT);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 160 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.UNLOAD_CHUNK, (ClientboundPacketType)ClientboundPackets1_8.CHUNK_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 163 */             handler(packetWrapper -> {
/*     */                   int chunkX = ((Integer)packetWrapper.read((Type)Type.INT)).intValue();
/*     */                   
/*     */                   int chunkZ = ((Integer)packetWrapper.read((Type)Type.INT)).intValue();
/*     */                   
/*     */                   ClientWorld world = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
/*     */                   packetWrapper.write((Type)new Chunk1_8Type(world), new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], null, new ArrayList()));
/*     */                 });
/*     */           }
/*     */         });
/* 173 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.CHUNK_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 176 */             handler(packetWrapper -> {
/*     */                   BaseChunk baseChunk;
/*     */ 
/*     */ 
/*     */                   
/*     */                   ClientWorld world = (ClientWorld)packetWrapper.user().get(ClientWorld.class);
/*     */ 
/*     */ 
/*     */                   
/*     */                   Chunk chunk = (Chunk)packetWrapper.read((Type)new Chunk1_9_1_2Type(world));
/*     */ 
/*     */ 
/*     */                   
/*     */                   for (ChunkSection section : chunk.getSections()) {
/*     */                     if (section != null) {
/*     */                       DataPalette palette = section.palette(PaletteType.BLOCKS);
/*     */ 
/*     */ 
/*     */                       
/*     */                       for (int i = 0; i < palette.size(); i++) {
/*     */                         int block = palette.idByIndex(i);
/*     */ 
/*     */ 
/*     */                         
/*     */                         int replacedBlock = ReplacementRegistry1_8to1_9.replace(block);
/*     */ 
/*     */                         
/*     */                         palette.setIdByIndex(i, replacedBlock);
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */ 
/*     */                   
/*     */                   if (chunk.isFullChunk() && chunk.getBitmask() == 0) {
/*     */                     boolean skylight = (world.getEnvironment() == Environment.NORMAL);
/*     */ 
/*     */                     
/*     */                     ChunkSection[] sections = new ChunkSection[16];
/*     */ 
/*     */                     
/*     */                     ChunkSectionImpl chunkSectionImpl = new ChunkSectionImpl(true);
/*     */ 
/*     */                     
/*     */                     sections[0] = (ChunkSection)chunkSectionImpl;
/*     */ 
/*     */                     
/*     */                     chunkSectionImpl.palette(PaletteType.BLOCKS).addId(0);
/*     */ 
/*     */                     
/*     */                     if (skylight) {
/*     */                       chunkSectionImpl.getLight().setSkyLight(new byte[2048]);
/*     */                     }
/*     */ 
/*     */                     
/*     */                     baseChunk = new BaseChunk(chunk.getX(), chunk.getZ(), true, false, 1, sections, chunk.getBiomeData(), chunk.getBlockEntities());
/*     */                   } 
/*     */ 
/*     */                   
/*     */                   packetWrapper.write((Type)new Chunk1_8Type(world), baseChunk);
/*     */ 
/*     */                   
/*     */                   UserConnection user = packetWrapper.user();
/*     */ 
/*     */                   
/*     */                   baseChunk.getBlockEntities().forEach(());
/*     */                 });
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 246 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.EFFECT, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 249 */             map((Type)Type.INT);
/* 250 */             map(Type.POSITION);
/* 251 */             map((Type)Type.INT);
/* 252 */             map((Type)Type.BOOLEAN);
/* 253 */             handler(packetWrapper -> {
/*     */                   int id = ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   id = Effect.getOldId(id);
/*     */                   
/*     */                   if (id == -1) {
/*     */                     packetWrapper.cancel();
/*     */                     return;
/*     */                   } 
/*     */                   packetWrapper.set((Type)Type.INT, 0, Integer.valueOf(id));
/*     */                   if (id == 2001) {
/*     */                     int replacedBlock = ReplacementRegistry1_8to1_9.replace(((Integer)packetWrapper.get((Type)Type.INT, 1)).intValue());
/*     */                     packetWrapper.set((Type)Type.INT, 1, Integer.valueOf(replacedBlock));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 270 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SPAWN_PARTICLE, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 273 */             map((Type)Type.INT);
/* 274 */             handler(packetWrapper -> {
/*     */                   int type = ((Integer)packetWrapper.get((Type)Type.INT, 0)).intValue();
/*     */                   
/*     */                   if (type > 41 && !ViaRewind.getConfig().isReplaceParticles()) {
/*     */                     packetWrapper.cancel();
/*     */                     
/*     */                     return;
/*     */                   } 
/*     */                   if (type == 42) {
/*     */                     packetWrapper.set((Type)Type.INT, 0, Integer.valueOf(24));
/*     */                   } else if (type == 43) {
/*     */                     packetWrapper.set((Type)Type.INT, 0, Integer.valueOf(3));
/*     */                   } else if (type == 44) {
/*     */                     packetWrapper.set((Type)Type.INT, 0, Integer.valueOf(34));
/*     */                   } else if (type == 45) {
/*     */                     packetWrapper.set((Type)Type.INT, 0, Integer.valueOf(1));
/*     */                   } 
/*     */                 });
/*     */           }
/*     */         });
/* 294 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.MAP_DATA, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 297 */             map((Type)Type.VAR_INT);
/* 298 */             map((Type)Type.BYTE);
/* 299 */             map((Type)Type.BOOLEAN, (Type)Type.NOTHING);
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 309 */     protocol.registerClientbound((ClientboundPacketType)ClientboundPackets1_9.SOUND, (ClientboundPacketType)ClientboundPackets1_8.NAMED_SOUND, (PacketHandler)new PacketHandlers()
/*     */         {
/*     */           public void register() {
/* 312 */             handler(packetWrapper -> {
/*     */                   int soundId = ((Integer)packetWrapper.read((Type)Type.VAR_INT)).intValue();
/*     */                   String sound = SoundRemapper.oldNameFromId(soundId);
/*     */                   if (sound == null) {
/*     */                     packetWrapper.cancel();
/*     */                   } else {
/*     */                     packetWrapper.write(Type.STRING, sound);
/*     */                   } 
/*     */                 });
/* 321 */             handler(packetWrapper -> packetWrapper.read((Type)Type.VAR_INT));
/* 322 */             map((Type)Type.INT);
/* 323 */             map((Type)Type.INT);
/* 324 */             map((Type)Type.INT);
/* 325 */             map((Type)Type.FLOAT);
/* 326 */             map((Type)Type.UNSIGNED_BYTE);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\packets\WorldPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
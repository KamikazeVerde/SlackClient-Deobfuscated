/*     */ package net.minecraft.world.chunk.storage;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.MinecraftException;
/*     */ import net.minecraft.world.NextTickListEntry;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.NibbleArray;
/*     */ import net.minecraft.world.storage.IThreadedFileIO;
/*     */ import net.minecraft.world.storage.ThreadedFileIOBase;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class AnvilChunkLoader implements IChunkLoader, IThreadedFileIO {
/*  34 */   private static final Logger logger = LogManager.getLogger();
/*  35 */   private Map<ChunkCoordIntPair, NBTTagCompound> chunksToRemove = new ConcurrentHashMap<>();
/*  36 */   private Set<ChunkCoordIntPair> pendingAnvilChunksCoordinates = Collections.newSetFromMap(new ConcurrentHashMap<>());
/*     */   
/*     */   private final File chunkSaveLocation;
/*     */   
/*     */   private boolean field_183014_e = false;
/*     */ 
/*     */   
/*     */   public AnvilChunkLoader(File chunkSaveLocationIn) {
/*  44 */     this.chunkSaveLocation = chunkSaveLocationIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk loadChunk(World worldIn, int x, int z) throws IOException {
/*  52 */     ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(x, z);
/*  53 */     NBTTagCompound nbttagcompound = this.chunksToRemove.get(chunkcoordintpair);
/*     */     
/*  55 */     if (nbttagcompound == null) {
/*     */       
/*  57 */       DataInputStream datainputstream = RegionFileCache.getChunkInputStream(this.chunkSaveLocation, x, z);
/*     */       
/*  59 */       if (datainputstream == null)
/*     */       {
/*  61 */         return null;
/*     */       }
/*     */       
/*  64 */       nbttagcompound = CompressedStreamTools.read(datainputstream);
/*     */     } 
/*     */     
/*  67 */     return checkedReadChunkFromNBT(worldIn, x, z, nbttagcompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Chunk checkedReadChunkFromNBT(World worldIn, int x, int z, NBTTagCompound p_75822_4_) {
/*  78 */     if (!p_75822_4_.hasKey("Level", 10)) {
/*     */       
/*  80 */       logger.error("Chunk file at " + x + "," + z + " is missing level data, skipping");
/*  81 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*  85 */     NBTTagCompound nbttagcompound = p_75822_4_.getCompoundTag("Level");
/*     */     
/*  87 */     if (!nbttagcompound.hasKey("Sections", 9)) {
/*     */       
/*  89 */       logger.error("Chunk file at " + x + "," + z + " is missing block data, skipping");
/*  90 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*  94 */     Chunk chunk = readChunkFromNBT(worldIn, nbttagcompound);
/*     */     
/*  96 */     if (!chunk.isAtLocation(x, z)) {
/*     */       
/*  98 */       logger.error("Chunk file at " + x + "," + z + " is in the wrong location; relocating. (Expected " + x + ", " + z + ", got " + chunk.xPosition + ", " + chunk.zPosition + ")");
/*  99 */       nbttagcompound.setInteger("xPos", x);
/* 100 */       nbttagcompound.setInteger("zPos", z);
/* 101 */       chunk = readChunkFromNBT(worldIn, nbttagcompound);
/*     */     } 
/*     */     
/* 104 */     return chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveChunk(World worldIn, Chunk chunkIn) throws MinecraftException, IOException {
/* 111 */     worldIn.checkSessionLock();
/*     */ 
/*     */     
/*     */     try {
/* 115 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 116 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 117 */       nbttagcompound.setTag("Level", (NBTBase)nbttagcompound1);
/* 118 */       writeChunkToNBT(chunkIn, worldIn, nbttagcompound1);
/* 119 */       addChunkToPending(chunkIn.getChunkCoordIntPair(), nbttagcompound);
/*     */     }
/* 121 */     catch (Exception exception) {
/*     */       
/* 123 */       logger.error("Failed to save chunk", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addChunkToPending(ChunkCoordIntPair p_75824_1_, NBTTagCompound p_75824_2_) {
/* 129 */     if (!this.pendingAnvilChunksCoordinates.contains(p_75824_1_))
/*     */     {
/* 131 */       this.chunksToRemove.put(p_75824_1_, p_75824_2_);
/*     */     }
/*     */     
/* 134 */     ThreadedFileIOBase.getThreadedIOInstance().queueIO(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean writeNextIO() {
/*     */     boolean lvt_3_1_;
/* 142 */     if (this.chunksToRemove.isEmpty()) {
/*     */       
/* 144 */       if (this.field_183014_e)
/*     */       {
/* 146 */         logger.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", new Object[] { this.chunkSaveLocation.getName() });
/*     */       }
/*     */       
/* 149 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 153 */     ChunkCoordIntPair chunkcoordintpair = this.chunksToRemove.keySet().iterator().next();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 158 */       this.pendingAnvilChunksCoordinates.add(chunkcoordintpair);
/* 159 */       NBTTagCompound nbttagcompound = this.chunksToRemove.remove(chunkcoordintpair);
/*     */       
/* 161 */       if (nbttagcompound != null) {
/*     */         
/*     */         try {
/*     */           
/* 165 */           func_183013_b(chunkcoordintpair, nbttagcompound);
/*     */         }
/* 167 */         catch (Exception exception) {
/*     */           
/* 169 */           logger.error("Failed to save chunk", exception);
/*     */         } 
/*     */       }
/*     */       
/* 173 */       lvt_3_1_ = true;
/*     */     }
/*     */     finally {
/*     */       
/* 177 */       this.pendingAnvilChunksCoordinates.remove(chunkcoordintpair);
/*     */     } 
/*     */     
/* 180 */     return lvt_3_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_183013_b(ChunkCoordIntPair p_183013_1_, NBTTagCompound p_183013_2_) throws IOException {
/* 186 */     DataOutputStream dataoutputstream = RegionFileCache.getChunkOutputStream(this.chunkSaveLocation, p_183013_1_.chunkXPos, p_183013_1_.chunkZPos);
/* 187 */     CompressedStreamTools.write(p_183013_2_, dataoutputstream);
/* 188 */     dataoutputstream.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraChunkData(World worldIn, Chunk chunkIn) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void chunkTick() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraData() {
/*     */     try {
/* 214 */       this.field_183014_e = true;
/*     */ 
/*     */       
/*     */       while (true) {
/* 218 */         if (writeNextIO());
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 226 */       this.field_183014_e = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeChunkToNBT(Chunk chunkIn, World worldIn, NBTTagCompound p_75820_3_) {
/* 236 */     p_75820_3_.setByte("V", (byte)1);
/* 237 */     p_75820_3_.setInteger("xPos", chunkIn.xPosition);
/* 238 */     p_75820_3_.setInteger("zPos", chunkIn.zPosition);
/* 239 */     p_75820_3_.setLong("LastUpdate", worldIn.getTotalWorldTime());
/* 240 */     p_75820_3_.setIntArray("HeightMap", chunkIn.getHeightMap());
/* 241 */     p_75820_3_.setBoolean("TerrainPopulated", chunkIn.isTerrainPopulated());
/* 242 */     p_75820_3_.setBoolean("LightPopulated", chunkIn.isLightPopulated());
/* 243 */     p_75820_3_.setLong("InhabitedTime", chunkIn.getInhabitedTime());
/* 244 */     ExtendedBlockStorage[] aextendedblockstorage = chunkIn.getBlockStorageArray();
/* 245 */     NBTTagList nbttaglist = new NBTTagList();
/* 246 */     boolean flag = !worldIn.provider.getHasNoSky();
/*     */     
/* 248 */     for (ExtendedBlockStorage extendedblockstorage : aextendedblockstorage) {
/*     */       
/* 250 */       if (extendedblockstorage != null) {
/*     */         
/* 252 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 253 */         nbttagcompound.setByte("Y", (byte)(extendedblockstorage.getYLocation() >> 4 & 0xFF));
/* 254 */         byte[] abyte = new byte[(extendedblockstorage.getData()).length];
/* 255 */         NibbleArray nibblearray = new NibbleArray();
/* 256 */         NibbleArray nibblearray1 = null;
/*     */         
/* 258 */         for (int i = 0; i < (extendedblockstorage.getData()).length; i++) {
/*     */           
/* 260 */           char c0 = extendedblockstorage.getData()[i];
/* 261 */           int j = i & 0xF;
/* 262 */           int k = i >> 8 & 0xF;
/* 263 */           int l = i >> 4 & 0xF;
/*     */           
/* 265 */           if (c0 >> 12 != 0) {
/*     */             
/* 267 */             if (nibblearray1 == null)
/*     */             {
/* 269 */               nibblearray1 = new NibbleArray();
/*     */             }
/*     */             
/* 272 */             nibblearray1.set(j, k, l, c0 >> 12);
/*     */           } 
/*     */           
/* 275 */           abyte[i] = (byte)(c0 >> 4 & 0xFF);
/* 276 */           nibblearray.set(j, k, l, c0 & 0xF);
/*     */         } 
/*     */         
/* 279 */         nbttagcompound.setByteArray("Blocks", abyte);
/* 280 */         nbttagcompound.setByteArray("Data", nibblearray.getData());
/*     */         
/* 282 */         if (nibblearray1 != null)
/*     */         {
/* 284 */           nbttagcompound.setByteArray("Add", nibblearray1.getData());
/*     */         }
/*     */         
/* 287 */         nbttagcompound.setByteArray("BlockLight", extendedblockstorage.getBlocklightArray().getData());
/*     */         
/* 289 */         if (flag) {
/*     */           
/* 291 */           nbttagcompound.setByteArray("SkyLight", extendedblockstorage.getSkylightArray().getData());
/*     */         }
/*     */         else {
/*     */           
/* 295 */           nbttagcompound.setByteArray("SkyLight", new byte[(extendedblockstorage.getBlocklightArray().getData()).length]);
/*     */         } 
/*     */         
/* 298 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 302 */     p_75820_3_.setTag("Sections", (NBTBase)nbttaglist);
/* 303 */     p_75820_3_.setByteArray("Biomes", chunkIn.getBiomeArray());
/* 304 */     chunkIn.setHasEntities(false);
/* 305 */     NBTTagList nbttaglist1 = new NBTTagList();
/*     */     
/* 307 */     for (int i1 = 0; i1 < (chunkIn.getEntityLists()).length; i1++) {
/*     */       
/* 309 */       for (Entity entity : chunkIn.getEntityLists()[i1]) {
/*     */         
/* 311 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/* 313 */         if (entity.writeToNBTOptional(nbttagcompound1)) {
/*     */           
/* 315 */           chunkIn.setHasEntities(true);
/* 316 */           nbttaglist1.appendTag((NBTBase)nbttagcompound1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 321 */     p_75820_3_.setTag("Entities", (NBTBase)nbttaglist1);
/* 322 */     NBTTagList nbttaglist2 = new NBTTagList();
/*     */     
/* 324 */     for (TileEntity tileentity : chunkIn.getTileEntityMap().values()) {
/*     */       
/* 326 */       NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/* 327 */       tileentity.writeToNBT(nbttagcompound2);
/* 328 */       nbttaglist2.appendTag((NBTBase)nbttagcompound2);
/*     */     } 
/*     */     
/* 331 */     p_75820_3_.setTag("TileEntities", (NBTBase)nbttaglist2);
/* 332 */     List<NextTickListEntry> list = worldIn.getPendingBlockUpdates(chunkIn, false);
/*     */     
/* 334 */     if (list != null) {
/*     */       
/* 336 */       long j1 = worldIn.getTotalWorldTime();
/* 337 */       NBTTagList nbttaglist3 = new NBTTagList();
/*     */       
/* 339 */       for (NextTickListEntry nextticklistentry : list) {
/*     */         
/* 341 */         NBTTagCompound nbttagcompound3 = new NBTTagCompound();
/* 342 */         ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(nextticklistentry.getBlock());
/* 343 */         nbttagcompound3.setString("i", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 344 */         nbttagcompound3.setInteger("x", nextticklistentry.position.getX());
/* 345 */         nbttagcompound3.setInteger("y", nextticklistentry.position.getY());
/* 346 */         nbttagcompound3.setInteger("z", nextticklistentry.position.getZ());
/* 347 */         nbttagcompound3.setInteger("t", (int)(nextticklistentry.scheduledTime - j1));
/* 348 */         nbttagcompound3.setInteger("p", nextticklistentry.priority);
/* 349 */         nbttaglist3.appendTag((NBTBase)nbttagcompound3);
/*     */       } 
/*     */       
/* 352 */       p_75820_3_.setTag("TileTicks", (NBTBase)nbttaglist3);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Chunk readChunkFromNBT(World worldIn, NBTTagCompound p_75823_2_) {
/* 362 */     int i = p_75823_2_.getInteger("xPos");
/* 363 */     int j = p_75823_2_.getInteger("zPos");
/* 364 */     Chunk chunk = new Chunk(worldIn, i, j);
/* 365 */     chunk.setHeightMap(p_75823_2_.getIntArray("HeightMap"));
/* 366 */     chunk.setTerrainPopulated(p_75823_2_.getBoolean("TerrainPopulated"));
/* 367 */     chunk.setLightPopulated(p_75823_2_.getBoolean("LightPopulated"));
/* 368 */     chunk.setInhabitedTime(p_75823_2_.getLong("InhabitedTime"));
/* 369 */     NBTTagList nbttaglist = p_75823_2_.getTagList("Sections", 10);
/* 370 */     int k = 16;
/* 371 */     ExtendedBlockStorage[] aextendedblockstorage = new ExtendedBlockStorage[k];
/* 372 */     boolean flag = !worldIn.provider.getHasNoSky();
/*     */     
/* 374 */     for (int l = 0; l < nbttaglist.tagCount(); l++) {
/*     */       
/* 376 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(l);
/* 377 */       int i1 = nbttagcompound.getByte("Y");
/* 378 */       ExtendedBlockStorage extendedblockstorage = new ExtendedBlockStorage(i1 << 4, flag);
/* 379 */       byte[] abyte = nbttagcompound.getByteArray("Blocks");
/* 380 */       NibbleArray nibblearray = new NibbleArray(nbttagcompound.getByteArray("Data"));
/* 381 */       NibbleArray nibblearray1 = nbttagcompound.hasKey("Add", 7) ? new NibbleArray(nbttagcompound.getByteArray("Add")) : null;
/* 382 */       char[] achar = new char[abyte.length];
/*     */       
/* 384 */       for (int j1 = 0; j1 < achar.length; j1++) {
/*     */         
/* 386 */         int k1 = j1 & 0xF;
/* 387 */         int l1 = j1 >> 8 & 0xF;
/* 388 */         int i2 = j1 >> 4 & 0xF;
/* 389 */         int j2 = (nibblearray1 != null) ? nibblearray1.get(k1, l1, i2) : 0;
/* 390 */         achar[j1] = (char)(j2 << 12 | (abyte[j1] & 0xFF) << 4 | nibblearray.get(k1, l1, i2));
/*     */       } 
/*     */       
/* 393 */       extendedblockstorage.setData(achar);
/* 394 */       extendedblockstorage.setBlocklightArray(new NibbleArray(nbttagcompound.getByteArray("BlockLight")));
/*     */       
/* 396 */       if (flag)
/*     */       {
/* 398 */         extendedblockstorage.setSkylightArray(new NibbleArray(nbttagcompound.getByteArray("SkyLight")));
/*     */       }
/*     */       
/* 401 */       extendedblockstorage.removeInvalidBlocks();
/* 402 */       aextendedblockstorage[i1] = extendedblockstorage;
/*     */     } 
/*     */     
/* 405 */     chunk.setStorageArrays(aextendedblockstorage);
/*     */     
/* 407 */     if (p_75823_2_.hasKey("Biomes", 7))
/*     */     {
/* 409 */       chunk.setBiomeArray(p_75823_2_.getByteArray("Biomes"));
/*     */     }
/*     */     
/* 412 */     NBTTagList nbttaglist1 = p_75823_2_.getTagList("Entities", 10);
/*     */     
/* 414 */     if (nbttaglist1 != null)
/*     */     {
/* 416 */       for (int k2 = 0; k2 < nbttaglist1.tagCount(); k2++) {
/*     */         
/* 418 */         NBTTagCompound nbttagcompound1 = nbttaglist1.getCompoundTagAt(k2);
/* 419 */         Entity entity = EntityList.createEntityFromNBT(nbttagcompound1, worldIn);
/* 420 */         chunk.setHasEntities(true);
/*     */         
/* 422 */         if (entity != null) {
/*     */           
/* 424 */           chunk.addEntity(entity);
/* 425 */           Entity entity1 = entity;
/*     */           
/* 427 */           for (NBTTagCompound nbttagcompound4 = nbttagcompound1; nbttagcompound4.hasKey("Riding", 10); nbttagcompound4 = nbttagcompound4.getCompoundTag("Riding")) {
/*     */             
/* 429 */             Entity entity2 = EntityList.createEntityFromNBT(nbttagcompound4.getCompoundTag("Riding"), worldIn);
/*     */             
/* 431 */             if (entity2 != null) {
/*     */               
/* 433 */               chunk.addEntity(entity2);
/* 434 */               entity1.mountEntity(entity2);
/*     */             } 
/*     */             
/* 437 */             entity1 = entity2;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 443 */     NBTTagList nbttaglist2 = p_75823_2_.getTagList("TileEntities", 10);
/*     */     
/* 445 */     if (nbttaglist2 != null)
/*     */     {
/* 447 */       for (int l2 = 0; l2 < nbttaglist2.tagCount(); l2++) {
/*     */         
/* 449 */         NBTTagCompound nbttagcompound2 = nbttaglist2.getCompoundTagAt(l2);
/* 450 */         TileEntity tileentity = TileEntity.createAndLoadEntity(nbttagcompound2);
/*     */         
/* 452 */         if (tileentity != null)
/*     */         {
/* 454 */           chunk.addTileEntity(tileentity);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 459 */     if (p_75823_2_.hasKey("TileTicks", 9)) {
/*     */       
/* 461 */       NBTTagList nbttaglist3 = p_75823_2_.getTagList("TileTicks", 10);
/*     */       
/* 463 */       if (nbttaglist3 != null)
/*     */       {
/* 465 */         for (int i3 = 0; i3 < nbttaglist3.tagCount(); i3++) {
/*     */           Block block;
/* 467 */           NBTTagCompound nbttagcompound3 = nbttaglist3.getCompoundTagAt(i3);
/*     */ 
/*     */           
/* 470 */           if (nbttagcompound3.hasKey("i", 8)) {
/*     */             
/* 472 */             block = Block.getBlockFromName(nbttagcompound3.getString("i"));
/*     */           }
/*     */           else {
/*     */             
/* 476 */             block = Block.getBlockById(nbttagcompound3.getInteger("i"));
/*     */           } 
/*     */           
/* 479 */           worldIn.scheduleBlockUpdate(new BlockPos(nbttagcompound3.getInteger("x"), nbttagcompound3.getInteger("y"), nbttagcompound3.getInteger("z")), block, nbttagcompound3.getInteger("t"), nbttagcompound3.getInteger("p"));
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 484 */     return chunk;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\world\chunk\storage\AnvilChunkLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
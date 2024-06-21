/*      */ package net.minecraft.world.chunk;
/*      */ 
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Queues;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.ConcurrentLinkedQueue;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.ITileEntityProvider;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ClassInheritanceMultiMap;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.world.ChunkCoordIntPair;
/*      */ import net.minecraft.world.EnumSkyBlock;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldType;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.minecraft.world.biome.WorldChunkManager;
/*      */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*      */ import net.minecraft.world.gen.ChunkProviderDebug;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public class Chunk {
/*   40 */   private static final Logger logger = LogManager.getLogger();
/*      */ 
/*      */   
/*      */   private final ExtendedBlockStorage[] storageArrays;
/*      */ 
/*      */   
/*      */   private final byte[] blockBiomeArray;
/*      */ 
/*      */   
/*      */   private final int[] precipitationHeightMap;
/*      */ 
/*      */   
/*      */   private final boolean[] updateSkylightColumns;
/*      */ 
/*      */   
/*      */   private boolean isChunkLoaded;
/*      */ 
/*      */   
/*      */   private final World worldObj;
/*      */ 
/*      */   
/*      */   private final int[] heightMap;
/*      */ 
/*      */   
/*      */   public final int xPosition;
/*      */ 
/*      */   
/*      */   public final int zPosition;
/*      */ 
/*      */   
/*      */   private boolean isGapLightingUpdated;
/*      */ 
/*      */   
/*      */   private final Map<BlockPos, TileEntity> chunkTileEntityMap;
/*      */ 
/*      */   
/*      */   private final ClassInheritanceMultiMap<Entity>[] entityLists;
/*      */ 
/*      */   
/*      */   private boolean isTerrainPopulated;
/*      */ 
/*      */   
/*      */   private boolean isLightPopulated;
/*      */ 
/*      */   
/*      */   private boolean field_150815_m;
/*      */ 
/*      */   
/*      */   private boolean isModified;
/*      */ 
/*      */   
/*      */   private boolean hasEntities;
/*      */ 
/*      */   
/*      */   private long lastSaveTime;
/*      */ 
/*      */   
/*      */   private int heightMapMinimum;
/*      */ 
/*      */   
/*      */   private long inhabitedTime;
/*      */ 
/*      */   
/*      */   private int queuedLightChecks;
/*      */   
/*      */   private ConcurrentLinkedQueue<BlockPos> tileEntityPosQueue;
/*      */ 
/*      */   
/*      */   public Chunk(World worldIn, int x, int z) {
/*  109 */     this.storageArrays = new ExtendedBlockStorage[16];
/*  110 */     this.blockBiomeArray = new byte[256];
/*  111 */     this.precipitationHeightMap = new int[256];
/*  112 */     this.updateSkylightColumns = new boolean[256];
/*  113 */     this.chunkTileEntityMap = Maps.newHashMap();
/*  114 */     this.queuedLightChecks = 4096;
/*  115 */     this.tileEntityPosQueue = Queues.newConcurrentLinkedQueue();
/*  116 */     this.entityLists = (ClassInheritanceMultiMap<Entity>[])new ClassInheritanceMultiMap[16];
/*  117 */     this.worldObj = worldIn;
/*  118 */     this.xPosition = x;
/*  119 */     this.zPosition = z;
/*  120 */     this.heightMap = new int[256];
/*      */     
/*  122 */     for (int i = 0; i < this.entityLists.length; i++)
/*      */     {
/*  124 */       this.entityLists[i] = new ClassInheritanceMultiMap(Entity.class);
/*      */     }
/*      */     
/*  127 */     Arrays.fill(this.precipitationHeightMap, -999);
/*  128 */     Arrays.fill(this.blockBiomeArray, (byte)-1);
/*      */   }
/*      */ 
/*      */   
/*      */   public Chunk(World worldIn, ChunkPrimer primer, int x, int z) {
/*  133 */     this(worldIn, x, z);
/*  134 */     int i = 256;
/*  135 */     boolean flag = !worldIn.provider.getHasNoSky();
/*      */     
/*  137 */     for (int j = 0; j < 16; j++) {
/*      */       
/*  139 */       for (int k = 0; k < 16; k++) {
/*      */         
/*  141 */         for (int l = 0; l < i; l++) {
/*      */           
/*  143 */           int i1 = j * i * 16 | k * i | l;
/*  144 */           IBlockState iblockstate = primer.getBlockState(i1);
/*      */           
/*  146 */           if (iblockstate.getBlock().getMaterial() != Material.air) {
/*      */             
/*  148 */             int j1 = l >> 4;
/*      */             
/*  150 */             if (this.storageArrays[j1] == null)
/*      */             {
/*  152 */               this.storageArrays[j1] = new ExtendedBlockStorage(j1 << 4, flag);
/*      */             }
/*      */             
/*  155 */             this.storageArrays[j1].set(j, l & 0xF, k, iblockstate);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAtLocation(int x, int z) {
/*  167 */     return (x == this.xPosition && z == this.zPosition);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getHeight(BlockPos pos) {
/*  172 */     return getHeightValue(pos.getX() & 0xF, pos.getZ() & 0xF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHeightValue(int x, int z) {
/*  180 */     return this.heightMap[z << 4 | x];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTopFilledSegment() {
/*  188 */     for (int i = this.storageArrays.length - 1; i >= 0; i--) {
/*      */       
/*  190 */       if (this.storageArrays[i] != null)
/*      */       {
/*  192 */         return this.storageArrays[i].getYLocation();
/*      */       }
/*      */     } 
/*      */     
/*  196 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ExtendedBlockStorage[] getBlockStorageArray() {
/*  204 */     return this.storageArrays;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateHeightMap() {
/*  212 */     int i = getTopFilledSegment();
/*  213 */     this.heightMapMinimum = Integer.MAX_VALUE;
/*      */     
/*  215 */     for (int j = 0; j < 16; j++) {
/*      */       
/*  217 */       for (int k = 0; k < 16; k++) {
/*      */         
/*  219 */         this.precipitationHeightMap[j + (k << 4)] = -999;
/*      */         
/*  221 */         for (int l = i + 16; l > 0; l--) {
/*      */           
/*  223 */           Block block = getBlock0(j, l - 1, k);
/*      */           
/*  225 */           if (block.getLightOpacity() != 0) {
/*      */             
/*  227 */             this.heightMap[k << 4 | j] = l;
/*      */             
/*  229 */             if (l < this.heightMapMinimum)
/*      */             {
/*  231 */               this.heightMapMinimum = l;
/*      */             }
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  240 */     this.isModified = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void generateSkylightMap() {
/*  248 */     int i = getTopFilledSegment();
/*  249 */     this.heightMapMinimum = Integer.MAX_VALUE;
/*      */     
/*  251 */     for (int j = 0; j < 16; j++) {
/*      */       
/*  253 */       for (int k = 0; k < 16; k++) {
/*      */         
/*  255 */         this.precipitationHeightMap[j + (k << 4)] = -999;
/*      */         
/*  257 */         for (int l = i + 16; l > 0; l--) {
/*      */           
/*  259 */           if (getBlockLightOpacity(j, l - 1, k) != 0) {
/*      */             
/*  261 */             this.heightMap[k << 4 | j] = l;
/*      */             
/*  263 */             if (l < this.heightMapMinimum)
/*      */             {
/*  265 */               this.heightMapMinimum = l;
/*      */             }
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */         
/*  272 */         if (!this.worldObj.provider.getHasNoSky()) {
/*      */           
/*  274 */           int k1 = 15;
/*  275 */           int i1 = i + 16 - 1;
/*      */ 
/*      */           
/*      */           do {
/*  279 */             int j1 = getBlockLightOpacity(j, i1, k);
/*      */             
/*  281 */             if (j1 == 0 && k1 != 15)
/*      */             {
/*  283 */               j1 = 1;
/*      */             }
/*      */             
/*  286 */             k1 -= j1;
/*      */             
/*  288 */             if (k1 <= 0)
/*      */               continue; 
/*  290 */             ExtendedBlockStorage extendedblockstorage = this.storageArrays[i1 >> 4];
/*      */             
/*  292 */             if (extendedblockstorage == null)
/*      */               continue; 
/*  294 */             extendedblockstorage.setExtSkylightValue(j, i1 & 0xF, k, k1);
/*  295 */             this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + j, i1, (this.zPosition << 4) + k));
/*      */ 
/*      */ 
/*      */             
/*  299 */             --i1;
/*      */           }
/*  301 */           while (i1 > 0 && k1 > 0);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  310 */     this.isModified = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void propagateSkylightOcclusion(int x, int z) {
/*  318 */     this.updateSkylightColumns[x + z * 16] = true;
/*  319 */     this.isGapLightingUpdated = true;
/*      */   }
/*      */ 
/*      */   
/*      */   private void recheckGaps(boolean p_150803_1_) {
/*  324 */     this.worldObj.theProfiler.startSection("recheckGaps");
/*      */     
/*  326 */     if (this.worldObj.isAreaLoaded(new BlockPos(this.xPosition * 16 + 8, 0, this.zPosition * 16 + 8), 16)) {
/*      */       
/*  328 */       for (int i = 0; i < 16; i++) {
/*      */         
/*  330 */         for (int j = 0; j < 16; j++) {
/*      */           
/*  332 */           if (this.updateSkylightColumns[i + j * 16]) {
/*      */             
/*  334 */             this.updateSkylightColumns[i + j * 16] = false;
/*  335 */             int k = getHeightValue(i, j);
/*  336 */             int l = this.xPosition * 16 + i;
/*  337 */             int i1 = this.zPosition * 16 + j;
/*  338 */             int j1 = Integer.MAX_VALUE;
/*      */             
/*  340 */             for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
/*      */             {
/*  342 */               j1 = Math.min(j1, this.worldObj.getChunksLowestHorizon(l + enumfacing.getFrontOffsetX(), i1 + enumfacing.getFrontOffsetZ()));
/*      */             }
/*      */             
/*  345 */             checkSkylightNeighborHeight(l, i1, j1);
/*      */             
/*  347 */             for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL)
/*      */             {
/*  349 */               checkSkylightNeighborHeight(l + enumfacing1.getFrontOffsetX(), i1 + enumfacing1.getFrontOffsetZ(), k);
/*      */             }
/*      */             
/*  352 */             if (p_150803_1_) {
/*      */               
/*  354 */               this.worldObj.theProfiler.endSection();
/*      */               
/*      */               return;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  361 */       this.isGapLightingUpdated = false;
/*      */     } 
/*      */     
/*  364 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkSkylightNeighborHeight(int x, int z, int maxValue) {
/*  372 */     int i = this.worldObj.getHeight(new BlockPos(x, 0, z)).getY();
/*      */     
/*  374 */     if (i > maxValue) {
/*      */       
/*  376 */       updateSkylightNeighborHeight(x, z, maxValue, i + 1);
/*      */     }
/*  378 */     else if (i < maxValue) {
/*      */       
/*  380 */       updateSkylightNeighborHeight(x, z, i, maxValue + 1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateSkylightNeighborHeight(int x, int z, int startY, int endY) {
/*  386 */     if (endY > startY && this.worldObj.isAreaLoaded(new BlockPos(x, 0, z), 16)) {
/*      */       
/*  388 */       for (int i = startY; i < endY; i++)
/*      */       {
/*  390 */         this.worldObj.checkLightFor(EnumSkyBlock.SKY, new BlockPos(x, i, z));
/*      */       }
/*      */       
/*  393 */       this.isModified = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void relightBlock(int x, int y, int z) {
/*  402 */     int i = this.heightMap[z << 4 | x] & 0xFF;
/*  403 */     int j = i;
/*      */     
/*  405 */     if (y > i)
/*      */     {
/*  407 */       j = y;
/*      */     }
/*      */     
/*  410 */     while (j > 0 && getBlockLightOpacity(x, j - 1, z) == 0)
/*      */     {
/*  412 */       j--;
/*      */     }
/*      */     
/*  415 */     if (j != i) {
/*      */       
/*  417 */       this.worldObj.markBlocksDirtyVertical(x + this.xPosition * 16, z + this.zPosition * 16, j, i);
/*  418 */       this.heightMap[z << 4 | x] = j;
/*  419 */       int k = this.xPosition * 16 + x;
/*  420 */       int l = this.zPosition * 16 + z;
/*      */       
/*  422 */       if (!this.worldObj.provider.getHasNoSky()) {
/*      */         
/*  424 */         if (j < i) {
/*      */           
/*  426 */           for (int j1 = j; j1 < i; j1++) {
/*      */             
/*  428 */             ExtendedBlockStorage extendedblockstorage2 = this.storageArrays[j1 >> 4];
/*      */             
/*  430 */             if (extendedblockstorage2 != null)
/*      */             {
/*  432 */               extendedblockstorage2.setExtSkylightValue(x, j1 & 0xF, z, 15);
/*  433 */               this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + x, j1, (this.zPosition << 4) + z));
/*      */             }
/*      */           
/*      */           } 
/*      */         } else {
/*      */           
/*  439 */           for (int i1 = i; i1 < j; i1++) {
/*      */             
/*  441 */             ExtendedBlockStorage extendedblockstorage = this.storageArrays[i1 >> 4];
/*      */             
/*  443 */             if (extendedblockstorage != null) {
/*      */               
/*  445 */               extendedblockstorage.setExtSkylightValue(x, i1 & 0xF, z, 0);
/*  446 */               this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + x, i1, (this.zPosition << 4) + z));
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  451 */         int k1 = 15;
/*      */         
/*  453 */         while (j > 0 && k1 > 0) {
/*      */           
/*  455 */           j--;
/*  456 */           int i2 = getBlockLightOpacity(x, j, z);
/*      */           
/*  458 */           if (i2 == 0)
/*      */           {
/*  460 */             i2 = 1;
/*      */           }
/*      */           
/*  463 */           k1 -= i2;
/*      */           
/*  465 */           if (k1 < 0)
/*      */           {
/*  467 */             k1 = 0;
/*      */           }
/*      */           
/*  470 */           ExtendedBlockStorage extendedblockstorage1 = this.storageArrays[j >> 4];
/*      */           
/*  472 */           if (extendedblockstorage1 != null)
/*      */           {
/*  474 */             extendedblockstorage1.setExtSkylightValue(x, j & 0xF, z, k1);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  479 */       int l1 = this.heightMap[z << 4 | x];
/*  480 */       int j2 = i;
/*  481 */       int k2 = l1;
/*      */       
/*  483 */       if (l1 < i) {
/*      */         
/*  485 */         j2 = l1;
/*  486 */         k2 = i;
/*      */       } 
/*      */       
/*  489 */       if (l1 < this.heightMapMinimum)
/*      */       {
/*  491 */         this.heightMapMinimum = l1;
/*      */       }
/*      */       
/*  494 */       if (!this.worldObj.provider.getHasNoSky()) {
/*      */         
/*  496 */         for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
/*      */         {
/*  498 */           updateSkylightNeighborHeight(k + enumfacing.getFrontOffsetX(), l + enumfacing.getFrontOffsetZ(), j2, k2);
/*      */         }
/*      */         
/*  501 */         updateSkylightNeighborHeight(k, l, j2, k2);
/*      */       } 
/*      */       
/*  504 */       this.isModified = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBlockLightOpacity(BlockPos pos) {
/*  510 */     return getBlock(pos).getLightOpacity();
/*      */   }
/*      */ 
/*      */   
/*      */   private int getBlockLightOpacity(int x, int y, int z) {
/*  515 */     return getBlock0(x, y, z).getLightOpacity();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Block getBlock0(int x, int y, int z) {
/*  523 */     Block block = Blocks.air;
/*      */     
/*  525 */     if (y >= 0 && y >> 4 < this.storageArrays.length) {
/*      */       
/*  527 */       ExtendedBlockStorage extendedblockstorage = this.storageArrays[y >> 4];
/*      */       
/*  529 */       if (extendedblockstorage != null) {
/*      */         
/*      */         try {
/*      */           
/*  533 */           block = extendedblockstorage.getBlockByExtId(x, y & 0xF, z);
/*      */         }
/*  535 */         catch (Throwable throwable) {
/*      */           
/*  537 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Getting block");
/*  538 */           throw new ReportedException(crashreport);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  543 */     return block;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Block getBlock(final int x, final int y, final int z) {
/*      */     try {
/*  550 */       return getBlock0(x & 0xF, y, z & 0xF);
/*      */     }
/*  552 */     catch (ReportedException reportedexception) {
/*      */       
/*  554 */       CrashReportCategory crashreportcategory = reportedexception.getCrashReport().makeCategory("Block being got");
/*  555 */       crashreportcategory.addCrashSectionCallable("Location", new Callable<String>()
/*      */           {
/*      */             public String call() throws Exception
/*      */             {
/*  559 */               return CrashReportCategory.getCoordinateInfo(new BlockPos(Chunk.this.xPosition * 16 + x, y, Chunk.this.zPosition * 16 + z));
/*      */             }
/*      */           });
/*  562 */       throw reportedexception;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Block getBlock(final BlockPos pos) {
/*      */     try {
/*  570 */       return getBlock0(pos.getX() & 0xF, pos.getY(), pos.getZ() & 0xF);
/*      */     }
/*  572 */     catch (ReportedException reportedexception) {
/*      */       
/*  574 */       CrashReportCategory crashreportcategory = reportedexception.getCrashReport().makeCategory("Block being got");
/*  575 */       crashreportcategory.addCrashSectionCallable("Location", new Callable<String>()
/*      */           {
/*      */             public String call() throws Exception
/*      */             {
/*  579 */               return CrashReportCategory.getCoordinateInfo(pos);
/*      */             }
/*      */           });
/*  582 */       throw reportedexception;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public IBlockState getBlockState(final BlockPos pos) {
/*  588 */     if (this.worldObj.getWorldType() == WorldType.DEBUG_WORLD) {
/*      */       
/*  590 */       IBlockState iblockstate = null;
/*      */       
/*  592 */       if (pos.getY() == 60)
/*      */       {
/*  594 */         iblockstate = Blocks.barrier.getDefaultState();
/*      */       }
/*      */       
/*  597 */       if (pos.getY() == 70)
/*      */       {
/*  599 */         iblockstate = ChunkProviderDebug.func_177461_b(pos.getX(), pos.getZ());
/*      */       }
/*      */       
/*  602 */       return (iblockstate == null) ? Blocks.air.getDefaultState() : iblockstate;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  608 */       if (pos.getY() >= 0 && pos.getY() >> 4 < this.storageArrays.length) {
/*      */         
/*  610 */         ExtendedBlockStorage extendedblockstorage = this.storageArrays[pos.getY() >> 4];
/*      */         
/*  612 */         if (extendedblockstorage != null) {
/*      */           
/*  614 */           int j = pos.getX() & 0xF;
/*  615 */           int k = pos.getY() & 0xF;
/*  616 */           int i = pos.getZ() & 0xF;
/*  617 */           return extendedblockstorage.get(j, k, i);
/*      */         } 
/*      */       } 
/*      */       
/*  621 */       return Blocks.air.getDefaultState();
/*      */     }
/*  623 */     catch (Throwable throwable) {
/*      */       
/*  625 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Getting block state");
/*  626 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being got");
/*  627 */       crashreportcategory.addCrashSectionCallable("Location", new Callable<String>()
/*      */           {
/*      */             public String call() throws Exception
/*      */             {
/*  631 */               return CrashReportCategory.getCoordinateInfo(pos);
/*      */             }
/*      */           });
/*  634 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getBlockMetadata(int x, int y, int z) {
/*  644 */     if (y >> 4 >= this.storageArrays.length)
/*      */     {
/*  646 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  650 */     ExtendedBlockStorage extendedblockstorage = this.storageArrays[y >> 4];
/*  651 */     return (extendedblockstorage != null) ? extendedblockstorage.getExtBlockMetadata(x, y & 0xF, z) : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBlockMetadata(BlockPos pos) {
/*  657 */     return getBlockMetadata(pos.getX() & 0xF, pos.getY(), pos.getZ() & 0xF);
/*      */   }
/*      */ 
/*      */   
/*      */   public IBlockState setBlockState(BlockPos pos, IBlockState state) {
/*  662 */     int i = pos.getX() & 0xF;
/*  663 */     int j = pos.getY();
/*  664 */     int k = pos.getZ() & 0xF;
/*  665 */     int l = k << 4 | i;
/*      */     
/*  667 */     if (j >= this.precipitationHeightMap[l] - 1)
/*      */     {
/*  669 */       this.precipitationHeightMap[l] = -999;
/*      */     }
/*      */     
/*  672 */     int i1 = this.heightMap[l];
/*  673 */     IBlockState iblockstate = getBlockState(pos);
/*      */     
/*  675 */     if (iblockstate == state)
/*      */     {
/*  677 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  681 */     Block block = state.getBlock();
/*  682 */     Block block1 = iblockstate.getBlock();
/*  683 */     ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4];
/*  684 */     boolean flag = false;
/*      */     
/*  686 */     if (extendedblockstorage == null) {
/*      */       
/*  688 */       if (block == Blocks.air)
/*      */       {
/*  690 */         return null;
/*      */       }
/*      */       
/*  693 */       extendedblockstorage = this.storageArrays[j >> 4] = new ExtendedBlockStorage(j >> 4 << 4, !this.worldObj.provider.getHasNoSky());
/*  694 */       flag = (j >= i1);
/*      */     } 
/*      */     
/*  697 */     extendedblockstorage.set(i, j & 0xF, k, state);
/*      */     
/*  699 */     if (block1 != block)
/*      */     {
/*  701 */       if (!this.worldObj.isRemote) {
/*      */         
/*  703 */         block1.breakBlock(this.worldObj, pos, iblockstate);
/*      */       }
/*  705 */       else if (block1 instanceof ITileEntityProvider) {
/*      */         
/*  707 */         this.worldObj.removeTileEntity(pos);
/*      */       } 
/*      */     }
/*      */     
/*  711 */     if (extendedblockstorage.getBlockByExtId(i, j & 0xF, k) != block)
/*      */     {
/*  713 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  717 */     if (flag) {
/*      */       
/*  719 */       generateSkylightMap();
/*      */     }
/*      */     else {
/*      */       
/*  723 */       int j1 = block.getLightOpacity();
/*  724 */       int k1 = block1.getLightOpacity();
/*      */       
/*  726 */       if (j1 > 0) {
/*      */         
/*  728 */         if (j >= i1)
/*      */         {
/*  730 */           relightBlock(i, j + 1, k);
/*      */         }
/*      */       }
/*  733 */       else if (j == i1 - 1) {
/*      */         
/*  735 */         relightBlock(i, j, k);
/*      */       } 
/*      */       
/*  738 */       if (j1 != k1 && (j1 < k1 || getLightFor(EnumSkyBlock.SKY, pos) > 0 || getLightFor(EnumSkyBlock.BLOCK, pos) > 0))
/*      */       {
/*  740 */         propagateSkylightOcclusion(i, k);
/*      */       }
/*      */     } 
/*      */     
/*  744 */     if (block1 instanceof ITileEntityProvider) {
/*      */       
/*  746 */       TileEntity tileentity = getTileEntity(pos, EnumCreateEntityType.CHECK);
/*      */       
/*  748 */       if (tileentity != null)
/*      */       {
/*  750 */         tileentity.updateContainingBlockInfo();
/*      */       }
/*      */     } 
/*      */     
/*  754 */     if (!this.worldObj.isRemote && block1 != block)
/*      */     {
/*  756 */       block.onBlockAdded(this.worldObj, pos, state);
/*      */     }
/*      */     
/*  759 */     if (block instanceof ITileEntityProvider) {
/*      */       
/*  761 */       TileEntity tileentity1 = getTileEntity(pos, EnumCreateEntityType.CHECK);
/*      */       
/*  763 */       if (tileentity1 == null) {
/*      */         
/*  765 */         tileentity1 = ((ITileEntityProvider)block).createNewTileEntity(this.worldObj, block.getMetaFromState(state));
/*  766 */         this.worldObj.setTileEntity(pos, tileentity1);
/*      */       } 
/*      */       
/*  769 */       if (tileentity1 != null)
/*      */       {
/*  771 */         tileentity1.updateContainingBlockInfo();
/*      */       }
/*      */     } 
/*      */     
/*  775 */     this.isModified = true;
/*  776 */     return iblockstate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLightFor(EnumSkyBlock p_177413_1_, BlockPos pos) {
/*  783 */     int i = pos.getX() & 0xF;
/*  784 */     int j = pos.getY();
/*  785 */     int k = pos.getZ() & 0xF;
/*  786 */     ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4];
/*  787 */     return (extendedblockstorage == null) ? (canSeeSky(pos) ? p_177413_1_.defaultLightValue : 0) : ((p_177413_1_ == EnumSkyBlock.SKY) ? (this.worldObj.provider.getHasNoSky() ? 0 : extendedblockstorage.getExtSkylightValue(i, j & 0xF, k)) : ((p_177413_1_ == EnumSkyBlock.BLOCK) ? extendedblockstorage.getExtBlocklightValue(i, j & 0xF, k) : p_177413_1_.defaultLightValue));
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLightFor(EnumSkyBlock p_177431_1_, BlockPos pos, int value) {
/*  792 */     int i = pos.getX() & 0xF;
/*  793 */     int j = pos.getY();
/*  794 */     int k = pos.getZ() & 0xF;
/*  795 */     ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4];
/*      */     
/*  797 */     if (extendedblockstorage == null) {
/*      */       
/*  799 */       extendedblockstorage = this.storageArrays[j >> 4] = new ExtendedBlockStorage(j >> 4 << 4, !this.worldObj.provider.getHasNoSky());
/*  800 */       generateSkylightMap();
/*      */     } 
/*      */     
/*  803 */     this.isModified = true;
/*      */     
/*  805 */     if (p_177431_1_ == EnumSkyBlock.SKY) {
/*      */       
/*  807 */       if (!this.worldObj.provider.getHasNoSky())
/*      */       {
/*  809 */         extendedblockstorage.setExtSkylightValue(i, j & 0xF, k, value);
/*      */       }
/*      */     }
/*  812 */     else if (p_177431_1_ == EnumSkyBlock.BLOCK) {
/*      */       
/*  814 */       extendedblockstorage.setExtBlocklightValue(i, j & 0xF, k, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLightSubtracted(BlockPos pos, int amount) {
/*  820 */     int i = pos.getX() & 0xF;
/*  821 */     int j = pos.getY();
/*  822 */     int k = pos.getZ() & 0xF;
/*  823 */     ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4];
/*      */     
/*  825 */     if (extendedblockstorage == null)
/*      */     {
/*  827 */       return (!this.worldObj.provider.getHasNoSky() && amount < EnumSkyBlock.SKY.defaultLightValue) ? (EnumSkyBlock.SKY.defaultLightValue - amount) : 0;
/*      */     }
/*      */ 
/*      */     
/*  831 */     int l = this.worldObj.provider.getHasNoSky() ? 0 : extendedblockstorage.getExtSkylightValue(i, j & 0xF, k);
/*  832 */     l -= amount;
/*  833 */     int i1 = extendedblockstorage.getExtBlocklightValue(i, j & 0xF, k);
/*      */     
/*  835 */     if (i1 > l)
/*      */     {
/*  837 */       l = i1;
/*      */     }
/*      */     
/*  840 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addEntity(Entity entityIn) {
/*  849 */     this.hasEntities = true;
/*  850 */     int i = MathHelper.floor_double(entityIn.posX / 16.0D);
/*  851 */     int j = MathHelper.floor_double(entityIn.posZ / 16.0D);
/*      */     
/*  853 */     if (i != this.xPosition || j != this.zPosition) {
/*      */       
/*  855 */       logger.warn("Wrong location! (" + i + ", " + j + ") should be (" + this.xPosition + ", " + this.zPosition + "), " + entityIn, new Object[] { entityIn });
/*  856 */       entityIn.setDead();
/*      */     } 
/*      */     
/*  859 */     int k = MathHelper.floor_double(entityIn.posY / 16.0D);
/*      */     
/*  861 */     if (k < 0)
/*      */     {
/*  863 */       k = 0;
/*      */     }
/*      */     
/*  866 */     if (k >= this.entityLists.length)
/*      */     {
/*  868 */       k = this.entityLists.length - 1;
/*      */     }
/*      */     
/*  871 */     entityIn.addedToChunk = true;
/*  872 */     entityIn.chunkCoordX = this.xPosition;
/*  873 */     entityIn.chunkCoordY = k;
/*  874 */     entityIn.chunkCoordZ = this.zPosition;
/*  875 */     this.entityLists[k].add(entityIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEntity(Entity entityIn) {
/*  883 */     removeEntityAtIndex(entityIn, entityIn.chunkCoordY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEntityAtIndex(Entity entityIn, int p_76608_2_) {
/*  891 */     if (p_76608_2_ < 0)
/*      */     {
/*  893 */       p_76608_2_ = 0;
/*      */     }
/*      */     
/*  896 */     if (p_76608_2_ >= this.entityLists.length)
/*      */     {
/*  898 */       p_76608_2_ = this.entityLists.length - 1;
/*      */     }
/*      */     
/*  901 */     this.entityLists[p_76608_2_].remove(entityIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canSeeSky(BlockPos pos) {
/*  906 */     int i = pos.getX() & 0xF;
/*  907 */     int j = pos.getY();
/*  908 */     int k = pos.getZ() & 0xF;
/*  909 */     return (j >= this.heightMap[k << 4 | i]);
/*      */   }
/*      */ 
/*      */   
/*      */   private TileEntity createNewTileEntity(BlockPos pos) {
/*  914 */     Block block = getBlock(pos);
/*  915 */     return !block.hasTileEntity() ? null : ((ITileEntityProvider)block).createNewTileEntity(this.worldObj, getBlockMetadata(pos));
/*      */   }
/*      */ 
/*      */   
/*      */   public TileEntity getTileEntity(BlockPos pos, EnumCreateEntityType p_177424_2_) {
/*  920 */     TileEntity tileentity = this.chunkTileEntityMap.get(pos);
/*      */     
/*  922 */     if (tileentity == null) {
/*      */       
/*  924 */       if (p_177424_2_ == EnumCreateEntityType.IMMEDIATE)
/*      */       {
/*  926 */         tileentity = createNewTileEntity(pos);
/*  927 */         this.worldObj.setTileEntity(pos, tileentity);
/*      */       }
/*  929 */       else if (p_177424_2_ == EnumCreateEntityType.QUEUED)
/*      */       {
/*  931 */         this.tileEntityPosQueue.add(pos);
/*      */       }
/*      */     
/*  934 */     } else if (tileentity.isInvalid()) {
/*      */       
/*  936 */       this.chunkTileEntityMap.remove(pos);
/*  937 */       return null;
/*      */     } 
/*      */     
/*  940 */     return tileentity;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addTileEntity(TileEntity tileEntityIn) {
/*  945 */     addTileEntity(tileEntityIn.getPos(), tileEntityIn);
/*      */     
/*  947 */     if (this.isChunkLoaded)
/*      */     {
/*  949 */       this.worldObj.addTileEntity(tileEntityIn);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void addTileEntity(BlockPos pos, TileEntity tileEntityIn) {
/*  955 */     tileEntityIn.setWorldObj(this.worldObj);
/*  956 */     tileEntityIn.setPos(pos);
/*      */     
/*  958 */     if (getBlock(pos) instanceof ITileEntityProvider) {
/*      */       
/*  960 */       if (this.chunkTileEntityMap.containsKey(pos))
/*      */       {
/*  962 */         ((TileEntity)this.chunkTileEntityMap.get(pos)).invalidate();
/*      */       }
/*      */       
/*  965 */       tileEntityIn.validate();
/*  966 */       this.chunkTileEntityMap.put(pos, tileEntityIn);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeTileEntity(BlockPos pos) {
/*  972 */     if (this.isChunkLoaded) {
/*      */       
/*  974 */       TileEntity tileentity = this.chunkTileEntityMap.remove(pos);
/*      */       
/*  976 */       if (tileentity != null)
/*      */       {
/*  978 */         tileentity.invalidate();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onChunkLoad() {
/*  988 */     this.isChunkLoaded = true;
/*  989 */     this.worldObj.addTileEntities(this.chunkTileEntityMap.values());
/*      */     
/*  991 */     for (int i = 0; i < this.entityLists.length; i++) {
/*      */       
/*  993 */       for (Entity entity : this.entityLists[i])
/*      */       {
/*  995 */         entity.onChunkLoad();
/*      */       }
/*      */       
/*  998 */       this.worldObj.loadEntities((Collection)this.entityLists[i]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onChunkUnload() {
/* 1007 */     this.isChunkLoaded = false;
/*      */     
/* 1009 */     for (TileEntity tileentity : this.chunkTileEntityMap.values())
/*      */     {
/* 1011 */       this.worldObj.markTileEntityForRemoval(tileentity);
/*      */     }
/*      */     
/* 1014 */     for (int i = 0; i < this.entityLists.length; i++)
/*      */     {
/* 1016 */       this.worldObj.unloadEntities((Collection)this.entityLists[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setChunkModified() {
/* 1025 */     this.isModified = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getEntitiesWithinAABBForEntity(Entity entityIn, AxisAlignedBB aabb, List<Entity> listToFill, Predicate<? super Entity> p_177414_4_) {
/* 1033 */     int i = MathHelper.floor_double((aabb.minY - 2.0D) / 16.0D);
/* 1034 */     int j = MathHelper.floor_double((aabb.maxY + 2.0D) / 16.0D);
/* 1035 */     i = MathHelper.clamp_int(i, 0, this.entityLists.length - 1);
/* 1036 */     j = MathHelper.clamp_int(j, 0, this.entityLists.length - 1);
/*      */     
/* 1038 */     for (int k = i; k <= j; k++) {
/*      */       
/* 1040 */       if (!this.entityLists[k].isEmpty())
/*      */       {
/* 1042 */         for (Entity entity : this.entityLists[k]) {
/*      */           
/* 1044 */           if (entity.getEntityBoundingBox().intersectsWith(aabb) && entity != entityIn) {
/*      */             
/* 1046 */             if (p_177414_4_ == null || p_177414_4_.apply(entity))
/*      */             {
/* 1048 */               listToFill.add(entity);
/*      */             }
/*      */             
/* 1051 */             Entity[] aentity = entity.getParts();
/*      */             
/* 1053 */             if (aentity != null)
/*      */             {
/* 1055 */               for (int l = 0; l < aentity.length; l++) {
/*      */                 
/* 1057 */                 entity = aentity[l];
/*      */                 
/* 1059 */                 if (entity != entityIn && entity.getEntityBoundingBox().intersectsWith(aabb) && (p_177414_4_ == null || p_177414_4_.apply(entity)))
/*      */                 {
/* 1061 */                   listToFill.add(entity);
/*      */                 }
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Entity> void getEntitiesOfTypeWithinAAAB(Class<? extends T> entityClass, AxisAlignedBB aabb, List<T> listToFill, Predicate<? super T> p_177430_4_) {
/* 1073 */     int i = MathHelper.floor_double((aabb.minY - 2.0D) / 16.0D);
/* 1074 */     int j = MathHelper.floor_double((aabb.maxY + 2.0D) / 16.0D);
/* 1075 */     i = MathHelper.clamp_int(i, 0, this.entityLists.length - 1);
/* 1076 */     j = MathHelper.clamp_int(j, 0, this.entityLists.length - 1);
/*      */     
/* 1078 */     for (int k = i; k <= j; k++) {
/*      */       
/* 1080 */       for (Entity entity : this.entityLists[k].getByClass(entityClass)) {
/*      */         
/* 1082 */         if (entity.getEntityBoundingBox().intersectsWith(aabb) && (p_177430_4_ == null || p_177430_4_.apply(entity)))
/*      */         {
/* 1084 */           listToFill.add((T)entity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean needsSaving(boolean p_76601_1_) {
/* 1095 */     if (p_76601_1_) {
/*      */       
/* 1097 */       if ((this.hasEntities && this.worldObj.getTotalWorldTime() != this.lastSaveTime) || this.isModified)
/*      */       {
/* 1099 */         return true;
/*      */       }
/*      */     }
/* 1102 */     else if (this.hasEntities && this.worldObj.getTotalWorldTime() >= this.lastSaveTime + 600L) {
/*      */       
/* 1104 */       return true;
/*      */     } 
/*      */     
/* 1107 */     return this.isModified;
/*      */   }
/*      */ 
/*      */   
/*      */   public Random getRandomWithSeed(long seed) {
/* 1112 */     return new Random(this.worldObj.getSeed() + (this.xPosition * this.xPosition * 4987142) + (this.xPosition * 5947611) + (this.zPosition * this.zPosition) * 4392871L + (this.zPosition * 389711) ^ seed);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/* 1117 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void populateChunk(IChunkProvider p_76624_1_, IChunkProvider p_76624_2_, int p_76624_3_, int p_76624_4_) {
/* 1122 */     boolean flag = p_76624_1_.chunkExists(p_76624_3_, p_76624_4_ - 1);
/* 1123 */     boolean flag1 = p_76624_1_.chunkExists(p_76624_3_ + 1, p_76624_4_);
/* 1124 */     boolean flag2 = p_76624_1_.chunkExists(p_76624_3_, p_76624_4_ + 1);
/* 1125 */     boolean flag3 = p_76624_1_.chunkExists(p_76624_3_ - 1, p_76624_4_);
/* 1126 */     boolean flag4 = p_76624_1_.chunkExists(p_76624_3_ - 1, p_76624_4_ - 1);
/* 1127 */     boolean flag5 = p_76624_1_.chunkExists(p_76624_3_ + 1, p_76624_4_ + 1);
/* 1128 */     boolean flag6 = p_76624_1_.chunkExists(p_76624_3_ - 1, p_76624_4_ + 1);
/* 1129 */     boolean flag7 = p_76624_1_.chunkExists(p_76624_3_ + 1, p_76624_4_ - 1);
/*      */     
/* 1131 */     if (flag1 && flag2 && flag5)
/*      */     {
/* 1133 */       if (!this.isTerrainPopulated) {
/*      */         
/* 1135 */         p_76624_1_.populate(p_76624_2_, p_76624_3_, p_76624_4_);
/*      */       }
/*      */       else {
/*      */         
/* 1139 */         p_76624_1_.func_177460_a(p_76624_2_, this, p_76624_3_, p_76624_4_);
/*      */       } 
/*      */     }
/*      */     
/* 1143 */     if (flag3 && flag2 && flag6) {
/*      */       
/* 1145 */       Chunk chunk = p_76624_1_.provideChunk(p_76624_3_ - 1, p_76624_4_);
/*      */       
/* 1147 */       if (!chunk.isTerrainPopulated) {
/*      */         
/* 1149 */         p_76624_1_.populate(p_76624_2_, p_76624_3_ - 1, p_76624_4_);
/*      */       }
/*      */       else {
/*      */         
/* 1153 */         p_76624_1_.func_177460_a(p_76624_2_, chunk, p_76624_3_ - 1, p_76624_4_);
/*      */       } 
/*      */     } 
/*      */     
/* 1157 */     if (flag && flag1 && flag7) {
/*      */       
/* 1159 */       Chunk chunk1 = p_76624_1_.provideChunk(p_76624_3_, p_76624_4_ - 1);
/*      */       
/* 1161 */       if (!chunk1.isTerrainPopulated) {
/*      */         
/* 1163 */         p_76624_1_.populate(p_76624_2_, p_76624_3_, p_76624_4_ - 1);
/*      */       }
/*      */       else {
/*      */         
/* 1167 */         p_76624_1_.func_177460_a(p_76624_2_, chunk1, p_76624_3_, p_76624_4_ - 1);
/*      */       } 
/*      */     } 
/*      */     
/* 1171 */     if (flag4 && flag && flag3) {
/*      */       
/* 1173 */       Chunk chunk2 = p_76624_1_.provideChunk(p_76624_3_ - 1, p_76624_4_ - 1);
/*      */       
/* 1175 */       if (!chunk2.isTerrainPopulated) {
/*      */         
/* 1177 */         p_76624_1_.populate(p_76624_2_, p_76624_3_ - 1, p_76624_4_ - 1);
/*      */       }
/*      */       else {
/*      */         
/* 1181 */         p_76624_1_.func_177460_a(p_76624_2_, chunk2, p_76624_3_ - 1, p_76624_4_ - 1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockPos getPrecipitationHeight(BlockPos pos) {
/* 1188 */     int i = pos.getX() & 0xF;
/* 1189 */     int j = pos.getZ() & 0xF;
/* 1190 */     int k = i | j << 4;
/* 1191 */     BlockPos blockpos = new BlockPos(pos.getX(), this.precipitationHeightMap[k], pos.getZ());
/*      */     
/* 1193 */     if (blockpos.getY() == -999) {
/*      */       
/* 1195 */       int l = getTopFilledSegment() + 15;
/* 1196 */       blockpos = new BlockPos(pos.getX(), l, pos.getZ());
/* 1197 */       int i1 = -1;
/*      */       
/* 1199 */       while (blockpos.getY() > 0 && i1 == -1) {
/*      */         
/* 1201 */         Block block = getBlock(blockpos);
/* 1202 */         Material material = block.getMaterial();
/*      */         
/* 1204 */         if (!material.blocksMovement() && !material.isLiquid()) {
/*      */           
/* 1206 */           blockpos = blockpos.down();
/*      */           
/*      */           continue;
/*      */         } 
/* 1210 */         i1 = blockpos.getY() + 1;
/*      */       } 
/*      */ 
/*      */       
/* 1214 */       this.precipitationHeightMap[k] = i1;
/*      */     } 
/*      */     
/* 1217 */     return new BlockPos(pos.getX(), this.precipitationHeightMap[k], pos.getZ());
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_150804_b(boolean p_150804_1_) {
/* 1222 */     if (this.isGapLightingUpdated && !this.worldObj.provider.getHasNoSky() && !p_150804_1_)
/*      */     {
/* 1224 */       recheckGaps(this.worldObj.isRemote);
/*      */     }
/*      */     
/* 1227 */     this.field_150815_m = true;
/*      */     
/* 1229 */     if (!this.isLightPopulated && this.isTerrainPopulated)
/*      */     {
/* 1231 */       func_150809_p();
/*      */     }
/*      */     
/* 1234 */     while (!this.tileEntityPosQueue.isEmpty()) {
/*      */       
/* 1236 */       BlockPos blockpos = this.tileEntityPosQueue.poll();
/*      */       
/* 1238 */       if (getTileEntity(blockpos, EnumCreateEntityType.CHECK) == null && getBlock(blockpos).hasTileEntity()) {
/*      */         
/* 1240 */         TileEntity tileentity = createNewTileEntity(blockpos);
/* 1241 */         this.worldObj.setTileEntity(blockpos, tileentity);
/* 1242 */         this.worldObj.markBlockRangeForRenderUpdate(blockpos, blockpos);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPopulated() {
/* 1249 */     return (this.field_150815_m && this.isTerrainPopulated && this.isLightPopulated);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChunkCoordIntPair getChunkCoordIntPair() {
/* 1257 */     return new ChunkCoordIntPair(this.xPosition, this.zPosition);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAreLevelsEmpty(int startY, int endY) {
/* 1266 */     if (startY < 0)
/*      */     {
/* 1268 */       startY = 0;
/*      */     }
/*      */     
/* 1271 */     if (endY >= 256)
/*      */     {
/* 1273 */       endY = 255;
/*      */     }
/*      */     
/* 1276 */     for (int i = startY; i <= endY; i += 16) {
/*      */       
/* 1278 */       ExtendedBlockStorage extendedblockstorage = this.storageArrays[i >> 4];
/*      */       
/* 1280 */       if (extendedblockstorage != null && !extendedblockstorage.isEmpty())
/*      */       {
/* 1282 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1286 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setStorageArrays(ExtendedBlockStorage[] newStorageArrays) {
/* 1291 */     if (this.storageArrays.length != newStorageArrays.length) {
/*      */       
/* 1293 */       logger.warn("Could not set level chunk sections, array length is " + newStorageArrays.length + " instead of " + this.storageArrays.length);
/*      */     }
/*      */     else {
/*      */       
/* 1297 */       for (int i = 0; i < this.storageArrays.length; i++)
/*      */       {
/* 1299 */         this.storageArrays[i] = newStorageArrays[i];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillChunk(byte[] p_177439_1_, int p_177439_2_, boolean p_177439_3_) {
/* 1309 */     int i = 0;
/* 1310 */     boolean flag = !this.worldObj.provider.getHasNoSky();
/*      */     
/* 1312 */     for (int j = 0; j < this.storageArrays.length; j++) {
/*      */       
/* 1314 */       if ((p_177439_2_ & 1 << j) != 0) {
/*      */         
/* 1316 */         if (this.storageArrays[j] == null)
/*      */         {
/* 1318 */           this.storageArrays[j] = new ExtendedBlockStorage(j << 4, flag);
/*      */         }
/*      */         
/* 1321 */         char[] achar = this.storageArrays[j].getData();
/*      */         
/* 1323 */         for (int k = 0; k < achar.length; k++)
/*      */         {
/* 1325 */           achar[k] = (char)((p_177439_1_[i + 1] & 0xFF) << 8 | p_177439_1_[i] & 0xFF);
/* 1326 */           i += 2;
/*      */         }
/*      */       
/* 1329 */       } else if (p_177439_3_ && this.storageArrays[j] != null) {
/*      */         
/* 1331 */         this.storageArrays[j] = null;
/*      */       } 
/*      */     } 
/*      */     int l;
/* 1335 */     for (l = 0; l < this.storageArrays.length; l++) {
/*      */       
/* 1337 */       if ((p_177439_2_ & 1 << l) != 0 && this.storageArrays[l] != null) {
/*      */         
/* 1339 */         NibbleArray nibblearray = this.storageArrays[l].getBlocklightArray();
/* 1340 */         System.arraycopy(p_177439_1_, i, nibblearray.getData(), 0, (nibblearray.getData()).length);
/* 1341 */         i += (nibblearray.getData()).length;
/*      */       } 
/*      */     } 
/*      */     
/* 1345 */     if (flag)
/*      */     {
/* 1347 */       for (int i1 = 0; i1 < this.storageArrays.length; i1++) {
/*      */         
/* 1349 */         if ((p_177439_2_ & 1 << i1) != 0 && this.storageArrays[i1] != null) {
/*      */           
/* 1351 */           NibbleArray nibblearray1 = this.storageArrays[i1].getSkylightArray();
/* 1352 */           System.arraycopy(p_177439_1_, i, nibblearray1.getData(), 0, (nibblearray1.getData()).length);
/* 1353 */           i += (nibblearray1.getData()).length;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1358 */     if (p_177439_3_) {
/*      */       
/* 1360 */       System.arraycopy(p_177439_1_, i, this.blockBiomeArray, 0, this.blockBiomeArray.length);
/* 1361 */       l = i + this.blockBiomeArray.length;
/*      */     } 
/*      */     
/* 1364 */     for (int j1 = 0; j1 < this.storageArrays.length; j1++) {
/*      */       
/* 1366 */       if (this.storageArrays[j1] != null && (p_177439_2_ & 1 << j1) != 0)
/*      */       {
/* 1368 */         this.storageArrays[j1].removeInvalidBlocks();
/*      */       }
/*      */     } 
/*      */     
/* 1372 */     this.isLightPopulated = true;
/* 1373 */     this.isTerrainPopulated = true;
/* 1374 */     generateHeightMap();
/*      */     
/* 1376 */     for (TileEntity tileentity : this.chunkTileEntityMap.values())
/*      */     {
/* 1378 */       tileentity.updateContainingBlockInfo();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public BiomeGenBase getBiome(BlockPos pos, WorldChunkManager chunkManager) {
/* 1384 */     int i = pos.getX() & 0xF;
/* 1385 */     int j = pos.getZ() & 0xF;
/* 1386 */     int k = this.blockBiomeArray[j << 4 | i] & 0xFF;
/*      */     
/* 1388 */     if (k == 255) {
/*      */       
/* 1390 */       BiomeGenBase biomegenbase = chunkManager.getBiomeGenerator(pos, BiomeGenBase.plains);
/* 1391 */       k = biomegenbase.biomeID;
/* 1392 */       this.blockBiomeArray[j << 4 | i] = (byte)(k & 0xFF);
/*      */     } 
/*      */     
/* 1395 */     BiomeGenBase biomegenbase1 = BiomeGenBase.getBiome(k);
/* 1396 */     return (biomegenbase1 == null) ? BiomeGenBase.plains : biomegenbase1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getBiomeArray() {
/* 1404 */     return this.blockBiomeArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBiomeArray(byte[] biomeArray) {
/* 1413 */     if (this.blockBiomeArray.length != biomeArray.length) {
/*      */       
/* 1415 */       logger.warn("Could not set level chunk biomes, array length is " + biomeArray.length + " instead of " + this.blockBiomeArray.length);
/*      */     }
/*      */     else {
/*      */       
/* 1419 */       for (int i = 0; i < this.blockBiomeArray.length; i++)
/*      */       {
/* 1421 */         this.blockBiomeArray[i] = biomeArray[i];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetRelightChecks() {
/* 1431 */     this.queuedLightChecks = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enqueueRelightChecks() {
/* 1441 */     BlockPos blockpos = new BlockPos(this.xPosition << 4, 0, this.zPosition << 4);
/*      */     
/* 1443 */     for (int i = 0; i < 8; i++) {
/*      */       
/* 1445 */       if (this.queuedLightChecks >= 4096) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 1450 */       int j = this.queuedLightChecks % 16;
/* 1451 */       int k = this.queuedLightChecks / 16 % 16;
/* 1452 */       int l = this.queuedLightChecks / 256;
/* 1453 */       this.queuedLightChecks++;
/*      */       
/* 1455 */       for (int i1 = 0; i1 < 16; i1++) {
/*      */         
/* 1457 */         BlockPos blockpos1 = blockpos.add(k, (j << 4) + i1, l);
/* 1458 */         boolean flag = (i1 == 0 || i1 == 15 || k == 0 || k == 15 || l == 0 || l == 15);
/*      */         
/* 1460 */         if ((this.storageArrays[j] == null && flag) || (this.storageArrays[j] != null && this.storageArrays[j].getBlockByExtId(k, i1, l).getMaterial() == Material.air)) {
/*      */           
/* 1462 */           for (EnumFacing enumfacing : EnumFacing.values()) {
/*      */             
/* 1464 */             BlockPos blockpos2 = blockpos1.offset(enumfacing);
/*      */             
/* 1466 */             if (this.worldObj.getBlockState(blockpos2).getBlock().getLightValue() > 0)
/*      */             {
/* 1468 */               this.worldObj.checkLight(blockpos2);
/*      */             }
/*      */           } 
/*      */           
/* 1472 */           this.worldObj.checkLight(blockpos1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_150809_p() {
/* 1480 */     this.isTerrainPopulated = true;
/* 1481 */     this.isLightPopulated = true;
/* 1482 */     BlockPos blockpos = new BlockPos(this.xPosition << 4, 0, this.zPosition << 4);
/*      */     
/* 1484 */     if (!this.worldObj.provider.getHasNoSky())
/*      */     {
/* 1486 */       if (this.worldObj.isAreaLoaded(blockpos.add(-1, 0, -1), blockpos.add(16, this.worldObj.func_181545_F(), 16))) {
/*      */         int i;
/*      */ 
/*      */         
/* 1490 */         label31: for (i = 0; i < 16; i++) {
/*      */           
/* 1492 */           for (int j = 0; j < 16; j++) {
/*      */             
/* 1494 */             if (!func_150811_f(i, j)) {
/*      */               
/* 1496 */               this.isLightPopulated = false;
/*      */               
/*      */               break label31;
/*      */             } 
/*      */           } 
/*      */         } 
/* 1502 */         if (this.isLightPopulated)
/*      */         {
/* 1504 */           for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*      */             
/* 1506 */             int k = (enumfacing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE) ? 16 : 1;
/* 1507 */             this.worldObj.getChunkFromBlockCoords(blockpos.offset(enumfacing, k)).func_180700_a(enumfacing.getOpposite());
/*      */           } 
/*      */           
/* 1510 */           func_177441_y();
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1515 */         this.isLightPopulated = false;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_177441_y() {
/* 1522 */     for (int i = 0; i < this.updateSkylightColumns.length; i++)
/*      */     {
/* 1524 */       this.updateSkylightColumns[i] = true;
/*      */     }
/*      */     
/* 1527 */     recheckGaps(false);
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_180700_a(EnumFacing p_180700_1_) {
/* 1532 */     if (this.isTerrainPopulated)
/*      */     {
/* 1534 */       if (p_180700_1_ == EnumFacing.EAST) {
/*      */         
/* 1536 */         for (int i = 0; i < 16; i++)
/*      */         {
/* 1538 */           func_150811_f(15, i);
/*      */         }
/*      */       }
/* 1541 */       else if (p_180700_1_ == EnumFacing.WEST) {
/*      */         
/* 1543 */         for (int j = 0; j < 16; j++)
/*      */         {
/* 1545 */           func_150811_f(0, j);
/*      */         }
/*      */       }
/* 1548 */       else if (p_180700_1_ == EnumFacing.SOUTH) {
/*      */         
/* 1550 */         for (int k = 0; k < 16; k++)
/*      */         {
/* 1552 */           func_150811_f(k, 15);
/*      */         }
/*      */       }
/* 1555 */       else if (p_180700_1_ == EnumFacing.NORTH) {
/*      */         
/* 1557 */         for (int l = 0; l < 16; l++)
/*      */         {
/* 1559 */           func_150811_f(l, 0);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean func_150811_f(int x, int z) {
/* 1567 */     int i = getTopFilledSegment();
/* 1568 */     boolean flag = false;
/* 1569 */     boolean flag1 = false;
/* 1570 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos((this.xPosition << 4) + x, 0, (this.zPosition << 4) + z);
/*      */     
/* 1572 */     for (int j = i + 16 - 1; j > this.worldObj.func_181545_F() || (j > 0 && !flag1); j--) {
/*      */       
/* 1574 */       blockpos$mutableblockpos.func_181079_c(blockpos$mutableblockpos.getX(), j, blockpos$mutableblockpos.getZ());
/* 1575 */       int k = getBlockLightOpacity((BlockPos)blockpos$mutableblockpos);
/*      */       
/* 1577 */       if (k == 255 && blockpos$mutableblockpos.getY() < this.worldObj.func_181545_F())
/*      */       {
/* 1579 */         flag1 = true;
/*      */       }
/*      */       
/* 1582 */       if (!flag && k > 0) {
/*      */         
/* 1584 */         flag = true;
/*      */       }
/* 1586 */       else if (flag && k == 0 && !this.worldObj.checkLight((BlockPos)blockpos$mutableblockpos)) {
/*      */         
/* 1588 */         return false;
/*      */       } 
/*      */     } 
/*      */     
/* 1592 */     for (int l = blockpos$mutableblockpos.getY(); l > 0; l--) {
/*      */       
/* 1594 */       blockpos$mutableblockpos.func_181079_c(blockpos$mutableblockpos.getX(), l, blockpos$mutableblockpos.getZ());
/*      */       
/* 1596 */       if (getBlock((BlockPos)blockpos$mutableblockpos).getLightValue() > 0)
/*      */       {
/* 1598 */         this.worldObj.checkLight((BlockPos)blockpos$mutableblockpos);
/*      */       }
/*      */     } 
/*      */     
/* 1602 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isLoaded() {
/* 1607 */     return this.isChunkLoaded;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setChunkLoaded(boolean loaded) {
/* 1612 */     this.isChunkLoaded = loaded;
/*      */   }
/*      */ 
/*      */   
/*      */   public World getWorld() {
/* 1617 */     return this.worldObj;
/*      */   }
/*      */ 
/*      */   
/*      */   public int[] getHeightMap() {
/* 1622 */     return this.heightMap;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHeightMap(int[] newHeightMap) {
/* 1627 */     if (this.heightMap.length != newHeightMap.length) {
/*      */       
/* 1629 */       logger.warn("Could not set level chunk heightmap, array length is " + newHeightMap.length + " instead of " + this.heightMap.length);
/*      */     }
/*      */     else {
/*      */       
/* 1633 */       for (int i = 0; i < this.heightMap.length; i++)
/*      */       {
/* 1635 */         this.heightMap[i] = newHeightMap[i];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Map<BlockPos, TileEntity> getTileEntityMap() {
/* 1642 */     return this.chunkTileEntityMap;
/*      */   }
/*      */ 
/*      */   
/*      */   public ClassInheritanceMultiMap<Entity>[] getEntityLists() {
/* 1647 */     return this.entityLists;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isTerrainPopulated() {
/* 1652 */     return this.isTerrainPopulated;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTerrainPopulated(boolean terrainPopulated) {
/* 1657 */     this.isTerrainPopulated = terrainPopulated;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isLightPopulated() {
/* 1662 */     return this.isLightPopulated;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLightPopulated(boolean lightPopulated) {
/* 1667 */     this.isLightPopulated = lightPopulated;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setModified(boolean modified) {
/* 1672 */     this.isModified = modified;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHasEntities(boolean hasEntitiesIn) {
/* 1677 */     this.hasEntities = hasEntitiesIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLastSaveTime(long saveTime) {
/* 1682 */     this.lastSaveTime = saveTime;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLowestHeight() {
/* 1687 */     return this.heightMapMinimum;
/*      */   }
/*      */ 
/*      */   
/*      */   public long getInhabitedTime() {
/* 1692 */     return this.inhabitedTime;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setInhabitedTime(long newInhabitedTime) {
/* 1697 */     this.inhabitedTime = newInhabitedTime;
/*      */   }
/*      */   
/*      */   public enum EnumCreateEntityType
/*      */   {
/* 1702 */     IMMEDIATE,
/* 1703 */     QUEUED,
/* 1704 */     CHECK;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\world\chunk\Chunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*      */ package net.minecraft.world;
/*      */ 
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Sets;
/*      */ import de.florianmichael.viamcp.fixes.FixedSoundEngine;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Callable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockLiquid;
/*      */ import net.minecraft.block.BlockSlab;
/*      */ import net.minecraft.block.BlockSnow;
/*      */ import net.minecraft.block.BlockStairs;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLiving;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.profiler.Profiler;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EntitySelectors;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.ITickable;
/*      */ import net.minecraft.util.IntHashMap;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.village.VillageCollection;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.minecraft.world.biome.WorldChunkManager;
/*      */ import net.minecraft.world.border.WorldBorder;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ import net.minecraft.world.chunk.IChunkProvider;
/*      */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*      */ import net.minecraft.world.storage.ISaveHandler;
/*      */ import net.minecraft.world.storage.MapStorage;
/*      */ import net.minecraft.world.storage.WorldInfo;
/*      */ 
/*      */ public abstract class World
/*      */   implements IBlockAccess
/*      */ {
/*   60 */   private int field_181546_a = 63;
/*      */ 
/*      */   
/*      */   protected boolean scheduledUpdatesAreImmediate;
/*      */ 
/*      */   
/*   66 */   public final List<Entity> loadedEntityList = Lists.newArrayList();
/*   67 */   protected final List<Entity> unloadedEntityList = Lists.newArrayList();
/*   68 */   public final List<TileEntity> loadedTileEntityList = Lists.newArrayList();
/*   69 */   public final List<TileEntity> tickableTileEntities = Lists.newArrayList();
/*   70 */   private final List<TileEntity> addedTileEntityList = Lists.newArrayList();
/*   71 */   private final List<TileEntity> tileEntitiesToBeRemoved = Lists.newArrayList();
/*   72 */   public final List<EntityPlayer> playerEntities = Lists.newArrayList();
/*   73 */   public final List<Entity> weatherEffects = Lists.newArrayList();
/*   74 */   protected final IntHashMap<Entity> entitiesById = new IntHashMap();
/*   75 */   private long cloudColour = 16777215L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int skylightSubtracted;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   85 */   protected int updateLCG = (new Random()).nextInt();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   90 */   protected final int DIST_HASH_MAGIC = 1013904223;
/*      */ 
/*      */   
/*      */   protected float prevRainingStrength;
/*      */   
/*      */   protected float rainingStrength;
/*      */   
/*      */   protected float prevThunderingStrength;
/*      */   
/*      */   protected float thunderingStrength;
/*      */   
/*      */   private int lastLightningBolt;
/*      */   
/*  103 */   public final Random rand = new Random();
/*      */   
/*      */   public final WorldProvider provider;
/*      */   
/*  107 */   protected List<IWorldAccess> worldAccesses = Lists.newArrayList();
/*      */ 
/*      */   
/*      */   protected IChunkProvider chunkProvider;
/*      */ 
/*      */   
/*      */   protected final ISaveHandler saveHandler;
/*      */ 
/*      */   
/*      */   protected WorldInfo worldInfo;
/*      */   
/*      */   protected boolean findingSpawnPoint;
/*      */   
/*      */   protected MapStorage mapStorage;
/*      */   
/*      */   protected VillageCollection villageCollectionObj;
/*      */   
/*      */   public final Profiler theProfiler;
/*      */   
/*  126 */   private final Calendar theCalendar = Calendar.getInstance();
/*  127 */   protected Scoreboard worldScoreboard = new Scoreboard();
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isRemote;
/*      */ 
/*      */   
/*  134 */   protected Set<ChunkCoordIntPair> activeChunkSet = Sets.newHashSet();
/*      */ 
/*      */   
/*      */   private int ambientTickCountdown;
/*      */ 
/*      */   
/*      */   protected boolean spawnHostileMobs;
/*      */ 
/*      */   
/*      */   protected boolean spawnPeacefulMobs;
/*      */ 
/*      */   
/*      */   private boolean processingLoadedTiles;
/*      */ 
/*      */   
/*      */   private final WorldBorder worldBorder;
/*      */ 
/*      */   
/*      */   int[] lightUpdateBlockList;
/*      */ 
/*      */ 
/*      */   
/*      */   protected World(ISaveHandler saveHandlerIn, WorldInfo info, WorldProvider providerIn, Profiler profilerIn, boolean client) {
/*  157 */     this.ambientTickCountdown = this.rand.nextInt(12000);
/*  158 */     this.spawnHostileMobs = true;
/*  159 */     this.spawnPeacefulMobs = true;
/*  160 */     this.lightUpdateBlockList = new int[32768];
/*  161 */     this.saveHandler = saveHandlerIn;
/*  162 */     this.theProfiler = profilerIn;
/*  163 */     this.worldInfo = info;
/*  164 */     this.provider = providerIn;
/*  165 */     this.isRemote = client;
/*  166 */     this.worldBorder = providerIn.getWorldBorder();
/*      */   }
/*      */ 
/*      */   
/*      */   public World init() {
/*  171 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public BiomeGenBase getBiomeGenForCoords(final BlockPos pos) {
/*  176 */     if (isBlockLoaded(pos)) {
/*      */       
/*  178 */       Chunk chunk = getChunkFromBlockCoords(pos);
/*      */ 
/*      */       
/*      */       try {
/*  182 */         return chunk.getBiome(pos, this.provider.getWorldChunkManager());
/*      */       }
/*  184 */       catch (Throwable throwable) {
/*      */         
/*  186 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Getting biome");
/*  187 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Coordinates of biome request");
/*  188 */         crashreportcategory.addCrashSectionCallable("Location", new Callable<String>()
/*      */             {
/*      */               public String call() throws Exception
/*      */               {
/*  192 */                 return CrashReportCategory.getCoordinateInfo(pos);
/*      */               }
/*      */             });
/*  195 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  200 */     return this.provider.getWorldChunkManager().getBiomeGenerator(pos, BiomeGenBase.plains);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldChunkManager getWorldChunkManager() {
/*  206 */     return this.provider.getWorldChunkManager();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract IChunkProvider createChunkProvider();
/*      */ 
/*      */ 
/*      */   
/*      */   public void initialize(WorldSettings settings) {
/*  216 */     this.worldInfo.setServerInitialized(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInitialSpawnLocation() {
/*  224 */     setSpawnPoint(new BlockPos(8, 64, 8));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Block getGroundAboveSeaLevel(BlockPos pos) {
/*      */     BlockPos blockpos;
/*  231 */     for (blockpos = new BlockPos(pos.getX(), func_181545_F(), pos.getZ()); !isAirBlock(blockpos.up()); blockpos = blockpos.up());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  236 */     return getBlockState(blockpos).getBlock();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValid(BlockPos pos) {
/*  244 */     return (pos.getX() >= -30000000 && pos.getZ() >= -30000000 && pos.getX() < 30000000 && pos.getZ() < 30000000 && pos.getY() >= 0 && pos.getY() < 256);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAirBlock(BlockPos pos) {
/*  255 */     return (getBlockState(pos).getBlock().getMaterial() == Material.air);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockLoaded(BlockPos pos) {
/*  260 */     return isBlockLoaded(pos, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockLoaded(BlockPos pos, boolean allowEmpty) {
/*  265 */     return !isValid(pos) ? false : isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4, allowEmpty);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAreaLoaded(BlockPos center, int radius) {
/*  270 */     return isAreaLoaded(center, radius, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAreaLoaded(BlockPos center, int radius, boolean allowEmpty) {
/*  275 */     return isAreaLoaded(center.getX() - radius, center.getY() - radius, center.getZ() - radius, center.getX() + radius, center.getY() + radius, center.getZ() + radius, allowEmpty);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAreaLoaded(BlockPos from, BlockPos to) {
/*  280 */     return isAreaLoaded(from, to, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAreaLoaded(BlockPos from, BlockPos to, boolean allowEmpty) {
/*  285 */     return isAreaLoaded(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ(), allowEmpty);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAreaLoaded(StructureBoundingBox box) {
/*  290 */     return isAreaLoaded(box, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAreaLoaded(StructureBoundingBox box, boolean allowEmpty) {
/*  295 */     return isAreaLoaded(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, allowEmpty);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isAreaLoaded(int xStart, int yStart, int zStart, int xEnd, int yEnd, int zEnd, boolean allowEmpty) {
/*  300 */     if (yEnd >= 0 && yStart < 256) {
/*      */       
/*  302 */       xStart >>= 4;
/*  303 */       zStart >>= 4;
/*  304 */       xEnd >>= 4;
/*  305 */       zEnd >>= 4;
/*      */       
/*  307 */       for (int i = xStart; i <= xEnd; i++) {
/*      */         
/*  309 */         for (int j = zStart; j <= zEnd; j++) {
/*      */           
/*  311 */           if (!isChunkLoaded(i, j, allowEmpty))
/*      */           {
/*  313 */             return false;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  318 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  322 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isChunkLoaded(int x, int z, boolean allowEmpty) {
/*  328 */     return (this.chunkProvider.chunkExists(x, z) && (allowEmpty || !this.chunkProvider.provideChunk(x, z).isEmpty()));
/*      */   }
/*      */ 
/*      */   
/*      */   public Chunk getChunkFromBlockCoords(BlockPos pos) {
/*  333 */     return getChunkFromChunkCoords(pos.getX() >> 4, pos.getZ() >> 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Chunk getChunkFromChunkCoords(int chunkX, int chunkZ) {
/*  344 */     return this.chunkProvider.provideChunk(chunkX, chunkZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setBlockState(BlockPos pos, IBlockState newState, int flags) {
/*  357 */     if (!isValid(pos))
/*      */     {
/*  359 */       return false;
/*      */     }
/*  361 */     if (!this.isRemote && this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD)
/*      */     {
/*  363 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  367 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*  368 */     Block block = newState.getBlock();
/*  369 */     IBlockState iblockstate = chunk.setBlockState(pos, newState);
/*      */     
/*  371 */     if (iblockstate == null)
/*      */     {
/*  373 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  377 */     Block block1 = iblockstate.getBlock();
/*      */     
/*  379 */     if (block.getLightOpacity() != block1.getLightOpacity() || block.getLightValue() != block1.getLightValue()) {
/*      */       
/*  381 */       this.theProfiler.startSection("checkLight");
/*  382 */       checkLight(pos);
/*  383 */       this.theProfiler.endSection();
/*      */     } 
/*      */     
/*  386 */     if ((flags & 0x2) != 0 && (!this.isRemote || (flags & 0x4) == 0) && chunk.isPopulated())
/*      */     {
/*  388 */       markBlockForUpdate(pos);
/*      */     }
/*      */     
/*  391 */     if (!this.isRemote && (flags & 0x1) != 0) {
/*      */       
/*  393 */       notifyNeighborsRespectDebug(pos, iblockstate.getBlock());
/*      */       
/*  395 */       if (block.hasComparatorInputOverride())
/*      */       {
/*  397 */         updateComparatorOutputLevel(pos, block);
/*      */       }
/*      */     } 
/*      */     
/*  401 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setBlockToAir(BlockPos pos) {
/*  408 */     return setBlockState(pos, Blocks.air.getDefaultState(), 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean destroyBlock(BlockPos pos, boolean dropBlock) {
/*  416 */     return FixedSoundEngine.destroyBlock(this, pos, dropBlock);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setBlockState(BlockPos pos, IBlockState state) {
/*  424 */     return setBlockState(pos, state, 3);
/*      */   }
/*      */ 
/*      */   
/*      */   public void markBlockForUpdate(BlockPos pos) {
/*  429 */     for (int i = 0; i < this.worldAccesses.size(); i++)
/*      */     {
/*  431 */       ((IWorldAccess)this.worldAccesses.get(i)).markBlockForUpdate(pos);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyNeighborsRespectDebug(BlockPos pos, Block blockType) {
/*  437 */     if (this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD)
/*      */     {
/*  439 */       notifyNeighborsOfStateChange(pos, blockType);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void markBlocksDirtyVertical(int x1, int z1, int x2, int z2) {
/*  448 */     if (x2 > z2) {
/*      */       
/*  450 */       int i = z2;
/*  451 */       z2 = x2;
/*  452 */       x2 = i;
/*      */     } 
/*      */     
/*  455 */     if (!this.provider.getHasNoSky())
/*      */     {
/*  457 */       for (int j = x2; j <= z2; j++)
/*      */       {
/*  459 */         checkLightFor(EnumSkyBlock.SKY, new BlockPos(x1, j, z1));
/*      */       }
/*      */     }
/*      */     
/*  463 */     markBlockRangeForRenderUpdate(x1, x2, z1, x1, z2, z1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void markBlockRangeForRenderUpdate(BlockPos rangeMin, BlockPos rangeMax) {
/*  468 */     markBlockRangeForRenderUpdate(rangeMin.getX(), rangeMin.getY(), rangeMin.getZ(), rangeMax.getX(), rangeMax.getY(), rangeMax.getZ());
/*      */   }
/*      */ 
/*      */   
/*      */   public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
/*  473 */     for (int i = 0; i < this.worldAccesses.size(); i++)
/*      */     {
/*  475 */       ((IWorldAccess)this.worldAccesses.get(i)).markBlockRangeForRenderUpdate(x1, y1, z1, x2, y2, z2);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyNeighborsOfStateChange(BlockPos pos, Block blockType) {
/*  481 */     notifyBlockOfStateChange(pos.west(), blockType);
/*  482 */     notifyBlockOfStateChange(pos.east(), blockType);
/*  483 */     notifyBlockOfStateChange(pos.down(), blockType);
/*  484 */     notifyBlockOfStateChange(pos.up(), blockType);
/*  485 */     notifyBlockOfStateChange(pos.north(), blockType);
/*  486 */     notifyBlockOfStateChange(pos.south(), blockType);
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyNeighborsOfStateExcept(BlockPos pos, Block blockType, EnumFacing skipSide) {
/*  491 */     if (skipSide != EnumFacing.WEST)
/*      */     {
/*  493 */       notifyBlockOfStateChange(pos.west(), blockType);
/*      */     }
/*      */     
/*  496 */     if (skipSide != EnumFacing.EAST)
/*      */     {
/*  498 */       notifyBlockOfStateChange(pos.east(), blockType);
/*      */     }
/*      */     
/*  501 */     if (skipSide != EnumFacing.DOWN)
/*      */     {
/*  503 */       notifyBlockOfStateChange(pos.down(), blockType);
/*      */     }
/*      */     
/*  506 */     if (skipSide != EnumFacing.UP)
/*      */     {
/*  508 */       notifyBlockOfStateChange(pos.up(), blockType);
/*      */     }
/*      */     
/*  511 */     if (skipSide != EnumFacing.NORTH)
/*      */     {
/*  513 */       notifyBlockOfStateChange(pos.north(), blockType);
/*      */     }
/*      */     
/*  516 */     if (skipSide != EnumFacing.SOUTH)
/*      */     {
/*  518 */       notifyBlockOfStateChange(pos.south(), blockType);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyBlockOfStateChange(BlockPos pos, final Block blockIn) {
/*  524 */     if (!this.isRemote) {
/*      */       
/*  526 */       IBlockState iblockstate = getBlockState(pos);
/*      */ 
/*      */       
/*      */       try {
/*  530 */         iblockstate.getBlock().onNeighborBlockChange(this, pos, iblockstate, blockIn);
/*      */       }
/*  532 */       catch (Throwable throwable) {
/*      */         
/*  534 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception while updating neighbours");
/*  535 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being updated");
/*  536 */         crashreportcategory.addCrashSectionCallable("Source block type", new Callable<String>()
/*      */             {
/*      */               
/*      */               public String call() throws Exception
/*      */               {
/*      */                 try {
/*  542 */                   return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(Block.getIdFromBlock(this.val$blockIn)), this.val$blockIn.getUnlocalizedName(), this.val$blockIn.getClass().getCanonicalName() });
/*      */                 }
/*  544 */                 catch (Throwable var2) {
/*      */                   
/*  546 */                   return "ID #" + Block.getIdFromBlock(blockIn);
/*      */                 } 
/*      */               }
/*      */             });
/*  550 */         CrashReportCategory.addBlockInfo(crashreportcategory, pos, iblockstate);
/*  551 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockTickPending(BlockPos pos, Block blockType) {
/*  558 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canSeeSky(BlockPos pos) {
/*  563 */     return getChunkFromBlockCoords(pos).canSeeSky(pos);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBlockSeeSky(BlockPos pos) {
/*  568 */     if (pos.getY() >= func_181545_F())
/*      */     {
/*  570 */       return canSeeSky(pos);
/*      */     }
/*      */ 
/*      */     
/*  574 */     BlockPos blockpos = new BlockPos(pos.getX(), func_181545_F(), pos.getZ());
/*      */     
/*  576 */     if (!canSeeSky(blockpos))
/*      */     {
/*  578 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  582 */     for (blockpos = blockpos.down(); blockpos.getY() > pos.getY(); blockpos = blockpos.down()) {
/*      */       
/*  584 */       Block block = getBlockState(blockpos).getBlock();
/*      */       
/*  586 */       if (block.getLightOpacity() > 0 && !block.getMaterial().isLiquid())
/*      */       {
/*  588 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  592 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLight(BlockPos pos) {
/*  599 */     if (pos.getY() < 0)
/*      */     {
/*  601 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  605 */     if (pos.getY() >= 256)
/*      */     {
/*  607 */       pos = new BlockPos(pos.getX(), 255, pos.getZ());
/*      */     }
/*      */     
/*  610 */     return getChunkFromBlockCoords(pos).getLightSubtracted(pos, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLightFromNeighbors(BlockPos pos) {
/*  616 */     return getLight(pos, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLight(BlockPos pos, boolean checkNeighbors) {
/*  621 */     if (pos.getX() >= -30000000 && pos.getZ() >= -30000000 && pos.getX() < 30000000 && pos.getZ() < 30000000) {
/*      */       
/*  623 */       if (checkNeighbors && getBlockState(pos).getBlock().getUseNeighborBrightness()) {
/*      */         
/*  625 */         int i1 = getLight(pos.up(), false);
/*  626 */         int i = getLight(pos.east(), false);
/*  627 */         int j = getLight(pos.west(), false);
/*  628 */         int k = getLight(pos.south(), false);
/*  629 */         int l = getLight(pos.north(), false);
/*      */         
/*  631 */         if (i > i1)
/*      */         {
/*  633 */           i1 = i;
/*      */         }
/*      */         
/*  636 */         if (j > i1)
/*      */         {
/*  638 */           i1 = j;
/*      */         }
/*      */         
/*  641 */         if (k > i1)
/*      */         {
/*  643 */           i1 = k;
/*      */         }
/*      */         
/*  646 */         if (l > i1)
/*      */         {
/*  648 */           i1 = l;
/*      */         }
/*      */         
/*  651 */         return i1;
/*      */       } 
/*  653 */       if (pos.getY() < 0)
/*      */       {
/*  655 */         return 0;
/*      */       }
/*      */ 
/*      */       
/*  659 */       if (pos.getY() >= 256)
/*      */       {
/*  661 */         pos = new BlockPos(pos.getX(), 255, pos.getZ());
/*      */       }
/*      */       
/*  664 */       Chunk chunk = getChunkFromBlockCoords(pos);
/*  665 */       return chunk.getLightSubtracted(pos, this.skylightSubtracted);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  670 */     return 15;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getHeight(BlockPos pos) {
/*      */     int i;
/*  681 */     if (pos.getX() >= -30000000 && pos.getZ() >= -30000000 && pos.getX() < 30000000 && pos.getZ() < 30000000) {
/*      */       
/*  683 */       if (isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4, true))
/*      */       {
/*  685 */         i = getChunkFromChunkCoords(pos.getX() >> 4, pos.getZ() >> 4).getHeightValue(pos.getX() & 0xF, pos.getZ() & 0xF);
/*      */       }
/*      */       else
/*      */       {
/*  689 */         i = 0;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  694 */       i = func_181545_F() + 1;
/*      */     } 
/*      */     
/*  697 */     return new BlockPos(pos.getX(), i, pos.getZ());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getChunksLowestHorizon(int x, int z) {
/*  705 */     if (x >= -30000000 && z >= -30000000 && x < 30000000 && z < 30000000) {
/*      */       
/*  707 */       if (!isChunkLoaded(x >> 4, z >> 4, true))
/*      */       {
/*  709 */         return 0;
/*      */       }
/*      */ 
/*      */       
/*  713 */       Chunk chunk = getChunkFromChunkCoords(x >> 4, z >> 4);
/*  714 */       return chunk.getLowestHeight();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  719 */     return func_181545_F() + 1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLightFromNeighborsFor(EnumSkyBlock type, BlockPos pos) {
/*  725 */     if (this.provider.getHasNoSky() && type == EnumSkyBlock.SKY)
/*      */     {
/*  727 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  731 */     if (pos.getY() < 0)
/*      */     {
/*  733 */       pos = new BlockPos(pos.getX(), 0, pos.getZ());
/*      */     }
/*      */     
/*  736 */     if (!isValid(pos))
/*      */     {
/*  738 */       return type.defaultLightValue;
/*      */     }
/*  740 */     if (!isBlockLoaded(pos))
/*      */     {
/*  742 */       return type.defaultLightValue;
/*      */     }
/*  744 */     if (getBlockState(pos).getBlock().getUseNeighborBrightness()) {
/*      */       
/*  746 */       int i1 = getLightFor(type, pos.up());
/*  747 */       int i = getLightFor(type, pos.east());
/*  748 */       int j = getLightFor(type, pos.west());
/*  749 */       int k = getLightFor(type, pos.south());
/*  750 */       int l = getLightFor(type, pos.north());
/*      */       
/*  752 */       if (i > i1)
/*      */       {
/*  754 */         i1 = i;
/*      */       }
/*      */       
/*  757 */       if (j > i1)
/*      */       {
/*  759 */         i1 = j;
/*      */       }
/*      */       
/*  762 */       if (k > i1)
/*      */       {
/*  764 */         i1 = k;
/*      */       }
/*      */       
/*  767 */       if (l > i1)
/*      */       {
/*  769 */         i1 = l;
/*      */       }
/*      */       
/*  772 */       return i1;
/*      */     } 
/*      */ 
/*      */     
/*  776 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*  777 */     return chunk.getLightFor(type, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLightFor(EnumSkyBlock type, BlockPos pos) {
/*  784 */     if (pos.getY() < 0)
/*      */     {
/*  786 */       pos = new BlockPos(pos.getX(), 0, pos.getZ());
/*      */     }
/*      */     
/*  789 */     if (!isValid(pos))
/*      */     {
/*  791 */       return type.defaultLightValue;
/*      */     }
/*  793 */     if (!isBlockLoaded(pos))
/*      */     {
/*  795 */       return type.defaultLightValue;
/*      */     }
/*      */ 
/*      */     
/*  799 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*  800 */     return chunk.getLightFor(type, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLightFor(EnumSkyBlock type, BlockPos pos, int lightValue) {
/*  806 */     if (isValid(pos))
/*      */     {
/*  808 */       if (isBlockLoaded(pos)) {
/*      */         
/*  810 */         Chunk chunk = getChunkFromBlockCoords(pos);
/*  811 */         chunk.setLightFor(type, pos, lightValue);
/*  812 */         notifyLightSet(pos);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyLightSet(BlockPos pos) {
/*  819 */     for (int i = 0; i < this.worldAccesses.size(); i++)
/*      */     {
/*  821 */       ((IWorldAccess)this.worldAccesses.get(i)).notifyLightSet(pos);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCombinedLight(BlockPos pos, int lightValue) {
/*  827 */     int i = getLightFromNeighborsFor(EnumSkyBlock.SKY, pos);
/*  828 */     int j = getLightFromNeighborsFor(EnumSkyBlock.BLOCK, pos);
/*      */     
/*  830 */     if (j < lightValue)
/*      */     {
/*  832 */       j = lightValue;
/*      */     }
/*      */     
/*  835 */     return i << 20 | j << 4;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getLightBrightness(BlockPos pos) {
/*  840 */     return this.provider.getLightBrightnessTable()[getLightFromNeighbors(pos)];
/*      */   }
/*      */ 
/*      */   
/*      */   public IBlockState getBlockState(BlockPos pos) {
/*  845 */     if (!isValid(pos))
/*      */     {
/*  847 */       return Blocks.air.getDefaultState();
/*      */     }
/*      */ 
/*      */     
/*  851 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*  852 */     return chunk.getBlockState(pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDaytime() {
/*  861 */     return (this.skylightSubtracted < 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MovingObjectPosition rayTraceBlocks(Vec3 p_72933_1_, Vec3 p_72933_2_) {
/*  869 */     return rayTraceBlocks(p_72933_1_, p_72933_2_, false, false, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public MovingObjectPosition rayTraceBlocks(Vec3 start, Vec3 end, boolean stopOnLiquid) {
/*  874 */     return rayTraceBlocks(start, end, stopOnLiquid, false, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MovingObjectPosition rayTraceBlocks(Vec3 vec31, Vec3 vec32, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
/*  883 */     if (!Double.isNaN(vec31.xCoord) && !Double.isNaN(vec31.yCoord) && !Double.isNaN(vec31.zCoord)) {
/*      */       
/*  885 */       if (!Double.isNaN(vec32.xCoord) && !Double.isNaN(vec32.yCoord) && !Double.isNaN(vec32.zCoord)) {
/*      */         
/*  887 */         int i = MathHelper.floor_double(vec32.xCoord);
/*  888 */         int j = MathHelper.floor_double(vec32.yCoord);
/*  889 */         int k = MathHelper.floor_double(vec32.zCoord);
/*  890 */         int l = MathHelper.floor_double(vec31.xCoord);
/*  891 */         int i1 = MathHelper.floor_double(vec31.yCoord);
/*  892 */         int j1 = MathHelper.floor_double(vec31.zCoord);
/*  893 */         BlockPos blockpos = new BlockPos(l, i1, j1);
/*  894 */         IBlockState iblockstate = getBlockState(blockpos);
/*  895 */         Block block = iblockstate.getBlock();
/*      */         
/*  897 */         if ((!ignoreBlockWithoutBoundingBox || block.getCollisionBoundingBox(this, blockpos, iblockstate) != null) && block.canCollideCheck(iblockstate, stopOnLiquid)) {
/*      */           
/*  899 */           MovingObjectPosition movingobjectposition = block.collisionRayTrace(this, blockpos, vec31, vec32);
/*      */           
/*  901 */           if (movingobjectposition != null)
/*      */           {
/*  903 */             return movingobjectposition;
/*      */           }
/*      */         } 
/*      */         
/*  907 */         MovingObjectPosition movingobjectposition2 = null;
/*  908 */         int k1 = 200;
/*      */         
/*  910 */         while (k1-- >= 0) {
/*      */           EnumFacing enumfacing;
/*  912 */           if (Double.isNaN(vec31.xCoord) || Double.isNaN(vec31.yCoord) || Double.isNaN(vec31.zCoord))
/*      */           {
/*  914 */             return null;
/*      */           }
/*      */           
/*  917 */           if (l == i && i1 == j && j1 == k)
/*      */           {
/*  919 */             return returnLastUncollidableBlock ? movingobjectposition2 : null;
/*      */           }
/*      */           
/*  922 */           boolean flag2 = true;
/*  923 */           boolean flag = true;
/*  924 */           boolean flag1 = true;
/*  925 */           double d0 = 999.0D;
/*  926 */           double d1 = 999.0D;
/*  927 */           double d2 = 999.0D;
/*      */           
/*  929 */           if (i > l) {
/*      */             
/*  931 */             d0 = l + 1.0D;
/*      */           }
/*  933 */           else if (i < l) {
/*      */             
/*  935 */             d0 = l + 0.0D;
/*      */           }
/*      */           else {
/*      */             
/*  939 */             flag2 = false;
/*      */           } 
/*      */           
/*  942 */           if (j > i1) {
/*      */             
/*  944 */             d1 = i1 + 1.0D;
/*      */           }
/*  946 */           else if (j < i1) {
/*      */             
/*  948 */             d1 = i1 + 0.0D;
/*      */           }
/*      */           else {
/*      */             
/*  952 */             flag = false;
/*      */           } 
/*      */           
/*  955 */           if (k > j1) {
/*      */             
/*  957 */             d2 = j1 + 1.0D;
/*      */           }
/*  959 */           else if (k < j1) {
/*      */             
/*  961 */             d2 = j1 + 0.0D;
/*      */           }
/*      */           else {
/*      */             
/*  965 */             flag1 = false;
/*      */           } 
/*      */           
/*  968 */           double d3 = 999.0D;
/*  969 */           double d4 = 999.0D;
/*  970 */           double d5 = 999.0D;
/*  971 */           double d6 = vec32.xCoord - vec31.xCoord;
/*  972 */           double d7 = vec32.yCoord - vec31.yCoord;
/*  973 */           double d8 = vec32.zCoord - vec31.zCoord;
/*      */           
/*  975 */           if (flag2)
/*      */           {
/*  977 */             d3 = (d0 - vec31.xCoord) / d6;
/*      */           }
/*      */           
/*  980 */           if (flag)
/*      */           {
/*  982 */             d4 = (d1 - vec31.yCoord) / d7;
/*      */           }
/*      */           
/*  985 */           if (flag1)
/*      */           {
/*  987 */             d5 = (d2 - vec31.zCoord) / d8;
/*      */           }
/*      */           
/*  990 */           if (d3 == -0.0D)
/*      */           {
/*  992 */             d3 = -1.0E-4D;
/*      */           }
/*      */           
/*  995 */           if (d4 == -0.0D)
/*      */           {
/*  997 */             d4 = -1.0E-4D;
/*      */           }
/*      */           
/* 1000 */           if (d5 == -0.0D)
/*      */           {
/* 1002 */             d5 = -1.0E-4D;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1007 */           if (d3 < d4 && d3 < d5) {
/*      */             
/* 1009 */             enumfacing = (i > l) ? EnumFacing.WEST : EnumFacing.EAST;
/* 1010 */             vec31 = new Vec3(d0, vec31.yCoord + d7 * d3, vec31.zCoord + d8 * d3);
/*      */           }
/* 1012 */           else if (d4 < d5) {
/*      */             
/* 1014 */             enumfacing = (j > i1) ? EnumFacing.DOWN : EnumFacing.UP;
/* 1015 */             vec31 = new Vec3(vec31.xCoord + d6 * d4, d1, vec31.zCoord + d8 * d4);
/*      */           }
/*      */           else {
/*      */             
/* 1019 */             enumfacing = (k > j1) ? EnumFacing.NORTH : EnumFacing.SOUTH;
/* 1020 */             vec31 = new Vec3(vec31.xCoord + d6 * d5, vec31.yCoord + d7 * d5, d2);
/*      */           } 
/*      */           
/* 1023 */           l = MathHelper.floor_double(vec31.xCoord) - ((enumfacing == EnumFacing.EAST) ? 1 : 0);
/* 1024 */           i1 = MathHelper.floor_double(vec31.yCoord) - ((enumfacing == EnumFacing.UP) ? 1 : 0);
/* 1025 */           j1 = MathHelper.floor_double(vec31.zCoord) - ((enumfacing == EnumFacing.SOUTH) ? 1 : 0);
/* 1026 */           blockpos = new BlockPos(l, i1, j1);
/* 1027 */           IBlockState iblockstate1 = getBlockState(blockpos);
/* 1028 */           Block block1 = iblockstate1.getBlock();
/*      */           
/* 1030 */           if (!ignoreBlockWithoutBoundingBox || block1.getCollisionBoundingBox(this, blockpos, iblockstate1) != null) {
/*      */             
/* 1032 */             if (block1.canCollideCheck(iblockstate1, stopOnLiquid)) {
/*      */               
/* 1034 */               MovingObjectPosition movingobjectposition1 = block1.collisionRayTrace(this, blockpos, vec31, vec32);
/*      */               
/* 1036 */               if (movingobjectposition1 != null)
/*      */               {
/* 1038 */                 return movingobjectposition1;
/*      */               }
/*      */               
/*      */               continue;
/*      */             } 
/* 1043 */             movingobjectposition2 = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec31, enumfacing, blockpos);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1048 */         return returnLastUncollidableBlock ? movingobjectposition2 : null;
/*      */       } 
/*      */ 
/*      */       
/* 1052 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1057 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSoundAtEntity(Entity entityIn, String name, float volume, float pitch) {
/* 1067 */     for (int i = 0; i < this.worldAccesses.size(); i++)
/*      */     {
/* 1069 */       ((IWorldAccess)this.worldAccesses.get(i)).playSound(name, entityIn.posX, entityIn.posY, entityIn.posZ, volume, pitch);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSoundToNearExcept(EntityPlayer player, String name, float volume, float pitch) {
/* 1078 */     for (int i = 0; i < this.worldAccesses.size(); i++)
/*      */     {
/* 1080 */       ((IWorldAccess)this.worldAccesses.get(i)).playSoundToNearExcept(player, name, player.posX, player.posY, player.posZ, volume, pitch);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSoundEffect(double x, double y, double z, String soundName, float volume, float pitch) {
/* 1091 */     for (int i = 0; i < this.worldAccesses.size(); i++)
/*      */     {
/* 1093 */       ((IWorldAccess)this.worldAccesses.get(i)).playSound(soundName, x, y, z, volume, pitch);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSound(double x, double y, double z, String soundName, float volume, float pitch, boolean distanceDelay) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playRecord(BlockPos pos, String name) {
/* 1106 */     for (int i = 0; i < this.worldAccesses.size(); i++)
/*      */     {
/* 1108 */       ((IWorldAccess)this.worldAccesses.get(i)).playRecord(name, pos);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... p_175688_14_) {
/* 1114 */     spawnParticle(particleType.getParticleID(), particleType.getShouldIgnoreRange(), xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, p_175688_14_);
/*      */   }
/*      */ 
/*      */   
/*      */   public void spawnParticle(EnumParticleTypes particleType, boolean p_175682_2_, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... p_175682_15_) {
/* 1119 */     spawnParticle(particleType.getParticleID(), particleType.getShouldIgnoreRange() | p_175682_2_, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, p_175682_15_);
/*      */   }
/*      */ 
/*      */   
/*      */   private void spawnParticle(int particleID, boolean p_175720_2_, double xCood, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... p_175720_15_) {
/* 1124 */     for (int i = 0; i < this.worldAccesses.size(); i++)
/*      */     {
/* 1126 */       ((IWorldAccess)this.worldAccesses.get(i)).spawnParticle(particleID, p_175720_2_, xCood, yCoord, zCoord, xOffset, yOffset, zOffset, p_175720_15_);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addWeatherEffect(Entity entityIn) {
/* 1135 */     this.weatherEffects.add(entityIn);
/* 1136 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean spawnEntityInWorld(Entity entityIn) {
/* 1144 */     int i = MathHelper.floor_double(entityIn.posX / 16.0D);
/* 1145 */     int j = MathHelper.floor_double(entityIn.posZ / 16.0D);
/* 1146 */     boolean flag = entityIn.forceSpawn;
/*      */     
/* 1148 */     if (entityIn instanceof EntityPlayer)
/*      */     {
/* 1150 */       flag = true;
/*      */     }
/*      */     
/* 1153 */     if (!flag && !isChunkLoaded(i, j, true))
/*      */     {
/* 1155 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1159 */     if (entityIn instanceof EntityPlayer) {
/*      */       
/* 1161 */       EntityPlayer entityplayer = (EntityPlayer)entityIn;
/* 1162 */       this.playerEntities.add(entityplayer);
/* 1163 */       updateAllPlayersSleepingFlag();
/*      */     } 
/*      */     
/* 1166 */     getChunkFromChunkCoords(i, j).addEntity(entityIn);
/* 1167 */     this.loadedEntityList.add(entityIn);
/* 1168 */     onEntityAdded(entityIn);
/* 1169 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onEntityAdded(Entity entityIn) {
/* 1175 */     for (int i = 0; i < this.worldAccesses.size(); i++)
/*      */     {
/* 1177 */       ((IWorldAccess)this.worldAccesses.get(i)).onEntityAdded(entityIn);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onEntityRemoved(Entity entityIn) {
/* 1183 */     for (int i = 0; i < this.worldAccesses.size(); i++)
/*      */     {
/* 1185 */       ((IWorldAccess)this.worldAccesses.get(i)).onEntityRemoved(entityIn);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEntity(Entity entityIn) {
/* 1194 */     if (entityIn.riddenByEntity != null)
/*      */     {
/* 1196 */       entityIn.riddenByEntity.mountEntity(null);
/*      */     }
/*      */     
/* 1199 */     if (entityIn.ridingEntity != null)
/*      */     {
/* 1201 */       entityIn.mountEntity(null);
/*      */     }
/*      */     
/* 1204 */     entityIn.setDead();
/*      */     
/* 1206 */     if (entityIn instanceof EntityPlayer) {
/*      */       
/* 1208 */       this.playerEntities.remove(entityIn);
/* 1209 */       updateAllPlayersSleepingFlag();
/* 1210 */       onEntityRemoved(entityIn);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removePlayerEntityDangerously(Entity entityIn) {
/* 1219 */     entityIn.setDead();
/*      */     
/* 1221 */     if (entityIn instanceof EntityPlayer) {
/*      */       
/* 1223 */       this.playerEntities.remove(entityIn);
/* 1224 */       updateAllPlayersSleepingFlag();
/*      */     } 
/*      */     
/* 1227 */     int i = entityIn.chunkCoordX;
/* 1228 */     int j = entityIn.chunkCoordZ;
/*      */     
/* 1230 */     if (entityIn.addedToChunk && isChunkLoaded(i, j, true))
/*      */     {
/* 1232 */       getChunkFromChunkCoords(i, j).removeEntity(entityIn);
/*      */     }
/*      */     
/* 1235 */     this.loadedEntityList.remove(entityIn);
/* 1236 */     onEntityRemoved(entityIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addWorldAccess(IWorldAccess worldAccess) {
/* 1244 */     this.worldAccesses.add(worldAccess);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeWorldAccess(IWorldAccess worldAccess) {
/* 1252 */     this.worldAccesses.remove(worldAccess);
/*      */   }
/*      */ 
/*      */   
/*      */   public List<AxisAlignedBB> getCollidingBoundingBoxes(Entity entityIn, AxisAlignedBB bb) {
/* 1257 */     List<AxisAlignedBB> list = Lists.newArrayList();
/* 1258 */     int i = MathHelper.floor_double(bb.minX);
/* 1259 */     int j = MathHelper.floor_double(bb.maxX + 1.0D);
/* 1260 */     int k = MathHelper.floor_double(bb.minY);
/* 1261 */     int l = MathHelper.floor_double(bb.maxY + 1.0D);
/* 1262 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 1263 */     int j1 = MathHelper.floor_double(bb.maxZ + 1.0D);
/* 1264 */     WorldBorder worldborder = getWorldBorder();
/* 1265 */     boolean flag = entityIn.isOutsideBorder();
/* 1266 */     boolean flag1 = isInsideBorder(worldborder, entityIn);
/* 1267 */     IBlockState iblockstate = Blocks.stone.getDefaultState();
/* 1268 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 1270 */     for (int k1 = i; k1 < j; k1++) {
/*      */       
/* 1272 */       for (int l1 = i1; l1 < j1; l1++) {
/*      */         
/* 1274 */         if (isBlockLoaded((BlockPos)blockpos$mutableblockpos.func_181079_c(k1, 64, l1)))
/*      */         {
/* 1276 */           for (int i2 = k - 1; i2 < l; i2++) {
/*      */             
/* 1278 */             blockpos$mutableblockpos.func_181079_c(k1, i2, l1);
/*      */             
/* 1280 */             if (flag && flag1) {
/*      */               
/* 1282 */               entityIn.setOutsideBorder(false);
/*      */             }
/* 1284 */             else if (!flag && !flag1) {
/*      */               
/* 1286 */               entityIn.setOutsideBorder(true);
/*      */             } 
/*      */             
/* 1289 */             IBlockState iblockstate1 = iblockstate;
/*      */             
/* 1291 */             if (worldborder.contains((BlockPos)blockpos$mutableblockpos) || !flag1)
/*      */             {
/* 1293 */               iblockstate1 = getBlockState((BlockPos)blockpos$mutableblockpos);
/*      */             }
/*      */             
/* 1296 */             iblockstate1.getBlock().addCollisionBoxesToList(this, (BlockPos)blockpos$mutableblockpos, iblockstate1, bb, list, entityIn);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1302 */     double d0 = 0.25D;
/* 1303 */     List<Entity> list1 = getEntitiesWithinAABBExcludingEntity(entityIn, bb.expand(d0, d0, d0));
/*      */     
/* 1305 */     for (int j2 = 0; j2 < list1.size(); j2++) {
/*      */       
/* 1307 */       if (entityIn.riddenByEntity != list1 && entityIn.ridingEntity != list1) {
/*      */         
/* 1309 */         AxisAlignedBB axisalignedbb = ((Entity)list1.get(j2)).getCollisionBoundingBox();
/*      */         
/* 1311 */         if (axisalignedbb != null && axisalignedbb.intersectsWith(bb))
/*      */         {
/* 1313 */           list.add(axisalignedbb);
/*      */         }
/*      */         
/* 1316 */         axisalignedbb = entityIn.getCollisionBox(list1.get(j2));
/*      */         
/* 1318 */         if (axisalignedbb != null && axisalignedbb.intersectsWith(bb))
/*      */         {
/* 1320 */           list.add(axisalignedbb);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1325 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isInsideBorder(WorldBorder worldBorderIn, Entity entityIn) {
/* 1330 */     double d0 = worldBorderIn.minX();
/* 1331 */     double d1 = worldBorderIn.minZ();
/* 1332 */     double d2 = worldBorderIn.maxX();
/* 1333 */     double d3 = worldBorderIn.maxZ();
/*      */     
/* 1335 */     if (entityIn.isOutsideBorder()) {
/*      */       
/* 1337 */       d0++;
/* 1338 */       d1++;
/* 1339 */       d2--;
/* 1340 */       d3--;
/*      */     }
/*      */     else {
/*      */       
/* 1344 */       d0--;
/* 1345 */       d1--;
/* 1346 */       d2++;
/* 1347 */       d3++;
/*      */     } 
/*      */     
/* 1350 */     return (entityIn.posX > d0 && entityIn.posX < d2 && entityIn.posZ > d1 && entityIn.posZ < d3);
/*      */   }
/*      */ 
/*      */   
/*      */   public List<AxisAlignedBB> func_147461_a(AxisAlignedBB bb) {
/* 1355 */     List<AxisAlignedBB> list = Lists.newArrayList();
/* 1356 */     int i = MathHelper.floor_double(bb.minX);
/* 1357 */     int j = MathHelper.floor_double(bb.maxX + 1.0D);
/* 1358 */     int k = MathHelper.floor_double(bb.minY);
/* 1359 */     int l = MathHelper.floor_double(bb.maxY + 1.0D);
/* 1360 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 1361 */     int j1 = MathHelper.floor_double(bb.maxZ + 1.0D);
/* 1362 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 1364 */     for (int k1 = i; k1 < j; k1++) {
/*      */       
/* 1366 */       for (int l1 = i1; l1 < j1; l1++) {
/*      */         
/* 1368 */         if (isBlockLoaded((BlockPos)blockpos$mutableblockpos.func_181079_c(k1, 64, l1)))
/*      */         {
/* 1370 */           for (int i2 = k - 1; i2 < l; i2++) {
/*      */             IBlockState iblockstate;
/* 1372 */             blockpos$mutableblockpos.func_181079_c(k1, i2, l1);
/*      */ 
/*      */             
/* 1375 */             if (k1 >= -30000000 && k1 < 30000000 && l1 >= -30000000 && l1 < 30000000) {
/*      */               
/* 1377 */               iblockstate = getBlockState((BlockPos)blockpos$mutableblockpos);
/*      */             }
/*      */             else {
/*      */               
/* 1381 */               iblockstate = Blocks.bedrock.getDefaultState();
/*      */             } 
/*      */             
/* 1384 */             iblockstate.getBlock().addCollisionBoxesToList(this, (BlockPos)blockpos$mutableblockpos, iblockstate, bb, list, null);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1390 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int calculateSkylightSubtracted(float p_72967_1_) {
/* 1398 */     float f = getCelestialAngle(p_72967_1_);
/* 1399 */     float f1 = 1.0F - MathHelper.cos(f * 3.1415927F * 2.0F) * 2.0F + 0.5F;
/* 1400 */     f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
/* 1401 */     f1 = 1.0F - f1;
/* 1402 */     f1 = (float)(f1 * (1.0D - (getRainStrength(p_72967_1_) * 5.0F) / 16.0D));
/* 1403 */     f1 = (float)(f1 * (1.0D - (getThunderStrength(p_72967_1_) * 5.0F) / 16.0D));
/* 1404 */     f1 = 1.0F - f1;
/* 1405 */     return (int)(f1 * 11.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getSunBrightness(float p_72971_1_) {
/* 1413 */     float f = getCelestialAngle(p_72971_1_);
/* 1414 */     float f1 = 1.0F - MathHelper.cos(f * 3.1415927F * 2.0F) * 2.0F + 0.2F;
/* 1415 */     f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
/* 1416 */     f1 = 1.0F - f1;
/* 1417 */     f1 = (float)(f1 * (1.0D - (getRainStrength(p_72971_1_) * 5.0F) / 16.0D));
/* 1418 */     f1 = (float)(f1 * (1.0D - (getThunderStrength(p_72971_1_) * 5.0F) / 16.0D));
/* 1419 */     return f1 * 0.8F + 0.2F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getSkyColor(Entity entityIn, float partialTicks) {
/* 1427 */     float f = getCelestialAngle(partialTicks);
/* 1428 */     float f1 = MathHelper.cos(f * 3.1415927F * 2.0F) * 2.0F + 0.5F;
/* 1429 */     f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
/* 1430 */     int i = MathHelper.floor_double(entityIn.posX);
/* 1431 */     int j = MathHelper.floor_double(entityIn.posY);
/* 1432 */     int k = MathHelper.floor_double(entityIn.posZ);
/* 1433 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 1434 */     BiomeGenBase biomegenbase = getBiomeGenForCoords(blockpos);
/* 1435 */     float f2 = biomegenbase.getFloatTemperature(blockpos);
/* 1436 */     int l = biomegenbase.getSkyColorByTemp(f2);
/* 1437 */     float f3 = (l >> 16 & 0xFF) / 255.0F;
/* 1438 */     float f4 = (l >> 8 & 0xFF) / 255.0F;
/* 1439 */     float f5 = (l & 0xFF) / 255.0F;
/* 1440 */     f3 *= f1;
/* 1441 */     f4 *= f1;
/* 1442 */     f5 *= f1;
/* 1443 */     float f6 = getRainStrength(partialTicks);
/*      */     
/* 1445 */     if (f6 > 0.0F) {
/*      */       
/* 1447 */       float f7 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.6F;
/* 1448 */       float f8 = 1.0F - f6 * 0.75F;
/* 1449 */       f3 = f3 * f8 + f7 * (1.0F - f8);
/* 1450 */       f4 = f4 * f8 + f7 * (1.0F - f8);
/* 1451 */       f5 = f5 * f8 + f7 * (1.0F - f8);
/*      */     } 
/*      */     
/* 1454 */     float f10 = getThunderStrength(partialTicks);
/*      */     
/* 1456 */     if (f10 > 0.0F) {
/*      */       
/* 1458 */       float f11 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.2F;
/* 1459 */       float f9 = 1.0F - f10 * 0.75F;
/* 1460 */       f3 = f3 * f9 + f11 * (1.0F - f9);
/* 1461 */       f4 = f4 * f9 + f11 * (1.0F - f9);
/* 1462 */       f5 = f5 * f9 + f11 * (1.0F - f9);
/*      */     } 
/*      */     
/* 1465 */     if (this.lastLightningBolt > 0) {
/*      */       
/* 1467 */       float f12 = this.lastLightningBolt - partialTicks;
/*      */       
/* 1469 */       if (f12 > 1.0F)
/*      */       {
/* 1471 */         f12 = 1.0F;
/*      */       }
/*      */       
/* 1474 */       f12 *= 0.45F;
/* 1475 */       f3 = f3 * (1.0F - f12) + 0.8F * f12;
/* 1476 */       f4 = f4 * (1.0F - f12) + 0.8F * f12;
/* 1477 */       f5 = f5 * (1.0F - f12) + 1.0F * f12;
/*      */     } 
/*      */     
/* 1480 */     return new Vec3(f3, f4, f5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getCelestialAngle(float partialTicks) {
/* 1488 */     return this.provider.calculateCelestialAngle(this.worldInfo.getWorldTime(), partialTicks);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMoonPhase() {
/* 1493 */     return this.provider.getMoonPhase(this.worldInfo.getWorldTime());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getCurrentMoonPhaseFactor() {
/* 1501 */     return WorldProvider.moonPhaseFactors[this.provider.getMoonPhase(this.worldInfo.getWorldTime())];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getCelestialAngleRadians(float partialTicks) {
/* 1509 */     float f = getCelestialAngle(partialTicks);
/* 1510 */     return f * 3.1415927F * 2.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec3 getCloudColour(float partialTicks) {
/* 1515 */     float f = getCelestialAngle(partialTicks);
/* 1516 */     float f1 = MathHelper.cos(f * 3.1415927F * 2.0F) * 2.0F + 0.5F;
/* 1517 */     f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
/* 1518 */     float f2 = (float)(this.cloudColour >> 16L & 0xFFL) / 255.0F;
/* 1519 */     float f3 = (float)(this.cloudColour >> 8L & 0xFFL) / 255.0F;
/* 1520 */     float f4 = (float)(this.cloudColour & 0xFFL) / 255.0F;
/* 1521 */     float f5 = getRainStrength(partialTicks);
/*      */     
/* 1523 */     if (f5 > 0.0F) {
/*      */       
/* 1525 */       float f6 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.6F;
/* 1526 */       float f7 = 1.0F - f5 * 0.95F;
/* 1527 */       f2 = f2 * f7 + f6 * (1.0F - f7);
/* 1528 */       f3 = f3 * f7 + f6 * (1.0F - f7);
/* 1529 */       f4 = f4 * f7 + f6 * (1.0F - f7);
/*      */     } 
/*      */     
/* 1532 */     f2 *= f1 * 0.9F + 0.1F;
/* 1533 */     f3 *= f1 * 0.9F + 0.1F;
/* 1534 */     f4 *= f1 * 0.85F + 0.15F;
/* 1535 */     float f9 = getThunderStrength(partialTicks);
/*      */     
/* 1537 */     if (f9 > 0.0F) {
/*      */       
/* 1539 */       float f10 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.2F;
/* 1540 */       float f8 = 1.0F - f9 * 0.95F;
/* 1541 */       f2 = f2 * f8 + f10 * (1.0F - f8);
/* 1542 */       f3 = f3 * f8 + f10 * (1.0F - f8);
/* 1543 */       f4 = f4 * f8 + f10 * (1.0F - f8);
/*      */     } 
/*      */     
/* 1546 */     return new Vec3(f2, f3, f4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getFogColor(float partialTicks) {
/* 1554 */     float f = getCelestialAngle(partialTicks);
/* 1555 */     return this.provider.getFogColor(f, partialTicks);
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockPos getPrecipitationHeight(BlockPos pos) {
/* 1560 */     return getChunkFromBlockCoords(pos).getPrecipitationHeight(pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getTopSolidOrLiquidBlock(BlockPos pos) {
/* 1570 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*      */     
/*      */     BlockPos blockpos;
/*      */     
/* 1574 */     for (blockpos = new BlockPos(pos.getX(), chunk.getTopFilledSegment() + 16, pos.getZ()); blockpos.getY() >= 0; blockpos = blockpos1) {
/*      */       
/* 1576 */       BlockPos blockpos1 = blockpos.down();
/* 1577 */       Material material = chunk.getBlock(blockpos1).getMaterial();
/*      */       
/* 1579 */       if (material.blocksMovement() && material != Material.leaves) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1585 */     return blockpos;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getStarBrightness(float partialTicks) {
/* 1593 */     float f = getCelestialAngle(partialTicks);
/* 1594 */     float f1 = 1.0F - MathHelper.cos(f * 3.1415927F * 2.0F) * 2.0F + 0.25F;
/* 1595 */     f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
/* 1596 */     return f1 * f1 * 0.5F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void scheduleUpdate(BlockPos pos, Block blockIn, int delay) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateBlockTick(BlockPos pos, Block blockIn, int delay, int priority) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void scheduleBlockUpdate(BlockPos pos, Block blockIn, int delay, int priority) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateEntities() {
/* 1616 */     this.theProfiler.startSection("entities");
/* 1617 */     this.theProfiler.startSection("global");
/*      */     
/* 1619 */     for (int i = 0; i < this.weatherEffects.size(); i++) {
/*      */       
/* 1621 */       Entity entity = this.weatherEffects.get(i);
/*      */ 
/*      */       
/*      */       try {
/* 1625 */         entity.ticksExisted++;
/* 1626 */         entity.onUpdate();
/*      */       }
/* 1628 */       catch (Throwable throwable2) {
/*      */         
/* 1630 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable2, "Ticking entity");
/* 1631 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being ticked");
/*      */         
/* 1633 */         if (entity == null) {
/*      */           
/* 1635 */           crashreportcategory.addCrashSection("Entity", "~~NULL~~");
/*      */         }
/*      */         else {
/*      */           
/* 1639 */           entity.addEntityCrashInfo(crashreportcategory);
/*      */         } 
/*      */         
/* 1642 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/* 1645 */       if (entity.isDead)
/*      */       {
/* 1647 */         this.weatherEffects.remove(i--);
/*      */       }
/*      */     } 
/*      */     
/* 1651 */     this.theProfiler.endStartSection("remove");
/* 1652 */     this.loadedEntityList.removeAll(this.unloadedEntityList);
/*      */     
/* 1654 */     for (int k = 0; k < this.unloadedEntityList.size(); k++) {
/*      */       
/* 1656 */       Entity entity1 = this.unloadedEntityList.get(k);
/* 1657 */       int j = entity1.chunkCoordX;
/* 1658 */       int l1 = entity1.chunkCoordZ;
/*      */       
/* 1660 */       if (entity1.addedToChunk && isChunkLoaded(j, l1, true))
/*      */       {
/* 1662 */         getChunkFromChunkCoords(j, l1).removeEntity(entity1);
/*      */       }
/*      */     } 
/*      */     
/* 1666 */     for (int l = 0; l < this.unloadedEntityList.size(); l++)
/*      */     {
/* 1668 */       onEntityRemoved(this.unloadedEntityList.get(l));
/*      */     }
/*      */     
/* 1671 */     this.unloadedEntityList.clear();
/* 1672 */     this.theProfiler.endStartSection("regular");
/*      */     
/* 1674 */     for (int i1 = 0; i1 < this.loadedEntityList.size(); i1++) {
/*      */       
/* 1676 */       Entity entity2 = this.loadedEntityList.get(i1);
/*      */       
/* 1678 */       if (entity2.ridingEntity != null) {
/*      */         
/* 1680 */         if (!entity2.ridingEntity.isDead && entity2.ridingEntity.riddenByEntity == entity2) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/* 1685 */         entity2.ridingEntity.riddenByEntity = null;
/* 1686 */         entity2.ridingEntity = null;
/*      */       } 
/*      */       
/* 1689 */       this.theProfiler.startSection("tick");
/*      */       
/* 1691 */       if (!entity2.isDead) {
/*      */         
/*      */         try {
/*      */           
/* 1695 */           updateEntity(entity2);
/*      */         }
/* 1697 */         catch (Throwable throwable1) {
/*      */           
/* 1699 */           CrashReport crashreport1 = CrashReport.makeCrashReport(throwable1, "Ticking entity");
/* 1700 */           CrashReportCategory crashreportcategory2 = crashreport1.makeCategory("Entity being ticked");
/* 1701 */           entity2.addEntityCrashInfo(crashreportcategory2);
/* 1702 */           throw new ReportedException(crashreport1);
/*      */         } 
/*      */       }
/*      */       
/* 1706 */       this.theProfiler.endSection();
/* 1707 */       this.theProfiler.startSection("remove");
/*      */       
/* 1709 */       if (entity2.isDead) {
/*      */         
/* 1711 */         int k1 = entity2.chunkCoordX;
/* 1712 */         int i2 = entity2.chunkCoordZ;
/*      */         
/* 1714 */         if (entity2.addedToChunk && isChunkLoaded(k1, i2, true))
/*      */         {
/* 1716 */           getChunkFromChunkCoords(k1, i2).removeEntity(entity2);
/*      */         }
/*      */         
/* 1719 */         this.loadedEntityList.remove(i1--);
/* 1720 */         onEntityRemoved(entity2);
/*      */       } 
/*      */       
/* 1723 */       this.theProfiler.endSection();
/*      */       continue;
/*      */     } 
/* 1726 */     this.theProfiler.endStartSection("blockEntities");
/* 1727 */     this.processingLoadedTiles = true;
/* 1728 */     Iterator<TileEntity> iterator = this.tickableTileEntities.iterator();
/*      */     
/* 1730 */     while (iterator.hasNext()) {
/*      */       
/* 1732 */       TileEntity tileentity = iterator.next();
/*      */       
/* 1734 */       if (!tileentity.isInvalid() && tileentity.hasWorldObj()) {
/*      */         
/* 1736 */         BlockPos blockpos = tileentity.getPos();
/*      */         
/* 1738 */         if (isBlockLoaded(blockpos) && this.worldBorder.contains(blockpos)) {
/*      */           
/*      */           try {
/*      */             
/* 1742 */             ((ITickable)tileentity).update();
/*      */           }
/* 1744 */           catch (Throwable throwable) {
/*      */             
/* 1746 */             CrashReport crashreport2 = CrashReport.makeCrashReport(throwable, "Ticking block entity");
/* 1747 */             CrashReportCategory crashreportcategory1 = crashreport2.makeCategory("Block entity being ticked");
/* 1748 */             tileentity.addInfoToCrashReport(crashreportcategory1);
/* 1749 */             throw new ReportedException(crashreport2);
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/* 1754 */       if (tileentity.isInvalid()) {
/*      */         
/* 1756 */         iterator.remove();
/* 1757 */         this.loadedTileEntityList.remove(tileentity);
/*      */         
/* 1759 */         if (isBlockLoaded(tileentity.getPos()))
/*      */         {
/* 1761 */           getChunkFromBlockCoords(tileentity.getPos()).removeTileEntity(tileentity.getPos());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1766 */     this.processingLoadedTiles = false;
/*      */     
/* 1768 */     if (!this.tileEntitiesToBeRemoved.isEmpty()) {
/*      */       
/* 1770 */       this.tickableTileEntities.removeAll(this.tileEntitiesToBeRemoved);
/* 1771 */       this.loadedTileEntityList.removeAll(this.tileEntitiesToBeRemoved);
/* 1772 */       this.tileEntitiesToBeRemoved.clear();
/*      */     } 
/*      */     
/* 1775 */     this.theProfiler.endStartSection("pendingBlockEntities");
/*      */     
/* 1777 */     if (!this.addedTileEntityList.isEmpty()) {
/*      */       
/* 1779 */       for (int j1 = 0; j1 < this.addedTileEntityList.size(); j1++) {
/*      */         
/* 1781 */         TileEntity tileentity1 = this.addedTileEntityList.get(j1);
/*      */         
/* 1783 */         if (!tileentity1.isInvalid()) {
/*      */           
/* 1785 */           if (!this.loadedTileEntityList.contains(tileentity1))
/*      */           {
/* 1787 */             addTileEntity(tileentity1);
/*      */           }
/*      */           
/* 1790 */           if (isBlockLoaded(tileentity1.getPos()))
/*      */           {
/* 1792 */             getChunkFromBlockCoords(tileentity1.getPos()).addTileEntity(tileentity1.getPos(), tileentity1);
/*      */           }
/*      */           
/* 1795 */           markBlockForUpdate(tileentity1.getPos());
/*      */         } 
/*      */       } 
/*      */       
/* 1799 */       this.addedTileEntityList.clear();
/*      */     } 
/*      */     
/* 1802 */     this.theProfiler.endSection();
/* 1803 */     this.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addTileEntity(TileEntity tile) {
/* 1808 */     boolean flag = this.loadedTileEntityList.add(tile);
/*      */     
/* 1810 */     if (flag && tile instanceof ITickable)
/*      */     {
/* 1812 */       this.tickableTileEntities.add(tile);
/*      */     }
/*      */     
/* 1815 */     return flag;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addTileEntities(Collection<TileEntity> tileEntityCollection) {
/* 1820 */     if (this.processingLoadedTiles) {
/*      */       
/* 1822 */       this.addedTileEntityList.addAll(tileEntityCollection);
/*      */     }
/*      */     else {
/*      */       
/* 1826 */       for (TileEntity tileentity : tileEntityCollection) {
/*      */         
/* 1828 */         this.loadedTileEntityList.add(tileentity);
/*      */         
/* 1830 */         if (tileentity instanceof ITickable)
/*      */         {
/* 1832 */           this.tickableTileEntities.add(tileentity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateEntity(Entity ent) {
/* 1843 */     updateEntityWithOptionalForce(ent, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateEntityWithOptionalForce(Entity entityIn, boolean forceUpdate) {
/* 1852 */     int i = MathHelper.floor_double(entityIn.posX);
/* 1853 */     int j = MathHelper.floor_double(entityIn.posZ);
/* 1854 */     int k = 32;
/*      */     
/* 1856 */     if (!forceUpdate || isAreaLoaded(i - k, 0, j - k, i + k, 0, j + k, true)) {
/*      */       
/* 1858 */       entityIn.lastTickPosX = entityIn.posX;
/* 1859 */       entityIn.lastTickPosY = entityIn.posY;
/* 1860 */       entityIn.lastTickPosZ = entityIn.posZ;
/* 1861 */       entityIn.prevRotationYaw = entityIn.rotationYaw;
/* 1862 */       entityIn.prevRotationPitch = entityIn.rotationPitch;
/*      */       
/* 1864 */       if (forceUpdate && entityIn.addedToChunk) {
/*      */         
/* 1866 */         entityIn.ticksExisted++;
/*      */         
/* 1868 */         if (entityIn.ridingEntity != null) {
/*      */           
/* 1870 */           entityIn.updateRidden();
/*      */         }
/*      */         else {
/*      */           
/* 1874 */           entityIn.onUpdate();
/*      */         } 
/*      */       } 
/*      */       
/* 1878 */       this.theProfiler.startSection("chunkCheck");
/*      */       
/* 1880 */       if (Double.isNaN(entityIn.posX) || Double.isInfinite(entityIn.posX))
/*      */       {
/* 1882 */         entityIn.posX = entityIn.lastTickPosX;
/*      */       }
/*      */       
/* 1885 */       if (Double.isNaN(entityIn.posY) || Double.isInfinite(entityIn.posY))
/*      */       {
/* 1887 */         entityIn.posY = entityIn.lastTickPosY;
/*      */       }
/*      */       
/* 1890 */       if (Double.isNaN(entityIn.posZ) || Double.isInfinite(entityIn.posZ))
/*      */       {
/* 1892 */         entityIn.posZ = entityIn.lastTickPosZ;
/*      */       }
/*      */       
/* 1895 */       if (Double.isNaN(entityIn.rotationPitch) || Double.isInfinite(entityIn.rotationPitch))
/*      */       {
/* 1897 */         entityIn.rotationPitch = entityIn.prevRotationPitch;
/*      */       }
/*      */       
/* 1900 */       if (Double.isNaN(entityIn.rotationYaw) || Double.isInfinite(entityIn.rotationYaw))
/*      */       {
/* 1902 */         entityIn.rotationYaw = entityIn.prevRotationYaw;
/*      */       }
/*      */       
/* 1905 */       int l = MathHelper.floor_double(entityIn.posX / 16.0D);
/* 1906 */       int i1 = MathHelper.floor_double(entityIn.posY / 16.0D);
/* 1907 */       int j1 = MathHelper.floor_double(entityIn.posZ / 16.0D);
/*      */       
/* 1909 */       if (!entityIn.addedToChunk || entityIn.chunkCoordX != l || entityIn.chunkCoordY != i1 || entityIn.chunkCoordZ != j1) {
/*      */         
/* 1911 */         if (entityIn.addedToChunk && isChunkLoaded(entityIn.chunkCoordX, entityIn.chunkCoordZ, true))
/*      */         {
/* 1913 */           getChunkFromChunkCoords(entityIn.chunkCoordX, entityIn.chunkCoordZ).removeEntityAtIndex(entityIn, entityIn.chunkCoordY);
/*      */         }
/*      */         
/* 1916 */         if (isChunkLoaded(l, j1, true)) {
/*      */           
/* 1918 */           entityIn.addedToChunk = true;
/* 1919 */           getChunkFromChunkCoords(l, j1).addEntity(entityIn);
/*      */         }
/*      */         else {
/*      */           
/* 1923 */           entityIn.addedToChunk = false;
/*      */         } 
/*      */       } 
/*      */       
/* 1927 */       this.theProfiler.endSection();
/*      */       
/* 1929 */       if (forceUpdate && entityIn.addedToChunk && entityIn.riddenByEntity != null)
/*      */       {
/* 1931 */         if (!entityIn.riddenByEntity.isDead && entityIn.riddenByEntity.ridingEntity == entityIn) {
/*      */           
/* 1933 */           updateEntity(entityIn.riddenByEntity);
/*      */         }
/*      */         else {
/*      */           
/* 1937 */           entityIn.riddenByEntity.ridingEntity = null;
/* 1938 */           entityIn.riddenByEntity = null;
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkNoEntityCollision(AxisAlignedBB bb) {
/* 1949 */     return checkNoEntityCollision(bb, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkNoEntityCollision(AxisAlignedBB bb, Entity entityIn) {
/* 1957 */     List<Entity> list = getEntitiesWithinAABBExcludingEntity(null, bb);
/*      */     
/* 1959 */     for (int i = 0; i < list.size(); i++) {
/*      */       
/* 1961 */       Entity entity = list.get(i);
/*      */       
/* 1963 */       if (!entity.isDead && entity.preventEntitySpawning && entity != entityIn && (entityIn == null || (entityIn.ridingEntity != entity && entityIn.riddenByEntity != entity)))
/*      */       {
/* 1965 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1969 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkBlockCollision(AxisAlignedBB bb) {
/* 1977 */     int i = MathHelper.floor_double(bb.minX);
/* 1978 */     int j = MathHelper.floor_double(bb.maxX);
/* 1979 */     int k = MathHelper.floor_double(bb.minY);
/* 1980 */     int l = MathHelper.floor_double(bb.maxY);
/* 1981 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 1982 */     int j1 = MathHelper.floor_double(bb.maxZ);
/* 1983 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 1985 */     for (int k1 = i; k1 <= j; k1++) {
/*      */       
/* 1987 */       for (int l1 = k; l1 <= l; l1++) {
/*      */         
/* 1989 */         for (int i2 = i1; i2 <= j1; i2++) {
/*      */           
/* 1991 */           Block block = getBlockState((BlockPos)blockpos$mutableblockpos.func_181079_c(k1, l1, i2)).getBlock();
/*      */           
/* 1993 */           if (block.getMaterial() != Material.air)
/*      */           {
/* 1995 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2001 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAnyLiquid(AxisAlignedBB bb) {
/* 2009 */     int i = MathHelper.floor_double(bb.minX);
/* 2010 */     int j = MathHelper.floor_double(bb.maxX);
/* 2011 */     int k = MathHelper.floor_double(bb.minY);
/* 2012 */     int l = MathHelper.floor_double(bb.maxY);
/* 2013 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 2014 */     int j1 = MathHelper.floor_double(bb.maxZ);
/* 2015 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 2017 */     for (int k1 = i; k1 <= j; k1++) {
/*      */       
/* 2019 */       for (int l1 = k; l1 <= l; l1++) {
/*      */         
/* 2021 */         for (int i2 = i1; i2 <= j1; i2++) {
/*      */           
/* 2023 */           Block block = getBlockState((BlockPos)blockpos$mutableblockpos.func_181079_c(k1, l1, i2)).getBlock();
/*      */           
/* 2025 */           if (block.getMaterial().isLiquid())
/*      */           {
/* 2027 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2033 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFlammableWithin(AxisAlignedBB bb) {
/* 2038 */     int i = MathHelper.floor_double(bb.minX);
/* 2039 */     int j = MathHelper.floor_double(bb.maxX + 1.0D);
/* 2040 */     int k = MathHelper.floor_double(bb.minY);
/* 2041 */     int l = MathHelper.floor_double(bb.maxY + 1.0D);
/* 2042 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 2043 */     int j1 = MathHelper.floor_double(bb.maxZ + 1.0D);
/*      */     
/* 2045 */     if (isAreaLoaded(i, k, i1, j, l, j1, true)) {
/*      */       
/* 2047 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */       
/* 2049 */       for (int k1 = i; k1 < j; k1++) {
/*      */         
/* 2051 */         for (int l1 = k; l1 < l; l1++) {
/*      */           
/* 2053 */           for (int i2 = i1; i2 < j1; i2++) {
/*      */             
/* 2055 */             Block block = getBlockState((BlockPos)blockpos$mutableblockpos.func_181079_c(k1, l1, i2)).getBlock();
/*      */             
/* 2057 */             if (block == Blocks.fire || block == Blocks.flowing_lava || block == Blocks.lava)
/*      */             {
/* 2059 */               return true;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2066 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleMaterialAcceleration(AxisAlignedBB bb, Material materialIn, Entity entityIn) {
/* 2074 */     int i = MathHelper.floor_double(bb.minX);
/* 2075 */     int j = MathHelper.floor_double(bb.maxX + 1.0D);
/* 2076 */     int k = MathHelper.floor_double(bb.minY);
/* 2077 */     int l = MathHelper.floor_double(bb.maxY + 1.0D);
/* 2078 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 2079 */     int j1 = MathHelper.floor_double(bb.maxZ + 1.0D);
/*      */     
/* 2081 */     if (!isAreaLoaded(i, k, i1, j, l, j1, true))
/*      */     {
/* 2083 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2087 */     boolean flag = false;
/* 2088 */     Vec3 vec3 = new Vec3(0.0D, 0.0D, 0.0D);
/* 2089 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 2091 */     for (int k1 = i; k1 < j; k1++) {
/*      */       
/* 2093 */       for (int l1 = k; l1 < l; l1++) {
/*      */         
/* 2095 */         for (int i2 = i1; i2 < j1; i2++) {
/*      */           
/* 2097 */           blockpos$mutableblockpos.func_181079_c(k1, l1, i2);
/* 2098 */           IBlockState iblockstate = getBlockState((BlockPos)blockpos$mutableblockpos);
/* 2099 */           Block block = iblockstate.getBlock();
/*      */           
/* 2101 */           if (block.getMaterial() == materialIn) {
/*      */             
/* 2103 */             double d0 = ((l1 + 1) - BlockLiquid.getLiquidHeightPercent(((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue()));
/*      */             
/* 2105 */             if (l >= d0) {
/*      */               
/* 2107 */               flag = true;
/* 2108 */               vec3 = block.modifyAcceleration(this, (BlockPos)blockpos$mutableblockpos, entityIn, vec3);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2115 */     if (vec3.lengthVector() > 0.0D && entityIn.isPushedByWater()) {
/*      */       
/* 2117 */       vec3 = vec3.normalize();
/* 2118 */       double d1 = 0.014D;
/* 2119 */       entityIn.motionX += vec3.xCoord * d1;
/* 2120 */       entityIn.motionY += vec3.yCoord * d1;
/* 2121 */       entityIn.motionZ += vec3.zCoord * d1;
/*      */     } 
/*      */     
/* 2124 */     return flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMaterialInBB(AxisAlignedBB bb, Material materialIn) {
/* 2133 */     int i = MathHelper.floor_double(bb.minX);
/* 2134 */     int j = MathHelper.floor_double(bb.maxX + 1.0D);
/* 2135 */     int k = MathHelper.floor_double(bb.minY);
/* 2136 */     int l = MathHelper.floor_double(bb.maxY + 1.0D);
/* 2137 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 2138 */     int j1 = MathHelper.floor_double(bb.maxZ + 1.0D);
/* 2139 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 2141 */     for (int k1 = i; k1 < j; k1++) {
/*      */       
/* 2143 */       for (int l1 = k; l1 < l; l1++) {
/*      */         
/* 2145 */         for (int i2 = i1; i2 < j1; i2++) {
/*      */           
/* 2147 */           if (getBlockState((BlockPos)blockpos$mutableblockpos.func_181079_c(k1, l1, i2)).getBlock().getMaterial() == materialIn)
/*      */           {
/* 2149 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2155 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAABBInMaterial(AxisAlignedBB bb, Material materialIn) {
/* 2163 */     int i = MathHelper.floor_double(bb.minX);
/* 2164 */     int j = MathHelper.floor_double(bb.maxX + 1.0D);
/* 2165 */     int k = MathHelper.floor_double(bb.minY);
/* 2166 */     int l = MathHelper.floor_double(bb.maxY + 1.0D);
/* 2167 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 2168 */     int j1 = MathHelper.floor_double(bb.maxZ + 1.0D);
/* 2169 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 2171 */     for (int k1 = i; k1 < j; k1++) {
/*      */       
/* 2173 */       for (int l1 = k; l1 < l; l1++) {
/*      */         
/* 2175 */         for (int i2 = i1; i2 < j1; i2++) {
/*      */           
/* 2177 */           IBlockState iblockstate = getBlockState((BlockPos)blockpos$mutableblockpos.func_181079_c(k1, l1, i2));
/* 2178 */           Block block = iblockstate.getBlock();
/*      */           
/* 2180 */           if (block.getMaterial() == materialIn) {
/*      */             
/* 2182 */             int j2 = ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue();
/* 2183 */             double d0 = (l1 + 1);
/*      */             
/* 2185 */             if (j2 < 8)
/*      */             {
/* 2187 */               d0 = (l1 + 1) - j2 / 8.0D;
/*      */             }
/*      */             
/* 2190 */             if (d0 >= bb.minY)
/*      */             {
/* 2192 */               return true;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2199 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Explosion createExplosion(Entity entityIn, double x, double y, double z, float strength, boolean isSmoking) {
/* 2207 */     return newExplosion(entityIn, x, y, z, strength, false, isSmoking);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Explosion newExplosion(Entity entityIn, double x, double y, double z, float strength, boolean isFlaming, boolean isSmoking) {
/* 2215 */     Explosion explosion = new Explosion(this, entityIn, x, y, z, strength, isFlaming, isSmoking);
/* 2216 */     explosion.doExplosionA();
/* 2217 */     explosion.doExplosionB(true);
/* 2218 */     return explosion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getBlockDensity(Vec3 vec, AxisAlignedBB bb) {
/* 2226 */     double d0 = 1.0D / ((bb.maxX - bb.minX) * 2.0D + 1.0D);
/* 2227 */     double d1 = 1.0D / ((bb.maxY - bb.minY) * 2.0D + 1.0D);
/* 2228 */     double d2 = 1.0D / ((bb.maxZ - bb.minZ) * 2.0D + 1.0D);
/* 2229 */     double d3 = (1.0D - Math.floor(1.0D / d0) * d0) / 2.0D;
/* 2230 */     double d4 = (1.0D - Math.floor(1.0D / d2) * d2) / 2.0D;
/*      */     
/* 2232 */     if (d0 >= 0.0D && d1 >= 0.0D && d2 >= 0.0D) {
/*      */       
/* 2234 */       int i = 0;
/* 2235 */       int j = 0;
/*      */       float f;
/* 2237 */       for (f = 0.0F; f <= 1.0F; f = (float)(f + d0)) {
/*      */         float f1;
/* 2239 */         for (f1 = 0.0F; f1 <= 1.0F; f1 = (float)(f1 + d1)) {
/*      */           float f2;
/* 2241 */           for (f2 = 0.0F; f2 <= 1.0F; f2 = (float)(f2 + d2)) {
/*      */             
/* 2243 */             double d5 = bb.minX + (bb.maxX - bb.minX) * f;
/* 2244 */             double d6 = bb.minY + (bb.maxY - bb.minY) * f1;
/* 2245 */             double d7 = bb.minZ + (bb.maxZ - bb.minZ) * f2;
/*      */             
/* 2247 */             if (rayTraceBlocks(new Vec3(d5 + d3, d6, d7 + d4), vec) == null)
/*      */             {
/* 2249 */               i++;
/*      */             }
/*      */             
/* 2252 */             j++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2257 */       return i / j;
/*      */     } 
/*      */ 
/*      */     
/* 2261 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean extinguishFire(EntityPlayer player, BlockPos pos, EnumFacing side) {
/* 2274 */     pos = pos.offset(side);
/*      */     
/* 2276 */     if (getBlockState(pos).getBlock() == Blocks.fire) {
/*      */       
/* 2278 */       playAuxSFXAtEntity(player, 1004, pos, 0);
/* 2279 */       setBlockToAir(pos);
/* 2280 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 2284 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDebugLoadedEntities() {
/* 2293 */     return "All: " + this.loadedEntityList.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getProviderName() {
/* 2301 */     return this.chunkProvider.makeString();
/*      */   }
/*      */ 
/*      */   
/*      */   public TileEntity getTileEntity(BlockPos pos) {
/* 2306 */     if (!isValid(pos))
/*      */     {
/* 2308 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 2312 */     TileEntity tileentity = null;
/*      */     
/* 2314 */     if (this.processingLoadedTiles)
/*      */     {
/* 2316 */       for (int i = 0; i < this.addedTileEntityList.size(); i++) {
/*      */         
/* 2318 */         TileEntity tileentity1 = this.addedTileEntityList.get(i);
/*      */         
/* 2320 */         if (!tileentity1.isInvalid() && tileentity1.getPos().equals(pos)) {
/*      */           
/* 2322 */           tileentity = tileentity1;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 2328 */     if (tileentity == null)
/*      */     {
/* 2330 */       tileentity = getChunkFromBlockCoords(pos).getTileEntity(pos, Chunk.EnumCreateEntityType.IMMEDIATE);
/*      */     }
/*      */     
/* 2333 */     if (tileentity == null)
/*      */     {
/* 2335 */       for (int j = 0; j < this.addedTileEntityList.size(); j++) {
/*      */         
/* 2337 */         TileEntity tileentity2 = this.addedTileEntityList.get(j);
/*      */         
/* 2339 */         if (!tileentity2.isInvalid() && tileentity2.getPos().equals(pos)) {
/*      */           
/* 2341 */           tileentity = tileentity2;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 2347 */     return tileentity;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTileEntity(BlockPos pos, TileEntity tileEntityIn) {
/* 2353 */     if (tileEntityIn != null && !tileEntityIn.isInvalid())
/*      */     {
/* 2355 */       if (this.processingLoadedTiles) {
/*      */         
/* 2357 */         tileEntityIn.setPos(pos);
/* 2358 */         Iterator<TileEntity> iterator = this.addedTileEntityList.iterator();
/*      */         
/* 2360 */         while (iterator.hasNext()) {
/*      */           
/* 2362 */           TileEntity tileentity = iterator.next();
/*      */           
/* 2364 */           if (tileentity.getPos().equals(pos)) {
/*      */             
/* 2366 */             tileentity.invalidate();
/* 2367 */             iterator.remove();
/*      */           } 
/*      */         } 
/*      */         
/* 2371 */         this.addedTileEntityList.add(tileEntityIn);
/*      */       }
/*      */       else {
/*      */         
/* 2375 */         addTileEntity(tileEntityIn);
/* 2376 */         getChunkFromBlockCoords(pos).addTileEntity(pos, tileEntityIn);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeTileEntity(BlockPos pos) {
/* 2383 */     TileEntity tileentity = getTileEntity(pos);
/*      */     
/* 2385 */     if (tileentity != null && this.processingLoadedTiles) {
/*      */       
/* 2387 */       tileentity.invalidate();
/* 2388 */       this.addedTileEntityList.remove(tileentity);
/*      */     }
/*      */     else {
/*      */       
/* 2392 */       if (tileentity != null) {
/*      */         
/* 2394 */         this.addedTileEntityList.remove(tileentity);
/* 2395 */         this.loadedTileEntityList.remove(tileentity);
/* 2396 */         this.tickableTileEntities.remove(tileentity);
/*      */       } 
/*      */       
/* 2399 */       getChunkFromBlockCoords(pos).removeTileEntity(pos);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void markTileEntityForRemoval(TileEntity tileEntityIn) {
/* 2408 */     this.tileEntitiesToBeRemoved.add(tileEntityIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockFullCube(BlockPos pos) {
/* 2413 */     IBlockState iblockstate = getBlockState(pos);
/* 2414 */     AxisAlignedBB axisalignedbb = iblockstate.getBlock().getCollisionBoundingBox(this, pos, iblockstate);
/* 2415 */     return (axisalignedbb != null && axisalignedbb.getAverageEdgeLength() >= 1.0D);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean doesBlockHaveSolidTopSurface(IBlockAccess blockAccess, BlockPos pos) {
/* 2420 */     IBlockState iblockstate = blockAccess.getBlockState(pos);
/* 2421 */     Block block = iblockstate.getBlock();
/* 2422 */     return (block.getMaterial().isOpaque() && block.isFullCube()) ? true : ((block instanceof BlockStairs) ? ((iblockstate.getValue((IProperty)BlockStairs.HALF) == BlockStairs.EnumHalf.TOP)) : ((block instanceof BlockSlab) ? ((iblockstate.getValue((IProperty)BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP)) : ((block instanceof net.minecraft.block.BlockHopper) ? true : ((block instanceof BlockSnow) ? ((((Integer)iblockstate.getValue((IProperty)BlockSnow.LAYERS)).intValue() == 7)) : false))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBlockNormalCube(BlockPos pos, boolean _default) {
/* 2430 */     if (!isValid(pos))
/*      */     {
/* 2432 */       return _default;
/*      */     }
/*      */ 
/*      */     
/* 2436 */     Chunk chunk = this.chunkProvider.provideChunk(pos);
/*      */     
/* 2438 */     if (chunk.isEmpty())
/*      */     {
/* 2440 */       return _default;
/*      */     }
/*      */ 
/*      */     
/* 2444 */     Block block = getBlockState(pos).getBlock();
/* 2445 */     return (block.getMaterial().isOpaque() && block.isFullCube());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void calculateInitialSkylight() {
/* 2455 */     int i = calculateSkylightSubtracted(1.0F);
/*      */     
/* 2457 */     if (i != this.skylightSubtracted)
/*      */     {
/* 2459 */       this.skylightSubtracted = i;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllowedSpawnTypes(boolean hostile, boolean peaceful) {
/* 2468 */     this.spawnHostileMobs = hostile;
/* 2469 */     this.spawnPeacefulMobs = peaceful;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void tick() {
/* 2477 */     updateWeather();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void calculateInitialWeather() {
/* 2485 */     if (this.worldInfo.isRaining()) {
/*      */       
/* 2487 */       this.rainingStrength = 1.0F;
/*      */       
/* 2489 */       if (this.worldInfo.isThundering())
/*      */       {
/* 2491 */         this.thunderingStrength = 1.0F;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateWeather() {
/* 2501 */     if (!this.provider.getHasNoSky())
/*      */     {
/* 2503 */       if (!this.isRemote) {
/*      */         
/* 2505 */         int i = this.worldInfo.getCleanWeatherTime();
/*      */         
/* 2507 */         if (i > 0) {
/*      */           
/* 2509 */           i--;
/* 2510 */           this.worldInfo.setCleanWeatherTime(i);
/* 2511 */           this.worldInfo.setThunderTime(this.worldInfo.isThundering() ? 1 : 2);
/* 2512 */           this.worldInfo.setRainTime(this.worldInfo.isRaining() ? 1 : 2);
/*      */         } 
/*      */         
/* 2515 */         int j = this.worldInfo.getThunderTime();
/*      */         
/* 2517 */         if (j <= 0) {
/*      */           
/* 2519 */           if (this.worldInfo.isThundering())
/*      */           {
/* 2521 */             this.worldInfo.setThunderTime(this.rand.nextInt(12000) + 3600);
/*      */           }
/*      */           else
/*      */           {
/* 2525 */             this.worldInfo.setThunderTime(this.rand.nextInt(168000) + 12000);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 2530 */           j--;
/* 2531 */           this.worldInfo.setThunderTime(j);
/*      */           
/* 2533 */           if (j <= 0)
/*      */           {
/* 2535 */             this.worldInfo.setThundering(!this.worldInfo.isThundering());
/*      */           }
/*      */         } 
/*      */         
/* 2539 */         this.prevThunderingStrength = this.thunderingStrength;
/*      */         
/* 2541 */         if (this.worldInfo.isThundering()) {
/*      */           
/* 2543 */           this.thunderingStrength = (float)(this.thunderingStrength + 0.01D);
/*      */         }
/*      */         else {
/*      */           
/* 2547 */           this.thunderingStrength = (float)(this.thunderingStrength - 0.01D);
/*      */         } 
/*      */         
/* 2550 */         this.thunderingStrength = MathHelper.clamp_float(this.thunderingStrength, 0.0F, 1.0F);
/* 2551 */         int k = this.worldInfo.getRainTime();
/*      */         
/* 2553 */         if (k <= 0) {
/*      */           
/* 2555 */           if (this.worldInfo.isRaining())
/*      */           {
/* 2557 */             this.worldInfo.setRainTime(this.rand.nextInt(12000) + 12000);
/*      */           }
/*      */           else
/*      */           {
/* 2561 */             this.worldInfo.setRainTime(this.rand.nextInt(168000) + 12000);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 2566 */           k--;
/* 2567 */           this.worldInfo.setRainTime(k);
/*      */           
/* 2569 */           if (k <= 0)
/*      */           {
/* 2571 */             this.worldInfo.setRaining(!this.worldInfo.isRaining());
/*      */           }
/*      */         } 
/*      */         
/* 2575 */         this.prevRainingStrength = this.rainingStrength;
/*      */         
/* 2577 */         if (this.worldInfo.isRaining()) {
/*      */           
/* 2579 */           this.rainingStrength = (float)(this.rainingStrength + 0.01D);
/*      */         }
/*      */         else {
/*      */           
/* 2583 */           this.rainingStrength = (float)(this.rainingStrength - 0.01D);
/*      */         } 
/*      */         
/* 2586 */         this.rainingStrength = MathHelper.clamp_float(this.rainingStrength, 0.0F, 1.0F);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setActivePlayerChunksAndCheckLight() {
/* 2593 */     this.activeChunkSet.clear();
/* 2594 */     this.theProfiler.startSection("buildList");
/*      */     
/* 2596 */     for (int i = 0; i < this.playerEntities.size(); i++) {
/*      */       
/* 2598 */       EntityPlayer entityplayer = this.playerEntities.get(i);
/* 2599 */       int j = MathHelper.floor_double(entityplayer.posX / 16.0D);
/* 2600 */       int k = MathHelper.floor_double(entityplayer.posZ / 16.0D);
/* 2601 */       int l = getRenderDistanceChunks();
/*      */       
/* 2603 */       for (int i1 = -l; i1 <= l; i1++) {
/*      */         
/* 2605 */         for (int j1 = -l; j1 <= l; j1++)
/*      */         {
/* 2607 */           this.activeChunkSet.add(new ChunkCoordIntPair(i1 + j, j1 + k));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2612 */     this.theProfiler.endSection();
/*      */     
/* 2614 */     if (this.ambientTickCountdown > 0)
/*      */     {
/* 2616 */       this.ambientTickCountdown--;
/*      */     }
/*      */     
/* 2619 */     this.theProfiler.startSection("playerCheckLight");
/*      */     
/* 2621 */     if (!this.playerEntities.isEmpty()) {
/*      */       
/* 2623 */       int k1 = this.rand.nextInt(this.playerEntities.size());
/* 2624 */       EntityPlayer entityplayer1 = this.playerEntities.get(k1);
/* 2625 */       int l1 = MathHelper.floor_double(entityplayer1.posX) + this.rand.nextInt(11) - 5;
/* 2626 */       int i2 = MathHelper.floor_double(entityplayer1.posY) + this.rand.nextInt(11) - 5;
/* 2627 */       int j2 = MathHelper.floor_double(entityplayer1.posZ) + this.rand.nextInt(11) - 5;
/* 2628 */       checkLight(new BlockPos(l1, i2, j2));
/*      */     } 
/*      */     
/* 2631 */     this.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   protected abstract int getRenderDistanceChunks();
/*      */   
/*      */   protected void playMoodSoundAndCheckLight(int p_147467_1_, int p_147467_2_, Chunk chunkIn) {
/* 2638 */     this.theProfiler.endStartSection("moodSound");
/*      */     
/* 2640 */     if (this.ambientTickCountdown == 0 && !this.isRemote) {
/*      */       
/* 2642 */       this.updateLCG = this.updateLCG * 3 + 1013904223;
/* 2643 */       int i = this.updateLCG >> 2;
/* 2644 */       int j = i & 0xF;
/* 2645 */       int k = i >> 8 & 0xF;
/* 2646 */       int l = i >> 16 & 0xFF;
/* 2647 */       BlockPos blockpos = new BlockPos(j, l, k);
/* 2648 */       Block block = chunkIn.getBlock(blockpos);
/* 2649 */       j += p_147467_1_;
/* 2650 */       k += p_147467_2_;
/*      */       
/* 2652 */       if (block.getMaterial() == Material.air && getLight(blockpos) <= this.rand.nextInt(8) && getLightFor(EnumSkyBlock.SKY, blockpos) <= 0) {
/*      */         
/* 2654 */         EntityPlayer entityplayer = getClosestPlayer(j + 0.5D, l + 0.5D, k + 0.5D, 8.0D);
/*      */         
/* 2656 */         if (entityplayer != null && entityplayer.getDistanceSq(j + 0.5D, l + 0.5D, k + 0.5D) > 4.0D) {
/*      */           
/* 2658 */           playSoundEffect(j + 0.5D, l + 0.5D, k + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + this.rand.nextFloat() * 0.2F);
/* 2659 */           this.ambientTickCountdown = this.rand.nextInt(12000) + 6000;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2664 */     this.theProfiler.endStartSection("checkLight");
/* 2665 */     chunkIn.enqueueRelightChecks();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateBlocks() {
/* 2670 */     setActivePlayerChunksAndCheckLight();
/*      */   }
/*      */ 
/*      */   
/*      */   public void forceBlockUpdateTick(Block blockType, BlockPos pos, Random random) {
/* 2675 */     this.scheduledUpdatesAreImmediate = true;
/* 2676 */     blockType.updateTick(this, pos, getBlockState(pos), random);
/* 2677 */     this.scheduledUpdatesAreImmediate = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBlockFreezeWater(BlockPos pos) {
/* 2682 */     return canBlockFreeze(pos, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBlockFreezeNoWater(BlockPos pos) {
/* 2687 */     return canBlockFreeze(pos, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBlockFreeze(BlockPos pos, boolean noWaterAdj) {
/* 2699 */     BiomeGenBase biomegenbase = getBiomeGenForCoords(pos);
/* 2700 */     float f = biomegenbase.getFloatTemperature(pos);
/*      */     
/* 2702 */     if (f > 0.15F)
/*      */     {
/* 2704 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2708 */     if (pos.getY() >= 0 && pos.getY() < 256 && getLightFor(EnumSkyBlock.BLOCK, pos) < 10) {
/*      */       
/* 2710 */       IBlockState iblockstate = getBlockState(pos);
/* 2711 */       Block block = iblockstate.getBlock();
/*      */       
/* 2713 */       if ((block == Blocks.water || block == Blocks.flowing_water) && ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue() == 0) {
/*      */         
/* 2715 */         if (!noWaterAdj)
/*      */         {
/* 2717 */           return true;
/*      */         }
/*      */         
/* 2720 */         boolean flag = (isWater(pos.west()) && isWater(pos.east()) && isWater(pos.north()) && isWater(pos.south()));
/*      */         
/* 2722 */         if (!flag)
/*      */         {
/* 2724 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2729 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isWater(BlockPos pos) {
/* 2735 */     return (getBlockState(pos).getBlock().getMaterial() == Material.water);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canSnowAt(BlockPos pos, boolean checkLight) {
/* 2746 */     BiomeGenBase biomegenbase = getBiomeGenForCoords(pos);
/* 2747 */     float f = biomegenbase.getFloatTemperature(pos);
/*      */     
/* 2749 */     if (f > 0.15F)
/*      */     {
/* 2751 */       return false;
/*      */     }
/* 2753 */     if (!checkLight)
/*      */     {
/* 2755 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 2759 */     if (pos.getY() >= 0 && pos.getY() < 256 && getLightFor(EnumSkyBlock.BLOCK, pos) < 10) {
/*      */       
/* 2761 */       Block block = getBlockState(pos).getBlock();
/*      */       
/* 2763 */       if (block.getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(this, pos))
/*      */       {
/* 2765 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 2769 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkLight(BlockPos pos) {
/* 2775 */     boolean flag = false;
/*      */     
/* 2777 */     if (!this.provider.getHasNoSky())
/*      */     {
/* 2779 */       flag |= checkLightFor(EnumSkyBlock.SKY, pos);
/*      */     }
/*      */     
/* 2782 */     flag |= checkLightFor(EnumSkyBlock.BLOCK, pos);
/* 2783 */     return flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getRawLight(BlockPos pos, EnumSkyBlock lightType) {
/* 2791 */     if (lightType == EnumSkyBlock.SKY && canSeeSky(pos))
/*      */     {
/* 2793 */       return 15;
/*      */     }
/*      */ 
/*      */     
/* 2797 */     Block block = getBlockState(pos).getBlock();
/* 2798 */     int i = (lightType == EnumSkyBlock.SKY) ? 0 : block.getLightValue();
/* 2799 */     int j = block.getLightOpacity();
/*      */     
/* 2801 */     if (j >= 15 && block.getLightValue() > 0)
/*      */     {
/* 2803 */       j = 1;
/*      */     }
/*      */     
/* 2806 */     if (j < 1)
/*      */     {
/* 2808 */       j = 1;
/*      */     }
/*      */     
/* 2811 */     if (j >= 15)
/*      */     {
/* 2813 */       return 0;
/*      */     }
/* 2815 */     if (i >= 14)
/*      */     {
/* 2817 */       return i;
/*      */     }
/*      */ 
/*      */     
/* 2821 */     for (EnumFacing enumfacing : EnumFacing.values()) {
/*      */       
/* 2823 */       BlockPos blockpos = pos.offset(enumfacing);
/* 2824 */       int k = getLightFor(lightType, blockpos) - j;
/*      */       
/* 2826 */       if (k > i)
/*      */       {
/* 2828 */         i = k;
/*      */       }
/*      */       
/* 2831 */       if (i >= 14)
/*      */       {
/* 2833 */         return i;
/*      */       }
/*      */     } 
/*      */     
/* 2837 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkLightFor(EnumSkyBlock lightType, BlockPos pos) {
/* 2844 */     if (!isAreaLoaded(pos, 17, false))
/*      */     {
/* 2846 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2850 */     int i = 0;
/* 2851 */     int j = 0;
/* 2852 */     this.theProfiler.startSection("getBrightness");
/* 2853 */     int k = getLightFor(lightType, pos);
/* 2854 */     int l = getRawLight(pos, lightType);
/* 2855 */     int i1 = pos.getX();
/* 2856 */     int j1 = pos.getY();
/* 2857 */     int k1 = pos.getZ();
/*      */     
/* 2859 */     if (l > k) {
/*      */       
/* 2861 */       this.lightUpdateBlockList[j++] = 133152;
/*      */     }
/* 2863 */     else if (l < k) {
/*      */       
/* 2865 */       this.lightUpdateBlockList[j++] = 0x20820 | k << 18;
/*      */       
/* 2867 */       while (i < j) {
/*      */         
/* 2869 */         int l1 = this.lightUpdateBlockList[i++];
/* 2870 */         int i2 = (l1 & 0x3F) - 32 + i1;
/* 2871 */         int j2 = (l1 >> 6 & 0x3F) - 32 + j1;
/* 2872 */         int k2 = (l1 >> 12 & 0x3F) - 32 + k1;
/* 2873 */         int l2 = l1 >> 18 & 0xF;
/* 2874 */         BlockPos blockpos = new BlockPos(i2, j2, k2);
/* 2875 */         int i3 = getLightFor(lightType, blockpos);
/*      */         
/* 2877 */         if (i3 == l2) {
/*      */           
/* 2879 */           setLightFor(lightType, blockpos, 0);
/*      */           
/* 2881 */           if (l2 > 0) {
/*      */             
/* 2883 */             int j3 = MathHelper.abs_int(i2 - i1);
/* 2884 */             int k3 = MathHelper.abs_int(j2 - j1);
/* 2885 */             int l3 = MathHelper.abs_int(k2 - k1);
/*      */             
/* 2887 */             if (j3 + k3 + l3 < 17) {
/*      */               
/* 2889 */               BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */               
/* 2891 */               for (EnumFacing enumfacing : EnumFacing.values()) {
/*      */                 
/* 2893 */                 int i4 = i2 + enumfacing.getFrontOffsetX();
/* 2894 */                 int j4 = j2 + enumfacing.getFrontOffsetY();
/* 2895 */                 int k4 = k2 + enumfacing.getFrontOffsetZ();
/* 2896 */                 blockpos$mutableblockpos.func_181079_c(i4, j4, k4);
/* 2897 */                 int l4 = Math.max(1, getBlockState((BlockPos)blockpos$mutableblockpos).getBlock().getLightOpacity());
/* 2898 */                 i3 = getLightFor(lightType, (BlockPos)blockpos$mutableblockpos);
/*      */                 
/* 2900 */                 if (i3 == l2 - l4 && j < this.lightUpdateBlockList.length)
/*      */                 {
/* 2902 */                   this.lightUpdateBlockList[j++] = i4 - i1 + 32 | j4 - j1 + 32 << 6 | k4 - k1 + 32 << 12 | l2 - l4 << 18;
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2910 */       i = 0;
/*      */     } 
/*      */     
/* 2913 */     this.theProfiler.endSection();
/* 2914 */     this.theProfiler.startSection("checkedPosition < toCheckCount");
/*      */     
/* 2916 */     while (i < j) {
/*      */       
/* 2918 */       int i5 = this.lightUpdateBlockList[i++];
/* 2919 */       int j5 = (i5 & 0x3F) - 32 + i1;
/* 2920 */       int k5 = (i5 >> 6 & 0x3F) - 32 + j1;
/* 2921 */       int l5 = (i5 >> 12 & 0x3F) - 32 + k1;
/* 2922 */       BlockPos blockpos1 = new BlockPos(j5, k5, l5);
/* 2923 */       int i6 = getLightFor(lightType, blockpos1);
/* 2924 */       int j6 = getRawLight(blockpos1, lightType);
/*      */       
/* 2926 */       if (j6 != i6) {
/*      */         
/* 2928 */         setLightFor(lightType, blockpos1, j6);
/*      */         
/* 2930 */         if (j6 > i6) {
/*      */           
/* 2932 */           int k6 = Math.abs(j5 - i1);
/* 2933 */           int l6 = Math.abs(k5 - j1);
/* 2934 */           int i7 = Math.abs(l5 - k1);
/* 2935 */           boolean flag = (j < this.lightUpdateBlockList.length - 6);
/*      */           
/* 2937 */           if (k6 + l6 + i7 < 17 && flag) {
/*      */             
/* 2939 */             if (getLightFor(lightType, blockpos1.west()) < j6)
/*      */             {
/* 2941 */               this.lightUpdateBlockList[j++] = j5 - 1 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
/*      */             }
/*      */             
/* 2944 */             if (getLightFor(lightType, blockpos1.east()) < j6)
/*      */             {
/* 2946 */               this.lightUpdateBlockList[j++] = j5 + 1 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
/*      */             }
/*      */             
/* 2949 */             if (getLightFor(lightType, blockpos1.down()) < j6)
/*      */             {
/* 2951 */               this.lightUpdateBlockList[j++] = j5 - i1 + 32 + (k5 - 1 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
/*      */             }
/*      */             
/* 2954 */             if (getLightFor(lightType, blockpos1.up()) < j6)
/*      */             {
/* 2956 */               this.lightUpdateBlockList[j++] = j5 - i1 + 32 + (k5 + 1 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
/*      */             }
/*      */             
/* 2959 */             if (getLightFor(lightType, blockpos1.north()) < j6)
/*      */             {
/* 2961 */               this.lightUpdateBlockList[j++] = j5 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 - 1 - k1 + 32 << 12);
/*      */             }
/*      */             
/* 2964 */             if (getLightFor(lightType, blockpos1.south()) < j6)
/*      */             {
/* 2966 */               this.lightUpdateBlockList[j++] = j5 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 + 1 - k1 + 32 << 12);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2973 */     this.theProfiler.endSection();
/* 2974 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean tickUpdates(boolean p_72955_1_) {
/* 2983 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<NextTickListEntry> getPendingBlockUpdates(Chunk chunkIn, boolean p_72920_2_) {
/* 2988 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<NextTickListEntry> func_175712_a(StructureBoundingBox structureBB, boolean p_175712_2_) {
/* 2993 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<Entity> getEntitiesWithinAABBExcludingEntity(Entity entityIn, AxisAlignedBB bb) {
/* 2998 */     return getEntitiesInAABBexcluding(entityIn, bb, EntitySelectors.NOT_SPECTATING);
/*      */   }
/*      */ 
/*      */   
/*      */   public List<Entity> getEntitiesInAABBexcluding(Entity entityIn, AxisAlignedBB boundingBox, Predicate<? super Entity> predicate) {
/* 3003 */     List<Entity> list = Lists.newArrayList();
/* 3004 */     int i = MathHelper.floor_double((boundingBox.minX - 2.0D) / 16.0D);
/* 3005 */     int j = MathHelper.floor_double((boundingBox.maxX + 2.0D) / 16.0D);
/* 3006 */     int k = MathHelper.floor_double((boundingBox.minZ - 2.0D) / 16.0D);
/* 3007 */     int l = MathHelper.floor_double((boundingBox.maxZ + 2.0D) / 16.0D);
/*      */     
/* 3009 */     for (int i1 = i; i1 <= j; i1++) {
/*      */       
/* 3011 */       for (int j1 = k; j1 <= l; j1++) {
/*      */         
/* 3013 */         if (isChunkLoaded(i1, j1, true))
/*      */         {
/* 3015 */           getChunkFromChunkCoords(i1, j1).getEntitiesWithinAABBForEntity(entityIn, boundingBox, list, predicate);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 3020 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Entity> List<T> getEntities(Class<? extends T> entityType, Predicate<? super T> filter) {
/* 3025 */     List<T> list = Lists.newArrayList();
/*      */     
/* 3027 */     for (Entity entity : this.loadedEntityList) {
/*      */       
/* 3029 */       if (entityType.isAssignableFrom(entity.getClass()) && filter.apply(entity))
/*      */       {
/* 3031 */         list.add((T)entity);
/*      */       }
/*      */     } 
/*      */     
/* 3035 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Entity> List<T> getPlayers(Class<? extends T> playerType, Predicate<? super T> filter) {
/* 3040 */     List<T> list = Lists.newArrayList();
/*      */     
/* 3042 */     for (Entity entity : this.playerEntities) {
/*      */       
/* 3044 */       if (playerType.isAssignableFrom(entity.getClass()) && filter.apply(entity))
/*      */       {
/* 3046 */         list.add((T)entity);
/*      */       }
/*      */     } 
/*      */     
/* 3050 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> classEntity, AxisAlignedBB bb) {
/* 3055 */     return getEntitiesWithinAABB(classEntity, bb, EntitySelectors.NOT_SPECTATING);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> clazz, AxisAlignedBB aabb, Predicate<? super T> filter) {
/* 3060 */     int i = MathHelper.floor_double((aabb.minX - 2.0D) / 16.0D);
/* 3061 */     int j = MathHelper.floor_double((aabb.maxX + 2.0D) / 16.0D);
/* 3062 */     int k = MathHelper.floor_double((aabb.minZ - 2.0D) / 16.0D);
/* 3063 */     int l = MathHelper.floor_double((aabb.maxZ + 2.0D) / 16.0D);
/* 3064 */     List<T> list = Lists.newArrayList();
/*      */     
/* 3066 */     for (int i1 = i; i1 <= j; i1++) {
/*      */       
/* 3068 */       for (int j1 = k; j1 <= l; j1++) {
/*      */         
/* 3070 */         if (isChunkLoaded(i1, j1, true))
/*      */         {
/* 3072 */           getChunkFromChunkCoords(i1, j1).getEntitiesOfTypeWithinAAAB(clazz, aabb, list, filter);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 3077 */     return list;
/*      */   }
/*      */   
/*      */   public <T extends Entity> T findNearestEntityWithinAABB(Class<? extends T> entityType, AxisAlignedBB aabb, T closestTo) {
/*      */     Entity entity;
/* 3082 */     List<T> list = getEntitiesWithinAABB(entityType, aabb);
/* 3083 */     T t = null;
/* 3084 */     double d0 = Double.MAX_VALUE;
/*      */     
/* 3086 */     for (int i = 0; i < list.size(); i++) {
/*      */       
/* 3088 */       Entity entity1 = (Entity)list.get(i);
/*      */       
/* 3090 */       if (entity1 != closestTo && EntitySelectors.NOT_SPECTATING.apply(entity1)) {
/*      */         
/* 3092 */         double d1 = closestTo.getDistanceSqToEntity(entity1);
/*      */         
/* 3094 */         if (d1 <= d0) {
/*      */           
/* 3096 */           entity = entity1;
/* 3097 */           d0 = d1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3102 */     return (T)entity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity getEntityByID(int id) {
/* 3110 */     return (Entity)this.entitiesById.lookup(id);
/*      */   }
/*      */ 
/*      */   
/*      */   public List<Entity> getLoadedEntityList() {
/* 3115 */     return this.loadedEntityList;
/*      */   }
/*      */ 
/*      */   
/*      */   public void markChunkDirty(BlockPos pos, TileEntity unusedTileEntity) {
/* 3120 */     if (isBlockLoaded(pos))
/*      */     {
/* 3122 */       getChunkFromBlockCoords(pos).setChunkModified();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int countEntities(Class<?> entityType) {
/* 3131 */     int i = 0;
/*      */     
/* 3133 */     for (Entity entity : this.loadedEntityList) {
/*      */       
/* 3135 */       if ((!(entity instanceof EntityLiving) || !((EntityLiving)entity).isNoDespawnRequired()) && entityType.isAssignableFrom(entity.getClass()))
/*      */       {
/* 3137 */         i++;
/*      */       }
/*      */     } 
/*      */     
/* 3141 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadEntities(Collection<Entity> entityCollection) {
/* 3146 */     this.loadedEntityList.addAll(entityCollection);
/*      */     
/* 3148 */     for (Entity entity : entityCollection)
/*      */     {
/* 3150 */       onEntityAdded(entity);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void unloadEntities(Collection<Entity> entityCollection) {
/* 3156 */     this.unloadedEntityList.addAll(entityCollection);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBlockBePlaced(Block blockIn, BlockPos pos, boolean p_175716_3_, EnumFacing side, Entity entityIn, ItemStack itemStackIn) {
/* 3161 */     Block block = getBlockState(pos).getBlock();
/* 3162 */     AxisAlignedBB axisalignedbb = p_175716_3_ ? null : blockIn.getCollisionBoundingBox(this, pos, blockIn.getDefaultState());
/* 3163 */     return (axisalignedbb != null && !checkNoEntityCollision(axisalignedbb, entityIn)) ? false : ((block.getMaterial() == Material.circuits && blockIn == Blocks.anvil) ? true : ((block.getMaterial().isReplaceable() && blockIn.canReplace(this, pos, side, itemStackIn))));
/*      */   }
/*      */ 
/*      */   
/*      */   public int func_181545_F() {
/* 3168 */     return this.field_181546_a;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_181544_b(int p_181544_1_) {
/* 3173 */     this.field_181546_a = p_181544_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getStrongPower(BlockPos pos, EnumFacing direction) {
/* 3178 */     IBlockState iblockstate = getBlockState(pos);
/* 3179 */     return iblockstate.getBlock().isProvidingStrongPower(this, pos, iblockstate, direction);
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldType getWorldType() {
/* 3184 */     return this.worldInfo.getTerrainType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStrongPower(BlockPos pos) {
/* 3192 */     int i = 0;
/* 3193 */     i = Math.max(i, getStrongPower(pos.down(), EnumFacing.DOWN));
/*      */     
/* 3195 */     if (i >= 15)
/*      */     {
/* 3197 */       return i;
/*      */     }
/*      */ 
/*      */     
/* 3201 */     i = Math.max(i, getStrongPower(pos.up(), EnumFacing.UP));
/*      */     
/* 3203 */     if (i >= 15)
/*      */     {
/* 3205 */       return i;
/*      */     }
/*      */ 
/*      */     
/* 3209 */     i = Math.max(i, getStrongPower(pos.north(), EnumFacing.NORTH));
/*      */     
/* 3211 */     if (i >= 15)
/*      */     {
/* 3213 */       return i;
/*      */     }
/*      */ 
/*      */     
/* 3217 */     i = Math.max(i, getStrongPower(pos.south(), EnumFacing.SOUTH));
/*      */     
/* 3219 */     if (i >= 15)
/*      */     {
/* 3221 */       return i;
/*      */     }
/*      */ 
/*      */     
/* 3225 */     i = Math.max(i, getStrongPower(pos.west(), EnumFacing.WEST));
/*      */     
/* 3227 */     if (i >= 15)
/*      */     {
/* 3229 */       return i;
/*      */     }
/*      */ 
/*      */     
/* 3233 */     i = Math.max(i, getStrongPower(pos.east(), EnumFacing.EAST));
/* 3234 */     return (i >= 15) ? i : i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSidePowered(BlockPos pos, EnumFacing side) {
/* 3244 */     return (getRedstonePower(pos, side) > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getRedstonePower(BlockPos pos, EnumFacing facing) {
/* 3249 */     IBlockState iblockstate = getBlockState(pos);
/* 3250 */     Block block = iblockstate.getBlock();
/* 3251 */     return block.isNormalCube() ? getStrongPower(pos) : block.isProvidingWeakPower(this, pos, iblockstate, facing);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockPowered(BlockPos pos) {
/* 3256 */     return (getRedstonePower(pos.down(), EnumFacing.DOWN) > 0) ? true : ((getRedstonePower(pos.up(), EnumFacing.UP) > 0) ? true : ((getRedstonePower(pos.north(), EnumFacing.NORTH) > 0) ? true : ((getRedstonePower(pos.south(), EnumFacing.SOUTH) > 0) ? true : ((getRedstonePower(pos.west(), EnumFacing.WEST) > 0) ? true : ((getRedstonePower(pos.east(), EnumFacing.EAST) > 0))))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int isBlockIndirectlyGettingPowered(BlockPos pos) {
/* 3265 */     int i = 0;
/*      */     
/* 3267 */     for (EnumFacing enumfacing : EnumFacing.values()) {
/*      */       
/* 3269 */       int j = getRedstonePower(pos.offset(enumfacing), enumfacing);
/*      */       
/* 3271 */       if (j >= 15)
/*      */       {
/* 3273 */         return 15;
/*      */       }
/*      */       
/* 3276 */       if (j > i)
/*      */       {
/* 3278 */         i = j;
/*      */       }
/*      */     } 
/*      */     
/* 3282 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayer getClosestPlayerToEntity(Entity entityIn, double distance) {
/* 3291 */     return getClosestPlayer(entityIn.posX, entityIn.posY, entityIn.posZ, distance);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayer getClosestPlayer(double x, double y, double z, double distance) {
/* 3300 */     double d0 = -1.0D;
/* 3301 */     EntityPlayer entityplayer = null;
/*      */     
/* 3303 */     for (int i = 0; i < this.playerEntities.size(); i++) {
/*      */       
/* 3305 */       EntityPlayer entityplayer1 = this.playerEntities.get(i);
/*      */       
/* 3307 */       if (EntitySelectors.NOT_SPECTATING.apply(entityplayer1)) {
/*      */         
/* 3309 */         double d1 = entityplayer1.getDistanceSq(x, y, z);
/*      */         
/* 3311 */         if ((distance < 0.0D || d1 < distance * distance) && (d0 == -1.0D || d1 < d0)) {
/*      */           
/* 3313 */           d0 = d1;
/* 3314 */           entityplayer = entityplayer1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3319 */     return entityplayer;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAnyPlayerWithinRangeAt(double x, double y, double z, double range) {
/* 3324 */     for (int i = 0; i < this.playerEntities.size(); i++) {
/*      */       
/* 3326 */       EntityPlayer entityplayer = this.playerEntities.get(i);
/*      */       
/* 3328 */       if (EntitySelectors.NOT_SPECTATING.apply(entityplayer)) {
/*      */         
/* 3330 */         double d0 = entityplayer.getDistanceSq(x, y, z);
/*      */         
/* 3332 */         if (range < 0.0D || d0 < range * range)
/*      */         {
/* 3334 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 3339 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayer getPlayerEntityByName(String name) {
/* 3347 */     for (int i = 0; i < this.playerEntities.size(); i++) {
/*      */       
/* 3349 */       EntityPlayer entityplayer = this.playerEntities.get(i);
/*      */       
/* 3351 */       if (name.equals(entityplayer.getCommandSenderName()))
/*      */       {
/* 3353 */         return entityplayer;
/*      */       }
/*      */     } 
/*      */     
/* 3357 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityPlayer getPlayerEntityByUUID(UUID uuid) {
/* 3362 */     for (int i = 0; i < this.playerEntities.size(); i++) {
/*      */       
/* 3364 */       EntityPlayer entityplayer = this.playerEntities.get(i);
/*      */       
/* 3366 */       if (uuid.equals(entityplayer.getUniqueID()))
/*      */       {
/* 3368 */         return entityplayer;
/*      */       }
/*      */     } 
/*      */     
/* 3372 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendQuittingDisconnectingPacket() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkSessionLock() throws MinecraftException {
/* 3387 */     this.saveHandler.checkSessionLock();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTotalWorldTime(long worldTime) {
/* 3392 */     this.worldInfo.setWorldTotalTime(worldTime);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getSeed() {
/* 3400 */     return this.worldInfo.getSeed();
/*      */   }
/*      */ 
/*      */   
/*      */   public long getTotalWorldTime() {
/* 3405 */     return this.worldInfo.getWorldTotalTime();
/*      */   }
/*      */ 
/*      */   
/*      */   public long getWorldTime() {
/* 3410 */     return this.worldInfo.getWorldTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWorldTime(long time) {
/* 3418 */     this.worldInfo.setWorldTime(time);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getSpawnPoint() {
/* 3426 */     BlockPos blockpos = new BlockPos(this.worldInfo.getSpawnX(), this.worldInfo.getSpawnY(), this.worldInfo.getSpawnZ());
/*      */     
/* 3428 */     if (!getWorldBorder().contains(blockpos))
/*      */     {
/* 3430 */       blockpos = getHeight(new BlockPos(getWorldBorder().getCenterX(), 0.0D, getWorldBorder().getCenterZ()));
/*      */     }
/*      */     
/* 3433 */     return blockpos;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSpawnPoint(BlockPos pos) {
/* 3438 */     this.worldInfo.setSpawn(pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void joinEntityInSurroundings(Entity entityIn) {
/* 3446 */     int i = MathHelper.floor_double(entityIn.posX / 16.0D);
/* 3447 */     int j = MathHelper.floor_double(entityIn.posZ / 16.0D);
/* 3448 */     int k = 2;
/*      */     
/* 3450 */     for (int l = i - k; l <= i + k; l++) {
/*      */       
/* 3452 */       for (int i1 = j - k; i1 <= j + k; i1++)
/*      */       {
/* 3454 */         getChunkFromChunkCoords(l, i1);
/*      */       }
/*      */     } 
/*      */     
/* 3458 */     if (!this.loadedEntityList.contains(entityIn))
/*      */     {
/* 3460 */       this.loadedEntityList.add(entityIn);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockModifiable(EntityPlayer player, BlockPos pos) {
/* 3466 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEntityState(Entity entityIn, byte state) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChunkProvider getChunkProvider() {
/* 3481 */     return this.chunkProvider;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addBlockEvent(BlockPos pos, Block blockIn, int eventID, int eventParam) {
/* 3486 */     blockIn.onBlockEventReceived(this, pos, getBlockState(pos), eventID, eventParam);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ISaveHandler getSaveHandler() {
/* 3494 */     return this.saveHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldInfo getWorldInfo() {
/* 3502 */     return this.worldInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GameRules getGameRules() {
/* 3510 */     return this.worldInfo.getGameRulesInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateAllPlayersSleepingFlag() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getThunderStrength(float delta) {
/* 3522 */     return (this.prevThunderingStrength + (this.thunderingStrength - this.prevThunderingStrength) * delta) * getRainStrength(delta);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setThunderStrength(float strength) {
/* 3530 */     this.prevThunderingStrength = strength;
/* 3531 */     this.thunderingStrength = strength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getRainStrength(float delta) {
/* 3539 */     return this.prevRainingStrength + (this.rainingStrength - this.prevRainingStrength) * delta;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRainStrength(float strength) {
/* 3547 */     this.prevRainingStrength = strength;
/* 3548 */     this.rainingStrength = strength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isThundering() {
/* 3556 */     return (getThunderStrength(1.0F) > 0.9D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRaining() {
/* 3564 */     return (getRainStrength(1.0F) > 0.2D);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canLightningStrike(BlockPos strikePosition) {
/* 3569 */     if (!isRaining())
/*      */     {
/* 3571 */       return false;
/*      */     }
/* 3573 */     if (!canSeeSky(strikePosition))
/*      */     {
/* 3575 */       return false;
/*      */     }
/* 3577 */     if (getPrecipitationHeight(strikePosition).getY() > strikePosition.getY())
/*      */     {
/* 3579 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 3583 */     BiomeGenBase biomegenbase = getBiomeGenForCoords(strikePosition);
/* 3584 */     return biomegenbase.getEnableSnow() ? false : (canSnowAt(strikePosition, false) ? false : biomegenbase.canSpawnLightningBolt());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBlockinHighHumidity(BlockPos pos) {
/* 3590 */     BiomeGenBase biomegenbase = getBiomeGenForCoords(pos);
/* 3591 */     return biomegenbase.isHighHumidity();
/*      */   }
/*      */ 
/*      */   
/*      */   public MapStorage getMapStorage() {
/* 3596 */     return this.mapStorage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemData(String dataID, WorldSavedData worldSavedDataIn) {
/* 3605 */     this.mapStorage.setData(dataID, worldSavedDataIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldSavedData loadItemData(Class<? extends WorldSavedData> clazz, String dataID) {
/* 3614 */     return this.mapStorage.loadData(clazz, dataID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getUniqueDataId(String key) {
/* 3623 */     return this.mapStorage.getUniqueDataId(key);
/*      */   }
/*      */ 
/*      */   
/*      */   public void playBroadcastSound(int p_175669_1_, BlockPos pos, int p_175669_3_) {
/* 3628 */     for (int i = 0; i < this.worldAccesses.size(); i++)
/*      */     {
/* 3630 */       ((IWorldAccess)this.worldAccesses.get(i)).broadcastSound(p_175669_1_, pos, p_175669_3_);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void playAuxSFX(int p_175718_1_, BlockPos pos, int p_175718_3_) {
/* 3636 */     playAuxSFXAtEntity(null, p_175718_1_, pos, p_175718_3_);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void playAuxSFXAtEntity(EntityPlayer player, int sfxType, BlockPos pos, int p_180498_4_) {
/*      */     try {
/* 3643 */       for (int i = 0; i < this.worldAccesses.size(); i++)
/*      */       {
/* 3645 */         ((IWorldAccess)this.worldAccesses.get(i)).playAuxSFX(player, sfxType, pos, p_180498_4_);
/*      */       }
/*      */     }
/* 3648 */     catch (Throwable throwable) {
/*      */       
/* 3650 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Playing level event");
/* 3651 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Level event being played");
/* 3652 */       crashreportcategory.addCrashSection("Block coordinates", CrashReportCategory.getCoordinateInfo(pos));
/* 3653 */       crashreportcategory.addCrashSection("Event source", player);
/* 3654 */       crashreportcategory.addCrashSection("Event type", Integer.valueOf(sfxType));
/* 3655 */       crashreportcategory.addCrashSection("Event data", Integer.valueOf(p_180498_4_));
/* 3656 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHeight() {
/* 3665 */     return 256;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getActualHeight() {
/* 3673 */     return this.provider.getHasNoSky() ? 128 : 256;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Random setRandomSeed(int p_72843_1_, int p_72843_2_, int p_72843_3_) {
/* 3681 */     long i = p_72843_1_ * 341873128712L + p_72843_2_ * 132897987541L + getWorldInfo().getSeed() + p_72843_3_;
/* 3682 */     this.rand.setSeed(i);
/* 3683 */     return this.rand;
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockPos getStrongholdPos(String name, BlockPos pos) {
/* 3688 */     return getChunkProvider().getStrongholdGen(this, name, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean extendedLevelsInChunkCache() {
/* 3696 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getHorizon() {
/* 3704 */     return (this.worldInfo.getTerrainType() == WorldType.FLAT) ? 0.0D : 63.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CrashReportCategory addWorldInfoToCrashReport(CrashReport report) {
/* 3712 */     CrashReportCategory crashreportcategory = report.makeCategoryDepth("Affected level", 1);
/* 3713 */     crashreportcategory.addCrashSection("Level name", (this.worldInfo == null) ? "????" : this.worldInfo.getWorldName());
/* 3714 */     crashreportcategory.addCrashSectionCallable("All players", new Callable<String>()
/*      */         {
/*      */           public String call()
/*      */           {
/* 3718 */             return World.this.playerEntities.size() + " total; " + World.this.playerEntities.toString();
/*      */           }
/*      */         });
/* 3721 */     crashreportcategory.addCrashSectionCallable("Chunk stats", new Callable<String>()
/*      */         {
/*      */           public String call()
/*      */           {
/* 3725 */             return World.this.chunkProvider.makeString();
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*      */     try {
/* 3731 */       this.worldInfo.addToCrashReport(crashreportcategory);
/*      */     }
/* 3733 */     catch (Throwable throwable) {
/*      */       
/* 3735 */       crashreportcategory.addCrashSectionThrowable("Level Data Unobtainable", throwable);
/*      */     } 
/*      */     
/* 3738 */     return crashreportcategory;
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
/* 3743 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*      */       
/* 3745 */       IWorldAccess iworldaccess = this.worldAccesses.get(i);
/* 3746 */       iworldaccess.sendBlockBreakProgress(breakerId, pos, progress);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Calendar getCurrentDate() {
/* 3755 */     if (getTotalWorldTime() % 600L == 0L)
/*      */     {
/* 3757 */       this.theCalendar.setTimeInMillis(MinecraftServer.getCurrentTimeMillis());
/*      */     }
/*      */     
/* 3760 */     return this.theCalendar;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void makeFireworks(double x, double y, double z, double motionX, double motionY, double motionZ, NBTTagCompound compund) {}
/*      */ 
/*      */   
/*      */   public Scoreboard getScoreboard() {
/* 3769 */     return this.worldScoreboard;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateComparatorOutputLevel(BlockPos pos, Block blockIn) {
/* 3774 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*      */       
/* 3776 */       BlockPos blockpos = pos.offset(enumfacing);
/*      */       
/* 3778 */       if (isBlockLoaded(blockpos)) {
/*      */         
/* 3780 */         IBlockState iblockstate = getBlockState(blockpos);
/*      */         
/* 3782 */         if (Blocks.unpowered_comparator.isAssociated(iblockstate.getBlock())) {
/*      */           
/* 3784 */           iblockstate.getBlock().onNeighborBlockChange(this, blockpos, iblockstate, blockIn); continue;
/*      */         } 
/* 3786 */         if (iblockstate.getBlock().isNormalCube()) {
/*      */           
/* 3788 */           blockpos = blockpos.offset(enumfacing);
/* 3789 */           iblockstate = getBlockState(blockpos);
/*      */           
/* 3791 */           if (Blocks.unpowered_comparator.isAssociated(iblockstate.getBlock()))
/*      */           {
/* 3793 */             iblockstate.getBlock().onNeighborBlockChange(this, blockpos, iblockstate, blockIn);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public DifficultyInstance getDifficultyForLocation(BlockPos pos) {
/* 3802 */     long i = 0L;
/* 3803 */     float f = 0.0F;
/*      */     
/* 3805 */     if (isBlockLoaded(pos)) {
/*      */       
/* 3807 */       f = getCurrentMoonPhaseFactor();
/* 3808 */       i = getChunkFromBlockCoords(pos).getInhabitedTime();
/*      */     } 
/*      */     
/* 3811 */     return new DifficultyInstance(getDifficulty(), getWorldTime(), i, f);
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumDifficulty getDifficulty() {
/* 3816 */     return getWorldInfo().getDifficulty();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSkylightSubtracted() {
/* 3821 */     return this.skylightSubtracted;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSkylightSubtracted(int newSkylightSubtracted) {
/* 3826 */     this.skylightSubtracted = newSkylightSubtracted;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLastLightningBolt() {
/* 3831 */     return this.lastLightningBolt;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLastLightningBolt(int lastLightningBoltIn) {
/* 3836 */     this.lastLightningBolt = lastLightningBoltIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFindingSpawnPoint() {
/* 3841 */     return this.findingSpawnPoint;
/*      */   }
/*      */ 
/*      */   
/*      */   public VillageCollection getVillageCollection() {
/* 3846 */     return this.villageCollectionObj;
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldBorder getWorldBorder() {
/* 3851 */     return this.worldBorder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSpawnChunk(int x, int z) {
/* 3859 */     BlockPos blockpos = getSpawnPoint();
/* 3860 */     int i = x * 16 + 8 - blockpos.getX();
/* 3861 */     int j = z * 16 + 8 - blockpos.getZ();
/* 3862 */     int k = 128;
/* 3863 */     return (i >= -k && i <= k && j >= -k && j <= k);
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\world\World.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
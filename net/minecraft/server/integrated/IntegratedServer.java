/*     */ package net.minecraft.server.integrated;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ import net.minecraft.client.ClientBrandRetriever;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ThreadLanServerPing;
/*     */ import net.minecraft.command.ServerCommandManager;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketThreadUtil;
/*     */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*     */ import net.minecraft.profiler.PlayerUsageSnooper;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.CryptManager;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ import net.minecraft.util.Util;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.IWorldAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldManager;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.WorldServerMulti;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import net.optifine.ClearWater;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class IntegratedServer extends MinecraftServer {
/*  44 */   private static final Logger logger = LogManager.getLogger();
/*     */   
/*     */   private final Minecraft mc;
/*     */   
/*     */   private final WorldSettings theWorldSettings;
/*     */   private boolean isGamePaused;
/*     */   private boolean isPublic;
/*     */   private ThreadLanServerPing lanServerPing;
/*  52 */   private long ticksSaveLast = 0L;
/*  53 */   public World difficultyUpdateWorld = null;
/*  54 */   public BlockPos difficultyUpdatePos = null;
/*  55 */   public DifficultyInstance difficultyLast = null;
/*     */ 
/*     */   
/*     */   public IntegratedServer(Minecraft mcIn) {
/*  59 */     super(mcIn.getProxy(), new File(mcIn.mcDataDir, USER_CACHE_FILE.getName()));
/*  60 */     this.mc = mcIn;
/*  61 */     this.theWorldSettings = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public IntegratedServer(Minecraft mcIn, String folderName, String worldName, WorldSettings settings) {
/*  66 */     super(new File(mcIn.mcDataDir, "saves"), mcIn.getProxy(), new File(mcIn.mcDataDir, USER_CACHE_FILE.getName()));
/*  67 */     setServerOwner(mcIn.getSession().getUsername());
/*  68 */     setFolderName(folderName);
/*  69 */     setWorldName(worldName);
/*  70 */     canCreateBonusChest(settings.isBonusChestEnabled());
/*  71 */     setBuildLimit(256);
/*  72 */     setConfigManager(new IntegratedPlayerList(this));
/*  73 */     this.mc = mcIn;
/*  74 */     this.theWorldSettings = settings;
/*  75 */     ISaveHandler isavehandler = getActiveAnvilConverter().getSaveLoader(folderName, false);
/*  76 */     WorldInfo worldinfo = isavehandler.loadWorldInfo();
/*     */     
/*  78 */     if (worldinfo != null) {
/*     */       
/*  80 */       NBTTagCompound nbttagcompound = worldinfo.getPlayerNBTTagCompound();
/*     */       
/*  82 */       if (nbttagcompound != null && nbttagcompound.hasKey("Dimension")) {
/*     */         
/*  84 */         int i = nbttagcompound.getInteger("Dimension");
/*  85 */         PacketThreadUtil.lastDimensionId = i;
/*  86 */         this.mc.loadingScreen.setLoadingProgress(-1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ServerCommandManager createNewCommandManager() {
/*  93 */     return new IntegratedServerCommandManager();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadAllWorlds(String p_71247_1_, String p_71247_2_, long seed, WorldType type, String p_71247_6_) {
/*  98 */     convertMapIfNeeded(p_71247_1_);
/*  99 */     boolean flag = Reflector.DimensionManager.exists();
/*     */     
/* 101 */     if (!flag) {
/*     */       
/* 103 */       this.worldServers = new WorldServer[3];
/* 104 */       this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
/*     */     } 
/*     */     
/* 107 */     ISaveHandler isavehandler = getActiveAnvilConverter().getSaveLoader(p_71247_1_, true);
/* 108 */     setResourcePackFromWorld(getFolderName(), isavehandler);
/* 109 */     WorldInfo worldinfo = isavehandler.loadWorldInfo();
/*     */     
/* 111 */     if (worldinfo == null) {
/*     */       
/* 113 */       worldinfo = new WorldInfo(this.theWorldSettings, p_71247_2_);
/*     */     }
/*     */     else {
/*     */       
/* 117 */       worldinfo.setWorldName(p_71247_2_);
/*     */     } 
/*     */     
/* 120 */     if (flag) {
/*     */       
/* 122 */       WorldServer worldserver = (WorldServer)(new WorldServer(this, isavehandler, worldinfo, 0, this.theProfiler)).init();
/* 123 */       worldserver.initialize(this.theWorldSettings);
/* 124 */       Integer[] ainteger = (Integer[])Reflector.call(Reflector.DimensionManager_getStaticDimensionIDs, new Object[0]);
/* 125 */       Integer[] ainteger1 = ainteger;
/* 126 */       int i = ainteger.length;
/*     */       
/* 128 */       for (int j = 0; j < i; j++) {
/*     */         
/* 130 */         int k = ainteger1[j].intValue();
/* 131 */         WorldServer worldserver1 = (k == 0) ? worldserver : (WorldServer)(new WorldServerMulti(this, isavehandler, k, worldserver, this.theProfiler)).init();
/* 132 */         worldserver1.addWorldAccess((IWorldAccess)new WorldManager(this, worldserver1));
/*     */         
/* 134 */         if (!isSinglePlayer())
/*     */         {
/* 136 */           worldserver1.getWorldInfo().setGameType(getGameType());
/*     */         }
/*     */         
/* 139 */         if (Reflector.EventBus.exists())
/*     */         {
/* 141 */           Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, new Object[] { worldserver1 });
/*     */         }
/*     */       } 
/*     */       
/* 145 */       getConfigurationManager().setPlayerManager(new WorldServer[] { worldserver });
/*     */       
/* 147 */       if (worldserver.getWorldInfo().getDifficulty() == null)
/*     */       {
/* 149 */         setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 154 */       for (int l = 0; l < this.worldServers.length; l++) {
/*     */         
/* 156 */         int i1 = 0;
/*     */         
/* 158 */         if (l == 1)
/*     */         {
/* 160 */           i1 = -1;
/*     */         }
/*     */         
/* 163 */         if (l == 2)
/*     */         {
/* 165 */           i1 = 1;
/*     */         }
/*     */         
/* 168 */         if (l == 0) {
/*     */           
/* 170 */           this.worldServers[l] = (WorldServer)(new WorldServer(this, isavehandler, worldinfo, i1, this.theProfiler)).init();
/* 171 */           this.worldServers[l].initialize(this.theWorldSettings);
/*     */         }
/*     */         else {
/*     */           
/* 175 */           this.worldServers[l] = (WorldServer)(new WorldServerMulti(this, isavehandler, i1, this.worldServers[0], this.theProfiler)).init();
/*     */         } 
/*     */         
/* 178 */         this.worldServers[l].addWorldAccess((IWorldAccess)new WorldManager(this, this.worldServers[l]));
/*     */       } 
/*     */       
/* 181 */       getConfigurationManager().setPlayerManager(this.worldServers);
/*     */       
/* 183 */       if (this.worldServers[0].getWorldInfo().getDifficulty() == null)
/*     */       {
/* 185 */         setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
/*     */       }
/*     */     } 
/*     */     
/* 189 */     initialWorldChunkLoad();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean startServer() throws IOException {
/* 197 */     logger.info("Starting integrated minecraft server version 1.9");
/* 198 */     setOnlineMode(true);
/* 199 */     setCanSpawnAnimals(true);
/* 200 */     setCanSpawnNPCs(true);
/* 201 */     setAllowPvp(true);
/* 202 */     setAllowFlight(true);
/* 203 */     logger.info("Generating keypair");
/* 204 */     setKeyPair(CryptManager.generateKeyPair());
/*     */     
/* 206 */     if (Reflector.FMLCommonHandler_handleServerAboutToStart.exists()) {
/*     */       
/* 208 */       Object object = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/*     */       
/* 210 */       if (!Reflector.callBoolean(object, Reflector.FMLCommonHandler_handleServerAboutToStart, new Object[] { this }))
/*     */       {
/* 212 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 216 */     loadAllWorlds(getFolderName(), getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.getWorldName());
/* 217 */     setMOTD(getServerOwner() + " - " + this.worldServers[0].getWorldInfo().getWorldName());
/*     */     
/* 219 */     if (Reflector.FMLCommonHandler_handleServerStarting.exists()) {
/*     */       
/* 221 */       Object object1 = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/*     */       
/* 223 */       if (Reflector.FMLCommonHandler_handleServerStarting.getReturnType() == boolean.class)
/*     */       {
/* 225 */         return Reflector.callBoolean(object1, Reflector.FMLCommonHandler_handleServerStarting, new Object[] { this });
/*     */       }
/*     */       
/* 228 */       Reflector.callVoid(object1, Reflector.FMLCommonHandler_handleServerStarting, new Object[] { this });
/*     */     } 
/*     */     
/* 231 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/* 239 */     onTick();
/* 240 */     boolean flag = this.isGamePaused;
/* 241 */     this.isGamePaused = (Minecraft.getMinecraft().getNetHandler() != null && Minecraft.getMinecraft().isGamePaused());
/*     */     
/* 243 */     if (!flag && this.isGamePaused) {
/*     */       
/* 245 */       logger.info("Saving and pausing game...");
/* 246 */       getConfigurationManager().saveAllPlayerData();
/* 247 */       saveAllWorlds(false);
/*     */     } 
/*     */     
/* 250 */     if (this.isGamePaused) {
/*     */       
/* 252 */       synchronized (this.futureTaskQueue)
/*     */       {
/* 254 */         while (!this.futureTaskQueue.isEmpty())
/*     */         {
/* 256 */           Util.func_181617_a(this.futureTaskQueue.poll(), logger);
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 262 */       super.tick();
/*     */       
/* 264 */       if (this.mc.gameSettings.renderDistanceChunks != getConfigurationManager().getViewDistance()) {
/*     */         
/* 266 */         logger.info("Changing view distance to {}, from {}", new Object[] { Integer.valueOf(this.mc.gameSettings.renderDistanceChunks), Integer.valueOf(getConfigurationManager().getViewDistance()) });
/* 267 */         getConfigurationManager().setViewDistance(this.mc.gameSettings.renderDistanceChunks);
/*     */       } 
/*     */       
/* 270 */       if (this.mc.theWorld != null) {
/*     */         
/* 272 */         WorldInfo worldinfo1 = this.worldServers[0].getWorldInfo();
/* 273 */         WorldInfo worldinfo = this.mc.theWorld.getWorldInfo();
/*     */         
/* 275 */         if (!worldinfo1.isDifficultyLocked() && worldinfo.getDifficulty() != worldinfo1.getDifficulty()) {
/*     */           
/* 277 */           logger.info("Changing difficulty to {}, from {}", new Object[] { worldinfo.getDifficulty(), worldinfo1.getDifficulty() });
/* 278 */           setDifficultyForAllWorlds(worldinfo.getDifficulty());
/*     */         }
/* 280 */         else if (worldinfo.isDifficultyLocked() && !worldinfo1.isDifficultyLocked()) {
/*     */           
/* 282 */           logger.info("Locking difficulty to {}", new Object[] { worldinfo.getDifficulty() });
/*     */           
/* 284 */           for (WorldServer worldserver : this.worldServers) {
/*     */             
/* 286 */             if (worldserver != null)
/*     */             {
/* 288 */               worldserver.getWorldInfo().setDifficultyLocked(true);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canStructuresSpawn() {
/* 298 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldSettings.GameType getGameType() {
/* 303 */     return this.theWorldSettings.getGameType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumDifficulty getDifficulty() {
/* 311 */     return (this.mc.theWorld == null) ? this.mc.gameSettings.difficulty : this.mc.theWorld.getWorldInfo().getDifficulty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHardcore() {
/* 319 */     return this.theWorldSettings.getHardcoreEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_181034_q() {
/* 324 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_183002_r() {
/* 329 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveAllWorlds(boolean dontLog) {
/* 337 */     if (dontLog) {
/*     */       
/* 339 */       int i = getTickCounter();
/* 340 */       int j = this.mc.gameSettings.ofAutoSaveTicks;
/*     */       
/* 342 */       if (i < this.ticksSaveLast + j) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 347 */       this.ticksSaveLast = i;
/*     */     } 
/*     */     
/* 350 */     super.saveAllWorlds(dontLog);
/*     */   }
/*     */ 
/*     */   
/*     */   public File getDataDirectory() {
/* 355 */     return this.mc.mcDataDir;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDedicatedServer() {
/* 360 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_181035_ah() {
/* 365 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalTick(CrashReport report) {
/* 373 */     this.mc.crashed(report);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReport addServerInfoToCrashReport(CrashReport report) {
/* 381 */     report = super.addServerInfoToCrashReport(report);
/* 382 */     report.getCategory().addCrashSectionCallable("Type", new Callable<String>()
/*     */         {
/*     */           public String call() throws Exception
/*     */           {
/* 386 */             return "Integrated Server (map_client.txt)";
/*     */           }
/*     */         });
/* 389 */     report.getCategory().addCrashSectionCallable("Is Modded", new Callable<String>()
/*     */         {
/*     */           public String call() throws Exception
/*     */           {
/* 393 */             String s = ClientBrandRetriever.getClientModName();
/*     */             
/* 395 */             if (!s.equals("vanilla"))
/*     */             {
/* 397 */               return "Definitely; Client brand changed to '" + s + "'";
/*     */             }
/*     */ 
/*     */             
/* 401 */             s = IntegratedServer.this.getServerModName();
/* 402 */             return !s.equals("vanilla") ? ("Definitely; Server brand changed to '" + s + "'") : ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.");
/*     */           }
/*     */         });
/*     */     
/* 406 */     return report;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDifficultyForAllWorlds(EnumDifficulty difficulty) {
/* 411 */     super.setDifficultyForAllWorlds(difficulty);
/*     */     
/* 413 */     if (this.mc.theWorld != null)
/*     */     {
/* 415 */       this.mc.theWorld.getWorldInfo().setDifficulty(difficulty);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addServerStatsToSnooper(PlayerUsageSnooper playerSnooper) {
/* 421 */     super.addServerStatsToSnooper(playerSnooper);
/* 422 */     playerSnooper.addClientStat("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSnooperEnabled() {
/* 430 */     return Minecraft.getMinecraft().isSnooperEnabled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String shareToLAN(WorldSettings.GameType type, boolean allowCheats) {
/*     */     try {
/* 440 */       int i = -1;
/*     */ 
/*     */       
/*     */       try {
/* 444 */         i = HttpUtil.getSuitableLanPort();
/*     */       }
/* 446 */       catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 451 */       if (i <= 0)
/*     */       {
/* 453 */         i = 25564;
/*     */       }
/*     */       
/* 456 */       getNetworkSystem().addLanEndpoint(null, i);
/* 457 */       logger.info("Started on " + i);
/* 458 */       this.isPublic = true;
/* 459 */       this.lanServerPing = new ThreadLanServerPing(getMOTD(), i + "");
/* 460 */       this.lanServerPing.start();
/* 461 */       getConfigurationManager().setGameType(type);
/* 462 */       getConfigurationManager().setCommandsAllowedForAll(allowCheats);
/* 463 */       return i + "";
/*     */     }
/* 465 */     catch (IOException var6) {
/*     */       
/* 467 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopServer() {
/* 476 */     super.stopServer();
/*     */     
/* 478 */     if (this.lanServerPing != null) {
/*     */       
/* 480 */       this.lanServerPing.interrupt();
/* 481 */       this.lanServerPing = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initiateShutdown() {
/* 490 */     if (!Reflector.MinecraftForge.exists() || isServerRunning())
/*     */     {
/* 492 */       Futures.getUnchecked((Future)addScheduledTask(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 496 */                 for (EntityPlayerMP entityplayermp : Lists.newArrayList(IntegratedServer.this.getConfigurationManager().func_181057_v()))
/*     */                 {
/* 498 */                   IntegratedServer.this.getConfigurationManager().playerLoggedOut(entityplayermp);
/*     */                 }
/*     */               }
/*     */             }));
/*     */     }
/*     */     
/* 504 */     super.initiateShutdown();
/*     */     
/* 506 */     if (this.lanServerPing != null) {
/*     */       
/* 508 */       this.lanServerPing.interrupt();
/* 509 */       this.lanServerPing = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStaticInstance() {
/* 515 */     setInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getPublic() {
/* 523 */     return this.isPublic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGameType(WorldSettings.GameType gameMode) {
/* 531 */     getConfigurationManager().setGameType(gameMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCommandBlockEnabled() {
/* 539 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOpPermissionLevel() {
/* 544 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   private void onTick() {
/* 549 */     for (WorldServer worldserver : Arrays.<WorldServer>asList(this.worldServers))
/*     */     {
/* 551 */       onTick(worldserver);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public DifficultyInstance getDifficultyAsync(World p_getDifficultyAsync_1_, BlockPos p_getDifficultyAsync_2_) {
/* 557 */     this.difficultyUpdateWorld = p_getDifficultyAsync_1_;
/* 558 */     this.difficultyUpdatePos = p_getDifficultyAsync_2_;
/* 559 */     return this.difficultyLast;
/*     */   }
/*     */ 
/*     */   
/*     */   private void onTick(WorldServer p_onTick_1_) {
/* 564 */     if (!Config.isTimeDefault())
/*     */     {
/* 566 */       fixWorldTime(p_onTick_1_);
/*     */     }
/*     */     
/* 569 */     if (!Config.isWeatherEnabled())
/*     */     {
/* 571 */       fixWorldWeather(p_onTick_1_);
/*     */     }
/*     */     
/* 574 */     if (Config.waterOpacityChanged) {
/*     */       
/* 576 */       Config.waterOpacityChanged = false;
/* 577 */       ClearWater.updateWaterOpacity(Config.getGameSettings(), (World)p_onTick_1_);
/*     */     } 
/*     */     
/* 580 */     if (this.difficultyUpdateWorld == p_onTick_1_ && this.difficultyUpdatePos != null) {
/*     */       
/* 582 */       this.difficultyLast = p_onTick_1_.getDifficultyForLocation(this.difficultyUpdatePos);
/* 583 */       this.difficultyUpdateWorld = null;
/* 584 */       this.difficultyUpdatePos = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fixWorldWeather(WorldServer p_fixWorldWeather_1_) {
/* 590 */     WorldInfo worldinfo = p_fixWorldWeather_1_.getWorldInfo();
/*     */     
/* 592 */     if (worldinfo.isRaining() || worldinfo.isThundering()) {
/*     */       
/* 594 */       worldinfo.setRainTime(0);
/* 595 */       worldinfo.setRaining(false);
/* 596 */       p_fixWorldWeather_1_.setRainStrength(0.0F);
/* 597 */       worldinfo.setThunderTime(0);
/* 598 */       worldinfo.setThundering(false);
/* 599 */       p_fixWorldWeather_1_.setThunderStrength(0.0F);
/* 600 */       getConfigurationManager().sendPacketToAllPlayers((Packet)new S2BPacketChangeGameState(2, 0.0F));
/* 601 */       getConfigurationManager().sendPacketToAllPlayers((Packet)new S2BPacketChangeGameState(7, 0.0F));
/* 602 */       getConfigurationManager().sendPacketToAllPlayers((Packet)new S2BPacketChangeGameState(8, 0.0F));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fixWorldTime(WorldServer p_fixWorldTime_1_) {
/* 608 */     WorldInfo worldinfo = p_fixWorldTime_1_.getWorldInfo();
/*     */     
/* 610 */     if (worldinfo.getGameType().getID() == 1) {
/*     */       
/* 612 */       long i = p_fixWorldTime_1_.getWorldTime();
/* 613 */       long j = i % 24000L;
/*     */       
/* 615 */       if (Config.isTimeDayOnly()) {
/*     */         
/* 617 */         if (j <= 1000L)
/*     */         {
/* 619 */           p_fixWorldTime_1_.setWorldTime(i - j + 1001L);
/*     */         }
/*     */         
/* 622 */         if (j >= 11000L)
/*     */         {
/* 624 */           p_fixWorldTime_1_.setWorldTime(i - j + 24001L);
/*     */         }
/*     */       } 
/*     */       
/* 628 */       if (Config.isTimeNightOnly()) {
/*     */         
/* 630 */         if (j <= 14000L)
/*     */         {
/* 632 */           p_fixWorldTime_1_.setWorldTime(i - j + 14001L);
/*     */         }
/*     */         
/* 635 */         if (j >= 22000L)
/*     */         {
/* 637 */           p_fixWorldTime_1_.setWorldTime(i - j + 24000L + 14001L);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\server\integrated\IntegratedServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
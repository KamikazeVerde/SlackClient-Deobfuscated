/*      */ package net.minecraft.server;
/*      */ import com.google.common.base.Charsets;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Queues;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import com.google.common.util.concurrent.ListenableFuture;
/*      */ import com.google.common.util.concurrent.ListenableFutureTask;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import com.mojang.authlib.GameProfileRepository;
/*      */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*      */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufOutputStream;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.handler.codec.base64.Base64;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.net.Proxy;
/*      */ import java.security.KeyPair;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import java.util.Queue;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.Executors;
/*      */ import java.util.concurrent.FutureTask;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.command.CommandBase;
/*      */ import net.minecraft.command.CommandResultStats;
/*      */ import net.minecraft.command.ICommandManager;
/*      */ import net.minecraft.command.ICommandSender;
/*      */ import net.minecraft.command.ServerCommandManager;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.network.NetworkSystem;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.ServerStatusResponse;
/*      */ import net.minecraft.network.play.server.S03PacketTimeUpdate;
/*      */ import net.minecraft.profiler.IPlayerUsage;
/*      */ import net.minecraft.profiler.PlayerUsageSnooper;
/*      */ import net.minecraft.profiler.Profiler;
/*      */ import net.minecraft.server.management.PlayerProfileCache;
/*      */ import net.minecraft.server.management.ServerConfigurationManager;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.IProgressUpdate;
/*      */ import net.minecraft.util.IThreadListener;
/*      */ import net.minecraft.util.ITickable;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.Util;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.IWorldAccess;
/*      */ import net.minecraft.world.MinecraftException;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldManager;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.minecraft.world.WorldServerMulti;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.WorldType;
/*      */ import net.minecraft.world.chunk.storage.AnvilSaveConverter;
/*      */ import net.minecraft.world.storage.ISaveFormat;
/*      */ import net.minecraft.world.storage.ISaveHandler;
/*      */ import net.minecraft.world.storage.WorldInfo;
/*      */ import org.apache.commons.lang3.Validate;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public abstract class MinecraftServer implements Runnable, ICommandSender, IThreadListener, IPlayerUsage {
/*   81 */   private static final Logger logger = LogManager.getLogger();
/*   82 */   public static final File USER_CACHE_FILE = new File("usercache.json");
/*      */ 
/*      */   
/*      */   private static MinecraftServer mcServer;
/*      */   
/*      */   private final ISaveFormat anvilConverterForAnvilFile;
/*      */   
/*   89 */   private final PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("server", this, getCurrentTimeMillis());
/*      */   private final File anvilFile;
/*   91 */   private final List<ITickable> playersOnline = Lists.newArrayList();
/*      */   protected final ICommandManager commandManager;
/*   93 */   public final Profiler theProfiler = new Profiler();
/*      */   private final NetworkSystem networkSystem;
/*   95 */   private final ServerStatusResponse statusResponse = new ServerStatusResponse();
/*   96 */   private final Random random = new Random();
/*      */ 
/*      */   
/*   99 */   private int serverPort = -1;
/*      */ 
/*      */   
/*      */   public WorldServer[] worldServers;
/*      */ 
/*      */   
/*      */   private ServerConfigurationManager serverConfigManager;
/*      */ 
/*      */   
/*      */   private boolean serverRunning = true;
/*      */ 
/*      */   
/*      */   private boolean serverStopped;
/*      */ 
/*      */   
/*      */   private int tickCounter;
/*      */ 
/*      */   
/*      */   protected final Proxy serverProxy;
/*      */ 
/*      */   
/*      */   public String currentTask;
/*      */ 
/*      */   
/*      */   public int percentDone;
/*      */ 
/*      */   
/*      */   private boolean onlineMode;
/*      */ 
/*      */   
/*      */   private boolean canSpawnAnimals;
/*      */ 
/*      */   
/*      */   private boolean canSpawnNPCs;
/*      */ 
/*      */   
/*      */   private boolean pvpEnabled;
/*      */ 
/*      */   
/*      */   private boolean allowFlight;
/*      */ 
/*      */   
/*      */   private String motd;
/*      */   
/*      */   private int buildLimit;
/*      */   
/*  145 */   private int maxPlayerIdleMinutes = 0;
/*  146 */   public final long[] tickTimeArray = new long[100];
/*      */ 
/*      */   
/*      */   public long[][] timeOfLastDimensionTick;
/*      */ 
/*      */   
/*      */   private KeyPair serverKeyPair;
/*      */   
/*      */   private String serverOwner;
/*      */   
/*      */   private String folderName;
/*      */   
/*      */   private String worldName;
/*      */   
/*      */   private boolean enableBonusChest;
/*      */   
/*      */   private boolean worldIsBeingDeleted;
/*      */   
/*  164 */   private String resourcePackUrl = "";
/*  165 */   private String resourcePackHash = "";
/*      */   
/*      */   private boolean serverIsRunning;
/*      */   
/*      */   private long timeOfLastWarning;
/*      */   
/*      */   private String userMessage;
/*      */   
/*      */   private boolean startProfiling;
/*      */   private boolean isGamemodeForced;
/*      */   private final YggdrasilAuthenticationService authService;
/*      */   private final MinecraftSessionService sessionService;
/*  177 */   private long nanoTimeSinceStatusRefresh = 0L;
/*      */   private final GameProfileRepository profileRepo;
/*      */   private final PlayerProfileCache profileCache;
/*  180 */   protected final Queue<FutureTask<?>> futureTaskQueue = Queues.newArrayDeque();
/*      */   private Thread serverThread;
/*  182 */   private long currentTime = getCurrentTimeMillis();
/*      */ 
/*      */   
/*      */   public MinecraftServer(Proxy proxy, File workDir) {
/*  186 */     this.serverProxy = proxy;
/*  187 */     mcServer = this;
/*  188 */     this.anvilFile = null;
/*  189 */     this.networkSystem = null;
/*  190 */     this.profileCache = new PlayerProfileCache(this, workDir);
/*  191 */     this.commandManager = null;
/*  192 */     this.anvilConverterForAnvilFile = null;
/*  193 */     this.authService = new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString());
/*  194 */     this.sessionService = this.authService.createMinecraftSessionService();
/*  195 */     this.profileRepo = this.authService.createProfileRepository();
/*      */   }
/*      */ 
/*      */   
/*      */   public MinecraftServer(File workDir, Proxy proxy, File profileCacheDir) {
/*  200 */     this.serverProxy = proxy;
/*  201 */     mcServer = this;
/*  202 */     this.anvilFile = workDir;
/*  203 */     this.networkSystem = new NetworkSystem(this);
/*  204 */     this.profileCache = new PlayerProfileCache(this, profileCacheDir);
/*  205 */     this.commandManager = (ICommandManager)createNewCommandManager();
/*  206 */     this.anvilConverterForAnvilFile = (ISaveFormat)new AnvilSaveConverter(workDir);
/*  207 */     this.authService = new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString());
/*  208 */     this.sessionService = this.authService.createMinecraftSessionService();
/*  209 */     this.profileRepo = this.authService.createProfileRepository();
/*      */   }
/*      */ 
/*      */   
/*      */   protected ServerCommandManager createNewCommandManager() {
/*  214 */     return new ServerCommandManager();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void convertMapIfNeeded(String worldNameIn) {
/*  224 */     if (getActiveAnvilConverter().isOldMapFormat(worldNameIn)) {
/*      */       
/*  226 */       logger.info("Converting map!");
/*  227 */       setUserMessage("menu.convertingLevel");
/*  228 */       getActiveAnvilConverter().convertMapFormat(worldNameIn, new IProgressUpdate()
/*      */           {
/*  230 */             private long startTime = System.currentTimeMillis();
/*      */ 
/*      */             
/*      */             public void displaySavingString(String message) {}
/*      */ 
/*      */             
/*      */             public void resetProgressAndMessage(String message) {}
/*      */             
/*      */             public void setLoadingProgress(int progress) {
/*  239 */               if (System.currentTimeMillis() - this.startTime >= 1000L) {
/*      */                 
/*  241 */                 this.startTime = System.currentTimeMillis();
/*  242 */                 MinecraftServer.logger.info("Converting... " + progress + "%");
/*      */               } 
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public void setDoneWorking() {}
/*      */ 
/*      */ 
/*      */             
/*      */             public void displayLoadingString(String message) {}
/*      */           });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized void setUserMessage(String message) {
/*  260 */     this.userMessage = message;
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized String getUserMessage() {
/*  265 */     return this.userMessage;
/*      */   }
/*      */   
/*      */   protected void loadAllWorlds(String p_71247_1_, String p_71247_2_, long seed, WorldType type, String p_71247_6_) {
/*      */     WorldSettings worldsettings;
/*  270 */     convertMapIfNeeded(p_71247_1_);
/*  271 */     setUserMessage("menu.loadingLevel");
/*  272 */     this.worldServers = new WorldServer[3];
/*  273 */     this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
/*  274 */     ISaveHandler isavehandler = this.anvilConverterForAnvilFile.getSaveLoader(p_71247_1_, true);
/*  275 */     setResourcePackFromWorld(getFolderName(), isavehandler);
/*  276 */     WorldInfo worldinfo = isavehandler.loadWorldInfo();
/*      */ 
/*      */     
/*  279 */     if (worldinfo == null) {
/*      */       
/*  281 */       worldsettings = new WorldSettings(seed, getGameType(), canStructuresSpawn(), isHardcore(), type);
/*  282 */       worldsettings.setWorldName(p_71247_6_);
/*      */       
/*  284 */       if (this.enableBonusChest)
/*      */       {
/*  286 */         worldsettings.enableBonusChest();
/*      */       }
/*      */       
/*  289 */       worldinfo = new WorldInfo(worldsettings, p_71247_2_);
/*      */     }
/*      */     else {
/*      */       
/*  293 */       worldinfo.setWorldName(p_71247_2_);
/*  294 */       worldsettings = new WorldSettings(worldinfo);
/*      */     } 
/*      */     
/*  297 */     for (int i = 0; i < this.worldServers.length; i++) {
/*      */       
/*  299 */       int j = 0;
/*      */       
/*  301 */       if (i == 1)
/*      */       {
/*  303 */         j = -1;
/*      */       }
/*      */       
/*  306 */       if (i == 2)
/*      */       {
/*  308 */         j = 1;
/*      */       }
/*      */       
/*  311 */       if (i == 0) {
/*      */         
/*  313 */         this.worldServers[i] = (WorldServer)(new WorldServer(this, isavehandler, worldinfo, j, this.theProfiler)).init();
/*  314 */         this.worldServers[i].initialize(worldsettings);
/*      */       }
/*      */       else {
/*      */         
/*  318 */         this.worldServers[i] = (WorldServer)(new WorldServerMulti(this, isavehandler, j, this.worldServers[0], this.theProfiler)).init();
/*      */       } 
/*      */       
/*  321 */       this.worldServers[i].addWorldAccess((IWorldAccess)new WorldManager(this, this.worldServers[i]));
/*      */       
/*  323 */       if (!isSinglePlayer())
/*      */       {
/*  325 */         this.worldServers[i].getWorldInfo().setGameType(getGameType());
/*      */       }
/*      */     } 
/*      */     
/*  329 */     this.serverConfigManager.setPlayerManager(this.worldServers);
/*  330 */     setDifficultyForAllWorlds(getDifficulty());
/*  331 */     initialWorldChunkLoad();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void initialWorldChunkLoad() {
/*  336 */     int i = 16;
/*  337 */     int j = 4;
/*  338 */     int k = 192;
/*  339 */     int l = 625;
/*  340 */     int i1 = 0;
/*  341 */     setUserMessage("menu.generatingTerrain");
/*  342 */     int j1 = 0;
/*  343 */     logger.info("Preparing start region for level " + j1);
/*  344 */     WorldServer worldserver = this.worldServers[j1];
/*  345 */     BlockPos blockpos = worldserver.getSpawnPoint();
/*  346 */     long k1 = getCurrentTimeMillis();
/*      */     
/*  348 */     for (int l1 = -192; l1 <= 192 && isServerRunning(); l1 += 16) {
/*      */       
/*  350 */       for (int i2 = -192; i2 <= 192 && isServerRunning(); i2 += 16) {
/*      */         
/*  352 */         long j2 = getCurrentTimeMillis();
/*      */         
/*  354 */         if (j2 - k1 > 1000L) {
/*      */           
/*  356 */           outputPercentRemaining("Preparing spawn area", i1 * 100 / 625);
/*  357 */           k1 = j2;
/*      */         } 
/*      */         
/*  360 */         i1++;
/*  361 */         worldserver.theChunkProviderServer.loadChunk(blockpos.getX() + l1 >> 4, blockpos.getZ() + i2 >> 4);
/*      */       } 
/*      */     } 
/*      */     
/*  365 */     clearCurrentTask();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setResourcePackFromWorld(String worldNameIn, ISaveHandler saveHandlerIn) {
/*  370 */     File file1 = new File(saveHandlerIn.getWorldDirectory(), "resources.zip");
/*      */     
/*  372 */     if (file1.isFile())
/*      */     {
/*  374 */       setResourcePack("level://" + worldNameIn + "/" + file1.getName(), "");
/*      */     }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void outputPercentRemaining(String message, int percent) {
/*  403 */     this.currentTask = message;
/*  404 */     this.percentDone = percent;
/*  405 */     logger.info(message + ": " + percent + "%");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void clearCurrentTask() {
/*  413 */     this.currentTask = null;
/*  414 */     this.percentDone = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void saveAllWorlds(boolean dontLog) {
/*  422 */     if (!this.worldIsBeingDeleted)
/*      */     {
/*  424 */       for (WorldServer worldserver : this.worldServers) {
/*      */         
/*  426 */         if (worldserver != null) {
/*      */           
/*  428 */           if (!dontLog)
/*      */           {
/*  430 */             logger.info("Saving chunks for level '" + worldserver.getWorldInfo().getWorldName() + "'/" + worldserver.provider.getDimensionName());
/*      */           }
/*      */ 
/*      */           
/*      */           try {
/*  435 */             worldserver.saveAllChunks(true, null);
/*      */           }
/*  437 */           catch (MinecraftException minecraftexception) {
/*      */             
/*  439 */             logger.warn(minecraftexception.getMessage());
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
/*      */   public void stopServer() {
/*  451 */     if (!this.worldIsBeingDeleted) {
/*      */       
/*  453 */       logger.info("Stopping server");
/*      */       
/*  455 */       if (getNetworkSystem() != null)
/*      */       {
/*  457 */         getNetworkSystem().terminateEndpoints();
/*      */       }
/*      */       
/*  460 */       if (this.serverConfigManager != null) {
/*      */         
/*  462 */         logger.info("Saving players");
/*  463 */         this.serverConfigManager.saveAllPlayerData();
/*  464 */         this.serverConfigManager.removeAllPlayers();
/*      */       } 
/*      */       
/*  467 */       if (this.worldServers != null) {
/*      */         
/*  469 */         logger.info("Saving worlds");
/*  470 */         saveAllWorlds(false);
/*      */         
/*  472 */         for (int i = 0; i < this.worldServers.length; i++) {
/*      */           
/*  474 */           WorldServer worldserver = this.worldServers[i];
/*  475 */           worldserver.flush();
/*      */         } 
/*      */       } 
/*      */       
/*  479 */       if (this.usageSnooper.isSnooperRunning())
/*      */       {
/*  481 */         this.usageSnooper.stopSnooper();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isServerRunning() {
/*  488 */     return this.serverRunning;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initiateShutdown() {
/*  496 */     this.serverRunning = false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setInstance() {
/*  501 */     mcServer = this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void run() {
/*      */     try {
/*  508 */       if (startServer()) {
/*      */         
/*  510 */         this.currentTime = getCurrentTimeMillis();
/*  511 */         long i = 0L;
/*  512 */         this.statusResponse.setServerDescription((IChatComponent)new ChatComponentText(this.motd));
/*  513 */         this.statusResponse.setProtocolVersionInfo(new ServerStatusResponse.MinecraftProtocolVersionIdentifier("1.8.9", 47));
/*  514 */         addFaviconToStatusResponse(this.statusResponse);
/*      */         
/*  516 */         while (this.serverRunning)
/*      */         {
/*  518 */           long k = getCurrentTimeMillis();
/*  519 */           long j = k - this.currentTime;
/*      */           
/*  521 */           if (j > 2000L && this.currentTime - this.timeOfLastWarning >= 15000L) {
/*      */             
/*  523 */             logger.warn("Can't keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", new Object[] { Long.valueOf(j), Long.valueOf(j / 50L) });
/*  524 */             j = 2000L;
/*  525 */             this.timeOfLastWarning = this.currentTime;
/*      */           } 
/*      */           
/*  528 */           if (j < 0L) {
/*      */             
/*  530 */             logger.warn("Time ran backwards! Did the system time change?");
/*  531 */             j = 0L;
/*      */           } 
/*      */           
/*  534 */           i += j;
/*  535 */           this.currentTime = k;
/*      */           
/*  537 */           if (this.worldServers[0].areAllPlayersAsleep()) {
/*      */             
/*  539 */             tick();
/*  540 */             i = 0L;
/*      */           }
/*      */           else {
/*      */             
/*  544 */             while (i > 50L) {
/*      */               
/*  546 */               i -= 50L;
/*  547 */               tick();
/*      */             } 
/*      */           } 
/*      */           
/*  551 */           Thread.sleep(Math.max(1L, 50L - i));
/*  552 */           this.serverIsRunning = true;
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  557 */         finalTick(null);
/*      */       }
/*      */     
/*  560 */     } catch (Throwable throwable1) {
/*      */       
/*  562 */       logger.error("Encountered an unexpected exception", throwable1);
/*  563 */       CrashReport crashreport = null;
/*      */       
/*  565 */       if (throwable1 instanceof ReportedException) {
/*      */         
/*  567 */         crashreport = addServerInfoToCrashReport(((ReportedException)throwable1).getCrashReport());
/*      */       }
/*      */       else {
/*      */         
/*  571 */         crashreport = addServerInfoToCrashReport(new CrashReport("Exception in server tick loop", throwable1));
/*      */       } 
/*      */       
/*  574 */       File file1 = new File(new File(getDataDirectory(), "crash-reports"), "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-server.txt");
/*      */       
/*  576 */       if (crashreport.saveToFile(file1)) {
/*      */         
/*  578 */         logger.error("This crash report has been saved to: " + file1.getAbsolutePath());
/*      */       }
/*      */       else {
/*      */         
/*  582 */         logger.error("We were unable to save this crash report to disk.");
/*      */       } 
/*      */       
/*  585 */       finalTick(crashreport);
/*      */     } finally {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/*  591 */         this.serverStopped = true;
/*  592 */         stopServer();
/*      */       }
/*  594 */       catch (Throwable throwable) {
/*      */         
/*  596 */         logger.error("Exception stopping the server", throwable);
/*      */       }
/*      */       finally {
/*      */         
/*  600 */         systemExitNow();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void addFaviconToStatusResponse(ServerStatusResponse response) {
/*  607 */     File file1 = getFile("server-icon.png");
/*      */     
/*  609 */     if (file1.isFile()) {
/*      */       
/*  611 */       ByteBuf bytebuf = Unpooled.buffer();
/*      */ 
/*      */       
/*      */       try {
/*  615 */         BufferedImage bufferedimage = ImageIO.read(file1);
/*  616 */         Validate.validState((bufferedimage.getWidth() == 64), "Must be 64 pixels wide", new Object[0]);
/*  617 */         Validate.validState((bufferedimage.getHeight() == 64), "Must be 64 pixels high", new Object[0]);
/*  618 */         ImageIO.write(bufferedimage, "PNG", (OutputStream)new ByteBufOutputStream(bytebuf));
/*  619 */         ByteBuf bytebuf1 = Base64.encode(bytebuf);
/*  620 */         response.setFavicon("data:image/png;base64," + bytebuf1.toString(Charsets.UTF_8));
/*      */       }
/*  622 */       catch (Exception exception) {
/*      */         
/*  624 */         logger.error("Couldn't load server icon", exception);
/*      */       }
/*      */       finally {
/*      */         
/*  628 */         bytebuf.release();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public File getDataDirectory() {
/*  635 */     return new File(".");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void finalTick(CrashReport report) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void systemExitNow() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void tick() {
/*  657 */     long i = System.nanoTime();
/*  658 */     this.tickCounter++;
/*      */     
/*  660 */     if (this.startProfiling) {
/*      */       
/*  662 */       this.startProfiling = false;
/*  663 */       this.theProfiler.profilingEnabled = true;
/*  664 */       this.theProfiler.clearProfiling();
/*      */     } 
/*      */     
/*  667 */     this.theProfiler.startSection("root");
/*  668 */     updateTimeLightAndEntities();
/*      */     
/*  670 */     if (i - this.nanoTimeSinceStatusRefresh >= 5000000000L) {
/*      */       
/*  672 */       this.nanoTimeSinceStatusRefresh = i;
/*  673 */       this.statusResponse.setPlayerCountData(new ServerStatusResponse.PlayerCountData(getMaxPlayers(), getCurrentPlayerCount()));
/*  674 */       GameProfile[] agameprofile = new GameProfile[Math.min(getCurrentPlayerCount(), 12)];
/*  675 */       int j = MathHelper.getRandomIntegerInRange(this.random, 0, getCurrentPlayerCount() - agameprofile.length);
/*      */       
/*  677 */       for (int k = 0; k < agameprofile.length; k++)
/*      */       {
/*  679 */         agameprofile[k] = ((EntityPlayerMP)this.serverConfigManager.func_181057_v().get(j + k)).getGameProfile();
/*      */       }
/*      */       
/*  682 */       Collections.shuffle(Arrays.asList((Object[])agameprofile));
/*  683 */       this.statusResponse.getPlayerCountData().setPlayers(agameprofile);
/*      */     } 
/*      */     
/*  686 */     if (this.tickCounter % 900 == 0) {
/*      */       
/*  688 */       this.theProfiler.startSection("save");
/*  689 */       this.serverConfigManager.saveAllPlayerData();
/*  690 */       saveAllWorlds(true);
/*  691 */       this.theProfiler.endSection();
/*      */     } 
/*      */     
/*  694 */     this.theProfiler.startSection("tallying");
/*  695 */     this.tickTimeArray[this.tickCounter % 100] = System.nanoTime() - i;
/*  696 */     this.theProfiler.endSection();
/*  697 */     this.theProfiler.startSection("snooper");
/*      */     
/*  699 */     if (!this.usageSnooper.isSnooperRunning() && this.tickCounter > 100)
/*      */     {
/*  701 */       this.usageSnooper.startSnooper();
/*      */     }
/*      */     
/*  704 */     if (this.tickCounter % 6000 == 0)
/*      */     {
/*  706 */       this.usageSnooper.addMemoryStatsToSnooper();
/*      */     }
/*      */     
/*  709 */     this.theProfiler.endSection();
/*  710 */     this.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateTimeLightAndEntities() {
/*  715 */     this.theProfiler.startSection("jobs");
/*      */     
/*  717 */     synchronized (this.futureTaskQueue) {
/*      */       
/*  719 */       while (!this.futureTaskQueue.isEmpty())
/*      */       {
/*  721 */         Util.func_181617_a(this.futureTaskQueue.poll(), logger);
/*      */       }
/*      */     } 
/*      */     
/*  725 */     this.theProfiler.endStartSection("levels");
/*      */     
/*  727 */     for (int j = 0; j < this.worldServers.length; j++) {
/*      */       
/*  729 */       long i = System.nanoTime();
/*      */       
/*  731 */       if (j == 0 || getAllowNether()) {
/*      */         
/*  733 */         WorldServer worldserver = this.worldServers[j];
/*  734 */         this.theProfiler.startSection(worldserver.getWorldInfo().getWorldName());
/*      */         
/*  736 */         if (this.tickCounter % 20 == 0) {
/*      */           
/*  738 */           this.theProfiler.startSection("timeSync");
/*  739 */           this.serverConfigManager.sendPacketToAllPlayersInDimension((Packet)new S03PacketTimeUpdate(worldserver.getTotalWorldTime(), worldserver.getWorldTime(), worldserver.getGameRules().getGameRuleBooleanValue("doDaylightCycle")), worldserver.provider.getDimensionId());
/*  740 */           this.theProfiler.endSection();
/*      */         } 
/*      */         
/*  743 */         this.theProfiler.startSection("tick");
/*      */ 
/*      */         
/*      */         try {
/*  747 */           worldserver.tick();
/*      */         }
/*  749 */         catch (Throwable throwable1) {
/*      */           
/*  751 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Exception ticking world");
/*  752 */           worldserver.addWorldInfoToCrashReport(crashreport);
/*  753 */           throw new ReportedException(crashreport);
/*      */         } 
/*      */ 
/*      */         
/*      */         try {
/*  758 */           worldserver.updateEntities();
/*      */         }
/*  760 */         catch (Throwable throwable) {
/*      */           
/*  762 */           CrashReport crashreport1 = CrashReport.makeCrashReport(throwable, "Exception ticking world entities");
/*  763 */           worldserver.addWorldInfoToCrashReport(crashreport1);
/*  764 */           throw new ReportedException(crashreport1);
/*      */         } 
/*      */         
/*  767 */         this.theProfiler.endSection();
/*  768 */         this.theProfiler.startSection("tracker");
/*  769 */         worldserver.getEntityTracker().updateTrackedEntities();
/*  770 */         this.theProfiler.endSection();
/*  771 */         this.theProfiler.endSection();
/*      */       } 
/*      */       
/*  774 */       this.timeOfLastDimensionTick[j][this.tickCounter % 100] = System.nanoTime() - i;
/*      */     } 
/*      */     
/*  777 */     this.theProfiler.endStartSection("connection");
/*  778 */     getNetworkSystem().networkTick();
/*  779 */     this.theProfiler.endStartSection("players");
/*  780 */     this.serverConfigManager.onTick();
/*  781 */     this.theProfiler.endStartSection("tickables");
/*      */     
/*  783 */     for (int k = 0; k < this.playersOnline.size(); k++)
/*      */     {
/*  785 */       ((ITickable)this.playersOnline.get(k)).update();
/*      */     }
/*      */     
/*  788 */     this.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAllowNether() {
/*  793 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void startServerThread() {
/*  798 */     this.serverThread = new Thread(this, "Server thread");
/*  799 */     this.serverThread.start();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File getFile(String fileName) {
/*  807 */     return new File(getDataDirectory(), fileName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void logWarning(String msg) {
/*  815 */     logger.warn(msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldServer worldServerForDimension(int dimension) {
/*  823 */     return (dimension == -1) ? this.worldServers[1] : ((dimension == 1) ? this.worldServers[2] : this.worldServers[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMinecraftVersion() {
/*  831 */     return "1.8.9";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCurrentPlayerCount() {
/*  839 */     return this.serverConfigManager.getCurrentPlayerCount();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxPlayers() {
/*  847 */     return this.serverConfigManager.getMaxPlayers();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getAllUsernames() {
/*  855 */     return this.serverConfigManager.getAllUsernames();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GameProfile[] getGameProfiles() {
/*  863 */     return this.serverConfigManager.getAllProfiles();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getServerModName() {
/*  868 */     return "vanilla";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CrashReport addServerInfoToCrashReport(CrashReport report) {
/*  876 */     report.getCategory().addCrashSectionCallable("Profiler Position", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/*  880 */             return MinecraftServer.this.theProfiler.profilingEnabled ? MinecraftServer.this.theProfiler.getNameOfLastSection() : "N/A (disabled)";
/*      */           }
/*      */         });
/*      */     
/*  884 */     if (this.serverConfigManager != null)
/*      */     {
/*  886 */       report.getCategory().addCrashSectionCallable("Player Count", new Callable<String>()
/*      */           {
/*      */             public String call()
/*      */             {
/*  890 */               return MinecraftServer.this.serverConfigManager.getCurrentPlayerCount() + " / " + MinecraftServer.this.serverConfigManager.getMaxPlayers() + "; " + MinecraftServer.this.serverConfigManager.func_181057_v();
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*  895 */     return report;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<String> getTabCompletions(ICommandSender sender, String input, BlockPos pos) {
/*  900 */     List<String> list = Lists.newArrayList();
/*      */     
/*  902 */     if (input.startsWith("/")) {
/*      */       
/*  904 */       input = input.substring(1);
/*  905 */       boolean flag = !input.contains(" ");
/*  906 */       List<String> list1 = this.commandManager.getTabCompletionOptions(sender, input, pos);
/*      */       
/*  908 */       if (list1 != null)
/*      */       {
/*  910 */         for (String s2 : list1) {
/*      */           
/*  912 */           if (flag) {
/*      */             
/*  914 */             list.add("/" + s2);
/*      */             
/*      */             continue;
/*      */           } 
/*  918 */           list.add(s2);
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  923 */       return list;
/*      */     } 
/*      */ 
/*      */     
/*  927 */     String[] astring = input.split(" ", -1);
/*  928 */     String s = astring[astring.length - 1];
/*      */     
/*  930 */     for (String s1 : this.serverConfigManager.getAllUsernames()) {
/*      */       
/*  932 */       if (CommandBase.doesStringStartWith(s, s1))
/*      */       {
/*  934 */         list.add(s1);
/*      */       }
/*      */     } 
/*      */     
/*  938 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MinecraftServer getServer() {
/*  947 */     return mcServer;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAnvilFileSet() {
/*  952 */     return (this.anvilFile != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCommandSenderName() {
/*  960 */     return "Server";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChatMessage(IChatComponent component) {
/*  970 */     logger.info(component.getUnformattedText());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/*  981 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public ICommandManager getCommandManager() {
/*  986 */     return this.commandManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyPair getKeyPair() {
/*  994 */     return this.serverKeyPair;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getServerOwner() {
/* 1002 */     return this.serverOwner;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setServerOwner(String owner) {
/* 1010 */     this.serverOwner = owner;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSinglePlayer() {
/* 1015 */     return (this.serverOwner != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getFolderName() {
/* 1020 */     return this.folderName;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFolderName(String name) {
/* 1025 */     this.folderName = name;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setWorldName(String p_71246_1_) {
/* 1030 */     this.worldName = p_71246_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getWorldName() {
/* 1035 */     return this.worldName;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setKeyPair(KeyPair keyPair) {
/* 1040 */     this.serverKeyPair = keyPair;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDifficultyForAllWorlds(EnumDifficulty difficulty) {
/* 1045 */     for (int i = 0; i < this.worldServers.length; i++) {
/*      */       
/* 1047 */       WorldServer worldServer = this.worldServers[i];
/*      */       
/* 1049 */       if (worldServer != null)
/*      */       {
/* 1051 */         if (worldServer.getWorldInfo().isHardcoreModeEnabled()) {
/*      */           
/* 1053 */           worldServer.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
/* 1054 */           worldServer.setAllowedSpawnTypes(true, true);
/*      */         }
/* 1056 */         else if (isSinglePlayer()) {
/*      */           
/* 1058 */           worldServer.getWorldInfo().setDifficulty(difficulty);
/* 1059 */           worldServer.setAllowedSpawnTypes((worldServer.getDifficulty() != EnumDifficulty.PEACEFUL), true);
/*      */         }
/*      */         else {
/*      */           
/* 1063 */           worldServer.getWorldInfo().setDifficulty(difficulty);
/* 1064 */           worldServer.setAllowedSpawnTypes(allowSpawnMonsters(), this.canSpawnAnimals);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean allowSpawnMonsters() {
/* 1072 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void canCreateBonusChest(boolean enable) {
/* 1077 */     this.enableBonusChest = enable;
/*      */   }
/*      */ 
/*      */   
/*      */   public ISaveFormat getActiveAnvilConverter() {
/* 1082 */     return this.anvilConverterForAnvilFile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteWorldAndStopServer() {
/* 1091 */     this.worldIsBeingDeleted = true;
/* 1092 */     getActiveAnvilConverter().flushCache();
/*      */     
/* 1094 */     for (int i = 0; i < this.worldServers.length; i++) {
/*      */       
/* 1096 */       WorldServer worldserver = this.worldServers[i];
/*      */       
/* 1098 */       if (worldserver != null)
/*      */       {
/* 1100 */         worldserver.flush();
/*      */       }
/*      */     } 
/*      */     
/* 1104 */     getActiveAnvilConverter().deleteWorldDirectory(this.worldServers[0].getSaveHandler().getWorldDirectoryName());
/* 1105 */     initiateShutdown();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getResourcePackUrl() {
/* 1110 */     return this.resourcePackUrl;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getResourcePackHash() {
/* 1115 */     return this.resourcePackHash;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setResourcePack(String url, String hash) {
/* 1120 */     this.resourcePackUrl = url;
/* 1121 */     this.resourcePackHash = hash;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addServerStatsToSnooper(PlayerUsageSnooper playerSnooper) {
/* 1126 */     playerSnooper.addClientStat("whitelist_enabled", Boolean.FALSE);
/* 1127 */     playerSnooper.addClientStat("whitelist_count", Integer.valueOf(0));
/*      */     
/* 1129 */     if (this.serverConfigManager != null) {
/*      */       
/* 1131 */       playerSnooper.addClientStat("players_current", Integer.valueOf(getCurrentPlayerCount()));
/* 1132 */       playerSnooper.addClientStat("players_max", Integer.valueOf(getMaxPlayers()));
/* 1133 */       playerSnooper.addClientStat("players_seen", Integer.valueOf((this.serverConfigManager.getAvailablePlayerDat()).length));
/*      */     } 
/*      */     
/* 1136 */     playerSnooper.addClientStat("uses_auth", Boolean.valueOf(this.onlineMode));
/* 1137 */     playerSnooper.addClientStat("gui_state", getGuiEnabled() ? "enabled" : "disabled");
/* 1138 */     playerSnooper.addClientStat("run_time", Long.valueOf((getCurrentTimeMillis() - playerSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L));
/* 1139 */     playerSnooper.addClientStat("avg_tick_ms", Integer.valueOf((int)(MathHelper.average(this.tickTimeArray) * 1.0E-6D)));
/* 1140 */     int i = 0;
/*      */     
/* 1142 */     if (this.worldServers != null)
/*      */     {
/* 1144 */       for (int j = 0; j < this.worldServers.length; j++) {
/*      */         
/* 1146 */         if (this.worldServers[j] != null) {
/*      */           
/* 1148 */           WorldServer worldserver = this.worldServers[j];
/* 1149 */           WorldInfo worldinfo = worldserver.getWorldInfo();
/* 1150 */           playerSnooper.addClientStat("world[" + i + "][dimension]", Integer.valueOf(worldserver.provider.getDimensionId()));
/* 1151 */           playerSnooper.addClientStat("world[" + i + "][mode]", worldinfo.getGameType());
/* 1152 */           playerSnooper.addClientStat("world[" + i + "][difficulty]", worldserver.getDifficulty());
/* 1153 */           playerSnooper.addClientStat("world[" + i + "][hardcore]", Boolean.valueOf(worldinfo.isHardcoreModeEnabled()));
/* 1154 */           playerSnooper.addClientStat("world[" + i + "][generator_name]", worldinfo.getTerrainType().getWorldTypeName());
/* 1155 */           playerSnooper.addClientStat("world[" + i + "][generator_version]", Integer.valueOf(worldinfo.getTerrainType().getGeneratorVersion()));
/* 1156 */           playerSnooper.addClientStat("world[" + i + "][height]", Integer.valueOf(this.buildLimit));
/* 1157 */           playerSnooper.addClientStat("world[" + i + "][chunks_loaded]", Integer.valueOf(worldserver.getChunkProvider().getLoadedChunkCount()));
/* 1158 */           i++;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1163 */     playerSnooper.addClientStat("worlds", Integer.valueOf(i));
/*      */   }
/*      */ 
/*      */   
/*      */   public void addServerTypeToSnooper(PlayerUsageSnooper playerSnooper) {
/* 1168 */     playerSnooper.addStatToSnooper("singleplayer", Boolean.valueOf(isSinglePlayer()));
/* 1169 */     playerSnooper.addStatToSnooper("server_brand", getServerModName());
/* 1170 */     playerSnooper.addStatToSnooper("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
/* 1171 */     playerSnooper.addStatToSnooper("dedicated", Boolean.valueOf(isDedicatedServer()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSnooperEnabled() {
/* 1179 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isServerInOnlineMode() {
/* 1186 */     return this.onlineMode;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setOnlineMode(boolean online) {
/* 1191 */     this.onlineMode = online;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getCanSpawnAnimals() {
/* 1196 */     return this.canSpawnAnimals;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCanSpawnAnimals(boolean spawnAnimals) {
/* 1201 */     this.canSpawnAnimals = spawnAnimals;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getCanSpawnNPCs() {
/* 1206 */     return this.canSpawnNPCs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCanSpawnNPCs(boolean spawnNpcs) {
/* 1213 */     this.canSpawnNPCs = spawnNpcs;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPVPEnabled() {
/* 1218 */     return this.pvpEnabled;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAllowPvp(boolean allowPvp) {
/* 1223 */     this.pvpEnabled = allowPvp;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFlightAllowed() {
/* 1228 */     return this.allowFlight;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAllowFlight(boolean allow) {
/* 1233 */     this.allowFlight = allow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMOTD() {
/* 1243 */     return this.motd;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMOTD(String motdIn) {
/* 1248 */     this.motd = motdIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBuildLimit() {
/* 1253 */     return this.buildLimit;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBuildLimit(int maxBuildHeight) {
/* 1258 */     this.buildLimit = maxBuildHeight;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_71241_aa() {
/* 1263 */     return this.serverStopped;
/*      */   }
/*      */ 
/*      */   
/*      */   public ServerConfigurationManager getConfigurationManager() {
/* 1268 */     return this.serverConfigManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setConfigManager(ServerConfigurationManager configManager) {
/* 1273 */     this.serverConfigManager = configManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGameType(WorldSettings.GameType gameMode) {
/* 1281 */     for (int i = 0; i < this.worldServers.length; i++)
/*      */     {
/* 1283 */       (getServer()).worldServers[i].getWorldInfo().setGameType(gameMode);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public NetworkSystem getNetworkSystem() {
/* 1289 */     return this.networkSystem;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean serverIsInRunLoop() {
/* 1294 */     return this.serverIsRunning;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getGuiEnabled() {
/* 1299 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTickCounter() {
/* 1309 */     return this.tickCounter;
/*      */   }
/*      */ 
/*      */   
/*      */   public void enableProfiling() {
/* 1314 */     this.startProfiling = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public PlayerUsageSnooper getPlayerUsageSnooper() {
/* 1319 */     return this.usageSnooper;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getPosition() {
/* 1328 */     return BlockPos.ORIGIN;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getPositionVector() {
/* 1337 */     return new Vec3(0.0D, 0.0D, 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public World getEntityWorld() {
/* 1346 */     return (World)this.worldServers[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity getCommandSenderEntity() {
/* 1354 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSpawnProtectionSize() {
/* 1362 */     return 16;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockProtected(World worldIn, BlockPos pos, EntityPlayer playerIn) {
/* 1367 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getForceGamemode() {
/* 1372 */     return this.isGamemodeForced;
/*      */   }
/*      */ 
/*      */   
/*      */   public Proxy getServerProxy() {
/* 1377 */     return this.serverProxy;
/*      */   }
/*      */ 
/*      */   
/*      */   public static long getCurrentTimeMillis() {
/* 1382 */     return System.currentTimeMillis();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxPlayerIdleMinutes() {
/* 1387 */     return this.maxPlayerIdleMinutes;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPlayerIdleTimeout(int idleTimeout) {
/* 1392 */     this.maxPlayerIdleMinutes = idleTimeout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChatComponent getDisplayName() {
/* 1400 */     return (IChatComponent)new ChatComponentText(getCommandSenderName());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAnnouncingPlayerAchievements() {
/* 1405 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public MinecraftSessionService getMinecraftSessionService() {
/* 1410 */     return this.sessionService;
/*      */   }
/*      */ 
/*      */   
/*      */   public GameProfileRepository getGameProfileRepository() {
/* 1415 */     return this.profileRepo;
/*      */   }
/*      */ 
/*      */   
/*      */   public PlayerProfileCache getPlayerProfileCache() {
/* 1420 */     return this.profileCache;
/*      */   }
/*      */ 
/*      */   
/*      */   public ServerStatusResponse getServerStatusResponse() {
/* 1425 */     return this.statusResponse;
/*      */   }
/*      */ 
/*      */   
/*      */   public void refreshStatusNextTick() {
/* 1430 */     this.nanoTimeSinceStatusRefresh = 0L;
/*      */   }
/*      */ 
/*      */   
/*      */   public Entity getEntityFromUuid(UUID uuid) {
/* 1435 */     for (WorldServer worldserver : this.worldServers) {
/*      */       
/* 1437 */       if (worldserver != null) {
/*      */         
/* 1439 */         Entity entity = worldserver.getEntityFromUuid(uuid);
/*      */         
/* 1441 */         if (entity != null)
/*      */         {
/* 1443 */           return entity;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1448 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean sendCommandFeedback() {
/* 1456 */     return (getServer()).worldServers[0].getGameRules().getGameRuleBooleanValue("sendCommandFeedback");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCommandStat(CommandResultStats.Type type, int amount) {}
/*      */ 
/*      */   
/*      */   public int getMaxWorldSize() {
/* 1465 */     return 29999984;
/*      */   }
/*      */ 
/*      */   
/*      */   public <V> ListenableFuture<V> callFromMainThread(Callable<V> callable) {
/* 1470 */     Validate.notNull(callable);
/*      */     
/* 1472 */     if (!isCallingFromMinecraftThread() && !func_71241_aa()) {
/*      */       
/* 1474 */       ListenableFutureTask<V> listenablefuturetask = ListenableFutureTask.create(callable);
/*      */       
/* 1476 */       synchronized (this.futureTaskQueue) {
/*      */         
/* 1478 */         this.futureTaskQueue.add(listenablefuturetask);
/* 1479 */         return (ListenableFuture<V>)listenablefuturetask;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1486 */       return Futures.immediateFuture(callable.call());
/*      */     }
/* 1488 */     catch (Exception exception) {
/*      */       
/* 1490 */       return (ListenableFuture<V>)Futures.immediateFailedCheckedFuture(exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ListenableFuture<Object> addScheduledTask(Runnable runnableToSchedule) {
/* 1497 */     Validate.notNull(runnableToSchedule);
/* 1498 */     return callFromMainThread(Executors.callable(runnableToSchedule));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCallingFromMinecraftThread() {
/* 1503 */     return (Thread.currentThread() == this.serverThread);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNetworkCompressionTreshold() {
/* 1511 */     return 256;
/*      */   }
/*      */   
/*      */   protected abstract boolean startServer() throws IOException;
/*      */   
/*      */   public abstract boolean canStructuresSpawn();
/*      */   
/*      */   public abstract WorldSettings.GameType getGameType();
/*      */   
/*      */   public abstract EnumDifficulty getDifficulty();
/*      */   
/*      */   public abstract boolean isHardcore();
/*      */   
/*      */   public abstract int getOpPermissionLevel();
/*      */   
/*      */   public abstract boolean func_181034_q();
/*      */   
/*      */   public abstract boolean func_183002_r();
/*      */   
/*      */   public abstract boolean isDedicatedServer();
/*      */   
/*      */   public abstract boolean func_181035_ah();
/*      */   
/*      */   public abstract boolean isCommandBlockEnabled();
/*      */   
/*      */   public abstract String shareToLAN(WorldSettings.GameType paramGameType, boolean paramBoolean);
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\server\MinecraftServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
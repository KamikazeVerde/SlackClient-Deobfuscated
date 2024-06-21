/*      */ package net.minecraft.client;
/*      */ 
/*      */ import cc.slack.Slack;
/*      */ import cc.slack.events.State;
/*      */ import cc.slack.events.impl.game.TickEvent;
/*      */ import cc.slack.events.impl.input.KeyEvent;
/*      */ import cc.slack.features.modules.impl.exploit.MultiAction;
/*      */ import cc.slack.features.modules.impl.other.Tweaks;
/*      */ import cc.slack.features.modules.impl.world.FastPlace;
/*      */ import cc.slack.utils.player.ItemSpoofUtil;
/*      */ import cc.slack.utils.player.MovementUtil;
/*      */ import cc.slack.utils.player.RotationUtil;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Multimap;
/*      */ import com.google.common.collect.Queues;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import com.google.common.util.concurrent.ListenableFuture;
/*      */ import com.google.common.util.concurrent.ListenableFutureTask;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*      */ import com.mojang.authlib.properties.PropertyMap;
/*      */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*      */ import de.florianmichael.viamcp.fixes.AttackOrder;
/*      */ import java.awt.Image;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.Proxy;
/*      */ import java.net.SocketAddress;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Queue;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.Executors;
/*      */ import java.util.concurrent.FutureTask;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.client.audio.MusicTicker;
/*      */ import net.minecraft.client.audio.SoundHandler;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.FontRenderer;
/*      */ import net.minecraft.client.gui.GuiChat;
/*      */ import net.minecraft.client.gui.GuiControls;
/*      */ import net.minecraft.client.gui.GuiGameOver;
/*      */ import net.minecraft.client.gui.GuiIngame;
/*      */ import net.minecraft.client.gui.GuiIngameMenu;
/*      */ import net.minecraft.client.gui.GuiMainMenu;
/*      */ import net.minecraft.client.gui.GuiMemoryErrorScreen;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.GuiSleepMP;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.gui.achievement.GuiAchievement;
/*      */ import net.minecraft.client.gui.inventory.GuiInventory;
/*      */ import net.minecraft.client.main.GameConfiguration;
/*      */ import net.minecraft.client.multiplayer.GuiConnecting;
/*      */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*      */ import net.minecraft.client.multiplayer.ServerData;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.network.NetHandlerLoginClient;
/*      */ import net.minecraft.client.network.NetHandlerPlayClient;
/*      */ import net.minecraft.client.particle.EffectRenderer;
/*      */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*      */ import net.minecraft.client.renderer.EntityRenderer;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.ItemRenderer;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.RenderGlobal;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*      */ import net.minecraft.client.renderer.entity.RenderItem;
/*      */ import net.minecraft.client.renderer.entity.RenderManager;
/*      */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*      */ import net.minecraft.client.renderer.texture.ITickableTextureObject;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.DefaultResourcePack;
/*      */ import net.minecraft.client.resources.FoliageColorReloadListener;
/*      */ import net.minecraft.client.resources.GrassColorReloadListener;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.resources.IReloadableResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.client.resources.IResourcePack;
/*      */ import net.minecraft.client.resources.LanguageManager;
/*      */ import net.minecraft.client.resources.ResourceIndex;
/*      */ import net.minecraft.client.resources.ResourcePackRepository;
/*      */ import net.minecraft.client.resources.SimpleReloadableResourceManager;
/*      */ import net.minecraft.client.resources.SkinManager;
/*      */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*      */ import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.FontMetadataSection;
/*      */ import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.IMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*      */ import net.minecraft.client.resources.data.LanguageMetadataSection;
/*      */ import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.PackMetadataSection;
/*      */ import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.TextureMetadataSection;
/*      */ import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.model.ModelManager;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.client.settings.KeyBinding;
/*      */ import net.minecraft.client.shader.Framebuffer;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.boss.BossStatus;
/*      */ import net.minecraft.entity.item.EntityItemFrame;
/*      */ import net.minecraft.entity.item.EntityMinecart;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.InventoryPlayer;
/*      */ import net.minecraft.init.Bootstrap;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemArmorStand;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.nbt.NBTTagString;
/*      */ import net.minecraft.network.EnumConnectionState;
/*      */ import net.minecraft.network.INetHandler;
/*      */ import net.minecraft.network.NetworkManager;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.handshake.client.C00Handshake;
/*      */ import net.minecraft.network.login.client.C00PacketLoginStart;
/*      */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*      */ import net.minecraft.profiler.IPlayerUsage;
/*      */ import net.minecraft.profiler.PlayerUsageSnooper;
/*      */ import net.minecraft.profiler.Profiler;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.integrated.IntegratedServer;
/*      */ import net.minecraft.stats.AchievementList;
/*      */ import net.minecraft.stats.IStatStringFormat;
/*      */ import net.minecraft.stats.StatFileWriter;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.FrameTimer;
/*      */ import net.minecraft.util.IThreadListener;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MinecraftError;
/*      */ import net.minecraft.util.MouseHelper;
/*      */ import net.minecraft.util.MovementInput;
/*      */ import net.minecraft.util.MovementInputFromOptions;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.ScreenShotHelper;
/*      */ import net.minecraft.util.Session;
/*      */ import net.minecraft.util.Timer;
/*      */ import net.minecraft.util.Util;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.chunk.storage.AnvilSaveConverter;
/*      */ import net.minecraft.world.storage.ISaveFormat;
/*      */ import net.minecraft.world.storage.ISaveHandler;
/*      */ import net.minecraft.world.storage.WorldInfo;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.apache.commons.lang3.Validate;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.LWJGLException;
/*      */ import org.lwjgl.Sys;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import org.lwjgl.opengl.ContextCapabilities;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.DisplayMode;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.opengl.OpenGLException;
/*      */ import org.lwjgl.opengl.PixelFormat;
/*      */ import org.lwjgl.util.glu.GLU;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Minecraft
/*      */   implements IThreadListener, IPlayerUsage
/*      */ {
/*  198 */   private static final Logger logger = LogManager.getLogger();
/*  199 */   private static final ResourceLocation locationMojangPng = new ResourceLocation("textures/gui/title/mojang.png");
/*  200 */   public static final boolean isRunningOnMac = (Util.getOSType() == Util.EnumOS.OSX);
/*      */ 
/*      */   
/*  203 */   public static byte[] memoryReserve = new byte[10485760];
/*  204 */   private static final List<DisplayMode> macDisplayModes = Lists.newArrayList((Object[])new DisplayMode[] { new DisplayMode(2560, 1600), new DisplayMode(2880, 1800) });
/*      */   
/*      */   private final File fileResourcepacks;
/*      */   
/*      */   private final PropertyMap field_181038_N;
/*      */   
/*      */   private ServerData currentServerData;
/*      */   
/*      */   private TextureManager renderEngine;
/*      */   
/*      */   private static Minecraft theMinecraft;
/*      */   
/*      */   public PlayerControllerMP playerController;
/*      */   
/*      */   private boolean fullscreen;
/*      */   
/*      */   private boolean enableGLErrorChecking = true;
/*      */   private boolean hasCrashed;
/*      */   private CrashReport crashReporter;
/*      */   public int displayWidth;
/*      */   public int displayHeight;
/*      */   private boolean field_181541_X = false;
/*  226 */   public Timer timer = new Timer(20.0F);
/*      */ 
/*      */   
/*  229 */   private PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("client", this, MinecraftServer.getCurrentTimeMillis());
/*      */   
/*      */   public WorldClient theWorld;
/*      */   
/*      */   public RenderGlobal renderGlobal;
/*      */   
/*      */   private RenderManager renderManager;
/*      */   
/*      */   private RenderItem renderItem;
/*      */   
/*      */   private ItemRenderer itemRenderer;
/*      */   
/*      */   public EntityPlayerSP thePlayer;
/*      */   
/*      */   private Entity renderViewEntity;
/*      */   
/*      */   public Entity pointedEntity;
/*      */   
/*      */   public EffectRenderer effectRenderer;
/*      */   
/*      */   public Session session;
/*      */   
/*      */   private boolean isGamePaused;
/*      */   
/*      */   public FontRenderer MCfontRenderer;
/*      */   
/*      */   public FontRenderer standardGalacticFontRenderer;
/*      */   
/*      */   public GuiScreen currentScreen;
/*      */   
/*      */   public LoadingScreenRenderer loadingScreen;
/*      */   
/*      */   public EntityRenderer entityRenderer;
/*      */   
/*      */   public int leftClickCounter;
/*      */   
/*      */   private int tempDisplayWidth;
/*      */   
/*      */   private int tempDisplayHeight;
/*      */   
/*      */   private IntegratedServer theIntegratedServer;
/*      */   
/*      */   public GuiAchievement guiAchievement;
/*      */   
/*      */   public GuiIngame ingameGUI;
/*      */   
/*      */   public boolean skipRenderWorld;
/*      */   
/*      */   public MovingObjectPosition objectMouseOver;
/*      */   
/*      */   public GameSettings gameSettings;
/*      */   
/*      */   public MouseHelper mouseHelper;
/*      */   
/*      */   public final File mcDataDir;
/*      */   
/*      */   private final File fileAssets;
/*      */   
/*      */   private final String launchedVersion;
/*      */   
/*      */   private final Proxy proxy;
/*      */   
/*      */   private ISaveFormat saveLoader;
/*      */   
/*      */   private static int debugFPS;
/*      */   
/*      */   private int rightClickDelayTimer;
/*      */   
/*      */   private String serverName;
/*      */   
/*      */   private int serverPort;
/*      */   public boolean inGameHasFocus;
/*  301 */   long systemTime = getSystemTime();
/*      */   
/*      */   private int joinPlayerCounter;
/*      */   
/*  305 */   public final FrameTimer field_181542_y = new FrameTimer();
/*  306 */   long field_181543_z = System.nanoTime();
/*      */   
/*      */   private final boolean jvm64bit;
/*      */   
/*      */   private NetworkManager myNetworkManager;
/*      */   private boolean integratedServerIsRunning;
/*  312 */   public final Profiler mcProfiler = new Profiler();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  317 */   private long debugCrashKeyPressTime = -1L;
/*      */   private IReloadableResourceManager mcResourceManager;
/*  319 */   private final IMetadataSerializer metadataSerializer_ = new IMetadataSerializer();
/*  320 */   private final List<IResourcePack> defaultResourcePacks = Lists.newArrayList();
/*      */   public final DefaultResourcePack mcDefaultResourcePack;
/*      */   private ResourcePackRepository mcResourcePackRepository;
/*      */   private LanguageManager mcLanguageManager;
/*      */   private Framebuffer framebufferMc;
/*      */   private TextureMap textureMapBlocks;
/*      */   private SoundHandler mcSoundHandler;
/*      */   private MusicTicker mcMusicTicker;
/*      */   private ResourceLocation mojangLogo;
/*      */   private final MinecraftSessionService sessionService;
/*      */   private SkinManager skinManager;
/*  331 */   private final Queue<FutureTask<?>> scheduledTasks = Queues.newArrayDeque();
/*  332 */   private long field_175615_aJ = 0L;
/*  333 */   private final Thread mcThread = Thread.currentThread();
/*      */ 
/*      */ 
/*      */   
/*      */   private ModelManager modelManager;
/*      */ 
/*      */ 
/*      */   
/*      */   private BlockRendererDispatcher blockRenderDispatcher;
/*      */ 
/*      */   
/*      */   volatile boolean running = true;
/*      */ 
/*      */   
/*  347 */   public String debug = "";
/*      */   
/*      */   public boolean field_175613_B = false;
/*      */   
/*      */   public boolean field_175614_C = false;
/*      */   public boolean field_175611_D = false;
/*      */   public boolean renderChunksMany = true;
/*  354 */   long debugUpdateTime = getSystemTime();
/*      */   
/*      */   int fpsCounter;
/*      */   
/*  358 */   long prevFrameTime = -1L;
/*      */ 
/*      */   
/*  361 */   private String debugProfilerName = "root";
/*      */ 
/*      */   
/*      */   public Minecraft(GameConfiguration gameConfig) {
/*  365 */     theMinecraft = this;
/*  366 */     this.mcDataDir = gameConfig.folderInfo.mcDataDir;
/*  367 */     this.fileAssets = gameConfig.folderInfo.assetsDir;
/*  368 */     this.fileResourcepacks = gameConfig.folderInfo.resourcePacksDir;
/*  369 */     this.launchedVersion = gameConfig.gameInfo.version;
/*  370 */     this.field_181038_N = gameConfig.userInfo.field_181172_c;
/*  371 */     this.mcDefaultResourcePack = new DefaultResourcePack((new ResourceIndex(gameConfig.folderInfo.assetsDir, gameConfig.folderInfo.assetIndex)).getResourceMap());
/*  372 */     this.proxy = (gameConfig.userInfo.proxy == null) ? Proxy.NO_PROXY : gameConfig.userInfo.proxy;
/*  373 */     this.sessionService = (new YggdrasilAuthenticationService(gameConfig.userInfo.proxy, UUID.randomUUID().toString())).createMinecraftSessionService();
/*  374 */     this.session = gameConfig.userInfo.session;
/*  375 */     logger.info("Setting user: " + this.session.getUsername());
/*  376 */     logger.info("(Session ID is " + this.session.getSessionID() + ")");
/*  377 */     this.displayWidth = (gameConfig.displayInfo.width > 0) ? gameConfig.displayInfo.width : 1;
/*  378 */     this.displayHeight = (gameConfig.displayInfo.height > 0) ? gameConfig.displayInfo.height : 1;
/*  379 */     this.tempDisplayWidth = gameConfig.displayInfo.width;
/*  380 */     this.tempDisplayHeight = gameConfig.displayInfo.height;
/*  381 */     this.fullscreen = gameConfig.displayInfo.fullscreen;
/*  382 */     this.jvm64bit = isJvm64bit();
/*  383 */     this.theIntegratedServer = new IntegratedServer(this);
/*      */     
/*  385 */     if (gameConfig.serverInfo.serverName != null) {
/*      */       
/*  387 */       this.serverName = gameConfig.serverInfo.serverName;
/*  388 */       this.serverPort = gameConfig.serverInfo.serverPort;
/*      */     } 
/*      */     
/*  391 */     ImageIO.setUseCache(false);
/*  392 */     Bootstrap.register();
/*      */   }
/*      */ 
/*      */   
/*      */   public void run() {
/*  397 */     this.running = true;
/*      */ 
/*      */     
/*      */     try {
/*  401 */       startGame();
/*      */     }
/*  403 */     catch (Throwable throwable) {
/*      */       
/*  405 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Initializing game");
/*  406 */       crashreport.makeCategory("Initialization");
/*  407 */       displayCrashReport(addGraphicsAndWorldToCrashReport(crashreport));
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  415 */       while (this.running)
/*      */       {
/*  417 */         if (!this.hasCrashed || this.crashReporter == null) {
/*      */ 
/*      */           
/*      */           try {
/*  421 */             runGameLoop();
/*      */           }
/*  423 */           catch (OutOfMemoryError var10) {
/*      */             
/*  425 */             freeMemory();
/*  426 */             displayGuiScreen((GuiScreen)new GuiMemoryErrorScreen());
/*  427 */             System.gc();
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/*  432 */         displayCrashReport(this.crashReporter);
/*      */       }
/*      */     
/*      */     }
/*  436 */     catch (MinecraftError var12) {
/*      */ 
/*      */     
/*      */     }
/*  440 */     catch (ReportedException reportedexception) {
/*      */       
/*  442 */       addGraphicsAndWorldToCrashReport(reportedexception.getCrashReport());
/*  443 */       freeMemory();
/*  444 */       logger.fatal("Reported exception thrown!", (Throwable)reportedexception);
/*  445 */       displayCrashReport(reportedexception.getCrashReport());
/*      */     
/*      */     }
/*  448 */     catch (Throwable throwable1) {
/*      */       
/*  450 */       CrashReport crashreport1 = addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", throwable1));
/*  451 */       freeMemory();
/*  452 */       logger.fatal("Unreported exception thrown!", throwable1);
/*  453 */       displayCrashReport(crashreport1);
/*      */     
/*      */     }
/*      */     finally {
/*      */       
/*  458 */       shutdownMinecraftApplet();
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
/*      */   private void startGame() throws LWJGLException, IOException {
/*  470 */     this.gameSettings = new GameSettings(this, this.mcDataDir);
/*  471 */     this.defaultResourcePacks.add(this.mcDefaultResourcePack);
/*  472 */     startTimerHackThread();
/*      */     
/*  474 */     if (this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0) {
/*      */       
/*  476 */       this.displayWidth = this.gameSettings.overrideWidth;
/*  477 */       this.displayHeight = this.gameSettings.overrideHeight;
/*      */     } 
/*      */     
/*  480 */     logger.info("LWJGL Version: " + Sys.getVersion());
/*  481 */     setWindowIcon();
/*  482 */     setInitialDisplayMode();
/*  483 */     createDisplay();
/*  484 */     OpenGlHelper.initializeTextures();
/*  485 */     this.framebufferMc = new Framebuffer(this.displayWidth, this.displayHeight, true);
/*  486 */     this.framebufferMc.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
/*  487 */     registerMetadataSerializers();
/*  488 */     this.mcResourcePackRepository = new ResourcePackRepository(this.fileResourcepacks, new File(this.mcDataDir, "server-resource-packs"), (IResourcePack)this.mcDefaultResourcePack, this.metadataSerializer_, this.gameSettings);
/*  489 */     this.mcResourceManager = (IReloadableResourceManager)new SimpleReloadableResourceManager(this.metadataSerializer_);
/*  490 */     this.mcLanguageManager = new LanguageManager(this.metadataSerializer_, this.gameSettings.language);
/*  491 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.mcLanguageManager);
/*  492 */     refreshResources();
/*  493 */     this.renderEngine = new TextureManager((IResourceManager)this.mcResourceManager);
/*  494 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.renderEngine);
/*  495 */     drawSplashScreen(this.renderEngine);
/*  496 */     this.skinManager = new SkinManager(this.renderEngine, new File(this.fileAssets, "skins"), this.sessionService);
/*  497 */     this.saveLoader = (ISaveFormat)new AnvilSaveConverter(new File(this.mcDataDir, "saves"));
/*  498 */     this.mcSoundHandler = new SoundHandler((IResourceManager)this.mcResourceManager, this.gameSettings);
/*  499 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.mcSoundHandler);
/*  500 */     this.mcMusicTicker = new MusicTicker(this);
/*  501 */     this.MCfontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.renderEngine, false);
/*      */     
/*  503 */     if (this.gameSettings.language != null) {
/*      */       
/*  505 */       this.MCfontRenderer.setUnicodeFlag(isUnicode());
/*  506 */       this.MCfontRenderer.setBidiFlag(this.mcLanguageManager.isCurrentLanguageBidirectional());
/*      */     } 
/*      */     
/*  509 */     this.standardGalacticFontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), this.renderEngine, false);
/*  510 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.MCfontRenderer);
/*  511 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.standardGalacticFontRenderer);
/*  512 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)new GrassColorReloadListener());
/*  513 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)new FoliageColorReloadListener());
/*  514 */     AchievementList.openInventory.setStatStringFormatter(new IStatStringFormat()
/*      */         {
/*      */           
/*      */           public String formatString(String p_74535_1_)
/*      */           {
/*      */             try {
/*  520 */               return String.format(p_74535_1_, new Object[] { GameSettings.getKeyDisplayString(this.this$0.gameSettings.keyBindInventory.getKeyCode()) });
/*      */             }
/*  522 */             catch (Exception exception) {
/*      */               
/*  524 */               return "Error: " + exception.getLocalizedMessage();
/*      */             } 
/*      */           }
/*      */         });
/*  528 */     this.mouseHelper = new MouseHelper();
/*  529 */     checkGLError("Pre startup");
/*  530 */     GlStateManager.enableTexture2D();
/*  531 */     GlStateManager.shadeModel(7425);
/*  532 */     GlStateManager.clearDepth(1.0D);
/*  533 */     GlStateManager.enableDepth();
/*  534 */     GlStateManager.depthFunc(515);
/*  535 */     GlStateManager.enableAlpha();
/*  536 */     GlStateManager.alphaFunc(516, 0.1F);
/*  537 */     GlStateManager.cullFace(1029);
/*  538 */     GlStateManager.matrixMode(5889);
/*  539 */     GlStateManager.loadIdentity();
/*  540 */     GlStateManager.matrixMode(5888);
/*  541 */     checkGLError("Startup");
/*  542 */     this.textureMapBlocks = new TextureMap("textures");
/*  543 */     this.textureMapBlocks.setMipmapLevels(this.gameSettings.mipmapLevels);
/*  544 */     this.renderEngine.loadTickableTexture(TextureMap.locationBlocksTexture, (ITickableTextureObject)this.textureMapBlocks);
/*  545 */     this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
/*  546 */     this.textureMapBlocks.setBlurMipmapDirect(false, (this.gameSettings.mipmapLevels > 0));
/*  547 */     this.modelManager = new ModelManager(this.textureMapBlocks);
/*  548 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.modelManager);
/*  549 */     this.renderItem = new RenderItem(this.renderEngine, this.modelManager);
/*  550 */     this.renderManager = new RenderManager(this.renderEngine, this.renderItem);
/*  551 */     this.itemRenderer = new ItemRenderer(this);
/*  552 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.renderItem);
/*  553 */     this.entityRenderer = new EntityRenderer(this, (IResourceManager)this.mcResourceManager);
/*  554 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.entityRenderer);
/*  555 */     this.blockRenderDispatcher = new BlockRendererDispatcher(this.modelManager.getBlockModelShapes(), this.gameSettings);
/*  556 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.blockRenderDispatcher);
/*  557 */     this.renderGlobal = new RenderGlobal(this);
/*  558 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.renderGlobal);
/*  559 */     this.guiAchievement = new GuiAchievement(this);
/*  560 */     GlStateManager.viewport(0, 0, this.displayWidth, this.displayHeight);
/*  561 */     this.effectRenderer = new EffectRenderer((World)this.theWorld, this.renderEngine);
/*  562 */     checkGLError("Post startup");
/*  563 */     this.ingameGUI = new GuiIngame(this);
/*      */     
/*  565 */     Slack.getInstance().start();
/*      */     
/*  567 */     if (this.serverName != null) {
/*      */       
/*  569 */       displayGuiScreen((GuiScreen)new GuiConnecting((GuiScreen)new GuiMainMenu(), this, this.serverName, this.serverPort));
/*      */     }
/*      */     else {
/*      */       
/*  573 */       displayGuiScreen((GuiScreen)new GuiMainMenu());
/*      */     } 
/*      */     
/*  576 */     this.renderEngine.deleteTexture(this.mojangLogo);
/*  577 */     this.mojangLogo = null;
/*  578 */     this.loadingScreen = new LoadingScreenRenderer(this);
/*      */     
/*  580 */     if (this.gameSettings.fullScreen && !this.fullscreen)
/*      */     {
/*  582 */       toggleFullscreen();
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  587 */       Display.setVSyncEnabled(this.gameSettings.enableVsync);
/*      */     }
/*  589 */     catch (OpenGLException var2) {
/*      */       
/*  591 */       this.gameSettings.enableVsync = false;
/*  592 */       this.gameSettings.saveOptions();
/*      */     } 
/*      */     
/*  595 */     this.renderGlobal.makeEntityOutlineShader();
/*      */   }
/*      */ 
/*      */   
/*      */   private void registerMetadataSerializers() {
/*  600 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
/*  601 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new FontMetadataSectionSerializer(), FontMetadataSection.class);
/*  602 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
/*  603 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new PackMetadataSectionSerializer(), PackMetadataSection.class);
/*  604 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
/*      */   }
/*      */ 
/*      */   
/*      */   private void createDisplay() throws LWJGLException {
/*  609 */     Display.setResizable(true);
/*  610 */     Display.setTitle("Loading " + Slack.getInstance().getInfo().getName() + " " + Slack.getInstance().getInfo().getVersion() + "-" + Slack.getInstance().getInfo().getType());
/*      */ 
/*      */     
/*      */     try {
/*  614 */       Display.create((new PixelFormat()).withDepthBits(24));
/*      */     }
/*  616 */     catch (LWJGLException lwjglexception) {
/*      */       
/*  618 */       logger.error("Couldn't set pixel format", (Throwable)lwjglexception);
/*      */ 
/*      */       
/*      */       try {
/*  622 */         Thread.sleep(1000L);
/*      */       }
/*  624 */       catch (InterruptedException interruptedException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  629 */       if (this.fullscreen)
/*      */       {
/*  631 */         updateDisplayMode();
/*      */       }
/*      */       
/*  634 */       Display.create();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setInitialDisplayMode() throws LWJGLException {
/*  640 */     if (this.fullscreen) {
/*      */       
/*  642 */       Display.setFullscreen(true);
/*  643 */       DisplayMode displaymode = Display.getDisplayMode();
/*  644 */       this.displayWidth = Math.max(1, displaymode.getWidth());
/*  645 */       this.displayHeight = Math.max(1, displaymode.getHeight());
/*      */     }
/*      */     else {
/*      */       
/*  649 */       Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setWindowIcon() {
/*  655 */     Util.EnumOS util$enumos = Util.getOSType();
/*      */     
/*  657 */     if (util$enumos != Util.EnumOS.OSX) {
/*      */       
/*  659 */       InputStream smallIcon = null;
/*  660 */       InputStream bigIcon = null;
/*      */ 
/*      */       
/*      */       try {
/*  664 */         smallIcon = this.mcDefaultResourcePack.getInputStream(new ResourceLocation("slack/textures/logo/16.png"));
/*  665 */         bigIcon = this.mcDefaultResourcePack.getInputStream(new ResourceLocation("slack/textures/logo/32.png"));
/*  666 */         if (smallIcon != null && bigIcon != null)
/*      */         {
/*  668 */           Display.setIcon(new ByteBuffer[] { readImageToBuffer(smallIcon), readImageToBuffer(bigIcon) });
/*      */         }
/*      */       }
/*  671 */       catch (IOException ioexception) {
/*      */         
/*  673 */         logger.error("Couldn't set icon", ioexception);
/*      */       }
/*      */       finally {
/*      */         
/*  677 */         IOUtils.closeQuietly(smallIcon);
/*  678 */         IOUtils.closeQuietly(bigIcon);
/*      */       } 
/*      */     } else {
/*      */       
/*      */       try {
/*  683 */         InputStream icon = this.mcDefaultResourcePack.getInputStream(new ResourceLocation("slack/textures/logo/32.png"));
/*  684 */         if (icon != null)
/*      */         { 
/*  686 */           try { Class<?> Application = Class.forName("com.apple.eawt.Application");
/*  687 */             Application.getMethod("setDockIconImage", new Class[] { Image.class }).invoke(Application.getMethod("getApplication", new Class[0]).invoke(null, new Object[0]), new Object[] { ImageIO.read(icon) }); }
/*  688 */           catch (Exception e) { System.err.println("[ IconUtils ] Error setting dock icon: " + e.getMessage()); }
/*  689 */            IOUtils.closeQuietly(icon); }
/*  690 */         else { System.err.println("[ IconUtils ] Icon file could not be found"); }
/*      */       
/*  692 */       } catch (IOException ioexception) {
/*      */         
/*  694 */         logger.error("Couldn't set icon", ioexception);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isJvm64bit() {
/*  701 */     String[] astring = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
/*      */     
/*  703 */     for (String s : astring) {
/*      */       
/*  705 */       String s1 = System.getProperty(s);
/*      */       
/*  707 */       if (s1 != null && s1.contains("64"))
/*      */       {
/*  709 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  713 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public Framebuffer getFramebuffer() {
/*  718 */     return this.framebufferMc;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getVersion() {
/*  723 */     return this.launchedVersion;
/*      */   }
/*      */ 
/*      */   
/*      */   private void startTimerHackThread() {
/*  728 */     Thread thread = new Thread("Timer hack thread")
/*      */       {
/*      */         public void run()
/*      */         {
/*  732 */           while (Minecraft.this.running) {
/*      */ 
/*      */             
/*      */             try {
/*  736 */               Thread.sleep(2147483647L);
/*      */             }
/*  738 */             catch (InterruptedException interruptedException) {}
/*      */           } 
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */     
/*  745 */     thread.setDaemon(true);
/*  746 */     thread.start();
/*      */   }
/*      */ 
/*      */   
/*      */   public void crashed(CrashReport crash) {
/*  751 */     this.hasCrashed = true;
/*  752 */     this.crashReporter = crash;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayCrashReport(CrashReport crashReportIn) {
/*  760 */     File file1 = new File((getMinecraft()).mcDataDir, "crash-reports");
/*  761 */     File file2 = new File(file1, "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-client.txt");
/*  762 */     Bootstrap.printToSYSOUT(crashReportIn.getCompleteReport());
/*      */     
/*  764 */     if (crashReportIn.getFile() != null) {
/*      */       
/*  766 */       Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReportIn.getFile());
/*  767 */       System.exit(-1);
/*      */     }
/*  769 */     else if (crashReportIn.saveToFile(file2)) {
/*      */       
/*  771 */       Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + file2.getAbsolutePath());
/*  772 */       System.exit(-1);
/*      */     }
/*      */     else {
/*      */       
/*  776 */       Bootstrap.printToSYSOUT("#@?@# Game crashed! Crash report could not be saved. #@?@#");
/*  777 */       System.exit(-2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isUnicode() {
/*  783 */     return (this.mcLanguageManager.isCurrentLocaleUnicode() || this.gameSettings.forceUnicodeFont);
/*      */   }
/*      */ 
/*      */   
/*      */   public void refreshResources() {
/*  788 */     List<IResourcePack> list = Lists.newArrayList(this.defaultResourcePacks);
/*      */     
/*  790 */     for (ResourcePackRepository.Entry resourcepackrepository$entry : this.mcResourcePackRepository.getRepositoryEntries())
/*      */     {
/*  792 */       list.add(resourcepackrepository$entry.getResourcePack());
/*      */     }
/*      */     
/*  795 */     if (this.mcResourcePackRepository.getResourcePackInstance() != null)
/*      */     {
/*  797 */       list.add(this.mcResourcePackRepository.getResourcePackInstance());
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  802 */       this.mcResourceManager.reloadResources(list);
/*      */     }
/*  804 */     catch (RuntimeException runtimeexception) {
/*      */       
/*  806 */       logger.info("Caught error stitching, removing all assigned resourcepacks", runtimeexception);
/*  807 */       list.clear();
/*  808 */       list.addAll(this.defaultResourcePacks);
/*  809 */       this.mcResourcePackRepository.setRepositories(Collections.emptyList());
/*  810 */       this.mcResourceManager.reloadResources(list);
/*  811 */       this.gameSettings.resourcePacks.clear();
/*  812 */       this.gameSettings.field_183018_l.clear();
/*  813 */       this.gameSettings.saveOptions();
/*      */     } 
/*      */     
/*  816 */     this.mcLanguageManager.parseLanguageMetadata(list);
/*      */     
/*  818 */     if (this.renderGlobal != null)
/*      */     {
/*  820 */       this.renderGlobal.loadRenderers();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException {
/*  826 */     BufferedImage bufferedimage = ImageIO.read(imageStream);
/*  827 */     int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
/*  828 */     ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
/*      */     
/*  830 */     for (int i : aint)
/*      */     {
/*  832 */       bytebuffer.putInt(i << 8 | i >> 24 & 0xFF);
/*      */     }
/*      */     
/*  835 */     bytebuffer.flip();
/*  836 */     return bytebuffer;
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateDisplayMode() throws LWJGLException {
/*  841 */     Set<DisplayMode> set = Sets.newHashSet();
/*  842 */     Collections.addAll(set, Display.getAvailableDisplayModes());
/*  843 */     DisplayMode displaymode = Display.getDesktopDisplayMode();
/*      */     
/*  845 */     if (!set.contains(displaymode) && Util.getOSType() == Util.EnumOS.OSX)
/*      */     {
/*      */ 
/*      */       
/*  849 */       for (DisplayMode displaymode1 : macDisplayModes) {
/*      */         
/*  851 */         boolean flag = true;
/*      */         
/*  853 */         for (DisplayMode displaymode2 : set) {
/*      */           
/*  855 */           if (displaymode2.getBitsPerPixel() == 32 && displaymode2.getWidth() == displaymode1.getWidth() && displaymode2.getHeight() == displaymode1.getHeight()) {
/*      */             
/*  857 */             flag = false;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*  862 */         if (!flag) {
/*      */           
/*  864 */           Iterator<DisplayMode> iterator = set.iterator();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  869 */           while (iterator.hasNext()) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  874 */             DisplayMode displaymode3 = iterator.next();
/*      */             
/*  876 */             if (displaymode3.getBitsPerPixel() == 32 && displaymode3.getWidth() == displaymode1.getWidth() / 2 && displaymode3.getHeight() == displaymode1.getHeight() / 2)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  882 */               displaymode = displaymode3; } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*  887 */     Display.setDisplayMode(displaymode);
/*  888 */     this.displayWidth = displaymode.getWidth();
/*  889 */     this.displayHeight = displaymode.getHeight();
/*      */   }
/*      */ 
/*      */   
/*      */   private void drawSplashScreen(TextureManager textureManagerInstance) throws LWJGLException {
/*  894 */     ScaledResolution scaledresolution = new ScaledResolution(this);
/*  895 */     int i = scaledresolution.getScaleFactor();
/*  896 */     Framebuffer framebuffer = new Framebuffer(scaledresolution.getScaledWidth() * i, scaledresolution.getScaledHeight() * i, true);
/*  897 */     framebuffer.bindFramebuffer(false);
/*  898 */     GlStateManager.matrixMode(5889);
/*  899 */     GlStateManager.loadIdentity();
/*  900 */     GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
/*  901 */     GlStateManager.matrixMode(5888);
/*  902 */     GlStateManager.loadIdentity();
/*  903 */     GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/*  904 */     GlStateManager.disableLighting();
/*  905 */     GlStateManager.disableFog();
/*  906 */     GlStateManager.disableDepth();
/*  907 */     GlStateManager.enableTexture2D();
/*  908 */     InputStream inputstream = null;
/*      */ 
/*      */     
/*      */     try {
/*  912 */       inputstream = this.mcDefaultResourcePack.getInputStream(locationMojangPng);
/*  913 */       this.mojangLogo = textureManagerInstance.getDynamicTextureLocation("logo", new DynamicTexture(ImageIO.read(inputstream)));
/*  914 */       textureManagerInstance.bindTexture(this.mojangLogo);
/*      */     }
/*  916 */     catch (IOException ioexception) {
/*      */       
/*  918 */       logger.error("Unable to load logo: " + locationMojangPng, ioexception);
/*      */     }
/*      */     finally {
/*      */       
/*  922 */       IOUtils.closeQuietly(inputstream);
/*      */     } 
/*      */     
/*  925 */     Tessellator tessellator = Tessellator.getInstance();
/*  926 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  927 */     worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/*  928 */     worldrenderer.pos(0.0D, this.displayHeight, 0.0D).tex(0.0D, 0.0D).func_181669_b(255, 255, 255, 255).endVertex();
/*  929 */     worldrenderer.pos(this.displayWidth, this.displayHeight, 0.0D).tex(0.0D, 0.0D).func_181669_b(255, 255, 255, 255).endVertex();
/*  930 */     worldrenderer.pos(this.displayWidth, 0.0D, 0.0D).tex(0.0D, 0.0D).func_181669_b(255, 255, 255, 255).endVertex();
/*  931 */     worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0.0D).func_181669_b(255, 255, 255, 255).endVertex();
/*  932 */     tessellator.draw();
/*  933 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  934 */     int j = 256;
/*  935 */     int k = 256;
/*  936 */     func_181536_a((scaledresolution.getScaledWidth() - j) / 2, (scaledresolution.getScaledHeight() - k) / 2, 0, 0, j, k, 255, 255, 255, 255);
/*  937 */     GlStateManager.disableLighting();
/*  938 */     GlStateManager.disableFog();
/*  939 */     framebuffer.unbindFramebuffer();
/*  940 */     framebuffer.framebufferRender(scaledresolution.getScaledWidth() * i, scaledresolution.getScaledHeight() * i);
/*  941 */     GlStateManager.enableAlpha();
/*  942 */     GlStateManager.alphaFunc(516, 0.1F);
/*  943 */     updateDisplay();
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_181536_a(int p_181536_1_, int p_181536_2_, int p_181536_3_, int p_181536_4_, int p_181536_5_, int p_181536_6_, int p_181536_7_, int p_181536_8_, int p_181536_9_, int p_181536_10_) {
/*  948 */     float f = 0.00390625F;
/*  949 */     float f1 = 0.00390625F;
/*  950 */     WorldRenderer worldrenderer = Tessellator.getInstance().getWorldRenderer();
/*  951 */     worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/*  952 */     worldrenderer.pos(p_181536_1_, (p_181536_2_ + p_181536_6_), 0.0D).tex((p_181536_3_ * f), ((p_181536_4_ + p_181536_6_) * f1)).func_181669_b(p_181536_7_, p_181536_8_, p_181536_9_, p_181536_10_).endVertex();
/*  953 */     worldrenderer.pos((p_181536_1_ + p_181536_5_), (p_181536_2_ + p_181536_6_), 0.0D).tex(((p_181536_3_ + p_181536_5_) * f), ((p_181536_4_ + p_181536_6_) * f1)).func_181669_b(p_181536_7_, p_181536_8_, p_181536_9_, p_181536_10_).endVertex();
/*  954 */     worldrenderer.pos((p_181536_1_ + p_181536_5_), p_181536_2_, 0.0D).tex(((p_181536_3_ + p_181536_5_) * f), (p_181536_4_ * f1)).func_181669_b(p_181536_7_, p_181536_8_, p_181536_9_, p_181536_10_).endVertex();
/*  955 */     worldrenderer.pos(p_181536_1_, p_181536_2_, 0.0D).tex((p_181536_3_ * f), (p_181536_4_ * f1)).func_181669_b(p_181536_7_, p_181536_8_, p_181536_9_, p_181536_10_).endVertex();
/*  956 */     Tessellator.getInstance().draw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ISaveFormat getSaveLoader() {
/*  964 */     return this.saveLoader;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGuiScreen(GuiScreen guiScreenIn) {
/*      */     GuiMainMenu guiMainMenu;
/*      */     GuiGameOver guiGameOver;
/*  972 */     if (this.currentScreen != null)
/*      */     {
/*  974 */       this.currentScreen.onGuiClosed();
/*      */     }
/*      */     
/*  977 */     if (guiScreenIn == null && this.theWorld == null) {
/*      */       
/*  979 */       guiMainMenu = new GuiMainMenu();
/*      */     }
/*  981 */     else if (guiMainMenu == null && this.thePlayer.getHealth() <= 0.0F) {
/*      */       
/*  983 */       guiGameOver = new GuiGameOver();
/*      */     } 
/*      */     
/*  986 */     if (guiGameOver instanceof GuiMainMenu) {
/*      */       
/*  988 */       this.gameSettings.showDebugInfo = false;
/*  989 */       this.ingameGUI.getChatGUI().clearChatMessages();
/*      */     } 
/*      */     
/*  992 */     this.currentScreen = (GuiScreen)guiGameOver;
/*      */     
/*  994 */     if (guiGameOver != null) {
/*      */       
/*  996 */       setIngameNotInFocus();
/*  997 */       ScaledResolution scaledresolution = new ScaledResolution(this);
/*  998 */       int i = scaledresolution.getScaledWidth();
/*  999 */       int j = scaledresolution.getScaledHeight();
/* 1000 */       guiGameOver.setWorldAndResolution(this, i, j);
/* 1001 */       this.skipRenderWorld = false;
/*      */     }
/*      */     else {
/*      */       
/* 1005 */       this.mcSoundHandler.resumeSounds();
/* 1006 */       setIngameFocus();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkGLError(String message) {
/* 1015 */     if (this.enableGLErrorChecking) {
/*      */       
/* 1017 */       int i = GL11.glGetError();
/*      */       
/* 1019 */       if (i != 0) {
/*      */         
/* 1021 */         String s = GLU.gluErrorString(i);
/* 1022 */         logger.error("########## GL ERROR ##########");
/* 1023 */         logger.error("@ " + message);
/* 1024 */         logger.error(i + ": " + s);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void shutdownMinecraftApplet() {
/*      */     try {
/* 1037 */       Slack.getInstance().shutdown();
/* 1038 */       logger.info("Stopping!");
/*      */ 
/*      */       
/*      */       try {
/* 1042 */         loadWorld(null);
/*      */       }
/* 1044 */       catch (Throwable throwable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1049 */       this.mcSoundHandler.unloadSounds();
/*      */     }
/*      */     finally {
/*      */       
/* 1053 */       Display.destroy();
/*      */       
/* 1055 */       if (!this.hasCrashed)
/*      */       {
/* 1057 */         System.exit(0);
/*      */       }
/*      */     } 
/*      */     
/* 1061 */     System.gc();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void runGameLoop() throws IOException {
/* 1069 */     long i = System.nanoTime();
/* 1070 */     this.mcProfiler.startSection("root");
/*      */     
/* 1072 */     if (Display.isCreated() && Display.isCloseRequested())
/*      */     {
/* 1074 */       shutdown();
/*      */     }
/*      */     
/* 1077 */     if (this.isGamePaused && this.theWorld != null) {
/*      */       
/* 1079 */       float f = this.timer.renderPartialTicks;
/* 1080 */       this.timer.updateTimer();
/* 1081 */       this.timer.renderPartialTicks = f;
/*      */     }
/*      */     else {
/*      */       
/* 1085 */       this.timer.updateTimer();
/*      */     } 
/*      */     
/* 1088 */     this.mcProfiler.startSection("scheduledExecutables");
/*      */     
/* 1090 */     synchronized (this.scheduledTasks) {
/*      */       
/* 1092 */       while (!this.scheduledTasks.isEmpty())
/*      */       {
/* 1094 */         Util.func_181617_a(this.scheduledTasks.poll(), logger);
/*      */       }
/*      */     } 
/*      */     
/* 1098 */     this.mcProfiler.endSection();
/* 1099 */     long l = System.nanoTime();
/* 1100 */     this.mcProfiler.startSection("tick");
/*      */     
/* 1102 */     for (int j = 0; j < this.timer.elapsedTicks; j++)
/*      */     {
/* 1104 */       runTick();
/*      */     }
/*      */     
/* 1107 */     this.mcProfiler.endStartSection("preRenderErrors");
/* 1108 */     long i1 = System.nanoTime() - l;
/* 1109 */     checkGLError("Pre render");
/* 1110 */     this.mcProfiler.endStartSection("sound");
/* 1111 */     this.mcSoundHandler.setListener((EntityPlayer)this.thePlayer, this.timer.renderPartialTicks);
/* 1112 */     this.mcProfiler.endSection();
/* 1113 */     this.mcProfiler.startSection("render");
/* 1114 */     GlStateManager.pushMatrix();
/* 1115 */     GlStateManager.clear(16640);
/* 1116 */     this.framebufferMc.bindFramebuffer(true);
/* 1117 */     this.mcProfiler.startSection("display");
/* 1118 */     GlStateManager.enableTexture2D();
/*      */     
/* 1120 */     if (this.thePlayer != null && this.thePlayer.isEntityInsideOpaqueBlock())
/*      */     {
/* 1122 */       this.gameSettings.thirdPersonView = 0;
/*      */     }
/*      */     
/* 1125 */     this.mcProfiler.endSection();
/*      */     
/* 1127 */     if (!this.skipRenderWorld) {
/*      */       
/* 1129 */       this.mcProfiler.endStartSection("gameRenderer");
/* 1130 */       this.entityRenderer.func_181560_a(this.timer.renderPartialTicks, i);
/* 1131 */       this.mcProfiler.endSection();
/*      */     } 
/*      */     
/* 1134 */     this.mcProfiler.endSection();
/*      */     
/* 1136 */     if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart && !this.gameSettings.hideGUI) {
/*      */       
/* 1138 */       if (!this.mcProfiler.profilingEnabled)
/*      */       {
/* 1140 */         this.mcProfiler.clearProfiling();
/*      */       }
/*      */       
/* 1143 */       this.mcProfiler.profilingEnabled = true;
/* 1144 */       displayDebugInfo(i1);
/*      */     }
/*      */     else {
/*      */       
/* 1148 */       this.mcProfiler.profilingEnabled = false;
/* 1149 */       this.prevFrameTime = System.nanoTime();
/*      */     } 
/*      */     
/* 1152 */     this.guiAchievement.updateAchievementWindow();
/* 1153 */     this.framebufferMc.unbindFramebuffer();
/* 1154 */     GlStateManager.popMatrix();
/* 1155 */     GlStateManager.pushMatrix();
/* 1156 */     this.framebufferMc.framebufferRender(this.displayWidth, this.displayHeight);
/* 1157 */     GlStateManager.popMatrix();
/* 1158 */     this.mcProfiler.startSection("root");
/* 1159 */     updateDisplay();
/* 1160 */     Thread.yield();
/* 1161 */     this.mcProfiler.startSection("update");
/* 1162 */     this.mcProfiler.endStartSection("submit");
/* 1163 */     this.mcProfiler.endSection();
/* 1164 */     checkGLError("Post render");
/* 1165 */     this.fpsCounter++;
/* 1166 */     this.isGamePaused = (isSingleplayer() && this.currentScreen != null && this.currentScreen.doesGuiPauseGame() && !this.theIntegratedServer.getPublic());
/* 1167 */     long k = System.nanoTime();
/* 1168 */     this.field_181542_y.func_181747_a(k - this.field_181543_z);
/* 1169 */     this.field_181543_z = k;
/*      */     
/* 1171 */     while (getSystemTime() >= this.debugUpdateTime + 1000L) {
/*      */       
/* 1173 */       debugFPS = this.fpsCounter;
/* 1174 */       this.debug = String.format("%d fps (%d chunk update%s) T: %s%s%s%s%s", new Object[] { Integer.valueOf(debugFPS), Integer.valueOf(RenderChunk.renderChunksUpdated), (RenderChunk.renderChunksUpdated != 1) ? "s" : "", (this.gameSettings.limitFramerate == GameSettings.Options.FRAMERATE_LIMIT.getValueMax()) ? "inf" : Integer.valueOf(this.gameSettings.limitFramerate), this.gameSettings.enableVsync ? " vsync" : "", this.gameSettings.fancyGraphics ? "" : " fast", (this.gameSettings.clouds == 0) ? "" : ((this.gameSettings.clouds == 1) ? " fast-clouds" : " fancy-clouds"), OpenGlHelper.useVbo() ? " vbo" : "" });
/* 1175 */       RenderChunk.renderChunksUpdated = 0;
/* 1176 */       this.debugUpdateTime += 1000L;
/* 1177 */       this.fpsCounter = 0;
/* 1178 */       this.usageSnooper.addMemoryStatsToSnooper();
/*      */       
/* 1180 */       if (!this.usageSnooper.isSnooperRunning())
/*      */       {
/* 1182 */         this.usageSnooper.startSnooper();
/*      */       }
/*      */     } 
/*      */     
/* 1186 */     if (isFramerateLimitBelowMax()) {
/*      */       
/* 1188 */       this.mcProfiler.startSection("fpslimit_wait");
/*      */       
/* 1190 */       Display.sync(getLimitFramerate());
/* 1191 */       this.mcProfiler.endSection();
/*      */     } 
/*      */     
/* 1194 */     this.mcProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateDisplay() {
/* 1199 */     this.mcProfiler.startSection("display_update");
/* 1200 */     Display.update();
/* 1201 */     this.mcProfiler.endSection();
/* 1202 */     checkWindowResize();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkWindowResize() {
/* 1207 */     if (!this.fullscreen && Display.wasResized()) {
/*      */       
/* 1209 */       int i = this.displayWidth;
/* 1210 */       int j = this.displayHeight;
/* 1211 */       this.displayWidth = Display.getWidth();
/* 1212 */       this.displayHeight = Display.getHeight();
/*      */       
/* 1214 */       if (this.displayWidth != i || this.displayHeight != j) {
/*      */         
/* 1216 */         if (this.displayWidth <= 0)
/*      */         {
/* 1218 */           this.displayWidth = 1;
/*      */         }
/*      */         
/* 1221 */         if (this.displayHeight <= 0)
/*      */         {
/* 1223 */           this.displayHeight = 1;
/*      */         }
/*      */         
/* 1226 */         resize(this.displayWidth, this.displayHeight);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLimitFramerate() {
/* 1233 */     return (this.currentScreen != null) ? 30 : this.gameSettings.limitFramerate;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFramerateLimitBelowMax() {
/* 1238 */     return (getLimitFramerate() < GameSettings.Options.FRAMERATE_LIMIT.getValueMax());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void freeMemory() {
/*      */     try {
/* 1245 */       memoryReserve = new byte[0];
/* 1246 */       this.renderGlobal.deleteAllDisplayLists();
/*      */     }
/* 1248 */     catch (Throwable throwable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1255 */       System.gc();
/* 1256 */       loadWorld(null);
/*      */     }
/* 1258 */     catch (Throwable throwable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1263 */     System.gc();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateDebugProfilerName(int keyCount) {
/* 1271 */     List<Profiler.Result> list = this.mcProfiler.getProfilingData(this.debugProfilerName);
/*      */     
/* 1273 */     if (list != null && !list.isEmpty()) {
/*      */       
/* 1275 */       Profiler.Result profiler$result = list.remove(0);
/*      */       
/* 1277 */       if (keyCount == 0) {
/*      */         
/* 1279 */         if (profiler$result.field_76331_c.length() > 0)
/*      */         {
/* 1281 */           int i = this.debugProfilerName.lastIndexOf(".");
/*      */           
/* 1283 */           if (i >= 0)
/*      */           {
/* 1285 */             this.debugProfilerName = this.debugProfilerName.substring(0, i);
/*      */           }
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1291 */         keyCount--;
/*      */         
/* 1293 */         if (keyCount < list.size() && !((Profiler.Result)list.get(keyCount)).field_76331_c.equals("unspecified")) {
/*      */           
/* 1295 */           if (this.debugProfilerName.length() > 0)
/*      */           {
/* 1297 */             this.debugProfilerName += ".";
/*      */           }
/*      */           
/* 1300 */           this.debugProfilerName += ((Profiler.Result)list.get(keyCount)).field_76331_c;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void displayDebugInfo(long elapsedTicksTime) {
/* 1311 */     if (this.mcProfiler.profilingEnabled) {
/*      */       
/* 1313 */       List<Profiler.Result> list = this.mcProfiler.getProfilingData(this.debugProfilerName);
/* 1314 */       Profiler.Result profiler$result = list.remove(0);
/* 1315 */       GlStateManager.clear(256);
/* 1316 */       GlStateManager.matrixMode(5889);
/* 1317 */       GlStateManager.enableColorMaterial();
/* 1318 */       GlStateManager.loadIdentity();
/* 1319 */       GlStateManager.ortho(0.0D, this.displayWidth, this.displayHeight, 0.0D, 1000.0D, 3000.0D);
/* 1320 */       GlStateManager.matrixMode(5888);
/* 1321 */       GlStateManager.loadIdentity();
/* 1322 */       GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/* 1323 */       GL11.glLineWidth(1.0F);
/* 1324 */       GlStateManager.disableTexture2D();
/* 1325 */       Tessellator tessellator = Tessellator.getInstance();
/* 1326 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1327 */       int i = 160;
/* 1328 */       int j = this.displayWidth - i - 10;
/* 1329 */       int k = this.displayHeight - i * 2;
/* 1330 */       GlStateManager.enableBlend();
/* 1331 */       worldrenderer.begin(7, DefaultVertexFormats.field_181706_f);
/* 1332 */       worldrenderer.pos((j - i * 1.1F), (k - i * 0.6F - 16.0F), 0.0D).func_181669_b(200, 0, 0, 0).endVertex();
/* 1333 */       worldrenderer.pos((j - i * 1.1F), (k + i * 2), 0.0D).func_181669_b(200, 0, 0, 0).endVertex();
/* 1334 */       worldrenderer.pos((j + i * 1.1F), (k + i * 2), 0.0D).func_181669_b(200, 0, 0, 0).endVertex();
/* 1335 */       worldrenderer.pos((j + i * 1.1F), (k - i * 0.6F - 16.0F), 0.0D).func_181669_b(200, 0, 0, 0).endVertex();
/* 1336 */       tessellator.draw();
/* 1337 */       GlStateManager.disableBlend();
/* 1338 */       double d0 = 0.0D;
/*      */       
/* 1340 */       for (int l = 0; l < list.size(); l++) {
/*      */         
/* 1342 */         Profiler.Result profiler$result1 = list.get(l);
/* 1343 */         int i1 = MathHelper.floor_double(profiler$result1.field_76332_a / 4.0D) + 1;
/* 1344 */         worldrenderer.begin(6, DefaultVertexFormats.field_181706_f);
/* 1345 */         int j1 = profiler$result1.func_76329_a();
/* 1346 */         int k1 = j1 >> 16 & 0xFF;
/* 1347 */         int l1 = j1 >> 8 & 0xFF;
/* 1348 */         int i2 = j1 & 0xFF;
/* 1349 */         worldrenderer.pos(j, k, 0.0D).func_181669_b(k1, l1, i2, 255).endVertex();
/*      */         
/* 1351 */         for (int j2 = i1; j2 >= 0; j2--) {
/*      */           
/* 1353 */           float f = (float)((d0 + profiler$result1.field_76332_a * j2 / i1) * Math.PI * 2.0D / 100.0D);
/* 1354 */           float f1 = MathHelper.sin(f) * i;
/* 1355 */           float f2 = MathHelper.cos(f) * i * 0.5F;
/* 1356 */           worldrenderer.pos((j + f1), (k - f2), 0.0D).func_181669_b(k1, l1, i2, 255).endVertex();
/*      */         } 
/*      */         
/* 1359 */         tessellator.draw();
/* 1360 */         worldrenderer.begin(5, DefaultVertexFormats.field_181706_f);
/*      */         
/* 1362 */         for (int i3 = i1; i3 >= 0; i3--) {
/*      */           
/* 1364 */           float f3 = (float)((d0 + profiler$result1.field_76332_a * i3 / i1) * Math.PI * 2.0D / 100.0D);
/* 1365 */           float f4 = MathHelper.sin(f3) * i;
/* 1366 */           float f5 = MathHelper.cos(f3) * i * 0.5F;
/* 1367 */           worldrenderer.pos((j + f4), (k - f5), 0.0D).func_181669_b(k1 >> 1, l1 >> 1, i2 >> 1, 255).endVertex();
/* 1368 */           worldrenderer.pos((j + f4), (k - f5 + 10.0F), 0.0D).func_181669_b(k1 >> 1, l1 >> 1, i2 >> 1, 255).endVertex();
/*      */         } 
/*      */         
/* 1371 */         tessellator.draw();
/* 1372 */         d0 += profiler$result1.field_76332_a;
/*      */       } 
/*      */       
/* 1375 */       DecimalFormat decimalformat = new DecimalFormat("##0.00");
/* 1376 */       GlStateManager.enableTexture2D();
/* 1377 */       String s = "";
/*      */       
/* 1379 */       if (!profiler$result.field_76331_c.equals("unspecified"))
/*      */       {
/* 1381 */         s = s + "[0] ";
/*      */       }
/*      */       
/* 1384 */       if (profiler$result.field_76331_c.length() == 0) {
/*      */         
/* 1386 */         s = s + "ROOT ";
/*      */       }
/*      */       else {
/*      */         
/* 1390 */         s = s + profiler$result.field_76331_c + " ";
/*      */       } 
/*      */       
/* 1393 */       int l2 = 16777215;
/* 1394 */       this.MCfontRenderer.drawStringWithShadow(s, (j - i), (k - i / 2 - 16), l2);
/* 1395 */       this.MCfontRenderer.drawStringWithShadow(s = decimalformat.format(profiler$result.field_76330_b) + "%", (j + i - this.MCfontRenderer.getStringWidth(s)), (k - i / 2 - 16), l2);
/*      */       
/* 1397 */       for (int k2 = 0; k2 < list.size(); k2++) {
/*      */         
/* 1399 */         Profiler.Result profiler$result2 = list.get(k2);
/* 1400 */         String s1 = "";
/*      */         
/* 1402 */         if (profiler$result2.field_76331_c.equals("unspecified")) {
/*      */           
/* 1404 */           s1 = s1 + "[?] ";
/*      */         }
/*      */         else {
/*      */           
/* 1408 */           s1 = s1 + "[" + (k2 + 1) + "] ";
/*      */         } 
/*      */         
/* 1411 */         s1 = s1 + profiler$result2.field_76331_c;
/* 1412 */         this.MCfontRenderer.drawStringWithShadow(s1, (j - i), (k + i / 2 + k2 * 8 + 20), profiler$result2.func_76329_a());
/* 1413 */         this.MCfontRenderer.drawStringWithShadow(s1 = decimalformat.format(profiler$result2.field_76332_a) + "%", (j + i - 50 - this.MCfontRenderer.getStringWidth(s1)), (k + i / 2 + k2 * 8 + 20), profiler$result2.func_76329_a());
/* 1414 */         this.MCfontRenderer.drawStringWithShadow(s1 = decimalformat.format(profiler$result2.field_76330_b) + "%", (j + i - this.MCfontRenderer.getStringWidth(s1)), (k + i / 2 + k2 * 8 + 20), profiler$result2.func_76329_a());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void shutdown() {
/* 1424 */     this.running = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIngameFocus() {
/* 1433 */     if (Display.isActive())
/*      */     {
/* 1435 */       if (!this.inGameHasFocus) {
/*      */         
/* 1437 */         this.inGameHasFocus = true;
/* 1438 */         this.mouseHelper.grabMouseCursor();
/* 1439 */         displayGuiScreen(null);
/* 1440 */         this.leftClickCounter = 10000;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIngameNotInFocus() {
/* 1450 */     if (this.inGameHasFocus) {
/*      */       
/* 1452 */       KeyBinding.unPressAllKeys();
/* 1453 */       this.inGameHasFocus = false;
/* 1454 */       this.mouseHelper.ungrabMouseCursor();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayInGameMenu() {
/* 1463 */     if (this.currentScreen == null) {
/*      */       
/* 1465 */       displayGuiScreen((GuiScreen)new GuiIngameMenu());
/*      */       
/* 1467 */       if (isSingleplayer() && !this.theIntegratedServer.getPublic())
/*      */       {
/* 1469 */         this.mcSoundHandler.pauseSounds();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void sendClickBlockToController(boolean leftClick) {
/* 1478 */     if (!leftClick || (((Tweaks)Slack.getInstance().getModuleManager().getInstance(Tweaks.class)).isToggle() && ((Boolean)((Tweaks)Slack.getInstance().getModuleManager().getInstance(Tweaks.class)).noclickdelay.getValue()).booleanValue() && this.objectMouseOver != null))
/*      */     {
/* 1480 */       this.leftClickCounter = 0;
/*      */     }
/*      */     
/* 1483 */     if (this.leftClickCounter <= 0 && (!this.thePlayer.isUsingItem() || ((MultiAction)Slack.getInstance().getModuleManager().getInstance(MultiAction.class)).isToggle()))
/*      */     {
/* 1485 */       if (leftClick && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/*      */         
/* 1487 */         BlockPos blockpos = this.objectMouseOver.getBlockPos();
/*      */         
/* 1489 */         if (this.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air && this.playerController.onPlayerDamageBlock(blockpos, this.objectMouseOver.sideHit))
/*      */         {
/* 1491 */           this.effectRenderer.addBlockHitEffects(blockpos, this.objectMouseOver.sideHit);
/* 1492 */           this.thePlayer.swingItem();
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1497 */         this.playerController.resetBlockRemoving();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void clickMouse() {
/* 1504 */     if (this.leftClickCounter <= 0) {
/*      */       
/* 1506 */       AttackOrder.sendConditionalSwing(this.objectMouseOver);
/*      */       
/* 1508 */       if (this.objectMouseOver == null) {
/*      */         
/* 1510 */         logger.error("Null returned as 'hitResult', this shouldn't happen!");
/*      */         
/* 1512 */         if (this.playerController.isNotCreative())
/*      */         {
/* 1514 */           this.leftClickCounter = 10;
/*      */         }
/*      */       } else {
/*      */         BlockPos blockpos;
/*      */         
/* 1519 */         switch (this.objectMouseOver.typeOfHit) {
/*      */           
/*      */           case FURNACE:
/* 1522 */             AttackOrder.sendFixedAttack((EntityPlayer)this.thePlayer, this.objectMouseOver.entityHit);
/*      */             return;
/*      */           
/*      */           case CHEST:
/* 1526 */             blockpos = this.objectMouseOver.getBlockPos();
/*      */             
/* 1528 */             if (this.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air) {
/*      */               
/* 1530 */               this.playerController.clickBlock(blockpos, this.objectMouseOver.sideHit);
/*      */               return;
/*      */             } 
/*      */             break;
/*      */         } 
/*      */         
/* 1536 */         if (this.playerController.isNotCreative())
/*      */         {
/* 1538 */           this.leftClickCounter = 10;
/*      */         }
/*      */       } 
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
/*      */   private void rightClickMouse() {
/* 1552 */     if (!this.playerController.func_181040_m()) {
/*      */       
/* 1554 */       this.rightClickDelayTimer = 4;
/* 1555 */       if (((FastPlace)Slack.getInstance().getModuleManager().getInstance(FastPlace.class)).isToggle()) {
/* 1556 */         this.rightClickDelayTimer = ((Integer)((FastPlace)Slack.getInstance().getModuleManager().getInstance(FastPlace.class)).placeDelay.getValue()).intValue();
/*      */       }
/* 1558 */       boolean flag = true;
/* 1559 */       ItemStack itemstack = this.thePlayer.inventory.getCurrentItem();
/*      */       
/* 1561 */       if (this.objectMouseOver == null) {
/*      */         
/* 1563 */         logger.warn("Null returned as 'hitResult', this shouldn't happen!");
/*      */       } else {
/*      */         BlockPos blockpos;
/*      */         
/* 1567 */         switch (this.objectMouseOver.typeOfHit) {
/*      */           
/*      */           case FURNACE:
/* 1570 */             if (this.playerController.func_178894_a((EntityPlayer)this.thePlayer, this.objectMouseOver.entityHit, this.objectMouseOver)) {
/*      */               
/* 1572 */               flag = false; break;
/*      */             } 
/* 1574 */             if (this.playerController.interactWithEntitySendPacket((EntityPlayer)this.thePlayer, this.objectMouseOver.entityHit))
/*      */             {
/* 1576 */               flag = false;
/*      */             }
/*      */             break;
/*      */ 
/*      */           
/*      */           case CHEST:
/* 1582 */             blockpos = this.objectMouseOver.getBlockPos();
/*      */             
/* 1584 */             if (this.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air) {
/*      */               
/* 1586 */               int i = (itemstack != null) ? itemstack.stackSize : 0;
/*      */               
/* 1588 */               if (this.playerController.onPlayerRightClick(this.thePlayer, this.theWorld, itemstack, blockpos, this.objectMouseOver.sideHit, this.objectMouseOver.hitVec)) {
/*      */                 
/* 1590 */                 flag = false;
/* 1591 */                 this.thePlayer.swingItem();
/*      */               } 
/*      */               
/* 1594 */               if (itemstack == null) {
/*      */                 return;
/*      */               }
/*      */ 
/*      */               
/* 1599 */               if (itemstack.stackSize == 0) {
/*      */                 
/* 1601 */                 this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.currentItem] = null; break;
/*      */               } 
/* 1603 */               if (itemstack.stackSize != i || this.playerController.isInCreativeMode())
/*      */               {
/* 1605 */                 this.entityRenderer.itemRenderer.resetEquippedProgress();
/*      */               }
/*      */             } 
/*      */             break;
/*      */         } 
/*      */       } 
/* 1611 */       if (flag) {
/*      */         
/* 1613 */         ItemStack itemstack1 = this.thePlayer.inventory.getCurrentItem();
/*      */         
/* 1615 */         if (itemstack1 != null && this.playerController.sendUseItem((EntityPlayer)this.thePlayer, (World)this.theWorld, itemstack1))
/*      */         {
/* 1617 */           this.entityRenderer.itemRenderer.resetEquippedProgress2();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toggleFullscreen() {
/*      */     try {
/* 1630 */       this.fullscreen = !this.fullscreen;
/* 1631 */       this.gameSettings.fullScreen = this.fullscreen;
/*      */       
/* 1633 */       if (this.fullscreen) {
/*      */         
/* 1635 */         updateDisplayMode();
/* 1636 */         this.displayWidth = Display.getDisplayMode().getWidth();
/* 1637 */         this.displayHeight = Display.getDisplayMode().getHeight();
/*      */         
/* 1639 */         if (this.displayWidth <= 0)
/*      */         {
/* 1641 */           this.displayWidth = 1;
/*      */         }
/*      */         
/* 1644 */         if (this.displayHeight <= 0)
/*      */         {
/* 1646 */           this.displayHeight = 1;
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 1651 */         Display.setDisplayMode(new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
/* 1652 */         this.displayWidth = this.tempDisplayWidth;
/* 1653 */         this.displayHeight = this.tempDisplayHeight;
/*      */         
/* 1655 */         if (this.displayWidth <= 0)
/*      */         {
/* 1657 */           this.displayWidth = 1;
/*      */         }
/*      */         
/* 1660 */         if (this.displayHeight <= 0)
/*      */         {
/* 1662 */           this.displayHeight = 1;
/*      */         }
/*      */       } 
/*      */       
/* 1666 */       if (this.currentScreen != null) {
/*      */         
/* 1668 */         resize(this.displayWidth, this.displayHeight);
/*      */       }
/*      */       else {
/*      */         
/* 1672 */         updateFramebufferSize();
/*      */       } 
/*      */       
/* 1675 */       Display.setFullscreen(this.fullscreen);
/* 1676 */       Display.setVSyncEnabled(this.gameSettings.enableVsync);
/* 1677 */       updateDisplay();
/*      */     }
/* 1679 */     catch (Exception exception) {
/*      */       
/* 1681 */       logger.error("Couldn't toggle fullscreen", exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resize(int width, int height) {
/* 1690 */     this.displayWidth = Math.max(1, width);
/* 1691 */     this.displayHeight = Math.max(1, height);
/*      */     
/* 1693 */     if (this.currentScreen != null) {
/*      */       
/* 1695 */       ScaledResolution scaledresolution = new ScaledResolution(this);
/* 1696 */       this.currentScreen.onResize(this, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());
/*      */     } 
/*      */     
/* 1699 */     this.loadingScreen = new LoadingScreenRenderer(this);
/* 1700 */     updateFramebufferSize();
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateFramebufferSize() {
/* 1705 */     this.framebufferMc.createBindFramebuffer(this.displayWidth, this.displayHeight);
/*      */     
/* 1707 */     if (this.entityRenderer != null)
/*      */     {
/* 1709 */       this.entityRenderer.updateShaderGroupSize(this.displayWidth, this.displayHeight);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public MusicTicker func_181535_r() {
/* 1715 */     return this.mcMusicTicker;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void runTick() throws IOException {
/* 1723 */     TickEvent tickEvent = new TickEvent();
/* 1724 */     if (this.thePlayer != null && this.theWorld != null) {
/* 1725 */       tickEvent.call();
/*      */     }
/* 1727 */     if (this.rightClickDelayTimer > 0)
/*      */     {
/* 1729 */       this.rightClickDelayTimer--;
/*      */     }
/*      */     
/* 1732 */     this.mcProfiler.startSection("gui");
/*      */     
/* 1734 */     if (!this.isGamePaused)
/*      */     {
/* 1736 */       this.ingameGUI.updateTick();
/*      */     }
/*      */     
/* 1739 */     this.mcProfiler.endSection();
/* 1740 */     this.entityRenderer.getMouseOver(1.0F);
/* 1741 */     this.mcProfiler.startSection("gameMode");
/*      */     
/* 1743 */     if (!this.isGamePaused && this.theWorld != null) {
/*      */       
/* 1745 */       this.playerController.updateController();
/* 1746 */       if (RotationUtil.isEnabled && 
/* 1747 */         RotationUtil.strafeFix && 
/* 1748 */         !RotationUtil.strictStrafeFix) {
/* 1749 */         if (MovementUtil.isBindsMoving()) {
/* 1750 */           int strafeYaw = Math.round((RotationUtil.clientRotation[0] - MovementUtil.getBindsDirection(this.thePlayer.rotationYaw)) / 45.0F);
/* 1751 */           if (strafeYaw > 4) {
/* 1752 */             strafeYaw -= 8;
/*      */           }
/* 1754 */           if (strafeYaw < -4) {
/* 1755 */             strafeYaw += 8;
/*      */           }
/* 1757 */           this.gameSettings.keyBindForward.pressed = (Math.abs(strafeYaw) <= 1);
/* 1758 */           this.gameSettings.keyBindLeft.pressed = (strafeYaw >= 1 && strafeYaw <= 3);
/* 1759 */           this.gameSettings.keyBindBack.pressed = (Math.abs(strafeYaw) >= 3);
/* 1760 */           this.gameSettings.keyBindRight.pressed = (strafeYaw >= -3 && strafeYaw <= -1);
/*      */         } else {
/* 1762 */           this.gameSettings.keyBindForward.pressed = false;
/* 1763 */           this.gameSettings.keyBindRight.pressed = false;
/* 1764 */           this.gameSettings.keyBindBack.pressed = false;
/* 1765 */           this.gameSettings.keyBindLeft.pressed = false;
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1772 */     this.mcProfiler.endStartSection("textures");
/*      */     
/* 1774 */     if (!this.isGamePaused)
/*      */     {
/* 1776 */       this.renderEngine.tick();
/*      */     }
/*      */     
/* 1779 */     if (this.currentScreen == null && this.thePlayer != null) {
/*      */       
/* 1781 */       if (this.thePlayer.getHealth() <= 0.0F)
/*      */       {
/* 1783 */         displayGuiScreen(null);
/*      */       }
/* 1785 */       else if (this.thePlayer.isPlayerSleeping() && this.theWorld != null)
/*      */       {
/* 1787 */         displayGuiScreen((GuiScreen)new GuiSleepMP());
/*      */       }
/*      */     
/* 1790 */     } else if (this.currentScreen != null && this.currentScreen instanceof GuiSleepMP && !this.thePlayer.isPlayerSleeping()) {
/*      */       
/* 1792 */       displayGuiScreen(null);
/*      */     } 
/*      */     
/* 1795 */     if (this.currentScreen != null)
/*      */     {
/* 1797 */       this.leftClickCounter = 10000;
/*      */     }
/*      */     
/* 1800 */     if (this.currentScreen != null) {
/*      */ 
/*      */       
/*      */       try {
/* 1804 */         this.currentScreen.handleInput();
/*      */       }
/* 1806 */       catch (Throwable throwable1) {
/*      */         
/* 1808 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Updating screen events");
/* 1809 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Affected screen");
/* 1810 */         crashreportcategory.addCrashSectionCallable("Screen name", new Callable<String>()
/*      */             {
/*      */               public String call() throws Exception
/*      */               {
/* 1814 */                 return Minecraft.this.currentScreen.getClass().getCanonicalName();
/*      */               }
/*      */             });
/* 1817 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/* 1820 */       if (this.currentScreen != null) {
/*      */         
/*      */         try {
/*      */           
/* 1824 */           this.currentScreen.updateScreen();
/*      */         }
/* 1826 */         catch (Throwable throwable) {
/*      */           
/* 1828 */           CrashReport crashreport1 = CrashReport.makeCrashReport(throwable, "Ticking screen");
/* 1829 */           CrashReportCategory crashreportcategory1 = crashreport1.makeCategory("Affected screen");
/* 1830 */           crashreportcategory1.addCrashSectionCallable("Screen name", new Callable<String>()
/*      */               {
/*      */                 public String call() throws Exception
/*      */                 {
/* 1834 */                   return Minecraft.this.currentScreen.getClass().getCanonicalName();
/*      */                 }
/*      */               });
/* 1837 */           throw new ReportedException(crashreport1);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1842 */     if (this.currentScreen == null || this.currentScreen.allowUserInput) {
/*      */       
/* 1844 */       this.mcProfiler.endStartSection("mouse");
/*      */       
/* 1846 */       while (Mouse.next()) {
/*      */         
/* 1848 */         int i = Mouse.getEventButton();
/* 1849 */         KeyBinding.setKeyBindState(i - 100, Mouse.getEventButtonState());
/*      */         
/* 1851 */         if (Mouse.getEventButtonState())
/*      */         {
/* 1853 */           if (this.thePlayer.isSpectator() && i == 2) {
/*      */             
/* 1855 */             this.ingameGUI.getSpectatorGui().func_175261_b();
/*      */           }
/*      */           else {
/*      */             
/* 1859 */             KeyBinding.onTick(i - 100);
/*      */           } 
/*      */         }
/*      */         
/* 1863 */         long i1 = getSystemTime() - this.systemTime;
/*      */         
/* 1865 */         if (i1 <= 200L) {
/*      */           
/* 1867 */           int j = Mouse.getEventDWheel();
/*      */           
/* 1869 */           if (j != 0)
/*      */           {
/* 1871 */             if (this.thePlayer.isSpectator()) {
/*      */               
/* 1873 */               j = (j < 0) ? -1 : 1;
/*      */               
/* 1875 */               if (this.ingameGUI.getSpectatorGui().func_175262_a())
/*      */               {
/* 1877 */                 this.ingameGUI.getSpectatorGui().func_175259_b(-j);
/*      */               }
/*      */               else
/*      */               {
/* 1881 */                 float f = MathHelper.clamp_float(this.thePlayer.capabilities.getFlySpeed() + j * 0.005F, 0.0F, 0.2F);
/* 1882 */                 this.thePlayer.capabilities.setFlySpeed(f);
/*      */               }
/*      */             
/*      */             } else {
/*      */               
/* 1887 */               this.thePlayer.inventory.changeCurrentItem(j);
/*      */             } 
/*      */           }
/*      */           
/* 1891 */           if (this.currentScreen == null) {
/*      */             
/* 1893 */             if (!this.inGameHasFocus && Mouse.getEventButtonState())
/*      */             {
/* 1895 */               setIngameFocus(); } 
/*      */             continue;
/*      */           } 
/* 1898 */           if (this.currentScreen != null)
/*      */           {
/* 1900 */             this.currentScreen.handleMouseInput();
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1905 */       if (this.leftClickCounter > 0)
/*      */       {
/* 1907 */         this.leftClickCounter--;
/*      */       }
/*      */       
/* 1910 */       this.mcProfiler.endStartSection("keyboard");
/*      */       
/* 1912 */       while (Keyboard.next()) {
/*      */         
/* 1914 */         int k = (Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + 256) : Keyboard.getEventKey();
/* 1915 */         KeyBinding.setKeyBindState(k, Keyboard.getEventKeyState());
/*      */         
/* 1917 */         if (Keyboard.getEventKeyState())
/*      */         {
/* 1919 */           KeyBinding.onTick(k);
/*      */         }
/*      */         
/* 1922 */         if (this.debugCrashKeyPressTime > 0L) {
/*      */           
/* 1924 */           if (getSystemTime() - this.debugCrashKeyPressTime >= 6000L)
/*      */           {
/* 1926 */             throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
/*      */           }
/*      */           
/* 1929 */           if (!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61))
/*      */           {
/* 1931 */             this.debugCrashKeyPressTime = -1L;
/*      */           }
/*      */         }
/* 1934 */         else if (Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61)) {
/*      */           
/* 1936 */           this.debugCrashKeyPressTime = getSystemTime();
/*      */         } 
/*      */         
/* 1939 */         dispatchKeypresses();
/*      */         
/* 1941 */         if (Keyboard.getEventKeyState()) {
/*      */           
/* 1943 */           if ((new KeyEvent(k)).call().isCanceled()) {
/*      */             break;
/*      */           }
/* 1946 */           if (k == 62 && this.entityRenderer != null)
/*      */           {
/* 1948 */             this.entityRenderer.switchUseShader();
/*      */           }
/*      */           
/* 1951 */           if (this.currentScreen != null) {
/*      */             
/* 1953 */             this.currentScreen.handleKeyboardInput();
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 1958 */             if (k == 1)
/*      */             {
/* 1960 */               displayInGameMenu();
/*      */             }
/*      */             
/* 1963 */             if (k == 32 && Keyboard.isKeyDown(61) && this.ingameGUI != null)
/*      */             {
/* 1965 */               this.ingameGUI.getChatGUI().clearChatMessages();
/*      */             }
/*      */             
/* 1968 */             if (k == 31 && Keyboard.isKeyDown(61))
/*      */             {
/* 1970 */               refreshResources();
/*      */             }
/*      */             
/* 1973 */             if (k != 17 || Keyboard.isKeyDown(61));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1978 */             if (k != 18 || Keyboard.isKeyDown(61));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1983 */             if (k != 47 || Keyboard.isKeyDown(61));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1988 */             if (k != 38 || Keyboard.isKeyDown(61));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1993 */             if (k != 22 || Keyboard.isKeyDown(61));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1998 */             if (k == 20 && Keyboard.isKeyDown(61))
/*      */             {
/* 2000 */               refreshResources();
/*      */             }
/*      */             
/* 2003 */             if (k == 33 && Keyboard.isKeyDown(61))
/*      */             {
/* 2005 */               this.gameSettings.setOptionValue(GameSettings.Options.RENDER_DISTANCE, GuiScreen.isShiftKeyDown() ? -1 : 1);
/*      */             }
/*      */             
/* 2008 */             if (k == 30 && Keyboard.isKeyDown(61))
/*      */             {
/* 2010 */               this.renderGlobal.loadRenderers();
/*      */             }
/*      */             
/* 2013 */             if (k == 35 && Keyboard.isKeyDown(61)) {
/*      */               
/* 2015 */               this.gameSettings.advancedItemTooltips = !this.gameSettings.advancedItemTooltips;
/* 2016 */               this.gameSettings.saveOptions();
/*      */             } 
/*      */             
/* 2019 */             if (k == 48 && Keyboard.isKeyDown(61))
/*      */             {
/* 2021 */               this.renderManager.setDebugBoundingBox(!this.renderManager.isDebugBoundingBox());
/*      */             }
/*      */             
/* 2024 */             if (k == 25 && Keyboard.isKeyDown(61)) {
/*      */               
/* 2026 */               this.gameSettings.pauseOnLostFocus = !this.gameSettings.pauseOnLostFocus;
/* 2027 */               this.gameSettings.saveOptions();
/*      */             } 
/*      */             
/* 2030 */             if (k == 59)
/*      */             {
/* 2032 */               this.gameSettings.hideGUI = !this.gameSettings.hideGUI;
/*      */             }
/*      */             
/* 2035 */             if (k == 61) {
/*      */               
/* 2037 */               this.gameSettings.showDebugInfo = !this.gameSettings.showDebugInfo;
/* 2038 */               this.gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
/* 2039 */               this.gameSettings.field_181657_aC = GuiScreen.isAltKeyDown();
/*      */             } 
/*      */             
/* 2042 */             if (this.gameSettings.keyBindTogglePerspective.isPressed()) {
/*      */               
/* 2044 */               this.gameSettings.thirdPersonView++;
/*      */               
/* 2046 */               if (this.gameSettings.thirdPersonView > 2)
/*      */               {
/* 2048 */                 this.gameSettings.thirdPersonView = 0;
/*      */               }
/*      */               
/* 2051 */               if (this.gameSettings.thirdPersonView == 0) {
/*      */                 
/* 2053 */                 this.entityRenderer.loadEntityShader(getRenderViewEntity());
/*      */               }
/* 2055 */               else if (this.gameSettings.thirdPersonView == 1) {
/*      */                 
/* 2057 */                 this.entityRenderer.loadEntityShader(null);
/*      */               } 
/*      */               
/* 2060 */               this.renderGlobal.setDisplayListEntitiesDirty();
/*      */             } 
/*      */             
/* 2063 */             if (this.gameSettings.keyBindSmoothCamera.isPressed())
/*      */             {
/* 2065 */               this.gameSettings.smoothCamera = !this.gameSettings.smoothCamera;
/*      */             }
/*      */           } 
/*      */           
/* 2069 */           if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart) {
/*      */             
/* 2071 */             if (k == 11)
/*      */             {
/* 2073 */               updateDebugProfilerName(0);
/*      */             }
/*      */             
/* 2076 */             for (int j1 = 0; j1 < 9; j1++) {
/*      */               
/* 2078 */               if (k == 2 + j1)
/*      */               {
/* 2080 */                 updateDebugProfilerName(j1 + 1);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2087 */       for (int l = 0; l < 9; l++) {
/*      */         
/* 2089 */         if (this.gameSettings.keyBindsHotbar[l].isPressed())
/*      */         {
/* 2091 */           if (this.thePlayer.isSpectator()) {
/*      */             
/* 2093 */             this.ingameGUI.getSpectatorGui().func_175260_a(l);
/*      */ 
/*      */           
/*      */           }
/* 2097 */           else if (ItemSpoofUtil.isEnabled) {
/* 2098 */             ItemSpoofUtil.renderSlot = l;
/*      */           } else {
/* 2100 */             this.thePlayer.inventory.currentItem = l;
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 2106 */       boolean flag = (this.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN);
/*      */       
/* 2108 */       while (this.gameSettings.keyBindInventory.isPressed()) {
/*      */         
/* 2110 */         if (this.playerController.isRidingHorse()) {
/*      */           
/* 2112 */           this.thePlayer.sendHorseInventory();
/*      */           
/*      */           continue;
/*      */         } 
/* 2116 */         getNetHandler().addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
/* 2117 */         displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)this.thePlayer));
/*      */       } 
/*      */ 
/*      */       
/* 2121 */       while (this.gameSettings.keyBindDrop.isPressed()) {
/*      */         
/* 2123 */         if (!this.thePlayer.isSpectator())
/*      */         {
/* 2125 */           this.thePlayer.dropOneItem(GuiScreen.isCtrlKeyDown());
/*      */         }
/*      */       } 
/*      */       
/* 2129 */       while (this.gameSettings.keyBindChat.isPressed() && flag)
/*      */       {
/* 2131 */         displayGuiScreen((GuiScreen)new GuiChat());
/*      */       }
/*      */       
/* 2134 */       if (this.currentScreen == null && this.gameSettings.keyBindCommand.isPressed() && flag)
/*      */       {
/* 2136 */         displayGuiScreen((GuiScreen)new GuiChat("/"));
/*      */       }
/*      */       
/* 2139 */       if (this.thePlayer.isUsingItem()) {
/*      */         
/* 2141 */         if (!this.gameSettings.keyBindUseItem.isKeyDown())
/*      */         {
/* 2143 */           this.playerController.onStoppedUsingItem((EntityPlayer)this.thePlayer);
/*      */         }
/*      */         
/* 2146 */         while (this.gameSettings.keyBindAttack.isPressed());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2151 */         while (this.gameSettings.keyBindUseItem.isPressed());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2156 */         while (this.gameSettings.keyBindPickBlock.isPressed());
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 2163 */         while (this.gameSettings.keyBindAttack.isPressed())
/*      */         {
/* 2165 */           clickMouse();
/*      */         }
/*      */         
/* 2168 */         while (this.gameSettings.keyBindUseItem.isPressed())
/*      */         {
/* 2170 */           rightClickMouse();
/*      */         }
/*      */         
/* 2173 */         while (this.gameSettings.keyBindPickBlock.isPressed())
/*      */         {
/* 2175 */           middleClickMouse();
/*      */         }
/*      */       } 
/*      */       
/* 2179 */       if (this.gameSettings.keyBindUseItem.isKeyDown() && this.rightClickDelayTimer == 0 && !this.thePlayer.isUsingItem())
/*      */       {
/* 2181 */         rightClickMouse();
/*      */       }
/*      */       
/* 2184 */       sendClickBlockToController((this.currentScreen == null && this.gameSettings.keyBindAttack.isKeyDown() && this.inGameHasFocus));
/*      */     } 
/*      */     
/* 2187 */     tickEvent.setState(State.POST);
/* 2188 */     tickEvent.call();
/*      */     
/* 2190 */     if (this.theWorld != null) {
/*      */       
/* 2192 */       if (this.thePlayer != null) {
/*      */         
/* 2194 */         this.joinPlayerCounter++;
/*      */         
/* 2196 */         if (this.joinPlayerCounter == 30) {
/*      */           
/* 2198 */           this.joinPlayerCounter = 0;
/* 2199 */           this.theWorld.joinEntityInSurroundings((Entity)this.thePlayer);
/*      */         } 
/*      */       } 
/*      */       
/* 2203 */       this.mcProfiler.endStartSection("gameRenderer");
/*      */       
/* 2205 */       if (!this.isGamePaused)
/*      */       {
/* 2207 */         this.entityRenderer.updateRenderer();
/*      */       }
/*      */       
/* 2210 */       this.mcProfiler.endStartSection("levelRenderer");
/*      */       
/* 2212 */       if (!this.isGamePaused)
/*      */       {
/* 2214 */         this.renderGlobal.updateClouds();
/*      */       }
/*      */       
/* 2217 */       this.mcProfiler.endStartSection("level");
/*      */       
/* 2219 */       if (!this.isGamePaused)
/*      */       {
/* 2221 */         if (this.theWorld.getLastLightningBolt() > 0)
/*      */         {
/* 2223 */           this.theWorld.setLastLightningBolt(this.theWorld.getLastLightningBolt() - 1);
/*      */         }
/*      */         
/* 2226 */         this.theWorld.updateEntities();
/*      */       }
/*      */     
/* 2229 */     } else if (this.entityRenderer.isShaderActive()) {
/*      */       
/* 2231 */       this.entityRenderer.func_181022_b();
/*      */     } 
/*      */     
/* 2234 */     if (!this.isGamePaused) {
/*      */       
/* 2236 */       this.mcMusicTicker.update();
/* 2237 */       this.mcSoundHandler.update();
/*      */     } 
/*      */     
/* 2240 */     if (this.theWorld != null) {
/*      */       
/* 2242 */       if (!this.isGamePaused) {
/*      */         
/* 2244 */         this.theWorld.setAllowedSpawnTypes((this.theWorld.getDifficulty() != EnumDifficulty.PEACEFUL), true);
/*      */ 
/*      */         
/*      */         try {
/* 2248 */           this.theWorld.tick();
/*      */         }
/* 2250 */         catch (Throwable throwable2) {
/*      */           
/* 2252 */           CrashReport crashreport2 = CrashReport.makeCrashReport(throwable2, "Exception in world tick");
/*      */           
/* 2254 */           if (this.theWorld == null) {
/*      */             
/* 2256 */             CrashReportCategory crashreportcategory2 = crashreport2.makeCategory("Affected level");
/* 2257 */             crashreportcategory2.addCrashSection("Problem", "Level is null!");
/*      */           }
/*      */           else {
/*      */             
/* 2261 */             this.theWorld.addWorldInfoToCrashReport(crashreport2);
/*      */           } 
/*      */           
/* 2264 */           throw new ReportedException(crashreport2);
/*      */         } 
/*      */       } 
/*      */       
/* 2268 */       this.mcProfiler.endStartSection("animateTick");
/*      */       
/* 2270 */       if (!this.isGamePaused && this.theWorld != null)
/*      */       {
/* 2272 */         this.theWorld.doVoidFogParticles(MathHelper.floor_double(this.thePlayer.posX), MathHelper.floor_double(this.thePlayer.posY), MathHelper.floor_double(this.thePlayer.posZ));
/*      */       }
/*      */       
/* 2275 */       this.mcProfiler.endStartSection("particles");
/*      */       
/* 2277 */       if (!this.isGamePaused)
/*      */       {
/* 2279 */         this.effectRenderer.updateEffects();
/*      */       }
/*      */     }
/* 2282 */     else if (this.myNetworkManager != null) {
/*      */       
/* 2284 */       this.mcProfiler.endStartSection("pendingConnection");
/* 2285 */       this.myNetworkManager.processReceivedPackets();
/*      */     } 
/*      */     
/* 2288 */     this.mcProfiler.endSection();
/* 2289 */     this.systemTime = getSystemTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void launchIntegratedServer(String folderName, String worldName, WorldSettings worldSettingsIn) {
/* 2297 */     loadWorld(null);
/* 2298 */     System.gc();
/* 2299 */     ISaveHandler isavehandler = this.saveLoader.getSaveLoader(folderName, false);
/* 2300 */     WorldInfo worldinfo = isavehandler.loadWorldInfo();
/*      */     
/* 2302 */     if (worldinfo == null && worldSettingsIn != null) {
/*      */       
/* 2304 */       worldinfo = new WorldInfo(worldSettingsIn, folderName);
/* 2305 */       isavehandler.saveWorldInfo(worldinfo);
/*      */     } 
/*      */     
/* 2308 */     if (worldSettingsIn == null)
/*      */     {
/* 2310 */       worldSettingsIn = new WorldSettings(worldinfo);
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 2315 */       this.theIntegratedServer = new IntegratedServer(this, folderName, worldName, worldSettingsIn);
/* 2316 */       this.theIntegratedServer.startServerThread();
/* 2317 */       this.integratedServerIsRunning = true;
/*      */     }
/* 2319 */     catch (Throwable throwable) {
/*      */       
/* 2321 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Starting integrated server");
/* 2322 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Starting integrated server");
/* 2323 */       crashreportcategory.addCrashSection("Level ID", folderName);
/* 2324 */       crashreportcategory.addCrashSection("Level Name", worldName);
/* 2325 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */     
/* 2328 */     this.loadingScreen.displaySavingString(I18n.format("menu.loadingLevel", new Object[0]));
/*      */     
/* 2330 */     while (!this.theIntegratedServer.serverIsInRunLoop()) {
/*      */       
/* 2332 */       String s = this.theIntegratedServer.getUserMessage();
/*      */       
/* 2334 */       if (s != null) {
/*      */         
/* 2336 */         this.loadingScreen.displayLoadingString(I18n.format(s, new Object[0]));
/*      */       }
/*      */       else {
/*      */         
/* 2340 */         this.loadingScreen.displayLoadingString("");
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/* 2345 */         Thread.sleep(200L);
/*      */       }
/* 2347 */       catch (InterruptedException interruptedException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2353 */     displayGuiScreen(null);
/* 2354 */     SocketAddress socketaddress = this.theIntegratedServer.getNetworkSystem().addLocalEndpoint();
/* 2355 */     NetworkManager networkmanager = NetworkManager.provideLocalClient(socketaddress);
/* 2356 */     networkmanager.setNetHandler((INetHandler)new NetHandlerLoginClient(networkmanager, this, null));
/* 2357 */     networkmanager.sendPacket((Packet)new C00Handshake(47, socketaddress.toString(), 0, EnumConnectionState.LOGIN));
/* 2358 */     networkmanager.sendPacket((Packet)new C00PacketLoginStart(getSession().getProfile()));
/* 2359 */     this.myNetworkManager = networkmanager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadWorld(WorldClient worldClientIn) {
/* 2367 */     loadWorld(worldClientIn, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadWorld(WorldClient worldClientIn, String loadingMessage) {
/* 2375 */     if (worldClientIn == null) {
/*      */       
/* 2377 */       NetHandlerPlayClient nethandlerplayclient = getNetHandler();
/*      */       
/* 2379 */       if (nethandlerplayclient != null)
/*      */       {
/* 2381 */         nethandlerplayclient.cleanup();
/*      */       }
/*      */       
/* 2384 */       if (this.theIntegratedServer != null && this.theIntegratedServer.isAnvilFileSet()) {
/*      */         
/* 2386 */         this.theIntegratedServer.initiateShutdown();
/* 2387 */         this.theIntegratedServer.setStaticInstance();
/*      */       } 
/*      */       
/* 2390 */       this.theIntegratedServer = null;
/* 2391 */       this.guiAchievement.clearAchievements();
/* 2392 */       this.entityRenderer.getMapItemRenderer().clearLoadedMaps();
/*      */     } 
/*      */     
/* 2395 */     this.renderViewEntity = null;
/* 2396 */     this.myNetworkManager = null;
/*      */     
/* 2398 */     if (this.loadingScreen != null) {
/*      */       
/* 2400 */       this.loadingScreen.resetProgressAndMessage(loadingMessage);
/* 2401 */       this.loadingScreen.displayLoadingString("");
/*      */     } 
/*      */     
/* 2404 */     if (worldClientIn == null && this.theWorld != null) {
/*      */       
/* 2406 */       this.mcResourcePackRepository.func_148529_f();
/* 2407 */       this.ingameGUI.func_181029_i();
/* 2408 */       setServerData(null);
/* 2409 */       this.integratedServerIsRunning = false;
/*      */     } 
/*      */     
/* 2412 */     this.mcSoundHandler.stopSounds();
/* 2413 */     this.theWorld = worldClientIn;
/*      */     
/* 2415 */     if (worldClientIn != null) {
/*      */       
/* 2417 */       if (this.renderGlobal != null)
/*      */       {
/* 2419 */         this.renderGlobal.setWorldAndLoadRenderers(worldClientIn);
/*      */       }
/*      */       
/* 2422 */       if (this.effectRenderer != null)
/*      */       {
/* 2424 */         this.effectRenderer.clearEffects((World)worldClientIn);
/*      */       }
/*      */       
/* 2427 */       if (this.thePlayer == null) {
/*      */         
/* 2429 */         this.thePlayer = this.playerController.func_178892_a((World)worldClientIn, new StatFileWriter());
/* 2430 */         this.playerController.flipPlayer((EntityPlayer)this.thePlayer);
/*      */       } 
/*      */       
/* 2433 */       this.thePlayer.preparePlayerToSpawn();
/* 2434 */       worldClientIn.spawnEntityInWorld((Entity)this.thePlayer);
/* 2435 */       this.thePlayer.movementInput = (MovementInput)new MovementInputFromOptions(this.gameSettings);
/* 2436 */       this.playerController.setPlayerCapabilities((EntityPlayer)this.thePlayer);
/* 2437 */       this.renderViewEntity = (Entity)this.thePlayer;
/*      */     }
/*      */     else {
/*      */       
/* 2441 */       this.saveLoader.flushCache();
/* 2442 */       this.thePlayer = null;
/*      */     } 
/*      */     
/* 2445 */     System.gc();
/* 2446 */     this.systemTime = 0L;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDimensionAndSpawnPlayer(int dimension) {
/* 2451 */     this.theWorld.setInitialSpawnLocation();
/* 2452 */     this.theWorld.removeAllEntities();
/* 2453 */     int i = 0;
/* 2454 */     String s = null;
/*      */     
/* 2456 */     if (this.thePlayer != null) {
/*      */       
/* 2458 */       i = this.thePlayer.getEntityId();
/* 2459 */       this.theWorld.removeEntity((Entity)this.thePlayer);
/* 2460 */       s = this.thePlayer.getClientBrand();
/*      */     } 
/*      */     
/* 2463 */     this.renderViewEntity = null;
/* 2464 */     EntityPlayerSP entityplayersp = this.thePlayer;
/* 2465 */     this.thePlayer = this.playerController.func_178892_a((World)this.theWorld, (this.thePlayer == null) ? new StatFileWriter() : this.thePlayer.getStatFileWriter());
/* 2466 */     this.thePlayer.getDataWatcher().updateWatchedObjectsFromList(entityplayersp.getDataWatcher().getAllWatched());
/* 2467 */     this.thePlayer.dimension = dimension;
/* 2468 */     this.renderViewEntity = (Entity)this.thePlayer;
/* 2469 */     this.thePlayer.preparePlayerToSpawn();
/* 2470 */     this.thePlayer.setClientBrand(s);
/* 2471 */     this.theWorld.spawnEntityInWorld((Entity)this.thePlayer);
/* 2472 */     this.playerController.flipPlayer((EntityPlayer)this.thePlayer);
/* 2473 */     this.thePlayer.movementInput = (MovementInput)new MovementInputFromOptions(this.gameSettings);
/* 2474 */     this.thePlayer.setEntityId(i);
/* 2475 */     this.playerController.setPlayerCapabilities((EntityPlayer)this.thePlayer);
/* 2476 */     this.thePlayer.setReducedDebug(entityplayersp.hasReducedDebug());
/*      */     
/* 2478 */     if (this.currentScreen instanceof GuiGameOver)
/*      */     {
/* 2480 */       displayGuiScreen(null);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public NetHandlerPlayClient getNetHandler() {
/* 2486 */     return (this.thePlayer != null) ? this.thePlayer.sendQueue : null;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isGuiEnabled() {
/* 2491 */     return (theMinecraft == null || !theMinecraft.gameSettings.hideGUI);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFancyGraphicsEnabled() {
/* 2496 */     return (theMinecraft != null && theMinecraft.gameSettings.fancyGraphics);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAmbientOcclusionEnabled() {
/* 2504 */     return (theMinecraft != null && theMinecraft.gameSettings.ambientOcclusion != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void middleClickMouse() {
/* 2512 */     if (this.objectMouseOver != null) {
/*      */       Item item;
/* 2514 */       boolean flag = this.thePlayer.capabilities.isCreativeMode;
/* 2515 */       int i = 0;
/* 2516 */       boolean flag1 = false;
/* 2517 */       TileEntity tileentity = null;
/*      */ 
/*      */       
/* 2520 */       if (this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/*      */         
/* 2522 */         BlockPos blockpos = this.objectMouseOver.getBlockPos();
/* 2523 */         Block block = this.theWorld.getBlockState(blockpos).getBlock();
/*      */         
/* 2525 */         if (block.getMaterial() == Material.air) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 2530 */         item = block.getItem((World)this.theWorld, blockpos);
/*      */         
/* 2532 */         if (item == null) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 2537 */         if (flag && GuiScreen.isCtrlKeyDown())
/*      */         {
/* 2539 */           tileentity = this.theWorld.getTileEntity(blockpos);
/*      */         }
/*      */         
/* 2542 */         Block block1 = (item instanceof net.minecraft.item.ItemBlock && !block.isFlowerPot()) ? Block.getBlockFromItem(item) : block;
/* 2543 */         i = block1.getDamageValue((World)this.theWorld, blockpos);
/* 2544 */         flag1 = item.getHasSubtypes();
/*      */       }
/*      */       else {
/*      */         
/* 2548 */         if (this.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY || this.objectMouseOver.entityHit == null || !flag) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 2553 */         if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.item.EntityPainting) {
/*      */           
/* 2555 */           item = Items.painting;
/*      */         }
/* 2557 */         else if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.EntityLeashKnot) {
/*      */           
/* 2559 */           item = Items.lead;
/*      */         }
/* 2561 */         else if (this.objectMouseOver.entityHit instanceof EntityItemFrame) {
/*      */           
/* 2563 */           EntityItemFrame entityitemframe = (EntityItemFrame)this.objectMouseOver.entityHit;
/* 2564 */           ItemStack itemstack = entityitemframe.getDisplayedItem();
/*      */           
/* 2566 */           if (itemstack == null)
/*      */           {
/* 2568 */             item = Items.item_frame;
/*      */           }
/*      */           else
/*      */           {
/* 2572 */             item = itemstack.getItem();
/* 2573 */             i = itemstack.getMetadata();
/* 2574 */             flag1 = true;
/*      */           }
/*      */         
/* 2577 */         } else if (this.objectMouseOver.entityHit instanceof EntityMinecart) {
/*      */           
/* 2579 */           EntityMinecart entityminecart = (EntityMinecart)this.objectMouseOver.entityHit;
/*      */           
/* 2581 */           switch (entityminecart.getMinecartType()) {
/*      */             
/*      */             case FURNACE:
/* 2584 */               item = Items.furnace_minecart;
/*      */               break;
/*      */             
/*      */             case CHEST:
/* 2588 */               item = Items.chest_minecart;
/*      */               break;
/*      */             
/*      */             case TNT:
/* 2592 */               item = Items.tnt_minecart;
/*      */               break;
/*      */             
/*      */             case HOPPER:
/* 2596 */               item = Items.hopper_minecart;
/*      */               break;
/*      */             
/*      */             case COMMAND_BLOCK:
/* 2600 */               item = Items.command_block_minecart;
/*      */               break;
/*      */             
/*      */             default:
/* 2604 */               item = Items.minecart;
/*      */               break;
/*      */           } 
/* 2607 */         } else if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.item.EntityBoat) {
/*      */           
/* 2609 */           item = Items.boat;
/*      */         }
/* 2611 */         else if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.item.EntityArmorStand) {
/*      */           
/* 2613 */           ItemArmorStand itemArmorStand = Items.armor_stand;
/*      */         }
/*      */         else {
/*      */           
/* 2617 */           item = Items.spawn_egg;
/* 2618 */           i = EntityList.getEntityID(this.objectMouseOver.entityHit);
/* 2619 */           flag1 = true;
/*      */           
/* 2621 */           if (!EntityList.entityEggs.containsKey(Integer.valueOf(i))) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2628 */       InventoryPlayer inventoryplayer = this.thePlayer.inventory;
/*      */       
/* 2630 */       if (tileentity == null) {
/*      */         
/* 2632 */         inventoryplayer.setCurrentItem(item, i, flag1, flag);
/*      */       }
/*      */       else {
/*      */         
/* 2636 */         ItemStack itemstack1 = func_181036_a(item, i, tileentity);
/* 2637 */         inventoryplayer.setInventorySlotContents(inventoryplayer.currentItem, itemstack1);
/*      */       } 
/*      */       
/* 2640 */       if (flag) {
/*      */         
/* 2642 */         int j = this.thePlayer.inventoryContainer.inventorySlots.size() - 9 + inventoryplayer.currentItem;
/* 2643 */         this.playerController.sendSlotPacket(inventoryplayer.getStackInSlot(inventoryplayer.currentItem), j);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private ItemStack func_181036_a(Item p_181036_1_, int p_181036_2_, TileEntity p_181036_3_) {
/* 2650 */     ItemStack itemstack = new ItemStack(p_181036_1_, 1, p_181036_2_);
/* 2651 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 2652 */     p_181036_3_.writeToNBT(nbttagcompound);
/*      */     
/* 2654 */     if (p_181036_1_ == Items.skull && nbttagcompound.hasKey("Owner")) {
/*      */       
/* 2656 */       NBTTagCompound nbttagcompound2 = nbttagcompound.getCompoundTag("Owner");
/* 2657 */       NBTTagCompound nbttagcompound3 = new NBTTagCompound();
/* 2658 */       nbttagcompound3.setTag("SkullOwner", (NBTBase)nbttagcompound2);
/* 2659 */       itemstack.setTagCompound(nbttagcompound3);
/*      */     }
/*      */     else {
/*      */       
/* 2663 */       itemstack.setTagInfo("BlockEntityTag", (NBTBase)nbttagcompound);
/* 2664 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 2665 */       NBTTagList nbttaglist = new NBTTagList();
/* 2666 */       nbttaglist.appendTag((NBTBase)new NBTTagString("(+NBT)"));
/* 2667 */       nbttagcompound1.setTag("Lore", (NBTBase)nbttaglist);
/* 2668 */       itemstack.setTagInfo("display", (NBTBase)nbttagcompound1);
/*      */     } 
/* 2670 */     return itemstack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CrashReport addGraphicsAndWorldToCrashReport(CrashReport theCrash) {
/* 2678 */     theCrash.getCategory().addCrashSectionCallable("Launched Version", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2682 */             return Minecraft.this.launchedVersion;
/*      */           }
/*      */         });
/* 2685 */     theCrash.getCategory().addCrashSectionCallable("LWJGL", new Callable<String>()
/*      */         {
/*      */           public String call()
/*      */           {
/* 2689 */             return Sys.getVersion();
/*      */           }
/*      */         });
/* 2692 */     theCrash.getCategory().addCrashSectionCallable("OpenGL", new Callable<String>()
/*      */         {
/*      */           public String call()
/*      */           {
/* 2696 */             return GL11.glGetString(7937) + " GL version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936);
/*      */           }
/*      */         });
/* 2699 */     theCrash.getCategory().addCrashSectionCallable("GL Caps", new Callable<String>()
/*      */         {
/*      */           public String call()
/*      */           {
/* 2703 */             return OpenGlHelper.getLogText();
/*      */           }
/*      */         });
/* 2706 */     theCrash.getCategory().addCrashSectionCallable("Using VBOs", new Callable<String>()
/*      */         {
/*      */           public String call()
/*      */           {
/* 2710 */             return Minecraft.this.gameSettings.useVbo ? "Yes" : "No";
/*      */           }
/*      */         });
/* 2713 */     theCrash.getCategory().addCrashSectionCallable("Is Modded", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2717 */             String s = ClientBrandRetriever.getClientModName();
/* 2718 */             return !s.equals("vanilla") ? ("Definitely; Client brand changed to '" + s + "'") : ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.");
/*      */           }
/*      */         });
/* 2721 */     theCrash.getCategory().addCrashSectionCallable("Type", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2725 */             return "Client (map_client.txt)";
/*      */           }
/*      */         });
/* 2728 */     theCrash.getCategory().addCrashSectionCallable("Resource Packs", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2732 */             StringBuilder stringbuilder = new StringBuilder();
/*      */             
/* 2734 */             for (String s : Minecraft.this.gameSettings.resourcePacks) {
/*      */               
/* 2736 */               if (stringbuilder.length() > 0)
/*      */               {
/* 2738 */                 stringbuilder.append(", ");
/*      */               }
/*      */               
/* 2741 */               stringbuilder.append(s);
/*      */               
/* 2743 */               if (Minecraft.this.gameSettings.field_183018_l.contains(s))
/*      */               {
/* 2745 */                 stringbuilder.append(" (incompatible)");
/*      */               }
/*      */             } 
/*      */             
/* 2749 */             return stringbuilder.toString();
/*      */           }
/*      */         });
/* 2752 */     theCrash.getCategory().addCrashSectionCallable("Current Language", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2756 */             return Minecraft.this.mcLanguageManager.getCurrentLanguage().toString();
/*      */           }
/*      */         });
/* 2759 */     theCrash.getCategory().addCrashSectionCallable("Profiler Position", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2763 */             return Minecraft.this.mcProfiler.profilingEnabled ? Minecraft.this.mcProfiler.getNameOfLastSection() : "N/A (disabled)";
/*      */           }
/*      */         });
/* 2766 */     theCrash.getCategory().addCrashSectionCallable("CPU", new Callable<String>()
/*      */         {
/*      */           public String call()
/*      */           {
/* 2770 */             return OpenGlHelper.func_183029_j();
/*      */           }
/*      */         });
/*      */     
/* 2774 */     if (this.theWorld != null)
/*      */     {
/* 2776 */       this.theWorld.addWorldInfoToCrashReport(theCrash);
/*      */     }
/*      */     
/* 2779 */     return theCrash;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Minecraft getMinecraft() {
/* 2787 */     return theMinecraft;
/*      */   }
/*      */   public static void setMinecraft(Minecraft mc) {
/* 2790 */     theMinecraft = mc;
/*      */   }
/*      */   
/*      */   public ListenableFuture<Object> scheduleResourcesRefresh() {
/* 2794 */     return addScheduledTask(new Runnable()
/*      */         {
/*      */           public void run()
/*      */           {
/* 2798 */             Minecraft.this.refreshResources();
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public void addServerStatsToSnooper(PlayerUsageSnooper playerSnooper) {
/* 2805 */     playerSnooper.addClientStat("fps", Integer.valueOf(debugFPS));
/* 2806 */     playerSnooper.addClientStat("vsync_enabled", Boolean.valueOf(this.gameSettings.enableVsync));
/* 2807 */     playerSnooper.addClientStat("display_frequency", Integer.valueOf(Display.getDisplayMode().getFrequency()));
/* 2808 */     playerSnooper.addClientStat("display_type", this.fullscreen ? "fullscreen" : "windowed");
/* 2809 */     playerSnooper.addClientStat("run_time", Long.valueOf((MinecraftServer.getCurrentTimeMillis() - playerSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L));
/* 2810 */     playerSnooper.addClientStat("current_action", func_181538_aA());
/* 2811 */     String s = (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) ? "little" : "big";
/* 2812 */     playerSnooper.addClientStat("endianness", s);
/* 2813 */     playerSnooper.addClientStat("resource_packs", Integer.valueOf(this.mcResourcePackRepository.getRepositoryEntries().size()));
/* 2814 */     int i = 0;
/*      */     
/* 2816 */     for (ResourcePackRepository.Entry resourcepackrepository$entry : this.mcResourcePackRepository.getRepositoryEntries())
/*      */     {
/* 2818 */       playerSnooper.addClientStat("resource_pack[" + i++ + "]", resourcepackrepository$entry.getResourcePackName());
/*      */     }
/*      */     
/* 2821 */     if (this.theIntegratedServer != null && this.theIntegratedServer.getPlayerUsageSnooper() != null)
/*      */     {
/* 2823 */       playerSnooper.addClientStat("snooper_partner", this.theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private String func_181538_aA() {
/* 2829 */     return (this.theIntegratedServer != null) ? (this.theIntegratedServer.getPublic() ? "hosting_lan" : "singleplayer") : ((this.currentServerData != null) ? (this.currentServerData.func_181041_d() ? "playing_lan" : "multiplayer") : "out_of_game");
/*      */   }
/*      */ 
/*      */   
/*      */   public void addServerTypeToSnooper(PlayerUsageSnooper playerSnooper) {
/* 2834 */     playerSnooper.addStatToSnooper("opengl_version", GL11.glGetString(7938));
/* 2835 */     playerSnooper.addStatToSnooper("opengl_vendor", GL11.glGetString(7936));
/* 2836 */     playerSnooper.addStatToSnooper("client_brand", ClientBrandRetriever.getClientModName());
/* 2837 */     playerSnooper.addStatToSnooper("launched_version", this.launchedVersion);
/* 2838 */     ContextCapabilities contextcapabilities = GLContext.getCapabilities();
/* 2839 */     playerSnooper.addStatToSnooper("gl_caps[ARB_arrays_of_arrays]", Boolean.valueOf(contextcapabilities.GL_ARB_arrays_of_arrays));
/* 2840 */     playerSnooper.addStatToSnooper("gl_caps[ARB_base_instance]", Boolean.valueOf(contextcapabilities.GL_ARB_base_instance));
/* 2841 */     playerSnooper.addStatToSnooper("gl_caps[ARB_blend_func_extended]", Boolean.valueOf(contextcapabilities.GL_ARB_blend_func_extended));
/* 2842 */     playerSnooper.addStatToSnooper("gl_caps[ARB_clear_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_clear_buffer_object));
/* 2843 */     playerSnooper.addStatToSnooper("gl_caps[ARB_color_buffer_float]", Boolean.valueOf(contextcapabilities.GL_ARB_color_buffer_float));
/* 2844 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compatibility]", Boolean.valueOf(contextcapabilities.GL_ARB_compatibility));
/* 2845 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compressed_texture_pixel_storage]", Boolean.valueOf(contextcapabilities.GL_ARB_compressed_texture_pixel_storage));
/* 2846 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_compute_shader));
/* 2847 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_buffer));
/* 2848 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_image));
/* 2849 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_buffer_float));
/* 2850 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_compute_shader));
/* 2851 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_buffer));
/* 2852 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_image));
/* 2853 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_buffer_float));
/* 2854 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_clamp]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_clamp));
/* 2855 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_texture]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_texture));
/* 2856 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_buffers));
/* 2857 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers_blend]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_buffers_blend));
/* 2858 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_elements_base_vertex]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_elements_base_vertex));
/* 2859 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_indirect]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_indirect));
/* 2860 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_instanced]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_instanced));
/* 2861 */     playerSnooper.addStatToSnooper("gl_caps[ARB_explicit_attrib_location]", Boolean.valueOf(contextcapabilities.GL_ARB_explicit_attrib_location));
/* 2862 */     playerSnooper.addStatToSnooper("gl_caps[ARB_explicit_uniform_location]", Boolean.valueOf(contextcapabilities.GL_ARB_explicit_uniform_location));
/* 2863 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_layer_viewport]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_layer_viewport));
/* 2864 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_program]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_program));
/* 2865 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_shader));
/* 2866 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_program_shadow]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_program_shadow));
/* 2867 */     playerSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_framebuffer_object));
/* 2868 */     playerSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_sRGB]", Boolean.valueOf(contextcapabilities.GL_ARB_framebuffer_sRGB));
/* 2869 */     playerSnooper.addStatToSnooper("gl_caps[ARB_geometry_shader4]", Boolean.valueOf(contextcapabilities.GL_ARB_geometry_shader4));
/* 2870 */     playerSnooper.addStatToSnooper("gl_caps[ARB_gpu_shader5]", Boolean.valueOf(contextcapabilities.GL_ARB_gpu_shader5));
/* 2871 */     playerSnooper.addStatToSnooper("gl_caps[ARB_half_float_pixel]", Boolean.valueOf(contextcapabilities.GL_ARB_half_float_pixel));
/* 2872 */     playerSnooper.addStatToSnooper("gl_caps[ARB_half_float_vertex]", Boolean.valueOf(contextcapabilities.GL_ARB_half_float_vertex));
/* 2873 */     playerSnooper.addStatToSnooper("gl_caps[ARB_instanced_arrays]", Boolean.valueOf(contextcapabilities.GL_ARB_instanced_arrays));
/* 2874 */     playerSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_alignment]", Boolean.valueOf(contextcapabilities.GL_ARB_map_buffer_alignment));
/* 2875 */     playerSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_range]", Boolean.valueOf(contextcapabilities.GL_ARB_map_buffer_range));
/* 2876 */     playerSnooper.addStatToSnooper("gl_caps[ARB_multisample]", Boolean.valueOf(contextcapabilities.GL_ARB_multisample));
/* 2877 */     playerSnooper.addStatToSnooper("gl_caps[ARB_multitexture]", Boolean.valueOf(contextcapabilities.GL_ARB_multitexture));
/* 2878 */     playerSnooper.addStatToSnooper("gl_caps[ARB_occlusion_query2]", Boolean.valueOf(contextcapabilities.GL_ARB_occlusion_query2));
/* 2879 */     playerSnooper.addStatToSnooper("gl_caps[ARB_pixel_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_pixel_buffer_object));
/* 2880 */     playerSnooper.addStatToSnooper("gl_caps[ARB_seamless_cube_map]", Boolean.valueOf(contextcapabilities.GL_ARB_seamless_cube_map));
/* 2881 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shader_objects]", Boolean.valueOf(contextcapabilities.GL_ARB_shader_objects));
/* 2882 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shader_stencil_export]", Boolean.valueOf(contextcapabilities.GL_ARB_shader_stencil_export));
/* 2883 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shader_texture_lod]", Boolean.valueOf(contextcapabilities.GL_ARB_shader_texture_lod));
/* 2884 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shadow]", Boolean.valueOf(contextcapabilities.GL_ARB_shadow));
/* 2885 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shadow_ambient]", Boolean.valueOf(contextcapabilities.GL_ARB_shadow_ambient));
/* 2886 */     playerSnooper.addStatToSnooper("gl_caps[ARB_stencil_texturing]", Boolean.valueOf(contextcapabilities.GL_ARB_stencil_texturing));
/* 2887 */     playerSnooper.addStatToSnooper("gl_caps[ARB_sync]", Boolean.valueOf(contextcapabilities.GL_ARB_sync));
/* 2888 */     playerSnooper.addStatToSnooper("gl_caps[ARB_tessellation_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_tessellation_shader));
/* 2889 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_border_clamp]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_border_clamp));
/* 2890 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_buffer_object));
/* 2891 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_cube_map));
/* 2892 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map_array]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_cube_map_array));
/* 2893 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_non_power_of_two]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_non_power_of_two));
/* 2894 */     playerSnooper.addStatToSnooper("gl_caps[ARB_uniform_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_uniform_buffer_object));
/* 2895 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_blend]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_blend));
/* 2896 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_buffer_object));
/* 2897 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_program]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_program));
/* 2898 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_shader));
/* 2899 */     playerSnooper.addStatToSnooper("gl_caps[EXT_bindable_uniform]", Boolean.valueOf(contextcapabilities.GL_EXT_bindable_uniform));
/* 2900 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_equation_separate]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_equation_separate));
/* 2901 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_func_separate]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_func_separate));
/* 2902 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_minmax]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_minmax));
/* 2903 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_subtract]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_subtract));
/* 2904 */     playerSnooper.addStatToSnooper("gl_caps[EXT_draw_instanced]", Boolean.valueOf(contextcapabilities.GL_EXT_draw_instanced));
/* 2905 */     playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_multisample]", Boolean.valueOf(contextcapabilities.GL_EXT_framebuffer_multisample));
/* 2906 */     playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_object]", Boolean.valueOf(contextcapabilities.GL_EXT_framebuffer_object));
/* 2907 */     playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_sRGB]", Boolean.valueOf(contextcapabilities.GL_EXT_framebuffer_sRGB));
/* 2908 */     playerSnooper.addStatToSnooper("gl_caps[EXT_geometry_shader4]", Boolean.valueOf(contextcapabilities.GL_EXT_geometry_shader4));
/* 2909 */     playerSnooper.addStatToSnooper("gl_caps[EXT_gpu_program_parameters]", Boolean.valueOf(contextcapabilities.GL_EXT_gpu_program_parameters));
/* 2910 */     playerSnooper.addStatToSnooper("gl_caps[EXT_gpu_shader4]", Boolean.valueOf(contextcapabilities.GL_EXT_gpu_shader4));
/* 2911 */     playerSnooper.addStatToSnooper("gl_caps[EXT_multi_draw_arrays]", Boolean.valueOf(contextcapabilities.GL_EXT_multi_draw_arrays));
/* 2912 */     playerSnooper.addStatToSnooper("gl_caps[EXT_packed_depth_stencil]", Boolean.valueOf(contextcapabilities.GL_EXT_packed_depth_stencil));
/* 2913 */     playerSnooper.addStatToSnooper("gl_caps[EXT_paletted_texture]", Boolean.valueOf(contextcapabilities.GL_EXT_paletted_texture));
/* 2914 */     playerSnooper.addStatToSnooper("gl_caps[EXT_rescale_normal]", Boolean.valueOf(contextcapabilities.GL_EXT_rescale_normal));
/* 2915 */     playerSnooper.addStatToSnooper("gl_caps[EXT_separate_shader_objects]", Boolean.valueOf(contextcapabilities.GL_EXT_separate_shader_objects));
/* 2916 */     playerSnooper.addStatToSnooper("gl_caps[EXT_shader_image_load_store]", Boolean.valueOf(contextcapabilities.GL_EXT_shader_image_load_store));
/* 2917 */     playerSnooper.addStatToSnooper("gl_caps[EXT_shadow_funcs]", Boolean.valueOf(contextcapabilities.GL_EXT_shadow_funcs));
/* 2918 */     playerSnooper.addStatToSnooper("gl_caps[EXT_shared_texture_palette]", Boolean.valueOf(contextcapabilities.GL_EXT_shared_texture_palette));
/* 2919 */     playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_clear_tag]", Boolean.valueOf(contextcapabilities.GL_EXT_stencil_clear_tag));
/* 2920 */     playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_two_side]", Boolean.valueOf(contextcapabilities.GL_EXT_stencil_two_side));
/* 2921 */     playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_wrap]", Boolean.valueOf(contextcapabilities.GL_EXT_stencil_wrap));
/* 2922 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_3d]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_3d));
/* 2923 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_array]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_array));
/* 2924 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_buffer_object]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_buffer_object));
/* 2925 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_integer]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_integer));
/* 2926 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_lod_bias]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_lod_bias));
/* 2927 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_sRGB]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_sRGB));
/* 2928 */     playerSnooper.addStatToSnooper("gl_caps[EXT_vertex_shader]", Boolean.valueOf(contextcapabilities.GL_EXT_vertex_shader));
/* 2929 */     playerSnooper.addStatToSnooper("gl_caps[EXT_vertex_weighting]", Boolean.valueOf(contextcapabilities.GL_EXT_vertex_weighting));
/* 2930 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_uniforms]", Integer.valueOf(GL11.glGetInteger(35658)));
/* 2931 */     GL11.glGetError();
/* 2932 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_fragment_uniforms]", Integer.valueOf(GL11.glGetInteger(35657)));
/* 2933 */     GL11.glGetError();
/* 2934 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_attribs]", Integer.valueOf(GL11.glGetInteger(34921)));
/* 2935 */     GL11.glGetError();
/* 2936 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_texture_image_units]", Integer.valueOf(GL11.glGetInteger(35660)));
/* 2937 */     GL11.glGetError();
/* 2938 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", Integer.valueOf(GL11.glGetInteger(34930)));
/* 2939 */     GL11.glGetError();
/* 2940 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", Integer.valueOf(GL11.glGetInteger(35071)));
/* 2941 */     GL11.glGetError();
/* 2942 */     playerSnooper.addStatToSnooper("gl_max_texture_size", Integer.valueOf(getGLMaximumTextureSize()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getGLMaximumTextureSize() {
/* 2950 */     for (int i = 16384; i > 0; i >>= 1) {
/*      */       
/* 2952 */       GL11.glTexImage2D(32868, 0, 6408, i, i, 0, 6408, 5121, (ByteBuffer)null);
/* 2953 */       int j = GL11.glGetTexLevelParameteri(32868, 0, 4096);
/*      */       
/* 2955 */       if (j != 0)
/*      */       {
/* 2957 */         return i;
/*      */       }
/*      */     } 
/*      */     
/* 2961 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSnooperEnabled() {
/* 2969 */     return this.gameSettings.snooperEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setServerData(ServerData serverDataIn) {
/* 2977 */     this.currentServerData = serverDataIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public ServerData getCurrentServerData() {
/* 2982 */     return this.currentServerData;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isIntegratedServerRunning() {
/* 2987 */     return this.integratedServerIsRunning;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSingleplayer() {
/* 2995 */     return (this.integratedServerIsRunning && this.theIntegratedServer != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntegratedServer getIntegratedServer() {
/* 3003 */     return this.theIntegratedServer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void stopIntegratedServer() {
/* 3008 */     if (theMinecraft != null) {
/*      */       
/* 3010 */       IntegratedServer integratedserver = theMinecraft.getIntegratedServer();
/*      */       
/* 3012 */       if (integratedserver != null)
/*      */       {
/* 3014 */         integratedserver.stopServer();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlayerUsageSnooper getPlayerUsageSnooper() {
/* 3024 */     return this.usageSnooper;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getSystemTime() {
/* 3032 */     return Sys.getTime() * 1000L / Sys.getTimerResolution();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFullScreen() {
/* 3040 */     return this.fullscreen;
/*      */   }
/*      */ 
/*      */   
/*      */   public Session getSession() {
/* 3045 */     return this.session;
/*      */   }
/*      */ 
/*      */   
/*      */   public PropertyMap func_181037_M() {
/* 3050 */     if (this.field_181038_N.isEmpty()) {
/*      */       
/* 3052 */       GameProfile gameprofile = getSessionService().fillProfileProperties(this.session.getProfile(), false);
/* 3053 */       this.field_181038_N.putAll((Multimap)gameprofile.getProperties());
/*      */     } 
/*      */     
/* 3056 */     return this.field_181038_N;
/*      */   }
/*      */ 
/*      */   
/*      */   public Proxy getProxy() {
/* 3061 */     return this.proxy;
/*      */   }
/*      */ 
/*      */   
/*      */   public TextureManager getTextureManager() {
/* 3066 */     return this.renderEngine;
/*      */   }
/*      */ 
/*      */   
/*      */   public IResourceManager getResourceManager() {
/* 3071 */     return (IResourceManager)this.mcResourceManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public ResourcePackRepository getResourcePackRepository() {
/* 3076 */     return this.mcResourcePackRepository;
/*      */   }
/*      */ 
/*      */   
/*      */   public LanguageManager getLanguageManager() {
/* 3081 */     return this.mcLanguageManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public TextureMap getTextureMapBlocks() {
/* 3086 */     return this.textureMapBlocks;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isJava64bit() {
/* 3091 */     return this.jvm64bit;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isGamePaused() {
/* 3096 */     return this.isGamePaused;
/*      */   }
/*      */ 
/*      */   
/*      */   public SoundHandler getSoundHandler() {
/* 3101 */     return this.mcSoundHandler;
/*      */   }
/*      */ 
/*      */   
/*      */   public MusicTicker.MusicType getAmbientMusicType() {
/* 3106 */     return (this.thePlayer != null) ? ((this.thePlayer.worldObj.provider instanceof net.minecraft.world.WorldProviderHell) ? MusicTicker.MusicType.NETHER : ((this.thePlayer.worldObj.provider instanceof net.minecraft.world.WorldProviderEnd) ? ((BossStatus.bossName != null && BossStatus.statusBarTime > 0) ? MusicTicker.MusicType.END_BOSS : MusicTicker.MusicType.END) : ((this.thePlayer.capabilities.isCreativeMode && this.thePlayer.capabilities.allowFlying) ? MusicTicker.MusicType.CREATIVE : MusicTicker.MusicType.GAME))) : MusicTicker.MusicType.MENU;
/*      */   }
/*      */ 
/*      */   
/*      */   public void dispatchKeypresses() {
/* 3111 */     int i = (Keyboard.getEventKey() == 0) ? Keyboard.getEventCharacter() : Keyboard.getEventKey();
/*      */     
/* 3113 */     if (i != 0 && !Keyboard.isRepeatEvent())
/*      */     {
/* 3115 */       if (!(this.currentScreen instanceof GuiControls) || ((GuiControls)this.currentScreen).time <= getSystemTime() - 20L)
/*      */       {
/* 3117 */         if (Keyboard.getEventKeyState())
/*      */         {
/* 3119 */           if (i == this.gameSettings.keyBindFullscreen.getKeyCode()) {
/*      */             
/* 3121 */             toggleFullscreen();
/*      */           }
/* 3123 */           else if (i == this.gameSettings.keyBindScreenshot.getKeyCode()) {
/*      */             
/* 3125 */             this.ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot(this.mcDataDir, this.displayWidth, this.displayHeight, this.framebufferMc));
/*      */           } 
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public MinecraftSessionService getSessionService() {
/* 3134 */     return this.sessionService;
/*      */   }
/*      */ 
/*      */   
/*      */   public SkinManager getSkinManager() {
/* 3139 */     return this.skinManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public Entity getRenderViewEntity() {
/* 3144 */     return this.renderViewEntity;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRenderViewEntity(Entity viewingEntity) {
/* 3149 */     this.renderViewEntity = viewingEntity;
/* 3150 */     this.entityRenderer.loadEntityShader(viewingEntity);
/*      */   }
/*      */ 
/*      */   
/*      */   public <V> ListenableFuture<V> addScheduledTask(Callable<V> callableToSchedule) {
/* 3155 */     Validate.notNull(callableToSchedule);
/*      */     
/* 3157 */     if (!isCallingFromMinecraftThread()) {
/*      */       
/* 3159 */       ListenableFutureTask<V> listenablefuturetask = ListenableFutureTask.create(callableToSchedule);
/*      */       
/* 3161 */       synchronized (this.scheduledTasks) {
/*      */         
/* 3163 */         this.scheduledTasks.add(listenablefuturetask);
/* 3164 */         return (ListenableFuture<V>)listenablefuturetask;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 3171 */       return Futures.immediateFuture(callableToSchedule.call());
/*      */     }
/* 3173 */     catch (Exception exception) {
/*      */       
/* 3175 */       return (ListenableFuture<V>)Futures.immediateFailedCheckedFuture(exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ListenableFuture<Object> addScheduledTask(Runnable runnableToSchedule) {
/* 3182 */     Validate.notNull(runnableToSchedule);
/* 3183 */     return addScheduledTask(Executors.callable(runnableToSchedule));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCallingFromMinecraftThread() {
/* 3188 */     return (Thread.currentThread() == this.mcThread);
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockRendererDispatcher getBlockRendererDispatcher() {
/* 3193 */     return this.blockRenderDispatcher;
/*      */   }
/*      */ 
/*      */   
/*      */   public RenderManager getRenderManager() {
/* 3198 */     return this.renderManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public RenderItem getRenderItem() {
/* 3203 */     return this.renderItem;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemRenderer getItemRenderer() {
/* 3208 */     return this.itemRenderer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getDebugFPS() {
/* 3213 */     return debugFPS;
/*      */   }
/*      */ 
/*      */   
/*      */   public FrameTimer func_181539_aj() {
/* 3218 */     return this.field_181542_y;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Map<String, String> getSessionInfo() {
/* 3223 */     Map<String, String> map = Maps.newHashMap();
/* 3224 */     map.put("X-Minecraft-Username", getMinecraft().getSession().getUsername());
/* 3225 */     map.put("X-Minecraft-UUID", getMinecraft().getSession().getPlayerID());
/* 3226 */     map.put("X-Minecraft-Version", "1.8.9");
/* 3227 */     return map;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_181540_al() {
/* 3232 */     return this.field_181541_X;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_181537_a(boolean p_181537_1_) {
/* 3237 */     this.field_181541_X = p_181537_1_;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\Minecraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
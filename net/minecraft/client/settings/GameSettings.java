/*      */ package net.minecraft.client.settings;
/*      */ import com.google.common.collect.ImmutableSet;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.google.gson.Gson;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FileWriter;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Type;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.audio.SoundCategory;
/*      */ import net.minecraft.client.gui.GuiNewChat;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.play.client.C15PacketClientSettings;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.World;
/*      */ import net.optifine.ClearWater;
/*      */ import net.optifine.CustomColors;
/*      */ import net.optifine.CustomGuis;
/*      */ import net.optifine.CustomSky;
/*      */ import net.optifine.DynamicLights;
/*      */ import net.optifine.Lang;
/*      */ import net.optifine.NaturalTextures;
/*      */ import net.optifine.RandomEntities;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import net.optifine.util.KeyUtils;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.apache.commons.lang3.ArrayUtils;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.DisplayMode;
/*      */ 
/*      */ public class GameSettings {
/*   58 */   private static final Logger logger = LogManager.getLogger();
/*   59 */   private static final Gson gson = new Gson();
/*   60 */   private static final ParameterizedType typeListString = new ParameterizedType()
/*      */     {
/*      */       public Type[] getActualTypeArguments()
/*      */       {
/*   64 */         return new Type[] { String.class };
/*      */       }
/*      */       
/*      */       public Type getRawType() {
/*   68 */         return List.class;
/*      */       }
/*      */       
/*      */       public Type getOwnerType() {
/*   72 */         return null;
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*   77 */   private static final String[] GUISCALES = new String[] { "options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large" };
/*   78 */   private static final String[] PARTICLES = new String[] { "options.particles.all", "options.particles.decreased", "options.particles.minimal" };
/*   79 */   private static final String[] AMBIENT_OCCLUSIONS = new String[] { "options.ao.off", "options.ao.min", "options.ao.max" };
/*   80 */   private static final String[] field_181149_aW = new String[] { "options.off", "options.graphics.fast", "options.graphics.fancy" };
/*   81 */   public float mouseSensitivity = 0.5F;
/*      */   public boolean invertMouse;
/*   83 */   public int renderDistanceChunks = -1;
/*      */   public boolean viewBobbing = true;
/*      */   public boolean anaglyph;
/*      */   public boolean fboEnable = true;
/*   87 */   public int limitFramerate = 120;
/*      */ 
/*      */   
/*   90 */   public int clouds = 2;
/*      */   
/*      */   public boolean fancyGraphics = true;
/*      */   
/*   94 */   public int ambientOcclusion = 2;
/*   95 */   public List<String> resourcePacks = Lists.newArrayList();
/*   96 */   public List<String> field_183018_l = Lists.newArrayList();
/*   97 */   public EntityPlayer.EnumChatVisibility chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
/*      */   public boolean chatColours = true;
/*      */   public boolean chatLinks = true;
/*      */   public boolean chatLinksPrompt = true;
/*  101 */   public float chatOpacity = 1.0F;
/*      */   
/*      */   public boolean snooperEnabled = true;
/*      */   
/*      */   public boolean fullScreen;
/*      */   
/*      */   public boolean enableVsync = true;
/*      */   
/*      */   public boolean useVbo = false;
/*      */   
/*      */   public boolean allowBlockAlternatives = true;
/*      */   
/*      */   public boolean reducedDebugInfo = false;
/*      */   public boolean hideServerAddress;
/*      */   public boolean advancedItemTooltips;
/*      */   public boolean pauseOnLostFocus = true;
/*  117 */   private final Set<EnumPlayerModelParts> setModelParts = Sets.newHashSet((Object[])EnumPlayerModelParts.values());
/*      */   public boolean touchscreen;
/*      */   public int overrideWidth;
/*      */   public int overrideHeight;
/*      */   public boolean heldItemTooltips = true;
/*  122 */   public float chatScale = 1.0F;
/*  123 */   public float chatWidth = 1.0F;
/*  124 */   public float chatHeightUnfocused = 0.44366196F;
/*  125 */   public float chatHeightFocused = 1.0F;
/*      */   public boolean showInventoryAchievementHint = true;
/*  127 */   public int mipmapLevels = 4;
/*  128 */   private Map<SoundCategory, Float> mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
/*      */   public boolean field_181150_U = true;
/*      */   public boolean field_181151_V = true;
/*      */   public boolean field_183509_X = true;
/*  132 */   public KeyBinding keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
/*  133 */   public KeyBinding keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
/*  134 */   public KeyBinding keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
/*  135 */   public KeyBinding keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
/*  136 */   public KeyBinding keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
/*  137 */   public KeyBinding keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
/*  138 */   public KeyBinding keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.movement");
/*  139 */   public KeyBinding keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
/*  140 */   public KeyBinding keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
/*  141 */   public KeyBinding keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
/*  142 */   public KeyBinding keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
/*  143 */   public KeyBinding keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
/*  144 */   public KeyBinding keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
/*  145 */   public KeyBinding keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
/*  146 */   public KeyBinding keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
/*  147 */   public KeyBinding keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
/*  148 */   public KeyBinding keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
/*  149 */   public KeyBinding keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
/*  150 */   public KeyBinding keyBindFullscreen = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
/*  151 */   public KeyBinding keyBindSpectatorOutlines = new KeyBinding("key.spectatorOutlines", 0, "key.categories.misc");
/*  152 */   public KeyBinding[] keyBindsHotbar = new KeyBinding[] { new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory") };
/*      */   
/*      */   public KeyBinding[] keyBindings;
/*      */   
/*      */   protected Minecraft mc;
/*      */   
/*      */   private File optionsFile;
/*      */   
/*      */   public EnumDifficulty difficulty;
/*      */   
/*      */   public boolean hideGUI;
/*      */   
/*      */   public int thirdPersonView;
/*      */   
/*      */   public boolean showDebugInfo;
/*      */   
/*      */   public boolean showDebugProfilerChart;
/*      */   
/*      */   public boolean field_181657_aC;
/*      */   
/*      */   public String lastServer;
/*      */   
/*      */   public boolean smoothCamera;
/*      */   
/*      */   public boolean debugCamEnable;
/*      */   public float fovSetting;
/*      */   public float gammaSetting;
/*      */   public float saturation;
/*      */   public int guiScale;
/*      */   public int particleSetting;
/*      */   public String language;
/*      */   public boolean forceUnicodeFont;
/*  184 */   public int ofFogType = 1;
/*  185 */   public float ofFogStart = 0.8F;
/*  186 */   public int ofMipmapType = 0;
/*      */   public boolean ofOcclusionFancy = false;
/*      */   public boolean ofSmoothFps = false;
/*  189 */   public boolean ofSmoothWorld = Config.isSingleProcessor();
/*  190 */   public boolean ofLazyChunkLoading = Config.isSingleProcessor();
/*      */   public boolean ofRenderRegions = false;
/*      */   public boolean ofSmartAnimations = false;
/*  193 */   public float ofAoLevel = 1.0F;
/*  194 */   public int ofAaLevel = 0;
/*  195 */   public int ofAfLevel = 1;
/*  196 */   public int ofClouds = 0;
/*  197 */   public float ofCloudsHeight = 0.0F;
/*  198 */   public int ofTrees = 0;
/*  199 */   public int ofRain = 0;
/*  200 */   public int ofDroppedItems = 0;
/*  201 */   public int ofBetterGrass = 3;
/*  202 */   public int ofAutoSaveTicks = 4000;
/*      */   public boolean ofLagometer = false;
/*      */   public boolean ofProfiler = false;
/*      */   public boolean ofShowFps = false;
/*      */   public boolean ofWeather = true;
/*      */   public boolean ofSky = true;
/*      */   public boolean ofStars = true;
/*      */   public boolean ofSunMoon = true;
/*  210 */   public int ofVignette = 0;
/*  211 */   public int ofChunkUpdates = 1;
/*      */   public boolean ofChunkUpdatesDynamic = false;
/*  213 */   public int ofTime = 0;
/*      */   public boolean ofClearWater = false;
/*      */   public boolean ofBetterSnow = false;
/*  216 */   public String ofFullscreenMode = "Default";
/*      */   public boolean ofSwampColors = true;
/*      */   public boolean ofRandomEntities = true;
/*      */   public boolean ofSmoothBiomes = true;
/*      */   public boolean ofCustomFonts = true;
/*      */   public boolean ofCustomColors = true;
/*      */   public boolean ofCustomSky = true;
/*      */   public boolean ofShowCapes = true;
/*  224 */   public int ofConnectedTextures = 2;
/*      */   public boolean ofCustomItems = true;
/*      */   public boolean ofNaturalTextures = false;
/*      */   public boolean ofEmissiveTextures = true;
/*      */   public boolean ofFastMath = false;
/*      */   public boolean ofFastRender = false;
/*  230 */   public int ofTranslucentBlocks = 0;
/*      */   public boolean ofDynamicFov = true;
/*      */   public boolean ofAlternateBlocks = true;
/*  233 */   public int ofDynamicLights = 3;
/*      */   public boolean ofCustomEntityModels = true;
/*      */   public boolean ofCustomGuis = true;
/*      */   public boolean ofShowGlErrors = false;
/*  237 */   public int ofScreenshotSize = 1;
/*  238 */   public int ofAnimatedWater = 0;
/*  239 */   public int ofAnimatedLava = 0;
/*      */   public boolean ofAnimatedFire = true;
/*      */   public boolean ofAnimatedPortal = true;
/*      */   public boolean ofAnimatedRedstone = true;
/*      */   public boolean ofAnimatedExplosion = true;
/*      */   public boolean ofAnimatedFlame = true;
/*      */   public boolean ofAnimatedSmoke = true;
/*      */   public boolean ofVoidParticles = true;
/*      */   public boolean ofWaterParticles = true;
/*      */   public boolean ofRainSplash = true;
/*      */   public boolean ofPortalParticles = true;
/*      */   public boolean ofPotionParticles = true;
/*      */   public boolean ofFireworkParticles = true;
/*      */   public boolean ofDrippingWaterLava = true;
/*      */   public boolean ofAnimatedTerrain = true;
/*      */   public boolean ofAnimatedTextures = true;
/*      */   public static final int DEFAULT = 0;
/*      */   public static final int FAST = 1;
/*      */   public static final int FANCY = 2;
/*      */   public static final int OFF = 3;
/*      */   public static final int SMART = 4;
/*      */   public static final int ANIM_ON = 0;
/*      */   public static final int ANIM_GENERATED = 1;
/*      */   public static final int ANIM_OFF = 2;
/*      */   public static final String DEFAULT_STR = "Default";
/*  264 */   private static final int[] OF_TREES_VALUES = new int[] { 0, 1, 4, 2 };
/*  265 */   private static final int[] OF_DYNAMIC_LIGHTS = new int[] { 3, 1, 2 };
/*  266 */   private static final String[] KEYS_DYNAMIC_LIGHTS = new String[] { "options.off", "options.graphics.fast", "options.graphics.fancy" };
/*      */   
/*      */   public KeyBinding ofKeyBindZoom;
/*      */   private File optionsFileOF;
/*      */   
/*      */   public GameSettings(Minecraft mcIn, File p_i46326_2_) {
/*  272 */     this.keyBindings = (KeyBinding[])ArrayUtils.addAll((Object[])new KeyBinding[] { this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindSprint, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindFullscreen, this.keyBindSpectatorOutlines }, (Object[])this.keyBindsHotbar);
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
/*  293 */     this.difficulty = EnumDifficulty.NORMAL;
/*  294 */     this.lastServer = "";
/*  295 */     this.fovSetting = 70.0F;
/*  296 */     this.language = "en_US";
/*  297 */     this.forceUnicodeFont = false;
/*  298 */     this.mc = mcIn;
/*  299 */     this.optionsFile = new File(p_i46326_2_, "options.txt");
/*      */     
/*  301 */     if (mcIn.isJava64bit() && Runtime.getRuntime().maxMemory() >= 1000000000L) {
/*      */       
/*  303 */       Options.RENDER_DISTANCE.setValueMax(32.0F);
/*  304 */       long i = 1000000L;
/*      */       
/*  306 */       if (Runtime.getRuntime().maxMemory() >= 1500L * i)
/*      */       {
/*  308 */         Options.RENDER_DISTANCE.setValueMax(48.0F);
/*      */       }
/*      */       
/*  311 */       if (Runtime.getRuntime().maxMemory() >= 2500L * i)
/*      */       {
/*  313 */         Options.RENDER_DISTANCE.setValueMax(64.0F);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  318 */       Options.RENDER_DISTANCE.setValueMax(16.0F);
/*      */     } 
/*      */     
/*  321 */     this.renderDistanceChunks = mcIn.isJava64bit() ? 12 : 8;
/*  322 */     this.optionsFileOF = new File(p_i46326_2_, "optionsof.txt");
/*  323 */     this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
/*  324 */     this.ofKeyBindZoom = new KeyBinding("of.key.zoom", 46, "key.categories.misc");
/*  325 */     this.keyBindings = (KeyBinding[])ArrayUtils.add((Object[])this.keyBindings, this.ofKeyBindZoom);
/*  326 */     KeyUtils.fixKeyConflicts(this.keyBindings, new KeyBinding[] { this.ofKeyBindZoom });
/*  327 */     this.renderDistanceChunks = 8;
/*  328 */     loadOptions();
/*  329 */     Config.initGameSettings(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public GameSettings() {
/*  334 */     this.keyBindings = (KeyBinding[])ArrayUtils.addAll((Object[])new KeyBinding[] { this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindSprint, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindFullscreen, this.keyBindSpectatorOutlines }, (Object[])this.keyBindsHotbar);
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
/*  355 */     this.difficulty = EnumDifficulty.NORMAL;
/*  356 */     this.lastServer = "";
/*  357 */     this.fovSetting = 70.0F;
/*  358 */     this.language = "en_US";
/*  359 */     this.forceUnicodeFont = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getKeyDisplayString(int p_74298_0_) {
/*  367 */     return (p_74298_0_ < 0) ? I18n.format("key.mouseButton", new Object[] { Integer.valueOf(p_74298_0_ + 101) }) : ((p_74298_0_ < 256) ? Keyboard.getKeyName(p_74298_0_) : String.format("%c", new Object[] { Character.valueOf((char)(p_74298_0_ - 256)) }).toUpperCase());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isKeyDown(KeyBinding p_100015_0_) {
/*  375 */     return (p_100015_0_.getKeyCode() == 0) ? false : ((p_100015_0_.getKeyCode() < 0) ? Mouse.isButtonDown(p_100015_0_.getKeyCode() + 100) : Keyboard.isKeyDown(p_100015_0_.getKeyCode()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOptionKeyBinding(KeyBinding p_151440_1_, int p_151440_2_) {
/*  383 */     p_151440_1_.setKeyCode(p_151440_2_);
/*  384 */     saveOptions();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOptionFloatValue(Options p_74304_1_, float p_74304_2_) {
/*  392 */     setOptionFloatValueOF(p_74304_1_, p_74304_2_);
/*      */     
/*  394 */     if (p_74304_1_ == Options.SENSITIVITY)
/*      */     {
/*  396 */       this.mouseSensitivity = p_74304_2_;
/*      */     }
/*      */     
/*  399 */     if (p_74304_1_ == Options.FOV)
/*      */     {
/*  401 */       this.fovSetting = p_74304_2_;
/*      */     }
/*      */     
/*  404 */     if (p_74304_1_ == Options.GAMMA)
/*      */     {
/*  406 */       this.gammaSetting = p_74304_2_;
/*      */     }
/*      */     
/*  409 */     if (p_74304_1_ == Options.FRAMERATE_LIMIT) {
/*      */       
/*  411 */       this.limitFramerate = (int)p_74304_2_;
/*  412 */       this.enableVsync = false;
/*      */       
/*  414 */       if (this.limitFramerate <= 0) {
/*      */         
/*  416 */         this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
/*  417 */         this.enableVsync = true;
/*      */       } 
/*      */       
/*  420 */       updateVSync();
/*      */     } 
/*      */     
/*  423 */     if (p_74304_1_ == Options.CHAT_OPACITY) {
/*      */       
/*  425 */       this.chatOpacity = p_74304_2_;
/*  426 */       this.mc.ingameGUI.getChatGUI().refreshChat();
/*      */     } 
/*      */     
/*  429 */     if (p_74304_1_ == Options.CHAT_HEIGHT_FOCUSED) {
/*      */       
/*  431 */       this.chatHeightFocused = p_74304_2_;
/*  432 */       this.mc.ingameGUI.getChatGUI().refreshChat();
/*      */     } 
/*      */     
/*  435 */     if (p_74304_1_ == Options.CHAT_HEIGHT_UNFOCUSED) {
/*      */       
/*  437 */       this.chatHeightUnfocused = p_74304_2_;
/*  438 */       this.mc.ingameGUI.getChatGUI().refreshChat();
/*      */     } 
/*      */     
/*  441 */     if (p_74304_1_ == Options.CHAT_WIDTH) {
/*      */       
/*  443 */       this.chatWidth = p_74304_2_;
/*  444 */       this.mc.ingameGUI.getChatGUI().refreshChat();
/*      */     } 
/*      */     
/*  447 */     if (p_74304_1_ == Options.CHAT_SCALE) {
/*      */       
/*  449 */       this.chatScale = p_74304_2_;
/*  450 */       this.mc.ingameGUI.getChatGUI().refreshChat();
/*      */     } 
/*      */     
/*  453 */     if (p_74304_1_ == Options.MIPMAP_LEVELS) {
/*      */       
/*  455 */       int i = this.mipmapLevels;
/*  456 */       this.mipmapLevels = (int)p_74304_2_;
/*      */       
/*  458 */       if (i != p_74304_2_) {
/*      */         
/*  460 */         this.mc.getTextureMapBlocks().setMipmapLevels(this.mipmapLevels);
/*  461 */         this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/*  462 */         this.mc.getTextureMapBlocks().setBlurMipmapDirect(false, (this.mipmapLevels > 0));
/*  463 */         this.mc.scheduleResourcesRefresh();
/*      */       } 
/*      */     } 
/*      */     
/*  467 */     if (p_74304_1_ == Options.BLOCK_ALTERNATIVES) {
/*      */       
/*  469 */       this.allowBlockAlternatives = !this.allowBlockAlternatives;
/*  470 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/*  473 */     if (p_74304_1_ == Options.RENDER_DISTANCE) {
/*      */       
/*  475 */       this.renderDistanceChunks = (int)p_74304_2_;
/*  476 */       this.mc.renderGlobal.setDisplayListEntitiesDirty();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOptionValue(Options p_74306_1_, int p_74306_2_) {
/*  485 */     setOptionValueOF(p_74306_1_, p_74306_2_);
/*      */     
/*  487 */     if (p_74306_1_ == Options.INVERT_MOUSE)
/*      */     {
/*  489 */       this.invertMouse = !this.invertMouse;
/*      */     }
/*      */     
/*  492 */     if (p_74306_1_ == Options.GUI_SCALE) {
/*      */       
/*  494 */       this.guiScale += p_74306_2_;
/*      */       
/*  496 */       if (GuiScreen.isShiftKeyDown())
/*      */       {
/*  498 */         this.guiScale = 0;
/*      */       }
/*      */       
/*  501 */       DisplayMode displaymode = Config.getLargestDisplayMode();
/*  502 */       int i = displaymode.getWidth() / 320;
/*  503 */       int j = displaymode.getHeight() / 240;
/*  504 */       int k = Math.min(i, j);
/*      */       
/*  506 */       if (this.guiScale < 0)
/*      */       {
/*  508 */         this.guiScale = k - 1;
/*      */       }
/*      */       
/*  511 */       if (this.mc.isUnicode() && this.guiScale % 2 != 0)
/*      */       {
/*  513 */         this.guiScale += p_74306_2_;
/*      */       }
/*      */       
/*  516 */       if (this.guiScale < 0 || this.guiScale >= k)
/*      */       {
/*  518 */         this.guiScale = 0;
/*      */       }
/*      */     } 
/*      */     
/*  522 */     if (p_74306_1_ == Options.PARTICLES)
/*      */     {
/*  524 */       this.particleSetting = (this.particleSetting + p_74306_2_) % 3;
/*      */     }
/*      */     
/*  527 */     if (p_74306_1_ == Options.VIEW_BOBBING)
/*      */     {
/*  529 */       this.viewBobbing = !this.viewBobbing;
/*      */     }
/*      */     
/*  532 */     if (p_74306_1_ == Options.RENDER_CLOUDS)
/*      */     {
/*  534 */       this.clouds = (this.clouds + p_74306_2_) % 3;
/*      */     }
/*      */     
/*  537 */     if (p_74306_1_ == Options.FORCE_UNICODE_FONT) {
/*      */       
/*  539 */       this.forceUnicodeFont = !this.forceUnicodeFont;
/*  540 */       this.mc.MCfontRenderer.setUnicodeFlag((this.mc.getLanguageManager().isCurrentLocaleUnicode() || this.forceUnicodeFont));
/*      */     } 
/*      */     
/*  543 */     if (p_74306_1_ == Options.FBO_ENABLE)
/*      */     {
/*  545 */       this.fboEnable = !this.fboEnable;
/*      */     }
/*      */     
/*  548 */     if (p_74306_1_ == Options.ANAGLYPH) {
/*      */       
/*  550 */       if (!this.anaglyph && Config.isShaders()) {
/*      */         
/*  552 */         Config.showGuiMessage(Lang.get("of.message.an.shaders1"), Lang.get("of.message.an.shaders2"));
/*      */         
/*      */         return;
/*      */       } 
/*  556 */       this.anaglyph = !this.anaglyph;
/*  557 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/*  560 */     if (p_74306_1_ == Options.GRAPHICS) {
/*      */       
/*  562 */       this.fancyGraphics = !this.fancyGraphics;
/*  563 */       updateRenderClouds();
/*  564 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/*  567 */     if (p_74306_1_ == Options.AMBIENT_OCCLUSION) {
/*      */       
/*  569 */       this.ambientOcclusion = (this.ambientOcclusion + p_74306_2_) % 3;
/*  570 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/*  573 */     if (p_74306_1_ == Options.CHAT_VISIBILITY)
/*      */     {
/*  575 */       this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility((this.chatVisibility.getChatVisibility() + p_74306_2_) % 3);
/*      */     }
/*      */     
/*  578 */     if (p_74306_1_ == Options.CHAT_COLOR)
/*      */     {
/*  580 */       this.chatColours = !this.chatColours;
/*      */     }
/*      */     
/*  583 */     if (p_74306_1_ == Options.CHAT_LINKS)
/*      */     {
/*  585 */       this.chatLinks = !this.chatLinks;
/*      */     }
/*      */     
/*  588 */     if (p_74306_1_ == Options.CHAT_LINKS_PROMPT)
/*      */     {
/*  590 */       this.chatLinksPrompt = !this.chatLinksPrompt;
/*      */     }
/*      */     
/*  593 */     if (p_74306_1_ == Options.SNOOPER_ENABLED)
/*      */     {
/*  595 */       this.snooperEnabled = !this.snooperEnabled;
/*      */     }
/*      */     
/*  598 */     if (p_74306_1_ == Options.TOUCHSCREEN)
/*      */     {
/*  600 */       this.touchscreen = !this.touchscreen;
/*      */     }
/*      */     
/*  603 */     if (p_74306_1_ == Options.USE_FULLSCREEN) {
/*      */       
/*  605 */       this.fullScreen = !this.fullScreen;
/*      */       
/*  607 */       if (this.mc.isFullScreen() != this.fullScreen)
/*      */       {
/*  609 */         this.mc.toggleFullscreen();
/*      */       }
/*      */     } 
/*      */     
/*  613 */     if (p_74306_1_ == Options.ENABLE_VSYNC) {
/*      */       
/*  615 */       this.enableVsync = !this.enableVsync;
/*  616 */       Display.setVSyncEnabled(this.enableVsync);
/*      */     } 
/*      */     
/*  619 */     if (p_74306_1_ == Options.USE_VBO) {
/*      */       
/*  621 */       this.useVbo = !this.useVbo;
/*  622 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/*  625 */     if (p_74306_1_ == Options.BLOCK_ALTERNATIVES) {
/*      */       
/*  627 */       this.allowBlockAlternatives = !this.allowBlockAlternatives;
/*  628 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/*  631 */     if (p_74306_1_ == Options.REDUCED_DEBUG_INFO)
/*      */     {
/*  633 */       this.reducedDebugInfo = !this.reducedDebugInfo;
/*      */     }
/*      */     
/*  636 */     if (p_74306_1_ == Options.ENTITY_SHADOWS)
/*      */     {
/*  638 */       this.field_181151_V = !this.field_181151_V;
/*      */     }
/*      */     
/*  641 */     saveOptions();
/*      */   }
/*      */ 
/*      */   
/*      */   public float getOptionFloatValue(Options p_74296_1_) {
/*  646 */     float f = getOptionFloatValueOF(p_74296_1_);
/*  647 */     return (f != Float.MAX_VALUE) ? f : ((p_74296_1_ == Options.FOV) ? this.fovSetting : ((p_74296_1_ == Options.GAMMA) ? this.gammaSetting : ((p_74296_1_ == Options.SATURATION) ? this.saturation : ((p_74296_1_ == Options.SENSITIVITY) ? this.mouseSensitivity : ((p_74296_1_ == Options.CHAT_OPACITY) ? this.chatOpacity : ((p_74296_1_ == Options.CHAT_HEIGHT_FOCUSED) ? this.chatHeightFocused : ((p_74296_1_ == Options.CHAT_HEIGHT_UNFOCUSED) ? this.chatHeightUnfocused : ((p_74296_1_ == Options.CHAT_SCALE) ? this.chatScale : ((p_74296_1_ == Options.CHAT_WIDTH) ? this.chatWidth : ((p_74296_1_ == Options.FRAMERATE_LIMIT) ? this.limitFramerate : ((p_74296_1_ == Options.MIPMAP_LEVELS) ? this.mipmapLevels : ((p_74296_1_ == Options.RENDER_DISTANCE) ? this.renderDistanceChunks : 0.0F))))))))))));
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
/*      */   public boolean getOptionOrdinalValue(Options p_74308_1_) {
/*  664 */     switch (p_74308_1_) {
/*      */       
/*      */       case INVERT_MOUSE:
/*  667 */         return this.invertMouse;
/*      */       
/*      */       case VIEW_BOBBING:
/*  670 */         return this.viewBobbing;
/*      */       
/*      */       case ANAGLYPH:
/*  673 */         return this.anaglyph;
/*      */       
/*      */       case FBO_ENABLE:
/*  676 */         return this.fboEnable;
/*      */       
/*      */       case CHAT_COLOR:
/*  679 */         return this.chatColours;
/*      */       
/*      */       case CHAT_LINKS:
/*  682 */         return this.chatLinks;
/*      */       
/*      */       case CHAT_LINKS_PROMPT:
/*  685 */         return this.chatLinksPrompt;
/*      */       
/*      */       case SNOOPER_ENABLED:
/*  688 */         return this.snooperEnabled;
/*      */       
/*      */       case USE_FULLSCREEN:
/*  691 */         return this.fullScreen;
/*      */       
/*      */       case ENABLE_VSYNC:
/*  694 */         return this.enableVsync;
/*      */       
/*      */       case USE_VBO:
/*  697 */         return this.useVbo;
/*      */       
/*      */       case TOUCHSCREEN:
/*  700 */         return this.touchscreen;
/*      */       
/*      */       case FORCE_UNICODE_FONT:
/*  703 */         return this.forceUnicodeFont;
/*      */       
/*      */       case BLOCK_ALTERNATIVES:
/*  706 */         return this.allowBlockAlternatives;
/*      */       
/*      */       case REDUCED_DEBUG_INFO:
/*  709 */         return this.reducedDebugInfo;
/*      */       
/*      */       case ENTITY_SHADOWS:
/*  712 */         return this.field_181151_V;
/*      */     } 
/*      */     
/*  715 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getTranslation(String[] p_74299_0_, int p_74299_1_) {
/*  725 */     if (p_74299_1_ < 0 || p_74299_1_ >= p_74299_0_.length)
/*      */     {
/*  727 */       p_74299_1_ = 0;
/*      */     }
/*      */     
/*  730 */     return I18n.format(p_74299_0_[p_74299_1_], new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKeyBinding(Options p_74297_1_) {
/*  738 */     String s = getKeyBindingOF(p_74297_1_);
/*      */     
/*  740 */     if (s != null)
/*      */     {
/*  742 */       return s;
/*      */     }
/*      */ 
/*      */     
/*  746 */     String s1 = I18n.format(p_74297_1_.getEnumString(), new Object[0]) + ": ";
/*      */     
/*  748 */     if (p_74297_1_.getEnumFloat()) {
/*      */       
/*  750 */       float f1 = getOptionFloatValue(p_74297_1_);
/*  751 */       float f = p_74297_1_.normalizeValue(f1);
/*  752 */       return (p_74297_1_ == Options.MIPMAP_LEVELS && f1 >= 4.0D) ? (s1 + Lang.get("of.general.max")) : ((p_74297_1_ == Options.SENSITIVITY) ? ((f == 0.0F) ? (s1 + 
/*  753 */         I18n.format("options.sensitivity.min", new Object[0])) : ((f == 1.0F) ? (s1 + I18n.format("options.sensitivity.max", new Object[0])) : (s1 + (int)(f * 200.0F) + "%"))) : ((p_74297_1_ == Options.FOV) ? ((f1 == 70.0F) ? (s1 + 
/*  754 */         I18n.format("options.fov.min", new Object[0])) : ((f1 == 110.0F) ? (s1 + I18n.format("options.fov.max", new Object[0])) : (s1 + (int)f1))) : ((p_74297_1_ == Options.FRAMERATE_LIMIT) ? (
/*  755 */         (f1 == p_74297_1_.valueMax) ? (s1 + I18n.format("options.framerateLimit.max", new Object[0])) : (s1 + (int)f1 + " fps")) : ((p_74297_1_ == Options.RENDER_CLOUDS) ? (
/*  756 */         (f1 == p_74297_1_.valueMin) ? (s1 + I18n.format("options.cloudHeight.min", new Object[0])) : (s1 + ((int)f1 + 128))) : ((p_74297_1_ == Options.GAMMA) ? ((f == 0.0F) ? (s1 + 
/*  757 */         I18n.format("options.gamma.min", new Object[0])) : ((f == 1.0F) ? (s1 + I18n.format("options.gamma.max", new Object[0])) : (s1 + "+" + (int)(f * 100.0F) + "%"))) : ((p_74297_1_ == Options.SATURATION) ? (s1 + (int)(f * 400.0F) + "%") : ((p_74297_1_ == Options.CHAT_OPACITY) ? (s1 + (int)(f * 90.0F + 10.0F) + "%") : ((p_74297_1_ == Options.CHAT_HEIGHT_UNFOCUSED) ? (s1 + 
/*      */         
/*  759 */         GuiNewChat.calculateChatboxHeight(f) + "px") : ((p_74297_1_ == Options.CHAT_HEIGHT_FOCUSED) ? (s1 + 
/*  760 */         GuiNewChat.calculateChatboxHeight(f) + "px") : ((p_74297_1_ == Options.CHAT_WIDTH) ? (s1 + 
/*  761 */         GuiNewChat.calculateChatboxWidth(f) + "px") : ((p_74297_1_ == Options.RENDER_DISTANCE) ? (s1 + (int)f1 + " chunks") : ((p_74297_1_ == Options.MIPMAP_LEVELS) ? ((f1 == 0.0F) ? (s1 + 
/*      */         
/*  763 */         I18n.format("options.off", new Object[0])) : (s1 + (int)f1)) : (s1 + (int)(f * 100.0F) + "%")))))))))))));
/*      */     } 
/*  765 */     if (p_74297_1_.getEnumBoolean()) {
/*      */       
/*  767 */       boolean flag = getOptionOrdinalValue(p_74297_1_);
/*  768 */       return flag ? (s1 + I18n.format("options.on", new Object[0])) : (s1 + I18n.format("options.off", new Object[0]));
/*      */     } 
/*  770 */     if (p_74297_1_ == Options.GUI_SCALE)
/*      */     {
/*  772 */       return (this.guiScale >= GUISCALES.length) ? (s1 + this.guiScale + "x") : (s1 + getTranslation(GUISCALES, this.guiScale));
/*      */     }
/*  774 */     if (p_74297_1_ == Options.CHAT_VISIBILITY)
/*      */     {
/*  776 */       return s1 + I18n.format(this.chatVisibility.getResourceKey(), new Object[0]);
/*      */     }
/*  778 */     if (p_74297_1_ == Options.PARTICLES)
/*      */     {
/*  780 */       return s1 + getTranslation(PARTICLES, this.particleSetting);
/*      */     }
/*  782 */     if (p_74297_1_ == Options.AMBIENT_OCCLUSION)
/*      */     {
/*  784 */       return s1 + getTranslation(AMBIENT_OCCLUSIONS, this.ambientOcclusion);
/*      */     }
/*  786 */     if (p_74297_1_ == Options.RENDER_CLOUDS)
/*      */     {
/*  788 */       return s1 + getTranslation(field_181149_aW, this.clouds);
/*      */     }
/*  790 */     if (p_74297_1_ == Options.GRAPHICS) {
/*      */       
/*  792 */       if (this.fancyGraphics)
/*      */       {
/*  794 */         return s1 + I18n.format("options.graphics.fancy", new Object[0]);
/*      */       }
/*      */ 
/*      */       
/*  798 */       String s2 = "options.graphics.fast";
/*  799 */       return s1 + I18n.format("options.graphics.fast", new Object[0]);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  804 */     return s1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadOptions() {
/*  814 */     FileInputStream fileinputstream = null;
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
/*      */     try {
/*      */     
/* 1157 */     } catch (Exception exception1) {
/*      */       
/* 1159 */       logger.error("Failed to load options", exception1);
/*      */     
/*      */     }
/*      */     finally {
/*      */       
/* 1164 */       IOUtils.closeQuietly(fileinputstream);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1169 */     loadOfOptions();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float parseFloat(String p_74305_1_) {
/* 1177 */     return p_74305_1_.equals("true") ? 1.0F : (p_74305_1_.equals("false") ? 0.0F : Float.parseFloat(p_74305_1_));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveOptions() {
/* 1185 */     if (Reflector.FMLClientHandler.exists()) {
/*      */       
/* 1187 */       Object object = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
/*      */       
/* 1189 */       if (object != null && Reflector.callBoolean(object, Reflector.FMLClientHandler_isLoading, new Object[0])) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1197 */       PrintWriter printwriter = new PrintWriter(new FileWriter(this.optionsFile));
/* 1198 */       printwriter.println("invertYMouse:" + this.invertMouse);
/* 1199 */       printwriter.println("mouseSensitivity:" + this.mouseSensitivity);
/* 1200 */       printwriter.println("fov:" + ((this.fovSetting - 70.0F) / 40.0F));
/* 1201 */       printwriter.println("gamma:" + this.gammaSetting);
/* 1202 */       printwriter.println("saturation:" + this.saturation);
/* 1203 */       printwriter.println("renderDistance:" + this.renderDistanceChunks);
/* 1204 */       printwriter.println("guiScale:" + this.guiScale);
/* 1205 */       printwriter.println("particles:" + this.particleSetting);
/* 1206 */       printwriter.println("bobView:" + this.viewBobbing);
/* 1207 */       printwriter.println("anaglyph3d:" + this.anaglyph);
/* 1208 */       printwriter.println("maxFps:" + this.limitFramerate);
/* 1209 */       printwriter.println("fboEnable:" + this.fboEnable);
/* 1210 */       printwriter.println("difficulty:" + this.difficulty.getDifficultyId());
/* 1211 */       printwriter.println("fancyGraphics:" + this.fancyGraphics);
/* 1212 */       printwriter.println("ao:" + this.ambientOcclusion);
/*      */       
/* 1214 */       switch (this.clouds) {
/*      */         
/*      */         case 0:
/* 1217 */           printwriter.println("renderClouds:false");
/*      */           break;
/*      */         
/*      */         case 1:
/* 1221 */           printwriter.println("renderClouds:fast");
/*      */           break;
/*      */         
/*      */         case 2:
/* 1225 */           printwriter.println("renderClouds:true");
/*      */           break;
/*      */       } 
/* 1228 */       printwriter.println("resourcePacks:" + gson.toJson(this.resourcePacks));
/* 1229 */       printwriter.println("incompatibleResourcePacks:" + gson.toJson(this.field_183018_l));
/* 1230 */       printwriter.println("lastServer:" + this.lastServer);
/* 1231 */       printwriter.println("lang:" + this.language);
/* 1232 */       printwriter.println("chatVisibility:" + this.chatVisibility.getChatVisibility());
/* 1233 */       printwriter.println("chatColors:" + this.chatColours);
/* 1234 */       printwriter.println("chatLinks:" + this.chatLinks);
/* 1235 */       printwriter.println("chatLinksPrompt:" + this.chatLinksPrompt);
/* 1236 */       printwriter.println("chatOpacity:" + this.chatOpacity);
/* 1237 */       printwriter.println("snooperEnabled:" + this.snooperEnabled);
/* 1238 */       printwriter.println("fullscreen:" + this.fullScreen);
/* 1239 */       printwriter.println("enableVsync:" + this.enableVsync);
/* 1240 */       printwriter.println("useVbo:" + this.useVbo);
/* 1241 */       printwriter.println("hideServerAddress:" + this.hideServerAddress);
/* 1242 */       printwriter.println("advancedItemTooltips:" + this.advancedItemTooltips);
/* 1243 */       printwriter.println("pauseOnLostFocus:" + this.pauseOnLostFocus);
/* 1244 */       printwriter.println("touchscreen:" + this.touchscreen);
/* 1245 */       printwriter.println("overrideWidth:" + this.overrideWidth);
/* 1246 */       printwriter.println("overrideHeight:" + this.overrideHeight);
/* 1247 */       printwriter.println("heldItemTooltips:" + this.heldItemTooltips);
/* 1248 */       printwriter.println("chatHeightFocused:" + this.chatHeightFocused);
/* 1249 */       printwriter.println("chatHeightUnfocused:" + this.chatHeightUnfocused);
/* 1250 */       printwriter.println("chatScale:" + this.chatScale);
/* 1251 */       printwriter.println("chatWidth:" + this.chatWidth);
/* 1252 */       printwriter.println("showInventoryAchievementHint:" + this.showInventoryAchievementHint);
/* 1253 */       printwriter.println("mipmapLevels:" + this.mipmapLevels);
/* 1254 */       printwriter.println("forceUnicodeFont:" + this.forceUnicodeFont);
/* 1255 */       printwriter.println("allowBlockAlternatives:" + this.allowBlockAlternatives);
/* 1256 */       printwriter.println("reducedDebugInfo:" + this.reducedDebugInfo);
/* 1257 */       printwriter.println("useNativeTransport:" + this.field_181150_U);
/* 1258 */       printwriter.println("entityShadows:" + this.field_181151_V);
/* 1259 */       printwriter.println("realmsNotifications:" + this.field_183509_X);
/*      */       
/* 1261 */       for (KeyBinding keybinding : this.keyBindings)
/*      */       {
/* 1263 */         printwriter.println("key_" + keybinding.getKeyDescription() + ":" + keybinding.getKeyCode());
/*      */       }
/*      */       
/* 1266 */       for (SoundCategory soundcategory : SoundCategory.values())
/*      */       {
/* 1268 */         printwriter.println("soundCategory_" + soundcategory.getCategoryName() + ":" + getSoundLevel(soundcategory));
/*      */       }
/*      */       
/* 1271 */       for (EnumPlayerModelParts enumplayermodelparts : EnumPlayerModelParts.values())
/*      */       {
/* 1273 */         printwriter.println("modelPart_" + enumplayermodelparts.getPartName() + ":" + this.setModelParts.contains(enumplayermodelparts));
/*      */       }
/*      */       
/* 1276 */       printwriter.close();
/*      */     }
/* 1278 */     catch (Exception exception) {
/*      */       
/* 1280 */       logger.error("Failed to save options", exception);
/*      */     } 
/*      */     
/* 1283 */     saveOfOptions();
/* 1284 */     sendSettingsToServer();
/*      */   }
/*      */ 
/*      */   
/*      */   public float getSoundLevel(SoundCategory p_151438_1_) {
/* 1289 */     return this.mapSoundLevels.containsKey(p_151438_1_) ? ((Float)this.mapSoundLevels.get(p_151438_1_)).floatValue() : 1.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSoundLevel(SoundCategory p_151439_1_, float p_151439_2_) {
/* 1294 */     this.mc.getSoundHandler().setSoundLevel(p_151439_1_, p_151439_2_);
/* 1295 */     this.mapSoundLevels.put(p_151439_1_, Float.valueOf(p_151439_2_));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendSettingsToServer() {
/* 1303 */     if (this.mc.thePlayer != null) {
/*      */       
/* 1305 */       int i = 0;
/*      */       
/* 1307 */       for (EnumPlayerModelParts enumplayermodelparts : this.setModelParts)
/*      */       {
/* 1309 */         i |= enumplayermodelparts.getPartMask();
/*      */       }
/*      */       
/* 1312 */       this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C15PacketClientSettings(this.language, this.renderDistanceChunks, this.chatVisibility, this.chatColours, i));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Set<EnumPlayerModelParts> getModelParts() {
/* 1318 */     return (Set<EnumPlayerModelParts>)ImmutableSet.copyOf(this.setModelParts);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setModelPartEnabled(EnumPlayerModelParts p_178878_1_, boolean p_178878_2_) {
/* 1323 */     if (p_178878_2_) {
/*      */       
/* 1325 */       this.setModelParts.add(p_178878_1_);
/*      */     }
/*      */     else {
/*      */       
/* 1329 */       this.setModelParts.remove(p_178878_1_);
/*      */     } 
/*      */     
/* 1332 */     sendSettingsToServer();
/*      */   }
/*      */ 
/*      */   
/*      */   public void switchModelPartEnabled(EnumPlayerModelParts p_178877_1_) {
/* 1337 */     if (!getModelParts().contains(p_178877_1_)) {
/*      */       
/* 1339 */       this.setModelParts.add(p_178877_1_);
/*      */     }
/*      */     else {
/*      */       
/* 1343 */       this.setModelParts.remove(p_178877_1_);
/*      */     } 
/*      */     
/* 1346 */     sendSettingsToServer();
/*      */   }
/*      */ 
/*      */   
/*      */   public int func_181147_e() {
/* 1351 */     return (this.renderDistanceChunks >= 4) ? this.clouds : 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_181148_f() {
/* 1356 */     return this.field_181150_U;
/*      */   }
/*      */ 
/*      */   
/*      */   private void setOptionFloatValueOF(Options p_setOptionFloatValueOF_1_, float p_setOptionFloatValueOF_2_) {
/* 1361 */     if (p_setOptionFloatValueOF_1_ == Options.CLOUD_HEIGHT) {
/*      */       
/* 1363 */       this.ofCloudsHeight = p_setOptionFloatValueOF_2_;
/* 1364 */       this.mc.renderGlobal.resetClouds();
/*      */     } 
/*      */     
/* 1367 */     if (p_setOptionFloatValueOF_1_ == Options.AO_LEVEL) {
/*      */       
/* 1369 */       this.ofAoLevel = p_setOptionFloatValueOF_2_;
/* 1370 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1373 */     if (p_setOptionFloatValueOF_1_ == Options.AA_LEVEL) {
/*      */       
/* 1375 */       int i = (int)p_setOptionFloatValueOF_2_;
/*      */       
/* 1377 */       if (i > 0 && Config.isShaders()) {
/*      */         
/* 1379 */         Config.showGuiMessage(Lang.get("of.message.aa.shaders1"), Lang.get("of.message.aa.shaders2"));
/*      */         
/*      */         return;
/*      */       } 
/* 1383 */       int[] aint = { 0, 2, 4, 6, 8, 12, 16 };
/* 1384 */       this.ofAaLevel = 0;
/*      */       
/* 1386 */       for (int j = 0; j < aint.length; j++) {
/*      */         
/* 1388 */         if (i >= aint[j])
/*      */         {
/* 1390 */           this.ofAaLevel = aint[j];
/*      */         }
/*      */       } 
/*      */       
/* 1394 */       this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
/*      */     } 
/*      */     
/* 1397 */     if (p_setOptionFloatValueOF_1_ == Options.AF_LEVEL) {
/*      */       
/* 1399 */       int k = (int)p_setOptionFloatValueOF_2_;
/*      */       
/* 1401 */       if (k > 1 && Config.isShaders()) {
/*      */         
/* 1403 */         Config.showGuiMessage(Lang.get("of.message.af.shaders1"), Lang.get("of.message.af.shaders2"));
/*      */         
/*      */         return;
/*      */       } 
/* 1407 */       for (this.ofAfLevel = 1; this.ofAfLevel * 2 <= k; this.ofAfLevel *= 2);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1412 */       this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
/* 1413 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/* 1416 */     if (p_setOptionFloatValueOF_1_ == Options.MIPMAP_TYPE) {
/*      */       
/* 1418 */       int l = (int)p_setOptionFloatValueOF_2_;
/* 1419 */       this.ofMipmapType = Config.limit(l, 0, 3);
/* 1420 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/* 1423 */     if (p_setOptionFloatValueOF_1_ == Options.FULLSCREEN_MODE) {
/*      */       
/* 1425 */       int i1 = (int)p_setOptionFloatValueOF_2_ - 1;
/* 1426 */       String[] astring = Config.getDisplayModeNames();
/*      */       
/* 1428 */       if (i1 < 0 || i1 >= astring.length) {
/*      */         
/* 1430 */         this.ofFullscreenMode = "Default";
/*      */         
/*      */         return;
/*      */       } 
/* 1434 */       this.ofFullscreenMode = astring[i1];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private float getOptionFloatValueOF(Options p_getOptionFloatValueOF_1_) {
/* 1440 */     if (p_getOptionFloatValueOF_1_ == Options.CLOUD_HEIGHT)
/*      */     {
/* 1442 */       return this.ofCloudsHeight;
/*      */     }
/* 1444 */     if (p_getOptionFloatValueOF_1_ == Options.AO_LEVEL)
/*      */     {
/* 1446 */       return this.ofAoLevel;
/*      */     }
/* 1448 */     if (p_getOptionFloatValueOF_1_ == Options.AA_LEVEL)
/*      */     {
/* 1450 */       return this.ofAaLevel;
/*      */     }
/* 1452 */     if (p_getOptionFloatValueOF_1_ == Options.AF_LEVEL)
/*      */     {
/* 1454 */       return this.ofAfLevel;
/*      */     }
/* 1456 */     if (p_getOptionFloatValueOF_1_ == Options.MIPMAP_TYPE)
/*      */     {
/* 1458 */       return this.ofMipmapType;
/*      */     }
/* 1460 */     if (p_getOptionFloatValueOF_1_ == Options.FRAMERATE_LIMIT)
/*      */     {
/* 1462 */       return (this.limitFramerate == Options.FRAMERATE_LIMIT.getValueMax() && this.enableVsync) ? 0.0F : this.limitFramerate;
/*      */     }
/* 1464 */     if (p_getOptionFloatValueOF_1_ == Options.FULLSCREEN_MODE) {
/*      */       
/* 1466 */       if (this.ofFullscreenMode.equals("Default"))
/*      */       {
/* 1468 */         return 0.0F;
/*      */       }
/*      */ 
/*      */       
/* 1472 */       List<String> list = Arrays.asList(Config.getDisplayModeNames());
/* 1473 */       int i = list.indexOf(this.ofFullscreenMode);
/* 1474 */       return (i < 0) ? 0.0F : (i + 1);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1479 */     return Float.MAX_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setOptionValueOF(Options p_setOptionValueOF_1_, int p_setOptionValueOF_2_) {
/* 1485 */     if (p_setOptionValueOF_1_ == Options.FOG_FANCY)
/*      */     {
/* 1487 */       switch (this.ofFogType) {
/*      */         
/*      */         case 1:
/* 1490 */           this.ofFogType = 2;
/*      */           
/* 1492 */           if (!Config.isFancyFogAvailable())
/*      */           {
/* 1494 */             this.ofFogType = 3;
/*      */           }
/*      */           break;
/*      */ 
/*      */         
/*      */         case 2:
/* 1500 */           this.ofFogType = 3;
/*      */           break;
/*      */         
/*      */         case 3:
/* 1504 */           this.ofFogType = 1;
/*      */           break;
/*      */         
/*      */         default:
/* 1508 */           this.ofFogType = 1;
/*      */           break;
/*      */       } 
/*      */     }
/* 1512 */     if (p_setOptionValueOF_1_ == Options.FOG_START) {
/*      */       
/* 1514 */       this.ofFogStart += 0.2F;
/*      */       
/* 1516 */       if (this.ofFogStart > 0.81F)
/*      */       {
/* 1518 */         this.ofFogStart = 0.2F;
/*      */       }
/*      */     } 
/*      */     
/* 1522 */     if (p_setOptionValueOF_1_ == Options.SMOOTH_FPS)
/*      */     {
/* 1524 */       this.ofSmoothFps = !this.ofSmoothFps;
/*      */     }
/*      */     
/* 1527 */     if (p_setOptionValueOF_1_ == Options.SMOOTH_WORLD) {
/*      */       
/* 1529 */       this.ofSmoothWorld = !this.ofSmoothWorld;
/* 1530 */       Config.updateThreadPriorities();
/*      */     } 
/*      */     
/* 1533 */     if (p_setOptionValueOF_1_ == Options.CLOUDS) {
/*      */       
/* 1535 */       this.ofClouds++;
/*      */       
/* 1537 */       if (this.ofClouds > 3)
/*      */       {
/* 1539 */         this.ofClouds = 0;
/*      */       }
/*      */       
/* 1542 */       updateRenderClouds();
/* 1543 */       this.mc.renderGlobal.resetClouds();
/*      */     } 
/*      */     
/* 1546 */     if (p_setOptionValueOF_1_ == Options.TREES) {
/*      */       
/* 1548 */       this.ofTrees = nextValue(this.ofTrees, OF_TREES_VALUES);
/* 1549 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1552 */     if (p_setOptionValueOF_1_ == Options.DROPPED_ITEMS) {
/*      */       
/* 1554 */       this.ofDroppedItems++;
/*      */       
/* 1556 */       if (this.ofDroppedItems > 2)
/*      */       {
/* 1558 */         this.ofDroppedItems = 0;
/*      */       }
/*      */     } 
/*      */     
/* 1562 */     if (p_setOptionValueOF_1_ == Options.RAIN) {
/*      */       
/* 1564 */       this.ofRain++;
/*      */       
/* 1566 */       if (this.ofRain > 3)
/*      */       {
/* 1568 */         this.ofRain = 0;
/*      */       }
/*      */     } 
/*      */     
/* 1572 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_WATER) {
/*      */       
/* 1574 */       this.ofAnimatedWater++;
/*      */       
/* 1576 */       if (this.ofAnimatedWater == 1)
/*      */       {
/* 1578 */         this.ofAnimatedWater++;
/*      */       }
/*      */       
/* 1581 */       if (this.ofAnimatedWater > 2)
/*      */       {
/* 1583 */         this.ofAnimatedWater = 0;
/*      */       }
/*      */     } 
/*      */     
/* 1587 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_LAVA) {
/*      */       
/* 1589 */       this.ofAnimatedLava++;
/*      */       
/* 1591 */       if (this.ofAnimatedLava == 1)
/*      */       {
/* 1593 */         this.ofAnimatedLava++;
/*      */       }
/*      */       
/* 1596 */       if (this.ofAnimatedLava > 2)
/*      */       {
/* 1598 */         this.ofAnimatedLava = 0;
/*      */       }
/*      */     } 
/*      */     
/* 1602 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_FIRE)
/*      */     {
/* 1604 */       this.ofAnimatedFire = !this.ofAnimatedFire;
/*      */     }
/*      */     
/* 1607 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_PORTAL)
/*      */     {
/* 1609 */       this.ofAnimatedPortal = !this.ofAnimatedPortal;
/*      */     }
/*      */     
/* 1612 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_REDSTONE)
/*      */     {
/* 1614 */       this.ofAnimatedRedstone = !this.ofAnimatedRedstone;
/*      */     }
/*      */     
/* 1617 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_EXPLOSION)
/*      */     {
/* 1619 */       this.ofAnimatedExplosion = !this.ofAnimatedExplosion;
/*      */     }
/*      */     
/* 1622 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_FLAME)
/*      */     {
/* 1624 */       this.ofAnimatedFlame = !this.ofAnimatedFlame;
/*      */     }
/*      */     
/* 1627 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_SMOKE)
/*      */     {
/* 1629 */       this.ofAnimatedSmoke = !this.ofAnimatedSmoke;
/*      */     }
/*      */     
/* 1632 */     if (p_setOptionValueOF_1_ == Options.VOID_PARTICLES)
/*      */     {
/* 1634 */       this.ofVoidParticles = !this.ofVoidParticles;
/*      */     }
/*      */     
/* 1637 */     if (p_setOptionValueOF_1_ == Options.WATER_PARTICLES)
/*      */     {
/* 1639 */       this.ofWaterParticles = !this.ofWaterParticles;
/*      */     }
/*      */     
/* 1642 */     if (p_setOptionValueOF_1_ == Options.PORTAL_PARTICLES)
/*      */     {
/* 1644 */       this.ofPortalParticles = !this.ofPortalParticles;
/*      */     }
/*      */     
/* 1647 */     if (p_setOptionValueOF_1_ == Options.POTION_PARTICLES)
/*      */     {
/* 1649 */       this.ofPotionParticles = !this.ofPotionParticles;
/*      */     }
/*      */     
/* 1652 */     if (p_setOptionValueOF_1_ == Options.FIREWORK_PARTICLES)
/*      */     {
/* 1654 */       this.ofFireworkParticles = !this.ofFireworkParticles;
/*      */     }
/*      */     
/* 1657 */     if (p_setOptionValueOF_1_ == Options.DRIPPING_WATER_LAVA)
/*      */     {
/* 1659 */       this.ofDrippingWaterLava = !this.ofDrippingWaterLava;
/*      */     }
/*      */     
/* 1662 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_TERRAIN)
/*      */     {
/* 1664 */       this.ofAnimatedTerrain = !this.ofAnimatedTerrain;
/*      */     }
/*      */     
/* 1667 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_TEXTURES)
/*      */     {
/* 1669 */       this.ofAnimatedTextures = !this.ofAnimatedTextures;
/*      */     }
/*      */     
/* 1672 */     if (p_setOptionValueOF_1_ == Options.RAIN_SPLASH)
/*      */     {
/* 1674 */       this.ofRainSplash = !this.ofRainSplash;
/*      */     }
/*      */     
/* 1677 */     if (p_setOptionValueOF_1_ == Options.LAGOMETER)
/*      */     {
/* 1679 */       this.ofLagometer = !this.ofLagometer;
/*      */     }
/*      */     
/* 1682 */     if (p_setOptionValueOF_1_ == Options.SHOW_FPS)
/*      */     {
/* 1684 */       this.ofShowFps = !this.ofShowFps;
/*      */     }
/*      */     
/* 1687 */     if (p_setOptionValueOF_1_ == Options.AUTOSAVE_TICKS) {
/*      */       
/* 1689 */       int i = 900;
/* 1690 */       this.ofAutoSaveTicks = Math.max(this.ofAutoSaveTicks / i * i, i);
/* 1691 */       this.ofAutoSaveTicks *= 2;
/*      */       
/* 1693 */       if (this.ofAutoSaveTicks > 32 * i)
/*      */       {
/* 1695 */         this.ofAutoSaveTicks = i;
/*      */       }
/*      */     } 
/*      */     
/* 1699 */     if (p_setOptionValueOF_1_ == Options.BETTER_GRASS) {
/*      */       
/* 1701 */       this.ofBetterGrass++;
/*      */       
/* 1703 */       if (this.ofBetterGrass > 3)
/*      */       {
/* 1705 */         this.ofBetterGrass = 1;
/*      */       }
/*      */       
/* 1708 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1711 */     if (p_setOptionValueOF_1_ == Options.CONNECTED_TEXTURES) {
/*      */       
/* 1713 */       this.ofConnectedTextures++;
/*      */       
/* 1715 */       if (this.ofConnectedTextures > 3)
/*      */       {
/* 1717 */         this.ofConnectedTextures = 1;
/*      */       }
/*      */       
/* 1720 */       if (this.ofConnectedTextures == 2) {
/*      */         
/* 1722 */         this.mc.renderGlobal.loadRenderers();
/*      */       }
/*      */       else {
/*      */         
/* 1726 */         this.mc.refreshResources();
/*      */       } 
/*      */     } 
/*      */     
/* 1730 */     if (p_setOptionValueOF_1_ == Options.WEATHER)
/*      */     {
/* 1732 */       this.ofWeather = !this.ofWeather;
/*      */     }
/*      */     
/* 1735 */     if (p_setOptionValueOF_1_ == Options.SKY)
/*      */     {
/* 1737 */       this.ofSky = !this.ofSky;
/*      */     }
/*      */     
/* 1740 */     if (p_setOptionValueOF_1_ == Options.STARS)
/*      */     {
/* 1742 */       this.ofStars = !this.ofStars;
/*      */     }
/*      */     
/* 1745 */     if (p_setOptionValueOF_1_ == Options.SUN_MOON)
/*      */     {
/* 1747 */       this.ofSunMoon = !this.ofSunMoon;
/*      */     }
/*      */     
/* 1750 */     if (p_setOptionValueOF_1_ == Options.VIGNETTE) {
/*      */       
/* 1752 */       this.ofVignette++;
/*      */       
/* 1754 */       if (this.ofVignette > 2)
/*      */       {
/* 1756 */         this.ofVignette = 0;
/*      */       }
/*      */     } 
/*      */     
/* 1760 */     if (p_setOptionValueOF_1_ == Options.CHUNK_UPDATES) {
/*      */       
/* 1762 */       this.ofChunkUpdates++;
/*      */       
/* 1764 */       if (this.ofChunkUpdates > 5)
/*      */       {
/* 1766 */         this.ofChunkUpdates = 1;
/*      */       }
/*      */     } 
/*      */     
/* 1770 */     if (p_setOptionValueOF_1_ == Options.CHUNK_UPDATES_DYNAMIC)
/*      */     {
/* 1772 */       this.ofChunkUpdatesDynamic = !this.ofChunkUpdatesDynamic;
/*      */     }
/*      */     
/* 1775 */     if (p_setOptionValueOF_1_ == Options.TIME) {
/*      */       
/* 1777 */       this.ofTime++;
/*      */       
/* 1779 */       if (this.ofTime > 2)
/*      */       {
/* 1781 */         this.ofTime = 0;
/*      */       }
/*      */     } 
/*      */     
/* 1785 */     if (p_setOptionValueOF_1_ == Options.CLEAR_WATER) {
/*      */       
/* 1787 */       this.ofClearWater = !this.ofClearWater;
/* 1788 */       updateWaterOpacity();
/*      */     } 
/*      */     
/* 1791 */     if (p_setOptionValueOF_1_ == Options.PROFILER)
/*      */     {
/* 1793 */       this.ofProfiler = !this.ofProfiler;
/*      */     }
/*      */     
/* 1796 */     if (p_setOptionValueOF_1_ == Options.BETTER_SNOW) {
/*      */       
/* 1798 */       this.ofBetterSnow = !this.ofBetterSnow;
/* 1799 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1802 */     if (p_setOptionValueOF_1_ == Options.SWAMP_COLORS) {
/*      */       
/* 1804 */       this.ofSwampColors = !this.ofSwampColors;
/* 1805 */       CustomColors.updateUseDefaultGrassFoliageColors();
/* 1806 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1809 */     if (p_setOptionValueOF_1_ == Options.RANDOM_ENTITIES) {
/*      */       
/* 1811 */       this.ofRandomEntities = !this.ofRandomEntities;
/* 1812 */       RandomEntities.update();
/*      */     } 
/*      */     
/* 1815 */     if (p_setOptionValueOF_1_ == Options.SMOOTH_BIOMES) {
/*      */       
/* 1817 */       this.ofSmoothBiomes = !this.ofSmoothBiomes;
/* 1818 */       CustomColors.updateUseDefaultGrassFoliageColors();
/* 1819 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1822 */     if (p_setOptionValueOF_1_ == Options.CUSTOM_FONTS) {
/*      */       
/* 1824 */       this.ofCustomFonts = !this.ofCustomFonts;
/* 1825 */       this.mc.MCfontRenderer.onResourceManagerReload(Config.getResourceManager());
/* 1826 */       this.mc.standardGalacticFontRenderer.onResourceManagerReload(Config.getResourceManager());
/*      */     } 
/*      */     
/* 1829 */     if (p_setOptionValueOF_1_ == Options.CUSTOM_COLORS) {
/*      */       
/* 1831 */       this.ofCustomColors = !this.ofCustomColors;
/* 1832 */       CustomColors.update();
/* 1833 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1836 */     if (p_setOptionValueOF_1_ == Options.CUSTOM_ITEMS) {
/*      */       
/* 1838 */       this.ofCustomItems = !this.ofCustomItems;
/* 1839 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/* 1842 */     if (p_setOptionValueOF_1_ == Options.CUSTOM_SKY) {
/*      */       
/* 1844 */       this.ofCustomSky = !this.ofCustomSky;
/* 1845 */       CustomSky.update();
/*      */     } 
/*      */     
/* 1848 */     if (p_setOptionValueOF_1_ == Options.SHOW_CAPES)
/*      */     {
/* 1850 */       this.ofShowCapes = !this.ofShowCapes;
/*      */     }
/*      */     
/* 1853 */     if (p_setOptionValueOF_1_ == Options.NATURAL_TEXTURES) {
/*      */       
/* 1855 */       this.ofNaturalTextures = !this.ofNaturalTextures;
/* 1856 */       NaturalTextures.update();
/* 1857 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1860 */     if (p_setOptionValueOF_1_ == Options.EMISSIVE_TEXTURES) {
/*      */       
/* 1862 */       this.ofEmissiveTextures = !this.ofEmissiveTextures;
/* 1863 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/* 1866 */     if (p_setOptionValueOF_1_ == Options.FAST_MATH) {
/*      */       
/* 1868 */       this.ofFastMath = !this.ofFastMath;
/* 1869 */       MathHelper.fastMath = this.ofFastMath;
/*      */     } 
/*      */     
/* 1872 */     if (p_setOptionValueOF_1_ == Options.FAST_RENDER) {
/*      */       
/* 1874 */       if (!this.ofFastRender && Config.isShaders()) {
/*      */         
/* 1876 */         Config.showGuiMessage(Lang.get("of.message.fr.shaders1"), Lang.get("of.message.fr.shaders2"));
/*      */         
/*      */         return;
/*      */       } 
/* 1880 */       this.ofFastRender = !this.ofFastRender;
/*      */       
/* 1882 */       if (this.ofFastRender)
/*      */       {
/* 1884 */         this.mc.entityRenderer.func_181022_b();
/*      */       }
/*      */       
/* 1887 */       Config.updateFramebufferSize();
/*      */     } 
/*      */     
/* 1890 */     if (p_setOptionValueOF_1_ == Options.TRANSLUCENT_BLOCKS) {
/*      */       
/* 1892 */       if (this.ofTranslucentBlocks == 0) {
/*      */         
/* 1894 */         this.ofTranslucentBlocks = 1;
/*      */       }
/* 1896 */       else if (this.ofTranslucentBlocks == 1) {
/*      */         
/* 1898 */         this.ofTranslucentBlocks = 2;
/*      */       }
/* 1900 */       else if (this.ofTranslucentBlocks == 2) {
/*      */         
/* 1902 */         this.ofTranslucentBlocks = 0;
/*      */       }
/*      */       else {
/*      */         
/* 1906 */         this.ofTranslucentBlocks = 0;
/*      */       } 
/*      */       
/* 1909 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1912 */     if (p_setOptionValueOF_1_ == Options.LAZY_CHUNK_LOADING)
/*      */     {
/* 1914 */       this.ofLazyChunkLoading = !this.ofLazyChunkLoading;
/*      */     }
/*      */     
/* 1917 */     if (p_setOptionValueOF_1_ == Options.RENDER_REGIONS) {
/*      */       
/* 1919 */       this.ofRenderRegions = !this.ofRenderRegions;
/* 1920 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1923 */     if (p_setOptionValueOF_1_ == Options.SMART_ANIMATIONS) {
/*      */       
/* 1925 */       this.ofSmartAnimations = !this.ofSmartAnimations;
/* 1926 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1929 */     if (p_setOptionValueOF_1_ == Options.DYNAMIC_FOV)
/*      */     {
/* 1931 */       this.ofDynamicFov = !this.ofDynamicFov;
/*      */     }
/*      */     
/* 1934 */     if (p_setOptionValueOF_1_ == Options.ALTERNATE_BLOCKS) {
/*      */       
/* 1936 */       this.ofAlternateBlocks = !this.ofAlternateBlocks;
/* 1937 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/* 1940 */     if (p_setOptionValueOF_1_ == Options.DYNAMIC_LIGHTS) {
/*      */       
/* 1942 */       this.ofDynamicLights = nextValue(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
/* 1943 */       DynamicLights.removeLights(this.mc.renderGlobal);
/*      */     } 
/*      */     
/* 1946 */     if (p_setOptionValueOF_1_ == Options.SCREENSHOT_SIZE) {
/*      */       
/* 1948 */       this.ofScreenshotSize++;
/*      */       
/* 1950 */       if (this.ofScreenshotSize > 4)
/*      */       {
/* 1952 */         this.ofScreenshotSize = 1;
/*      */       }
/*      */       
/* 1955 */       if (!OpenGlHelper.isFramebufferEnabled())
/*      */       {
/* 1957 */         this.ofScreenshotSize = 1;
/*      */       }
/*      */     } 
/*      */     
/* 1961 */     if (p_setOptionValueOF_1_ == Options.CUSTOM_ENTITY_MODELS) {
/*      */       
/* 1963 */       this.ofCustomEntityModels = !this.ofCustomEntityModels;
/* 1964 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/* 1967 */     if (p_setOptionValueOF_1_ == Options.CUSTOM_GUIS) {
/*      */       
/* 1969 */       this.ofCustomGuis = !this.ofCustomGuis;
/* 1970 */       CustomGuis.update();
/*      */     } 
/*      */     
/* 1973 */     if (p_setOptionValueOF_1_ == Options.SHOW_GL_ERRORS)
/*      */     {
/* 1975 */       this.ofShowGlErrors = !this.ofShowGlErrors;
/*      */     }
/*      */     
/* 1978 */     if (p_setOptionValueOF_1_ == Options.HELD_ITEM_TOOLTIPS)
/*      */     {
/* 1980 */       this.heldItemTooltips = !this.heldItemTooltips;
/*      */     }
/*      */     
/* 1983 */     if (p_setOptionValueOF_1_ == Options.ADVANCED_TOOLTIPS)
/*      */     {
/* 1985 */       this.advancedItemTooltips = !this.advancedItemTooltips;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private String getKeyBindingOF(Options p_getKeyBindingOF_1_) {
/* 1991 */     String s = I18n.format(p_getKeyBindingOF_1_.getEnumString(), new Object[0]) + ": ";
/*      */     
/* 1993 */     if (s == null)
/*      */     {
/* 1995 */       s = p_getKeyBindingOF_1_.getEnumString();
/*      */     }
/*      */     
/* 1998 */     if (p_getKeyBindingOF_1_ == Options.RENDER_DISTANCE) {
/*      */       
/* 2000 */       int i1 = (int)getOptionFloatValue(p_getKeyBindingOF_1_);
/* 2001 */       String s1 = I18n.format("options.renderDistance.tiny", new Object[0]);
/* 2002 */       int i = 2;
/*      */       
/* 2004 */       if (i1 >= 4) {
/*      */         
/* 2006 */         s1 = I18n.format("options.renderDistance.short", new Object[0]);
/* 2007 */         i = 4;
/*      */       } 
/*      */       
/* 2010 */       if (i1 >= 8) {
/*      */         
/* 2012 */         s1 = I18n.format("options.renderDistance.normal", new Object[0]);
/* 2013 */         i = 8;
/*      */       } 
/*      */       
/* 2016 */       if (i1 >= 16) {
/*      */         
/* 2018 */         s1 = I18n.format("options.renderDistance.far", new Object[0]);
/* 2019 */         i = 16;
/*      */       } 
/*      */       
/* 2022 */       if (i1 >= 32) {
/*      */         
/* 2024 */         s1 = Lang.get("of.options.renderDistance.extreme");
/* 2025 */         i = 32;
/*      */       } 
/*      */       
/* 2028 */       if (i1 >= 48) {
/*      */         
/* 2030 */         s1 = Lang.get("of.options.renderDistance.insane");
/* 2031 */         i = 48;
/*      */       } 
/*      */       
/* 2034 */       if (i1 >= 64) {
/*      */         
/* 2036 */         s1 = Lang.get("of.options.renderDistance.ludicrous");
/* 2037 */         i = 64;
/*      */       } 
/*      */       
/* 2040 */       int j = this.renderDistanceChunks - i;
/* 2041 */       String s2 = s1;
/*      */       
/* 2043 */       if (j > 0)
/*      */       {
/* 2045 */         s2 = s1 + "+";
/*      */       }
/*      */       
/* 2048 */       return s + i1 + " " + s2 + "";
/*      */     } 
/* 2050 */     if (p_getKeyBindingOF_1_ == Options.FOG_FANCY) {
/*      */       
/* 2052 */       switch (this.ofFogType) {
/*      */         
/*      */         case 1:
/* 2055 */           return s + Lang.getFast();
/*      */         
/*      */         case 2:
/* 2058 */           return s + Lang.getFancy();
/*      */         
/*      */         case 3:
/* 2061 */           return s + Lang.getOff();
/*      */       } 
/*      */       
/* 2064 */       return s + Lang.getOff();
/*      */     } 
/*      */     
/* 2067 */     if (p_getKeyBindingOF_1_ == Options.FOG_START)
/*      */     {
/* 2069 */       return s + this.ofFogStart;
/*      */     }
/* 2071 */     if (p_getKeyBindingOF_1_ == Options.MIPMAP_TYPE) {
/*      */       
/* 2073 */       switch (this.ofMipmapType) {
/*      */         
/*      */         case 0:
/* 2076 */           return s + Lang.get("of.options.mipmap.nearest");
/*      */         
/*      */         case 1:
/* 2079 */           return s + Lang.get("of.options.mipmap.linear");
/*      */         
/*      */         case 2:
/* 2082 */           return s + Lang.get("of.options.mipmap.bilinear");
/*      */         
/*      */         case 3:
/* 2085 */           return s + Lang.get("of.options.mipmap.trilinear");
/*      */       } 
/*      */       
/* 2088 */       return s + "of.options.mipmap.nearest";
/*      */     } 
/*      */     
/* 2091 */     if (p_getKeyBindingOF_1_ == Options.SMOOTH_FPS)
/*      */     {
/* 2093 */       return this.ofSmoothFps ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2095 */     if (p_getKeyBindingOF_1_ == Options.SMOOTH_WORLD)
/*      */     {
/* 2097 */       return this.ofSmoothWorld ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2099 */     if (p_getKeyBindingOF_1_ == Options.CLOUDS) {
/*      */       
/* 2101 */       switch (this.ofClouds) {
/*      */         
/*      */         case 1:
/* 2104 */           return s + Lang.getFast();
/*      */         
/*      */         case 2:
/* 2107 */           return s + Lang.getFancy();
/*      */         
/*      */         case 3:
/* 2110 */           return s + Lang.getOff();
/*      */       } 
/*      */       
/* 2113 */       return s + Lang.getDefault();
/*      */     } 
/*      */     
/* 2116 */     if (p_getKeyBindingOF_1_ == Options.TREES) {
/*      */       
/* 2118 */       switch (this.ofTrees) {
/*      */         
/*      */         case 1:
/* 2121 */           return s + Lang.getFast();
/*      */         
/*      */         case 2:
/* 2124 */           return s + Lang.getFancy();
/*      */ 
/*      */         
/*      */         default:
/* 2128 */           return s + Lang.getDefault();
/*      */         case 4:
/*      */           break;
/* 2131 */       }  return s + Lang.get("of.general.smart");
/*      */     } 
/*      */     
/* 2134 */     if (p_getKeyBindingOF_1_ == Options.DROPPED_ITEMS) {
/*      */       
/* 2136 */       switch (this.ofDroppedItems) {
/*      */         
/*      */         case 1:
/* 2139 */           return s + Lang.getFast();
/*      */         
/*      */         case 2:
/* 2142 */           return s + Lang.getFancy();
/*      */       } 
/*      */       
/* 2145 */       return s + Lang.getDefault();
/*      */     } 
/*      */     
/* 2148 */     if (p_getKeyBindingOF_1_ == Options.RAIN) {
/*      */       
/* 2150 */       switch (this.ofRain) {
/*      */         
/*      */         case 1:
/* 2153 */           return s + Lang.getFast();
/*      */         
/*      */         case 2:
/* 2156 */           return s + Lang.getFancy();
/*      */         
/*      */         case 3:
/* 2159 */           return s + Lang.getOff();
/*      */       } 
/*      */       
/* 2162 */       return s + Lang.getDefault();
/*      */     } 
/*      */     
/* 2165 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_WATER) {
/*      */       
/* 2167 */       switch (this.ofAnimatedWater) {
/*      */         
/*      */         case 1:
/* 2170 */           return s + Lang.get("of.options.animation.dynamic");
/*      */         
/*      */         case 2:
/* 2173 */           return s + Lang.getOff();
/*      */       } 
/*      */       
/* 2176 */       return s + Lang.getOn();
/*      */     } 
/*      */     
/* 2179 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_LAVA) {
/*      */       
/* 2181 */       switch (this.ofAnimatedLava) {
/*      */         
/*      */         case 1:
/* 2184 */           return s + Lang.get("of.options.animation.dynamic");
/*      */         
/*      */         case 2:
/* 2187 */           return s + Lang.getOff();
/*      */       } 
/*      */       
/* 2190 */       return s + Lang.getOn();
/*      */     } 
/*      */     
/* 2193 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_FIRE)
/*      */     {
/* 2195 */       return this.ofAnimatedFire ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2197 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_PORTAL)
/*      */     {
/* 2199 */       return this.ofAnimatedPortal ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2201 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_REDSTONE)
/*      */     {
/* 2203 */       return this.ofAnimatedRedstone ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2205 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_EXPLOSION)
/*      */     {
/* 2207 */       return this.ofAnimatedExplosion ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2209 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_FLAME)
/*      */     {
/* 2211 */       return this.ofAnimatedFlame ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2213 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_SMOKE)
/*      */     {
/* 2215 */       return this.ofAnimatedSmoke ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2217 */     if (p_getKeyBindingOF_1_ == Options.VOID_PARTICLES)
/*      */     {
/* 2219 */       return this.ofVoidParticles ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2221 */     if (p_getKeyBindingOF_1_ == Options.WATER_PARTICLES)
/*      */     {
/* 2223 */       return this.ofWaterParticles ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2225 */     if (p_getKeyBindingOF_1_ == Options.PORTAL_PARTICLES)
/*      */     {
/* 2227 */       return this.ofPortalParticles ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2229 */     if (p_getKeyBindingOF_1_ == Options.POTION_PARTICLES)
/*      */     {
/* 2231 */       return this.ofPotionParticles ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2233 */     if (p_getKeyBindingOF_1_ == Options.FIREWORK_PARTICLES)
/*      */     {
/* 2235 */       return this.ofFireworkParticles ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2237 */     if (p_getKeyBindingOF_1_ == Options.DRIPPING_WATER_LAVA)
/*      */     {
/* 2239 */       return this.ofDrippingWaterLava ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2241 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_TERRAIN)
/*      */     {
/* 2243 */       return this.ofAnimatedTerrain ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2245 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_TEXTURES)
/*      */     {
/* 2247 */       return this.ofAnimatedTextures ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2249 */     if (p_getKeyBindingOF_1_ == Options.RAIN_SPLASH)
/*      */     {
/* 2251 */       return this.ofRainSplash ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2253 */     if (p_getKeyBindingOF_1_ == Options.LAGOMETER)
/*      */     {
/* 2255 */       return this.ofLagometer ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2257 */     if (p_getKeyBindingOF_1_ == Options.SHOW_FPS)
/*      */     {
/* 2259 */       return this.ofShowFps ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2261 */     if (p_getKeyBindingOF_1_ == Options.AUTOSAVE_TICKS) {
/*      */       
/* 2263 */       int l = 900;
/* 2264 */       return (this.ofAutoSaveTicks <= l) ? (s + Lang.get("of.options.save.45s")) : ((this.ofAutoSaveTicks <= 2 * l) ? (s + Lang.get("of.options.save.90s")) : ((this.ofAutoSaveTicks <= 4 * l) ? (s + Lang.get("of.options.save.3min")) : ((this.ofAutoSaveTicks <= 8 * l) ? (s + Lang.get("of.options.save.6min")) : ((this.ofAutoSaveTicks <= 16 * l) ? (s + Lang.get("of.options.save.12min")) : (s + Lang.get("of.options.save.24min"))))));
/*      */     } 
/* 2266 */     if (p_getKeyBindingOF_1_ == Options.BETTER_GRASS) {
/*      */       
/* 2268 */       switch (this.ofBetterGrass) {
/*      */         
/*      */         case 1:
/* 2271 */           return s + Lang.getFast();
/*      */         
/*      */         case 2:
/* 2274 */           return s + Lang.getFancy();
/*      */       } 
/*      */       
/* 2277 */       return s + Lang.getOff();
/*      */     } 
/*      */     
/* 2280 */     if (p_getKeyBindingOF_1_ == Options.CONNECTED_TEXTURES) {
/*      */       
/* 2282 */       switch (this.ofConnectedTextures) {
/*      */         
/*      */         case 1:
/* 2285 */           return s + Lang.getFast();
/*      */         
/*      */         case 2:
/* 2288 */           return s + Lang.getFancy();
/*      */       } 
/*      */       
/* 2291 */       return s + Lang.getOff();
/*      */     } 
/*      */     
/* 2294 */     if (p_getKeyBindingOF_1_ == Options.WEATHER)
/*      */     {
/* 2296 */       return this.ofWeather ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2298 */     if (p_getKeyBindingOF_1_ == Options.SKY)
/*      */     {
/* 2300 */       return this.ofSky ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2302 */     if (p_getKeyBindingOF_1_ == Options.STARS)
/*      */     {
/* 2304 */       return this.ofStars ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2306 */     if (p_getKeyBindingOF_1_ == Options.SUN_MOON)
/*      */     {
/* 2308 */       return this.ofSunMoon ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2310 */     if (p_getKeyBindingOF_1_ == Options.VIGNETTE) {
/*      */       
/* 2312 */       switch (this.ofVignette) {
/*      */         
/*      */         case 1:
/* 2315 */           return s + Lang.getFast();
/*      */         
/*      */         case 2:
/* 2318 */           return s + Lang.getFancy();
/*      */       } 
/*      */       
/* 2321 */       return s + Lang.getDefault();
/*      */     } 
/*      */     
/* 2324 */     if (p_getKeyBindingOF_1_ == Options.CHUNK_UPDATES)
/*      */     {
/* 2326 */       return s + this.ofChunkUpdates;
/*      */     }
/* 2328 */     if (p_getKeyBindingOF_1_ == Options.CHUNK_UPDATES_DYNAMIC)
/*      */     {
/* 2330 */       return this.ofChunkUpdatesDynamic ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2332 */     if (p_getKeyBindingOF_1_ == Options.TIME)
/*      */     {
/* 2334 */       return (this.ofTime == 1) ? (s + Lang.get("of.options.time.dayOnly")) : ((this.ofTime == 2) ? (s + Lang.get("of.options.time.nightOnly")) : (s + Lang.getDefault()));
/*      */     }
/* 2336 */     if (p_getKeyBindingOF_1_ == Options.CLEAR_WATER)
/*      */     {
/* 2338 */       return this.ofClearWater ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2340 */     if (p_getKeyBindingOF_1_ == Options.AA_LEVEL) {
/*      */       
/* 2342 */       String s3 = "";
/*      */       
/* 2344 */       if (this.ofAaLevel != Config.getAntialiasingLevel())
/*      */       {
/* 2346 */         s3 = " (" + Lang.get("of.general.restart") + ")";
/*      */       }
/*      */       
/* 2349 */       return (this.ofAaLevel == 0) ? (s + Lang.getOff() + s3) : (s + this.ofAaLevel + s3);
/*      */     } 
/* 2351 */     if (p_getKeyBindingOF_1_ == Options.AF_LEVEL)
/*      */     {
/* 2353 */       return (this.ofAfLevel == 1) ? (s + Lang.getOff()) : (s + this.ofAfLevel);
/*      */     }
/* 2355 */     if (p_getKeyBindingOF_1_ == Options.PROFILER)
/*      */     {
/* 2357 */       return this.ofProfiler ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2359 */     if (p_getKeyBindingOF_1_ == Options.BETTER_SNOW)
/*      */     {
/* 2361 */       return this.ofBetterSnow ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2363 */     if (p_getKeyBindingOF_1_ == Options.SWAMP_COLORS)
/*      */     {
/* 2365 */       return this.ofSwampColors ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2367 */     if (p_getKeyBindingOF_1_ == Options.RANDOM_ENTITIES)
/*      */     {
/* 2369 */       return this.ofRandomEntities ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2371 */     if (p_getKeyBindingOF_1_ == Options.SMOOTH_BIOMES)
/*      */     {
/* 2373 */       return this.ofSmoothBiomes ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2375 */     if (p_getKeyBindingOF_1_ == Options.CUSTOM_FONTS)
/*      */     {
/* 2377 */       return this.ofCustomFonts ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2379 */     if (p_getKeyBindingOF_1_ == Options.CUSTOM_COLORS)
/*      */     {
/* 2381 */       return this.ofCustomColors ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2383 */     if (p_getKeyBindingOF_1_ == Options.CUSTOM_SKY)
/*      */     {
/* 2385 */       return this.ofCustomSky ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2387 */     if (p_getKeyBindingOF_1_ == Options.SHOW_CAPES)
/*      */     {
/* 2389 */       return this.ofShowCapes ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2391 */     if (p_getKeyBindingOF_1_ == Options.CUSTOM_ITEMS)
/*      */     {
/* 2393 */       return this.ofCustomItems ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2395 */     if (p_getKeyBindingOF_1_ == Options.NATURAL_TEXTURES)
/*      */     {
/* 2397 */       return this.ofNaturalTextures ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2399 */     if (p_getKeyBindingOF_1_ == Options.EMISSIVE_TEXTURES)
/*      */     {
/* 2401 */       return this.ofEmissiveTextures ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2403 */     if (p_getKeyBindingOF_1_ == Options.FAST_MATH)
/*      */     {
/* 2405 */       return this.ofFastMath ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2407 */     if (p_getKeyBindingOF_1_ == Options.FAST_RENDER)
/*      */     {
/* 2409 */       return this.ofFastRender ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2411 */     if (p_getKeyBindingOF_1_ == Options.TRANSLUCENT_BLOCKS)
/*      */     {
/* 2413 */       return (this.ofTranslucentBlocks == 1) ? (s + Lang.getFast()) : ((this.ofTranslucentBlocks == 2) ? (s + Lang.getFancy()) : (s + Lang.getDefault()));
/*      */     }
/* 2415 */     if (p_getKeyBindingOF_1_ == Options.LAZY_CHUNK_LOADING)
/*      */     {
/* 2417 */       return this.ofLazyChunkLoading ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2419 */     if (p_getKeyBindingOF_1_ == Options.RENDER_REGIONS)
/*      */     {
/* 2421 */       return this.ofRenderRegions ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2423 */     if (p_getKeyBindingOF_1_ == Options.SMART_ANIMATIONS)
/*      */     {
/* 2425 */       return this.ofSmartAnimations ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2427 */     if (p_getKeyBindingOF_1_ == Options.DYNAMIC_FOV)
/*      */     {
/* 2429 */       return this.ofDynamicFov ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2431 */     if (p_getKeyBindingOF_1_ == Options.ALTERNATE_BLOCKS)
/*      */     {
/* 2433 */       return this.ofAlternateBlocks ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2435 */     if (p_getKeyBindingOF_1_ == Options.DYNAMIC_LIGHTS) {
/*      */       
/* 2437 */       int k = indexOf(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
/* 2438 */       return s + getTranslation(KEYS_DYNAMIC_LIGHTS, k);
/*      */     } 
/* 2440 */     if (p_getKeyBindingOF_1_ == Options.SCREENSHOT_SIZE)
/*      */     {
/* 2442 */       return (this.ofScreenshotSize <= 1) ? (s + Lang.getDefault()) : (s + this.ofScreenshotSize + "x");
/*      */     }
/* 2444 */     if (p_getKeyBindingOF_1_ == Options.CUSTOM_ENTITY_MODELS)
/*      */     {
/* 2446 */       return this.ofCustomEntityModels ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2448 */     if (p_getKeyBindingOF_1_ == Options.CUSTOM_GUIS)
/*      */     {
/* 2450 */       return this.ofCustomGuis ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2452 */     if (p_getKeyBindingOF_1_ == Options.SHOW_GL_ERRORS)
/*      */     {
/* 2454 */       return this.ofShowGlErrors ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2456 */     if (p_getKeyBindingOF_1_ == Options.FULLSCREEN_MODE)
/*      */     {
/* 2458 */       return this.ofFullscreenMode.equals("Default") ? (s + Lang.getDefault()) : (s + this.ofFullscreenMode);
/*      */     }
/* 2460 */     if (p_getKeyBindingOF_1_ == Options.HELD_ITEM_TOOLTIPS)
/*      */     {
/* 2462 */       return this.heldItemTooltips ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2464 */     if (p_getKeyBindingOF_1_ == Options.ADVANCED_TOOLTIPS)
/*      */     {
/* 2466 */       return this.advancedItemTooltips ? (s + Lang.getOn()) : (s + Lang.getOff());
/*      */     }
/* 2468 */     if (p_getKeyBindingOF_1_ == Options.FRAMERATE_LIMIT) {
/*      */       
/* 2470 */       float f = getOptionFloatValue(p_getKeyBindingOF_1_);
/* 2471 */       return (f == 0.0F) ? (s + Lang.get("of.options.framerateLimit.vsync")) : ((f == p_getKeyBindingOF_1_.valueMax) ? (s + I18n.format("options.framerateLimit.max", new Object[0])) : (s + (int)f + " fps"));
/*      */     } 
/*      */ 
/*      */     
/* 2475 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadOfOptions() {
/*      */     try {
/* 2483 */       File file1 = this.optionsFileOF;
/*      */       
/* 2485 */       if (!file1.exists())
/*      */       {
/* 2487 */         file1 = this.optionsFile;
/*      */       }
/*      */       
/* 2490 */       if (!file1.exists()) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 2495 */       BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(file1), StandardCharsets.UTF_8));
/* 2496 */       String s = "";
/*      */       
/* 2498 */       while ((s = bufferedreader.readLine()) != null) {
/*      */ 
/*      */         
/*      */         try {
/* 2502 */           String[] astring = s.split(":");
/*      */           
/* 2504 */           if (astring[0].equals("ofRenderDistanceChunks") && astring.length >= 2) {
/*      */             
/* 2506 */             this.renderDistanceChunks = Integer.parseInt(astring[1]);
/* 2507 */             this.renderDistanceChunks = Config.limit(this.renderDistanceChunks, 2, 1024);
/*      */           } 
/*      */           
/* 2510 */           if (astring[0].equals("ofFogType") && astring.length >= 2) {
/*      */             
/* 2512 */             this.ofFogType = Integer.parseInt(astring[1]);
/* 2513 */             this.ofFogType = Config.limit(this.ofFogType, 1, 3);
/*      */           } 
/*      */           
/* 2516 */           if (astring[0].equals("ofFogStart") && astring.length >= 2) {
/*      */             
/* 2518 */             this.ofFogStart = Float.parseFloat(astring[1]);
/*      */             
/* 2520 */             if (this.ofFogStart < 0.2F)
/*      */             {
/* 2522 */               this.ofFogStart = 0.2F;
/*      */             }
/*      */             
/* 2525 */             if (this.ofFogStart > 0.81F)
/*      */             {
/* 2527 */               this.ofFogStart = 0.8F;
/*      */             }
/*      */           } 
/*      */           
/* 2531 */           if (astring[0].equals("ofMipmapType") && astring.length >= 2) {
/*      */             
/* 2533 */             this.ofMipmapType = Integer.parseInt(astring[1]);
/* 2534 */             this.ofMipmapType = Config.limit(this.ofMipmapType, 0, 3);
/*      */           } 
/*      */           
/* 2537 */           if (astring[0].equals("ofOcclusionFancy") && astring.length >= 2)
/*      */           {
/* 2539 */             this.ofOcclusionFancy = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2542 */           if (astring[0].equals("ofSmoothFps") && astring.length >= 2)
/*      */           {
/* 2544 */             this.ofSmoothFps = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2547 */           if (astring[0].equals("ofSmoothWorld") && astring.length >= 2)
/*      */           {
/* 2549 */             this.ofSmoothWorld = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2552 */           if (astring[0].equals("ofAoLevel") && astring.length >= 2) {
/*      */             
/* 2554 */             this.ofAoLevel = Float.parseFloat(astring[1]);
/* 2555 */             this.ofAoLevel = Config.limit(this.ofAoLevel, 0.0F, 1.0F);
/*      */           } 
/*      */           
/* 2558 */           if (astring[0].equals("ofClouds") && astring.length >= 2) {
/*      */             
/* 2560 */             this.ofClouds = Integer.parseInt(astring[1]);
/* 2561 */             this.ofClouds = Config.limit(this.ofClouds, 0, 3);
/* 2562 */             updateRenderClouds();
/*      */           } 
/*      */           
/* 2565 */           if (astring[0].equals("ofCloudsHeight") && astring.length >= 2) {
/*      */             
/* 2567 */             this.ofCloudsHeight = Float.parseFloat(astring[1]);
/* 2568 */             this.ofCloudsHeight = Config.limit(this.ofCloudsHeight, 0.0F, 1.0F);
/*      */           } 
/*      */           
/* 2571 */           if (astring[0].equals("ofTrees") && astring.length >= 2) {
/*      */             
/* 2573 */             this.ofTrees = Integer.parseInt(astring[1]);
/* 2574 */             this.ofTrees = limit(this.ofTrees, OF_TREES_VALUES);
/*      */           } 
/*      */           
/* 2577 */           if (astring[0].equals("ofDroppedItems") && astring.length >= 2) {
/*      */             
/* 2579 */             this.ofDroppedItems = Integer.parseInt(astring[1]);
/* 2580 */             this.ofDroppedItems = Config.limit(this.ofDroppedItems, 0, 2);
/*      */           } 
/*      */           
/* 2583 */           if (astring[0].equals("ofRain") && astring.length >= 2) {
/*      */             
/* 2585 */             this.ofRain = Integer.parseInt(astring[1]);
/* 2586 */             this.ofRain = Config.limit(this.ofRain, 0, 3);
/*      */           } 
/*      */           
/* 2589 */           if (astring[0].equals("ofAnimatedWater") && astring.length >= 2) {
/*      */             
/* 2591 */             this.ofAnimatedWater = Integer.parseInt(astring[1]);
/* 2592 */             this.ofAnimatedWater = Config.limit(this.ofAnimatedWater, 0, 2);
/*      */           } 
/*      */           
/* 2595 */           if (astring[0].equals("ofAnimatedLava") && astring.length >= 2) {
/*      */             
/* 2597 */             this.ofAnimatedLava = Integer.parseInt(astring[1]);
/* 2598 */             this.ofAnimatedLava = Config.limit(this.ofAnimatedLava, 0, 2);
/*      */           } 
/*      */           
/* 2601 */           if (astring[0].equals("ofAnimatedFire") && astring.length >= 2)
/*      */           {
/* 2603 */             this.ofAnimatedFire = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2606 */           if (astring[0].equals("ofAnimatedPortal") && astring.length >= 2)
/*      */           {
/* 2608 */             this.ofAnimatedPortal = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2611 */           if (astring[0].equals("ofAnimatedRedstone") && astring.length >= 2)
/*      */           {
/* 2613 */             this.ofAnimatedRedstone = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2616 */           if (astring[0].equals("ofAnimatedExplosion") && astring.length >= 2)
/*      */           {
/* 2618 */             this.ofAnimatedExplosion = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2621 */           if (astring[0].equals("ofAnimatedFlame") && astring.length >= 2)
/*      */           {
/* 2623 */             this.ofAnimatedFlame = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2626 */           if (astring[0].equals("ofAnimatedSmoke") && astring.length >= 2)
/*      */           {
/* 2628 */             this.ofAnimatedSmoke = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2631 */           if (astring[0].equals("ofVoidParticles") && astring.length >= 2)
/*      */           {
/* 2633 */             this.ofVoidParticles = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2636 */           if (astring[0].equals("ofWaterParticles") && astring.length >= 2)
/*      */           {
/* 2638 */             this.ofWaterParticles = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2641 */           if (astring[0].equals("ofPortalParticles") && astring.length >= 2)
/*      */           {
/* 2643 */             this.ofPortalParticles = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2646 */           if (astring[0].equals("ofPotionParticles") && astring.length >= 2)
/*      */           {
/* 2648 */             this.ofPotionParticles = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2651 */           if (astring[0].equals("ofFireworkParticles") && astring.length >= 2)
/*      */           {
/* 2653 */             this.ofFireworkParticles = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2656 */           if (astring[0].equals("ofDrippingWaterLava") && astring.length >= 2)
/*      */           {
/* 2658 */             this.ofDrippingWaterLava = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2661 */           if (astring[0].equals("ofAnimatedTerrain") && astring.length >= 2)
/*      */           {
/* 2663 */             this.ofAnimatedTerrain = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2666 */           if (astring[0].equals("ofAnimatedTextures") && astring.length >= 2)
/*      */           {
/* 2668 */             this.ofAnimatedTextures = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2671 */           if (astring[0].equals("ofRainSplash") && astring.length >= 2)
/*      */           {
/* 2673 */             this.ofRainSplash = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2676 */           if (astring[0].equals("ofLagometer") && astring.length >= 2)
/*      */           {
/* 2678 */             this.ofLagometer = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2681 */           if (astring[0].equals("ofShowFps") && astring.length >= 2)
/*      */           {
/* 2683 */             this.ofShowFps = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2686 */           if (astring[0].equals("ofAutoSaveTicks") && astring.length >= 2) {
/*      */             
/* 2688 */             this.ofAutoSaveTicks = Integer.parseInt(astring[1]);
/* 2689 */             this.ofAutoSaveTicks = Config.limit(this.ofAutoSaveTicks, 40, 40000);
/*      */           } 
/*      */           
/* 2692 */           if (astring[0].equals("ofBetterGrass") && astring.length >= 2) {
/*      */             
/* 2694 */             this.ofBetterGrass = Integer.parseInt(astring[1]);
/* 2695 */             this.ofBetterGrass = Config.limit(this.ofBetterGrass, 1, 3);
/*      */           } 
/*      */           
/* 2698 */           if (astring[0].equals("ofConnectedTextures") && astring.length >= 2) {
/*      */             
/* 2700 */             this.ofConnectedTextures = Integer.parseInt(astring[1]);
/* 2701 */             this.ofConnectedTextures = Config.limit(this.ofConnectedTextures, 1, 3);
/*      */           } 
/*      */           
/* 2704 */           if (astring[0].equals("ofWeather") && astring.length >= 2)
/*      */           {
/* 2706 */             this.ofWeather = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2709 */           if (astring[0].equals("ofSky") && astring.length >= 2)
/*      */           {
/* 2711 */             this.ofSky = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2714 */           if (astring[0].equals("ofStars") && astring.length >= 2)
/*      */           {
/* 2716 */             this.ofStars = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2719 */           if (astring[0].equals("ofSunMoon") && astring.length >= 2)
/*      */           {
/* 2721 */             this.ofSunMoon = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2724 */           if (astring[0].equals("ofVignette") && astring.length >= 2) {
/*      */             
/* 2726 */             this.ofVignette = Integer.parseInt(astring[1]);
/* 2727 */             this.ofVignette = Config.limit(this.ofVignette, 0, 2);
/*      */           } 
/*      */           
/* 2730 */           if (astring[0].equals("ofChunkUpdates") && astring.length >= 2) {
/*      */             
/* 2732 */             this.ofChunkUpdates = Integer.parseInt(astring[1]);
/* 2733 */             this.ofChunkUpdates = Config.limit(this.ofChunkUpdates, 1, 5);
/*      */           } 
/*      */           
/* 2736 */           if (astring[0].equals("ofChunkUpdatesDynamic") && astring.length >= 2)
/*      */           {
/* 2738 */             this.ofChunkUpdatesDynamic = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2741 */           if (astring[0].equals("ofTime") && astring.length >= 2) {
/*      */             
/* 2743 */             this.ofTime = Integer.parseInt(astring[1]);
/* 2744 */             this.ofTime = Config.limit(this.ofTime, 0, 2);
/*      */           } 
/*      */           
/* 2747 */           if (astring[0].equals("ofClearWater") && astring.length >= 2) {
/*      */             
/* 2749 */             this.ofClearWater = Boolean.parseBoolean(astring[1]);
/* 2750 */             updateWaterOpacity();
/*      */           } 
/*      */           
/* 2753 */           if (astring[0].equals("ofAaLevel") && astring.length >= 2) {
/*      */             
/* 2755 */             this.ofAaLevel = Integer.parseInt(astring[1]);
/* 2756 */             this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
/*      */           } 
/*      */           
/* 2759 */           if (astring[0].equals("ofAfLevel") && astring.length >= 2) {
/*      */             
/* 2761 */             this.ofAfLevel = Integer.parseInt(astring[1]);
/* 2762 */             this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
/*      */           } 
/*      */           
/* 2765 */           if (astring[0].equals("ofProfiler") && astring.length >= 2)
/*      */           {
/* 2767 */             this.ofProfiler = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2770 */           if (astring[0].equals("ofBetterSnow") && astring.length >= 2)
/*      */           {
/* 2772 */             this.ofBetterSnow = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2775 */           if (astring[0].equals("ofSwampColors") && astring.length >= 2)
/*      */           {
/* 2777 */             this.ofSwampColors = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2780 */           if (astring[0].equals("ofRandomEntities") && astring.length >= 2)
/*      */           {
/* 2782 */             this.ofRandomEntities = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2785 */           if (astring[0].equals("ofSmoothBiomes") && astring.length >= 2)
/*      */           {
/* 2787 */             this.ofSmoothBiomes = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2790 */           if (astring[0].equals("ofCustomFonts") && astring.length >= 2)
/*      */           {
/* 2792 */             this.ofCustomFonts = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2795 */           if (astring[0].equals("ofCustomColors") && astring.length >= 2)
/*      */           {
/* 2797 */             this.ofCustomColors = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2800 */           if (astring[0].equals("ofCustomItems") && astring.length >= 2)
/*      */           {
/* 2802 */             this.ofCustomItems = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2805 */           if (astring[0].equals("ofCustomSky") && astring.length >= 2)
/*      */           {
/* 2807 */             this.ofCustomSky = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2810 */           if (astring[0].equals("ofShowCapes") && astring.length >= 2)
/*      */           {
/* 2812 */             this.ofShowCapes = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2815 */           if (astring[0].equals("ofNaturalTextures") && astring.length >= 2)
/*      */           {
/* 2817 */             this.ofNaturalTextures = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2820 */           if (astring[0].equals("ofEmissiveTextures") && astring.length >= 2)
/*      */           {
/* 2822 */             this.ofEmissiveTextures = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2825 */           if (astring[0].equals("ofLazyChunkLoading") && astring.length >= 2)
/*      */           {
/* 2827 */             this.ofLazyChunkLoading = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2830 */           if (astring[0].equals("ofRenderRegions") && astring.length >= 2)
/*      */           {
/* 2832 */             this.ofRenderRegions = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2835 */           if (astring[0].equals("ofSmartAnimations") && astring.length >= 2)
/*      */           {
/* 2837 */             this.ofSmartAnimations = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2840 */           if (astring[0].equals("ofDynamicFov") && astring.length >= 2)
/*      */           {
/* 2842 */             this.ofDynamicFov = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2845 */           if (astring[0].equals("ofAlternateBlocks") && astring.length >= 2)
/*      */           {
/* 2847 */             this.ofAlternateBlocks = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2850 */           if (astring[0].equals("ofDynamicLights") && astring.length >= 2) {
/*      */             
/* 2852 */             this.ofDynamicLights = Integer.parseInt(astring[1]);
/* 2853 */             this.ofDynamicLights = limit(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
/*      */           } 
/*      */           
/* 2856 */           if (astring[0].equals("ofScreenshotSize") && astring.length >= 2) {
/*      */             
/* 2858 */             this.ofScreenshotSize = Integer.parseInt(astring[1]);
/* 2859 */             this.ofScreenshotSize = Config.limit(this.ofScreenshotSize, 1, 4);
/*      */           } 
/*      */           
/* 2862 */           if (astring[0].equals("ofCustomEntityModels") && astring.length >= 2)
/*      */           {
/* 2864 */             this.ofCustomEntityModels = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2867 */           if (astring[0].equals("ofCustomGuis") && astring.length >= 2)
/*      */           {
/* 2869 */             this.ofCustomGuis = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2872 */           if (astring[0].equals("ofShowGlErrors") && astring.length >= 2)
/*      */           {
/* 2874 */             this.ofShowGlErrors = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2877 */           if (astring[0].equals("ofFullscreenMode") && astring.length >= 2)
/*      */           {
/* 2879 */             this.ofFullscreenMode = astring[1];
/*      */           }
/*      */           
/* 2882 */           if (astring[0].equals("ofFastMath") && astring.length >= 2) {
/*      */             
/* 2884 */             this.ofFastMath = Boolean.parseBoolean(astring[1]);
/* 2885 */             MathHelper.fastMath = this.ofFastMath;
/*      */           } 
/*      */           
/* 2888 */           if (astring[0].equals("ofFastRender") && astring.length >= 2)
/*      */           {
/* 2890 */             this.ofFastRender = Boolean.parseBoolean(astring[1]);
/*      */           }
/*      */           
/* 2893 */           if (astring[0].equals("ofTranslucentBlocks") && astring.length >= 2) {
/*      */             
/* 2895 */             this.ofTranslucentBlocks = Integer.parseInt(astring[1]);
/* 2896 */             this.ofTranslucentBlocks = Config.limit(this.ofTranslucentBlocks, 0, 2);
/*      */           } 
/*      */           
/* 2899 */           if (astring[0].equals("key_" + this.ofKeyBindZoom.getKeyDescription()))
/*      */           {
/* 2901 */             this.ofKeyBindZoom.setKeyCode(Integer.parseInt(astring[1]));
/*      */           }
/*      */         }
/* 2904 */         catch (Exception exception) {
/*      */           
/* 2906 */           Config.dbg("Skipping bad option: " + s);
/* 2907 */           exception.printStackTrace();
/*      */         } 
/*      */       } 
/*      */       
/* 2911 */       KeyUtils.fixKeyConflicts(this.keyBindings, new KeyBinding[] { this.ofKeyBindZoom });
/* 2912 */       KeyBinding.resetKeyBindingArrayAndHash();
/* 2913 */       bufferedreader.close();
/*      */     }
/* 2915 */     catch (Exception exception1) {
/*      */       
/* 2917 */       Config.warn("Failed to load options");
/* 2918 */       exception1.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveOfOptions() {
/*      */     try {
/* 2926 */       PrintWriter printwriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.optionsFileOF), StandardCharsets.UTF_8));
/* 2927 */       printwriter.println("ofFogType:" + this.ofFogType);
/* 2928 */       printwriter.println("ofFogStart:" + this.ofFogStart);
/* 2929 */       printwriter.println("ofMipmapType:" + this.ofMipmapType);
/* 2930 */       printwriter.println("ofOcclusionFancy:" + this.ofOcclusionFancy);
/* 2931 */       printwriter.println("ofSmoothFps:" + this.ofSmoothFps);
/* 2932 */       printwriter.println("ofSmoothWorld:" + this.ofSmoothWorld);
/* 2933 */       printwriter.println("ofAoLevel:" + this.ofAoLevel);
/* 2934 */       printwriter.println("ofClouds:" + this.ofClouds);
/* 2935 */       printwriter.println("ofCloudsHeight:" + this.ofCloudsHeight);
/* 2936 */       printwriter.println("ofTrees:" + this.ofTrees);
/* 2937 */       printwriter.println("ofDroppedItems:" + this.ofDroppedItems);
/* 2938 */       printwriter.println("ofRain:" + this.ofRain);
/* 2939 */       printwriter.println("ofAnimatedWater:" + this.ofAnimatedWater);
/* 2940 */       printwriter.println("ofAnimatedLava:" + this.ofAnimatedLava);
/* 2941 */       printwriter.println("ofAnimatedFire:" + this.ofAnimatedFire);
/* 2942 */       printwriter.println("ofAnimatedPortal:" + this.ofAnimatedPortal);
/* 2943 */       printwriter.println("ofAnimatedRedstone:" + this.ofAnimatedRedstone);
/* 2944 */       printwriter.println("ofAnimatedExplosion:" + this.ofAnimatedExplosion);
/* 2945 */       printwriter.println("ofAnimatedFlame:" + this.ofAnimatedFlame);
/* 2946 */       printwriter.println("ofAnimatedSmoke:" + this.ofAnimatedSmoke);
/* 2947 */       printwriter.println("ofVoidParticles:" + this.ofVoidParticles);
/* 2948 */       printwriter.println("ofWaterParticles:" + this.ofWaterParticles);
/* 2949 */       printwriter.println("ofPortalParticles:" + this.ofPortalParticles);
/* 2950 */       printwriter.println("ofPotionParticles:" + this.ofPotionParticles);
/* 2951 */       printwriter.println("ofFireworkParticles:" + this.ofFireworkParticles);
/* 2952 */       printwriter.println("ofDrippingWaterLava:" + this.ofDrippingWaterLava);
/* 2953 */       printwriter.println("ofAnimatedTerrain:" + this.ofAnimatedTerrain);
/* 2954 */       printwriter.println("ofAnimatedTextures:" + this.ofAnimatedTextures);
/* 2955 */       printwriter.println("ofRainSplash:" + this.ofRainSplash);
/* 2956 */       printwriter.println("ofLagometer:" + this.ofLagometer);
/* 2957 */       printwriter.println("ofShowFps:" + this.ofShowFps);
/* 2958 */       printwriter.println("ofAutoSaveTicks:" + this.ofAutoSaveTicks);
/* 2959 */       printwriter.println("ofBetterGrass:" + this.ofBetterGrass);
/* 2960 */       printwriter.println("ofConnectedTextures:" + this.ofConnectedTextures);
/* 2961 */       printwriter.println("ofWeather:" + this.ofWeather);
/* 2962 */       printwriter.println("ofSky:" + this.ofSky);
/* 2963 */       printwriter.println("ofStars:" + this.ofStars);
/* 2964 */       printwriter.println("ofSunMoon:" + this.ofSunMoon);
/* 2965 */       printwriter.println("ofVignette:" + this.ofVignette);
/* 2966 */       printwriter.println("ofChunkUpdates:" + this.ofChunkUpdates);
/* 2967 */       printwriter.println("ofChunkUpdatesDynamic:" + this.ofChunkUpdatesDynamic);
/* 2968 */       printwriter.println("ofTime:" + this.ofTime);
/* 2969 */       printwriter.println("ofClearWater:" + this.ofClearWater);
/* 2970 */       printwriter.println("ofAaLevel:" + this.ofAaLevel);
/* 2971 */       printwriter.println("ofAfLevel:" + this.ofAfLevel);
/* 2972 */       printwriter.println("ofProfiler:" + this.ofProfiler);
/* 2973 */       printwriter.println("ofBetterSnow:" + this.ofBetterSnow);
/* 2974 */       printwriter.println("ofSwampColors:" + this.ofSwampColors);
/* 2975 */       printwriter.println("ofRandomEntities:" + this.ofRandomEntities);
/* 2976 */       printwriter.println("ofSmoothBiomes:" + this.ofSmoothBiomes);
/* 2977 */       printwriter.println("ofCustomFonts:" + this.ofCustomFonts);
/* 2978 */       printwriter.println("ofCustomColors:" + this.ofCustomColors);
/* 2979 */       printwriter.println("ofCustomItems:" + this.ofCustomItems);
/* 2980 */       printwriter.println("ofCustomSky:" + this.ofCustomSky);
/* 2981 */       printwriter.println("ofShowCapes:" + this.ofShowCapes);
/* 2982 */       printwriter.println("ofNaturalTextures:" + this.ofNaturalTextures);
/* 2983 */       printwriter.println("ofEmissiveTextures:" + this.ofEmissiveTextures);
/* 2984 */       printwriter.println("ofLazyChunkLoading:" + this.ofLazyChunkLoading);
/* 2985 */       printwriter.println("ofRenderRegions:" + this.ofRenderRegions);
/* 2986 */       printwriter.println("ofSmartAnimations:" + this.ofSmartAnimations);
/* 2987 */       printwriter.println("ofDynamicFov:" + this.ofDynamicFov);
/* 2988 */       printwriter.println("ofAlternateBlocks:" + this.ofAlternateBlocks);
/* 2989 */       printwriter.println("ofDynamicLights:" + this.ofDynamicLights);
/* 2990 */       printwriter.println("ofScreenshotSize:" + this.ofScreenshotSize);
/* 2991 */       printwriter.println("ofCustomEntityModels:" + this.ofCustomEntityModels);
/* 2992 */       printwriter.println("ofCustomGuis:" + this.ofCustomGuis);
/* 2993 */       printwriter.println("ofShowGlErrors:" + this.ofShowGlErrors);
/* 2994 */       printwriter.println("ofFullscreenMode:" + this.ofFullscreenMode);
/* 2995 */       printwriter.println("ofFastMath:" + this.ofFastMath);
/* 2996 */       printwriter.println("ofFastRender:" + this.ofFastRender);
/* 2997 */       printwriter.println("ofTranslucentBlocks:" + this.ofTranslucentBlocks);
/* 2998 */       printwriter.println("key_" + this.ofKeyBindZoom.getKeyDescription() + ":" + this.ofKeyBindZoom.getKeyCode());
/* 2999 */       printwriter.close();
/*      */     }
/* 3001 */     catch (Exception exception) {
/*      */       
/* 3003 */       Config.warn("Failed to save options");
/* 3004 */       exception.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateRenderClouds() {
/* 3010 */     switch (this.ofClouds) {
/*      */       
/*      */       case 1:
/* 3013 */         this.clouds = 1;
/*      */         return;
/*      */       
/*      */       case 2:
/* 3017 */         this.clouds = 2;
/*      */         return;
/*      */       
/*      */       case 3:
/* 3021 */         this.clouds = 0;
/*      */         return;
/*      */     } 
/*      */     
/* 3025 */     if (this.fancyGraphics) {
/*      */       
/* 3027 */       this.clouds = 2;
/*      */     }
/*      */     else {
/*      */       
/* 3031 */       this.clouds = 1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetSettings() {
/* 3038 */     this.renderDistanceChunks = 8;
/* 3039 */     this.viewBobbing = true;
/* 3040 */     this.anaglyph = false;
/* 3041 */     this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
/* 3042 */     this.enableVsync = false;
/* 3043 */     updateVSync();
/* 3044 */     this.mipmapLevels = 4;
/* 3045 */     this.fancyGraphics = true;
/* 3046 */     this.ambientOcclusion = 2;
/* 3047 */     this.clouds = 2;
/* 3048 */     this.fovSetting = 70.0F;
/* 3049 */     this.gammaSetting = 0.0F;
/* 3050 */     this.guiScale = 0;
/* 3051 */     this.particleSetting = 0;
/* 3052 */     this.heldItemTooltips = true;
/* 3053 */     this.useVbo = false;
/* 3054 */     this.forceUnicodeFont = false;
/* 3055 */     this.ofFogType = 1;
/* 3056 */     this.ofFogStart = 0.8F;
/* 3057 */     this.ofMipmapType = 0;
/* 3058 */     this.ofOcclusionFancy = false;
/* 3059 */     this.ofSmartAnimations = false;
/* 3060 */     this.ofSmoothFps = false;
/* 3061 */     Config.updateAvailableProcessors();
/* 3062 */     this.ofSmoothWorld = Config.isSingleProcessor();
/* 3063 */     this.ofLazyChunkLoading = false;
/* 3064 */     this.ofRenderRegions = false;
/* 3065 */     this.ofFastMath = false;
/* 3066 */     this.ofFastRender = false;
/* 3067 */     this.ofTranslucentBlocks = 0;
/* 3068 */     this.ofDynamicFov = true;
/* 3069 */     this.ofAlternateBlocks = true;
/* 3070 */     this.ofDynamicLights = 3;
/* 3071 */     this.ofScreenshotSize = 1;
/* 3072 */     this.ofCustomEntityModels = true;
/* 3073 */     this.ofCustomGuis = true;
/* 3074 */     this.ofShowGlErrors = true;
/* 3075 */     this.ofAoLevel = 1.0F;
/* 3076 */     this.ofAaLevel = 0;
/* 3077 */     this.ofAfLevel = 1;
/* 3078 */     this.ofClouds = 0;
/* 3079 */     this.ofCloudsHeight = 0.0F;
/* 3080 */     this.ofTrees = 0;
/* 3081 */     this.ofRain = 0;
/* 3082 */     this.ofBetterGrass = 3;
/* 3083 */     this.ofAutoSaveTicks = 4000;
/* 3084 */     this.ofLagometer = false;
/* 3085 */     this.ofShowFps = false;
/* 3086 */     this.ofProfiler = false;
/* 3087 */     this.ofWeather = true;
/* 3088 */     this.ofSky = true;
/* 3089 */     this.ofStars = true;
/* 3090 */     this.ofSunMoon = true;
/* 3091 */     this.ofVignette = 0;
/* 3092 */     this.ofChunkUpdates = 1;
/* 3093 */     this.ofChunkUpdatesDynamic = false;
/* 3094 */     this.ofTime = 0;
/* 3095 */     this.ofClearWater = false;
/* 3096 */     this.ofBetterSnow = false;
/* 3097 */     this.ofFullscreenMode = "Default";
/* 3098 */     this.ofSwampColors = true;
/* 3099 */     this.ofRandomEntities = true;
/* 3100 */     this.ofSmoothBiomes = true;
/* 3101 */     this.ofCustomFonts = true;
/* 3102 */     this.ofCustomColors = true;
/* 3103 */     this.ofCustomItems = true;
/* 3104 */     this.ofCustomSky = true;
/* 3105 */     this.ofShowCapes = true;
/* 3106 */     this.ofConnectedTextures = 2;
/* 3107 */     this.ofNaturalTextures = false;
/* 3108 */     this.ofEmissiveTextures = true;
/* 3109 */     this.ofAnimatedWater = 0;
/* 3110 */     this.ofAnimatedLava = 0;
/* 3111 */     this.ofAnimatedFire = true;
/* 3112 */     this.ofAnimatedPortal = true;
/* 3113 */     this.ofAnimatedRedstone = true;
/* 3114 */     this.ofAnimatedExplosion = true;
/* 3115 */     this.ofAnimatedFlame = true;
/* 3116 */     this.ofAnimatedSmoke = true;
/* 3117 */     this.ofVoidParticles = true;
/* 3118 */     this.ofWaterParticles = true;
/* 3119 */     this.ofRainSplash = true;
/* 3120 */     this.ofPortalParticles = true;
/* 3121 */     this.ofPotionParticles = true;
/* 3122 */     this.ofFireworkParticles = true;
/* 3123 */     this.ofDrippingWaterLava = true;
/* 3124 */     this.ofAnimatedTerrain = true;
/* 3125 */     this.ofAnimatedTextures = true;
/* 3126 */     Shaders.setShaderPack("OFF");
/* 3127 */     Shaders.configAntialiasingLevel = 0;
/* 3128 */     Shaders.uninit();
/* 3129 */     Shaders.storeConfig();
/* 3130 */     updateWaterOpacity();
/* 3131 */     this.mc.refreshResources();
/* 3132 */     saveOptions();
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateVSync() {
/* 3137 */     Display.setVSyncEnabled(this.enableVsync);
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateWaterOpacity() {
/* 3142 */     if (Config.isIntegratedServerRunning())
/*      */     {
/* 3144 */       Config.waterOpacityChanged = true;
/*      */     }
/*      */     
/* 3147 */     ClearWater.updateWaterOpacity(this, (World)this.mc.theWorld);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAllAnimations(boolean p_setAllAnimations_1_) {
/* 3152 */     int i = p_setAllAnimations_1_ ? 0 : 2;
/* 3153 */     this.ofAnimatedWater = i;
/* 3154 */     this.ofAnimatedLava = i;
/* 3155 */     this.ofAnimatedFire = p_setAllAnimations_1_;
/* 3156 */     this.ofAnimatedPortal = p_setAllAnimations_1_;
/* 3157 */     this.ofAnimatedRedstone = p_setAllAnimations_1_;
/* 3158 */     this.ofAnimatedExplosion = p_setAllAnimations_1_;
/* 3159 */     this.ofAnimatedFlame = p_setAllAnimations_1_;
/* 3160 */     this.ofAnimatedSmoke = p_setAllAnimations_1_;
/* 3161 */     this.ofVoidParticles = p_setAllAnimations_1_;
/* 3162 */     this.ofWaterParticles = p_setAllAnimations_1_;
/* 3163 */     this.ofRainSplash = p_setAllAnimations_1_;
/* 3164 */     this.ofPortalParticles = p_setAllAnimations_1_;
/* 3165 */     this.ofPotionParticles = p_setAllAnimations_1_;
/* 3166 */     this.ofFireworkParticles = p_setAllAnimations_1_;
/* 3167 */     this.particleSetting = p_setAllAnimations_1_ ? 0 : 2;
/* 3168 */     this.ofDrippingWaterLava = p_setAllAnimations_1_;
/* 3169 */     this.ofAnimatedTerrain = p_setAllAnimations_1_;
/* 3170 */     this.ofAnimatedTextures = p_setAllAnimations_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int nextValue(int p_nextValue_0_, int[] p_nextValue_1_) {
/* 3175 */     int i = indexOf(p_nextValue_0_, p_nextValue_1_);
/*      */     
/* 3177 */     if (i < 0)
/*      */     {
/* 3179 */       return p_nextValue_1_[0];
/*      */     }
/*      */ 
/*      */     
/* 3183 */     i++;
/*      */     
/* 3185 */     if (i >= p_nextValue_1_.length)
/*      */     {
/* 3187 */       i = 0;
/*      */     }
/*      */     
/* 3190 */     return p_nextValue_1_[i];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int limit(int p_limit_0_, int[] p_limit_1_) {
/* 3196 */     int i = indexOf(p_limit_0_, p_limit_1_);
/* 3197 */     return (i < 0) ? p_limit_1_[0] : p_limit_0_;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int indexOf(int p_indexOf_0_, int[] p_indexOf_1_) {
/* 3202 */     for (int i = 0; i < p_indexOf_1_.length; i++) {
/*      */       
/* 3204 */       if (p_indexOf_1_[i] == p_indexOf_0_)
/*      */       {
/* 3206 */         return i;
/*      */       }
/*      */     } 
/*      */     
/* 3210 */     return -1;
/*      */   }
/*      */   
/*      */   public enum Options
/*      */   {
/* 3215 */     INVERT_MOUSE("options.invertMouse", false, true),
/* 3216 */     SENSITIVITY("options.sensitivity", true, false),
/* 3217 */     FOV("options.fov", true, false, 30.0F, 110.0F, 1.0F),
/* 3218 */     GAMMA("options.gamma", true, false),
/* 3219 */     SATURATION("options.saturation", true, false),
/* 3220 */     RENDER_DISTANCE("options.renderDistance", true, false, 2.0F, 16.0F, 1.0F),
/* 3221 */     VIEW_BOBBING("options.viewBobbing", false, true),
/* 3222 */     ANAGLYPH("options.anaglyph", false, true),
/* 3223 */     FRAMERATE_LIMIT("options.framerateLimit", true, false, 0.0F, 260.0F, 5.0F),
/* 3224 */     FBO_ENABLE("options.fboEnable", false, true),
/* 3225 */     RENDER_CLOUDS("options.renderClouds", false, false),
/* 3226 */     GRAPHICS("options.graphics", false, false),
/* 3227 */     AMBIENT_OCCLUSION("options.ao", false, false),
/* 3228 */     GUI_SCALE("options.guiScale", false, false),
/* 3229 */     PARTICLES("options.particles", false, false),
/* 3230 */     CHAT_VISIBILITY("options.chat.visibility", false, false),
/* 3231 */     CHAT_COLOR("options.chat.color", false, true),
/* 3232 */     CHAT_LINKS("options.chat.links", false, true),
/* 3233 */     CHAT_OPACITY("options.chat.opacity", true, false),
/* 3234 */     CHAT_LINKS_PROMPT("options.chat.links.prompt", false, true),
/* 3235 */     SNOOPER_ENABLED("options.snooper", false, true),
/* 3236 */     USE_FULLSCREEN("options.fullscreen", false, true),
/* 3237 */     ENABLE_VSYNC("options.vsync", false, true),
/* 3238 */     USE_VBO("options.vbo", false, true),
/* 3239 */     TOUCHSCREEN("options.touchscreen", false, true),
/* 3240 */     CHAT_SCALE("options.chat.scale", true, false),
/* 3241 */     CHAT_WIDTH("options.chat.width", true, false),
/* 3242 */     CHAT_HEIGHT_FOCUSED("options.chat.height.focused", true, false),
/* 3243 */     CHAT_HEIGHT_UNFOCUSED("options.chat.height.unfocused", true, false),
/* 3244 */     MIPMAP_LEVELS("options.mipmapLevels", true, false, 0.0F, 4.0F, 1.0F),
/* 3245 */     FORCE_UNICODE_FONT("options.forceUnicodeFont", false, true),
/* 3246 */     BLOCK_ALTERNATIVES("options.blockAlternatives", false, true),
/* 3247 */     REDUCED_DEBUG_INFO("options.reducedDebugInfo", false, true),
/* 3248 */     ENTITY_SHADOWS("options.entityShadows", false, true),
/* 3249 */     FOG_FANCY("of.options.FOG_FANCY", false, false),
/* 3250 */     FOG_START("of.options.FOG_START", false, false),
/* 3251 */     MIPMAP_TYPE("of.options.MIPMAP_TYPE", true, false, 0.0F, 3.0F, 1.0F),
/* 3252 */     SMOOTH_FPS("of.options.SMOOTH_FPS", false, false),
/* 3253 */     CLOUDS("of.options.CLOUDS", false, false),
/* 3254 */     CLOUD_HEIGHT("of.options.CLOUD_HEIGHT", true, false),
/* 3255 */     TREES("of.options.TREES", false, false),
/* 3256 */     RAIN("of.options.RAIN", false, false),
/* 3257 */     ANIMATED_WATER("of.options.ANIMATED_WATER", false, false),
/* 3258 */     ANIMATED_LAVA("of.options.ANIMATED_LAVA", false, false),
/* 3259 */     ANIMATED_FIRE("of.options.ANIMATED_FIRE", false, false),
/* 3260 */     ANIMATED_PORTAL("of.options.ANIMATED_PORTAL", false, false),
/* 3261 */     AO_LEVEL("of.options.AO_LEVEL", true, false),
/* 3262 */     LAGOMETER("of.options.LAGOMETER", false, false),
/* 3263 */     SHOW_FPS("of.options.SHOW_FPS", false, false),
/* 3264 */     AUTOSAVE_TICKS("of.options.AUTOSAVE_TICKS", false, false),
/* 3265 */     BETTER_GRASS("of.options.BETTER_GRASS", false, false),
/* 3266 */     ANIMATED_REDSTONE("of.options.ANIMATED_REDSTONE", false, false),
/* 3267 */     ANIMATED_EXPLOSION("of.options.ANIMATED_EXPLOSION", false, false),
/* 3268 */     ANIMATED_FLAME("of.options.ANIMATED_FLAME", false, false),
/* 3269 */     ANIMATED_SMOKE("of.options.ANIMATED_SMOKE", false, false),
/* 3270 */     WEATHER("of.options.WEATHER", false, false),
/* 3271 */     SKY("of.options.SKY", false, false),
/* 3272 */     STARS("of.options.STARS", false, false),
/* 3273 */     SUN_MOON("of.options.SUN_MOON", false, false),
/* 3274 */     VIGNETTE("of.options.VIGNETTE", false, false),
/* 3275 */     CHUNK_UPDATES("of.options.CHUNK_UPDATES", false, false),
/* 3276 */     CHUNK_UPDATES_DYNAMIC("of.options.CHUNK_UPDATES_DYNAMIC", false, false),
/* 3277 */     TIME("of.options.TIME", false, false),
/* 3278 */     CLEAR_WATER("of.options.CLEAR_WATER", false, false),
/* 3279 */     SMOOTH_WORLD("of.options.SMOOTH_WORLD", false, false),
/* 3280 */     VOID_PARTICLES("of.options.VOID_PARTICLES", false, false),
/* 3281 */     WATER_PARTICLES("of.options.WATER_PARTICLES", false, false),
/* 3282 */     RAIN_SPLASH("of.options.RAIN_SPLASH", false, false),
/* 3283 */     PORTAL_PARTICLES("of.options.PORTAL_PARTICLES", false, false),
/* 3284 */     POTION_PARTICLES("of.options.POTION_PARTICLES", false, false),
/* 3285 */     FIREWORK_PARTICLES("of.options.FIREWORK_PARTICLES", false, false),
/* 3286 */     PROFILER("of.options.PROFILER", false, false),
/* 3287 */     DRIPPING_WATER_LAVA("of.options.DRIPPING_WATER_LAVA", false, false),
/* 3288 */     BETTER_SNOW("of.options.BETTER_SNOW", false, false),
/* 3289 */     FULLSCREEN_MODE("of.options.FULLSCREEN_MODE", true, false, 0.0F, (Config.getDisplayModes()).length, 1.0F),
/* 3290 */     ANIMATED_TERRAIN("of.options.ANIMATED_TERRAIN", false, false),
/* 3291 */     SWAMP_COLORS("of.options.SWAMP_COLORS", false, false),
/* 3292 */     RANDOM_ENTITIES("of.options.RANDOM_ENTITIES", false, false),
/* 3293 */     SMOOTH_BIOMES("of.options.SMOOTH_BIOMES", false, false),
/* 3294 */     CUSTOM_FONTS("of.options.CUSTOM_FONTS", false, false),
/* 3295 */     CUSTOM_COLORS("of.options.CUSTOM_COLORS", false, false),
/* 3296 */     SHOW_CAPES("of.options.SHOW_CAPES", false, false),
/* 3297 */     CONNECTED_TEXTURES("of.options.CONNECTED_TEXTURES", false, false),
/* 3298 */     CUSTOM_ITEMS("of.options.CUSTOM_ITEMS", false, false),
/* 3299 */     AA_LEVEL("of.options.AA_LEVEL", true, false, 0.0F, 16.0F, 1.0F),
/* 3300 */     AF_LEVEL("of.options.AF_LEVEL", true, false, 1.0F, 16.0F, 1.0F),
/* 3301 */     ANIMATED_TEXTURES("of.options.ANIMATED_TEXTURES", false, false),
/* 3302 */     NATURAL_TEXTURES("of.options.NATURAL_TEXTURES", false, false),
/* 3303 */     EMISSIVE_TEXTURES("of.options.EMISSIVE_TEXTURES", false, false),
/* 3304 */     HELD_ITEM_TOOLTIPS("of.options.HELD_ITEM_TOOLTIPS", false, false),
/* 3305 */     DROPPED_ITEMS("of.options.DROPPED_ITEMS", false, false),
/* 3306 */     LAZY_CHUNK_LOADING("of.options.LAZY_CHUNK_LOADING", false, false),
/* 3307 */     CUSTOM_SKY("of.options.CUSTOM_SKY", false, false),
/* 3308 */     FAST_MATH("of.options.FAST_MATH", false, false),
/* 3309 */     FAST_RENDER("of.options.FAST_RENDER", false, false),
/* 3310 */     TRANSLUCENT_BLOCKS("of.options.TRANSLUCENT_BLOCKS", false, false),
/* 3311 */     DYNAMIC_FOV("of.options.DYNAMIC_FOV", false, false),
/* 3312 */     DYNAMIC_LIGHTS("of.options.DYNAMIC_LIGHTS", false, false),
/* 3313 */     ALTERNATE_BLOCKS("of.options.ALTERNATE_BLOCKS", false, false),
/* 3314 */     CUSTOM_ENTITY_MODELS("of.options.CUSTOM_ENTITY_MODELS", false, false),
/* 3315 */     ADVANCED_TOOLTIPS("of.options.ADVANCED_TOOLTIPS", false, false),
/* 3316 */     SCREENSHOT_SIZE("of.options.SCREENSHOT_SIZE", false, false),
/* 3317 */     CUSTOM_GUIS("of.options.CUSTOM_GUIS", false, false),
/* 3318 */     RENDER_REGIONS("of.options.RENDER_REGIONS", false, false),
/* 3319 */     SHOW_GL_ERRORS("of.options.SHOW_GL_ERRORS", false, false),
/* 3320 */     SMART_ANIMATIONS("of.options.SMART_ANIMATIONS", false, false);
/*      */     
/*      */     private final boolean enumFloat;
/*      */     
/*      */     private final boolean enumBoolean;
/*      */     private final String enumString;
/*      */     private final float valueStep;
/*      */     private float valueMin;
/*      */     private float valueMax;
/*      */     
/*      */     public static Options getEnumOptions(int p_74379_0_) {
/* 3331 */       for (Options gamesettings$options : values()) {
/*      */         
/* 3333 */         if (gamesettings$options.returnEnumOrdinal() == p_74379_0_)
/*      */         {
/* 3335 */           return gamesettings$options;
/*      */         }
/*      */       } 
/*      */       
/* 3339 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Options(String p_i45004_3_, boolean p_i45004_4_, boolean p_i45004_5_, float p_i45004_6_, float p_i45004_7_, float p_i45004_8_) {
/* 3349 */       this.enumString = p_i45004_3_;
/* 3350 */       this.enumFloat = p_i45004_4_;
/* 3351 */       this.enumBoolean = p_i45004_5_;
/* 3352 */       this.valueMin = p_i45004_6_;
/* 3353 */       this.valueMax = p_i45004_7_;
/* 3354 */       this.valueStep = p_i45004_8_;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getEnumFloat() {
/* 3359 */       return this.enumFloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getEnumBoolean() {
/* 3364 */       return this.enumBoolean;
/*      */     }
/*      */ 
/*      */     
/*      */     public int returnEnumOrdinal() {
/* 3369 */       return ordinal();
/*      */     }
/*      */ 
/*      */     
/*      */     public String getEnumString() {
/* 3374 */       return this.enumString;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getValueMax() {
/* 3379 */       return this.valueMax;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setValueMax(float p_148263_1_) {
/* 3384 */       this.valueMax = p_148263_1_;
/*      */     }
/*      */ 
/*      */     
/*      */     public float normalizeValue(float p_148266_1_) {
/* 3389 */       return MathHelper.clamp_float((snapToStepClamp(p_148266_1_) - this.valueMin) / (this.valueMax - this.valueMin), 0.0F, 1.0F);
/*      */     }
/*      */ 
/*      */     
/*      */     public float denormalizeValue(float p_148262_1_) {
/* 3394 */       return snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp_float(p_148262_1_, 0.0F, 1.0F));
/*      */     }
/*      */ 
/*      */     
/*      */     public float snapToStepClamp(float p_148268_1_) {
/* 3399 */       p_148268_1_ = snapToStep(p_148268_1_);
/* 3400 */       return MathHelper.clamp_float(p_148268_1_, this.valueMin, this.valueMax);
/*      */     }
/*      */ 
/*      */     
/*      */     protected float snapToStep(float p_148264_1_) {
/* 3405 */       if (this.valueStep > 0.0F)
/*      */       {
/* 3407 */         p_148264_1_ = this.valueStep * Math.round(p_148264_1_ / this.valueStep);
/*      */       }
/*      */       
/* 3410 */       return p_148264_1_;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\settings\GameSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
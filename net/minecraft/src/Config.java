/*      */ package net.minecraft.src;
/*      */ 
/*      */ import java.awt.Desktop;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.lang.reflect.Array;
/*      */ import java.net.URI;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.client.LoadingScreenRenderer;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.renderer.GLAllocation;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.RenderGlobal;
/*      */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.resources.DefaultResourcePack;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.resources.IResource;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourcePack;
/*      */ import net.minecraft.client.resources.ResourcePackRepository;
/*      */ import net.minecraft.client.resources.model.ModelManager;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.FrameTimer;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.optifine.DynamicLights;
/*      */ import net.optifine.GlErrors;
/*      */ import net.optifine.config.GlVersion;
/*      */ import net.optifine.gui.GuiMessage;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.reflect.ReflectorForge;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import net.optifine.util.DisplayModeComparator;
/*      */ import net.optifine.util.PropertiesOrdered;
/*      */ import net.optifine.util.TextureUtils;
/*      */ import net.optifine.util.TimedEvent;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.LWJGLException;
/*      */ import org.lwjgl.Sys;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.DisplayMode;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GL30;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.opengl.PixelFormat;
/*      */ 
/*      */ public class Config
/*      */ {
/*      */   public static final String OF_NAME = "OptiFine";
/*      */   public static final String MC_VERSION = "1.8.9";
/*      */   public static final String OF_EDITION = "HD_U";
/*      */   public static final String OF_RELEASE = "L5";
/*      */   public static final String VERSION = "OptiFine_1.8.9_HD_U_L5";
/*   85 */   private static String build = null;
/*   86 */   private static String newRelease = null;
/*      */   private static boolean notify64BitJava = false;
/*   88 */   public static String openGlVersion = null;
/*   89 */   public static String openGlRenderer = null;
/*   90 */   public static String openGlVendor = null;
/*   91 */   public static String[] openGlExtensions = null;
/*   92 */   public static GlVersion glVersion = null;
/*   93 */   public static GlVersion glslVersion = null;
/*   94 */   public static int minecraftVersionInt = -1;
/*      */   public static boolean fancyFogAvailable = false;
/*      */   public static boolean occlusionAvailable = false;
/*   97 */   private static GameSettings gameSettings = null;
/*   98 */   private static Minecraft minecraft = Minecraft.getMinecraft();
/*      */   private static boolean initialized = false;
/*  100 */   private static Thread minecraftThread = null;
/*  101 */   private static DisplayMode desktopDisplayMode = null;
/*  102 */   private static DisplayMode[] displayModes = null;
/*  103 */   private static int antialiasingLevel = 0;
/*  104 */   private static int availableProcessors = 0;
/*      */   public static boolean zoomMode = false;
/*  106 */   private static int texturePackClouds = 0;
/*      */   public static boolean waterOpacityChanged = false;
/*      */   private static boolean fullscreenModeChecked = false;
/*      */   private static boolean desktopModeChecked = false;
/*  110 */   private static DefaultResourcePack defaultResourcePackLazy = null;
/*  111 */   public static final Float DEF_ALPHA_FUNC_LEVEL = Float.valueOf(0.1F);
/*  112 */   private static final Logger LOGGER = LogManager.getLogger();
/*  113 */   public static final boolean logDetail = System.getProperty("log.detail", "false").equals("true");
/*  114 */   private static String mcDebugLast = null;
/*  115 */   private static int fpsMinLast = 0;
/*      */   public static float renderPartialTicks;
/*      */   
/*      */   public static String getVersion() {
/*  119 */     return "OptiFine_1.8.9_HD_U_L5";
/*      */   }
/*      */   
/*      */   public static String getVersionDebug() {
/*  123 */     StringBuffer stringbuffer = new StringBuffer(32);
/*      */     
/*  125 */     if (isDynamicLights()) {
/*  126 */       stringbuffer.append("DL: ");
/*  127 */       stringbuffer.append(DynamicLights.getCount());
/*  128 */       stringbuffer.append(", ");
/*      */     } 
/*      */     
/*  131 */     stringbuffer.append("OptiFine_1.8.9_HD_U_L5");
/*  132 */     String s = Shaders.getShaderPackName();
/*      */     
/*  134 */     if (s != null) {
/*  135 */       stringbuffer.append(", ");
/*  136 */       stringbuffer.append(s);
/*      */     } 
/*      */     
/*  139 */     return stringbuffer.toString();
/*      */   }
/*      */   
/*      */   public static void initGameSettings(GameSettings p_initGameSettings_0_) {
/*  143 */     if (gameSettings == null) {
/*  144 */       gameSettings = p_initGameSettings_0_;
/*  145 */       desktopDisplayMode = Display.getDesktopDisplayMode();
/*  146 */       updateAvailableProcessors();
/*  147 */       ReflectorForge.putLaunchBlackboard("optifine.ForgeSplashCompatible", Boolean.TRUE);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void initDisplay() {
/*  152 */     checkInitialized();
/*  153 */     antialiasingLevel = gameSettings.ofAaLevel;
/*  154 */     checkDisplaySettings();
/*  155 */     checkDisplayMode();
/*  156 */     minecraftThread = Thread.currentThread();
/*  157 */     updateThreadPriorities();
/*  158 */     Shaders.startup(Minecraft.getMinecraft());
/*      */   }
/*      */   
/*      */   public static void checkInitialized() {
/*  162 */     if (!initialized && 
/*  163 */       Display.isCreated()) {
/*  164 */       initialized = true;
/*  165 */       checkOpenGlCaps();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void checkOpenGlCaps() {
/*  171 */     log(getVersion());
/*  172 */     log("Build: " + getBuild());
/*  173 */     log("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
/*  174 */     log("Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
/*  175 */     log("VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
/*  176 */     log("LWJGL: " + Sys.getVersion());
/*  177 */     openGlVersion = GL11.glGetString(7938);
/*  178 */     openGlRenderer = GL11.glGetString(7937);
/*  179 */     openGlVendor = GL11.glGetString(7936);
/*  180 */     log("OpenGL: " + openGlRenderer + ", version " + openGlVersion + ", " + openGlVendor);
/*  181 */     log("OpenGL Version: " + getOpenGlVersionString());
/*      */     
/*  183 */     if (!(GLContext.getCapabilities()).OpenGL12) {
/*  184 */       log("OpenGL Mipmap levels: Not available (GL12.GL_TEXTURE_MAX_LEVEL)");
/*      */     }
/*      */     
/*  187 */     fancyFogAvailable = (GLContext.getCapabilities()).GL_NV_fog_distance;
/*      */     
/*  189 */     if (!fancyFogAvailable) {
/*  190 */       log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
/*      */     }
/*      */     
/*  193 */     occlusionAvailable = (GLContext.getCapabilities()).GL_ARB_occlusion_query;
/*      */     
/*  195 */     if (!occlusionAvailable) {
/*  196 */       log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
/*      */     }
/*      */     
/*  199 */     int i = TextureUtils.getGLMaximumTextureSize();
/*  200 */     dbg("Maximum texture size: " + i + "x" + i);
/*      */   }
/*      */   
/*      */   public static String getBuild() {
/*  204 */     if (build == null) {
/*      */       try {
/*  206 */         InputStream inputstream = Config.class.getResourceAsStream("/buildof.txt");
/*      */         
/*  208 */         if (inputstream == null) {
/*  209 */           return null;
/*      */         }
/*      */         
/*  212 */         build = readLines(inputstream)[0];
/*  213 */       } catch (Exception exception) {
/*  214 */         warn("" + exception.getClass().getName() + ": " + exception.getMessage());
/*  215 */         build = "";
/*      */       } 
/*      */     }
/*      */     
/*  219 */     return build;
/*      */   }
/*      */   
/*      */   public static boolean isFancyFogAvailable() {
/*  223 */     return fancyFogAvailable;
/*      */   }
/*      */   
/*      */   public static boolean isOcclusionAvailable() {
/*  227 */     return occlusionAvailable;
/*      */   }
/*      */   
/*      */   public static int getMinecraftVersionInt() {
/*  231 */     if (minecraftVersionInt < 0) {
/*  232 */       String[] astring = tokenize("1.8.9", ".");
/*  233 */       int i = 0;
/*      */       
/*  235 */       if (astring.length > 0) {
/*  236 */         i += 10000 * parseInt(astring[0], 0);
/*      */       }
/*      */       
/*  239 */       if (astring.length > 1) {
/*  240 */         i += 100 * parseInt(astring[1], 0);
/*      */       }
/*      */       
/*  243 */       if (astring.length > 2) {
/*  244 */         i += 1 * parseInt(astring[2], 0);
/*      */       }
/*      */       
/*  247 */       minecraftVersionInt = i;
/*      */     } 
/*      */     
/*  250 */     return minecraftVersionInt;
/*      */   }
/*      */   
/*      */   public static String getOpenGlVersionString() {
/*  254 */     GlVersion glversion = getGlVersion();
/*  255 */     String s = "" + glversion.getMajor() + "." + glversion.getMinor() + "." + glversion.getRelease();
/*  256 */     return s;
/*      */   }
/*      */   
/*      */   private static GlVersion getGlVersionLwjgl() {
/*  260 */     return (GLContext.getCapabilities()).OpenGL44 ? new GlVersion(4, 4) : ((GLContext.getCapabilities()).OpenGL43 ? new GlVersion(4, 3) : ((GLContext.getCapabilities()).OpenGL42 ? new GlVersion(4, 2) : ((GLContext.getCapabilities()).OpenGL41 ? new GlVersion(4, 1) : ((GLContext.getCapabilities()).OpenGL40 ? new GlVersion(4, 0) : ((GLContext.getCapabilities()).OpenGL33 ? new GlVersion(3, 3) : ((GLContext.getCapabilities()).OpenGL32 ? new GlVersion(3, 2) : ((GLContext.getCapabilities()).OpenGL31 ? new GlVersion(3, 1) : ((GLContext.getCapabilities()).OpenGL30 ? new GlVersion(3, 0) : ((GLContext.getCapabilities()).OpenGL21 ? new GlVersion(2, 1) : ((GLContext.getCapabilities()).OpenGL20 ? new GlVersion(2, 0) : ((GLContext.getCapabilities()).OpenGL15 ? new GlVersion(1, 5) : ((GLContext.getCapabilities()).OpenGL14 ? new GlVersion(1, 4) : ((GLContext.getCapabilities()).OpenGL13 ? new GlVersion(1, 3) : ((GLContext.getCapabilities()).OpenGL12 ? new GlVersion(1, 2) : ((GLContext.getCapabilities()).OpenGL11 ? new GlVersion(1, 1) : new GlVersion(1, 0))))))))))))))));
/*      */   }
/*      */   
/*      */   public static GlVersion getGlVersion() {
/*  264 */     if (glVersion == null) {
/*  265 */       String s = GL11.glGetString(7938);
/*  266 */       glVersion = parseGlVersion(s, null);
/*      */       
/*  268 */       if (glVersion == null) {
/*  269 */         glVersion = getGlVersionLwjgl();
/*      */       }
/*      */       
/*  272 */       if (glVersion == null) {
/*  273 */         glVersion = new GlVersion(1, 0);
/*      */       }
/*      */     } 
/*      */     
/*  277 */     return glVersion;
/*      */   }
/*      */   
/*      */   public static GlVersion getGlslVersion() {
/*  281 */     if (glslVersion == null) {
/*  282 */       String s = GL11.glGetString(35724);
/*  283 */       glslVersion = parseGlVersion(s, null);
/*      */       
/*  285 */       if (glslVersion == null) {
/*  286 */         glslVersion = new GlVersion(1, 10);
/*      */       }
/*      */     } 
/*      */     
/*  290 */     return glslVersion;
/*      */   }
/*      */   
/*      */   public static GlVersion parseGlVersion(String p_parseGlVersion_0_, GlVersion p_parseGlVersion_1_) {
/*      */     try {
/*  295 */       if (p_parseGlVersion_0_ == null) {
/*  296 */         return p_parseGlVersion_1_;
/*      */       }
/*  298 */       Pattern pattern = Pattern.compile("([0-9]+)\\.([0-9]+)(\\.([0-9]+))?(.+)?");
/*  299 */       Matcher matcher = pattern.matcher(p_parseGlVersion_0_);
/*      */       
/*  301 */       if (!matcher.matches()) {
/*  302 */         return p_parseGlVersion_1_;
/*      */       }
/*  304 */       int i = Integer.parseInt(matcher.group(1));
/*  305 */       int j = Integer.parseInt(matcher.group(2));
/*  306 */       int k = (matcher.group(4) != null) ? Integer.parseInt(matcher.group(4)) : 0;
/*  307 */       String s = matcher.group(5);
/*  308 */       return new GlVersion(i, j, k, s);
/*      */     
/*      */     }
/*  311 */     catch (Exception exception) {
/*  312 */       exception.printStackTrace();
/*  313 */       return p_parseGlVersion_1_;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static String[] getOpenGlExtensions() {
/*  318 */     if (openGlExtensions == null) {
/*  319 */       openGlExtensions = detectOpenGlExtensions();
/*      */     }
/*      */     
/*  322 */     return openGlExtensions;
/*      */   }
/*      */   
/*      */   private static String[] detectOpenGlExtensions() {
/*      */     try {
/*  327 */       GlVersion glversion = getGlVersion();
/*      */       
/*  329 */       if (glversion.getMajor() >= 3) {
/*  330 */         int i = GL11.glGetInteger(33309);
/*      */         
/*  332 */         if (i > 0) {
/*  333 */           String[] astring = new String[i];
/*      */           
/*  335 */           for (int j = 0; j < i; j++) {
/*  336 */             astring[j] = GL30.glGetStringi(7939, j);
/*      */           }
/*      */           
/*  339 */           return astring;
/*      */         } 
/*      */       } 
/*  342 */     } catch (Exception exception1) {
/*  343 */       exception1.printStackTrace();
/*      */     } 
/*      */     
/*      */     try {
/*  347 */       String s = GL11.glGetString(7939);
/*  348 */       String[] astring1 = s.split(" ");
/*  349 */       return astring1;
/*  350 */     } catch (Exception exception) {
/*  351 */       exception.printStackTrace();
/*  352 */       return new String[0];
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void updateThreadPriorities() {
/*  357 */     updateAvailableProcessors();
/*  358 */     int i = 8;
/*      */     
/*  360 */     if (isSingleProcessor()) {
/*  361 */       if (isSmoothWorld()) {
/*  362 */         minecraftThread.setPriority(10);
/*  363 */         setThreadPriority("Server thread", 1);
/*      */       } else {
/*  365 */         minecraftThread.setPriority(5);
/*  366 */         setThreadPriority("Server thread", 5);
/*      */       } 
/*      */     } else {
/*  369 */       minecraftThread.setPriority(10);
/*  370 */       setThreadPriority("Server thread", 5);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void setThreadPriority(String p_setThreadPriority_0_, int p_setThreadPriority_1_) {
/*      */     try {
/*  376 */       ThreadGroup threadgroup = Thread.currentThread().getThreadGroup();
/*      */       
/*  378 */       if (threadgroup == null) {
/*      */         return;
/*      */       }
/*      */       
/*  382 */       int i = (threadgroup.activeCount() + 10) * 2;
/*  383 */       Thread[] athread = new Thread[i];
/*  384 */       threadgroup.enumerate(athread, false);
/*      */       
/*  386 */       for (int j = 0; j < athread.length; j++) {
/*  387 */         Thread thread = athread[j];
/*      */         
/*  389 */         if (thread != null && thread.getName().startsWith(p_setThreadPriority_0_)) {
/*  390 */           thread.setPriority(p_setThreadPriority_1_);
/*      */         }
/*      */       } 
/*  393 */     } catch (Throwable throwable) {
/*  394 */       warn(throwable.getClass().getName() + ": " + throwable.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean isMinecraftThread() {
/*  399 */     return (Thread.currentThread() == minecraftThread);
/*      */   }
/*      */   
/*      */   public static boolean isMipmaps() {
/*  403 */     return (gameSettings.mipmapLevels > 0);
/*      */   }
/*      */   
/*      */   public static int getMipmapLevels() {
/*  407 */     return gameSettings.mipmapLevels;
/*      */   }
/*      */   
/*      */   public static int getMipmapType() {
/*  411 */     switch (gameSettings.ofMipmapType) {
/*      */       case 0:
/*  413 */         return 9986;
/*      */       
/*      */       case 1:
/*  416 */         return 9986;
/*      */       
/*      */       case 2:
/*  419 */         if (isMultiTexture()) {
/*  420 */           return 9985;
/*      */         }
/*      */         
/*  423 */         return 9986;
/*      */       
/*      */       case 3:
/*  426 */         if (isMultiTexture()) {
/*  427 */           return 9987;
/*      */         }
/*      */         
/*  430 */         return 9986;
/*      */     } 
/*      */     
/*  433 */     return 9986;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isUseAlphaFunc() {
/*  438 */     float f = getAlphaFuncLevel();
/*  439 */     return (f > DEF_ALPHA_FUNC_LEVEL.floatValue() + 1.0E-5F);
/*      */   }
/*      */   
/*      */   public static float getAlphaFuncLevel() {
/*  443 */     return DEF_ALPHA_FUNC_LEVEL.floatValue();
/*      */   }
/*      */   
/*      */   public static boolean isFogFancy() {
/*  447 */     return !isFancyFogAvailable() ? false : ((gameSettings.ofFogType == 2));
/*      */   }
/*      */   
/*      */   public static boolean isFogFast() {
/*  451 */     return (gameSettings.ofFogType == 1);
/*      */   }
/*      */   
/*      */   public static boolean isFogOff() {
/*  455 */     return (gameSettings.ofFogType == 3);
/*      */   }
/*      */   
/*      */   public static boolean isFogOn() {
/*  459 */     return (gameSettings.ofFogType != 3);
/*      */   }
/*      */   
/*      */   public static float getFogStart() {
/*  463 */     return gameSettings.ofFogStart;
/*      */   }
/*      */   
/*      */   public static void detail(String p_detail_0_) {
/*  467 */     if (logDetail) {
/*  468 */       LOGGER.info("[OptiFine] " + p_detail_0_);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void dbg(String p_dbg_0_) {
/*  473 */     LOGGER.info("[OptiFine] " + p_dbg_0_);
/*      */   }
/*      */   
/*      */   public static void warn(String p_warn_0_) {
/*  477 */     LOGGER.warn("[OptiFine] " + p_warn_0_);
/*      */   }
/*      */   
/*      */   public static void error(String p_error_0_) {
/*  481 */     LOGGER.error("[OptiFine] " + p_error_0_);
/*      */   }
/*      */   
/*      */   public static void log(String p_log_0_) {
/*  485 */     dbg(p_log_0_);
/*      */   }
/*      */   
/*      */   public static int getUpdatesPerFrame() {
/*  489 */     return gameSettings.ofChunkUpdates;
/*      */   }
/*      */   
/*      */   public static boolean isDynamicUpdates() {
/*  493 */     return gameSettings.ofChunkUpdatesDynamic;
/*      */   }
/*      */   
/*      */   public static boolean isRainFancy() {
/*  497 */     return (gameSettings.ofRain == 0) ? gameSettings.fancyGraphics : ((gameSettings.ofRain == 2));
/*      */   }
/*      */   
/*      */   public static boolean isRainOff() {
/*  501 */     return (gameSettings.ofRain == 3);
/*      */   }
/*      */   
/*      */   public static boolean isCloudsFancy() {
/*  505 */     return (gameSettings.ofClouds != 0) ? ((gameSettings.ofClouds == 2)) : ((isShaders() && !Shaders.shaderPackClouds.isDefault()) ? Shaders.shaderPackClouds.isFancy() : ((texturePackClouds != 0) ? ((texturePackClouds == 2)) : gameSettings.fancyGraphics));
/*      */   }
/*      */   
/*      */   public static boolean isCloudsOff() {
/*  509 */     return (gameSettings.ofClouds != 0) ? ((gameSettings.ofClouds == 3)) : ((isShaders() && !Shaders.shaderPackClouds.isDefault()) ? Shaders.shaderPackClouds.isOff() : ((texturePackClouds != 0) ? ((texturePackClouds == 3)) : false));
/*      */   }
/*      */   
/*      */   public static void updateTexturePackClouds() {
/*  513 */     texturePackClouds = 0;
/*  514 */     IResourceManager iresourcemanager = getResourceManager();
/*      */     
/*  516 */     if (iresourcemanager != null) {
/*      */       try {
/*  518 */         InputStream inputstream = iresourcemanager.getResource(new ResourceLocation("mcpatcher/color.properties")).getInputStream();
/*      */         
/*  520 */         if (inputstream == null) {
/*      */           return;
/*      */         }
/*      */         
/*  524 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  525 */         propertiesOrdered.load(inputstream);
/*  526 */         inputstream.close();
/*  527 */         String s = propertiesOrdered.getProperty("clouds");
/*      */         
/*  529 */         if (s == null) {
/*      */           return;
/*      */         }
/*      */         
/*  533 */         dbg("Texture pack clouds: " + s);
/*  534 */         s = s.toLowerCase();
/*      */         
/*  536 */         if (s.equals("fast")) {
/*  537 */           texturePackClouds = 1;
/*      */         }
/*      */         
/*  540 */         if (s.equals("fancy")) {
/*  541 */           texturePackClouds = 2;
/*      */         }
/*      */         
/*  544 */         if (s.equals("off")) {
/*  545 */           texturePackClouds = 3;
/*      */         }
/*  547 */       } catch (Exception exception) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static ModelManager getModelManager() {
/*  554 */     return (minecraft.getRenderItem()).modelManager;
/*      */   }
/*      */   
/*      */   public static boolean isTreesFancy() {
/*  558 */     return (gameSettings.ofTrees == 0) ? gameSettings.fancyGraphics : ((gameSettings.ofTrees != 1));
/*      */   }
/*      */   
/*      */   public static boolean isTreesSmart() {
/*  562 */     return (gameSettings.ofTrees == 4);
/*      */   }
/*      */   
/*      */   public static boolean isCullFacesLeaves() {
/*  566 */     return (gameSettings.ofTrees == 0) ? (!gameSettings.fancyGraphics) : ((gameSettings.ofTrees == 4));
/*      */   }
/*      */   
/*      */   public static boolean isDroppedItemsFancy() {
/*  570 */     return (gameSettings.ofDroppedItems == 0) ? gameSettings.fancyGraphics : ((gameSettings.ofDroppedItems == 2));
/*      */   }
/*      */   
/*      */   public static int limit(int p_limit_0_, int p_limit_1_, int p_limit_2_) {
/*  574 */     return (p_limit_0_ < p_limit_1_) ? p_limit_1_ : ((p_limit_0_ > p_limit_2_) ? p_limit_2_ : p_limit_0_);
/*      */   }
/*      */   
/*      */   public static float limit(float p_limit_0_, float p_limit_1_, float p_limit_2_) {
/*  578 */     return (p_limit_0_ < p_limit_1_) ? p_limit_1_ : ((p_limit_0_ > p_limit_2_) ? p_limit_2_ : p_limit_0_);
/*      */   }
/*      */   
/*      */   public static double limit(double p_limit_0_, double p_limit_2_, double p_limit_4_) {
/*  582 */     return (p_limit_0_ < p_limit_2_) ? p_limit_2_ : ((p_limit_0_ > p_limit_4_) ? p_limit_4_ : p_limit_0_);
/*      */   }
/*      */   
/*      */   public static float limitTo1(float p_limitTo1_0_) {
/*  586 */     return (p_limitTo1_0_ < 0.0F) ? 0.0F : ((p_limitTo1_0_ > 1.0F) ? 1.0F : p_limitTo1_0_);
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedWater() {
/*  590 */     return (gameSettings.ofAnimatedWater != 2);
/*      */   }
/*      */   
/*      */   public static boolean isGeneratedWater() {
/*  594 */     return (gameSettings.ofAnimatedWater == 1);
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedPortal() {
/*  598 */     return gameSettings.ofAnimatedPortal;
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedLava() {
/*  602 */     return (gameSettings.ofAnimatedLava != 2);
/*      */   }
/*      */   
/*      */   public static boolean isGeneratedLava() {
/*  606 */     return (gameSettings.ofAnimatedLava == 1);
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedFire() {
/*  610 */     return gameSettings.ofAnimatedFire;
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedRedstone() {
/*  614 */     return gameSettings.ofAnimatedRedstone;
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedExplosion() {
/*  618 */     return gameSettings.ofAnimatedExplosion;
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedFlame() {
/*  622 */     return gameSettings.ofAnimatedFlame;
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedSmoke() {
/*  626 */     return gameSettings.ofAnimatedSmoke;
/*      */   }
/*      */   
/*      */   public static boolean isVoidParticles() {
/*  630 */     return gameSettings.ofVoidParticles;
/*      */   }
/*      */   
/*      */   public static boolean isWaterParticles() {
/*  634 */     return gameSettings.ofWaterParticles;
/*      */   }
/*      */   
/*      */   public static boolean isRainSplash() {
/*  638 */     return gameSettings.ofRainSplash;
/*      */   }
/*      */   
/*      */   public static boolean isPortalParticles() {
/*  642 */     return gameSettings.ofPortalParticles;
/*      */   }
/*      */   
/*      */   public static boolean isPotionParticles() {
/*  646 */     return gameSettings.ofPotionParticles;
/*      */   }
/*      */   
/*      */   public static boolean isFireworkParticles() {
/*  650 */     return gameSettings.ofFireworkParticles;
/*      */   }
/*      */   
/*      */   public static float getAmbientOcclusionLevel() {
/*  654 */     return (isShaders() && Shaders.aoLevel >= 0.0F) ? Shaders.aoLevel : gameSettings.ofAoLevel;
/*      */   }
/*      */   
/*      */   public static String listToString(List p_listToString_0_) {
/*  658 */     return listToString(p_listToString_0_, ", ");
/*      */   }
/*      */   
/*      */   public static String listToString(List p_listToString_0_, String p_listToString_1_) {
/*  662 */     if (p_listToString_0_ == null) {
/*  663 */       return "";
/*      */     }
/*  665 */     StringBuffer stringbuffer = new StringBuffer(p_listToString_0_.size() * 5);
/*      */     
/*  667 */     for (int i = 0; i < p_listToString_0_.size(); i++) {
/*  668 */       Object object = p_listToString_0_.get(i);
/*      */       
/*  670 */       if (i > 0) {
/*  671 */         stringbuffer.append(p_listToString_1_);
/*      */       }
/*      */       
/*  674 */       stringbuffer.append(object);
/*      */     } 
/*      */     
/*  677 */     return stringbuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static String arrayToString(Object[] p_arrayToString_0_) {
/*  682 */     return arrayToString(p_arrayToString_0_, ", ");
/*      */   }
/*      */   
/*      */   public static String arrayToString(Object[] p_arrayToString_0_, String p_arrayToString_1_) {
/*  686 */     if (p_arrayToString_0_ == null) {
/*  687 */       return "";
/*      */     }
/*  689 */     StringBuffer stringbuffer = new StringBuffer(p_arrayToString_0_.length * 5);
/*      */     
/*  691 */     for (int i = 0; i < p_arrayToString_0_.length; i++) {
/*  692 */       Object object = p_arrayToString_0_[i];
/*      */       
/*  694 */       if (i > 0) {
/*  695 */         stringbuffer.append(p_arrayToString_1_);
/*      */       }
/*      */       
/*  698 */       stringbuffer.append(object);
/*      */     } 
/*      */     
/*  701 */     return stringbuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static String arrayToString(int[] p_arrayToString_0_) {
/*  706 */     return arrayToString(p_arrayToString_0_, ", ");
/*      */   }
/*      */   
/*      */   public static String arrayToString(int[] p_arrayToString_0_, String p_arrayToString_1_) {
/*  710 */     if (p_arrayToString_0_ == null) {
/*  711 */       return "";
/*      */     }
/*  713 */     StringBuffer stringbuffer = new StringBuffer(p_arrayToString_0_.length * 5);
/*      */     
/*  715 */     for (int i = 0; i < p_arrayToString_0_.length; i++) {
/*  716 */       int j = p_arrayToString_0_[i];
/*      */       
/*  718 */       if (i > 0) {
/*  719 */         stringbuffer.append(p_arrayToString_1_);
/*      */       }
/*      */       
/*  722 */       stringbuffer.append(j);
/*      */     } 
/*      */     
/*  725 */     return stringbuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static String arrayToString(float[] p_arrayToString_0_) {
/*  730 */     return arrayToString(p_arrayToString_0_, ", ");
/*      */   }
/*      */   
/*      */   public static String arrayToString(float[] p_arrayToString_0_, String p_arrayToString_1_) {
/*  734 */     if (p_arrayToString_0_ == null) {
/*  735 */       return "";
/*      */     }
/*  737 */     StringBuffer stringbuffer = new StringBuffer(p_arrayToString_0_.length * 5);
/*      */     
/*  739 */     for (int i = 0; i < p_arrayToString_0_.length; i++) {
/*  740 */       float f = p_arrayToString_0_[i];
/*      */       
/*  742 */       if (i > 0) {
/*  743 */         stringbuffer.append(p_arrayToString_1_);
/*      */       }
/*      */       
/*  746 */       stringbuffer.append(f);
/*      */     } 
/*      */     
/*  749 */     return stringbuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static Minecraft getMinecraft() {
/*  754 */     return minecraft;
/*      */   }
/*      */   
/*      */   public static TextureManager getTextureManager() {
/*  758 */     return minecraft.getTextureManager();
/*      */   }
/*      */   
/*      */   public static IResourceManager getResourceManager() {
/*  762 */     return minecraft.getResourceManager();
/*      */   }
/*      */   
/*      */   public static InputStream getResourceStream(ResourceLocation p_getResourceStream_0_) throws IOException {
/*  766 */     return getResourceStream(minecraft.getResourceManager(), p_getResourceStream_0_);
/*      */   }
/*      */   
/*      */   public static InputStream getResourceStream(IResourceManager p_getResourceStream_0_, ResourceLocation p_getResourceStream_1_) throws IOException {
/*  770 */     IResource iresource = p_getResourceStream_0_.getResource(p_getResourceStream_1_);
/*  771 */     return (iresource == null) ? null : iresource.getInputStream();
/*      */   }
/*      */   
/*      */   public static IResource getResource(ResourceLocation p_getResource_0_) throws IOException {
/*  775 */     return minecraft.getResourceManager().getResource(p_getResource_0_);
/*      */   }
/*      */   
/*      */   public static boolean hasResource(ResourceLocation p_hasResource_0_) {
/*  779 */     if (p_hasResource_0_ == null) {
/*  780 */       return false;
/*      */     }
/*  782 */     IResourcePack iresourcepack = getDefiningResourcePack(p_hasResource_0_);
/*  783 */     return (iresourcepack != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean hasResource(IResourceManager p_hasResource_0_, ResourceLocation p_hasResource_1_) {
/*      */     try {
/*  789 */       IResource iresource = p_hasResource_0_.getResource(p_hasResource_1_);
/*  790 */       return (iresource != null);
/*  791 */     } catch (IOException var3) {
/*  792 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static IResourcePack[] getResourcePacks() {
/*  797 */     ResourcePackRepository resourcepackrepository = minecraft.getResourcePackRepository();
/*  798 */     List list = resourcepackrepository.getRepositoryEntries();
/*  799 */     List<IResourcePack> list1 = new ArrayList();
/*      */     
/*  801 */     for (Object e : list) {
/*  802 */       ResourcePackRepository.Entry resourcepackrepository$entry = (ResourcePackRepository.Entry)e;
/*  803 */       list1.add(resourcepackrepository$entry.getResourcePack());
/*      */     } 
/*      */     
/*  806 */     if (resourcepackrepository.getResourcePackInstance() != null) {
/*  807 */       list1.add(resourcepackrepository.getResourcePackInstance());
/*      */     }
/*      */     
/*  810 */     IResourcePack[] airesourcepack = list1.<IResourcePack>toArray(new IResourcePack[list1.size()]);
/*  811 */     return airesourcepack;
/*      */   }
/*      */   
/*      */   public static String getResourcePackNames() {
/*  815 */     if (minecraft.getResourcePackRepository() == null) {
/*  816 */       return "";
/*      */     }
/*  818 */     IResourcePack[] airesourcepack = getResourcePacks();
/*      */     
/*  820 */     if (airesourcepack.length <= 0) {
/*  821 */       return getDefaultResourcePack().getPackName();
/*      */     }
/*  823 */     String[] astring = new String[airesourcepack.length];
/*      */     
/*  825 */     for (int i = 0; i < airesourcepack.length; i++) {
/*  826 */       astring[i] = airesourcepack[i].getPackName();
/*      */     }
/*      */     
/*  829 */     String s = arrayToString((Object[])astring);
/*  830 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static DefaultResourcePack getDefaultResourcePack() {
/*  836 */     if (defaultResourcePackLazy == null) {
/*  837 */       Minecraft minecraft = Minecraft.getMinecraft();
/*  838 */       defaultResourcePackLazy = (DefaultResourcePack)Reflector.getFieldValue(minecraft, Reflector.Minecraft_defaultResourcePack);
/*      */       
/*  840 */       if (defaultResourcePackLazy == null) {
/*  841 */         ResourcePackRepository resourcepackrepository = minecraft.getResourcePackRepository();
/*      */         
/*  843 */         if (resourcepackrepository != null) {
/*  844 */           defaultResourcePackLazy = (DefaultResourcePack)resourcepackrepository.rprDefaultResourcePack;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  849 */     return defaultResourcePackLazy;
/*      */   }
/*      */   
/*      */   public static boolean isFromDefaultResourcePack(ResourceLocation p_isFromDefaultResourcePack_0_) {
/*  853 */     IResourcePack iresourcepack = getDefiningResourcePack(p_isFromDefaultResourcePack_0_);
/*  854 */     return (iresourcepack == getDefaultResourcePack());
/*      */   }
/*      */   
/*      */   public static IResourcePack getDefiningResourcePack(ResourceLocation p_getDefiningResourcePack_0_) {
/*  858 */     ResourcePackRepository resourcepackrepository = minecraft.getResourcePackRepository();
/*  859 */     IResourcePack iresourcepack = resourcepackrepository.getResourcePackInstance();
/*      */     
/*  861 */     if (iresourcepack != null && iresourcepack.resourceExists(p_getDefiningResourcePack_0_)) {
/*  862 */       return iresourcepack;
/*      */     }
/*  864 */     List<ResourcePackRepository.Entry> list = resourcepackrepository.repositoryEntries;
/*      */     
/*  866 */     for (int i = list.size() - 1; i >= 0; i--) {
/*  867 */       ResourcePackRepository.Entry resourcepackrepository$entry = list.get(i);
/*  868 */       IResourcePack iresourcepack1 = resourcepackrepository$entry.getResourcePack();
/*      */       
/*  870 */       if (iresourcepack1.resourceExists(p_getDefiningResourcePack_0_)) {
/*  871 */         return iresourcepack1;
/*      */       }
/*      */     } 
/*      */     
/*  875 */     if (getDefaultResourcePack().resourceExists(p_getDefiningResourcePack_0_)) {
/*  876 */       return (IResourcePack)getDefaultResourcePack();
/*      */     }
/*  878 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static RenderGlobal getRenderGlobal() {
/*  884 */     return minecraft.renderGlobal;
/*      */   }
/*      */   
/*      */   public static boolean isBetterGrass() {
/*  888 */     return (gameSettings.ofBetterGrass != 3);
/*      */   }
/*      */   
/*      */   public static boolean isBetterGrassFancy() {
/*  892 */     return (gameSettings.ofBetterGrass == 2);
/*      */   }
/*      */   
/*      */   public static boolean isWeatherEnabled() {
/*  896 */     return gameSettings.ofWeather;
/*      */   }
/*      */   
/*      */   public static boolean isSkyEnabled() {
/*  900 */     return gameSettings.ofSky;
/*      */   }
/*      */   
/*      */   public static boolean isSunMoonEnabled() {
/*  904 */     return gameSettings.ofSunMoon;
/*      */   }
/*      */   
/*      */   public static boolean isSunTexture() {
/*  908 */     return !isSunMoonEnabled() ? false : ((!isShaders() || Shaders.isSun()));
/*      */   }
/*      */   
/*      */   public static boolean isMoonTexture() {
/*  912 */     return !isSunMoonEnabled() ? false : ((!isShaders() || Shaders.isMoon()));
/*      */   }
/*      */   
/*      */   public static boolean isVignetteEnabled() {
/*  916 */     return (isShaders() && !Shaders.isVignette()) ? false : ((gameSettings.ofVignette == 0) ? gameSettings.fancyGraphics : ((gameSettings.ofVignette == 2)));
/*      */   }
/*      */   
/*      */   public static boolean isStarsEnabled() {
/*  920 */     return gameSettings.ofStars;
/*      */   }
/*      */   
/*      */   public static void sleep(long p_sleep_0_) {
/*      */     try {
/*  925 */       Thread.sleep(p_sleep_0_);
/*  926 */     } catch (InterruptedException interruptedexception) {
/*  927 */       interruptedexception.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean isTimeDayOnly() {
/*  932 */     return (gameSettings.ofTime == 1);
/*      */   }
/*      */   
/*      */   public static boolean isTimeDefault() {
/*  936 */     return (gameSettings.ofTime == 0);
/*      */   }
/*      */   
/*      */   public static boolean isTimeNightOnly() {
/*  940 */     return (gameSettings.ofTime == 2);
/*      */   }
/*      */   
/*      */   public static boolean isClearWater() {
/*  944 */     return gameSettings.ofClearWater;
/*      */   }
/*      */   
/*      */   public static int getAnisotropicFilterLevel() {
/*  948 */     return gameSettings.ofAfLevel;
/*      */   }
/*      */   
/*      */   public static boolean isAnisotropicFiltering() {
/*  952 */     return (getAnisotropicFilterLevel() > 1);
/*      */   }
/*      */   
/*      */   public static int getAntialiasingLevel() {
/*  956 */     return antialiasingLevel;
/*      */   }
/*      */   
/*      */   public static boolean isAntialiasing() {
/*  960 */     return (getAntialiasingLevel() > 0);
/*      */   }
/*      */   
/*      */   public static boolean isAntialiasingConfigured() {
/*  964 */     return ((getGameSettings()).ofAaLevel > 0);
/*      */   }
/*      */   
/*      */   public static boolean isMultiTexture() {
/*  968 */     return (getAnisotropicFilterLevel() > 1) ? true : ((getAntialiasingLevel() > 0));
/*      */   }
/*      */   
/*      */   public static boolean between(int p_between_0_, int p_between_1_, int p_between_2_) {
/*  972 */     return (p_between_0_ >= p_between_1_ && p_between_0_ <= p_between_2_);
/*      */   }
/*      */   
/*      */   public static boolean between(float p_between_0_, float p_between_1_, float p_between_2_) {
/*  976 */     return (p_between_0_ >= p_between_1_ && p_between_0_ <= p_between_2_);
/*      */   }
/*      */   
/*      */   public static boolean isDrippingWaterLava() {
/*  980 */     return gameSettings.ofDrippingWaterLava;
/*      */   }
/*      */   
/*      */   public static boolean isBetterSnow() {
/*  984 */     return gameSettings.ofBetterSnow;
/*      */   }
/*      */   
/*      */   public static Dimension getFullscreenDimension() {
/*  988 */     if (desktopDisplayMode == null)
/*  989 */       return null; 
/*  990 */     if (gameSettings == null) {
/*  991 */       return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
/*      */     }
/*  993 */     String s = gameSettings.ofFullscreenMode;
/*      */     
/*  995 */     if (s.equals("Default")) {
/*  996 */       return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
/*      */     }
/*  998 */     String[] astring = tokenize(s, " x");
/*  999 */     return (astring.length < 2) ? new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight()) : new Dimension(parseInt(astring[0], -1), parseInt(astring[1], -1));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int parseInt(String p_parseInt_0_, int p_parseInt_1_) {
/*      */     try {
/* 1006 */       if (p_parseInt_0_ == null) {
/* 1007 */         return p_parseInt_1_;
/*      */       }
/* 1009 */       p_parseInt_0_ = p_parseInt_0_.trim();
/* 1010 */       return Integer.parseInt(p_parseInt_0_);
/*      */     }
/* 1012 */     catch (NumberFormatException var3) {
/* 1013 */       return p_parseInt_1_;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static float parseFloat(String p_parseFloat_0_, float p_parseFloat_1_) {
/*      */     try {
/* 1019 */       if (p_parseFloat_0_ == null) {
/* 1020 */         return p_parseFloat_1_;
/*      */       }
/* 1022 */       p_parseFloat_0_ = p_parseFloat_0_.trim();
/* 1023 */       return Float.parseFloat(p_parseFloat_0_);
/*      */     }
/* 1025 */     catch (NumberFormatException var3) {
/* 1026 */       return p_parseFloat_1_;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean parseBoolean(String p_parseBoolean_0_, boolean p_parseBoolean_1_) {
/*      */     try {
/* 1032 */       if (p_parseBoolean_0_ == null) {
/* 1033 */         return p_parseBoolean_1_;
/*      */       }
/* 1035 */       p_parseBoolean_0_ = p_parseBoolean_0_.trim();
/* 1036 */       return Boolean.parseBoolean(p_parseBoolean_0_);
/*      */     }
/* 1038 */     catch (NumberFormatException var3) {
/* 1039 */       return p_parseBoolean_1_;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static Boolean parseBoolean(String p_parseBoolean_0_, Boolean p_parseBoolean_1_) {
/*      */     try {
/* 1045 */       if (p_parseBoolean_0_ == null) {
/* 1046 */         return p_parseBoolean_1_;
/*      */       }
/* 1048 */       p_parseBoolean_0_ = p_parseBoolean_0_.trim().toLowerCase();
/* 1049 */       return p_parseBoolean_0_.equals("true") ? Boolean.TRUE : (p_parseBoolean_0_.equals("false") ? Boolean.FALSE : p_parseBoolean_1_);
/*      */     }
/* 1051 */     catch (NumberFormatException var3) {
/* 1052 */       return p_parseBoolean_1_;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static String[] tokenize(String p_tokenize_0_, String p_tokenize_1_) {
/* 1057 */     StringTokenizer stringtokenizer = new StringTokenizer(p_tokenize_0_, p_tokenize_1_);
/* 1058 */     List<String> list = new ArrayList();
/*      */     
/* 1060 */     while (stringtokenizer.hasMoreTokens()) {
/* 1061 */       String s = stringtokenizer.nextToken();
/* 1062 */       list.add(s);
/*      */     } 
/*      */     
/* 1065 */     String[] astring = list.<String>toArray(new String[list.size()]);
/* 1066 */     return astring;
/*      */   }
/*      */   
/*      */   public static DisplayMode getDesktopDisplayMode() {
/* 1070 */     return desktopDisplayMode;
/*      */   }
/*      */   
/*      */   public static DisplayMode[] getDisplayModes() {
/* 1074 */     if (displayModes == null) {
/*      */       try {
/* 1076 */         DisplayMode[] adisplaymode = Display.getAvailableDisplayModes();
/* 1077 */         Set<Dimension> set = getDisplayModeDimensions(adisplaymode);
/* 1078 */         List<DisplayMode> list = new ArrayList();
/*      */         
/* 1080 */         for (Dimension dimension : set) {
/* 1081 */           DisplayMode[] adisplaymode1 = getDisplayModes(adisplaymode, dimension);
/* 1082 */           DisplayMode displaymode = getDisplayMode(adisplaymode1, desktopDisplayMode);
/*      */           
/* 1084 */           if (displaymode != null) {
/* 1085 */             list.add(displaymode);
/*      */           }
/*      */         } 
/*      */         
/* 1089 */         DisplayMode[] adisplaymode2 = list.<DisplayMode>toArray(new DisplayMode[list.size()]);
/* 1090 */         Arrays.sort(adisplaymode2, (Comparator<? super DisplayMode>)new DisplayModeComparator());
/* 1091 */         return adisplaymode2;
/* 1092 */       } catch (Exception exception) {
/* 1093 */         exception.printStackTrace();
/* 1094 */         displayModes = new DisplayMode[] { desktopDisplayMode };
/*      */       } 
/*      */     }
/*      */     
/* 1098 */     return displayModes;
/*      */   }
/*      */   
/*      */   public static DisplayMode getLargestDisplayMode() {
/* 1102 */     DisplayMode[] adisplaymode = getDisplayModes();
/*      */     
/* 1104 */     if (adisplaymode != null && adisplaymode.length >= 1) {
/* 1105 */       DisplayMode displaymode = adisplaymode[adisplaymode.length - 1];
/* 1106 */       return (desktopDisplayMode.getWidth() > displaymode.getWidth()) ? desktopDisplayMode : ((desktopDisplayMode.getWidth() == displaymode.getWidth() && desktopDisplayMode.getHeight() > displaymode.getHeight()) ? desktopDisplayMode : displaymode);
/*      */     } 
/* 1108 */     return desktopDisplayMode;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Set<Dimension> getDisplayModeDimensions(DisplayMode[] p_getDisplayModeDimensions_0_) {
/* 1113 */     Set<Dimension> set = new HashSet<>();
/*      */     
/* 1115 */     for (int i = 0; i < p_getDisplayModeDimensions_0_.length; i++) {
/* 1116 */       DisplayMode displaymode = p_getDisplayModeDimensions_0_[i];
/* 1117 */       Dimension dimension = new Dimension(displaymode.getWidth(), displaymode.getHeight());
/* 1118 */       set.add(dimension);
/*      */     } 
/*      */     
/* 1121 */     return set;
/*      */   }
/*      */   
/*      */   private static DisplayMode[] getDisplayModes(DisplayMode[] p_getDisplayModes_0_, Dimension p_getDisplayModes_1_) {
/* 1125 */     List<DisplayMode> list = new ArrayList();
/*      */     
/* 1127 */     for (int i = 0; i < p_getDisplayModes_0_.length; i++) {
/* 1128 */       DisplayMode displaymode = p_getDisplayModes_0_[i];
/*      */       
/* 1130 */       if (displaymode.getWidth() == p_getDisplayModes_1_.getWidth() && displaymode.getHeight() == p_getDisplayModes_1_.getHeight()) {
/* 1131 */         list.add(displaymode);
/*      */       }
/*      */     } 
/*      */     
/* 1135 */     DisplayMode[] adisplaymode = list.<DisplayMode>toArray(new DisplayMode[list.size()]);
/* 1136 */     return adisplaymode;
/*      */   }
/*      */   
/*      */   private static DisplayMode getDisplayMode(DisplayMode[] p_getDisplayMode_0_, DisplayMode p_getDisplayMode_1_) {
/* 1140 */     if (p_getDisplayMode_1_ != null) {
/* 1141 */       for (int i = 0; i < p_getDisplayMode_0_.length; i++) {
/* 1142 */         DisplayMode displaymode = p_getDisplayMode_0_[i];
/*      */         
/* 1144 */         if (displaymode.getBitsPerPixel() == p_getDisplayMode_1_.getBitsPerPixel() && displaymode.getFrequency() == p_getDisplayMode_1_.getFrequency()) {
/* 1145 */           return displaymode;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1150 */     if (p_getDisplayMode_0_.length <= 0) {
/* 1151 */       return null;
/*      */     }
/* 1153 */     Arrays.sort(p_getDisplayMode_0_, (Comparator<? super DisplayMode>)new DisplayModeComparator());
/* 1154 */     return p_getDisplayMode_0_[p_getDisplayMode_0_.length - 1];
/*      */   }
/*      */ 
/*      */   
/*      */   public static String[] getDisplayModeNames() {
/* 1159 */     DisplayMode[] adisplaymode = getDisplayModes();
/* 1160 */     String[] astring = new String[adisplaymode.length];
/*      */     
/* 1162 */     for (int i = 0; i < adisplaymode.length; i++) {
/* 1163 */       DisplayMode displaymode = adisplaymode[i];
/* 1164 */       String s = "" + displaymode.getWidth() + "x" + displaymode.getHeight();
/* 1165 */       astring[i] = s;
/*      */     } 
/*      */     
/* 1168 */     return astring;
/*      */   }
/*      */   
/*      */   public static DisplayMode getDisplayMode(Dimension p_getDisplayMode_0_) throws LWJGLException {
/* 1172 */     DisplayMode[] adisplaymode = getDisplayModes();
/*      */     
/* 1174 */     for (int i = 0; i < adisplaymode.length; i++) {
/* 1175 */       DisplayMode displaymode = adisplaymode[i];
/*      */       
/* 1177 */       if (displaymode.getWidth() == p_getDisplayMode_0_.width && displaymode.getHeight() == p_getDisplayMode_0_.height) {
/* 1178 */         return displaymode;
/*      */       }
/*      */     } 
/*      */     
/* 1182 */     return desktopDisplayMode;
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedTerrain() {
/* 1186 */     return gameSettings.ofAnimatedTerrain;
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedTextures() {
/* 1190 */     return gameSettings.ofAnimatedTextures;
/*      */   }
/*      */   
/*      */   public static boolean isSwampColors() {
/* 1194 */     return gameSettings.ofSwampColors;
/*      */   }
/*      */   
/*      */   public static boolean isRandomEntities() {
/* 1198 */     return gameSettings.ofRandomEntities;
/*      */   }
/*      */   
/*      */   public static void checkGlError(String p_checkGlError_0_) {
/* 1202 */     int i = GlStateManager.glGetError();
/*      */     
/* 1204 */     if (i != 0 && GlErrors.isEnabled(i)) {
/* 1205 */       String s = getGlErrorString(i);
/* 1206 */       String s1 = String.format("OpenGL error: %s (%s), at: %s", new Object[] { Integer.valueOf(i), s, p_checkGlError_0_ });
/* 1207 */       error(s1);
/*      */       
/* 1209 */       if (isShowGlErrors() && TimedEvent.isActive("ShowGlError", 10000L)) {
/* 1210 */         String s2 = I18n.format("of.message.openglError", new Object[] { Integer.valueOf(i), s });
/* 1211 */         minecraft.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText(s2));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean isSmoothBiomes() {
/* 1217 */     return gameSettings.ofSmoothBiomes;
/*      */   }
/*      */   
/*      */   public static boolean isCustomColors() {
/* 1221 */     return gameSettings.ofCustomColors;
/*      */   }
/*      */   
/*      */   public static boolean isCustomSky() {
/* 1225 */     return gameSettings.ofCustomSky;
/*      */   }
/*      */   
/*      */   public static boolean isCustomFonts() {
/* 1229 */     return gameSettings.ofCustomFonts;
/*      */   }
/*      */   
/*      */   public static boolean isShowCapes() {
/* 1233 */     return gameSettings.ofShowCapes;
/*      */   }
/*      */   
/*      */   public static boolean isConnectedTextures() {
/* 1237 */     return (gameSettings.ofConnectedTextures != 3);
/*      */   }
/*      */   
/*      */   public static boolean isNaturalTextures() {
/* 1241 */     return gameSettings.ofNaturalTextures;
/*      */   }
/*      */   
/*      */   public static boolean isEmissiveTextures() {
/* 1245 */     return gameSettings.ofEmissiveTextures;
/*      */   }
/*      */   
/*      */   public static boolean isConnectedTexturesFancy() {
/* 1249 */     return (gameSettings.ofConnectedTextures == 2);
/*      */   }
/*      */   
/*      */   public static boolean isFastRender() {
/* 1253 */     return gameSettings.ofFastRender;
/*      */   }
/*      */   
/*      */   public static boolean isTranslucentBlocksFancy() {
/* 1257 */     return (gameSettings.ofTranslucentBlocks == 0) ? gameSettings.fancyGraphics : ((gameSettings.ofTranslucentBlocks == 2));
/*      */   }
/*      */   
/*      */   public static boolean isShaders() {
/* 1261 */     return Shaders.shaderPackLoaded;
/*      */   }
/*      */   
/*      */   public static String[] readLines(File p_readLines_0_) throws IOException {
/* 1265 */     FileInputStream fileinputstream = new FileInputStream(p_readLines_0_);
/* 1266 */     return readLines(fileinputstream);
/*      */   }
/*      */   
/*      */   public static String[] readLines(InputStream p_readLines_0_) throws IOException {
/* 1270 */     List<String> list = new ArrayList();
/* 1271 */     InputStreamReader inputstreamreader = new InputStreamReader(p_readLines_0_, StandardCharsets.US_ASCII);
/* 1272 */     BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/*      */     
/*      */     while (true) {
/* 1275 */       String s = bufferedreader.readLine();
/*      */       
/* 1277 */       if (s == null) {
/* 1278 */         String[] astring = (String[])list.toArray((Object[])new String[list.size()]);
/* 1279 */         return astring;
/*      */       } 
/*      */       
/* 1282 */       list.add(s);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static String readFile(File p_readFile_0_) throws IOException {
/* 1287 */     FileInputStream fileinputstream = new FileInputStream(p_readFile_0_);
/* 1288 */     return readInputStream(fileinputstream, "ASCII");
/*      */   }
/*      */   
/*      */   public static String readInputStream(InputStream p_readInputStream_0_) throws IOException {
/* 1292 */     return readInputStream(p_readInputStream_0_, "ASCII");
/*      */   }
/*      */   
/*      */   public static String readInputStream(InputStream p_readInputStream_0_, String p_readInputStream_1_) throws IOException {
/* 1296 */     InputStreamReader inputstreamreader = new InputStreamReader(p_readInputStream_0_, p_readInputStream_1_);
/* 1297 */     BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/* 1298 */     StringBuffer stringbuffer = new StringBuffer();
/*      */     
/*      */     while (true) {
/* 1301 */       String s = bufferedreader.readLine();
/*      */       
/* 1303 */       if (s == null) {
/* 1304 */         return stringbuffer.toString();
/*      */       }
/*      */       
/* 1307 */       stringbuffer.append(s);
/* 1308 */       stringbuffer.append("\n");
/*      */     } 
/*      */   }
/*      */   
/*      */   public static byte[] readAll(InputStream p_readAll_0_) throws IOException {
/* 1313 */     ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
/* 1314 */     byte[] abyte = new byte[1024];
/*      */     
/*      */     while (true) {
/* 1317 */       int i = p_readAll_0_.read(abyte);
/*      */       
/* 1319 */       if (i < 0) {
/* 1320 */         p_readAll_0_.close();
/* 1321 */         byte[] abyte1 = bytearrayoutputstream.toByteArray();
/* 1322 */         return abyte1;
/*      */       } 
/*      */       
/* 1325 */       bytearrayoutputstream.write(abyte, 0, i);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static GameSettings getGameSettings() {
/* 1330 */     return gameSettings;
/*      */   }
/*      */   
/*      */   public static String getNewRelease() {
/* 1334 */     return newRelease;
/*      */   }
/*      */   
/*      */   public static void setNewRelease(String p_setNewRelease_0_) {
/* 1338 */     newRelease = p_setNewRelease_0_;
/*      */   }
/*      */   
/*      */   public static int compareRelease(String p_compareRelease_0_, String p_compareRelease_1_) {
/* 1342 */     String[] astring = splitRelease(p_compareRelease_0_);
/* 1343 */     String[] astring1 = splitRelease(p_compareRelease_1_);
/* 1344 */     String s = astring[0];
/* 1345 */     String s1 = astring1[0];
/*      */     
/* 1347 */     if (!s.equals(s1)) {
/* 1348 */       return s.compareTo(s1);
/*      */     }
/* 1350 */     int i = parseInt(astring[1], -1);
/* 1351 */     int j = parseInt(astring1[1], -1);
/*      */     
/* 1353 */     if (i != j) {
/* 1354 */       return i - j;
/*      */     }
/* 1356 */     String s2 = astring[2];
/* 1357 */     String s3 = astring1[2];
/*      */     
/* 1359 */     if (!s2.equals(s3)) {
/* 1360 */       if (s2.isEmpty()) {
/* 1361 */         return 1;
/*      */       }
/*      */       
/* 1364 */       if (s3.isEmpty()) {
/* 1365 */         return -1;
/*      */       }
/*      */     } 
/*      */     
/* 1369 */     return s2.compareTo(s3);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] splitRelease(String p_splitRelease_0_) {
/* 1375 */     if (p_splitRelease_0_ != null && p_splitRelease_0_.length() > 0) {
/* 1376 */       Pattern pattern = Pattern.compile("([A-Z])([0-9]+)(.*)");
/* 1377 */       Matcher matcher = pattern.matcher(p_splitRelease_0_);
/*      */       
/* 1379 */       if (!matcher.matches()) {
/* 1380 */         return new String[] { "", "", "" };
/*      */       }
/* 1382 */       String s = normalize(matcher.group(1));
/* 1383 */       String s1 = normalize(matcher.group(2));
/* 1384 */       String s2 = normalize(matcher.group(3));
/* 1385 */       return new String[] { s, s1, s2 };
/*      */     } 
/*      */     
/* 1388 */     return new String[] { "", "", "" };
/*      */   }
/*      */ 
/*      */   
/*      */   public static int intHash(int p_intHash_0_) {
/* 1393 */     p_intHash_0_ = p_intHash_0_ ^ 0x3D ^ p_intHash_0_ >> 16;
/* 1394 */     p_intHash_0_ += p_intHash_0_ << 3;
/* 1395 */     p_intHash_0_ ^= p_intHash_0_ >> 4;
/* 1396 */     p_intHash_0_ *= 668265261;
/* 1397 */     p_intHash_0_ ^= p_intHash_0_ >> 15;
/* 1398 */     return p_intHash_0_;
/*      */   }
/*      */   
/*      */   public static int getRandom(BlockPos p_getRandom_0_, int p_getRandom_1_) {
/* 1402 */     int i = intHash(p_getRandom_1_ + 37);
/* 1403 */     i = intHash(i + p_getRandom_0_.getX());
/* 1404 */     i = intHash(i + p_getRandom_0_.getZ());
/* 1405 */     i = intHash(i + p_getRandom_0_.getY());
/* 1406 */     return i;
/*      */   }
/*      */   
/*      */   public static int getAvailableProcessors() {
/* 1410 */     return availableProcessors;
/*      */   }
/*      */   
/*      */   public static void updateAvailableProcessors() {
/* 1414 */     availableProcessors = Runtime.getRuntime().availableProcessors();
/*      */   }
/*      */   
/*      */   public static boolean isSingleProcessor() {
/* 1418 */     return (getAvailableProcessors() <= 1);
/*      */   }
/*      */   
/*      */   public static boolean isSmoothWorld() {
/* 1422 */     return gameSettings.ofSmoothWorld;
/*      */   }
/*      */   
/*      */   public static boolean isLazyChunkLoading() {
/* 1426 */     return gameSettings.ofLazyChunkLoading;
/*      */   }
/*      */   
/*      */   public static boolean isDynamicFov() {
/* 1430 */     return gameSettings.ofDynamicFov;
/*      */   }
/*      */   
/*      */   public static boolean isAlternateBlocks() {
/* 1434 */     return gameSettings.ofAlternateBlocks;
/*      */   }
/*      */   
/*      */   public static int getChunkViewDistance() {
/* 1438 */     if (gameSettings == null) {
/* 1439 */       return 10;
/*      */     }
/* 1441 */     int i = gameSettings.renderDistanceChunks;
/* 1442 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean equals(Object p_equals_0_, Object p_equals_1_) {
/* 1447 */     return (p_equals_0_ == p_equals_1_) ? true : ((p_equals_0_ == null) ? false : p_equals_0_.equals(p_equals_1_));
/*      */   }
/*      */   
/*      */   public static boolean equalsOne(Object p_equalsOne_0_, Object[] p_equalsOne_1_) {
/* 1451 */     if (p_equalsOne_1_ == null) {
/* 1452 */       return false;
/*      */     }
/* 1454 */     for (int i = 0; i < p_equalsOne_1_.length; i++) {
/* 1455 */       Object object = p_equalsOne_1_[i];
/*      */       
/* 1457 */       if (equals(p_equalsOne_0_, object)) {
/* 1458 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1462 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean equalsOne(int p_equalsOne_0_, int[] p_equalsOne_1_) {
/* 1467 */     for (int i = 0; i < p_equalsOne_1_.length; i++) {
/* 1468 */       if (p_equalsOne_1_[i] == p_equalsOne_0_) {
/* 1469 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1473 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSameOne(Object p_isSameOne_0_, Object[] p_isSameOne_1_) {
/* 1477 */     if (p_isSameOne_1_ == null) {
/* 1478 */       return false;
/*      */     }
/* 1480 */     for (int i = 0; i < p_isSameOne_1_.length; i++) {
/* 1481 */       Object object = p_isSameOne_1_[i];
/*      */       
/* 1483 */       if (p_isSameOne_0_ == object) {
/* 1484 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1488 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String normalize(String p_normalize_0_) {
/* 1493 */     return (p_normalize_0_ == null) ? "" : p_normalize_0_;
/*      */   }
/*      */   
/*      */   public static void checkDisplaySettings() {
/* 1497 */     int i = getAntialiasingLevel();
/*      */     
/* 1499 */     if (i > 0) {
/* 1500 */       DisplayMode displaymode = Display.getDisplayMode();
/* 1501 */       dbg("FSAA Samples: " + i);
/*      */       
/*      */       try {
/* 1504 */         Display.destroy();
/* 1505 */         Display.setDisplayMode(displaymode);
/* 1506 */         Display.create((new PixelFormat()).withDepthBits(24).withSamples(i));
/* 1507 */         Display.setResizable(false);
/* 1508 */         Display.setResizable(true);
/* 1509 */       } catch (LWJGLException lwjglexception2) {
/* 1510 */         warn("Error setting FSAA: " + i + "x");
/* 1511 */         lwjglexception2.printStackTrace();
/*      */         
/*      */         try {
/* 1514 */           Display.setDisplayMode(displaymode);
/* 1515 */           Display.create((new PixelFormat()).withDepthBits(24));
/* 1516 */           Display.setResizable(false);
/* 1517 */           Display.setResizable(true);
/* 1518 */         } catch (LWJGLException lwjglexception1) {
/* 1519 */           lwjglexception1.printStackTrace();
/*      */           
/*      */           try {
/* 1522 */             Display.setDisplayMode(displaymode);
/* 1523 */             Display.create();
/* 1524 */             Display.setResizable(false);
/* 1525 */             Display.setResizable(true);
/* 1526 */           } catch (LWJGLException lwjglexception) {
/* 1527 */             lwjglexception.printStackTrace();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1532 */       if (!Minecraft.isRunningOnMac && getDefaultResourcePack() != null) {
/* 1533 */         InputStream inputstream = null;
/* 1534 */         InputStream inputstream1 = null;
/*      */         
/*      */         try {
/* 1537 */           inputstream = getDefaultResourcePack().getInputStreamAssets(new ResourceLocation("icons/icon_16x16.png"));
/* 1538 */           inputstream1 = getDefaultResourcePack().getInputStreamAssets(new ResourceLocation("icons/icon_32x32.png"));
/*      */           
/* 1540 */           if (inputstream != null && inputstream1 != null) {
/* 1541 */             Display.setIcon(new ByteBuffer[] { readIconImage(inputstream), readIconImage(inputstream1) });
/*      */           }
/* 1543 */         } catch (IOException ioexception) {
/* 1544 */           warn("Error setting window icon: " + ioexception.getClass().getName() + ": " + ioexception.getMessage());
/*      */         } finally {
/* 1546 */           IOUtils.closeQuietly(inputstream);
/* 1547 */           IOUtils.closeQuietly(inputstream1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static ByteBuffer readIconImage(InputStream p_readIconImage_0_) throws IOException {
/* 1554 */     BufferedImage bufferedimage = ImageIO.read(p_readIconImage_0_);
/* 1555 */     int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
/* 1556 */     ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
/*      */     
/* 1558 */     for (int i : aint) {
/* 1559 */       bytebuffer.putInt(i << 8 | i >> 24 & 0xFF);
/*      */     }
/*      */     
/* 1562 */     bytebuffer.flip();
/* 1563 */     return bytebuffer;
/*      */   }
/*      */   
/*      */   public static void checkDisplayMode() {
/*      */     try {
/* 1568 */       if (minecraft.isFullScreen()) {
/* 1569 */         if (fullscreenModeChecked) {
/*      */           return;
/*      */         }
/*      */         
/* 1573 */         fullscreenModeChecked = true;
/* 1574 */         desktopModeChecked = false;
/* 1575 */         DisplayMode displaymode = Display.getDisplayMode();
/* 1576 */         Dimension dimension = getFullscreenDimension();
/*      */         
/* 1578 */         if (dimension == null) {
/*      */           return;
/*      */         }
/*      */         
/* 1582 */         if (displaymode.getWidth() == dimension.width && displaymode.getHeight() == dimension.height) {
/*      */           return;
/*      */         }
/*      */         
/* 1586 */         DisplayMode displaymode1 = getDisplayMode(dimension);
/*      */         
/* 1588 */         if (displaymode1 == null) {
/*      */           return;
/*      */         }
/*      */         
/* 1592 */         Display.setDisplayMode(displaymode1);
/* 1593 */         minecraft.displayWidth = Display.getDisplayMode().getWidth();
/* 1594 */         minecraft.displayHeight = Display.getDisplayMode().getHeight();
/*      */         
/* 1596 */         if (minecraft.displayWidth <= 0) {
/* 1597 */           minecraft.displayWidth = 1;
/*      */         }
/*      */         
/* 1600 */         if (minecraft.displayHeight <= 0) {
/* 1601 */           minecraft.displayHeight = 1;
/*      */         }
/*      */         
/* 1604 */         if (minecraft.currentScreen != null) {
/* 1605 */           ScaledResolution scaledresolution = new ScaledResolution(minecraft);
/* 1606 */           int i = scaledresolution.getScaledWidth();
/* 1607 */           int j = scaledresolution.getScaledHeight();
/* 1608 */           minecraft.currentScreen.setWorldAndResolution(minecraft, i, j);
/*      */         } 
/*      */         
/* 1611 */         updateFramebufferSize();
/* 1612 */         Display.setFullscreen(true);
/* 1613 */         minecraft.gameSettings.updateVSync();
/* 1614 */         GlStateManager.enableTexture2D();
/*      */       } else {
/* 1616 */         if (desktopModeChecked) {
/*      */           return;
/*      */         }
/*      */         
/* 1620 */         desktopModeChecked = true;
/* 1621 */         fullscreenModeChecked = false;
/* 1622 */         minecraft.gameSettings.updateVSync();
/* 1623 */         Display.update();
/* 1624 */         GlStateManager.enableTexture2D();
/* 1625 */         Display.setResizable(false);
/* 1626 */         Display.setResizable(true);
/*      */       } 
/* 1628 */     } catch (Exception exception) {
/* 1629 */       exception.printStackTrace();
/* 1630 */       gameSettings.ofFullscreenMode = "Default";
/* 1631 */       gameSettings.saveOfOptions();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void updateFramebufferSize() {
/* 1636 */     minecraft.getFramebuffer().createBindFramebuffer(minecraft.displayWidth, minecraft.displayHeight);
/*      */     
/* 1638 */     if (minecraft.entityRenderer != null) {
/* 1639 */       minecraft.entityRenderer.updateShaderGroupSize(minecraft.displayWidth, minecraft.displayHeight);
/*      */     }
/*      */     
/* 1642 */     minecraft.loadingScreen = new LoadingScreenRenderer(minecraft);
/*      */   }
/*      */   
/*      */   public static Object[] addObjectToArray(Object[] p_addObjectToArray_0_, Object p_addObjectToArray_1_) {
/* 1646 */     if (p_addObjectToArray_0_ == null) {
/* 1647 */       throw new NullPointerException("The given array is NULL");
/*      */     }
/* 1649 */     int i = p_addObjectToArray_0_.length;
/* 1650 */     int j = i + 1;
/* 1651 */     Object[] aobject = (Object[])Array.newInstance(p_addObjectToArray_0_.getClass().getComponentType(), j);
/* 1652 */     System.arraycopy(p_addObjectToArray_0_, 0, aobject, 0, i);
/* 1653 */     aobject[i] = p_addObjectToArray_1_;
/* 1654 */     return aobject;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Object[] addObjectToArray(Object[] p_addObjectToArray_0_, Object p_addObjectToArray_1_, int p_addObjectToArray_2_) {
/* 1659 */     List<Object> list = new ArrayList(Arrays.asList(p_addObjectToArray_0_));
/* 1660 */     list.add(p_addObjectToArray_2_, p_addObjectToArray_1_);
/* 1661 */     Object[] aobject = (Object[])Array.newInstance(p_addObjectToArray_0_.getClass().getComponentType(), list.size());
/* 1662 */     return list.toArray(aobject);
/*      */   }
/*      */   
/*      */   public static Object[] addObjectsToArray(Object[] p_addObjectsToArray_0_, Object[] p_addObjectsToArray_1_) {
/* 1666 */     if (p_addObjectsToArray_0_ == null)
/* 1667 */       throw new NullPointerException("The given array is NULL"); 
/* 1668 */     if (p_addObjectsToArray_1_.length == 0) {
/* 1669 */       return p_addObjectsToArray_0_;
/*      */     }
/* 1671 */     int i = p_addObjectsToArray_0_.length;
/* 1672 */     int j = i + p_addObjectsToArray_1_.length;
/* 1673 */     Object[] aobject = (Object[])Array.newInstance(p_addObjectsToArray_0_.getClass().getComponentType(), j);
/* 1674 */     System.arraycopy(p_addObjectsToArray_0_, 0, aobject, 0, i);
/* 1675 */     System.arraycopy(p_addObjectsToArray_1_, 0, aobject, i, p_addObjectsToArray_1_.length);
/* 1676 */     return aobject;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Object[] removeObjectFromArray(Object[] p_removeObjectFromArray_0_, Object p_removeObjectFromArray_1_) {
/* 1681 */     List list = new ArrayList(Arrays.asList(p_removeObjectFromArray_0_));
/* 1682 */     list.remove(p_removeObjectFromArray_1_);
/* 1683 */     Object[] aobject = collectionToArray(list, p_removeObjectFromArray_0_.getClass().getComponentType());
/* 1684 */     return aobject;
/*      */   }
/*      */   
/*      */   public static Object[] collectionToArray(Collection p_collectionToArray_0_, Class<?> p_collectionToArray_1_) {
/* 1688 */     if (p_collectionToArray_0_ == null)
/* 1689 */       return null; 
/* 1690 */     if (p_collectionToArray_1_ == null)
/* 1691 */       return null; 
/* 1692 */     if (p_collectionToArray_1_.isPrimitive()) {
/* 1693 */       throw new IllegalArgumentException("Can not make arrays with primitive elements (int, double), element class: " + p_collectionToArray_1_);
/*      */     }
/* 1695 */     Object[] aobject = (Object[])Array.newInstance(p_collectionToArray_1_, p_collectionToArray_0_.size());
/* 1696 */     return p_collectionToArray_0_.toArray(aobject);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isCustomItems() {
/* 1701 */     return gameSettings.ofCustomItems;
/*      */   }
/*      */   
/*      */   public static void drawFps() {
/* 1705 */     int i = Minecraft.getDebugFPS();
/* 1706 */     String s = getUpdates(minecraft.debug);
/* 1707 */     int j = minecraft.renderGlobal.getCountActiveRenderers();
/* 1708 */     int k = minecraft.renderGlobal.getCountEntitiesRendered();
/* 1709 */     int l = minecraft.renderGlobal.getCountTileEntitiesRendered();
/* 1710 */     String s1 = "" + i + "/" + getFpsMin() + " fps, C: " + j + ", E: " + k + "+" + l + ", U: " + s;
/* 1711 */     minecraft.MCfontRenderer.drawString(s1, 2, 2, -2039584);
/*      */   }
/*      */   
/*      */   public static int getFpsMin() {
/* 1715 */     if (minecraft.debug == mcDebugLast) {
/* 1716 */       return fpsMinLast;
/*      */     }
/* 1718 */     mcDebugLast = minecraft.debug;
/* 1719 */     FrameTimer frametimer = minecraft.func_181539_aj();
/* 1720 */     long[] along = frametimer.func_181746_c();
/* 1721 */     int i = frametimer.func_181750_b();
/* 1722 */     int j = frametimer.func_181749_a();
/*      */     
/* 1724 */     if (i == j) {
/* 1725 */       return fpsMinLast;
/*      */     }
/* 1727 */     int k = Minecraft.getDebugFPS();
/*      */     
/* 1729 */     if (k <= 0) {
/* 1730 */       k = 1;
/*      */     }
/*      */     
/* 1733 */     long l = (long)(1.0D / k * 1.0E9D);
/* 1734 */     long i1 = l;
/* 1735 */     long j1 = 0L;
/*      */     int k1;
/* 1737 */     for (k1 = MathHelper.normalizeAngle(i - 1, along.length); k1 != j && j1 < 1.0E9D; k1 = MathHelper.normalizeAngle(k1 - 1, along.length)) {
/* 1738 */       long l1 = along[k1];
/*      */       
/* 1740 */       if (l1 > i1) {
/* 1741 */         i1 = l1;
/*      */       }
/*      */       
/* 1744 */       j1 += l1;
/*      */     } 
/*      */     
/* 1747 */     double d0 = i1 / 1.0E9D;
/* 1748 */     fpsMinLast = (int)(1.0D / d0);
/* 1749 */     return fpsMinLast;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getUpdates(String p_getUpdates_0_) {
/* 1755 */     int i = p_getUpdates_0_.indexOf('(');
/*      */     
/* 1757 */     if (i < 0) {
/* 1758 */       return "";
/*      */     }
/* 1760 */     int j = p_getUpdates_0_.indexOf(' ', i);
/* 1761 */     return (j < 0) ? "" : p_getUpdates_0_.substring(i + 1, j);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getBitsOs() {
/* 1766 */     String s = System.getenv("ProgramFiles(X86)");
/* 1767 */     return (s != null) ? 64 : 32;
/*      */   }
/*      */   
/*      */   public static int getBitsJre() {
/* 1771 */     String[] astring = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
/*      */     
/* 1773 */     for (int i = 0; i < astring.length; i++) {
/* 1774 */       String s = astring[i];
/* 1775 */       String s1 = System.getProperty(s);
/*      */       
/* 1777 */       if (s1 != null && s1.contains("64")) {
/* 1778 */         return 64;
/*      */       }
/*      */     } 
/*      */     
/* 1782 */     return 32;
/*      */   }
/*      */   
/*      */   public static boolean isNotify64BitJava() {
/* 1786 */     return notify64BitJava;
/*      */   }
/*      */   
/*      */   public static void setNotify64BitJava(boolean p_setNotify64BitJava_0_) {
/* 1790 */     notify64BitJava = p_setNotify64BitJava_0_;
/*      */   }
/*      */   
/*      */   public static boolean isConnectedModels() {
/* 1794 */     return false;
/*      */   }
/*      */   
/*      */   public static void showGuiMessage(String p_showGuiMessage_0_, String p_showGuiMessage_1_) {
/* 1798 */     GuiMessage guimessage = new GuiMessage(minecraft.currentScreen, p_showGuiMessage_0_, p_showGuiMessage_1_);
/* 1799 */     minecraft.displayGuiScreen((GuiScreen)guimessage);
/*      */   }
/*      */   
/*      */   public static int[] addIntToArray(int[] p_addIntToArray_0_, int p_addIntToArray_1_) {
/* 1803 */     return addIntsToArray(p_addIntToArray_0_, new int[] { p_addIntToArray_1_ });
/*      */   }
/*      */   
/*      */   public static int[] addIntsToArray(int[] p_addIntsToArray_0_, int[] p_addIntsToArray_1_) {
/* 1807 */     if (p_addIntsToArray_0_ != null && p_addIntsToArray_1_ != null) {
/* 1808 */       int i = p_addIntsToArray_0_.length;
/* 1809 */       int j = i + p_addIntsToArray_1_.length;
/* 1810 */       int[] aint = new int[j];
/* 1811 */       System.arraycopy(p_addIntsToArray_0_, 0, aint, 0, i);
/*      */       
/* 1813 */       for (int k = 0; k < p_addIntsToArray_1_.length; k++) {
/* 1814 */         aint[k + i] = p_addIntsToArray_1_[k];
/*      */       }
/*      */       
/* 1817 */       return aint;
/*      */     } 
/* 1819 */     throw new NullPointerException("The given array is NULL");
/*      */   }
/*      */ 
/*      */   
/*      */   public static DynamicTexture getMojangLogoTexture(DynamicTexture p_getMojangLogoTexture_0_) {
/*      */     try {
/* 1825 */       ResourceLocation resourcelocation = new ResourceLocation("textures/gui/title/mojang.png");
/* 1826 */       InputStream inputstream = getResourceStream(resourcelocation);
/*      */       
/* 1828 */       if (inputstream == null) {
/* 1829 */         return p_getMojangLogoTexture_0_;
/*      */       }
/* 1831 */       BufferedImage bufferedimage = ImageIO.read(inputstream);
/*      */       
/* 1833 */       if (bufferedimage == null) {
/* 1834 */         return p_getMojangLogoTexture_0_;
/*      */       }
/* 1836 */       DynamicTexture dynamictexture = new DynamicTexture(bufferedimage);
/* 1837 */       return dynamictexture;
/*      */     
/*      */     }
/* 1840 */     catch (Exception exception) {
/* 1841 */       warn(exception.getClass().getName() + ": " + exception.getMessage());
/* 1842 */       return p_getMojangLogoTexture_0_;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void writeFile(File p_writeFile_0_, String p_writeFile_1_) throws IOException {
/* 1847 */     FileOutputStream fileoutputstream = new FileOutputStream(p_writeFile_0_);
/* 1848 */     byte[] abyte = p_writeFile_1_.getBytes(StandardCharsets.US_ASCII);
/* 1849 */     fileoutputstream.write(abyte);
/* 1850 */     fileoutputstream.close();
/*      */   }
/*      */   
/*      */   public static TextureMap getTextureMap() {
/* 1854 */     return getMinecraft().getTextureMapBlocks();
/*      */   }
/*      */   
/*      */   public static boolean isDynamicLights() {
/* 1858 */     return (gameSettings.ofDynamicLights != 3);
/*      */   }
/*      */   
/*      */   public static boolean isDynamicLightsFast() {
/* 1862 */     return (gameSettings.ofDynamicLights == 1);
/*      */   }
/*      */   
/*      */   public static boolean isDynamicHandLight() {
/* 1866 */     return !isDynamicLights() ? false : (isShaders() ? Shaders.isDynamicHandLight() : true);
/*      */   }
/*      */   
/*      */   public static boolean isCustomEntityModels() {
/* 1870 */     return gameSettings.ofCustomEntityModels;
/*      */   }
/*      */   
/*      */   public static boolean isCustomGuis() {
/* 1874 */     return gameSettings.ofCustomGuis;
/*      */   }
/*      */   
/*      */   public static int getScreenshotSize() {
/* 1878 */     return gameSettings.ofScreenshotSize;
/*      */   }
/*      */   
/*      */   public static int[] toPrimitive(Integer[] p_toPrimitive_0_) {
/* 1882 */     if (p_toPrimitive_0_ == null)
/* 1883 */       return null; 
/* 1884 */     if (p_toPrimitive_0_.length == 0) {
/* 1885 */       return new int[0];
/*      */     }
/* 1887 */     int[] aint = new int[p_toPrimitive_0_.length];
/*      */     
/* 1889 */     for (int i = 0; i < aint.length; i++) {
/* 1890 */       aint[i] = p_toPrimitive_0_[i].intValue();
/*      */     }
/*      */     
/* 1893 */     return aint;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRenderRegions() {
/* 1898 */     return gameSettings.ofRenderRegions;
/*      */   }
/*      */   
/*      */   public static boolean isVbo() {
/* 1902 */     return OpenGlHelper.useVbo();
/*      */   }
/*      */   
/*      */   public static boolean isSmoothFps() {
/* 1906 */     return gameSettings.ofSmoothFps;
/*      */   }
/*      */   
/*      */   public static boolean openWebLink(URI p_openWebLink_0_) {
/*      */     try {
/* 1911 */       Desktop.getDesktop().browse(p_openWebLink_0_);
/* 1912 */       return true;
/* 1913 */     } catch (Exception exception) {
/* 1914 */       warn("Error opening link: " + p_openWebLink_0_);
/* 1915 */       warn(exception.getClass().getName() + ": " + exception.getMessage());
/* 1916 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean isShowGlErrors() {
/* 1921 */     return gameSettings.ofShowGlErrors;
/*      */   }
/*      */   
/*      */   public static String arrayToString(boolean[] p_arrayToString_0_, String p_arrayToString_1_) {
/* 1925 */     if (p_arrayToString_0_ == null) {
/* 1926 */       return "";
/*      */     }
/* 1928 */     StringBuffer stringbuffer = new StringBuffer(p_arrayToString_0_.length * 5);
/*      */     
/* 1930 */     for (int i = 0; i < p_arrayToString_0_.length; i++) {
/* 1931 */       boolean flag = p_arrayToString_0_[i];
/*      */       
/* 1933 */       if (i > 0) {
/* 1934 */         stringbuffer.append(p_arrayToString_1_);
/*      */       }
/*      */       
/* 1937 */       stringbuffer.append(flag);
/*      */     } 
/*      */     
/* 1940 */     return stringbuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isIntegratedServerRunning() {
/* 1945 */     return (minecraft.getIntegratedServer() == null) ? false : minecraft.isIntegratedServerRunning();
/*      */   }
/*      */   
/*      */   public static IntBuffer createDirectIntBuffer(int p_createDirectIntBuffer_0_) {
/* 1949 */     return GLAllocation.createDirectByteBuffer(p_createDirectIntBuffer_0_ << 2).asIntBuffer();
/*      */   }
/*      */   
/*      */   public static String getGlErrorString(int p_getGlErrorString_0_) {
/* 1953 */     switch (p_getGlErrorString_0_) {
/*      */       case 0:
/* 1955 */         return "No error";
/*      */       
/*      */       case 1280:
/* 1958 */         return "Invalid enum";
/*      */       
/*      */       case 1281:
/* 1961 */         return "Invalid value";
/*      */       
/*      */       case 1282:
/* 1964 */         return "Invalid operation";
/*      */       
/*      */       case 1283:
/* 1967 */         return "Stack overflow";
/*      */       
/*      */       case 1284:
/* 1970 */         return "Stack underflow";
/*      */       
/*      */       case 1285:
/* 1973 */         return "Out of memory";
/*      */       
/*      */       case 1286:
/* 1976 */         return "Invalid framebuffer operation";
/*      */     } 
/*      */     
/* 1979 */     return "Unknown";
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isTrue(Boolean p_isTrue_0_) {
/* 1984 */     return (p_isTrue_0_ != null && p_isTrue_0_.booleanValue());
/*      */   }
/*      */   
/*      */   public static boolean isQuadsToTriangles() {
/* 1988 */     return !isShaders() ? false : (!Shaders.canRenderQuads());
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\src\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
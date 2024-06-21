/*     */ package com.viaversion.viaversion;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.ViaManager;
/*     */ import com.viaversion.viaversion.api.command.ViaVersionCommand;
/*     */ import com.viaversion.viaversion.api.connection.ConnectionManager;
/*     */ import com.viaversion.viaversion.api.debug.DebugHandler;
/*     */ import com.viaversion.viaversion.api.platform.PlatformTask;
/*     */ import com.viaversion.viaversion.api.platform.UnsupportedSoftware;
/*     */ import com.viaversion.viaversion.api.platform.ViaInjector;
/*     */ import com.viaversion.viaversion.api.platform.ViaPlatform;
/*     */ import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
/*     */ import com.viaversion.viaversion.api.platform.providers.ViaProviders;
/*     */ import com.viaversion.viaversion.api.protocol.ProtocolManager;
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
/*     */ import com.viaversion.viaversion.api.scheduler.Scheduler;
/*     */ import com.viaversion.viaversion.commands.ViaCommandHandler;
/*     */ import com.viaversion.viaversion.connection.ConnectionManagerImpl;
/*     */ import com.viaversion.viaversion.debug.DebugHandlerImpl;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
/*     */ import com.viaversion.viaversion.protocol.ProtocolManagerImpl;
/*     */ import com.viaversion.viaversion.protocol.ServerProtocolVersionRange;
/*     */ import com.viaversion.viaversion.protocol.ServerProtocolVersionSingleton;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.TabCompleteThread;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.ViaIdleThread;
/*     */ import com.viaversion.viaversion.scheduler.TaskScheduler;
/*     */ import com.viaversion.viaversion.update.UpdateUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ViaManagerImpl
/*     */   implements ViaManager
/*     */ {
/*  54 */   private final ProtocolManagerImpl protocolManager = new ProtocolManagerImpl();
/*  55 */   private final ConnectionManager connectionManager = (ConnectionManager)new ConnectionManagerImpl();
/*  56 */   private final DebugHandler debugHandler = (DebugHandler)new DebugHandlerImpl();
/*  57 */   private final ViaProviders providers = new ViaProviders();
/*  58 */   private final Scheduler scheduler = (Scheduler)new TaskScheduler();
/*     */   private final ViaPlatform<?> platform;
/*     */   private final ViaInjector injector;
/*     */   private final ViaCommandHandler commandHandler;
/*     */   private final ViaPlatformLoader loader;
/*  63 */   private final Set<String> subPlatforms = new HashSet<>();
/*  64 */   private List<Runnable> enableListeners = new ArrayList<>();
/*     */   private PlatformTask<?> mappingLoadingTask;
/*     */   private boolean initialized;
/*     */   
/*     */   public ViaManagerImpl(ViaPlatform<?> platform, ViaInjector injector, ViaCommandHandler commandHandler, ViaPlatformLoader loader) {
/*  69 */     this.platform = platform;
/*  70 */     this.injector = injector;
/*  71 */     this.commandHandler = commandHandler;
/*  72 */     this.loader = loader;
/*     */   }
/*     */   
/*     */   public static ViaManagerBuilder builder() {
/*  76 */     return new ViaManagerBuilder();
/*     */   }
/*     */   
/*     */   public void init() {
/*  80 */     if (System.getProperty("ViaVersion") != null)
/*     */     {
/*  82 */       this.platform.onReload();
/*     */     }
/*     */ 
/*     */     
/*  86 */     if (!this.injector.lateProtocolVersionSetting()) {
/*  87 */       loadServerProtocol();
/*     */     }
/*     */ 
/*     */     
/*  91 */     this.protocolManager.registerProtocols();
/*     */ 
/*     */     
/*     */     try {
/*  95 */       this.injector.inject();
/*  96 */     } catch (Exception e) {
/*  97 */       this.platform.getLogger().severe("ViaVersion failed to inject:");
/*  98 */       e.printStackTrace();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 103 */     System.setProperty("ViaVersion", this.platform.getPluginVersion());
/*     */     
/* 105 */     for (Runnable listener : this.enableListeners) {
/* 106 */       listener.run();
/*     */     }
/* 108 */     this.enableListeners = null;
/*     */     
/* 110 */     this.initialized = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onServerLoaded() {
/* 115 */     if (this.platform.getConf().isCheckForUpdates()) {
/* 116 */       UpdateUtil.sendUpdateMessage();
/*     */     }
/*     */     
/* 119 */     if (!this.protocolManager.getServerProtocolVersion().isKnown())
/*     */     {
/* 121 */       loadServerProtocol();
/*     */     }
/*     */ 
/*     */     
/* 125 */     ServerProtocolVersion protocolVersion = this.protocolManager.getServerProtocolVersion();
/* 126 */     if (protocolVersion.isKnown()) {
/* 127 */       if (this.platform.isProxy()) {
/* 128 */         this.platform.getLogger().info("ViaVersion detected lowest supported version by the proxy: " + ProtocolVersion.getProtocol(protocolVersion.lowestSupportedVersion()));
/* 129 */         this.platform.getLogger().info("Highest supported version by the proxy: " + ProtocolVersion.getProtocol(protocolVersion.highestSupportedVersion()));
/* 130 */         if (this.debugHandler.enabled()) {
/* 131 */           this.platform.getLogger().info("Supported version range: " + Arrays.toString(protocolVersion.supportedVersions().toArray(new int[0])));
/*     */         }
/*     */       } else {
/* 134 */         this.platform.getLogger().info("ViaVersion detected server version: " + ProtocolVersion.getProtocol(protocolVersion.highestSupportedVersion()));
/*     */       } 
/*     */       
/* 137 */       if (!this.protocolManager.isWorkingPipe()) {
/* 138 */         this.platform.getLogger().warning("ViaVersion does not have any compatible versions for this server version!");
/* 139 */         this.platform.getLogger().warning("Please remember that ViaVersion only adds support for versions newer than the server version.");
/* 140 */         this.platform.getLogger().warning("If you need support for older versions you may need to use one or more ViaVersion addons too.");
/* 141 */         this.platform.getLogger().warning("In that case please read the ViaVersion resource page carefully or use https://jo0001.github.io/ViaSetup");
/* 142 */         this.platform.getLogger().warning("and if you're still unsure, feel free to join our Discord-Server for further assistance.");
/* 143 */       } else if (protocolVersion.highestSupportedVersion() <= ProtocolVersion.v1_12_2.getVersion()) {
/* 144 */         this.platform.getLogger().warning("This version of Minecraft is extremely outdated and support for it has reached its end of life. You will still be able to run Via on this Minecraft version, but we are unlikely to provide any further fixes or help with problems specific to legacy Minecraft versions. Please consider updating to give your players a better experience and to avoid issues that have long been fixed.");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 150 */     checkJavaVersion();
/*     */ 
/*     */     
/* 153 */     unsupportedSoftwareWarning();
/*     */ 
/*     */     
/* 156 */     this.loader.load();
/*     */     
/* 158 */     this.mappingLoadingTask = Via.getPlatform().runRepeatingAsync(() -> { if (this.protocolManager.checkForMappingCompletion() && this.mappingLoadingTask != null) { this.mappingLoadingTask.cancel(); this.mappingLoadingTask = null; }  }10L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 165 */     int serverProtocolVersion = this.protocolManager.getServerProtocolVersion().lowestSupportedVersion();
/* 166 */     if (serverProtocolVersion < ProtocolVersion.v1_9.getVersion() && 
/* 167 */       Via.getConfig().isSimulatePlayerTick()) {
/* 168 */       Via.getPlatform().runRepeatingSync((Runnable)new ViaIdleThread(), 1L);
/*     */     }
/*     */     
/* 171 */     if (serverProtocolVersion < ProtocolVersion.v1_13.getVersion() && 
/* 172 */       Via.getConfig().get1_13TabCompleteDelay() > 0) {
/* 173 */       Via.getPlatform().runRepeatingSync((Runnable)new TabCompleteThread(), 1L);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 178 */     this.protocolManager.refreshVersions();
/*     */   }
/*     */   private void loadServerProtocol() {
/*     */     try {
/*     */       ServerProtocolVersionSingleton serverProtocolVersionSingleton;
/* 183 */       ProtocolVersion serverProtocolVersion = ProtocolVersion.getProtocol(this.injector.getServerProtocolVersion());
/*     */       
/* 185 */       if (this.platform.isProxy()) {
/* 186 */         IntSortedSet supportedVersions = this.injector.getServerProtocolVersions();
/* 187 */         ServerProtocolVersionRange serverProtocolVersionRange = new ServerProtocolVersionRange(supportedVersions.firstInt(), supportedVersions.lastInt(), supportedVersions);
/*     */       } else {
/* 189 */         serverProtocolVersionSingleton = new ServerProtocolVersionSingleton(serverProtocolVersion.getVersion());
/*     */       } 
/*     */       
/* 192 */       this.protocolManager.setServerProtocol((ServerProtocolVersion)serverProtocolVersionSingleton);
/* 193 */     } catch (Exception e) {
/* 194 */       this.platform.getLogger().severe("ViaVersion failed to get the server protocol!");
/* 195 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 201 */     this.platform.getLogger().info("ViaVersion is disabling, if this is a reload and you experience issues consider rebooting.");
/*     */     try {
/* 203 */       this.injector.uninject();
/* 204 */     } catch (Exception e) {
/* 205 */       this.platform.getLogger().severe("ViaVersion failed to uninject:");
/* 206 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 209 */     this.loader.unload();
/* 210 */     this.scheduler.shutdown();
/*     */   }
/*     */   private void checkJavaVersion() {
/*     */     int version;
/* 214 */     String javaVersion = System.getProperty("java.version");
/* 215 */     Matcher matcher = Pattern.compile("(?:1\\.)?(\\d+)").matcher(javaVersion);
/* 216 */     if (!matcher.find()) {
/* 217 */       this.platform.getLogger().warning("Failed to determine Java version; could not parse: " + javaVersion);
/*     */       
/*     */       return;
/*     */     } 
/* 221 */     String versionString = matcher.group(1);
/*     */     
/*     */     try {
/* 224 */       version = Integer.parseInt(versionString);
/* 225 */     } catch (NumberFormatException e) {
/* 226 */       this.platform.getLogger().warning("Failed to determine Java version; could not parse: " + versionString);
/* 227 */       e.printStackTrace();
/*     */       
/*     */       return;
/*     */     } 
/* 231 */     if (version < 17) {
/* 232 */       this.platform.getLogger().warning("You are running an outdated Java version, please consider updating it to at least Java 17 (your version is " + javaVersion + "). At some point in the future, ViaVersion will no longer be compatible with this version of Java.");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void unsupportedSoftwareWarning() {
/* 238 */     boolean found = false;
/* 239 */     for (UnsupportedSoftware software : this.platform.getUnsupportedSoftwareClasses()) {
/* 240 */       String match = software.match();
/* 241 */       if (match == null) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 246 */       if (!found) {
/* 247 */         this.platform.getLogger().severe("************************************************");
/* 248 */         this.platform.getLogger().severe("You are using unsupported software and may encounter unforeseeable issues.");
/* 249 */         this.platform.getLogger().severe("");
/* 250 */         found = true;
/*     */       } 
/*     */       
/* 253 */       this.platform.getLogger().severe("We strongly advise against using " + match + ":");
/* 254 */       this.platform.getLogger().severe(software.getReason());
/* 255 */       this.platform.getLogger().severe("");
/*     */     } 
/*     */     
/* 258 */     if (found) {
/* 259 */       this.platform.getLogger().severe("We will not provide support in case you encounter issues possibly related to this software.");
/* 260 */       this.platform.getLogger().severe("************************************************");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ViaPlatform<?> getPlatform() {
/* 266 */     return this.platform;
/*     */   }
/*     */ 
/*     */   
/*     */   public ConnectionManager getConnectionManager() {
/* 271 */     return this.connectionManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public ProtocolManager getProtocolManager() {
/* 276 */     return (ProtocolManager)this.protocolManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public ViaProviders getProviders() {
/* 281 */     return this.providers;
/*     */   }
/*     */ 
/*     */   
/*     */   public DebugHandler debugHandler() {
/* 286 */     return this.debugHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public ViaInjector getInjector() {
/* 291 */     return this.injector;
/*     */   }
/*     */ 
/*     */   
/*     */   public ViaCommandHandler getCommandHandler() {
/* 296 */     return this.commandHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public ViaPlatformLoader getLoader() {
/* 301 */     return this.loader;
/*     */   }
/*     */ 
/*     */   
/*     */   public Scheduler getScheduler() {
/* 306 */     return this.scheduler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getSubPlatforms() {
/* 316 */     return this.subPlatforms;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEnableListener(Runnable runnable) {
/* 325 */     this.enableListeners.add(runnable);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInitialized() {
/* 330 */     return this.initialized;
/*     */   }
/*     */   
/*     */   public static final class ViaManagerBuilder {
/*     */     private ViaPlatform<?> platform;
/*     */     private ViaInjector injector;
/*     */     private ViaCommandHandler commandHandler;
/*     */     private ViaPlatformLoader loader;
/*     */     
/*     */     public ViaManagerBuilder platform(ViaPlatform<?> platform) {
/* 340 */       this.platform = platform;
/* 341 */       return this;
/*     */     }
/*     */     
/*     */     public ViaManagerBuilder injector(ViaInjector injector) {
/* 345 */       this.injector = injector;
/* 346 */       return this;
/*     */     }
/*     */     
/*     */     public ViaManagerBuilder loader(ViaPlatformLoader loader) {
/* 350 */       this.loader = loader;
/* 351 */       return this;
/*     */     }
/*     */     
/*     */     public ViaManagerBuilder commandHandler(ViaCommandHandler commandHandler) {
/* 355 */       this.commandHandler = commandHandler;
/* 356 */       return this;
/*     */     }
/*     */     
/*     */     public ViaManagerImpl build() {
/* 360 */       return new ViaManagerImpl(this.platform, this.injector, this.commandHandler, this.loader);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\ViaManagerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
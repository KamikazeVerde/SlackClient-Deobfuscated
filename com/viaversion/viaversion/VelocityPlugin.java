/*     */ package com.viaversion.viaversion;
/*     */ 
/*     */ import com.google.inject.Inject;
/*     */ import com.velocitypowered.api.command.Command;
/*     */ import com.velocitypowered.api.event.PostOrder;
/*     */ import com.velocitypowered.api.event.Subscribe;
/*     */ import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
/*     */ import com.velocitypowered.api.plugin.Plugin;
/*     */ import com.velocitypowered.api.plugin.PluginContainer;
/*     */ import com.velocitypowered.api.plugin.annotation.DataDirectory;
/*     */ import com.velocitypowered.api.proxy.Player;
/*     */ import com.velocitypowered.api.proxy.ProxyServer;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.ViaAPI;
/*     */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*     */ import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
/*     */ import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
/*     */ import com.viaversion.viaversion.api.platform.PlatformTask;
/*     */ import com.viaversion.viaversion.api.platform.ProtocolDetectorService;
/*     */ import com.viaversion.viaversion.api.platform.ViaInjector;
/*     */ import com.viaversion.viaversion.api.platform.ViaPlatform;
/*     */ import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
/*     */ import com.viaversion.viaversion.api.platform.ViaServerProxyPlatform;
/*     */ import com.viaversion.viaversion.commands.ViaCommandHandler;
/*     */ import com.viaversion.viaversion.dump.PluginInfo;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.util.GsonUtil;
/*     */ import com.viaversion.viaversion.velocity.command.VelocityCommandHandler;
/*     */ import com.viaversion.viaversion.velocity.platform.VelocityViaAPI;
/*     */ import com.viaversion.viaversion.velocity.platform.VelocityViaConfig;
/*     */ import com.viaversion.viaversion.velocity.platform.VelocityViaInjector;
/*     */ import com.viaversion.viaversion.velocity.platform.VelocityViaLoader;
/*     */ import com.viaversion.viaversion.velocity.platform.VelocityViaTask;
/*     */ import com.viaversion.viaversion.velocity.service.ProtocolDetectorService;
/*     */ import com.viaversion.viaversion.velocity.util.LoggerWrapper;
/*     */ import java.io.File;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.Function;
/*     */ import java.util.logging.Logger;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
/*     */ import org.slf4j.Logger;
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
/*     */ @Plugin(id = "viaversion", name = "ViaVersion", version = "4.7.0-1.20-pre5-SNAPSHOT", authors = {"_MylesC", "creeper123123321", "Gerrygames", "kennytv", "Matsv"}, description = "Allow newer Minecraft versions to connect to an older server version.", url = "https://viaversion.com")
/*     */ public class VelocityPlugin
/*     */   implements ViaServerProxyPlatform<Player>
/*     */ {
/*  66 */   public static final LegacyComponentSerializer COMPONENT_SERIALIZER = LegacyComponentSerializer.builder().character('§').extractUrls().build();
/*     */   
/*     */   public static ProxyServer PROXY;
/*     */   
/*     */   @Inject
/*     */   private ProxyServer proxy;
/*     */   @Inject
/*     */   private Logger loggerslf4j;
/*     */   @Inject
/*     */   @DataDirectory
/*     */   private Path configDir;
/*  77 */   private final ProtocolDetectorService protocolDetectorService = new ProtocolDetectorService();
/*     */   private VelocityViaAPI api;
/*     */   private Logger logger;
/*     */   private VelocityViaConfig conf;
/*     */   
/*     */   @Subscribe
/*     */   public void onProxyInit(ProxyInitializeEvent e) {
/*  84 */     if (!hasConnectionEvent()) {
/*     */       
/*  86 */       Logger logger = this.loggerslf4j;
/*  87 */       logger.error("      / \\");
/*  88 */       logger.error("     /   \\");
/*  89 */       logger.error("    /  |  \\");
/*  90 */       logger.error("   /   |   \\        VELOCITY 3.0.0 IS REQUIRED");
/*  91 */       logger.error("  /         \\   VIAVERSION WILL NOT WORK AS INTENDED");
/*  92 */       logger.error(" /     o     \\");
/*  93 */       logger.error("/_____________\\");
/*     */     } 
/*     */     
/*  96 */     PROXY = this.proxy;
/*  97 */     VelocityCommandHandler commandHandler = new VelocityCommandHandler();
/*  98 */     PROXY.getCommandManager().register("viaver", (Command)commandHandler, new String[] { "vvvelocity", "viaversion" });
/*  99 */     this.api = new VelocityViaAPI();
/* 100 */     this.conf = new VelocityViaConfig(this.configDir.toFile());
/* 101 */     this.logger = (Logger)new LoggerWrapper(this.loggerslf4j);
/* 102 */     Via.init(ViaManagerImpl.builder()
/* 103 */         .platform((ViaPlatform<?>)this)
/* 104 */         .commandHandler((ViaCommandHandler)commandHandler)
/* 105 */         .loader((ViaPlatformLoader)new VelocityViaLoader())
/* 106 */         .injector((ViaInjector)new VelocityViaInjector()).build());
/*     */   }
/*     */   
/*     */   @Subscribe(order = PostOrder.LAST)
/*     */   public void onProxyLateInit(ProxyInitializeEvent e) {
/* 111 */     ViaManagerImpl manager = (ViaManagerImpl)Via.getManager();
/* 112 */     manager.init();
/* 113 */     manager.onServerLoaded();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlatformName() {
/* 118 */     String proxyImpl = ProxyServer.class.getPackage().getImplementationTitle();
/* 119 */     return (proxyImpl != null) ? proxyImpl : "Velocity";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlatformVersion() {
/* 124 */     String version = ProxyServer.class.getPackage().getImplementationVersion();
/* 125 */     return (version != null) ? version : "Unknown";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isProxy() {
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPluginVersion() {
/* 135 */     return "4.7.0-1.20-pre5-SNAPSHOT";
/*     */   }
/*     */ 
/*     */   
/*     */   public PlatformTask runAsync(Runnable runnable) {
/* 140 */     return runSync(runnable);
/*     */   }
/*     */ 
/*     */   
/*     */   public PlatformTask runRepeatingAsync(Runnable runnable, long ticks) {
/* 145 */     return (PlatformTask)new VelocityViaTask(PROXY
/* 146 */         .getScheduler()
/* 147 */         .buildTask(this, runnable)
/* 148 */         .repeat(ticks * 50L, TimeUnit.MILLISECONDS).schedule());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PlatformTask runSync(Runnable runnable) {
/* 154 */     return runSync(runnable, 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public PlatformTask runSync(Runnable runnable, long delay) {
/* 159 */     return (PlatformTask)new VelocityViaTask(PROXY
/* 160 */         .getScheduler()
/* 161 */         .buildTask(this, runnable)
/* 162 */         .delay(delay * 50L, TimeUnit.MILLISECONDS).schedule());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PlatformTask runRepeatingSync(Runnable runnable, long period) {
/* 168 */     return runRepeatingAsync(runnable, period);
/*     */   }
/*     */ 
/*     */   
/*     */   public ViaCommandSender[] getOnlinePlayers() {
/* 173 */     return (ViaCommandSender[])PROXY.getAllPlayers().stream()
/* 174 */       .map(com.viaversion.viaversion.velocity.command.VelocityCommandSender::new)
/* 175 */       .toArray(x$0 -> new ViaCommandSender[x$0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMessage(UUID uuid, String message) {
/* 180 */     PROXY.getPlayer(uuid).ifPresent(player -> player.sendMessage((Component)COMPONENT_SERIALIZER.deserialize(message)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean kickPlayer(UUID uuid, String message) {
/* 185 */     return ((Boolean)PROXY.getPlayer(uuid).map(it -> {
/*     */           it.disconnect((Component)LegacyComponentSerializer.legacySection().deserialize(message));
/*     */           return Boolean.valueOf(true);
/* 188 */         }).orElse(Boolean.valueOf(false))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPluginEnabled() {
/* 193 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ConfigurationProvider getConfigurationProvider() {
/* 198 */     return (ConfigurationProvider)this.conf;
/*     */   }
/*     */ 
/*     */   
/*     */   public File getDataFolder() {
/* 203 */     return this.configDir.toFile();
/*     */   }
/*     */ 
/*     */   
/*     */   public VelocityViaAPI getApi() {
/* 208 */     return this.api;
/*     */   }
/*     */ 
/*     */   
/*     */   public VelocityViaConfig getConf() {
/* 213 */     return this.conf;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onReload() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonObject getDump() {
/* 223 */     JsonObject extra = new JsonObject();
/* 224 */     List<PluginInfo> plugins = new ArrayList<>();
/* 225 */     for (PluginContainer p : PROXY.getPluginManager().getPlugins()) {
/* 226 */       plugins.add(new PluginInfo(true, p
/*     */             
/* 228 */             .getDescription().getName().orElse(p.getDescription().getId()), p
/* 229 */             .getDescription().getVersion().orElse("Unknown Version"), 
/* 230 */             p.getInstance().isPresent() ? p.getInstance().get().getClass().getCanonicalName() : "Unknown", p
/* 231 */             .getDescription().getAuthors()));
/*     */     }
/*     */     
/* 234 */     extra.add("plugins", GsonUtil.getGson().toJsonTree(plugins));
/* 235 */     extra.add("servers", GsonUtil.getGson().toJsonTree(this.protocolDetectorService.detectedProtocolVersions()));
/* 236 */     return extra;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOldClientsAllowed() {
/* 241 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPlugin(String name) {
/* 246 */     return this.proxy.getPluginManager().getPlugin(name).isPresent();
/*     */   }
/*     */ 
/*     */   
/*     */   public Logger getLogger() {
/* 251 */     return this.logger;
/*     */   }
/*     */ 
/*     */   
/*     */   public ProtocolDetectorService protocolDetectorService() {
/* 256 */     return this.protocolDetectorService;
/*     */   }
/*     */   
/*     */   private boolean hasConnectionEvent() {
/*     */     try {
/* 261 */       Class.forName("com.velocitypowered.proxy.protocol.VelocityConnectionEvent");
/* 262 */       return true;
/* 263 */     } catch (ClassNotFoundException ignored) {
/* 264 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\VelocityPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viaversion;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.ViaAPI;
/*     */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*     */ import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
/*     */ import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
/*     */ import com.viaversion.viaversion.api.platform.PlatformTask;
/*     */ import com.viaversion.viaversion.api.platform.UnsupportedSoftware;
/*     */ import com.viaversion.viaversion.api.platform.ViaInjector;
/*     */ import com.viaversion.viaversion.api.platform.ViaPlatform;
/*     */ import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
/*     */ import com.viaversion.viaversion.bukkit.commands.BukkitCommandHandler;
/*     */ import com.viaversion.viaversion.bukkit.commands.BukkitCommandSender;
/*     */ import com.viaversion.viaversion.bukkit.platform.BukkitViaAPI;
/*     */ import com.viaversion.viaversion.bukkit.platform.BukkitViaConfig;
/*     */ import com.viaversion.viaversion.bukkit.platform.BukkitViaInjector;
/*     */ import com.viaversion.viaversion.bukkit.platform.BukkitViaLoader;
/*     */ import com.viaversion.viaversion.bukkit.platform.BukkitViaTask;
/*     */ import com.viaversion.viaversion.bukkit.platform.BukkitViaTaskTask;
/*     */ import com.viaversion.viaversion.bukkit.platform.PaperViaInjector;
/*     */ import com.viaversion.viaversion.commands.ViaCommandHandler;
/*     */ import com.viaversion.viaversion.dump.PluginInfo;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.unsupported.UnsupportedPlugin;
/*     */ import com.viaversion.viaversion.unsupported.UnsupportedServerSoftware;
/*     */ import com.viaversion.viaversion.util.GsonUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.TabCompleter;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.EventException;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ViaVersionPlugin
/*     */   extends JavaPlugin
/*     */   implements ViaPlatform<Player>
/*     */ {
/*  58 */   private static final boolean FOLIA = PaperViaInjector.hasClass("io.papermc.paper.threadedregions.RegionizedServer");
/*     */   private static ViaVersionPlugin instance;
/*     */   private final BukkitCommandHandler commandHandler;
/*     */   private final BukkitViaConfig conf;
/*  62 */   private final ViaAPI<Player> api = (ViaAPI<Player>)new BukkitViaAPI(this);
/*     */   private boolean protocolSupport;
/*     */   private boolean lateBind;
/*     */   
/*     */   public ViaVersionPlugin() {
/*  67 */     instance = this;
/*     */ 
/*     */     
/*  70 */     this.commandHandler = new BukkitCommandHandler();
/*     */ 
/*     */     
/*  73 */     BukkitViaInjector injector = new BukkitViaInjector();
/*  74 */     Via.init(ViaManagerImpl.builder()
/*  75 */         .platform(this)
/*  76 */         .commandHandler((ViaCommandHandler)this.commandHandler)
/*  77 */         .injector((ViaInjector)injector)
/*  78 */         .loader((ViaPlatformLoader)new BukkitViaLoader(this))
/*  79 */         .build());
/*     */ 
/*     */     
/*  82 */     this.conf = new BukkitViaConfig();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLoad() {
/*  87 */     this.protocolSupport = (Bukkit.getPluginManager().getPlugin("ProtocolSupport") != null);
/*  88 */     this.lateBind = !((BukkitViaInjector)Via.getManager().getInjector()).isBinded();
/*     */     
/*  90 */     if (!this.lateBind) {
/*  91 */       getLogger().info("ViaVersion " + getDescription().getVersion() + " is now loaded. Registering protocol transformers and injecting...");
/*  92 */       ((ViaManagerImpl)Via.getManager()).init();
/*     */     } else {
/*  94 */       getLogger().info("ViaVersion " + getDescription().getVersion() + " is now loaded. Waiting for boot (late-bind).");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 100 */     ViaManagerImpl manager = (ViaManagerImpl)Via.getManager();
/* 101 */     if (this.lateBind) {
/* 102 */       getLogger().info("Registering protocol transformers and injecting...");
/* 103 */       manager.init();
/*     */     } 
/*     */     
/* 106 */     if (FOLIA) {
/*     */       Class<? extends Event> serverInitEventClass;
/*     */ 
/*     */       
/*     */       try {
/* 111 */         serverInitEventClass = (Class)Class.forName("io.papermc.paper.threadedregions.RegionizedServerInitEvent");
/* 112 */       } catch (ReflectiveOperationException e) {
/* 113 */         throw new RuntimeException(e);
/*     */       } 
/*     */       
/* 116 */       getServer().getPluginManager().registerEvent(serverInitEventClass, new Listener() {  }, EventPriority.HIGHEST, (listener, event) -> manager.onServerLoaded(), (Plugin)this);
/*     */     }
/* 118 */     else if (Via.getManager().getInjector().lateProtocolVersionSetting()) {
/*     */       
/* 120 */       runSync(manager::onServerLoaded);
/*     */     } else {
/* 122 */       manager.onServerLoaded();
/*     */     } 
/*     */     
/* 125 */     getCommand("viaversion").setExecutor((CommandExecutor)this.commandHandler);
/* 126 */     getCommand("viaversion").setTabCompleter((TabCompleter)this.commandHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 131 */     ((ViaManagerImpl)Via.getManager()).destroy();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlatformName() {
/* 136 */     return Bukkit.getServer().getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlatformVersion() {
/* 141 */     return Bukkit.getServer().getVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPluginVersion() {
/* 146 */     return getDescription().getVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public PlatformTask runAsync(Runnable runnable) {
/* 151 */     if (FOLIA) {
/* 152 */       return (PlatformTask)new BukkitViaTaskTask(Via.getManager().getScheduler().execute(runnable));
/*     */     }
/* 154 */     return (PlatformTask)new BukkitViaTask(getServer().getScheduler().runTaskAsynchronously((Plugin)this, runnable));
/*     */   }
/*     */ 
/*     */   
/*     */   public PlatformTask runRepeatingAsync(Runnable runnable, long ticks) {
/* 159 */     if (FOLIA) {
/* 160 */       return (PlatformTask)new BukkitViaTaskTask(Via.getManager().getScheduler().schedule(runnable, ticks * 50L, TimeUnit.MILLISECONDS));
/*     */     }
/* 162 */     return (PlatformTask)new BukkitViaTask(getServer().getScheduler().runTaskTimerAsynchronously((Plugin)this, runnable, 0L, ticks));
/*     */   }
/*     */ 
/*     */   
/*     */   public PlatformTask runSync(Runnable runnable) {
/* 167 */     if (FOLIA)
/*     */     {
/* 169 */       return runAsync(runnable);
/*     */     }
/* 171 */     return (PlatformTask)new BukkitViaTask(getServer().getScheduler().runTask((Plugin)this, runnable));
/*     */   }
/*     */ 
/*     */   
/*     */   public PlatformTask runSync(Runnable runnable, long delay) {
/* 176 */     return (PlatformTask)new BukkitViaTask(getServer().getScheduler().runTaskLater((Plugin)this, runnable, delay));
/*     */   }
/*     */ 
/*     */   
/*     */   public PlatformTask runRepeatingSync(Runnable runnable, long period) {
/* 181 */     return (PlatformTask)new BukkitViaTask(getServer().getScheduler().runTaskTimer((Plugin)this, runnable, 0L, period));
/*     */   }
/*     */ 
/*     */   
/*     */   public ViaCommandSender[] getOnlinePlayers() {
/* 186 */     ViaCommandSender[] array = new ViaCommandSender[Bukkit.getOnlinePlayers().size()];
/* 187 */     int i = 0;
/* 188 */     for (Player player : Bukkit.getOnlinePlayers()) {
/* 189 */       array[i++] = (ViaCommandSender)new BukkitCommandSender((CommandSender)player);
/*     */     }
/* 191 */     return array;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMessage(UUID uuid, String message) {
/* 196 */     Player player = Bukkit.getPlayer(uuid);
/* 197 */     if (player != null) {
/* 198 */       player.sendMessage(message);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean kickPlayer(UUID uuid, String message) {
/* 204 */     Player player = Bukkit.getPlayer(uuid);
/* 205 */     if (player != null) {
/* 206 */       player.kickPlayer(message);
/* 207 */       return true;
/*     */     } 
/* 209 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPluginEnabled() {
/* 215 */     return Bukkit.getPluginManager().getPlugin("ViaVersion").isEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public ConfigurationProvider getConfigurationProvider() {
/* 220 */     return (ConfigurationProvider)this.conf;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onReload() {
/* 225 */     if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
/* 226 */       getLogger().severe("ViaVersion is already loaded, we're going to kick all the players... because otherwise we'll crash because of ProtocolLib.");
/* 227 */       for (Player player : Bukkit.getOnlinePlayers()) {
/* 228 */         player.kickPlayer(ChatColor.translateAlternateColorCodes('&', this.conf.getReloadDisconnectMsg()));
/*     */       }
/*     */     } else {
/*     */       
/* 232 */       getLogger().severe("ViaVersion is already loaded, this should work fine. If you get any console errors, try rebooting.");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonObject getDump() {
/* 238 */     JsonObject platformSpecific = new JsonObject();
/*     */     
/* 240 */     List<PluginInfo> plugins = new ArrayList<>();
/* 241 */     for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
/* 242 */       plugins.add(new PluginInfo(p.isEnabled(), p.getDescription().getName(), p.getDescription().getVersion(), p.getDescription().getMain(), p.getDescription().getAuthors()));
/*     */     }
/* 244 */     platformSpecific.add("plugins", GsonUtil.getGson().toJsonTree(plugins));
/*     */     
/* 246 */     return platformSpecific;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOldClientsAllowed() {
/* 251 */     return !this.protocolSupport;
/*     */   }
/*     */ 
/*     */   
/*     */   public BukkitViaConfig getConf() {
/* 256 */     return this.conf;
/*     */   }
/*     */ 
/*     */   
/*     */   public ViaAPI<Player> getApi() {
/* 261 */     return this.api;
/*     */   }
/*     */ 
/*     */   
/*     */   public final Collection<UnsupportedSoftware> getUnsupportedSoftwareClasses() {
/* 266 */     List<UnsupportedSoftware> list = new ArrayList<>(super.getUnsupportedSoftwareClasses());
/* 267 */     list.add((new UnsupportedServerSoftware.Builder()).name("Yatopia").reason("You are using server software that - outside of possibly breaking ViaVersion - can also cause severe damage to your server's integrity as a whole.")
/* 268 */         .addClassName("org.yatopiamc.yatopia.server.YatopiaConfig")
/* 269 */         .addClassName("net.yatopia.api.event.PlayerAttackEntityEvent")
/* 270 */         .addClassName("yatopiamc.org.yatopia.server.YatopiaConfig")
/* 271 */         .addMethod("org.bukkit.Server", "getLastTickTime").build());
/* 272 */     list.add((new UnsupportedPlugin.Builder()).name("software to mess with message signing").reason("Instead of doing the obvious (or nothing at all), these kinds of plugins completely break chat message handling, usually then also breaking other plugins.")
/* 273 */         .addPlugin("NoEncryption").addPlugin("NoReport")
/* 274 */         .addPlugin("NoChatReports").addPlugin("NoChatReport").build());
/* 275 */     return Collections.unmodifiableList(list);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPlugin(String name) {
/* 280 */     return (getServer().getPluginManager().getPlugin(name) != null);
/*     */   }
/*     */   
/*     */   public boolean isLateBind() {
/* 284 */     return this.lateBind;
/*     */   }
/*     */   
/*     */   public boolean isProtocolSupport() {
/* 288 */     return this.protocolSupport;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static ViaVersionPlugin getInstance() {
/* 293 */     return instance;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\ViaVersionPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
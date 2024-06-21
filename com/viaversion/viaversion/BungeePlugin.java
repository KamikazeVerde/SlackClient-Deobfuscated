/*     */ package com.viaversion.viaversion;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.ViaAPI;
/*     */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*     */ import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
/*     */ import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
/*     */ import com.viaversion.viaversion.api.platform.PlatformTask;
/*     */ import com.viaversion.viaversion.api.platform.ProtocolDetectorService;
/*     */ import com.viaversion.viaversion.api.platform.UnsupportedSoftware;
/*     */ import com.viaversion.viaversion.api.platform.ViaInjector;
/*     */ import com.viaversion.viaversion.api.platform.ViaPlatform;
/*     */ import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
/*     */ import com.viaversion.viaversion.api.platform.ViaServerProxyPlatform;
/*     */ import com.viaversion.viaversion.bungee.commands.BungeeCommand;
/*     */ import com.viaversion.viaversion.bungee.commands.BungeeCommandHandler;
/*     */ import com.viaversion.viaversion.bungee.commands.BungeeCommandSender;
/*     */ import com.viaversion.viaversion.bungee.platform.BungeeViaAPI;
/*     */ import com.viaversion.viaversion.bungee.platform.BungeeViaConfig;
/*     */ import com.viaversion.viaversion.bungee.platform.BungeeViaInjector;
/*     */ import com.viaversion.viaversion.bungee.platform.BungeeViaLoader;
/*     */ import com.viaversion.viaversion.bungee.platform.BungeeViaTask;
/*     */ import com.viaversion.viaversion.bungee.service.ProtocolDetectorService;
/*     */ import com.viaversion.viaversion.commands.ViaCommandHandler;
/*     */ import com.viaversion.viaversion.dump.PluginInfo;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.unsupported.UnsupportedServerSoftware;
/*     */ import com.viaversion.viaversion.util.GsonUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.md_5.bungee.api.CommandSender;
/*     */ import net.md_5.bungee.api.ProxyServer;
/*     */ import net.md_5.bungee.api.connection.ProxiedPlayer;
/*     */ import net.md_5.bungee.api.plugin.Command;
/*     */ import net.md_5.bungee.api.plugin.Listener;
/*     */ import net.md_5.bungee.api.plugin.Plugin;
/*     */ import net.md_5.bungee.protocol.ProtocolConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BungeePlugin
/*     */   extends Plugin
/*     */   implements ViaServerProxyPlatform<ProxiedPlayer>, Listener
/*     */ {
/*  54 */   private final ProtocolDetectorService protocolDetectorService = new ProtocolDetectorService();
/*     */   
/*     */   private BungeeViaAPI api;
/*     */   private BungeeViaConfig config;
/*     */   
/*     */   public void onLoad() {
/*     */     try {
/*  61 */       ProtocolConstants.class.getField("MINECRAFT_1_19_4");
/*  62 */     } catch (NoSuchFieldException e) {
/*  63 */       getLogger().warning("      / \\");
/*  64 */       getLogger().warning("     /   \\");
/*  65 */       getLogger().warning("    /  |  \\");
/*  66 */       getLogger().warning("   /   |   \\         BUNGEECORD IS OUTDATED");
/*  67 */       getLogger().warning("  /         \\   VIAVERSION MAY NOT WORK AS INTENDED");
/*  68 */       getLogger().warning(" /     o     \\");
/*  69 */       getLogger().warning("/_____________\\");
/*     */     } 
/*     */     
/*  72 */     this.api = new BungeeViaAPI();
/*  73 */     this.config = new BungeeViaConfig(getDataFolder());
/*  74 */     BungeeCommandHandler commandHandler = new BungeeCommandHandler();
/*  75 */     ProxyServer.getInstance().getPluginManager().registerCommand(this, (Command)new BungeeCommand(commandHandler));
/*     */ 
/*     */     
/*  78 */     Via.init(ViaManagerImpl.builder()
/*  79 */         .platform((ViaPlatform<?>)this)
/*  80 */         .injector((ViaInjector)new BungeeViaInjector())
/*  81 */         .loader((ViaPlatformLoader)new BungeeViaLoader(this))
/*  82 */         .commandHandler((ViaCommandHandler)commandHandler)
/*  83 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  88 */     ViaManagerImpl manager = (ViaManagerImpl)Via.getManager();
/*  89 */     manager.init();
/*  90 */     manager.onServerLoaded();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlatformName() {
/*  95 */     return getProxy().getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlatformVersion() {
/* 100 */     return getProxy().getVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isProxy() {
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPluginVersion() {
/* 110 */     return getDescription().getVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public PlatformTask runAsync(Runnable runnable) {
/* 115 */     return (PlatformTask)new BungeeViaTask(getProxy().getScheduler().runAsync(this, runnable));
/*     */   }
/*     */ 
/*     */   
/*     */   public PlatformTask runRepeatingAsync(Runnable runnable, long ticks) {
/* 120 */     return (PlatformTask)new BungeeViaTask(getProxy().getScheduler().schedule(this, runnable, 0L, ticks * 50L, TimeUnit.MILLISECONDS));
/*     */   }
/*     */ 
/*     */   
/*     */   public PlatformTask runSync(Runnable runnable) {
/* 125 */     return runAsync(runnable);
/*     */   }
/*     */ 
/*     */   
/*     */   public PlatformTask runSync(Runnable runnable, long delay) {
/* 130 */     return (PlatformTask)new BungeeViaTask(getProxy().getScheduler().schedule(this, runnable, delay * 50L, TimeUnit.MILLISECONDS));
/*     */   }
/*     */ 
/*     */   
/*     */   public PlatformTask runRepeatingSync(Runnable runnable, long period) {
/* 135 */     return runRepeatingAsync(runnable, period);
/*     */   }
/*     */ 
/*     */   
/*     */   public ViaCommandSender[] getOnlinePlayers() {
/* 140 */     Collection<ProxiedPlayer> players = getProxy().getPlayers();
/* 141 */     ViaCommandSender[] array = new ViaCommandSender[players.size()];
/* 142 */     int i = 0;
/* 143 */     for (ProxiedPlayer player : players) {
/* 144 */       array[i++] = (ViaCommandSender)new BungeeCommandSender((CommandSender)player);
/*     */     }
/* 146 */     return array;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMessage(UUID uuid, String message) {
/* 151 */     getProxy().getPlayer(uuid).sendMessage(message);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean kickPlayer(UUID uuid, String message) {
/* 156 */     ProxiedPlayer player = getProxy().getPlayer(uuid);
/* 157 */     if (player != null) {
/* 158 */       player.disconnect(message);
/* 159 */       return true;
/*     */     } 
/* 161 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPluginEnabled() {
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ViaAPI<ProxiedPlayer> getApi() {
/* 171 */     return (ViaAPI<ProxiedPlayer>)this.api;
/*     */   }
/*     */ 
/*     */   
/*     */   public BungeeViaConfig getConf() {
/* 176 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public ConfigurationProvider getConfigurationProvider() {
/* 181 */     return (ConfigurationProvider)this.config;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onReload() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonObject getDump() {
/* 191 */     JsonObject platformSpecific = new JsonObject();
/*     */     
/* 193 */     List<PluginInfo> plugins = new ArrayList<>();
/* 194 */     for (Plugin p : ProxyServer.getInstance().getPluginManager().getPlugins()) {
/* 195 */       plugins.add(new PluginInfo(true, p
/*     */             
/* 197 */             .getDescription().getName(), p
/* 198 */             .getDescription().getVersion(), p
/* 199 */             .getDescription().getMain(), 
/* 200 */             Collections.singletonList(p.getDescription().getAuthor())));
/*     */     }
/*     */     
/* 203 */     platformSpecific.add("plugins", GsonUtil.getGson().toJsonTree(plugins));
/* 204 */     platformSpecific.add("servers", GsonUtil.getGson().toJsonTree(this.protocolDetectorService.detectedProtocolVersions()));
/* 205 */     return platformSpecific;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOldClientsAllowed() {
/* 210 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<UnsupportedSoftware> getUnsupportedSoftwareClasses() {
/* 215 */     Collection<UnsupportedSoftware> list = new ArrayList<>(super.getUnsupportedSoftwareClasses());
/* 216 */     list.add((new UnsupportedServerSoftware.Builder())
/* 217 */         .name("FlameCord")
/* 218 */         .addClassName("dev._2lstudios.flamecord.FlameCord")
/* 219 */         .reason("You are using proxy software that intentionally breaks ViaVersion. Please use another proxy software or move ViaVersion to each backend server instead of the proxy.")
/* 220 */         .build());
/* 221 */     return (Collection<UnsupportedSoftware>)ImmutableList.copyOf(list);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPlugin(String name) {
/* 226 */     return (getProxy().getPluginManager().getPlugin(name) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ProtocolDetectorService protocolDetectorService() {
/* 231 */     return this.protocolDetectorService;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\BungeePlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
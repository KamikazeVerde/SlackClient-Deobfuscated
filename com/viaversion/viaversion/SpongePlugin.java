/*     */ package com.viaversion.viaversion;
/*     */ 
/*     */ import com.google.inject.Inject;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.ViaAPI;
/*     */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*     */ import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
/*     */ import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
/*     */ import com.viaversion.viaversion.api.platform.PlatformTask;
/*     */ import com.viaversion.viaversion.api.platform.ViaInjector;
/*     */ import com.viaversion.viaversion.api.platform.ViaPlatform;
/*     */ import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
/*     */ import com.viaversion.viaversion.commands.ViaCommandHandler;
/*     */ import com.viaversion.viaversion.dump.PluginInfo;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.sponge.commands.SpongeCommandHandler;
/*     */ import com.viaversion.viaversion.sponge.commands.SpongePlayer;
/*     */ import com.viaversion.viaversion.sponge.platform.SpongeViaAPI;
/*     */ import com.viaversion.viaversion.sponge.platform.SpongeViaConfig;
/*     */ import com.viaversion.viaversion.sponge.platform.SpongeViaInjector;
/*     */ import com.viaversion.viaversion.sponge.platform.SpongeViaLoader;
/*     */ import com.viaversion.viaversion.sponge.platform.SpongeViaTask;
/*     */ import com.viaversion.viaversion.sponge.util.LoggerWrapper;
/*     */ import com.viaversion.viaversion.util.GsonUtil;
/*     */ import java.io.File;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.stream.Collectors;
/*     */ import net.kyori.adventure.text.Component;
/*     */ import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.api.Game;
/*     */ import org.spongepowered.api.Platform;
/*     */ import org.spongepowered.api.Server;
/*     */ import org.spongepowered.api.Sponge;
/*     */ import org.spongepowered.api.command.Command;
/*     */ import org.spongepowered.api.command.registrar.CommandRegistrar;
/*     */ import org.spongepowered.api.config.ConfigDir;
/*     */ import org.spongepowered.api.entity.living.player.Player;
/*     */ import org.spongepowered.api.entity.living.player.server.ServerPlayer;
/*     */ import org.spongepowered.api.event.Listener;
/*     */ import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
/*     */ import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
/*     */ import org.spongepowered.api.event.lifecycle.StartingEngineEvent;
/*     */ import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
/*     */ import org.spongepowered.api.scheduler.Task;
/*     */ import org.spongepowered.api.util.Ticks;
/*     */ import org.spongepowered.plugin.PluginContainer;
/*     */ import org.spongepowered.plugin.builtin.jvm.Plugin;
/*     */ import org.spongepowered.plugin.metadata.PluginMetadata;
/*     */ import org.spongepowered.plugin.metadata.model.PluginContributor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin("viaversion")
/*     */ public class SpongePlugin
/*     */   implements ViaPlatform<Player>
/*     */ {
/*  69 */   public static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.builder().extractUrls().build();
/*  70 */   private final SpongeViaAPI api = new SpongeViaAPI();
/*     */   
/*     */   private final PluginContainer container;
/*     */   
/*     */   private final Game game;
/*     */   private final Logger logger;
/*     */   private SpongeViaConfig conf;
/*     */   @Inject
/*     */   @ConfigDir(sharedRoot = false)
/*     */   private Path configDir;
/*     */   
/*     */   @Inject
/*     */   SpongePlugin(PluginContainer container, Game game, Logger logger) {
/*  83 */     this.container = container;
/*  84 */     this.game = game;
/*  85 */     this.logger = (Logger)new LoggerWrapper(logger);
/*     */   }
/*     */ 
/*     */   
/*     */   @Listener
/*     */   public void constructPlugin(ConstructPluginEvent event) {
/*  91 */     this.conf = new SpongeViaConfig(this.configDir.toFile());
/*     */ 
/*     */     
/*  94 */     Via.init(ViaManagerImpl.builder()
/*  95 */         .platform(this)
/*  96 */         .commandHandler((ViaCommandHandler)new SpongeCommandHandler())
/*  97 */         .injector((ViaInjector)new SpongeViaInjector())
/*  98 */         .loader((ViaPlatformLoader)new SpongeViaLoader(this))
/*  99 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   @Listener
/*     */   public void onServerStart(StartingEngineEvent<Server> event) {
/* 105 */     ((CommandRegistrar)Sponge.server().commandManager().registrar(Command.Raw.class).get()).register(this.container, Via.getManager().getCommandHandler(), "viaversion", new String[] { "viaver", "vvsponge" });
/*     */     
/* 107 */     ViaManagerImpl manager = (ViaManagerImpl)Via.getManager();
/* 108 */     manager.init();
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void onServerStarted(StartedEngineEvent<Server> event) {
/* 113 */     ViaManagerImpl manager = (ViaManagerImpl)Via.getManager();
/* 114 */     manager.onServerLoaded();
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void onServerStop(StoppingEngineEvent<Server> event) {
/* 119 */     ((ViaManagerImpl)Via.getManager()).destroy();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlatformName() {
/* 124 */     return this.game.platform().container(Platform.Component.IMPLEMENTATION).metadata().name().orElse("unknown");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlatformVersion() {
/* 129 */     return this.game.platform().container(Platform.Component.IMPLEMENTATION).metadata().version().toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPluginVersion() {
/* 134 */     return this.container.metadata().version().toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public PlatformTask runAsync(Runnable runnable) {
/* 139 */     Task task = Task.builder().plugin(this.container).execute(runnable).build();
/* 140 */     return (PlatformTask)new SpongeViaTask(this.game.asyncScheduler().submit(task));
/*     */   }
/*     */ 
/*     */   
/*     */   public PlatformTask runRepeatingAsync(Runnable runnable, long ticks) {
/* 145 */     Task task = Task.builder().plugin(this.container).execute(runnable).interval(Ticks.of(ticks)).build();
/* 146 */     return (PlatformTask)new SpongeViaTask(this.game.asyncScheduler().submit(task));
/*     */   }
/*     */ 
/*     */   
/*     */   public PlatformTask runSync(Runnable runnable) {
/* 151 */     Task task = Task.builder().plugin(this.container).execute(runnable).build();
/* 152 */     return (PlatformTask)new SpongeViaTask(this.game.server().scheduler().submit(task));
/*     */   }
/*     */ 
/*     */   
/*     */   public PlatformTask runSync(Runnable runnable, long delay) {
/* 157 */     Task task = Task.builder().plugin(this.container).execute(runnable).delay(Ticks.of(delay)).build();
/* 158 */     return (PlatformTask)new SpongeViaTask(this.game.server().scheduler().submit(task));
/*     */   }
/*     */ 
/*     */   
/*     */   public PlatformTask runRepeatingSync(Runnable runnable, long period) {
/* 163 */     Task task = Task.builder().plugin(this.container).execute(runnable).interval(Ticks.of(period)).build();
/* 164 */     return (PlatformTask)new SpongeViaTask(this.game.server().scheduler().submit(task));
/*     */   }
/*     */ 
/*     */   
/*     */   public ViaCommandSender[] getOnlinePlayers() {
/* 169 */     Collection<ServerPlayer> players = this.game.server().onlinePlayers();
/* 170 */     ViaCommandSender[] array = new ViaCommandSender[players.size()];
/* 171 */     int i = 0;
/* 172 */     for (ServerPlayer player : players) {
/* 173 */       array[i++] = (ViaCommandSender)new SpongePlayer(player);
/*     */     }
/* 175 */     return array;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMessage(UUID uuid, String message) {
/* 180 */     this.game.server().player(uuid).ifPresent(player -> player.sendMessage((Component)LEGACY_SERIALIZER.deserialize(message)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean kickPlayer(UUID uuid, String message) {
/* 185 */     return ((Boolean)this.game.server().player(uuid).map(player -> {
/*     */           player.kick((Component)LegacyComponentSerializer.legacySection().deserialize(message));
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
/*     */   public void onReload() {
/* 208 */     this.logger.severe("ViaVersion is already loaded, this should work fine. If you get any console errors, try rebooting.");
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonObject getDump() {
/* 213 */     JsonObject platformSpecific = new JsonObject();
/*     */     
/* 215 */     List<PluginInfo> plugins = new ArrayList<>();
/* 216 */     for (PluginContainer plugin : this.game.pluginManager().plugins()) {
/* 217 */       PluginMetadata metadata = plugin.metadata();
/* 218 */       plugins.add(new PluginInfo(true, metadata
/*     */             
/* 220 */             .name().orElse("Unknown"), metadata
/* 221 */             .version().toString(), plugin
/* 222 */             .instance().getClass().getCanonicalName(), (List)metadata
/* 223 */             .contributors().stream().map(PluginContributor::name).collect(Collectors.toList())));
/*     */     } 
/*     */     
/* 226 */     platformSpecific.add("plugins", GsonUtil.getGson().toJsonTree(plugins));
/*     */     
/* 228 */     return platformSpecific;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOldClientsAllowed() {
/* 233 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPlugin(String name) {
/* 238 */     return this.game.pluginManager().plugin(name).isPresent();
/*     */   }
/*     */ 
/*     */   
/*     */   public SpongeViaAPI getApi() {
/* 243 */     return this.api;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpongeViaConfig getConf() {
/* 248 */     return this.conf;
/*     */   }
/*     */ 
/*     */   
/*     */   public Logger getLogger() {
/* 253 */     return this.logger;
/*     */   }
/*     */   
/*     */   public PluginContainer container() {
/* 257 */     return this.container;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\SpongePlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
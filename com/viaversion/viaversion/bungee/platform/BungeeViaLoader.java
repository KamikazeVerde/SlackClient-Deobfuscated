/*    */ package com.viaversion.viaversion.bungee.platform;
/*    */ 
/*    */ import com.viaversion.viaversion.BungeePlugin;
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
/*    */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*    */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*    */ import com.viaversion.viaversion.api.protocol.version.VersionProvider;
/*    */ import com.viaversion.viaversion.bungee.handlers.BungeeServerHandler;
/*    */ import com.viaversion.viaversion.bungee.listeners.ElytraPatch;
/*    */ import com.viaversion.viaversion.bungee.listeners.UpdateListener;
/*    */ import com.viaversion.viaversion.bungee.providers.BungeeBossBarProvider;
/*    */ import com.viaversion.viaversion.bungee.providers.BungeeEntityIdProvider;
/*    */ import com.viaversion.viaversion.bungee.providers.BungeeMainHandProvider;
/*    */ import com.viaversion.viaversion.bungee.providers.BungeeMovementTransmitter;
/*    */ import com.viaversion.viaversion.bungee.providers.BungeeVersionProvider;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.EntityIdProvider;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MainHandProvider;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import net.md_5.bungee.api.ProxyServer;
/*    */ import net.md_5.bungee.api.plugin.Listener;
/*    */ import net.md_5.bungee.api.plugin.Plugin;
/*    */ import net.md_5.bungee.api.scheduler.ScheduledTask;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BungeeViaLoader
/*    */   implements ViaPlatformLoader
/*    */ {
/* 45 */   private final Set<Listener> listeners = new HashSet<>();
/* 46 */   private final Set<ScheduledTask> tasks = new HashSet<>();
/*    */   private final BungeePlugin plugin;
/*    */   
/*    */   public BungeeViaLoader(BungeePlugin plugin) {
/* 50 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   private void registerListener(Listener listener) {
/* 54 */     this.listeners.add(listener);
/* 55 */     ProxyServer.getInstance().getPluginManager().registerListener((Plugin)this.plugin, listener);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void load() {
/* 61 */     registerListener((Listener)this.plugin);
/* 62 */     registerListener((Listener)new UpdateListener());
/* 63 */     registerListener((Listener)new BungeeServerHandler());
/*    */     
/* 65 */     if (Via.getAPI().getServerVersion().lowestSupportedVersion() < ProtocolVersion.v1_9.getVersion()) {
/* 66 */       registerListener((Listener)new ElytraPatch());
/*    */     }
/*    */ 
/*    */     
/* 70 */     Via.getManager().getProviders().use(VersionProvider.class, (Provider)new BungeeVersionProvider());
/* 71 */     Via.getManager().getProviders().use(EntityIdProvider.class, (Provider)new BungeeEntityIdProvider());
/*    */     
/* 73 */     if (Via.getAPI().getServerVersion().lowestSupportedVersion() < ProtocolVersion.v1_9.getVersion()) {
/* 74 */       Via.getManager().getProviders().use(MovementTransmitterProvider.class, (Provider)new BungeeMovementTransmitter());
/* 75 */       Via.getManager().getProviders().use(BossBarProvider.class, (Provider)new BungeeBossBarProvider());
/* 76 */       Via.getManager().getProviders().use(MainHandProvider.class, (Provider)new BungeeMainHandProvider());
/*    */     } 
/*    */     
/* 79 */     if (this.plugin.getConf().getBungeePingInterval() > 0) {
/* 80 */       this.tasks.add(this.plugin.getProxy().getScheduler().schedule((Plugin)this.plugin, () -> Via.proxyPlatform().protocolDetectorService().probeAllServers(), 0L, this.plugin
/*    */ 
/*    */             
/* 83 */             .getConf().getBungeePingInterval(), TimeUnit.SECONDS));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void unload() {
/* 91 */     for (Listener listener : this.listeners) {
/* 92 */       ProxyServer.getInstance().getPluginManager().unregisterListener(listener);
/*    */     }
/* 94 */     this.listeners.clear();
/* 95 */     for (ScheduledTask task : this.tasks) {
/* 96 */       task.cancel();
/*    */     }
/* 98 */     this.tasks.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\platform\BungeeViaLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
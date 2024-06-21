/*     */ package com.viaversion.viaversion.bukkit.platform;
/*     */ 
/*     */ import com.viaversion.viaversion.ViaVersionPlugin;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
/*     */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import com.viaversion.viaversion.bukkit.compat.ProtocolSupportCompat;
/*     */ import com.viaversion.viaversion.bukkit.listeners.JoinListener;
/*     */ import com.viaversion.viaversion.bukkit.listeners.UpdateListener;
/*     */ import com.viaversion.viaversion.bukkit.listeners.multiversion.PlayerSneakListener;
/*     */ import com.viaversion.viaversion.bukkit.listeners.protocol1_15to1_14_4.EntityToggleGlideListener;
/*     */ import com.viaversion.viaversion.bukkit.listeners.protocol1_19_4To1_19_3.ArmorToggleListener;
/*     */ import com.viaversion.viaversion.bukkit.listeners.protocol1_19to1_18_2.BlockBreakListener;
/*     */ import com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.ArmorListener;
/*     */ import com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.BlockListener;
/*     */ import com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.DeathListener;
/*     */ import com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.HandItemCache;
/*     */ import com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.PaperPatch;
/*     */ import com.viaversion.viaversion.bukkit.providers.BukkitAckSequenceProvider;
/*     */ import com.viaversion.viaversion.bukkit.providers.BukkitBlockConnectionProvider;
/*     */ import com.viaversion.viaversion.bukkit.providers.BukkitInventoryQuickMoveProvider;
/*     */ import com.viaversion.viaversion.bukkit.providers.BukkitViaMovementTransmitter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.providers.InventoryQuickMoveProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.provider.AckSequenceProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.HandItemProvider;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitTask;
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
/*     */ public class BukkitViaLoader
/*     */   implements ViaPlatformLoader
/*     */ {
/*  61 */   private final Set<BukkitTask> tasks = new HashSet<>();
/*     */   private final ViaVersionPlugin plugin;
/*     */   private HandItemCache handItemCache;
/*     */   
/*     */   public BukkitViaLoader(ViaVersionPlugin plugin) {
/*  66 */     this.plugin = plugin;
/*     */   }
/*     */   
/*     */   public void registerListener(Listener listener) {
/*  70 */     this.plugin.getServer().getPluginManager().registerEvents(listener, (Plugin)this.plugin);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public <T extends Listener> T storeListener(T listener) {
/*  75 */     return listener;
/*     */   }
/*     */ 
/*     */   
/*     */   public void load() {
/*  80 */     registerListener((Listener)new UpdateListener());
/*     */     
/*  82 */     if (Via.getConfig().shouldRegisterUserConnectionOnJoin()) {
/*  83 */       registerListener((Listener)new JoinListener());
/*     */     }
/*     */ 
/*     */     
/*  87 */     ViaVersionPlugin plugin = (ViaVersionPlugin)Bukkit.getPluginManager().getPlugin("ViaVersion");
/*     */ 
/*     */     
/*  90 */     if (plugin.isProtocolSupport() && ProtocolSupportCompat.isMultiplatformPS()) {
/*  91 */       ProtocolSupportCompat.registerPSConnectListener(plugin);
/*     */     }
/*     */     
/*  94 */     if (!Via.getAPI().getServerVersion().isKnown()) {
/*  95 */       Via.getPlatform().getLogger().severe("Server version has not been loaded yet, cannot register additional listeners");
/*     */       
/*     */       return;
/*     */     } 
/*  99 */     int serverProtocolVersion = Via.getAPI().getServerVersion().lowestSupportedVersion();
/*     */ 
/*     */     
/* 102 */     if (serverProtocolVersion < ProtocolVersion.v1_9.getVersion()) {
/* 103 */       (new ArmorListener((Plugin)plugin)).register();
/* 104 */       (new DeathListener((Plugin)plugin)).register();
/* 105 */       (new BlockListener((Plugin)plugin)).register();
/*     */       
/* 107 */       if (plugin.getConf().isItemCache()) {
/* 108 */         this.handItemCache = new HandItemCache();
/* 109 */         this.tasks.add(this.handItemCache.runTaskTimerAsynchronously((Plugin)plugin, 1L, 1L));
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     if (serverProtocolVersion < ProtocolVersion.v1_14.getVersion()) {
/* 114 */       boolean use1_9Fix = (plugin.getConf().is1_9HitboxFix() && serverProtocolVersion < ProtocolVersion.v1_9.getVersion());
/* 115 */       if (use1_9Fix || plugin.getConf().is1_14HitboxFix()) {
/*     */         try {
/* 117 */           (new PlayerSneakListener(plugin, use1_9Fix, plugin.getConf().is1_14HitboxFix())).register();
/* 118 */         } catch (ReflectiveOperationException e) {
/* 119 */           Via.getPlatform().getLogger().warning("Could not load hitbox fix - please report this on our GitHub");
/* 120 */           e.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 125 */     if (serverProtocolVersion < ProtocolVersion.v1_15.getVersion()) {
/*     */       try {
/* 127 */         Class.forName("org.bukkit.event.entity.EntityToggleGlideEvent");
/* 128 */         (new EntityToggleGlideListener(plugin)).register();
/* 129 */       } catch (ClassNotFoundException classNotFoundException) {}
/*     */     }
/*     */ 
/*     */     
/* 133 */     if (serverProtocolVersion < ProtocolVersion.v1_12.getVersion() && !Boolean.getBoolean("com.viaversion.ignorePaperBlockPlacePatch")) {
/* 134 */       boolean paper = true;
/*     */       try {
/* 136 */         Class.forName("org.github.paperspigot.PaperSpigotConfig");
/* 137 */       } catch (ClassNotFoundException ignored) {
/*     */         try {
/* 139 */           Class.forName("com.destroystokyo.paper.PaperConfig");
/* 140 */         } catch (ClassNotFoundException alsoIgnored) {
/* 141 */           paper = false;
/*     */         } 
/*     */       } 
/* 144 */       if (paper) {
/* 145 */         (new PaperPatch((Plugin)plugin)).register();
/*     */       }
/*     */     } 
/*     */     
/* 149 */     if (serverProtocolVersion < ProtocolVersion.v1_19_4.getVersion() && plugin.getConf().isArmorToggleFix() && hasGetHandMethod()) {
/* 150 */       (new ArmorToggleListener(plugin)).register();
/*     */     }
/*     */ 
/*     */     
/* 154 */     if (serverProtocolVersion < ProtocolVersion.v1_9.getVersion()) {
/* 155 */       Via.getManager().getProviders().use(MovementTransmitterProvider.class, (Provider)new BukkitViaMovementTransmitter());
/*     */       
/* 157 */       Via.getManager().getProviders().use(HandItemProvider.class, (Provider)new HandItemProvider()
/*     */           {
/*     */             public Item getHandItem(UserConnection info) {
/* 160 */               if (BukkitViaLoader.this.handItemCache != null) {
/* 161 */                 return BukkitViaLoader.this.handItemCache.getHandItem(info.getProtocolInfo().getUuid());
/*     */               }
/*     */               try {
/* 164 */                 return Bukkit.getScheduler().callSyncMethod(Bukkit.getPluginManager().getPlugin("ViaVersion"), () -> {
/*     */                       UUID playerUUID = info.getProtocolInfo().getUuid();
/*     */ 
/*     */                       
/*     */                       Player player = Bukkit.getPlayer(playerUUID);
/*     */                       
/*     */                       return (player != null) ? HandItemCache.convert(player.getItemInHand()) : null;
/* 171 */                     }).get(10L, TimeUnit.SECONDS);
/* 172 */               } catch (Exception e) {
/* 173 */                 Via.getPlatform().getLogger().severe("Error fetching hand item: " + e.getClass().getName());
/* 174 */                 if (Via.getManager().isDebug())
/* 175 */                   e.printStackTrace(); 
/* 176 */                 return null;
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 182 */     if (serverProtocolVersion < ProtocolVersion.v1_12.getVersion() && 
/* 183 */       plugin.getConf().is1_12QuickMoveActionFix()) {
/* 184 */       Via.getManager().getProviders().use(InventoryQuickMoveProvider.class, (Provider)new BukkitInventoryQuickMoveProvider());
/*     */     }
/*     */     
/* 187 */     if (serverProtocolVersion < ProtocolVersion.v1_13.getVersion() && 
/* 188 */       Via.getConfig().getBlockConnectionMethod().equalsIgnoreCase("world")) {
/* 189 */       BukkitBlockConnectionProvider blockConnectionProvider = new BukkitBlockConnectionProvider();
/* 190 */       Via.getManager().getProviders().use(BlockConnectionProvider.class, (Provider)blockConnectionProvider);
/* 191 */       ConnectionData.blockConnectionProvider = (BlockConnectionProvider)blockConnectionProvider;
/*     */     } 
/*     */     
/* 194 */     if (serverProtocolVersion < ProtocolVersion.v1_19.getVersion()) {
/* 195 */       Via.getManager().getProviders().use(AckSequenceProvider.class, (Provider)new BukkitAckSequenceProvider(plugin));
/* 196 */       (new BlockBreakListener(plugin)).register();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean hasGetHandMethod() {
/*     */     try {
/* 202 */       PlayerInteractEvent.class.getDeclaredMethod("getHand", new Class[0]);
/* 203 */       Material.class.getMethod("getEquipmentSlot", new Class[0]);
/* 204 */       return true;
/* 205 */     } catch (NoSuchMethodException e) {
/* 206 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void unload() {
/* 212 */     for (BukkitTask task : this.tasks) {
/* 213 */       task.cancel();
/*     */     }
/* 215 */     this.tasks.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\platform\BukkitViaLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
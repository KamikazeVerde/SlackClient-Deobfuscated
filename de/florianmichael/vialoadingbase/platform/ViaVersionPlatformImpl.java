/*     */ package de.florianmichael.vialoadingbase.platform;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.ViaAPI;
/*     */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*     */ import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
/*     */ import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.platform.PlatformTask;
/*     */ import com.viaversion.viaversion.api.platform.UnsupportedSoftware;
/*     */ import com.viaversion.viaversion.api.platform.ViaPlatform;
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import de.florianmichael.vialoadingbase.ViaLoadingBase;
/*     */ import de.florianmichael.vialoadingbase.platform.viaversion.VLBViaAPIWrapper;
/*     */ import de.florianmichael.vialoadingbase.platform.viaversion.VLBViaConfig;
/*     */ import de.florianmichael.vialoadingbase.util.VLBTask;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.stream.Collectors;
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
/*     */ public class ViaVersionPlatformImpl
/*     */   implements ViaPlatform<UUID>
/*     */ {
/*  44 */   private final ViaAPI<UUID> api = (ViaAPI<UUID>)new VLBViaAPIWrapper();
/*     */   
/*     */   private final Logger logger;
/*     */   private final VLBViaConfig config;
/*     */   
/*     */   public ViaVersionPlatformImpl(Logger logger) {
/*  50 */     this.logger = logger;
/*  51 */     this.config = new VLBViaConfig(new File(ViaLoadingBase.getInstance().getRunDirectory(), "viaversion.yml"));
/*     */   }
/*     */   
/*     */   public static List<ProtocolVersion> createVersionList() {
/*  55 */     List<ProtocolVersion> versions = (List<ProtocolVersion>)(new ArrayList(ProtocolVersion.getProtocols())).stream().filter(protocolVersion -> (protocolVersion != ProtocolVersion.unknown && ProtocolVersion.getProtocols().indexOf(protocolVersion) >= 7)).collect(Collectors.toList());
/*  56 */     Collections.reverse(versions);
/*  57 */     return versions;
/*     */   }
/*     */ 
/*     */   
/*     */   public ViaCommandSender[] getOnlinePlayers() {
/*  62 */     return new ViaCommandSender[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMessage(UUID uuid, String msg) {
/*  67 */     if (uuid == null) {
/*  68 */       getLogger().info(msg);
/*     */     } else {
/*  70 */       getLogger().info("[" + uuid + "] " + msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean kickPlayer(UUID uuid, String s) {
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean disconnect(UserConnection connection, String message) {
/*  81 */     return super.disconnect(connection, message);
/*     */   }
/*     */ 
/*     */   
/*     */   public VLBTask runAsync(Runnable runnable) {
/*  86 */     return new VLBTask(Via.getManager().getScheduler().execute(runnable));
/*     */   }
/*     */ 
/*     */   
/*     */   public VLBTask runRepeatingAsync(Runnable runnable, long ticks) {
/*  91 */     return new VLBTask(Via.getManager().getScheduler().scheduleRepeating(runnable, 0L, ticks * 50L, TimeUnit.MILLISECONDS));
/*     */   }
/*     */ 
/*     */   
/*     */   public VLBTask runSync(Runnable runnable) {
/*  96 */     return runAsync(runnable);
/*     */   }
/*     */ 
/*     */   
/*     */   public VLBTask runSync(Runnable runnable, long ticks) {
/* 101 */     return new VLBTask(Via.getManager().getScheduler().schedule(runnable, ticks * 50L, TimeUnit.MILLISECONDS));
/*     */   }
/*     */ 
/*     */   
/*     */   public VLBTask runRepeatingSync(Runnable runnable, long ticks) {
/* 106 */     return runRepeatingAsync(runnable, ticks);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isProxy() {
/* 111 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onReload() {}
/*     */ 
/*     */   
/*     */   public Logger getLogger() {
/* 120 */     return this.logger;
/*     */   }
/*     */ 
/*     */   
/*     */   public ViaVersionConfig getConf() {
/* 125 */     return (ViaVersionConfig)this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public ViaAPI<UUID> getApi() {
/* 130 */     return this.api;
/*     */   }
/*     */ 
/*     */   
/*     */   public File getDataFolder() {
/* 135 */     return ViaLoadingBase.getInstance().getRunDirectory();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPluginVersion() {
/* 140 */     return "4.7.0-1.20-pre5-SNAPSHOT";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlatformName() {
/* 145 */     return "ViaLoadingBase by FlorianMichael";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlatformVersion() {
/* 150 */     return "${vialoadingbase_version}";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPluginEnabled() {
/* 155 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ConfigurationProvider getConfigurationProvider() {
/* 160 */     return (ConfigurationProvider)this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOldClientsAllowed() {
/* 165 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<UnsupportedSoftware> getUnsupportedSoftwareClasses() {
/* 170 */     return super.getUnsupportedSoftwareClasses();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPlugin(String s) {
/* 175 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonObject getDump() {
/* 180 */     if (ViaLoadingBase.getInstance().getDumpSupplier() == null) return new JsonObject();
/*     */     
/* 182 */     return ViaLoadingBase.getInstance().getDumpSupplier().get();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\vialoadingbase\platform\ViaVersionPlatformImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
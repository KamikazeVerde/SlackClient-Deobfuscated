/*     */ package com.viaversion.viaversion.api.platform;
/*     */ 
/*     */ import com.viaversion.viaversion.api.ViaAPI;
/*     */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*     */ import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
/*     */ import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import java.io.File;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface ViaPlatform<T>
/*     */ {
/*     */   Logger getLogger();
/*     */   
/*     */   String getPlatformName();
/*     */   
/*     */   String getPlatformVersion();
/*     */   
/*     */   default boolean isProxy() {
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getPluginVersion();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PlatformTask runAsync(Runnable paramRunnable);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PlatformTask runRepeatingAsync(Runnable paramRunnable, long paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PlatformTask runSync(Runnable paramRunnable);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PlatformTask runSync(Runnable paramRunnable, long paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PlatformTask runRepeatingSync(Runnable paramRunnable, long paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ViaCommandSender[] getOnlinePlayers();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void sendMessage(UUID paramUUID, String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean kickPlayer(UUID paramUUID, String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean disconnect(UserConnection connection, String message) {
/* 156 */     if (connection.isClientSide()) return false; 
/* 157 */     UUID uuid = connection.getProtocolInfo().getUuid();
/* 158 */     if (uuid == null) return false; 
/* 159 */     return kickPlayer(uuid, message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isPluginEnabled();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ViaAPI<T> getApi();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ViaVersionConfig getConf();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ConfigurationProvider getConfigurationProvider();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   File getDataFolder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void onReload();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JsonObject getDump();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isOldClientsAllowed();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default Collection<UnsupportedSoftware> getUnsupportedSoftwareClasses() {
/* 225 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   boolean hasPlugin(String paramString);
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\platform\ViaPlatform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.viaversion.viaversion.api;
/*     */ 
/*     */ import com.viaversion.viaversion.api.command.ViaVersionCommand;
/*     */ import com.viaversion.viaversion.api.connection.ConnectionManager;
/*     */ import com.viaversion.viaversion.api.debug.DebugHandler;
/*     */ import com.viaversion.viaversion.api.platform.ViaInjector;
/*     */ import com.viaversion.viaversion.api.platform.ViaPlatform;
/*     */ import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
/*     */ import com.viaversion.viaversion.api.platform.providers.ViaProviders;
/*     */ import com.viaversion.viaversion.api.protocol.ProtocolManager;
/*     */ import com.viaversion.viaversion.api.scheduler.Scheduler;
/*     */ import java.util.Set;
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
/*     */ public interface ViaManager
/*     */ {
/*     */   ProtocolManager getProtocolManager();
/*     */   
/*     */   ViaPlatform<?> getPlatform();
/*     */   
/*     */   ConnectionManager getConnectionManager();
/*     */   
/*     */   ViaProviders getProviders();
/*     */   
/*     */   ViaInjector getInjector();
/*     */   
/*     */   ViaVersionCommand getCommandHandler();
/*     */   
/*     */   ViaPlatformLoader getLoader();
/*     */   
/*     */   Scheduler getScheduler();
/*     */   
/*     */   default boolean isDebug() {
/* 100 */     return debugHandler().enabled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void setDebug(boolean debug) {
/* 110 */     debugHandler().setEnabled(debug);
/*     */   }
/*     */   
/*     */   DebugHandler debugHandler();
/*     */   
/*     */   Set<String> getSubPlatforms();
/*     */   
/*     */   void addEnableListener(Runnable paramRunnable);
/*     */   
/*     */   boolean isInitialized();
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\ViaManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
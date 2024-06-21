/*    */ package com.viaversion.viabackwards;
/*    */ 
/*    */ import com.google.inject.Inject;
/*    */ import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.sponge.util.LoggerWrapper;
/*    */ import java.io.File;
/*    */ import java.nio.file.Path;
/*    */ import java.util.logging.Logger;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.spongepowered.api.config.ConfigDir;
/*    */ import org.spongepowered.api.event.Listener;
/*    */ import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
/*    */ import org.spongepowered.plugin.builtin.jvm.Plugin;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Plugin("viabackwards")
/*    */ public class SpongePlugin
/*    */   implements ViaBackwardsPlatform
/*    */ {
/*    */   private final Logger logger;
/*    */   @Inject
/*    */   @ConfigDir(sharedRoot = false)
/*    */   private Path configPath;
/*    */   
/*    */   @Inject
/*    */   SpongePlugin(Logger logger) {
/* 45 */     this.logger = (Logger)new LoggerWrapper(logger);
/*    */   }
/*    */   
/*    */   @Listener
/*    */   public void constructPlugin(ConstructPluginEvent event) {
/* 50 */     Via.getManager().addEnableListener(() -> init(getDataFolder()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void disable() {}
/*    */ 
/*    */   
/*    */   public File getDataFolder() {
/* 59 */     return this.configPath.toFile();
/*    */   }
/*    */ 
/*    */   
/*    */   public Logger getLogger() {
/* 64 */     return this.logger;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\SpongePlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
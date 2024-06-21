/*    */ package com.viaversion.viabackwards;
/*    */ 
/*    */ import com.google.inject.Inject;
/*    */ import com.velocitypowered.api.event.PostOrder;
/*    */ import com.velocitypowered.api.event.Subscribe;
/*    */ import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
/*    */ import com.velocitypowered.api.plugin.Dependency;
/*    */ import com.velocitypowered.api.plugin.Plugin;
/*    */ import com.velocitypowered.api.plugin.annotation.DataDirectory;
/*    */ import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.velocity.util.LoggerWrapper;
/*    */ import java.io.File;
/*    */ import java.nio.file.Path;
/*    */ import java.util.logging.Logger;
/*    */ import org.slf4j.Logger;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Plugin(id = "viabackwards", name = "ViaBackwards", version = "4.7.0-1.20-pre5-SNAPSHOT", authors = {"Matsv", "kennytv", "Gerrygames", "creeper123123321", "ForceUpdate1"}, description = "Allow older Minecraft versions to connect to a newer server version.", dependencies = {@Dependency(id = "viaversion")})
/*    */ public class VelocityPlugin
/*    */   implements ViaBackwardsPlatform
/*    */ {
/*    */   private Logger logger;
/*    */   @Inject
/*    */   private Logger loggerSlf4j;
/*    */   @Inject
/*    */   @DataDirectory
/*    */   private Path configPath;
/*    */   
/*    */   @Subscribe(order = PostOrder.LATE)
/*    */   public void onProxyStart(ProxyInitializeEvent event) {
/* 54 */     this.logger = (Logger)new LoggerWrapper(this.loggerSlf4j);
/* 55 */     Via.getManager().addEnableListener(() -> init(getDataFolder()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void disable() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public File getDataFolder() {
/* 65 */     return this.configPath.toFile();
/*    */   }
/*    */ 
/*    */   
/*    */   public Logger getLogger() {
/* 70 */     return this.logger;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\VelocityPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
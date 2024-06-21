/*    */ package com.viaversion.viabackwards;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
/*    */ import com.viaversion.viabackwards.fabric.util.LoggerWrapper;
/*    */ import java.io.File;
/*    */ import java.nio.file.Path;
/*    */ import java.util.logging.Logger;
/*    */ import net.fabricmc.loader.api.FabricLoader;
/*    */ import org.apache.logging.log4j.LogManager;
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
/*    */ public class ViaFabricAddon
/*    */   implements ViaBackwardsPlatform, Runnable
/*    */ {
/* 30 */   private final Logger logger = (Logger)new LoggerWrapper(LogManager.getLogger("ViaBackwards"));
/*    */   
/*    */   private File configDir;
/*    */   
/*    */   public void run() {
/* 35 */     Path configDirPath = FabricLoader.getInstance().getConfigDir().resolve("ViaBackwards");
/* 36 */     this.configDir = configDirPath.toFile();
/* 37 */     init(getDataFolder());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void disable() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public File getDataFolder() {
/* 47 */     return this.configDir;
/*    */   }
/*    */ 
/*    */   
/*    */   public Logger getLogger() {
/* 52 */     return this.logger;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\ViaFabricAddon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
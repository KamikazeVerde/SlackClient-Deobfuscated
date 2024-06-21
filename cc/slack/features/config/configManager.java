/*     */ package cc.slack.features.config;
/*     */ 
/*     */ import cc.slack.utils.other.PrintUtil;
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.Minecraft;
/*     */ 
/*     */ public class configManager {
/*  12 */   public static final Map<String, Config> configs = new HashMap<>();
/*     */   private Config activeConfig;
/*  14 */   private static final File configFolder = new File((Minecraft.getMinecraft()).mcDataDir, "/SlackClient/configs");
/*     */   public Config getActiveConfig() {
/*  16 */     return this.activeConfig;
/*     */   }
/*  18 */   public static String currentConfig = "default";
/*     */   
/*     */   private static void refresh() {
/*  21 */     for (File file : (File[])Objects.<File[]>requireNonNull(configFolder.listFiles())) {
/*  22 */       if (file.isFile() && file.getName().endsWith(".json")) {
/*  23 */         String name = file.getName().replaceAll(".json", "");
/*  24 */         Config config = new Config(name);
/*  25 */         configs.put(config.getName(), config);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void init() {
/*  31 */     if (!configFolder.mkdirs()) {
/*  32 */       PrintUtil.print("Failed to make config folder");
/*     */       
/*     */       return;
/*     */     } 
/*  36 */     refresh();
/*     */     
/*  38 */     if (getConfig("default") == null)
/*  39 */     { Config config = new Config("default");
/*  40 */       config.write();
/*  41 */       configs.put(config.getName(), config); }
/*  42 */     else { getConfig("default").read(); }
/*     */   
/*     */   }
/*     */   public static void stop() {
/*  46 */     if (getConfig(currentConfig) == null)
/*  47 */     { Config config = new Config(currentConfig);
/*  48 */       config.write(); }
/*  49 */     else { getConfig(currentConfig).write(); }
/*     */   
/*     */   }
/*     */   public static Config getConfig(String name) {
/*  53 */     return configs.keySet().stream().filter(key -> key.equalsIgnoreCase(name)).findFirst().map(configs::get).orElse(null);
/*     */   }
/*     */   
/*     */   public static Set<String> getConfigList() {
/*  57 */     return configs.keySet();
/*     */   }
/*     */   
/*     */   public static void saveConfig(String configName) {
/*  61 */     if (configName == "default") {
/*  62 */       PrintUtil.message("Cannot save config as 'default'.");
/*     */       return;
/*     */     } 
/*     */     try {
/*  66 */       if (getConfig(configName) == null)
/*  67 */       { Config config = new Config(configName);
/*  68 */         config.write(); }
/*  69 */       else { getConfig(configName).write(); } 
/*  70 */     } catch (Exception e) {
/*  71 */       PrintUtil.message("Failed to save config.");
/*  72 */       PrintUtil.message(e.getMessage());
/*     */     } 
/*     */     
/*  75 */     PrintUtil.print("Saved config " + configName + ".");
/*     */   }
/*     */   
/*     */   public static boolean delete(String configName) {
/*  79 */     Config existingConfig = getConfig(configName);
/*     */     
/*  81 */     if (configName == "default" || configName == currentConfig || existingConfig == null) {
/*  82 */       PrintUtil.message("Cannot delete config: " + configName + ".");
/*  83 */       return false;
/*     */     } 
/*     */     
/*  86 */     File configFile = new File(existingConfig.getDirectory().toString());
/*     */     
/*  88 */     if (configFile.exists()) {
/*  89 */       boolean deleted = configFile.delete();
/*  90 */       if (!deleted) {
/*  91 */         PrintUtil.message("Error: Unable to delete the config file");
/*  92 */         return false;
/*     */       } 
/*     */     } 
/*  95 */     return true;
/*     */   }
/*     */   
/*     */   public static void loadConfig(String configName) {
/*  99 */     refresh();
/*     */     
/* 101 */     if (getConfig(configName) != null) {
/* 102 */       PrintUtil.message("Loaded config " + configName + ".");
/* 103 */       getConfig(configName).read();
/*     */     } else {
/* 105 */       PrintUtil.message("Failed to load config.");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\config\configManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
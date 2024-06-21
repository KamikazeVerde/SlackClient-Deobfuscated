/*     */ package com.viaversion.viaversion.bungee.platform;
/*     */ 
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import com.viaversion.viaversion.bungee.providers.BungeeVersionProvider;
/*     */ import com.viaversion.viaversion.configuration.AbstractViaConfig;
/*     */ import java.io.File;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class BungeeViaConfig
/*     */   extends AbstractViaConfig
/*     */ {
/*  31 */   private static final List<String> UNSUPPORTED = Arrays.asList(new String[] { "nms-player-ticking", "item-cache", "quick-move-action-fix", "velocity-ping-interval", "velocity-ping-save", "velocity-servers", "blockconnection-method", "change-1_9-hitbox", "change-1_14-hitbox" });
/*     */   private int bungeePingInterval;
/*     */   private boolean bungeePingSave;
/*     */   private Map<String, Integer> bungeeServerProtocols;
/*     */   
/*     */   public BungeeViaConfig(File configFile) {
/*  37 */     super(new File(configFile, "config.yml"));
/*  38 */     reloadConfig();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadFields() {
/*  43 */     super.loadFields();
/*  44 */     this.bungeePingInterval = getInt("bungee-ping-interval", 60);
/*  45 */     this.bungeePingSave = getBoolean("bungee-ping-save", true);
/*  46 */     this.bungeeServerProtocols = (Map<String, Integer>)get("bungee-servers", Map.class, new HashMap<>());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleConfig(Map<String, Object> config) {
/*     */     Map<String, Object> servers;
/*  53 */     if (!(config.get("bungee-servers") instanceof Map)) {
/*  54 */       servers = new HashMap<>();
/*     */     } else {
/*  56 */       servers = (Map<String, Object>)config.get("bungee-servers");
/*     */     } 
/*     */     
/*  59 */     for (Map.Entry<String, Object> entry : (Iterable<Map.Entry<String, Object>>)new HashSet(servers.entrySet())) {
/*  60 */       if (!(entry.getValue() instanceof Integer)) {
/*  61 */         if (entry.getValue() instanceof String) {
/*  62 */           ProtocolVersion found = ProtocolVersion.getClosest((String)entry.getValue());
/*  63 */           if (found != null) {
/*  64 */             servers.put(entry.getKey(), Integer.valueOf(found.getVersion())); continue;
/*     */           } 
/*  66 */           servers.remove(entry.getKey());
/*     */           continue;
/*     */         } 
/*  69 */         servers.remove(entry.getKey());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  74 */     if (!servers.containsKey("default")) {
/*  75 */       servers.put("default", Integer.valueOf(BungeeVersionProvider.getLowestSupportedVersion()));
/*     */     }
/*     */     
/*  78 */     config.put("bungee-servers", servers);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getUnsupportedOptions() {
/*  83 */     return UNSUPPORTED;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isItemCache() {
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNMSPlayerTicking() {
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBungeePingInterval() {
/* 103 */     return this.bungeePingInterval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBungeePingSave() {
/* 112 */     return this.bungeePingSave;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Integer> getBungeeServerProtocols() {
/* 122 */     return this.bungeeServerProtocols;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\platform\BungeeViaConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
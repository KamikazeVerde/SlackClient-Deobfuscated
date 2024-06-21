/*     */ package com.viaversion.viaversion.velocity.platform;
/*     */ 
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
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
/*     */ public class VelocityViaConfig
/*     */   extends AbstractViaConfig
/*     */ {
/*  30 */   private static final List<String> UNSUPPORTED = Arrays.asList(new String[] { "nms-player-ticking", "item-cache", "quick-move-action-fix", "bungee-ping-interval", "bungee-ping-save", "bungee-servers", "blockconnection-method", "change-1_9-hitbox", "change-1_14-hitbox" });
/*     */   private int velocityPingInterval;
/*     */   private boolean velocityPingSave;
/*     */   private Map<String, Integer> velocityServerProtocols;
/*     */   
/*     */   public VelocityViaConfig(File configFile) {
/*  36 */     super(new File(configFile, "config.yml"));
/*  37 */     reloadConfig();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadFields() {
/*  42 */     super.loadFields();
/*  43 */     this.velocityPingInterval = getInt("velocity-ping-interval", 60);
/*  44 */     this.velocityPingSave = getBoolean("velocity-ping-save", true);
/*  45 */     this.velocityServerProtocols = (Map<String, Integer>)get("velocity-servers", Map.class, new HashMap<>());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleConfig(Map<String, Object> config) {
/*     */     Map<String, Object> servers;
/*  52 */     if (!(config.get("velocity-servers") instanceof Map)) {
/*  53 */       servers = new HashMap<>();
/*     */     } else {
/*  55 */       servers = (Map<String, Object>)config.get("velocity-servers");
/*     */     } 
/*     */     
/*  58 */     for (Map.Entry<String, Object> entry : (Iterable<Map.Entry<String, Object>>)new HashSet(servers.entrySet())) {
/*  59 */       if (!(entry.getValue() instanceof Integer)) {
/*  60 */         if (entry.getValue() instanceof String) {
/*  61 */           ProtocolVersion found = ProtocolVersion.getClosest((String)entry.getValue());
/*  62 */           if (found != null) {
/*  63 */             servers.put(entry.getKey(), Integer.valueOf(found.getVersion())); continue;
/*     */           } 
/*  65 */           servers.remove(entry.getKey());
/*     */           continue;
/*     */         } 
/*  68 */         servers.remove(entry.getKey());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  73 */     if (!servers.containsKey("default")) {
/*     */       
/*     */       try {
/*  76 */         servers.put("default", Integer.valueOf(VelocityViaInjector.getLowestSupportedProtocolVersion()));
/*  77 */       } catch (Exception e) {
/*     */         
/*  79 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  83 */     config.put("velocity-servers", servers);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getUnsupportedOptions() {
/*  88 */     return UNSUPPORTED;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isItemCache() {
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNMSPlayerTicking() {
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVelocityPingInterval() {
/* 108 */     return this.velocityPingInterval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVelocityPingSave() {
/* 117 */     return this.velocityPingSave;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Integer> getVelocityServerProtocols() {
/* 127 */     return this.velocityServerProtocols;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\velocity\platform\VelocityViaConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
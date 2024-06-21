/*     */ package com.viaversion.viabackwards;
/*     */ 
/*     */ import com.viaversion.viabackwards.api.ViaBackwardsConfig;
/*     */ import com.viaversion.viaversion.util.Config;
/*     */ import java.io.File;
/*     */ import java.net.URL;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
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
/*     */ public class ViaBackwardsConfig
/*     */   extends Config
/*     */   implements ViaBackwardsConfig
/*     */ {
/*     */   private boolean addCustomEnchantsToLore;
/*     */   private boolean addTeamColorToPrefix;
/*     */   private boolean fix1_13FacePlayer;
/*     */   private boolean alwaysShowOriginalMobName;
/*     */   private boolean fix1_13FormattedInventoryTitles;
/*     */   private boolean handlePingsAsInvAcknowledgements;
/*     */   private Map<String, String> chatTypeFormats;
/*     */   
/*     */   public ViaBackwardsConfig(File configFile) {
/*  40 */     super(configFile);
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadConfig() {
/*  45 */     super.reloadConfig();
/*  46 */     loadFields();
/*     */   }
/*     */   
/*     */   private void loadFields() {
/*  50 */     this.addCustomEnchantsToLore = getBoolean("add-custom-enchants-into-lore", true);
/*  51 */     this.addTeamColorToPrefix = getBoolean("add-teamcolor-to-prefix", true);
/*  52 */     this.fix1_13FacePlayer = getBoolean("fix-1_13-face-player", false);
/*  53 */     this.fix1_13FormattedInventoryTitles = getBoolean("fix-formatted-inventory-titles", true);
/*  54 */     this.alwaysShowOriginalMobName = getBoolean("always-show-original-mob-name", true);
/*  55 */     this.handlePingsAsInvAcknowledgements = getBoolean("handle-pings-as-inv-acknowledgements", false);
/*  56 */     this.chatTypeFormats = (Map<String, String>)get("chat-types-1_19_1", Map.class, new HashMap<>());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addCustomEnchantsToLore() {
/*  61 */     return this.addCustomEnchantsToLore;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addTeamColorTo1_13Prefix() {
/*  66 */     return this.addTeamColorToPrefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFix1_13FacePlayer() {
/*  71 */     return this.fix1_13FacePlayer;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean fix1_13FormattedInventoryTitle() {
/*  76 */     return this.fix1_13FormattedInventoryTitles;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean alwaysShowOriginalMobName() {
/*  81 */     return this.alwaysShowOriginalMobName;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handlePingsAsInvAcknowledgements() {
/*  86 */     return (this.handlePingsAsInvAcknowledgements || Boolean.getBoolean("com.viaversion.handlePingsAsInvAcknowledgements"));
/*     */   }
/*     */ 
/*     */   
/*     */   public String chatTypeFormat(String translationKey) {
/*  91 */     return this.chatTypeFormats.get(translationKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public URL getDefaultConfigURL() {
/*  96 */     return getClass().getClassLoader().getResource("assets/viabackwards/config.yml");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleConfig(Map<String, Object> map) {}
/*     */ 
/*     */   
/*     */   public List<String> getUnsupportedOptions() {
/* 105 */     return Collections.emptyList();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\ViaBackwardsConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
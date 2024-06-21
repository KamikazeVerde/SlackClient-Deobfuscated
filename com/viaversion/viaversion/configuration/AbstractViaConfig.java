/*     */ package com.viaversion.viaversion.configuration;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
/*     */ import com.viaversion.viaversion.api.minecraft.WorldIdentifiers;
/*     */ import com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions;
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
/*     */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.protocol.BlockedProtocolVersionsImpl;
/*     */ import com.viaversion.viaversion.util.Config;
/*     */ import java.io.File;
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
/*     */ 
/*     */ 
/*     */ public abstract class AbstractViaConfig
/*     */   extends Config
/*     */   implements ViaVersionConfig
/*     */ {
/*     */   private boolean checkForUpdates;
/*     */   private boolean preventCollision;
/*     */   private boolean useNewEffectIndicator;
/*     */   private boolean useNewDeathmessages;
/*     */   private boolean suppressMetadataErrors;
/*     */   private boolean shieldBlocking;
/*     */   private boolean noDelayShieldBlocking;
/*     */   private boolean showShieldWhenSwordInHand;
/*     */   private boolean hologramPatch;
/*     */   private boolean pistonAnimationPatch;
/*     */   private boolean bossbarPatch;
/*     */   private boolean bossbarAntiFlicker;
/*     */   private double hologramOffset;
/*     */   private int maxPPS;
/*     */   private String maxPPSKickMessage;
/*     */   private int trackingPeriod;
/*     */   private int warningPPS;
/*     */   private int maxPPSWarnings;
/*     */   private String maxPPSWarningsKickMessage;
/*     */   private boolean sendSupportedVersions;
/*     */   private boolean simulatePlayerTick;
/*     */   private boolean itemCache;
/*     */   private boolean nmsPlayerTicking;
/*     */   private boolean replacePistons;
/*     */   private int pistonReplacementId;
/*     */   private boolean chunkBorderFix;
/*     */   private boolean autoTeam;
/*     */   private boolean forceJsonTransform;
/*     */   private boolean nbtArrayFix;
/*     */   private BlockedProtocolVersions blockedProtocolVersions;
/*     */   private String blockedDisconnectMessage;
/*     */   private String reloadDisconnectMessage;
/*     */   private boolean suppressConversionWarnings;
/*     */   private boolean disable1_13TabComplete;
/*     */   private boolean minimizeCooldown;
/*     */   private boolean teamColourFix;
/*     */   private boolean serversideBlockConnections;
/*     */   private boolean reduceBlockStorageMemory;
/*     */   private boolean flowerStemWhenBlockAbove;
/*     */   private boolean vineClimbFix;
/*     */   private boolean snowCollisionFix;
/*     */   private boolean infestedBlocksFix;
/*     */   private int tabCompleteDelay;
/*     */   private boolean truncate1_14Books;
/*     */   private boolean leftHandedHandling;
/*     */   private boolean fullBlockLightFix;
/*     */   private boolean healthNaNFix;
/*     */   private boolean instantRespawn;
/*     */   private boolean ignoreLongChannelNames;
/*     */   private boolean forcedUse1_17ResourcePack;
/*     */   private JsonElement resourcePack1_17PromptMessage;
/*     */   private WorldIdentifiers map1_16WorldNames;
/*     */   private boolean cache1_17Light;
/*     */   private Map<String, String> chatTypeFormats;
/*     */   
/*     */   protected AbstractViaConfig(File configFile) {
/*  95 */     super(configFile);
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadConfig() {
/* 100 */     super.reloadConfig();
/* 101 */     loadFields();
/*     */   }
/*     */   
/*     */   protected void loadFields() {
/* 105 */     this.checkForUpdates = getBoolean("checkforupdates", true);
/* 106 */     this.preventCollision = getBoolean("prevent-collision", true);
/* 107 */     this.useNewEffectIndicator = getBoolean("use-new-effect-indicator", true);
/* 108 */     this.useNewDeathmessages = getBoolean("use-new-deathmessages", true);
/* 109 */     this.suppressMetadataErrors = getBoolean("suppress-metadata-errors", false);
/* 110 */     this.shieldBlocking = getBoolean("shield-blocking", true);
/* 111 */     this.noDelayShieldBlocking = getBoolean("no-delay-shield-blocking", false);
/* 112 */     this.showShieldWhenSwordInHand = getBoolean("show-shield-when-sword-in-hand", false);
/* 113 */     this.hologramPatch = getBoolean("hologram-patch", false);
/* 114 */     this.pistonAnimationPatch = getBoolean("piston-animation-patch", false);
/* 115 */     this.bossbarPatch = getBoolean("bossbar-patch", true);
/* 116 */     this.bossbarAntiFlicker = getBoolean("bossbar-anti-flicker", false);
/* 117 */     this.hologramOffset = getDouble("hologram-y", -0.96D);
/* 118 */     this.maxPPS = getInt("max-pps", 800);
/* 119 */     this.maxPPSKickMessage = getString("max-pps-kick-msg", "Sending packets too fast? lag?");
/* 120 */     this.trackingPeriod = getInt("tracking-period", 6);
/* 121 */     this.warningPPS = getInt("tracking-warning-pps", 120);
/* 122 */     this.maxPPSWarnings = getInt("tracking-max-warnings", 3);
/* 123 */     this.maxPPSWarningsKickMessage = getString("tracking-max-kick-msg", "You are sending too many packets, :(");
/* 124 */     this.sendSupportedVersions = getBoolean("send-supported-versions", false);
/* 125 */     this.simulatePlayerTick = getBoolean("simulate-pt", true);
/* 126 */     this.itemCache = getBoolean("item-cache", true);
/* 127 */     this.nmsPlayerTicking = getBoolean("nms-player-ticking", true);
/* 128 */     this.replacePistons = getBoolean("replace-pistons", false);
/* 129 */     this.pistonReplacementId = getInt("replacement-piston-id", 0);
/* 130 */     this.chunkBorderFix = getBoolean("chunk-border-fix", false);
/* 131 */     this.autoTeam = getBoolean("auto-team", true);
/* 132 */     this.forceJsonTransform = getBoolean("force-json-transform", false);
/* 133 */     this.nbtArrayFix = getBoolean("chat-nbt-fix", true);
/* 134 */     this.blockedProtocolVersions = loadBlockedProtocolVersions();
/* 135 */     this.blockedDisconnectMessage = getString("block-disconnect-msg", "You are using an unsupported Minecraft version!");
/* 136 */     this.reloadDisconnectMessage = getString("reload-disconnect-msg", "Server reload, please rejoin!");
/* 137 */     this.minimizeCooldown = getBoolean("minimize-cooldown", true);
/* 138 */     this.teamColourFix = getBoolean("team-colour-fix", true);
/* 139 */     this.suppressConversionWarnings = getBoolean("suppress-conversion-warnings", false);
/* 140 */     this.disable1_13TabComplete = getBoolean("disable-1_13-auto-complete", false);
/* 141 */     this.serversideBlockConnections = getBoolean("serverside-blockconnections", true);
/* 142 */     this.reduceBlockStorageMemory = getBoolean("reduce-blockstorage-memory", false);
/* 143 */     this.flowerStemWhenBlockAbove = getBoolean("flowerstem-when-block-above", false);
/* 144 */     this.vineClimbFix = getBoolean("vine-climb-fix", false);
/* 145 */     this.snowCollisionFix = getBoolean("fix-low-snow-collision", false);
/* 146 */     this.infestedBlocksFix = getBoolean("fix-infested-block-breaking", true);
/* 147 */     this.tabCompleteDelay = getInt("1_13-tab-complete-delay", 0);
/* 148 */     this.truncate1_14Books = getBoolean("truncate-1_14-books", false);
/* 149 */     this.leftHandedHandling = getBoolean("left-handed-handling", true);
/* 150 */     this.fullBlockLightFix = getBoolean("fix-non-full-blocklight", false);
/* 151 */     this.healthNaNFix = getBoolean("fix-1_14-health-nan", true);
/* 152 */     this.instantRespawn = getBoolean("use-1_15-instant-respawn", false);
/* 153 */     this.ignoreLongChannelNames = getBoolean("ignore-long-1_16-channel-names", true);
/* 154 */     this.forcedUse1_17ResourcePack = getBoolean("forced-use-1_17-resource-pack", false);
/* 155 */     this.resourcePack1_17PromptMessage = getSerializedComponent("resource-pack-1_17-prompt");
/* 156 */     Map<String, String> worlds = (Map<String, String>)get("map-1_16-world-names", Map.class, new HashMap<>());
/* 157 */     this
/*     */       
/* 159 */       .map1_16WorldNames = new WorldIdentifiers(worlds.getOrDefault("overworld", "minecraft:overworld"), worlds.getOrDefault("nether", "minecraft:the_nether"), worlds.getOrDefault("end", "minecraft:the_end"));
/* 160 */     this.cache1_17Light = getBoolean("cache-1_17-light", true);
/* 161 */     this.chatTypeFormats = (Map<String, String>)get("chat-types-1_19", Map.class, new HashMap<>());
/*     */   }
/*     */   
/*     */   private BlockedProtocolVersions loadBlockedProtocolVersions() {
/* 165 */     List<Integer> blockProtocols = getListSafe("block-protocols", Integer.class, "Invalid blocked version protocol found in config: '%s'");
/* 166 */     List<String> blockVersions = getListSafe("block-versions", String.class, "Invalid blocked version found in config: '%s'");
/* 167 */     IntOpenHashSet intOpenHashSet = new IntOpenHashSet(blockProtocols);
/* 168 */     int lowerBound = -1;
/* 169 */     int upperBound = -1;
/* 170 */     for (String s : blockVersions) {
/* 171 */       if (s.isEmpty()) {
/*     */         continue;
/*     */       }
/*     */       
/* 175 */       char c = s.charAt(0);
/* 176 */       if (c == '<' || c == '>') {
/*     */         
/* 178 */         ProtocolVersion protocolVersion1 = protocolVersion(s.substring(1));
/* 179 */         if (protocolVersion1 == null) {
/*     */           continue;
/*     */         }
/*     */         
/* 183 */         if (c == '<') {
/* 184 */           if (lowerBound != -1) {
/* 185 */             Via.getPlatform().getLogger().warning("Already set lower bound " + lowerBound + " overridden by " + protocolVersion1.getName());
/*     */           }
/* 187 */           lowerBound = protocolVersion1.getVersion(); continue;
/*     */         } 
/* 189 */         if (upperBound != -1) {
/* 190 */           Via.getPlatform().getLogger().warning("Already set upper bound " + upperBound + " overridden by " + protocolVersion1.getName());
/*     */         }
/* 192 */         upperBound = protocolVersion1.getVersion();
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 197 */       ProtocolVersion protocolVersion = protocolVersion(s);
/* 198 */       if (protocolVersion == null) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 203 */       if (!intOpenHashSet.add(protocolVersion.getVersion())) {
/* 204 */         Via.getPlatform().getLogger().warning("Duplicated blocked protocol version " + protocolVersion.getName() + "/" + protocolVersion.getVersion());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 209 */     if (lowerBound != -1 || upperBound != -1) {
/* 210 */       int finalLowerBound = lowerBound;
/* 211 */       int finalUpperBound = upperBound;
/* 212 */       intOpenHashSet.removeIf(version -> {
/*     */             if ((finalLowerBound != -1 && version < finalLowerBound) || (finalUpperBound != -1 && version > finalUpperBound)) {
/*     */               ProtocolVersion protocolVersion = ProtocolVersion.getProtocol(version);
/*     */               
/*     */               Via.getPlatform().getLogger().warning("Blocked protocol version " + protocolVersion.getName() + "/" + protocolVersion.getVersion() + " already covered by upper or lower bound");
/*     */               return true;
/*     */             } 
/*     */             return false;
/*     */           });
/*     */     } 
/* 222 */     return (BlockedProtocolVersions)new BlockedProtocolVersionsImpl((IntSet)intOpenHashSet, lowerBound, upperBound);
/*     */   }
/*     */   
/*     */   private ProtocolVersion protocolVersion(String s) {
/* 226 */     ProtocolVersion protocolVersion = ProtocolVersion.getClosest(s);
/* 227 */     if (protocolVersion == null) {
/* 228 */       Via.getPlatform().getLogger().warning("Unknown protocol version in block-versions: " + s);
/* 229 */       return null;
/*     */     } 
/* 231 */     return protocolVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCheckForUpdates() {
/* 236 */     return this.checkForUpdates;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCheckForUpdates(boolean checkForUpdates) {
/* 241 */     this.checkForUpdates = checkForUpdates;
/* 242 */     set("checkforupdates", Boolean.valueOf(checkForUpdates));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPreventCollision() {
/* 247 */     return this.preventCollision;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNewEffectIndicator() {
/* 252 */     return this.useNewEffectIndicator;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShowNewDeathMessages() {
/* 257 */     return this.useNewDeathmessages;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSuppressMetadataErrors() {
/* 262 */     return this.suppressMetadataErrors;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShieldBlocking() {
/* 267 */     return this.shieldBlocking;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNoDelayShieldBlocking() {
/* 272 */     return this.noDelayShieldBlocking;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShowShieldWhenSwordInHand() {
/* 277 */     return this.showShieldWhenSwordInHand;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHologramPatch() {
/* 282 */     return this.hologramPatch;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPistonAnimationPatch() {
/* 287 */     return this.pistonAnimationPatch;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBossbarPatch() {
/* 292 */     return this.bossbarPatch;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBossbarAntiflicker() {
/* 297 */     return this.bossbarAntiFlicker;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getHologramYOffset() {
/* 302 */     return this.hologramOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxPPS() {
/* 307 */     return this.maxPPS;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMaxPPSKickMessage() {
/* 312 */     return this.maxPPSKickMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTrackingPeriod() {
/* 317 */     return this.trackingPeriod;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWarningPPS() {
/* 322 */     return this.warningPPS;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxWarnings() {
/* 327 */     return this.maxPPSWarnings;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMaxWarningsKickMessage() {
/* 332 */     return this.maxPPSWarningsKickMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSendSupportedVersions() {
/* 337 */     return this.sendSupportedVersions;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSimulatePlayerTick() {
/* 342 */     return this.simulatePlayerTick;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isItemCache() {
/* 347 */     return this.itemCache;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNMSPlayerTicking() {
/* 352 */     return this.nmsPlayerTicking;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReplacePistons() {
/* 357 */     return this.replacePistons;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPistonReplacementId() {
/* 362 */     return this.pistonReplacementId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isChunkBorderFix() {
/* 367 */     return this.chunkBorderFix;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAutoTeam() {
/* 373 */     return (this.preventCollision && this.autoTeam);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isForceJsonTransform() {
/* 378 */     return this.forceJsonTransform;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is1_12NBTArrayFix() {
/* 383 */     return this.nbtArrayFix;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRegisterUserConnectionOnJoin() {
/* 388 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is1_12QuickMoveActionFix() {
/* 393 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockedProtocolVersions blockedProtocolVersions() {
/* 398 */     return this.blockedProtocolVersions;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBlockedDisconnectMsg() {
/* 403 */     return this.blockedDisconnectMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getReloadDisconnectMsg() {
/* 408 */     return this.reloadDisconnectMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMinimizeCooldown() {
/* 413 */     return this.minimizeCooldown;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is1_13TeamColourFix() {
/* 418 */     return this.teamColourFix;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSuppressConversionWarnings() {
/* 423 */     return this.suppressConversionWarnings;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDisable1_13AutoComplete() {
/* 428 */     return this.disable1_13TabComplete;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isServersideBlockConnections() {
/* 433 */     return this.serversideBlockConnections;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBlockConnectionMethod() {
/* 438 */     return "packet";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReduceBlockStorageMemory() {
/* 443 */     return this.reduceBlockStorageMemory;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStemWhenBlockAbove() {
/* 448 */     return this.flowerStemWhenBlockAbove;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVineClimbFix() {
/* 453 */     return this.vineClimbFix;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSnowCollisionFix() {
/* 458 */     return this.snowCollisionFix;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInfestedBlocksFix() {
/* 463 */     return this.infestedBlocksFix;
/*     */   }
/*     */ 
/*     */   
/*     */   public int get1_13TabCompleteDelay() {
/* 468 */     return this.tabCompleteDelay;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTruncate1_14Books() {
/* 473 */     return this.truncate1_14Books;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLeftHandedHandling() {
/* 478 */     return this.leftHandedHandling;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is1_9HitboxFix() {
/* 483 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is1_14HitboxFix() {
/* 488 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNonFullBlockLightFix() {
/* 493 */     return this.fullBlockLightFix;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is1_14HealthNaNFix() {
/* 498 */     return this.healthNaNFix;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is1_15InstantRespawn() {
/* 503 */     return this.instantRespawn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIgnoreLong1_16ChannelNames() {
/* 508 */     return this.ignoreLongChannelNames;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isForcedUse1_17ResourcePack() {
/* 513 */     return this.forcedUse1_17ResourcePack;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonElement get1_17ResourcePackPrompt() {
/* 518 */     return this.resourcePack1_17PromptMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldIdentifiers get1_16WorldNamesMap() {
/* 523 */     return this.map1_16WorldNames;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean cache1_17Light() {
/* 528 */     return this.cache1_17Light;
/*     */   }
/*     */ 
/*     */   
/*     */   public String chatTypeFormat(String translationKey) {
/* 533 */     return this.chatTypeFormats.get(translationKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isArmorToggleFix() {
/* 538 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\configuration\AbstractViaConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.viaversion.viaversion.dump;
/*    */ 
/*    */ import java.util.Set;
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
/*    */ public class VersionInfo
/*    */ {
/*    */   private final String javaVersion;
/*    */   private final String operatingSystem;
/*    */   private final int serverProtocol;
/*    */   private final Set<Integer> enabledProtocols;
/*    */   private final String platformName;
/*    */   private final String platformVersion;
/*    */   private final String pluginVersion;
/*    */   private final String implementationVersion;
/*    */   private final Set<String> subPlatforms;
/*    */   
/*    */   public VersionInfo(String javaVersion, String operatingSystem, int serverProtocol, Set<Integer> enabledProtocols, String platformName, String platformVersion, String pluginVersion, String implementationVersion, Set<String> subPlatforms) {
/* 35 */     this.javaVersion = javaVersion;
/* 36 */     this.operatingSystem = operatingSystem;
/* 37 */     this.serverProtocol = serverProtocol;
/* 38 */     this.enabledProtocols = enabledProtocols;
/* 39 */     this.platformName = platformName;
/* 40 */     this.platformVersion = platformVersion;
/* 41 */     this.pluginVersion = pluginVersion;
/* 42 */     this.implementationVersion = implementationVersion;
/* 43 */     this.subPlatforms = subPlatforms;
/*    */   }
/*    */   
/*    */   public String getJavaVersion() {
/* 47 */     return this.javaVersion;
/*    */   }
/*    */   
/*    */   public String getOperatingSystem() {
/* 51 */     return this.operatingSystem;
/*    */   }
/*    */   
/*    */   public int getServerProtocol() {
/* 55 */     return this.serverProtocol;
/*    */   }
/*    */   
/*    */   public Set<Integer> getEnabledProtocols() {
/* 59 */     return this.enabledProtocols;
/*    */   }
/*    */   
/*    */   public String getPlatformName() {
/* 63 */     return this.platformName;
/*    */   }
/*    */   
/*    */   public String getPlatformVersion() {
/* 67 */     return this.platformVersion;
/*    */   }
/*    */   
/*    */   public String getPluginVersion() {
/* 71 */     return this.pluginVersion;
/*    */   }
/*    */   
/*    */   public String getImplementationVersion() {
/* 75 */     return this.implementationVersion;
/*    */   }
/*    */   
/*    */   public Set<String> getSubPlatforms() {
/* 79 */     return this.subPlatforms;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\dump\VersionInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
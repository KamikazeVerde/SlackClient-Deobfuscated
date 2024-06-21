/*    */ package com.viaversion.viaversion.dump;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*    */ import java.util.Map;
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
/*    */ public class DumpTemplate
/*    */ {
/*    */   private final VersionInfo versionInfo;
/*    */   private final Map<String, Object> configuration;
/*    */   private final JsonObject platformDump;
/*    */   private final JsonObject injectionDump;
/*    */   private final JsonObject playerSample;
/*    */   
/*    */   public DumpTemplate(VersionInfo versionInfo, Map<String, Object> configuration, JsonObject platformDump, JsonObject injectionDump, JsonObject playerSample) {
/* 31 */     this.versionInfo = versionInfo;
/* 32 */     this.configuration = configuration;
/* 33 */     this.platformDump = platformDump;
/* 34 */     this.injectionDump = injectionDump;
/* 35 */     this.playerSample = playerSample;
/*    */   }
/*    */   
/*    */   public VersionInfo getVersionInfo() {
/* 39 */     return this.versionInfo;
/*    */   }
/*    */   
/*    */   public Map<String, Object> getConfiguration() {
/* 43 */     return this.configuration;
/*    */   }
/*    */   
/*    */   public JsonObject getPlatformDump() {
/* 47 */     return this.platformDump;
/*    */   }
/*    */   
/*    */   public JsonObject getInjectionDump() {
/* 51 */     return this.injectionDump;
/*    */   }
/*    */   
/*    */   public JsonObject getPlayerSample() {
/* 55 */     return this.playerSample;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\dump\DumpTemplate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package de.gerrygames.viarewind.api;
/*    */ 
/*    */ import com.viaversion.viaversion.util.Config;
/*    */ import java.io.File;
/*    */ import java.net.URL;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ViaRewindConfigImpl
/*    */   extends Config implements ViaRewindConfig {
/*    */   public ViaRewindConfigImpl(File configFile) {
/* 13 */     super(configFile);
/* 14 */     reloadConfig();
/*    */   }
/*    */ 
/*    */   
/*    */   public ViaRewindConfig.CooldownIndicator getCooldownIndicator() {
/* 19 */     return ViaRewindConfig.CooldownIndicator.valueOf(getString("cooldown-indicator", "TITLE").toUpperCase());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isReplaceAdventureMode() {
/* 24 */     return getBoolean("replace-adventure", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isReplaceParticles() {
/* 29 */     return getBoolean("replace-particles", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxBookPages() {
/* 34 */     return getInt("max-book-pages", 100);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxBookPageSize() {
/* 39 */     return getInt("max-book-page-length", 5000);
/*    */   }
/*    */ 
/*    */   
/*    */   public URL getDefaultConfigURL() {
/* 44 */     return getClass().getClassLoader().getResource("assets/viarewind/config.yml");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void handleConfig(Map<String, Object> map) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getUnsupportedOptions() {
/* 54 */     return Collections.emptyList();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\api\ViaRewindConfigImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
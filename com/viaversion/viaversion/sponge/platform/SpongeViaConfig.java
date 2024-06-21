/*    */ package com.viaversion.viaversion.sponge.platform;
/*    */ 
/*    */ import com.viaversion.viaversion.configuration.AbstractViaConfig;
/*    */ import java.io.File;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
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
/*    */ public class SpongeViaConfig
/*    */   extends AbstractViaConfig
/*    */ {
/* 27 */   private static final List<String> UNSUPPORTED = Arrays.asList(new String[] { "bungee-ping-interval", "bungee-ping-save", "bungee-servers", "velocity-ping-interval", "velocity-ping-save", "velocity-servers", "quick-move-action-fix", "change-1_9-hitbox", "change-1_14-hitbox", "blockconnection-method" });
/*    */ 
/*    */ 
/*    */   
/*    */   public SpongeViaConfig(File configFile) {
/* 32 */     super(new File(configFile, "config.yml"));
/* 33 */     reloadConfig();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void handleConfig(Map<String, Object> config) {}
/*    */ 
/*    */   
/*    */   public List<String> getUnsupportedOptions() {
/* 42 */     return UNSUPPORTED;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\sponge\platform\SpongeViaConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
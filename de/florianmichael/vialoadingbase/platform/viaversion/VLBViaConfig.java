/*    */ package de.florianmichael.vialoadingbase.platform.viaversion;
/*    */ 
/*    */ import com.viaversion.viaversion.configuration.AbstractViaConfig;
/*    */ import java.io.File;
/*    */ import java.net.URL;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VLBViaConfig
/*    */   extends AbstractViaConfig
/*    */ {
/* 31 */   private static final List<String> UNSUPPORTED = Arrays.asList(new String[] { "anti-xray-patch", "bungee-ping-interval", 
/* 32 */         "bungee-ping-save", "bungee-servers", "quick-move-action-fix", "nms-player-ticking", 
/* 33 */         "velocity-ping-interval", "velocity-ping-save", "velocity-servers", 
/* 34 */         "blockconnection-method", "change-1_9-hitbox", "change-1_14-hitbox", 
/* 35 */         "show-shield-when-sword-in-hand", "left-handed-handling" });
/*    */ 
/*    */   
/*    */   public VLBViaConfig(File configFile) {
/* 39 */     super(configFile);
/*    */     
/* 41 */     reloadConfig();
/*    */   }
/*    */ 
/*    */   
/*    */   public URL getDefaultConfigURL() {
/* 46 */     return getClass().getClassLoader().getResource("assets/viaversion/config.yml");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void handleConfig(Map<String, Object> config) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getUnsupportedOptions() {
/* 56 */     return UNSUPPORTED;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\vialoadingbase\platform\viaversion\VLBViaConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
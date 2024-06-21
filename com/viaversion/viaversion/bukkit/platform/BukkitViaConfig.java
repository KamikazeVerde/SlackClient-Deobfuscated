/*    */ package com.viaversion.viaversion.bukkit.platform;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.configuration.AbstractViaConfig;
/*    */ import java.io.File;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.bukkit.plugin.Plugin;
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
/*    */ public class BukkitViaConfig
/*    */   extends AbstractViaConfig
/*    */ {
/* 29 */   private static final List<String> UNSUPPORTED = Arrays.asList(new String[] { "bungee-ping-interval", "bungee-ping-save", "bungee-servers", "velocity-ping-interval", "velocity-ping-save", "velocity-servers" });
/*    */   private boolean quickMoveActionFix;
/*    */   private boolean hitboxFix1_9;
/*    */   private boolean hitboxFix1_14;
/*    */   private String blockConnectionMethod;
/*    */   private boolean armorToggleFix;
/*    */   private boolean registerUserConnectionOnJoin;
/*    */   
/*    */   public BukkitViaConfig() {
/* 38 */     super(new File(((Plugin)Via.getPlatform()).getDataFolder(), "config.yml"));
/* 39 */     reloadConfig();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void loadFields() {
/* 44 */     super.loadFields();
/* 45 */     this.registerUserConnectionOnJoin = getBoolean("register-userconnections-on-join", true);
/* 46 */     this.quickMoveActionFix = getBoolean("quick-move-action-fix", false);
/* 47 */     this.hitboxFix1_9 = getBoolean("change-1_9-hitbox", false);
/* 48 */     this.hitboxFix1_14 = getBoolean("change-1_14-hitbox", false);
/* 49 */     this.blockConnectionMethod = getString("blockconnection-method", "packet");
/* 50 */     this.armorToggleFix = getBoolean("armor-toggle-fix", true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void handleConfig(Map<String, Object> config) {}
/*    */ 
/*    */   
/*    */   public boolean shouldRegisterUserConnectionOnJoin() {
/* 59 */     return this.registerUserConnectionOnJoin;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean is1_12QuickMoveActionFix() {
/* 64 */     return this.quickMoveActionFix;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean is1_9HitboxFix() {
/* 69 */     return this.hitboxFix1_9;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean is1_14HitboxFix() {
/* 74 */     return this.hitboxFix1_14;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getBlockConnectionMethod() {
/* 79 */     return this.blockConnectionMethod;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isArmorToggleFix() {
/* 84 */     return this.armorToggleFix;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getUnsupportedOptions() {
/* 89 */     return UNSUPPORTED;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\platform\BukkitViaConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
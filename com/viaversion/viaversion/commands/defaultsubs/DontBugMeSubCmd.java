/*    */ package com.viaversion.viaversion.commands.defaultsubs;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*    */ import com.viaversion.viaversion.api.command.ViaSubCommand;
/*    */ import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
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
/*    */ public class DontBugMeSubCmd
/*    */   extends ViaSubCommand
/*    */ {
/*    */   public String name() {
/* 29 */     return "dontbugme";
/*    */   }
/*    */ 
/*    */   
/*    */   public String description() {
/* 34 */     return "Toggle checking for updates";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(ViaCommandSender sender, String[] args) {
/* 39 */     ConfigurationProvider provider = Via.getPlatform().getConfigurationProvider();
/* 40 */     boolean newValue = !Via.getConfig().isCheckForUpdates();
/*    */     
/* 42 */     Via.getConfig().setCheckForUpdates(newValue);
/* 43 */     provider.saveConfig();
/* 44 */     sendMessage(sender, "&6We will %snotify you about updates.", new Object[] { newValue ? "&a" : "&cnot " });
/*    */     
/* 46 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\commands\defaultsubs\DontBugMeSubCmd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
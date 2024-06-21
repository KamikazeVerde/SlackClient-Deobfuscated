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
/*    */ public class AutoTeamSubCmd
/*    */   extends ViaSubCommand
/*    */ {
/*    */   public String name() {
/* 28 */     return "autoteam";
/*    */   }
/*    */ 
/*    */   
/*    */   public String description() {
/* 33 */     return "Toggle automatically teaming to prevent colliding.";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(ViaCommandSender sender, String[] args) {
/* 38 */     ConfigurationProvider provider = Via.getPlatform().getConfigurationProvider();
/* 39 */     boolean newValue = !Via.getConfig().isAutoTeam();
/*    */     
/* 41 */     provider.set("auto-team", Boolean.valueOf(newValue));
/* 42 */     provider.saveConfig();
/* 43 */     sendMessage(sender, "&6We will %s", new Object[] { newValue ? "&aautomatically team players" : "&cno longer auto team players" });
/* 44 */     sendMessage(sender, "&6All players will need to re-login for the change to take place.", new Object[0]);
/*    */     
/* 46 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\commands\defaultsubs\AutoTeamSubCmd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
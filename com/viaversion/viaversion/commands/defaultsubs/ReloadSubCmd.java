/*    */ package com.viaversion.viaversion.commands.defaultsubs;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*    */ import com.viaversion.viaversion.api.command.ViaSubCommand;
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
/*    */ public class ReloadSubCmd
/*    */   extends ViaSubCommand
/*    */ {
/*    */   public String name() {
/* 27 */     return "reload";
/*    */   }
/*    */ 
/*    */   
/*    */   public String description() {
/* 32 */     return "Reload the config from the disk";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(ViaCommandSender sender, String[] args) {
/* 37 */     Via.getPlatform().getConfigurationProvider().reloadConfig();
/* 38 */     sendMessage(sender, "&6Configuration successfully reloaded! Some features may need a restart.", new Object[0]);
/* 39 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\commands\defaultsubs\ReloadSubCmd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
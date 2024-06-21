/*    */ package com.viaversion.viaversion.bungee.commands.subs;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*    */ import com.viaversion.viaversion.api.command.ViaSubCommand;
/*    */ import com.viaversion.viaversion.bungee.platform.BungeeViaConfig;
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
/*    */ public class ProbeSubCmd
/*    */   extends ViaSubCommand
/*    */ {
/*    */   public String name() {
/* 28 */     return "probe";
/*    */   }
/*    */ 
/*    */   
/*    */   public String description() {
/* 33 */     return "Forces ViaVersion to scan server protocol versions " + (
/* 34 */       (((BungeeViaConfig)Via.getConfig()).getBungeePingInterval() == -1) ? "" : "(Also happens at an interval)");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute(ViaCommandSender sender, String[] args) {
/* 40 */     Via.proxyPlatform().protocolDetectorService().probeAllServers();
/* 41 */     sendMessage(sender, "&6Started searching for protocol versions", new Object[0]);
/* 42 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\commands\subs\ProbeSubCmd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
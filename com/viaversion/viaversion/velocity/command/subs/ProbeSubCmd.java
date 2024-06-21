/*    */ package com.viaversion.viaversion.velocity.command.subs;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*    */ import com.viaversion.viaversion.api.command.ViaSubCommand;
/*    */ import com.viaversion.viaversion.velocity.platform.VelocityViaConfig;
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
/* 34 */       (((VelocityViaConfig)Via.getConfig()).getVelocityPingInterval() == -1) ? "" : "(Also happens at an interval)");
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


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\velocity\command\subs\ProbeSubCmd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
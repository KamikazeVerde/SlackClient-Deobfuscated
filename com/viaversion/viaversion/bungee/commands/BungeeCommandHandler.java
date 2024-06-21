/*    */ package com.viaversion.viaversion.bungee.commands;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.command.ViaSubCommand;
/*    */ import com.viaversion.viaversion.bungee.commands.subs.ProbeSubCmd;
/*    */ import com.viaversion.viaversion.commands.ViaCommandHandler;
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
/*    */ public class BungeeCommandHandler
/*    */   extends ViaCommandHandler
/*    */ {
/*    */   public BungeeCommandHandler() {
/*    */     try {
/* 27 */       registerSubCommand((ViaSubCommand)new ProbeSubCmd());
/* 28 */     } catch (Exception e) {
/* 29 */       Via.getPlatform().getLogger().severe("Failed to register Bungee subcommands");
/* 30 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\commands\BungeeCommandHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
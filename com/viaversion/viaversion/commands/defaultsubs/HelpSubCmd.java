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
/*    */ public class HelpSubCmd
/*    */   extends ViaSubCommand
/*    */ {
/*    */   public String name() {
/* 27 */     return "help";
/*    */   }
/*    */ 
/*    */   
/*    */   public String description() {
/* 32 */     return "You are looking at it right now!";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(ViaCommandSender sender, String[] args) {
/* 37 */     Via.getManager().getCommandHandler().showHelp(sender);
/* 38 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\commands\defaultsubs\HelpSubCmd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
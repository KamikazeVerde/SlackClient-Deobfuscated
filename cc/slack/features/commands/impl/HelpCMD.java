/*    */ package cc.slack.features.commands.impl;
/*    */ 
/*    */ import cc.slack.Slack;
/*    */ import cc.slack.features.commands.api.CMD;
/*    */ import cc.slack.features.commands.api.CMDInfo;
/*    */ import cc.slack.utils.other.PrintUtil;
/*    */ import net.minecraft.util.ChatFormatting;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CMDInfo(name = "Help", alias = "h", description = "Displays all of Slack's commands.")
/*    */ public class HelpCMD
/*    */   extends CMD
/*    */ {
/*    */   public void onCommand(String[] args, String command) {
/* 18 */     if (args.length > 0) {
/* 19 */       PrintUtil.message("Â§cInvalid use of arguments, expected none.");
/*    */       
/*    */       return;
/*    */     } 
/* 23 */     PrintUtil.message("Command list: ");
/* 24 */     Slack.getInstance().getCmdManager().getCommands().forEach(cmd -> PrintUtil.message(cmd.getName() + " - " + ChatFormatting.GRAY + cmd.getDescription()));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\commands\impl\HelpCMD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
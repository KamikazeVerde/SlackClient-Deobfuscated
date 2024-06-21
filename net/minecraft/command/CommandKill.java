/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandKill
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 16 */     return "kill";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 24 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 34 */     return "commands.kill.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 45 */     if (args.length == 0) {
/*    */       
/* 47 */       EntityPlayerMP entityPlayerMP = getCommandSenderAsPlayer(sender);
/* 48 */       entityPlayerMP.onKillCommand();
/* 49 */       notifyOperators(sender, this, "commands.kill.successful", new Object[] { entityPlayerMP.getDisplayName() });
/*    */     }
/*    */     else {
/*    */       
/* 53 */       Entity entity = func_175768_b(sender, args[0]);
/* 54 */       entity.onKillCommand();
/* 55 */       notifyOperators(sender, this, "commands.kill.successful", new Object[] { entity.getDisplayName() });
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 67 */     return (index == 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 72 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandKill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
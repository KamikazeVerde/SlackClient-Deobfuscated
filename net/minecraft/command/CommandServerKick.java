/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandServerKick
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 15 */     return "kick";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 23 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 33 */     return "commands.kick.usage";
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
/* 44 */     if (args.length > 0 && args[0].length() > 1) {
/*    */       
/* 46 */       EntityPlayerMP entityplayermp = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(args[0]);
/* 47 */       String s = "Kicked by an operator.";
/* 48 */       boolean flag = false;
/*    */       
/* 50 */       if (entityplayermp == null)
/*    */       {
/* 52 */         throw new PlayerNotFoundException();
/*    */       }
/*    */ 
/*    */       
/* 56 */       if (args.length >= 2) {
/*    */         
/* 58 */         s = getChatComponentFromNthArg(sender, args, 1).getUnformattedText();
/* 59 */         flag = true;
/*    */       } 
/*    */       
/* 62 */       entityplayermp.playerNetServerHandler.kickPlayerFromServer(s);
/*    */       
/* 64 */       if (flag)
/*    */       {
/* 66 */         notifyOperators(sender, this, "commands.kick.success.reason", new Object[] { entityplayermp.getCommandSenderName(), s });
/*    */       }
/*    */       else
/*    */       {
/* 70 */         notifyOperators(sender, this, "commands.kick.success", new Object[] { entityplayermp.getCommandSenderName() });
/*    */       }
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 76 */       throw new WrongUsageException("commands.kick.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 82 */     return (args.length >= 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandServerKick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
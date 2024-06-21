/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.regex.Matcher;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.SyntaxErrorException;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandPardonIp
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 20 */     return "pardon-ip";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 28 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canCommandSenderUseCommand(ICommandSender sender) {
/* 38 */     return (MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer() && super.canCommandSenderUseCommand(sender));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 48 */     return "commands.unbanip.usage";
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
/* 59 */     if (args.length == 1 && args[0].length() > 1) {
/*    */       
/* 61 */       Matcher matcher = CommandBanIp.field_147211_a.matcher(args[0]);
/*    */       
/* 63 */       if (matcher.matches())
/*    */       {
/* 65 */         MinecraftServer.getServer().getConfigurationManager().getBannedIPs().removeEntry(args[0]);
/* 66 */         notifyOperators(sender, (ICommand)this, "commands.unbanip.success", new Object[] { args[0] });
/*    */       }
/*    */       else
/*    */       {
/* 70 */         throw new SyntaxErrorException("commands.unbanip.invalid", new Object[0]);
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 75 */       throw new WrongUsageException("commands.unbanip.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 81 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys()) : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\server\CommandPardonIp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
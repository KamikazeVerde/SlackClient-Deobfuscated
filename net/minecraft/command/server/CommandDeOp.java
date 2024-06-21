/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandDeOp
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 19 */     return "deop";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 27 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 37 */     return "commands.deop.usage";
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
/* 48 */     if (args.length == 1 && args[0].length() > 0) {
/*    */       
/* 50 */       MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 51 */       GameProfile gameprofile = minecraftserver.getConfigurationManager().getOppedPlayers().getGameProfileFromName(args[0]);
/*    */       
/* 53 */       if (gameprofile == null)
/*    */       {
/* 55 */         throw new CommandException("commands.deop.failed", new Object[] { args[0] });
/*    */       }
/*    */ 
/*    */       
/* 59 */       minecraftserver.getConfigurationManager().removeOp(gameprofile);
/* 60 */       notifyOperators(sender, (ICommand)this, "commands.deop.success", new Object[] { args[0] });
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 65 */       throw new WrongUsageException("commands.deop.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 71 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getConfigurationManager().getOppedPlayerNames()) : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\server\CommandDeOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import com.google.common.collect.Lists;
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
/*    */ public class CommandOp
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 20 */     return "op";
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
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 38 */     return "commands.op.usage";
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
/* 49 */     if (args.length == 1 && args[0].length() > 0) {
/*    */       
/* 51 */       MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 52 */       GameProfile gameprofile = minecraftserver.getPlayerProfileCache().getGameProfileForUsername(args[0]);
/*    */       
/* 54 */       if (gameprofile == null)
/*    */       {
/* 56 */         throw new CommandException("commands.op.failed", new Object[] { args[0] });
/*    */       }
/*    */ 
/*    */       
/* 60 */       minecraftserver.getConfigurationManager().addOp(gameprofile);
/* 61 */       notifyOperators(sender, (ICommand)this, "commands.op.success", new Object[] { args[0] });
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 66 */       throw new WrongUsageException("commands.op.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 72 */     if (args.length == 1) {
/*    */       
/* 74 */       String s = args[args.length - 1];
/* 75 */       List<String> list = Lists.newArrayList();
/*    */       
/* 77 */       for (GameProfile gameprofile : MinecraftServer.getServer().getGameProfiles()) {
/*    */         
/* 79 */         if (!MinecraftServer.getServer().getConfigurationManager().canSendCommands(gameprofile) && doesStringStartWith(s, gameprofile.getName()))
/*    */         {
/* 81 */           list.add(gameprofile.getName());
/*    */         }
/*    */       } 
/*    */       
/* 85 */       return list;
/*    */     } 
/*    */ 
/*    */     
/* 89 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\server\CommandOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.server.management.UserListBansEntry;
/*    */ import net.minecraft.server.management.UserListEntry;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandBanPlayer
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 22 */     return "ban";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 30 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 40 */     return "commands.ban.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canCommandSenderUseCommand(ICommandSender sender) {
/* 50 */     return (MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer() && super.canCommandSenderUseCommand(sender));
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
/* 61 */     if (args.length >= 1 && args[0].length() > 0) {
/*    */       
/* 63 */       MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 64 */       GameProfile gameprofile = minecraftserver.getPlayerProfileCache().getGameProfileForUsername(args[0]);
/*    */       
/* 66 */       if (gameprofile == null)
/*    */       {
/* 68 */         throw new CommandException("commands.ban.failed", new Object[] { args[0] });
/*    */       }
/*    */ 
/*    */       
/* 72 */       String s = null;
/*    */       
/* 74 */       if (args.length >= 2)
/*    */       {
/* 76 */         s = getChatComponentFromNthArg(sender, args, 1).getUnformattedText();
/*    */       }
/*    */       
/* 79 */       UserListBansEntry userlistbansentry = new UserListBansEntry(gameprofile, null, sender.getCommandSenderName(), null, s);
/* 80 */       minecraftserver.getConfigurationManager().getBannedPlayers().addEntry((UserListEntry)userlistbansentry);
/* 81 */       EntityPlayerMP entityplayermp = minecraftserver.getConfigurationManager().getPlayerByUsername(args[0]);
/*    */       
/* 83 */       if (entityplayermp != null)
/*    */       {
/* 85 */         entityplayermp.playerNetServerHandler.kickPlayerFromServer("You are banned from this server.");
/*    */       }
/*    */       
/* 88 */       notifyOperators(sender, (ICommand)this, "commands.ban.success", new Object[] { args[0] });
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 93 */       throw new WrongUsageException("commands.ban.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 99 */     return (args.length >= 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\server\CommandBanPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
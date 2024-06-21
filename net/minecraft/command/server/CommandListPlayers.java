/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.CommandResultStats;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandListPlayers
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 18 */     return "list";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 26 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 36 */     return "commands.players.usage";
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
/* 47 */     int i = MinecraftServer.getServer().getCurrentPlayerCount();
/* 48 */     sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.players.list", new Object[] { Integer.valueOf(i), Integer.valueOf(MinecraftServer.getServer().getMaxPlayers()) }));
/* 49 */     sender.addChatMessage((IChatComponent)new ChatComponentText(MinecraftServer.getServer().getConfigurationManager().func_181058_b((args.length > 0 && "uuids".equalsIgnoreCase(args[0])))));
/* 50 */     sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, i);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\server\CommandListPlayers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.command;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.world.WorldSettings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandDefaultGameMode
/*    */   extends CommandGameMode
/*    */ {
/*    */   public String getCommandName() {
/* 15 */     return "defaultgamemode";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 25 */     return "commands.defaultgamemode.usage";
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
/* 36 */     if (args.length <= 0)
/*    */     {
/* 38 */       throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 42 */     WorldSettings.GameType worldsettings$gametype = getGameModeFromCommand(sender, args[0]);
/* 43 */     setGameType(worldsettings$gametype);
/* 44 */     notifyOperators(sender, this, "commands.defaultgamemode.success", new Object[] { new ChatComponentTranslation("gameMode." + worldsettings$gametype.getName(), new Object[0]) });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setGameType(WorldSettings.GameType p_71541_1_) {
/* 50 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 51 */     minecraftserver.setGameType(p_71541_1_);
/*    */     
/* 53 */     if (minecraftserver.getForceGamemode())
/*    */     {
/* 55 */       for (EntityPlayerMP entityplayermp : MinecraftServer.getServer().getConfigurationManager().func_181057_v()) {
/*    */         
/* 57 */         entityplayermp.setGameType(p_71541_1_);
/* 58 */         entityplayermp.fallDistance = 0.0F;
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\CommandDefaultGameMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
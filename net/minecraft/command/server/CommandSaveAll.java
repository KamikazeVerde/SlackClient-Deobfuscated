/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.world.MinecraftException;
/*    */ import net.minecraft.world.WorldServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandSaveAll
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 19 */     return "save-all";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 29 */     return "commands.save.usage";
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
/* 40 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 41 */     sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.save.start", new Object[0]));
/*    */     
/* 43 */     if (minecraftserver.getConfigurationManager() != null)
/*    */     {
/* 45 */       minecraftserver.getConfigurationManager().saveAllPlayerData();
/*    */     }
/*    */ 
/*    */     
/*    */     try {
/* 50 */       for (int i = 0; i < minecraftserver.worldServers.length; i++) {
/*    */         
/* 52 */         if (minecraftserver.worldServers[i] != null) {
/*    */           
/* 54 */           WorldServer worldserver = minecraftserver.worldServers[i];
/* 55 */           boolean flag = worldserver.disableLevelSaving;
/* 56 */           worldserver.disableLevelSaving = false;
/* 57 */           worldserver.saveAllChunks(true, null);
/* 58 */           worldserver.disableLevelSaving = flag;
/*    */         } 
/*    */       } 
/*    */       
/* 62 */       if (args.length > 0 && "flush".equals(args[0]))
/*    */       {
/* 64 */         sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.save.flushStart", new Object[0]));
/*    */         
/* 66 */         for (int j = 0; j < minecraftserver.worldServers.length; j++) {
/*    */           
/* 68 */           if (minecraftserver.worldServers[j] != null) {
/*    */             
/* 70 */             WorldServer worldserver1 = minecraftserver.worldServers[j];
/* 71 */             boolean flag1 = worldserver1.disableLevelSaving;
/* 72 */             worldserver1.disableLevelSaving = false;
/* 73 */             worldserver1.saveChunkData();
/* 74 */             worldserver1.disableLevelSaving = flag1;
/*    */           } 
/*    */         } 
/*    */         
/* 78 */         sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.save.flushEnd", new Object[0]));
/*    */       }
/*    */     
/* 81 */     } catch (MinecraftException minecraftexception) {
/*    */       
/* 83 */       notifyOperators(sender, (ICommand)this, "commands.save.failed", new Object[] { minecraftexception.getMessage() });
/*    */       
/*    */       return;
/*    */     } 
/* 87 */     notifyOperators(sender, (ICommand)this, "commands.save.success", new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\server\CommandSaveAll.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
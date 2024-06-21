/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.world.WorldServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandSaveOn
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 16 */     return "save-on";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 26 */     return "commands.save-on.usage";
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
/* 37 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 38 */     boolean flag = false;
/*    */     
/* 40 */     for (int i = 0; i < minecraftserver.worldServers.length; i++) {
/*    */       
/* 42 */       if (minecraftserver.worldServers[i] != null) {
/*    */         
/* 44 */         WorldServer worldserver = minecraftserver.worldServers[i];
/*    */         
/* 46 */         if (worldserver.disableLevelSaving) {
/*    */           
/* 48 */           worldserver.disableLevelSaving = false;
/* 49 */           flag = true;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 54 */     if (flag) {
/*    */       
/* 56 */       notifyOperators(sender, (ICommand)this, "commands.save.enabled", new Object[0]);
/*    */     }
/*    */     else {
/*    */       
/* 60 */       throw new CommandException("commands.save-on.alreadyOn", new Object[0]);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\command\server\CommandSaveOn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
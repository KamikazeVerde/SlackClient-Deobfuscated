/*    */ package com.viaversion.viaversion.bukkit.commands;
/*    */ 
/*    */ import com.viaversion.viaversion.commands.ViaCommandHandler;
/*    */ import java.util.List;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.command.TabExecutor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BukkitCommandHandler
/*    */   extends ViaCommandHandler
/*    */   implements CommandExecutor, TabExecutor
/*    */ {
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 30 */     return onCommand(new BukkitCommandSender(sender), args);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
/* 35 */     return onTabComplete(new BukkitCommandSender(sender), args);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\commands\BukkitCommandHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
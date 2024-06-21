/*    */ package com.viaversion.viaversion.bungee.commands;
/*    */ 
/*    */ import net.md_5.bungee.api.CommandSender;
/*    */ import net.md_5.bungee.api.plugin.Command;
/*    */ import net.md_5.bungee.api.plugin.TabExecutor;
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
/*    */ public class BungeeCommand
/*    */   extends Command
/*    */   implements TabExecutor
/*    */ {
/*    */   private final BungeeCommandHandler handler;
/*    */   
/*    */   public BungeeCommand(BungeeCommandHandler handler) {
/* 28 */     super("viaversion", "viaversion.admin", new String[] { "viaver", "vvbungee" });
/* 29 */     this.handler = handler;
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(CommandSender commandSender, String[] strings) {
/* 34 */     this.handler.onCommand(new BungeeCommandSender(commandSender), strings);
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterable<String> onTabComplete(CommandSender commandSender, String[] strings) {
/* 39 */     return this.handler.onTabComplete(new BungeeCommandSender(commandSender), strings);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\commands\BungeeCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
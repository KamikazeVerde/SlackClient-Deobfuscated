/*    */ package com.viaversion.viaversion.velocity.command;
/*    */ 
/*    */ import com.velocitypowered.api.command.CommandInvocation;
/*    */ import com.velocitypowered.api.command.SimpleCommand;
/*    */ import com.viaversion.viaversion.api.command.ViaSubCommand;
/*    */ import com.viaversion.viaversion.commands.ViaCommandHandler;
/*    */ import com.viaversion.viaversion.velocity.command.subs.ProbeSubCmd;
/*    */ import java.util.List;
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
/*    */ public class VelocityCommandHandler
/*    */   extends ViaCommandHandler
/*    */   implements SimpleCommand
/*    */ {
/*    */   public VelocityCommandHandler() {
/*    */     try {
/* 28 */       registerSubCommand((ViaSubCommand)new ProbeSubCmd());
/* 29 */     } catch (Exception e) {
/* 30 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(SimpleCommand.Invocation invocation) {
/* 36 */     onCommand(new VelocityCommandSender(invocation.source()), (String[])invocation.arguments());
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> suggest(SimpleCommand.Invocation invocation) {
/* 41 */     return onTabComplete(new VelocityCommandSender(invocation.source()), (String[])invocation.arguments());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasPermission(SimpleCommand.Invocation invocation) {
/* 46 */     return invocation.source().hasPermission("viaversion.admin");
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\velocity\command\VelocityCommandHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
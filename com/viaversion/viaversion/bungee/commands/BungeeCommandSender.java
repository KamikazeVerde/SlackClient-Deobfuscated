/*    */ package com.viaversion.viaversion.bungee.commands;
/*    */ 
/*    */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*    */ import java.util.UUID;
/*    */ import net.md_5.bungee.api.CommandSender;
/*    */ import net.md_5.bungee.api.connection.ProxiedPlayer;
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
/*    */ public class BungeeCommandSender
/*    */   implements ViaCommandSender
/*    */ {
/*    */   private final CommandSender sender;
/*    */   
/*    */   public BungeeCommandSender(CommandSender sender) {
/* 29 */     this.sender = sender;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasPermission(String permission) {
/* 34 */     return this.sender.hasPermission(permission);
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendMessage(String msg) {
/* 39 */     this.sender.sendMessage(msg);
/*    */   }
/*    */ 
/*    */   
/*    */   public UUID getUUID() {
/* 44 */     if (this.sender instanceof ProxiedPlayer) {
/* 45 */       return ((ProxiedPlayer)this.sender).getUniqueId();
/*    */     }
/* 47 */     return new UUID(0L, 0L);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 53 */     return this.sender.getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\commands\BungeeCommandSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.viaversion.viaversion.bukkit.commands;
/*    */ 
/*    */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Entity;
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
/*    */ public class BukkitCommandSender
/*    */   implements ViaCommandSender
/*    */ {
/*    */   private final CommandSender sender;
/*    */   
/*    */   public BukkitCommandSender(CommandSender sender) {
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
/* 44 */     if (this.sender instanceof Entity) {
/* 45 */       return ((Entity)this.sender).getUniqueId();
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


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\commands\BukkitCommandSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
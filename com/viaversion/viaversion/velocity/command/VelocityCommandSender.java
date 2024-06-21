/*    */ package com.viaversion.viaversion.velocity.command;
/*    */ 
/*    */ import com.velocitypowered.api.command.CommandSource;
/*    */ import com.velocitypowered.api.proxy.Player;
/*    */ import com.viaversion.viaversion.VelocityPlugin;
/*    */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*    */ import java.util.UUID;
/*    */ import net.kyori.adventure.text.Component;
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
/*    */ public class VelocityCommandSender
/*    */   implements ViaCommandSender
/*    */ {
/*    */   private final CommandSource source;
/*    */   
/*    */   public VelocityCommandSender(CommandSource source) {
/* 30 */     this.source = source;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasPermission(String permission) {
/* 35 */     return this.source.hasPermission(permission);
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendMessage(String msg) {
/* 40 */     this.source.sendMessage((Component)VelocityPlugin.COMPONENT_SERIALIZER.deserialize(msg));
/*    */   }
/*    */ 
/*    */   
/*    */   public UUID getUUID() {
/* 45 */     if (this.source instanceof Player) {
/* 46 */       return ((Player)this.source).getUniqueId();
/*    */     }
/* 48 */     return new UUID(0L, 0L);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 53 */     if (this.source instanceof Player) {
/* 54 */       return ((Player)this.source).getUsername();
/*    */     }
/* 56 */     return "?";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\velocity\command\VelocityCommandSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
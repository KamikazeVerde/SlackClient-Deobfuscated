/*    */ package de.florianmichael.vialoadingbase.command;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import java.util.UUID;
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
/*    */ 
/*    */ public class UserCommandSender
/*    */   implements ViaCommandSender
/*    */ {
/*    */   private final UserConnection user;
/*    */   
/*    */   public UserCommandSender(UserConnection user) {
/* 30 */     this.user = user;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasPermission(String s) {
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendMessage(String s) {
/* 40 */     Via.getPlatform().sendMessage(getUUID(), s);
/*    */   }
/*    */ 
/*    */   
/*    */   public UUID getUUID() {
/* 45 */     return this.user.getProtocolInfo().getUuid();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 50 */     return this.user.getProtocolInfo().getUsername();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\vialoadingbase\command\UserCommandSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
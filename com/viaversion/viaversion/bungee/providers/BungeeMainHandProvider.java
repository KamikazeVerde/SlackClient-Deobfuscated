/*    */ package com.viaversion.viaversion.bungee.providers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.ProtocolInfo;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MainHandProvider;
/*    */ import java.lang.reflect.Method;
/*    */ import net.md_5.bungee.api.ProxyServer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BungeeMainHandProvider
/*    */   extends MainHandProvider
/*    */ {
/* 32 */   private static Method getSettings = null;
/* 33 */   private static Method setMainHand = null;
/*    */   
/*    */   static {
/*    */     try {
/* 37 */       getSettings = Class.forName("net.md_5.bungee.UserConnection").getDeclaredMethod("getSettings", new Class[0]);
/* 38 */       setMainHand = Class.forName("net.md_5.bungee.protocol.packet.ClientSettings").getDeclaredMethod("setMainHand", new Class[] { int.class });
/* 39 */     } catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMainHand(UserConnection user, int hand) {
/* 45 */     ProtocolInfo info = user.getProtocolInfo();
/* 46 */     if (info == null || info.getUuid() == null)
/* 47 */       return;  ProxiedPlayer player = ProxyServer.getInstance().getPlayer(info.getUuid());
/* 48 */     if (player == null)
/*    */       return;  try {
/* 50 */       Object settings = getSettings.invoke(player, new Object[0]);
/* 51 */       if (settings != null) {
/* 52 */         setMainHand.invoke(settings, new Object[] { Integer.valueOf(hand) });
/*    */       }
/* 54 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 55 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\providers\BungeeMainHandProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
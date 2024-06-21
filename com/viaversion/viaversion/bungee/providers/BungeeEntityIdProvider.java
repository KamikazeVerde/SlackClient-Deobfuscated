/*    */ package com.viaversion.viaversion.bungee.providers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.bungee.storage.BungeeStorage;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.EntityIdProvider;
/*    */ import java.lang.reflect.Method;
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
/*    */ public class BungeeEntityIdProvider
/*    */   extends EntityIdProvider
/*    */ {
/*    */   private static Method getClientEntityId;
/*    */   
/*    */   static {
/*    */     try {
/* 31 */       getClientEntityId = Class.forName("net.md_5.bungee.UserConnection").getDeclaredMethod("getClientEntityId", new Class[0]);
/* 32 */     } catch (NoSuchMethodException|ClassNotFoundException e) {
/* 33 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEntityId(UserConnection user) throws Exception {
/* 39 */     BungeeStorage storage = (BungeeStorage)user.get(BungeeStorage.class);
/* 40 */     ProxiedPlayer player = storage.getPlayer();
/*    */     
/* 42 */     return ((Integer)getClientEntityId.invoke(player, new Object[0])).intValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\providers\BungeeEntityIdProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
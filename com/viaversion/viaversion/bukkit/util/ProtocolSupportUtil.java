/*    */ package com.viaversion.viaversion.bukkit.util;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import org.bukkit.entity.Player;
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
/*    */ public final class ProtocolSupportUtil
/*    */ {
/*    */   private static final Method PROTOCOL_VERSION_METHOD;
/*    */   private static final Method GET_ID_METHOD;
/*    */   
/*    */   static {
/* 29 */     Method protocolVersionMethod = null;
/* 30 */     Method getIdMethod = null;
/*    */     try {
/* 32 */       protocolVersionMethod = Class.forName("protocolsupport.api.ProtocolSupportAPI").getMethod("getProtocolVersion", new Class[] { Player.class });
/* 33 */       getIdMethod = Class.forName("protocolsupport.api.ProtocolVersion").getMethod("getId", new Class[0]);
/* 34 */     } catch (ReflectiveOperationException reflectiveOperationException) {}
/*    */ 
/*    */     
/* 37 */     PROTOCOL_VERSION_METHOD = protocolVersionMethod;
/* 38 */     GET_ID_METHOD = getIdMethod;
/*    */   }
/*    */   
/*    */   public static int getProtocolVersion(Player player) {
/* 42 */     if (PROTOCOL_VERSION_METHOD == null) {
/* 43 */       return -1;
/*    */     }
/*    */     try {
/* 46 */       Object version = PROTOCOL_VERSION_METHOD.invoke(null, new Object[] { player });
/* 47 */       return ((Integer)GET_ID_METHOD.invoke(version, new Object[0])).intValue();
/* 48 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 49 */       e.printStackTrace();
/*    */       
/* 51 */       return -1;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukki\\util\ProtocolSupportUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
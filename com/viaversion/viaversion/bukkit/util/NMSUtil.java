/*    */ package com.viaversion.viaversion.bukkit.util;
/*    */ 
/*    */ import org.bukkit.Bukkit;
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
/*    */ public final class NMSUtil
/*    */ {
/* 23 */   private static final String BASE = Bukkit.getServer().getClass().getPackage().getName();
/* 24 */   private static final String NMS = BASE.replace("org.bukkit.craftbukkit", "net.minecraft.server");
/* 25 */   private static final boolean DEBUG_PROPERTY = loadDebugProperty();
/*    */   
/*    */   private static boolean loadDebugProperty() {
/*    */     try {
/* 29 */       Class<?> serverClass = nms("MinecraftServer", "net.minecraft.server.MinecraftServer");
/*    */ 
/*    */ 
/*    */       
/* 33 */       Object server = serverClass.getDeclaredMethod("getServer", new Class[0]).invoke(null, new Object[0]);
/* 34 */       return ((Boolean)serverClass.getMethod("isDebugging", new Class[0]).invoke(server, new Object[0])).booleanValue();
/* 35 */     } catch (ReflectiveOperationException e) {
/* 36 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static Class<?> nms(String className) throws ClassNotFoundException {
/* 41 */     return Class.forName(NMS + "." + className);
/*    */   }
/*    */   
/*    */   public static Class<?> nms(String className, String fallbackFullClassName) throws ClassNotFoundException {
/*    */     try {
/* 46 */       return Class.forName(NMS + "." + className);
/* 47 */     } catch (ClassNotFoundException ignored) {
/* 48 */       return Class.forName(fallbackFullClassName);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static Class<?> obc(String className) throws ClassNotFoundException {
/* 53 */     return Class.forName(BASE + "." + className);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isDebugPropertySet() {
/* 60 */     return DEBUG_PROPERTY;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukki\\util\NMSUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
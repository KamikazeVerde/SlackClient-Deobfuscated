/*    */ package com.viaversion.viaversion.util;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Key
/*    */ {
/*    */   public static String stripNamespace(String identifier) {
/* 28 */     int index = identifier.indexOf(':');
/* 29 */     if (index == -1) {
/* 30 */       return identifier;
/*    */     }
/* 32 */     return identifier.substring(index + 1);
/*    */   }
/*    */   
/*    */   public static String stripMinecraftNamespace(String identifier) {
/* 36 */     if (identifier.startsWith("minecraft:")) {
/* 37 */       return identifier.substring(10);
/*    */     }
/* 39 */     return identifier;
/*    */   }
/*    */   
/*    */   public static String namespaced(String identifier) {
/* 43 */     if (identifier.indexOf(':') == -1) {
/* 44 */       return "minecraft:" + identifier;
/*    */     }
/* 46 */     return identifier;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\util\Key.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
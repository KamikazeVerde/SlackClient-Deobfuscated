/*    */ package com.viaversion.viaversion.bungee.providers;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.connection.ProtocolInfo;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*    */ import com.viaversion.viaversion.protocols.base.BaseVersionProvider;
/*    */ import com.viaversion.viaversion.util.ReflectionUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.md_5.bungee.api.ProxyServer;
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
/*    */ public class BungeeVersionProvider
/*    */   extends BaseVersionProvider
/*    */ {
/*    */   private static Class<?> ref;
/*    */   
/*    */   static {
/*    */     try {
/* 37 */       ref = Class.forName("net.md_5.bungee.protocol.ProtocolConstants");
/* 38 */     } catch (Exception e) {
/* 39 */       Via.getPlatform().getLogger().severe("Could not detect the ProtocolConstants class");
/* 40 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getClosestServerProtocol(UserConnection user) throws Exception {
/* 46 */     if (ref == null) {
/* 47 */       return super.getClosestServerProtocol(user);
/*    */     }
/* 49 */     List<Integer> list = (List<Integer>)ReflectionUtil.getStatic(ref, "SUPPORTED_VERSION_IDS", List.class);
/* 50 */     List<Integer> sorted = new ArrayList<>(list);
/* 51 */     Collections.sort(sorted);
/*    */     
/* 53 */     ProtocolInfo info = user.getProtocolInfo();
/*    */ 
/*    */     
/* 56 */     if (sorted.contains(Integer.valueOf(info.getProtocolVersion()))) {
/* 57 */       return info.getProtocolVersion();
/*    */     }
/*    */     
/* 60 */     if (info.getProtocolVersion() < ((Integer)sorted.get(0)).intValue()) {
/* 61 */       return getLowestSupportedVersion();
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 68 */     for (Integer protocol : Lists.reverse(sorted)) {
/* 69 */       if (info.getProtocolVersion() > protocol.intValue() && ProtocolVersion.isRegistered(protocol.intValue())) {
/* 70 */         return protocol.intValue();
/*    */       }
/*    */     } 
/* 73 */     Via.getPlatform().getLogger().severe("Panic, no protocol id found for " + info.getProtocolVersion());
/* 74 */     return info.getProtocolVersion();
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getLowestSupportedVersion() {
/*    */     try {
/* 80 */       List<Integer> list = (List<Integer>)ReflectionUtil.getStatic(ref, "SUPPORTED_VERSION_IDS", List.class);
/* 81 */       return ((Integer)list.get(0)).intValue();
/* 82 */     } catch (NoSuchFieldException|IllegalAccessException e) {
/* 83 */       e.printStackTrace();
/*    */ 
/*    */       
/* 86 */       return ProxyServer.getInstance().getProtocolVersion();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\providers\BungeeVersionProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
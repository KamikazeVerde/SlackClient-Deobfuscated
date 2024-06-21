/*    */ package com.viaversion.viaversion.api;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
/*    */ import com.viaversion.viaversion.api.platform.ViaPlatform;
/*    */ import com.viaversion.viaversion.api.platform.ViaServerProxyPlatform;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Via
/*    */ {
/*    */   private static ViaManager manager;
/*    */   
/*    */   public static ViaAPI getAPI() {
/* 40 */     return manager().getPlatform().getApi();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ViaManager getManager() {
/* 50 */     return manager();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ViaVersionConfig getConfig() {
/* 60 */     return manager().getPlatform().getConf();
/*    */   }
/*    */   
/*    */   public static ViaPlatform getPlatform() {
/* 64 */     return manager().getPlatform();
/*    */   }
/*    */   
/*    */   public static ViaServerProxyPlatform<?> proxyPlatform() {
/* 68 */     Preconditions.checkArgument(manager().getPlatform() instanceof ViaServerProxyPlatform, "Platform is not proxying Minecraft servers!");
/* 69 */     return (ViaServerProxyPlatform)manager().getPlatform();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void init(ViaManager viaManager) {
/* 79 */     Preconditions.checkArgument((manager == null), "ViaManager is already set");
/* 80 */     manager = viaManager;
/*    */   }
/*    */   
/*    */   private static ViaManager manager() {
/* 84 */     Preconditions.checkArgument((manager != null), "ViaVersion has not loaded the platform yet");
/* 85 */     return manager;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\Via.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
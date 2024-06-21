/*    */ package com.viaversion.viabackwards;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.viaversion.viabackwards.api.ViaBackwardsConfig;
/*    */ import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
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
/*    */ public final class ViaBackwards
/*    */ {
/*    */   private static ViaBackwardsPlatform platform;
/*    */   private static ViaBackwardsConfig config;
/*    */   
/*    */   public static void init(ViaBackwardsPlatform platform, ViaBackwardsConfig config) {
/* 31 */     Preconditions.checkArgument((platform != null), "ViaBackwards is already initialized");
/*    */     
/* 33 */     ViaBackwards.platform = platform;
/* 34 */     ViaBackwards.config = config;
/*    */   }
/*    */   
/*    */   public static ViaBackwardsPlatform getPlatform() {
/* 38 */     return platform;
/*    */   }
/*    */   
/*    */   public static ViaBackwardsConfig getConfig() {
/* 42 */     return config;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\ViaBackwards.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
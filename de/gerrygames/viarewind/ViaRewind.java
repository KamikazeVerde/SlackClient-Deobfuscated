/*    */ package de.gerrygames.viarewind;
/*    */ 
/*    */ import de.gerrygames.viarewind.api.ViaRewindConfig;
/*    */ import de.gerrygames.viarewind.api.ViaRewindPlatform;
/*    */ 
/*    */ public class ViaRewind {
/*    */   private static ViaRewindPlatform platform;
/*    */   private static ViaRewindConfig config;
/*    */   
/*    */   public static void init(ViaRewindPlatform platform, ViaRewindConfig config) {
/* 11 */     ViaRewind.platform = platform;
/* 12 */     ViaRewind.config = config;
/*    */   }
/*    */   
/*    */   public static ViaRewindPlatform getPlatform() {
/* 16 */     return platform;
/*    */   }
/*    */   
/*    */   public static ViaRewindConfig getConfig() {
/* 20 */     return config;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\ViaRewind.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
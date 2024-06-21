/*    */ package com.viaversion.viaversion.api.legacy;
/*    */ 
/*    */ import com.viaversion.viaversion.api.legacy.bossbar.BossBar;
/*    */ import com.viaversion.viaversion.api.legacy.bossbar.BossColor;
/*    */ import com.viaversion.viaversion.api.legacy.bossbar.BossStyle;
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
/*    */ public interface LegacyViaAPI<T>
/*    */ {
/*    */   BossBar createLegacyBossBar(String paramString, float paramFloat, BossColor paramBossColor, BossStyle paramBossStyle);
/*    */   
/*    */   default BossBar createLegacyBossBar(String title, BossColor color, BossStyle style) {
/* 56 */     return createLegacyBossBar(title, 1.0F, color, style);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\legacy\LegacyViaAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
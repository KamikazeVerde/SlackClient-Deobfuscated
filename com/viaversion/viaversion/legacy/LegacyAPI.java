/*    */ package com.viaversion.viaversion.legacy;
/*    */ 
/*    */ import com.viaversion.viaversion.api.legacy.LegacyViaAPI;
/*    */ import com.viaversion.viaversion.api.legacy.bossbar.BossBar;
/*    */ import com.viaversion.viaversion.api.legacy.bossbar.BossColor;
/*    */ import com.viaversion.viaversion.api.legacy.bossbar.BossStyle;
/*    */ import com.viaversion.viaversion.legacy.bossbar.CommonBoss;
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
/*    */ public final class LegacyAPI<T>
/*    */   implements LegacyViaAPI<T>
/*    */ {
/*    */   public BossBar createLegacyBossBar(String title, float health, BossColor color, BossStyle style) {
/* 30 */     return (BossBar)new CommonBoss(title, health, color, style);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\legacy\LegacyAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
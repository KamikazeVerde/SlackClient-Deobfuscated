/*    */ package com.viaversion.viabackwards;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import net.md_5.bungee.api.plugin.Plugin;
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
/*    */ public class BungeePlugin
/*    */   extends Plugin
/*    */   implements ViaBackwardsPlatform
/*    */ {
/*    */   public void onLoad() {
/* 30 */     Via.getManager().addEnableListener(() -> init(getDataFolder()));
/*    */   }
/*    */   
/*    */   public void disable() {}
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\BungeePlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
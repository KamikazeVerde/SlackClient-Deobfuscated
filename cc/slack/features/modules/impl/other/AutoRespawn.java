/*    */ package cc.slack.features.modules.impl.other;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.utils.client.mc;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "AutoRespawn", category = Category.OTHER)
/*    */ public class AutoRespawn
/*    */   extends Module
/*    */ {
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 19 */     if (!mc.getPlayer().isEntityAlive())
/* 20 */       mc.getPlayer().respawnPlayer(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\other\AutoRespawn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package cc.slack.features.modules.impl.player.nofalls.basics;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.impl.player.nofalls.INoFall;
/*    */ import cc.slack.utils.client.mc;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VanillaNofall
/*    */   implements INoFall
/*    */ {
/*    */   public void onUpdate(UpdateEvent event) {
/* 14 */     (mc.getPlayer()).fallDistance = 0.0F;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 18 */     return "Vanilla";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\player\nofalls\basics\VanillaNofall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
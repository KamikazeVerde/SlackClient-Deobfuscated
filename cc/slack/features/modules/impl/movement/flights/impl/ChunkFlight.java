/*    */ package cc.slack.features.modules.impl.movement.flights.impl;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.impl.movement.flights.IFlight;
/*    */ import cc.slack.utils.client.mc;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkFlight
/*    */   implements IFlight
/*    */ {
/*    */   public void onUpdate(UpdateEvent event) {
/* 13 */     if ((mc.getPlayer()).motionY < 0.0D) {
/* 14 */       (mc.getPlayer()).motionY = -0.09800000190735147D;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 20 */     return "Chunk";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\flights\impl\ChunkFlight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
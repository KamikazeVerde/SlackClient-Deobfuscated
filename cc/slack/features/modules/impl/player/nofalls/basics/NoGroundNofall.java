/*    */ package cc.slack.features.modules.impl.player.nofalls.basics;
/*    */ 
/*    */ import cc.slack.events.impl.player.MotionEvent;
/*    */ import cc.slack.features.modules.impl.player.nofalls.INoFall;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoGroundNofall
/*    */   implements INoFall
/*    */ {
/*    */   public void onMotion(MotionEvent event) {
/* 13 */     event.setGround(false);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 17 */     return "No Ground";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\player\nofalls\basics\NoGroundNofall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
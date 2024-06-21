/*    */ package cc.slack.features.modules.impl.player.nofalls.basics;
/*    */ 
/*    */ import cc.slack.events.impl.player.MotionEvent;
/*    */ import cc.slack.features.modules.impl.player.nofalls.INoFall;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpoofGroundNofall
/*    */   implements INoFall
/*    */ {
/*    */   public void onMotion(MotionEvent event) {
/* 13 */     event.setGround(true);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 17 */     return "Spoof Ground";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\player\nofalls\basics\SpoofGroundNofall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
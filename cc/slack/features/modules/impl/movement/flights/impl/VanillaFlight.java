/*    */ package cc.slack.features.modules.impl.movement.flights.impl;
/*    */ 
/*    */ import cc.slack.events.impl.player.MoveEvent;
/*    */ import cc.slack.features.modules.impl.movement.flights.IFlight;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.player.MovementUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VanillaFlight
/*    */   implements IFlight
/*    */ {
/*    */   public void onDisable() {
/* 14 */     MovementUtil.resetMotion(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onMove(MoveEvent event) {
/* 19 */     event.setY((mc.getGameSettings()).keyBindJump.isKeyDown() ? 3.32D : (
/* 20 */         (mc.getGameSettings()).keyBindSneak.isKeyDown() ? -3.32D : 0.0D));
/* 21 */     MovementUtil.setSpeed(event, 3.5D);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 26 */     return "Vanilla";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\flights\impl\VanillaFlight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
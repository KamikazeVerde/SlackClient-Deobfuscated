/*    */ package cc.slack.features.modules.impl.movement.speeds.hop;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.impl.movement.speeds.ISpeed;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.player.MovementUtil;
/*    */ import cc.slack.utils.player.PlayerUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HypixelHopSpeed
/*    */   implements ISpeed
/*    */ {
/*    */   public void onUpdate(UpdateEvent event) {
/* 17 */     if ((mc.getPlayer()).onGround) {
/* 18 */       if (MovementUtil.isMoving()) {
/* 19 */         mc.getPlayer().jump();
/* 20 */         MovementUtil.strafe(0.46F);
/* 21 */         (mc.getPlayer()).motionY = PlayerUtil.getJumpHeight();
/*    */       } 
/*    */     } else {
/* 24 */       (mc.getPlayer()).motionX *= 1.001D;
/* 25 */       (mc.getPlayer()).motionZ *= 1.001D;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 32 */     return "Hypixel Hop";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\speeds\hop\HypixelHopSpeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
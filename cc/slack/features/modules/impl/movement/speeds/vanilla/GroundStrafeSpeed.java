/*    */ package cc.slack.features.modules.impl.movement.speeds.vanilla;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.impl.movement.speeds.ISpeed;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.player.MovementUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GroundStrafeSpeed
/*    */   implements ISpeed
/*    */ {
/*    */   public void onUpdate(UpdateEvent event) {
/* 14 */     if ((mc.getPlayer()).onGround && MovementUtil.isMoving()) {
/* 15 */       mc.getPlayer().jump();
/* 16 */       MovementUtil.strafe();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 22 */     return "GroundStrafe";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\speeds\vanilla\GroundStrafeSpeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package cc.slack.features.modules.impl.movement.speeds.hop;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.impl.movement.speeds.ISpeed;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.player.MovementUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VulcanHopSpeed
/*    */   implements ISpeed
/*    */ {
/*    */   boolean modifiedTimer;
/*    */   
/*    */   public void onUpdate(UpdateEvent event) {
/* 19 */     if (this.modifiedTimer) {
/* 20 */       (mc.getTimer()).timerSpeed = 1.0F;
/* 21 */       this.modifiedTimer = false;
/*    */     } 
/*    */     
/* 24 */     if (MovementUtil.getSpeed() < 0.2150000035762787D && !(mc.getPlayer()).onGround) {
/* 25 */       MovementUtil.strafe(0.215F);
/*    */     }
/*    */     
/* 28 */     if ((mc.getPlayer()).onGround && MovementUtil.isMoving()) {
/* 29 */       mc.getPlayer().jump();
/*    */       
/* 31 */       (mc.getTimer()).timerSpeed = 1.25F;
/* 32 */       this.modifiedTimer = true;
/*    */       
/* 34 */       MovementUtil.minLimitStrafe(0.4849F);
/*    */     }
/* 36 */     else if (!MovementUtil.isMoving()) {
/* 37 */       (mc.getTimer()).timerSpeed = 1.0F;
/* 38 */       MovementUtil.resetMotion();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 44 */     return "Vulcan Hop";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\speeds\hop\VulcanHopSpeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
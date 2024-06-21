/*    */ package cc.slack.features.modules.impl.movement.speeds.lowhops;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.impl.movement.speeds.ISpeed;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.player.MovementUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VulcanLowSpeed
/*    */   implements ISpeed
/*    */ {
/* 13 */   double launchY = 0.0D;
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 17 */     this.launchY = (mc.getPlayer()).motionY;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate(UpdateEvent event) {
/* 22 */     (mc.getPlayer()).jumpMovementFactor = 0.0245F;
/* 23 */     if ((mc.getPlayer()).onGround && MovementUtil.isMoving()) {
/* 24 */       mc.getPlayer().jump();
/* 25 */       MovementUtil.strafe();
/* 26 */       if (MovementUtil.getSpeed() < 0.5D) {
/* 27 */         MovementUtil.strafe(0.484F);
/*    */       }
/* 29 */       this.launchY = (mc.getPlayer()).posY;
/* 30 */     } else if ((mc.getPlayer()).offGroundTicks == 4) {
/* 31 */       (mc.getPlayer()).motionY = -0.27D;
/*    */     } 
/* 33 */     if (MovementUtil.getSpeed() < 0.2150000035762787D && !(mc.getPlayer()).onGround) {
/* 34 */       MovementUtil.strafe(0.215F);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 40 */     return "Vulcan Low";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\speeds\lowhops\VulcanLowSpeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
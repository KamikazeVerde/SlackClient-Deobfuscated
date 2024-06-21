/*    */ package cc.slack.features.modules.impl.movement.speeds.hop;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.impl.movement.speeds.ISpeed;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.player.MovementUtil;
/*    */ import net.minecraft.potion.Potion;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NCPHopSpeed
/*    */   implements ISpeed
/*    */ {
/*    */   public void onUpdate(UpdateEvent event) {
/* 19 */     if ((mc.getPlayer()).onGround) {
/* 20 */       if (MovementUtil.isMoving()) {
/* 21 */         mc.getPlayer().jump();
/* 22 */         double baseSpeed = 0.484D;
/* 23 */         if (mc.getPlayer().isPotionActive(Potion.moveSpeed)) {
/* 24 */           double amplifier = mc.getPlayer().getActivePotionEffect(Potion.moveSpeed).getAmplifier();
/* 25 */           baseSpeed *= 1.0D + 0.13D * (amplifier + 1.0D);
/*    */         } 
/* 27 */         MovementUtil.minLimitStrafe((float)baseSpeed);
/*    */       } 
/*    */     } else {
/*    */       
/* 31 */       (mc.getPlayer()).motionX *= 1.001D;
/* 32 */       (mc.getPlayer()).motionZ *= 1.001D;
/*    */       
/* 34 */       if ((mc.getPlayer()).offGroundTicks == 5) {
/* 35 */         (mc.getPlayer()).motionY = -0.07840000152D;
/*    */       }
/* 37 */       MovementUtil.strafe();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 44 */     return "NCP Hop";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\speeds\hop\NCPHopSpeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
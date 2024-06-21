/*    */ package cc.slack.features.modules.impl.movement.speeds.vanilla;
/*    */ 
/*    */ import cc.slack.Slack;
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.impl.movement.Speed;
/*    */ import cc.slack.features.modules.impl.movement.speeds.ISpeed;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.player.MovementUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VanillaSpeed
/*    */   implements ISpeed
/*    */ {
/*    */   public void onUpdate(UpdateEvent event) {
/* 16 */     if ((mc.getPlayer()).onGround && MovementUtil.isMoving()) {
/* 17 */       mc.getPlayer().jump();
/* 18 */       MovementUtil.strafe(((Float)((Speed)Slack.getInstance().getModuleManager().getInstance(Speed.class)).vanillaspeed.getValue()).floatValue());
/*    */     } 
/* 20 */     if (MovementUtil.isMoving()) {
/* 21 */       MovementUtil.strafe();
/* 22 */       if (!((Boolean)((Speed)Slack.getInstance().getModuleManager().getInstance(Speed.class)).vanillaGround.getValue()).booleanValue()) {
/* 23 */         MovementUtil.strafe(((Float)((Speed)Slack.getInstance().getModuleManager().getInstance(Speed.class)).vanillaspeed.getValue()).floatValue());
/*    */       }
/*    */     } else {
/* 26 */       MovementUtil.resetMotion(false);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 33 */     return "Vanilla Hop";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\speeds\vanilla\VanillaSpeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
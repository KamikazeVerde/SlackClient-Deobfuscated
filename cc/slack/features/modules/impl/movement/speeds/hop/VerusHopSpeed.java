/*    */ package cc.slack.features.modules.impl.movement.speeds.hop;
/*    */ 
/*    */ import cc.slack.events.impl.player.MoveEvent;
/*    */ import cc.slack.features.modules.impl.movement.speeds.ISpeed;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.player.MovementUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VerusHopSpeed
/*    */   implements ISpeed
/*    */ {
/*    */   double moveSpeed;
/*    */   int airTicks;
/*    */   
/*    */   public void onEnable() {
/* 18 */     this.moveSpeed = 0.0D;
/* 19 */     this.airTicks = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onMove(MoveEvent event) {
/* 24 */     if ((mc.getPlayer()).onGround) {
/* 25 */       if (MovementUtil.isMoving()) event.setY(0.41999998688697815D); 
/* 26 */       this.moveSpeed = 0.6800000071525574D;
/* 27 */       this.airTicks = 0;
/*    */     } else {
/* 29 */       if (this.airTicks == 0) {
/* 30 */         this.moveSpeed *= 0.5934960376506356D;
/*    */       }
/* 32 */       this.airTicks++;
/*    */     } 
/*    */     
/* 35 */     MovementUtil.setSpeed(event, this.moveSpeed);
/* 36 */     this.moveSpeed *= 0.9800000190734863D;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 41 */     return "Verus Hop";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\speeds\hop\VerusHopSpeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
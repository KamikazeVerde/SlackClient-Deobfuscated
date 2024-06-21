/*    */ package cc.slack.events.impl.player;
/*    */ 
/*    */ import cc.slack.events.Event;
/*    */ 
/*    */ public class JumpEvent extends Event {
/*    */   private float yaw;
/*    */   
/*    */   public void setYaw(float yaw) {
/*  9 */     this.yaw = yaw; } public JumpEvent(float yaw) {
/* 10 */     this.yaw = yaw;
/*    */   } public float getYaw() {
/* 12 */     return this.yaw;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\events\impl\player\JumpEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
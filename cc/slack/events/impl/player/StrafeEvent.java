/*    */ package cc.slack.events.impl.player;
/*    */ 
/*    */ public class StrafeEvent extends Event {
/*    */   private float strafe;
/*    */   private float forward;
/*    */   private float friction;
/*    */   private float yaw;
/*    */   
/*  9 */   public void setStrafe(float strafe) { this.strafe = strafe; } public void setForward(float forward) { this.forward = forward; } public void setFriction(float friction) { this.friction = friction; } public void setYaw(float yaw) { this.yaw = yaw; } public StrafeEvent(float strafe, float forward, float friction, float yaw) {
/* 10 */     this.strafe = strafe; this.forward = forward; this.friction = friction; this.yaw = yaw;
/*    */   }
/* 12 */   public float getStrafe() { return this.strafe; }
/* 13 */   public float getForward() { return this.forward; }
/* 14 */   public float getFriction() { return this.friction; } public float getYaw() {
/* 15 */     return this.yaw;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\events\impl\player\StrafeEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
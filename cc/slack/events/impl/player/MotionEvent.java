/*    */ package cc.slack.events.impl.player;
/*    */ 
/*    */ public class MotionEvent extends Event {
/*    */   private double x;
/*    */   private double y;
/*    */   private double z;
/*    */   private float yaw;
/*    */   private float pitch;
/*    */   
/* 10 */   public void setX(double x) { this.x = x; } private float lastTickYaw; private float lastTickPitch; private boolean ground; private boolean forcedC06; private State state; private final EntityPlayerSP player; public void setY(double y) { this.y = y; } public void setZ(double z) { this.z = z; } public void setYaw(float yaw) { this.yaw = yaw; } public void setPitch(float pitch) { this.pitch = pitch; } public void setLastTickYaw(float lastTickYaw) { this.lastTickYaw = lastTickYaw; } public void setLastTickPitch(float lastTickPitch) { this.lastTickPitch = lastTickPitch; } public void setGround(boolean ground) { this.ground = ground; } public void setForcedC06(boolean forcedC06) { this.forcedC06 = forcedC06; } public void setState(State state) { this.state = state; }
/*    */   
/* 12 */   public double getX() { return this.x; } public double getY() { return this.y; } public double getZ() { return this.z; }
/* 13 */   public float getYaw() { return this.yaw; } public float getPitch() { return this.pitch; }
/* 14 */   public float getLastTickYaw() { return this.lastTickYaw; } public float getLastTickPitch() { return this.lastTickPitch; }
/* 15 */   public boolean isGround() { return this.ground; } public boolean isForcedC06() { return this.forcedC06; }
/* 16 */   public State getState() { return this.state; } public EntityPlayerSP getPlayer() {
/* 17 */     return this.player;
/*    */   }
/*    */   public MotionEvent(double x, double y, double z, float yaw, float pitch, float lastTickYaw, float lastTickPitch, boolean ground, EntityPlayerSP player) {
/* 20 */     this.state = State.PRE;
/* 21 */     this.x = x;
/* 22 */     this.y = y;
/* 23 */     this.z = z;
/* 24 */     this.yaw = yaw;
/* 25 */     this.pitch = pitch;
/* 26 */     this.lastTickYaw = lastTickYaw;
/* 27 */     this.lastTickPitch = lastTickPitch;
/* 28 */     this.ground = ground;
/* 29 */     this.player = player;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\events\impl\player\MotionEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package cc.slack.events.impl.player;
/*    */ 
/*    */ import cc.slack.events.Event;
/*    */ import cc.slack.utils.client.mc;
/*    */ 
/*    */ public class MoveEvent extends Event {
/*    */   private double x;
/*    */   private double y;
/*    */   private double z;
/*    */   public boolean safewalk;
/*    */   
/*    */   public MoveEvent(double x, double y, double z, boolean safewalk) {
/* 13 */     this.safewalk = false; this.x = x; this.y = y; this.z = z; this.safewalk = safewalk; } public double getX() { return this.x; } public boolean isSafewalk() { return this.safewalk; }
/*    */   public double getY() { return this.y; }
/*    */   public double getZ() { return this.z; } public void setZero() {
/* 16 */     setX(0.0D);
/* 17 */     setZ(0.0D);
/* 18 */     setY(0.0D);
/*    */   }
/*    */   
/*    */   public void setZeroXZ() {
/* 22 */     setX(0.0D);
/* 23 */     setZ(0.0D);
/*    */   }
/*    */   
/*    */   public void setX(double x) {
/* 27 */     (mc.getPlayer()).motionX = x;
/* 28 */     this.x = x;
/*    */   }
/*    */   
/*    */   public void setY(double y) {
/* 32 */     (mc.getPlayer()).motionY = y;
/* 33 */     this.y = y;
/*    */   }
/*    */   
/*    */   public void setZ(double z) {
/* 37 */     (mc.getPlayer()).motionZ = z;
/* 38 */     this.z = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\events\impl\player\MoveEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
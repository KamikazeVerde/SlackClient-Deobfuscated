/*    */ package cc.slack.features.modules.impl.movement.flights.impl;
/*    */ 
/*    */ import cc.slack.events.impl.player.CollideEvent;
/*    */ import cc.slack.events.impl.player.MoveEvent;
/*    */ import cc.slack.features.modules.impl.movement.flights.IFlight;
/*    */ import cc.slack.utils.client.mc;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AirJumpFlight
/*    */   implements IFlight
/*    */ {
/*    */   double startY;
/*    */   
/*    */   public void onEnable() {
/* 22 */     this.startY = Math.floor((mc.getPlayer()).posY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onCollide(CollideEvent event) {
/* 27 */     if (event.getBlock() instanceof net.minecraft.block.BlockAir && event.getY() <= this.startY) {
/* 28 */       event.setBoundingBox(AxisAlignedBB.fromBounds(event.getX(), event.getY(), event.getZ(), event.getX() + 1.0D, this.startY, event.getZ() + 1.0D));
/*    */     }
/*    */   }
/*    */   
/*    */   public void onMove(MoveEvent event) {
/* 33 */     if ((mc.getGameSettings()).keyBindJump.isPressed() && (mc.getPlayer()).onGround) {
/* 34 */       event.setY(0.41999998688697815D);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 40 */     return "Air Jump";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\flights\impl\AirJumpFlight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
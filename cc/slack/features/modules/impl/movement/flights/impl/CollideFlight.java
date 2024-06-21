/*    */ package cc.slack.features.modules.impl.movement.flights.impl;
/*    */ 
/*    */ import cc.slack.events.impl.player.CollideEvent;
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.impl.movement.flights.IFlight;
/*    */ import cc.slack.utils.client.mc;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CollideFlight
/*    */   implements IFlight
/*    */ {
/*    */   double startY;
/*    */   
/*    */   public void onEnable() {
/* 20 */     this.startY = Math.floor((mc.getPlayer()).posY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate(UpdateEvent event) {
/* 25 */     (mc.getGameSettings()).keyBindJump.pressed = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onCollide(CollideEvent event) {
/* 30 */     if (event.getBlock() instanceof net.minecraft.block.BlockAir && event.getY() <= this.startY) {
/* 31 */       event.setBoundingBox(AxisAlignedBB.fromBounds(event.getX(), event.getY(), event.getZ(), event.getX() + 1.0D, this.startY, event.getZ() + 1.0D));
/*    */     }
/*    */   }
/*    */   
/*    */   public String toString() {
/* 36 */     return "Collide";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\flights\impl\CollideFlight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
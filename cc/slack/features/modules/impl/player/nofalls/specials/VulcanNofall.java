/*    */ package cc.slack.features.modules.impl.player.nofalls.specials;
/*    */ 
/*    */ import cc.slack.events.impl.player.MotionEvent;
/*    */ import cc.slack.features.modules.impl.player.nofalls.INoFall;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.network.PacketUtil;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C03PacketPlayer;
/*    */ 
/*    */ 
/*    */ public class VulcanNofall
/*    */   implements INoFall
/*    */ {
/*    */   int count;
/*    */   boolean isFixed;
/*    */   
/*    */   public void onEnable() {
/* 18 */     this.count = 0;
/* 19 */     this.isFixed = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onMotion(MotionEvent event) {
/* 24 */     if ((mc.getPlayer()).onGround && this.isFixed) {
/* 25 */       this.isFixed = false;
/* 26 */       this.count = 0;
/* 27 */       (mc.getTimer()).timerSpeed = 1.0F;
/*    */     } 
/*    */     
/* 30 */     if ((mc.getPlayer()).fallDistance > 2.0F) {
/* 31 */       this.isFixed = true;
/* 32 */       (mc.getTimer()).timerSpeed = 0.9F;
/*    */     } 
/*    */ 
/*    */     
/* 36 */     PacketUtil.sendNoEvent((Packet)new C03PacketPlayer(true));
/* 37 */     (mc.getPlayer()).motionY = -0.1D;
/* 38 */     (mc.getPlayer()).fallDistance = 0.0F;
/* 39 */     (mc.getPlayer()).motionY *= 1.1D;
/*    */     
/* 41 */     if ((mc.getPlayer()).fallDistance > 2.9F && this.count++ > 5) {
/* 42 */       this.count = 0;
/*    */     }
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     return "Vulcan";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\player\nofalls\specials\VulcanNofall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
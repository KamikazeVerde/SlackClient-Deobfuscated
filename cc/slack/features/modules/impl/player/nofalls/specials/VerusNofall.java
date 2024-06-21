/*    */ package cc.slack.features.modules.impl.player.nofalls.specials;
/*    */ 
/*    */ import cc.slack.events.impl.network.PacketEvent;
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.impl.player.nofalls.INoFall;
/*    */ import cc.slack.utils.client.mc;
/*    */ import net.minecraft.network.play.client.C03PacketPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VerusNofall
/*    */   implements INoFall
/*    */ {
/*    */   boolean spoof;
/*    */   int packet1Count;
/*    */   boolean packetModify;
/*    */   
/*    */   public void onEnable() {
/* 21 */     this.packetModify = false;
/* 22 */     this.packet1Count = 0;
/* 23 */     this.spoof = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate(UpdateEvent event) {
/* 28 */     if ((mc.getPlayer()).fallDistance - (mc.getPlayer()).motionY > 3.0D) {
/* 29 */       (mc.getPlayer()).motionY = 0.0D;
/* 30 */       (mc.getPlayer()).motionX *= 0.5D;
/* 31 */       (mc.getPlayer()).motionZ *= 0.5D;
/* 32 */       (mc.getPlayer()).fallDistance = 0.0F;
/* 33 */       this.spoof = true;
/*    */     } 
/* 35 */     if ((mc.getPlayer()).fallDistance / 3.0F > this.packet1Count) {
/* 36 */       this.packet1Count = (int)((mc.getPlayer()).fallDistance / 3.0F);
/* 37 */       this.packetModify = true;
/*    */     } 
/* 39 */     if ((mc.getPlayer()).onGround) {
/* 40 */       this.packet1Count = 0;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPacket(PacketEvent event) {
/* 47 */     if (this.spoof && event.getPacket() instanceof C03PacketPlayer) {
/* 48 */       ((C03PacketPlayer)event.getPacket()).onGround = true;
/* 49 */       this.spoof = false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String toString() {
/* 54 */     return "Verus";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\player\nofalls\specials\VerusNofall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
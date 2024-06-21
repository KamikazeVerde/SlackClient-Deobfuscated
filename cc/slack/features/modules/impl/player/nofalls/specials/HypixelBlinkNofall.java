/*    */ package cc.slack.features.modules.impl.player.nofalls.specials;
/*    */ 
/*    */ import cc.slack.events.impl.network.PacketEvent;
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.events.impl.render.RenderEvent;
/*    */ import cc.slack.features.modules.impl.player.nofalls.INoFall;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.font.Fonts;
/*    */ import cc.slack.utils.player.BlinkUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.network.play.client.C03PacketPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HypixelBlinkNofall
/*    */   implements INoFall
/*    */ {
/*    */   boolean spoof;
/*    */   
/*    */   public void onEnable() {
/* 24 */     this.spoof = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 29 */     BlinkUtil.disable();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate(UpdateEvent event) {
/* 34 */     if ((mc.getPlayer()).onGround && this.spoof) {
/* 35 */       this.spoof = false;
/* 36 */       BlinkUtil.disable();
/* 37 */     } else if ((mc.getPlayer()).offGroundTicks == 1 && (mc.getPlayer()).motionY < 0.0D) {
/* 38 */       this.spoof = true;
/* 39 */       BlinkUtil.enable(false, true);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPacket(PacketEvent event) {
/* 46 */     if (this.spoof && event.getPacket() instanceof C03PacketPlayer) {
/* 47 */       ((C03PacketPlayer)event.getPacket()).onGround = true;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onRender(RenderEvent e) {
/* 54 */     if (e.state != RenderEvent.State.RENDER_2D)
/* 55 */       return;  if (!this.spoof)
/*    */       return; 
/* 57 */     ScaledResolution sr = mc.getScaledResolution();
/* 58 */     Fonts.apple18.drawStringWithShadow("Blinking " + BlinkUtil.getSize(), sr.getScaledWidth() / 2.0F + 10.0F, sr.getScaledHeight() / 2.0F - 10.0F, 16777215);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 62 */     return "Hypixel Blink";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\player\nofalls\specials\HypixelBlinkNofall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
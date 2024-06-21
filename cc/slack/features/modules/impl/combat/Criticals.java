/*    */ package cc.slack.features.modules.impl.combat;
/*    */ 
/*    */ import cc.slack.events.impl.network.PacketEvent;
/*    */ import cc.slack.events.impl.player.AttackEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.network.PacketUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C03PacketPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Criticals", category = Category.COMBAT)
/*    */ public class Criticals
/*    */   extends Module
/*    */ {
/* 25 */   public final ModeValue<String> criticalMode = new ModeValue((Object[])new String[] { "Edit", "Vulcan", "Packet", "Mini" });
/* 26 */   public final BooleanValue onlyGround = new BooleanValue("Only Ground", true);
/*    */ 
/*    */   
/*    */   private boolean spoof;
/*    */ 
/*    */   
/*    */   public Criticals() {
/* 33 */     this.spoof = false;
/*    */     addSettings(new Value[] { (Value)this.criticalMode, (Value)this.onlyGround });
/*    */   } @Listen
/*    */   public void onAttack(AttackEvent event) {
/* 37 */     switch (((String)this.criticalMode.getValue()).toLowerCase()) {
/*    */       case "edit":
/* 39 */         this.spoof = true;
/*    */         break;
/*    */       case "vulcan":
/* 42 */         sendPacket(0.16477328182606651D, false);
/* 43 */         sendPacket(0.08307781780646721D, false);
/* 44 */         sendPacket(0.0030162615090425808D, false);
/*    */         break;
/*    */       case "packet":
/* 47 */         sendPacket(-0.07840000152D, false);
/*    */         break;
/*    */       case "mini":
/* 50 */         sendPacket(1.0E-4D, true);
/* 51 */         sendPacket(0.0D, false);
/*    */         break;
/*    */     } 
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onPacket(PacketEvent event) {
/* 58 */     if (event.getPacket() instanceof C03PacketPlayer && 
/* 59 */       this.spoof) {
/* 60 */       ((C03PacketPlayer)event.getPacket()).onGround = false;
/* 61 */       this.spoof = false;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void sendPacket(double yOffset, boolean ground) {
/* 67 */     PacketUtil.send((Packet)new C03PacketPlayer.C04PacketPlayerPosition((mc.getPlayer()).posX, (mc.getPlayer()).posY + yOffset, (mc.getPlayer()).posZ, ground));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\combat\Criticals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
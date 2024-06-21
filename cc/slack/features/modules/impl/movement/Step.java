/*    */ package cc.slack.features.modules.impl.movement;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C03PacketPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Step", category = Category.MOVEMENT)
/*    */ public class Step
/*    */   extends Module
/*    */ {
/* 21 */   private final ModeValue<String> mode = new ModeValue("Step Mode", (Object[])new String[] { "Vanilla", "NCP", "Verus", "Vulcan" });
/* 22 */   private final NumberValue<Float> timerSpeed = new NumberValue("Timer", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(2.0F), Float.valueOf(0.05F));
/*    */ 
/*    */   
/*    */   public Step() {
/* 26 */     addSettings(new Value[] { (Value)this.mode, (Value)this.timerSpeed });
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 31 */     if ((mc.getPlayer()).isCollidedHorizontally && (mc.getPlayer()).onGround) {
/* 32 */       (mc.getTimer()).timerSpeed = ((Float)this.timerSpeed.getValue()).floatValue();
/* 33 */       switch (((String)this.mode.getValue()).toLowerCase()) {
/*    */         case "vanilla":
/* 35 */           (mc.getPlayer()).stepHeight = 1.0F;
/*    */           break;
/*    */         case "verus":
/* 38 */           mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition((mc.getPlayer()).posX, (mc.getPlayer()).posY + 0.41999998688697815D, (mc.getPlayer()).posZ, (mc.getPlayer()).onGround));
/* 39 */           mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition((mc.getPlayer()).posX, (mc.getPlayer()).posY + 0.8500000238418579D, (mc.getPlayer()).posZ, (mc.getPlayer()).onGround));
/* 40 */           (mc.getPlayer()).stepHeight = 1.0F;
/* 41 */           (mc.getTimer()).timerSpeed = 0.91F;
/*    */           break;
/*    */         case "ncp":
/* 44 */           mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition((mc.getPlayer()).posX, (mc.getPlayer()).posY + 0.41999998688697815D, (mc.getPlayer()).posZ, (mc.getPlayer()).onGround));
/* 45 */           mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition((mc.getPlayer()).posX, (mc.getPlayer()).posY + 0.7531999945640564D, (mc.getPlayer()).posZ, (mc.getPlayer()).onGround));
/* 46 */           (mc.getPlayer()).stepHeight = 1.0F;
/*    */           break;
/*    */         case "vulcan":
/* 49 */           mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition((mc.getPlayer()).posX, (mc.getPlayer()).posY + 0.5D, (mc.getPlayer()).posZ, (mc.getPlayer()).onGround));
/* 50 */           (mc.getPlayer()).stepHeight = 1.0F;
/*    */           break;
/*    */       } 
/*    */     
/*    */     } else {
/* 55 */       (mc.getPlayer()).stepHeight = 0.5F;
/* 56 */       (mc.getTimer()).timerSpeed = 1.0F;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\Step.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
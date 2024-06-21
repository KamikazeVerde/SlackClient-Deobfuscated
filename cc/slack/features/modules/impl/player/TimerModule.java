/*    */ package cc.slack.features.modules.impl.player;
/*    */ 
/*    */ import cc.slack.events.impl.network.PacketEvent;
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.events.impl.render.RenderEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.other.PrintUtil;
/*    */ import cc.slack.utils.other.TimeUtil;
/*    */ import cc.slack.utils.player.TimerUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.network.PacketDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Timer", category = Category.PLAYER)
/*    */ public class TimerModule
/*    */   extends Module
/*    */ {
/* 27 */   private final ModeValue<String> mode = new ModeValue((Object[])new String[] { "Normal", "Balance" });
/* 28 */   private final NumberValue<Float> speed = new NumberValue("Speed", Float.valueOf(1.5F), Float.valueOf(0.1F), Float.valueOf(10.0F), Float.valueOf(0.25F));
/*    */   
/*    */   private int balance;
/*    */   public boolean balancing;
/* 32 */   private final TimeUtil timer = new TimeUtil();
/*    */ 
/*    */   
/*    */   public TimerModule() {
/* 36 */     addSettings(new Value[] { (Value)this.mode, (Value)this.speed });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 41 */     this.balance = 0;
/* 42 */     this.balancing = false;
/* 43 */     this.timer.reset();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 48 */     TimerUtil.reset();
/*    */   }
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 54 */     switch (((String)this.mode.getValue()).toLowerCase()) {
/*    */       case "balance":
/* 56 */         if (mc.getPlayer() != null && (mc.getPlayer()).ticksExisted % 20 == 0) {
/* 57 */           PrintUtil.debugMessage("Balance: " + this.balance);
/*    */         }
/*    */         break;
/*    */       case "normal":
/* 61 */         (mc.getTimer()).timerSpeed = ((Float)this.speed.getValue()).floatValue();
/*    */         break;
/*    */     } 
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onPacket(PacketEvent event) {
/* 68 */     if (event.getDirection() != PacketDirection.INCOMING)
/*    */       return; 
/* 70 */     if (((String)this.mode.getValue()).equalsIgnoreCase("balance") && 
/* 71 */       event.getPacket() instanceof net.minecraft.network.play.client.C03PacketPlayer) {
/* 72 */       if (!(event.getPacket() instanceof net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition) && 
/* 73 */         !(event.getPacket() instanceof net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook) && 
/* 74 */         !(event.getPacket() instanceof net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook)) {
/* 75 */         this.balancing = true;
/* 76 */         this.balance--;
/* 77 */         event.cancel();
/*    */       } else {
/* 79 */         this.balancing = false;
/* 80 */         this.balance++;
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onRender(RenderEvent event) {
/* 88 */     if (event.state != RenderEvent.State.RENDER_2D)
/* 89 */       return;  if (((String)this.mode.getValue()).equalsIgnoreCase("balance") && 
/* 90 */       this.timer.hasReached(50)) {
/* 91 */       this.balance--;
/* 92 */       this.timer.reset();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\player\TimerModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package cc.slack.features.modules.impl.player;
/*    */ 
/*    */ import cc.slack.events.impl.network.PacketEvent;
/*    */ import cc.slack.events.impl.player.MotionEvent;
/*    */ import cc.slack.events.impl.player.MoveEvent;
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.features.modules.impl.player.nofalls.INoFall;
/*    */ import cc.slack.features.modules.impl.player.nofalls.basics.NoGroundNofall;
/*    */ import cc.slack.features.modules.impl.player.nofalls.basics.SpoofGroundNofall;
/*    */ import cc.slack.features.modules.impl.player.nofalls.basics.VanillaNofall;
/*    */ import cc.slack.features.modules.impl.player.nofalls.specials.HypixelBlinkNofall;
/*    */ import cc.slack.features.modules.impl.player.nofalls.specials.VerusNofall;
/*    */ import cc.slack.features.modules.impl.player.nofalls.specials.VulcanNofall;
/*    */ import cc.slack.utils.client.mc;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ 
/*    */ @ModuleInfo(name = "NoFall", category = Category.PLAYER)
/*    */ public class NoFall
/*    */   extends Module {
/* 25 */   private final ModeValue<INoFall> mode = new ModeValue((Object[])new INoFall[] { (INoFall)new VanillaNofall(), (INoFall)new SpoofGroundNofall(), (INoFall)new NoGroundNofall(), (INoFall)new HypixelBlinkNofall(), (INoFall)new VulcanNofall(), (INoFall)new VerusNofall() });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NoFall() {
/* 40 */     addSettings(new Value[] { (Value)this.mode });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 45 */     ((INoFall)this.mode.getValue()).onEnable();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 50 */     ((INoFall)this.mode.getValue()).onDisable();
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onMove(MoveEvent event) {
/* 55 */     ((INoFall)this.mode.getValue()).onMove(event);
/*    */   }
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 61 */     if (mc.getPlayer().isSpectator() || (mc.getPlayer()).capabilities.allowFlying || (mc.getPlayer()).capabilities.disableDamage) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 66 */     ((INoFall)this.mode.getValue()).onUpdate(event);
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onMotion(MotionEvent event) {
/* 71 */     ((INoFall)this.mode.getValue()).onMotion(event);
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onPacket(PacketEvent event) {
/* 76 */     ((INoFall)this.mode.getValue()).onPacket(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\player\NoFall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package cc.slack.features.modules.impl.movement;
/*    */ 
/*    */ import cc.slack.events.impl.network.PacketEvent;
/*    */ import cc.slack.events.impl.player.CollideEvent;
/*    */ import cc.slack.events.impl.player.MotionEvent;
/*    */ import cc.slack.events.impl.player.MoveEvent;
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.features.modules.impl.movement.flights.IFlight;
/*    */ import cc.slack.features.modules.impl.movement.flights.impl.AirJumpFlight;
/*    */ import cc.slack.features.modules.impl.movement.flights.impl.ChunkFlight;
/*    */ import cc.slack.features.modules.impl.movement.flights.impl.CollideFlight;
/*    */ import cc.slack.features.modules.impl.movement.flights.impl.VanillaFlight;
/*    */ import cc.slack.features.modules.impl.movement.flights.impl.VerusFlight;
/*    */ import cc.slack.features.modules.impl.movement.flights.impl.VerusJumpFlight;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Flight", category = Category.MOVEMENT, key = 33)
/*    */ public class Flight
/*    */   extends Module
/*    */ {
/* 27 */   private final ModeValue<IFlight> mode = new ModeValue((Object[])new IFlight[] { (IFlight)new VanillaFlight(), (IFlight)new VerusJumpFlight(), (IFlight)new VerusFlight(), (IFlight)new ChunkFlight(), (IFlight)new CollideFlight(), (IFlight)new AirJumpFlight() });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Flight() {
/* 38 */     addSettings(new Value[] { (Value)this.mode });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 43 */     ((IFlight)this.mode.getValue()).onEnable();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 48 */     ((IFlight)this.mode.getValue()).onDisable();
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onMove(MoveEvent event) {
/* 53 */     ((IFlight)this.mode.getValue()).onMove(event);
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 58 */     ((IFlight)this.mode.getValue()).onUpdate(event);
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onPacket(PacketEvent event) {
/* 63 */     ((IFlight)this.mode.getValue()).onPacket(event);
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onCollide(CollideEvent event) {
/* 68 */     ((IFlight)this.mode.getValue()).onCollide(event);
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onMotion(MotionEvent event) {
/* 73 */     ((IFlight)this.mode.getValue()).onMotion(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\Flight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
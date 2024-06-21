/*     */ package cc.slack.features.modules.impl.movement.flights.impl;
/*     */ 
/*     */ import cc.slack.Slack;
/*     */ import cc.slack.events.State;
/*     */ import cc.slack.events.impl.network.PacketEvent;
/*     */ import cc.slack.events.impl.player.MotionEvent;
/*     */ import cc.slack.events.impl.player.MoveEvent;
/*     */ import cc.slack.features.modules.impl.movement.Flight;
/*     */ import cc.slack.features.modules.impl.movement.flights.IFlight;
/*     */ import cc.slack.utils.client.mc;
/*     */ import cc.slack.utils.player.MovementUtil;
/*     */ import cc.slack.utils.player.TimerUtil;
/*     */ import net.minecraft.network.PacketDirection;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ 
/*     */ 
/*     */ public class VerusFlight
/*     */   implements IFlight
/*     */ {
/*  20 */   double moveSpeed = 0.0D;
/*     */   
/*  22 */   int stage = 0;
/*  23 */   int hops = 0;
/*  24 */   int ticks = 0;
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  28 */     this.stage = -1;
/*  29 */     this.hops = 0;
/*  30 */     this.ticks = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onMove(MoveEvent event) {
/*  35 */     switch (this.stage) {
/*     */       case -1:
/*  37 */         this.stage++;
/*     */         break;
/*     */       case 0:
/*  40 */         event.setZeroXZ();
/*     */         
/*  42 */         if (this.hops >= 4 && (mc.getPlayer()).onGround) {
/*  43 */           this.stage++;
/*     */           
/*     */           return;
/*     */         } 
/*  47 */         if ((mc.getPlayer()).onGround) {
/*  48 */           event.setY(0.41999998688697815D);
/*  49 */           this.ticks = 0;
/*  50 */           this.hops++; break;
/*     */         } 
/*  52 */         this.ticks++;
/*     */         break;
/*     */       
/*     */       case 1:
/*  56 */         TimerUtil.reset();
/*  57 */         event.setZeroXZ();
/*     */         
/*  59 */         if ((mc.getPlayer()).hurtTime > 0) {
/*  60 */           this.ticks = 0;
/*  61 */           this.moveSpeed = 0.525D;
/*  62 */           this.stage++;
/*  63 */           event.setY(0.41999998688697815D);
/*  64 */           MovementUtil.setSpeed(event, this.moveSpeed);
/*     */         } 
/*     */         break;
/*     */       case 2:
/*  68 */         if (event.getY() < 0.0D) {
/*  69 */           event.setY(-0.033D);
/*     */         }
/*  71 */         if (this.ticks == 0) this.moveSpeed *= 7.0D;
/*     */         
/*  73 */         this.moveSpeed -= this.moveSpeed / 159.0D;
/*  74 */         this.ticks++;
/*     */         
/*  76 */         MovementUtil.setSpeed(event, this.moveSpeed);
/*     */         
/*  78 */         if ((mc.getPlayer()).hurtTime == 0 && ((mc.getPlayer()).onGround || (mc.getPlayer()).isCollidedHorizontally)) {
/*  79 */           ((Flight)Slack.getInstance().getModuleManager().getInstance(Flight.class)).toggle();
/*     */         }
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onMotion(MotionEvent event) {
/*  86 */     if (event.getState() != State.PRE)
/*  87 */       return;  event.setYaw(MovementUtil.getDirection());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPacket(PacketEvent event) {
/*  92 */     if (event.getDirection() != PacketDirection.OUTGOING)
/*  93 */       return;  if (event.getPacket() instanceof C03PacketPlayer && 
/*  94 */       this.stage == 0 && this.hops >= 1) {
/*  95 */       ((C03PacketPlayer)event.getPacket()).onGround = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 102 */     return "Verus";
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\flights\impl\VerusFlight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
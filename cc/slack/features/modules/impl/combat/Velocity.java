/*     */ package cc.slack.features.modules.impl.combat;
/*     */ 
/*     */ import cc.slack.events.impl.network.PacketEvent;
/*     */ import cc.slack.events.impl.player.UpdateEvent;
/*     */ import cc.slack.features.modules.api.Category;
/*     */ import cc.slack.features.modules.api.Module;
/*     */ import cc.slack.features.modules.api.ModuleInfo;
/*     */ import cc.slack.features.modules.api.settings.Value;
/*     */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*     */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*     */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*     */ import cc.slack.utils.client.mc;
/*     */ import cc.slack.utils.player.MovementUtil;
/*     */ import io.github.nevalackin.radbus.Listen;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.network.play.server.S12PacketEntityVelocity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ModuleInfo(name = "Velocity", category = Category.COMBAT)
/*     */ public class Velocity
/*     */   extends Module
/*     */ {
/*  27 */   private final ModeValue<String> mode = new ModeValue((Object[])new String[] { "Cancel", "Motion", "Tick", "Reverse", "Hypixel", "Hypixel Damage Strafe", "Intave" });
/*     */   
/*  29 */   private final NumberValue<Integer> vertical = new NumberValue("Vertical", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(100), Integer.valueOf(1));
/*  30 */   private final NumberValue<Integer> horizontal = new NumberValue("Horizontal", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(100), Integer.valueOf(1));
/*  31 */   private final NumberValue<Integer> velocityTick = new NumberValue("Velocity Tick", Integer.valueOf(5), Integer.valueOf(0), Integer.valueOf(20), Integer.valueOf(1));
/*  32 */   private final BooleanValue onlyground = new BooleanValue("OnlyGround", false);
/*  33 */   private final BooleanValue noFire = new BooleanValue("NoFire", false);
/*     */   
/*  35 */   int jumped = 0;
/*     */   
/*     */   public Velocity() {
/*  38 */     addSettings(new Value[] { (Value)this.mode, (Value)this.vertical, (Value)this.horizontal, (Value)this.velocityTick, (Value)this.onlyground, (Value)this.noFire });
/*     */   }
/*     */   
/*     */   @Listen
/*     */   public void onPacket(PacketEvent event) {
/*  43 */     if (mc.getPlayer() == null || mc.getWorld() == null)
/*  44 */       return;  if (((Boolean)this.noFire.getValue()).booleanValue() && mc.getPlayer().isBurning())
/*     */       return; 
/*  46 */     if (((Boolean)this.onlyground.getValue()).booleanValue() && !(mc.getPlayer()).onGround) {
/*     */       return;
/*     */     }
/*     */     
/*  50 */     if (event.getPacket() instanceof S12PacketEntityVelocity) {
/*  51 */       S12PacketEntityVelocity packet = (S12PacketEntityVelocity)event.getPacket();
/*  52 */       if (packet.getEntityID() == mc.getPlayer().getEntityId()) {
/*  53 */         switch (((String)this.mode.getValue()).toLowerCase()) {
/*     */           case "motion":
/*  55 */             if (((Integer)this.horizontal.getValue()).intValue() == 0) {
/*  56 */               event.cancel();
/*  57 */               (mc.getPlayer()).motionY = packet.getMotionY() * ((Integer)this.vertical.getValue()).doubleValue() / 100.0D / 8000.0D; break;
/*  58 */             }  if (((Integer)this.vertical.getValue()).intValue() == 0) {
/*  59 */               event.cancel();
/*  60 */               (mc.getPlayer()).motionX = packet.getMotionX() * ((Integer)this.horizontal.getValue()).doubleValue() / 100.0D / 8000.0D;
/*  61 */               (mc.getPlayer()).motionZ = packet.getMotionZ() * ((Integer)this.horizontal.getValue()).doubleValue() / 100.0D / 8000.0D; break;
/*     */             } 
/*  63 */             packet.setMotionX(packet.getMotionX() * ((Integer)this.horizontal.getValue()).intValue() / 100);
/*  64 */             packet.setMotionY(packet.getMotionY() * ((Integer)this.vertical.getValue()).intValue() / 100);
/*  65 */             packet.setMotionZ(packet.getMotionZ() * ((Integer)this.horizontal.getValue()).intValue() / 100);
/*     */             break;
/*     */           
/*     */           case "cancel":
/*  69 */             event.cancel();
/*     */             break;
/*     */           case "hypixel":
/*  72 */             event.cancel();
/*  73 */             (mc.getPlayer()).motionY = packet.getMotionY() * ((Integer)this.vertical.getValue()).doubleValue() / 100.0D / 8000.0D;
/*     */             break;
/*     */           case "reverse":
/*  76 */             event.cancel();
/*  77 */             (mc.getPlayer()).motionY = packet.getMotionY() / 8000.0D;
/*  78 */             (mc.getPlayer()).motionX = packet.getMotionX() / 8000.0D;
/*  79 */             (mc.getPlayer()).motionZ = packet.getMotionZ() / 8000.0D;
/*  80 */             MovementUtil.strafe();
/*     */             break;
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Listen
/*     */   public void onUpdate(UpdateEvent event) {
/*  91 */     if (mc.getPlayer().isInWater() || mc.getPlayer().isInLava() || (mc.getPlayer()).isInWeb) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  96 */     switch (((String)this.mode.getValue()).toLowerCase()) {
/*     */       case "intave":
/*  98 */         if ((mc.getPlayer()).hurtTime == 9) {
/*  99 */           if (++this.jumped % 2 == 0 && (mc.getPlayer()).onGround && mc.getPlayer().isSprinting() && mc.getCurrentScreen() == null) {
/* 100 */             (mc.getGameSettings()).keyBindJump.pressed = true;
/* 101 */             this.jumped = 0;
/*     */           }  break;
/*     */         } 
/* 104 */         (mc.getGameSettings()).keyBindJump.pressed = GameSettings.isKeyDown((mc.getGameSettings()).keyBindJump);
/*     */         break;
/*     */       
/*     */       case "hypixel damage strafe":
/* 108 */         if ((mc.getPlayer()).hurtTime == 8) {
/* 109 */           MovementUtil.strafe((float)MovementUtil.getSpeed() * 0.75F);
/*     */         }
/*     */         break;
/*     */       case "tick":
/* 113 */         if ((mc.getPlayer()).ticksSinceLastDamage == ((Integer)this.velocityTick.getValue()).intValue()) {
/* 114 */           (mc.getPlayer()).motionX *= ((Integer)this.horizontal.getValue()).doubleValue() / 100.0D;
/* 115 */           (mc.getPlayer()).motionY *= ((Integer)this.vertical.getValue()).doubleValue() / 100.0D;
/* 116 */           (mc.getPlayer()).motionZ *= ((Integer)this.horizontal.getValue()).doubleValue() / 100.0D;
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\combat\Velocity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
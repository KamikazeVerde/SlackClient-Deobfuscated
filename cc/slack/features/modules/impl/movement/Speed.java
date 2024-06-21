/*    */ package cc.slack.features.modules.impl.movement;
/*    */ 
/*    */ import cc.slack.events.impl.network.PacketEvent;
/*    */ import cc.slack.events.impl.player.MoveEvent;
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ import cc.slack.features.modules.impl.movement.speeds.ISpeed;
/*    */ import cc.slack.features.modules.impl.movement.speeds.hop.HypixelHopSpeed;
/*    */ import cc.slack.features.modules.impl.movement.speeds.hop.NCPHopSpeed;
/*    */ import cc.slack.features.modules.impl.movement.speeds.hop.VerusHopSpeed;
/*    */ import cc.slack.features.modules.impl.movement.speeds.hop.VulcanHopSpeed;
/*    */ import cc.slack.features.modules.impl.movement.speeds.lowhops.VulcanLowSpeed;
/*    */ import cc.slack.features.modules.impl.movement.speeds.vanilla.GroundStrafeSpeed;
/*    */ import cc.slack.features.modules.impl.movement.speeds.vanilla.LegitSpeed;
/*    */ import cc.slack.features.modules.impl.movement.speeds.vanilla.StrafeSpeed;
/*    */ import cc.slack.features.modules.impl.movement.speeds.vanilla.VanillaSpeed;
/*    */ import cc.slack.utils.client.mc;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ @ModuleInfo(name = "Speed", category = Category.MOVEMENT, key = 48)
/*    */ public class Speed
/*    */   extends Module {
/* 30 */   private final ModeValue<ISpeed> mode = new ModeValue((Object[])new ISpeed[] { (ISpeed)new VanillaSpeed(), (ISpeed)new StrafeSpeed(), (ISpeed)new GroundStrafeSpeed(), (ISpeed)new LegitSpeed(), (ISpeed)new VerusHopSpeed(), (ISpeed)new HypixelHopSpeed(), (ISpeed)new NCPHopSpeed(), (ISpeed)new VulcanLowSpeed(), (ISpeed)new VulcanHopSpeed() });
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   public final NumberValue<Float> vanillaspeed = new NumberValue("Vanilla Speed", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(3.0F), Float.valueOf(0.01F));
/* 53 */   public final BooleanValue vanillaGround = new BooleanValue("Vanilla Only Ground", false);
/*    */ 
/*    */   
/* 56 */   public final BooleanValue jumpFix = new BooleanValue("Jump Fix", true);
/*    */ 
/*    */   
/*    */   public Speed() {
/* 60 */     addSettings(new Value[] { (Value)this.mode, (Value)this.vanillaspeed, (Value)this.vanillaGround, (Value)this.jumpFix });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 65 */     ((ISpeed)this.mode.getValue()).onEnable();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 70 */     if (((Boolean)this.jumpFix.getValue()).booleanValue()) (mc.getGameSettings()).keyBindJump.pressed = GameSettings.isKeyDown((mc.getGameSettings()).keyBindJump); 
/* 71 */     ((ISpeed)this.mode.getValue()).onDisable();
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onMove(MoveEvent event) {
/* 76 */     ((ISpeed)this.mode.getValue()).onMove(event);
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 81 */     if (((Boolean)this.jumpFix.getValue()).booleanValue()) (mc.getGameSettings()).keyBindJump.pressed = false; 
/* 82 */     ((ISpeed)this.mode.getValue()).onUpdate(event);
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onPacket(PacketEvent event) {
/* 87 */     ((ISpeed)this.mode.getValue()).onPacket(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\Speed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package cc.slack.features.modules.impl.ghost;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "JumpReset", category = Category.GHOST)
/*    */ public class JumpReset
/*    */   extends Module
/*    */ {
/* 22 */   public final NumberValue<Double> chance = new NumberValue("Chance", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(1.0D), Double.valueOf(0.01D));
/*    */   
/*    */   boolean enable;
/*    */   
/*    */   public JumpReset() {
/* 27 */     addSettings(new Value[] { (Value)this.chance });
/*    */   }
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 33 */     if (mc.getCurrentScreen() != null)
/* 34 */       return;  if ((mc.getPlayer()).hurtTime == 10) {
/* 35 */       this.enable = (MathHelper.getRandomDoubleInRange(new Random(), 0.0D, 1.0D) <= ((Double)this.chance.getValue()).doubleValue());
/*    */     }
/* 37 */     if (this.enable)
/* 38 */       return;  if ((mc.getPlayer()).hurtTime >= 8) {
/* 39 */       (mc.getGameSettings()).keyBindJump.pressed = true;
/*    */     }
/* 41 */     if ((mc.getPlayer()).hurtTime >= 7) {
/* 42 */       (mc.getGameSettings()).keyBindForward.pressed = true;
/* 43 */     } else if ((mc.getPlayer()).hurtTime >= 4) {
/* 44 */       (mc.getGameSettings()).keyBindJump.pressed = false;
/* 45 */       (mc.getGameSettings()).keyBindForward.pressed = false;
/* 46 */     } else if ((mc.getPlayer()).hurtTime > 1) {
/* 47 */       (mc.getGameSettings()).keyBindForward.pressed = GameSettings.isKeyDown((mc.getGameSettings()).keyBindForward);
/* 48 */       (mc.getGameSettings()).keyBindJump.pressed = GameSettings.isKeyDown((mc.getGameSettings()).keyBindJump);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\ghost\JumpReset.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
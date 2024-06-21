/*    */ package cc.slack.features.modules.impl.ghost;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.other.TimeUtil;
/*    */ import cc.slack.utils.player.PlayerUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "LegitScaffold", category = Category.GHOST)
/*    */ public class LegitScaffold
/*    */   extends Module
/*    */ {
/* 23 */   private final NumberValue<Integer> sneakTime = new NumberValue("Sneak Time", Integer.valueOf(60), Integer.valueOf(0), Integer.valueOf(300), Integer.valueOf(20));
/* 24 */   private final BooleanValue onlyGround = new BooleanValue("Only Ground", true);
/* 25 */   private final BooleanValue holdSneak = new BooleanValue("Hold Sneak", false);
/*    */   
/*    */   private boolean shouldSneak = false;
/* 28 */   private final TimeUtil sneakTimer = new TimeUtil();
/*    */   
/*    */   public LegitScaffold() {
/* 31 */     addSettings(new Value[] { (Value)this.sneakTime, (Value)this.onlyGround, (Value)this.holdSneak });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 36 */     (mc.getGameSettings()).keyBindSneak.pressed = GameSettings.isKeyDown((mc.getGameSettings()).keyBindSneak);
/*    */   }
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 42 */     if (mc.getCurrentScreen() != null)
/* 43 */       return;  this.shouldSneak = !this.sneakTimer.hasReached(((Integer)this.sneakTime.getValue()).intValue());
/* 44 */     if (PlayerUtil.isOverAir() && (!((Boolean)this.onlyGround.getValue()).booleanValue() || (mc.getPlayer()).onGround) && (mc.getPlayer()).motionY < 0.1D) {
/* 45 */       this.shouldSneak = true;
/*    */     }
/*    */     
/* 48 */     if (((Boolean)this.holdSneak.getValue()).booleanValue()) {
/* 49 */       (mc.getGameSettings()).keyBindSneak.pressed = (GameSettings.isKeyDown((mc.getGameSettings()).keyBindSneak) && this.shouldSneak);
/*    */     } else {
/* 51 */       (mc.getGameSettings()).keyBindSneak.pressed = (GameSettings.isKeyDown((mc.getGameSettings()).keyBindSneak) || this.shouldSneak);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\ghost\LegitScaffold.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
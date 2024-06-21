/*    */ package cc.slack.features.modules.impl.ghost;
/*    */ 
/*    */ import cc.slack.events.impl.player.AttackEvent;
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.other.TimeUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Wtap", category = Category.GHOST)
/*    */ public class Wtap
/*    */   extends Module
/*    */ {
/*    */   private int ticks;
/* 22 */   private final TimeUtil wtapTimer = new TimeUtil();
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onAttack(AttackEvent event) {
/* 27 */     if (this.wtapTimer.hasReached(500L)) {
/* 28 */       this.wtapTimer.reset();
/* 29 */       this.ticks = 2;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 36 */     switch (this.ticks) {
/*    */       case 2:
/* 38 */         (mc.getGameSettings()).keyBindForward.pressed = false;
/* 39 */         this.ticks--;
/*    */         break;
/*    */       case 1:
/* 42 */         (mc.getGameSettings()).keyBindForward.pressed = GameSettings.isKeyDown((mc.getGameSettings()).keyBindForward);
/* 43 */         this.ticks--;
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\ghost\Wtap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
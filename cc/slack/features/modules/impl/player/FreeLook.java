/*    */ package cc.slack.features.modules.impl.player;
/*    */ 
/*    */ import cc.slack.events.impl.game.TickEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.render.FreeLookUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "FreeLook", category = Category.PLAYER, key = 42)
/*    */ public class FreeLook
/*    */   extends Module
/*    */ {
/*    */   private boolean freeLookingactivated;
/*    */   
/*    */   public void onDisable() {
/* 23 */     this.freeLookingactivated = false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onTick(TickEvent event) {
/* 29 */     if ((mc.getPlayer()).ticksExisted < 10) {
/* 30 */       stop();
/*    */     }
/* 32 */     if (Keyboard.isKeyDown(getKey())) {
/* 33 */       if (!this.freeLookingactivated) {
/* 34 */         this.freeLookingactivated = true;
/* 35 */         FreeLookUtil.enable();
/* 36 */         (mc.getGameSettings()).thirdPersonView = 1;
/*    */       } 
/* 38 */     } else if (this.freeLookingactivated) {
/* 39 */       stop();
/*    */     } 
/*    */   }
/*    */   
/*    */   private void stop() {
/* 44 */     toggle();
/* 45 */     FreeLookUtil.setFreelooking(false);
/* 46 */     this.freeLookingactivated = false;
/* 47 */     (mc.getGameSettings()).thirdPersonView = 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\player\FreeLook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
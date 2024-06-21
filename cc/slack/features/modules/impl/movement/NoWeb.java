/*    */ package cc.slack.features.modules.impl.movement;
/*    */ 
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.player.MovementUtil;
/*    */ import io.github.nevalackin.radbus.Listen;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "NoWeb", category = Category.MOVEMENT)
/*    */ public class NoWeb
/*    */   extends Module
/*    */ {
/* 20 */   private final ModeValue<String> mode = new ModeValue((Object[])new String[] { "Vanilla", "Fast Fall", "Verus" });
/*    */ 
/*    */   
/*    */   public NoWeb() {
/* 24 */     addSettings(new Value[] { (Value)this.mode });
/*    */   }
/*    */ 
/*    */   
/*    */   @Listen
/*    */   public void onUpdate(UpdateEvent event) {
/* 30 */     if (!(mc.getPlayer()).isInWeb) {
/*    */       return;
/*    */     }
/*    */     
/* 34 */     switch (((String)this.mode.getValue()).toLowerCase()) {
/*    */       case "vanilla":
/* 36 */         (mc.getPlayer()).isInWeb = false;
/*    */         break;
/*    */       case "fast fall":
/* 39 */         if ((mc.getPlayer()).onGround) mc.getPlayer().jump(); 
/* 40 */         if ((mc.getPlayer()).motionY > 0.0D) {
/* 41 */           (mc.getPlayer()).motionY -= (mc.getPlayer()).motionY * 2.0D;
/*    */         }
/*    */         break;
/*    */       case "verus":
/* 45 */         MovementUtil.strafe(1.0F);
/* 46 */         if (!(mc.getGameSettings()).keyBindJump.isKeyDown() && !(mc.getGameSettings()).keyBindSneak.isKeyDown()) {
/* 47 */           (mc.getPlayer()).motionY = 0.0D;
/*    */         }
/* 49 */         if ((mc.getGameSettings()).keyBindJump.isKeyDown()) {
/* 50 */           (mc.getPlayer()).motionY = 4.42D;
/*    */         }
/* 52 */         if ((mc.getGameSettings()).keyBindSneak.isKeyDown())
/* 53 */           (mc.getPlayer()).motionY = -4.42D; 
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\NoWeb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
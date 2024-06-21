/*    */ package cc.slack.features.modules.impl.movement;
/*    */ 
/*    */ import cc.slack.events.impl.player.MoveEvent;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Jesus", category = Category.MOVEMENT, key = 36)
/*    */ public class Jesus
/*    */   extends Module
/*    */ {
/* 24 */   private final ModeValue<String> mode = new ModeValue((Object[])new String[] { "Vanilla", "Verus" });
/*    */ 
/*    */   
/*    */   public Jesus() {
/* 28 */     addSettings(new Value[] { (Value)this.mode });
/*    */   }
/*    */   
/*    */   @Listen
/*    */   public void onMove(MoveEvent event) {
/* 33 */     if (!mc.getPlayer().isInWater())
/*    */       return; 
/* 35 */     switch (((String)this.mode.getValue()).toLowerCase()) {
/*    */       case "vanilla":
/* 37 */         MovementUtil.setSpeed(event, 0.4000000059604645D);
/* 38 */         event.setY(0.01D);
/*    */         break;
/*    */       case "verus":
/* 41 */         MovementUtil.setSpeed(event, 0.4000000059604645D);
/* 42 */         event.setY(0.4050000011920929D);
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\movement\Jesus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
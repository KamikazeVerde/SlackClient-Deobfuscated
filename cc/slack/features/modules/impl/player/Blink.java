/*    */ package cc.slack.features.modules.impl.player;
/*    */ 
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.utils.player.BlinkUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "Blink", category = Category.PLAYER)
/*    */ public class Blink
/*    */   extends Module
/*    */ {
/* 19 */   private final BooleanValue outbound = new BooleanValue("Outbound", true);
/* 20 */   private final BooleanValue inbound = new BooleanValue("Inbound", false);
/*    */ 
/*    */   
/*    */   public Blink() {
/* 24 */     addSettings(new Value[] { (Value)this.outbound, (Value)this.inbound });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 29 */     BlinkUtil.enable(((Boolean)this.inbound.getValue()).booleanValue(), ((Boolean)this.outbound.getValue()).booleanValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 34 */     BlinkUtil.disable();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\player\Blink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */